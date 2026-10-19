/**
* @version		$Id: modal.js 5263 2006-10-02 01:25:24Z webImagery $
* @copyright	Copyright (C) 2005 - 2008 Open Source Matters. All rights reserved.
* @license		GNU/GPL, see LICENSE.php
* Joomla! is free software. This version may have been modified pursuant
* to the GNU General Public License, and as distributed it includes or
* is derivative of works licensed under the GNU General Public License or
* other free or open source software licenses.
* See COPYRIGHT.php for copyright notices and details.
*/

/**
 * JCaption javascript behavior
 *
 * Used for displaying image captions
 *
 * @author		Johan Janssens <johan.janssens@joomla.org>
 * @package		Joomla
 * @since		1.5
 * @version     1.0
 */


	var currentStr = 0;
	var currentElem = "";
	var currentDiv = "";
	
	var agt = navigator.userAgent.toLowerCase ();
	var is_moz = ((agt.indexOf ("msie") == -1) && (agt.indexOf ("opera") == -1));
	
	function createForm(offsetValue)
	{
		var formPage = document.getElementById('formToPage');
		formPage.offset.value = (offsetValue-1)*10; 
		return formPage; 
	}
	
	function setWidth(){
	
	   	var el = document.getElementById('main');
	   //	alert(el);
	   	var el2 = document.getElementById('bodyDiv');
	   	//	alert(el2);
	   	var userwidth = document.body.clientWidth;
	   //	alert(userwidth);
	   	
	   	el.style.paddingLeft = (userwidth-1350)/2+'px';	
	  // 	alert(el.style.paddingLeft);
	}

	function setStyle(){
		var agt = navigator.userAgent.toLowerCase ();
		var is_ie = ((agt.indexOf ("msie") != -1) && (agt.indexOf ("opera") == -1) && (agt.indexOf ("safari") == -1));
	    	
		if(is_ie){
			//setWidth();
	     	var el = document.getElementById('rigtColumn');
	     	el.style.paddingRight = 60+'px';
		}
		
	}



function showH(isAdmin){
	var h2 = document.getElementById('forum').clientHeight;
	
	var h = 0;
	
	if(isAdmin == 2){
		h = document.getElementById('main').clientHeight - 672 - h2;		
	} else{
		h = document.getElementById('main').clientHeight - 633 - h2;
	}
	document.getElementById('mainContent').style.height = h + 'px';
	
}

function setCurrent(page, isAdmin) {

	var p = 0;
	p = page;
	currentStr = p/10 + 1;
	
	for(i = 1;i !=21; i++){ 		
	 	if(document.getElementById('link'+i).innerHTML == currentStr){
	 		document.getElementById('d'+i).style.backgroundColor = '#cecec6';
	 		document.getElementById('link'+i).style.color = 'white';
	 		//document.getElementById('d'+i).style.width = '2.5%';
	 		currentElem  = 'link'+i;
	 		currentDiv = 'd'+i;
	 		break;
	 	}
	}
	//setStyle();
	//showH(isAdmin);
}


function showStr(linkId) 
{
	document.getElementById(currentDiv).style.backgroundColor = '#edede7';
	document.getElementById(currentElem).style.color = '#2a2a2a';
	//document.getElementById(currentDiv).style.width = 'auto';
	
	var strN = 0;
 	strN = parseInt(document.getElementById(linkId).innerHTML);
 	
 	var formPage = createForm(strN);
 	
 	
 	currentStr = strN;
	
	formPage.method = 'get';				
 	formPage.submit();
 	
}

function showNext() 
{	 

	document.getElementById(currentDiv).style.backgroundColor = '#edede7';
	document.getElementById(currentElem).style.color = '#2a2a2a';
	//document.getElementById(currentDiv).style.width = 'auto';

	var strN = parseInt(document.getElementById('link1').innerHTML);
	
 	if (!(strN > 99980)){
	 	var i = 0;
	 	for(i = 1;i !=21; i++){ 		
	 		document.getElementById('link'+i).innerHTML = strN+9+i;
	 		
	 		if((strN+9+i) == currentStr){
		 		document.getElementById('link'+i).style.backgroundColor = '#cecec6';
		 		document.getElementById('link'+i).style.color = 'white';
		 		//document.getElementById('d'+i).style.width = '2.5%';
	 		}
	 	}
 	}
}

function showPrev() 
{	 
	document.getElementById(currentDiv).style.backgroundColor = '#edede7';
	document.getElementById(currentElem).style.color = '#2a2a2a';
	//document.getElementById(currentDiv).style.width = 'auto';

	var strN = parseInt(document.getElementById('link1').innerHTML)-11;
	
 	if (strN > 1){
	 	var i = 0;
	 	for(i = 1;i !=21; i++){ 		
	 		document.getElementById('link'+i).innerHTML = strN+i;
	 		
	 		if((strN+i) == currentStr){
		 		document.getElementById('d'+i).style.backgroundColor = '#cecec6';
		 		document.getElementById('link'+i).style.color = 'white';
		 		//document.getElementById('d'+i).style.width = '2.5%';
	 		}
	 	}
 	}else{
 		for(i = 1;i !=21; i++){ 		
	 		document.getElementById('link'+i).innerHTML = i;
	 		
	 		if( i == currentStr){
		 		document.getElementById('d'+i).style.backgroundColor = '#cecec6';
		 		document.getElementById('link'+i).style.color = 'white';
		 		//document.getElementById('d'+i).style.width = '2.5%';
	 		}
	 	} 	
 	}
}

function search( char ) 
{ 
document.forms[0].method = 'post' ;
document.forms[0].search_char.value = char ;
document.forms[0].search_value.value = '' ;
document.forms[0].searchquery.value = '2' ;
document.forms[0].submit();
}

function search_word() 
{ 
document.forms[0].method = 'post' ;
document.forms[0].search_char.value = '' ;
document.forms[0].searchquery.value = '1' ;
document.forms[0].submit();
}


function search_cre() 
{ 
document.forms[0].method = 'post' ;
document.forms[0].search_char.value = '' ;
document.forms[0].search_value.value = '' ;
document.forms[0].searchquery.value = '3' ;
}


function search_cre_bronirovanie() 
{ 
document.forms[0].method = 'post' ;
document.forms[0].search_char.value = '' ;
document.forms[0].search_value.value = '' ;
document.forms[0].searchquery.value = '4' ;
}

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


function doChengeCreteria( creteria_id , creteria_value ) 
{

           
           var url = 'creteria?' + creteria_id  + '=' + creteria_value  ; 

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


       var doc = responseXML.getElementsByTagName('document')[0];


	// createri 1
       var creteria1 = doc.getElementsByTagName('creteria1')[0];
       if(document.all['creteria1_id'] != null  && creteria1 != null )
       {

         var combobox = document.all['creteria1_id'].ownerDocument; 
         if (!combobox) combobox = document.all['creteria1_id'].document;
         document.all['creteria1_id'].options.length = 0 ;


         for (loop = 0; loop < creteria1.childNodes.length; loop++) 
	 {
           var creteria1_item = creteria1.childNodes[loop];

	   if( creteria1_item.nodeName == 'creteria1-item'  ){ 

           var selected = creteria1_item.getElementsByTagName('selected')[0];

           var code = creteria1_item.getElementsByTagName('code')[0];
           var item  = creteria1_item.getElementsByTagName('item')[0];

	   var opt = combobox.createElement('OPTION');
 	   opt.value = code.childNodes[0].nodeValue;
 	   opt.text = item.childNodes[0].nodeValue;


	   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue ) opt.selected = true;

	   document.all['creteria1_id'].options.add(opt, loop);

	   }

         }
	}	


	// createri 2
       var creteria2 = doc.getElementsByTagName('creteria2')[0];
       if(document.all['creteria2_id'] != null && creteria2 != null )
       {

          var combobox2 = document.all['creteria2_id'].ownerDocument; 
          if (!combobox2) combobox2 = document.all['creteria2_id'].document;
          document.all['creteria2_id'].options.length = 0 ;


          for (loop = 0; loop < creteria2.childNodes.length; loop++) 
	  {
           var creteria2_item = creteria2.childNodes[loop];

	   if( creteria2_item.nodeName == 'creteria2-item'  ){ 

           var selected = creteria2_item.getElementsByTagName('selected')[0];
           var code = creteria2_item.getElementsByTagName('code')[0];
           var item  = creteria2_item.getElementsByTagName('item')[0];

	   var opt = combobox2.createElement('OPTION');
 	   opt.value = code.childNodes[0].nodeValue;
 	   opt.text = item.childNodes[0].nodeValue;

	   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;

	   document.all['creteria2_id'].options.add(opt, loop);

	    }
          }
	}	



	// createri 3
       var creteria3 = doc.getElementsByTagName('creteria3')[0];
       if(document.all['creteria3_id'] != null && creteria3 != null )
       {

          var combobox2 = document.all['creteria3_id'].ownerDocument; 
          if (!combobox2) combobox2 = document.all['creteria3_id'].document;
          document.all['creteria3_id'].options.length = 0 ;


          for (loop = 0; loop < creteria3.childNodes.length; loop++) 
	  {
           var creteria3_item = creteria3.childNodes[loop];

	   if( creteria3_item.nodeName == 'creteria3-item'  ){ 

           var selected = creteria3_item.getElementsByTagName('selected')[0];
           var code = creteria3_item.getElementsByTagName('code')[0];
           var item  = creteria3_item.getElementsByTagName('item')[0];

	   var opt = combobox2.createElement('OPTION');
 	   opt.value = code.childNodes[0].nodeValue;
 	   opt.text = item.childNodes[0].nodeValue;

	   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;

	   document.all['creteria3_id'].options.add(opt, loop);

	    }

          }
	}	



	// createri 4
       var creteria4 = doc.getElementsByTagName('creteria4')[0];
       if(document.all['creteria4_id'] != null && creteria4 != null )
       {

          var combobox2 = document.all['creteria4_id'].ownerDocument; 
          if (!combobox2) combobox2 = document.all['creteria4_id'].document;
          document.all['creteria4_id'].options.length = 0 ;


          for (loop = 0; loop < creteria4.childNodes.length; loop++) 
	  {
           var creteria4_item = creteria4.childNodes[loop];

	   if( creteria4_item.nodeName == 'creteria4-item'  ){ 
           var selected = creteria4_item.getElementsByTagName('selected')[0];
           var code = creteria4_item.getElementsByTagName('code')[0];
           var item  = creteria4_item.getElementsByTagName('item')[0];

	   var opt = combobox2.createElement('OPTION');
 	   opt.value = code.childNodes[0].nodeValue;
 	   opt.text = item.childNodes[0].nodeValue;
	   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;
	   document.all['creteria4_id'].options.add(opt, loop);
	    }
          }
	}	



	// createri 5
       var creteria5 = doc.getElementsByTagName('creteria5')[0];
       if(document.all['creteria5_id'] != null && creteria5 != null )
       {

          var combobox2 = document.all['creteria5_id'].ownerDocument; 
          if (!combobox2) combobox2 = document.all['creteria5_id'].document;
          document.all['creteria5_id'].options.length = 0 ;


          for (loop = 0; loop < creteria5.childNodes.length; loop++) 
	  {
           var creteria5_item = creteria5.childNodes[loop];
	   if( creteria5_item.nodeName == 'creteria5-item'  ){ 

           var selected = creteria5_item.getElementsByTagName('selected')[0];
           var code = creteria5_item.getElementsByTagName('code')[0];
           var item  = creteria5_item.getElementsByTagName('item')[0];

	   var opt = combobox2.createElement('OPTION');
 	   opt.value = code.childNodes[0].nodeValue;
 	   opt.text = item.childNodes[0].nodeValue;

	   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;

	   document.all['creteria5_id'].options.add(opt, loop);

	    }
          }
	}	



	// createri 6
       var creteria6 = doc.getElementsByTagName('creteria6')[0];
       if(document.all['creteria6_id'] != null && creteria6 != null )
       {

          var combobox2 = document.all['creteria6_id'].ownerDocument; 
          if (!combobox2) combobox2 = document.all['creteria6_id'].document;
          document.all['creteria6_id'].options.length = 0 ;


          for (loop = 0; loop < creteria6.childNodes.length; loop++) 
	  {
           var creteria6_item = creteria6.childNodes[loop];

	   if( creteria6_item.nodeName == 'creteria6-item'  ){ 

           var selected = creteria6_item.getElementsByTagName('selected')[0];
           var code = creteria6_item.getElementsByTagName('code')[0];
           var item  = creteria6_item.getElementsByTagName('item')[0];

	   var opt = combobox2.createElement('OPTION');
 	   opt.value = code.childNodes[0].nodeValue;
 	   opt.text = item.childNodes[0].nodeValue;

	   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;

	   document.all['creteria6_id'].options.add(opt, loop);

	   }
          }
	}	



	// createri 7
       var creteria7 = doc.getElementsByTagName('creteria7')[0];
       if(document.all['creteria7_id'] != null && creteria7 != null )
       {

          var combobox2 = document.all['creteria7_id'].ownerDocument; 
          if (!combobox2) combobox2 = document.all['creteria7_id'].document;
          document.all['creteria7_id'].options.length = 0 ;


          for (loop = 0; loop < creteria7.childNodes.length; loop++) 
	  {
           var creteria7_item = creteria7.childNodes[loop];
	   if( creteria7_item.nodeName == 'creteria7-item'  ){ 

           var selected = creteria7_item.getElementsByTagName('selected')[0];
           var code = creteria7_item.getElementsByTagName('code')[0];
           var item  = creteria7_item.getElementsByTagName('item')[0];

	   var opt = combobox2.createElement('OPTION');
 	   opt.value = code.childNodes[0].nodeValue;
 	   opt.text = item.childNodes[0].nodeValue;

	   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;

	   document.all['creteria7_id'].options.add(opt, loop);
	    }
          }
	}	


	// createri 8
       var creteria8 = doc.getElementsByTagName('creteria8')[0];
       if(document.all['creteria8_id'] != null && creteria8 != null )
       {

          var combobox2 = document.all['creteria8_id'].ownerDocument; 
          if (!combobox2) combobox2 = document.all['creteria8_id'].document;
          document.all['creteria8_id'].options.length = 0 ;


          for (loop = 0; loop < creteria8.childNodes.length; loop++) 
	  {
           var creteria8_item = creteria8.childNodes[loop];
	   if( creteria8_item.nodeName == 'creteria8-item'  ){ 
           var selected = creteria8_item.getElementsByTagName('selected')[0];
           var code = creteria8_item.getElementsByTagName('code')[0];
           var item  = creteria8_item.getElementsByTagName('item')[0];

	   var opt = combobox2.createElement('OPTION');
 	   opt.value = code.childNodes[0].nodeValue;
 	   opt.text = item.childNodes[0].nodeValue;

	   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;

	   document.all['creteria8_id'].options.add(opt, loop);
	    }
          }
	}	


	// createri 9
       var creteria9 = doc.getElementsByTagName('creteria9')[0];
       if(document.all['creteria9_id'] != null && creteria9 != null )
       {

          var combobox2 = document.all['creteria9_id'].ownerDocument; 
          if (!combobox2) combobox2 = document.all['creteria9_id'].document;
          document.all['creteria9_id'].options.length = 0 ;


          for (loop = 0; loop < creteria9.childNodes.length; loop++) 
	  {
           var creteria9_item = creteria9.childNodes[loop];
	   if( creteria9_item.nodeName == 'creteria9-item'  ){ 
           var selected = creteria9_item.getElementsByTagName('selected')[0];
           var code = creteria9_item.getElementsByTagName('code')[0];
           var item  = creteria9_item.getElementsByTagName('item')[0];

	   var opt = combobox2.createElement('OPTION');
 	   opt.value = code.childNodes[0].nodeValue;
 	   opt.text = item.childNodes[0].nodeValue;

	   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;

	   document.all['creteria9_id'].options.add(opt, loop);

	    }
          }
	}	



	// createri 10
       var creteria10 = doc.getElementsByTagName('creteria10')[0];
       if(document.all['creteria10_id'] != null && creteria10 != null )
       {

          var combobox2 = document.all['creteria10_id'].ownerDocument; 
          if (!combobox2) combobox2 = document.all['creteria10_id'].document;
          document.all['creteria10_id'].options.length = 0 ;


          for (loop = 0; loop < creteria10.childNodes.length; loop++) 
	  {
           var creteria10_item = creteria10.childNodes[loop];
	   if( creteria10_item.nodeName == 'creteria10-item'  ){ 

           var selected = creteria10_item.getElementsByTagName('selected')[0];
           var code = creteria10_item.getElementsByTagName('code')[0];
           var item  = creteria10_item.getElementsByTagName('item')[0];

	   var opt = combobox2.createElement('OPTION');
 	   opt.value = code.childNodes[0].nodeValue;
 	   opt.text = item.childNodes[0].nodeValue;

	   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue )  opt.selected = true;

	   document.all['creteria10_id'].options.add(opt, loop);

	    }
          }
	}	

}

function doChangeCity( country_id , country_value ) 
{

           
           var url = 'authorization?' + country_id  + '=' + country_value  ; 

           var req = initRequest();
           req.onreadystatechange = function() 
	   {
               if (req.readyState == 4) 
	       {
                   if (req.status == 200) 
		   {

		     parseMessages2(req.responseXML);
                   }
               }
           };

           req.open("GET", url, true);
           req.send(null);


}

function parseMessages2(responseXML) 
{


       var doc = responseXML.getElementsByTagName("document")[0];

	// createri 1
       var city = doc.getElementsByTagName("city")[0];
       if(document.all["city_id"] != null  && city != null )
       {

         var combobox = document.all["city_id"].ownerDocument; 
         if (!combobox) combobox = document.all["city_id"].document;
         document.all["city_id"].options.length = 0 ;


         for (loop = 0; loop < city.childNodes.length; loop++) 
	 {
           var city_item = city.childNodes[loop];
	   if( city_item.nodeName == 'city-item'  ){ 
           var selected = city_item.getElementsByTagName("selected")[0];
           var code = city_item.getElementsByTagName("code")[0];
           var item  = city_item.getElementsByTagName("item")[0];

	   var opt = combobox.createElement('OPTION');
 	   opt.value = code.childNodes[0].nodeValue;
 	   opt.text = item.childNodes[0].nodeValue;


	   if(code.childNodes[0].nodeValue == selected.childNodes[0].nodeValue ) opt.selected = true;

	   document.all["city_id"].options.add(opt, loop);

            }
	  }
	}	


}



function addMessage(role_id){
	window.location.href="Authorization.jsp?Login=";
	if(role_id == 0){
	
		var ok2 = confirm("?");
		if(ok2){
			window.location.href="Authorization.jsp?Login=";
		}
	}else{
		window.location.href = "BlogExtProductPost.jsp";
	}
}


function isAddressValid(){
	var message = "";
	
	var object = document.getElementById('country_id');
	if(object != null){
		if (object.value.length == 0){
			message = message + "choose country\n";
		}
	}
	
	object = document.getElementById('city_id');
	if(object != null){
		if (object.value.length == 0){
			message = message + "choose city\n";
		}
	}
	
	object = document.getElementById('shipment_address');
	if(object != null){
		if (object.value.length == 0){
			message = message + "enter shipment address\n";
		}
	}
	
	object = document.getElementById('shipment_phone');
	if(object != null){
		if (object.value.length == 0){
			message = message + "enter shipment phone number\n";
		}
	}
	
	object = document.getElementById('contact_person');
	if(object != null){
		if (object.value.length == 0){
			message = message + "enter person \n";
		}
	}
	
	object = document.getElementById('shipment_email');
	if(object != null){
		if (object.value.length == 0){
			message = message + "enter shipment email\n";
		}
	}	
	
	if (message.length > 0){
		alert(message);
	}else{
		var addresForm = document.getElementById('addressForm');
		addresForm.submit();
	}

}



function setDates(datefrom, dateto){
	var dateFrom = new Date(datefrom);
	
	var day = dateFrom.getDate();
	var month = dateFrom.getMonth()+1;
	var year = dateFrom.getYear();	
		
	if(is_moz){
		year = year + 1900;
	}
	
	var month_text = month;
	var day_text = day;
	
	
	if (!(month > 9)){
		month_text = "0" + month;
	}
	if (!(day > 9)){
		day_text = "0" + day;
	}
	
	document.getElementById('datepicker1').value =  day_text +"/"+ month_text +"/"+year;
	
	var dateTo = new Date(dateto);
	
	day = dateTo.getDate();
	month = dateTo.getMonth()+1;
	year = dateTo.getYear();
	
	month_text = month;
	day_text = day;
	
	if (!(month > 9)){
		month_text = "0" + month;
	}
	if (!(day > 9)){
		day_text = "0" + day;
	}
	
	if(is_moz){
		year = year + 1900;
	}
	
	document.getElementById('datepicker2').value = day_text +"/"+ month_text +"/"+year;
     	
}