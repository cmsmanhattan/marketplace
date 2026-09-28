<?xml version='1.0' encoding='windows-1251' ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"  xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:output method="html" indent="yes"/>
<xsl:output encoding="UTF-8"/>
<xsl:strip-space elements="*"/>

<xsl:template match="/">
	<xsl:variable name="host" select="string(document/host)"/>
	<xsl:variable name="page" select="number(document/offset)"/>
	<xsl:variable name="user_id" select="number(/document/owner_user_id)"/> 
	<xsl:variable name="role" select="document/role_id"/> 
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
	 
</HEAD>


<body id="body">

<div class="main" style="background-color: #E7E7DF">
				<IMG alt="Logo"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/logo.gif')"/></xsl:attribute></IMG>
			</div>
	<div id="gradient">
		<div class="main">
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
	  				
		
						 <xsl:if test="document/role_id != 0">
	        			 <LI ><A href="Productlist.jsp?action=logoff">
	        	 		 <SPAN>Exit</SPAN></A>
	        	 		 </LI>
	      				 </xsl:if>
	      				 
	      				 <LI>
	      				 
	      				 <A href="Order.jsp"> 
	        			 <IMG border="0" height="40" width="40"  style="margin: -10px;" >
	        	 		 <xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/empty-cart-light.png')"/></xsl:attribute></IMG>
	        	 		 </A>
	        	 		  
	      				 </LI>
	      				 
	      				 <!--
	      				 <div style="padding:5px 2px 0px;" >
	        			 <A href="Order.jsp"> 
	        			 <IMG border="0" height="40" width="40"  style="margin: 15px;" >
	        	 		 <xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/empty-cart-light.png')"/></xsl:attribute></IMG>
	        	 		 </A>
	        	 		 </div>
	      				   -->
	      				 
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
			        				<xsl:if test="document/login != ''">   <!--  показывать если есть логин -->
									<B>User</B> 
									 <a href="Authorization.jsp" style="margin-left: 5px; text-decoration: none">				                
									
								 	<xsl:if test="document/login = 'user'">   <!--  показывать если нет логина -->
									<font class="user0">
										<xsl:value-of select="document/login"/>
									</font>
									</xsl:if>
									
									<xsl:if test="document/login != 'user'">   <!--  показывать если есть логин -->
									<xsl:if test="document/role_id = 1"> <!--  оранжевый если юзер -->
										<font class="user1">
											<xsl:value-of select="document/login"/>
									</font>
								</xsl:if>
											 			
								<xsl:if test="document/role_id = 2"><!--  красный если админ -->
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
						     <xsl:if test="count(document/parent/parent-item) &gt; 0">
                                    
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
							<h3>News</h3>
								<div class="box-indent">
								<div class="width">
								
								 <xsl:if test="count(document/newslist/news) != 0">
				                  <UL class="menu">
				                  <xsl:for-each select="document/newslist/news">
				                    <LI>                   
									<A><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute>
						        	
								      <!-- Обрабатываем перевод строки  -->
						             
							   		     <SPAN><xsl:for-each select="description/r">
									      	<xsl:value-of select="."/><BR/>
									     </xsl:for-each></SPAN></A>
						             				
									<!-- Кнопки удаления  , начало -->
									
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
								   <!-- Кнопки удаления  , конец  -->
				                    </LI>                    
				                  </xsl:for-each> 
				                  <LI>
				                    <A title="All news" href="Productlist.jsp?catalog_id=-1">
				                    <SPAN>News Archive</SPAN>
				                    </A>
				                    
				                   
				                  </LI> 
				                  </UL>
				                  </xsl:if>
				                  
                  
													
								</div>
							</div>
							
						</div>
					</div>
					</div>
								
				
				
				    <xsl:if test="document/empty_page_ext1 = 'false'" >
					<xsl:if test="count(document/extpolicy_productlist2/extpolicy_product2) != 0" >
					<div class="module">
					<div class="first">
						<div class="sec">
							<h3>More</h3>
							<div class="box-indent">
								<div class="width">
								
									<TABLE class="poll">
						              <TBODY>
						              <TR>
						                <TD class="over">
						                  <UL class="mostread_s3">
						                  <xsl:for-each select="document/extpolicy_productlist2/extpolicy_product2">
						                    <LI class="mostread_s3">
						                    <xsl:if test="image != ''" >
									   	        <xsl:if test="big_image_type != 'swf'">
									   	           <xsl:if test="big_image_type != 'flv'">
						                    <A class="mostread_s3"><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute><br/>
										       <!-- Обрабатываем перевод строки  -->
										       <img  alt=""  width="147" border="0">
										       <xsl:attribute name="src">
										       <xsl:value-of select="image"/>
										       </xsl:attribute>
										       </img>
								               <br/>
								                <xsl:if test="count(description/r) > 1">
							               		<xsl:if test="string-length(description/r[1]) > 3">
									                	<IMG style="margin-right: 5px;margin-bottom:2px;">
	                                       				<xsl:attribute name="src">
	                                         			<xsl:value-of select="concat('xsl/',$host,'/images/list-item-bg.gif')"/>
	                                      				</xsl:attribute>
                                      			</IMG>
                                      			</xsl:if>
							              		</xsl:if>   
                                     				
								               <U>
								   		       <xsl:for-each select="description/r">
										       <xsl:value-of select="."/> 
										       </xsl:for-each>
								               </U>
										       </A>
								               </xsl:if>
								               </xsl:if>
								          
									    </xsl:if>
								
								            <xsl:if test="image = ''" >
								                <A class="mostread_s3"  ><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute><br/>
										      <!-- Обрабатываем перевод строки  -->
										      <xsl:if test="count(description/r) > 1"> 
							                  	<xsl:if test="string-length(description/r[1]) > 3">
										              <IMG style="margin-right: 5px;margin-bottom:2px;">
		                                       				<xsl:attribute name="src">
		                                         			<xsl:value-of select="concat('xsl/',$host,'/images/list-item-bg.gif')"/>
		                                      				</xsl:attribute>
		                                     		  </IMG>
                                     		  	 </xsl:if>
                                     		  </xsl:if>	
								              <U>
									   		      <xsl:for-each select="description/r">
											      <xsl:value-of select="."/> <br/>
											      </xsl:for-each>
								              </U>
												</A>
										   </xsl:if>	
										 <!-- Кнопки удаления  , начало -->
										<xsl:if test="/document/admin/post_manager != ''">	
										<table><tr><td width="60px" style="padding-bottom: 5px; padding-top: 5px">																
											        <form name="product_del"  action="Productlist.jsp" method="POST">
														<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
														<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
														<INPUT class="button" TYPE="submit" name="submit" value="Delete"></INPUT>
											        </form>
											        </td>
											        <td width="5px">
											        </td>
											        <td style="padding-bottom: 5px; padding-top: 5px">
											        <form name="product_edit"  action="Productlist.jsp" method="POST">
														<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
														<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="ext2" ></INPUT>
														<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
														<INPUT  class="button" TYPE="submit" name="submit" value="Edit" style="margin-left: 0px"></INPUT>
											        </form>
										</td></tr></table>	
										</xsl:if>
									   <!-- Кнопки удаления  , конец  -->														
								        </LI>
								        </xsl:for-each>      
						               <LI class="mostread_s3" >
						              	   <A class="mostread_s3"  title="All news"   href="Productlist.jsp?catalog_id=-1">
									        	 <IMG style="margin-right: 5px;margin-bottom:2px;">
                                       				<xsl:attribute name="src">
                                         			<xsl:value-of select="concat('xsl/',$host,'/images/list-item-bg.gif')"/>
                                      				</xsl:attribute>
                                     			</IMG>	
								                	
								                <u>News Archive</u>
									        </A>
								        </LI> 
								         
						                              
						            </UL></TD></TR></TBODY></TABLE>					
								</div>
							</div>
						</div>
					
					</div>
					
				</div></xsl:if></xsl:if>
			</div>
			

				
				
					<div id="right">
					            <xsl:if test="document/login = 'user'">
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
						                  
						                  <INPUT id="modlgn_username" title="User" class="inputbox" tabindex="10001"  AUTOCOMPLETE="off" TYPE="TEXT" NAME="Login" >
														<xsl:attribute name="value">
															<xsl:value-of select="document/login"/>
														</xsl:attribute>		
										  </INPUT> 
										  </P>
						                  
						                  <P id="form-login-password">
						                  <LABEL for="modlgn_passwd">Password</LABEL><BR/>
						                  <INPUT class="inputbox" title="Password" id="modlgn_passwd" tabindex="10002" AUTOCOMPLETE="off" TYPE="PASSWORD" NAME="Passwd1" ></INPUT> 
						                  </P>
						                  
						                  <div style="padding-left: 35px; padding-bottom: 5px;border: none; width: 100px">
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
						</xsl:if>
						
						
						<!-- разделы (для админа) -->
			            <xsl:if test="document/role_id != 0"><!--  если админ -->
			            <DIV class="module" style="margin-right:0px">
			            <DIV class="first">
			            <DIV class="sec">
			            <DIV>
			            <H3>My Account</H3>
			          	
						  	<div style="padding:5px 0px 26px"> 
						  	<div class="cab"><a><xsl:attribute name="HREF"><xsl:value-of select="document/to_order_hist"/></xsl:attribute>All orders</a></div>
						  	<div class="cab"><a><xsl:attribute name="HREF"><xsl:value-of select="document/to_order"/></xsl:attribute>The current order</a></div>	
						  	<div class="cab"><a href="Policy.jsp?page=pay">Pay Order</a></div>
						  	<!-- <div class="cab"><a><xsl:attribute name="HREF"><xsl:value-of select="document/to_account_history"/></xsl:attribute>Monetary operations</a></div>				      												             		    								
						  	<div class="cab" style="color: black;"><b>Available funds: </b> <xsl:value-of select="document/balans"/> рублей .</div> -->
						  	</div> 
						  	            
			            </DIV>
			            </DIV>
			            </DIV>
			            </DIV>
			            
			            
			            <DIV style="">
			            <DIV class="module" style="height : expression(parentNode.clientHeight > 300? '347px' : 'auto');max-height: 347px;height: auto;">
						<DIV class="first"  >
						<DIV class="sec" style="height : expression(parentNode.clientHeight > 300? '347px' : 'auto');max-height: 347px;height: auto;">
						 <H3>Sections</H3>
			            <TABLE>
			            
			            <TBODY>
			              <TR>
			                <TD class="over">
			                <DIV class="module_scroll"  >
								<DIV class="first_scroll" >
			          				
										<TABLE cellSpacing="0" cellPadding="0" width="159" >
										   <TBODY>
										   <xsl:for-each select="document/catalog/catalog-item">
											       <xsl:if test="item != ''">
													   <xsl:if test="code != '-1'">
														   <xsl:if test="code != '-2'">
																   <xsl:if test="code != '-3'">
																         <xsl:if test="subcatalog-item/subitem = ''">
																             <TR>
																             <TD width="159" colspan="2"  height="10" >
																             </TD>
																             </TR>						
																             <xsl:if test="code != selected">
																	              <TR>
																	              	<TD  width="25"    height="12" ></TD>
																	             	 <TD  width="149"   height="12" >
																					  <A ><xsl:attribute name="HREF"><xsl:value-of select="url"/></xsl:attribute>
																					  <font size="2" ><xsl:value-of select="item"/></font>
																					  </A>
																				  </TD>
																              </TR>
																	     	</xsl:if>
																		
																		
																		             <xsl:if test="code = selected">
																		              <TR>
																		                <TD  width="25"   height="12" ></TD>
																		                <TD  width="149"   height="12" >
																						<A ><xsl:attribute name="HREF"><xsl:value-of select="url"/></xsl:attribute>
																				       	 <b><font size="2" ><xsl:value-of select="item"/></font></b>
																				        </A>
																						</TD>
																		              </TR>
																			     	</xsl:if>
															
															
																     </xsl:if>
														 </xsl:if>
												 	 </xsl:if>
											 	 </xsl:if>
										 	 </xsl:if>
										
										
										 	 <xsl:if test="subcatalog-item/subitem != ''">
										       <TR>
										          <TD   colspan="2"  height="10" style="text-align: left; padding-left: 5px">
										              <TABLE cellSpacing="0" cellPadding="0" >
											              <TBODY>
											              <TR>
											                <TD   width="25"  height="11" ></TD>
												                <TD height="11" style="text-align: left; padding-right: 10px">
													        		 <A ><xsl:attribute name="HREF"><xsl:value-of select="subcatalog-item/suburl"/></xsl:attribute>
													           		<xsl:value-of select="subcatalog-item/subitem"/>
													        		</A>
																</TD>
											              </TR>
											  	         </TBODY>
										  	  		 </TABLE>
												 </TD>
										    </TR>
										    </xsl:if>
										    
									        </xsl:for-each>
									</TBODY>
								</TABLE>
							
							</DIV>
							</DIV>
			                </TD>
			               </TR>
			               </TBODY>
			               </TABLE>
			               </DIV>
			               </DIV>
			               </DIV>
			               </DIV>
			            </xsl:if>	
						
			<!-- дополнительный модуль -->
			
			<xsl:if test="document/empty_page_ext1 = 'false'" >
			<xsl:if test="count(document/extpolicy_productlist1/extpolicy_product1) != 0" >
            <DIV class="module" style="margin-right:0px">
            <DIV class="first">
            <DIV class="sec">
            <DIV>
            <H3>More</H3>
            <DIV class="box-indent">
			<DIV class="width">
			
            <TABLE class="poll">
              <TBODY>
              <TR>
                <TD class="over">
                  <UL class="mostread_s3">
                  <xsl:for-each select="document/extpolicy_productlist1/extpolicy_product1">
                    <LI class="mostread_s3">
                    <xsl:if test="image != ''" >
                    
			   	     <xsl:if test="big_image_type != 'swf'">
			   	     <xsl:if test="big_image_type != 'flv'">
                   	 <A class="mostread_s3">
                   	 <xsl:attribute name="HREF">
                   	 <xsl:value-of select="policy_url"/>
                   	 </xsl:attribute>
                   	 <br/>
				       <!-- Обрабатываем перевод строки  -->
				       <img  alt=""  width="147" border="0">
				       <xsl:attribute name="src">
				       <xsl:value-of select="image"/>
				       </xsl:attribute>
				       </img>
		                <br/>
 						<xsl:if test="count(description/r) > 1"> 
							 <xsl:if test="string-length(description/r[1]) > 3">
							 	<IMG style="margin-right: 5px;margin-bottom:2px;">
		                        <xsl:attribute name="src">
		                        <xsl:value-of select="concat('xsl/',$host,'/images/list-item-bg.gif')"/>
		                        </xsl:attribute>
		                        </IMG>
                             </xsl:if>
                        </xsl:if>	
		                 	 <U>
				   		       <xsl:for-each select="description/r">
						       <xsl:value-of select="."/> 
						       </xsl:for-each>
			                 </U>
				        </A>
		              </xsl:if>
		              </xsl:if>
		
			    </xsl:if>
		
		            <xsl:if test="image = ''" >
		                <A class="mostread_s3"  ><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute><br/>
				      <!-- Обрабатываем перевод строки  -->
 						<xsl:if test="count(description/r) > 1"> 
							 <xsl:if test="string-length(description/r[1]) > 3">
							 	<IMG style="margin-right: 5px;margin-bottom:2px;">
		                        <xsl:attribute name="src">
		                        <xsl:value-of select="concat('xsl/',$host,'/images/list-item-bg.gif')"/>
		                        </xsl:attribute>
		                        </IMG>
                             </xsl:if>
                        </xsl:if>	
		              <U>
			   		      <xsl:for-each select="description/r">
					      <xsl:value-of select="."/> <br/>
					      </xsl:for-each>
		              </U>
					</A>
			    </xsl:if>	
				 <!-- Кнопки удаления  , начало -->
				<xsl:if test="/document/admin/post_manager != ''">	
				<table><tr><td width="60px" style="padding-bottom: 5px;PADDING-TOP: 5px">																
					        <form name="product_del"  action="Productlist.jsp" method="POST">
								<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
								<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
								<INPUT class="button" TYPE="submit" name="submit" value="Delete"></INPUT>
					        </form>
					        </td>
					        <td width="5px">
					        </td>
					        <td style="padding-bottom: 5px;PADDING-TOP: 5px">
					        <form name="product_edit"  action="Productlist.jsp" method="POST">
								<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
								<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="ext1" ></INPUT>
								<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
								<INPUT  class="button" TYPE="submit" name="submit" value="Edit" style="margin-left: 0px"></INPUT>
					        </form>
				</td></tr></table>	
				</xsl:if>
			   <!-- Кнопки удаления  , конец  -->														
		        </LI>
		        </xsl:for-each>      
                <LI class="mostread_s3" >
                	
		        	<a title="All news" href="Productlist.jsp?catalog_id=-1">
		        	<IMG style="margin-right: 5px;margin-bottom:2px;">
                       		<xsl:attribute name="src">
                       		<xsl:value-of select="concat('xsl/',$host,'/images/list-item-bg.gif')"/>
                       		</xsl:attribute>
					</IMG>
		        	<u>News Archive</u>
		        	</a>
		        </LI>    
                              
            </UL></TD></TR></TBODY></TABLE></DIV></DIV></DIV></DIV></DIV></DIV></xsl:if>
            </xsl:if>
						
							
							
			
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
							
							     <TABLE height="20" cellSpacing="0" cellPadding="0"  >
					              <TBODY>
							              <TR>
								            <TD align="center">									
											    <!-- Показать кнопку для управления сайтом , начало  -->				
											    <xsl:if test="document/role_id = '2'">											    
														<div style="font-size: 12px">
														This is the button to edit the site<br/>
															<div class="change">													
															<A ><xsl:attribute name="HREF"><xsl:value-of select="document/admin/post_manager"/>
															</xsl:attribute><DIV >Menu</DIV></A>
															</div>
													    </div>										    
											    </xsl:if>								   
										    </TD>
										  </TR>
						              <TR>
						              <TD >					              
						               <DIV class="contentArticle" >
									<FORM  name="order" id="order" action="ProductInfo.jsp" method="POST">
									<table><tr>
									<td width="90%" align="right"  >
									<INPUT AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"  ><xsl:attribute name="value"><xsl:value-of select="document/product/product_id"/></xsl:attribute></INPUT>
									<INPUT AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action"><xsl:attribute name="VALUE"><xsl:choose><xsl:when test="document/subscribed = 'true'">unsubscribe</xsl:when><xsl:otherwise>subscribe</xsl:otherwise></xsl:choose></xsl:attribute></INPUT>
									</td><td width="5%"  ></td><td width="5%"  >
									<a href="#" onclick="document.getElementById('order').submit();"><xsl:attribute name="title"><xsl:choose><xsl:when test="document/subscribed = 'true'">Stop following this product</xsl:when><xsl:otherwise>Follow this product: bids, offers, sale, order status</xsl:otherwise></xsl:choose></xsl:attribute><xsl:choose><xsl:when test="document/subscribed = 'true'"><svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#5f6368"><path d="m644-428-58-58q9-47-27-88t-93-32l-58-58q17-8 34.5-12t37.5-4q75 0 127.5 52.5T660-500q0 20-4 37.5T644-428Zm128 126-58-56q38-29 67.5-63.5T832-500q-50-101-143.5-160.5T480-720q-29 0-57 4t-55 12l-62-62q41-17 84-25.5t90-8.5q151 0 269 83.5T920-500q-23 59-60.5 109.5T772-302Zm20 246L624-222q-35 11-70.5 16.5T480-200q-151 0-269-83.5T40-500q21-53 53-98.5t73-81.5L56-792l56-56 736 736-56 56ZM222-624q-29 26-53 57t-41 67q50 101 143.5 160.5T480-280q20 0 39-2.5t39-5.5l-36-38q-11 3-21 4.5t-21 1.5q-75 0-127.5-52.5T300-500q0-11 1.5-21t4.5-21l-84-82Zm319 93Zm-151 75Z"/></svg></xsl:when><xsl:otherwise><svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#5f6368"><path d="M480-320q75 0 127.5-52.5T660-500q0-75-52.5-127.5T480-680q-75 0-127.5 52.5T300-500q0 75 52.5 127.5T480-320Zm0-72q-45 0-76.5-31.5T372-500q0-45 31.5-76.5T480-608q45 0 76.5 31.5T588-500q0 45-31.5 76.5T480-392Zm0 192q-146 0-266-81.5T40-500q54-137 174-218.5T480-800q146 0 266 81.5T920-500q-54 137-174 218.5T480-200Zm0-300Zm0 220q113 0 207.5-59.5T832-500q-50-101-144.5-160.5T480-720q-113 0-207.5 59.5T128-500q50 101 144.5 160.5T480-280Z"/></svg></xsl:otherwise></xsl:choose></a>
									</td></tr></table>
									</FORM>
		                         		<table><tr>
			                        		<td><h2 style="color: #4C4B49; font-size: 20px;padding-bottom:10px;"><xsl:value-of select="document/product/name"/></h2></td>
			                        		<td>
				                        		<div class="cena2">
												<xsl:if test="document/product/amount != 0.0">
													<label>Price:</label><span>$<xsl:value-of select="document/product/amount"/></span>
												</xsl:if>
												</div>
			                        		</td>
			                        	</tr></table>
			                        	<xsl:if test="document/product/amount != '0'"> 
										<xsl:if test="document/product/amount != '0.0'"> 
										 <br/>
								       <FORM  name="order" action="Order.jsp" method="POST">
			                        	<table><tr>
			                        		<td width="90%" align="right"  >
			            						    <INPUT AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="position"  ><xsl:attribute name="value"><xsl:value-of select="document/product/product_id"/></xsl:attribute></INPUT>
												    <INPUT AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="add" ></INPUT>
												    <div class="cena2" style="padding:2px;margin:2px;" >Quantity</div>
						            		</td>
			                        		<td width="5%"  >
			       								    <INPUT AUTOCOMPLETE="off" type="number"  size="6" min="1" max="90000"  NAME="quantity" VALUE="1" ></INPUT>
			                        		</td>
			                        		<td width="5%"  >
 											            <INPUT class="button" AUTOCOMPLETE="off" TYPE="Submit" NAME="Submit" alt="Add to cart"  VALUE="Add to cart" ></INPUT>   
						            		</td>
			                        	</tr></table>
			                        	 </FORM> 
			                        	 <br/>
			                        	</xsl:if>
										</xsl:if>					
			                        	  <xsl:if test="document/show_offer = 'true'"> 
			                        	  <br/>
									       <FORM  name="order" action="PostProductOffer.jsp" method="POST">
				                        	<table><tr>
				                        		<td width="90%" align="right"  >
				            						    <INPUT AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"  ><xsl:attribute name="value"><xsl:value-of select="document/product/product_id"/></xsl:attribute></INPUT>
													    <INPUT AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="do_offer" ></INPUT>
													    <div class="cena2" style="padding:2px;margin:2px;" >Put your offer </div>
							            		</td>
				                        		<td width="5%"  >
				       								    <INPUT AUTOCOMPLETE="off" type="price"  size="6" min="1" max="90000"  NAME="price"  ><xsl:attribute name="value"><xsl:value-of select="document/product/offerAmount"/></xsl:attribute></INPUT>
				                        		</td>
				                        		<td width="5%"  >
	 											            <INPUT class="button" AUTOCOMPLETE="off" TYPE="Submit" NAME="Submit" alt="Submit offer"  VALUE="Submit offer" ></INPUT>   
							            		</td>
				                        	</tr></table>
				                        	 </FORM> 
			                        	  </xsl:if>
			                        	  <xsl:if test="document/show_action = 'true'">
			                        	   <br/>
			                        	  <div class="cena2">
												<xsl:if test="document/product/actionMaxBitAmount != 0.0">
													<label>Max bid:</label><span>$<xsl:value-of select="document/product/actionMaxBitAmount"/></span>
												</xsl:if>
												</div>
				                           <br/>
									       <FORM  name="order" action="PostProductBidAction.jsp" method="POST">
				                        	<table><tr>
				                        		<td width="90%" align="right"  >
				            						    <INPUT AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"  ><xsl:attribute name="value"><xsl:value-of select="document/product/product_id"/></xsl:attribute></INPUT>
													    <INPUT AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="do_bid" ></INPUT>
													    <div class="cena2" style="padding:2px;margin:2px;" >Put your Action bid </div>
							            		</td>
				                        		<td width="5%"  >
				       								    <INPUT AUTOCOMPLETE="off" type="price"  size="6" min="1" max="90000"  NAME="price"  ><xsl:attribute name="value"><xsl:value-of select="document/product/actionBitAmount"/></xsl:attribute></INPUT>
				                        		</td>
				                        		<td width="5%"  >
	 											            <INPUT class="button" AUTOCOMPLETE="off" TYPE="Submit" NAME="Submit" alt="Submit Bid"  VALUE="Submit Bid" ></INPUT>   
							            		</td>
				                        	</tr></table>
				                        	 </FORM> 
			                        	  </xsl:if>
									  <FORM  name="order" action="Order.jsp" method="POST">
									  <INPUT AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="position"  ><xsl:attribute name="value"><xsl:value-of select="document/product/product_id"/></xsl:attribute></INPUT>
									  <INPUT AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="add" ></INPUT>
										
										 	<xsl:if test="document/product/image != ''">
										        
												<div style="width: 100%; text-align: center; margin-bottom: 10px; margin-top: 10px">
										   	    <xsl:if test="document/product/image_type != 'swf'">
										   	      <xsl:if test="document/product/image_type != 'flv'">
												<IMG alt=""  width="400" border="0"><xsl:attribute name="src"><xsl:value-of select="document/product/image"/></xsl:attribute></IMG> 
										              </xsl:if>
										            </xsl:if>
										
										   	    <xsl:if test="document/product/image_type = 'flv'">
												  <embed src="http://www.jeroenwijering.com/embed/player.swf" 
													width="400" 
													height="400"  
													searchbar="false"
													allowscriptaccess="always" 
													allowfullscreen="true" >
													<xsl:attribute name="flashvars">searchbar=false<![CDATA[&]]>file=http://<xsl:value-of select="document/domain"/>/<xsl:value-of select="document/product/image"/><![CDATA[&]]>bufferlength=200</xsl:attribute>
												   </embed>
										            </xsl:if>
										
										   	    <xsl:if test="document/product/image_type = 'swf'">
												<OBJECT id="Tech Presentation1" >
												<embed 
												quality="high" 
												bgcolor="#ffffff" 
												width="400" 
												
												name="Tech Presentation1" 
												align="middle" 
												allowScriptAccess="sameDomain" 
												type="application/x-shockwave-flash" 
												pluginspage="http://www.macromedia.com/go/getflashplayer" ><xsl:attribute name="src"><xsl:value-of select="document/product/image"/></xsl:attribute></embed>
												</OBJECT>
										             </xsl:if>
										
										   	   </div> 
										  	</xsl:if>
										
										
										       <p class="policy_main_product">

										   	    
										            
										  	    <!-- Обрабатываем перевод строки  --> 
												  <xsl:for-each select="document/product/description/r">
													<xsl:value-of select="."/><br style="line-height: 0px"/>
											  	  </xsl:for-each>
										
										            
											    
											    </p>			
											    
											    <xsl:if test="document/show_rating1 = 'true'">	
												<div class="star">Vote:
  <A href="Policy.jsp?rate=1"  alt="Rating 1"  ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red1.gif')"/></xsl:attribute></IMG></A>
  <A href="Policy.jsp?rate=2"  alt="Rating 2" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red2.gif')"/></xsl:attribute></IMG></A>
  <A href="Policy.jsp?rate=3"  alt="Rating 3" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red3.gif')"/></xsl:attribute></IMG></A>
  <A href="Policy.jsp?rate=4"  alt="Rating 4" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red4.gif')"/></xsl:attribute></IMG></A>
  <A href="Policy.jsp?rate=5"  alt="Rating 5" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red5.gif')"/></xsl:attribute></IMG></A>
  <A href="Policy.jsp?rate=6"  alt="Rating 6" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red6.gif')"/></xsl:attribute></IMG></A>
  <A href="Policy.jsp?rate=7"  alt="Rating 7" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red7.gif')"/></xsl:attribute></IMG></A>
  <A href="Policy.jsp?rate=8"  alt="Rating 8" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red8.gif')"/></xsl:attribute></IMG></A>
  <A href="Policy.jsp?rate=9"  alt="Rating 9" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red9.gif')"/></xsl:attribute></IMG></A>
  <A href="Policy.jsp?rate=10"  alt="Rating 10" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red10.gif')"/></xsl:attribute></IMG></A>
       </div><div class="star">Average rating :


 <xsl:choose>
          <xsl:when test="document/rating1/show_star_1 != 'no'">
  <A href="Policy.jsp?rate=1"  alt="Rating 1"  ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red_fill.gif')"/></xsl:attribute></IMG></A>
          </xsl:when>
          <xsl:otherwise>
  <A href="Policy.jsp?rate=1"  alt="Rating 1"  ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red.gif')"/></xsl:attribute></IMG></A>
          </xsl:otherwise>
        </xsl:choose>

 <xsl:choose>
          <xsl:when test="document/rating1/show_star_2 != 'no'">
  <A href="Policy.jsp?rate=2"  alt="Rating 2" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red_fill.gif')"/></xsl:attribute></IMG></A>
          </xsl:when>
          <xsl:otherwise>
  <A href="Policy.jsp?rate=2"  alt="Rating 2" > <IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red.gif')"/></xsl:attribute></IMG></A>
          </xsl:otherwise>
        </xsl:choose>


 <xsl:choose>
          <xsl:when test="document/rating1/show_star_3 != 'no'">
  <A href="Policy.jsp?rate=3"  alt="Rating 3" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red_fill.gif')"/></xsl:attribute></IMG></A>
          </xsl:when>
          <xsl:otherwise>
  <A href="Policy.jsp?rate=3"  alt="Rating 3" > <IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red.gif')"/></xsl:attribute></IMG></A>
          </xsl:otherwise>
        </xsl:choose>


 <xsl:choose>
          <xsl:when test="document/rating1/show_star_4 != 'no'">
  <A href="Policy.jsp?rate=4"  alt="Rating 4" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red_fill.gif')"/></xsl:attribute></IMG></A>
          </xsl:when>
          <xsl:otherwise>
  <A href="Policy.jsp?rate=4"  alt="Rating 4" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red.gif')"/></xsl:attribute></IMG></A>
          </xsl:otherwise>
        </xsl:choose>



 <xsl:choose>
          <xsl:when test="document/rating1/show_star_5 != 'no'">
  <A href="Policy.jsp?rate=5"  alt="Rating 5" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red_fill.gif')"/></xsl:attribute></IMG></A>
          </xsl:when>
          <xsl:otherwise>
  <A href="Policy.jsp?rate=5"  alt="Rating 5" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red.gif')"/></xsl:attribute></IMG></A>
          </xsl:otherwise>
        </xsl:choose>


 <xsl:choose>
          <xsl:when test="document/rating1/show_star_6 != 'no'">
  <A href="Policy.jsp?rate=6"  alt="Rating 6" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red_fill.gif')"/></xsl:attribute></IMG></A>
          </xsl:when>
          <xsl:otherwise>
  <A href="Policy.jsp?rate=6"  alt="Rating 6" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red.gif')"/></xsl:attribute></IMG></A>
          </xsl:otherwise>
        </xsl:choose>



 <xsl:choose>
          <xsl:when test="document/rating1/show_star_7 != 'no'">
  <A href="Policy.jsp?rate=7"  alt="Rating 7" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red_fill.gif')"/></xsl:attribute></IMG></A>
          </xsl:when>
          <xsl:otherwise>
  <A href="Policy.jsp?rate=7"  alt="Rating 7" > <IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red.gif')"/></xsl:attribute></IMG></A>
          </xsl:otherwise>
        </xsl:choose>



 <xsl:choose>
          <xsl:when test="document/rating1/show_star_8 != 'no'">
  <A href="Policy.jsp?rate=8"  alt="Rating 8" ><IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red_fill.gif')"/></xsl:attribute></IMG></A>
          </xsl:when>
          <xsl:otherwise>
  <A href="Policy.jsp?rate=8"  alt="Rating 8" > <IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red.gif')"/></xsl:attribute></IMG></A>
          </xsl:otherwise>
        </xsl:choose>



 <xsl:choose>
          <xsl:when test="document/rating1/show_star_9 != 'no'">
  <A href="Policy.jsp?rate=9"  alt="Rating 9" > <IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red_fill.gif')"/></xsl:attribute></IMG></A>
          </xsl:when>
          <xsl:otherwise>
  <A href="Policy.jsp?rate=9"  alt="Rating 9" > <IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red.gif')"/></xsl:attribute></IMG></A>
          </xsl:otherwise>
        </xsl:choose>



 <xsl:choose>
          <xsl:when test="document/rating1/show_star_10 != 'no'">
  <A href="Policy.jsp?rate=10"  alt="Rating 10" > <IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red_fill.gif')"/></xsl:attribute></IMG></A>
          </xsl:when>
          <xsl:otherwise>
  <A href="Policy.jsp?rate=10"  alt="Rating 10" > <IMG><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/star_red.gif')"/></xsl:attribute></IMG></A>
          </xsl:otherwise>
        </xsl:choose>



        </div></xsl:if>							
												
												<DIV style="padding-right:0px; text-align: right; color: #4C4B49; font-size: 11px">
											    Viewings: <xsl:value-of select="document/product/statistic"/>  <br/>
												Published: <xsl:value-of select="document/product/cdate"/>
												</DIV>
												
												
												<DIV style="text-align: left; color: #3b7ecc; font-size: 11px">
												<table width="100%">
												<tr>
													<td style="font-size: 12px; vertical-align: middle; width: 420px">															
															<div class="readmore">
																<a HREF = "#" onClick="javascript:history.back()" class="readon">
																	<strong>Back</strong>				
																</a>
															</div>														
													</td>
													<td style="padding-right:0px; text-align: right;font-size: 12px; vertical-align: middle">
														<div class="readmore">
																<a class="readon"><xsl:attribute name="HREF"><xsl:value-of select="document/shoping_url"/></xsl:attribute>
																	<strong>Back to search</strong>				
																</a>
														</div>
													</td>
												</tr>
										  		</table>
										  		</DIV>
													   	    						
						  			 </FORM>						
											 	                        
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

		<tr > 
		 <td >
 		  <!-- forum -->
 		  <xsl:if test="document/show_blog = 'true'">
		  <DIV class="module_forum" style="margin-right:0px;">
		  <DIV class="first_forum">
		  <DIV class="sec_forum">
		  <H3>Forum<xsl:value-of select="document/product/name"/></H3>
		  <DIV>
		  <DIV class="forum" style="width:512px" >
		  <DIV>
		  <DIV>
		  <div class="add_message">
							<a href="ProductReviewPost.jsp?parent_id={document/product/product_id}" >
							<DIV >
								Add message		
							</DIV>		
							</a>
		 </div><br/>
		 	<TABLE>
				<TBODY>
					<TR>
						<TD class="over" id="forum">
						<UL>
						
						<xsl:for-each select="document/product_blog_list/product_blog">
						<LI  width="100%" style="padding-bottom:20px">
						
						<table width="100%">
							<tr>
								<td colspan="2" style="padding-bottom:5px">
								<A class="menu"><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute><b><xsl:value-of select="parent_title"/></b></A> 												        	
								</td>			        	
							</tr>
							<tr>
								<td>			        		
								<IMG border="0" height="20" width="20" style="margin-right: 5px">
								<xsl:attribute name="src">
								<xsl:value-of select="concat('xsl/',$host,'/images/user1.png')"/>
								</xsl:attribute>
								</IMG> 
								<em><xsl:value-of select="author"/></em>											        	
								</td>
								
								<td style="text-align: right; color: #4C4B49; padding-right: 20px; vertical-align: bottom; font-size: 10px;">
								Added: <xsl:value-of select="cdate"/>
								</td>
							</tr>
							<tr>
								<td colspan="2" style="FONT: 11px Arial; COLOR: #474646">			        	
								<b><xsl:value-of select="name"/></b>
								<br/>						    
								<xsl:for-each select="description/r">
								<xsl:value-of select="."/> 
								</xsl:for-each>					    
								</td>													        
							</tr>													        
						</table>
															     												
															
													
										   <!-- Кнопки удаления , начало для админа  -->
										<xsl:choose>
										<xsl:when test="/document/role_id = 2">
											<table style="width:200px; padding-left: 20px">
											    <tbody>
											        <tr>
												        <td style="padding-left: 30px; padding-right: 16px;padding-top: 10px;">														         
													                <form name="product_del"  action="Productlist.jsp" method="POST">
															<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
															<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
															<INPUT class="button" TYPE="submit" name="submit" value="Delete"></INPUT>
													                </form>
												         </td>
											             <td style="padding-top: 10px;">
														    <form name="product_edit"  action="Productlist.jsp" method="POST">
																<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
																<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="blog" ></INPUT>
																<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
																<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_parent_id"><xsl:attribute name="value"><xsl:value-of select="/document/product/product_id"/></xsl:attribute></INPUT>
																<INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
														    </form>
												         </td>
											        </tr>
											    </tbody>
											</table>
										</xsl:when>
									   <!-- Кнопки удаления  , конец  для админа  -->
							
									   <!-- Кнопки удаления , начало для автора  -->
							
							         <xsl:otherwise>
										<xsl:if  test="user_id = string(number($user_id))">
										<xsl:if test="/document/role_id = '1'">
										<table style="width:200px; padding-left: 20px">
									   		<tbody>
									        	<tr>
									        	<td style="padding-left: 30px; padding-right: 16px;padding-top: 10px;">
									            <form name="product_del"  action="Productlist.jsp" method="POST">
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
													<INPUT class="button" TYPE="submit" name="submit" value="Delete"></INPUT>
											    </form>
									        	</td>
										         <td style="padding-top: 10px;">
										         <form name="product_edit"  action="Productlist.jsp" method="POST">
														<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
														<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="blog" ></INPUT>
														<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
														<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_parent_id"><xsl:attribute name="value"><xsl:value-of select="/document/product/product_id"/></xsl:attribute></INPUT>
														<INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
												</form>
										        </td>
									        </tr>
									        </tbody>
									     </table>
									     </xsl:if>
									     </xsl:if>
									     </xsl:otherwise>
									     </xsl:choose>
									     <SPAN class="article_separator"></SPAN> 
							</LI>
							</xsl:for-each>
							</UL>
							</TD>
							</TR>
							</TBODY>
							</TABLE>
										            </DIV>
									            </DIV>
								            </DIV>   
	     	          					</DIV>
			                         </DIV>
		                         </DIV>
		                    </DIV>
						</xsl:if>		                                            
		
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
		<p align="center">
<table width="100%"   > 
	<tbody>
	 <tr><td width="20%"></td>
		<xsl:for-each select="document/bottomlist/bottom">
					               <td>
							                <A ><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute><br/>
						                      <U>
									   		      <xsl:value-of select="name"/>
						                      </U>
											</A>
											

								        	<!-- Кнопки удаления  , начало -->
											<xsl:if test="/document/admin/post_manager != ''">
											<br/>
											<table>
											    <tbody>
											        <tr>
											         <td>
											               <form name="product_edit"  action="Productlist.jsp">
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="bottom"  ></INPUT>
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
													<INPUT TYPE="submit" name="submit" value="Change"></INPUT>
											                </form>  
											         </td>
											         <td>
															<form name="product_del"  action="Productlist.jsp">
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
													<INPUT TYPE="submit" name="submit" value="Delete"></INPUT>
											                </form>
											               
											         </td>
											        </tr>
											    </tbody>
											</table>
											</xsl:if>
							           		<!-- Кнопки удаления  , конец  -->
										</td>
					  	    	</xsl:for-each>
	 <td width="20%"></td></tr>
    </tbody>
</table>
</p>
<br/>
		Internet shop . Copyright 2010 
		<A HREF="http://www.siteoneclick.com"> Center Business Solutions Ltd </A>.  All rights reserved 
		</div>
		</DIV>
	</DIV>

</body>



</HTML>
</xsl:template>
     


</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios/><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->