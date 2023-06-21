package com.automation.bymodules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import comautomation.base.BaseTestSalesForce;

public class ContactsAutomationScripts extends BaseTestSalesForce{
	@Test
	public void Create_NewContact_TestScript_25() throws InterruptedException {

		loginToWeb();

		WebElement contactTab = driver.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Contact_Tab']"));
		ClickAction(contactTab, "Contacts Tab");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement newBtn = driver.findElement(By.name("new"));
		ClickAction(newBtn, "New Contact");

		WebElement contactLastName = driver.findElement(By.id("name_lastcon2"));
		enterKeys(contactLastName, "Last Name", "GCC");

		WebElement accountName = driver.findElement(By.id("con4"));
		enterKeys(accountName, "Account Name", "GC Account");

		WebElement saveBtn = driver.findElement(By.name("save"));
		ClickAction(saveBtn, "Save Button");

		WebElement contactName = driver.findElement(By.xpath("//*[@id='contactHeaderRow']/div[2]/h2"));
		String topName = contactName.getText();
		String actualTitle1 = "Contact: " + topName + " ~ Salesforce - Developer Edition";
		String expectedTitle1 = getPageTitle(driver);
		Assert.assertEquals(actualTitle1, expectedTitle1);
		log.info("New Contact is created");
		report.logTestInfo("New contact is created and displayed with details");
	}

	@Test
	public void Create_NewViewFor_Contact_TestScript_26() throws InterruptedException {

		loginToWeb();

		WebElement contactTab = driver.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Contact_Tab']"));
		ClickAction(contactTab, "Contact Tab");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement createNewView = driver.findElement(By.xpath("//div[@class='bFilterView']/span/span[2]/a[2]"));
		ClickAction(createNewView, "Create New View");

		WebElement viewName = driver.findElement(By.id("fname"));
		enterKeys(viewName, "View Name", "TestView1");

		WebElement viewUniqName = driver.findElement(By.id("devname"));
		enterKeys(viewUniqName, "View Unique Name", "TestView1");

		WebElement saveBtn = driver.findElement(By.name("save"));
		ClickAction(saveBtn, "Save Button");

		WebElement contactView = driver.findElement(By.name("fcf"));
		Select selView = new Select(contactView);
		String expectedtedView = selView.getFirstSelectedOption().getText();
		String actualView = "TestView1";

		Assert.assertEquals(actualView, expectedtedView);
		log.info("Created view is selected by default");
		report.logTestInfo("Created view is selected by default");
	}

	@Test
	public void Recent_Contacts_TestScript_27() throws InterruptedException {

		loginToWeb();

		WebElement contactTab = driver.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Contact_Tab']"));
		ClickAction(contactTab, "Contact Tab");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement recentContact = driver
				.findElement(By.cssSelector("tr.dataRow:nth-child(2) > th:nth-child(1) > a:nth-child(1)"));

		String expectedtedView = recentContact.getText();
		String actualView = "GCC";

		Assert.assertEquals(actualView, expectedtedView);
		log.info("Recent Contact is displayed under Recent Contacts");
		report.logTestInfo("Recent Contact is displayed under Recent Contacts");
	}

	@Test
	public void MyContacts_View_TestScript_28() throws InterruptedException {

		loginToWeb();

		WebElement contactTab = driver.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Contact_Tab']"));
		ClickAction(contactTab, "Contact Tab");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement contactView = driver.findElement(By.name("fcf"));
		Select selView = new Select(contactView);
		selView.selectByVisibleText("My Contacts");

		WebElement goBtn = driver.findElement(By.name("go"));
		ClickAction(goBtn, "Go Button");
		String expectedtedView = getPageTitle(driver);
		String actualView = "Contacts ~ Salesforce - Developer Edition";

		Assert.assertEquals(actualView, expectedtedView);
		log.info("My Contacts view is displayed.");
		report.logTestInfo("My Contacts view is displayed.");
	}

	@Test
	public void View_Contact_Details_FromView_TestScript_29() throws InterruptedException {

		loginToWeb();

		WebElement contactTab = driver.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Contact_Tab']"));
		ClickAction(contactTab, "Contact Tab");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement recentContact = driver
				.findElement(By.cssSelector("tr.dataRow:nth-child(2) > th:nth-child(1) > a:nth-child(1)"));

		ClickAction(recentContact, "Recent Contact");
		WebElement contactDetails = driver.findElement(By.xpath("//h2[contains(text(),'Contact Detail')]"));
		FluentWaitElement(contactDetails);
		String expectedText = contactDetails.getText();
		String actualText = "Contact Detail";

		Assert.assertEquals(actualText, expectedText);
		log.info("Selected Contact is displayed with details");
		report.logTestInfo("Selected Contact is displayed with details");
	}

	@Test
	public void Create_NewView_ErrorMessage_TestScript_30() throws InterruptedException {

		loginToWeb();

		WebElement contactTab = driver.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Contact_Tab']"));
		ClickAction(contactTab, "Contact Tab");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement createNewView = driver.findElement(By.xpath("//div[@class='bFilterView']/span/span[2]/a[2]"));
		ClickAction(createNewView, "Create New View");

		WebElement viewUniqName = driver.findElement(By.id("devname"));
		enterKeys(viewUniqName, "View Unique Name", "TestView1");

		WebElement saveBtn = driver.findElement(By.name("save"));
		ClickAction(saveBtn, "Save Button");

		WebElement errorMsg = driver.findElement(By.xpath("//div[@class='errorMsg']"));
		String expectedErrMessage = errorMsg.getText();
		String actualErrMessage = "Error: You must enter a value";

		Assert.assertEquals(actualErrMessage, expectedErrMessage);
		log.info("Error Message is displayed");
		report.logTestInfo("Error Message is displayed");
	}

	@Test
	public void Cancel_Button_Create_NewView_TestScript_31() throws InterruptedException {

		loginToWeb();

		WebElement contactTab = driver.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Contact_Tab']"));
		ClickAction(contactTab, "Contact Tab");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement createNewView = driver.findElement(By.xpath("//div[@class='bFilterView']/span/span[2]/a[2]"));
		ClickAction(createNewView, "Create New View");

		WebElement viewName = driver.findElement(By.id("fname"));
		enterKeys(viewName, "View Name", "ABCD");

		WebElement viewUniqName = driver.findElement(By.id("devname"));
		enterKeys(viewUniqName, "View Unique Name", "EFGH");

		WebElement cancelBtn = driver.findElement(By.name("cancel"));
		ClickAction(cancelBtn, "Cancel Button");

		WebElement contactView = driver.findElement(By.name("fcf"));
		Select selView = new Select(contactView);
		String expectedtedView = selView.getFirstSelectedOption().getText();
		String actualView = "ABCD";

		Assert.assertNotEquals(actualView, expectedtedView, "Cancel Button works good, view not created");
		log.info("Cancel button works fine, view not created");
		report.logTestInfo("Cancel button works fine, view not created");
	}

	@Test
	public void Create_NewContact_TestScript_32() throws InterruptedException {

		loginToWeb();

		WebElement contactTab = driver.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Contact_Tab']"));
		ClickAction(contactTab, "Contacts Tab");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement newBtn = driver.findElement(By.name("new"));
		ClickAction(newBtn, "New Contact");

		WebElement contactFirstName = driver.findElement(By.id("name_firstcon2"));
		enterKeys(contactFirstName, "First Name", "xyz");

		WebElement contactLastName = driver.findElement(By.id("name_lastcon2"));
		enterKeys(contactLastName, "Last Name", "Indian");

		WebElement accountName = driver.findElement(By.id("con4"));
		enterKeys(accountName, "Account Name", "Global Media");

		WebElement saveNewBtn = driver.findElement(By.name("save_new"));
		ClickAction(saveNewBtn, "Save & New Button");

		String actualTitle1 = "Contact Edit: New Contact ~ Salesforce - Developer Edition";
		String expectedTitle1 = getPageTitle(driver);
		Assert.assertEquals(actualTitle1, expectedTitle1);
		log.info("Contact Edit: New Contact page is displayed");
		report.logTestInfo("Contact Edit: New Contact page is displayed");
	}
}
