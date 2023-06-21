package com.automation.utility;


import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class XlFileUtility {

	public static String cellValue = null;
	public static String pword = null;
	public static String cellValue1 = null;
	public static XSSFWorkbook workbook = null;
	

	public static void readAllDataFromXLfile(File path, String sName) {
	
		try {
			workbook = new XSSFWorkbook(path);
//			System.out.println(workbook.getSheetName(0));
			XSSFSheet sheet = workbook.getSheet(sName);
			int rowCount = getTotalRowsCount(sheet);
			System.out.println("row count=" +rowCount);
			for (int i=0;i<rowCount;i++)
			{
				XSSFRow row = sheet.getRow(i);
				
				Cell cell = row.getCell(i);
				cellValue = cell.getStringCellValue();
				
				System.out.println(cellValue);
				
			}
			
	} catch (IOException e) {
		e.printStackTrace();
	} catch (InvalidFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public static int getTotalRowsCount(XSSFSheet sheet) {
		return sheet.getLastRowNum()+1;
	}
	
	public static int getTotalCellCount(XSSFRow row) {
		return row.getLastCellNum();
	}
}
	
