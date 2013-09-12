package net.canadensys.dataportal.vascan.model.api;

public class DistributionAPIResult {
	
	private String locationID; // : “CA-BC”,
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
