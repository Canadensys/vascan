<#include "inc/common.ftl">
<#include "inc/global-functions.ftl">

<head>
<#if page.search.term?has_content>
	<title>${rc.getMessage("namesearch_title2",[page.search.term])+ " - " + rc.getMessage("site_title")}</title>
<#else>
	<title>${rc.getMessage("namesearch_title1")+ " - " + rc.getMessage("site_title")}</title>
</#if>
<@cssAsset fileName="vascan" version=currentVersion useMinified=false/>
</head>

<#assign currentPage="search"/>
<#include "inc/menu.ftl">

<h1>${rc.getMessage("namesearch_title1")}</h1>
<form id="search_box" class="round custom_form" method="get">
	<p class="inputs">
	  <input class="typeahead" type="text" name="q" id="search_term" value="${page.search.term?html}" placeholder="${rc.getMessage("namesearch_placeholder")}" autofocus="autofocus" />
	  <input type="submit" id="search_button" value="${rc.getMessage("namesearch_button1")}" />
	</p>

	<#if rc.getLocale().getLanguage() == "fr">
		<input type="hidden" name="lang" id="search_lang" class="" value="fr" autocomplete="off"/>
	</#if>
	<p>${rc.getMessage("namesearch_msg1")}</p>
</form>

<#if page.search.term?has_content>
	<#assign totalPages=(page.search.total/page.search.pageSize)?ceiling/>
	<h2>
	<#if (page.search.pageNumber > 1)>
		${rc.getMessage("namesearch_h2_results_paging",[page.search.total, page.search.pageNumber, totalPages])}
	<#else>
		${rc.getMessage("namesearch_h2_results",[page.search.total])}
	</#if>
	</h2>
	<ul id="search_list">
	<#if !page.results?has_content>
		<p><em>${rc.getMessage("namesearch_notfound")}</em></p>
	</#if>
	<#list page.results as result>
		<#if result.type = "taxon">
			<#if result.status = "accepted">
				<li class="sprite sprite-${result.status}"><a href="${getI18nContextUrl('/taxon/'+result.id)}">${result.namehtmlauthor}</a>
				<span>${rc.getMessage("taxon_accepted_msg_noref",[prefixFrenchRank(rc.getMessage("rank_"+result.rankname?lower_case))?lower_case])}</span></li>
			<#elseif result.status = "synonym">
				<li class="sprite sprite-${result.status}"><a href="${getI18nContextUrl('/taxon/'+result.id)}">${result.namehtmlauthor}</a>
				<#if result.hassingleparrent>
				<span>${rc.getMessage("taxon_synonym_msg_noref")} <a href="${getI18nContextUrl('/taxon/'+result.parentid)}">${result.parentnamehtml}</a></span></li>
				<#else>
				<span>${rc.getMessage("taxon_synonym_msg_noref")} 
				<#list 0..result.parentidlist?size-1 as i>
					<a href="${getI18nContextUrl('/taxon/'+result.parentidlist[i])}">${result.parentnamehtmllist[i]}<#if i<result.parentidlist?size-1>,</#if></a>
				</#list>
				</span></li>
				</#if>
			</#if>
		<#elseif result.type = "vernacular"> 
			<#if result.status = "accepted">
				<li class="sprite sprite-${result.type}"><a href="${getI18nContextUrl('/vernacular/'+result.id)}">${result.name}</a>
				<span>${rc.getMessage("vernacular_accepted_msg2",[rc.getMessage("language_"+result.lang)])} <a href="${getI18nContextUrl('/taxon/'+result.taxonid)}">${result.taxonnamehtml}</a></span></li>
			<#elseif result.status = "synonym">
				<li class="sprite sprite-${result.type}"><a href="${getI18nContextUrl('/vernacular/'+result.id)}">${result.name}</a>
				<span>${rc.getMessage("vernacular_synonym_msg2",[rc.getMessage("language_"+result.lang)])} <a href="${getI18nContextUrl('/taxon/'+result.taxonid)}">${result.taxonnamehtml}</a></span></li>
			</#if>
		</#if>
	</#list>
	</ul>

	<#if (page.search.total >= page.search.pageSize)>
		<@pages 1..totalPages page.search.pageNumber/>
	</#if>
  <#else>
	 <p><@imageAsset imageName="accepted_species_per_genus.png" attributes={"width":"100%","alt":"Word cloud image","title":rc.getMessage("img1_title")}/></p>
  </#if>
	</div><#-- content -->
</div>

<#-- JavaScript handling -->
<content tag="local_script">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<@jsLibAsset libName="typeahead.bundle.min.js"/>
<@jsAsset fileName="vascan" version=currentVersion useMinified=useMinified/>
<@jsAsset fileName="search" version=currentVersion useMinified=useMinified/>

<script>
	VASCAN.common.setLanguageResources({autocomplete_title1: "${rc.getMessage("autocomplete_title1")}", autocomplete_title2: "${rc.getMessage("autocomplete_title2")}"});
</script>

</content>