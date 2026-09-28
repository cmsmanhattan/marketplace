<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<jsp:useBean id="publisherBeanId" scope="session" class="com.cbsinc.cms.PublisherBean" />
<jsp:useBean id="catalogListBeanId" scope="session" class="com.cbsinc.cms.CatalogListBean" />
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


var windowsearch_hide = false ;

function switchWindowSearch()
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


var window_input_form_hide = true ;

function switchWindowInputForm()
{
				window_input_form_hide = !window_input_form_hide;
				document.getElementById("input_form").src =  (window_input_form_hide ? "images/expand.gif" : "images/collapse.gif");
				document.getElementById("input_form").title =  (window_input_form_hide ? "<%= authorizationPageBeanId.getLocalization(application).getString("Expand") %>" : "<%= authorizationPageBeanId.getLocalization(application).getString("Collapse") %>");
				document.getElementById("window_input_form").style.display = window_input_form_hide ? "none" : "";
				if(!windowsearch_hide)  switchWindowSearch();
}


var window_forum_hide = true ;

function switchWindowSwitchForum()
{
				window_forum_hide = !window_forum_hide;
				document.getElementById("switch_forum").src =  (window_forum_hide ? "images/expand.gif" : "images/collapse.gif");
				document.getElementById("switch_forum").title =  (window_forum_hide ? "<%= authorizationPageBeanId.getLocalization(application).getString("Expand") %>" : "<%= authorizationPageBeanId.getLocalization(application).getString("Collapse") %>");
				document.getElementById("window_switch_forum").style.display = window_forum_hide ? "none" : "";
				if(!window_input_form_hide)switchWindowInputForm();
}

var window_gencode_hide = true ;

function switchWindowGenCode()
{
				window_gencode_hide = !window_gencode_hide;
				document.getElementById("gencode").src =  (window_gencode_hide ? "images/expand.gif" : "images/collapse.gif");
				document.getElementById("gencode").title =  (window_gencode_hide ? "<%= authorizationPageBeanId.getLocalization(application).getString("Expand") %>" : "<%= authorizationPageBeanId.getLocalization(application).getString("Collapse") %>");
				document.getElementById("window_gencode").style.display = window_gencode_hide ? "none" : "";
				if(!window_forum_hide)switchWindowSwitchForum();
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
document.postsoftform.save.value = 'true' ;
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
if(isNumber(tmp) ){ alert('This is not number'); return false; }
return true ;
}

function checkEmpty(tmp){
if(isEmpty(tmp) ){ alert('Filed is empty'); return false; }
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

           
           var url = 'postcreteria?' + creteria_id  + '=' + creteria_value  ; 

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
	// createri 1
       var creteria1 = doc.getElementsByTagName("creteria1")[0];
       if(document.all["creteria1_id"] != null  && creteria1 != null )
       {

         var combobox = document.all["creteria1_id"].ownerDocument; 
         if (!combobox) combobox = document.all["creteria1_id"].document;
         document.all["creteria1_id"].options.length = 0 ;


         for (loop = 0; loop < creteria1.childNodes.length; loop++) 
		 {
           var creteria1_item = creteria1.childNodes[loop];
           var selected = creteria1_item.getElementsByTagName("selected")[0];
           var code = creteria1_item.getElementsByTagName("code")[0];
           var item  = creteria1_item.getElementsByTagName("item")[0];
		   var opt = combobox.createElement('OPTION');
	 	   opt.value = code.childNodes[0].nodeValue;
	 	   opt.text = item.childNodes[0].nodeValue;
		   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue ) opt.selected = true;
		   document.all["creteria1_id"].options.add(opt, loop);
         }
	}	

 	   var creteria_lable_1 = doc.getElementsByTagName("creteria_lable_1")[0];
       if(document.all["creteria_lable_1"] != null  && creteria_lable_1 != null )
       {

         var td = document.all["creteria_lable_1"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_1"].document;
         for (loop = 0; loop < creteria_lable_1.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_1.childNodes[loop];
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_1"].innerText = item.childNodes[0].nodeValue;
         }
	   }	

	// createri 2
       var creteria2 = doc.getElementsByTagName("creteria2")[0];
       if(document.all["creteria2_id"] != null && creteria2 != null )
       {

          var combobox2 = document.all["creteria2_id"].ownerDocument; 
          if (!combobox2) combobox2 = document.all["creteria2_id"].document;
          document.all["creteria2_id"].options.length = 0 ;

          for (loop = 0; loop < creteria2.childNodes.length; loop++) 
		  {
           var creteria2_item = creteria2.childNodes[loop];
           var selected = creteria2_item.getElementsByTagName("selected")[0];
           var code = creteria2_item.getElementsByTagName("code")[0];
           var item  = creteria2_item.getElementsByTagName("item")[0];

		   var opt = combobox2.createElement('OPTION');
	 	   opt.value = code.childNodes[0].nodeValue;
	 	   opt.text = item.childNodes[0].nodeValue;
		   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;
		   document.all["creteria2_id"].options.add(opt, loop);
          }
	}	

		var creteria_lable_2 = doc.getElementsByTagName("creteria_lable_2")[0];
       if(document.all["creteria_lable_2"] != null  && creteria_lable_2 != null )
       {

         var td = document.all["creteria_lable_2"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_2"].document;
         for (loop = 0; loop < creteria_lable_2.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_2.childNodes[loop];
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_2"].innerText = item.childNodes[0].nodeValue;
         }
	   }	


	// createri 3
       var creteria3 = doc.getElementsByTagName("creteria3")[0];
       if(document.all["creteria3_id"] != null && creteria3 != null )
       {

          var combobox2 = document.all["creteria3_id"].ownerDocument; 
          if (!combobox2) combobox2 = document.all["creteria3_id"].document;
          document.all["creteria3_id"].options.length = 0 ;


          for (loop = 0; loop < creteria3.childNodes.length; loop++) 
		  {
           var creteria3_item = creteria3.childNodes[loop];
           var selected = creteria3_item.getElementsByTagName("selected")[0];
           var code = creteria3_item.getElementsByTagName("code")[0];
           var item  = creteria3_item.getElementsByTagName("item")[0];
		   var opt = combobox2.createElement('OPTION');
	 	   opt.value = code.childNodes[0].nodeValue;
	 	   opt.text = item.childNodes[0].nodeValue;
		   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;
		   document.all["creteria3_id"].options.add(opt, loop);
          }
	}	

	   var creteria_lable_3 = doc.getElementsByTagName("creteria_lable_3")[0];
       if(document.all["creteria_lable_3"] != null  && creteria_lable_3 != null )
       {

         var td = document.all["creteria_lable_3"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_3"].document;
         for (loop = 0; loop < creteria_lable_3.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_3.childNodes[loop];
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_3"].innerText = item.childNodes[0].nodeValue;
         }
	   }	

	// createri 4
       var creteria4 = doc.getElementsByTagName("creteria4")[0];
       if(document.all["creteria4_id"] != null && creteria4 != null )
       {

          var combobox2 = document.all["creteria4_id"].ownerDocument; 
          if (!combobox2) combobox2 = document.all["creteria4_id"].document;
          document.all["creteria4_id"].options.length = 0 ;


          for (loop = 0; loop < creteria4.childNodes.length; loop++) 
		  {
           var creteria4_item = creteria4.childNodes[loop];
           var selected = creteria4_item.getElementsByTagName("selected")[0];
           var code = creteria4_item.getElementsByTagName("code")[0];
           var item  = creteria4_item.getElementsByTagName("item")[0];
		   var opt = combobox2.createElement('OPTION');
	 	   opt.value = code.childNodes[0].nodeValue;
	 	   opt.text = item.childNodes[0].nodeValue;
		   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;
		   document.all["creteria4_id"].options.add(opt, loop);
          }
	}	

	   var creteria_lable_4 = doc.getElementsByTagName("creteria_lable_4")[0];
       if(document.all["creteria_lable_4"] != null  && creteria_lable_4 != null )
       {

         var td = document.all["creteria_lable_4"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_4"].document;
         for (loop = 0; loop < creteria_lable_4.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_4.childNodes[loop];
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_4"].innerText = item.childNodes[0].nodeValue;
         }
	   }

	// createri 5
       var creteria5 = doc.getElementsByTagName("creteria5")[0];
       if(document.all["creteria5_id"] != null && creteria5 != null )
       {

          var combobox2 = document.all["creteria5_id"].ownerDocument; 
          if (!combobox2) combobox2 = document.all["creteria5_id"].document;
          document.all["creteria5_id"].options.length = 0 ;


          for (loop = 0; loop < creteria5.childNodes.length; loop++) 
		  {
           var creteria5_item = creteria5.childNodes[loop];
           var selected = creteria5_item.getElementsByTagName("selected")[0];
           var code = creteria5_item.getElementsByTagName("code")[0];
           var item  = creteria5_item.getElementsByTagName("item")[0];
		   var opt = combobox2.createElement('OPTION');
	 	   opt.value = code.childNodes[0].nodeValue;
	 	   opt.text = item.childNodes[0].nodeValue;
		   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;
		   document.all["creteria5_id"].options.add(opt, loop);

          }
	}	

		var creteria_lable_5 = doc.getElementsByTagName("creteria_lable_5")[0];
       if(document.all["creteria_lable_5"] != null  && creteria_lable_5 != null )
       {

         var td = document.all["creteria_lable_5"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_5"].document;
         for (loop = 0; loop < creteria_lable_5.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_5.childNodes[loop];
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_5"].innerText = item.childNodes[0].nodeValue;
         }
	   }


	// createri 6
       var creteria6 = doc.getElementsByTagName("creteria6")[0];
       if(document.all["creteria6_id"] != null && creteria6 != null )
       {

          var combobox2 = document.all["creteria6_id"].ownerDocument; 
          if (!combobox2) combobox2 = document.all["creteria6_id"].document;
          document.all["creteria6_id"].options.length = 0 ;


          for (loop = 0; loop < creteria6.childNodes.length; loop++) 
		  {
           var creteria6_item = creteria6.childNodes[loop];
           var selected = creteria6_item.getElementsByTagName("selected")[0];
           var code = creteria6_item.getElementsByTagName("code")[0];
           var item  = creteria6_item.getElementsByTagName("item")[0];
		   var opt = combobox2.createElement('OPTION');
	 	   opt.value = code.childNodes[0].nodeValue;
	 	   opt.text = item.childNodes[0].nodeValue;
		   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;
		   document.all["creteria6_id"].options.add(opt, loop);
          }
	}	

		var creteria_lable_6 = doc.getElementsByTagName("creteria_lable_6")[0];
       if(document.all["creteria_lable_6"] != null  && creteria_lable_6 != null )
       {

         var td = document.all["creteria_lable_6"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_6"].document;
         for (loop = 0; loop < creteria_lable_6.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_6.childNodes[loop];
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_6"].innerText = item.childNodes[0].nodeValue;
         }
	   }


	// createri 7
       var creteria7 = doc.getElementsByTagName("creteria7")[0];
       if(document.all["creteria7_id"] != null && creteria7 != null )
       {

          var combobox2 = document.all["creteria7_id"].ownerDocument; 
          if (!combobox2) combobox2 = document.all["creteria7_id"].document;
          document.all["creteria7_id"].options.length = 0 ;


          for (loop = 0; loop < creteria7.childNodes.length; loop++) 
		  {
           var creteria7_item = creteria7.childNodes[loop];
           var selected = creteria7_item.getElementsByTagName("selected")[0];
           var code = creteria7_item.getElementsByTagName("code")[0];
           var item  = creteria7_item.getElementsByTagName("item")[0];
		   var opt = combobox2.createElement('OPTION');
	 	   opt.value = code.childNodes[0].nodeValue;
	 	   opt.text = item.childNodes[0].nodeValue;
		   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;
		   document.all["creteria7_id"].options.add(opt, loop);
          }
	}	

		var creteria_lable_7 = doc.getElementsByTagName("creteria_lable_7")[0];
       if(document.all["creteria_lable_7"] != null  && creteria_lable_7 != null )
       {

         var td = document.all["creteria_lable_7"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_7"].document;
         for (loop = 0; loop < creteria_lable_7.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_7.childNodes[loop];
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_7"].innerText = item.childNodes[0].nodeValue;
         }
	   }

	// createri 8
       var creteria8 = doc.getElementsByTagName("creteria8")[0];
       if(document.all["creteria8_id"] != null && creteria8 != null )
       {

          var combobox2 = document.all["creteria8_id"].ownerDocument; 
          if (!combobox2) combobox2 = document.all["creteria8_id"].document;
          document.all["creteria8_id"].options.length = 0 ;


          for (loop = 0; loop < creteria8.childNodes.length; loop++) 
		  {
           var creteria8_item = creteria8.childNodes[loop];
           var selected = creteria8_item.getElementsByTagName("selected")[0];
           var code = creteria8_item.getElementsByTagName("code")[0];
           var item  = creteria8_item.getElementsByTagName("item")[0];

		   var opt = combobox2.createElement('OPTION');
 		   opt.value = code.childNodes[0].nodeValue;
 	   	   opt.text = item.childNodes[0].nodeValue;
		   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;
		   document.all["creteria8_id"].options.add(opt, loop);

          }
	}	
	   
	   var creteria_lable_8 = doc.getElementsByTagName("creteria_lable_8")[0];
       if(document.all["creteria_lable_8"] != null  && creteria_lable_8 != null )
       {

         var td = document.all["creteria_lable_8"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_8"].document;
         for (loop = 0; loop < creteria_lable_8.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_8.childNodes[loop];
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_8"].innerText = item.childNodes[0].nodeValue;
         }
	   }

	// createri 9
       var creteria9 = doc.getElementsByTagName("creteria9")[0];
       if(document.all["creteria9_id"] != null && creteria9 != null )
       {

          var combobox2 = document.all["creteria9_id"].ownerDocument; 
          if (!combobox2) combobox2 = document.all["creteria9_id"].document;
          document.all["creteria9_id"].options.length = 0 ;


          for (loop = 0; loop < creteria9.childNodes.length; loop++) 
	  {
           var creteria9_item = creteria9.childNodes[loop];
           var selected = creteria9_item.getElementsByTagName("selected")[0];
           var code = creteria9_item.getElementsByTagName("code")[0];
           var item  = creteria9_item.getElementsByTagName("item")[0];

		   var opt = combobox2.createElement('OPTION');
 		   opt.value = code.childNodes[0].nodeValue;
 	   	   opt.text = item.childNodes[0].nodeValue;
		   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;

		   document.all["creteria9_id"].options.add(opt, loop);

          }
	}	

		var creteria_lable_9 = doc.getElementsByTagName("creteria_lable_9")[0];
       if(document.all["creteria_lable_9"] != null  && creteria_lable_9 != null )
       {

         var td = document.all["creteria_lable_9"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_9"].document;
         for (loop = 0; loop < creteria_lable_9.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_9.childNodes[loop];
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_9"].innerText = item.childNodes[0].nodeValue;
         }
	   }


	// createri 10
       var creteria10 = doc.getElementsByTagName("creteria10")[0];
       if(document.all["creteria10_id"] != null && creteria10 != null )
       {

          var combobox2 = document.all["creteria10_id"].ownerDocument; 
          if (!combobox2) combobox2 = document.all["creteria10_id"].document;
          document.all["creteria10_id"].options.length = 0 ;

          for (loop = 0; loop < creteria10.childNodes.length; loop++) 
	 	   {
           var creteria10_item = creteria10.childNodes[loop];
           var selected = creteria10_item.getElementsByTagName("selected")[0];
           var code = creteria10_item.getElementsByTagName("code")[0];
           var item  = creteria10_item.getElementsByTagName("item")[0];

	       var opt = combobox2.createElement('OPTION');
           opt.value = code.childNodes[0].nodeValue;
   	       opt.text = item.childNodes[0].nodeValue;
	       if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;
		   document.all["creteria10_id"].options.add(opt, loop);

          }
	}	

	   var creteria_lable_10 = doc.getElementsByTagName("creteria_lable_10")[0];
       if(document.all["creteria_lable_10"] != null  && creteria_lable_10 != null )
       {
         var td = document.all["creteria_lable_10"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_10"].document;
         for (loop = 0; loop < creteria_lable_10.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_10.childNodes[loop];
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_10"].innerText = item.childNodes[0].nodeValue;
         }
	   }

}
//-->
</script>
</head>
<body>


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
        
       	<%
               	if(!authorizationPageBeanId.getStrMessage().equals("")){
               	%>
       	<SCRIPT LANGUAGE="javascript">
         alert('<%= authorizationPageBeanId.getStrMessage() %>');
		</SCRIPT>
      	<%
      	} authorizationPageBeanId.setStrMessage("") ;
      	%>
        

    </div>



<table class="columns">

    <tbody>
        <tr>
            <td class="left">
   
            </td>

            <td class="main">

            <!-- News part -->

	    <h1><%=authorizationPageBeanId.getLocalization(application).getString("music_form_for_upload")%> </h1>

		
		<br/>
	    <DIV style="background-image:url('images/f.jpg');height:20px; TEXT-ALIGN: left"  >
				<font color='white' size='2' > &nbsp;&raquo; <%=authorizationPageBeanId.getLocalization(application).getString("music_from_your_info_will_save_in_current_section")%>  </font>
		</DIV>
		<div class='box'>
		<div class='body'>
		<div>&nbsp; <%=authorizationPageBeanId.getLocalization(application).getString("pre_status_description")%> &raquo; 
		<%=catalogListBeanId.getCatalogUrlPathMain(authorizationPageBeanId)%>
		</div>
		</div>
		</div>
		<br/>
		<h1><%=authorizationPageBeanId.getLocalization(application).getString("music_form_describe_extend_info_for_music")%></h1>
		<br/>
        <form method="post" name="postsoftform"  ACTION="ProductUserPostMusic.jsp" onSubmit="return  IsFormOk()"  >
		<br/>
		<br/>
		<div class="box">
		  <div class="body">
		    <div >
		    <DIV style="background-image:url('images/f.jpg');height:20px; TEXT-ALIGN: left"  >
				<font color='white' size='2' > &nbsp;&raquo; <%=authorizationPageBeanId.getLocalization(application).getString("music_form_describe_extend_field_for_music_info")%> </font>
			</DIV>
                     <TABLE>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("title_form")%>:* </TD><TD> <input  name="softname" size="20"  value="<%= publisherBeanId.getStrSoftName() %>" onBlur="checkEmpty(this.value)" >
                     <%=authorizationPageBeanId.getLocalization(application).getString("selected_section")%>:*<%=publisherBeanId.getComboBoxAutoSubmit("catalog_id", authorizationPageBeanId.getCatalogId() ,"select catalog_id , lable   from catalog   where  active = true and parent_id = " + authorizationPageBeanId.getCatalogParentId() )%>
					 <input type="hidden"  name="softname2" size="40"  value="<%= publisherBeanId.getStrSoftName2() %>"  >
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("upload_small_image")%>:* </TD> <TD><input  name="imagename"  disabled="disabled" size="20" value="<%= publisherBeanId.getImgname() %>" ><input type="button" name="newimage" value="<%= authorizationPageBeanId.getLocalization(application).getString("new_small_image") %>"   onclick="dwindow('NewSmallImage.jsp'); return false;"  ><input type="button" name="selectimage" value="<%= authorizationPageBeanId.getLocalization(application).getString("select_small_image") %>" onclick="dwindow('SelectImage.jsp'); return false;" ><input type="hidden"  name="image_id" size="20" value="<%= publisherBeanId.getImageId() %>" ></TD></TR>
                     <!-- <TR><TD>Цена:* </TD> <TD><input  name="softcost" size="20" value="0" onBlur="checkNumber(this.value)"  ></TD></TR>  -->
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("short_info")%> :* </TD> <TD> <textarea name="description" rows="10" cols="70"  ><%=publisherBeanId.getStrSoftDescription()%></textarea></TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("upload_audio_clip")%>:* </TD> <TD><input  name="bigimagename" disabled="disabled" size="20" value="<%= publisherBeanId.getBigimgname() %>" ><input type="button" name="newbig_image" value="<%= authorizationPageBeanId.getLocalization(application).getString("upload_audio_clip") %>"  onclick="dwindow('NewAudioClip.jsp'); return false;" ><input type="button" name="selectbig_image" value="<%= authorizationPageBeanId.getLocalization(application).getString("select_uploaded_music") %>" image" onclick="dwindow('SelectBigImage.jsp'); return false;" ><input type="hidden"  name="bigimage_id" size="20" value="<%= publisherBeanId.getBigimageId() %>" ></TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("full_information")%>:* </TD> <TD> <textarea name="fulldescription" rows="10" cols="70"  ><%=publisherBeanId.getProductFulldescription()%></textarea></TD></TR>
                      <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("attache_atbom")%>:* </TD> <TD><input  name="filename" disabled="disabled"  size="20" value="<%= publisherBeanId.getFilename() %>" ><input type="button" name="newfile" value="<%= authorizationPageBeanId.getLocalization(application).getString("new_file") %>" onclick="dwindow('NewFileMusicArchive.jsp')" ><input type="button" name="selectfile" value="<%= authorizationPageBeanId.getLocalization(application).getString("select_file") %>" onclick="dwindow('SelectFile.jsp')" ><input type="hidden"  name="file_id" size="20" value="<%= publisherBeanId.getFileId() %>" ></TD></TR>

                     <TR><TD></TD> <TD><input  type="checkbox" name="show_rating1"  value="<%= publisherBeanId.getStrShowRatimg1() %>"    <%= publisherBeanId.getStrShowRatimg1Checked() %> > <%=authorizationPageBeanId.getLocalization(application).getString("open_rate")%></TD></TR>
                     <TR><TD></TD> <TD><input  type="checkbox" name="show_blog"  value="<%= publisherBeanId.getStrShowForum() %>"  <%= publisherBeanId.getShowForumChecked() %> > <%=authorizationPageBeanId.getLocalization(application).getString("open_blog")%></TD></TR>
                     <TR><TD colspan="2" ><%=authorizationPageBeanId.getLocalization(application).getString("before_input_generator_code")%> </TD></TR>
                     <TR><TD><img alt="<%= authorizationPageBeanId.getLocalization(application).getString("image_with_gen_numer") %>" src="gennumberservlet"  /></TD> <TD><input type="text" name="gen_number"></TD></TR>
                     <TR><TD colspan="2" ></TD></TR>
                     <TR><TD></TD> <TD><input type="button" name="button" value="<%= authorizationPageBeanId.getLocalization(application).getString("save") %>"  onclick="do_save()" > <input type="reset" value="<%= authorizationPageBeanId.getLocalization(application).getString("clear") %>"></TD></TR>
                     </TABLE>
                     <input type="hidden"  name="save"  value="false"/>
                     <input type="hidden"  name="portlettype_id"  value="0"/>
		     </div>
		  </div>
		</div>
       </form>
		<br/>
		<br/>

        <!-- Navigation -->
        <div class="listingBar">
	    <span class="next">
                <a HREF = "Productlist.jsp?catalog_id=-2"  >
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