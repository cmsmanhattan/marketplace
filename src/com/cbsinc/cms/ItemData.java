package com.cbsinc.cms;

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

public class ItemData implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String value;
	String contentType;
	int size;
	boolean isFile;

	ItemData(String name, String value, String contentType, int size, boolean isFile) {
		this.name = name;
		this.value = value;
		this.contentType = contentType;
		this.size = size;
		this.isFile = isFile;
	}

	public ItemData() {
		// TODO Auto-generated constructor stub
	}
}
