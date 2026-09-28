<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<html>
<head>
     <title>GBS Portal</title>
     <style type="text/css" media="screen"> @import url(style2.css);</style>
<script language="JavaScript">
<!--

function select_Image(){
self.name  = 'UploadForm' ;
var url = 'SelectImage.jsp' ;
window.open(url ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100');
return false ;
}

function new_Image(){
self.name  = 'UploadForm' ;
var url = 'Image.html' ;
window.open(url ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100');
return false ;
}

function isNumber(tmp){
var IntField = '0123456789.' ;
for(var i=0 ; i< tmp.length ; i++ ){
if( IntField.indexOf(tmp.charAt(i)) == -1 ) return true ;
}
return false ;
}

function isEmpty(tmp){
if( tmp.length == 0 ) return true ;
return false ;
}

function isDate(tmp){
var IntField = '0123456789' ;
//1957-06-13
if( IntField.indexOf(tmp.charAt(0)) != -1 )
if( IntField.indexOf(tmp.charAt(1)) != -1 )
if( IntField.indexOf(tmp.charAt(2)) != -1 )
if( IntField.indexOf(tmp.charAt(3)) != -1 )
if( IntField.indexOf(tmp.charAt(5)) != -1 )
if( IntField.indexOf(tmp.charAt(6)) != -1 )
if( IntField.indexOf(tmp.charAt(8)) != -1 )
if( IntField.indexOf(tmp.charAt(9)) != -1 )
if( tmp.indexOf('-') == 4 )
if( tmp.indexOf('-',5) == 7 ) return false ;
return true ;
}

function isURL(tmp){
if( tmp.indexOf('http://') != -1 ) return false ;
if( tmp.indexOf('ftp://') != -1 ) return false ;
if( tmp.indexOf('mailto:') != -1 ) return false ;
return true ;
}


function IsFormOk(){
if(isEmpty( document.catalog_add.name.value)){ alert('Field name did not fill'); return false; }
return true ;
}

function checkNumber(tmp){
if(isNumber(tmp) ){ alert('This is not number'); return false; }
return true ;
}

function checkEmpty(tmp){
if(isEmpty(tmp) ){ alert('Filed is empty'); return false; }
return true ;
}

//-->
</script>

</head>
<jsp:useBean id="calendarListBeanId" scope="request" class="com.cbsinc.cms.CalendarListBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="calendarAddBeanId" scope="session" class="com.cbsinc.cms.CalendarAddBean" />
<%
request.setCharacterEncoding("UTF-8");
calendarAddBeanId.setSiteId(authorizationPageBeanId.getSiteId());
//if( request.getParameter("holddate") != null)
//{
//	calendarAddBeanId.setHolddate( request.getParameter("holddate"));
//}

if( request.getParameter("day_id") != null)calendarAddBeanId.setDayId( request.getParameter("day_id"));
if( request.getParameter("mount_id") != null)calendarAddBeanId.setMountId( request.getParameter("mount_id"));
if( request.getParameter("year_id") != null)calendarAddBeanId.setYearId( request.getParameter("year_id"));
if( request.getParameter("product_id") != null)calendarAddBeanId.setProductId( request.getParameter("product_id"));

if( request.getParameter("first_name") != null)calendarAddBeanId.setFirstName( request.getParameter("first_name"));
if( request.getParameter("last_name") != null)calendarAddBeanId.setLastName( request.getParameter("last_name"));
if( request.getParameter("father_name") != null)calendarAddBeanId.setFatherName( request.getParameter("father_name"));
if( request.getParameter("document_number") != null)calendarAddBeanId.setDocumentNumber( request.getParameter("document_number"));
if( request.getParameter("document_type") != null)calendarAddBeanId.setDocumentType( request.getParameter("document_type"));
if( request.getParameter("age") != null)calendarAddBeanId.setAge( request.getParameter("age"));
if( request.getParameter("note") != null)calendarAddBeanId.setNote( request.getParameter("note"));


if(request.getMethod().toUpperCase().compareTo("POST") == 0)
{
calendarAddBeanId.addCalendar();
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

	    <h1>Добавление в раздела</h1>
<br/>


		<div class="box">
		  <div class="body">
		    <div >

                     <form method="post"   name="calendar_add"  ACTION="calendar_add.jsp" >
                      <input type="hidden"  name="product_id" value="<%= calendarListBeanId.getProductId()%>" >
                     <TABLE>
                     <TR><TD>Дата:* </TD><TD><TABLE> <%= calendarAddBeanId.getComboBoxDay("day_id", calendarAddBeanId.getDayId()  ) %><%= calendarAddBeanId.getComboBoxMount("mount_id", calendarAddBeanId.getMountId()  ) %><%= calendarAddBeanId.getComboBoxYear("year_id", calendarAddBeanId.getYearId()  ) %> </TABLE><TD>
                     <TR><TD width="40" >Имя</TD> <TD><input type="text" name="first_name" value="<%= calendarAddBeanId.getFirstName()%>"> </TD></TR>
                     <TR><TD width="40" >Фамилия</TD> <TD><input type="text" name="last_name" value="<%= calendarAddBeanId.getLastName()%>"> </TD></TR>
                     <TR><TD width="40" >Отчество</TD> <TD><input type="text" name="father_name" value="<%= calendarAddBeanId.getFatherName()%>"> </TD></TR> 
                     <TR><TD width="40" >Номер документа</TD> <TD><input type="text" name="document_number" value="<%= calendarAddBeanId.getDocumentNumber()%>"> </TD></TR>
                     <TR><TD width="40" >Тип документа</TD> <TD><input type="text" name="document_type" value="<%= calendarAddBeanId.getDocumentType()%>"> </TD></TR>
                     <TR><TD width="40" >Возраст</TD> <TD><input type="text" name="age" value="<%= calendarAddBeanId.getAge()%>"> </TD></TR> 
                     <TR><TD width="40" >Дополнительная информация</TD> <TD><input type="text" name="note" value="<%= calendarAddBeanId.getNote()%>"> </TD></TR>
                                                                                    
                     <TR><TD></TD> <TD><input type="submit" name="Submit" value="Сохранить"> <input type="reset" value="Сброс"></TD></TR>
                     </TABLE>
                     </form>

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