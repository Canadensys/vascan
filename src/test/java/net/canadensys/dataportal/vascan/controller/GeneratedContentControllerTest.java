package net.canadensys.dataportal.vascan.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.canadensys.dataportal.vascan.config.GeneratedContentConfig;
import net.canadensys.utils.ZipUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.gbif.dwc.terms.DwcTerm;
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
	
	private int DWCA_IDX_TAXONID = 0;
	private int DWCA_IDX_ACCEPTED_NAME_USAGE_ID = 4;
	private int DWCA_IDX_ACCEPTED_NAME_USAGE = 5;
	private int DWCA_IDX_PARENT_NAME_USAGE_ID = 6;
	private int DWCA_IDX_PARENT_NAME_USAGE = 7;
	private int DWCA_IDX_SCIENTIFIC_NAME = 10;
	private int DWCA_IDX_TAXONOMIC_STATUS = 21;
	
	@Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;
    
    @Autowired
    private GeneratedContentConfig generatedContentConfig;
    
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
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        String filename= (String)mav.getModelMap().get("filename");
        
        //since the page will not get rendered, we call the URI to generate the file
        request.setRequestURI("/generate");
    	handler = handlerMapping.getHandler(request).getHandler();
    	handlerAdapter.handle(request, response, handler);
    	assertEquals(HttpServletResponse.SC_OK, response.getStatus());
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
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        String filename= (String)mav.getModelMap().get("filename");
        
        //since the page will not get rendered, we call the URI to generate the file
        request.setRequestURI("/generate");
    	handler = handlerMapping.getHandler(request).getHandler();
    	handlerAdapter.handle(request, response, handler);
    	assertEquals(HttpServletResponse.SC_OK, response.getStatus());
    	File generatedDwcA = new File(generatedContentConfig.getGeneratedFilesFolder()+filename);
    	assertTrue(generatedDwcA.exists());
    	
    	//Test DarwinCore archive content
    	String unzippedFolder = generatedDwcA.getParentFile().getAbsolutePath()+"/"+ FilenameUtils.getBaseName(generatedDwcA.getName());
    	ZipUtils.unzipFileOrFolder(generatedDwcA, unzippedFolder);
    	List<String> fileLines = FileUtils.readLines(new File(unzippedFolder+"/taxon.txt"));
    	
    	String[] synonymData = fileLines.get(1).split(DELIMITER);
    	
		assertEquals("15164", synonymData[DWCA_IDX_TAXONID]);
		assertEquals("Carex alpina var. holostoma (Drejer) L.H. Bailey",synonymData[DWCA_IDX_SCIENTIFIC_NAME]);
		assertEquals("4904", synonymData[DWCA_IDX_ACCEPTED_NAME_USAGE_ID]);
		assertEquals("Carex holostoma Drejer", synonymData[DWCA_IDX_ACCEPTED_NAME_USAGE]);
		assertEquals("",synonymData[DWCA_IDX_PARENT_NAME_USAGE_ID]);
		assertEquals("",synonymData[DWCA_IDX_PARENT_NAME_USAGE]);
		assertEquals("synonym", synonymData[DWCA_IDX_TAXONOMIC_STATUS]);
		
		FileUtils.deleteDirectory(new File(unzippedFolder));
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
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        String filename= (String)mav.getModelMap().get("filename");
        
        //since the page will not get rendered, we call the URI to generate the file
        request.setRequestURI("/generate");
    	handler = handlerMapping.getHandler(request).getHandler();
    	handlerAdapter.handle(request, response, handler);
    	assertEquals(HttpServletResponse.SC_OK, response.getStatus());
    	File generatedDwcA = new File(generatedContentConfig.getGeneratedFilesFolder()+filename);
    	assertTrue(generatedDwcA.exists());
    	
    	//Test DarwinCore archive content
    	String unzippedFolder = generatedDwcA.getParentFile().getAbsolutePath()+"/"+ FilenameUtils.getBaseName(generatedDwcA.getName());
    	ZipUtils.unzipFileOrFolder(generatedDwcA, unzippedFolder);
    	List<String> fileLines = FileUtils.readLines(new File(unzippedFolder+"/taxon.txt"));
    	
    	String[] taxonData = fileLines.get(1).split(DELIMITER);
    	
		assertEquals("4904", taxonData[DWCA_IDX_TAXONID]);
		assertEquals("Carex holostoma Drejer",taxonData[DWCA_IDX_SCIENTIFIC_NAME]);
		assertEquals("4904", taxonData[DWCA_IDX_ACCEPTED_NAME_USAGE_ID]);
		assertEquals("Carex holostoma Drejer", taxonData[DWCA_IDX_ACCEPTED_NAME_USAGE]);
		assertEquals("2096",taxonData[DWCA_IDX_PARENT_NAME_USAGE_ID]);
		assertEquals("accepted", taxonData[DWCA_IDX_TAXONOMIC_STATUS]);
		
		//test that the synonym is included
		String[] synonymData = fileLines.get(2).split(DELIMITER);
		assertEquals("15164", synonymData[DWCA_IDX_TAXONID]);
		
		FileUtils.deleteDirectory(new File(unzippedFolder));
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
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        String filename= (String)mav.getModelMap().get("filename");
        
        //since the page will not get rendered, we call the URI to generate the file
        request.setRequestURI("/generate");
    	handler = handlerMapping.getHandler(request).getHandler();
    	handlerAdapter.handle(request, response, handler);
    	assertEquals(HttpServletResponse.SC_OK, response.getStatus());
    	File generatedDwcA = new File(generatedContentConfig.getGeneratedFilesFolder()+filename);
    	assertTrue(generatedDwcA.exists());
    	
    	//Test DarwinCore archive content
    	String unzippedFolder = generatedDwcA.getParentFile().getAbsolutePath()+"/"+ FilenameUtils.getBaseName(generatedDwcA.getName());
    	ZipUtils.unzipFileOrFolder(generatedDwcA, unzippedFolder);
    	List<String> fileLines = FileUtils.readLines(new File(unzippedFolder+"/resourcerelationship.txt"));
    	
    	String line1 = fileLines.get(1);
    	
		assertTrue(line1.contains("4793"));
		assertTrue(line1.contains("4790"));
		assertTrue(line1.contains("hybrid parent of"));
		assertTrue(line1.contains("Carex canescens var. brunnescens (Persoon) W.D.J. Koch"));
		
		FileUtils.deleteDirectory(new File(unzippedFolder));
		generatedDwcA.delete();
    }
}
