package com.cbsinc.cms.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Latency guard (pass 22): timing helpers and message formatting. */
class LatencyTest {

    @Test
    void elapsedMsIsNonNegativeAndPlausible() {
        long start = Latency.start();
        long ms = Latency.elapsedMs(start);
        assertTrue(ms >= 0, "elapsed must never be negative");
        assertTrue(ms < 60000, "a no-op must not appear to take a minute");
    }

    @Test
    void elapsedGrowsAfterASleep() throws InterruptedException {
        long start = Latency.start();
        Thread.sleep(15);
        assertTrue(Latency.elapsedMs(start) >= 10, "should measure at least ~10 ms after a 15 ms sleep");
    }

    @Test
    void slowSqlMessageCollapsesWhitespaceAndNamesLocation() {
        Latency.SlowSqlException ex =
                new Latency.SlowSqlException("OrderFaced.load", 1234,
                        "select   *\n from    orders");
        String msg = ex.getMessage();
        assertTrue(msg.contains("OrderFaced.load"), msg);
        assertTrue(msg.contains("1234"), msg);
        assertTrue(msg.contains("select * from orders"), "whitespace must be collapsed: " + msg);
    }

    @Test
    void slowSqlMessageTruncatesVeryLongQuery() {
        String longQuery = "x".repeat(2000);
        String msg = new Latency.SlowSqlException("w", 5000, longQuery).getMessage();
        assertTrue(msg.contains("..."), "an over-long query must be abbreviated");
        assertTrue(msg.length() < 900, "abbreviated message must stay short: " + msg.length());
    }

    @Test
    void slowSqlHandlesNullQuery() {
        String msg = new Latency.SlowSqlException("w", 2000, null).getMessage();
        assertTrue(msg.contains("2000"), msg);
    }

    @Test
    void slowMethodMessageNamesLocationAndTime() {
        String msg = new Latency.SlowMethodException("OrderAction.doGet", 3000).getMessage();
        assertTrue(msg.contains("OrderAction.doGet"), msg);
        assertTrue(msg.contains("3000"), msg);
    }

    @Test
    void defaultThresholdsAreOneSecond() {
        // no -D overrides in the test JVM, so defaults apply
        assertEquals(1000L, Latency.SQL_THRESHOLD_MS);
        assertEquals(1000L, Latency.METHOD_THRESHOLD_MS);
    }
}
