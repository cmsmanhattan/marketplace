<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<jsp:useBean id="productlistFaced" scope="application" class="com.cbsinc.cms.faceds.ProductlistFaced" />
<jsp:useBean id="calendarListBeanId" scope="session" class="com.cbsinc.cms.CalendarListBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en"
      lang="en">
<head>
    <title>GBS Portal</title>
     <style type="text/css" media="screen"> @import url(style2.css);</style>
</head>

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
                <span> Управление сайтом </span>
            </span>

        </div>

        <hr size="" class="netscape4" />

    </div>

<table class="columns">

    <tbody>
        <tr>
            <td class="left">
    <div class="box">

        <h5>Вход</h5>

        <div class="body">

            <div class="content odd">
                <form action="Authorization.jsp"
                      method="post">



                    <strong>Пользователь</strong> <br />



                     <INPUT  title="Пользователь" tabindex="10001" SIZE="12" AUTOCOMPLETE="off" TYPE="TEXT" NAME="Login" ></INPUT>

                    <br />

                    <strong>Пароль</strong>
		    <br />
		    <INPUT title="Пароль" tabindex="10002"  SIZE="12" AUTOCOMPLETE="off" TYPE="PASSWORD" NAME="Passwd1" ></INPUT>
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
                   Выслать пароль по почте
                </a>
            </div>
        </div>
    </div>


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