package com.automation.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	private Properties propFile = null;
	private FileInputStream fis = null;
	
	
	public Properties LoadFile(String filename) {
		
		propFile = new Properties();
		String propFilePath = null;
		switch (filename)
		{
		case "sfLoginProperties":
			propFilePath = Constants.SALESFORCE_PROPERTIES;
			break;
		case "sfDataProperties":
			propFilePath =Constants.SALESFORCE_TESTDATA_PROPERTIES;
			break;
		}
		
		try {
			fis = new FileInputStream(propFilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			propFile.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return propFile;
	}
	
	public String getPropertyValue(String key) {
		String value = propFile.getProperty(key);
		return value;
	}
	
}
