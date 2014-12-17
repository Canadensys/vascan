package net.canadensys.dataportal.vascan;

import java.util.List;
import java.util.Map;

import net.canadensys.dataportal.vascan.model.api.reconciliation.ReconciliationResult;

/**
 * Vascan API for OpenRefine, service layer interface.
 * 
 * @author cgendreau
 *
 */
public interface APIRefineService {
	
	/**
	 * Get the OpenRefine Reconciliation Service Metadata.
	 * 
	 * @return
	 */
	Map<String,Object> getReconciliationServiceMetadata();
	
	/**
	 * Reconcile a string.
	 * Get a list of ReconciliationResult from a searchString.
	 * 
	 * @param searchString
	 * @return List, never null
	 */
	List<ReconciliationResult> reconcile(String searchString);

}
