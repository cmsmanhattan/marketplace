<?xml version='1.0' encoding='windows-1251' ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"  xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:output method="html" indent="yes"/>
<xsl:output encoding="UTF-8"/>
<xsl:strip-space elements="*"/>


<xsl:template match="/">

<html>

<head>
     <title>GBS Portal</title>
     <style type="text/css" media="screen"> @import url(style2.css);</style>
</head>

<body>

<TABLE cellSpacing="0" cellPadding="0" width="100%"  border="1" rightmargin="0" leftmargin="0" topmargin="0" bordercolor="#ECEFF8" >
<TR>
<TD bgcolor="#ECEFF8" ></TD>
<TD vAlign="top" Align="center" width="1030">

<a class="skipnav" href="#documentContent">Skip to content</a>

<div>

     

        <div class="top">

<TABLE cellSpacing="0" cellPadding="10"  height="120"  width="100%" background="index.files/bg.jpg" border="0" rightmargin="0" leftmargin="0" topmargin="0">
  <TBODY>
  <TR>
    <TD vAlign="top" Align="left"  width="20%">
<!--      <img src="xsl/reservation.gvidons.net/img/logotip.gif" border="0" height="70" width="90"   /> -->
    </TD>
    <TD vAlign="center" Align="right"  width="80%">
                <form name="searchform"  action="Productlist.jsp" >
		<br />
                    <input id="search_value"  name="search_value" type="text"  size="20" alt="Search in goods name"   title="Search in goods name" tabindex="30001" ><xsl:attribute name="value"><xsl:value-of select="document/search_value"/></xsl:attribute></input>
                    <input class="context searchButton"  type="submit" size="20" value="Search"  tabindex="30002" />
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
	    <div class="content even"> <img src="xsl/reservation.gvidons.net/img/logo5.jpg" border="0" ><font size="3"><b> Internet shop<xsl:value-of select="document/site_name"/></b></font></img></div>
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

        
           <a href="Productlist.jsp?catalog_id=-1" class="plain" ><font size="2"> News </font> </a>
           <a href="Productlist.jsp?catalog_id=-2" class="plain" ><font size="2"> The main page </font> </a>
           <A href="Authorization.jsp?Login=newuser" class="plain" ><font size="2"> Registration of the buyer </font></A>
           <a href="about.html" class="plain"><font size="2">  About the company </font></a>

        

        </div>

        <div class="personalBar">

            <a href="Authorization.jsp">
                <img src="images/user.gif" alt="Link icon" title="Link icon" height="15" width="10" border="0" />
		Login is <xsl:value-of select="document/login"/>@<xsl:value-of select="document/host"/>
            </a>
            
        </div>

        <div class="pathBar">
            <span>
                <span> </span>
            </span>

        </div>

        <hr size="" class="netscape4" />

    </div>



<table class="columns">

    <tbody>
        <tr>
            <td class="left">

  <xsl:if test="document/login = 'user'">   <!--  To show if there is no login -->

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
            <div class="odd">
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
    
            <div class="even"> 
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
<strong>1.</strong>By pressing buttons "In bank", You get to a payment sluice of bank.
        </div>
        <div class="portletContent even">
<strong>2.</strong> 
        </div>
      </div>
    </div>
</div>






            </td>

            <td class="main">

            <!-- News part -->

	    <h1>Sending of the data in bank</h1>
<br/>


<!--  <FORM ACTION="https://secure.assist.ru/shops/cardpayment.cfm" METHOD="POST"> -->
<!--		<FORM ACTION="https://pgate.grabko.com:443/authorizationPay.do" METHOD="POST">  -->
		<!-- Stripe Checkout: the controller normally redirects straight to Stripe;
		     this branch renders only if the redirect was suppressed. -->
		<xsl:choose>
		<xsl:when test="document/payment_channel = 'stripe'">
		<div class="box">
		  <div class="body">
			<p>Payment for the sum - <xsl:value-of select="document/amount"/> <xsl:value-of select="document/currency_paysys_id"/></p>
			<p><a class="button" href="{document/stripe_checkout_url}">Pay with card (Stripe)</a></p>
			<p><a href="Pay.jsp?channel=legacy&amp;Amount={document/amount}&amp;currency_id={document/currency_id}&amp;paysystem_id=1">Use bank gateway instead</a></p>
		  </div>
		</div>
		</xsl:when>
		<xsl:otherwise>
		<FORM ACTION="https://pgate.grabko.com:8443/authorizationPay.do" METHOD="POST">
		<div class="box">
		  <div class="body">
		    <div >
			<table >
			    <tbody>
			<!-- <xsl:value-of select="document/currency_paysys_id"/> -->
			<TR><TD align="CENTER"  colspan="5" >Payment for the sum  - <xsl:value-of select="document/amount"/></TD><TD></TD><TD width="400" align="LEFT">
			<INPUT TYPE="HIDDEN" NAME="Shop_IDP" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/shop_paysys_id"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="Order_IDP" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/account_history_id"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" SIZE="10" NAME="Subtotal_P" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/amount"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="Delay" VALUE="0"></INPUT>
			<INPUT TYPE="HIDDEN" NAME="Language" VALUE="0"></INPUT>
			<INPUT TYPE="HIDDEN" NAME="URL_RETURN_OK" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/url_rezalt_banking_ok"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="URL_RETURN_NO" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/url_rezalt_banking_no"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="Currency" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/currency_paysys_id"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="Comment" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/decsription"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="LastName" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/lastname"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="FirstName" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/firstname"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="MiddleName" VALUE=""></INPUT>
			<INPUT TYPE="HIDDEN" NAME="Email" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/email"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="Address" VALUE=""></INPUT>
			<INPUT TYPE="HIDDEN" NAME="Phone" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/mphone"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="Country" VALUE=""></INPUT>
			<INPUT TYPE="HIDDEN" NAME="State" VALUE=""></INPUT>
			<INPUT TYPE="HIDDEN" NAME="City" VALUE=""></INPUT>
			<INPUT TYPE="HIDDEN" NAME="Zip" VALUE=""></INPUT>
			<INPUT TYPE="HIDDEN" NAME="ChoosenCardType" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/type_creditcard"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="IsFrame" VALUE="1"></INPUT>
			<INPUT TYPE="HIDDEN" NAME="f_Email" VALUE="1"></INPUT>
			<INPUT TYPE="HIDDEN" NAME="CardPayment" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/card_payment"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="WalletPayment" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/wallet_payment"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="WebMoneyPayment" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/webmoney_payment"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="RapidaPayment" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/papida_payment"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="PayCashPayment" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/paycash_payment"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="EPortPayment" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/eport_payment"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="KreditPilotPayment" VALUE=""><xsl:attribute name="VALUE"><xsl:value-of select="document/kredit_pilotpayment"/></xsl:attribute></INPUT>
			<INPUT TYPE="HIDDEN" NAME="ListParameters" VALUE=""></INPUT>
     		      <!--	<INPUT TYPE="HIDDEN" NAME="DemoResult" VALUE="AS000" ID="Hidden1"></INPUT> -->
			
			</TD><TD  align="right" ><input type="submit" name="Submit" value="In bank"></input></TD></TR>


			    </tbody>
			</table>
		    </div>
		  </div>
		</div>
	</FORM>
		</xsl:otherwise>
		</xsl:choose>


        <!-- Navigation -->
        <div class="listingBar">
	    <span class="next">
                <a HREF = "#" onClick="javascript:history.back()"  >
		<strong>	
		назад
		</strong>	
	        </a>
	    </span>
	</div>


            </td>

            <td class="right">


        <TABLE height="20" cellSpacing="4" cellPadding="0" width="159" background="index.files/bg11.gif" >
           <TBODY>
            <TR>
                <TD vAlign="center" ><FONT color="white" ><B>Buy</B></FONT>
            </TD>
            </TR>
           </TBODY>
        </TABLE>
	<div class="box">
	  <div class="body">
	    <div class="even" align="left" >
		<A><xsl:attribute name="HREF"><xsl:value-of select="document/shoping_url"/></xsl:attribute><IMG border="0" height="20" name="Nindex_14_03" src="images/user.gif" width="20"></IMG>To return to the catalogue for continuation of purchases</A>
	    </div>
	  </div>
	</div>


        <TABLE height="20" cellSpacing="4" cellPadding="0" width="159" background="index.files/bg11.gif" >
           <TBODY>
            <TR>
                <TD vAlign="center" ><FONT color="white" ><B>Your balance</B></FONT>
            </TD>
            </TR>
           </TBODY>
        </TABLE>
	<div class="box">
	  <div class="body">
	    <div class="even" align="left" >
		<A><xsl:attribute name="HREF"><xsl:value-of select="document/to_account_history"/></xsl:attribute><IMG border="0" height="20" name="Nindex_14_03" src="images/user.gif" width="20"></IMG>Your monetary operations.<br/>Presence of money resources in shop<br/> <xsl:value-of select="document/balans"/>$.</A>
	    </div>
	  </div>
	</div>


        <TABLE height="20" cellSpacing="4" cellPadding="0" width="159" background="index.files/bg11.gif" >
           <TBODY>
            <TR>
                <TD vAlign="center" ><FONT color="white" ><B>All your orders</B></FONT>
            </TD>
            </TR>
           </TBODY>
        </TABLE>
	<div class="box">
	  <div class="body">
	    <div class="even" align="left" >
		<A><xsl:attribute name="HREF"><xsl:value-of select="document/to_order_hist"/></xsl:attribute><IMG border="0" name="Nindex_14_03" src="images/user.gif" height="20"   width="20"></IMG>Orders</A>
	    </div>
	  </div>
	</div>


        <TABLE height="20" cellSpacing="4" cellPadding="0" width="159" background="index.files/bg11.gif" >
           <TBODY>
            <TR>
                <TD vAlign="center" ><FONT color="white" ><B>The current order</B></FONT>
            </TD>
            </TR>
           </TBODY>
        </TABLE>
	<div class="box">
	  <div class="body">
	    <div class="even" align="left" >
		<A><xsl:attribute name="HREF"><xsl:value-of select="document/to_order"/></xsl:attribute><IMG border="0"  name="Nindex_14_03" src="images/user.gif" height="20"  width="20"></IMG>Order</A>
	    </div>
	  </div>
	</div>


        <TABLE height="20" cellSpacing="4" cellPadding="0" width="159" background="index.files/bg11.gif" >
           <TBODY>
            <TR>
                <TD vAlign="center" ><FONT color="white" ><B>To pay the order</B></FONT>
            </TD>
            </TR>
           </TBODY>
        </TABLE>
	<div class="box">
	  <div class="body">
	    <div class="even" align="left" ><br/><IMG border="0" height="20" name="Nindex_14_03" src="images/user.gif" width="20"></IMG> You can translate money resources in shop for order payment, using credit cards or virtual purses, such as Web money, Yandex money, E-Port and others<br/><br/>
		<A><xsl:attribute name="HREF"><xsl:value-of select="document/to_pay"/></xsl:attribute><IMG border="0" height="20" name="Nindex_14_03" src="xsl/reservation.gvidons.net/img/credit-cards.jpg" width="130"></IMG></A>
	    </div>
	  </div>
	</div>




            </td>
        </tr>
    </tbody>
</table>


<hr size="" class="netscape4" />

<div class="footer">


<br />

Marketpace. Copyright 2026 <A HREF="http://www.cmsmanhattan.com">CMS Manhattan</A>. All rights reserved.

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
