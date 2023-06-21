package com.automation.bymodules;

import java.awt.AWTException;
import java.awt.Robot;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import comautomation.base.BaseTestSalesForce;

public class AccountAutomationScripts extends BaseTestSalesForce{

	@Test
	public void Create_Account_TestScript_10() throws AWTException, InterruptedException {
		loginToWeb();
		WebElement accounts = driver.findElement(By.xpath("//ul/li[6]/a"));
		ClickAction(accounts, "Accounts");
		Thread.sleep(4000);

		Close_SwitchTo_Lightning_PopUp(); // Closing the switch to lightning exp popup

		WebElement new_account = driver.findElement(By.xpath("//input[@name='new']"));
		ClickAction(new_account, "New");

		WebElement acc_Name = driver.findElement(By.xpath("//input[@id='acc2']"));
		enterKeys(acc_Name, "Account Name", "GC Account");
		WebElement acc_Type = driver.findElement(By.xpath("//select[@id='acc6']"));
		Select select_type = new Select(acc_Type);
		select_type.selectByVisibleText("Technology Partner");

		WebElement cust_priority = driver.findElement(By.xpath("//*[@id='00NDp00000CTEGD']"));
		Select select_priority = new Select(cust_priority);
		select_priority.selectByVisibleText("High");
		WebElement save_btn = driver.findElement(By.xpath("//input[@name='save']"));
		ClickAction(save_btn, "Save");

		WebElement acc_details = driver.findElement(By.xpath("//*[@id='ep']/div[1]/table/tbody/tr/td[1]/h2"));
		Verify_ElementDisplay(acc_details, "Account Details Page");
		log.info("Accounts Details page is displayed");
		report.logTestInfo("Accounts Details page is displayed");

	}

	@Test
	public void Account_New_View_TestScript_11() throws AWTException, InterruptedException {
		loginToWeb();
		WebElement accounts = driver.findElement(By.xpath("//ul/li[6]/a"));
		ClickAction(accounts, "Accounts");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Close_SwitchTo_Lightning_PopUp();

		WebElement create_newView = driver.findElement(By.xpath("//span[@class='fFooter']/a[2]"));
		ClickAction(create_newView, "Create New View");

		String actualTitle = " Accounts: Create New View ~ Salesforce - Developer Edition";
		String expectedTitle = getPageTitle(driver);
		Assert.assertEquals(actualTitle, expectedTitle);
		log.info("Create New View page is displayed");
		report.logTestInfo("Create New View page is displayed");

		WebElement viewName = driver.findElement(By.id("fname"));
		enterKeys(viewName, "View Name", "View7");
		WebElement view_uniqName = driver.findElement(By.id("devname"));
		enterKeys(view_uniqName, "View Unique Name", "");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Robot robot = new Robot();
		robot.mouseWheel(6);
		WebElement save_btn = driver.findElement(By.xpath("//*[@id='editPage']/div[3]/table/tbody/tr/td[2]/input[1]"));

		ExplicitWaitElement(save_btn);
		ClickAction(save_btn, "Save");

		WebElement updated_view = driver.findElement(By.name("fcf"));
		ExplicitWaitElement(updated_view);
		Select savedView = new Select(updated_view);
		driver.switchTo().activeElement();
		WebElement view_Displayed = savedView.getFirstSelectedOption();
		String updatedViewName = "View7";

		log.info("The updated View name is displayed in the UI:" + updatedViewName.equals(view_Displayed.getText()));
		report.logTestInfo("The updated View name is displayed in the UI");

	}

	@Test
	public void Edit_View_Script_12() throws AWTException, InterruptedException {

		loginToWeb();

		WebElement accounts = driver.findElement(By.xpath("//ul/li[6]/a"));
		ClickAction(accounts, "Accounts");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement select_view = driver.findElement(By.xpath("//*[@id='fcf']"));
		ExplicitWaitElement(select_view);
		Select selectView = new Select(select_view);
		// selectView.selectByIndex(3);
		selectView.getFirstSelectedOption();

		WebElement editView = driver
				.findElement(By.cssSelector("#filter_element > div > span > span.fFooter > a:nth-child(1)")); // .xpath("//div[@class='topNav
																												// primaryPalette']/div/div/a"));
		ExplicitWaitElement(editView);
		ClickAction(editView, "Edit");

		String actualTitle = "Accounts: Edit View ~ Salesforce - Developer Edition";
		String expectedTitle = getPageTitle(driver);
		Assert.assertEquals(actualTitle, expectedTitle);

		log.info("Accounts page Title Matched");
		report.logTestInfo("Accounts page title is matched.");

		WebElement newView_name = driver.findElement(By.id("fname")); // edit view name
		enterKeys(newView_name, "New View Name", "ViewUpdated");

		WebElement filter1_field = driver.findElement(By.id("fcol1"));
		ExplicitWaitElement(filter1_field);
		Select select_filter1 = new Select(filter1_field);
		select_filter1.selectByVisibleText("Account Name");

		Thread.sleep(3000);
		WebElement filter2_op = driver.findElement(By.id("fop1"));
		ExplicitWaitElement(filter2_op);
		ClickAction(filter2_op, "Filter Operator");
		Select select_filter2 = new Select(filter2_op);
		select_filter2.selectByVisibleText("contains");

		WebElement filter3_value = driver.findElement(By.id("fval1"));
		ExplicitWaitElement(filter3_value);
		enterKeys(filter3_value, "Value", "a");

		Robot robot = new Robot();
		robot.mouseWheel(7);

		WebElement save_btn = driver.findElement(By.xpath("//*[@id='editPage']/div[3]/table/tbody/tr/td[2]/input[1]"));
		ClickAction(save_btn, "Save Button");

		WebElement updated_view = driver.findElement(By.name("fcf"));
		ExplicitWaitElement(updated_view);
		Select savedView = new Select(updated_view);

		driver.switchTo().activeElement();
		WebElement view_Displayed = savedView.getFirstSelectedOption();
		String updatedViewName = "ViewUpdated";

		log.info("The updated View name is displayed in the UI:" + updatedViewName.equals(view_Displayed.getText()));
		report.logTestInfo("The updated View name is displayed in the UI");
	}

	@Test
	public void Merge_Accounts_TestScript_13() throws InterruptedException {
		loginToWeb();

		WebElement accounts = driver.findElement(By.xpath("//ul/li[@id='Account_Tab']/a"));
		ClickAction(accounts, "Accounts");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement mergeAcc = driver.findElement(By.xpath("//div[@class='toolsContentRight']/div/div/ul/li[4]/span/a"));
		ClickAction(mergeAcc, "Merge Accounts");

		WebElement findAcc = driver.findElement(By.id("srch"));
		enterKeys(findAcc, "Account", "GAC");

		WebElement findAccBtn = driver.findElement(By.name("srchbutton"));
		ClickAction(findAccBtn, "Find Accounts");
		Thread.sleep(3000);

		WebElement findAcc1 = driver.findElement(By.id("cid0"));
		FluentWaitElement(findAcc1);

		WebElement findAcc2 = driver.findElement(By.id("cid1"));
		FluentWaitElement(findAcc2);

		WebElement nextBtn = driver.findElement(By.name("goNext"));

		ClickAction(nextBtn, "Next Button");

		WebElement mergeBtn = driver.findElement(By.name("save"));
		FluentWaitElement(mergeBtn);
		ClickAction(mergeBtn, "Save Button");

		alert_accept();

		WebElement recAccount = driver.findElement(By.xpath("//th[@scope='row']/a"));
		String actualAccount = "GAC1";
		String expAccount = get_Text(recAccount);

		Assert.assertEquals(actualAccount, expAccount);
		log.info("Account is Merged and displayed in the Recent Account");
		report.logTestInfo("Account is Merged and displayed in the Recent Account");

	}

	@Test
	public void Create_Account_Report_TestScript_14() throws InterruptedException, AWTException {
		loginToWeb();

		WebElement accounts = driver.findElement(By.xpath("//ul/li[@id='Account_Tab']/a"));
		ClickAction(accounts, "Accounts");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement click_Report = driver.findElement(By.linkText("Accounts with last activity > 30 days"));
		ExplicitWaitElement(click_Report);
		ClickAction(click_Report, "Create Report");

		Thread.sleep(4000);
		WebElement from_date = driver.findElement(By.xpath("//*[@id=\"ext-gen152\"]"));

		FluentWaitElement(from_date);
		ClickAction(from_date, "From Date:");

		WebElement select_Fromdate = driver.findElement(By.cssSelector("#ext-gen276"));
		ClickAction(select_Fromdate, "Select From Date:");

		WebElement to_date = driver.findElement(By.xpath("//img[@id='ext-gen154']"));
		ExplicitWaitElement(to_date);
		ClickAction(to_date, "To Field");

		WebElement select_Todate = driver.findElement(By.xpath("//button[@id='ext-gen292']"));
		ExplicitWaitElement(select_Todate);
		ClickAction(select_Todate, "Select ToDate");

		WebElement saveBtn = driver.findElement(By.xpath("//button[@id='ext-gen49']"));
		ClickAction(saveBtn, "Save Button");

		WebElement repName = driver.findElement(By.id("saveReportDlg_reportNameField"));
		enterKeys(repName, "Report Name", "Report1");

		WebElement repUniqName = driver.findElement(By.id("saveReportDlg_DeveloperName"));
		enterKeys(repUniqName, "Report Unique Name", "Report1");

		WebElement saveReport = driver.findElement(By.cssSelector("#ext-gen312"));
		FluentWaitElement(saveReport);
		// ClickAction(saveReport, "Save Report");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click;", saveReport);

	}
}
