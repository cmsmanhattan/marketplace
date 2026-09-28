package com.cbsinc.cms;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Provider;
import java.security.Security;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

public class CheckPaymentEProcessingNetwork implements java.io.Serializable {

	private static final long serialVersionUID = 8160271389486247546L;

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

	static private Logger log = Logger.getLogger(CheckPaymentResult.class);

	private URL url1;
	transient ResourceBundle setupResources = null;
	transient long delay = 60000;
	String strUrlServer = "https://www.eProcessingNetwork.Com/cgi-bin/tdbe/transact.pl";

	public CheckPaymentEProcessingNetwork() {

		if (setupResources == null)
			setupResources = PropertyResourceBundle.getBundle("appconfig");
		delay = Long.parseLong(setupResources.getString("checkpay.enp").trim());

		try {
			url1 = new URL(strUrlServer);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		java.util.Timer timer = new java.util.Timer();
		timer.scheduleAtFixedRate(t, 0, delay);
	}

	transient java.util.TimerTask t = new java.util.TimerTask() {
		public void run() {
			OrderBank objOrderBank = getOrder();
			if (objOrderBank != null && objOrderBank.getOrderID() != null) {
				System.out.println("Order : " + objOrderBank.getOrderID());
				System.out.println("Send  request to " + strUrlServer + " pay gateway: "
						+ new Date(scheduledExecutionTime()).toString());
				CheckOrder(objOrderBank);
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
			log.error(query, ex);
			return null;
		} catch (Exception ex) {
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
			}
		}

		return objOrderBank;
	}

	public void CheckOrder(OrderBank objOrderBank) {

		try {
			// URL url1 = new URL ("http://pgate.grabko.com:88");
			InputStreamReader inputstreamreader = new InputStreamReader(url1.openStream());
			String request = "ShopOrderNumber=" + objOrderBank.getOrderID()
					+ "&Shop_ID=84473&login=gvidon&password=231003&SUCCESS=2&STARTDAY="
					+ objOrderBank.getBeginDataDay() + "&STARTMONTH=" + objOrderBank.getBeginDataMonth()
					+ "&STARTYEAR=" + objOrderBank.getBeginDataYear() + "&ENDDAY=" + objOrderBank.getEndDataDay()
					+ "&ENDMONTH=" + objOrderBank.getEndDataMonth() + "&ENDYEAR=" + objOrderBank.getEndDataYear()
					+ "&MEANTYPE=0&PAYMENTTYPE=0&FORMAT=1&ZIPFLAG=0&ENGLISH=0&HEADER1=0&Delimiter=;&RowDelimiter=13,10&S_FIELDS=ORDERNUMBER;RESPONSE_CODE;RECOMMENDATION;DATE;TOTAL";
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			// bufferedreader =
			// postData("http://secure.assist.ru/results/results_long.cfm",
			// request);
			bufferedreader = postData(strUrlServer, request);
			String nextLine = "";
			while ((nextLine = bufferedreader.readLine()) != null) {
				String tmp = java.net.URLDecoder.decode(nextLine, "UTF-8");
				String[] result = tmp.split(";");
				System.err.println(tmp);
				if (tmp.indexOf(";") != -1) {
					parserRequest(result[0], result[1], result[2]);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

		System.out.println("Answer from pay gateway: " + new Date(System.currentTimeMillis()).toString());
	}

	public void parserRequest(String iStrNumerOrder, String iStrRezult, String iStrDecsription) {
		// final static int INPROCESS = 1 ;
		// final static int SUCCESS = 2 ;
		// final static int UNSUCCESS = 3 ;

		System.out.println("Order: " + iStrNumerOrder + " Rezalt: " + iStrRezult + " " + iStrDecsription);
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String query = "";
		try {
			if (Long.parseLong(iStrRezult) == PayStatus.SUCCESS) {
				endAddmoney(Adp, iStrNumerOrder, iStrRezult, iStrRezult);
			} else if (Long.parseLong(iStrRezult) == PayStatus.UNSUCCESS) {
				query = "UPDATE account_hist SET complete = ? , active = ? , rezult_cd = ? , decsription =  ?  WHERE  id =  "
						+ iStrNumerOrder + "";
				HashMap args = new HashMap();
				args.put("complete", false);
				args.put("active", false);
				args.put("rezult_cd", iStrRezult);
				args.put("decsription", iStrDecsription);
				Adp.executeUpdateWithArgs(query, args);
			}

			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
			return;
		} finally {
			Adp.close();
		}

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
				log.error(cfe);
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
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
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
		String orderId = "";

		try {
			query = "select add_amount , old_amount ,  date_input , rate , date_end , user_id , order_id from  account_hist where  id =  "
					+ iStrAccountHistoryId;
			Adp.beginTransaction();
			Adp.executeQuery(query);
			if (Adp.rows().size() > 0) {
				addAmount = new Double((String) Adp.getValueAt(0, 0)).doubleValue();
				oldAmount = new Double((String) Adp.getValueAt(0, 1)).doubleValue();
				dateInput = (String) Adp.getValueAt(0, 2);
				rate = new Double((String) Adp.getValueAt(0, 3)).doubleValue();
				userId = (String) Adp.getValueAt(0, 5);
				orderId = (String) Adp.getValueAt(0, 6);
				totalAmount = oldAmount + addAmount;
			}

			query = "UPDATE account_hist SET complete = ? , active = ? , rezult_cd = ?  WHERE  id =  "
					+ iStrAccountHistoryId + "";
			HashMap args = new HashMap();
			args.put("complete", true);
			args.put("active", false);
			args.put("rezult_cd", iStrRezult);
			Adp.executeUpdateWithArgs(query, args);

			totalAmount = amount + (addAmount * rate);
			query = "UPDATE account SET amount = ? , curr = ? , date_input = ? WHERE  user_id = " + userId;
			args = new HashMap();
			args.put("amount", Double.valueOf(totalAmount));
			args.put("curr", (long) rate);
			args.put("date_input", new java.util.Date());
			Adp.executeUpdateWithArgs(query, args);

			if (totalAmount >= 0) {
				query = "update orders  set paystatus_id = " + PayStatus.SUCCESS + " where order_id = " + orderId;
				Adp.executeUpdate(query);
			}

			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
			return;
		} finally {
			Adp.close();
		}
	}

}
