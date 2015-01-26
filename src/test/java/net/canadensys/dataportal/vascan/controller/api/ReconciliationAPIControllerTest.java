package net.canadensys.dataportal.vascan.controller.api;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Reconciliation API related tests
 * @author canadensys
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-dispatcher-servlet.xml")
public class ReconciliationAPIControllerTest {
	
	private static final String TESTED_API_VERSION = "0.1";
	private static final String RECONCILE_URL = "/refine/"+TESTED_API_VERSION+"/reconcile";
		
	@Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @Test
	public void testServiceMetadata() throws Exception{
    	//test JSON
    	MvcResult result = this.mockMvc.perform(get(RECONCILE_URL))
            	.andExpect(status().isOk())
            	.andExpect(content().encoding("UTF-8"))
            	.andExpect(content().contentType("application/json;charset=UTF-8"))
            	.andReturn();
        
    	//tes JSONP
        result = this.mockMvc.perform(get(RECONCILE_URL)
        	.param("callback", "mycallback"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/x-javascript"))
        	.andReturn();
        
        assertTrue(result.getResponse().getContentAsString().startsWith("mycallback("));
    }
    
    @Test
    public void testReconcileServiceSingleQuery() throws Exception{
    	this.mockMvc.perform(get(RECONCILE_URL)
    			.param("query","Carex arctata"))
            	.andExpect(status().isOk())
            	.andExpect(content().encoding("UTF-8"))
            	.andExpect(content().contentType("application/json;charset=UTF-8"))
            	.andExpect(jsonPath("$.result[0].id").value("4751"));
    }
    
    @Test
    public void testReconcileServiceSingleQueryJsonp() throws Exception{
    	MvcResult result = this.mockMvc.perform(get(RECONCILE_URL)
    			.param("query","Carex arctata")
    			.param("callback", "mycallback"))
            	.andExpect(status().isOk())
            	.andExpect(content().encoding("UTF-8"))
            	.andExpect(content().contentType("application/x-javascript"))
            	.andReturn();
    	assertTrue(result.getResponse().getContentAsString().startsWith("mycallback("));
    }
    
    @Test
    public void testReconcileServiceSingleQueryJson() throws Exception{
    	this.mockMvc.perform(get(RECONCILE_URL)
    			.param("query","{\"query\" : \"Carex arctata\" }"))
            	.andExpect(status().isOk())
            	.andExpect(content().encoding("UTF-8"))
            	.andExpect(content().contentType("application/json;charset=UTF-8"))
            	.andExpect(jsonPath("$.result[0].id").value("4751"));
    }
    
    @Test
    public void testOpenRefineCall() throws Exception{
    	
    	MvcResult result = this.mockMvc.perform(get(RECONCILE_URL)
			.param("queries","{\"q0\":{\"query\":\"Carex umbellata\",\"limit\":3}}"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/json;charset=UTF-8"))
        	.andReturn();
    	System.out.println(result.getResponse().getContentAsString());
    }
    
    @Test
    public void testReconcileServiceMultipleQuery() throws Exception{
    	this.mockMvc.perform(get(RECONCILE_URL)
    			.param("queries","{\"q0\" : { \"query\" : \"Carex arctata\" }, \"q1\" : { \"query\" : \"Carex umbellata\" } }"))
            	.andExpect(status().isOk())
            	.andExpect(content().encoding("UTF-8"))
            	.andExpect(content().contentType("application/json;charset=UTF-8"))
            	.andExpect(jsonPath("$.q0.result[0].id").value("4751"));
    }
}
