package com.automation.bymodules;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import comautomation.base.BaseTestSalesForce;

public class OptyAutomationScripts extends BaseTestSalesForce{
	@Test
	public void opportunities_TestScript_15() throws InterruptedException {

		loginToWeb();

		WebElement opportunites = driver
				.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Opportunity_Tab']"));
		ClickAction(opportunites, "Opportunites");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement select_view = driver.findElement(By.xpath("//*[@id='fcf']"));
		ExplicitWaitElement(select_view);
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

		loginToWeb();

		WebElement opportunites = driver
				.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Opportunity_Tab']"));
		ClickAction(opportunites, "Opportunites");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement new_btn = driver.findElement(By.name("new"));
		ClickAction(new_btn, "New Opportunity");

		String actualTitle1 = "Opportunity Edit: New Opportunity ~ Salesforce - Developer Edition";
		String expectedTitle1 = getPageTitle(driver);
		Assert.assertEquals(actualTitle1, expectedTitle1);

		WebElement oppName = driver.findElement(By.id("opp3"));
		enterKeys(oppName, "Opportunity Name", "OPP2");

		WebElement accName = driver.findElement(By.id("opp4"));
		enterKeys(accName, "Account Name", "GC Account");

		WebElement leadSource = driver.findElement(By.id("opp6"));
		Select source = new Select(leadSource);
		source.selectByVisibleText("Web");

		WebElement closeDate = driver
				.findElement(By.xpath("//*[@id='ep']/div[2]/div[3]/table/tbody/tr[2]/td[4]/div/span/span/a"));
		ClickAction(closeDate, "Close Date");

		WebElement selectStage = driver.findElement(By.id("opp11"));
		Select stage = new Select(selectStage);
		stage.selectByVisibleText("Closed Won");

		WebElement probability = driver.findElement(By.id("opp12"));
		enterKeys(probability, "Probability", "100");

		WebElement leadSourceUserdropdown = driver.findElement(By.xpath("//select[@id='opp6']/option[2]"));
		ExplicitWaitElement(leadSourceUserdropdown);
		ClickAction(leadSourceUserdropdown, "leadSourceUserdropdown");

		WebElement PrimCampSource = driver.findElement(By.id("opp17"));
		ExplicitWaitElement(PrimCampSource);
		ClickAction(PrimCampSource, "PrimaryCampaignsource");

		PrimCampSource.sendKeys("DM Campaign to Top Customers");

		WebElement saveBtn = driver.findElement(By.xpath("//*[@id=\"bottomButtonRow\"]/input[1]"));
		ClickAction(saveBtn, "Save Button");

		WebElement oppCreated = driver.findElement(By.xpath("//*[@id='bodyCell']/div[1]/div[1]/div[1]/h2"));
		String opppDisplayed = get_Text(oppCreated);

		String actualTitle2 = "Opportunity: " + opppDisplayed + "~ Salesforce - Developer Edition";
		String expectedTitle2 = getPageTitle(driver);
		Assert.assertEquals(actualTitle2, expectedTitle2);
	}

	@Test
	public void Opty_Pipeline_Report_TestScript_17() throws InterruptedException {

		loginToWeb();

		WebElement opportunites = driver
				.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Opportunity_Tab']"));
		ClickAction(opportunites, "Opportunites");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement opty_pipeline = driver
				.findElement(By.xpath("//*[@id=\"toolsContent\"]/tbody/tr/td[1]/div/div[1]/div[1]/ul/li[1]/a"));

		Thread.sleep(4000);
		ClickAction(opty_pipeline, "Opportunity Pipeline");

		String actualTitle2 = "Opportunity Pipeline ~ Salesforce - Developer Edition";
		String expectedTitle2 = getPageTitle(driver);
		Assert.assertEquals(actualTitle2, expectedTitle2);
		log.info("Opportunities Pipeline page is displayed");
	}

	@Test
	public void Stuck_Opportunities_Report_TestScript_18() throws InterruptedException {

		loginToWeb();

		WebElement opportunites = driver
				.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Opportunity_Tab']"));
		ClickAction(opportunites, "Opportunites");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement stuckOpp = driver.findElement(By.xpath("//div[@class='toolsContentLeft']/div[1]//div/ul/li[2]/a"));

		Thread.sleep(4000);
		ClickAction(stuckOpp, "Stuck Opportunities");

		String actualTitle2 = "Stuck Opportunities ~ Salesforce - Developer Edition";
		String expectedTitle2 = getPageTitle(driver);
		Assert.assertEquals(actualTitle2, expectedTitle2);
		log.info("Stuck Opportunities page is displayed");
	}

	@Test
	public void Quaterly_Summary_report_TestScript_19() throws InterruptedException {

		loginToWeb();

		WebElement opportunites = driver
				.findElement(By.xpath("//div[@id='tabContainer']/nav/ul/li[@id='Opportunity_Tab']"));
		ClickAction(opportunites, "Opportunites");

		Thread.sleep(4000);
		Close_SwitchTo_Lightning_PopUp();

		WebElement runReport = driver.findElement(By.xpath("//*[@id='lead_summary']/table/tbody/tr[3]/td/input"));
		ClickAction(runReport, "Run Report");

		WebElement repStatus = driver.findElement(By.id("quarter_q"));
		Select oppStatus = new Select(repStatus);

		String expStatus = oppStatus.getFirstSelectedOption().getText();

		String actStatus = "Current FQ";

		log.info("Status" + expStatus);
		Assert.assertEquals(expStatus, actStatus);

		WebElement repRange = driver.findElement(By.id("open"));

		Select selRange = new Select(repRange);
		String expRange = selRange.getFirstSelectedOption().getText();
		log.info("Range" + expRange);

		String actRange = "Any";

		Assert.assertEquals(expRange, actRange);
		log.info("Range is matched");

		driver.navigate().back();
		Thread.sleep(3000);

		WebElement qSumInterval = driver
				.findElement(By.xpath("//table[@class='opportunitySummary']/tbody/tr/td/select[@id='quarter_q']"));
		Select qInterval = new Select(qSumInterval);
		qInterval.selectByVisibleText("Previous FQ");

		WebElement qSumInclude = driver.findElement(By.xpath("//*[@id='open']"));
		Select qInclude = new Select(qSumInclude);
		qInclude.selectByVisibleText("Closed Opportunities");

		WebElement runReport1 = driver.findElement(By.xpath("//*[@id='lead_summary']/table/tbody/tr[3]/td/input"));
		ClickAction(runReport1, "Run Report");

		WebElement repStatus1 = driver.findElement(By.id("quarter_q"));

		Select oppStatus1 = new Select(repStatus1);

		String expStatus1 = oppStatus1.getFirstSelectedOption().getText();

		String actStatus1 = "Previous FQ";

		log.info("Status" + expStatus);
		Assert.assertEquals(expStatus1, actStatus1);

		WebElement repRange1 = driver.findElement(By.id("open"));

		Select selRange1 = new Select(repRange1);

		String expRange1 = selRange1.getFirstSelectedOption().getText();

		String actRange1 = "Closed";

		Assert.assertEquals(expRange1, actRange1);
		log.info("Range is matched");

	}
}
