package com.cbsinc.cms.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Upload filename hardening (pass 27) and path-traversal defence. */
class FileNamesTest {

    @Test
    void stripPathHandlesBothSeparators() {
        assertEquals("photo.jpg", FileNames.stripPath("C:\\Users\\me\\photo.jpg"));
        assertEquals("web.xml", FileNames.stripPath("../../../WEB-INF/web.xml"));
        assertEquals("a.png", FileNames.stripPath("a.png"));
    }

    @Test
    void extensionIsLowercasedWithoutDot() {
        assertEquals("jpg", FileNames.extension("Photo.JPG"));
        assertEquals("png", FileNames.extension("dir/sub/img.png"));
    }

    @Test
    void noExtensionWhenDotIsMissingOrLeadingOrTrailing() {
        assertEquals("", FileNames.extension("README"));
        assertEquals("", FileNames.extension(".htaccess"));
        assertEquals("", FileNames.extension("report."));
        assertEquals("", FileNames.extension(null));
    }

    @Test
    void injectionInExtensionIsRejected() {
        assertEquals("", FileNames.extension("shell.jsp'--"));
        assertEquals("", FileNames.extension("evil.jpg/.."));
        assertEquals("", FileNames.extension("x." + "a".repeat(11)));
    }

    @Test
    void onlyImageExtensionsAreAllowed() {
        assertTrue(FileNames.isAllowedImage("cat.jpg"));
        assertTrue(FileNames.isAllowedImage("cat.JPEG"));
        assertTrue(FileNames.isAllowedImage("logo.png"));
        assertTrue(FileNames.isAllowedImage("banner.webp"));
    }

    @Test
    void activeContentUploadsAreBlocked() {
        assertFalse(FileNames.isAllowedImage("shell.jsp"));
        assertFalse(FileNames.isAllowedImage("page.html"));
        assertFalse(FileNames.isAllowedImage("vector.svg"));
        assertFalse(FileNames.isAllowedImage("evil.php"));
        assertFalse(FileNames.isAllowedImage("noext"));
        assertFalse(FileNames.isAllowedImage(null));
    }
}
