package net.canadensys.dataportal.vascan;

import java.util.Map;

public interface NameService {
	
	public Map<String,Object> retrieveNameData(String name, String redirect);

}
