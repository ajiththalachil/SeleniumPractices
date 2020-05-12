package QAPackage;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Utilities {
	WebDriver driver;
	String scrName = "";
	Class<?> getClass;
	String methodNames;
	Logger logs;

	public void takeScreenshots(WebDriver driver, String destFile, String methodName)
			throws IOException, NoSuchMethodException, ClassNotFoundException, RuntimeException {
		this.driver = driver;
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String date = java.time.Clock.systemUTC().instant().toString().replaceAll(":", "-").replaceAll("//.", "-");
		String newLocation = destFile + methodName + date + ".png";
		FileUtils.copyFile(screenshot, new File(newLocation));
	}
	
	public Logger logging(Class<?> currentClass) {
		this.getClass = currentClass;
		logs = LogManager.getLogger(currentClass.getName());
		return logs;
	}

}
