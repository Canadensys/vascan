<#include "inc/common.ftl">
<#include "inc/global-functions.ftl">

<head>
<title>${rc.getMessage("api_title1")+ " - " + rc.getMessage("site_title")}</title>
<@cssAsset fileName="vascan" version=currentVersion! useMinified=false/>
</head>

<#assign currentPage="api"/>
<#include "inc/menu.ftl">

<h1>${rc.getMessage("api_title2")}</h1>

<ul class="index">
	<li><a href="#resource">${rc.getMessage("api_title4")}</a></li>
	<li><a href="#openrefine">${rc.getMessage("api_title7")}</a></li>
	<li><a href="#taxize">${rc.getMessage("api_title8")}</a></li>
</ul>

<a name="resource"></a><h2>${rc.getMessage("api_title4")}</h2>
<p>${rc.getMessage("api_rest_description")}</p>
<p class="api-path round">
  http://data.canadensys.net/vascan/api/0.1/search.json
  <span class="separator">${rc.getMessage("api_uri_separator")}</span>
  http://data.canadensys.net/vascan/api/0.1/search.xml
</p>
<h3>${rc.getMessage("api_title5")}</h3>
<dl>
  <dt>q</dt>
  <dd>${rc.getMessage("api_parameter_q_definition")}
    <span><em>e.g.</em> ?q=Crataegus dodgei</span>
    <span><em>e.g.</em> ?q=1004232|${rc.getMessage("api_vernacular")}</span>
    <span><em>e.g.</em> ?q=5031
  </dd>
</dl>
<h3>${rc.getMessage("api_title6")}</h3>
<pre>
{
    apiVersion: "0.1",
    lastUpdatedDate: "YYYY-MM-DD",
    results: [
        {
            searchedTerm: "Crataegus dodgei",
            numMatches: 1,
            matches: [
                {
                    taxonID: 8673,
                    scientificName: "Crataegus dodgei Ashe",
                    scientificNameAuthorship: "Ashe",
                    canonicalName: "Crataegus dodgei",
                    taxonRank: "species",
                    taxonomicAssertions: [
                        {
                            acceptedNameUsage: "Crataegus dodgei Ashe",
                            acceptedNameUsageID: 8673,
                            nameAccordingTo: "FNA Editorial Committee. in prep. Flora of North America north of Mexico...etc",
                            nameAccordingToID: "",
                            taxonomicStatus: "accepted",
                            parentNameUsageID: 2408,
                            higherClassification: "Equisetopsida;Magnoliidae;Rosanae;Rosales;Rosaceae;Spiraeoideae;Pyreae;Crataegus;Crataegus sect. Coccineae;Crataegus ser. Rotundifoliae"
                        }
                    ],
                    vernacularNames: [
                        {
                            vernacularName: "aubépine de Dodge",
                            language: "fr",
                            source: "McMurray, S. & A. Lehela. 1999. Ontario Plant List - Digital Version 1.0...etc",
                            preferredName: true
                        },
                        {
                            vernacularName: "Dodge's hawthorn",
                            language: "en",
                            source: "",
                            preferredName: true
                        },
                        {
                            vernacularName: "aubépine jaunâtre",
                            language: "fr",
                            source: "Cuerrier, A. Proposition de nom français. (pers. comm.)",
                            preferredName: false
                        },
                        {
                            vernacularName: "yellowish hawthorn",
                            language: "en",
                            source: "Anions, M., Director of Science, NatureServe Canada (pers. comm.)",
                            preferredName: false
                        },
                        {
                            vernacularName: "yellowish Dodge's hawthorn",
                            language: "en",
                            source: "FNA Editorial Committee. in prep. Flora of North America north of Mexico. Volume 17...etc",
                            preferredName: false
                        }
                    ],
                    distribution: [
                        {
                            locationID: "ISO 3166-2:CA-ON",
                            locality: "ON",
                            establishmentMeans: "native",
                            occurrenceStatus: "native"
                        },
                        {
                            locationID: "ISO 3166-2:CA-QC",
                            locality: "QC",
                            establishmentMeans: "native",
                            occurrenceStatus: "native"
                        }
                    ]
                }
            ]
        }
    ]
}
</pre>

<a name="openrefine"></a><h2>${rc.getMessage("api_title7")}</h2>
<p>${rc.getMessage("api_openrefine_description")}</p>
<p class="api-path round">
  http://data.canadensys.net/vascan/refine/0.1/reconcile
</p>

<a name="taxize"></a><h2>${rc.getMessage("api_title8")}</h2>
<p>${rc.getMessage("api_taxize_description")}</p>
<p class="api-path round">
  https://cran.r-project.org/web/packages/taxize/index.html
</p>

  </div><#-- content -->
</div>