package com.myntra.apiTests.erpservices.lms.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.utility.DataOrcUtil;
import com.myntra.lms.client.response.TrackingNumberResponse;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.testbase.BaseTest;

/**
 * GetTrackingNumber
 * @author Shubham gupta
 */
public class GetTrackingNumber extends BaseTest {
	
	@Test(groups = { "Smoke","Regression"}, priority = 2, enabled = false, description = "ID: C91")
	public void dataPrepForGetTrackingNumber() throws IOException, JAXBException{
	/*	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		String EKON = "";
		int noON = 11111;
		for (int i = 0; i < 300; i++) {
			noON++;
			EKON = EKON.concat(",MYNC00000"+noON);
		}
		EKON = EKON.replaceFirst(",", "");

		String EKCOD = "";
		int noCOD = 21111;
		for (int i = 0; i < 300; i++) {
			noCOD++;
			EKCOD = EKCOD.concat(",MYNC00000"+noCOD);
		}
		EKCOD = EKCOD.replaceFirst(",", "");
		DBUtilities.exUpdateQuery("delete from tracking_number where tracking_number in("+EKON.replaceAll(",", "','").replaceFirst("", "'").concat("'")+")", "lms");
		DBUtilities.exUpdateQuery("delete from tracking_number where tracking_number in("+EKCOD.replaceAll(",", "','").replaceFirst("", "'").concat("'")+")", "lms");
		TrackingNumberResponse responseEKNO = lmsServiceHelper.generateTrackingNumberManual("EK", "ON", "", EKON);
		Assert.assertEquals(responseEKNO.getStatus().getStatusType().toString(), "SUCEndCESS", "Unable to upload traking number for EK-ON");
		TrackingNumberResponse responseEKCOD = lmsServiceHelper.generateTrackingNumberManual("EK", "COD", "", EKCOD);
		Assert.assertEquals(responseEKCOD.getStatus().getStatusType().toString(), "SUCCESS", "Unable to upload traking number for EK-COD");*/
		
		//DE GET tracking Numbers
		URL url  = new URL("https://test.delhivery.com/waybill/api/bulk/json/?token=cf3ee76782c42f959370012940a68d22b0756beb&count=200");
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		String DEON = br.readLine();
		DEON = DEON.replaceAll("\"", "");
		
		URL urlCOD  = new URL("https://test.delhivery.com/waybill/api/bulk/json/?token=cf3ee76782c42f959370012940a68d22b0756beb&count=200");
		BufferedReader brCOD = new BufferedReader(new InputStreamReader(urlCOD.openStream()));
		String DECOD = brCOD.readLine();
		DECOD = DECOD.replaceAll("\"", "");
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		
		TrackingNumberResponse responseDEON = lmsServiceHelper.generateTrackingNumberManual("DE", "ON", "", DEON);
		Assert.assertEquals(responseDEON.getStatus().getStatusType().toString(), "SUCCESS", "Unable to upload traking number for DE-ON");
		TrackingNumberResponse responseDECOD = lmsServiceHelper.generateTrackingNumberManual("DE", "COD", "", DECOD);
		Assert.assertEquals(responseDECOD.getStatus().getStatusType().toString(), "SUCCESS", "Unable to upload traking number for DE-COD");
	}
	
	@Test(groups = { "Smoke","Regression"}, priority = 2, dataProvider = "generateTrackingNumberCombinations", description = "ID: C92, Hit get tracking number api "
			+ "with multiple combination multiple time")
	public void getTrackingNumber(String courier, String wh, String isCod, String pinCode, String shippingMethod) throws Exception{
		Set<String> tn = new HashSet<String>();
		for (int i = 0; i < 10; i++) {
			String trackingNumber = hitTrackingNumber(courier, wh, isCod, pinCode, shippingMethod);
			Assert.assertEquals(tn.add(trackingNumber), true, "TrackingNumber is not unique");
		}
	}

	/**
	 * hitTrackingNumber
	 * @param courier
	 * @param wh
	 * @param isCod
	 * @param pinCode
	 * @param shippingMethod
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
     * @throws JAXBException
     */
	public String hitTrackingNumber(String courier, String wh, String isCod, String pinCode, String shippingMethod) throws Exception{
		LmsServiceHelper lmsServiceHelper= new LmsServiceHelper();
		TrackingNumberResponse trackingNumberResponse = lmsServiceHelper.getTrackingNumber(courier, wh, isCod, pinCode, shippingMethod);
		Assert.assertEquals(trackingNumberResponse.getStatus().getStatusType().toString(), "SUCCESS"); 
		String trackingNumber = trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber().toString();
		return trackingNumber;
	}
	
	//Courier: ML:EK:DE:BD
	//WH: 1:28:36
	//PaymentMode: true:false
	//pincode: 560068:110011
	@DataProvider/*(parallel = true)*/
	public static Object [][] generateTrackingNumberCombinations(ITestContext testContext)
	{
		ArrayList<String> courier = new ArrayList<String>();
		ArrayList<String> wareHouses = new ArrayList<String>();
		ArrayList<String> paymentModes = new ArrayList<String>();
		ArrayList<String> pincodes = new ArrayList<String>();
		ArrayList<String> shippingMethod = new ArrayList<String>();
		
		courier.add("ML");
		courier.add("EK");
		courier.add("DE");
//		courier.add("BD");
		
		wareHouses.add("1");
//		wareHouses.add("28");
		wareHouses.add("36");
		
		paymentModes.add("true");
		paymentModes.add("false");
		
		pincodes.add("560068");
		pincodes.add("110011");
		
		shippingMethod.add("NORMAL");
		shippingMethod.add("EXPRESS");
		
		DataOrcUtil combinations = new DataOrcUtil(courier, wareHouses, paymentModes, pincodes, shippingMethod);
		return combinations.Explode();
	}

	/**
	 * updateEK bulk tracking numbers(To be used when we need to bulk upload)
	 * @throws JAXBException
	 * @throws IOException
     */
	@Test(enabled = false /*This is to be used only when you want to upload EK tracking numbers in Bulk*/,description = "ID:C93")
	public void updateTrackingNumberEK() throws JAXBException, IOException{
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		String EKON = "";
		int noON = 1000000001;
		for (int i = 0; i < 200000; i++) {
			noON++;
			EKON = EKON.concat(",MYNC"+noON);
		}
		EKON = EKON.replaceFirst(",", "");

		String EKCOD = "";
		int noCOD = 2000000001;
		for (int i = 0; i < 200000; i++) {
			noCOD++;
			EKCOD = EKCOD.concat(",MYNC"+noCOD);
		}
		EKCOD = EKCOD.replaceFirst(",", "");
		DBUtilities.exUpdateQuery("delete from tracking_number where tracking_number in("+EKON.replaceAll(",", "','").replaceFirst("", "'").concat("'")+")", "lms");
		DBUtilities.exUpdateQuery("delete from tracking_number where tracking_number in("+EKCOD.replaceAll(",", "','").replaceFirst("", "'").concat("'")+")", "lms");
		TrackingNumberResponse responseEKNO = lmsServiceHelper.generateTrackingNumberManual("EK", "ON", "", EKON);
		Assert.assertEquals(responseEKNO.getStatus().getStatusType().toString(), "SUCCESS", "Unable to upload traking number for EK-ON");
		TrackingNumberResponse responseEKCOD = lmsServiceHelper.generateTrackingNumberManual("EK", "COD", "", EKCOD);
		Assert.assertEquals(responseEKCOD.getStatus().getStatusType().toString(), "SUCCESS", "Unable to upload traking number for EK-COD");
		System.out.println("upload Done!!");
	}

	/**
	 * updateDE bulk tracking numbers(To be used when we need to bulk upload)
	 * @throws JAXBException
	 * @throws IOException
     */
	@Test(enabled = false /*This is to be used only when you want to upload DE tracking numbers in Bulk*/, description = "C94")
	public void updateTrackingNumberDE() throws JAXBException, IOException{
		URL url  = new URL("https://test.delhivery.com/waybill/api/bulk/json/?token=cf3ee76782c42f959370012940a68d22b0756beb&count=200000");
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		String DEON = br.readLine();
		DEON = DEON.replaceAll("\"", "");
		
		URL urlCOD  = new URL("https://test.delhivery.com/waybill/api/bulk/json/?token=cf3ee76782c42f959370012940a68d22b0756beb&count=200000");
		BufferedReader brCOD = new BufferedReader(new InputStreamReader(urlCOD.openStream()));
		String DECOD = brCOD.readLine();
		DECOD = DECOD.replaceAll("\"", "");
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		
		TrackingNumberResponse responseDEON = lmsServiceHelper.generateTrackingNumberManual("DE", "ON", "", DEON);
		Assert.assertEquals(responseDEON.getStatus().getStatusType().toString(), "SUCCESS", "Unable to upload traking number for DE-ON");
		TrackingNumberResponse responseDECOD = lmsServiceHelper.generateTrackingNumberManual("DE", "COD", "", DECOD);
		Assert.assertEquals(responseDECOD.getStatus().getStatusType().toString(), "SUCCESS", "Unable to upload traking number for DE-COD");
	}


	@Test(enabled = false, description = "ID: C95")
	public void addBDIPTrackingNumber() throws JAXBException, IOException {
		String IPON = "";
		long noON = 50000000001L;
		for (int i = 0; i < 50000; i++) {
			noON++;
			IPON = IPON.concat(",BD"+noON);
		}
		IPON = IPON.replaceFirst(",", "");

		String IPCOD = "";
		long noCOD = 60000000001L;
		for (int i = 0; i < 50000; i++) {
			noCOD++;
			IPCOD = IPCOD.concat(",BD"+noCOD);
		}
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		TrackingNumberResponse responseDEON = lmsServiceHelper.generateTrackingNumberManual("IP", "ON", "", IPON);
		Assert.assertEquals(responseDEON.getStatus().getStatusType().toString(), "SUCCESS", "Unable to upload traking number for DE-ON");
		TrackingNumberResponse responseDECOD = lmsServiceHelper.generateTrackingNumberManual("IP", "COD", "", IPCOD);
		Assert.assertEquals(responseDECOD.getStatus().getStatusType().toString(), "SUCCESS", "Unable to upload traking number for DE-COD");
	}

	@Test(enabled = false, description = "ID: C96")
	public void updateJabongTrackingNumberInTestDB(){
		long generateTN = 20000;
		long maxId = (long)DBUtilities.exSelectQueryForSingleRecord("select max(id) from tracking_number","myntra_test").get("max(id)");
		StringBuilder values = new StringBuilder();
		long n = maxId+generateTN;
		for (long i=maxId;i<n;i++){
			values.append(",(0)");
		}
		String insertQ = "insert into tracking_number (status) values "+values.toString().replaceFirst(",","");
		DBUtilities.exUpdateQuery(insertQ,"myntra_test");
	}
	
}
