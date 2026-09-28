package com.cbsinc.cms.faceds;

/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code.
 * You can not use it and you cannot change it without written permission from Konstantin Grabko
 * Email: konstantin.grabko@yahoo.com or konstantin.grabko@gmail.com
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
import java.io.File;
import java.io.FilenameFilter;
import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;

/**
 * Recursive listing of files under a directory, as an array, a collection or XML for the file-selection pages.
 */
public class SearchFilesFaced {

	static Calendar calendar = null;

	public static FilenameFilter filenameFilter = new FilenameFilter() {
		/**
		 * @return true for regular files
		 */
		public boolean accept(File dir, String name) {
			return name.endsWith(".txt") || name.endsWith(".htm") || dir.isDirectory();
		}
	};

	public SearchFilesFaced() {
		calendar = Calendar.getInstance();

	}

	/**
	 * @return the files under the directory matching the filter
	 */
	public static File[] listFilesAsArray(File directory, FilenameFilter filter, boolean recurse) {
		Collection files = listFiles(directory, filter, recurse);
		File[] arr = new File[files.size()];
		return (File[]) files.toArray(arr);
	}

	/**
	 * @return the files under the directory matching the filter
	 */
	public static Collection listFiles(File directory, FilenameFilter filter, boolean recurse) {
		Vector files = new Vector();
		File[] entries = directory.listFiles(filter);
		// File[] entries = directory.listFiles();
		for (File entry : entries) {

			/*
			 * if (filter == null || filter.accept(directory, entry.getName())) {
			 * files.add(entry); System.out.println(entry.getAbsolutePath()) ; }
			 */
			if (filter != null && filter.accept(directory, entry.getName())) {
				files.add(entry);
				System.out.println(entry.getAbsolutePath());
			}

			if (recurse && entry.isDirectory()) {
				files.addAll(listFiles(entry, filter, recurse));
			}
		}
		return files;
	}

	/**
	 * @return the files as XML for the file-selection template
	 */
	public synchronized static String listFilesXML(File directory, FilenameFilter filter, boolean recurse) {
		StringBuffer xmlbuff = new StringBuffer();
		File[] entries = directory.listFiles(filter);

		for (File entry : entries) {

			if (filter != null && filter.accept(directory, entry.getName())) {

				calendar.setTimeInMillis(entry.lastModified());
				xmlbuff.append("<file>");
				xmlbuff.append("<path>" + entry.getAbsolutePath() + "</path>");
				xmlbuff.append("<dir>" + entry.getParent() + "</dir>");
				xmlbuff.append("<name>" + entry.getName() + "</name>");
				xmlbuff.append(
						"<time>" + calendar.get(Calendar.HOUR) + ":" + +calendar.get(Calendar.MINUTE) + "</time>");
				xmlbuff.append("<date>" + calendar.get(Calendar.YEAR) + "." + calendar.get(Calendar.MONTH) + "."
						+ calendar.get(Calendar.DAY_OF_MONTH) + "</date>");
				xmlbuff.append("<length>" + entry.length() + "</length>");
				xmlbuff.append("</file>");
				xmlbuff.append(entry.getAbsolutePath());
			}

			if (recurse && entry.isDirectory()) {
				listFiles(entry, filter, recurse);
			}
		}

		return xmlbuff.toString();
	}

	/**
	 * Command-line probe.
	 */
	public static void main(String[] args) {
		// listFilesAsArray( new File("c://") , filenameFilter , true ) ;
		listFiles(new File("c://"), filenameFilter, true);
	}

}
