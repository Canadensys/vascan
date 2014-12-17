package net.canadensys.dataportal.vascan.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.canadensys.dataportal.vascan.APIRefineService;
import net.canadensys.dataportal.vascan.APIService;
import net.canadensys.dataportal.vascan.config.VascanConfig;
import net.canadensys.dataportal.vascan.model.api.TaxonAPIResult;
import net.canadensys.dataportal.vascan.model.api.VascanAPIResponseElement;
import net.canadensys.dataportal.vascan.model.api.reconciliation.ReconciliationResult;

/**
 * Implementation of Vascan API for OpenRefine that uses the Vascan API internally.
 * @author cgendreau
 *
 */
@Service
public class APIRefineServiceImpl implements APIRefineService {
	
	private static final Integer PREVIEW_WIDTH = 430;
	private static final Integer PREVIEW_HEIGHT = 300;
	
	private static final List<Map<String,String>> SERVICE_TYPES = new ArrayList<Map<String, String>>();
	static{
		Map<String,String> service  = new HashMap<String, String>();
		service.put("id", "/biology/organism_classification/scientific_name");
		service.put("name", "Scientific name");
		SERVICE_TYPES.add(service);
	}
	
	@Autowired
	private APIService apiService;
	
	@Autowired
	private VascanConfig vascanConfig;
	
	/**
	 * Get the reconciliation service metadata.
	 * 
	 * @return
	 */
	@Override
	public Map<String,Object> getReconciliationServiceMetadata(){
		Map<String,Object> serviceMetadata = new HashMap<String, Object>();
		serviceMetadata.put("name", "Database of Vascular Plants of Canada");
		serviceMetadata.put("identifierSpace", "http://data.canadensys.net/vascan/");
		serviceMetadata.put("schemaSpace", "http://rdf.freebase.com/ns/type.object.id"); // FreeBase object id
		
		Map<String,String> viewMetadata = new HashMap<String, String>();
		viewMetadata.put("url", vascanConfig.getTaxonUrl().concat("{{id}}"));
		serviceMetadata.put("view", viewMetadata);
		
		Map<String,String> previewMetadata = new HashMap<String, String>();
		previewMetadata.put("url", vascanConfig.getTaxonUrl().concat("{{id}}"));
		previewMetadata.put("width", PREVIEW_WIDTH.toString());
		previewMetadata.put("height", PREVIEW_HEIGHT.toString()); 
		serviceMetadata.put("preview", previewMetadata);
		serviceMetadata.put("defaultTypes", SERVICE_TYPES);
		
		return serviceMetadata;
	}
	
	@Override
	public List<ReconciliationResult> reconcile(String searchString){
		VascanAPIResponseElement responseElement = apiService.search(searchString);
		List<TaxonAPIResult> matches = responseElement.getMatches();
		List<ReconciliationResult> rResult = new ArrayList<ReconciliationResult>();
		
		if(responseElement.getNumMatches() > 0){
			for(TaxonAPIResult tar : matches){
				ReconciliationResult rr = new ReconciliationResult();
				rr.setId(tar.getTaxonID().toString());
				rr.setName(tar.getScientificName());
				rr.setScore(1.0);
				rr.setType(SERVICE_TYPES);
				rResult.add(rr);
			}
		}
		return rResult;
	}

}
