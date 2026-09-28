package com.cbsinc.cms.payments;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/** Circuit breaker state (pass 3): a fresh/reset breaker is closed. */
class PaymentRouterTest {

    @Test
    void breakerIsClosedAfterReset() {
        PaymentRouter r = PaymentRouter.getInstance();
        r.resetCircuit();
        assertFalse(r.isCircuitOpen(), "after reset the circuit must be closed");
    }
}
