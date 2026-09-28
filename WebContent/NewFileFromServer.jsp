<%@ page errorPage="error.jsp" %>
<%
  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<HTML>
  <HEAD>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <TITLE>Image upload</TITLE>
       <script language="JavaScript">
       <!--

        function setData()
        {
        document.forms.fullpath.filepath.value = document.forms.fullpath.file.value  ;
        document.forms.fullpath.submit()  ;
        return true ;
        }


        function setClose()
        {
        top.dwindow('SelectImage.jsp'); 
        return true ;
        }

//-->
</script>
  </HEAD>
  <BODY>

  <h5>Upload file </h5>
  <form  name="fullpath"  action="fileservletupload" method="post" >
  <input type="file" name="file"><br>
  <input type="hidden" name="filepath" />
  <input type="button"  value="Attach" onClick="return  setData()" />
  <input type="button" value = "close" onClick="return setClose()" />
  </form>

  <h5>Upload file  </h5>
  <form  name="uploadform"  action="/fileservletupload" method="post" enctype="multipart/form-data">
  <input type="file" name="file"><br>
  <input type="submit" value = "UpLoad" />
  <input type="button" value = "close" onClick="return setClose()" />
  </form>
</BODY>

</HTML>
