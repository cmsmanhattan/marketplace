package com.cbsinc.cms.utils;

public class OSUtil {

	 public static boolean isWindows(){
		 String osName = System.getProperty("os.name").toLowerCase();
	        if (osName.contains("win")) return true;
			return false;
	    }

	 public static boolean isMac(){
	        String osName = System.getProperty("os.name").toLowerCase();
	        if (osName.contains("mac")) return true;
			return false;
	    }

	 public static boolean isUnix(){
	        String osName = System.getProperty("os.name").toLowerCase();
	        if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) return true;
			return false;
	    }

}
