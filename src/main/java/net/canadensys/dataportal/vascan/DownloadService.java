package net.canadensys.dataportal.vascan;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * Vascan Service layer interface to manage the download and the creation of generated content.
 * @author canadensys
 *
 */
public interface DownloadService {
	
	/**
	 * Generate an unique file name for a specific format
	 * @param format txt or dwca
	 * @return the generated filename
	 */
	public String generateFileName(String format);
	
	/**
	 * Get the URL to used to access the file once generated. The URL will be built according to the current configuration.
	 * @param filename
	 * @return complete URL to access the file
	 */
	public String getFileDownloadURL(String filename);
	
	/**
	 * Generate a text file for the provided params
	 * @param params from the original request
	 * @param filename unique filename
	 * @param bundle ResourceBundle used to fill the headers row
	 * @return
	 */
	public boolean generateTextFile(Map<String,String[]> params, String filename, ResourceBundle bundle);
	
	/**
	 * Generate a DarwinCore archive file for the provided params
	 * @param params from the original request
	 * @param filename unique filename
	 * @param bundle ResourceBundle used to fill the headers row
	 * @return
	 */
	public boolean generateDwcAFile(Map<String,String[]> params, String filename, ResourceBundle bundle);

}
