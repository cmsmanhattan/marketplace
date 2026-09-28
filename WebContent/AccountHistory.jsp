<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<jsp:useBean id="orderListBeanId" scope="session" class="com.cbsinc.cms.OrderListBean" />
<jsp:useBean id="accountHistoryBeanId" scope="session" class="com.cbsinc.cms.AccountHistoryBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:setProperty name="accountHistoryBeanId" property="*" />
<%-- explicit mapping: request params keep their original names; bean properties are camelCase --%>
<jsp:setProperty name="accountHistoryBeanId" property="addAmount" param="add_amount" />
<jsp:setProperty name="accountHistoryBeanId" property="catalogId" param="catalog_id" />
<jsp:setProperty name="accountHistoryBeanId" property="currencyAddLable" param="currency_add_lable" />
<jsp:setProperty name="accountHistoryBeanId" property="currencyOldLable" param="currency_old_lable" />
<jsp:setProperty name="accountHistoryBeanId" property="currencyTotalLable" param="currency_total_lable" />
<jsp:setProperty name="accountHistoryBeanId" property="dateEnd" param="date_end" />
<jsp:setProperty name="accountHistoryBeanId" property="dateInput" param="date_input" />
<jsp:setProperty name="accountHistoryBeanId" property="oldAmount" param="old_amount" />
<jsp:setProperty name="accountHistoryBeanId" property="totalAmount" param="total_amount" />
<jsp:setProperty name="accountHistoryBeanId" property="typeId" param="type_id" />
<%-- explicit mapping: request params keep their original names; bean properties are camelCase --%>
<jsp:setProperty name="accountHistoryBeanId" property="addAmount" param="add_amount" />
<jsp:setProperty name="accountHistoryBeanId" property="catalogId" param="catalog_id" />
<jsp:setProperty name="accountHistoryBeanId" property="currencyAddLable" param="currency_add_lable" />
<jsp:setProperty name="accountHistoryBeanId" property="currencyOldLable" param="currency_old_lable" />
<jsp:setProperty name="accountHistoryBeanId" property="currencyTotalLable" param="currency_total_lable" />
<jsp:setProperty name="accountHistoryBeanId" property="dateEnd" param="date_end" />
<jsp:setProperty name="accountHistoryBeanId" property="dateInput" param="date_input" />
<jsp:setProperty name="accountHistoryBeanId" property="oldAmount" param="old_amount" />
<jsp:setProperty name="accountHistoryBeanId" property="totalAmount" param="total_amount" />
<jsp:setProperty name="accountHistoryBeanId" property="typeId" param="type_id" />
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
xsltUrl =  request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "accounthistory.xsl" ; 
xsltUrl_default = request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  + "accounthistory.xsl" ; 
xsltpath =  "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "accounthistory.xsl" ; 
xsltpathDefault = "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/" + "accounthistory.xsl" ; 
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
    System.out.println("AccountHistory.jsp xsltpath: " + xsltpath);
    System.out.println("AccountHistory.jsp xslt url: " + url);
}


PrintWriter printWriter = response.getWriter();
String tmp ="<?xml-stylesheet type=\"text/xsl\" href=\""+url+"\"?>" ;
printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
printWriter.println(tmp);

%>

<document>
   <version>1.0</version>
   <name>GBS ltd.</name>

   <virtual_host><%= authorizationPageBeanId.getVirtualHost() %></virtual_host>
   <title><%=  authorizationPageBeanId.getHost() %></title>
   <subject_site><%=  authorizationPageBeanId.getSubjectSite() %></subject_site>
   <site_name><%=  authorizationPageBeanId.getNickSite() %></site_name>
   <host><%=  authorizationPageBeanId.getSiteDir() %></host>
   <domain><%=  authorizationPageBeanId.getHost() %></domain>
   <login><%= authorizationPageBeanId.getStrLogin() %></login>
   <passwdord></passwdord>
   <shoping_url>Productlist.jsp</shoping_url>
   <message><%= authorizationPageBeanId.getStrMessage() %></message>
   <shoping_url>Productlist.jsp</shoping_url>
   <balans><%=  accountHistoryBeanId.getStrBalans(authorizationPageBeanId.getIntUserID()) %></balans>
   <to_navigator>wCatalog.jsp</to_navigator>
   <to_navigator_location>NavigatorLocation.jsp</to_navigator_location>
   <to_account_history>AccountHistory.jsp</to_account_history>
   <to_login>Authorization.jsp</to_login>
   <to_registration>RegPage.jsp</to_registration>
   <to_order>Order.jsp</to_order>
   <to_order_hist>OrderList.jsp</to_order_hist>
   <to_pay>PrePay.jsp</to_pay>
   <datefrom>01/01/2025</datefrom>
   <dateto>01/01/2025</dateto>
   <%=accountHistoryBeanId.getSelectAccountHistoryXML()  %>
   
    <%=  orderListBeanId.getSelectMenuCatalog()	 %>

<next><jsp:getProperty name="accountHistoryBeanId" property="listup" /></next>
<prev><jsp:getProperty name="accountHistoryBeanId" property="listdown" /></prev>

</document>