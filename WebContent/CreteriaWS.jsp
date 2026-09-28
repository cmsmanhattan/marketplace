<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<jsp:useBean id="creteriaListBeanId" scope="session" class="com.cbsinc.cms.CreteriaListBean" />
<jsp:useBean id="publisherBeanId" scope="session" class="com.cbsinc.cms.PublisherBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="authorizationPageFaced" scope="application" class="com.cbsinc.cms.faceds.AuthorizationPageFaced" />
 <%
response.setCharacterEncoding("UTF-8");
response.setContentType("text/xml");

String url = "" ;
String xsltpath = "";
String xsltpathDefault = "" ;
String xsltUrl = "" ;
String xsltUrl_default = "" ;

try
{
String mobile = authorizationPageBeanId.isMobileSession()?"/mobile":""  ;
xsltUrl = request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "creteria.xsl" ; 
xsltUrl_default = request.getScheme() + "://" + request.getServerName() +  ":"+request.getServerPort() + request.getContextPath() + "/xsl/" +  authorizationPageBeanId.getSiteDir()  + mobile + "/"  + "creteria.xsl" ; 
xsltpath =  "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/"  +  authorizationPageBeanId.getLocale() + "/" + "creteria.xsl" ; 
xsltpathDefault = "xsl/" +  authorizationPageBeanId.getSiteDir() + mobile + "/" + "creteria.xsl" ; 
xsltpath = request.getServletContext().getRealPath("/" +xsltpath);
xsltpathDefault = request.getServletContext().getRealPath("/" +xsltpathDefault);

	File file = new File(xsltpath) ;
	if( file == null  || !file.exists() ) url = xsltUrl_default ;
	else url = xsltUrl ;
		 
}
 catch (Exception e) 
{
	 throw e ;
}
finally {
	System.out.println("isMobileSession: " + authorizationPageBeanId.isMobileSession());
    System.out.println("ProductPostCre.jsp xsltpath: " + xsltpath);
    System.out.println("ProductPostCre.jsp xslt url: " + url);
}

PrintWriter printWriter = response.getWriter();
String tmp ="<?xml-stylesheet type=\"text/xsl\" href=\""+url+"\"?>" ;
printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
printWriter.println(tmp);

%>
<document>
   <version>1.0</version>
   <name><%=authorizationPageBeanId.getLocalization(application).getString("change_name_of_creteria")%></name>
   <title><%=authorizationPageBeanId.getLocalization(application).getString("change_name_of_creteria")%></title>
   <virtual_host><%= authorizationPageBeanId.getVirtualHost() %></virtual_host>   
   <subject_site><%= authorizationPageBeanId.getSubjectSite() %></subject_site>
   <site_name><%=  authorizationPageBeanId.getNickSite() %></site_name>
   <host><%=  authorizationPageBeanId.getSiteDir() %></host>
   <domain><%=  authorizationPageBeanId.getHost() %></domain>
   <login><%=  authorizationPageBeanId.getStrLogin() %></login>

<%

if( request.getParameter("table_name") != null) creteriaListBeanId.setTableName(request.getParameter("table_name"));

if( request.getParameter("table_name") != null  )
{
if( request.getParameter("table_name").compareTo("creteria1") == 0 ) creteriaListBeanId.setLinkId(0);
else if( request.getParameter("table_name").compareTo("creteria2") == 0 ) creteriaListBeanId.setLinkId(Integer.parseInt(publisherBeanId.getCreteria1Id()));
else if( request.getParameter("table_name").compareTo("creteria3") == 0 ) creteriaListBeanId.setLinkId(Integer.parseInt(publisherBeanId.getCreteria2Id()));
else if( request.getParameter("table_name").compareTo("creteria4") == 0 ) creteriaListBeanId.setLinkId(Integer.parseInt(publisherBeanId.getCreteria3Id()));
else if( request.getParameter("table_name").compareTo("creteria5") == 0 ) creteriaListBeanId.setLinkId(Integer.parseInt(publisherBeanId.getCreteria4Id()));
else if( request.getParameter("table_name").compareTo("creteria6") == 0 ) creteriaListBeanId.setLinkId(Integer.parseInt(publisherBeanId.getCreteria5Id()));
else if( request.getParameter("table_name").compareTo("creteria7") == 0 ) creteriaListBeanId.setLinkId(Integer.parseInt(publisherBeanId.getCreteria6Id()));
else if( request.getParameter("table_name").compareTo("creteria8") == 0 ) creteriaListBeanId.setLinkId(Integer.parseInt(publisherBeanId.getCreteria7Id()));
else if( request.getParameter("table_name").compareTo("creteria9") == 0 ) creteriaListBeanId.setLinkId(Integer.parseInt(publisherBeanId.getCreteria8Id()));
else if( request.getParameter("table_name").compareTo("creteria10") == 0 ) creteriaListBeanId.setLinkId(Integer.parseInt(publisherBeanId.getCreteria9Id()));
}

if( request.getParameter("creteria_value") != null) creteriaListBeanId.setTitle(request.getParameter("creteria_value"), creteriaListBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true")));


if( request.getParameter("row") != null)
{
int index =  creteriaListBeanId.stringToInt(request.getParameter("row")) ;
creteriaListBeanId.setIndxSelect(index);
}
if( request.getParameter("del") != null)
{
int index =  creteriaListBeanId.stringToInt(request.getParameter("del")) ;
//int g =  creteriaListBeanId.rows.length ;
String creteriaId = creteriaListBeanId.rows[index][0] ;
  if(!creteriaId.equals("0"))
   { 
   if(creteriaId != null)creteriaListBeanId.delete(creteriaId) ;
   request.setAttribute("del",null);
   }
}
if( request.getParameter("offset") != null){
creteriaListBeanId.setOffset(  creteriaListBeanId.stringToInt(request.getParameter("offset")));
}
creteriaListBeanId.initPage(creteriaListBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true")));
%>
<body>
	   <div>
		        <form method="post"  name="creteria"  action="Creteria.jsp">
				<INPUT SIZE="40"  AUTOCOMPLETE="off" TYPE="TEXT" NAME="creteria_value" VALUE="<%= creteriaListBeanId.getTitle() %>"  ></INPUT>
				<INPUT TYPE="submit" name="submit" value="<%= authorizationPageBeanId.getLocalization(application).getString("edit") %>"></INPUT>
		        </form>
		</div>
		<h1><%=authorizationPageBeanId.getLocalization(application).getString("add_keyword")%> </h1>
		<div >
                   <%=creteriaListBeanId.getTableInXML(request.getLocale())%>
		</div>
		<div>
   	   	 	<a HREF = "Creteria.jsp?creteria_value="  ><%=authorizationPageBeanId.getLocalization(application).getString("hide")%></a>
		</div>
</body>
</document>