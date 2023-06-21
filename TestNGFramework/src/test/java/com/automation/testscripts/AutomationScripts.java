package com.automation.testscripts;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Properties;
import java.awt.AWTException;
import org.openqa.selenium.By;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import comautomation.base.BaseTestSalesForce;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import java.awt.datatransfer.StringSelection;
import com.automation.utility.PropertiesUtility;
import com.automation.utility.Log4JUtility;

public class AutomationScripts extends BaseTestSalesForce {

	public PropertiesUtility propUtility = new PropertiesUtility();
	public Properties propertyFile = propUtility.loadFile("salesforceproperties");
	String username = propUtility.getPropertyValue("sf.valid.username");
	String password = propUtility.getPropertyValue("sf.valid.password");

	public String invalidUsername = propUtility.getPropertyValue("sf.invalid.password");
	public String invalidPassword = propUtility.getPropertyValue("sf.invalid.password");
	public String emailForgotPassword = propUtility.getPropertyValue("sf.forgot.password");
	public Properties dataPropFile = propUtility.loadFile("salesforcedataproperties");

	public Log4JUtility logObj = Log4JUtility.getInstance();

	protected Logger log = logObj.getLogger();

	@Test
	public void Login_Error_Message_TestScript_01() {

		WebElement uName = driver.findElement(By.id("username"));
		enterKeys(uName, "username", username);

		WebElement btn_Login = driver.findElement(By.name("Login"));
		ClickAction(btn_Login, "Login");

		WebElement login_Error = driver.findElement(By.id("error"));
		String expected_ErrorMessage = "Please enter your password.";

		String actual_ErrorMessage = get_Text(login_Error);
		Assert.assertEquals(actual_ErrorMessage, expected_ErrorMessage);

	}

	@Test
	public void Success_login_TestScript_02() {

		validLogin();

		RememberMeLogin(false);
		WebElement btn_Login = driver.findElement(By.name("Login"));
		ClickAction(btn_Login, "Login");

		String actualTitle1 = "Home Page ~ Salesforce - Developer Edition";
		String expectedTitle1 = getPageTitle(driver);

		Assert.assertEquals(actualTitle1, expectedTitle1);
		report.logTestInfo("Test Case passed");

	}

	@Test
	public void Success_login_RememberMe_TestScript_03() throws InterruptedException { // test case 3: 'Check
																						// RemeberMe - 3'

		String actualTitle = "Login | Salesforce";
		String expectedTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);

		validLogin();

		RememberMeLogin(true);

		WebElement btn_Login = driver.findElement(By.name("Login"));
		ClickAction(btn_Login, "Login");

		String actualTitle1 = "Home Page ~ Salesforce - Developer Edition";
		String expectedTitle1 = getPageTitle(driver);
		Assert.assertEquals(actualTitle1, expectedTitle1);

		WebElement uMenu = driver.findElement(By.id("userNav-arrow")); // userNav"));
		ClickAction(uMenu, "User Menu");

		// userNavMenu
		WebElement sLogout = driver.findElement(By.linkText("Logout"));
		ClickAction(sLogout, "Logout");

		Thread.sleep(4000);
		WebElement uNameValue = driver
				.findElement(By.xpath("//div[@id='idcard-container']//following::input[@id='username']"));

		String userValue = uNameValue.getAttribute("value");
		// System.out.println(userValue);

		if (userValue.equals(username)) {
			log.info("User Name is entered in the text box - application remembered the username");
		} else
			log.error("Applicaion doesnt remember the username");

		WebElement RememberMe_Checkbox1 = driver.findElement(By.xpath("//*[@id='rememberUn']"));

		if (RememberMe_Checkbox1.isSelected())
			log.info("The Remember me check box is checked.");
		else
			log.error("The Remember me check box is NOT checked.");

	}

	@Test
	public void Forgot_Password_TestScript__4A() {

		String actualTitle = "Login | Salesforce";
		String expectedTitle = getPageTitle(driver);
		Assert.assertEquals(actualTitle, expectedTitle);

		WebElement ForgotPassword_Link = driver.findElement(By.id("forgot_password_link"));
		ClickAction(ForgotPassword_Link, "Forgot Password");

		String actualTitle1 = "Forgot Your Password | Salesforce";
		String expectedTitle1 = getPageTitle(driver);
		Assert.assertEquals(actualTitle1, expectedTitle1);

		WebElement userName = driver.findElement(By.id("un"));
		enterKeys(userName, "ForgotPassword_UserName", emailForgotPassword);

		WebElement Continue_Button = driver.findElement(By.id("continue"));
		ClickAction(Continue_Button, "Continue");

		String actualTitle2 = "Check Your Email | Salesforce";
		String expectedTitle2 = getPageTitle(driver);
		Assert.assertEquals(actualTitle2, expectedTitle2);
	}

	@Test
	public void Forgot_Password_TestScript_4B() {

		String actualTitle = "Login | Salesforce";
		String expectedTitle = getPageTitle(driver);
		Assert.assertEquals(actualTitle, expectedTitle);

		WebElement uName = driver.findElement(By.id("username"));
		enterKeys(uName, "username", invalidUsername);

		WebElement passWord = driver.findElement(By.name("pw"));
		enterKeys(passWord, "password", invalidPassword);

		RememberMeLogin(false);
		WebElement btn_Login = driver.findElement(By.name("Login"));
		ClickAction(btn_Login, "Login");

		String error_message = "Please check your username and password. If you still can't log in, contact your Salesforce administrator.";
		WebElement forgot_PasswordError = driver.findElement(By.id("error"));
		ExplicitWaitElement(forgot_PasswordError);

		if (forgot_PasswordError.isDisplayed())
			log.info("Error Message displayed as " + error_message);
		else
			log.error("Error Message is NOT displayed");
	}

	@Test
	public void User_Menu_Dropdown_TestScript_05() throws InterruptedException {

		loginToWeb();
		Thread.sleep(4000);

		UserMenuOptions();
	}

	@Test(dataProvider = "sfdatatc6", dataProviderClass = BaseTestSalesForce.class, enabled = false)
	public void MyProfile_UserMenu_Option_TestScript_06(String lname, String postMsg)
			throws InterruptedException, AWTException {

		loginToWeb();

		UserMenuOptions();

		WebElement My_Profile = driver.findElement(By.xpath("//*[@id='userNav-menuItems']/a[1]"));

		ClickAction(My_Profile, "My Profile");
		Thread.sleep(4000);

		WebElement editPenElement = driver
				.findElement(By.xpath("//div[@class='editPen']/a[@class='contactInfoLaunch editLink']"));
		ExplicitWaitElement(editPenElement);

		ClickAction(editPenElement, "editPen");

		Thread.sleep(2000);
		WebElement frameElement = driver.findElement(By.xpath("//iframe[@id='contactInfoContentId']"));
		driver.switchTo().frame(frameElement);
		System.out.println("Switched to frame");

		WebElement aboutTab = driver.findElement(By.xpath("//li[@id='aboutTab']/a"));
		ExplicitWaitElement(aboutTab);

		ClickAction(aboutTab, "aboutTab");

		WebElement lastName = driver.findElement(By.id("lastName"));
		ExplicitWaitElement(lastName);
		lastName.clear();
		lastName.sendKeys("AChellapandi");
		System.out.println("Last name changed");

		WebElement saveAll = driver.findElement(By.xpath("//input[@value='Save All']"));
		ClickAction(saveAll, "saveAll");

		driver.switchTo().defaultContent();

		Thread.sleep(3000);
		WebElement postLink = driver.findElement(By.xpath("//div[@id='profileFeed']//li[@label='TextPost']")); // .xpath("//*[@id=\"publisherAttachTextPost\"]"));

		postLink.click();

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		System.out.println("on it");

		WebElement post_frame = driver.findElement(By.xpath("//iframe[@class ='cke_wysiwyg_frame cke_reset']"));

		// xpath("//*[@id='cke_43_contents']/iframe"));

		ClickAction(post_frame, "Post Clicking");

		Actions actions = new Actions(driver);

		actions.sendKeys("Hi everyone!").build().perform();

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		WebElement shareButton = driver.findElement(By.id("publishersharebutton"));

		ClickAction(shareButton, "Message Posted");

		WebElement fileElement = driver.findElement(By.cssSelector("#publisherAttachContentPost > span:nth-child(2)"));

		Thread.sleep(3000);

		ClickAction(fileElement, "File");

		WebElement uploadfileElement = driver.findElement(By.xpath("//a[@id='chatterUploadFileAction']"));

		ClickAction(uploadfileElement, "Upload File");

		WebElement browseButton = driver.findElement(By.xpath("//input[@id='chatterFile']"));
		Thread.sleep(2000);
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js2.executeScript("arguments[0].click();", browseButton); // javascript notation to perform click

		Robot robot = new Robot();
		robot.delay(2000);

		// copy the path of the file in clip board using StringSelection class and
		// ToolKit class
		StringSelection ss = new StringSelection(
				"C:\\Users\\geeth\\OneDrive\\Documents\\TekArch\\Instructions\\Jenkins.txt");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

		robot.delay(2000);

		// CTRL-V
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);

		robot.delay(2000);

		// ENTER(open)
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		System.out.println("Uploaded file");

		WebElement uploadPhotoElement = driver.findElement(By.xpath("//div[@id='photoSection']//img[1]"));
		ExplicitWaitElement(uploadPhotoElement);

		Actions action = new Actions(driver);
		action.moveToElement(uploadPhotoElement).build().perform();
		Thread.sleep(2000);
		uploadPhotoElement.click();

		WebElement addPhotoElement = driver.findElement(By.id("uploadLink"));
		ExplicitWaitElement(addPhotoElement);
		ClickAction(addPhotoElement, "Add Photo");

		driver.switchTo().frame(2);
		System.out.println("Switched to Upload profile photo frame");

		WebElement browsePhotoElement = driver.findElement(By.id("j_id0:uploadFileForm:uploadInputFile"));
		Thread.sleep(2000);
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", browsePhotoElement); // javascript notation to perform click

		Robot robot1 = new Robot();
		robot.delay(2000);

		// copy the path of the file in clip board using StringSelection class and
		// ToolKit class
		StringSelection s = new StringSelection(
				"C:\\Users\\geeth\\OneDrive\\Pictures\\Camera Roll\\20230415_163507.jpg");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

		robot1.delay(2000);

		// CTRL-V
		robot1.keyPress(KeyEvent.VK_CONTROL);
		robot1.keyPress(KeyEvent.VK_V);
		robot1.keyRelease(KeyEvent.VK_CONTROL);
		robot1.keyRelease(KeyEvent.VK_V);

		robot1.delay(2000);

		// ENTER(open)
		robot1.keyPress(KeyEvent.VK_ENTER);
		robot1.keyRelease(KeyEvent.VK_ENTER);

		WebElement savePhotoElement = driver.findElement(By.id("j_id0:uploadFileForm:uploadBtn"));
		ExplicitWaitElement(savePhotoElement);
		ClickAction(savePhotoElement, "save photo");

		WebElement savePhoto = driver.findElement(By.id("j_id0:j_id7:save"));
		ExplicitWaitElement(savePhoto);
		ClickAction(savePhoto, "saved photo");

	}

	@Test(enabled = false)
	public void MySettings_UserMenu_Option_TestScript_07() throws InterruptedException, AWTException { // Verifying
		String senderName = propUtility.getPropertyValue("email.name");

		loginToWeb();
		Thread.sleep(4000);

		UserMenuOptions();

		WebElement MySettings = driver.findElement(By.xpath("//*[@id='userNav-menuItems']/a[2]"));

		ClickAction(MySettings, "My Settings");
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
		ExplicitWaitElement(rightDropdown);
		ClickAction(rightDropdown, "Right Drop Down");

		WebElement salesforceChatter = driver.findElement(By.xpath("//div[@id='tsid-menuItems']/a[6]"));
		ExplicitWaitElement(salesforceChatter);
		ClickAction(salesforceChatter, "Salesforce Chatter");
		Close_SwitchTo_Lightning_PopUp();

		WebElement reportsTab = driver.findElement(By.id("report_Tab"));
		Verify_ElementDisplay(reportsTab, "Reports Tab");
		log.info("Reports tab is added to Salesforce Chatter list");
		report.logTestInfo("Reports tab is added to Salesforce Chatter list");
		UserMenuOptions();

		WebElement MySettings1 = driver.findElement(By.xpath("//*[@id='userNav-menuItems']/a[2]"));

		ClickAction(MySettings1, "My Settings");
		Thread.sleep(3000);

		WebElement Email = driver.findElement(By.xpath("//div[@id='EmailSetup']"));
		ExplicitWaitElement(Email);
		ClickAction(Email, "Email");

		WebElement myEmailSettings = driver.findElement(By.xpath("//span[@id='EmailSettings_font']"));
		ExplicitWaitElement(myEmailSettings);
		ClickAction(myEmailSettings, "My Email Settings");

		WebElement emailName = driver.findElement(By.xpath("//input[@id='sender_name']"));
		ExplicitWaitElement(emailName);
		enterKeys(emailName, "Sender Name", senderName);

		WebElement emailAddress = driver.findElement(By.xpath("//input[@id='sender_email']"));
		ExplicitWaitElement(emailAddress);
		enterKeys(emailAddress, "Email Id", username);

		WebElement bccRadioButton = driver.findElement(By.xpath("//input[@id='auto_bcc1']"));
		ExplicitWaitElement(bccRadioButton);
		ClickAction(bccRadioButton, "Bcc RadioButton");

		WebElement saveEmailSettings = driver.findElement(By.xpath("//td[@id='bottomButtonRow']/input[1]"));
		ExplicitWaitElement(saveEmailSettings);
		ClickAction(saveEmailSettings, "Save Email Settings");

		String expectedSuccessfulMessage = "Your settings have been successfully saved.";
		WebElement successMessage = driver.findElement(By.xpath("//div[@class='messageText']"));
		ExplicitWaitElement(successMessage);
		Assert.assertEquals(successMessage.getText(), expectedSuccessfulMessage, "Message failed");
		System.out.println("Message matched");

		WebElement calendarRemainder = driver.findElement(By.xpath("//span[@id='CalendarAndReminders_font']"));
		ExplicitWaitElement(calendarRemainder);
		ClickAction(calendarRemainder, "Calendar and Remainder");

		WebElement activityRemainders = driver.findElement(By.xpath("//span[@id='Reminders_font']"));
		ExplicitWaitElement(activityRemainders);
		ClickAction(activityRemainders, "ActivityRemainders");

		WebElement openTestRemainder = driver.findElement(By.xpath("//input[@id='testbtn']"));
		ExplicitWaitElement(openTestRemainder);
		ClickAction(openTestRemainder, "Open Test Remainder");

		String parent_window = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();

		for (String s : allWindows)

			// System.out.println(a);
			if (!parent_window.equals(s)) {
				driver.switchTo().window(s);
				WebElement sampleEvent = driver.findElement(By.xpath("//*[@id='summary0']/div/text()"));
				Verify_ElementDisplay(sampleEvent, "Sample Event");
				driver.close();
			}

		driver.switchTo().window(parent_window);
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

	@Test
	public void Create_Account_TestScript_10() throws AWTException, InterruptedException {
		String accNameProp = propUtility.getPropertyValue("acc.name");
		String accTypeProp = propUtility.getPropertyValue("acc.type");
		String custPriority = propUtility.getPropertyValue("cust.priority");

		loginToWeb();
		WebElement accounts = driver.findElement(By.xpath("//ul/li[@id='Account_Tab']/a"));
		if (!accounts.isDisplayed()) {
			addTab("Accounts");

		} else {

			ClickAction(accounts, "Accounts");
		}
		Thread.sleep(4000);

		Close_SwitchTo_Lightning_PopUp(); // Closing the switch to lightning exp popup

		WebElement new_account = driver.findElement(By.xpath("//input[@name='new']"));
		ClickAction(new_account, "New");

		WebElement acc_Name = driver.findElement(By.xpath("//input[@id='acc2']"));
		enterKeys(acc_Name, "Account Name", accNameProp);
		WebElement acc_Type = driver.findElement(By.xpath("//select[@id='acc6']"));
		Select select_type = new Select(acc_Type);
		select_type.selectByVisibleText(accTypeProp);

		WebElement cust_priority = driver.findElement(By.xpath("//*[@id='00NDp00000CTEGD']"));
		Select select_priority = new Select(cust_priority);
		select_priority.selectByVisibleText(custPriority);
		WebElement save_btn = driver.findElement(By.xpath("//input[@name='save']"));
		ClickAction(save_btn, "Save");

		WebElement acc_details = driver.findElement(By.xpath("//*[@id='ep']/div[1]/table/tbody/tr/td[1]/h2"));
		Verify_ElementDisplay(acc_details, "Account Details Page");
		log.info("Accounts Details page is displayed");
		report.logTestInfo("Accounts Details page is displayed");

	}

	@Test
	public void Account_New_View_TestScript_11() throws AWTException, InterruptedException {
		String accViewProp = propUtility.getPropertyValue("acc.view");
		String accUniqViewProp = propUtility.getPropertyValue("acc.uniqview");
		String updatedViewName = propUtility.getPropertyValue("updated.view");

		loginToWeb();
		WebElement accounts = driver.findElement(By.xpath("//ul/li[@id='Account_Tab']/a"));
		ClickAction(accounts, "Accounts");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Close_SwitchTo_Lightning_PopUp();

		WebElement create_newView = driver.findElement(By.xpath("//span[@class='fFooter']/a[2]"));
		ExplicitWaitElement(create_newView);
		ClickAction(create_newView, "Create New View");

		String actualTitle = "Accounts: Create New View ~ Salesforce - Developer Edition";
		String expectedTitle = getPageTitle(driver);
		Assert.assertEquals(actualTitle, expectedTitle);
		log.info("Create New View page is displayed");
		report.logTestInfo("Create New View page is displayed");

		WebElement viewName = driver.findElement(By.id("fname"));
		enterKeys(viewName, "View Name", accViewProp);
		WebElement view_uniqName = driver.findElement(By.id("devname"));
		enterKeys(view_uniqName, "View Unique Name", accUniqViewProp);

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
		Thread.sleep(2500);
		Select savedView = new Select(updated_view);
		driver.switchTo().activeElement();
		WebElement view_Displayed = savedView.getFirstSelectedOption();

		log.info("The updated View name is displayed in the UI:" + updatedViewName.equals(view_Displayed.getText()));
		report.logTestInfo("The updated View name is displayed in the UI");

	}

	@Test
	public void Edit_View_Script_12() throws AWTException, InterruptedException {

		String newViewName = propUtility.getPropertyValue("new.view.name");
		String viewFilter1 = propUtility.getPropertyValue("view.filter1");
		String viewFilter2 = propUtility.getPropertyValue("view.filter2");
		String viewFilter3 = propUtility.getPropertyValue("view.filter3");
		String updatedViewName = propUtility.getPropertyValue("updated.view.name");

		loginToWeb();

		WebElement accounts = driver.findElement(By.xpath("//ul/li[@id='Account_Tab']/a"));
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
		enterKeys(newView_name, "New View Name", newViewName);

		WebElement filter1_field = driver.findElement(By.id("fcol1"));
		ExplicitWaitElement(filter1_field);
		Select select_filter1 = new Select(filter1_field);
		select_filter1.selectByVisibleText(viewFilter1);

		Thread.sleep(3000);
		WebElement filter2_op = driver.findElement(By.id("fop1"));
		ExplicitWaitElement(filter2_op);
		ClickAction(filter2_op, "Filter Operator");
		Select select_filter2 = new Select(filter2_op);
		select_filter2.selectByVisibleText(viewFilter2);

		WebElement filter3_value = driver.findElement(By.id("fval1"));
		ExplicitWaitElement(filter3_value);
		enterKeys(filter3_value, "Value", viewFilter3);

		Robot robot = new Robot();
		robot.mouseWheel(7);

		WebElement save_btn = driver.findElement(By.xpath("//*[@id='editPage']/div[3]/table/tbody/tr/td[2]/input[1]"));
		ClickAction(save_btn, "Save Button");

		WebElement updated_view = driver.findElement(By.name("fcf"));
		ExplicitWaitElement(updated_view);
		Select savedView = new Select(updated_view);

		driver.switchTo().activeElement();
		WebElement view_Displayed = savedView.getFirstSelectedOption();

		log.info("The updated View name is displayed in the UI:" + updatedViewName.equals(view_Displayed.getText()));
		report.logTestInfo("The updated View name is displayed in the UI");
	}

	@Test
	public void Merge_Accounts_TestScript_13() throws InterruptedException {
		String findAccName = propUtility.getPropertyValue("find.acc.name");
		String actualAccount = propUtility.getPropertyValue("actual.Account13");

		loginToWeb();

		WebElement accounts = driver.findElement(By.xpath("//ul/li[@id='Account_Tab']/a"));
		ClickAction(accounts, "Accounts");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement mergeAcc = driver.findElement(By.xpath("//div[@class='toolsContentRight']/div/div/ul/li[4]/span/a"));
		ClickAction(mergeAcc, "Merge Accounts");

		WebElement findAcc = driver.findElement(By.id("srch"));
		enterKeys(findAcc, "Account", findAccName);

		WebElement findAccBtn = driver.findElement(By.name("srchbutton"));
		ClickAction(findAccBtn, "Find Accounts");
		Thread.sleep(3000);

		WebElement findAcc1 = driver.findElement(By.id("cid0"));
		FluentWaitElement(findAcc1);
		ClickAction(findAcc1, "account1");

		WebElement findAcc2 = driver.findElement(By.id("cid1"));
		FluentWaitElement(findAcc2);
		ClickAction(findAcc2, "account2");

		WebElement nextBtn = driver.findElement(By.name("goNext"));
		Thread.sleep(3000);
		ClickAction(nextBtn, "Next Button");

		WebElement mergeBtn = driver.findElement(By.name("save"));
		FluentWaitElement(mergeBtn);
		ClickAction(mergeBtn, "Save Button");

		alert_accept();

		WebElement recAccount = driver.findElement(By.xpath("//th[@scope='row']/a"));
		String expAccount = get_Text(recAccount);

		Assert.assertEquals(actualAccount, expAccount);
		log.info("Account is Merged and displayed in the Recent Account");
		report.logTestInfo("Account is Merged and displayed in the Recent Account");

	}

	@Test
	public void Create_Account_Report_TestScript_14() throws InterruptedException, AWTException {
		String reportName = propUtility.getPropertyValue("report.name");
		String reportUniqName = propUtility.getPropertyValue("report.uniq.name");

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
		enterKeys(repName, "Report Name", reportName);

		WebElement repUniqName = driver.findElement(By.id("saveReportDlg_DeveloperName"));
		enterKeys(repUniqName, "Report Unique Name", reportUniqName);

		WebElement saveReport = driver.findElement(By.cssSelector("#ext-gen312"));
		FluentWaitElement(saveReport);
		// ClickAction(saveReport, "Save Report");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click;", saveReport);

	}

	public void NoSuchElement() {
		log.info("no such element exception is found...");
	}

	@Test
	public void opportunities_TestScript_15() throws InterruptedException, AWTException {

		loginToWeb();

		WebElement opportunites = driver.findElement(By.xpath("//*[@id='Opportunity_Tab']/a")); 
		try {
			Thread.sleep(2000);
			if (opportunites.isDisplayed())
				ClickAction(opportunites, "Opportunites");
			else
				throw new NoSuchElementException();
		} catch (NoSuchElementException e) {

			addTab("Opportunites");
		}

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement select_view = driver.findElement(By.xpath("//*[@id='fcf']"));
		FluentWaitElement(select_view);
		Select selectView = new Select(select_view);

		List<WebElement> all_opp_options = selectView.getOptions();

		String[] opp_options = { "All Oppotunities", "Closing Next Month", "Closing This Month", "My Opportunities",
				"New Last Week", "New This Week", "Opportunity PipeLine", "Private", "Recently Viewed Opportunities",
				"Won" };

		int i = 0;
		for (WebElement item : all_opp_options) {
			if (item.getText().equalsIgnoreCase(opp_options[i]))
				log.info(opp_options[i] + " is displayed");

			else
				log.info(opp_options[i] + " is NOT displayed");
			i++;
		}

	}

	@Test
	public void New_opty_TestScript_16() throws InterruptedException {
		
		String oppNameProp = propUtility.getPropertyValue("opp.name");
		String oppAccName = propUtility.getPropertyValue("opp.accname");
		String leadSrc = propUtility.getPropertyValue("lead.source");
		String stageProp = propUtility.getPropertyValue("opp.stage");
		String primCampSrc = propUtility.getPropertyValue("prim.camp.src");
		String oppProbability = propUtility.getPropertyValue("opp.probability");

		loginToWeb();

		WebElement opportunites = driver.findElement(By.xpath("//*[@id='Opportunity_Tab']/a")); // div[@id='tabContainer']/nav/ul/li[@id='Opportunity_Tab']"));
		ClickAction(opportunites, "Opportunites");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement new_btn = driver.findElement(By.name("new"));
		ClickAction(new_btn, "New Opportunity");

		String actualTitle1 = "Opportunity Edit: New Opportunity ~ Salesforce - Developer Edition";
		String expectedTitle1 = getPageTitle(driver);
		Assert.assertEquals(actualTitle1, expectedTitle1);

		WebElement oppName = driver.findElement(By.id("opp3"));
		enterKeys(oppName, "Opportunity Name", oppNameProp);

		WebElement accName = driver.findElement(By.id("opp4"));
		enterKeys(accName, "Account Name", oppAccName);

		WebElement leadSource = driver.findElement(By.id("opp6"));
		Select source = new Select(leadSource);
		source.selectByVisibleText(leadSrc);

		WebElement closeDate = driver
				.findElement(By.xpath("//*[@id='ep']/div[2]/div[3]/table/tbody/tr[2]/td[4]/div/span/span/a"));
		ClickAction(closeDate, "Close Date");

		WebElement selectStage = driver.findElement(By.id("opp11"));
		Select stage = new Select(selectStage);
		stage.selectByVisibleText(stageProp);

		WebElement probability = driver.findElement(By.id("opp12"));
		enterKeys(probability, "Probability", oppProbability);

		WebElement leadSourceUserdropdown = driver.findElement(By.xpath("//select[@id='opp6']/option[2]"));
		ExplicitWaitElement(leadSourceUserdropdown);
		ClickAction(leadSourceUserdropdown, "leadSourceUserdropdown");

		WebElement PrimCampSource = driver.findElement(By.id("opp17"));
		ExplicitWaitElement(PrimCampSource);
		ClickAction(PrimCampSource, "PrimaryCampaignsource");

		PrimCampSource.sendKeys(primCampSrc);

		WebElement saveBtn = driver.findElement(By.xpath("//*[@id=\"bottomButtonRow\"]/input[1]"));
		ClickAction(saveBtn, "Save Button");

		WebElement oppCreated = driver.findElement(By.xpath("//*[@id='bodyCell']/div[1]/div[1]/div[1]/h2"));
		String opppDisplayed = get_Text(oppCreated);

		String actualTitle2 = "Opportunity: " + opppDisplayed + " ~ Salesforce - Developer Edition";
		String expectedTitle2 = getPageTitle(driver);
		Assert.assertEquals(actualTitle2, expectedTitle2);
	}

	@Test
	public void Opty_Pipeline_Report_TestScript_17() throws InterruptedException {
		String actualTitle2 = propUtility.getPropertyValue("actual.title17");
		loginToWeb();

		WebElement opportunites = driver.findElement(By.xpath("//*[@id='Opportunity_Tab']/a")); // div[@id='tabContainer']/nav/ul/li[@id='Opportunity_Tab']"));
		ClickAction(opportunites, "Opportunites");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement opty_pipeline = driver
				.findElement(By.xpath("//*[@id=\"toolsContent\"]/tbody/tr/td[1]/div/div[1]/div[1]/ul/li[1]/a"));

		Thread.sleep(4000);
		ClickAction(opty_pipeline, "Opportunity Pipeline");

		String expectedTitle2 = getPageTitle(driver);
		Assert.assertEquals(actualTitle2, expectedTitle2);

		log.info("Opportunities Pipeline page is displayed");
		report.logTestInfo("Opportunities Pipeline page is displayed");
	}

	@Test
	public void Stuck_Opportunities_Report_TestScript_18() throws InterruptedException {
		String actualTitle2 = propUtility.getPropertyValue("actual.title18");

		loginToWeb();

		WebElement opportunites = driver.findElement(By.xpath("//*[@id='Opportunity_Tab']/a")); // div[@id='tabContainer']/nav/ul/li[@id='Opportunity_Tab']"));
		ClickAction(opportunites, "Opportunites");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement stuckOpp = driver.findElement(By.xpath("//div[@class='toolsContentLeft']/div[1]//div/ul/li[2]/a"));

		Thread.sleep(4000);
		ClickAction(stuckOpp, "Stuck Opportunities");

		String expectedTitle2 = getPageTitle(driver);
		Assert.assertEquals(actualTitle2, expectedTitle2);

		log.info("Stuck Opportunities page is displayed");
		report.logTestInfo("Stuck Opportunities page is displayed");
	}

	@Test
	public void Quaterly_Summary_report_TestScript_19() throws InterruptedException {
		String actStatus1 = propUtility.getPropertyValue("actual.status1");
		String actRange1 = propUtility.getPropertyValue("actual.range1");
		String sumInterval = propUtility.getPropertyValue("quar.sum.interval");
		String sumInclude = propUtility.getPropertyValue("quar.sum.include");
		String actRange2 = propUtility.getPropertyValue("actual.range2");
		String actStatus2 = propUtility.getPropertyValue("actual.status2");

		loginToWeb();

		WebElement opportunites = driver.findElement(By.xpath("//*[@id='Opportunity_Tab']/a")); // div[@id='tabContainer']/nav/ul/li[@id='Opportunity_Tab']"));
		ClickAction(opportunites, "Opportunites");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement runReport = driver.findElement(By.xpath("//*[@id='lead_summary']/table/tbody/tr[3]/td/input"));
		ClickAction(runReport, "Run Report");

		WebElement repStatus = driver.findElement(By.id("quarter_q"));
		Select oppStatus = new Select(repStatus);

		String expStatus = oppStatus.getFirstSelectedOption().getText();

		log.info("Status" + expStatus);
		report.logTestInfo("Status" + expStatus);

		Assert.assertEquals(expStatus, actStatus1);

		WebElement repRange = driver.findElement(By.id("open"));

		Select selRange = new Select(repRange);
		String expRange = selRange.getFirstSelectedOption().getText();
		log.info("Range" + expRange);

		Assert.assertEquals(expRange, actRange1);
		log.info("Range is matched");
		report.logTestInfo("Range is matched");

		driver.navigate().back();
		Thread.sleep(3000);

		WebElement qSumInterval = driver
				.findElement(By.xpath("//table[@class='opportunitySummary']/tbody/tr/td/select[@id='quarter_q']"));
		Select qInterval = new Select(qSumInterval);
		qInterval.selectByVisibleText(sumInterval);

		WebElement qSumInclude = driver.findElement(By.xpath("//*[@id='open']"));
		Select qInclude = new Select(qSumInclude);
		qInclude.selectByVisibleText(sumInclude);

		WebElement runReport1 = driver.findElement(By.xpath("//*[@id='lead_summary']/table/tbody/tr[3]/td/input"));
		ClickAction(runReport1, "Run Report");

		WebElement repStatus1 = driver.findElement(By.id("quarter_q"));

		Select oppStatus1 = new Select(repStatus1);

		String expStatus1 = oppStatus1.getFirstSelectedOption().getText();

		log.info("Status" + expStatus);
		report.logTestInfo("Status" + expStatus);

		Assert.assertEquals(expStatus1, actStatus2);

		WebElement repRange1 = driver.findElement(By.id("open"));

		Select selRange1 = new Select(repRange1);

		String expRange1 = selRange1.getFirstSelectedOption().getText();

		Assert.assertEquals(expRange1, actRange2);
		log.info("Range is matched");
		report.logTestInfo("Range is matched");
	}

	@Test
	public void Lead_HomePage_TestScript_20() throws InterruptedException {
		String actualTitle = propUtility.getPropertyValue("actual.title20");

		loginToWeb();

		WebElement Leads = driver.findElement(By.xpath("//*[@id='Lead_Tab']/a")); // div[@id='tabContainer']/nav/ul/li[@id='Lead_Tab']"));
		ClickAction(Leads, "Leads");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		String expTitle = getPageTitle(driver);

		Assert.assertEquals(actualTitle, expTitle);
		log.info("Page title is matched");
		report.logTestInfo("Page title is matched");
	}

	@Test
	public void Lead_Dropdown_ListContents_TestScript_21() throws InterruptedException {

		loginToWeb();

		WebElement Leads = driver.findElement(By.xpath("//*[@id='Lead_Tab']/a")); // div[@id='tabContainer']/nav/ul/li[@id='Lead_Tab']"));
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
		String leadViewProp = propUtility.getPropertyValue("lead.view");
		String actualTitle = propUtility.getPropertyValue("actual.title22");
		String actualTitle2 = propUtility.getPropertyValue("actual.title22.2");
		String selectedView1 = propUtility.getPropertyValue("selected.view1");

		loginToWeb();

		WebElement Leads = driver.findElement(By.xpath("//*[@id='Lead_Tab']/a")); // div[@id='tabContainer']/nav/ul/li[@id='Lead_Tab']"));
		ClickAction(Leads, "Leads");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement leadsView = driver.findElement(By.id("fcf"));
		Thread.sleep(2500);
		Select selView = new Select(leadsView);
		selView.selectByVisibleText(leadViewProp);

		String expTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expTitle);
		log.info(expTitle + "Page title is matched for My Unread Leads");
		report.logTestInfo("Page title is matched for My Unread Leads");

		WebElement uMenu = driver.findElement(By.id("userNav-arrow")); // userNav"));
		ClickAction(uMenu, "User Menu");

		// userNavMenu
		WebElement sLogout = driver.findElement(By.linkText("Logout"));
		ClickAction(sLogout, "Logout");

		Thread.sleep(3000);
		String expectedTitle2 = driver.getTitle();

		Assert.assertEquals(actualTitle2, expectedTitle2);
		log.info("Page title is matched - user is logged out");
		report.logTestInfo("Page title is matched - user is logged out");

		validLogin();
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
		WebElement btn_Login = driver.findElement(By.name("Login"));
		ClickAction(btn_Login, "Login");

		WebElement Leads1 = driver.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Lead_Tab']"));
		ClickAction(Leads1, "Leads");

		Thread.sleep(2000);

		WebElement leadsView1 = driver.findElement(By.id("fcf"));
		Select selView1 = new Select(leadsView1);
		String expectedView1 = selView1.getFirstSelectedOption().getText();

		Assert.assertEquals(expectedView1, selectedView1);
		log.info("View is matched for the Leads selected during the last session");
		report.logTestInfo("View is matched for the Leads selected during the last session");
	}

	@Test
	public void Todays_Lead_TestScript_23() throws InterruptedException {
		String actualTitle = propUtility.getPropertyValue("actual.title23");
		String leadView = propUtility.getPropertyValue("leads.view23");

		loginToWeb();

		WebElement Leads = driver.findElement(By.xpath("//*[@id='Lead_Tab']/a")); // div[@id='tabContainer']/nav/ul/li[@id='Lead_Tab']"));
		ClickAction(Leads, "Leads");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement leadsView = driver.findElement(By.id("fcf"));
		FluentWaitElement(leadsView);
		Select selView = new Select(leadsView);
		selView.selectByVisibleText(leadView);

		WebElement goBtn = driver.findElement(By.name("go"));
		ClickAction(goBtn, "Go Button");
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
		String expTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expTitle);
		log.info("Page title is matched for Today's Leads");
		report.logTestInfo("Page title is matched for Today's Leads");
	}

	@Test
	public void NewButton_Lead_TestScript_24() throws InterruptedException {

		String leadView = propUtility.getPropertyValue("leads.view24");
		String actualTitle = propUtility.getPropertyValue("actual.title23");
		String leadLastname = propUtility.getPropertyValue("lead.lastname");
		String leadComp = propUtility.getPropertyValue("lead.company");

		loginToWeb();
		WebElement Leads = driver.findElement(By.xpath("//*[@id='Lead_Tab']/a"));
		ClickAction(Leads, "Leads");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement leadsView = driver.findElement(By.id("fcf"));
		FluentWaitElement(leadsView);
		Select selView = new Select(leadsView);
		selView.selectByVisibleText(leadView);

//		WebElement goBtn = driver.findElement(By.name("go"));
//		ClickAction(goBtn, "Go Button");

		WebElement newBtn = driver.findElement(By.name("new"));
		FluentWaitElement(newBtn);
		ClickAction(newBtn, "New Button");
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		String expTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expTitle);
		log.info("New Lead page is loaded");
		report.logTestInfo("New Lead page is loaded");
		// Add info

		WebElement leadLastName = driver.findElement(By.id("name_lastlea2"));
		enterKeys(leadLastName, "Last Name", leadLastname);

		WebElement leadCompany = driver.findElement(By.id("lea3"));
		enterKeys(leadCompany, "Last Name", leadComp);

		WebElement saveBtn = driver.findElement(By.name("save"));
		ClickAction(saveBtn, "Save Button");

		WebElement leadName = driver.findElement(By.xpath("//*[@id='contactHeaderRow']/div[2]/h2"));
		String topName = leadName.getText();

		String actualTitle1 = "Lead: " + topName + " ~ Salesforce - Developer Edition";
		System.out.println("act" + actualTitle1);

		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		String expectedTitle1 = getPageTitle(driver);
		System.out.println("exp" + expectedTitle1);
		Assert.assertEquals(actualTitle1, expectedTitle1);

		log.info("New Lead is created");
		report.logTestInfo("New Lead is created and displayed with details");

	}

	@Test
	public void Create_NewContact_TestScript_25() throws InterruptedException {

		String contLastName = propUtility.getPropertyValue("contact.lastname");
		String contAccName = propUtility.getPropertyValue("contact.accname");

		loginToWeb();

		WebElement rightDropdown = driver.findElement(By.id("tsidButton"));
		ExplicitWaitElement(rightDropdown);
		ClickAction(rightDropdown, "Right Drop Down");
		String viewMenu = rightDropdown.getText();
		System.out.println(viewMenu);

		WebElement contactTab = driver.findElement(By.xpath("//*[@id=\"Contact_Tab\"]/a"));
		ClickAction(contactTab, "Contacts Tab");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement newBtn = driver.findElement(By.name("new"));
		ClickAction(newBtn, "New Contact");

		WebElement contactLastName = driver.findElement(By.id("name_lastcon2"));
		enterKeys(contactLastName, "Last Name", contLastName);

		WebElement accountName = driver.findElement(By.id("con4"));
		enterKeys(accountName, "Account Name", contAccName);

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

		String viewNameProp = propUtility.getPropertyValue("view.name26");
		String viewUniqProp = propUtility.getPropertyValue("view.uniq.name26");
		String actualView = propUtility.getPropertyValue("actual.view26");

		loginToWeb();
//		clickSalesforceChatter();
		WebElement contactTab = driver.findElement(By.xpath("//*[@id=\"Contact_Tab\"]/a")); // div[@id='tabContainer']/nav/ul/li[@id='Contact_Tab']"));
		ClickAction(contactTab, "Contact Tab");

		Thread.sleep(3000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement createNewView = driver.findElement(By.xpath("//div[@class='bFilterView']/span/span[2]/a[2]"));
		ClickAction(createNewView, "Create New View");

		WebElement viewName = driver.findElement(By.id("fname"));
		enterKeys(viewName, "View Name", viewNameProp);

		WebElement viewUniqName = driver.findElement(By.id("devname"));
		enterKeys(viewUniqName, "View Unique Name", viewUniqProp);

		WebElement saveBtn = driver.findElement(By.name("save"));
		ClickAction(saveBtn, "Save Button");

		WebElement contactView = driver.findElement(By.name("fcf"));
		FluentWaitElement(contactView);
		Select selView = new Select(contactView);
		String expectedtedView = selView.getFirstSelectedOption().getText();

		Assert.assertEquals(actualView, expectedtedView);
		log.info("Created view is selected by default");
		report.logTestInfo("Created view is selected by default");
	}

	@Test
	public void Recent_Contacts_TestScript_27() throws InterruptedException {
		String actualView = propUtility.getPropertyValue("actual.view27");

		loginToWeb();
//		clickSalesforceChatter();

		WebElement contactTab = driver.findElement(By.xpath("//*[@id=\"Contact_Tab\"]/a")); // ul/li[@id='Contact_Tab']/a"));
																							// //div[@id='tabContainer']/nav/ul/li[@id='Contact_Tab']"));
		ClickAction(contactTab, "Contact Tab");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement recentContact = driver
				.findElement(By.cssSelector("tr.dataRow:nth-child(2) > th:nth-child(1) > a:nth-child(1)"));
		FluentWaitElement(recentContact);
		String expectedtedView = recentContact.getText();

		Assert.assertEquals(actualView, expectedtedView);
		log.info("Recent Contact is displayed under Recent Contacts");
		report.logTestInfo("Recent Contact is displayed under Recent Contacts");
	}

	@Test
	public void MyContacts_View_TestScript_28() throws InterruptedException {
		String actualView = propUtility.getPropertyValue("actual.view28");
		String contView = propUtility.getPropertyValue("contact.view28");

		loginToWeb();

		WebElement contactTab = driver.findElement(By.xpath("//*[@id=\"Contact_Tab\"]/a")); // div[@id='tabContainer']/nav/ul/li[@id='Contact_Tab']"));
		ClickAction(contactTab, "Contact Tab");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement contactView = driver.findElement(By.name("fcf"));
		FluentWaitElement(contactView);
		Select selView = new Select(contactView);
		selView.selectByVisibleText(contView);

		WebElement goBtn = driver.findElement(By.name("go"));
		ExplicitWaitElement(goBtn);
		ClickAction(goBtn, "Go Button");
		String expectedtedView = getPageTitle(driver);

		Assert.assertEquals(actualView, expectedtedView);
		log.info("My Contacts view is displayed.");
		report.logTestInfo("My Contacts view is displayed.");
	}

	@Test
	public void View_Contact_Details_FromView_TestScript_29() throws InterruptedException {
		String actualText = propUtility.getPropertyValue("actual.text29");

		loginToWeb();

		WebElement contactTab = driver.findElement(By.xpath("//*[@id=\"Contact_Tab\"]/a")); // div[@id='tabContainer']/nav/ul/li[@id='Contact_Tab']"));
		ClickAction(contactTab, "Contact Tab");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement recentContact = driver
				.findElement(By.cssSelector("tr.dataRow:nth-child(2) > th:nth-child(1) > a:nth-child(1)"));

		ClickAction(recentContact, "Recent Contact");
		WebElement contactDetails = driver.findElement(By.xpath("//h2[contains(text(),'Contact Detail')]"));
		FluentWaitElement(contactDetails);
		String expectedText = contactDetails.getText();

		Assert.assertEquals(actualText, expectedText);
		log.info("Selected Contact is displayed with details");
		report.logTestInfo("Selected Contact is displayed with details");
	}

	@Test
	public void Create_NewView_ErrorMessage_TestScript_30() throws InterruptedException {
		String viewUniName = propUtility.getPropertyValue("view.uniq.name30");
		String actualErrMessage = propUtility.getPropertyValue("actual.errmsg30");

		loginToWeb();

		WebElement contactTab = driver.findElement(By.xpath("//*[@id=\"Contact_Tab\"]/a")); // div[@id='tabContainer']/nav/ul/li[@id='Contact_Tab']"));
		ClickAction(contactTab, "Contact Tab");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement createNewView = driver.findElement(By.xpath("//div[@class='bFilterView']/span/span[2]/a[2]"));
		ClickAction(createNewView, "Create New View");

		WebElement viewUniqName = driver.findElement(By.id("devname"));
		enterKeys(viewUniqName, "View Unique Name", viewUniName);

		WebElement saveBtn = driver.findElement(By.name("save"));
		ClickAction(saveBtn, "Save Button");

		WebElement errorMsg = driver.findElement(By.xpath("//div[@class='errorMsg']"));
		String expectedErrMessage = errorMsg.getText();

		Assert.assertEquals(actualErrMessage, expectedErrMessage);
		log.info("Error Message is displayed");
		report.logTestInfo("Error Message is displayed");
	}

	@Test
	public void Cancel_Button_Create_NewView_TestScript_31() throws InterruptedException {
		String viewNameProp = propUtility.getPropertyValue("view.name31");
		String viewUniName = propUtility.getPropertyValue("view.uniq.name31");
		String actualView = propUtility.getPropertyValue("actual.view31");

		loginToWeb();

		WebElement contactTab = driver.findElement(By.xpath("//*[@id=\"Contact_Tab\"]/a")); // div[@id='tabContainer']/nav/ul/li[@id='Contact_Tab']"));
		ClickAction(contactTab, "Contact Tab");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement createNewView = driver.findElement(By.xpath("//div[@class='bFilterView']/span/span[2]/a[2]"));
		ClickAction(createNewView, "Create New View");

		WebElement viewName = driver.findElement(By.id("fname"));
		enterKeys(viewName, "View Name", viewNameProp);

		WebElement viewUniqName = driver.findElement(By.id("devname"));
		enterKeys(viewUniqName, "View Unique Name", viewUniName);

		WebElement cancelBtn = driver.findElement(By.name("cancel"));
		ClickAction(cancelBtn, "Cancel Button");

		WebElement contactView = driver.findElement(By.name("fcf"));
		Select selView = new Select(contactView);
		String expectedtedView = selView.getFirstSelectedOption().getText();

		Assert.assertNotEquals(actualView, expectedtedView, "Cancel Button works good, view not created");
		log.info("Cancel button works fine, view not created");
		report.logTestInfo("Cancel button works fine, view not created");
	}

	@Test
	public void Create_NewContact_TestScript_32() throws InterruptedException {
		String contFirstName = propUtility.getPropertyValue("cont.firstname32");
		String contLastName = propUtility.getPropertyValue("cont.lastname32");
		String acctName = propUtility.getPropertyValue("cont.accname32");
		String actualTitle1 = propUtility.getPropertyValue("actual.title32");

		loginToWeb();

		WebElement contactTab = driver.findElement(By.xpath("//*[@id=\"Contact_Tab\"]/a")); // div[@id='tabContainer']/nav/ul/li[@id='Contact_Tab']"));
		ClickAction(contactTab, "Contacts Tab");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement newBtn = driver.findElement(By.name("new"));
		ClickAction(newBtn, "New Contact");

		WebElement contactFirstName = driver.findElement(By.id("name_firstcon2"));
		enterKeys(contactFirstName, "First Name", contFirstName);

		WebElement contactLastName = driver.findElement(By.id("name_lastcon2"));
		enterKeys(contactLastName, "Last Name", contLastName);

		WebElement accountName = driver.findElement(By.id("con4"));
		enterKeys(accountName, "Account Name", acctName);

		WebElement saveNewBtn = driver.findElement(By.name("save_new"));
		ClickAction(saveNewBtn, "Save & New Button");

		String expectedTitle1 = getPageTitle(driver);
		Assert.assertEquals(actualTitle1, expectedTitle1);
		log.info("Contact Edit: New Contact page is displayed");
		report.logTestInfo("Contact Edit: New Contact page is displayed");
	}

	@Test
	public void UserName_Verification_TestSCript_33() throws InterruptedException {

		String expectedProfileName = propUtility.getPropertyValue("exp.prof.name33");
		loginToWeb();

		WebElement homeElement = driver.findElement(By.xpath("//*[@id=\"home_Tab\"]/a"));
		ExplicitWaitElement(homeElement);
		ClickAction(homeElement, "Home Element");

		Thread.sleep(3000);
		Close_SwitchTo_Lightning_PopUp();

//		String expectedTitle = "Salesforce - Developer Edition";
//		pageTitle(expectedTitle);

		WebElement profileName = driver.findElement(By.xpath("//*[@id=\"ptBody\"]/div/div[2]/span[1]/h1/a"));
		ExplicitWaitElement(profileName);

		String actualProfileName = profileName.getText();
		Assert.assertEquals(actualProfileName, expectedProfileName);

		WebElement name = driver.findElement(By.xpath("//h1[@class='currentStatusUserName']/a"));
		ExplicitWaitElement(name);

		String nameType = name.getTagName();
		Assert.assertEquals(nameType, "a");
		log.info("Profile name is link text");
		report.logTestInfo("Profile name is link text");

		ClickAction(name, "Profile Name");

		String namePageTitle = driver.getTitle();
		log.info("Page title is :" + namePageTitle);
		report.logTestInfo("Page title is :" + namePageTitle);

		WebElement userMenu = driver.findElement(By.id("userNavLabel"));
		ExplicitWaitElement(userMenu);
		ClickAction(userMenu, "userMenu");

		WebElement myProfile = driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a[1]"));
		ExplicitWaitElement(myProfile);
		ClickAction(myProfile, "myProfile");

		String myProfilePageTitle = driver.getTitle();
		log.info("Profile page title is :" + myProfilePageTitle);
		report.logTestInfo("Profile page title is :" + myProfilePageTitle);

		Assert.assertEquals(namePageTitle, myProfilePageTitle);
		log.info("Page Titles are matched");
		report.logTestInfo("Page Titles are matched");

	}

	public void EditedLastName_Verification_TestScript_34() throws InterruptedException {
		String contLastName = propUtility.getPropertyValue("edit.lastname33");
		String expectedProfileName = propUtility.getPropertyValue("exp.prof.name33");

		WebElement homeElement = driver.findElement(By.xpath("//li[@id='home_Tab']/a"));
		ExplicitWaitElement(homeElement);
		ClickAction(homeElement, "Home Element");

		Thread.sleep(3000);
		Close_SwitchTo_Lightning_PopUp();

//	String expectedTitle = "Salesforce - Developer Edition";
//	pageTitle(expectedTitle);

		WebElement name = driver.findElement(By.xpath("//h1[@class='currentStatusUserName']/a"));
		ExplicitWaitElement(name);

		ClickAction(name, "Profile Name");

		WebElement editProfile = driver
				.findElement(By.xpath("//div[@class='contactInfo profileSection']//img[@title='Edit Profile'][1]"));
		ExplicitWaitElement(editProfile);

		ClickAction(editProfile, "Edit Profile (pen icon)");

		driver.switchTo().frame("contactInfoContentId");
		log.info("Switched to frame");
		report.logTestInfo("Switched to frame");

		WebElement aboutTab = driver.findElement(By.xpath("//li[@id='aboutTab']/a"));
		ExplicitWaitElement(aboutTab);

		ClickAction(aboutTab, "aboutTab");

		WebElement lastName = driver.findElement(By.id("lastName"));
		ExplicitWaitElement(lastName);
		enterKeys(lastName, "Last Name", contLastName);

		WebElement saveAll = driver.findElement(By.xpath("//input[@value='Save All']"));
		ClickAction(saveAll, "saveAll");

		WebElement profileName = driver.findElement(By.xpath("//div[@class='chatterBreadcrumbs']/span[3]"));
		ExplicitWaitElement(profileName);

		String actualProfileName = profileName.getText();
		Assert.assertEquals(actualProfileName, expectedProfileName);
		log.info("Edited last name updated in the profile");
		report.logTestInfo("Edited last name updated in the profile");
	}

	@Test

	public void Tab_Customization_TestScript_35() throws InterruptedException {

		loginToWeb();

		ArrayList<String> tabsB4Custom = new ArrayList<String>();
		tabsB4Custom.add("Home");
		tabsB4Custom.add("Opportunities");
		tabsB4Custom.add("Leads");
		tabsB4Custom.add("Reports");
		tabsB4Custom.add("Accounts");
		tabsB4Custom.add("Assets");
		tabsB4Custom.add("Chatter");
		tabsB4Custom.add("Campaigns");
		tabsB4Custom.add("Content");
		tabsB4Custom.add("Contacts");
		tabsB4Custom.add("Files");
		tabsB4Custom.add("Groups");
		tabsB4Custom.add("People");
		tabsB4Custom.add("");
		tabsB4Custom.add("");

		tabsB4Custom.remove(6);

		WebElement showAllTabs = driver.findElement(By.xpath("//li[@id=\"AllTab_Tab\"]//a"));
		ExplicitWaitElement(showAllTabs);

		ClickAction(showAllTabs, "Show All Tabs");

		Thread.sleep(3000);

		WebElement customizeMyTabs = driver.findElement(By.xpath("//td[@class='bCustomize']/input"));
		ExplicitWaitElement(customizeMyTabs);

		ClickAction(customizeMyTabs, "Customize My Tabs");
		Thread.sleep(2000);

		WebElement selectedTabs = driver.findElement(By.xpath("//select[@id='duel_select_1']/option[7]"));
		ClickAction(selectedTabs, "Selected Tabs");

		WebElement removeTab = driver.findElement(By.xpath("//img[@title='Remove']"));
		ClickAction(removeTab, "Remove Tab");

		// WebElement saveCustomizeTabs =
		// driver.findElement(By.xpath("//td[@id='bottomButtonRow']/input[1]"));
		WebElement saveBtn = driver.findElement(By.name("save"));
		ExplicitWaitElement(saveBtn);

		ClickAction(saveBtn, "Save Button");

		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
		WebElement userMenuDropdown = driver.findElement(By.xpath("//span[@id= 'userNavLabel']"));
		ClickAction(userMenuDropdown, "UserMenu Dropdown");

		WebElement LogoutElement = driver.findElement(By.xpath("//div[@id='userNav-menuItems']/a[5]"));
		Thread.sleep(4000);

		ClickAction(LogoutElement, "LogoutElement");

		validLogin();

		WebElement btn_Login = driver.findElement(By.name("Login"));
		ClickAction(btn_Login, "Login");

		Collection<WebElement> customizedTabBarMenus = driver.findElements(By.xpath("//ul[@id='tabBar']/li"));

		int i = 0;

		for (WebElement list : customizedTabBarMenus) {
			Assert.assertEquals(list.getText().trim(), tabsB4Custom.get(i), "Tabs not matched");
			i++;
		}
		log.info("Tabs matched after customization");
		report.logTestInfo("Tabs matched after customization");
	}

	@Test
	public void Blocking_Event_InCalender_TestScript_36() throws InterruptedException, AWTException {
		String actUserName = propUtility.getPropertyValue("exp.lastname36");
		loginToWeb();

		WebElement homeTab = driver.findElement(By.xpath("//ul[@id='tabBar']/li[1]/a"));
		ClickAction(homeTab, "Home Tab");
		Thread.sleep(3000);
		Close_SwitchTo_Lightning_PopUp();
		WebElement currUserName = driver.findElement(By.xpath("//span[@class='pageType']/h1/a"));

		String expUserName = currUserName.getText();

		Assert.assertEquals(actUserName, expUserName);
		log.info("User Name is displayed with First and Last name.");
		report.logTestInfo("User Name is displayed with First and Last name.");

		WebElement currDateLink = driver.findElement(By.xpath("//span[@class='pageDescription']/a"));

		String expDateLink = currDateLink.getText();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE MMMM d, YYYY");
		Date date = new Date();
		String actDateLink = sdf.format(date);

		Assert.assertEquals(actDateLink, expDateLink);
		log.info("Date format is displayed as expected.");
		report.logTestInfo("Date format is displayed as expected.");

		ClickAction(currDateLink, "Current Date Link");

		String actTitle = "Calendar for " + actUserName + " ~ Salesforce - Developer Edition";
		String expTitle = getPageTitle(driver);

		Assert.assertEquals(actTitle, expTitle);
		log.info("Calender for FirstName and LastName page is displayed");
		report.logTestInfo("Calender for FirstName and LastName page is displayed");

		WebElement startTime = driver.findElement(By.xpath("//*[@id=\"p:f:j_id25:j_id61:28:j_id64\"]/a"));
		ClickAction(startTime, "Click on 8PM");
		Thread.sleep(3000);
		String actEventTitle = "Calendar: New Event ~ Salesforce - Developer Edition";
		String expEventTitle = getPageTitle(driver);

		Assert.assertEquals(actEventTitle, expEventTitle);
		log.info("New Event page is displayed");
		report.logTestInfo("New Event page is displayed");
//		WebElement subject = driver.findElement(By.xpath("//input[@id='evt5']"));
//		Verify_ElementDisplay(subject, "Subject Field");
		WebElement subCombo = driver.findElement(By.xpath("//div[@class='requiredInput']/a"));
		ClickAction(subCombo, "Clicking on Subject Combo");

		String parent_window = driver.getWindowHandle();
		Set<String> all_windows = driver.getWindowHandles();

		for (String s : all_windows) {
			if (!s.equals(parent_window)) {
				driver.switchTo().window(s);
				WebElement eventOther = driver.findElement(By.xpath("//div[2]/ul/li[5]/a"));
				ClickAction(eventOther, "Selecting Other Event Type");
				driver.switchTo().window(parent_window);
			}
		}
		driver.switchTo().activeElement();

		WebElement endTime = driver.findElement(By.id("EndDateTime_time"));

		enterKeys(endTime, "Start Time", "9:00 PM");

		WebElement saveBtn = driver.findElement(By.name("save"));
		ClickAction(saveBtn, "Save Button");
	}

	@Test
	public void Blocking_Event_Recurrence_TestScript_37() throws InterruptedException, AWTException {
		String actUserName = propUtility.getPropertyValue("exp.lastname37");

		loginToWeb();

		WebElement homeTab = driver.findElement(By.xpath("//ul[@id='tabBar']/li[1]/a"));
		ClickAction(homeTab, "Home Tab");
		Thread.sleep(3000);
		Close_SwitchTo_Lightning_PopUp();
		WebElement currUserName = driver.findElement(By.xpath("//span[@class='pageType']/h1/a"));

		String expUserName = currUserName.getText();

		Assert.assertEquals(actUserName, expUserName);
		log.info("User Name is displayed with First and Last name.");
		report.logTestInfo("User Name is displayed with First and Last name.");

		WebElement currDateLink = driver.findElement(By.xpath("//span[@class='pageDescription']/a"));

		String expDateLink = currDateLink.getText();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE MMMM d, YYYY");
		Date date = new Date();
		String actDateLink = sdf.format(date);

		Assert.assertEquals(actDateLink, expDateLink);
		log.info("Date format is displayed as expected.");
		report.logTestInfo("Date format is displayed as expected.");
		ExplicitWaitElement(currDateLink);
		ClickAction(currDateLink, "Current Date Link");

		String actTitle = "Calendar for " + actUserName + " ~ Salesforce - Developer Edition";
		String expTitle = getPageTitle(driver);
		System.out.println("exp: " + expTitle);
		Assert.assertEquals(actTitle, expTitle);
		log.info("Calender for FirstName and LastName page is displayed");
		report.logTestInfo("Calender for FirstName and LastName page is displayed");

		WebElement startTime = driver.findElement(By.xpath("//*[@id=\"p:f:j_id25:j_id61:20:j_id64\"]/a"));
		ClickAction(startTime, "Click on 4PM");
		Thread.sleep(3000);
		String actEventTitle = "Calendar: New Event ~ Salesforce - Developer Edition";
		String expEventTitle = getPageTitle(driver);

		Assert.assertEquals(actEventTitle, expEventTitle);
		log.info("New Event page is displayed");
		report.logTestInfo("New Event page is displayed");
		WebElement subject = driver.findElement(By.xpath("//input[@id='evt5']"));
		Verify_ElementDisplay(subject, "Subject Field");
		WebElement subCombo = driver.findElement(By.xpath("//div[@class='requiredInput']/a"));
		ClickAction(subCombo, "Clicking on Subject Combo");

		String parent_window = driver.getWindowHandle();
		Set<String> all_windows = driver.getWindowHandles();

		for (String s : all_windows) {
			if (!s.equals(parent_window)) {
				driver.switchTo().window(s);
				WebElement eventOther = driver.findElement(By.xpath("//div[2]/ul/li[5]/a"));
				ClickAction(eventOther, "Selecting Other Event Type");
				driver.switchTo().window(parent_window);
			}
		}
		// driver.switchTo().activeElement();

		String expSubjectValue = subject.getText();

		WebElement endTime = driver.findElement(By.id("EndDateTime_time"));
//		Select selEndTime = new Select(endTime);
//		String expDispTime = selEndTime.getFirstSelectedOption().getText();
		enterKeys(endTime, "Start Time", "7:00 PM");
//		String expDispTime = get_Text(endTime);
//		
//		String actDispTime = "7:00 PM";
//
//		Assert.assertEquals(actDispTime, expDispTime);
//		log.info("End time is selected as 7:00 PM");

		/*
		 * List<WebElement> allOptions = selEndTime.getAllSelectedOptions();
		 * 
		 * String[] timeOptions = { "5:00 PM", "5.30 PM", "6:00 PM", "6:30 PM",
		 * "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM", "10:00 PM",
		 * "10:30 PM", "11:00 PM", "11:30 PM" }; int i = 0; for (WebElement item :
		 * allOptions) { if (item.getText().equalsIgnoreCase(timeOptions[i])) {
		 * log.info(timeOptions[i] + " is displayed"); } else { log.info(timeOptions[i]
		 * + " is NOT displayed"); } i++; } selEndTime.selectByVisibleText("7:00 PM");
		 * String expectedEndTime = selEndTime.getFirstSelectedOption().getText();
		 * String actualEndTime = "7:00 PM";
		 * 
		 * Assert.assertEquals(actualEndTime, expectedEndTime);
		 * log.info("End time is selected as 7:00 PM");
		 */
		WebElement recurrEvent = driver.findElement(By.id("IsRecurrence"));
		ExplicitWaitElement(recurrEvent);
		ClickAction(recurrEvent, "Checking Recurrence of Events");

		WebElement recurrType = driver.findElement(By.xpath("//*[@id=\"recpat\"]/table/tbody/tr[1]/td[1]/label"));

		Verify_ElementDisplay(recurrType, "Frequency is displayed");

		WebElement recurrStart = driver.findElement(By.id("RecurrenceStartDateTime"));
		Verify_ElementDisplay(recurrStart, "Recurrence Start is displayed");

		WebElement recurrEnd = driver.findElement(By.id("RecurrenceEndDateOnly"));
		Verify_ElementDisplay(recurrEnd, "Recurrence End is displayed");

		WebElement RecurrWeekly = driver.findElement(By.xpath("//label[contains(text(),'Weekly')]"));
		ClickAction(RecurrWeekly, "Weekly recurrence");

		WebElement recurEvery = driver.findElement(By.id("wi"));
		Verify_ElementDisplay(recurEvery, "Recurrs every text box");

		WebElement day1 = driver.findElement(By.id("1"));
		WebElement day2 = driver.findElement(By.id("2"));
		WebElement day3 = driver.findElement(By.id("4"));
		WebElement day4 = driver.findElement(By.id("8"));
		WebElement day5 = driver.findElement(By.id("16"));
		WebElement day6 = driver.findElement(By.id("32"));
		WebElement day7 = driver.findElement(By.id("64"));

		String actualDay = null;

		if (day1.isSelected())
			actualDay = "Sunday";
		else if (day2.isSelected())
			actualDay = "Monday";
		else if (day3.isSelected())
			actualDay = "Tuesday";
		else if (day4.isSelected())
			actualDay = "Wednesday";
		else if (day5.isSelected())
			actualDay = "Thursday";
		else if (day6.isSelected())
			actualDay = "Friday";
		else if (day7.isSelected())
			actualDay = "Saturday";

		SimpleDateFormat simpleDay = new SimpleDateFormat("EEEE");
		Date day = new Date();
		String expectedDay = simpleDay.format(day);
		Assert.assertEquals(actualDay, expectedDay);
		log.info("Current Day is selected");

		Robot robot = new Robot();
		robot.mouseWheel(2);

		WebElement recurEndDate = driver.findElement(By.id("RecurrenceEndDateOnly"));

		LocalDate today = LocalDate.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd-yyyy");

		LocalDate plus2Weeks = today.plusWeeks(2);
		String dateString = plus2Weeks.format(df);
		enterKeys(recurEndDate, "Recurrence End Date", dateString);

		WebElement saveBtn = driver.findElement(By.name("save"));
		ClickAction(saveBtn, "Save Button");

		WebElement eventCreated = driver
				.findElement(By.xpath("//*[@id='p:f:j_id25:j_id69:20:j_id71:0:j_id72:calendarEvent:j_id84']/a/span"));
		Verify_ElementDisplay(eventCreated, "Event Created");

		WebElement monthView = driver.findElement(By.xpath("//*[@id=\"bCalDiv\"]/div/div[2]/span[2]/a[3]/img"));
		ClickAction(monthView, "Clicking Month View");

		WebElement thisMonth = driver.findElement(By.xpath("//*[@id=\"bodyCell\"]/div[1]/div[2]/span[5]/a"));
		Verify_ElementDisplay(thisMonth, "Month view display");

		WebElement firstOccur = driver
				.findElement(By.xpath("//*[@id='bodyCell']/div[2]/div[1]/div[1]/table/tbody/tr[3]/td[7]/div[2]/a"));

		WebElement secondOccur = driver
				.findElement(By.xpath("//*[@id='bodyCell']/div[2]/div[1]/div[1]/table/tbody/tr[4]/td[7]/div[2]/a"));

		WebElement thirdOccur = driver
				.findElement(By.xpath("//*[@id='bodyCell']/div[2]/div[1]/div[1]/table/tbody/tr[5]/td[7]/div[2]/a"));
		String eventType = thirdOccur.getText();
		Verify_ElementDisplay(firstOccur, "First event occurence");
		Verify_ElementDisplay(secondOccur, "Second event occurence");
		Verify_ElementDisplay(thirdOccur, "Third event occurence");

		log.info(eventType + " is selected as Event subject");
		report.logTestInfo(eventType + " is selected as Event subject");

	}
}
