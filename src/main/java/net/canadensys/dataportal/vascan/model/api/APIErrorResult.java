package net.canadensys.dataportal.vascan.model.api;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Used to report an API error.
 * @author canadensys
 *
 */
@XmlRootElement
public class APIErrorResult {
	
	private String error;
	
	public APIErrorResult(){}
	public APIErrorResult(String error){
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
