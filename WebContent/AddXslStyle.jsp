<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<%  request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
<title>
xsl
</title>
<script language="JavaScript">
<!--
function new_file(){
self.name  = 'UploadForm' ;
var url = 'xsl.html' ;
window.open(url ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100');
return false ;
//return true ;
}
//-->
</script>
</head>
<jsp:useBean id="xslBeanId" scope="request" class="com.cbsinc.cms.XslBean" />
<%
//xslBeanId.initFields(AuthorizationPageBeanId.getSite_id() );

if( request.getParameter("new_folder") != null) xslBeanId.setXslSubjId(  request.getParameter("new_folder"));

//if( request.getParameter("xsl_style_id") != null) xslBeanId.setXsl_style_id(  request.getParameter("xsl_style_id"));
%>

<BODY>
<P><STRONG><U><FONT color="#000099">This is pages control manager system</FONT></U></STRONG></P>
<BR>You could  easy change design pages on your site .
<BR>To change design page you must upload file of style (XSL File)
<BR>Or You could  select file from me list

<P><STRONG><U><FONT color="#000099">Ctalog Style</FONT></U></STRONG></P>
<form method="post" name="form1"  ACTION="AddXslStyle.jsp"  >
<BR>????????? ????? ??? ??????? ???? ????? ??????? ???? ??????
<input type="input" name="new_folder"  value="" >
<BR>
<input type="submit" name="Submit" value="?????????">
<% xslBeanId.setDirname(xslBeanId.getDirnameBy( xslBeanId.getXslStyleId() )); %>
<%= xslBeanId.getDir( request ,xslBeanId.getDirname()) %>
</form>

</BODY>


</html>