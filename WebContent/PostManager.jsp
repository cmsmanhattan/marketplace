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

	    <h1><%=authorizationPageBeanId.getLocalization(application).getString("setup_page")%></h1><font color="red" size="3" ><%=authorizationPageBeanId.getStrMessage()%></font>
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
						<td width="500" align="left"><A HREF="ProductPostCre.jsp?parent_id=-1" ><img SRC="images/file.png" border="0" alt="Post Product center" ><%=authorizationPageBeanId.getLocalization(application).getString("post_news_description")%> </A></td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;</td>
                    </tr>
					<tr>
						<td width="500" align="left"><A HREF="ProductPostCre.jsp?parent_id=0" ><img SRC="images/file.png" border="0" alt="Post Product center" ><%=authorizationPageBeanId.getLocalization(application).getString("post_product_description")%> </A></td>
					</tr>

					<tr>
						<td colspan="2">  &nbsp;  </td>
					</tr>



					<tr>
						<td width="500" align="left"><A HREF="RecommentedItemPost.jsp" ><img SRC="images/file.png" border="0" alt="Post left" ><%=authorizationPageBeanId.getLocalization(application).getString("post_product_left_description")%> </A></td>
					</tr>

					<tr>
						<td colspan="2">  &nbsp;  </td>
					</tr>



					<tr>
						<td width="500" align="left"><A HREF="SponsoredBySellersItemPost.jsp" ><img SRC="images/file.png" border="0" alt="Post right" ><%=authorizationPageBeanId.getLocalization(application).getString("post_product_right_description")%> </A></td>

					</tr>

<!-- 
					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>

					<tr>
						<td width="500" align="left"><A HREF="XSLControl.jsp" ><img SRC="images/file.png" border="0" alt="Новый web дизайн" > <%= authorizationPageBeanId.getLocalization(application).getString("upload_new_design") %></A></td>

					</tr>
					
					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>

					<tr>
						<td width="500" align="left"><A HREF="/uploadservletxsl" ><img SRC="images/file.png" border="0" alt=" Изменение web дизайна  " > <%= authorizationPageBeanId.getLocalization(application).getString("change_new_design") %> </A></td>

					</tr>
				
					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td width="500" align="left"><A HREF="PostProductOffer.jsp" ><img SRC="images/file.png" border="0" alt="post new offer for test" > Make offer for testing </A></td>

					</tr>
					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td width="500" align="left"><A HREF="PostProductBidAction.jsp" ><img SRC="images/file.png" border="0" alt="post new action bid for test" > Make action bid for testing </A></td>

					</tr>
					 -->	
					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td width="500" align="left"><A HREF="NewLogo.jsp" ><img SRC="images/file.png" border="0" alt=" upload site logo" > <%=authorizationPageBeanId.getLocalization(application).getString("upload_logo")%> </A></td>

					</tr>
					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td width="500" align="left"><A HREF="DeployDesign.jsp?catalog_id=-3" ><img SRC="images/file.png" border="0" alt=" Change site design  " > create private catalog or store  </A></td>

					</tr>
					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td width="500" align="left"><A HREF="SiteDesignPost.jsp?parent_id=-3" ><img SRC="images/file.png" border="0" alt="Upload new site design" >Upload new site design </A></td>
					</tr>
					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>
					
					<%-- <tr>
						<td width="500" align="left"><A HREF="http://<%=authorizationPageBeanId.getVirtualHost()%>:8761/" ><img SRC="images/file.png" border="0" alt="Microservices console" >Back-end microservices console  </A></td>
					</tr> --%>
					
					<tr>
						<td width="500" align="left"><A HREF="https://ws.cmsmanhattan.com" ><img SRC="images/file.png" border="0" alt="Microservices console" >Back-end microservices console  </A></td>
					</tr>
					
					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>

					<%--
	 				<tr>
						<td width="500" align="left"><A HREF="http://<%=authorizationPageBeanId.getVirtualHost()%>:8098/" ><img SRC="images/file.png" border="0" alt="Web mail administrator" >Web mail administrator </A></td>
					</tr>
					
					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>
					
					<tr>
						<td width="500" align="left"><A HREF="http://<%=authorizationPageBeanId.getVirtualHost()%>:8097/" ><img SRC="images/file.png" border="0" alt="Web mail" >Web mail</A></td>
					</tr> 
					--%>
					
					
					<tr>
						<td width="500" align="left"><A HREF="http://madmin.cmsmanhattan.com" ><img SRC="images/file.png" border="0" alt="Web mail administrator" >Web mail administrator </A></td>
					</tr>
					
					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>
					
					<tr>
						<td width="500" align="left"><A HREF="https://mail.cmsmanhattan.com" ><img SRC="images/file.png" border="0" alt="Web mail" >Web mail</A></td>
					</tr>
					
					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>
					
					<tr>
						<td width="500" align="left"><A HREF="PayGatewayList.jsp" ><img SRC="images/file.png" border="0" alt="Payment page settings" ><%= authorizationPageBeanId.getLocalization(application).getString("payment_page_settings") %> </A></td>

					</tr>
 					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>
					<!-- 
					<tr>
						<td width="500" align="left"><A HREF="UserList.jsp" ><img SRC="images/file.png" border="0" alt="User list " ><%= authorizationPageBeanId.getLocalization(application).getString("use_list") %> </A></td>

					</tr>
 					-->
 					
 					<tr>
						<td width="500" align="left"><A HREF="SetDomain.jsp" ><img SRC="images/file.png" border="0" alt="Set up host name for your site" >Set up host name for your site </A></td>

					</tr>
 					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>
 					<tr>
						<td width="500" align="left"><A HREF="Domain.jsp" ><img SRC="images/file.png" border="0" alt="Buy new domain" >Buy new domain </A></td>

					</tr>
 					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>

					
					<tr>
						<td width="500" align="left"><A HREF="https://www.jirack.com" ><img SRC="images/file.png" border="0" alt="Machine learning (ML) " >Machine learning (ML) - build and train your AI Model </A></td>

					</tr>
 					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>
 	
					
 					<tr>
						<td width="500" align="left"><A HREF="FooterLinksListPost.jsp" ><img SRC="images/file.png" border="0" alt="Make a bottom line" >Make a bottom line</A></td>
					</tr>
					<tr>
						<td  colspan="2">&nbsp;</td>
					</tr>

 					<tr>
						<td width="500" align="left"><A HREF="CMSAPI.jsp" ><img SRC="images/file.png" border="0" alt="CMS REST API" >CMS REST API</A></td>
					</tr>
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