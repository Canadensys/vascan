package net.canadensys.dataportal.vascan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
 * Integration tests of the download page
 * @author canadensys
 *
 */
public class DownloadPageIntegrationTest extends AbstractIntegrationTest{
	
	@FindBy(css = "div#content")
	private WebElement contentDiv;
	
	//make sure we find the footer since this is the last processed element
	@FindBy(css = "div#footer")
	private WebElement footerDiv;
		
	@Before
	public void setup() {
		browser = new FirefoxDriver();
	}
	
	@After
	public void tearDown() throws Exception {
		browser.close();
	}
	 
	@Test
	public void testDownloadPage() {
		//Coordinates is the landing page
		browser.get(TESTING_SERVER_URL + "download?format=txt&taxon=0&habit=tree&combination=anyof&province=PM&status=native&status=introduced&status=ephemeral&status=excluded&status=extirpated&status=doubtful");
		
		WebElement readyElement = new WebDriverWait(browser, 10).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ready")));
		WebElement filenameElement = readyElement.findElement(By.cssSelector("a"));
		
		//make sure we have a filename
		assertTrue(filenameElement.getText().endsWith(".txt"));

		//bind the WebElement to the current page
		PageFactory.initElements(browser, this);
		//make sure we see the footer
		assertEquals("div",footerDiv.getTagName());
	}

}
