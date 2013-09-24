<#include "inc/common.ftl">
<#include "inc/global-functions.ftl">
<#assign page={"title": rc.getMessage("api_title1"),
"cssScreenPrintList": [rc.getContextUrl("/styles/"+formatFileInclude("vascan",currentVersion!,false,".css"))]}>

<#assign currentPage="api"/>

<#include "inc/header.ftl">
<#include "inc/menu.ftl">

<h1>${rc.getMessage("api_title2")}</h1>

<#if rc.getLocale().getLanguage()=="en">
<p class="api-path round">
  http://data.canadensys.net/vascan/api/0.1/search.json
  <span class="separator">${rc.getMessage("api_uri_separator")}</span>
  http://data.canadensys.net/vascan/api/0.1/search.xml
</p>

<#elseif rc.getLocale().getLanguage()=="fr">
<p class="api-path round">
  http://data.canadensys.net/vascan/api/0.1/search.json
  <span class="separator">${rc.getMessage("api_uri_separator")}</span>
  http://data.canadensys.net/vascan/api/0.1/search.xml
</p>
</#if>

	</div><#-- content -->
</div>
<#include "inc/footer.ftl">