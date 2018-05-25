package com.myntra.apiTests.erpservices.taxMaster;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.ServiceException;
import com.myntra.lordoftherings.parser.GoogleSheetsApiTest;
import com.myntra.taxmaster.client.entry.request.CustomerGSTRequestEntry;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.taxmaster.client.entry.request.CustomerVatRequestEntry;
import org.testng.annotations.DataProvider;
import com.myntra.commons.codes.StatusResponse.Type;

// TODO: Auto-generated Javadoc

/**
 * The Class VatRuleEngineDP_global.
 *
 * @author : Sneha Agarwal 18/08/2016
 */

public class VatRuleEngineDP_global {

/** The log. */

	static org.slf4j.Logger log = LoggerFactory.getLogger(VatRuleEngineDP_global.class);
/** The list. */

	static HashMap<String,String> hp_state = new HashMap<>();
/** The random. */

	private static double random;
/** The courier code. */

	private static String courierCode = "ML";
/** The date. */
	private static Date date = new Date();
	private static final String CLIENT_ID = "myntra@myntra-148806.iam.gserviceaccount.com";
	private static final String filePath = System.getProperty("user.dir")+"/Myntra-taxmaster.p12";
	private static final String spreadsheeetTitle="Global Tax Master Configuration";
	private static final String worksheetName="OMS Global Tax Master";
	static GoogleSheetsApiTest sheetAPI = new GoogleSheetsApiTest();

 /** Payload.
	 *
	 * @param testContext the test context
	 * @return the object[][]
	 * @throws IOException Signals that an I/O exception has occurred.
*/
	@DataProvider(parallel = true)
	public static Object[][] payload(ITestContext testContext) throws IOException, GeneralSecurityException, ServiceException, URISyntaxException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();

		hp_state.put("karnataka","KAR");
		hp_state.put("haryana","HAR");
		hp_state.put("delhi","DEL");
		hp_state.put("maharasthtra","MAH");
		hp_state.put("telangana","TEL");
		hp_state.put("westbengal","WES");

		List<ListEntry> listfeed = sheetAPI.testConnectToSpreadSheet(CLIENT_ID,filePath,spreadsheeetTitle,worksheetName);
		for (ListEntry listentry : listfeed) {
			Double columnFourValue = null;
			Double columnFiveValue = null;
			CustomerVatRequestEntry cvr = new CustomerVatRequestEntry();
			CustomElementCollection cec = listentry.getCustomElements();
			for(String keys:cec.getTags()){
				String key_name=keys.toString();
				Double cellValueDouble = null;
				switch (key_name) {
					case "articleid": {
						String articleId=cec.getValue(key_name);
						log.info("Article Id: " + articleId);
						cvr.setArticleId(Long.parseLong(articleId));
						break;
					}
					case "material": {
							cvr.setMaterial(cec.getValue(key_name));
						break;
					}
					case "mrprangemin": {
						if (cec.getValue(key_name) != null) {
							cellValueDouble=Double.parseDouble(cec.getValue(key_name));
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
					case "mrprangemax": {
						if (cec.getValue(key_name) != null) {
							cellValueDouble=Double.parseDouble(cec.getValue(key_name));
							columnFiveValue = cellValueDouble;
						}
						if (columnFourValue != null && columnFiveValue != null) {
							random = genarateRandomNumber(columnFourValue, columnFiveValue);
							cvr.setMrpValue(random);
						} else if ((columnFourValue != null) && (columnFiveValue == null)) {
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
					case "karnataka":
					case "haryana":
					case "delhi":
					case "maharasthtra":
					case "telangana":
					case "westbengal": {
						if (cec.getValue(key_name) != null && !cec.getValue(key_name).equals("-") && !cec.getValue(key_name).equals("`")) {
							cellValueDouble=Double.parseDouble(cec.getValue(key_name));
							cvr.setDestinationStateCode("" + hp_state.get(key_name));
							cvr.setSourceStateCode("" + hp_state.get(key_name));
							cvr.setCourierCode(courierCode);
							cvr.setDate(date);
							cvr.setMrpValue(random);
							list.add(new Object[] { createNewObject(cvr), cellValueDouble });



							cvr.setMrpValue(columnFourValue);
							list.add(new Object[] { createNewObject(cvr), cellValueDouble });

							cvr.setMrpValue(columnFiveValue);
							list.add(new Object[] { createNewObject(cvr), cellValueDouble });


						}
						else
							cellValueDouble=0.0;
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
		log.info("Adding the object" +newObject.toString());
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

	public static CustomerGSTRequestEntry createRequestObject(String HSNCode, String CourierCode, Date date, String DestinationCode, String Material, Double MRP, String SourceCode) {
		CustomerGSTRequestEntry newObject = new CustomerGSTRequestEntry();
		newObject.setHsnCode(HSNCode);
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
	@DataProvider(parallel = true)
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

	@DataProvider
	public static Object[][] GSTScenarios(ITestContext testContext){
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Date date = new Date();
		list.add(new Object[]{createRequestObject("1","ML",date,"DEL"," ",1000.0,"KAR"),14.5,1.00,1.00});
		list.add(new Object[]{createRequestObject("2","ML",date,"MAH"," ",1000.0,"DEL"),14.5,1.00,1.00});
		list.add(new Object[]{createRequestObject("3","ML",date,"DEL"," ",1000.0,"WES"),14.5,1.00,1.00});
		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}
}
