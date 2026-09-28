<%@ page import = "java.io.*,java.util.*" %>
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />

<html>
   <head>
      <title>Web mail service</title>
     <% response.sendRedirect("http://"+ authorizationPageBeanId.getVirtualHost()+":8097") ; %>
   </head>
   
   <body>
      <center>
         <h1>Web mail service</h1>
      </center>
      Use your site login and password to login in email .
      So IMAP server address is the same as site host name 
      and SMPT server address is the same as site host name also
      IMAP port is 993 and SMPT port is 465 and they are both SSL encrypted .
      Software you can use Microsoft Outlook for Window via select IMAP setting when do setup email 
      and Linux and Mac use Thunderbird is free software as Microsoft Outlook .
      <a href="https://james.apache.org/howTo/imap-server.html">Linux and Mac use Thunderbird setup instructions</a>
      <br/>
      <br/>
      Welcome to CMS Manhattan Web mail better version &nbsp;&nbsp;
      <a href="http://<%=authorizationPageBeanId.getVirtualHost()%>:8097"> Webmail 
	  </a>
 
   </body>
</html>