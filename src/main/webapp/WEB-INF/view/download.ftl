<#-- Copyright 2010-2013 Canadensys -->
<#include "inc/common.ftl">
<#include "inc/global-functions.ftl">
<#assign page={"title": rc.getMessage("download_title1") + " - " + rc.getMessage("site_title"),
"cssList": [rc.getContextUrl("/styles/"+formatFileInclude("vascan",currentVersion!,false,".css"))],"cssPrintList": [rc.getContextUrl("/styles/print.css")],
"javaScriptIncludeList": ["http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js", rc.getContextUrl("/js/"+formatFileInclude("vascan",currentVersion!,useMinified,".js")),rc.getContextUrl("/js/vascan/"+formatFileInclude("download",currentVersion!,useMinified,".js"))]}>

<#assign currentPage="download"/>

<#include "inc/header.ftl">
<#include "inc/menu.ftl">

<div class="busy">
	<h1>${rc.getMessage("download_title2")} <img src="${rc.getContextUrl('/images/ajax-loader.gif')}"/></h1>
	<p>${rc.getMessage("download_msg1")}</p>
	<#if filename?has_content == true>
	<blockquote><a href="${downloadURL}" onclick="_gaq.push(['_trackEvent', '<#if format == "txt">Text<#else>Archive</#if>', 'Pre-Download', 'Checklist builder']);">${filename}</a></blockquote>
	</#if>
	<p>${rc.getMessage("download_msg2")}</p>
</div>
<div class="ready" style="display:none">
	<h1>${rc.getMessage("download_title3")}</h1>
	<p>${rc.getMessage("download_msg3")}</p>
	<#if filename?has_content == true>
	<blockquote><a href="${downloadURL}" onclick="_gaq.push(['_trackEvent', '<#if format == "txt">Text<#else>Archive</#if>', 'Download', 'Checklist builder']);">${filename}</a></blockquote>
	</#if>
	<p>${rc.getMessage("download_msg4")}</p>
</div>
<div class="error" style="display:none">
	<h1>${rc.getMessage("download_title4")}</h1>
	<p>${rc.getMessage("download_msg5")}</p>
</div>
	</div><#-- content -->
</div>
<#include "inc/footer.ftl">