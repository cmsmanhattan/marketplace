<%@ page errorPage="error.jsp" %>
<jsp:useBean id="merchantBeanId" scope="session" class="com.cbsinc.cms.MerchantBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="messageMail" scope="session" class="java.util.HashMap" type="java.util.HashMap"/>
<%@page import="java.util.PropertyResourceBundle,java.util.ResourceBundle,java.io.*"%>

<%
  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
  request.setCharacterEncoding("UTF-8");
%>

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
xsltUrl =  request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "merchant.xsl" ; 
xsltUrl_default = request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  + "merchant.xsl" ; 
xsltpath =  "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "merchant.xsl" ; 
xsltpathDefault = "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/" + "merchant.xsl" ; 
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
    System.out.println("Merchant.jsp xsltpath: " + xsltpath);
    System.out.println("Merchant.jsp xslt url: " + url);
}

PrintWriter printWriter = response.getWriter();
String tmp ="<?xml-stylesheet type=\"text/xsl\" href=\""+url+"\"?>" ;
printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
printWriter.println(tmp);

%>

<document>
   <version>1.0</version>
   <name>Marchant application</name>

   <title><%=  authorizationPageBeanId.getHost() %></title>
   <subject_site><%=  authorizationPageBeanId.getNickSite() %></subject_site>
   <site_name><%=  authorizationPageBeanId.getNickSite() %></site_name>
   <host><%=  authorizationPageBeanId.getSiteDir() %></host>
   <domain><%=  authorizationPageBeanId.getHost() %></domain>
   <login><%= authorizationPageBeanId.getStrLogin() %></login>
   <role_id><%=  authorizationPageBeanId.getRoleId() %></role_id>
   <shoping_url>Productlist.jsp</shoping_url>
   <message><%= authorizationPageBeanId.getStrMessage() %></message>
   
   <c_name><%= merchantBeanId.getCName() %></c_name>
   <trademark><%= merchantBeanId.getTradeMark() %></trademark>
   <biz_description><%= merchantBeanId.getBizDescription() %></biz_description>
   <formation_date><%= merchantBeanId.getFormationDate() %></formation_date>
   <taxid><%= merchantBeanId.getTaxid() %></taxid>
   <biz_address><%= merchantBeanId.getBizAddress() %></biz_address>
   <biz_city><%= merchantBeanId.getBizCity() %></biz_city>
   <biz_country><%= merchantBeanId.getBizCountry() %></biz_country>
   <biz_zip><%= merchantBeanId.getBizZip() %></biz_zip>
   <biz_phone><%= merchantBeanId.getBizPhone() %></biz_phone>
   <biz_email><%= merchantBeanId.getBizEmail() %></biz_email>
   
   <f_o_name><%= merchantBeanId.getFoName() %></f_o_name>
   <f_o_address><%= merchantBeanId.getFoAddress() %></f_o_address>
   <f_o_city><%= merchantBeanId.getFoCity() %></f_o_city>
   <f_o_country><%= merchantBeanId.getFoCountry() %></f_o_country>
   <f_o_zip><%= merchantBeanId.getFoZip() %></f_o_zip>
   <f_o_ownership><%= merchantBeanId.getFoOwnership() %></f_o_ownership>
   <f_o_phone><%= merchantBeanId.getFoPhone() %></f_o_phone>
   <f_o_email><%= merchantBeanId.getFoEmail() %></f_o_email>
   
   <s_o_name><%= merchantBeanId.getSoName() %></s_o_name>
   <s_o_address><%= merchantBeanId.getSoAddress() %></s_o_address>
   <s_o_city><%= merchantBeanId.getSoCity() %></s_o_city>
   <s_o_country><%= merchantBeanId.getSoCountry() %></s_o_country>
   <s_o_zip><%= merchantBeanId.getSoZip() %></s_o_zip>
   <s_o_ownership><%= merchantBeanId.getSoOwnership() %></s_o_ownership>
   <s_o_phone><%= merchantBeanId.getSoPhone() %></s_o_phone>
   <s_o_email><%= merchantBeanId.getSoEmail() %></s_o_email>
   
   <bank_name><%= merchantBeanId.getBankName() %></bank_name>
   <bank_account_number><%= merchantBeanId.getBankAccountNumber() %></bank_account_number>
   <bank_routing_number><%= merchantBeanId.getBankRoutingNumber() %></bank_routing_number>
   <bank_address><%= merchantBeanId.getBankAddress() %></bank_address>
   <bank_city><%= merchantBeanId.getBankCity() %></bank_city>
   <bank_country><%= merchantBeanId.getBankCountry() %></bank_country>
   <bank_zip><%= merchantBeanId.getBankZip() %></bank_zip>
   <bank_phone><%= merchantBeanId.getBankPhone() %></bank_phone>
   

</document>