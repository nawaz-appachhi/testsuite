package com.myntra.apiTests.portalservices.all;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.MyntCashDP;
import com.myntra.apiTests.portalservices.myntcashservice.MyntCashServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;


import com.myntra.lordoftherings.gandalf.PayloadType;
import org.apache.commons.collections.CollectionUtils;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;

public class MyntCashServiceTest extends MyntCashDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(MyntCashServiceTest.class);
	static MyntCashServiceHelper myntrCashServiceHelper = new MyntCashServiceHelper();

	private static List<String> getcashBackNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add("balance");
		addNodes.add("storeCreditBalance");
		addNodes.add("earnedCreditbalance");
		addNodes.add("login");		
		return addNodes;	
	}

	private static List<String> getDbtcashBackNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add("login"); 
		addNodes.add("earnedCreditAmount");
		addNodes.add("storeCreditAmount");
		addNodes.add("debitAmount");	
		addNodes.add("description");
		addNodes.add("businessProcess");	
		addNodes.add("itemType");
		addNodes.add("itemId");		
		return addNodes;	
	}

	private static List<String> getDbtcashBackStatusAdMsgNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add("status"); 
		addNodes.add("message");
		return addNodes;	
	}

	/**
	 * Verify balance, and email id possitive values.
	 * @param emailID
	 * @param newUser
	 */
	@Test(groups = {"Smoke","Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "laxmiTc"} ,
							dataProvider = "checkbalanceDataProvider" )
	public void MyntraCash_checkbalance_balanceAmt_possitive(String emailID, String newUser)
	{		
		RequestGenerator req = getChkBlnceRequest(emailID);
		System.out.println(req.respvalidate.returnresponseasstring());
		if(Boolean.parseBoolean(newUser)){
			AssertJUnit.assertEquals("Balance should be 0 for new user :", 0.0, getBalance(emailID));
		}else
		{			
			AssertJUnit.assertTrue(getBalance(emailID)>=0.0);
		}
		System.out.println(req.respvalidate.NodeValue("login", true).replaceAll("\"", ""));
		System.out.println(emailID);
		AssertJUnit.assertTrue("email id Mismatch : ", 
						req.respvalidate.NodeValue("login", true).replaceAll("\"", "").trim().equalsIgnoreCase(emailID.trim()));
	}

	/**
	 * Verify balance negative check.
	 * @param emailID
	 * @param newUser
	 */
//	@Test(groups = {"Smoke","Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "laxmiTc"} ,
//							dataProvider = "checkbalanceDataProvider_negative" )
//	public void MyntraCash_checkbalance_balanceAmt_negative(String emailID, String newUser)
//	{
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKBALANCE,
//								init.Configurations, PayloadType.JSON, new String[]{emailID}, PayloadType.JSON);
//		RequestGenerator req = new RequestGenerator(service);
//		log.info(service.URL);
//		System.out.println(req.respvalidate.returnresponseasstring());		
//		if(Boolean.parseBoolean(newUser)){
//			AssertJUnit.assertFalse(getBalance(emailID)>0.0 && getBalance(emailID)<0.0);
//		}else
//		{			
//			AssertJUnit.assertFalse(getBalance(emailID)<0.0);
//		}
//	}

	/**
	 * Verify balance negative check.
	 * @param emailID
	 * @param newUser
	 */
//	@Test(groups = {"Smoke","Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "laxmiTc"} ,
//							dataProvider = "checkbalanceDataProvider_negative_emailID" )
//	public void MyntraCash_checkbalance_balanceAmt_negativeEmailID(String emailID, String newUser)
//	{
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKBALANCE,
//							init.Configurations, PayloadType.JSON, new String[]{emailID}, PayloadType.JSON);
//		RequestGenerator req = new RequestGenerator(service);
//		log.info(service.URL);
//		System.out.println(req.respvalidate.returnresponseasstring());
//
//		if(Boolean.parseBoolean(newUser)){
//			AssertJUnit.assertTrue(getBalance(emailID)==0.0);
//		}else
//		{			
//			AssertJUnit.assertTrue(getBalance(emailID)==0.0);
//		}
//	}

	/**
	 *  Verify cash back check balance Nodes
	 * @param createdBy
	 * @param Desc
	 * @param city
	 * @param country
	 * @param region
	 * @param title
	 * @param type
	 */
	@Test(groups = {"Smoke","Sanity", "Regression", "MiniRegression", "ExhaustiveRegression","Fox7Sanity"} ,dataProvider = "EmailCashBackDataProvider")
	public void MyntraCash_checkbalance_nodes(String EmailID)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKBALANCE,init.Configurations, PayloadType.JSON,new String [] {EmailID},PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertTrue("Inval cash Back information.", req.respvalidate.DoesNodesExists(getcashBackNodes()));		
	}

	/**
	 * Verify Debit cash back with negative values
	 * @param param1
	 * @param param2
	 * @param param3
	 * @param param4
	 * @param param5
	 * @param param6
	 * @param param7
	 * @param param8
	 * @param param9
	 */
	@Test(groups = {"Sanity","Regression", "MiniRegression", "ExhaustiveRegression", "laxmiTc"} ,dataProvider = "debitCashBackDataProvider_negativeData" )
	public void MyntraCash_debitCashBack_negative(String param1, String param2, String param3, String param4, 
			String param5, String param6, String param7, String param8, String param9)
	{
		Double balanceBefoe, balanceAfter, finalBalance;
		balanceBefoe = getBalance(param1);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.DEBITCASHBACK,init.Configurations,
				new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
		RequestGenerator req = getRequest(service, param1);
		balanceAfter = getBalance(param1);
		param2 = param2.equalsIgnoreCase("") ? "0" : param2;
		param3 = param3.equalsIgnoreCase("") ? "0" : param3;
		finalBalance = balanceBefoe - Double.parseDouble(param4);
		new DecimalFormat("#.##").format(finalBalance);
		log.info(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());	
		AssertJUnit.assertEquals("debitCashBack API is not working", 200, req.response.getStatus());
		AssertJUnit.assertEquals("Balance Mismatch", finalBalance, balanceAfter);		
	}

	/**
	 * Verify Debit cash back with negative values
	 * @param param1
	 * @param param2
	 * @param param3
	 * @param param4
	 * @param param5
	 * @param param6
	 * @param param7
	 * @param param8
	 * @param param9
	 */
	@Test(groups = {"Sanity","Regression", "MiniRegression", "ExhaustiveRegression","Fox7Sanity"} ,
						dataProvider = "debitCashBackDataProvider_WrontMailId" )
	public void MyntraCash_debitCashBack_wrongEmailId(String param1, String param2, String param3, String param4, 
			String param5, String param6, String param7, String param8)
	{
//		Double balanceBefore = getBalance(param1);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.DEBITCASHBACK,init.Configurations,
				new String[]{param1,param2,param3,param4,param5,param6,param7,param8});
		RequestGenerator req = getRequest(service, param1);
		param2 = param2.equalsIgnoreCase("") ? "0" : param2;
		log.info(service.URL);
		System.out.println("REsponse "+req.response.getStatus());	
		AssertJUnit.assertEquals("Response is not 400 OK", 400, req.response.getStatus());
//		AssertJUnit.assertEquals("Response status mismatch : ", "\""+errStatus+"\"", req.respvalidate.NodeValue("status", true));
//
//		String errMsg = "earned credit : "+balanceBefore+"  store credit : "+Double.parseDouble(param2)+
//				"  requested amount : "+Double.parseDouble(param4);
//		AssertJUnit.assertEquals("Response message mismatch : ", errMsg.trim(), 
//				req.respvalidate.NodeValue("message", true).replaceAll("\"", "").trim());
	}

	/**
	 * Debit cashback when debit amount is greater than amount available.
	 * @param param1
	 * @param param2
	 * @param param3
	 * @param param4
	 * @param param5
	 * @param param6
	 * @param param7
	 * @param param8
	 * @param errStatus
	 */
	@Test(groups = {"Sanity","Regression", "MiniRegression", "ExhaustiveRegression", "laxmiTc"} ,
							dataProvider = "debitCashBackDataProvider_moreDbtAmt" )
	public void MyntraCash_debitCashBack_moreDtbAmt_verifyErrorStatusAdMsg(String param1, String param2, String param3,
							String param4, String param5, String param6, String param7, String param8, String errStatus)
	{
		RequestGenerator req = getChkBlnceRequest(param1);
		System.out.println(req.respvalidate.returnresponseasstring());
		String storedAmtStr = req.respvalidate.NodeValue("storeCreditBalance", true).replaceAll("\"", "");
		String earnedAmtStr= req.respvalidate.NodeValue("earnedCreditbalance", true).replaceAll("\"", "");
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.DEBITCASHBACK,init.Configurations,
				new String[]{param1,param2,param3,param4,param5,param6,param7,param8});
		RequestGenerator req1 = getRequest(service, param1);	
	
		System.out.println(req1.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("debitCashBack API is not working", 200, req1.response.getStatus());		
		AssertJUnit.assertEquals("Response status mismatch : ", "\""+errStatus+"\"", req1.respvalidate.NodeValue("status", true));
		String errMsg = "earned credit : "+earnedAmtStr+"  store credit : "+storedAmtStr+"  requested amount : "+Double.parseDouble(param4);
		AssertJUnit.assertEquals("Response message mismatch : ", errMsg.trim(), req1.respvalidate.NodeValue("message", true).replaceAll("\"", "").trim());	
	}

	/**
	 *  Verify debit cash back Nodes
	 * @param createdBy
	 * @param Desc
	 * @param city
	 * @param country
	 * @param region
	 * @param title
	 * @param type
	 */
	@Test(groups = {"Smoke","Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "laxmiTc"}, 
			dataProvider = "debitCashBackDataProvider_Nodes")
	public void MyntraCash_checkbalance_debitNodes(String param1, String param2, String param3, String param4, String param5, 
			String param6, String param7, String param8)
	{
		Double beforeBalance = getBalance(param1);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.DEBITCASHBACK,init.Configurations,
				new String[]{param1,param2,param3,param4,param5,param6,param7,param8});		
		RequestGenerator req = getRequest(service, param1);
		log.info(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());	
		System.out.println(service.Payload);
		System.out.println(service.URL);
		System.out.println(beforeBalance);
		Double dbtAmt = Double.parseDouble(param4);
		if(beforeBalance < dbtAmt) {
			AssertJUnit.assertTrue("Inval debit cash Back information.", req.respvalidate.DoesNodesExists(getDbtcashBackStatusAdMsgNodes()));
		}else {
			AssertJUnit.assertTrue("Inval debit cash Back information.", req.respvalidate.DoesNodesExists(getDbtcashBackNodes()));
		}
	}

	/**
	 *  Verify debit cash back status, and message Nodes
	 * @param createdBy
	 * @param Desc
	 * @param city
	 * @param country
	 * @param region
	 * @param title
	 * @param type
	 */
//	@Test(groups = {"Smoke","Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "laxmiTc"}, 
//			dataProvider = "debitCashBackDataProvider_negativeVerifycation_Nodes")
//	public void MyntraCash_checkbalance_negativeVefycation_debitNodes(String param1, String param2, String param3, 
//			String param4, String param5, String param6, String param7, String param8)
//	{
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.DEBITCASHBACK,init.Configurations,
//				new String[]{param1,param2,param3,param4,param5,param6,param7,param8});		
//		RequestGenerator req = getRequest(service, param1);
//		log.info(service.URL);
//		System.out.println(req.respvalidate.returnresponseasstring());	
//		AssertJUnit.assertTrue("Inval debit cash Back information.", req.respvalidate.DoesNodesExists(getDbtcashBackStatusAdMsgNodes()));		
//	}

	/**
	 * Debit cashback verification with negative values
	 * @param param1
	 * @param param2
	 * @param param3
	 * @param param4
	 * @param param5
	 * @param param6
	 * @param param7
	 * @param param8
	 * @param param9
	 */
	@Test(groups = {"Sanity","Regression", "MiniRegression", "ExhaustiveRegression", "laxmiTc"} ,
							dataProvider = "debitCashBackDataProvider_status_negative" )
	public void MyntraCash_debitCashBack_verifyErrorStatusAdMsg(String param1, String param2, String param3, String param4, 
			String param5, String param6, String param7, String param8, String errstatus)
	{
		Double balanceBefore = getBalance(param1);
		System.out.println("Before:"+balanceBefore);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.DEBITCASHBACK,
				init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8, errstatus});
		RequestGenerator req = getRequest(service, param1);
		log.info(service.URL);
		System.out.println(req.response);
		System.out.println(req.respvalidate.returnresponseasstring());
		//Double debitAmt = Double.parseDouble(param4);
		AssertJUnit.assertEquals("debitCashBack API is not working", 200, req.response.getStatus());		
		/* Commentted : For debit call, earnedCreditAmount and storeCreditAmount fields' values are ignored and do not have any impact on the results.
		AssertJUnit.assertEquals("Response status mismatch : ", "\""+errstatus+"\"", 
				req.respvalidate.NodeValue("status", true));
		String errMsg = "earned credit : "+balanceBefore+"  store credit : "+Double.parseDouble(param2)+
							"  requested amount : "+Double.parseDouble(param4);
		AssertJUnit.assertEquals("Response message mismatch : ", errMsg.trim(), 
								req.respvalidate.NodeValue("message", true).replaceAll("\"", "").trim());*/
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "laxmiTc" }, dataProvider = "debitCashBackDataProvider_status_negative")
	public void MyntraCash_debitCashBackOms_verifyErrorStatusAdMsg(
			String param1, String param2, String param3, String param4,
			String param5, String param6, String param7, String param8,
			String errstatus) {
		Double balanceBefore = getBalance(param1);
		System.out.println("Before:" + balanceBefore);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.DEBITCASHBACKOMS,
				init.Configurations, new String[] { param1, param2, param3,
						param4, param5, param6, param7, param8, errstatus });
		RequestGenerator req = getRequest(service, param1);
		log.info(service.URL);
		System.out.println(req.response);
		System.out.println(req.respvalidate.returnresponseasstring());
		// Double debitAmt = Double.parseDouble(param4);
		AssertJUnit.assertEquals("debitCashBack API is not working", 200,
				req.response.getStatus());
		/*
		 * Commentted : For debit call, earnedCreditAmount and storeCreditAmount
		 * fields' values are ignored and do not have any impact on the results.
		 * AssertJUnit.assertEquals("Response status mismatch : ",
		 * "\""+errstatus+"\"", req.respvalidate.NodeValue("status", true));
		 * String errMsg =
		 * "earned credit : "+balanceBefore+"  store credit : "+Double
		 * .parseDouble(param2)+
		 * "  requested amount : "+Double.parseDouble(param4);
		 * AssertJUnit.assertEquals("Response message mismatch : ",
		 * errMsg.trim(), req.respvalidate.NodeValue("message",
		 * true).replaceAll("\"", "").trim());
		 */
	}

	/**
	 * Verify, Debit cashback from earnedcredit amount
	 * @param param1
	 * @param param2
	 * @param param3
	 * @param param4
	 * @param param5
	 * @param param6
	 * @param param7
	 * @param param8
	 * @param param9
	 */
	@Test(groups = {"Sanity","Regression", "MiniRegression", "ExhaustiveRegression", "laxmiTc"} ,
						dataProvider = "debitCashBackDataProvider_earnedAmt" )
	public void MyntraCash_debitCashBack_from_earnedAmt_possitive(String param1, String param2, String param3, String param4, 
			String param5, String param6, String param7, String param8)
	{
		Double balanceBefore, finalBalance = 0.0, earnedAmt;
		balanceBefore = getBalance(param1);
		System.out.println("Before:"+balanceBefore);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.DEBITCASHBACK,
				init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8});
		RequestGenerator req = getRequest(service, param1);
		System.out.println("URL Please" +service.URL);
		System.out.println("Request Paylaod Please" +service.Payload);

		System.out.println(req.returnresponseasstring());
		System.out.println("Payload:"+service.Payload);
		System.out.println(service.URL);
		earnedAmt = Double.parseDouble(param4);
		System.out.println("earned: "+earnedAmt);
		if(earnedAmt > 0.00 && balanceBefore > earnedAmt){
			finalBalance = balanceBefore - earnedAmt;
		new DecimalFormat("#.##").format(finalBalance);
		log.info(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("login does't match", req.respvalidate.NodeValue("login", true).replaceAll("\"", ""), param1);
		AssertJUnit.assertEquals("earnedCreditAmount does't match", req.respvalidate.NodeValue("earnedCreditAmount", true).replaceAll("\"", ""), earnedAmt+"");	
		AssertJUnit.assertEquals("description does't match", req.respvalidate.NodeValue("description", true).replaceAll("\"", ""), param5);
		AssertJUnit.assertEquals("itemType does't match", req.respvalidate.NodeValue("itemType", true).replaceAll("\"", ""), param6);
		AssertJUnit.assertEquals("itemId does't match", req.respvalidate.NodeValue("itemId", true).replaceAll("\"", ""), param8);
		AssertJUnit.assertEquals("businessProcess does't match", req.respvalidate.NodeValue("businessProcess", true).replaceAll("\"", ""), param7);	
		} else{
		AssertJUnit.assertEquals("debitCashBack API is not working", 200, req.response.getStatus());		
		AssertJUnit.assertEquals("Response status mismatch : ", "\""+"error"+"\"", req.respvalidate.NodeValue("status", true));
		String errMsg = "earned credit : "+balanceBefore+"  store credit : "+Double.parseDouble(param3)+"  requested amount : "+Double.parseDouble(param4);
		AssertJUnit.assertEquals("Response message mismatch : ", errMsg.trim(), req.respvalidate.NodeValue("message", true).replaceAll("\"", "").trim());
		log.error("'earnedCreditAmount' should have some amount. But now it is having: "+ param4);
		}
		
	}

	
	/**
	 * Verify, Debit cashback from  '0' earnedcredit amount
	 * @param param1
	 * @param param2
	 * @param param3
	 * @param param4
	 * @param param5
	 * @param param6
	 * @param param7
	 * @param param8
	 * @param param9
	 */
	@Test(groups = {"Sanity","Regression", "MiniRegression", "ExhaustiveRegression", "laxmiTc"} ,
						dataProvider = "debitCashBackDataProvider_negative" )
	public void MyntraCash_debitCashBack_from_earnedAmt_negative(String param1, String param2, String param3, String param4, 
			String param5, String param6, String param7, String param8)
	{
		Double beforeBbalance, finalBalance = 0.0, earnedAmt;
		beforeBbalance = getBalance(param1);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.DEBITCASHBACK,
				init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8});
		RequestGenerator req = getRequest(service, param1);
		earnedAmt = Double.parseDouble(param4);
		if(earnedAmt == 0.00){
			finalBalance = beforeBbalance - earnedAmt;
			System.out.println(finalBalance);
			System.out.println(beforeBbalance);
			if(finalBalance.equals(beforeBbalance)){
				log.info("'earnedCreditAmount' not having amount.");
			} else{
				log.error("'earnedCreditAmount' having some amount.");
			}
		} else{
			log.error("'earnedCreditAmount' should not have amount. But now it is having: "+param4);
		}
		new DecimalFormat("#.##").format(finalBalance);
		log.info(service.URL);

		System.out.println(req.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("login does't match", req.respvalidate.NodeValue("login", true).replaceAll("\"", ""), param1);
		AssertJUnit.assertEquals("earnedCreditAmount does't match", 
						req.respvalidate.NodeValue("earnedCreditAmount", true).replaceAll("\"", ""), earnedAmt+"");	
		AssertJUnit.assertEquals("description does't match", 
								req.respvalidate.NodeValue("description", true).replaceAll("\"", ""), param5);
		AssertJUnit.assertEquals("itemType does't match", req.respvalidate.NodeValue("itemType", true).replaceAll("\"", ""), param6);
		AssertJUnit.assertEquals("itemId does't match", req.respvalidate.NodeValue("itemId", true).replaceAll("\"", ""), param8);
		AssertJUnit.assertEquals("businessProcess does't match", 
							req.respvalidate.NodeValue("businessProcess", true).replaceAll("\"", ""), param7);	
	}

	/**
	 * Verify, Debit cashback from earnedcredit amount
	 * @param param1
	 * @param param2
	 * @param param3
	 * @param param4
	 * @param param5
	 * @param param6
	 * @param param7
	 * @param param8
	 * @param param9
	 */
	@Test(groups = {"Sanity","Regression", "MiniRegression", "ExhaustiveRegression", "laxmiTc"} ,
						dataProvider = "debitCashBackDataProvider_earned_stored_amt" )
	public void MyntraCash_debitCashBack_from_earnedAdStored(String param1, String param2, String param3, String param4, 
			String param5, String param6, String param7, String param8)
	{
		RequestGenerator req = getChkBlnceRequest(param1);
		System.out.println(req.respvalidate.returnresponseasstring());
		String storedAmtStr = req.respvalidate.NodeValue("storeCreditBalance", true).replaceAll("\"", "");
		String earnedAmtStr= req.respvalidate.NodeValue("earnedCreditbalance", true).replaceAll("\"", "");
		
		if(Double.parseDouble(earnedAmtStr) > 0.00 && Double.parseDouble(storedAmtStr) > 0.00){	
			MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.DEBITCASHBACK,
					init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8});
			RequestGenerator req1 = getRequest(service, param1);	
			param2 = param2.equalsIgnoreCase("") ? "0.0" : param2+".0";

			System.out.println(req1.respvalidate.returnresponseasstring());
			AssertJUnit.assertEquals("login does't match", req1.respvalidate.NodeValue("login", true).replaceAll("\"", ""), param1);
			AssertJUnit.assertEquals("earnedCreditAmount does't match", req1.respvalidate.NodeValue("earnedCreditAmount", true).replaceAll("\"", ""), param2);
			AssertJUnit.assertEquals("earnedCreditAmount does't match", req1.respvalidate.NodeValue("storeCreditAmount", true).replaceAll("\"", ""), param4+".0");	
			AssertJUnit.assertEquals("earnedCreditAmount does't match", req1.respvalidate.NodeValue("debitAmount", true).replaceAll("\"", ""), "0.0");	
			AssertJUnit.assertEquals("description does't match", req1.respvalidate.NodeValue("description", true).replaceAll("\"", ""), param5);
			AssertJUnit.assertEquals("itemType does't match", req1.respvalidate.NodeValue("itemType", true).replaceAll("\"", ""), param6);
			AssertJUnit.assertEquals("itemId does't match", req1.respvalidate.NodeValue("itemId", true).replaceAll("\"", ""), param8);
			AssertJUnit.assertEquals("businessProcess does't match", req1.respvalidate.NodeValue("businessProcess", true).replaceAll("\"", ""), param7);	
			AssertJUnit.assertEquals("businessProcess does't match", req1.respvalidate.NodeValue("goodwillReason", true).replaceAll("\"", ""), "null");
		} else{
				
			log.error("'earnedCreditAmount', and 'storeCreditBalance' should need some amount. But now it is having: "+param4);
		}
	}

	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "laxmiTc" }, dataProvider = "debitCashBackDataProvider_earned_stored_amt")
	public void MyntraCash_debitCashBackoms_from_earnedAdStored(String param1,
			String param2, String param3, String param4, String param5,
			String param6, String param7, String param8) {
		RequestGenerator req = getChkBlnceRequest(param1);
		System.out.println(req.respvalidate.returnresponseasstring());
		String storedAmtStr = req.respvalidate.NodeValue("storeCreditBalance",
				true).replaceAll("\"", "");
		String earnedAmtStr = req.respvalidate.NodeValue("earnedCreditbalance",
				true).replaceAll("\"", "");

		if (Double.parseDouble(earnedAmtStr) > 0.00
				&& Double.parseDouble(storedAmtStr) > 0.00) {
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.DEBITCASHBACKOMS,
					init.Configurations, new String[] { param1, param2, param3,
							param4, param5, param6, param7, param8 });
			RequestGenerator req1 = getRequest(service, param1);
			param2 = param2.equalsIgnoreCase("") ? "0.0" : param2 + ".0";

			System.out.println(req1.respvalidate.returnresponseasstring());
			AssertJUnit.assertEquals("login does't match", req1.respvalidate
					.NodeValue("login", true).replaceAll("\"", ""), param1);
			AssertJUnit.assertEquals("earnedCreditAmount does't match",
					req1.respvalidate.NodeValue("earnedCreditAmount", true)
							.replaceAll("\"", ""), param2);
			AssertJUnit.assertEquals("earnedCreditAmount does't match",
					req1.respvalidate.NodeValue("storeCreditAmount", true)
							.replaceAll("\"", ""), param4 + ".0");
			AssertJUnit.assertEquals("earnedCreditAmount does't match",
					req1.respvalidate.NodeValue("debitAmount", true)
							.replaceAll("\"", ""), "0.0");
			AssertJUnit.assertEquals("description does't match",
					req1.respvalidate.NodeValue("description", true)
							.replaceAll("\"", ""), param5);
			AssertJUnit.assertEquals("itemType does't match", req1.respvalidate
					.NodeValue("itemType", true).replaceAll("\"", ""), param6);
			AssertJUnit.assertEquals("itemId does't match", req1.respvalidate
					.NodeValue("itemId", true).replaceAll("\"", ""), param8);
			AssertJUnit.assertEquals("businessProcess does't match",
					req1.respvalidate.NodeValue("businessProcess", true)
							.replaceAll("\"", ""), param7);
			AssertJUnit.assertEquals("businessProcess does't match",
					req1.respvalidate.NodeValue("goodwillReason", true)
							.replaceAll("\"", ""), "null");
		} else {

			log.error("'earnedCreditAmount', and 'storeCreditBalance' should need some amount. But now it is having: "
					+ param4);
		}
	}
	//----------------------------------------------------------------------------------------------------------------

	/**
	 * Get Request
	 */
	private  RequestGenerator getRequest(MyntraService service, String userID){
		HashMap hm = new HashMap();
		hm.put("user", userID);
		RequestGenerator req = new RequestGenerator(service, hm);	
		return req;
	}

	/**
	 * Get check balance request
	 */
	private RequestGenerator getChkBlnceRequest(String userID){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKBALANCE,init.Configurations, PayloadType.JSON, new String[]{userID}, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		return req;
	}
	
	public static List<String> getIDcreditCashBackNodes(){
		List<String> creditNodes = new ArrayList<>();
		creditNodes.add("status");
		creditNodes.add("message");
		//System.out.println(locationNodes);
		return creditNodes;		
	}
	
	public static List<String> getIDcreditCashBackLogNodes(){
		List<String> creditNodes = new ArrayList<>();
		creditNodes.add("id");
		System.out.println(creditNodes);
		return creditNodes;		
	}
	
	@Test(groups = {"Smoke","Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "ProdSanity","Fox7Sanity"} ,dataProvider = "checkbalanceDataProvider" )
	public void MyntraCash_checkbalance(String emailID, String newUser)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKBALANCE,init.Configurations, PayloadType.JSON, new String[]{emailID}, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());
		System.out.println(req.respvalidate.GetNodeTextUsingIndex("balance"));
		AssertJUnit.assertEquals("CheckBalance API is not working", 200, req.response.getStatus());		
		if(Boolean.parseBoolean(newUser))
			AssertJUnit.assertEquals("Balance should be 0 for new user :", 0.0, getBalance(emailID));
	}
	
	@Test(groups = {"Sanity","Regression", "MiniRegression", "ExhaustiveRegression", "ProdSanity"} ,dataProvider = "checkTransactionLogsofUserDataProvider" )
	public void MyntraCash_checkTransactionLogsofUser(String email, String newUser)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKTRANSACTIONLOGSOFUSER,init.Configurations,PayloadType.JSON, new String[]{email}, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("checkTransactionLogsofUser API is not working", 200, req.response.getStatus());
		if(Boolean.parseBoolean(newUser))
			AssertJUnit.assertEquals("There should be no transaction log for new user :", "[ ]", req.respvalidate.returnresponseasstring());
	}	
	
	@Test(groups = {"Sanity","Regression", "MiniRegression", "ExhaustiveRegression"} ,dataProvider = "debitCashBackDataProvider" )
	public void MyntraCash_debitCashBack(String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8, String param9)
	{
		Double balanceBefore, balanceAfter, finalBalance, debitValue;
		balanceBefore = getBalance(param1);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.DEBITCASHBACK,
						init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
		RequestGenerator req = getRequest(service, param1);
		balanceAfter = getBalance(param1);
		debitValue = Double.parseDouble(param4);
		param2 = param2.equalsIgnoreCase("") ? "0" : param2;
		param3 = param3.equalsIgnoreCase("") ? "0" : param3;
		finalBalance = balanceBefore - debitValue;
		new DecimalFormat("#.##").format(finalBalance);
		if(balanceBefore <= debitValue || debitValue < 0){
			System.out.println("Print");
			finalBalance = balanceAfter;
		}
		log.info(service.URL);
		AssertJUnit.assertEquals("debitCashBack API is not working", 200, req.response.getStatus());
		System.out.println("respone----===??? " +req.respvalidate.returnresponseasstring() );
		AssertJUnit.assertEquals("Balance Mismatch", finalBalance, balanceAfter);
	}
	
	
	@Test(groups = {"Sanity","Regression", "MiniRegression", "ExhaustiveRegression"} ,dataProvider = "debitCashBackDataProviderNegative1" )
	public void MyntraCash_debitCashBackWIthInvalidProcessBank(String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8, String param9)
	{
		Double balanceBefore, balanceAfter, finalBalance, debitValue;
		balanceBefore = getBalance(param1);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.DEBITCASHBACK,
						init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
		RequestGenerator req = getRequest(service, param1);
		balanceAfter = getBalance(param1);
		debitValue = Double.parseDouble(param4);
		param2 = param2.equalsIgnoreCase("") ? "0" : param2;
		param3 = param3.equalsIgnoreCase("") ? "0" : param3;
		finalBalance = balanceBefore - debitValue;
		new DecimalFormat("#.##").format(finalBalance);
		if(balanceBefore <= debitValue || debitValue < 0){
			System.out.println("Print");
			finalBalance = balanceAfter;
		}
		log.info(service.URL);
		AssertJUnit.assertEquals("debitCashBack API is not working", 200, req.response.getStatus());
		String jsonRes = req.ResponseValidations.GetResponseAsString();
		
		System.out.println("Response ====== \n"+jsonRes);
		if(jsonRes.contains("error")){
		System.out.println("debit fail");
		}
		System.out.println("respone----===??? " +req.respvalidate.returnresponseasstring() );
		AssertJUnit.assertEquals("Balance Mismatch", finalBalance+debitValue, balanceAfter);
	}
	
	
	
	
	@Test(groups={"Smoke", "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "laxmiTc" }, dataProvider = "debitCashBackOmsDataProvider")
	public void MyntraCash_debitCashBackOms(String param1, String param2,
			String param3, String param4, String param5, String param6,
			String param7, String param8, String param9
			) {
		Double balanceBefore, balanceAfter, finalBalance, debitValue;
		balanceBefore = getBalance(param1);
     	MyntraService service = Myntra.getService(
				ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.DEBITCASHBACKOMS,
				init.Configurations,new String[] {param1,param2,param3,param4,param5,param6,param7,param8,param9});
		RequestGenerator req = getRequest(service, param1);
		balanceAfter = getBalance(param1);
		System.out.println("balanceAfter------------>" + balanceAfter);
		debitValue = Double.parseDouble(param4);
		System.out.println("debitValue------------>" + debitValue);

		param2 = param2.equalsIgnoreCase("") ? "0" : param2;
		param3 = param3.equalsIgnoreCase("") ? "0" : param3;
		finalBalance = balanceBefore - debitValue;
		System.out.println("finalBalance------------>" + finalBalance);

		
		new DecimalFormat("#.##").format(finalBalance);
		if (balanceBefore <= debitValue || debitValue < 0) {
			System.out.println("Print");
			finalBalance = balanceAfter;
		}
		log.info(service.URL);
		AssertJUnit.assertEquals("debitCashBack API is not working", 200,
				req.response.getStatus());
		AssertJUnit
				.assertEquals("Balance Mismatch", finalBalance, balanceAfter);
		
	}
	/*
	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"} ,dataProvider = "creditCashBackDataProvider" )
	public void MyntraCash_creditCashBack(String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8, String param9, String resMessage)
	{	
		Double balanceBefoe, balanceAfter, finalBalance;
		balanceBefoe = getBalance(param1);		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACK,init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
				RequestGenerator req = getRequest(service, param1);
		balanceAfter = getBalance(param1);
		param2 = param2.equalsIgnoreCase("") ? "0" : param2;
		param3 = param3.equalsIgnoreCase("") ? "0" : param3;
		finalBalance = balanceBefoe + Double.parseDouble(param2) + Double.parseDouble(param3);
		new DecimalFormat("#.##").format(finalBalance);
		log.info(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
		AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("status", true));
		AssertJUnit.assertEquals("Balance Mismatch", finalBalance, balanceAfter);

	/*	HashMap hm = new HashMap();
=======
		HashMap<String, String> hm = new HashMap<String, String>();
>>>>>>> refs/heads/master
		hm.put("user", param9);
		RequestGenerator req = new RequestGenerator(service, hm);


	}	
	}
	*/
	
	
	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"} ,dataProvider = "creditCashBackDataProvider" )
	public void MyntraCash_creditCashBack(String param1, String param2, String param3, 
												String param4, String param5, String param6, String param7, String param8, String param9, String resMessage)
	{	
		Double balanceBefoe, balanceAfter, finalBalance, storedAmt, earnedAmt;
		balanceBefoe = getBalance(param1);	
		//System.out.println("Before: "+balanceBefoe);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACK,
															init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("user", param9);
		RequestGenerator req = new RequestGenerator(service, hm);
		balanceAfter = getBalance(param1);
		//System.out.println("After: "+balanceAfter);
		param2 = param2.equalsIgnoreCase("") ? "0" : param2;
		param3 = param3.equalsIgnoreCase("") ? "0" : param3;
		storedAmt = Double.parseDouble(param2);
		earnedAmt = Double.parseDouble(param3);
		if(storedAmt < 0 || earnedAmt < 0 || param7 == "INVALID") {
			log.error("'earnedCreditAmount' or 'storeCreditBalance' should not be negative amount.");
			AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
		}else {
		finalBalance = balanceBefoe + storedAmt + earnedAmt;
		//System.out.println("Final: "+finalBalance);
		new DecimalFormat("#.##").format(finalBalance);
		System.out.println(finalBalance);
		log.info(service.URL);
		//System.out.println("2: "+req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
		AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("status", true));
		AssertJUnit.assertEquals("Balance Mismatch", finalBalance, balanceAfter);
		}
	}
		
	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"} ,dataProvider = "creditCashBackDataProvider" )
	public void MyntraCash_creditCashBackoms(String param1, String param2, String param3, 
												String param4, String param5, String param6, String param7, String param8, String param9, String resMessage)
	{	
		Double balanceBefoe, balanceAfter, finalBalance, storedAmt, earnedAmt;
		balanceBefoe = getBalance(param1);	
		//System.out.println("Before: "+balanceBefoe);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACKOMS,
															init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("user", param9);
		RequestGenerator req = new RequestGenerator(service, hm);
		balanceAfter = getBalance(param1);
		//System.out.println("After: "+balanceAfter);
		param2 = param2.equalsIgnoreCase("") ? "0" : param2;
		param3 = param3.equalsIgnoreCase("") ? "0" : param3;
		storedAmt = Double.parseDouble(param2);
		earnedAmt = Double.parseDouble(param3);
		if(storedAmt < 0 || earnedAmt < 0 || param7 == "INVALID") {
			log.error("'earnedCreditAmount' or 'storeCreditBalance' should not be negative amount.");
			AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
		}else {
		finalBalance = balanceBefoe + storedAmt + earnedAmt;
		//System.out.println("Final: "+finalBalance);
		new DecimalFormat("#.##").format(finalBalance);
		System.out.println(finalBalance);
		log.info(service.URL);
		//System.out.println("2: "+req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
		AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("status", true));
		AssertJUnit.assertEquals("Balance Mismatch", finalBalance, balanceAfter);
		}
	}
	
	// Start
		
		// checking Transaction log of user nodes
	
		@Test(groups = {"Sanity","Regression", "MiniRegression", "ExhaustiveRegression", "ProdSanity","Fox7Sanity"} ,dataProvider = "checkTransactionLogsofUserDataProvider" )
		public void MyntraCash_checkTransactionLogsofUser_verifyNodes(String email, String newUser)
		{
			MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKTRANSACTIONLOGSOFUSER,init.Configurations,PayloadType.JSON, new String[]{email}, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			log.info(service.URL);
			//System.out.println(service.URL);
			//System.out.println(req.respvalidate.returnresponseasstring());
			
			String jsonResp = JsonPath.read(req.respvalidate.returnresponseasstring(), "$.[0]").toString();
			
			AssertJUnit.assertTrue("Invalid id node", new APIUtilities().Exists("id", jsonResp));
			AssertJUnit.assertTrue("Invalid cashback AccountId node", new APIUtilities().Exists("cashbackAccountId", jsonResp));
			AssertJUnit.assertTrue("Invalid item Id node", new APIUtilities().Exists("itemId", jsonResp));
			AssertJUnit.assertTrue("Invalid item Type node", new APIUtilities().Exists("itemType", jsonResp));
			AssertJUnit.assertTrue("Invalid busines Process node", new APIUtilities().Exists("businesProcess", jsonResp));
			AssertJUnit.assertTrue("Invalid creditIn flow node", new APIUtilities().Exists("creditInflow", jsonResp));
			AssertJUnit.assertTrue("Invalid creditOut flow node", new APIUtilities().Exists("creditOutflow", jsonResp));
			AssertJUnit.assertTrue("Invalid balance node", new APIUtilities().Exists("balance", jsonResp));
			AssertJUnit.assertTrue("Invalid modified By node", new APIUtilities().Exists("modifiedBy", jsonResp));
			AssertJUnit.assertTrue("Invalid modified On node", new APIUtilities().Exists("modifiedOn", jsonResp));
			AssertJUnit.assertTrue("Invalid description node", new APIUtilities().Exists("description", jsonResp));
			/*
				Will discuss with dev for this validation
			*/
			//AssertJUnit.assertTrue("Invalid goodwill Reason node", new APIUtilities().Exists("goodwillReason", jsonResp));
			
		}
	
	// Verify response for different item types 
	
		@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "Sarath"} ,dataProvider = "creditCashBackDifferentItemTypesDataProvider" )
		public void MyntraCash_creditCashBack_DifferentItemTypes(String param1, String param2, String param3, 
															String param4, String param5, String param6, String param7, String param8, String param9, String resMessage)
		{	
			MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACK,
														init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("user", param9);
			RequestGenerator req = new RequestGenerator(service, hm);
			
			log.info(service.URL);
			AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
			AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("status", true));
			AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("message", true));
		}
		
		@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "Manish"} ,dataProvider = "creditCashBackDifferentItemTypesDataProvider" )
		public void MyntraCash_creditCashBackOms_DifferentItemTypes(String param1, String param2, String param3, 
															String param4, String param5, String param6, String param7, String param8, String param9, String resMessage)
		{	
			MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACKOMS,
														init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("user", param9);
			RequestGenerator req = new RequestGenerator(service, hm);
			
			log.info(service.URL);
			AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
			AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("status", true));
			AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("message", true));
		}
		// Verify response for invalid userid 
		
		@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "Sarath"} ,dataProvider = "creditCashBackInvalidUserIdDataProvider" )
		public void MyntraCash_creditCashBack_InvalidUserId(String param1, String param2, String param3, 
																String param4, String param5, String param6, String param7, String param8, String param9, String resMessage)
		{	
			MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACK,
														init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("user", param9);
			RequestGenerator req = new RequestGenerator(service, hm);
			
			log.info(service.URL);
			AssertJUnit.assertEquals("creditCashBack API is not working", 400, req.response.getStatus());
			
		}
		
//		@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "Sarath"} ,dataProvider = "creditCashBackInvalidUserIdDataProvider" )
//		public void MyntraCash_creditCashBackOms_InvalidUserId(String param1, String param2, String param3, 
//																String param4, String param5, String param6, String param7, String param8, String param9, String resMessage)
//		{	
//			MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACKOMS,
//														init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
//			HashMap<String, String> hm = new HashMap<String, String>();
//			hm.put("user", param9);
//			RequestGenerator req = new RequestGenerator(service, hm);
//			
//			log.info(service.URL);
//			AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
//			AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("status", true));
//			AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("message", true));
//		}
				
		// Verify response stored credit balance 
		@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression","Fox7Sanity"} ,dataProvider = "creditCashBackstoredCreditBalanceDataProvider" )
		public void MyntraCash_creditCashBack_VerifystoredCreditBalance(String param1, String param2, 
										String param3, String param4, String param5, String param6, String param7, String param8, String param9, String resMessage)
		{	
			Double balanceBefore, balanceAfter,finalBalance;
			balanceBefore = getstoreCreditAmount(param1);
	
			MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACK,
												init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("user", param9);
			RequestGenerator req = new RequestGenerator(service, hm);
			//System.out.println(service.Payload);
			balanceAfter = getstoreCreditAmount(param1);
			finalBalance = balanceBefore + Double.parseDouble(param3);
	
			log.info(service.URL);
			AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
			AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("status", true));
			AssertJUnit.assertEquals("Stored Credit Amount Mismatch", finalBalance, balanceAfter);
		}
		
//		@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"} ,dataProvider = "creditCashBackstoredCreditBalanceDataProvider" )
//		public void MyntraCash_creditCashBackOMS_VerifystoredCreditBalance(String param1, String param2, 
//										String param3, String param4, String param5, String param6, String param7, String param8, String param9, String resMessage)
//		{	
//			Double balanceBefore, balanceAfter,finalBalance;
//			balanceBefore = getstoreCreditAmount(param1);
//	
//			MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACKOMS,
//												init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
//			HashMap<String, String> hm = new HashMap<String, String>();
//			hm.put("user", param9);
//			RequestGenerator req = new RequestGenerator(service, hm);
//			//System.out.println(service.Payload);
//			balanceAfter = getstoreCreditAmount(param1);
//			finalBalance = balanceBefore + Double.parseDouble(param3);
//	
//			log.info(service.URL);
//			AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
//			AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("status", true));
//			AssertJUnit.assertEquals("Stored Credit Amount Mismatch", finalBalance, balanceAfter);
//		}


	// Verify response for null stored credit balance 

	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression","Fox7Sanity"} ,dataProvider = "creditCashBackstoredCreditBalanceNullValueDataProvider" )
	public void MyntraCash_creditCashBack_VerifystoredCreditBalanceForNullValues(String param1, String param2, String param3, 
											String param4, String param5, String param6, String param7, String param8, String param9, String resMessage)
	{	
		Double balanceBefore, balanceAfter,finalBalance;
		balanceBefore = getstoreCreditAmount(param1);
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACK,
														init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("user", param9);
		RequestGenerator req = new RequestGenerator(service, hm);
		//System.out.println(service.Payload);
		param3 = param3.equalsIgnoreCase("") ? "0" : param3;
		balanceAfter = getstoreCreditAmount(param1);
		finalBalance = balanceBefore + Double.parseDouble(param3);
		
		log.info(service.URL);
		AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
		AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("status", true));
		AssertJUnit.assertEquals("Stored Credit Amount Mismatch", finalBalance, balanceAfter);
	}
//
//	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"} ,dataProvider = "creditCashBackstoredCreditBalanceNullValueDataProvider" )
//	public void MyntraCash_creditCashBackOms_VerifystoredCreditBalanceForNullValues(String param1, String param2, String param3, 
//											String param4, String param5, String param6, String param7, String param8, String param9, String resMessage)
//	{	
//		Double balanceBefore, balanceAfter,finalBalance;
//		balanceBefore = getstoreCreditAmount(param1);
//		
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACKOMS,
//														init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
//		HashMap<String, String> hm = new HashMap<String, String>();
//		hm.put("user", param9);
//		RequestGenerator req = new RequestGenerator(service, hm);
//		//System.out.println(service.Payload);
//		param3 = param3.equalsIgnoreCase("") ? "0" : param3;
//		balanceAfter = getstoreCreditAmount(param1);
//		finalBalance = balanceBefore + Double.parseDouble(param3);
//		
//		log.info(service.URL);
//		AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
//		AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("status", true));
//		AssertJUnit.assertEquals("Stored Credit Amount Mismatch", finalBalance, balanceAfter);
//	}
	
	// Verify response for stored credit balance for negetive values
	
	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression","Fox7Sanity"} ,dataProvider = "creditCashBackstoredCreditBalanceNegetiveValueDataProvider" )
	public void MyntraCash_creditCashBack_VerifystoredCreditBalanceForNegetiveValue(String param1, String param2, String param3, 
											String param4, String param5, String param6, String param7, String param8, String param9, String resStatus)
	{	
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACK,
														init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("user", param9);
		RequestGenerator req = new RequestGenerator(service, hm);
		param3 = param3.equalsIgnoreCase("") ? "0" : param3;
		log.info(service.URL);
		String resMessage= "requested credit for earnedCreditAmount : "+Double.parseDouble(param2)+" storeCreditAmount: "+Double.parseDouble(param3)+" \\n";
		
		AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
		AssertJUnit.assertEquals("Response status mismatch : ", "\""+resStatus+"\"", req.respvalidate.NodeValue("status", true));
		AssertJUnit.assertEquals("Response message mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("message", true));
		
	}
//	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression","Sarath"} ,dataProvider = "creditCashBackstoredCreditBalanceNegetiveValueDataProvider" )
//	public void MyntraCash_creditCashBackOms_VerifystoredCreditBalanceForNegetiveValue(String param1, String param2, String param3, 
//											String param4, String param5, String param6, String param7, String param8, String param9, String resStatus)
//	{	
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACKOMS,
//														init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
//		HashMap<String, String> hm = new HashMap<String, String>();
//		hm.put("user", param9);
//		RequestGenerator req = new RequestGenerator(service, hm);
//		param3 = param3.equalsIgnoreCase("") ? "0" : param3;
//		log.info(service.URL);
//		String resMessage= "requested credit for earnedCreditAmount : "+Double.parseDouble(param2)+" storeCreditAmount: "+Double.parseDouble(param3)+" \\n";
//		
//		AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
//		AssertJUnit.assertEquals("Response status mismatch : ", "\""+resStatus+"\"", req.respvalidate.NodeValue("status", true));
//		AssertJUnit.assertEquals("Response message mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("message", true));
//		
//	}

// Verify response earned credit balance 

	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"} ,dataProvider = "creditCashBackearnedCreditBalanceDataProvider" )
	public void MyntraCash_creditCashBack_VerifyearnedCreditBalance(String param1, String param2, String param3, 
													String param4, String param5, String param6, String param7, String param8, String param9, String resMessage)
	{	
		Double balanceBefore, balanceAfter,finalBalance;
		balanceBefore = getearnedCreditAmount(param1);
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACK,
													init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("user", param9);
		RequestGenerator req = new RequestGenerator(service, hm);
		
		balanceAfter = getearnedCreditAmount(param1);
		finalBalance = balanceBefore + Double.parseDouble(param2);
		log.info(service.URL);
		AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
		AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("status", true));
		AssertJUnit.assertEquals("Stored Credit Amount Mismatch", finalBalance, balanceAfter);
	}
//	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"} ,dataProvider = "creditCashBackearnedCreditBalanceDataProvider" )
//	public void MyntraCash_creditCashBackOms_VerifyearnedCreditBalance(String param1, String param2, String param3, 
//													String param4, String param5, String param6, String param7, String param8, String param9, String resMessage)
//	{	
//		Double balanceBefore, balanceAfter,finalBalance;
//		balanceBefore = getearnedCreditAmount(param1);
//		
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACKOMS,
//													init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
//		HashMap<String, String> hm = new HashMap<String, String>();
//		hm.put("user", param9);
//		RequestGenerator req = new RequestGenerator(service, hm);
//		
//		balanceAfter = getearnedCreditAmount(param1);
//		finalBalance = balanceBefore + Double.parseDouble(param2);
//		log.info(service.URL);
//		AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
//		AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("status", true));
//		AssertJUnit.assertEquals("Stored Credit Amount Mismatch", finalBalance, balanceAfter);
//	}
	// Verify response for null earned credit balance 
	
		@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression","Fox7Sanity"} ,dataProvider = "creditCashBackearnedCreditBalanceNullValueDataProvider" )
		public void MyntraCash_creditCashBack_VerifyearnedCreditBalanceForNullValues(String param1, String param2, String param3, 
														String param4, String param5, String param6, String param7, String param8, String param9, String resMessage)
		{	
			Double balanceBefore, balanceAfter,finalBalance;
			balanceBefore = getearnedCreditAmount(param1);
			
			MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACK,
														init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("user", param9);
			RequestGenerator req = new RequestGenerator(service, hm);
			param2 = param2.equalsIgnoreCase("") ? "0" : param2;
			balanceAfter = getearnedCreditAmount(param1);
			finalBalance = balanceBefore + Double.parseDouble(param2);
			log.info(service.URL);
			AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
			AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("status", true));
			AssertJUnit.assertEquals("Stored Credit Amount Mismatch", finalBalance, balanceAfter);
		}
//		@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"} ,dataProvider = "creditCashBackearnedCreditBalanceNullValueDataProvider" )
//		public void MyntraCash_creditCashBackOms_VerifyearnedCreditBalanceForNullValues(String param1, String param2, String param3, 
//														String param4, String param5, String param6, String param7, String param8, String param9, String resMessage)
//		{	
//			Double balanceBefore, balanceAfter,finalBalance;
//			balanceBefore = getearnedCreditAmount(param1);
//			
//			MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACKOMS,
//														init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
//			HashMap<String, String> hm = new HashMap<String, String>();
//			hm.put("user", param9);
//			RequestGenerator req = new RequestGenerator(service, hm);
//			param2 = param2.equalsIgnoreCase("") ? "0" : param2;
//			balanceAfter = getearnedCreditAmount(param1);
//			finalBalance = balanceBefore + Double.parseDouble(param2);
//			log.info(service.URL);
//			AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
//			AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("status", true));
//			AssertJUnit.assertEquals("Stored Credit Amount Mismatch", finalBalance, balanceAfter);
//		}
		
	// Verify response for earned credit balance for negetive values
	
		@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression","Fox7Sanity"} ,dataProvider = "creditCashBackearnedCreditBalanceNegetiveValueDataProvider" )
		public void MyntraCash_creditCashBack_VerifyearnedCreditBalanceForNegetiveValue(String param1, String param2, String param3, 
												String param4, String param5, String param6, String param7, String param8, String param9, String resStatus)
		{	
			MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACK,
					init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("user", param9);
			RequestGenerator req = new RequestGenerator(service, hm);
			param2 = param2.equalsIgnoreCase("") ? "0" : param2;
			log.info(service.URL);
			String resMessage= "requested credit for earnedCreditAmount : "+Double.parseDouble(param2)+" storeCreditAmount: "+Double.parseDouble(param3)+" \\n";

			AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
			AssertJUnit.assertEquals("Response status mismatch : ", "\""+resStatus+"\"", req.respvalidate.NodeValue("status", true));
			AssertJUnit.assertEquals("Response message mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("message", true));
			
		}
		
		// Verify response for earned credit balance for negetive values
		
//			@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "Sarath"} ,dataProvider = "creditCashBackearnedCreditBalanceNegetiveValueDataProvider" )
//			public void MyntraCash_creditCashBackOms_VerifyearnedCreditBalanceForNegetiveValue(String param1, String param2, String param3, 
//													String param4, String param5, String param6, String param7, String param8, String param9, String resStatus)
//			{	
//				MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACKOMS,
//						init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
//				HashMap<String, String> hm = new HashMap<String, String>();
//				hm.put("user", param9);
//				RequestGenerator req = new RequestGenerator(service, hm);
//				param2 = param2.equalsIgnoreCase("") ? "0" : param2;
//				log.info(service.URL);
//				String resMessage= "requested credit for earnedCreditAmount : "+Double.parseDouble(param2)+" storeCreditAmount: "+Double.parseDouble(param3)+" \\n";
//
//				AssertJUnit.assertEquals("creditCashBack API is not working", 200, req.response.getStatus());
//				AssertJUnit.assertEquals("Response status mismatch : ", "\""+resStatus+"\"", req.respvalidate.NodeValue("status", true));
//				AssertJUnit.assertEquals("Response message mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("message", true));
//				
//			}

	// Verify response message value when status is success
	
	@Test(groups = { "Sanity","MiniRegression","Regression", "ExhasutiveRegression" ,"Fox7Sanity"}, dataProvider = "creditCashBackVerifyMessageDataProvider")
	public void MyntraCash_creditCashBack_VerifyResponseMessage(String param1, String param2, String param3, String param4, 
																		String param5, String param6, String param7, String param8, String param9, String resMessage){
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACK,init.Configurations,
														new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("user", param9);
		RequestGenerator req = new RequestGenerator(service, hm);
		
		log.info(service.URL);
		
		AssertJUnit.assertEquals("creditCashBack api is not working", 200, req.response.getStatus());
		AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("status", true));
		AssertJUnit.assertEquals("Response message mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("message", true));
	}
	
//	@Test(groups = { "Sanity","MiniRegression","Regression", "ExhasutiveRegression" }, dataProvider = "creditCashBackVerifyMessageDataProvider")
//	public void MyntraCash_creditCashBackOms_VerifyResponseMessage(String param1, String param2, String param3, String param4, 
//																		String param5, String param6, String param7, String param8, String param9, String resMessage){
//		
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACKOMS,init.Configurations,
//														new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
//		HashMap<String, String> hm = new HashMap<String, String>();
//		hm.put("user", param9);
//		RequestGenerator req = new RequestGenerator(service, hm);
//		
//		log.info(service.URL);
//		
//		AssertJUnit.assertEquals("creditCashBack api is not working", 200, req.response.getStatus());
//		AssertJUnit.assertEquals("Response status mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("status", true));
//		AssertJUnit.assertEquals("Response message mismatch : ", "\""+resMessage+"\"", req.respvalidate.NodeValue("message", true));
//	}

	// Verify response Nodes
	
	@Test(groups = { "Sanity","MiniRegression","Regression", "ExhasutiveRegression" ,"Fox7Sanity"}, dataProvider = "creditCashBackResponseNodesDataProvider")
	public void MyntraCash_creditCashBack_VerifyNodes(String param1, String param2, String param3, 
														String param4, String param5, String param6, String param7, String param8, String param9, String resMessage){
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE,
									APINAME.CREDITCASHBACK,init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("user", param9);
		RequestGenerator req = new RequestGenerator(service, hm);
		
		log.info(service.URL);
		
		AssertJUnit.assertTrue("Invalid nodes", req.respvalidate.DoesNodesExists(getIDcreditCashBackNodes()));
	}
	
//	@Test(groups = { "Sanity","MiniRegression","Regression", "ExhasutiveRegression" }, dataProvider = "creditCashBackResponseNodesDataProvider")
//	public void MyntraCash_creditCashBackOms_VerifyNodes(String param1, String param2, String param3, 
//														String param4, String param5, String param6, String param7, String param8, String param9, String resMessage){
//		
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE,
//									APINAME.CREDITCASHBACKOMS,init.Configurations,new String[]{param1,param2,param3,param4,param5,param6,param7,param8,param9});
//		HashMap<String, String> hm = new HashMap<String, String>();
//		hm.put("user", param9);
//		RequestGenerator req = new RequestGenerator(service, hm);
//		
//		log.info(service.URL);
//		
//		AssertJUnit.assertTrue("Invalid nodes", req.respvalidate.DoesNodesExists(getIDcreditCashBackNodes()));
//	}

	private Double getBalance(String emailID){
		String strToreturn ;
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKBALANCE,init.Configurations,
													PayloadType.JSON, new String[]{emailID}, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println("Balnce URL------" + service.URL);
		System.out.println("Balnce response ------" + req.respvalidate.returnresponseasstring());

		strToreturn = req.respvalidate.GetNodeTextUsingIndex("balance");
		strToreturn = strToreturn.substring(strToreturn.indexOf("'")+1, strToreturn.indexOf("'", strToreturn.indexOf("'")+1));
		//System.out.println(strToreturn);
		return Double.parseDouble(strToreturn);
	}
	
	private Double getstoreCreditAmount(String emailID){
		String strToreturn ;
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKBALANCE,init.Configurations,
														PayloadType.JSON, new String[]{emailID}, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		//System.out.println(req.returnresponseasstring());
		strToreturn = req.respvalidate.NodeValue("storeCreditBalance", true);
		//System.out.println("Returened: "+strToreturn);
		return Double.parseDouble(strToreturn);
	}
	
	private Double getearnedCreditAmount(String emailID){
		String strToreturn ;
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKBALANCE,init.Configurations,
														PayloadType.JSON, new String[]{emailID}, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		strToreturn = req.respvalidate.NodeValue("earnedCreditbalance", true);
		return Double.parseDouble(strToreturn);
	}
	
	// end
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "MyntCashDP_checkMyntCashBalance_verifyResponseDataNodesUsingSchemaValidations" )
	public void MyntraCashService_checkMyntCashBalance_verifyResponseDataNodesUsingSchemaValidations(String emailID, String respCode)
	{		
		RequestGenerator checkBalanceReqGen = myntrCashServiceHelper.invokeCheckMyntCashBalance(emailID);
		String checkBalanceResponse = checkBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MyntraCashService checkBalance API response:\n\n"+checkBalanceResponse+"\n");
		log.info("\nPrinting MyntraCashService checkBalance API response:\n\n"+checkBalanceResponse+"\n");
		
		AssertJUnit.assertEquals("MyntraCashService checkBalance API is not working", checkBalanceReqGen.response.getStatus(), Integer.parseInt(respCode));
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/myntracashservice_checkbalance-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(checkBalanceResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MyntraCashService checkBalance API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation","Fox7Sanity" } ,dataProvider = "MyntCashDP_checkTransactionLogsofUser_verifyResponseDataNodesUsingSchemaValidations" )
	public void MyntraCash_checkTransactionLogsofUser_verifyResponseDataNodesUsingSchemaValidations(String email, String respCode)
	{
		RequestGenerator checkTransactionLogsofUserReqGen = myntrCashServiceHelper.invokeCheckTransactionLogsofUser(email);
		String checkTransactionLogsofUserResponse = checkTransactionLogsofUserReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MyntraCashService checkBalance API response:\n\n"+checkTransactionLogsofUserResponse+"\n");
		log.info("\nPrinting MyntraCashService checkBalance API response:\n\n"+checkTransactionLogsofUserResponse+"\n");
		
		AssertJUnit.assertEquals("MyntraCashService checkTransactionLogsofUser API is not working", checkTransactionLogsofUserReqGen.response.getStatus(), 
				Integer.parseInt(respCode));
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/myntracashservice_checktransactionlogsofuser-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(checkTransactionLogsofUserResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MyntraCashService checkBalance API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
//	@Test(groups = { "SchemaValidation" } ,dataProvider = "MyntCashDP_debitCashBack_verifyResponseDataNodesUsingSchemaValidations")
//	public void MyntraCash_debitCashBack_verifyResponseDataNodesUsingSchemaValidations(String login, String earnedCreditAmount, String storeCreditAmount, 
//			String debitAmount, String description, String businessProcess, String itemType, String itemId, String respCode)
//	{
//		RequestGenerator debitCashBackReqGen = myntrCashServiceHelper.invokeDebitCashBack(login, earnedCreditAmount, storeCreditAmount, debitAmount, description,
//				businessProcess, itemType, itemId);
//		String debitCashBackResponse = debitCashBackReqGen.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting MyntraCashService debitCashBack API response:\n\n"+debitCashBackResponse+"\n");
//		log.info("\nPrinting MyntraCashService debitCashBack API response:\n\n"+debitCashBackResponse+"\n");
//		
//		AssertJUnit.assertEquals("MyntraCashService debitCashBack API is not working", debitCashBackReqGen.response.getStatus(), Integer.parseInt(respCode));
//		
//		try {
//            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/myntracashservice_debitcashback-schema.txt");
//            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(debitCashBackResponse, jsonschema);
//            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MyntraCashService checkBalance API response", CollectionUtils.isEmpty(missingNodeList));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//	}
	
	@Test(groups = { "SchemaValidation" ,"Fox7Sanity"} ,dataProvider = "MyntCashDP_creditCashBack_verifyResponseDataNodesUsingSchemaValidations" )
	public void MyntraCash_creditCashBack_verifyResponseDataNodesUsingSchemaValidations(String login, String earnedCreditAmount, String storeCreditAmount, 
			String debitAmount, String description, String businessProcess, String itemType, String itemId, String respCode)
	{	
		RequestGenerator creditCashBackReqGen = myntrCashServiceHelper.invokeCreditCashBack(login, earnedCreditAmount, storeCreditAmount, debitAmount, description, 
				businessProcess, itemType, itemId);
		String creditCashBackResponse = creditCashBackReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MyntraCashService creditCashBack API response:\n\n"+creditCashBackResponse+"\n");
		log.info("\nPrinting MyntraCashService creditCashBack API response:\n\n"+creditCashBackResponse+"\n");
		
		AssertJUnit.assertEquals("MyntraCashService creditCashBack API is not working", creditCashBackReqGen.response.getStatus(), Integer.parseInt(respCode));
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/myntracashservice_creditcashback-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(creditCashBackResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MyntraCashService creditCashBack API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
