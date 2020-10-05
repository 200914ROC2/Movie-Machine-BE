package utilities; 

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration { 
	private static Properties prop;
	
	public static String getProperty(String property) { 
		prop = new Properties();
		//String address = System.getProperty("user.dir") + File.separator +  "mm.properties";    
	//	System.out.println("------------------------------PROPERTY: " + address + "-------------------------------");
		String address = "E:\\JavaWorkSpace\\MovieMachineBackend\\mm.properties";       
		try { 
			FileInputStream fis = new FileInputStream(address); 
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(property);   
	}
	
	
}
