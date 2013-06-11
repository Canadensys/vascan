package net.canadensys.dataportal.vascan;

import java.util.Map;

public interface NameService {
	
	/**
	 * 
	 * @param name
	 * @param redirect
	 * @param extra
	 * @return the filled data map or null if the name was not found
	 */
	public Map<String,Object> retrieveNameData(String name, String redirect,Map<String,Object> extra);

}
