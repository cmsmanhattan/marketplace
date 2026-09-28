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
	 <SCRIPT type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/caption.js')"/></xsl:attribute></SCRIPT>
	 <link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/menu.css')"/></xsl:attribute></link>
	 <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/menu.js')"/></xsl:attribute></script>
	 
	 <link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/showiframe.css')"/></xsl:attribute></link>
	 <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/showiframe.js')"/></xsl:attribute></script>
	 <link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/slider.css')"/></xsl:attribute></link>
	 <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/slider.js')"/></xsl:attribute></script>

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
			        				<xsl:if test="document/login != ''">   <!--  ���������� ���� ���� ����� -->
									<B>User</B> 
									 <a href="Authorization.jsp" style="margin-left: 5px; text-decoration: none">				                
									
								 	<xsl:if test="document/login = 'user'">   <!--  ���������� ���� ��� ������ -->
									<font class="user0">
										<xsl:value-of select="document/login"/>
									</font>
									</xsl:if>
									
									<xsl:if test="document/login != 'user'">   <!--  ���������� ���� ���� ����� -->
									<xsl:if test="document/role_id = 1"> <!--  ��������� ���� ���� -->
										<font class="user1">
											<xsl:value-of select="document/login"/>
									</font>
								</xsl:if>
											 			
								<xsl:if test="document/role_id = 2"><!--  ������� ���� ����� -->
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
							<h3>Ordering information</h3>
								<div class="box-indent">
								<div class="width">
								<P  style="font-size: 12px; margin-bottom: 5px;padding-top:5px">
								 Your order is controlled by security guards on internet 
                                 Best  regards
                                 Siteoneclick Team 
						        </P>												        
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
			            <H3>Important</H3>           	
		            		
		            		<P class="text" style="padding:15px 10px 26px 15px;"> There is fields  that have sign  " * "  they are required fields </P>               
					
			            </DIV>
			            </DIV>
			            </DIV>
			            </DIV>
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
							<div class="cab"><a href="Productlist.jsp?catalog_id=-2">Contunue shopping</a></div>               
						  	<div class="cab"><a><xsl:attribute name="HREF"><xsl:value-of select="document/to_account_history"/></xsl:attribute>Payments</a></div>
						  	<div class="cab"><A HREF="Purchases.jsp" >  Purchases </A></div>
						  	<div class="cab"><A HREF="Productlist.jsp?site=-4" > Resolution center </A></div>
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
			            <H3>Pay Order</H3>
			          	<P class="text" style="padding:10px 20px 13px;">
			          	 <A href="Policy.jsp?page=pay">  How can I pay my order </A>
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
							<xsl:if test="document/message != ''">
			        				<p align="center"> 			        				 >
			        				<font color="red" >
										<xsl:value-of select="document/message"/>
									</font>
									</p>
									<br/>
									<br/>
							</xsl:if>
							<DIV style="padding-left: 80px">
									     <h3 style="padding-left: 40px; font-size: 17px">Order: <xsl:value-of select="document/order_id"/></h3>
										    <DIV class="article_indent">
											    <table class="blog" cellpadding="0" cellspacing="0"> 
                              <tbody>
                                <tr>
                                  <td valign="top">
                                    <TABLE cellSpacing="0" cellPadding="0" width="100%">
                                              <TBODY>
                                                <TR>
                                                  <TD vAlign="top">
                                                      <div style="width: 100%"> 
                                                      <TABLE> <TR><TD>   
                                                         	<DIV style="padding-bottom: 0px;" >							
														<br/>
							                              
                              		<div class="binTytle">
                                    <h3>Your cart</h3>  
                                 	</div>
                            		

										    <DIV style="padding-bottom: 0px;" >	
										    <xsl:if test="document/quantity_product = '0'" > Empty cart</xsl:if>
									 	    <xsl:if test="document/quantity_product != '0'" > 
									 	    <xsl:if test="document/empty_page = 'true'" > On this page there is nothing. </xsl:if>
											<xsl:if test="document/empty_page != 'true'" > 
										    
										    <DIV class="bin">
											    	<xsl:for-each select="document/list/product">
													<table class="order">
													    <tbody>
													        <tr>
												            <td>	
																<IMG height="40" alt=""  width="40" border="0" style="margin-right: 10px"><xsl:attribute name="src"><xsl:value-of select="icon"/></xsl:attribute></IMG>			
														    </td>
												            <td align="left" width="500">
															<xsl:value-of select="name"/>:<xsl:value-of select="quantity"/> x <xsl:value-of select="amount"/>(<xsl:value-of select="currency/description"/>)
												            </td>
												            <td align="left" valign="center" width="100"  >
										 				      <xsl:if test="file_exist != ''"> 
						                                         <A><xsl:attribute name="href"><xsl:value-of select="product_url"/></xsl:attribute>Download file</A>
														      </xsl:if>
												            </td>
												            <td align="right" style="padding-right: 15px">
													        <FORM name="order_del" action="Order.jsp" method="POST" ><INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT><INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="position"><xsl:attribute name="value"><xsl:value-of select="basket_id"/></xsl:attribute></INPUT><INPUT size="12" TYPE="submit" class="button" name="submit" value="Delete"></INPUT></FORM>
													   	    </td>
													        </tr>
													    </tbody>
													</table>
													</xsl:for-each>										    
												</DIV>
											    </xsl:if>   <!-- ������� ������� � ������� �� ���� ��������   -->
								                
										        </xsl:if>  <!-- ������� ������� � �������  -->
											</DIV>
											<DIV style="border: 1px dashed black; width: 400px;">
												<DIV>
												<DIV style="float: left">
												<A><xsl:attribute name="HREF"><xsl:value-of select="document/prev"/></xsl:attribute><IMG height="15" alt="�����" title="�����" src="" width="15" border="0"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/previous.gif')"/></xsl:attribute></IMG></A>
											    </DIV>
											    <DIV  style="float: right">
											    <A><xsl:attribute name="HREF"><xsl:value-of select="document/next"/></xsl:attribute><IMG height="15" alt="���������" title="���������" src="" width="15" border="0"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/next.gif')"/></xsl:attribute></IMG></A>
											    </DIV>
												</DIV>
												<DIV style="text-align: center">
													<xsl:if test="document/empty_page = 'true'" >
														<font size="2" style="color:black">On this page there is nothing.</font>
													</xsl:if>										
													
													<xsl:if test="document/empty_page != 'true'" >
														<xsl:variable name="offset" select="number(substring(document/next,18))"/>
														
														<font size="2" style="color:black"> 
														Listing 
															<b>
																<xsl:value-of select="$offset+(-9)"/> 
																 - 
																<xsl:if test="$offset &lt; document/quantity_product" >
																	<xsl:value-of select="$offset"/>
																</xsl:if>
																<xsl:if test="$offset &gt; document/quantity_product" >
																	<xsl:value-of select="document/quantity_product"/>
																</xsl:if>														
															</b> 
															
														from <b><xsl:value-of select="document/quantity_product"/></b>
														</font> 
													</xsl:if>
												</DIV>
											</DIV>												 			
									 	                        
	                        </DIV>
	                         <DIV>
							
							         <br/>                         
                              		<div class="binTytle">
                                    <h3>Additional expenses</h3>  
                                 	</div>
                            		
                            
							
										   <DIV style="width: 400px;padding-bottom: 0px;">
											    	<form  ACTION="Order.jsp" method="POST">
												   <table class="orderInfo">
												   	<colgroup>
												   		<col width="340"/>
												   		<col width="140"/>
												   		<col width="60"/>
												   	</colgroup>
												    <tbody>
													<tr>
														<td bgColor="#EFEFEF" align="center" vAlign="top" >Name</td>
														<td bgColor="#EFEFEF" align="center" vAlign="top" >Sum</td>
														<td bgColor="#EFEFEF" align="center" vAlign="top" >Currency</td>
													</tr>
			                                       <xsl:if test="document/admin/post_manager = ''"> 
													<tr>
														<td colspan="3"  width="500" align="left" vAlign="top" ><INPUT SIZE="40" AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="order_paystatus" VALUE="1"  ></INPUT></td>
													</tr>
													</xsl:if>
													<tr>
														<td align="left" vAlign="top" >Order sum:</td>
														<td align="right" vAlign="top" ><STRONG><xsl:value-of select="document/order_amount"/></STRONG></td>
														<td align="left" vAlign="top" style="padding-left: 3px"><xsl:value-of select="document/currency_lable"/></td>
													</tr>
													<tr>
														<td align="left" vAlign="top" >Sum for delivery:</td>
														<td align="right" vAlign="top" ><STRONG><xsl:value-of select="document/delivery_amoun"/></STRONG></td>
														<td align="left" vAlign="top"  style="padding-left: 3px"><xsl:value-of select="document/currency_lable"/></td>
													</tr>
													<tr>
														<td align="left" vAlign="top" ><b>Total:</b> </td>
														<td align="right" vAlign="top" ><STRONG><xsl:value-of select="document/order_end_amount"/></STRONG></td>
														<td align="left" vAlign="top"  style="padding-left: 3px"><xsl:value-of select="document/currency_lable"/></td>
													</tr>
													<xsl:if test="not(document/role_id = 2 or document/role_id = 3 or document/role_id = 4)">
													<tr>
														<td align="left" vAlign="top" >Payment status:</td>
														<td align="left" vAlign="top" ><STRONG><xsl:value-of select="document/paystatus_lable"/></STRONG></td>
														<td align="left" vAlign="top" ></td>
													</tr>
													
													<tr>
														<td align="left" vAlign="top" >Order status:</td>
														<td align="left" vAlign="top" ><STRONG><xsl:value-of select="document/order_status_lable"/></STRONG></td>
														<td align="left" vAlign="top" ></td>
													</tr>
													</xsl:if>


													<xsl:if test="(document/role_id = 2 or document/role_id = 3 or document/role_id = 4)">
													<tr>
														<td align="left" vAlign="top" >Change the payment status: </td>
														<td colspan="2" align="left" vAlign="top" >
														<SELECT NAME = "order_paystatus" onChange="javascript:this.form.submit()" style="width: 170px">
												  	  	 <xsl:for-each select="document/paystatus/paystatus-item">
														 <OPTION>
															<xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute>
							                           		 			<xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if>
                             												<xsl:value-of select="item"/>
									   	          			 </OPTION>
													  	 </xsl:for-each>		
						                       						</SELECT></td>
													</tr>
													</xsl:if>


														<xsl:if test="(document/role_id = 2 or document/role_id = 3 or document/role_id = 4)">
													<tr>
														<td align="left" vAlign="top" >To change the order status: </td>
														<td colspan="2" align="left" vAlign="top" >
														<SELECT NAME = "order_status" onChange="javascript:this.form.submit()" style="width: 170px">
												  	  	 <xsl:for-each select="document/deliverystatus/deliverystatus-item">
														 <OPTION>
															<xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute>
							                           		 			<xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if>
                             												<xsl:value-of select="item"/>
									   	          			 </OPTION>
													  	 </xsl:for-each>		
						                       						</SELECT></td>
													</tr>
													</xsl:if>
												    </tbody>
													</table>	
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="status"  />
												</form>												    
											</DIV>			 			
									 	                        
	                        </DIV>
	                        </TD>
	                         <TD>
	                        
	                        <DIV><br/></DIV>
	                        <DIV>							
								
												                                 
					                              	<br/>                         
                              						<div class="binTytle">
					                                    <h3>Delivery address</h3>  
					                                 </div>
												<DIV style="width: 400px;padding-bottom: 0px;" class="address">
											    	<form method="post" ACTION="Order.jsp"  id="addressForm">
													<INPUT SIZE="40" AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="order_paystatus" ><xsl:attribute name="value"><xsl:value-of select="document/order_paystatus"/></xsl:attribute></INPUT>
												<TABLE class="contentpane aut" cellSpacing="0" cellPadding="0" border="0">															    													
					                       					<colgroup>
					                       						<col width="130px"/>
					                       						<col width="200px"/>
					                       					</colgroup>
					                       					<TR><TD>Country*</TD>
				                       						<TD align="left"  >
				                       						<SELECT NAME = "country_id" onChange="doChangeCity('country_id', this.value)"  id="country_id">
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
											            <TR><TD>City*</TD>
											            	<TD align="left" >
												                <SELECT NAME = "city_id" id="city_id">
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

                    								  
 
					<tr><td> Address*</td><td align="left" ><INPUT SIZE="40"  AUTOCOMPLETE="off" TYPE="TEXT" NAME="shipment_address"  id="shipment_address"><xsl:attribute name="value"><xsl:value-of select="document/shipment_address"/></xsl:attribute></INPUT></td></tr>
					<tr><td> Phone* </td><td align="left" ><INPUT SIZE="40" AUTOCOMPLETE="off" TYPE="TEXT" NAME="shipment_phone"  id="shipment_phone"><xsl:attribute name="value"><xsl:value-of select="document/shipment_phone"/></xsl:attribute></INPUT></td></tr>
					<tr><td> Contact name* </td><td align="left" ><INPUT SIZE="40"  AUTOCOMPLETE="off" TYPE="TEXT" NAME="contact_person"  id="contact_person"><xsl:attribute name="value"><xsl:value-of select="document/contact_person"/></xsl:attribute></INPUT></td>	</tr>
					<!-- Shipping company: each carrier is a site of its own in this system; the choice is saved with the order -->
					<tr><td> Shipping company </td><td align="left" ><SELECT NAME="shipping_company_id" style="width: 200px"><xsl:for-each select="document/shipping_company/shipping_company-item"><OPTION><xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute><xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if><xsl:value-of select="item"/></OPTION></xsl:for-each></SELECT><xsl:if test="document/shipping_company_host != ''"><xsl:text> </xsl:text><a target="_blank" href="http://{document/shipping_company_host}/"><xsl:value-of select="document/shipping_company_name"/></a></xsl:if></td></tr>
					<!-- Resolution center (dispute on this order); shown only when sql/resolution_center_migration.sql is applied -->
					<xsl:if test="document/resolution_center_enabled = 'true'">
					<tr><td> Resolution center </td><td align="left" ><SELECT NAME="resolution_center_id" style="width: 200px"><xsl:for-each select="document/resolution_center/resolution_center-item"><OPTION><xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute><xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if><xsl:value-of select="item"/></OPTION></xsl:for-each></SELECT><xsl:if test="document/resolution_center_host != ''"><xsl:text> </xsl:text><a target="_blank" href="http://{document/resolution_center_host}/"><xsl:value-of select="document/resolution_center_name"/></a></xsl:if><xsl:if test="document/resolution_status_lable != ''"><xsl:text> - </xsl:text><b><xsl:value-of select="document/resolution_status_lable"/></b></xsl:if></td></tr>
					</xsl:if>
					<tr><td> E-Mail *</td><td align="left" ><INPUT  title="On this e-mail the account will be sent" SIZE="40" AUTOCOMPLETE="off" id="shipment_email" TYPE="TEXT" NAME="shipment_email"  ><xsl:attribute name="value"><xsl:value-of select="document/shipment_email"/></xsl:attribute></INPUT></td></tr>
					<tr><td> Fax</td><td align="left" ><INPUT SIZE="40" AUTOCOMPLETE="off" TYPE="TEXT" NAME="shipment_fax"  ><xsl:attribute name="value"><xsl:value-of select="document/shipment_fax"/></xsl:attribute></INPUT></td></tr>
					<tr><td> Comments </td><td align="left" ><textarea rows="5" NAME="shipment_description"><xsl:value-of select="document/shipment_description"/></textarea></td></tr>
					


																
														<TR><TD><input size="40"  AUTOCOMPLETE="off" TYPE="hidden" NAME="redirect" VALUE="Policy.jsp">   </input>
														<input size="40"  AUTOCOMPLETE="off" TYPE="hidden" NAME="action" VALUE="save">   </input></TD> <TD></TD></TR>
														
														<TR  align="right" style="padding-top: 15px">
															<TD colspan="2" align="center">
																<DIV style="text-align: center; padding-left: 27px; padding-top: 5px">
																	 <TABLE class="regbut">
                                                                      <TR>
                                                                        <TD style="width:50px; padding-left: 140px;">
						<xsl:if test="document/role_id != 0">
						<input class="button" type="button" name="Submit" value="Checkout" onClick="isAddressValid()" ></input>
						</xsl:if>  
						<xsl:if test="document/role_id = 0">
						<input class="button" type="button" name="button" value="Checkout" onClick="if( confirm('For order creation it is necessary to be authorised') == true  ) parent.location='Authorization.jsp?Login=';" ></input>
						</xsl:if>  

                                                                        </TD>
                                                                        <td align="left" style="padding-left: 10px">
                                                                          <input  class="button" type="reset" value="Clean" ></input>
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
	                      <br/>
															New Arrival
    														 <div class="line2"></div><br/>
															
															<div class="scroll-container">
															  <button class="scroll-left" onclick="leftScrollNewArrival()">&#9664;</button>
															   <div class="scroll-content-new-arrival">
																	  <xsl:if test="count(document/new_arrival_list/new_arrival) != 0">
													                  <xsl:for-each select="document/new_arrival_list/new_arrival">
													                  <div class="item">
													                    <LI>  
																		<A><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute>
																   		     <SPAN>
																   		       <xsl:if test="image != ''" >
																   		   		   <img  alt=""  width="147" border="0">
																			       <xsl:attribute name="src">
																			       <xsl:value-of select="image"/>
																			       </xsl:attribute>
																			       </img>
																			       <br/>
																	               <br/>
																	             </xsl:if>    
																   		     <xsl:for-each select="description/r">
																		      	<xsl:value-of select="."/><BR/>
																		     </xsl:for-each></SPAN><br/>
																		 </A>
																		
																		<xsl:if test="/document/admin/post_manager != ''">
																			<table style="width:150px">
																			    <tbody>
																			        <tr style="padding-bottom: 15px">
																			         <td style="padding-bottom: 5px">
																			         <form name="product_del" style="width:50px" action="Productlist.jsp" method="POST">
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
																					<INPUT class="button"  TYPE="submit" name="submit" value="Delete"></INPUT>
																			         </form>
																			         </td>
																			         <td width="5px"></td>
																			         <td>
																	
																			        <form name="product_edit" style="width:50px"  action="Productlist.jsp" method="POST">
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="news"  ></INPUT>
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
																					<INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
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
															  <button class="scroll-right" onclick="rightScrollNewArrival()">&#9654;</button>
															</div>	
															
															<br/>
															Recommended items
    														<div class="line2"></div><br/>
															
															<div class="scroll-container">
															  <button class="scroll-left" onclick="leftScrollRecommendedItems()">&#9664;</button>
															   <div class="scroll-content-recommended-items">
																	  <xsl:if test="count(document/recommentedItems/recommented) != 0">
													                  <xsl:for-each select="document/recommentedItems/recommented">
													                  <div class="item">
													                    <LI>  
																		<A><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute>
																   		     <SPAN>
																   		       <xsl:if test="image != ''" >
																   		   		   <img  alt=""  width="147" border="0">
																			       <xsl:attribute name="src">
																			       <xsl:value-of select="image"/>
																			       </xsl:attribute>
																			       </img>
																			       <br/>
																	               <br/>
																	             </xsl:if>    
																   		     <xsl:for-each select="description/r">
																		      	<xsl:value-of select="."/><BR/>
																		     </xsl:for-each></SPAN><br/>
																		 </A>
																		
																		<xsl:if test="/document/admin/post_manager != ''">
																			<table style="width:150px">
																			    <tbody>
																			        <tr style="padding-bottom: 15px">
																			         <td style="padding-bottom: 5px">
																			         <form name="product_del" style="width:50px" action="Productlist.jsp" method="POST">
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
																					<INPUT class="button"  TYPE="submit" name="submit" value="Delete"></INPUT>
																			         </form>
																			         </td>
																			         <td width="5px"></td>
																			         <td>
																	
																			        <form name="product_edit" style="width:50px"  action="Productlist.jsp" method="POST">
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="news"  ></INPUT>
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
																					<INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
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
															  <button class="scroll-right" onclick="rightScrollRecommendedItems()">&#9654;</button>
															</div>		
															
															<br/>
															Sponsored by seller
    														<div class="line2"></div><br/>
															
															<div class="scroll-container">
															  <button class="scroll-left" onclick="leftScrollSponsoredBySeller()">&#9664;</button>
															   <div class="scroll-content-sponsored-by-seller">
																	  <xsl:if test="count(document/sponsoredBySellersItems/sponsored) != 0">
													                  <xsl:for-each select="document/sponsoredBySellersItems/sponsored">
													                  <div class="item">
													                    <LI>  
																		<A><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute>
																   		     <SPAN>
																   		       <xsl:if test="image != ''" >
																   		   		   <img  alt=""  width="147" border="0">
																			       <xsl:attribute name="src">
																			       <xsl:value-of select="image"/>
																			       </xsl:attribute>
																			       </img>
																			       <br/>
																	               <br/>
																	             </xsl:if>    
																   		     <xsl:for-each select="description/r">
																		      	<xsl:value-of select="."/><BR/>
																		     </xsl:for-each></SPAN><br/>
																		 </A>
																		
																		<xsl:if test="/document/admin/post_manager != ''">
																			<table style="width:150px">
																			    <tbody>
																			        <tr style="padding-bottom: 15px">
																			         <td style="padding-bottom: 5px">
																			         <form name="product_del" style="width:50px" action="Productlist.jsp" method="POST">
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
																					<INPUT class="button"  TYPE="submit" name="submit" value="Delete"></INPUT>
																			         </form>
																			         </td>
																			         <td width="5px"></td>
																			         <td>
																	
																			        <form name="product_edit" style="width:50px"  action="Productlist.jsp" method="POST">
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="news"  ></INPUT>
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
																					<INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
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
															  <button class="scroll-right" onclick="rightScrollSponsoredBySeller()">&#9654;</button>
															</div>
															<br/>
															Recently reviewed
    														<div class="line2"></div><br/>
															
															<div class="scroll-container">
															  <button class="scroll-left" onclick="leftScrollSponsoredBySeller()">&#9664;</button>
															   <div class="scroll-content-sponsored-by-seller">
																	  <xsl:if test="count(document/recentlyReviewedItems/reviewed) != 0">
													                  <xsl:for-each select="document/recentlyReviewedItems/reviewed">
													                  <div class="item">
													                    <LI>  
																		<A><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute>
																   		     <SPAN>
																   		       <xsl:if test="image != ''" >
																   		   		   <img  alt=""  width="147" border="0">
																			       <xsl:attribute name="src">
																			       <xsl:value-of select="image"/>
																			       </xsl:attribute>
																			       </img>
																			       <br/>
																	               <br/>
																	             </xsl:if>    
																   		     <xsl:for-each select="description/r">
																		      	<xsl:value-of select="."/><BR/>
																		     </xsl:for-each></SPAN><br/>
																		 </A>
																		
																		<xsl:if test="/document/admin/post_manager != ''">
																			<table style="width:150px">
																			    <tbody>
																			        <tr style="padding-bottom: 15px">
																			         <td style="padding-bottom: 5px">
																			         <form name="product_del" style="width:50px" action="Productlist.jsp" method="POST">
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
																					<INPUT class="button"  TYPE="submit" name="submit" value="Delete"></INPUT>
																			         </form>
																			         </td>
																			         <td width="5px"></td>
																			         <td>
																	
																			        <form name="product_edit" style="width:50px"  action="Productlist.jsp" method="POST">
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="news"  ></INPUT>
																					<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
																					<INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
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
															  <button class="scroll-right" onclick="rightScrollSponsoredBySeller()">&#9654;</button>
															</div>
                                                      </div>
                                                  </TD>
                                                </TR>
                                                <tr>
                                                  <td><br/></td>
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