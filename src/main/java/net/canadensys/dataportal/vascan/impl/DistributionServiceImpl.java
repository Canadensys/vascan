package net.canadensys.dataportal.vascan.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.canadensys.dataportal.vascan.DistributionService;
import net.canadensys.dataportal.vascan.constant.Distribution;
import net.canadensys.dataportal.vascan.constant.Region;
import net.canadensys.dataportal.vascan.dao.DistributionDAO;
import net.canadensys.dataportal.vascan.dao.RegionDAO;
import net.canadensys.dataportal.vascan.model.DistributionStatusModel;
import net.canadensys.dataportal.vascan.model.RegionModel;
import net.canadensys.dataportal.vascan.model.TaxonLookupModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("distributionService")
public class DistributionServiceImpl implements DistributionService{
	
	@Autowired
	private DistributionDAO distributionDAO;
	
	@Autowired
	private RegionDAO regionDAO;
	
	/**
	 * Get the computed distribution for a TaxonLookupModel as a list. The list includes distribution information for all regions where
	 * this taxon is NOT absent.
	 * @param taxonLookupModel
	 * @return
	 */
	public List<Map<String,String>> getComputedDistribution(TaxonLookupModel taxonLookupModel){
		List<Map<String,String>> taxonDistributions = new ArrayList<Map<String,String>>();
	    HashMap<String,String> distributionData = null;
	    List<RegionModel> allRegion = regionDAO.loadAllRegion();
	    
		//get all distributionstatus
		List<DistributionStatusModel> allDistributionStatus = distributionDAO.loadAllDistributionStatus();
		Map<Integer,String> distributionIdStatusMap = new HashMap<Integer, String>();
		for(DistributionStatusModel currDistributionStatus : allDistributionStatus){
			distributionIdStatusMap.put(currDistributionStatus.getId(), currDistributionStatus.getDistributionstatus());
		}
	    
		String currDistributionStatus;
		for(RegionModel currRegion : allRegion){
			currDistributionStatus = getDistributionTaxonLookup(currRegion.getId(),taxonLookupModel);
			if(!currDistributionStatus.equals(distributionIdStatusMap.get(Distribution.ABSENT))){
				distributionData = new HashMap<String,String>();
		        distributionData.put("province",currRegion.getRegion());
		        distributionData.put("exluded","");
		        distributionData.put("status",currDistributionStatus);
		        distributionData.put("reference","");
		        distributionData.put("referenceShort","");
		        distributionData.put("link","");
		        taxonDistributions.add(distributionData);
			}
		}
	    return taxonDistributions;
	}
	
	/**
	 * We use this method for performance reason.
	 * Computing the distribution for the class Equisetopsida would not be possible for taxon query.
	 * @param regionId
	 * @param taxonLookup
	 * @return
	 */
	private String getDistributionTaxonLookup(Integer regionId, TaxonLookupModel taxonLookup){
		switch (regionId) {
		case Region.AB:
			return taxonLookup.getAB();
		case Region.BC:
			return taxonLookup.getBC();
		case Region.GL:
			return taxonLookup.getGL();
		case Region.MB:
			return taxonLookup.getMB();
		case Region.NB:
			return taxonLookup.getNB();
		case Region.NLL:
			return taxonLookup.getNL_L();
		case Region.NLN:
			return taxonLookup.getNL_N();
		case Region.NS:
			return taxonLookup.getNS();
		case Region.NT:
			return taxonLookup.getNT();
		case Region.NU:
			return taxonLookup.getNU();
		case Region.ON:
			return taxonLookup.getON();
		case Region.PE:
			return taxonLookup.getPE();
		case Region.PM:
			return taxonLookup.getPM();
		case Region.QC:
			return taxonLookup.getQC();
		case Region.SK:
			return taxonLookup.getSK();
		case Region.YT:
			return taxonLookup.getYT();
		default:
			break;
		}
		return null;
	}

}
