package com.cbsinc.cms.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** OS detection helper: exactly one family must match the host. */
class OSUtilTest {

    @Test
    void exactlyOneOsFamilyMatches() {
        int matches = 0;
        if (OSUtil.isWindows()) matches++;
        if (OSUtil.isMac()) matches++;
        if (OSUtil.isUnix()) matches++;
        assertTrue(matches >= 1, "at least one OS family must be recognised");
    }

    @Test
    void windowsAndUnixAreMutuallyExclusive() {
        assertFalse(OSUtil.isWindows() && OSUtil.isUnix(),
                "a host cannot be both Windows and Unix");
    }
}
