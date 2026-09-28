<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en"
      lang="en">

<head>
     <title>GBS Portal</title>
     <style type="text/css" media="screen"> @import url(style2.css);</style>

<script language="JavaScript">
<!--

function select_Image(){
self.name  = 'UploadForm' ;
var url = 'SelectImage.jsp' ;
window.open(url ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100');
return false ;
}

function new_Image(){
self.name  = 'UploadForm' ;
var url = 'Image.html' ;
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
if(isEmpty( document.catalog_add.name.value)){ alert('Field name did not fill'); return false; }
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
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="catalogAddBeanId" scope="session" class="com.cbsinc.cms.CatalogAddBean" />
<%
request.setCharacterEncoding("UTF-8");
//catalogAddBeanId.setParent_id(catalog_listBean.getParent_id());
//catalogAddBeanId.setSite_id(authorizationPageBeanId.getSite_id());
if( request.getParameter("name") != null)
{
catalogAddBeanId.setName(  request.getParameter("name"));
}

if(request.getMethod().toUpperCase().compareTo("POST") == 0)
{
catalogAddBeanId.addCatalog(authorizationPageBeanId);
response.sendRedirect("catalog_list.jsp" );
}
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
                <span> <%=authorizationPageBeanId.getLocalization(application).getString("control_of_site")%> </span>
            </span>

        </div>
        <hr size="" class="netscape4" />
    </div>

<table class="columns">

    <tbody>
        <tr>
            <td class="left">
    <div class="box">

        <h5><%=authorizationPageBeanId.getLocalization(application).getString("enter_on_site")%></h5>

        <div class="body">

            <div class="content odd">
                <form action="Authorization.jsp"   method="post">

                    <strong><%=authorizationPageBeanId.getLocalization(application).getString("username")%></strong> <br />

                     <INPUT  title="<%= authorizationPageBeanId.getLocalization(application).getString("username") %>" tabindex="10001" SIZE="12" AUTOCOMPLETE="off" TYPE="TEXT" NAME="Login" >
        	     </INPUT>

                    <br />

                    <strong><%=authorizationPageBeanId.getLocalization(application).getString("password")%></strong>
		    <br />
		    <INPUT title="<%= authorizationPageBeanId.getLocalization(application).getString("password") %>" tabindex="10002"  SIZE="12" AUTOCOMPLETE="off" TYPE="PASSWORD" NAME="Passwd1" ></INPUT>
                    <br />
                    <br />

                    <input class="context searchButton"
                           type="submit" name="submit"
                           value="Log in" tabindex="10003" />
                </form>
            </div>

            <div class="content even">
                <a href="">
                   <img src="xsl/www.gvidons.com/img/user.gif" alt="Link icon" title="Link icon" height="15" width="10" border="0" />
                   <%=authorizationPageBeanId.getLocalization(application).getString("send_password_by_email")%>
                </a>
            </div>
        </div>
    </div>




            </td>

            <td class="main">

            <!-- News part -->

	    <h1>Добавление в раздела</h1>
<br/>


		<div class="box">
		  <div class="body">
		    <div >

                     <form method="post"   name="catalog_add"  ACTION="catalog_add.jsp" >
                     <TABLE>
                     <TR><TD>Раздел:* </TD><TD> <input  name="name" size="80"   onBlur="checkEmpty(this.value)" >
                     <TR><TD></TD> <TD><input type="submit" name="Submit" value="Сохранить"> <input type="reset" value="Сброс"></TD></TR>
                     </TABLE>
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
</TD>
<TD bgcolor="#ECEFF8" ></TD>
</TR>
</TABLE>
</body>
</html>