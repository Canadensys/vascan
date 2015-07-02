package net.canadensys.dataportal.vascan.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.canadensys.dataportal.vascan.DownloadService;
import net.canadensys.dataportal.vascan.DwcaGeneratorService;
import net.canadensys.dataportal.vascan.config.GeneratedContentConfig;
import net.canadensys.dataportal.vascan.config.VascanConfig;
import net.canadensys.dataportal.vascan.dao.DistributionDAO;
import net.canadensys.dataportal.vascan.dao.TaxonDAO;
import net.canadensys.dataportal.vascan.dao.TaxonomyDAO;
import net.canadensys.dataportal.vascan.dao.VernacularNameDAO;
import net.canadensys.dataportal.vascan.dao.query.RegionQueryPart;
import net.canadensys.dataportal.vascan.model.TaxonLookupModel;
import net.canadensys.dataportal.vascan.query.ChecklistQuery;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("downloadService")
public class DownloadServiceImpl implements DownloadService {
	
	private static final Logger LOGGER = Logger.getLogger(DownloadServiceImpl.class);
	
	private String	NEWLINE = "\n";
	
	public static final String DARWIN_CORE_FILE_ARCHIVE_PREFIX = "DWC-";
	public static final String DARWIN_CORE_FILE_ARCHIVE_EXT = ".zip";
	public static final String CSV_FILE_PREFIX = "TXT-";
	public static final String CSV_FILE_EXT = ".txt";
	
	public static final String DARWIN_CORE_FILE_META = "meta.xml";
	
	public static final String DARWIN_CORE_FILE_TAXON = "taxon.txt";
	public static final String DARWIN_CORE_FILE_DISTRIBUTION = "distribution.txt";
	public static final String DARWIN_CORE_FILE_VERNACULAR = "vernacularnames.txt";
	public static final String DARWIN_CORE_FILE_HABITUS = "habit.txt";
	public static final String DARWIN_CORE_FILE_COMPLETE_ARCHIVE = "vascan.zip";

	@Autowired
	private VascanConfig vascanConfig;
	
	@Autowired
	private GeneratedContentConfig generatedContentConfig;
	
	@Autowired
	private TaxonDAO taxonDAO;
	
	@Autowired
	private TaxonomyDAO taxonomyDAO;
	
	@Autowired
	private DistributionDAO distributionDAO;
	
	@Autowired
	private VernacularNameDAO vernacularNameDAO;
	
	@Autowired
	private DwcaGeneratorService dwcaGeneratorService;
	
	@Override
	public String generateFileName(String format) {
	    // generate filename based on requested format
	    if(format.equals("txt")){
	        return CSV_FILE_PREFIX + UUID.randomUUID() + CSV_FILE_EXT;
	    }
	    else{
	        return DARWIN_CORE_FILE_ARCHIVE_PREFIX + UUID.randomUUID() + DARWIN_CORE_FILE_ARCHIVE_EXT;
	    }
	}
	
	@Override
	public String getFileDownloadURL(String filename){
		return generatedContentConfig.getPublicDownloadUrl()+filename;
	}

	@Transactional
	@Override
	public boolean generateTextFile(Map<String,String[]> params, String filename, ResourceBundle bundle) {
	    String destinationFilePath = generatedContentConfig.getGeneratedFilesFolder() + filename;
	    
	    ChecklistQuery cQuery = extractParameters(params);
	    Iterator<TaxonLookupModel> it = taxonDAO.searchIterator(0, cQuery.getHabit(), cQuery.getTaxonId(), cQuery.getRegionQueryPart(),
	    		cQuery.getDistributionStatus(), cQuery.getRank(), cQuery.isHybrids(), cQuery.getSort());
        
	    return generateTSVFile(destinationFilePath, bundle, it);	    
	}
	
	@Transactional
	@Override
	public boolean generateDwcAFile(Map<String,String[]> params, String filename, ResourceBundle bundle){
		String destinationFilePath = generatedContentConfig.getGeneratedFilesFolder() + filename;
		File workingFolder = new File(generatedContentConfig.getGeneratedFilesFolder() + UUID.randomUUID().toString());
		workingFolder.mkdir();
		
		ChecklistQuery cQuery = extractParameters(params);
		Iterator<Map<String,Object>> it = taxonDAO.searchIteratorDenormalized(0, cQuery.getHabit(), cQuery.getTaxonId(), cQuery.getRegionQueryPart(),
				cQuery.getDistributionStatus(), cQuery.getRank(), cQuery.isHybrids(), cQuery.getSort());
		boolean success = dwcaGeneratorService.generateDwcArchive(it, workingFolder, destinationFilePath, bundle);
		try {
			FileUtils.deleteDirectory(workingFolder);
		}
		catch (IOException e) {
			LOGGER.warn("Can't delete working directory " + workingFolder, e);
		}
		return success;
	}
	
	/**
	 * TODO move to Search Service to reuse it
	 * @param params parameters map
	 */
	public ChecklistQuery extractParameters(Map<String,String[]> params){
		ChecklistQuery cQuery = new ChecklistQuery();
		RegionQueryPart reqionQueryPart = new RegionQueryPart();

	    if(params.containsKey("province")){
	    	reqionQueryPart.setRegion(params.get("province"));
	    }
	    
	    if(params.containsKey("combination")){
	    	RegionQueryPart.RegionSelector regionSelector = RegionQueryPart.RegionSelector.fromLabel(params.get("combination")[0]);
	    	reqionQueryPart.setRegionSelector(regionSelector);
	    }
	    else{
	    	reqionQueryPart.setRegionSelector(RegionQueryPart.RegionSelector.ANY_OF);
	    }
	    
	    /* only_ca */
	    if(params.containsKey("only_ca")){
	    	reqionQueryPart.setSearchOnlyInCanada(BooleanUtils.toBoolean(params.get("only_ca")[0]));
	    }
	    
	    if(params.containsKey("habit")){
	    	cQuery.setHabit(params.get("habit")[0]);
	    }
	    else{
	    	cQuery.setHabit("all");
	    }
	    
	    /* taxonid */
	    if(params.containsKey("taxon")){
	    	String taxonIdStr = params.get("taxon")[0];
	        if(!taxonIdStr.equals("")){
	        	cQuery.setTaxonId(Integer.valueOf(taxonIdStr));
	        }
	    }
	    
	    if(params.containsKey("status")){
	    	cQuery.setDistributionStatus(params.get("status"));
	    }
	    else{
	        String distributionStatus[] = {"introduced","native","ephemeral","excluded","extirpated","doubtful"};
	        cQuery.setDistributionStatus(distributionStatus);
	    }

	    if(params.containsKey("rank")){
	    	cQuery.setRank(params.get("rank"));
	    }
	    
	    /* display hybrids */
	    if(params.containsKey("hybrids")){
	    	cQuery.setHybrids(Boolean.parseBoolean(params.get("hybrids")[0]));
	    }
	    else{
	    	cQuery.setHybrids(false);
	    }
	    
	    
	    if(params.containsKey("sort")){
	    	cQuery.setSort(params.get("sort")[0]);
	    }
	    else{
	    	cQuery.setSort("taxonomically");
	    }
	    
	    cQuery.setRegionQueryPart(reqionQueryPart);

	    return cQuery;

	}
	
	private boolean generateTSVFile(String destinationFilePath, ResourceBundle _bundle, Iterator<TaxonLookupModel> it){
		
		if(StringUtils.isBlank(destinationFilePath)){
			return false;
		}
		
	    boolean success = true;
		
		// tsv file
	    StringBuilder csv = new StringBuilder("");
	    
	    String      delimiter = "\t";
	    
	    // csv file columns headers
	    csv.append("URL").append(delimiter);
	    csv.append(_bundle.getObject("rank")).append(delimiter);
	    csv.append(_bundle.getObject("scientific_name")).append(delimiter);
	    csv.append(_bundle.getObject("vernacular_fr")).append(delimiter);
	    csv.append(_bundle.getObject("vernacular_en")).append(delimiter);
	    csv.append(_bundle.getObject("habitus")).append(delimiter);
	    csv.append(_bundle.getObject("province_BC")).append(delimiter);
	    csv.append(_bundle.getObject("province_AB")).append(delimiter);
	    csv.append(_bundle.getObject("province_SK")).append(delimiter);
	    csv.append(_bundle.getObject("province_MB")).append(delimiter);
	    csv.append(_bundle.getObject("province_ON")).append(delimiter);
	    csv.append(_bundle.getObject("province_QC")).append(delimiter);
	    csv.append(_bundle.getObject("province_NB")).append(delimiter);    
	    csv.append(_bundle.getObject("province_PE")).append(delimiter);
	    csv.append(_bundle.getObject("province_NS")).append(delimiter);
	    csv.append(_bundle.getObject("province_NL_N")).append(delimiter);
	    csv.append(_bundle.getObject("province_NL_L")).append(delimiter);
	    csv.append(_bundle.getObject("province_PM")).append(delimiter);
	    csv.append(_bundle.getObject("province_YT")).append(delimiter);
	    csv.append(_bundle.getObject("province_NT")).append(delimiter);
	    csv.append(_bundle.getObject("province_NU")).append(delimiter);
	    csv.append(_bundle.getObject("province_GL"));
	    //remove 
	    Pattern p = Pattern.compile("<\\/?[a-zA-Z][^>]*>" ,Pattern.CASE_INSENSITIVE);
	    if(it !=null){
	    	while(it.hasNext()){
	    		TaxonLookupModel currTlm = it.next();
            	csv.append(NEWLINE);
            	csv.append(vascanConfig.getTaxonUrl()).append(currTlm.getTaxonId()).append(delimiter);
                csv.append(_bundle.getObject("rank_"+currTlm.getRank())).append(delimiter);
                String fsn = currTlm.getCalname();
                StringBuffer sb = new StringBuffer("");
                
                Matcher m = p.matcher(fsn);
                while (m.find()) {
                    m.appendReplacement(sb,"");
                }
                m.appendTail(sb);
                csv.append(sb.toString()).append(delimiter);
                
                //Accepted vernacular names
                if(StringUtils.isNotBlank(currTlm.getAcceptedVernacularFr())){
                	csv.append(currTlm.getAcceptedVernacularFr());
                }
                csv.append(delimiter);
                if(StringUtils.isNotBlank(currTlm.getAcceptedVernacularEn())){
                	csv.append(currTlm.getAcceptedVernacularEn());
                }
                csv.append(delimiter);
                
                String habituses = currTlm.getCalhabit();
                StringBuffer taxonHabitus = new StringBuffer("");
                String splitHabituses[] = habituses.split(",");
                if(splitHabituses != null){
                    for(String h : splitHabituses){
                    	if(taxonHabitus.length() > 0)
                    		taxonHabitus.append(" ");
                    	taxonHabitus.append(_bundle.getObject("habitus_"+h));
                    }
                    csv.append(taxonHabitus.toString());
                }

                csv.append(delimiter);
                csv.append(_bundle.getObject("distribution_"+currTlm.getBC())).append(delimiter);
                csv.append(_bundle.getObject("distribution_"+currTlm.getAB())).append(delimiter);
                csv.append(_bundle.getObject("distribution_"+currTlm.getSK())).append(delimiter);
                csv.append(_bundle.getObject("distribution_"+currTlm.getMB())).append(delimiter);
                csv.append(_bundle.getObject("distribution_"+currTlm.getON())).append(delimiter);
                csv.append(_bundle.getObject("distribution_"+currTlm.getQC())).append(delimiter);
                csv.append(_bundle.getObject("distribution_"+currTlm.getNB())).append(delimiter);
                csv.append(_bundle.getObject("distribution_"+currTlm.getPE())).append(delimiter);
                csv.append(_bundle.getObject("distribution_"+currTlm.getNS())).append(delimiter);
                csv.append(_bundle.getObject("distribution_"+currTlm.getNL_N())).append(delimiter);
                csv.append(_bundle.getObject("distribution_"+currTlm.getNL_L())).append(delimiter);
                csv.append(_bundle.getObject("distribution_"+currTlm.getPM())).append(delimiter);
                csv.append(_bundle.getObject("distribution_"+currTlm.getYT())).append(delimiter);
                csv.append(_bundle.getObject("distribution_"+currTlm.getNT())).append(delimiter);
                csv.append(_bundle.getObject("distribution_"+currTlm.getNU())).append(delimiter);
                csv.append(_bundle.getObject("distribution_"+currTlm.getGL()));
            }
        }
	    
	    File file = new File(destinationFilePath);
	    
        //make sure the destination folder exists
        if(!file.getParentFile().exists()){
        	LOGGER.info("Creating folder structure : " + file.getParentFile().getAbsolutePath());
        	if(!file.getParentFile().mkdirs()){
        		LOGGER.error("Can't create folder structure: " + file.getParentFile());
        	}
        }
	    BufferedWriter bw = null;
	    try{
		    FileOutputStream fos = new FileOutputStream(file);
		    bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
		    bw.write(csv.toString());
		    bw.close();
		    bw = null;
		    fos.close();
	    }
	    catch (IOException e) {
	    	success = false;
	    	LOGGER.fatal("Can't write Vascan TSV file", e);
		}
		finally{
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {}
			}
		}
	    return success;
	}
	
}
