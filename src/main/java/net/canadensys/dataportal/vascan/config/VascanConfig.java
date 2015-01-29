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
	
	// volatile variable to ensure consistency if updated by an external thread
	private volatile String lastPublicationDate;
	
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
	
	public String getLastPublicationDate() {
		return lastPublicationDate;
	}
	public void setLastPublicationDate(String lastPublicationDate) {
		this.lastPublicationDate = lastPublicationDate;
	}
	
	public String getTaxonUrl() {
		return taxonUrl;
	}
	public void setTaxonUrl(String taxonUrl) {
		this.taxonUrl = taxonUrl;
	}
}
