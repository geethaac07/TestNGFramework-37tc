package com.automation.bymodules;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import comautomation.base.BaseTestSalesForce;

public class UserMenuAutomationScripts extends BaseTestSalesForce{

	@Test
	public void User_Menu_Dropdown_TestScript_05() throws InterruptedException {

		loginToWeb();
		Thread.sleep(4000);

		UserMenuOptions();
	}

	@Test
	public void MyProfile_UserMenu_Option_TestScript_06() throws AWTException, InterruptedException { // Verifying

		loginToWeb();

		Thread.sleep(4000);

		UserMenuOptions();

		WebElement My_Profile = driver.findElement(By.xpath("//*[@id='userNav-menuItems']/a[1]"));

		ClickAction(My_Profile, "My Profile");
		Thread.sleep(4000);

		WebElement edit_arrow = driver.findElement(By.id("moderatorMutton"));
		ExplicitWaitElement(edit_arrow);
		ClickAction(edit_arrow, "Down Arrow");

		driver.switchTo().activeElement();
		WebElement edit_profile = driver
				.findElement(By.cssSelector(".zen-open > ul:nth-child(2) > li:nth-child(2) > a:nth-child(1)"));

		ExplicitWaitElement(edit_profile);
		ClickAction(edit_profile, "Edit Profile"); // reaching the Edit Profile
		Thread.sleep(4000);

		driver.switchTo().frame(2);
		log.info("Switched to iFrame3");

		Thread.sleep(4000);
		WebElement lastName = driver.findElement(By.xpath("//input[@id='lastName']"));

		ExplicitWaitElement(lastName);

		ClickAction(lastName, "Last Name");
		enterKeys(lastName, "Last Name", "AC");

		WebElement saveAllElement = driver.findElement(By.cssSelector("input.zen-btn:nth-child(1)"));

		ExplicitWaitElement(saveAllElement);

		ClickAction(saveAllElement, "Save All");

		driver.switchTo().defaultContent();
		Thread.sleep(4000);

		WebElement postLink = driver.findElement(By.id("publisherAttachTextPost")); // .xpath("//*[@id=\"publisherAttachTextPost\"]"));
		postLink.click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		WebElement post_frame = driver.findElement(By.xpath("//iframe[@class ='cke_wysiwyg_frame cke_reset']"));
		// xpath("//*[@id='cke_43_contents']/iframe"));
		ClickAction(post_frame, "Post Clicking");

		Actions actions = new Actions(driver);
		actions.sendKeys("Hi everyone!").build().perform();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		WebElement shareButton = driver.findElement(By.id("publishersharebutton"));
		ClickAction(shareButton, "Message Posted");

	}

	@Test
	public void MySettings_UserMenu_Option_TestScript_07() throws InterruptedException, AWTException { // Verifying

		loginToWeb();
		Thread.sleep(4000);

		UserMenuOptions();

		WebElement My_Profile = driver.findElement(By.xpath("//*[@id='userNav-menuItems']/a[2]"));

		ClickAction(My_Profile, "My Profile");
		Thread.sleep(5000);

		Robot robot = new Robot();
		robot.mouseWheel(2);

		WebElement personal_link = driver.findElement(By.xpath("//*[@id=\"PersonalInfo\"]/a")); // cssSelector(".PersonalInfo_icon"));
		ExplicitWaitElement(personal_link);
		ClickAction(personal_link, "Personal");
		Thread.sleep(5000);
		// *[@id="LoginHistory_font"]

		WebElement loginHistory = driver.findElement(By.xpath("//*[@id='LoginHistory_font']/span"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", loginHistory);
		log.info("Login History is clicked");
		Thread.sleep(5000);

		Robot robot2 = new Robot();
		robot2.mouseWheel(6);

		WebElement download_history = driver.findElement(By.xpath("//div[@class='pShowMore']/a"));
		ExplicitWaitElement(download_history);
		js.executeScript("arguments[0].click();", download_history);
		log.info("Download History is clicked - File is Downloaded");

		WebElement dispLayout = driver.findElement(By.xpath("//*[@id=\"DisplayAndLayout_font\"]"));
		js.executeScript("arguments[0].click();", dispLayout);
		log.info("Display & Layout is clicked");
		Thread.sleep(5000);

		robot2.mouseWheel(6);
		WebElement custTabs = driver.findElement(By.xpath("//*[@id='CustomizeTabs_font']"));
		js.executeScript("arguments[0].click();", custTabs);
		log.info("Customize My Tabs is clicked");

		WebElement custApp = driver.findElement(By.xpath("//*[@id='p4']"));
		Select selectCustApp = new Select(custApp);
		selectCustApp.selectByVisibleText("Salesforce Chatter");

		WebElement availTabs = driver.findElement(By.xpath("//*[@id='duel_select_0']"));
		Select selectAvailTabs = new Select(availTabs);
		selectAvailTabs.selectByVisibleText("Reports");

		WebElement addToTab = driver.findElement(By.xpath("//*[@id='duel_select_0_right']/img"));
		ClickAction(addToTab, "Add to Selected Tabs");

		WebElement saveBtn = driver.findElement(By.xpath("//*[@id='bottomButtonRow']/input[1]"));
		ClickAction(saveBtn, "Save Button");
		log.info("Customized tab settings is saved");

		WebElement rightDropdown = driver.findElement(By.id("tsidButton"));
		ClickAction(rightDropdown, "Right Drop Down");

		WebElement salesforceChatter = driver.findElement(By.xpath("//div[@id='tsid-menuItems']/a[6]"));
		ClickAction(salesforceChatter, "Salesforce Chatter");

		WebElement reportsTab = driver.findElement(By.id("report_Tab"));
		Verify_ElementDisplay(reportsTab, "Reports Tab");
		log.info("Reports tab is added to Salesforce Chatter list");
		report.logTestInfo("Reports tab is added to Salesforce Chatter list");
	}

	@Test
	public void DevConsole_UserMenu_Option_TestScript_08() throws InterruptedException, AWTException { // Verifying

		loginToWeb();
		Thread.sleep(4000);

		UserMenuOptions();

		WebElement dev_console = driver.findElement(By.xpath("//*[@id='userNav-menuItems']/a[3]"));

		ClickAction(dev_console, "Developer Console");
		Thread.sleep(5000);

		String parent_window = driver.getWindowHandle();
		Set<String> all_windows = driver.getWindowHandles();

		for (String s : all_windows) {
			if (!s.equals(parent_window)) {
				driver.switchTo().window(s);
				driver.close();
				driver.switchTo().window(parent_window);
			}
		}
		log.info("Developer Console window is closed - TC passed");
		report.logTestInfo("Developer Console window is closed - TC passed");
	}

	@Test
	public void Logout_TestScript_09() throws InterruptedException { // Testing the log out scenario TC 09

		loginToWeb();

		String actualTitle1 = "Home Page ~ Salesforce - Developer Edition";
		String expectedTitle1 = getPageTitle(driver);

		Assert.assertEquals(actualTitle1, expectedTitle1);

		WebElement uMenu = driver.findElement(By.id("userNav-arrow")); // userNav"));
		ClickAction(uMenu, "User Menu");

		// userNavMenu
		WebElement sLogout = driver.findElement(By.linkText("Logout"));
		ClickAction(sLogout, "Logout");

		String actualTitle2 = "Login | Salesforce";
		Thread.sleep(3000);
		String expectedTitle2 = getPageTitle(driver);

		Assert.assertEquals(actualTitle2, expectedTitle2);
		log.info("Page title is matched, Test Case Passed");
		report.logTestInfo("Page title is matched, Test Case Passed");
	}
}
