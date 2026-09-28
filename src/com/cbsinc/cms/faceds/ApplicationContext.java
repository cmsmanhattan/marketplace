package com.cbsinc.cms.faceds;

/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code.
 * You can not use it and you cannot change without written permission from Konstantin Grabko
 * Email: konstantin.grabko@yahoo.com or konstantin.grabko@gmail.com
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
import jakarta.servlet.ServletContext;

/**
 * Holds the ServletContext for code that has no request at hand.
 */
public interface ApplicationContext {

	/**
	 * @return the stored ServletContext
	 */
	public ServletContext getServletContext();

	/**
	 * Stores the ServletContext at start-up.
	 */
	public void setServletContext(ServletContext applicationContext);

}
