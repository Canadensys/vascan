<#-- some /WEB-INF/view/inc/.ftl can come from canadensys-web-core project -->
<#include "global-functions.ftl">
<!DOCTYPE html>
<html lang="${rc.getLocale().getLanguage()}">
<head>
<meta charset="UTF-8">
<title><sitemesh:write property='title'>Title goes here</sitemesh:write></title>
<link rel="stylesheet" href="http://data.canadensys.net/common/styles/common-1.1.min.css" media="screen,print"/>
<link rel="shortcut icon" href="http://data.canadensys.net/common/images/favicon.png"/>
<#list page.otherLanguage?keys as currLang>
<link rel="alternate" hreflang="${currLang}" href="${page.otherLanguage[currLang]}"/>
</#list>

<sitemesh:write property='head'/>
<#include "ga.ftl">
</head>
<body>
	<div id="skip-link">
		<a href="#main-content" class="skipnav">${rc.getMessage("header.skip")}</a>
	</div>
	<#if feedbackURL?? && feedbackURL?has_content>
	<div id="feedback_bar"><a href="${feedbackURL}" target="_blank" title="${rc.getMessage("feedback.hover")}">&nbsp;</a></div>
	</#if>
	<#include "include/header-div.ftl">
	
	<sitemesh:write property='body'/>
	<#include "include/footer.ftl">
	<sitemesh:write property='page.local_script'/>
</body>
</html>