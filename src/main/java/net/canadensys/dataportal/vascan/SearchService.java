package net.canadensys.dataportal.vascan;

import java.util.List;

import net.canadensys.dataportal.vascan.model.NameConceptModelIF;

public interface SearchService {
	
	public List<NameConceptModelIF> searchName(String name);
	public List<NameConceptModelIF> searchTaxon(String name);
	public List<NameConceptModelIF> searchVernacularName(String name);

}
