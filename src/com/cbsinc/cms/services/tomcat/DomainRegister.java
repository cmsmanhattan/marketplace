package com.cbsinc.cms.services.tomcat;

import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLException;

//import jakarta.servlet.ServletContextEvent;

import org.w3c.dom.Document;

import com.cbsinc.cms.QueryManager;
import com.cbsinc.cms.controllers.ServiceLocator;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;

public class DomainRegister {

	private static final Logger log = Logger.getLogger(DomainRegister.class);

	String authId = "";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		DomainRegister r = new DomainRegister();
//		String auth = r.getAuth() ;
//		r.setDomainAliases("irr.bz" , "www1.kostya A 0 78.37.191.193 86400" + "\n"  +	"web A 0 78.37.191.193 86400" + "\n"  +	"www2 A 0 78.37.191.193 86400\n" ,auth );
//		System.out.println(r.getPostCommand(auth));
////		r.addAliases() ;

		AuthorizationPageFaced authorizationPageFaced = null;
		try {
			authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		authorizationPageFaced.postToDNSServerAliases();
	}

	public String getSiteIdByHost(String host) {

		String siteId = "2";
		String login = "admin";
		QueryManager Adp = null;
		QueryManager Adp1 = null;
		String query = "select site_id ,login from tuser where levelup_cd = 2 ";
		// where levelup_cd = 2

		try {
			DomainRegister r = new DomainRegister();
			// r.getSiteIdByHost("");
			String auth = r.getAuth();
			// r.setDomain("irr.bz" , "wew A 0 78.37.191.193 86400" ,auth );
			// System.out.println(r.getPostCommand(auth));

			Adp = new QueryManager();
			// Adp1 = new QueryManager();
			Adp.executeQuery(query);
			for (int i = 0; Adp.rows().size() > i; i++) {
				siteId = Adp.getValueAt(i, 0);
				login = Adp.getValueAt(i, 1);
				String queryUpdate = "update site set host = '" + "www." + login + ".irr.bz" + "' where site_id =  "
						+ siteId;
				System.out.println(" site " + login + " id " + siteId);
				// Adp1.executeUpdate(queryUpdate);
			}
		} catch (SQLException e) {
			// FIX: this catch was empty, so a failed domain registration looked
			// identical to a successful one from the caller's point of view.
			log.error("Domain registration query failed.", e);
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
			// hdotoodu
			// String data = "username=grabko@mail.ru&password=xvabcvje" ;
			String data = "username=grabko@mail.ru&password=hdotoodu";
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

	public String setDomainAliasesOld(String domain, String zone, String authId) {

		StringBuffer buff = new StringBuffer();
		try {
			// Construct data
			// Send data
			String data = "domain=" + domain + "&zone=" + URLEncoder.encode(zone, "UTF-8") + "&" + authId;
			URL url = new URL("https://dmapi.joker.com/request/dns-zone-put?");
			// URLConnection conn = url.openConnection();
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			// conn.set
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

	public String setDomainAliases(String domain, String zone, String authId) {

		StringBuffer buff = new StringBuffer();
		try {
			// Construct data
			// Send data
			String data = "domain=" + domain + "&zone=" + URLEncoder.encode(zone, "UTF-8") + "&" + authId;
			URL url = new URL("https://dmapi.joker.com/request/dns-zone-put?");
			// URLConnection conn = url.openConnection();
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.setUseCaches(false);
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

	public String getPostCommandOld(String authId) {

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
			int i = 0;
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				buff.append(++i + " " + line + "\n");
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
			// URLConnection conn = url.openConnection();
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.setUseCaches(false);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			int i = 0;
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				buff.append(++i + " " + line + "\n");
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buff.toString();
	}

	public void addAliases() {

		QueryManager Adp = null;
		AddAliase addAliase;
		Document doc;
		// String query = "select host from site" ;
		String query = "select host  from site group by host";

		// String host = "www.siteoneclick.com" ;
		String host = "www.siteforyou.net";
		String aliase = "";

		StringBuffer hostsForDns = new StringBuffer();
		File file = new File("/");
		String serverConfig = System.getProperty("conf");
		if (serverConfig == null || serverConfig.length() == 0) {
			// String dir = System.getProperty("user.dir");
			// dir = dir.substring(0 ,dir.indexOf("bin")) + "conf" +
			// File.separatorChar+"server.xml" ;
			// File f = new File("C:/apache-tomcat-6.0.14/conf/server.xml") ;
			file = new File("C:/apache-tomcat-6.0.14/conf/server.xml");
			// file = new File(dir) ;
		} else {
			file = new File(serverConfig);
		}

		try {

			Adp = new QueryManager();
			if (Adp == null)
				return;
			addAliase = new AddAliase();
			doc = addAliase.loadConfig(file);
			addAliase.RemoveAllAliase(doc, host);

			Adp.executeQuery(query);
			for (int i = 0; Adp.rows().size() > i; i++) {
				aliase = Adp.getValueAt(i, 0);
				int indexof = aliase.indexOf(".irr.bz");
				// if(indexof != -1 && i < 280 && isAnsi(aliase) )
				if (indexof != -1 && isAnsi(aliase)) {
					addAliase.AddAliaseToHost(doc, host, aliase);
					hostsForDns.append(aliase.substring(0, indexof));
					// hosts_for_dns.append(" A 0 78.37.191.193 86400\n");
					hostsForDns.append(" A 0 54.201.41.182 86400\n");
				}
			}
			addAliase.writeXmlFile(doc, file);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(" server config: " + file.getPath());
			e.printStackTrace();
		} finally {
			if (Adp != null) {
				Adp.close();
				DomainRegister r = new DomainRegister();
				// r.getSiteIdByHost("");
				String auth = r.getAuth();
				r.setDomainAliases("irr.bz", hostsForDns.toString(), auth);
				System.out.println(r.getPostCommand(auth));
			}

		}

	}

	public boolean isAnsi(String tmp) {
		if (tmp == null)
			return false;
		tmp = tmp.toLowerCase();
		String IntField = "0123456789qwertyuiopasdfghjklzxcvbnm-_.";
		for (int i = 0; i < tmp.length(); i++) {

			if (IntField.indexOf(tmp.charAt(i)) == -1) {
				if (tmp.charAt(i) != '-' && i != 0)
					return false;
			}
		}
		return true;
	}

}
