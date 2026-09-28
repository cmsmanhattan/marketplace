package com.cbsinc.cms.services.ajax;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Vector;

import jakarta.servlet.http.HttpSession;

public class SearchFilesThread implements Runnable, Serializable {

	transient Calendar calendar = null;
	transient Thread thread = null;
	// HttpSession session = null ;
	transient private boolean isRunning = false;

	transient File directory = null;
	transient FilenameFilter filter = null;
	transient boolean recurse = false;;
	transient HttpSession session = null;
	transient int fromLenght = 0;
	transient int toLenght = 0;
	transient long fromDateInMlsec = 0;
	transient long toDateInMlsec = 0;

	public FilenameFilter filenameFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			boolean result = name.endsWith(".txt") || name.endsWith(".htm") || dir.isDirectory();

			return result;
		}
	};

	public SearchFilesThread() {
		this.calendar = Calendar.getInstance();
	}

	public File[] listFilesAsArray(File directory, FilenameFilter filter, boolean recurse) {
		Collection files = listFiles(directory, filter, recurse);
		File[] arr = new File[files.size()];
		return (File[]) files.toArray(arr);
	}

	public Collection listFiles(File directory, FilenameFilter filter, boolean recurse) {
		Vector files = new Vector();
		File[] entries = directory.listFiles(filter);
		for (File entry : entries) {

			if (filter != null && filter.accept(entry, entry.getName())) {
				files.add(entry);
				System.out.println(entry.getAbsolutePath());
			}

			if (recurse && entry.isDirectory()) {
				files.addAll(listFiles(entry, filter, recurse));
			}
		}
		return files;
	}

	public void listFilesXMLUnitTest(File directory, FilenameFilter filter, boolean recurse, int fromLenght,
			int toLenght, long fromDateInMlsec, long toDateInMlsec) {

		this.fromLenght = fromLenght;
		this.toLenght = toLenght;
		this.fromDateInMlsec = fromDateInMlsec;
		this.toDateInMlsec = toDateInMlsec;

		StringBuffer xmlbuff = null;
		LinkedList linkedList = new LinkedList();

		File[] entries = directory.listFiles(filter);

		for (File entry : entries) {

			if (filter != null && filter.accept(entry, entry.getName())) {

				xmlbuff = new StringBuffer();
				calendar.setTimeInMillis(entry.lastModified());
				xmlbuff.append("<file>\n");
				xmlbuff.append("<path>" + entry.getAbsolutePath() + "</path>\n");
				xmlbuff.append("<dir>" + entry.getParent() + "</dir>\n");
				xmlbuff.append("<name>" + entry.getName() + "</name>\n");
				xmlbuff.append(
						"<time>" + calendar.get(Calendar.HOUR) + ":" + +calendar.get(Calendar.MINUTE) + "</time>\n");
				xmlbuff.append("<date>" + calendar.get(Calendar.YEAR) + "." + calendar.get(Calendar.MONTH) + "."
						+ calendar.get(Calendar.DAY_OF_MONTH) + "</date>\n");
				xmlbuff.append("<length>" + entry.length() + "</length>\n");
				xmlbuff.append("</file>\n");
				xmlbuff.append(entry.getAbsolutePath());
				linkedList.add(xmlbuff.toString());
				System.out.println(xmlbuff.toString());
			}

			if (recurse && entry.isDirectory()) {
				listFilesXMLUnitTest(entry, filter, recurse, toLenght, toLenght, toDateInMlsec, toDateInMlsec);
			}
		}

	}

	public void listFilesXMLThread(File directory, FilenameFilter filter, boolean recurse, HttpSession session) {
		if (isRunning)
			return;
		this.directory = directory;
		this.filter = filter;
		this.recurse = recurse;
		this.session = session;
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}

	public void listFilesXML(File directory, FilenameFilter filter, boolean recurse) {
		// StringBuffer allbuff = null ;
		Vector vector = null;

		if (session.getAttribute("result") instanceof Vector) {
			vector = (Vector) session.getAttribute("result");
		} else {
			session.setAttribute("result", new Vector());
			vector = (Vector) session.getAttribute("result");
		}

		File[] entries = directory.listFiles(filter);

		for (File entry : entries) {

			if (filter != null && filter.accept(entry, entry.getName())) {

				StringBuffer xmlbuff = new StringBuffer();
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
				xmlbuff.append("</file>\n");
				// xmlbuff.append(entry.getAbsolutePath()) ;
				vector.add(xmlbuff.toString());
			}

			if (recurse && entry.isDirectory()) {
				listFilesXML(entry, filter, recurse);
			}
		}

	}

	public static void main(String[] args) {
		SearchFilesThread searchFilesThread = new SearchFilesThread();
		searchFilesThread.listFilesXMLUnitTest(new File("c://Far"), searchFilesThread.filenameFilter, true, 0, 100000,
				0, 2000000000);
	}

	public void run() {

		listFilesXML(directory, filter, recurse);
		isRunning = false;
	}

	public void stop() {

		thread.interrupt();
		thread = null;
		isRunning = false;
	}

	@Override
	protected void finalize() throws Throwable {
		if (thread != null) {
			thread.interrupt();
			thread = null;
		}

		super.finalize();
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
