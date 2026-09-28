package com.cbsinc.cms;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.cbsinc.cms.controllers.ServiceLocator;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;

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
public class UpdateDomains implements java.io.Serializable {

	private static final long serialVersionUID = 8160271689486247546L;

	static private Logger log = Logger.getLogger(UpdateDomains.class);

	transient ResourceBundle setupResources = null;

	long delay = 60000;

	public UpdateDomains() {
		createDomainsInServerXmlFile();
	}

	transient java.util.TimerTask t = new java.util.TimerTask() {
		public void run() {
			AuthorizationPageFaced authorizationPageFaced = null;
			try {
				authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			authorizationPageFaced.addAliasesInFile();

		}
	};

	public static void main(String[] args) {

	}

	public void createDomainsInServerXmlFile() {
		if (setupResources == null)
			setupResources = PropertyResourceBundle.getBundle("appconfig");
		delay = Long.parseLong(setupResources.getString("createdomains.delay").trim());
		java.util.Timer timer = new java.util.Timer();
		timer.scheduleAtFixedRate(t, 0, delay);
	}

}
