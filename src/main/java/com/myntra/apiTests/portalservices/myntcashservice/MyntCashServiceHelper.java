package com.myntra.apiTests.portalservices.myntcashservice;

import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import org.apache.log4j.Logger;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * @author shankara.c
 *
 */
public class MyntCashServiceHelper 
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(MyntCashServiceHelper.class);
	APIUtilities apiUtil = new APIUtilities();

	public RequestGenerator invokeCheckMyntCashBalance(String emailId)
	{
		MyntraService checkMyntCashBalanceService = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKBALANCE, init.Configurations, PayloadType.JSON,
				new String[]{ emailId }, PayloadType.JSON);
		System.out.println("\nPrinting MyntraCashService checkBalance API URL : "+checkMyntCashBalanceService.URL);
		log.info("\nPrinting MyntraCashService checkBalance API URL : "+checkMyntCashBalanceService.URL);
		
		System.out.println("\nPrinting MyntraCashService checkBalance API Payload : \n\n"+checkMyntCashBalanceService.Payload);
		log.info("\nPrinting MyntraCashService checkBalance API Payload : \n\n"+checkMyntCashBalanceService.Payload);
		
		return new RequestGenerator(checkMyntCashBalanceService);
	}
	
	public RequestGenerator invokeCheckTransactionLogsofUser(String email)
	{
		MyntraService checkTransactionLogsofUserService = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKTRANSACTIONLOGSOFUSER, init.Configurations,
				PayloadType.JSON, new String[]{ email }, PayloadType.JSON);
		System.out.println("\nPrinting MyntraCashService checkTransactionLogsofUser API URL : "+checkTransactionLogsofUserService.URL);
		log.info("\nPrinting MyntraCashService checkTransactionLogsofUser API URL : "+checkTransactionLogsofUserService.URL);
		
		System.out.println("\nPrinting MyntraCashService checkTransactionLogsofUser API Payload : \n\n"+checkTransactionLogsofUserService.Payload);
		log.info("\nPrinting MyntraCashService checkTransactionLogsofUser API Payload : \n\n"+checkTransactionLogsofUserService.Payload);
		
		return new RequestGenerator(checkTransactionLogsofUserService);
	}
	
	public RequestGenerator invokeDebitCashBack(String login, String earnedCreditAmount, String storeCreditAmount, 
			String debitAmount, String description, String businessProcess, String itemType, String itemId)
	{
		MyntraService debitCashBackService = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.DEBITCASHBACK, init.Configurations,
				new String[]{ login, earnedCreditAmount, storeCreditAmount, debitAmount, description, businessProcess, itemType, itemId });
		
		System.out.println("\nPrinting MyntraCashService debitCashBack API URL : "+debitCashBackService.URL);
		log.info("\nPrinting MyntraCashService debitCashBack API URL : "+debitCashBackService.URL);
		
		System.out.println("\nPrinting MyntraCashService debitCashBack API Payload : \n\n"+debitCashBackService.Payload);
		log.info("\nPrinting MyntraCashService debitCashBack API Payload : \n\n"+debitCashBackService.Payload);
		
		HashMap<String, String> debitCashBackHeaders = new HashMap<String, String>();
		debitCashBackHeaders.put("user", login);
		
		return new RequestGenerator(debitCashBackService, debitCashBackHeaders);
		
	}
	
	
	
	public RequestGenerator invokeCreditCashBack(String login, String earnedCreditAmount, String storeCreditAmount, String debitAmount, String description, 
			String businessProcess, String itemType, String itemId)
	{
		MyntraService creditCashBackService = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACK, init.Configurations,
				new String[]{ login, earnedCreditAmount, storeCreditAmount, debitAmount, description, businessProcess, itemType, itemId });
		
		System.out.println("\nPrinting MyntraCashService creditCashBack API URL : "+creditCashBackService.URL);
		log.info("\nPrinting MyntraCashService creditCashBack API URL : "+creditCashBackService.URL);
		
		System.out.println("\nPrinting MyntraCashService creditCashBack API Payload : \n\n"+creditCashBackService.Payload);
		log.info("\nPrinting MyntraCashService creditCashBack API Payload : \n\n"+creditCashBackService.Payload);
		
		HashMap<String, String> creditCashBackHeaders = new HashMap<String, String>();
		creditCashBackHeaders.put("user", login);
		
		return new RequestGenerator(creditCashBackService, creditCashBackHeaders);
		
	}
	
}
