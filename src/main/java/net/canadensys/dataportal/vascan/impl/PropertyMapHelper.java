package net.canadensys.dataportal.vascan.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.canadensys.dataportal.vascan.DistributionService;
import net.canadensys.dataportal.vascan.manager.IsSynonymPredicate;
import net.canadensys.dataportal.vascan.model.DistributionModel;
import net.canadensys.dataportal.vascan.model.TaxonLookupModel;
import net.canadensys.dataportal.vascan.model.TaxonModel;
import net.canadensys.dataportal.vascan.model.VernacularNameModel;

import org.apache.commons.collections.iterators.FilterIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropertyMapHelper {
	
	@Autowired
	private DistributionService distributionService;
	
	public void fillHybridParents(List<TaxonModel> hybridParents,Map<String,Object> data){
		// is he taxon concept an hybrid
		// the state of isHybridConcept is determined by inspecting the taxon
		// full scientific name for the presence of "x" (multiply sign); this
		// is acheived by the method isHybird() in Taxon; The hybrid status
		// cannot be determined by the presence of hybrid parents because
		// they are sometimes unknown (no entry in database for hybdridparent1 and
	    // hybridparent1, but we know that it's an hybrid, because someone said so.
		boolean isHybridConcept = false;
	    List<Map<String,Object>> taxonHybridParents = new ArrayList<Map<String,Object>>();
	    if(hybridParents != null){
	    	if(hybridParents.size() > 0)
	    	    isHybridConcept = true;
	        for(TaxonModel hybridParent : hybridParents){
	            Map<String,Object> hybridParentInfo = new HashMap<String,Object>();
	            try{
	            	hybridParentInfo.put("taxonId",hybridParent.getId());
	                hybridParentInfo.put("fullScientificName",hybridParent.getLookup().getCalnamehtmlauthor());
	                hybridParentInfo.put("status",hybridParent.getStatus().getStatus());
	                taxonHybridParents.add(hybridParentInfo);
	            }
	            catch(NullPointerException e){
	                //drop on error
	            }
	        }
	    }
	    data.put("isHybridConcept",isHybridConcept);
	    data.put("hybridParents",taxonHybridParents);
	    
	}
	
	public void fillTaxonClassification(List<TaxonModel> classification, Map<String,Object> data){        
		// classification for taxon ; get all the hierarchy of taxon above, and possibly
		// below of taxon, and save to hashmap and add to vector. Vector is available
		// as a sequence in .ftl
		List<Map<String,Object>> taxonClassification = new ArrayList<Map<String,Object>>();
		//List<TaxonModel> classification = taxonManager.getClassification(taxon);
	    if(classification != null){
	        for(TaxonModel node : classification){
	            Map<String,Object> nodeInfo = new HashMap<String,Object>();
	            try{
	            	nodeInfo.put("taxonId",node.getId());
	                nodeInfo.put("fullScientificName",node.getLookup().getCalnamehtml());
	                nodeInfo.put("fullScientificNameUrl",node.getLookup().getCalname());
	                nodeInfo.put("rank",node.getRank().getRank());
	                nodeInfo.put("rankId",node.getRank().getId());
	                taxonClassification.add(nodeInfo);
	            }
	            catch(NullPointerException e){
	                //drop on error
	            }
	        }
	    }
		data.put("tree",taxonClassification);
	}
	
	public void fillVernacularNames(List<VernacularNameModel> vernacularNames, Map<String,Object> data){
		// vernacular names for taxon ; each vernacular name is saved in an hashmap
	    // and all hashmap are added to a vector wich is added to the main data 
	    // hashmap. That vector is available in the ftl as a sequence   	
	   	Vector<HashMap<String,Object>> taxonVernacularNames = new Vector<HashMap<String,Object>>();
	    if(vernacularNames != null){
	        for(VernacularNameModel vernacularName : vernacularNames){
	            try{
	                HashMap<String,Object> vernacularData = new HashMap<String,Object>();
	                vernacularData.put("vernacularId",vernacularName.getId());
	                vernacularData.put("name",vernacularName.getName());
	                vernacularData.put("status",vernacularName.getStatus().getStatus());
	                vernacularData.put("language",vernacularName.getLanguage());
	                vernacularData.put("reference",vernacularName.getReference().getReference());
	                vernacularData.put("referenceShort",vernacularName.getReference().getReferenceshort());
	                vernacularData.put("link",vernacularName.getReference().getUrl());
	                taxonVernacularNames.add(vernacularData);
	            }
	            catch(NullPointerException e){
	                //drop on error
	            }
	        }
	    }
	    data.put("vernacularNames",taxonVernacularNames);
	}
	
	public void fillSynonyms(List<TaxonModel> allChildren, Map<String,Object> data){
	    // synonyms for taxon ; each synonym is saved in an hashmap and all hashmap
	    // are added to a vector wich is added to the main data hashmap. That vector
	    // is available in the ftl as a sequence. The synonyms are filtered from the
	    // children of the taxon by the isSynonymPredicate predicate
	    List<Map<String,Object>> taxonSynonyms = new ArrayList<Map<String,Object>>();
	    FilterIterator filterIterator = new FilterIterator(allChildren.iterator(),new IsSynonymPredicate());
	    if(filterIterator != null){
	    	while(filterIterator.hasNext()){
	    		try{
		            Map<String,Object> synonymData = new HashMap<String,Object>();
		            TaxonModel synonym = (TaxonModel) filterIterator.next();
		            synonymData.put("taxonId",synonym.getId());
		            synonymData.put("fullScientificName",synonym.getLookup().getCalnamehtmlauthor());
		            synonymData.put("fullScientificNameUrl",synonym.getLookup().getCalname());
	                synonymData.put("reference",synonym.getReference().getReference());
	                synonymData.put("referenceShort",synonym.getReference().getReferenceshort());
	                synonymData.put("link",synonym.getReference().getUrl());
		            taxonSynonyms.add(synonymData);
		        }
		        catch(NullPointerException e){
		            //drop on error
		        }
	    	}
	    }
	    data.put("synonyms",taxonSynonyms);
	}
	
	public void fillTaxonDistribution(List<DistributionModel> distributions, TaxonModel taxon, Map<String,Object> data){
	    // distribution
	    List<Map<String,String>> taxonDistributions = new ArrayList<Map<String,String>>();
	    if(distributions != null){
	        for(DistributionModel distribution : distributions){
	        	try{
		            Map<String,String> distributionData = new HashMap<String,String>();
		            distributionData.put("province",distribution.getRegion().getRegion());
		            distributionData.put("status",distribution.getDistributionStatus().getDistributionstatus());
		            distributionData.put("excluded",distribution.getExcludedcode().getExcludedcode());
		            distributionData.put("reference",distribution.getReference().getReference());
		            distributionData.put("referenceShort",distribution.getReference().getReferenceshort());
		            distributionData.put("link",distribution.getReference().getUrl());
		            taxonDistributions.add(distributionData);
	        	}
	        	catch(NullPointerException e){
	        		//drop on error
	        		e.printStackTrace();
	        	}
	        }
	    }
        
	    boolean computedDistribution = false;
	    // if taxon has no distribution information (distribution entity), display
	    // distribution lookup info (distributionlookup entity) and display message
	    // to user...
	    if(taxonDistributions.size() == 0){
	    	computedDistribution = true;
	        //build a distribution hashmap and add to taxonDistributions vector
	        TaxonLookupModel lookup = taxon.getLookup();
	        if(lookup != null){
	        	taxonDistributions = distributionService.getComputedDistribution(lookup);
	        }
	    }	

	    data.put("computedDistribution",computedDistribution);
	    data.put("distributions",taxonDistributions);
	}

}
