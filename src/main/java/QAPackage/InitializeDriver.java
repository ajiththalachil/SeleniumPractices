package QAPackage;

import java.util.Properties;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class InitializeDriver {

	WebDriver driver;
	public WebDriver intialize() throws Exception {
		DataPropertiesInitialize properties = new DataPropertiesInitialize();
		Properties prop = properties.dataPropertiesfetch();
		//Required Variables
		String browserName = prop.getProperty("Browser");
        
		if (browserName.equalsIgnoreCase("chrome")==true) {
			System.setProperty("webdriver.chrome.driver", "D:\\Aj_Selenium\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			//options.addArguments("--headless");
			driver = new ChromeDriver(options);
		}

		if (browserName.equalsIgnoreCase("firefox")==true) {
			driver = new FirefoxDriver();
		}

		if (browserName.equalsIgnoreCase("InternetExplorer")==true) {
			driver = new InternetExplorerDriver();
		}
        
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
		return driver;
        
	}

}
