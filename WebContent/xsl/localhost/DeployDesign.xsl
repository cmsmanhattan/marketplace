<?xml version='1.0' encoding='windows-1251' ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0" xmlns:java="http://xml.apache.org/xslt/java"  
	exclude-result-prefixes="java">
	<xsl:output method="html" indent="yes" />
	<xsl:output encoding="UTF-8" />
	<xsl:strip-space elements="*" />

	<xsl:template match="/">
		<xsl:variable name="host" select="string(document/host)" />
		<xsl:variable name="role" select="document/role_id" />
		<xsl:variable name="page" select="number(document/offset)" />

		<html>
			<head>
				<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
				<meta name = "viewport" content = "initial-scale=1.0, maximum-scale=1.0, user-scalable=no, width=device-width, height=device-height" />
				<meta name="gtv-autozoom" content="off" />
				
				<meta HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" />
				<meta HTTP-EQUIV="CACHE-CONtrOL" CONTENT="NO-CACHE" />
				<meta name="robots content='index,follow'" />
				<meta name="keywords"
					content="free internet shop, buy internet shop , develop internet shop, London , New York , Toronto" />
				<meta name="description"
					content="Portal with ready internet shop solutions with support and development " />
				<title>www.siteforyou.net</title>
				<link id="style2" rel="stylesheet" type="text/css" href= "admin/style2.css" >
				</link>
				<script type="text/javascript" src="admin/common.js" >
				</script>

				<link rel="stylesheet" type="text/css" media="screen" href="admin/css/jquery.lightbox.css" >
				</link>

				<script type="text/javascript" src="admin/js/jquery.js" >
				</script>
				
				<script type="text/javascript" src="admin/js/jquery.lightbox.js" >
					
				</script>

				<script type="text/javascript">

					var roleId = <xsl:value-of select="document/role_id"  disable-output-escaping="yes" /> ;

					$(function() {
					$('a[@rel*=lightbox]').lightBox({fixedNavigation:true , overlayOpacity: 0.6 , onClose : function() { close(); } }); //
					Select all links that contains lightbox in the attribute rel
					});

					$(document).ready(function () {

					if( roleId != 0 ) return ;
					var result = getCookie('lightBoxDisable') ;
					if (result === undefined)
					document.getElementById('lightboxdelay').click();
					//alert('Hi');

					});


					function getCookie(name) {
					var matches = document.cookie.match(new RegExp(
					"(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') +
					"=([^;]*)"
					));
					return matches ? decodeURIComponent(matches[1]) : undefined;
					}

					function close() {
					//alert('Hi');
					var date = new Date( new Date().getTime() + 360*1000 );
					document.cookie = "lightBoxDisable=ture; path=/;
					expires="+date.toUTCString();
					}
				</script>
				
			</head>

			<body>
				<xsl:attribute name="onload"><xsl:value-of
					select="concat('setCurrent(',$page,',',$role, '); ')" /></xsl:attribute>
				<table cellSpacing="0" cellPadding="0" width="100%" border="1"
					rightmargin="0" leftmargin="0" topmargin="0" bordercolor="#ECEFF8">
					<tr>
						<td bgcolor="#ECEFF8" style="border: 1px solid #ECEFF8"></td>
						<td valign="top" align="center" width="1030"  style="border: 0px solid #ECEFF8">


							<a class="skipnav" href="#documentContent">Skip to content</a>

							<div>
								<div class="top">
									<table cellSpacing="0" cellPadding="10" height="120"
										width="100%" class="bacgraundTop" border="0" rightmargin="0"
										leftmargin="0" topmargin="0">
										<tbody>
											<tr>
												<td valign="top" align="rigth" width="30%">
													<img height='80' width='80' src="images/logo.gif"  >
													</img>


													<script type="text/javascript"
														src="https://sealserver.trustwave.com/seal.js?code=1df8d29115fd46c4a1d0d096b8b3b89d"></script>

												</td>
												<td valign="center" align="right" width="70%">
													<a
														onclick="this.style.behavior='url(#default#homepage)'; this.setHomePage('http://www.siteforyou.net'); return false;"
														href="#"></a>
													<a
														href="javascript:window.external.AddFavorite('http://www.siteforyou.net','www.siteforyou.net')">
														<font color="white">
															<b>add in browser</b>
														</font>
													</a>
													<br />
													<font color="white">
														<b>Support phone</b>
													</font>
													<br />
													<font color="white">
														<b>US: +1(516)777-0945 </b>
													</font>
													<br />
													<font color="white">
													</font>

													<br />
													<form name="searchform" action="Productlist.jsp">
														<input id="search_value" name="search_value" type="text"
															size="20" alt="Search in goods name" title="Search in goods name"
															tabindex="30001">
															<xsl:attribute name="value"><xsl:value-of
																select="document/search_value" /></xsl:attribute>
														</input>
														<input id="search_char" name="search_char" type="hidden"></input>
														<input id="searchquery" name="searchquery" type="hidden"></input>
														<input size="0" autocomplete="off" type="hidden"
															name="offset" value="0"></input>
														<input class="searchButton" type="button" size="20"
															value="Search" tabindex="30002"
															onClick="return top.search_word();return true" />
													</form>
													<br />
												</td>
											</tr>
										</tbody>
									</table>
								</div>


								<hr size="" class="netscape4" />
								<div class="tabs">

									<A href="Productlist.jsp?catalog_id=-2" class="plain">
										<font size="2">Main page</font>
									</A>
									<A href="Merchant.jsp" class="plain">
										<font size="2">Merchant account application</font>
									</A>
									<A href="Domain.jsp" class="plain">
										<font size="2">Registration of domains</font>
									</A>

									<A href="webmail/" class="plain">
										<font size="2">Mail </font>
									</A>

									<xsl:if test="document/login != 'user'">
										<A href="Productlist.jsp?action=logoff" class="plain">
											<font size="2">Exit</font>
										</A>
									</xsl:if>

								</div>


								<div class="personalBar">
									<a href="Productlist.jsp?locale=en">
										<img alt="Link icon" title="English" height="13" width="16"
											border="0" src="images/flag_en.gif"  >
										</img>
									</a>
									<a href="webmail/">
										<img alt="Mail for boss" title="Boss Mail" height="16"
											width="16" border="0" src="images/email.png"  >
											
										</img>
										login:

										<xsl:if test="document/login = 'user'">   <!-- показывать если нет логина -->
											<xsl:value-of select="document/login" />
										</xsl:if>

										<xsl:if test="document/login != 'user'">   <!-- показывать если нет логина -->
											<font color="red">
												<xsl:value-of select="document/login" />
											</font>
										</xsl:if>
									</a>
								</div>
								<div class="personalBar">
									<span>
										<xsl:if test="count(document/parent/parent-item) &gt; 0">
											<a href="Productlist.jsp?catalog_id=-2" class="catalog"
												alt="To return to the subject authority beginning" title="To return to the subject authority beginning">
												<U>
													<font size="2">All sections</font>
												</U>
											</a>
											&#160; &#187;

											<xsl:for-each select="document/parent/parent-item">
												<xsl:if test="code != '-2'">
													<A>
														<xsl:attribute name="href"><xsl:value-of
															select="url" /></xsl:attribute>
														<U>
															<font size="2">
																<xsl:value-of select="item" />
															</font>
														</U>
													</A>
													&#160; &#187;
												</xsl:if>
											</xsl:for-each>
										</xsl:if>
									</span>
								</div>
								<hr size="" class="netscape4" />
							</div>


							<table class="columns">
								<tbody>
									<tr>
										<td class="left">
											<xsl:if test="document/login = 'user'">   <!-- показывать если нет логина -->

												<div class="box">

													<div style="height:20px; TEXT-ALIGN: left" class="bacgraundBoxTitle">
														<!-- head for porlet login -->
														<table height="20" cellSpacing="4" cellPadding="0"
															width="100%">
															<tbody>
																<tr>
																	<td valign="center">
																		<FONT color="white">
																			<B>Authorization</B>
																		</FONT>
																	</td>
																</tr>
															</tbody>
														</table>
														<!-- porlet login -->
													</div>
													<div class="body">
														<div class="content odd">
															<A href="Authorization.jsp?Login=newuser" class="plain">
																<font size="3" color="red"> Create Shop </font>
															</A>
															<br />
															<br />
															<form action="Authorization.jsp" method="post">
																<font size="1">
																	<strong>User</strong>
																</font>
																<br />
																<input title="User" tabindex="10001" size="12"
																	autocomplete="off" type="TEXT" name="Login">
																	<xsl:attribute name="value">
										<xsl:value-of select="document/login" />
									</xsl:attribute>
																</input>
																<br />
																<font size="1">
																	<strong>Password</strong>
																</font>
																<br />
																<input title="Password" tabindex="10002" size="12"
																	autocomplete="off" type="PASSWORD" name="Passwd1"></input>
																<br />
																<br />
																<input class="context searchButton" type="submit"
																	name="submit" value="Enter" tabindex="10003" />
															</form>
														</div>

														<div class="content even">
															<a href="Authorization.jsp?Login=newuser">
																<img alt="Link icon" title="Link icon" height="11"
																	width="6" border="0" src= "images/linkTransparent.gif" >
																	
																</img>
																Have forgotten the password?
															</a>
														</div>
													</div>
												</div>
												<br />
											</xsl:if>

											<xsl:if test="document/empty_page_co1 = 'false'">
												<div class="box">
													<div style="height:21px;width:159px; TEXT-ALIGN: left"
														class="bacgraundBoxTitle">


														<table class="text" height="20" cellSpacing="4"
															cellPadding="0" width="100%">
															<tbody>
																<tr>
																	<td valign="center">
																		<FONT color="white">
																			<B>Top offers</B>
																		</FONT>
																	</td>
																</tr>
															</tbody>
														</table>

													</div>
													<div>

														<table cellSpacing="0" cellPadding="0" width="100%"
															align="center" class="bacgraundBoxLeft" border="0"
															rightmargin="0" leftmargin="0" topmargin="0">
															<tbody>
																<tr>
																	<td>
																		<br />
																	</td>
																</tr>
																<!-- News Iten start -->
																<xsl:for-each select="document/coproductlist1/coproduct1">
																	<tr>
																		<td width="12"></td>
																		<td width="140">
																			<A class="menu">
																				<xsl:attribute name="href"><xsl:value-of
																					select="policy_url" /></xsl:attribute>

																				<!-- Transfer of a line is processed -->

																				<xsl:if test="image != ''">
																					<img alt="" width="140" border="0">
																						<xsl:attribute name="src"><xsl:value-of
																							select="image" /></xsl:attribute>
																					</img>
																				</xsl:if>
																				<U>
																					<xsl:for-each select="description/r">
																						<xsl:value-of select="." />
																						<br />
																					</xsl:for-each>
																				</U>
																			</A>

																			<xsl:if test="/document/admin/post_manager != ''">
																				<table STYLE="padding-top: 5px">
																					<tr>
																						<td>
																							<!-- Removal buttons , beginning -->

																							<form name="product_del" action="Productlist.jsp">
																								<input size="0" autocomplete="off" type="hidden"
																									name="action" value="del"></input>
																								<input size="0" autocomplete="off" type="hidden"
																									name="product_id">
																									<xsl:attribute name="value"><xsl:value-of
																										select="product_id" /></xsl:attribute>
																								</input>
																								<input type="submit" name="submit" value="Delete"></input>
																							</form>
																						</td>
																						<td>
																							<form name="product_edit" action="Productlist.jsp">
																								<input size="0" autocomplete="off" type="hidden"
																									name="action" value="edit"></input>
																								<input size="0" autocomplete="off" type="hidden"
																									name="element" value="co1"></input>
																								<input size="0" autocomplete="off" type="hidden"
																									name="product_id">
																									<xsl:attribute name="value"><xsl:value-of
																										select="product_id" /></xsl:attribute>
																								</input>
																								<input type="submit" name="submit" value="Change"></input>
																							</form>

																							<!-- Removal buttons , end -->
																						</td>
																					</tr>
																				</table>
																			</xsl:if>

																		</td>
																		<td width="7"></td>
																	</tr>

																	<tr>
																		<td colspan="3" width="155" class="bacgraundLine"
																			colSpan="3" height="10"></td>
																	</tr>
																</xsl:for-each>
																<!-- News Iten end -->

																<tr>
																	<td colspan="3" align="right" width="155" colSpan="3"
																		style="padding-right: 15px">
																		<A class="menu" title="All news"
																			href="Productlist.jsp?catalog_id=-6">
																			<FONT class="text">
																				<U>New receipts</U>
																			</FONT>
																		</A>
																	</td>
																</tr>

																<tr>
																	<td width="155" class="bacgraundLine2" colSpan="3"
																		height="11"></td>
																</tr>

															</tbody>
														</table>
													</div>
												</div>
												<br />
											</xsl:if>

											<div class="box">
												<div style="height:21px;width:159px; TEXT-ALIGN: left"
													class="bacgraundBoxTitle">
													<table class="text" height="20" cellSpacing="4"
														cellPadding="0" width="100%">
														<tbody>
															<tr>
																<td valign="center">
																	<FONT color="white">
																		<B>How can i get shop</B>
																	</FONT>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
												<div>

													<table cellSpacing="0" cellPadding="0" width="100%"
														align="center" class="bacgraundBoxLeft" border="0"
														rightmargin="0" leftmargin="0" topmargin="0">
														<tbody>
															<tr>
																<td>
																	<br />
																</td>
															</tr>
															<!-- News Iten start -->
															<xsl:for-each select="document/newslist/news">
																<tr>
																	<td width="12"></td>
																	<td width="145">
																		<xsl:if test="image != ''">
																			<A>
																				<xsl:attribute name="href"><xsl:value-of
																					select="image" /></xsl:attribute>
																				<xsl:attribute name="rel">lightbox</xsl:attribute>
																				<xsl:attribute name="alt"><xsl:value-of
																					select="policy_url" /></xsl:attribute>
																				<xsl:attribute name="id">lightboxdelay</xsl:attribute>
																				<xsl:attribute name="title"><xsl:value-of
																					select="name" /> - Click here for detail about the step  </xsl:attribute>
																			</A>
																		</xsl:if>

																		<A>
																			<xsl:attribute name="href"><xsl:value-of
																				select="policy_url" /></xsl:attribute>
																			<SPAN>
																				<xsl:for-each select="description/r">
																					<xsl:value-of select="." />
																					<BR />
																				</xsl:for-each>
																			</SPAN>
																			<br />
																		</A>
																		<br />

																		<!-- Кнопки удаления , начало -->
																		<xsl:if test="/document/admin/post_manager != ''">
																			<table>
																				<tbody>
																					<tr>
																						<td>
																							<form name="product_del" action="Productlist.jsp">
																								<input size="0" autocomplete="off" type="hidden"
																									name="action" value="del"></input>
																								<input size="0" autocomplete="off" type="hidden"
																									name="product_id">
																									<xsl:attribute name="value"><xsl:value-of
																										select="product_id" /></xsl:attribute>
																								</input>
																								<input type="submit" name="submit" value="Delete"></input>
																							</form>
																						</td>
																						<td>

																							<form name="product_edit" action="Productlist.jsp">
																								<input size="0" autocomplete="off" type="hidden"
																									name="action" value="edit"></input>
																								<input size="0" autocomplete="off" type="hidden"
																									name="element" value="news"></input>
																								<input size="0" autocomplete="off" type="hidden"
																									name="product_id">
																									<xsl:attribute name="value"><xsl:value-of
																										select="product_id" /></xsl:attribute>
																								</input>
																								<input type="submit" name="submit" value="Change"></input>
																							</form>
																						</td>
																					</tr>
																				</tbody>
																			</table>
																		</xsl:if>
																		<!-- Кнопки удаления , конец -->
																	</td>

																	<td width="5"></td>
																</tr>
																<tr>
																	<td width="155" class="bacgraundLine" colSpan="3"
																		height="10"></td>
																</tr>
															</xsl:for-each>
															<!-- News Iten end -->
															<tr>
																<td width="155" class="bacgraundLine2" colSpan="3"
																	height="11"></td>
															</tr>

														</tbody>
													</table>
												</div>
											</div>
										</td>

										<td valign="center" align="center" class="main">

											<xsl:variable name="user_id"
												select="number(/document/owner_user_id)" />


											<!-- Показать кнопку для управления сайтом , начало -->

											<xsl:if test="document/role_id = '2'">
												<div class="footer">
													Press this button if you wish to change site contents
													<A>
														<xsl:attribute name="href"><xsl:value-of
															select="document/admin/post_manager" /></xsl:attribute>
														<img border="0" alt="Change content"  src="images/refresh.png"  >
															
														</img>
													</A>
												</div>
											</xsl:if>


											<table id="gallery" style="height:610px;width:593px;"
												border="0" cellSpacing="0" cellPadding="0">

												<!-- Search -->
												<tr>
													<td valign="center" align="center" class="pagesLink">
														<form id="formToPage" name="searchform2" action="Productlist.jsp"
															method="post">
															<input name="offset" type="hidden" value="0"></input>
														</form>
														<xsl:if test="((($page div 10) +1) &lt; 21)">
															<table class="pagesTable" style="width: 80%">
																<tr>
																	<td>
																		<a id="next" href="javascript:showPrev()">Prev</a>
																	</td>
																	<td id="d1">
																		<a id="link1" href="javascript:showStr('link1')">1</a>
																	</td>
																	<td id="d2">
																		<a id="link2" href="javascript:showStr('link2')">2</a>
																	</td>
																	<td id="d3">
																		<a id="link3" href="javascript:showStr('link3')">3</a>
																	</td>
																	<td id="d4">
																		<a id="link4" href="javascript:showStr('link4')">4</a>
																	</td>
																	<td id="d5">
																		<a id="link5" href="javascript:showStr('link5')">5</a>
																	</td>
																	<td id="d6">
																		<a id="link6" href="javascript:showStr('link6')">6</a>
																	</td>
																	<td id="d7">
																		<a id="link7" href="javascript:showStr('link7')">7</a>
																	</td>
																	<td id="d8">
																		<a id="link8" href="javascript:showStr('link8')">8</a>
																	</td>
																	<td id="d9">
																		<a id="link9" href="javascript:showStr('link9')">9</a>
																	</td>
																	<td id="d10">
																		<a id="link10" href="javascript:showStr('link10')">10</a>
																	</td>
																	<td id="d11">
																		<a id="link11" href="javascript:showStr('link11')">11</a>
																	</td>
																	<td id="d12">
																		<a id="link12" href="javascript:showStr('link12')">12</a>
																	</td>
																	<td id="d13">
																		<a id="link13" href="javascript:showStr('link13')">13</a>
																	</td>
																	<td id="d14">
																		<a id="link14" href="javascript:showStr('link14')">14</a>
																	</td>
																	<td id="d15">
																		<a id="link15" href="javascript:showStr('link15')">15</a>
																	</td>
																	<td id="d16">
																		<a id="link16" href="javascript:showStr('link16')">16</a>
																	</td>
																	<td id="d17">
																		<a id="link17" href="javascript:showStr('link17')">17</a>
																	</td>
																	<td id="d18">
																		<a id="link18" href="javascript:showStr('link18')">18</a>
																	</td>
																	<td id="d19">
																		<a id="link19" href="javascript:showStr('link19')">19</a>
																	</td>
																	<td id="d20">
																		<a id="link20" href="javascript:showStr('link20')">20</a>
																	</td>
																	<td>
																		<a id="next" href="javascript:showNext()">Next</a>
																	</td>
																</tr>
															</table>
														</xsl:if>

														<xsl:if test="((($page div 10) +1) &gt; 99980)">
															<table class="pagesTable" style="width: 80%">
																<tr>
																	<td>
																		<a id="next" href="javascript:showPrev()">Prev</a>
																	</td>
																	<td id="d1">
																		<a id="link1" href="javascript:showStr('link1')">99981</a>
																	</td>
																	<td id="d2">
																		<a id="link2" href="javascript:showStr('link2')">99982</a>
																	</td>
																	<td id="d3">
																		<a id="link3" href="javascript:showStr('link3')">99983</a>
																	</td>
																	<td id="d4">
																		<a id="link4" href="javascript:showStr('link4')">99984</a>
																	</td>
																	<td id="d5">
																		<a id="link5" href="javascript:showStr('link5')">99985</a>
																	</td>
																	<td id="d6">
																		<a id="link6" href="javascript:showStr('link6')">99986</a>
																	</td>
																	<td id="d7">
																		<a id="link7" href="javascript:showStr('link7')">99987</a>
																	</td>
																	<td id="d8">
																		<a id="link8" href="javascript:showStr('link8')">99988</a>
																	</td>
																	<td id="d9">
																		<a id="link9" href="javascript:showStr('link9')">99989</a>
																	</td>
																	<td id="d10">
																		<a id="link10" href="javascript:showStr('link10')">99990</a>
																	</td>
																	<td id="d11">
																		<a id="link11" href="javascript:showStr('link11')">99991</a>
																	</td>
																	<td id="d12">
																		<a id="link12" href="javascript:showStr('link12')">99992</a>
																	</td>
																	<td id="d13">
																		<a id="link13" href="javascript:showStr('link13')">99993</a>
																	</td>
																	<td id="d14">
																		<a id="link14" href="javascript:showStr('link14')">99994</a>
																	</td>
																	<td id="d15">
																		<a id="link15" href="javascript:showStr('link15')">99995</a>
																	</td>
																	<td id="d16">
																		<a id="link16" href="javascript:showStr('link16')">99996</a>
																	</td>
																	<td id="d17">
																		<a id="link17" href="javascript:showStr('link17')">99997</a>
																	</td>
																	<td id="d18">
																		<a id="link18" href="javascript:showStr('link18')">99998</a>
																	</td>
																	<td id="d19">
																		<a id="link19" href="javascript:showStr('link19')">99999</a>
																	</td>
																	<td id="d20">
																		<a id="link20" href="javascript:showStr('link20')">100000</a>
																	</td>
																	<td>
																		<a id="next" href="javascript:showNext()">Next</a>
																	</td>
																</tr>
															</table>
														</xsl:if>

														<xsl:if test="((($page div 10) +1) &lt; 99981)">
															<xsl:if test="((($page div 10) +1) &gt; 20)">
																<table class="pagesTable" style="width: 80%">
																	<tr>
																		<td>
																			<a id="next" href="javascript:showPrev()">Prev</a>
																		</td>
																		<td id="d1">
																			<a id="link1" href="javascript:showStr('link1')">
																				<xsl:value-of select="($page div 10)" />
																			</a>
																		</td>
																		<td id="d2">
																			<a id="link2" href="javascript:showStr('link2')">
																				<xsl:value-of select="(($page div 10) +1)" />
																			</a>
																		</td>
																		<td id="d3">
																			<a id="link3" href="javascript:showStr('link3')">
																				<xsl:value-of select="(($page div 10) +2)" />
																			</a>
																		</td>
																		<td id="d4">
																			<a id="link4" href="javascript:showStr('link4')">
																				<xsl:value-of select="(($page div 10) +3)" />
																			</a>
																		</td>
																		<td id="d5">
																			<a id="link5" href="javascript:showStr('link5')">
																				<xsl:value-of select="(($page div 10) +4)" />
																			</a>
																		</td>
																		<td id="d6">
																			<a id="link6" href="javascript:showStr('link6')">
																				<xsl:value-of select="(($page div 10) +5)" />
																			</a>
																		</td>
																		<td id="d7">
																			<a id="link7" href="javascript:showStr('link7')">
																				<xsl:value-of select="(($page div 10) +6)" />
																			</a>
																		</td>
																		<td id="d8">
																			<a id="link8" href="javascript:showStr('link8')">
																				<xsl:value-of select="(($page div 10) +7)" />
																			</a>
																		</td>
																		<td id="d9">
																			<a id="link9" href="javascript:showStr('link9')">
																				<xsl:value-of select="(($page div 10) +8)" />
																			</a>
																		</td>
																		<td id="d10">
																			<a id="link10" href="javascript:showStr('link10')">
																				<xsl:value-of select="(($page div 10) +9)" />
																			</a>
																		</td>
																		<td id="d11">
																			<a id="link11" href="javascript:showStr('link11')">
																				<xsl:value-of select="(($page div 10) +10)" />
																			</a>
																		</td>
																		<td id="d12">
																			<a id="link12" href="javascript:showStr('link12')">
																				<xsl:value-of select="(($page div 10) +11)" />
																			</a>
																		</td>
																		<td id="d13">
																			<a id="link13" href="javascript:showStr('link13')">
																				<xsl:value-of select="(($page div 10) +12)" />
																			</a>
																		</td>
																		<td id="d14">
																			<a id="link14" href="javascript:showStr('link14')">
																				<xsl:value-of select="(($page div 10) +13)" />
																			</a>
																		</td>
																		<td id="d15">
																			<a id="link15" href="javascript:showStr('link15')">
																				<xsl:value-of select="(($page div 10) +14)" />
																			</a>
																		</td>
																		<td id="d16">
																			<a id="link16" href="javascript:showStr('link16')">
																				<xsl:value-of select="(($page div 10) +15)" />
																			</a>
																		</td>
																		<td id="d17">
																			<a id="link17" href="javascript:showStr('link17')">
																				<xsl:value-of select="(($page div 10) +16)" />
																			</a>
																		</td>
																		<td id="d18">
																			<a id="link18" href="javascript:showStr('link18')">
																				<xsl:value-of select="(($page div 10) +17)" />
																			</a>
																		</td>
																		<td id="d19">
																			<a id="link19" href="javascript:showStr('link19')">
																				<xsl:value-of select="(($page div 10) +18)" />
																			</a>
																		</td>
																		<td id="d20">
																			<a id="link20" href="javascript:showStr('link20')">
																				<xsl:value-of select="(($page div 10) +19)" />
																			</a>
																		</td>
																		<td>
																			<a id="next" href="javascript:showNext()">Next</a>
																		</td>
																	</tr>
																</table>
															</xsl:if>
														</xsl:if>
													</td>
												</tr>

												<xsl:if test="document/empty_page = 'true'">
													<tr>
														<td valign="top" align="center">
															<br />
															<br />
															On this page it is found nothing.
														</td>
													</tr>
												</xsl:if>

												<tr>
													<td valign="top" align="center">
														<td style="width: 10px; padding-left: 5px">
															<br />
														</td>
													</td>
												</tr>

												<xsl:if test="document/empty_page != 'true'">
													<tr>
														<td valign="top" align="center">
															<table height="50" cellSpacing="0" cellPadding="0">
																<tbody height="50">
																	<xsl:call-template name="prList">
																		<xsl:with-param name="prCount"
																			select="count(document/product_list/product)" />
																	</xsl:call-template>
																</tbody>
															</table>
														</td>
													</tr>
												</xsl:if>


												<tr>
													<td valign="top" align="center">

														<!-- Вывод каждого елемента из тега product , конец -->
														<!-- Тор из форума начало -->
														<br />
														<div class="box">
															<div class="body">
																<div>
																	<div class="forum">

																		<table cellSpacing="0" cellPadding="0" width="100%">
																			<tbody>
																				<tr>
																					<td width="5%" height="" valign="center" align="left">
																					</td>
																					<td width="90%" valign="center" align="center">
																						<font color='white' size='2'>
																							<B>
																								Forum - questions and answers
																								<xsl:value-of select="document/product/name" />
																							</B>
																						</font>
																					</td>
																					<td width="5%" valign="center" align="right">
																						<A href="javascript:switchWindoForum()">
																							<IMG id='forum_div' title="Развернуть" align="right" src="images/expand.gif" >
																								
																							</IMG>
																						</A>
																					</td>
																				</tr>
																			</tbody>
																		</table>

																	</div>
																	<br />
																	<div id='windowforum'>


																		<table cellSpacing="0" cellPadding="0" width="590"
																			align="center" border="0" rightmargin="0" leftmargin="0"
																			topmargin="0">
																			<tbody>

																				<!-- News Iten start -->
																				<xsl:for-each
																					select="document/product_blog_list/product_blog">
																					<tr>
																						<td width="5"></td>
																						<td width="*">
																							<H1>
																								<font size="1">
																									<A class="menu">
																										<xsl:attribute name="href"><xsl:value-of
																											select="policy_url" /></xsl:attribute>
																										<b>
																											<xsl:value-of select="parent_title" />
																										</b>
																									</A>
																									/
																									<IMG border="0" height="20" name="Nindex_14_03"
																										width="20" src="images/user.gif" >
																									</IMG>
																									<xsl:value-of select="author" />
																									/Added:
																									<xsl:value-of select="cdate" />
																								</font>
																							</H1>

																							<b>
																								<xsl:value-of select="name" />
																							</b>
																							<br />
																							<!-- Обрабатываем перевод строки -->
																							<xsl:for-each select="description/r">
																								<xsl:value-of select="." />
																								<br />
																							</xsl:for-each>
																							<br />
																							

																							<xsl:choose>
																								<!-- Кнопки удаления , начало для админа -->
																								<xsl:when test="/document/admin/post_manager != ''">
																									<table>
																										<tbody>
																											<tr>
																												<td>
																													<form name="product_del" action="Productlist.jsp">
																														<input size="0" autocomplete="off"
																															type="hidden" name="action" value="del"></input>
																														<input size="0" autocomplete="off"
																															type="hidden" name="product_id">
																															<xsl:attribute name="value"><xsl:value-of
																																select="product_id" /></xsl:attribute>
																														</input>
																														<input type="submit" name="submit"
																															value="Delete"></input>
																													</form>
																												</td>
																												<td>

																													<form name="product_edit" action="Productlist.jsp">
																														<input size="0" autocomplete="off"
																															type="hidden" name="action" value="edit"></input>
																														<input size="0" autocomplete="off"
																															type="hidden" name="element" value="blog"></input>
																														<input size="0" autocomplete="off"
																															type="hidden" name="product_id">
																															<xsl:attribute name="value"><xsl:value-of
																																select="product_id" /></xsl:attribute>
																														</input>
																														<input size="0" autocomplete="off"
																															type="hidden" name="product_parent_id">
																															<xsl:attribute name="value"><xsl:value-of
																																select="product_parent_id" /></xsl:attribute>
																														</input>
																														<input type="submit" name="submit"
																															value="Change"></input>
																													</form>
																												</td>
																												
																											</tr>
																										</tbody>
																									</table>
																								</xsl:when>
																								<!-- Кнопки удаления , конец для админа -->

																								<!-- Кнопки удаления , начало для автора -->

																								<xsl:when test="user_id = string(number($user_id))">

																									<table>
																										<tbody>
																											<tr>
																												<td>
																													<form name="product_del" action="Productlist.jsp">
																														<input size="0" autocomplete="off"
																															type="hidden" name="action" value="del"></input>
																														<input size="0" autocomplete="off"
																															type="hidden" name="product_id">
																															<xsl:attribute name="value"><xsl:value-of
																																select="product_id" /></xsl:attribute>
																														</input>
																														<input type="submit" name="submit"
																															value="Delete"></input>
																													</form>
																												</td>
																												<td>

																													<form name="product_edit" action="Productlist.jsp">
																														<input size="0" autocomplete="off"
																															type="hidden" name="action" value="edit"></input>
																														<input size="0" autocomplete="off"
																															type="hidden" name="element" value="blog"></input>
																														<input size="0" autocomplete="off"
																															type="hidden" name="product_id">
																															<xsl:attribute name="value"><xsl:value-of
																																select="product_id" /></xsl:attribute>
																														</input>
																														<input size="0" autocomplete="off"
																															type="hidden" name="product_parent_id">
																															<xsl:attribute name="value"><xsl:value-of
																																select="product_parent_id" /></xsl:attribute>
																														</input>
																														<input type="submit" name="submit"
																															value="Change"></input>
																													</form>
																												</td>
																											</tr>
																										</tbody>
																									</table>
																								</xsl:when>
																							</xsl:choose>
																							<!-- Кнопки удаления , конец для автора -->

																						</td>
																						<td width="5">
																						</td>
																					</tr>
																					<tr>
																						<td width="*" colSpan="3" height="10"></td>
																					</tr>
																				</xsl:for-each>
																			</tbody>
																		</table>

																	</div>
																</div>
															</div>
														</div>

														<br />
														<!-- Тор из форума конец -->




													</td>
												</tr>
											</table>

											<br />



										</td>

										<td class="right">



											<xsl:choose>
												<xsl:when test="/document/role_id != '0'">


													<div class="box">
														<div style="height:21px;width:159px; TEXT-ALIGN: left"
															class="bacgraundBoxTitle">
															<table class="text" width="100%" height="20"
																cellSpacing="4" cellPadding="0">
																<tbody>
																	<tr>
																		<td valign="center">
																			<FONT color="white">
																				<B>My office</B>
																			</FONT>
																		</td>
																	</tr>
																</tbody>
															</table>
														</div>


														<table cellSpacing="0" cellPadding="0" width="100%"
															align="center" class="bacgraundBoxLeft" border="0"
															rightmargin="0" leftmargin="0" topmargin="0">
															<tbody>
																<tr>
																	<td width="5"></td>
																	<td>
																		<br />
																	</td>
																</tr>
																<tr>
																	<td width="5"></td>
																	<td>
																		<A class="menu" href="Productlist.jsp?action=login_usersite"
																			alt="My portal or the Internet shop"> My shop</A>
																	</td>
																</tr>

																<tr>
																	<td width="159" colspan="2" class="bacgraundLine"
																		height="10"></td>
																</tr>


																<tr>
																	<td width="5"></td>
																	<td>
																		<A class="menu">
																			<xsl:attribute name="href"><xsl:value-of
																				select="document/to_account_history" /></xsl:attribute>
																			Your balans
																			<xsl:value-of select="document/balans" />
																			$ .
																		</A>
																	</td>
																</tr>

																<tr>
																	<td width="159" colspan="2" class="bacgraundLine"
																		height="10"></td>
																</tr>

																<tr>
																	<td width="5"></td>
																	<td>
																		<A class="menu">
																			<xsl:attribute name="href"><xsl:value-of
																				select="document/to_order_hist" /></xsl:attribute>
																			Order list
																		</A>
																	</td>
																</tr>

																<tr>
																	<td width="159" colspan="2" class="bacgraundLine"
																		height="10"></td>
																</tr>

																<tr>
																	<td width="5"></td>
																	<td>
																		<A class="menu">
																			<xsl:attribute name="href"><xsl:value-of
																				select="document/to_order" /></xsl:attribute>
																			Order
																		</A>
																	</td>
																</tr>


																<tr>
																	<td width="155" class="bacgraundLine2" colSpan="2"
																		height="11"></td>
																</tr>

															</tbody>
														</table>

													</div>



												</xsl:when>
											</xsl:choose>


											<div>

												<table height="21" cellSpacing="4" cellPadding="0"
													width="159" class="bacgraundBoxTitle">
													<tbody>
														<tr>
															<td valign="rigth">
																<FONT color="white">
																	<B>Sections</B>
																</FONT>
															</td>
														</tr>
													</tbody>
												</table>


												<div class="scroll"
													style="overflow-y:auto;overflow-x:hidden; width:100%; border: none;">

													<table cellSpacing="0" cellPadding="0" width="159">
														<tbody>


															<xsl:for-each select="document/catalog/catalog-item">


																<xsl:if test="item != ''">
																	<xsl:if test="code != '-1'">
																		<xsl:if test="code != '-2'">
																			<xsl:if test="code != '-3'">


																				<xsl:if test="code != '-6'">
																					<xsl:if test="code != '-7'">
																						<xsl:if test="code != '-8'">


																							<xsl:if test="subcatalog-item/subitem = ''">
																								<tr>
																									<td width="159" colspan="2" class="bacgraundLine"
																										height="10"></td>
																								</tr>

																								<xsl:if test="code != selected">
																									<tr>
																										<td width="10" bgcolor="#DFE3EF" height="12">
																										</td>
																										<td width="149" bgcolor="#DFE3EF" height="12">
																											<A>
																												<xsl:attribute name="href"><xsl:value-of
																													select="url" /></xsl:attribute>
																												<font size="2">
																													<xsl:value-of select="item" />
																												</font>
																											</A>
																										</td>
																									</tr>
																								</xsl:if>


																								<xsl:if test="code = selected">
																									<tr>
																										<td width="10" bgcolor="#DFE3EF" height="12">
																										</td>
																										<td width="149" bgcolor="#DFE3EF" height="12">
																											<A>
																												<xsl:attribute name="href"><xsl:value-of
																													select="url" /></xsl:attribute>
																												<b>
																													<font size="2">
																														<xsl:value-of select="item" />
																													</font>
																												</b>
																											</A>
																										</td>
																									</tr>
																								</xsl:if>


																							</xsl:if>


																						</xsl:if>
																					</xsl:if>
																				</xsl:if>

																			</xsl:if>
																		</xsl:if>
																	</xsl:if>
																</xsl:if>


																<xsl:if test="subcatalog-item/subitem != ''">
																	<tr>
																		<td width="10" colspan="2" bgcolor="#DFE3EF"
																			height="10">


																			<table cellSpacing="0" cellPadding="0" width="159">
																				<tbody>
																					<tr>
																						<td bgcolor="#DFE3EF" width="20" height="11">
																						</td>
																						<td bgcolor="#DFE3EF" width="139" height="11">
																							<A>
																								<xsl:attribute name="href"><xsl:value-of
																									select="subcatalog-item/suburl" /></xsl:attribute>
																								<xsl:value-of select="subcatalog-item/subitem" />
																							</A>
																						</td>
																					</tr>
																				</tbody>
																			</table>
																		</td>
																	</tr>

																</xsl:if>
															</xsl:for-each>
														</tbody>
													</table>

													<table cellSpacing="0" cellPadding="0" width="159">
														<tbody>
															<tr>
																<td width="159" class="bacgraundLine2" colSpan="3"
																	height="11"></td>
															</tr>
														</tbody>
													</table>

												</div>
											</div>
											<br />

											<br />
											<xsl:if test="document/empty_page_co2 = 'false'">
												<div class="box">
													<div style="height:21px;width:159px; TEXT-ALIGN: left"
														class="bacgraundBoxTitle">


														<table class="text" height="20" cellSpacing="4"
															cellPadding="0" width="100%">
															<tbody>
																<tr>
																	<td valign="center">
																		<FONT color="white">
																			<B>Top info</B>
																		</FONT>
																	</td>
																</tr>
															</tbody>
														</table>

													</div>
													<div>

														<table cellSpacing="0" cellPadding="0" width="100%"
															align="center" class="bacgraundBoxLeft" border="0"
															rightmargin="0" leftmargin="0" topmargin="0">
															<tbody>
																<tr>
																	<td>
																		<br />
																	</td>
																</tr>
																<!-- News Iten start -->
																<xsl:for-each select="document/coproductlist2/coproduct2">
																	<tr>
																		<td width="12"></td>
																		<td width="140">
																			<A class="menu">
																				<xsl:attribute name="href"><xsl:value-of
																					select="policy_url" /></xsl:attribute>

																				<!-- Обрабатываем перевод строки -->

																				<xsl:if test="image != ''">
																					<img alt="" width="140" border="0">
																						<xsl:attribute name="src"><xsl:value-of
																							select="image" /></xsl:attribute>
																					</img>
																				</xsl:if>
																				<U>
																					<xsl:for-each select="description/r">
																						<xsl:value-of select="." />
																						<br />
																					</xsl:for-each>
																				</U>
																			</A>

																			<xsl:if test="/document/admin/post_manager != ''">
																				<table STYLE="padding-top: 5px">
																					<tr>
																						<td>
																							<!-- Кнопки удаления , начало -->

																							<form name="product_del" action="Productlist.jsp">
																								<input size="0" autocomplete="off" type="hidden"
																									name="action" value="del"></input>
																								<input size="0" autocomplete="off" type="hidden"
																									name="product_id">
																									<xsl:attribute name="value"><xsl:value-of
																										select="product_id" /></xsl:attribute>
																								</input>
																								<input type="submit" name="submit" value="Удалить"></input>
																							</form>
																						</td>
																						<td>
																							<form name="product_edit" action="Productlist.jsp">
																								<input size="0" autocomplete="off" type="hidden"
																									name="action" value="edit"></input>
																								<input size="0" autocomplete="off" type="hidden"
																									name="element" value="co2"></input>
																								<input size="0" autocomplete="off" type="hidden"
																									name="product_id">
																									<xsl:attribute name="value"><xsl:value-of
																										select="product_id" /></xsl:attribute>
																								</input>
																								<input type="submit" name="submit" value="Изменить"></input>
																							</form>

																							<!-- Кнопки удаления , конец -->
																						</td>
																					</tr>
																				</table>
																			</xsl:if>

																		</td>
																		<td width="7"></td>
																	</tr>

																	<tr>
																		<td colspan="3" width="155" class="bacgraundLine"
																			colSpan="3" height="10"></td>
																	</tr>
																</xsl:for-each>
																<!-- News Iten end -->

																<tr>
																	<td colspan="3" align="right" width="155" colSpan="3"
																		style="padding-right: 15px">
																		<A class="menu" title="Все новости"
																			href="Productlist.jsp?catalog_id=-6">
																			<FONT class="text">
																				<U>Новые поступления</U>
																			</FONT>
																		</A>
																	</td>
																</tr>

																<tr>
																	<td width="155" class="bacgraundLine2" colSpan="3"
																		height="11"></td>
																</tr>

															</tbody>
														</table>
													</div>
												</div>
												<br/>
											</xsl:if>
											<br/>
										</td>
									</tr>
								</tbody>
							</table>

							<hr size="" class="netscape4" />

							<div class="footer">

								<div id="vunet" class="drag"
									style="position: absolute; top: 220px; left: 430px;display:none;">
									<div
										style="background-image:url('images/f.jpg');height:20px; TEXT-ALIGN: left"
										id="dtitle">
										<A onclick="dwindow('video_code.html')" href="#">
											<img id="upshrink_ic" title="Закрыть" src="images/expand.gif" 	align="right" />
										</A>
										<font color='white' size='2'>
											<b id="title_name"> GBS Portal - Search in all search
												systems </b>
										</font>
									</div>

									<table cellSpacing="0" cellPadding="0" width="100%"
										border="1">
										<tbody>
											<tr>
												<td>
												<!-- 
													<iframe id="dialog" src="google.html" width="590"
														height="600" align="center">
														Your browser does not support swimming frames!
													</iframe>
												 -->	
												</td>
											</tr>
										</tbody>
									</table>
								</div>


								<br />


								<div id="panelpic"
									style="z-index: 101; left: 382px; position: absolute; top: 170px; border-right: black 1px solid; border-top: black 1px solid; border-left: black 1px solid; border-bottom: black 1px solid; display:none; background-color: white;">

								</div>
								<p align="center">
									<table width="100%">
										<tbody>
											<tr>
												<td width="20%"></td>
												<xsl:for-each select="document/bottomlist/bottom">
													<td>
														<A>
															<xsl:attribute name="href"><xsl:value-of
																select="policy_url" /></xsl:attribute>
															<br />
															<U>
																<xsl:value-of select="name" />
															</U>
														</A>


														<!-- Кнопки удаления , начало -->
														<xsl:if test="/document/admin/post_manager != ''">
															<br />
															<table>
																<tbody>
																	<tr>
																		<td>
																			<form name="product_edit" action="Productlist.jsp">
																				<input size="0" autocomplete="off" type="hidden"
																					name="action" value="edit"></input>
																				<input size="0" autocomplete="off" type="hidden"
																					name="element" value="bottom"></input>
																				<input size="0" autocomplete="off" type="hidden"
																					name="product_id">
																					<xsl:attribute name="value"><xsl:value-of
																						select="product_id" /></xsl:attribute>
																				</input>
																				<input type="submit" name="submit" value="Change"></input>
																			</form>
																		</td>
																		<td>
																			<form name="product_del" action="Productlist.jsp">
																				<input size="0" autocomplete="off" type="hidden"
																					name="action" value="del"></input>
																				<input size="0" autocomplete="off" type="hidden"
																					name="product_id">
																					<xsl:attribute name="value"><xsl:value-of
																						select="product_id" /></xsl:attribute>
																				</input>
																				<input type="submit" name="submit" value="Delete"></input>
																			</form>

																		</td>
																	</tr>
																</tbody>
															</table>
														</xsl:if>
														<!-- Кнопки удаления , конец -->
													</td>
												</xsl:for-each>
												<td width="20%"></td>
											</tr>
										</tbody>
									</table>
								</p>

								<font color="black">
									Internet shop . Copyright 2010
									<A href="http://www.siteforyou.net">
										<font color="black"> FDIS Center Business Solutions Inc </font>
									</A>
									. All rights reserved
								</font>

								<hr size="" class="netscape4" />



								<strong class="netscape4">
									for user netscape
								</strong>

							</div>

						</td>
						<td bgcolor="#ECEFF8" style="border: 1px solid #ECEFF8"></td>
					</tr>
				</table>


			</body>
		</html>
	</xsl:template>


	<xsl:template name="prList">
		<xsl:param name="position" select="1" />
		<xsl:param name="prCount" />


		<tr>
			<td>
				<div style="height:20px;" class="bacgraundProductHead">
					<table cellSpacing="0" cellPadding="0" width="100%">
						<tbody>
							<tr>
								<td valign="bottom " align="center">
									<front style="color: white;" >
										<b>
											<xsl:if test="document/product_list/product[$position]/amount != '0'">
												<b>
													<xsl:value-of select="document/product_list/product[$position]/name" />
												</b>
											</xsl:if>
											<xsl:if  test="document/product_list/product[$position]/amount = '0'">
												<xsl:value-of select="document/product_list/product[$position]/name" />
											</xsl:if>
										</b>
									</front>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</td>

			<td style="width: 10px; padding-left: 5px">
			</td>

			<td>
				<xsl:if test="($position+1) &lt;= ($prCount)">
					<xsl:if test="document/product_list/product[$position+1]/name != ''">
						<div style="height:20px;" class="bacgraundProductHead">
							<table cellSpacing="0" cellPadding="0" width="100%">
								<tr>
									<td valign="bottom " align="center">
										<FONT style="color: white;" >
											<B>
												<xsl:if
													test="document/product_list/product[$position+1]/amount != '0'">
													<b>
														<xsl:value-of
															select="document/product_list/product[$position+1]/name" />
													</b>
												</xsl:if>
												<xsl:if
													test="document/product_list/product[$position+1]/amount = '0'">
													<xsl:value-of
														select="document/product_list/product[$position+1]/name" />
												</xsl:if>
											</B>
										</FONT>
									</td>
								</tr>
							</table>
						</div>
					</xsl:if>
				</xsl:if>
			</td>

		</tr>
		<tr>
			<td valign="top" class="productBox">

				<div>
					<div class="body">
						<div valign="center" align="center" class="description">

							<table width="298" border="0" cellSpacing="0" cellPadding="2">
								<tbody width="298">

									<xsl:if test="document/product_list/product[$position]/icon != ''">
										<tr>
											<td valign="center" align="center" width="100%" height="100">
												<A>
													<xsl:if test="/document/role_id != 2">
														<xsl:attribute name="href"><xsl:value-of
															select="document/product_list/product[$position]/policy_url" /></xsl:attribute>
													</xsl:if>
													<xsl:if test="/document/role_id = 2">
														<xsl:attribute name="href"><xsl:value-of
															select="document/product_list/product[$position]/policy_url" /></xsl:attribute>
													</xsl:if>

													<IMG alt="" width="290" border="0">
														<xsl:attribute name="src"><xsl:value-of
															select="document/product_list/product[$position]/icon" /></xsl:attribute>
													</IMG>
												</A>
											</td>
										</tr>
									</xsl:if>

									<tr>
										<td width="100%" style="padding: 10px">
											<a>
												<xsl:attribute name="href"><xsl:value-of
													select="document/product_list/product[$position]/policy_url" /></xsl:attribute>
												<xsl:for-each
													select="document/product_list/product[$position]/description/r">
													<xsl:value-of select="." />
												</xsl:for-each>
											</a>
											<br/>
											<a><xsl:attribute name="href">Productlist.jsp?create_site_by_id=<xsl:value-of select="document/product_list/product[$position]/product_id"/></xsl:attribute>To create shop</a><br/>
											
										</td>
									</tr>
								</tbody>
							</table>


						</div>
					</div>
				</div>


			</td>

			<td width="3">
				<br />
			</td>

			<xsl:if test="($position+1) &lt;= ($prCount)">
				<xsl:if test="document/product_list/product[$position+1]/name != ''">
					<td class="productBox">
						<div>
							<div class="body">
								<div align="center" class="description">
									<table width="298" border="0" cellSpacing="0" cellPadding="2">
										<tbody width="298">
											<xsl:if
												test="document/product_list/product[$position+1]/icon != ''">
												<tr>
													<td align="center" width="100%">
														<a>
															<xsl:if test="/document/role_id != 2">
																<xsl:attribute name="href"><xsl:value-of
																	select="document/product_list/product[$position+1]/policy_url" /></xsl:attribute>
															</xsl:if>
															<xsl:if test="/document/role_id = 2">
																<xsl:attribute name="href"><xsl:value-of
																	select="document/product_list/product[$position+1]/policy_url" /></xsl:attribute>
															</xsl:if>

															<img alt="" width="290" border="0">
																<xsl:attribute name="src"><xsl:value-of
																	select="document/product_list/product[$position+1]/icon" /></xsl:attribute>
															</img>
														</a>
													</td>
												</tr>
											</xsl:if>

											<tr>
												<td width="100%" style="padding: 10px">
													<a>
														<xsl:attribute name="href"><xsl:value-of
															select="document/product_list/product[$position+1]/policy_url" /></xsl:attribute>
														<xsl:for-each
															select="document/product_list/product[$position+1]/description/r">
															<xsl:value-of select="." />
														</xsl:for-each>
													</a>
													<br/>
													<a><xsl:attribute name="href">Productlist.jsp?create_site_by_id=<xsl:value-of select="document/product_list/product[$position]/product_id"/></xsl:attribute>To create shop</a><br/>
																							<br />
												</td>
											</tr>
										</tbody>
									</table>


								</div>
							</div>
						</div>

					</td>
				</xsl:if>


				<xsl:if test="document/product_list/product[$position+1]/name = ''">
					<td width="298">
						<br/>
					</td>
				</xsl:if>
			</xsl:if>

		</tr>


		<xsl:choose>

			<!-- Кнопки удаления , начало -->
			<xsl:when test="/document/role_id = '2'">
				<tr>
					<td colspan="3">
						<br />
					</td>
				</tr>
				<tr>
					<td class="buttons-line">

						<form name="product_edit" action="Productlist.jsp" style="float: left">
							<input size="0" autocomplete="off" type="hidden" name="action" 	value="edit"></input>
							<input size="0" autocomplete="off" type="hidden" name="element" value="product"></input>
							<input size="0" autocomplete="off" type="hidden" name="product_id">
								<xsl:attribute name="value"><xsl:value-of
									select="document/product_list/product[$position]/product_id" /></xsl:attribute>
							</input>
							<input type="submit" name="submit" value="Change"></input>
						</form>

						<form name="product_del" action="Productlist.jsp"
							style="float: left; margin-left: 5px">
							<input size="0" autocomplete="off" type="hidden" name="action" 	value="del"></input>
							<input size="0" autocomplete="off" type="hidden" name="product_id">
								<xsl:attribute name="value"><xsl:value-of
									select="document/product_list/product[$position]/product_id" /></xsl:attribute>
							</input>
							<input type="submit" name="submit" value="Delete"></input>
						</form>
					</td>

					<td width="3px"></td>
					<xsl:if test="($position+1) &lt;= ($prCount)">
						<xsl:if
							test="document/product_list/product[$position+1]/product_id != ''">
							<td class="buttons-line">
								<form name="product_edit" action="Productlist.jsp" style="float: left">
									<input size="0" autocomplete="off" type="hidden" name="action" 	value="edit"></input>
									<input size="0" autocomplete="off" type="hidden" name="element" value="product"></input>
									<input size="0" autocomplete="off" type="hidden" name="product_id">
										<xsl:attribute name="value"><xsl:value-of
											select="document/product_list/product[$position+1]/product_id" /></xsl:attribute>
									</input>
									<input type="submit" name="submit" value="Change" style="float: left"></input>
								</form>

								<form name="product_del" action="Productlist.jsp"
									style="float: left; margin-left: 5px">
									<input size="0" autocomplete="off" type="hidden" name="action" 	value="del"></input>
									<input size="0" autocomplete="off" type="hidden" name="product_id"> 
											<xsl:attribute name="value"><xsl:value-of
											select="document/product_list/product[$position+1]/product_id" /></xsl:attribute>
									</input>
									<input type="submit" name="submit" value="Delete" style="float: left"></input>
								</form>
							</td>
						</xsl:if>
					</xsl:if>
				</tr>
			</xsl:when>
			<!-- Кнопки удаления , конец -->

			<!-- for Edit User context Кнопки удаления , начало -->
			<xsl:when test="/document/role_id = '1'">
 			<xsl:variable name="user_id" select="number(/document/owner_user_id)" />
				<xsl:if
					test="(document/product_list/product[$position]/user_id = string(number($user_id))) or (($position+1) &lt;= ($prCount) and (document/product_list/product[$position+1]/user_id = string(number($user_id))))">
					<tr>
						<td colspan="3">
							<br />
						</td>
					</tr>
				</xsl:if>

				<tr>
					<xsl:if test="document/product_list/product[$position]/user_id = string(number($user_id))">

						<td nowrap="nowrap" class="buttons-line">
							<form name="product_edit" action="Productlist.jsp" style="float: left">
								<input size="0" autocomplete="off" type="hidden" name="action"   value="edit"></input>
								<input size="0" autocomplete="off" type="hidden" name="element"  value="edit_biz_info"></input>
								<input size="0" autocomplete="off" type="hidden" name="product_id">
									<xsl:attribute name="value"><xsl:value-of
										select="document/product_list/product[$position]/product_id" /></xsl:attribute>
								</input>
								<input type="submit" name="submit" value="Change"></input>
							</form>

							<form name="product_del" action="Productlist.jsp" 	style="float: left; margin-left: 5px">
								<input size="0" autocomplete="off" type="hidden" name="action" 	value="del"></input>
								<input size="0" autocomplete="off" type="hidden" name="product_id">
									<xsl:attribute name="value"><xsl:value-of
										select="document/product_list/product[$position]/product_id" /></xsl:attribute>
								</input>
								<input type="submit" name="submit" value="Delete"></input>
							</form>
						</td>

					</xsl:if>
					<xsl:if
						test="document/product_list/product[$position]/user_id != string(number($user_id))">
						<td width="280"></td>
					</xsl:if>


					<td width="3px"></td>
 
					<xsl:if test="($position+1) &lt;= ($prCount)">
						<xsl:if
							test="document/product_list/product[$position+1]/user_id = string(number($user_id))">
							<td nowrap="nowrap" class="buttons-line">
								<xsl:if
									test="document/product_list/product[$position+1]/product_id != ''">
									<form name="product_edit" action="Productlist.jsp" style="float: left">
										<input size="0" autocomplete="off" type="hidden" name="action"
											value="edit"></input>
										<input size="0" autocomplete="off" type="hidden" name="element"
											value="edit_biz_info"></input>
										<input size="0" autocomplete="off" type="hidden" name="product_id">
											<xsl:attribute name="value"><xsl:value-of
												select="document/product_list/product[$position+1]/product_id" /></xsl:attribute>
										</input>
										<input type="submit" name="submit" value="Change"></input>
									</form>

									<form name="product_del" action="Productlist.jsp"
										style="float: left; margin-left: 5px">
										<input size="0" autocomplete="off" type="hidden" name="action" 	value="del"></input>
										<input size="0" autocomplete="off" type="hidden" name="product_id">
											<xsl:attribute name="value"><xsl:value-of 
												select="document/product_list/product[$position+1]/product_id" /></xsl:attribute>
										</input>
										<input type="submit" name="submit" value="Delete"></input>
									</form>
								</xsl:if>
							</td>
						</xsl:if>


						<xsl:if
							test="document/product_list/product[$position+1]/user_id != string(number($user_id))">
							<td width="280"></td>
						</xsl:if>
					</xsl:if>
			

			</tr>
			
			</xsl:when>
		</xsl:choose>

		<tr>
			<td>
			</td>

			<td style="width: 10px; padding-left: 5px">
				<br />
			</td>

			<td>
			</td>

		</tr>

		<xsl:if test="$position+2 &lt;= $prCount">
			<xsl:call-template name="prList">
				<xsl:with-param name="position" select="$position+2" />
				<xsl:with-param name="prCount" select="$prCount" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>
