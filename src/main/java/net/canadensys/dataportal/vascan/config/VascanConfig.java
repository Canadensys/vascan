package net.canadensys.dataportal.vascan.config;

import java.util.ArrayList;
import java.util.List;

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
	
	private String lastPublicationDateFilePath;
	
	private volatile String lastPublicationDate;
	
	//This variable can be updated after the app deployment
	private final StringBuffer mutableLastPublicationDate = new StringBuffer("");
	
	private String taxonUrl;

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
	
	
	public void updateLastPublicationDate(String newPublicationDate){
		mutableLastPublicationDate.replace(0, mutableLastPublicationDate.length(), newPublicationDate);
	}
	
	public StringBuffer getLastPublicationDate() {
		return mutableLastPublicationDate;
	}
	
	/**
	 * Get the current lastPublicationDate.
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
}
