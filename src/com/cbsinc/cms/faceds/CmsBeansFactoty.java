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

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.cbsinc.cms.annotations.utils.PropertiesReadAndWriteSingletonBean;

/**
 * Factory and cache of bean instances registered by id in the properties.
 */
public class CmsBeansFactoty {

	private static CmsBeansFactoty cmsBeansFactoty = new CmsBeansFactoty();
	private static final String propertiesFileName = "beans";

	/**
	 * Cache of singleton instances, keyed by the id used in beans.properties.
	 *
	 * FIX: was a raw Map holding whatever getProperty() returned, i.e. Strings.
	 * It now holds the instantiated beans and is typed.
	 */
	private final Map<String, Object> classes = new ConcurrentHashMap<String, Object>();

	private CmsBeansFactoty() {
	}

	/**
	 * @return the singleton factory
	 */
	public static CmsBeansFactoty getInstance() {
		return cmsBeansFactoty;
	}

	/**
	 * Returns the singleton bean registered under <code>id</code>.
	 *
	 * FIX: three defects in the previous implementation.
	 *
	 * 1. It returned the value of beans.properties directly. That value is the
	 * fully qualified CLASS NAME as a String, not an instance, so every caller
	 * received a String. FrontControllers passes the result to Field.set() for
	 * every @Singleton field, which threw IllegalArgumentException ("Can not set
	 * com.cbsinc.cms.faceds.XxxFaced field to java.lang.String") on every single
	 * request that touched an annotated controller. The name is now resolved to a
	 * real instance with Class.forName(...).getDeclaredConstructor().newInstance().
	 *
	 * 2. lock.unlock() sat in a finally block that ran even when tryLock(50ms)
	 * returned false or threw. A thread that never acquired the lock then raised
	 * IllegalMonitorStateException from the finally block, which replaced
	 * whatever the method was about to return or throw. Under load this is the
	 * common path, not the rare one. The lock is gone entirely: ConcurrentHashMap
	 * .computeIfAbsent already gives atomic, per-key initialisation, which is
	 * exactly what a singleton registry needs and it never blocks the whole map.
	 *
	 * 3. When the 50ms tryLock timed out the method returned null with no error,
	 * so the caller silently injected null and failed later somewhere unrelated.
	 * Unknown ids now raise IOException naming the id and the properties file.
	 *
	 * @param id key in beans.properties
	 * @return the shared instance, never null
	 * @throws IOException if the id is absent or the class cannot be instantiated
	 */
	public Object getBean(String id) throws IOException {
		if (id == null)
			throw new IOException("bean id is null");

		Object cached = classes.get(id);
		if (cached != null)
			return cached;

		String className = lookupClassName(id);
		if (className == null || className.trim().length() == 0)
			throw new IOException("No bean registered under id '" + id + "' in " + propertiesFileName
					+ ".properties. The file is generated at compile time from the @Singleton annotations;"
					+ " check that the annotation processor ran.");

		final String type = className.trim();
		try {
			return classes.computeIfAbsent(id, key -> newInstance(type));
		} catch (IllegalStateException e) {
			throw new IOException("Cannot instantiate bean '" + id + "' of type " + type, e.getCause());
		}
	}

	/**
	 * Reads the class name registered for an id. Kept separate so the properties
	 * lookup is the only part that can throw IOException.
	 */
	private String lookupClassName(String id) throws IOException {
		Properties props = PropertiesReadAndWriteSingletonBean.loadProps();
		if (props == null)
			throw new IOException("Cannot load " + propertiesFileName + ".properties");
		return props.getProperty(id);
	}

	/**
	 * Instantiates a bean by class name. Wraps any reflective failure in an
	 * IllegalStateException because computeIfAbsent cannot propagate a checked
	 * exception; getBean unwraps it again.
	 */
	private Object newInstance(String className) {
		try {
			return Class.forName(className).getDeclaredConstructor().newInstance();
		} catch (ReflectiveOperationException e) {
			throw new IllegalStateException(e);
		}
	}

}
