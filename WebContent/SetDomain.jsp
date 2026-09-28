<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page errorPage="error.jsp"%>
<%
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setDateHeader("Expires", 0); //prevents caching at the proxy server
request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="authorizationPageBeanId" scope="session"
	class="com.cbsinc.cms.AuthorizationPageBean" />
<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Set up a hostname for your internet shop</title>
<style type="text/css" media="screen">
@import url(style2.css);
</style>
</head>
<body>
	<TABLE cellSpacing="0" cellPadding="0" width="100%" border="0"
		rightmargin="0" leftmargin="0" topmargin="0" bordercolor="#ECEFF8">
		<TR>
			<TD bgcolor="#ECEFF8" style="border: 1px solid #ECEFF8"></TD>
			<TD vAlign="top" Align="center" width="1030"
				style="border: 0px solid #ECEFF8"><a class="skipnav"
				href="#documentContent">Skip to content</a>
				<div>
					<div class="top"></div>
					<hr size="" class="netscape4">
					
					<div class="pathBar">
						<span> <span> <%=authorizationPageBeanId.getLocalization(application).getString("control_of_site")%>
						</span>
						</span>

					</div>

					<hr size="" class="netscape4" />

					<div class="personalBar">
						<a href="Authorization.jsp"><img src="images/user.gif"
							alt="Link icon" title="Link icon" height="15" width="10"
							border="0"> <%=authorizationPageBeanId.getLocalization(application).getString("login")%>:
							<%=authorizationPageBeanId.getStrLogin()%></a>
					</div>
					<hr size="" class="netscape4">
				</div>
				<table class="columns">
					<tbody>
						<tr>
							<td class="left">
								<TABLE style="height: 20px" cellSpacing="4" cellPadding="0"
									width="159" class="bacgraundBoxTitle">
									<TBODY>
										<TR>
											<TD vAlign="center"><FONT color="white"><B>Authorization</B></FONT></TD>
										</TR>
									</TBODY>
								</TABLE>
								<div class="box">
									<div class="body">
										<div class="odd">
											<form action="Authorization.jsp" method="post">
												<strong><%=authorizationPageBeanId.getLocalization(application).getString("username")%></strong>
												<br> <INPUT
													title="<%=authorizationPageBeanId.getLocalization(application).getString("username")%>"
													tabindex="10001" SIZE="16" AUTOCOMPLETE="off" TYPE="TEXT"
													NAME="Login" value="newuser"> <br> <strong><%=authorizationPageBeanId.getLocalization(application).getString("password")%></strong>
												<br> <INPUT
													title="<%=authorizationPageBeanId.getLocalization(application).getString("password")%>"
													tabindex="10002" SIZE="16" AUTOCOMPLETE="off"
													TYPE="PASSWORD" NAME="Passwd1"> <br> <br>
												<input type="submit" name="submit"
													value="<%=authorizationPageBeanId.getLocalization(application).getString("login")%>"
													tabindex="10003" class="context searchButton">
											</form>
										</div>
										<div class="even">
											<a href=""><img
												src="xsl/shops.online-spb.com/images/linkTransparent.gif">
												<%=authorizationPageBeanId.getLocalization(application).getString("send_password_by_email")%>
											</a>
										</div>
									</div>
								</div>

							</td>
							<td class="main">
								<h1>Setup your domain for your store</h1>
								<br/>
							    <DIV style="background-image:url('images/f.jpg');height:20px; TEXT-ALIGN: left"  >
										<font color='white' size='2' > &nbsp;&raquo; Domain apply form </font>
								</DIV>
								<div class="box">
									<div class="body">
										<div class="content">
											<p>




												<br>
											<center>
												<table border="0" cellspacing="0" cellpadding="5">
													<tr>
														<td colspan="2">&nbsp;<br />
														<font size="3"><b> Install new domain for your
																	internet site</b></font><font size="2"></font></td>
													</tr>
													<tr>
														<td colspan="2">&nbsp;<br />
														<font color="red"><%=request.getAttribute("message") == null ? "" : request.getAttribute("message")%></font></td>
													</tr>
													<tr>
														<td width="400" align="center">
															<form action="SetDomain.jsp" method="post">
																<input type="text" size="50" name="domain"
																	value="<%=authorizationPageBeanId.getHost()%>" /> <input
																	type="submit" name="submit" value="Save" />
															</form>
														</td>
													</tr>

													<tr>
														<td height="500" colspan="2">&nbsp;</td>
													</tr>

												</table>

											</center>
										</div>
									</div>
								</div>
								<div class="listingBar">
									<span class="next"><a HREF="#"
										onClick="javascript:history.back()"><strong> <%=authorizationPageBeanId.getLocalization(application).getString("back")%>
										</strong></a></span>
								</div></td>
							<td class="right">
								<TABLE style="height: 20px" cellSpacing="4" cellPadding="0"
									width="159" class="bacgraundBoxTitle">
									<TBODY>
										<TR>
											<TD vAlign="center"><FONT color="white"><B>Help</B></FONT></TD>
										</TR>
									</TBODY>
								</TABLE>
								<div class="box">
									<div class="body">
										<div class="even" align="left">
											<TABLE>
												<TBODY>
													<TR>
														<TD>You need make a record "A" in DNS server</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>and link this record with your IP 54.201.41.182
														</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>this way your internet site will become
															accessible from internet,</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>of course if would be write this host name in
															internet browser.</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>If you wish we can help to do that but</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>you have to pay for additional support .</TD>
														<TD></TD>
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
					<br> Internet shop . Copyright 2010 FDIS Center Business
					Solutions Inc .
					<%=authorizationPageBeanId.getLocalization(application).getString("all_rights_reserved")%>
					<hr size="" class="netscape4">
					<strong class="netscape4"> for user netscape </strong>
				</div></TD>
			<TD bgcolor="#ECEFF8" style="border: 1px solid #ECEFF8"></TD>
		</TR>
	</TABLE>
</body>
</html>
