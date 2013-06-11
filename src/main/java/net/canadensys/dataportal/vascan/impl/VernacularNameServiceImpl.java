package net.canadensys.dataportal.vascan.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.canadensys.dataportal.vascan.VernacularNameService;
import net.canadensys.dataportal.vascan.dao.VernacularNameDAO;
import net.canadensys.dataportal.vascan.model.TaxonModel;
import net.canadensys.dataportal.vascan.model.VernacularNameModel;

@Service("vernacularNameService")
public class VernacularNameServiceImpl implements VernacularNameService {
	
	@Autowired
	private VernacularNameDAO vernacularNameDAO;

	@Transactional(readOnly=true)
	@Override
	public Map<String,Object> loadVernacularNameModel(Integer vernacularNameId) {
		VernacularNameModel vernacularName = vernacularNameDAO.loadVernacularName(vernacularNameId);
		if(vernacularName == null){
			return null;
		}
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
	    return data;
	}

}
