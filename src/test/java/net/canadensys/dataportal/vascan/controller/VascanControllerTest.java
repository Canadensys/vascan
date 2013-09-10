package net.canadensys.dataportal.vascan.controller;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

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
 * Testing the Vascan main controller.
 * @author canadensys
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test-dispatcher-servlet.xml"})
@TransactionConfiguration(transactionManager="hibernateTransactionManager")
public class VascanControllerTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;
    
    @Test
    public void testSearchRawInput() throws Exception {
    	MockHttpServletResponse response = new MockHttpServletResponse();
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	request.setMethod("GET");
    	request.setRequestURI("/search");
    	//search with unnecessary spaces to make sure we will handle it correctly
    	request.addParameter("q", " Carex abbreviata ");
    	
    	Object handler = handlerMapping.getHandler(request).getHandler();
        ModelAndView mav = handlerAdapter.handle(request, response, handler);
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());

        @SuppressWarnings("unchecked")
		HashMap<String,Object> searchModel = (HashMap<String,Object>)mav.getModelMap().get("search");
        String term= (String)searchModel.get("term");
        assertEquals("Carex abbreviata",term);
    }
}
