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
	<xsl:variable name="virtual_host" select="document/virtual_host"/> 
<HTML>
<HEAD>
<META HTTP-EQUIV="no-cache"/>
 <title><xsl:value-of select="document/title"/></title>
 
     <LINK rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/template.css')"/></xsl:attribute></LINK> 
     <LINK rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/constant.css')"/></xsl:attribute></LINK> 
	 <SCRIPT type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/caption.js')"/></xsl:attribute></SCRIPT>
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


			    <DIV class="indent" style="width: 960px">
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
			
<div id="container" style="margin: 0px 21px;  margin-top: ;  margin-right: ; margin-bottom: ;  margin-left:"  >
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
											    <!-- �������� ������ ��� ���������� ������ , ������  -->				
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
				       								    <INPUT AUTOCOMPLETE="off" type="number"  size="6" min="1" max="90000"  NAME="price"  ><xsl:attribute name="value"><xsl:value-of select="document/product/offerAmount"/></xsl:attribute></INPUT>
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
				       								    <INPUT AUTOCOMPLETE="off" type="number"  size="6" min="1" max="90000"  NAME="price"  ><xsl:attribute name="value"><xsl:value-of select="document/product/actionBitAmount"/></xsl:attribute></INPUT>
				                        		</td>
				                        		<td width="5%"  >
	 											            <INPUT class="button" AUTOCOMPLETE="off" TYPE="Submit" NAME="Submit" alt="Submit Bid"  VALUE="Submit Bid" ></INPUT>
							            		</td>
				                        	</tr></table>
				                        	 </FORM> 
			                        	  </xsl:if>
									       <FORM  name="follow" id="follow" action="ProductInfo.jsp" method="POST">
				            					    <INPUT AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"  ><xsl:attribute name="value"><xsl:value-of select="document/product/product_id"/></xsl:attribute></INPUT>
											    <INPUT AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action"><xsl:attribute name="VALUE"><xsl:choose><xsl:when test="document/subscribed = 'true'">unsubscribe</xsl:when><xsl:otherwise>subscribe</xsl:otherwise></xsl:choose></xsl:attribute></INPUT>
										    <a href="#" onclick="document.getElementById('follow').submit();"><xsl:attribute name="title"><xsl:choose><xsl:when test="document/subscribed = 'true'">Stop following this product</xsl:when><xsl:otherwise>Follow this product</xsl:otherwise></xsl:choose></xsl:attribute><xsl:choose><xsl:when test="document/subscribed = 'true'">Unfollow</xsl:when><xsl:otherwise>Follow</xsl:otherwise></xsl:choose></a>
				                        	 </FORM>
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

										   	    
										            
										  	    <!-- ������������ ������� ������  --> 
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
															     												
															
													
										   <!-- ������ �������� , ������ ��� ������  -->
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
									   <!-- ������ ��������  , �����  ��� ������  -->
							
									   <!-- ������ �������� , ������ ��� ������  -->
							
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
											

								        	<!-- ������ ��������  , ������ -->
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
							           		<!-- ������ ��������  , �����  -->
										</td>
					  	    	</xsl:for-each>
	 <td width="20%"></td></tr>
    </tbody>
</table>
</p>
<br/>
<font color="black" >Internet shop . Copyright 2024 
		<A HREF="http://www.cmsmanhattan.com"><font color="black">  FDIS Center Business Solutions Inc </font></A>.  All rights reserved
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