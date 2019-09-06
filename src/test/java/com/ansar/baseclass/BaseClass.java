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

import static  com.ansar.browser.Browsers.*;

import com.ansar.browser.Browsers;
import com.ansar.config.LoadConfigurations;
import com.ansar.locaters.Locaters;
import com.ansar.report.ExtendReport;
import com.ansar.utilities.PageSource;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;


import static com.ansar.locaters.Locaters.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import static com.ansar.seleniumactions.SeleniumActions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static com.ansar.constants.constansts.*;
import static com.ansar.browser.Browsers.*;
import static com.ansar.config.LoadConfigurations.*;
import static com.ansar.utilities.PageSource.*;
import static com.ansar.utilities.GeneralUtils.*;
import static com.ansar.report.ExtendReport.*;


public class BaseClass {
	

	public static Logger log;
	public static Set<String> enviromentDetails;
	public WebDriver driver = null;
	
	
	public static WebDriver getDriver() {
        return Browsers.getDriver();
    }

    public  void setDriver(WebDriver driver) {
        this.driver = driver;
    }
	
	@BeforeSuite(alwaysRun = true)
	public synchronized void config() {
		enviromentDetails  = new LinkedHashSet<String>();
	}
	
	 @BeforeTest(alwaysRun = true)
     public void Befortest() {
		 log = Logger.getLogger("ansar");
		 PropertyConfigurator.configure("Log4j.properties");
		 loadGlobalConfig();	
		 ExtendReport.startTest();
     }
	@Parameters("browser")
	@BeforeMethod(alwaysRun = true)
	 public synchronized  void beforeMethod(@Optional("BROWSER") String browserType, Method method) {
		setDriver(browserselection(browserType));
		maximizeWindow();
        geturl(baseurl);
		Locaters l = new Locaters();
		l.method(); 
	    org.testng.annotations.Test test = method.getAnnotation(org.testng.annotations.Test.class);
        log.info("************Test case execution started***************");
        log.info("=================================================");
        log.info(" Method Name : " + method.getName());
        log.info(" TEST CASE   : " + test.testName());
        log.info(" DESCRIPTION : " + test.description());
        log.info("=================================================");
	 }
	
	
	@AfterMethod(alwaysRun = true)
	 public synchronized void afterMethod(ITestResult result) throws IOException {
		//	ExtendReport.assignGroups(result, driver);
		ExtendReport.getReport(result);
		ExtendReport.browserProperties(getDriver(), enviromentDetails);
		 if (result.getStatus() == ITestResult.FAILURE) {
	            String currentURL = currentUrl();
	            getTest().log(Status.INFO, "URL : <a href='" + currentURL + "'>" + currentURL + "</a>");
	           /* String failureImageFileName = null;
	            failureImageFileName = captureScreenShot(driver, result.getName());*/
	            String screenshotPath=System.getProperty("user.dir")+"\\Screenshots\\"+result.getName()+".png";
	            try {
	            	File f = new File(screenshotPath);
	            	if(f.exists())
	            		getTest().fail("Failed test screenshot", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
	        		}
	            	catch (Exception e) {
	            		getTest().error(e.getMessage());
	            }
	            String pageSourcePath = null;
	            pageSourcePath = PageSource.createPageSourceFile(getDriver(), result.getName());
	            getTest().log(Status.INFO,
	                    "page source link : <a href='" + pageSourcePath + "' target='_blank'>Page Source</a>");
	        }
		getDriver().close();
	 }

	@AfterSuite(alwaysRun = true)
     public synchronized void aftersuit() {
		 ExtendReport.addPropertiesToReport(enviromentDetails);
		 extent.setSystemInfo("URL :", "<a href='" + baseurl + "'>" + baseurl + "</a>");
		 extent.flush();
     }
		public static void captureScreen(WebDriver driver, String tname) throws IOException {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
			FileUtils.copyFile(source, target);
			System.out.println("Screenshot taken");
		}
		
}
	 
	 
		
		
