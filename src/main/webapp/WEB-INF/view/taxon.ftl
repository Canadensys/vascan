<#include "inc/common.ftl">
<#include "inc/global-functions.ftl">

<head>
<title>${page.data.pageTitle+ " - " + rc.getMessage("site_title")}</title>
<@cssAsset fileName="vascan" version=currentVersion! useMinified=false/>
</head>

<#assign currentPage="taxon"/>
<#assign data=page.data/>
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

<#-- JavaScript handling -->
<content tag="local_script">
<#-- 1.7.2 required by jquery.tooltip.min.js-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src="https://layout.canadensys.net/common/js/jquery.tooltip.min.js"></script>

<@jsAsset fileName="vascan" version=currentVersion! useMinified=useMinified/>
<@jsAsset fileName="taxon" version=currentVersion! useMinified=useMinified/>
</content>
