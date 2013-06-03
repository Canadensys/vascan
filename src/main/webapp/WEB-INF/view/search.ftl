<#include "inc/common.ftl">

<#assign page={"title":rc.getMessage("namesearch_title1")+ " - " + rc.getMessage("site_title"),"cssList":[rc.getContextUrl("/styles/vascan.css")],"javaScriptIncludeList":
["http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js", rc.getContextUrl("/js/typeahead.min.js"), rc.getContextUrl("/js/application.js"), rc.getContextUrl("/js/autocomplete.js")]}>

<#if search.term?has_content>
	<#assign page = page + {"title": rc.getMessage("namesearch_title2",[search.term])+ " - " + rc.getMessage("site_title")}>
</#if>

<#assign currentPage="search"/>

<#include "inc/header.ftl">
<#include "inc/menu.ftl">

<h1>${rc.getMessage("namesearch_title1")}</h1>		 
<form id="search_box" class="round custom_form" method="get" onkeypress="return event.keyCode != 13;">
	<p><input class="typeahead" type="text" name="q" id="search_term" value="${search.term?html}" autocomplete="off"/> <input type="button" onclick="document.forms[0].submit()" value="${rc.getMessage("namesearch_button1")}"/></p>

	<#if rc.getLocale().getLanguage() == "fr">
		<input type="hidden" name="lang" id="search_lang" class="" value="fr" autocomplete="off"/>
	</#if>
	<p>${rc.getMessage("namesearch_msg1")}</p>
</form>

<#if search.term?has_content>
<h2>${rc.getMessage("namesearch_h2_results",[search.total])}</h2>
<table class="sortable custom_results_table" id="search_results">
<thead>	
	<tr>
		<th><@display str=locale.namesearch_th1/></th>
		<th><@display str=locale.namesearch_th2/></th>
		<th><@display str=locale.namesearch_th3/></th>
	</tr>
</thead>
<tbody>
<#list results as result>
	<tr>
		<td class="${result.fullScientificNameStatus}"><a href="taxon/${result.taxonId}<@display str=locale.url_language/>">${result.fullScientificNameHtml}</a></td>
		<td class="${result.frenchVernacularNameStatus}">${result.frenchVernacularNameHtml}</td>
		<td class="${result.englishVernacularNameStatus}">${result.englishVernacularNameHtml}</td>
	</tr>
</#list>
</tbody>
 </table>
 <#else>
 <p><img src="${rc.getContextUrl("/images/accepted_species_per_genus.png")}" width="100%" alt="Word cloud image" title="${rc.getMessage("img1_title")}"/></p>
 </#if>
	</div><#-- content -->
</div>
<#include "inc/footer.ftl">