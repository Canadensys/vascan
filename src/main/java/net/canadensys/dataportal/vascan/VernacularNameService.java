package net.canadensys.dataportal.vascan;

import java.util.Map;

import net.canadensys.dataportal.vascan.model.VernacularNameModel;

public interface VernacularNameService {
	
	/**
	 * Load a VernacularNameModel from an id
	 * @param id
	 * @return
	 */
	public Map<String,Object> loadVernacularNameModel(Integer id);

}
