

import java.util.Properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UtilProp {
	static String _PROP_FILE_ = "C:\\Users\\a2tri\\Documents\\Software Engineering\\Individual Project\\TriplettTermProject4830\\workspaceCSCI4830-tech-excercise-Triplett\\individualExcerciseTriplett\\WebContent\\config.properties";
	static Properties prop = new Properties();
	
	public static void loadProperty() throws Exception {
		FileInputStream inputStream = null;
		if (new File(_PROP_FILE_).exists()) {
			inputStream = new FileInputStream(_PROP_FILE_);
			System.out.println("Server Properties Loaded");
		}
		
		if (inputStream == null) {
			throw new FileNotFoundException();
		}
		prop.load(inputStream);
	}
	
	public static String getProp(String key) {
		return prop.getProperty(key).trim();
	}
}
