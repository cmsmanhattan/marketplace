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
		<xsl:variable name="user_id"
			select="number(/document/owner_user_id)" />
		<xsl:variable name="role" select="document/role_id" />
		<xsl:variable name="virtual_host"
			select="document/virtual_host" />
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
					.article_indent {
						margin-left: -60px !important;
					}
					.bin {
						margin-left: 40px !important;
					}
					/* Delivery address block: the address container was narrowed to
					   200px, but the Checkout button's TD kept its old padding-left
					   (meant for a much wider desktop layout), pushing it past the
					   container edge and overlapping the Clean button next to it. */
					.regbut td[style*="padding-left: 180px"] {
						padding-left: 0 !important;
						width: auto !important;
					}
					.address .regbut {
						width: 100% !important;
						table-layout: auto !important;
					}
					.address .regbut TR {
						display: flex !important;
						flex-wrap: wrap !important;
						justify-content: flex-end !important;
						gap: 8px !important;
					}
					.address .regbut TD {
						display: block !important;
						padding-left: 0 !important;
						width: auto !important;
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
					/* "My Account" slide-panel: moved out of the page body (where it sat
					   in a narrow desktop sidebar column) into the top bar, right after
					   "Registration". Opens the same way as the categories menu. menu.css
					   forcibly hides every div/a nested inside #sddm and styles bare
					   li/a with dark nav-bar colors, so contents are force-reset below. */
					#sddm li.item-account {
						position: relative !important;
						height: 100% !important;
					}
					#sddm li.item-account > a {
						cursor: pointer !important;
						position: relative !important;
						top: -14px !important;
						display: flex !important;
						align-items: center !important;
						width: auto !important;
						height: 100% !important;
						padding: 0 12px !important;
						margin: 0 !important;
						color: #fff !important;
						font: bold 13px arial !important;
						text-decoration: none !important;
						white-space: nowrap !important;
						box-sizing: border-box !important;
						background: transparent !important;
					}
					#sddm li.item-account div.categories-panel {
						display: none;
						visibility: visible !important;
						position: absolute;
						top: 100%;
						left: 0;
						margin: 0;
						padding: 22px !important;
						background: #fff !important;
						border: 1px solid #ccc !important;
						border-radius: 8px !important;
						width: 280px !important;
						min-width: 240px;
						max-width: 92vw;
						max-height: 70vh;
						overflow-y: auto;
						z-index: 9999 !important;
						box-shadow: 0 6px 20px rgba(0,0,0,0.3);
						text-align: left;
						box-sizing: border-box;
					}
					#sddm li.item-account div.categories-panel.open {
						display: block !important;
					}
					#sddm li.item-account div.categories-panel * {
						visibility: visible !important;
						position: static !important;
						float: none !important;
						height: auto !important;
						min-height: 0 !important;
						max-width: none !important;
						line-height: normal !important;
						text-indent: 0 !important;
					}
					#sddm li.item-account div.categories-panel a.panel-close {
						display: block !important;
						position: absolute !important;
						top: 10px !important;
						right: 12px !important;
						width: 26px !important;
						height: 26px !important;
						line-height: 26px !important;
						text-align: center !important;
						font: bold 18px arial !important;
						color: #888 !important;
						background: transparent !important;
						text-decoration: none !important;
						border-radius: 50% !important;
					}
					#sddm li.item-account div.categories-panel a.panel-close:hover {
						color: #333 !important;
						background: #f0f0f0 !important;
					}
					#sddm li.item-account div.categories-panel H3 {
						display: block !important;
						font-size: 18px !important;
						font-weight: bold !important;
						color: #333 !important;
						margin: 0 22px 14px 0 !important;
						padding: 0 !important;
						background: transparent !important;
					}
					#sddm li.item-account div.categories-panel div {
						display: block !important;
						width: auto !important;
						margin: 0 !important;
						padding: 0 !important;
						background: transparent !important;
						border: none !important;
					}
					#sddm li.item-account div.categories-panel div.cab {
						margin: 0 0 10px 0 !important;
					}
					#sddm li.item-account div.categories-panel div.cab:last-child {
						margin-bottom: 0 !important;
						padding-top: 10px !important;
						border-top: 1px solid #eee !important;
						color: #333 !important;
						font: 13px arial !important;
					}
					#sddm li.item-account div.categories-panel div.cab A {
						display: inline !important;
						color: #072f3a !important;
						background: transparent !important;
						font: 13px arial !important;
						text-decoration: underline !important;
					}
					#sddm li.item-account div.categories-panel div.cab A:hover {
						color: #041a20 !important;
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
				<SCRIPT type="text/javascript">
					<xsl:attribute name="src"><xsl:value-of
						select="concat('xsl/',$host,'/caption.js')" /></xsl:attribute>
				</SCRIPT>
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
				<link rel="stylesheet" type="text/css" media="screen">
					<xsl:attribute name="href"><xsl:value-of
						select="concat('xsl/',$host,'/slider.css')" /></xsl:attribute>
				</link>
				<script type="text/javascript">
					<xsl:attribute name="src"><xsl:value-of
						select="concat('xsl/',$host,'/slider.js')" /></xsl:attribute>
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

										<!-- My Account: moved here from the narrow desktop sidebar
										     column, where it didn't fit the mobile layout. Opens as a
										     slide-down panel, same mechanism as the categories menu. -->
										<li class="item-account">
											<a href="#" id="accountToggle" onclick="return toggleAccountMenu(event);">
												<span>My Account</span>
											</a>
											<div id="accountPanel" class="categories-panel">
												<a href="#" class="panel-close" onclick="return closeAccountMenu(event);">&#215;</a>
												<H3>My Account</H3>
												<div class="cab">
													<a>
														<xsl:attribute name="HREF"><xsl:value-of select="document/to_order_hist" /></xsl:attribute>
														All orders
													</a>
												</div>
												<div class="cab">
													<a>
														<xsl:attribute name="HREF"><xsl:value-of select="document/to_order" /></xsl:attribute>
														The current order
													</a>
												</div>
												<div class="cab">
													<a href="Productlist.jsp?catalog_id=-2">Contunue shopping</a>
												</div>
												<div class="cab">
													<a>
														<xsl:attribute name="HREF"><xsl:value-of select="document/to_account_history" /></xsl:attribute>
														Payments
													</a>
												</div>
												<div class="cab">
													<b>Available funds: </b>
													<xsl:value-of select="document/balans" />
												</div>
											</div>
										</li>

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
																<xsl:if test="document/message != ''">
																	<p align="center">
																		>
																		<font color="red">
																			<xsl:value-of select="document/message" />
																		</font>
																	</p>
																	<br />
																	<br />
																</xsl:if>
																<DIV style="padding-left: 90px">
																	<h3 style="padding-left: 2px; font-size: 17px">
																		Order &#8470;-
																		<xsl:value-of select="document/order_id" />
																	</h3>
																	<DIV class="article_indent">
																		<table class="blog" cellpadding="0"
																			cellspacing="0">
																			<tbody>
																				<tr>
																					<td valign="top">
																						<TABLE cellSpacing="0" cellPadding="0"
																							width="100%">
																							<TBODY>
																								<TR>
																									<TD vAlign="top">
																										<div style="width: 100%">
																											<TABLE>
																												<TR>
																													<TD>
																														<DIV style="padding-bottom: 0px;">
																															<br />

																															<div class="binTytle">
																																<h3>Your cart</h3>
																															</div>


																															<DIV style="padding-bottom: 0px;">
																																<xsl:if
																																	test="document/quantity_product = '0'">
																																	Empty cart
																																</xsl:if>
																																<xsl:if
																																	test="document/quantity_product != '0'">
																																	<xsl:if
																																		test="document/empty_page = 'true'">
																																		On this page there is nothing.
																																	</xsl:if>
																																	<xsl:if
																																		test="document/empty_page != 'true'">

																																		<DIV class="bin">
																																			<xsl:for-each
																																				select="document/list/product">
																																				<table class="order">
																																					<tbody>
																																						<tr>
																																							<td>
																																								<IMG height="40" alt=""
																																									width="40" border="0"
																																									style="margin-right: 10px">
																																									<xsl:attribute
																																										name="src"><xsl:value-of
																																										select="icon" /></xsl:attribute>
																																								</IMG>
																																							</td>
																																							<td align="left" width="800">
																																								<xsl:value-of
																																									select="name" />
																																								:
																																								<xsl:value-of
																																									select="quantity" />
																																								x
																																								<xsl:value-of
																																									select="amount" />
																																								(
																																								<xsl:value-of
																																									select="currency/description" />
																																								)
																																							</td>
																																							<td align="left"
																																								valign="center" width="100">
																																								<xsl:if
																																									test="file_exist != ''">
																																									<A>
																																										<xsl:attribute
																																											name="href"><xsl:value-of
																																											select="product_url" /></xsl:attribute>
																																										Download file
																																									</A>
																																								</xsl:if>
																																							</td>
																																							<td align="right"
																																								style="padding-right: 15px">
																																								<FORM name="order_del"
																																									action="Order.jsp"
																																									method="POST">
																																									<INPUT SIZE="0"
																																										AUTOCOMPLETE="off"
																																										TYPE="HIDDEN" NAME="action"
																																										VALUE="del"></INPUT>
																																									<INPUT SIZE="0"
																																										AUTOCOMPLETE="off"
																																										TYPE="HIDDEN" NAME="position">
																																										<xsl:attribute
																																											name="value"><xsl:value-of
																																											select="basket_id" /></xsl:attribute>
																																									</INPUT>
																																									<INPUT size="12"
																																										TYPE="submit" class="button"
																																										name="submit" value="Delete"></INPUT>
																																								</FORM>
																																							</td>
																																						</tr>
																																					</tbody>
																																				</table>
																																			</xsl:for-each>
																																		</DIV>
																																	</xsl:if>   <!-- наличие товаров в корзине на этой странице -->

																																</xsl:if>  <!-- наличие товаров в корзине -->
																															</DIV>
																															<DIV
																																style="border: 1px dashed black; width: 400px;">
																																<DIV>
																																	<DIV style="float: left">
																																		<A>
																																			<xsl:attribute
																																				name="HREF"><xsl:value-of
																																				select="document/prev" /></xsl:attribute>
																																			<IMG height="15" alt="back"
																																				title="back" src="" width="15"
																																				border="0">
																																				<xsl:attribute
																																					name="src"><xsl:value-of
																																					select="concat('xsl/',$host,'/images/previous.gif')" /></xsl:attribute>
																																			</IMG>
																																		</A>
																																	</DIV>
																																	<DIV style="float: right">
																																		<A>
																																			<xsl:attribute
																																				name="HREF"><xsl:value-of
																																				select="document/next" /></xsl:attribute>
																																			<IMG height="15" alt="next"
																																				title="next" src="" width="15"
																																				border="0">
																																				<xsl:attribute
																																					name="src"><xsl:value-of
																																					select="concat('xsl/',$host,'/images/next.gif')" /></xsl:attribute>
																																			</IMG>
																																		</A>
																																	</DIV>
																																</DIV>
																																<DIV style="text-align: center">
																																	<xsl:if
																																		test="document/empty_page = 'true'">
																																		<font size="2"
																																			style="color:black">On this page there is nothing.</font>
																																	</xsl:if>

																																	<xsl:if
																																		test="document/empty_page != 'true'">
																																		<xsl:variable
																																			name="offset"
																																			select="number(substring(document/next,18))" />

																																		<font size="2"
																																			style="color:black">
																																			Listing
																																			<b>
																																				<xsl:value-of
																																					select="$offset+(-9)" />
																																				-
																																				<xsl:if
																																					test="$offset &lt; document/quantity_product">
																																					<xsl:value-of
																																						select="$offset" />
																																				</xsl:if>
																																				<xsl:if
																																					test="$offset &gt; document/quantity_product">
																																					<xsl:value-of
																																						select="document/quantity_product" />
																																				</xsl:if>
																																			</b>

																																			from
																																			<b>
																																				<xsl:value-of
																																					select="document/quantity_product" />
																																			</b>
																																		</font>
																																	</xsl:if>
																																</DIV>
																															</DIV>

																														</DIV>
																														<DIV>


																															<br />
																															<!-- 
																															<div class="binTytle">
																																<h3>Additional expenses</h3>
																															</div>
																															 -->
																															<br />
																															<h3>Additional expenses</h3>
																															<br />

																															<DIV
																																style="width: 600px;padding-bottom: 0px;">
																																<form ACTION="Order.jsp"
																																	method="POST">
																																	<table class="orderInfo">
																																		<colgroup>
																																			<col width="140" />
																																			<col width="70" />
																																			<col width="60" />
																																		</colgroup>
																																		<tbody>
																																			<tr>
																																				<td bgColor="#EFEFEF"
																																					align="center" vAlign="top">Name</td>
																																				<td bgColor="#EFEFEF"
																																					align="center" vAlign="top">Sum</td>
																																				<td bgColor="#EFEFEF"
																																					align="center" vAlign="top">Currency</td>
																																			</tr>
																																			<xsl:if
																																				test="document/admin/post_manager = ''">
																																				<tr>
																																					<td colspan="3" width="500"
																																						align="left" vAlign="top">
																																						<INPUT SIZE="40"
																																							AUTOCOMPLETE="off" TYPE="HIDDEN"
																																							NAME="order_paystatus" VALUE="1"></INPUT>
																																					</td>
																																				</tr>
																																			</xsl:if>
																																			<tr>
																																				<td align="left" vAlign="top">Order
																																					sum:</td>
																																				<td align="right" vAlign="top">
																																					<STRONG>
																																						<xsl:value-of
																																							select="document/order_amount" />
																																					</STRONG>
																																				</td>
																																				<td align="left" vAlign="top"
																																					style="padding-left: 3px">
																																					<xsl:value-of
																																						select="document/currency_lable" />
																																				</td>
																																			</tr>
																																			<tr>
																																				<td align="left" vAlign="top">Sum
																																					for delivery:</td>
																																				<td align="right" vAlign="top">
																																					<STRONG>
																																						<xsl:value-of
																																							select="document/delivery_amoun" />
																																					</STRONG>
																																				</td>
																																				<td align="left" vAlign="top"
																																					style="padding-left: 3px">
																																					<xsl:value-of
																																						select="document/currency_lable" />
																																				</td>
																																			</tr>
																																			<tr>
																																				<td align="left" vAlign="top">
																																					<b>Total:</b>
																																				</td>
																																				<td align="right" vAlign="top">
																																					<STRONG>
																																						<xsl:value-of
																																							select="document/order_end_amount" />
																																					</STRONG>
																																				</td>
																																				<td align="left" vAlign="top"
																																					style="padding-left: 3px">
																																					<xsl:value-of
																																						select="document/currency_lable" />
																																				</td>
																																			</tr>
																																			<xsl:if
																																				test="not(document/role_id = 2 or document/role_id = 3 or document/role_id = 4)">
																																				<tr>
																																					<td align="left" vAlign="top">Payment
																																						status:</td>
																																					<td align="left" vAlign="top">
																																						<STRONG>
																																							<xsl:value-of
																																								select="document/paystatus_lable" />
																																						</STRONG>
																																					</td>
																																					<td align="left" vAlign="top"></td>
																																				</tr>

																																				<tr>
																																					<td align="left" vAlign="top">Order
																																						status:</td>
																																					<td align="left" vAlign="top">
																																						<STRONG>
																																							<xsl:value-of
																																								select="document/order_status_lable" />
																																						</STRONG>
																																					</td>
																																					<td align="left" vAlign="top"></td>
																																				</tr>
																																			</xsl:if>


																																			<xsl:if
																																				test="(document/role_id = 2 or document/role_id = 3 or document/role_id = 4)">
																																				<tr>
																																					<td align="left" vAlign="top">Change
																																						the payment status: </td>
																																					<td colspan="2" align="left"
																																						vAlign="top">
																																						<SELECT
																																							NAME="order_paystatus"
																																							onChange="javascript:this.form.submit()"
																																							style="width: 170px">
																																							<xsl:for-each
																																								select="document/paystatus/paystatus-item">
																																								<OPTION>
																																									<xsl:attribute
																																										name="value"><xsl:value-of
																																										select="code" /></xsl:attribute>
																																									<xsl:if
																																										test="code = selected">
																																										<xsl:attribute
																																											name="SELECTED">SELECTED</xsl:attribute>
																																									</xsl:if>
																																									<xsl:value-of
																																										select="item" />
																																								</OPTION>
																																							</xsl:for-each>
																																						</SELECT>
																																					</td>
																																				</tr>
																																			</xsl:if>


																																			<xsl:if
																																				test="(document/role_id = 2 or document/role_id = 3 or document/role_id = 4)">
																																				<tr>
																																					<td align="left" vAlign="top">To
																																						change the order status: </td>
																																					<td colspan="2" align="left"
																																						vAlign="top">
																																						<SELECT NAME="order_status"
																																							onChange="javascript:this.form.submit()"
																																							style="width: 170px">
																																							<xsl:for-each
																																								select="document/deliverystatus/deliverystatus-item">
																																								<OPTION>
																																									<xsl:attribute
																																										name="value"><xsl:value-of
																																										select="code" /></xsl:attribute>
																																									<xsl:if
																																										test="code = selected">
																																										<xsl:attribute
																																											name="SELECTED">SELECTED</xsl:attribute>
																																									</xsl:if>
																																									<xsl:value-of
																																										select="item" />
																																								</OPTION>
																																							</xsl:for-each>
																																						</SELECT>
																																					</td>
																																				</tr>
																																			</xsl:if>
																																		</tbody>
																																	</table>
																																	<INPUT SIZE="0"
																																		AUTOCOMPLETE="off" TYPE="HIDDEN"
																																		NAME="action" VALUE="status" />
																																</form>
																															</DIV>

																														</DIV>


																														<DIV>
																															<br />
																														</DIV>
																														<DIV>


																															<br />
																															<!-- 
																															<div class="binTytle"  >
																																<h3>Delivery address</h3>
																															</div>
																															 -->
																															<h3>Delivery address</h3>
																															<br />
																															
																															<DIV
																																style="width: 200px;padding-bottom: 0px;  "
																																class="address">
																																<form method="post"
																																	ACTION="Order.jsp" id="addressForm">
																																	<INPUT SIZE="40"
																																		AUTOCOMPLETE="off" TYPE="HIDDEN"
																																		NAME="order_paystatus">
																																		<xsl:attribute
																																			name="value"><xsl:value-of
																																			select="document/order_paystatus" /></xsl:attribute>
																																	</INPUT>
																																	<TABLE class="contentpane aut"
																																		cellSpacing="0" cellPadding="0"
																																		border="0">
																																		<colgroup>
																																			<col width="60px" />
																																			<col width="200px" />
																																		</colgroup>
																																		<TR>
																																			<TD>Country*</TD>
																																			<TD align="left">
																																				<SELECT NAME="country_id"
																																					onChange="doChangeCity('country_id', this.value)"
																																					id="country_id">
																																					<xsl:for-each
																																						select="document/country/country-item">
																																						<OPTION>
																																							<xsl:attribute
																																								name="value">
																	<xsl:value-of select="code" />
																</xsl:attribute>
																																							<xsl:if
																																								test="code = selected">
																																								<xsl:attribute
																																									name="SELECTED">SELECTED</xsl:attribute>
																																							</xsl:if>
																																							<xsl:value-of
																																								select="item" />
																																						</OPTION>
																																					</xsl:for-each>
																																				</SELECT>
																																			</TD>
																																		</TR>
																																		<TR>
																																			<TD>City*</TD>
																																			<TD align="left">
																																				<SELECT NAME="city_id"
																																					id="city_id">
																																					<xsl:for-each
																																						select="document/city/city-item">
																																						<OPTION>
																																							<xsl:attribute
																																								name="value">
																	    	<xsl:value-of select="code" />
																	 </xsl:attribute>
																																							<xsl:if
																																								test="code = selected">
																																								<xsl:attribute
																																									name="SELECTED">SELECTED</xsl:attribute>
																																							</xsl:if>
																																							<xsl:value-of
																																								select="item" />
																																						</OPTION>
																																					</xsl:for-each>
																																				</SELECT>
																																			</TD>
																																		</TR>



																																		<tr>
																																			<td> Address*</td>
																																			<td align="left">
																																				<INPUT SIZE="20"
																																					AUTOCOMPLETE="off" TYPE="TEXT"
																																					NAME="shipment_address"
																																					id="shipment_address">
																																					<xsl:attribute
																																						name="value"><xsl:value-of
																																						select="document/shipment_address" /></xsl:attribute>
																																				</INPUT>
																																			</td>
																																		</tr>
																																		<tr>
																																			<td> Phone* </td>
																																			<td align="left">
																																				<INPUT SIZE="40"
																																					AUTOCOMPLETE="off" TYPE="TEXT"
																																					NAME="shipment_phone"
																																					id="shipment_phone">
																																					<xsl:attribute
																																						name="value"><xsl:value-of
																																						select="document/shipment_phone" /></xsl:attribute>
																																				</INPUT>
																																			</td>
																																		</tr>
																																		<tr>
																																			<td> Contact name* </td>
																																			<td align="left">
																																				<INPUT SIZE="40"
																																					AUTOCOMPLETE="off" TYPE="TEXT"
																																					NAME="contact_person"
																																					id="contact_person">
																																					<xsl:attribute
																																						name="value"><xsl:value-of
																																						select="document/contact_person" /></xsl:attribute>
																																				</INPUT>
																																			</td>
																																		</tr>
					<!-- Shipping company: each carrier is a site of its own in this system; the choice is saved with the order -->
					<tr><td> Shipping company </td><td align="left" ><SELECT NAME="shipping_company_id" style="width: 200px"><xsl:for-each select="document/shipping_company/shipping_company-item"><OPTION><xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute><xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if><xsl:value-of select="item"/></OPTION></xsl:for-each></SELECT><xsl:if test="document/shipping_company_host != ''"><xsl:text> </xsl:text><a target="_blank" href="http://{document/shipping_company_host}/"><xsl:value-of select="document/shipping_company_name"/></a></xsl:if></td></tr>
					<!-- Resolution center (dispute on this order); shown only when sql/resolution_center_migration.sql is applied -->
					<xsl:if test="document/resolution_center_enabled = 'true'">
					<tr><td> Resolution center </td><td align="left" ><SELECT NAME="resolution_center_id" style="width: 200px"><xsl:for-each select="document/resolution_center/resolution_center-item"><OPTION><xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute><xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if><xsl:value-of select="item"/></OPTION></xsl:for-each></SELECT><xsl:if test="document/resolution_center_host != ''"><xsl:text> </xsl:text><a target="_blank" href="http://{document/resolution_center_host}/"><xsl:value-of select="document/resolution_center_name"/></a></xsl:if><xsl:if test="document/resolution_status_lable != ''"><xsl:text> - </xsl:text><b><xsl:value-of select="document/resolution_status_lable"/></b></xsl:if></td></tr>
					</xsl:if>
																																		<tr>
																																			<td> E-Mail *</td>
																																			<td align="left">
																																				<INPUT
																																					title="On this e-mail the account will be sent"
																																					SIZE="40" AUTOCOMPLETE="off"
																																					id="shipment_email" TYPE="TEXT"
																																					NAME="shipment_email">
																																					<xsl:attribute
																																						name="value"><xsl:value-of
																																						select="document/shipment_email" /></xsl:attribute>
																																				</INPUT>
																																			</td>
																																		</tr>
																																		<tr>
																																			<td> Fax</td>
																																			<td align="left">
																																				<INPUT SIZE="40"
																																					AUTOCOMPLETE="off" TYPE="TEXT"
																																					NAME="shipment_fax">
																																					<xsl:attribute
																																						name="value"><xsl:value-of
																																						select="document/shipment_fax" /></xsl:attribute>
																																				</INPUT>
																																			</td>
																																		</tr>
																																		<tr>
																																			<td> Comments </td>
																																			<td align="left">
																																				<textarea rows="5"
																																					NAME="shipment_description">
																																					<xsl:value-of
																																						select="document/shipment_description" />
																																				</textarea>
																																			</td>
																																		</tr>




																																		<TR>
																																			<TD>
																																				<input size="40"
																																					AUTOCOMPLETE="off" TYPE="hidden"
																																					NAME="redirect" VALUE="Policy.jsp">
																																				</input>
																																				<input size="40"
																																					AUTOCOMPLETE="off" TYPE="hidden"
																																					NAME="action" VALUE="save">
																																				</input>
																																			</TD>
																																			<TD></TD>
																																		</TR>

																																		<TR align="right"
																																			style="padding-top: 15px">
																																			<TD colspan="2" align="center">
																																				<br />
																																				<DIV
																																					style="text-align: center; padding-left: 27px; padding-top: 5px">
																																					<TABLE class="regbut">
																																						<TR>
																																							<TD
																																								style="width:50px; padding-left: 180px;">
																																								<xsl:if
																																									test="document/role_id != 0">
																																									<input class="button"
																																										type="button" name="Submit"
																																										value="Checkout"
																																										onClick="isAddressValid()"></input>
																																								</xsl:if>
																																								<xsl:if
																																									test="document/role_id = 0">
																																									<input class="button"
																																										type="button" name="button"
																																										value="Checkout"
																																										onClick="if( confirm('For order creation it is necessary to be authorised') == true  ) parent.location='Authorization.jsp?Login=';"></input>
																																								</xsl:if>

																																							</TD>
																																							<td align="left"
																																								style="padding-left: 10px">
																																								<input class="button"
																																									type="reset" value="Clean"></input>
																																							</td>
																																						</TR>
																																					</TABLE>
																																				</DIV>
																																			</TD>

																																		</TR>

																																	</TABLE>
																																</form>
																															</DIV>

																														</DIV>
																													</TD>
																												</TR>
																											</TABLE>
																											<br />
																											New Arrival
																											<div class="line2"></div>
																											<br />

																											<div class="scroll-container">
																												<button class="scroll-left"
																													onclick="leftScrollNewArrival()">&#9664;</button>
																												<div class="scroll-content-new-arrival">
																													<xsl:if
																														test="count(document/new_arrival_list/new_arrival) != 0">
																														<xsl:for-each
																															select="document/new_arrival_list/new_arrival">
																															<div class="item">
																																<LI>
																																	<A>
																																		<xsl:attribute
																																			name="HREF"><xsl:value-of
																																			select="policy_url" /></xsl:attribute>
																																		<SPAN>
																																			<xsl:if test="image != ''">
																																				<img alt="" width="147"
																																					border="0">
																																					<xsl:attribute
																																						name="src">
																			       <xsl:value-of select="image" />
																			       </xsl:attribute>
																																				</img>
																																				<br />
																																				<br />
																																			</xsl:if>
																																			<xsl:for-each
																																				select="description/r">
																																				<xsl:value-of
																																					select="." />
																																				<BR />
																																			</xsl:for-each>
																																		</SPAN>
																																		<br />
																																	</A>

																																	<xsl:if
																																		test="/document/admin/post_manager != ''">
																																		<table style="width:150px">
																																			<tbody>
																																				<tr style="padding-bottom: 15px">
																																					<td style="padding-bottom: 5px">
																																						<form name="product_del"
																																							style="width:50px"
																																							action="Productlist.jsp"
																																							method="POST">
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="action" VALUE="del"></INPUT>
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="product_id">
																																								<xsl:attribute
																																									name="value"><xsl:value-of
																																									select="product_id" /></xsl:attribute>
																																							</INPUT>
																																							<INPUT class="button"
																																								TYPE="submit" name="submit"
																																								value="Delete"></INPUT>
																																						</form>
																																					</td>
																																					<td width="5px"></td>
																																					<td>

																																						<form name="product_edit"
																																							style="width:50px"
																																							action="Productlist.jsp"
																																							method="POST">
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="action" VALUE="edit"></INPUT>
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="element" VALUE="news"></INPUT>
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="product_id">
																																								<xsl:attribute
																																									name="value"><xsl:value-of
																																									select="product_id" /></xsl:attribute>
																																							</INPUT>
																																							<INPUT class="button"
																																								TYPE="submit" name="submit"
																																								value="Edit"></INPUT>
																																						</form>
																																					</td>
																																				</tr>
																																			</tbody>
																																		</table>
																																	</xsl:if>
																																</LI>
																															</div>
																														</xsl:for-each>
																													</xsl:if>
																												</div>
																												<button class="scroll-right"
																													onclick="rightScrollNewArrival()">&#9654;</button>
																											</div>

																											<br />
																											Recommended items
																											<div class="line2"></div>
																											<br />

																											<div class="scroll-container">
																												<button class="scroll-left"
																													onclick="leftScrollRecommendedItems()">&#9664;</button>
																												<div
																													class="scroll-content-recommended-items">
																													<xsl:if
																														test="count(document/recommentedItems/recommented) != 0">
																														<xsl:for-each
																															select="document/recommentedItems/recommented">
																															<div class="item">
																																<LI>
																																	<A>
																																		<xsl:attribute
																																			name="HREF"><xsl:value-of
																																			select="policy_url" /></xsl:attribute>
																																		<SPAN>
																																			<xsl:if test="image != ''">
																																				<img alt="" width="147"
																																					border="0">
																																					<xsl:attribute
																																						name="src">
																			       <xsl:value-of select="image" />
																			       </xsl:attribute>
																																				</img>
																																				<br />
																																				<br />
																																			</xsl:if>
																																			<xsl:for-each
																																				select="description/r">
																																				<xsl:value-of
																																					select="." />
																																				<BR />
																																			</xsl:for-each>
																																		</SPAN>
																																		<br />
																																	</A>

																																	<xsl:if
																																		test="/document/admin/post_manager != ''">
																																		<table style="width:150px">
																																			<tbody>
																																				<tr style="padding-bottom: 15px">
																																					<td style="padding-bottom: 5px">
																																						<form name="product_del"
																																							style="width:50px"
																																							action="Productlist.jsp"
																																							method="POST">
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="action" VALUE="del"></INPUT>
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="product_id">
																																								<xsl:attribute
																																									name="value"><xsl:value-of
																																									select="product_id" /></xsl:attribute>
																																							</INPUT>
																																							<INPUT class="button"
																																								TYPE="submit" name="submit"
																																								value="Delete"></INPUT>
																																						</form>
																																					</td>
																																					<td width="5px"></td>
																																					<td>

																																						<form name="product_edit"
																																							style="width:50px"
																																							action="Productlist.jsp"
																																							method="POST">
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="action" VALUE="edit"></INPUT>
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="element" VALUE="news"></INPUT>
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="product_id">
																																								<xsl:attribute
																																									name="value"><xsl:value-of
																																									select="product_id" /></xsl:attribute>
																																							</INPUT>
																																							<INPUT class="button"
																																								TYPE="submit" name="submit"
																																								value="Edit"></INPUT>
																																						</form>
																																					</td>
																																				</tr>
																																			</tbody>
																																		</table>
																																	</xsl:if>
																																</LI>
																															</div>
																														</xsl:for-each>
																													</xsl:if>
																												</div>
																												<button class="scroll-right"
																													onclick="rightScrollRecommendedItems()">&#9654;</button>
																											</div>

																											<br />
																											Sponsored by seller
																											<div class="line2"></div>
																											<br />

																											<div class="scroll-container">
																												<button class="scroll-left"
																													onclick="leftScrollSponsoredBySeller()">&#9664;</button>
																												<div
																													class="scroll-content-sponsored-by-seller">
																													<xsl:if
																														test="count(document/sponsoredBySellersItems/sponsored) != 0">
																														<xsl:for-each
																															select="document/sponsoredBySellersItems/sponsored">
																															<div class="item">
																																<LI>
																																	<A>
																																		<xsl:attribute
																																			name="HREF"><xsl:value-of
																																			select="policy_url" /></xsl:attribute>
																																		<SPAN>
																																			<xsl:if test="image != ''">
																																				<img alt="" width="147"
																																					border="0">
																																					<xsl:attribute
																																						name="src">
																			       <xsl:value-of select="image" />
																			       </xsl:attribute>
																																				</img>
																																				<br />
																																				<br />
																																			</xsl:if>
																																			<xsl:for-each
																																				select="description/r">
																																				<xsl:value-of
																																					select="." />
																																				<BR />
																																			</xsl:for-each>
																																		</SPAN>
																																		<br />
																																	</A>

																																	<xsl:if
																																		test="/document/admin/post_manager != ''">
																																		<table style="width:150px">
																																			<tbody>
																																				<tr style="padding-bottom: 15px">
																																					<td style="padding-bottom: 5px">
																																						<form name="product_del"
																																							style="width:50px"
																																							action="Productlist.jsp"
																																							method="POST">
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="action" VALUE="del"></INPUT>
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="product_id">
																																								<xsl:attribute
																																									name="value"><xsl:value-of
																																									select="product_id" /></xsl:attribute>
																																							</INPUT>
																																							<INPUT class="button"
																																								TYPE="submit" name="submit"
																																								value="Delete"></INPUT>
																																						</form>
																																					</td>
																																					<td width="5px"></td>
																																					<td>

																																						<form name="product_edit"
																																							style="width:50px"
																																							action="Productlist.jsp"
																																							method="POST">
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="action" VALUE="edit"></INPUT>
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="element" VALUE="news"></INPUT>
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="product_id">
																																								<xsl:attribute
																																									name="value"><xsl:value-of
																																									select="product_id" /></xsl:attribute>
																																							</INPUT>
																																							<INPUT class="button"
																																								TYPE="submit" name="submit"
																																								value="Edit"></INPUT>
																																						</form>
																																					</td>
																																				</tr>
																																			</tbody>
																																		</table>
																																	</xsl:if>
																																</LI>
																															</div>
																														</xsl:for-each>
																													</xsl:if>
																												</div>
																												<button class="scroll-right"
																													onclick="rightScrollSponsoredBySeller()">&#9654;</button>
																											</div>
																											<br />
																											Recently reviewed
																											<div class="line2"></div>
																											<br />

																											<div class="scroll-container">
																												<button class="scroll-left"
																													onclick="leftScrollSponsoredBySeller()">&#9664;</button>
																												<div
																													class="scroll-content-sponsored-by-seller">
																													<xsl:if
																														test="count(document/recentlyReviewedItems/reviewed) != 0">
																														<xsl:for-each
																															select="document/recentlyReviewedItems/reviewed">
																															<div class="item">
																																<LI>
																																	<A>
																																		<xsl:attribute
																																			name="HREF"><xsl:value-of
																																			select="policy_url" /></xsl:attribute>
																																		<SPAN>
																																			<xsl:if test="image != ''">
																																				<img alt="" width="147"
																																					border="0">
																																					<xsl:attribute
																																						name="src">
																			       <xsl:value-of select="image" />
																			       </xsl:attribute>
																																				</img>
																																				<br />
																																				<br />
																																			</xsl:if>
																																			<xsl:for-each
																																				select="description/r">
																																				<xsl:value-of
																																					select="." />
																																				<BR />
																																			</xsl:for-each>
																																		</SPAN>
																																		<br />
																																	</A>

																																	<xsl:if
																																		test="/document/admin/post_manager != ''">
																																		<table style="width:150px">
																																			<tbody>
																																				<tr style="padding-bottom: 15px">
																																					<td style="padding-bottom: 5px">
																																						<form name="product_del"
																																							style="width:50px"
																																							action="Productlist.jsp"
																																							method="POST">
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="action" VALUE="del"></INPUT>
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="product_id">
																																								<xsl:attribute
																																									name="value"><xsl:value-of
																																									select="product_id" /></xsl:attribute>
																																							</INPUT>
																																							<INPUT class="button"
																																								TYPE="submit" name="submit"
																																								value="Delete"></INPUT>
																																						</form>
																																					</td>
																																					<td width="5px"></td>
																																					<td>

																																						<form name="product_edit"
																																							style="width:50px"
																																							action="Productlist.jsp"
																																							method="POST">
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="action" VALUE="edit"></INPUT>
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="element" VALUE="news"></INPUT>
																																							<INPUT SIZE="0"
																																								AUTOCOMPLETE="off" TYPE="HIDDEN"
																																								NAME="product_id">
																																								<xsl:attribute
																																									name="value"><xsl:value-of
																																									select="product_id" /></xsl:attribute>
																																							</INPUT>
																																							<INPUT class="button"
																																								TYPE="submit" name="submit"
																																								value="Edit"></INPUT>
																																						</form>
																																					</td>
																																				</tr>
																																			</tbody>
																																		</table>
																																	</xsl:if>
																																</LI>
																															</div>
																														</xsl:for-each>
																													</xsl:if>
																												</div>
																												<button class="scroll-right"
																													onclick="rightScrollSponsoredBySeller()">&#9654;</button>
																											</div>
																										</div>
																									</TD>
																								</TR>
																								<tr>
																									<td>
																										<br />
																									</td>
																								</tr>
																							</TBODY>
																						</TABLE>
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
					function openCategoriesPanel(panel, toggleId) {
						panel.classList.add('open');
						panel.style.display = 'block';
						panel.style.visibility = 'visible';
						var toggle = document.getElementById(toggleId);
						var icon = null;
						if (toggle) { icon = toggle.querySelector('.hamburger-icon'); }
						var ref = icon ? icon : toggle;
						if (ref) {
							var r = ref.getBoundingClientRect();
							var topGap = (toggleId === 'accountToggle') ? -8 : 14;
							panel.style.position = 'fixed';
							panel.style.top = Math.round(r.bottom + topGap) + 'px';
							panel.style.marginTop = '0';
							if (toggleId === 'accountToggle') {
								// Right-aligned panel with its own fixed width (set in CSS),
								// regardless of viewport size. Shifted further right by 20px.
								panel.style.left = 'auto';
								panel.style.right = Math.max(0, Math.round(window.innerWidth - r.right - 20)) + 'px';
								panel.style.width = 'auto';
								panel.style.maxWidth = '92vw';
							} else if (window.innerWidth &lt;= 768) {
								panel.style.left = '0px';
								panel.style.right = 'auto';
								panel.style.width = '100vw';
								panel.style.maxWidth = '100vw';
							} else {
								panel.style.left = Math.max(0, Math.round(r.left - 10)) + 'px';
								panel.style.right = 'auto';
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
						else { openCategoriesPanel(panel, 'categoriesToggle'); }
						return false;
					}
					function toggleAccountMenu(e) {
						if (e) { e.preventDefault(); e.stopPropagation(); }
						var panel = document.getElementById('accountPanel');
						if (!panel) return false;
						if (panel.classList.contains('open')) { closeCategoriesPanel(panel); }
						else { openCategoriesPanel(panel, 'accountToggle'); }
						return false;
					}
					function closeAccountMenu(e) {
						if (e) { e.preventDefault(); e.stopPropagation(); }
						var panel = document.getElementById('accountPanel');
						if (panel) { closeCategoriesPanel(panel); }
						return false;
					}

					document.addEventListener('click', function (e) {
						[['categoriesPanel', 'categoriesToggle'], ['accountPanel', 'accountToggle']].forEach(function (pair) {
							var panel = document.getElementById(pair[0]);
							var toggle = document.getElementById(pair[1]);
							if (!panel || !toggle) return;
							if (panel.classList.contains('open') &amp;&amp; !panel.contains(e.target) &amp;&amp; !toggle.contains(e.target)) {
								closeCategoriesPanel(panel);
							}
						});
					});
				</script>
			</body>
		</HTML>
	</xsl:template>
</xsl:stylesheet>
