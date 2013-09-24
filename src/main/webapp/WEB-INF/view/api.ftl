<#include "inc/common.ftl">
<#include "inc/global-functions.ftl">
<#assign page={"title": rc.getMessage("api_title1"),
"cssScreenPrintList": [rc.getContextUrl("/styles/"+formatFileInclude("vascan",currentVersion!,false,".css"))]}>

<#assign currentPage="api"/>

<#include "inc/header.ftl">
<#include "inc/menu.ftl">

<h1>${rc.getMessage("api_title2")}</h1>

<h2>${rc.getMessage("api_title3")}</h2>
<p>${rc.getMessage("api_description")}</p>

<h3>${rc.getMessage("api_title4")}</h3>
<p class="api-path round">
  http://data.canadensys.net/vascan/api/0.1/search.json
  <span class="separator">${rc.getMessage("api_uri_separator")}</span>
  http://data.canadensys.net/vascan/api/0.1/search.xml
</p>
<h3>${rc.getMessage("api_title5")}</h3>
<dl>
  <dt>q</dt>
  <dd>${rc.getMessage("api_parameter_q_definition")}
    <span><em>e.g.</em> ?q=Amaranthus+graecizans</span>
    <span><em>e.g.</em> ?q=1004232|V.+angustifolium</span>
    <span><em>e.g.</em> ?q=5031
  </dd>
</dl>
<h3>${rc.getMessage("api_title6")}</h3>
<pre>
{
  apiVersion: "0.1",
  results: [
    {
      searchedTerm: "Amaranthus graecizans",
      localIdentifier: "3",
      numMatches: 1,
      matches: [
        {
          taxonID: 9946,
          scientificName: "Amaranthus graecizans auct. non Linnaeus p.p.",
          scientificNameAuthorship: "auct. non Linnaeus p.p.",
          canonicalName: "Amaranthus graecizans",
          taxonRank: "species",
          taxonomicAssertions: [
            {
              acceptedNameUsage: "Amaranthus albus Linnaeus",
              acceptedNameUsageID: 2498,
              nameAccordingTo: "FNA Editorial Committee. 2003. Flora of North America...etc.",
              nameAccordingToID: "http://www.efloras.org/volume_page.aspx?volume_id=1004&flora_id=1",
              taxonomicStatus: "synonym"
            },
            {
              acceptedNameUsage: "Amaranthus blitoides S. Watson",
              acceptedNameUsageID: 2499,
              nameAccordingTo: "FNA Editorial Committee. 2003. Flora of North America...etc.",
              nameAccordingToID: "http://www.efloras.org/volume_page.aspx?volume_id=1004&flora_id=1",
              taxonomicStatus: "synonym"
             }
           ]
         }
       ]
     }
  ]
}
</pre>

	</div><#-- content -->
</div>
<#include "inc/footer.ftl">