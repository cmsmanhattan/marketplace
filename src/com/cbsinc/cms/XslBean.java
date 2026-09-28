package com.cbsinc.cms;

import java.io.File;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.cbsinc.cms.utils.FileStorage;

import jakarta.servlet.http.HttpServletRequest;

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

public class XslBean extends com.cbsinc.cms.WebControls implements java.io.Serializable {

	private static final long serialVersionUID = 1148781987253439919L;

	static private Logger log = Logger.getLogger(XslBean.class);

	private String xslSubjId = "";

	private String xslStyleId = "";

	private String dirname = "";

	public String getUserDir(HttpServletRequest req) {
		File file = null;
		StringBuffer table = new StringBuffer();

		//String path = this.getClass().getResource("").getPath();
		//path = path.substring(0, path.indexOf("/WEB-INF/"));
		String path = FileStorage.getInstance().getPath() ;
		file = new File(path + "//xsl//" + req.getServerName());

		if (!file.exists())
			file.mkdir();
		File[] filesName = file.listFiles();

		for (int i = 0; i < filesName.length; i++) {
			table.append("<BR><STRONG><U><FONT color=\"#000099\">" + filesName[i].getName()
					+ "....................................." + new java.util.Date(filesName[i].lastModified())
					+ "</FONT></U></STRONG>\n");
		}

		return table.toString();
	}

	public String getXslSubjId() {
		return xslSubjId;
	}

	public void setXslSubjId(String xslSubjId) {
		this.xslSubjId = xslSubjId;
	}

	public String getXslStyleId() {
		return xslStyleId;
	}

	public void setXslStyleId(String xslStyleId) {
		this.xslStyleId = xslStyleId;
	}

	public String getDirname() {
		return dirname;
	}

	public void setDirname(String dirname) {
		this.dirname = dirname;
		// validate dirname
		if (!isXslSubjIdChange(xslStyleId))
			this.dirname = getFirstDirnameByXslSubjId(xslSubjId);
		// setDirname(xslBeanId.getDirnameBy( xslBeanId.getXsl_style_id() )) ;
	}

	public String getDir(HttpServletRequest req, String folder) {
		// / if(!isXsl_subj_idChange(xsl_style_id))return "";
		// SELECT public.xsl_style.xsl_style_id, public.xsl_style.name FROM
		// public.xsl_style WHERE public.xsl_style.active = true AND
		// public.xsl_style.xsl_subj_id = "+ xslBeanId.getXsl_subj_id()
		// if(!isXsl_subj_idChange(xsl_style_id)) folder =
		// getFirstDirnameByXsl_subj_id (xsl_subj_id) ;

		File file = null;
		StringBuffer table = new StringBuffer();

		//String path = this.getClass().getResource("").getPath();
		//path = path.substring(0, path.indexOf("/WEB-INF/"));
		String path = FileStorage.getInstance().getPath() ;
		file = new File(path + "//xsl//" + folder);

		if (!file.exists())
			file.mkdir();
		File[] filesName = file.listFiles();

		for (int i = 0; i < filesName.length; i++) {
			table.append("<BR><STRONG><U><FONT color=\"#000099\">" + filesName[i].getName()
					+ "....................................." + new java.util.Date(filesName[i].lastModified())
					+ "</FONT></U></STRONG>\n");
		}

		return table.toString();
	}

	public boolean isXslSubjIdChange(String xslStyleId) {
		boolean b = true;
		String subjId = "-1";
		// /String query = "SELECT public.xsl_style.dirname FROM
		// public.xsl_style WHERE public.xsl_style.xsl_style_id = " +
		// xsl_style_id ;
		String query = "SELECT  public.xsl_style.xsl_subj_id FROM public.xsl_style WHERE   public.xsl_style.xsl_style_id = "
				+ xslStyleId;

		QueryManager Adp = new QueryManager();
		// Adp.BeginTransaction();
		try {
			Adp.executeQuery(query);

			if (Adp.rows().size() != 0)
				subjId = (String) Adp.getValueAt(0, 0);

			Adp.close();
			if (xslSubjId.compareTo(subjId) != 0)
				b = false;
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return b;
	}

	public String getFirstDirnameByXslSubjId(String subjId) {
		String folder = "";
		String query = "SELECT  public.xsl_style.dirname   FROM  public.xsl_style WHERE   public.xsl_style.active = true  AND public.xsl_style.xsl_subj_id = "
				+ subjId;
		// String query = "SELECT public.xsl_style.xsl_subj_id FROM
		// public.xsl_style WHERE public.xsl_style.xsl_style_id = "+
		// xsl_style_id ;

		QueryManager Adp = new QueryManager();
		// Adp.BeginTransaction();
		try {
			Adp.executeQuery(query);

			if (Adp.rows().size() != 0)
				folder = (String) Adp.getValueAt(0, 0);

		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}

		return folder;
	}

	public String getDirnameBy(String xslStyleId) {
		String dirname = "default";
		String query = "SELECT  public.xsl_style.dirname FROM  public.xsl_style  WHERE  public.xsl_style.xsl_style_id = "
				+ xslStyleId;

		QueryManager Adp = new QueryManager();
		// Adp.BeginTransaction();
		try {
			Adp.executeQuery(query);

			if (Adp.rows().size() != 0)
				dirname = (String) Adp.getValueAt(0, 0);

		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		// this.dirname = dirname;
		return dirname;

	}

	public void initFields(String siteId) {
		if (xslSubjId.length() == 0 && xslStyleId.length() == 0 && dirname.length() == 0) {
			String query = "SELECT  public.xsl_style.xsl_style_id, public.xsl_style.dirname, public.xsl_style.xsl_subj_id, public.xsl_style.site_id FROM public.xsl_style WHERE public.xsl_style.site_id = "
					+ siteId;
			QueryManager Adp = new QueryManager();
			// Adp.BeginTransaction();
			try {
				Adp.executeQuery(query);

				if (Adp.rows().size() != 0) {
					xslStyleId = (String) Adp.getValueAt(0, 0);
					dirname = (String) Adp.getValueAt(0, 1);
					xslSubjId = (String) Adp.getValueAt(0, 2);
				}
			} catch (SQLException ex) {
				log.error(query, ex);
			} catch (Exception ex) {
				log.error(ex);
			} finally {
				Adp.close();
			}

		}
	}

	public void addDir(String siteId) {
		if (xslSubjId.length() == 0 && xslStyleId.length() == 0 && dirname.length() == 0) {
			String query = "SELECT  public.xsl_style.xsl_style_id, public.xsl_style.dirname, public.xsl_style.xsl_subj_id, public.xsl_style.site_id FROM public.xsl_style WHERE public.xsl_style.site_id = "
					+ siteId;
			QueryManager Adp = new QueryManager();
			// Adp.BeginTransaction();
			try {
				Adp.executeQuery(query);

				if (Adp.rows().size() != 0) {
					xslStyleId = (String) Adp.getValueAt(0, 0);
					dirname = (String) Adp.getValueAt(0, 1);
					xslSubjId = (String) Adp.getValueAt(0, 2);
				}
			} catch (SQLException ex) {
				log.error(query, ex);
			} catch (Exception ex) {
				log.error(ex);
			} finally {
				Adp.close();
			}

		}
	}

}
