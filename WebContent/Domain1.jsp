<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page errorPage="error.jsp" %>
<%@ page import="com.cbsinc.cms.services.whois.DomainState"%>
<%
  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
  request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="domainList" scope="session" class="java.util.LinkedList" type="java.util.LinkedList"/>
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
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

        </div>

        <hr size="" class="netscape4" />


        <div class="pathBar">
                <font size="4"> <%=authorizationPageBeanId.getLocalization(application).getString("site_controll")%> </font>

        </div>

        <hr size="" class="netscape4" />

    </div>



<table class="columns">

    <tbody>
        <tr>
            <td class="left">


<div>
    <div class="portlet">
    <h5><strong>Помощ</strong></h5>
      <div class="body">
        <div class="portletContent odd">
         Это главное меню управления сайтом .
        </div>
        <div class="portletContent even">
	Здесь можно добавлять товары ,
	новости на саит  , и  рубрики
	для обьединения товаров в группы
        </div>
      </div>
    </div>
</div>








            </td>

            <td class="main">

            <!-- News part -->

	    <h1><%=authorizationPageBeanId.getLocalization(application).getString("find_domain")%></h1>
<br/>


		<div class="box">
		  <div class="body">
		    <div >
		    <div >



				<table width="670"    border="0" cellspacing="0" cellpadding="5" bgcolor="#DFE3EF">
					<tr>
						<td colspan="2">&nbsp;<br/><font color="red"><%=request.getAttribute("message") == null?"":request.getAttribute("message")%></font></td>
                    </tr>
                    <tr>
						<td width="500" align="center">
						<form action="Domain.jsp"  method="post" >
						<input type="text" size="70" name="domain" value="<%= request.getAttribute("domain") == null?"":request.getAttribute("domain") %>"  />  
						<input type="submit"  name="submit" value="<%= authorizationPageBeanId.getLocalization(application).getString("do_find") %>" />  						
						</form>
						</td>
					</tr>
					<%
					java.util.Iterator iterator = domainList.descendingIterator() ;
					%>
					
					<%
										while(iterator.hasNext()){
										%>
					<%
					DomainState domainState  = (DomainState)iterator.next();
					%>
					<tr>
						<td width="500" align="left"><img SRC="images/home.png" border="0" alt="Domain" > <%=domainState.getDomain() + " " + domainState.getStatus()%> </td>
						<td>
						<%
						if(domainState.isFree()){
						%>
						<form action="Domain.jsp"  method="post" ><input type="hidden" name="regdomain" value="<%= request.getAttribute("domain") == null?"":request.getAttribute("domain") %>"  /><input type="submit"  name="submit" value="<%= authorizationPageBeanId.getLocalization(application).getString("send_application") %>" /> </form>
						<% } %>
						</td>
					</tr>
					<tr>
						<td colspan="2">  &nbsp;  </td>
					</tr>
					<% } %>
					
					<tr>
						<td height="500"  colspan="2">&nbsp;</td>
					</tr>

				</table>
		    </div>
		  </div>
		</div>


        <!-- Navigation -->
        <div class="listingBar">
  	    <span class="next"> <a HREF = "Productlist.jsp?catalog_id=-2"  ><strong>назад</strong></a> </span>
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

Product Media@Shop  - Internet shop. Copyright 2005 <A HREF="http://www.grabko.com">Grabko Business Solution</A> . Все права защищены.

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