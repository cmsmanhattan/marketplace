package com.cbsinc.cms.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** PBKDF2 password hashing (pass 29), including legacy clear-text acceptance. */
class PasswordHashTest {

    @Test
    void hashIsRecognisedAndVerifies() {
        String stored = PasswordHash.hash("s3cret!");
        assertTrue(PasswordHash.looksHashed(stored), "hash() output must look hashed");
        assertTrue(PasswordHash.matches("s3cret!", stored));
        assertFalse(PasswordHash.matches("wrong", stored));
    }

    @Test
    void hashIsSaltedSoTwoHashesDiffer() {
        assertNotEquals(PasswordHash.hash("same"), PasswordHash.hash("same"));
    }

    @Test
    void plainTextStoredPasswordStillAccepted() {
        // migration path: an un-migrated row holds the password in clear text
        assertFalse(PasswordHash.looksHashed("plainpass"));
        assertTrue(PasswordHash.matches("plainpass", "plainpass"));
        assertFalse(PasswordHash.matches("plainpass", "different"));
    }

    @Test
    void generatedTemporaryPasswordIsUsable() {
        String tmp = PasswordHash.generateTemporary();
        org.junit.jupiter.api.Assertions.assertNotNull(tmp);
        assertTrue(tmp.length() >= 6);
    }
}
