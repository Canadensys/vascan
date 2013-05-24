package net.canadensys.dataportal.vascan;

import java.util.List;
import java.util.Map;

import net.canadensys.dataportal.vascan.model.TaxonLookupModel;

public interface DistributionService {
	
	public List<Map<String,String>> getComputedDistribution(TaxonLookupModel taxonLookupModel);

}
