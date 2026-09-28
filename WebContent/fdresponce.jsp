<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<body>
 <h1>Merchant.com Online Store</h1>

 <h2>Thanks for your order</h2>

 <p>
  Your order was processed successfully. Here is your receipt. 
  Your order will be shipped in two business days.
 </p>
 <pre>
  <%=request.getParameter("exact_ctr")%>
 </pre>

 <% if(request.getParameter("exact_issname") != null) { %>
 <p>
  Issuer:   <%=request.getParameter("exact_issname")%>
  Confirmation Number:   <%=request.getParameter("exact_issconf")%>
 </p>
 <% } %>

 <p>
  <% String track_url = "http://merchant.com/order_tracking/" + request.getParameter("x_invoice_num"); %>
  You can track it at <a href="<%= track_url%>"><%= track_url %></a>.
 </p>

 <p>
  Return to <a href="http://merchant.com/home">home</a>.
 </p>
</body>
</html>