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

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.cbsinc.cms.annotations.PageModel;
import com.cbsinc.cms.annotations.Scope;

@PageModel(Id = "payGatewayListBeanId", scope = Scope.SESSION)
//@PageXsltView(jspName = "PayGatewayList.jsp", xsltName = "pay.xsl", responseType = Type.XML)
public class PayGatewayListBean implements Serializable {

	private static final long serialVersionUID = -1197050877775233920L;

	static private Logger log = Logger.getLogger(PayGatewayListBean.class);

	private String limit = "10";

	private String selectShopId = "0";

	private String selectNameGateway = "0";

	private String siteId = "0";

	private LinkedList<PayGatewayBean> listShopSetupBean = new LinkedList<PayGatewayBean>();

	private Integer indxSelect = 0;

	private Integer offset = 0;

	private String cururl;

	private String listup;

	private String listdown;

	public PayGatewayListBean() {
		// Adp.rows
	}

	public void mapmingShopBean(String siteId) {
		listShopSetupBean.clear();
		// select shop_id , shop_cd , owner_id , login , passwd , site_id from
		// shop where site_id > 0 ;
		String query = "select shop_id , shop_cd ,  owner_id , login , passwd ,  pay_gateway.pay_gateway_id  , pay_gateway.name_gateway , pay_gateway.site_url  from shop join pay_gateway on pay_gateway.pay_gateway_id = shop.pay_gateway_id  where active = true and  shop.site_id = "
				+ siteId + " limit  " + limit + " offset " + offset;
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);

			for (int i = 0; Adp.rows().size() > i; i++) {
				PayGatewayBean shopSetupBean = new PayGatewayBean();
				shopSetupBean.setShopId((String) Adp.getValueAt(i, 0));
				shopSetupBean.setShopCd((String) Adp.getValueAt(i, 1));
				shopSetupBean.setOwnerId((String) Adp.getValueAt(i, 2));
				shopSetupBean.setLogin((String) Adp.getValueAt(i, 3));
				shopSetupBean.setPasswd((String) Adp.getValueAt(i, 4));
				shopSetupBean.setPayGatewayId((String) Adp.getValueAt(i, 5));
				shopSetupBean.setNameGateway((String) Adp.getValueAt(i, 6));
				shopSetupBean.setPayUrl((String) Adp.getValueAt(i, 7));
				listShopSetupBean.add(shopSetupBean);
			}

		} catch (SQLException ex) {

			log.error(query, ex);
		} catch (Exception ex) {

			log.error(ex);
		} finally {
			Adp.close();
		}
	}

	public String getTable(long roleId) {
		cururl = "PayGatewayList.jsp?offset=" + offset;
		listup = "PayGatewayList.jsp?offset=" + (offset + 10);
		if (offset - 10 < 0)
			listdown = "PayGatewayList.jsp?offset=0";
		else
			listdown = "PayGatewayList.jsp?offset=" + (offset - 10); // +
																		// "&catalog_id="
																		// +
																		// catalog_id
																		// +
																		// "&phonetype_id="
																		// +
																		// phonetype_id
																		// +
																		// "&licence_id="
																		// +
																		// licence_id
																		// ;

		StringBuffer table = new StringBuffer();
		// table.append("<TABLE ALIGN=\"CENTER\" WIDTH=\"400\" border=\"1\"
		// CELLSPACING=\"0\" CELLPADDING=\"2\">\n");

		table.append("<table class=\"columns\">\n");
		table.append("<tbody>\n");

		if (roleId == 2) {

			if (listShopSetupBean.size() == 0) {
				table.append("<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"10%\" >ID </TD>"
						+ "<TD WIDTH=\"70%\" >Paymant Gateway </TD>"
						+ "<TD WIDTH=\"20%\" ><a href =\"PayGatewaySetup.jsp\">add</a> </TD>" + "</TR>\n");
			} else {
				table.append("<TR BGCOLOR=\"#808080\" >" + "<TD WIDTH=\"10%\" >ID </TD>"
						+ "<TD WIDTH=\"70%\" >Paymant Gateway </TD>" + "<TD WIDTH=\"20%\" ></TD>" + "</TR>\n");
			}
		} else {
			table.append("<TR BGCOLOR=\"#808080\" >" + "<TD>ID</TD>" + "<TD>Paymant Gateway </TD>" + "<TD></TD>"
					+ "</TR>\n");
		}

		if (listShopSetupBean.size() < 10) {
			// table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD></TD>" + "</TR>\n");
		} else {
			table.append(
					"<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD><a href=\"" + listup + "\">up 10</a>  </TD>" + "</TR>\n");
		}

		for (int i = 0; listShopSetupBean.size() > i; i++) {
			table.append("<TR>" + "<TD>" + (i + 1) + "</TD>" + "<TD>"
					+ ((PayGatewayBean) listShopSetupBean.get(i)).getNameGateway() + "</TD>"
					+ "<TD><a href =\"PayGatewaySetup.jsp?row=" + i + "\">edit</a> </TD>" + "</TR>\n");
		}

		table.append(
				"<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD><a href=\"" + listdown + "\">back 10</a>  </TD>" + "</TR>\n");

		table.append("</tbody>\n");
		table.append("</TABLE>\n");

		return table.toString();
	}

	public static void main(String[] args) {
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getSelectShopId() {
		return selectShopId;
	}

	public void setSelectShopId(String selectShopId) {
		this.selectShopId = selectShopId;
	}

	public String getSelectNameGateway() {
		return selectNameGateway;
	}

	public void setSelectNameGateway(String selectNameGateway) {
		this.selectNameGateway = selectNameGateway;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public int getIndxSelect() {
		return indxSelect;
	}

	public void setIndxSelect(int indxSelect) {
		this.indxSelect = indxSelect;
	}

	public int stringToInt(String s) {
		int i;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			i = 0;
		}
		return i;
	}

	public String getCururl() {
		return cururl;
	}

	public void setCururl(String cururl) {
		this.cururl = cururl;
	}

	public String getListup() {
		return listup;
	}

	public void setListup(String listup) {
		this.listup = listup;
	}

	public String getListdown() {
		return listdown;
	}

	public void setListdown(String listdown) {
		this.listdown = listdown;
	}

	public PayGatewayBean getPayGatewayBean(int index) {
		Object payGatewayBean = listShopSetupBean.get(index);
		if (payGatewayBean instanceof PayGatewayBean)
			return (PayGatewayBean) payGatewayBean;
		return null;
	}

}
