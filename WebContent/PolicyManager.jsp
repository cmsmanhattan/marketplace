<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<%
  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
  request.setCharacterEncoding("UTF-8");
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en"
      lang="en">

<head>
     <title>GBS Portal</title>
     <style type="text/css" media="screen"> @import url(style2.css);</style>

</head>

<body>
<TABLE cellSpacing="0" cellPadding="0" width="100%"  border="1" rightmargin="0" leftmargin="0" topmargin="0" bordercolor="#ECEFF8" >
<TR>
<TD bgcolor="#ECEFF8" ></TD>
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

<!-- 
<div>
    <div class="portlet">
    <h5><strong>Помощ</strong></h5>
      <div class="body">
        <div class="portletContent odd">
         Это главное меню управления сайтом .
        </div>
        <div class="portletContent even">
		На страницу с подробным описанем можно добавлять допольнительные информационные модули ( товары ,	новости)  на саит , 
		они будут распологаться справа или слева от подробного описания основного информационного блока .
        </div>
      </div>
    </div>
</div>

 -->




            </td>

            <td class="main">

            <!-- News part -->

  	    <h1><%=authorizationPageBeanId.getLocalization(application).getString("setup_page_detail")%></h1><font color="red" size="3" ><%=authorizationPageBeanId.getStrMessage()%></font>
<br/>


		<div class="box">
		  <div class="body">
		    <div >




				<table width="670"    border="0" cellspacing="0" cellpadding="5" bgcolor="#DFE3EF">
					<tr>
						<td colspan="2">&nbsp;</td>
                      			</tr>
					<tr>
						<td width="500" align="left"><A HREF="Ext1ProductPost.jsp" ><img SRC="images/file.png" border="0" alt="Post CoProduct" ><%=authorizationPageBeanId.getLocalization(application).getString("post_product_right_description")%></A></td>
					</tr>

					<tr>
						<td colspan="2">&nbsp;</td>
                     </tr>

					<tr>
						<td width="500" align="left"><A HREF="Ext2ProductPost.jsp" ><img SRC="images/file.png" border="0" alt="Post CoProduct" ><%=authorizationPageBeanId.getLocalization(application).getString("post_product_left_description")%> </A></td>
					</tr>

					<tr>
						<td colspan="2">&nbsp;</td>
                    </tr>
					<tr>
						<td width="500" align="left"><A HREF="ProductReviewPost.jsp" ><img SRC="images/file.png" border="0" alt="Post Blog" ><%=authorizationPageBeanId.getLocalization(application).getString("leave_message_onpage")%> </A></td>
					</tr>

					<tr>
						<td colspan="2">&nbsp;</td>
                    </tr>
					<!-- 
					<tr>
						<td width="500" align="left"><A HREF="ExtFilesProductPost.jsp" ><img SRC="images/file.png" border="0" alt="Post file" ><%= authorizationPageBeanId.getLocalization(application).getString("add_file_onpage") %> </A></td>
					</tr>

					<tr>
						<td colspan="2">&nbsp;</td>
                    </tr>
					<tr>
						<td width="500" align="left"><A HREF="ExtTabsProductPost.jsp" ><img SRC="images/file.png" border="0" alt="Post tab" ><%= authorizationPageBeanId.getLocalization(application).getString("add_tab_onpage") %></A></td>
					</tr>

					<tr>
						<td colspan="2">&nbsp;</td>
                    </tr>
                    
					<tr>
						<td width="500" align="left"><A HREF="Productlist.jsp?element=ext_service_page&action=edit&product_id=<%= ((com.cbsinc.cms.AuthorizationPageBean)session.getAttribute("authorizationPageBeanId")).getLastProductId() %>" ><img SRC="images/file.png" border="0" alt="Post about company" ><%=authorizationPageBeanId.getLocalization(application).getString("change_page_about_company")%> </A></td>
					</tr>

					<tr>
						<td colspan="2">&nbsp;</td>
                    </tr>
					<tr>
						<td width="500" align="left"><A HREF="Productlist.jsp?element=ext_service_page&action=edit&product_id=<%= ((com.cbsinc.cms.AuthorizationPageBean)session.getAttribute("authorizationPageBeanId")).getLastProductId() %>" ><img SRC="images/file.png" border="0" alt="Post CoProduct" ><%=authorizationPageBeanId.getLocalization(application).getString("change_page_how_pay")%></A></td>
					</tr>
					
 					-->
					<tr>
						<td  height="550" >&nbsp;</td>
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
<TD bgcolor="#ECEFF8" ></TD>
</TR>
</TABLE>

</body>
</html>