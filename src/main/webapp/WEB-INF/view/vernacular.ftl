<#include "inc/common.ftl">
<#include "inc/global-functions.ftl">

<#assign page={"title": vernacularName.name+ " - " + rc.getMessage("site_title"),
"cssList": [rc.getContextUrl("/styles/"+formatFileInclude("vascan",currentVersion!,false,".css"))],"cssPrintList": [rc.getContextUrl("/styles/print.css")],
"javaScriptIncludeList": ["http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js", "http://data.canadensys.net/common/js/jquery.tooltip.min.js", rc.getContextUrl("/js/"+formatFileInclude("vascan",currentVersion!,useMinified,".js")),	rc.getContextUrl("/js/vascan/"+formatFileInclude("vernacular",currentVersion!,useMinified,".js"))]}>

<#assign currentPage="vernacular"/>

<#include "inc/header.ftl">
<#include "inc/menu.ftl">

<h1>${vernacularName.name}</h1>

<p class="sprite sprite-${vernacularName.status?lower_case}">
	<a href="${rc.getContextUrl('/vernacular/'+vernacularName.vernacularId)}">${vernacularName.name}</a>  ${rc.getMessage("vernacular_" + vernacularName.status?lower_case + "_msg1",[refBuilder(vernacularName.link,vernacularName.reference,vernacularName.referenceShort,true,false,false),rc.getMessage("language_"+vernacularName.language)])}
</p>                                
<p class="sprite sprite-redirect_${vernacularName.taxon.status?lower_case}">
	<a href="${rc.getContextUrl('/taxon/'+vernacularName.taxon.taxonId)}">${vernacularName.taxon.fullScientificName}</a> ${rc.getMessage("taxon_" + vernacularName.taxon.status?lower_case + "_no_strong_msg1",[prefixFrenchRank(rc.getMessage("rank_"+vernacularName.taxon.rank?lower_case))?lower_case,refBuilder(vernacularName.taxon.link,vernacularName.taxon.reference,vernacularName.taxon.referenceShort,false,true,false)])}
</p>
	</div><#-- content -->
</div>

<#include "inc/footer.ftl">