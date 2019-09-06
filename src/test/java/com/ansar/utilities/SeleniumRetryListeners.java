package com.ansar.utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;


import static com.ansar.report.ExtendReport.*;

public class SeleniumRetryListeners implements IRetryAnalyzer {
	 private int count = 0;
	  private static int maxTry = 1;

	  public boolean retry(ITestResult result) {
		  System.out.println("Status of the result :" + result.getStatus());
		  if (!result.isSuccess()) {
	      if (count < maxTry) { 
	         count++; 
	         result.setStatus(ITestResult.FAILURE);
	         extent.removeTest(getTest());
	        return true; 
	      } else {
	    	  result.setStatus(ITestResult.FAILURE);
	      }
	      
		    } else {
		    	result.setStatus(ITestResult.SUCCESS); 
		    }
	    return false;
	      }
	  
	
		 
			        
			   
			 
		  
	  public String getResultStatusName(int status){
			String resultName = null;
			if(status == 1){
				resultName = "SUCCESS";
			}
			if(status == 2){
				resultName = "FAILURE";
			}
			if(status == 3){
				resultName = "SKIP";
			}
			return resultName;
		}
}
