<#include "inc/common.ftl">

<#assign page={"title": rc.getMessage("namesearch_title1")+ " - " + rc.getMessage("site_title"), "cssList": [rc.getContextUrl("/styles/vascan.css")], "cssPrintList": [rc.getContextUrl("/styles/print.css")], "javaScriptIncludeList": ["http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js", rc.getContextUrl("/js/typeahead.min.js"), rc.getContextUrl("/js/vascan.js"), rc.getContextUrl("/js/vascan/autocomplete.js")]}>

<#if search.term?has_content>
	<#assign page = page + {"title": rc.getMessage("namesearch_title2",[search.term])+ " - " + rc.getMessage("site_title")}>
</#if>

<#assign currentPage="search"/>

<#include "inc/header.ftl">
<#include "inc/menu.ftl">

<h1>${rc.getMessage("namesearch_title1")}</h1>
<form id="search_box" class="round custom_form" method="get">
	<p>
	  <input class="typeahead" type="text" name="q" id="search_term" value="${search.term?html}" placeholder="${rc.getMessage("namesearch_placeholder")}"/>
	  <input type="submit" id="search_button" value="${rc.getMessage("namesearch_button1")}" />
	</p>

	<#if rc.getLocale().getLanguage() == "fr">
		<input type="hidden" name="lang" id="search_lang" class="" value="fr" autocomplete="off"/>
	</#if>
	<p>${rc.getMessage("namesearch_msg1")}</p>
</form>

<#if search.term?has_content>
<h2>${rc.getMessage("namesearch_h2_results",[search.total])}</h2>
<ul id="search_list">
<#list results as result>
	<#if result.type = "taxon">
		<li class="sprite sprite-${result.status}"><a href="${rc.getContextUrl('/taxon/'+result.id)}">${result.name}</a></li>
	<#elseif result.type = "vernacular">
		<li><a href="${rc.getContextUrl('/vernacular/'+result.id)}">${result.name}</a></li>
	</#if>
</#list>
</ul>
<#if search.pageIndex?has_content>
	Page ${search.pageIndex+1} / ${(search.total/search.pageSize)?ceiling}
</#if>
 <#else>
 <p><img src="${rc.getContextUrl("/images/accepted_species_per_genus.png")}" width="100%" alt="Word cloud image" title="${rc.getMessage("img1_title")}"/></p>
 </#if>
	</div><#-- content -->
</div>
<#include "inc/footer.ftl">