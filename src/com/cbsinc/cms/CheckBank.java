package com.cbsinc.cms;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.security.Provider;
import java.security.Security;
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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

public class CheckBank implements java.io.Serializable {

	private static final long serialVersionUID = -3492879607551630598L;

	transient static private Logger log = Logger.getLogger(CheckBank.class);

	public CheckBank() {
		java.util.Timer timer = new java.util.Timer();
		timer.scheduleAtFixedRate(t, 0, 60000);
	}

	transient java.util.TimerTask t = new java.util.TimerTask() {
		public void run() {
			OrderBank objOrderBank = getOrder();
			if (objOrderBank != null) {
				System.out.println("Order : " + objOrderBank.getOrderID());
				System.out.println("Work Time: " + scheduledExecutionTime());
				System.out.println("System Time: " + System.currentTimeMillis());
				System.out.println("Rezalt: " + CheckOrder(objOrderBank));
			}
		}
	};

	public OrderBank getOrder() {
		String query = "";
		OrderBank objOrderBank = null;
		QueryManager Adp = new QueryManager();
		try {
			query = "SELECT id, date_input FROM account_hist WHERE complete = false and active = true ORDER BY id ASC LIMIT 1  OFFSET 0";
			Adp.executeQuery(query);
		} catch (SQLException ex) {
			log.error(ex);
			return null;
		} finally {
			Adp.close();
		}

		if (Adp.rows().size() > 0) {
			try {
				objOrderBank = new OrderBank();
				objOrderBank.setOrderID((String) Adp.getValueAt(0, 0));
				objOrderBank.setBeginData((String) Adp.getValueAt(0, 1));
			} catch (Exception ex) {
				log.error(ex);
				return null;
			} finally {
				Adp.close();
			}
		}

		Adp.close();
		return objOrderBank;
	}

	public String CheckOrder(OrderBank objOrderBank) {

		try {
			URL url1 = new URL("http://www.assist.ru");
			InputStreamReader inputstreamreader = new InputStreamReader(url1.openStream());
			String request = "ShopOrderNumber=" + objOrderBank.getOrderID()
					+ "&Shop_ID=84473&login=gvidon&password=231003&SUCCESS=2&STARTDAY="
					+ objOrderBank.getBeginDataDay() + "&STARTMONTH=" + objOrderBank.getBeginDataMonth()
					+ "&STARTYEAR=" + objOrderBank.getBeginDataYear() + "&ENDDAY=" + objOrderBank.getEndDataDay()
					+ "&ENDMONTH=" + objOrderBank.getEndDataMonth() + "&ENDYEAR=" + objOrderBank.getEndDataYear()
					+ "&MEANTYPE=0&PAYMENTTYPE=0&FORMAT=1&ZIPFLAG=0&ENGLISH=0&HEADER1=0&Delimiter=;&RowDelimiter=13,10&S_FIELDS=ORDERNUMBER;RESPONSE_CODE;RECOMMENDATION;DATE;TOTAL";
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			bufferedreader = postData("http://secure.assist.ru/results/results_long.cfm", request);
			String nextLine = "";
			while ((nextLine = bufferedreader.readLine()) != null) {
				String tmp = java.net.URLDecoder.decode(nextLine, "UTF-8");
				System.err.println(tmp);
				if (tmp.indexOf(";") != -1) {
					parserRequest(objOrderBank.getOrderID(), tokenize(tmp, ";")[1], tokenize(tmp, ";")[2]);
					return tokenize(tmp, ";")[1];
				}
			}
		} catch (Exception ex) {
			log.error(ex);
			System.out.println(ex.toString());
		}
		return null;
	}

	public String CheckOrder1(OrderBank objOrderBank) {
		try {
			URL url1 = new URL("http://www.assist.ru");
			InputStreamReader inputstreamreader = new InputStreamReader(url1.openStream());
			String request = "ShopOrderNumber=%&Shop_ID=84473&login=gvidon&password=231003&SUCCESS=2&STARTDAY="
					+ objOrderBank.getBeginDataDay() + "&STARTMONTH=" + objOrderBank.getBeginDataMonth()
					+ "&STARTYEAR=" + objOrderBank.getBeginDataYear() + "&ENDDAY=" + objOrderBank.getEndDataDay()
					+ "&ENDMONTH=" + objOrderBank.getEndDataMonth() + "&ENDYEAR=" + objOrderBank.getEndDataYear()
					+ "&MEANTYPE=0&PAYMENTTYPE=0&FORMAT=1&ZIPFLAG=0&ENGLISH=1&HEADER1=0&Delimiter=;&RowDelimiter=13,10&S_FIELDS=ORDERNUMBER;RESPONSE_CODE;RECOMMENDATION;DATE;TOTAL";
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			bufferedreader = postData("http://secure.assist.ru/results/results_long.cfm", request);
			LinkedList linkedList = new LinkedList();
			String nextLine = "";
			while ((nextLine = bufferedreader.readLine()) != null) {
				String tmp = java.net.URLDecoder.decode(nextLine, "UTF-8");
				if (tmp.indexOf("ERROR") != -1)
					return null;
				System.err.println(tmp);
				if (tmp.length() > 0)
					linkedList.add(tmp);
			}

			for (int i = 0; linkedList.size() > i; i++) {
				String tmp = (String) linkedList.get(i);
				if (tmp.indexOf(";") != -1) {
					parserRequest(objOrderBank.getOrderID(), tokenize(tmp, ";")[1], tokenize(tmp, ";")[2]);
					return tokenize(tmp, ";")[1];
				}
			}

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return null;
	}

	synchronized public void parserRequest(String iStrNumerOrder, String iStrRezult, String iStrDecsription) {
		System.out.println("Order: " + iStrNumerOrder + " Rezalt: " + iStrRezult + " " + iStrDecsription);
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String query = "";
		try {
			if (iStrRezult.compareTo("AS000") == 0) {
				endAddmoney(Adp, iStrNumerOrder, iStrRezult, iStrRezult);
			}

			if (iStrRezult.compareTo("AS001") == 0) {
				endAddmoney(Adp, iStrNumerOrder, iStrRezult, iStrRezult);
			}
			if (iStrRezult.compareTo("AS000") != 0 && iStrRezult.compareTo("AS001") != 0
					&& iStrRezult.compareTo("AS300") != 0) {
				query = "UPDATE account_hist SET complete = ? , active = ? , rezult_cd = ? , decsription =  ?  WHERE  id =  "
						+ iStrNumerOrder + "";
				HashMap args = new HashMap();
				args.put("complete", false);
				args.put("active", false);
				args.put("rezult_cd", iStrRezult);
				args.put("decsription", iStrDecsription);
				Adp.executeUpdateWithArgs(query, args);
				Adp.commit();
			}
		} catch (SQLException ex) {
			log.error(ex);
			Adp.rollback();
			return;
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
			return;
		} finally {
			Adp.close();
		}

		return;
	}

	public String[] tokenize(String s, String d) {
		Vector vector = new Vector();
		for (StringTokenizer stringtokenizer = new StringTokenizer(s, d); stringtokenizer.hasMoreTokens(); vector
				.addElement(stringtokenizer.nextToken()))
			;

		String as[] = new String[vector.size()];
		for (int i = 0; i < as.length; i++)
			as[i] = (String) vector.elementAt(i);
		return as;
	}

	public BufferedReader postData(String s, String s1) {
		BufferedReader bufferedreader = null;
		try {
			System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
			try {
				Class clsFactory = Class.forName("com.sun.net.ssl.internal.ssl.Provider");
				if ((null != clsFactory) && (null == Security.getProvider("SunJSSE")))
					Security.addProvider((Provider) clsFactory.newInstance());
			} catch (ClassNotFoundException cfe) {
				throw new Exception("Unable to load the JSSE SSL stream handler.  Check classpath." + cfe.toString());
			}

			URL url1 = new URL(s);
			java.net.HttpURLConnection urlconnection = (java.net.HttpURLConnection) url1.openConnection();
			urlconnection.setUseCaches(false);
			urlconnection.setDoOutput(true);
			ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(s1.length() * 2);
			PrintWriter printwriter = new PrintWriter(bytearrayoutputstream, true);
			printwriter.print(s1);
			printwriter.flush();
			urlconnection.setRequestProperty("Content-Length", String.valueOf(bytearrayoutputstream.size()));
			urlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			bytearrayoutputstream.writeTo(urlconnection.getOutputStream());
			bufferedreader = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return bufferedreader;
	}

	public static void main(String[] args) {

	}

	void endAddmoney(QueryManager Adp, String iStrAccountHistoryId, String iStrRezult, String iStrDecsription)
			throws Exception {

		double addAmount = 0;
		double oldAmount = 0;
		double totalAmount = 0;
		// String date_input = "" ;
		double amount = 0;
		double rate = 0;
		String dateInput = null;
		String userId = "";
		String query = "";

		query = "select add_amount , old_amount ,  date_input , rate , date_end , user_id  from  account_hist where  id =  "
				+ iStrAccountHistoryId;
		Adp.executeQuery(query);
		if (Adp.rows().size() > 0) {
			addAmount = new Double((String) Adp.getValueAt(0, 0)).doubleValue();
			oldAmount = new Double((String) Adp.getValueAt(0, 1)).doubleValue();
			dateInput = (String) Adp.getValueAt(0, 2);
			rate = new Double((String) Adp.getValueAt(0, 3)).doubleValue();
			userId = (String) Adp.getValueAt(0, 5);
			totalAmount = oldAmount + addAmount;
		}

		query = "UPDATE account_hist SET complete = true  , active = false , rezult_cd = '" + iStrRezult
				+ "'  WHERE  id = " + iStrAccountHistoryId;
		Adp.executeUpdate(query);

		totalAmount = amount + (addAmount * rate);
		query = "UPDATE account SET amount = " + totalAmount + " , curr = " + rate + " , date_input = '" + dateInput
				+ "' WHERE  user_id = " + userId;
		Adp.executeUpdate(query);
	}

}
