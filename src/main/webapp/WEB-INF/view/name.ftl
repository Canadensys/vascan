<#include "inc/common.ftl">
<#assign page={"title":data.pageTitle+ " - " + rc.getMessage("site_title"),"cssList":[rc.getContextUrl("/styles/vascan.css")],"javaScriptIncludeList":
["http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js",
"http://data.canadensys.net/common/js/jquery.tooltip.min.js"]}>

<#assign currentPage="name"/>

<#include "inc/header.ftl">
<#include "inc/menu.ftl">

<#if data.requiresDisambiguation == true >
<h1>${data.nameH1}</h1>
<#if data.isVernacularName == true>
	<#list data.disambiguationVernaculars as vernacular>
		<p class="sprite sprite-${vernacular.status?lower_case}">
		    ${rc.getMessage("vernacular_" + vernacular.status?lower_case + "_msg1",[vernacular.vernacularid,vernacular.name,refBuilder(vernacular.link,vernacular.reference,vernacular.referenceShort,true,false,false),rc.getMessage("language_"+rc.getLocale().getLanguage())])}
		</p>								
		<p class="sprite sprite-redirect_${vernacular.taxon.status?lower_case}">
            ${rc.getMessage("taxon_" + vernacular.taxon.status?lower_case + "_no_strong_msg1",[vernacular.taxon.taxonId,vernacular.taxon.fullScientificName,prefixFrenchRank(rc.getMessage("rank_"+vernacular.taxon.rank?lower_case))?lower_case,refBuilder(vernacular.taxon.link,vernacular.taxon.reference,vernacular.taxon.referenceShort,false,true,false)])}
		</p>
	</#list>
</#if>
<#if data.isTaxon == true>
	<#list data.disambiguationTaxons as taxon>
		<#if taxon.status?lower_case == "synonym">
			<p class="sprite sprite-synonym">
			   ${rc.getMessage("taxon_synonym_msg1",[taxon.taxonId,taxon.fullScientificName,refBuilder(taxon.link,taxon.reference,taxon.referenceShort,true,false,false)])}
			</p>
                              <#list taxon.parents as parent>
                                  <p class="sprite sprite-redirect_${parent.status?lower_case}">
                                      ${rc.getMessage("taxon_accepted_no_strong_msg1",[parent.taxonId,parent.fullScientificName,prefixFrenchRank(rc.getMessage("rank_"+parent.rank?lower_case))?lower_case,refBuilder(parent.link,parent.reference,parent.referenceShort,false,true,false)])}
                                  </p>
                              </#list>    
                          <#else>
                              <p class="sprite sprite-accepted">
                                 ${rc.getMessage("taxon_accepted_msg1",[taxon.taxonId,taxon.fullScientificName,prefixFrenchRank(rc.getMessage("rank_"+taxon.rank?lower_case))?lower_case,refBuilder(taxon.link,taxon.reference,taxon.referenceShort,false,true,false)])}
                              </p>
                          </#if>							
	</#list>
</#if>
<#else>
<#if data.isRedirect == true >
	<#if data.isSynonymWarning == true >
		<h4 class="warning round">
		    ${rc.getMessage("warning_synonym_msg", [data.synonymWarningUrl,data.synonymWarningH1])}
		</h4>
	</#if>
	
	<#if data.isVernacularNameWarning == true >
		<h4 class="warning round">
		    ${rc.getMessage("warning_vernacular_msg", [data.vernacularNameWarning,data.vernacularNameWarning?cap_first])}
		</h4>
	</#if>
	
	<h1>${data.nameH1}</h1>
	<p class="sprite sprite-accepted">
  		${rc.getMessage("taxon_accepted_msg1",[data.id,data.name,prefixFrenchRank(rc.getMessage("rank_"+data.rank?lower_case))?lower_case,refBuilder(data.link,data.reference,data.referenceShort,false,true,false)])}
	</p>			 
				   
	<@taxonContent />
	
<#else>
	<#-- no redirect ; display basic information for synonym or vernacular only -->
	<#if data.isSynonymWarning == true >
		<h1>${data.synonymWarningH1}</h1>
		<p class="sprite sprite-synonym">
		    ${rc.getMessage("taxon_synonym_msg1",[data.synonymWarningId,data.synonymWarning,refBuilder(data.synonymWarningLink,data.synonymWarningReference,data.synonymWarningReferenceShort,true,false,false)])}
		</p>	
	<#elseif data.isVernacularNameWarning == true >
		<h1>${data.vernacularNameWarning}</h1>
		<p class="sprite sprite-${data.vernacularNameWarningStatus?lower_case}">
		    ${rc.getMessage("vernacular_" + data.vernacularNameWarningStatus?lower_case + "_msg1",[data.vernacularNameWarningId,data.vernacularNameWarning,refBuilder(data.vernacularNameWarningLink,data.vernacularNameWarningReference,data.vernacularNameWarningReferenceShort,true,false,false),rc.getMessage("language_"+data.vernacularNameWarningLanguage)?lower_case])}
		</p>
	</#if>  
	<p class="sprite sprite-redirect_${data.status?lower_case}">
	    ${rc.getMessage("taxon_" + data.status?lower_case + "_no_strong_msg1",[data.id,data.name,prefixFrenchRank(rc.getMessage("rank_"+data.rank?lower_case))?lower_case,refBuilder(data.link,data.reference,data.referenceShort,false,true,false)])}
	</p>
</#if>
</#if>
	</div><#-- content -->
</div>
<#include "inc/footer.ftl">