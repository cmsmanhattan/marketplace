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
	<xsl:variable name="site_id" select="document/site_id"/> 
	<xsl:variable name="virtual_host" select="document/virtual_host"/> 
	
<HTML>
<HEAD>
<META HTTP-EQUIV="no-cache"/>
 <title><xsl:value-of select="document/title"/></title>
 
     <LINK id="style2" rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/template.css')"/></xsl:attribute></LINK> 
	 <LINK rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/constant.css')"/></xsl:attribute></LINK>
	 <SCRIPT type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/caption.js')"/></xsl:attribute></SCRIPT>
	 <link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/jquery.lightbox.css')"/></xsl:attribute></link>
	 
	 <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/jquery.js')"/></xsl:attribute></script>
	 <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/jquery.lightbox.js')"/></xsl:attribute></script>

	 <link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/menu.css')"/></xsl:attribute></link>
	 <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/menu.js')"/></xsl:attribute></script>

     <link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/slider.css')"/></xsl:attribute></link>
	 <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/slider.js')"/></xsl:attribute></script>

    <link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/showiframe.css')"/></xsl:attribute></link>
	<script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/showiframe.js')"/></xsl:attribute></script>
	
	<link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/webmail.css')"/></xsl:attribute></link>
	<script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/webmail.js')"/></xsl:attribute></script>

	<script type="text/javascript">
 
 	var roleId = <xsl:value-of select="document/role_id" disable-output-escaping="yes" />;
 
	$(function() {
		$('a[@rel*=lightbox]').lightBox({fixedNavigation:true , overlayOpacity: 0.6 , onClose : function() { close(); } }); // Select all links that contains lightbox in the attribute rel
	}); 

	

	$(document).ready(function () {
	if( roleId != 0 ) return ;
	//var result = getCookie('lightBoxDisable') ;
	//if (result === undefined) document.getElementById('lightboxdelay').click();   
	//alert('Hi');
	}); 


	function getCookie(name) {
  	var matches = document.cookie.match(new RegExp(
    "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
  	));
  	return matches ? decodeURIComponent(matches[1]) : undefined;
	}

	function close() {
	//alert('Hi');
	var date = new Date( new Date().getTime() + 360*1000 );
	document.cookie = "lightBoxDisable=ture; path=/; expires="+date.toUTCString();
 	}

	
	function resetSearchCreForm() {
            top.searchcreform.creteria1_id.value = '0' ;
            top.searchcreform.creteria2_id.value = '0' ;
            top.searchcreform.creteria3_id.value = '0' ;
            top.searchcreform.creteria4_id.value = '0' ;
            top.searchcreform.creteria5_id.value = '0' ;
            top.searchcreform.creteria6_id.value = '0' ;
            top.searchcreform.creteria7_id.value = '0' ;
            top.searchcreform.creteria8_id.value = '0' ;
            top.searchcreform.creteria9_id.value = '0' ;
            top.searchcreform.creteria10_id.value = '0' ;
        }

	</script>
	 
	 
</HEAD>

<body id="body">

<xsl:attribute name="onload">
<xsl:value-of select="concat('setCurrent(',$page,',',$role, ');')"/>
</xsl:attribute>
	<div class="main" style="background-color: #E7E7DF">
				<IMG alt="Logo"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/logo.gif')"/></xsl:attribute></IMG>
			</div>
	<div id="gradient">
		
		<div class="main" style="width: 960px" >
			
			<div id="top">

					<div id="topmenu">
						<div class="module-topmenu">
						
						<!--  <ul class="menu-nav">  -->
						<ul id="sddm" >
						<li class="item53">
						<a href="Productlist.jsp?catalog_id=-2">
						<span>Home</span>
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



			    <DIV class="indent" style="width: 960px" >
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
										<font class="user0">
											<xsl:value-of select="document/login"/>
									</font>
								</xsl:if>
											 			
								<xsl:if test="document/role_id = 2"><!--  ������� ���� ����� -->
									<font class="user0">
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
					          
				                       <a href="Productlist.jsp?catalog_id=-2" class="catalog" alt="To return back  to the top of Categorization" title="To return back  to the top of Categorization">
                                        <U><font size="2" >All Categories</font></U>
                                      </a>&#160; &#187; 
				
						      <xsl:if test="count(document/parent/parent-item) &gt; 0">
                                    
               				
								        <xsl:for-each select="document/parent/parent-item">										
											 <xsl:if test="code != '-2'">												
												<A ><xsl:attribute name="HREF"><xsl:value-of select="url"/></xsl:attribute>
											        <U><font size="2" > <xsl:value-of select="item"/></font> </U> 
											    </A>&#160; &#187; 
											 </xsl:if>													
								        </xsl:for-each>
								        
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

							
							     <TABLE height="20" cellSpacing="0" cellPadding="0"  >
					              <TBODY>
							              <TR>
								            <TD align="center">									
											    <!-- �������� ������ ��� ���������� ������ , ������  -->				
											    <xsl:if test="document/role_id = '2'">											    
														<div style="font-size: 12px">
														Button to edit the site<br/>
															<div class="change">													
															<A ><xsl:attribute name="HREF"><xsl:value-of select="document/admin/post_manager"/>
															</xsl:attribute><DIV >Menu</DIV></A>
															</div>
													    </div>										    
											    </xsl:if>								   
										    </TD>
										  </TR>
						              <TR>
						              <TD vAlign="center"  align="center" class="pagesLink">					              
						              <xsl:if test="((($page div 10) +1) &lt; 21)">
						              <table class="pagesTable" style="width: 80%">
						              <tr>
									        <td><a id="next" href="javascript:showPrev()">Prev</a></td>					       
									        <td id="d1"><a id="link1" href="javascript:showStr('link1')">1</a></td>								       
									        <td id="d2"><a id="link2" href="javascript:showStr('link2')">2</a></td>								       
									        <td id="d3"><a id="link3" href="javascript:showStr('link3')">3</a></td>
									        <td id="d4"><a id="link4" href="javascript:showStr('link4')">4</a></td>								       
									        <td id="d5"><a id="link5" href="javascript:showStr('link5')">5</a></td>								       
									        <td id="d6"><a id="link6" href="javascript:showStr('link6')">6</a></td>
									        <td id="d7"><a id="link7" href="javascript:showStr('link7')">7</a></td>								       
									        <td id="d8"><a id="link8" href="javascript:showStr('link8')">8</a></td>								       
									        <td id="d9"><a id="link9" href="javascript:showStr('link9')">9</a></td>
									        <td id="d10"><a id="link10" href="javascript:showStr('link10')">10</a></td>								       
									        <td id="d11"><a id="link11" href="javascript:showStr('link11')">11</a></td>								       
									        <td id="d12"><a id="link12" href="javascript:showStr('link12')">12</a></td>
									        <td id="d13"><a id="link13" href="javascript:showStr('link13')">13</a></td>								       
									        <td id="d14"><a id="link14" href="javascript:showStr('link14')">14</a></td>								       
									        <td id="d15"><a id="link15" href="javascript:showStr('link15')">15</a></td>
									        <td id="d16"><a id="link16" href="javascript:showStr('link16')">16</a></td>								       
									        <td id="d17"><a id="link17" href="javascript:showStr('link17')">17</a></td>								       
									        <td id="d18"><a id="link18" href="javascript:showStr('link18')">18</a></td>
									        <td id="d19"><a id="link19" href="javascript:showStr('link19')">19</a></td>								       
									        <td id="d20"><a id="link20" href="javascript:showStr('link20')">20</a></td>								       				        
									        <td><a id="next" href="javascript:showNext()">Next</a></td>
									  </tr>
									  </table>      
									  </xsl:if>
									  
									  <xsl:if test="((($page div 10) +1) &gt; 99980)">
						              <table class="pagesTable" style="width: 80%">
						              <tr>
									        <td><a id="next" href="javascript:showPrev()">Prev</a></td>								        
									        <td id="d1"><a id="link1" href="javascript:showStr('link1')">99981</a></td>								       
									        <td id="d2"><a id="link2" href="javascript:showStr('link2')">99982</a></td>								        
									        <td id="d3"><a id="link3" href="javascript:showStr('link3')">99983</a></td>	
									        <td id="d4"><a id="link4" href="javascript:showStr('link4')">99984</a></td>								       
									        <td id="d5"><a id="link5" href="javascript:showStr('link5')">99985</a></td>								       
									        <td id="d6"><a id="link6" href="javascript:showStr('link6')">99986</a></td>
									        <td id="d7"><a id="link7" href="javascript:showStr('link7')">99987</a></td>								       
									        <td id="d8"><a id="link8" href="javascript:showStr('link8')">99988</a></td>								       
									        <td id="d9"><a id="link9" href="javascript:showStr('link9')">99989</a></td>
									        <td id="d10"><a id="link10" href="javascript:showStr('link10')">99990</a></td>								       
									        <td id="d11"><a id="link11" href="javascript:showStr('link11')">99991</a></td>								       
									        <td id="d12"><a id="link12" href="javascript:showStr('link12')">99992</a></td>
									        <td id="d13"><a id="link13" href="javascript:showStr('link13')">99993</a></td>								       
									        <td id="d14"><a id="link14" href="javascript:showStr('link14')">99994</a></td>								       
									        <td id="d15"><a id="link15" href="javascript:showStr('link15')">99995</a></td>
									        <td id="d16"><a id="link16" href="javascript:showStr('link16')">99996</a></td>								       
									        <td id="d17"><a id="link17" href="javascript:showStr('link17')">99997</a></td>								       
									        <td id="d18"><a id="link18" href="javascript:showStr('link18')">99998</a></td>
									        <td id="d19"><a id="link19" href="javascript:showStr('link19')">99999</a></td>								       
									        <td id="d20"><a id="link20" href="javascript:showStr('link20')">100000</a></td>							        					        
									        <td><a id="next" href="javascript:showNext()">Next</a></td>
									  </tr>
									  </table>
									  </xsl:if>
									  
									  <xsl:if test="((($page div 10) +1) &lt; 99981)">
									  <xsl:if test="((($page div 10) +1) &gt; 20)">
						              <table class="pagesTable" style="width: 80%">
						              <tr>
									        <td><a id="next" href="javascript:showPrev()">Prev</a></td>								       
									        <td id="d1"><a id="link1" href="javascript:showStr('link1')"><xsl:value-of select="($page div 10)"/></a></td>								      
									        <td id="d2"><a id="link2" href="javascript:showStr('link2')"><xsl:value-of select="(($page div 10) +1)"/></a></td>								        
									        <td id="d3"><a id="link3" href="javascript:showStr('link3')"><xsl:value-of select="(($page div 10) +2)"/></a></td>
									        <td id="d4"><a id="link4" href="javascript:showStr('link4')"><xsl:value-of select="(($page div 10) +3)"/></a></td>								       
									        <td id="d5"><a id="link5" href="javascript:showStr('link5')"><xsl:value-of select="(($page div 10) +4)"/></a></td>								       
									        <td id="d6"><a id="link6" href="javascript:showStr('link6')"><xsl:value-of select="(($page div 10) +5)"/></a></td>
									        <td id="d7"><a id="link7" href="javascript:showStr('link7')"><xsl:value-of select="(($page div 10) +6)"/></a></td>								       
									        <td id="d8"><a id="link8" href="javascript:showStr('link8')"><xsl:value-of select="(($page div 10) +7)"/></a></td>								       
									        <td id="d9"><a id="link9" href="javascript:showStr('link9')"><xsl:value-of select="(($page div 10) +8)"/></a></td>
									        <td id="d10"><a id="link10" href="javascript:showStr('link10')"><xsl:value-of select="(($page div 10) +9)"/></a></td>								       
									        <td id="d11"><a id="link11" href="javascript:showStr('link11')"><xsl:value-of select="(($page div 10) +10)"/></a></td>								       
									        <td id="d12"><a id="link12" href="javascript:showStr('link12')"><xsl:value-of select="(($page div 10) +11)"/></a></td>
									        <td id="d13"><a id="link13" href="javascript:showStr('link13')"><xsl:value-of select="(($page div 10) +12)"/></a></td>								       
									        <td id="d14"><a id="link14" href="javascript:showStr('link14')"><xsl:value-of select="(($page div 10) +13)"/></a></td>								       
									        <td id="d15"><a id="link15" href="javascript:showStr('link15')"><xsl:value-of select="(($page div 10) +14)"/></a></td>
									        <td id="d16"><a id="link16" href="javascript:showStr('link16')"><xsl:value-of select="(($page div 10) +15)"/></a></td>								       
									        <td id="d17"><a id="link17" href="javascript:showStr('link17')"><xsl:value-of select="(($page div 10) +16)"/></a></td>								       
									        <td id="d18"><a id="link18" href="javascript:showStr('link18')"><xsl:value-of select="(($page div 10) +17)"/></a></td>
									        <td id="d19"><a id="link19" href="javascript:showStr('link19')"><xsl:value-of select="(($page div 10) +18)"/></a></td>								       
									        <td id="d20"><a id="link20" href="javascript:showStr('link20')"><xsl:value-of select="(($page div 10) +19)"/></a></td>									        					        
									        <td><a id="next" href="javascript:showNext()">Next</a></td>
									  </tr>
									  </table>      
									  </xsl:if>								        
									  </xsl:if>
								
										<form id="formToPage" name="searchform2"  action="Productlist.jsp" method="post"><input name="offset" type="hidden" value="0" ></input></form>
								
	
	
						               </TD>
						              </TR>					              
					  	     </TBODY>
					  	   </TABLE>
							
							
		
			            <DIV  id="mainContent" class="mainContent" style="width: 494px">
			            
			            <TABLE class="blog" cellSpacing="0" cellPadding="0"  style="width: 494px">
			              <TBODY>
			              <TR>
			                <TD vAlign="top">                
					                  <TABLE cellSpacing="0" cellPadding="0" width="100%">
						                    <TBODY>
								                    <xsl:if test="document/empty_page = 'true'" >
														<TR>
												          <TD vAlign="top"> 
															On this page there is nothing
															 <br/>
															New Arrival
    														 <div class="line2"></div><br/>
															
															<div class="scroll-container">
															  <button class="scroll-left" onclick="leftScrollNewArrival()">&#9664;</button>
															   <div class="scroll-content-new-arrival">
																	  <xsl:if test="count(document/newslist/news) != 0">
													                  <xsl:for-each select="document/newslist/news">
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
																	  <xsl:if test="count(document/coproductlist1/coproduct1) != 0">
													                  <xsl:for-each select="document/coproductlist1/coproduct1">
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
																	  <xsl:if test="count(document/coproductlist2/coproduct2) != 0">
													                  <xsl:for-each select="document/coproductlist2/coproduct2">
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
															
														  </TD>
														</TR>
										   	        </xsl:if>
								   	        
											   	        <xsl:if test="document/empty_page != 'true'" >
									                    <TR>
									                      <TD class="article_column" vAlign="top" width="100%"> 
																									                        
											                           <!-- 
											                           <DIV class="products_line3">
											                            <DIV class="products_line2">
											                             <DIV class="products">
											                             -->
													                        <xsl:if test="document/empty_page != 'true'" >
																					<table width="494px">
																						<xsl:call-template name="prList">
													               							<xsl:with-param name="prCount" select="count(document/product_list/product)"/>
													            						</xsl:call-template>
																					</table>	
																			</xsl:if>	
												                       <!-- 
												                       </DIV>
												                       </DIV>
												                       </DIV>
												                        -->
												                        
												                        															 <br/>
															New Arrival
    														 <div class="line2"></div><br/>
															
															<div class="scroll-container">
															  <button class="scroll-left" onclick="leftScrollNewArrival()">&#9664;</button>
															   <div class="scroll-content-new-arrival">
																	  <xsl:if test="count(document/newslist/news) != 0">
													                  <xsl:for-each select="document/newslist/news">
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
																	  <xsl:if test="count(document/coproductlist1/coproduct1) != 0">
													                  <xsl:for-each select="document/coproductlist1/coproduct1">
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
																	  <xsl:if test="count(document/coproductlist2/coproduct2) != 0">
													                  <xsl:for-each select="document/coproductlist2/coproduct2">
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
															
									                  		</TD>
									                  	</TR>
									                  	</xsl:if>
							                  </TBODY>
					                  </TABLE>
			                  
			                  	</TD>
			                  </TR>
			                  </TBODY>
			                  
			                  </TABLE>
			                  </DIV>
								<span class="article_separator"></span>

							</div>
						</div>
					</div>
				</td>
			</tr>

		<tr > 
		 <td >
		  <DIV class="module_forum" style="margin-right:0px;">
		  <DIV class="first_forum">
		  <DIV class="sec_forum">
		  <H3>Products review<xsl:value-of select="document/product/name"/></H3>
		  <DIV>
		  <DIV class="forum" style="width:912px" >
		  <DIV>
		  <DIV>
		  
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
																<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_parent_id"><xsl:attribute name="value"><xsl:value-of select="product_parent_id"/></xsl:attribute></INPUT>
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
														<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_parent_id"><xsl:attribute name="value"><xsl:value-of select="product_parent_id"/></xsl:attribute></INPUT>
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
<font color="black">Internet shop . Copyright 2024 
		<A HREF="http://www.cmsmanhattan.com"><font color="black">  FDIS Center Business Solutions Inc </font></A>.  All rights reserved
</font>
<br/>
		
		</div>
		</DIV>
	</DIV>


    <div id="overlay"></div>
    <div id="dialog">
        <iframe id="iframe" src=""></iframe>
        <button onclick="closeDialog()">Close</button>
    </div>
    
    <div id="overlay_mail"></div>
    <div id="dialog_mail">
        <iframe id="iframe_mail" src=""></iframe>
        <button onclick="closeDialogMail()">Close</button>
    </div>

</body>






</HTML>
</xsl:template>

<xsl:template name="prList">
			<xsl:param name="position" select="1"/>
			<xsl:param name="prCount"/>	
			<tr>
			<td>
			
			<table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td width="235px">
					<div class="contentheading"><a ><xsl:attribute name="HREF"><xsl:value-of select="document/product_list/product[$position]/policy_url"/></xsl:attribute> <xsl:value-of select="document/product_list/product[$position]/name"/></a></div>
					<xsl:if test="document/product_list/product[$position]/amount != 0">
					<div style="background-color: #CFCFC8; text-align: right; margin-bottom: 3px"><font class="price" ><xsl:value-of select="document/product_list/product[$position]/amount"/>  </font> </div>
					</xsl:if>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr>
							<td>
								<xsl:if test="document/product_list/product[$position]/icon != ''"> 						                			
											        	<A>
														<xsl:attribute name="HREF"><xsl:value-of select="document/product_list/product[$position]/policy_url"/></xsl:attribute>
														<img  height="80" border="0" style="float: left; margin: 5 15px 5px 5;"  >
									                                   	<xsl:attribute name="alt">
									   				    	  <xsl:for-each select="document/product_list/product[$position]/description/r">
														    <xsl:value-of select="."/> 
												   	    	  </xsl:for-each>
													    	</xsl:attribute>
														<xsl:attribute name="src">
														<xsl:value-of select="document/product_list/product[$position]/icon"/></xsl:attribute>
														</img>
														</A> 				     				   				
					           </xsl:if>
								
							</td>
						</tr>
						<tr>
							<td>
								
								<a > <xsl:attribute name="HREF"><xsl:value-of select="document/product_list/product[$position]/policy_url"/></xsl:attribute>
											   				    	  <xsl:for-each select="document/product_list/product[$position]/description/r">
																    <xsl:value-of select="."/> 
														   	    	  </xsl:for-each>
								</a>
							</td>
						</tr>
					</table>
					
				</td>
				<td width="10px">
				</td>
			    <td width="245px">
			   		<xsl:if test="($position+1) &lt;= ($prCount)">
					<div class="contentheading"><a><xsl:attribute name="HREF"><xsl:value-of select="document/product_list/product[$position+1]/policy_url"/></xsl:attribute> <xsl:value-of select="document/product_list/product[$position+1]/name"/></a></div>
					<xsl:if test="document/product_list/product[$position+1]/amount != 0">
					<div style="background-color: #CFCFC8; text-align: right; margin-bottom: 3px"><font class="price" ><xsl:value-of select="document/product_list/product[$position+1]/amount"/>  </font> </div>
					</xsl:if>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr>
							<td>
							<xsl:if test="document/product_list/product[$position+1]/icon != ''"> 						                			
								<A>
									<xsl:attribute name="HREF"><xsl:value-of select="document/product_list/product[$position+1]/policy_url"/></xsl:attribute>
														<img height="80"  border="0"  style="float: left; margin: 5 15px 5px 5;" >
									                        <xsl:attribute name="alt">
									   				    	  <xsl:for-each select="document/product_list/product[$position+1]/description/r">
														   		 <xsl:value-of select="."/> 
												   	    	  </xsl:for-each>
													    	</xsl:attribute>
														<xsl:attribute name="src">
														<xsl:value-of select="document/product_list/product[$position+1]/icon"/></xsl:attribute>
														</img>
								</A> 							     				  				
					        </xsl:if>
							
							</td>
						</tr>
						<tr>
							<td>
							
								<a > <xsl:attribute name="HREF"><xsl:value-of select="document/product_list/product[$position+1]/policy_url"/></xsl:attribute>
											   				    	  <xsl:for-each select="document/product_list/product[$position+1]/description/r">
																    <xsl:value-of select="."/> 
														   	    	  </xsl:for-each>
								</a>
							</td>
						</tr>
					</table>
					</xsl:if>
				</td>
			  </tr>
			  <tr>
			  	<td width="235px">
					<div>
					<xsl:choose> 

									   <!-- ������ ��������  , ������ -->
									       <xsl:when test="/document/role_id = '2'">									
									       
										
											<table style="height: 30px;">
											    <tbody>
											        <tr>
											         <td style="padding-top: 7px">
											                <form name="product_edit"  action="Productlist.jsp" method="POST">
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="product" ></INPUT>
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="document/product_list/product[$position]/product_id"/></xsl:attribute></INPUT>
													<INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
											                </form>
											         </td>
												 <td style="padding-left: 10px; padding-top: 7px">  
											                <form name="product_del"  action="Productlist.jsp" method="POST">
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="document/product_list/product[$position]/product_id"/></xsl:attribute></INPUT>
													<INPUT class="button" TYPE="submit" name="submit" value="Delete"></INPUT>
											                </form>
											         </td>
											        </tr>
											    </tbody>
											</table>								            
									      									
									
									       
								    </xsl:when> 
								    <!-- ������ ��������  , �����  -->
								
								
								
								
								  <!--  for Edit  User context  ������ ��������  , ������ -->
								   <xsl:when test="/document/role_id = '1'">
									<xsl:variable name="user_id" select="number(/document/owner_user_id)"/> 
									<xsl:variable name="owner_id" select="number(/document/product_list/product[$position]/user_id)"/>
								       <xsl:if test="$owner_id = $user_id" >								
										<table style="height: 30px;">
										    <tbody>
										        <tr>
										           <td style="padding-top: 7px">
								
										                <form name="product_edit"  action="Productlist.jsp" method="POST">
												<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
												<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="userinfo" ></INPUT>
												<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="document/product_list/product[$position]/product_id"/></xsl:attribute></INPUT>
												<INPUT  class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
										                </form>
										           </td>
											   <td style="padding-left: 10px; padding-top: 7px">  
										                <form name="product_del"  action="Productlist.jsp" method="POST">
												<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
												<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="document/product_list/product[$position]/product_id"/></xsl:attribute></INPUT>
												<INPUT  class="button" TYPE="submit" name="submit" value="Delete"></INPUT>
										                </form>
										           </td>
										        </tr>
										    </tbody>
										</table>								           
									</xsl:if>
								   </xsl:when>  
								
								   </xsl:choose> 
								
								    <!-- ������ ��������  , �����  -->	
					
					</div>
					<xsl:if test="($position) &lt; (($prCount)-1)">
						<div class="line2"></div>
					</xsl:if>
			  	</td>
			  	<td width="10px"></td>
			  	<td width="245px">
			  		<xsl:if test="($position+1) &lt;= ($prCount)">
					<div>
					<xsl:choose> 

									   <!-- ������ ��������  , ������ -->
									       <xsl:when test="/document/role_id = '2'">								
									      
										
											<table style="height: 30px;">
											    <tbody>
											        <tr>
											         <td style="padding-top: 7px">
											                <form name="product_edit"  action="Productlist.jsp" method="POST">
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="product" ></INPUT>
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="document/product_list/product[$position+1]/product_id"/></xsl:attribute></INPUT>
													<INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
											                </form>
											         </td>
												 <td style="padding-left: 10px; padding-top: 7px">  
											                <form name="product_del"  action="Productlist.jsp" method="POST">
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
													<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="document/product_list/product[$position+1]/product_id"/></xsl:attribute></INPUT>
													<INPUT class="button" TYPE="submit" name="submit" value="Delete"></INPUT>
											                </form>
											         </td>
											        </tr>
											    </tbody>
											</table>									            
									       	
								    </xsl:when> 
								    <!-- ������ ��������  , �����  -->
								
								
								
								
								  <!--  for Edit  User context  ������ ��������  , ������ -->
								   <xsl:when test="/document/role_id = '1'">
									<xsl:variable name="user_id" select="number(/document/owner_user_id)"/> 
									<xsl:variable name="owner_id" select="number(/document/product_list/product[$position+1]/user_id)"/>
								       <xsl:if test="$owner_id = $user_id" >								
										<table style="height: 30px;">
										    <tbody>
										        <tr>
										           <td style="padding-top: 7px">
								
										                <form name="product_edit"  action="Productlist.jsp" method="POST">
												<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
												<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="userinfo" ></INPUT>
												<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="document/product_list/product[$position+1]/product_id"/></xsl:attribute></INPUT>
												<INPUT  class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
										                </form>
										           </td>
											   <td style="padding-left: 10px; padding-top: 7px">  
										                <form name="product_del"  action="Productlist.jsp" method="POST">
												<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
												<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="document/product_list/product[$position+1]/product_id"/></xsl:attribute></INPUT>
												<INPUT  class="button" TYPE="submit" name="submit" value="Delete"></INPUT>
										                </form>
										           </td>
										        </tr>
										    </tbody>
										</table>								           
									</xsl:if>
								   </xsl:when>  
								
								   </xsl:choose> 
								
								    <!-- ������ ��������  , �����  -->	
					
					</div>	
					<xsl:if test="($position) &lt; (($prCount)-2)">
						<div class="line2"></div>
					</xsl:if>	
					</xsl:if>	  	
			  	</td>			  
			  </tr>
			</table>
			</td>					
			</tr>
			<xsl:if test="$position+2 &lt;= $prCount">
				<xsl:call-template name="prList">
					<xsl:with-param name="position" select="$position+2"/>
					<xsl:with-param name="prCount" select="$prCount"/>
				</xsl:call-template>
			</xsl:if>
</xsl:template>
        


</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios/><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->