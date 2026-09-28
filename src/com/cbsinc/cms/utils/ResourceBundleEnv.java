package com.cbsinc.cms.utils;

import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Set;

public class ResourceBundleEnv extends ResourceBundle {

	@Override
	protected Object handleGetObject(String key) {
		// TODO Auto-generated method stub
		return this.handleGetObject(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		// TODO Auto-generated method stub
		return this.getKeys();
	}


	public String getStringEnv(String key) {
	    String retrun = this.getString(key);
		if (retrun == null || retrun.trim().isEmpty()) {
			return null;
		}
		retrun = retrun.trim();
	    Set<String> keys = System.getenv().keySet() ;
	    for (String envKey : keys) retrun = retrun.replaceAll("${" + envKey.toUpperCase() + "}", System.getenv(envKey.toUpperCase()));
        return this.getString( retrun);
    }

}
