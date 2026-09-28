<?xml version='1.0' encoding='windows-1251' ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"  xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:output method="html" indent="yes"/>
<xsl:output encoding="UTF-8"/>
<xsl:strip-space elements="*"/>

<xsl:template match="/">
<xsl:variable name="host" select="string(document/host)"/>
<xsl:variable name="role" select="document/role_id"/>
<xsl:variable name="page" select="number(document/offset)"/>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en"  lang="en">

<head>
       <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"/>
     <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE"/>
     <meta name="robots content='index,follow'"/>
     <meta name="keywords" content="free internet shop, buy internet shop , develop internet shop, London , New York , Toronto"/>
     <meta name="description" content="Portal with ready internet shop solutions with support and development "/>
     <title>www.siteforyou.net</title>
     <LINK id="style2" rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/style2.css')"/></xsl:attribute></LINK> 
     <SCRIPT type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/js/common.js')"/></xsl:attribute></SCRIPT>
     <SCRIPT type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/js/caption.js')"/></xsl:attribute></SCRIPT>
     
     <link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/css/jquery.lightbox.css')"/></xsl:attribute></link>
     
     <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/js/jquery.js')"/></xsl:attribute></script>
	 <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/js/jquery.lightbox.js')"/></xsl:attribute></script>
	
</head>


<body>

<TABLE cellSpacing="0" cellPadding="0" width="100%" height="100%"  border="1" rightmargin="0" leftmargin="0" topmargin="0" bordercolor="#ECEFF8" >
<TR>
<TD bgcolor="#ECEFF8" ></TD>
<TD vAlign="top" Align="center" width="1030" style="border: 0px solid #ECEFF8" >


<a class="skipnav" href="#documentContent">Skip to content</a>

<div>

     

        <div class="top">


<TABLE cellSpacing="0" cellPadding="10" height="120" width="100%" class="bacgraundTop" border="0" rightmargin="0" leftmargin="0" topmargin="0">
  <TBODY>
  <TR>
    <TD vAlign="top" Align="left"  width="20%">
<!--      <img src="xsl/reservation.gvidons.net/img/logotip.gif" border="0" height="70" width="90"   /> -->
    </TD>
    <TD vAlign="center" Align="right"  width="80%">
                <form name="searchform"  action="Productlist.jsp" >
		<br />
                    <input id="search_value"  name="search_value" type="text"  size="20" alt="Search in goods name"   title="Search in goods name" tabindex="30001" ><xsl:attribute name="value"><xsl:value-of select="document/search_value"/></xsl:attribute></input>
                    <input class="context searchButton"  type="submit" size="20" value="Поиск"  tabindex="30002" />
                </form>

</TD>
  </TR>
  </TBODY>
</TABLE>

<!--

            <div class="searchBox">
                <form name="searchform"
                      action="">

                    <input id="searchGadget"
                           name="SearchableText" type="text"
                           size="20" alt="Search"
                           title="Search" tabindex="30001" />

                    <input class="context searchButton"
                           type="submit" size="20" value="Search"
                           tabindex="30002" />
                </form>
            </div>
<br />
<br />


	<div class="box">
	  <div class="body">
	    <div class="content even"> <img src="xsl/reservation.gvidons.net/img/logo5.jpg" border="0" ><font size="3"><b> Internet shop <xsl:value-of select="document/site_name"/></b></font></img></div>
	  </div>
	</div>

-->


<!--	
            <a href="http://www.jabber.ru/login_form">
                <img src="xsl/localhost/img/index_00.gif" alt="Link icon" title="Link icon" height="11" width="6" border="0" />
            </a>
-->

        </div>

        <hr size="" class="netscape4" />

        <div class="tabs">
		
        <A href="Productlist.jsp?catalog_id=-2" class="plain"><font size="2">Main page</font></A>
        <A href="Domain.jsp" class="plain"><font size="2">Registration of domains</font></A> 

	    <A href="webmail/" class="plain"><font size="2">Mail </font></A> 
			
        <xsl:if test="document/login != 'user'">
        	<A href="Productlist.jsp?action=logoff" class="plain"><font size="2">Exit</font></A>        	
        </xsl:if>

        </div>


       <div class="personalBar">
		<!-- 
	    <a href="Productlist.jsp?locale=ru">
                <img alt="Link icon" title="Russian" height="13" width="16" border="0">
                	<xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/flag_ru.gif')"/></xsl:attribute>
                </img>
            </a>
          -->   
	    <a href="Productlist.jsp?locale=en">
                <img alt="Link icon" title="English" height="13" width="16" border="0">
                	<xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/flag_en.gif')"/></xsl:attribute>
                </img>
            </a>
             <a href="webmail/">
                <img alt="Mail for boss" title="Boss Mail" height="16" width="16" border="0">
                	<xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/email.png')"/></xsl:attribute>
                </img>
				login: 

               <xsl:if test="document/login = 'user'">   <!--  показывать если нет логина -->
		 			<xsl:value-of select="document/login"/>
               </xsl:if>

               <xsl:if test="document/login != 'user'">   <!--  показывать если нет логина -->
		 			<font color="red"><xsl:value-of select="document/login"/></font>
               </xsl:if>
            </a>
        </div>



        <div class="pathBar">
            <span>
                <span> . </span>
            </span>

        </div>

        <hr size="" class="netscape4" />

    </div>



<table class="columns">

    <tbody>
        <tr>
            <td class="left">

  <xsl:if test="document/login = 'user'">   <!--  To show, if there is no login -->

	  <!-- head for porlet login   --> 
          <TABLE height="20" cellSpacing="4" cellPadding="0" width="159" background="index.files/bg11.gif" >
              <TBODY>
              <TR>
                <TD vAlign="center" ><FONT color="white" ><B>For buyers</B></FONT>
               </TD>
              </TR>
  	     </TBODY>
  	   </TABLE>

	  <!-- porlet login   --> 

    <div class="box">
        <div class="body">
        
            <div class="content odd">
                <form action="Authorization.jsp"
                      method="post">
    
                    
    
                    <strong>User</strong> <br />



                     <INPUT  title="User" tabindex="10001" SIZE="12" AUTOCOMPLETE="off" TYPE="TEXT" NAME="Login" >
			<xsl:attribute name="value">
				<xsl:value-of select="document/login"/>
			</xsl:attribute>		
        	     </INPUT>
    
                    <br />
    
                    <strong>Password</strong>
		    <br />
		    <INPUT title="Password" tabindex="10002"  SIZE="12" AUTOCOMPLETE="off" TYPE="PASSWORD" NAME="Passwd1" ></INPUT>
                    <br />
                    <br />
    
                    <input class="context searchButton"
                           type="submit" name="submit"
                           value="Log in" tabindex="10003" />
                </form>
            </div>
    
            <div class="content even"> 
                <a href="">
                   <img src="images/user.gif" alt="Link icon" title="Link icon" height="15" width="10" border="0" />
                   To send the password by mail
                </a>
            </div>
        </div>
    </div>

</xsl:if>


<div>
    <div class="portlet">
    <h5><strong>Help</strong></h5>
      <div class="body">
        <div class="portletContent odd">
		This is screen describes detail about the transaction
        </div>
        <div class="portletContent even">
		Click back to find another transaction.
        </div>
      </div>
    </div>
</div>






            </td>

            <td class="main">

            <!-- News part -->

	    <h1>The detailed description of your monetary operation</h1>
<br/>



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


        <!-- Navigation -->
        <div class="listingBar">
	    <span class="next">
                <a HREF = "#" onClick="javascript:history.back()"  >
		<strong>	
		Back
		</strong>	
	        </a>
	    </span>
	</div>


            </td>

            <td class="right">
            
            <xsl:choose> 
	     <xsl:when test="/document/role_id != '0'">
	     
	     
	      <div class="box"  >
	       <div style="height:21px;width:159px; TEXT-ALIGN: left" class="bacgraundBoxTitle">
	           <TABLE class="text"   width="100%"   height="20" cellSpacing="4" cellPadding="0" >
	              <TBODY>
	              <TR>
	                <TD vAlign="center" ><FONT color="white" ><B>My office</B></FONT>
	               </TD>
	              </TR>
	  	     </TBODY>
	  	   </TABLE>
			</div>
	                                                               

	           <TABLE cellSpacing="0" cellPadding="0" width="100%" align="center"  class="bacgraundBoxLeft" border="0" rightmargin="0"  leftmargin="0" topmargin="0">
	           <TBODY>
					<TR><TD width="5"></TD><TD><br/></TD></TR>
	              <TR><TD width="5"></TD><TD><A class="menu"  href="Productlist.jsp?action=login_usersite" alt="My portal or the Internet shop"  > My shop</A></TD></TR>

	              <TR><TD width="159" colspan="2"  class="bacgraundLine"  height="10" ></TD></TR>
	     	 
				 <TR><TD width="5"></TD><TD>
				 		<A class="menu"  ><xsl:attribute name="HREF"><xsl:value-of select="document/shoping_url"/></xsl:attribute>Return to navigator </A>
				 </TD></TR>

		 		 <TR><TD width="159" colspan="2"  class="bacgraundLine"  height="10" ></TD></TR>
			 
				 <TR><TD width="5"></TD><TD>
			 		<A class="menu" ><xsl:attribute name="HREF"><xsl:value-of select="document/to_account_history"/></xsl:attribute>Your balans <xsl:value-of select="document/balans"/> $ .</A>
				 </TD></TR>

				 <TR><TD width="159" colspan="2"  class="bacgraundLine"  height="10" ></TD></TR>
			 
				 <TR><TD width="5"></TD><TD>
				 		<A class="menu" ><xsl:attribute name="HREF"><xsl:value-of select="document/to_order_hist"/></xsl:attribute>Order list</A>
				 </TD></TR>
				 
				 <TR><TD width="159" colspan="2"  class="bacgraundLine"  height="10" ></TD></TR>
			 
				 <TR><TD width="5"></TD><TD>
				    <A class="menu" ><xsl:attribute name="HREF"><xsl:value-of select="document/to_order"/></xsl:attribute>Order</A>
				 </TD></TR>


	              <TR><TD width="155" class="bacgraundLine2" colSpan="2" height="11" ></TD></TR>

	             </TBODY>
	             </TABLE>
	       
	     </div>
	    
	    

	     </xsl:when>
	   </xsl:choose> 

      
            </td>
        </tr>
    </tbody>
</table>


<hr size="" class="netscape4" />



</TD>
<TD bgcolor="#ECEFF8" ></TD>
</TR>

<TR>
<TD bgcolor="#ECEFF8" ></TD>
<TD vAlign="bottom" Align="center"  style="border: 0px solid #ECEFF8" >

<div class="footer">
<font color="black">Internet shop . Copyright 2010 
		<A HREF="http://www.siteforyou.net"><font color="black">  FDIS Center Business Solutions Inc </font></A>.  All rights reserved
</font>
<hr size="" class="netscape4" />
<strong class="netscape4">
for user netscape 
</strong>
</div>

</TD>
<TD bgcolor="#ECEFF8" ></TD>
</TR>

</TABLE>


</body>
</html>
</xsl:template>
</xsl:stylesheet>