md xsl 
cd xsl
md shops.online-spb.com
cd shops.online-spb.com
md images
md mail
cd ../
cd ../

rem xcopy . ss
     
xcopy menu.js xsl\shops.online-spb.com
xcopy menu.css xsl\shops.online-spb.com
xcopy caption.js xsl\shops.online-spb.com
xcopy calendar.css xsl\shops.online-spb.com
xcopy jquery.lightbox.css xsl\shops.online-spb.com
xcopy constant.css xsl\shops.online-spb.com
xcopy constant_f.css xsl\shops.online-spb.com
xcopy datepicker.js xsl\shops.online-spb.com
xcopy jquery.js  xsl\shops.online-spb.com
xcopy jquery_v2009.js xsl\shops.online-spb.com\
xcopy jquery.lightbox.js xsl\shops.online-spb.com
xcopy template.css  xsl\shops.online-spb.com
xcopy template_f.css  xsl\shops.online-spb.com
copy /z  images  xsl\shops.online-spb.com\images
copy /z  mail  xsl\shops.online-spb.com\mail

del menu.js
del menu.css
del caption.js
del jquery.lightbox.js
del jquery.lightbox.css
del calendar.css
del constant.css 
del constant_f.css 
del datepicker.js 
del jquery.js  
del jquery_v2009.js
del template.css  
del template_f.css  
rd /s /q images
rd /s /q mail  
