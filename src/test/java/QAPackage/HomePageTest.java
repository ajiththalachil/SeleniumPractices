package QAPackage;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class HomePageTest extends InitializeDriver {

	String url = "";
	String selectionCountryUAE;
	String[] expectedCities;
	WebDriverWait wait;
	HomePageObjects homePage;
	Utilities utility;
	String destFile;
	String className;
	Properties prop;
	String methodName;
	Logger log;
	String selectedCityName;

	@BeforeTest(alwaysRun=true)
	public void inputIntializeTest() throws Exception {
		DataPropertiesInitialize properties = new DataPropertiesInitialize();
		utility = new Utilities();
		log = utility.logging(this.getClass());
		try {
			prop = properties.dataPropertiesfetch();
		} catch (Exception e) {
			log.fatal("data Properties not found");
		}
		selectionCountryUAE = prop.getProperty("UAE");
		expectedCities = prop.getProperty("ExpectedCities").split(",");
		url = prop.getProperty("URL");
		destFile = prop.getProperty("screenshotDestination");
		log.info("Data Initiated successfully");
	}

	@BeforeMethod(alwaysRun=true)
	public void browserOpenTest() throws Exception {
		try {driver = intialize();
		}catch(Exception e) {
			log.fatal("Issue with driver Initialization");
		}
		driver.get(url);
		wait = new WebDriverWait(driver, 10);
		homePage = PageFactory.initElements(driver, HomePageObjects.class);
		log.info("Browser Initiated Successfully");
	}

	@Test(groups= {"baseRun"})
	public void homePageNavTest() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info("Executing home page navigation");
		log.info(driver.getTitle());
		log.info("Home Page Navigation done successfully");
		Assert.assertTrue(homePage.citySelectionPopUp.isDisplayed());
		if (homePage.citySelectionPopUp.isDisplayed() == false) {
			log.fatal("The Home page is not displayed properly");
		}
		try {
			utility.takeScreenshots(driver, destFile, methodName);
		} catch (Exception e) {
			log.error("Issue with takeScreenshot");
			e.printStackTrace();
		}

	}

	@Test(dependsOnMethods = "homePageNavTest",groups= {"fullRun"})
	public void switchCountryLinkPresentTest() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info("Executing UAE Country Selection link available");
		wait.until(ExpectedConditions.visibilityOf(homePage.citySelectionPopUp));
		boolean linkPresent = homePage.switchToCountryToAE.isDisplayed();
		Assert.assertEquals(linkPresent, true);
		if (linkPresent == false) {
			log.error("The Country change link is not displayed");
		}
		try {
			utility.takeScreenshots(driver, destFile, methodName);
		} catch (Exception e) {
			log.error("Issue with takeScreenshot");
			e.printStackTrace();
		}
		log.info("The country change Link is Present in he city selection Pop up");
	}

	@Test(dependsOnMethods = "switchCountryLinkPresentTest",groups= {"fullRun"})
	public void switchCountryLinkClickableTest() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info("Executing UAE Country Selection link Click");
		wait.until(ExpectedConditions.visibilityOf(homePage.citySelectionPopUp));
		homePage.countryLinkUaeClick();
		wait.until(ExpectedConditions.visibilityOf(homePage.currentCitySelected));
		String newCityName = homePage.currentCityText();
		Assert.assertEquals(selectionCountryUAE, newCityName);
		if (selectionCountryUAE.equalsIgnoreCase(newCityName) == false) {
			log.error("The change country link is not working properly");
		}
		try {
			utility.takeScreenshots(driver, destFile, methodName);
		} catch (Exception e) {
			log.error("Issue with takeScreenshot");
			e.printStackTrace();
		}
		log.info("The country change link is Clicked and Navigates successfully");

	}

	@Test(dependsOnMethods = "switchCountryLinkClickableTest",groups= {"fullRun"})
	public void homPageCitySelectionsTest() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info("Executing home page all city selections testcase");
		List<WebElement> citySelections = homePage.citySelectionList;
	    String capturedCities[] = new String[citySelections.size()];
		for (int cityId = 0; cityId < citySelections.size(); cityId++) {
			wait.until(ExpectedConditions.visibilityOf(homePage.citySelectionPopUp));
			citySelections = homePage.citySelectionList;
			homePage.citylistText(cityId);
			homePage.cityListSelectCity(cityId);
			wait.until(ExpectedConditions.visibilityOf(homePage.currentCitySelected));
			selectedCityName = homePage.currentCityText();	
			if (selectedCityName.contains("/")==true) {
				String[] cityNameSplit = selectedCityName.split("/");
				selectedCityName = cityNameSplit[0];
			}
			capturedCities[cityId] = selectedCityName;
			try {
				utility.takeScreenshots(driver, destFile, methodName + selectedCityName);
			} catch (Exception e) {
				log.error("Issue with takeScreenshot");
				e.printStackTrace();
			}
			String currentUrl = driver.getCurrentUrl();
			Assert.assertEquals(currentUrl, url);
			log.info(selectedCityName
					+ " is successfully selected and the current city Selection is displayed correctly");
			if (currentUrl.equalsIgnoreCase(url) == false) {
				log.error("The URL is incorrect");
			}
			if (cityId != citySelections.size() - 1) {
				homePage.currentCityClick();
			}
		}
		Assert.assertEquals(capturedCities,expectedCities );
		log.info("All cities are selectable and the city selection is updated properly");
		if (Arrays.equals(capturedCities, expectedCities)==false){
			log.error("The city list is not as per the expected list");
		}

	}

	@AfterMethod(alwaysRun=true)
	public void quitDriver() {
		log.info(methodName + " - Test Finished Execution");
		log.info("************************NEXT*****************************************");
		driver.quit();
	}
	
	@AfterClass(alwaysRun=true)
	public void endLine() {
		log.info("************************END OF SUITE*****************************************");
	}
	

}
