package net.canadensys.dataportal.vascan;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Integration tests of the rendered name page
 * @author canadensys
 *
 */
public class SearchPageIntegrationTest extends AbstractIntegrationTest{
	
	
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
	public void testSearchPage() {
		//Coordinates is the landing page
		browser.get(TESTING_SERVER_URL + "search/");
		
		//bind the WebElement to the current page
		PageFactory.initElements(browser, this);
		
		assertEquals("Name search",contentDiv.findElement(By.cssSelector("h1")).getText());
		
		assertEquals("div",footerDiv.getTagName());
	}
	
	@After
	public void tearDown() {
		browser.close();
	}

}
