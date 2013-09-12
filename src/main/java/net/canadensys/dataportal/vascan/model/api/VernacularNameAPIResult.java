package net.canadensys.dataportal.vascan.model.api;

/**
 * Could we merge this with VernacularNameModel? 
 * VernacularNameModel is heavier (hibernate based) but needed to be loaded anyway
 * @author cgendreau
 *
 */
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
