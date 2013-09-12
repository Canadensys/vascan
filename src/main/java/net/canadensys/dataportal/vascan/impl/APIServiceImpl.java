package net.canadensys.dataportal.vascan.impl;

import java.util.ArrayList;
import java.util.List;

import net.canadensys.dataportal.vascan.APIService;
import net.canadensys.dataportal.vascan.constant.Status;
import net.canadensys.dataportal.vascan.dao.NameDAO;
import net.canadensys.dataportal.vascan.dao.TaxonDAO;
import net.canadensys.dataportal.vascan.model.DistributionModel;
import net.canadensys.dataportal.vascan.model.NameConceptModelIF;
import net.canadensys.dataportal.vascan.model.TaxonLookupModel;
import net.canadensys.dataportal.vascan.model.TaxonModel;
import net.canadensys.dataportal.vascan.model.VascanAPIResponse;
import net.canadensys.dataportal.vascan.model.VernacularNameModel;
import net.canadensys.dataportal.vascan.model.api.DistributionAPIResult;
import net.canadensys.dataportal.vascan.model.api.TaxonAPIResult;
import net.canadensys.dataportal.vascan.model.api.VernacularNameAPIResult;
import net.canadensys.query.LimitedResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class APIServiceImpl implements APIService{

	@Autowired
	private NameDAO nameDAO;
	
	@Autowired
	private TaxonDAO taxonDAO;
	
	@Transactional(readOnly=true)
	@Override
	public List<VascanAPIResponse> search(String query) {
		List<VascanAPIResponse> results = new ArrayList<VascanAPIResponse>();
		
		LimitedResult<List<NameConceptModelIF>> searchResult = nameDAO.search(query);
		
		VascanAPIResponse apiResponse = new VascanAPIResponse();
		apiResponse.setNumResults(Long.valueOf(searchResult.getTotal_rows()).intValue());
		apiResponse.setSearchTerm(query);
		
		List<Integer> taxonIdList = new ArrayList<Integer>();
		for(NameConceptModelIF currName : searchResult.getRows()){
			//avoid duplicated entry (where query fits in taxon name and vernacular name of the same taxon)
			if(!taxonIdList.contains(currName)){
				taxonIdList.add(currName.getTaxonId());
			}
		}
		//build a set of id
		List<TaxonModel> taxonModelList = taxonDAO.loadTaxonList(taxonIdList);
		List<TaxonModel> parentsList;
		for(TaxonModel currTaxonModel : taxonModelList){
			TaxonLookupModel tlm = currTaxonModel.getLookup();
			
			TaxonAPIResult tar = new TaxonAPIResult();
			tar.setTaxonID(currTaxonModel.getId());
			tar.setTaxonomicStatus(tlm.getStatus());
			tar.setNameAccordingTo(currTaxonModel.getReference().getReference());
			
			parentsList = currTaxonModel.getParents();
			if(currTaxonModel.getStatus().getId() == Status.SYNONYM){
				//synonyms can have more than one parent
				
				//loop over parents
				//tar.setAcceptedNameUsage(acceptedNameUsage);
				//tar.setAcceptedNameUsageID(acceptedNameUsageID);
			}
			else{ //accepted taxon
				
				//tar.setParentNameUsageID(parentNameUsageID);
				tar.setHigherClassification(tlm.getHigherclassification());
			}
			
			tar.setCanonicalName(tlm.getCalname());
			tar.setScientificName(tlm.getCalnameauthor());
			tar.setScientificNameAuthorship(tlm.getAuthor());
			tar.setTaxonRank(tlm.getRank());

			for(VernacularNameModel currVernacular : currTaxonModel.getVernacularnames()){
				VernacularNameAPIResult vnar = new VernacularNameAPIResult();
				vnar.setVernacularName(currVernacular.getName());
				vnar.setLanguage(currVernacular.getLanguage());
				vnar.setSource(currVernacular.getReference().getReference());
				tar.addVernacularName(vnar);
			}
			
			for(DistributionModel currDistribution : currTaxonModel.getDistribution()){
				DistributionAPIResult dar = new DistributionAPIResult();
				dar.setLocality(currDistribution.getRegion().getRegion());
				dar.setLocationID(currDistribution.getRegion().getIso3166_2());
				dar.setOccurrenceStatus(currDistribution.getDistributionStatus().getDistributionstatus());
				dar.setEstablishmentMeans(currDistribution.getDistributionStatus().getEstablishmentmeans());
				tar.addDistribution(dar);
			}
			apiResponse.addResult(tar);
		}

		results.add(apiResponse);
		return results;
	}

}
