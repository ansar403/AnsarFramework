package com.ansar.report;

import java.io.File;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.ITestAnnotation;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;


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


public class ExtendReport extends BaseClass  {
	

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest logger;
	private static ThreadLocal<ExtentTest>  extentTestThreadSafe = new ThreadLocal<ExtentTest>();
	private int count = 0;
	private static int maxTry = 1;
	
	

    public static synchronized ExtentTest getTest() {
        return extentTestThreadSafe.get();
    }

    public static synchronized void setTest(ExtentTest test) {
        extentTestThreadSafe.set(test);
    }
	
    public static synchronized void createTest(String testCaseId, String testCaseName) {
	    setTest(extent.createTest(testCaseId + "-" + testCaseName));
	  }
	
	  public static synchronized void startTest() {
		    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//time stamp
			String repName="Test-Report-"+timeStamp+".html";
			htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir")+ "/target/"+repName);//specify location of the report
			htmlReporter.loadXMLConfig(System.getProperty("user.dir")+ "/extent-config.xml");
			extent=new ExtentReports();
			extent.attachReporter(htmlReporter);
			extent.setSystemInfo("Hostname: ", "localhost");
			extent.setSystemInfo("Environemnt:" ,"QA");
			extent.setSystemInfo("user","pavan");
			htmlReporter.config().setDocumentTitle("Ansar Test Project"); // Tile of report
			htmlReporter.config().setReportName("Functional Test Automation Report"); // name of the report
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP); //location of the chart
			htmlReporter.config().setTheme(Theme.DARK);
	  }
	  
	  public static synchronized void getReport(ITestResult result) {
		    if (result.getStatus() == ITestResult.FAILURE) {
	        	//logger=extent.createTest(result.getName()); 
	        	getTest().log(Status.FAIL,MarkupHelper.createLabel(result.getName() + " is failed due to below issues.",ExtentColor.RED)); // send the passed information to the report with GREEN color highlighted
	        	getTest().log(Status.FAIL,result.getThrowable());
	        } else if (result.getStatus() == ITestResult.SUCCESS) {
	        	//logger=extent.createTest(result.getName()); // create new entry in th report
	        	getTest().log(Status.PASS,MarkupHelper.createLabel(result.getName() + "TestCase Passed",ExtentColor.GREEN)); // send the passed information to the report with GREEN color highlighted
	        } else {
	        	//logger=extent.createTest(result.getName()); // create new entry in th report
	        	getTest().log(Status.SKIP,MarkupHelper.createLabel(result.getName() + "Test case is skipped",ExtentColor.ORANGE));
	        	getTest().log(Status.FAIL,result.getThrowable());
	        }
	        if (ITestResult.SUCCESS == result.getStatus()) {
	        	log.info("*******************************************************************************************");
	        	log.info("*******************************Test Passed*************************************************");
	        	log.info("*******************************************************************************************");
	        } else {
	        	log.info("===========================================================================================");
	        	log.info("===============================Test Faileld================================================");
	        	log.info("===========================================================================================");
	        }
	    }
	  public static synchronized void assignGroups(ITestResult result, WebDriver driver) {
	        for (String group : result.getMethod().getGroups()) {
	        	getTest().assignCategory(group);
	        }
	        Map<String, String> params = result.getTestContext().getCurrentXmlTest().getLocalParameters();

	        if (params.containsKey("browser") && !params.containsKey("device")) {
	        	getTest().assignCategory(params.get("browser"));
	        }

	        if (params.containsKey("device") && params.containsKey("browser")) {
	        	getTest().assignCategory(params.get("device") + " - " + params.get("browser"));
	        }

	    }
	  public static synchronized void browserProperties(WebDriver driver, Set<String> obj) {
	        Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
	        String os = System.getProperty("os.name").toLowerCase();
	        String browsername = caps.getBrowserName();
	        String browserversion = caps.getVersion();
	        String envVariable = "Brower Name :" + browsername + " | Version :" + browserversion + " | Os :" + os;
	        obj.add(envVariable);
	    }

	    public static void addPropertiesToReport(Set<String> enviromentDetails) {
	        int count = 0;
	        for (String env : enviromentDetails) {
	            int enviroment = count + 1;
	            extent.setSystemInfo("Env " + enviroment + " : ", env);
	            count = count + 1;
	        }
	    }
		
	}

