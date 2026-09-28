<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<%
  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
  request.setCharacterEncoding("UTF-8");
%>
<html>

<head>
     <title>GBS Portal</title>
     <style type="text/css" media="screen"> @import url(style2.css);</style>

</head>

<body>
<TABLE cellSpacing="0" cellPadding="0" width="100%"  border="0" rightmargin="0" leftmargin="0" topmargin="0" bordercolor="#ECEFF8" >
<TR>
<TD bgcolor="#ECEFF8" style="border: 1px solid #ECEFF8"></TD>
<TD vAlign="top" Align="center" width="1030" style="border: 0px solid #ECEFF8" >


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



            </td>

            <td class="main">

            <!-- News part -->

	    <h1><%=authorizationPageBeanId.getLocalization(application).getString("setup_page")%></h1><font color="red" size="3" ></font>
		<br/>


		<div class="box">
		  <div class="body">
		    <div >
		    <div >



				<table width="670"    border="0" cellspacing="0" cellpadding="5" bgcolor="#DFE3EF">
					<tr>
						<td colspan="2">&nbsp;</td>
                    </tr>
					<tr>
						<td width="500" align="left"><A HREF="http://<%=authorizationPageBeanId.getVirtualHost()%>:8081/actuator/swagger-ui" ><img SRC="images/file.png" border="0" alt="Post Product center" >CMS Catalog REST OPEN API</A></td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;</td>
                    </tr>
					<tr>
						<td width="500" align="left"><A HREF="http://<%=authorizationPageBeanId.getVirtualHost()%>:8087/actuator/swagger-ui" ><img SRC="images/file.png" border="0" alt="Post Product center" >Web Mail REST OPEN API   </A></td>
					</tr>
					
					<tr>
						<td colspan="2">&nbsp;</td>
                    </tr>
					<tr>
						<td width="500" align="left"><A HREF="http://<%=authorizationPageBeanId.getVirtualHost()%>:8088/actuator/swagger-ui" ><img SRC="images/file.png" border="0" alt="Post Product center" >Web Mail Administrator REST OPEN API</A></td>
					</tr>
					
					<tr>
						<td colspan="2">&nbsp;</td>
                    </tr>
					<tr>
						<td width="500" align="left"><A HREF="http://<%=authorizationPageBeanId.getVirtualHost()%>:8086/actuator/swagger-ui" ><img SRC="images/file.png" border="0" alt="Post Product center" >AI ChatBot REST OPEN API</A></td>
					</tr>
					<!-- 
					<tr>
						<td colspan="2">&nbsp;</td>
                    </tr>
					<tr>
						<td width="500" align="left"><A HREF="http://<%=authorizationPageBeanId.getVirtualHost()%>:8082/actuator/swagger-ui" ><img SRC="images/file.png" border="0" alt="Publish CMS content REST OPEN APIr" >Publish CMS content REST OPEN API </A></td>
					</tr>
					
					<tr>
						<td colspan="2">&nbsp;</td>
                    </tr>
					<tr>
						<td width="500" align="left"><A HREF="http://<%=authorizationPageBeanId.getVirtualHost()%>:8083/actuator/swagger-ui" ><img SRC="images/file.png" border="0" alt="Order operations for Shop REST OPEN API" >Order operations for Shop REST OPEN API</A></td>
					</tr>
					
					<tr>
						<td colspan="2">&nbsp;</td>
                    </tr>
					<tr>
						<td width="500" align="left"><A HREF="http://<%=authorizationPageBeanId.getVirtualHost()%>:8084/actuator/swagger-ui" ><img SRC="images/file.png" border="0" alt="Payment operations for Shop REST OPEN API" >Payment operations for Shop REST OPEN API</A></td>
					</tr>
					 -->
					<tr>
						<td   height="500"  colspan="2">&nbsp;</td>
					</tr>
					

				</table>
		    </div>
		  </div>
		</div>


        <!-- Navigation -->
        <div class="listingBar">
  	    <span class="next"> <a HREF = "Productlist.jsp?catalog_id=-2"  ><strong><%=authorizationPageBeanId.getLocalization(application).getString("back")%></strong></a> </span>
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