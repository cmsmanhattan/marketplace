/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code.
 * Программный код написан Грабко Константином Владимировичем и является его интеллектуальной
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

package com.cbsinc.cms;

import java.math.BigDecimal;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.cbsinc.cms.annotations.PageModel;
import com.cbsinc.cms.annotations.Scope;
import com.cbsinc.cms.controllers.ServiceLocator;
import com.cbsinc.cms.controllers.SiteType;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;

import jakarta.servlet.ServletContext;

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
@PageModel(Id = "authorizationPageBeanId", scope = Scope.SESSION)
//@PageXsltView(jspName = "Authorization.jsp", xsltName = "authorization.xsl", responseType = Type.XML)
public class AuthorizationPageBean implements java.io.Serializable {

	private static final long serialVersionUID = -7953241588509729879L;

	private static Logger log = Logger.getLogger(AuthorizationPageBean.class);

	transient private ResourceBundle localization = null;

	public AuthorizationPageBean() {
		try {
			calendar = java.util.Calendar.getInstance();

			dayfromId = calendar.get(java.util.Calendar.DAY_OF_MONTH);
			mountfromId = (calendar.get(java.util.Calendar.MONTH) + 1);
			yearfromId = calendar.get(java.util.Calendar.YEAR);

			daytoId = calendar.get(java.util.Calendar.DAY_OF_MONTH);
			mounttoId = (calendar.get(java.util.Calendar.MONTH) + 1);
			yeartoId = calendar.get(java.util.Calendar.YEAR);

		} catch (Exception ex) {
			log.error(ex);
		}
	}


	private boolean isMobileSession = false;

	java.util.Calendar calendar;

	private String strLogin = "";

	private String strPasswd = "";

	private Long intUserID = Long.valueOf(0);

	private Long roleId = Long.valueOf(0);

	private String strFirstName = "";

	private String strLastName = "";

	private String strMessage = "";

	private String strEMail = "";

	private Integer intLogined = Integer.valueOf(0);

	private String siteId = "2";

	private Integer langId = Integer.valueOf(-1);

	private String siteDir = "localhost";

	private String virtualHost = "localhost";

	private Integer rezaltReg = Integer.valueOf(0);

	private String strCountry = "";

	private String strCity = "";

	private String strCompany = "";

	private String strPhone = "";

	private String strMPhone = "";

	private String strFax = "";

	private String strIcq = "";

	private String strWebsite = "";

	private String strQuestion = "";

	private String strAnswer = "";

	private String strCPasswd = "";

	private String countryId = "1";

	private String cityId = "0";

	private String currencyId = "1";

	private String paysysShopCd;

	private String address = "";

	private String subjectSite = "";

	private String nickSite = "";

	private String companyName = "";

	private String host = "";

	private String userSite = "-1";

	private String userList = "";

	private String selectSite = "";
	private String selectCountry = "";
	private String selectCity = "";
	private String selectCurrency = "";

	private String catalogId = "-2";

	private long catalogParentId = 0;

	private Long offsetLastPage = Long.valueOf(0);

	private Long lastProductId = Long.valueOf(0);

	private Long currentOrderId = Long.valueOf(0);

	private Long creteria1Id = Long.valueOf(0);

	private Long creteria2Id = Long.valueOf(0);

	private Long creteria3Id = Long.valueOf(0);

	private Long creteria4Id = Long.valueOf(0);

	private Long creteria5Id = Long.valueOf(0);

	private Long creteria6Id = Long.valueOf(0);

	private Long creteria7Id = Long.valueOf(0);

	private Long creteria8Id = Long.valueOf(0);

	private Long creteria9Id = Long.valueOf(0);

	private Long creteria10Id = Long.valueOf(0);

	private Integer dayfromId = Integer.valueOf(0);

	private Integer mountfromId = Integer.valueOf(0);

	private Integer yearfromId = Integer.valueOf(0);

	private Integer daytoId = Integer.valueOf(0);

	private Integer mounttoId = Integer.valueOf(0);

	private Integer yeartoId = Integer.valueOf(0);

	private Integer numberPostedMessages = Integer.valueOf(0);

	private BigDecimal fromCost = BigDecimal.valueOf(0);
	private BigDecimal toCost = BigDecimal.valueOf(0);

	private String locale = "en";

	private String lastSessionId = "";

	private String balance = "0";

	// private String emailPassword = "" ;

	private String lastVisitedPage = "";


	public boolean isMobileSession() {
		return isMobileSession;
	}

	public void setMobileSession(boolean isMobileSession) {
		this.isMobileSession = isMobileSession;
	}

	public void setStrLogin(String strLogin) {
		this.strLogin = strLogin;
	}

	public String getStrLogin() {
		return strLogin;
	}

	public String userToGuestLogin() {

		return strLogin.equals("user") ? "guest" : strLogin;
	}

	public void setStrPasswd(String strPasswd) {
		this.strPasswd = strPasswd;
	}

	public String getStrPasswd() {
		return strPasswd;
	}

	public void setIntUserID(long intUserID) {
		this.intUserID = intUserID;
	}

	public long getIntUserID() {
		return intUserID;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setStrFirstName(String strFirstName) {
		this.strFirstName = strFirstName;
	}

	public String getStrFirstName() {
		return strFirstName;
	}

	public void setStrLastName(String strLastName) {
		this.strLastName = strLastName;
	}

	public String getStrLastName() {
		return strLastName;
	}

	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}

	public String getStrMessage() {
		String tmp = strMessage;
		strMessage = "";
		return tmp;
	}

	public void setStrEMail(String strEMail) {
		this.strEMail = strEMail;
	}

	public String getStrEMail() {
		return strEMail;
	}

	public void setIntLogined(int intLogined) {
		this.intLogined = intLogined;
	}

	public int getIntLogined() {
		return intLogined;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		// FIX: concatenated unquoted into SQL by callers; constrain to digits here.
		this.siteId = com.cbsinc.cms.utils.Validation.requireNumericId(siteId);
	}

	public void setSiteId(String siteId, AuthorizationPageFaced authorizationPageFaced) {
		this.siteId = com.cbsinc.cms.utils.Validation.requireNumericId(siteId);
		try {
			authorizationPageFaced.initSiteDir(this.siteId, this);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}


	public String getVirtualHost() {
		return virtualHost;
	}

	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	public String getSiteDir() {
		return siteDir;
	}

	public void setSiteDir(String siteDir) {
		this.siteDir = siteDir;
	}

	public int getRezaltReg() {
		return rezaltReg;
	}

	public void setRezaltReg(int rezaltReg) {
		this.rezaltReg = rezaltReg;
	}

	public String getStrCountry() {
		return strCountry;
	}

	public void setStrCountry(String strCountry) {
		this.strCountry = strCountry;
	}

	public String getStrCity() {
		return strCity;
	}

	public void setStrCity(String strCity) {
		this.strCity = strCity;
	}

	public String getStrCompany() {
		return strCompany;
	}

	public void setStrCompany(String strCompany) {
		this.strCompany = strCompany;
	}

	public String getStrPhone() {
		return strPhone;
	}

	public void setStrPhone(String strPhone) {
		this.strPhone = strPhone;
	}

	public String getStrMPhone() {
		return strMPhone;
	}

	public void setStrMPhone(String strMPhone) {
		this.strMPhone = strMPhone;
	}

	public String getStrFax() {
		return strFax;
	}

	public void setStrFax(String strFax) {
		this.strFax = strFax;
	}

	public String getStrIcq() {
		return strIcq;
	}

	public void setStrIcq(String strIcq) {
		this.strIcq = strIcq;
	}

	public String getStrWebsite() {
		return strWebsite;
	}

	public void setStrWebsite(String strWebsite) {
		this.strWebsite = strWebsite;
	}

	public String getStrQuestion() {
		return strQuestion;
	}

	public void setStrQuestion(String strQuestion) {
		this.strQuestion = strQuestion;
	}

	public String getStrAnswer() {
		return strAnswer;
	}

	public void setStrAnswer(String strAnswer) {
		this.strAnswer = strAnswer;
	}

	public String getStrCPasswd() {
		return strCPasswd;
	}

	public void setStrCPasswd(String strCPasswd) {
		this.strCPasswd = strCPasswd;
	}


	public boolean isSellerMode()
	{
		if( !getSiteId().equals(SiteType.MAIN_SITE) && getRoleId() == 2 ) return true;
		return false;
	}

	public boolean isHttpSession(jakarta.servlet.http.HttpServletResponse response,
			jakarta.servlet.http.HttpServletRequest request) {

		jakarta.servlet.http.HttpSession hsession = request.getSession();
		if (hsession == null) {
			hsession.invalidate();
			setStrMessage("You have auto logout  by timeout, login again");
			try {
				response.sendRedirect("Authorization.jsp");
			} catch (Exception ex) {
				System.err.println(ex);
			}
			return false;
		}

		if (getIntUserID() == 0) {
			setStrMessage("User not login or loguot by timeout , make login in site now");
			try {
				response.sendRedirect("Authorization.jsp");
			} catch (Exception ex) {
				System.err.println(ex);
			}
			return false;
		}

		return true;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getPaysysShopCd() {
		return paysysShopCd;
	}

	public void setPaysysShopCd(String paysysShopCd) {
		this.paysysShopCd = paysysShopCd;
	}

	public String getSubjectSite() {
		return subjectSite;
	}

	public void setSubjectSite(String subjectSite) {
		this.subjectSite = subjectSite;
	}

	public String getNickSite() {
		return nickSite;
	}

	public void setNickSite(String nickSite) {
		this.nickSite = nickSite;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserSite() {
		return userSite;
	}

	public void setUserSite(String userSite) {
		this.userSite = userSite;
	}

	public void setUserList(String userList) {
		this.userList = userList;
	}

	public String getUserList() {
		return userList;
	}

	public String getSelectCity() {
		return selectCity;
	}

	public void setSelectCity(String selectCity) {
		this.selectCity = selectCity;
	}

	public String getSelectCountry() {
		return selectCountry;
	}

	public void setSelectCountry(String selectCountry) {
		this.selectCountry = selectCountry;
	}

	public String getSelectCurrency() {
		return selectCurrency;
	}

	public void setSelectCurrency(String selectCurrency) {
		this.selectCurrency = selectCurrency;
	}

	public String getSelectSite() {
		return selectSite;
	}

	public void setSelectSite(String selectSite) {
		this.selectSite = selectSite;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		// FIX: concatenated unquoted into SQL by callers; constrain to digits here.
		this.catalogId = com.cbsinc.cms.utils.Validation.requireNumericId(catalogId);
	}

	public long getCatalogParentId() {
		return catalogParentId;
	}

	public void setCatalogParentId(long catalogParentId) {
		this.catalogParentId = catalogParentId;
	}

	public void setCatalogParentId(String catalogParentId) {
		this.catalogParentId = Long.valueOf(catalogParentId);
	}

	public long getOffsetLastPage() {
		return offsetLastPage;
	}

	public void setOffsetLastPage(long offsetLastPage) {
		this.offsetLastPage = offsetLastPage;
	}

	public long getLastProductId() {
		return lastProductId;
	}

	public void setLastProductId(long lastProductId) {
		this.lastProductId = lastProductId;
	}

	public long getCurrentOrderId() {
		return currentOrderId;
	}

	public void setCurrentOrderId(long currentOrderId) {
		this.currentOrderId = currentOrderId;
	}

	public long getCreteria1Id() {
		return creteria1Id;
	}

//	public String getCreteria1_id() {
//		return Long.toString(creteria1_id);
//	}

	public void setCreteria1Id(long creteria1Id) {
		this.creteria1Id = creteria1Id;
	}

	public void setStrCreteria1Id(String creteria1Id) {
		this.creteria1Id = Long.parseLong(creteria1Id);
	}

	public long getCreteria10Id() {
		return creteria10Id;
	}

	public void setCreteria10Id(long creteria10Id) {
		this.creteria10Id = creteria10Id;
	}

	public void setStrCreteria10Id(String creteria10Id) {
		this.creteria10Id = Long.parseLong(creteria10Id);
	}

	public long getCreteria2Id() {
		return creteria2Id;
	}

	public void setCreteria2Id(long creteria2Id) {
		this.creteria2Id = creteria2Id;
	}

	public void setStrCreteria2Id(String creteria2Id) {
		this.creteria2Id = Long.parseLong(creteria2Id);
	}

	public long getCreteria3Id() {
		return creteria3Id;
	}

	public void setCreteria3Id(long creteria3Id) {
		this.creteria3Id = creteria3Id;
	}

	public void setStrCreteria3Id(String creteria3Id) {
		this.creteria3Id = Long.parseLong(creteria3Id);
	}

	public long getCreteria4Id() {
		return creteria4Id;
	}

	public void setCreteria4Id(long creteria4Id) {
		this.creteria4Id = creteria4Id;
	}

	public void setStrCreteria4Id(String creteria4Id) {
		this.creteria4Id = Long.parseLong(creteria4Id);
	}

	public long getCreteria5Id() {
		return creteria5Id;
	}

	public void setCreteria5Id(long creteria5Id) {
		this.creteria5Id = creteria5Id;
	}

	public void setStrCreteria5Id(String creteria5Id) {
		this.creteria5Id = Long.parseLong(creteria5Id);
	}

	public long getCreteria6Id() {
		return creteria6Id;
	}

	public void setCreteria6Id(long creteria6Id) {
		this.creteria6Id = creteria6Id;
	}

	public void setStrCreteria6Id(String creteria6Id) {
		this.creteria6Id = Long.parseLong(creteria6Id);
	}

	public long getCreteria7Id() {
		return creteria7Id;
	}

	public void setCreteria7Id(long creteria7Id) {
		this.creteria7Id = creteria7Id;
	}

	public void setStrCreteria7Id(String creteria7Id) {
		this.creteria7Id = Long.parseLong(creteria7Id);
	}

	public long getCreteria8Id() {
		return creteria8Id;
	}

	public void setCreteria8Id(long creteria8Id) {
		this.creteria8Id = creteria8Id;
	}

	public void setStrCreteria8Id(String creteria8Id) {
		this.creteria8Id = Long.parseLong(creteria8Id);
	}

	public long getCreteria9Id() {
		return creteria9Id;
	}

	public void setCreteria9Id(long creteria9Id) {
		this.creteria9Id = creteria9Id;
	}

	public void setStrCreteria9Id(String creteria9Id) {
		this.creteria9Id = Long.parseLong(creteria9Id);
	}

	public BigDecimal getFromCost() {
		return fromCost;
	}

	public void setFromCost(BigDecimal fromCost) {
		this.fromCost = fromCost;
	}

	public void setStrFromCost(String fromCost) {
		this.fromCost = new BigDecimal(fromCost);
	}

	public Integer getMountfromId() {
		return mountfromId;
	}

	public BigDecimal getToCost() {
		return toCost;
	}

	public void setToCost(BigDecimal toCost) {
		this.toCost = toCost;
	}

	public void setStrToCost(String toCost) {
		this.toCost = new BigDecimal(toCost);
	}

	public Integer getYearfromId() {
		return yearfromId;
	}

	public Integer getYeartoId() {
		return yeartoId;
	}

	public java.util.Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(java.util.Calendar calendar) {
		this.calendar = calendar;
	}

	public Integer getDayfromId() {
		return dayfromId;
	}

	public void setDayfromId(Integer dayfromId) {
		this.dayfromId = dayfromId;
	}

	public Integer getDaytoId() {
		return daytoId;
	}

	public void setDaytoId(Integer daytoId) {
		this.daytoId = daytoId;
	}

	public Integer getMounttoId() {
		return mounttoId;
	}

	public void setMounttoId(Integer mounttoId) {
		this.mounttoId = mounttoId;
	}

	public void setCreteria1Id(Long creteria1Id) {
		this.creteria1Id = creteria1Id;
	}

	public void setCreteria10Id(Long creteria10Id) {
		this.creteria10Id = creteria10Id;
	}

	public void setCreteria2Id(Long creteria2Id) {
		this.creteria2Id = creteria2Id;
	}

	public void setCreteria3Id(Long creteria3Id) {
		this.creteria3Id = creteria3Id;
	}

	public void setCreteria4Id(Long creteria4Id) {
		this.creteria4Id = creteria4Id;
	}

	public void setCreteria5Id(Long creteria5Id) {
		this.creteria5Id = creteria5Id;
	}

	public void setCreteria6Id(Long creteria6Id) {
		this.creteria6Id = creteria6Id;
	}

	public void setCreteria7Id(Long creteria7Id) {
		this.creteria7Id = creteria7Id;
	}

	public void setCreteria8Id(Long creteria8Id) {
		this.creteria8Id = creteria8Id;
	}

	public void setCreteria9Id(Long creteria9Id) {
		this.creteria9Id = creteria9Id;
	}

	public void setCurrentOrderId(Long currentOrderId) {
		this.currentOrderId = currentOrderId;
	}

	public void setLastProductId(Long lastProductId) {
		this.lastProductId = lastProductId;
	}

	public void setMountfromId(Integer mountfromId) {
		this.mountfromId = mountfromId;
	}

	public void setOffsetLastPage(Long offsetLastPage) {
		this.offsetLastPage = offsetLastPage;
	}

	public void setYearfromId(Integer yearfromId) {
		this.yearfromId = yearfromId;
	}

	public void setYeartoId(Integer yeartoId) {
		this.yeartoId = yeartoId;
	}

	public Integer getNumberPostedMessages() {
		return numberPostedMessages;
	}

	public void setNumberPostedMessages(Integer numberPostedMessages) {
		this.numberPostedMessages = numberPostedMessages;
	}

	public String getEmailPassword() {
		if (getStrPasswd().length() > 4)
			return getStrPasswd().substring(0, 4).concat(getStrLogin());
		else
			return "";
		// return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		// this.emailPassword = emailPassword;
	}

	public ResourceBundle getLocalization(ServletContext applicationContext) {

		if (applicationContext != null)
			localization = (ResourceBundle) applicationContext.getAttribute("user_locale_" + getLocale());
		return localization;
		// return localization;
	}

	public ResourceBundle getLocalization() {
		return localization;
	}

	public void setLocalization(ResourceBundle localization) {
		this.localization = localization;
	}

//	public ServletContext getServletContext() {
//		// TODO Auto-generated method stub
//		return applicationContext;
//	}
//
//	public void setServletContext(ServletContext applicationContext) {
//		this.applicationContext = applicationContext ;
//
//	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public void setLocale(String locale, ServletContext applicationContext) {
		this.locale = locale;
		if (applicationContext == null)
			return;
		AuthorizationPageFaced authorizationPageFaced = null;
		try {
			authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (authorizationPageFaced != null) {
			setLangId(authorizationPageFaced.getLengId(locale));
		}
	}

	public String getIdSession() {
		return lastSessionId;
	}

	public void setLastSessionId(String idSession) {
		this.lastSessionId = idSession;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getLastVisitedPage() {
		return lastVisitedPage;
	}

	public void setLastVisitedPage(String lastVisitedPage) {
		this.lastVisitedPage = lastVisitedPage;
	}

	private String selectMenuCatalog;

	public String getSelectMenuCatalog() {
		return selectMenuCatalog;
	}

	public void setSelectMenuCatalog(String selectMenuCatalog) {
		this.selectMenuCatalog = selectMenuCatalog;
	}

	@Override
	public String toString() {
		// FIX: toString() printed strPasswd and strCPasswd in clear. It is written
		// at INFO level by ProductlistAction on every catalogue view, so every
		// logged-in user's password ended up in catalina.out. Both are masked.
		return "AuthorizationPageBean [calendar=" + calendar + ", strLogin=" + strLogin + ", strPasswd=***"
				+ ", intUserID=" + intUserID + ", roleId=" + roleId + ", strFirstName=" + strFirstName
				+ ", strLastName=" + strLastName + ", strMessage=" + strMessage + ", strEMail=" + strEMail
				+ ", intLogined=" + intLogined + ", site_id=" + siteId + ", lang_id=" + langId + ", site_dir="
				+ siteDir + ", rezalt_reg=" + rezaltReg + ", strCountry=" + strCountry + ", strCity=" + strCity
				+ ", strCompany=" + strCompany + ", strPhone=" + strPhone + ", strMPhone=" + strMPhone + ", strFax="
				+ strFax + ", strIcq=" + strIcq + ", strWebsite=" + strWebsite + ", strQuestion=" + strQuestion
				+ ", strAnswer=***, strCPasswd=***, country_id=" + countryId
				+ ", city_id=" + cityId + ", currency_id=" + currencyId + ", paysys_shop_cd=" + paysysShopCd
				+ ", address=" + address + ", subject_site=" + subjectSite + ", nick_site=" + nickSite
				+ ", company_name=" + companyName + ", host=" + host + ", user_site=" + userSite + ", userList="
				+ userList + ", select_site=" + selectSite + ", select_country=" + selectCountry + ", select_city="
				+ selectCity + ", select_currency=" + selectCurrency + ", catalog_id=" + catalogId
				+ ", catalog_parent_id=" + catalogParentId + ", offsetLastPage=" + offsetLastPage + ", lastProductId="
				+ lastProductId + ", currentOrderId=" + currentOrderId + ", creteria1_id=" + creteria1Id
				+ ", creteria2_id=" + creteria2Id + ", creteria3_id=" + creteria3Id + ", creteria4_id=" + creteria4Id
				+ ", creteria5_id=" + creteria5Id + ", creteria6_id=" + creteria6Id + ", creteria7_id=" + creteria7Id
				+ ", creteria8_id=" + creteria8Id + ", creteria9_id=" + creteria9Id + ", creteria10_id="
				+ creteria10Id + ", dayfrom_id=" + dayfromId + ", mountfrom_id=" + mountfromId + ", yearfrom_id="
				+ yearfromId + ", dayto_id=" + daytoId + ", mountto_id=" + mounttoId + ", yearto_id=" + yeartoId
				+ ", numberPostedMessages=" + numberPostedMessages + ", fromCost=" + fromCost + ", toCost=" + toCost
				+ ", locale=" + locale + ", lastSessionId=" + lastSessionId + ", balance=" + balance
				+ ", lastVisitedPage=" + lastVisitedPage + ", select_menu_catalog=" + selectMenuCatalog + "]";
	}




}
