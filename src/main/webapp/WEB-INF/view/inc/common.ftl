<#function replace str args=[]>
	<#assign strreplace=str />
	<#list args as arg>
		<#assign strreplace = strreplace?replace("{"+args?seq_index_of(arg)+"}",arg) />
	</#list>
	<#return strreplace />
</#function>

<#macro lang strEn strFr><#if language == "en">${strEn}<#else>${strFr}</#if></#macro>

<#function prefixFrenchRank rank>
    <#assign str=rank >
    <#-- in french, use d' instead of de if rank name starts with a vowel (Ordre, Espece) -->
    <#if rc.getLocale().getLanguage() == "fr">
        <#if rank?lower_case?matches("^[aeiou](.*)")>
            <#assign str="d'" + rank >
        <#else>
            <#assign str="de " + rank >
        </#if>
    </#if>
    <#return str>
</#function>

<#macro vernacularLabel status lang capitalize=false colon=false strong=false>
	<#assign label="" />
	<#if language=="fr">
		<#assign label="nom ">
		<#if strong == true>
			<#assign label= label + "<strong>vernaculaire ">
		<#else>
			<#assign label=label + "vernaculaire ">
		</#if>
		<#if lang=="fr">
			<#assign label=label + "fran&ccedil;ais " />
		<#else>
			<#assign label=label + "anglais " />
		</#if>
		<#if status=="accepted">
			<#assign label=label + "accept&eacute; " />
		<#else>
			<#assign label=label + "synonyme " />
		</#if>
		<#if strong == true>
			<#assign label= label + "</strong> ">
		</#if>
		<#assign label= label + "pour ">
	<#else>
		<#assign label="">
		<#if status=="accepted">
			<#assign label="accepted "/>
		<#else>
			<#assign label="synonym " />
		</#if>	
		
		<#if lang=="fr">
			<#assign label=label + "french "/>
		<#else>
			<#assign label=label+ "english "/>
		</#if>
		<#if strong == true>
			<#assign label= "<strong>" + label + "vernacular</strong> name ">
		<#else>
			<#assign label=label + "vernacular name ">
		</#if>
		<#assign label=label + "for">
	</#if>
	<#if capitalize==true>
		<#assign label=label?cap_first>
	</#if>
	<#if colon==true>
		<#assign label=label+": ">
	</#if>
	${label}
</#macro>

<#function refBuilder link ref refshort parenthesis=false sensu=false accordingTo=false>
	<#assign reference = "">
    <#assign return_reference = "" >
    <#assign referenceTitle = "">
    <#assign finalLink="">
    <#if refshort?has_content == true && ref?has_content == true>
        <#assign reference=refshort>
        <#assign referenceTitle=ref>
    <#elseif refshort?has_content == false && ref?has_content == true>
        <#assign reference=ref>
        <#assign referenceTitle=ref>
    <#elseif refshort?has_content == true && ref?has_content == false>
        <#assign reference=refshort>
        <#assign referenceTitle=refshort>
    </#if>
    <#if reference?has_content>
        <#if sensu == true>
            <#assign return_reference = return_reference + " sensu "> 
        <#elseif accordingTo = true>
            <#if language == "fr">
                <#assign return_reference = return_reference + "selon "> 
            <#else>
                <#assign return_reference = return_reference + "according to ">
            </#if>
        </#if>
        <#if link?has_content == true>
        	<#assign reference = "<a href=\"" + link + "\">" + reference + "</a>">
        </#if>
        <#if parenthesis == true>
            <#assign reference = "(" + reference + ")">
        </#if>
    	<#assign return_reference = return_reference + "<span class=\"reference\" title =\"" + referenceTitle + "\">" + reference + "</span>">
    </#if>
    <#return return_reference>    
</#function>
<#macro languageSwitcher>
	<#if language == "fr">
		<a href="javascript:document.location.href=new Uri(document.location.href).deleteQueryParam('lang');">English</a>
	<#else>
		<a href="javascript:document.location.href=new Uri(document.location.href).replaceQueryParam('lang','fr');">Fran&ccedil;ais</a>
	</#if>
</#macro>

<#macro taxonContent>
	<#nested>
						<#if data.isHybridConcept>
							<h2>${rc.getMessage("taxon_h2_hybrids")}</h2>
							<#list data.hybridParents as hybridParent>
							 <p class="sprite sprite-redirect_${hybridParent.status?lower_case}" />
								 <a href="${rc.getContextUrl('/taxon/'+hybridParent.taxonId?c+rc.getMessage('url_language'))}">${hybridParent.fullScientificName}</a>.
							 </p>
							</#list>
						</#if>
						<h2>${rc.getMessage("taxon_h2_vernaculars")}</h2>
						<ul class="custom_list">
						<#list data.vernacularNames as vernacular>
							<#if vernacular.language == "fr">
								<li class="sprite sprite-${vernacular.status?lower_case}">
									<a href="${rc.getContextUrl('/vernacular/'+vernacular.vernacularId?c+rc.getMessage('url_language'))}">${vernacular.name}</a>
									<span class="right">${refBuilder(vernacular.link,vernacular.reference,vernacular.referenceShort,false,false,false)}</span> 
								</li>
							</#if> 
						</#list>
						</ul>
						<ul class="custom_list">
						<#list data.vernacularNames as vernacular> 
							<#if vernacular.language == "en">
								<li class="sprite sprite-${vernacular.status?lower_case}">
									<a href="${rc.getContextUrl('/vernacular/'+vernacular.vernacularId?c+rc.getMessage('url_language'))}">${vernacular.name}</a>
									<span class="right">${refBuilder(vernacular.link,vernacular.reference,vernacular.referenceShort,false,false,false)}</span>
								</li>
							</#if> 
						</#list>
						</ul>
						
						<h2>${rc.getMessage("taxon_h2_synonyms")}</h2>
						<ul class="custom_list">
						<#list data.synonyms as synonym>
							<li class="sprite sprite-synonym">
								<a href="${rc.getContextUrl('/taxon/'+synonym.taxonId?c+rc.getMessage('url_language'))}">${synonym.fullScientificName}</a>
								<span class="right">${refBuilder(synonym.link,synonym.reference,synonym.referenceShort,false,false,false)}</span>
							</li>
						</#list>
						</ul>
						
						<h2>${rc.getMessage("taxon_h2_distribution")}</h2>
						<ul class="buttons">
							<li><a href="#" class="selected">${rc.getMessage("taxon_button1")}</a></li>
							<li><a href="#">${rc.getMessage("taxon_button2")}</a></li>
						</ul>

						<ul class="distribution_legend custom_list">
							<li class="sprite sprite-native">${rc.getMessage("distribution_native")}</li>
							<li class="sprite sprite-introduced">${rc.getMessage("distribution_introduced")}</li> 
							<li class="sprite sprite-ephemeral">${rc.getMessage("distribution_ephemeral")}</li>
							<li class="sprite sprite-excluded">${rc.getMessage("distribution_excluded")}</li>
							<li class="sprite sprite-extirpated">${rc.getMessage("distribution_extirpated")}</li> 
							<li class="sprite sprite-doubtful">${rc.getMessage("distribution_doubtful")}</li>
							<li class="sprite sprite-absent">${rc.getMessage("distribution_absent")}</li>
						</ul>

						<div id="map_result">
							<img src="${rc.getContextUrl('/images/distribution/'+data.taxonId+'.png')}" width="400" height="400" alt="Distribution: ${data.pageTitle}" name="png" id="png" />
							<p>${rc.getMessage("taxon_msg1",[rc.getContextUrl('/images/distribution/'+data.taxonId+'.svg')])}</p>
						</div>
						<div id="list_result" style="display:none;">
							<#if data.computedDistribution == true>
								<p>${rc.getMessage("taxon_msg2")}</p>
							</#if>
							<ul class="custom_list">
							
							<#list data.distributions as distribution> 
								<li class="sprite sprite-${distribution.status?lower_case}">
									${rc.getMessage("province_" + distribution.province?replace("-","_"))}
									<span class="right">${refBuilder(distribution.link,distribution.reference,distribution.referenceShort,false,false,false)}</span>
								</li>
								<#if distribution.excluded?has_content == true>
								<li class="sprite sprite-redirect">
									${rc.getMessage("excluded_" + distribution.excluded?lower_case)}
								</li>
								</#if>
							</#list>
							</ul>
						</div>
						<h2>${rc.getMessage("taxon_h2_classification")}</h2>
						<table class="custom_table">
							<tbody>
							<#list data.tree as node>
								<#assign indent = node.rankId - 1>				 
								<tr<#if node.taxonId == data.taxonId> class="selected"</#if>><td class="indent_${indent}">${rc.getMessage("rank_" + node.rank?lower_case)}</td><td class="name"><a href="${rc.getContextUrl('/taxon/'+node.taxonId?c+rc.getMessage('url_language'))}">${node.fullScientificName}</a></td></tr>
							</#list>
							</tbody>
						</table>
						
						<h2>${rc.getMessage("taxon_h2_habitus")}</h2>
						<p><#list data.habituses as habitus><#assign str = (rc.getMessage("habitus_"+habitus.habitus?lower_case))?cap_first/><#if habitus_has_next><#assign str=str+", "/></#if>${str}</#list></p>
</#macro>

<#function getI18nContextUrl uri>
	<#return rc.getContextUrl(URLHelper.getUriWithLanguage(uri,rc.getLocale().getLanguage()))>
</#function>
