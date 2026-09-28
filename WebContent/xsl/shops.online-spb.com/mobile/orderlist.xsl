<?xml version='1.0' encoding='windows-1251' ?>
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="html" indent="yes" />
	<xsl:output encoding="UTF-8" />
	<xsl:strip-space elements="*" />


	<xsl:template match="/">
		<xsl:variable name="host" select="string(document/host)" />
		<HTML>
			<HEAD>
				<META HTTP-EQUIV="no-cache" />
				<META name="viewport"
					content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=yes" />
				<title>
					<xsl:value-of select="document/title" />
				</title>
				<style type="text/css">
					/* Mobile-fit overrides: the legacy markup below hardcodes desktop
					   pixel widths (960px page wrapper, fixed-width tables/images)
					   which forces the browser to render at desktop scale and shrink
					   it, producing a sideways-shifted/clipped look on phones. These
					   overrides make those containers fluid. */
					* { box-sizing: border-box; }
					body, html { overflow-x: hidden; max-width: 100%; }
					.main {
						width: 100% !important;
						max-width: 960px !important;
						margin: 0 auto !important;
					}
					.width {
						width: 100% !important;
						max-width: 670px !important;
						box-sizing: border-box;
					}
					.indent {
						width: 100% !important;
						max-width: 960px !important;
						box-sizing: border-box;
					}
					.article-bg, .article-left, .article-right, .comp-cont {
						width: 100% !important;
						max-width: 100% !important;
						box-sizing: border-box;
					}
					img { max-width: 100%; height: auto; }
					#container {
						margin: 0 auto !important;
						max-width: 100% !important;
						padding: 0 15px !important;
						box-sizing: border-box;
					}
					#footer {
						height: auto !important;
						min-height: 74px;
					}
					/* Hard global reset: force every table/td/div under #content to
					   respect the viewport width instead of expanding to fit
					   unbreakable pixel-width content. */
					#content, #content table, #content tbody, #content tr, #content div {
						max-width: 100% !important;
						width: 100% !important;
						box-sizing: border-box !important;
						word-wrap: break-word !important;
						overflow-wrap: break-word !important;
						word-break: break-word !important;
						table-layout: fixed !important;
					}
					#content td {
						max-width: 100% !important;
						box-sizing: border-box !important;
						word-wrap: break-word !important;
						overflow-wrap: break-word !important;
						word-break: break-word !important;
					}
					#content img {
						max-width: 100% !important;
						height: auto !important;
					}
					/* Categories dropdown menu: categories live behind a hamburger-style
					   button (first item in the bar) that opens a panel underneath it.
					   NOTE: the external menu.css hides every div inside #sddm
					   (visibility:hidden, position:absolute) for its own hover submenus,
					   so all rules below use higher specificity plus !important to win. */
					#sddm li.item-categories {
						position: relative !important;
						height: 100% !important;
					}
					#sddm li.item-categories > a {
						cursor: pointer !important;
						position: static !important;
						display: block !important;
						width: 60px !important;
						height: 100% !important;
						min-height: 100% !important;
						padding: 0 !important;
						margin: 0 !important;
						line-height: normal !important;
						box-sizing: border-box !important;
						background: transparent !important;
					}
					.hamburger-icon {
						display: block !important;
						position: absolute !important;
						top: 50% !important;
						left: 50% !important;
						transform: translate(-50%, -50%) !important;
						width: 24px !important;
						height: 18px !important;
						margin: 0 !important;
						background:
							linear-gradient(#FFFFFF, #FFFFFF) left top / 100% 3px no-repeat,
							linear-gradient(#FFFFFF, #FFFFFF) left 50% / 100% 3px no-repeat,
							linear-gradient(#FFFFFF, #FFFFFF) left bottom / 100% 3px no-repeat !important;
					}
					.hamburger-icon i {
						display: none !important;
					}
					#sddm li.item-categories div.categories-panel {
						display: none;
						visibility: visible !important;
						position: absolute;
						top: 100%;
						left: 0;
						margin: 0;
						padding: 0 !important;
						background: #fff !important;
						border: 1px solid #ccc !important;
						min-width: 250px;
						max-width: 90vw;
						max-height: 70vh;
						overflow-y: auto;
						z-index: 9999 !important;
						box-shadow: 0 4px 12px rgba(0,0,0,0.25);
						text-align: left;
					}
					#sddm li.item-categories div.categories-panel.open {
						display: block !important;
					}
					#sddm li.item-categories div.categories-panel div.cat-block {
						display: block !important;
						visibility: visible !important;
						position: static !important;
						margin: 0 !important;
						padding: 0 !important;
						background: #fff !important;
						border: none !important;
						border-bottom: 1px solid #eee !important;
					}
					#sddm li.item-categories div.categories-panel div.cat-block:last-child {
						border-bottom: none !important;
					}
					#sddm li.item-categories div.categories-panel div.cat-sub {
						display: flex !important;
						visibility: visible !important;
						position: static !important;
						flex-direction: column;
						margin: 0 !important;
						padding: 0 14px 8px 26px !important;
						background: #fff !important;
						border: none !important;
					}
					#sddm li.item-categories div.categories-panel .cat-block > a {
						display: block !important;
						position: static !important;
						width: auto !important;
						white-space: normal !important;
						margin: 0 !important;
						padding: 10px 14px !important;
						font: bold 13px arial !important;
						color: #333 !important;
						background: #fff !important;
						text-align: left !important;
						text-decoration: none !important;
					}
					#sddm li.item-categories div.categories-panel .cat-block > a:hover {
						background: #f0f0f0 !important;
						color: #000 !important;
					}
					#sddm li.item-categories div.categories-panel .cat-sub a {
						display: block !important;
						position: static !important;
						width: auto !important;
						white-space: normal !important;
						margin: 0 !important;
						padding: 5px 0 !important;
						font: 13px arial !important;
						color: #555 !important;
						background: #fff !important;
						text-align: left !important;
						text-decoration: none !important;
					}
					#sddm li.item-categories div.categories-panel .cat-sub a:hover {
						color: #000 !important;
						text-decoration: underline !important;
					}
				</style>

				<LINK rel="stylesheet" type="text/css">
					<xsl:attribute name="href"><xsl:value-of
						select="concat('xsl/',$host,'/template.css')" /></xsl:attribute>
				</LINK>
				<LINK rel="stylesheet" type="text/css">
					<xsl:attribute name="href"><xsl:value-of
						select="concat('xsl/',$host,'/constant.css')" /></xsl:attribute>
				</LINK>


				<link type="text/css" rel="stylesheet">
					<xsl:attribute name="href"><xsl:value-of
						select="concat('xsl/',$host,'/calendar.css')" /></xsl:attribute>
				</link>
				<script type="text/javascript">
					<xsl:attribute name="src"><xsl:value-of
						select="concat('xsl/',$host,'/jquery_v2009.js')" /></xsl:attribute>
				</script>


				<link rel="stylesheet" type="text/css" media="screen">
					<xsl:attribute name="href"><xsl:value-of
						select="concat('xsl/',$host,'/menu.css')" /></xsl:attribute>
				</link>
				<script type="text/javascript">
					<xsl:attribute name="src"><xsl:value-of
						select="concat('xsl/',$host,'/menu.js')" /></xsl:attribute>
				</script>

				<link rel="stylesheet" type="text/css" media="screen">
					<xsl:attribute name="href"><xsl:value-of
						select="concat('xsl/',$host,'/showiframe.css')" /></xsl:attribute>
				</link>
				<script type="text/javascript">
					<xsl:attribute name="src"><xsl:value-of
						select="concat('xsl/',$host,'/showiframe.js')" /></xsl:attribute>
				</script>


			</HEAD>


			<body id="body">
				<div class="main" style="background-color: #E7E7DF">
					<IMG alt="Logo">
						<xsl:attribute name="src"><xsl:value-of
							select="concat('xsl/',$host,'/images/logo.gif')" /></xsl:attribute>
					</IMG>
				</div>
				<div id="gradient">
					<div style="width: 960px" class="main">
						<div id="top">

							<div id="topmenu">
								<div class="module-topmenu">

									<!-- <ul class="menu-nav"> -->
									<ul id="sddm">

										<!-- Categories used to be listed individually in the top bar;
										     they now live behind a hamburger-style menu button (first item
										     in the bar) that opens a dropdown panel with all categories
										     and subcategories. -->
										<li class="item-categories">
											<a href="#" id="categoriesToggle" onclick="return toggleCategoriesMenu(event);">
												<span class="hamburger-icon"><i></i><i></i><i></i></span>
											</a>
											<div id="categoriesPanel" class="categories-panel">
												<xsl:for-each select="document/menu/menu-item">
													<xsl:if test="item != ''">
														<xsl:if test="code != '-1'">
															<xsl:if test="code != '-2'">
																<xsl:if test="code != '-3'">
																	<div class="cat-block">
																		<a>
																			<xsl:attribute name="HREF"><xsl:value-of select="url" /></xsl:attribute>
																			<xsl:value-of select="item" />
																		</a>
																		<xsl:if test="count(submenu-item) &gt; 0">
																			<div class="cat-sub">
																				<xsl:for-each select="submenu-item">
																					<a>
																						<xsl:attribute name="HREF"><xsl:value-of select="suburl" /></xsl:attribute>
																						<xsl:value-of select="subitem" />
																					</a>
																				</xsl:for-each>
																			</div>
																		</xsl:if>
																	</div>
																</xsl:if>
															</xsl:if>
														</xsl:if>
													</xsl:if>
												</xsl:for-each>
											</div>
										</li>

										<li class="item53">
											<a href="Productlist.jsp?catalog_id=-2">
												<span>Home page</span>
											</a>
										</li>
										
										<LI class="item18">
											<A href="Authorization.jsp?Login=">
												<SPAN>Registration</SPAN>
											</A>
										</LI>

										<LI>
											<A href="Order.jsp">
												<IMG border="0" height="40" width="40" style="margin: -10px;">
													<xsl:attribute name="src"><xsl:value-of
														select="concat('xsl/',$host,'/images/empty-cart-light.png')" /></xsl:attribute>
												</IMG>
											</A>
										</LI>


										<xsl:if test="document/role_id != 0">
											<LI>
												<A href="Productlist.jsp?action=logoff">
													<SPAN>
														<svg xmlns="http://www.w3.org/2000/svg" height="24px"
															viewBox="0 -960 960 960" width="24px" fill="#F3F3F3">
															<path
																d="M212-86q-53 0-89.5-36.5T86-212v-536q0-53 36.5-89.5T212-874h276v126H212v536h276v126H212Zm415-146-88-89 96-96H352v-126h283l-96-96 88-89 247 248-247 248Z" />
															<title id="title">Exit</title>
														</svg>
													</SPAN>
												</A>
											</LI>
										</xsl:if>



									</ul>
								</div>
							</div>
						</div>




						<DIV class="indent" style="width: 960px">
							<DIV class="moduletable">
								<TABLE class="who_is_online" style="WIDTH: auto"
									align="right">
									<TBODY>
										<TR>
											<TD>
												<xsl:if test="document/login != ''">   <!-- показывать если есть логин -->
													<B>User</B>
													<a href="Authorization.jsp"
														style="margin-left: 5px; text-decoration: none">

														<xsl:if test="document/login = 'user'">   <!-- показывать если нет логина -->
															<font class="user0">
																<xsl:value-of select="document/login" />
															</font>
														</xsl:if>

														<xsl:if test="document/login != 'user'">   <!-- показывать если есть логин -->
															<xsl:if test="document/role_id = 1"> <!-- оранжевый если юзер -->
																<font class="user1">
																	<xsl:value-of select="document/login" />
																</font>
															</xsl:if>

															<xsl:if test="document/role_id = 2"><!-- красный если админ -->
																<font class="user2">
																	<xsl:value-of select="document/login" />
																</font>
															</xsl:if>
														</xsl:if>

													</a>
												</xsl:if>
											</TD>
										</TR>
									</TBODY>
								</TABLE>
							</DIV>
						</DIV>



						<div id="mid">
							<div class="mid-left">
								<div class="mid-right">

									

									<div id="breadcrumb">
										<div class="space">
											<span class="breadcrumbs pathway">
												<xsl:if test="document/role_id != 0">
													<xsl:if
														test="count(document/parent/parent-item) != 1">

														<a href="Productlist.jsp?catalog_id=-2" class="catalog"
															alt="To return back  to the top of Categorization"
															title="To return back  to the top of Categorization">
															<U>
																<font size="2">All Categories</font>
															</U>
														</a>
														&#160; &#187;

														<xsl:for-each
															select="document/parent/parent-item">
															<xsl:if test="code != '-2'">
																<A>
																	<xsl:attribute name="HREF"><xsl:value-of
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
												</xsl:if>

											</span>
										</div>
									</div>


								</div>
							</div>
						</div>

						<div id="content">
							<div class="width">



								<div id="container"
									style="margin: 0px 21px;  margin-top: ;  margin-right: ; margin-bottom: ;  margin-left:">
									<div class="comp-cont">
										<table class="blog" cellpadding="0" cellspacing="0">
											<tr>
												<td valign="top">
													<div class="article-bg">
														<div class="article-left">
															<div class="article-right">
																<DIV style="padding-left: 20px; ">
																	<h3 style="padding-left: 20px; font-size: 17px">The list of all your orders</h3>
																	<br />
																	<DIV>
																		<table border="0" class="orders"
																			style="width:600px">
																			<colgroup>
																				<col width="50px" />
																				<col width="80" />
																				<col width="65" />
																				<col width="70" />
																				<col width="40" />
																			</colgroup>
																			<thead>
																				<TR>
																					<TD class="order_head">Order</TD>
																					<TD class="order_head">Sum</TD>
																					<TD class="order_head">Date</TD>
																					<TD class="order_head">Status</TD>
																					<TD class="order_head"></TD>
																				</TR>
																			</thead>
																			<tbody>
																				<xsl:for-each
																					select="document/list/order">
																					<TR>
																						<TD>
																							N
																							<xsl:value-of select="order_id" />
																						</TD>

																						<TD>
																							<xsl:value-of select="end_amount" />
																							/
																							<xsl:value-of
																								select="currency_lable" />
																						</TD>

																						<TD>
																							<xsl:value-of select="cdate" />
																						</TD>

																						<TD>
																							[
																							<xsl:value-of
																								select="paystatus_lable" />
																							]
																						</TD>

																						<TD>
																							<FORM name="order" action="Order.jsp"
																								method="POST">
																								<INPUT SIZE="0" AUTOCOMPLETE="off"
																									TYPE="HIDDEN" NAME="order_id">
																									<xsl:attribute name="value"><xsl:value-of
																										select="order_id" /></xsl:attribute>
																								</INPUT>
																								<INPUT TYPE="submit" 
																									name="submit" value="Order"></INPUT>
																							</FORM>
																						</TD>

																					</TR>
																				</xsl:for-each>
																				<TR>
																					<TD colspan="5" align="center"
																						class="none_border">
																						<br />
																						<!-- <a><xsl:attribute name="HREF"><xsl:value-of select="document/prev"/></xsl:attribute> 
																							<IMG height="15" alt="Back" title="Back" src="" width="15" border="0"><xsl:attribute 
																							name="src"><xsl:value-of select="concat('xsl/',$host,'/images/previous.gif')"/></xsl:attribute></IMG> 
																							</a> <a><xsl:attribute name="HREF"><xsl:value-of select="document/next"/></xsl:attribute> 
																							<IMG height="15" alt="The following" title="The following" src="" width="15" 
																							border="0"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/next.gif')"/></xsl:attribute></IMG> 
																							</a> -->
																					</TD>
																				</TR>
																				<tr>
																					<td class="none_border">
																						<span class="next">
																							<a HREF="#" onClick="javascript:history.back()">
																								<strong>
																									Back
																								</strong>
																							</a>
																						</span>
																					</td>
																				</tr>
																			</tbody>
																		</table>
																	</DIV>
																</DIV>
															</div>
														</div>
													</div>
												</td>
											</tr>


										</table>

									</div>
								</div>



							</div>
						</div>
					</div>
				</div>


				<DIV id="footer">
					<DIV class="main">
						<div class="space">
							<font color="White">
								 Copyright 2026
								<A HREF="http://www.cmsmanhattan.com">
									<font color="White"> CMS Manhattan </font>
								</A>
								. All rights reserved
							</font>
						</div>
					</DIV>
				</DIV>

				<div id="overlay" style="display:none"></div>
				<div id="dialog" style="display:none">
					<iframe id="iframe" src="about:blank" style="border:0"></iframe>
					<button onclick="closeDialog()">Close</button>
				</div>

				<script type="text/javascript">
					// Categories dropdown menu: toggles the panel that now holds all the
					// categories that used to be listed individually in the top bar.
					// Inline styles are set as well because the external menu.css forcibly
					// hides every div inside #sddm for its own hover submenus.
					function openCategoriesPanel(panel) {
						panel.classList.add('open');
						panel.style.display = 'block';
						panel.style.visibility = 'visible';
						var toggle = document.getElementById('categoriesToggle');
						var icon = null;
						if (toggle) { icon = toggle.querySelector('.hamburger-icon'); }
						var ref = icon ? icon : toggle;
						if (ref) {
							var r = ref.getBoundingClientRect();
							panel.style.position = 'fixed';
							panel.style.top = Math.round(r.bottom + 14) + 'px';
							panel.style.marginTop = '0';
							if (window.innerWidth &lt;= 768) {
								panel.style.left = '0px';
								panel.style.width = '100vw';
								panel.style.maxWidth = '100vw';
							} else {
								panel.style.left = Math.max(0, Math.round(r.left - 10)) + 'px';
								panel.style.width = 'auto';
							}
						}
					}
					function closeCategoriesPanel(panel) {
						panel.classList.remove('open');
						panel.style.display = 'none';
						panel.style.visibility = 'hidden';
					}
					function toggleCategoriesMenu(e) {
						if (e) { e.preventDefault(); e.stopPropagation(); }
						var panel = document.getElementById('categoriesPanel');
						if (!panel) return false;
						if (panel.classList.contains('open')) { closeCategoriesPanel(panel); }
						else { openCategoriesPanel(panel); }
						return false;
					}

					document.addEventListener('click', function (e) {
						var panel = document.getElementById('categoriesPanel');
						var toggle = document.getElementById('categoriesToggle');
						if (!panel || !toggle) return;
						if (panel.classList.contains('open') &amp;&amp; !panel.contains(e.target) &amp;&amp; !toggle.contains(e.target)) {
							closeCategoriesPanel(panel);
						}
					});
				</script>

			</body>
		</HTML>
	</xsl:template>
</xsl:stylesheet>
