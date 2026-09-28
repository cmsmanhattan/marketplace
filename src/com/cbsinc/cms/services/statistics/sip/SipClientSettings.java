package com.cbsinc.cms.services.statistics.sip;

import java.io.IOException;
import java.util.HashMap;

import com.cbsinc.cms.AuthorizationPageBean;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet Class
 *
 * @web.servlet name="sipclientsettings" display-name="Name for
 *              sipclientsettings" description="Description for
 *              sipclientsettings"
 * @web.servlet-mapping url-pattern="/sipclientsettings"
 * @web.servlet-init-param name="A parameter" value="A value"
 */
public class SipClientSettings extends HttpServlet {

	public SipClientSettings() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub text/plain")
		resp.setContentType("text/plain");
		resp.setHeader("Cache-Control", "no-cache");
		// resp.setCharacterEncoding("UTF-8");
		HashMap hashMap = null;
		ServletContext servletContext = getServletContext();
		hashMap = (HashMap) servletContext.getAttribute("userlist");
		if (hashMap == null) {
			hashMap = new HashMap();
			servletContext.setAttribute("userlist", hashMap);
		}

		HttpSession session = req.getSession();
		AuthorizationPageBean AuthorizationPageBeanId = (AuthorizationPageBean) session
				.getAttribute("authorizationPageBeanId");
		// String full_name = AuthorizationPageBeanId.getStrFirstName() + " " +
		// AuthorizationPageBeanId.getStrLastName() ;
		String ipAdress = req.getRemoteAddr();
		String user = AuthorizationPageBeanId.getStrLogin();

		resp.getWriter().write("via_addr=" + ipAdress + "\n");
		// via_addr=213.172.2.2
		resp.getWriter().write("host_port=80" + "\n");
		// host_port=80
		resp.getWriter().write("transport_protocols=udp tcp" + "\n");
		// transport_protocols=udp tcp
		resp.getWriter().write("#outbound_proxy=127.0.0.2:5060" + "\n");
		// #outbound_proxy=127.0.0.2:5060
		resp.getWriter().write("from_url=\"" + user + "\" <sip:" + user + "@" + ipAdress + ":80>" + "\n");
		hashMap.put(user, "\"" + user + "\" <sip:" + user + "@" + ipAdress + ":80>\n");
		// from_url="Alice" <sip:alice@213.172.2.2:80>
		resp.getWriter().write("username=" + user + "\n");
		// username=alice
		resp.getWriter().write("realm=gvidons.net" + "\n");
		// realm=gvidons.net
		resp.getWriter().write("passwd=pippo" + "\n");
		// passwd=pippo
		resp.getWriter().write("debug_level=8" + "\n");
		// debug_level=8
		resp.getWriter().write("log_path=log" + "\n");
		// log_path=log
		resp.getWriter().write("max_logsize=2000" + "\n");
		// max_logsize=2000
		// resp.getWriter().write("contacts_file=config/contacts.lst"+ "\n") ;
		resp.getWriter().write("contacts_file=" + req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + "/sipclientcontact" + "\n");
		// contacts_file=config/contacts.lst
		// #do_register=yes
		// #do_unregister=yes
		// #do_unregister_all=yes
		resp.getWriter().write("keepalive_time=8000" + "\n");
		// keepalive_time=8000

		// #call_to=sip:127.0.0.9:5090
		// #accept_time=0
		// #hangup_time=20
		// #redirect_to=sip:127.0.0.9:5090
		// #no_offer=yes
		// #transfer_to=sip:127.0.0.9:5090
		// #transfer_time=10
		// #re_invite_time=5

		// #recv_only=yes
		// #send_only=yes
		// #send_tone=yes
		// #send_file=yes

		// #audio=yes
		resp.getWriter().write("audio_port=3000" + "\n");
		// audio_port=3000
		// #audio_avp=0
		// #audio_codec=PCMU
		// #audio_sample_rate=8000
		// #audio_sample_size=1
		// #audio_frame_size=500

		// #video=yes
		resp.getWriter().write("video_port=3002" + "\n");
		// video_port=3002
		// #video_avp=101

		// #use_jmf=yes
		// #use_rat=yes
		// #use_vic=yes
		// #bin_rat="c:\program files\mbone\rat"
		// #bin_vic="c:\program files\mbone\vic"
		resp.getWriter().write("bin_rat=rat" + "\n");
		// bin_rat=rat
		resp.getWriter().write("bin_vic=vic" + "\n");
		// bin_vic=vic

	}

}
