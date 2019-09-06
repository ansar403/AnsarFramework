package com.ansar.locaters;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ansar.baseclass.BaseClass;


public class Locaters {
	
	public void method() {
		 PageFactory.initElements(BaseClass.getDriver(),this);
	}
	
	@FindBy(xpath = "//a[@data-dd-target='dropdown-industri']") public static WebElement industries;
	@FindBy(xpath = "//a[@data-dd-target='dropdown-wwo']") public static WebElement WhatWeOffer;

}
