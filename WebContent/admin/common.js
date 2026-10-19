// show window with big image start




	function showpic(src)
    {
        document.getElementById('loadingpic').style.display='block'
        document.getElementById('panelpic').style.display='block';
        document.getElementById('pic').src=src;
		
    }

	function showpic(src,event)
    {
        //movepic(event);
        //document.getElementById('loadingpic').style.display='block'
        //document.getElementById('panelpic').style.display='block';
        //document.getElementById('pic').src=src;
		//current_url_img = src;
		//movepic(event);
    }


  function movepic(evt)
    {


	var posx = 0;
	var posy = 0;
	

       document.getElementById('panelpic').style.left =  posx ;
       document.getElementById('panelpic').style.top =  posy ;



    }


    function hidepic()
    {
        document.getElementById('panelpic').style.display='none';
       
        document.getElementById('pic').style.display='none';
    }

// show window with big image end



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


// start select page button in table



// start select page button in table
var select_creteria_id ;

function pageSelected( color , id ) 
{
if(document.getElementById(select_creteria_id) != null ) document.getElementById(select_creteria_id).style.background = 'white' ;
select_creteria_id = id ; 
document.getElementById(id).style.background = color ;
}


function setPageButtonColor( color , id ) 
{
if( select_creteria_id != id ) document.getElementById(id).style.background = color ;
else  document.getElementById(id).style.background = '#DFE3EF' ;
}
// end select row page button in table


function webmail() 
{ 
//alert('ok');
document.getElementById('webmail').submit();
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






// end select row in table

var windowsearch_hide = false ;

function switchWindoSearch()
{
				windowsearch_hide = !windowsearch_hide;
				document.getElementById("search_cre").src =  (windowsearch_hide ? "images/expand.gif" : "images/collapse.gif");
				document.getElementById("search_cre").title =  (windowsearch_hide ? "Razvernut" : "Svernut");
				document.getElementById("windowsearch").style.display = windowsearch_hide ? "none" : "";
}



// end select row in table

var windowforum_hide = false ;

function switchWindoForum()
{
	windowforum_hide = !windowforum_hide;
	document.getElementById("windowforum").style.display = windowforum_hide ? "none" : "block";
	document.getElementById("forum_div").src =  (windowforum_hide ? "images/expand.gif" : "images/collapse.gif");
	document.getElementById("forum_div").title =  (windowforum_hide ? "\u0420\u0430\u0437\u0432\u0435\u0440\u043d\u0443\u0442\u044c" : "\u0421\u0432\u0435\u0440\u043d\u0443\u0442\u044c");
	
}

var currentStr = 0;
var currentElem = "";
var currentDiv = "";

function createForm(offsetValue)
{
	var formPage = document.getElementById('formToPage');
	formPage.offset.value = (offsetValue-1)*10; 
	return formPage; 
}

function setCurrent(page, isAdmin) {

	var p = 0;
	p = page;
	currentStr = p/10 + 1;
	
	for(i = 1;i !=21; i++){ 		
	 	if(document.getElementById('link'+i).innerHTML == currentStr){
	 		document.getElementById('d'+i).style.backgroundColor = '#DFE3EF';
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
	document.getElementById(currentDiv).style.backgroundColor = 'white';
	document.getElementById(currentElem).style.color = '#8997a0';
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

	document.getElementById(currentDiv).style.backgroundColor = 'white';
	document.getElementById(currentElem).style.color = '#8997a0';
	//document.getElementById(currentDiv).style.width = 'auto';

	var strN = parseInt(document.getElementById('link1').innerHTML);
	
 	if (!(strN > 99980)){
	 	var i = 0;
	 	for(i = 1;i !=21; i++){ 		
	 		document.getElementById('link'+i).innerHTML = strN+9+i;
	 		
	 		if((strN+9+i) == currentStr){
		 		document.getElementById('link'+i).style.backgroundColor = '#DFE3EF';
		 		document.getElementById('link'+i).style.color = 'white';
		 		//document.getElementById('d'+i).style.width = '2.5%';
	 		}
	 	}
 	}
}

function showPrev() 
{	 
	document.getElementById(currentDiv).style.backgroundColor = 'white';
	document.getElementById(currentElem).style.color = '#8997a0';
	//document.getElementById(currentDiv).style.width = 'auto';

	var strN = parseInt(document.getElementById('link1').innerHTML)-11;
	
 	if (strN > 1){
	 	var i = 0;
	 	for(i = 1;i !=21; i++){ 		
	 		document.getElementById('link'+i).innerHTML = strN+i;
	 		
	 		if((strN+i) == currentStr){
		 		document.getElementById('d'+i).style.backgroundColor = '#DFE3EF';
		 		document.getElementById('link'+i).style.color = 'white';
		 		//document.getElementById('d'+i).style.width = '2.5%';
	 		}
	 	}
 	}else{
 		for(i = 1;i !=21; i++){ 		
	 		document.getElementById('link'+i).innerHTML = i;
	 		
	 		if( i == currentStr){
		 		document.getElementById('d'+i).style.backgroundColor = '#DFE3EF';
		 		document.getElementById('link'+i).style.color = 'white';
		 		//document.getElementById('d'+i).style.width = '2.5%';
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

var blink = 0 ;

function Changehead() 
{ 

//alert('ok' + blink ) ;
//document.getElementById('blink').style.display='none';
if(blink == 0 )
{ 
document.getElementById('blink').style.color = 'red' ;
//document.getElementById('regist').style.color = 'red' ;
blink = 1;
}
else 
{
document.getElementById('blink').style.color = 'black' ;
//document.getElementById('regist').style.color = 'black' ;
//document.getElementById('blink').style.display='block'  ;
blink = 0 ;
}

window.setTimeout("Changehead()",300); /*спустя 1 секунду, меняешь цвет на новый, если нужно дольше, то ставишь число больше*/ 
} 

 