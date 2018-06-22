package net.canadensys.dataportal.vascan.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.canadensys.dataportal.vascan.config.GeneratedContentConfig;
import net.canadensys.utils.ZipUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.gbif.dwc.terms.DwcTerm;
import org.gbif.dwc.terms.Term;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Testing the generated content controller routing and make sure files are generated.
 * @author canadensys
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test-dispatcher-servlet.xml"})
@TransactionConfiguration(transactionManager="hibernateTransactionManager")
public class GeneratedContentControllerTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	public static String	DELIMITER = "\t";
	public static String	NEWLINE = "\n";
		
	@Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;
    
    @Autowired
    private GeneratedContentConfig generatedContentConfig;
    
    private int getHeaderIndexFromTerm(List<String> headers, Term term){
    	return headers.indexOf(term.simpleName());
    }
    
    @Test
    public void testTextFileGeneration() throws Exception {
    	MockHttpServletResponse response = new MockHttpServletResponse();
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	request.setMethod("GET");
    	request.setRequestURI("/download");
    	request.addParameter("format", "txt");
    	request.addParameter("taxon", "0");
    	request.addParameter("habit", "tree");
    	request.addParameter("combination", "anyof");
    	request.addParameter("province", "PM");
    	request.addParameter("status", "native");
    	request.addParameter("status", "introduced");
    	request.addParameter("status", "ephemeral");
    	request.addParameter("status", "excluded");
    	request.addParameter("status", "extirpated");
    	request.addParameter("status", "doubtful");
    	
    	Object handler = handlerMapping.getHandler(request).getHandler();
    	
    	//ask for a download and get a download
        ModelAndView mav = handlerAdapter.handle(request, response, handler);
        assertEquals(MockHttpServletResponse.SC_OK, response.getStatus());
        
        HashMap<String,Object> pageModel = (HashMap<String,Object>)mav.getModelMap().get("page");
        String filename= (String)pageModel.get("filename");
        
        //since the page will not get rendered, we call the URI to generate the file
        request.setRequestURI("/generate");
    	handler = handlerMapping.getHandler(request).getHandler();
    	handlerAdapter.handle(request, response, handler);
    	assertEquals(MockHttpServletResponse.SC_OK, response.getStatus());
    	assertTrue(new File(generatedContentConfig.getGeneratedFilesFolder()+filename).exists());
    }
    
    /**
     * Test the content of a generated DwcA that includes a synonym.
     * 
     * @throws Exception
     */
    @Test
    public void testDwcAFileGenerationSynonym() throws Exception {
    	MockHttpServletResponse response = new MockHttpServletResponse();
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	request.setMethod("GET");
    	request.setRequestURI("/download");
    	request.addParameter("format", "dwc");
    	request.addParameter("taxon", "15164");
    	request.addParameter("habit", "all");
    	request.addParameter("status", "native");
    	request.addParameter("rank", "variety");
    	
    	Object handler = handlerMapping.getHandler(request).getHandler();
    	
    	//ask for a download and get a download
        ModelAndView mav = handlerAdapter.handle(request, response, handler);
        assertEquals(MockHttpServletResponse.SC_OK, response.getStatus());
        HashMap<String,Object> pageModel = (HashMap<String,Object>)mav.getModelMap().get("page");
        String filename= (String)pageModel.get("filename");
        
        System.out.println("+++testDwcAFileGenerationSynonym filename:" + filename);
        
        //since the page will not get rendered, we call the URI to generate the file
        request.setRequestURI("/generate");
    	handler = handlerMapping.getHandler(request).getHandler();
    	handlerAdapter.handle(request, response, handler);
    	assertEquals(MockHttpServletResponse.SC_OK, response.getStatus());
    	File generatedDwcA = new File(generatedContentConfig.getGeneratedFilesFolder()+filename);
    	assertTrue(generatedDwcA.exists());
    	
    	//Test DarwinCore archive content
    	String unzippedFolder = generatedDwcA.getParentFile().getAbsolutePath()+"/"+ FilenameUtils.getBaseName(generatedDwcA.getName());
    	ZipUtils.unzipFileOrFolder(generatedDwcA, unzippedFolder);
    	List<String> fileLines = FileUtils.readLines(new File(unzippedFolder+"/taxon.txt"));
    	
    	List<String> headers = Arrays.asList(fileLines.get(0).split(DELIMITER));
    	String[] synonymData = fileLines.get(1).split(DELIMITER);
    	
		assertEquals("15164", synonymData[getHeaderIndexFromTerm(headers,DwcTerm.taxonID)]);
		assertEquals("Carex alpina var. holostoma (Drejer) L.H. Bailey",synonymData[getHeaderIndexFromTerm(headers,DwcTerm.scientificName)]);
		assertEquals("4904", synonymData[getHeaderIndexFromTerm(headers,DwcTerm.acceptedNameUsageID)]);
		assertEquals("Carex holostoma Drejer", synonymData[getHeaderIndexFromTerm(headers,DwcTerm.acceptedNameUsage)]);
		assertEquals("",synonymData[getHeaderIndexFromTerm(headers,DwcTerm.parentNameUsageID)]);
		assertEquals("",synonymData[getHeaderIndexFromTerm(headers,DwcTerm.parentNameUsage)]);
		assertEquals("synonym", synonymData[getHeaderIndexFromTerm(headers,DwcTerm.taxonomicStatus)]);
		
		FileUtils.deleteDirectory(new File(unzippedFolder));
    	request.getSession().invalidate();
    }
    
    /**
     * Test the content of a generated DwcA that includes an accepted taxon.
     * @throws Exception
     */
    @Test
    public void testDwcAFileGenerationAccepted() throws Exception {
    	MockHttpServletResponse response = new MockHttpServletResponse();
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	request.setMethod("GET");
    	request.setRequestURI("/download");
    	request.addParameter("format", "dwc");
    	request.addParameter("taxon", "4904");
    	request.addParameter("habit", "all");
    	request.addParameter("status", "native");
    	
    	Object handler = handlerMapping.getHandler(request).getHandler();
    	
    	//ask for a download and get a download
        ModelAndView mav = handlerAdapter.handle(request, response, handler);
        assertEquals(MockHttpServletResponse.SC_OK, response.getStatus());
        HashMap<String,Object> pageModel = (HashMap<String,Object>)mav.getModelMap().get("page");
        String filename= (String)pageModel.get("filename");
        
        //since the page will not get rendered, we call the URI to generate the file
        request.setRequestURI("/generate");
    	handler = handlerMapping.getHandler(request).getHandler();
    	handlerAdapter.handle(request, response, handler);
    	assertEquals(MockHttpServletResponse.SC_OK, response.getStatus());
    	File generatedDwcA = new File(generatedContentConfig.getGeneratedFilesFolder()+filename);
    	assertTrue(generatedDwcA.exists());
    	
    	//Test DarwinCore archive content
    	String unzippedFolder = generatedDwcA.getParentFile().getAbsolutePath()+"/"+ FilenameUtils.getBaseName(generatedDwcA.getName());
    	ZipUtils.unzipFileOrFolder(generatedDwcA, unzippedFolder);
    	List<String> fileLines = FileUtils.readLines(new File(unzippedFolder+"/taxon.txt"));
    	
    	List<String> headers = Arrays.asList(fileLines.get(0).split(DELIMITER));
    	String[] taxonData = fileLines.get(1).split(DELIMITER);
    	
		assertEquals("4904", taxonData[getHeaderIndexFromTerm(headers,DwcTerm.taxonID)]);
		assertEquals("Carex holostoma Drejer",taxonData[getHeaderIndexFromTerm(headers,DwcTerm.scientificName)]);
		assertEquals("4904", taxonData[getHeaderIndexFromTerm(headers,DwcTerm.acceptedNameUsageID)]);
		assertEquals("Carex holostoma Drejer", taxonData[getHeaderIndexFromTerm(headers,DwcTerm.acceptedNameUsage)]);
		assertEquals("2096",taxonData[getHeaderIndexFromTerm(headers,DwcTerm.parentNameUsageID)]);
		assertEquals("accepted", taxonData[getHeaderIndexFromTerm(headers,DwcTerm.taxonomicStatus)]);
		
		//test that the synonym is included
		String[] synonymData = fileLines.get(2).split(DELIMITER);
		assertEquals("15164", synonymData[getHeaderIndexFromTerm(headers,DwcTerm.taxonID)]);
		
		FileUtils.deleteDirectory(new File(unzippedFolder));
    	request.getSession().invalidate();
    }
    
    /**
     * Test the content of a generated DwcA that includes an accepted taxon.
     * @throws Exception
     */
    @Test
    public void testDwcAFileGenerationHybrid() throws Exception {
    	MockHttpServletResponse response = new MockHttpServletResponse();
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	request.setMethod("GET");
    	request.setRequestURI("/download");
    	request.addParameter("format", "dwc");
    	request.addParameter("taxon", "4793");
    	request.addParameter("habit", "all");
    	request.addParameter("status", "native");
    	request.addParameter("hybrids", "true");
    	
    	Object handler = handlerMapping.getHandler(request).getHandler();
    	
    	//ask for a download and get a download
        ModelAndView mav = handlerAdapter.handle(request, response, handler);
        assertEquals(MockHttpServletResponse.SC_OK, response.getStatus());
        HashMap<String,Object> pageModel = (HashMap<String,Object>)mav.getModelMap().get("page");
        String filename= (String)pageModel.get("filename");
        
        
        //since the page will not get rendered, we call the URI to generate the file
        request.setRequestURI("/generate");
    	handler = handlerMapping.getHandler(request).getHandler();
    	handlerAdapter.handle(request, response, handler);
    	assertEquals(MockHttpServletResponse.SC_OK, response.getStatus());
    	File generatedDwcA = new File(generatedContentConfig.getGeneratedFilesFolder()+filename);
    	assertTrue(generatedDwcA.exists());
    	
    	//Test DarwinCore archive content
    	String unzippedFolder = generatedDwcA.getParentFile().getAbsolutePath()+"/"+ FilenameUtils.getBaseName(generatedDwcA.getName());
    	ZipUtils.unzipFileOrFolder(generatedDwcA, unzippedFolder);
    	List<String> fileLines = FileUtils.readLines(new File(unzippedFolder+"/resourcerelationship.txt"));
    	
    	String line;
    	boolean taxonFound = false;
    	for(int i=1;i<fileLines.size();i++){
    		line = fileLines.get(i);
    		if(line.contains("23712")){
    			assertTrue(line.contains("4793"));
    			assertTrue(line.contains("hybrid parent of"));
    			assertTrue(line.contains("Carex brunnescens (Persoon) Poiret subsp. brunnescens"));
    			taxonFound = true;
    		}
    	}
    	assertTrue("taxon 23712 not found in resourcerelationship extension", taxonFound);
		
		FileUtils.deleteDirectory(new File(unzippedFolder));
		generatedDwcA.delete();
    	
    	request.getSession().invalidate();
    }
}
