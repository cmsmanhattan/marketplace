<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page errorPage="error.jsp"%>
<%@ page import="com.cbsinc.cms.services.whois.DomainState"%>
<%@ page import="java.util.LinkedList,java.util.Iterator"%>
<%
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setDateHeader("Expires", 0); //prevents caching at the proxy server
request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="domainList" scope="request"
	class="java.util.LinkedList" />
<jsp:useBean id="authorizationPageBeanId" scope="session"
	class="com.cbsinc.cms.AuthorizationPageBean" />
<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=authorizationPageBeanId.getLocalization(application).getString("find_domain")%></title>
<style type="text/css" media="screen">
@import url(style2.css);
</style>
<SCRIPT type="text/javascript" src="xsl/shops.online-spb.com/common.js"></SCRIPT>
</head>
<body>
	<TABLE cellSpacing="0" cellPadding="0" width="100%" border="0"
		rightmargin="0" leftmargin="0" topmargin="0" bordercolor="#ECEFF8"
		height="100%">
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
													tabindex="10001" SIZE="12" AUTOCOMPLETE="off" TYPE="TEXT"
													NAME="Login" value="newuser"> <br> <strong><%=authorizationPageBeanId.getLocalization(application).getString("password")%></strong>
												<br> <INPUT
													title="<%=authorizationPageBeanId.getLocalization(application).getString("password")%>"
													tabindex="10002" SIZE="12" AUTOCOMPLETE="off"
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
							  <h1>Find available domains</h1>
								<br/>
							    <DIV style="background-image:url('images/f.jpg');height:20px; TEXT-ALIGN: left"  >
										<font color='white' size='2' > &nbsp;&raquo; Search domain form </font>
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
														<font size="3"><b> <%=authorizationPageBeanId.getLocalization(application).getString("find_domain")%>
															</b></font><font size="2"></font></td>
													</tr>
													<tr>
														<td colspan="2">&nbsp;<br />
														<font color="red"><%=request.getAttribute("message") == null ? "" : request.getAttribute("message")%></font></td>
													</tr>
													<tr>
														<td width="400" align="center">
															<form action="Domain.jsp" method="post">
																<input type="text" size="45" name="domain"
																	value="<%=request.getAttribute("domain") == null ? "" : request.getAttribute("domain")%>" />
																<input type="submit" name="submit"
																	class="context searchButton"
																	value="<%=authorizationPageBeanId.getLocalization(application).getString("do_find")%>" />
															</form>
														</td>
													</tr>
													<%
													java.util.Iterator iterator = domainList.iterator();
													%>

													<%
													while (iterator.hasNext()) {
													%>
													<%
													DomainState domainState = (DomainState) iterator.next();
													%>
													<tr>
														<td width="500" align="left"><img
															SRC="images/home.png" border="0" alt="Domain"> <%=domainState.getDomain() + " " + domainState.getStatus()%>
														</td>
														<td>
															<%
															if (domainState.isFree()) {
															%> <%
 if (authorizationPageBeanId.getRoleId() != 0) {
 %>
															<form action="Domain.jsp" method="post">
																<input type="hidden" name="regdomain"
																	value="<%=request.getAttribute("domain") == null ? "" : request.getAttribute("domain")%>" /><input
																	type="submit" name="submit"
																	value="<%=authorizationPageBeanId.getLocalization(application).getString("send_application")%>" />
															</form> <%
 } else {
 %> <font size="3" color="red"><%=authorizationPageBeanId.getLocalization(application).getString("user_not_autorization")%></font>
															<form action="Domain.jsp" method="post">
																<input type="hidden" name="regdomain"
																	value="<%=request.getAttribute("domain") == null ? "" : request.getAttribute("domain")%>" /><input
																	type="submit" disabled="disabled" name="submit"
																	value="<%=authorizationPageBeanId.getLocalization(application).getString("send_application")%>" />
															</form> <%
 }
 %> <%
 }
 %>
														</td>
													</tr>
													<tr>
														<td colspan="2">&nbsp;</td>
													</tr>
													<%
													}
													%>

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
											<TD vAlign="center"><FONT color="white"><B><%=authorizationPageBeanId.getLocalization(application).getString("price_of_zone")%></B></FONT></TD>
										</TR>
									</TBODY>
								</TABLE>
								<div class="box">
									<div class="body">
										<div class="even" align="left">
											<TABLE>
												<TBODY>
													<TR>
														<TD>
															<!-- <%=authorizationPageBeanId.getLocalization(application).getString("zone_of_domain")%>  -->
															all domain zones
														</TD>
														<TD>
															<!-- <%=authorizationPageBeanId.getLocalization(application).getString("rub_and_year")%>  -->
														</TD>
													</TR>
													<TR>
														<TD>.com</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.net</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.org</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.info</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.biz</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.us</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.name</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.eu</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.de</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.at</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.co.uk</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.me.uk</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>org.uk</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.me</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.tel</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.mobi</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.asia</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.cn</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.sc</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.ag</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.bz</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.hn</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.mn</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.lc</TD>
														<TD></TD>
													</TR>
													<TR>
														<TD>.vc</TD>
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
				</table></TD>
			<TD bgcolor="#ECEFF8" style="border: 1px solid #ECEFF8"></TD>
		</TR>

		<TR>
			<TD bgcolor="#ECEFF8" style="border: 1px solid #ECEFF8"></TD>


			<TD colspan="1" width="1030" vAlign="bottom" Align="center"
				cellSpacing="0" cellPadding="10">
				<div class="footer" style="height: 60px">
					<font color="black">Internet shop . Copyright 2010 <A
						HREF="http://www.cmsmanhattan.com"><font color="black">
								FDIS Center Business Solutions Inc </font></A>. All rights reserved
					</font>
				</div>
			</TD>

			<TD bgcolor="#ECEFF8" style="border: 1px solid #ECEFF8"></TD>

		</TR>


	</TABLE>
</body>
</html>
