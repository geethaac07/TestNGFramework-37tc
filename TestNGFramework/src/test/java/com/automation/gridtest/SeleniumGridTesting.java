package com.automation.gridtest;

import java.net.MalformedURLException;
import java.net.URI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class SeleniumGridTesting {
	@Test
	public void remoteTest() {
		ChromeOptions chrome = new ChromeOptions();
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability(CapabilityType.BROWSER_NAME, "chrome");
		caps.setCapability(ChromeOptions.CAPABILITY, chrome);
		WebDriver driver = null;
		try {
			driver = new RemoteWebDriver(URI.create("http://localhost:4444/").toURL(), caps);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		driver.get("https://login.salesforce.com");

		WebElement uName = driver.findElement(By.id("username"));
		uName.sendKeys("geenalls@icloud.com");
		WebElement pWord = driver.findElement(By.id("password"));
		pWord.sendKeys("123admin$");

		WebElement btn_Login = driver.findElement(By.name("Login"));
		btn_Login.click();
		driver.close();
	}
}
