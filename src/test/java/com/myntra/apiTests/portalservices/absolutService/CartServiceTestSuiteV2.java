package com.myntra.apiTests.portalservices.absolutService;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.testbase.BaseTest; 

public class CartServiceTestSuiteV2 extends BaseTest implements AbsolutConstants{

	public CartServiceTestSuiteV2() {
		new CartServiceTestsDPV2();
	}
	static Logger log = Logger.getLogger(CartServiceTestSuiteV2.class);
	AbsolutHelperV2 absolutHelperV2 = new AbsolutHelperV2();
	static Initialize init = new Initialize("./Data/configuration");	
	
	
	@Test(groups = { "San","Sanity", "FoxSanity", "Regression", "MiniRegression",
	"ExhaustiveRegression" }, dataProvider = "addItemToCart_verifyAPIIsUpDP",dataProviderClass= CartServiceTestsDPV2.class, description = "1. Perform search and extract styleIds 2.Check if searched styleIds exists in user's cart or not. "
			+ "\n 3. login user using IDP service call. 4.hit addItemToCart API giving skuId which doesnt exists in cart"
			+ "\n 5.Check the service response must be 200")
public void addItemToCart_verifyAPIIsUp(String tenantId, String userName, String password,
	String skuId, String successRespCode, String successStatusMsg, String successStatusType) {
long startTime = Calendar.getInstance().getTimeInMillis();
String tokens[] = absolutHelperV2.idp_GetTokens(userName, password);
AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
RequestGenerator addItemToCartReqGen = absolutHelperV2.addItemToCart(tokens[0], tenantId, skuId, "1");
System.out.println("\nPrinting CheckoutService addItemToCart API response:\n\n"
	+addItemToCartReqGen.respvalidate.returnresponseasstring());

AssertJUnit.assertEquals("addItemToCart API is not working",
	Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());
absolutHelperV2.performSignOutOperation(userName, tokens[1]);
long endTime = Calendar.getInstance().getTimeInMillis();
double timeTaken = (endTime - startTime) / 1000.0;
  
System.out.println("\nTime taken to execute - TestCase - addItemToCart_verifyAPIIsUp : "+ timeTaken + " seconds\n");

}
}
