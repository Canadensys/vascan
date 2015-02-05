package net.canadensys.dataportal.vascan;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Integration tests of the rendered about and api pages.
 * 
 * @author canadensys
 *
 */
public class OtherPagesIntegrationTest extends AbstractIntegrationTest{
		
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
	public void testAboutPage() {
		browser.get(TESTING_SERVER_URL + "about");
		
		PageFactory.initElements(browser, this);
		assertEquals("div",footerDiv.getTagName());
	}
	
	@Test
	public void testAPIPage(){
		browser.get(TESTING_SERVER_URL + "api");
				
		PageFactory.initElements(browser, this);
		assertEquals("div",footerDiv.getTagName());		
	}
	 
	@After
	public void tearDown() {
		browser.close();
	}

}
