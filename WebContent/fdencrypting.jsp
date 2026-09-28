<html>
<head>
 <title>Sample JSP Payment Form with SHA-1</title>
 <style type="text/css">
   label {
      display: block;
      margin: 5px 0px;
      color: #AAA;
   }
   input {
      display: block;
   }
   input[type=submit] {
      margin-top: 20px;
   }

 </style>

</head>
<body onload="document.forms['pay_form'].submit()">
<%@ page import="java.util.Random" %>
<%@ page import="java.lang.System" %>
<%@ page import="javax.crypto.Mac" %>
<%@ page import="javax.crypto.SecretKey" %>
<%@ page import="javax.crypto.spec.SecretKeySpec" %>
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="payGatewayListBeanId" scope="request" class="com.cbsinc.cms.PayGatewayListBean" />
<jsp:useBean id="payGatewayBeanId" scope="request" class="com.cbsinc.cms.PayGatewayBean" />
<jsp:useBean id="orderBeanId" scope="request" class="com.cbsinc.cms.OrderBean" />
<jsp:useBean id="operationAmountBeanId" scope="request" class="com.cbsinc.cms.OperationAmountBean" />

<%

// x_login and transactionKey should be taken from Payment Page settings
String x_login        = "HCO-CENTE-406"; // aka Payment Page ID
String transactionKey = "uPm3HcbbR10dHL2WBok6"; // aka Transaction Key
String x_amount       = "0";

payGatewayListBeanId.mapmingShopBean(authorizationPageBeanId.getSiteId());
payGatewayBeanId = payGatewayListBeanId.getPayGatewayBean(0);

x_login = payGatewayBeanId.getLogin().trim();
transactionKey = payGatewayBeanId.getPasswd().trim();
if(orderBeanId != null ) x_amount =  orderBeanId.getOrderAmount().trim();

//Generate a random sequence number
Random generator = new Random();
int x_fp_sequence = generator.nextInt(1000);

// Generate the timestamp
// Make sure this will be in UTC
long x_fp_timestamp = System.currentTimeMillis()/1000;

// Use Java Cryptography functions to generate the x_fp_hash value
// generate secret key for HMAC-SHA1 using the transaction key
SecretKey key = new SecretKeySpec(transactionKey.getBytes(), "HmacSHA1");

// Get instance of Mac object implementing HMAC-SHA1, and
// Initialize it with the above secret key
Mac mac = Mac.getInstance("HmacSHA1");
mac.init(key);

// process the input string
String inputstring = x_login + "^" + x_fp_sequence + "^" +
x_fp_timestamp + "^" + x_amount + "^";
byte[] result = mac.doFinal(inputstring.getBytes());

// convert the result from byte[] to hexadecimal format
StringBuffer strbuf = new StringBuffer(result.length * 2);
for(int i=0; i< result.length; i++)
   {
       if(((int) result[i] & 0xff) < 0x10)
           strbuf.append("0");
       strbuf.append(Long.toString((int) result[i] & 0xff, 16));
   }
String x_fp_hash = strbuf.toString();
%>

<!-- The form to do post by auto submit button -->
<!--  <form  id="pay_form" action="https://demo.globalgatewaye4.firstdata.com/payment" method="post">  -->
<form  id="pay_form" action="<%= payGatewayBeanId.getPayUrl() %>" method="post">
 <input name="x_login" value="<%= x_login %>" type="hidden" />
 <input name="x_fp_sequence" value="<%= x_fp_sequence %>" type="hidden" />
 <input name="x_fp_timestamp" value="<%= x_fp_timestamp %>" type="hidden" />
 <input name="x_amount" value="<%= x_amount %>"  type="hidden" />
 <input name="x_fp_hash" value="<%= x_fp_hash %>" size="40" type="hidden"  />
 <input name="x_show_form" value="PAYMENT_FORM" type="hidden" />
 <input name="x_relay_response" value="TRUE" type="hidden"> 
 <input name="x_relay_url" value="http://<%= authorizationPageBeanId.getHost()  %>/fdresponce.jsp" type="hidden"> 
 <input name="x_po_num" value="<%= authorizationPageBeanId.getSiteId() + "_" + orderBeanId.getOrderId() %>" type="hidden"> 
 <input name="x_invoice_num" value="<%= operationAmountBeanId.addMoneyStart("Purchase" , new Double( orderBeanId.getOrderAmount()) , orderBeanId.getOrderCurrencyId() , authorizationPageBeanId.getIntUserID(),request.getRemoteAddr() , "" , orderBeanId.getOrderId() )  %>" type="hidden"> 
 <!-- <input type="submit" value="Checkout"   /> -->
</form>
<TABLE cellSpacing="0" cellPadding="0" width="100%" height ="100%"  rightmargin="0" leftmargin="0" topmargin="0"  >
<TR>
authorizationPageBean.getHost()
<TD bgcolor="#ECEFF8" ></TD>

<TD vAlign="top" Align="center" width="1030"  >
<img alt="this is progressbar " src="images/progressbar.gif">
</TD>

<TD bgcolor="#ECEFF8" ></TD>

</TR>
</TABLE>

</body>
</html>