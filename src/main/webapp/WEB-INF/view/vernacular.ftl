<#include "inc/common.ftl">
<#include "inc/global-functions.ftl">

<head>
<title>${vernacularName.name+ " - " + rc.getMessage("site_title")}</title>
<@cssAsset fileName="vascan" version=currentVersion! useMinified=false/>
</head>

<#assign currentPage="vernacular"/>
<#include "inc/menu.ftl">
<#assign vernacularName = page.vernacularName/>

<h1>${vernacularName.name}</h1>

<p class="sprite sprite-${vernacularName.status?lower_case}">
	<a href="${rc.getContextUrl('/vernacular/'+vernacularName.vernacularId)}">${vernacularName.name}</a>  ${rc.getMessage("vernacular_" + vernacularName.status?lower_case + "_msg1",[refBuilder(vernacularName.link,vernacularName.reference,vernacularName.referenceShort,true,false,false),rc.getMessage("language_"+vernacularName.language)])}
</p>                                
<p class="sprite sprite-redirect_${vernacularName.taxon.status?lower_case}">
	<a href="${rc.getContextUrl('/taxon/'+vernacularName.taxon.taxonId)}">${vernacularName.taxon.fullScientificName}</a> ${rc.getMessage("taxon_" + vernacularName.taxon.status?lower_case + "_no_strong_msg1",[prefixFrenchRank(rc.getMessage("rank_"+vernacularName.taxon.rank?lower_case))?lower_case,refBuilder(vernacularName.taxon.link,vernacularName.taxon.reference,vernacularName.taxon.referenceShort,false,true,false)])}
</p>
	</div><#-- content -->
</div>

<#-- JavaScript handling -->
<content tag="local_script">
<#-- 1.7.2 required by jquery.tooltip.min.js-->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src="http://data.canadensys.net/common/js/jquery.tooltip.min.js"></script>

<@jsAsset fileName="vascan" version=currentVersion! useMinified=useMinified/>
<@jsAsset fileName="vernacular" version=currentVersion! useMinified=useMinified/>
</content>