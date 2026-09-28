<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<jsp:useBean id="publisherBeanId" scope="session" class="com.cbsinc.cms.PublisherBean" />
<jsp:useBean id="policyBeanId" scope="request" class="com.cbsinc.cms.ItemDescriptionBean" />
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

function select_file(){
self.name  = 'UploadForm' ;
var url = 'SelectFile.jsp' ;
window.open(url ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100');
return false ;
//return true ;
}

function new_file(){
self.name  = 'UploadForm' ;
var url = 'File1.html' ;
window.open(url ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100');
return false ;
//return true ;
}

function select_Image(){
self.name  = 'UploadForm' ;
var url = 'SelectImage.jsp' ;
window.open(url ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100');
return false ;
}

function select_BigImage(){
self.name  = 'UploadForm' ;
var url = 'SelectBigImage.jsp' ;
window.open(url ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100');
return false ;
}


function new_Image(){
self.name  = 'UploadForm' ;
var url = 'Image.html' ;
window.open(url ,'New','width=400,height=210,scrollbars=yes,screenX=100,screenY=100');
return false ;
}


function new_BigImage(){
self.name  = 'UploadForm' ;
var url = 'big_images.html' ;
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
if(isEmpty( document.postsoftform.softname.value)){ alert('Field name did not fill'); return false; }
//if(isEmpty( document.postsoftform.bigimagename.value)){ alert('Field image did not fill'); return false; }
if(isEmpty( document.postsoftform.description.value)){ alert('Field description did not fill'); return false; }
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

function do_save() 
{ 
document.postsoftform.save.value = 'true' ;
document.postsoftform.submit();
return true ;
}
// start dialog widows
var current_header_ic = true;	

	function dwindow(url)
	{
	    document.onmousemove = null ;
	    current_header_ic = !current_header_ic;
	    document.getElementById("dialog").src = url ;
	    document.getElementById("vunet").style.display = current_header_ic ? "none" : "";

	}

function setTitle(message) 
{
    var mdiv = document.getElementById("dtitle");
    mdiv.innerHTML =  message ;
}

var ie = document.all;
var ns6 = document.getElementById && !document.all;
var dragapproved=false;
var z, x, y;
function move(e) {
  if (dragapproved) {
    z.style.left=ns6? temp1+e.clientX-x: temp1+event.clientX-x;
    z.style.top=ns6? temp2+e.clientY-y : temp2+event.clientY-y;
    return false;
  }
}
function drags(e) {
  if (!ie&&!ns6)
  return;
  var firedobj = ns6? e.target : event.srcElement;
  var topelement = ns6? "HTML" : "BODY";
  while (firedobj.tagName != topelement&&firedobj.className != "drag") {
    firedobj = ns6? firedobj.parentNode : firedobj.parentElement;
  }
  if (firedobj.className == "drag") {
    dragapproved = true;
    z = firedobj;
    temp1 = parseInt(z.style.left+0);
    temp2 = parseInt(z.style.top+0);
    x = ns6? e.clientX: event.clientX;
    y = ns6? e.clientY: event.clientY;
    document.onmousemove=move;
    return false;
  }
}
document.onmousedown=drags;
document.onmouseup=new Function("dragapproved=false");

// end dialog widows

//-->
</script>


</head>
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
    
	<div>
    	<div class="portlet">
	    <h5><strong><%=authorizationPageBeanId.getLocalization(application).getString("help")%></strong></h5>
	      <div class="body">
	        <div class="portletContent odd">
	         <%=authorizationPageBeanId.getLocalization(application).getString("help_ext2_productpost_1")%>
	        </div>
	        <!-- 
	        <div class="portletContent even">
	         <%= authorizationPageBeanId.getLocalization(application).getString("help_ext2_productpost_2") %>
	        </div>
	         -->
    	  </div>
	    </div>
	</div>






            </td>

            <td class="main">

            <!-- News part -->

	     <h1><%=authorizationPageBeanId.getLocalization(application).getString("title_info_modul")%></h1>
		<br/>
		<div class="box">
		  <div class="body">
		    <div >


                     <form method="post" name="postsoftform"  ACTION="Ext2ProductPost.jsp" onSubmit="return  IsFormOk()"  >
					<DIV style="background-image:url('images/f.jpg');height:20px; TEXT-ALIGN: left"  >
					<font color='white' size='2' > &nbsp;&raquo; <%=authorizationPageBeanId.getLocalization(application).getString("title_form_post_full_info")%>  </font>
					</DIV>
					 <TABLE>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("title_form")%>:* </TD><TD> <input  name="softname" size="20"  value="<%= publisherBeanId.getStrSoftName() %>" onBlur="checkEmpty(this.value)" >
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("cost")%>:* </TD> <TD><input  name="softcost" size="20" value="<%= publisherBeanId.getStrSoftCost() %>" onBlur="checkNumber(this.value)"  ><%=publisherBeanId.getComboBox("currency_id", "3" ,"SELECT currency_id , currency_lable FROM currency  WHERE active = true")%></TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("short_info")%> :* </TD> <TD> <textarea name="description" rows="10" cols="70"  ><%=publisherBeanId.getStrSoftDescription()%></textarea></TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("upload_big_image")%>:* </TD> <TD><input  name="bigimagename" disabled="disabled" size="20" value="<%= publisherBeanId.getBigimgname() %>" ><input type="button" name="newbig_image" value="<%= authorizationPageBeanId.getLocalization(application).getString("new_big_image") %>"  onclick="dwindow('NewBigImage.jsp'); return false;" ><input type="button" name="selectbig_image" value="<%= authorizationPageBeanId.getLocalization(application).getString("select_big_image") %>" image" onclick="dwindow('SelectBigImage.jsp'); return false;" ><input type="hidden"  name="bigimage_id" size="20" value="<%= publisherBeanId.getBigimageId() %>" ></TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("full_information")%> :* </TD> <TD> <textarea name="fulldescription" rows="10" cols="70"  ><%=publisherBeanId.getProductFulldescription()%></textarea></TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("attach_file")%>:* </TD> <TD><input  name="filename" disabled="disabled"  size="20" value="<%= publisherBeanId.getFilename() %>" ><input type="button" name="newfile" value="<%= authorizationPageBeanId.getLocalization(application).getString("new_file") %>" onclick="dwindow('NewFile.jsp')" ><input type="button" name="selectfile" value="<%= authorizationPageBeanId.getLocalization(application).getString("select_file") %>" onclick="dwindow('SelectFile.jsp')" ><input type="hidden"  name="file_id" size="20" value="<%= publisherBeanId.getFileId() %>" ></TD></TR>
                     <TR><TD  colspan="2" ><%=authorizationPageBeanId.getLocalization(application).getString("approve")%>:*<%=publisherBeanId.getComboBox("type_id", publisherBeanId.getTypeId() ,"select type_id , type_lable from typesoft   where  active = true order by type_id asc" )%></TD></TR>
                     <TR><TD></TD> <TD><input type="submit" name="Submit" value="<%= authorizationPageBeanId.getLocalization(application).getString("save") %>"> <input type="reset" value="<%= authorizationPageBeanId.getLocalization(application).getString("clear") %>"></TD></TR>
                     </TABLE>

					 <input type="hidden"  name="save"  value="false"/>
                     <input type="hidden"  name="portlettype_id"  value="2"/>
                     <input type="hidden"  name="parent_id"  value="<%= policyBeanId.getProductId() %>"/>
                     </form>

		     </div>
		  </div>
		</div>

       <!-- Navigation -->
        <div class="listingBar">
	    <span class="next">
                <a HREF = "ProductInfo.jsp"  >
		<strong>
			<%=authorizationPageBeanId.getLocalization(application).getString("back")%>
		</strong>
	        </a>
	    </span>
		</div>
		
		<TABLE border="0" height="200" >
		<TR><TD></TD></TR>
        </TABLE>

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

<DIV id="vunet"  class="drag" style="position: absolute; top: 120px; left: 130px;display:none;"  >
	<DIV style="background-image:url('images/f.jpg');height:20px; TEXT-ALIGN: left" id="dtitle"  >
	<A	onclick="dwindow('SelectImage.jsp')" href="#" >
	 <IMG id=upshrink_ic  title="<%= authorizationPageBeanId.getLocalization(application).getString("close_window") %>" src="images/expand.gif" align="right"/> 
	</A>
	<font  color='white' size='2' > <b id="title_name" >&nbsp;&raquo;&nbsp; GBS Portal</b> </font>
	</DIV>
	
	<TABLE cellSpacing="0" cellPadding="0" width="100%" border="1">
		<TBODY>
			<TR>
				<td>
				<iframe id="dialog" src="SelectImage.jsp" width="390" height="400" align="center">
				<%=authorizationPageBeanId.getLocalization(application).getString("browser_not_support_frame")%>
				</iframe>
				</td>
			</TR>
		</TBODY>
	</TABLE>
</DIV>

</body>
</html>