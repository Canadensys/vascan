<#include "inc/common.ftl">

<#assign page={"title": rc.getMessage("namesearch_title1")+ " - " + rc.getMessage("site_title"), "cssList": [rc.getContextUrl("/styles/vascan.css")], "cssPrintList": [rc.getContextUrl("/styles/print.css")], "javaScriptIncludeList": ["http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js", rc.getContextUrl("/js/typeahead.min.js"), rc.getContextUrl("/js/vascan.js"), rc.getContextUrl("/js/vascan/search.js")], "javaScriptSetupCallList" : ['VASCAN.common.setLanguageResources({autocomplete_title1: "${rc.getMessage("autocomplete_title1")}", autocomplete_title2: "${rc.getMessage("autocomplete_title2")}"})']}>

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
	<#assign totalPages=(search.total/search.pageSize)?ceiling/>
	<h2>
	<#if (search.pageNumber > 1)>
		${rc.getMessage("namesearch_h2_results_paging",[search.total, search.pageNumber, totalPages])}
	<#else>
		${rc.getMessage("namesearch_h2_results",[search.total])}
	</#if>
	</h2>
	<ul id="search_list">
	<#if !results?has_content>
		<p><em>${rc.getMessage("namesearch_notfound")}</em></p>
	</#if>
	<#list results as result>
		<#if result.type = "taxon">
			<#if result.status = "accepted">
				<li class="sprite sprite-${result.status}"><a href="${getI18nContextUrl('/taxon/'+result.id)}">${result.namehtmlauthor}</a> ${rc.getMessage("taxon_accepted_msg_noref",[result.rankname])}</li>
			<#elseif result.status = "synonym">
				<li class="sprite sprite-${result.status}"><a href="${getI18nContextUrl('/taxon/'+result.id)}">${result.namehtmlauthor}</a> ${rc.getMessage("taxon_synonym_msg_noref")} <a href="${getI18nContextUrl('/taxon/'+result.parentid)}">${result.parentnamehtml}</a></li>
			</#if>
		<#elseif result.type = "vernacular"> 
			<#if result.status = "accepted">
				<li><a href="${getI18nContextUrl('/vernacular/'+result.id)}">${result.name}</a> ${rc.getMessage("vernacular_accepted_msg2",[rc.getMessage("language_"+result.lang)])} <a href="${getI18nContextUrl('/taxon/'+result.taxonid)}">${result.taxonnamehtml}</a></li>
			<#elseif result.status = "synonym">
				<li><a href="${getI18nContextUrl('/vernacular/'+result.id)}">${result.name}</a> ${rc.getMessage("vernacular_msg2",[rc.getMessage("language_"+result.lang)])} <a href="${getI18nContextUrl('/taxon/'+result.taxonid)}">${result.taxonnamehtml}</a></li>
			</#if>
		</#if>
	</#list>
	
	</ul>

	<#if (search.total >= search.pageSize)>
		<@pages 1..totalPages search.pageNumber/>
	</#if>
<#else>
 <p><img src="${rc.getContextUrl("/images/accepted_species_per_genus.png")}" width="100%" alt="Word cloud image" title="${rc.getMessage("img1_title")}"/></p>
</#if>

	</div><#-- content -->
</div>
<#include "inc/footer.ftl">