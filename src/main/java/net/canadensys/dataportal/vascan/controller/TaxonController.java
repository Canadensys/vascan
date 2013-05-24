package net.canadensys.dataportal.vascan.controller;

import java.util.HashMap;
import java.util.Map;

import net.canadensys.dataportal.vascan.DistributionService;
import net.canadensys.dataportal.vascan.NameService;
import net.canadensys.dataportal.vascan.VascanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TaxonController {
	
	@Autowired
	private VascanService vascanService;
	
	@Autowired
	private NameService nameService;
	
	@RequestMapping(value={"/taxon/{taxonId}"}, method={RequestMethod.GET})
	public ModelAndView handleTaxon(@PathVariable Integer taxonId){

	    
	    Map<String,Object> model = new HashMap<String,Object>();
	    // attach data to document root
	    model.put("data",vascanService.retrieveTaxonData(taxonId));
	    
	    // add extra data to page global hashmap
//	    _page.put("rank",LabelMappings.getRankLabel(rankid));
//	    _page.put("isSynonym",isSynonymConcept);
	    
		return new ModelAndView("taxon", model);
	}
	
	@RequestMapping(value={"/name/{name}"}, method={RequestMethod.GET})
	public ModelAndView handleName(@PathVariable String name, @RequestParam(required=false) String redirect){

	    Map<String,Object> model = new HashMap<String,Object>();
	    // attach data to document root
	    model.put("data",nameService.retrieveNameData(name, redirect));
	    
	    // add extra data to page global hashmap
//	    _page.put("rank",LabelMappings.getRankLabel(rankid));
//	    _page.put("isSynonym",isSynonymConcept);
	    
		return new ModelAndView("name", model);
	}

}
