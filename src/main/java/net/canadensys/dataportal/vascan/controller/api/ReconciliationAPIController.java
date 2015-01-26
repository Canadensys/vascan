package net.canadensys.dataportal.vascan.controller.api;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.canadensys.dataportal.vascan.APIRefineService;
import net.canadensys.dataportal.vascan.model.api.reconciliation.ReconciliationResponse;

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
	private static final String RECONCILIATION_API_VERSION = "0.1";
	
	@Autowired
	private APIRefineService apiRefineService;
	
	@RequestMapping(value="/refine/{version}/reconcile",method={RequestMethod.GET, RequestMethod.POST},params="callback")
	public void handleReconciliation(@PathVariable String version, String callback,
			@RequestParam(required=false) String query, @RequestParam(required=false) String queries,
			HttpServletRequest request, HttpServletResponse response){
		
		if(!APIControllerHelper.JSONP_ACCEPTED_CHAR_PATTERN.matcher(callback).matches()){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		//make sure the answer is set as UTF-8
		response.setCharacterEncoding("UTF-8");
		response.setContentType(APIControllerHelper.JSONP_CONTENT_TYPE);
		Object responseObject = null;
		
		if(StringUtils.isBlank(query) && StringUtils.isBlank(queries)){
			responseObject = apiRefineService.getReconciliationServiceMetadata();
			LOGGER.info("ReconciliationServiceMetadata|{}|{}|{}|{}", request.getMethod(), request.getRequestURI(), request.getQueryString(), request.getRemoteAddr());
		}
		else{
			responseObject = handleReconciliation(query, queries, version, request, response);
		}
		
		String json;
		try {
			json = APIController.JACKSON_MAPPER.writeValueAsString(responseObject);
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
	 * TODO add limit to "queries" ?
	 * 
	 * @param query
	 * @param queries
	 * @param version
	 * @param request
	 * @param response status variable could be modified by this function in case of error
	 * @return
	 */
	@RequestMapping(value="/refine/{version}/reconcile",method={RequestMethod.GET,RequestMethod.POST}, params="!callback")
	public @ResponseBody Object handleReconciliation(@RequestParam(required=false) String query, @RequestParam(required=false) String queries, @PathVariable String version,
			HttpServletRequest request, HttpServletResponse response){
		
		Object responseObject;
		
		if(!RECONCILIATION_API_VERSION.equals(version)){
			return APIController.notFound(response);
		}
		
		// determine if we have a single or multiple query mode
		if(StringUtils.isNotBlank(query)){
			try{
				responseObject = runSingleQueryReconciliation(query);
				LOGGER.info("SingleQueryReconciliation|{}|{}|{}|{}", request.getMethod(), request.getRequestURI(), request.getQueryString(), request.getRemoteAddr());
			}
			catch(IllegalArgumentException iaEx){
				responseObject = APIController.badRequest(response);
			}		
		} //Multiple query mode
		else if(StringUtils.isNotBlank(queries)){
			try{
				responseObject = runMultipleQueryReconciliation(queries);
				LOGGER.info("MultipleQueryReconciliation|{}|{}|{}|{}", request.getMethod(), request.getRequestURI(), request.getQueryString(), request.getRemoteAddr());
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
		reconciliationResponse.addAll(apiRefineService.reconcile(searchString));
		
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
				reconciliationResponse.addAll(apiRefineService.reconcile(queryData.get(q).get(JSON_QUERY_PARAMETER)));
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
	
}
