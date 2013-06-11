package net.canadensys.dataportal.vascan;

import java.util.Map;

import net.canadensys.dataportal.vascan.model.TaxonModel;

/**
 * Vascan Service layer interface to access taxon related data. This interface handles only high-level methods.
 * @author canadensys
 *
 */
public interface TaxonService {
	
	/**
	 * Load a TaxonModel from an id
	 * @param id
	 * @return
	 */
	public TaxonModel loadTaxonModel(Integer id);

	/**
	 * Get all available information for an accepted taxon,
	 * or only base (name, rank, status, authority...) information in 
	 * case of an synonym taxon. 
	 * @param taxonId
	 * @return the filled data map or null if the taxon was not found
	 */
	public Map<String,Object> retrieveTaxonData(Integer taxonId);

}
