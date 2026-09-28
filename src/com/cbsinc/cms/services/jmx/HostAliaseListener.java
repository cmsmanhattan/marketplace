package com.cbsinc.cms.services.jmx;

import javax.management.Notification;
import javax.management.NotificationListener;

public class HostAliaseListener implements NotificationListener {
	public void handleNotification(Notification notification, Object handback) {
		System.out.println("\nReceived notification: " + notification);
	}
}
