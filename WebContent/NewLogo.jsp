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
		
	 
	 <script language="JavaScript">
        <!--
        function setClose()
        {
        document.location.href='PostManager.jsp';
        return true ;
        }
        //-->
	</script>
	 
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
		        <%=authorizationPageBeanId.getLocalization(application).getString("commentariy_upload_new_logotype_on_site")%>
		        </div>
		         
		        <div class="portletContent even">
		        </div>
		        
		      </div>
		    </div>
		</div>

            </td>

            <td class="main">

            <!-- News part -->

	     <h1><%=authorizationPageBeanId.getLocalization(application).getString("title_upload_new_logotype_on_site")%></h1>

		
		
		<br/>
 		 <form  name="uploadform"  action="logoservletupload" method="post" enctype="multipart/form-data">
 			 <input type="file" name="file"><br>
 			 <input type="submit" value='<%= authorizationPageBeanId.getLocalization(application).getString("save") %>' ><input type="button" value="<%= authorizationPageBeanId.getLocalization(application).getString("close_window") %>" onClick="return setClose()" >
 		 </form>
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
<TD bgcolor="#ECEFF8" ></TD>
</TR>
</TABLE>


</body>
</html>