package net.canadensys.dataportal.vascan.model.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.canadensys.dataportal.vascan.model.TaxonLookupModel;
import net.canadensys.dataportal.vascan.taxonomy.TaxonRankEnum;

/**
 * Classifications is retrieved by nested sets and nested sets are working with the concept of depth.
 * Taxonomic ranks can not be mapped to specific depth since some rank may not be defined or skipped.
 * This class will apply alphabetical sorting at rank level as well as rank correction when a rank is skipped.
 * 
 * Rank correction means moving the taxon right under its parent and sort direct child taxon by inverted rank order and 
 * alphabetically at the same rank level.
 * 
 * This model should only be used for display purposed.
 * 
 * @author cgendreau
 *
 */
public class ClassificationOrderingViewModel {
	
	private static final Comparator<TaxonRankEnum> TAXON_RANK_COMPARATOR = Collections.reverseOrder(new TaxonRankEnum.TaxonRankEnumComparator());
	
	private TaxonSortedClassificationInternalModel rootClassificationInternalModel;
	private TaxonLookupModel rootLookupModel;
	private Map<Integer,TaxonSortedClassificationInternalModel> parentIdClassificationInternalModelMap;
	
	public ClassificationOrderingViewModel(TaxonLookupModel root){
		parentIdClassificationInternalModelMap = new HashMap<Integer, TaxonSortedClassificationInternalModel>();
		
		this.rootClassificationInternalModel = new TaxonSortedClassificationInternalModel();
		this.rootLookupModel = root;
		this.parentIdClassificationInternalModelMap.put(root.getTaxonId(), rootClassificationInternalModel);
	}
	
	/**
	 * Attach a TaxonLookupModel to the current classification. The parent of the provided TaxonLookupModel
	 * must already be attached.
	 * This shall be called in the same order the nested sets was retrieved.
	 * @param lookupModel
	 * @return successfully attached to the current classification.
	 */
	public boolean attach(TaxonLookupModel lookupModel){
		boolean isAttached = false;
		TaxonSortedClassificationInternalModel parentNode = parentIdClassificationInternalModelMap.get(lookupModel.getParentID());
		if(parentNode != null){
			isAttached = parentNode.attach(lookupModel);
			//keep this taxonid just in case it is the parent of another node.
			parentIdClassificationInternalModelMap.put(lookupModel.getTaxonId(), new TaxonSortedClassificationInternalModel());
		}
		return isAttached;
	}
	
	/**
	 * Get the ordered list after ordering.
	 * @return
	 */
	public List<TaxonLookupModel> toOrderedList(){
		List<TaxonLookupModel> outputList = new ArrayList<TaxonLookupModel>();
		outputList.add(rootLookupModel);
		innerListBuilder(rootClassificationInternalModel,outputList);
		return outputList;
	}
	
	/**
	 * Add the children of current ClassificationInternalModel, recursively to the outputList.
	 * @param current
	 * @param outputList
	 */
	private void innerListBuilder(TaxonSortedClassificationInternalModel current, List<TaxonLookupModel> outputList){
		if(!current.childClassificationPerRank.isEmpty()){
			for(TreeMap<String,TaxonLookupModel> childClassificationPerRank : current.childClassificationPerRank.values()){
				for(TaxonLookupModel currLookup : childClassificationPerRank.values()){
					outputList.add(currLookup);
					if(!parentIdClassificationInternalModelMap.get(currLookup.getTaxonId()).isLeaf()){
						innerListBuilder(parentIdClassificationInternalModelMap.get(currLookup.getTaxonId()),outputList);
					}
				}
			}
		}
	}
	
	/**
	 * Allow to keep classification organized per rank and sorted alphabetically for a taxon.
	 * @author cgendreau
	 *
	 */
	private static class TaxonSortedClassificationInternalModel{
		private TreeMap<TaxonRankEnum,TreeMap<String,TaxonLookupModel>> childClassificationPerRank;
		
		TaxonSortedClassificationInternalModel(){
			childClassificationPerRank = new TreeMap<TaxonRankEnum, TreeMap<String,TaxonLookupModel>>(TAXON_RANK_COMPARATOR);
		}
		
		public boolean isLeaf(){
			return childClassificationPerRank.isEmpty();
		}
		
		public boolean attach(TaxonLookupModel lookupModel){
			TaxonRankEnum rank = TaxonRankEnum.fromLabel(lookupModel.getRank());
			
			if(rank == null){
				return false;
			}
			
			if(childClassificationPerRank.get(rank) == null){
				childClassificationPerRank.put(rank, new TreeMap<String,TaxonLookupModel>());
			}
			TreeMap<String,TaxonLookupModel> childClassification = childClassificationPerRank.get(rank);
			childClassification.put(lookupModel.getCalname(), lookupModel);
			return true;
		}
	}

}
