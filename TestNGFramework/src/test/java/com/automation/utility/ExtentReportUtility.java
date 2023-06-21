package com.automation.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentReportUtility {
	public static ExtentReports report;
	public static ExtentSparkReporter spark;
	public static ExtentTest reportLogger;
	private static ExtentReportUtility extentObject;

	private ExtentReportUtility() {

	}

	public static ExtentReportUtility getInstance() {
		if (extentObject == null) {
			extentObject = new ExtentReportUtility();
		}
		return extentObject;
	}

	public void startExtentReport() {
		report = new ExtentReports();
		spark = new ExtentSparkReporter(Constants.SPARK_HTML_REPORT_PATH);
		report.attachReporter(spark);

		report.setSystemInfo("HostName", "SalesForce");
		report.setSystemInfo("Environment", "QA");

		spark.config().setDocumentTitle("Test Execution Document");
		spark.config().setReportName("Salesforce Test Execution Report");
		spark.config().setTheme(Theme.DARK);
	}
	
	public void startSingleTestReport(String methodName)
	{
		reportLogger = report.createTest(methodName);
	}
	
	public void endReport()
	{
		report.flush();
	}
	
	public void logTestInfo(String text)
	{
		reportLogger.log(Status.INFO, text);		
	}
	
	public void logTestPass(String text)
	{
		reportLogger.log(Status.PASS, MarkupHelper.createLabel(text,ExtentColor.GREEN));
	}
	
	public void logTestFailed(String text)
	{
		reportLogger.log(Status.FAIL, MarkupHelper.createLabel(text,ExtentColor.RED));
	}
	
	public void logTestFailedWithException(Throwable e)
	{
		reportLogger.log(Status.FAIL, e);
	}
	
	public void logTestWithScreenshot(String path)
	{
		reportLogger.addScreenCaptureFromBase64String(path);
	}
}
