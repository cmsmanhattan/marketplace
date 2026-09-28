<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="creteriaListBeanId" scope="session" class="com.cbsinc.cms.CreteriaListBean" />
<jsp:useBean id="creteriaAddBeanId" scope="session" class="com.cbsinc.cms.CreteriaAddBean" />
<%
request.setCharacterEncoding("UTF-8");

if( authorizationPageBeanId.getRoleId() != 2 )
{

	authorizationPageBeanId.setStrMessage(authorizationPageBeanId.getLocalization(application).getString("post_message_notaccess_admin"));
	response.sendRedirect("Authorization.jsp" );
}

creteriaAddBeanId.setTableName(creteriaListBeanId.getTableName());
if( request.getParameter("link_id") != null) creteriaAddBeanId.setLinkId(Integer.parseInt(request.getParameter("link_id")));

if( request.getParameter("name") != null)
{
	creteriaAddBeanId.setName(  request.getParameter("name"));
	creteriaAddBeanId.setLabel(creteriaListBeanId.getTitle());
}

if(request.getMethod().toUpperCase().compareTo("POST") == 0)
{
	creteriaAddBeanId.addCatalog(authorizationPageBeanId.getSiteId());
	response.sendRedirect("Creteria.jsp");
}
%>
<html>
<head>
     <title>GBS Portal</title>
     <style type="text/css" media="screen"> @import url(style2.css);</style>
     <script language="JavaScript">
	<!--  
     function checkEmpty(tmp){
	 if(isEmpty(tmp) ){ alert('Filed ' + tmp + ' is empty'); return false; }
	 return true ;
	}
	
	function isEmpty(tmp)
	{
	if( tmp.length == 0 ) return true ;
	return false ;
	}
	
	//-->
	</script>
</head>

<body>
    <h1><%=authorizationPageBeanId.getLocalization(application).getString("add_means_of_creteria")%> </h1>
	<br/>
		<div class="box">
		  <div class="body">
		    <div >
                     <form method="post"   name="catalog_add"  ACTION="Creteria_add.jsp" >
                     <TABLE>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("creteriy")%>:* </TD><TD> <input  name="name" size="30"   onBlur="checkEmpty(this.value)" >
                     <TR><TD></TD> <TD><input type="submit" name="Submit" value="<%= authorizationPageBeanId.getLocalization(application).getString("save") %>"> <input type="reset" value="<%= authorizationPageBeanId.getLocalization(application).getString("clear") %>"></TD></TR>
                     </TABLE>
                     </form>
		     </div>
		  </div>
		</div>
		<div class="listingBar">
   	   	 <span class="next"> 
   	   	 	<a HREF = "#" onClick="javascript:history.back()"  >
			<strong>
			<%=authorizationPageBeanId.getLocalization(application).getString("back")%>
			</strong>
	        </a>
		</div>
</body>
</html>