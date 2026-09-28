package com.cbsinc.cms.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Input hardening added across the security passes. */
class ValidationTest {

    // --- escapeSqlLiteral: the stop-gap for the concatenated search queries ---

    @Test
    void escapeSqlDoublesSingleQuote() {
        assertEquals("O''Brien", Validation.escapeSqlLiteral("O'Brien"));
    }

    @Test
    void escapeSqlEscapesBackslash() {
        assertEquals("a\\\\b", Validation.escapeSqlLiteral("a\\b"));
    }

    @Test
    void escapeSqlNeutralisesClassicInjection() {
        String out = Validation.escapeSqlLiteral("' OR '1'='1");
        // A textual check for "' OR" can never pass: the escaped form of a
        // leading quote is "''", which itself begins with a quote, so the
        // substring occurs in any correctly escaped output. What actually
        // neutralises the injection is that every quote is doubled, so the
        // literal cannot be closed early. Assert the escaped form itself.
        assertTrue(out.startsWith("''"));
        assertEquals("'' OR ''1''=''1", out);
    }

    @Test
    void escapeSqlDropsControlCharsIncludingNul() {
        assertEquals("ab", Validation.escapeSqlLiteral("a\u0000\u0007b"));
    }

    @Test
    void escapeSqlNullBecomesEmpty() {
        assertEquals("", Validation.escapeSqlLiteral(null));
    }

    // --- escapeXml: used before splicing values into the generated XML ---

    @Test
    void escapeXmlReplacesMarkup() {
        assertEquals("&lt;b&gt;&amp;&quot;", Validation.escapeXml("<b>&\""));
    }

    @Test
    void escapeXmlNullBecomesEmpty() {
        assertEquals("", Validation.escapeXml(null));
    }

    // --- isNonNegativeInteger: the id guard on ~38 *_id parameters ---

    @Test
    void nonNegativeIntegerAcceptsDigits() {
        assertTrue(Validation.isNonNegativeInteger("0"));
        assertTrue(Validation.isNonNegativeInteger("12345"));
        assertTrue(Validation.isNonNegativeInteger("  42  "));
    }

    @Test
    void nonNegativeIntegerRejectsRubbish() {
        assertFalse(Validation.isNonNegativeInteger(null));
        assertFalse(Validation.isNonNegativeInteger(""));
        assertFalse(Validation.isNonNegativeInteger("-1"));
        assertFalse(Validation.isNonNegativeInteger("1 OR 1=1"));
        assertFalse(Validation.isNonNegativeInteger("3.14"));
        assertFalse(Validation.isNonNegativeInteger("0x1F"));
    }

    // --- isDecimal: the fromcost / tocost filters ---

    @Test
    void decimalAcceptsSignedAndFractional() {
        assertTrue(Validation.isDecimal("12"));
        assertTrue(Validation.isDecimal("-3"));
        assertTrue(Validation.isDecimal("0.5"));
        assertTrue(Validation.isDecimal("-0.75"));
    }

    @Test
    void decimalRejectsMalformed() {
        assertFalse(Validation.isDecimal(""));
        assertFalse(Validation.isDecimal("."));
        assertFalse(Validation.isDecimal("-"));
        assertFalse(Validation.isDecimal("1.2.3"));
        assertFalse(Validation.isDecimal("1--"));
        assertFalse(Validation.isDecimal("1e5"));
        assertFalse(Validation.isDecimal(null));
    }

    @Test
    void numericIdAcceptsPlainDigits() {
        assertEquals("42", Validation.requireNumericId("42"));
        assertEquals("42", Validation.requireNumericId("  42  "));
        assertEquals("-1", Validation.requireNumericId("-1"));
        assertEquals("0", Validation.requireNumericId("0"));
    }

    @Test
    void numericIdPassesThroughBlanks() {
        assertEquals("", Validation.requireNumericId(null));
        assertEquals("", Validation.requireNumericId(""));
        assertEquals("", Validation.requireNumericId("   "));
    }

    @Test
    void numericIdNeutralisesInjection() {
        // Callers splice this straight into "... site_id = " + value + " and ...",
        // unquoted, so anything non-numeric must not survive.
        assertEquals("-1", Validation.requireNumericId("1 OR 1=1"));
        assertEquals("-1", Validation.requireNumericId("1; DROP TABLE tuser"));
        assertEquals("-1", Validation.requireNumericId("1 UNION SELECT passwd FROM tuser"));
        assertEquals("-1", Validation.requireNumericId("1/*comment*/"));
        assertEquals("-1", Validation.requireNumericId("1'"));
        assertEquals("-1", Validation.requireNumericId("abc"));
    }

    @Test
    void numericIdRejectsOverlongDigitRuns() {
        // 18 digits is the widest that still fits a BIGINT comparison.
        assertEquals("123456789012345678", Validation.requireNumericId("123456789012345678"));
        assertEquals("-1", Validation.requireNumericId("1234567890123456789"));
    }
}
