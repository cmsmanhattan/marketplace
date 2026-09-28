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

import com.cbsinc.cms.utils.FileNames;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Vector;

import org.apache.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet Class
 *
 * @web.servlet name="downloadservletbyrowid" display-name="Name for
 *              DownloadServletByRowId" description="Description for
 *              DownloadServletByRowId"
 * @web.servlet-mapping url-pattern="/downloadservletbyrowid"
 * @web.servlet-init-param name="A parameter" value="A value"
 */
public class DownloadServletByRowId extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	static private Logger log = Logger.getLogger(DownloadServletByRowId.class);

	String strDevice = "";

	// OperationAmountBean OperationAmountBeanId = null ;
	AuthorizationPageBean AuthorizationPageBeanId = null;

	String fileId = "1"; // "unknown.zip" ;

	String softId = "1"; // "unknown.zip" ;

	String filename = "unknown.zip";

	int row = 0;

	// LargeObject obj = null ;
	// LargeObjectManager lobj = null ;
	// int oid = 0 ;

	String Qtable = "";

	Vector rows = new Vector();

	String[] columnNames = {};

	// Class[] columnTpyes = {};

	/////// ProductlistBean ProductlistBeanId = null;

	String typePage = "";

	// private static final String CONTENT_TYPE = "text/html;
	// charset=windows-1251";
	// private static final String CONTENT_TYPE = "application/x-zip-compressed;
	// ";
	// private static final String CONTENT_TYPE = "application/zip";
	// application/zip
	// application/x-zip-compressed
	// Initialize global variables

	transient ResourceBundle localization = null;

	public void init() throws ServletException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		processRequest(request, response);
	}

	// FIX (shared state between concurrent requests): a servlet is a single
	// instance shared by every request thread, but this class keeps the
	// current request's user bean, file id, filename, output stream and
	// similar values in instance fields that processRequest() overwrites. Two
	// requests at the same time therefore read each other's values: one user
	// receives another user's file, or two uploads write into one
	// FileOutputStream and both files are corrupt.
	// The correct fix is to turn those fields into locals passed to the helper
	// methods; that is a large edit across all upload/download servlets. As a
	// safe interim measure, requests to this servlet are serialised. Uploads
	// are short and infrequent so this costs nothing; for downloads it caps
	// throughput at one transfer at a time and should be replaced by the
	// proper refactor if download volume matters.
	protected synchronized void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		if (localization == null)
			localization = PropertyResourceBundle.getBundle("localization", request.getLocale());
		else if (!localization.getLocale().getLanguage().equals(request.getLocale().getLanguage()))
			localization = PropertyResourceBundle.getBundle("localization", request.getLocale());

		if (request.getParameter("productid") != null)
			softId = request.getParameter("productid");
		AuthorizationPageBeanId = (AuthorizationPageBean) request.getSession().getAttribute("authorizationPageBeanId");

		if (strDevice != null) {
			if (softId == null || softId.length() == 0) {
				AuthorizationPageBeanId.setStrMessage(localization.getString("user_not_autorization"));
				response.sendRedirect("ProductInfo.jsp");
				return;
			}
		}

		// FileDownload fileDownload = setFileNameByFile_ID(file_id);
		FileDownload fileDownload = setFileNameByProductId(softId);
		filename = fileDownload.getName();
		// response.setContentType(CONTENT_TYPE);
		// response.setHeader("Content-Type", "application/zip");
		// response.setHeader("Content-Encoding", "zip");
		// response.setHeader("Content-disposition", "attachment;filename=" +
		// filename);
		String CONTENT_TYPE = "application/octet-stream";
		String ext = "";
		// FIX: was filename.substring(filename.lastIndexOf(".") + 1, ...).
		// For a name with no dot lastIndexOf returns -1, so the expression became
		// substring(0, length) and handed back the WHOLE filename as the
		// extension. That value is used to pick the Content-Type below, so the
		// browser was told the type was e.g. "invoice2024" and downloaded the file
		// as application/octet-stream instead of opening it.
		ext = FileNames.extension(filename);
		ext = ext.toLowerCase();
		if (ext.compareTo("jar") == 0)
			CONTENT_TYPE = "application/java-archive";
		if (ext.compareTo("wml") == 0)
			CONTENT_TYPE = "text/vnd.wap.wml";
		if (ext.compareTo("mid") == 0)
			CONTENT_TYPE = "audio/x-midi";
		if (ext.compareTo("midi") == 0)
			CONTENT_TYPE = "audio/x-midi";
		if (ext.compareTo("wbmp") == 0)
			CONTENT_TYPE = "image/vnd.wap.wbmp";
		if (ext.compareTo("wml") == 0)
			CONTENT_TYPE = "text/vnd.wap.wml";
		if (ext.compareTo("wmlc") == 0)
			CONTENT_TYPE = "application/vnd.wap.wmlc";
		if (ext.compareTo("wmlscriptc") == 0)
			CONTENT_TYPE = "application/vnd.wap.wmlscriptc";
		if (ext.compareTo("jpe") == 0)
			CONTENT_TYPE = "image/jpeg";
		if (ext.compareTo("jpeg") == 0)
			CONTENT_TYPE = "image/jpeg";
		if (ext.compareTo("jpg") == 0)
			CONTENT_TYPE = "image/jpeg";
		if (ext.compareTo("gif") == 0)
			CONTENT_TYPE = "image/gif";
		if (ext.compareTo("mov") == 0)
			CONTENT_TYPE = "video/quicktime";
		if (ext.compareTo("movie") == 0)
			CONTENT_TYPE = "video/x-sgi-movie";
		if (ext.compareTo("mp1") == 0)
			CONTENT_TYPE = "audio/x-mpeg";
		if (ext.compareTo("mp2") == 0)
			CONTENT_TYPE = "audio/x-mpeg";
		if (ext.compareTo("mp3") == 0)
			CONTENT_TYPE = "audio/x-mpeg";
		if (ext.compareTo("mp4") == 0)
			CONTENT_TYPE = "audio/x-mpeg";
		if (ext.compareTo("mpa") == 0)
			CONTENT_TYPE = "audio/x-mpeg";
		if (ext.compareTo("mpe") == 0)
			CONTENT_TYPE = "video/mpeg";
		if (ext.compareTo("mpeg") == 0)
			CONTENT_TYPE = "video/mpeg";
		if (ext.compareTo("mpega") == 0)
			CONTENT_TYPE = "audio/x-mpeg";
		if (ext.compareTo("mpg") == 0)
			CONTENT_TYPE = "video/mpeg";
		if (ext.compareTo("mpv2") == 0)
			CONTENT_TYPE = "video/mpeg2";
		if (ext.compareTo("divx") == 0)
			CONTENT_TYPE = "video/mpeg4";

		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-disposition", "attachment;filename=" + filename);
		java.nio.channels.WritableByteChannel strout = java.nio.channels.Channels
				.newChannel(response.getOutputStream());
		java.io.FileInputStream fInStreem = null;
		java.nio.channels.FileChannel infileChannel = null;
		java.nio.ByteBuffer buff = null;
		if (fileDownload.getPath() != null && fileDownload.getPath().length() > 0) {

			try {
				fInStreem = new java.io.FileInputStream(fileDownload.getPath());
				infileChannel = fInStreem.getChannel();
				buff = java.nio.ByteBuffer.allocate(2048);
				long count = infileChannel.size();
				while (count > 0) {
					// ///////if(limmit < downloadzise) break ;
					count = count - 2048;
					infileChannel.read(buff);
					buff.rewind();
					strout.write(buff);
					buff.rewind();
				}

				/// setPassiveRow(soft_id);
				AuthorizationPageBeanId.setStrMessage(
						filename + " " + localization.getString("download_servlet_by_order.has_downloaded"));
			} catch (Exception e) {
				log.error(e);
			} finally {
				if (infileChannel != null)
					infileChannel.close();
				if (fInStreem != null)
					fInStreem.close();
				if (strout != null)
					strout.close();
				if (buff != null)
					buff.clear();
			}
			return;
		}

		/*
		 * if (ext.compareTo("jad") == 0) { String jad = getBObj(strout, false); //
		 * servletoutputstream = response.getOutputStream(); int sjad =
		 * jad.indexOf("MIDlet-Jar-URL:"); String jad1 = jad.substring(0, sjad +
		 * "MIDlet-Jar-URL:".length()); int ejad = jad.indexOf("MIDlet-Name:"); String
		 * jad2 = jad.substring(ejad); String makejar = filename.substring(0,
		 * filename.length() - 1) + "r"; String midl_jar_url = " midlets/" + makejar +
		 * "\n"; jad = jad1 + midl_jar_url + jad2; java.nio.ByteBuffer buff =
		 * java.nio.ByteBuffer.wrap(jad.getBytes()); strout.write(buff); } else {
		 * getBObj(strout, true);
		 *
		 * } close(); setPassiveRow(soft_id);
		 * AuthorizationPageBeanId.setStrMessage(filename + " " +
		 * setup_resources.getString("download_servlet_by_order.has_downloaded"));
		 */
		// response.sendRedirect("Order.jsp" );
		// return ;
	}

	// Clean up resources
	public void destroy() {

	}

	// protected void finalize() throws Throwable {
	// close();
	// super.finalize();
	// }

	/**
	 * Returns a short description of the servlet.
	 */
	public String getServletInfo() {
		return "Short description";
	}

	public FileDownload setFileNameByFileID(String fileId) {
		FileDownload fileDownload = new FileDownload();
		fileDownload.setFileId(fileId);
		QueryManager Adp = new QueryManager();
		// FIX: fileId is request-derived and concatenated unquoted; constrain to digits.
		String query = "select name , path  from file  where  file_id  = "
				+ com.cbsinc.cms.utils.Validation.requireNumericId(fileId);
		try {
			Adp.executeQuery(query);

			if (Adp.rows().size() != 0) {
				fileDownload.setName(Adp.getValueAt(0, 0));
				fileDownload.setPath(Adp.getValueAt(0, 1));
			}

		} catch (SQLException ex) {

			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}

		return fileDownload;
	}

	public FileDownload setFileNameByProductId(String productId) {
		FileDownload fileDownload = new FileDownload();
		QueryManager Adp = new QueryManager();
		String query = "select file.name , file.path , file.file_id from soft LEFT  JOIN file  ON  soft.file_id = file.file_id   where  soft.soft_id  = "
				+ productId;
		String path = "";
		try {
			Adp.executeQuery(query);

			if (Adp.rows().size() != 0) {
				fileDownload.setName(Adp.getValueAt(0, 0));
				path = Adp.getValueAt(0, 1);
				if (!path.startsWith("/"))
					path = "/" + path;
				fileDownload.setPath(path);
				fileDownload.setFileId(Adp.getValueAt(0, 2));
			}

		} catch (SQLException ex) {

			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}

		return fileDownload;
	}

	public void setPassiveRow(String softId) {
		QueryManager Adp = new QueryManager();
		String query = "UPDATE soft SET active = true  WHERE soft_id = " + softId;
		// select 0 AS test ;
		try {
			Adp.executeUpdate(query);
		} catch (SQLException ex) {

			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}

		return;
	}

}
