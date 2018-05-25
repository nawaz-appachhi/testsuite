package com.myntra.apiTests.portalservices.loyalitypointsservice;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.lordoftherings.Initialize;

import com.myntra.loyaltyPointsService.domain.response.LoyaltyPointsServiceErrorResponse;
import com.myntra.loyaltyPointsService.domain.response.LoyaltyPointsServiceUserAccountResponse;
import com.myntra.loyaltyPointsService.domain.response.entry.LoyaltyPointsUserAccountEntry;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


/**
 * @author shankara.c
 *
 */
public class LoyalityPointsServiceHelper {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(LoyalityPointsServiceHelper.class);

	public RequestGenerator invokeCreditLoyaltyPoints(String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints, String inActiveDebitPoints, String description,
			String itemType, String itemId, String businessProcess) {
		MyntraService creditLoyalityPointsService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYPOINTS,
				init.Configurations, new String[] { login, activeCreditPoints, inActiveCreditPoints, activeDebitPoints,
						inActiveDebitPoints, description, itemType, itemId, businessProcess },
				PayloadType.JSON, PayloadType.JSON);

		System.out.println(
				"\nPrinting LoyalityPointsService creditLoyalityPoints API URL : " + creditLoyalityPointsService.URL);
		log.info("\nPrinting LoyalityPointsService creditLoyalityPoints API URL : " + creditLoyalityPointsService.URL);

		System.out.println("\nPrinting LoyalityPointsService creditLoyalityPoints API Payload : \n\n"
				+ creditLoyalityPointsService.Payload);
		log.info("\nPrinting LoyalityPointsService creditLoyalityPoints API Payload : \n\n"
				+ creditLoyalityPointsService.Payload);

		return new RequestGenerator(creditLoyalityPointsService);
	}

	public RequestGenerator invokeDebitLoyaltyPoints(String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints, String inActiveDebitPoints, String description,
			String itemType, String itemId, String businessProcess) {
		MyntraService debitLoyalityPointsService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYPOINTS,
				init.Configurations, new String[] { login, activeCreditPoints, inActiveCreditPoints, activeDebitPoints,
						inActiveDebitPoints, description, itemType, itemId, businessProcess },
				PayloadType.JSON, PayloadType.JSON);

		System.out.println(
				"\nPrinting LoyalityPointsService debitLoyalityPoints API URL : " + debitLoyalityPointsService.URL);
		log.info("\nPrinting LoyalityPointsService debitLoyalityPoints API URL : " + debitLoyalityPointsService.URL);

		System.out.println("\nPrinting LoyalityPointsService debitLoyalityPoints API Payload : \n\n"
				+ debitLoyalityPointsService.Payload);
		log.info("\nPrinting LoyalityPointsService debitLoyalityPoints API Payload : \n\n"
				+ debitLoyalityPointsService.Payload);

		return new RequestGenerator(debitLoyalityPointsService);
	}

	public RequestGenerator invokeGetTransactionHistoryOfInactivePoints(String login) {
		MyntraService getTransactionHistoryOfInactivePointsService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYINACTIVE, init.Configurations, PayloadType.JSON, new String[] { login },
				PayloadType.JSON);

		System.out.println("\nPrinting LoyalityPointsService getTransactionHistoryOfInactivePoints API URL : "
				+ getTransactionHistoryOfInactivePointsService.URL);
		log.info("\nPrinting LoyalityPointsService getTransactionHistoryOfInactivePoints API URL : "
				+ getTransactionHistoryOfInactivePointsService.URL);

		System.out.println("\nPrinting LoyalityPointsService getTransactionHistoryOfInactivePoints API Payload : \n\n"
				+ getTransactionHistoryOfInactivePointsService.Payload);
		log.info("\nPrinting LoyalityPointsService getTransactionHistoryOfInactivePoints API Payload : \n\n"
				+ getTransactionHistoryOfInactivePointsService.Payload);

		return new RequestGenerator(getTransactionHistoryOfInactivePointsService);

	}

	public RequestGenerator invokeGetTransactionHistoryOfActivePoints(String login) {
		MyntraService getTransactionHistoryOfActivePointsService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYACTIVE, init.Configurations, PayloadType.JSON, new String[] { login },
				PayloadType.JSON);

		System.out.println("\nPrinting LoyalityPointsService getTransactionHistoryOfActivePoints API URL : "
				+ getTransactionHistoryOfActivePointsService.URL);
		log.info("\nPrinting LoyalityPointsService getTransactionHistoryOfActivePoints API URL : "
				+ getTransactionHistoryOfActivePointsService.URL);

		System.out.println("\nPrinting LoyalityPointsService getTransactionHistoryOfActivePoints API Payload : \n\n"
				+ getTransactionHistoryOfActivePointsService.Payload);
		log.info("\nPrinting LoyalityPointsService getTransactionHistoryOfActivePoints API Payload : \n\n"
				+ getTransactionHistoryOfActivePointsService.Payload);

		return new RequestGenerator(getTransactionHistoryOfActivePointsService);

	}

	public RequestGenerator invokeGetAccountInfo(String login) {
		MyntraService getAccountInfoService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCOUNTINFOLOYALITY, init.Configurations, PayloadType.JSON, new String[] { login },
				PayloadType.JSON);

		System.out.println("\nPrinting LoyalityPointsService getAccountInfo API URL : " + getAccountInfoService.URL);
		log.info("\nPrinting LoyalityPointsService getAccountInfo API URL : " + getAccountInfoService.URL);

		System.out.println(
				"\nPrinting LoyalityPointsService getAccountInfo API Payload : \n\n" + getAccountInfoService.Payload);
		log.info("\nPrinting LoyalityPointsService getAccountInfo API Payload : \n\n" + getAccountInfoService.Payload);

		return new RequestGenerator(getAccountInfoService);

	}

	public RequestGenerator invokeCreditLoyalityOrderConfirmation(String login, String points, String orderId,
			String description) {
		MyntraService creditLoyalityOrderConfirmationService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYORDERCONFIRMATION, init.Configurations,
				new String[] { login, points, orderId, description }, PayloadType.JSON, PayloadType.JSON);

		System.out.println("\nPrinting LoyalityPointsService creditLoyalityOrderConfirmation API URL : "
				+ creditLoyalityOrderConfirmationService.URL);
		log.info("\nPrinting LoyalityPointsService creditLoyalityOrderConfirmation API URL : "
				+ creditLoyalityOrderConfirmationService.URL);

		System.out.println("\nPrinting LoyalityPointsService creditLoyalityOrderConfirmation API Payload : \n\n"
				+ creditLoyalityOrderConfirmationService.Payload);
		log.info("\nPrinting LoyalityPointsService creditLoyalityOrderConfirmation API Payload : \n\n"
				+ creditLoyalityOrderConfirmationService.Payload);

		return new RequestGenerator(creditLoyalityOrderConfirmationService);

	}

	public RequestGenerator invokeCreditLoyalityItemCancellation(String login, String points, String orderId,
			String description) {
		MyntraService creditLoyalityOrderConfirmationService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYITEMCANCELLATION, init.Configurations,
				new String[] { login, points, orderId, description }, PayloadType.JSON, PayloadType.JSON);

		System.out.println("\nPrinting LoyalityPointsService creditLoyalityItemCancellation API URL : "
				+ creditLoyalityOrderConfirmationService.URL);
		log.info("\nPrinting LoyalityPointsService creditLoyalityItemCancellation API URL : "
				+ creditLoyalityOrderConfirmationService.URL);

		System.out.println("\nPrinting LoyalityPointsService creditLoyalityItemCancellation API Payload : \n\n"
				+ creditLoyalityOrderConfirmationService.Payload);
		log.info("\nPrinting LoyalityPointsService creditLoyalityItemCancellation API Payload : \n\n"
				+ creditLoyalityOrderConfirmationService.Payload);

		return new RequestGenerator(creditLoyalityOrderConfirmationService);

	}

	public RequestGenerator invokeDebitLoyalityOrderusage(String login, String points, String orderId,
			String description) {
		MyntraService debitLoyalityOrderUsageService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
				new String[] { login, points, orderId, description }, PayloadType.JSON, PayloadType.JSON);

		System.out.println("\nPrinting LoyalityPointsService debitLoyalityOrderusage API URL : "
				+ debitLoyalityOrderUsageService.URL);
		log.info("\nPrinting LoyalityPointsService debitLoyalityOrderusage API URL : "
				+ debitLoyalityOrderUsageService.URL);

		System.out.println("\nPrinting LoyalityPointsService debitLoyalityOrderusage API Payload : \n\n"
				+ debitLoyalityOrderUsageService.Payload);
		log.info("\nPrinting LoyalityPointsService debitLoyalityOrderusage API Payload : \n\n"
				+ debitLoyalityOrderUsageService.Payload);

		return new RequestGenerator(debitLoyalityOrderUsageService);

	}

	public RequestGenerator invokeDebitLoyalityItemCancellation(String login, String points, String orderId,
			String description) {
		MyntraService debitLoyalityItemCancellationService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYITEMCANCELLATION, init.Configurations,
				new String[] { login, points, orderId, description }, PayloadType.JSON, PayloadType.JSON);

		System.out.println("\nPrinting LoyalityPointsService debitLoyalityItemCancellation API URL : "
				+ debitLoyalityItemCancellationService.URL);
		log.info("\nPrinting LoyalityPointsService debitLoyalityItemCancellation API URL : "
				+ debitLoyalityItemCancellationService.URL);

		System.out.println("\nPrinting LoyalityPointsService debitLoyalityItemCancellation API Payload : \n\n"
				+ debitLoyalityItemCancellationService.Payload);
		log.info("\nPrinting LoyalityPointsService debitLoyalityItemCancellation API Payload : \n\n"
				+ debitLoyalityItemCancellationService.Payload);

		return new RequestGenerator(debitLoyalityItemCancellationService);

	}

	public RequestGenerator invokeActivateLoyalityPointsForOrders(String login, String points, String orderId,
			String description) {
		MyntraService activateLoyalityPointsForOrdersService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACTIVATELOYALITYPOINTSFORORDER, init.Configurations,
				new String[] { login, points, orderId, description }, PayloadType.JSON, PayloadType.JSON);

		System.out.println("\nPrinting LoyalityPointsService activateLoyalityPointsForOrders API URL : "
				+ activateLoyalityPointsForOrdersService.URL);
		log.info("\nPrinting LoyalityPointsService activateLoyalityPointsForOrders API URL : "
				+ activateLoyalityPointsForOrdersService.URL);

		System.out.println("\nPrinting LoyalityPointsService activateLoyalityPointsForOrders API Payload : \n\n"
				+ activateLoyalityPointsForOrdersService.Payload);
		log.info("\nPrinting LoyalityPointsService activateLoyalityPointsForOrders API Payload : \n\n"
				+ activateLoyalityPointsForOrdersService.Payload);

		return new RequestGenerator(activateLoyalityPointsForOrdersService);

	}

	public RequestGenerator invokeMyMyntraAccountInfo(String login) {
		MyntraService myMyntraAccountInfoService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.MYMYNTRAACCOUNTINFO, init.Configurations, PayloadType.JSON, new String[] { login },
				PayloadType.JSON);

		System.out.println(
				"\nPrinting LoyalityPointsService myMyntraAccountInfo API URL : " + myMyntraAccountInfoService.URL);
		log.info("\nPrinting LoyalityPointsService myMyntraAccountInfo API URL : " + myMyntraAccountInfoService.URL);

		System.out.println("\nPrinting LoyalityPointsService myMyntraAccountInfo API Payload : \n\n"
				+ myMyntraAccountInfoService.Payload);
		log.info("\nPrinting LoyalityPointsService myMyntraAccountInfo API Payload : \n\n"
				+ myMyntraAccountInfoService.Payload);

		return new RequestGenerator(myMyntraAccountInfoService);

	}

	public RequestGenerator invokeAcceptTermsAndConditions(String login) {
		MyntraService acceptTermsAndConditionsService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCEPTTERMSANDCONDITIONS, init.Configurations, PayloadType.JSON, new String[] { login },
				PayloadType.JSON);

		System.out.println("\nPrinting LoyalityPointsService acceptTermsAndConditions API URL : "
				+ acceptTermsAndConditionsService.URL);
		log.info("\nPrinting LoyalityPointsService acceptTermsAndConditions API URL : "
				+ acceptTermsAndConditionsService.URL);

		System.out.println("\nPrinting LoyalityPointsService acceptTermsAndConditions API Payload : \n\n"
				+ acceptTermsAndConditionsService.Payload);
		log.info("\nPrinting LoyalityPointsService acceptTermsAndConditions API Payload : \n\n"
				+ acceptTermsAndConditionsService.Payload);

		return new RequestGenerator(acceptTermsAndConditionsService);

	}

	public RequestGenerator invokeComputeTierInfo(String login) {
		MyntraService computeTierInfoService = Myntra.getService(ServiceType.PORTAL_LOYALITY, APINAME.COMPUTETIERINFO,
				init.Configurations, PayloadType.JSON, new String[] { login }, PayloadType.JSON);

		System.out.println("\nPrinting LoyalityPointsService computeTierInfo API URL : " + computeTierInfoService.URL);
		log.info("\nPrinting LoyalityPointsService computeTierInfo API URL : " + computeTierInfoService.URL);

		System.out.println(
				"\nPrinting LoyalityPointsService computeTierInfo API Payload : \n\n" + computeTierInfoService.Payload);
		log.info(
				"\nPrinting LoyalityPointsService computeTierInfo API Payload : \n\n" + computeTierInfoService.Payload);

		return new RequestGenerator(computeTierInfoService);

	}

	public RequestGenerator invokeProcessUsersTiers(String day, String month, String year) {
		MyntraService processUsersTiersService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.PROCESSUSERSTIERS, init.Configurations, new String[] { day, month, year }, PayloadType.JSON,
				PayloadType.JSON);

		System.out.println(
				"\nPrinting LoyalityPointsService processUsersTiers API URL : " + processUsersTiersService.URL);
		log.info("\nPrinting LoyalityPointsService processUsersTiers API URL : " + processUsersTiersService.URL);

		System.out.println("\nPrinting LoyalityPointsService processUsersTiers API Payload : \n\n"
				+ processUsersTiersService.Payload);
		log.info("\nPrinting LoyalityPointsService processUsersTiers API Payload : \n\n"
				+ processUsersTiersService.Payload);

		return new RequestGenerator(processUsersTiersService);

	}

	public RequestGenerator invokeProcessOrdersActivatePoints(String day, String month, String year) {
		MyntraService processOrdersActivatePointsService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.PROCESSORDERSACTIVATEPOINTS, init.Configurations, new String[] { day, month, year },
				PayloadType.JSON, PayloadType.JSON);

		System.out.println("\nPrinting LoyalityPointsService processOrdersActivatePoints API URL : "
				+ processOrdersActivatePointsService.URL);
		log.info("\nPrinting LoyalityPointsService processOrdersActivatePoints API URL : "
				+ processOrdersActivatePointsService.URL);

		System.out.println("\nPrinting LoyalityPointsService processOrdersActivatePoints API Payload : \n\n"
				+ processOrdersActivatePointsService.Payload);
		log.info("\nPrinting LoyalityPointsService processOrdersActivatePoints API Payload : \n\n"
				+ processOrdersActivatePointsService.Payload);

		return new RequestGenerator(processOrdersActivatePointsService);

	}

	public RequestGenerator invokeConfigurationKeyValue(String key, String value) {
		MyntraService configurationKeyValueService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.LOYALITYCONFIGURATION, init.Configurations, new String[] { key, value }, PayloadType.JSON,
				PayloadType.JSON);
		HashMap<String, String> getParam = new HashMap<String, String>();
		getParam.put("Authorization", "Basic bG95YWx0eXVzZXJ+bG95YWx0eXVzZXI6Q29uZmlnbG95YWx0eSQlXiYjQA==");

		System.out.println(
				"\nPrinting LoyalityPointsService configurationKeyValue API URL : " + configurationKeyValueService.URL);
		log.info(
				"\nPrinting LoyalityPointsService configurationKeyValue API URL : " + configurationKeyValueService.URL);

		System.out.println("\nPrinting LoyalityPointsService configurationKeyValue API Payload : \n\n"
				+ configurationKeyValueService.Payload);
		log.info("\nPrinting LoyalityPointsService configurationKeyValue API Payload : \n\n"
				+ configurationKeyValueService.Payload);

		return new RequestGenerator(configurationKeyValueService);

	}

	public RequestGenerator invokeFetchAllConfigurationKeyValue() {
		MyntraService fetchAllConfigurationKeyValueService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.KEYVALUEFETCHALL, init.Configurations);

		System.out.println("\nPrinting LoyalityPointsService fetchAllConfigurationKeyValue API URL : "
				+ fetchAllConfigurationKeyValueService.URL);
		log.info("\nPrinting LoyalityPointsService fetchAllConfigurationKeyValue API URL : "
				+ fetchAllConfigurationKeyValueService.URL);

		System.out.println("\nPrinting LoyalityPointsService fetchAllConfigurationKeyValue API Payload : \n\n"
				+ fetchAllConfigurationKeyValueService.Payload);
		log.info("\nPrinting LoyalityPointsService fetchAllConfigurationKeyValue API Payload : \n\n"
				+ fetchAllConfigurationKeyValueService.Payload);

		return new RequestGenerator(fetchAllConfigurationKeyValueService);

	}

	public RequestGenerator invokeCreateLoyaltyGroup(String brands, String genders, String articleTypes,
			String groupName, String groupDescription, String conversionFactorPoints) {
		MyntraService createLoyaltyGroupService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations,
				new String[] { brands, genders, articleTypes, groupName, groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);

		System.out.println(
				"\nPrinting LoyalityPointsService createLoyaltyGroup API URL : " + createLoyaltyGroupService.URL);
		log.info("\nPrinting LoyalityPointsService createLoyaltyGroup API URL : " + createLoyaltyGroupService.URL);

		System.out.println("\nPrinting LoyalityPointsService createLoyaltyGroup API Payload : \n\n"
				+ createLoyaltyGroupService.Payload);
		log.info("\nPrinting LoyalityPointsService createLoyaltyGroup API Payload : \n\n"
				+ createLoyaltyGroupService.Payload);

		return new RequestGenerator(createLoyaltyGroupService);

	}

	public RequestGenerator invokeDeleteLoyaltyGroup(String groupIdOrName) {
		MyntraService deleteLoyaltyGroupService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DELETELOYALITYGROUP, init.Configurations, PayloadType.JSON, new String[] { groupIdOrName },
				PayloadType.JSON);

		System.out.println(
				"\nPrinting LoyalityPointsService deleteLoyaltyGroup API URL : " + deleteLoyaltyGroupService.URL);
		log.info("\nPrinting LoyalityPointsService deleteLoyaltyGroup API URL : " + deleteLoyaltyGroupService.URL);

		System.out.println("\nPrinting LoyalityPointsService deleteLoyaltyGroup API Payload : \n\n"
				+ deleteLoyaltyGroupService.Payload);
		log.info("\nPrinting LoyalityPointsService deleteLoyaltyGroup API Payload : \n\n"
				+ deleteLoyaltyGroupService.Payload);

		return new RequestGenerator(deleteLoyaltyGroupService);

	}

	public RequestGenerator invokeIndexStyleListLoyaltyGroup(String styleId, String brand, String gender,
			String articleType) {
		MyntraService indexStyleListLoyaltyGroupService = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.INDEXSTYLELISTLOYALITYGROUP, init.Configurations,
				new String[] { styleId, brand, gender, articleType }, PayloadType.JSON, PayloadType.JSON);

		System.out.println("\nPrinting LoyalityPointsService indexStyleListLoyaltyGroup API URL : "
				+ indexStyleListLoyaltyGroupService.URL);
		log.info("\nPrinting LoyalityPointsService indexStyleListLoyaltyGroup API URL : "
				+ indexStyleListLoyaltyGroupService.URL);

		System.out.println("\nPrinting LoyalityPointsService indexStyleListLoyaltyGroup API Payload : \n\n"
				+ indexStyleListLoyaltyGroupService.Payload);
		log.info("\nPrinting LoyalityPointsService indexStyleListLoyaltyGroup API Payload : \n\n"
				+ indexStyleListLoyaltyGroupService.Payload);

		return new RequestGenerator(indexStyleListLoyaltyGroupService);

	}

	/**
	 * Get Loyalty Point Account Info For User
	 * 
	 * @param accountID
	 * @return {@link LoyaltyPointsServiceErrorResponse}
	 * @throws IOException
	 * @throws JAXBException
	 */
	public static LoyaltyPointsUserAccountEntry getLoyaltyPointAccountInfo(String accountID)
			throws IOException, JAXBException {
		Svc service = HttpExecutorService.executeHttpService(Constants.LOYALTY_PATH.GET_LOYALTY_USER_ACCOUNT_INFO,
				new String[] { accountID }, SERVICE_TYPE.LOYALTY_SVC.toString(), HTTPMethods.GET, null, getPnPHeader());
		//System.out.println("Loyalty Service OutPut:- " + service.getResponseBody());
		LoyaltyPointsServiceUserAccountResponse loyaltyPointsServiceUserAccountResponse = (LoyaltyPointsServiceUserAccountResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LoyaltyPointsServiceUserAccountResponse());
		System.out.println(loyaltyPointsServiceUserAccountResponse.toString());
		LoyaltyPointsUserAccountEntry userAccountEntry = loyaltyPointsServiceUserAccountResponse.getUserAccountEntry();
		return userAccountEntry;
	}

	private static HashMap<String, String> getPnPHeader() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		createOrderHeaders.put("Content-Type", "Application/json");
		return createOrderHeaders;
	}

}
