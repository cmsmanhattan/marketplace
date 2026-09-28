<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page errorPage="error.jsp" %>
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<%
  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
  request.setCharacterEncoding("UTF-8");
%>
<html>
<head>
<title><%=authorizationPageBeanId.getLocalization(application).getString("relation_with_tech_support")%></title>
</head>
<body>
<br/><%=authorizationPageBeanId.getLocalization(application).getString("relation_with_tech_support")%><br/><font color="red"><%=request.getAttribute("message") == null?"":request.getAttribute("message")%></font>
<form action="Support.jsp"  method="post" >
	<table width="670"    border="0" cellspacing="0" cellpadding="5" bgcolor="#DFE3EF">
		<tr>
			<td><%=authorizationPageBeanId.getLocalization(application).getString("your_email")%></td><td><input type="text" size="70" name="email" value="<%= request.getAttribute("email") == null?"":request.getAttribute("email") %>"  />  </td>
        </tr>
		<tr>
			<td><%=authorizationPageBeanId.getLocalization(application).getString("person")%></td><td><input type="text" size="70" name="person" value="<%= request.getAttribute("person") == null?"":request.getAttribute("person") %>"  />  </td>
        </tr>
		<tr>
			<td><%=authorizationPageBeanId.getLocalization(application).getString("problem")%></td><td><textarea type="text" cols="70" rows="20" name="problem" ><%=request.getAttribute("problem") == null?"":request.getAttribute("problem")%></textarea> </td>
        </tr>
        <tr>
			<td colspan="2"><input type="submit"  name="submit" value="<%= authorizationPageBeanId.getLocalization(application).getString("do_send") %>" /></td>
        </tr>
    </table>                
</form>
</body>
</html>