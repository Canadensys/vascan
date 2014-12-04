package net.canadensys.dataportal.vascan.generatedcontent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import net.canadensys.dataportal.vascan.constant.Status;
import net.canadensys.dataportal.vascan.dao.impl.HibernateTaxonDAO;
import net.canadensys.dataportal.vascan.model.DistributionModel;
import net.canadensys.dataportal.vascan.model.DistributionStatusModel;
import net.canadensys.dataportal.vascan.model.ExcludedCodeModel;
import net.canadensys.dataportal.vascan.model.ReferenceModel;
import net.canadensys.dataportal.vascan.model.RegionModel;
import net.canadensys.dataportal.vascan.model.TaxonLookupModel;
import net.canadensys.dataportal.vascan.model.TaxonModel;
import net.canadensys.dataportal.vascan.model.VernacularNameModel;
import net.canadensys.utils.ZipUtils;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.gbif.dwc.terms.DcTerm;
import org.gbif.dwc.terms.DwcTerm;
import org.gbif.dwc.terms.GbifTerm;
import org.gbif.dwc.terms.Term;
import org.gbif.dwc.text.DwcaWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

/**
 * Class responsible for generating a Darwin Core archive.
 * 
 * @author canadensys
 *
 */
@Component
public class DarwinCoreGenerator {
		
	private static SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat SDF_TIME = new SimpleDateFormat("H:mmZ");
	private Set<Integer> synonymIdRecorded = new HashSet<Integer>();
	
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
	public boolean generateDwcArchive(Iterator<TaxonModel> taxonModelIt, File tempDirectory, String archiveAbsolutePath, ResourceBundle resourceBundle){
		DwcaWriter dwcaWriter;
		try {
			dwcaWriter = new DwcaWriter(DwcTerm.Taxon, tempDirectory, true);
			
			TaxonModel currTaxonModel = null;
			Set<TaxonModel> childrenList;
			while(taxonModelIt.hasNext()){
				currTaxonModel = taxonModelIt.next();
				
				//in rare cases we can receive a synonym in the taxon list
				if(currTaxonModel.getStatus().getId() == HibernateTaxonDAO.STATUS_ACCEPTED){
					addCoreTaxonRecord(currTaxonModel, dwcaWriter, resourceBundle);
					
					addExtDistributionRecords(currTaxonModel.getDistribution(), dwcaWriter, resourceBundle);
					
					addExtVernacularRecords(currTaxonModel.getVernacularnames(), dwcaWriter);
					
					addExtDescriptionRecord(currTaxonModel.getLookup(), dwcaWriter);
					
					// deal with synonyms
					childrenList = currTaxonModel.getChildren();
					for(TaxonModel currChildTaxonModel : childrenList){
						if(HibernateTaxonDAO.STATUS_SYNONYM == currChildTaxonModel.getStatus().getId() && !synonymIdRecorded.contains(currChildTaxonModel.getId())){
							addCoreSynonymTaxonRecord(currChildTaxonModel, dwcaWriter, resourceBundle);
							synonymIdRecorded.add(currChildTaxonModel.getId());
						}
					}
				}
				else{
					addCoreSynonymTaxonRecord(currTaxonModel, dwcaWriter, resourceBundle);
				}
			}
			dwcaWriter.close();
			
			ZipUtils.zipFolder(tempDirectory, new File(archiveAbsolutePath).getAbsolutePath());
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void addCoreTaxonRecord(TaxonModel taxonModel, DwcaWriter dwcaWriter, ResourceBundle resourceBundle) throws IOException{
		
		Preconditions.checkState(HibernateTaxonDAO.STATUS_ACCEPTED == taxonModel.getStatus().getId(), "addCoreTaxonRecord can only save accepted taxon");
		
		List<TaxonModel> parentList = taxonModel.getParents();
		Preconditions.checkState(parentList.size() <= 1, "addCoreTaxonRecord can only deal with accepted taxon linked to one or zero parent(only)");
		
		String taxonId = taxonModel.getId().toString();
		
		TaxonLookupModel currTaxonLookup = taxonModel.getLookup();
		ReferenceModel currReference = taxonModel.getReference();
		
		String vascanReference = generatedContentConfig.getTaxonUrl().concat(taxonId);
		dwcaWriter.newRecord(taxonId);
		//dwcaWriter.addCoreColumn(DwcTerm.taxonID, taxonId);
		
		dwcaWriter.addCoreColumn(DcTerm.modified, SDF_DATE.format((taxonModel.getMdate()).getTime()).concat("T").concat(SDF_TIME.format((taxonModel.getMdate()).getTime())));

		String bibliographicCitation = String.format(resourceBundle.getString("dwc_bibliographic_citation"), currTaxonLookup.getCalnameauthor(), vascanReference, SDF_DATE.format(Calendar.getInstance().getTime()));
		dwcaWriter.addCoreColumn(DcTerm.bibliographicCitation, bibliographicCitation);
		
		dwcaWriter.addCoreColumn(DcTerm.references, vascanReference);

		dwcaWriter.addCoreColumn(DwcTerm.acceptedNameUsageID, taxonId);
		dwcaWriter.addCoreColumn(DwcTerm.acceptedNameUsage, currTaxonLookup.getCalnameauthor());
		
		String parentNameUsageID = "";
		String parentNameUsage = "";
		if(parentList.size() == 1){
			parentNameUsageID = parentList.get(0).getId().toString();
			parentNameUsage = parentList.get(0).getLookup().getCalnameauthor();
		}
		dwcaWriter.addCoreColumn(DwcTerm.parentNameUsageID, parentNameUsageID);
		dwcaWriter.addCoreColumn(DwcTerm.parentNameUsage, parentNameUsage);
		
		dwcaWriter.addCoreColumn(DwcTerm.nameAccordingToID, currReference.getUrl());
		dwcaWriter.addCoreColumn(DwcTerm.nameAccordingTo, currReference.getReference());
		
		dwcaWriter.addCoreColumn(DwcTerm.scientificName, currTaxonLookup.getCalnameauthor());
		dwcaWriter.addCoreColumn(DwcTerm.scientificNameAuthorship, taxonModel.getAuthor());
		
		dwcaWriter.addCoreColumn(DwcTerm.higherClassification, currTaxonLookup.getHigherclassification());
		dwcaWriter.addCoreColumn(DwcTerm.class_, currTaxonLookup.getClasse());
		dwcaWriter.addCoreColumn(DwcTerm.order, currTaxonLookup.getOrder());
		dwcaWriter.addCoreColumn(DwcTerm.family, currTaxonLookup.getFamily());
		dwcaWriter.addCoreColumn(DwcTerm.genus, currTaxonLookup.getGenus());
		dwcaWriter.addCoreColumn(DwcTerm.subgenus, currTaxonLookup.getSubgenus());
		dwcaWriter.addCoreColumn(DwcTerm.specificEpithet, currTaxonLookup.getSpecificepithet());
		dwcaWriter.addCoreColumn(DwcTerm.infraspecificEpithet, currTaxonLookup.getInfraspecificepithet());
		
		dwcaWriter.addCoreColumn(DwcTerm.taxonRank, StringUtils.defaultString(currTaxonLookup.getRank()).toLowerCase());
		dwcaWriter.addCoreColumn(DwcTerm.taxonomicStatus, StringUtils.defaultString(currTaxonLookup.getStatus()).toLowerCase());
	}
	
	public void addCoreSynonymTaxonRecord(TaxonModel synonymTaxonModel, DwcaWriter dwcaWriter, ResourceBundle resourceBundle) throws IOException{
		Preconditions.checkState(HibernateTaxonDAO.STATUS_SYNONYM == synonymTaxonModel.getStatus().getId(), "addCoreSynonymTaxonRecord can only save synonym taxon");
		
		String taxonId = synonymTaxonModel.getId().toString();
		
		TaxonLookupModel currTaxonLookup = synonymTaxonModel.getLookup();
		ReferenceModel currReference = synonymTaxonModel.getReference();
		
		String vascanReference = generatedContentConfig.getTaxonUrl().concat(taxonId);
		
		dwcaWriter.newRecord(taxonId);
		//dwcaWriter.addCoreColumn(DwcTerm.taxonID, taxonId);
		
		dwcaWriter.addCoreColumn(DcTerm.modified, SDF_DATE.format((synonymTaxonModel.getMdate()).getTime()).concat("T").concat(SDF_TIME.format((synonymTaxonModel.getMdate()).getTime())));

		String bibliographicCitation = String.format(resourceBundle.getString("dwc_bibliographic_citation"), currTaxonLookup.getCalnameauthor(), vascanReference, SDF_DATE.format(Calendar.getInstance().getTime()));
		dwcaWriter.addCoreColumn(DcTerm.bibliographicCitation, bibliographicCitation);
		
		dwcaWriter.addCoreColumn(DcTerm.references, vascanReference);

		// we may have more than one
		List<TaxonModel> parentList = synonymTaxonModel.getParents();
		String acceptedNameUsageID = "";
		String acceptedNameUsage = "";
		for(TaxonModel currParent : parentList){
			if(StringUtils.isNotEmpty(acceptedNameUsageID)){
				acceptedNameUsageID += "|";
				acceptedNameUsage += "|";
			}
			acceptedNameUsageID += currParent.getId();
			acceptedNameUsage += currParent.getLookup().getCalnameauthor();
		}
		
		dwcaWriter.addCoreColumn(DwcTerm.acceptedNameUsageID,acceptedNameUsageID);
		dwcaWriter.addCoreColumn(DwcTerm.acceptedNameUsage, acceptedNameUsage);
		
		// do not use for synonyms
		dwcaWriter.addCoreColumn(DwcTerm.parentNameUsageID, "");
		dwcaWriter.addCoreColumn(DwcTerm.parentNameUsage, "");
				
		dwcaWriter.addCoreColumn(DwcTerm.nameAccordingToID, currReference.getUrl());
		dwcaWriter.addCoreColumn(DwcTerm.nameAccordingTo, currReference.getReference());
		
		dwcaWriter.addCoreColumn(DwcTerm.scientificName, currTaxonLookup.getCalnameauthor());
		dwcaWriter.addCoreColumn(DwcTerm.scientificNameAuthorship, synonymTaxonModel.getAuthor());
		
		dwcaWriter.addCoreColumn(DwcTerm.higherClassification, "");
		dwcaWriter.addCoreColumn(DwcTerm.class_, "");
		dwcaWriter.addCoreColumn(DwcTerm.order, "");
		dwcaWriter.addCoreColumn(DwcTerm.family, "");
		
		dwcaWriter.addCoreColumn(DwcTerm.genus, currTaxonLookup.getGenus());
		dwcaWriter.addCoreColumn(DwcTerm.subgenus, "");

		dwcaWriter.addCoreColumn(DwcTerm.specificEpithet, currTaxonLookup.getSpecificepithet());
		dwcaWriter.addCoreColumn(DwcTerm.infraspecificEpithet, currTaxonLookup.getInfraspecificepithet());
		
		dwcaWriter.addCoreColumn(DwcTerm.taxonRank, StringUtils.defaultString(currTaxonLookup.getRank()).toLowerCase());
		dwcaWriter.addCoreColumn(DwcTerm.taxonomicStatus, StringUtils.defaultString(currTaxonLookup.getStatus()).toLowerCase());
	}
	
	/**
	 * Add a Distribution extension records to the current core record.
	 * @param distributionModel
	 * @param dwcaWriter
	 * @param resourceBundle
	 * @throws IOException
	 */
	public void addExtDistributionRecords(Set<DistributionModel> distributionModelList, DwcaWriter dwcaWriter, ResourceBundle resourceBundle) throws IOException{
		
		Map<Term,String> recordValues = new HashMap<Term,String>();
		DistributionStatusModel currDistributionStatus = null;
		RegionModel currRegion = null;
		ReferenceModel currReference = null;
		ExcludedCodeModel currExcludedCode = null;
		
		for(DistributionModel distributionModel : distributionModelList){
			currDistributionStatus = distributionModel.getDistributionStatus();
			currRegion = distributionModel.getRegion();
			currReference = distributionModel.getReference();
			currExcludedCode = distributionModel.getExcludedcode();
					
			String localityID = "";
			if(StringUtils.isNotBlank(currRegion.getIso3166_2())){
				localityID = "ISO 3166-2:"+currRegion.getIso3166_2();
			}
			recordValues.put(DwcTerm.locationID, localityID);
			String locality = resourceBundle.getString("province_" + currRegion.getRegion().toUpperCase());
			recordValues.put(DwcTerm.locality, locality);
			recordValues.put(DwcTerm.countryCode, currRegion.getIso3166_1());
			recordValues.put(DwcTerm.occurrenceStatus, currDistributionStatus.getOccurrencestatus());
			recordValues.put(DwcTerm.establishmentMeans, currDistributionStatus.getEstablishmentmeans());
			recordValues.put(DcTerm.source, currReference.getReference() + " " + currReference.getUrl());
			String excludedRemarks = "";
			
			if(currExcludedCode != null && StringUtils.isNotBlank(currExcludedCode.getExcludedcode())){
				excludedRemarks = resourceBundle.getString("excluded_" + currExcludedCode.getExcludedcode().toLowerCase());
			}
			recordValues.put(DwcTerm.occurrenceRemarks, excludedRemarks);
			
			dwcaWriter.addExtensionRecord(GbifTerm.Distribution, recordValues);
			recordValues.clear();
		}
		
	}
	
	/**
	 * Add a VernacularName extension record to the current core record.
	 * @param vernacularNameModel
	 * @param dwcaWriter
	 * @throws IOException
	 */
	public void addExtVernacularRecords(Set<VernacularNameModel> vernacularNameModelList, DwcaWriter dwcaWriter) throws IOException{
		Map<Term,String> recordValues = new HashMap<Term,String>();
		ReferenceModel currReference = null;

		for(VernacularNameModel vernacularNameModel : vernacularNameModelList){
			currReference = vernacularNameModel.getReference();
			
			recordValues.put(DwcTerm.vernacularName, vernacularNameModel.getName());
			recordValues.put(DcTerm.source, currReference.getReference() + " " + currReference.getUrl());
			recordValues.put(DcTerm.language, vernacularNameModel.getLanguage().toUpperCase());
			
			recordValues.put(GbifTerm.isPreferredName, BooleanUtils.toStringTrueFalse(vernacularNameModel.getStatus().getId() == Status.ACCEPTED));
			
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
	public void addExtDescriptionRecord(TaxonLookupModel taxonLookupModel, DwcaWriter dwcaWriter) throws IOException{
		
		Map<Term,String> recordValues = new HashMap<Term,String>();
		recordValues.put(DcTerm.description, taxonLookupModel.getCalhabit());
		dwcaWriter.addExtensionRecord(GbifTerm.Description, recordValues);
	}
}
