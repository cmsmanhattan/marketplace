<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<jsp:useBean id="publisherBeanId" scope="session" class="com.cbsinc.cms.PublisherBean" />
<jsp:useBean id="catalogListBeanId" scope="session" class="com.cbsinc.cms.CatalogListBean" />
<jsp:useBean id="catalogEditBeanId" scope="session" class="com.cbsinc.cms.CatalogEditBean" />
<jsp:useBean id="catalogAddBeanId" scope="session" class="com.cbsinc.cms.CatalogAddBean" />
<jsp:useBean id="authorizationPageBeanId" scope="session" class="com.cbsinc.cms.AuthorizationPageBean" />
<jsp:useBean id="authorizationPageFaced" scope="application" class="com.cbsinc.cms.faceds.AuthorizationPageFaced" />
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


// start select row catalog in table
var select_id ;

function selected( color , id ) 
{
if(document.getElementById(select_id) != null ) document.getElementById(select_id).style.background = 'white' ;
select_id = id ; 
document.getElementById(id).style.background = color ;

var combobox = document.getElementById("catalog_id") ;

 for (loop = 0; loop < combobox.options.length; loop++) 
 {
 if(combobox.options[loop].value == select_id) 
  {
  combobox.options[loop].style.background = '#FFEFFF' ;
  combobox.options[loop].selected = true;
  }
  else combobox.options[loop].style.background = 'white' ;
 }

}


function setColor( color , id ) 
{
if( select_id != id ) document.getElementById(id).style.background = color ;
else  document.getElementById(id).style.background = '#FFEFFF' ;
}
// end select row catalog in table


// start select row creteria in table
var select_creteria_id ;

function creteriaSelected( color , id ) 
{
if(document.getElementById(select_creteria_id) != null ) document.getElementById(select_creteria_id).style.background = 'white' ;
select_creteria_id = id ; 
document.getElementById(id).style.background = color ;
}


function setCreteriaColor( color , id ) 
{
if( select_creteria_id != id ) document.getElementById(id).style.background = color ;
else  document.getElementById(id).style.background = '#FFEFFF' ;
}
// end select row creteria in table



var windowsearch_hide = true ;

function switchWindoSearch()
{
				windowsearch_hide = !windowsearch_hide;
				document.getElementById("search_cre").src =  (windowsearch_hide ? "images/expand.gif" : "images/collapse.gif");
				document.getElementById("search_cre").title =  (windowsearch_hide ? "<%= authorizationPageBeanId.getLocalization(application).getString("Expand") %>" : "<%= authorizationPageBeanId.getLocalization(application).getString("Collapse") %>");
				document.getElementById("windowsearch").style.display = windowsearch_hide ? "none" : "";
}


var window_urlreport_hide = true ;

function switchWindowReport()
{
				window_urlreport_hide = !window_urlreport_hide;
				document.getElementById("urlreport_cre").src =  (window_urlreport_hide ? "images/expand.gif" : "images/collapse.gif");
				document.getElementById("urlreport_cre").title =  (window_urlreport_hide ? "<%= authorizationPageBeanId.getLocalization(application).getString("Expand") %>" : "<%= authorizationPageBeanId.getLocalization(application).getString("Collapse") %>");
				document.getElementById("window_urlreport").style.display = window_urlreport_hide ? "none" : "";
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

function do_save() 
{ 
document.postsoftform.action.value = 'save' ;
document.postsoftform.submit();
return true ;
}

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
window.open(url ,'New','width=400,height=410,scrollbars=yes,screenX=100,screenY=100');
return false ;
}

function select_BigImage(){
self.name  = 'UploadForm' ;
var url = 'SelectBigImage.jsp' ;
window.open(url ,'New','width=400,height=410,scrollbars=yes,screenX=100,screenY=100');
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
//if(isNumber(document.postsoftform.image_id.value) ){ alert('This is not number'); return false; }
//if(isEmpty( document.postsoftform.imagename.value)){ alert('Field image did not fill'); return false; }
if(isEmpty( document.postsoftform.description.value)){ alert('Field description did not fill'); return false; }
return true ;
}

function checkNumber(tmp){
if(isNumber(tmp) ){ alert('Filed ' + tmp + ' is not number'); return false; }
return true ;
}

function checkEmpty(tmp){
if(isEmpty(tmp) ){ alert('Filed ' + tmp + ' is empty'); return false; }
return true ;
}

function checkEmpty(name, value){
if(isEmpty(value) ){ alert('Filed ' + name + ' is empty'); return false; }
return true ;
}

function saveField(name, value){
//if(isEmpty(value) ){ alert('Filed ' + name + ' is empty'); return false; }
doChangeCreteria( name , encodeURIComponent(value) ) ;
//doChangeCreteria( name , value) ;
return true ;
}

//  Ajax - chenge criteria
function initRequest() 
{
       if (window.XMLHttpRequest) 
       {
           return new XMLHttpRequest();
       }
       else if (window.ActiveXObject) 
       {
           return new ActiveXObject("Microsoft.XMLHTTP");
       }
}


function doChangeCreteria( creteria_id , creteria_value ) 
{

           
           //var url = 'postcreteria?' + creteria_id  + '=' + creteria_value  ; 
			var url = 'postcreteria?'  ;     
           var req = initRequest();
           req.onreadystatechange = function() 
	   {
               if (req.readyState == 4) 
	       {
                   if (req.status == 200) 
		   			{

		     parseMessages(req.responseXML);
                   }
               }
           };

          // req.open("GET", url, true);
          // req.send(null);

            req.open("POST", url, true);
		    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
           //alert('name= ' + creteria_id + ' value=' + creteria_value) ;
            req.send(creteria_id + '=' + creteria_value) ;
           //alert('Ok') ;

}



function doRefreshCreteria() 
{

           
           var url = 'postcreteria' ; 

           var req = initRequest();
           req.onreadystatechange = function() 
	   	   {
            if (req.readyState == 4) 
	         {
               if (req.status == 200) 
		       {
			     parseMessages(req.responseXML);
               }
             }
           };

           req.open("GET", url, true);
           req.send(null);


}


function parseMessages(responseXML) 
{
       var doc = responseXML.getElementsByTagName("document")[0];
	
}
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
		        <%=authorizationPageBeanId.getLocalization(application).getString("help_col1_productpost_1")%>
		        </div>
		        <!-- 
		        <div class="portletContent even">
		        <%= authorizationPageBeanId.getLocalization(application).getString("help_col1_productpost_2") %>
		        </div>
		         -->
		      </div>
		    </div>
		</div>

            </td>

            <td class="main">

            <!-- News part -->

	     <h1>Make a bottom line </h1>

		
		
		<br/>
	   		

        <!--  end  list catalog  -->
		<br/>

      <form method="post" name="postsoftform"  ACTION="BottomListPost.jsp" onSubmit="return  IsFormOk()"  >
		<br/>
		<div class="box">
		  <div class="body">
		    <div >
					<DIV style="background-image:url('images/f.jpg');height:20px; TEXT-ALIGN: left"  >
					<font color='white' size='2' > &nbsp;&raquo; <%=authorizationPageBeanId.getLocalization(application).getString("title_form_post_info")%> </font>
					</DIV>
                     <TABLE>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("title_form")%>:* </TD><TD> <input  name="softname" size="20"  value="<%= publisherBeanId.getStrSoftName() %>" onBlur="checkEmpty(this.name,this.value);saveField(this.name,this.value)" >
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("upload_big_image")%>:* </TD> <TD><input onChange="saveField(this.name,this.value)" name="bigimagename" disabled="disabled" size="20" value="<%= publisherBeanId.getBigimgname() %>" ><input type="button" name="newbig_image" value="<%= authorizationPageBeanId.getLocalization(application).getString("new_big_image") %>"  onclick="dwindow('NewBigImage.jsp'); return false;" ><input type="button" name="selectbig_image" value="<%= authorizationPageBeanId.getLocalization(application).getString("select_big_image") %>" image" onclick="dwindow('SelectBigImage.jsp'); return false;" ><input onChange="saveField(this.name,this.value)" type="hidden"  name="bigimage_id" size="20" value="<%= publisherBeanId.getBigimageId() %>" ></TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("full_information")%> :* </TD> <TD> <textarea name="fulldescription" rows="30" cols="70" onBlur="saveField(this.name,this.value)" ><%=publisherBeanId.getProductFulldescription()%></textarea></TD></TR>
                     <TR><TD></TD> <TD><input type="button" name="button" value="<%= authorizationPageBeanId.getLocalization(application).getString("save") %>"  onclick="do_save()" > <input type="button" name="button" value="<%= authorizationPageBeanId.getLocalization(application).getString("save_as_new") %>"  onclick="do_insert()" > <input type="reset" value="<%= authorizationPageBeanId.getLocalization(application).getString("clear") %>"></TD></TR>
                     </TABLE>
                      <input type="hidden"  name="type_id"  value="0"/>
                     <input type="hidden"  name="action"  value=""/>
                     <input type="hidden"  name="insert"  value="false"/>
                     <input type="hidden"  name="portlettype_id"  value="18"/>
		     </div>
		  </div>
		</div>
    </form>


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

<DIV id="vunet"  class="drag" style="position: absolute; top: 120px; left: 130px;display:none;"  >
	<DIV style="background-image:url('images/f.jpg');height:20px; TEXT-ALIGN: left" id="dtitle"  >
	<A	onclick="dwindow('SelectBigImage.jsp')" href="#" >
	 <IMG id=upshrink_ic  title="<%= authorizationPageBeanId.getLocalization(application).getString("close_window") %>" src="images/expand.gif" align="right"/> 
	</A>
	<font  color='white' size='2' > <b id="title_name" >&nbsp;&raquo;&nbsp; GBS Portal</b> </font>
	</DIV>
	
	<TABLE cellSpacing="0" cellPadding="0" width="100%" border="1">
		<TBODY>
			<TR>
				<td>
				<iframe id="dialog" src="SelectBigImage.jsp" width="390" height="400" align="center">
				<%=authorizationPageBeanId.getLocalization(application).getString("browser_not_support_frame")%>
				</iframe>
				</td>
			</TR>
		</TBODY>
	</TABLE>
</DIV>
</body>
</html>