package net.canadensys.dataportal.vascan.controller.api;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
	
	private static final String TESTED_API_VERSION = "0.1";
	@Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    /**
     * Test that we can not access the resource with an invalid version number
     * @throws Exception
     */
    @Test
   	public void testWrongAPIVersion() throws Exception{
        this.mockMvc.perform(get("/api/wrongversion/search.json").param("q","Amaranthus graecizans"))
        	.andExpect(status().isNotFound())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/json;charset=UTF-8"));
    }
    
    /**
     * Test the search resource without providing the q parameter
     * @throws Exception
     */
    @Test
   	public void testNoParam() throws Exception{
        this.mockMvc.perform(get("/api/"+TESTED_API_VERSION+"/search.json"))
        	.andExpect(status().isBadRequest());
    }
	
    /**
     * Test a taxon with 2 taxonomic assertions
     * @throws Exception
     */
    @Test
	public void testApiGetJSON() throws Exception{
    	//test GET with a synonym with 2 parents
        this.mockMvc.perform(get("/api/"+TESTED_API_VERSION+"/search.json").param("q","Amaranthus graecizans"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/json;charset=UTF-8")) //this is a bug in Spring 3.2, charset should be avoided  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        	.andExpect(jsonPath("$.results[0].matches[0].taxonomicAssertions[0]").exists())
        	.andExpect(jsonPath("$.results[0].matches[0].taxonomicAssertions[1]").exists());
    }
    
    @Test
	public void testApiGetXML() throws Exception{
        this.mockMvc.perform(get("/api/"+TESTED_API_VERSION+"/search.xml").param("q","Acer circinatum"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/xml"))
        	.andExpect(xpath("/vascanAPIResponse/results/searchedName[1]/matches/result[1]/taxonID").number(9199d));
    }
    
    /**
     * Test that we can take a scientific name with authorship and find the matching scientific name.
     * Warning : the authorship is simply ignored
     * @throws Exception
     */
    @Test
	public void testApiGetSciNameWithAuthors() throws Exception{
        this.mockMvc.perform(get("/api/"+TESTED_API_VERSION+"/search.json").param("q","Carex macloviana subsp. haydeniana (Olney) Taylor & MacBryde"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/json;charset=UTF-8")) //this is a bug in Spring 3.2, charset should be avoided  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        	.andExpect(jsonPath("$.results[0].matches[0].taxonID").value(15148));
    }
    
    @Test
	public void testApiGetWithLocalId() throws Exception{
        this.mockMvc.perform(get("/api/"+TESTED_API_VERSION+"/search.json").param("q","8|Acer circinatum"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/json;charset=UTF-8")) //this is a bug in Spring 3.2, charset should be avoided  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        	.andExpect(jsonPath("$.results[0].matches[0].taxonID").value(9199))
        	.andExpect(jsonPath("$.$.results[0].localIdentifier").value("8"));
    }
    
    @Test
  	public void testApiGetWithTaxonId() throws Exception{
        this.mockMvc.perform(get("/api/"+TESTED_API_VERSION+"/search.json").param("q","73"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/json;charset=UTF-8")) //this is a bug in Spring 3.2, charset should be avoided  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        	.andExpect(jsonPath("$.results[0].matches[0].taxonID").value(73));
    }
    
    /**
     * Make sure we do not use autocompletion in the API.
     * @throws Exception
     */
    @Test
  	public void testNoAutocompletion() throws Exception{
        this.mockMvc.perform(get("/api/"+TESTED_API_VERSION+"/search.json").param("q","ca"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/json;charset=UTF-8")) //this is a bug in Spring 3.2, charset should be avoided  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        	.andExpect(jsonPath("$.results[0].numMatches").value(0));
    }
    
    /**
     * Test the POST with a list names with local identifiers
     * @throws Exception
     */
    @Test
	public void testApiPostWithLocalId() throws Exception{
        this.mockMvc.perform(post("/api/"+TESTED_API_VERSION+"/search.json").param("q","8|Acer circinatum\n9|Acer spicatum"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/json;charset=UTF-8")) //this is a bug in Spring 3.2, charset should be avoided  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        	.andExpect(jsonPath("$.results[0].matches[0].taxonID").value(9199))
        	.andExpect(jsonPath("$.results[0].localIdentifier").value("8"))
        	.andExpect(jsonPath("$.results[1].matches[0].taxonID").value(9215))
        	.andExpect(jsonPath("$.results[1].localIdentifier").value("9"));
    }
    
    /**
     * Test the POST with a list of taxonID
     * @throws Exception
     */
    @Test
	public void testApiPostWithUsingTaxonID() throws Exception{
        this.mockMvc.perform(post("/api/"+TESTED_API_VERSION+"/search.json").param("q","9199\n9215"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/json;charset=UTF-8")) //this is a bug in Spring 3.2, charset should be avoided  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        	.andExpect(jsonPath("$.results[0].matches[0].taxonID").value(9199))
        	.andExpect(jsonPath("$.results[1].matches[0].taxonID").value(9215));
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
        MvcResult result = this.mockMvc.perform(get("/api/"+TESTED_API_VERSION+"/search.json").param("q","73").param("callback", "mycallback"))
        	.andExpect(status().isOk())
        	.andExpect(content().encoding("UTF-8"))
        	.andExpect(content().contentType("application/x-javascript"))
        	.andReturn();
        assertTrue(result.getResponse().getContentAsString().startsWith("mycallback("));
    }
}
