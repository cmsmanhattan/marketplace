<%@page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp" %>
<%@page import="java.util.PropertyResourceBundle,java.util.ResourceBundle"%>
<jsp:useBean id="searchBeanId" scope="request" class="com.cbsinc.cms.SearchBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="catalogListBeanId" scope="session" class="com.cbsinc.cms.CatalogListBean" />

<?xml version="1.0" encoding="UTF-8"?>
<document>
   <version>1.0</version>
   <name>Catalog</name>

   <title>Catalog test </title>
   <reklama>Here may be your reklama</reklama>
   <subject_site><%=  authorizationPageBeanId.getNickSite() %></subject_site>
   <site_name><%=  authorizationPageBeanId.getNickSite() %></site_name>
   <host><%=  authorizationPageBeanId.getSiteDir() %></host>
   <message><%= authorizationPageBeanId.getStrMessage() %></message>
   <login><%=  authorizationPageBeanId.getStrLogin() %></login>
   <passwdord><%=  authorizationPageBeanId.getStrCPasswd() %></passwdord>
   <balans><%=  authorizationPageBeanId.getBalance() %></balans>
   <search_value><%=  searchBeanId.getSearchValueArg() %></search_value>
   <search_query><%=  searchBeanId.getSearchquery() %></search_query	>
   <fromcost> <%=  authorizationPageBeanId.getFromCost() %> </fromcost>
   <tocost> <%=  authorizationPageBeanId.getToCost() %>  </tocost>
   <to_navigator>wCatalog.jsp</to_navigator>
   <to_navigator_location>NavigatorLocation.jsp</to_navigator_location>
   <to_account_history>wAccountHist.jsp</to_account_history>
   <to_pay>PrePay.jsp</to_pay>
   <to_order>Order.jsp</to_order>
   <to_order_hist>OrderList.jsp</to_order_hist>
   <to_login>Authorization.jsp</to_login>
   <to_registration>RegPage.jsp</to_registration>
   <owner_user_id><%=  authorizationPageBeanId.getIntUserID() %></owner_user_id>
   <role_id><%=  authorizationPageBeanId.getRoleId() %></role_id>
   <user_site_id><%=  authorizationPageBeanId.getUserSite() %></user_site_id>
   <site_id><%=  authorizationPageBeanId.getSiteId() %></site_id>
   <country_id><%= authorizationPageBeanId.getCountryId() %></country_id>
   <city_id><%= authorizationPageBeanId.getCityId() %></city_id>
   <country><%= authorizationPageBeanId.getStrCountry() %></country>
   <city><%= authorizationPageBeanId.getStrCity() %></city>
 
   <![CDATA[]]>
   <path><%= catalogListBeanId.getCatalogUrlPath(authorizationPageBeanId) %></path>

   <dialog><%= searchBeanId.getDialog() %></dialog>
   <is_advanced_search_open><%= searchBeanId.getAdvancedSearchOpen() %></is_advanced_search_open>
   <is_forum_open><%= searchBeanId.getForumOpen() %></is_forum_open>
   <internet><%= searchBeanId.isInternet %></internet>


   <admin>
   <post_manager><%=  searchBeanId.getTrueValue("PostManager.jsp","",authorizationPageBeanId.getRoleId()==2) %></post_manager>
   <post_manager_img><%=  searchBeanId.getTrueValue("images/post.jpg","",authorizationPageBeanId.getRoleId()==2) %></post_manager_img>
   <post_manager_text><%=  searchBeanId.getTrueValue("Post","",authorizationPageBeanId.getRoleId()==2) %></post_manager_text>
   </admin>

   <xslstyle>
<!-- javascript:window.open('xsl.html' ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100'); -->
   <xsl_url><%= searchBeanId.getTrueValue("xsl.jsp","",authorizationPageBeanId.getRoleId()==2) %></xsl_url>
   <xsl_url_text><%=  searchBeanId.getTrueValue("change style","",authorizationPageBeanId.getRoleId()==2) %></xsl_url_text>
   </xslstyle>



<%=  searchBeanId.getProductlist("" + authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId()) %>
<%=  searchBeanId.getProductSimpleList("" + authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId()) %>
<%=  searchBeanId.getCoOneProductlist("" + authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId()) %>
<%=  searchBeanId.getCoTwoProductlist("" + authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId()) %>
<%=  authorizationPageBeanId.getSelectCountry() %>
<%=  authorizationPageBeanId.getSelectCity() %>

   <!-- ????????? ?????? ???????? ??? ???   -->
   <empty_page_co1><%=  searchBeanId.getPagecountCo1() == 0 %></empty_page_co1>
   <!-- ????????? ?????? ???????? ??? ???   -->
   <empty_page_co2><%=  searchBeanId.getPagecountCo2() == 0 %></empty_page_co2>
   <!-- ????????? ?????? ???????? ??? ???   -->
   <empty_page><%=  searchBeanId.getPagecount() == 0 %></empty_page>
   <!-- ?????????? ???????    -->
   <quantity_products><%=  searchBeanId.getAllFoundProducts() %></quantity_products>
   <!-- ?????????? Offset   -->
   <offset><%=  searchBeanId.getOffset() %></offset>

<next><%=  searchBeanId.getListup() %></next>
<prev><%=  searchBeanId.getListdown() %></prev>


<%=  searchBeanId.getSelectCurrencyCd() %>




</document>

