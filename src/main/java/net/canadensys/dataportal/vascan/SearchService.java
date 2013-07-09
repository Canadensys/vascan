package net.canadensys.dataportal.vascan;

import java.util.List;

import net.canadensys.dataportal.vascan.model.NameConceptModelIF;
import net.canadensys.query.LimitedResult;

/**
 * Vascan Service layer interface to search Vascan data.
 * @author canadensys
 *
 */
public interface SearchService {
	
	public LimitedResult<List<NameConceptModelIF>> searchName(String name);
	public LimitedResult<List<NameConceptModelIF>> searchName(String text, int page);
	public List<NameConceptModelIF> searchTaxon(String name);
	public List<NameConceptModelIF> searchVernacularName(String name);

	/**
	 * Get the size (default) used to search a name with paging capability.
	 * @return
	 */
	public int getPageSize();
}
