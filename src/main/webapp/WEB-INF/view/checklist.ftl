<#include "inc/common.ftl">

<#assign page={"title": rc.getMessage("checklist_title1")+ " - " + rc.getMessage("site_title"), "cssList": [rc.getContextUrl("/styles/vascan.css")], "cssPrintList": [rc.getContextUrl("/styles/print.css")], "javaScriptIncludeList": ["http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js", rc.getContextUrl("/js/vascan.js"), rc.getContextUrl("/js/vascan/checklist.js")]}>

<#assign currentPage="checklist"/>

<#include "inc/header.ftl">
<#include "inc/menu.ftl">

<h1>${rc.getMessage("checklist_title1")}</h1>
<form id="checklist_box" class="round custom_form" method="get">
	<ul class="custom_list" id="checklist_header">
		<li class="round big_button selected" id="selection_button">${rc.getMessage("checklist_msg1")}</li>
		<li class="round big_button" id="display_button">${rc.getMessage("checklist_msg2")}</li>
	</ul>

	<div id="selection_criteria" class="checklist_content">
		<p class="alignright">${rc.getMessage("checklist_msg4")}
		<input type="hidden" name="lang" value="${rc.getLocale().getLanguage()}" />
		<select name="habit">
			<option <#attempt>${habit.all}<#recover></#attempt> value="all">${rc.getMessage("checklist_option5")}</option>
			<option <#attempt>${habit.tree}<#recover></#attempt> value="tree">${rc.getMessage("habitus_tree")}</option>
			<option <#attempt>${habit.shrub}<#recover></#attempt> value="shrub">${rc.getMessage("habitus_shrub")}</option>
			<option <#attempt>${habit.herb}<#recover></#attempt> value="herb">${rc.getMessage("habitus_herb")}</option>
			<option <#attempt>${habit.vine}<#recover></#attempt> value="vine">${rc.getMessage("habitus_vine")}</option>
		</select>
		</p>

		<p>${rc.getMessage("checklist_msg3")}</p>
		<p>
			<select name="taxon" id="search_term">
				<option name="0" value="0">- ${rc.getMessage("checklist_msg9")} -</option>
			<#list data.taxons as taxon>
				<option ${taxon.selected} name="${taxon.id?c}" value="${taxon.id?c}">${taxon.calname} (${rc.getMessage("rank_"+taxon.rank?lower_case)})</option>
			</#list>
			</select>
		<p>
		
		<p>${rc.getMessage("checklist_msg5")}
		<select name="combination">
			<option <#attempt>${combination.anyof}<#recover></#attempt> value="anyof">${rc.getMessage("checklist_option2")}</option>
			<option <#attempt>${combination.allof}<#recover></#attempt> value="allof">${rc.getMessage("checklist_option1")}</option>
			<option <#attempt>${combination.only}<#recover></#attempt> value="only">${rc.getMessage("checklist_option3")}</option>
			<option <#attempt>${combination.only_ca}<#recover></#attempt>value="only_ca">${rc.getMessage("checklist_option4")}</option>
		</select>
		${rc.getMessage("checklist_msg6")}</p>
		
		<!-- regions -->
		<div id="checklist_distribution">
			<ul class="custom_list round canada">
				<li><span>${rc.getMessage("region_canada")}</span><input <#attempt>${data.territory.CANADA}<#recover></#attempt> type="checkbox" id="canada" /></li>
			</ul>
			<ul class="custom_list round pacific">
				<li><span><input type="checkbox" class="region"/>${rc.getMessage("region_pacific")}</span>${rc.getMessage("province_BC")}<input <#attempt>${data.territory.BC}<#recover></#attempt> type="checkbox" name="province" class="province" value="BC" /></li>
			</ul>
			<ul class="custom_list round prairies">
				<li><span><input type="checkbox" class="region"/>${rc.getMessage("region_prairies")}</span>${rc.getMessage("province_AB")}<input <#attempt>${data.territory.AB}<#recover></#attempt> type="checkbox" name="province" class="province" value="AB" /></li>
				<li><span></span>${rc.getMessage("province_SK")}<input <#attempt>${data.territory.SK}<#recover></#attempt> type="checkbox" name="province" class="province" value="SK" /></li>
				<li><span></span>${rc.getMessage("province_MB")}<input <#attempt>${data.territory.MB}<#recover></#attempt> type="checkbox" name="province" class="province" value="MB" /></li>
			</ul>
			<ul class="custom_list round central">
				<li><span><input type="checkbox" class="region"/>${rc.getMessage("region_central")}</span>${rc.getMessage("province_ON")}<input <#attempt>${territory.ON}<#recover></#attempt> type="checkbox" name="province" class="province" value="ON" /></li>
				<li><span></span>${rc.getMessage("province_QC")}<input <#attempt>${data.territory.QC}<#recover></#attempt> type="checkbox" name="province" class="province" value="QC" /></li>
			</ul>
			<ul class="custom_list round atlantic">
				<li><span><input type="checkbox" class="region"/>${rc.getMessage("region_atlantic")}</span>${rc.getMessage("province_NB")}<input <#attempt>${territory.NB}<#recover></#attempt> type="checkbox" name="province" class="province" value="NB" /></li>
				<li><span></span>${rc.getMessage("province_PE")}<input <#attempt>${data.territory.PE}<#recover></#attempt> type="checkbox" name="province" class="province" value="PE" /></li>
				<li><span></span>${rc.getMessage("province_NS")}<input <#attempt>${data.territory.NS}<#recover></#attempt> type="checkbox" name="province" class="province" value="NS" /></li>
				<li><span></span>${rc.getMessage("province_NL_N")}<input <#attempt>${data.territory.NL_N}<#recover></#attempt> type="checkbox" name="province" class="province" value="NL_N" /></li>
				<li><span></span>${rc.getMessage("province_NL_L")}<input <#attempt>${data.territory.NL_L}<#recover></#attempt> type="checkbox" name="province" class="province" value="NL_L" /></li>
				<li><span></span>${rc.getMessage("province_PM")}<input <#attempt>${data.territory.PM}<#recover></#attempt> type="checkbox" name="province" class="province" value="PM"/></li>
			</ul>
			<ul class="custom_list round arctic">
				<li><span><input type="checkbox" class="region">${rc.getMessage("region_arctic")}</span>${rc.getMessage("province_YT")}<input <#attempt>${territory.YT}<#recover></#attempt> type="checkbox" name="province" class="province" value="YT"/></li>
				<li><span></span>${rc.getMessage("province_NT")}<input <#attempt>${data.territory.NT}<#recover></#attempt> type="checkbox" name="province" class="province" value="NT"/></li>
				<li><span></span>${rc.getMessage("province_NU")}<input <#attempt>${data.territory.NU}<#recover></#attempt> type="checkbox" name="province" class="province" value="NU"/></li>
				<li><span></span>${rc.getMessage("province_GL")}<input <#attempt>${data.territory.GL}<#recover></#attempt> type="checkbox" name="province" class="province" value="GL"/></li>
			</ul>
		
			<!-- map -->
			<div class="map">
				  <object id="map" width="410" height="410" data="${rc.getContextUrl("/images/distribution_checklist.svg")}" type="image/svg+xml">${rc.getMessage("checklist_msg7")}</object>
			</div>
		</div>
		<p>${rc.getMessage("checklist_msg8")}</p>
		<ul class="distribution_legend custom_list">
			<li class="sprite sprite-native">${rc.getMessage("distribution_native")} <input <#attempt>${data.status.native}<#recover></#attempt> type="checkbox" name="status" id="native" value="native"/></li>
			<li class="sprite sprite-introduced">${rc.getMessage("distribution_introduced")} <input <#attempt>${data.status.introduced}<#recover></#attempt> type="checkbox" name="status" id="introduced" value="introduced"/></li> 
			<li class="sprite sprite-ephemeral">${rc.getMessage("distribution_ephemeral")} <input <#attempt>${data.status.ephemeral}<#recover></#attempt> type="checkbox" name="status" id="ephemeral" value="ephemeral"/></li>
			<li class="sprite sprite-excluded">${rc.getMessage("distribution_excluded")} <input <#attempt>${data.status.excluded}<#recover></#attempt> type="checkbox" name="status" id="excluded" value="excluded"/></li>
			<li class="sprite sprite-extirpated">${rc.getMessage("distribution_extirpated")} <input <#attempt>${data.status.extirpated}<#recover></#attempt> type="checkbox" name="status" id="extirpated" value="extirpated"/></li> 
			<li class="sprite sprite-doubtful">${rc.getMessage("distribution_doubtful")} <input <#attempt>${data.status.doubtful}<#recover></#attempt> type="checkbox" name="status" id="doubtful" value="doubtful"/></li>
		</ul>
	</div>
	
	<div id="display_criteria" class="checklist_content">
		<p>${rc.getMessage("checklist_msg10")}</p>
		<div id="checklist_ranks">
			<ul class="custom_list all">
				<li><input <#attempt>${data.rank.main_rank}<#recover></#attempt>type="checkbox" id="main_rank"> ${rc.getMessage("checklist_msg16")}</li>
				<li><input <#attempt>${data.rank.sub_rank}<#recover></#attempt>type="checkbox" id="sub_rank"> ${rc.getMessage("checklist_msg16")}</li>
			</ul>
			<ul class="custom_list">
				<li><input <#attempt>${data.rank.class}<#recover></#attempt> type="checkbox" class="main_rank" name="rank" id="rank_class" value="class"/> ${rc.getMessage("rank_class")}
				<li><input <#attempt>${data.rank.subclass}<#recover></#attempt> type="checkbox" class="sub_rank" name="rank" id="rank_subclass" value="subclass"/> ${rc.getMessage("rank_subclass")}</li>
				<li><input <#attempt>${data.rank.superorder}<#recover></#attempt> type="checkbox" class="sub_rank" name="rank" id="rank_superorder" value="superorder"/> ${rc.getMessage("rank_superorder")}</li>
			</ul>
			<ul class="custom_list">
				<li><input <#attempt>${data.rank.order}<#recover></#attempt> type="checkbox" class="main_rank" name="rank" id="rank_order" value="order"/> ${rc.getMessage("rank_order")}</li>
			</ul>
			<ul class="custom_list">
				<li><input <#attempt>${data.rank.family}<#recover></#attempt> type="checkbox" class="main_rank" name="rank" id="rank_family" value="family"/> ${rc.getMessage("rank_family")}
				<li><input <#attempt>${data.rank.subfamily}<#recover></#attempt> type="checkbox" class="sub_rank" name="rank" id="rank_subfamily" value="subfamily"/> ${rc.getMessage("rank_subfamily")}</li>
				<li><input <#attempt>${data.rank.tribe}<#recover></#attempt> type="checkbox" class="sub_rank" name="rank" id="rank_tribe" value="tribe"/> ${rc.getMessage("rank_tribe")}</li>
				<li><input <#attempt>${data.rank.subtribe}<#recover></#attempt> type="checkbox" class="sub_rank" name="rank" id="rank_subtribe" value="subtribe"/> ${rc.getMessage("rank_subtribe")}</li>
			</ul>
			<ul class="custom_list">
				<li><input <#attempt>${data.rank.genus}<#recover></#attempt> type="checkbox" class="main_rank" name="rank" id="rank_genus" value="genus"/> ${rc.getMessage("rank_genus")}
				<li><input <#attempt>${data.rank.subgenus}<#recover></#attempt> type="checkbox" class="sub_rank" name="rank" id="rank_subgenus" value="subgenus"/> ${rc.getMessage("rank_subgenus")}</li>
				<li><input <#attempt>${data.rank.section}<#recover></#attempt> type="checkbox" class="sub_rank" name="rank" id="rank_section" value="section"/> ${rc.getMessage("rank_section")}</li>
				<li><input <#attempt>${data.rank.subsection}<#recover></#attempt> type="checkbox" class="sub_rank" name="rank" id="rank_subsection" value="subsection"/> ${rc.getMessage("rank_subsection")}</li>
				<li><input <#attempt>${data.rank.series}<#recover></#attempt> type="checkbox" class="sub_rank" name="rank" id="rank_series" value="series"/> ${rc.getMessage("rank_series")}</li>
			</ul>
			<ul class="custom_list">
				<li><input <#attempt>${data.rank.species}<#recover></#attempt> type="checkbox" class="main_rank" name="rank" id="rank_species" value="species"/> ${rc.getMessage("rank_species")}
				<li><input <#attempt>${data.rank.subspecies}<#recover></#attempt> type="checkbox" class="sub_rank" name="rank" id="rank_subspecies" value="subspecies"/> ${rc.getMessage("rank_subspecies")}</li>
				<li><input <#attempt>${data.rank.variety}<#recover></#attempt> type="checkbox" class="sub_rank" name="rank" id="rank_variety" value="variety"/> ${rc.getMessage("rank_variety")}</li>
			</ul>
			<div class="clear"></div>
		</div>
		<p>${rc.getMessage("checklist_msg11")} <input <#attempt>${data.hybrids.display}<#recover></#attempt> type="checkbox" name="hybrids" id="hybrids" value="true"/>
		<input type="hidden" name="nohybrids" value="false" />
		</p>
		<p>${rc.getMessage("checklist_msg17")} <input <#attempt>${data.limitResults.display}<#recover></#attempt> type="checkbox" name="limitResults" id="limitResults" value="true"/>
		<input type="hidden" name="nolimit" value="false" />
		</p>
		<p>${rc.getMessage("checklist_msg12")}
		<select name="sort" id="sort">
			<option <#attempt>${data.sort.taxonomically}<#recover></#attempt> name="taxonomically" value="taxonomically">${rc.getMessage("checklist_option6")}</option>
			<option <#attempt>${data.sort.alphabetically}<#recover></#attempt> name="alphabetically" value="alphabetically">${rc.getMessage("checklist_option7")}</option>
		</select>
		</p>
	</div>

	<p id="checklist_footer">
		<input type="submit" value="${rc.getMessage("checklist_button1")}"/>
	</p>
</form>	 
<#if data.isSearch == true>
	<h2>
		  ${rc.getMessage("checklist_h2_results")}: ${data.numResults}
		  <span id="results_info" style="float:right;font-size:10px;">
			<#if data.numResults gt 200 && data.limitResults.display != "">
				${rc.getMessage("checklist_msg18")} <a href="#" onclick="$('#limitResults').prop('checked', false);$('#checklist_box').submit();return false;">${rc.getMessage("checklist_show_all")}</a>
			<#elseif data.numResults gte 200>
			${rc.getMessage("checklist_msg19")} <a href="#" onclick="$('#limitResults').prop('checked', true);$('#checklist_box').submit();return false;">${rc.getMessage("checklist_show_partial")}</a>
			</#if>
		  </span>
	</h2>		   
	<#if data.distributions?has_content == true>
	<table class="custom_results_table">
	<thead>
		<tr>
			<th>${rc.getMessage("rank")}</th>
			<th>${rc.getMessage("scientific_name")}</th>
			<th>${rc.getMessage("habitus")}</th>
			<th class="align_center" title="${rc.getMessage("province_BC")}">${rc.getMessage("province_BC_code")}</th>
			<th class="align_center" title="${rc.getMessage("province_AB")}">${rc.getMessage("province_AB_code")}</th>
			<th class="align_center" title="${rc.getMessage("province_SK")}">${rc.getMessage("province_SK_code")}</th>
			<th class="align_center" title="${rc.getMessage("province_MB")}">${rc.getMessage("province_MB_code")}</th>
			<th class="align_center" title="${rc.getMessage("province_ON")}">${rc.getMessage("province_ON_code")}</th>
			<th class="align_center" title="${rc.getMessage("province_QC")}">${rc.getMessage("province_QC_code")}</th>
			<th class="align_center" title="${rc.getMessage("province_NB")}">${rc.getMessage("province_NB_code")}</th>
			<th class="align_center" title="${rc.getMessage("province_PE")}">${rc.getMessage("province_PE_code")}</th>
			<th class="align_center" title="${rc.getMessage("province_NS")}">${rc.getMessage("province_NS_code")}</th>
			<th class="align_center" title="${rc.getMessage("province_NL_N")}">${rc.getMessage("province_NL_N_code")}</th>
			<th class="align_center" title="${rc.getMessage("province_NL_L")}">${rc.getMessage("province_NL_L_code")}</th>
			<th class="align_center" title="${rc.getMessage("province_PM")}">${rc.getMessage("province_PM_code")}</th>
			<th class="align_center" title="${rc.getMessage("province_YT")}">${rc.getMessage("province_YT_code")}</th>
			<th class="align_center" title="${rc.getMessage("province_NT")}">${rc.getMessage("province_NT_code")}</th>
			<th class="align_center" title="${rc.getMessage("province_NU")}">${rc.getMessage("province_NU_code")}</th>
			<th class="align_center" title="${rc.getMessage("province_GL")}">${rc.getMessage("province_GL_code")}</th>
		</tr>
	</thead>
	
	<tbody>
		<#list data.distributions as distribution>
		<tr class="background_center">
			<td>${rc.getMessage("rank_"+distribution.rank?lower_case)}</td>
			<td><a href="${rc.getContextUrl("/taxon/"+distribution.taxonId?c+rc.getMessage("url_language"))}">${distribution.fullScientificName}</a></td>
			<td><#list distribution.habit as habitus><#assign str = rc.getMessage("habitus_"+habitus.habit?lower_case)/><#if habitus_has_next><#assign str=str+", "/></#if>${str}</#list></td>
			<td class="distribution_${distribution.BC}"></td>
			<td class="distribution_${distribution.AB}"></td>
			<td class="distribution_${distribution.SK}"></td>
			<td class="distribution_${distribution.MB}"></td>
			<td class="distribution_${distribution.ON}"></td>
			<td class="distribution_${distribution.QC}"></td>
			<td class="distribution_${distribution.NB}"></td>
			<td class="distribution_${distribution.PE}"></td>
			<td class="distribution_${distribution.NS}"></td>
			<td class="distribution_${distribution.NL_N}"></td>
			<td class="distribution_${distribution.NL_L}"></td>
			<td class="distribution_${distribution.PM}"></td>
			<td class="distribution_${distribution.YT}"></td>
			<td class="distribution_${distribution.NT}"></td>
			<td class="distribution_${distribution.NU}"></td>
			<td class="distribution_${distribution.GL}"></td>
		</tr>
		</#list>
	</tbody>
	</table>
	</#if>
<#else>
<h2>${rc.getMessage("checklist_h2_examples")}</h2>
<ul>
	<li><a href="${rc.getContextUrl("/checklist?taxon=0&habit=tree&combination=anyof&province=PM&status=native&status=introduced&status=ephemeral&status=excluded&status=extirpated&status=doubtful")}">${rc.getMessage("checklist_msg13")}</a></li>
	<li><a href="${rc.getContextUrl("/checklist?taxon=193&combination=allof&province=YT&province=NT&province=NU&province=GL&status=native")}">${rc.getMessage("checklist_msg14")}</a></li>
	<li><a href="${rc.getContextUrl("/checklist?taxon=0&combination=only&province=AB&province=SK&province=MB&status=introduced")}">${rc.getMessage("checklist_msg15")}</a></li>
</ul>
</#if>
	</div><#-- content -->
</div>
<#include "inc/footer.ftl">