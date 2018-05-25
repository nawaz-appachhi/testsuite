package com.myntra.apiTests.portalservices.all;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;

import com.myntra.apiTests.dataproviders.AddressServiceDP;
import com.myntra.apiTests.portalservices.checkoutservice.AddressNodes;
import com.myntra.apiTests.portalservices.checkoutservice.CartNodes;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutTestsHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import argo.saj.InvalidSyntaxException;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutDataProviderEnum;
import com.myntra.apiTests.portalservices.commons.StatusNodes;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.MyntAssert;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
//import com.mysql.jdbc.Statement;


public class AddressServiceTests extends AddressServiceDP {
	
	static Logger log = Logger.getLogger(AddressServiceTests.class);
	static CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();
	APIUtilities api = new APIUtilities();
	static Initialize init = new Initialize("/Data/configuration");
	
	/**
	 * This Test Case used to invoke CheckoutService getAllAddress API and verify for success response(200)
	 *
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "FoxSanity","Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "AddressServiceDP_getAllAddressDataProvider_validateAPIResponse",description="1.login user using IDP service call. \n 2.hit fetchAllAddress API to fetch user's addresses."
					+ "\n 3.Check the service response must be 200")
	public void AddressServiceTests_getAllAddress_verifySuccessResponse(String userName, String password, String successRespCode)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress (userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		 
		AssertJUnit.assertEquals("CheckoutService getAllAddress API is not working", 
				Integer.parseInt(successRespCode), getAllAddressReqGen.response.getStatus());
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_getAllAddress_verifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_getAllAddress_verifySuccessResponse : "+timeTaken+" seconds\n");
		
	}
	
	/**
	 * This Test Case used to invoke CheckoutService getAllAddress API and verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"}, 
			dataProvider = "AddressServiceDP_getAllAddressDataProvider_positiveCases",description="1.login user using IDP service call. \n 2.hit getAllAddress API to fetch user's addresses."
					+ "\n 3.Verify statusCode, statusMessage & statusType should be of Success in response")
	public void AddressServiceTests_getAllAddress_verifySuccessStatusResponse(String userName, String password, String successStatusCode, String successStatusMsg, 
			String successStatusType)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress (userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("CheckoutService getAllAddress API response status code is not a success code", 
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
		
		AssertJUnit.assertTrue("CheckoutService getAllAddress API response status message is not a success message",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		 
		AssertJUnit.assertTrue("CheckoutService getAllAddress API response status type is not a success type",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_getAllAddress_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_getAllAddress_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		
	}
	
	/**
	 * This Test Case used to invoke CheckoutService getAllAddress API and verify for failure status response
	 * 
	 * @param userName
	 * @param password
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"}, 
			dataProvider = "AddressServiceDP_getAllAddressDataProvider_negativeCases",description="1. provide invalid credentials to login \n2.login user using IDP service call. "
					+ "\n 3.hit getAllAddress API to fetch user's addresses."
					 + "\n 4. Check the service response must be a failure. \n 5. Check failure status code, failure status message, failure status type")
	public void AddressServiceTests_getAllAddress_verifyFailureStatusResponse(String userName, String password, String failureStatusCode, String failureStatusMsg, 
			String failureStatusType)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress (userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("CheckoutService getAllAddress API response status code is not a failure code", 
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(failureStatusCode));	
		
		AssertJUnit.assertTrue("CheckoutService getAllAddress API response status message is not a failure message",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(failureStatusMsg));
		 
		AssertJUnit.assertTrue("CheckoutService getAllAddress API response status type is not a failure type",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_getAllAddress_verifyFailureStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_getAllAddress_verifyFailureStatusResponse : "+timeTaken+" seconds\n");
		
	}
	
	/**
	 * This Test Case used to invoke CheckoutService getAllAddress API and verify for address nodes
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"}, 
			dataProvider = "AddressServiceDP_getAllAddressDataProvider_validateAddressData",description="1. provide valid credentials to login \n2.login user using IDP service call. "
					+ "\n 3.hit getAllAddress API to fetch user's addresses."
					 + "\n 4. verify response nodes.")
	public void AddressServiceTests_getAllAddress_verifyAddressNodes(String userName, String password)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress (userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
			
			getAllAddressReqGen = checkoutTestsHelper.getAllAddress (userName, password);
			System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
					getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		}
		
		AssertJUnit.assertTrue("address nodes are not exists", getAllAddressReqGen.respvalidate.DoesNodesExists(AddressNodes.getAddressNodes()));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_getAllAddress_verifyAddressNodes : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_getAllAddress_verifyAddressNodes : "+timeTaken+" seconds\n");

	}
	
	/**
	 * This Test Case used to invoke CheckoutService fetchSingleAddress API and verify for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param pAddressId
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "FoxSanity","Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "AddressServiceDP_fetchSingleAddressDataProvider_validateAPIResponse",description="1. provide valid credentials to login \n2.login user using IDP service call. "
					+ "\n 3.hit fetchAllAddress API to fetch user's addresses and fetch a single addressID."
					+"\n4. if no address exists, add new address using addNewAddress API and save addressID"
					 + "\n 5. hit fetchSingleAddress API and fetch a single address for above feched addressID. \n 6. Check API response should be success 200")
	public void AddressServiceTests_fetchSingleAddress_verifySuccessResponse(String userName, String password, String pAddressId, String successRespCode)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addrId = "";
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress(userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress - API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress - API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(),	true).contains(SUCCESS_STATUS_TYPE));
			
			addrId = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), true);
			
		} else addrId = getAllAddressReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), true);
		
			String addressId = pAddressId.isEmpty() ?  addrId : pAddressId;
			RequestGenerator fetchSingleAddrReqGen = checkoutTestsHelper.fetchSingleAddress(userName, password, addressId);
			System.out.println("\nPrinting CheckoutService fetchSingleAddress API response :\n\n"+fetchSingleAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService fetchSingleAddress API response :\n\n"+fetchSingleAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertEquals("CheckoutService fetchSingleAddress API is not working", 
					Integer.parseInt(successRespCode), fetchSingleAddrReqGen.response.getStatus());
		
			long endTime = Calendar.getInstance().getTimeInMillis();
			double timeTaken = (endTime - startTime)/1000.0;
			
			System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_fetchSingleAddress_verifySuccessResponse : "+timeTaken+" seconds\n");
			log.info("\nTime taken to execute - TestCase - AddressServiceTests_fetchSingleAddress_verifySuccessResponse : "+timeTaken+" seconds\n");
			
	}
	
	/**
	 * This Test Case used to invoke CheckoutService fetchSingleAddress API and verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param pAddressId
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "AddressServiceDP_fetchSingleAddressDataProvider_positiveCases",description="1. provide valid credentials to login \n2.login user using IDP service call. "
					+ "\n 3.hit fetchAllAddress API to fetch user's addresses and fetch a single addressID."
					+"\n4. if no address exists, add new address using addNewAddress API and save addressID"
					 + "\n 5. hit fetchSingleAddress API and fetch a single address for above feched addressID. \n 6. Check API response successStatus with statusCode, statusMessgae & statusType")
	public void AddressServiceTests_fetchSingleAddress_verifySuccessStatusResponse(String userName, String password, String pAddressId, String successStatusCode, 
			String successStatusMsg, String successStatusType)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addrId = "";
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress(userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue(
				"Error while invoking CheckoutService getAllAddress API",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
			
			addrId = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), true);
			
		} else addrId = getAllAddressReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), true);
		
		String addressId = pAddressId.isEmpty() ?  addrId : pAddressId;
		RequestGenerator fetchSingleAddrReqGen = checkoutTestsHelper.fetchSingleAddress(userName, password, addressId);
		System.out.println("\nPrinting CheckoutService fetchSingleAddress API response :\n\n"+fetchSingleAddrReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService fetchSingleAddress API response :\n\n"+fetchSingleAddrReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("CheckoutService fetchSingleAddress API status code is not a success status code", 
				fetchSingleAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
		
		AssertJUnit.assertTrue("CheckoutService fetchSingleAddress API status message is not a success status message",
				fetchSingleAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		 
		AssertJUnit.assertTrue("CheckoutService fetchSingleAddress API status type is not a success status type",
				fetchSingleAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_fetchSingleAddress_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_fetchSingleAddress_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		
	}
	
	/**
	 * This Test Case used to invoke CheckoutService fetchSingleAddress API and verify for failure status response
	 * 
	 * @param userName
	 * @param password
	 * @param pAddressId
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "AddressServiceDP_fetchSingleAddressDataProvider_negativeCases",description="1. provide invalid credentials/invalid addressID \n2.login user using IDP service call. "
					 + "\n 3. hit fetchSingleAddress API and fetch a single address for given addressID. "
					 + "\n 4. Check API response should be a failure one"
					 + "\n 5. Verify failureStatusCode, failureStatusMessage, failureStatusType")
	public void AddressServiceTests_fetchSingleAddress_verifyFailureStatusResponse(String userName, String password, String pAddressId, String failureStatusCode, 
			String failureStatusMsg, String failureStatusType)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator fetchSingleAddrReqGen = checkoutTestsHelper.fetchSingleAddress(userName, password, pAddressId);
		System.out.println("\nPrinting CheckoutService fetchSingleAddress API response :\n\n"+fetchSingleAddrReqGen.returnresponseasstring());
		log.info("\nPrinting CheckoutService fetchSingleAddress API response :\n\n"+fetchSingleAddrReqGen.returnresponseasstring());
		
		AssertJUnit.assertTrue("CheckoutService fetchSingleAddress API status code is not a failure status code", 
				fetchSingleAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(failureStatusCode));	
		
		AssertJUnit.assertTrue("CheckoutService fetchSingleAddress API status message is not a failure status message",
				fetchSingleAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(failureStatusMsg));
		 
		AssertJUnit.assertTrue("CheckoutService fetchSingleAddress API status type is not a failure status type",
				fetchSingleAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_fetchSingleAddress_verifyFailureStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_fetchSingleAddress_verifyFailureStatusResponse : "+timeTaken+" seconds\n");
		
	}
	
	/**
	 * This Test Case used to invoke CheckoutService fetchSingleAddress API and verify for address nodes
	 * 
	 * @param userName
	 * @param password
	 * @param pAddressId
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "AddressServiceDP_fetchSingleAddressDataProvider_validateAddressData",description="1. provide valid credentials to login \n2.login user using IDP service call. "
					 + "\n 3. hit getAllAddress API and fetch a single addressID. "
					 +"\n4. if no address exists, add new address using addNewAddress API and save addressID"
					+ "\n 5.Hit FetchSingleAddress API to fetch address if extracted addressID "
					 + "\n 6. Verify response Nodes exists ")
	public void AddressServiceTests_fetchSingleAddress_verifyAddressNodes(String userName, String password, String pAddressId)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addrId = "";
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress(userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

			addrId = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), true);
			
		} else addrId = getAllAddressReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), true);
		
		String addressId = pAddressId.isEmpty() ?  addrId : pAddressId;
		RequestGenerator fetchSingleAddrReqGen = checkoutTestsHelper.fetchSingleAddress(userName, password, addressId);
		System.out.println("\nPrinting CheckoutService fetchSingleAddress API response :\n\n"+fetchSingleAddrReqGen.returnresponseasstring());
		log.info("\nPrinting CheckoutService fetchSingleAddress API response :\n\n"+fetchSingleAddrReqGen.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService fetchSingleAddress API",
				fetchSingleAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		AssertJUnit.assertTrue("address nodes are not exists", fetchSingleAddrReqGen.respvalidate.DoesNodesExists(AddressNodes.getAddressNodes()));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_fetchSingleAddress_verifyAddressNodes : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_fetchSingleAddress_verifyAddressNodes : "+timeTaken+" seconds\n");
		
	}
	
	/**
	 * This Test Case used to invoke CheckoutService listAllAddressWithServiceAbility API and verify for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke","FoxSanity", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="AddressServiceDP_listAllAddressWithServiceAbilityDataProvider_validateAPIResponse",description="1. provide valid credentials to login \n2.login user using IDP service call. "
					 + "\n 3. fetch user's cart. \n 4. if cart is empty add items to cart"
					 + "\n 5. hit listAllAddressWithServiceability API to list down all the serviceable addresses for cart "
					 + "\n 6. Verify API response should be success response and it fetched the serviceable addresses")
	public void AddressServiceTests_listAllAddressWithServiceAbility_verifySuccessResponse(String userName, String password, String successRespCode)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty ="1";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(LAA_SEARCH_PROD, LAA_SEARCH_QTY, LAA_SEARCH_RET_DOCS, LAA_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE))
						{
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
							{
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue(
										"Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								log.info("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}
			
			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		}
		
		RequestGenerator listAllAddressWithServiceAbilityReqGen = checkoutTestsHelper.getAllAddressWithServiceAbility(userName, password);
		System.out.println("\nPrinting CheckoutService listAllAddressWithServiceAbility API response :\n\n"+listAllAddressWithServiceAbilityReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService listAllAddressWithServiceAbility API response :\n\n"+listAllAddressWithServiceAbilityReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("CheckoutService listAllAddressWithServiceAbility API is not working",
				Integer.parseInt(successRespCode), listAllAddressWithServiceAbilityReqGen.response.getStatus());
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_listAllAddressWithServiceAbility_verifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_listAllAddressWithServiceAbility_verifySuccessResponse : "+timeTaken+" seconds\n");
		
	}
	
	/**
	 * This Test Case used to invoke CheckoutService listAllAddressWithServiceAbility API and verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="AddressServiceDP_listAllAddressWithServiceAbilityDataProvider_positiveCases",description="1. provide valid credentials to login \n2.login user using IDP service call. "
					 + "\n 3. fetch user's cart. \n 4. if cart is empty add items to cart"
					 + "\n 5. hit listAllAddressWithServiceability API to list down all the serviceable addresses for cart "
					 + "\n 6. Verify API responseCode, statusMessage & statusType should be of success")
	public void AddressServiceTests_listAllAddressWithServiceAbility_verifySuccessStatusResponse(String userName, String password, String successStatusCode, 
			String successStatusMsg, String successStatusType)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty ="1";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(LAA_SEARCH_PROD, LAA_SEARCH_QTY, LAA_SEARCH_RET_DOCS, LAA_SEARCH_IS_FACET);
			if(!CollectionUtils.isEmpty(styleIdList))
			{	System.out.println("style id list not empty");
				for(Integer styleId : styleIdList)
				{
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE))
						{
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
							{
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								log.info("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
					
				}
				
			} else {
				System.out.println("inside empty style id else");
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}
		}
		
		RequestGenerator listAllAddressWithServiceAbilityReqGen = checkoutTestsHelper.getAllAddressWithServiceAbility(userName, password);
		System.out.println("\nPrinting CheckoutService listAllAddressWithServiceAbility API response :\n\n"+listAllAddressWithServiceAbilityReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService listAllAddressWithServiceAbility API response :\n\n"+listAllAddressWithServiceAbilityReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("CheckoutService listAllAddressWithServiceAbility API response status code is not a success status code", 
				listAllAddressWithServiceAbilityReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
		
		AssertJUnit.assertTrue("CheckoutService listAllAddressWithServiceAbility API response status message is not a success status message",
				listAllAddressWithServiceAbilityReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		 
		AssertJUnit.assertTrue("CheckoutService listAllAddressWithServiceAbility API response status type is not a success status type",
				listAllAddressWithServiceAbilityReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_listAllAddressWithServiceAbility_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_listAllAddressWithServiceAbility_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This Test Case used to invoke CheckoutService listAllAddressWithServiceAbility API and verify for failure status response
	 * 
	 * @param userName
	 * @param password
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="AddressServiceDP_listAllAddressWithServiceAbilityDataProvider_negetiveCases",description="1. provide invalid/not authorized credentials to login \n2.login user using IDP service call. "
					 + "\n 3. hit listAllAddressWithServiceability API "
					 + "\n 4. Verify API responseCode, statusMessage & statusType should be of Failure")
	public void AddressServiceTests_listAllAddressWithServiceAbility_verifyFailureStatusResponse(String userName, String password, String failureStatusCode, 
			String failureStatusMsg, String failureStatusType)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator listAllAddressWithServiceAbilityReqGen = checkoutTestsHelper.getAllAddressWithServiceAbility(userName, password);
		System.out.println("\nPrinting CheckoutService listAllAddressWithServiceAbility API response :\n\n"+listAllAddressWithServiceAbilityReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService listAllAddressWithServiceAbility API response :\n\n"+listAllAddressWithServiceAbilityReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("CheckoutService listAllAddressWithServiceAbility API response status code is not a failure status code", 
				listAllAddressWithServiceAbilityReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(failureStatusCode));	
		
		AssertJUnit.assertTrue("CheckoutService listAllAddressWithServiceAbility API response status message is not a failure status message",
				listAllAddressWithServiceAbilityReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(failureStatusMsg));
		 
		AssertJUnit.assertTrue("CheckoutService listAllAddressWithServiceAbility API response status type is not a failure status type",
				listAllAddressWithServiceAbilityReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_listAllAddressWithServiceAbility_verifyFailureStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_listAllAddressWithServiceAbility_verifyFailureStatusResponse : "+timeTaken+" seconds\n");

	}
	
	/**
	 *  This Test Case used to invoke CheckoutService listAllAddressWithServiceAbility API and verify for cod and noncod serviceability nodes
	 * 
	 * @param userName
	 * @param password
	 * @param codAvailMsg
	 * @param codServAvailMsg
	 * @param nonCODServAvailMsg
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="AddressServiceDP_listAllAddressWithServiceAbilityDataProvider_validateAddrServiceabilityNodes",description="1. provide valid credentials to login \n2.login user using IDP service call. "
					 + "\n 3. fetch user's cart. \n 4. if cart is empty add items to cart"
					 + "\n 5. hit listAllAddressWithServiceability API to list down all the serviceable addresses for cart "
					 + "\n 6. Verify addressServiceabilityEntry nodes in API response")
	public void AddressServiceTests_listAllAddressWithServiceAbility_validateAddressServiceabilityEntry(String userName, String password, String codAvailMsg, 
			String codServAvailMsg, String nonCODServAvailMsg)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty ="1";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(LAA_SEARCH_PROD, LAA_SEARCH_QTY, LAA_SEARCH_RET_DOCS, LAA_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE))
						{
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
							{
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								log.info("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
					
				}
				
			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}
			
			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		}
		
		RequestGenerator listAllAddressWithServiceAbilityReqGen = checkoutTestsHelper.getAllAddressWithServiceAbility(userName, password);
		System.out.println("\nPrinting CheckoutService listAllAddressWithServiceAbility API response :\n\n"+listAllAddressWithServiceAbilityReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService listAllAddressWithServiceAbility API response :\n\n"+listAllAddressWithServiceAbilityReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService listAllAddressWithServiceAbility API",
				listAllAddressWithServiceAbilityReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(listAllAddressWithServiceAbilityReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
			listAllAddressWithServiceAbilityReqGen = checkoutTestsHelper.getAllAddressWithServiceAbility(userName, password);
			System.out.println("\nPrinting CheckoutService listAllAddressWithServiceAbility API response :\n\n"+listAllAddressWithServiceAbilityReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService listAllAddressWithServiceAbility API response :\n\n"+listAllAddressWithServiceAbilityReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService listAllAddressWithServiceAbility API",
					listAllAddressWithServiceAbilityReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		} 

		AssertJUnit.assertTrue("COD Availability Entry is not available", 
				listAllAddressWithServiceAbilityReqGen.respvalidate.DoesNodesExists(AddressNodes.getCODAvailabilityEntryNodes()));
		
		AssertJUnit.assertTrue("Invalid servicability value in COD availability entry",
				listAllAddressWithServiceAbilityReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_COD_AVAILABILITY_SERVICEABILITY.toString(), true).contains(codAvailMsg));
		
		AssertJUnit.assertTrue("Address serviceability entry is not available", 
				listAllAddressWithServiceAbilityReqGen.respvalidate.DoesNodesExists(AddressNodes.getAddresServiceabilityEntryNodes()));
		
		AssertJUnit.assertTrue("Invalid COD Service Ability value  in address serviceability entry", 
				listAllAddressWithServiceAbilityReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ADDR_SERVICEABILITY_COD_SERVICEABILITY.toString(), true).contains(codServAvailMsg));
		
		AssertJUnit.assertTrue("Invalid NonCOD Service Ability value in address serviceability entry", 
				listAllAddressWithServiceAbilityReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ADDR_SERVICEABILITY_COD_SERVICEABILITY.toString(), true).contains(nonCODServAvailMsg));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_listAllAddressWithServiceAbility_ValidateAddressServiceabilityEntry : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_listAllAddressWithServiceAbility_ValidateAddressServiceabilityEntry : "+timeTaken+" seconds\n");
	}	
	
	/**
	 *  This Test Case used to invoke CheckoutService addNewAddress API and verify for success response code(200)
	 * 
	 * @param userName
	 * @param password
	 * @param streetName
	 * @param cityName
	 * @param countryCode
	 * @param isDefaultAddress
	 * @param emailId
	 * @param localityName
	 * @param name
	 * @param pincode
	 * @param stateCode
	 * @param stateName
	 * @param userId
	 * @param mobileNumber
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "FoxSanity","Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "AddressServiceDP_addNewAddressDataProvider_validateAPIResponse",description="1. provide valid credentials to login \n2.login user using IDP service call. "
					 + "\n 3. add new address for user by hitting addAddress API"
					 + "\n 4. Verify success response code in API's response")
	public void AddressServiceTests_addNewAddress_verifySuccessResponse(String userName, String password, String streetName, String cityName, String countryCode, 
			String isDefaultAddress, String emailId, String localityName, String name, String pincode, String stateCode, String stateName, String userId, 
			String mobileNumber, String successRespCode)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator addNewAddressReqGen =checkoutTestsHelper.addNewAddress(userName, password, streetName, cityName, countryCode, isDefaultAddress, emailId,
				localityName, name, pincode, stateCode, stateName, userId, mobileNumber);
		System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("CheckoutService addNewAddress API is not working",
				Integer.parseInt(successRespCode), addNewAddressReqGen.response.getStatus());
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_addNewAddress_verifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_addNewAddress_verifySuccessResponse : "+timeTaken+" seconds\n");
	}

	
	/**
	 * This Test Case used to invoke CheckoutService addNewAddress API and verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param streetName
	 * @param cityName
	 * @param countryCode
	 * @param isDefaultAddress
	 * @param emailId
	 * @param localityName
	 * @param name
	 * @param pincode
	 * @param stateCode
	 * @param stateName
	 * @param userId
	 * @param mobileNumber
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "AddressServiceDP_addNewAddressDataProvider_positiveCases",description="1. provide valid credentials to login \n2.login user using IDP service call. "
					 + "\n 3. add new address for user by hitting addAddress API"
					 + "\n 4. Verify statusCode, statusMessage & statusType in API's response to be of success type")
	public void AddressServiceTests_addNewAddress_verifySuccessStatusResponse(String userName, String password, String streetName, String cityName, String countryCode, 
			String isDefaultAddress, String emailId, String localityName, String name, String pincode, String stateCode, String stateName, String userId, 
			String mobileNumber, String successStatusCode, String successStatusMsg, String successStatusType)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator addNewAddressReqGen = checkoutTestsHelper.addNewAddress(userName, password, streetName, cityName, countryCode, isDefaultAddress, emailId,
				localityName, name, pincode, stateCode, stateName, userId, mobileNumber);
		System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("CheckoutService addNewAddress API response status code is not a success status code", 
				addNewAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
		
		AssertJUnit.assertTrue("CheckoutService addNewAddress API response status message is not a success status message",
				addNewAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		 
		AssertJUnit.assertTrue("CheckoutService addNewAddress API response status type is not a success status type",
				addNewAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_addNewAddress_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_addNewAddress_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
	}
	
	
	/**
	 * This Test Case used to invoke CheckoutService addNewAddress API and verify for failure status response
	 * 
	 * @param userName
	 * @param password
	 * @param streetName
	 * @param cityName
	 * @param countryCode
	 * @param isDefaultAddress
	 * @param emailId
	 * @param localityName
	 * @param name
	 * @param pincode
	 * @param stateCode
	 * @param stateName
	 * @param userId
	 * @param mobileNumber
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "AddressServiceDP_addNewAddressDataProvider_negetiveCases",description="1. provide invalid data combinations \n2.login user using IDP service call. "
					 + "\n 3. add new address for user by hitting addAddress API"
					 + "\n 4. Verify statusCode, statusMessage & statusType in API's response to be of failure type")
	public void AddressServiceTests_addNewAddress_verifyFailureStatusResponse(String userName, String password, String streetName, String cityName, String countryCode, 
			String isDefaultAddress, String emailId, String localityName, String name, String pincode, String stateCode, String stateName, String userId, 
			String mobileNumber, String failureStatusCode, String failureStatusMsg, String failureStatusType)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator addNewAddressReqGen = checkoutTestsHelper.addNewAddress(userName, password, streetName, cityName, countryCode, isDefaultAddress, 
				emailId, localityName, name, pincode, stateCode, stateName, userId, mobileNumber);
		System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService addNewAddress AP I response :\n\n"+addNewAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("CheckoutService addNewAddress API response status code is not a failure status code", 
				addNewAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(failureStatusCode));	
		
		AssertJUnit.assertTrue("CheckoutService addNewAddress API response status message is not a failure status message",
				addNewAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(failureStatusMsg));
		 
		AssertJUnit.assertTrue("CheckoutService addNewAddress API response status type is not a failure status type",
				addNewAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_addNewAddress_verifyFailureStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_addNewAddress_verifyFailureStatusResponse : "+timeTaken+" seconds\n");	
	}
	
	/**
	 * This Test Case used to invoke CheckoutService updateAddress API and verify for success response code (200)
	 * 
	 * @param userName
	 * @param password
	 * @param streetName
	 * @param cityName
	 * @param countryCode
	 * @param isDefaultAddress
	 * @param emailId
	 * @param localityName
	 * @param name
	 * @param pincode
	 * @param stateCode
	 * @param stateName
	 * @param userId
	 * @param mobileNumber
	 * @param pAddressId
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "FoxSanity","Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"}, 
			dataProvider = "AddressServiceDP_updateAddressDataProvider_validateAPIResponse",description="1. provide valid credentials to login \n2.login user using IDP service call. "
					 + "\n 3. Hit getAllAddress API and fetch one existing addressID"
					 +"\n 4. If no address exists for user add a new address, save new addressID"
					 +"\n 5. Hit updateAddress API to update address with extracted addressID"
					 + "\n 6. Verify responseCode should be 200 in APIs response")
	public void AddressServiceTests_updateAddress_verifySuccessResponse(String userName, String password, String streetName, String cityName, String countryCode, 
			String isDefaultAddress, String emailId, String localityName, String name, String pincode, String stateCode, String stateName, String userId, 
			String mobileNumber, String pAddressId,	String successRespCode)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addrId = "";
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress(userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
			
			addrId = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), false);
			
		} else addrId = getAllAddressReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), false);
		
		String addressId = pAddressId.isEmpty() ?  addrId : pAddressId;
		
		RequestGenerator updateAddrReqGen = checkoutTestsHelper.updateAddress(userName, password, streetName, cityName, countryCode, isDefaultAddress, emailId, 
				localityName, name, pincode, stateCode, stateName, userId, mobileNumber, addressId);
		System.out.println("\nPrinting CheckoutService updateAddress API response :\n\n"+updateAddrReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService updateAddress API response :\n\n"+updateAddrReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("CheckoutService updateAddress API is not working",
				Integer.parseInt(successRespCode), updateAddrReqGen.response.getStatus());
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_updateAddress_verifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_updateAddress_verifySuccessResponse : "+timeTaken+" seconds\n");
	}

	
	/**
	 * This Test Case used to invoke CheckoutService updateAddress API and verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param streetName
	 * @param cityName
	 * @param countryCode
	 * @param isDefaultAddress
	 * @param emailId
	 * @param localityName
	 * @param name
	 * @param pincode
	 * @param stateCode
	 * @param stateName
	 * @param userId
	 * @param mobileNumber
	 * @param pAddressId
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "AddressServiceDP_updateAddressDataProvider_positiveCases",description="1. provide valid positive combinations \n2.login user using IDP service call. "
					 + "\n 3. Hit getAllAddress API and fetch one existing addressID"
					 +"\n 4. If no address exists for user add a new address, save new addressID"
					 +"\n 5. Hit updateAddress API to update address with extracted addressID"
					 + "\n 6. Verify statusCode, statusMessage & statusType should be of success in APIs response")
	public void AddressServiceTests_updateAddress_verifySuccessStatusResponse(String userName, String password, String streetName, String cityName, String countryCode, 
			String isDefaultAddress, String emailId, String localityName, String name, String pincode, String stateCode, String stateName, String userId, 
			String mobileNumber, String pAddressId, String successStatusCode, String successStatusMsg, String successStatusType)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addrId = "";
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress(userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
			
			addrId = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), false);
			
		} else addrId = getAllAddressReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), false);
		
		String addressId = pAddressId.isEmpty() ?  addrId : pAddressId;
		RequestGenerator updateAddrReqGen = checkoutTestsHelper.updateAddress(userName, password, streetName, cityName, countryCode, isDefaultAddress, 
				emailId, localityName, name, pincode, stateCode, stateName, userId, mobileNumber, addressId);
		System.out.println("\nPrinting CheckoutService updateAddress API response :\n\n"+updateAddrReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService updateAddress API response :\n\n"+updateAddrReqGen.respvalidate.returnresponseasstring());
			
		AssertJUnit.assertTrue("CheckoutService updateAddress API response status code is not a success status code", 
				updateAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
		
		AssertJUnit.assertTrue("CheckoutService updateAddress API response status message is not a success status message",
				updateAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		 
		AssertJUnit.assertTrue("CheckoutService updateAddress API response status type is not a success status type",
				updateAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));	
	
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_updateAddress_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_updateAddress_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
	}
	
	
	/**
	 * This Test Case used to invoke CheckoutService updateAddress API and verify for failure status response
	 * 
	 * @param userName
	 * @param password
	 * @param streetName
	 * @param cityName
	 * @param countryCode
	 * @param isDefaultAddress
	 * @param emailId
	 * @param localityName
	 * @param name
	 * @param pincode
	 * @param stateCode
	 * @param stateName
	 * @param userId
	 * @param mobileNumber
	 * @param pAddressId
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "AddressServiceDP_updateAddressDataProvider_negetiveCases",description="1. provide possible negative combinations \n2.login user using IDP service call. "
					 +"\n 2. Hit updateAddress API to update address with given addressID"
					 + "\n 3. Verify statusCode, statusMessage & statusType should be of Failure in APIs response")
	public void AddressServiceTests_updateAddress_verifyFailureStatusResponse(String userName, String password, String streetName, String cityName, String countryCode, 
			String isDefaultAddress, String emailId, String localityName, String name, String pincode, String stateCode, String stateName, String userId, 
			String mobileNumber, String pAddressId, String failureStatusCode, String failureStatusMsg, String failureStatusType)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator updateAddrReqGen = checkoutTestsHelper.updateAddress(userName, password, streetName, cityName, countryCode, isDefaultAddress, emailId, 
				localityName, name, pincode, stateCode, stateName, userId, mobileNumber, pAddressId);
		System.out.println("\nPrinting CheckoutService updateAddress API response :\n\n"+updateAddrReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService updateAddress API response :\n\n"+updateAddrReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("CheckoutService updateAddress API response status code is not a failure status code", 
				updateAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(failureStatusCode));	
		
		AssertJUnit.assertTrue("CheckoutService updateAddress API response status message is not a failure status message",
				updateAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(failureStatusMsg));
		 
		AssertJUnit.assertTrue("CheckoutService updateAddress API response status type is not a failure status type",
				updateAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));	
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_updateAddress_verifyFailureStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_updateAddress_verifyFailureStatusResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This Test Case used to invoke CheckoutService deleteAddress API and verify for success response code(200)
	 * 
	 * @param userName
	 * @param password
	 * @param pAddressId
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "FoxSanity","Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "AddressServiceDP_deleteAddressDataProvider_validateAPIResponse",description="1. provide valid credentials to login \n2.login user using IDP service call. "
					 + "\n 3. Hit getAllAddress API and fetch one existing addressID"
					 +"\n 4. If no address exists for user add a new address, save new addressID"
					 +"\n 5. Hit deleteAddress API to delete address of extracted addressID"
					 + "\n 6. Verify responseCode should be 200 in APIs response")
	public void AddressServiceTests_deleteAddress_VerifySuccessResponse(String userName, String password, String pAddressId, String successRespCode)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addrId = "";
		RequestGenerator getAllAddrReqGen = checkoutTestsHelper.getAllAddress(userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddrReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddrReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
				getAllAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
			
			addrId = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), false);
			
		} else addrId = getAllAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), false);
		
		String addressId = pAddressId.isEmpty() ?  addrId : pAddressId;
		RequestGenerator deleteAddrReqGen = checkoutTestsHelper.deleteAddress(userName, password, addressId);
		System.out.println("\nPrinting CheckoutService deleteAddress API response :\n\n"+deleteAddrReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService deleteAddress API response :\n\n"+deleteAddrReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("CheckoutService deleteAddress API is not working",
					Integer.parseInt(successRespCode), deleteAddrReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_deleteAddress_VerifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_deleteAddress_VerifySuccessResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This Test Case used to invoke CheckoutService deleteAddress API and verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param pAddressId
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "AddressServiceDP_deleteAddressDataProvider_positiveCases",description="1. provide valid combinations \n2.login user using IDP service call. "
					 + "\n 3. Hit getAllAddress API and fetch one existing addressID"
					 +"\n 4. If no address exists for user add a new address, save new addressID"
					 +"\n 5. Hit deleteAddress API to delete address of extracted addressID"
					 + "\n 6. Verify statusCode, statusMessage & statusType should be of success in APIs response")
	public void AddressServiceTests_deleteAddress_VerifySuccessStatusResponse(String userName, String password, String pAddressId, String successStatusCode, 
			String successStatusMsg, String successStatusType)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addrId = "";
		RequestGenerator getAllAddrReqGen = checkoutTestsHelper.getAllAddress(userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddrReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddrReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
				getAllAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
			
			addrId = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), false);
			
		} else addrId = getAllAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), false);
		
		String addressId = pAddressId.isEmpty() ?  addrId : pAddressId;
		RequestGenerator deleteAddrReqGen = checkoutTestsHelper.deleteAddress(userName, password, addressId);
		System.out.println("\nPrinting CheckoutService deleteAddress API response :\n\n"+deleteAddrReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService deleteAddress API response :\n\n"+deleteAddrReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("CheckoutService deleteAddress API response status code is not a success status code", 
				deleteAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
		
		AssertJUnit.assertTrue("CheckoutService deleteAddress API response status message is not a success status message",
				deleteAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		 
		AssertJUnit.assertTrue("CheckoutService deleteAddress API response status type is not a success status type",
				deleteAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));	
			
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_deleteAddress_VerifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_deleteAddress_VerifySuccessStatusResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This Test Case used to invoke CheckoutService deleteAddress API and verify for failure status response
	 * 
	 * @param userName
	 * @param password
	 * @param pAddressId
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "AddressServiceDP_deleteAddressDataProvider_negetiveCases",description="1. provide possible negative combinations \n2.login user using IDP service call. "
					 +"\n 2. Hit deleteAddress API to delete address for given addressID"
					 + "\n 3. Verify statusCode, statusMessage & statusType should be of Failure in APIs response")
	public void AddressServiceTests_deleteAddress_VerifyFailureStatusResponse(String userName, String password, String pAddressId, String failureStatusCode, 
			String failureStatusMsg, String failureStatusType)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator deleteAddrReqGen = checkoutTestsHelper.deleteAddress(userName, password, pAddressId);
		System.out.println("\nPrinting CheckoutService deleteAddress API response :\n\n"+deleteAddrReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService deleteAddress API response :\n\n"+deleteAddrReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("CheckoutService deleteAddress API response status code is not a failure status code", 
				deleteAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(failureStatusCode));	
		
		AssertJUnit.assertTrue("CheckoutService deleteAddress API response status message is not a failure status message",
				deleteAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(failureStatusMsg));
		 
		AssertJUnit.assertTrue("CheckoutService deleteAddress API response status type is not a failure status type",
				deleteAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));	
			
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_deleteAddress_VerifyFailureStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_deleteAddress_VerifyFailureStatusResponse : "+timeTaken+" seconds\n");
	}
	
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "AddressServiceDP_getAllAddressDataProvider_verifyResponseDataNodesUsingSchemaValidations",description="1.login user using IDP service call. \n 2.hit getAllAddress API to fetch user's addresses."
			+ "\n 3.Verify Schema Nodes in APIs response")
	public void AddressServiceTests_getAllAddress_verifyResponseDataNodesUsingSchemaValidations(String userName, String password)
	{	
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress (userName, password);
		String getAllAddressResponse = getAllAddressReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressResponse);
		log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressResponse);

		AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			String addNewAddrResponse = addNewAddrReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrResponse);
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrResponse);
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
			
			try {
	            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/checkoutservice-addnewaddress-schema.txt");
	            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(addNewAddrResponse, jsonschema);
	            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService addNewAddress API response", CollectionUtils.isEmpty(missingNodeList));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
			getAllAddressReqGen = checkoutTestsHelper.getAllAddress (userName, password);
			getAllAddressResponse = getAllAddressReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressResponse);
			log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressResponse);

			AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
					getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		}
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/checkoutservice-getalladdress-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getAllAddressResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "AddressServiceDP_addNewAddressDataProvider_verifyResponseDataNodesUsingSchemaValidations",description="1.login user using IDP service call. \n 2.hit addNewAddress API to add new address."
			+ "\n 3.Verify Schema Nodes in APIs response")
	public void AddressServiceTests_addNewAddress_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String streetName, String cityName, 
			String countryCode, String isDefaultAddress, String emailId, String localityName, String name, String pincode, String stateCode, String stateName, 
			String userId, String mobileNumber)
	{	
		RequestGenerator addNewAddressReqGen = checkoutTestsHelper.addNewAddress(userName, password, streetName, cityName, countryCode, isDefaultAddress, emailId,
				localityName, name, pincode, stateCode, stateName, userId, mobileNumber);
		String addNewAddressResponse = addNewAddressReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddressResponse);
		log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddressResponse);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/checkoutservice-addnewaddress-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(addNewAddressResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService addNewAddress API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "AddressServiceDP_fetchSingleAddressDataProvider_verifyResponseDataNodesUsingSchemaValidations",description="1. provide valid credentials to login \n2.login user using IDP service call. "
			+ "\n 3.hit getAllAddress API to fetch user's addresses and fetch a single addressID."
			+"\n 4. if no address exists, add a new address via AddNewAddress API"
			 + "\n 5. hit fetchSingleAddress API and fetch a single address for above feched addressID. \n 6. Verify Schema Nodes in APIs response")
	public void AddressServiceTests_fetchSingleAddress_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String pAddressId)
	{	
		String addrId = "";
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress(userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		
		MyntAssert.assertEquals("getAllAddress API response ["+StatusNodes.STATUS_TYPE.toString()+"] node value is invalid.", SUCCESS_STATUS_TYPE.trim(),
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "").trim());
		
		if(getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

			addrId = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), true);
			
		} else addrId = getAllAddressReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), true);
		
		String addressId = pAddressId.isEmpty() ?  addrId : pAddressId;
		RequestGenerator fetchSingleAddrReqGen = checkoutTestsHelper.fetchSingleAddress(userName, password, addressId);
		String fetchSingleAddrResponse = fetchSingleAddrReqGen.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService fetchSingleAddress API response :\n\n"+fetchSingleAddrResponse);
		log.info("\nPrinting CheckoutService fetchSingleAddress API response :\n\n"+fetchSingleAddrResponse);
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService fetchSingleAddress API",
				fetchSingleAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/checkoutservice-fetchsingleaddress-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(fetchSingleAddrReqGen.respvalidate.returnresponseasstring(), jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService addNewAddress API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="AddressServiceDP_listAllAddressWithServiceAbilityDataProvider_verifyResponseDataNodesUsingSchemaValidations",description="1. provide valid credentials to login \n2.login user using IDP service call. "
			 + "\n 3. fetch user's cart. \n 4. if cart is empty add items to cart"
			 + "\n 5. hit listAllAddressWithServiceability API to list down all the serviceable addresses for cart "
			 + "\n 6. Verify Schema Nodes in APIs response")
	public void AddressServiceTests_listAllAddressWithServiceAbility_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String successRespCode)
	{	
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		String addItemQty ="1";
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(LAA_SEARCH_PROD, LAA_SEARCH_QTY, LAA_SEARCH_RET_DOCS, LAA_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE))
						{
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
							{
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue(
										"Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								log.info("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}
			
			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		}
		
		RequestGenerator listAllAddressWithServiceAbilityReqGen = checkoutTestsHelper.getAllAddressWithServiceAbility(userName, password);
		String listAllAddressWithServiceAbilityResponse = listAllAddressWithServiceAbilityReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService listAllAddressWithServiceAbility API response :\n\n"+listAllAddressWithServiceAbilityResponse);
		log.info("\nPrinting CheckoutService listAllAddressWithServiceAbility API response :\n\n"+listAllAddressWithServiceAbilityResponse);
		
		AssertJUnit.assertEquals("CheckoutService listAllAddressWithServiceAbility API is not working",
				Integer.parseInt(successRespCode), listAllAddressWithServiceAbilityReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/checkoutservice-listalladdresswithserviceability-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(listAllAddressWithServiceAbilityResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService listAllAddressWithServiceAbility API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "AddressServiceDP_updateAddressDataProvider_verifyResponseDataNodesUsingSchemaValidations",description="1. provide valid credentials to login \n2.login user using IDP service call. "
			 + "\n 3. Hit getAllAddress API and fetch one existing addressID"
			 +"\n 4. If no address exists for user add a new address, save new addressID"
			 +"\n 5. Hit updateAddress API to update address with extracted addressID"
			 + "\n 6. Verify Schema Nodes in APIs response")
	public void AddressServiceTests_updateAddress_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String streetName, String cityName, 
			String countryCode, String isDefaultAddress, String emailId, String localityName, String name, String pincode, String stateCode, String stateName, 
			String userId, String mobileNumber, String pAddressId,	String successRespCode)
	{	
		String addrId = "";
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress(userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
			
			addrId = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), false);
			
		} else addrId = getAllAddressReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), false);
		
		String addressId = pAddressId.isEmpty() ?  addrId : pAddressId;
		
		RequestGenerator updateAddrReqGen = checkoutTestsHelper.updateAddress(userName, password, streetName, cityName, countryCode, isDefaultAddress, emailId, 
				localityName, name, pincode, stateCode, stateName, userId, mobileNumber, addressId);
		String updateAddressResponse = updateAddrReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService updateAddress API response :\n\n"+updateAddressResponse);
		log.info("\nPrinting CheckoutService updateAddress API response :\n\n"+updateAddressResponse);
		
		AssertJUnit.assertEquals("CheckoutService updateAddress API is not working", Integer.parseInt(successRespCode), updateAddrReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/checkoutservice-updateaddress-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(updateAddressResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService updateAddress API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "AddressServiceDP_deleteAddressDataProvider_verifyResponseDataNodesUsingSchemaValidations",description="1. provide valid credentials to login \n2.login user using IDP service call. "
			 + "\n 3. Hit getAllAddress API and fetch one existing addressID"
			 +"\n 4. If no address exists for user add a new address, save new addressID"
			 +"\n 5. Hit deleteAddress API to delete address of extracted addressID"
			 + "\n 6. Verify Schema Nodes in APIs response")
	public void AddressServiceTests_deleteAddress_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String pAddressId, String successRespCode)
	{		
		String addrId = "";
		RequestGenerator getAllAddrReqGen = checkoutTestsHelper.getAllAddress(userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddrReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddrReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
				getAllAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
			
			addrId = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), false);
			
		} else addrId = getAllAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), false);
		
		String addressId = pAddressId.isEmpty() ?  addrId : pAddressId;
		RequestGenerator deleteAddrReqGen = checkoutTestsHelper.deleteAddress(userName, password, addressId);
		String deleteAddressResponse = deleteAddrReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService deleteAddress API response :\n\n"+deleteAddressResponse);
		log.info("\nPrinting CheckoutService deleteAddress API response :\n\n"+deleteAddressResponse);
		
		AssertJUnit.assertEquals("CheckoutService deleteAddress API is not working", Integer.parseInt(successRespCode), deleteAddrReqGen.response.getStatus());

		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/checkoutservice-deleteaddress-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(deleteAddressResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService deleteAddress API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"})
	public void securityFix_DifferentXID_getAddress()
	{	
		RequestGenerator req = checkoutTestsHelper.securityFixDifferentXID_getAllAddress ("mohittest100@myntra.com", "123456");
		 
		System.out.println("response =" +req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("CheckoutService getAllAddress API response status code is not a success code", 
				req.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains("110001"));	
		
		AssertJUnit.assertTrue("CheckoutService getAllAddress API response status message is not a success message",
				req.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains("Not Authorized to perform the operation"));
		 
		AssertJUnit.assertTrue("CheckoutService getAllAddress API response status type is not a success type",
				req.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains("ERROR"));
	}
	
	@Test(groups={"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"})
	public void securityFix_SameXID_getAddress()
	{	
		RequestGenerator req = checkoutTestsHelper.getAllAddress ("mohittest100@myntra.com", "123456");
		 
		System.out.println("response =" +req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("CheckoutService getAllAddress API response status code is not a success code", 
				req.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains("3"));	
		
		AssertJUnit.assertTrue("CheckoutService getAllAddress API response status message is not a success message",
				req.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains("Success"));
		 
		AssertJUnit.assertTrue("CheckoutService getAllAddress API response status type is not a success type",
				req.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains("SUCCESS"));
	}
	
	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"},dataProvider = "securityFix_DifferentXID_updateAddressDataProvider")
	public void securityFix_DifferentXID_updateAddress(String userName, String password, String streetName, String cityName, String countryCode, 
			String isDefaultAddress, String emailId, String localityName, String name, String pincode, String stateCode, String stateName, String userId, 
			String mobileNumber, String pAddressId, String notAuthStatusCode, String notAuthStatusMsg, String failureStatusType)
	{	
		String addrId = "";
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress(userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
			
			addrId = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), false);
			
		} else addrId = getAllAddressReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), false);
		
		String addressId = pAddressId.isEmpty() ?  addrId : pAddressId;
		RequestGenerator req = checkoutTestsHelper.securityFixDifferentXID_upDateAddress(userName, password, streetName, cityName, countryCode, isDefaultAddress, 
				emailId, localityName, name, pincode, stateCode, stateName, userId, mobileNumber, addressId);
		
		System.out.println("response =" +req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("CheckoutService getAllAddress API response status code is not a success code", 
				req.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(notAuthStatusCode));	
		
		AssertJUnit.assertTrue("CheckoutService getAllAddress API response status message is not a success message",
				req.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(notAuthStatusMsg));
		 
		AssertJUnit.assertTrue("CheckoutService getAllAddress API response status type is not a success type",
				req.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));
		
		
	}
	
	
	@Test(groups={"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AddressServiceDP_addNewAddressDataProvider_positiveCases")
	public void securityFix_DifferentXID_addNewAddress(String userName, String password, String streetName, String cityName, String countryCode, 
			String isDefaultAddress, String emailId, String localityName, String name, String pincode, String stateCode, String stateName, String userId, 
			String mobileNumber, String successStatusCode, String successStatusMsg, String successStatusType)
	{		
			RequestGenerator req = checkoutTestsHelper.securityFixDifferentXID_addNewAddress(userName, password, streetName, cityName, countryCode, isDefaultAddress, emailId,
				localityName, name, pincode, stateCode, stateName, userId, mobileNumber);
			System.out.println("response =" +req.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("CheckoutService getAllAddress API response status code is not a success code", 
					req.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains("110001"));	
			
			AssertJUnit.assertTrue("CheckoutService getAllAddress API response status message is not a success message",
					req.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains("Not Authorized to perform the operation"));
			 
			AssertJUnit.assertTrue("CheckoutService getAllAddress API response status type is not a success type",
					req.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains("ERROR"));
	}
	
	@Test(groups={"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AddressServiceDP_deleteAddressDataProvider_positiveCases")
	public void securityFix_DifferentXID_deleteAddress (String userName, String password, String pAddressId, String successStatusCode, 
			String successStatusMsg, String successStatusType)
	{			
				String addrId = "";
				RequestGenerator getAllAddrReqGen = checkoutTestsHelper.getAllAddress(userName, password);
				System.out.println("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddrReqGen.respvalidate.returnresponseasstring());
				log.info("\nPrinting CheckoutService getAllAddress API response :\n\n"+getAllAddrReqGen.respvalidate.returnresponseasstring());
				
				AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
						getAllAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
				
				if(getAllAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
				{
					RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
							VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
							userName, VALID_MOBILE_NUMBER);
					
					System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
							addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
					
					addrId = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), false);
					
				} else addrId = getAllAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), false);
				
				String addressId = pAddressId.isEmpty() ?  addrId : pAddressId;
				RequestGenerator req = checkoutTestsHelper.securityFixDifferentXID_deleteAddress(userName, password, addressId);
				
				System.out.println("response=" +req.respvalidate.returnresponseasstring());
				
				AssertJUnit.assertTrue("CheckoutService getAllAddress API response status code is not a success code", 
						req.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains("110001"));	
				
				AssertJUnit.assertTrue("CheckoutService getAllAddress API response status message is not a success message",
						req.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains("Not Authorized to perform the operation"));
				 
				AssertJUnit.assertTrue("CheckoutService getAllAddress API response status type is not a success type",
						req.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains("ERROR"));
	}
			
	
	/**
	 *  This Test Case used to invoke CheckoutService addNewAddress API and verify for success response code(200)
	 * 
	 * @param userName
	 * @param password
	 * @param streetName
	 * @param cityName
	 * @param countryCode
	 * @param isDefaultAddress
	 * @param emailId
	 * @param localityName
	 * @param name
	 * @param pincode
	 * @param stateCode
	 * @param stateName
	 * @param userId
	 * @param mobileNumber
	 * @param successRespCode
	 * @throws InvalidSyntaxException 
	 * @throws NumberFormatException 
	 *//*
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "AddressServiceDP_createNewAddressDataProvider_validateAPIResponse")
	public void AddressServiceTest_CreateNewAddress(String userId, String defaultAddress, String name, String address, String city, 
			String stateCode, String countryCode, String pincode, String email, String mobile, String locality, String role, String login, String token, String statusCode, String respCode) throws NumberFormatException, InvalidSyntaxException
	{
		String dbConnection_Fox = "jdbc:mysql://"+"54.179.37.12"+"/myntra?"+"user=myntraAppDBUser&password=9eguCrustuBR&port=3306";
		//int count = getCount(dbConnection_Fox, "audit");
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator addNewAddressReqGen =checkoutTestsHelper.createNewAddress(userId, defaultAddress, name, address, city, stateCode, countryCode, pincode, email, mobile, locality, role, login, token);
		System.out.println("\nPrinting CheckoutService CreateNewAddress API response :\n\n"+addNewAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService CreateNewAddress API response :\n\n"+addNewAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("CheckoutService CreateNewAddress API is not working",
				Integer.parseInt(respCode), addNewAddressReqGen.response.getStatus());
		
		if(Integer.parseInt(respCode)!=200){
		AssertJUnit.assertEquals("CheckoutService CreateNewAddress StatusCode is not matching",
				Integer.parseInt(statusCode), Integer.parseInt(addNewAddressReqGen.respvalidate.GetNodeValue("statusCode", addNewAddressReqGen.returnresponseasstring())));
		}else{
			AssertJUnit.assertEquals("CheckoutService CreateNewAddress StatusCode is not matching",
					Integer.parseInt(statusCode), Integer.parseInt(addNewAddressReqGen.respvalidate.GetNodeValue("status.statusCode", addNewAddressReqGen.returnresponseasstring())));
		}
		
		if(Integer.parseInt(respCode)==200){
			boolean testCond = false;
			//Get the address ID
			String addressID = addNewAddressReqGen.respvalidate.GetNodeValue("data.id", addNewAddressReqGen.returnresponseasstring());
			System.out.println("New Address ID : "+addressID);
			
			// Checking whether newly created address in added
			MyntraService  ms = Myntra.getService(ServiceType.PORTAL_ADDRESS, APINAME.GETALLADDRESSFORCC, init.Configurations);
			ms.URL = api.prepareparameterizedURL(ms.URL, userId);
			System.out.println("Get All Address URL : "+ms.URL);
			HashMap hm = new HashMap();
			hm.put("role", "Test");
			hm.put("login", "cc-user@gmail.com");
			hm.put("token", "Test");
			RequestGenerator rs = new RequestGenerator(ms, hm);
			List addressIds = JsonPath.read(rs.respvalidate.returnresponseasstring(), "$..data.id[*]");
			for(Object s : addressIds){
				if(s.toString().equals(addressID)){
					testCond = true;
				}
			}
			
			AssertJUnit.assertTrue("CheckoutService CreateNewAddress : response of getAllAddress dosn't contains newly created address ",testCond);
			AssertJUnit.assertTrue("CheckoutService CreateNewAddress : No Database entry for this request",getCount(dbConnection_Fox, addressID)==1);
			AssertJUnit.assertTrue("CheckoutService editAddress : value of updateBy is not matching with DB [address ID="+addressID+"]", getValueFromDB(dbConnection_Fox, "updatedBy", addressID).equals(login));
			AssertJUnit.assertTrue("CheckoutService editAddress : value of locality is not matching with DB [address ID="+addressID+"]", getValueFromDB(dbConnection_Fox, "locality", addressID).equals(locality));
			AssertJUnit.assertTrue("CheckoutService editAddress : value of mobile is not matching with DB [address ID="+addressID+"]", getValueFromDB(dbConnection_Fox, "mobile", addressID).equals(mobile));
			AssertJUnit.assertTrue("CheckoutService editAddress : value of pincode is not matching with DB [address ID="+addressID+"]", getValueFromDB(dbConnection_Fox, "pincode", addressID).equals(pincode));
			AssertJUnit.assertTrue("CheckoutService editAddress : value of name is not matching with DB [address ID="+addressID+"]", getValueFromDB(dbConnection_Fox, "name", addressID).equals(name));
			AssertJUnit.assertTrue("CheckoutService editAddress : value of login is not matching with DB [address ID="+addressID+"]", getValueFromDB(dbConnection_Fox, "login", addressID).equals(userId));
		}
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_createNewAddress_verifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_createNewAddress_verifySuccessResponse : "+timeTaken+" seconds\n");
	}
	
	*//**
	 *  This Test Case used to invoke CheckoutService addNewAddress API and verify for success response code(200)
	 * 
	 * @param userName
	 * @param password
	 * @param streetName
	 * @param cityName
	 * @param countryCode
	 * @param isDefaultAddress
	 * @param emailId
	 * @param localityName
	 * @param name
	 * @param pincode
	 * @param stateCode
	 * @param stateName
	 * @param userId
	 * @param mobileNumber
	 * @param successRespCode
	 * @throws InvalidSyntaxException 
	 * @throws NumberFormatException 
	 *//*
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "AddressServiceDP_createNewAddressDataProvider_validateAPIResponse")
	public void AddressServiceTest_EditAddress(String userId, String defaultAddress, String name, String address, String city, 
			String stateCode, String countryCode, String pincode, String email, String mobile, String locality, String role, String login, String token, String statusCode, String respCode) throws NumberFormatException, InvalidSyntaxException
	{
		String dbConnection_Fox = "jdbc:mysql://"+"54.179.37.12"+"/myntra?"+"user=myntraAppDBUser&password=9eguCrustuBR&port=3306";
		//int count = getCount(dbConnection_Fox, "audit");
		
		long startTime = Calendar.getInstance().getTimeInMillis();
		MyntraService  ms = Myntra.getService(ServiceType.PORTAL_ADDRESS, APINAME.GETALLADDRESSFORCC, init.Configurations);
		ms.URL = api.prepareparameterizedURL(ms.URL, userId);HashMap hm = new HashMap();
		hm.put("role", "Test");
		hm.put("login", "cc-user@gmail.com");
		hm.put("token", "Test");
		RequestGenerator rs = new RequestGenerator(ms, hm);
		List addressIds = JsonPath.read(rs.respvalidate.returnresponseasstring(), "$..data.id[*]");
		
		String addressID = addressIds.get(new Random().nextInt(addressIds.size())).toString();
		
		RequestGenerator addNewAddressReqGen =checkoutTestsHelper.editAddress(addressID, userId, defaultAddress, name, address, city, stateCode, countryCode, pincode, email, mobile, locality, role, login, token);
		System.out.println("\nPrinting CheckoutService EditAddress API response :\n\n"+addNewAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService EditAddress API response :\n\n"+addNewAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("CheckoutService editAddress API is not working",
				Integer.parseInt(respCode), addNewAddressReqGen.response.getStatus());
		
		if(Integer.parseInt(respCode)!=200){
			AssertJUnit.assertEquals("CheckoutService EditAddress StatusCode is not matching",
					Integer.parseInt(statusCode), Integer.parseInt(addNewAddressReqGen.respvalidate.GetNodeValue("statusCode", addNewAddressReqGen.returnresponseasstring())));
			}else{
				AssertJUnit.assertEquals("CheckoutService EditAddress StatusCode is not matching",
						Integer.parseInt(statusCode), Integer.parseInt(addNewAddressReqGen.respvalidate.GetNodeValue("status.statusCode", addNewAddressReqGen.returnresponseasstring())));
			}
		
		if(addNewAddressReqGen.response.getStatus()==200){
			AssertJUnit.assertTrue("CheckoutService editAddress : No Database entry for this request address ID="+addressID, getCount(dbConnection_Fox, addressID)>=1);
			AssertJUnit.assertTrue("CheckoutService editAddress : value of updateBy is not matching with DB [address ID="+addressID+"]", getValueFromDB(dbConnection_Fox, "updatedBy", addressID).equals(login));
			AssertJUnit.assertTrue("CheckoutService editAddress : value of locality is not matching with DB [address ID="+addressID+"]", getValueFromDB(dbConnection_Fox, "locality", addressID).equals(locality));
			AssertJUnit.assertTrue("CheckoutService editAddress : value of mobile is not matching with DB [address ID="+addressID+"]", getValueFromDB(dbConnection_Fox, "mobile", addressID).equals(mobile));
			AssertJUnit.assertTrue("CheckoutService editAddress : value of pincode is not matching with DB [address ID="+addressID+"]", getValueFromDB(dbConnection_Fox, "pincode", addressID).equals(pincode));
			AssertJUnit.assertTrue("CheckoutService editAddress : value of name is not matching with DB [address ID="+addressID+"]", getValueFromDB(dbConnection_Fox, "name", addressID).equals(name));
			AssertJUnit.assertTrue("CheckoutService editAddress : value of login is not matching with DB [address ID="+addressID+"]", getValueFromDB(dbConnection_Fox, "login", addressID).equals(userId));
			
		}
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_createNewAddress_verifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_createNewAddress_verifySuccessResponse : "+timeTaken+" seconds\n");
	}*/
	/*
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "AddressServiceDP_getAllAddressCC")
	public void AddressServiceTest_getAllAddressCC(String userId, String role, String login, String token, String statusCode, String respCode) throws NumberFormatException, InvalidSyntaxException
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		MyntraService  ms = Myntra.getService(ServiceType.PORTAL_ADDRESS, APINAME.GETALLADDRESSFORCC, init.Configurations);
		ms.URL = api.prepareparameterizedURL(ms.URL, userId);HashMap hm = new HashMap();
		hm.put("role", role);
		hm.put("login", login);
		hm.put("token", token);
		RequestGenerator rs = new RequestGenerator(ms, hm);
		List addressIds = JsonPath.read(rs.respvalidate.returnresponseasstring(), "$..data.id[*]");
		
		AssertJUnit.assertEquals("CheckoutService getAllAddressCC API is not working",
				Integer.parseInt(respCode), rs.response.getStatus());
		
		if(Integer.parseInt(respCode)!=200){
			AssertJUnit.assertEquals("CheckoutService getAllAddressCC StatusCode is not matching",
					Integer.parseInt(statusCode), Integer.parseInt(rs.respvalidate.GetNodeValue("statusCode", rs.returnresponseasstring())));
			}else{
				AssertJUnit.assertEquals("CheckoutService getAllAddressCC StatusCode is not matching",
						Integer.parseInt(statusCode), Integer.parseInt(rs.respvalidate.GetNodeValue("status.statusCode", rs.returnresponseasstring())));
			}
		
		if(rs.response.getStatus()==200){
			AssertJUnit.assertEquals("CheckoutService getAllAddressCC Address count in response is not matching",
					addressIds.size(), Integer.parseInt(rs.respvalidate.GetNodeValue("status.totalCount", rs.returnresponseasstring())));
		}
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - getAllAddressCC : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - getAllAddressCC : "+timeTaken+" seconds\n");
	}
	
	*/
	private int getCount(String conString, String addressId){
		Statement stmt = null;
		Connection con = null;
		int count = 0;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(conString);
			stmt = con.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String query = "select count(*) from mk_customer_address_history where id="+addressId;
		System.out.println(query);
		try {
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				count = rs.getInt("count(*)");
				//System.out.println("count(*)"+ count);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		return count;
		
	}
	
	private String getValueFromDB(String conString, String columnName, String addressId){
		Statement stmt = null;
		Connection con = null;
		String toReturn = "";
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(conString);
			stmt = con.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String query = "select "+columnName+" from mk_customer_address_history where id="+addressId+" ORDER BY rev_no DESC limit 1";
		System.out.println(query);
		try {
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				toReturn = rs.getString(columnName);
				//System.out.println("count(*)"+ count);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return toReturn;
		
	}
	
	/**
	 * This Test Case used to invoke CheckoutService addressService_getPincodeServicability API and verify for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="AddressServiceTests_getPincodeServicability_verifySuccessStatusResponseDP",description="1. provide valid credentials to login \n2.login user using IDP service call. "
					 + "\n 3. fetch user's cart. \n 4. if cart is empty add items to cart"
					 + "\n 5. hit pincodeServiceability API to list down all the serviceable addresses for cart through addressService "
					 + "\n 6. Verify API response should be success response")
	public void AddressServiceTests_addressService_getPincodeServicability_verifySuccessStatusResponse(String userName, String password, String successRespCode)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty ="1";
		String pinCode="";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(LAA_SEARCH_PROD, LAA_SEARCH_QTY, LAA_SEARCH_RET_DOCS, LAA_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE))
						{
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
							{
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue(
										"Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								log.info("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}
			
			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		}
		
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress(userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress - API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress - API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(),	true).contains(SUCCESS_STATUS_TYPE));
			
			pinCode = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_PIN_CODE.toString(), true);
			
		} else pinCode = getAllAddressReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_PIN_CODE.toString(), true);
			
		pinCode = (pinCode).replace("\"", "");
		ServiceType servName = ServiceType.PORTAL_ADDRESS;
		APINAME apiName = APINAME.PINCODESERVICEABILITYADDRESS;
		RequestGenerator fetchPinCodeServiceabilityAddrReqGen = checkoutTestsHelper.fetchPincodeServicabilityAddress(servName,apiName, userName, password, pinCode);
		System.out.println("\nPrinting CheckoutService fetchPinCodeServiceabilityAddress API response :\n\n"+fetchPinCodeServiceabilityAddrReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService fetchPinCodeServiceabilityAddress API response :\n\n"+fetchPinCodeServiceabilityAddrReqGen.respvalidate.returnresponseasstring());
			
		AssertJUnit.assertEquals("CheckoutService fetchPinCodeServiceabilityAddress API is not working", 
				Integer.parseInt(successRespCode), fetchPinCodeServiceabilityAddrReqGen.response.getStatus());
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_addressService_getPincodeServicability_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_addressService_getPincodeServicability_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		
	}

	
	/**
	 * This Test Case used to invoke CheckoutService absoluteService_getPincodeServicability API and verify for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke","FoxSanity", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="AddressServiceTests_getPincodeServicability_verifySuccessStatusResponseDP",description="1. provide valid credentials to login \n2.login user using IDP service call. "
					 + "\n 3. fetch user's cart. \n 4. if cart is empty add items to cart"
					 + "\n 5. hit pincodeServiceability API to list down all the serviceable addresses for cart through absolut Service "
					 + "\n 6. Verify API response should be success response")
	public void AddressServiceTests_absolutService_getPincodeServicability_verifySuccessStatusResponse(String userName, String password, String successRespCode)
	{	userName ="testing125@myntra.com";
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty ="1";
		String pinCode="";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(LAA_SEARCH_PROD, LAA_SEARCH_QTY, LAA_SEARCH_RET_DOCS, LAA_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE))
						{
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
							{
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue(
										"Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								log.info("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}
			
			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		}
		
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress(userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress - API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress - API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(),	true).contains(SUCCESS_STATUS_TYPE));
			
			pinCode = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_PIN_CODE.toString(), true);
			
		} else pinCode = getAllAddressReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_PIN_CODE.toString(), true);
			
		pinCode = (pinCode).replace("\"", "");
		ServiceType servName = ServiceType.PORTAL_ADDRESS;
		APINAME apiName = APINAME.PINCODESERVICEABILITYADDRESS;
		RequestGenerator fetchPinCodeServiceabilityAddrReqGen = checkoutTestsHelper.fetchPincodeServicabilityAddress(servName,apiName, userName, password, pinCode);
		System.out.println("\nPrinting CheckoutService fetchPinCodeServiceabilityAddress API response :\n\n"+fetchPinCodeServiceabilityAddrReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService fetchPinCodeServiceabilityAddress API response :\n\n"+fetchPinCodeServiceabilityAddrReqGen.respvalidate.returnresponseasstring());
			
		AssertJUnit.assertEquals("CheckoutService fetchPinCodeServiceabilityAddress API is not working", 
				Integer.parseInt(successRespCode), fetchPinCodeServiceabilityAddrReqGen.response.getStatus());
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_absoluteService_getPincodeServicability_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_absoluteService_getPincodeServicability_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		
	}

	/**
	 * This Test Case used to invoke CheckoutService addressService_getPincodeServicability API and verify for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="AddressServiceTests_addressService_getPincodeServicability_verifyItemLevelNodesDP",description="1. provide valid credentials to login \n2.login user using IDP service call. "
					 + "\n 3. fetch user's cart. \n 4. if cart is empty add items to cart"
					 + "\n 5. hit pincodeServiceability API to list down all the serviceable addresses for cart through addressService "
					 + "\n 6. Verify ItemLevelNodes for Pincode Serviceablity in APIs response")
	public void AddressServiceTests_addressService_getPincodeServicability_verifyItemLevelNodes(String userName, String password, String successRespCode)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty ="1";
		String pinCode="";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(LAA_SEARCH_PROD, LAA_SEARCH_QTY, LAA_SEARCH_RET_DOCS, LAA_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE))
						{
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
							{
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue(
										"Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								log.info("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}
			
			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		}
		
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress(userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress - API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress - API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(),	true).contains(SUCCESS_STATUS_TYPE));
			
			pinCode = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_PIN_CODE.toString(), true);
			
		} else pinCode = getAllAddressReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_PIN_CODE.toString(), true);
			
		pinCode = (pinCode).replace("\"", "");
		ServiceType servName = ServiceType.PORTAL_ADDRESS;
		APINAME apiName = APINAME.PINCODESERVICEABILITYADDRESS;
		RequestGenerator fetchPinCodeServiceabilityAddrReqGen = checkoutTestsHelper.fetchPincodeServicabilityAddress(servName,apiName, userName, password, pinCode);
		System.out.println("\nPrinting CheckoutService fetchPinCodeServiceabilityAddress API response :\n\n"+fetchPinCodeServiceabilityAddrReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService fetchPinCodeServiceabilityAddress API response :\n\n"+fetchPinCodeServiceabilityAddrReqGen.respvalidate.returnresponseasstring());
			
		AssertJUnit.assertEquals("CheckoutService fetchPinCodeServiceabilityAddress API is not working", 
				Integer.parseInt(successRespCode), fetchPinCodeServiceabilityAddrReqGen.response.getStatus());
		
		AssertJUnit.assertTrue("pincodeServiceablity itemLevelnode addItemEntries does not exists", 
				fetchPinCodeServiceabilityAddrReqGen.respvalidate.DoesNodeExists(AddressNodes.ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES.toString()));
		
		AssertJUnit.assertTrue("pincodeServiceablity itemLevelnode addItemEntries.deleiveryPromiseTime does not exists", 
				fetchPinCodeServiceabilityAddrReqGen.respvalidate.DoesNodeExists(AddressNodes.ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES_DELPROMISETIME.toString()));
		
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_addressService_getPincodeServicability_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_addressService_getPincodeServicability_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		
	}
	
	/**
	 * This Test Case used to invoke CheckoutService addressService_getPincodeServicability API and verify for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 *//*
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="AddressServiceTests_addressService_getPincodeServicability_verifyItemLevelNodesDP",description="1. provide valid credentials to login \n2.login user using IDP service call. "
					 + "\n 3. fetch user's cart. \n 4. if cart is empty add items to cart"
					 + "\n 5. hit pincodeServiceability API to list down all the serviceable addresses for cart through addressService "
					 + "\n 6. Verify ItemLevelNodes for Pincode Serviceablity in APIs response")
	public void AddressServiceTests_addressService_getPincodeServicability_verifyTNBNodesExists(String userName, String password, String successRespCode)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty ="1";
		String pinCode="";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(LAA_SEARCH_PROD, LAA_SEARCH_QTY, LAA_SEARCH_RET_DOCS, LAA_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE))
						{
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
							{
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue(
										"Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								log.info("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}
			
			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		}
		
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress(userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress - API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress - API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(),	true).contains(SUCCESS_STATUS_TYPE));
			
			pinCode = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_PIN_CODE.toString(), true);
			
		} else pinCode = getAllAddressReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_PIN_CODE.toString(), true);
			
		pinCode = (pinCode).replace("\"", "");
		ServiceType servName = ServiceType.PORTAL_ADDRESS;
		APINAME apiName = APINAME.PINCODESERVICEABILITYADDRESS;
		RequestGenerator fetchPinCodeServiceabilityAddrReqGen = checkoutTestsHelper.fetchPincodeServicabilityAddress(servName,apiName, userName, password, pinCode);
		System.out.println("\nPrinting CheckoutService fetchPinCodeServiceabilityAddress API response :\n\n"+fetchPinCodeServiceabilityAddrReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService fetchPinCodeServiceabilityAddress API response :\n\n"+fetchPinCodeServiceabilityAddrReqGen.respvalidate.returnresponseasstring());
			
		AssertJUnit.assertEquals("CheckoutService fetchPinCodeServiceabilityAddress API is not working", 
				Integer.parseInt(successRespCode), fetchPinCodeServiceabilityAddrReqGen.response.getStatus());
		
		AssertJUnit.assertTrue("pincodeServiceablity itemLevelnode addItemEntries.tryAndBuyEnabled does not exists", 
				fetchPinCodeServiceabilityAddrReqGen.respvalidate.DoesNodeExists(AddressNodes.ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES_TNBENABLED.toString()));
		
		AssertJUnit.assertTrue("pincodeServiceablity itemLevelnode addItemEntries.tryAndBuyOpted does not exists", 
				fetchPinCodeServiceabilityAddrReqGen.respvalidate.DoesNodeExists(AddressNodes.ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES_TNBOPTED.toString()));
		
		AssertJUnit.assertTrue("pincodeServiceablity itemLevelnode addItemEntries.tryAndBuyServiceable does not exists", 
				fetchPinCodeServiceabilityAddrReqGen.respvalidate.DoesNodeExists(AddressNodes.ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES_TNBSERVICEABLE.toString()));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_addressService_getPincodeServicability_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_addressService_getPincodeServicability_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		
	}*/
	
	/**
	 * This Test Case used to invoke CheckoutService absoluteService_getPincodeServicability API and verify for existence of TNB Nodes
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="AddressServiceTests_absolutService_getPincodeServicability_verifyTNBNodesExistsDP",description="1. provide valid credentials to login \n2.login user using IDP service call. "
					 + "\n 3. fetch user's cart. \n 4. if cart is empty add items to cart"
					 + "\n 5. hit pincodeServiceability API to list down all the serviceable addresses for cart through absolut Service "
					 + "\n 6. Verify API response should be success response")
	public void AddressServiceTests_absolutService_getPincodeServicability_verifyTNBNodesExists(String userName, String password, String successRespCode)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty ="1";
		String pinCode="";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(LAA_SEARCH_PROD, LAA_SEARCH_QTY, LAA_SEARCH_RET_DOCS, LAA_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE))
						{
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
							{
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue(
										"Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								log.info("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}
			
			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		}
		
		RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress(userName, password);
		System.out.println("\nPrinting CheckoutService getAllAddress - API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getAllAddress - API response :\n\n"+getAllAddressReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Error while invoking CheckoutService getAllAddress API",
				getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		if(getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(userName, password, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY_CODE, 
					VALID_DEFAULT_ADDRESS, userName, VALID_LOCALITY, userName.substring(0, userName.indexOf("@")), VALID_PINCODE, VALID_STATE_CODE, VALID_STATE_NAME, 
					userName, VALID_MOBILE_NUMBER);
			
			System.out.println("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addNewAddress API response :\n\n"+addNewAddrReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addNewAddress API",
					addNewAddrReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(),	true).contains(SUCCESS_STATUS_TYPE));
			
			pinCode = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_PIN_CODE.toString(), true);
			
		} else pinCode = getAllAddressReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_PIN_CODE.toString(), true);
			
		pinCode = (pinCode).replace("\"", "");
		ServiceType servName = ServiceType.PORTAL_CART;
		APINAME apiName = APINAME.PINCODESERVICEABILITYADDRESS;
		RequestGenerator fetchPinCodeServiceabilityAddrReqGen = checkoutTestsHelper.fetchPincodeServicabilityAddress(servName,apiName, userName, password, pinCode);
		System.out.println("\nPrinting CheckoutService fetchPinCodeServiceabilityAddress API response :\n\n"+fetchPinCodeServiceabilityAddrReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService fetchPinCodeServiceabilityAddress API response :\n\n"+fetchPinCodeServiceabilityAddrReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("CheckoutService fetchPinCodeServiceabilityAddress API is not working", 
				Integer.parseInt(successRespCode), fetchPinCodeServiceabilityAddrReqGen.response.getStatus());
		
		AssertJUnit.assertTrue("pincodeServiceablity itemLevelnode addItemEntries.tryAndBuyEnabled does not exists", 
				fetchPinCodeServiceabilityAddrReqGen.respvalidate.DoesNodeExists(AddressNodes.ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES_TNBENABLED.toString()));
		
		AssertJUnit.assertTrue("pincodeServiceablity itemLevelnode addItemEntries.tryAndBuyOpted does not exists", 
				fetchPinCodeServiceabilityAddrReqGen.respvalidate.DoesNodeExists(AddressNodes.ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES_TNBOPTED.toString()));
		
		AssertJUnit.assertTrue("pincodeServiceablity itemLevelnode addItemEntries.tryAndBuyServiceable does not exists", 
				fetchPinCodeServiceabilityAddrReqGen.respvalidate.DoesNodeExists(AddressNodes.ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES_TNBSERVICEABLE.toString()));
		
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - AddressServiceTests_absolutService_getPincodeServicability_verifyTNBNodesExists : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - AddressServiceTests_absolutService_getPincodeServicability_verifyTNBNodesExists : "+timeTaken+" seconds\n");
		
	}


}
