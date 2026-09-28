package com.cbsinc.cms.faceds;

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
import java.sql.SQLException;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.Currency;
import com.cbsinc.cms.CurrencyHash;
import com.cbsinc.cms.ItemDescriptionBean;
import com.cbsinc.cms.QueryManager;
import com.cbsinc.cms.controllers.Layout;
import com.cbsinc.cms.controllers.SpecialCatalog;
import com.cbsinc.cms.exceptions.LocalException;

/**
 * Business logic of the product information page: statistics, ratings, and the offer / auction amounts shown to the current user.
 */
public class ProductInfoFaced extends com.cbsinc.cms.WebControls {

	/**
	 * <p>
	 * Title: Content Manager System
	 * </p>
	 * <p>
	 * Description: System building web application develop by Konstantin Grabko.
	 * Konstantin Grabko is Owner and author this code. Программный код написан
	 * Грабко Константином Владимировичем и является его интеллектуальной
	 * собственностью.
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2008
	 * </p>
	 * <p>
	 * Company: Предприниматель Грабко Константин Владимирович
	 * </p>
	 *
	 * @author Konstantin Grabko
	 * @version 1.0
	 */

	final static private Logger log = Logger.getLogger(ProductInfoFaced.class);
	final ResourceBundle setupResources = PropertyResourceBundle.getBundle("appconfig");
	final ResourceBundle sequencesRs = PropertyResourceBundle.getBundle("sequence");
	float fltEndAmount = (float) 0.01;

	public ProductInfoFaced() {
		String amount = setupResources.getString("pay_for_user_session");
		fltEndAmount = Float.parseFloat(amount != null ? amount : "0");
	}

	/**
	 * Increments the view counter of the product.
	 * @return the new counter value
	 * @throws SQLException on failure
	 */
	final public long incrementShowPageStatistics(final ItemDescriptionBean productInfoBean) throws SQLException {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		long statictic = 0;
		String query = "SELECT STATISTIC_ID  FROM soft where soft_id = ?";
		try {
			Adp.executeQueryWithArgs(query, new Object[] { productInfoBean.getProductId() });
			if (Adp.rows().size() > 0) {
				statictic = Long.parseLong(Adp.getValueAt(0, 0));
				statictic = statictic + 1;

				query = "update soft set  STATISTIC_ID = ? where soft_id = ?";

				Map args = Adp.getArgs();
				args.put("STATISTIC_ID", statictic);
				args.put("soft_id", productInfoBean.getProductId());
				Adp.executeUpdateWithArgs(query, args);
			}
			Adp.commit();
		} catch (SQLException ex) {
			log.error(ex);
			System.err.println("Method " + "incrementShowPage()");
			System.err.println(query);
			Adp.rollback();
			throw ex;
		} finally {
			Adp.close();
		}
		return statictic;
	}

	/**
	 * Fills the payment information block of the product page.
	 * @return the page id
	 */
	final public long setPaymentInfoPage(final ItemDescriptionBean productInfoBean,
			final AuthorizationPageBean authorizationPageBean, final String userIPAddress) throws Exception {

		if (authorizationPageBean.getRoleId() == 2)
			return 0;

		QueryManager Adp = new QueryManager();

		long statictic = 0;
		long ownerSite = 0;
		String query = "";
		// query = "SELECT user_id , login , passwd , first_name , last_name , e_mail ,
		// phone , mobil_phone,fax,icq,website,question,answer,levelup_cd ,bank_cd ,
		// company , country_id , city_id , currency_id " +
		// " FROM tuser where levelup_cd = 2 and site_id = " +
		// authorizationPageBean.getSite_id() + "" ;
		// query = "SELECT user_id , login , passwd , first_name , last_name , e_mail ,
		// phone , mobil_phone,fax,icq,website,question,answer,levelup_cd ,bank_cd ,
		// company , country_id , city_id , currency_id " +
		// " FROM tuser where levelup_cd = 1 and site_id = " +
		// authorizationPageBean.getSite_id() + "" ;
		query = "SELECT user_id , login , passwd , first_name , last_name , e_mail , phone , mobil_phone,fax,icq,website,question,answer,levelup_cd ,bank_cd , company , country_id , city_id ,  currency_id FROM tuser  where  levelup_cd = 1  and login in (	SELECT login  FROM tuser  where  levelup_cd = 2 and site_id = ?  )";

		try {
			Adp.beginTransaction();
			Adp.executeQueryWithArgs(query, new Object[] { authorizationPageBean.getSiteId() });

			if (Adp.rows().size() > 0) {
				ownerSite = Long.parseLong((String) Adp.getValueAt(0, 0));
			} else
				throw new LocalException();

			query = "SELECT STATISTIC_ID  FROM soft where soft_id = ?";
			Adp.executeQueryWithArgs(query, new Object[] { productInfoBean.getProductId() });
			if (Adp.rows().size() == 0)
				throw new LocalException();
			statictic = Long.parseLong(Adp.getValueAt(0, 0));
			statictic = statictic + 1;

			query = "update soft set  STATISTIC_ID = ? where soft_id = ?";

			Map statArgs = Adp.getArgs();
			statArgs.put("STATISTIC_ID", statictic);
			statArgs.put("soft_id", productInfoBean.getProductId());
			Adp.executeUpdateWithArgs(query, statArgs);

			// if (orderBean.getAccount_history_id().length() == 0) {
			// int intUser_id = Integer.parseInt(orderBean.getUser_ID());
			float Balans = getBalans(ownerSite);

			float fltTotalAmount = (Balans - fltEndAmount);

			float fltWithtaxTotalAmount = fltTotalAmount;
			// query = "SELECT NEXT VALUE FOR account_hist_id_seq AS ID FROM ONE_SEQUENCES";
			query = sequencesRs.getString("account_hist");
			Adp.executeQuery(query);
			String accountHistoryId = Adp.getValueAt(0, 0);
			String strAccountCurrencyId = "-1";
			query = "SELECT currency_id from account WHERE  user_id = " + ownerSite;
			Adp.executeQuery(query);
			strAccountCurrencyId = Adp.getValueAt(0, 0);

			CurrencyHash currencyHash = CurrencyHash.getInstance();
			Currency curr = currencyHash.getCurrency(strAccountCurrencyId);

			query = "insert into account_hist ( id  , " + " user_id ,  order_id ,  add_amount , "
					+ " old_amount  ,  date_input ,  date_end , " + " complete   ,  decsription  , "
					+ " currency_id_add  ,  currency_id_old  , " + " currency_id_total  ,  active  , "
					+ " account_hist.sysdate  ,  total_amount ,  tax  , " + " withtax_total_amount , rate )"
					+ " VALUES " + "( ?  , " + " ?  ,  ? ,  ? , " + " ?  ,  ? ,  ? , " + " ?  ,  ?  , " + " ?  ,  ?  , "
					+ " ?  ,  ?  , " + " ?  ,  ? ,  ?  , " + " ?  , ? )";

			/// Adp.executeUpdate(query);

			Map args = Adp.getArgs();
			args.put("id", Long.parseLong(accountHistoryId));
			args.put("user_id", ownerSite);
			args.put("order_id", 0);
			args.put("add_amount", fltEndAmount * -1);
			args.put("old_amount", Balans);
			args.put("date_input", new java.util.Date());
			args.put("date_end", new java.util.Date());
			args.put("complete", true);
			args.put("decsription",
					" Payment  " + fltEndAmount + " for user which is visited site from IP  " + userIPAddress);
			args.put("currency_id_add", Long.parseLong(strAccountCurrencyId));
			args.put("currency_id_old", Long.parseLong(strAccountCurrencyId));
			args.put("currency_id_total", Long.parseLong(strAccountCurrencyId));
			args.put("active", false);
			args.put("account_hist.sysdate", new java.util.Date());
			args.put("total_amount", fltTotalAmount);
			args.put("tax", 1);
			args.put("withtax_total_amount", fltWithtaxTotalAmount);
			args.put("rate", curr.getRate());
			Adp.executeInsertWithArgs(query, args);

			query = "update account set amount = ?  where  user_id = " + ownerSite;
			args.clear();
			args.put("amount", (Balans - fltEndAmount));
			Adp.executeUpdateWithArgs(query, args);

			Adp.commit();
		} catch (SQLException ex) {
			log.error(ex);
			System.err.println("Method " + "payMoneyForShowPage()");
			System.err.println(query);
			Adp.rollback();
			throw ex;
		} catch (LocalException ex) {
			Adp.rollback();
		} finally {
			Adp.close();
		}

		return statictic;
	}

//public long payMoneyForShowPage_old(final ProductInfoLegacyBean productInfoBean , final AuthorizationPageBean authorizationPageBean  , String userIPAddress  ) throws SQLException {
//
//		QueryManager Adp = new QueryManager();
//		Adp.BeginTransaction();
//		long statictic = 0 ;
//		long userMainSite = 0 ;
//		String query = "" ;
//		query = "SELECT user_id , login , passwd , first_name , last_name , e_mail , phone , mobil_phone,fax,icq,website,question,answer,levelup_cd ,bank_cd , company , country_id , city_id ,  currency_id  " +
//		"FROM tuser  where  login = '" + authorizationPageBean.getStrLogin()  + "' and  site_id = " + SiteType.MAIN_SITE  ;
//		try
//		{
//
//		Adp.executeQuery(query);
//
//		if (Adp.rows().size() == 1)
//		{
//			userMainSite =  Integer.parseInt((String) Adp.getValueAt(0, 0));
//		}
//
//		query = "SELECT STATISTIC_ID  FROM soft where soft_id = " + productInfoBean.getProduct_id() ;
//			Adp.executeQuery(query);
//			statictic = Long.parseLong(Adp.getValueAt(0, 0));
//			statictic = statictic + 1;
//
//			query = "update soft set  STATISTIC_ID = " + statictic + " where soft_id = " +  productInfoBean.getProduct_id() ;
//
//			Adp.executeUpdate(query);
//
//			//if (orderBean.getAccount_history_id().length() == 0) {
//				//int intUser_id = Integer.parseInt(orderBean.getUser_ID());
//				float Balans = getBalans(userMainSite);
//
//				float fltTotal_amount = (Balans - fltEnd_amount);
//
//				float fltWithtaxTotal_amount = fltTotal_amount ;
//				//query = "SELECT NEXT VALUE FOR account_hist_id_seq  AS ID  FROM ONE_SEQUENCES";
//				query = sequences_rs.getString("account_hist");
//				Adp.executeQuery(query);
//				String account_history_id = Adp.getValueAt(0, 0);
//				String strAccountCurrency_id = "-1";
//				query = "SELECT currency_id from account WHERE  user_id = " + userMainSite;
//				Adp.executeQuery(query);
//				strAccountCurrency_id = Adp.getValueAt(0, 0);
//
//				CurrencyHash currencyHash = CurrencyHash.getInstance();
//				Currency curr = currencyHash.getCurrency(strAccountCurrency_id);
//
//				query = "insert into account_hist " + "(" + " id  , "
//						+ " user_id , " + " order_id , " + " add_amount , "
//						+ " old_amount  , " + " date_input , " + " date_end , "
//						+ " complete   , " + " decsription  , "
//						+ " currency_id_add  , " + " currency_id_old  , "
//						+ " currency_id_total  , " + " active  , "
//						+ " sysdate  , " + " total_amount , " + " tax  , "
//						+ " withtax_total_amount ," + " rate " + ")"
//						+ " VALUES " + "( " + account_history_id + ", "
//						+ userMainSite + ", " + 0 + ", -" + fltEnd_amount + ", "
//						+ Balans + ", " + "now()" + ", " + "now()" + ", "
//						+ "true" + ", '" + " payment  "+ fltEnd_amount +" for user which is visited site from IP  " + userIPAddress + " ', " + strAccountCurrency_id + ", "
//						+ strAccountCurrency_id + ", " + strAccountCurrency_id
//						+ ", " + "false  , " + "now()" + ", " + fltTotal_amount
//						+ ", " + 1 + ", " + fltWithtaxTotal_amount
//						+ ", " + curr.getRate() + " " + ")";
//
//				Adp.executeUpdate(query);
//				query = "UPDATE account SET amount = "+ (Balans - fltEnd_amount) + " WHERE  user_id = " + userMainSite ;
//				Adp.executeUpdate(query);
//			//}
//
//			Adp.commit();
//		}
//		catch (SQLException ex)
//		{
//			log.error(ex);
//			System.err.println("Method " + "incrementShowPage()");
//			System.err.println(query);
//			Adp.rollback();
//			throw ex;
//		}
//		finally
//		{
//			Adp.close();
//		}
//
//		return statictic ;
//	}

	/**
	 * Stores a rating vote for the product.
	 */
	final public void setRatring1(final int bal, final String productId) {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		long ratingSumm = 0;
		long ratingCount = 0;
		long midleBal = 0;
		String query = "SELECT RATING_SUMM1 , COUNTPOST_RATING1  FROM soft where soft_id = " + productId;
		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() > 0) {
				ratingSumm = Long.parseLong(Adp.getValueAt(0, 0));
				ratingSumm = ratingSumm + bal;
				ratingCount = Long.parseLong(Adp.getValueAt(0, 1));
				ratingCount = ratingCount + 1;
				midleBal = ratingSumm / ratingCount;

				query = "update soft set  RATING_SUMM1 = " + ratingSumm + " , COUNTPOST_RATING1 = " + ratingCount
						+ " , MIDLE_BAL1 = " + midleBal + " where soft_id = " + productId;
				Adp.executeUpdate(query);
			}
			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(query, ex);
			Adp.rollback();
		} finally {
			Adp.close();
		}

	}

	/**
	 * @return the product rating as XML for the template
	 */
	final public String getRatring1XML(final String productId) {
		long midleBal = 0;
		int number = 0;
		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		String query = "SELECT MIDLE_BAL1  FROM soft where soft_id = " + productId;
		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() == 0)
				return "";
			midleBal = Long.parseLong(Adp.getValueAt(0, 0));
			table.append("<rating1>\n");
			for (int i = 0; 10 > i; i++) {
				number = i + 1;
				if (midleBal > i) {
					table.append("<show_star_" + number + ">yes</show_star_" + number + ">\n");
				} else {
					table.append("<show_star_" + number + ">no</show_star_" + number + ">\n");
				}
			}
			table.append("</rating1>\n");
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
	 * Extention info 2 for policy page
	 *
	 * @param strUser_id
	 * @param site_id
	 * @param tree_id    - Установить родителя для записи обязательно
	 * @return
	 * @throws Exception
	 */

	final public void setProductInfoBean(final long userId, final String productId,
			final ItemDescriptionBean productInfoBeanId) {

		QueryManager Adp = new QueryManager();
		String query = "";

		query = "SELECT  soft.soft_id, soft.name,soft.description, soft.version, soft.cost, soft.currency, soft.serial_nubmer, file.file_id, soft.type_id, soft.active , soft.phonetype_id , soft.progname_id  , soft.image_id , images.img_url , soft.fulldescription , big_images.img_url , soft.user_id  , soft.CDATE , soft.STATISTIC_ID , tuser.FIRST_NAME , tuser.LAST_NAME , tuser.COMPANY , soft.tree_id , show_blog , show_rating1 , show_rating2 , show_rating3 , jsp_url , portlettype_id , SHOW_OFFER , SHOW_ACTION , creteria1.NAME AS NAME1 , creteria1.LABEL AS LABEL1  , creteria2.NAME AS NAME2 , creteria2.LABEL AS LABEL2  , creteria3.NAME AS NAME3 , creteria3.LABEL AS LABEL3   , creteria4.NAME AS NAME4 , creteria4.LABEL AS LABEL4   , creteria5.NAME AS NAME5 , creteria5.LABEL AS LABEL5   , creteria6.NAME AS NAME6 , creteria6.LABEL AS LABEL6 , creteria7.NAME AS NAME7 , creteria7.LABEL AS LABEL7   , creteria8.NAME AS NAME8 , creteria8.LABEL AS LABEL8   , creteria9.NAME AS NAME9 , creteria9.LABEL AS LABEL9   , creteria10.NAME AS NAME10 , creteria10.LABEL AS LABEL10  FROM soft LEFT  JOIN images ON soft.image_id = images.image_id  LEFT  JOIN big_images ON soft.bigimage_id = big_images.big_images_id  LEFT  JOIN file  ON soft.file_id = file.file_id LEFT  JOIN tuser  ON soft.user_id = tuser.user_id LEFT  JOIN  creteria1  ON soft.creteria1_id  = creteria1.creteria1_id  LEFT  JOIN  creteria2  ON soft.creteria2_id  = creteria2.creteria2_id  LEFT  JOIN  creteria3  ON soft.creteria3_id  = creteria3.creteria3_id  LEFT  JOIN  creteria4  ON soft.creteria4_id  = creteria4.creteria4_id  LEFT  JOIN  creteria5  ON soft.creteria5_id  = creteria5.creteria5_id  LEFT  JOIN  creteria6  ON soft.creteria6_id  = creteria6.creteria6_id  LEFT  JOIN  creteria7  ON soft.creteria7_id  = creteria7.creteria7_id  LEFT  JOIN  creteria8  ON soft.creteria8_id  = creteria8.creteria8_id  LEFT  JOIN  creteria9  ON soft.creteria9_id  = creteria9.creteria9_id  LEFT  JOIN  creteria10  ON soft.creteria10_id  = creteria10.creteria10_id WHERE  soft.soft_id = "
				+ productId;

		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() > 0) {
				/*
				 * size = (String) Adp.getValueAt(0, 0);
				 *
				 * file_id = position.rows[intRow_id][1]; // file_id =
				 * (String)position.newsAdp.getValueAt(intRow_id , 7) == null //
				 * ?"":(String)position.newsAdp.getValueAt(intRow_id , 7) ; if (file_id.length()
				 * > 0) file_exist = "true"; else file_exist = ""; if
				 * (position.Adp.getRowCount() == 0) { return; }
				 */

				String fileId = Adp.getValueAt(0, 7);
				if (fileId.length() > 0)
					productInfoBeanId.setFileExist("true");
				else
					productInfoBeanId.setFileExist("");

				productInfoBeanId.setProductId(Adp.getValueAt(0, 0));

				String portlettypeId = Adp.getValueAt(0, 28).toLowerCase();
				if (portlettypeId != null && portlettypeId.length() > 0)
					productInfoBeanId.setPortlettypeId(Long.parseLong(portlettypeId));
				// productInfoBeanId.setParent_product_id(productInfoBeanId.getProduct_id()) ;
				// 593
				String parentId = Adp.getValueAt(0, 22);
				if (parentId.equals(""))
					parentId = Adp.getValueAt(0, 0);
				productInfoBeanId.setParentProductId(parentId);
				productInfoBeanId.setProductName(Adp.getValueAt(0, 1));

				String showBlog = Adp.getValueAt(0, 23).toLowerCase();
				productInfoBeanId.setStrShowForum(showBlog);

				String showRatimg1 = Adp.getValueAt(0, 24).toLowerCase();
				productInfoBeanId.setStrShowRatimg1(showRatimg1);

				String showRatimg2 = Adp.getValueAt(0, 25).toLowerCase();
				productInfoBeanId.setStrShowRatimg2(showRatimg2);

				String showRatimg3 = Adp.getValueAt(0, 26).toLowerCase();
				productInfoBeanId.setStrShowRatimg3(showRatimg3);

				String jspUrl = Adp.getValueAt(0, 27).toLowerCase();
				productInfoBeanId.setJspUrl(jspUrl);

				String showOffer = Adp.getValueAt(0, 29).toLowerCase();
				productInfoBeanId.setStrShowOffer(showOffer);

				String showAction = Adp.getValueAt(0, 30).toLowerCase();
				productInfoBeanId.setStrShowAction(showAction);

				productInfoBeanId.setCreteriaName1(Adp.getValueAt(0, 31));
				productInfoBeanId.setCreteriaLabel1(Adp.getValueAt(0, 32));

				productInfoBeanId.setCreteriaName2(Adp.getValueAt(0, 33));
				productInfoBeanId.setCreteriaLabel2(Adp.getValueAt(0, 34));

				productInfoBeanId.setCreteriaName3(Adp.getValueAt(0, 35));
				productInfoBeanId.setCreteriaLabel3(Adp.getValueAt(0, 36));

				productInfoBeanId.setCreteriaName4(Adp.getValueAt(0, 37));
				productInfoBeanId.setCreteriaLabel4(Adp.getValueAt(0, 38));

				productInfoBeanId.setCreteriaName5(Adp.getValueAt(0, 39));
				productInfoBeanId.setCreteriaLabel5(Adp.getValueAt(0, 40));

				productInfoBeanId.setCreteriaName6(Adp.getValueAt(0, 41));
				productInfoBeanId.setCreteriaLabel6(Adp.getValueAt(0, 42));

				productInfoBeanId.setCreteriaName7(Adp.getValueAt(0, 43));
				productInfoBeanId.setCreteriaLabel7(Adp.getValueAt(0, 44));

				productInfoBeanId.setCreteriaName8(Adp.getValueAt(0, 45));
				productInfoBeanId.setCreteriaLabel8(Adp.getValueAt(0, 46));

				productInfoBeanId.setCreteriaName9(Adp.getValueAt(0, 47));
				productInfoBeanId.setCreteriaLabel9(Adp.getValueAt(0, 48));

				productInfoBeanId.setCreteriaName10(Adp.getValueAt(0, 49));
				productInfoBeanId.setCreteriaLabel10(Adp.getValueAt(0, 50));

				// productInfoBeanId.setProductURL("downloadservletbyrowid?row=" + 0);
				productInfoBeanId.setProductURL("downloadservletbyrowid?productid=" + productInfoBeanId.getProductId());
				productInfoBeanId.setImgUrl(Adp.getValueAt(0, 13));
				if (productInfoBeanId.getImgUrl() != null)
					productInfoBeanId.setImgURL(productInfoBeanId.getImgUrl());
				else
					productInfoBeanId.setImgURL("images/Folder.jpg");
				productInfoBeanId.setProductDescription(Adp.getValueAt(0, 14));
				int http = productInfoBeanId.getProductDescription().indexOf("http://");
				if (http != -1) {
					int space = productInfoBeanId.getProductDescription().indexOf(" ", http);
					if (space == -1)
						space = productInfoBeanId.getProductDescription().indexOf("/t", http);
					if (space != -1) {
						String link = productInfoBeanId.getProductDescription().substring(http, space);
						String newlink = " <a href='" + link + "' >" + link + "</a>  ";
						productInfoBeanId
								.setProductDescription(productInfoBeanId.getProductDescription().replaceAll(link, newlink));
					}

				}

				int ftp = productInfoBeanId.getProductDescription().indexOf("ftp://");
				if (ftp != -1) {
					int space = productInfoBeanId.getProductDescription().indexOf(" ", ftp);
					if (space == -1)
						space = productInfoBeanId.getProductDescription().indexOf("/t", ftp);
					if (space != -1) {
						String link = productInfoBeanId.getProductDescription().substring(ftp, space);
						// String newlink = "<![CDATA[ <a href='"+link+"' >" + link + "</a> ]]> " ;
						String newlink = " <a href='" + link + "' >" + link + "</a> ";
						productInfoBeanId
								.setProductDescription(productInfoBeanId.getProductDescription().replaceAll(link, newlink));
					}
				}

				int email = productInfoBeanId.getProductDescription().indexOf("mailto://");
				if (email != -1) {
					int space = productInfoBeanId.getProductDescription().indexOf(" ", email);
					if (space == -1)
						space = productInfoBeanId.getProductDescription().indexOf("/t", email);
					if (space != -1) {
						String link = productInfoBeanId.getProductDescription().substring(email, space);
						// String newlink = "<![CDATA[ <a href='"+link+"' >" + link + "</a> ]]> " ;
						String newlink = " <a href='" + link + "' >" + link + "</a>  ";
						productInfoBeanId
								.setProductDescription(productInfoBeanId.getProductDescription().replaceAll(link, newlink));
					}
				}

				productInfoBeanId.setBigimgURL(Adp.getValueAt(0, 15));
				productInfoBeanId.setProductVersion(Adp.getValueAt(0, 3));
				productInfoBeanId.setProductCost(Adp.getValueAt(0, 4));
				productInfoBeanId.setStrCDate(Adp.getValueAt(0, 17));
				// productInfoBeanId.setStrCDate(productInfoBeanId.getStrCDate().substring(0,10)) ;
				productInfoBeanId.setStatistic(Adp.getValueAt(0, 18));

				productInfoBeanId.setCreatorInfoUserId(Adp.getValueAt(0, 16));
				// creator_info_user_id= (String) query_result.getValueAt(intRow_id, 16);

				String currencyId = (String) Adp.getValueAt(0, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				Currency curr = currencyHash.getCurrency(currencyId);
				if (curr == null)
					throw new Exception("Currency curr == null and currency_id " + currencyId);
				productInfoBeanId.setCurrencyCd(curr.getCode());
				productInfoBeanId.setCurrencyDesc(currencyHash.getCurrencyDecs(currencyId));
				// Not momey not href
				if (getBalans(userId) < 1)
					productInfoBeanId.setProductURL("");

			}

			/// Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			// Adp.rollback();

		} catch (Exception ex) {
			log.error(ex);
			// Adp.rollback();

		} finally {
			Adp.close();
		}

	}




	/**
	 * Puts the current user's offer amount on the product on the bean.
	 */
	final public void setProductInfoBeanForOfferAmount(final String productId, final AuthorizationPageBean authorizationPageBean ,
			final ItemDescriptionBean itemDescriptionBean) {

		QueryManager Adp = new QueryManager();
		String query = "SELECT  soft.soft_id, soft.cost FROM soft WHERE soft.catalog_id = " + SpecialCatalog.OFFERS_CATALOG
				+ " and soft.tree_id = ? and soft.active = true and soft.lang_id = ? and soft.user_id = ? ORDER BY soft.soft_id DESC ";
		try {
			Adp.executeQueryWithArgs(query, new Object[] { productId, authorizationPageBean.getLangId(), authorizationPageBean.getIntUserID() });
			if (Adp.rows().size() > 0) {
				itemDescriptionBean.setOfferAmount(Adp.getValueAt(0, 1));
			}

			/// Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			// Adp.rollback();

		} catch (Exception ex) {
			log.error(ex);
			// Adp.rollback();

		} finally {
			Adp.close();
		}

	}


	/**
	 * Puts the current maximum auction bid and whether the user may bid on the bean.
	 */
	final public void setProductInfoBeanForAuctionMaxBidAmount(final String productId, final AuthorizationPageBean authorizationPageBean ,
			final ItemDescriptionBean itemDescriptionBean) {

		QueryManager Adp = new QueryManager();
		String query = "";

		// FIX: a cancelled or losing bid must not be shown as the current maximum
		query = "SELECT max( cost ) FROM soft WHERE soft.catalog_id = "+ SpecialCatalog.AUCTION_BID_CATALOG
				+ " and soft.tree_id = " + productId
				+ " and soft.active = true and soft.ACTION_BID_STATUS_ID in ( "
				+ com.cbsinc.cms.controllers.AuctionBidStatus.PRODUCT_AUCTION_BID_SUBMITED + " , "
				+ com.cbsinc.cms.controllers.AuctionBidStatus.PRODUCT_AUCTION_BID_WON + " ) " ;
		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() > 0) {
				String auctionMaxBidAmount = Adp.getValueAt(0, 0) ;
				if( auctionMaxBidAmount == null || auctionMaxBidAmount.length() == 0 ) auctionMaxBidAmount = "0" ;
				itemDescriptionBean.setAuctionMaxBidAmount(auctionMaxBidAmount);
			}

			/// Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			// Adp.rollback();

		} catch (Exception ex) {
			log.error(ex);
			// Adp.rollback();

		} finally {
			Adp.close();
		}

	}



	/**
	 * Extention info 2 for policy page
	 *
	 * @param strUser_id
	 * @param site_id
	 * @param tree_id    - Установить родителя для записи обязательно
	 * @return
	 * @throws Exception
	 */

	final public void setProductInfoBeanForAboutPage(final String siteId, final ItemDescriptionBean productInfoBeanId) {

		QueryManager Adp = new QueryManager();
		String query = "";

		query = "SELECT  soft.soft_id, soft.name,soft.description, soft.version, soft.cost, soft.currency, soft.serial_nubmer, file.file_id, soft.type_id, soft.active , soft.phonetype_id , soft.progname_id  , soft.image_id , images.img_url , soft.fulldescription , big_images.img_url , soft.user_id  , soft.CDATE , soft.STATISTIC_ID , tuser.FIRST_NAME , tuser.LAST_NAME , tuser.COMPANY , soft.tree_id ,  show_blog , show_rating1 , show_rating2 , show_rating3 , jsp_url  FROM soft LEFT  JOIN images ON soft.image_id = images.image_id  LEFT  JOIN big_images ON soft.bigimage_id = big_images.big_images_id  LEFT  JOIN file  ON soft.file_id = file.file_id LEFT  JOIN tuser  ON soft.user_id = tuser.user_id  WHERE  soft.site_id = "
				+ siteId + " and soft.portlettype_id = " + Layout.FILES_ON_PRODUCTINFO_PAGE;

		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() > 0) {
				/*
				 * size = (String) Adp.getValueAt(0, 0);
				 *
				 * file_id = position.rows[intRow_id][1]; // file_id =
				 * (String)position.newsAdp.getValueAt(intRow_id , 7) == null //
				 * ?"":(String)position.newsAdp.getValueAt(intRow_id , 7) ; if (file_id.length()
				 * > 0) file_exist = "true"; else file_exist = ""; if
				 * (position.Adp.getRowCount() == 0) { return; }
				 */

				String fileId = Adp.getValueAt(0, 7);
				if (fileId.length() > 0)
					productInfoBeanId.setFileExist("true");
				else
					productInfoBeanId.setFileExist("");

				productInfoBeanId.setProductId(Adp.getValueAt(0, 0));
				// productInfoBeanId.setParent_product_id(productInfoBeanId.getProduct_id()) ;
				// 593
				String parentId = Adp.getValueAt(0, 22);
				if (parentId.equals(""))
					parentId = Adp.getValueAt(0, 0);
				productInfoBeanId.setParentProductId(parentId);

				String showBlog = Adp.getValueAt(0, 23).toLowerCase();
				productInfoBeanId.setStrShowForum(showBlog);

				String showRatimg1 = Adp.getValueAt(0, 24).toLowerCase();
				productInfoBeanId.setStrShowRatimg1(showRatimg1);

				String showRatimg2 = Adp.getValueAt(0, 25).toLowerCase();
				productInfoBeanId.setStrShowRatimg2(showRatimg2);

				String showRatimg3 = Adp.getValueAt(0, 26).toLowerCase();
				productInfoBeanId.setStrShowRatimg3(showRatimg3);

				String jspUrl = Adp.getValueAt(0, 27).toLowerCase();
				productInfoBeanId.setJspUrl(jspUrl);

				productInfoBeanId.setProductName(Adp.getValueAt(0, 1));
				// productInfoBeanId.setProductURL("downloadservletbyrowid?row=" + 0);
				productInfoBeanId.setProductURL("downloadservletbyrowid?productid=" + productInfoBeanId.getProductId());
				productInfoBeanId.setImgUrl(Adp.getValueAt(0, 13));
				if (productInfoBeanId.getImgUrl() != null)
					productInfoBeanId.setImgURL(productInfoBeanId.getImgUrl());
				else
					productInfoBeanId.setImgURL("images/Folder.jpg");
				productInfoBeanId.setProductDescription(Adp.getValueAt(0, 14));
				int http = productInfoBeanId.getProductDescription().indexOf("http://");
				if (http != -1) {
					int space = productInfoBeanId.getProductDescription().indexOf(" ", http);
					if (space == -1)
						space = productInfoBeanId.getProductDescription().indexOf("/t", http);
					if (space != -1) {
						String link = productInfoBeanId.getProductDescription().substring(http, space);
						String newlink = " <a href='" + link + "' >" + link + "</a>  ";
						productInfoBeanId
								.setProductDescription(productInfoBeanId.getProductDescription().replaceAll(link, newlink));
					}

				}

				int ftp = productInfoBeanId.getProductDescription().indexOf("ftp://");
				if (ftp != -1) {
					int space = productInfoBeanId.getProductDescription().indexOf(" ", ftp);
					if (space == -1)
						space = productInfoBeanId.getProductDescription().indexOf("/t", ftp);
					if (space != -1) {
						String link = productInfoBeanId.getProductDescription().substring(ftp, space);
						// String newlink = "<![CDATA[ <a href='"+link+"' >" + link + "</a> ]]> " ;
						String newlink = " <a href='" + link + "' >" + link + "</a> ";
						productInfoBeanId
								.setProductDescription(productInfoBeanId.getProductDescription().replaceAll(link, newlink));
					}
				}

				int email = productInfoBeanId.getProductDescription().indexOf("mailto://");
				if (email != -1) {
					int space = productInfoBeanId.getProductDescription().indexOf(" ", email);
					if (space == -1)
						space = productInfoBeanId.getProductDescription().indexOf("/t", email);
					if (space != -1) {
						String link = productInfoBeanId.getProductDescription().substring(email, space);
						// String newlink = "<![CDATA[ <a href='"+link+"' >" + link + "</a> ]]> " ;
						String newlink = " <a href='" + link + "' >" + link + "</a>  ";
						productInfoBeanId
								.setProductDescription(productInfoBeanId.getProductDescription().replaceAll(link, newlink));
					}
				}

				productInfoBeanId.setBigimgURL(Adp.getValueAt(0, 15));
				productInfoBeanId.setProductVersion(Adp.getValueAt(0, 3));
				productInfoBeanId.setProductCost(Adp.getValueAt(0, 4));
				productInfoBeanId.setStrCDate(Adp.getValueAt(0, 17));
				// productInfoBeanId.setStrCDate(productInfoBeanId.getStrCDate().substring(0,10)) ;
				productInfoBeanId.setStatistic(Adp.getValueAt(0, 18));

				productInfoBeanId.setCreatorInfoUserId(Adp.getValueAt(0, 16));
				// creator_info_user_id= (String) query_result.getValueAt(intRow_id, 16);

				String currencyId = (String) Adp.getValueAt(0, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				Currency curr = currencyHash.getCurrency(currencyId);
				if (curr == null)
					throw new Exception("Currency curr == null and currency_id = " + currencyId);
				productInfoBeanId.setCurrencyCd(curr.getCode());
				productInfoBeanId.setCurrencyDesc(currencyHash.getCurrencyDecs(currencyId));
				// Not momey not href
				// if(getBalans(user_id) < 1 ) productInfoBeanId.setProductURL("");

			}

			/// Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			// Adp.rollback();

		} catch (Exception ex) {
			log.error(ex);
			// Adp.rollback();

		} finally {
			Adp.close();
		}

	}

	/**
	 * Extention info 2 for policy page
	 *
	 * @param strUser_id
	 * @param site_id
	 * @param tree_id    - Установить родителя для записи обязательно
	 * @return
	 * @throws Exception
	 */

	final public void setProductInfoBeanForPayPageInfo(final String siteId, final ItemDescriptionBean productInfoBeanId) {

		QueryManager Adp = new QueryManager();
		String query = "";

		query = "SELECT  soft.soft_id, soft.name,soft.description, soft.version, soft.cost, soft.currency, soft.serial_nubmer, file.file_id, soft.type_id, soft.active , soft.phonetype_id , soft.progname_id  , soft.image_id , images.img_url , soft.fulldescription , big_images.img_url , soft.user_id  , soft.CDATE , soft.STATISTIC_ID , tuser.FIRST_NAME , tuser.LAST_NAME , tuser.COMPANY , soft.tree_id ,  show_blog , show_rating1 , show_rating2 , show_rating3 , jsp_url  FROM soft LEFT  JOIN images ON soft.image_id = images.image_id  LEFT  JOIN big_images ON soft.bigimage_id = big_images.big_images_id  LEFT  JOIN file  ON soft.file_id = file.file_id LEFT  JOIN tuser  ON soft.user_id = tuser.user_id  WHERE  soft.site_id = "
				+ siteId + " and soft.portlettype_id = " + Layout.PAGE_ABOUT_PAY;

		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() > 0) {
				/*
				 * size = (String) Adp.getValueAt(0, 0);
				 *
				 * file_id = position.rows[intRow_id][1]; // file_id =
				 * (String)position.newsAdp.getValueAt(intRow_id , 7) == null //
				 * ?"":(String)position.newsAdp.getValueAt(intRow_id , 7) ; if (file_id.length()
				 * > 0) file_exist = "true"; else file_exist = ""; if
				 * (position.Adp.getRowCount() == 0) { return; }
				 */

				String fileId = Adp.getValueAt(0, 7);
				if (fileId.length() > 0)
					productInfoBeanId.setFileExist("true");
				else
					productInfoBeanId.setFileExist("");

				productInfoBeanId.setProductId(Adp.getValueAt(0, 0));
				// productInfoBeanId.setParent_product_id(productInfoBeanId.getProduct_id()) ;
				// 593
				String parentId = Adp.getValueAt(0, 22);
				if (parentId.equals(""))
					parentId = Adp.getValueAt(0, 0);
				productInfoBeanId.setParentProductId(parentId);

				String showBlog = Adp.getValueAt(0, 23).toLowerCase();
				productInfoBeanId.setStrShowForum(showBlog);

				String showRatimg1 = Adp.getValueAt(0, 24).toLowerCase();
				productInfoBeanId.setStrShowRatimg1(showRatimg1);

				String showRatimg2 = Adp.getValueAt(0, 25).toLowerCase();
				productInfoBeanId.setStrShowRatimg2(showRatimg2);

				String showRatimg3 = Adp.getValueAt(0, 26).toLowerCase();
				productInfoBeanId.setStrShowRatimg3(showRatimg3);

				String jspUrl = Adp.getValueAt(0, 27).toLowerCase();
				productInfoBeanId.setJspUrl(jspUrl);

				productInfoBeanId.setProductName(Adp.getValueAt(0, 1));
				// productInfoBeanId.setProductURL("downloadservletbyrowid?row=" + 0);
				productInfoBeanId.setProductURL("downloadservletbyrowid?productid=" + productInfoBeanId.getProductId());
				productInfoBeanId.setImgUrl(Adp.getValueAt(0, 13));
				if (productInfoBeanId.getImgUrl() != null)
					productInfoBeanId.setImgURL(productInfoBeanId.getImgUrl());
				else
					productInfoBeanId.setImgURL("images/Folder.jpg");
				productInfoBeanId.setProductDescription(Adp.getValueAt(0, 14));
				int http = productInfoBeanId.getProductDescription().indexOf("http://");
				if (http != -1) {
					int space = productInfoBeanId.getProductDescription().indexOf(" ", http);
					if (space == -1)
						space = productInfoBeanId.getProductDescription().indexOf("/t", http);
					if (space != -1) {
						String link = productInfoBeanId.getProductDescription().substring(http, space);
						String newlink = " <a href='" + link + "' >" + link + "</a>  ";
						productInfoBeanId
								.setProductDescription(productInfoBeanId.getProductDescription().replaceAll(link, newlink));
					}

				}

				int ftp = productInfoBeanId.getProductDescription().indexOf("ftp://");
				if (ftp != -1) {
					int space = productInfoBeanId.getProductDescription().indexOf(" ", ftp);
					if (space == -1)
						space = productInfoBeanId.getProductDescription().indexOf("/t", ftp);
					if (space != -1) {
						String link = productInfoBeanId.getProductDescription().substring(ftp, space);
						// String newlink = "<![CDATA[ <a href='"+link+"' >" + link + "</a> ]]> " ;
						String newlink = " <a href='" + link + "' >" + link + "</a> ";
						productInfoBeanId
								.setProductDescription(productInfoBeanId.getProductDescription().replaceAll(link, newlink));
					}
				}

				int email = productInfoBeanId.getProductDescription().indexOf("mailto://");
				if (email != -1) {
					int space = productInfoBeanId.getProductDescription().indexOf(" ", email);
					if (space == -1)
						space = productInfoBeanId.getProductDescription().indexOf("/t", email);
					if (space != -1) {
						String link = productInfoBeanId.getProductDescription().substring(email, space);
						// String newlink = "<![CDATA[ <a href='"+link+"' >" + link + "</a> ]]> " ;
						String newlink = " <a href='" + link + "' >" + link + "</a>  ";
						productInfoBeanId
								.setProductDescription(productInfoBeanId.getProductDescription().replaceAll(link, newlink));
					}
				}

				productInfoBeanId.setBigimgURL(Adp.getValueAt(0, 15));
				productInfoBeanId.setProductVersion(Adp.getValueAt(0, 3));
				productInfoBeanId.setProductCost(Adp.getValueAt(0, 4));
				productInfoBeanId.setStrCDate(Adp.getValueAt(0, 17));
				// productInfoBeanId.setStrCDate(productInfoBeanId.getStrCDate().substring(0,10)) ;
				productInfoBeanId.setStatistic(Adp.getValueAt(0, 18));

				productInfoBeanId.setCreatorInfoUserId(Adp.getValueAt(0, 16));
				// creator_info_user_id= (String) query_result.getValueAt(intRow_id, 16);

				String currencyId = (String) Adp.getValueAt(0, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				Currency curr = currencyHash.getCurrency(currencyId);
				if (curr == null)
					throw new Exception("Currency curr == null and currency_id = " + currencyId);
				productInfoBeanId.setCurrencyCd(curr.getCode());
				productInfoBeanId.setCurrencyDesc(currencyHash.getCurrencyDecs(currencyId));
				// Not momey not href
				// if(getBalans(user_id) < 1 ) productInfoBeanId.setProductURL("");

			}

			/// Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			// Adp.rollback();

		} catch (Exception ex) {
			log.error(ex);
			// Adp.rollback();

		} finally {
			Adp.close();
		}

	}

	/**
	 * @return the id of the site's payment information page
	 */
	final public String getPayPageInfoId(final String siteId) {
		QueryManager Adp = new QueryManager();
		String query = "";
		String productId = "0";
		query = "SELECT  soft.soft_id, soft.name,soft.description, soft.version, soft.cost, soft.currency, soft.serial_nubmer, file.file_id, soft.type_id, soft.active , soft.phonetype_id , soft.progname_id  , soft.image_id , images.img_url , soft.fulldescription , big_images.img_url , soft.user_id  , soft.CDATE , soft.STATISTIC_ID , tuser.FIRST_NAME , tuser.LAST_NAME , tuser.COMPANY , soft.tree_id ,  show_blog , show_rating1 , show_rating2 , show_rating3 , jsp_url  FROM soft LEFT  JOIN images ON soft.image_id = images.image_id  LEFT  JOIN big_images ON soft.bigimage_id = big_images.big_images_id  LEFT  JOIN file  ON soft.file_id = file.file_id LEFT  JOIN tuser  ON soft.user_id = tuser.user_id  WHERE  soft.site_id = "
				+ siteId + " and soft.portlettype_id = " + Layout.PAGE_ABOUT_PAY;
		;

		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() > 0) {
				productId = Adp.getValueAt(0, 0);
			}

		} catch (SQLException ex) {
			log.error(query, ex);
			// Adp.rollback();

		} catch (Exception ex) {
			log.error(ex);
			// Adp.rollback();

		} finally {
			Adp.close();
		}

		return productId;

	}

	/**
	 * @return the id of the site's about page
	 */
	final public String getAboutPageId(final String siteId) {
		QueryManager Adp = new QueryManager();
		String query = "";
		String productId = "0";
		query = "SELECT  soft.soft_id, soft.name,soft.description, soft.version, soft.cost, soft.currency, soft.serial_nubmer, file.file_id, soft.type_id, soft.active , soft.phonetype_id , soft.progname_id  , soft.image_id , images.img_url , soft.fulldescription , big_images.img_url , soft.user_id  , soft.CDATE , soft.STATISTIC_ID , tuser.FIRST_NAME , tuser.LAST_NAME , tuser.COMPANY , soft.tree_id ,  show_blog , show_rating1 , show_rating2 , show_rating3 , jsp_url  FROM soft LEFT  JOIN images ON soft.image_id = images.image_id  LEFT  JOIN big_images ON soft.bigimage_id = big_images.big_images_id  LEFT  JOIN file  ON soft.file_id = file.file_id LEFT  JOIN tuser  ON soft.user_id = tuser.user_id  WHERE  soft.site_id = "
				+ siteId + " and soft.portlettype_id = " + Layout.FILES_ON_PRODUCTINFO_PAGE;

		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() > 0) {
				productId = Adp.getValueAt(0, 0);
			}

		} catch (SQLException ex) {
			log.error(query, ex);
			// Adp.rollback();

		} catch (Exception ex) {
			log.error(ex);
			// Adp.rollback();

		} finally {
			Adp.close();
		}

		return productId;

	}

	// --------- Business logic functionality start -----

	/**
	 * Parses an int, returning 0 for invalid input.
	 * @return the value or 0
	 */
	final public int stringToInt(final String s) {
		int i;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			i = 0;
		}
		return i;
	}

	// --------- Business logic functionality end -----

}
