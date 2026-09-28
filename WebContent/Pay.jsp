<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<jsp:useBean id="payBeanId" scope="request" class="com.cbsinc.cms.PayBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<%@page import="java.util.PropertyResourceBundle,java.util.ResourceBundle,java.io.*"%>

<%
response.setCharacterEncoding("UTF-8");
response.setContentType("text/xml");

String url = "" ;
String xsltpath = "";
String xsltpathDefault = "" ;
String xsltUrl = "" ;
String xsltUrl_default = "" ;

try
{
String mobile = authorizationPageBeanId.isMobileSession()?"/mobile":""  ;
xsltUrl =  request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "pay.xsl" ; 
xsltUrl_default = request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  + "pay.xsl" ; 
xsltpath =  "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "pay.xsl" ; 
xsltpathDefault = "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/" + "pay.xsl" ; 
xsltpath = request.getServletContext().getRealPath("/" +xsltpath);
xsltpathDefault = request.getServletContext().getRealPath("/" +xsltpathDefault);

	File file = new File(xsltpath) ;
	if( file == null  || !file.exists() ) url = xsltUrl_default ;
	else url = xsltUrl ;
		 
}
 catch (Exception e) 
{
	 throw e ;
}
finally {
	System.out.println("isMobileSession: " + authorizationPageBeanId.isMobileSession());
    System.out.println("OrderList.jsp xsltpath: " + xsltpath);
    System.out.println("OrderList.jsp xslt url: " + url);
}

PrintWriter printWriter = response.getWriter();
String tmp ="<?xml-stylesheet type=\"text/xsl\" href=\""+url+"\"?>" ;
printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
printWriter.println(tmp);

%>
<document>
   <version>1.0</version>
   <name>GBS ltd.</name>
   <title>SELECT PAY SYSTEM</title>
   <subject_site><%=  authorizationPageBeanId.getNickSite() %></subject_site>
   <site_name><%=  authorizationPageBeanId.getNickSite() %></site_name>
   <host><%=  authorizationPageBeanId.getSiteDir() %></host>
   <login><%= authorizationPageBeanId.getStrLogin() %></login>
   <passwdord></passwdord>
   <shoping_url>Productlist.jsp</shoping_url>
   <message><%= authorizationPageBeanId.getStrMessage() %></message>
   <shoping_url>Productlist.jsp</shoping_url>
   <balans><%=  payBeanId.getStrBalans(authorizationPageBeanId.getIntUserID()) %></balans>
   <to_navigator>wCatalog.jsp</to_navigator>
   <to_navigator_location>NavigatorLocation.jsp</to_navigator_location>
   <to_account_history>AccountHistory.jsp</to_account_history>
   <to_login>Authorization.jsp</to_login>
   <to_registration>RegPage.jsp</to_registration>
   <to_order>Order.jsp</to_order>
   <to_order_hist>OrderList.jsp</to_order_hist>
   <to_pay>PrePay.jsp</to_pay>


   <firstname><%= authorizationPageBeanId.getStrFirstName() %></firstname>
   <lastname><%= authorizationPageBeanId.getStrLastName() %></lastname>
   <company><%= authorizationPageBeanId.getStrCompany() %></company>
   <email><%= authorizationPageBeanId.getStrEMail() %></email>
   <phone><%= authorizationPageBeanId.getStrPhone() %></phone>
   <mphone><%= authorizationPageBeanId.getStrMPhone() %></mphone>
   <fax><%= authorizationPageBeanId.getStrFax() %></fax>
   <icq><%= authorizationPageBeanId.getStrIcq() %></icq>
   <website><%= authorizationPageBeanId.getStrWebsite() %></website>
   <question><%= authorizationPageBeanId.getStrQuestion() %></question>
   <answer><%= authorizationPageBeanId.getStrAnswer() %></answer>
   <country><%= authorizationPageBeanId.getStrCountry() %></country>
   <city><%= authorizationPageBeanId.getStrCity() %></city>
   <site><%= authorizationPageBeanId.getSiteId() %></site>
   <message><%= authorizationPageBeanId.getStrMessage() %></message>
   <country_id><%= authorizationPageBeanId.getCountryId() %></country_id>
   <city_id><%= authorizationPageBeanId.getCityId() %></city_id>
   <currency_id><%= authorizationPageBeanId.getCurrencyId() %></currency_id>
   <amount><%= payBeanId.getAmount() %></amount>
   <currency_paysys_id><%= payBeanId.getCurrencyCd()   %></currency_paysys_id>
   <account_history_id><%= payBeanId.getAccountHistId() %></account_history_id>
   <shop_paysys_id><%= authorizationPageBeanId.getPaysysShopCd() %></shop_paysys_id>
   <type_creditcard><%= payBeanId.getChoosenTypeCreditCard()%></type_creditcard>
   <card_payment><%= payBeanId.getCardPayment()%></card_payment>
   <wallet_payment><%= payBeanId.getWalletPayment()%></wallet_payment>
   <webmoney_payment><%= payBeanId.getWebMoneyPayment()%></webmoney_payment>
   <papida_payment><%= payBeanId.getRapidaPayment()%></papida_payment>
   <paycash_payment><%= payBeanId.getPayCashPayment()%></paycash_payment>
   <eport_payment><%= payBeanId.getEPortPayment()%></eport_payment>
   <kredit_pilotpayment><%=payBeanId.getKreditPilotPayment()%></kredit_pilotpayment>
   <url_rezalt_banking_ok><%= "http://" + request.getServerName() + ":" + request.getServerPort() + "/AccountHistory.jsp"   %></url_rezalt_banking_ok>
   <url_rezalt_banking_no><%= "http://" + request.getServerName() + ":" + request.getServerPort() + "/Productlist.jsp?catalog_id=-2"   %></url_rezalt_banking_no>
   <decsription><%=  payBeanId.getDescription() %></decsription>

</document>

