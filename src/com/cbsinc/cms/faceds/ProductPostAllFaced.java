package com.cbsinc.cms.faceds;

import com.cbsinc.cms.utils.FileNames;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.PostType;
import com.cbsinc.cms.PublisherBean;
import com.cbsinc.cms.QueryManager;
import com.cbsinc.cms.controllers.AuctionBidStatus;
import com.cbsinc.cms.controllers.Layout;
import com.cbsinc.cms.controllers.OfferStatus;
import com.cbsinc.cms.controllers.SpecialCatalog;
import com.cbsinc.cms.utils.FileStorage;
import com.cbsinc.cms.utils.Validation;

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

public class ProductPostAllFaced extends com.cbsinc.cms.WebControls {

	final ResourceBundle sequencesRs = PropertyResourceBundle.getBundle("sequence");
	final ResourceBundle setupResources = PropertyResourceBundle.getBundle("appconfig");

	public ProductPostAllFaced() {
	}

	/**
	 *
	 */

	static private Logger log = Logger.getLogger(PublisherBean.class);

	/**
	 * Resolves the card a review must be attached to. Returns the id of the
	 * top-level card (if the given id is itself a sub-card, its tree_id is
	 * used) provided that card exists on this site, is active and has the
	 * review block switched on; otherwise "".
	 */
	final public String resolveReviewParent(final String candidateId, final String siteId) {
		if (candidateId == null || !candidateId.matches("\\d{1,18}") || siteId == null || !siteId.matches("-?\\d{1,18}"))
			return "";
		QueryManager qm = new QueryManager();
		try {
			qm.executeQuery("select soft_id , tree_id from soft where soft_id = " + candidateId + " and site_id = " + siteId);
			if (qm.rows().size() == 0)
				return "";
			String tree = qm.getValueAt(0, 1);
			String parent = (tree == null || tree.trim().length() == 0 || tree.trim().equals("0")) ? candidateId : tree.trim();
			qm.executeQuery("select soft_id from soft where soft_id = " + parent + " and site_id = " + siteId
					+ " and active = true and show_blog = true");
			return qm.rows().size() > 0 ? parent : "";
		} catch (Exception ex) {
			log.error("resolveReviewParent " + candidateId, ex);
			return "";
		} finally {
			qm.close();
		}
	}

	/**
	 * True only if softId is a review (portlettype 3) of parentId written by
	 * userId. Used before letting a POST turn into an UPDATE.
	 */
	final public boolean isOwnReview(final String softId, final String parentId, final long userId) {
		if (softId == null || !softId.matches("\\d{1,18}") || parentId == null || !parentId.matches("\\d{1,18}"))
			return false;
		QueryManager qm = new QueryManager();
		try {
			qm.executeQuery("select soft_id from soft where soft_id = " + softId + " and tree_id = " + parentId
					+ " and portlettype_id = " + com.cbsinc.cms.controllers.Layout.REVIEW_MESSAGES + " and user_id = " + userId);
			return qm.rows().size() > 0;
		} catch (Exception ex) {
			log.error("isOwnReview " + softId, ex);
			return false;
		} finally {
			qm.close();
		}
	}

	/**
	 * Loads the product (soft row) identified by _soft_id into the publisher bean for editing.
	 */
	final public void initPage(final String softId, final PublisherBean publisherBeanId,
			final AuthorizationPageBean authorizationPageBeanId) throws UnsupportedEncodingException {

		/*
		 * if( publisherBeanId.position_cd == 0 ) return false ; if(
		 * publisherBeanId.cost == 0 ) return false ; if( publisherBeanId.currency_cd ==
		 * 0 ) return false ; if( publisherBeanId.countposition == 0 ) return false ;
		 * if( publisherBeanId.deliverylength_ofday == 0 ) return false ; if(
		 * publisherBeanId.producer_cd == 0 ) return false ;
		 */

		/*
		 *
		 * + " amount1 = " + publisherBeanId.getAmount1() + " , " + " amount2 = " +
		 * publisherBeanId.getAmount2() + " , " + " amount3 = " +
		 * publisherBeanId.getAmount3() + " , " + " search2 = '" + search2 + "' , " +
		 * " name2 = '" + publisherBeanId.getStrSoftName2() + "' , " +
		 * " show_rating1 = " + publisherBeanId.getStrShow_ratimg1() + " , " +
		 * " show_rating2 = " + publisherBeanId.getStrShow_ratimg1() + " , " +
		 * " show_rating3 = " + publisherBeanId.getStrShow_ratimg1() + " , " +
		 * " show_blog = " + publisherBeanId.getStrShow_forum() + " , " + " jsp_url = '"
		 * + publisherBeanId.getJsp_url() + "' , "
		 *
		 */

		/*
		 *
		 * String query =
		 * "select soft_id , name , description , fulldescription ,  version , " +
		 * " cost ,  currency ,  serial_nubmer  ,  file_id ,  catalog_id , " +
		 * " image_id ,  bigimage_id , user_id ,  phonemodel_id ,  salelogic_id , " +
		 * " site_id ,  product_code ,   portlettype_id , creteria1_id , " +
		 * " creteria2_id , creteria3_id , creteria4_id , creteria5_id , creteria6_id , "
		 * + " creteria7_id , creteria8_id , creteria9_id , " +
		 * " creteria10_id from soft where soft_id=" + _soft_id;
		 *
		 */

		QueryManager Adp = new QueryManager();
		// Adp.BeginTransaction();
		String query = "select soft.soft_id , soft.name , soft.description , soft.fulldescription ,  soft.version , "
				+ " soft.cost ,  soft.currency ,  soft.serial_nubmer  ,  soft.file_id ,  soft.catalog_id , "
				+ " soft.image_id ,  soft.bigimage_id , soft.user_id ,  soft.phonemodel_id ,  soft.salelogic_id , "
				+ " soft.site_id ,  soft.product_code ,   soft.portlettype_id , soft.creteria1_id , "
				+ " soft.creteria2_id , soft.creteria3_id , soft.creteria4_id , soft.creteria5_id , soft.creteria6_id , "
				+ " soft.creteria7_id , soft.creteria8_id , soft.creteria9_id , "
				+ " soft.creteria10_id , soft.amount1 , soft.amount2 , soft.amount3 , "
				+ " soft.search2 , soft.name2 , soft.show_rating1 , soft.show_rating2 , soft.show_rating3 , "
				+ " soft.show_blog , soft.jsp_url , images.imgname as imgname_s ,  big_images.imgname as imgname_b  , file.name , soft.type_id ,  soft1.portlettype_id  from soft LEFT  JOIN images ON soft.image_id = images.image_id  LEFT  JOIN soft soft1 ON soft.soft_id = soft1.tree_id LEFT  JOIN big_images ON soft.bigimage_id = big_images.big_images_id  LEFT  JOIN file  ON  soft.file_id = file.file_id  where soft.soft_id="
				+ softId;
		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() > 0) {
				publisherBeanId.setSoftId((String) Adp.getValueAt(0, 0));
				publisherBeanId.setStrSoftName((String) Adp.getValueAt(0, 1));
				publisherBeanId.setStrSoftDescription((String) Adp.getValueAt(0, 2));
				publisherBeanId.setProductFulldescription((String) Adp.getValueAt(0, 3));
				publisherBeanId.setStrSoftVersion((String) Adp.getValueAt(0, 4));
				publisherBeanId.setStrSoftCost((String) Adp.getValueAt(0, 5));
				publisherBeanId.setStrCurrency((String) Adp.getValueAt(0, 6));
				publisherBeanId.setSerialNubmer((String) Adp.getValueAt(0, 7));
				publisherBeanId.setFileId((String) Adp.getValueAt(0, 8));
				authorizationPageBeanId.setCatalogId((String) Adp.getValueAt(0, 9));
				if (authorizationPageBeanId.getCatalogParentId() == 0)
					authorizationPageBeanId.setCatalogParentId((String) Adp.getValueAt(0, 9));
				// authorizationPageBeanId.setCatalog_parent_id(getCatalogParentId(authorizationPageBeanId));
				// // fixed for open editor in dir
				publisherBeanId.setImageId((String) Adp.getValueAt(0, 10));
				publisherBeanId.setBigimageId((String) Adp.getValueAt(0, 11));
				publisherBeanId.setUserId((String) Adp.getValueAt(0, 12));
				publisherBeanId.setSalelogicId((String) Adp.getValueAt(0, 14));
				publisherBeanId.setSiteId((String) Adp.getValueAt(0, 15));
				publisherBeanId.setProductCodeId((String) Adp.getValueAt(0, 16));
				publisherBeanId.setPortlettypeId((String) Adp.getValueAt(0, 17));
				publisherBeanId.setCreteria1Id((String) Adp.getValueAt(0, 18));
				publisherBeanId.setCreteria2Id((String) Adp.getValueAt(0, 19));
				publisherBeanId.setCreteria3Id((String) Adp.getValueAt(0, 20));
				publisherBeanId.setCreteria4Id((String) Adp.getValueAt(0, 21));
				publisherBeanId.setCreteria5Id((String) Adp.getValueAt(0, 22));
				publisherBeanId.setCreteria6Id((String) Adp.getValueAt(0, 23));
				publisherBeanId.setCreteria7Id((String) Adp.getValueAt(0, 24));
				publisherBeanId.setCreteria8Id((String) Adp.getValueAt(0, 25));
				publisherBeanId.setCreteria9Id((String) Adp.getValueAt(0, 26));
				publisherBeanId.setCreteria10Id((String) Adp.getValueAt(0, 27));

				publisherBeanId.setAmount1((String) Adp.getValueAt(0, 28));
				publisherBeanId.setAmount2((String) Adp.getValueAt(0, 29));
				publisherBeanId.setAmount3((String) Adp.getValueAt(0, 30));
				publisherBeanId.setStrSearch2((String) Adp.getValueAt(0, 31));
				publisherBeanId.setStrSoftName2((String) Adp.getValueAt(0, 32));
				publisherBeanId.setStrShowRatimg1((String) Adp.getValueAt(0, 33));
				publisherBeanId.setStrShowRatimg2((String) Adp.getValueAt(0, 34));
				publisherBeanId.setStrShowRatimg3((String) Adp.getValueAt(0, 35)); // FIX: was ratimg2 (column 35 is show_rating3)
				publisherBeanId.setStrShowForum((String) Adp.getValueAt(0, 36));
				publisherBeanId.setJspUrl((String) Adp.getValueAt(0, 37));

				publisherBeanId.setImgname((String) Adp.getValueAt(0, 38));
				publisherBeanId.setBigimgname((String) Adp.getValueAt(0, 39));
				publisherBeanId.setFilename((String) Adp.getValueAt(0, 40));
				publisherBeanId.setTypeId((String) Adp.getValueAt(0, 41));
				publisherBeanId.setParentPortlettypeId((String) Adp.getValueAt(0, 42));
			} else {
				// add current catalog
				publisherBeanId.setSoftId("-1");
				if (authorizationPageBeanId.getCatalogParentId() == 0)
					authorizationPageBeanId.setCatalogParentId(authorizationPageBeanId.getCatalogId());
			}

		} catch (SQLException ex) {
			// Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			// Adp.rollback();
			log.error(ex);
		} finally {
			Adp.close();
		}

		if (publisherBeanId.getImageId().length() > 0)
			setImageNameByImageID(publisherBeanId.getImageId(), publisherBeanId);
		if (publisherBeanId.getBigimageId().length() > 0)
			setBigImageNameByImageID(publisherBeanId.getBigimageId(), publisherBeanId);
		if (publisherBeanId.getFileId().length() > 0)
			setFileNameByFileID(publisherBeanId.getFileId(), publisherBeanId);
	}

	/**
	 * @return the parent catalogue id of the user's current catalogue
	 */
	final public String getCatalogParentId(final AuthorizationPageBean authorizationPageBeanId) {
		QueryManager tmpAdp = new QueryManager();
		String query = "";
		String parentId = "0";
		try {

			query = "select a.catalog_id  from catalog a , catalog  b  where   b.parent_id = a.catalog_id  and b.parent_id = ? limit 1";
			tmpAdp.executeQueryWithArgs(query, new Object[] { authorizationPageBeanId.getCatalogId() });
			if (tmpAdp.rows().size() > 0) {
				if (((String) tmpAdp.getValueAt(0, 0)).length() > 0)
					parentId = tmpAdp.getValueAt(0, 0);
				;
			}

			// authorizationPageBeanId.setCatalogParent_id( _parent_id < 0?"0":""
			// +_parent_id ) ;
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			tmpAdp.close();
		}

		return parentId;
	}

	/**
	 * Updates the description of an existing product.
	 * @return the product id
	 */
	final public String updateDescSoft(final PublisherBean publisherBeanId,
			final AuthorizationPageBean authorizationPageBeanId) throws UnsupportedEncodingException {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String id = publisherBeanId.getSoftId();
		String query = "";
		Map args = Adp.getArgs();
		if (publisherBeanId.getSoftId().compareTo("-1") == 0)
			return "";

		if (publisherBeanId.getStrSoftName2() != null && publisherBeanId.getStrSoftName2().length() > 0)
			publisherBeanId.setStrSearch2(publisherBeanId.getStrSoftName2().substring(0, 1));

		// if( publisherBeanId.getType_id().compareTo("0") == 0 ||
		// publisherBeanId.getType_id().length() == 0
		// || publisherBeanId.getType_id().compareTo("2") == 0 )

		try {

			query = " update soft set soft_id = ?, " + " name = ?, " + " description = ?, " + " fulldescription = ?, "
					+ " version = ?, " + " cost = ?, " + " currency = ?, " + " file_id = ?, " + " catalog_id = ?, "
					+ " image_id = ?, " + " bigimage_id = ?, " + " salelogic_id = ?, " + " site_id = ?, "
					+ " product_code = ?, " + " search = ?, " + " type_id = ? , " + " portlettype_id = ? , "
					+ " creteria1_id = ? , " + " creteria2_id = ? , " + " creteria3_id = ? , " + " creteria4_id = ? , "
					+ " creteria5_id = ? , " + " creteria6_id = ? , " + " creteria7_id = ? , " + " creteria8_id = ? , "
					+ " creteria9_id = ? , " + " creteria10_id = ? , " + " show_rating1 = ? , " + " show_rating2 = ? , " + " show_rating3 = ? , " + " show_blog = ? , " + " amount1 = ? , " + " amount2 = ? , "
					+ " amount3 = ? , " + " search2 = ? , " + " name2 = ? , " + " SHOW_RATING1 = ? , "
					+ " SHOW_RATING2 = ? , " + " SHOW_RATING3 = ? , " + " SHOW_BLOG = ? , " + " lang_id = ? , "
					+ " jsp_url = ? , " + " CDATE = ? , " + " SHOW_OFFER = ? , " + " SHOW_ACTION = ? " + " where soft_id = " + publisherBeanId.getSoftId();

			args = Adp.getArgs();
			args.put("soft_id", Long.valueOf(publisherBeanId.getSoftId()));
			args.put("name", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName()));
			args.put("description", Validation.removeSpecificSymbols(publisherBeanId.strSoftDescription));
			args.put("fulldescription", Validation.removeSpecificSymbols(publisherBeanId.productFulldescription));
			args.put("version", publisherBeanId.getStrSoftVersion());
			args.put("cost", Double.valueOf(publisherBeanId.getStrSoftCost()));
			args.put("currency", Long.valueOf(publisherBeanId.getStrCurrency()));
			args.put("file_id", Long.valueOf(publisherBeanId.getFileId()));
			args.put("catalog_id", Long.valueOf(authorizationPageBeanId.getCatalogId()));
			args.put("image_id", Long.valueOf(publisherBeanId.getImageId()));
			args.put("bigimage_id", Long.valueOf(publisherBeanId.getBigimageId()));
			args.put("salelogic_id", Long.valueOf(publisherBeanId.getSalelogicId()));
			args.put("site_id", Long.parseLong(publisherBeanId.getSiteId()));
			args.put("product_code", Long.valueOf(publisherBeanId.getProductCodeId()));
			args.put("search", publisherBeanId.getStrSoftName().substring(0, 1));
			args.put("type_id", Long.valueOf(publisherBeanId.getTypeId()));
			args.put("portlettype_id", Long.valueOf(publisherBeanId.getPortlettypeId()));
			args.put("creteria1_id", Long.valueOf(publisherBeanId.getCreteria1Id()));
			args.put("creteria2_id", Long.valueOf(publisherBeanId.getCreteria2Id()));
			args.put("creteria3_id", Long.valueOf(publisherBeanId.getCreteria3Id()));
			args.put("creteria4_id", Long.valueOf(publisherBeanId.getCreteria4Id()));
			args.put("creteria5_id", Long.valueOf(publisherBeanId.getCreteria5Id()));
			args.put("creteria6_id", Long.valueOf(publisherBeanId.getCreteria6Id()));
			args.put("creteria7_id", Long.valueOf(publisherBeanId.getCreteria7Id()));
			args.put("creteria8_id", Long.valueOf(publisherBeanId.getCreteria8Id()));
			args.put("creteria9_id", Long.valueOf(publisherBeanId.getCreteria9Id()));
			args.put("creteria10_id", Long.valueOf(publisherBeanId.getCreteria10Id()));
			args.put("show_rating1", Boolean.valueOf(publisherBeanId.getStrShowRatimg1()));
			args.put("show_rating2", Boolean.valueOf(publisherBeanId.getStrShowRatimg2()));
			args.put("show_rating3", Boolean.valueOf(publisherBeanId.getStrShowRatimg3()));
			args.put("show_blog", Boolean.valueOf(publisherBeanId.getStrShowForum()));
			args.put("amount1", Double.parseDouble(publisherBeanId.getAmount1()));
			args.put("amount2", Double.parseDouble(publisherBeanId.getAmount2()));
			args.put("amount3", Double.parseDouble(publisherBeanId.getAmount3()));
			args.put("search2", Validation.removeSpecificSymbols(publisherBeanId.getStrSearch2()) );
			args.put("name2", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName2()) );
			args.put("SHOW_RATING1", Boolean.valueOf(publisherBeanId.getStrShowRatimg1()));
			args.put("SHOW_RATING2", Boolean.valueOf(publisherBeanId.getStrShowRatimg2()));
			args.put("SHOW_RATING3", Boolean.valueOf(publisherBeanId.getStrShowRatimg3()));
			args.put("SHOW_BLOG", Boolean.valueOf(publisherBeanId.getStrShowForum()));
			args.put("lang_id", authorizationPageBeanId.getLangId());
			args.put("jsp_url", publisherBeanId.getJspUrl());
			args.put("CDATE", new java.util.Date());
			args.put("SHOW_OFFER", Boolean.valueOf(publisherBeanId.getStrShowOffer()));
			args.put("SHOW_ACTION", Boolean.valueOf(publisherBeanId.getStrShowAction()));

			Adp.executeUpdateWithArgs(query, args);
			Adp.commit();
		} catch (SQLException ex) {
			Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			Adp.rollback();
			log.error(ex);
		} finally {
			Adp.close();
		}

		publisherBeanId.setSoftId("-1");
		return id;
	}

	/**
	 * Updates a tree row (product or block) keeping its parent.
	 * @return the row id
	 */
	final public String updateRowWithParent(final String treeId, final PublisherBean publisherBeanId,
			final AuthorizationPageBean authorizationPageBeanId) throws UnsupportedEncodingException {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String id = publisherBeanId.getSoftId();
		String query = "";
		Map args = Adp.getArgs();
		if (publisherBeanId.getSoftId().compareTo("-1") == 0)
			return "";

		if (publisherBeanId.getStrSoftName2() != null && publisherBeanId.getStrSoftName2().length() > 0)
			publisherBeanId.setStrSearch2(publisherBeanId.getStrSoftName2().substring(0, 1));

		// if( publisherBeanId.getType_id().compareTo("0") == 0 ||
		// publisherBeanId.getType_id().length() == 0 )
		// {
		query = " update soft set soft_id = ?, " + " name = ?, " + " description = ?, " + " fulldescription = ?, "
				+ " version = ?, " + " cost = ?, " + " currency = ?, " + " file_id = ?, " + " catalog_id = ?, "
				+ " image_id = ?, " + " bigimage_id = ?, " + " salelogic_id = ?, " + " site_id = ?, "
				+ " product_code = ?, " + " search = ?, " + " type_id = ? , " + " portlettype_id = ? , "
				+ " tree_id = ? , " + " creteria1_id = ? , " + " creteria2_id = ? , " + " creteria3_id = ? , "
				+ " creteria4_id = ? , " + " creteria5_id = ? , " + " creteria6_id = ? , " + " creteria7_id = ? , "
				+ " creteria8_id = ? , " + " creteria9_id = ? , " + " creteria10_id = ? , " + " show_rating1 = ? , " + " show_rating2 = ? , " + " show_rating3 = ? , " + " show_blog = ? , " + " amount1 = ? , "
				+ " amount2 = ? , " + " amount3 = ? , " + " search2 = ? , " + " name2 = ? , " + " SHOW_RATING1 = ? , "
				+ " SHOW_RATING2 = ? , " + " SHOW_RATING3 = ? , " + " SHOW_BLOG = ? , " + " lang_id = ? , "
				+ " jsp_url = ? , " + " CDATE = ? " + " where soft_id = " + publisherBeanId.getSoftId();

		args.put("soft_id", Long.valueOf(publisherBeanId.getSoftId()));
		args.put("name", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName()) );
		args.put("description", Validation.removeSpecificSymbols(publisherBeanId.strSoftDescription));
		args.put("fulldescription",Validation.removeSpecificSymbols(publisherBeanId.productFulldescription));
		args.put("version", publisherBeanId.getStrSoftVersion());
		args.put("cost", Double.valueOf(publisherBeanId.getStrSoftCost()));
		args.put("currency", Long.valueOf(publisherBeanId.getStrCurrency()));
		args.put("file_id", Long.valueOf(publisherBeanId.getFileId()));
		args.put("catalog_id", Long.valueOf(authorizationPageBeanId.getCatalogId()));
		args.put("image_id", Long.valueOf(publisherBeanId.getImageId()));
		args.put("bigimage_id", Long.valueOf(publisherBeanId.getBigimageId()));
		args.put("salelogic_id", Long.valueOf(publisherBeanId.getSalelogicId()));
		args.put("site_id", Long.parseLong(publisherBeanId.getSiteId()));
		args.put("product_code", Long.valueOf(publisherBeanId.getProductCodeId()));
		args.put("search", publisherBeanId.getStrSoftName().substring(0, 1));
		args.put("type_id", Long.valueOf(publisherBeanId.getTypeId()));
		args.put("portlettype_id", Long.valueOf(publisherBeanId.getPortlettypeId()));
		args.put("tree_id", Long.valueOf(treeId));
		args.put("creteria1_id", Long.valueOf(publisherBeanId.getCreteria1Id()));
		args.put("creteria2_id", Long.valueOf(publisherBeanId.getCreteria2Id()));
		args.put("creteria3_id", Long.valueOf(publisherBeanId.getCreteria3Id()));
		args.put("creteria4_id", Long.valueOf(publisherBeanId.getCreteria4Id()));
		args.put("creteria5_id", Long.valueOf(publisherBeanId.getCreteria5Id()));
		args.put("creteria6_id", Long.valueOf(publisherBeanId.getCreteria6Id()));
		args.put("creteria7_id", Long.valueOf(publisherBeanId.getCreteria7Id()));
		args.put("creteria8_id", Long.valueOf(publisherBeanId.getCreteria8Id()));
		args.put("creteria9_id", Long.valueOf(publisherBeanId.getCreteria9Id()));
		args.put("creteria10_id", Long.valueOf(publisherBeanId.getCreteria10Id()));
		args.put("show_rating1", Boolean.valueOf(publisherBeanId.getStrShowRatimg1()));
		args.put("show_rating2", Boolean.valueOf(publisherBeanId.getStrShowRatimg2()));
		args.put("show_rating3", Boolean.valueOf(publisherBeanId.getStrShowRatimg3()));
		args.put("show_blog", Boolean.valueOf(publisherBeanId.getStrShowForum()));
		args.put("amount1", Double.parseDouble(publisherBeanId.getAmount1()));
		args.put("amount2", Double.parseDouble(publisherBeanId.getAmount2()));
		args.put("amount3", Double.parseDouble(publisherBeanId.getAmount3()));
		args.put("search2", Validation.removeSpecificSymbols(publisherBeanId.getStrSearch2()));
		args.put("name2", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName2()));
		args.put("SHOW_RATING1", Boolean.valueOf(publisherBeanId.getStrShowRatimg1()));
		args.put("SHOW_RATING2", Boolean.valueOf(publisherBeanId.getStrShowRatimg2()));
		args.put("SHOW_RATING3", Boolean.valueOf(publisherBeanId.getStrShowRatimg3()));
		args.put("SHOW_BLOG", Boolean.valueOf(publisherBeanId.getStrShowForum()));
		args.put("lang_id", authorizationPageBeanId.getLangId());
		args.put("jsp_url", publisherBeanId.getJspUrl());
		args.put("CDATE", new java.util.Date());
		// }
//	if( publisherBeanId.getType_id().compareTo("2") == 0  )
//	{
//		query = " update soft set soft_id = ?, "
//			+ " name = ?, "
//			+ " description = ?, "
//			+ " fulldescription = ?, "
//			+ " version = ?, "
//			+ " cost = ?, "
//			+ " currency = ?, "
//			+ " file_id = ?, "
//			+ " catalog_id = ?, "
//			+ " image_id = ?, "
//			+ " bigimage_id = ?, "
//			+ " salelogic_id = ?, "
//			+ " site_id = ?, "
//			+ " product_code = ?, "
//			+ " search = ?, "
//			+ " type_id = ? , "
//			+ " portlettype_id = ? , "
//			+ " creteria1_id = ? , "
//			+ " creteria2_id = ? , "
//			+ " creteria3_id = ? , "
//			+ " creteria4_id = ? , "
//			+ " creteria5_id = ? , "
//			+ " creteria6_id = ? , "
//			+ " creteria7_id = ? , "
//			+ " creteria8_id = ? , "
//			+ " creteria9_id = ? , "
//			+ " creteria10_id = ? , "
//			+ " amount1 = ? , "
//			+ " amount2 = ? , "
//			+ " amount3 = ? , "
//			+ " search2 = ? , "
//			+ " name2 = ? , "
//			+ " SHOW_RATING1 = ? , "
//			+ " SHOW_RATING2 = ? , "
//			+ " SHOW_RATING3 = ? , "
//			+ " SHOW_BLOG = ? , "
//			+ " jsp_url = ? , "
//			+ " CDATE = ? "
//			+ " where soft_id = " + publisherBeanId.getSoft_id() ;
//
//
//
//	args.put("soft_id" , Long.valueOf(publisherBeanId.getSoft_id()));
//	args.put("name" , publisherBeanId.getStrSoftName());
//	args.put("description" , publisherBeanId.strSoftDescription );
//	args.put("fulldescription", publisherBeanId.product_fulldescription );
//	args.put("version" , publisherBeanId.getStrSoftVersion() );
//	args.put("cost"	, Double.valueOf(publisherBeanId.getStrSoftCost()) );
//	args.put("currency" , Long.valueOf(publisherBeanId.getStrCurrency()) );
//	args.put("file_id" , Long.valueOf(publisherBeanId.getFile_id() ));
//	args.put("catalog_id", Long.valueOf(publisherBeanId.getCatalog_id()) );
//	args.put("image_id" , Long.valueOf(publisherBeanId.getImage_id()));
//	args.put("bigimage_id" , Long.valueOf(publisherBeanId.getBigimage_id() ));
//	args.put("salelogic_id" , Long.valueOf(publisherBeanId.getSalelogic_id()) );
//	args.put("site_id" , Long.parseLong(publisherBeanId.getSite_id()) );
//	args.put("product_code" , Long.valueOf(publisherBeanId.getProduct_code_id() ));
//	args.put("search", publisherBeanId.getStrSoftName().substring(0, 1) );
//	args.put("type_id" , Long.valueOf(publisherBeanId.getType_id()) );
//	args.put("portlettype_id" ,Long.valueOf(publisherBeanId.getPortlettype_id()) );
//	args.put("creteria1_id" , Long.valueOf(publisherBeanId.getCreteria1_id()));
//	args.put("creteria2_id" , Long.valueOf(publisherBeanId.getCreteria2_id()));
//	args.put("creteria3_id" , Long.valueOf(publisherBeanId.getCreteria3_id()));
//	args.put("creteria4_id" , Long.valueOf(publisherBeanId.getCreteria4_id()));
//	args.put("creteria5_id" , Long.valueOf(publisherBeanId.getCreteria5_id()));
//	args.put("creteria6_id" , Long.valueOf(publisherBeanId.getCreteria6_id()));
//	args.put("creteria7_id" , Long.valueOf(publisherBeanId.getCreteria7_id()));
//	args.put("creteria8_id" , Long.valueOf(publisherBeanId.getCreteria8_id()));
//	args.put("creteria9_id" , Long.valueOf(publisherBeanId.getCreteria9_id()));
//	args.put("creteria10_id" , Long.valueOf(publisherBeanId.getCreteria10_id()));
//	args.put("amount1" , Double.parseDouble(publisherBeanId.getAmount1()));
//	args.put("amount2" , Double.parseDouble(publisherBeanId.getAmount2()));
//	args.put("amount3" , Double.parseDouble(publisherBeanId.getAmount3()));
//	args.put("search2" , publisherBeanId.getStrSearch2() );
//	args.put("name2" , publisherBeanId.getStrSoftName2() );
//	args.put("SHOW_RATING1" , Boolean.valueOf(publisherBeanId.getStrShow_ratimg1()));
//	args.put("SHOW_RATING2" , Boolean.valueOf(publisherBeanId.getStrShow_ratimg2()));
//	args.put("SHOW_RATING3" , Boolean.valueOf(publisherBeanId.getStrShow_ratimg3()));
//	args.put("SHOW_BLOG" , Boolean.valueOf(publisherBeanId.getStrShow_forum()));
//	args.put("jsp_url" , publisherBeanId.getJsp_url() );
//	args.put("CDATE", new java.util.Date() );
//	}
		try {
			Adp.executeUpdateWithArgs(query, args);
			Adp.commit();
		} catch (SQLException ex) {
			Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			Adp.rollback();
			log.error(ex);
		} finally {
			Adp.close();
		}

		publisherBeanId.setSoftId("-1");
		return id;
	}

	/**
	 * Inserts a new product with its description.
	 * @return the new product id
	 */
	final public String saveDescSoft(final PublisherBean publisherBeanId,
			final AuthorizationPageBean authorizationPageBeanId) throws UnsupportedEncodingException {

		if (publisherBeanId.getStrSoftName2() != null && publisherBeanId.getStrSoftName2().length() > 0)
			publisherBeanId.setStrSearch2(publisherBeanId.getStrSoftName2().substring(0, 1));

		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String strID = null;
		// String query = "SELECT NEXT VALUE FOR soft_id_seq AS ID FROM ONE_SEQUENCES";
		String query = "";
		try {

			query = sequencesRs.getString("soft");
			Adp.executeQuery(query);

			strID = Adp.getValueAt(0, 0);
			// SHOW_RATING1
			query = "insert into soft ( soft_id , " + " name , " + " description , " + " fulldescription , "
					+ " version , " + " cost , " + " currency , " + " file_id , " + " catalog_id , " + " active , "
					+ " licence_id  , " + " image_id , " + " bigimage_id , " + " user_id , " + " salelogic_id ,"
					+ " site_id , " + " product_code , " + " search,  " + " portlettype_id  ,  " + " type_id  ,  "
					+ " creteria1_id ,  " + " creteria2_id ,  " + " creteria3_id ,  " + " creteria4_id ,  "
					+ " creteria5_id ,  " + " creteria6_id ,  " + " creteria7_id ,  " + " creteria8_id ,  "
					+ " creteria9_id ,  " + " creteria10_id , " + " show_rating1 ,  " + " show_rating2 ,  "
					+ " show_rating3 ,  " + " show_blog ,  " + " search2 ,  " + " amount1 , " + " amount2 , "
					+ " amount3 , " + " name2 ," + " lang_id ," + " jsp_url ," + " SHOW_OFFER ," + " SHOW_ACTION ) " + " VALUES ( ? , " + " ? , " + " ? , "
					+ " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , "
					+ " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? ,  "
					+ " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  "
					+ " ? ,  " + " ? ,  " + " ? , " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? , "+ " ? , "+ " ? , " + " ?  )";

			Map args = Adp.getArgs();
			args.put("soft_id", Long.valueOf(strID));
			args.put("name", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName()) );
			args.put("description", Validation.removeSpecificSymbols(publisherBeanId.strSoftDescription));
			args.put("fulldescription",Validation.removeSpecificSymbols(publisherBeanId.productFulldescription) );
			args.put("version", publisherBeanId.getStrSoftVersion());
			args.put("cost", Double.valueOf(publisherBeanId.getStrSoftCost()));
			args.put("currency", Long.valueOf(publisherBeanId.getStrCurrency()));
			args.put("file_id", Long.valueOf(publisherBeanId.getFileId()));
			args.put("catalog_id", Long.valueOf(authorizationPageBeanId.getCatalogId()));
			args.put("active", true);
			args.put("licence_id", Long.valueOf(publisherBeanId.getLicenceId()));
			args.put("image_id", Long.valueOf(publisherBeanId.getImageId()));
			args.put("bigimage_id", Long.valueOf(publisherBeanId.getBigimageId()));
			args.put("user_id", Long.valueOf(publisherBeanId.getUserId()));
			args.put("salelogic_id", Long.valueOf(publisherBeanId.getSalelogicId()));
			args.put("site_id", Long.valueOf(authorizationPageBeanId.getSiteId()));
			args.put("product_code", Long.valueOf(publisherBeanId.getProductCodeId()));
			args.put("search", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName().substring(0, 1)));
			args.put("portlettype_id", Long.valueOf(publisherBeanId.getPortlettypeId()));
			if (authorizationPageBeanId.getCatalogId().equals("-2"))
				args.put("type_id", 3);
			else
				args.put("type_id", Long.valueOf(publisherBeanId.getTypeId()));
			args.put("creteria1_id", Long.valueOf(publisherBeanId.getCreteria1Id()));
			args.put("creteria2_id", Long.valueOf(publisherBeanId.getCreteria2Id()));
			args.put("creteria3_id", Long.valueOf(publisherBeanId.getCreteria3Id()));
			args.put("creteria4_id", Long.valueOf(publisherBeanId.getCreteria4Id()));
			args.put("creteria5_id", Long.valueOf(publisherBeanId.getCreteria5Id()));
			args.put("creteria6_id", Long.valueOf(publisherBeanId.getCreteria6Id()));
			args.put("creteria7_id", Long.valueOf(publisherBeanId.getCreteria7Id()));
			args.put("creteria8_id", Long.valueOf(publisherBeanId.getCreteria8Id()));
			args.put("creteria9_id", Long.valueOf(publisherBeanId.getCreteria9Id()));
			args.put("creteria10_id", Long.valueOf(publisherBeanId.getCreteria10Id()));
			args.put("show_rating1", Boolean.valueOf(publisherBeanId.getStrShowRatimg1()));
			args.put("show_rating2", Boolean.valueOf(publisherBeanId.getStrShowRatimg2()));
			args.put("show_rating3", Boolean.valueOf(publisherBeanId.getStrShowRatimg3()));
			args.put("show_blog", Boolean.valueOf(publisherBeanId.getStrShowForum()));
			args.put("search2", Validation.removeSpecificSymbols(publisherBeanId.getStrSearch2()));
			args.put("amount1", Double.valueOf(publisherBeanId.getAmount1()));
			args.put("amount2", Double.valueOf(publisherBeanId.getAmount2()));
			args.put("amount3", Double.valueOf(publisherBeanId.getAmount3()));
			args.put("name2", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName2()));
			args.put("lang_id", authorizationPageBeanId.getLangId());
			args.put("jsp_url", publisherBeanId.getJspUrl());
			args.put("SHOW_OFFER", Boolean.valueOf(publisherBeanId.getStrShowOffer()));
			args.put("SHOW_ACTION", Boolean.valueOf(publisherBeanId.getStrShowAction()));

			Adp.executeInsertWithArgs(query, args);
			Adp.commit();
		} catch (SQLException ex) {
			Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			Adp.rollback();
			log.error(ex);
		} finally {
			Adp.close();
		}

		return strID;
	}

	/**
	 * Inserts a tree row under the given parent.
	 * @return the new row id
	 */
	final public String insertRowWithParent(final String treeId, final PublisherBean publisherBeanId,
			final AuthorizationPageBean authorizationPageBeanId) throws UnsupportedEncodingException {

		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String strID = "";
		// String query = "SELECT NEXT VALUE FOR soft_id_seq AS ID FROM ONE_SEQUENCES";
		String query = sequencesRs.getString("soft");
		try {

			Adp.executeQuery(query);

			strID = Adp.getValueAt(0, 0);

			query = "insert into soft ( soft_id , " + " name , " + " description , " + " fulldescription , "
					+ " version , " + " cost , " + " currency , " + " file_id , " + " catalog_id , " + " active , "
					+ " licence_id  , " + " image_id , " + " bigimage_id , " + " user_id , " + " salelogic_id ,"
					+ " site_id , " + " product_code , " + " search,  " + " portlettype_id  ,  " + " type_id  ,  "
					+ " tree_id  ,  " + " creteria1_id ,  " + " creteria2_id ,  " + " creteria3_id ,  "
					+ " creteria4_id ,  " + " creteria5_id ,  " + " creteria6_id ,  " + " creteria7_id ,  "
					+ " creteria8_id ,  " + " creteria9_id ,  " + " creteria10_id , " + " show_rating1 ,  "
					+ " show_rating2 ,  " + " show_rating3 ,  " + " show_blog ,  " + " search2 ,  " + " amount1 , "
					+ " amount2 , " + " amount3 , " + " name2 ," + " lang_id ," + " jsp_url  ) " + " VALUES ( ? , "
					+ " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , "
					+ " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , "
					+ " ? , " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  "
					+ " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? , " + " ? ,  " + " ? ,  " + " ? ,  "
					+ " ? , " + " ? , " + " ?  )";

			Map args = Adp.getArgs();
			args.put("soft_id", Long.valueOf(strID));
			args.put("name", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName()));
			args.put("description", Validation.removeSpecificSymbols(publisherBeanId.strSoftDescription) );
			args.put("fulldescription", Validation.removeSpecificSymbols(publisherBeanId.productFulldescription));
			args.put("version", publisherBeanId.getStrSoftVersion());
			args.put("cost", Double.valueOf(publisherBeanId.getStrSoftCost()));
			args.put("currency", Long.valueOf(publisherBeanId.getStrCurrency()));
			args.put("file_id", Long.valueOf(publisherBeanId.getFileId()));
			args.put("catalog_id", Long.valueOf(authorizationPageBeanId.getCatalogId()));
			args.put("active", true);
			args.put("licence_id", Long.valueOf(publisherBeanId.getLicenceId()));
			args.put("image_id", Long.valueOf(publisherBeanId.getImageId()));
			args.put("bigimage_id", Long.valueOf(publisherBeanId.getBigimageId()));
			args.put("user_id", Long.valueOf(publisherBeanId.getUserId()));
			args.put("salelogic_id", Long.valueOf(publisherBeanId.getSalelogicId()));
			args.put("site_id", Long.valueOf(publisherBeanId.getSiteId()));
			args.put("product_code", Long.valueOf(publisherBeanId.getProductCodeId()));
			args.put("search", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName().substring(0, 1)));
			args.put("portlettype_id", Long.valueOf(publisherBeanId.getPortlettypeId()));
			args.put("type_id", Long.valueOf(publisherBeanId.getTypeId()));
			args.put("tree_id", Long.valueOf(treeId));
			args.put("creteria1_id", Long.valueOf(publisherBeanId.getCreteria1Id()));
			args.put("creteria2_id", Long.valueOf(publisherBeanId.getCreteria2Id()));
			args.put("creteria3_id", Long.valueOf(publisherBeanId.getCreteria3Id()));
			args.put("creteria4_id", Long.valueOf(publisherBeanId.getCreteria4Id()));
			args.put("creteria5_id", Long.valueOf(publisherBeanId.getCreteria5Id()));
			args.put("creteria6_id", Long.valueOf(publisherBeanId.getCreteria6Id()));
			args.put("creteria7_id", Long.valueOf(publisherBeanId.getCreteria7Id()));
			args.put("creteria8_id", Long.valueOf(publisherBeanId.getCreteria8Id()));
			args.put("creteria9_id", Long.valueOf(publisherBeanId.getCreteria9Id()));
			args.put("creteria10_id", Long.valueOf(publisherBeanId.getCreteria10Id()));
			args.put("show_rating1", Boolean.valueOf(publisherBeanId.getStrShowRatimg1()));
			args.put("show_rating2", Boolean.valueOf(publisherBeanId.getStrShowRatimg2()));
			args.put("show_rating3", Boolean.valueOf(publisherBeanId.getStrShowRatimg3()));
			args.put("show_blog", Boolean.valueOf(publisherBeanId.getStrShowForum()));
			args.put("search2", Validation.removeSpecificSymbols(publisherBeanId.getStrSearch2()));
			args.put("amount1", Double.valueOf(publisherBeanId.getAmount1()));
			args.put("amount2", Double.valueOf(publisherBeanId.getAmount2()));
			args.put("amount3", Double.valueOf(publisherBeanId.getAmount3()));
			args.put("name2", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName2()) );
			args.put("lang_id", authorizationPageBeanId.getLangId());
			args.put("jsp_url", publisherBeanId.getJspUrl());

			Adp.executeInsertWithArgs(query, args);
			Adp.commit();
		} catch (SQLException ex) {
			Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			Adp.rollback();
			log.error(ex);
		} finally {
			Adp.close();
		}

		return strID;
	}

	/**
	 * Offer guard for do_offer: the product must exist on this site, be active,
	 * accept offers (show_offer) and not belong to the offerer.
	 */
	final public boolean offerAllowedOn(final String productId, final String siteId, final long userId) {
		if (productId == null || !productId.matches("\\d{1,18}") || siteId == null || !siteId.matches("-?\\d{1,18}"))
			return false;
		QueryManager qm = new QueryManager();
		try {
			qm.executeQuery("select soft.user_id , site.owner from soft join site on site.site_id = soft.site_id where soft.soft_id = "
					+ productId + " and soft.site_id = " + siteId + " and soft.active = true and soft.show_offer = true and soft.tree_id is null");
			if (qm.rows().size() == 0)
				return false;
			String u = "" + userId;
			return !u.equals(qm.getValueAt(0, 0)) && !u.equals(qm.getValueAt(0, 1));
		} catch (Exception ex) {
			log.error("offerAllowedOn " + productId, ex);
			return false;
		} finally {
			qm.close();
		}
	}

	/** Which side of an offer the user is. */
	public static final String OFFER_PARTY_SELLER = "seller", OFFER_PARTY_BUYER = "buyer";

	/**
	 * Returns { party , status , counterAmount } for an offer on this site, or
	 * null if offerId is not an offer here or the user is neither the buyer
	 * (who submitted it) nor the seller (product author or site owner).
	 */
	final public String[] offerParty(final String offerId, final String siteId, final long userId) {
		if (offerId == null || !offerId.matches("\\d{1,18}") || siteId == null || !siteId.matches("-?\\d{1,18}"))
			return null;
		QueryManager qm = new QueryManager();
		try {
			qm.executeQuery("select o.user_id , p.user_id , site.owner , o.OFFER_STATUS_ID , o.amount1 from soft o"
					+ " join soft p on p.soft_id = o.tree_id join site on site.site_id = o.site_id"
					+ " where o.soft_id = " + offerId + " and o.site_id = " + siteId + " and o.catalog_id = " + SpecialCatalog.OFFERS_CATALOG);
			if (qm.rows().size() == 0)
				return null;
			String u = "" + userId, status = qm.getValueAt(0, 3), counter = qm.getValueAt(0, 4);
			if (u.equals(qm.getValueAt(0, 1)) || u.equals(qm.getValueAt(0, 2)))
				return new String[] { OFFER_PARTY_SELLER, status, counter };
			if (u.equals(qm.getValueAt(0, 0)))
				return new String[] { OFFER_PARTY_BUYER, status, counter };
			return null;
		} catch (Exception ex) {
			log.error("offerParty " + offerId, ex);
			return null;
		} finally {
			qm.close();
		}
	}

	/**
	 * Seller's counter-offer: stores the counter amount in soft.amount1 and
	 * moves the offer to PRODUCT_COUNTER_OFFER. The buyer's original price
	 * stays in soft.cost until the counter is accepted.
	 */
	final public boolean storeCounterOffer(final String offerId, final String counterAmount) {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String query = "update soft set amount1 = ? , OFFER_STATUS_ID = ? where soft_id = ?";
		try {
			Map args = Adp.getArgs();
			args.put("amount1", Double.valueOf(counterAmount));
			args.put("OFFER_STATUS_ID", Long.valueOf(OfferStatus.PRODUCT_COUNTER_OFFER));
			args.put("soft_id", Long.valueOf(offerId));
			Adp.executeUpdateWithArgs(query, args);
			Adp.commit();
			return true;
		} catch (Exception ex) {
			Adp.rollback();
			log.error(query, ex);
			return false;
		} finally {
			Adp.close();
		}
	}

	/** Buyer accepted the seller's counter: the agreed price becomes the offer price. */
	final public boolean acceptCounterOffer(final String offerId, final String counterAmount) {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String query = "update soft set cost = ? , OFFER_STATUS_ID = ? where soft_id = ?";
		try {
			Map args = Adp.getArgs();
			args.put("cost", Double.valueOf(counterAmount));
			args.put("OFFER_STATUS_ID", Long.valueOf(OfferStatus.PRODUCT_OFFER_ACCEPTED));
			args.put("soft_id", Long.valueOf(offerId));
			Adp.executeUpdateWithArgs(query, args);
			Adp.commit();
			return true;
		} catch (Exception ex) {
			Adp.rollback();
			log.error(query, ex);
			return false;
		} finally {
			Adp.close();
		}
	}

	/**
	 * Records an offer on a product for the current user and notifies the seller (pass 10).
	 * @return the offer id
	 */
	final public String addOffer(PublisherBean publisherBeanId,
			final AuthorizationPageBean authorizationPageBeanId) throws UnsupportedEncodingException {

		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String strID = "";
		// String query = "SELECT NEXT VALUE FOR soft_id_seq AS ID FROM ONE_SEQUENCES";
		String query = sequencesRs.getString("soft");
		try {

			Adp.executeQuery(query);

			strID = Adp.getValueAt(0, 0);

			query = "insert into soft ( soft_id , " + " name , " + " description , " + " fulldescription , "
					+ " version , " + " cost , " + " currency , " + " file_id , " + " catalog_id , " + " active , "
					+ " licence_id  , " + " image_id , " + " bigimage_id , " + " user_id , " + " salelogic_id ,"
					+ " site_id , " + " product_code , " + " search,  " + " portlettype_id  ,  " + " type_id  ,  "
					+ " tree_id  ,  " + " creteria1_id ,  " + " creteria2_id ,  " + " creteria3_id ,  "
					+ " creteria4_id ,  " + " creteria5_id ,  " + " creteria6_id ,  " + " creteria7_id ,  "
					+ " creteria8_id ,  " + " creteria9_id ,  " + " creteria10_id , " + " show_rating1 ,  "
					+ " show_rating2 ,  " + " show_rating3 ,  " + " show_blog ,  " + " search2 ,  " + " amount1 , "
					+ " amount2 , " + " amount3 , " + " name2 ," + " lang_id ," + " jsp_url , OFFER_STATUS_ID ) " + " VALUES ( ? , "
					+ " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , "
					+ " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , "
					+ " ? , " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  "
					+ " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? , " + " ? ,  " + " ? ,  " + " ? ,  "
					+ " ? , " + " ? , " + " ? , " + " ?  )";

			Map args = Adp.getArgs();
			args.put("soft_id", Long.valueOf(strID));
			args.put("name", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName()));
			args.put("description", Validation.removeSpecificSymbols(publisherBeanId.strSoftDescription));
			args.put("fulldescription", Validation.removeSpecificSymbols(publisherBeanId.productFulldescription));
			args.put("version", publisherBeanId.getStrSoftVersion());
			args.put("cost", Double.valueOf(publisherBeanId.getStrSoftCost()));
			args.put("currency", Long.valueOf(publisherBeanId.getStrCurrency()));
			args.put("file_id", Long.valueOf(publisherBeanId.getFileId()));
			args.put("catalog_id", Long.valueOf(SpecialCatalog.OFFERS_CATALOG));
			args.put("active", true);
			args.put("licence_id", Long.valueOf(publisherBeanId.getLicenceId()));
			args.put("image_id", Long.valueOf(publisherBeanId.getImageId()));
			args.put("bigimage_id", Long.valueOf(publisherBeanId.getBigimageId()));
			args.put("user_id", Long.valueOf(authorizationPageBeanId.getIntUserID()));
			args.put("salelogic_id", Long.valueOf(publisherBeanId.getSalelogicId()));
			args.put("site_id", Long.valueOf(publisherBeanId.getSiteId()));
			args.put("product_code", Long.valueOf(publisherBeanId.getProductCodeId()));
			args.put("search", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName().substring(0, 1)));
			args.put("portlettype_id", Long.valueOf(Layout.PORTLET_TYPE_PRODUCT_OFFER));
			args.put("type_id", Long.valueOf(publisherBeanId.getTypeId()));
			args.put("tree_id", Long.valueOf(publisherBeanId.getSoftId()));
			args.put("creteria1_id", Long.valueOf(publisherBeanId.getCreteria1Id()));
			args.put("creteria2_id", Long.valueOf(publisherBeanId.getCreteria2Id()));
			args.put("creteria3_id", Long.valueOf(publisherBeanId.getCreteria3Id()));
			args.put("creteria4_id", Long.valueOf(publisherBeanId.getCreteria4Id()));
			args.put("creteria5_id", Long.valueOf(publisherBeanId.getCreteria5Id()));
			args.put("creteria6_id", Long.valueOf(publisherBeanId.getCreteria6Id()));
			args.put("creteria7_id", Long.valueOf(publisherBeanId.getCreteria7Id()));
			args.put("creteria8_id", Long.valueOf(publisherBeanId.getCreteria8Id()));
			args.put("creteria9_id", Long.valueOf(publisherBeanId.getCreteria9Id()));
			args.put("creteria10_id", Long.valueOf(publisherBeanId.getCreteria10Id()));
			args.put("show_rating1", Boolean.valueOf(publisherBeanId.getStrShowRatimg1()));
			args.put("show_rating2", Boolean.valueOf(publisherBeanId.getStrShowRatimg2()));
			args.put("show_rating3", Boolean.valueOf(publisherBeanId.getStrShowRatimg3()));
			args.put("show_blog", Boolean.valueOf(publisherBeanId.getStrShowForum()));
			args.put("search2", Validation.removeSpecificSymbols(publisherBeanId.getStrSearch2()));
			args.put("amount1", Double.valueOf(publisherBeanId.getAmount1()));
			args.put("amount2", Double.valueOf(publisherBeanId.getAmount2()));
			args.put("amount3", Double.valueOf(publisherBeanId.getAmount3()));
			args.put("name2", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName2()));
			args.put("lang_id", authorizationPageBeanId.getLangId());
			args.put("jsp_url", publisherBeanId.getJspUrl());
			args.put("OFFER_STATUS_ID", OfferStatus.PRODUCT_OFFER_SUBMITED);

			Adp.executeInsertWithArgs(query, args);
			Adp.commit();
		} catch (SQLException ex) {
			Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			Adp.rollback();
			log.error(ex);
		} finally {
			Adp.close();
		}

		return strID;
	}


	/**
	 * Changes the status of an offer after checking the caller is a party to it.
	 * @return the offer id
	 */
	final public String updateOfferStatus(final PublisherBean publisherBeanId,
			final AuthorizationPageBean authorizationPageBeanId , long offerStatus) throws UnsupportedEncodingException {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String id = publisherBeanId.getSoftId();
		String query = "";
		if (publisherBeanId.getSoftId().compareTo("-1") == 0) return "";

		query = "update soft set OFFER_STATUS_ID = ?  where soft_id = ? " ;

		Map args = Adp.getArgs();
		args.put("OFFER_STATUS_ID", Long.valueOf(offerStatus));
		args.put("soft_id", Long.valueOf(publisherBeanId.getSoftId()));


		try {
			Adp.executeUpdateWithArgs(query, args);
			Adp.commit();
		} catch (SQLException ex) {
			Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			Adp.rollback();
			log.error(ex);
		} finally {
			Adp.close();
		}

		publisherBeanId.setSoftId("-1");
		return id;
	}

		/** user_id of a bid or offer row (any soft row), 0 if unknown. */
	/** Product (tree_id) of an offer or bid row, "" if unknown. */
	final public String offerProductId(final String softId) {
		if (softId == null || !softId.matches("\\d{1,18}"))
			return "";
		QueryManager qm = new QueryManager();
		try {
			qm.executeQuery("select tree_id from soft where soft_id = " + softId);
			return qm.rows().size() == 0 || qm.getValueAt(0, 0) == null ? "" : qm.getValueAt(0, 0);
		} catch (Exception ex) {
			return "";
		} finally {
			qm.close();
		}
	}

	/**
	 * @return the user id that owns the bid row, or 0
	 */
	final public long bidUserId(final String softId) {
		if (softId == null || !softId.matches("\\d{1,18}"))
			return 0;
		QueryManager qm = new QueryManager();
		try {
			qm.executeQuery("select coalesce(user_id, 0) from soft where soft_id = " + softId);
			return qm.rows().size() == 0 ? 0 : Long.parseLong(qm.getValueAt(0, 0));
		} catch (Exception ex) {
			return 0;
		} finally {
			qm.close();
		}
	}

/**
	 * Auction guard: the product must exist on this site, be active, have the
	 * auction switched on (show_action) and not belong to the bidder. Returns
	 * the product's current highest live bid (submitted or won) or -1 when
	 * bidding is not allowed.
	 */
	final public double auctionMaxBidIfBiddable(final String productId, final String siteId, final long bidderId) {
		if (productId == null || !productId.matches("\\d{1,18}") || siteId == null || !siteId.matches("-?\\d{1,18}"))
			return -1;
		QueryManager qm = new QueryManager();
		try {
			qm.executeQuery("select soft.user_id , site.owner from soft join site on site.site_id = soft.site_id where soft.soft_id = "
					+ productId + " and soft.site_id = " + siteId + " and soft.active = true and soft.show_action = true and soft.tree_id is null");
			if (qm.rows().size() == 0)
				return -1;
			String owner = qm.getValueAt(0, 0), siteOwner = qm.getValueAt(0, 1);
			if (("" + bidderId).equals(owner) || ("" + bidderId).equals(siteOwner))
				return -1;
			qm.executeQuery("select coalesce(max(cost), 0) from soft where catalog_id = " + SpecialCatalog.AUCTION_BID_CATALOG
					+ " and tree_id = " + productId + " and active = true and ACTION_BID_STATUS_ID in ("
					+ com.cbsinc.cms.controllers.AuctionBidStatus.PRODUCT_AUCTION_BID_SUBMITED + " , "
					+ com.cbsinc.cms.controllers.AuctionBidStatus.PRODUCT_AUCTION_BID_WON + ")");
			String v = qm.getValueAt(0, 0);
			return (v == null || v.trim().length() == 0) ? 0 : Double.parseDouble(v.trim());
		} catch (Exception ex) {
			log.error("auctionMaxBidIfBiddable " + productId, ex);
			return -1;
		} finally {
			qm.close();
		}
	}

		/** Does the user watch this product (eye icon on ProductInfo)? */
	final public boolean isSubscribed(final String productId, final long userId) {
		if (productId == null || !productId.matches("\\d{1,18}") || userId <= 0)
			return false;
		QueryManager qm = new QueryManager();
		try {
			qm.executeQuery("SELECT SOFT_ID FROM subscription WHERE SOFT_ID = " + productId + " AND USER_ID = " + userId);
			return qm.rows().size() > 0;
		} catch (Exception ex) {
			log.error("isSubscribed " + productId, ex);
			return false;
		} finally {
			qm.close();
		}
	}

/**
	 * Who may act on a bid. Returns the bid's product id when bidId is a bid on
	 * this site and (asSeller == false) the current user placed it, or
	 * (asSeller == true) the current user owns the product or the site.
	 * Otherwise "".
	 */
	final public String auctionBidOwnedBy(final String bidId, final String siteId, final long userId, final boolean asSeller) {
		if (bidId == null || !bidId.matches("\\d{1,18}") || siteId == null || !siteId.matches("-?\\d{1,18}"))
			return "";
		QueryManager qm = new QueryManager();
		try {
			qm.executeQuery("select bid.tree_id , bid.user_id , prod.user_id , site.owner , bid.ACTION_BID_STATUS_ID from soft bid"
					+ " join soft prod on prod.soft_id = bid.tree_id join site on site.site_id = bid.site_id"
					+ " where bid.soft_id = " + bidId + " and bid.site_id = " + siteId + " and bid.catalog_id = " + SpecialCatalog.AUCTION_BID_CATALOG);
			if (qm.rows().size() == 0)
				return "";
			String u = "" + userId;
			boolean ok = asSeller ? (u.equals(qm.getValueAt(0, 2)) || u.equals(qm.getValueAt(0, 3))) : u.equals(qm.getValueAt(0, 1));
			if (ok && asSeller && !("" + com.cbsinc.cms.controllers.AuctionBidStatus.PRODUCT_AUCTION_BID_SUBMITED).equals(qm.getValueAt(0, 4)))
				ok = false; // only a live bid can be declared the winner
			return ok ? qm.getValueAt(0, 0) : "";
		} catch (Exception ex) {
			log.error("auctionBidOwnedBy " + bidId, ex);
			return "";
		} finally {
			qm.close();
		}
	}

	/**
	 * Places an auction bid if it exceeds the current maximum and notifies live bidders (pass 9).
	 * @return the bid id
	 */
	final public String addAuctionBid( final PublisherBean publisherBeanId,
			final AuthorizationPageBean authorizationPageBeanId) throws UnsupportedEncodingException {

		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String strID = "";
		// String query = "SELECT NEXT VALUE FOR soft_id_seq AS ID FROM ONE_SEQUENCES";
		String query = sequencesRs.getString("soft");
		try {

			Adp.executeQuery(query);

			strID = Adp.getValueAt(0, 0);

			query = "insert into soft ( soft_id , " + " name , " + " description , " + " fulldescription , "
					+ " version , " + " cost , " + " currency , " + " file_id , " + " catalog_id , " + " active , "
					+ " licence_id  , " + " image_id , " + " bigimage_id , " + " user_id , " + " salelogic_id ,"
					+ " site_id , " + " product_code , " + " search,  " + " portlettype_id  ,  " + " type_id  ,  "
					+ " tree_id  ,  " + " creteria1_id ,  " + " creteria2_id ,  " + " creteria3_id ,  "
					+ " creteria4_id ,  " + " creteria5_id ,  " + " creteria6_id ,  " + " creteria7_id ,  "
					+ " creteria8_id ,  " + " creteria9_id ,  " + " creteria10_id , " + " show_rating1 ,  "
					+ " show_rating2 ,  " + " show_rating3 ,  " + " show_blog ,  " + " search2 ,  " + " amount1 , "
					+ " amount2 , " + " amount3 , " + " name2 ," + " lang_id ," + " jsp_url , ACTION_BID_STATUS_ID  ) " + " VALUES ( ? , "
					+ " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , "
					+ " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , "
					+ " ? , " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  "
					+ " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? , " + " ? ,  " + " ? ,  " + " ? ,  "
					+ " ? , " + " ? , " + " ? , " + " ?  )";

			Map args = Adp.getArgs();
			args.put("soft_id", Long.valueOf(strID));
			args.put("name", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName()) );
			args.put("description", Validation.removeSpecificSymbols(publisherBeanId.strSoftDescription) );
			args.put("fulldescription", Validation.removeSpecificSymbols(publisherBeanId.productFulldescription) );
			args.put("version", publisherBeanId.getStrSoftVersion());
			args.put("cost", Double.valueOf(publisherBeanId.getStrSoftCost()));
			args.put("currency", Long.valueOf(publisherBeanId.getStrCurrency()));
			args.put("file_id", Long.valueOf(publisherBeanId.getFileId()));
			args.put("catalog_id", Long.valueOf(SpecialCatalog.AUCTION_BID_CATALOG));
			args.put("active", true);
			args.put("licence_id", Long.valueOf(publisherBeanId.getLicenceId()));
			args.put("image_id", Long.valueOf(publisherBeanId.getImageId()));
			args.put("bigimage_id", Long.valueOf(publisherBeanId.getBigimageId()));
			args.put("user_id", Long.valueOf(authorizationPageBeanId.getIntUserID()));
			args.put("salelogic_id", Long.valueOf(publisherBeanId.getSalelogicId()));
			args.put("site_id", Long.valueOf(publisherBeanId.getSiteId()));
			args.put("product_code", Long.valueOf(publisherBeanId.getProductCodeId()));
			args.put("search", publisherBeanId.getStrSoftName().substring(0, 1));
			args.put("portlettype_id", Long.valueOf(Layout.PORTLET_TYPE_PRODUCT_AUCTION_BID));
			args.put("type_id", Long.valueOf(publisherBeanId.getTypeId()));
			args.put("tree_id", Long.valueOf(publisherBeanId.getSoftId()));
			args.put("creteria1_id", Long.valueOf(publisherBeanId.getCreteria1Id()));
			args.put("creteria2_id", Long.valueOf(publisherBeanId.getCreteria2Id()));
			args.put("creteria3_id", Long.valueOf(publisherBeanId.getCreteria3Id()));
			args.put("creteria4_id", Long.valueOf(publisherBeanId.getCreteria4Id()));
			args.put("creteria5_id", Long.valueOf(publisherBeanId.getCreteria5Id()));
			args.put("creteria6_id", Long.valueOf(publisherBeanId.getCreteria6Id()));
			args.put("creteria7_id", Long.valueOf(publisherBeanId.getCreteria7Id()));
			args.put("creteria8_id", Long.valueOf(publisherBeanId.getCreteria8Id()));
			args.put("creteria9_id", Long.valueOf(publisherBeanId.getCreteria9Id()));
			args.put("creteria10_id", Long.valueOf(publisherBeanId.getCreteria10Id()));
			args.put("show_rating1", Boolean.valueOf(publisherBeanId.getStrShowRatimg1()));
			args.put("show_rating2", Boolean.valueOf(publisherBeanId.getStrShowRatimg2()));
			args.put("show_rating3", Boolean.valueOf(publisherBeanId.getStrShowRatimg3()));
			args.put("show_blog", Boolean.valueOf(publisherBeanId.getStrShowForum()));
			args.put("search2", Validation.removeSpecificSymbols(publisherBeanId.getStrSearch2()));
			args.put("amount1", Double.valueOf(publisherBeanId.getAmount1()));
			args.put("amount2", Double.valueOf(publisherBeanId.getAmount2()));
			args.put("amount3", Double.valueOf(publisherBeanId.getAmount3()));
			args.put("name2", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName2()));
			args.put("lang_id", authorizationPageBeanId.getLangId());
			args.put("jsp_url", publisherBeanId.getJspUrl());
			args.put("ACTION_BID_STATUS_ID", AuctionBidStatus.PRODUCT_AUCTION_BID_SUBMITED);

			Adp.executeInsertWithArgs(query, args);
			Adp.commit();
		} catch (SQLException ex) {
			Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			Adp.rollback();
			log.error(ex);
		} finally {
			Adp.close();
		}

		return strID;
	}



	/**
	 * Starts following the product for the current user (pass 12).
	 */
	final public void subscribe(final String productId,
			final AuthorizationPageBean authorizationPageBeanId ) throws UnsupportedEncodingException, SQLException {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();

		String query = "";
		// FIX: a missing product_id threw NPE and the two early returns leaked the connection.
		if (productId == null || !productId.matches("\\d{1,18}") || authorizationPageBeanId.getIntUserID() <= 0) {
			Adp.close();
			return;
		}
		query = "SELECT SOFT_ID FROM subscription WHERE SOFT_ID = ? AND USER_ID = ?";
		Adp.executeQueryWithArgs(query, new Object[] { productId, authorizationPageBeanId.getIntUserID() });
		if (Adp.rows().size() > 0) {
			Adp.close();
			return;
		}

		query = "INSERT INTO subscription (SOFT_ID, MESSAGE, USER_ID) VALUES(?, ?, ?)" ;
		Map args = Adp.getArgs();

		args.put("SOFT_ID", Long.valueOf(productId));
		args.put("MESSAGE", String.valueOf("subscribed"));
		args.put("USER_ID", Long.valueOf(authorizationPageBeanId.getIntUserID()));


		try {
			Adp.executeInsertWithArgs(query, args);
			Adp.commit();
		} catch (SQLException ex) {
			Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			Adp.rollback();
			log.error(ex);
		} finally {
			Adp.close();
		}
	}

	/**
	 * Stops following the product for the current user.
	 */
	final public void unsubscribe(final String productId,
			final AuthorizationPageBean authorizationPageBeanId ) throws UnsupportedEncodingException {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();

		String query = "";
		if (productId == null || !productId.matches("\\d{1,18}")) {
			Adp.close();
			return;
		}
		query = "DELETE FROM subscription WHERE SOFT_ID = ? AND USER_ID = ?";

		/**
		need to fix executeUpdateArgs() for delete
		Map args = Adp.getArgs();
		args.put("SOFT_ID", Long.valueOf(product_id));
		args.put("USER_ID", Long.valueOf(authorizationPageBeanId.getIntUserID()));
		*/

		try {
			Adp.executeUpdateWithArgs(query, new Object[] { productId, authorizationPageBeanId.getIntUserID() });
			Adp.commit();
		} catch (SQLException ex) {
			Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			Adp.rollback();
			log.error(ex);
		} finally {
			Adp.close();
		}
	}

	/**
	 * Changes the status of the caller's own bid.
	 * @return the bid id
	 */
	final public String updateAuctionBidStatus(final PublisherBean publisherBeanId,
			final AuthorizationPageBean authorizationPageBeanId , long auctionBidStatus) throws UnsupportedEncodingException {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String id = publisherBeanId.getSoftId();
		String query = "";
		if (publisherBeanId.getSoftId().compareTo("-1") == 0) return "";


		query = "update soft set ACTION_BID_STATUS_ID = ?  where soft_id = ? " ;

		Map args = Adp.getArgs();
		args.put("ACTION_BID_STATUS_ID", Long.valueOf(auctionBidStatus));
		args.put("soft_id", Long.valueOf(publisherBeanId.getSoftId()));


		try {
			Adp.executeUpdateWithArgs(query, args);
			Adp.commit();
		} catch (SQLException ex) {
			Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			Adp.rollback();
			log.error(ex);
		} finally {
			Adp.close();
		}

		publisherBeanId.setSoftId("-1");
		return id;
	}


	/**
	 * Marks a bid as winner; only the seller of the lot may do so.
	 * @return the bid id
	 */
	final public String setupAuctionWinningBidStatus(final PublisherBean publisherBeanId,
			final AuthorizationPageBean authorizationPageBeanId ) throws UnsupportedEncodingException {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String id = publisherBeanId.getSoftId();
		String query = "";
		if (publisherBeanId.getSoftId().compareTo("-1") == 0) return "";

		 query = "SELECT  tree_id  FROM soft  WHERE soft_id = ?";

		QueryManager qm = new QueryManager();
		try {
			qm.executeQueryWithArgs(query, new Object[] { publisherBeanId.getSoftId() });
			if (qm.rows().size() > 0)
			{
				String productId =	 qm.getValueAt(0, 0) ;
				query = "update soft set ACTION_BID_STATUS_ID = ?  where tree_id = ? " ;

				Map args = Adp.getArgs();
				args.put("ACTION_BID_STATUS_ID", Long.valueOf(AuctionBidStatus.PRODUCT_AUCTION_BID_DID_NOT_WIN));
				args.put("tree_id", Long.valueOf(productId));

				try {
					Adp.executeUpdateWithArgs(query, args);
					Adp.commit();
				} catch (Exception ex) {
					Adp.rollback();
					log.error(query, ex);
					throw ex ;
				}


				query = "update soft set ACTION_BID_STATUS_ID = ?  where soft_id = ? " ;

				args = Adp.getArgs();
				args.put("ACTION_BID_STATUS_ID", Long.valueOf(AuctionBidStatus.PRODUCT_AUCTION_BID_WON));
				args.put("soft_id", Long.valueOf(publisherBeanId.getSoftId()));


				try {
					Adp.executeUpdateWithArgs(query, args);
					Adp.commit();
				} catch (Exception ex) {
					Adp.rollback();
					log.error(query, ex);
					throw ex ;
				}

			}

		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			qm.close();
		}


		publisherBeanId.setSoftId("-1");
		return id;
	}

	/**
	 * Save for aprouch administrator
	 *
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	final public String saveInformationWithCheck(final PublisherBean publisherBeanId,
			final AuthorizationPageBean authorizationPageBeanId) throws UnsupportedEncodingException {

		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String strID = "";
		// String query = "SELECT NEXT VALUE FOR soft_id_seq AS ID FROM ONE_SEQUENCES";
		String query = sequencesRs.getString("soft");
		try {
			Adp.executeQuery(query);
			strID = Adp.getValueAt(0, 0);
			query = "insert into soft ( soft_id , " + " name , " + " description , " + " fulldescription , "
					+ " version , " + " cost , " + " currency , " + " file_id , " + " catalog_id , " + " active , "
					+ " licence_id  , " + " image_id , " + " bigimage_id , " + " user_id , " + " salelogic_id ,"
					+ " site_id , " + " product_code , " + " search,  " + " portlettype_id  ,  " + " tree_id  ,  "
					+ " creteria1_id ,  " + " creteria2_id ,  " + " creteria3_id ,  " + " creteria4_id ,  "
					+ " creteria5_id ,  " + " creteria6_id ,  " + " creteria7_id ,  " + " creteria8_id ,  "
					+ " creteria9_id ,  " + " creteria10_id , " + " show_rating1 ,  " + " show_rating2 ,  "
					+ " show_rating3 ,  " + " show_blog ,  " + " search2 ,  " + " amount1 , " + " amount2 , "
					+ " amount3 , " + " name2 ," + " lang_id ," + " jsp_url  ) " + " VALUES ( ? , " + " ? , " + " ? , "
					+ " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , "
					+ " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? , " + " ? ,  "
					+ " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  " + " ? ,  "
					+ " ? ,  " + " ? ,  " + " ? ,  " + " ? , " + " ? ,  " + " ? ,  " + " ? ,  " + " ? , " + " ? , "
					+ " ?  )";

			Map args = Adp.getArgs();
			args.put("soft_id", Long.valueOf(strID));
			args.put("name", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName()) );
			args.put("description", Validation.removeSpecificSymbols(publisherBeanId.strSoftDescription) );
			args.put("fulldescription", Validation.removeSpecificSymbols(publisherBeanId.productFulldescription));
			args.put("version", publisherBeanId.getStrSoftVersion());
			args.put("cost", Double.valueOf(publisherBeanId.getStrSoftCost()));
			args.put("currency", Long.valueOf(publisherBeanId.getStrCurrency()));
			args.put("file_id", Long.valueOf(publisherBeanId.getFileId()));
			args.put("catalog_id", Long.valueOf(authorizationPageBeanId.getCatalogId()));
			args.put("active", true);
			args.put("licence_id", Long.valueOf(publisherBeanId.getLicenceId()));
			args.put("image_id", Long.valueOf(publisherBeanId.getImageId()));
			args.put("bigimage_id", Long.valueOf(publisherBeanId.getBigimageId()));
			args.put("user_id", Long.valueOf(authorizationPageBeanId.getIntUserID()));
			args.put("salelogic_id", Long.valueOf(publisherBeanId.getSalelogicId()));
			args.put("site_id", Long.valueOf(publisherBeanId.getSiteId()));
			args.put("product_code", Long.valueOf(publisherBeanId.getProductCodeId()));
			args.put("search", publisherBeanId.getStrSoftName().substring(0, 1));
			args.put("portlettype_id", Long.valueOf(publisherBeanId.getPortlettypeId()));
			args.put("type_id", Long.valueOf(PostType.FOR_APROVE));
			args.put("creteria1_id", Long.valueOf(publisherBeanId.getCreteria1Id()));
			args.put("creteria2_id", Long.valueOf(publisherBeanId.getCreteria2Id()));
			args.put("creteria3_id", Long.valueOf(publisherBeanId.getCreteria3Id()));
			args.put("creteria4_id", Long.valueOf(publisherBeanId.getCreteria4Id()));
			args.put("creteria5_id", Long.valueOf(publisherBeanId.getCreteria5Id()));
			args.put("creteria6_id", Long.valueOf(publisherBeanId.getCreteria6Id()));
			args.put("creteria7_id", Long.valueOf(publisherBeanId.getCreteria7Id()));
			args.put("creteria8_id", Long.valueOf(publisherBeanId.getCreteria8Id()));
			args.put("creteria9_id", Long.valueOf(publisherBeanId.getCreteria9Id()));
			args.put("creteria10_id", Long.valueOf(publisherBeanId.getCreteria10Id()));
			args.put("show_rating1", Boolean.valueOf(publisherBeanId.getStrShowRatimg1()));
			args.put("show_rating2", Boolean.valueOf(publisherBeanId.getStrShowRatimg2()));
			args.put("show_rating3", Boolean.valueOf(publisherBeanId.getStrShowRatimg3()));
			args.put("show_blog", Boolean.valueOf(publisherBeanId.getStrShowForum()));
			args.put("search2", Validation.removeSpecificSymbols(publisherBeanId.getStrSearch2()) );
			args.put("amount1", Double.valueOf(publisherBeanId.getAmount1()));
			args.put("amount2", Double.valueOf(publisherBeanId.getAmount2()));
			args.put("amount3", Double.valueOf(publisherBeanId.getAmount3()));
			args.put("name2", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName2()) );
			args.put("lang_id", authorizationPageBeanId.getLangId());
			args.put("jsp_url", publisherBeanId.getJspUrl());

			Adp.executeInsertWithArgs(query, args);
			Adp.commit();

		} catch (SQLException ex) {
			Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			Adp.rollback();
			log.error(ex);
		} finally {
			Adp.close();
		}

		return strID;
	}

	/**
	 * Updates the product row after verifying the caller owns it.
	 * @return the product id
	 */
	final public String updateInformationWithCheck(final PublisherBean publisherBeanId,
			final AuthorizationPageBean authorizationPageBeanId) throws UnsupportedEncodingException {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String id = publisherBeanId.getSoftId();
		String query = "";
		if (publisherBeanId.getSoftId().compareTo("-1") == 0)
			return "";

		if (publisherBeanId.getStrSoftName2() != null && publisherBeanId.getStrSoftName2().length() > 0)
			publisherBeanId.setStrSearch2(publisherBeanId.getStrSoftName2().substring(0, 1));

		query = " update soft set soft_id = ?, " + " name = ?, " + " description = ?, " + " fulldescription = ?, "
				+ " version = ?, " + " cost = ?, " + " currency = ?, " + " file_id = ?, " + " catalog_id = ?, "
				+ " image_id = ?, " + " bigimage_id = ?, " + " salelogic_id = ?, " + " site_id = ?, "
				+ " product_code = ?, " + " search = ?, " + " type_id = ? , " + " portlettype_id = ? , "
				+ " creteria1_id = ? , " + " creteria2_id = ? , " + " creteria3_id = ? , " + " creteria4_id = ? , "
				+ " creteria5_id = ? , " + " creteria6_id = ? , " + " creteria7_id = ? , " + " creteria8_id = ? , "
				+ " creteria9_id = ? , " + " creteria10_id = ? , " + " show_rating1 = ? , " + " show_rating2 = ? , " + " show_rating3 = ? , " + " show_blog = ? , " + " amount1 = ? , " + " amount2 = ? , "
				+ " amount3 = ? , " + " search2 = ? , " + " name2 = ? , " + " SHOW_RATING1 = ? , "
				+ " SHOW_RATING2 = ? , " + " SHOW_RATING3 = ? , " + " SHOW_BLOG = ? , " + " lang_id = ? , "
				+ " jsp_url = ? , " + " CDATE = ? " + " where soft_id = " + publisherBeanId.getSoftId();

		Map args = Adp.getArgs();
		args.put("soft_id", Long.valueOf(publisherBeanId.getSoftId()));
		args.put("name", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName()) );
		args.put("description", Validation.removeSpecificSymbols(publisherBeanId.strSoftDescription));
		args.put("fulldescription", Validation.removeSpecificSymbols(publisherBeanId.productFulldescription));
		args.put("version", publisherBeanId.getStrSoftVersion());
		args.put("cost", Double.valueOf(publisherBeanId.getStrSoftCost()));
		args.put("currency", Long.valueOf(publisherBeanId.getStrCurrency()));
		args.put("file_id", Long.valueOf(publisherBeanId.getFileId()));
		args.put("catalog_id", Long.valueOf(authorizationPageBeanId.getCatalogId()));
		args.put("image_id", Long.valueOf(publisherBeanId.getImageId()));
		args.put("bigimage_id", Long.valueOf(publisherBeanId.getBigimageId()));
		args.put("salelogic_id", Long.valueOf(publisherBeanId.getSalelogicId()));
		args.put("site_id", Long.parseLong(publisherBeanId.getSiteId()));
		args.put("product_code", Long.valueOf(publisherBeanId.getProductCodeId()));
		args.put("search", publisherBeanId.getStrSoftName().substring(0, 1));
		args.put("type_id", PostType.FOR_APROVE);
		args.put("portlettype_id", Long.valueOf(publisherBeanId.getPortlettypeId()));
		args.put("creteria1_id", Long.valueOf(publisherBeanId.getCreteria1Id()));
		args.put("creteria2_id", Long.valueOf(publisherBeanId.getCreteria2Id()));
		args.put("creteria3_id", Long.valueOf(publisherBeanId.getCreteria3Id()));
		args.put("creteria4_id", Long.valueOf(publisherBeanId.getCreteria4Id()));
		args.put("creteria5_id", Long.valueOf(publisherBeanId.getCreteria5Id()));
		args.put("creteria6_id", Long.valueOf(publisherBeanId.getCreteria6Id()));
		args.put("creteria7_id", Long.valueOf(publisherBeanId.getCreteria7Id()));
		args.put("creteria8_id", Long.valueOf(publisherBeanId.getCreteria8Id()));
		args.put("creteria9_id", Long.valueOf(publisherBeanId.getCreteria9Id()));
		args.put("creteria10_id", Long.valueOf(publisherBeanId.getCreteria10Id()));
		args.put("show_rating1", Boolean.valueOf(publisherBeanId.getStrShowRatimg1()));
		args.put("show_rating2", Boolean.valueOf(publisherBeanId.getStrShowRatimg2()));
		args.put("show_rating3", Boolean.valueOf(publisherBeanId.getStrShowRatimg3()));
		args.put("show_blog", Boolean.valueOf(publisherBeanId.getStrShowForum()));
		args.put("amount1", Double.parseDouble(publisherBeanId.getAmount1()));
		args.put("amount2", Double.parseDouble(publisherBeanId.getAmount2()));
		args.put("amount3", Double.parseDouble(publisherBeanId.getAmount3()));
		args.put("search2", Validation.removeSpecificSymbols(publisherBeanId.getStrSearch2()));
		//args.put("name2", publisherBeanId.getStrSoftName2());
		args.put("name2", Validation.removeSpecificSymbols(publisherBeanId.getStrSoftName2()));

		args.put("SHOW_RATING1", Boolean.valueOf(publisherBeanId.getStrShowRatimg1()));
		args.put("SHOW_RATING2", Boolean.valueOf(publisherBeanId.getStrShowRatimg2()));
		args.put("SHOW_RATING3", Boolean.valueOf(publisherBeanId.getStrShowRatimg3()));
		args.put("SHOW_BLOG", Boolean.valueOf(publisherBeanId.getStrShowForum()));
		args.put("lang_id", authorizationPageBeanId.getLangId());
		args.put("jsp_url", publisherBeanId.getJspUrl());
		args.put("CDATE", new java.util.Date());

		try {
			Adp.executeUpdateWithArgs(query, args);
			Adp.commit();
		} catch (SQLException ex) {
			Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			Adp.rollback();
			log.error(ex);
		} finally {
			Adp.close();
		}

		publisherBeanId.setSoftId("-1");
		return id;
	}

//	public String saveDescSoft(String tree_id , SoftPostBean publisherBeanId)
//			throws UnsupportedEncodingException {
//
//		/*
//		 * if( publisherBeanId.position_cd == 0 ) return false ; if( publisherBeanId.cost == 0 )
//		 * return false ; if( publisherBeanId.currency_cd == 0 ) return false ; if(
//		 * publisherBeanId.countposition == 0 ) return false ; if(
//		 * publisherBeanId.deliverylength_ofday == 0 ) return false ; if( publisherBeanId.producer_cd ==
//		 * 0 ) return false ;
//		 */
//		QueryManager Adp = new QueryManager();
//		Adp.BeginTransaction();
//		String strID;
//		//String query = "SELECT NEXT VALUE FOR soft_id_seq  AS ID  FROM ONE_SEQUENCES";
//		String query = sequences_rs.getString("soft");
//		try {
//			Adp.executeQuery(query);
//		} catch (SQLException ex) {
//			Adp.rollback();
//			Adp.close();
//			log.error(ex);
//		}
//
//		strID = Adp.getValueAt(0, 0);
//
//		query = "insert into soft ( soft_id , "
//			+ " name , "
//			+ " description , "
//			+ " fulldescription , "
//			+ " version , "
//			+ " cost , "
//			+ " currency , "
//			+ " file_id , "
//			+ " catalog_id , "
//			+ " active , "
//			+ " licence_id  , "
//			+ " image_id , "
//			+ " bigimage_id , "
//			+ " user_id , "
//			+ " salelogic_id ,"
//			+ " site_id , "
//			+ " product_code , "
//			+ " search,  "
//			+ " portlettype_id  ,  "
//			+ " tree_id  ,  "
//			+ " creteria1_id ,  "
//			+ " creteria2_id ,  "
//			+ " creteria3_id ,  "
//			+ " creteria4_id ,  "
//			+ " creteria5_id ,  "
//			+ " creteria6_id ,  "
//			+ " creteria7_id ,  "
//			+ " creteria8_id ,  "
//			+ " creteria9_id ,  "
//			+ " creteria10_id , "
//			+ " show_rating1 ,  "
//			+ " show_rating2 ,  "
//			+ " show_rating3 ,  "
//			+ " show_blog ,  "
//			+ " search2 ,  "
//			+ " amount1 , "
//			+ " amount2 , "
//			+ " amount3 , "
//			+ " name2 ,"
//			+ " jsp_url  ) "
//			+ " VALUES ( ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? , "
//			+ " ? ,  "
//			+ " ? ,  "
//			+ " ? ,  "
//			+ " ? ,  "
//			+ " ? ,  "
//			+ " ? ,  "
//			+ " ? ,  "
//			+ " ? ,  "
//			+ " ? ,  "
//			+ " ? ,  "
//			+ " ? ,  "
//			+ " ? ,  "
//			+ " ? , "
//			+ " ? ,  "
//			+ " ? ,  "
//			+ " ? ,  "
//			+ " ? , "
//			+ " ?  )";
//
//		HashMap args = new HashMap();
//		args.put("soft_id",Long.valueOf(strID) );
//		args.put("name",publisherBeanId.getStrSoftName() );
//		args.put("description", publisherBeanId.strSoftDescription);
//		args.put("fulldescription",publisherBeanId.product_fulldescription );
//		args.put("version",publisherBeanId.getStrSoftVersion() );
//		args.put("cost", Double.valueOf(publisherBeanId.getStrSoftCost() ));
//		args.put("currency", Long.valueOf(publisherBeanId.getStrCurrency()) );
//		args.put("file_id", Long.valueOf(publisherBeanId.getFile_id()) );
//		args.put("catalog_id", Long.valueOf(publisherBeanId.getCatalog_id()) );
//		args.put("active",true);
//		args.put("licence_id" ,Long.valueOf(publisherBeanId.getLicence_id()) );
//		args.put("image_id",Long.valueOf(publisherBeanId.getImage_id()) );
//		args.put("bigimage_id" ,Long.valueOf(publisherBeanId.getBigimage_id()));
//		args.put("user_id" ,Long.valueOf(publisherBeanId.getUser_id()));
//		args.put("salelogic_id" ,Long.valueOf(publisherBeanId.getSalelogic_id()));
//		args.put("site_id" , Long.valueOf(publisherBeanId.getSite_id()));
//		args.put("product_code",Long.valueOf(publisherBeanId.getProduct_code_id()));
//		args.put("search", publisherBeanId.getStrSoftName().substring(0, 1));
//		args.put("portlettype_id" ,Long.valueOf(publisherBeanId.getPortlettype_id()));
//		args.put("tree_id" ,Long.valueOf(tree_id));
//		args.put("creteria1_id",Long.valueOf(publisherBeanId.getCreteria1_id()));
//		args.put("creteria2_id" ,Long.valueOf(publisherBeanId.getCreteria2_id()));
//		args.put("creteria3_id",Long.valueOf(publisherBeanId.getCreteria3_id()));
//		args.put("creteria4_id" ,Long.valueOf(publisherBeanId.getCreteria4_id()));
//		args.put("creteria5_id",Long.valueOf(publisherBeanId.getCreteria5_id()));
//		args.put("creteria6_id" ,Long.valueOf(publisherBeanId.getCreteria6_id()));
//		args.put("creteria7_id",Long.valueOf(publisherBeanId.getCreteria7_id()));
//		args.put("creteria8_id" ,Long.valueOf(publisherBeanId.getCreteria8_id()));
//		args.put("creteria9_id",Long.valueOf(publisherBeanId.getCreteria9_id()));
//		args.put("creteria10_id" ,Long.valueOf(publisherBeanId.getCreteria10_id()));
//		args.put("show_rating1",Boolean.valueOf(publisherBeanId.getStrShow_ratimg1()));
//		args.put("show_rating2" ,Boolean.valueOf(publisherBeanId.getStrShow_ratimg2()));
//		args.put("show_rating3",Boolean.valueOf(publisherBeanId.getStrShow_ratimg3()));
//		args.put("show_blog" ,Boolean.valueOf(publisherBeanId.getStrShow_forum()) );
//		args.put("search2",publisherBeanId.getStrSearch2());
//		args.put("amount1" ,Double.valueOf(publisherBeanId.getAmount1()));
//		args.put("amount2",Double.valueOf(publisherBeanId.getAmount2()));
//		args.put("amount3" ,Double.valueOf(publisherBeanId.getAmount3()));
//		args.put("name2" ,publisherBeanId.getStrSoftName2());
//		args.put("jsp_url" ,publisherBeanId.getJsp_url());
//		try
//		{
//			Adp.executeInsertWithArgs(query, args);
//			Adp.commit();
//		}
//		catch (SQLException ex)
//		{
//		  Adp.rollback();
//		  log.error(query, ex);
//		}
//		catch (Exception ex)
//		{
//		   Adp.rollback();
//		   log.error(ex);
//		}
//		 finally
//		{
//		Adp.close();
//		}
//
//		return strID;
//	}
//

	/**
	 * Puts the file name of the given file id on the bean.
	 */
	final public void setFileNameByFileID(final String fileId, final PublisherBean publisherBeanId) {

		QueryManager Adp = new QueryManager();
		String query = "select name  from file  where  file_id  = " + fileId;
		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() > 0)
				publisherBeanId.setFilename(Adp.getValueAt(0, 0));
			else
				publisherBeanId.setFilename("");

		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}

	}

	/**
	 * Puts the small image name of the given image id on the bean.
	 */
	final public void setImageNameByImageID(final String imageId, final PublisherBean publisherBeanId) {

		QueryManager Adp = new QueryManager();
		// FIX: imageId is request-derived and concatenated unquoted; constrain to digits.
		String query = "SELECT imgname FROM images WHERE image_id = "
				+ com.cbsinc.cms.utils.Validation.requireNumericId(imageId);

		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() > 0)
				publisherBeanId.setImgname(Adp.getValueAt(0, 0));
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}

	}

	/**
	 * Puts the big image name of the given image id on the bean.
	 */
	final public void setBigImageNameByImageID(final String imageId, final PublisherBean publisherBeanId) {

		QueryManager Adp = new QueryManager();
		// FIX: imageId is request-derived and concatenated unquoted; constrain to digits.
		String query = "SELECT imgname FROM big_images WHERE big_images_id = "
				+ com.cbsinc.cms.utils.Validation.requireNumericId(imageId);
		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() > 0)
				publisherBeanId.setBigimgname(Adp.getValueAt(0, 0));
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
	}


	/**
	 * Puts the catalogue image name of the given image id on the bean.
	 */
	final public void setCatalogImageNameByImageID(final String catalogImageId, final PublisherBean publisherBeanId) {

		QueryManager Adp = new QueryManager();
		String query = "SELECT imgname FROM catalog_images WHERE catalog_images_id = ?";
		try {
			Adp.executeQueryWithArgs(query, new Object[] { catalogImageId });
			if (Adp.rows().size() > 0)
				publisherBeanId.setCatalogImgname(Adp.getValueAt(0, 0));
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
	}

//	public String getCountActiveRow( SoftPostBean publisherBeanId ) {
//
//
//		QueryManager Adp = new QueryManager();
//		String strID = "";
//		String query = "SELECT count(soft_id) FROM soft WHERE catalog_id = ? and  active = ? ";
//
//		Object[] args = new Object[2];
//		args[0] =  Long.valueOf(publisherBeanId.getCatalog_id()) ;
//		args[1] =  true ;
//
//		try
//		{
//			Adp.executeQueryWithArgs(query,args,10,0);
//			if (Adp.rows().size() > 0)strID = Adp.getValueAt(0, 0);
//		}
//		catch (SQLException ex)
//		{
//			 log.error(query, ex);
//		}
//		 catch (Exception ex)
//		{
//			 log.error(ex);
//		}
//		finally
//		{
//		Adp.close();
//		}
//
//		return strID;
//	}
//

	/**
	 * Renders a select of big images with a preview script.
	 * @return the select markup
	 */
	final public String getComboBoxWithJavaScriptBigImage(final String name, String selectedCd, final String query,
			final String javascriptStatment, final PublisherBean publisherBeanId) {
		String strCD = "0";
		String strLable = "Other";
		if (selectedCd == null)
			selectedCd = "";
		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();

		try {
			Adp.executeQuery(query);
			// onChange=\"return document.forms[0].submit()\"
			table.append("<select name=\"" + name + "\"   id=\"" + name + "\"    " + javascriptStatment + "   > \n");
			if (Adp.rows().size() > 0) {
				strCD = (String) Adp.getValueAt(0, 0);
				strLable = (String) Adp.getValueAt(0, 1);
				publisherBeanId.setBigimageId(strCD);
				publisherBeanId.setBigimgname(strLable);
			}
			for (int i = 0; Adp.rows().size() > i; i++) {
				// if(i==0) if(((String)selected_cd).length()==0 ) selected_cd =
				// (String)Adp.getValueAt(i,0) ;
				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				if (selectedCd.compareTo(strCD) == 0) {
					table.append("<option value=\"" + strCD + "\" selected >" + strLable + "\n");
					publisherBeanId.setBigimageId(strCD);
					publisherBeanId.setBigimgname(strLable);
				} else
					table.append("<option value=\"" + strCD + "\">" + strLable + "\n");
			}
			table.append("</select> \n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	/**
	 * Renders a select of small images with a preview script.
	 * @return the select markup
	 */
	final public String getComboBoxWithJavaScriptSmallImage(final String name, String selectedCd, final String query,
			final String javascriptStatment, final PublisherBean publisherBeanId) {
		String strCD = "0";
		String strLable = "Other";
		if (selectedCd == null)
			selectedCd = "";

		StringBuffer table = new StringBuffer();
		// QueryManager Adp = new
		// QueryManager("jdbc:postgresql://192.168.0.10:5432/nnt")
		// ;
		// QueryManager Adp = new
		// QueryManager("jdbc:postgresql://nntsport.com:5432/nnt")
		// ;
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);
			// onChange=\"return document.forms[0].submit()\"
			table.append("<select name=\"" + name + "\"   id=\"" + name + "\"    " + javascriptStatment + "   > \n");
			if (Adp.rows().size() > 0) {
				strCD = (String) Adp.getValueAt(0, 0);
				strLable = (String) Adp.getValueAt(0, 1);
				publisherBeanId.setImageId(strCD);
				publisherBeanId.setImgname(strLable);
			}
			for (int i = 0; Adp.rows().size() > i; i++) {

				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				if (selectedCd.compareTo(strCD) == 0) {
					table.append("<option value=\"" + strCD + "\" selected >" + strLable + "\n");
					publisherBeanId.setImageId(strCD);
					publisherBeanId.setImgname(strLable);
				} else
					table.append("<option value=\"" + strCD + "\">" + strLable + "\n");
			}
			table.append("</select> \n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	/**
	 * Renders an auto-submitting locale select.
	 * @return the select markup
	 */
	final public String getComboBoxAutoSubmitLocale(final String name, String selectedCd, final String defaultLabel,
			final String query) {

		String strCD = "0";
		String strLable = "Other";
		if (selectedCd == null)
			selectedCd = "";

		StringBuffer table = new StringBuffer();

		QueryManager Adp = new QueryManager();
		try {

			Adp.executeQuery(query);

			table.append("<select name=\"" + name + "\"   id=\"" + name + "\"    onChange=\"doChangeCreteria('" + name
					+ "', this.value)\"    > \n");
			for (int i = 0; Adp.rows().size() > i; i++) {

				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				if (strCD.equals("0"))
					strLable = defaultLabel;

				if (selectedCd.compareTo(strCD) == 0)

					table.append("<option value=\"" + strCD + "\" selected >" + strLable + "\n");
				else

					table.append("<option value=\"" + strCD + "\">" + strLable + "\n");
			}

			table.append("</select> \n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	/**
	 * Runs a single-value query and returns the first cell.
	 * @return the value or an empty string
	 */
	final public String getOneLabel(final String query) {
		String name = "";

		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() != 0)
				name = (String) Adp.getValueAt(0, 0);
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return name;
	}

	/**
	 * Tells whether the site's posting limit has been reached for the user.
	 * @return true if no more posts are allowed
	 */
	final public boolean isLimmitPostedMessages(final AuthorizationPageBean authorizationBean,
			final boolean updateStatus) {
		Calendar calendar = Calendar.getInstance();
		boolean rezult = false;
		int limmitPostedMessages = 0;
		String stateLimmit = "";
		String stateDay = "";
		String limmit = setupResources.getString("limmit_posted_messages");
		if (Validation.isNonNegativeInteger(limmit))
			limmitPostedMessages = Integer.parseInt(limmit);
		else
			return false;

		String query = "select state  from tuser where site_id = ?";
		QueryManager Adp = new QueryManager();
		Map args = Adp.getArgs();
		// Adp.BeginTransaction();
		try {
			Adp.executeQueryWithArgs(query, new Object[] { authorizationBean.getSiteId() });
			if (Adp.rows().size() != 0) {
				String stateField = Adp.getValueAt(0, 0);
				String[] arrey = stateField.split("_");
				if (stateField.equals("") || arrey.length != 2) {
					stateDay = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
					stateLimmit = "0";
				} else {
					stateLimmit = arrey[0];
					stateDay = arrey[1];
				}

				if (stateDay.compareTo(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH))) != 0) {
					stateLimmit = "0";
					stateDay = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
				}

				if (Validation.isNonNegativeInteger(stateLimmit))
					authorizationBean.setNumberPostedMessages(Integer.parseInt(stateLimmit) + 1); // + " " +
				if (limmitPostedMessages < authorizationBean.getNumberPostedMessages())
					rezult = true;

			}

			if (updateStatus) {
				query = "update tuser set  state = ?  where site_id = " + authorizationBean.getSiteId();
				args = Adp.getArgs();
				args.put("state", authorizationBean.getNumberPostedMessages().toString().concat("_").concat(stateDay));
				Adp.executeUpdateWithArgs(query, args);
			}
			// Adp.commit();

		} catch (SQLException ex) {
			log.error(query, ex);
			// Adp.rollback();
		} catch (Exception ex) {
			log.error(ex);
			// Adp.rollback();
		}

		finally {
			Adp.close();
		}

		// global_has_limmit_forsite

		return rezult;
	}

	/**
	 * Registers an uploaded small image for the user.
	 * @return the new image id
	 */
	public long saveSmallImgURL(String fileName, long userId) {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String strID = null;
		String query = sequencesRs.getString("images");
		try {

			Adp.executeQuery(query);
			strID = Adp.getValueAt(0, 0);
			query = "insert into images ( image_id ,  imgname , img_url ,  user_id ) VALUES " + "( ? ,  ? , ? ,  ? )";
			Map args = new HashMap();
			args.put("image_id", Long.valueOf(strID));
			args.put("imgname", fileName);
			args.put("img_url", "imgpositions/" + strID + FileNames.extensionWithDot(fileName));
			args.put("user_id", Long.valueOf(userId));
			Adp.executeInsertWithArgs(query, args);
			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
		} finally {
			Adp.close();
		}

		return Long.parseLong(strID);
	}

	/**
	 * Deletes a small image record.
	 */
	public String deleteSmallImgURL(long imageId) {

		QueryManager Adp = new QueryManager();
		String path = "";
		Adp.beginTransaction();
		String query = "select img_url from images where image_id = " + imageId;

		try {
			Adp.executeQuery(query);
			path = Adp.getValueAt(0, 0);
			query = query = "delete from images where image_id = " + imageId;
			// Adp.executeQuery(query);
			Adp.executeUpdate(query);
			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
		} finally {
			Adp.close();
		}

		return path;
	}

	/**
	 * Registers an uploaded big image for the user.
	 * @return the new image id
	 */
	public long saveBigImgURL(String fileName, long userId) {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String strID = null;
		String query = sequencesRs.getString("big_images");
		try {
			Adp.executeQuery(query);
			strID = Adp.getValueAt(0, 0);
			query = "insert into big_images ( big_images_id , imgname ,  img_url ,  user_id  )"
					+ " VALUES ( ?, ?, ? , ? )";

			Map args = new HashMap();
			args.put("big_images_id", Long.valueOf(strID));
			args.put("imgname", fileName);
			args.put("img_url", "big_imgpositions/" + strID + FileNames.extensionWithDot(fileName));
			args.put("user_id", Long.valueOf(userId));
			Adp.executeInsertWithArgs(query, args);

			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
		} finally {
			Adp.close();
		}

		return Long.parseLong(strID);
	}

	/**
	 * Deletes a big image record.
	 */
	public String deleteBigImgURL(long bigImagesId) {
		QueryManager Adp = new QueryManager();
		String path = "";
		Adp.beginTransaction();
		String query = "select img_url from big_images where big_images_id = " + bigImagesId;

		try {
			Adp.executeQuery(query);
			path = Adp.getValueAt(0, 0);
			query = "delete from big_images where big_images_id = " + bigImagesId;
			Adp.executeUpdate(query);
			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
		} finally {
			Adp.close();
		}

		return path;
	}

	/**
	 * Registers an uploaded file for the user.
	 * @return the new file id
	 */
	public long saveFileURL(String fileName, long userId) {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String strID = "-1";
		String path;
		String query = sequencesRs.getString("file");
		try {

			Adp.executeQuery(query);

			strID = Adp.getValueAt(0, 0);

			//path = this.getClass().getResource("").getPath();
			//path = path.substring(0, path.indexOf("/WEB-INF/"));
			path = FileStorage.getInstance().getPath() ;
			path = path.substring(1) + "/files/" + strID + FileNames.extensionWithDot(fileName);

			query = "insert into file " + "(" + " file_id , " + " name , " + " path , " + " user_id " + ")" + " VALUES "
					+ "( " + strID + ", '" + fileName + "', '" + path + "' , " + userId + " )";

			Adp.executeUpdate(query);
			Adp.commit();
		} catch (SQLException ex) {
			Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			Adp.rollback();
			log.error(ex);
		} finally {
			Adp.close();
		}

		return Long.parseLong(strID);
	}

	/**
	 * Deletes a file record.
	 */
	public String deleteFileURL(long fileId) {
		QueryManager Adp = new QueryManager();
		String path = "";
		Adp.beginTransaction();
		String query = "select path from file where file_id = " + fileId;

		try {
			Adp.executeQuery(query);
			path = Adp.getValueAt(0, 0);
			query = "delete from file where file_id = " + fileId;
			Adp.executeUpdate(query);
			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
		} finally {
			Adp.close();
		}

		return path;
	}

	/**
	 * Renders the paging navigator for the user's product list.
	 * @return the navigator markup
	 */
	public String getNavigator(AuthorizationPageBean authorizationPageBeanId, int offset) {
		boolean folder = false;
		String id = "";
		String name = "";
		StringBuffer table = new StringBuffer();

		QueryManager queryManager = new QueryManager();

		String query = "";

		query = "select catalog_id ,  lable   FROM catalog WHERE active = true and lang_id = ? and site_id = ? and parent_id = ? limit 50 offset ?";

		try {

			queryManager.executeQueryWithArgs(query, new Object[] { authorizationPageBeanId.getLangId(), authorizationPageBeanId.getSiteId(), authorizationPageBeanId.getCatalogParentId(), offset });

			for (int i = 0; queryManager.rows().size() > i; i++) {
				id = (String) queryManager.getValueAt(i, 0);
				name = (String) queryManager.getValueAt(i, 1);
				folder = isFolder(id);

			}

		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			queryManager.close();
		}

		return table.toString();
	}

	/**
	 * @return true if the given id is a folder rather than a product
	 */
	public boolean isFolder(String parentId) {
		QueryManager queryManager = new QueryManager();
		String query = "";
		long count = 0;
		try {

			query = "select count (catalog_id) as number  FROM catalog WHERE  parent_id  = " + parentId;
			queryManager.executeQuery(query);
			if (queryManager.rows().size() > 0) {
				count = Long.parseLong((String) queryManager.getValueAt(0, 0));
			}
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			queryManager.close();
		}

		return count == 0 ? false : true;
	}

	/**
	 * @return the URL path of the user's current catalogue
	 */
	public String getCatalogUrlPath(AuthorizationPageBean authorizationPageBeanId) {
		// if( localization == null ) localization =
		// PropertyResourceBundle.getBundle("localization", locale);
		QueryManager queryManager = new QueryManager();
		String query = "";
		long parentId = 0;
		long parentIdLast = 0;
		long catalogId = 0;
		String lable = "";
		// String lable_last =
		// authorizationPageBeanId.getLocalization(applicationContext).getString("section_of_list_catalog")
		// ;
		String lableLast = "";
		boolean doWhile = true;
		StringBuffer path = new StringBuffer();
		if (authorizationPageBeanId.getCatalogId().length() > 0)
			parentId = authorizationPageBeanId.getCatalogParentId();
		if (parentId == 0)
			return lableLast;
		String item = "";

		try {

			while (doWhile) {
				// Adp.BeginTransaction();
				query = "select parent_id , lable   FROM catalog WHERE  catalog_id  = " + parentId;
				queryManager.executeQuery(query);
				if (queryManager.rows().size() > 0) {
					if (((String) queryManager.getValueAt(0, 0)).length() > 0)
						parentIdLast = Long.parseLong((String) queryManager.getValueAt(0, 0));
					lable = (String) queryManager.getValueAt(0, 1);
					doWhile = true;
					// path.insert(0,"/" + lable) ;
					item = "<a href=\"ProductPostCre.jsp?parent_id=" + parentId + "\" >" + "/" + lable + "</a>";
					path.insert(0, item);
					parentId = parentIdLast;

				} else
					doWhile = false;
				// Adp.commit();
			}

			item = "<a href=\"ProductPostCre.jsp?parent_id=" + parentIdLast + "\" >" + "/" + lableLast + "</a>";
			path.insert(0, item);

		} catch (SQLException ex) {
			// Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			// Adp.rollback();
			log.error(ex);
		} finally {
			queryManager.close();
		}

		return path.toString();
	}

}
