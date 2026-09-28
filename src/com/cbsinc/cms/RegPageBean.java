package com.cbsinc.cms;

import java.io.Serializable;

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

public class RegPageBean implements Serializable {
	/**
	 *
	 */
	transient private static final long serialVersionUID = 784019315635817236L;
	private String sample = "Start value";

	// Access sample property
	public String getSample() {
		return sample;
	}

	// Access sample property
	public void setSample(String newValue) {
		if (newValue != null) {
			sample = newValue;
		}
	}
}
