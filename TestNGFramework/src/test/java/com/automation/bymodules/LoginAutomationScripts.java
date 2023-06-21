package com.automation.bymodules;

import java.util.Properties;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.Test;

import comautomation.base.BaseTestSalesForce;

import com.automation.utility.PropertiesUtility;

public class LoginAutomationScripts extends BaseTestSalesForce {

	public PropertiesUtility propUtility = new PropertiesUtility();
	public Properties propertyFile = propUtility.loadFile("salesforceproperties");
	String username = propUtility.getPropertyValue("sf.valid.username");
	String password = propUtility.getPropertyValue("sf.valid.password");
	public String emailForgotPassword = propUtility.getPropertyValue("sf.forgot.password");
	public String invalidUsername = propUtility.getPropertyValue("sf.invalid.password");
	public String invalidPassword = propUtility.getPropertyValue("sf.invalid.password");

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


}
