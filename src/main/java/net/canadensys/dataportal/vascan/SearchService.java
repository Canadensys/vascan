package net.canadensys.dataportal.vascan;

import java.util.List;

import net.canadensys.dataportal.vascan.model.NameConceptModelIF;
import net.canadensys.query.LimitedResult;

public interface SearchService {
	
	public LimitedResult<List<NameConceptModelIF>> searchName(String name);
	public List<NameConceptModelIF> searchTaxon(String name);
	public List<NameConceptModelIF> searchVernacularName(String name);

}
