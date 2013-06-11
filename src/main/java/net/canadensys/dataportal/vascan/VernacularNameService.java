package net.canadensys.dataportal.vascan;

import java.util.Map;

public interface VernacularNameService {
	
	/**
	 * Load a VernacularNameModel from an id
	 * @param id
	 * @return the filled data map or null if the vernacular name was not found
	 */
	public Map<String,Object> loadVernacularNameModel(Integer id);

}
