package net.canadensys.dataportal.vascan.model.api.reconciliation;

/**
 * OpenRefine Reconciliation result object.
 * 
 * @author cgendreau
 *
 */
public class ReconciliationResult {

	private String id; //database ID ...
	private String name;
	private String[] type; 
	private boolean match;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getType() {
		return type;
	}
	public void setType(String[] type) {
		this.type = type;
	}
	public boolean isMatch() {
		return match;
	}
	public void setMatch(boolean match) {
		this.match = match;
	}

	//"score" : ... double ...
	//"match" : ... boolean, true if the service is quite confident about the match ...
}
