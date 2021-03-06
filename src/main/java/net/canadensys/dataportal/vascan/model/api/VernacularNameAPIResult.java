package net.canadensys.dataportal.vascan.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * API response for vernacular names related data.
 * @author canadensys
 *
 */
@JsonInclude(Include.NON_NULL)
public class VernacularNameAPIResult {
    private String vernacularName;
    private String language;
    private String source;
    private boolean isPreferredName;
    
	public String getVernacularName() {
		return vernacularName;
	}
	public void setVernacularName(String vernacularName) {
		this.vernacularName = vernacularName;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public boolean isPreferredName() {
		return isPreferredName;
	}
	public void setPreferredName(boolean isPreferredName) {
		this.isPreferredName = isPreferredName;
	}
}
