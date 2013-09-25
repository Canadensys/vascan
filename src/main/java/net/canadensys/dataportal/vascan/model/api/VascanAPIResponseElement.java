package net.canadensys.dataportal.vascan.model.api;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Response element object. If the API need to send back a list of names, multiple VascanAPIResponseElement are used.
 * @author canadensys
 *
 */
@JsonInclude(Include.NON_NULL)
public class VascanAPIResponseElement {
	
	private String searchedTerm;
	private Integer searchedId;
	private String localIdentifier;
	
	private int numMatches;

	private List<TaxonAPIResult> matches;

	public String getSearchedTerm() {
		return searchedTerm;
	}
	public void setSearchedTerm(String searchedTerm) {
		this.searchedTerm = searchedTerm;
	}
	
	public Integer getSearchedId() {
		return searchedId;
	}
	public void setSearchedId(Integer searchedId) {
		this.searchedId = searchedId;
	}
	
	public String getLocalIdentifier() {
		return localIdentifier;
	}
	public void setLocalIdentifier(String localIdentifier) {
		this.localIdentifier = localIdentifier;
	}
	
	public int getNumMatches() {
		return numMatches;
	}
	public void setNumMatches(int numMatches) {
		this.numMatches = numMatches;
	}

	@XmlElementWrapper(name="matches")
	@XmlElement(name="searchedName")
	public List<TaxonAPIResult> getMatches() {
		return matches;
	}
	public void setMatches(List<TaxonAPIResult> matches) {
		this.matches = matches;
	}
	
	public void addMatch(TaxonAPIResult match){
		if(matches == null){
			matches = new ArrayList<TaxonAPIResult>();
		}
		matches.add(match);
	}
}
