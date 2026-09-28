<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<%@ page import="com.cbsinc.cms.controllers.CurrencyEnum" %>
<jsp:useBean id="publisherBeanId" scope="session" class="com.cbsinc.cms.PublisherBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<%
  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
  request.setCharacterEncoding("UTF-8");
%>


if( request.getParameter("creteria1_id") !=null ) publisherBeanId.setCreteria1Id( request.getParameter("creteria1_id"));
if( request.getParameter("creteria2_id") !=null ) publisherBeanId.setCreteria2Id( request.getParameter("creteria2_id"));
if( request.getParameter("creteria3_id") !=null ) publisherBeanId.setCreteria3Id( request.getParameter("creteria3_id"));
if( request.getParameter("creteria4_id") !=null ) publisherBeanId.setCreteria4Id( request.getParameter("creteria4_id"));
if( request.getParameter("creteria5_id") !=null ) publisherBeanId.setCreteria5Id( request.getParameter("creteria5_id"));
if( request.getParameter("creteria6_id") !=null ) publisherBeanId.setCreteria6Id( request.getParameter("creteria6_id"));
if( request.getParameter("creteria7_id") !=null ) publisherBeanId.setCreteria7Id( request.getParameter("creteria7_id"));
if( request.getParameter("creteria8_id") !=null ) publisherBeanId.setCreteria8Id( request.getParameter("creteria8_id"));
if( request.getParameter("creteria9_id") !=null ) publisherBeanId.setCreteria9Id( request.getParameter("creteria9_id"));
if( request.getParameter("creteria10_id") !=null ) publisherBeanId.setCreteria10Id( request.getParameter("creteria10_id"));


String   softname  = request.getParameter("softname");
if ( softname != null ) {  publisherBeanId.setStrSoftName( softname ); }


String catalogId  = request.getParameter("catalog_id") ;
if(catalogId  != null){ authorizationPageBeanId.setCatalogId( catalogId) ; }


String softcost  = request.getParameter("softcost") ;
if(softcost  != null){ publisherBeanId.setStrSoftCost( softcost) ; }

String currencyId  = request.getParameter("currency_id") ;
if(currencyId  != null){ publisherBeanId.setStrCurrency( currencyId) ; }

String   description  = request.getParameter("description");
if ( description != null ) {  publisherBeanId.setStrSoftDescription( description ); }


String   fulldescription  = request.getParameter("fulldescription");
if ( fulldescription != null ) {  publisherBeanId.setProductFulldescription( fulldescription ); }


String   imagename  = request.getParameter("imagename");
if ( imagename != null ) {  publisherBeanId.setImgname( imagename ); }


String   imageId  = request.getParameter("image_id");
if ( imageId != null ) {  publisherBeanId.setImageId( imageId ); }

if ( request.getParameter("portlettype_id") != null ) {  publisherBeanId.setPortlettypeId( request.getParameter("portlettype_id")); }

String   filename  = request.getParameter("filename");
if ( filename != null ) {  publisherBeanId.setSample( filename ); }
else{ publisherBeanId.setSample(""); }
filename = null ;



String   bigimagename  = request.getParameter("bigimagename");
if ( bigimagename != null ) {  publisherBeanId.setBigimgname( bigimagename ); }


String   bigimageId  = request.getParameter("bigimage_id");
if ( bigimageId != null ) {  publisherBeanId.setBigimageId( bigimageId ); }


if( request.getParameter("salelogic_id") !=null ) publisherBeanId.setPrognameId( request.getParameter("salelogic_id"));

if( authorizationPageBeanId.getIntUserID() == 0 ){
authorizationPageBeanId.setStrMessage("User not login or loguot by timeout , make login in site now") ;
response.sendRedirect("Authorization.jsp" );
}
else publisherBeanId.setUserId("" + authorizationPageBeanId.getIntUserID()) ;

if( authorizationPageBeanId.getRoleId() != 2 ){
authorizationPageBeanId.setStrMessage("You don't have access to add position , send mail to grabko@mail.ru for access") ;
response.sendRedirect("Authorization.jsp" );
}

if(request.getMethod().toUpperCase().compareTo("POST") == 0)
{
/////publisherBeanId.setSite_id(authorizationPageBeanId.getSite_id());
/////if(publisherBeanId.getSoft_id().compareTo("-1")==0) publisherBeanId.saveDescSoft();
/////else publisherBeanId.updateDescSoft();
/////String strSoft_id = publisherBeanId.getCountActiveRow() ;
/////if(strSoft_id.length() > 1) strSoft_id = strSoft_id.substring(0,strSoft_id.length() - 1) + "0" ;
/////else strSoft_id = "0" ;
response.sendRedirect("Productlist.jsp?offset=" + 0 + "&catalog_id=" + authorizationPageBeanId.getCatalogId()  );
}
%>


<html>
<head>
     <title>GBS Portal</title>
     <style type="text/css" media="screen"> @import url(style2.css);</style>

<script language="JavaScript">
<!--
function select_file(){
self.name  = 'UploadForm' ;
var url = 'SelectFile.jsp' ;
window.open(url ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100');
return false ;
//return true ;
}

function new_file(){
self.name  = 'UploadForm' ;
var url = 'File1.html' ;
window.open(url ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100');
return false ;
//return true ;
}

function select_Image(){
self.name  = 'UploadForm' ;
var url = 'SelectImage.jsp' ;
window.open(url ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100');
return false ;
}

function select_BigImage(){
self.name  = 'UploadForm' ;
var url = 'SelectBigImage.jsp' ;
window.open(url ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100');
return false ;
}


function new_Image(){
self.name  = 'UploadForm' ;
var url = 'Image.html' ;
window.open(url ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100');
return false ;
}


function new_BigImage(){
self.name  = 'UploadForm' ;
var url = 'big_images.html' ;
window.open(url ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100');
return false ;
}


function isNumber(tmp){
var IntField = '0123456789.' ;
for(var i=0 ; i< tmp.length ; i++ ){
if( IntField.indexOf(tmp.charAt(i)) == -1 ) return true ;
}
return false ;
}

function isEmpty(tmp){
if( tmp.length == 0 ) return true ;
return false ;
}

function isDate(tmp){
var IntField = '0123456789' ;
//1957-06-13
if( IntField.indexOf(tmp.charAt(0)) != -1 )
if( IntField.indexOf(tmp.charAt(1)) != -1 )
if( IntField.indexOf(tmp.charAt(2)) != -1 )
if( IntField.indexOf(tmp.charAt(3)) != -1 )
if( IntField.indexOf(tmp.charAt(5)) != -1 )
if( IntField.indexOf(tmp.charAt(6)) != -1 )
if( IntField.indexOf(tmp.charAt(8)) != -1 )
if( IntField.indexOf(tmp.charAt(9)) != -1 )
if( tmp.indexOf('-') == 4 )
if( tmp.indexOf('-',5) == 7 ) return false ;
return true ;
}

function isURL(tmp){
if( tmp.indexOf('http://') != -1 ) return false ;
if( tmp.indexOf('ftp://') != -1 ) return false ;
if( tmp.indexOf('mailto:') != -1 ) return false ;
return true ;
}


function IsFormOk(){
if(isEmpty( document.postsoftform.softname.value)){ alert('Field name did not fill'); return false; }
//if(isNumber(document.postsoftform.image_id.value) ){ alert('This is not number'); return false; }
if(isEmpty( document.postsoftform.imagename.value)){ alert('Field image did not fill'); return false; }
if(isEmpty( document.postsoftform.description.value)){ alert('Field description did not fill'); return false; }
return true ;
}

function checkNumber(tmp){
if(isNumber(tmp) ){ alert('This is not number'); return false; }
return true ;
}

function checkEmpty(tmp){
if(isEmpty(tmp) ){ alert('Filed is empty'); return false; }
return true ;
}

//-->
</script>


</head>
<body>

<a class="skipnav" href="#documentContent">Skip to content</a>

<div>



        <div class="top">

        </div>

        <hr size="" class="netscape4" />


        <div class="pathBar">
            <span>
                <span> <%=authorizationPageBeanId.getLocalization(application).getString("control_of_site")%> </span>
            </span>

        </div>

        <hr size="" class="netscape4" />

    </div>



<table class="columns">

    <tbody>
        <tr>
            <td class="left">
            </td>

            <td class="main">

            <!-- News part -->

	    <h1><%=authorizationPageBeanId.getLocalization(application).getString("title_common_of_form")%></h1>
<br/>


		<div class="box">
		  <div class="body">
		    <div >


                     <form method="post" name="postsoftform"  ACTION="ProductPost.jsp" onSubmit="return  IsFormOk()"  >
                     <TABLE>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("title_form")%>:* </TD><TD> <input  name="softname" size="20"  value="<%= publisherBeanId.getStrSoftName() %>" onBlur="checkEmpty(this.value)" >
                     <%=authorizationPageBeanId.getLocalization(application).getString("selected_section")%>:*<%=publisherBeanId.getComboBoxAutoSubmit("catalog_id", authorizationPageBeanId.getCatalogId() ,"select catalog_id , lable   from catalog   where  active = true and site_id = " + authorizationPageBeanId.getSiteId() + " and parent_id = " + authorizationPageBeanId.getCatalogParentId() )%>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("title_form_on_foreign_lang")%>:* </TD> <TD> <input  name="softname2" size="40"  value="<%= publisherBeanId.getStrSoftName2() %>"  >  </TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("upload_small_image")%>:* </TD> <TD><input  name="imagename"  disabled="disabled" size="20" value="<%= publisherBeanId.getImgname() %>" ><input type="button" name="newimage" value="<%= authorizationPageBeanId.getLocalization(application).getString("new_small_image") %>"   onclick="dwindow('NewSmallImage.jsp'); return false;"  ><input type="button" name="selectimage" value="<%= authorizationPageBeanId.getLocalization(application).getString("select_small_image") %>" onclick="dwindow('SelectImage.jsp'); return false;" ><input type="hidden"  name="image_id" size="20" value="<%= publisherBeanId.getImageId() %>" ></TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("cost")%>:* </TD> <TD><input  name="softcost" size="20" value="<%= publisherBeanId.getStrSoftCost() %>" onBlur="checkNumber(this.value)"  ><%=publisherBeanId.getComboBox("currency_id", CurrencyEnum.USD.getStrId() ,"SELECT currency_id , currency_lable FROM currency  WHERE active = true")%></TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("short_info")%> :* </TD> <TD> <textarea name="description" rows="10" cols="70"  ><%=publisherBeanId.getStrSoftDescription()%></textarea></TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("upload_big_image")%>:* </TD> <TD><input  name="bigimagename" disabled="disabled" size="20" value="<%= publisherBeanId.getBigimgname() %>" ><input type="button" name="newbig_image" value="<%= authorizationPageBeanId.getLocalization(application).getString("new_big_image") %>"  onclick="dwindow('NewBigImage.jsp'); return false;" ><input type="button" name="selectbig_image" value="<%= authorizationPageBeanId.getLocalization(application).getString("select_big_image") %>" image" onclick="dwindow('SelectBigImage.jsp'); return false;" ><input type="hidden"  name="bigimage_id" size="20" value="<%= publisherBeanId.getBigimageId() %>" ></TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("full_information")%> :* </TD> <TD> <textarea name="fulldescription" rows="10" cols="70"  ><%=publisherBeanId.getProductFulldescription()%></textarea></TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("attach_file")%>:* </TD> <TD><input  name="filename" disabled="disabled"  size="20" value="<%= publisherBeanId.getFilename() %>" ><input type="button" name="newfile" value="<%= authorizationPageBeanId.getLocalization(application).getString("new_file") %>" onclick="dwindow('NewFile.jsp')" ><input type="button" name="selectfile" value="<%= authorizationPageBeanId.getLocalization(application).getString("select_file") %>" onclick="dwindow('SelectFile.jsp')" ><input type="hidden"  name="file_id" size="20" value="<%= publisherBeanId.getFileId() %>" ></TD></TR>
                     <TR><TD></TD> <TD><input type="submit" name="Submit" value="<%= authorizationPageBeanId.getLocalization(application).getString("save") %>"> <input type="reset" value="<%= authorizationPageBeanId.getLocalization(application).getString("clear") %>"></TD></TR>
                     </TABLE>
                     <input type="hidden"  name="portlettype_id"  value="0"/>
                     </form>

		     </div>
		  </div>
		</div>


        <!-- Navigation -->
        <div class="listingBar">
	    <span class="next">
                <a HREF = "#" onClick="javascript:history.back()"  >
		<strong>
		<%=authorizationPageBeanId.getLocalization(application).getString("back")%>
		</strong>
	        </a>
	    </span>
	</div>


            </td>

            <td class="right">




            </td>
        </tr>
    </tbody>
</table>


<hr size="" class="netscape4" />

<div class="footer">


<br />

 <%=authorizationPageBeanId.getLocalization(application).getString("all_rights_reserved")%>

<hr size="" class="netscape4" />

<strong class="netscape4">
for user netscape
</strong>

</div>

</body>
</html>