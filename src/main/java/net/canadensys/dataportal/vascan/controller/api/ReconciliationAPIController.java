package net.canadensys.dataportal.vascan.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.canadensys.dataportal.vascan.APIService;
import net.canadensys.dataportal.vascan.model.api.TaxonAPIResult;
import net.canadensys.dataportal.vascan.model.api.VascanAPIResponseElement;
import net.canadensys.dataportal.vascan.model.api.reconciliation.ReconciliationResponse;
import net.canadensys.dataportal.vascan.model.api.reconciliation.ReconciliationResult;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Controller responsible to handle OpenRefine Reconciliation API calls.
 * 
 * @author canadensys
 *
 */
@Controller
public class ReconciliationAPIController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReconciliationAPIController.class);
	private static final String JSON_QUERY_PARAMETER = "query";
	
	@Autowired
	private APIService apiService;
	
	@RequestMapping(value="/api/{version}/reconcile",method={RequestMethod.GET},params="callback")
	public void handleReconciliation(@PathVariable String version,@RequestParam String callback,
			HttpServletRequest request, HttpServletResponse response){
		
		if(!APIControllerHelper.JSONP_ACCEPTED_CHAR_PATTERN.matcher(callback).matches()){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		//make sure the answer is set as UTF-8
		response.setCharacterEncoding("UTF-8");
		response.setContentType(APIControllerHelper.JSONP_CONTENT_TYPE);
		
		String json;
		try {
			json = APIController.JACKSON_MAPPER.writeValueAsString(getServiceMetadata());
			String responseTxt = callback + "("+json+");";
			response.getWriter().print(responseTxt);
			response.setContentLength(responseTxt.length());
			response.getWriter().close();
		}
		catch (JsonProcessingException e) {
			LOGGER.warn("Can't parse received json", e);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		catch (IOException e) {
			LOGGER.warn("Can't parse received json", e);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	/**
	 * OpenRefine Reconciliation service.
	 * TODO jsonp support
	 * 
	 * @param query
	 * @param queries
	 * @param version
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/api/{version}/reconcile",method={RequestMethod.GET})
	public @ResponseBody Object handleReconciliation(@RequestParam(required=false) String query, @RequestParam(required=false) String queries, @PathVariable String version,
			HttpServletRequest request, HttpServletResponse response){
		
		Object responseObject;
		
		if(!apiService.getAPIVersion().equals(version)){
			return APIController.notFound(response);
		}
		
		// determine if we have a single or multiple query mode
		if(StringUtils.isNotBlank(query)){
			try{
				responseObject = runSingleQueryReconciliation(query);
			}
			catch(IllegalArgumentException iaEx){
				responseObject = APIController.badRequest(response);
			}
		} //Multiple query mode
		else if(StringUtils.isNotBlank(queries)){
			try{
				responseObject = runMultipleQueryReconciliation(queries);
			}
			catch(IllegalArgumentException iaEx){
				responseObject = APIController.badRequest(response);
			}
		}
		else{
			return APIController.badRequest(response);
		}

		return responseObject;
	}
	
	/**
	 * Get the reconciliation service metadata.
	 * 
	 * @return
	 */
	private Map<String,Object> getServiceMetadata(){
		Map<String,Object> serviceMetadata = new HashMap<String, Object>();
		serviceMetadata.put("name", "Database of Vascular Plants of Canada");
		serviceMetadata.put("identifierSpace", "http://data.canadensys.net/vascan/");
		serviceMetadata.put("schemaSpace", "http://rdf.freebase.com/ns/type.object.id"); // FreeBase object id
		
		Map<String,String> serviceType = new HashMap<String, String>();
		serviceType.put("id", "/biology/organism_classification/scientific_name");
		serviceType.put("name", "Scientific name");
		
		serviceMetadata.put("defaultTypes", new Map<?,?>[]{serviceType});
		
		return serviceMetadata;
	}
	
	/**
	 * Run a single query mode reconciliation.
	 * 
	 * @param query "carex" or "{"query":"carex"}
	 * @param response used to set the status code in case of error
	 * @return
	 * @throws IllegalArgumentException if error while parsing json
	 */
	private ReconciliationResponse runSingleQueryReconciliation(String query) throws IllegalArgumentException{
		String searchString = "";
		if(query.startsWith("{") && query.endsWith("}")){
			Map<String,String> queryData = null;
			
			try {
				queryData = APIController.JACKSON_MAPPER.readValue(query,  new TypeReference<Map<String,String>>(){} );
				searchString = queryData.get(JSON_QUERY_PARAMETER);
			}
			catch (JsonProcessingException e) {
				LOGGER.warn("Can't parse received json", e);
				throw new IllegalArgumentException();
			}
			catch (IOException e) {
				throw new IllegalArgumentException();
			}
		}
		else{
			searchString = query;
		}
		
		ReconciliationResponse reconciliationResponse = new ReconciliationResponse();
		reconciliationResponse.addAll(getReconciliationResponse(searchString));
		
		return reconciliationResponse;
	}
	
	/**
	 * Run a multiple query mode reconciliation.
	 * 
	 * @param queries queries in json format { "q0" : { "query" : "foo" }, "q1" : { "query" : "bar" } }
	 * @return
	 * @throws IllegalArgumentException if error while parsing json
	 */
	private Map<String,ReconciliationResponse> runMultipleQueryReconciliation(String queries) throws IllegalArgumentException {
		Map<String,ReconciliationResponse> resultMap = new LinkedHashMap<String, ReconciliationResponse>();
		Map<String,Map<String,String>> queryData = null;

		try {
			queryData = APIController.JACKSON_MAPPER.readValue(queries,  new TypeReference<Map<String,Map<String,String>>>(){} );
			
			for(String q : queryData.keySet()){
				ReconciliationResponse reconciliationResponse = new ReconciliationResponse();
				reconciliationResponse.addAll(getReconciliationResponse(queryData.get(q).get(JSON_QUERY_PARAMETER)));
				resultMap.put(q, reconciliationResponse);
			}
		}
		catch (JsonProcessingException e) {
			LOGGER.warn("Can't parse received json", e);
			throw new IllegalArgumentException();
		}
		catch (IOException e) {
			throw new IllegalArgumentException();
		}
		
		return resultMap;
	}
	
	/**
	 * Get a list of ReconciliationResult from a searchString.
	 * Internally this method will create ReconciliationResult from a VascanAPIResponseElement object.
	 * 
	 * @param searchString
	 * @return
	 */
	private List<ReconciliationResult> getReconciliationResponse(String searchString){
		VascanAPIResponseElement responseElement = apiService.search(searchString);
		List<TaxonAPIResult> matches = responseElement.getMatches();
		List<ReconciliationResult> rResult = new ArrayList<ReconciliationResult>();
		
		if(responseElement.getNumMatches() > 0){
			for(TaxonAPIResult tar : matches){
				ReconciliationResult rr = new ReconciliationResult();
				rr.setId(tar.getTaxonID().toString());
				rr.setName(tar.getScientificName());
				rResult.add(rr);
			}
		}
		return rResult;
	}
	
}
