package com.cbsinc.cms;

/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code.
 * You can not use it and you cannot change it without written permission from Konstantin Grabko
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class QueryManager implements IQueryManager {

	// uncomment it if you like use JDBC
	final IQueryManager iQueryManager = new QueryManagerSQL();
	// uncomment it if you like use Hibernate

	public QueryManager() {
		// iQueryManager = new QueryManagerSQL();
		// iQueryManager = new QueryManagerDS();
	}

	final public void beginTransaction() {
		iQueryManager.beginTransaction();
	}

	final public void close() {
		iQueryManager.close();
	}

	final public void commit() {
		iQueryManager.commit();
	}

	final public int executeInsertWithArgs(String query, Object[] args) throws SQLException {
		return iQueryManager.executeInsertWithArgs(query, args);
	}

	final public int executeInsertWithArgs(String query, Map args) throws SQLException {
		// TODO Auto-generated method stub
		return iQueryManager.executeInsertWithArgs(query, args);
	}

	final public int executeInsertWithJavaObject(String query, Map args) throws SQLException {
		// TODO Auto-generated method stub
		return iQueryManager.executeInsertWithJavaObject(query, args);
	}

	final public void executeQuery(String query) throws SQLException {
		iQueryManager.executeQuery(query);
	}

	final public List executeQueryList(String query) throws SQLException {
		// TODO Auto-generated method stub
		return iQueryManager.executeQueryList(query);
	}

	final public List executeQueryList(String query, int limmit, int offset) throws SQLException {
		return iQueryManager.executeQueryList(query, limmit, offset);
	}

	final public ResultSet executeQueryResultSet(String query) throws SQLException {
		// TODO Auto-generated method stub
		return iQueryManager.executeQueryResultSet(query);
	}

	final public ResultSet executeQueryResultSet(String query, Object[] args) throws SQLException {
		// TODO Auto-generated method stub
		return iQueryManager.executeQueryResultSet(query, args);
	}

	final public void executeQueryWithArgs(String query, Object[] args) throws SQLException {
		iQueryManager.executeQueryWithArgs(query, args);

	}

	final public void executeQueryWithArgs(String query, Object[] args, int limmit, int offset) throws SQLException {
		iQueryManager.executeQueryWithArgs(query, args);

	}

	final public List executeQueryWithArgsList(String query, Object[] args, int limmit, int offset)
			throws SQLException {
		// TODO Auto-generated method stub
		return iQueryManager.executeQueryWithArgsList(query, args, limmit, offset);
	}

	final public void executeUpdate(String query) throws SQLException {
		iQueryManager.executeUpdate(query);

	}

	final public int executeUpdateWithArgs(String query, Map args) throws SQLException {
		// TODO Auto-generated method stub
		return iQueryManager.executeUpdateWithArgs(query, args);
	}

	final public int executeUpdateWithArgs(String query, Object[] args) throws SQLException {
		return iQueryManager.executeUpdateWithArgs(query, args);
	}

	final public Map getArgs() {
		// TODO Auto-generated method stub
		return iQueryManager.getArgs();
	}

	final public int getColumnCount() {
		// TODO Auto-generated method stub
		return iQueryManager.getColumnCount();
	}

	final public ResourceBundle getResourcesLocalization() {
		// TODO Auto-generated method stub
		return iQueryManager.getResourcesLocalization();
	}

	final public int getRowCount() {
		// TODO Auto-generated method stub
		return iQueryManager.getRowCount();
	}

	final public String getValueAt(int aRow, int aColumn) throws SQLException {
		// TODO Auto-generated method stub
		return iQueryManager.getValueAt(aRow, aColumn);
	}

	final public Object getValueObjectAt(int aRow, int aColumn) {
		// TODO Auto-generated method stub
		return iQueryManager.getValueObjectAt(aRow, aColumn);
	}

	final public void rollback() {
		iQueryManager.rollback();

	}

//	final public void setResources_localization(ResourceBundle resources_localization)
//	{
//		iQueryManager.setResources_localization( resources_localization) ;
//	}

	final public double string2Double(String s) {
		return iQueryManager.string2Double(s);
	}

	final public int string2Integer(String s) {
		return iQueryManager.string2Integer(s);
	}

	final public List rows() {
		// TODO Auto-generated method stub
		return iQueryManager.rows();
	}

	final public Connection getCurrentConnection() {
		// TODO Auto-generated method stub
		return iQueryManager.getCurrentConnection();
	}

	final public SimpleDateFormat getSimpleDateFormat() {
		// TODO Auto-generated method stub
		return iQueryManager.getSimpleDateFormat();
	}

}
