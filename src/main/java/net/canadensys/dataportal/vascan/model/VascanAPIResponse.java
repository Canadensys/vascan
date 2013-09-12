package net.canadensys.dataportal.vascan.model;

import java.util.ArrayList;
import java.util.List;

import net.canadensys.dataportal.vascan.model.api.TaxonAPIResult;

/**
 * 
 * 
 * @author canadensys
 *
 */
public class VascanAPIResponse {
	
	private String searchTerm;
	private int numResults;

	private List<TaxonAPIResult> results;

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
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

}
