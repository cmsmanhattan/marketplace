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
	<script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/datepicker.js')"/></xsl:attribute></script>
	
	 <link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/menu.css')"/></xsl:attribute></link>
	 <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/menu.js')"/></xsl:attribute></script>
	 
	<link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/showiframe.css')"/></xsl:attribute></link>
	<script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/showiframe.js')"/></xsl:attribute></script>
	
	
	<script type="text/javascript">
	$(function() {
		$("#datepicker1").datepicker({showOn: 'button', buttonImage: '<xsl:value-of select="concat('xsl/',$host,'/images/calendar.png')"/>', buttonImageOnly: true,
		 	onSelect: function(){ 
		 		var inp_date = $("#datepicker1").datepicker("getDate");			 		
		 		document.getElementById('datefrom').value = inp_date.valueOf();	
		 				 		
		 	}
		});
	});
	
	$(function() {
		$("#datepicker2").datepicker({showOn: 'button', buttonImage: '<xsl:value-of select="concat('xsl/',$host,'/images/calendar.png')"/>', buttonImageOnly: true,
			onSelect: function(){ 
		 		var inp_date = $("#datepicker2").datepicker("getDate");
		 		document.getElementById('dateto').value = inp_date.valueOf();			 		
		 	}
		});
	});
	</script>
</HEAD>


<body id="body">

	<xsl:attribute name="onload"><xsl:value-of select="concat('setDates(',$datefrom,',',$dateto, ');')"/></xsl:attribute>
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
	      				 
	      				<LI >
	  					<A href="Notifications.jsp">
	  					<SPAN>	
							<svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#F3F3F3"><path d="M126-166v-126h68v-246q0-96 55.5-173T397-811v-20q0-34.58 24.21-58.79T480-914q34.58 0 58.79 24.21T563-831v20q93 23 148 99.5T766-538v246h68v126H126Zm354-329Zm1 460q-37.95 0-64.98-26.73Q389-88.46 389-126h183q0 38-26.73 64.5T481-35ZM320-292h320v-246q0-66-47-113t-113-47q-66 0-113 47t-47 113v246Z"/></svg>
						</SPAN>
	  					</A>
	  					</LI>
	  					
	  					<LI >
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




			    <DIV class="indent" >
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
					<div id="left">
					<DIV class="module" style="margin-right:0px">
			            <DIV class="first">
			            <DIV class="sec">
			            <DIV>
			            <H3>Search orders</H3>           	
		            		<div style="padding:10px 0px 26px 25px"> 
		            		<FORM name="searchcreform"  action="OrderList.jsp"  method="POST">				
						  	   							
						  	   							<TABLE class="poll_s3" cellSpacing="0" cellPadding="1" width="95%" border="0">
													        
													        <TBODY>
													        <TR>
													          <TD align="middle">
													            <TABLE class="pollstableborder_s3" cellSpacing="0" cellPadding="0" border="0">
													            <TBODY>
													              <TR>
													                 <TD class="width sectiontableentry2" vAlign="top">
													                	<LABEL for="voteid25">from date: </LABEL>
													                </TD>
													              </TR>
													              <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="searchquery" value="2"></INPUT>
													              <TR>
													                 <TD class="width sectiontableentry2" vAlign="top">
													                 	<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="datefrom" id="datefrom"></INPUT>
													                	<INPUT id="datepicker1" size="7" readonly="true"  type="text" style="width:127px; color: black"></INPUT>
													                	
													                </TD>
													              </TR>
													              
													              <TR>
													                 <TD class="width sectiontableentry2" vAlign="top">
													                	<LABEL for="voteid25">before date: </LABEL>
													                </TD>
													              </TR>
													              <TR>
													                 <TD class="width sectiontableentry2" vAlign="top">
													                 	<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" id="dateto" NAME="dateto"></INPUT>
													                	<INPUT id="datepicker2" size="7"  readonly="true" type="text" style="width:127px; color: black"></INPUT>
													                </TD>
													              </TR>
													              										           												                                                         												           																		            																		            																            															              																											      
													      </TBODY></TABLE></TD></TR>
													        <TR>
													          <TD>
													            <DIV style="padding-left:20px; padding-top: 10px">
																	<INPUT class="button"  type="submit" size="20" value="Search"  tabindex="30002"   />
													            </DIV>
													            </TD></TR></TBODY>
															</TABLE>
														  </FORM> </div>           
					
			            </DIV>
			            </DIV>
			            </DIV>
			            </DIV>
					
					<!-- пїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ -->
			           
			            <DIV class="module" style="margin-right:0px">
			            <DIV class="first">
			            <DIV class="sec">
			            <DIV>
			            <H3>Search order</H3>           	
		            		<div style="padding:10px 0px 26px 25px"> 
		            		<FORM name="order" action="Order.jsp" method="POST" >	 						
						  	   							
	   						<TABLE class="poll" cellSpacing="0" cellPadding="1" width="95%" border="0">
					        <THEAD>
					        <TR>
					          <TD style="FONT-WEIGHT: bold">Enter your order number
					        </TD></TR></THEAD>
					        <TBODY>
					        <TR>
					          <TD align="middle">
					            <TABLE class="pollstableborder" cellSpacing="0" cellPadding="0" border="0">
					            	<TBODY>            	 
					              <TR>
					                 <TD class="width sectiontableentry2" vAlign="top">
					                 	<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="TEXT" NAME="order_id" style="width:147px"><xsl:attribute name="value"><xsl:value-of select="document/list/order/order_id"/></xsl:attribute></INPUT>													                	
					                </TD>
					              </TR>
					              										           												                                                         												           																		            																		            																            															              																											      
					      </TBODY></TABLE></TD></TR>
					        <TR>
					          <TD class="find">
					            <DIV style="padding-left:30px; padding-top: 10px">
									<INPUT class="button"  type="submit" size="20" value="Search"  tabindex="30002"   />
					            </DIV></TD></TR></TBODY>
							</TABLE>
						  </FORM>
						 
						</div>
			            </DIV>
			            </DIV>
			            </DIV>
			            </DIV>
			            
			             <xsl:if test="document/role_id = 2">
			            <DIV class="module" style="margin-right:0px">
			            <DIV class="first">
			            <DIV class="sec">
			            <DIV>
			            <H3>Search order by status</H3>           	
		            		<div style="padding:10px 0px 26px 25px"> 
		            		<FORM name="order" action="OrderList.jsp" method="POST" >	 						
						    <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="searchquery" value="3"></INPUT>	   							
	   						<TABLE class="poll" cellSpacing="0" cellPadding="1" width="95%" border="0">
					        <TBODY>
					        <TR>
					          <TD align="middle">
					            <TABLE class="pollstableborder" cellSpacing="0" cellPadding="0" border="0">
					            	<TBODY>            	 
					             					<TR>
					          						<TD style="FONT-WEIGHT: bold">the payment status</TD>
					        						</TR>
													<tr>
														<td  align="left" vAlign="top" >
														<SELECT NAME = "order_paystatus"  style="width: 170px">
												  	  	 <xsl:for-each select="document/paystatus/paystatus-item">
														 <OPTION>
															<xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute>
							                           		 			<xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if>
                             												<xsl:value-of select="item"/>
									   	          			 </OPTION>
													  	 </xsl:for-each>		
						                       			</SELECT></td>
													</tr>
													<TR>
					          						<TD style="FONT-WEIGHT: bold">the order status</TD>
					        						</TR>	
													<tr>
														<td  align="left" vAlign="top" >
														<SELECT NAME = "order_status"  style="width: 170px">
												  	  	 <xsl:for-each select="document/deliverystatus/deliverystatus-item">
														 <OPTION>
															<xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute>
							                           		 			<xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if>
                             												<xsl:value-of select="item"/>
									   	          			 </OPTION>
													  	 </xsl:for-each>		
						                       			</SELECT></td>
													</tr>
					              										           												                                                         												           																		            																		            																            															              																											      
					      </TBODY></TABLE></TD></TR>
					        <TR>
					          <TD class="find">
					            <DIV style="padding-left:30px; padding-top: 10px">
									<INPUT class="button"  type="submit" size="20" value="Search"  tabindex="30002"   />
					            </DIV></TD></TR></TBODY>
							</TABLE>
						  </FORM>
						 
						</div>
			            </DIV>
			            </DIV>
			            </DIV>
			            </DIV>
			            </xsl:if>
			</div>
					<div id="right">
			           
			           <DIV class="module" style="margin-right:0px">
			            <DIV class="first">
			            <DIV class="sec">
			            <DIV>
			            <H3>My Account</H3>
			          	
						  	<div style="padding:5px 0px 26px"> 
						  	<div class="cab" style="color: black;"><b>Available funds: </b> <xsl:value-of select="document/balans"/></div> 
						  	<div class="cab"><a><xsl:attribute name="HREF"><xsl:value-of select="document/to_order_hist"/></xsl:attribute>All orders</a></div>
						  	<div class="cab"><a><xsl:attribute name="HREF"><xsl:value-of select="document/to_order"/></xsl:attribute>The current order</a></div>	
						  	<div class="cab"><a><xsl:attribute name="HREF"><xsl:value-of select="document/to_account_history"/></xsl:attribute>Payments</a></div>				      												             		    								
						  	<div class="cab"><A HREF="Purchases.jsp" >  Purchases </A></div>
							<div class="cab"><A HREF="Notifications.jsp" >Notifications </A></div>
							<div class="cab"><A HREF="Offers.jsp" > Offers </A></div>
							<div class="cab"><A HREF="ActionBids.jsp" >Action bids </A></div>	
						  	</div> 
						  	            
			            </DIV>
			            </DIV>
			            </DIV>
			            </DIV>
			            
			            
			            <DIV class="module" style="margin-right:0px">
			            <DIV class="first">
			            <DIV class="sec">
			            <DIV>
			            <H3>Search payments</H3>           	
		            		<div style="padding:10px 0px 26px 25px"> 
		            		<FORM name="searchcreform"  action="OrderList.jsp"  method="POST">				
						  	   							
						  	   							<TABLE class="poll_s3" cellSpacing="0" cellPadding="1" width="95%" border="0">
													        
													        <TBODY>
													        <TR>
													          <TD align="middle">
													            <TABLE class="pollstableborder_s3" cellSpacing="0" cellPadding="0" border="0">
													            <TBODY>
													              <TR>
													                 <TD class="width sectiontableentry2" vAlign="top">
													                	<LABEL for="voteid25">from date: </LABEL>
													                </TD>
													              </TR>
													              <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="searchquery" value="2"></INPUT>
													              <TR>
													                 <TD class="width sectiontableentry2" vAlign="top">
													                 	<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="datefrom" id="datefrom"></INPUT>
													                	<INPUT id="datepicker1" size="7" readonly="true"  type="text" style="width:127px; color: black"></INPUT>
													                	
													                </TD>
													              </TR>
													              
													              <TR>
													                 <TD class="width sectiontableentry2" vAlign="top">
													                	<LABEL for="voteid25">before date: </LABEL>
													                </TD>
													              </TR>
													              <TR>
													                 <TD class="width sectiontableentry2" vAlign="top">
													                 	<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" id="dateto" NAME="dateto"></INPUT>
													                	<INPUT id="datepicker2" size="7"  readonly="true" type="text" style="width:127px; color: black"></INPUT>
													                </TD>
													              </TR>
													              										           												                                                         												           																		            																		            																            															              																											      
													      </TBODY></TABLE></TD></TR>
													        <TR>
													          <TD>
													            <DIV style="padding-left:20px; padding-top: 10px">
																	<INPUT class="button"  type="submit" size="20" value="Search"  tabindex="30002"   />
													            </DIV>
													            </TD></TR></TBODY>
															</TABLE>
														  </FORM> </div>           
					
			            </DIV>
			            </DIV>
			            </DIV>
			            </DIV>
			            
			            
			            
			             <DIV class="module" style="margin-right:0px">
			            <DIV class="first">
			            <DIV class="sec">
			            <DIV>
			            <H3>Find payment</H3>           	
		            		<div style="padding:10px 0px 26px 25px"> 
		            		<FORM name="order" action="Order.jsp" method="POST" >	 						
						  	   							
	   						<TABLE class="poll" cellSpacing="0" cellPadding="1" width="95%" border="0">
					        <THEAD>
					        <TR>
					          <TD style="FONT-WEIGHT: bold">Enter your payment transation number
					        </TD></TR></THEAD>
					        <TBODY>
					        <TR>
					          <TD align="middle">
					            <TABLE class="pollstableborder" cellSpacing="0" cellPadding="0" border="0">
					            	<TBODY>            	 
					              <TR>
					                 <TD class="width sectiontableentry2" vAlign="top">
					                 	<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="TEXT" NAME="order_id" style="width:147px"><xsl:attribute name="value"><xsl:value-of select="document/list/order/order_id"/></xsl:attribute></INPUT>													                	
					                </TD>
					              </TR>
					              										           												                                                         												           																		            																		            																            															              																											      
					      </TBODY></TABLE></TD></TR>
					        <TR>
					          <TD class="find">
					            <DIV style="padding-left:30px; padding-top: 10px">
									<INPUT class="button"  type="submit" size="20" value="Search"  tabindex="30002"   />
					            </DIV></TD></TR></TBODY>
							</TABLE>
						  </FORM>
						 
						</div>
			            </DIV>
			            </DIV>
			            </DIV>
			            </DIV>
			            
			             <xsl:if test="document/role_id = 2">
			            <DIV class="module" style="margin-right:0px">
			            <DIV class="first">
			            <DIV class="sec">
			            <DIV>
			            <H3>Search payment by status</H3>           	
		            		<div style="padding:10px 0px 26px 25px"> 
		            		<FORM name="order" action="OrderList.jsp" method="POST" >	 						
						    <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="searchquery" value="3"></INPUT>	   							
	   						<TABLE class="poll" cellSpacing="0" cellPadding="1" width="95%" border="0">
					        <TBODY>
					        <TR>
					          <TD align="middle">
					            <TABLE class="pollstableborder" cellSpacing="0" cellPadding="0" border="0">
					            	<TBODY>            	 
					             					<TR>
					          						<TD style="FONT-WEIGHT: bold">the payment status</TD>
					        						</TR>
													<tr>
														<td  align="left" vAlign="top" >
														<SELECT NAME = "order_paystatus"  style="width: 170px">
												  	  	 <xsl:for-each select="document/paystatus/paystatus-item">
														 <OPTION>
															<xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute>
							                           		 			<xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if>
                             												<xsl:value-of select="item"/>
									   	          			 </OPTION>
													  	 </xsl:for-each>		
						                       			</SELECT></td>
													</tr>
													<TR>
					          						<TD style="FONT-WEIGHT: bold">the order status</TD>
					        						</TR>	
													<tr>
														<td  align="left" vAlign="top" >
														<SELECT NAME = "order_status"  style="width: 170px">
												  	  	 <xsl:for-each select="document/deliverystatus/deliverystatus-item">
														 <OPTION>
															<xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute>
							                           		 			<xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if>
                             												<xsl:value-of select="item"/>
									   	          			 </OPTION>
													  	 </xsl:for-each>		
						                       			</SELECT></td>
													</tr>
					              										           												                                                         												           																		            																		            																            															              																											      
					      </TBODY></TABLE></TD></TR>
					        <TR>
					          <TD class="find">
					            <DIV style="padding-left:30px; padding-top: 10px">
									<INPUT class="button"  type="submit" size="20" value="Search"  tabindex="30002"   />
					            </DIV></TD></TR></TBODY>
							</TABLE>
						  </FORM>
						 
						</div>
			            </DIV>
			            </DIV>
			            </DIV>
			            </DIV>
			            </xsl:if>
			            
			            <!--  
			            <DIV class="module" style="margin-right:0px">
			            <DIV class="first">
			            <DIV class="sec">
			            <DIV>
			            <H3>Pay Order</H3>
			          	<P class="text" style="padding:10px 20px 13px;">
			          	 <A href="Policy.jsp?page=pay"> How can I pay my order </A>
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
			            
						-->
						
		
						
							
							
			
</div>
			
<div id="container">
<div class="comp-cont">
		<table class="blog" cellpadding="0" cellspacing="0">
			<tr>
			<td valign="top">
				<div class="article-bg">
					<div class="article-left">
						<div class="article-right">
							<DIV style="padding-left: 20px; ">
							     <h3 style="padding-left: 20px; font-size: 17px">The payment details</h3>
							     <br/>
								    <DIV >
									<div class="box">
											  <div class="body">
											    <div >
												<table >
												    <tbody>
												       <TR><TD></TD><TD></TD></TR>
										                       <TR><TD>The rest on the account before operation       </TD><TD  align="right" ><xsl:value-of select="document/payment/old_amount"   /></TD><TD  align="left" ><xsl:value-of select="document/payment/currency_old_lable"   /></TD></TR>
										                       <TR><TD>The operation sum                      		</TD><TD  align="right" ><xsl:value-of select="document/payment/add_amount"   /></TD><TD  align="left" ><xsl:value-of select="document/payment/currency_add_lable"   /></TD></TR>
										                       <TR><TD>The rest on the account after operation		</TD><TD  align="right" ><xsl:value-of select="document/payment/total_amount" /></TD><TD  align="left" ><xsl:value-of select="document/payment/currency_total_lable" /></TD></TR>
												    </tbody>
												</table>
											    </div>
											  </div>
											</div>
									
											<div class="box">
											  <div class="body">
											    <div >
												<table >
												    <tbody>
									                           <TR><TD></TD><TD></TD></TR>
										                       <TR><TD width="40%" >Operation date started	</TD><TD width="60%" ><xsl:value-of select="document/payment/date_input"   /></TD></TR>
										                       <TR><TD>Operation date closed				</TD><TD ><xsl:value-of select="document/payment/date_end"     /></TD></TR>
										                       <TR><TD>The operation description			</TD><TD ><xsl:value-of select="document/payment/decsription"  /></TD></TR>
										                       <TR><TD width="100%"  colspan="2" >IP The address of the client <xsl:value-of select="document/payment/user_ip"  /></TD></TR>
										                       <TR><TD>The full information on it IP </TD><TD > <A href="http://www.ripn.net:8082/nic/whois/" >Service "Whois" </A></TD></TR>
										                       <TR><TD>Data about system of the client </TD><TD ><xsl:value-of select="document/payment/user_header"  /></TD></TR>
										                       <TR><TD>The status of performance of operation </TD><TD width="60%" ><xsl:if test="document/payment/complete = 't'"> <STRONG><FONT color="#000099">Executed </FONT></STRONG></xsl:if><xsl:if test="document/payment/complete = 'f'"> <STRONG><FONT color="#000099">The result is expected </FONT></STRONG></xsl:if></TD></TR>
										                       <TR><TD></TD><TD></TD><TD></TD><TD></TD><TD></TD><TD></TD></TR> 
												    </tbody>
												</table>
									
											    </div>
											  </div>
										    </div>

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