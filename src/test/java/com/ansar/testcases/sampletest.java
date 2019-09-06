package com.ansar.testcases;

import org.testng.annotations.Test;
import com.ansar.baseclass.BaseClass;
import com.ansar.locaters.Locaters;
import com.ansar.report.ExtendReport;

import static com.ansar.pageobject.HomePage.*;

public class sampletest extends BaseClass{
	
	@Test(groups = { "P0" ,"Regression"})
	public static void placeurl () {
		ExtendReport.createTest("BOF_TA011", "placeurl");
	    getDriver().get("https://facebook.com");
		industries();
	}

}
