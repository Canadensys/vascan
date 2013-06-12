<#include "inc/common.ftl">

<#assign page={"title": rc.getMessage("about_title1"), "cssList": [rc.getContextUrl("/styles/vascan.css")], "cssPrintList": [rc.getContextUrl("/styles/print.css")]}>

<#assign currentPage="about"/>

<#include "inc/header.ftl">
<#include "inc/menu.ftl">

<h1>${rc.getMessage("about_title1")}</h1>
<#if rc.getLocale().getLanguage()=="en">
<ul class="index">
	<li><a href="about/#introduction">Introduction</a></li>
	<li><a href="about/#rationale">Rationale</a></li>
	<li><a href="about/#people">People</a></li>
	<li><a href="about/#rights">Citation, rights and disclaimer</a></li>
	<li><a href="about/#feedback">Feedback</a></li>
	<li><a href="about/#distribution">Distribution status codes</a></li>
</ul>
<a name="introduction"></a><h2>Introduction</h2>
<p><a href="http://data.canadensys.net/vascan/">VASCAN</a> is the <strong>Database of Vascular Plants of Canada</strong>. It is a comprehensive list of all vascular plants reported in Canada, Greenland (Denmark) and Saint Pierre and Miquelon (France). VASCAN is literature-based, though recent additions are sometimes specimen-based.</p>
<p>For every species, subspecies and variety in VASCAN we provide the accepted <strong>scientific name</strong> (Latin), the accepted French and English <strong>vernacular name</strong>, and their <strong>synonyms/alternatives</strong> in Canada. We indicate the <strong>distribution</strong> status (<a href="about/#distribution_status">native, introduced, etc.</a>) of the plant for each province or territory, and the <strong>habit</strong> (tree, shrub, herb or vine) of the plant in Canada. For reported hybrids (nothotaxa or hybrid formulas), we also provide the <strong>hybrid parents</strong><a href="about/#remark">*</a>, except for introduced hybrids. We refer to a <strong>source</strong> for all name, classification and distribution information.</p>
<p>All taxa are linked to a classification. We follow <a href="http://www.mapress.com/phytotaxa/content/2011/f/pt00019p054.pdf">Christenhusz et al. (2011a)</a> for lycophytes, <a href="http://www.jstor.org/stable/25065646">Smith et al. (2006)</a> for ferns, <a href="http://www.mapress.com/phytotaxa/content/2011/f/pt00019p070.pdf">Christenhusz et al. (2011b)</a> for gymnosperms, <a href="http://dx.doi.org/10.1111/j.1095-8339.2009.00996.x">APG III (2009)</a> for flowering plants and <a href="http://dx.doi.org/10.1111/j.1095-8339.2009.01002.x">Chase and Reveal (2009)</a> for the higher taxonomy. We do not provide core information (synonyms, vernacular names, etc.) for most higher taxa (above species), but we do calculate a distribution<a href="about/#remark">*</a> and habit based on lower taxa.</p>
<p>VASCAN does not include information on conservation status, invasiveness or weediness. Please use websites such as <a href="http://www.natureserve-canada.ca/">NatureServe Canada</a>, <a href="http://www.invasivespecies.gc.ca/">Invasive Species</a> or <a href="http://www.weedinfo.ca/">Weed Info</a> to find such information. VASCAN should not be used as an authoritative source for scientific names or taxon concepts. Please use sources such as the <a href="http://www.ipni.org">International Plant Names Index (IPNI)</a>, <a href="http://www.tropicos.org">Tropicos</a>, the <a href="http://www.ars-grin.gov/">Germplasm Resources Information Network (GRIN)</a>, the <a href="http://botany.si.edu/ing/">Index Nominum Genericorum (ING)</a> or the <a href="http://www.plantsystematics.org/reveal/pbio/WWW/supragen.html">Index Nominum Supragenericorum Plantarum Vascularium</a> instead.</p>
<p><a name="remark"></a>* Not included in the <a href="${rc.getMessage("url_dataset")}">dataset download</a> for technical reasons.</p>
<a name="rationale"></a><h2>Rationale</h2>
<p>VASCAN developed from the need to validate data from eastern Canada (Ontario and eastward), Greenland and Saint Pierre and Miquelon for the <a href="http://www.fna.org/">Flora of North America (FNA)</a> project and from the need to provide French vernacular names for taxa present in Quebec in the FNA. It expanded when <a href="http://www.pc.gc.ca/">Parks Canada</a> wanted to harmonize the names from vascular plant species lists of its parks across the country. At the time we also realized that - aside from the now taxonomically more or less out-of-date flora by Scoggan (1978-1979) - there was not only no standardized scientific name list for the country - despite worthwhile efforts from the Synthesis of the North American Flora (<a href="http://www.bonap.org/">BONAP</a>) and <a href="http://plants.usda.gov/">PLANTS</a> - but also no standardized source of Canadian English and French vernacular names. Several endemic taxa of conservation concern in Canada had been neglected in floras or web-based lists. Names used for plants in English Canada are not necessarily those used in the United States, for instance, and thus U.S. sources were not always appropriate for this goal. Finally, several national organizations expressed the need for a web-based list of Canadian taxa, with data on provincial/territorial distribution.</p>
<p>The current goal of VASCAN is to provide an up-to-date, documented source of the names of vascular plants in Canada, Greenland, and Saint Pierre and Miquelon, both scientific or vernacular. The latter two are added because their floras are intimately related to that of Canada and it is useful for Canadians and others to know about them. VASCAN has no pretention to be an official list for these polities. Another source of names for living Canadian organisms is <a href="http://www.cbif.gc.ca/pls/itisca/">ITIS Canada</a>, with which we are collaborating closely. Provincial distributions are provided to help Canadians visualize the relationship among the floras of their provinces and territories. VASCAN does not intend to replace regional or provincial lists but to act as a complement to them. Likewise, the taxonomy adopted by VASCAN, while aiming to be as current as possible, does not preclude the use of alternate taxonomic concepts in other floras, though with time it is hoped that we will be able to converge on a single accepted taxonomy, at least at the species level. This is, after all, an avowed international goal. In that respect, VASCAN adopted the taxonomy published in the <a href="http://www.fna.org/">Flora of North America (FNA)</a>, provided that it accounted for all the taxa reported to date in Canada. Progress in taxonomy and phylogeny since the publication of individual FNA volumes, however, means that the taxonomy of VASCAN sometimes differs from it.</p>
<a name="people"></a><h2>People</h2>
<h3>Data compilation</h3>
<ul>
	<li><a href="http://www.irbv.umontreal.ca/luc-brouillet?lang=en">Luc Brouillet</a>, <a href="http://www.biodiversite.umontreal.ca/?lang=en">Université de Montréal Biodiversity Centre</a>, <a href="http://www.biodiversite.umontreal.ca/herbier-marie-victorin?lang=en">Marie-Victorin Herbarium (MT)</a> - Coordination and design, compilation of taxonomic and distribution data</li>
	<li>F. Coursol, <a href="http://www2.ville.montreal.qc.ca/jardin/">Montréal Botanical Garden</a> - Compilation of taxonomic and distribution data</li>
	<li>Susan Meades, <a href="http://www.northernontarioflora.ca/">Northern Ontario Plant Database</a> - Compilation of taxonomic data</li>
	<li>Marc Favreau, <a href="http://www.btb.gc.ca/">Translation Bureau</a> - Compilation of French vernacular names</li>
	<li>Marilyn Anions, <a href="http://www.natureserve-canada.ca/">NatureServe Canada</a> - Compilation of English vernacular names</li>
</ul>

<h3>Development</h3>
<ul>
	<li><a href="http://www.linkedin.com/in/peterdesmet">Peter Desmet</a>, <a href="http://www.biodiversite.ca/">Université de Montréal Biodiversity Centre</a> - Coordination, database and web design</li>
	<li><a href="http://www.linkedin.com/in/pierrebelisle">Pierre Bélisle</a>, <a href="http://www.biodiversite.ca/">Université de Montréal Biodiversity Centre</a> - Database and web development</li>
	<li><a href="http://ca.linkedin.com/in/christiangendreau">Christian Gendreau</a>, <a href="http://www.biodiversite.ca/">Université de Montréal Biodiversity Centre</a> - Maintenance</li>
	<li>Patrick O'Reilly, Université de Montréal - Data validation and import</li>
</ul>
<h3>Collaboration</h3>
<ul>
	<li>George Argus, <a href="http://nature.ca/">Canadian Museum of Nature</a></li>
	<li>Sean Blaney, <a href="http://www.accdc.com/">Atlantic Canada Conservation Data Center</a></li>
	<li>Bruce Bennett, <a href="http://www.environmentyukon.gov.yk.ca/">Environment Yukon</a></li>
	<li>Jacques Cayouette, <a href="http://www4.agr.gc.ca/AAFC-AAC/display-afficher.do?id=1180546650582">Eastern Cereal and Oilseed Research Centre</a></li>
	<li><a href="http://www.irbv.umontreal.ca/alain-cuerrier?lang=en">Alain Cuerrier</a>, <a href="http://www2.ville.montreal.qc.ca/jardin">Montreal Botanical Garden</a></li>
	<li>Roger Etcheberry, <a href="http://www.grandcolombier.com/nature/">Saint Pierre and Miquelon</a></li>
	<li><a href="http://home.cc.umanitoba.ca/~bford/">Bruce Ford</a>, <a href="http://umanitoba.ca/">University of Manitoba</a></li>
	<li>Bent Fredskild</li>
	<li><a href="http://www.huh.harvard.edu/research/faculty_staff/gandhi.html">Kanchi Gandhi</a>, <a href="http://www.huh.harvard.edu/">Harvard University Herbaria</a></li>
	<li><a href="http://www.nature.ca/en/about-us/museum-corporation/staff-directory/biography-lynn-j-gillespie">Lynn Gillespie</a>, <a href="http://nature.ca/en/home">Canadian Museum of Nature</a></li>
	<li>Joyce Gould, <a href="http://www.tpr.alberta.ca/parks/heritageinfocentre/default.aspx">Alberta Tourism, Parks and Recreation</a></li>
	<li>Jason Greenall, <a href="http://www.gov.mb.ca/conservation/cdc/">Manitoba Conservation Data Centre</a></li>
	<li>Geoffrey Halliday</li>
	<li>Claudia Hanel, <a href="http://www.env.gov.nl.ca/env/">Newfoundland and Labrador Department of Environment and Conservation</a></li>
	<li>Vernon Harms, <a href="http://www.usask.ca/">University of Saskatchewan</a></li>
	<li>Stuart Hay, <a href="http://www.biodiversite.ca/">Université de Montréal Biodiversity Centre</a></li>
	<li>Jacques Labrecque, <a href="http://www.cdpnq.gouv.qc.ca/">Centre de données sur le patrimoine naturel du Québec</a></li>
	<li>John Maunder, <a href="http://therooms.ca/museum/">The Rooms Provincial Museum</a></li>
	<li>Marian Munro, <a href="http://museum.gov.ns.ca/mnhnew/en/home/whattoseedo/collections/botany.aspx">Nova Scotia Museum</a></li>
	<li>Mike Oldham, <a href="http://nhic.mnr.gov.on.ca/">Ontario Natural Heritage Information Centre</a></li>
	<li>Elizabeth Punter, <a href="http://umanitoba.ca/">University of Manitoba</a></li>
	<li><a href="http://www.jcsemple.uwaterloo.ca">John C. Semple</a>, <a href="http://uwaterloo.ca/">University of Waterloo</a></li>
</ul>
<h3>Funding</h3>
<p>Partial funding for the project was provided by:</p>
<ul>
	<li><a href="http://www.pc.gc.ca/">Parks Canada</a></li>
	<li><a href="http://www.cbif.gc.ca/home_e.php">Canadian Biodiversity Information Facility (CBIF)</a></li>
	<li><a href="http://www.natureserve-canada.ca/">NatureServe Canada</a></li>
</ul>

<a name="rights"></a><h2>Citation, rights and disclaimer</h2>
<h3>Preferred citation</h3>
<p>Brouillet, L., F. Coursol, S.J. Meades, M. Favreau, M. Anions, P. Bélisle & P. Desmet. 2010+. VASCAN, the Database of Vascular Plants of Canada. <a href="http://data.canadensys.net/vascan/">http://data.canadensys.net/vascan/</a> (consulted on ${currentDate})</p>
<p><strong>For a single taxon:</strong><br />
Brouillet et al. 2010+. <em>Acer saccharum</em> Marshall in VASCAN, the Database of Vascular Plants of Canada. <a href="http://data.canadensys.net/vascan/taxon/9214">http://data.canadensys.net/vascan/taxon/9214</a> (consulted on ${currentDate})</p>
<p><strong>For a single vernacular name:</strong><br />
Brouillet et al. 2010+. sugar maple in VASCAN, the Database of Vascular Plants of Canada. <a href="http://data.canadensys.net/vascan/vernacular/25478">http://data.canadensys.net/vascan/vernacular/25478</a> (consulted on ${currentDate})</p>
<h3>Intellectual property rights</h3>
<p><a href="http://creativecommons.org/publicdomain/zero/1.0/" title="CC0 1.0 Universal Public Domain Dedication"><img class="alignright" src="http://data.canadensys.net/common/images/cc0-logo.png" /></a> All data in the <a href="http://data.canadensys.net/vascan">Database of Vascular Plants of Canada (VASCAN)</a> have been released into the public domain under the <a href="http://creativecommons.org/publicdomain/zero/1.0/">CC0 1.0 Universal Public Domain Dedication</a>. Although you are free to use the data as you want, we advise you to <a href="http://www.canadensys.net/norms">follow these norms</a>. We have also released the <a href="http://www.canadensys.net/vascan">VASCAN source code and database model</a>.</p>
<h3>Disclaimer</h3>
<p>Although we make every effort to provide accurate and complete information in the VASCAN database, we take <strong>no responsibility</strong> with regard to the use, accuracy, reliability or completeness of the provided data.</p>

<a name="feedback"></a><h2>Feedback</h2>
<p>We are dependent on colleagues and users to help us improve the database and we <a href="http://code.google.com/p/canadensys/issues/entry?template=Vascan%20-%20Data%20issue">welcome suggestions</a> backed by authoritative data. This is a collective effort after all, built upon the work done by our illustrious predecessors (many of which are cited in the database), as well as a number of collaborators.</p>
<p>Feedback on the data or interface can be submitted by using the <strong>feedback button</strong> on the right or by clicking one of the links below. You'll need a <a href="https://www.google.com/accounts/NewAccount">Google Account</a> to do so. All feedback is public on <a href="http://code.google.com/p/canadensys/issues/list">this website</a>.</p>
<ul>
	<li><a href="http://code.google.com/p/canadensys/issues/entry?template=Vascan%20-%20Data%20issue">Report a data issue</a></li>
	<li><a href="http://code.google.com/p/canadensys/issues/entry?template=Vascan%20-%20Interface%20issue">Report an interface issue</a></li>
</ul>
<p>VASCAN updates and tips are announced via <a href="http://www.twitter.com/canadensys/">@Canadensys</a> on Twitter.</p>

<a name="distribution"></a><h2>Distribution status codes</h2>
<p>We use the following codes and colours for the distribution status:</p>
<ul class="custom_list">
	<li class="sprite sprite-native"><strong>Native</strong> - Taxon present as a result of natural processes only, without human agency.</li>
	<li class="sprite sprite-introduced"><strong>Introduced</strong> - Taxon established (naturalized) in a region outside of its original range, as a result of human activity, either deliberate or accidental. Taxa are considered introduced in Canada when they became established after European colonization.</li>
	<li class="sprite sprite-ephemeral"><strong>Ephemeral</strong> - Taxon not established permanently in the region, but recurring in the wild on a near-annual basis, usually from cultivation (e.g. <a href="name/wheat">wheat</a>, <a href="name/tomato">tomato</a>, etc.).</li>
	<li class="sprite sprite-excluded"><strong>Excluded</strong> - Taxon reported from the region, but not established or erroneously determined.</li>
	<li class="sprite sprite-extirpated"><strong>Extirpated</strong> - Taxon native to the region, but currently considered eradicated. This status is only given after active search, and is usually determined by a conservation agency.</li>
	<li class="sprite sprite-doubtful"><strong>Doubtful</strong> - Taxon reported from the region by a source, but information not yet validated.</li>
	<li class="sprite sprite-absent"><strong>Absent</strong> - Taxon not reported from the region.</li>
</ul>
<#elseif rc.getLocale().getLanguage()=="fr">
<ul class="index">
	<li><a href="about/?lang=fr#introduction">Introduction</a></li>
	<li><a href="about/?lang=fr#rationale">Objectifs</a></li>
	<li><a href="about/?lang=fr#people">Personnes</a></li>
	<li><a href="about/?lang=fr#rights">Citation, droits et avis de non-responsabilité</a></li>
	<li><a href="about/?lang=fr#feedback">Commentaires</a></li>
	<li><a href="about/?lang=fr#distribution">Légende des états de répartition</a></li>
</ul>
<a name="introduction"></a><h2>Introduction</h2>
<p><a href="http://data.canadensys.net/vascan?lang=fr">VASCAN</a> est la <strong>Base de données des plantes vasculaires du Canada</strong>. Il s'agit d'une liste complète des plantes vasculaires signalées au Canada, au Groënland (Danemark) et à Saint-Pierre-et-Miquelon (France). La base de données VASCAN est fondée sur les publications disponibles, mais certaines additions récentes sont basées sur des spécimens.</p>
<p>Pour chaque espèce, sous-espèce ou variété, VASCAN fournit le nom <strong>scientifique accepté</strong> (latin), les <strong>noms vernaculaires</strong> français et anglais acceptés et les <strong>synonymes ou autres noms utilisés</strong> au Canada. Nous donnons l'état de <strong>répartition</strong> (indigène, introduit, etc.) de la plante dans chaque province ou territoire et le <strong>port</strong> (arbre, arbuste, plante herbacée ou liane) de la plante au Canada. Dans le cas des hybrides non introduits (nothotaxons ou formules hybrides), nous donnons aussi les <strong>parents de l'hybride</strong><a href="about/?lang=fr#remark">*</a>. Nous indiquons une <strong>source</strong> pour tous les noms et toutes les informations concernant la classification et la répartition.</p>

<p>Tous les taxons sont liés à une classification particulière. Nous suivons <a href="http://www.mapress.com/phytotaxa/content/2011/f/pt00019p054.pdf">Christenhusz et al. (2011a)</a> pour les lycophytes, <a href="http://www.jstor.org/stable/25065646">Smith et al. (2006)</a> (2006) pour les fougères et plantes alliées, <a href="http://www.mapress.com/phytotaxa/content/2011/f/pt00019p070.pdf">Christenhusz et al. (2011b)</a> pour les gymnosperms, <a href="http://dx.doi.org/10.1111/j.1095-8339.2009.00996.x">APG III (2009)</a> pour les plantes à fleurs ainsi que <a href="http://dx.doi.org/10.1111/j.1095-8339.2009.01002.x">Chase et Reveal (2009)</a> pour la taxonomie supérieure. Nous ne fournissons pas d'information taxonomique (synonymes, noms vernaculaires, etc.) pour la plupart des taxons supérieurs (au-dessus du rang de l'espèce), mais nous en évaluons la répartition<a href="about/?lang=fr#remark">*</a> et le port à partir des données visant les taxons inférieurs.</p>
<p>La base de données VASCAN ne contient pas d'information sur le statut de conservation ou le caractère envahissant ou nuisible des plantes. Pour des informations de ce type, veuillez consulter des sites tels que <a href="http://www.natureserve-canada.ca/fr/index.html">NatureServe Canada</a>, <a href="http://www.invasivespecies.gc.ca/">Espèces envahissantes</a> ou <a href="http://www.weedinfo.ca/">Mauvaises herbes</a>. VASCAN ne devrait pas servir de source première pour les noms scientifiques ou les concepts taxonomiques. Veuillez plutôt utiliser des ressources comme <a href="http://www.ipni.org">International Plant Names Index (IPNI)</a>, <a href="http://www.tropicos.org">Tropicos</a>, <a href="http://www.ars-grin.gov/">Germplasm Resources Information Network (GRIN)</a>, <a href="http://botany.si.edu/ing/">Index Nominum Genericorum (ING)</a> ou <a href="http://www.plantsystematics.org/reveal/pbio/WWW/supragen.html">Index Nominum Supragenericorum Plantarum Vascularium</a>.</p>
<p><a name="remark"></a>* Non inclus dans les <a href="${rc.getMessage("url_dataset")}">téléchargements de jeux de données</a>, pour des raisons techniques.</p>
<a name="rationale"></a><h2>Objectifs</h2>
<p>La création de VASCAN est née du besoin de valider les informations recueillies pour l'est du Canada (Ontario vers l'est), le Groënland et Saint-Pierre-et-Miquelon pour le projet <a href="http://www.fna.org/">Flora of North America (FNA)</a> et d'y ajouter des noms vernaculaires français pour le Québec. Le projet prit de l'ampleur quand <a href="http://www.pc.gc.ca/">Parcs Canada</a> a voulu harmoniser la liste des plantes vasculaires présentes dans l'ensemble de ses parcs. Nous avions réalisé que, outre la flore de Scoggan (1978-1979), taxonomiquement obsolète, il n'existait pas de liste standard des noms scientifiques pour le pays, en dépit des efforts notables de la Synthesis of the North American Flora (<a href="http://www.bonap.org/">BONAP</a>) et de <a href="http://plants.usda.gov/">PLANTS</a>. De plus, il n'y avait pas de source standard pour les noms vernaculaires canadiens français ou anglais. Plusieurs taxons endémiques au Canada avaient été négligés dans les flores et les listes disponibles sur le web. Les noms utilisés au Canada anglais n'étant pas nécessairement ceux en usage aux États-Unis, les sources américaines n'étaient pas toujours appropriées dans ce but. Finalement, plusieurs organismes nationaux avaient exprimé le besoin d'une liste nationale des taxons canadiens, disponible sur le web, avec des données sur leur répartition par provinces ou territoires.</p>
<p>Le but de VASCAN est de fournir une source à jour documentée pour les noms scientifiques et vernaculaires des plantes vasculaires du Canada, du Groënland et de Saint-Pierre-et-Miquelon. Ces deux derniers territoires ont été ajoutés parce que leur flore est intimement liée à celle du Canada et qu'il est utile pour les Canadiens et pour d'autres de la connaître. VASCAN n'a pas la prétention de fournir une liste officielle des plantes de ces territoires. Par ailleurs, une autre source de noms pour les organismes vivants du Canada est <a href="http://www.cbif.gc.ca/pls/itisca/">ITIS Canada</a>, avec qui nous collaborons étroitement. Les répartitions par provinces visent à aider les Canadiens à visualiser les relations existant entre les flores des diverses provinces et territoires. VASCAN ne vise pas à remplacer les listes régionales ou provinciales, mais à les compléter. Bien que la taxonomie adoptée par VASCAN se veuille le plus à jour possible, elle n'exclut pas l'utilisation d'autres concepts taxonomiques dans diverses flores; cependant, nous espérons qu'avec le temps il y aura convergence vers une seule taxonomie acceptée, au moins au niveau de l'espèce. C'est, après tout, un objectif international déclaré. Dans ce but, VASCAN adopte la taxonomie publiée dans la <a href="http://www.fna.org/">Flora of North America (FNA)</a> lorsque celle-ci tient compte de tous les taxons signalés jusqu'à présent au Canada. Cependant, les progrès de la taxonomie et de la phylogénèse survenus depuis la parution des divers volumes de FNA font en sorte que la taxonomie de VASCAN peut parfois en différer.</p>					
<a name="people"></a><h2>Personnes</h2>
<h3>Compilation des données</h3>
<ul>
	<li><a href="http://www.irbv.umontreal.ca/luc-brouillet">Luc Brouillet</a>, <a href="http://www.biodiversite.umontreal.ca/">Centre sur la biodiversité</a> (<a href="http://www.umontreal.ca/">Université de Montréal</a>), <a href="http://www.biodiversite.umontreal.ca/herbier-marie-victorin">Herbier Marie-Victorin (MT)</a> - Coordination et conception, compilation des données de taxonomie et de répartition</li>
	<li>F. Coursol, <a href="http://www2.ville.montreal.qc.ca/jardin/">Jardin botanique de Montréal</a> - Compilation des données de taxonomie et de répartition</li>
	<li>Susan Meades, <a href="http://www.northernontarioflora.ca/">Northern Ontario Plant Database</a> - Compilation des données de taxonomie</li>
	<li>Marc Favreau, <a href="http://www.btb.gc.ca/">Bureau de la traduction</a> - Compilation des noms vernaculaires français</li>
	<li>Marilyn Anions, <a href="http://www.natureserve-canada.ca/">NatureServe Canada</a> - Compilation des noms vernaculaires anglais</li>
</ul>

<h3>Développement</h3>
<ul>
	<li><a href="http://www.linkedin.com/in/peterdesmet">Peter Desmet</a>, <a href="http://www.biodiversite.ca/">Centre sur la biodiversité</a> - Coordination, conception de la base de données et de l'interface web</li>
	<li><a href="http://www.linkedin.com/in/pierrebelisle">Pierre Bélisle</a>, <a href="http://www.biodiversite.ca/">Centre sur la biodiversité</a> - Développement de la base de données et de l'interface web</li>
	<li><a href="http://ca.linkedin.com/in/christiangendreau">Christian Gendreau</a>, <a href="http://www.biodiversite.ca/">Centre sur la biodiversité</a> - Entretien</li>
	<li>Patrick O'Reilly, Université de Montréal - Validation et importation des données</li>
</ul>
<h3>Collaboration</h3>
<ul>
	<li>George Argus, <a href="http://nature.ca/">Musée canadien de la nature</a></li>
	<li>Sean Blaney, <a href="http://www.accdc.com/">Centre de données sur la conservation du Canada atlantique</a></li>
	<li>Bruce Bennett, <a href="http://www.environmentyukon.gov.yk.ca/">Environment Yukon</a></li>
	<li>Jacques Cayouette, <a href="http://www4.agr.gc.ca/AAFC-AAC/display-afficher.do?id=1180546650582">Centre de recherches de l'Est sur les céréales et les oléagineux</a></li>
	<li><a href="http://www.irbv.umontreal.ca/alain-cuerrier">Alain Cuerrier</a>, <a href="http://www2.ville.montreal.qc.ca/jardin">Jardin Botanique de Montréal</a></li>
	<li>Roger Etcheberry, <a href="http://www.grandcolombier.com/nature/">Saint-Pierre-et-Miquelon</a></li>
	<li><a href="http://home.cc.umanitoba.ca/~bford/">Bruce Ford</a>, <a href="http://umanitoba.ca/">University of Manitoba</a></li>
	<li>Bent Fredskild</li>
	<li><a href="http://www.huh.harvard.edu/research/faculty_staff/gandhi.html">Kanchi Gandhi</a>, <a href="http://www.huh.harvard.edu/">Harvard University Herbaria</a></li>
	<li><a href="http://www.nature.ca/fr/sujet-musee/mission-organisation/annuaire-personnel/biographie-lynn-j-gillespie">Lynn Gillespie</a>, <a href="http://nature.ca/">Musée canadien de la nature</a></li>
	<li>Joyce Gould, <a href="http://www.tpr.alberta.ca/parks/heritageinfocentre/default.aspx">Alberta Tourism, Parks and Recreation</a></li>
	<li>Jason Greenall, <a href="http://www.gov.mb.ca/conservation/cdc/">Manitoba Conservation Data Centre</a></li>
	<li>Geoffrey Halliday</li>
	<li>Claudia Hanel, <a href="http://www.env.gov.nl.ca/env/">Newfoundland and Labrador Department of Environment and Conservation</a></li>
	<li>Vernon Harms, <a href="http://www.usask.ca/">University of Saskatchewan</a></li>
	<li>Stuart Hay, <a href="http://www.biodiversite.ca/">Centre sur la biodiversité</a></li>
	<li>Jacques Labrecque, <a href="http://www.cdpnq.gouv.qc.ca/">Centre de données sur le patrimoine naturel du Québec</a></li>
	<li>John Maunder, <a href="http://therooms.ca/museum/">The Rooms Provincial Museum</a></li>
	<li>Marian Munro, <a href="http://museum.gov.ns.ca/mnhnew/en/home/whattoseedo/collections/botany.aspx">Nova Scotia Museum</a></li>
	<li>Mike Oldham, <a href="http://nhic.mnr.gov.on.ca/">Centre d'information sur le patrimoine naturel de l'Ontario</a></li>
	<li>Elizabeth Punter, <a href="http://umanitoba.ca/">University of Manitoba</a></li>
	<li><a href="http://www.jcsemple.uwaterloo.ca">John C. Semple</a>, <a href="http://uwaterloo.ca/">University of Waterloo</a></li>
</ul>
<h3>Financement</h3>
<p>Le projet a été financé partiellement par:</p>
<ul>
	<li><a href="http://www.pc.gc.ca/">Parcs Canada</a></li>
	<li><a href="http://www.cbif.gc.ca/home_e.php">Système canadien d'information sur la biodiversité (CBIF)</a></li>
	<li><a href="http://www.natureserve-canada.ca/">NatureServe Canada</a></li>
</ul>

<a name="rights"></a><h2>Citation, droits et avis de non-responsabilité</h2>
<h3>Citation recommandée</h3>
<p>Brouillet, L., F. Coursol, S.J. Meades, M. Favreau, M. Anions, P. Bélisle et P. Desmet. 2010+. VASCAN, la Base de données des plantes vasculaires du Canada. <a href="http://data.canadensys.net/vascan/">http://data.canadensys.net/vascan/</a> (consultée le ${currentDate})</p>
<p><strong>Pour un taxon:</strong><br />
Brouillet et al. 2010+. <em>Acer saccharum</em> Marshall in VASCAN, la Base de données des plantes vasculaires du Canada. <a href="http://data.canadensys.net/vascan/taxon/9214">http://data.canadensys.net/vascan/taxon/9214</a> (consultée le ${currentDate})</p>
<p><strong>Pour un nom vernaculaire:</strong><br />
Brouillet et al. 2010+. érable à sucre in VASCAN, la Base de données des plantes vasculaires du Canada. <a href="http://data.canadensys.net/vascan/vernacular/25475">http://data.canadensys.net/vascan/vernacular/25475</a> (consultée le ${currentDate})</p>
<h3>Droits de propriété intellectuelle</h3>
<p><a href="http://creativecommons.org/publicdomain/zero/1.0/deed.fr" title="CC0 1.0 universel transfert au domaine public"><img class="alignright" src="http://data.canadensys.net/common/images/cc0-logo.png" /></a> Toutes les données de la <a href="http://data.canadensys.net/vascan?lang=fr">base de données des plantes vasculaires du Canada (VASCAN)</a> ont été transférées au domaine public sous <a href="http://creativecommons.org/publicdomain/zero/1.0/deed.fr">CC0 1.0 universel transfert au domaine public</a>. Bien que vous soyez libres d'utiliser les données comme bon vous semble, nous vous recommendons cependant de <a href="http://www.canadensys.net/norms">respecter ces normes</a>. Nous avons aussi publié le <a href="http://www.canadensys.net/vascan">code source et le modèle de base de données</a> de VASCAN.</p>
<h3>Avis de non-responsabilité</h3>
<p>Bien que nous ayons fait tous les efforts possibles pour nous assurer que l'information dans la base de données VASCAN est précise et complète, l'utilisation de la base de données et la précision, la fiabilité ou l'exhaustivité des données fournies n'engagent <strong>en rien notre responsabilité</strong>.</p>

<a name="feedback"></a><h2>Commentaires</h2>
<p>Nous dépendons beaucoup des collègues et des usagers pour nous aider à améliorer la base de données et nous <a href="http://code.google.com/p/canadensys/issues/entry?template=Vascan%20-%20Data%20issue">recevons avec gratitude toute suggestion</a> fondée sur des données fiables. Il s'agit d'un effort collectif, bâti sur le travail de nos illustres prédécesseurs (dont plusieurs sont cités dans la base de données) et d'un grand nombre de collaborateurs.</p>
<p>Vous pouvez nous transmettre des commentaires sur les données ou le logiciel en utilisant l'onglet de rétroaction situé à la droite de l'écran ou en cliquant sur un des liens ci-dessous. Vous aurez besoin d'un compte <a href="https://www.google.com/accounts/NewAccount">Google Account</a>. Tous les commentaires sont publiés sur ce <a href="http://code.google.com/p/canadensys/issues/list">site web</a>.</p>
<ul>
	<li><a href="http://code.google.com/p/canadensys/issues/entry?template=Vascan%20-%20Data%20issue">Signaler un problème de données</a></li>
	<li><a href="http://code.google.com/p/canadensys/issues/entry?template=Vascan%20-%20Interface%20issue">Signaler un problème d'interface</a></li>
</ul>
<p>Les mises à jour et les conseils concernant VASCAN sont annoncés via Twitter par <a href="http://www.twitter.com/canadensys/">@Canadensys</a>.</p>

<a name="distribution"></a><h2>Légende des états de répartition</h2>
<p>Nous utilisons les codes et couleurs suivants pour indiquer l'état de répartition de chaque taxon :</p>
<ul class="custom_list">
	<li class="sprite sprite-native"><strong>Indigène</strong> - Taxon présent uniquement en raison de processus naturels, sans intervention humaine.</li>
	<li class="sprite sprite-introduced"><strong>Introduit</strong> - Taxon établi (naturalisé) dans une région autre que son aire d'origine, après y avoir été introduit délibérément ou accidentellement dans le cadre d'activités humaines. Le taxon est jugé introduit au Canada s'il s'y est établi après la colonisation européenne.</li>
	<li class="sprite sprite-ephemeral"><strong>Éphémère</strong> - Taxon non établi de façon permanente dans une région, mais présent de manière récurrente dans la nature, quasi annuellement, généralement à partir de cultures (<a href="name/blé?lang=fr">blé</a>, <a href="name/tomate?lang=fr">tomate</a>, etc.).</li>
	<li class="sprite sprite-excluded"><strong>Exclus</strong> - Taxon qui a déjà été signalé dans une région, mais ne s'y est pas établi ou avait été identifié de façon erronée.</li>
	<li class="sprite sprite-extirpated"><strong>Disparu</strong> - Taxon indigène à une région, mais actuellement considéré comme n'y étant plus présent. Le taxon n'est déclaré disparu qu'après des recherches actives, et cet état est généralement déterminé par un organisme de conservation.</li>
	<li class="sprite sprite-doubtful"><strong>Douteux</strong> - Taxon signalé dans une région par une source quelconque, sans que l'information ait été validée.</li>
	<li class="sprite sprite-absent"><strong>Absent</strong> - Taxon inconnu dans la région.</li>
</ul>
</#if>
	</div><#-- content -->
</div>
<#include "inc/footer.ftl">