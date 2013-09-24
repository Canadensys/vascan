<#include "inc/common.ftl">
<#include "inc/global-functions.ftl">
<#assign page={"title": rc.getMessage("api_title1"),
"cssScreenPrintList": [rc.getContextUrl("/styles/"+formatFileInclude("vascan",currentVersion!,false,".css"))]}>

<#assign currentPage="api"/>

<#include "inc/header.ftl">
<#include "inc/menu.ftl">

<h1>${rc.getMessage("api_title2")}</h1>

<#if rc.getLocale().getLanguage()=="en">
<h2>${rc.getMessage("api_title3")}</h2>
<p>The VASCAN API accepts GET and POST requests. The path contains an explicit version and the search resource accepts a parameter <strong>q</strong>. The value for <strong>q</strong> can be a scientific name, a vernacular name or a VASCAN taxon identifier (<em>e.g.</em> 861). It may have a single value for GET requests or multiple values for POST requests, each separated by line breaks, \n. Scientific or vernacular names may optionally be preceded by your local identifier followed by a pipe, |. GET requests for JSON-based URIs may have an additional callback parameter for JSONP responses.</p>
<p class="api-path round">
  http://data.canadensys.net/vascan/api/0.1/search.json
  <span class="separator">${rc.getMessage("api_uri_separator")}</span>
  http://data.canadensys.net/vascan/api/0.1/search.xml
</p>

<#elseif rc.getLocale().getLanguage()=="fr">
<h2>${rc.getMessage("api_title3")}</h2>
<p></p>
<p class="api-path round">
  http://data.canadensys.net/vascan/api/0.1/search.json
  <span class="separator">${rc.getMessage("api_uri_separator")}</span>
  http://data.canadensys.net/vascan/api/0.1/search.xml
</p>
</#if>

	</div><#-- content -->
</div>
<#include "inc/footer.ftl">