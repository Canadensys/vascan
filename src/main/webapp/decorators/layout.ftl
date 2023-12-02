<#-- some /WEB-INF/view/inc/.ftl can come from canadensys-web-core project -->
<#include "global-functions.ftl">
<!DOCTYPE html>
<html lang="${rc.getLocale().getLanguage()}">
<head>
    <meta charset="UTF-8">
    <title><sitemesh:write property='title'>Title goes here</sitemesh:write></title>
    <link rel="stylesheet" type="text/css" media="all" href="http://layout.canadensys.net/common/bootstrap3/css/bootstrap.css"/>
    <link rel="stylesheet" href="http://layout.canadensys.net/common/css/common-1.1.css" media="screen,print"/>
    <link rel="stylesheet" type="text/css" media="all" href="http://layout.canadensys.net/commonui-bs3/css/ala-styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="http://layout.canadensys.net/common/css/alc-styles-bs3.css"/>
    <!--<link rel="shortcut icon" href="http://layout.canadensys.net/common/images/favicon.png"/>-->
    <link rel="icon" href="http://layout.canadensys.net/common/images/favicon.ico" sizes="16x16 32x32 48x48 64x64" type="image/vnd.microsoft.icon">
    <link rel="alternate" hreflang="${page.language}" href="${page.languageUrl}"/>
    <#list page.otherLanguage?keys as currLang>
    <link rel="alternate" hreflang="${currLang}" href="${page.otherLanguage[currLang]}"/>
    </#list>

    <#-- Test war building nov 22 2023 -->

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
    <script type="text/javascript" src="http://layout.canadensys.net/common/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="http://layout.canadensys.net/common/bootstrap3/js/bootstrap.js"/></script>
    <script type="text/javascript" src="http://layout.canadensys.net/common/js/js.cookie-2.1.4.min.js"/></script>
    <script type="text/javascript" src="http://layout.canadensys.net/common/js/change_lang.js"/></script>
    <script type="text/javascript" src="http://layout.canadensys.net/common/js/change_active_menu.js"></script>
    <script type="text/javascript" src="http://layout.canadensys.net/common/js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
    <sitemesh:write property='page.local_script'/>
</body>
</html>
