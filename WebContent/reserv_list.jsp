<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page errorPage="error.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en"
      lang="en">
<head>
     <title>web site list </title>
     <style type="text/css" media="screen"> @import url(style2.css);</style>
</head>
<jsp:useBean id="calendarListBeanId" scope="request" class="com.cbsinc.cms.CalendarListBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="productlistFaced" scope="application" class="com.cbsinc.cms.faceds.ProductlistFaced" />

<%
if( request.getParameter("mount_id") != null)calendarListBeanId.setMountId( request.getParameter("mount_id"));
if( request.getParameter("year_id") != null)calendarListBeanId.setYearId( request.getParameter("year_id"));
if( request.getParameter("product_id") != null)
{
  calendarListBeanId.setProductId( request.getParameter("product_id"));
  calendarListBeanId.setDescrition( productlistFaced.getProductName( request.getParameter("product_id")));
}
//ProductlistBeanId.getProduct()

calendarListBeanId.setSiteId(authorizationPageBeanId.getSiteId());
//calendarListBeanId.setIntLevelUp( authorizationPageBeanId.getIntLevelUp());
//System.out.println("===== levelup ===== : " + authorizationPageBeanId.getIntLevelUp() ) ;

if( request.getParameter("row") != null)
{
int index =  calendarListBeanId.stringToInt(request.getParameter("row")) ;
calendarListBeanId.setIndxSelect(index);
}
if( request.getParameter("del") != null)
{
int index =  calendarListBeanId.stringToInt(request.getParameter("del")) ;
int g =  calendarListBeanId.rows.length ;
String calendarId = calendarListBeanId.rows[index][0] ;
if(calendarId != null)calendarListBeanId.delete(calendarId) ;
request.setAttribute("del",null);
}
if( request.getParameter("offset") != null){
calendarListBeanId.setOffset(  calendarListBeanId.stringToInt(request.getParameter("offset")));
}



if(request.getMethod().toUpperCase().compareTo("POST") == 0)
{
 calendarListBeanId.calendarChange();
 response.sendRedirect("calendar_list.jsp" );
}

%>

<BODY>

<TABLE cellSpacing="0" cellPadding="0" width="100%"  border="1" rightmargin="0" leftmargin="0" topmargin="0" bordercolor="#ECEFF8" >
<TR>
<TD bgcolor="#ECEFF8" ></TD>
<TD vAlign="top" Align="center" width="1020">


<a class="skipnav" href="#documentContent">Skip to content</a>

<div>

     

        <div class="top">


<TABLE cellSpacing="0" cellPadding="10"  height="120"  width="100%" background="index.files/bg.jpg" border="0" rightmargin="0" leftmargin="0" topmargin="0">
  <TBODY>
  <TR>
    <TD vAlign="top" Align="left"  width="20%">
    </TD>
    <TD vAlign="center" Align="right"  width="80%">
                <form name="searchform"  action="Productlist.jsp" >
		<br />
                    <input id="search_value"  name="search_value" type="text"  size="20" alt="Поиск по имени товара"   title="Поиск по имени товара" tabindex="30001" ><xsl:attribute name="value"><xsl:value-of select="document/search_value"/></xsl:attribute></input>
                    <input class="context searchButton"  type="submit" size="20" value="Поиск"  tabindex="30002" />
                </form>

</TD>
  </TR>
  </TBODY>
</TABLE>


        </div>

        <hr size="" class="netscape4" />

        <div class="tabs">
           <a href="Productlist.jsp?catalog_id=-2" class="plain" ><font size="2"> Главная станица </font></a>
           <a href="Productlist.jsp?catalog_id=-1" class="plain" ><font size="2"> Новости </font></a>
           <A href="Authorization.jsp?Login=newuser" class="plain" > <font size="2">Регистрация покупателя </font></A>
           <a href="about.html" class="selected"> <font size="2">О компании  </font></a>
        </div>

        <div class="personalBar">

       <!--   Наш девиз - Мы это деньги а деньги это Мы -->
         программый продукт  Media@Shop           
        </div>

        <div class="pathBar">
            <span>
                <span> </span>
            </span>

        </div>

        <hr size="" class="netscape4" />

    </div>



<table class="columns">

    <tbody>
        <tr>
            <td class="left">


<div>
    <div class="box">

          <TABLE height="20" cellSpacing="5" cellPadding="0" width="159" background="index.files/bg11.gif" >
              <TBODY>
              <TR>
                <TD vAlign="center" ><FONT color="white" ><B>О проекте </B></FONT>
               </TD>
              </TR>
  	     </TBODY>
  	   </TABLE>


      <div class="body">
        <div class="even">
	<strong>1.</strong> Это много фукциональный , динамично развивающийся  продукт ,
          реализующий в себе возможности интернет магазина и  билинговой системы .  
	  Умеющий продавать мутлимедийный контент , и просто товары обозначенные картинкой . 
        </div>
        <div class="even">
<strong>2.</strong> Используемые технологии  JDBC , JSP , Servlets , XML , XSLT .
        </div>
        <div class="even">
	<strong>3.</strong> Media Shop  - работает на всеx J2EE Application Servers
         JBoss , BEA , Sun One Application Servers .
        </div>

        <div class="even">
<strong>4.</strong> Ведутся разработки по продаже мобильного контента . Новая  система с мобильным контентом ожидается  летом 2006 года . 
        </div>



      </div>
    </div>
</div>






            </td>

            <td class="main">

            <!-- News part -->

			<h1>Список брони для  <%= calendarListBeanId.getDescrition() %> </h1>
	        <br/>



		<div class="box">
		  <div class="body">
		    <div >

		    <form method="post"   name="calendar_list"  ACTION="calendar_list.jsp" >
                     <TABLE>
                     <TR><TD></TD><TD><input type="hidden" name="calendar_id"  value = "<%= calendarListBeanId.rows[calendarListBeanId.getIndxSelect()][0] %>"  />
                     <TR><TD>На месяц:* </TD><TD><TABLE> <%= calendarListBeanId.getComboBoxMount("mount_id", calendarListBeanId.getMountId()  ) %><%= calendarListBeanId.getComboBoxYear("year_id", calendarListBeanId.getYearId()  ) %> <input type="submit" name="Submit" value="Выбрать"></TABLE><TD>
                     </TABLE>
             </form>
              <%= calendarListBeanId.getTable( authorizationPageBeanId.getRoleId()) %>
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

Internet shop. Copyright 2005 <A HREF="http://www.grabko.com">Grabko Business Solution</A> . Все права защищены.

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