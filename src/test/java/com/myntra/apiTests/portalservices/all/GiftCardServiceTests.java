package com.myntra.apiTests.portalservices.all;

import java.util.List;

import com.myntra.apiTests.dataproviders.GiftCardServiceDP;
import com.myntra.apiTests.portalservices.giftcardservice.GiftCardServiceHelper;
import com.myntra.apiTests.portalservices.giftcardservice.GiftCardServiceNodes;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.apiTests.portalservices.commons.StatusNodes;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

/**
 * @author shankara.c
 *
 */
public class GiftCardServiceTests extends GiftCardServiceDP
{
	static GiftCardServiceHelper giftCardServiceHelper = new GiftCardServiceHelper();
	static Logger log = Logger.getLogger(GiftCardServiceTests.class);

	/**
	 * This TestCase is used to invoke GiftCardService getGiftCardBalance and verification for success response
	 * 
	 * @param giftCardType
	 * @param giftCardNumber
	 * @param giftCardPinCode
	 * @param userLogin
	 */
	@Test(groups = { "Smoke", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_getGiftCardBalance_verifySuccessResponse")
	public void GiftCardService_getGiftCardBalance_verifySuccessResponse(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		
		AssertJUnit.assertEquals("GiftCardService getGiftCardBalance API is not working", 200, getGiftCardBalanceReqGen.response.getStatus());
	}
	
	/**
	 * This TestCase is used to invoke GiftCardService getGiftCardBalance and verification for success status response
	 * 
	 * @param giftCardType
	 * @param giftCardNumber
	 * @param giftCardPinCode
	 * @param userLogin
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_getGiftCardBalance_verifySuccessStatusResponse")
	public void GiftCardService_getGiftCardBalance_verifySuccessStatusResponse(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin, 
			String successStatusCode, String successStatusMsg, String successStatusType)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		
		AssertJUnit.assertTrue("GiftCardService getGiftCardBalance API response status code is not a success code", 
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
		
		AssertJUnit.assertTrue("GiftCardService getGiftCardBalance API response status message is not a success message",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		 
		AssertJUnit.assertTrue("GiftCardService getGiftCardBalance API response status type is not a success type",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
	}
	
	/**
	 * This TestCase is used to invoke GiftCardService getGiftCardBalance and verification for success response
	 * 
	 * @param giftCardType
	 * @param giftCardNumber
	 * @param giftCardPinCode
	 * @param userLogin
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_getGiftCardBalance_verifyFailureStatusResponse")
	public void GiftCardService_getGiftCardBalance_verifyFailureStatusResponse(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin, 
			String failureStatusCode, String failureStatusMsg, String failureStatusType)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		
		AssertJUnit.assertTrue("GiftCardService getGiftCardBalance API response status code is not a failure code", 
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(failureStatusCode));	
		
		AssertJUnit.assertTrue("GiftCardService getGiftCardBalance API response status message is not a failure message",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(failureStatusMsg));
		 
		AssertJUnit.assertTrue("GiftCardService getGiftCardBalance API response status type is not a failure type",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));
	}
	
	/**
	 * This TestCase is used to invoke GiftCardService getGiftCardBalance and verification for node values
	 * 
	 * @param giftCardType
	 * @param giftCardNumber
	 * @param giftCardPinCode
	 * @param userLogin
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_getGiftCardBalance_verifyNodeValues")
	public void GiftCardService_getGiftCardBalance_verifyNodeValues(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking GiftCardService getGiftCardBalance API",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		AssertJUnit.assertTrue("GiftCardService getGiftCardBalance API response nodes are invalid/incomplete", 
				getGiftCardBalanceReqGen.respvalidate.DoesNodesExists(GiftCardServiceNodes.getGiftCardNodes()));
		
	}
	
	/**
	 * This TestCase is used to invoke GiftCardService getGiftCardAmountFromOrder API and verification for success response code (200)
	 * 
	 * @param orderId
	 */
	@Test(groups = { "Smoke", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_getGiftCardAmountFromOrder_verifySuccessResponse")
	public void GiftCardService_getGiftCardAmountFromOrder_verifySuccessResponse(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin,
			String orderId, String totalBillAmount)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		 
		AssertJUnit.assertTrue("Error while invoking GiftCardService getGiftCardBalance API",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		String giftCardBalance = getGiftCardBalanceReqGen.respvalidate.NodeValue(GiftCardServiceNodes.GIFT_CARD_DATA_AMOUNT.getNodePath(), true);
		Double giftCardBal = Double.parseDouble(giftCardBalance);
		Double totalBillAmt = Double.parseDouble(totalBillAmount);
		
		if(giftCardBal > totalBillAmt){
			giftCardBalance = totalBillAmount;
		}
		
		RequestGenerator redeemGiftCardAmountReqGen = giftCardServiceHelper.redeemGiftCardAmount(giftCardType, giftCardNumber, giftCardPinCode, userLogin, 
				orderId, giftCardBalance, totalBillAmount);
		String redeemGiftCardAmountResponse = redeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertEquals("GiftCardService redeemGiftCardAmount API is not working", 200, redeemGiftCardAmountReqGen.response.getStatus());
		
		RequestGenerator getGiftCardAmountFromOrderReqGen = giftCardServiceHelper.getGiftCardAmountFromOrder(orderId);
		String getGiftCardAmountFromOrderResponse = getGiftCardAmountFromOrderReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardAmountFromOrder API response : \n\n"+getGiftCardAmountFromOrderResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardAmountFromOrder API response : \n\n"+getGiftCardAmountFromOrderResponse+"\n");
		
		AssertJUnit.assertEquals("GiftCardService getGiftCardAmountFromOrder API is not working", 200, getGiftCardAmountFromOrderReqGen.response.getStatus());
		
		RequestGenerator cancelRedeemGiftCardAmountReqGen = giftCardServiceHelper.cancelRedeemGiftCardAmount(orderId);
		String cancelRedeemGiftCardAmountResponse = cancelRedeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking GiftCardService cancelRedeemGiftCardAmount API",
				cancelRedeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
	}
	
	/**
	 * This TestCase is used to invoke GiftCardService getGiftCardAmountFromOrder API and verification for success status response
	 * 
	 * @param orderId
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_getGiftCardAmountFromOrder_verifySuccessStatusResponse")
	public void GiftCardService_getGiftCardAmountFromOrder_verifySuccessStatusResponse(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin,
			String orderId, String totalBillAmount, String successStatusCode, String successStatusMsg, String successStatusType)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		 
		AssertJUnit.assertTrue("Error while invoking GiftCardService getGiftCardBalance API",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		String giftCardBalance = getGiftCardBalanceReqGen.respvalidate.NodeValue(GiftCardServiceNodes.GIFT_CARD_DATA_AMOUNT.getNodePath(), true);
		Double giftCardBal = Double.parseDouble(giftCardBalance);
		Double totalBillAmt = Double.parseDouble(totalBillAmount);
		
		if(giftCardBal > totalBillAmt){
			giftCardBalance = totalBillAmount;
		}
		
		RequestGenerator redeemGiftCardAmountReqGen = giftCardServiceHelper.redeemGiftCardAmount(giftCardType, giftCardNumber, giftCardPinCode, userLogin, 
				orderId, giftCardBalance, totalBillAmount);
		String redeemGiftCardAmountResponse = redeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertEquals("GiftCardService redeemGiftCardAmount API is not working", 200, redeemGiftCardAmountReqGen.response.getStatus());
		
		RequestGenerator getGiftCardAmountFromOrderReqGen = giftCardServiceHelper.getGiftCardAmountFromOrder(orderId);
		String getGiftCardAmountFromOrderResponse = getGiftCardAmountFromOrderReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardAmountFromOrder API response : \n\n"+getGiftCardAmountFromOrderResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardAmountFromOrder API response : \n\n"+getGiftCardAmountFromOrderResponse+"\n");
		
		AssertJUnit.assertTrue("GiftCardService getGiftCardAmountFromOrder API response status code is not a success code", 
				getGiftCardAmountFromOrderReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
		
		AssertJUnit.assertTrue("GiftCardService getGiftCardAmountFromOrder API response status message is not a success message",
				getGiftCardAmountFromOrderReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		 
		AssertJUnit.assertTrue("GiftCardService getGiftCardAmountFromOrder API response status type is not a success type",
				getGiftCardAmountFromOrderReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		
		RequestGenerator cancelRedeemGiftCardAmountReqGen = giftCardServiceHelper.cancelRedeemGiftCardAmount(orderId);
		String cancelRedeemGiftCardAmountResponse = cancelRedeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking GiftCardService cancelRedeemGiftCardAmount API",
				cancelRedeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
	}
	
	/**
	 * This TestCase is used to invoke GiftCardService getGiftCardAmountFromOrder and verification for node values
	 * 
	 * @param orderId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_getGiftCardAmountFromOrder_verifyNodeValues")
	public void GiftCardService_getGiftCardAmountFromOrder_verifyNodeValues(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin,
			String orderId, String totalBillAmount)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		 
		AssertJUnit.assertTrue("Error while invoking GiftCardService getGiftCardBalance API",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		String giftCardBalance = getGiftCardBalanceReqGen.respvalidate.NodeValue(GiftCardServiceNodes.GIFT_CARD_DATA_AMOUNT.getNodePath(), true);
		Double giftCardBal = Double.parseDouble(giftCardBalance);
		Double totalBillAmt = Double.parseDouble(totalBillAmount);
		
		if(giftCardBal > totalBillAmt){
			giftCardBalance = totalBillAmount;
		}
		
		RequestGenerator redeemGiftCardAmountReqGen = giftCardServiceHelper.redeemGiftCardAmount(giftCardType, giftCardNumber, giftCardPinCode, userLogin, 
				orderId, giftCardBalance, totalBillAmount);
		String redeemGiftCardAmountResponse = redeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertEquals("GiftCardService redeemGiftCardAmount API is not working", 200, redeemGiftCardAmountReqGen.response.getStatus());
		
		RequestGenerator getGiftCardAmountFromOrderReqGen = giftCardServiceHelper.getGiftCardAmountFromOrder(orderId);
		String getGiftCardAmountFromOrderResponse = getGiftCardAmountFromOrderReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardAmountFromOrder API response : \n\n"+getGiftCardAmountFromOrderResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardAmountFromOrder API response : \n\n"+getGiftCardAmountFromOrderResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking GiftCardService getGiftCardAmountFromOrder API",
				getGiftCardAmountFromOrderReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		AssertJUnit.assertTrue("GiftCardService getGiftCardAmountFromOrder API response nodes are invalid/incomplete", 
				getGiftCardAmountFromOrderReqGen.respvalidate.DoesNodesExists(GiftCardServiceNodes.getGiftCardNodes()));
		
		RequestGenerator cancelRedeemGiftCardAmountReqGen = giftCardServiceHelper.cancelRedeemGiftCardAmount(orderId);
		String cancelRedeemGiftCardAmountResponse = cancelRedeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking GiftCardService cancelRedeemGiftCardAmount API",
				cancelRedeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
	}
	
	/**
	 * This TestCase is used to invoke GiftCardService cancelRedeemGiftCardAmount API and verification for success response code(200)
	 * 
	 * @param orderId
	 */
	@Test(groups = { "Smoke", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_cancelRedeemGiftCardAmount_verifySuccessResponse")
	public void GiftCardService_cancelRedeemGiftCardAmount_verifySuccessResponse(String orderId)
	{
		RequestGenerator cancelRedeemGiftCardAmountReqGen = giftCardServiceHelper.cancelRedeemGiftCardAmount(orderId);
		String cancelRedeemGiftCardAmountResponse = cancelRedeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertEquals("GiftCardService cancelRedeemGiftCardAmount API is not working", 200, cancelRedeemGiftCardAmountReqGen.response.getStatus());
	}
	
	/**
	 * @param orderId
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_cancelRedeemGiftCardAmount_verifySuccessStatusResponse")
	public void GiftCardService_cancelRedeemGiftCardAmount_verifySuccessStatusResponse(String orderId, String successStatusCode, String successStatusMsg,
			String successStatusType)
	{
		RequestGenerator cancelRedeemGiftCardAmountReqGen = giftCardServiceHelper.cancelRedeemGiftCardAmount(orderId);
		String cancelRedeemGiftCardAmountResponse = cancelRedeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertTrue("GiftCardService cancelRedeemGiftCardAmount API response status code is not a success code", 
				cancelRedeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
		
		AssertJUnit.assertTrue("GiftCardService cancelRedeemGiftCardAmount API response status message is not a success message",
				cancelRedeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		 
		AssertJUnit.assertTrue("GiftCardService cancelRedeemGiftCardAmount API response status type is not a success type",
				cancelRedeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
	}
	
	/**
	 * This TestCase is used to invoke GiftCardService cancelRedeemGiftCardAmount API and verification for node values
	 * 
	 * @param orderId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_cancelRedeemGiftCardAmount_verifyNodeValues")
	public void GiftCardService_cancelRedeemGiftCardAmount_verifyNodeValues(String orderId)
	{
		RequestGenerator cancelRedeemGiftCardAmountReqGen = giftCardServiceHelper.cancelRedeemGiftCardAmount(orderId);
		String cancelRedeemGiftCardAmountResponse = cancelRedeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking GiftCardService cancelRedeemGiftCardAmount API",
				cancelRedeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		AssertJUnit.assertTrue("GiftCardService cancelRedeemGiftCardAmount API response nodes are invalid/incomplete", 
				cancelRedeemGiftCardAmountReqGen.respvalidate.DoesNodesExists(GiftCardServiceNodes.getGiftCardNodes()));
	}
	
	/**
	 * This TestCase is used to invoke GiftCardService redeemGiftCardAmount API and verification for success response code (200)
	 * 
	 * @param giftCardType
	 * @param giftCardNumber
	 * @param giftCardPinCode
	 * @param userLogin
	 * @param orderId
	 * @param giftCardAmount
	 * @param totalBillAmount
	 */
	@Test(groups = { "Smoke", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_redeemGiftCardAmount_verifySuccessResponse")
	public void GiftCardService_redeemGiftCardAmount_verifySuccessResponse(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin,
			String orderId, String totalBillAmount)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		 
		AssertJUnit.assertTrue("Error while invoking GiftCardService getGiftCardBalance API",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		String giftCardBalance = getGiftCardBalanceReqGen.respvalidate.NodeValue(GiftCardServiceNodes.GIFT_CARD_DATA_AMOUNT.getNodePath(), true);
		Double giftCardBal = Double.parseDouble(giftCardBalance);
		Double totalBillAmt = Double.parseDouble(totalBillAmount);
		
		if(giftCardBal > totalBillAmt){
			giftCardBalance = totalBillAmount;
		}
		
		RequestGenerator redeemGiftCardAmountReqGen = giftCardServiceHelper.redeemGiftCardAmount(giftCardType, giftCardNumber, giftCardPinCode, userLogin, 
				orderId, giftCardBalance, totalBillAmount);
		String redeemGiftCardAmountResponse = redeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertEquals("GiftCardService redeemGiftCardAmount API is not working", 200, redeemGiftCardAmountReqGen.response.getStatus());
		
		RequestGenerator cancelRedeemGiftCardAmountReqGen = giftCardServiceHelper.cancelRedeemGiftCardAmount(orderId);
		String cancelRedeemGiftCardAmountResponse = cancelRedeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking GiftCardService cancelRedeemGiftCardAmount API",
				cancelRedeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
	}
	
	/**
	 * This TestCase is used to invoke GiftCardService redeemGiftCardAmount API and verification for success status response
	 * 
	 * @param giftCardType
	 * @param giftCardNumber
	 * @param giftCardPinCode
	 * @param userLogin
	 * @param orderId
	 * @param giftCardAmount
	 * @param totalBillAmount
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_redeemGiftCardAmount_verifySuccessStatusResponse")
	public void GiftCardService_redeemGiftCardAmount_verifySuccessStatusResponse(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin,
			String orderId, String totalBillAmount, String successStatusCode, String successStatusMsg, String successStatusType)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		 
		AssertJUnit.assertTrue("Error while invoking GiftCardService getGiftCardBalance API",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		String giftCardBalance = getGiftCardBalanceReqGen.respvalidate.NodeValue(GiftCardServiceNodes.GIFT_CARD_DATA_AMOUNT.getNodePath(), true);
		Double giftCardBal = Double.parseDouble(giftCardBalance);
		Double totalBillAmt = Double.parseDouble(totalBillAmount);
		
		if(giftCardBal > totalBillAmt){
			giftCardBalance = totalBillAmount;
		}
		
		RequestGenerator redeemGiftCardAmountReqGen = giftCardServiceHelper.redeemGiftCardAmount(giftCardType, giftCardNumber, giftCardPinCode, userLogin, 
				orderId, giftCardBalance, totalBillAmount);
		String redeemGiftCardAmountResponse = redeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");

		AssertJUnit.assertTrue("GiftCardService redeemGiftCardAmount API response status code is not a success code", 
				redeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
		
		AssertJUnit.assertTrue("GiftCardService redeemGiftCardAmount API response status message is not a success message",
				redeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		 
		AssertJUnit.assertTrue("GiftCardService redeemGiftCardAmount API response status type is not a success type",
				redeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		
		RequestGenerator cancelRedeemGiftCardAmountReqGen = giftCardServiceHelper.cancelRedeemGiftCardAmount(orderId);
		String cancelRedeemGiftCardAmountResponse = cancelRedeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking GiftCardService cancelRedeemGiftCardAmount API",
				cancelRedeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
	}
	
	/**
	 * @param giftCardType
	 * @param giftCardNumber
	 * @param giftCardPinCode
	 * @param userLogin
	 * @param orderId
	 * @param totalBillAmount
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_redeemGiftCardAmount_verifyNodeValues")
	public void GiftCardService_redeemGiftCardAmount_verifyNodeValues(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin,
			String orderId, String totalBillAmount)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		 
		AssertJUnit.assertTrue("Error while invoking GiftCardService getGiftCardBalance API",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		String giftCardBalance = getGiftCardBalanceReqGen.respvalidate.NodeValue(GiftCardServiceNodes.GIFT_CARD_DATA_AMOUNT.getNodePath(), true);
		Double giftCardBal = Double.parseDouble(giftCardBalance);
		Double totalBillAmt = Double.parseDouble(totalBillAmount);
		
		if(giftCardBal > totalBillAmt){
			giftCardBalance = totalBillAmount;
		}
		
		RequestGenerator redeemGiftCardAmountReqGen = giftCardServiceHelper.redeemGiftCardAmount(giftCardType, giftCardNumber, giftCardPinCode, userLogin, 
				orderId, giftCardBalance, totalBillAmount);
		String redeemGiftCardAmountResponse = redeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");

		AssertJUnit.assertTrue("Error while invoking GiftCardService redeemGiftCardAmount API",
				redeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		AssertJUnit.assertTrue("GiftCardService redeemGiftCardAmount API response nodes are invalid/incomplete",
				redeemGiftCardAmountReqGen.respvalidate.DoesNodesExists(GiftCardServiceNodes.getRedeemGiftCardNodes()));
		
		AssertJUnit.assertEquals("GiftCardService redeemGiftCardAmount API request and response giftCardBalance are not same",
				redeemGiftCardAmountReqGen.respvalidate.NodeValue(GiftCardServiceNodes.GIFT_CARD_DATA_AMOUNT.getNodePath(), true), giftCardBalance);
		
		AssertJUnit.assertNotNull("GiftCardService redeemGiftCardAmount API response TransactionId is null/blank", 
				redeemGiftCardAmountReqGen.respvalidate.NodeValue(GiftCardServiceNodes.GIFT_CARD_TXN_ID.getNodePath(), true));
		
		RequestGenerator cancelRedeemGiftCardAmountReqGen = giftCardServiceHelper.cancelRedeemGiftCardAmount(orderId);
		String cancelRedeemGiftCardAmountResponse = cancelRedeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking GiftCardService cancelRedeemGiftCardAmount API",
				cancelRedeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
	}

	/**
	 * This TestCase is used to invoke GiftCardService redeemGiftCardAmount API and verification for failure status response
	 * 
	 * @param giftCardType
	 * @param giftCardNumber
	 * @param giftCardPinCode
	 * @param userLogin
	 * @param orderId
	 * @param giftCardAmount
	 * @param totalBillAmount
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_redeemGiftCardAmount_verifyFailureStatusResponse")
	public void GiftCardService_redeemGiftCardAmount_verifyFailureStatusResponse(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin,
			String orderId, String giftCardAmount, String totalBillAmount, String failureStatusCode, String failureStatusMsg, String failureStatusType)
	{
		RequestGenerator redeemGiftCardAmountReqGen = giftCardServiceHelper.redeemGiftCardAmount(giftCardType, giftCardNumber, giftCardPinCode, userLogin, 
				orderId, giftCardAmount, totalBillAmount);
		String redeemGiftCardAmountResponse = redeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertTrue("GiftCardService redeemGiftCardAmount API response status code is not a failure code", 
				redeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(failureStatusCode));	
		
		AssertJUnit.assertTrue("GiftCardService redeemGiftCardAmount API response status message is not a failure message",
				redeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(failureStatusMsg));
		 
		AssertJUnit.assertTrue("GiftCardService redeemGiftCardAmount API response status type is not a failure type",
				redeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));
	}
	
	/**
	 * This TestCase is used to invoke GiftCardService orderStatus API and verification for success response code(200)
	 * 
	 * @param orderId
	 */
	@Test(groups = { "Smoke", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_orderStatus_verifySuccessResponse")
	public void GiftCardService_orderStatus_verifySuccessResponse(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin, String orderId,
			String totalBillAmount)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		 
		AssertJUnit.assertTrue("Error while invoking GiftCardService getGiftCardBalance API",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		String giftCardBalance = getGiftCardBalanceReqGen.respvalidate.NodeValue(GiftCardServiceNodes.GIFT_CARD_DATA_AMOUNT.getNodePath(), true);
		Double giftCardBal = Double.parseDouble(giftCardBalance);
		Double totalBillAmt = Double.parseDouble(totalBillAmount);
		
		if(giftCardBal > totalBillAmt){
			giftCardBalance = totalBillAmount;
		}
		
		RequestGenerator redeemGiftCardAmountReqGen = giftCardServiceHelper.redeemGiftCardAmount(giftCardType, giftCardNumber, giftCardPinCode, userLogin, 
				orderId, giftCardBalance, totalBillAmount);
		String redeemGiftCardAmountResponse = redeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		 
		AssertJUnit.assertTrue("Error while invoking GiftCardService redeemGiftCardAmount API",
				redeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		RequestGenerator orderStatusReqGen = giftCardServiceHelper.orderStatus(orderId);
		String orderStatusResponse = orderStatusReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService orderStatus API response : \n\n"+orderStatusResponse+"\n");
		log.info("\nPrinting GiftCardService orderStatus API response : \n\n"+orderStatusResponse+"\n");
		
		AssertJUnit.assertEquals("GiftCardService orderStatus API is not working", 200, orderStatusReqGen.response.getStatus());
		
		RequestGenerator cancelRedeemGiftCardAmountReqGen = giftCardServiceHelper.cancelRedeemGiftCardAmount(orderId);
		String cancelRedeemGiftCardAmountResponse = cancelRedeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking GiftCardService cancelRedeemGiftCardAmount API",
				cancelRedeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
	}
	
	/**
	 * This TestCase is used to invoke GiftCardService orderStatus API and verification for success  response
	 * 
	 * @param orderId
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_orderStatus_verifySuccessStatusResponse")
	public void GiftCardService_orderStatus_verifySuccessStatusResponse(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin, 
			String orderId, String totalBillAmount, String successStatusCode, String successStatusMsg, String successStatusType)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		 
		AssertJUnit.assertTrue("Error while invoking GiftCardService getGiftCardBalance API",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		String giftCardBalance = getGiftCardBalanceReqGen.respvalidate.NodeValue(GiftCardServiceNodes.GIFT_CARD_DATA_AMOUNT.getNodePath(), true);
		Double giftCardBal = Double.parseDouble(giftCardBalance);
		Double totalBillAmt = Double.parseDouble(totalBillAmount);
		
		if(giftCardBal > totalBillAmt){
			giftCardBalance = totalBillAmount;
		}
		
		RequestGenerator redeemGiftCardAmountReqGen = giftCardServiceHelper.redeemGiftCardAmount(giftCardType, giftCardNumber, giftCardPinCode, userLogin, 
				orderId, giftCardBalance, totalBillAmount);
		String redeemGiftCardAmountResponse = redeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		 
		AssertJUnit.assertTrue("Error while invoking GiftCardService redeemGiftCardAmount API",
				redeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		RequestGenerator orderStatusReqGen = giftCardServiceHelper.orderStatus(orderId);
		String orderStatusResponse = orderStatusReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService orderStatus API response : \n\n"+orderStatusResponse+"\n");
		log.info("\nPrinting GiftCardService orderStatus API response : \n\n"+orderStatusResponse+"\n");
		
		AssertJUnit.assertTrue("GiftCardService orderStatus API response status code is not a success code", 
				orderStatusReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
		
		AssertJUnit.assertTrue("GiftCardService orderStatus API response status message is not a success message",
				orderStatusReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		 
		AssertJUnit.assertTrue("GiftCardService orderStatus API response status type is not a success type",
				orderStatusReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		
		RequestGenerator cancelRedeemGiftCardAmountReqGen = giftCardServiceHelper.cancelRedeemGiftCardAmount(orderId);
		String cancelRedeemGiftCardAmountResponse = cancelRedeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking GiftCardService cancelRedeemGiftCardAmount API",
				cancelRedeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
	}
	
	/**
	 * This TestCase is used to invoke GiftCardService orderStatus API and verification for failure status response
	 * 
	 * @param giftCardType
	 * @param giftCardNumber
	 * @param giftCardPinCode
	 * @param userLogin
	 * @param orderId
	 * @param totalBillAmount
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="GiftCardServiceDP_orderStatus_verifyFailureStatusResponse")
	public void GiftCardService_orderStatus_verifyFailureStatusResponse(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin, 
			String orderId, String totalBillAmount, String failureStatusCode, String failureStatusMsg, String failureStatusType)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		 
		AssertJUnit.assertTrue("Error while invoking GiftCardService getGiftCardBalance API",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		String giftCardBalance = getGiftCardBalanceReqGen.respvalidate.NodeValue(GiftCardServiceNodes.GIFT_CARD_DATA_AMOUNT.getNodePath(), true);
		Double giftCardBal = Double.parseDouble(giftCardBalance);
		Double totalBillAmt = Double.parseDouble(totalBillAmount);
		
		if(giftCardBal > totalBillAmt){
			giftCardBalance = totalBillAmount;
		}
		
		RequestGenerator redeemGiftCardAmountReqGen = giftCardServiceHelper.redeemGiftCardAmount(giftCardType, giftCardNumber, giftCardPinCode, userLogin, 
				orderId, giftCardBalance, totalBillAmount);
		String redeemGiftCardAmountResponse = redeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		 
		AssertJUnit.assertTrue("Error while invoking GiftCardService redeemGiftCardAmount API",
				redeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		RequestGenerator orderStatusReqGen = giftCardServiceHelper.orderStatus(orderId);
		String orderStatusResponse = orderStatusReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService orderStatus API response : \n\n"+orderStatusResponse+"\n");
		log.info("\nPrinting GiftCardService orderStatus API response : \n\n"+orderStatusResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking GiftCardService orderStatus API",
				orderStatusReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		RequestGenerator cancelRedeemGiftCardAmountReqGen = giftCardServiceHelper.cancelRedeemGiftCardAmount(orderId);
		String cancelRedeemGiftCardAmountResponse = cancelRedeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking GiftCardService cancelRedeemGiftCardAmount API",
				cancelRedeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		orderStatusReqGen = giftCardServiceHelper.orderStatus(orderId);
		orderStatusResponse = orderStatusReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService orderStatus API response : \n\n"+orderStatusResponse+"\n");
		log.info("\nPrinting GiftCardService orderStatus API response : \n\n"+orderStatusResponse+"\n");
		
		AssertJUnit.assertTrue("GiftCardService orderStatus API response status code is not a failure code", 
				orderStatusReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(failureStatusCode));	
		
		AssertJUnit.assertTrue("GiftCardService orderStatus API response status message is not a failure message",
				orderStatusReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(failureStatusMsg));
		 
		AssertJUnit.assertTrue("GiftCardService orderStatus API response status type is not a failure type",
				orderStatusReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="GiftCardServiceDP_getGiftCardBalance_verifyResponseDataNodesUsingSchemaValidations")
	public void GiftCardService_getGiftCardBalance_verifyResponseDataNodesUsingSchemaValidations(String giftCardType, String giftCardNumber, String giftCardPinCode, 
			String userLogin)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		
		AssertJUnit.assertEquals("GiftCardService getGiftCardBalance API is not working", 200, getGiftCardBalanceReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/giftcardservice-getgiftcardbalance-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getGiftCardBalanceResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService getGiftCardBalance API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="GiftCardServiceDP_getGiftCardAmountFromOrder_verifyResponseDataNodesUsingSchemaValidations")
	public void GiftCardService_getGiftCardAmountFromOrder_verifyResponseDataNodesUsingSchemaValidations(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin,
			String orderId, String totalBillAmount)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		 
		AssertJUnit.assertTrue("Error while invoking GiftCardService getGiftCardBalance API",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		String giftCardBalance = getGiftCardBalanceReqGen.respvalidate.NodeValue(GiftCardServiceNodes.GIFT_CARD_DATA_AMOUNT.getNodePath(), true);
		Double giftCardBal = Double.parseDouble(giftCardBalance);
		Double totalBillAmt = Double.parseDouble(totalBillAmount);
		
		if(giftCardBal > totalBillAmt){
			giftCardBalance = totalBillAmount;
		}
		
		RequestGenerator redeemGiftCardAmountReqGen = giftCardServiceHelper.redeemGiftCardAmount(giftCardType, giftCardNumber, giftCardPinCode, userLogin, 
				orderId, giftCardBalance, totalBillAmount);
		String redeemGiftCardAmountResponse = redeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertEquals("GiftCardService redeemGiftCardAmount API is not working", 200, redeemGiftCardAmountReqGen.response.getStatus());
		
		RequestGenerator getGiftCardAmountFromOrderReqGen = giftCardServiceHelper.getGiftCardAmountFromOrder(orderId);
		String getGiftCardAmountFromOrderResponse = getGiftCardAmountFromOrderReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardAmountFromOrder API response : \n\n"+getGiftCardAmountFromOrderResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardAmountFromOrder API response : \n\n"+getGiftCardAmountFromOrderResponse+"\n");
		
		AssertJUnit.assertEquals("GiftCardService getGiftCardAmountFromOrder API is not working", 200, getGiftCardAmountFromOrderReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/giftcardservice-getgiftcardamountfromorder-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getGiftCardAmountFromOrderResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService getGiftCardAmountFromOrder API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		RequestGenerator cancelRedeemGiftCardAmountReqGen = giftCardServiceHelper.cancelRedeemGiftCardAmount(orderId);
		String cancelRedeemGiftCardAmountResponse = cancelRedeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking GiftCardService cancelRedeemGiftCardAmount API",
				cancelRedeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="GiftCardServiceDP_cancelRedeemGiftCardAmount_verifyResponseDataNodesUsingSchemaValidations")
	public void GiftCardService_cancelRedeemGiftCardAmount_verifyResponseDataNodesUsingSchemaValidations(String orderId)
	{
		RequestGenerator cancelRedeemGiftCardAmountReqGen = giftCardServiceHelper.cancelRedeemGiftCardAmount(orderId);
		String cancelRedeemGiftCardAmountResponse = cancelRedeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertEquals("GiftCardService cancelRedeemGiftCardAmount API is not working", 200, cancelRedeemGiftCardAmountReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/giftcardservice-cancelredeemgiftcardamount-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(cancelRedeemGiftCardAmountResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService cancelRedeemGiftCardAmount API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="GiftCardServiceDP_redeemGiftCardAmount_verifyResponseDataNodesUsingSchemaValidations")
	public void GiftCardService_redeemGiftCardAmount_verifyResponseDataNodesUsingSchemaValidations(String giftCardType, String giftCardNumber, String giftCardPinCode, 
			String userLogin, String orderId, String totalBillAmount)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		 
		AssertJUnit.assertTrue("Error while invoking GiftCardService getGiftCardBalance API",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		String giftCardBalance = getGiftCardBalanceReqGen.respvalidate.NodeValue(GiftCardServiceNodes.GIFT_CARD_DATA_AMOUNT.getNodePath(), true);
		Double giftCardBal = Double.parseDouble(giftCardBalance);
		Double totalBillAmt = Double.parseDouble(totalBillAmount);
		
		if(giftCardBal > totalBillAmt){
			giftCardBalance = totalBillAmount;
		}
		
		RequestGenerator redeemGiftCardAmountReqGen = giftCardServiceHelper.redeemGiftCardAmount(giftCardType, giftCardNumber, giftCardPinCode, userLogin, 
				orderId, giftCardBalance, totalBillAmount);
		String redeemGiftCardAmountResponse = redeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertEquals("GiftCardService redeemGiftCardAmount API is not working", 200, redeemGiftCardAmountReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/giftcardservice-redeemgiftcardamount-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(redeemGiftCardAmountResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService redeemGiftCardAmount API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		RequestGenerator cancelRedeemGiftCardAmountReqGen = giftCardServiceHelper.cancelRedeemGiftCardAmount(orderId);
		String cancelRedeemGiftCardAmountResponse = cancelRedeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking GiftCardService cancelRedeemGiftCardAmount API",
				cancelRedeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="GiftCardServiceDP_orderStatus_verifyResponseDataNodesUsingSchemaValidations")
	public void GiftCardService_orderStatus_verifyResponseDataNodesUsingSchemaValidations(String giftCardType, String giftCardNumber, String giftCardPinCode, 
			String userLogin, String orderId, String totalBillAmount)
	{
		RequestGenerator getGiftCardBalanceReqGen = giftCardServiceHelper.getGiftCardBalance(giftCardType, giftCardNumber, giftCardPinCode, userLogin);
		String getGiftCardBalanceResponse = getGiftCardBalanceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API response : \n\n"+getGiftCardBalanceResponse+"\n");
		 
		AssertJUnit.assertTrue("Error while invoking GiftCardService getGiftCardBalance API",
				getGiftCardBalanceReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		String giftCardBalance = getGiftCardBalanceReqGen.respvalidate.NodeValue(GiftCardServiceNodes.GIFT_CARD_DATA_AMOUNT.getNodePath(), true);
		Double giftCardBal = Double.parseDouble(giftCardBalance);
		Double totalBillAmt = Double.parseDouble(totalBillAmount);
		
		if(giftCardBal > totalBillAmt){
			giftCardBalance = totalBillAmount;
		}
		
		RequestGenerator redeemGiftCardAmountReqGen = giftCardServiceHelper.redeemGiftCardAmount(giftCardType, giftCardNumber, giftCardPinCode, userLogin, 
				orderId, giftCardBalance, totalBillAmount);
		String redeemGiftCardAmountResponse = redeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService redeemGiftCardAmount API response : \n\n"+redeemGiftCardAmountResponse+"\n");
		 
		AssertJUnit.assertTrue("Error while invoking GiftCardService redeemGiftCardAmount API",
				redeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		RequestGenerator orderStatusReqGen = giftCardServiceHelper.orderStatus(orderId);
		String orderStatusResponse = orderStatusReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService orderStatus API response : \n\n"+orderStatusResponse+"\n");
		log.info("\nPrinting GiftCardService orderStatus API response : \n\n"+orderStatusResponse+"\n");
		
		AssertJUnit.assertEquals("GiftCardService orderStatus API is not working", 200, orderStatusReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/giftcardservice-orderstatus-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(orderStatusResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService orderStatus API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		RequestGenerator cancelRedeemGiftCardAmountReqGen = giftCardServiceHelper.cancelRedeemGiftCardAmount(orderId);
		String cancelRedeemGiftCardAmountResponse = cancelRedeemGiftCardAmountReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API response : \n\n"+cancelRedeemGiftCardAmountResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking GiftCardService cancelRedeemGiftCardAmount API",
				cancelRedeemGiftCardAmountReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
	}
	
}
