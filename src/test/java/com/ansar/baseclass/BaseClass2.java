package com.ansar.baseclass;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.ansar.config.LoadConfigurations;
import com.ansar.locaters.Locaters;
import com.ansar.report.ExtendReport2;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;


import static com.ansar.locaters.Locaters.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import static com.ansar.seleniumactions.SeleniumActions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static com.ansar.constants.constansts.*;
import static com.ansar.browser.Browsers.*;
import static com.ansar.config.LoadConfigurations.*;
import static com.ansar.utilities.PageSource.*;
import static com.ansar.utilities.GeneralUtils.*;


public class BaseClass2 {
	
	public static WebDriver driver;
	public static Logger log;
	private static Set<String> enviromentDetails;
	
	
	@BeforeSuite
	public static void config() {
		
	}
	
	@Parameters("browser")
	@BeforeClass
	public static void setup(@Optional("BROWSER") String browserType) {
		log = Logger.getLogger("ansar");
		PropertyConfigurator.configure("Log4j.properties");
		ExtendReport2.startReport(prop);
		loadGlobalConfig();	
		/* WebDriverManager.chromedriver().setup(); */
		browserselection(browserType);
        maximizeWindow();
        geturl(baseurl);
	}
	
	 @BeforeTest()
     public static void Befortest() {
	  
     }
	 
	@BeforeMethod()
	 public static void beforeMethod() {
		Locaters l = new Locaters();
		l.method();
	 }
	
	
	@AfterMethod()
	 public static void afterMethod(ITestResult result) throws IOException {
		 // Assigning groups
		ExtendReport2.assignGroups(result, driver);

	    // adding status to report
		ExtendReport2.getReport(result);

	    // Storing properties in enviromentDetails object and this object is
	    // used to add environment details to report in After suite method
	//	ExtendReport2.browserProperties(driver, enviromentDetails);

	    if (result.getStatus() == ITestResult.FAILURE) {

	      // log failed URL
	      String currentURL = currentUrl();
	      ExtendReport2.getTest().log(Status.INFO, "URL : <a href='" + currentURL + "'>" + currentURL + "</a>");

	      // log failed screenshot to extent report
	      String failureImageFileName = captureScreenShot(driver, result.getName());
	      ExtendReport2.getTest().fail("Failed test screenshot",
	      MediaEntityBuilder.createScreenCaptureFromPath(failureImageFileName).build());

	      // log page source to extent report
	      String pageSourcePath = createPageSourceFile(driver, result.getName());
	      ExtendReport2.getTest().log(Status.INFO, "page source link : <a href='" + pageSourcePath + "'>Page Source</a>");

	    }
		driver.close();
	 }
	
	 @AfterTest()
     public static void aftertest() {
     	
     }

	 @AfterClass()
     public static void afterclass() {
     	
     }
	 
	 @AfterSuite()
     public static void aftersuit() {
		 ExtendReport2.addPropertiesToReport(enviromentDetails);
		 ExtentReports extent = ExtendReport2.getExtent();
		    // extent.setSystemInfo("URL: ", URL);
		    // ExtentReport.browserProperties(driver);
		  //  extent.setSystemInfo("URL :", "<a href='" + url + "'>" + url + "</a>");
		    ExtendReport2.getExtent().flush();

		    // Closing the browser drivers based on the parameter 'closeBrowser' value
		    // available in
		    // runconfig.properties.

		    List<String> browsers = new ArrayList<String>();
		    browsers.add("chrome.exe");
		    browsers.add("firefox.exe");
		    browsers.add("iexplore.exe");
		    browsers.add("chromedriver.exe");
		    browsers.add("IEDriverServer.exe");
		    browsers.add("geckodriver.exe");

		    if (prop.getProperty("closeBrowser") != null) {
		      if (!prop.getProperty("closeBrowser").isEmpty() && prop.getProperty("closeBrowser").equalsIgnoreCase("true")) {
		      killProcess(browsers);
		      }
		    } else {
		      killProcess(browsers);
		    }
		    List<String> tempfiles = new ArrayList<String>();
		    tempfiles.add("rust_mozprofile");
		    tempfiles.add("scoped_dir");
		    tempfiles.add("chrome_BITS");
		    tempfiles.add("tmpaddon");
		    deleteTempFiles(tempfiles);
     }
}
	 
	 
		
		
