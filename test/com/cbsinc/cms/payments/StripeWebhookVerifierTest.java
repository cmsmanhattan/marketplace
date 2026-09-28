package com.cbsinc.cms.payments;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;

/**
 * The only defence on the Stripe webhook (passes 3/32). A forged
 * checkout.session.completed would credit an account, so these must hold.
 */
class StripeWebhookVerifierTest {

    private static final String SECRET = "whsec_testsecret";

    /** Compute the signature Stripe would send for this body at this timestamp. */
    private static String sign(byte[] payload, long ts, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        mac.update(Long.toString(ts).getBytes(StandardCharsets.US_ASCII));
        mac.update((byte) '.');
        mac.update(payload);
        byte[] sig = mac.doFinal();
        StringBuilder hex = new StringBuilder();
        for (byte b : sig) hex.append(String.format("%02x", b));
        return hex.toString();
    }

    @Test
    void validSignatureIsAccepted() throws Exception {
        byte[] body = "{\"type\":\"checkout.session.completed\"}".getBytes(StandardCharsets.UTF_8);
        long now = 1_700_000_000L;
        String header = "t=" + now + ",v1=" + sign(body, now, SECRET);
        assertTrue(StripeWebhookVerifier.verify(body, header, SECRET, 300, now));
    }

    @Test
    void forgedSignatureIsRejected() {
        byte[] body = "{}".getBytes(StandardCharsets.UTF_8);
        long now = 1_700_000_000L;
        String header = "t=" + now + ",v1=deadbeefdeadbeefdeadbeefdeadbeef";
        assertFalse(StripeWebhookVerifier.verify(body, header, SECRET, 300, now));
    }

    @Test
    void wrongSecretIsRejected() throws Exception {
        byte[] body = "{}".getBytes(StandardCharsets.UTF_8);
        long now = 1_700_000_000L;
        String header = "t=" + now + ",v1=" + sign(body, now, "whsec_other");
        assertFalse(StripeWebhookVerifier.verify(body, header, SECRET, 300, now));
    }

    @Test
    void tamperedBodyIsRejected() throws Exception {
        byte[] original = "{\"amount\":100}".getBytes(StandardCharsets.UTF_8);
        long now = 1_700_000_000L;
        String header = "t=" + now + ",v1=" + sign(original, now, SECRET);
        byte[] tampered = "{\"amount\":999}".getBytes(StandardCharsets.UTF_8);
        assertFalse(StripeWebhookVerifier.verify(tampered, header, SECRET, 300, now));
    }

    @Test
    void staleTimestampIsRejectedToStopReplay() throws Exception {
        byte[] body = "{}".getBytes(StandardCharsets.UTF_8);
        long signedAt = 1_700_000_000L;
        String header = "t=" + signedAt + ",v1=" + sign(body, signedAt, SECRET);
        long muchLater = signedAt + 10_000; // far outside a 300 s window
        assertFalse(StripeWebhookVerifier.verify(body, header, SECRET, 300, muchLater));
    }

    @Test
    void futureTimestampIsAlsoRejected() throws Exception {
        byte[] body = "{}".getBytes(StandardCharsets.UTF_8);
        long signedAt = 1_700_010_000L;
        String header = "t=" + signedAt + ",v1=" + sign(body, signedAt, SECRET);
        long earlier = signedAt - 10_000;
        assertFalse(StripeWebhookVerifier.verify(body, header, SECRET, 300, earlier));
    }

    @Test
    void anyOneOfSeveralSignaturesMatchesDuringRotation() throws Exception {
        byte[] body = "{}".getBytes(StandardCharsets.UTF_8);
        long now = 1_700_000_000L;
        String good = sign(body, now, SECRET);
        String header = "t=" + now + ",v1=00000000,v1=" + good;
        assertTrue(StripeWebhookVerifier.verify(body, header, SECRET, 300, now));
    }

    @Test
    void emptyOrNullInputsAreRejected() {
        byte[] body = "{}".getBytes(StandardCharsets.UTF_8);
        assertFalse(StripeWebhookVerifier.verify(null, "t=1,v1=aa", SECRET, 300, 1));
        assertFalse(StripeWebhookVerifier.verify(body, null, SECRET, 300, 1));
        assertFalse(StripeWebhookVerifier.verify(body, "t=1,v1=aa", "", 300, 1));
        assertFalse(StripeWebhookVerifier.verify(body, "t=1,v1=aa", null, 300, 1));
    }

    @Test
    void malformedHeaderIsRejected() {
        byte[] body = "{}".getBytes(StandardCharsets.UTF_8);
        assertFalse(StripeWebhookVerifier.verify(body, "garbage", SECRET, 300, 1));
        assertFalse(StripeWebhookVerifier.verify(body, "t=notanumber,v1=aa", SECRET, 300, 1));
        assertFalse(StripeWebhookVerifier.verify(body, "v1=aa", SECRET, 300, 1)); // no t
        assertFalse(StripeWebhookVerifier.verify(body, "t=1", SECRET, 300, 1));   // no v1
    }

    @Test
    void hexToBytesRoundTripsAndRejectsBadInput() {
        assertArrayEquals(new byte[]{0x00, (byte) 0xff, 0x10},
                StripeWebhookVerifier.hexToBytes("00ff10"));
        assertNull(StripeWebhookVerifier.hexToBytes("abc"));   // odd length
        assertNull(StripeWebhookVerifier.hexToBytes("zz"));    // non-hex
        assertNull(StripeWebhookVerifier.hexToBytes(null));
    }
}
