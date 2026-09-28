
<jsp:useBean id="productlistBeanId" scope="request" class="com.cbsinc.cms.ProductlistBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="catalogListBeanId" scope="session" class="com.cbsinc.cms.CatalogListBean" />
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
xsltUrl =  request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "DeployDesign.xsl" ; 
xsltUrl_default = request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  + "DeployDesign.xsl" ; 
xsltpath =  "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "DeployDesign.xsl" ; 
xsltpathDefault = "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/" + "DeployDesign.xsl" ; 
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
    System.out.println("DeployDesign.jsp xsltpath: " + xsltpath);
    System.out.println("DeployDesign.jsp xslt url: " + url);
}

PrintWriter printWriter = response.getWriter();
String tmp ="<?xml-stylesheet type=\"text/xsl\" href=\""+url+"\"?>" ;
printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
printWriter.println(tmp);

%>
 
<document>
   <version>1.0</version>
   <name>Catalog</name>

   <title><%=  authorizationPageBeanId.getHost() %> </title>
   <virtual_host><%= authorizationPageBeanId.getVirtualHost() %></virtual_host>
   <reklama>Here may be your reklama</reklama>
   <subject_site><%=  authorizationPageBeanId.getNickSite() %></subject_site>
   <site_name><%=  authorizationPageBeanId.getNickSite() %></site_name>
   <host><%=  authorizationPageBeanId.getSiteDir() %></host>
   <message><%= authorizationPageBeanId.getStrMessage() %></message>
   <login><%=  authorizationPageBeanId.getStrLogin() %></login>
   <passwdord><%=  authorizationPageBeanId.getStrCPasswd() %></passwdord>
   <balans><%=  authorizationPageBeanId.getBalance() %></balans>
   <search_value><%=  productlistBeanId.getSearchValueArg() %></search_value>
   <search_query><%=  productlistBeanId.getSearchquery() %></search_query	>
   <fromcost> <%=  authorizationPageBeanId.getFromCost() %> </fromcost>
   <tocost> <%=  authorizationPageBeanId.getToCost() %>  </tocost>
   <to_navigator>wCatalog.jsp</to_navigator>
   <to_navigator_location>NavigatorLocation.jsp</to_navigator_location>
   <to_account_history>AccountHistory.jsp</to_account_history>
   <to_pay>PrePay.jsp</to_pay>
   <to_order>Order.jsp</to_order>
   <to_order_hist>OrderList.jsp</to_order_hist>
   <to_login>Authorization.jsp</to_login>
   <to_registration>RegPage.jsp</to_registration>
   <owner_user_id><%=  authorizationPageBeanId.getIntUserID() %></owner_user_id>
   <role_id><%=  authorizationPageBeanId.getRoleId() %></role_id>
   <user_site_id><%=  authorizationPageBeanId.getUserSite() %></user_site_id>
   <site_id><%=  authorizationPageBeanId.getSiteId() %></site_id>
   <![CDATA[]]>
   <path><%= catalogListBeanId.getCatalogUrlPath(authorizationPageBeanId) %></path>

   <dialog><%= productlistBeanId.getDialog() %></dialog>
   <is_advanced_search_open><%= productlistBeanId.getAdvancedSearchOpen() %></is_advanced_search_open>
   <is_forum_open><%= productlistBeanId.getForumOpen() %></is_forum_open>
   <internet><%= productlistBeanId.isInternet %></internet>


   <admin>
   <post_manager><%=  productlistBeanId.getTrueValue("PostManager.jsp","",authorizationPageBeanId.getRoleId()==2) %></post_manager>
   <post_manager_img><%=  productlistBeanId.getTrueValue("images/post.jpg","",authorizationPageBeanId.getRoleId()==2) %></post_manager_img>
   <post_manager_text><%=  productlistBeanId.getTrueValue("Post","",authorizationPageBeanId.getRoleId()==2) %></post_manager_text>
   </admin>

   <xslstyle>
<!-- javascript:window.open('xsl.html' ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100'); -->
   <xsl_url><%= productlistBeanId.getTrueValue("xsl.jsp","",authorizationPageBeanId.getRoleId()==2) %></xsl_url>
   <xsl_url_text><%=  productlistBeanId.getTrueValue("change style","",authorizationPageBeanId.getRoleId()==2) %></xsl_url_text>
   </xslstyle>



<%=  productlistBeanId.getProductSimpleList(authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId()) %>
<%=  productlistBeanId.getCoOneProductlist("" + authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId()) %>
<%=  productlistBeanId.getCoTwoProductlist("" + authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId()) %>
<%=  productlistBeanId.getBlogTopProductlist(authorizationPageBeanId.getSiteId()) %>
<%=  productlistBeanId.getNewslist("" + authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId()) %>
<%=  productlistBeanId.getBottomList("" + authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId()) %>

   <!-- ????????? ?????? ???????? ??? ??? ?  -->
   <empty_page_co1><%=  productlistBeanId.getPagecountCo1() == 0 %></empty_page_co1>
   <!-- ????????? ?????? ???????? ??? ???   -->
   <empty_page_co2><%=  productlistBeanId.getPagecountCo2() == 0 %></empty_page_co2>
   <!-- ????????? ?????? ???????? ??? ???   -->
   <empty_page><%=  productlistBeanId.getPagecount() == 0 %></empty_page>
   <!-- ?????????? ???????    -->
   <quantity_products><%=  productlistBeanId.getAllFoundProducts() %></quantity_products>
   <!-- ?????????? Offset   -->
   <offset><%=  productlistBeanId.getOffset() %></offset>

<next><%=  productlistBeanId.getListup() %></next>
<prev><%=  productlistBeanId.getListdown() %></prev>

<criteria1_label><%= productlistBeanId.getCriteria1Label() %></criteria1_label>
<criteria2_label><%= productlistBeanId.getCriteria2Label() %></criteria2_label>
<criteria3_label><%= productlistBeanId.getCriteria3Label() %></criteria3_label>
<criteria4_label><%= productlistBeanId.getCriteria4Label() %></criteria4_label>
<criteria5_label><%= productlistBeanId.getCriteria5Label() %></criteria5_label>
<criteria6_label><%= productlistBeanId.getCriteria6Label() %></criteria6_label>
<criteria7_label><%= productlistBeanId.getCriteria7Label() %></criteria7_label>
<criteria8_label><%= productlistBeanId.getCriteria8Label() %></criteria8_label>
<criteria9_label><%= productlistBeanId.getCriteria9Label() %></criteria9_label>
<criteria10_label><%= productlistBeanId.getCriteria10Label() %></criteria10_label>


<%=  productlistBeanId.getSelectCurrencyCd() %>
<% // productlistBeanId.getTreeXMLDBList("Productlist.jsp?catalog_id","catalog", productlistBeanId.getCatalog_id() ,"select catalog_id , lable   from catalog   where  active = true and site_id = " + authorizationPageBeanId.getSite_id() + " and parent_id = " + productlistBeanId.getCatalogParent_id() ,"select catalog_id , lable   from catalog   where  active = true and parent_id = " + productlistBeanId.getCatalog_id()  )	;  %>
<%=  productlistBeanId.getSelectTreeCatalog()	 %>
<%=  productlistBeanId.getSelectMenuCatalog()	 %>
<%=  productlistBeanId.getSelectCreteria1Id() %>
<%=  productlistBeanId.getSelectCreteria2Id() %>
<%=  productlistBeanId.getSelectCreteria3Id() %>
<%=  productlistBeanId.getSelectCreteria4Id() %>
<%=  productlistBeanId.getSelectCreteria5Id() %>
<%=  productlistBeanId.getSelectCreteria6Id() %>
<%=  productlistBeanId.getSelectCreteria7Id() %>
<%=  productlistBeanId.getSelectCreteria8Id() %>
<%=  productlistBeanId.getSelectCreteria9Id() %>
<%=  productlistBeanId.getSelectCreteria10Id() %>

<%=  productlistBeanId.getSelectDayfromId() %>
<%=  productlistBeanId.getSelectMountfromId() %>
<%=  productlistBeanId.getSelectYearfromId() %>

<%=  productlistBeanId.getSelectDaytoId() %>
<%=  productlistBeanId.getSelectMounttoId() %>
<%=  productlistBeanId.getSelectYeartoId() %>

<%=  catalogListBeanId.getCatalogXMLUrlPath("Productlist.jsp?catalog_id","parent",authorizationPageBeanId.getCatalogParentId(),authorizationPageBeanId.getCatalogId(),authorizationPageBeanId) %>



</document>

