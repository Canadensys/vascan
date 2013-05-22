package net.canadensys.dataportal.vascan.controller;

import java.util.HashMap;
import java.util.Map;

import net.canadensys.dataportal.vascan.VascanService;
import net.canadensys.dataportal.vascan.model.TaxonModel;
import net.canadensys.dataportal.vascan.model.VernacularNameModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller used to access vernacular name related data.
 * @author canadensys
 *
 */
@Controller
public class VernacularController {
	
	@Autowired
	private VascanService vascanService;
	
	@RequestMapping(value={"/vernacular/{id}"}, method={RequestMethod.GET})
	public ModelAndView handleVernacular(@PathVariable Integer id){
		
	    VernacularNameModel vernacularName = vascanService.loadVernacularNameModel(id);
	
	    //denormalize data related to this vernacular name
	    Map<String,Object> data = new HashMap<String,Object>();
	    data.put("vernacularId",vernacularName.getId());
	    data.put("name",vernacularName.getName());
	    data.put("reference",vernacularName.getReference().getReference());
	    data.put("referenceShort",vernacularName.getReference().getReferenceshort());
	    data.put("link",vernacularName.getReference().getUrl());
	    data.put("language",vernacularName.getLanguage());
	    data.put("status",vernacularName.getStatus().getStatus());
	    
	    //denormalize data related the related taxon
	    TaxonModel relatedTaxon = vernacularName.getTaxon();
	    HashMap<String,Object> t = new HashMap<String,Object>();
	    t.put("taxonId",relatedTaxon.getId());
	    t.put("fullScientificName",relatedTaxon.getLookup().getCalnamehtmlauthor());
	    t.put("fullScientificNameUrl",relatedTaxon.getLookup().getCalname());
	    t.put("reference",relatedTaxon.getReference().getReference());
	    t.put("referenceShort",relatedTaxon.getReference().getReferenceshort());
	    t.put("link",relatedTaxon.getReference().getUrl());
	    t.put("status",relatedTaxon.getStatus().getStatus());
	    t.put("rank",relatedTaxon.getRank().getRank());
	    data.put("taxon",t);
	    
	    Map<String,Object> model = new HashMap<String,Object>();
	    model.put("vernacularName",data);
	    
	    // add extra data to page global hashmap
	    //_page.put("isVernacular",true);
	    
	    return new ModelAndView("vernacular",model);
	}

}
