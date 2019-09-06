package com.ansar.report;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.ansar.baseclass.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;


import static com.ansar.seleniumactions.SeleniumActions.*;


public class ExtendReport2 extends BaseClass  {

	  private static ExtentHtmlReporter htmlReporter;
	  private static ExtentReports extent;
	  private static ExtentTest test;


	  public static void createTest(String testCaseId, String testCaseName) {
		  ExtendReport2.setTest(ExtendReport2.getExtent().createTest(testCaseId + "-" + testCaseName));
		  }
	  
	  
	  public static ExtentHtmlReporter getHtmlReporter() {
	    return htmlReporter;
	  }

	
	  public static void setHtmlReporter(ExtentHtmlReporter htmlReporter) {
		  ExtendReport2.htmlReporter = htmlReporter; 
	  }
	 

	  public static ExtentReports getExtent() {
	    return extent;
	  }

	  public static void setExtent(ExtentReports extent) {
		  ExtendReport2.extent = extent;
	  }

	  public static ExtentTest getTest() {
	    return test;
	  }

	  public static void setTest(ExtentTest test) {
		  ExtendReport2.test = test;
	  }

	  public static void startReport(Properties prop) {
	    htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/target/ExtentReport.html");
	    extent = new ExtentReports();
	    extent.attachReporter(htmlReporter);
		/*
		 * extent.setSystemInfo("OS", prop.getProperty("OS"));
		 * extent.setSystemInfo("Author", prop.getProperty("Author"));
		 * htmlReporter.config().setDocumentTitle(prop.getProperty("ReportTitle"));
		 * htmlReporter.config().setReportName(prop.getProperty("ReportName"));
		 */
	    htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
	    htmlReporter.config().setTheme(Theme.STANDARD);
	    htmlReporter.config().setChartVisibilityOnOpen(false);
	   
	  }

	  public static void getReport(ITestResult result) {

	 
	    if (result.getStatus() == ITestResult.FAILURE) {

	      test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()
	          + "Test case failed due to below issues", ExtentColor.RED));
	      test.fail(result.getThrowable());
	    
	    } else if (result.getStatus() == ITestResult.SUCCESS) {
	      test.log(Status.PASS,
	          MarkupHelper.createLabel(result.getName() + "Test case Passed", ExtentColor.GREEN));
	    } else {
	      test.log(Status.SKIP,
	          MarkupHelper.createLabel(result.getName() + "Test case is skipped", ExtentColor.YELLOW));
	      test.skip(result.getThrowable());
	    }
	    if (ITestResult.SUCCESS == result.getStatus()) {
	      log
	          .info("*******************************************************************************************");
	      log
	          .info("*******************************Test Passed*************************************************");
	      log
	          .info("*******************************************************************************************");
	    } else {
	    	log
	          .info("===========================================================================================");
	    	log
	          .info("===============================Test Faileld================================================");
	    	log
	          .info("===========================================================================================");
	    }
	  }

	 

	  public static void assignGroups(ITestResult result, WebDriver driver) {
	    for (String group : result.getMethod().getGroups()) {
	    	ExtendReport2.getTest().assignCategory(group);
	    }
	   
	    Map<String, String> params = result.getTestContext().getCurrentXmlTest().getLocalParameters();

	    if (params.containsKey("browser") && !params.containsKey("device")) {
	    	ExtendReport2.getTest().assignCategory(params.get("browser"));
	    }

	    if (params.containsKey("device") && params.containsKey("browser")) {
	    	ExtendReport2.getTest().assignCategory(params.get("device") + " - " + params.get("browser"));
	    }

	  }

	  // This method is for webservices automation
	  public static void assignGroups(ITestResult result) {
	    for (String group : result.getMethod().getGroups()) {
	    	ExtendReport2.getTest().assignCategory(group);
	    }
	  }

	  public static void browserProperties(WebDriver driver, Set<String> obj) throws IOException {
	    Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();

	    String os = System.getProperty("os.name").toLowerCase();
	    String browsername = caps.getBrowserName();
	    String browserversion = caps.getVersion();
	    String envVariable = "Brower Name :" + browsername + " | Version :" + browserversion
	        + " | Os :" + os;
	 //   obj.add(envVariable);

	  }

	  public static void addPropertiesToReport(Set<String> enviromentDetails) {
	    int count = 0;
	    for (String env : enviromentDetails) {
	      int enviroment = count + 1;
	      extent.setSystemInfo("Env " + enviroment + " : ", env);
	      count = count + 1;
	    }
	  }

	  public static void flushIt() {
	    extent.flush();
	  }
		
	}

