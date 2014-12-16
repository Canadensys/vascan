package net.canadensys.dataportal.vascan.config;

/**
 * Class containing the configuration for the Vascan generated content.
 * @author canadensys
 *
 */
public class GeneratedContentConfig {
	
	private static final String DISTRIBUTION_FILE_PREFIX = "distribution";
	
	private String distributionImagePrefix = DISTRIBUTION_FILE_PREFIX;
	private String imageFolder;
	
	//text file and DwcA
	private String generatedFilesFolder;
	private String publicDownloadUrl;

	public String getImageFolder() {
		return imageFolder;
	}
	
	/**
	 * 
	 * @param imageFolder must ends with a slash
	 */
	public void setImageFolder(String imageFolder) {
		this.imageFolder = imageFolder;
	}
	
	public String getDistributionImagePrefix() {
		return distributionImagePrefix;
	}

	public void setDistributionImagePrefix(String distributionImagePrefix) {
		this.distributionImagePrefix = distributionImagePrefix;
	}
	public static String getDistributionFilePrefix() {
		return DISTRIBUTION_FILE_PREFIX;
	}

	public String getGeneratedFilesFolder() {
		return generatedFilesFolder;
	}
	public void setGeneratedFilesFolder(String generatedFilesFolder) {
		this.generatedFilesFolder = generatedFilesFolder;
	}

	public String getPublicDownloadUrl() {
		return publicDownloadUrl;
	}
	public void setPublicDownloadUrl(String publicDownloadUrl) {
		this.publicDownloadUrl = publicDownloadUrl;
	}
}
