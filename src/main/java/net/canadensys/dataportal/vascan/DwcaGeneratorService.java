package net.canadensys.dataportal.vascan;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

public interface DwcaGeneratorService {
	
	public boolean generateDwcArchive(Iterator<Map<String,Object>> taxonModelIt, File tempDirectory, String archiveAbsolutePath, ResourceBundle resourceBundle);

}
