package com.facebook.genericPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;

public class MasterPage {

	public static WebDriver driver;
	public Properties config;
	public Properties loc;
	public static Logger logger;

	public MasterPage() throws Exception {

		FileInputStream ip = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\com\\facebook\\repository\\configuration.properties");
		config = new Properties();
		config.load(ip);

		FileInputStream fs = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\com\\facebook\\repository\\locators.properties");
		loc = new Properties();
		loc.load(fs);

		if (config.getProperty("browser").equalsIgnoreCase("chrome")) {
			ChromeOptions op= new ChromeOptions();
			op.addArguments("--disable-notifications");

			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\com.facebook.drivers\\chromedriver.exe");
			driver = new ChromeDriver(op);
		} else if (config.getProperty("browser").equalsIgnoreCase("FireFox")) {
			System.out.println("Open FireFox");
		} else {
			System.out.println("Open IE");
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		
		driver.get("https://www.facebook.com/");
		
		logger =Logger.getLogger("MasterPage");
		PropertyConfigurator.configure(System.getProperty("user.dir")+"\\src\\com\\facebook\\logs\\log4j.properties");
		
		
	}

	public void click(String xpathkey) {

		driver.findElement(By.xpath(loc.getProperty(xpathkey))).click();
	}

	public void sendData(String xpathkey, String data) {

		driver.findElement(By.xpath(loc.getProperty(xpathkey))).sendKeys(data);
	}
	
	public String getText(String Text) {

		return driver.findElement(By.xpath(loc.getProperty(Text))).getText();
	}
	
	public String getUrl() {
		
		return driver.getCurrentUrl();
	}
	
	public String getTitle() {
		return driver.getTitle();
		
	}
	
	public void clear(String xpathkey) {
		
		driver.findElement(By.xpath(loc.getProperty(xpathkey))).clear();
	}
	
	public void navigateBack() {
		
		driver.navigate().back();
	}
	
	public static void captureScreenshot(ITestResult result) throws IOException {

		if (ITestResult.FAILURE == result.getStatus()) {

			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(source, new File(System.getProperty("user.dir")+"\\src\\com\\facebook\\screenshotOnFailure\\Screenshot" + result.getName() + ".png"));
		}

	}
	

}
