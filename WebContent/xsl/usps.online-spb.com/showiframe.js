function openDialog(url) {
   document.getElementById('iframe').src = url;
   document.getElementById('dialog').style.display = 'block';
   document.getElementById('overlay').style.display = 'block';
}


function closeDialog() {
   document.getElementById('dialog').style.display = 'none';
   document.getElementById('overlay').style.display = 'none';
}