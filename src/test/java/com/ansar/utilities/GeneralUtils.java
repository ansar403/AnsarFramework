package com.ansar.utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.ansar.baseclass.BaseClass;

public class GeneralUtils extends BaseClass{

	public static void killProcess(List<String> process) {

	    for (String p : process) {
	      try {
	        Runtime.getRuntime().exec("taskkill /f /im " + p);
	      } catch (IOException e) {

	        // e.printStackTrace();
	        log.info("Exception occured while deleting the process");
	      }
	    }
	  }

	  public static void deleteTempFiles(List<String> flodernames) {

	    for (String flodername : flodernames) {

	      try {
	        java.util.Properties properties = System.getProperties();

	        String tempFileName = properties.getProperty("java.io.tmpdir");
	        File directory = new File(tempFileName);
	        File[] files = directory.listFiles();
	        for (File file : files) {
	          if (file.isDirectory()) {
	            if (file.getName().contains(flodername)) {
	              FileUtils.deleteDirectory(file);
	            }

	          } else if (file.isFile() && file.getName().contains(flodername)) {

	            file.delete();
	            // FileUtils.deleteDirectory(file);

	          }
	        }
	      } catch (IOException e) {
	        // e.printStackTrace();
	        log.info("Exception occured while deleting the file");
	      }
	
	    }
	  }
}
