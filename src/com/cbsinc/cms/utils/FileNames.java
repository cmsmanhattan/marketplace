package com.cbsinc.cms.utils;

/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code. You can not use it and you
 * cannot change it without written permission from Konstantin Grabko Email:
 * konstantin.grabko@yahoo.com or konstantin.grabko@gmail.com
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002-2025
 * </p>
 * <p>
 * Company: CENTER BUSINESS SOLUTIONS INC
 * </p>
 *
 * @author Konstantin Grabko
 * @version 1.0
 */

/**
 * Filename helpers for the upload and download servlets.
 *
 * <p>
 * Added while fixing a crash that appeared across the upload path. The servlets
 * all derived a stored filename the same way:
 *
 * <pre>
 * FileName = "" + intID + FileName.substring(FileName.lastIndexOf("."));
 * </pre>
 *
 * When the uploaded part carries a name with no dot in it - "screenshot",
 * "IMG_0431", or anything sent by a client that does not bother with
 * extensions - lastIndexOf(".") returns -1 and substring(-1) throws
 * StringIndexOutOfBoundsException. In these servlets that happened after the
 * database row had already been inserted, so the catalogue ended up with a
 * record pointing at a file that was never written.
 * </p>
 */
public final class FileNames {

	private FileNames() {
	}

	/**
	 * Returns the extension of a filename, including the leading dot.
	 *
	 * @param fileName filename, possibly null, possibly without an extension
	 * @return the extension including the dot (".jpg"), or an empty string when
	 *         there is none. Never null, never throws.
	 */
	public static String extensionWithDot(String fileName) {
		if (fileName == null)
			return "";

		String name = stripPath(fileName);
		int dot = name.lastIndexOf('.');

		// No dot, a leading dot (".htaccess" is not an extension), or a trailing
		// dot ("report." has nothing after it) all mean "no usable extension".
		if (dot <= 0 || dot == name.length() - 1)
			return "";

		String ext = name.substring(dot + 1);

		// The extension is later concatenated into an INSERT and into a path,
		// so it must be plain ASCII letters and digits. "jpg'--" or "jpg/.."
		// is not an extension; treat it as absent.
		for (int i = 0; i < ext.length(); i++) {
			char c = ext.charAt(i);
			boolean ok = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
			if (!ok)
				return "";
		}
		if (ext.length() > 10)
			return "";

		return "." + ext;
	}

	/**
	 * Returns the extension of a filename without the leading dot, lowercased.
	 *
	 * @param fileName filename, possibly null, possibly without an extension
	 * @return the extension ("jpg"), or an empty string when there is none
	 */
	public static String extension(String fileName) {
		String ext = extensionWithDot(fileName);
		return ext.isEmpty() ? "" : ext.substring(1).toLowerCase();
	}

	/**
	 * Strips any directory part from a name supplied by a client.
	 *
	 * <p>
	 * A multipart filename is attacker-controlled and some clients legitimately
	 * send a full local path. Internet Explorer sends "C:\Users\me\photo.jpg";
	 * a hostile client sends "../../../WEB-INF/web.xml". Only the last segment
	 * is ever meaningful, so both separators are handled regardless of the
	 * platform the server runs on.
	 * </p>
	 *
	 * @param fileName filename as received from the client, may be null
	 * @return the final path segment, or an empty string if there is none
	 */
	public static String stripPath(String fileName) {
		if (fileName == null)
			return "";

		String name = fileName;
		int slash = name.lastIndexOf('/');
		if (slash >= 0)
			name = name.substring(slash + 1);

		int backslash = name.lastIndexOf('\\');
		if (backslash >= 0)
			name = name.substring(backslash + 1);

		return name.trim();
	}

	/**
	 * Extensions accepted for image/logo uploads. Anything not on this list
	 * (in particular .jsp, .jspx, .html, .svg, .php) is rejected to prevent
	 * uploading a file that the web container would execute or a browser would
	 * run as active content.
	 */
	private static final java.util.Set<String> ALLOWED_IMAGE_EXTENSIONS =
			new java.util.HashSet<>(java.util.Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp"));

	/**
	 * @return true when the file name ends with an allowed image extension.
	 */
	public static boolean isAllowedImage(String fileName) {
		return ALLOWED_IMAGE_EXTENSIONS.contains(extension(fileName));
	}


	/**
	 * Verifies that the bytes actually are the image type the extension claims.
	 *
	 * <p>The extension whitelist alone only stops the container from executing the
	 * upload; it does not stop a file whose contents are something else entirely
	 * (a polyglot, an HTML document that a browser will sniff and render as active
	 * content, or a payload aimed at an image library). This checks the leading
	 * signature bytes and requires them to agree with the extension.</p>
	 *
	 * @param fileName the declared file name, used for its extension
	 * @param content  the first bytes of the upload (at least 12 are needed)
	 * @return true when the signature matches the declared extension
	 */
	public static boolean hasMatchingImageSignature(String fileName, byte[] content) {
		if (content == null || content.length < 12) {
			return false;
		}
		String ext = extension(fileName);
		if ("jpg".equals(ext) || "jpeg".equals(ext)) {
			// SOI marker.
			return u(content[0]) == 0xFF && u(content[1]) == 0xD8 && u(content[2]) == 0xFF;
		}
		if ("png".equals(ext)) {
			return u(content[0]) == 0x89 && content[1] == 'P' && content[2] == 'N' && content[3] == 'G'
					&& u(content[4]) == 0x0D && u(content[5]) == 0x0A
					&& u(content[6]) == 0x1A && u(content[7]) == 0x0A;
		}
		if ("gif".equals(ext)) {
			return content[0] == 'G' && content[1] == 'I' && content[2] == 'F' && content[3] == '8'
					&& (content[4] == '7' || content[4] == '9') && content[5] == 'a';
		}
		if ("bmp".equals(ext)) {
			return content[0] == 'B' && content[1] == 'M';
		}
		if ("webp".equals(ext)) {
			// "RIFF" .... "WEBP"
			return content[0] == 'R' && content[1] == 'I' && content[2] == 'F' && content[3] == 'F'
					&& content[8] == 'W' && content[9] == 'E' && content[10] == 'B' && content[11] == 'P';
		}
		return false;
	}

	/**
	 * Full image-upload check: allowed extension AND matching content signature.
	 *
	 * @param fileName the declared file name
	 * @param content  the uploaded bytes
	 * @return true when the upload may be stored
	 */
	public static boolean isAllowedImage(String fileName, byte[] content) {
		return isAllowedImage(fileName) && hasMatchingImageSignature(fileName, content);
	}

	/** Widen a signature byte to its unsigned value. */
	private static int u(byte b) {
		return b & 0xFF;
	}

}
