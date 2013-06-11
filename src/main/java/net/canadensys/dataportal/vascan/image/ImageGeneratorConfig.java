package net.canadensys.dataportal.vascan.image;

/**
 * Class containing the configuration for an Image generator.
 * @author canadensys
 *
 */
public class ImageGeneratorConfig {
	
	private static final String DISTRIBUTION_FILE_PREFIX = "distribution";
	
	private String distributionImagePrefix = DISTRIBUTION_FILE_PREFIX;
	private String imageFolder;
	

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
}
