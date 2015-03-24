package net.canadensys.dataportal.vascan;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Functional tests of the rendered taxon page.
 * 
 * @author canadensys
 *
 */
public class TaxonPageFunctionalTest extends AbstractIntegrationTest {
	
	@FindBy(css = "div#content")
	private WebElement contentDiv;
	
	//make sure we find the footer since this is the last processed element
	@FindBy(css = "div#footer")
	private WebElement footerDiv;
		
	@Before
	public void setup() {
		browser = new FirefoxDriver();
	}
	 
	@Test
	public void testTaxonPage() {
		//Coordinates is the landing page
		browser.get(TESTING_SERVER_URL + "taxon/73");
		
		WebElement classificationTable = browser.findElement(By.cssSelector("h2+table.custom_table"));
		//bind the WebElement to the current page
		PageFactory.initElements(browser, this);
		assertEquals("Equisetopsida C. Aghard",contentDiv.findElement(By.cssSelector("h1")).getText());
		assertEquals("sprite sprite-accepted",contentDiv.findElement(By.cssSelector("h1 + p")).getAttribute("class"));
		
		
		WebElement firstClassificationRow = classificationTable.findElement(By.cssSelector("tbody tr"));
		assertEquals("selected",firstClassificationRow.getAttribute("class"));
		assertEquals("Equisetopsida", firstClassificationRow.findElement(By.cssSelector(":nth-child(2)")).getText());
		
		assertEquals("div",footerDiv.getTagName());
	}
	
	@Test
	public void testSynonymPage(){
		//get a synonym
		browser.get(TESTING_SERVER_URL + "taxon/15428");
		
		PageFactory.initElements(browser, this);
		
		assertEquals("Carex abdita Bicknell", contentDiv.findElement(By.cssSelector("h1")).getText());
		assertEquals("sprite sprite-synonym",contentDiv.findElement(By.cssSelector("h1 + p")).getAttribute("class"));
		
		assertEquals("Carex umbellata Schkuhr ex Willdenow", contentDiv.findElement(By.cssSelector("p.sprite-redirect_accepted a")).getText());
		
		assertEquals("div",footerDiv.getTagName());		
	}
	
	@Test
	public void testHybridParentsOnPage(){
		//get a hybrid page
		browser.get(TESTING_SERVER_URL + "taxon/4793");
		
		PageFactory.initElements(browser, this);
		
		WebElement hybridParentH2 = contentDiv.findElement(By.cssSelector("h2"));
		assertEquals("Hybrid parents", hybridParentH2.getText());
		
		List<String> knowHybridParents = new ArrayList<String>();
		knowHybridParents.add("Carex brunnescens (Persoon) Poiret subsp. brunnescens");
		knowHybridParents.add("Carex canescens Linnaeus subsp. canescens");
		assertTrue(knowHybridParents.contains(contentDiv.findElement(By.cssSelector("h2 + p.sprite-redirect_accepted a")).getText()));
		
		assertEquals("div",footerDiv.getTagName());
		
	}
	
	@Test
	public void testHybridChildrenOnPage(){
		//get a hybrid page
		browser.get(TESTING_SERVER_URL + "taxon/23712");
		
		PageFactory.initElements(browser, this);
		
		WebElement hybridChildrenH2 = contentDiv.findElement(By.cssSelector("h2"));
		assertEquals("Hybrid parent of", hybridChildrenH2.getText());
		assertEquals("Carex brunnescens subsp. brunnescens × Carex canescens subsp. canescens", contentDiv.findElement(By.cssSelector("h2 + p.sprite-redirect_accepted a")).getText());
		
		assertEquals("div",footerDiv.getTagName());
	}
	 
	@After
	public void tearDown() {
		browser.close();
	}
}
