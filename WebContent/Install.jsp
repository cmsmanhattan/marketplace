<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:setProperty name="authorizationPageBeanId" property="*" />
<%-- explicit mapping: request params keep their original names; bean properties are camelCase --%>
<jsp:setProperty name="authorizationPageBeanId" property="catalogParentId" param="catalogParent_id" />
<jsp:setProperty name="authorizationPageBeanId" property="catalogId" param="catalog_id" />
<jsp:setProperty name="authorizationPageBeanId" property="catalogParentId" param="catalog_parent_id" />
<jsp:setProperty name="authorizationPageBeanId" property="cityId" param="city_id" />
<jsp:setProperty name="authorizationPageBeanId" property="companyName" param="company_name" />
<jsp:setProperty name="authorizationPageBeanId" property="countryId" param="country_id" />
<jsp:setProperty name="authorizationPageBeanId" property="creteria10Id" param="creteria10_id" />
<jsp:setProperty name="authorizationPageBeanId" property="creteria1Id" param="creteria1_id" />
<jsp:setProperty name="authorizationPageBeanId" property="creteria2Id" param="creteria2_id" />
<jsp:setProperty name="authorizationPageBeanId" property="creteria3Id" param="creteria3_id" />
<jsp:setProperty name="authorizationPageBeanId" property="creteria4Id" param="creteria4_id" />
<jsp:setProperty name="authorizationPageBeanId" property="creteria5Id" param="creteria5_id" />
<jsp:setProperty name="authorizationPageBeanId" property="creteria6Id" param="creteria6_id" />
<jsp:setProperty name="authorizationPageBeanId" property="creteria7Id" param="creteria7_id" />
<jsp:setProperty name="authorizationPageBeanId" property="creteria8Id" param="creteria8_id" />
<jsp:setProperty name="authorizationPageBeanId" property="creteria9Id" param="creteria9_id" />
<jsp:setProperty name="authorizationPageBeanId" property="currencyId" param="currency_id" />
<jsp:setProperty name="authorizationPageBeanId" property="dayfromId" param="dayfrom_id" />
<jsp:setProperty name="authorizationPageBeanId" property="daytoId" param="dayto_id" />
<jsp:setProperty name="authorizationPageBeanId" property="langId" param="lang_id" />
<jsp:setProperty name="authorizationPageBeanId" property="mountfromId" param="mountfrom_id" />
<jsp:setProperty name="authorizationPageBeanId" property="mounttoId" param="mountto_id" />
<jsp:setProperty name="authorizationPageBeanId" property="nickSite" param="nick_site" />
<jsp:setProperty name="authorizationPageBeanId" property="paysysShopCd" param="paysys_shop_cd" />
<jsp:setProperty name="authorizationPageBeanId" property="rezaltReg" param="rezalt_reg" />
<jsp:setProperty name="authorizationPageBeanId" property="selectCity" param="select_city" />
<jsp:setProperty name="authorizationPageBeanId" property="selectCountry" param="select_country" />
<jsp:setProperty name="authorizationPageBeanId" property="selectCurrency" param="select_currency" />
<jsp:setProperty name="authorizationPageBeanId" property="selectMenuCatalog" param="select_menu_catalog" />
<jsp:setProperty name="authorizationPageBeanId" property="selectSite" param="select_site" />
<jsp:setProperty name="authorizationPageBeanId" property="siteDir" param="site_dir" />
<jsp:setProperty name="authorizationPageBeanId" property="siteId" param="site_id" />
<jsp:setProperty name="authorizationPageBeanId" property="subjectSite" param="subject_site" />
<jsp:setProperty name="authorizationPageBeanId" property="userSite" param="user_site" />
<jsp:setProperty name="authorizationPageBeanId" property="yearfromId" param="yearfrom_id" />
<jsp:setProperty name="authorizationPageBeanId" property="yeartoId" param="yearto_id" />
<%



%>
<?xml version="1.0" encoding="UTF-8"?>
<document>
   <version>1.0</version>
   <name>Authorization</name>

   <title>Authorization page</title>
   <subject_site><%=  authorizationPageBeanId.getSubjectSite() %></subject_site>
   <site_name><%=  authorizationPageBeanId.getNickSite() %></site_name>
   <host><%=  authorizationPageBeanId.getSiteDir() %></host>
   <login><%= authorizationPageBeanId.getStrLogin() %></login>
   <passwdord><%=  authorizationPageBeanId.getStrCPasswd() %></passwdord>
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

<%=  authorizationPageBeanId.getSelectSite() %>
<%=  authorizationPageBeanId.getSelectCountry() %>
<%=  authorizationPageBeanId.getSelectCity() %>
<%=  authorizationPageBeanId.getSelectCurrency() %>
<!--  authorizationPageBeanId.getXMLDBList("Authorization.jsp?currency_id","currency", authorizationPageBeanId.getCurrency_id()  ,"SELECT currency_id , currency_desc FROM currency  WHERE active = true") -->

</document>