xcopy xsl\shops.online-spb.com\calendar.css
xcopy xsl\shops.online-spb.com\caption.js 
xcopy xsl\shops.online-spb.com\constant.css
xcopy xsl\shops.online-spb.com\datepicker.js
xcopy xsl\shops.online-spb.com\jquery.js
xcopy xsl\shops.online-spb.com\mootools.js
xcopy xsl\shops.online-spb.com\template.css
xcopy /E xsl\shops.online-spb.com\images images\
xcopy /E xsl\shops.online-spb.com\mail   mail\
xcopy /E xsl\shops.online-spb.com\ru   ru\
xcopy /E xsl\shops.online-spb.com\en   en\

rem del /p  xsl
rd /s /q xsl


jar cvfM arhive.jar *.xsl mail images ru en *.css *.js *.xml *.bat
