<%@ page errorPage="error.jsp" %>
<jsp:useBean id="orderBeanId" scope="request" class="com.cbsinc.cms.OrderBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="messageMail" scope="session" class="java.util.HashMap" type="java.util.HashMap"/>
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
xsltUrl =  request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "order.xsl" ; 
xsltUrl_default = request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  + "order.xsl" ; 
xsltpath =  "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "order.xsl" ; 
xsltpathDefault = "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/" + "order.xsl" ; 
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
    System.out.println("Order.jsp xsltpath: " + xsltpath);
    System.out.println("Order.jsp xslt url: " + url);
}


PrintWriter printWriter = response.getWriter();
String tmp ="<?xml-stylesheet type=\"text/xsl\" href=\""+url+"\"?>" ;
printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
printWriter.println(tmp);

%>

<document>
   <version>1.0</version>
   <name>Authorization</name>
   <virtual_host><%= authorizationPageBeanId.getVirtualHost() %></virtual_host>   
   <role_id><%=  authorizationPageBeanId.getRoleId() %></role_id>
   <title><%=  authorizationPageBeanId.getHost() %></title>
   <admin>
   <post_manager><%=  orderBeanId.getTrueValue("PostManager.jsp","",authorizationPageBeanId.getRoleId()==2) %></post_manager>
   </admin>
   <subject_site><%=  authorizationPageBeanId.getNickSite() %></subject_site>
   <site_name><%=  authorizationPageBeanId.getNickSite() %></site_name>
   <host><%=  authorizationPageBeanId.getSiteDir() %></host>
   <domain><%=  authorizationPageBeanId.getHost() %></domain>
   <login><%= authorizationPageBeanId.getStrLogin() %></login>
   <passwdord></passwdord>
   <message><%= authorizationPageBeanId.getStrMessage() %></message>
   <shoping_url>Productlist.jsp</shoping_url>
   <balans><%=  orderBeanId.getStrBalans() %></balans>
   <to_navigator>wCatalog.jsp</to_navigator>
   <to_navigator_location>NavigatorLocation.jsp</to_navigator_location>
   <to_account_history>AccountHistory.jsp</to_account_history>
   <to_login>Authorization.jsp</to_login>
   <to_registration>Authorization.jsp?Login=</to_registration>
   <to_order>Order.jsp</to_order>
   <to_order_hist>OrderList.jsp</to_order_hist>
   <to_pay>PrePay.jsp</to_pay>

<shipment_phone><jsp:getProperty name="orderBeanId" property="shipmentPhone" /></shipment_phone>
<contact_person><%= orderBeanId.getContactPerson() %></contact_person>
<shipment_email><jsp:getProperty name="orderBeanId" property="shipmentEmail" /></shipment_email>



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

   <%=orderBeanId.getProductList()%>


<!-- ????????? ?????? ???????? ??? ???   -->
   <empty_page><%=  orderBeanId.getPagecount() == 0 %></empty_page>
<!-- ????????? ?????? ??????? ??? ???   -->
   <empty_basket><%=  orderBeanId.isEmptyBasket() %></empty_basket>
<!-- ?????????? ??????? ???????   -->
   <quantity_product><%=  orderBeanId.getQuantityProduct() %></quantity_product>
<!-- ??? ?????? ????????   -->
    <offset><%=  orderBeanId.getOffset() %></offset>
    
  <%=orderBeanId.getNewArrivalItems(authorizationPageBeanId)%>
  <%=orderBeanId.getRecentlyReviewedItems(authorizationPageBeanId)%>
  <%=orderBeanId.getSponsoredBySellersItems(authorizationPageBeanId)%>
  <%=orderBeanId.getRecommentedItems(authorizationPageBeanId)%>  


<next><jsp:getProperty name="orderBeanId" property="listup" /></next>
<prev><jsp:getProperty name="orderBeanId" property="listdown" /></prev>

<action><jsp:getProperty name="orderBeanId" property="action" /></action>
<imgname><jsp:getProperty name="orderBeanId" property="imgname" /></imgname>
<image_id><jsp:getProperty name="orderBeanId" property="imageId" /></image_id>
<img_url><jsp:getProperty name="orderBeanId" property="imgUrl" /></img_url>
<city_id><jsp:getProperty name="orderBeanId" property="cityId" /></city_id>
<country_id><jsp:getProperty name="orderBeanId" property="countryId" /></country_id>
<order_end_amount><jsp:getProperty name="orderBeanId" property="endAmount" /></order_end_amount>
<order_amount><jsp:getProperty name="orderBeanId" property="orderAmount" /></order_amount>
<order_tax><jsp:getProperty name="orderBeanId" property="orderTax" /></order_tax>
<order_id><jsp:getProperty name="orderBeanId" property="orderId" /></order_id>
<order_currency_id><jsp:getProperty name="orderBeanId" property="orderCurrencyId" /></order_currency_id>
<order_paystatus><jsp:getProperty name="orderBeanId" property="orderPaystatus" /></order_paystatus>
<order_status><jsp:getProperty name="orderBeanId" property="deliverystatusId" /></order_status>
<order_status_lable><jsp:getProperty name="orderBeanId" property="orderStatus" /></order_status_lable>
<delivery_amoun><jsp:getProperty name="orderBeanId" property="deliveryAmoun" /></delivery_amoun>
<delivery_timeend><jsp:getProperty name="orderBeanId" property="deliveryTimeend" /></delivery_timeend>
<delivery_long><jsp:getProperty name="orderBeanId" property="orderDeliveryLong" /></delivery_long>
<delivery_start><jsp:getProperty name="orderBeanId" property="deliveryStart" /></delivery_start>
<cards_name><jsp:getProperty name="orderBeanId" property="cardsName" /></cards_name>
<city_fullname><jsp:getProperty name="orderBeanId" property="cityFullname" /></city_fullname>
<country_fullname><jsp:getProperty name="orderBeanId" property="countryFullname" /></country_fullname>
<currency_lable><jsp:getProperty name="orderBeanId" property="currencyLable" /></currency_lable>
<img_url><jsp:getProperty name="orderBeanId" property="imgUrl" /></img_url>

<shipment_address><jsp:getProperty name="orderBeanId" property="shipmentAddress" /></shipment_address>
<shipment_fax><jsp:getProperty name="orderBeanId" property="shipmentFax" /></shipment_fax>
<shipment_description><jsp:getProperty name="orderBeanId" property="shipmentDescription" /></shipment_description>
<city_name><jsp:getProperty name="orderBeanId" property="cityName" /></city_name>
<country_name><jsp:getProperty name="orderBeanId" property="countryName" /></country_name>

<country_telcode><jsp:getProperty name="orderBeanId" property="countryTelcode" /></country_telcode>
<currency_rate><jsp:getProperty name="orderBeanId" property="currencyRate" /></currency_rate>
<city_telcode><jsp:getProperty name="orderBeanId" property="orderCityTelcode" /></city_telcode>
<cdate><jsp:getProperty name="orderBeanId" property="cdate" /></cdate>
<paystatus_lable><jsp:getProperty name="orderBeanId" property="paystatusLable" /></paystatus_lable>

<internet><%= orderBeanId.isInternet %></internet>


<%=  orderBeanId.getSelectCountry() %>
<%=  orderBeanId.getSelectCity() %>
<%=  orderBeanId.getSelectPaystatus() %>
<%=  orderBeanId.getSelectDeliverystatus() %>

<!-- Shipping company (carriers of this shop) and the chosen carrier's own site. -->
<%=  orderBeanId.getSelectShippingCompany() %>
<shipping_company_id><jsp:getProperty name="orderBeanId" property="shippingCompanyId" /></shipping_company_id>
<shipping_company_name><jsp:getProperty name="orderBeanId" property="shippingCompanyName" /></shipping_company_name>
<shipping_company_site_id><jsp:getProperty name="orderBeanId" property="shippingCompanySiteId" /></shipping_company_site_id>
<shipping_company_host><jsp:getProperty name="orderBeanId" property="shippingCompanyHost" /></shipping_company_host>

<!-- Resolution center; the template shows the field only when the migration was applied. -->
<resolution_center_enabled><%= orderBeanId.isResolutionCenterEnabled() %></resolution_center_enabled>
<% if (orderBeanId.isResolutionCenterEnabled()) { %>
<%=  orderBeanId.getSelectResolutionCenter() %>
<resolution_center_id><jsp:getProperty name="orderBeanId" property="resolutionCenterId" /></resolution_center_id>
<resolution_center_name><jsp:getProperty name="orderBeanId" property="resolutionCenterName" /></resolution_center_name>
<resolution_center_site_id><jsp:getProperty name="orderBeanId" property="resolutionCenterSiteId" /></resolution_center_site_id>
<resolution_center_host><jsp:getProperty name="orderBeanId" property="resolutionCenterHost" /></resolution_center_host>
<resolution_status_id><jsp:getProperty name="orderBeanId" property="resolutionStatusId" /></resolution_status_id>
<resolution_status_lable><jsp:getProperty name="orderBeanId" property="resolutionStatusLable" /></resolution_status_lable>
<% } %>

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

<%=  orderBeanId.getSelectMenuCatalog()	 %>

</document>