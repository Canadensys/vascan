package net.canadensys.dataportal.vascan.model.api;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * 
 * @author canadensys
 *
 */
@JsonInclude(Include.NON_NULL)
public class VascanAPIResponse {
	
	private String searchedTerm;
	private Integer searchedId;
	private String localIdentifier;
	
	private int numResults;
	private String apiVersion;

	private List<TaxonAPIResult> results;

	public String getSearchedTerm() {
		return searchedTerm;
	}
	public void setSearchTerm(String searchedTerm) {
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
	
	public int getNumResults() {
		return numResults;
	}
	public void setNumResults(int numResults) {
		this.numResults = numResults;
	}

	public List<TaxonAPIResult> getResults() {
		return results;
	}
	public void setResults(List<TaxonAPIResult> results) {
		this.results = results;
	}
	
	public void addResult(TaxonAPIResult result){
		if(results == null){
			results = new ArrayList<TaxonAPIResult>();
		}
		results.add(result);
	}

	public String getApiVersion() {
		return apiVersion;
	}
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

}
