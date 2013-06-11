package net.canadensys.dataportal.vascan;

/**
 * Service used to access dynamic images.
 * @author canadensys
 *
 */
public interface ImageService {
	
	/**
	 * 
	 * @param taxonId
	 * @param imageExtension
	 * @return the image absolute path or null if the image could not be generated
	 */
	public String getImage(Integer taxonId, String imageExtension);
}
