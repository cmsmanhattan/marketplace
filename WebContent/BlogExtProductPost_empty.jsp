<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
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
<jsp:useBean id="publisherBeanId" scope="session" class="com.cbsinc.cms.PublisherBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="itemDescriptionBeanId" scope="request" class="com.cbsinc.cms.ItemDescriptionBean" />
<%
request.setCharacterEncoding("UTF-8");
%>

<body>
<TABLE cellSpacing="0" cellPadding="0" width="100%"  border="1" rightmargin="0" leftmargin="0" topmargin="0" bordercolor="#ECEFF8" >
<TR>
<TD bgcolor="#ECEFF8" ></TD>
<TD vAlign="top" Align="center" width="1010">
<a class="skipnav" href="#documentContent">Skip to content</a>

<div>



        <div class="top">

        </div>

        <hr size="" class="netscape4" />


        <div class="pathBar">
            <span>
                <span> Управление сайтом </span>
            </span>

        </div>

        <hr size="" class="netscape4" />

    </div>



<table class="columns">

    <tbody>
        <tr>
            <td class="left">
 

<div>
    <div class="portlet">
    <h5><strong>Помощ</strong></h5>
      <div class="body">
        <div class="portletContent odd">
         Это форма для добавления вашего сообщения на форум .
        </div>
        <div class="portletContent even">
	Здесь можно добавлять свои ,
	сообщения на саит в ту область 
	в которой вы нажали кнопку добавить .
        </div>
      </div>
    </div>
</div>






            </td>

            <td class="main">

            <!-- News part -->

	    <h1>Написать сообщение на форум</h1>
<br/>


		<div class="box">
		  <div class="body">
		    <div >


                     <form method="post" name="postsoftform"  ACTION="ProductReviewPost.jsp" onSubmit="return  IsFormOk()"  >
                     <TABLE width="500" >
                     <TR><TD>Заголовок:* </TD><TD> <input  name="softname" size="40"  value="<%= publisherBeanId.getStrSoftName() %>" onBlur="checkEmpty(this.value)" >
                     <input type="hidden"  name="catalog_id"  value="<%= authorizationPageBeanId.getCatalogId() %>"/></TR>
                     <TR><TD>Краткая информация :* </TD> <TD> <textarea name="description" rows="10" cols="140"  ><%= publisherBeanId.getStrSoftDescription() %></textarea></TD></TR>
                     <TR><TD></TD> <TD><input type="submit" name="Submit" value="Сохранить"> <input type="reset" value="Сброс"></TD></TR>
                     </TABLE>
					 <input type="hidden"  name="currency_id" size="20" value="3" >
					 <input type="hidden"  name="softcost" size="20" value="0" >
					 <input type="hidden"  name="bigimage_id" size="20" value="-1" >
					 <input type="hidden"  name="type_id" size="20" value="0" >
					 <input type="hidden"  name="file_id" size="20" value="-1" >
                     <input type="hidden"  name="portlettype_id"  value="3"/>
                     <input type="hidden"  name="parent_id"  value="<%= itemDescriptionBeanId.getProductId() %>"/>
                     </form>

		     </div>
		  </div>
		</div>
	
        <!-- Navigation -->
        <div class="listingBar">
	    <span class="next">
                <a HREF = "#" onClick="javascript:history.back()"  >
				<strong>
				назад
				</strong>
		        </a>
	    </span>
		</div>
		<TABLE border="0" height="200" >
		<TR><TD></TD></TR>
        </TABLE>
    
            </td>

            <td class="right">




            </td>
        </tr>
    </tbody>
</table>


<hr size="" class="netscape4" />

<div class="footer">


<br />

Product Media@Shop  - Internet shop. Copyright 2005 <A HREF="http://www.grabko.com">Grabko Business Solution</A> . Все права защищены.

<hr size="" class="netscape4" />

<strong class="netscape4">
for user netscape
</strong>

</div>
</TD>
<TD bgcolor="#ECEFF8" ></TD>
</TR>
</TABLE>
</body>
</html>