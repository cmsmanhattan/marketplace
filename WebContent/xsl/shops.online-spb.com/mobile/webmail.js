function openDialogMail(url) {
   document.getElementById('iframe_mail').src = url;
   document.getElementById('dialog_mail').style.display = 'block';
   document.getElementById('overlay_mail').style.display = 'block';
}


function closeDialogMail() {
   document.getElementById('dialog_mail').style.display = 'none';
   document.getElementById('overlay_mail').style.display = 'none';
}