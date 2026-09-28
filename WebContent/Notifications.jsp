<%@ page errorPage="error.jsp" %>
<jsp:useBean id="notificationsBeanId" scope="session" class="com.cbsinc.cms.NotificationsBean" />
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
xsltUrl =  request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "notifications.xsl" ; 
xsltUrl_default = request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  + "notifications.xsl" ; 
xsltpath =  "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "notifications.xsl" ; 
xsltpathDefault = "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/" + "notifications.xsl" ; 
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
    System.out.println("Notifications.jsp xsltpath: " + xsltpath);
    System.out.println("Notifications.jsp xslt url: " + url);
}

PrintWriter printWriter = response.getWriter();
String tmp ="<?xml-stylesheet type=\"text/xsl\" href=\""+url+"\"?>" ;
printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
printWriter.println(tmp);

%>

<document>
   <version>1.0</version>
   <name>GBS ltd.</name>

   <title><%=  authorizationPageBeanId.getHost() %></title>
   <subject_site><%=  authorizationPageBeanId.getNickSite() %></subject_site>
   <site_name><%=  authorizationPageBeanId.getNickSite() %></site_name>
   <virtual_host><%= authorizationPageBeanId.getVirtualHost() %></virtual_host>
   <host><%=  authorizationPageBeanId.getSiteDir() %></host>
   <domain><%=  authorizationPageBeanId.getHost() %></domain>
   <login><%= authorizationPageBeanId.getStrLogin() %></login>
   <role_id><%=  authorizationPageBeanId.getRoleId() %></role_id>
   <passwdord></passwdord>
   <shoping_url>Productlist.jsp</shoping_url>
   <message><%= authorizationPageBeanId.getStrMessage() %></message>
   <shoping_url>Productlist.jsp</shoping_url>
   <balans><%=  notificationsBeanId.getStrBalans(authorizationPageBeanId.getIntUserID()) %></balans>
   <to_navigator>wCatalog.jsp</to_navigator>
   <to_navigator_location>NavigatorLocation.jsp</to_navigator_location>
   <to_account_history>AccountHistory.jsp</to_account_history>
   <to_login>Authorization.jsp</to_login>
   <to_registration>RegPage.jsp</to_registration>
   <to_order>Order.jsp</to_order>
   <to_order_hist>OrderList.jsp?searchquery=0</to_order_hist>
   <to_pay>PrePay.jsp</to_pay>
   <datefrom_formated><%=notificationsBeanId.getFormatedDateFrom(request.getLocale()) %></datefrom_formated>
   <dateto_formated><%=notificationsBeanId.getFormatedDateFrom(request.getLocale()) %></dateto_formated>
   <datefrom><%=notificationsBeanId.getDateFrom() %></datefrom>
   <dateto><%=notificationsBeanId.getDateTo() %></dateto>
   <date_format><%=notificationsBeanId.getDatePattern()%></date_format>
   <%=notificationsBeanId.getSelectOrderlistXML()  %>
   <%=notificationsBeanId.getSelectPaystatus() %>
   <%=notificationsBeanId.getSelectDeliverystatus() %>
 
<next><jsp:getProperty name="notificationsBeanId" property="listup" /></next>
<prev><jsp:getProperty name="notificationsBeanId" property="listdown" /></prev>

   <!--  for members -->
   <do_form_1>
                <form-header>
                <name>login</name>
		<method>post</method>
		<action>Authorization.jsp</action>
                </form-header>

                <fields>
                <ref_1>/login</ref_1>
                <ref_2>/passwdord</ref_2>
                <ref_3>/lang_cd</ref_3>
                <ref_4>product/currency_cd</ref_4>
                </fields>
   </do_form_1>

   <do_form_2>
   <!-- become new member -->
                <form-header>
                <name>registration</name>
		<method>post</method>
		<action>RegPage.jsp</action>
                </form-header>
   </do_form_2>
   
 <%=  notificationsBeanId.getSelectMenuCatalog()	 %>

</document>