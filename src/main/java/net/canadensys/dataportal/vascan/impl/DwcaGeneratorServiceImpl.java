package net.canadensys.dataportal.vascan.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import net.canadensys.dataportal.vascan.DwcaGeneratorService;
import net.canadensys.dataportal.vascan.config.GeneratedContentConfig;
import net.canadensys.dataportal.vascan.config.VascanConfig;
import net.canadensys.dataportal.vascan.constant.Status;
import net.canadensys.dataportal.vascan.dao.DistributionDAO;
import net.canadensys.dataportal.vascan.dao.HybridDAO;
import net.canadensys.dataportal.vascan.dao.TaxonDAO;
import net.canadensys.dataportal.vascan.dao.TaxonomyDAO;
import net.canadensys.dataportal.vascan.dao.VernacularNameDAO;
import net.canadensys.dataportal.vascan.dao.impl.HibernateTaxonDAO;
import net.canadensys.utils.ZipUtils;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.log4j.Logger;
import org.gbif.dwc.terms.DcTerm;
import org.gbif.dwc.terms.DwcTerm;
import org.gbif.dwc.terms.GbifTerm;
import org.gbif.dwc.terms.Term;
import org.gbif.dwc.text.DwcaWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class responsible for generating a Darwin Core archive.
 * 
 * @author canadensys
 *
 */
@Service("dwcaGeneratorService")
public class DwcaGeneratorServiceImpl implements DwcaGeneratorService {
	
	private static final Logger LOGGER = Logger.getLogger(DwcaGeneratorServiceImpl.class);
	private static final int SYNONYMS_WRITING_BATCH_SIZE = 100;
	private static final int BATCH_SIZE = 100;
		
	private static final FastDateFormat SDF_DATE = FastDateFormat.getInstance("yyyy-MM-dd");
	private static final FastDateFormat SDF_TIME = FastDateFormat.getInstance("H:mmZ");
	private static final String HYBRID_PARENT_RELATIONSHIP = "hybrid parent of";
	
	@Autowired
	private VascanConfig vascanConfig;
	
	@Autowired
	private TaxonDAO taxonDAO;
	
	@Autowired
	private TaxonomyDAO taxonomyDAO;
	
	@Autowired
	private VernacularNameDAO vernacularNameDAO;
	
	@Autowired
	private DistributionDAO distributionDAO;
	
	@Autowired
	private HybridDAO hybridDAO;
	
	@Autowired
	private GeneratedContentConfig generatedContentConfig;
	
	/**
	 * 
	 * @param taxonModelIt
	 * @param tempDirectory
	 * @param archiveAbsolutePath full and absolute path of the result archive
	 * @param resourceBundle
	 * @return
	 */
	public boolean generateDwcArchive(Iterator<Map<String,Object>> taxonModelIt, File tempDirectory, String archiveAbsolutePath, ResourceBundle resourceBundle){
		DwcaWriter dwcaWriter;
		Set<Integer> synonymTaxonId = new HashSet<Integer>();
		//we should work in batch for the extensions but the dwca writer can not support it for now
		List<Integer> batchIdList = new ArrayList<Integer>(BATCH_SIZE);
		
		try {
			dwcaWriter = new DwcaWriter(DwcTerm.Taxon, DwcTerm.taxonID, tempDirectory, true);
			
			Map<String,Object> currTaxonData = null;
			Integer taxonId;
			while(taxonModelIt.hasNext()){
				currTaxonData = taxonModelIt.next();
				taxonId = (Integer)currTaxonData.get(TaxonDAO.DD_ID);

				//in rare cases we can receive a synonym in the taxon list
				if(HibernateTaxonDAO.STATUS_ACCEPTED == ((Integer)currTaxonData.get(TaxonDAO.DD_STATUS_ID)).intValue()){
					addCoreTaxonRecord(currTaxonData, dwcaWriter, resourceBundle);
					
					//fill the description extension since we already have the data
					addExtDescriptionRecord((String)currTaxonData.get(TaxonDAO.DD_CALHABIT), dwcaWriter);
					handleExtensions(taxonId, dwcaWriter, resourceBundle);
					
					batchIdList.add(taxonId);
					if(batchIdList.size() == BATCH_SIZE){
						manageBatchOperations(batchIdList, synonymTaxonId);
						batchIdList.clear();
					}
				}
				else{
					synonymTaxonId.add((Integer)currTaxonData.get(TaxonDAO.DD_ID));
				}
			}
			
			//run batch operation for remaining taxon id
			if(!batchIdList.isEmpty()){
				manageBatchOperations(batchIdList, synonymTaxonId);
				batchIdList.clear();
			}
			
			//append synonyms to taxon file
			appendSynonyms(synonymTaxonId,dwcaWriter,resourceBundle);
			
			// core default values
			dwcaWriter.addCoreDefaultValue(DcTerm.language, "en");
			dwcaWriter.addCoreDefaultValue(DcTerm.rights, "http://creativecommons.org/publicdomain/zero/1.0/ & http://www.canadensys.net/norms");
			dwcaWriter.addCoreDefaultValue(DcTerm.rightsHolder, "Université de Montréal Biodiversity Centre");
			dwcaWriter.addCoreDefaultValue(DwcTerm.datasetName, "Database of Vascular Plants of Canada (VASCAN)");
			dwcaWriter.addCoreDefaultValue(DwcTerm.kingdom, "Plantae");
			dwcaWriter.addCoreDefaultValue(DwcTerm.nomenclaturalCode, "ICBN");
			
			// VernacularName default values
			dwcaWriter.addDefaultValue(GbifTerm.VernacularName, DwcTerm.countryCode, "CA");
			dwcaWriter.addDefaultValue(GbifTerm.VernacularName, GbifTerm.isPlural, "false");
			
			// Description default values
			dwcaWriter.addDefaultValue(GbifTerm.Description, DcTerm.type, "habit");
			dwcaWriter.addDefaultValue(GbifTerm.Description, DcTerm.language, "EN");
			
			dwcaWriter.close();
			
			ZipUtils.zipFolder(tempDirectory, new File(archiveAbsolutePath).getAbsolutePath());
		}
		catch (IOException e) {
			LOGGER.fatal("Can't generate Dwc-A", e);
			return false;
		}
		return true;
	}
	
	public void addCoreTaxonRecord(Map<String,Object> taxonData, DwcaWriter dwcaWriter, ResourceBundle resourceBundle) throws IOException{
		
		String taxonId = ((Integer)taxonData.get(TaxonDAO.DD_ID)).toString();
		Calendar mdate = (Calendar)taxonData.get(TaxonDAO.DD_MDATE);
		String vascanReference = vascanConfig.getTaxonUrl().concat(taxonId);
		
		dwcaWriter.newRecord(taxonId);
		
		dwcaWriter.addCoreColumn(DcTerm.modified, SDF_DATE.format(mdate.getTime()).concat("T").concat(SDF_TIME.format(mdate.getTime())));

		String bibliographicCitation = String.format(resourceBundle.getString("dwc_bibliographic_citation"), (String)taxonData.get(TaxonDAO.DD_CALNAME_AUTHOR), vascanReference, SDF_DATE.format(Calendar.getInstance().getTime()));
		dwcaWriter.addCoreColumn(DcTerm.bibliographicCitation, bibliographicCitation);
		
		dwcaWriter.addCoreColumn(DcTerm.references, vascanReference);

		dwcaWriter.addCoreColumn(DwcTerm.acceptedNameUsageID, taxonId);
		dwcaWriter.addCoreColumn(DwcTerm.acceptedNameUsage, (String)taxonData.get(TaxonDAO.DD_CALNAME_AUTHOR));
		
		String parentNameUsageID = StringUtils.defaultString((String)taxonData.get(TaxonDAO.DD_CONCAT_PARENT_ID)).replaceAll(",", "|");
		String parentNameUsage = StringUtils.defaultString((String)taxonData.get(TaxonDAO.DD_CONCAT_PARENT_CALNAME_AUTHOR)).replaceAll(",", "|");

		//
		dwcaWriter.addCoreColumn(DwcTerm.parentNameUsageID, parentNameUsageID);
		dwcaWriter.addCoreColumn(DwcTerm.parentNameUsage, parentNameUsage);
		
		dwcaWriter.addCoreColumn(DwcTerm.nameAccordingToID, (String)taxonData.get(TaxonDAO.DD_URL));
		dwcaWriter.addCoreColumn(DwcTerm.nameAccordingTo, (String)taxonData.get(TaxonDAO.DD_REFERENCE));
		
		dwcaWriter.addCoreColumn(DwcTerm.scientificName, (String)taxonData.get(TaxonDAO.DD_CALNAME_AUTHOR));
		dwcaWriter.addCoreColumn(DwcTerm.scientificNameAuthorship, (String)taxonData.get(TaxonDAO.DD_AUTHOR));
		
		dwcaWriter.addCoreColumn(DwcTerm.higherClassification, (String)taxonData.get(TaxonDAO.DD_HIGHER_CLASSIFICATION));
		dwcaWriter.addCoreColumn(DwcTerm.class_, (String)taxonData.get(TaxonDAO.DD_CLASS));
		dwcaWriter.addCoreColumn(DwcTerm.order, (String)taxonData.get(TaxonDAO.DD_ORDER));
		dwcaWriter.addCoreColumn(DwcTerm.family,(String)taxonData.get(TaxonDAO.DD_FAMILY));
		dwcaWriter.addCoreColumn(DwcTerm.genus, (String)taxonData.get(TaxonDAO.DD_GENUS));
		dwcaWriter.addCoreColumn(DwcTerm.subgenus, (String)taxonData.get(TaxonDAO.DD_SUBGENUS));
		dwcaWriter.addCoreColumn(DwcTerm.specificEpithet, (String)taxonData.get(TaxonDAO.DD_SPECIFIC_EPITHET));
		dwcaWriter.addCoreColumn(DwcTerm.infraspecificEpithet, (String)taxonData.get(TaxonDAO.DD_INFRASPECIFIC_EPITHET));
		
		dwcaWriter.addCoreColumn(DwcTerm.taxonRank, StringUtils.defaultString((String)taxonData.get(TaxonDAO.DD_RANK)).toLowerCase());
		dwcaWriter.addCoreColumn(DwcTerm.taxonomicStatus, StringUtils.defaultString((String)taxonData.get(TaxonDAO.DD_STATUS)).toLowerCase());
	}
	
	/**
	 * Add synonym record for each id(taxonid) provided.
	 * 
	 * @param idList
	 * @param dwcaWriter
	 * @param resourceBundle
	 * @throws IOException
	 */
	public void addCoreSynonymTaxonRecords(List<Integer> idList, DwcaWriter dwcaWriter, ResourceBundle resourceBundle) throws IOException{

		List<Map<String,Object>> synonymData = taxonDAO.loadDenormalizedTaxonData(idList);
		
		String taxonId;
		String vascanReference;
		String bibliographicCitation;
		Calendar mdate;
		
		for(Map<String,Object> currDataLine : synonymData){
			taxonId = ((Integer)currDataLine.get(TaxonDAO.DD_ID)).toString();
			vascanReference = vascanConfig.getTaxonUrl().concat(taxonId);
			mdate = (Calendar)currDataLine.get(TaxonDAO.DD_MDATE);
					
			dwcaWriter.newRecord(taxonId);
			
			dwcaWriter.addCoreColumn(DcTerm.modified, SDF_DATE.format(mdate.getTime()).concat("T").concat(SDF_TIME.format(mdate.getTime())));

			bibliographicCitation = String.format(resourceBundle.getString("dwc_bibliographic_citation"), (String)currDataLine.get(TaxonDAO.DD_CALNAME_AUTHOR), vascanReference, SDF_DATE.format(Calendar.getInstance().getTime()));
			dwcaWriter.addCoreColumn(DcTerm.bibliographicCitation, bibliographicCitation);
			
			dwcaWriter.addCoreColumn(DcTerm.references, vascanReference);

			// we may have more than one parent for synonyms
			String acceptedNameUsageID = StringUtils.defaultString((String)currDataLine.get(TaxonDAO.DD_CONCAT_PARENT_ID)).replaceAll(",", "|");
			String acceptedNameUsage = StringUtils.defaultString((String)currDataLine.get(TaxonDAO.DD_CONCAT_PARENT_CALNAME_AUTHOR)).replaceAll(",", "|");

			dwcaWriter.addCoreColumn(DwcTerm.acceptedNameUsageID,acceptedNameUsageID);
			dwcaWriter.addCoreColumn(DwcTerm.acceptedNameUsage, acceptedNameUsage);
			
			// not used for synonyms
			dwcaWriter.addCoreColumn(DwcTerm.parentNameUsageID, "");
			dwcaWriter.addCoreColumn(DwcTerm.parentNameUsage, "");
					
			dwcaWriter.addCoreColumn(DwcTerm.nameAccordingToID, (String)currDataLine.get(TaxonDAO.DD_URL));
			dwcaWriter.addCoreColumn(DwcTerm.nameAccordingTo, (String)currDataLine.get(TaxonDAO.DD_REFERENCE));
			
			dwcaWriter.addCoreColumn(DwcTerm.scientificName, (String)currDataLine.get(TaxonDAO.DD_CALNAME_AUTHOR));
			dwcaWriter.addCoreColumn(DwcTerm.scientificNameAuthorship, (String)currDataLine.get(TaxonDAO.DD_AUTHOR));
			
			// not used for synonyms
			dwcaWriter.addCoreColumn(DwcTerm.higherClassification, "");
			dwcaWriter.addCoreColumn(DwcTerm.class_, "");
			dwcaWriter.addCoreColumn(DwcTerm.order, "");
			dwcaWriter.addCoreColumn(DwcTerm.family, "");
			dwcaWriter.addCoreColumn(DwcTerm.subgenus, "");
			
			dwcaWriter.addCoreColumn(DwcTerm.genus, (String)currDataLine.get(TaxonDAO.DD_GENUS));
			dwcaWriter.addCoreColumn(DwcTerm.specificEpithet, (String)currDataLine.get(TaxonDAO.DD_SPECIFIC_EPITHET));
			dwcaWriter.addCoreColumn(DwcTerm.infraspecificEpithet, (String)currDataLine.get(TaxonDAO.DD_INFRASPECIFIC_EPITHET));
			
			dwcaWriter.addCoreColumn(DwcTerm.taxonRank, StringUtils.defaultString((String)currDataLine.get(TaxonDAO.DD_RANK)).toLowerCase());
			dwcaWriter.addCoreColumn(DwcTerm.taxonomicStatus, StringUtils.defaultString((String)currDataLine.get(TaxonDAO.DD_STATUS)).toLowerCase());
		}
	}
	
	/**
	 * Add extension records from an id list.
	 * All data will be loaded from the database in a denormalized query to have acceptable performance.
	 * All the data of all the provided id will be loaded, this should be considered when choosing a batch size.
	 * @param taxonId
	 * @param dwcaWriter
	 * @param resourceBundle
	 * @param synonymTaxonId current set of synonyms taxonid to add
	 */
	private void handleExtensions(Integer taxonId, DwcaWriter dwcaWriter, ResourceBundle resourceBundle) throws IOException {
		
		addExtVernacularRecords(taxonId, dwcaWriter);
		addExtDistributionRecords(taxonId, dwcaWriter, resourceBundle);
		
		//deal with hybrid parents
		addExtHybridResourceRelationship(taxonId, dwcaWriter);
	}
	
	/**
	 * Run all operation that can be applied in batch
	 */
	private void manageBatchOperations(List<Integer> taxonIdList, Set<Integer> synonymTaxonId) {
		//we only record the id, synonyms will be added after all accepted taxon
		List<Integer> synonymsId = taxonomyDAO.getSynonymChildrenIdList(taxonIdList);
		if(synonymsId != null && !synonymsId.isEmpty()){
			synonymTaxonId.addAll(synonymsId);
		}
	}
	
	/**
	 * Appends synonyms to the taxon core file in batch.
	 * 
	 * @param synonymTaxonId
	 * @param dwcaWriter
	 * @param resourceBundle
	 * @throws IOException 
	 */
	private void appendSynonyms(Set<Integer> synonymTaxonId, DwcaWriter dwcaWriter, ResourceBundle resourceBundle) throws IOException{
		List<Integer> taxonIdList = new ArrayList<Integer>(synonymTaxonId);
		int size = taxonIdList.size();
		int fromIdx = 0;
		int toIdx = 0;
		int count = 0;
		int batchNumber=0;
		
 		while(count < size){
 			fromIdx = count;
 			toIdx = Math.min(((batchNumber+1)*SYNONYMS_WRITING_BATCH_SIZE), size);

 			//fromIdx is inclusive, toIdx is exclusive
			addCoreSynonymTaxonRecords(taxonIdList.subList(fromIdx, toIdx), dwcaWriter,resourceBundle);
			batchNumber++;
			count += (toIdx-fromIdx);
		}
	}
	
	/**
	 * Add a Distribution extension records to the current core record.
	 * @param idList ids of taxon
	 * @param dwcaWriter
	 * @param resourceBundle
	 * @throws IOException
	 */
	private void addExtDistributionRecords(Integer taxonid, DwcaWriter dwcaWriter, ResourceBundle resourceBundle) throws IOException{
		
		Map<Term,String> recordValues = new HashMap<Term,String>();
		String currExcludedCode = null;
		
		List<Map<String,Object>> distributionData = distributionDAO.loadDenormalizedDistributionData(Arrays.asList(taxonid));
		
		for(Map<String,Object> currDataLine : distributionData){
			
			String localityID = "";
			if(StringUtils.isNotBlank((String)currDataLine.get(DistributionDAO.DD_ISO3166_2))){
				localityID = "ISO 3166-2:"+(String)currDataLine.get(DistributionDAO.DD_ISO3166_2);
			}
			recordValues.put(DwcTerm.locationID, localityID);
			String locality = resourceBundle.getString("province_" + ((String)currDataLine.get(DistributionDAO.DD_REGION)).toUpperCase());
			recordValues.put(DwcTerm.locality, locality);
			recordValues.put(DwcTerm.countryCode, (String)currDataLine.get(DistributionDAO.DD_ISO3166_1));
			recordValues.put(DwcTerm.occurrenceStatus, (String)currDataLine.get(DistributionDAO.DD_OCCURRENCE_STATUS));
			recordValues.put(DwcTerm.establishmentMeans, (String)currDataLine.get(DistributionDAO.DD_ESTABLISHMENT_MEANS));
			recordValues.put(DcTerm.source, (String)currDataLine.get(DistributionDAO.DD_REFERENCE + " " + (String)currDataLine.get(DistributionDAO.DD_URL)));
			String excludedRemarks = "";
			
			currExcludedCode = (String)currDataLine.get(DistributionDAO.DD_EXCLUDED_CODE);
			if(StringUtils.isNotBlank(currExcludedCode)){
				excludedRemarks = resourceBundle.getString("excluded_" + currExcludedCode.toLowerCase());
			}
			recordValues.put(DwcTerm.occurrenceRemarks, excludedRemarks);
			
			dwcaWriter.addExtensionRecord(GbifTerm.Distribution, recordValues);
			recordValues.clear();
		}
		
	}
	
	/**
	 * Add a VernacularName extension record to the current core record.
	 * @param idList ids of taxon
	 * @param dwcaWriter
	 * @throws IOException
	 */
	private void addExtVernacularRecords(Integer taxonid, DwcaWriter dwcaWriter) throws IOException{
		Map<Term,String> recordValues = new HashMap<Term,String>();

		List<Map<String,Object>> vernacularData = vernacularNameDAO.loadDenormalizedVernacularNameData(Arrays.asList(taxonid));

		for(Map<String,Object> currDataLine : vernacularData){
			recordValues.put(DwcTerm.vernacularName, (String)currDataLine.get(VernacularNameDAO.DD_NAME));
			recordValues.put(DcTerm.source, (String)currDataLine.get(VernacularNameDAO.DD_REFERENCE) + " " + (String)currDataLine.get(VernacularNameDAO.DD_URL));
			recordValues.put(DcTerm.language, ((String)currDataLine.get(VernacularNameDAO.DD_LANGUAGE)).toUpperCase());
			recordValues.put(GbifTerm.isPreferredName, BooleanUtils.toStringTrueFalse(((Integer)currDataLine.get(VernacularNameDAO.DD_STATUS_ID)).equals(Status.ACCEPTED)));
			dwcaWriter.addExtensionRecord(GbifTerm.VernacularName, recordValues);
			recordValues.clear();
		}
	}
	
	/**
	 * Add a Description extension record to the current core record.
	 * @param taxonLookupModel
	 * @param dwcaWriter
	 * @throws IOException
	 */
	private void addExtDescriptionRecord(String calculatedHabit, DwcaWriter dwcaWriter) throws IOException{
		Map<Term,String> recordValues = new HashMap<Term,String>();
		recordValues.put(DcTerm.description, calculatedHabit);
		dwcaWriter.addExtensionRecord(GbifTerm.Description, recordValues);
	}
	
	/**
	 * Add a ResourceRelationship extension record to represent hybrid relationship.
	 * @param idList ids of taxon
	 * @param dwcaWriter
	 * @throws IOException
	 */
	private void addExtHybridResourceRelationship(Integer taxonid, DwcaWriter dwcaWriter) throws IOException{
		Map<Term,String> recordValues = new HashMap<Term,String>();
		List<Map<String,Object>> hybridParentsData = hybridDAO.loadDenormalizedHybridParentsData(Arrays.asList(taxonid));
		for(Map<String,Object> currDataLine : hybridParentsData){
			recordValues.put(DwcTerm.resourceID, ((Integer)currDataLine.get(HybridDAO.DD_TAXON_ID)).toString());
			recordValues.put(DwcTerm.relatedResourceID, ((Integer)currDataLine.get(HybridDAO.DD_PARENT_ID)).toString());
			recordValues.put(DwcTerm.relationshipOfResource, HYBRID_PARENT_RELATIONSHIP);
			recordValues.put(DwcTerm.scientificName, (String)currDataLine.get(HybridDAO.DD_PARENT_CALNAME_AUTHOR));
			dwcaWriter.addExtensionRecord(DwcTerm.ResourceRelationship, recordValues);
			recordValues.clear();
		}
	}
}
