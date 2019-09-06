package com.ansar.testcases;

import org.testng.annotations.Test;

import com.ansar.baseclass.BaseClass;
import com.ansar.report.ExtendReport;

import static com.ansar.pageobject.HomePage.*;

public class sampletesttwo extends BaseClass {

	@Test(groups = { "P0" ,"Regression"})
	public void method() {
	ExtendReport.createTest("BOF_TA010", "Method");
	//	getDriver().get("https://yahoo.com");
		whatweoffer();
	}
	
}
