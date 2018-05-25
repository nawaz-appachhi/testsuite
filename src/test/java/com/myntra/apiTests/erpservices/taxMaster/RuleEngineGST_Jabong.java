package com.myntra.apiTests.erpservices.taxMaster;

import java.io.IOException;

import com.myntra.apiTests.erpservices.Constants;
import com.myntra.taxmaster.client.entry.TaxEntry;
import com.myntra.taxmaster.client.entry.request.CustomerGSTRequestEntry;
import org.slf4j.LoggerFactory;
import javax.xml.bind.JAXBException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.taxmaster.client.response.TaxResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.commons.codes.StatusResponse.Type;

// TODO: Auto-generated Javadoc
/**
 * The Class GSTRuleEngine.
 *
 * @author : Sneha Agarwal 18/08/2016
 */

public class RuleEngineGST_Jabong extends BaseTest {
	
	/** The init. */
	static Initialize init = new Initialize("/Data/configuration");
	
	/** The log. */
	static org.slf4j.Logger log = LoggerFactory.getLogger(RuleEngineGST_Jabong.class);
	
	/** The vat helper. */
	VatRuleEngineHelper vatHelper = new VatRuleEngineHelper();
	
	/** The status code success. */
	static int statusCodeSuccess=1001;
		
	/**
	 * Tax rule engine.
	 *
	 * @param cvr the cvr
	 * @param expectedVat the expected vat
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws JAXBException the JAXB exception
	 */
	/*@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" },dataProviderClass = VatRuleEngineDP_global.class, dataProvider = "payload", enabled = true)
	public void TaxRuleEngineVAT(CustomerVatRequestEntry cvr,Double expectedVat) throws IOException, JAXBException{
		String payload = APIUtilities.convertXMLObjectToString(cvr);
		Svc service = HttpExecutorService.executeHttpService(Constants.TAXMASTER_PATH.GET_TAX_GST, null,SERVICE_TYPE.TAXMASTER_SVC.toString(), HTTPMethods.POST, payload, vatHelper.getTaxMasterHeaderXML());
		TaxResponse response = (TaxResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new TaxResponse());
		log.info("Article ID :" +cvr.getArticleId() +" Destination code :" +cvr.getDestinationStateCode() +" Source code :" + cvr.getSourceStateCode() + " MRP :" + cvr.getMrpValue());
		validateTaxRate(response,expectedVat,payload);
	}
*/
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" },dataProviderClass = RuleEngineDP_global_GST.class, dataProvider = "payload", enabled = true)
	public void TaxRuleEngineGST(CustomerGSTRequestEntry cvr, Double expectedIGST,Double expectedCGST,Double expectedSGST) throws IOException, JAXBException{
		String payload = APIUtilities.convertXMLObjectToString(cvr);
		Svc service = HttpExecutorService.executeHttpService(Constants.TAXMASTER_PATH.GET_TAX_GST, null,SERVICE_TYPE.TAXMASTER_JABONG_SVC.toString(), HTTPMethods.POST, payload, vatHelper.getTaxMasterHeaderXML());
		TaxResponse response = (TaxResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new TaxResponse());
		log.info("HSN CODE :" +cvr.getHsnCode() +" Destination code :" +cvr.getDestinationStateCode() +" Source code :" + cvr.getSourceStateCode() + " MRP :" + cvr.getMrpValue());
		validateTaxRate(response,expectedIGST,expectedCGST,expectedSGST,payload);
		//System.out.println(response.getData().get(0).getTaxType());
	}


	/**
	 * Validate tax rate.
	 *
	 * @param response the response
	 * @param expectedVat the expected vat
	 * @param payload the payload
	 */
	public void validateTaxRate(TaxResponse response, Double expectedIGST,Double expectedCGST,Double expectedSGST,String payload){
		SoftAssert sft = new SoftAssert();
		if(response.getStatus().getStatusCode()==statusCodeSuccess) {
			if(expectedIGST != null){
				for(TaxEntry entry :response.getData()){
					String taxType=entry.getTaxType().name();
					
				if (taxType.equals("IGST")){
					
				Double actualVat = response.getData().iterator().next().getTaxRate();
			    sft.assertEquals(actualVat, expectedIGST, "Comparing the IGST tax rates Actual:  " + actualVat + "  Expected  " + expectedIGST + "  for payload  " + payload);
				}
			}
			}
			else{
				for(TaxEntry entry :response.getData()){
					String taxType=entry.getTaxType().name();
					
					if (taxType.equals("CGST")){
						Double CGSTRate=entry.getTaxRate();
						 sft.assertEquals(CGSTRate, expectedCGST, "Comparing the CGST tax rates Actual:  " + CGSTRate + "  Expected  " + expectedCGST + "  for payload  " + payload);
					}
					else {
						Double SGSTRate=entry.getTaxRate();
						 sft.assertEquals(SGSTRate, expectedSGST, "Comparing the SGST tax rates Actual:  " + SGSTRate + "  Expected  " + expectedSGST + "  for payload  " + payload);
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
	 * @param cvr the cvr
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

}
