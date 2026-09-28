-- ---------------------------------------------------------------------------
-- Pass 39, item 4: widen tuser.passwd for PBKDF2 hashes.
--
-- DEPLOYMENT BLOCKER. Pass 29 replaced clear-text passwords with salted
-- PBKDF2-HMAC-SHA256 hashes produced by com.cbsinc.cms.utils.PasswordHash.
-- Those hashes are self-describing strings of the form
--
--     pbkdf2$120000$<base64 16-byte salt>$<base64 32-byte key>
--       6  +1+  6  +1+          24        +1+         44        = 83 characters
--
-- The schema still declares `PASSWD` varchar(50). On a non-strict MySQL server
-- every stored hash would be silently truncated to 50 characters and NO USER
-- COULD EVER LOG IN AGAIN; on a strict server the UPDATE fails outright.
--
-- Run this BEFORE deploying the application. 255 leaves room for a future
-- increase in the iteration count or a longer derived key.
-- ---------------------------------------------------------------------------

ALTER TABLE `tuser` MODIFY `PASSWD` VARCHAR(255) DEFAULT NULL;

-- Verification: this must report 255.
-- SELECT CHARACTER_MAXIMUM_LENGTH FROM information_schema.COLUMNS
--  WHERE TABLE_NAME = 'tuser' AND COLUMN_NAME = 'PASSWD';

-- Any row whose password was already truncated by an earlier deploy is
-- unusable and must be reset through the "forgot password" flow. This query
-- lists them (a valid hash always has exactly three '$' separators):
--
-- SELECT USER_ID, LOGIN, E_MAIL FROM tuser
--  WHERE PASSWD LIKE 'pbkdf2$%'
--    AND LENGTH(PASSWD) < 83;
