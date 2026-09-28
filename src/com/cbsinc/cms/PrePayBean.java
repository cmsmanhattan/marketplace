package com.cbsinc.cms;

import com.cbsinc.cms.annotations.PageModel;
import com.cbsinc.cms.annotations.Scope;

@PageModel(Id = "prePayBeanId", scope = Scope.SESSION)
//@PageXsltView(jspName = "PrePay.jsp", xsltName = "pay.xsl", responseType = Type.XML)
public class PrePayBean extends com.cbsinc.cms.WebControls implements java.io.Serializable {

	private static final long serialVersionUID = 6234842953899930459L;

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

	private String selectCurrencyListXML = "";

	private String selectPaysystemListXML = "";

	public String getSelectCurrencyListXML() {
		return selectCurrencyListXML;
	}

	public void setSelectCurrencyListXML(String selectCurrencyListXML) {
		this.selectCurrencyListXML = selectCurrencyListXML;
	}

	public String getSelectPaysystemListXML() {
		return selectPaysystemListXML;
	}

	public void setSelectPaysystemListXML(String selectPaysystemListXML) {
		this.selectPaysystemListXML = selectPaysystemListXML;
	}

}
