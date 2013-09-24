package net.canadensys.dataportal.vascan.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TaxonomicAssertionAPIResult {
	
	private String acceptedNameUsage;
	private Integer acceptedNameUsageID;
	
	private String nameAccordingTo;
	private String nameAccordingToID;
	
	private String taxonomicStatus;
	private Integer parentNameUsageID;
	
	private String higherClassification;
	
	public String getAcceptedNameUsage() {
		return acceptedNameUsage;
	}
	public void setAcceptedNameUsage(String acceptedNameUsage) {
		this.acceptedNameUsage = acceptedNameUsage;
	}

	public Integer getAcceptedNameUsageID() {
		return acceptedNameUsageID;
	}

	public void setAcceptedNameUsageID(Integer acceptedNameUsageID) {
		this.acceptedNameUsageID = acceptedNameUsageID;
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

	public String getTaxonomicStatus() {
		return taxonomicStatus;
	}

	public void setTaxonomicStatus(String taxonomicStatus) {
		this.taxonomicStatus = taxonomicStatus;
	}

	public Integer getParentNameUsageID() {
		return parentNameUsageID;
	}

	public void setParentNameUsageID(Integer parentNameUsageID) {
		this.parentNameUsageID = parentNameUsageID;
	}

	public String getHigherClassification() {
		return higherClassification;
	}

	public void setHigherClassification(String higherClassification) {
		this.higherClassification = higherClassification;
	}
}
