/**
 * 
 */
package com.myntra.apiTests.portalservices.giftcardservice;

import com.myntra.apiTests.common.Myntra;
import org.apache.log4j.Logger;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * @author shankara.c
 *
 */
public class GiftCardServiceHelper 
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(GiftCardServiceHelper.class);
	
	/**
	 * This method is used to invoke GiftCardService getGiftCardBalance API to check the balance for the given card and pin.
	 * 
	 * @return RequestGenerator
	 */
	public RequestGenerator getGiftCardBalance(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin)
	{
		MyntraService getGiftCardBalanceService = Myntra.getService(ServiceType.PORTAL_GIFTCARDSERVICE, APINAME.GETGIFTCARDBALANCE, init.Configurations,
				new String[]{ giftCardType, giftCardNumber, giftCardPinCode, userLogin }, PayloadType.JSON, PayloadType.JSON);
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API URL : "+getGiftCardBalanceService.URL);
		log.info("\nPrinting GiftCardService getGiftCardBalance API URL : "+getGiftCardBalanceService.URL);
		
		System.out.println("\nPrinting GiftCardService getGiftCardBalance API Payload : \n\n"+getGiftCardBalanceService.Payload+"\n");
		log.info("\nPrinting GiftCardService getGiftCardBalance API Payload : \n\n"+getGiftCardBalanceService.Payload+"\n");
		
		return new RequestGenerator(getGiftCardBalanceService);
	}
	
	/**
	 * This method is used to invoke getGiftCardAmountFromOrder API to get the total gift card amount spent against the given order id.
	 * 
	 * @param orderId
	 * @return RequestGenerator
	 */
	public RequestGenerator getGiftCardAmountFromOrder(String orderId)
	{
		MyntraService getGiftCardAmountFromOrderService = Myntra.getService(ServiceType.PORTAL_GIFTCARDSERVICE, APINAME.GETGIFTCARDAMOUNTFROMORDER, init.Configurations,
				new String[]{ orderId }, PayloadType.JSON, PayloadType.JSON);
		System.out.println("\nPrinting GiftCardService getGiftCardAmountFromOrder API URL : "+getGiftCardAmountFromOrderService.URL);
		log.info("\nPrinting GiftCardService getGiftCardAmountFromOrder API URL : "+getGiftCardAmountFromOrderService.URL);
		
		System.out.println("\nPrinting GiftCardService getGiftCardAmountFromOrder API Payload : \n\n"+getGiftCardAmountFromOrderService.Payload+"\n");
		log.info("\nPrinting GiftCardService getGiftCardAmountFromOrder API Payload : \n\n"+getGiftCardAmountFromOrderService.Payload+"\n");
		
		return new RequestGenerator(getGiftCardAmountFromOrderService);
	}
	
	/**
	 * This method is used to invoke redeemGiftCardAmount API to redeem the amount from the gift card and logs the entry against the given order Id. 
	 * 
	 * @param giftCardType
	 * @param giftCardNumber
	 * @param giftCardPinCode
	 * @param userLogin
	 * @param orderId
	 * @param amount
	 * @param totalBillAmount
	 * @return RequestGenerator
	 */
	public RequestGenerator redeemGiftCardAmount(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin, String orderId, 
			String giftCardAmount, String totalBillAmount)
	{
		MyntraService redeemGiftCardAmountService = Myntra.getService(ServiceType.PORTAL_GIFTCARDSERVICE, APINAME.REDEEMGIFTCARDAMOUNT, init.Configurations,
				new String[]{ giftCardType, giftCardNumber, giftCardPinCode, userLogin, orderId, giftCardAmount, totalBillAmount }, PayloadType.JSON, PayloadType.JSON);
		System.out.println("\nPrinting GiftCardService redeemGiftCardAmount API URL : "+redeemGiftCardAmountService.URL);
		log.info("\nPrinting GiftCardService redeemGiftCardAmount API URL : "+redeemGiftCardAmountService.URL);
		
		System.out.println("\nPrinting GiftCardService redeemGiftCardAmount API Payload : \n\n"+redeemGiftCardAmountService.Payload+"\n");
		log.info("\nPrinting GiftCardService redeemGiftCardAmount API Payload : \n\n"+redeemGiftCardAmountService.Payload+"\n");
		
		return new RequestGenerator(redeemGiftCardAmountService);
	}
	
	/**
	 * This method is used to invoke GiftCardService cancelRedeemGiftCardAmount API to reverse all the redeemed transactions against the given order Id.
	 * 
	 * @param orderId
	 * @return RequestGenerator
	 */
	public RequestGenerator cancelRedeemGiftCardAmount(String orderId)
	{
		MyntraService cancelRedeemGiftCardAmountService = Myntra.getService(ServiceType.PORTAL_GIFTCARDSERVICE, APINAME.CANCELREDEEMGIFTCARDAMOUNT, init.Configurations,
				new String[]{ orderId }, PayloadType.JSON, PayloadType.JSON);
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API URL : "+cancelRedeemGiftCardAmountService.URL);
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API URL : "+cancelRedeemGiftCardAmountService.URL);
		
		System.out.println("\nPrinting GiftCardService cancelRedeemGiftCardAmount API Payload : \n\n"+cancelRedeemGiftCardAmountService.Payload+"\n");
		log.info("\nPrinting GiftCardService cancelRedeemGiftCardAmount API Payload : \n\n"+cancelRedeemGiftCardAmountService.Payload+"\n");
		
		return new RequestGenerator(cancelRedeemGiftCardAmountService);
	}
	
	/**
	 * This method is used to invoke GiftCardService orderStatus API to verify if the order was successful or failed for the given order Id. 
	 * An order is successful only if all the redeem transactions made for that orderId were successful.
	 * 
	 * @param orderId
	 * @return RequestGenerator
	 */
	public RequestGenerator orderStatus(String orderId)
	{
		MyntraService orderStatusService = Myntra.getService(ServiceType.PORTAL_GIFTCARDSERVICE, APINAME.ORDERSTATUS, init.Configurations, new String[]{ orderId },
				PayloadType.JSON, PayloadType.JSON);
		System.out.println("\nPrinting GiftCardService orderStatus API URL : "+orderStatusService.URL);
		log.info("\nPrinting GiftCardService orderStatus API URL : "+orderStatusService.URL);
		
		System.out.println("\nPrinting GiftCardService orderStatus API Payload : \n\n"+orderStatusService.Payload+"\n");
		log.info("\nPrinting GiftCardService orderStatus API Payload : \n\n"+orderStatusService.Payload+"\n");
		
		return new RequestGenerator(orderStatusService);
	}

}
