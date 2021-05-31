package com.facebook.tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.facebook.genericPage.MasterPage;
import com.facebook.pages.LoginPage;

public class LoginTest  {
	

	public LoginPage lp;


	@Test
	public void performLogin() throws Exception {
	lp= new LoginPage();
		
		lp.clickOnEmail();
		lp.enterEmailOrPhone();
		//lp.clickOnPasword();
		//lp.enterPasword();
		//lp.clickOnLogin();
		//lp.confirmLogin();
		//lp.validUserLogin();
		//lp.invalidUser();

	}
	
	@AfterMethod
	public void takeSreenshotOnFailure (ITestResult result) throws Exception {
		MasterPage.captureScreenshot(result);
	}
		
	
}
