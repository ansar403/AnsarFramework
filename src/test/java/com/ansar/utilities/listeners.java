package com.ansar.utilities;

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


import static com.ansar.baseclass.BaseClass.*;
import static com.ansar.seleniumactions.SeleniumActions.*;

public class listeners extends TestListenerAdapter {
	
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest logger;
	private int count = 0;
	private static int maxTry = 1;
	
		
	public void onStart(ITestContext testContext)
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//time stamp
		String repName="Test-Report-"+timeStamp+".html";
		
		htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir")+ "/test-output/"+repName);//specify location of the report
		htmlReporter.loadXMLConfig(System.getProperty("user.dir")+ "/extent-config.xml");
		
		extent=new ExtentReports();
		
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host name","localhost");
		extent.setSystemInfo("Environemnt","QA");
		extent.setSystemInfo("user","pavan");
		
		htmlReporter.config().setDocumentTitle("InetBanking Test Project"); // Tile of report
		htmlReporter.config().setReportName("Functional Test Automation Report"); // name of the report
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP); //location of the chart
		htmlReporter.config().setTheme(Theme.DARK);
	}
	
	public void onTestSuccess(ITestResult tr)
	{
		log.info("*******************************************************************************************");
		log.info("*******************************Test Passed*************************************************");
		log.info("*******************************************************************************************");
		logger=extent.createTest(tr.getName()); // create new entry in th report
		logger.log(Status.PASS,MarkupHelper.createLabel(tr.getName() + "TestCase Passed",ExtentColor.GREEN)); // send the passed information to the report with GREEN color highlighted
	}
	
	public void onTestFailure(ITestResult tr)
	{
		log.info("===========================================================================================");
        log.info("===============================Test Faileld================================================");
        log.info("===========================================================================================");
		retry(tr);
	    
		logger=extent.createTest(tr.getName()); // create new entry in th report
		logger.log(Status.FAIL,MarkupHelper.createLabel(tr.getName() + "TestCase failed due to below issues",ExtentColor.RED)); // send the passed information to the report with GREEN color highlighted
		logger.log(Status.FAIL,tr.getThrowable());
		try {
			captureScreen(getDriver(), tr.getName());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String screenshotPath=System.getProperty("user.dir")+"\\Screenshots\\"+tr.getName()+".png";
		
		File f = new File(screenshotPath); 
		
		if(f.exists())
		{
		try {
			logger.fail("Screenshot is below:" + logger.addScreenCaptureFromPath(screenshotPath));
			} 
		catch (IOException e) 
				{
				e.printStackTrace();
				}
		}
		String currentURL = currentUrl();
	    logger.log(Status.INFO, "URL : <a href='" + currentURL + "'>" + currentURL + "</a>");
		 String pageSourcePath = null;
         try {
			pageSourcePath = PageSource.createPageSourceFile(getDriver(), tr.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         logger.log(Status.INFO,"page source link : <a href='" + pageSourcePath + "' target='_blank'>Page Source</a>");
	}
	
	public void onTestSkipped(ITestResult tr)
	{
		logger=extent.createTest(tr.getName()); // create new entry in th report
		logger.log(Status.SKIP,MarkupHelper.createLabel(tr.getName() + "Test case is skipped",ExtentColor.ORANGE));
		logger.log(Status.FAIL,tr.getThrowable());
	}
	
	public void onFinish(ITestContext testContext)
	{
		extent.flush();
	}
	
	

	  public boolean  retry(ITestResult iTestResult) {
	    if (!iTestResult.isSuccess()) {
	      if (count < maxTry) { 
	        count++; 
	        return true;
	      } 
	     
	    }
	    return false;
	  }


}
