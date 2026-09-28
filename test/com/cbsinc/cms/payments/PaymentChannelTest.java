package com.cbsinc.cms.payments;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PaymentChannelTest {

    @Test
    void codesAreStable() {
        assertEquals("stripe", PaymentChannel.STRIPE.code());
        assertEquals("legacy", PaymentChannel.LEGACY.code());
    }
}
