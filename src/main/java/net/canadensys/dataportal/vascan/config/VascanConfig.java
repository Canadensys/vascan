package net.canadensys.dataportal.vascan.config;

/**
 * General configurations for VASCAN. Those configurations are not tied to a specific service.
 * @author canadensys
 *
 */
public class VascanConfig {
	
	private String currentVersion;
	private String lastPublicationDateFilePath;

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
}
