package com.ansar.seleniumactions;

import static com.ansar.baseclass.BaseClass.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.ansar.baseclass.BaseClass;



public class SeleniumActions extends BaseClass{

	
	
	public static void maximizeWindow() {
		getDriver().manage().window().maximize();
	}
	
	public static void geturl(String url) {
		getDriver().get(url);
	}
	public static String currentUrl() {
		return getDriver().getCurrentUrl();
	}
	
	 public static String captureScreenShot(WebDriver driver, String imageName) throws IOException {
		    String tagetFolder = ".\\target";
		    String failureImageFileName = tagetFolder + "\\Screenshots\\" + System.currentTimeMillis()
		        + "-" + imageName + ".png";
		    String failureImageFileNamePath = ".\\Screenshots\\" + System.currentTimeMillis() + "-"
		        + imageName + ".png";
		    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		    try {
		      FileUtils.copyFile(scrFile, new File(failureImageFileName));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		    return failureImageFileNamePath;
		  }
}
