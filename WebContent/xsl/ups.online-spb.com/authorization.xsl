<?xml version='1.0' encoding='windows-1251' ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"  xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:output method="html" indent="yes"/>
<xsl:output encoding="UTF-8"/>
<xsl:strip-space elements="*"/>


<xsl:template match="/">
	<xsl:variable name="host" select="string(document/host)"/>	
	<xsl:variable name="user_id" select="number(/document/owner_user_id)"/> 
	<xsl:variable name="role" select="document/role_id"/> 
	<xsl:variable name="virtual_host" select="document/virtual_host"/> 
<HTML>
<HEAD>
<META HTTP-EQUIV="no-cache"/>
 <title><xsl:value-of select="document/title"/></title>
 
     <LINK rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/template.css')"/></xsl:attribute></LINK> 
     <LINK rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/constant.css')"/></xsl:attribute></LINK> 
	 <SCRIPT type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/mootools.js')"/></xsl:attribute></SCRIPT>
	 <SCRIPT type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/caption.js')"/></xsl:attribute></SCRIPT>
	 <SCRIPT type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/caption.js')"/></xsl:attribute></SCRIPT>
	 <link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/menu.css')"/></xsl:attribute></link>
	 <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/menu.js')"/></xsl:attribute></script>
	 
	 <link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/showiframe.css')"/></xsl:attribute></link>
	 <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/showiframe.js')"/></xsl:attribute></script>

</HEAD>


<body id="body">

<div class="main" style="background-color: #E7E7DF">
				<IMG alt="Logo"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/logo.gif')"/></xsl:attribute></IMG>
			
			</div>
	<div id="gradient">
		<div style="width: 1400px;" class="main">
			<div id="top">

					<div id="topmenu">
						<div class="module-topmenu">
						
						<!--  <ul class="menu-nav">  -->
						<ul id="sddm" >
						<li class="item53">
						<a href="Productlist.jsp?catalog_id=-2">
						<span>Home page</span>
						</a>
						</li>
						<!-- 
						<li class="item29">
						<a href="Productlist.jsp?catalog_id=-6">
						<span>New Arrivals</span>
						</a>
						</li>
						
						<LI class="item18">
	  					<A href="Productlist.jsp?catalog_id=-10">
	  					<SPAN>Popular</SPAN>
	  					</A>
	  					</LI>
	  					 -->
	  						  				 <xsl:for-each select="document/menu/menu-item">
	  				   <xsl:variable name="rowNum" select="position()" /> 
											       <xsl:if test="item != ''">
													   <xsl:if test="code != '-1'">
														   <xsl:if test="code != '-2'">
																   <xsl:if test="code != '-3'">
																         
																	             <LI class="item17">
																					  <A    onmouseout="mclosetime()" >
																					  <xsl:attribute name="HREF"><xsl:value-of select="url"/></xsl:attribute>
																					  <xsl:attribute name="onmouseover">mopen('m<xsl:value-of select="$rowNum"/>')</xsl:attribute>
																					  <SPAN><xsl:value-of select="item"/></SPAN>
																					  </A>
																					  
													    							<div  onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
													    								 <xsl:attribute name="id"><xsl:value-of select="concat('m',$rowNum)"/></xsl:attribute>	
																					  
																					  <xsl:for-each select="submenu-item">
																					  <A ><xsl:attribute name="HREF"><xsl:value-of select="suburl"/></xsl:attribute>
											           									<xsl:value-of select="subitem"/>
													     							 </A>
													     							 
																					 </xsl:for-each>
																					</div>
																				  </LI>
															 </xsl:if>
												 		 </xsl:if>
											 		 </xsl:if>
										 		 </xsl:if>
						</xsl:for-each>
	  					
	  				    <LI>
	      				 <A href="Order.jsp"> 
	        			 <IMG alt="Cart" border="0" height="40" width="40"  style="margin: -10px;" >
	        	 		 <xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/empty-cart-light.png')"/></xsl:attribute></IMG>
	        	 		 </A>
	      				</LI>
	  				    
	  				    <LI >
	  					<A href="Notifications.jsp">
	  					<SPAN>	
							<svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#F3F3F3"><path d="M126-166v-126h68v-246q0-96 55.5-173T397-811v-20q0-34.58 24.21-58.79T480-914q34.58 0 58.79 24.21T563-831v20q93 23 148 99.5T766-538v246h68v126H126Zm354-329Zm1 460q-37.95 0-64.98-26.73Q389-88.46 389-126h183q0 38-26.73 64.5T481-35ZM320-292h320v-246q0-66-47-113t-113-47q-66 0-113 47t-47 113v246Z"/>
								<title id="title">Notifications</title>
							</svg>
						</SPAN>
	  					</A>
	  					</LI>

	  					<LI>
	  					<A href="EMail.jsp">
	  					<SPAN>	
							<svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#F3F3F3"><path d="M172-126q-53 0-89.5-36.5T46-252v-456q0-53 36.5-89.5T172-834h616q53 0 89.5 36.5T914-708v456q0 53-36.5 89.5T788-126H172Zm308-271L172-597v345h616v-345L480-397Zm0-111 308-200H172l308 200Zm-308-89v-111 456-345Z"/></svg>
						</SPAN>
	  					</A>
	  					</LI>
	      				 
	      				 <LI>
	      				 <A href="#"> 
	      				   <xsl:attribute name="onclick"><xsl:value-of select="concat('openDialog(&quot;http://',$virtual_host,':8096/index.html&quot;)')"/></xsl:attribute>
	      				  <IMG border="0" height="40" width="40"  style="margin: -10px;" >
	        	 		    <xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/ai_icon.png')"/></xsl:attribute>
	        	 		  </IMG>
	        	 		 </A>
	      				 </LI>

						</ul>
						</div>
				</div>
			</div>


			    <DIV class="indent" >
			    	<DIV class="moduletable">
			     		<TABLE class="who_is_online" style="WIDTH: auto" align="right">
			       			<TBODY>
			        			<TR>
			        				
			          				<TD>
			        				<xsl:if test="document/login != ''">   <!--  ���������� ���� ���� ����� -->
									<b>User </b> 
									<a href="Authorization.jsp" class="user0">
									<xsl:value-of select="document/login"/>
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
					
					  	<div id="search">
							<div class="module-search">
								<FORM name="searchform"  action="Productlist.jsp"  method="POST">
								<div class="search">
								<INPUT class="inputbox" 
								id="search_value"  
						   		name="search_value" 
						   		type="text"  
						   		size="20" 
						   		alt="It is search by name goods"   
						   		title="It is search by name goods">
						   		<xsl:attribute name="value">
						   		<xsl:value-of select="document/search_value"/>
						   		</xsl:attribute>
						   		</INPUT>
						   	  	<INPUT  class="button" type="image" value="Search" onClick="return top.search_word();return true"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/searchButton.gif')"/></xsl:attribute></INPUT>
						      	</div>
						     	<INPUT id="search_char"  name="search_char" type="hidden" ></INPUT>
							  	<INPUT id="searchquery"  name="searchquery" type="hidden" ></INPUT>
							  	<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="offset" VALUE="0"  ></INPUT> 
						     	</FORM>					
					        </div>
						</div>
			
						
					   <div id="breadcrumb">
						   <div class="space">
					       <span class="breadcrumbs pathway">
					          <xsl:if test="document/role_id != 0">
						      <xsl:if test="count(document/parent/parent-item) != 1">
                                    
                                      <a href="Productlist.jsp?catalog_id=-2" class="catalog" alt="To return back  to the top of Categorization" title="To return back  to the top of Categorization">
                                        <U><font size="2" >All Categories</font></U>
                                      </a>&#160; &#187; 
								
								        <xsl:for-each select="document/parent/parent-item">										
											 <xsl:if test="code != '-2'">												
												<A ><xsl:attribute name="HREF"><xsl:value-of select="url"/></xsl:attribute>
											        <U><font size="2" > <xsl:value-of select="item"/></font> </U> 
											    </A>&#160; &#187; 
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
					<div id="left">
					<div class="module_menu">
					<div class="first">
					<div class="sec">
							<h3>Forgot your password?</h3>
								<div class="box-indent">
								<div class="width">
								<P  style="font-size: 12px; margin-bottom: 5px;padding-top:5px">
								  To get password , Please  input your email in field for it.
						        </P>												        
							     <form action="/sendpassword"  method="post">													      	
					                     <INPUT style="width:170px;" class="inputbox" title="E-Mail"  AUTOCOMPLETE="off" TYPE="TEXT" NAME="email" /><br/>
					                     <div style="padding-top: 5px; padding-left: 45px"><br/>
					                     <input  class="button" type="submit" name="submit"  value="Send"/><br/>
					                     </div>
					             </form>

								</div>
					</div>
					</div>
					</div>
					</div>
					
					<!-- � ����������� -->
			           
			            <DIV class="module" style="margin-right:0px">
			            <DIV class="first">
			            <DIV class="sec">
			            <DIV>
			            <H3>About registration</H3>
			            	<P class="text" style="padding:10px 20px 13px;">Registration is necessary for registration of the order and payment through the Internet.</P>
		            		
		            		<P class="text" style="padding:10px 20px 16px;">" * "required fields. </P>               
					
			            </DIV>
			            </DIV>
			            </DIV>
			            </DIV>
			</div>
					<div id="right">
					
					
								<div class="module_s2">
								<div class="first">
								<div class="sec">

								<h3>Authorization</h3>
								<div class="box-indent">
								<div class="width">
										
										<FORM action="Authorization.jsp"  method="post" id="form-login" class="form-login">
						                  <FIELDSET class="input">
						                  <P id="form-login-username">
						                  <LABEL for="modlgn_username">Login</LABEL>
						                  <BR/>
						                  
						                  <INPUT id="modlgn_username" title=" User" class="inputbox" tabindex="10001"  AUTOCOMPLETE="off" TYPE="TEXT" NAME="Login" >
														<xsl:attribute name="value">
															<xsl:value-of select="document/login"/>
														</xsl:attribute>		
										  </INPUT> 
										  </P>
						                  
						                  <P id="form-login-password">
						                  <LABEL for="modlgn_passwd">Password</LABEL><BR/>
						                  <INPUT class="inputbox" title="Password" id="modlgn_passwd" tabindex="10002" AUTOCOMPLETE="off" TYPE="PASSWORD" NAME="Passwd1" ></INPUT> 
						                  </P>
						                  
						                  <div style="padding-left: 35px; padding-bottom: 5px;border: none; width: 100px"><BR/>
						                  	<INPUT class="button" type="submit" value="Enter" name="Submit"/> 
						                  </div>
						                  
						                  </FIELDSET> 
						                 </FORM>                  
						                  
						                  <UL class="log_list">
						                  <LI>
						                  <A href="Authorization.jsp?Login=newuser" style="COLOR: #072f3a">Forgot your password?
						                  </A> 
						                  </LI> 
						                  <LI>
						                  <A href="Authorization.jsp?Login=" style="COLOR: #072f3a">Registration
						                  </A>
						                  </LI>
						                  </UL>
											
								</div>
								</div>

						</div>
						</div>
						</div>
						
			           
			            <DIV class="module" style="margin-right:0px">
			            <DIV class="first">
			            <DIV class="sec">
			            <DIV>
			            <H3>Pay Order</H3>
			          	<P class="text" style="padding:10px 20px 13px;">
			          	 To pay order payment you must be already logged on this site
			          	</P>
		                <div style="padding:0px 20px 17px;">
		                <A>
		                
		                <xsl:attribute name="HREF">
		                <xsl:value-of select="document/to_pay"/>
		                </xsl:attribute>
		                
						<IMG border="0" height="20" width="140">
						<xsl:attribute name="src">
						<xsl:value-of select="concat('xsl/',$host,'/images/credit-cards.jpg')"/>
						</xsl:attribute>
						</IMG> 
						</A>               
				      	</div>
			            </DIV>
			            </DIV>
			            </DIV>
			            </DIV>
			            
						
						
		
						
							
							
			
</div>
			
<div id="container">
<div class="comp-cont">
		<table class="blog" cellpadding="0" cellspacing="0">
			<tr>
			<td valign="top">
				<div class="article-bg">
					<div class="article-left">
						<div class="article-right">
							<div class="width">
							
			        				<xsl:if test="document/message != ''">
			        				<p align="center"> 			        				 >
			        				<font color="red" >
										<xsl:value-of select="document/message"/>
									</font>
									</p>
									<br/>
									<br/>
									</xsl:if>
									
							<h3 style="padding-left: 40px; padding-bottom: 15px;color: #4C4B49; font-size: 20px">Registration</h3>
                            
								<DIV  class="registration">							
										  <form method="post" ACTION="RegPage.jsp">
												    <TABLE class="registration" cellSpacing="0" cellPadding="0" border="0">															    													
														<TR><TD width="150px">Login:*</TD> <TD align="left"> <input size="60"  AUTOCOMPLETE="off" TYPE="TEXT" name="Login"><xsl:attribute name="value"><xsl:value-of select="document/login"/></xsl:attribute></input></TD></TR>
														<TR><TD width="150px">Password:* </TD> <TD> <input size="60" name="Passwd1" type="password" id="password" ></input></TD></TR>
														<TR><TD width="150px">Re-enter password*   :  </TD> <TD><input size="60" name="Passwd2" type="password" id="password"></input></TD></TR>
														<TR><TD width="150px">First name:*  </TD> <TD><input  size="60" AUTOCOMPLETE="off" TYPE="TEXT" name="FName"><xsl:attribute name="value"><xsl:value-of select="document/firstname"/></xsl:attribute></input></TD></TR>
														<TR><TD style="width: 150px">Last name:* </TD> <TD><input size="60" AUTOCOMPLETE="off" TYPE="TEXT"  name="LName"  value=""   ><xsl:attribute name="value"><xsl:value-of select="document/lastname"/></xsl:attribute></input></TD></TR>
														<TR><TD width="150px">The company:* </TD> <TD><input size="60" AUTOCOMPLETE="off" TYPE="TEXT"  name="Company" value=""><xsl:attribute name="value"><xsl:value-of select="document/company"/></xsl:attribute></input></TD></TR>
				                       					<TR><TD width="150px">Country:*</TD>
				                       						<TD >
				                       						<SELECT NAME = "country_id" onChange="doChangeCity('country_id', this.value)"  style="margin-bottom: 12px">
													 	   	<xsl:for-each select="document/country/country-item">										
															<OPTION>
																<xsl:attribute name="value">
																	<xsl:value-of select="code"/>
																</xsl:attribute>
											                    <xsl:if test="code = selected">
											                   		 <xsl:attribute name="SELECTED">SELECTED</xsl:attribute>
											                    </xsl:if>
											                    <xsl:value-of select="item"/>
											   	           </OPTION>
														   </xsl:for-each>		
											               </SELECT>
											               </TD>
											            </TR>
											           
											            <TR><TD width="150px">City:*</TD>
											            	<TD>
												                <SELECT NAME = "city_id">
														 	    <xsl:for-each select="document/city/city-item">
																 <OPTION>
																	 <xsl:attribute name="value">
																	    	<xsl:value-of select="code"/>
																	 </xsl:attribute>
																	  <xsl:if test="code = selected">
											                   		 	<xsl:attribute name="SELECTED">SELECTED</xsl:attribute>
											                    	  </xsl:if>
													                 <xsl:value-of select="item"/>
												   	          	 </OPTION>
													   		   </xsl:for-each>
													   		   </SELECT>
												   		   </TD> 
												   	   </TR>

                    								   <TR><TD width="150px">Currency</TD><TD><input size="60" AUTOCOMPLETE="off" TYPE="hidden"  name="currency_id" value="1" ></input>dollars</TD> </TR>							
														<TR><TD width="150px">E-Mail:*  </TD> <TD><input size="60" AUTOCOMPLETE="off" TYPE="TEXT"  name="EMail" value="" ><xsl:attribute name="value"><xsl:value-of select="document/email"/></xsl:attribute></input></TD></TR>
														<TR><TD width="150px">Phone:</TD> <TD><input size="60"  AUTOCOMPLETE="off" TYPE="TEXT"  name="Phone" value=""><xsl:attribute name="value"><xsl:value-of select="document/phone"/></xsl:attribute></input></TD></TR>
														<TR><TD width="150px">Cell:  </TD> <TD><input size="60"  AUTOCOMPLETE="off" TYPE="TEXT"  name="MPhone" value="" ><xsl:attribute name="value"><xsl:value-of select="document/mphone"/></xsl:attribute></input></TD></TR>
														<TR><TD width="150px">Fax:  </TD> <TD><input size="60"  AUTOCOMPLETE="off" TYPE="TEXT" name="Fax" value="" ><xsl:attribute name="value"><xsl:value-of select="document/fax"/></xsl:attribute></input></TD></TR>
														<TR><TD width="150px">Skype:  </TD> <TD><input size="60"  AUTOCOMPLETE="off" TYPE="TEXT"  name="Skype" value=""  ><xsl:attribute name="value"><xsl:value-of select="document/icq"/></xsl:attribute></input></TD></TR>
														<TR><TD width="150px">Broker ID:  </TD> <TD><input size="60"  AUTOCOMPLETE="off" TYPE="TEXT"  name="Broker ID" value="" ><xsl:attribute name="value"><xsl:value-of select="document/website"/></xsl:attribute></input>
															<input size="60"  AUTOCOMPLETE="off" TYPE="hidden"  name="site_id" value="" ><xsl:attribute name="value"><xsl:value-of select="document/site/site-item/selected"/></xsl:attribute></input>
														</TD></TR>
														<TR><TD width="150px"><img width="100px" alt="The picture with the generation number" src="/gennumberservlet"  /> </TD> <TD><input size="20" style="width:80px;" AUTOCOMPLETE="off" TYPE="TEXT"  name="gen_number" value=""></input></TD></TR>																
														<TR><TD width="150px"></TD> <TD></TD></TR>
														<TR  align="right">
															<TD colspan="2" width="250px" align="center" class="regbut" style="padding-left: 150px; padding-bottom: 25px">	
															
																<DIV class="regbut" style="padding-top:10px">
																	<table width="150px"><tr>
																	<td width="100px">
																		<input class="button" type="submit" name="Submit" value="OK"></input>	
																	</td>
																	<td width="130px">
	
																	</td>
																	<td>
																	<input  class="button" type="reset" value="Clean"></input>
																	</td></tr></table>
																</DIV>																
															</TD>															
															
														</TR>
																
														</TABLE>		
														</form>						
									 	                        
	                        		</DIV>
							
							
							
							
							
							
							
							
							
							
							
							
							     
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

        <font color="White">Internet shop . Copyright 2024 
        		<A HREF="http://www.cmsmanhattan.com"><font color="White">  FDIS Center Business Solutions Inc </font></A>.  All rights reserved
        </font>

		</div>
		</DIV>
	</DIV>

    <div id="overlay"></div>
    <div id="dialog">
        <iframe id="iframe" src=""></iframe>
        <button class="top-right-button" onclick="closeDialog()">X</button>
    </div>
    
    <div id="overlay_mail"></div>
    <div id="dialog_mail">
        <iframe id="iframe_mail" src=""></iframe>
        <button class="top-right-button" onclick="closeDialogMail()">X</button>
    </div>

</body>









</HTML>
</xsl:template>
</xsl:stylesheet>