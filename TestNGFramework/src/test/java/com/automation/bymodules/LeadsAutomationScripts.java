package com.automation.bymodules;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import comautomation.base.BaseTestSalesForce;

public class LeadsAutomationScripts extends BaseTestSalesForce {
	@Test
	public void Lead_HomePage_TestScript_20() throws InterruptedException {

		loginToWeb();

		WebElement Leads = driver.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Lead_Tab']"));
		ClickAction(Leads, "Leads");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		String actualTitle = "Leads: Home ~ Salesforce - Developer Edition";
		String expTitle = getPageTitle(driver);

		Assert.assertEquals(actualTitle, expTitle);
		log.info("Page title is matched");
	}

	@Test
	public void Lead_Dropdown_ListContents_TestScript_21() throws InterruptedException {

		loginToWeb();

		WebElement Leads = driver.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Lead_Tab']"));
		ClickAction(Leads, "Leads");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement leadsView = driver.findElement(By.id("fcf"));
		Select selView = new Select(leadsView);
		List<WebElement> allLeadViews = selView.getOptions();

		String[] allViews = { "All Open Leads", "My Unread Leads", "Recently Viewed Leads", "Today's Leads",
				"View - Custom1", "View - Custom2" };

		int i = 0;
		for (WebElement item : allLeadViews) {
			if (item.getText().equalsIgnoreCase(allViews[i]))
				log.info(allViews[i] + " is displayed");
			else
				log.info(allViews[i] + " is NOT displayed");
			i++;
		}
	}

	@Test
	public void Lead_GoButton_TestScript_22() throws InterruptedException {

		loginToWeb();

		WebElement Leads = driver.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Lead_Tab']"));
		ClickAction(Leads, "Leads");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement leadsView = driver.findElement(By.id("fcf"));
		Select selView = new Select(leadsView);
		selView.selectByVisibleText("My Unread Leads");

		String actualTitle = "Leads: Home ~ Salesforce - Developer Edition";
		String expTitle = getPageTitle(driver);

		Assert.assertEquals(actualTitle, expTitle);
		log.info("Page title is matched for My Unread Leads");

		WebElement uMenu = driver.findElement(By.id("userNav-arrow")); // userNav"));
		ClickAction(uMenu, "User Menu");

		// userNavMenu
		WebElement sLogout = driver.findElement(By.linkText("Logout"));
		ClickAction(sLogout, "Logout");

		String actualTitle2 = "Login | Salesforce";
		Thread.sleep(3000);
		String expectedTitle2 = getPageTitle(driver);

		Assert.assertEquals(actualTitle2, expectedTitle2);
		log.info("Page title is matched - user is logged out");

		validLogin();
		WebElement btn_Login = driver.findElement(By.name("Login"));
		ClickAction(btn_Login, "Login");

		WebElement Leads1 = driver.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Lead_Tab']"));
		ClickAction(Leads1, "Leads");

		Thread.sleep(2000);

		WebElement leadsView1 = driver.findElement(By.id("fcf"));
		Select selView1 = new Select(leadsView1);
		String expectedView1 = selView1.getFirstSelectedOption().getText();
		String selectedView1 = "My Unread Leads";

		Assert.assertEquals(expectedView1, selectedView1);
		log.info("View is matched for the Leads selected during the last session");

	}

	@Test
	public void Todays_Lead_TestScript_23() throws InterruptedException {

		loginToWeb();

		WebElement Leads = driver.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Lead_Tab']"));
		ClickAction(Leads, "Leads");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement leadsView = driver.findElement(By.id("fcf"));
		Select selView = new Select(leadsView);
		selView.selectByVisibleText("Today's Leads");

		WebElement goBtn = driver.findElement(By.name("go"));
		ClickAction(goBtn, "Go Button");

		String actualTitle = "Leads ~ Salesforce - Developer Edition";
		String expTitle = getPageTitle(driver);

		Assert.assertEquals(actualTitle, expTitle);
		log.info("Page title is matched for Today's Leads");

	}

	@Test
	public void NewButton_Lead_TestScript_24() throws InterruptedException {

		loginToWeb();

		WebElement Leads = driver.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Lead_Tab']"));
		ClickAction(Leads, "Leads");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement leadsView = driver.findElement(By.id("fcf"));
		Select selView = new Select(leadsView);
		selView.selectByVisibleText("Today's Leads");

		WebElement goBtn = driver.findElement(By.name("go"));
		ClickAction(goBtn, "Go Button");

		WebElement newBtn = driver.findElement(By.name("new"));
		ClickAction(newBtn, "New Button");

		String actualTitle = "Lead Edit: New Lead ~ Salesforce - Developer Edition";
		String expTitle = getPageTitle(driver);

		Assert.assertEquals(actualTitle, expTitle);
		log.info("New Lead page is loaded");
		report.logTestInfo("New Lead page is loaded");
		// Add info

		WebElement leadLastName = driver.findElement(By.id("name_lastlea2"));
		enterKeys(leadLastName, "Last Name", "ABCD");

		WebElement leadCompany = driver.findElement(By.id("lea3"));
		enterKeys(leadCompany, "Last Name", "ABCD");

		WebElement saveBtn = driver.findElement(By.name("save"));
		ClickAction(saveBtn, "Save Button");

		WebElement leadName = driver.findElement(By.xpath("//*[@id='contactHeaderRow']/div[2]/h2"));
		String topName = leadName.getText();

		String actualTitle1 = "Lead: " + topName + " ~ Salesforce - Developer Edition";
		String expectedTitle1 = getPageTitle(driver);
		Assert.assertEquals(actualTitle1, expectedTitle1);
		log.info("New Lead is created");
		report.logTestInfo("New Lead is created and displayed with details");

	}
}
