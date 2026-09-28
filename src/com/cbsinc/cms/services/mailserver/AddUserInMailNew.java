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
import java.net.InetAddress;
import java.net.Socket;

import com.cbsinc.cms.services.net.FingerClient;

public class AddUserInMailNew {

	private String rootPassword = "root";
	private String rootLogin = "root";
	private String addUserLogin = "root";
	private String addUserPassword = "root";

	String szStr = "";

	public void exec(String rootLogin, String rootPassword, String addUserLogin, String addUserPassword, String host) {

		this.rootLogin = rootLogin;
		this.rootPassword = rootPassword;
		this.addUserLogin = addUserLogin;
		this.addUserPassword = addUserPassword;

		System.out.println("* Socket Mail Server *");

		Socket s = null;

		try {
			s = new Socket(host, 4555);
		} catch (Exception ex) {
			// FIX: this was System.exit(0), inside an ordinary instance method that
			// the application calls to provision a mailbox. A mail server that was
			// down therefore shut down the entire servlet container - and with
			// status 0, so the supervisor treated it as a clean, intended stop and
			// did not restart it. Report the failure to the caller instead.
			throw new IllegalStateException("Cannot reach the mail server at " + host + ":4555", ex);
		}

		// FIX: s was dereferenced here while still possibly null, because the catch
		// above did not stop execution on the paths where exit() was unreachable.
		int nPort = s.getLocalPort();
		System.out.println("Local Port: " + nPort);

		InputStream is = null;
		OutputStream os = null;

		try {
			is = s.getInputStream();
			os = s.getOutputStream();

			while (true) {
				szStr = getCommand();

				sendString(os, szStr);

				os.flush();

				if (szStr.equals("quit"))
					break;

				szStr = recvString(is);

				System.out.println(szStr);
			}

		} catch (Exception ex) {
			System.out.println(ex.toString());
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

		FingerClient finger = new FingerClient();
		// We want to timeout if a response takes longer than 60 seconds
		finger.setDefaultTimeout(60000);
		try {
			InetAddress address = InetAddress.getByName("192.168.1.135");
			finger.connect(address, 4555);
			// finger.
			// String[] status = finger.query(false, handle + domain).split(" ") ;
			// DomainState domainState = new DomainState();
			// domainState.setDomain(domain);
			// domainState.setStatus(AuthorizationPageBeanId.getLocalization().getString(status[1].trim()));
			// domainState.setFree(status[1].trim().startsWith("free"));
			// domainList.add(domainState);
		} catch (IOException e) {
			// AuthorizationPageBeanId.setStrMessage(e.getLocalizedMessage());
			System.err.println("Error I/O exception: " + e.getMessage());

		} finally {
			try {
				finger.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void main(String args[]) {
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
				szStr = getKbdString();

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

		if (szStr.indexOf("User " + addUserLogin + " added") != -1)
			return "quit";

		String result = "\n";
		return result;
	}

}
