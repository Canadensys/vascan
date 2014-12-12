package net.canadensys.dataportal.vascan.model.api.reconciliation;

import java.util.ArrayList;
import java.util.List;

/**
 * OpenRefine Reconciliation response object.
 * 
 * @author cgendreau
 *
 */
public class ReconciliationResponse {
	
	private List<ReconciliationResult> result = new ArrayList<ReconciliationResult>();

	public List<ReconciliationResult> getResult() {
		return result;
	}

	public void add(ReconciliationResult result) {
		this.result.add(result);
	}
	
	public void addAll(List<ReconciliationResult> results) {
		this.result.addAll(results);
	}

}
