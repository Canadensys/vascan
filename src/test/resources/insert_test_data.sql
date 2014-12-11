INSERT INTO `status` (id,status) VALUES (1,'accepted'),(2,'synonym');

INSERT INTO `habit` (id,habit) VALUES (1,''),(2,'herb'),(3,'shrub'),(4,'tree'),(5,'vine');

INSERT INTO `excludedcode` (id,excludedcode) VALUES 
(1,''),
(2,'misidentification'),
(3,'documented'),
(4,'cultivated'),
(5,'old'),
(6,'ephemeral'),
(7,'determined');

INSERT INTO `distributionstatus` (id,distributionstatus) VALUES 
(1,'native'),
(2,'introduced'),
(3,'ephemeral'),
(4,'extirpated'),
(5,'excluded'),
(6,'doubtful'),
(7,'absent');

INSERT INTO `rank` (id,rank,sort) VALUES
(1,'Class',1),
(2,'Subclass',2),
(3,'Superorder',3),
(4,'Order',4),
(5,'Family',5),
(6,'Subfamily',6),
(7,'Tribe',7),
(8,'Subtribe',8),
(9,'Genus',9),
(10,'Subgenus',10),
(11,'Section',11),
(12,'Subsection',12),
(13,'Series',13),
(14,'Species',14),
(15,'Subspecies',15),
(16,'Variety',16),
(17,'',17);

INSERT INTO `region` (id,region,iso3166_1,iso3166_2,sort) VALUES
(1,'AB','CA','CA-AB',2),
(2,'BC','CA','CA-BC',1),
(3,'GL','GL','',16),
(4,'NL_L','CA','',11),
(5,'MB','CA','CA-MB',4),
(6,'NB','CA','CA-NB',7),
(7,'NL_N','CA','',10),
(8,'NT','CA','CA-NT',14),
(9,'NS','CA','CA-NS',9),
(10,'NU','CA','CA-NU',15),
(11,'ON','CA','CA-ON',5),
(12,'PE','CA','CA-PE',8),
(13,'QC','CA','CA-QC',6),
(14,'PM','FR','FR-PM',12),
(15,'SK','CA','CA-SK',3),
(16,'YT','CA','CA-YT',13);

INSERT INTO `reference` (id,referencecode) VALUES (1,'empty');
INSERT INTO `reference` (id,referencecode,referenceshort,reference,url) VALUES 
(105,'Chase&Reveal09','Chase & Reveal, 2009','Chase, M.W. & J.L. Reveal. 2009. A phylogenetic classification of land plants to accompany APG III. Botanical Journal of the Linnaen Society 161 (2): 122-127.','http://dx.doi.org/10.1111/j.1095-8339.2009.01002.x');

INSERT INTO `reference` (id,referencecode,referenceshort,reference,url) VALUES 
(180,'FNA23','FNA Ed. Comm., 2002a','FNA Editorial Committee. 2002. Flora of North America north of Mexico. Volume 23: Cyperaceae. Oxford University Press, New York.','http://www.efloras.org/volume_page.aspx?volume_id=1023&flora_id=1');

INSERT INTO `reference` (id,referencecode,referenceshort,reference,url) VALUES
(270,'MV95','Marie-Victorin, 1995','Marie-Victorin, Fr. 1995. Flore laurentienne. 3e éd. Mise à jour et annotée par L. Brouillet, S.G. Hay, I. Goulet, M. Blondeau, J. Cayouette et J. Labrecque. Gaétan Morin éditeur. 1093 pp.','');

INSERT INTO `reference` (id,referencecode,referenceshort,reference,url) VALUES
(314,'TB','TelaBotanica','TelaBotanica 2000-2009+. Flore électronique. France métropolitaine. http://www.tela-botanica.org','http://www.tela-botanica.org/');

INSERT INTO `reference` (id,referencecode,referenceshort,reference,url) VALUES
(176,'FNA2','FNA Ed. Comm., 1993','FNA Editorial Committee. 1993. Flora of North America north of Mexico. Volume 2: Pteridophytes and Gymnosperms. Oxford University Press, New York.','http://www.efloras.org/volume_page.aspx?volume_id=1002&flora_id=1');

INSERT INTO `reference` (id,referencecode,referenceshort,reference,url) VALUES
(178,'FNA21','FNA Ed. Comm., 2006c','FNA Editorial Committee. 2006. Flora of North America north of Mexico. Volume 21: Magnoliophyta: Asteridae, part 8: Asteraceae, part 3. Oxford University Press, New York.','http://www.efloras.org/volume_page.aspx?volume_id=1021&flora_id=1');

INSERT INTO `reference` (id,referencecode,referenceshort,reference,url) VALUES
(175,'FNA19','FNA Ed. Comm., 2006a','FNA Editorial Committee. 2006. Flora of North America north of Mexico. Volume 19: Magnoliophyta: Asteridae, part 6: Asteraceae, part 1. Oxford University Press, New York.','http://www.efloras.org/volume_page.aspx?volume_id=1019&flora_id=1');

INSERT INTO `reference` (id,referencecode,referenceshort,reference,url) VALUES
(65,'Nunavik','Blondeau & Roy, 2004','Blondeau, M. & C. Roy. 2004. Atlas des plantes des villages du Nunavik. Multimondes. 610 pp.',''),
(141,'NPS','Darbyshire et al., 2000','Darbyshire S.J., M. Favreau & M. Murray (revu et augmenté par). 2000. Noms populaires et scientifiques des plantes nuisibles du Canada. Agriculture et Agroalimentaire Canada. Publication 1397. 132 pp.',''),
(359,'GRFl','Böcher et al., 1978','Böcher, T.W., B. Fredskild, K. Holmen & K. Jakobsen. 1978. Grønlands Flora. P. Haase & Søns Forlag, København. 326 pp.',''),
(361,'PC80','Porsild & Cody, 1980','Porsild, A.E. & W.J. Cody. 1980. Vascular Plants of the Continental Northwest Territories, Canada. National Museum of Natural Sciences, Ottawa, Ont. 667 pp.',''),
(161,'FCAA','Aiken et al., 2007','Aiken, S.G., M.J. Dallwitz, L.L. Consaul, C.L., McJannet, R.L., Boles, G.W. Argus, J.M. Gillett, P.J. Scott, R. Elven, M.C. LeBlanc, L.J. Gillespie, A.K. Brysting, H. Solstad & J.G. Harris. 2007. Flora of the Canadian Arctic Archipelago: Descriptions, Illustrations, Identification, and Information Retrieval. [CD-ROM] NRC Research Press, National Research Council of Canada, Ottawa.',''),
(119,'Cody2000','Cody, 2000','Cody, W.J. 2000. Flora of the Yukon Territory. 2nd ed. National Research Press, Ottawa. 669 pp.',''),
(160,'MF','Favreau','Favreau, M. Proposition de nom français.',''),
(96,'Cayouette&Catling92','Cayouette & Catling, 1992','Cayouette, J. & P.M. Catling. 1992. Hybridization in the genus Carex with special reference to North America. Botanical Review 58 (4): 351-440.','http://www.jstor.org/stable/4354195'),
(185,'FNA4','FNA Ed. Comm., 2003a','FNA Editorial Committee. 2003. Flora of North America north of Mexico. Volume 4: Magnoliophyta: Caryophyllidae, part 1. Oxford University Press, New York.','http://www.efloras.org/volume_page.aspx?volume_id=1004&flora_id=1');

INSERT INTO taxon (id,
  uninomial,author,statusid,rankid,referenceid)
VALUES(73,'Equisetopsida','C. Aghard',1,1,105);

INSERT INTO taxon (id,uninomial,binomial,trinomial,author,statusid,rankid,referenceid)
VALUES
(4751,'Carex','arctata','','Boott',1,14,180),
(14992,'Carex','arctata','faxoni','L.H. Bailey',2,16,180),
(15164,'Carex','alpina','holostoma','(Drejer) L.H. Bailey',2,16,180),
(15428,'Carex','abdita','','Bicknell',2,14,180),
(9401,'Taxus','canadensis','','Marshall',1,14,176),
(3018,'Cosmos','bipinnatus','','Cavanilles',1,14,178),
(2844,'Arctanthemum','arcticum','arcticum','(Linnaeus) Tzvelev',1,15,175),
(2846,'Arctanthemum','arcticum','polare','(Hultén) Tzvelev',1,15,175),
(2845,'Arctanthemum','arcticum','','(Linnaeus) Tzvelev',1,14,175),
(5129,'Carex','umbellata','','Schkuhr ex Willdenow',1,14,180),
(4904,'Carex','holostoma','','Drejer','1','14','180'),
(2096,'Carex','Racemosae','','G. Don','1','11','180'),
(4793,'Carex','brunnescens subsp. brunnescens × Carex canescens subsp. canescens','','','1','14',96),
(4790,'Carex','canescens','brunnescens','(Persoon) W.D.J. Koch','2','16',180),
(4797,'Carex','canescens','canescens','Linnaeus','1','15',180),
(9946,'Amaranthus','graecizans','','auct. non Linnaeus p.p.','2','14',185),
(2498,'Amaranthus','albus','','Linnaeus','1','14',185),
(2499,'Amaranthus','blitoides','','S. Watson','1','14',185);

INSERT INTO taxonomy (id,childid,parentid)
VALUES
(1,4992,4751),
(2,15164,4904),
(3,15428,5129),
(4,4904,2096),
(5,9946,2498),
(6,9946,2499);

INSERT INTO lookup (
calname,calnameauthor,calnamehtml,calnamehtmlauthor,taxonid,status,rank,calhabit,
`AB`,`BC`,`GL`,`NL_L`,`MB`,`NB`,`NL_N`,`NT`,`NS`,`NU`,`ON`,`PE`,`QC`,`PM`,`SK`,`YT`,
higherclassification,class,_order,family,genus,subgenus,specificepithet,
infraspecificepithet,author,vernacularfr,vernacularen,cdate,mdate,_left,_right)
VALUES
('Equisetopsida','Equisetopsida C. Aghard','<em>Equisetopsida</em>','<em>Equisetopsida</em> C. Aghard',
'73','accepted','class','herb,shrub,tree,vine','native','native','native','native','native','native','native',
'native','native','native','native','native','native','native','native','native','','Equisetopsida','','','','',
'','','C. Aghard',NULL,NULL,'2013-08-22 00:00:00','2013-08-22 11:21:07','0','20067'
),
('Taxus canadensis','Taxus canadensis Marshall','<em>Taxus canadensis</em>','<em>Taxus canadensis</em> Marshall',
'9401','accepted','species','shrub','absent','absent','absent','native','native','native','native','absent','native',
'absent','native','native','native','native','absent','absent','Equisetopsida;Pinidae;Cupressales;Taxaceae;Taxus',
'Equisetopsida','Cupressales','Taxaceae','Taxus','','canadensis','','Marshall','if du Canada','Canada yew',
'2013-08-22 00:00:00','2013-08-22 11:21:07','19633','19634'
),
('Cosmos bipinnatus','Cosmos bipinnatus Cavanilles','<em>Cosmos bipinnatus</em>','<em>Cosmos bipinnatus</em> Cavanilles',
'3018','accepted','species','herb','absent','absent','absent','absent','absent','absent','absent','absent','absent',
'absent','introduced','absent','introduced','absent','absent','absent','Equisetopsida;Magnoliidae;Asteranae;Asterales;Asteraceae;Asteroideae;Coreopsideae;Cosmos',
'Equisetopsida','Asterales','Asteraceae','Cosmos','','bipinnatus','','Cavanilles','cosmos bipenné','garden cosmos',
'2013-08-22 00:00:00','2013-08-22 11:21:03','1676','1677'
),
('Carex arctata var. faxoni','Carex arctata var. faxoni L.H. Bailey','<em>Carex arctata</em> var. <em>faxoni</em>','<em>Carex arctata</em> var. <em>faxoni</em> L.H. Bailey',
'14992','synonym','variety','','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent',
'','','','','Carex','','arctata','faxoni','L.H. Bailey',NULL,NULL,'2013-08-22 00:00:00','2013-08-22 11:18:37',NULL,NULL
),
('Carex alpina var. holostoma','Carex alpina var. holostoma (Drejer) L.H. Bailey','<em>Carex alpina</em> var. <em>holostoma</em>','<em>Carex alpina</em> var. <em>holostoma</em> (Drejer) L.H. Bailey',
'15164','synonym','variety','','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent',
'','','','','Carex','','alpina','holostoma','(Drejer) L.H. Bailey',NULL,NULL,'2013-08-22 00:00:00','2013-08-22 11:18:31',NULL,NULL
),
('Silene latifolia subsp. alba','Silene latifolia subsp. alba (Miller) Greuter & Burdet','<em>Silene latifolia</em> subsp. <em>alba</em>','<em>Silene latifolia</em> subsp. <em>alba</em> (Miller) Greuter & Burdet',
'14428','synonym','subspecies','','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','','','','','Silene','','latifolia',
'alba','(Miller) Greuter & Burdet',NULL,NULL,'2013-08-22 00:00:00','2013-08-22 11:17:52',NULL,NULL
),
('Cosmos bipinnatus','Cosmos bipinnatus Cavanilles','<em>Cosmos bipinnatus</em>','<em>Cosmos bipinnatus</em> Cavanilles',
'3018','accepted','species','herb','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','introduced','absent','introduced','absent','absent','absent',
'Equisetopsida;Magnoliidae;Asteranae;Asterales;Asteraceae;Asteroideae;Coreopsideae;Cosmos','Equisetopsida','Asterales','Asteraceae','Cosmos','','bipinnatus','',
'Cavanilles','cosmos bipenné','garden cosmos','2013-08-22 00:00:00','2013-08-22 11:21:03','1676','1677'
),
('Arctanthemum arcticum subsp. arcticum','Arctanthemum arcticum (Linnaeus) Tzvelev subsp. arcticum','<em>Arctanthemum arcticum</em> subsp. <em>arcticum</em>','<em>Arctanthemum arcticum</em> (Linnaeus) Tzvelev subsp. <em>arcticum</em>',
'2844','accepted','subspecies','herb','absent','native','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent',
'Equisetopsida;Magnoliidae;Asteranae;Asterales;Asteraceae;Asteroideae;Anthemideae;Arctanthemum;Arctanthemum arcticum','Equisetopsida','Asterales','Asteraceae','Arctanthemum','',
'arcticum','arcticum','(Linnaeus) Tzvelev','chrysanthème arctique','arctic daisy','2013-08-22 00:00:00','2013-08-22 11:21:02','705','706'
),
('Arctanthemum arcticum subsp. polare','Arctanthemum arcticum subsp. polare (Hultén) Tzvelev','<em>Arctanthemum arcticum</em> subsp. <em>polare</em>','<em>Arctanthemum arcticum</em> subsp. <em>polare</em> (Hultén) Tzvelev',
'2846','accepted','subspecies','herb','absent','absent','absent','absent','native','absent','absent','native','absent','native','native','absent','native','absent','absent','native',
'Equisetopsida;Magnoliidae;Asteranae;Asterales;Asteraceae;Asteroideae;Anthemideae;Arctanthemum;Arctanthemum arcticum','Equisetopsida','Asterales','Asteraceae','Arctanthemum','','arcticum',
'polare','(Hultén) Tzvelev','chrysanthème polaire','polar daisy','2013-08-22 00:00:00','2013-08-22 11:21:02','707','708'
),
('Arctanthemum arcticum','Arctanthemum arcticum (Linnaeus) Tzvelev','<em>Arctanthemum arcticum</em>','<em>Arctanthemum arcticum</em> (Linnaeus) Tzvelev',
'2845','accepted','species','herb','absent','native','absent','absent','native','absent','absent','native','absent','native','native','absent','native','absent','absent','native',
'Equisetopsida;Magnoliidae;Asteranae;Asterales;Asteraceae;Asteroideae;Anthemideae;Arctanthemum','Equisetopsida','Asterales','Asteraceae','Arctanthemum','','arcticum','','(Linnaeus) Tzvelev',
'chrysanthème arctique','arctic daisy','2013-08-22 00:00:00','2013-08-22 11:21:02','704','709'
),
('Carex arctata','Carex arctata Boott','<em>Carex arctata</em>','<em>Carex arctata</em> Boott',
'4751','accepted','species','herb','absent','absent','absent','native','native','native','native','absent','native','absent','native','native','native','native','absent','absent',
'Equisetopsida;Magnoliidae;Lilianae;Poales;Cyperaceae;Caricoideae;Carex;Carex sect. Hymenochlaenae','Equisetopsida','Poales','Cyperaceae','Carex','','arctata','','Boott','carex comprimé',
'drooping woodland sedge','2013-08-22 00:00:00','2013-08-22 11:21:04','9500','9501'
),
('Carex umbellata','Carex umbellata Schkuhr ex Willdenow','<em>Carex umbellata</em>','<em>Carex umbellata</em> Schkuhr ex Willdenow',
'5129','accepted','species','herb','native','excluded','native','native','native','native','native','absent','native','absent','native','excluded','native','absent','native','absent',
'Equisetopsida;Magnoliidae;Lilianae;Poales;Cyperaceae;Caricoideae;Carex;Carex sect. Acrocystis','Equisetopsida','Poales','Cyperaceae','Carex','','umbellata','','Schkuhr ex Willdenow',
'carex en ombelle','umbellate sedge','2013-08-22 00:00:00','2013-08-22 11:21:04','9192','9193'
),
('Carex abdita','Carex abdita Bicknell','<em>Carex abdita</em>','<em>Carex abdita</em> Bicknell',
'15428','synonym','species','','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','absent',
'','','','','Carex','','abdita','','Bicknell',NULL,NULL,'2013-08-22 00:00:00','2013-08-22 11:18:30',NULL,NULL
),
('Carex holostoma','Carex holostoma Drejer','<em>Carex holostoma</em>','<em>Carex holostoma</em> Drejer',
'4904','accepted','species','herb','absent','absent','native','doubtful','absent','absent','doubtful','native','absent','native','absent','absent','native','absent','absent','native',
'Equisetopsida;Magnoliidae;Lilianae;Poales;Cyperaceae;Caricoideae;Carex;Carex sect. Racemosae','Equisetopsida','Poales','Cyperaceae','Carex','','holostoma','','Drejer',
'carex à bec entier','arctic marsh sedge','2013-08-22 00:00:00','2013-08-22 11:21:05','10066','10067'
),
('Carex sect. Racemosae','Carex sect. Racemosae G. Don','<em>Carex</em> sect. <em>Racemosae</em>','<em>Carex</em> sect. <em>Racemosae</em> G. Don',
'2096','accepted','section','herb','native','native','native','native','native','native','native','native','native','native','native','absent','native','native','native','native',
'Equisetopsida;Magnoliidae;Lilianae;Poales;Cyperaceae;Caricoideae;Carex','Equisetopsida','Poales','Cyperaceae','Carex','','','','G. Don',NULL,NULL,
'2013-08-22 00:00:00','2013-08-22 11:21:05','10045','10088'
),
('Carex brunnescens subsp. brunnescens × Carex canescens subsp. canescens','Carex brunnescens subsp. brunnescens × Carex canescens subsp. canescens',
'<em>Carex brunnescens subsp. brunnescens × Carex canescens subsp. canescens</em>','<em>Carex brunnescens subsp. brunnescens × Carex canescens subsp. canescens</em>',
'4793','accepted','species','herb','absent','absent','native','absent','absent','native','absent','absent','absent','absent','absent','absent','absent','absent','absent',
'absent','Equisetopsida;Magnoliidae;Lilianae;Poales;Cyperaceae;Caricoideae;Carex;Carex sect. Glareosae','Equisetopsida','Poales','Cyperaceae','Carex','',
'brunnescens subsp. brunnescens × Carex canescens subsp. canescens','','',NULL,NULL,'2013-08-22 00:00:00','2013-08-22 11:21:04','9410','9411'
),
('Carex canescens var. brunnescens','Carex canescens var. brunnescens (Persoon) W.D.J. Koch','<em>Carex canescens</em> var. <em>brunnescens</em>',
'<em>Carex canescens</em> var. <em>brunnescens</em> (Persoon) W.D.J. Koch','4790','synonym','variety','','absent','absent','absent','absent','absent','absent','absent','absent','absent',
'absent','absent','absent','absent','absent','absent','absent','','','','','Carex','','canescens','brunnescens','(Persoon) W.D.J. Koch',NULL,NULL,'2013-08-22 00:00:00','2013-08-22 11:18:33',NULL,NULL
),
('Carex canescens subsp. canescens','Carex canescens Linnaeus subsp. canescens','<em>Carex canescens</em> subsp. <em>canescens</em>','<em>Carex canescens</em> Linnaeus subsp. <em>canescens</em>',
'4797','accepted','subspecies','herb','native','native','native','native','native','native','native','native','native','native','native','native','native','doubtful','native','native',
'Equisetopsida;Magnoliidae;Lilianae;Poales;Cyperaceae;Caricoideae;Carex;Carex sect. Glareosae;Carex canescens','Equisetopsida','Poales','Cyperaceae','Carex','','canescens','canescens','Linnaeus',
'carex blanchâtre','hoary sedge','2013-08-22 00:00:00','2013-08-22 11:21:04','9413','9414'
),
('Amaranthus albus','Amaranthus albus Linnaeus','<em>Amaranthus albus</em>','<em>Amaranthus albus</em> Linnaeus','2498','accepted','species','herb','introduced','introduced','absent','absent',
'introduced','introduced','excluded','absent','introduced','absent','introduced','introduced','introduced','absent','introduced','absent','Equisetopsida;Magnoliidae;Caryophyllanae;Caryophyllales;Amaranthaceae;Amaranthoideae;Amarantheae;Amaranthus;Amaranthus subg. Albersia',
'Equisetopsida','Caryophyllales','Amaranthaceae','Amaranthus','Amaranthus subg. Albersia','albus','','Linnaeus','amarante blanche','white amaranth','2014-08-18 00:00:00','2014-08-18 17:06:04','6367','6368'
),
('Amaranthus blitoides','Amaranthus blitoides S. Watson','<em>Amaranthus blitoides</em>','<em>Amaranthus blitoides</em> S. Watson','2499','accepted','species','herb','introduced','introduced','absent','absent',
'introduced','absent','absent','absent','absent','absent','introduced','absent','introduced','absent','introduced','absent','Equisetopsida;Magnoliidae;Caryophyllanae;Caryophyllales;Amaranthaceae;Amaranthoideae;Amarantheae;Amaranthus;Amaranthus subg. Albersia',
'Equisetopsida','Caryophyllales','Amaranthaceae','Amaranthus','Amaranthus subg. Albersia','blitoides','','S. Watson','amarante fausse-blite','prostrate amaranth','2014-08-18 00:00:00','2014-08-18 17:06:04','6369','6370'
),
('Amaranthus graecizans','Amaranthus graecizans auct. non Linnaeus p.p.','<em>Amaranthus graecizans</em>','<em>Amaranthus graecizans</em> auct. non Linnaeus p.p.','9946','synonym','species','','absent','absent','absent','absent','absent','absent',
'absent','absent','absent','absent','absent','absent','absent','absent','absent','absent','','','','','Amaranthus','','graecizans','','auct. non Linnaeus p.p.',NULL,NULL,'2014-08-18 00:00:00','2014-08-18 16:53:41',NULL,NULL
);

INSERT INTO vernacularname (
  id,name,statusid,taxonid,language,referenceid)
VALUES
(26258,'buis',2,9401,'fr',270),
(2627,'cosmos',2,3018,'fr',314),
(1919,'chrysanthème arctique',1,2844,'fr',160),
(1922,'chrysanthème arctique',2,2846,'fr',65),
(30813,'chrysanthème arctique',1,2845,'fr',160),
(26256,'if du Canada','1','9401','fr',141
);

INSERT INTO distribution(
id,taxonid,regionid,
distributionstatusid,excludedcodeid,referenceid,cdate,mdate)
VALUES
('9450','4904','3','1','1','359','2011-02-21 12:20:13','2011-02-21 12:26:18'),
('9451','4904','4','6','1','1','2011-02-21 12:20:13','2011-08-16 10:37:01'),
('9452','4904','7','6','1','1','2011-02-21 12:20:13','2011-08-16 10:37:01'),
('9453','4904','8','1','1','361','2011-02-21 12:20:13','2011-02-21 12:26:18'),
('9454','4904','10','1','1','161','2011-02-21 12:20:13','2011-02-21 12:26:18'),
('9455','4904','13','1','1','270','2011-02-21 12:20:13','2011-02-21 12:26:18'),
('9456','4904','16','1','1','119','2011-02-21 12:20:13','2011-02-21 12:26:18');

INSERT INTO taxonhybridparent (id,childid,parentid)
VALUES
(143,4793,4790),
(144,4793,4797);

