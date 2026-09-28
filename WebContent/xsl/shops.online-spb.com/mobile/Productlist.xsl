<?xml version='1.0' encoding='windows-1251' ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"  xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:output method="html" indent="yes"/>
<xsl:output encoding="UTF-8"/>
<xsl:strip-space elements="*"/>

<xsl:template match="/">
        <xsl:variable name="host" select="string(document/host)"/>
        <xsl:variable name="page" select="number(document/offset)"/>
        <xsl:variable name="user_id" select="number(/document/owner_user_id)"/>
        <xsl:variable name="role" select="document/role_id"/>
        <xsl:variable name="site_id" select="document/site_id"/>
        <xsl:variable name="virtual_host" select="document/virtual_host"/>

<HTML>
<HEAD>
<META HTTP-EQUIV="no-cache"/>
<META name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=yes"/>
 <title><xsl:value-of select="document/title"/></title>

	<style type="text/css">
		/* Mobile-fit overrides: the legacy markup below hardcodes desktop
		   pixel widths (960px page wrapper, 494px content column, 235/245px
		   product-grid cells, 912px reviews block). On a phone that forces
		   the browser to render the whole page at desktop scale and then
		   shrink it, producing the sideways-shifted/clipped look. These
		   overrides make every one of those containers fluid so the page
		   actually fits the screen width instead of being scaled down. */
		* { box-sizing: border-box; }
		body, html { overflow-x: hidden; max-width: 100%; }
		.main {
			width: 100% !important;
			max-width: 960px !important;
			margin: 0 auto !important;
		}
		.width {
			width: 100% !important;
			max-width: 670px !important;
			box-sizing: border-box;
		}
		.article_column {
			width: 100% !important;
			max-width: 100% !important;
			box-sizing: border-box;
		}
		.article-bg, .article-left, .article-right, .comp-cont {
			width: 100% !important;
			max-width: 100% !important;
			box-sizing: border-box;
		}
		.indent {
			width: 100% !important;
			max-width: 960px !important;
			box-sizing: border-box;
		}
		#mainContent.mainContent {
			width: 100% !important;
			max-width: 100% !important;
			box-sizing: border-box;
		}
		#mainContent.mainContent table.blog {
			width: 100% !important;
			max-width: 100% !important;
		}
		#mainContent table[width="494px"],
		#mainContent table[width='494px'] {
			width: 100% !important;
		}
		/* Product grid: force the two-column 235/10/245px layout to stack
		   into a single fluid column on narrow screens. */
		@media (max-width: 640px) {
			#mainContent table[border="0"][cellspacing="0"][cellpadding="0"] > tbody > tr {
				display: block;
				width: 100%;
			}
			#mainContent table[border="0"][cellspacing="0"][cellpadding="0"] > tbody > tr > td[width="235px"],
			#mainContent table[border="0"][cellspacing="0"][cellpadding="0"] > tbody > tr > td[width="245px"] {
				display: block;
				width: 100% !important;
				max-width: 100% !important;
			}
			#mainContent table[border="0"][cellspacing="0"][cellpadding="0"] > tbody > tr > td[width="10px"] {
				display: none;
			}
		}
		.forum {
			width: 67% !important;
			max-width: 67% !important;
			margin: 0 !important;
			box-sizing: border-box;
		}
		img { max-width: 100%; height: auto; }
		/* The page looked shifted/off-center because #container had a
		   malformed inline style (empty margin-top/right/left values,
		   which browsers can mis-parse) and .mainContent's own CSS class
		   uses asymmetric left/right padding (29px vs 15px). On top of a
		   960px desktop canvas that's invisible, but on a narrow phone
		   screen it eats a very different amount of space on each side,
		   making the whole block look pushed to one side. Force equal,
		   small, symmetric spacing on mobile instead. */
		#container {
			margin: 0 auto !important;
			max-width: 100% !important;
			padding: 0 15px !important;
			box-sizing: border-box;
		}
		.mainContent {
			padding-left: 10px !important;
			padding-right: 10px !important;
			box-sizing: border-box;
		}
		/* template.css hardcodes #footer HEIGHT:74px for the old single-row
		   link bar. Now that footer links stack vertically on mobile, that
		   fixed height is too short: the dark background stops at 74px
		   while the extra links overflow below it onto the page's white
		   background, and since footer link text is a pale grey (#e7e7df)
		   they become unreadable. Let the footer grow to fit its content. */
		#footer {
			height: auto !important;
			min-height: 74px;
		}
		/* Categories dropdown menu: categories now live behind a hamburger-style
		   button (first item in the bar) that opens a panel underneath it.
		   NOTE: the external menu.css hides every div inside #sddm
		   (#sddm div is set to visibility:hidden and position:absolute) for its
		   own hover submenus, so all rules below use higher specificity plus
		   !important to win over it. */
		#sddm li.item-categories {
			position: relative !important;
			height: 100% !important;
		}
		#sddm li.item-categories > a {
			cursor: pointer !important;
			position: static !important;
			display: block !important;
			width: 60px !important;
			height: 100% !important;
			min-height: 100% !important;
			padding: 0 !important;
			margin: 0 !important;
			line-height: normal !important;
			box-sizing: border-box !important;
			background: transparent !important;
		}
		.hamburger-icon {
			display: block !important;
			position: absolute !important;
			top: 50% !important;
			left: 50% !important;
			transform: translate(-50%, -50%) !important;
			width: 24px !important;
			height: 18px !important;
			margin: 0 !important;
			background:
				linear-gradient(#FFFFFF, #FFFFFF) left top / 100% 3px no-repeat,
				linear-gradient(#FFFFFF, #FFFFFF) left 50% / 100% 3px no-repeat,
				linear-gradient(#FFFFFF, #FFFFFF) left bottom / 100% 3px no-repeat !important;
		}
		/* the inner i-tag bars are kept in the markup but the icon itself is
		   drawn with background stripes above, so external CSS can't hide it */
		.hamburger-icon i {
			display: none !important;
		}
		#sddm li.item-categories div.categories-panel {
			display: none;
			visibility: visible !important;
			position: absolute;
			top: 100%;
			left: 0;
			margin: 0;
			padding: 0 !important;
			background: #fff !important;
			border: 1px solid #ccc !important;
			min-width: 250px;
			max-width: 90vw;
			max-height: 70vh;
			overflow-y: auto;
			z-index: 9999 !important;
			box-shadow: 0 4px 12px rgba(0,0,0,0.25);
			text-align: left;
		}
		#sddm li.item-categories div.categories-panel.open {
			display: block !important;
		}
		/* Inner blocks also match the menu.css div-hider - neutralise it */
		#sddm li.item-categories div.categories-panel div.cat-block {
			display: block !important;
			visibility: visible !important;
			position: static !important;
			margin: 0 !important;
			padding: 0 !important;
			background: #fff !important;
			border: none !important;
			border-bottom: 1px solid #eee !important;
		}
		#sddm li.item-categories div.categories-panel div.cat-block:last-child {
			border-bottom: none !important;
		}
		#sddm li.item-categories div.categories-panel div.cat-sub {
			display: flex !important;
			visibility: visible !important;
			position: static !important;
			flex-direction: column;
			margin: 0 !important;
			padding: 0 14px 8px 26px !important;
			background: #fff !important;
			border: none !important;
		}
		/* menu.css also restyles links inside #sddm divs - override for our panel */
		#sddm li.item-categories div.categories-panel .cat-block > a {
			display: block !important;
			position: static !important;
			width: auto !important;
			white-space: normal !important;
			margin: 0 !important;
			padding: 10px 14px !important;
			font: bold 13px arial !important;
			color: #333 !important;
			background: #fff !important;
			text-align: left !important;
			text-decoration: none !important;
		}
		#sddm li.item-categories div.categories-panel .cat-block > a:hover {
			background: #f0f0f0 !important;
			color: #000 !important;
		}
		#sddm li.item-categories div.categories-panel .cat-sub a {
			display: block !important;
			position: static !important;
			width: auto !important;
			white-space: normal !important;
			margin: 0 !important;
			padding: 5px 0 !important;
			font: 13px arial !important;
			color: #555 !important;
			background: #fff !important;
			text-align: left !important;
			text-decoration: none !important;
		}
		#sddm li.item-categories div.categories-panel .cat-sub a:hover {
			color: #000 !important;
			text-decoration: underline !important;
		}
		/* "My Account" slide-panel: mirrors the categories panel mechanics,
		   right-aligned to its own button instead of the hamburger. */
		#sddm li.item-account {
			position: relative !important;
			height: 100% !important;
		}
		#sddm li.item-account > a {
			cursor: pointer !important;
			position: relative !important;
			top: -14px !important;
			display: flex !important;
			align-items: center !important;
			width: auto !important;
			height: 100% !important;
			padding: 0 12px !important;
			margin: 0 !important;
			color: #fff !important;
			font: bold 13px arial !important;
			text-decoration: none !important;
			white-space: nowrap !important;
			box-sizing: border-box !important;
			background: transparent !important;
		}
		#sddm li.item-account div.categories-panel {
			display: none;
			visibility: visible !important;
			position: absolute;
			top: 100%;
			left: 0;
			margin: 0;
			padding: 22px !important;
			background: #fff !important;
			border: 1px solid #ccc !important;
			border-radius: 8px !important;
			width: 280px !important;
			min-width: 240px;
			max-width: 92vw;
			max-height: 70vh;
			overflow-y: auto;
			z-index: 9999 !important;
			box-shadow: 0 6px 20px rgba(0,0,0,0.3);
			text-align: left;
			box-sizing: border-box;
		}
		#sddm li.item-account div.categories-panel.open {
			display: block !important;
		}
		#sddm li.item-account div.categories-panel * {
			visibility: visible !important;
			position: static !important;
			float: none !important;
			height: auto !important;
			min-height: 0 !important;
			max-width: none !important;
			line-height: normal !important;
			text-indent: 0 !important;
		}
		#sddm li.item-account div.categories-panel a.panel-close {
			display: block !important;
			position: absolute !important;
			top: 10px !important;
			right: 12px !important;
			width: 26px !important;
			height: 26px !important;
			line-height: 26px !important;
			text-align: center !important;
			font: bold 18px arial !important;
			color: #888 !important;
			background: transparent !important;
			text-decoration: none !important;
			border-radius: 50% !important;
		}
		#sddm li.item-account div.categories-panel a.panel-close:hover {
			color: #333 !important;
			background: #f0f0f0 !important;
		}
		#sddm li.item-account div.categories-panel H3 {
			display: block !important;
			font-size: 18px !important;
			font-weight: bold !important;
			color: #333 !important;
			margin: 0 22px 14px 0 !important;
			padding: 0 !important;
			background: transparent !important;
		}
		#sddm li.item-account div.categories-panel div {
			display: block !important;
			width: auto !important;
			margin: 0 !important;
			padding: 0 !important;
			background: transparent !important;
			border: none !important;
		}
		#sddm li.item-account div.categories-panel div.cab {
			margin: 0 0 10px 0 !important;
		}
		#sddm li.item-account div.categories-panel div.cab:last-child {
			margin-bottom: 0 !important;
			padding-top: 10px !important;
			border-top: 1px solid #eee !important;
			color: #333 !important;
			font: 13px arial !important;
		}
		#sddm li.item-account div.categories-panel div.cab A {
			display: inline !important;
			color: #072f3a !important;
			background: transparent !important;
			font: 13px arial !important;
			text-decoration: underline !important;
		}
		#sddm li.item-account div.categories-panel div.cab A:hover {
			color: #041a20 !important;
		}
		/* "Search / Filter by attributes" slide-panel: same mechanism as
		   My Account, holding the criteria/price filter form that used to
		   live in the old sidebar block. */
		#sddm li.item-search {
			position: relative !important;
			height: 100% !important;
		}
		#sddm li.item-search > a {
			cursor: pointer !important;
			position: relative !important;
			top: -14px !important;
			display: flex !important;
			align-items: center !important;
			width: auto !important;
			height: 100% !important;
			padding: 0 12px !important;
			margin: 0 !important;
			color: #fff !important;
			font: bold 13px arial !important;
			text-decoration: none !important;
			white-space: nowrap !important;
			box-sizing: border-box !important;
			background: transparent !important;
		}
		#sddm li.item-search div.categories-panel {
			display: none;
			visibility: visible !important;
			position: absolute;
			top: 100%;
			left: 0;
			margin: 0;
			padding: 22px !important;
			background: #fff !important;
			border: 1px solid #ccc !important;
			border-radius: 8px !important;
			width: 300px !important;
			min-width: 240px;
			max-width: 92vw;
			max-height: 70vh;
			overflow-y: auto;
			z-index: 9999 !important;
			box-shadow: 0 6px 20px rgba(0,0,0,0.3);
			text-align: left;
			box-sizing: border-box;
		}
		#sddm li.item-search div.categories-panel.open {
			display: block !important;
		}
		#sddm li.item-search div.categories-panel * {
			visibility: visible !important;
			position: static !important;
			float: none !important;
			height: auto !important;
			min-height: 0 !important;
			max-width: none !important;
			line-height: normal !important;
			text-indent: 0 !important;
		}
		#sddm li.item-search div.categories-panel a.panel-close {
			display: block !important;
			position: absolute !important;
			top: 10px !important;
			right: 12px !important;
			width: 26px !important;
			height: 26px !important;
			line-height: 26px !important;
			text-align: center !important;
			font: bold 18px arial !important;
			color: #888 !important;
			background: transparent !important;
			text-decoration: none !important;
			border-radius: 50% !important;
		}
		#sddm li.item-search div.categories-panel a.panel-close:hover {
			color: #333 !important;
			background: #f0f0f0 !important;
		}
		#sddm li.item-search div.categories-panel H3 {
			display: block !important;
			font-size: 18px !important;
			font-weight: bold !important;
			color: #333 !important;
			margin: 0 22px 14px 0 !important;
			padding: 0 !important;
			background: transparent !important;
		}
		#sddm li.item-search div.categories-panel FORM,
		#sddm li.item-search div.categories-panel div {
			display: block !important;
			width: auto !important;
			margin: 0 !important;
			padding: 0 !important;
			background: transparent !important;
			border: none !important;
		}
		#sddm li.item-search div.categories-panel div.sf-row {
			margin: 0 0 12px 0 !important;
		}
		#sddm li.item-search div.categories-panel LABEL {
			display: block !important;
			color: #666 !important;
			font: bold 12px arial !important;
			text-transform: uppercase;
			letter-spacing: .03em;
			margin: 0 0 5px 0 !important;
			background: transparent !important;
		}
		#sddm li.item-search div.categories-panel SELECT,
		#sddm li.item-search div.categories-panel INPUT[type="text"] {
			display: block !important;
			width: 100% !important;
			box-sizing: border-box !important;
			padding: 8px 10px !important;
			margin: 0 !important;
			font-size: 14px !important;
			color: #222 !important;
			background: #fff !important;
			border: 1px solid #ccc !important;
			border-radius: 5px !important;
		}
		#sddm li.item-search div.categories-panel div.sf-buttons {
			margin: 14px 0 0 0 !important;
			padding-top: 12px !important;
			border-top: 1px solid #eee !important;
		}
		#sddm li.item-search div.categories-panel INPUT.button {
			display: inline-block !important;
			width: auto !important;
			margin: 0 8px 0 0 !important;
			padding: 8px 20px !important;
			background: #072f3a !important;
			color: #fff !important;
			border: none !important;
			border-radius: 5px !important;
			font: bold 13px arial !important;
			cursor: pointer !important;
		}
		#sddm li.item-search div.categories-panel INPUT.button[type="reset"] {
			background: #fff !important;
			color: #072f3a !important;
			border: 1px solid #ccc !important;
		}
	</style>	<style type="text/css">
		/* Hard global reset: force every table/td/div in the mainContent
		   ancestor chain to respect viewport width and never expand
		   past it due to unbreakable content. This targets the root
		   cause: HTML tables auto-size columns to fit content unless
		   explicitly told to wrap and cap width. */
		#mainContent, #mainContent table, #mainContent tbody, #mainContent tr, #mainContent div {
			max-width: 100% !important;
			width: 100% !important;
			box-sizing: border-box !important;
			word-wrap: break-word !important;
			overflow-wrap: break-word !important;
			word-break: break-word !important;
			table-layout: fixed !important;
		}
		#mainContent td {
			max-width: 100% !important;
			box-sizing: border-box !important;
			word-wrap: break-word !important;
			overflow-wrap: break-word !important;
			word-break: break-word !important;
		}
		#mainContent img {
			max-width: 100% !important;
			height: auto !important;
		}
	</style>
	<style type="text/css">
		/* ============================================================
		   PG2 — self-contained mobile product grid, independent of the
		   legacy template.css cascade. Every rule below is scoped under
		   .pg2-scope and uses !important so it cannot be overridden by
		   any old desktop-pixel-width rule higher up the page. Flexbox
		   is used because it reliably wraps/stacks content to fit any
		   screen width without needing table-layout tricks.
		   ============================================================ */
		.pg2-scope {
			all: unset !important;
			display: block !important;
			width: 100% !important;
			max-width: 100% !important;
			box-sizing: border-box !important;
			padding: 0 12px !important;
			margin: 0 auto !important;
			overflow: hidden !important;
			font-family: Arial, Helvetica, sans-serif !important;
		}
		.pg2-scope * {
			box-sizing: border-box !important;
			max-width: 100% !important;
		}
		.pg2-grid {
			display: flex !important;
			flex-direction: column !important;
			width: 100% !important;
			gap: 18px !important;
		}
		.pg2-card {
			display: flex !important;
			flex-direction: column !important;
			width: 100% !important;
			padding: 10px !important;
			border-bottom: 1px solid #ddd !important;
		}
		.pg2-title {
			display: block !important;
			font-size: 16px !important;
			font-weight: bold !important;
			text-decoration: underline !important;
			color: #000 !important;
			margin-bottom: 6px !important;
			word-wrap: break-word !important;
			overflow-wrap: break-word !important;
		}
		.pg2-price {
			display: block !important;
			text-align: right !important;
			background-color: #CFCFC8 !important;
			padding: 3px 6px !important;
			margin-bottom: 8px !important;
			font-size: 14px !important;
		}
		.pg2-image-wrap {
			display: block !important;
			width: 45% !important;
			max-width: 45% !important;
			margin: 0 0 10px 0 !important;
		}
		.pg2-image-wrap img {
			display: block !important;
			width: 100% !important;
			height: auto !important;
		}
		.pg2-desc {
			display: block !important;
			font-size: 14px !important;
			line-height: 1.4 !important;
			color: #333 !important;
			text-decoration: none !important;
			word-wrap: break-word !important;
			overflow-wrap: break-word !important;
			word-break: break-word !important;
		}
	</style>


     <LINK id="style2" rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/template.css')"/></xsl:attribute></LINK>
         <LINK rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/constant.css')"/></xsl:attribute></LINK>
         <SCRIPT type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/caption.js')"/></xsl:attribute></SCRIPT>
         <link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/jquery.lightbox.css')"/></xsl:attribute></link>

         <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/jquery.js')"/></xsl:attribute></script>
         <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/jquery.lightbox.js')"/></xsl:attribute></script>

         <link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/menu.css')"/></xsl:attribute></link>
         <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/menu.js')"/></xsl:attribute></script>

     <link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/slider.css')"/></xsl:attribute></link>
         <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/slider.js')"/></xsl:attribute></script>

    <link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/showiframe.css')"/></xsl:attribute></link>
        <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/showiframe.js')"/></xsl:attribute></script>

        <link rel="stylesheet" type="text/css" media="screen"><xsl:attribute name="href"><xsl:value-of select="concat('xsl/',$host,'/webmail.css')"/></xsl:attribute></link>
        <script type="text/javascript"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/webmail.js')"/></xsl:attribute></script>

        <script type="text/javascript">

        var roleId = <xsl:value-of select="document/role_id" disable-output-escaping="yes" />;

        $(function() {
                $('a[@rel*=lightbox]').lightBox({fixedNavigation:true , overlayOpacity: 0.6 , onClose : function() { close(); } }); // Select all links that contains lightbox in the attribute rel
        });



        $(document).ready(function () {
        if( roleId != 0 ) return ;
        //var result = getCookie('lightBoxDisable') ;
        //if (result === undefined) document.getElementById('lightboxdelay').click();
        //alert('Hi');
        });


        function getCookie(name) {
        var matches = document.cookie.match(new RegExp(
    "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
        ));
        return matches ? decodeURIComponent(matches[1]) : undefined;
        }

        function close() {
        //alert('Hi');
        var date = new Date( new Date().getTime() + 360*1000 );
        document.cookie = "lightBoxDisable=ture; path=/; expires="+date.toUTCString();
        }


        // Categories dropdown menu: toggles the panel that now holds all the
        // categories that used to be listed individually in the top bar.
        // Inline styles are set as well because the external menu.css forcibly
        // hides every div inside #sddm for its own hover submenus.
        function openCategoriesPanel(panel, toggleId) {
                panel.classList.add('open');
                panel.style.display = 'block';
                panel.style.visibility = 'visible';
                var toggle = document.getElementById(toggleId);
                var icon = null;
                if (toggle) { icon = toggle.querySelector('.hamburger-icon'); }
                var ref = icon ? icon : toggle;
                if (ref) {
                        var r = ref.getBoundingClientRect();
                        panel.style.position = 'fixed';
                        panel.style.top = Math.round(r.bottom + 14) + 'px';
                        panel.style.marginTop = '0';
                        if (toggleId === 'accountToggle' || toggleId === 'searchFilterToggle') {
                                panel.style.left = 'auto';
                                panel.style.right = Math.max(0, Math.round(window.innerWidth - r.right)) + 'px';
                                panel.style.width = 'auto';
                                panel.style.maxWidth = '92vw';
                        } else if (window.innerWidth &lt;= 768) {
                                panel.style.left = '0px';
                                panel.style.right = 'auto';
                                panel.style.width = '100vw';
                                panel.style.maxWidth = '100vw';
                        } else {
                                panel.style.left = Math.max(0, Math.round(r.left - 10)) + 'px';
                                panel.style.right = 'auto';
                                panel.style.width = 'auto';
                        }
                }
        }
        function closeCategoriesPanel(panel) {
                panel.classList.remove('open');
                panel.style.display = 'none';
                panel.style.visibility = 'hidden';
        }
        function toggleCategoriesMenu(e) {
                if (e) { e.preventDefault(); e.stopPropagation(); }
                var panel = document.getElementById('categoriesPanel');
                if (!panel) return false;
                if (panel.classList.contains('open')) { closeCategoriesPanel(panel); }
                else { openCategoriesPanel(panel, 'categoriesToggle'); }
                return false;
        }
        function toggleAccountMenu(e) {
                if (e) { e.preventDefault(); e.stopPropagation(); }
                var panel = document.getElementById('accountPanel');
                if (!panel) return false;
                if (panel.classList.contains('open')) { closeCategoriesPanel(panel); }
                else { openCategoriesPanel(panel, 'accountToggle'); }
                return false;
        }
        function closeAccountMenu(e) {
                if (e) { e.preventDefault(); e.stopPropagation(); }
                var panel = document.getElementById('accountPanel');
                if (panel) { closeCategoriesPanel(panel); }
                return false;
        }
        function toggleSearchFilterMenu(e) {
                if (e) { e.preventDefault(); e.stopPropagation(); }
                var panel = document.getElementById('searchFilterPanel');
                if (!panel) return false;
                if (panel.classList.contains('open')) { closeCategoriesPanel(panel); }
                else { openCategoriesPanel(panel, 'searchFilterToggle'); }
                return false;
        }
        function closeSearchFilterMenu(e) {
                if (e) { e.preventDefault(); e.stopPropagation(); }
                var panel = document.getElementById('searchFilterPanel');
                if (panel) { closeCategoriesPanel(panel); }
                return false;
        }

        document.addEventListener('click', function (e) {
                [['categoriesPanel', 'categoriesToggle'], ['accountPanel', 'accountToggle'], ['searchFilterPanel', 'searchFilterToggle']].forEach(function (pair) {
                        var panel = document.getElementById(pair[0]);
                        var toggle = document.getElementById(pair[1]);
                        if (!panel || !toggle) return;
                        if (panel.classList.contains('open') &amp;&amp; !panel.contains(e.target) &amp;&amp; !toggle.contains(e.target)) {
                                closeCategoriesPanel(panel);
                        }
                });
        });


        function resetSearchCreForm() {
            top.searchcreform.creteria1_id.value = '0' ;
            top.searchcreform.creteria2_id.value = '0' ;
            top.searchcreform.creteria3_id.value = '0' ;
            top.searchcreform.creteria4_id.value = '0' ;
            top.searchcreform.creteria5_id.value = '0' ;
            top.searchcreform.creteria6_id.value = '0' ;
            top.searchcreform.creteria7_id.value = '0' ;
            top.searchcreform.creteria8_id.value = '0' ;
            top.searchcreform.creteria9_id.value = '0' ;
            top.searchcreform.creteria10_id.value = '0' ;
        }

        </script>


</HEAD>

<body id="body">

<xsl:attribute name="onload">
<xsl:value-of select="concat('setCurrent(',$page,',',$role, ');')"/>
</xsl:attribute>
        <div class="main" style="background-color: #E7E7DF">
                                <IMG alt="Logo"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/logo.gif')"/></xsl:attribute></IMG>
                        </div>
        <div id="gradient">

                <div class="main" style="width: 100%; max-width: 960px; margin: 0 auto;" >

                        <div id="top">

                                        <div id="topmenu">
                                                <div class="module-topmenu">

                                                <!--  <ul class="menu-nav">  -->
                                                <ul id="sddm" >

                                                <!-- Categories used to be listed individually in the top bar;
                                                     they now live behind a hamburger-style menu button (first item in the bar)
                                                     that opens a dropdown panel with all categories and subcategories. -->
                                                <li class="item-categories">
                                                <a href="#" id="categoriesToggle" onclick="return toggleCategoriesMenu(event);">
                                                <span class="hamburger-icon"><i></i><i></i><i></i></span>
                                                </a>
                                                <div id="categoriesPanel" class="categories-panel">
                                                <xsl:for-each select="document/menu/menu-item">
                                                <xsl:if test="item != ''">
                                                <xsl:if test="code != '-1'">
                                                <xsl:if test="code != '-2'">
                                                <xsl:if test="code != '-3'">
                                                <div class="cat-block">
                                                <a>
                                                <xsl:attribute name="HREF"><xsl:value-of select="url"/></xsl:attribute>
                                                <xsl:value-of select="item"/>
                                                </a>
                                                <xsl:if test="count(submenu-item) &gt; 0">
                                                <div class="cat-sub">
                                                <xsl:for-each select="submenu-item">
                                                <a><xsl:attribute name="HREF"><xsl:value-of select="suburl"/></xsl:attribute>
                                                <xsl:value-of select="subitem"/>
                                                </a>
                                                </xsl:for-each>
                                                </div>
                                                </xsl:if>
                                                </div>
                                                </xsl:if>
                                                </xsl:if>
                                                </xsl:if>
                                                </xsl:if>
                                                </xsl:for-each>
                                                </div>
                                                </li>

                                                <!--
                                                <li class="item29">
                                                <a href="Productlist.jsp?catalog_id=-6">
                                                <span>New Arrivals</span>
                                                </a>
                                                </li>

                                                <LI class="item18">
                                                <A href="Productlist.jsp?catalog_id=-10">
                                                <SPAN>Popular</SPAN>
                                                </A>
                                                </LI>
                                                 -->
                                                                                         


                                                <LI class="item18">
                                                <A href="Authorization.jsp?Login=">
                                                <SPAN>Registration</SPAN>
                                                </A>
                                                </LI>

                                                <!-- My Account: same slide-down panel used on the cart page,
                                                     mirrored here so users can jump to their orders/payments
                                                     from anywhere in the catalog. -->
                                                <li class="item-account">
                                                <a href="#" id="accountToggle" onclick="return toggleAccountMenu(event);">
                                                <span>My Account</span>
                                                </a>
                                                <div id="accountPanel" class="categories-panel">
                                                <a href="#" class="panel-close" onclick="return closeAccountMenu(event);">&#215;</a>
                                                <H3>My Account</H3>
                                                <div class="cab">
                                                <a><xsl:attribute name="HREF"><xsl:value-of select="document/to_order_hist"/></xsl:attribute>All orders</a>
                                                </div>
                                                <div class="cab">
                                                <a><xsl:attribute name="HREF"><xsl:value-of select="document/to_order"/></xsl:attribute>The current order</a>
                                                </div>
                                                <div class="cab">
                                                <a><xsl:attribute name="HREF"><xsl:value-of select="document/to_account_history"/></xsl:attribute>Payments</a>
                                                </div>
                                                <div class="cab">
                                                <b>Available funds: </b><xsl:value-of select="document/balans"/>
                                                </div>
                                                </div>
                                                </li>

                                                <!-- Filter by attributes: moved here from the old sidebar block
                                                     into a slide-down panel, same mechanism as My Account. -->
                                                <li class="item-search">
                                                <a href="#" id="searchFilterToggle" onclick="return toggleSearchFilterMenu(event);">
                                                <span>Search</span>
                                                </a>
                                                <div id="searchFilterPanel" class="categories-panel">
                                                <a href="#" class="panel-close" onclick="return closeSearchFilterMenu(event);">&#215;</a>
                                                <H3>Filter by attributes</H3>
                                                <FORM name="searchcreform"  action="Productlist.jsp" method="POST"  onSubmit="top.searchcreform.search_value.value = top.searchform.search_value.value"  >
                                                <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="searchquery" VALUE="16"  ></INPUT>

                                                <xsl:if test="/document/criteria1_label != ''">
                                                <div class="sf-row">
                                                <LABEL for="voteid25"><xsl:value-of select="/document/criteria1_label"/>:</LABEL>
                                                <SELECT NAME = "creteria1_id" onChange="doChengeCreteria('creteria1_id', this.value)">
                                                <xsl:for-each select="document/creteria1/creteria1-item">
                                                <OPTION>
                                                <xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute>
                                                <xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if>
                                                <xsl:value-of select="item"/>
                                                </OPTION>
                                                </xsl:for-each>
                                                </SELECT>
                                                </div>
                                                </xsl:if>

                                                <xsl:if test="/document/criteria2_label != ''">
                                                <div class="sf-row">
                                                <LABEL for="voteid25"><xsl:value-of select="/document/criteria2_label"/>:</LABEL>
                                                <SELECT NAME = "creteria2_id" onChange="doChengeCreteria('creteria2_id', this.value)">
                                                <xsl:for-each select="document/creteria2/creteria2-item">
                                                <OPTION>
                                                <xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute>
                                                <xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if>
                                                <xsl:value-of select="item"/>
                                                </OPTION>
                                                </xsl:for-each>
                                                </SELECT>
                                                </div>
                                                </xsl:if>

                                                <xsl:if test="/document/criteria3_label != ''">
                                                <div class="sf-row">
                                                <LABEL for="voteid25"><xsl:value-of select="/document/criteria3_label"/>:</LABEL>
                                                <SELECT NAME = "creteria3_id" onChange="doChengeCreteria('creteria3_id', this.value)">
                                                <xsl:for-each select="document/creteria3/creteria3-item">
                                                <OPTION>
                                                <xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute>
                                                <xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if>
                                                <xsl:value-of select="item"/>
                                                </OPTION>
                                                </xsl:for-each>
                                                </SELECT>
                                                </div>
                                                </xsl:if>

                                                <xsl:if test="/document/criteria4_label != ''">
                                                <div class="sf-row">
                                                <LABEL for="voteid25"><xsl:value-of select="/document/criteria4_label"/>:</LABEL>
                                                <SELECT NAME = "creteria4_id" onChange="doChengeCreteria('creteria4_id', this.value)">
                                                <xsl:for-each select="document/creteria4/creteria4-item">
                                                <OPTION>
                                                <xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute>
                                                <xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if>
                                                <xsl:value-of select="item"/>
                                                </OPTION>
                                                </xsl:for-each>
                                                </SELECT>
                                                </div>
                                                </xsl:if>

                                                <xsl:if test="/document/criteria5_label != ''">
                                                <div class="sf-row">
                                                <LABEL for="voteid25"><xsl:value-of select="/document/criteria5_label"/>:</LABEL>
                                                <SELECT NAME = "creteria5_id" onChange="doChengeCreteria('creteria5_id', this.value)">
                                                <xsl:for-each select="document/creteria5/creteria5-item">
                                                <OPTION>
                                                <xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute>
                                                <xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if>
                                                <xsl:value-of select="item"/>
                                                </OPTION>
                                                </xsl:for-each>
                                                </SELECT>
                                                </div>
                                                </xsl:if>

                                                <xsl:if test="/document/criteria6_label != ''">
                                                <div class="sf-row">
                                                <LABEL for="voteid25"><xsl:value-of select="/document/criteria6_label"/>:</LABEL>
                                                <SELECT NAME = "creteria6_id" onChange="doChengeCreteria('creteria6_id', this.value)">
                                                <xsl:for-each select="document/creteria6/creteria6-item">
                                                <OPTION>
                                                <xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute>
                                                <xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if>
                                                <xsl:value-of select="item"/>
                                                </OPTION>
                                                </xsl:for-each>
                                                </SELECT>
                                                </div>
                                                </xsl:if>

                                                <xsl:if test="/document/criteria7_label != ''">
                                                <div class="sf-row">
                                                <LABEL for="voteid25"><xsl:value-of select="/document/criteria7_label"/>:</LABEL>
                                                <SELECT NAME = "creteria7_id" onChange="doChengeCreteria('creteria7_id', this.value)">
                                                <xsl:for-each select="document/creteria7/creteria7-item">
                                                <OPTION>
                                                <xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute>
                                                <xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if>
                                                <xsl:value-of select="item"/>
                                                </OPTION>
                                                </xsl:for-each>
                                                </SELECT>
                                                </div>
                                                </xsl:if>

                                                <xsl:if test="/document/criteria8_label != ''">
                                                <div class="sf-row">
                                                <LABEL for="voteid25"><xsl:value-of select="/document/criteria8_label"/>:</LABEL>
                                                <SELECT NAME = "creteria8_id" onChange="doChengeCreteria('creteria8_id', this.value)">
                                                <xsl:for-each select="document/creteria8/creteria8-item">
                                                <OPTION>
                                                <xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute>
                                                <xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if>
                                                <xsl:value-of select="item"/>
                                                </OPTION>
                                                </xsl:for-each>
                                                </SELECT>
                                                </div>
                                                </xsl:if>

                                                <xsl:if test="/document/criteria9_label != ''">
                                                <div class="sf-row">
                                                <LABEL for="voteid25"><xsl:value-of select="/document/criteria9_label"/>:</LABEL>
                                                <SELECT NAME = "creteria9_id" onChange="doChengeCreteria('creteria9_id', this.value)">
                                                <xsl:for-each select="document/creteria9/creteria9-item">
                                                <OPTION>
                                                <xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute>
                                                <xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if>
                                                <xsl:value-of select="item"/>
                                                </OPTION>
                                                </xsl:for-each>
                                                </SELECT>
                                                </div>
                                                </xsl:if>

                                                <xsl:if test="/document/criteria10_label != ''">
                                                <div class="sf-row">
                                                <LABEL for="voteid25"><xsl:value-of select="/document/criteria10_label"/>:</LABEL>
                                                <SELECT NAME = "creteria10_id" onChange="doChengeCreteria('creteria10_id', this.value)">
                                                <xsl:for-each select="document/creteria10/creteria10-item">
                                                <OPTION>
                                                <xsl:attribute name="value"><xsl:value-of select="code"/></xsl:attribute>
                                                <xsl:if test="code = selected"><xsl:attribute name="SELECTED">SELECTED</xsl:attribute></xsl:if>
                                                <xsl:value-of select="item"/>
                                                </OPTION>
                                                </xsl:for-each>
                                                </SELECT>
                                                </div>
                                                </xsl:if>

                                                <div class="sf-row">
                                                <LABEL for="voteid25">Price from:</LABEL>
                                                <INPUT name="fromcost" size="7" type="text"><xsl:attribute name="value"><xsl:value-of select="/document/fromcost"/></xsl:attribute></INPUT>
                                                </div>
                                                <div class="sf-row">
                                                <LABEL for="voteid25">Price to:</LABEL>
                                                <INPUT name="tocost" size="7" type="text"><xsl:attribute name="value"><xsl:value-of select="/document/tocost"/></xsl:attribute></INPUT>
                                                </div>

                                                <INPUT name="search_value"  type="hidden" ></INPUT>
                                                <div class="sf-buttons">
                                                <INPUT class="button"  type="submit" value="Search"  tabindex="30002" />
                                                <INPUT class="button"  type="reset" value="Clean"  tabindex="30003" />
                                                </div>
                                                </FORM>
                                                </div>
                                                </li>

                                         <LI>
                                         <A href="Order.jsp">
                                         <IMG border="0" height="40" width="40"  style="margin: -10px;" >
                                         <xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/empty-cart-light.png')"/></xsl:attribute></IMG>
                                         </A>

                                         </LI>



                                         <xsl:if test="document/role_id != 0">
                                         <LI ><A href="Productlist.jsp?action=logoff"  >
                                         <SPAN >
                                         <svg xmlns="http://www.w3.org/2000/svg"  height="24px" viewBox="0 -960 960 960" width="24px" fill="#F3F3F3"><path d="M212-86q-53 0-89.5-36.5T86-212v-536q0-53 36.5-89.5T212-874h276v126H212v536h276v126H212Zm415-146-88-89 96-96H352v-126h283l-96-96 88-89 247 248-247 248Z"/>
                                          <title id="title">Exit</title>
                                         </svg>
                                         </SPAN>
                                         </A>
                                         </LI>
                                         </xsl:if>



                                         <!--
                                         <div style="padding:5px 2px 0px;" >
                                         <A href="Order.jsp">
                                         <IMG border="0" height="40" width="40"  style="margin: 15px;" >
                                         <xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/empty-cart-light.png')"/></xsl:attribute></IMG>
                                         </A>
                                         </div>
                                           -->

                                                </ul>
                                                </div>
                                </div>
                        </div>



                            <DIV class="indent" style="width: 100%; max-width: 960px; box-sizing: border-box;" >
                                <DIV class="moduletable">
                                        <TABLE class="who_is_online" style="WIDTH: auto" align="right">
                                                <TBODY>
                                                        <TR>
                                                                <TD>
                                                                <xsl:if test="document/login != ''">   <!--  showbar if logged in -->
                                                                        <B>User</B>
                                                                         <a href="Authorization.jsp" style="margin-left: 5px; text-decoration: none">

                                                                        <xsl:if test="document/login = 'user'">   <!--  guest -->
                                                                        <font class="user0">
                                                                                <xsl:value-of select="document/login"/>
                                                                        </font>
                                                                        </xsl:if>

                                                                        <xsl:if test="document/login != 'user'">   <!--  registered -->
                                                                        <xsl:if test="document/role_id = 1"> <!--  role 1 -->
                                                                                <font class="user0">
                                                                                        <xsl:value-of select="document/login"/>
                                                                        </font>
                                                                </xsl:if>

                                                                <xsl:if test="document/role_id = 2"><!--  role 2 -->
                                                                        <font class="user0">
                                                                        <xsl:value-of select="document/login"/>
                                                                        </font>
                                                                </xsl:if>
                                                                </xsl:if>

                                                        </a>
                                                                        </xsl:if>
                                                                </TD>
                                                        </TR>
                                                </TBODY>
                                </TABLE>
                                </DIV>
                         </DIV>



                        <div id="mid">
                                <div class="mid-left">
                                        <div class="mid-right">

                                                <div id="search">
                                                        <div class="module-search">
                                                                <FORM name="searchform"  action="Productlist.jsp"  method="POST">
                                                                <div class="search">
                                                                <INPUT class="inputbox"
                                                                id="search_value"
                                                                name="search_value"
                                                                type="text"
                                                                size="20"
                                                                alt="It is search by name goods"
                                                                title="It is search by name goods">
                                                                <xsl:attribute name="value">
                                                                <xsl:value-of select="document/search_value"/>
                                                                </xsl:attribute>
                                                                </INPUT>
                                                                <INPUT  class="button" type="image" value="Search" onClick="return top.search_word();return true"><xsl:attribute name="src"><xsl:value-of select="concat('xsl/',$host,'/images/searchButton.gif')"/></xsl:attribute></INPUT>
                                                        </div>
                                                        <INPUT id="search_char"  name="search_char" type="hidden" ></INPUT>
                                                                <INPUT id="searchquery"  name="searchquery" type="hidden" ></INPUT>
                                                                <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="offset" VALUE="0"  ></INPUT>
                                                        </FORM>
                                                </div>
                                                </div>


                                           <div id="breadcrumb">
                                                   <div class="space">
                                               <span class="breadcrumbs pathway">

                                                       <a href="Productlist.jsp?catalog_id=-2" class="catalog" alt="To return back  to the top of Categorization" title="To return back  to the top of Categorization">
                                        <U><font size="2" >All Categories</font></U>
                                      </a>&#160; &#187;

                                                      <xsl:if test="count(document/parent/parent-item) &gt; 0">


                                                                        <xsl:for-each select="document/parent/parent-item">
                                                                                         <xsl:if test="code != '-2'">
                                                                                                <A ><xsl:attribute name="HREF"><xsl:value-of select="url"/></xsl:attribute>
                                                                                                <U><font size="2" > <xsl:value-of select="item"/></font> </U>
                                                                                            </A>&#160; &#187;
                                                                                         </xsl:if>
                                                                        </xsl:for-each>

                                                      </xsl:if>


                                               </span>
                                               </div>
                                           </div>


                                        </div>
                                </div>
                        </div>



                        <div id="content">
                                <div class="width">


<div id="container" style="margin: 0px auto;" >
<div class="comp-cont">
                <table class="blog" cellpadding="0" cellspacing="0">
                        <tr>
                        <td valign="top">
                                <div class="article-bg">
                                        <div class="article-left">
                                                <div class="article-right">


                                                             <TABLE height="20" cellSpacing="0" cellPadding="0"  >
                                                      <TBODY>
                                                                      <TR>
                                                                            <TD align="center">
                                                                                            <!-- ADMIN edit button, redirects -->
                                                                                            <xsl:if test="document/role_id = '2'">                                                                               
                                                                                                                <div style="font-size: 12px">
                                                                                                                Button to edit the site<br/>
                                                                                                                        <div class="change">
                                                                                                                        <A ><xsl:attribute name="HREF"><xsl:value-of select="document/admin/post_manager"/>
                                                                                                                        </xsl:attribute><DIV >Menu</DIV></A>
                                                                                                                        </div>
                                                                                                            </div>
                                                                                            </xsl:if>
                                                                                    </TD>
                                                                                  </TR>
                                                              
                                                     </TBODY>
                                                   </TABLE>



                                    <DIV  id="mainContent" class="mainContent" style="width: 100%; max-width: 100%;">

                                    <TABLE class="blog" cellSpacing="0" cellPadding="0"  style="width: 100%; max-width: 100%;">
                                      <TBODY>
                                      <TR>
                                        <TD vAlign="top">
                                                          <TABLE cellSpacing="0" cellPadding="0" width="100%">
                                                                    <TBODY>
                                                                                    <xsl:if test="document/empty_page = 'true'" >
                                                                                                                <TR>
                                                                                                          <TD vAlign="top">
                                                                                                                        On this page there is nothing
                                                                                                                         <br/>
                                                                                                                        New Arrival
                                                                                                                 <div class="line2"></div><br/>

                                                                                                                        <div class="scroll-container">
                                                                                                                          <button class="scroll-left" onclick="leftScrollNewArrival()">&#9664;</button>
                                                                                                                           <div class="scroll-content-new-arrival">
                                                                                                                                          <xsl:if test="count(document/newslist/news) != 0">
                                                                                                                          <xsl:for-each select="document/newslist/news">
                                                                                                                          <div class="item">
                                                                                                                            <LI>
                                                                                                                                                <A><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute>
                                                                                                                                                     <SPAN>
                                                                                                                                                       <xsl:if test="image != ''" >
                                                                                                                                                                   <img  alt=""  width="74" border="0">
                                                                                                                                                               <xsl:attribute name="src">
                                                                                                                                                               <xsl:value-of select="image"/>
                                                                                                                                                               </xsl:attribute>
                                                                                                                                                               </img>
                                                                                                                                                               <br/>
                                                                                                                                                       <br/>
                                                                                                                                                     </xsl:if>
                                                                                                                                                     <xsl:for-each select="description/r">
                                                                                                                                                        <xsl:value-of select="."/><BR/>
                                                                                                                                                     </xsl:for-each></SPAN><br/>
                                                                                                                                                 </A>

                                                                                                                                                <xsl:if test="/document/admin/post_manager != ''">
                                                                                                                                                        <table style="width:150px">
                                                                                                                                                            <tbody>
                                                                                                                                                                <tr style="padding-bottom: 15px">
                                                                                                                                                                 <td style="padding-bottom: 5px">
                                                                                                                                                                 <form name="product_del" style="width:50px" action="Productlist.jsp" method="POST">
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
                                                                                                                                                                        <INPUT class="button"  TYPE="submit" name="submit" value="Delete"></INPUT>
                                                                                                                                                                 </form>
                                                                                                                                                                 </td>
                                                                                                                                                                 <td width="5px"></td>
                                                                                                                                                                 <td>

                                                                                                                                                                <form name="product_edit" style="width:50px"  action="Productlist.jsp" method="POST">
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="news"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
                                                                                                                                                                        <INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
                                                                                                                                                                </form>
                                                                                                                                                                 </td>
                                                                                                                                                                </tr>
                                                                                                                                                            </tbody>
                                                                                                                                                        </table>
                                                                                                                                                </xsl:if>
                                                                                                                            </LI>
                                                                                                                            </div>
                                                                                                                          </xsl:for-each>
                                                                                                                          </xsl:if>
                                                                                                                                   </div>
                                                                                                                          <button class="scroll-right" onclick="rightScrollNewArrival()">&#9654;</button>
                                                                                                                        </div>


                                                                                                                        <br/>
                                                                                                                        Recommended items
                                                                                                                <div class="line2"></div><br/>

                                                                                                                        <div class="scroll-container">
                                                                                                                          <button class="scroll-left" onclick="leftScrollRecommendedItems()">&#9664;</button>
                                                                                                                           <div class="scroll-content-recommended-items">
                                                                                                                                          <xsl:if test="count(document/coproductlist1/coproduct1) != 0">
                                                                                                                          <xsl:for-each select="document/coproductlist1/coproduct1">
                                                                                                                          <div class="item">
                                                                                                                            <LI>
                                                                                                                                                <A><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute>
                                                                                                                                                     <SPAN>
                                                                                                                                                       <xsl:if test="image != ''" >
                                                                                                                                                                   <img  alt=""  width="74" border="0">
                                                                                                                                                               <xsl:attribute name="src">
                                                                                                                                                               <xsl:value-of select="image"/>
                                                                                                                                                               </xsl:attribute>
                                                                                                                                                               </img>
                                                                                                                                                               <br/>
                                                                                                                                                       <br/>
                                                                                                                                                     </xsl:if>
                                                                                                                                                     <xsl:for-each select="description/r">
                                                                                                                                                        <xsl:value-of select="."/><BR/>
                                                                                                                                                     </xsl:for-each></SPAN><br/>
                                                                                                                                                 </A>

                                                                                                                                                <xsl:if test="/document/admin/post_manager != ''">
                                                                                                                                                        <table style="width:150px">
                                                                                                                                                            <tbody>
                                                                                                                                                                <tr style="padding-bottom: 15px">
                                                                                                                                                                 <td style="padding-bottom: 5px">
                                                                                                                                                                 <form name="product_del" style="width:50px" action="Productlist.jsp" method="POST">
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
                                                                                                                                                                        <INPUT class="button"  TYPE="submit" name="submit" value="Delete"></INPUT>
                                                                                                                                                                 </form>
                                                                                                                                                                 </td>
                                                                                                                                                                 <td width="5px"></td>
                                                                                                                                                                 <td>

                                                                                                                                                                <form name="product_edit" style="width:50px"  action="Productlist.jsp" method="POST">
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="news"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
                                                                                                                                                                        <INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
                                                                                                                                                                </form>
                                                                                                                                                                 </td>
                                                                                                                                                                </tr>
                                                                                                                                                            </tbody>
                                                                                                                                                        </table>
                                                                                                                                                </xsl:if>
                                                                                                                            </LI>
                                                                                                                            </div>
                                                                                                                          </xsl:for-each>
                                                                                                                          </xsl:if>
                                                                                                                                   </div>
                                                                                                                          <button class="scroll-right" onclick="rightScrollRecommendedItems()">&#9654;</button>
                                                                                                                        </div>

                                                                                                                        <br/>
                                                                                                                        Sponsored by seller
                                                                                                                <div class="line2"></div><br/>

                                                                                                                        <div class="scroll-container">
                                                                                                                          <button class="scroll-left" onclick="leftScrollSponsoredBySeller()">&#9664;</button>
                                                                                                                           <div class="scroll-content-sponsored-by-seller">
                                                                                                                                          <xsl:if test="count(document/coproductlist2/coproduct2) != 0">
                                                                                                                          <xsl:for-each select="document/coproductlist2/coproduct2">
                                                                                                                          <div class="item">
                                                                                                                            <LI>
                                                                                                                                                <A><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute>
                                                                                                                                                     <SPAN>
                                                                                                                                                       <xsl:if test="image != ''" >
                                                                                                                                                                   <img  alt=""  width="74" border="0">
                                                                                                                                                               <xsl:attribute name="src">
                                                                                                                                                               <xsl:value-of select="image"/>
                                                                                                                                                               </xsl:attribute>
                                                                                                                                                               </img>
                                                                                                                                                               <br/>
                                                                                                                                                       <br/>
                                                                                                                                                     </xsl:if>
                                                                                                                                                     <xsl:for-each select="description/r">
                                                                                                                                                        <xsl:value-of select="."/><BR/>
                                                                                                                                                     </xsl:for-each></SPAN><br/>
                                                                                                                                                 </A>

                                                                                                                                                <xsl:if test="/document/admin/post_manager != ''">
                                                                                                                                                        <table style="width:150px">
                                                                                                                                                            <tbody>
                                                                                                                                                                <tr style="padding-bottom: 15px">
                                                                                                                                                                 <td style="padding-bottom: 5px">
                                                                                                                                                                 <form name="product_del" style="width:50px" action="Productlist.jsp" method="POST">
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
                                                                                                                                                                        <INPUT class="button"  TYPE="submit" name="submit" value="Delete"></INPUT>
                                                                                                                                                                 </form>
                                                                                                                                                                 </td>
                                                                                                                                                                 <td width="5px"></td>
                                                                                                                                                                 <td>

                                                                                                                                                                <form name="product_edit" style="width:50px"  action="Productlist.jsp" method="POST">
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="news"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
                                                                                                                                                                        <INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
                                                                                                                                                                </form>
                                                                                                                                                                 </td>
                                                                                                                                                                </tr>
                                                                                                                                                            </tbody>
                                                                                                                                                        </table>
                                                                                                                                                </xsl:if>
                                                                                                                            </LI>
                                                                                                                            </div>
                                                                                                                          </xsl:for-each>
                                                                                                                          </xsl:if>
                                                                                                                                   </div>
                                                                                                                          <button class="scroll-right" onclick="rightScrollSponsoredBySeller()">&#9654;</button>
                                                                                                                        </div>

                                                                                                                  </TD>
                                                                                                                </TR>
                                                                                                </xsl:if>

                                                                                                        <xsl:if test="document/empty_page != 'true'" >
                                                                                            <TR>
                                                                                              <TD class="article_column" vAlign="top" width="100%">
                                                                                                                                                                                                                 
                                                                                                                   <!--
                                                                                                                   <DIV class="products_line3">
                                                                                                                    <DIV class="products_line2">
                                                                                                                     <DIV class="products">
                                                                                                                     -->
                                                                                                                                <xsl:if test="document/empty_page != 'true'" >
                                                                                                                                                                        <div id="product-grid-wrap" class="pg2-scope">
                                                                                                                                                                        <xsl:attribute name="data-offset"><xsl:value-of select="$page"/></xsl:attribute>
                                                                                                                                                                        <xsl:attribute name="data-catalog-id"><xsl:value-of select="document/catalog_id"/></xsl:attribute>
                                                                                                                                                                        <div class="pg2-grid">
                                                                                                                                                                        <!-- pg2: table removed, using flex grid -->
                                                                                                                                                                                <xsl:call-template name="prList">
                                                                                                                                                                        <xsl:with-param name="prCount" select="count(document/product_list/product)"/>
                                                                                                                                                                </xsl:call-template>
                                                                                                                                                                        </div></div>
                                                                                                                                                                        
                                                                                                                                                                        <div id="scroll-sentinel" style="height: 1px;"></div>
                                                                                                                                                                        <div id="scroll-loading" style="display:none; text-align:center; padding: 15px; font-size: 13px; color: #777;">Loading more…</div>
                                                                                                                                                        </xsl:if>
                                                                                                                       <!--
                                                                                                                       </DIV>
                                                                                                                       </DIV>
                                                                                                                       </DIV>
                                                                                                                        -->

                                                                                                                                                                                                                 <br/>
                                                                                                                        New Arrival
                                                                                                                 <div class="line2"></div><br/>

                                                                                                                        <div class="scroll-container">
                                                                                                                          <button class="scroll-left" onclick="leftScrollNewArrival()">&#9664;</button>
                                                                                                                           <div class="scroll-content-new-arrival">
                                                                                                                                          <xsl:if test="count(document/newslist/news) != 0">
                                                                                                                          <xsl:for-each select="document/newslist/news">
                                                                                                                          <div class="item">
                                                                                                                            <LI>
                                                                                                                                                <A><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute>
                                                                                                                                                     <SPAN>
                                                                                                                                                       <xsl:if test="image != ''" >
                                                                                                                                                                   <img  alt=""  width="74" border="0">
                                                                                                                                                               <xsl:attribute name="src">
                                                                                                                                                               <xsl:value-of select="image"/>
                                                                                                                                                               </xsl:attribute>
                                                                                                                                                               </img>
                                                                                                                                                               <br/>
                                                                                                                                                       <br/>
                                                                                                                                                     </xsl:if>
                                                                                                                                                     <xsl:for-each select="description/r">
                                                                                                                                                        <xsl:value-of select="."/><BR/>
                                                                                                                                                     </xsl:for-each></SPAN><br/>
                                                                                                                                                 </A>

                                                                                                                                                <xsl:if test="/document/admin/post_manager != ''">
                                                                                                                                                        <table style="width:150px">
                                                                                                                                                            <tbody>
                                                                                                                                                                <tr style="padding-bottom: 15px">
                                                                                                                                                                 <td style="padding-bottom: 5px">
                                                                                                                                                                 <form name="product_del" style="width:50px" action="Productlist.jsp" method="POST">
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
                                                                                                                                                                        <INPUT class="button"  TYPE="submit" name="submit" value="Delete"></INPUT>
                                                                                                                                                                 </form>
                                                                                                                                                                 </td>
                                                                                                                                                                 <td width="5px"></td>
                                                                                                                                                                 <td>

                                                                                                                                                                <form name="product_edit" style="width:50px"  action="Productlist.jsp" method="POST">
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="news"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
                                                                                                                                                                        <INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
                                                                                                                                                                </form>
                                                                                                                                                                 </td>
                                                                                                                                                                </tr>
                                                                                                                                                            </tbody>
                                                                                                                                                        </table>
                                                                                                                                                </xsl:if>
                                                                                                                            </LI>
                                                                                                                            </div>
                                                                                                                          </xsl:for-each>
                                                                                                                          </xsl:if>
                                                                                                                                   </div>
                                                                                                                          <button class="scroll-right" onclick="rightScrollNewArrival()">&#9654;</button>
                                                                                                                        </div>


                                                                                                                        <br/>
                                                                                                                        Recommended items
                                                                                                                <div class="line2"></div><br/>

                                                                                                                        <div class="scroll-container">
                                                                                                                          <button class="scroll-left" onclick="leftScrollRecommendedItems()">&#9664;</button>
                                                                                                                           <div class="scroll-content-recommended-items">
                                                                                                                                          <xsl:if test="count(document/coproductlist1/coproduct1) != 0">
                                                                                                                          <xsl:for-each select="document/coproductlist1/coproduct1">
                                                                                                                          <div class="item">
                                                                                                                            <LI>
                                                                                                                                                <A><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute>
                                                                                                                                                     <SPAN>
                                                                                                                                                       <xsl:if test="image != ''" >
                                                                                                                                                                   <img  alt=""  width="74" border="0">
                                                                                                                                                               <xsl:attribute name="src">
                                                                                                                                                               <xsl:value-of select="image"/>
                                                                                                                                                               </xsl:attribute>
                                                                                                                                                               </img>
                                                                                                                                                               <br/>
                                                                                                                                                       <br/>
                                                                                                                                                     </xsl:if>
                                                                                                                                                     <xsl:for-each select="description/r">
                                                                                                                                                        <xsl:value-of select="."/><BR/>
                                                                                                                                                     </xsl:for-each></SPAN><br/>
                                                                                                                                                 </A>

                                                                                                                                                <xsl:if test="/document/admin/post_manager != ''">
                                                                                                                                                        <table style="width:150px">
                                                                                                                                                            <tbody>
                                                                                                                                                                <tr style="padding-bottom: 15px">
                                                                                                                                                                 <td style="padding-bottom: 5px">
                                                                                                                                                                 <form name="product_del" style="width:50px" action="Productlist.jsp" method="POST">
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
                                                                                                                                                                        <INPUT class="button"  TYPE="submit" name="submit" value="Delete"></INPUT>
                                                                                                                                                                 </form>
                                                                                                                                                                 </td>
                                                                                                                                                                 <td width="5px"></td>
                                                                                                                                                                 <td>

                                                                                                                                                                <form name="product_edit" style="width:50px"  action="Productlist.jsp" method="POST">
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="news"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
                                                                                                                                                                        <INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
                                                                                                                                                                </form>
                                                                                                                                                                 </td>
                                                                                                                                                                </tr>
                                                                                                                                                            </tbody>
                                                                                                                                                        </table>
                                                                                                                                                </xsl:if>
                                                                                                                            </LI>
                                                                                                                            </div>
                                                                                                                          </xsl:for-each>
                                                                                                                          </xsl:if>
                                                                                                                                   </div>
                                                                                                                          <button class="scroll-right" onclick="rightScrollRecommendedItems()">&#9654;</button>
                                                                                                                        </div>

                                                                                                                        <br/>
                                                                                                                        Sponsored by seller
                                                                                                                <div class="line2"></div><br/>

                                                                                                                        <div class="scroll-container">
                                                                                                                          <button class="scroll-left" onclick="leftScrollSponsoredBySeller()">&#9664;</button>
                                                                                                                           <div class="scroll-content-sponsored-by-seller">
                                                                                                                                          <xsl:if test="count(document/coproductlist2/coproduct2) != 0">
                                                                                                                          <xsl:for-each select="document/coproductlist2/coproduct2">
                                                                                                                          <div class="item">
                                                                                                                            <LI>
                                                                                                                                                <A><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute>
                                                                                                                                                     <SPAN>
                                                                                                                                                       <xsl:if test="image != ''" >
                                                                                                                                                                   <img  alt=""  width="74" border="0">
                                                                                                                                                               <xsl:attribute name="src">
                                                                                                                                                               <xsl:value-of select="image"/>
                                                                                                                                                               </xsl:attribute>
                                                                                                                                                               </img>
                                                                                                                                                               <br/>
                                                                                                                                                       <br/>
                                                                                                                                                     </xsl:if>
                                                                                                                                                     <xsl:for-each select="description/r">
                                                                                                                                                        <xsl:value-of select="."/><BR/>
                                                                                                                                                     </xsl:for-each></SPAN><br/>
                                                                                                                                                 </A>

                                                                                                                                                <xsl:if test="/document/admin/post_manager != ''">
                                                                                                                                                        <table style="width:150px">
                                                                                                                                                            <tbody>
                                                                                                                                                                <tr style="padding-bottom: 15px">
                                                                                                                                                                 <td style="padding-bottom: 5px">
                                                                                                                                                                 <form name="product_del" style="width:50px" action="Productlist.jsp" method="POST">
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
                                                                                                                                                                        <INPUT class="button"  TYPE="submit" name="submit" value="Delete"></INPUT>
                                                                                                                                                                 </form>
                                                                                                                                                                 </td>
                                                                                                                                                                 <td width="5px"></td>
                                                                                                                                                                 <td>

                                                                                                                                                                <form name="product_edit" style="width:50px"  action="Productlist.jsp" method="POST">
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="news"  ></INPUT>
                                                                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
                                                                                                                                                                        <INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
                                                                                                                                                                </form>
                                                                                                                                                                 </td>
                                                                                                                                                                </tr>
                                                                                                                                                            </tbody>
                                                                                                                                                        </table>
                                                                                                                                                </xsl:if>
                                                                                                                            </LI>
                                                                                                                            </div>
                                                                                                                          </xsl:for-each>
                                                                                                                          </xsl:if>
                                                                                                                                   </div>
                                                                                                                          <button class="scroll-right" onclick="rightScrollSponsoredBySeller()">&#9654;</button>
                                                                                                                        </div>

                                                                                                        </TD>
                                                                                                </TR>
                                                                                                </xsl:if>
                                                                          </TBODY>
                                                          </TABLE>

                                                </TD>
                                          </TR>
                                          </TBODY>

                                          </TABLE>
                                          </DIV>
                                                                <span class="article_separator"></span>

                                                        </div>
                                                </div>
                                        </div>
                                </td>
                        </tr>

                <tr >
                 <td >
                  <DIV class="module_forum" style="margin-right:0px;">
                  <DIV class="first_forum">
                  <DIV class="sec_forum">
                  <H3>Products review<xsl:value-of select="document/product/name"/></H3>
                  <DIV>
                  <DIV class="forum" style="width:67%; max-width:67%; margin: 0; box-sizing: border-box;" >
                  <DIV>
                  <DIV>

                        <TABLE width="100%" style="table-layout: fixed; width: 100%; max-width: 100%;">
                                <TBODY>
                                        <TR>
                                                <TD class="over" id="forum" style="word-wrap: break-word; overflow-wrap: break-word;">
                                                <UL>
                                                <xsl:for-each select="document/product_blog_list/product_blog">
                                                <LI  width="100%" style="padding-bottom:20px">

                                                <table width="100%" style="table-layout: fixed; width: 100%; max-width: 100%;">
                                                        <tr>
                                                                <td colspan="2" style="padding-bottom:5px; word-wrap: break-word; overflow-wrap: break-word;">
                                                                <A class="menu"><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute><b><xsl:value-of select="parent_title"/></b></A>   
                                                                </td>
                                                        </tr>
                                                        <tr>
                                                                <td colspan="2">
                                                                <div style="display: flex !important; justify-content: space-between !important; align-items: flex-end !important; width: 100% !important;">
                                                                <span>
                                                                <IMG border="0" height="20" width="20" style="margin-right: 5px; vertical-align: middle;">
                                                                <xsl:attribute name="src">
                                                                <xsl:value-of select="concat('xsl/',$host,'/images/user1.png')"/>
                                                                </xsl:attribute>
                                                                </IMG>
                                                                <em><xsl:value-of select="author"/></em>
                                                                </span>
                                                                <span style="color: #4C4B49; font-size: 10px; white-space: nowrap; text-align: right;">
                                                                Added: <xsl:value-of select="cdate"/>
                                                                </span>
                                                                </div>
                                                                </td>
                                                        </tr>
                                                        <tr>
                                                                <td colspan="2" style="FONT: 11px Arial; COLOR: #474646; word-wrap: break-word; overflow-wrap: break-word;">
                                                                <b><xsl:value-of select="name"/></b>
                                                                <br/>
                                                                <xsl:for-each select="description/r">
                                                                <xsl:value-of select="."/>
                                                                </xsl:for-each>
                                                                </td>
                                                        </tr>
                                                </table>



                                                                                   <!-- ADMIN edit/delete -->
                                                                                <xsl:choose>
                                                                                <xsl:when test="/document/role_id = 2">
                                                                                        <table style="width:200px; padding-left: 20px">
                                                                                            <tbody>
                                                                                                <tr>
                                                                                                        <td style="padding-left: 30px; padding-right: 16px;padding-top: 10px;">                                  
                                                                                                                        <form name="product_del"  action="Productlist.jsp" method="POST">
                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
                                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
                                                                                                                        <INPUT class="button" TYPE="submit" name="submit" value="Delete"></INPUT>
                                                                                                                        </form>
                                                                                                         </td>
                                                                                                     <td style="padding-top: 10px;">
                                                                                                                    <form name="product_edit"  action="Productlist.jsp" method="POST">
                                                                                                                                <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
                                                                                                                                <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="blog" ></INPUT>
                                                                                                                                <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
                                                                                                                                <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_parent_id"><xsl:attribute name="value"><xsl:value-of select="product_parent_id"/></xsl:attribute></INPUT>
                                                                                                                                <INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
                                                                                                                    </form>
                                                                                                         </td>
                                                                                                </tr>
                                                                                            </tbody>
                                                                                        </table>
                                                                                </xsl:when>
                                                                                <xsl:otherwise>
                                                                                <xsl:if  test="user_id = string(number($user_id))">
                                                                                <xsl:if test="/document/role_id = '1'">
                                                                                <table style="width:200px; padding-left: 20px">
                                                                                        <tbody>
                                                                                        <tr>
                                                                                        <td style="padding-left: 30px; padding-right: 16px;padding-top: 10px;">
                                                                                    <form name="product_del"  action="Productlist.jsp" method="POST">
                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
                                                                                                        <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
                                                                                                        <INPUT class="button" TYPE="submit" name="submit" value="Delete"></INPUT>
                                                                                            </form>
                                                                                        </td>
                                                                                         <td style="padding-top: 10px;">
                                                                                         <form name="product_edit"  action="Productlist.jsp" method="POST">
                                                                                                                <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
                                                                                                                <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="blog" ></INPUT>
                                                                                                                <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
                                                                                                                <INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_parent_id"><xsl:attribute name="value"><xsl:value-of select="product_parent_id"/></xsl:attribute></INPUT>
                                                                                                                <INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
                                                                                                </form>
                                                                                        </td>
                                                                                </tr>
                                                                                </tbody>
                                                                             </table>
                                                                             </xsl:if>
                                                                             </xsl:if>
                                                                             </xsl:otherwise>
                                                                             </xsl:choose>
                                                                             <SPAN class="article_separator"></SPAN>
                                                        </LI>
                                                        </xsl:for-each>
                                                        </UL>
                                                        </TD>
                                                        </TR>
                                                        </TBODY>
                                                        </TABLE>
                                                                                            </DIV>
                                                                                            </DIV>
                                                                                            </DIV>
                                                                                        </DIV>
                                                                </DIV>
                                                            </DIV>
                                                            </DIV>

                  </td>
                </tr>
        </table>

</div>
</div>



                                </div>
                        </div>
                </div>
        </div>

        <DIV id="footer">
                <DIV class="main">
                <div class="space">
                <p align="center">
<div style="width:100%; text-align:center; padding: 0 10px; box-sizing: border-box;">
	<xsl:for-each select="document/bottomlist/bottom">
		<div style="padding: 6px 0;">
				<A ><xsl:attribute name="HREF"><xsl:value-of select="policy_url"/></xsl:attribute>
			      <U>
				      <xsl:value-of select="name"/>
			      </U>
				</A>


			<xsl:if test="/document/admin/post_manager != ''">
				<br/>
				<table style="margin: 0 auto;">
				    <tbody>
					<tr>
					 <td>
					       <form name="product_edit"  action="Productlist.jsp">
						<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
						<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="bottom"  ></INPUT>
						<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
						<INPUT TYPE="submit" name="submit" value="Change"></INPUT>
						</form>
					 </td>
					 <td>
								<form name="product_del"  action="Productlist.jsp">
						<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
						<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="product_id"/></xsl:attribute></INPUT>
						<INPUT TYPE="submit" name="submit" value="Delete"></INPUT>
						</form>

					 </td>
					</tr>
				    </tbody>
				</table>
				</xsl:if>
		</div>
	</xsl:for-each>
</div>
</p>
<br/>
<font color="#e7e7df">Copyright 2026
                <A HREF="http://www.cmsmanhattan.com/" style="color:#e7e7df; text-decoration:none;"><font color="#e7e7df">  CMS Manhattan </font></A>.  All rights reserved
</font>
<br/>

                </div>
                </DIV>
        </DIV>


    <div id="overlay"></div>
    <div id="dialog">
        <iframe id="iframe" src=""></iframe>
        <button onclick="closeDialog()">Close</button>
    </div>

    <div id="overlay_mail"></div>
    <div id="dialog_mail">
        <iframe id="iframe_mail" src=""></iframe>
        <button onclick="closeDialogMail()">Close</button>
    </div>

    <script type="text/javascript">
    (function() {
        var wrap = document.getElementById('product-grid-wrap');
        var sentinel = document.getElementById('scroll-sentinel');
        var loadingEl = document.getElementById('scroll-loading');
        if (!wrap || !sentinel) { return; }

        var currentOffset = parseInt(wrap.getAttribute('data-offset'), 10) || 0;
        var pageStep = 10; // matches the existing "(page div 10)" pagination step
        var isLoading = false;
        var isDone = false;

        function loadMore() {
            if (isLoading || isDone) { return; }
            isLoading = true;
            if (loadingEl) { loadingEl.style.display = 'block'; }

            var nextOffset = currentOffset + pageStep;
            var params = new URLSearchParams(window.location.search);
            params.set('offset', nextOffset);

            fetch(window.location.pathname + '?' + params.toString(), { credentials: 'same-origin' })
                .then(function(resp) {
                    if (!resp.ok) { throw new Error('bad response'); }
                    return resp.text();
                })
                .then(function(html) {
                    var doc = new DOMParser().parseFromString(html, 'text/html');
                    var newGrid = doc.getElementById('product-grid');
                    var currentGrid = document.getElementById('product-grid');

                    if (!newGrid || !currentGrid || newGrid.rows.length === 0) {
                        isDone = true;
                        if (observer) { observer.disconnect(); }
                        if (loadingEl) { loadingEl.style.display = 'none'; }
                        return;
                    }

                    var targetBody = currentGrid.tBodies[0] || currentGrid;
                    var newRows = Array.prototype.slice.call(newGrid.rows);
                    newRows.forEach(function(row) {
                        targetBody.appendChild(row);
                    });

                    currentOffset = nextOffset;
                    isLoading = false;
                    if (loadingEl) { loadingEl.style.display = 'none'; }
                })
                .catch(function() {
                    // On any failure, stop auto-loading; the original Prev/Next controls remain as a fallback.
                    isDone = true;
                    isLoading = false;
                    if (observer) { observer.disconnect(); }
                    if (loadingEl) { loadingEl.style.display = 'none'; }
                });
        }

        var observer = null;
        if ('IntersectionObserver' in window) {
            observer = new IntersectionObserver(function(entries) {
                entries.forEach(function(entry) {
                    if (entry.isIntersecting) { loadMore(); }
                });
            }, { rootMargin: '200px' });
            observer.observe(sentinel);
        }
    })();
    </script>

</body>






</HTML>
</xsl:template>

<xsl:template name="prList">
			<xsl:param name="position" select="1"/>
			<xsl:param name="prCount"/>
			<div class="pg2-card">
				<a class="pg2-title"><xsl:attribute name="HREF"><xsl:value-of select="document/product_list/product[$position]/policy_url"/></xsl:attribute> <xsl:value-of select="document/product_list/product[$position]/name"/></a>
				<xsl:if test="document/product_list/product[$position]/amount != 0">
				<span class="pg2-price"><xsl:value-of select="document/product_list/product[$position]/amount"/>  </span>
				</xsl:if>
				<xsl:if test="document/product_list/product[$position]/icon != ''">
				<div class="pg2-image-wrap">
					<a>
						<xsl:attribute name="HREF"><xsl:value-of select="document/product_list/product[$position]/policy_url"/></xsl:attribute>
						<img border="0">
						<xsl:attribute name="alt">
						  <xsl:for-each select="document/product_list/product[$position]/description/r">
						    <xsl:value-of select="."/>
						  </xsl:for-each>
						</xsl:attribute>
						<xsl:attribute name="src">
						<xsl:value-of select="document/product_list/product[$position]/icon"/></xsl:attribute>
						</img>
					</a>
				</div>
				</xsl:if>
				<a class="pg2-desc"><xsl:attribute name="HREF"><xsl:value-of select="document/product_list/product[$position]/policy_url"/></xsl:attribute>
					  <xsl:for-each select="document/product_list/product[$position]/description/r">
					    <xsl:value-of select="."/>
					  </xsl:for-each>
				</a>

				<xsl:choose>
					   <xsl:when test="/document/role_id = '2'">
							<div style="margin-top:8px;">
								<form name="product_edit" style="display:inline-block;" action="Productlist.jsp" method="POST">
								<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
								<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="product" ></INPUT>
								<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="document/product_list/product[$position]/product_id"/></xsl:attribute></INPUT>
								<INPUT class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
								</form>
								<form name="product_del" style="display:inline-block; margin-left:10px;" action="Productlist.jsp" method="POST">
								<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
								<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="document/product_list/product[$position]/product_id"/></xsl:attribute></INPUT>
								<INPUT class="button" TYPE="submit" name="submit" value="Delete"></INPUT>
								</form>
							</div>
					   </xsl:when>
					   <xsl:when test="/document/role_id = '1'">
						<xsl:variable name="user_id" select="number(/document/owner_user_id)"/>
						<xsl:variable name="owner_id" select="number(/document/product_list/product[$position]/user_id)"/>
					       <xsl:if test="$owner_id = $user_id" >
								<div style="margin-top:8px;">
									<form name="product_edit" style="display:inline-block;" action="Productlist.jsp" method="POST">
									<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="edit"  ></INPUT>
									<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="element" VALUE="userinfo" ></INPUT>
									<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="document/product_list/product[$position]/product_id"/></xsl:attribute></INPUT>
									<INPUT  class="button" TYPE="submit" name="submit" value="Edit"></INPUT>
									</form>
									<form name="product_del" style="display:inline-block; margin-left:10px;" action="Productlist.jsp" method="POST">
									<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="action" VALUE="del"  ></INPUT>
									<INPUT SIZE="0"  AUTOCOMPLETE="off" TYPE="HIDDEN" NAME="product_id"><xsl:attribute name="value"><xsl:value-of select="document/product_list/product[$position]/product_id"/></xsl:attribute></INPUT>
									<INPUT  class="button" TYPE="submit" name="submit" value="Delete"></INPUT>
									</form>
								</div>
					       </xsl:if>
					   </xsl:when>
					   </xsl:choose>
			</div>
			<xsl:if test="$position+1 &lt;= $prCount">
				<xsl:call-template name="prList">
					<xsl:with-param name="position" select="$position+1"/>
					<xsl:with-param name="prCount" select="$prCount"/>
				</xsl:call-template>
			</xsl:if>
</xsl:template>



</xsl:stylesheet>
