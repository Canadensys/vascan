package net.canadensys.dataportal.vascan;

import java.util.Map;

public interface ChecklistService {
	
	public Map<String,Object> retrieveChecklistData(Map<String,String[]> parameters);

}
