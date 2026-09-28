<?xml version='1.0' encoding='utf-8' ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"  xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:output method="html" indent="yes"/>
<xsl:output encoding="UTF-8"/>
<xsl:strip-space elements="*"/>


<xsl:template match="/">
	<xsl:variable name="host" select="string(document/host)"/>	
	<xsl:variable name="user_id" select="number(/document/owner_user_id)"/> 
	<xsl:variable name="role" select="document/role_id"/> 
	<xsl:variable name="virtual_host" select="document/virtual_host"/> 	
	<xsl:variable name="datefrom" select="document/datefrom"/>
    <xsl:variable name="dateto" select="document/dateto"/>
<HTML>
<HEAD>
<META HTTP-EQUIV="no-cache"/>
 <title><xsl:value-of select="document/title"/></title>
 
     <LINK rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/template.css')"/></xsl:attribute></LINK> 
     <LINK rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/constant.css')"/></xsl:attribute></LINK> 
	 <SCRIPT type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/caption.js')"/></xsl:attribute></SCRIPT>

	<link type="text/css" rel="stylesheet"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/calendar.css')"/></xsl:attribute></link>	
	<script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/jquery_v2009.js')"/></xsl:attribute></script>

	
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
		<div style="width: 960px"  class="main">
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
	  					
						
						<LI class="item18">
	  					<A href="Authorization.jsp?Login=">
	  					<SPAN>Registration</SPAN>
	  					</A>
	  					</LI>
	  				
		
 <LI>
	      				 <A href="Order.jsp"> 
	        			 <IMG border="0" height="40" width="40"  style="margin: -10px;" >
	        	 		 <xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/empty-cart-light.png')"/></xsl:attribute></IMG>
	        	 		 </A>
	      				 </LI>
	      				 
	      				 
	      				 <xsl:if test="document/role_id != 0">
	        			 <LI ><A href="Productlist.jsp?action=logoff"  >
	        	 		 <SPAN >
	        	 		 <svg xmlns="http://www.w3.org/2000/svg"  height="24px" viewBox="0 -960 960 960" width="24px" fill="#F3F3F3"><path d="M212-86q-53 0-89.5-36.5T86-212v-536q0-53 36.5-89.5T212-874h276v126H212v536h276v126H212Zm415-146-88-89 96-96H352v-126h283l-96-96 88-89 247 248-247 248Z"/>
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




			    <DIV class="indent" style="width: 960px" >
			    	<DIV class="moduletable">
			     		<TABLE class="who_is_online" style="WIDTH: auto" align="right">
			       			<TBODY>
			        			<TR>
			          				<TD>
			        				<xsl:if test="document/login != ''">   <!--  пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ -->
									<B>User</B> 
									 <a href="Authorization.jsp" style="margin-left: 5px; text-decoration: none">				                
									
								 	<xsl:if test="document/login = 'user'">   <!--  пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ -->
									<font class="user0">
										<xsl:value-of select="document/login"/>
									</font>
									</xsl:if>
									
									<xsl:if test="document/login != 'user'">   <!--  пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ -->
									<xsl:if test="document/role_id = 1"> <!--  пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ -->
										<font class="user1">
											<xsl:value-of select="document/login"/>
									</font>
								</xsl:if>
											 			
								<xsl:if test="document/role_id = 2"><!--  пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ -->
									<font class="user2">
									<xsl:value-of select="document/login"/>
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
					
					  	<div id="search">
							<div class="module-search">
								<FORM name="searchform"  action="Productlist.jsp" method="POST" >
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

			
<div id="container" style="margin: 0px 21px;  margin-top: ;  margin-right: ; margin-bottom: ;  margin-left:" >
<div class="comp-cont">
		<table class="blog" cellpadding="0" cellspacing="0">
			<tr>
			<td valign="top">
				<div class="article-bg">
					<div class="article-left">
						<div class="article-right">
							<DIV style="padding-left: 20px; ">
							     <h3 style="padding-left: 20px; font-size: 17px">The list of your monetary operations</h3>
							     <br/>
								    <DIV >
								    <table border="0" class="orders" style="width:830px">															   
									<colgroup>
										<col width="80px"/>
										<col width="80"/>
										<col width="90"/>
										<col width="200"/>
										<col width="*"/>
										<col width="50"/>
									</colgroup>
									<thead>									
					                <TR>
					                	<TD class="order_head">Account</TD>
					                	<TD class="order_head">Sum</TD>
					                	<TD class="order_head">Currency</TD>
					                	<TD class="order_head">Date</TD>
					                	<TD class="order_head">Status</TD>
					                	<TD class="order_head">Action</TD>
					                </TR>
					                </thead>					               
					                <tbody>

										<xsl:for-each select="document/list/payment">
							              <TR>
							                <TD>N<xsl:value-of select="amount_id"/></TD>
							                <TD align="right" ><xsl:value-of select="add_amount"/></TD>
											<TD align="left"  ><xsl:value-of select="currency_add_lable"/></TD>
							                                <TD><xsl:value-of select="sysdate"/></TD>
							
							                                <!--  <TD><xsl:if test="complete = 't'"> <STRONG><FONT color="#000099">Executed</FONT></STRONG></xsl:if><xsl:if test="complete = 'f'"> <STRONG><FONT color="#000099">Expects performance</FONT></STRONG></xsl:if></TD>  -->
							                                <TD>
											<xsl:if test="rezult_cd = '2'"> <STRONG><FONT color="#000099">Payment is not approved </FONT></STRONG></xsl:if>
											<xsl:if test="rezult_cd = '1'"> <STRONG><FONT color="#000099">Payment was approved </FONT></STRONG></xsl:if>
											<xsl:if test="rezult_cd = '3'"> <STRONG><FONT color="#000099">Payment is not approved and error</FONT></STRONG></xsl:if>
											<xsl:if test="rezult_cd = ''"> 
											<xsl:if test="complete = 't'"> <STRONG><FONT color="#000099">Executed</FONT></STRONG></xsl:if><xsl:if test="complete = 'f'"> <STRONG><FONT color="#000099">Expects performance</FONT></STRONG></xsl:if>
											</xsl:if>
											</TD>
							
							                <TD align="right"  ><FORM name="order" action="AccountHistoryDetal.jsp" ><INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="amount_id"><xsl:attribute name="value"><xsl:value-of select="amount_id"/></xsl:attribute></INPUT><INPUT TYPE="submit" name="submit" value="Detailed"></INPUT></FORM></TD>
							              </TR>
										</xsl:for-each>
										 <TR>
										 	<TD colspan="5" align="center" class="none_border">
										 		<br/>
										 		<!-- 
										 		<a><xsl:attribute name="HREF"><xsl:value-of select="document/prev"/></xsl:attribute>
										 			<IMG height="15" alt="Back" title="Back" src="" width="15" border="0"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/previous.gif')"/></xsl:attribute></IMG>
										 		</a>
										 		<a><xsl:attribute name="HREF"><xsl:value-of select="document/next"/></xsl:attribute>
										 			<IMG height="15" alt="The following" title="The following" src="" width="15" border="0"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/next.gif')"/></xsl:attribute></IMG>
										 		</a>
										 		 -->
										 	</TD>
										 </TR>
										 <tr>
											<td class="none_border">
												<span class="next">
													<a HREF = "#" onClick="javascript:history.back()"  >
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
<font color="White" >Internet shop . Copyright 2024 
		<A HREF="http://www.cmsmanhattan.com"><font color="White">  FDIS Center Business Solutions Inc </font></A>.  All rights reserved
</font>
		</div>
		</DIV>
	</DIV>
	
	<div id="overlay"></div>
    <div id="dialog">
        <iframe id="iframe" src=""></iframe>
        <button onclick="closeDialog()">Close</button>
    </div>

</body>









</HTML>
</xsl:template>
</xsl:stylesheet>