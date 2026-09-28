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

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.cbsinc.cms.annotations.PageModel;
import com.cbsinc.cms.annotations.Scope;

@PageModel(Id = "payGatewayBean", scope = Scope.SESSION)
//@PageXsltView(jspName = "Pay.jsp", xsltName = "pay.xsl", responseType = Type.XML)
public class PayGatewayBean extends com.cbsinc.cms.WebControls implements java.io.Serializable {

	private static final long serialVersionUID = 338258813101694289L;

	static private Logger log = Logger.getLogger(PayGatewayBean.class);

	private String shopId = "";

	private String shopCd = "";

	private String login = "";

	private String passwd = "";

	private String ownerId = "";

	private String payGatewayId = "";

	private String nameGateway = "";

	private String rertypePasswd = "";

	private String payUrl = "";

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		payUrl = payUrl;
	}

	public void mapmingShopBean(String siteId) {

		// String query = "select shop_id , shop_cd , owner_id , login , passwd
		// , pay_gateway_id from shop where site_id = " + site_id ;
		String query = "select shop_id , shop_cd ,  owner_id , login , passwd ,  pay_gateway.pay_gateway_id  , pay_gateway.name_gateway   from shop join pay_gateway on pay_gateway.pay_gateway_id = shop.pay_gateway_id  where active = true and  shop.site_id = "
				+ siteId;
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);

			if (Adp.rows().size() != 0) {
				shopId = (String) Adp.getValueAt(0, 0);
				shopCd = (String) Adp.getValueAt(0, 1);
				ownerId = (String) Adp.getValueAt(0, 2);
				login = (String) Adp.getValueAt(0, 3);
				passwd = (String) Adp.getValueAt(0, 4);
				payGatewayId = (String) Adp.getValueAt(0, 5);
				nameGateway = (String) Adp.getValueAt(0, 6);
			} else
				log.error(
						"ERROR: select shop_id , shop_cd ,  owner_id , login , passwd , pay_gateway_id  , pay_gateway.name_gateway   from shop join pay_gateway on pay_gateway.pay_gateway_id = shop.pay_gateway_id  where active = true and  shop.site_id = "
								+ siteId + " \n   shop_cd = null ");

		} catch (SQLException ex) {

			log.error(query, ex);
		} catch (Exception ex) {

			log.error(ex);
		} finally {
			Adp.close();
		}

	}

	public void saveShopBeanBySiteId(String siteId) {
		StringBuffer buffquery = new StringBuffer();
		buffquery.append("update shop set shop_id = ").append(shopId);
		buffquery.append(" , shop_cd  = ").append(shopCd);
		buffquery.append(" , owner_id  = ").append(ownerId);
		buffquery.append(" , login  = '").append(login);
		buffquery.append("' , passwd  = '").append(passwd);
		buffquery.append("' , pay_gateway_id  = ").append(payGatewayId);
		buffquery.append(" from shop where site_id = ").append(siteId);

		QueryManager Adp = new QueryManager();
		try {
			Adp.executeUpdate(buffquery.toString());

			if (Adp.rows().size() != 0) {
				shopId = (String) Adp.getValueAt(0, 0);
				shopCd = (String) Adp.getValueAt(0, 1);
				ownerId = (String) Adp.getValueAt(0, 2);
				login = (String) Adp.getValueAt(0, 3);
				passwd = (String) Adp.getValueAt(0, 4);
				payGatewayId = (String) Adp.getValueAt(0, 5);
			}
		} catch (SQLException ex) {

			log.error(buffquery.toString(), ex);
		} catch (Exception ex) {

			log.error(ex);
		} finally {
			Adp.close();
		}

	}

	public void saveShopBean() {
		StringBuffer buffquery = new StringBuffer();
		buffquery.append("update shop set ");
		buffquery.append(" shop_cd  = ").append(shopCd);
		// buffquery.append(" , owner_id = ").append(owner_id) ;
		buffquery.append(" , login  = '").append(login);
		buffquery.append("' , passwd  = '").append(passwd);
		buffquery.append("' , pay_gateway_id  = ").append(payGatewayId);
		buffquery.append("  where shop_id = ").append(shopId);

		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		try {
			Adp.executeUpdate(buffquery.toString());
			Adp.commit();

			if (Adp.rows().size() != 0) {
				shopId = (String) Adp.getValueAt(0, 0);
				shopCd = (String) Adp.getValueAt(0, 1);
				ownerId = (String) Adp.getValueAt(0, 2);
				login = (String) Adp.getValueAt(0, 3);
				passwd = (String) Adp.getValueAt(0, 4);
				payGatewayId = (String) Adp.getValueAt(0, 5);
			}
		} catch (SQLException ex) {
			log.error(buffquery.toString(), ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
		} finally {
			Adp.close();
		}
	}

	public void addShopBean(String siteId) {
		StringBuffer buffquery = new StringBuffer();
		// insert into shop(shop_cd , login , passwd , pay_gateway_id , site_id
		// ) values( shop_cd , login , passwd , pay_gateway_id , site_id )
		buffquery.append("insert into shop(shop_cd , login , passwd , pay_gateway_id , site_id  ) values(");
		buffquery.append(shopCd).append(",");
		buffquery.append("'").append(login).append("',");
		buffquery.append("'").append(passwd).append("',");
		buffquery.append(payGatewayId).append(",");
		buffquery.append(siteId).append(" )");

		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		try {
			Adp.executeUpdate(buffquery.toString());

			if (Adp.rows().size() != 0) {
				shopId = (String) Adp.getValueAt(0, 0);
				shopCd = (String) Adp.getValueAt(0, 1);
				ownerId = (String) Adp.getValueAt(0, 2);
				login = (String) Adp.getValueAt(0, 3);
				passwd = (String) Adp.getValueAt(0, 4);
				payGatewayId = (String) Adp.getValueAt(0, 5);
			}

		} catch (SQLException ex) {
			log.error(buffquery.toString(), ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
		} finally {
			Adp.close();
		}
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopCd() {
		return shopCd;
	}

	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getPayGatewayId() {
		return payGatewayId;
	}

	public void setPayGatewayId(String payGatewayId) {
		this.payGatewayId = payGatewayId;
	}

	public String getNameGateway() {
		return nameGateway;
	}

	public void setNameGateway(String nameGateway) {
		this.nameGateway = nameGateway;
	}

	public String getRertypePasswd() {
		return rertypePasswd;
	}

	public void setRertypePasswd(String rertypePasswd) {
		this.rertypePasswd = rertypePasswd;
	}

}
