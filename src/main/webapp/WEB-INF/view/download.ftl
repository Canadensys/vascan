<#-- Copyright 2010-2013 Canadensys -->
<#include "inc/common.ftl">
<#include "inc/global-functions.ftl">

<head>
<title>${rc.getMessage("download_title1") + " - " + rc.getMessage("site_title")}</title>
<@cssAsset fileName="vascan" version=currentVersion! useMinified=false/>
</head>

<#assign currentPage="download"/>
<#include "inc/menu.ftl">

<div class="busy">
	<h1>${rc.getMessage("download_title2")} <@imageAsset imageName="ajax-loader" ext="gif"/></h1>
	<p>${rc.getMessage("download_msg1")}</p>
	<#if page.filename?has_content == true>
	<blockquote><a href="${page.downloadURL}" onclick="_gaq.push(['_trackEvent', '<#if page.format == "txt">Text<#else>Archive</#if>', 'Pre-Download', 'Checklist builder']);">${page.filename}</a></blockquote>
	</#if>
	<p>${rc.getMessage("download_msg2")}</p>
</div>
<div class="ready" style="display:none">
	<h1>${rc.getMessage("download_title3")}</h1>
	<p>${rc.getMessage("download_msg3")}</p>
	<#if page.filename?has_content == true>
	<blockquote><a href="${page.downloadURL}" onclick="_gaq.push(['_trackEvent', '<#if page.format == "txt">Text<#else>Archive</#if>', 'Download', 'Checklist builder']);">${page.filename}</a></blockquote>
	</#if>
	<p>${rc.getMessage("download_msg4")}</p>
</div>
<div class="error" style="display:none">
	<h1>${rc.getMessage("download_title4")}</h1>
	<p>${rc.getMessage("download_msg5")}</p>
</div>
	</div><#-- content -->
</div>

<#-- JavaScript handling -->
<content tag="local_script">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>

<@jsAsset fileName="vascan" version=currentVersion! useMinified=useMinified/>
<@jsAsset fileName="download" version=currentVersion! useMinified=useMinified/>
</content>
