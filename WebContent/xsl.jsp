<%@ page errorPage="error.jsp" %>
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
<%
  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

</head>
<jsp:useBean id="xslBeanId" scope="request" class="com.cbsinc.cms.XslBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<%
xslBeanId.initFields(authorizationPageBeanId.getSiteId() );
if( request.getParameter("xsl_subj_id") != null) xslBeanId.setXslSubjId(  request.getParameter("xsl_subj_id"));
if( request.getParameter("xsl_style_id") != null) xslBeanId.setXslStyleId(  request.getParameter("xsl_style_id"));
%>

<BODY>
<P><STRONG><U><FONT color="#000099">This is pages control manager system</FONT></U></STRONG></P>
<BR>You could  easy change design pages on your site .
<BR>To change design page you must upload file of style (XSL File)
<BR>Or You could  select file from me list

<P><STRONG><U><FONT color="#000099">Ctalog Style</FONT></U></STRONG></P>
<form method="post" name="form1"  ACTION="xsl.jsp"  >
<%=  xslBeanId.getComboBox("xsl_subj_id", "" + xslBeanId.getXslSubjId()  ,"SELECT   public.xsl_subj.xsl_subj_id,   public.xsl_subj.name FROM  public.xsl_subj WHERE   public.xsl_subj.active = true" ) %> <input type="submit" name="Submit" value="OK">
<%=  xslBeanId.getComboBox("xsl_style_id", ""  + xslBeanId.getXslStyleId()  ,"SELECT  public.xsl_style.xsl_style_id, public.xsl_style.name FROM  public.xsl_style WHERE   public.xsl_style.active = true  AND public.xsl_style.xsl_subj_id = "+ xslBeanId.getXslSubjId() ) %> <input type="submit" name="Submit" value="OK">
<input type="button" name="add_file" value="Add File" onClick="return  new_file()">
<% xslBeanId.setDirname(xslBeanId.getDirnameBy( xslBeanId.getXslStyleId() )); %>
<%= xslBeanId.getDir( request ,xslBeanId.getDirname()) %>
</form>

<P><STRONG><U><FONT color="#000099">My Style</FONT></U></STRONG></P>
<%= xslBeanId.getUserDir( request ) %>

</BODY>


</html>