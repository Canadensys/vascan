package net.canadensys.dataportal.vascan.model.api;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * API response for taxon related data.
 * Using DarwinCore terms as key when possible.
 * @author canadensys
 *
 */
@JsonInclude(Include.NON_NULL)
public class TaxonAPIResult {
	private float score;
	private Integer taxonID;
	
	private String scientificName;
	private String scientificNameAuthorship;
	
	private String canonicalName;
	private String taxonRank;

	@XmlElementWrapper(name="taxonomicAssertions")
	@XmlElement(name="taxonomicAssertion")
	private List<TaxonomicAssertionAPIResult> taxonomicAssertions;
	
	private List<VernacularNameAPIResult> vernacularNames;
	private List<DistributionAPIResult> distribution;

	private String habit;
    //modified : "2013-03-08 00:00:0"

	/**
	 * The the score given to this taxon in a search context.
	 * @return
	 */
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	
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


	public Integer getTaxonID() {
		return taxonID;
	}
	public void setTaxonID(Integer taxonID) {
		this.taxonID = taxonID;
	}

	public String getTaxonRank() {
		return taxonRank;
	}

	public void setTaxonRank(String taxonRank) {
		this.taxonRank = taxonRank;
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

	public List<DistributionAPIResult> getDistribution() {
		return distribution;
	}
	public void setDistribution(List<DistributionAPIResult> distribution) {
		this.distribution = distribution;
	}
	public void addDistribution(DistributionAPIResult _distribution){
		if(distribution == null){
			distribution = new ArrayList<DistributionAPIResult>();
		}
		distribution.add(_distribution);
	}
	
	public List<TaxonomicAssertionAPIResult> getTaxonomicAssertions() {
		return taxonomicAssertions;
	}
	public void addTaxonomicAssertion(TaxonomicAssertionAPIResult taxonomicAssertionAPIResult){
		if(taxonomicAssertions == null){
			taxonomicAssertions = new ArrayList<TaxonomicAssertionAPIResult>();
		}
		taxonomicAssertions.add(taxonomicAssertionAPIResult);
	}

	public String getHabit() {
		return habit;
	}
	public void setHabit(String habit) {
		this.habit = habit;
	}
}
