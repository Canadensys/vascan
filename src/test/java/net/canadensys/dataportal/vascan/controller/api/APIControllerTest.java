package net.canadensys.dataportal.vascan.controller.api;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
 * API related tests
 * @author canadensys
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-dispatcher-servlet.xml")
public class APIControllerTest {
	
	@Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @Test
   	public void testWrongAPIVersion() throws Exception{
    	//test GET with a synonym with 2 parents
        this.mockMvc.perform(get("/api/wrongversion/search.json").param("q","Amaranthus graecizans"))
        	.andExpect(status().isNotFound())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/json;charset=UTF-8"));
    }
    
    @Test
   	public void testNoParam() throws Exception{
    	//test GET with a synonym with 2 parents
        this.mockMvc.perform(get("/api/0.1/search.json"))
        	.andExpect(status().isBadRequest());
    }
	
    @Test
	public void testApiGetJSON() throws Exception{
    	//test GET with a synonym with 2 parents
        this.mockMvc.perform(get("/api/0.1/search.json").param("q","Amaranthus graecizans"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/json;charset=UTF-8")) //this is a bug in Spring 3.2, charset should be avoided  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        	.andExpect(jsonPath("$.results[0].taxonID").value(9946))
        	.andExpect(jsonPath("$.results[1].taxonID").value(9946));
    }
    
    @Test
	public void testApiGetXML() throws Exception{
    	//test GET with a synonym with 2 parents
        this.mockMvc.perform(get("/api/0.1/search.xml").param("q","Amaranthus graecizans"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/xml"));
        	//.andExpect(jsonPath("$.results[0].taxonID").value(9946))
        	//.andExpect(jsonPath("$.results[1].taxonID").value(9946));
    }
    
    @Test
	public void testApiGetSciNameWithAuthors() throws Exception{
    	//test GET with a synonym with 2 parents
        this.mockMvc.perform(get("/api/0.1/search.json").param("q","Carex macloviana subsp. haydeniana (Olney) Taylor & MacBryde"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/json;charset=UTF-8")) //this is a bug in Spring 3.2, charset should be avoided  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        	.andExpect(jsonPath("$.results[0].taxonID").value(15148));
    }
    
    @Test
	public void testApiGetWithLocalId() throws Exception{
    	//test GET with a synonym with 2 parents
        this.mockMvc.perform(get("/api/0.1/search.json").param("q","8|Acer circinatum"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/json;charset=UTF-8")) //this is a bug in Spring 3.2, charset should be avoided  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        	.andExpect(jsonPath("$.results[0].taxonID").value(9199))
        	.andExpect(jsonPath("$.localIdentifier").value("8"));
    }
    
    @Test
  	public void testApiGetWithTaxonId() throws Exception{
    	//test GET with a synonym with 2 parents
        this.mockMvc.perform(get("/api/0.1/search.json").param("q","73"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/json;charset=UTF-8")) //this is a bug in Spring 3.2, charset should be avoided  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        	.andExpect(jsonPath("$.results[0].taxonID").value(73));
    }
    
    /**
     * Make sure we do not use autocompletion in the API.
     * @throws Exception
     */
    @Test
  	public void testNoAutocompletion() throws Exception{
    	//test GET with a synonym with 2 parents
        this.mockMvc.perform(get("/api/0.1/search.json").param("q","ca"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/json;charset=UTF-8")) //this is a bug in Spring 3.2, charset should be avoided  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        	.andExpect(jsonPath("$.numResults").value(0));
    }
    
    @Test
	public void testApiPostWithLocalId() throws Exception{
    	//test GET with a synonym with 2 parents
        this.mockMvc.perform(post("/api/0.1/search.json").param("q","8|Acer circinatum\n9|Acer pensylvanicum"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/json;charset=UTF-8")) //this is a bug in Spring 3.2, charset should be avoided  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        	.andExpect(jsonPath("$[0].results[0].taxonID").value(9199))
        	.andExpect(jsonPath("$[0].localIdentifier").value("8"))
        	.andExpect(jsonPath("$[1].results[0].taxonID").value(9209))
        	.andExpect(jsonPath("$[1].localIdentifier").value("9"));
    }
    
//    @Test //we should not accept unknown extension
//	public void testWrongResourceExt() throws Exception{
//    	//test GET with a synonym with 2 parents
//        this.mockMvc.perform(get("/api/0.1/search.jason").param("q","8|Acer circinatum"))
//        	.andExpect(status().isOk())
//        	.andExpect(content().encoding("UTF-8"))
//        	.andExpect(content().contentType("application/json;charset=UTF-8"));
//    }
    
    @Test
	public void testJSONP() throws Exception{
    	//test GET with a synonym with 2 parents
        MvcResult result = this.mockMvc.perform(get("/api/0.1/search.json").param("q","73").param("callback", "mycallback"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/x-javascript"))
        	.andReturn();
        assertTrue(result.getResponse().getContentAsString().startsWith("mycallback("));
    }


}
