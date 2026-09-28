package com.cbsinc.cms.payments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Payment configuration (pass 3). System properties take precedence over the
 * bundle, which lets these tests run without payment.properties on the path.
 */
class PaymentConfigTest {

    private void set(String k, String v) { System.setProperty(k, v); }

    @AfterEach
    void clearProps() {
        for (String k : new String[]{
                "payment.primary", "payment.fallback_to_legacy",
                "payment.circuit_failures", "stripe.public_base_url",
                "stripe.currencies", "stripe.webhook_tolerance_seconds",
                "stripe.enabled"}) {
            System.clearProperty(k);
        }
        PaymentConfig.reload();
    }

    @Test
    void defaultsApplyWhenNothingIsConfigured() {
        PaymentConfig c = PaymentConfig.getInstance();
        assertEquals(3, c.getCircuitFailures());
        assertEquals(120, c.getCircuitOpenSeconds());
        assertEquals(300, c.getStripeWebhookToleranceSeconds());
        assertTrue(c.isFallbackToLegacy());
    }

    @Test
    void booleanAcceptsSeveralTruthyForms() {
        PaymentConfig c = PaymentConfig.getInstance();
        set("stripe.enabled", "yes");
        assertTrue(c.getBoolean("stripe.enabled", false));
        set("stripe.enabled", "1");
        assertTrue(c.getBoolean("stripe.enabled", false));
        set("stripe.enabled", "TRUE");
        assertTrue(c.getBoolean("stripe.enabled", false));
        set("stripe.enabled", "no");
        assertFalse(c.getBoolean("stripe.enabled", true));
    }

    @Test
    void intFallsBackOnGarbage() {
        PaymentConfig c = PaymentConfig.getInstance();
        set("payment.circuit_failures", "notanumber");
        assertEquals(7, c.getInt("payment.circuit_failures", 7));
    }

    @Test
    void primaryChannelReadsProperty() {
        set("payment.primary", "legacy");
        assertEquals(PaymentChannel.LEGACY, PaymentConfig.getInstance().getPrimaryChannel());
        set("payment.primary", "stripe");
        assertEquals(PaymentChannel.STRIPE, PaymentConfig.getInstance().getPrimaryChannel());
    }

    @Test
    void publicBaseUrlHasTrailingSlashesTrimmed() {
        set("stripe.public_base_url", "https://shop.example.com///");
        assertEquals("https://shop.example.com", PaymentConfig.getInstance().getStripePublicBaseUrl());
    }

    @Test
    void currenciesAreParsedUppercasedAndDeduped() {
        set("stripe.currencies", "usd, eur ,USD,,gbp");
        java.util.Set<String> cur = PaymentConfig.getInstance().getStripeCurrencies();
        assertTrue(cur.contains("USD"));
        assertTrue(cur.contains("EUR"));
        assertTrue(cur.contains("GBP"));
        assertEquals(3, cur.size(), "duplicates and blanks must be dropped");
    }
}
