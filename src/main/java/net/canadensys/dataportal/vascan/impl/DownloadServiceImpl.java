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
import net.canadensys.dataportal.vascan.dao.TaxonDAO;
import net.canadensys.dataportal.vascan.generatedcontent.GeneratedContentConfig;
import net.canadensys.dataportal.vascan.model.TaxonLookupModel;
import net.canadensys.dataportal.vascan.query.ChecklistQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("downloadService")
public class DownloadServiceImpl implements DownloadService {
	
	public static final String DARWIN_CORE_FILE_ARCHIVE_PREFIX = "DWC-";
	public static final String DARWIN_CORE_FILE_ARCHIVE_EXT = ".zip";
	public static final String CSV_FILE_PREFIX = "TXT-";
	public static final String CSV_FILE_EXT = ".txt";

	@Autowired
	private GeneratedContentConfig generatedContentConfig;
	
	@Autowired
	private TaxonDAO taxonDAO;
	
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
	
	@Override
	public void downloadDwcAFile() {
		// TODO Auto-generated method stub

	}

	@Transactional
	@Override
	public boolean generateTextFile(Map<String,String[]> params, String filename, ResourceBundle bundle) {
	    String destinationFilePath = generatedContentConfig.getGeneratedFilesFolder() + filename;
	    
	    ChecklistQuery cQuery = extractParameters(params);
	    Iterator<TaxonLookupModel> it = taxonDAO.loadTaxonLookup(0, cQuery.getHabit(), cQuery.getTaxonId(), cQuery.getCombination(), cQuery.getProvince(),
	    		cQuery.getDistributionStatus(), cQuery.getRank(), cQuery.isHybrids(), cQuery.getSort());
        
	    return generateTSVFile(destinationFilePath, bundle, it);	    
	}
	
	/**
	 * 
	 * @param params parameters map
	 */
	public ChecklistQuery extractParameters(Map<String,String[]> params){
		ChecklistQuery cQuery = new ChecklistQuery();

	    if(params.containsKey("province")){
	    	cQuery.setProvince(params.get("province"));
	    }
	    
	    if(params.containsKey("combination")){
	    	cQuery.setCombination(params.get("combination")[0]); 
	    }
	    else{
	    	cQuery.setCombination("anyof");
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
	    String      newline = "\n";
	    
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
            	csv.append(newline);
            	csv.append(generatedContentConfig.getTaxonUrl()).append(currTlm.getTaxonId()).append(delimiter);
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
			e.printStackTrace();
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
