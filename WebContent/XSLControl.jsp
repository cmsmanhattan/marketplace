<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<%
  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
  request.setCharacterEncoding("UTF-8");
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title><%=authorizationPageBeanId.getLocalization(application).getString("title_upload_new_design_on_site")%></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="JavaScript">
        <!--
        function setClose()
        {
        document.location.href='PostManager.jsp';
        return true ;
        }
        //-->
	</script>
  </head>
  
  <body>
    <%=authorizationPageBeanId.getLocalization(application).getString("title_upload_new_design_on_site")%><br>
  <form  name="uploadfile"  action="/uploadservletxsl" method="post" enctype="multipart/form-data">
  <input type="file" name="file"/><br>
  <input type="submit" value = "<%= authorizationPageBeanId.getLocalization(application).getString("save") %>"/> <input type="button" value="<%= authorizationPageBeanId.getLocalization(application).getString("close_window") %>" onClick="return setClose()" ><br>
  </form>
  
  </body>
</html>
