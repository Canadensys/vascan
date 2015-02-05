package net.canadensys.dataportal.vascan.config;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

/**
 * General configurations for VASCAN. Those configurations are not tied to a specific service.
 * @author canadensys
 *
 */
public class VascanConfig {
	
	//Key used for models in view
	public static final String PAGE_ROOT_MODEL_KEY = "page";
	public static final String LANG_PARAM = "lang";
	
	public static final List<String> SUPPORTED_LANGUAGES = new ArrayList<String>();
	static{
		SUPPORTED_LANGUAGES.add("en");
		SUPPORTED_LANGUAGES.add("fr");
	}
	
	private String currentVersion;
	private String gaSiteVerification;
	private String gaAccount;
	private String feedbackURL;
	
	private String lastPublicationDateFilePath;
	
	//This variable can be updated after the app deployment
	private final StringBuffer mutableLastPublicationDate = new StringBuffer("");
	
	private String taxonUrl;
	
	//TODO should move to its own configuration class eventually
	private Integer searchPageSize;

	public String getLastPublicationDateFilePath() {
		return lastPublicationDateFilePath;
	}
	public void setLastPublicationDateFilePath(String lastPublicationDateFilePath) {
		this.lastPublicationDateFilePath = lastPublicationDateFilePath;
	}
	
	public String getCurrentVersion() {
		return currentVersion;
	}
	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}
	
	/**
	 * Update the lastPublicationDate.
	 * 
	 * @param newPublicationDate
	 */
	public void updateLastPublicationDate(String newPublicationDate){
		mutableLastPublicationDate.replace(0, mutableLastPublicationDate.length(), newPublicationDate);
	}
	
	public StringBuffer getLastPublicationDate() {
		return mutableLastPublicationDate;
	}
	
	/**
	 * Get the current lastPublicationDate as String.
	 * This value can change after application deploy.
	 * 
	 * @return String of the lastPublicationDate at the moment of the call
	 */
	public String getCurrentLastPublicationDate() {
		return mutableLastPublicationDate.toString();
	}
	
	public String getTaxonUrl() {
		return taxonUrl;
	}
	public void setTaxonUrl(String taxonUrl) {
		this.taxonUrl = taxonUrl;
	}
	
	public String getGaSiteVerification() {
		return gaSiteVerification;
	}
	public void setGaSiteVerification(String gaSiteVerification) {
		this.gaSiteVerification = gaSiteVerification;
	}
	
	public String getGaAccount() {
		return gaAccount;
	}
	public void setGaAccount(String gaAccount) {
		this.gaAccount = gaAccount;
	}
	
	public String getFeedbackURL() {
		return feedbackURL;
	}
	public void setFeedbackURL(String feedbackURL) {
		this.feedbackURL = feedbackURL;
	}
	
	/**
	 * Get the number of results per page to send back after a search.
	 * Can be null if not specified.
	 * 
	 * @return
	 */
	public Integer getSearchPageSize() {
		return searchPageSize;
	}
	
	/**
	 * Set the number of results per page to send back after a search.
	 * 
	 * @param searchPageSize
	 */
	public void setSearchPageSize(Integer searchPageSize) {
		Preconditions.checkNotNull(searchPageSize);
		
		this.searchPageSize = searchPageSize;
	}
}
