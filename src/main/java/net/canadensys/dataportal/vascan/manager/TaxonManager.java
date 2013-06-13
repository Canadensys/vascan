package net.canadensys.dataportal.vascan.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.canadensys.dataportal.vascan.constant.Rank;
import net.canadensys.dataportal.vascan.constant.Status;
import net.canadensys.dataportal.vascan.model.DistributionModel;
import net.canadensys.dataportal.vascan.model.TaxonLookupModel;
import net.canadensys.dataportal.vascan.model.TaxonModel;

/**
 * The TaxonManager understands the taxon concept.
 * 
 * @author canadensys
 *
 */
public class TaxonManager {
	
	/**
	 * Get the higher and lower classifications for a taxon.
	 * @param taxon
	 * @return
	 */
	public List<TaxonModel> getClassification(TaxonModel taxon){
		int lowerRank = 0;
		int rank = taxon.getRank().getId();
		if(rank >= Rank.CLASS && rank < Rank.ORDER){
			lowerRank = Rank.ORDER;
		}
		else if(rank >= Rank.ORDER && rank < Rank.FAMILY){
			lowerRank = Rank.FAMILY;
		}
		else if(rank >= Rank.FAMILY && rank < Rank.GENUS){
			lowerRank = Rank.GENUS;
		}
		else if(rank >= Rank.GENUS && rank < Rank.SPECIES){
			lowerRank = Rank.SPECIES;
		}
		else if(rank >= Rank.SPECIES && rank < Rank.VARIETY)
			lowerRank = Rank.VARIETY;
		
		List<TaxonModel> classification = new ArrayList<TaxonModel>();
		recursiveClassificationParent(taxon,classification);
		classification.add(taxon);
		if(lowerRank != 0)
			recursiveClassificationChild(taxon,classification,lowerRank);
		return classification;
	}
	
	/**
	 * Get all accepted parent taxa. 
	 * @param currTaxon
	 * @param parentList
	 * @return
	 */
	public List<TaxonModel> recursiveClassificationParent(TaxonModel currTaxon, List<TaxonModel> parentList){
		List<TaxonModel> parents = currTaxon.getParents();
		for(TaxonModel parent : parents){
			if(parent.getStatus().getId() == Status.ACCEPTED){
				parentList.add(0,parent);
				recursiveClassificationParent(parent,parentList);
			}
		}
		return parentList;
	}
	
	/**
	 * Get all accepted parent taxa. 
	 * @param currTaxon
	 * @param parentList
	 * @return
	 */
	public List<TaxonLookupModel> getParentClassification(TaxonModel currTaxon, List<TaxonLookupModel> parentList){
		List<TaxonModel> parents = currTaxon.getParents();
		for(TaxonModel parent : parents){
			if(parent.getStatus().getId() == Status.ACCEPTED){
				parentList.add(0,parent.getLookup());
				getParentClassification(parent,parentList);
			}
		}
		return parentList;
	}
	
	/**
	 * Get all accepted children taxa. 
	 * @param currTaxon
	 * @param childrenList
	 * @param maximumRank inclusive 
	 * @return
	 */
	public List<TaxonModel> recursiveClassificationChild(TaxonModel currTaxon, List<TaxonModel> childrenList, int maximumRank){
		List<TaxonModel> children = currTaxon.getChildren();
		for(TaxonModel child : children){
			if(child.getStatus().getId() == Status.ACCEPTED && child.getRank().getId() <= maximumRank){
				childrenList.add(child);
				recursiveClassificationChild(child,childrenList,maximumRank);
			}
		}
		return childrenList;
	}
	
	/**
	 * 
	 * @param id
	 * @param computedMap
	 * @return
	 */
	public void getComputedDistribution(Map<Integer,Integer> computedMap, List<DistributionModel> distributionList){
		for(DistributionModel distribution : distributionList){
			int regionid = distribution.getRegion().getId();
			int distributionStatusid = distribution.getDistributionStatus().getId();
			if(computedMap.containsKey(regionid)){
				//TODO : do not rely on the value of the id
				if(computedMap.get(regionid) > distributionStatusid){
					computedMap.put(regionid,distributionStatusid);
				}
			}
			else{
				computedMap.put(regionid,distributionStatusid);
			}
		}
	}

}
