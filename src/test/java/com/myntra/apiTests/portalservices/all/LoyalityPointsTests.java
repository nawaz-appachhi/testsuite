package com.myntra.apiTests.portalservices.all;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.LoyalityDP;
import com.myntra.apiTests.portalservices.loyalitypointsservice.LoyalityPointsServiceHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.MyntAssert;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * @author shankara.c
 *
 */
public class LoyalityPointsTests extends LoyalityDP
{
	static LoyalityPointsServiceHelper loyalityPointsServiceHelper = new LoyalityPointsServiceHelper();
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(LoyalityPointsTests.class);
	static String[] expAct;

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression","Fox7Sanity" }, dataProvider = "creditLoyaltyPointsDataProvider")
	public void LoyaltyPoints_creditLoyalityPoints_verifySuccessResponse(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {

		RequestGenerator req = creditLoyaltyPoints(login, activeCreditPoints,
				inActiveCreditPoints, activeDebitPoints, inActiveDebitPoints,
				description, itemType, itemId, businessProcess);
	//	System.out.println("test change");
		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" ,"Fox7Sanity" }, dataProvider = "creditLoyaltyPointsDataProvider")
	public void LoyaltyPoints_creditLoyalityPoints_verifyStatusMessage(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {

		RequestGenerator req = creditLoyaltyPoints(login, activeCreditPoints,
				inActiveCreditPoints, activeDebitPoints, inActiveDebitPoints,
				description, itemType, itemId, businessProcess);

		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("credited successfuly"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyaltyPointsDataProvider")
	public void LoyaltyPoints_creditLoyalityPoints_verifyStatusType(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {

		RequestGenerator req = creditLoyaltyPoints(login, activeCreditPoints,
				inActiveCreditPoints, activeDebitPoints, inActiveDebitPoints,
				description, itemType, itemId, businessProcess);

		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusType);
		System.out.println(statusType);

		AssertJUnit.assertTrue("StatusType does not match",
				req.respvalidate.NodeValue("status.statusType", true)
						.toLowerCase().contains("success"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression","Fox7Sanity"}, dataProvider = "creditActiveLoyaltyPointsPositiveCasesDataProvider")
	public void LoyaltyPoints_creditActiveLoyalityPoints_verifyBalance(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		Double balanceBefore, balanceAfter, finalBalance;

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before credit:"
				+ balanceBefore);

		RequestGenerator req = creditLoyaltyPoints(login, activeCreditPoints,
				inActiveCreditPoints, activeDebitPoints, inActiveDebitPoints,
				description, itemType, itemId, businessProcess);

		balanceAfter = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance after credit :"
				+ balanceAfter);

		finalBalance = balanceBefore + Double.parseDouble(activeCreditPoints);
		System.out.println("Final Balance:" + finalBalance);

		AssertJUnit
				.assertEquals("Balance Mismatch", finalBalance, balanceAfter);

		MyntAssert.assertEquals(
				"Credit Active Loyality Points API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression","Fox7Sanity" }, dataProvider = "creditInActiveLoyaltyPointsPositiveCasesDataProvider")
	public void LoyaltyPoints_creditInActiveLoyalityPoints_verifyBalance(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		Double balanceBefore, balanceAfter, finalBalance;

		balanceBefore = getInActiveLoyaltyPointBalance(login);
		System.out.println("InActive Loyalty Points balance before credit:"
				+ balanceBefore);

		RequestGenerator req = creditLoyaltyPoints(login, activeCreditPoints,
				inActiveCreditPoints, activeDebitPoints, inActiveDebitPoints,
				description, itemType, itemId, businessProcess);

		balanceAfter = getInActiveLoyaltyPointBalance(login);
		System.out.println("InActive Loyalty Points balance after credit :"
				+ balanceAfter);

		finalBalance = balanceBefore + Double.parseDouble(inActiveCreditPoints);
		System.out.println("Final Balance:" + finalBalance);

		AssertJUnit
				.assertEquals("Balance Mismatch", finalBalance, balanceAfter);

		MyntAssert.assertEquals(
				"Credit InActive Loyality Points API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditBothActiveAndInactiveLoyaltyPointsDataProvider")
	public void LoyaltyPoints_creditBothActiveAndInactiveLoyaltyPoints_verifyBalance(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		Double ActivePointsBalanceBefore, ActivePointsBalanceAfter, FinalActivePointsBalance;
		Double InactivePointsBalanceBefore, InactivePointsBalanceAfter, FinalInactivePointsBalance;

		ActivePointsBalanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points Balance before Credit :"
				+ ActivePointsBalanceBefore);

		InactivePointsBalanceBefore = getInActiveLoyaltyPointBalance(login);
		System.out.println("InActive Loyalty Points Balance before Credit :"
				+ InactivePointsBalanceBefore);

		RequestGenerator req = creditLoyaltyPoints(login, activeCreditPoints,
				inActiveCreditPoints, activeDebitPoints, inActiveDebitPoints,
				description, itemType, itemId, businessProcess);

		ActivePointsBalanceAfter = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points Balance after Credit :"
				+ ActivePointsBalanceAfter);

		InactivePointsBalanceAfter = getInActiveLoyaltyPointBalance(login);
		System.out.println("InActive Loyalty Points Balance after Credit :"
				+ InactivePointsBalanceAfter);

		FinalActivePointsBalance = ActivePointsBalanceBefore
				+ Double.parseDouble(activeCreditPoints);
		System.out.println("Final Active Loyalty Points Balance after Credit :"
				+ FinalActivePointsBalance);

		FinalInactivePointsBalance = InactivePointsBalanceBefore
				+ Double.parseDouble(inActiveCreditPoints);
		System.out
				.println("Final InActive Loyalty Points Balance after Credit :"
						+ FinalInactivePointsBalance);

		AssertJUnit.assertEquals("Active Credit Points Balance Mismatch",
				FinalActivePointsBalance, ActivePointsBalanceAfter);

		AssertJUnit.assertEquals("InActive Credit Points Balance Mismatch",
				FinalInactivePointsBalance, InactivePointsBalanceAfter);

		MyntAssert
				.assertEquals(
						"Credit Both Active and InActive Loyality Points API is not working",
						200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);

	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression","Fox7Sanity"}, dataProvider = "creditLoyaltyPointsDataProvider")
	public void LoyaltyPoints_creditLoyalityPoints_verifyLogin(String login,
			String activeCreditPoints, String inActiveCreditPoints,
			String activeDebitPoints, String inActiveDebitPoints,
			String description, String itemType, String itemId,
			String businessProcess) {

		RequestGenerator req = creditLoyaltyPoints(login, activeCreditPoints,
				inActiveCreditPoints, activeDebitPoints, inActiveDebitPoints,
				description, itemType, itemId, businessProcess);

		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());

		System.out.println("Login ID in request is :" + login);

		String loginID = req.respvalidate
				.NodeValue("transactiontEntry.login", true)
				.replaceAll("\"", "").trim();
		System.out.println("Login ID in transactiontEntry Node's response is: "
				+ loginID);
		log.info(loginID);

		String loginID1 = req.respvalidate
				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
				.trim();
		System.out.println("Login ID in userAccountEntry Node's response is: "
				+ loginID1);
		log.info(loginID1);

		AssertJUnit.assertEquals(
				"Login ID in transactiontEntry Node does not match", login,
				loginID);
		AssertJUnit.assertEquals(
				"Login ID in userAccountEntry Node does not match", login,
				loginID1);
		MyntAssert.setExpectedAndActualForMyntListen(true);

	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyaltyPointsDataProvider")
	public void LoyaltyPoints_creditLoyalityPoints_verifyTransactionEntry(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {

		RequestGenerator req = creditLoyaltyPoints(login, activeCreditPoints,
				inActiveCreditPoints, activeDebitPoints, inActiveDebitPoints,
				description, itemType, itemId, businessProcess);

		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());

		System.out.println("Description in request is :" + description);
		String description_response = req.respvalidate
				.NodeValue("transactiontEntry.description", true)
				.replaceAll("\"", "").trim();
		System.out
				.println("Description in transactiontEntry Node's response is: "
						+ description_response);
		log.info(description_response);

		System.out.println("Item Type in request is :" + itemType);
		String itemType_response = req.respvalidate
				.NodeValue("transactiontEntry.itemType", true)
				.replaceAll("\"", "").trim();
		System.out
				.println("item Type in transactiontEntry Node's response is: "
						+ itemType_response);
		log.info(itemType_response);

		System.out.println("Item ID in request is :" + itemId);
		String itemId_response = req.respvalidate
				.NodeValue("transactiontEntry.itemId", true)
				.replaceAll("\"", "").trim();
		System.out.println("itemID in transactiontEntry Node's response is: "
				+ itemId_response);
		log.info(itemId_response);

		System.out
				.println("Business Process in request is :" + businessProcess);
		String businessProcess_response = req.respvalidate
				.NodeValue("transactiontEntry.businessProcess", true)
				.replaceAll("\"", "").trim();
		System.out
				.println("Business Process in transactiontEntry Node's response is: "
						+ businessProcess_response);
		log.info(businessProcess_response);

		AssertJUnit
				.assertEquals(
						"Description in transactiontEntry Node's response does not match",
						description, description_response);
		AssertJUnit
				.assertEquals(
						"Item Type in transactiontEntry Node's response does not match",
						itemType, itemType_response);
		AssertJUnit.assertEquals(
				"Item ID in transactiontEntry Node's response does not match",
				itemId, itemId_response);
		AssertJUnit
				.assertEquals(
						"Business Process in transactiontEntry Node's response does not match",
						businessProcess, businessProcess_response);
		MyntAssert.setExpectedAndActualForMyntListen(true);

	}

	private Double getActiveLoyaltyPointBalance(String loginID) {
		String activePointsBalance;
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { loginID }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		activePointsBalance = req.respvalidate.NodeValue(
				"userAccountEntry.activePointsBalance", true);
		AssertJUnit.assertEquals("Get Transaction History API is not working",
				200, req.response.getStatus());

		return Double.parseDouble(activePointsBalance);
	}

	private Double getInActiveLoyaltyPointBalance(String loginID) {
		String InactivePointsBalance;
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYINACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { loginID }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		InactivePointsBalance = req.respvalidate.NodeValue(
				"userAccountEntry.inActivePointsBalance", true);
		AssertJUnit.assertEquals(
				"Get Transaction History Inactive API is not working", 200,
				req.response.getStatus());

		return Double.parseDouble(InactivePointsBalance);
	}

	private RequestGenerator debitLoyaltyPoints(String login,
			String activeCreditPoints, String inActiveCreditPoints,
			String activeDebitPoints, String inActiveDebitPoints,
			String description, String itemType, String itemId,
			String businessProcess) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYPOINTS, init.Configurations, new String[] {
						login, activeCreditPoints, inActiveCreditPoints,
						activeDebitPoints, inActiveDebitPoints, description,
						itemType, itemId, businessProcess }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.assertEquals("Debit Loyality Points is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		return req;

	}

	private RequestGenerator creditLoyaltyPoints(String login,
			String activeCreditPoints, String inActiveCreditPoints,
			String activeDebitPoints, String inActiveDebitPoints,
			String description, String itemType, String itemId,
			String businessProcess) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYPOINTS, init.Configurations,
				new String[] { login, activeCreditPoints, inActiveCreditPoints,
						activeDebitPoints, inActiveDebitPoints, description,
						itemType, itemId, businessProcess }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());
		MyntAssert.setJsonResponse(req.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		return req;

	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression","Fox7Sanity" }, dataProvider = "debitLoyaltyPointsDataProvider")
	public void LoyaltyPoints_debitLoyalityPoints_verifySuccessResponse(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYPOINTS, init.Configurations, new String[] {
						login, activeCreditPoints, inActiveCreditPoints,
						activeDebitPoints, inActiveDebitPoints, description,
						itemType, itemId, businessProcess }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.assertEquals("Debit Loyality Points is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" ,"Fox7Sanity" }, dataProvider = "debitLoyaltyPointsDataProvider")
	public void LoyaltyPoints_debitLoyalityPoints_verifyStatusMessage(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		Double balanceBefore, DebitPoints;
		DebitPoints = Double.parseDouble(activeDebitPoints);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit: "
				+ balanceBefore);
		System.out.println("Points to be debited: " + DebitPoints);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Points to be Debited is greater than the balanace available");
			RequestGenerator newReq = debitLoyaltyPoints(login,
					activeCreditPoints, inActiveCreditPoints,
					activeDebitPoints, inActiveDebitPoints, description,
					itemType, itemId, businessProcess);

			String statusMessage = newReq.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			System.out.println("statusMessage : " + statusMessage);

			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("Loyalty Points Debit Failure"));
		}

		else {
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY, APINAME.DEBITLOYALITYPOINTS,
					init.Configurations, new String[] { login,
							activeCreditPoints, inActiveCreditPoints,
							activeDebitPoints, inActiveDebitPoints,
							description, itemType, itemId, businessProcess },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			log.info(service.URL);
			System.out.println(service.URL);
			MyntAssert.assertEquals("Debit Loyality Points is not working",
					200, req.response.getStatus());

			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			log.info(statusMessage);
			System.out.println(statusMessage);
			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("debited successfuly"));
		}
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression","Fox7Sanity"  }, dataProvider = "debitLoyaltyPointsDataProvider")
	public void LoyaltyPoints_debitLoyalityPoints_verifyStatusType(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {

		Double balanceBefore, DebitPoints;
		DebitPoints = Double.parseDouble(activeDebitPoints);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit: "
				+ balanceBefore);
		System.out.println("Points to be debited: " + DebitPoints);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Points to be Debited is greater than the balanace available");
			RequestGenerator newReq = debitLoyaltyPoints(login,
					activeCreditPoints, inActiveCreditPoints,
					activeDebitPoints, inActiveDebitPoints, description,
					itemType, itemId, businessProcess);

			String statusType = newReq.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();

			System.out.println("statusType : " + statusType);

			AssertJUnit.assertTrue("Status Type doesn't match",
					statusType.contains("ERROR"));

		}

		else {

			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY, APINAME.DEBITLOYALITYPOINTS,
					init.Configurations, new String[] { login,
							activeCreditPoints, inActiveCreditPoints,
							activeDebitPoints, inActiveDebitPoints,
							description, itemType, itemId, businessProcess },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			log.info(service.URL);
			System.out.println(service.URL);
			MyntAssert.assertEquals("Debit Loyality Points is not working",
					200, req.response.getStatus());

			String statusType = req.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();
			log.info(statusType);
			System.out.println(statusType);

			AssertJUnit.assertTrue("StatusType does not match",
					req.respvalidate.NodeValue("status.statusType", true)
							.toLowerCase().contains("success"));
		}
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression","Fox7Sanity"  }, dataProvider = "debitLoyaltyPointsDataProvider")
	public void LoyaltyPoints_debitLoyalityPoints_verifyStatusCode(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {

		Double balanceBefore, DebitPoints;
		DebitPoints = Double.parseDouble(activeDebitPoints);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit: "
				+ balanceBefore);
		System.out.println("Points to be debited: " + DebitPoints);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Points to be Debited is greater than the balanace available");
			RequestGenerator newReq = debitLoyaltyPoints(login,
					activeCreditPoints, inActiveCreditPoints,
					activeDebitPoints, inActiveDebitPoints, description,
					itemType, itemId, businessProcess);

			String statusCode = newReq.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();

			System.out.println("statusCode : " + statusCode);

			AssertJUnit.assertTrue("Status Code doesn't match",
					statusCode.contains("10004"));

		}

		else {

			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY, APINAME.DEBITLOYALITYPOINTS,
					init.Configurations, new String[] { login,
							activeCreditPoints, inActiveCreditPoints,
							activeDebitPoints, inActiveDebitPoints,
							description, itemType, itemId, businessProcess },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			log.info(service.URL);
			System.out.println(service.URL);
			MyntAssert.assertEquals("Debit Loyality Points is not working",
					200, req.response.getStatus());

			String statusCode = req.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();
			log.info(statusCode);
			System.out.println(statusCode);

			AssertJUnit.assertTrue("StatusCode does not match",
					req.respvalidate.NodeValue("status.statusCode", true)
							.toLowerCase().contains("1"));
		}
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitActiveLoyaltyPointsPositiveCasesDataProvider")
	public void LoyaltyPoints_debitActiveLoyalityPointsCalculations_positiveCases(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		Double balanceBefore, balanceAfter, finalBalance, DebitPoints;

		DebitPoints = Double.parseDouble(activeDebitPoints);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit: "
				+ balanceBefore);
		System.out.println("Points to be debited: " + DebitPoints);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Points to be Debited is greater than the balanace available");
			RequestGenerator newReq = debitLoyaltyPoints(login,
					activeCreditPoints, inActiveCreditPoints,
					activeDebitPoints, inActiveDebitPoints, description,
					itemType, itemId, businessProcess);

			balanceAfter = getActiveLoyaltyPointBalance(login);
			System.out.println("Active Loyalty Points balance after debit :"
					+ balanceAfter);

			AssertJUnit.assertEquals("Balance Mismatch", balanceBefore,
					balanceAfter);
		}

		else {
			// System.out.println("........Debiting Active Loyalty Points....");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY, APINAME.DEBITLOYALITYPOINTS,
					init.Configurations, new String[] { login,
							activeCreditPoints, inActiveCreditPoints,
							activeDebitPoints, inActiveDebitPoints,
							description, itemType, itemId, businessProcess },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			balanceAfter = getActiveLoyaltyPointBalance(login);
			System.out.println("Active Loyalty Points balance after debit :"
					+ balanceAfter);

			finalBalance = balanceBefore
					- Double.parseDouble(activeDebitPoints);
			System.out.println("Final Balance:" + finalBalance);

			AssertJUnit.assertEquals("Balance Mismatch", finalBalance,
					balanceAfter);

			MyntAssert.assertEquals(
					"Debit Active Loyality Points API is not working", 200,
					req.response.getStatus());
			MyntAssert.setExpectedAndActualForMyntListen(true);
		}
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyaltyPointsNegativeCasesDataProvider")
	public void LoyaltyPoints_debitLoyalityPoints_negativeCases(String login,
			String activeCreditPoints, String inActiveCreditPoints,
			String activeDebitPoints, String inActiveDebitPoints,
			String description, String itemType, String itemId,
			String businessProcess) {
		Double balanceBefore, DebitPoints;

		DebitPoints = Double.parseDouble(activeDebitPoints);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit: "
				+ balanceBefore);
		System.out.println("Points to be debited: " + DebitPoints);

		if (DebitPoints < 0) {
			System.out.println("Points to be Debited is lesser than zero");
			RequestGenerator newReq = debitLoyaltyPoints(login,
					activeCreditPoints, inActiveCreditPoints,
					activeDebitPoints, inActiveDebitPoints, description,
					itemType, itemId, businessProcess);

			String statusCode = newReq.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();
			String statusMessage = newReq.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			String statusType = newReq.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();

			System.out.println("StatusCode : " + statusCode);
			System.out.println("statusMessage : " + statusMessage);
			System.out.println("statusType : " + statusType);

			AssertJUnit.assertTrue("Status code doesn't match",
					statusCode.contains("-1"));
			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("incomplete transaction entry"));
			AssertJUnit.assertTrue("Status Type doesn't match",
					statusType.contains("ERROR"));
		}

		else {
			// Expected 500 internal server error
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY, APINAME.DEBITLOYALITYPOINTS,
					init.Configurations, new String[] { login,
							activeCreditPoints, inActiveCreditPoints,
							activeDebitPoints, inActiveDebitPoints,
							description, itemType, itemId, businessProcess },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			AssertJUnit.assertEquals("API response code mismatch", 500,
					req.response.getStatus());
			MyntAssert.setExpectedAndActualForMyntListen(true);
		}
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitInActiveLoyaltyPointsPositiveCasesDataProvider")
	public void LoyaltyPoints_debitInActiveLoyalityPoints_verifyCalculation(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		Double balanceBefore, balanceAfter, finalBalance, DebitPoints;

		DebitPoints = Double.parseDouble(inActiveDebitPoints);

		balanceBefore = getInActiveLoyaltyPointBalance(login);
		System.out.println("InActive Loyalty Points balance before debit:"
				+ balanceBefore);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Amount to be debited is greater than Balance available");
			RequestGenerator newReq = debitLoyaltyPoints(login,
					activeCreditPoints, inActiveCreditPoints,
					activeDebitPoints, inActiveDebitPoints, description,
					itemType, itemId, businessProcess);

			String statusCode = newReq.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();
			String statusMessage = newReq.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			String statusType = newReq.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();

			System.out.println("StatusCode : " + statusCode);
			System.out.println("statusMessage : " + statusMessage);
			System.out.println("statusType : " + statusType);

			AssertJUnit.assertTrue("Status code doesn't match",
					statusCode.contains("1"));
			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("debited successfuly"));
			AssertJUnit.assertTrue("Status Type doesn't match",
					statusType.contains("SUCCESS"));

			balanceAfter = getInActiveLoyaltyPointBalance(login);
			System.out.println("Active Loyalty Points balance after debit :"
					+ balanceAfter);

			AssertJUnit.assertEquals("Balance Mismatch", 0.0, balanceAfter);

		}

		else {

			System.out
					.println(".....Debiting Inactive Loyalty Points from account..... ");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY, APINAME.DEBITLOYALITYPOINTS,
					init.Configurations, new String[] { login,
							activeCreditPoints, inActiveCreditPoints,
							activeDebitPoints, inActiveDebitPoints,
							description, itemType, itemId, businessProcess },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			balanceAfter = getInActiveLoyaltyPointBalance(login);
			System.out.println("InActive Loyalty Points balance after debit :"
					+ balanceAfter);

			finalBalance = balanceBefore
					- Double.parseDouble(inActiveDebitPoints);
			System.out.println("Final Balance:" + finalBalance);

			AssertJUnit.assertEquals("Balance Mismatch", finalBalance,
					balanceAfter);

			MyntAssert.assertEquals(
					"Debit InActive Loyality Points API is not working", 200,
					req.response.getStatus());
			MyntAssert.setExpectedAndActualForMyntListen(true);
		}
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitBothActiveAndInactiveLoyaltyPointsDataProvider")
	public void LoyaltyPoints_debitBothActiveAndInactiveLoyaltyPoints_verifyCalculations(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		Double ActivePointsBalanceBefore, ActivePointsBalanceAfter, FinalActivePointsBalance;
		Double InactivePointsBalanceBefore, InactivePointsBalanceAfter, FinalInactivePointsBalance, DebitActivePoints, DebitInactivePoints;

		DebitActivePoints = Double.parseDouble(activeDebitPoints);
		DebitInactivePoints = Double.parseDouble(inActiveDebitPoints);

		ActivePointsBalanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points Balance before Debit :"
				+ ActivePointsBalanceBefore);

		System.out.println("Active Points to be Debited :" + DebitActivePoints);

		InactivePointsBalanceBefore = getInActiveLoyaltyPointBalance(login);
		System.out.println("InActive Loyalty Points Balance before Debit :"
				+ InactivePointsBalanceBefore);

		System.out.println("In Active Points to be Debited :"
				+ DebitInactivePoints);

		// If Debit Active points is > Active Points Balance, irrespective of
		// whether In Active Debit Points is greater or lesser than In Active
		// Points Balance
		if (DebitActivePoints > ActivePointsBalanceBefore) {
			System.out
					.println(" Active Debit Points is greater than Active Points Balance");
			RequestGenerator newReq = debitLoyaltyPoints(login,
					activeCreditPoints, inActiveCreditPoints,
					activeDebitPoints, inActiveDebitPoints, description,
					itemType, itemId, businessProcess);

			String statusMessage = newReq.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			System.out.println("statusMessage : " + statusMessage);

			String statusType = newReq.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();

			System.out.println("statusType : " + statusType);

			String statusCode = newReq.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();

			System.out.println("statusCode : " + statusCode);

			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("Loyalty Points Debit Failure"));

			AssertJUnit.assertTrue("Status Type doesn't match",
					statusType.contains("ERROR"));

			AssertJUnit.assertTrue("Status Code doesn't match",
					statusCode.contains("10004"));

			ActivePointsBalanceAfter = getActiveLoyaltyPointBalance(login);
			System.out.println("Active Loyalty Points balance does not change:"
					+ ActivePointsBalanceAfter);

			InactivePointsBalanceAfter = getInActiveLoyaltyPointBalance(login);
			System.out
					.println("In Active Loyalty Points balance does not change  :"
							+ InactivePointsBalanceAfter);

			AssertJUnit.assertEquals("Balance Mismatch for Active Points",
					ActivePointsBalanceBefore, ActivePointsBalanceAfter);
			AssertJUnit.assertEquals("Balance Mismatch for In Active Points",
					InactivePointsBalanceBefore, InactivePointsBalanceAfter);

		}

		// If Debit Active points is < Active Points Balance, and In Active
		// Debit Points is > than In Active Points Balance
		else if ((DebitActivePoints < ActivePointsBalanceBefore)
				&& (DebitInactivePoints > InactivePointsBalanceBefore)) {
			System.out
					.println(" Active Points will be debited. Inactive points will become zero");

			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY, APINAME.DEBITLOYALITYPOINTS,
					init.Configurations, new String[] { login,
							activeCreditPoints, inActiveCreditPoints,
							activeDebitPoints, inActiveDebitPoints,
							description, itemType, itemId, businessProcess },
					PayloadType.JSON, PayloadType.JSON);

			RequestGenerator req = new RequestGenerator(service);

			ActivePointsBalanceAfter = getActiveLoyaltyPointBalance(login);
			System.out.println("Active Loyalty Points balance after debit :"
					+ ActivePointsBalanceAfter);

			FinalActivePointsBalance = ActivePointsBalanceBefore
					- DebitActivePoints;
			System.out.println("Final Active Poinys Balance:"
					+ FinalActivePointsBalance);

			AssertJUnit.assertEquals(
					"Balance Mismatch for Active Debit Points",
					FinalActivePointsBalance, ActivePointsBalanceAfter);

			InactivePointsBalanceAfter = getInActiveLoyaltyPointBalance(login);
			System.out.println("InActive Loyalty Points balance after debit :"
					+ InactivePointsBalanceAfter);

			AssertJUnit.assertEquals("Balance Mismatch for In Active Points",
					0.0, InactivePointsBalanceAfter);

		}

		else if ((DebitActivePoints < ActivePointsBalanceBefore)
				&& (DebitInactivePoints < InactivePointsBalanceBefore)) {
			System.out
					.println("Both Active and In Active Points will be debited");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY, APINAME.DEBITLOYALITYPOINTS,
					init.Configurations, new String[] { login,
							activeCreditPoints, inActiveCreditPoints,
							activeDebitPoints, inActiveDebitPoints,
							description, itemType, itemId, businessProcess },
					PayloadType.JSON, PayloadType.JSON);

			RequestGenerator req = new RequestGenerator(service);

			ActivePointsBalanceAfter = getActiveLoyaltyPointBalance(login);
			System.out.println("Active Loyalty Points Balance after Debit :"
					+ ActivePointsBalanceAfter);

			InactivePointsBalanceAfter = getInActiveLoyaltyPointBalance(login);
			System.out.println("InActive Loyalty Points Balance after Debit :"
					+ InactivePointsBalanceAfter);

			FinalActivePointsBalance = ActivePointsBalanceBefore
					- Double.parseDouble(activeDebitPoints);
			System.out
					.println("Final Active Loyalty Points Balance after Debit :"
							+ FinalActivePointsBalance);

			FinalInactivePointsBalance = InactivePointsBalanceBefore
					- Double.parseDouble(inActiveDebitPoints);
			System.out
					.println("Final InActive Loyalty Points Balance after Debit :"
							+ FinalInactivePointsBalance);

			AssertJUnit.assertEquals("Active Debit Points Balance Mismatch",
					FinalActivePointsBalance, ActivePointsBalanceAfter);

			AssertJUnit.assertEquals("InActive Debit Points Balance Mismatch",
					FinalInactivePointsBalance, InactivePointsBalanceAfter);

		}
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyaltyPointsDataProvider")
	public void LoyaltyPoints_debitLoyalityPoints_verifyLogin(String login,
			String activeCreditPoints, String inActiveCreditPoints,
			String activeDebitPoints, String inActiveDebitPoints,
			String description, String itemType, String itemId,
			String businessProcess) {

		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYPOINTS, init.Configurations, new String[] {
						login, activeCreditPoints, inActiveCreditPoints,
						inActiveCreditPoints, inActiveDebitPoints, description,
						itemType, itemId, businessProcess }, PayloadType.JSON,
				PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		// AssertJUnit.assertEquals(req.response.getStatus(), 200);
		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());

		System.out.println("Login ID in request is :" + login);

		String loginID = req.respvalidate
				.NodeValue("transactiontEntry.login", true)
				.replaceAll("\"", "").trim();
		System.out.println("Login ID in transactiontEntry Node's response is: "
				+ loginID);
		log.info(loginID);

		String loginID1 = req.respvalidate
				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
				.trim();
		System.out.println("Login ID in userAccountEntry Node's response is: "
				+ loginID1);
		log.info(loginID1);

		AssertJUnit.assertEquals(
				"Login ID in transactiontEntry Node does not match", login,
				loginID);
		AssertJUnit.assertEquals(
				"Login ID in userAccountEntry Node does not match", login,
				loginID1);
		MyntAssert.setExpectedAndActualForMyntListen(true);

	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression","Fox7Sanity"  }, dataProvider = "debitLoyaltyPointsDataProvider")
	public void LoyaltyPoints_debitLoyalityPoints_verifyTransactionEntry(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {

		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYPOINTS, init.Configurations, new String[] {
						login, activeCreditPoints, inActiveCreditPoints,
						inActiveCreditPoints, inActiveDebitPoints, description,
						itemType, itemId, businessProcess }, PayloadType.JSON,
				PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);

		MyntAssert.assertEquals("Debit Loyality Points is not working", 200,
				req.response.getStatus());

		System.out.println("Description in request is :" + description);
		String description_response = req.respvalidate
				.NodeValue("transactiontEntry.description", true)
				.replaceAll("\"", "").trim();
		System.out
				.println("Description in transactiontEntry Node's response is: "
						+ description_response);
		log.info(description_response);

		System.out.println("Item Type in request is :" + itemType);
		String itemType_response = req.respvalidate
				.NodeValue("transactiontEntry.itemType", true)
				.replaceAll("\"", "").trim();
		System.out
				.println("item Type in transactiontEntry Node's response is: "
						+ itemType_response);
		log.info(itemType_response);

		System.out.println("Item ID in request is :" + itemId);
		String itemId_response = req.respvalidate
				.NodeValue("transactiontEntry.itemId", true)
				.replaceAll("\"", "").trim();
		System.out.println("itemID in transactiontEntry Node's response is: "
				+ itemId_response);
		log.info(itemId_response);

		System.out
				.println("Business Process in request is :" + businessProcess);
		String businessProcess_response = req.respvalidate
				.NodeValue("transactiontEntry.businessProcess", true)
				.replaceAll("\"", "").trim();
		System.out
				.println("Business Process in transactiontEntry Node's response is: "
						+ businessProcess_response);
		log.info(businessProcess_response);

		AssertJUnit
				.assertEquals(
						"Description in transactiontEntry Node's response does not match",
						description, description_response);
		AssertJUnit
				.assertEquals(
						"Item Type in transactiontEntry Node's response does not match",
						itemType, itemType_response);
		AssertJUnit.assertEquals(
				"Item ID in transactiontEntry Node's response does not match",
				itemId, itemId_response);
		AssertJUnit
				.assertEquals(
						"Business Process in transactiontEntry Node's response does not match",
						businessProcess, businessProcess_response);
		MyntAssert.setExpectedAndActualForMyntListen(true);

	}

	@Test(groups = { "Sanity", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "transactionHistoryInactivePointsDataProvider")
	public void LoyaltyPoints_transactionHistoryInactivePoints_verifySuccessResponse(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYINACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		MyntAssert.assertEquals(
				"transactionHistoryInactivePoints API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "ProdSanity","MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "transactionHistoryInactivePointsDataProvider")
	public void LoyaltyPoints_transactionHistoryInactivePoints_verifyStatusMessage(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYINACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out
				.println("Status Message for Transaction History Inactive Point API is :"
						+ statusMessage);

		AssertJUnit
				.assertTrue(
						"Status Message for Transaction History Inactive Point API does not match",
						statusMessage.contains("transaction history"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "ProdSanity","MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "transactionHistoryInactivePointsDataProvider")
	public void LoyaltyPoints_transactionHistoryInactivePoints_verifyStatusType(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYINACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusType);
		System.out
				.println("Status Type for Transaction History Inactive Point API is :"
						+ statusType);

		AssertJUnit
				.assertTrue(
						"Status Type for Transaction History Inactive Point API does not match",
						statusType.contains("SUCCESS"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "ProdSanity","MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "transactionHistoryInactivePointsDataProvider")
	public void LoyaltyPoints_transactionHistoryInactivePoints_verifyLogin(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYINACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		System.out.println("Login ID sent in request is :" + login);
		String loginID = req.respvalidate
				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
				.trim();
		log.info(loginID);
		System.out
				.println("Login ID captured from Transaction History Inactive Point API's response is :"
						+ loginID);

		AssertJUnit.assertEquals("Login ID does not match", login, loginID);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "transactionHistoryActivePointsDataProvider")
	public void LoyaltyPoints_transactionHistoryActivePoints_verifySuccessResponse(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		MyntAssert.assertEquals(
				"transactionHistoryActivePoints API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "ProdSanity","MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "transactionHistoryActivePointsDataProvider")
	public void LoyaltyPoints_transactionHistoryActivePoints_verifyStatusMessage(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out
				.println("Status Message for Transaction History Active Point API is :"
						+ statusMessage);

		AssertJUnit
				.assertTrue(
						"Status Message for Transaction History Active Point API does not match",
						statusMessage.contains("transaction history"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "ProdSanity","MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "transactionHistoryActivePointsDataProvider")
	public void LoyaltyPoints_transactionHistoryActivePoints_verifyStatusType(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusType);
		System.out
				.println("Status Type for Transaction History Active Point API is :"
						+ statusType);

		AssertJUnit
				.assertTrue(
						"Status Type for Transaction History Active Point API does not match",
						statusType.contains("SUCCESS"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "ProdSanity","MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "transactionHistoryActivePointsDataProvider")
	public void LoyaltyPoints_transactionHistoryActivePoints_verifyLogin(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		System.out.println("Login ID sent in request is :" + login);
		String loginID = req.respvalidate
				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
				.trim();
		log.info(loginID);
		System.out
				.println("Login ID captured from Transaction History Active Point API's response is :"
						+ loginID);

		AssertJUnit.assertEquals("Login ID does not match", login, loginID);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Smoke", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "getAccountInfoDataProvider")
	public void LoyaltyPoints_getAccountInfo_verifySuccessResponse(String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCOUNTINFOLOYALITY, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		MyntAssert.assertEquals(
				"Response code of getAccountInfo API does not match", 200,
				req.response.getStatus());
		MyntAssert.setJsonResponse(req.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "getAccountInfoDataProvider")
	public void LoyaltyPoints_getAccountInfo_verifyStatusMessage(String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCOUNTINFOLOYALITY, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println("Status Message for Get Account Info API is :"
				+ statusMessage);

		AssertJUnit.assertTrue(
				"Status Message for Get Account Info API does not match",
				statusMessage.contains("account info for user : " + login));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "ProdSanity","MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "getAccountInfoDataProvider")
	public void LoyaltyPoints_getAccountInfo_verifyStatusType(String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCOUNTINFOLOYALITY, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusType);
		System.out.println("Status Type for Get Account Info API is :"
				+ statusType);

		AssertJUnit.assertTrue(
				"Status Type for Get Account Info API does not match",
				statusType.contains("SUCCESS"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "ProdSanity","MiniRegression", "Regression",
			"ExhaustiveRegression" ,"Fox7Sanity" }, dataProvider = "getAccountInfoDataProvider")
	public void LoyaltyPoints_getAccountInfo_verifyLogin(String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCOUNTINFOLOYALITY, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		System.out.println("Login ID sent in request is :" + login);
		String loginID = req.respvalidate
				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
				.trim();
		log.info(loginID);
		System.out
				.println("Login ID captured from Get Account Info API's response is :"
						+ loginID);

		AssertJUnit.assertEquals("Login ID does not match", login, loginID);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider = "getAccountInfoDataProvider_negativeCases")
	public void LoyaltyPoints_getAccountInfoNegativeCases(String loginID) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCOUNTINFOLOYALITY, init.Configurations,
				PayloadType.JSON, new String[] { loginID }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals(req.response.getStatus(), 200);
		MyntAssert.assertEquals("Response code", 200, req.response.getStatus());
		MyntAssert.setJsonResponse(req.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	// This test case will add points in inactive credit points account
	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsOrderConfirmationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityOrderConfirmation_verifySuccessResponse(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYORDERCONFIRMATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		MyntAssert
				.assertEquals(
						"Response code for creditLoyalityOrderConfirmation API does not match",
						200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsOrderConfirmationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityOrderConfirmation_verifyStatusMessage(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYORDERCONFIRMATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.assertEquals(
				"creditLoyalityOrderConfirmation API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("credited successfuly"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsOrderConfirmationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityOrderConfirmation_verifyStatusType(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYORDERCONFIRMATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusType);
		System.out
				.println("Status Type for creditLoyalityOrderConfirmation API is :"
						+ statusType);

		AssertJUnit
				.assertTrue(
						"Status Type for creditLoyalityOrderConfirmation API does not match",
						statusType.contains("SUCCESS"));
		// MyntAssert.setJsonResponse(req.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsOrderConfirmationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityOrderConfirmation_verifyLogin(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYORDERCONFIRMATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		System.out.println("Login ID in request is :" + login);

		String loginID = req.respvalidate
				.NodeValue("transactiontEntry.login", true)
				.replaceAll("\"", "").trim();
		System.out.println("Login ID in transactiontEntry Node's response is: "
				+ loginID);
		log.info(loginID);

		String loginID1 = req.respvalidate
				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
				.trim();
		System.out.println("Login ID in userAccountEntry Node's response is: "
				+ loginID1);
		log.info(loginID1);

		AssertJUnit.assertEquals(
				"Login ID in transactiontEntry Node does not match", login,
				loginID);
		AssertJUnit.assertEquals(
				"Login ID in userAccountEntry Node does not match", login,
				loginID1);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	// This test case will add points to inactive points balance account
	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsOrderConfirmationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityOrderConfirmation_verifyBalancePositiveCases(
			String login, String points, String orderID, String description) {
		Double balanceBefore, balanceAfter, finalBalance;

		balanceBefore = getInActiveLoyaltyPointBalance(login);
		System.out.println("InActive Loyalty Points balance before credit:"
				+ balanceBefore);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYORDERCONFIRMATION, init.Configurations,
				new String[] { login, points, orderID, description, },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);

		balanceAfter = getInActiveLoyaltyPointBalance(login);
		System.out.println("InActive Loyalty Points balance after credit :"
				+ balanceAfter);

		finalBalance = balanceBefore + Double.parseDouble(points);
		System.out.println("Final Balance:" + finalBalance);

		AssertJUnit
				.assertEquals("Balance Mismatch", finalBalance, balanceAfter);

		MyntAssert.assertEquals(
				"creditLoyalityOrderConfirmation API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsOrderConfirmationNegativeCasesDataProvider", enabled = false)
	public void LoyaltyPoints_creditLoyalityOrderConfirmation_verifyBalanceNegativeCases(
			String login, String points, String orderID, String description) {
		Double OrderId, Debitpoints;
		OrderId = Double.parseDouble(orderID);
		Debitpoints = Double.parseDouble(points);

		if ((OrderId < 0) || ((OrderId < 0 && Debitpoints < 0))) {
			System.out
					.println("Either only Order ID is lesser than zero or both OrderId and DebitPoints are lesser than zero");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.ACTIVATELOYALITYPOINTSFORORDER,
					init.Configurations, new String[] { login, points, orderID,
							description, }, PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			String statusCode = req.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();
			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			String statusType = req.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();

			System.out.println("StatusCode : " + statusCode);
			System.out.println("statusMessage : " + statusMessage);
			System.out.println("statusType : " + statusType);

			AssertJUnit.assertTrue("Status code doesn't match",
					statusCode.contains("10008"));
			AssertJUnit
					.assertTrue(
							"Status Message doesn't match",
							statusMessage
									.contains("Points for item cancellation could not be debited"));
			AssertJUnit.assertTrue("Status Type doesn't match",
					statusType.contains("ERROR"));

		}

		else {
			System.out.println("Only Debit Points is lesser than zero");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.ACTIVATELOYALITYPOINTSFORORDER,
					init.Configurations, new String[] { login, points, orderID,
							description, }, PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			String statusCode = req.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();
			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			String statusType = req.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();

			System.out.println("StatusCode : " + statusCode);
			System.out.println("statusMessage : " + statusMessage);
			System.out.println("statusType : " + statusType);

			AssertJUnit.assertTrue("Status code doesn't match",
					statusCode.contains("1"));
			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("activated points successfuly"));
			AssertJUnit.assertTrue("Status Type doesn't match",
					statusType.contains("SUCCESS"));

		}
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsOrderConfirmationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityOrderConfirmation_verifyBusinessProcess(
			String login, String points, String orderID, String description) {

		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYORDERCONFIRMATION, init.Configurations,
				new String[] { login, points, orderID, description, },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);

		String businessProcess = req.respvalidate
				.NodeValue("transactiontEntry.businessProcess", true)
				.replaceAll("\"", "").trim();

		System.out.println("Business Process captured in response is : "
				+ businessProcess);

		AssertJUnit.assertTrue("Business Process does not match",
				businessProcess.contains("POINTS_AWARDED"));

		MyntAssert.assertEquals(
				"creditLoyalityOrderConfirmation API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}



	// This test case will add points in active credit points account
	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityItemCancellationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityItemCancellation_verifySuccessResponse(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYITEMCANCELLATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		MyntAssert
				.assertEquals(
						"Response code for creditLoyalityItemCancellation API does not match",
						200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityItemCancellationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityItemCancellation_verifyStatusMessage(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYITEMCANCELLATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.assertEquals(
				"creditLoyalityItemCancellation API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("credited successfuly"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityItemCancellationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityItemCancellation_verifyStatusType(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYITEMCANCELLATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusType);
		System.out
				.println("Status Type for creditLoyalityItemCancellation API is :"
						+ statusType);

		AssertJUnit
				.assertTrue(
						"Status Type for creditLoyalityItemCancellation API does not match",
						statusType.contains("SUCCESS"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityItemCancellationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityItemCancellation_verifyLogin(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYITEMCANCELLATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		System.out.println("Login ID in request is :" + login);

		String loginID = req.respvalidate
				.NodeValue("transactiontEntry.login", true)
				.replaceAll("\"", "").trim();
		System.out.println("Login ID in transactiontEntry Node's response is: "
				+ loginID);
		log.info(loginID);

		String loginID1 = req.respvalidate
				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
				.trim();
		System.out.println("Login ID in userAccountEntry Node's response is: "
				+ loginID1);
		log.info(loginID1);

		AssertJUnit.assertEquals(
				"Login ID in transactiontEntry Node does not match", login,
				loginID);
		AssertJUnit.assertEquals(
				"Login ID in userAccountEntry Node does not match", login,
				loginID1);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	// This test case will add points in active credit points account
	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityItemCancellationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityItemCancellationPositiveCases_verifyBalance(
			String login, String points, String orderID, String description) {
		Double balanceBefore, balanceAfter, finalBalance;

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before credit:"
				+ balanceBefore);

		System.out.println("Points to be credited :" + points);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYITEMCANCELLATION, init.Configurations,
				new String[] { login, points, orderID, description, },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);

		balanceAfter = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance after credit :"
				+ balanceAfter);

		finalBalance = balanceBefore + Double.parseDouble(points);
		System.out.println("Final Balance:" + finalBalance);

		AssertJUnit
				.assertEquals("Balance Mismatch", finalBalance, balanceAfter);

		MyntAssert.assertEquals(
				"creditLoyalityItemCancellation API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityItemCancellationNegativeCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityItemCancellationNegativeCases_verifyBalance(
			String login, String points, String orderID, String description) {
		Double OrderId, Debitpoints;
		OrderId = Double.parseDouble(orderID);
		Debitpoints = Double.parseDouble(points);

		if ((OrderId < 0) || ((OrderId < 0 && Debitpoints < 0))) {
			System.out
					.println("Either only Order ID is lesser than zero or both OrderId and DebitPoints are lesser than zero");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.ACTIVATELOYALITYPOINTSFORORDER,
					init.Configurations, new String[] { login, points, orderID,
							description, }, PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			String statusCode = req.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();
			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			String statusType = req.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();

			System.out.println("StatusCode : " + statusCode);
			System.out.println("statusMessage : " + statusMessage);
			System.out.println("statusType : " + statusType);

			AssertJUnit.assertTrue("Status code doesn't match",
					statusCode.contains("10008"));
			AssertJUnit
					.assertTrue(
							"Status Message doesn't match",
							statusMessage
									.contains("Points for item cancellation could not be debited"));
			AssertJUnit.assertTrue("Status Type doesn't match",
					statusType.contains("ERROR"));
		}
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityItemCancellationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityItemCancellation_verifyBusinessProcess(
			String login, String points, String orderID, String description) {

		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYITEMCANCELLATION, init.Configurations,
				new String[] { login, points, orderID, description, },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);

		String businessProcess = req.respvalidate
				.NodeValue("transactiontEntry.businessProcess", true)
				.replaceAll("\"", "").trim();

		System.out.println("Business Process captured in response is : "
				+ businessProcess);

		AssertJUnit.assertTrue("Business Process does not match",
				businessProcess.contains("ITEM_CANCELLATION"));

		MyntAssert.assertEquals(
				"creditLoyalityOrderConfirmation API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	// This test case will deduct points from active points balance against a
	// orderid
	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyalityOrderUsageDataProvider")
	public void LoyaltyPoints_debitLoyalityOrderusage_verifySuccessResponse(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		MyntAssert.assertEquals(
				"Response code for debitLoyalityOrderusage API does not match",
				200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyalityOrderUsageDataProvider")
	public void LoyaltyPoints_debitLoyalityOrderusage_verifyStatusMessage(
			String login, String points, String orderID, String description) {

		Double balanceBefore, balanceAfter, finalBalance, DebitPoints;

		DebitPoints = Double.parseDouble(points);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit:"
				+ balanceBefore);
		System.out.println("Points to be Debited:" + DebitPoints);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Amount to be debited is greater than balance available");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
					new String[] { login, points, orderID, description, },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			System.out.println("statusMessage : " + statusMessage);

			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("Loyalty Points Debit Failure"));
		}

		else {
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
					new String[] { login, points, orderID, description },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			System.out.println("statusMessage :" + statusMessage);
			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("debited successfuly"));
		}
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyalityOrderUsageDataProvider")
	public void LoyaltyPoints_debitLoyalityOrderusage_verifyStatusType(
			String login, String points, String orderID, String description) {

		Double balanceBefore, balanceAfter, finalBalance, DebitPoints;

		DebitPoints = Double.parseDouble(points);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit:"
				+ balanceBefore);
		System.out.println("Points to be Debited:" + DebitPoints);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Amount to be debited is greater than balance available");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
					new String[] { login, points, orderID, description, },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			String statusType = req.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();
			System.out.println("statusType : " + statusType);

			AssertJUnit.assertTrue("Status Type doesn't match",
					statusType.contains("ERROR"));
		}

		else {
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
					new String[] { login, points, orderID, description },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			log.info(service.URL);

			String statusType = req.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();
			log.info(statusType);
			System.out
					.println("Status Type for debitLoyalityOrderUsage API is :"
							+ statusType);

			AssertJUnit
					.assertTrue(
							"Status Type for debitLoyalityOrderUsage API does not match",
							statusType.contains("SUCCESS"));
			MyntAssert.setExpectedAndActualForMyntListen(true);
		}
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyalityOrderUsageDataProvider")
	public void LoyaltyPoints_debitLoyalityOrderusage_verifyStatusCode(
			String login, String points, String orderID, String description) {

		Double balanceBefore, balanceAfter, finalBalance, DebitPoints;

		DebitPoints = Double.parseDouble(points);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit:"
				+ balanceBefore);
		System.out.println("Points to be Debited:" + DebitPoints);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Amount to be debited is greater than balance available");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
					new String[] { login, points, orderID, description, },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			String statusCode = req.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();
			System.out.println("statusCode : " + statusCode);

			AssertJUnit.assertTrue("Status Code doesn't match",
					statusCode.contains("10004"));
		}

		else {
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
					new String[] { login, points, orderID, description },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			log.info(service.URL);

			String statusCode = req.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();
			log.info(statusCode);
			System.out
					.println("Status Code for debitLoyalityOrderUsage API is :"
							+ statusCode);

			AssertJUnit
					.assertTrue(
							"Status Code for debitLoyalityOrderUsage API does not match",
							statusCode.contains("1"));
			MyntAssert.setExpectedAndActualForMyntListen(true);
		}
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyalityOrderUsageDataProvider")
	public void LoyaltyPoints_debitLoyalityOrderusage_verifyLogin(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		System.out.println("Login ID in request is :" + login);

		String loginID = req.respvalidate
				.NodeValue("transactiontEntry.login", true)
				.replaceAll("\"", "").trim();
		System.out.println("Login ID in transactiontEntry Node's response is: "
				+ loginID);
		log.info(loginID);

		String loginID1 = req.respvalidate
				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
				.trim();
		System.out.println("Login ID in userAccountEntry Node's response is: "
				+ loginID1);
		log.info(loginID1);

		AssertJUnit.assertEquals(
				"Login ID in transactiontEntry Node does not match", login,
				loginID);
		AssertJUnit.assertEquals(
				"Login ID in userAccountEntry Node does not match", login,
				loginID1);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	// This test case will debit points from active points balance account
	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyalityOrderUsageDataProvider")
	public void LoyaltyPoints_debitLoyalityOrderusage_verifyBalance(
			String login, String points, String orderID, String description) {
		Double balanceBefore, balanceAfter, finalBalance, DebitPoints;

		DebitPoints = Double.parseDouble(points);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit:"
				+ balanceBefore);
		System.out.println("Points to be Debited:" + DebitPoints);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Amount to be debited is greater than balance available. Balance will remain as such");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
					new String[] { login, points, orderID, description, },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			System.out
					.println("Active points balance after debit captured from it's API response :"
							+ req.respvalidate.NodeValue(
									"userAccountEntry.activePointsBalance",
									true));
			System.out
					.println("Active points balance after debit captured from API Validation's response :");
			balanceAfter = getActiveLoyaltyPointBalance(login);

			AssertJUnit.assertEquals("Balance Mismatch", balanceBefore,
					balanceAfter);

		}

		else {
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
					new String[] { login, points, orderID, description, },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			balanceAfter = getActiveLoyaltyPointBalance(login);
			System.out.println("Active Loyalty Points balance after debit :"
					+ balanceAfter);

			finalBalance = balanceBefore - Double.parseDouble(points);
			System.out.println("Final Balance:" + finalBalance);

			AssertJUnit.assertEquals("Balance Mismatch", finalBalance,
					balanceAfter);
		}
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyalityOrderUsageDataProvider")
	public void LoyaltyPoints_debitLoyalityOrderusage_verifyBusinessProcess(
			String login, String points, String orderID, String description) {

		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
				new String[] { login, points, orderID, description, },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);

		String businessProcess = req.respvalidate
				.NodeValue("transactiontEntry.businessProcess", true)
				.replaceAll("\"", "").trim();

		System.out.println("Business Process captured in response is : "
				+ businessProcess);

		AssertJUnit.assertTrue("Business Process does not match",
				businessProcess.contains("POINTS_REDEEMED"));

		MyntAssert.assertEquals("debitLoyalityOrderusage API is not working",
				200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}



	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyalityItemCancellationDataProvider_positiveCases")
	public void LoyaltyPoints_debitLoyalityItemCancellation_verifySuccessResponse(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYITEMCANCELLATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		MyntAssert
				.assertEquals(
						"Response code for debitLoyalityItemCancellation API does not match",
						200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

//	@Test(groups = { "Sanity", "MiniRegression", "Regression",
//			"ExhaustiveRegression" }, dataProvider = "debitLoyalityItemCancellationDataProvider_positiveCases")
//	public void LoyaltyPoints_debitLoyalityItemCancellation_verifyStatusMessage(
//			String login, String points, String orderID, String description) {
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
//				APINAME.DEBITLOYALITYITEMCANCELLATION, init.Configurations,
//				new String[] { login, points, orderID, description },
//				PayloadType.JSON, PayloadType.JSON);
//		RequestGenerator req = new RequestGenerator(service);
//		log.info(service.URL);
//		System.out.println(service.URL);
//		MyntAssert.assertEquals(
//				"debitLoyalityItemCancellation API is not working", 200,
//				req.response.getStatus());
//
//		String statusMessage = req.respvalidate
//				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
//				.trim();
//		log.info(statusMessage);
//		System.out.println(statusMessage);
//		AssertJUnit.assertTrue("Status Message doesn't match",
//				statusMessage.contains("debited successfuly"));
//		MyntAssert.setExpectedAndActualForMyntListen(true);
//	}

//	@Test(groups = { "Sanity", "MiniRegression", "Regression",
//			"ExhaustiveRegression" }, dataProvider = "debitLoyalityItemCancellationDataProvider_positiveCases")
//	public void LoyaltyPoints_debitLoyalityItemCancellation_verifyStatusType(
//			String login, String points, String orderID, String description) {
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
//				APINAME.DEBITLOYALITYITEMCANCELLATION, init.Configurations,
//				new String[] { login, points, orderID, description },
//				PayloadType.JSON, PayloadType.JSON);
//		RequestGenerator req = new RequestGenerator(service);
//		log.info(service.URL);
//
//		String statusType = req.respvalidate
//				.NodeValue("status.statusType", true).replaceAll("\"", "")
//				.trim();
//		log.info(statusType);
//		System.out
//				.println("Status Type for debitLoyalityItemCancellation API is :"
//						+ statusType);
//
//		AssertJUnit.assertTrue(
//				"Status Type for debitLoyalityOrderUsage API does not match",
//				statusType.contains("SUCCESS"));
//		MyntAssert.setExpectedAndActualForMyntListen(true);
//	}

//	@Test(groups = { "Sanity", "MiniRegression", "Regression",
//			"ExhaustiveRegression" }, dataProvider = "debitLoyalityItemCancellationDataProvider_positiveCases")
//	public void LoyaltyPoints_debitLoyalityItemCancellation_verifyLogin(
//			String login, String points, String orderID, String description) {
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
//				APINAME.DEBITLOYALITYITEMCANCELLATION, init.Configurations,
//				new String[] { login, points, orderID, description },
//				PayloadType.JSON, PayloadType.JSON);
//		RequestGenerator req = new RequestGenerator(service);
//		log.info(service.URL);
//
//		System.out.println("Login ID in request is :" + login);
//
//		String loginID = req.respvalidate
//				.NodeValue("transactiontEntry.login", true)
//				.replaceAll("\"", "").trim();
//		System.out.println("Login ID in transactiontEntry Node's response is: "
//				+ loginID);
//		log.info(loginID);
//
//		String loginID1 = req.respvalidate
//				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
//				.trim();
//		System.out.println("Login ID in userAccountEntry Node's response is: "
//				+ loginID1);
//		log.info(loginID1);
//
//		AssertJUnit.assertEquals(
//				"Login ID in transactiontEntry Node does not match", login,
//				loginID);
//		AssertJUnit.assertEquals(
//				"Login ID in userAccountEntry Node does not match", login,
//				loginID1);
//		MyntAssert.setExpectedAndActualForMyntListen(true);
//	}

	// This test case will debit the points from inactive points balance. Points
	// debited would depend upon the total points which got credited using
	// credit for order confirmation API. Points will be credited in inactive
	// balance.
//	@Test(groups = { "Sanity", "MiniRegression", "Regression",
//			"ExhaustiveRegression" }, dataProvider = "debitLoyalityItemCancellationDataProvider_positiveCases")
//	public void LoyaltyPoints_debitLoyalityItemCancellation_verifyBalance_positiveCases(
//			String login, String points, String orderID, String description) {
//
//		Double balanceBefore, balanceAfter, finalBalance;
//		// First Credit points for item cancellation
//
//		balanceBefore = getInActiveLoyaltyPointBalance(login);
//		System.out.println("Total Inactive points available before credit:"
//				+ balanceBefore);
//
//		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_LOYALITY,
//				APINAME.CREDITLOYALITYORDERCONFIRMATION, init.Configurations,
//				new String[] { login, points, orderID, description, },
//				PayloadType.JSON, PayloadType.JSON);
//		RequestGenerator req1 = new RequestGenerator(service1);
//
//		System.out
//				.println("Total inactive points credited which is available to debit using debit loyalty item cancellation are: "
//						+ points);
//		System.out.println("Final In Active Points Balance:"
//				+ req1.respvalidate.NodeValue(
//						"userAccountEntry.inActivePointsBalance", true));
//		balanceBefore = getInActiveLoyaltyPointBalance(login);
//
//		// Now Debit the credited points for item cancellation
//
//		MyntraService service2 = Myntra.getService(ServiceType.PORTAL_LOYALITY,
//				APINAME.DEBITLOYALITYITEMCANCELLATION, init.Configurations,
//				new String[] { login, points, orderID, description, },
//				PayloadType.JSON, PayloadType.JSON);
//		RequestGenerator req2 = new RequestGenerator(service2);
//
//		// Inactive points balance from its API response
//		System.out
//				.println("Final InActive Points Balance after Debit captured from API response :"
//						+ req2.respvalidate.NodeValue(
//								"userAccountEntry.inActivePointsBalance", true));
//
//		// Inactive points balance from other API response
//		balanceAfter = getInActiveLoyaltyPointBalance(login);
//		System.out
//				.println("Final InActive Loyalty Points balance using API validation:"
//						+ balanceAfter);
//
//		// Inactive points balance using manual calculation
//		finalBalance = balanceBefore - Double.parseDouble(points);
//		System.out
//				.println("Final In Active Points Balance expected using Manual calculation:"
//						+ finalBalance);
//
//		AssertJUnit
//				.assertEquals("Balance Mismatch", finalBalance, balanceAfter);
//
//	}

//	@Test(groups = { "Sanity", "MiniRegression", "Regression",
//			"ExhaustiveRegression" }, dataProvider = "debitLoyalityItemCancellationDataProvider_negativeCases")
//	public void LoyaltyPoints_debitLoyalityItemCancellation_verifyBalance_negativeCases(
//			String login, String points, String orderID, String description) {
//
//		Double DebitPoints, OrderID;
//
//		DebitPoints = Double.parseDouble(points);
//		OrderID = Double.parseDouble(orderID);
//
//		System.out.println("Points to be debited: " + DebitPoints);
//
//		// When only points to be debited is negative
//		if ((DebitPoints < 0) && (OrderID > 0)) {
//			System.out.println("Points to be Debited is lesser than zero");
//			MyntraService service = Myntra.getService(
//					ServiceType.PORTAL_LOYALITY,
//					APINAME.DEBITLOYALITYITEMCANCELLATION, init.Configurations,
//					new String[] { login, points, orderID, description, },
//					PayloadType.JSON, PayloadType.JSON);
//			RequestGenerator req = new RequestGenerator(service);
//
//			String statusCode = req.respvalidate
//					.NodeValue("status.statusCode", true).replaceAll("\"", "")
//					.trim();
//			String statusMessage = req.respvalidate
//					.NodeValue("status.statusMessage", true)
//					.replaceAll("\"", "").trim();
//			String statusType = req.respvalidate
//					.NodeValue("status.statusType", true).replaceAll("\"", "")
//					.trim();
//
//			System.out.println("StatusCode : " + statusCode);
//			System.out.println("statusMessage : " + statusMessage);
//			System.out.println("statusType : " + statusType);
//
//			AssertJUnit.assertTrue("Status code doesn't match",
//					statusCode.contains("-1"));
//			AssertJUnit.assertTrue("Status Message doesn't match",
//					statusMessage.contains("incomplete transaction entry"));
//			AssertJUnit.assertTrue("Status Type doesn't match",
//					statusType.contains("ERROR"));
//		}
//
//		// If orderId is lesser than zero irrespective of whether points to be
//		// debited is lesser or greater than zero
//		else if ((OrderID < 0)) {
//
//			System.out.println("Order ID is lesser than zero");
//			MyntraService service = Myntra.getService(
//					ServiceType.PORTAL_LOYALITY,
//					APINAME.DEBITLOYALITYITEMCANCELLATION, init.Configurations,
//					new String[] { login, points, orderID, description, },
//					PayloadType.JSON, PayloadType.JSON);
//			RequestGenerator req = new RequestGenerator(service);
//
//			String statusCode = req.respvalidate
//					.NodeValue("status.statusCode", true).replaceAll("\"", "")
//					.trim();
//			String statusMessage = req.respvalidate
//					.NodeValue("status.statusMessage", true)
//					.replaceAll("\"", "").trim();
//			String statusType = req.respvalidate
//					.NodeValue("status.statusType", true).replaceAll("\"", "")
//					.trim();
//
//			System.out.println("StatusCode : " + statusCode);
//			System.out.println("statusMessage : " + statusMessage);
//			System.out.println("statusType : " + statusType);
//
//			AssertJUnit.assertTrue("Status code doesn't match",
//					statusCode.contains("10008"));
//			AssertJUnit
//					.assertTrue(
//							"Status Message doesn't match",
//							statusMessage
//									.contains("Points for item cancellation could not be debited"));
//			AssertJUnit.assertTrue("Status Type doesn't match",
//					statusType.contains("ERROR"));
//
//		}
//	}

//	@Test(groups = { "Sanity", "MiniRegression", "Regression",
//			"ExhaustiveRegression" }, dataProvider = "debitLoyalityItemCancellationDataProvider_positiveCases")
//	public void LoyaltyPoints_debitLoyalityItemCancellation_verifyBusinessProcess(
//			String login, String points, String orderID, String description) {
//
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
//				APINAME.DEBITLOYALITYITEMCANCELLATION, init.Configurations,
//				new String[] { login, points, orderID, description, },
//				PayloadType.JSON, PayloadType.JSON);
//		RequestGenerator req = new RequestGenerator(service);
//
//		String businessProcess = req.respvalidate
//				.NodeValue("transactiontEntry.businessProcess", true)
//				.replaceAll("\"", "").trim();
//
//		System.out.println("Business Process captured in response is : "
//				+ businessProcess);
//
//		AssertJUnit.assertTrue("Business Process does not match",
//				businessProcess.contains("ITEM_CANCELLATION"));
//
//		MyntAssert.assertEquals("debitLoyalityOrderusage API is not working",
//				200, req.response.getStatus());
//		MyntAssert.setExpectedAndActualForMyntListen(true);
//	}

	

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "acivateLoyalityPointsForOrderDataProvider")
	public void LoyaltyPoints_activateLoyalityPointsForOrders_verifySuccessResponse(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACTIVATELOYALITYPOINTSFORORDER, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		MyntAssert
				.assertEquals(
						"Response code for activateLoyalityPointsForOrders API does not match",
						200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

//	@Test(groups = { "Sanity", "MiniRegression", "Regression",
//			"ExhaustiveRegression" }, dataProvider = "acivateLoyalityPointsForOrderDataProvider")
//	public void LoyaltyPoints_activateLoyalityPointsForOrders_verifyStatusMessage(
//			String login, String points, String orderID, String description) {
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
//				APINAME.ACTIVATELOYALITYPOINTSFORORDER, init.Configurations,
//				new String[] { login, points, orderID, description },
//				PayloadType.JSON, PayloadType.JSON);
//		RequestGenerator req = new RequestGenerator(service);
//		log.info(service.URL);
//		System.out.println(service.URL);
//		MyntAssert.assertEquals(
//				"activateLoyalityPointsForOrders API is not working", 200,
//				req.response.getStatus());
//
//		String statusMessage = req.respvalidate
//				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
//				.trim();
//		log.info(statusMessage);
//		System.out.println(statusMessage);
//		AssertJUnit.assertTrue("Status Message doesn't match",
//				statusMessage.contains("activated points successfuly"));
//		MyntAssert.setExpectedAndActualForMyntListen(true);
//	}

//	@Test(groups = { "Sanity", "MiniRegression", "Regression",
//			"ExhaustiveRegression" }, dataProvider = "acivateLoyalityPointsForOrderDataProvider")
//	public void LoyaltyPoints_activateLoyalityPointsForOrders_verifyStatusType(
//			String login, String points, String orderID, String description) {
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
//				APINAME.ACTIVATELOYALITYPOINTSFORORDER, init.Configurations,
//				new String[] { login, points, orderID, description },
//				PayloadType.JSON, PayloadType.JSON);
//		RequestGenerator req = new RequestGenerator(service);
//		log.info(service.URL);
//
//		String statusType = req.respvalidate
//				.NodeValue("status.statusType", true).replaceAll("\"", "")
//				.trim();
//		log.info(statusType);
//		System.out
//				.println("Status Type for debitLoyalityItemCancellation API is :"
//						+ statusType);
//
//		AssertJUnit
//				.assertTrue(
//						"Status Type for activateLoyalityPointsForOrders API does not match",
//						statusType.contains("SUCCESS"));
//		MyntAssert.setExpectedAndActualForMyntListen(true);
//	}

//	@Test(groups = { "Sanity", "MiniRegression", "Regression",
//			"ExhaustiveRegression" }, dataProvider = "acivateLoyalityPointsForOrderDataProvider")
//	public void LoyaltyPoints_activateLoyalityPointsForOrders_verifyLogin(
//			String login, String points, String orderID, String description) {
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
//				APINAME.ACTIVATELOYALITYPOINTSFORORDER, init.Configurations,
//				new String[] { login, points, orderID, description },
//				PayloadType.JSON, PayloadType.JSON);
//		RequestGenerator req = new RequestGenerator(service);
//		log.info(service.URL);
//
//		System.out.println("Login ID in request is :" + login);
//
//		String loginID1 = req.respvalidate
//				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
//				.trim();
//		System.out.println("Login ID in userAccountEntry Node's response is: "
//				+ loginID1);
//		log.info(loginID1);
//
//		AssertJUnit.assertEquals(
//				"Login ID in userAccountEntry Node does not match", login,
//				loginID1);
//		MyntAssert.setExpectedAndActualForMyntListen(true);
//	}

	// This test case will deduct the points from InActive points balance
	// account
	// and will credit the same in Active Points Balance Account
//	@Test(groups = { "Sanity", "MiniRegression", "Regression",
//			"ExhaustiveRegression" }, dataProvider = "acivateLoyalityPointsForOrderDataProvider")
//	public void LoyaltyPoints_activateLoyalityPointsForOrders_verifyBalancePositiveCases(
//			String login, String points, String orderID, String description) {
//		Double ActivePointsBalanceBefore, ActivePointsBalanceAfter, FinalActivePointsBalance, FinalActivePointsBalance1;
//		Double InactivePointsBalanceBefore, InactivePointsBalanceAfter, FinalInactivePointsBalance, FinalInactivePointsBalance1, ActivatePoints;
//
//		ActivatePoints = Double.parseDouble(points);
//		ActivePointsBalanceBefore = getActiveLoyaltyPointBalance(login);
//		System.out.println("Active Loyalty Points balance before credit:"
//				+ ActivePointsBalanceBefore);
//
//		InactivePointsBalanceBefore = getInActiveLoyaltyPointBalance(login);
//		System.out.println("InActive Loyalty Points balance before credit:"
//				+ InactivePointsBalanceBefore);
//
//		System.out
//				.println("Points to be credited which will be available for activation:"
//						+ points);
//
//		// This will credit points in Inactive points balance account
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
//				APINAME.CREDITLOYALITYORDERCONFIRMATION, init.Configurations,
//				new String[] { login, points, orderID, description, },
//				PayloadType.JSON, PayloadType.JSON);
//		RequestGenerator req = new RequestGenerator(service);
//
//		ActivePointsBalanceAfter = getActiveLoyaltyPointBalance(login);
//		System.out
//				.println("Unchanged Active Loyalty Points balance after credit:"
//						+ ActivePointsBalanceAfter);
//
//		InactivePointsBalanceAfter = getInActiveLoyaltyPointBalance(login);
//		System.out.println("InActive Loyalty Points balance after credit:"
//				+ InactivePointsBalanceAfter);
//
//		// This will Debit points from Inactive Points Balance and will credit
//		// it in Active Points Balance
//		System.out
//				.println("........Now Points will get activate from Inactive Points to Active Points.....");
//		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_LOYALITY,
//				APINAME.ACTIVATELOYALITYPOINTSFORORDER, init.Configurations,
//				new String[] { login, points, orderID, description },
//				PayloadType.JSON, PayloadType.JSON);
//		System.out.println("URL    _________ \n" + service1.URL);
//		RequestGenerator req1 = new RequestGenerator(service1);
//		System.out.println("ACTIVATENNNNNNN----->>>>>>\n"+req1.returnresponseasstring());
//		log.info(service.URL);
//
//		System.out
//				.println("Final Active Points Balance from it's API Response:"
//						+ req1.respvalidate.NodeValue(
//								"userAccountEntry.activePointsBalance", true));
//		FinalActivePointsBalance = getActiveLoyaltyPointBalance(login);
//		System.out
//				.println(" Final Active Points Balance from API Validation : "
//						+ FinalActivePointsBalance);
//
//		FinalActivePointsBalance1 = ActivePointsBalanceAfter + ActivatePoints;
//		System.out
//				.println("Final Active Points Balance from manual calculation:"
//						+ FinalActivePointsBalance1);
//
//		System.out
//				.println("Final InActive Points Balance from it's API Response:"
//						+ req1.respvalidate.NodeValue(
//								"userAccountEntry.inActivePointsBalance", true));
//		FinalInactivePointsBalance = getInActiveLoyaltyPointBalance(login);
//		System.out
//				.println(" Final InActive Points Balance from API Validation : "
//						+ FinalInactivePointsBalance);
//
//		FinalInactivePointsBalance1 = InactivePointsBalanceAfter
//				- ActivatePoints;
//		System.out
//				.println("Final InActive Points Balance from manual calculation:"
//						+ FinalInactivePointsBalance1);
//
//		AssertJUnit.assertEquals("Active Points Balance Mismatch",
//				FinalActivePointsBalance1, FinalActivePointsBalance);
//
//		AssertJUnit.assertEquals("InActive Points Balance Mismatch",
//				FinalInactivePointsBalance1, FinalInactivePointsBalance);
//
//	}

//	@Test(groups = { "Sanity", "MiniRegression", "Regression",
//			"ExhaustiveRegression" }, dataProvider = "acivateLoyalityPointsForOrderDataProvider_negativeCases")
//	public void LoyaltyPoints_activateLoyalityPointsForOrders_verifyBalanceNegativeCases(
//			String login, String points, String orderID, String description) {
//
//		Double OrderId, DebitPoints;
//		OrderId = Double.parseDouble(orderID);
//		DebitPoints = Double.parseDouble(points);
//
//		if ((OrderId < 0) || (OrderId < 0) && (DebitPoints < 0)) {
//
//			System.out
//					.println("Either Order ID is lesser than zero or both OrderID and DebitPoints are lesser than zero");
//			MyntraService service = Myntra.getService(
//					ServiceType.PORTAL_LOYALITY,
//					APINAME.ACTIVATELOYALITYPOINTSFORORDER,
//					init.Configurations, new String[] { login, points, orderID,
//							description, }, PayloadType.JSON, PayloadType.JSON);
//			RequestGenerator req = new RequestGenerator(service);
//
//			String statusCode = req.respvalidate
//					.NodeValue("status.statusCode", true).replaceAll("\"", "")
//					.trim();
//			String statusMessage = req.respvalidate
//					.NodeValue("status.statusMessage", true)
//					.replaceAll("\"", "").trim();
//			String statusType = req.respvalidate
//					.NodeValue("status.statusType", true).replaceAll("\"", "")
//					.trim();
//
//			System.out.println("StatusCode : " + statusCode);
//			System.out.println("statusMessage : " + statusMessage);
//			System.out.println("statusType : " + statusType);
//
//			AssertJUnit.assertTrue("Status code doesn't match",
//					statusCode.contains("10008"));
//			AssertJUnit
//					.assertTrue(
//							"Status Message doesn't match",
//							statusMessage
//									.contains("Points for item cancellation could not be debited"));
//			AssertJUnit.assertTrue("Status Type doesn't match",
//					statusType.contains("ERROR"));
//
//		}
//
//		else {
//			System.out.println("Debit Points is lesser than zero");
//			MyntraService service = Myntra.getService(
//					ServiceType.PORTAL_LOYALITY,
//					APINAME.ACTIVATELOYALITYPOINTSFORORDER,
//					init.Configurations, new String[] { login, points, orderID,
//							description }, PayloadType.JSON, PayloadType.JSON);
//			RequestGenerator req = new RequestGenerator(service);
//			log.info(service.URL);
//			String statusCode = req.respvalidate
//					.NodeValue("status.statusCode", true).replaceAll("\"", "")
//					.trim();
//			String statusMessage = req.respvalidate
//					.NodeValue("status.statusMessage", true)
//					.replaceAll("\"", "").trim();
//			String statusType = req.respvalidate
//					.NodeValue("status.statusType", true).replaceAll("\"", "")
//					.trim();
//
//			System.out.println("StatusCode : " + statusCode);
//			System.out.println("statusMessage : " + statusMessage);
//			System.out.println("statusType : " + statusType);
//
//			AssertJUnit.assertTrue("Status code doesn't match",
//					statusCode.contains("1"));
//			AssertJUnit.assertTrue("Status Message doesn't match",
//					statusMessage.contains("activated points successfuly"));
//			AssertJUnit.assertTrue("Status Type doesn't match",
//					statusType.contains("SUCCESS"));
//		}
//	}

	@Test(groups = { "Sanity", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "mymyntraAccountInfoDataProvider")
	public void LoyaltyPoints_myMyntraAccountInfo_verifySuccessResponse(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.MYMYNTRAACCOUNTINFO, init.Configurations, PayloadType.JSON, 
				new String[] { login },PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		MyntAssert.assertEquals(
				"Response code for Mymyntra Account Info API does not match",
				200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "ProdSanity","MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "mymyntraAccountInfoDataProvider")
	public void LoyaltyPoints_myMyntraAccountInfo_verifyStatusMessage(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.MYMYNTRAACCOUNTINFO, init.Configurations, PayloadType.JSON, 
				new String[] { login },PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.assertEquals("myMyntraAccountInfo API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("SUCCESS"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "mymyntraAccountInfoDataProvider")
	public void LoyaltyPoints_myMyntraAccountInfo_verifyStatusType(String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.MYMYNTRAACCOUNTINFO, init.Configurations, PayloadType.JSON, 
				new String[] { login },PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusType);
		System.out.println("Status Type for myMyntraAccountInfo API is :"
				+ statusType);

		AssertJUnit.assertTrue(
				"Status Type for myMyntraAccountInfo API does not match",
				statusType.contains("SUCCESS"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "mymyntraAccountInfoDataProvider")
	public void LoyaltyPoints_myMyntraAccountInfo_verifyLogin(String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.MYMYNTRAACCOUNTINFO, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		System.out.println("Login ID in request is :" + login);

		String loginID1 = req.respvalidate
				.NodeValue("userAccountInfo.login", true).replaceAll("\"", "")
				.trim();
		System.out.println("Login ID in userAccountInfo Node's response is: "
				+ loginID1);
		log.info(loginID1);

		AssertJUnit.assertEquals(
				"Login ID in userAccountInfo Node does not match", login,
				loginID1);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	/*@Test(groups = { "Sanity","MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider = "mymyntraAccountInfoDataProvider_negativeCases")
	public void LoyaltyPoints_myMyntraAccountInfoNegativeCases(String loginID) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.MYMYNTRAACCOUNTINFO, init.Configurations,
				PayloadType.JSON, new String[] { loginID }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals(req.response.getStatus(), 200);
		MyntAssert.assertEquals("myMyntraAccountInfoNegativeCases not working",
				200, req.response.getStatus());
		MyntAssert.setJsonResponse(req.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}*/

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "acceptTermsAndConditionsDataProvider")
	public void LoyaltyPoints_acceptTermsAndConditions_verifySuccessResponse(
			String loginID) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCEPTTERMSANDCONDITIONS, init.Configurations,
				PayloadType.JSON, new String[] { loginID }, PayloadType.JSON);
		log.info(service.URL);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		log.info(req.response);
		// AssertJUnit.assertEquals(req.response.getStatus(), 200);
		MyntAssert.assertEquals(
				"LoyalityPoints_acceptTermsAndConditions is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "acceptTermsAndConditionsDataProvider")
	public void LoyaltyPoints_acceptTermsAndConditions_verifyNodeValues(
			String loginID) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCEPTTERMSANDCONDITIONS, init.Configurations,
				PayloadType.JSON, new String[] { loginID }, PayloadType.JSON);
		log.info(service.URL);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		System.out.println("statusMessage : " + statusMessage);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();

		System.out.println("statusType : " + statusType);

		String statusCode = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();

		System.out.println("statusCode : " + statusCode);

		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("successfully recorded TNC action"));

		AssertJUnit.assertTrue("Status Type doesn't match",
				statusType.contains("SUCCESS"));

		AssertJUnit.assertTrue("Status Code doesn't match",
				statusCode.contains("1"));

	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "computeTierInfoDataProvider")
	public void LoyaltyPoints_computeTierInfo_verifySuccessResponse(
			String loginID) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.COMPUTETIERINFO, init.Configurations, PayloadType.JSON,
				new String[] { loginID }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		log.info(req.response);
		// AssertJUnit.assertEquals(req.response.getStatus(), 200);
		MyntAssert.assertEquals(
				"LoyalityPoints_computeTierInfo is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "computeTierInfoDataProvider")
	public void LoyaltyPoints_computeTierInfo_verifyNodeValues(String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.COMPUTETIERINFO, init.Configurations, PayloadType.JSON,
				new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		System.out.println("statusMessage : " + statusMessage);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();

		System.out.println("statusType : " + statusType);

		String statusCode = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();

		System.out.println("statusCode : " + statusCode);

		AssertJUnit
				.assertTrue(
						"Status Message doesn't match",
						statusMessage
								.contains("completed tier recomputation for order acceptance by the user"));

		AssertJUnit.assertTrue("Status Type doesn't match",
				statusType.contains("SUCCESS"));

		AssertJUnit.assertTrue("Status Code doesn't match",
				statusCode.contains("1"));

	}

	// This test case takes too much of time to execute - 20-25 mins
	@Test(groups = { "" }, dataProvider = "processUsersTiersDataProvider")
	public void LoyaltyPoints_processUsersTiers_verifySuccessResponse(
			String day, String month, String year) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.PROCESSUSERSTIERS, init.Configurations, new String[] {
						day, month, year }, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		log.info(req.response);
		// AssertJUnit.assertEquals(req.response.getStatus(), 200);
		MyntAssert.assertEquals(
				"LoyalityPoints_processUsersTiers is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "ExhaustiveRegression" }, dataProvider = "processOrdersActivatePointsDataProvider")
	public void LoyaltyPoints_processOrdersActivatePoints_verifySuccessResponse(
			String day, String month, String year) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.PROCESSORDERSACTIVATEPOINTS, init.Configurations,
				new String[] { day, month, year }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		MyntAssert.assertEquals(
				"processOrdersActivatePoints API is not working", 200,
				req.response.getStatus());
		// MyntAssert.setJsonResponse(req.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "ExhaustiveRegression" }, dataProvider = "processOrdersActivatePointsDataProvider")
	public void LoyaltyPoints_processOrdersActivatePoints_verifyStatusCode(
			String day, String month, String year) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.PROCESSORDERSACTIVATEPOINTS, init.Configurations,
				new String[] { day, month, year }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusCode = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		System.out
				.println("Status Code captured in response is :" + statusCode);

		MyntAssert.assertEquals("Status Code does not match", "1", statusCode);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "ExhaustiveRegression" }, dataProvider = "processOrdersActivatePointsDataProvider")
	public void LoyaltyPoints_processOrdersActivatePoints_verifyStatusMessage(
			String day, String month, String year) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.PROCESSORDERSACTIVATEPOINTS, init.Configurations,
				new String[] { day, month, year }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		System.out.println("Status Message captured in response is :"
				+ statusMessage);

		AssertJUnit
				.assertTrue(
						"Status Message doesn't match",
						statusMessage
								.contains("completed the orders LP activation processing for day"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "ExhaustiveRegression" }, dataProvider = "processOrdersActivatePointsDataProvider")
	public void LoyaltyPoints_processOrdersActivatePoints_verifyStatusType(
			String day, String month, String year) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.PROCESSORDERSACTIVATEPOINTS, init.Configurations,
				new String[] { day, month, year }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		System.out
				.println("Status Type captured in response is :" + statusType);

		MyntAssert.assertEquals("Status Type does not match", "SUCCESS",
				statusType);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "loyalityPointsConfigurationKeyValueDataProvider")
	public void LoyaltyPoints_configurationKeyValue_verifySuccessResponse(
			String key, String value) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.LOYALITYCONFIGURATION, init.Configurations,
				new String[] { key, value }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic bG95YWx0eXVzZXJ+bG95YWx0eXVzZXI6Q29uZmlnbG95YWx0eSQlXiYjQA==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		log.info(service.URL);
		// AssertJUnit.assertEquals(req.response.getStatus(), 200);
		MyntAssert.assertEquals(
				"configurationKeyValuePositiveCases is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "loyalityPointsConfigurationKeyValueDataProvider")
	public void LoyaltyPoints_configurationKeyValue_verifyNodeValues(
			String key, String value) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.LOYALITYCONFIGURATION, init.Configurations,
				new String[] { key, value }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic bG95YWx0eXVzZXJ+bG95YWx0eXVzZXI6Q29uZmlnbG95YWx0eSQlXiYjQA==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		log.info(service.URL);
		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		System.out.println("statusMessage : " + statusMessage);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();

		System.out.println("statusType : " + statusType);

		String statusCode = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();

		System.out.println("statusCode : " + statusCode);

		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("saved the given key value pair"));

		AssertJUnit.assertTrue("Status Type doesn't match",
				statusType.contains("SUCCESS"));

		AssertJUnit.assertTrue("Status Code doesn't match",
				statusCode.contains("1"));
	}

	@Test(groups = { "Sanity","MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider = "loyalityPointsConfigurationKeyValue_negativeCases")
	public void LoyaltyPoints_configurationKeyValueNegativeCases(String key,
			String value) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.LOYALITYCONFIGURATION, init.Configurations,
				new String[] { key, value }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic bG95YWx0eXVzZXJ+bG95YWx0eXVzZXI6Q29uZmlnbG95YWx0eSQlXiYjQA==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		log.info(service.URL);
		// AssertJUnit.assertEquals(req.response.getStatus(), 200);
		MyntAssert.assertEquals("configurationKeyValue API is not working",
				200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "geKkeyNameDataProvider_positiveCases")
	public void LoyaltyPoints_configurationKeyValueGetKeyName_positiveCases(
			String key) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.GETKEYNAME, init.Configurations, PayloadType.JSON,
				new String[] { key }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		// AssertJUnit.assertEquals(req.response.getStatus(), 200);
		MyntAssert.assertEquals(
				"configurationKeyValueKeyNamePositiveCases is not working",
				200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "geKkeyNameDataProvider_positiveCases")
	public void LoyaltyPoints_configurationKeyValueGetKeyName_verifyNodeValues(
			String key) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.GETKEYNAME, init.Configurations, PayloadType.JSON,
				new String[] { key }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		String value = req.respvalidate.NodeValue("keyValuePairs.value", true);
		System.out.println("Value found for given key - " + key + "is :"
				+ value);

		String statusCode = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		String key1 = req.respvalidate.NodeValue("keyValuePairs.key", true)
				.replaceAll("\"", "").trim();

		System.out.println("StatusCode : " + statusCode);
		System.out.println("statusMessage : " + statusMessage);
		System.out.println("statusType : " + statusType);
		System.out.println("Key Captured in response is :" + key1);

		AssertJUnit.assertTrue("Status code doesn't match",
				statusCode.contains("1"));
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("value found for the given key"));
		AssertJUnit.assertTrue("Status Type doesn't match",
				statusType.contains("SUCCESS"));
		AssertJUnit.assertEquals("Key doesn't match", key, key1);
	}

	@Test(groups = { "Sanity","MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider = "getKeyNameDataProvider_negativeCases")
	public void LoyaltyPoints_configurationKeyValueKeyName_verifyResponse_NegativeCases(
			String key) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.GETKEYNAME, init.Configurations, PayloadType.JSON,
				new String[] { key }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		MyntAssert.assertEquals("configurationKeyValueFatchAll is not working",
				200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider = "getKeyNameDataProvider_negativeCases")
	public void LoyaltyPoints_configurationKeyValueKeyName_verifyNodeValues_NegativeCases(
			String key) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.GETKEYNAME, init.Configurations, PayloadType.JSON,
				new String[] { key }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		String statusCode = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();

		System.out.println("StatusCode : " + statusCode);
		System.out.println("statusMessage : " + statusMessage);
		System.out.println("statusType : " + statusType);

		AssertJUnit.assertTrue("Status code doesn't match",
				statusCode.contains("1"));
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("value found for the given key"));
		AssertJUnit.assertTrue("Status Type doesn't match",
				statusType.contains("SUCCESS"));
	}

	@Test(groups = { "Sanity","MiniRegression", "Regression", "ExhaustiveRegression" })
	public void LoyaltyPoints_configurationKeyValueFetchAll_verifySuccessResponse() {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.KEYVALUEFETCHALL, init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		MyntAssert.assertEquals("configurationKeyValueFatchAll is not working",
				200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" })
	public void LoyaltyPoints_configurationKeyValueFetchAll_verifyNodeValues() {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.KEYVALUEFETCHALL, init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		// AssertJUnit.assertEquals(req.response.getStatus(), 200);
		String statusCode = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();

		System.out.println("StatusCode : " + statusCode);
		System.out.println("statusMessage : " + statusMessage);
		System.out.println("statusType : " + statusType);

		AssertJUnit.assertTrue("Status code doesn't match",
				statusCode.contains("1"));
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("value found for the given key"));
		AssertJUnit.assertTrue("Status Type doesn't match",
				statusType.contains("SUCCESS"));
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupDataProvider", enabled=false)
	public void LoyaltyPoints_createLoyaltyGroup_VerifySuccessResponse(
			String brands, String genders, String articleTypes,
			String groupName, String groupDescription,
			String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		MyntAssert.assertEquals("createLoyalityGroup API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupDataProvider")
	public void LoyaltyPoints_createLoyaltyGroup_verifyStatusCode(
			String brands, String genders, String articleTypes,
			String groupName, String groupDescription,
			String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusCode = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		System.out
				.println("Status Code captured in response is :" + statusCode);
		MyntAssert.assertEquals("Status Code does not match", "19001",
				statusCode);

		MyntAssert.assertEquals("createLoyalityGroup API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupDataProvider")
	public void LoyaltyPoints_createLoyaltyGroup_verifyStatusMessage(
			String brands, String genders, String articleTypes,
			String groupName, String groupDescription,
			String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.assertEquals("Create Loyalty Group API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("Group Successfully Created"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupDataProvider", enabled=false)
	public void LoyaltyPoints_createLoyaltyGroup_verifyGroupName(
			String brands, String genders, String articleTypes,
			String groupName, String groupDescription,
			String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		System.out.println("GroupName in request is :" + groupName);

		String GroupName = req.respvalidate
				.NodeValue(
						"lpGroupResponseEntry.loyaltyPointsGroupEntry.groupName",
						true).replaceAll("\"", "").trim();
		System.out.println("Group Name Captured in response is : " + GroupName);

		AssertJUnit.assertEquals(
				"Group Name in request and response does not match", groupName,
				GroupName);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	// Verify 200 Response
	/*@Test(groups = { "Sanity","MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupNegativeCasesDataProvider_200Response")
	public void LoyaltyPoints_createLoyalityGroupNegativeCases_verify200Response(
			String brands, String genders, String articleTypes,
			String groupName, String groupDescription,
			String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL); 
		AssertJUnit.assertEquals("Response does not match",200,req.response.getStatus());
	}*/

	// Verify 500 Response
	/*@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupNegativeCasesDataProvider_500Response")
	public void LoyaltyPoints_createLoyalityGroupNegativeCases_verify500Response(
			String brands, String genders, String articleTypes,
			String groupName, String groupDescription,
			String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL); //
		AssertJUnit.assertEquals("Response does not match",500,req.response.getStatus());
	}*/


	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupDataProvider")
	public void LoyaltyPoints_deleteGroupByID_verifySuccessResponse(
			String brands, String genders, String articleTypes,
			String groupName, String groupDescription,
			String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String GroupId = req.respvalidate
				.NodeValue("lpGroupResponseEntry.id", true)
				.replaceAll("\"", "").trim();
		System.out.println("Group ID Captured in response is : " + GroupId);

		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DELETELOYALITYGROUP, init.Configurations,
				PayloadType.JSON, new String[] { GroupId }, PayloadType.JSON);
		log.info(service1.URL);

		RequestGenerator req1 = new RequestGenerator(service1);
		MyntAssert.assertEquals("deleteGroupById is not working", 200,
				req1.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupDataProvider")
	public void LoyaltyPoints_deleteGroupByID_verifyStatusCode(String brands,
			String genders, String articleTypes, String groupName,
			String groupDescription, String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String GroupId = req.respvalidate
				.NodeValue("lpGroupResponseEntry.id", true)
				.replaceAll("\"", "").trim();
		System.out.println("Group ID Captured in response is : " + GroupId);

		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DELETELOYALITYGROUP, init.Configurations,
				PayloadType.JSON, new String[] { GroupId }, PayloadType.JSON);
		log.info(service1.URL);

		RequestGenerator req1 = new RequestGenerator(service1);

		String StatusCode = req1.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		System.out.println("Status Code Captured in response is : "
				+ StatusCode);

		MyntAssert.assertEquals("Status Code does not match", "19002",
				StatusCode);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupDataProvider")
	public void LoyaltyPoints_deleteGroupByID_verifyStatusMessage(
			String brands, String genders, String articleTypes,
			String groupName, String groupDescription,
			String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String GroupId = req.respvalidate
				.NodeValue("lpGroupResponseEntry.id", true)
				.replaceAll("\"", "").trim();
		System.out.println("Group ID Captured in response is : " + GroupId);

		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DELETELOYALITYGROUP, init.Configurations,
				PayloadType.JSON, new String[] { GroupId }, PayloadType.JSON);
		log.info(service1.URL);

		RequestGenerator req1 = new RequestGenerator(service1);

		String StatusMessage = req1.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		System.out.println("Status Message Captured in response is : "
				+ StatusMessage);

		MyntAssert.assertEquals("Status Message does not match",
				"Successfully Refreshed Rules", StatusMessage);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupDataProvider")
	public void LoyaltyPoints_deleteGroupByID_verifyStatusType(String brands,
			String genders, String articleTypes, String groupName,
			String groupDescription, String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String GroupId = req.respvalidate
				.NodeValue("lpGroupResponseEntry.id", true)
				.replaceAll("\"", "").trim();
		System.out.println("Group ID Captured in response is : " + GroupId);

		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DELETELOYALITYGROUP, init.Configurations,
				PayloadType.JSON, new String[] { GroupId }, PayloadType.JSON);
		log.info(service1.URL);

		RequestGenerator req1 = new RequestGenerator(service1);

		String StatusType = req1.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		System.out.println("Status Type Captured in response is : "
				+ StatusType);

		MyntAssert.assertEquals("Status Type does not match", "SUCCESS",
				StatusType);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupDataProvider")
	public void LoyaltyPoints_deleteGroupByID_verifyID(String brands,
			String genders, String articleTypes, String groupName,
			String groupDescription, String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String GroupId = req.respvalidate
				.NodeValue("lpGroupResponseEntry.id", true)
				.replaceAll("\"", "").trim();
		System.out.println("Created Group ID Captured in response is : "
				+ GroupId);

		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DELETELOYALITYGROUP, init.Configurations,
				PayloadType.JSON, new String[] { GroupId }, PayloadType.JSON);
		log.info(service1.URL);

		RequestGenerator req1 = new RequestGenerator(service1);

		String GroupID1 = req1.respvalidate
				.NodeValue("lpGroupResponseEntry.id", true)
				.replaceAll("\"", "").trim();
		System.out.println("Deleted Group ID Captured in response is : "
				+ GroupID1);

		MyntAssert.assertEquals("Group ID does not match", GroupId, GroupID1);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}


	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupDataProvider")
	public void LoyaltyPoints_deleteGroupByName_verifySuccessResponse(
			String brands, String genders, String articleTypes,
			String groupName, String groupDescription,
			String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String GroupName = req.respvalidate
				.NodeValue(
						"lpGroupResponseEntry.loyaltyPointsGroupEntry.groupName",
						true).replaceAll("\"", "").trim();
		System.out.println("Group Name Captured in response is : " + GroupName);

		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DELETELOYALITYGROUPBYNAME, init.Configurations,
				PayloadType.JSON, new String[] { GroupName }, PayloadType.JSON);
		log.info(service1.URL);

		RequestGenerator req1 = new RequestGenerator(service1);
		MyntAssert.assertEquals("deleteGroupByName is not working", 200,
				req1.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupDataProvider")
	public void LoyaltyPoints_deleteGroupByName_verifyStatusCode(
			String brands, String genders, String articleTypes,
			String groupName, String groupDescription,
			String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String GroupName = req.respvalidate
				.NodeValue(
						"lpGroupResponseEntry.loyaltyPointsGroupEntry.groupName",
						true).replaceAll("\"", "").trim();
		System.out.println("Group Name Captured in response is : " + GroupName);

		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DELETELOYALITYGROUPBYNAME, init.Configurations,
				PayloadType.JSON, new String[] { GroupName }, PayloadType.JSON);
		log.info(service1.URL);

		RequestGenerator req1 = new RequestGenerator(service1);

		String StatusCode = req1.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		System.out.println("Status Code Captured in response is : "
				+ StatusCode);

		MyntAssert.assertEquals("Status Code does not match", "19002",
				StatusCode);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupDataProvider")
	public void LoyaltyPoints_deleteGroupByName_verifyStatusMessage(
			String brands, String genders, String articleTypes,
			String groupName, String groupDescription,
			String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String GroupName = req.respvalidate
				.NodeValue(
						"lpGroupResponseEntry.loyaltyPointsGroupEntry.groupName",
						true).replaceAll("\"", "").trim();
		System.out.println("Group Name Captured in response is : " + GroupName);

		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DELETELOYALITYGROUPBYNAME, init.Configurations,
				PayloadType.JSON, new String[] { GroupName }, PayloadType.JSON);
		log.info(service1.URL);

		RequestGenerator req1 = new RequestGenerator(service1);

		String StatusMessage = req1.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		System.out.println("Status Message Captured in response is : "
				+ StatusMessage);

		MyntAssert.assertEquals("Status Message does not match",
				"Successfully Refreshed Rules", StatusMessage);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupDataProvider")
	public void LoyaltyPoints_deleteGroupByName_verifyStatusType(
			String brands, String genders, String articleTypes,
			String groupName, String groupDescription,
			String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String GroupName = req.respvalidate
				.NodeValue(
						"lpGroupResponseEntry.loyaltyPointsGroupEntry.groupName",
						true).replaceAll("\"", "").trim();
		System.out.println("Group Name Captured in response is : " + GroupName);

		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DELETELOYALITYGROUPBYNAME, init.Configurations,
				PayloadType.JSON, new String[] { GroupName }, PayloadType.JSON);
		log.info(service1.URL);

		RequestGenerator req1 = new RequestGenerator(service1);

		String StatusType = req1.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		System.out.println("Status Type Captured in response is : "
				+ StatusType);

		MyntAssert.assertEquals("Status Type does not match", "SUCCESS",
				StatusType);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupDataProvider")
	public void LoyaltyPoints_deleteGroupByName_verifyID(String brands,
			String genders, String articleTypes, String groupName,
			String groupDescription, String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String GroupName = req.respvalidate
				.NodeValue(
						"lpGroupResponseEntry.loyaltyPointsGroupEntry.groupName",
						true).replaceAll("\"", "").trim();
		System.out.println("Group Name Captured in response is : " + GroupName);

		String GroupID1 = req.respvalidate
				.NodeValue("lpGroupResponseEntry.id", true)
				.replaceAll("\"", "").trim();
		System.out.println("Created Group ID Captured in response is : "
				+ GroupID1);

		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DELETELOYALITYGROUPBYNAME, init.Configurations,
				PayloadType.JSON, new String[] { GroupName }, PayloadType.JSON);
		log.info(service1.URL);

		RequestGenerator req1 = new RequestGenerator(service1);

		String GroupID = req1.respvalidate
				.NodeValue("lpGroupResponseEntry.id", true)
				.replaceAll("\"", "").trim();
		System.out.println("Deleted Group ID Captured in response is : "
				+ GroupID);

		MyntAssert.assertEquals("Group IDs does not match", GroupID1, GroupID);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "loyaltyPoints_deleteGroupByName_negativeCases")
	public void LoyaltyPoints_deleteGroupByNameNegativeCases(String urlparams) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DELETELOYALITYGROUPBYNAME, init.Configurations,
				PayloadType.JSON, new String[] { urlparams }, PayloadType.JSON);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put(HttpHeaders.ACCEPT, "application/json");
		headers.put(HttpHeaders.CONTENT_TYPE, "application/json");
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		MyntAssert.assertEquals(
				"deleteGroupByNameNegativeCases is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "indexstyleDataProvider")
	public void LoyaltyPoints_indexStyle_verifySuccessResponse(String styleId,
			String brand, String gender, String articleType) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.INDEXSTYLELISTLOYALITYGROUP, init.Configurations,
				new String[] { styleId, brand, gender, articleType },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		MyntAssert.assertEquals("indexStyle API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);

	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "indexstyleDataProvider")
	public void LoyaltyPoints_indexStyle_verifyStatusCode(String styleId,
			String brand, String gender, String articleType) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.INDEXSTYLELISTLOYALITYGROUP, init.Configurations,
				new String[] { styleId, brand, gender, articleType },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String StatusCode = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		System.out
				.println("Status Code Captured in response is :" + StatusCode);

		MyntAssert
				.assertEquals("Response Code does not match", "1", StatusCode);
		MyntAssert.setExpectedAndActualForMyntListen(true);

	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "indexstyleDataProvider")
	public void LoyaltyPoints_indexStyle_verifyStatusMessage(String styleId,
			String brand, String gender, String articleType) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.INDEXSTYLELISTLOYALITYGROUP, init.Configurations,
				new String[] { styleId, brand, gender, articleType },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String StatusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		System.out.println("Status Message Captured in response is :"
				+ StatusMessage);

		MyntAssert.assertEquals("Status Message does not match", "SUCCESS",
				StatusMessage);
		MyntAssert.setExpectedAndActualForMyntListen(true);

	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "indexstyleDataProvider")
	public void LoyaltyPoints_indexStyle_verifyStatusType(String styleId,
			String brand, String gender, String articleType) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.INDEXSTYLELISTLOYALITYGROUP, init.Configurations,
				new String[] { styleId, brand, gender, articleType },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		String StatusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		System.out
				.println("Status Type Captured in response is :" + StatusType);

		MyntAssert.assertEquals("Status Type does not match", "SUCCESS",
				StatusType);
		MyntAssert.setExpectedAndActualForMyntListen(true);

	}

	/*@Test(groups = { "Sanity","MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider = "indexstyleErrorCases200ResponseDataProvider")
	public void LoyaltyPoints_indexStyleErrorCases_verify200Response(
			String styleId, String brand, String gender, String articleType) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.INDEXSTYLELISTLOYALITYGROUP, init.Configurations,
				new String[] { styleId, brand, gender, articleType },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service); //
		log.info(service.URL);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		MyntAssert.assertEquals("Response for Index Style API does not match",
				200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);

	}*/

	/*@Test(groups = { "Sanity","MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider = "indexstyleErrorCases500ResponseDataProvider")
	public void LoyaltyPoints_indexStyleErrorCases_verify500Response(
			String styleId, String brand, String gender, String articleType) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.INDEXSTYLELISTLOYALITYGROUP, init.Configurations,
				new String[] { styleId, brand, gender, articleType },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals(500, req.response.getStatus());
		MyntAssert.assertEquals("Response for Index Style API does not match",
				500, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);

	}*/

	@Test(groups = { "SchemaValidation" ,"Fox7Sanity" }, dataProvider = "LoyalityDP_creditLoyaltyPoints_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPointsService_creditLoyalityPoints_verifyResponseDataNodesUsingSchemaValidations(String login, String activeCreditPoints, String inActiveCreditPoints,
			String activeDebitPoints, String inActiveDebitPoints, String description, String itemType, String itemId, String businessProcess) 
	{
		RequestGenerator creditLoyalityPointsReqGen = loyalityPointsServiceHelper.invokeCreditLoyaltyPoints(login, activeCreditPoints, inActiveCreditPoints, 
				activeDebitPoints, inActiveDebitPoints, description, itemType, itemId, businessProcess);
		String creditLoyalityPointsResponse = creditLoyalityPointsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService creditLoyalityPoints API response :\n\n"+creditLoyalityPointsResponse);
		log.info("\nPrinting LoyalityPointsService creditLoyalityPoints API response :\n\n"+creditLoyalityPointsResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService creditLoyalityPoints API is not working", 200, creditLoyalityPointsReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-creditloyalitypoints-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(creditLoyalityPointsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService creditLoyalityPoints API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation","Fox7Sanity"  }, dataProvider = "LoyalityDP_debitLoyaltyPoints_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPointsService_debitLoyalityPoints_verifyResponseDataNodesUsingSchemaValidations(String login, String activeCreditPoints, String inActiveCreditPoints, 
			String activeDebitPoints, String inActiveDebitPoints, String description, String itemType, String itemId, String businessProcess) 
	{
		RequestGenerator debitLoyalityPointsReqGen = loyalityPointsServiceHelper.invokeDebitLoyaltyPoints(login, activeCreditPoints, inActiveCreditPoints, 
				activeDebitPoints, inActiveDebitPoints, description, itemType, itemId, businessProcess);
		String debitLoyalityPointsResponse = debitLoyalityPointsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService debitLoyaltyPoints API response :\n\n"+debitLoyalityPointsResponse);
		log.info("\nPrinting LoyalityPointsService debitLoyaltyPoints API response :\n\n"+debitLoyalityPointsResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService debitLoyalityPoints API is not working", 200, debitLoyalityPointsReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-debitloyalitypoints-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(debitLoyalityPointsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService debitLoyaltyPoints API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" ,"Fox7Sanity" }, dataProvider = "LoyalityDP_getTransactionHistoryOfInactivePoints_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPointsService_getTransactionHistoryOfInactivePoints_verifyResponseDataNodesUsingSchemaValidations(String login) 
	{
		RequestGenerator getTransactionHistoryOfInactivePointsReqGen = loyalityPointsServiceHelper.invokeGetTransactionHistoryOfInactivePoints(login);
		String getTransactionHistoryOfInactivePointsResponse = getTransactionHistoryOfInactivePointsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService getTransactionHistoryOfInactivePoints API response :\n\n"+getTransactionHistoryOfInactivePointsResponse);
		log.info("\nPrinting LoyalityPointsService getTransactionHistoryOfInactivePoints API response :\n\n"+getTransactionHistoryOfInactivePointsResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService getTransactionHistoryOfInactivePoints API is not working", 200, 
				getTransactionHistoryOfInactivePointsReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-gettransactionhistoryofinactivepoints-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getTransactionHistoryOfInactivePointsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService getTransactionHistoryOfInactivePoints API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation"}, dataProvider = "LoyalityDP_getTransactionHistoryOfActivePoints_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPointsService_getTransactionHistoryOfActivePoints_verifyResponseDataNodesUsingSchemaValidations(String login) 
	{
		RequestGenerator getTransactionHistoryOfActivePointsReqGen = loyalityPointsServiceHelper.invokeGetTransactionHistoryOfActivePoints(login);
		String getTransactionHistoryOfActivePointsResponse = getTransactionHistoryOfActivePointsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService getTransactionHistoryOfActivePoints API response :\n\n"+getTransactionHistoryOfActivePointsResponse);
		log.info("\nPrinting LoyalityPointsService getTransactionHistoryOfActivePoints API response :\n\n"+getTransactionHistoryOfActivePointsResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService getTransactionHistoryOfActivePoints API is not working", 200, 
				getTransactionHistoryOfActivePointsReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-gettransactionhistoryofactivepoints-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getTransactionHistoryOfActivePointsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService getTransactionHistoryOfActivePoints API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation","Fox7Sanity"  }, dataProvider = "LoyalityDP_getAccountInfo_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPoints_getAccountInfo_verifyResponseDataNodesUsingSchemaValidations(String login)
	{
		RequestGenerator getAccountInfoReqGen = loyalityPointsServiceHelper.invokeGetAccountInfo(login);
		String getAccountInfoResponse = getAccountInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService getAccountInfo API response :\n\n"+getAccountInfoResponse);
		log.info("\nPrinting LoyalityPointsService getAccountInfo API response :\n\n"+getAccountInfoResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService getAccountInfo API is not working", 200, getAccountInfoReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-getaccountinfo-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getAccountInfoResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService getAccountInfo API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_creditLoyalityPointsOrderConfirmation_verifyResponseDataNodesUsingSchemaValidations", enabled=false)
	public void LoyaltyPoints_creditLoyalityOrderConfirmation_verifyResponseDataNodesUsingSchemaValidations(String login, String points, String orderId, 
			String description) 
	{
		RequestGenerator creditLoyalityOrderConfirmationReqGen = loyalityPointsServiceHelper.invokeCreditLoyalityOrderConfirmation(login, points, orderId, description);
		String creditLoyalityOrderConfirmationResponse = creditLoyalityOrderConfirmationReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService creditLoyalityOrderConfirmation API response :\n\n"+creditLoyalityOrderConfirmationResponse);
		log.info("\nPrinting LoyalityPointsService creditLoyalityOrderConfirmation API response :\n\n"+creditLoyalityOrderConfirmationResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService creditLoyalityOrderConfirmation API is not working", 200, 
				creditLoyalityOrderConfirmationReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-creditloyalityorderconfirmation-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(creditLoyalityOrderConfirmationResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService creditLoyalityOrderConfirmation API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_creditLoyalityItemCancellation_verifyResponseDataNodesUsingSchemaValidations", enabled=false)
	public void LoyaltyPoints_creditLoyalityItemCancellation_verifyResponseDataNodesUsingSchemaValidations(String login, String points, String orderId, 
			String description)
	{
		RequestGenerator creditLoyalityItemCancellationReqGen = loyalityPointsServiceHelper.invokeCreditLoyalityItemCancellation(login, points, orderId, description);
		String creditLoyalityItemCancellationResponse = creditLoyalityItemCancellationReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService creditLoyalityItemCancellation API response :\n\n"+creditLoyalityItemCancellationResponse);
		log.info("\nPrinting LoyalityPointsService creditLoyalityItemCancellation API response :\n\n"+creditLoyalityItemCancellationResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService creditLoyalityItemCancellation API is not working", 200, 
				creditLoyalityItemCancellationReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-creditloyalityitemcancellation-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(creditLoyalityItemCancellationResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService creditLoyalityItemCancellation API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_debitLoyalityOrderUsage_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPoints_debitLoyalityOrderusage_verifyResponseDataNodesUsingSchemaValidations(String login, String points, String orderId, String description)
	{
		RequestGenerator debitLoyalityOrderusageReqGen = loyalityPointsServiceHelper.invokeDebitLoyalityOrderusage(login, points, orderId, description);
		String debitLoyalityOrderusageResponse = debitLoyalityOrderusageReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService debitLoyalityOrderusage API response :\n\n"+debitLoyalityOrderusageResponse);
		log.info("\nPrinting LoyalityPointsService debitLoyalityOrderusage API response :\n\n"+debitLoyalityOrderusageResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService debitLoyalityOrderusage API is not working", 200, 
				debitLoyalityOrderusageReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-debitloyalityorderusage-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(debitLoyalityOrderusageResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService debitLoyalityOrderusage API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
//	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_debitLoyalityItemCancellation_verifyResponseDataNodesUsingSchemaValidations")
//	public void LoyaltyPoints_debitLoyalityItemCancellation_verifyResponseDataNodesUsingSchemaValidations(String login, String points, String orderId, String description) 
//	{
//		RequestGenerator debitLoyalityOrderusageReqGen = loyalityPointsServiceHelper.invokeDebitLoyalityItemCancellation(login, points, orderId, description);
//		String debitLoyalityItemCancellationResponse = debitLoyalityOrderusageReqGen.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting LoyalityPointsService debitLoyalityItemCancellation API response :\n\n"+debitLoyalityItemCancellationResponse);
//		log.info("\nPrinting LoyalityPointsService debitLoyalityItemCancellation API response :\n\n"+debitLoyalityItemCancellationResponse);
//		
//		MyntAssert.assertEquals("LoyalityPointsService debitLoyalityItemCancellation API is not working", 200, 
//				debitLoyalityOrderusageReqGen.response.getStatus());
//		MyntAssert.setExpectedAndActualForMyntListen(true);
//		
//		try {
//            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-debitloyalityitemcancellation-schema.txt");
//            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(debitLoyalityItemCancellationResponse, jsonschema);
//            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService debitLoyalityItemCancellation API response", 
//            		CollectionUtils.isEmpty(missingNodeList));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//	}
//	
//	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_activateLoyalityPointsForOrder_verifyResponseDataNodesUsingSchemaValidations")
//	public void LoyaltyPoints_activateLoyalityPointsForOrders_verifyResponseDataNodesUsingSchemaValidations(String login, String points, String orderId, 
//			String description) 
//	{
//		RequestGenerator activateLoyalityPointsForOrdersReqGen = loyalityPointsServiceHelper.invokeActivateLoyalityPointsForOrders(login, points, orderId, description);
//		String activateLoyalityPointsForOrdersResponse = activateLoyalityPointsForOrdersReqGen.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting LoyalityPointsService activateLoyalityPointsForOrders API response :\n\n"+activateLoyalityPointsForOrdersResponse);
//		log.info("\nPrinting LoyalityPointsService activateLoyalityPointsForOrders API response :\n\n"+activateLoyalityPointsForOrdersResponse);
//		
//		MyntAssert.assertEquals("LoyalityPointsService activateLoyalityPointsForOrders API is not working", 200, 
//				activateLoyalityPointsForOrdersReqGen.response.getStatus());
//		MyntAssert.setExpectedAndActualForMyntListen(true);
//		
//		try {
//            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-activateloyalitypointsfororders-schema.txt");
//            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(activateLoyalityPointsForOrdersResponse, jsonschema);
//            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService activateLoyalityPointsForOrders API response", 
//            		CollectionUtils.isEmpty(missingNodeList));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_myMyntraAccountInfo_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPoints_myMyntraAccountInfo_verifyResponseDataNodesUsingSchemaValidations(String login)
	{
		RequestGenerator myMyntraAccountInfoReqGen = loyalityPointsServiceHelper.invokeMyMyntraAccountInfo(login);
		String myMyntraAccountInfoResponse = myMyntraAccountInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService myMyntraAccountInfo API response :\n\n"+myMyntraAccountInfoResponse);
		log.info("\nPrinting LoyalityPointsService myMyntraAccountInfo API response :\n\n"+myMyntraAccountInfoResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService myMyntraAccountInfo API is not working", 200, 
				myMyntraAccountInfoReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-mymyntraaccountinfo-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(myMyntraAccountInfoResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService myMyntraAccountInfo API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_acceptTermsAndConditions_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPoints_acceptTermsAndConditions_verifyResponseDataNodesUsingSchemaValidations(String login) 
	{
		RequestGenerator acceptTermsAndConditionsReqGen = loyalityPointsServiceHelper.invokeAcceptTermsAndConditions(login);
		String acceptTermsAndConditionsResponse = acceptTermsAndConditionsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService acceptTermsAndConditions API response :\n\n"+acceptTermsAndConditionsResponse);
		log.info("\nPrinting LoyalityPointsService acceptTermsAndConditions API response :\n\n"+acceptTermsAndConditionsResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService acceptTermsAndConditions API is not working", 200, 
				acceptTermsAndConditionsReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-accepttermsandconditions-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(acceptTermsAndConditionsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService acceptTermsAndConditions API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_computeTierInfo_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPoints_computeTierInfo_verifyResponseDataNodesUsingSchemaValidations(String login) 
	{
		RequestGenerator computeTierInfoReqGen = loyalityPointsServiceHelper.invokeComputeTierInfo(login);
		String computeTierInfoResponse = computeTierInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService computeTierInfo API response :\n\n"+computeTierInfoResponse);
		log.info("\nPrinting LoyalityPointsService computeTierInfo API response :\n\n"+computeTierInfoResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService computeTierInfo API is not working", 200, 
				computeTierInfoReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-computeTierInfo-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(computeTierInfoResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService computeTierInfo API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_processUsersTiers_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPoints_processUsersTiers_verifyResponseDataNodesUsingSchemaValidations(String day, String month, String year) 
	{
		RequestGenerator processUsersTiersReqGen = loyalityPointsServiceHelper.invokeProcessUsersTiers(day, month, year);
		String processUsersTiersResponse = processUsersTiersReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService processUsersTiers API response :\n\n"+processUsersTiersResponse);
		log.info("\nPrinting LoyalityPointsService processUsersTiers API response :\n\n"+processUsersTiersResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService processUsersTiers API is not working", 200, 
				processUsersTiersReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-processuserstiers-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(processUsersTiersResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService processUsersTiers API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_processOrdersActivatePoints_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPoints_processOrdersActivatePoints_verifyResponseDataNodesUsingSchemaValidations(String day, String month, String year) 
	{
		RequestGenerator processOrdersActivatePointsReqGen = loyalityPointsServiceHelper.invokeProcessOrdersActivatePoints(day, month, year);
		String processOrdersActivatePointsResponse = processOrdersActivatePointsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService processOrdersActivatePoints API response :\n\n"+processOrdersActivatePointsResponse);
		log.info("\nPrinting LoyalityPointsService processOrdersActivatePoints API response :\n\n"+processOrdersActivatePointsResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService processOrdersActivatePoints API is not working", 200, 
				processOrdersActivatePointsReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-processordersactivatepoints-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(processOrdersActivatePointsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService processOrdersActivatePoints API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
//	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_configurationKeyValue_verifyResponseDataNodesUsingSchemaValidations")
//	public void LoyaltyPoints_configurationKeyValue_verifyResponseDataNodesUsingSchemaValidations(String key, String value) 
//	{
//		RequestGenerator configurationKeyValueReqGen = loyalityPointsServiceHelper.invokeConfigurationKeyValue(key, value);
//		String configurationKeyValueResponse = configurationKeyValueReqGen.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting LoyalityPointsService configurationKeyValue API response :\n\n"+configurationKeyValueResponse);
//		log.info("\nPrinting LoyalityPointsService configurationKeyValue API response :\n\n"+configurationKeyValueResponse);
//		
//		MyntAssert.assertEquals("LoyalityPointsService configurationKeyValue API is not working", 200, 
//				configurationKeyValueReqGen.response.getStatus());
//		MyntAssert.setExpectedAndActualForMyntListen(true);
//		
//		try {
//            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-configurationkeyvalue-schema.txt");
//            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(configurationKeyValueResponse, jsonschema);
//            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService configurationKeyValue API response", 
//            		CollectionUtils.isEmpty(missingNodeList));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//	}
	
	@Test(groups = { "SchemaValidation" })
	public void LoyaltyPoints_fetchAllConfigurationKeyValue_verifyResponseDataNodesUsingSchemaValidations() 
	{
		RequestGenerator fetchAllConfigurationKeyValueReqGen = loyalityPointsServiceHelper.invokeFetchAllConfigurationKeyValue();
		String fetchAllConfigurationKeyValueResponse = fetchAllConfigurationKeyValueReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService fetchAllConfigurationKeyValue API response :\n\n"+fetchAllConfigurationKeyValueResponse);
		log.info("\nPrinting LoyalityPointsService fetchAllConfigurationKeyValue API response :\n\n"+fetchAllConfigurationKeyValueResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService fetchAllConfigurationKeyValue API is not working", 200, 
				fetchAllConfigurationKeyValueReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-fetchallconfigurationkeyvalue-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(fetchAllConfigurationKeyValueResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService fetchAllConfigurationKeyValue API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_createLoyaltyGroupData_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPoints_createLoyaltyGroup_verifyResponseDataNodesUsingSchemaValidations(String brands, String genders, String articleTypes, String groupName, 
			String groupDescription, String conversionFactorPoints) 
	{
		RequestGenerator createLoyaltyGroupReqGen = loyalityPointsServiceHelper.invokeCreateLoyaltyGroup(brands, genders, articleTypes, groupName, 
				groupDescription, conversionFactorPoints);
		String createLoyaltyGroupResponse = createLoyaltyGroupReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService createLoyaltyGroup API response :\n\n"+createLoyaltyGroupResponse);
		log.info("\nPrinting LoyalityPointsService createLoyaltyGroup API response :\n\n"+createLoyaltyGroupResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService createLoyaltyGroup API is not working", 200, createLoyaltyGroupReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-createloyaltygroup-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(createLoyaltyGroupResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService createLoyaltyGroup API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_deleteLoyaltyGroupById_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPoints_deleteLoyaltyGroupById_verifyResponseDataNodesUsingSchemaValidations(String brands, String genders, String articleTypes, String groupName, 
			String groupDescription, String conversionFactorPoints) 
	{
		RequestGenerator createLoyaltyGroupReqGen = loyalityPointsServiceHelper.invokeCreateLoyaltyGroup(brands, genders, articleTypes, groupName, 
				groupDescription, conversionFactorPoints);
		String createLoyaltyGroupResponse = createLoyaltyGroupReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService createLoyaltyGroup API response :\n\n"+createLoyaltyGroupResponse);
		log.info("\nPrinting LoyalityPointsService createLoyaltyGroup API response :\n\n"+createLoyaltyGroupResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService createLoyaltyGroup API is not working", 200, createLoyaltyGroupReqGen.response.getStatus());
		
		String groupId = createLoyaltyGroupReqGen.respvalidate.NodeValue("lpGroupResponseEntry.id", true).replaceAll("\"", "").trim();
		System.out.println("\nPrinting groupId captured in response is : " + groupId);
		log.info("\nPrinting groupId captured in response is : " + groupId);
		
		RequestGenerator deleteLoyaltyGroupByIdReqGen = loyalityPointsServiceHelper.invokeDeleteLoyaltyGroup(groupId);
		String deleteLoyaltyGroupByIdResponse = deleteLoyaltyGroupByIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService deleteLoyaltyGroupById API response :\n\n"+deleteLoyaltyGroupByIdResponse);
		log.info("\nPrinting LoyalityPointsService deleteLoyaltyGroupById API response :\n\n"+deleteLoyaltyGroupByIdResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService deleteLoyaltyGroup API is not working", 200, deleteLoyaltyGroupByIdReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-deleteloyaltygroupbyid-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(deleteLoyaltyGroupByIdResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService deleteLoyaltyGroupById API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_indexStyleListLoyaltyGroup_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPoints_indexStyleListLoyaltyGroup_verifyResponseDataNodesUsingSchemaValidations(String styleId, String brand, String gender, String articleType)
	{
		RequestGenerator indexStyleListLoyaltyGroupService = loyalityPointsServiceHelper.invokeIndexStyleListLoyaltyGroup(styleId, brand, gender, articleType);
		String indexStyleListLoyaltyGroupResponse = indexStyleListLoyaltyGroupService.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting LoyalityPointsService indexStyleListLoyaltyGroup API response :\n\n"+indexStyleListLoyaltyGroupResponse);
		log.info("\nPrinting LoyalityPointsService indexStyleListLoyaltyGroup API response :\n\n"+indexStyleListLoyaltyGroupResponse);
		
		MyntAssert.assertEquals("LoyalityPointsService indexStyleListLoyaltyGroup API is not working", 200, 
				indexStyleListLoyaltyGroupService.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-indexstylelistloyaltygroup-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(indexStyleListLoyaltyGroupResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LoyalityPointsService indexStyleListLoyaltyGroup API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
