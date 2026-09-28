<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page import="java.util.PropertyResourceBundle,java.util.ResourceBundle"%>
<jsp:useBean id="authorizationPageBean" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="operationAmountBeanId" scope="session" class="com.cbsinc.cms.OperationAmountBean" />
<jsp:useBean id="messageMail" scope="session" class="java.util.HashMap" type="java.util.HashMap"/>
<jsp:setProperty name="authorizationPageBean" property="*" />
<%



%>
<?xml version="1.0" encoding="UTF-8"?>
<document>
   <version>1.0</version>
   <name>Authorization</name>

   <title>Authorization page</title>
   <subject_site><%=  authorizationPageBean.getSubject_site() %></subject_site>
   <site_name><%=  authorizationPageBean.getNick_site() %></site_name>
   <host><%=  authorizationPageBean.getSite_dir() %></host>
   <login><%= authorizationPageBean.getStrLogin() %></login>
   <passwdord><%=  authorizationPageBean.getStrCPasswd() %></passwdord>
   <firstname><%= authorizationPageBean.getStrFirstName() %></firstname>
   <lastname><%= authorizationPageBean.getStrLastName() %></lastname>
   <company><%= authorizationPageBean.getStrCompany() %></company>
   <email><%= authorizationPageBean.getStrEMail() %></email>
   <phone><%= authorizationPageBean.getStrPhone() %></phone>
   <mphone><%= authorizationPageBean.getStrMPhone() %></mphone>
   <fax><%= authorizationPageBean.getStrFax() %></fax>
   <icq><%= authorizationPageBean.getStrIcq() %></icq>
   <website><%= authorizationPageBean.getStrWebsite() %></website>
   <question><%= authorizationPageBean.getStrQuestion() %></question>
   <answer><%= authorizationPageBean.getStrAnswer() %></answer>
   <country><%= authorizationPageBean.getStrCountry() %></country>
   <city><%= authorizationPageBean.getStrCity() %></city>
   <site><%= authorizationPageBean.getSite_id() %></site>
   <message><%= authorizationPageBean.getStrMessage() %></message>
   <country_id><%= authorizationPageBean.getCountry_id() %></country_id>
   <city_id><%= authorizationPageBean.getCity_id() %></city_id>
   <currency_id><%= authorizationPageBean.getCurrency_id() %></currency_id>

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

<%=  authorizationPageBean.getSelect_site() %>
<%=  authorizationPageBean.getSelect_country() %>
<%=  authorizationPageBean.getSelect_city() %>
<%=  authorizationPageBean.getSelect_currency() %>
<!--  authorizationPageBean.getXMLDBList("Authorization.jsp?currency_id","currency", authorizationPageBean.getCurrency_id()  ,"SELECT currency_id , currency_desc FROM currency  WHERE active = true") -->

</document>