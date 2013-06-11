package net.canadensys.dataportal.vascan.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.canadensys.dataportal.vascan.ChecklistService;
import net.canadensys.dataportal.vascan.NameService;
import net.canadensys.dataportal.vascan.SearchService;
import net.canadensys.dataportal.vascan.TaxonService;
import net.canadensys.dataportal.vascan.VernacularNameService;
import net.canadensys.dataportal.vascan.model.NameConceptModelIF;
import net.canadensys.dataportal.vascan.model.NameConceptTaxonModel;
import net.canadensys.dataportal.vascan.model.NameConceptVernacularNameModel;
import net.canadensys.exception.web.ResourceNotFoundException;
import net.canadensys.query.LimitedResult;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Vascan controller.
 * 
 * @author canadensys
 *
 */
@Controller
public class VascanController {
	
	public static final String JSON_CONTENT_TYPE = "application/json";
	
	@Autowired
	private TaxonService taxonService;
	
	@Autowired
	private VernacularNameService vernacularNameService;
	
	@Autowired
	private NameService nameService;
	
	@Autowired
	private ChecklistService checklistService;
	
	@Autowired
	private SearchService searchService;
	
	/**
	 * Render taxon view for a given taxonId
	 * @param taxonId
	 * @return
	 */
	@RequestMapping(value={"/taxon/{taxonId}"}, method={RequestMethod.GET})
	public ModelAndView handleTaxon(@PathVariable Integer taxonId){

	    Map<String,Object> model = new HashMap<String,Object>();
	    Object data = taxonService.retrieveTaxonData(taxonId);
	    if(data == null){
	    	throw new ResourceNotFoundException();
	    }
	    model.put("data",data);
	    
		return new ModelAndView("taxon", model);
	}
	
	/**
	 * Render a vernacular name view for a given vernacularNameId
	 * @param id
	 * @return
	 */
	@RequestMapping(value={"/vernacular/{vernacularNameId}"}, method={RequestMethod.GET})
	public ModelAndView handleVernacular(@PathVariable Integer vernacularNameId){
		
		Map<String,Object> model = new HashMap<String,Object>();
		Object vernacularNameData = vernacularNameService.loadVernacularNameModel(vernacularNameId);
		
	    if(vernacularNameData == null){
	    	throw new ResourceNotFoundException();
	    }
	    model.put("vernacularName",vernacularNameData);
	    Map<String,Object> extra = new HashMap<String,Object>();
	    extra.put("isVernacular",true);
	    model.put("extra",extra);
	    return new ModelAndView("vernacular",model);
	}
	
	/**
	 * Render a name view for a given name string
	 * @param name
	 * @param redirect
	 * @return
	 */
	@RequestMapping(value={"/name/{name}"}, method={RequestMethod.GET})
	public ModelAndView handleName(@PathVariable String name, @RequestParam(required=false) String redirect){
		
	    Map<String,Object> model = new HashMap<String,Object>();
	    Map<String,Object> extra = new HashMap<String,Object>();

	    Object data = nameService.retrieveNameData(name, redirect,extra);
	    if( data == null){
	    	throw new ResourceNotFoundException();
	    }
	    model.put("data",data); 
	    model.put("extra",extra);
	    
		return new ModelAndView("name", model);
	}
	
	/**
	 * Render a checklist view for the given query parameters
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/checklist"}, method={RequestMethod.GET})
	public ModelAndView handleChecklist(HttpServletRequest request){
		
		Map<String,Object> model = new HashMap<String,Object>();
		request.getParameterMap();
		
		model.put("data", checklistService.retrieveChecklistData(request.getParameterMap()));
		model.put("pageQuery",StringUtils.defaultString(request.getQueryString()));
		
		return new ModelAndView("checklist", model);
	}
	
	/**
	 * Render a search page
	 * @param q name to search for
	 * @param page page index, starting at 0
	 * @return
	 */
	@RequestMapping(value={"/search"}, method={RequestMethod.GET})
	public ModelAndView handleSearch(@RequestParam(required=false) String q, @RequestParam(required=false) Integer page){
		Map<String,Object> model = new HashMap<String,Object>();
		
	    HashMap<String,Object> search = new HashMap<String,Object>();
	    search.put("term", "");
	    search.put("total",0);

	    List<Map<String,String>> searchResult = new ArrayList<Map<String,String>>();
	    if(StringUtils.isNotBlank(q)){
	    	search.put("term", q);
	    	LimitedResult<List<NameConceptModelIF>> nameConceptModelList = null;
	    	if(page != null){
	    		search.put("pageIndex", page);
	    		search.put("pageSize", searchService.getPageSize());
	    		nameConceptModelList = searchService.searchName(q,page);
	    	}
	    	else{
	    		nameConceptModelList = searchService.searchName(q);
	    	}

		    search.put("total",nameConceptModelList.getTotal_rows());
		    List<Map<String,String>> searchResults = new ArrayList<Map<String,String>>();
		    Map<String,String> searchRow = null;
		    for(NameConceptModelIF currNameConceptModel : nameConceptModelList.getRows()){
		    	if(currNameConceptModel.getClass().equals(NameConceptTaxonModel.class)){
		    		searchRow = new HashMap<String,String>();
		    		searchRow.put("type","taxon");
		    		searchRow.put("name", currNameConceptModel.getName());
		    		searchRow.put("id", Integer.toString(currNameConceptModel.getTaxonId()));
		    		searchRow.put("status", currNameConceptModel.getStatus());
		    		searchRow.put("namehtml",((NameConceptTaxonModel)currNameConceptModel).getNamehtml());
		    		searchRow.put("namehtmlauthor",((NameConceptTaxonModel)currNameConceptModel).getNamehtmlauthor());
		    		searchResult.add(searchRow);
		    	}
		    	else if(currNameConceptModel.getClass().equals(NameConceptVernacularNameModel.class)){
		    		searchRow = new HashMap<String, String>();
		    		searchRow.put("type","vernacular");
		    		searchRow.put("name", currNameConceptModel.getName());
		    		searchRow.put("id", Integer.toString(((NameConceptVernacularNameModel)currNameConceptModel).getId()));
		    		searchRow.put("status", currNameConceptModel.getStatus());
		    		searchRow.put("lang",((NameConceptVernacularNameModel)currNameConceptModel).getLang());
		    		searchResult.add(searchRow);
		    	}
		    	else{
		    		//logger
		    		searchRow = null;
		    	}
		    	searchResults.add(searchRow);
		    }
		    model.put("results",searchResults);
	    }
	    
	    model.put("search",search);
	    return new ModelAndView("search", model);
	}
	
	@RequestMapping(value={"/search.json"}, method={RequestMethod.GET})
	public void handleSearchJson(@RequestParam String q,@RequestParam String t, HttpServletResponse response){
		
		//make sure the answer is set as UTF-8
		response.setCharacterEncoding("UTF-8");
		response.setContentType(JSON_CONTENT_TYPE);
		
		List<NameConceptModelIF> nameConceptModelList = null;
		if("taxon".equalsIgnoreCase(t)){
			nameConceptModelList = searchService.searchTaxon(q);
		}
		else if("vernacular".equalsIgnoreCase(t)){
			nameConceptModelList = searchService.searchVernacularName(q);
		}
		
		JSONArray responseJson = new JSONArray();
		for(NameConceptModelIF currNameConcept : nameConceptModelList){
			responseJson.put(currNameConcept.getName());
		}
		try {
			response.getWriter().print(responseJson.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
