package net.canadensys.dataportal.vascan.model.api;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * root of response object
 * @author canadensys
 *
 */
@XmlRootElement
public class VascanAPIResponse {
	
	private String apiVersion;
	private String lastUpdatedDate;
	
	private List<VascanAPIResponseElement> results;
	
	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@XmlElementWrapper(name="results")
	@XmlElement(name="searchedName")
	@JsonProperty(value="results")
	public List<VascanAPIResponseElement> getResults() {
		return results;
	}
	
	public void addResult(VascanAPIResponseElement vascanAPIResponseElement){
		if(results == null){
			results = new ArrayList<VascanAPIResponseElement>();
		}
		results.add(vascanAPIResponseElement);
	}

}
