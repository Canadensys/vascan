package net.canadensys.dataportal.vascan;

import java.util.Map;

import net.canadensys.dataportal.vascan.model.TaxonModel;
import net.canadensys.dataportal.vascan.model.VernacularNameModel;

/**
 * Vascan Service layer interface to access Vascan related data. This interface handles only high-level methods.
 * @author canadensys
 *
 */
public interface VascanService {
	
	/**
	 * Load a VernacularNameModel from an id
	 * @param id
	 * @return
	 */
	public VernacularNameModel loadVernacularNameModel(Integer id);
	
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
	 * @return
	 */
	public Map<String,Object> retrieveTaxonData(Integer taxonId);

}
