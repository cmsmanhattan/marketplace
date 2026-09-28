<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<html>
<head>
     <title>GBS Portal</title>
     <style type="text/css" media="screen"> @import url(style2.css);</style>
  <%
   response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
   response.setHeader("Pragma","no-cache"); //HTTP 1.0
   response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
   %>
</head>
<jsp:useBean id="catalogListBeanId" scope="session" class="com.cbsinc.cms.CatalogListBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:setProperty name="catalogListBeanId" property="*" />
<%-- explicit mapping: request params keep their original names; bean properties are camelCase --%>
<jsp:setProperty name="catalogListBeanId" property="indxSelect" param="indx_select" />
<jsp:setProperty name="catalogListBeanId" property="rowId" param="row_id" />
<%-- explicit mapping: request params keep their original names; bean properties are camelCase --%>
<jsp:setProperty name="catalogListBeanId" property="indxSelect" param="indx_select" />
<jsp:setProperty name="catalogListBeanId" property="rowId" param="row_id" />
<%
if( request.getParameter("parent_id") != null) authorizationPageBeanId.setCatalogId(request.getParameter("parent_id"));
if( request.getParameter("row") != null)
{
int index =  catalogListBeanId.stringToInt(request.getParameter("row")) ;
catalogListBeanId.setIndxSelect(index);
}
if( request.getParameter("del") != null)
{
int index =  catalogListBeanId.stringToInt(request.getParameter("del")) ;
//int g =  catalogListBeanId.rows.length ;
String catalogId = catalogListBeanId.rows[index][0] ;
if(catalogId != null)catalogListBeanId.delete(catalogId,authorizationPageBeanId) ;
request.setAttribute("del",null);
}
if( request.getParameter("offset") != null){
catalogListBeanId.setOffset(  catalogListBeanId.stringToInt(request.getParameter("offset")));
}
%>
<body>
<TABLE cellSpacing="0" cellPadding="0" width="100%"  border="1" rightmargin="0" leftmargin="0" topmargin="0" bordercolor="#ECEFF8" >
<TR>
<TD bgcolor="#ECEFF8" ></TD>
<TD vAlign="top" Align="center" width="1010">
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



<table class="columns">

    <tbody>
        <tr>
            <td class="left">
    <div class="box">

        <h5><%=authorizationPageBeanId.getLocalization(application).getString("enter_on_site")%></h5>

        <div class="body">

            <div class="content odd">
                <form action="Authorization.jsp"
                      method="post">



                    <strong><%=authorizationPageBeanId.getLocalization(application).getString("username")%></strong> <br />



                     <INPUT  title="<%= authorizationPageBeanId.getLocalization(application).getString("username") %>" tabindex="10001" SIZE="12" AUTOCOMPLETE="off" TYPE="TEXT" NAME="Login" >
        	     </INPUT>

                    <br />

                    <strong><%=authorizationPageBeanId.getLocalization(application).getString("password")%></strong>
		    <br />
		    <INPUT title="<%= authorizationPageBeanId.getLocalization(application).getString("password") %>" tabindex="10002"  SIZE="12" AUTOCOMPLETE="off" TYPE="PASSWORD" NAME="Passwd1" ></INPUT>
                    <br />
                    <br />

                    <input class="context searchButton"
                           type="submit" name="submit"
                           value="Log in" tabindex="10003" />
                </form>
            </div>

            <div class="content even">
                <a href="">
                   <img src="xsl/www.gvidons.com/img/user.gif" alt="Link icon" title="Link icon" height="15" width="10" border="0" />
                   <%=authorizationPageBeanId.getLocalization(application).getString("send_password_by_email")%>
                </a>
            </div>
        </div>
    </div>


<div>
    <div class="portlet">
    <h5><strong><%=authorizationPageBeanId.getLocalization(application).getString("help")%></strong></h5>
      <div class="body">
        <div class="portletContent odd">
         <%=authorizationPageBeanId.getLocalization(application).getString("help_cataloglist_1")%>
        </div>
        <div class="portletContent even">
	<%=authorizationPageBeanId.getLocalization(application).getString("help_cataloglist_2")%>
        </div>
      </div>
    </div>
</div>






            </td>

            <td class="main">

            <!-- News part -->

	    <h1>Список разделов</h1>
		<br/><%=catalogListBeanId.getCatalogPath(authorizationPageBeanId)%>


		<div class="box">
		  <div class="body">
		    <div >
                       <%=catalogListBeanId.getTable("" + authorizationPageBeanId.getSiteId(),authorizationPageBeanId,application)%>
		     </div>
		  </div>
		</div>


        <!-- Navigation -->
        <div class="listingBar">
	    <span class="next">
                <a HREF = "#" onClick="javascript:history.back()"  >
		<strong>
		назад
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