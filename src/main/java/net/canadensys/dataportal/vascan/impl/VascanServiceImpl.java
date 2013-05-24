package net.canadensys.dataportal.vascan.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import net.canadensys.dataportal.vascan.VascanService;
import net.canadensys.dataportal.vascan.constant.Distribution;
import net.canadensys.dataportal.vascan.constant.Region;
import net.canadensys.dataportal.vascan.constant.Status;
import net.canadensys.dataportal.vascan.dao.DistributionDAO;
import net.canadensys.dataportal.vascan.dao.RegionDAO;
import net.canadensys.dataportal.vascan.dao.TaxonDAO;
import net.canadensys.dataportal.vascan.dao.TaxonomyDAO;
import net.canadensys.dataportal.vascan.dao.VernacularNameDAO;
import net.canadensys.dataportal.vascan.manager.IsSynonymPredicate;
import net.canadensys.dataportal.vascan.manager.TaxonManager;
import net.canadensys.dataportal.vascan.model.DistributionModel;
import net.canadensys.dataportal.vascan.model.DistributionStatusModel;
import net.canadensys.dataportal.vascan.model.RegionModel;
import net.canadensys.dataportal.vascan.model.TaxonLookupModel;
import net.canadensys.dataportal.vascan.model.TaxonModel;
import net.canadensys.dataportal.vascan.model.VernacularNameModel;

import org.apache.commons.collections.iterators.FilterIterator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The service layer provides a unified access to controller(s).
 * This layer is responsible for transaction managements and to find the appropriate DAO.
 * @author canadensys
 *
 */
@Service("vascanService")
public class VascanServiceImpl implements VascanService {

	//get log4j handler
	private static final Logger LOGGER = Logger.getLogger(VascanServiceImpl.class);
		
	@Autowired
	private VernacularNameDAO vernacularNameDAO;
	
	@Autowired
	private TaxonDAO taxonDAO;
	
	@Autowired
	private TaxonomyDAO taxonomyDAO;
	
	private TaxonManager taxonManager= new TaxonManager();
	private PropertyMapHelper propertyMapHelper = new PropertyMapHelper();
	
	@Transactional(readOnly=true)
	@Override
	public Map<String,Object> retrieveTaxonData(Integer taxonId){

	    // data hashmap that will be passed on to root document for display in .ftl
	    Map<String,Object> data = new HashMap<String,Object>();
		 	    
	    // instantiate taxon via hibernate session ; ObjectNotFoundException
	    // will pick up the exception in case id is still 0 or if supplied id
	    // is invalid
	    TaxonModel taxon = loadTaxonModel(taxonId);
	    if(taxon != null){
	        data.put("taxonId",taxon.getId());
	    }
	    
		// is the taxon concept a synonym
		boolean isSynonymConcept = false;
		

		propertyMapHelper.fillHybridParents(taxon.getHybridparents(),data);
	    
	    // parents ; it is accepted that a taxon has only one parent, but this
	    // is not a rule and some exceptions may occur with synonyms. To prevent
	    // errors, the getParent() method of a taxon returns a list of most of 
	    // the time one element...
	    List<Map<String,Object>> taxonParents = new ArrayList<Map<String,Object>>();
	    List<TaxonModel> parents = taxon.getParents();
	    if(parents != null){
	        for(TaxonModel parent : parents){
	            Map<String,Object> parentInfo = new HashMap<String,Object>();
	            try{
	            	parentInfo.put("taxonId",parent.getId());
	                parentInfo.put("fullScientificName",parent.getLookup().getCalnamehtmlauthor());
	                parentInfo.put("rank",parent.getRank().getRank());
	                parentInfo.put("reference",parent.getReference().getReference());
	                parentInfo.put("referenceShort",parent.getReference().getReferenceshort());
	                parentInfo.put("link",parent.getReference().getUrl());
	                taxonParents.add(parentInfo);
	            }
	            catch(NullPointerException e){
	                //drop on error
	            }
	        }
	    }
	    data.put("parents",taxonParents);
	    		
		propertyMapHelper.fillTaxonClassification(taxonManager.getClassification(taxon), data);
		
		propertyMapHelper.fillVernacularNames(taxon.getVernacularnames(), data);

		propertyMapHelper.fillSynonyms(taxon.getChildren(), data);

	    
	    // habitus (using the calculated habit from lookup)
	    Vector<HashMap<String,String>> taxonHabituses = new Vector<HashMap<String,String>>();
	    if(taxon.getLookup().getCalhabit() != null){
		    String[] unsplitedHabitusData = taxon.getLookup().getCalhabit().split(",");
		    if(unsplitedHabitusData != null){
				for(String habit : unsplitedHabitusData){
					HashMap<String,String> habitusData = new HashMap<String,String>();
					habitusData.put("habitus",habit);
					taxonHabituses.add(habitusData);
				}
		    }
	    }
	    data.put("habituses",taxonHabituses);

	    // base information for taxon ;
	    // the full scientific name, generated with html and author name
	    String fullScientificName = taxon.getLookup().getCalnamehtmlauthor();
	    data.put("fullScientificName",fullScientificName);

	    // the full scientific name, stripped of html
	    String pageTitle = taxon.getLookup().getCalnameauthor();
	    data.put("pageTitle",pageTitle);
	    
	    // status 
	    String status = "";
	    if(taxon.getStatus() != null && taxon.getStatus().getStatus() != null){
	    	status = taxon.getStatus().getStatus();
	    	if(taxon.getStatus().getId() == Status.SYNONYM)
	    		isSynonymConcept = true;
	    }
	    data.put("status",status);
	    
	    // rank
	    String rank = taxon.getRank().getRank();
	    data.put("rank",rank);
	    
	    // rankid (for indentation purposes in classification tree display)
	    int rankid = taxon.getRank().getId();
	    data.put("rankId",rankid);
	    
	    // reference code
	    String reference = taxon.getReference().getReference();
	    data.put("reference",reference);
	    
	    String referenceShort = taxon.getReference().getReferenceshort();
	    data.put("referenceShort",referenceShort);
	    
	    // reference link
	    String link = taxon.getReference().getUrl();
	    data.put("link",link);
	    
//	    // is taxon is an accepted concept, verify if it's distribution image exists
//	    // if not, create the svg & png file. Never create a distribution image file
//	    // for a synonym
	    String png = "";
	    String svg = "";
	    if(!isSynonymConcept){
	        //TODO: verify image, generate it if necessary
	        //write SVG file
	        //TaxonManager.writeDistributionSvg(taxon);

	        svg = "";//ApplicationConfig.getGeneratedImageURL() + ApplicationConfig.SVG_FILE_PREFIX + taxon.getId() + ApplicationConfig.SVG_FILE_EXT;
	        png = "";//ApplicationConfig.getGeneratedImageURL() + ApplicationConfig.SVG_FILE_PREFIX + taxon.getId() + ApplicationConfig.PNG_FILE_EXT;
	    }
//	    
	    data.put("png",png);
	    data.put("svg",svg);
	    
	    //Those URL will allow to open the 'Save as' dialog automaticly (client side)
//	    data.put("pngDownload",png+ApplicationConfig.DOWNLOAD_GENERATED_IMG_URL);
//	    data.put("svgDownload",svg+ApplicationConfig.DOWNLOAD_GENERATED_IMG_URL);
	    
	    data.put("pngDownload","");
	    data.put("svgDownload","");

	    // boolean synonym concept
	    data.put("isSynonymConcept",isSynonymConcept);
	    
	    return data;
	}
	
	@Transactional(readOnly=true)
	@Override
	public VernacularNameModel loadVernacularNameModel(Integer vernacularNameId) {
		return vernacularNameDAO.loadVernacularName(vernacularNameId);
	}
	
	@Transactional(readOnly=true)
	@Override
	public TaxonModel loadTaxonModel(Integer id){
		return taxonDAO.loadTaxon(id);
	}
	
	
	
//	/**
//	 * Not used by the service layer, this is only used to create the lookup value.
//	 */
//	private List<Map<String,String>> getComputedDistribution(Integer taxonId){
//		
//		//allRegion and distributionIdStatusMap should be cache
//		//get all region
//		List<RegionModel> allRegion = regionDAO.loadAllRegion();
//		//get all distributionstatus
//		List<DistributionStatusModel> allDistributionStatus = distributionDAO.loadAllDistributionStatus();
//		Map<Integer,String> distributionIdStatusMap = new HashMap<Integer, String>();
//		for(DistributionStatusModel currDistributionStatus : allDistributionStatus){
//			distributionIdStatusMap.put(currDistributionStatus.getId(), currDistributionStatus.getDistributionstatus());
//		}
//		
//		//get computed distribution per region
//		Map<Integer,Integer> distributionMap = new HashMap<Integer,Integer>();
//		for(int j = 1; j <= 16; j++){
//			distributionMap.put(j,0);
//		}
//		internalGetDistribution(taxonId,distributionMap);
//		
//		List<Map<String,String>> taxonDistributions = new ArrayList<Map<String,String>>();
//		Map<String,String> distributionData = new HashMap<String,String>();
//		
//		for(RegionModel currRegion : allRegion){
//			if(distributionMap.get(currRegion.getId()) != null && !distributionMap.get(currRegion.getId()).equals(Distribution.ABSENT)){
//		        distributionData = new HashMap<String,String>();
//		        distributionData.put("province",currRegion.getRegion());
//		        distributionData.put("exluded","");
//		        distributionData.put("status",distributionIdStatusMap.get(distributionMap.get(currRegion.getId())));
//		        distributionData.put("reference","");
//		        distributionData.put("referenceShort","");
//		        distributionData.put("link","");
//		        taxonDistributions.add(distributionData);
//			}
//		}
//		return taxonDistributions;
//	}
//	
//	/**
//	 * Recursive internal call.
//	 * @param taxonId
//	 * @param distributionMap
//	 */
//	private void internalGetDistribution(Integer taxonId, Map<Integer,Integer> distributionMap){
//		//load the dirctibution data for this taxon
//		List<DistributionModel> distModelList = distributionDAO.loadTaxonDistribution(taxonId);
//		//compute it with the current data in the distributionMap
//		taxonManager.getComputedDistribution(distributionMap, distModelList);
//		
//		//go to next children
//		Set<Integer> childIdSet = taxonomyDAO.getChildrenIdSet(taxonId, false);
//		for(Integer childId : childIdSet){
//			internalGetDistribution(childId,distributionMap);
//		}
//	}
}
