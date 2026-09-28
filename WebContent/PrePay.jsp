<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<jsp:useBean id="prePayBeanId" scope="request" class="com.cbsinc.cms.PrePayBean" />
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
xsltUrl =  request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "prepay.xsl" ; 
xsltUrl_default = request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  + "prepay.xsl" ; 
xsltpath =  "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "prepay.xsl" ; 
xsltpathDefault = "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/" + "prepay.xsl" ; 
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
    System.out.println("PrePay.jsp xsltpath: " + xsltpath);
    System.out.println("PrePay.jsp xslt url: " + url);
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
   <balans><%=  prePayBeanId.getStrBalans(authorizationPageBeanId.getIntUserID()) %></balans>
   <to_navigator>wCatalog.jsp</to_navigator>
   <to_navigator_location>NavigatorLocation.jsp</to_navigator_location>
   <to_account_history>AccountHistory.jsp</to_account_history>
   <to_login>Authorization.jsp</to_login>
   <to_registration>RegPage.jsp</to_registration>
   <to_order>Order.jsp</to_order>
   <to_order_hist>OrderList.jsp</to_order_hist>
   <to_pay>PrePay.jsp</to_pay>
   <order_end_amount><jsp:getProperty name="orderBeanId" property="endAmount" /></order_end_amount>

	<%= prePayBeanId.getSelectCurrencyListXML() %>
	<%= prePayBeanId.getSelectPaysystemListXML() %>

</document>