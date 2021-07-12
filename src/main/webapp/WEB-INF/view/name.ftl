<#include "inc/common.ftl">
<#include "inc/global-functions.ftl">

<head>
<title>${page.data.pageTitle+ " - " + rc.getMessage("site_title")}</title>
<@cssAsset fileName="vascan" version=currentVersion! useMinified=false/>
</head>

<#assign currentPage="name"/>
<#assign data=page.data/>
<#include "inc/menu.ftl">

<#if data.requiresDisambiguation == true >
<h1>${data.nameH1}</h1>
<#if data.isVernacularName == true>
	<#list data.disambiguationVernaculars as vernacular>
		<p class="sprite sprite-${vernacular.status?lower_case}">
		<a href="${rc.getContextUrl('/vernacular/'+vernacular.vernacularid)}">${vernacular.name}</a> ${rc.getMessage("vernacular_" + vernacular.status?lower_case + "_msg1",[refBuilder(vernacular.link,vernacular.reference,vernacular.referenceShort,true,false,false),rc.getMessage("language_"+vernacular.language)])}
		</p>
		<p class="sprite sprite-redirect_${vernacular.taxon.status?lower_case}">
		<a href="${rc.getContextUrl('/taxon/'+vernacular.taxon.taxonId)}">${vernacular.taxon.fullScientificName}</a> ${rc.getMessage("taxon_" + vernacular.taxon.status?lower_case + "_no_strong_msg1",[prefixFrenchRank(rc.getMessage("rank_"+vernacular.taxon.rank?lower_case))?lower_case,refBuilder(vernacular.taxon.link,vernacular.taxon.reference,vernacular.taxon.referenceShort,false,true,false)])}
		</p>
	</#list>
</#if>
<#if data.isTaxon == true>
	<#list data.disambiguationTaxons as taxon>
		<#if taxon.status?lower_case == "synonym">
			<p class="sprite sprite-synonym">
			<a href="${rc.getContextUrl('/taxon/'+taxon.taxonId)}">${taxon.fullScientificName}</a> ${rc.getMessage("taxon_synonym_msg1",[refBuilder(taxon.link,taxon.reference,taxon.referenceShort,true,false,false)])}
			</p>
          <#list taxon.parents as parent>
              <p class="sprite sprite-redirect_${parent.status?lower_case}">
              <a href="${rc.getContextUrl('/taxon/'+parent.taxonId)}">${parent.fullScientificName}</a>${rc.getMessage("taxon_accepted_no_strong_msg1",[prefixFrenchRank(rc.getMessage("rank_"+parent.rank?lower_case))?lower_case,refBuilder(parent.link,parent.reference,parent.referenceShort,false,true,false)])}
              </p>
          </#list>    
      	<#else>
          <p class="sprite sprite-accepted">
          <a href="${rc.getContextUrl('/taxon/'+taxon.taxonId)}">${taxon.fullScientificName}</a> ${rc.getMessage("taxon_accepted_msg1",[prefixFrenchRank(rc.getMessage("rank_"+taxon.rank?lower_case))?lower_case,refBuilder(taxon.link,taxon.reference,taxon.referenceShort,false,true,false)])}
          </p>
      	</#if>
	</#list>
</#if>
<#else>
<#if data.isRedirect == true >
	<#if data.isSynonymWarning == true >
		<h4 class="warning round">
		    <a href="${rc.getContextUrl('/name/'+data.synonymWarningUrl+'?redirect=no')}">${data.synonymWarningH1}</a> ${rc.getMessage("warning_synonym_msg")}
		</h4>
	</#if>
	
	<#if data.isVernacularNameWarning == true >
		<h4 class="warning round">
			<a href="${rc.getContextUrl('/name/'+data.vernacularNameWarning+'?redirect=no')}">${data.vernacularNameWarning?cap_first}</a> ${rc.getMessage("warning_vernacular_msg")}
		</h4>
	</#if>
	
	<h1>${data.nameH1}</h1>
	<p class="sprite sprite-accepted">
	<a href="${rc.getContextUrl('/taxon/'+data.id)}">${data.name}</a> ${rc.getMessage("taxon_accepted_msg1",[prefixFrenchRank(rc.getMessage("rank_"+data.rank?lower_case))?lower_case,refBuilder(data.link,data.reference,data.referenceShort,false,true,false)])}
	</p>
	<@taxonContent />
<#else>
	<#-- no redirect ; display basic information for synonym or vernacular only -->
	<#if data.isSynonymWarning == true >
		<h1>${data.synonymWarningH1}</h1>
		<p class="sprite sprite-synonym">
		<a href="${rc.getContextUrl('/taxon/'+data.synonymWarningId)}">${data.synonymWarning}</a> ${rc.getMessage("taxon_synonym_msg1",[refBuilder(data.synonymWarningLink,data.synonymWarningReference,data.synonymWarningReferenceShort,true,false,false)])}
		</p>	
	<#elseif data.isVernacularNameWarning == true >
		<h1>${data.vernacularNameWarning}</h1>
		<p class="sprite sprite-${data.vernacularNameWarningStatus?lower_case}">
		<a href="${rc.getContextUrl('/vernacular/'+data.vernacularNameWarningId)}">${data.vernacularNameWarning}</a> ${rc.getMessage("vernacular_" + data.vernacularNameWarningStatus?lower_case + "_msg1",[refBuilder(data.vernacularNameWarningLink,data.vernacularNameWarningReference,data.vernacularNameWarningReferenceShort,true,false,false),rc.getMessage("language_"+data.vernacularNameWarningLanguage)?lower_case])}
		</p>
	</#if>  
	<p class="sprite sprite-redirect_${data.status?lower_case}">
		<a href="${rc.getContextUrl('/taxon/'+data.id)}">${data.name}</a> ${rc.getMessage("taxon_" + data.status?lower_case + "_no_strong_msg1",[prefixFrenchRank(rc.getMessage("rank_"+data.rank?lower_case))?lower_case,refBuilder(data.link,data.reference,data.referenceShort,false,true,false)])}
	</p>
</#if>
</#if>
	</div><#-- content -->
</div>

<#-- JavaScript handling -->
<content tag="local_script">
<#-- 1.7.2 required by jquery.tooltip.min.js-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src="https://layout.canadensys.net/common/js/jquery.tooltip.min.js"></script>

<@jsAsset fileName="vascan" version=currentVersion! useMinified=useMinified/>
<@jsAsset fileName="name" version=currentVersion! useMinified=useMinified/>
</content>
