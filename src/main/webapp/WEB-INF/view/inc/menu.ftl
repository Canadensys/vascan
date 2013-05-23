</head>
<body>
	<div id="feedback_bar"><a href="http://code.google.com/p/canadensys/issues/entry?template=Vascan%20-%20Data%20issue" target="_blank" title="${rc.getMessage("feedback_msg1")}">&nbsp;</a></div>
	<#include "canadensys-header.ftl">
	<div id="body"<#if currentPage == "checklist" || currentPage == "darwincore" || currentPage == "dwc" || currentPage == "search"> class="fullscreen"</#if>>
		<#if currentPage == "search">
		<!--[if lte IE 7]>
		<p id="ie"><@display str=locale.browser_msg1/></p>
		<![endif]-->
		</#if>
		
		<div id="side_bar">
			<ul class="side_menu custom_list round">
				<li><a href="${rc.getContextUrl("/search"+rc.getMessage("url_language"))}">${rc.getMessage("namesearch_title1")}</a></li>
				<li><a href="${rc.getContextUrl("/checklist"+rc.getMessage("url_language"))}">${rc.getMessage("checklist_title1")}</a></li>
				<li><a href="${rc.getContextUrl("/about"+rc.getMessage("url_language"))}">${rc.getMessage("about_title1")}</a></li>
				<li><a href="${rc.getMessage("url_dataset")}">${rc.getMessage("dataset_title1")}</a></li>
			</ul>
	
		<#if currentPage == "name" && page.isDisambiguation != true>
		<//Link to taxon page>
			<#if data.isRedirect != true && page.isSynonym == true>
				<p><a class="round big_button multi_line" id="see_taxon" href="taxon/${data.synonymWarningId}<@display str=locale.url_language/>"><@display str=locale.page_button4/></a></p>
			<#elseif data.isRedirect != true && page.isVernacular == true>
				<p><a class="round big_button multi_line" id="see_taxon" href="vernacular/${data.vernacularNameWarningId}<@display str=locale.url_language/>"><@display str=locale.page_button5/></a></p>
			<#else>
				<p><a class="round big_button multi_line" id="see_taxon" href="taxon/${data.id}<@display str=locale.url_language/>"><@display str=locale.page_button4/></a></p>
			</#if>
		<#elseif currentPage == "taxon" && data.status == "accepted">
				<p><a class="round big_button multi_line" id="create_checklist" href="checklist?taxon=${data.taxonId}${rc.getMessage("url_language_amp")}">${rc.getMessage("page_button1")}</a></p>
		<#elseif currentPage == "checklist">
				<p><a class="round big_button multi_line" id="dwc_archive" href="download?format=dwc&${pageQuery}"><@display str=locale.page_button2/></a></p>
				<p><a class="round big_button multi_line" id="csv_file" href="download?format=txt&${pageQuery}"><@display str=locale.page_button3/></a></p>
		</#if>
		<#if currentPage == "name" || currentPage == "taxon" || currentPage == "vernacular" || currentPage="checklist">
				<p><a class="round big_button" id="print_page" href="javascript:window.print();">${rc.getMessage("page_button6")}</a></p>
		</#if>
		</div>
		
		<div id="content" class="clear_fix">