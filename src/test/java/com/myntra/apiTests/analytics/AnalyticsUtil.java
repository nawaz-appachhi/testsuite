package com.myntra.apiTests.analytics;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.testng.annotations.DataProvider;


import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;


public class AnalyticsUtil {
	static final String dbURL = "jdbc:redshift://dw-prod.cvrnhetyq5tx.ap-southeast-1.redshift.amazonaws.com:5439/myntra_dw"; 
	static final String MasterUsername = "analysis_user";
	static final String MasterUserPassword = "AdhoCus@123!";
	final static String seperator="___";
	static Connection conn = null;
	static Statement stmt = null;
	static 
	{

		try{

			Class.forName("com.amazon.redshift.jdbc41.Driver");
			Properties props = new Properties();
			//Uncomment the following line if using a keystore.
			//props.setProperty("ssl", "true");  
			props.setProperty("user", MasterUsername);
			props.setProperty("password", MasterUserPassword);
			conn = DriverManager.getConnection(dbURL, props);
			stmt=conn.createStatement();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();

		}
	}
	public static void main(String[] args) {
		System.out.println("Finished connectivity test.");
		AnalyticsUtil obj=new AnalyticsUtil();
		obj.getLastMonthStartDate(-1);
	}

	public ResultSet getDataFromRedshift(String query)
	{
		ResultSet rSet=null;
		try
		{
			rSet=stmt.executeQuery(query);
			return rSet;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}

	}

	@DataProvider
	public static Object[][] readData(String sheetName, String filePath,
			String tableName){
		Object testData[][];
		String line=null;
		Vector<TestDataParam> dataVect=new Vector<TestDataParam>();
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		GregorianCalendar calObj=new GregorianCalendar();
		try {
			BufferedReader rd=new BufferedReader(new InputStreamReader(new FileInputStream("Data/analytics_queries/redshift_bi_queries.txt")));
			while((line=rd.readLine())!=null)
			{
				TestDataParam td=new TestDataParam();
				line=line.split("<->")[1];
				String payload=line.split(seperator)[0];
				String pathToVerify=line.split(seperator)[1];
				String query=line.split(seperator)[2];

				if(payload.contains("<from_yesterday_start>"))
				{
					calObj.add(Calendar.DATE, -1);
					calObj.set(Calendar.HOUR, 0);
					calObj.set(Calendar.MINUTE, 0);
					calObj.set(Calendar.SECOND, 0);
					calObj.set(Calendar.MILLISECOND, 0);
					calObj.setTimeZone(TimeZone.getDefault());
					Date startDate=calObj.getTime();
					long startTime=startDate.getTime();
					calObj.set(Calendar.HOUR, 23);
					calObj.set(Calendar.MINUTE, 59);
					calObj.set(Calendar.SECOND, 59);
					calObj.set(Calendar.MILLISECOND, 999);
					long endTime=calObj.getTimeInMillis();
					payload=payload.replace("<from_yesterday_start>", startTime+"");
					payload=payload.replace("<to_yesterday>", endTime+"");
					td.setPayload(payload);
					td.setQuery(query);
					td.setPathToVerify(pathToVerify);
				}
				dataVect.add(td);
			}
			testData=new Object[dataVect.size()][1];
			for(int k=0;k<dataVect.size();k++)
			{
				testData[k][0]=dataVect.get(k);
			}
			return testData;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public static HSSFCell[] findCell(HSSFSheet sheet, String text) {

		String pos = "start";

		HSSFCell[] cells = new HSSFCell[2];

		for (Row row : sheet) {
			for (Cell cell : row) {
				String cellValue = "";

				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

					cellValue = String.valueOf(cell.getNumericCellValue());
				} else {
					cellValue = cell.getStringCellValue();

				}
				if (text.equals(cellValue)) {
					if (pos.equalsIgnoreCase("start")) {
						cells[0] = (HSSFCell) cell;
						pos = "end";
					} else {
						cells[1] = (HSSFCell) cell;
					}
				}

			}
		}
		return cells;
	}



	@DataProvider
	public static Object[][] readTestData(String sheetName, String filePath,
			String tableName){
		Object testData[][];
		Vector<TestDataParam> dataVect=new Vector<TestDataParam>();

		try {


			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					filePath));
			HSSFSheet sheet = workbook.getSheet(sheetName);
			String group="regression";
			String cellGroup=null;
			HSSFCell[] boundaryCells = findCell(sheet, tableName);
			HSSFCell startCell = boundaryCells[0];
			HSSFCell endCell = boundaryCells[1];
			int startRow = startCell.getRowIndex() + 2;
			int endRow = endCell.getRowIndex() - 1;
			int startCol = startCell.getColumnIndex() + 1;
			int endCol = endCell.getColumnIndex() - 1;
			for (int i = startRow; i < endRow + 1; i++) {
				TestDataParam td=new TestDataParam();
				int dayStart=0;
				int dayEnd=0;

				for (int j = startCol; j < endCol + 1; j++) {

					String colName = String.valueOf(sheet.getRow(startRow - 1).getCell(j).getStringCellValue());

					String cellValue = "";
					try {
						cellValue = String.valueOf(sheet.getRow(i).getCell(j).getStringCellValue());

					} catch (Exception e) {

						cellValue = "";
					}
					
					if(colName.equalsIgnoreCase("Group"))
					{
						cellGroup=cellValue;
					}
					else
						if(colName.equalsIgnoreCase("Test_case_Id"))
						{
							td.setTestCaseId(cellValue);
						}
						else
							if(colName.equalsIgnoreCase("Description"))
							{
								td.setDescription(cellValue);
							}
							else
								if(colName.equalsIgnoreCase("SQL_Query"))
								{
									td.setQuery(cellValue);
								}
								else
									if(colName.equalsIgnoreCase("Day_Start"))
									{
										if(cellValue.equalsIgnoreCase("month_start"))
										{
											cellValue=getMonthDays()*-1+"";
										}
										else
										{
											if(cellValue.startsWith("month_minus"))
											{
												int month= Integer.parseInt(cellValue.split("_")[2]);
												month=month*-1;
												cellValue=(getLastMonthStartDate(month)*-1)+"";
											}
										}

										
										dayStart=Integer.parseInt(cellValue);
										td.setDayStart(dayStart);
									}
									else
										if(colName.equalsIgnoreCase("Day_End"))
										{
											if(cellValue.startsWith("month_minus"))
											{
												int month= Integer.parseInt(cellValue.split("_")[2]);
												month=month*-1;
												int daysDiffFromToday=(getLastMonthEndDate(month)+2)*-1;
												cellValue=daysDiffFromToday+"";
											}

											dayEnd=Integer.parseInt(cellValue);
											td.setDayEnd(dayEnd);

										}
										else
											if(colName.equalsIgnoreCase("Payload"))
											{
												long startTime=getDayStartTime(dayStart);
												long endTime=getDayEndTime(dayEnd);
												String payload=cellValue;

												payload=payload.replace("<from_yesterday_start>", startTime+"");
												payload=payload.replace("<to_yesterday>", endTime+"");
												td.setPayload(payload);
											}
											else
												if(colName.equalsIgnoreCase("ParamPath"))
												{
													td.setPathToVerify(cellValue);
												}
												else
													if(colName.equalsIgnoreCase("Query_Column"))
													{
														td.setQuyeryColumn(cellValue);
													}


				}
				if(cellGroup.equalsIgnoreCase(group))
					dataVect.add(td);
			}
			testData=new Object[dataVect.size()][1];
			for(int k=0;k<dataVect.size();k++)
			{
				testData[k][0]=dataVect.get(k);
			}
			return testData;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static long getDayStartTime(int dayIndex)
	{
		try
		{
			Calendar calObj=null;
			calObj= Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			calObj.add(java.util.Calendar.DAY_OF_YEAR, dayIndex);
			int day=calObj.get(java.util.Calendar.DAY_OF_MONTH);
			int month=calObj.get(java.util.Calendar.MONTH);
			int year=calObj.get(java.util.Calendar.YEAR);
			month++;
			String startDateStr=day+"/"+month+"/"+year+" 00:00:00 ";
			Date startDate=sdf.parse(startDateStr);
			return startDate.getTime();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}

	public static int getMonthDays()
	{
		Calendar calObj=Calendar.getInstance();
		int dayOfMonth=calObj.get(Calendar.DAY_OF_MONTH)-1;
		System.out.println(dayOfMonth);
		return dayOfMonth;

	}

	public static int getLastMonthStartDate(int monthIndex)
	{
		try
		{
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			Calendar calObj=Calendar.getInstance();
			calObj.add(java.util.Calendar.MONTH, monthIndex);
			int day=1;
			int month=calObj.get(java.util.Calendar.MONTH)+1;
			int year=calObj.get(java.util.Calendar.YEAR);
			String startDateStr=day+"/"+month+"/"+year+" 00:00:00 ";
			Date startDate=sdf.parse(startDateStr);
			int diffDays=(int)getDateDiffInDays(startDate.getTime());
			return diffDays;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}

	public static long getDateDiffInDays(long timeInMS)
	{
		long diff=new Date().getTime()-timeInMS;
		long diffDays=TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		System.out.println(diffDays);
		return diffDays;
	}



	public static int getDaysInMonth(int year,int month,int day)
	{
		GregorianCalendar mycal = new GregorianCalendar(year, month, day);
		int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return daysInMonth;
	}
	public static int getLastMonthEndDate(int monthIndex)
	{
		try
		{
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			Calendar calObj=Calendar.getInstance();
			calObj.add(java.util.Calendar.MONTH, monthIndex);
			int day=1;
			int month=calObj.get(java.util.Calendar.MONTH)+1;
			int year=calObj.get(java.util.Calendar.YEAR);
			int days=getDaysInMonth(year, month, day);
			String endDateStr=days+"/"+month+"/"+year+" 23:59:59 ";
			Date endDate=sdf.parse(endDateStr);
			int diffDays=(int)getDateDiffInDays(endDate.getTime());
			return diffDays;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}

	public static long getDayEndTime(int dayIndex)
	{

		try
		{
			Calendar calObj=null;
			calObj= Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			calObj.add(java.util.Calendar.DAY_OF_MONTH, dayIndex);
			int day=calObj.get(java.util.Calendar.DAY_OF_MONTH);
			int month=calObj.get(java.util.Calendar.MONTH);
			int year=calObj.get(java.util.Calendar.YEAR);
			month++;
			String endDateStr=day+"/"+month+"/"+year+" 23:59:59 ";
			Date endDate=sdf.parse(endDateStr);
			return endDate.getTime();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}


}
class TestDataParam
{
	String query;
	String payload;
	String pathToVerify;
	String testCaseId;
	String description;
	String quyeryColumn;
	int dayStart;
	int dayEnd;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public String getPathToVerify() {
		return pathToVerify;
	}
	public void setPathToVerify(String pathToVerify) {
		this.pathToVerify = pathToVerify;
	}
	public String getQuyeryColumn() {
		return quyeryColumn;
	}
	public void setQuyeryColumn(String quyeryColumn) {
		this.quyeryColumn = quyeryColumn;
	}
	public int getDayStart() {
		return dayStart;
	}
	public void setDayStart(int dayStart) {
		this.dayStart = dayStart;
	}
	public int getDayEnd() {
		return dayEnd;
	}
	public void setDayEnd(int dayEnd) {
		this.dayEnd = dayEnd;
	}


}