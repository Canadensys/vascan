<#include "inc/common.ftl">
<#include "inc/global-functions.ftl">

<#assign page={"title": data.pageTitle+ " - " + rc.getMessage("site_title"), 
"cssList": [rc.getContextUrl("/styles/"+formatFileInclude("vascan",currentVersion!,false,".css"))],"cssPrintList": [rc.getContextUrl("/styles/print.css")],
"javaScriptIncludeList": ["http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js", "http://data.canadensys.net/common/js/jquery.tooltip.min.js",rc.getContextUrl("/js/"+formatFileInclude("vascan",currentVersion!,useMinified,".js")), rc.getContextUrl("/js/vascan/"+formatFileInclude("taxon",currentVersion!,useMinified,".js"))]}>

<#assign currentPage="taxon"/>

<#include "inc/header.ftl">
<#include "inc/menu.ftl">

<h1>${data.fullScientificName}</h1>
<#if data.isSynonymConcept>
	<p class="sprite sprite-synonym">
	<a href="${rc.getContextUrl('/taxon/'+data.taxonId)}">${data.fullScientificName}</a> ${rc.getMessage("taxon_synonym_msg1",[refBuilder(data.link,data.reference,data.referenceShort,false,true,false)])}
	</p>	
	<#list data.parents as parent>
		<p class="sprite sprite-redirect_accepted">
		<a href="${rc.getContextUrl('/taxon/'+parent.taxonId)}">${parent.fullScientificName}</a>${rc.getMessage("taxon_accepted_no_strong_msg1",[prefixFrenchRank(rc.getMessage("rank_"+parent.rank?lower_case))?lower_case,refBuilder(parent.link,parent.reference,parent.referenceShort,false,true,false)])}
		</p>
	</#list>
</#if>
<#if data.isSynonymConcept != true>
	<p class="sprite sprite-accepted">
	<a href="${rc.getContextUrl('/taxon/'+data.taxonId)}">${data.fullScientificName}</a> ${rc.getMessage("taxon_accepted_msg1",[prefixFrenchRank(rc.getMessage("rank_"+data.rank?lower_case))?lower_case,refBuilder(data.link,data.reference,data.referenceShort,false,true,false)])}	    
	</p>
	<@taxonContent />
</#if>
	</div><#-- content -->
</div>
<#include "inc/footer.ftl">