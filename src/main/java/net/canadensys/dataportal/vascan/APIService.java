package net.canadensys.dataportal.vascan;

import java.util.List;

import net.canadensys.dataportal.vascan.model.api.VascanAPIResponse;

/**
 * Vascan API service layer interface.
 * @author canadensys
 *
 */
public interface APIService {
	
	public String getAPIVersion();
	
	/**
	 * Search for a list of id(optional) and searchTerm
	 * @param idList
	 * @param searchTermList
	 * @return
	 */
	public List<VascanAPIResponse> search(List<String> idList, List<String> searchTermList);
	
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
	public VascanAPIResponse search(Integer taxonID);
}
