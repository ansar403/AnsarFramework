package com.ansar.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;

public class PageSource {

	public static String createPageSourceFile(WebDriver driver, String pagesource) throws IOException {
	    // writing to file
	    String tagetFolder = ".\\target";
	    String pageSourcefilename = tagetFolder + "\\Pagesource\\" + pagesource + ".html";
	    String pageSourcefilepath = ".\\Pagesource\\" + pagesource + ".html";
	    String filename = "./target/" + pagesource + ".html";
	    File file = new File(filename);
	    OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
	    Writer writer = new OutputStreamWriter(outputStream);
	    String pageSource = driver.getPageSource();
	    writer.write(pageSource);
	    writer.close();
	    try {
	      FileUtils.copyFile(file, new File(pageSourcefilename));
	      file.delete();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return pageSourcefilepath;
	  }
	
}
