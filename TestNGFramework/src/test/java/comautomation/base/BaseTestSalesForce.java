package comautomation.base;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.automation.utility.Constants;
import com.automation.utility.ExtentReportUtility;
import com.automation.utility.Log4JUtility;
import com.automation.utility.PropertiesUtility;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.text.SimpleDateFormat;

public class BaseTestSalesForce {
	public static WebDriver driver;
	protected WebDriverWait waitObj;
	protected Logger log;
	protected String s;
	protected ExtentReportUtility report = ExtentReportUtility.getInstance();
	protected PropertiesUtility propUtility = new PropertiesUtility();
	protected Log4JUtility logObj = Log4JUtility.getInstance();

	@BeforeTest
	public void setUpForBeforeTest() {
		// log = LogManager.getLogger(BaseTestSalesForce.class.getName());
		log = logObj.getLogger();
	}

	@BeforeMethod
	@Parameters("browserName")
	public void setUpForBeforeMethod(@Optional("chrome") String browName) {

		Properties propertyFile = propUtility.loadFile("salesforceproperties");
		String url = propUtility.getPropertyValue("salesforce.url");

		launchBrowser(browName);
		goToUrl(url);

	}

	@AfterMethod
	public void tearDownAfterMethod() {
		driver.close();
	}

	public void launchBrowser(String str) {
		switch (str) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			break;
		}
	}

	public void goToUrl(String url) {
		driver.get(url);
		log.info("Url is entered in the browser");
	}

	public void enterKeys(WebElement element, String objName, String data) {
		if (element.isDisplayed()) {
			element.clear();
			element.sendKeys(data);
			log.info(data + " is entered in the " + objName + " field");
			report.logTestInfo(data + " is entered in the " + objName + " field");
		} else {
			log.error("Element is not displayed");
			report.logTestFailed("Element is not displayed");
		}
	}

	public void RememberMeLogin(boolean rememberMe) {
		if (rememberMe == true) {
			WebElement RememberMe_Checkbox = driver.findElement(By.xpath("//*[@id='rememberUn']"));
			RememberMe_Checkbox.click();
		}
	}

	public void ClickAction(WebElement element, String objName) {
		if (element.isEnabled()) {
			element.click();
			log.info(objName + " is clicked");
		}
	}

	public String get_Text(WebElement element) {
		if (element.isDisplayed())
			s = element.getText();
		return s;
	}

	public String get_Attribute(WebElement element) {
		s = element.getAttribute("type");
		return s;
	}

	public String getPageTitle(WebDriver driver) {
		s = driver.getTitle();
		return s;
	}

	public void Verify_ElementDisplay(WebElement element, String data) {
		if (element.isDisplayed())
			log.info(data + " is displayed");
		else
			log.error(data + " is NOT displayed");
	}

	public void ExplicitWaitElement(WebElement element) {
		waitObj = new WebDriverWait(driver, 45);
		waitObj.until(ExpectedConditions.visibilityOf(element));
	}

	public void ImplicitWaitElement() {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@SuppressWarnings({ "deprecation", "unused" })
	public void FluentWaitElement(WebElement element) {
		waitObj = new WebDriverWait(driver, 45);
		@SuppressWarnings("unchecked")
		Wait<WebDriver> waitObj = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

	}

	public void alert_accept() {
		Alert alert = driver.switchTo().alert();
		String alertMessage = alert.getText();
		log.info("Alert Message is: " + alertMessage);
		alert.accept();
	}

	public void alert_dismiss() {
		Alert alert = driver.switchTo().alert();
		String alertMessage = alert.getText();
		log.info("Alert Message is: " + alertMessage);
		alert.dismiss();
	}

	public void validLogin() {
		String username = propUtility.getPropertyValue("sf.valid.username");
		String password = propUtility.getPropertyValue("sf.valid.password");

		WebElement uName = driver.findElement(By.id("username"));
		enterKeys(uName, "username", username);
		WebElement pWord = driver.findElement(By.id("password"));
		enterKeys(pWord, "Password", password);

	}

	public void loginToWeb() {
		String username = propUtility.getPropertyValue("sf.valid.username");
		String password = propUtility.getPropertyValue("sf.valid.password");

		String actualTitle = "Login | Salesforce";
		String expectedTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);

		WebElement uName = driver.findElement(By.id("username"));
		enterKeys(uName, "username", username);
		WebElement pWord = driver.findElement(By.id("password"));
		enterKeys(pWord, "Password", password);

		RememberMeLogin(false);
		WebElement btn_Login = driver.findElement(By.name("Login"));
		ClickAction(btn_Login, "Login");
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

	}

	public void UserMenuOptions() throws InterruptedException {

		WebElement uMenu = driver.findElement(By.id("userNav-arrow"));
		Thread.sleep(3000);
		ClickAction(uMenu, "User Menu");

		String[] user_menu_items = { "My Profile", "My Settings", "Developer Console", "Switch to Lightning Experience",
				"Logout" };

		List<WebElement> user_dropdown = driver.findElements(By.xpath("//*[@id='userNav-menuItems']/a"));
		int i = 0;
		for (WebElement item : user_dropdown) {
			if (item.getText().equalsIgnoreCase(user_menu_items[i]))
				log.info(user_menu_items[i] + " is displayed");
			else
				log.error(user_menu_items[i] + " is NOT displayed");
			i++;
		}
	}

	public void Close_SwitchTo_Lightning_PopUp() throws InterruptedException // Closing the switch to lightning exp
																				// popup
	{

		Thread.sleep(3000);
		WebElement noThanks_popup = driver.findElement(By.xpath("//*[@id='lexNoThanks']"));

		ClickAction(noThanks_popup, "No Thanks");

		WebElement submit_popup = driver.findElement(By.xpath("//*[@id='lexSubmit']"));
		ExplicitWaitElement(submit_popup);
		ClickAction(submit_popup, "Send TO SalesForce");
	}

	@DataProvider(name = "sfdatatc6")
	public String[][] getExcelData() throws IOException {
		File file = new File(Constants.XL_DATA);

		FileInputStream fs = new FileInputStream(file);

		XSSFWorkbook workbook = new XSSFWorkbook(fs);

		XSSFSheet sheet = workbook.getSheet("Sheet1");

		int nRows = sheet.getPhysicalNumberOfRows(); // gets the physically defined rows not the total rows
		int nColumns = sheet.getRow(0).getLastCellNum();
		// System.out.println(nRows + " " +nColumns);
		String[][] data = new String[nRows - 1][nColumns];
		for (int i = 0; i < nRows - 1; i++) {
			for (int j = 0; j < nColumns; j++) {
				DataFormatter df = new DataFormatter();
				data[i][j] = df.formatCellValue(sheet.getRow(i + 1).getCell(j));
				System.out.println(data[i][j].toString());
			}
			System.out.print("");
		}
		workbook.close();
		fs.close();
		return data;
	}

	public String getSheetName(String sheetname) {
		return sheetname;
	}

	public File getScreenCapture(WebDriver driver) {

		String date = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
		TakesScreenshot scrnShot = (TakesScreenshot) driver;
		File imgFile = scrnShot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(Constants.SCREENSHOTS_DIRECTORY_PATH + date + ".png");
		try {
			FileUtils.copyFile(imgFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Screenshot captured at :" + destFile.getAbsolutePath());

		return destFile;
	}

	public void addTab(String tabName) throws InterruptedException, AWTException {
		UserMenuOptions();

		WebElement MySettings = driver.findElement(By.xpath("//*[@id='userNav-menuItems']/a[2]"));

		ClickAction(MySettings, "My Settings");
		Thread.sleep(5000);

		Robot robot = new Robot();
		robot.mouseWheel(2);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement dispLayout = driver.findElement(By.xpath("//*[@id=\"DisplayAndLayout_font\"]"));
		js.executeScript("arguments[0].click();", dispLayout);
		log.info("Display & Layout is clicked");
		Thread.sleep(5000);

		robot.mouseWheel(6);
		WebElement custTabs = driver.findElement(By.xpath("//*[@id='CustomizeTabs_font']"));
		js.executeScript("arguments[0].click();", custTabs);
		log.info("Customize My Tabs is clicked");

		WebElement custApp = driver.findElement(By.xpath("//*[@id='p4']"));
		Select selectCustApp = new Select(custApp);
		selectCustApp.selectByVisibleText("Salesforce Chatter");

		WebElement availTabs = driver.findElement(By.xpath("//*[@id='duel_select_0']"));
		Select selectAvailTabs = new Select(availTabs);
		selectAvailTabs.selectByVisibleText(tabName);

		WebElement addToTab = driver.findElement(By.xpath("//*[@id='duel_select_0_right']/img"));
		ClickAction(addToTab, "Add to Selected Tabs");

		WebElement saveBtn = driver.findElement(By.xpath("//*[@id='bottomButtonRow']/input[1]"));
		ClickAction(saveBtn, "Save Button");
		log.info("Customized tab settings is saved");
	}

}
