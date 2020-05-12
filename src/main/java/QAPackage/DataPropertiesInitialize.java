package QAPackage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataPropertiesInitialize {
	
	final String dataLocation = "C:\\Users\\PC\\FreshToHome\\properties\\data.properties";
	public Properties dataPropertiesfetch() throws IOException {
		Properties prop = new Properties();
		FileInputStream file = new FileInputStream(dataLocation);
		prop.load(file);
		return prop;
	}

}
