<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<%@ page import="com.cbsinc.cms.controllers.CurrencyEnum" %>
<%@ page import="com.cbsinc.cms.controllers.SiteType" %>
<jsp:useBean id="publisherBeanId" scope="session" class="com.cbsinc.cms.PublisherBean" />
<jsp:useBean id="catalogListBeanId" scope="session" class="com.cbsinc.cms.CatalogListBean" />
<jsp:useBean id="catalogEditBeanId" scope="session" class="com.cbsinc.cms.CatalogEditBean" />
<jsp:useBean id="catalogAddBeanId" scope="session" class="com.cbsinc.cms.CatalogAddBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="authorizationPageFaced" scope="application" class="com.cbsinc.cms.faceds.AuthorizationPageFaced" />
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
xsltUrl = request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "productpostcre.xsl" ; 
xsltUrl_default = request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  + "productpostcre.xsl" ; 
xsltpath =  "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "productpostcre.xsl" ; 
xsltpathDefault = "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/" + "productpostcre.xsl" ; 
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
    System.out.println("ProductPostCre.jsp xsltpath: " + xsltpath);
    System.out.println("ProductPostCre.jsp xslt url: " + url);
}

PrintWriter printWriter = response.getWriter();
String tmp ="<?xml-stylesheet type=\"text/xsl\" href=\""+url+"\"?>" ;
printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
printWriter.println(tmp);

%>
<document>
   <version>1.0</version>
   <name>Post product with attributes</name>
   <title>Post product with attributes</title>
   <virtual_host><%= authorizationPageBeanId.getVirtualHost() %></virtual_host>   
   <subject_site><%= authorizationPageBeanId.getSubjectSite() %></subject_site>
   <site_name><%=  authorizationPageBeanId.getNickSite() %></site_name>
   <host><%=  authorizationPageBeanId.getSiteDir() %></host>
   <domain><%=  authorizationPageBeanId.getHost() %></domain>
   <login><%=  authorizationPageBeanId.getStrLogin() %></login>
   
   <softname><%= publisherBeanId.getStrSoftName() %></softname>
   <description_label><%=authorizationPageBeanId.getLocalization(application).getString("short_info")%></description_label>
   <description><%=publisherBeanId.getStrSoftDescription()%></description>
   <fulldescription_label><%=authorizationPageBeanId.getLocalization(application).getString("full_information")%></fulldescription_label>
   <fulldescription><%=publisherBeanId.getProductFulldescription()%></fulldescription>

   <submit_bnt><%= authorizationPageBeanId.getLocalization(application).getString("save") %></submit_bnt>
   <reset_bnt><%= authorizationPageBeanId.getLocalization(application).getString("clear") %></reset_bnt>

   
   <catalog_id><%= authorizationPageBeanId.getCatalogParentId() %></catalog_id>
   <currency_id>3</currency_id>
   <softcost><%= publisherBeanId.getStrSoftCost() %></softcost>
   <image_id><%= publisherBeanId.getImageId() %></image_id> 
   <bigimage_id><%= publisherBeanId.getBigimageId() %></bigimage_id> 
   <type_id><%= publisherBeanId.getTypeId() %></type_id>
   <file_id><%= publisherBeanId.getFileId() %></file_id>
   <portlettype_id>0</portlettype_id>
   <parent_id><%= itemDescriptionBeanId.getProductId() %></parent_id> 
   <show_rating1><%= publisherBeanId.getStrShowRatimg1() %></show_rating1>
   <show_blog><%= publisherBeanId.getStrShowForum() %></show_blog>
   <show_offer><%= publisherBeanId.getStrShowOffer() %></show_offer>
   <show_action><%= publisherBeanId.getStrShowAction() %></show_action>      
   
   <imagename_label><%=authorizationPageBeanId.getLocalization(application).getString("upload_small_image")%></imagename_label>  
   <bigimagename_label><%=authorizationPageBeanId.getLocalization(application).getString("upload_big_image")%></bigimagename_label>  
   <filename_label><%=authorizationPageBeanId.getLocalization(application).getString("full_information")%></filename_label>  
   
   <imagename><%= publisherBeanId.getImgname() %></imagename>  
   <bigimagename><%= publisherBeanId.getBigimgname() %></bigimagename>  
   <filename><%= publisherBeanId.getFilename() %></filename>  
      
   <newimage><%= authorizationPageBeanId.getLocalization(application).getString("new_small_image") %></newimage> 
   <newbig_image><%= authorizationPageBeanId.getLocalization(application).getString("new_big_image") %></newbig_image>
   <newfile><%= authorizationPageBeanId.getLocalization(application).getString("new_file") %></newfile>     
   
   
   <selectimage><%= authorizationPageBeanId.getLocalization(application).getString("select_small_image") %></selectimage>  
   <selectbig_image><%= authorizationPageBeanId.getLocalization(application).getString("select_big_image") %></selectbig_image>
   <selectfile><%= authorizationPageBeanId.getLocalization(application).getString("select_file") %></selectfile>          
      
   <creteria1_id><%=publisherBeanId.getCreteria1Id()%></creteria1_id>
   <creteria_lable_1><%=publisherBeanId.getOneLabel("select  label   from creteria1   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria_lable_1>
   <creteria1_list><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria1_id", publisherBeanId.getCreteria1Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") , "select creteria1_id , name   from creteria1   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria1_list>
      
   <creteria2_id><%=publisherBeanId.getCreteria2Id()%></creteria2_id>
   <creteria_lable_2><%=publisherBeanId.getOneLabel("select  label   from creteria2   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria_lable_2>
   <creteria2_list><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria2_id", publisherBeanId.getCreteria1Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") , "select creteria2_id , name   from creteria1   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria2_list>
   
   <creteria3_id><%=publisherBeanId.getCreteria3Id()%></creteria3_id>
   <creteria_lable_3><%=publisherBeanId.getOneLabel("select  label   from creteria3   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria_lable_3>
   <creteria3_list><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria3_id", publisherBeanId.getCreteria1Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") , "select creteria3_id , name   from creteria1   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria3_list>

   <creteria4_id><%=publisherBeanId.getCreteria4Id()%></creteria4_id>         
   <creteria_lable_4><%=publisherBeanId.getOneLabel("select  label   from creteria4   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria_lable_4>
   <creteria4_list><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria4_id", publisherBeanId.getCreteria1Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") , "select creteria4_id , name   from creteria1   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria4_list>

   <creteria5_id><%=publisherBeanId.getCreteria5Id()%></creteria5_id>
   <creteria_lable_5><%=publisherBeanId.getOneLabel("select  label   from creteria5   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria_lable_5>
   <creteria5_list><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria5_id", publisherBeanId.getCreteria1Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") , "select creteria5_id , name   from creteria1   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria5_list>

   <creteria6_id><%=publisherBeanId.getCreteria6Id()%></creteria6_id>
   <creteria_lable_6><%=publisherBeanId.getOneLabel("select  label   from creteria6   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria_lable_6>
   <creteria6_list><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria6_id", publisherBeanId.getCreteria1Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") , "select creteria6_id , name   from creteria1   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria6_list>

   <creteria7_id><%=publisherBeanId.getCreteria7Id()%></creteria7_id>
   <creteria_lable_7><%=publisherBeanId.getOneLabel("select  label   from creteria7   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria_lable_7>
   <creteria7_list><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria7_id", publisherBeanId.getCreteria1Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") , "select creteria7_id , name   from creteria1   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria7_list>

   <creteria8_id><%=publisherBeanId.getCreteria8Id()%></creteria8_id>
   <creteria_lable_8><%=publisherBeanId.getOneLabel("select  label   from creteria8   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria_lable_8>
   <creteria8_list><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria8_id", publisherBeanId.getCreteria1Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") , "select creteria8_id , name   from creteria1   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria8_list>

   <creteria9_id><%=publisherBeanId.getCreteria9Id()%></creteria9_id>
   <creteria_lable_9><%=publisherBeanId.getOneLabel("select  label   from creteria9   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria_lable_9>
   <creteria9_list><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria9_id", publisherBeanId.getCreteria1Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") , "select creteria9_id , name   from creteria1   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria9_list>

   <creteria10_id><%=publisherBeanId.getCreteria10Id()%></creteria10_id>
   <creteria_lable_10><%=publisherBeanId.getOneLabel("select  label   from creteria10   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria_lable_10>
   <creteria10_list><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria10_id", publisherBeanId.getCreteria1Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") , "select creteria10_id , name   from creteria1   where  active = true " + publisherBeanId.getPartCriteria(SiteType.MAIN_SITE, authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></creteria10_list>
                     
   <!--  for members -->
   <do_form_1>
        <form-header>
        <name>postsoftform</name>
		<method>post</method>
		<action>ProductPostCre.jsp</action>
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
	        <input-12>
		        <name>parent_id</name>
		        <type>int</type>
	        </input-12>	        
        </inputs>
   </do_form_1>

</document>