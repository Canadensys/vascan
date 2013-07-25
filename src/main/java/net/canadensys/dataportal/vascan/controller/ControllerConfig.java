package net.canadensys.dataportal.vascan.controller;

/**
 * Configuration used by some controller's function.
 * @author canadensys
 *
 */
public class ControllerConfig {
	
	private String lastPublicationDateFilePath;

	public String getLastPublicationDateFilePath() {
		return lastPublicationDateFilePath;
	}
	public void setLastPublicationDateFilePath(String lastPublicationDateFilePath) {
		this.lastPublicationDateFilePath = lastPublicationDateFilePath;
	}
}
