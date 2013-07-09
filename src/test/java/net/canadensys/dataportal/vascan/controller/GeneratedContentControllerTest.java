package net.canadensys.dataportal.vascan.controller;

import static org.junit.Assert.*;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import net.canadensys.dataportal.vascan.generatedcontent.GeneratedContentConfig;

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
}
