package net.canadensys.dataportal.vascan.model.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Using DarwinCore terms as key when possible.
 * @author canadensys
 *
 */
public class TaxonAPIResult {
	
	private String scientificName;
	private String canonicalName;
	private String scientificNameAuthorship;
	private String nameAccordingTo; //not for synonyms
	private String nameAccordingToID; //url, //not for synonyms
	private Integer taxonID;
	private String taxonomicStatus;
	private String taxonRank;
	private String parentNameUsageID; //not for synonyms
	private String acceptedNameUsage;
	private String acceptedNameUsageID; //for synonyms, this will link to the accepted taxon
	private String higherClassification; //not for synonyms
	
	private List<VernacularNameAPIResult> vernacularNames;//not for synonyms
	private List<DistributionAPIResult> distributions; //not for synonyms

	private String habit;
    //modified : "2013-03-08 00:00:0"

	public String getScientificName() {
		return scientificName;
	}
	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getCanonicalName() {
		return canonicalName;
	}

	public void setCanonicalName(String canonicalName) {
		this.canonicalName = canonicalName;
	}

	public String getScientificNameAuthorship() {
		return scientificNameAuthorship;
	}

	public void setScientificNameAuthorship(String scientificNameAuthorship) {
		this.scientificNameAuthorship = scientificNameAuthorship;
	}

	public String getNameAccordingTo() {
		return nameAccordingTo;
	}

	public void setNameAccordingTo(String nameAccordingTo) {
		this.nameAccordingTo = nameAccordingTo;
	}

	public String getNameAccordingToID() {
		return nameAccordingToID;
	}

	public void setNameAccordingToID(String nameAccordingToID) {
		this.nameAccordingToID = nameAccordingToID;
	}

	public Integer getTaxonID() {
		return taxonID;
	}

	public void setTaxonID(Integer taxonID) {
		this.taxonID = taxonID;
	}

	public String getTaxonomicStatus() {
		return taxonomicStatus;
	}

	public void setTaxonomicStatus(String taxonomicStatus) {
		this.taxonomicStatus = taxonomicStatus;
	}

	public String getTaxonRank() {
		return taxonRank;
	}

	public void setTaxonRank(String taxonRank) {
		this.taxonRank = taxonRank;
	}

	public String getParentNameUsageID() {
		return parentNameUsageID;
	}

	public void setParentNameUsageID(String parentNameUsageID) {
		this.parentNameUsageID = parentNameUsageID;
	}

	public String getAcceptedNameUsage() {
		return acceptedNameUsage;
	}

	public void setAcceptedNameUsage(String acceptedNameUsage) {
		this.acceptedNameUsage = acceptedNameUsage;
	}

	public String getAcceptedNameUsageID() {
		return acceptedNameUsageID;
	}

	public void setAcceptedNameUsageID(String acceptedNameUsageID) {
		this.acceptedNameUsageID = acceptedNameUsageID;
	}

	public String getHigherClassification() {
		return higherClassification;
	}

	public void setHigherClassification(String higherClassification) {
		this.higherClassification = higherClassification;
	}

	public List<VernacularNameAPIResult> getVernacularNames() {
		return vernacularNames;
	}
	public void setVernacularNames(List<VernacularNameAPIResult> vernacularNames) {
		this.vernacularNames = vernacularNames;
	}
	public void addVernacularName(VernacularNameAPIResult vernacularName){
		if(vernacularNames == null){
			vernacularNames = new ArrayList<VernacularNameAPIResult>();
		}
		vernacularNames.add(vernacularName);
	}

	public List<DistributionAPIResult> getDistributions() {
		return distributions;
	}
	public void setDistributions(List<DistributionAPIResult> distribution) {
		this.distributions = distribution;
	}
	public void addDistribution(DistributionAPIResult distribution){
		if(distributions == null){
			distributions = new ArrayList<DistributionAPIResult>();
		}
		distributions.add(distribution);
	}

	public String getHabit() {
		return habit;
	}
	public void setHabit(String habit) {
		this.habit = habit;
	}

}
