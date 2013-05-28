package net.canadensys.dataportal.vascan;

import net.canadensys.dataportal.vascan.model.VernacularNameModel;

public interface VernacularNameService {
	
	/**
	 * Load a VernacularNameModel from an id
	 * @param id
	 * @return
	 */
	public VernacularNameModel loadVernacularNameModel(Integer id);

}
