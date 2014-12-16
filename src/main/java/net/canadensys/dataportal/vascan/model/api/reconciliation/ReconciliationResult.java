package net.canadensys.dataportal.vascan.model.api.reconciliation;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * OpenRefine Reconciliation result object.
 * 
 * @author cgendreau
 *
 */
@JsonInclude(Include.NON_NULL)
public class ReconciliationResult {

	private String id; //database ID ...
	private String name;
	private Collection<Map<String,String>> type; 
	private boolean match;
	private double score;
	
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
	public Collection<Map<String,String>> getType() {
		return type;
	}
	public void setType(Collection<Map<String,String>> type) {
		this.type = type;
	}
	public boolean isMatch() {
		return match;
	}
	public void setMatch(boolean match) {
		this.match = match;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
}
