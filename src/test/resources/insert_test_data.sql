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
(160,'MF','Favreau','Favreau, M. Proposition de nom français.','');

INSERT INTO `reference` (id,referencecode,referenceshort,reference,url) VALUES
(65,'Nunavik','Blondeau & Roy, 2004','Blondeau, M. & C. Roy. 2004. Atlas des plantes des villages du Nunavik. Multimondes. 610 pp.','');

INSERT INTO taxon (id,
  uninomial,author,statusid,rankid,referenceid)
VALUES(73,'Equisetopsida','C. Aghard',1,1,105);

INSERT INTO taxon (id,
  uninomial,binomial,trinomial,author,statusid,rankid,referenceid)
VALUES(14992,'Carex','arctata','faxoni','L.H. Bailey',2,16,180);

INSERT INTO taxon (id,
  uninomial,binomial,trinomial,author,statusid,rankid,referenceid)
VALUES(15164,'Carex','alpina','holostoma','(Drejer) L.H. Bailey',2,16,180);

INSERT INTO taxon (id,
  uninomial,binomial,author,statusid,rankid,referenceid)
VALUES(15428,'Carex','abdita','Bicknell',2,14,180);

INSERT INTO taxon (id,
  uninomial,binomial,author,statusid,rankid,referenceid)
VALUES(9401,'Taxus','canadensis','Marshall',1,14,176);

INSERT INTO taxon (id,
  uninomial,binomial,author,statusid,rankid,referenceid)
VALUES(3018,'Cosmos','bipinnatus','Cavanilles',1,14,178);

INSERT INTO taxon (id,
  uninomial,binomial,trinomial,author,statusid,rankid,referenceid)
VALUES(2844,'Arctanthemum','arcticum','arcticum','(Linnaeus) Tzvelev',1,15,175);

INSERT INTO taxon (id,
  uninomial,binomial,trinomial,author,statusid,rankid,referenceid)
VALUES(2846,'Arctanthemum','arcticum','polare','(Hultén) Tzvelev',1,15,175);

INSERT INTO taxon (id,
  uninomial,binomial,author,statusid,rankid,referenceid)
VALUES(2845,'Arctanthemum','arcticum','(Linnaeus) Tzvelev',1,14,175);


INSERT INTO vernacularname (
  id,name,statusid,taxonid,language,referenceid)
VALUES(26258,'buis',2,9401,'fr',270);

INSERT INTO vernacularname (
  id,name,statusid,taxonid,language,referenceid)
VALUES(2627,'cosmos',2,3018,'fr',314);

INSERT INTO vernacularname (
  id,name,statusid,taxonid,language,referenceid)
1919,'chrysanthème arctique',1,2844,'fr',160);

INSERT INTO vernacularname (
  id,name,statusid,taxonid,language,referenceid)
1922,'chrysanthème arctique',2,2846,'fr',65);

INSERT INTO vernacularname (
  id,name,statusid,taxonid,language,referenceid)
30813,'chrysanthème arctique',1,2845,'fr',160);


