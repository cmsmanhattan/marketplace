package com.cbsinc.cms;

import com.cbsinc.cms.utils.Latency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Date;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


import org.apache.log4j.Logger;

public class QueryManagerSQL implements java.io.Serializable, IQueryManager {

	final static private Logger log = Logger.getLogger(QueryManager.class);

	static {
	    TimerTask task = new TimerTask() {
	        public void run() {
	            //System.out.println("The SQL-Pool-Cleaner Task performed on: " + new Date() + "n" + "Thread's name: " + Thread.currentThread().getName());
	           if(freeConnectionPool != null) {
			           freeConnectionPool.forEach(t ->  {
						try {
							if(t.localconnection != null  && !t.localconnection.isClosed()) {
							t.localconnection.close();
							}

							} catch (Throwable e) {
								log.error(e);
							}
			           } );
			           freeConnectionPool.clear();
	           }

	        }
	    };
	    Timer timer = new Timer("SQL-Pool-Cleaner-Timer");
	    //long period = 1000L * 60L * 60L * 24L;
	    long period = 120000L ;
	    long delay = 15000L;
	    timer.schedule(task, delay, period);
	}


	/**
	 *
	 */
	private static final long serialVersionUID = -5583757095679256800L;

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

	final boolean debug = false;
	long sqltime = 0;


	final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	final public static Map<String, GBSConnection> connectionPool = Collections
			.synchronizedMap(new HashMap<String, GBSConnection>());
	final public static List<GBSConnection> freeConnectionPool = Collections
			.synchronizedList(new LinkedList<GBSConnection>());

	transient public List rows = new LinkedList<String[]>();

	transient boolean isTrunsactionActive = false;

	transient public Connection conn = null;

	transient Statement stat = null;

	transient PreparedStatement prepaStat = null;

	transient ResultSet rs = null;

	transient ResultSetMetaData metaData = null;

	transient String Qtable = "";

	transient String[] columnNames = {};

	final static ResourceBundle resourcesDs = PropertyResourceBundle.getBundle("driver");

	final private static ResourceBundle resourcesLocalization = PropertyResourceBundle.getBundle("localization");

	static int maxConnection = 1;

	// Input format is "dd/mm/yyyy"
	final public String dateFormat = "dd/MM/yyyy";

	final private Map args = new HashMap();

	final public List rows() {
		return rows;
	}

	final public SimpleDateFormat formatte = new SimpleDateFormat(dateFormat, Locale.getDefault());

	final public SimpleDateFormat getSimpleDateFormat() {
		return formatte;
	}

	final Connection getConnection() throws Throwable {

		GBSConnection conn;

		readWriteLock.writeLock().lock();

		try {

			if (freeConnectionPool.size() > 0) {
				conn = freeConnectionPool.remove(0);
				(conn).lock = true;
				return (conn).localconnection;

			}

			setPoolCountConnection(maxConnection);
			conn = newInstanceConnection();
			if (conn == null)
				return null;
			conn.lock = true;
			// connection_pool.add(conn_);
			connectionPool.put(conn.key, conn);
			if (conn.localconnection.isClosed()) {
				close();
				conn.localconnection = getConnection();
			}
		} finally {
			readWriteLock.writeLock().unlock();
		}

		return conn.localconnection;
	}

	final void setPoolCountConnection(int count) {

		readWriteLock.writeLock().lock();

		try {
			int makeconn = count - connectionPool.size();
			for (int i = 0; makeconn > i; i++) {
				GBSConnection conn = newInstanceConnection();
				if (conn == null)
					return;
				conn.lock = false;
				connectionPool.put(conn.key, conn);
				freeConnectionPool.add(conn);
			}

		} finally {
			readWriteLock.writeLock().unlock();
		}

	}

	/**
	 * Optional container-managed DataSource.
	 *
	 * <p>Resolved once, lazily, from the JNDI name in the CMS_DB_JNDI_NAME environment
	 * variable (for example "java:comp/env/jdbc/cms"). When it is not set, the class
	 * keeps using DriverManager exactly as before.</p>
	 *
	 * <p>This is deliberately opt-in. The class also runs its own connection pool
	 * (connection_pool / free_connection_pool), and handing it connections borrowed
	 * from a container pool while it holds them open would exhaust the container pool.
	 * Turning CMS_DB_JNDI_NAME on is therefore only correct together with setting
	 * max_connection to 1, so that each QueryManagerSQL borrows and returns a single
	 * connection rather than hoarding a pool of its own.</p>
	 */
	private static volatile javax.sql.DataSource jndiDataSource = null;

	private static volatile boolean jndiDataSourceResolved = false;

	/**
	 * @return the container DataSource, or null when CMS_DB_JNDI_NAME is unset or the
	 *         lookup fails (in which case DriverManager is used instead).
	 */
	private static javax.sql.DataSource lookupDataSource() {
		if (jndiDataSourceResolved) {
			return jndiDataSource;
		}
		synchronized (QueryManagerSQL.class) {
			if (jndiDataSourceResolved) {
				return jndiDataSource;
			}
			String name = System.getenv("CMS_DB_JNDI_NAME");
			if (name != null && name.trim().length() > 0) {
				try {
					javax.naming.Context ctx = new javax.naming.InitialContext();
					jndiDataSource = (javax.sql.DataSource) ctx.lookup(name.trim());
					log.info("using container DataSource " + name.trim());
				} catch (Throwable ex) {
					log.error("JNDI DataSource lookup failed for " + name.trim()
							+ "; falling back to DriverManager", ex);
					jndiDataSource = null;
				}
			}
			jndiDataSourceResolved = true;
			return jndiDataSource;
		}
	}

	final GBSConnection newInstanceConnection() {
		try {
			javax.sql.DataSource ds = lookupDataSource();
			if (ds != null) {
				return new GBSConnection(ds.getConnection());
			}
			Class.forName(resourcesDs.getString("driver").trim()); // PG7.0
			if (resourcesDs.getString("driver") != null)
				Class.forName(resourcesDs.getString("driver").trim(), true,
						Thread.currentThread().getContextClassLoader());

			Properties connProp = new Properties();

			String user = System.getenv("CMS_DB_USER");
			String password = System.getenv("CMS_DB_PASSWORD");
			String url = System.getenv("CMS_DB_JDBC_URL");
			String useSSL = System.getenv("CMS_DB_USE_SSL");
			String autoReconnect = System.getenv("CMS_DB_AUTO_RECONECT");

			if (user != null && password != null && url != null) {
				connProp.put("user", user.trim());
				connProp.put("password", password.trim());
				connProp.put("useSSL", useSSL.trim());
				connProp.put("autoReconnect", autoReconnect.trim());
				return new GBSConnection(DriverManager.getConnection(url.trim(), connProp));
			}

			connProp.put("user", resourcesDs.getString("user").trim());
			connProp.put("password", resourcesDs.getString("password").trim());
			connProp.put("useSSL", resourcesDs.getString("useSSL").trim());
			connProp.put("autoReconnect", resourcesDs.getString("autoReconnect").trim());

			return new GBSConnection(DriverManager.getConnection(resourcesDs.getString("url").trim(), connProp));
		} catch (Throwable ex1) {
			log.error(ex1);
			log.debug(ex1);
			return null;
		}

	}

	public QueryManagerSQL() {
		try {
			if (debug)
				sqltime = System.currentTimeMillis();

			if (maxConnection == 1)
				maxConnection = Integer.parseInt(resourcesDs.getString("max_connection").trim());
			conn = getConnection();
			stat = conn.createStatement();
			if (debug)
				log.debug(conn.toString() + " pool size: " + connectionPool.size());

			// DriverManager.setLogWriter(new PrintWriter(System.out));
			// DriverManager.setLogStream(new java.io.PrintStream(System.out));
			// formatte = new SimpleDateFormat();

		} catch (Throwable e) {
			log.error("ERROR " + conn.hashCode(), e);
			close();
		}
	}

	final public void executeQuery(String query) throws SQLException {
		final long __latStart = Latency.start();
		try {
			executeQueryImpl(query);
		} finally {
			Latency.sql("QueryManagerSQL.executeQuery", __latStart, query);
		}
	}

	private void executeQueryImpl(String query) throws SQLException {

		if (debug)
			log.debug(query);
		try {
			if (debug)
				log.debug(query);

			StringBuffer buff = new StringBuffer();
			Qtable = query;
			if (conn == null) {
				log.error("ERROR: " + query + " conn == null");
				return;
			}

			if (conn == null || stat == null) {
				log.error("ERROR: " + query + "  stat == null");
				return;
			}
			buff.append("begin query: ").append(query);
			log.debug(buff.toString());
			// log.debug(buff.toString());
			// if(stat.isClosed() ) stat = conn.createStatement();
			rs = stat.executeQuery(query);
			metaData = rs.getMetaData();
			int numberOfColumns = metaData.getColumnCount();
			// metaData.getColumnType(numberOfColumns);
			columnNames = new String[numberOfColumns];
			for (int column = 0; column < numberOfColumns; column++) {
				columnNames[column] = metaData.getColumnLabel(column + 1);
			}

			// Get all rows.
			rows = new LinkedList();

			while (rs.next()) {
				String[] Doc = new String[getColumnCount()];
				for (int i = 1; i <= getColumnCount(); i++) {
					if (rs.getString(i) == null) {
						Doc[i - 1] = "";
					} else {
						if (rs.getObject(i) instanceof java.sql.Timestamp || rs.getObject(i) instanceof java.sql.Date) {
							Doc[i - 1] = formatte.format(rs.getDate(i));
						} else if (rs.getObject(i) instanceof Boolean) {
							Doc[i - 1] = ((Boolean) rs.getObject(i)).toString();
						} else
							Doc[i - 1] = rs.getString(i);
					}
				}
				rows.add(Doc);
			}
		} catch (Exception ex) {
			log.error(query, ex);
			throw new SQLException(query + ex.getLocalizedMessage());
		}
	}

	final public ResultSet executeQueryResultSet(String query) throws SQLException {
		final long __latStart = Latency.start();
		try {
			return executeQueryResultSetImpl(query);
		} finally {
			Latency.sql("QueryManagerSQL.executeQueryResultSet", __latStart, query);
		}
	}

	private ResultSet executeQueryResultSetImpl(String query) throws SQLException {

		if (debug)
			log.debug(query);
		try {
			StringBuffer buff = new StringBuffer();
			Qtable = query;
			if (conn == null) {
				log.error("ERROR: " + query + " conn == null");
				return null;
			}

			if (conn == null || stat == null) {
				log.error("ERROR: " + query + "  stat == null");
				return null;
			}
			buff.append("begin query: ").append(query);
			log.debug(buff.toString());
			// log.debug(buff.toString());
			// if(stat.isClosed() ) stat = conn.createStatement();
			rs = stat.executeQuery(query);
			return rs;
		} catch (Exception ex) {
			log.error(query, ex);
			throw new SQLException(query + ex.getLocalizedMessage());
		}
	}

	final public int executeInsertWithArgs(String query, Object[] args) throws SQLException {
		final long __latStart = Latency.start();
		try {
			return executeInsertWithArgsImpl(query, args);
		} finally {
			Latency.sql("QueryManagerSQL.executeInsertWithArgs", __latStart, query);
		}
	}

	private int executeInsertWithArgsImpl(String query, Object[] args) throws SQLException {
		if (debug)
			log.debug(query);
		int keygen = 0;
		int result = 0;
		try {
			StringBuffer buff = new StringBuffer();
			Qtable = query;
			if (conn == null) {
				log.error("ERROR: " + query + " conn == null");
				return keygen;
			}

			if (conn == null || stat == null) {
				log.error("ERROR: " + query + "  stat == null");
				return keygen;
			}

			// stat.close() ;

			buff.append("begin query: ").append(query);
			log.debug(buff.toString());
			// log.debug(buff.toString());
			// stat.
			prepaStat = conn.prepareStatement(query, keygen);

			for (int i = 0; i < args.length; i++) {
				if (args[i] instanceof String)
					prepaStat.setString(i + 1, (String) args[i]);
				else if (args[i] instanceof Integer)
					prepaStat.setInt(i + 1, (Integer) args[i]);
				else if (args[i] instanceof Long)
					prepaStat.setLong(i + 1, (Long) args[i]);
				else if (args[i] instanceof Float)
					prepaStat.setFloat(i + 1, (Float) args[i]);
				else if (args[i] instanceof Double)
					prepaStat.setDouble(i + 1, (Double) args[i]);
				else if (args[i] instanceof Byte)
					prepaStat.setByte(i + 1, (Byte) args[i]);
				else if (args[i] instanceof Time)
					prepaStat.setTime(i + 1, (Time) args[i]);
				else if (args[i] instanceof java.sql.Date)
					prepaStat.setDate(i + 1, (java.sql.Date) args[i]);
				else if (args[i] instanceof Object)
					prepaStat.setObject(i + 1, args[i]);
			}

			result = prepaStat.executeUpdate();
		} catch (Exception ex) {
			log.error(query, ex);
			throw new SQLException(query + ex.getLocalizedMessage());
		}

		return keygen == 0 ? result : keygen;
	}

	final public int executeInsertWithArgs(String query, Map args) throws SQLException {
		final long __latStart = Latency.start();
		try {
			return executeInsertWithArgsImpl(query, args);
		} finally {
			Latency.sql("QueryManagerSQL.executeInsertWithArgs", __latStart, query);
		}
	}

	private int executeInsertWithArgsImpl(String query, Map args) throws SQLException {
		if (debug)
			log.debug(query);
		int keygen = 0;
		int result = 0;
		try {
			StringBuffer buff = new StringBuffer();
			Qtable = query;
			if (conn == null) {
				log.error("ERROR: " + query + " conn == null");
				return keygen;
			}

			if (conn == null || stat == null) {
				log.error("ERROR: " + query + "  stat == null");
				return keygen;
			}

			// stat.close() ;

			buff.append("begin query: ").append(query);
			log.debug(buff.toString());
			// log.debug(buff.toString());
			// stat.
			// prepa_stat = conn.prepareStatement(query,keygen) ;
			prepaStat = conn.prepareStatement(query);

			if (query.toLowerCase().indexOf("insert") == -1)
				throw new SQLException("Not found sql command insert in body query : " + query);
			String[] fields = query.substring(query.indexOf("(") + 1, query.indexOf(")")).split(",");

			for (int i = 0; i < fields.length; i++) {
				Object obj = args.get(fields[i].trim());
				if (obj == null)
					obj = args.get(fields[i].toLowerCase().trim());
				if (obj instanceof String)
					prepaStat.setString(i + 1, (String) obj);
				else if (obj instanceof Integer)
					prepaStat.setInt(i + 1, (Integer) obj);
				else if (obj instanceof Long)
					prepaStat.setLong(i + 1, (Long) obj);
				else if (obj instanceof Float)
					prepaStat.setFloat(i + 1, (Float) obj);
				else if (obj instanceof Double)
					prepaStat.setDouble(i + 1, (Double) obj);
				else if (obj instanceof Byte)
					prepaStat.setByte(i + 1, (Byte) obj);
				else if (obj instanceof Time)
					prepaStat.setTime(i + 1, (Time) obj);
				else if (obj instanceof java.sql.Date)
					prepaStat.setDate(i + 1, (java.sql.Date) obj);
				// else if( obj instanceof java.util.Date ) prepa_stat.setDate(i + 1 , new
				// java.sql.Date(((java.util.Date)obj)).getTime()) ;
				else if (obj instanceof java.util.Date)
					prepaStat.setDate(i + 1, new java.sql.Date(((java.util.Date) obj).getTime()));
				else if (obj instanceof Object)
					prepaStat.setObject(i + 1, obj);
				else if (obj == null)
					prepaStat.setObject(i + 1, obj);
			}
			result = prepaStat.executeUpdate();
		} catch (Exception ex) {
			log.error(query, ex);
			throw new SQLException(query + ex.getLocalizedMessage());
		}

		return keygen == 0 ? result : keygen;
	}

	final public int executeInsertWithJavaObject(String query, Map args) throws SQLException {
		final long __latStart = Latency.start();
		try {
			return executeInsertWithJavaObjectImpl(query, args);
		} finally {
			Latency.sql("QueryManagerSQL.executeInsertWithJavaObject", __latStart, query);
		}
	}

	private int executeInsertWithJavaObjectImpl(String query, Map args) throws SQLException {
		if (debug)
			log.debug(query);
		int keygen = 0;
		int result = 0;
		try {
			StringBuffer buff = new StringBuffer();
			Qtable = query;
			if (conn == null) {
				log.error("ERROR: " + query + " conn == null");
				return keygen;
			}

			if (conn == null || stat == null) {
				log.error("ERROR: " + query + "  stat == null");
				return keygen;
			}

			// stat.close() ;

			buff.append("begin query: ").append(query);
			log.debug(buff.toString());
			// log.debug(buff.toString());
			// stat.
			// prepa_stat = conn.prepareStatement(query,keygen) ;
			prepaStat = conn.prepareStatement(query);

			if (query.toLowerCase().indexOf("insert") == -1)
				throw new SQLException("Not found sql command insert in body query : " + query);
			String[] fields = query.substring(query.indexOf("(") + 1, query.indexOf(")")).split(",");

			for (int i = 0; i < fields.length; i++) {
				Object obj = args.get(fields[i].trim());
				if (obj == null)
					obj = args.get(fields[i].toLowerCase().trim());
				if (obj instanceof String)
					prepaStat.setString(i + 1, (String) obj);
				else if (obj instanceof Integer)
					prepaStat.setInt(i + 1, (Integer) obj);
				else if (obj instanceof Long)
					prepaStat.setLong(i + 1, (Long) obj);
				else if (obj instanceof Float)
					prepaStat.setFloat(i + 1, (Float) obj);
				else if (obj instanceof Double)
					prepaStat.setDouble(i + 1, (Double) obj);
				else if (obj instanceof Byte)
					prepaStat.setByte(i + 1, (Byte) obj);
				else if (obj instanceof Time)
					prepaStat.setTime(i + 1, (Time) obj);
				else if (obj instanceof java.sql.Date)
					prepaStat.setDate(i + 1, (java.sql.Date) obj);
				// else if( obj instanceof java.util.Date ) prepa_stat.setDate(i + 1 , new
				// java.sql.Date(((java.util.Date)obj)).getTime()) ;
				else if (obj instanceof java.util.Date)
					prepaStat.setDate(i + 1, new java.sql.Date(((java.util.Date) obj).getTime()));
				else if (obj instanceof Object) {
					prepaStat.setObject(i + 1, obj);
				}
			}
			result = prepaStat.executeUpdate();
		} catch (Exception ex) {
			log.error(query, ex);
			throw new SQLException(query + ex.getLocalizedMessage());
		}

		return keygen == 0 ? result : keygen;
	}


	String[] getFileds(String query )
	{

		List<String> list = new ArrayList<>();
		query = query.trim() ;
		String[] substring = query.split("(?<=\\?)");
		for(int i = 0 ; i < substring.length ; i++ )
		{
			String[] substring2 = substring[i].split("=");
			if(substring2[1].indexOf("?") == -1) continue ;
			String substring3 = substring2[0].trim();
			String[] substring4 = substring3.split(" ");
			String field = substring4[substring4.length - 1].trim();
			if(field.length() > 0) list.add( field) ;

		}
		return list.toArray(new String[0]);
	}



	/** Positional UPDATE/DELETE with bound parameters; same binding loop as executeInsertWithArgs(Object[]). */
	final public int executeUpdateWithArgs(String query, Object[] args) throws SQLException {
		final long __latStart = Latency.start();
		try {
			return executeUpdateWithArgsImpl(query, args);
		} finally {
			Latency.sql("QueryManagerSQL.executeUpdateWithArgs", __latStart, query);
		}
	}

	private int executeUpdateWithArgsImpl(String query, Object[] args) throws SQLException {
		return executeInsertWithArgs(query, args);
	}

	final public int executeUpdateWithArgs(String query, Map args) throws SQLException {
		final long __latStart = Latency.start();
		try {
			return executeUpdateWithArgsImpl(query, args);
		} finally {
			Latency.sql("QueryManagerSQL.executeUpdateWithArgs", __latStart, query);
		}
	}

	private int executeUpdateWithArgsImpl(String query, Map args) throws SQLException {
		if (debug)
			log.debug(query);
		int keygen = 0;
		int result = 0;
		try {
			StringBuffer buff = new StringBuffer();
			Qtable = query;
			if (conn == null) {
				log.error("ERROR: " + query + " conn == null");
				return keygen;
			}

			if (conn == null || stat == null) {
				log.error("ERROR: " + query + "  stat == null");
				return keygen;
			}

			// stat.close() ;

			buff.append("begin query: ").append(query);
			log.debug(buff.toString());
			// log.debug(buff.toString());
			// stat.
			// prepa_stat = conn.prepareStatement(query,keygen) ;
			prepaStat = conn.prepareStatement(query);

			if (query.toLowerCase().indexOf("update") == -1)
				throw new SQLException("Not found sql command update in body query : " + query);
			String[] fields = getFileds(query);
			//String[] fields = query .substring(query.toLowerCase().indexOf("set") + 3, query.toLowerCase().indexOf("where")).split(",");


			String field = "";
			for (int i = 0; i < fields.length; i++) {
				//field = fields[i].substring(0, fields[i].indexOf("=")).trim();
				field = fields[i];
				Object obj = args.get(field);
				if (obj instanceof String)
					prepaStat.setString(i + 1, (String) obj);
				else if (obj instanceof Integer)
					prepaStat.setInt(i + 1, (Integer) obj);
				else if (obj instanceof Long)
					prepaStat.setLong(i + 1, (Long) obj);
				else if (obj instanceof Float)
					prepaStat.setFloat(i + 1, (Float) obj);
				else if (obj instanceof Double)
					prepaStat.setDouble(i + 1, (Double) obj);
				else if (obj instanceof Byte)
					prepaStat.setByte(i + 1, (Byte) obj);
				else if (obj instanceof Time)
					prepaStat.setTime(i + 1, (Time) obj);
				else if (obj instanceof java.sql.Date)
					prepaStat.setDate(i + 1, (java.sql.Date) obj);
				// else if( obj instanceof java.util.Date ) prepa_stat.setDate(i + 1 , new
				// java.sql.Date(((java.util.Date)obj)).getTime()) ;
				else if (obj instanceof java.util.Date)
					prepaStat.setDate(i + 1, new java.sql.Date(((java.util.Date) obj).getTime()));
				else if (obj instanceof Object)
					prepaStat.setObject(i + 1, obj);
			}
			result = prepaStat.executeUpdate();
		} catch (Exception ex) {
			log.error(query, ex);
			throw new SQLException(query + ex.getLocalizedMessage());
		}

		return keygen == 0 ? result : keygen;
	}

//		void setArgString( String value ) throws SQLException
//		{
//			if( prepa_stat == null  || prepa_stat.isClosed())  throw new SQLException("PrepaStatement colosed or null" );
//			prepa_stat.
//
//		}

	final public void executeQueryWithArgs(String query, Object[] args) throws SQLException {
		final long __latStart = Latency.start();
		try {
			executeQueryWithArgsImpl(query, args);
		} finally {
			Latency.sql("QueryManagerSQL.executeQueryWithArgs", __latStart, query);
		}
	}

	private void executeQueryWithArgsImpl(String query, Object[] args) throws SQLException {

		if (debug)
			log.debug(query);
		try {
			StringBuffer buff = new StringBuffer();
			Qtable = query;
			if (conn == null) {
				log.error("ERROR: " + query + " conn == null");
				return;
			}

			if (conn == null || stat == null) {
				log.error("ERROR: " + query + "  stat == null");
				return;
			}

			// stat.close() ;

			buff.append("begin query: ").append(query);
			log.debug(buff.toString());
			// log.debug(buff.toString());
			// stat.
			prepaStat = conn.prepareStatement(query);

			for (int i = 0; i < args.length; i++) {
				if (args[i] instanceof String)
					prepaStat.setString(i + 1, (String) args[i]);
				else if (args[i] instanceof Integer)
					prepaStat.setInt(i + 1, (Integer) args[i]);
				else if (args[i] instanceof Long)
					prepaStat.setLong(i + 1, (Long) args[i]);
				else if (args[i] instanceof Float)
					prepaStat.setFloat(i + 1, (Float) args[i]);
				else if (args[i] instanceof Double)
					prepaStat.setDouble(i + 1, (Double) args[i]);
				else if (args[i] instanceof Byte)
					prepaStat.setByte(i + 1, (Byte) args[i]);
				else if (args[i] instanceof Time)
					prepaStat.setTime(i + 1, (Time) args[i]);
				else if (args[i] instanceof java.sql.Date)
					prepaStat.setDate(i + 1, (java.sql.Date) args[i]);
				else if (args[i] instanceof Object)
					prepaStat.setObject(i + 1, args[i]);
			}

			rs = prepaStat.executeQuery();
			// rs = stat.executeQuery(query);
			metaData = rs.getMetaData();
			int numberOfColumns = metaData.getColumnCount();
			// metaData.getColumnType(numberOfColumns);
			columnNames = new String[numberOfColumns];
			for (int column = 0; column < numberOfColumns; column++) {
				columnNames[column] = metaData.getColumnLabel(column + 1);
			}

			// Get all rows.
			rows = new LinkedList();

			while (rs.next()) {
				String[] Doc = new String[getColumnCount()];
				for (int i = 1; i <= getColumnCount(); i++) {
					if (rs.getString(i) == null) {
						Doc[i - 1] = "";
					} else {
						if (rs.getObject(i) instanceof java.sql.Timestamp || rs.getObject(i) instanceof java.sql.Date) {
							Doc[i - 1] = formatte.format(rs.getDate(i));
						} else if (rs.getObject(i) instanceof Boolean) {
							Doc[i - 1] = ((Boolean) rs.getObject(i)).toString();
						} else
							Doc[i - 1] = rs.getString(i);
					}
				}
				rows.add(Doc);
			}
		} catch (Exception ex) {
			log.error(query, ex);
			throw new SQLException(query + ex.getLocalizedMessage());
		}
	}

	final public ResultSet executeQueryResultSet(String query, Object[] args) throws SQLException {
		final long __latStart = Latency.start();
		try {
			return executeQueryResultSetImpl(query, args);
		} finally {
			Latency.sql("QueryManagerSQL.executeQueryResultSet", __latStart, query);
		}
	}

	private ResultSet executeQueryResultSetImpl(String query, Object[] args) throws SQLException {
		if (debug)
			log.debug(query);
		try {
			StringBuffer buff = new StringBuffer();
			Qtable = query;
			if (conn == null) {
				log.error("ERROR: " + query + " conn == null");
				return null;
			}

			if (conn == null || stat == null) {
				log.error("ERROR: " + query + "  stat == null");
				return null;
			}

			// stat.close() ;

			buff.append("begin query: ").append(query);
			log.debug(buff.toString());
			// log.debug(buff.toString());
			// stat.
			prepaStat = conn.prepareStatement(query);

			for (int i = 0; i < args.length; i++) {
				if (args[i] instanceof String)
					prepaStat.setString(i + 1, (String) args[i]);
				else if (args[i] instanceof Integer)
					prepaStat.setInt(i + 1, (Integer) args[i]);
				else if (args[i] instanceof Long)
					prepaStat.setLong(i + 1, (Long) args[i]);
				else if (args[i] instanceof Float)
					prepaStat.setFloat(i + 1, (Float) args[i]);
				else if (args[i] instanceof Double)
					prepaStat.setDouble(i + 1, (Double) args[i]);
				else if (args[i] instanceof Byte)
					prepaStat.setByte(i + 1, (Byte) args[i]);
				else if (args[i] instanceof Time)
					prepaStat.setTime(i + 1, (Time) args[i]);
				else if (args[i] instanceof java.sql.Date)
					prepaStat.setDate(i + 1, (java.sql.Date) args[i]);
				else if (args[i] instanceof Object)
					prepaStat.setObject(i + 1, args[i]);
			}

			return prepaStat.executeQuery();

		} catch (Exception ex) {
			log.error(query, ex);
			throw new SQLException(query + ex.getLocalizedMessage());
		}
	}

	/**
	 * Appends a SQL-level LIMIT/OFFSET so MySQL returns only one page instead of
	 * materialising the full result and having the driver scroll past the first
	 * <code>offset</code> rows. Both values are ints supplied by code, never
	 * raw request strings. A negative limit means "no limit" (legacy callers).
	 */
	private static String pageQuery(String query, int limmit, int offset) {
		if (query == null || limmit < 0)
			return query;
		String q = query.trim();
		while (q.endsWith(";"))
			q = q.substring(0, q.length() - 1).trim();
		return q + " LIMIT " + limmit + " OFFSET " + Math.max(offset, 0);
	}

	final public void executeQueryWithArgs(String query, Object[] args, int limmit, int offset) throws SQLException {
		final long __latStart = Latency.start();
		try {
			executeQueryWithArgsImpl(query, args, limmit, offset);
		} finally {
			Latency.sql("QueryManagerSQL.executeQueryWithArgs", __latStart, query);
		}
	}

	private void executeQueryWithArgsImpl(String query, Object[] args, int limmit, int offset) throws SQLException {
		if (debug)
			log.debug(query);
		try {
			StringBuffer buff = new StringBuffer();
			Qtable = query;
			if (conn == null) {
				log.error("ERROR: " + query + " conn == null");
				return;
			}

			if (conn == null || stat == null) {
				log.error("ERROR: " + query + "  stat == null");
				return;
			}

			// stat.close() ;

			buff.append("begin query: ").append(query);
			log.debug(buff.toString());
			// log.debug(buff.toString());
			// stat.
			// stat =
			// conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			// rs = stat.executeQuery(query);

			prepaStat = conn.prepareStatement(pageQuery(query, limmit, offset), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			for (int i = 0; i < args.length; i++) {
				if (args[i] instanceof String)
					prepaStat.setString(i + 1, (String) args[i]);
				else if (args[i] instanceof Integer)
					prepaStat.setInt(i + 1, (Integer) args[i]);
				else if (args[i] instanceof Long)
					prepaStat.setLong(i + 1, (Long) args[i]);
				else if (args[i] instanceof Float)
					prepaStat.setFloat(i + 1, (Float) args[i]);
				else if (args[i] instanceof Double)
					prepaStat.setDouble(i + 1, (Double) args[i]);
				else if (args[i] instanceof Byte)
					prepaStat.setByte(i + 1, (Byte) args[i]);
				else if (args[i] instanceof Time)
					prepaStat.setTime(i + 1, (Time) args[i]);
				else if (args[i] instanceof java.sql.Date)
					prepaStat.setDate(i + 1, (java.sql.Date) args[i]);
				else if (args[i] instanceof Object)
					prepaStat.setObject(i + 1, args[i]);
			}

			rs = prepaStat.executeQuery();
			// FIX: paging is done in SQL (LIMIT/OFFSET); no client-side scrolling.

			// rs = stat.executeQuery(query);
			metaData = rs.getMetaData();
			int numberOfColumns = metaData.getColumnCount();
			// metaData.getColumnType(numberOfColumns);
			columnNames = new String[numberOfColumns];
			for (int column = 0; column < numberOfColumns; column++) {
				columnNames[column] = metaData.getColumnLabel(column + 1);
			}

			// Get all rows.
			rows = new LinkedList();

			while (rs.next() && limmit-- != 0) {
				String[] Doc = new String[getColumnCount()];
				for (int i = 1; i <= getColumnCount(); i++) {
					if (rs.getString(i) == null) {
						Doc[i - 1] = "";
					} else {
						if (rs.getObject(i) instanceof java.sql.Timestamp || rs.getObject(i) instanceof java.sql.Date) {
							Doc[i - 1] = formatte.format(rs.getDate(i));
						} else if (rs.getObject(i) instanceof Boolean) {
							Doc[i - 1] = ((Boolean) rs.getObject(i)).toString();
						} else
							Doc[i - 1] = rs.getString(i);
					}
				}
				rows.add(Doc);
			}
		} catch (Exception ex) {
			log.error(query, ex);
			throw new SQLException(query + ex.getLocalizedMessage());
		}
	}

	final public List executeQueryWithArgsList(String query, Object[] args, int limmit, int offset) throws SQLException {
		final long __latStart = Latency.start();
		try {
			return executeQueryWithArgsListImpl(query, args, limmit, offset);
		} finally {
			Latency.sql("QueryManagerSQL.executeQueryWithArgsList", __latStart, query);
		}
	}

	private List executeQueryWithArgsListImpl(String query, Object[] args, int limmit, int offset) throws SQLException {

		if (debug)
			log.debug(query);
		List list = new LinkedList();
		try {
			StringBuffer buff = new StringBuffer();
			Qtable = query;
			if (conn == null) {
				log.error("ERROR: " + query + " conn == null");
				return list;
			}

			if (conn == null || stat == null) {
				log.error("ERROR: " + query + "  stat == null");
				return list;
			}

			// stat.close() ;

			buff.append("begin query: ").append(query);
			log.debug(buff.toString());
			// log.debug(buff.toString());
			// stat.
			// stat =
			// conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			// rs = stat.executeQuery(query);

			prepaStat = conn.prepareStatement(pageQuery(query, limmit, offset), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			for (int i = 0; i < args.length; i++) {
				if (args[i] instanceof String)
					prepaStat.setString(i + 1, (String) args[i]);
				else if (args[i] instanceof Integer)
					prepaStat.setInt(i + 1, (Integer) args[i]);
				else if (args[i] instanceof Long)
					prepaStat.setLong(i + 1, (Long) args[i]);
				else if (args[i] instanceof Float)
					prepaStat.setFloat(i + 1, (Float) args[i]);
				else if (args[i] instanceof Double)
					prepaStat.setDouble(i + 1, (Double) args[i]);
				else if (args[i] instanceof Byte)
					prepaStat.setByte(i + 1, (Byte) args[i]);
				else if (args[i] instanceof Time)
					prepaStat.setTime(i + 1, (Time) args[i]);
				else if (args[i] instanceof java.sql.Date)
					prepaStat.setDate(i + 1, (java.sql.Date) args[i]);
				else if (args[i] instanceof Object)
					prepaStat.setObject(i + 1, args[i]);
			}

			rs = prepaStat.executeQuery();
			// FIX: paging is done in SQL (LIMIT/OFFSET); no client-side scrolling.

			// rs = stat.executeQuery(query);
			metaData = rs.getMetaData();
			int numberOfColumns = metaData.getColumnCount();
			// metaData.getColumnType(numberOfColumns);
			columnNames = new String[numberOfColumns];
			for (int column = 0; column < numberOfColumns; column++) {
				columnNames[column] = metaData.getColumnLabel(column + 1);
			}

			// Get all rows.
			list = new LinkedList();

			while (rs.next() && limmit-- != 0) {
				String[] Doc = new String[getColumnCount()];
				for (int i = 1; i <= getColumnCount(); i++) {
					if (rs.getString(i) == null) {
						Doc[i - 1] = "";
					} else {
						if (rs.getObject(i) instanceof java.sql.Timestamp || rs.getObject(i) instanceof java.sql.Date) {
							Doc[i - 1] = formatte.format(rs.getDate(i));
						} else if (rs.getObject(i) instanceof Boolean) {
							Doc[i - 1] = ((Boolean) rs.getObject(i)).toString();
						} else
							Doc[i - 1] = rs.getString(i);
					}
				}
				rows.add(Doc);
			}
		} catch (Exception ex) {
			log.error(query, ex);
			throw new SQLException(query + ex.getLocalizedMessage());
		}

		return list;
	}

	final public List executeQueryList(String query) throws SQLException {
		final long __latStart = Latency.start();
		try {
			return executeQueryListImpl(query);
		} finally {
			Latency.sql("QueryManagerSQL.executeQueryList", __latStart, query);
		}
	}

	private List executeQueryListImpl(String query) throws SQLException {

		if (debug)
			log.debug(query);
		List list = new LinkedList();
		try {
			StringBuffer buff = new StringBuffer();
			Qtable = query;
			if (conn == null) {
				log.error("ERROR: " + query + " conn == null");
				return list;
			}

			if (conn == null || stat == null) {
				log.error("ERROR: " + query + "  stat == null");
				return list;
			}
			buff.append("begin query: ").append(query);
			log.debug(buff.toString());
			// log.debug(buff.toString());
			/// if(stat.isClosed() ) stat = conn.createStatement();
			rs = stat.executeQuery(query);
			// rs.absolute(row)
			metaData = rs.getMetaData();

			int numberOfColumns = metaData.getColumnCount();
			// metaData.getColumnType(numberOfColumns);
			columnNames = new String[numberOfColumns];
			for (int column = 0; column < numberOfColumns; column++) {
				columnNames[column] = metaData.getColumnLabel(column + 1);
			}

			// Get all rows.
			list = new LinkedList();

			while (rs.next()) {
				String[] Doc = new String[getColumnCount()];
				for (int i = 1; i <= getColumnCount(); i++) {
					if (rs.getString(i) == null) {
						Doc[i - 1] = "";
					} else {
						if (rs.getObject(i) instanceof java.sql.Timestamp || rs.getObject(i) instanceof java.sql.Date) {
							Doc[i - 1] = formatte.format(rs.getDate(i));
						} else if (rs.getObject(i) instanceof Boolean) {
							Doc[i - 1] = ((Boolean) rs.getObject(i)).toString();
						} else
							Doc[i - 1] = rs.getString(i);
					}
				}
				list.add(Doc);

			}

		} catch (Exception ex) {
			log.error(query, ex);
			throw new SQLException(query + ex.getLocalizedMessage());
		}

		return list;
	}

	/*
	 *
	 */
	final public List executeQueryList(String query, int limmit, int offset) throws SQLException {
		final long __latStart = Latency.start();
		try {
			return executeQueryListImpl(query, limmit, offset);
		} finally {
			Latency.sql("QueryManagerSQL.executeQueryList", __latStart, query);
		}
	}

	private List executeQueryListImpl(String query, int limmit, int offset) throws SQLException {

		if (debug)
			log.debug(query);
		List list = new LinkedList();
		try {
			StringBuffer buff = new StringBuffer();
			Qtable = query;
			if (conn == null) {
				log.error("ERROR: " + query + " conn == null");
				return list;
			}

			if (conn == null || stat == null) {
				log.error("ERROR: " + query + "  stat == null");
				return list;
			}
			buff.append("begin query: ").append(query);
			log.debug(buff.toString());
			// log.debug(buff.toString());
			stat.close(); // rigth close
			stat = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stat.executeQuery(pageQuery(query, limmit, offset));
			// FIX: paging is done in SQL (LIMIT/OFFSET); no client-side scrolling.
			// Statement stmt =
			// con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

			// rs.absolute(row)
			metaData = rs.getMetaData();

			int numberOfColumns = metaData.getColumnCount();
			// metaData.getColumnType(numberOfColumns);
			columnNames = new String[numberOfColumns];
			for (int column = 0; column < numberOfColumns; column++) {
				columnNames[column] = metaData.getColumnLabel(column + 1);
			}

			// Get all rows.
			list = new LinkedList();

			while (rs.next() && limmit-- != 0) {
				String[] Doc = new String[getColumnCount()];
				for (int i = 1; i <= getColumnCount(); i++) {
					if (rs.getString(i) == null) {
						Doc[i - 1] = "";
					} else {
						if (rs.getObject(i) instanceof java.sql.Timestamp || rs.getObject(i) instanceof java.sql.Date) {
							Doc[i - 1] = formatte.format(rs.getDate(i));
						} else if (rs.getObject(i) instanceof Boolean) {
							Doc[i - 1] = ((Boolean) rs.getObject(i)).toString();
						} else
							Doc[i - 1] = rs.getString(i);
					}
				}
				list.add(Doc);

			}

		} catch (Exception ex) {
			log.error(query, ex);
			throw new SQLException(query + ex.getLocalizedMessage());
		}

		return list;
	}

	final public void executeUpdate(String query) throws SQLException {
		final long __latStart = Latency.start();
		try {
			executeUpdateImpl(query);
		} finally {
			Latency.sql("QueryManagerSQL.executeUpdate", __latStart, query);
		}
	}

	private void executeUpdateImpl(String query) throws SQLException {
		if (debug)
			log.debug(query);
		Qtable = query;
		if (conn == null) {
			log.error("ERROR: " + query + " conn == null");
			return;
		}

		if (stat == null) {
			log.error("ERROR: " + query + "  stat == null");
			return;
		}

		log.debug("begin update: " + query);

		try {
			stat.executeUpdate(query);

		} catch (Exception ex) {
			log.error(query, ex);
			throw new SQLException(query + ex.getLocalizedMessage());
		}

		// stat.executeUpdate(norm(query)) ;
	}

	final public int getColumnCount() {
		return columnNames.length;
	}

	final public int getRowCount() {
		return rows.size();
	}

	final public String getValueAt(int aRow, int aColumn) throws SQLException {
		if (rows.size() == 0)
			throw new SQLException("QueryManager.getValueAt - error ArrayIndexOutOffBound");
		String[] row = (String[]) rows.get(aRow);
		return row[aColumn];
	}

	final public Object getValueObjectAt(int aRow, int aColumn) {
		Object[] row = (Object[]) rows.get(aRow);
		return row[aColumn];
	}

	final public void close() {

		if (debug) {
			sqltime = System.currentTimeMillis() - sqltime;
			log.debug("querytime: " + sqltime + " ms.");
		}

		readWriteLock.writeLock().lock();

		try {
			if (rs != null)
				rs.close();
			if (isTrunsactionActive)
				return;

			if (stat != null)
				stat.close();

			if (prepaStat != null)
				prepaStat.close();

			if (conn != null) {

				if (maxConnection < connectionPool.size()) {
					connectionPool.remove("" + conn.hashCode());
					conn.close();
				}

				if (connectionPool.get("" + conn.hashCode()) instanceof GBSConnection) {
					if (((GBSConnection) connectionPool.get("" + conn.hashCode())).localconnection.equals(conn)) {
						((GBSConnection) connectionPool.get("" + conn.hashCode())).lock = false;
						((GBSConnection) connectionPool.get("" + conn.hashCode())).localconnection.setAutoCommit(true);
						freeConnectionPool.add(((GBSConnection) connectionPool.get("" + conn.hashCode())));
						if (conn.isClosed()) {
							connectionPool.remove("" + conn.hashCode());
							GBSConnection conn = newInstanceConnection();
							conn.lock = false;
							freeConnectionPool.add(conn);
							connectionPool.put(conn.key, conn);
						}
					}
				}
			} else {
				connectionPool.remove(null);
				GBSConnection conn = newInstanceConnection();
				conn.lock = false;
				connectionPool.put(conn.key, conn);
				freeConnectionPool.add(conn);
			}

		} catch (SQLException ex) {
			log.error(ex);
			log.assertLog(false, "Method close()");
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	final public void closeOld() {
		try {
			if (rs != null)
				rs.close();
			if (isTrunsactionActive)
				return;

			if (stat != null)
				stat.close();
			if (conn != null) {
				for (int i = 0; connectionPool.size() > i; i++) {
					if (connectionPool.get(i) instanceof GBSConnection) {
						if (((GBSConnection) connectionPool.get(i)).localconnection.equals(conn)) {
							((GBSConnection) connectionPool.get(i)).lock = false;
							((GBSConnection) connectionPool.get(i)).localconnection.setAutoCommit(true);
						}
					}
				}
			}

		} catch (SQLException ex) {
			log.error(ex);
			log.assertLog(false, "Method close()");
		}
	}

	// conn

	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	final public int string2Integer(String s) {
		int i;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			i = -1;
		}
		return i;
	}

	final public double string2Double(String s) {
		double d;
		try {
			d = Double.parseDouble(s);
		} catch (NumberFormatException ex) {
			d = -1.0;
		}
		return d;
	}

	final public void rollback() {

		try {
			if (conn == null)
				throw new SQLException("rollback , Connention == null, query=" + Qtable);
			if (conn.getAutoCommit())
				throw new SQLException("rollback, AutoCommit must be == false, query=" + Qtable);
			conn.rollback();
			log.debug("Transaction Rollback()");
		} catch (SQLException ex) {
			log.error(ex);
		} finally {
			isTrunsactionActive = false;
		}

	}

	final public void commit() {
		try {
			if (conn == null)
				throw new SQLException("commit, Connention == null, query=" + Qtable);
			if (conn.getAutoCommit())
				throw new SQLException("commit, AutoCommit must be == false , query=" + Qtable);
			conn.commit();
			log.debug("Transaction Commit()");
		} catch (SQLException ex) {
			log.error(ex);
		} finally {
			isTrunsactionActive = false;
		}

	}

	final public void beginTransaction() {

		try {
			if (conn == null)
				throw new SQLException("BeginTransaction , Connention == null, query=" + Qtable);
			conn.setAutoCommit(false);
			isTrunsactionActive = true;
			log.debug("BeginTransaction()");
		} catch (SQLException ex) {
			isTrunsactionActive = false;
			log.error(ex);
		}

	}

	final public ResourceBundle getResourcesLocalization() {
		return resourcesLocalization;
	}

//	final public void setResources_localization(ResourceBundle resources_localization) {
//			this.resources_localization = resources_localization;
//		}

	final public Map getArgs() {
		args.clear();
		return args;
	}

	final public Connection getCurrentConnection() {
		// TODO Auto-generated method stub
		return conn;
	}

}

final class GBSConnection {
	public Connection localconnection;

	public boolean lock = false;
	public String key = "";

	public GBSConnection(final Connection connection) {
		this.localconnection = connection;
		key = "" + connection.hashCode();

	}
}
