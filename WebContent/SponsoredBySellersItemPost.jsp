<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<%@ page import="com.cbsinc.cms.controllers.CurrencyEnum" %>
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
	//doChangeCreteria( name , encodeURIComponent(value) ) ; // tranfered to new user interface
return true ;
}

//  Ajax - chenge criteria
/**
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
           if( creteria1_item.nodeName == 'creteria1-item'  ){ 
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
	}	

 	   var creteria_lable_1 = doc.getElementsByTagName("creteria_lable_1")[0];
       if(document.all["creteria_lable_1"] != null  && creteria_lable_1 != null )
       {

         var td = document.all["creteria_lable_1"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_1"].document;
         for (loop = 0; loop < creteria_lable_1.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_1.childNodes[loop];
           if( creteria_item.nodeName == 'creteria_lable_1-item'  ){ 
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_1"].innerText = item.childNodes[0].nodeValue;
		   }
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
           if( creteria2_item.nodeName == 'creteria2-item'  )
           { 
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
	}	

		var creteria_lable_2 = doc.getElementsByTagName("creteria_lable_2")[0];
       if(document.all["creteria_lable_2"] != null  && creteria_lable_2 != null )
       {

         var td = document.all["creteria_lable_2"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_2"].document;
         for (loop = 0; loop < creteria_lable_2.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_2.childNodes[loop];
           if( creteria_item.nodeName == 'creteria_lable_2-item'  ){ 
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_2"].innerText = item.childNodes[0].nodeValue;
		   }
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
            if( creteria3_item.nodeName == 'creteria3-item'  )
            {
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
	}	

	   var creteria_lable_3 = doc.getElementsByTagName("creteria_lable_3")[0];
       if(document.all["creteria_lable_3"] != null  && creteria_lable_3 != null )
       {

         var td = document.all["creteria_lable_3"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_3"].document;
         for (loop = 0; loop < creteria_lable_3.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_3.childNodes[loop];
           if( creteria_item.nodeName == 'creteria_lable_3-item'  ){ 
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_3"].innerText = item.childNodes[0].nodeValue;
		   }
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
           if( creteria4_item.nodeName == 'creteria4-item'  )
            {
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
	}	

	   var creteria_lable_4 = doc.getElementsByTagName("creteria_lable_4")[0];
       if(document.all["creteria_lable_4"] != null  && creteria_lable_4 != null )
       {

         var td = document.all["creteria_lable_4"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_4"].document;
         for (loop = 0; loop < creteria_lable_4.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_4.childNodes[loop];
           if( creteria_item.nodeName == 'creteria_lable_4-item'  ){ 
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_4"].innerText = item.childNodes[0].nodeValue;
		   }
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
           if( creteria5_item.nodeName == 'creteria5-item'  )
            {
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
	}	

		var creteria_lable_5 = doc.getElementsByTagName("creteria_lable_5")[0];
       if(document.all["creteria_lable_5"] != null  && creteria_lable_5 != null )
       {

         var td = document.all["creteria_lable_5"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_5"].document;
         for (loop = 0; loop < creteria_lable_5.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_5.childNodes[loop];
           if( creteria_item.nodeName == 'creteria_lable_5-item'  ){ 
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_5"].innerText = item.childNodes[0].nodeValue;
		   }
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
           if( creteria6_item.nodeName == 'creteria6-item'  )
            {
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
	}	

		var creteria_lable_6 = doc.getElementsByTagName("creteria_lable_6")[0];
       if(document.all["creteria_lable_6"] != null  && creteria_lable_6 != null )
       {

         var td = document.all["creteria_lable_6"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_6"].document;
         for (loop = 0; loop < creteria_lable_6.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_6.childNodes[loop];
           if( creteria_item.nodeName == 'creteria_lable_6-item'  ){ 
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_6"].innerText = item.childNodes[0].nodeValue;
		   }
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
           if( creteria7_item.nodeName == 'creteria7-item'  )
            {
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
	}	

		var creteria_lable_7 = doc.getElementsByTagName("creteria_lable_7")[0];
       if(document.all["creteria_lable_7"] != null  && creteria_lable_7 != null )
       {

         var td = document.all["creteria_lable_7"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_7"].document;
         for (loop = 0; loop < creteria_lable_7.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_7.childNodes[loop];
           if( creteria_item.nodeName == 'creteria_lable_7-item'  ){ 
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_7"].innerText = item.childNodes[0].nodeValue;
		   }
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
           if( creteria8_item.nodeName == 'creteria8-item'  )
            {
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
	}	
	   
	   var creteria_lable_8 = doc.getElementsByTagName("creteria_lable_8")[0];
       if(document.all["creteria_lable_8"] != null  && creteria_lable_8 != null )
       {

         var td = document.all["creteria_lable_8"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_8"].document;
         for (loop = 0; loop < creteria_lable_8.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_8.childNodes[loop];
           if( creteria_item.nodeName == 'creteria_lable_8-item'  ){ 
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_8"].innerText = item.childNodes[0].nodeValue;
		   }
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
           if( creteria9_item.nodeName == 'creteria9-item'  )
            {
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
	}	

		var creteria_lable_9 = doc.getElementsByTagName("creteria_lable_9")[0];
       if(document.all["creteria_lable_9"] != null  && creteria_lable_9 != null )
       {

         var td = document.all["creteria_lable_9"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_9"].document;
         for (loop = 0; loop < creteria_lable_9.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_9.childNodes[loop];
           if( creteria_item.nodeName == 'creteria_lable_9-item'  ){ 
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_9"].innerText = item.childNodes[0].nodeValue;
		   }
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
           if( creteria10_item.nodeName == 'creteria10-item'  )
            {
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
	}	

	   var creteria_lable_10 = doc.getElementsByTagName("creteria_lable_10")[0];
       if(document.all["creteria_lable_10"] != null  && creteria_lable_10 != null )
       {
         var td = document.all["creteria_lable_10"].ownerDocument; 
         if (!td) td = document.all["creteria_lable_10"].document;
         for (loop = 0; loop < creteria_lable_10.childNodes.length; loop++) 
		 {
           var creteria_item = creteria_lable_10.childNodes[loop];
           if( creteria_item.nodeName == 'creteria_lable_10-item'  ){ 
           var item  = creteria_item.getElementsByTagName("item")[0];
		   document.all["creteria_lable_10"].innerText = item.childNodes[0].nodeValue;
		   }
         }
	   }

}
*/
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
          <%=authorizationPageBeanId.getLocalization(application).getString("help_col2_productpost_1")%>
        </div>
        <!-- 
        <div class="portletContent even">
		  <%= authorizationPageBeanId.getLocalization(application).getString("help_col2_productpost_2") %>
        </div>
         -->
      </div>
    </div>
</div>






            </td>

            <td class="main">

            <!-- News part -->

	    <h1><%=authorizationPageBeanId.getLocalization(application).getString("title_common_of_form")%></h1>

		
		
		<br/>
	    <DIV style="background-image:url('images/f.jpg');height:20px; TEXT-ALIGN: left"  >
				<font color='white' size='2' > &nbsp;&raquo; <%=authorizationPageBeanId.getLocalization(application).getString("selection_common_of_form")%> </font>
		</DIV>
		<div class='box'>
		<div class='body'>
		<div>&nbsp; <%=authorizationPageBeanId.getLocalization(application).getString("pre_status_description")%> &raquo; 
		<%=catalogListBeanId.getCatalogUrlPath("SponsoredBySellersItemPost.jsp",authorizationPageBeanId)%>
		</div>
		</div>
		</div>

       <!--  list catalog  -->
		<div class="box">
		  <div class="body">
		    <div >
	         <%=publisherBeanId.getAction().equals("")?catalogListBeanId.getNavigator("SponsoredBySellersItemPost.jsp",authorizationPageBeanId):""%>
     	     <%=publisherBeanId.getAction().equals("add")?catalogAddBeanId.getAddFormWithJsp("SponsoredBySellersItemPost.jsp"):""%>
	         <%=publisherBeanId.getAction().equals("edit")?catalogEditBeanId.getEditForm(catalogListBeanId.rows[catalogEditBeanId.getIndxSelect()][0] , catalogListBeanId.rows[catalogEditBeanId.getIndxSelect()][1],"SponsoredBySellersItemPost.jsp"):""%>

		     </div>
		  </div>
		</div>
        <!--  end  list catalog  -->
		<br/>

      <form method="post" name="postsoftform"  ACTION="SponsoredBySellersItemPost.jsp" onSubmit="return  IsFormOk()"  >
      <!-- 
      <br/>
		<br/>
		<div class="box">
		  <div class="body">
		    <div >
		    <DIV style="background-image:url('images/f.jpg');height:20px; TEXT-ALIGN: left"  >
				<A	onclick="switchWindoSearch()" href="#" > <IMG id='search_cre'  title="<%= authorizationPageBeanId.getLocalization(application).getString("Expand") %>" src="images/expand.gif" align="right"/> </A>
				<font color='white' size='2' >&nbsp; <%=authorizationPageBeanId.getLocalization(application).getString("title_search_criteria")%> </font>
			</DIV>
			<br/>
			<div id='windowsearch' style="display:none;" >
					<TABLE border="0"  width="100%" cellpadding="0" cellspacing="0"   >
                     <TR id='creteria1'  onMouseOver="setCreteriaColor( '#DFE3EF' , 'creteria1' )"  onMouseOut="setCreteriaColor( 'white' , 'creteria1' )"   onMouseDown="creteriaSelected( '#FFEFFF' , 'creteria1' )"  >
					 <TD width="20" >&nbsp;</TD>
                     <TD width="20" >&nbsp;1.</TD>
                     <TD width="180" id='creteria_lable_1'  ><%=publisherBeanId.getOneLabel("select  label   from creteria1   where  active = true " + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%> :* </TD> 
                     <TD align="left" ><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria1_id", publisherBeanId.getCreteria1Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") , "select creteria1_id , name   from creteria1   where  active = true " + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%></TD>
                     <TD align="right" width="90" ><input type="button" name="change_creteria1" value="<%= authorizationPageBeanId.getLocalization(application).getString("edit") %>" onclick="dwindow('Creteria.jsp?table_name=creteria1'); return false;" ></TD>
					 <TD width="20" >&nbsp;</TD>                     
                     </TR>

                     <TR id='creteria2'  onMouseOver="setCreteriaColor( '#DFE3EF' , 'creteria2' )"  onMouseOut="setCreteriaColor( 'white' , 'creteria2' )"   onMouseDown="creteriaSelected( '#FFEFFF' , 'creteria2' )"  >
					 <TD width="20" >&nbsp;</TD>
                     <TD width="20" >&nbsp;2.</TD>
                     <TD width="180" id='creteria_lable_2' ><%=publisherBeanId.getOneLabel("select label   from creteria2   where  active = true " + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%> :* </TD> 
                     <TD align="left" ><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria2_id", publisherBeanId.getCreteria2Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") ,"select creteria2_id , name   from creteria2   where  active = true " + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true")) + " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria1Id()+ " ) "   )%></TD>
                     <TD align="right" width="90" ><input type="button" name="change_creteria2" value="<%= authorizationPageBeanId.getLocalization(application).getString("edit") %>" onclick="dwindow('Creteria.jsp?table_name=creteria2'); return false;" ></TD>
					 <TD width="20" >&nbsp;</TD>                     
                     </TR>
                     
                     <TR id='creteria3'  onMouseOver="setCreteriaColor( '#DFE3EF' , 'creteria3' )"  onMouseOut="setCreteriaColor( 'white' , 'creteria3' )"   onMouseDown="creteriaSelected( '#FFEFFF' , 'creteria3' )"  >
					 <TD width="20" >&nbsp;</TD>
                     <TD width="20" >&nbsp;3.</TD>
                     <TD width="180" id='creteria_lable_3'  ><%=publisherBeanId.getOneLabel("select  label   from creteria3   where  active = true "  + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%> :* </TD> 
                     <TD align="left"  ><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria3_id", publisherBeanId.getCreteria3Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") ,"select creteria3_id , name   from creteria3   where  active = true "  + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true")) + " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria2Id()+ " ) " )%></TD>
                     <TD align="right" width="90" ><input type="button" name="change_creteria3" value="<%= authorizationPageBeanId.getLocalization(application).getString("edit") %>" onclick="dwindow('Creteria.jsp?table_name=creteria3'); return false;" ></TD>
					 <TD width="20" >&nbsp;</TD>                     
                     </TR>

                     <TR id='creteria4'  onMouseOver="setCreteriaColor( '#DFE3EF' , 'creteria4' )"  onMouseOut="setCreteriaColor( 'white' , 'creteria4' )"   onMouseDown="creteriaSelected( '#FFEFFF' , 'creteria4' )"  >
					 <TD width="20" >&nbsp;</TD>
                     <TD width="20" >&nbsp;4.</TD>
                     <TD width="180" id='creteria_lable_4'  ><%=publisherBeanId.getOneLabel("select  label   from creteria4   where  active = true " + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%> :* </TD> 
                     <TD align="left"  ><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria4_id", publisherBeanId.getCreteria4Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") ,"select creteria4_id , name   from creteria4   where  active = true "  + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true")) + " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria3Id()+ " ) " )%></TD>
                     <TD align="right" width="90" ><input type="button" name="change_creteria4" value="<%= authorizationPageBeanId.getLocalization(application).getString("edit") %>" onclick="dwindow('Creteria.jsp?table_name=creteria4'); return false;" ></TD>
					 <TD width="20" >&nbsp;</TD>                    
                     </TR>

                    <TR id='creteria5'  onMouseOver="setCreteriaColor( '#DFE3EF' , 'creteria5' )"  onMouseOut="setCreteriaColor( 'white' , 'creteria5' )"   onMouseDown="creteriaSelected( '#FFEFFF' , 'creteria5' )"  >
					 <TD width="20" >&nbsp;</TD>
                     <TD width="20" >&nbsp;5.</TD>
                     <TD width="180"  id='creteria_lable_5' ><%=publisherBeanId.getOneLabel("select  label   from creteria5   where  active = true " + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%> :* </TD> 
                     <TD align="left" ><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria5_id", publisherBeanId.getCreteria5Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") ,"select creteria5_id , name   from creteria5   where  active = true "  + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true")) + " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria4Id()+ " ) " )%></TD>
                     <TD align="right" width="90" ><input type="button" name="change_creteria5" value="<%= authorizationPageBeanId.getLocalization(application).getString("edit") %>" onclick="dwindow('Creteria.jsp?table_name=creteria5'); return false;" ></TD>
					 <TD width="20" >&nbsp;</TD>                    
                     </TR>

                     <TR id='creteria6'  onMouseOver="setCreteriaColor( '#DFE3EF' , 'creteria6' )"  onMouseOut="setCreteriaColor( 'white' , 'creteria6' )"   onMouseDown="creteriaSelected( '#FFEFFF' , 'creteria6' )"  >
					 <TD width="20" >&nbsp;</TD>
                     <TD width="20" >&nbsp;6.</TD>
                     <TD width="180"  id='creteria_lable_6' ><%=publisherBeanId.getOneLabel("select  label   from creteria6   where  active = true " + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%> :* </TD> 
                     <TD align="left" ><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria6_id", publisherBeanId.getCreteria6Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") ,"select creteria6_id , name   from creteria6   where  active = true "  + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true")) + " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria5Id()+ " ) " )%></TD>
                     <TD align="right" width="90" ><input type="button" name="change_creteria6" value="<%= authorizationPageBeanId.getLocalization(application).getString("edit") %>" onclick="dwindow('Creteria.jsp?table_name=creteria6'); return false;" ></TD>
   					 <TD width="20" >&nbsp;</TD>
                     </TR>


                    <TR id='creteria7'  onMouseOver="setCreteriaColor( '#DFE3EF' , 'creteria7' )"  onMouseOut="setCreteriaColor( 'white' , 'creteria7' )"   onMouseDown="creteriaSelected( '#FFEFFF' , 'creteria7' )"  >
					 <TD width="20" >&nbsp;</TD>
                     <TD width="20" >&nbsp;7.</TD>
                     <TD width="180"  id='creteria_lable_7' ><%=publisherBeanId.getOneLabel("select  label   from creteria7   where  active = true " + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%> :* </TD>
                     <TD align="left" ><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria7_id", publisherBeanId.getCreteria7Id() , authorizationPageBeanId.getLocalization(application).getString("notselected")  ,"select creteria7_id , name   from creteria7   where  active = true " + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true")) + " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria6Id()+ " ) " )%></TD>
					 <TD align="right" width="90" ><input type="button" name="change_creteria7" value="<%= authorizationPageBeanId.getLocalization(application).getString("edit") %>" onclick="dwindow('Creteria.jsp?table_name=creteria7'); return false;" ></TD>
 					 <TD width="20" >&nbsp;</TD>
                   
                     </TR>

                    <TR id='creteria8'  onMouseOver="setCreteriaColor( '#DFE3EF' , 'creteria8' )"  onMouseOut="setCreteriaColor( 'white' , 'creteria8' )"   onMouseDown="creteriaSelected( '#FFEFFF' , 'creteria8' )"  >
					 <TD width="20" >&nbsp;</TD>
                     <TD width="20" >&nbsp;8.</TD>
                     <TD width="180"  id='creteria_lable_8' ><%=publisherBeanId.getOneLabel("select  label   from creteria8   where  active = true " + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%> :* </TD> 
                     <TD align="left" ><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria8_id", publisherBeanId.getCreteria8Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") ,"select creteria8_id , name   from creteria8   where  active = true " + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true")) + " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria7Id()+ " ) " )%></TD>
                     <TD align="right" width="90" ><input type="button" name="change_creteria8" value="<%= authorizationPageBeanId.getLocalization(application).getString("edit") %>" onclick="dwindow('Creteria.jsp?table_name=creteria8'); return false;" ></TD>
   					 <TD width="20" >&nbsp;</TD>
                     </TR>


                    <TR id='creteria9'  onMouseOver="setCreteriaColor( '#DFE3EF' , 'creteria9' )"  onMouseOut="setCreteriaColor( 'white' , 'creteria9' )"   onMouseDown="creteriaSelected( '#FFEFFF' , 'creteria9' )"  >
					 <TD width="20" >&nbsp;</TD>
                     <TD width="20" >&nbsp;9.</TD>
                     <TD width="180" id='creteria_lable_9'  ><%=publisherBeanId.getOneLabel("select  label   from creteria9   where  active = true " + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%> :* </TD> 
                     <TD align="left" ><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria9_id", publisherBeanId.getCreteria9Id() , authorizationPageBeanId.getLocalization(application).getString("notselected")  ,"select creteria9_id , name   from creteria9   where  active = true " + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true")) + " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria8Id()+ " ) " )%></TD>
                     <TD align="right" width="90" ><input type="button" name="change_creteria9" value="<%= authorizationPageBeanId.getLocalization(application).getString("edit") %>" onclick="dwindow('Creteria.jsp?table_name=creteria9'); return false;" ></TD>
   					 <TD width="20" >&nbsp;</TD>
                     </TR>                                          
                     
                    <TR id='creteria10'  onMouseOver="setCreteriaColor( '#DFE3EF' , 'creteria10' )"  onMouseOut="setCreteriaColor( 'white' , 'creteria10' )"   onMouseDown="creteriaSelected( '#FFEFFF' , 'creteria10' )"  >
					 <TD width="20" >&nbsp;</TD>
                     <TD width="20" >&nbsp;10.</TD>
                     <TD width="180" id='creteria_lable_10'  ><%=publisherBeanId.getOneLabel("select  label   from creteria10   where  active = true " + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true"))  )%> :* </TD> 
                     <TD align="left"  ><%=publisherBeanId.getComboBoxAutoSubmitLocale("creteria10_id", publisherBeanId.getCreteria10Id() , authorizationPageBeanId.getLocalization(application).getString("notselected") ,"select creteria10_id , name   from creteria10   where  active = true " + publisherBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(), authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog").equals("true")) + " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria9Id()+ " ) " )%></TD>
                     <TD align="right"  width="90" ><input type="button" name="change_creteria10" value="<%= authorizationPageBeanId.getLocalization(application).getString("edit") %>" onclick="dwindow('Creteria.jsp?table_name=creteria10'); return false;" ></TD>
					 <TD width="20" >&nbsp;</TD>                     
                     </TR>                                          
                     
                     <TR>
					 <TD width="20" >&nbsp;</TD>
                     <TD width="20" >&nbsp;</TD>
                     <TD width="180" ></TD> 
                     <TD></TD>
                     <TD width="90" ></TD>
					 <TD width="20" >&nbsp;</TD>                     
                     </TR>  
					</TABLE>
			   </div>
		     </div>
		  </div>
		</div>
		 -->
		<br/>
		<br/>
      
		<div class="box">
		  <div class="body">
		    <div >
					<DIV style="background-image:url('images/f.jpg');height:20px; TEXT-ALIGN: left"  >
					<font color='white' size='2' > &nbsp;&raquo; <%=authorizationPageBeanId.getLocalization(application).getString("title_form_post_info")%> </font>
					</DIV>
                     <TABLE>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("title_form")%>:* </TD><TD> <input  name="softname" size="68"  value="<%= publisherBeanId.getStrSoftName() %>" onBlur="checkEmpty(this.name,this.value);saveField(this.name,this.value)" >
					 <!-- 
                     <%=authorizationPageBeanId.getLocalization(application).getString("selected_section")%>:*<%=publisherBeanId.getComboBoxAutoSubmit("catalog_id", authorizationPageBeanId.getCatalogId() ,"select catalog_id , lable   from catalog   where  active = true and site_id = " + authorizationPageBeanId.getSiteId() + " and parent_id = " + authorizationPageBeanId.getCatalogParentId() )%>
 					 -->                     
					  <!-- 
                     <TR><TD>Закачать картинку:* </TD> <TD><input  name="imagename"  disabled="disabled" size="20" value="<%= publisherBeanId.getImgname() %>" ><input type="button" name="newimage" value="New Image"   onclick="dwindow('Image.html'); return false;"  ><input type="button" name="selectimage" value="select image" onclick="dwindow('SelectImage.jsp'); return false;" ><input type="hidden"  name="image_id" size="20" value="<%= publisherBeanId.getImageId() %>" ></TD></TR>
					  -->
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("cost")%>:* </TD> <TD><input  name="softcost" size="56" value="<%= publisherBeanId.getStrSoftCost() %>" onBlur="checkNumber(this.value);saveField(this.name,this.value)"  ><%=publisherBeanId.getComboBox("currency_id", CurrencyEnum.USD.getStrId() ,"SELECT currency_id , currency_lable FROM currency  WHERE active = true")%></TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("short_info")%> :* </TD> <TD> <textarea name="description" rows="10" cols="70" onBlur="saveField(this.name,this.value)" ><%=publisherBeanId.getStrSoftDescription()%></textarea></TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("upload_big_image")%>:* </TD> <TD><input onChange="saveField(this.name,this.value)" name="bigimagename" disabled="disabled" size="56" value="<%= publisherBeanId.getBigimgname() %>" ><input type="button" name="newbig_image" value="<%= authorizationPageBeanId.getLocalization(application).getString("new_big_image") %>"  onclick="dwindow('NewBigImage.jsp'); return false;" ><input type="button" name="selectbig_image" value="<%= authorizationPageBeanId.getLocalization(application).getString("select_big_image") %>" image" onclick="dwindow('SelectBigImage.jsp'); return false;" ><input onChange="saveField(this.name,this.value)" type="hidden"  name="bigimage_id" size="20" value="<%= publisherBeanId.getBigimageId() %>" ></TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("full_information")%> :* </TD> <TD> <textarea name="fulldescription" rows="10" cols="70" onBlur="saveField(this.name,this.value)" ><%=publisherBeanId.getProductFulldescription()%></textarea></TD></TR>
                     <TR><TD><%=authorizationPageBeanId.getLocalization(application).getString("attach_file")%>:* </TD> <TD><input onChange="saveField(this.name,this.value)" name="filename" disabled="disabled"  size="51" value="<%= publisherBeanId.getFilename() %>" ><input type="button" name="newfile" value="<%= authorizationPageBeanId.getLocalization(application).getString("new_file") %>" onclick="dwindow('NewFile.jsp')" ><input type="button" name="selectfile" value="<%= authorizationPageBeanId.getLocalization(application).getString("select_file") %>" onclick="dwindow('SelectFile.jsp')" ><input onChange="saveField(this.name,this.value)" type="hidden"  name="file_id" size="20" value="<%= publisherBeanId.getFileId() %>" ></TD></TR>
					<!-- 
                     <TR><TD  colspan="2" ><%=authorizationPageBeanId.getLocalization(application).getString("approve")%>:*<%=publisherBeanId.getComboBox("type_id", publisherBeanId.getTypeId() ,"select type_id , type_lable from typesoft   where  active = true order by type_id asc" )%></TD></TR>
 					-->    
 					 <TR><TD></TD> <TD></TD></TR>
 					 <TR><TD></TD> <TD></TD></TR>
 					 <TR><TD></TD> <TD></TD></TR>
 					 <TR><TD></TD> <TD></TD></TR>                   
                     <TR><TD></TD> <TD><input type="button" name="button" value="<%= authorizationPageBeanId.getLocalization(application).getString("save") %>"  onclick="do_save()" > <input type="button" name="button" value="<%= authorizationPageBeanId.getLocalization(application).getString("save_as_new") %>"  onclick="do_insert()" > <input type="reset" value="<%= authorizationPageBeanId.getLocalization(application).getString("clear") %>"></TD></TR>
                     </TABLE>
                     <input type="hidden"  name="action"  value=""/>
                     <input type="hidden"  name="insert"  value="false"/>
                     <input type="hidden"  name="portlettype_id"  value="2"/>
                     <input type="hidden"  name="type_id"  value="3"/>
                     <input type="hidden"  name="catalog_id"  value="<%= authorizationPageBeanId.getCatalogParentId() %>"/> <!-- because i removed subcatalog  -->
                     
		     </div>
		  </div>
		</div>
    </form>


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

<br/>

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