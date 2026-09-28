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
					/* Registration form inputs: the "size" HTML attribute (character
					   width hint) is overridden by a fixed px width in the external
					   template.css/constant.css, so shrinking size="" in the markup has
					   no visible effect. Force width back to auto so the fields size
					   themselves naturally and don't run the full row width. */
					.registration input[type="text"],
					.registration input[type="password"],
					.registration input[type="TEXT"],
					.registration input[type="PASSWORD"],
					.registration select {
						width: auto !important;
						max-width: 100% !important;
						box-sizing: border-box !important;
					}
					/* Categories dropdown menu: categories live behind a hamburger-style
					   button (first item in the bar) that opens a panel underneath it.
					   NOTE: the external menu.css hides every div inside #sddm
					   (visibility:hidden, position:absolute) for its own hover submenus,
					   so all rules below use higher specificity plus !important to win. */
					#sddm li.item-categories, #sddm li.item-auth {
						position: relative !important;
						height: 100% !important;
					}
					#sddm li.item-categories > a, #sddm li.item-auth > a {
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
					#sddm li.item-auth > a {
						width: auto !important;
						padding: 0 12px !important;
						display: flex !important;
						align-items: center !important;
						color: #fff !important;
						font: bold 13px arial !important;
						text-decoration: none !important;
						white-space: nowrap !important;
						position: relative !important;
						top: -14px !important;
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
					#sddm li.item-categories div.categories-panel, #sddm li.item-auth div.categories-panel {
						display: none;
						visibility: visible !important;
						position: absolute;
						top: 100%;
						left: 0;
						margin: 0;
						padding: 0 !important;
						background: #fff !important;
						border: 1px solid #ccc !important;
						border-radius: 8px !important;
						min-width: 250px;
						max-width: 90vw;
						max-height: 70vh;
						overflow-y: auto;
						z-index: 9999 !important;
						box-shadow: 0 6px 20px rgba(0,0,0,0.3);
						text-align: left;
					}
					#sddm li.item-categories div.categories-panel.open, #sddm li.item-auth div.categories-panel.open {
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
					/* Authorization slide-panel (moved out of the page body, where it
					   overlapped the registration form on mobile, into the top bar
					   right after "Home page"). Reuses the same slide-down mechanics
					   as the categories panel. menu.css forcibly hides every div/li/a
					   nested inside #sddm (for its own legacy hover-dropdown mechanics)
					   and styles bare li/a with dark nav-bar colors, so everything below
					   is force-reset to plain, readable form styling. */
					#sddm li.item-auth div.categories-panel {
						padding: 22px !important;
						min-width: 320px !important;
						width: 340px !important;
						max-width: 92vw !important;
						box-sizing: border-box;
					}
					#sddm li.item-auth div.categories-panel * {
						visibility: visible !important;
						position: static !important;
						float: none !important;
						height: auto !important;
						min-height: 0 !important;
						max-width: none !important;
						line-height: normal !important;
						text-indent: 0 !important;
					}
					#sddm li.item-auth div.categories-panel h3 {
						display: block !important;
						font-size: 18px !important;
						font-weight: bold !important;
						color: #333 !important;
						margin: 0 0 16px 0 !important;
						padding: 0 !important;
						background: transparent !important;
					}
					#sddm li.item-auth div.categories-panel div,
					#sddm li.item-auth div.categories-panel FORM,
					#sddm li.item-auth div.categories-panel P {
						display: block !important;
						width: auto !important;
						margin: 0 !important;
						padding: 0 !important;
						background: transparent !important;
						border: none !important;
					}
					#sddm li.item-auth div.categories-panel P {
						margin: 0 0 14px 0 !important;
					}
					#sddm li.item-auth div.categories-panel FIELDSET {
						display: block !important;
						margin: 0 !important;
						padding: 0 !important;
						border: none !important;
						background: transparent !important;
					}
					#sddm li.item-auth div.categories-panel LABEL {
						display: block !important;
						color: #666 !important;
						font: bold 12px arial !important;
						text-transform: uppercase;
						letter-spacing: .03em;
						margin: 0 0 5px 0 !important;
						background: transparent !important;
					}
					#sddm li.item-auth div.categories-panel INPUT.inputbox {
						display: block !important;
						width: 100% !important;
						box-sizing: border-box !important;
						padding: 10px 12px !important;
						margin: 0 !important;
						font-size: 15px !important;
						color: #222 !important;
						background: #fff !important;
						border: 1px solid #ccc !important;
						border-radius: 5px !important;
					}
					#sddm li.item-auth div.categories-panel INPUT.inputbox:focus {
						border-color: #072f3a !important;
						outline: none !important;
					}
					#sddm li.item-auth div.categories-panel INPUT.button {
						display: inline-block !important;
						width: auto !important;
						margin: 6px 8px 0 0 !important;
						padding: 10px 26px !important;
						background: #072f3a !important;
						color: #fff !important;
						border: none !important;
						border-radius: 5px !important;
						font: bold 14px arial !important;
						cursor: pointer !important;
					}
					#sddm li.item-auth div.categories-panel INPUT.button.auth-cancel {
						background: #fff !important;
						color: #072f3a !important;
						border: 1px solid #ccc !important;
						margin-right: 0 !important;
					}
					#sddm li.item-auth div.categories-panel INPUT.button.auth-cancel:hover {
						background: #f5f5f5 !important;
					}
					#sddm li.item-auth div.categories-panel BR {
						display: none !important;
					}
					#sddm li.item-auth div.categories-panel UL.log_list {
						display: block !important;
						list-style: none !important;
						margin: 16px 0 0 0 !important;
						padding: 12px 0 0 0 !important;
						border-top: 1px solid #eee !important;
						background: transparent !important;
					}
					#sddm li.item-auth div.categories-panel UL.log_list LI {
						display: block !important;
						margin: 0 0 8px 0 !important;
						padding: 0 !important;
						background: transparent !important;
					}
					#sddm li.item-auth div.categories-panel UL.log_list LI:last-child {
						margin-bottom: 0 !important;
					}
					#sddm li.item-auth div.categories-panel UL.log_list A {
						display: inline !important;
						color: #072f3a !important;
						background: transparent !important;
						font: 13px arial !important;
						text-decoration: underline !important;
					}
					#sddm li.item-auth div.categories-panel UL.log_list A:hover {
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

										<!-- Authorization form: moved here from the page body, where it
										     overlapped the registration form on mobile. Opens as a
										     slide-down panel, same mechanism as the categories menu. -->
										<li class="item-auth">
											<a href="#" id="authToggle" onclick="return toggleAuthMenu(event);">
												<span>Login</span>
											</a>
											<div id="authPanel" class="categories-panel">
												<h3>Authorization</h3>
												<div class="box-indent">
													<div class="width">

														<FORM action="Authorization.jsp"
															method="post" id="form-login"
															class="form-login">
															<FIELDSET class="input">
																<P id="form-login-username">
																	<LABEL for="modlgn_username">Login</LABEL>
																	<BR />

																	<INPUT id="modlgn_username"
																		title=" User" class="inputbox"
																		tabindex="10001" AUTOCOMPLETE="off"
																		TYPE="TEXT" NAME="Login">
																		<xsl:attribute name="value">
																			<xsl:value-of select="document/login" />
																		</xsl:attribute>
																	</INPUT>
																</P>

																<P id="form-login-password">
																	<LABEL for="modlgn_passwd">Password</LABEL>
																	<BR />
																	<INPUT class="inputbox"
																		title="Password" id="modlgn_passwd"
																		tabindex="10002" AUTOCOMPLETE="off"
																		TYPE="PASSWORD" NAME="Passwd1"></INPUT>
																</P>

																<div
																	style="padding-left: 0px; padding-bottom: 5px;border: none;">
																	<BR />
																	<INPUT class="button" type="submit"
																		value="Enter" name="Submit" />
																	<INPUT class="button auth-cancel" type="button"
																		value="Cancel" name="Cancel"
																		onclick="return closeAuthMenu(event);" />
																</div>

															</FIELDSET>
														</FORM>

														<UL class="log_list">
															<LI>
																<A href="Authorization.jsp?Login=newuser"
																	style="COLOR: #072f3a">Forgot your password?
																</A>
															</LI>
															<LI>
																<A href="Authorization.jsp?Login="
																	style="COLOR: #072f3a">Registration
																</A>
															</LI>
														</UL>

													</div>
												</div>
											</div>
										</li>

										<LI>
											<A href="Order.jsp">
												<IMG border="0" height="40" width="40"
													style="margin: -10px;">
													<xsl:attribute name="src"><xsl:value-of
														select="concat('xsl/',$host,'/images/empty-cart-light.png')" /></xsl:attribute>
												</IMG>
											</A>
										</LI>
										



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
													<b>User </b>
													<a href="Authorization.jsp" class="user0">
														<xsl:value-of select="document/login" />
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
																<div class="width">

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

																	<h3
																		style="padding-left: 40px; padding-bottom: 15px;color: #4C4B49; font-size: 20px">Registration</h3>

																	<TABLE cellSpacing="0" cellPadding="0"
																		width="100%">
																		<TBODY>
																			<TR>
																				<TD vAlign="top">

																					<DIV class="registration">

																						<form method="post" ACTION="RegPage.jsp">
																							<TABLE class="registration" cellSpacing="0"
																								cellPadding="0" border="0">
																								<TR>
																									<TD width="150px">Login:*</TD>
																									<TD align="left">
																										<input size="20" AUTOCOMPLETE="off"
																											TYPE="TEXT" name="Login">
																											<xsl:attribute name="value"><xsl:value-of
																												select="document/login" /></xsl:attribute>
																										</input>
																									</TD>

																								</TR>
																								<TR>
																									<TD width="150px">Password:* </TD>
																									<TD>
																										<input size="20" name="Passwd1"
																											type="password" id="Passwd1"></input>
																									</TD>
																								</TR>
																								<TR>
																									<TD width="150px">Re-enter password* :  </TD>
																									<TD>
																										<input size="20" name="Passwd2"
																											type="password" id="Passwd2"></input>
																									</TD>
																								</TR>
																								<TR>
																									<TD width="150px">First name:*  </TD>
																									<TD>
																										<input size="20" AUTOCOMPLETE="off"
																											TYPE="TEXT" name="FName">
																											<xsl:attribute name="value"><xsl:value-of
																												select="document/firstname" /></xsl:attribute>
																										</input>
																									</TD>
																								</TR>
																								<TR>
																									<TD style="width: 150px">Last name:* </TD>
																									<TD>
																										<input size="20" AUTOCOMPLETE="off"
																											TYPE="TEXT" name="LName" value="">
																											<xsl:attribute name="value"><xsl:value-of
																												select="document/lastname" /></xsl:attribute>
																										</input>
																									</TD>
																								</TR>
																								<TR>
																									<TD width="150px">The company:* </TD>
																									<TD>
																										<input size="20" AUTOCOMPLETE="off"
																											TYPE="TEXT" name="Company" value="">
																											<xsl:attribute name="value"><xsl:value-of
																												select="document/company" /></xsl:attribute>
																										</input>
																									</TD>
																								</TR>
																								<TR>
																									<TD width="150px">Country:*</TD>
																									<TD>
																										<SELECT NAME="country_id"
																											onChange="doChangeCity('country_id', this.value)"
																											style="margin-bottom: 12px">
																											<xsl:for-each
																												select="document/country/country-item">
																												<OPTION>
																													<xsl:attribute name="value">
																	<xsl:value-of select="code" />
																</xsl:attribute>
																													<xsl:if test="code = selected">
																														<xsl:attribute
																															name="SELECTED">SELECTED</xsl:attribute>
																													</xsl:if>
																													<xsl:value-of select="item" />
																												</OPTION>
																											</xsl:for-each>
																										</SELECT>
																									</TD>
																								</TR>

																								<TR>
																									<TD width="150px">City:*</TD>
																									<TD>
																										<SELECT NAME="city_id">
																											<xsl:for-each
																												select="document/city/city-item">
																												<OPTION>
																													<xsl:attribute name="value">
																	    	<xsl:value-of select="code" />
																	 </xsl:attribute>
																													<xsl:if test="code = selected">
																														<xsl:attribute
																															name="SELECTED">SELECTED</xsl:attribute>
																													</xsl:if>
																													<xsl:value-of select="item" />
																												</OPTION>
																											</xsl:for-each>
																										</SELECT>
																									</TD>
																								</TR>

																								<TR>
																									<TD width="150px">Currency</TD>
																									<TD>
																										<input size="20" AUTOCOMPLETE="off"
																											TYPE="hidden" name="currency_id" value="1"></input>
																										dollars
																									</TD>
																								</TR>
																								<TR>
																									<TD width="150px">E-Mail:*  </TD>
																									<TD>
																										<input size="20" AUTOCOMPLETE="off"
																											TYPE="TEXT" name="EMail" value="">
																											<xsl:attribute name="value"><xsl:value-of
																												select="document/email" /></xsl:attribute>
																										</input>
																									</TD>
																								</TR>
																								<TR>
																									<TD width="150px">Phone:</TD>
																									<TD>
																										<input size="20" AUTOCOMPLETE="off"
																											TYPE="TEXT" name="Phone" value="">
																											<xsl:attribute name="value"><xsl:value-of
																												select="document/phone" /></xsl:attribute>
																										</input>
																									</TD>
																								</TR>
																								<TR>
																									<TD width="150px">Cell:  </TD>
																									<TD>
																										<input size="20" AUTOCOMPLETE="off"
																											TYPE="TEXT" name="MPhone" value="">
																											<xsl:attribute name="value"><xsl:value-of
																												select="document/mphone" /></xsl:attribute>
																										</input>
																									</TD>
																								</TR>
																								<TR>
																									<TD width="150px">Fax:  </TD>
																									<TD>
																										<input size="20" AUTOCOMPLETE="off"
																											TYPE="TEXT" name="Fax" value="">
																											<xsl:attribute name="value"><xsl:value-of
																												select="document/fax" /></xsl:attribute>
																										</input>
																									</TD>
																								</TR>
																								<TR>
																									<TD width="150px">Skype:  </TD>
																									<TD>
																										<input size="20" AUTOCOMPLETE="off"
																											TYPE="TEXT" name="Skype" value="">
																											<xsl:attribute name="value"><xsl:value-of
																												select="document/icq" /></xsl:attribute>
																										</input>
																									</TD>
																								</TR>
																								<TR>
																									<TD width="150px">Broker ID:  </TD>
																									<TD>
																										<input size="20" AUTOCOMPLETE="off"
																											TYPE="TEXT" name="Broker ID" value="">
																											<xsl:attribute name="value"><xsl:value-of
																												select="document/website" /></xsl:attribute>
																										</input>
																										<input size="20" AUTOCOMPLETE="off"
																											TYPE="hidden" name="site_id" value="">
																											<xsl:attribute name="value"><xsl:value-of
																												select="document/site/site-item/selected" /></xsl:attribute>
																										</input>
																									</TD>
																								</TR>
																								<TR>
																									<TD width="150px">
																										<img width="100px"
																											alt="The picture with the generation number"
																											src="/gennumberservlet" />
																									</TD>
																									<TD>
																										<input size="20" style="width:80px;"
																											AUTOCOMPLETE="off" TYPE="TEXT"
																											name="gen_number" value=""></input>
																									</TD>
																								</TR>
																								<TR>
																									<TD width="150px"></TD>
																									<TD></TD>
																								</TR>
																								<TR align="right">
																									<TD colspan="2" width="250px" align="center"
																										class="regbut"
																										style="padding-left: 150px; padding-bottom: 25px">

																										<DIV class="regbut"
																											style="padding-top:10px">
																											<table width="150px">
																												<tr>
																													<td width="100px">
																														<input class="button" type="submit"
																															name="Submit" value="OK"></input>
																													</td>
																													<td width="130px">

																													</td>
																													<td>
																														<input class="button" type="reset"
																															value="Clean"></input>
																													</td>
																												</tr>
																											</table>
																										</DIV>
																									</TD>


																								</TR>

																							</TABLE>
																						</form>

																					</DIV>


																				</TD>
																			</TR>
																		</TBODY>
																	</TABLE>










																	<span class="article_separator"></span>
																</div>
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
							var gap = (toggleId === 'authToggle') ? 0 : 14;
							panel.style.position = 'fixed';
							panel.style.top = Math.round(r.bottom + gap) + 'px';
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
						else { openCategoriesPanel(panel, 'categoriesToggle'); }
						return false;
					}
					function toggleAuthMenu(e) {
						if (e) { e.preventDefault(); e.stopPropagation(); }
						var panel = document.getElementById('authPanel');
						if (!panel) return false;
						if (panel.classList.contains('open')) { closeCategoriesPanel(panel); }
						else { openCategoriesPanel(panel, 'authToggle'); }
						return false;
					}
					function closeAuthMenu(e) {
						if (e) { e.preventDefault(); e.stopPropagation(); }
						var panel = document.getElementById('authPanel');
						if (panel) { closeCategoriesPanel(panel); }
						return false;
					}

					document.addEventListener('click', function (e) {
						[['categoriesPanel', 'categoriesToggle'], ['authPanel', 'authToggle']].forEach(function (pair) {
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
