package net.canadensys.dataportal.vascan;

import java.util.List;
import java.util.Map;

import net.canadensys.dataportal.vascan.model.api.VascanAPIResponse;
import net.canadensys.dataportal.vascan.model.api.VascanAPIResponseElement;

/**
 * Vascan API service layer interface.
 * @author canadensys
 *
 */
public interface APIService {
	
	public String getAPIVersion();
	
	/**
	 * Get the OpenRefine Reconciliation Service Metadata.
	 * 
	 * @return
	 */
	public Map<String,Object> getReconciliationServiceMetadata();
	
	/**
	 * Search for a single string value.
	 * 
	 * @param searchString
	 * @return
	 */
	public VascanAPIResponseElement search(String searchString);
	
	/**
	 * Search for a list of id(optional) and searchTerm
	 * @param idList list of local identifiers associated with searchTermList.
	 * 		  May contains null(s) but size must match searchTermList 
	 * @param searchTermList
	 * @return
	 */
	public VascanAPIResponse search(List<String> idList, List<String> searchTermList);
	
	/**
	 * Search for a single id(optional) and searchTerm.
	 * @param id use null if not provided
	 * @param searchTerm
	 * @return
	 */
	public VascanAPIResponse search(String id, String searchTerm);
	
	/**
	 * Search for a taxonID
	 * @param taxonID
	 * @return
	 */
	public VascanAPIResponse searchTaxonId(Integer taxonID);
	
	/**
	 * Search for a list of taxonID
	 * @param taxonIdList
	 * @return
	 */
	public VascanAPIResponse searchTaxonId(List<Integer> taxonIdList);
}
