<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<%
  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
  request.setCharacterEncoding("UTF-8");
%>
<html>
<HEAD>
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="publisherBeanId" scope="session" class="com.cbsinc.cms.PublisherBean" />
<jsp:setProperty name="publisherBeanId" property="*" />
<%-- explicit mapping: request params keep their original names; bean properties are camelCase --%>
<jsp:setProperty name="publisherBeanId" property="bigimageId" param="bigimage_id" />
<jsp:setProperty name="publisherBeanId" property="cardCode" param="card_code" />
<jsp:setProperty name="publisherBeanId" property="cardNumber" param="card_number" />
<jsp:setProperty name="publisherBeanId" property="catalogImageId" param="catalogImage_id" />
<jsp:setProperty name="publisherBeanId" property="creteria10Id" param="creteria10_id" />
<jsp:setProperty name="publisherBeanId" property="creteria10Name" param="creteria10_name" />
<jsp:setProperty name="publisherBeanId" property="creteria1Id" param="creteria1_id" />
<jsp:setProperty name="publisherBeanId" property="creteria1Name" param="creteria1_name" />
<jsp:setProperty name="publisherBeanId" property="creteria2Id" param="creteria2_id" />
<jsp:setProperty name="publisherBeanId" property="creteria2Name" param="creteria2_name" />
<jsp:setProperty name="publisherBeanId" property="creteria3Id" param="creteria3_id" />
<jsp:setProperty name="publisherBeanId" property="creteria3Name" param="creteria3_name" />
<jsp:setProperty name="publisherBeanId" property="creteria4Id" param="creteria4_id" />
<jsp:setProperty name="publisherBeanId" property="creteria4Name" param="creteria4_name" />
<jsp:setProperty name="publisherBeanId" property="creteria5Id" param="creteria5_id" />
<jsp:setProperty name="publisherBeanId" property="creteria5Name" param="creteria5_name" />
<jsp:setProperty name="publisherBeanId" property="creteria6Id" param="creteria6_id" />
<jsp:setProperty name="publisherBeanId" property="creteria6Name" param="creteria6_name" />
<jsp:setProperty name="publisherBeanId" property="creteria7Id" param="creteria7_id" />
<jsp:setProperty name="publisherBeanId" property="creteria7Name" param="creteria7_name" />
<jsp:setProperty name="publisherBeanId" property="creteria8Id" param="creteria8_id" />
<jsp:setProperty name="publisherBeanId" property="creteria8Name" param="creteria8_name" />
<jsp:setProperty name="publisherBeanId" property="creteria9Id" param="creteria9_id" />
<jsp:setProperty name="publisherBeanId" property="creteria9Name" param="creteria9_name" />
<jsp:setProperty name="publisherBeanId" property="criteria10Label" param="criteria10_label" />
<jsp:setProperty name="publisherBeanId" property="criteria1Label" param="criteria1_label" />
<jsp:setProperty name="publisherBeanId" property="criteria2Label" param="criteria2_label" />
<jsp:setProperty name="publisherBeanId" property="criteria3Label" param="criteria3_label" />
<jsp:setProperty name="publisherBeanId" property="criteria4Label" param="criteria4_label" />
<jsp:setProperty name="publisherBeanId" property="criteria5Label" param="criteria5_label" />
<jsp:setProperty name="publisherBeanId" property="criteria6Label" param="criteria6_label" />
<jsp:setProperty name="publisherBeanId" property="criteria7Label" param="criteria7_label" />
<jsp:setProperty name="publisherBeanId" property="criteria8Label" param="criteria8_label" />
<jsp:setProperty name="publisherBeanId" property="criteria9Label" param="criteria9_label" />
<jsp:setProperty name="publisherBeanId" property="dayId" param="day_id" />
<jsp:setProperty name="publisherBeanId" property="fileId" param="file_id" />
<jsp:setProperty name="publisherBeanId" property="imageId" param="image_id" />
<jsp:setProperty name="publisherBeanId" property="jspUrl" param="jsp_url" />
<jsp:setProperty name="publisherBeanId" property="licenceId" param="licence_id" />
<jsp:setProperty name="publisherBeanId" property="mountId" param="mount_id" />
<jsp:setProperty name="publisherBeanId" property="parentPortlettypeId" param="parent_portlettype_id" />
<jsp:setProperty name="publisherBeanId" property="phonemodelId" param="phonemodel_id" />
<jsp:setProperty name="publisherBeanId" property="phonetypeId" param="phonetype_id" />
<jsp:setProperty name="publisherBeanId" property="portlettypeId" param="portlettype_id" />
<jsp:setProperty name="publisherBeanId" property="productCodeId" param="product_code_id" />
<jsp:setProperty name="publisherBeanId" property="productFulldescription" param="product_fulldescription" />
<jsp:setProperty name="publisherBeanId" property="prognameId" param="progname_id" />
<jsp:setProperty name="publisherBeanId" property="salelogicId" param="salelogic_id" />
<jsp:setProperty name="publisherBeanId" property="selectBigImageUrl" param="select_big_image_url" />
<jsp:setProperty name="publisherBeanId" property="selectBigImages" param="select_big_images" />
<jsp:setProperty name="publisherBeanId" property="selectCatalogImageUrl" param="select_catalog_image_url" />
<jsp:setProperty name="publisherBeanId" property="selectCatalogImages" param="select_catalog_images" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria10Id" param="select_creteria10_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria1Id" param="select_creteria1_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria2Id" param="select_creteria2_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria3Id" param="select_creteria3_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria4Id" param="select_creteria4_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria5Id" param="select_creteria5_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria6Id" param="select_creteria6_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria7Id" param="select_creteria7_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria8Id" param="select_creteria8_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria9Id" param="select_creteria9_id" />
<jsp:setProperty name="publisherBeanId" property="selectCurrencyCd" param="select_currency_cd" />
<jsp:setProperty name="publisherBeanId" property="selectDayfromId" param="select_dayfrom_id" />
<jsp:setProperty name="publisherBeanId" property="selectDaytoId" param="select_dayto_id" />
<jsp:setProperty name="publisherBeanId" property="selectFiles" param="select_files" />
<jsp:setProperty name="publisherBeanId" property="selectMountfromId" param="select_mountfrom_id" />
<jsp:setProperty name="publisherBeanId" property="selectMounttoId" param="select_mountto_id" />
<jsp:setProperty name="publisherBeanId" property="selectPath" param="select_path" />
<jsp:setProperty name="publisherBeanId" property="selectSmallImageUrl" param="select_small_image_url" />
<jsp:setProperty name="publisherBeanId" property="selectSmallImages" param="select_small_images" />
<jsp:setProperty name="publisherBeanId" property="selectTreeCatalog" param="select_tree_catalog" />
<jsp:setProperty name="publisherBeanId" property="selectYearfromId" param="select_yearfrom_id" />
<jsp:setProperty name="publisherBeanId" property="selectYeartoId" param="select_yearto_id" />
<jsp:setProperty name="publisherBeanId" property="serialNubmer" param="serial_nubmer" />
<jsp:setProperty name="publisherBeanId" property="showActionChecked" param="show_action_checked" />
<jsp:setProperty name="publisherBeanId" property="showForumChecked" param="show_forum_checked" />
<jsp:setProperty name="publisherBeanId" property="showOfferChecked" param="show_offer_checked" />
<jsp:setProperty name="publisherBeanId" property="siteId" param="site_id" />
<jsp:setProperty name="publisherBeanId" property="softId" param="soft_id" />
<jsp:setProperty name="publisherBeanId" property="strShowAction" param="strShow_action" />
<jsp:setProperty name="publisherBeanId" property="strShowForum" param="strShow_forum" />
<jsp:setProperty name="publisherBeanId" property="strShowOffer" param="strShow_offer" />
<jsp:setProperty name="publisherBeanId" property="strShowRatimg1" param="strShow_ratimg1" />
<jsp:setProperty name="publisherBeanId" property="strShowRatimg1Checked" param="strShow_ratimg1_checked" />
<jsp:setProperty name="publisherBeanId" property="strShowRatimg2" param="strShow_ratimg2" />
<jsp:setProperty name="publisherBeanId" property="strShowRatimg2Checked" param="strShow_ratimg2_checked" />
<jsp:setProperty name="publisherBeanId" property="strShowRatimg3" param="strShow_ratimg3" />
<jsp:setProperty name="publisherBeanId" property="strShowRatimg3Checked" param="strShow_ratimg3_checked" />
<jsp:setProperty name="publisherBeanId" property="typeCardId" param="type_card_id" />
<jsp:setProperty name="publisherBeanId" property="typeId" param="type_id" />
<jsp:setProperty name="publisherBeanId" property="userId" param="user_id" />
<jsp:setProperty name="publisherBeanId" property="yearId" param="year_id" />
<%-- explicit mapping: request params keep their original names; bean properties are camelCase --%>
<jsp:setProperty name="publisherBeanId" property="bigimageId" param="bigimage_id" />
<jsp:setProperty name="publisherBeanId" property="cardCode" param="card_code" />
<jsp:setProperty name="publisherBeanId" property="cardNumber" param="card_number" />
<jsp:setProperty name="publisherBeanId" property="catalogImageId" param="catalogImage_id" />
<jsp:setProperty name="publisherBeanId" property="creteria10Id" param="creteria10_id" />
<jsp:setProperty name="publisherBeanId" property="creteria10Name" param="creteria10_name" />
<jsp:setProperty name="publisherBeanId" property="creteria1Id" param="creteria1_id" />
<jsp:setProperty name="publisherBeanId" property="creteria1Name" param="creteria1_name" />
<jsp:setProperty name="publisherBeanId" property="creteria2Id" param="creteria2_id" />
<jsp:setProperty name="publisherBeanId" property="creteria2Name" param="creteria2_name" />
<jsp:setProperty name="publisherBeanId" property="creteria3Id" param="creteria3_id" />
<jsp:setProperty name="publisherBeanId" property="creteria3Name" param="creteria3_name" />
<jsp:setProperty name="publisherBeanId" property="creteria4Id" param="creteria4_id" />
<jsp:setProperty name="publisherBeanId" property="creteria4Name" param="creteria4_name" />
<jsp:setProperty name="publisherBeanId" property="creteria5Id" param="creteria5_id" />
<jsp:setProperty name="publisherBeanId" property="creteria5Name" param="creteria5_name" />
<jsp:setProperty name="publisherBeanId" property="creteria6Id" param="creteria6_id" />
<jsp:setProperty name="publisherBeanId" property="creteria6Name" param="creteria6_name" />
<jsp:setProperty name="publisherBeanId" property="creteria7Id" param="creteria7_id" />
<jsp:setProperty name="publisherBeanId" property="creteria7Name" param="creteria7_name" />
<jsp:setProperty name="publisherBeanId" property="creteria8Id" param="creteria8_id" />
<jsp:setProperty name="publisherBeanId" property="creteria8Name" param="creteria8_name" />
<jsp:setProperty name="publisherBeanId" property="creteria9Id" param="creteria9_id" />
<jsp:setProperty name="publisherBeanId" property="creteria9Name" param="creteria9_name" />
<jsp:setProperty name="publisherBeanId" property="criteria10Label" param="criteria10_label" />
<jsp:setProperty name="publisherBeanId" property="criteria1Label" param="criteria1_label" />
<jsp:setProperty name="publisherBeanId" property="criteria2Label" param="criteria2_label" />
<jsp:setProperty name="publisherBeanId" property="criteria3Label" param="criteria3_label" />
<jsp:setProperty name="publisherBeanId" property="criteria4Label" param="criteria4_label" />
<jsp:setProperty name="publisherBeanId" property="criteria5Label" param="criteria5_label" />
<jsp:setProperty name="publisherBeanId" property="criteria6Label" param="criteria6_label" />
<jsp:setProperty name="publisherBeanId" property="criteria7Label" param="criteria7_label" />
<jsp:setProperty name="publisherBeanId" property="criteria8Label" param="criteria8_label" />
<jsp:setProperty name="publisherBeanId" property="criteria9Label" param="criteria9_label" />
<jsp:setProperty name="publisherBeanId" property="dayId" param="day_id" />
<jsp:setProperty name="publisherBeanId" property="fileId" param="file_id" />
<jsp:setProperty name="publisherBeanId" property="imageId" param="image_id" />
<jsp:setProperty name="publisherBeanId" property="jspUrl" param="jsp_url" />
<jsp:setProperty name="publisherBeanId" property="licenceId" param="licence_id" />
<jsp:setProperty name="publisherBeanId" property="mountId" param="mount_id" />
<jsp:setProperty name="publisherBeanId" property="parentPortlettypeId" param="parent_portlettype_id" />
<jsp:setProperty name="publisherBeanId" property="phonemodelId" param="phonemodel_id" />
<jsp:setProperty name="publisherBeanId" property="phonetypeId" param="phonetype_id" />
<jsp:setProperty name="publisherBeanId" property="portlettypeId" param="portlettype_id" />
<jsp:setProperty name="publisherBeanId" property="productCodeId" param="product_code_id" />
<jsp:setProperty name="publisherBeanId" property="productFulldescription" param="product_fulldescription" />
<jsp:setProperty name="publisherBeanId" property="prognameId" param="progname_id" />
<jsp:setProperty name="publisherBeanId" property="salelogicId" param="salelogic_id" />
<jsp:setProperty name="publisherBeanId" property="selectBigImageUrl" param="select_big_image_url" />
<jsp:setProperty name="publisherBeanId" property="selectBigImages" param="select_big_images" />
<jsp:setProperty name="publisherBeanId" property="selectCatalogImageUrl" param="select_catalog_image_url" />
<jsp:setProperty name="publisherBeanId" property="selectCatalogImages" param="select_catalog_images" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria10Id" param="select_creteria10_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria1Id" param="select_creteria1_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria2Id" param="select_creteria2_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria3Id" param="select_creteria3_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria4Id" param="select_creteria4_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria5Id" param="select_creteria5_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria6Id" param="select_creteria6_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria7Id" param="select_creteria7_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria8Id" param="select_creteria8_id" />
<jsp:setProperty name="publisherBeanId" property="selectCreteria9Id" param="select_creteria9_id" />
<jsp:setProperty name="publisherBeanId" property="selectCurrencyCd" param="select_currency_cd" />
<jsp:setProperty name="publisherBeanId" property="selectDayfromId" param="select_dayfrom_id" />
<jsp:setProperty name="publisherBeanId" property="selectDaytoId" param="select_dayto_id" />
<jsp:setProperty name="publisherBeanId" property="selectFiles" param="select_files" />
<jsp:setProperty name="publisherBeanId" property="selectMountfromId" param="select_mountfrom_id" />
<jsp:setProperty name="publisherBeanId" property="selectMounttoId" param="select_mountto_id" />
<jsp:setProperty name="publisherBeanId" property="selectPath" param="select_path" />
<jsp:setProperty name="publisherBeanId" property="selectSmallImageUrl" param="select_small_image_url" />
<jsp:setProperty name="publisherBeanId" property="selectSmallImages" param="select_small_images" />
<jsp:setProperty name="publisherBeanId" property="selectTreeCatalog" param="select_tree_catalog" />
<jsp:setProperty name="publisherBeanId" property="selectYearfromId" param="select_yearfrom_id" />
<jsp:setProperty name="publisherBeanId" property="selectYeartoId" param="select_yearto_id" />
<jsp:setProperty name="publisherBeanId" property="serialNubmer" param="serial_nubmer" />
<jsp:setProperty name="publisherBeanId" property="showActionChecked" param="show_action_checked" />
<jsp:setProperty name="publisherBeanId" property="showForumChecked" param="show_forum_checked" />
<jsp:setProperty name="publisherBeanId" property="showOfferChecked" param="show_offer_checked" />
<jsp:setProperty name="publisherBeanId" property="siteId" param="site_id" />
<jsp:setProperty name="publisherBeanId" property="softId" param="soft_id" />
<jsp:setProperty name="publisherBeanId" property="strShowAction" param="strShow_action" />
<jsp:setProperty name="publisherBeanId" property="strShowForum" param="strShow_forum" />
<jsp:setProperty name="publisherBeanId" property="strShowOffer" param="strShow_offer" />
<jsp:setProperty name="publisherBeanId" property="strShowRatimg1" param="strShow_ratimg1" />
<jsp:setProperty name="publisherBeanId" property="strShowRatimg1Checked" param="strShow_ratimg1_checked" />
<jsp:setProperty name="publisherBeanId" property="strShowRatimg2" param="strShow_ratimg2" />
<jsp:setProperty name="publisherBeanId" property="strShowRatimg2Checked" param="strShow_ratimg2_checked" />
<jsp:setProperty name="publisherBeanId" property="strShowRatimg3" param="strShow_ratimg3" />
<jsp:setProperty name="publisherBeanId" property="strShowRatimg3Checked" param="strShow_ratimg3_checked" />
<jsp:setProperty name="publisherBeanId" property="typeCardId" param="type_card_id" />
<jsp:setProperty name="publisherBeanId" property="typeId" param="type_id" />
<jsp:setProperty name="publisherBeanId" property="userId" param="user_id" />
<jsp:setProperty name="publisherBeanId" property="yearId" param="year_id" />
<title><%=authorizationPageBeanId.getLocalization(application).getString("title_select_small_image")%></title>
<script language="JavaScript">
        <!--
        function setData(){
        parent.postsoftform.imagename.value = '<%= publisherBeanId.getImgname() %>'  ;
        parent.postsoftform.image_id.value =  '<%= publisherBeanId.getImageId() %>'  ;
        parent.dwindow('SelectImage.jsp'); 
        return true ;
        }

		function setEmpty(){
        top.postsoftform.imagename.value = ''  ;
        top.postsoftform.image_id.value =  -1  ;
        top.dwindow('SelectImage.jsp'); 
        return true ;
        }

        function setClose(){
        parent.dwindow('SelectImage.jsp'); 
        return true ;
        }

        function changeImage(){
		document.forms["selectImage1"].submit();
        return true ;
        }



        //-->
</script>
</HEAD><BODY>
<form method="post" name="selectImage1"   ACTION="SelectImage.jsp"  >
<TABLE>
<TR><TD colspan="3" ><%=authorizationPageBeanId.getLocalization(application).getString("title_select_small_image")%></TD></TR>
<TR><TD colspan="3" ><%=publisherBeanId.getSelectSmallImages()%></TD></TR>
<TR><TD><input type="submit" name="Submit" value="<%= authorizationPageBeanId.getLocalization(application).getString("apply") %>"  onclick="return setData()"  ></TD><TD><input type="button" value="<%= authorizationPageBeanId.getLocalization(application).getString("select_with_out_pic") %>" onClick="return setEmpty()" ></TD></TR>
</TABLE>
</form>
 
 <img  id="smalimage"   height="260" alt="Current image"  src="<%= publisherBeanId.getSelectSmallImageUrl() %>"  >

</body>
</html>
