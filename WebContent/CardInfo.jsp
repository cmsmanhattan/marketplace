<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page errorPage="error.jsp" %>
<%@ page import="java.util.LinkedList,java.util.Iterator"%>
<%
  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
  request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FDIS Center Business Solutions Payment Gateway</title>
<LINK id="style2" rel="stylesheet" type="text/css" href="xsl/shops.online-spb.com/style2.css">
<SCRIPT type="text/javascript" src="xsl/shops.online-spb.com/common.js"></SCRIPT>
</head>
<body>
<TABLE cellSpacing="0" cellPadding="0" width="100%" border="1" rightmargin="0" leftmargin="0" topmargin="0" bordercolor="#ECEFF8">
<TR>
<TD bgcolor="#ECEFF8"></TD><TD vAlign="top" Align="center" width="1030"><a class="skipnav" href="#documentContent">Skip to content</a>
<div>
<div class="top">
<TABLE cellSpacing="0" cellPadding="10" height="120" width="100%" class="bacgraundTop" border="0" rightmargin="0" leftmargin="0" topmargin="0">
<TBODY>
<TR>
<TD vAlign="top" Align="left" width="20%"></TD><TD vAlign="center" Align="right" width="80%">
<form name="searchform" action="Productlist.jsp">
<br>
<input id="search_value" name="search_value" type="text" size="20" alt="<%= authorizationPageBeanId.getLocalization(application).getString("find_good_by_name") %>" title="<%= authorizationPageBeanId.getLocalization(application).getString("find_good_by_name") %>" tabindex="30001" value=""><input class="searchButton" type="submit" size="20" value="<%= authorizationPageBeanId.getLocalization(application).getString("Search") %>" tabindex="30002">
</form>
</TD>
</TR>
</TBODY>
</TABLE>
</div>
<hr size="" class="netscape4">
<div class="tabs">
<A href="Productlist.jsp?catalog_id=-2" class="plain"><font size="2"><%=authorizationPageBeanId.getLocalization(application).getString("main_page")%></font></A><A href="Productlist.jsp?catalog_id=-10" class="plain"><font size="2"><%=authorizationPageBeanId.getLocalization(application).getString("populyar_page")%></font></A><a href="ProductInfo.jsp?page=about" class="plain"><font size="2"><%=authorizationPageBeanId.getLocalization(application).getString("about_company")%></font></a><A href="Productlist.jsp?action=logoff" class="plain"><font size="2"><%=authorizationPageBeanId.getLocalization(application).getString("exit")%></font></A>
</div>
<div class="personalBar">
<a href="Authorization.jsp"><img src="images/user.gif" alt="Link icon" title="Link icon" height="15" width="10" border="0">
					<%=authorizationPageBeanId.getLocalization(application).getString("login")%>: <%=authorizationPageBeanId.getStrLogin()%></a>
</div>
<hr size="" class="netscape4">
</div>
<table class="columns">
<tbody>
<tr>
<td class="left">
<TABLE style="height: 20px" cellSpacing="4" cellPadding="0" width="159" class="bacgraundBoxTitle">
<TBODY>
<TR>
<TD vAlign="center"><FONT color="white"><B><%=authorizationPageBeanId.getLocalization(application).getString("enter_on_site")%></B></FONT></TD>
</TR>
</TBODY>
</TABLE>
<div class="box">
<div class="body">
<div class="odd">
<form action="Authorization.jsp" method="post">
<strong><%=authorizationPageBeanId.getLocalization(application).getString("username")%></strong>
<br>
<INPUT title="<%= authorizationPageBeanId.getLocalization(application).getString("username") %>" tabindex="10001" SIZE="16" AUTOCOMPLETE="off" TYPE="TEXT" NAME="Login" value="newuser">
<br>
<strong><%=authorizationPageBeanId.getLocalization(application).getString("password")%></strong>
<br>
<INPUT title="<%= authorizationPageBeanId.getLocalization(application).getString("password") %>" tabindex="10002" SIZE="16" AUTOCOMPLETE="off" TYPE="PASSWORD" NAME="Passwd1">
<br>
<br>
<input type="submit" name="submit" value="<%= authorizationPageBeanId.getLocalization(application).getString("login") %>" tabindex="10003" class="context searchButton">
</form>
</div>
<div class="even">
<a href=""><img src="xsl/shops.online-spb.com/images/linkTransparent.gif">
                   <%=authorizationPageBeanId.getLocalization(application).getString("send_password_by_email")%>
                </a>
</div>
</div>
</div>
<div>
<TABLE style="height: 20px" cellSpacing="4" cellPadding="0" width="159" class="bacgraundBoxTitle">
<TBODY>
<TR>
<TD vAlign="center"><FONT color="white"><B>Independent Sales</B></FONT></TD>
</TR>
</TBODY>
</TABLE>
<div class="box">
<div class="body">
<div class="even" align="left">
<IMG border="0" height="20" name="Nindex_14_03" src="images/user.gif" width="20">
		 We are Independent Sales agent of First Data officially . Our ID #: 39084 .
	    </div>
</div>
</div>
</div>
</td><td class="main">
<br>
<div class="box">
<div class="body">
<div class="content">
<p>

 
<h1>
<font size="3"><b> FDIS CBS Payment Gateway </b></font><font size="2"></font>
</h1>
<br>
<center>
<form action="https://www.eprocessingnetwork.com/cgi-bin/tdbe/transact.pl" method="post">
<table    border="0" cellspacing="0" cellpadding="5" >
					<tr>
						<td colspan="2">&nbsp;<br/><font color="red"><%=request.getAttribute("message") == null?"":request.getAttribute("message")%></font></td>
                    </tr>
                    <tr>
						<td>CardNo:</td>
						<td width="400" align="center">
						<input type="text" name="CardNo" value="4111111111111111"/>
						</td>
					</tr>
					<tr>
						<td>ExpMonth:</td>
						<td width="400" align="center">
						<input type="text" name="ExpMonth" value="12"/>
						</td>
					</tr>
					<tr>
						<td>ExpYear:</td>
						<td width="400" align="center">
						<input type="text" name="ExpYear" value="09"/>
						</td>
					</tr>
					<tr>
						<td>Total:</td>
						<td width="400" align="center">
						<input type="text" name="Total" value="12.34"/>
						</td>
					</tr>
					<tr>
						<td>Address:</td>
						<td width="400" align="center">
						<input type="text" name="Address" value="123 Fake St."/>
						</td>
					</tr>
					<tr>
						<td>Zip:</td>
						<td width="400" align="center">
						<input type="text" name="Zip" value="12345"/>
						</td>
					</tr>					
					<tr>
						<td>EMail:</td>
						<td width="400" align="center">
						<input type="text" name="EMail" value="customer@email.com"/>
						</td>
					</tr>
					<tr>
						<td>CVV2Type:</td>
						<td width="400" align="center">
						<input type="text" name="CVV2Type" value="1"/>
						</td>
					</tr>
					<tr>
						<td>CVV2:</td>
						<td width="400" align="center">
						<input type="text" name="CVV2Type" value="123"/>
						</td>
					</tr>
					<tr>
						<td height="500"  colspan="2">&nbsp;</td>
					</tr>

				</table>
</form>
</center>
</div>
</div>
</div>
<div class="listingBar">
<span class="next"><a HREF="#" onClick="javascript:history.back()"><strong>	
		<%=authorizationPageBeanId.getLocalization(application).getString("back")%>
		</strong></a></span>
</div>
</td><td class="right">
<TABLE style="height: 20px" cellSpacing="4" cellPadding="0" width="159" class="bacgraundBoxTitle">
<TBODY>
<TR>
<TD vAlign="center"><FONT color="white"><B>Protection of deal</B></FONT></TD>
</TR>
</TBODY>
</TABLE>
<div class="box">
<div class="body">
<div class="even" align="left">
<TABLE >
<TBODY>
<TR>
<TD >Phone</TD> <TD >+1(516)7770945</TD>
</TR>
</TBODY>
</TABLE>
</div>
</div>
</div>
</td>
</tr>
</tbody>
</table>
<hr size="" class="netscape4">
<div class="footer">
<br>

 <%=authorizationPageBeanId.getLocalization(application).getString("all_rights_reserved")%>

<hr size="" class="netscape4">
<strong class="netscape4">
for user netscape </strong>
</div>
</TD><TD bgcolor="#ECEFF8"></TD>
</TR>
</TABLE>
</body>
</html>
