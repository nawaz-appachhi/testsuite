
package com.myntra.apiTests.portalservices.pricingpromotionservices;


import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutServiceConstants;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutTestsHelper;
import com.myntra.apiTests.portalservices.idpservice.IDPServiceHelper;
import com.myntra.apiTests.portalservices.all.AddressServiceTests;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.FirstOrderFreeShippingDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.apiTests.portalservices.commons.StatusNodes;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.portalservices.all.IDPService;
import com.myntra.apiTests.portalservices.idpservice.IDPServiceConstants;

/**
 * Verifies the first_order and first_order_id in the hallmark response.Before and After user place an order.
 * This Covers the Signup and login from Email only. As there are no exposed APIs to login and Signup through facebook and google.
 * @author Suhas.Kashyap
 *
 */
public class FirstOrderFreeShipping extends BaseTest {

	private static Logger logger = LoggerFactory.getLogger(FirstOrderFreeShipping.class);
	static IDPServiceHelper idpServiceHelper = new IDPServiceHelper();
	private static String logInSessionID;
	Initialize init = new Initialize("/Data/configuration");
	APIUtilities apiUtil = new APIUtilities();

	/**
	 *  Cancel the order
	 * @param orderID order ID which needs to be canceled.
	 * @param user login of the user who has placed the order
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 * @author Suhas.Kashyap
	 */
	public void cancelOrder(String orderID,String user)throws JAXBException, UnsupportedEncodingException {
		OMSServiceHelper OrderResponse = new OMSServiceHelper();
		try{
			OrderResponse.cancelOrder(orderID, "1", user, "TestOrder Cancellation");
			logger.info("Order "+ orderID+" cancelled Successfully");
			System.out.println("Order "+ orderID+" cancelled Successfully for the user "+user);
		} catch(JAXBException e) {
			e.printStackTrace();
			logger.error("Order Cancel Failed "+e.getMessage());
			System.out.println("Order Cancel Failed "+e.getMessage());
		}
		catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("Order Cancel Failed "+e.getMessage());
			System.out.println("Order Cancel Failed "+e.getMessage());
		}
	}

	/**
	 * Retrieves the email ID from the UIDX.
	 * @param uidx UIDX of the User whose email id is required.
	 * @return email id as a String
	 * @author Suhas.Kashyap
	 */
	public String getMailID(String uidx){
		String mailID="";
		MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA,APINAME.GETMAILIDFROMUIDX, init.Configurations,PayloadType.JSON,new String[] {uidx},PayloadType.JSON);
		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("Service URL ======>>>>> "+service.URL);
		String jsonRes=requestGenerator.respvalidate.returnresponseasstring();
		System.out.println("Json Response is: ----->>> "+jsonRes);
		AssertJUnit.assertEquals("Status not equal to 200", 200,requestGenerator.response.getStatus());
		mailID = JsonPath.read(jsonRes,"$..email").toString().replace("[\"", "").replace("\"]", "");
		if(mailID == ""){
			System.out.println("Mail ID not found");
			logger.error("Mail Id not found");
		}
		return mailID;
	}

	/**
	 * logs in the user
	 * @param userUidx - UIDX of the user
	 * @param password - password of the user whose UIDX is passed in the first parameter.
	 * @author Suhas.Kashyap
	 */
	public void login(String userUidx,String password){
		String uName = getMailID(userUidx);
		try{
			RequestGenerator signInReqGen = idpServiceHelper.performSignInOperation(uName, password);
			System.out.println("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
			logger.info("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertEquals("IDPService signIn API is not working", 
					Integer.parseInt(IDPServiceConstants.SUCCESS_RESP_CODE), signInReqGen.response.getStatus());
			logInSessionID = signInReqGen.response.getHeaderString("Set-Cookie").split(";")[0].split("=")[1];
			System.out.println("Session ID After Login : "+logInSessionID);
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("\nLogin Failed\n");
			logger.error("Sign in Failed  "+e.getMessage());
		}
	}

	/**
	 * logs out the user
	 * @param userUidx - UIDX of the user
	 * @param password - password of the user whose UIDX is passed in the first parameter.
	 * @author Suhas.Kashyap
	 */
	public void logout(String userUidx,String password){
		String uName = getMailID(userUidx);
		IDPService idp = new IDPService();
		try{
			idp.IDPService_signOut_verifySuccessResponse(uName, password, "200");
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("\nLogout Failed\n");
			logger.error("Failed to Signout  "+e.getMessage());
		}
	}

	/**
	 * Place an order for the user whose UIDX is passed.
	 * @param uidx - UIDX of the user 
	 * @param password password of the user whose UIDX is passed.
	 * @return orderID as a String
	 * @author Suhas.Kashyap
	 */
	public String placeOrder(String uidx, String password){
		CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();
		String orderID= null;
		AddressServiceTests address = new AddressServiceTests();
		try{
			address.AddressServiceTests_addNewAddress_verifySuccessResponse(getMailID(uidx), password, "Begur", "Bangalore", "IN", 
					"true", getMailID(uidx), "Begur", "AutoFreeshippinguser", "560068", "KA", "Karnataka",getMailID(uidx) , 
					"1234567890", "200");
		} catch (Exception e){
			logger.error("Address not added");
			System.out.println("Address not added");
		}
		List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds("Roadster Socks", "10", "true", "true");
		if(!CollectionUtils.isEmpty(styleIdList))
		{
			for(Integer styleId : styleIdList)
			{
				System.out.println("\nPrinting the StyleId : "+styleId);
				logger.info("\nPrinting the StyleId : "+styleId);

				RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
				System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
				logger.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());

				AssertJUnit.assertTrue("Error while getting style data",
						getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));

				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

				for(int i = 0 ; i < ids.size(); i++)
				{
					String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
					String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
					String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();

					if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
					{
						End2EndHelper e2eHelper = new End2EndHelper();

						try{
							orderID=""+e2eHelper.createOrder(getMailID(uidx), password, "6132352", new String[]{skuId+":1"}, "", false, false, false, "", false);
							break;
						} catch( Exception e){
							e.printStackTrace();
						}
					}
				}
				if(orderID != null)
					break;
			}
		}
		if(orderID == null){
			System.out.println("Not Placed any order");
			logger.error("Order is not placed");
		}
		else {
			System.out.println("Order is placed and the order ID is:"+orderID);
			logger.info("Order is placed and the order ID is:"+orderID);
		}
		return orderID;
	}

	/**
	 * Verifies the Hallmark Response for the New User After Placing an order.
	 * @param uidx - UIDX of the User
	 * @param password - Password of the user whose UIDX is passing in the first parameter
	 * @param payload Payload which needs to be used in the Hallmark API.
	 * @author Suhas.Kashyap
	 */

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" }, dataProviderClass = FirstOrderFreeShippingDP.class, dataProvider = "NewuserFreeShipping")
	public void verifyFreeShippingAfterOrderPlaced(String uidx,String password,String payload) throws Exception{
		String order = placeOrder(uidx,password);
		Thread.sleep(6000);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FREESHIPPING,
				APINAME.NEWUSERFREESHIPPING, init.Configurations, new String[] {payload},new String[] {uidx},
				PayloadType.JSON, PayloadType.JSON);
		verifyHallmarkResponse(service, "FALSE", order);
		cancelOrder(order,getMailID(uidx));
	}

	/**
	 * Verify the Free shipping Flag After placing an order in the hallmark response.when user logs in.
	 * @param uidx - UIDX of the User
	 * @param password - password of the user whose UIDX is passed in the first parameter.
	 * @param payload - hallmark API payload
	 * @author Suhas.kashyap
	 */
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" }, dataProviderClass = FirstOrderFreeShippingDP.class, dataProvider = "NewuserFreeShipping")
	public void verifyFreeShippingAfterOrderPlacedWhenLogin(String uidx,String password,String payload) throws Exception{

		String order = placeOrder(uidx,password);
		logout(uidx,password);
		login(uidx,password);
		Thread.sleep(6000);
		if(logInSessionID == FirstOrderFreeShippingDP.signUpSessionID){
			logger.error("Login has not happened");
			AssertJUnit.assertTrue("Login failed",false);
		}
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FREESHIPPING,
				APINAME.NEWUSERFREESHIPPING, init.Configurations, new String[] {payload},new String[] {uidx},
				PayloadType.JSON, PayloadType.JSON);
		verifyHallmarkResponse(service, "FALSE", order);
		cancelOrder(order,getMailID(uidx));
	}


	/**
	 * Verifies the Hallmark Response for the New User Before Placing an order.
	 * @param uidx - UIDX of the User
	 * @param dummyParameter - This is not used any where in the method. 
	 * It is used as a dummy parameter to use the Same data Provider for different methods.It can be any String value.
	 * @param payload Payload which needs to be used in the Hallmark API.
	 * @author Suhas.Kashyap
	 */
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" }, dataProviderClass = FirstOrderFreeShippingDP.class, dataProvider = "NewuserFreeShipping")
	public void verifyFreeShippingBeforeOrderPlaced(String uidx,String dummyParameter,String payload){

		MyntraService service = Myntra.getService(ServiceType.PORTAL_FREESHIPPING,
				APINAME.NEWUSERFREESHIPPING, init.Configurations, new String[] {payload},new String[] {uidx},
				PayloadType.JSON, PayloadType.JSON);
		verifyHallmarkResponse(service, "TRUE", null);
	}

	/**
	 * Verify the Free shipping in the hallmark response.when user logs in.
	 * @param uidx - UIDX of the User
	 * @param password - password of the user whose UIDX is passed in the first parameter.
	 * @param payload - hallmark API payload
	 * @author Suhas.Kashyap
	 */
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" }, dataProviderClass = FirstOrderFreeShippingDP.class, dataProvider = "NewuserFreeShipping")
	public void verifyFreeShippingBeforeOrderPlacedWhenLogin(String uidx,String password,String payload){
		logout(uidx,password);
		login(uidx,password);
		if(logInSessionID == FirstOrderFreeShippingDP.signUpSessionID){
			logger.error("Login has not happened");
			AssertJUnit.assertTrue("Login failed",false);
		}
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FREESHIPPING,
				APINAME.NEWUSERFREESHIPPING, init.Configurations, new String[] {payload},new String[] {uidx},
				PayloadType.JSON, PayloadType.JSON);
		verifyHallmarkResponse(service, "TRUE", null);
	}


	/**
	 * Verifies the Hallmark response 
	 * @param service - End point for the hallmark.
	 * @param firstOrderStatus - expected first_Order Status value.
	 * @param orderID -Expected first_order_id flag value. This should only applicable when first_order is false. specify null when first_order is true.
	 * @author Suhas.Kashyap
	 */
	public void verifyHallmarkResponse(MyntraService service,String firstOrderStatus,String orderID){
		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("Service URL ======>>>>> "+service.URL);
		System.out.println("Service payload ------>>>>> "+service.Payload);
		String jsonRes=requestGenerator.respvalidate.returnresponseasstring();
		System.out.println("Json Response is: ----->>> "+jsonRes);
		AssertJUnit.assertEquals("Status not equal to 200", 200,requestGenerator.response.getStatus());
		String firstOrder = JsonPath.read(jsonRes,"$.formattedUser.free_shipping.first_order").toString().toUpperCase();
		AssertJUnit.assertEquals("First Order is not equal to "+firstOrderStatus, firstOrderStatus,firstOrder);
		if(orderID != null){
			String firstOrderID = JsonPath.read(jsonRes,"$.formattedUser.user_orders.first_order_id").toString().toUpperCase();
			AssertJUnit.assertEquals("First Order is not equal to "+orderID, orderID,firstOrderID);
		}
	}
}


