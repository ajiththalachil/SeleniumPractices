package QAPackage;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePageObjects {

	WebDriver driver;

	public HomePageObjects(WebDriver driver) {
		this.driver = driver;

	}

	// Link for Switching the Country
	@FindBy(xpath = "//div[@class = 'switch-country']//a[@id = 'AE']")
	private WebElement switchToCountryToAE;

	// List of cities available in the pop up
	@FindBy(xpath = "//div[@class = 'switch-city']/ul/li")
	private List<WebElement> citySelectionList;

	// The City Selection Pop up
	@FindBy(id = "cityModal")
	private WebElement citySelectionPopUp;

	// The currently selected city displayed on the top right
	@FindBy(css = "span#currentCity")
	private WebElement currentCitySelected;
	
	/************************************************-Methods-***********************************************/
	
	public WebElement countryLinkUae() {
		return switchToCountryToAE;
	}
	
	public WebElement cityPopUp() {
		return citySelectionPopUp;
	}
	
	public List <WebElement> citySelectList(){
		return citySelectionList;
	}
	
	public WebElement currentCitySel() {
		return currentCitySelected;
	}

	public void countryLinkUaeClick() {
		switchToCountryToAE.click();

	}

	public String citylistText(int index) {
		
		return citySelectionList.get(index).getText();
	}
	
public void cityListSelectCity(int index) {
		
		citySelectionList.get(index).click();
	}

	public String currentCityText() {
		return currentCitySelected.getText();

	}
	
	public void currentCityClick() {
		currentCitySelected.click();	
	}

}
