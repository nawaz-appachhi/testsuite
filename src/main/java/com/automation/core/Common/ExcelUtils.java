/**
 * 
 */
package com.automation.core.Common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author 300020817
 *
 */
public class ExcelUtils {
	/**
	 * @param ExcelFilePath
	 * @return
	 * @author neerajkumar.b.lad Date of Creation: 28th November 2017 Purpose: This
	 *         method help to get excel object by passing excel file path
	 *         Contributors Name : neerajkumar.b.lad Example : ExcelFilePath =
	 *         ".//Automation/TestData/Login.xls"
	 */
	public Workbook getExcelSheet(String ExcelFilePath) {
		workbook = null;
		try {
			File file = new File(ExcelFilePath).getAbsoluteFile();
			FileInputStream inputStream = new FileInputStream(file);
			String fileExtensionName = ExcelFilePath.substring(ExcelFilePath.lastIndexOf("."));
			if (fileExtensionName.equals(".xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else if (fileExtensionName.equals(".xls")) {
				workbook = new HSSFWorkbook(inputStream);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found " + ExcelFilePath);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File does not exists " + ExcelFilePath);
			e.printStackTrace();
		}
		return workbook;
	}

	/**
	 * @param ExcelFilePath
	 * @param SheetName
	 * @return
	 * @author neerajkumar.b.lad Date of Creation: 28th November 2017 Purpose: This
	 *         method help to get excel sheet name by passing excel file path, and
	 *         sheet name Contributors Name : neerajkumar.b.lad Example :
	 *         ExcelFilePath = ".//Automation/TestData/Login.xls" SheetName =
	 *         "Sheet1"
	 */
	public Sheet getsheetName(String ExcelFilePath, String SheetName) {
		Workbook workbook = getExcelSheet(ExcelFilePath);
		Sheet sheet = null;
		try {
			sheet = workbook.getSheet(SheetName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Sheet " + SheetName + " Not found");
			e.printStackTrace();
		}
		return sheet;
	}

	/**
	 * @param SheetName
	 * @return Date of Creation: 28th November 2017 Purpose: This method help to get
	 *         total row count of excel sheet name by passing sheet name
	 *         Contributors Name : neerajkumar.b.lad Example : SheetName = "Sheet1"
	 */
	public int getRowCount(Sheet SheetName) {
		return SheetName.getLastRowNum();
	}

	/**
	 * @param SheetName
	 * @param ColumnName
	 * 
	 * @return Date of Creation: 28th November 2017 Purpose: This method help to get
	 *         total row count of particular column in the excel sheet name by
	 *         passing sheet name and column Name Contributors Name :
	 *         neerajkumar.b.lad Example : SheetName = "Sheet1", ColumnName =
	 *         "UserID"
	 */
	public int getRowCountByColumnName(Sheet SheetName, String ColumnName) {
		Map<String, Integer> Header = readHeader(SheetName);
		Integer Index = Header.get(ColumnName);
		int RowCount = 0;
		for (Row row : SheetName) { // For each Row.
			Cell cell = row.getCell(Index); // Get the Cell at the Index / Column you want.
			if (cell != null) {
				if (cell.getCellType() != cell.CELL_TYPE_BLANK) {
					RowCount++;
				}
			}
		}
		System.out.println("RowCount:" + RowCount);
		return RowCount;
	}

	/**
	 * @param SheetName
	 * @return Date of Creation: 28th November 2017 Purpose: This method help to get
	 *         total column count of excel sheet name by passing sheet name
	 *         Contributors Name : neerajkumar.b.lad Example : SheetName = "Sheet1"
	 */
	public int getColumnCount(Sheet SheetName) {
		return SheetName.getRow(0).getLastCellNum();
	}

	/**
	 * @param SheetName
	 * @param Row
	 * @param Col
	 * @return Date of Creation: 28th November 2017 Purpose: This method help to get
	 *         cell data from excel sheet by passing sheet name, row and col index
	 *         Contributors Name : neerajkumar.b.lad Example : SheetName = "Sheet1"
	 *         Row = 0 Col = 1
	 */
	@SuppressWarnings("static-access")
	public String getCellData(Sheet SheetName, int Row, int Col) {
		String cellValue = null;
		try {
			Cell cell = SheetName.getRow(Row).getCell(Col);
			int cellType = cell.getCellType();
			if (cellType == cell.CELL_TYPE_STRING) {
				cellValue = cell.getStringCellValue();
			} else if (cellType == Cell.CELL_TYPE_FORMULA) {
				cellValue = cell.getCellFormula();
			} else if (cellType == Cell.CELL_TYPE_NUMERIC) {
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = cell.getDateCellValue().toString();
				} else {
					cellValue = Double.toString(cell.getNumericCellValue());
				}
			} else if (cellType == Cell.CELL_TYPE_BLANK) {
				cellValue = "";
			} else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
				cellValue = Boolean.toString(cell.getBooleanCellValue());
			}
		} catch (Exception e) {
			System.out.println("Could not return TestData");
			e.printStackTrace();
		}
		return cellValue;
	}

	/**
	 * @param SheetName
	 * @return Date of Creation: 28th November 2017 Purpose: This method help to
	 *         read 1st row header name from excel sheet by passing sheet name
	 *         Contributors Name : neerajkumar.b.lad Example : SheetName = "Sheet1"
	 */
	public Map<String, Integer> readHeader(Sheet SheetName) {
		Map<String, Integer> RecordIndex = new HashMap<String, Integer>();
		Row Row = SheetName.getRow(0);
		for (int i = 0; i < Row.getLastCellNum(); i++) {
			RecordIndex.put(Row.getCell(i).getStringCellValue(), i);
		}
		return RecordIndex;
	}

	/**
	 * @param SheetName
	 * @param objRecord
	 * @param ColumnName
	 * @return Date of Creation: 28th November 2017 Purpose: This method will help
	 *         user to find column index of given columnName from the first 0th row
	 *         ( header) Contributors Name : neerajkumar.b.lad Example : SheetName =
	 *         "Sheet1" Record = SheetName.getRow(0); ColumnName = "Login"
	 */
	public String getColumnValue(Sheet SheetName, Row objRecord, String ColumnName) {
		try {
			Map<String, Integer> Header = readHeader(SheetName);
			Integer Index = Header.get(ColumnName);
			// return objRecord[Index].getStringCellValue();
			return objRecord.getCell(Index).getStringCellValue();
		} catch (Exception e) {
			System.out.println(ColumnName + " Column is not exists, or could be empty");
		}
		return null;
	}

	// public static String filename = System.getProperty("user.dir") +
	// "\\src\\config\\testcases\\TestData.xlsx";
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private Workbook workbook = null;
	private Sheet sheet = null;
	private Row row = null;
	private Cell cell = null;

	public void Xls_Reader(String ExcelFilePath) {
		this.path = ExcelFilePath;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// //Workbook = null;
		// try {
		// File file = new File(path);
		// FileInputStream inputStream = new FileInputStream(file);
		// String fileExtensionName =
		// ExcelFilePath.substring(ExcelFilePath.lastIndexOf("."));
		//
		// if (fileExtensionName.equals(".xlsx")) {
		// workbook = new XSSFWorkbook(inputStream);
		// } else if (fileExtensionName.equals(".xls")) {
		// workbook = new HSSFWorkbook(inputStream);
		// }
		// } catch (FileNotFoundException e) {
		// System.out.println("File not found " + ExcelFilePath);
		// e.printStackTrace();
		// } catch (IOException e) {
		// System.out.println("File does not exists " + ExcelFilePath);
		// e.printStackTrace();
		// }
		// //return workbook;
	}

	// returns the row count in a sheet
	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}
	}

	// returns the data from a cell
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";
			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);
			if (cell == null)
				return "";
			// System.out.println(cell.getCellType());
			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				String cellText = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
					// System.out.println(cellText);
				}
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}

	// returns the data from a cell
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";
			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				String cellText = String.valueOf(cell.getNumericCellValue());
				/*
				 * if (HSSFDateUtil.isCellDateFormatted(cell)) { // format in form of M/D/YY
				 * double d = cell.getNumericCellValue();
				 * 
				 * Calendar cal =Calendar.getInstance();
				 * cal.setTime(HSSFDateUtil.getJavaDate(d)); cellText =
				 * (String.valueOf(cal.get(Calendar.YEAR))).substring(2); cellText =
				 * cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" +
				 * cellText;
				 * 
				 * // System.out.println(cellText);
				 * 
				 * }
				 * 
				 */
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			if (rowNum <= 0)
				return false;
			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;
			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);
			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);
			// cell style
			CellStyle cs = workbook.createCellStyle();
			cs.setWrapText(true);
			cell.setCellStyle(cs);
			cell.setCellValue(data);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {
		// System.out.println("setCellData setCellData******************");
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			if (rowNum <= 0)
				return false;
			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;
			sheet = workbook.getSheetAt(index);
			// System.out.println("A");
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;
			sheet.autoSizeColumn(colNum); // ashish
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);
			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);
			cell.setCellValue(data);
			CreationHelper createHelper = workbook.getCreationHelper();
			// cell style for hyperlinks
			// by default hypelrinks are blue and underlined
			CellStyle hlink_style = workbook.createCellStyle();
			Font hlink_font = workbook.createFont();
			hlink_font.setUnderline(Font.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);
			// hlink_style.setWrapText(true);
			Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_FILE);
			link.setAddress(url);
			cell.setHyperlink(link);
			cell.setCellStyle(hlink_style);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// returns true if sheet is created successfully else false
	public boolean addSheet(String sheetname) {
		FileOutputStream fileOut;
		try {
			workbook.createSheet(sheetname);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// returns true if sheet is removed successfully else false if sheet does not
	// exist
	public boolean removeSheet(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return false;
		FileOutputStream fileOut;
		try {
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// returns true if column is created successfully
	public boolean addColumn(String sheetName, String colName) {
		// System.out.println("**************addColumn*********************");
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return false;
			CellStyle style = workbook.createCellStyle();
			// style.setFillForegroundColor(Color.GREY_40_PERCENT.index);
			// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);
			// cell = row.getCell();
			// if (cell == null)
			// System.out.println(row.getLastCellNum());
			if (row.getLastCellNum() == -1)
				cell = row.createCell(0);
			else
				cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(colName);
			cell.setCellStyle(style);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// removes a column and all the contents
	public boolean removeColumn(String sheetName, int colNum) {
		try {
			if (!isSheetExist(sheetName))
				return false;
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			CellStyle style = workbook.createCellStyle();
			// style.setFillForegroundColor(Color.GREY_40_PERCENT.index);
			CreationHelper createHelper = workbook.getCreationHelper();
			// style.setFillPattern(HSSFCellStyle.NO_FILL);
			for (int i = 0; i < getRowCount(sheetName); i++) {
				row = sheet.getRow(i);
				if (row != null) {
					cell = row.getCell(colNum);
					if (cell != null) {
						cell.setCellStyle(style);
						row.removeCell(cell);
					}
				}
			}
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// find whether sheets exists
	public boolean isSheetExist(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}

	// returns number of columns in a sheet
	public int getColumnCount(String sheetName) {
		// check if sheet exists
		if (!isSheetExist(sheetName))
			return -1;
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		if (row == null)
			return -1;
		return row.getLastCellNum();
	}

	// String sheetName, String testCaseName,String keyword ,String URL,String
	// message
	public boolean addHyperLink(String sheetName, String screenShotColName, String testCaseName, int index, String url,
			String message) {
		// System.out.println("ADDING addHyperLink******************");
		url = url.replace('\\', '/');
		if (!isSheetExist(sheetName))
			return false;
		sheet = workbook.getSheet(sheetName);
		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)) {
				// System.out.println("**caught "+(i+index));
				setCellData(sheetName, screenShotColName, i + index, message, url);
				break;
			}
		}
		return true;
	}

	public int getCellRowNum(String sheetName, String colName, String cellValue) {
		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;
	}

	// true-Y
	// false -N
	public boolean isExecutable(String testName) {
		int rows = getRowCount("TestCases");
		for (int rNum = 2; rNum <= rows; rNum++) {
			String tcid = getCellData("TestCases", "TCID", rNum);
			if (tcid.equals(testName)) {
				String runMode = getCellData("TestCases", "Runmode", rNum);
				if (runMode.equals("N"))
					return false;
				else
					return true;
			}
		}
		return false;// default
	}

	public Object[][] getData(String testCaseName, String testDataSheetName) {
		String sheetName = testDataSheetName;
		int testStartRowNum = 1;
		while (!getCellData(sheetName, 0, testStartRowNum).equals(testCaseName)) {
			testStartRowNum++;
		}
		int colStartRowNum = testStartRowNum + 1;
		int dataStartRowNum = testStartRowNum + 2;
		// calculate rows of data
		int rows = 0;
		while(!(getCellData(sheetName, 0, dataStartRowNum+rows).equals("") || getCellData(sheetName, 0, dataStartRowNum+rows).equals("N"))){
			rows++;
		}
		// calculate total cols
		int cols = 0;
		while (!getCellData(sheetName, cols, colStartRowNum).equals("")) {
			cols++;
		}
		Object[][] data = new Object[rows][1];
		// read the data
		int dataRow = 0;
		Hashtable<String, String> table = null;
		for (int rNum = dataStartRowNum; rNum < dataStartRowNum + rows; rNum++) {
			table = new Hashtable<String, String>();
			for (int cNum = 0; cNum < cols; cNum++) {
				String key = getCellData(sheetName, cNum, colStartRowNum);
				String value = getCellData(sheetName, cNum, rNum);
				table.put(key, value);
			}
			data[dataRow][0] = table;
			dataRow++;
		}
		return data;
	}
}
