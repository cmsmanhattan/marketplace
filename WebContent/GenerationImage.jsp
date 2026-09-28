<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<%
  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
  request.setCharacterEncoding("UTF-8");
%>
<HTML>
  <HEAD>
   <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <TITLE>Image capture text </TITLE>
    <script language="JavaScript">
        <!--
        function setSendValue()
        {
        self.close();
        return true ;
        }
        //-->
	</script>
  </HEAD>
  <BODY>
  <br/> 
  <form action="#" >
  <TABLE>
    <TR><TD colspan="2" >Enter text from capture </TD></TR>
    <TR><TD><img alt="Image created by generator" src="/gennumberservlet"  /></TD> <TD><input type="text" name="gen_number"></TD></TR>
    <TR><TD colspan="2" ><input type="button" name="button" value="ОК"  onclick="return setSendValue()" > </TD></TR>
  </TABLE>
  </form>
  <form>
</BODY>

</HTML>
