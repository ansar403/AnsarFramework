package com.ansar.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ansar.baseclass.BaseClass;

public class LoadConfigurations extends BaseClass{
	
	public static Properties prop = new Properties();
	private static String env;
	private static FileInputStream fis;
	
	public static void loadGlobalConfig() {
	    String fileLocation = "." + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "config" + File.separator;
	    List<String> files = new ArrayList<String>();
	    try {
	      if (System.getProperty("ENV") != null) {
	        env = System.getProperty("ENV");
	        files.add(fileLocation + env + File.separator + "localproperties.properties");
	        files.add(fileLocation + env + File.separator + "testdata.properties");
	        prop = loadPropertyFile(files);
	      } else {
	        files.add(fileLocation + "config.properties");
	      //files.add(fileLocation + "testdata.properties");
	        prop = loadPropertyFile(files);
	        env = prop.getProperty("ENV");
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	public static Properties loadPropertyFile(List<String> files) {
	    prop = new Properties();

	    for (String file : files) {
	      try {
	        fis = new FileInputStream(file);
	        if (fis == null) {
	        log.info("No file exists");
	        }
	        prop.load(fis);
	      } catch (IOException ex) {
	        ex.printStackTrace();
	      } finally {
	        if (fis != null) {
	          try {
	            fis.close();
	          } catch (Exception e) {
	            e.printStackTrace();
	          }
	        }
	      }
	    }
	    return prop;
}
}