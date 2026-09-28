<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page errorPage="error.jsp" %>
<%
  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
  request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<html>
<head>
<META HTTP-EQUIV="no-cache">
<title>Login to mail server</title>
<SCRIPT language="JavaScript">
<!--

function webmail() 
{ 
//alert('ok');
document.getElementById('webmail').submit();
}
// -->
</SCRIPT>

</head>
<body>
<DIV style="display:none;"  >
<form  id="webmail"  name="webmail" method="post"  action="webmail/login.jsp"   >
	<input type="text"  name="user" value="<%= authorizationPageBeanId.getStrLogin() %>"  ></input>
    <input type="text"  name="pass" value="<%= authorizationPageBeanId.getEmailPassword()  %>"  ></input>
    
</form >
</DIV>
<SCRIPT language="JavaScript">top.webmail();</SCRIPT>
</body>
</html>