package com.myntra.apiTests.erpservices.Serviceability.test;

import com.myntra.apiTests.common.Constants.Headers;
import com.myntra.apiTests.erpservices.Serviceability.test.DP.ServiceabilityPayloadGenerator;
import com.myntra.apiTests.erpservices.lms.Helper.LMSUtils;
import com.myntra.lms.serviceabilityV2.response.CourierServiceabilityInfoResponseV2;
import com.myntra.serviceability.client.response.OrderServiceabilityDetailResponse;
import com.myntra.serviceability.client.util.PaymentMode;
import com.myntra.serviceability.client.util.ServiceType;
import com.myntra.serviceability.client.util.ShippingMethod;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

public class MT_Compare {
	
	private static Logger log = Logger.getLogger(MT_Compare.class);
	
	//Setting the URL here , just in case we want to deploy on 2 different machines on  Prod

	public static final String baseUrl1 = "http://lms.scmqa.myntra.com";
	public static final String baseUrl2 = "http://lms.scmqa.myntra.com";
	private LMSUtils lmsUtils = new LMSUtils();
	private ServiceabilityPayloadGenerator serviceabilityPayloadGenerator = new ServiceabilityPayloadGenerator();
	
	
	
	@Test
	public void comparePromiseDateAndFetchDetail(ITestContext testContext) throws JsonParseException, JsonMappingException, IOException, JAXBException {
		
		String fileName = "../quickbeam/src/test/java/com/myntra/apiTests/erpservices/Serviceability/test/pincode.csv";
		List<Long> pincodeList = serviceabilityPayloadGenerator.getPincodeList(fileName);
		//Just in case we want to check the total invocations
		
		int currentCount = testContext.getAllTestMethods()[0].getCurrentInvocationCount();
		
		pincodeList.parallelStream().forEach( pincode -> {
			
			try {
				
				List<String> payloadList = serviceabilityPayloadGenerator.getPayloadList(pincode);
				
					
					HashMap<String, String> requestHeader = Headers.getLmsHeaderXML();
					//Write own post method because the Base urls are different , we are not extending BaseTest
					String responseMessage1 = lmsUtils.post(baseUrl1, "/myntra-lms-service/serviceabilityv2/promiseDates", payloadList.get(0), requestHeader);
					String responseMessage2 = lmsUtils.post(baseUrl2, "/myntra-lms-service/v2/promiseDate/fetchDetail", payloadList.get(1), requestHeader);
					CourierServiceabilityInfoResponseV2 response1 = LMSUtils.getJavaFromXml(responseMessage1, CourierServiceabilityInfoResponseV2.class);
					OrderServiceabilityDetailResponse response2 =  LMSUtils.getJavaFromXml_MT(responseMessage2, OrderServiceabilityDetailResponse.class);
				
			//	OrderServiceabilityDetailResponse response = (OrderServiceabilityDetailResponse) APIUtilities
				//		.convertXMLStringToObject(responseMessage2, new OrderServiceabilityDetailResponse());
				
				
				//Make sure to provide the detailed paths , so that the old and new client will be used correctly
				String promiseDate1=response1.getOrder().getItemServiceabilityInfos().get(0).getServiceTypeServiceabilityInfoMap().get(com.myntra.lms.client.response.ServiceType.DELIVERY).getShippingMethodServiceabilityInfoMap().get(com.myntra.lms.client.status.ShippingMethod.NORMAL).getPaymentMethodServiceabilityInfoMap().get(com.myntra.lms.client.status.PaymentMode.on).getPaymentMethodServiceabilityInfoSummary().getPromiseDate().toString();
				System.out.println("Promise Date 1 : "+promiseDate1);
				String promiseDate2=response2.getItemServiceabilityEntries().get(0).getServiceabilityDetailEntries(ServiceType.DELIVERY, ShippingMethod.NORMAL, PaymentMode.ONLINE).get(0).getCourierServiceabilityEntries().get(0).getPromiseDate().toString();
				System.out.println("Promise Date 2 : "+promiseDate2);
				
				Assert.assertTrue(promiseDate1.equals(promiseDate2),"Promise Date 1 and 2 are not equal");
				
				
				System.out.println("\n Response of Promise Date"+response1);
				System.out.println("\n Response of Fetch  Detail"+response2);
			
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	@Test
	public void checkServiceabilityWithoutAttributes(ITestContext testContext) throws JsonParseException, JsonMappingException, IOException, JAXBException {
		
		String fileName = "";
		List<Long> pincodeList = serviceabilityPayloadGenerator.getPincodeList(fileName);
		int currentCount = testContext.getAllTestMethods()[0].getCurrentInvocationCount();
		
		pincodeList.parallelStream().forEach( pincode -> {
			
			try {
				
				List<String> payloadList = serviceabilityPayloadGenerator.getPayloadList(pincode);
				
				for(String requestPayload : payloadList) {
					
					HashMap<String, String> requestHeader = Headers.getLmsHeaderXML();
					String responseMessage1 = lmsUtils.put(baseUrl1, "/myntra-lms-service/serviceabilityv2/checkServiceabilityWithOutAttributes", requestPayload, requestHeader);
					String responseMessage2 = lmsUtils.put(baseUrl2, "/myntra-lms-service/serviceabilityv2/checkServiceabilityWithOutAttributes", requestPayload, requestHeader);
					CourierServiceabilityInfoResponseV2 response1 = LMSUtils.getJavaFromXml(responseMessage1, CourierServiceabilityInfoResponseV2.class);
					CourierServiceabilityInfoResponseV2 response2 =  LMSUtils.getJavaFromXml(responseMessage2, CourierServiceabilityInfoResponseV2.class);
					
					if(response2.equals(response1)) {
						
						log.info("Test pass "+currentCount);
						Assert.assertEquals(response2, response1,"failed to validate");
						
					}else {
						
						log.info("Test fail "+currentCount);
						log.info("failed payload - "+requestPayload);
						log.info("LMC on Response - "+responseMessage1);
						log.info("Oldresponse - "+responseMessage2);
						Assert.assertEquals(response2, response1,"failed to validate");
					}
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
}
