package net.canadensys.dataportal.vascan;

import java.util.Map;

/**
 * Vascan Service layer interface to generate a checklist.
 * @author canadensys
 *
 */
public interface ChecklistService {
	
	public Map<String,Object> retrieveChecklistData(Map<String,String[]> parameters);
}
