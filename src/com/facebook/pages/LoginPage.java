package com.facebook.pages;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import com.facebook.genericPage.MasterPage;

public class LoginPage extends MasterPage {

	File src = new File(System.getProperty("user.dir") + "\\src\\com\\facebook\\resorces\\UserCredentials.xlsx");
	FileInputStream fis = new FileInputStream(src);
	XSSFWorkbook wb = new XSSFWorkbook(fis);
	XSSFSheet sh = wb.getSheet("DemoSheet");
	int countvalid=0;
	int countInvalid=0;

	public LoginPage() throws Exception {
		super();
	}

	public void clickOnEmail() {
		click("Email");
		logger.info("Email Clicked");
	}

	public void enterEmailOrPhone() throws Exception {

		int rows = sh.getLastRowNum();
		int columns = sh.getRow(0).getLastCellNum();
		
		for (int i = 1; i <= rows; i++) {
			for (int j = 0; j < columns - 1; j++) {

				String email = sh.getRow(i).getCell(j).getStringCellValue();
				String pass = sh.getRow(i).getCell(j + 1).getStringCellValue();
				sendData("Email", email);
				sendData("Password", pass);
				click("LoginButton");
				Thread.sleep(3000);
				
				
				String expectedTitle="Facebook";
				
				 if (expectedTitle.equalsIgnoreCase(getTitle())) {
					 countvalid++;
					 Thread.sleep(3000);
					 click("Account");
					 click("Logout");
					 
				 }
				 else {
					 String expectedErrorTitle="Log in to Facebook";
						if (expectedErrorTitle.equalsIgnoreCase(getTitle())) {
							countInvalid++;
				 }
				
				 }
				 if (i==(rows)) {
						break;
					}
					
				navigateBack();
				Thread.sleep(3000);
				clear("Email");
				
			}
			
		}
		logger.info(countvalid + " Valid User logged In");
		logger.info(countInvalid + " Invalid Users logged In");
	}

	public void clickOnPasword() {
		click("Password");
	}

	 public void enterPasword() {

	 sendData("Password", "123456");
	 }

	public void clickOnLogin() {
	click("LoginButton");
	}
	
	public void validUserLogin() throws InterruptedException {
		String validUserID= sh.getRow(1).getCell(0).getStringCellValue();
		String validUserpassword= sh.getRow(1).getCell(1).getStringCellValue();
		sendData("Email",validUserID);
		sendData("Password",validUserpassword);
		click("LoginButton");
		Thread.sleep(3000);
		String expectedUrl="https://www.facebook.com/";
		String actualUrl= getUrl();
		Assert.assertEquals(actualUrl, expectedUrl);
		logger.info("Valid User Logged In ");
		driver.close();
		
		
	}
	
	public void invalidUser() throws Exception {
		MasterPage mp=new MasterPage();
		String invalidUserID= sh.getRow(2).getCell(0).getStringCellValue();
		String invalidpasssword=sh.getRow(2).getCell(1).getStringCellValue();
		sendData("Email",invalidUserID);
		sendData("Password",invalidpasssword);
		click("LoginButton");
		Thread.sleep(3000);
		String expectedErrosMessage="You can't log in at the moment";
		String actualErrorMessage= getText("Message");
		Assert.assertEquals(actualErrorMessage, expectedErrosMessage);
		logger.info("InValid User Logged In ");
	}

	public void confirmLogin() {

		String Acceptedmsg = "You have sucessfully logged in";
		String Actualmsg = getText("Message");
		Assert.assertEquals(Actualmsg, Acceptedmsg);

	}

}
