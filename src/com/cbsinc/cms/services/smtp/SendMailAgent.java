package com.cbsinc.cms.services.smtp;

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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMailAgent {
	static String host = "smtp.mail.ru";

	static int port = 25;

	static String from = "grabko@mail.ru";

	static String mailto = "grabko@mail.ru";

	static int delay = 3000;

	static ResourceBundle resources;

	static java.util.Vector messbuffer = new java.util.Vector();

	static java.util.Timer timer = new java.util.Timer();

	// static Session session = null;

	public SendMailAgent() {

		resources = PropertyResourceBundle.getBundle("appconfig");
		/*
		 * sendmailagent.host=mail.qbix.ru sendmailagent.port=25
		 * sendmailagent.mailfrom=Dmitry.Gurov@qbix.ru sendmailagent.delay=3000
		 * sendxmppagent.1=В ОЧЕРЕД�? sendxmppagent.2=П�?СЕМ sendxmppagent.3=ОТСЫЛК�?
		 * П�?СМА : к
		 */
		host = resources.getString("sendmailagent.host");
		port = new Long(resources.getString("sendmailagent.port")).intValue();
		from = resources.getString("sendmailagent.mailfrom");
		delay = new Long(resources.getString("sendmailagent.delay")).intValue();
		mailto = resources.getString("sendmailagent.mailto");
		timer.schedule(t, 0, delay);
	}

	static java.util.TimerTask t = new java.util.TimerTask() {
		public void run() {
			sendPoolMessage();
		}
	};

	private static boolean sendPoolMessage() {
		Properties p = new Properties();
		p.put("mail.smtp.host", host);
		Session session = null;
		session = Session.getDefaultInstance(p, null);

		Vector buffer;
		synchronized (SendMailAgent.messbuffer) {
			buffer = SendMailAgent.messbuffer;
			SendMailAgent.messbuffer = new Vector();
		}
		if (buffer.size() == 0)
			return false;

		if (buffer.size() > 0) {
			System.out.println("----------------\n " + resources.getString("sendxmppagent.1") + buffer.size() + " "
					+ resources.getString("sendxmppagent.2"));
		}
		for (int i = 0; buffer.size() > i; i++) {
			String query = (String) buffer.get(i);
			String[] queryElement = query.split(";");

			// create a message
			String subject = queryElement[1];
			String to = queryElement[0];
			String msgText1 = queryElement[2] + "\n";
			String filename = queryElement[3];
			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(from));
				InternetAddress[] address = { new InternetAddress(to) };
				msg.setRecipients(Message.RecipientType.TO, address);
				msg.setSubject(subject, "UTF-8");

				// create and fill the first message part
				MimeBodyPart mbp1 = new MimeBodyPart();
				mbp1.setText(msgText1);

				// create the second message part
				MimeBodyPart mbp2 = new MimeBodyPart();

				// attach the file to the message

				FileDataSource fds = new FileDataSource(filename);
				mbp2.setDataHandler(new DataHandler(fds));
				mbp2.setFileName(fds.getName());

				// create the Multipart and add its parts to it
				Multipart mp = new MimeMultipart();
				mp.addBodyPart(mbp1);
				mp.addBodyPart(mbp2);

				// add the Multipart to the message
				msg.setContent(mp);

				// set the Date: header
				msg.setSentDate(new Date());
				msg.saveChanges();
				// send the message
				// Transport t = session.getTransport("smtp");
				Transport.send(msg);

				System.out.println(
						"" + resources.getString("sendxmppagent.3") + queryElement[0] + ", " + queryElement[3]);
			} catch (Throwable th) {
				t.cancel();
				timer = new java.util.Timer();
				timer.schedule(t, 0, delay);
				th.printStackTrace();
			}

		}

		return true;
	}

	public static boolean putMessageInPool(String to, String subject, String body, String filename) {
		if (to != null)
			if (to.length() > 0)
				mailto = to;
		// || mailto.length() == 0)
		String query = "";
		query = "" + mailto + ";" + subject + ";" + body + ";" + filename;
		synchronized (messbuffer) {
			messbuffer.add(query);
		}
		return true;
	}

	public static boolean putMessageInPool(String to, String subject, String pathmessage, String filename,
			HashMap fields) {
		if (to != null)
			if (to.length() > 0)
				mailto = to;
		// || mailto.length() == 0)

		String query = "";
		query = "" + mailto + ";" + subject + ";" + getBody(pathmessage, fields) + ";" + filename;
		synchronized (messbuffer) {
			messbuffer.add(query);
		}
		return true;
	}

	public static String getBody(String filename, HashMap fields) {
		String body = "";
		byte[] b = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filename);
			b = new byte[fis.available()];
			fis.read(b);
			body = new String(b);

			Iterator iterator = fields.keySet().iterator();
			String key = "";
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				body = body.replaceAll(key, (String) fields.get(key));
			}

			// body.replaceAll()
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return body;
	}

	public static void main(String[] args) {
		// SendMailAgent sendMailAgent = new SendMailAgent();
		// System.out.println("rezalt: " +
		// sendMailAgent.putMessageInPool("grabko@mail.ru" ,"test","send file" ,
		// "C://111.txt") ) ;
		System.out.println("rezalt: " + SendMailAgent.putMessageInPool(null, "test", "send file", "C://111.txt"));
		SendMailAgent.sendPoolMessage();
	}

}
