<jsp:useBean id="publisherBeanId" scope="session" class="com.cbsinc.cms.PublisherBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="itemDescriptionBeanId" scope="request" class="com.cbsinc.cms.ItemDescriptionBean" />
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
xsltUrl = request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "productreviewpost.xsl" ; 
xsltUrl_default = request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  + "productreviewpost.xsl" ; 
xsltpath =  "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "productreviewpost.xsl" ; 
xsltpathDefault = "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/" + "productreviewpost.xsl" ; 
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
    System.out.println("ProductReviewPost.jsp xsltpath: " + xsltpath);
    System.out.println("ProductReviewPost.jsp xslt url: " + url);
}

PrintWriter printWriter = response.getWriter();
String tmp ="<?xml-stylesheet type=\"text/xsl\" href=\""+url+"\"?>" ;
printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
printWriter.println(tmp);

%>
<document>
   <version>1.0</version>
   <name>Post product review</name>
   <title>Post product review</title>
   <virtual_host><%= authorizationPageBeanId.getVirtualHost() %></virtual_host>   
   <subject_site><%=  authorizationPageBeanId.getSubjectSite() %></subject_site>
   <site_name><%=  authorizationPageBeanId.getNickSite() %></site_name>
   <host><%=  authorizationPageBeanId.getSiteDir() %></host>
   <domain><%=  authorizationPageBeanId.getHost() %></domain>
   <login><%=  authorizationPageBeanId.getStrLogin() %></login>
   <softname><%= publisherBeanId.getStrSoftName() %></softname>
   <description_label><%=authorizationPageBeanId.getLocalization(application).getString("short_info")%></description_label>
   <description><%=publisherBeanId.getStrSoftDescription()%></description>
   <submit_bnt><%= authorizationPageBeanId.getLocalization(application).getString("save") %></submit_bnt>
   <reset_bnt><%= authorizationPageBeanId.getLocalization(application).getString("clear") %></reset_bnt>
   <cupture_code></cupture_code>
   <cupture_label><%= authorizationPageBeanId.getLocalization(application).getString("before_input_generator_code") %></cupture_label>
   <cupture_image_url>gennumberservlet</cupture_image_url>
   <catalog_id><%= authorizationPageBeanId.getCatalogId() %></catalog_id>
   <currency_id>3</currency_id>
   <softcost>0</softcost>
   <bigimage_id>-1</bigimage_id> 
   <type_id>0</type_id>
   <file_id>-1</file_id>
   <portlettype_id>3</portlettype_id>
   <parent_id><%= itemDescriptionBeanId.getProductId() %></parent_id>   
                     
   <!--  for members -->
   <do_form_1>
        <form-header>
        <name>postsoftform</name>
		<method>post</method>
		<action>ProductReviewPost.jsp</action>
        </form-header>

        <inputs>
	        <input-1>
		        <name>gen_number</name>
		        <type>string</type>
	        </input-1>
	        <input-2>
		        <name>softname</name>
		        <type>string</type>
	        </input-2>
	        <input-3>
		        <name>description</name>
		        <type>string</type>
	        </input-3>
	        <input-4>
		        <name>catalog_id</name>
		        <type>int</type>
	        </input-4>
	        <input-5>
		        <name>currency_id</name>
		        <type>int</type>
	        </input-5>
	        <input-6>
		        <name>softcost</name>
		        <type>double</type>
	        </input-6>
	        <input-7>
		        <name>bigimage_id</name>
		        <type>int</type>
	        </input-7>
	        <input-8>
		        <name>file_id</name>
		        <type>inr</type>
	        </input-8>
	        <input-9>
		        <name>type_id</name>
		        <type>int</type>
	        </input-9>	        	                
	        <input-10>
		        <name>portlettype_id</name>
		        <type>inr</type>
	        </input-10>
	        <input-11>
		        <name>parent_id</name>
		        <type>int</type>
	        </input-11>
        </inputs>
   </do_form_1>

</document>