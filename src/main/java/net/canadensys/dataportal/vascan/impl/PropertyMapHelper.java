package net.canadensys.dataportal.vascan.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.canadensys.dataportal.vascan.DistributionService;
import net.canadensys.dataportal.vascan.constant.Rank;
import net.canadensys.dataportal.vascan.manager.IsSynonymPredicate;
import net.canadensys.dataportal.vascan.model.DistributionModel;
import net.canadensys.dataportal.vascan.model.TaxonLookupModel;
import net.canadensys.dataportal.vascan.model.TaxonModel;
import net.canadensys.dataportal.vascan.model.VernacularNameModel;
import net.canadensys.dataportal.vascan.model.view.ClassificationOrderingViewModel;
import net.canadensys.dataportal.vascan.taxonomy.TaxonRankEnum;

import org.apache.commons.collections.iterators.FilterIterator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The PropertyMapHelper is used to transform a List of models into a property Map.
 * @author canadensys
 *
 */
@Component
public class PropertyMapHelper {
	
	private static final Logger LOGGER = Logger.getLogger(PropertyMapHelper.class);
	
	@Autowired
	private DistributionService distributionService;
	
	/**
	 * Fill hybrid data (if any) in the provided model.
	 * 
	 * @param hybridParents
	 * @param hybridChildren
	 * @param data
	 */
	public void fillHybridData(List<TaxonModel> hybridParents, List<TaxonModel> hybridChildren, Map<String,Object> data){

		// To know if a taxon is a hybrid, we need to inspect the taxon
		// full scientific name for the presence of "x" (multiply sign);
		// The hybrid status cannot be determined by the presence of hybrid parents because
		// they are sometimes unknown.
		boolean isHybridConcept = false;
	    List<Map<String,Object>> taxonHybridParents = new ArrayList<Map<String,Object>>();
	    if(hybridParents != null){
	    	if(hybridParents.size() > 0){
	    	    isHybridConcept = true;
	    	}
	    	fillHybridRelatedData(hybridParents, taxonHybridParents);
	    }
	    
	    if(hybridChildren !=null && !hybridChildren.isEmpty()){
	    	List<Map<String,Object>> taxonHybridChildren = new ArrayList<Map<String,Object>>();
	    	fillHybridRelatedData(hybridChildren, taxonHybridChildren);
	    	data.put("hybridChildren", taxonHybridChildren);
	    }
	    data.put("isHybridConcept",isHybridConcept);
	    data.put("hybridParents",taxonHybridParents);
	}
	
	/**
	 * Add hybrid parent or children related data.
	 * @param taxonList
	 * @param hybridData
	 */
	private void fillHybridRelatedData(List<TaxonModel> taxonList, List<Map<String,Object>> hybridData){
		Map<String,Object> hybridInfo;
		for(TaxonModel hybridParent : taxonList){
            hybridInfo = new HashMap<String,Object>();
            hybridInfo.put("taxonId", hybridParent.getId());
            hybridInfo.put("fullScientificName", hybridParent.getLookup().getCalnamehtmlauthor());
            hybridInfo.put("status", hybridParent.getLookup().getStatus());
            hybridData.add(hybridInfo);
        }
	}
	
	/**
	 * Fill classification in the data object and apply alphabetical sorting at rank level as well 
	 * as rank correction when a rank is skipped @see {@link ClassificationOrderingViewModel}.
	 * @param classification
	 * @param data
	 */
	public void fillTaxonClassification(List<TaxonLookupModel> classification, Map<String,Object> data){  

		List<Map<String,Object>> taxonClassification = new ArrayList<Map<String,Object>>();		
	    if(classification != null){
	    	//reorder classification
	    	ClassificationOrderingViewModel covm = null;
	    	for(TaxonLookupModel node : classification){
	    		if(covm == null){
	    			covm = new ClassificationOrderingViewModel(node);
	    		}
	    		else{
	    			if(!covm.attach(node)){
	    				LOGGER.fatal("Can NOT attached node with taxonId:" + node.getTaxonId());
	    			}
	    		}
	    	}

	    	classification = covm.toOrderedList();
	    	Map<String,Object> nodeInfo;
	        for(TaxonLookupModel node : classification){
	            nodeInfo = new HashMap<String,Object>();
            	nodeInfo.put("taxonId",node.getTaxonId());
                nodeInfo.put("fullScientificName",node.getCalnamehtml());
                nodeInfo.put("fullScientificNameUrl",node.getCalname());
                nodeInfo.put("rank",node.getRank());
                nodeInfo.put("rankId",Rank.getIdFromLabel(node.getRank()));
                taxonClassification.add(nodeInfo);
	        }
	    }
		data.put("tree",taxonClassification);
	}
	
	public void fillVernacularNames(Set<VernacularNameModel> vernacularNames, Map<String,Object> data){ 	
	   	List<HashMap<String,Object>> taxonVernacularNames = new ArrayList<HashMap<String,Object>>();
	    if(vernacularNames != null){
	        for(VernacularNameModel vernacularName : vernacularNames){
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
	    }
	    data.put("vernacularNames",taxonVernacularNames);
	}
	
	public void fillSynonyms(Set<TaxonModel> allChildren, Map<String,Object> data){
	    // Use a TreeMap so synonyms will be alphabetically sorted
	    Map<String,Map<String,Object>> taxonSynonymsMap = new TreeMap<String, Map<String,Object>>();
	    FilterIterator filterIterator = new FilterIterator(allChildren.iterator(),new IsSynonymPredicate());
	    if(filterIterator != null){
	    	while(filterIterator.hasNext()){
	            Map<String,Object> synonymData = new HashMap<String,Object>();
	            TaxonModel synonym = (TaxonModel) filterIterator.next();
	            synonymData.put("taxonId",synonym.getId());
	            synonymData.put("fullScientificName",synonym.getLookup().getCalnamehtmlauthor());
	            synonymData.put("fullScientificNameUrl",synonym.getLookup().getCalname());
                synonymData.put("reference",synonym.getReference().getReference());
                synonymData.put("referenceShort",synonym.getReference().getReferenceshort());
                synonymData.put("link",synonym.getReference().getUrl());
	            taxonSynonymsMap.put(synonym.getLookup().getCalnamehtmlauthor(), synonymData);
	    	}
	    }
	    data.put("synonyms",taxonSynonymsMap.values());
	}
	
	public void fillTaxonDistribution(Set<DistributionModel> distributions, TaxonModel taxon, Map<String,Object> data){
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

	/**
	 * Returns the range of rank label we want to return for a specific rank.
	 * This is used to avoid returning the whole hierarchy for a taxon.
	 * @param taxonRank
	 * @return range of rank label or null if nothing was found(e.g. variety)
	 */
	public static String[] getRankLabelRange(TaxonRankEnum taxonRank){
		
		TaxonRankEnum[] rankRange = getRankRangeForDisplay(taxonRank);
		if(rankRange == null){
			return null;
		}
		
		String[] rankRangeLabels = new String[rankRange.length];
		for(int i=0; i<rankRange.length;i++){
			rankRangeLabels[i]=rankRange[i].getLabel();
		}
		return rankRangeLabels;
	}
	
	/**
	 * Returns the range of TaxonRankEnum we want to display under a specific rank.
	 * This is used to avoid returning the whole hierarchy for a taxon.
	 * @param taxonRank
	 * @return range of values or null if nothing was found(e.g. variety)
	 */
	public static TaxonRankEnum[] getRankRangeForDisplay(TaxonRankEnum taxonRank){
		switch(taxonRank){
			case CLASS :
			case SUBCLASS :
			case SUPERORDER : return new TaxonRankEnum[]{TaxonRankEnum.SUBCLASS, TaxonRankEnum.SUPERORDER, TaxonRankEnum.ORDER};
			
			case ORDER : return new TaxonRankEnum[]{TaxonRankEnum.FAMILY};
			
			case FAMILY :
			case SUBFAMILY:
			case TRIBE:
				
			case SUBTRIBE: return new TaxonRankEnum[]{TaxonRankEnum.SUBFAMILY,TaxonRankEnum.TRIBE,TaxonRankEnum.SUBTRIBE,TaxonRankEnum.GENUS};
			case GENUS : 
			case SUBGENUS:
			case SECTION:
			case SUBSECTION:
			case SERIES : return new TaxonRankEnum[]{TaxonRankEnum.SUBGENUS,TaxonRankEnum.SECTION,TaxonRankEnum.SUBSECTION,TaxonRankEnum.SERIES,TaxonRankEnum.SPECIES};
				
			case SPECIES:
			case SUBSPECIES:return new TaxonRankEnum[]{TaxonRankEnum.SUBSPECIES,TaxonRankEnum.VARIETY};
			
			default: return null;
		}
	}

}
