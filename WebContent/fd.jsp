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
<body>

<%@ page import="java.util.Random" %>
<%@ page import="java.lang.System" %>
<%@ page import="javax.crypto.Mac" %>
<%@ page import="javax.crypto.SecretKey" %>
<%@ page import="javax.crypto.spec.SecretKeySpec" %>

<%
// x_login and transactionKey should be taken from Payment Page settings
String x_login        = "HCO-CENTE-406"; // aka Payment Page ID
String transactionKey = "91KiBFRtE8fF7VHc8tvr"; // aka Transaction Key
String x_amount       = "19.99";

// Generate a random sequence number
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
x_fp_timestamp + "^" + x_amount;
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

<form action="https://demo.globalgatewaye4.firstdata.com/payment" method="post">
 <label>x_login</label>
 <input name="x_login" value="<%= x_login %>" />
 <label>x_fp_sequence</label>
 <input name="x_fp_sequence" value="<%= x_fp_sequence %>" />
 <label>x_fp_timestamp</label>
 <input name="x_fp_timestamp" value="<%= x_fp_timestamp %>" />
 <label>x_amount</label>
 <input name="x_amount" value="<%= x_amount %>" />
 <label>x_fp_hash (with SHA-1)</label>
 <input name="x_fp_hash" value="<%= x_fp_hash %>" size="40"/>
 <input name="x_show_form" value="PAYMENT_FORM" type="hidden" />
 <input type="submit" value="Checkout" />
</form>

</body>
</html>