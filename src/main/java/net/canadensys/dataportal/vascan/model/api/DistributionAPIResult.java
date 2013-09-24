package net.canadensys.dataportal.vascan.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * API response for distribution related data.
 * @author canadensys
 *
 */
@JsonInclude(Include.NON_NULL)
public class DistributionAPIResult {
	
	private String locationID; // : “ISO 3166-2:CA-BC”,
	private String locality; // : “British Columbia”,
	private String establishmentMeans; // : “native”,
	private String occurrenceStatus; // : “present”
	
	public String getLocationID() {
		return locationID;
	}
	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getEstablishmentMeans() {
		return establishmentMeans;
	}
	public void setEstablishmentMeans(String establishmentMeans) {
		this.establishmentMeans = establishmentMeans;
	}
	public String getOccurrenceStatus() {
		return occurrenceStatus;
	}
	public void setOccurrenceStatus(String occurrenceStatus) {
		this.occurrenceStatus = occurrenceStatus;
	}

}
