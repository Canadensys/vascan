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
	
	private List<VascanAPIResponseElement> results;
	
	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	
	@XmlElementWrapper(name="results")
	@XmlElement(name="result")
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
