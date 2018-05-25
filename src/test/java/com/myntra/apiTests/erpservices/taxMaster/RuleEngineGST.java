package com.myntra.apiTests.erpservices.taxMaster;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.taxmaster.client.entry.TaxEntry;
import com.myntra.taxmaster.client.entry.request.CustomerGSTRequestEntry;
import com.myntra.taxmaster.client.response.TaxResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * The Class GSTRuleEngine.
 *
 * @author : Sneha Agarwal 18/08/2016
 */
public class RuleEngineGST extends BaseTest {
	
	/** The init. */
	static Initialize init = new Initialize("/Data/configuration");
	
	/** The log. */
	static org.slf4j.Logger log = LoggerFactory.getLogger(RuleEngineGST.class);
	
	/** The vat helper. */
	VatRuleEngineHelper vatHelper = new VatRuleEngineHelper();
	
	/** The status code success. */
	static int statusCodeSuccess=1001;
	
	Map<String,HashMap<String,String>> hsnCodeMap=new HashMap<>();
		
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" },dataProviderClass = RuleEngineDP_global_GST.class, dataProvider = "payload", enabled = true)
	public void TaxRuleEngineGST(CustomerGSTRequestEntry cvr, Double expectedIGST,Double expectedCGST,Double expectedSGST, Double expectedCessValue, String expectedTaxType) throws IOException, JAXBException, ManagerException{
		String payload = APIUtilities.convertXMLObjectToString(cvr);
		TaxResponse response =null;
		try{
			Svc service = HttpExecutorService.executeHttpService(Constants.TAXMASTER_PATH.GET_TAX_GST, null,SERVICE_TYPE.TAXMASTER_SVC.toString(), HTTPMethods.POST, payload, vatHelper.getTaxMasterHeaderXML());
			response = (TaxResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new TaxResponse());
		}catch(JAXBException exception){
			String hsnCode=cvr.getHsnCode();
			HashMap<String, String> sourceDestinationMap=new HashMap<>();
			sourceDestinationMap.put(cvr.getDestinationStateCode(), cvr.getSourceStateCode());
			hsnCodeMap.put(hsnCode, sourceDestinationMap);
		}
		log.info("HSN CODE :" +cvr.getHsnCode() +" Destination code :" +cvr.getDestinationStateCode() +" Source code :" + cvr.getSourceStateCode() + " MRP :" + cvr.getMrpValue());
		validateTaxRate(response,expectedIGST,expectedCGST,expectedSGST,payload);
		//System.out.println(response.getData().get(0).getTaxType());
	}
	/**
	 * Validate tax rate.
	 * @param response
	 * @param expectedIGST
	 * @param expectedCGST
	 * @param expectedSGST
	 * @param payload
	 */
	public void validateTaxRate(TaxResponse response, Double expectedIGST,Double expectedCGST,Double expectedSGST, String payload) throws ManagerException {
		SoftAssert sft = new SoftAssert();
		String dest=getTagValue(payload,"destinationStateCode");
		String source=getTagValue(payload,"sourceStateCode");
		if(dest.equals("null")&& source.equals("null")) {
			ExceptionHandler.handleEquals(response.getStatus().getStatusCode(), 1026, "payload :" + payload);
			return;
		}

		if(response.getStatus().getStatusCode()==statusCodeSuccess) {
			if(expectedIGST != null){
				for(TaxEntry entry :response.getData()){
					String taxType=entry.getTaxType().name();
					
				if (taxType.equals("IGST")){
					
					Double IGSTRate=entry.getTaxRate();
					 sft.assertEquals(IGSTRate, expectedIGST, "Comparing the tax rates Actual:  " + IGSTRate + "  Expected  " + expectedIGST + "  for payload  " + payload);
				}
			}
			}
			else{
				for(TaxEntry entry :response.getData()){
					String taxType=entry.getTaxType().name();
					
					if (taxType.equals("CGST")){
						Double CGSTRate=entry.getTaxRate();
						 sft.assertEquals(CGSTRate, expectedCGST, "Comparing the tax rates Actual:  " + CGSTRate + "  Expected  " + expectedCGST + "  for payload  " + payload);
					}
					else {
						Double SGSTRate=entry.getTaxRate();
						 sft.assertEquals(SGSTRate, expectedSGST, "Comparing the tax rates Actual:  " + SGSTRate + "  Expected  " + expectedSGST + "  for payload  " + payload);
					}
				}
				
			}	
		}
		else
			sft.assertEquals(response.getStatus().getStatusCode(),statusCodeSuccess,"The response returned is " +response.getStatus().getStatusCode() +" Message is " + response.getStatus().getStatusMessage()+ "  for payload  " + payload);

		sft.assertAll();
	}
	
	/**
	 * Tax rule engine negative.
	 *
	 * @param testScenario the test scenario
	 * @param cvr the cvr
	 * @param statusCode the status code
	 * @param statusMessage the status message
	 * @param statusType the status type
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws JAXBException the JAXB exception
	 */
	/*@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" },dataProviderClass = VatRuleEngineDP_global.class,dataProvider = "negativescenarios",enabled = true)
	public void TaxRuleEngineNegative(String testScenario,CustomerVatRequestEntry cvr,int statusCode,String statusMessage,Type statusType) throws IOException, JAXBException{
		String payload = APIUtilities.convertXMLObjectToString(cvr);
		Svc service = HttpExecutorService.executeHttpService(Constants.TAXMASTER_PATH.GET_TAX_VAT, null,SERVICE_TYPE.TAXMASTER_SVC.toString(), HTTPMethods.POST, payload, vatHelper.getTaxMasterHeaderXML());
		TaxResponse response = (TaxResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new TaxResponse());
		log.info("Article ID :" +cvr.getArticleId() +" Destination code :" +cvr.getDestinationStateCode() +" Source code :" + cvr.getSourceStateCode() + " MRP :" + cvr.getMrpValue());
		validateError(response,statusCode,statusMessage,statusType);
	}
*/
	/**
	 * Validate error.
	 *
	 * @param response the response
	 * @param statusCode the status code
	 * @param statusMessage the status message
	 * @param statusType the status type
	 */
	private void validateError(TaxResponse response, int statusCode, String statusMessage, Type statusType) {
		SoftAssert sft = new SoftAssert();
		sft.assertEquals(response.getStatus().getStatusCode(), statusCode,"Expected StatusCode : " + statusCode + " Actual : "+response.getStatus().getStatusCode());
		sft.assertTrue(response.getStatus().getStatusMessage().contains(statusMessage),"Expected StatusMessage: " +statusMessage + " Actual: " + response.getStatus().getStatusMessage());
		sft.assertEquals(response.getStatus().getStatusType(), statusType,"Expected StatusType : " + statusType + " Actual : "+response.getStatus().getStatusType());
		sft.assertAll();
	}

	/**
	 * Tax rule engine.
	 *
	 * @return the double
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws JAXBException the JAXB exception
	 * @throws Exception the exception
	 */
	/*public Double TaxRuleEngine(CustomerVatRequestEntry cvr) throws IOException, JAXBException,Exception{

		String payload = APIUtilities.convertXMLObjectToString(cvr);
		Svc service = HttpExecutorService.executeHttpService(Constants.TAXMASTER_PATH.GET_TAX_VAT, null,SERVICE_TYPE.TAXMASTER_SVC.toString(), HTTPMethods.POST, payload, vatHelper.getTaxMasterHeaderXML());
		TaxResponse response = (TaxResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new TaxResponse());
		if(response.getData().size()>0)
			return response.getData().get(0).getTaxRate();
		else
            return -1.0;
	}*/


	public static String getTagValue(String xml, String tagName){
		return xml.split("<"+tagName+">")[1].split("</"+tagName+">")[0];
	}

	@AfterClass
	public void afterClass(){
		for(String hsnCode:hsnCodeMap.keySet()){
			log.debug("HSN Code="+ hsnCode);
			Map<String,String> sourceDestinationMap=hsnCodeMap.get(hsnCode);
			for(String sourceCode: sourceDestinationMap.keySet()){
				log.debug("Source State Code="+ sourceCode);
				log.debug("Destination State Code="+ sourceDestinationMap.get(sourceCode));
			}
		}
	}
}
