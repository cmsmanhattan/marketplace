package com.cbsinc.cms.export.hsql;

import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;

import com.cbsinc.cms.QueryManager;

public class RecoveryHost {

	private static final Logger log = Logger.getLogger(RecoveryHost.class);

	String authId = "";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RecoveryHost r = new RecoveryHost();
		// r.getSiteIdByHost("");
		String auth = r.getAuth();
		r.setDomain("irr.bz", "wew A 0 78.37.191.193 86400", auth);
		System.out.println(r.getPostCommand(auth));
		// System.out.println(r.getAuth());
		// getPostCommand
	}

	public String getSiteIdByHost(String host) {

		String siteId = "2";
		String login = "admin";
		QueryManager Adp = null;
		QueryManager Adp1 = null;
		String query = "select site_id ,login from tuser where levelup_cd = 2 ";
		// where levelup_cd = 2

		try {
			Adp = new QueryManager();
			Adp1 = new QueryManager();
			Adp.executeQuery(query);
			for (int i = 0; Adp.rows().size() > i; i++) {
				siteId = Adp.getValueAt(i, 0);
				login = Adp.getValueAt(i, 1);
				String queryUpdate = "update site set host = '" + "www." + login + ".irr.bz" + "' where site_id =  "
						+ siteId;
				System.out.println(" site " + login + " id " + siteId);
				Adp1.executeUpdate(queryUpdate);
			}
		} catch (SQLException e) {
			// FIX: this catch was empty. A failure part-way through the host
			// recovery loop left some sites updated and some not, and reported
			// success to the caller either way.
			log.error("Host recovery failed; some sites may be left half-updated.", e);
		} finally {
			Adp.close();
		}

		return siteId;
	}

	public String getAuth() {

		StringBuffer buff = new StringBuffer();
		try {
			// Construct data
			// Send data
			// https://dmapi.joker.com/request/login?username=grabko@mail.ru&password=xvabcvje
			String data = "username=grabko@mail.ru&password=xvabcvje";
			URL url = new URL("https://dmapi.joker.com/request/login");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			// AuthId
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String[] array = new String[0];
			while ((line = rd.readLine()) != null) {
				if (line.indexOf("Auth-Sid") != -1) {
					array = line.split(":");
					buff.append("Auth-Sid=" + array[1].trim());
				}
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buff.toString();
	}

	public String setDomain(String domain, String zone, String authId) {

		StringBuffer buff = new StringBuffer();
		try {
			// Construct data
			// Send data
			// https://dmapi.joker.com/request/login?username=grabko@mail.ru&password=xvabcvje
			// String data = "domain=irr.bz&zone=web A 0 78.37.191.193 86400" ;
			String data = "domain=" + domain + "&zone=" + zone + "&" + authId;
			URL url = new URL("https://dmapi.joker.com/request/dns-zone-put");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				buff.append(line + "\n");
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buff.toString();
	}

	public String getPostCommand(String authId) {

		StringBuffer buff = new StringBuffer();
		try {
			// Construct data
			// String data = URLEncoder.encode("key1", "UTF-8") + "=" +
			// URLEncoder.encode("value1", "UTF-8");
			// data += "&" + URLEncoder.encode("key2", "UTF-8") + "=" +
			// URLEncoder.encode("value2", "UTF-8");

			// Send data
			// https://dmapi.joker.com/request/dns-zone-get?domain=irr.bz
			String data = "domain=irr.bz&" + authId;
			URL url = new URL("https://dmapi.joker.com/request/dns-zone-get");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				buff.append(line + "\n");
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buff.toString();
	}

}
