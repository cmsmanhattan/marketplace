package com.cbsinc.cms.services.mailserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code.
 * Программный код написан Грабко Константином Владимировичем и является его интеллектуальной
 * собственностью.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Grabko Business (Предприниматель Грабко Константин Владимирович)
 * </p>
 *
 * @author Konstantin Grabko
 * @version 1.0
 */
import java.net.Socket;

import org.apache.log4j.Logger;

public class AddUserInMail {

	static private Logger LOGGER = Logger.getLogger(AddUserInMail.class);

	private String rootPassword = "root";
	private String rootLogin = "root";
	private String addUserLogin = "root";
	private String addUserPassword = "root";

	String szStr = "";

	public void exec(String rootLogin, String rootPassword, String addUserLogin, String addUserPassword, String host)
			throws Exception {

		this.rootLogin = rootLogin;
		this.rootPassword = rootPassword;
		this.addUserLogin = addUserLogin;
		this.addUserPassword = addUserPassword;

		System.out.println("* Socket Mail Server *");
		Socket s = null;
		InputStream is = null;
		OutputStream os = null;

		try {
			s = new Socket(host, 4555);
			int nPort = s.getLocalPort();
			System.out.println("Local Port: " + nPort);
			is = s.getInputStream();
			os = s.getOutputStream();

			while (true) {

				szStr = getCommand();
				sendString(os, szStr);
				os.flush();
				Thread.currentThread().yield();
				if (szStr.equals("quit"))
					break;

				szStr = recvString(is);

				System.out.println(szStr);
			}

		} catch (Exception ex) {
			// System.out.println(ex.toString());
			throw ex;
		} finally {
			try {
				if (is != null)
					is.close();
				if (os != null)
					os.close();
				if (s != null)
					s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void main1(String args[]) {
		System.out.println("* Socket Mail Server *");

		Socket s = null;

		try {
			s = new Socket("localhost", 4555);
		} catch (Exception ex) {
			System.out.println(ex.toString());
			System.exit(0);
		}

		int nPort = s.getLocalPort();
		System.out.println("Local Port: " + nPort);

		InputStream is;
		OutputStream os;

		try {
			is = s.getInputStream();
			os = s.getOutputStream();

			String szStr;
			while (true) {
				szStr = getKbdStringNew();

				sendString(os, szStr);

				os.flush();

				if (szStr.equals("quit"))
					break;

				szStr = recvString(is);

				System.out.println(szStr);
			}

			is.close();
			os.close();
			s.close();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

// ============================================
// sendString
// ============================================
	void sendString(OutputStream os, String s) throws IOException {
		for (int i = 0; i < s.length(); i++) {
			os.write((byte) s.charAt(i));
		}
		os.write('\n');
		os.flush();
	}

// ============================================
// recvString
// ============================================
	String recvString(InputStream is) throws IOException {
		String szBuf = "";
		int ch = is.read();

		while (ch >= 0 && ch != '\n') {
			szBuf += (char) ch;
			ch = is.read();
		}
		return szBuf;
	}

// ============================================
// getKbdString
// ============================================
	public String getKbdString() {
		byte bKbd[] = new byte[256];
		int iCnt = 0;
		String szStr = "";

		try {
			iCnt = System.in.read(bKbd);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

		szStr = new String(bKbd, 0, iCnt);
		szStr = szStr.trim();
		return szStr;
	}

	public String getKbdStringNew() {
		byte[] bKbd = new byte[256];
		int iCnt = 0;
		String szStr = "";

		try {
			bKbd = new byte[System.in.available()];
			iCnt = System.in.read(bKbd);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

		// szStr = new String(bKbd, 0, iCnt);
		szStr = new String(bKbd);
		szStr = szStr.trim();
		return szStr;
	}

//============================================
//getKbdString
//============================================
	public String getCommand() {

		if (szStr.indexOf("Login id:") != -1)
			return rootLogin;
		if (szStr.indexOf("Password:") != -1)
			return rootPassword;

		if (szStr.indexOf("Welcome root. HELP for a list of commands") != -1)
			return "adduser " + addUserLogin + " " + addUserPassword;
		if (szStr.indexOf("Welcome root") != -1)
			return "adduser " + addUserLogin + " " + addUserPassword;

		if (szStr.indexOf("User " + addUserLogin + " added") != -1)
			return "quit";

		if (szStr.indexOf("User " + addUserLogin + " already exists") != -1)
			return "quit";

		String result = "\n";
		return result;
	}

}
