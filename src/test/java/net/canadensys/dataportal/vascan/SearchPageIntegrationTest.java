package net.canadensys.dataportal.vascan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.containsString;

import java.util.regex.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Integration tests of the rendered search page
 * @author canadensys
 *
 */
public class SearchPageIntegrationTest extends AbstractIntegrationTest{

	@FindBy(css = "div#content")
	private WebElement contentDiv;
	
	@FindBy(css = "div#footer")
	private WebElement footerDiv;

	@FindBy(css = "input#search_term")
	private WebElement searchInput;
	
	@FindBy(xpath = "//div[@id='content']/h2[1]")
	private WebElement searchHeader;
	
	@FindBy(css = "ul#search_list")
	private WebElement searchResults;
		
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
		
		searchInput.click();
		searchInput.sendKeys("carex");
		WebElement searchDropdown = new WebDriverWait(browser, 10).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.tt-dropdown-menu")));
		assertTrue(searchDropdown.getText().contains("Scientific Names"));
		assertTrue(searchDropdown.getText().contains("Vernacular Names"));
		assertTrue(searchDropdown.getText().contains("Carex feta"));
		assertTrue(searchDropdown.getText().contains("carex noir"));

		searchDropdown.findElement(By.cssSelector(".tt-suggestion:nth-of-type(4)")).click();
		PageFactory.initElements(browser, this);
		assertEquals("Carex alma",contentDiv.findElement(By.cssSelector("h1")).getText());
	}

	@Test
	public void testSearchResults() {

		browser.get(TESTING_SERVER_URL + "search/?q=carex");
		
		//bind the WebElement to the current page
		PageFactory.initElements(browser, this);

//		Pattern p = Pattern.compile(".*\\d.*");
//		Matcher m = p.matcher(searchHeader.getText().replaceAll(",", ""));
		
//		assertTrue(m.find());
//		assertTrue(Integer.parseInt(m.group(1)) > 1000);

		assertEquals("Carex Linnaeus", searchResults.findElement(By.cssSelector("li:first-child")).getText());
		
		//test nothing found
		browser.get(TESTING_SERVER_URL + "search/?q=blablabla");
		
		//bind the WebElement to the current page
		PageFactory.initElements(browser, this);
		
		assertEquals("0 Results", contentDiv.findElement(By.cssSelector("h2")).getText());
		assertEquals("Nothing found.", searchResults.getText());
	}
	
	@After
	public void tearDown() {
		browser.close();
	}

}
