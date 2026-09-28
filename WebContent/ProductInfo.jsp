<%@ page errorPage="error.jsp" %>
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="itemDescriptionBeanId" scope="request" class="com.cbsinc.cms.ItemDescriptionBean" />
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
xsltUrl =  request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "policy.xsl" ; 
xsltUrl_default = request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  + "policy.xsl" ; 
xsltpath =  "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "policy.xsl" ; 
xsltpathDefault = "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/" + "policy.xsl" ; 
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
    System.out.println("ProductInfo.jsp xsltpath: " + xsltpath);
    System.out.println("ProductInfo.jsp xslt url: " + url);
}

PrintWriter printWriter = response.getWriter();
String tmp ="<?xml-stylesheet type=\"text/xsl\" href=\""+url+"\"?>" ;
printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
printWriter.println(tmp);

%>
<document>
   <version>1.0</version>
   <name>Product info</name>

   <title><%=  authorizationPageBeanId.getHost() %></title>
   <subject_site><%=  authorizationPageBeanId.getSubjectSite() %></subject_site>
   <site_name><%=  authorizationPageBeanId.getNickSite() %></site_name>
   <virtual_host><%= authorizationPageBeanId.getVirtualHost() %></virtual_host>
   <host><%=  authorizationPageBeanId.getSiteDir() %></host>
   <domain><%=  authorizationPageBeanId.getHost() %></domain>
   <admin>
   <post_manager><%=  itemDescriptionBeanId.getTrueValue("PolicyManager.jsp","",authorizationPageBeanId.getRoleId()==2) %></post_manager>
   <post_manager_img><%=  itemDescriptionBeanId.getTrueValue("images/post.jpg","",authorizationPageBeanId.getRoleId()==2) %></post_manager_img>
   <post_manager_text><%=  itemDescriptionBeanId.getTrueValue("Post","",authorizationPageBeanId.getRoleId()==2) %></post_manager_text>
   </admin>

   <role_id><%=  authorizationPageBeanId.getRoleId() %></role_id>
   <user_site_id><%=  authorizationPageBeanId.getUserSite() %></user_site_id>
   <internet><%= itemDescriptionBeanId.isInternet() %></internet>
   <login><%=  authorizationPageBeanId.getStrLogin() %></login>
   <shoping_url>Productlist.jsp</shoping_url>
   <message><%= authorizationPageBeanId.getStrMessage() %></message>
   <shoping_url>Productlist.jsp</shoping_url>
   <balans><%= "" + itemDescriptionBeanId.getBalans() %></balans>
   <to_account_history>AccountHistory.jsp</to_account_history>
   <to_login>Authorization.jsp</to_login>
   <to_registration>RegPage.jsp</to_registration>
   <to_order>Order.jsp</to_order>
   <to_order_hist>OrderList.jsp</to_order_hist>
   <to_pay>PrePay.jsp</to_pay>
   <owner_user_id><%=  authorizationPageBeanId.getIntUserID() %></owner_user_id>
   <site_id><%=  authorizationPageBeanId.getSiteId() %></site_id>
   <show_blog><%=  itemDescriptionBeanId.getStrShowForum() %></show_blog>
   <show_offer><%=  itemDescriptionBeanId.getStrShowOffer() %></show_offer>
   <show_action><%=  itemDescriptionBeanId.getStrShowAction() %></show_action>
   <show_rating1><%=  itemDescriptionBeanId.getStrShowRatimg1() %></show_rating1> 
   <show_rating2><%=  itemDescriptionBeanId.getStrShowRatimg2() %></show_rating2>
   <show_rating3><%=  itemDescriptionBeanId.getStrShowRatimg3() %></show_rating3>  
   
   <creteriaName1><%=  itemDescriptionBeanId.getCreteriaName1() %></creteriaName1>
   <creteriaLabel1><%=  itemDescriptionBeanId.getCreteriaLabel1() %></creteriaLabel1>
   
   <creteriaName2><%=  itemDescriptionBeanId.getCreteriaName2() %></creteriaName2>
   <creteriaLabel2><%=  itemDescriptionBeanId.getCreteriaLabel2() %></creteriaLabel2>
   
   <creteriaName3><%=  itemDescriptionBeanId.getCreteriaName3() %></creteriaName3>
   <creteriaLabel3><%=  itemDescriptionBeanId.getCreteriaLabel3() %></creteriaLabel3> 
   
   <creteriaName4><%=  itemDescriptionBeanId.getCreteriaName4() %></creteriaName4>
   <creteriaLabel4><%=  itemDescriptionBeanId.getCreteriaLabel4() %></creteriaLabel4> 
   
   <creteriaName5><%=  itemDescriptionBeanId.getCreteriaName5() %></creteriaName5>
   <creteriaLabel5><%=  itemDescriptionBeanId.getCreteriaLabel5() %></creteriaLabel5>
   
   <creteriaName6><%=  itemDescriptionBeanId.getCreteriaName6() %></creteriaName6>
   <creteriaLabel6><%=  itemDescriptionBeanId.getCreteriaLabel6() %></creteriaLabel6>
   
   <creteriaName7><%=  itemDescriptionBeanId.getCreteriaName7() %></creteriaName7>
   <creteriaLabel7><%=  itemDescriptionBeanId.getCreteriaLabel7() %></creteriaLabel7>
   
   <creteriaName8><%=  itemDescriptionBeanId.getCreteriaName8() %></creteriaName8>
   <creteriaLabel8><%=  itemDescriptionBeanId.getCreteriaLabel8() %></creteriaLabel8>
   
   <creteriaName9><%=  itemDescriptionBeanId.getCreteriaName9() %></creteriaName9>
   <creteriaLabel9><%=  itemDescriptionBeanId.getCreteriaLabel9() %></creteriaLabel9>
   
   <creteriaName10><%=  itemDescriptionBeanId.getCreteriaName10() %></creteriaName10>
   <creteriaLabel10><%=  itemDescriptionBeanId.getCreteriaLabel10() %></creteriaLabel10>     

   

<product>
<page_url>http://<%= request.getServerName() %>:<%=request.getServerPort()%>/ProductInfo.jsp?policy_byproductid=<jsp:getProperty name="itemDescriptionBeanId" property="productId" /></page_url>
<product_id><jsp:getProperty name="itemDescriptionBeanId" property="productId" /></product_id>
<name><jsp:getProperty name="itemDescriptionBeanId" property="productName" /></name>
<file_exist><jsp:getProperty name="itemDescriptionBeanId" property="fileExist" /></file_exist>
<icon><jsp:getProperty name="itemDescriptionBeanId" property="imgURL" /></icon>
<image><jsp:getProperty name="itemDescriptionBeanId" property="bigimgURL" /></image>
<image_type><%= itemDescriptionBeanId.getBigimgURL().substring(itemDescriptionBeanId.getBigimgURL().indexOf(".") + 1 ) %></image_type>
<product_url>http://<%= request.getServerName() %>:<%=request.getServerPort()%>/<jsp:getProperty name="itemDescriptionBeanId" property="productURL" /></product_url>
<back_url><jsp:getProperty name="itemDescriptionBeanId" property="backUrl" /></back_url>
<description><jsp:getProperty name="itemDescriptionBeanId" property="productDescription" /></description>
<amount><jsp:getProperty name="itemDescriptionBeanId" property="productCost" /></amount>
<offerAmount><jsp:getProperty name="itemDescriptionBeanId" property="offerAmount" /></offerAmount>
<actionMaxBitAmount><jsp:getProperty name="itemDescriptionBeanId" property="auctionMaxBidAmount" /></actionMaxBitAmount>
<actionBitAmount><jsp:getProperty name="itemDescriptionBeanId" property="auctionBidAmount" /></actionBitAmount>
<currency>
 <code><jsp:getProperty name="itemDescriptionBeanId" property="currencyCd" /></code>
 <description><jsp:getProperty name="itemDescriptionBeanId" property="currencyDesc" /></description>
</currency>
<statistic><%= itemDescriptionBeanId.getStatistic() %></statistic>  
<cdate><%= itemDescriptionBeanId.getStrCDate() %></cdate>  
<creator_info_user_id><%=  itemDescriptionBeanId.getCreatorInfoUserId() %></creator_info_user_id>
</product>

<%=  itemDescriptionBeanId.getRating1Xml() %>
<%=  itemDescriptionBeanId.getSelectCurrencies() %>
<%=itemDescriptionBeanId.getExtPolicyOneProductlist(authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId(),itemDescriptionBeanId.getProductId() )%>
<%=itemDescriptionBeanId.getExtPolicyTwoProductlist(authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId(),itemDescriptionBeanId.getProductId() )%>

<%=itemDescriptionBeanId.getExtPolicyFilesProductlist(authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId(),itemDescriptionBeanId.getProductId() )%>
<%=itemDescriptionBeanId.getExtPolicyTabsProductlist(authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId(),itemDescriptionBeanId.getProductId() )%>

<%=itemDescriptionBeanId.getBlogExtPolicyProductlist(authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId(),itemDescriptionBeanId.getProductId() )%>
<%=itemDescriptionBeanId.getNewslist(authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId())%>
<%=itemDescriptionBeanId.getNewArrivalItems(authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId())%>
<%=itemDescriptionBeanId.getRecentlyReviewedItems()%>
<%=itemDescriptionBeanId.getSponsoredBySellersItems()%>
<%=itemDescriptionBeanId.getRecommentedItems()%>
<%=itemDescriptionBeanId.getBottomList( authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId())%>

   <!-- ????????? ?????? ???????? ??? ??? ? ?   -->
   <empty_page_ext1><%=  itemDescriptionBeanId.getPagecountExt1() == 0 %></empty_page_ext1>
   <!-- ????????? ?????? ???????? ??? ???   -->
   <empty_page_ext2><%=  itemDescriptionBeanId.getPagecountExt2() == 0 %></empty_page_ext2>
   
   <%=  itemDescriptionBeanId.getSelectTreeCatalog()	 %>
   <%=  itemDescriptionBeanId.getSelectCatalogXMLUrlPath() %>
   <%=  itemDescriptionBeanId.getSelectMenuCatalog()	 %>

</document>

