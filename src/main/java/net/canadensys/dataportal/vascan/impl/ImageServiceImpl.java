package net.canadensys.dataportal.vascan.impl;

import java.io.File;
import java.util.List;

import net.canadensys.dataportal.vascan.ImageService;
import net.canadensys.dataportal.vascan.config.GeneratedContentConfig;
import net.canadensys.dataportal.vascan.dao.DistributionDAO;
import net.canadensys.dataportal.vascan.dao.TaxonDAO;
import net.canadensys.dataportal.vascan.generatedcontent.DistributionImageGenerator;
import net.canadensys.dataportal.vascan.model.DistributionModel;
import net.canadensys.dataportal.vascan.model.TaxonLookupModel;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("imageService")
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private GeneratedContentConfig config;
	
	@Autowired
	private DistributionImageGenerator distributionImageGenerator;
	
	@Autowired
	private DistributionDAO distributionDAO;
	
	@Autowired
	private TaxonDAO taxonDAO;
	
	@Transactional
	@Override
	public String getImage(Integer taxonId, String imageExtension){
		
		if(taxonId == null || StringUtils.isBlank(imageExtension)){
			return null;
		}
		
		File wantedImage = new File(config.getImageFolder() + config.getDistributionImagePrefix() + taxonId + "." + imageExtension);

		if(!wantedImage.exists()){
			List<DistributionModel> distributions = distributionDAO.loadTaxonDistribution(taxonId);
			String generatedImgPath = null;
			if(distributions != null && !distributions.isEmpty()){
				generatedImgPath = distributionImageGenerator.writeDistributionSvg(taxonId, distributions);
			}
			else{
				TaxonLookupModel tlm = taxonDAO.loadTaxonLookup(taxonId);
				if(tlm != null){
					generatedImgPath = distributionImageGenerator.writeDistributionSvg(tlm);
				}
			}
			if(StringUtils.isNotBlank(generatedImgPath)){
				distributionImageGenerator.SVGtoPNG(taxonId,new File(generatedImgPath));
			}
		}

		if(wantedImage.exists()){
			return wantedImage.getAbsolutePath();
		}
		else{
			System.out.println("Does not exist "+wantedImage.getAbsolutePath());
		}
		return null;
	}

}
