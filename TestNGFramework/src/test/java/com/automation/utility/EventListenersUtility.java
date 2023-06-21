package com.automation.utility;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import comautomation.base.BaseTestSalesForce;

public class EventListenersUtility implements ITestListener {
	
	private static ExtentReportUtility extentReport;
	
	
	@Override
	public void onTestStart(ITestResult result) {
		extentReport.startSingleTestReport(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentReport.logTestPass(result.getMethod().getMethodName() +" is PASSED");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extentReport.logTestFailed(result.getMethod().getMethodName()+" is FAILED");
		extentReport.logTestFailedWithException(result.getThrowable());
		BaseTestSalesForce obj = new BaseTestSalesForce();
		WebDriver driver = BaseTestSalesForce.driver;
		System.out.println("Driver is in the listeners utility"+driver);
		File imageFile=obj.getScreenCapture(driver);
		byte[] fileContent = null;
		try {
			fileContent = FileUtils.readFileToByteArray(new File(imageFile.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String imgEncodedStr = Base64.getEncoder().encodeToString(fileContent);
		extentReport.logTestWithScreenshot(imgEncodedStr);
	}

	@Override
	public void onStart(ITestContext context) {
		
		extentReport = ExtentReportUtility.getInstance();
		extentReport.startExtentReport();
	}

	@Override
	public void onFinish(ITestContext context) {
		extentReport.endReport();
	}

}
