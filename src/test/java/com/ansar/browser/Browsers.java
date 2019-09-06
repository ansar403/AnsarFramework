package com.ansar.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.ansar.baseclass.BaseClass;
import com.ansar.config.LoadConfigurations;

import static com.ansar.config.LoadConfigurations.*;


public class Browsers extends BaseClass {

	 private static String broWser;
	 private static WebDriver driver;
	 private static ThreadLocal<WebDriver> threadSafeWebDriver = new ThreadLocal<WebDriver>();
	 
	 public void setDriver(WebDriver driver) {
	        threadSafeWebDriver.set(driver);
	    }
	 
	static Browsers b = new Browsers();
	 
	
	    public static WebDriver getDriver() {
	        return threadSafeWebDriver.get();
	    }

	public static WebDriver browserselection(String browser) {
		  String sBrowser = "";
		  sBrowser = System.getProperty("BROWSER");
		  try { 
			  if(sBrowser != null && !sBrowser.equals(""))
			  { 
				  broWser = sBrowser;
				  } 
			  else  {
				broWser = prop.getProperty("BROWSER");
		        }
			   if(browser.equals("BROWSER")) {
					 browser(broWser);
				 }
					 else {
						 browser(browser);
					 }
				 }
	   catch (Exception e) {
		  e.printStackTrace(); 
		  
	  }
		return driver;
	}
	
	public static WebDriver browser(String browserType) {
		
		if(browserType.equalsIgnoreCase("chrome") || browserType.equalsIgnoreCase("gc")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//src//test//resources//drivers//chromedriver.exe");
		     driver = new ChromeDriver();
		      threadSafeWebDriver.set(driver);
		}
		else if(browserType.equalsIgnoreCase("firefox") || browserType.equalsIgnoreCase("ff")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//src//test//resources//drivers//chromedriver.exe");
		     driver = new FirefoxDriver();
		     threadSafeWebDriver.set(driver);
		}
		else if (browserType.equalsIgnoreCase("internetexplorer") || browserType.equalsIgnoreCase("ie")) {
			 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//src//test//resources//drivers//chromedriver.exe");
		     driver = new InternetExplorerDriver();
		     threadSafeWebDriver.set(driver);
		}
		else {
			log.info("No browser selected hence chooisng chrome browser");
		}
		
		 /*switch(browserType) {
		 case "chrome":
		 case "gc" :
			 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//src//test//resources//drivers//chromedriver.exe");
		     driver = new ChromeDriver();
		      threadSafeWebDriver.set(driver);
		     break;
		 case "firefox":
		 case "ff":
			 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//src//test//resources//drivers//chromedriver.exe");
		     driver = new FirefoxDriver();
		 break;
	 case "internetexplorer":
	 case "ie":
		 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//src//test//resources//drivers//chromedriver.exe");
	     driver = new InternetExplorerDriver();
	     break;
	 default:
		 log.info("No browser selected hence chooisng chrome browser");
			
			 * System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+
			 * "//src//test//resources//drivers//chromedriver.exe"); driver = new
			 * ChromeDriver();
			 
	 }*/
		 b.setDriver(driver);
	     return getDriver();
	}
	
	
}
