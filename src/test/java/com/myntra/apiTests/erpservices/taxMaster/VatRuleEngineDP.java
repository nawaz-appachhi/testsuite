package com.myntra.apiTests.erpservices.taxMaster;

import com.google.gdata.util.ServiceException;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.parser.GoogleSheetsApiTest;
import com.myntra.taxmaster.client.entry.request.CustomerVatRequestEntry;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.*;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.*;

//import com.google.gdata.data.spreadsheet.*;


// TODO: Auto-generated Javadoc

/**
 * The Class VatRuleEngineDP.
 *
 * @author : Sneha Agarwal 18/08/2016
 */

public class VatRuleEngineDP {
	
	/** The log. */
	static org.slf4j.Logger log = LoggerFactory.getLogger(VatRuleEngineDP.class);
	
	/** The excelbook. */
	private static String excelbook = System.getProperty("user.dir")
			+ "/src/test/java/com/myntra/apiTests/erpservices/taxMaster/Tax.xlsx";
	
	/** The list. */
	static ArrayList<Object[]> list = new ArrayList<Object[]>();
	
	/** The is. */
	private static InputStream is;
	
	/** The random. */
	private static double random;
	
	/** The courier code. */
	private static String courierCode = "ML";
	
	/** The date. */
	private static Date date = new Date();
	
	/** The sheet. */
	static XSSFSheet sheet;

	/**
	 * The Enum state.
	 */
	public enum state {
		
		/** The kar. */
		KAR(6), 
 /** The har. */
 HAR(7), 
 /** The mah. */
 MAH(9), 
 /** The del. */
 DEL(8), 
 /** The tel. */
 TEL(10), 
 /** The wes. */
 WES(11);
		
		/** The state no. */
		private int stateNo;
		
		/** The map. */
		private static Map<Integer, state> map = new HashMap<Integer, state>();
		static {
			for (state stateEnum : state.values()) {
				map.put(stateEnum.stateNo, stateEnum);
			}
		}

		/**
		 * Instantiates a new state.
		 *
		 * @param stateNum the state num
		 */
		private state(final int stateNum) {
			stateNo = stateNum;
		}

		/**
		 * Value of.
		 *
		 * @param num the num
		 * @return the state
		 */
		public static state valueOf(int num) {
			return map.get(num);
		}

	}
	static GoogleSheetsApiTest sheetAPI = new GoogleSheetsApiTest();

	/**
	 * Payload.
	 *
	 * @param testContext the test context
	 * @return the object[][]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@DataProvider
	public static Object[][] payload(ITestContext testContext) throws IOException, GeneralSecurityException, ServiceException, URISyntaxException {

		try {
			is = new FileInputStream(new File(excelbook));
		} catch (FileNotFoundException fnf) {
			log.info(fnf.getMessage());
		}
		XSSFWorkbook workbook = new XSSFWorkbook(is);
		sheet = workbook.getSheetAt(0);
		Iterator<Row> itr = sheet.iterator();
		itr.next();
		while (itr.hasNext()) {
			Row row = itr.next();
			Iterator<Cell> cellitr = row.cellIterator();
			Double columnFourValue = null;
			Double columnFiveValue = null;
			CustomerVatRequestEntry cvr = new CustomerVatRequestEntry();
			int column = -1;
			while (cellitr.hasNext()) {
				Cell cell = cellitr.next();
				String cellValueString = null;
				Double cellValueDouble = null;
				long cellValueLong = 0;
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC: {
					cellValueDouble = (Double) cell.getNumericCellValue();
					cellValueLong = (long) cell.getNumericCellValue();
					column++;
					break;
				}
				case Cell.CELL_TYPE_STRING: {
					cellValueString = cell.getStringCellValue();
					column++;
					break;
				}
				case Cell.CELL_TYPE_FORMULA:
					switch (cell.getCachedFormulaResultType()) {
					case Cell.CELL_TYPE_NUMERIC: {
						cellValueDouble = (Double) cell.getNumericCellValue();
						cellValueLong = (long) cell.getNumericCellValue();
						column++;
						break;
					}
					case Cell.CELL_TYPE_STRING: {
						cellValueString = cell.getStringCellValue();
						column++;
						break;
					}
					}
					break;
				case Cell.CELL_TYPE_BOOLEAN: {
					column++;
					break;
				}
				case Cell.CELL_TYPE_BLANK: {
					cellValueString = "Blank";
					column++;
					break;
				}
				default: {
					column++;
				}
				}
				switch (column) {
				case 0: {
					cvr.setArticleId(cellValueLong);
					break;
				}
				case 3: {
					if (cellValueString == "Blank")
						cvr.setMaterial("");
					else
						cvr.setMaterial(cellValueString);
					break;
				}
				case 4: {
					if (cellValueDouble != null) {
						if(cellValueDouble>0.00) {
							cvr.setMrpValue(cellValueDouble+0.01);
							columnFourValue = cvr.getMrpValue();
						}
						else
						{
							cvr.setMrpValue(cellValueDouble);
							columnFourValue = cvr.getMrpValue();
						}

					} else
						columnFourValue = 0.00;
					break;
				}
				case 5: {
					if (cellValueDouble != null) {
						columnFiveValue = cellValueDouble;
					}
					if (columnFourValue != null && columnFiveValue != null) {
						random = genarateRandomNumber(columnFourValue, columnFiveValue);
						cvr.setMrpValue(random);
					} else if (columnFourValue != null && columnFiveValue == null) {
						columnFiveValue = 999999.0;
						random = genarateRandomNumber(columnFourValue, columnFiveValue);
						cvr.setMrpValue(random);
					} else {
						columnFourValue = 0.0;
						columnFiveValue = 999999.0;
						random = genarateRandomNumber(columnFourValue, columnFiveValue);
						cvr.setMrpValue(random);
					}
					break;
				}
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:
				case 11: {
					if (cellValueDouble != null) {
						cvr.setDestinationStateCode("" + state.valueOf(column));
						cvr.setSourceStateCode("" + state.valueOf(column));
						cvr.setCourierCode(courierCode);
						cvr.setDate(date);
						cvr.setMrpValue(random);
						list.add(new Object[] { createNewObject(cvr), cellValueDouble });

						cvr.setMrpValue(columnFourValue);
						list.add(new Object[] { createNewObject(cvr), cellValueDouble });

						cvr.setMrpValue(columnFiveValue);
						list.add(new Object[] { createNewObject(cvr), cellValueDouble });


					}
				}
				}
			}
		}
		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}

	/**
	 * Creates the new object.
	 *
	 * @param oldObject the old object
	 * @return the customer vat request entry
	 */
	public static CustomerVatRequestEntry createNewObject(CustomerVatRequestEntry oldObject) {
		CustomerVatRequestEntry newObject = new CustomerVatRequestEntry();
		newObject.setArticleId(oldObject.getArticleId());
		newObject.setCourierCode(oldObject.getCourierCode());
		newObject.setDate(oldObject.getDate());
		newObject.setDestinationStateCode(oldObject.getDestinationStateCode());
		newObject.setMaterial(oldObject.getMaterial());
		newObject.setMrpValue(oldObject.getMrpValue());
		newObject.setSourceStateCode(oldObject.getSourceStateCode());
		return newObject;
	}

	/**
	 * Creates the request object.
	 *
	 * @param ArticleId the article id
	 * @param CourierCode the courier code
	 * @param date the date
	 * @param DestinationCode the destination code
	 * @param Material the material
	 * @param MRP the mrp
	 * @param SourceCode the source code
	 * @return the customer vat request entry
	 */
	public static CustomerVatRequestEntry createRequestObject(Long ArticleId,String CourierCode,Date date, String DestinationCode,String Material,Double MRP,String SourceCode) {
		CustomerVatRequestEntry newObject = new CustomerVatRequestEntry();
		newObject.setArticleId(ArticleId);
		newObject.setCourierCode(CourierCode);
		newObject.setDate(date);
		newObject.setDestinationStateCode(DestinationCode);
		newObject.setMaterial(Material);
		newObject.setMrpValue(MRP);
		newObject.setSourceStateCode(SourceCode);
		return newObject;
	}

	/**
	 * Genarate random number.
	 *
	 * @param min the min
	 * @param max the max
	 * @return the double
	 */
	private static Double genarateRandomNumber(Double min, Double max) {
		Random r = new Random();
		return r.nextInt((int) ((max - min) + 1)) + min;
	}

	/**
	 * Negativescenarios.
	 *
	 * @param testContext the test context
	 * @return the object[][]
	 */
	@DataProvider
	public static Object[][] negativescenarios(ITestContext testContext){
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Date date = new Date();
		list.add(new Object[]{"For random article ID",VatRuleEngineHelper.createRequestObject(123456789L,"ML",date,"MAH","",30.00,"TEL"),1009,"Tax rule not configured for the input",Type.ERROR});
		list.add(new Object[]{"For incorrect destination/source code",VatRuleEngineHelper.createRequestObject(49L,"ML",date,"ABC","",30.00,"ABC"),1009,"Tax rule not configured for the input",Type.ERROR});
		list.add(new Object[]{"For negative mrp",VatRuleEngineHelper.createRequestObject(49L,"ML",date,"TEL","",-30.00,"TEL"),1001,"Govt. vat calculation should have mrpValue",Type.ERROR});
		list.add(new Object[]{"For empty source code",VatRuleEngineHelper.createRequestObject(49L,"ML",date,"TEL","",30.00,""),1001,"Govt. vat calculation should have sourceStateCode",Type.ERROR});
		list.add(new Object[]{"For empty mrp",VatRuleEngineHelper.createRequestObject(49L,"ML",date,"TEL","",null,"TEL"),1001,"Govt. vat calculation should have mrpValue",Type.ERROR});
		list.add(new Object[]{"For empty articleid",VatRuleEngineHelper.createRequestObject(null,"ML",date,"TEL","",30.00,"TEL"),1001,"Govt. vat calculation should have articleId",Type.ERROR});
		list.add(new Object[]{"For empty date",VatRuleEngineHelper.createRequestObject(49L,"ML",null,"TEL","",30.00,"TEL"),1001,"Govt. vat calculation should have date",Type.ERROR});
		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());

	}

}