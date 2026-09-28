<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="payGatewayListBeanId" scope="session" class="com.cbsinc.cms.PayGatewayListBean" />
<jsp:useBean id="payGatewayBeanId" scope="session" class="com.cbsinc.cms.PayGatewayBean" />

<%
  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
  request.setCharacterEncoding("UTF-8");

  if( request.getParameter("row") != null)
  {
  int index =  payGatewayListBeanId.stringToInt(request.getParameter("row")) ;
  payGatewayListBeanId.setIndxSelect(index);
  payGatewayBeanId.setShopId(payGatewayListBeanId.getPayGatewayBean(index).getShopId());
  payGatewayBeanId.setShopCd(payGatewayListBeanId.getPayGatewayBean(index).getShopCd());
  payGatewayBeanId.setPasswd(payGatewayListBeanId.getPayGatewayBean(index).getPasswd());
  payGatewayBeanId.setLogin(payGatewayListBeanId.getPayGatewayBean(index).getLogin());
  payGatewayBeanId.setPayGatewayId(payGatewayListBeanId.getPayGatewayBean(index).getPayGatewayId());
  payGatewayBeanId.setNameGateway(payGatewayListBeanId.getPayGatewayBean(index).getNameGateway());
  }

  if (request.getParameter("pay_gateway_id")!= null )   payGatewayBeanId.setPayGatewayId( request.getParameter("pay_gateway_id") );
  if (request.getParameter("shop_cd")  != null){ payGatewayBeanId.setShopCd( request.getParameter("shop_cd")) ; }
  if (request.getParameter("login") != null ) {  payGatewayBeanId.setLogin( request.getParameter("login")); }
  if (request.getParameter("passwd") != null ) {  payGatewayBeanId.setPasswd( request.getParameter("passwd") ); }
  if (request.getParameter("retype_passwd") != null ) {  payGatewayBeanId.setRertypePasswd( request.getParameter("retype_passwd") ); }

  if( authorizationPageBeanId.getRoleId() != 2 ){
  authorizationPageBeanId.setStrMessage("You don't have access to add position , send mail to grabko@mail.ru for access") ;
  response.sendRedirect("Authorization.jsp" );
  }

  if(request.getMethod().toUpperCase().compareTo("POST") == 0)
  {
  if(payGatewayBeanId.getShopId().length() == 0 ) payGatewayBeanId.addShopBean(authorizationPageBeanId.getSiteId());
  else payGatewayBeanId.saveShopBean();

  payGatewayBeanId.setShopId("");
  payGatewayBeanId.setShopCd("");
  payGatewayBeanId.setPasswd("");
  payGatewayBeanId.setLogin("");
  payGatewayBeanId.setPayGatewayId("");
  payGatewayBeanId.setNameGateway("");
  payGatewayBeanId.setPayUrl("");

  response.sendRedirect(payGatewayListBeanId.getCururl());
  }
 
%>

<html>
<head>
     <title>GBS Portal</title>
     <style type="text/css" media="screen"> @import url(style2.css);</style>
	 <style type="text/css">
	 .drag{
      position:relative;
      cursor:hand
      top: 120px;
      left: 130px;
	 }
	 #vunet{
      background-color:#F4F4F4;
      cursor:move;
      width:400px;
      filter:alpha(opacity=85);
      -moz-opacity:.85;
      opacity:.85;
	 }
	 </style>
	 
  </head>
<body>
<TABLE cellSpacing="0" cellPadding="0" width="100%"  border="0" rightmargin="0" leftmargin="0" topmargin="0" bordercolor="#ECEFF8" >
<TR>
<TD bgcolor="#ECEFF8" style="border: 1px solid #ECEFF8"></TD>
<TD vAlign="top" Align="center" width="1010">
<a class="skipnav" href="#documentContent">Skip to content</a>
<div>
        <div class="top">
        </div>
        <hr size="" class="netscape4" />
        <div class="pathBar">
            <span>
                <span> <%=authorizationPageBeanId.getLocalization(application).getString("control_of_site")%> </span>
            </span>
        </div>
        <hr size="" class="netscape4" />
    </div>

<table class="columns" style="height: 1000px;">

    <tbody>
        <tr>
            <td class="left">
		<div>
		    <div class="portlet">
		    <h5><strong><%=authorizationPageBeanId.getLocalization(application).getString("help")%></strong></h5>
		      <div class="body">
		        <div class="portletContent odd">
		        <%=authorizationPageBeanId.getLocalization(application).getString("help_gateway_setup_productpost_1")%>
		        </div>
		        <!-- 
		        <div class="portletContent even">
		        <%= authorizationPageBeanId.getLocalization(application).getString("help_gateway_setup_productpost_2") %>
		        </div>
		         -->
		      </div>
		    </div>
		</div>

            </td>

            <td class="main">

            <!-- News part -->

	     <h1><%=authorizationPageBeanId.getLocalization(application).getString("title_payment_settings_of_form")%></h1>

		
		
		<br/>
	    <DIV style="background-image:url('images/f.jpg');height:20px; TEXT-ALIGN: left"  >
				<font color='white' size='2' > &nbsp;&raquo; <%=authorizationPageBeanId.getLocalization(application).getString("selection_setup_your_paygateway_of_form")%></font>
		</DIV>
		<div class='box'>
		<div class='body'>
		<div>        
		<form method="post"  ACTION="PayGatewaySetup.jsp"  >
                     <TABLE>
                     <TR><TD>Payment Gateway  :* </TD><TD><%=  payGatewayBeanId.getComboBox("pay_gateway_id", payGatewayBeanId.getPayGatewayId() ,"SELECT pay_gateway_id , name_gateway  FROM pay_gateway  where active = true"  ) %></TD></TR>
                     <TR><TD>Login:* </TD> <TD><input  name="login" size="20" value="<%= payGatewayBeanId.getLogin() %>" onBlur="checkEmpty(this.value)"  /></TD></TR>
                     <TR><TD>Transaction Key:* </TD> <TD><input  name="passwd" size="20" value="<%= payGatewayBeanId.getPasswd() %>" /></TD></TR>
                     <TR><TD></TD> <TD><input type="submit" name="Submit" value="Save"> <input type="reset" value="Reset"/></TD></TR>
                     </TABLE>
        </form>
		</div>
		</div>
		</div>


		<br/>



        <!-- Navigation -->
        <div class="listingBar">
	    <span class="next">
                <a HREF = "PostManager.jsp"  >
		<strong>
		<%=authorizationPageBeanId.getLocalization(application).getString("back")%>
		</strong>
	        </a>
	    </span>
		</div>


            </td>

            <td class="right">

            </td>
        </tr>
    </tbody>
</table>


<hr size="" class="netscape4" />

<div class="footer">


<br />

 <%=authorizationPageBeanId.getLocalization(application).getString("all_rights_reserved")%>

<hr size="" class="netscape4" />

<strong class="netscape4">
for user netscape
</strong>

</div>
</TD>
<TD bgcolor="#ECEFF8" style="border: 1px solid #ECEFF8"></TD>
</TR>
</TABLE>


</body>
</html>