<%@page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="authorizationPageFaced" scope="application" class="com.cbsinc.cms.faceds.AuthorizationPageFaced" />
<html>

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
                <font size="4"><%=authorizationPageBeanId.getLocalization(application).getString("control_of_site")%></font>

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

	    <h1><%=authorizationPageBeanId.getLocalization(application).getString("use_list")%></h1>
		<br/>


		<div class="box">
		  <div class="body">
		    <div >
		    <div >
	         <%=authorizationPageFaced.getUserList(authorizationPageBeanId)%>
			 </div>
		  </div>
		</div>


        <!-- Navigation -->
        <div class="listingBar">
  	    <span class="next"> <a HREF = "Productlist.jsp?catalog_id=-2"  ><strong> <%=authorizationPageBeanId.getLocalization(application).getString("back")%></strong></a> </span>
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