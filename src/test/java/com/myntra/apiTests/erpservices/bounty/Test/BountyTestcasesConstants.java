package com.myntra.apiTests.erpservices.bounty.Test;

public class BountyTestcasesConstants {

	public static class BountyServiceTest {
		public static final String LOGIN_URL = "bountytestuser001@myntra.com";
		public static final String PASSWORD = "123456";
		public static final String bountyServiceCreateOrderInvalidPPSID_ITEM1 = "828703";
		public static final String bountyServiceCreateOrderInvalidPPSID_ITEM2 = "585868";
		public static final String bountyServiceCreateOrderInvalidCart_ITEM1 = "828703";
		public static final String bountyServiceCreateOrderInvalidCart_ITEM2 = "585868";
		public static final String bountyServiceCreateOrderBlankSession_ITEM1 = "796931";
		public static final String bountyServiceCreateOrder_ITEM1 = "796931";
		public static final String bountyServiceCreateOrder_ITEM2 = "796932";
		public static final String bountyServiceCreateOrderForOOSSKUs_ITEM1 = "47357";
		public static final String bountyQueueOrderSuccess_ITEM1 = "796933";
		public static final String queueAnOrderWhenInventoryIsNotAvailable_ITEM1 = "796934";
		public static final String confirmOrderSuccess_ITEM1 = "796931";
		public static final String OrderQueueOnHold5FromPPStatus_ITEM1 = "796931";
		public static final String OrderQueueOnHold5FromPVStatus_ITEM1 = "796931";
		public static final String declineOrderShouldFreeUpBountyInventory_ITEM1 = "796931";
		public static final String declineOrderShouldUnBlockTheCouponUsage_ITEM1 = "796931";
		public static final String verifyCouponShouldBeLockedAfterQueueOrder_ITEM1 = "796931";
		public static final String verifyCartShouldBeDeletedAfterSuccessfulQueuingOrder_ITEM1 = "796931";
		public static final String declineOrderQueueOnHold5FromPPStatus_ITEM1 = "796931";
		public static final String verifyMultipleItemAreAvailableTryAndBuyAndTnbFlagShouldAvailableAtItemLevelFromCart_ITEM1 = "945928";
		public static final String verifyMultipleItemAreAvailableTryAndBuyAndTnbFlagShouldAvailableAtItemLevelFromCart_ITEM2 = "3836";
		public static final String verifyTheIsTryAndBuyFlagInXCartOrderDetailsTableOneIsTryAndBuyAndOneIsNonTryAndBuyForOrder_ITEM1 = "3856";
		public static final String verifyTheIsTryAndBuyFlagInXCartOrderDetailsTableOneIsTryAndBuyAndOneIsNonTryAndBuyForOrder_ITEM2 = "330411";
		public static final String verifyFlagShouldBePropagatedInBountyToOmsIfMultipleTnbAndNonTnbItemsPlaced_ITEM1 = "3834";
		public static final String verifyFlagShouldBePropagatedInBountyToOmsIfMultipleTnbAndNonTnbItemsPlaced_ITEM2 = "3856";
		public static final String verifyFlagShouldBePropagatedInBountyToOmsIfMultipleTnbAndNonTnbItemsPlaced_ITEM3 = "330411";
		public static final String verifyFlagShouldBePropagatedInBountyToOmsIfMultipleItemsPlaced_ITEM1 = "3856";
		public static final String verifyFlagShouldBePropagatedInBountyToOmsIfMultipleItemsPlaced_ITEM2 = "3836";
		public static final String verifySingleItemIsAvailableTryAndBuyAndTnbFlagShouldAvailableAtItemLevelFromCart_ITEM1 = "3832";
		public static final String verifyTheIsTryAndBuyFlagInXcartdOrderDetailsTabelIsTryAndBuyFlagForOrder_ITEM1 = "3856";
		public static final String verifyAdressSwitchToDefaultAddressIfFetchThroughAddressIDFailedAndFGIsOn_ITEM1 = "3831";
		public static final String verifyFetchAddressShouldnotFetchDefaultAddressIfFGIsOFF_ITEM1 = "3831";
		public static final String verifyAddressShouldBeStoredInBounty_ITEM1 = "3831";
		public static final String verifyDBDetailsForNormalOrder_ITEM1 = "3831";
		public static final String queueAlreadyQueuedOrderDP_ITEM1 = "796931";
		public static final String confirmOrderFailedDP_ITEM1 = "796931";
		public static final String declineOrderSuccessDP_ITEM1 = "796931";
		public static final String declineOrderSuccessFromPPStatus_ITEM1 = "796931";
		public static final String declineOrderSuccessFromPVStatus_ITEM1 = "796931";
		public static final String declineOrderSuccessFromPPStatusDP_ITEM1 = "796931";
		public static final String declineOrderSuccessFromPVStatusDP_ITEM1 = "796931";
		public static final String declineOrderSuccessFromPVToQOnHold39StatusDP_ITEM1 = "796931";
		public static final String declineOrderSuccessFromPPToQOnHold39StatusDP_ITEM1 = "796931";
		public static final String declineBountyQueueOrderSuccessDP_ITEM1 = "796931";
		public static final String declineAlreadyDeclinedOrderDP_ITEM1 = "796931";
		public static final String confirmOrderNeverOnHoldDP_ITEM1 = "796931";
		public static final String BountyServiceDP_createOrder_verifySuccessResponse_ITEM1 = "987669";
		public static final String BountyServiceDP_createOrder_verifyStatus_ITEM1 = "987669";
		public static final String BountyServiceDP_createOrder_verifyStatus_pps_ITEM1 = "987669";
		public static final String BountyServiceDP_createOrder_verifySuccessStatusValues_ITEM1 = "987669";
		public static final String createOrder_verifyFailure_SkuOOS_ITEM1 = "852767";
		public static final String queueOrder_GiftcardAmount_ITEM1 = "987669";
		public static final String createOrder_OnlineOrder_ITEM1 = "987669";
		public static final String createOrder_CODorder_ITEM1 = "987669";
		public static final String createOrder_CODorder_ITEM2 = "987669";
		public static final String createOrder_multipleskus_ITEM1 = "1147531";
		public static final String createOrder_multipleskus_ITEM2 = "1111187";
		public static final String createOrder_typeOnhandAndJIT_ITEM1 = "828703";
		public static final String createOrder_typeOnhandAndJIT_ITEM2 = "585868";
		public static final String SingleItemOrder_ITEM1 = "1153080";
		public static final String SingleItemOrder_ITEM2 = "1153080";
		public static final String twoItemSingleQty_ITEM1 = "1152964";
		public static final String twoItemSingleQty_ITEM2 = "1152952";
		public static final String twoItemMultipleQty_ITEM1 = "1152956";
		public static final String twoItemMultipleQty_ITEM2 = "1152955";
		public static final String singleQtyDiffVendor_ITEM1 = "1152957";
		public static final String singleQtyDiffVendor_ITEM2 = "1152958";
		public static final String multipleQtyDiffVendor_ITEM1 = "1152959";
		public static final String multipleQtyDiffVendor_ITEM2 = "1152960";
		public static final String multipleQtyDiffWH_ITEM1 = "1152961";
		public static final String multipleQtyDiffWH_ITEM2 = "1152962";
		public static final String OnHandAndJit_ITEM1 = "828703";
		public static final String OnHandAndJit_ITEM2 = "585868";
		public static final String BountyServiceDP_confirmOrder_pps_ITEM1 = "828703";
		public static final String BountyServiceDP_queueOrder_pps_ITEM1 = "828703";
		public static final String BountyServiceDP_declineOrder_pps_ITEM1 = "828703";
		public static final String declineOrderAfterPPSOnHoldPPS_ITEM1 = "828703";
		public static final String declineOrderAfterPPSOnHoldPPS_ITEM2 = "493441";
		public static final String BountyServiceDP_createOrder_ppsDP_ITEM1 = "828703";
		public static final String BountyServiceDP_createOrder_ppsDP_ITEM2 = "585868";
		public static final String orderCreationShouldFailIfTheIfTheOrderValueIsLessThanCODMinValue_PPSDP_ITEM1 = "3835";
		public static final String orderCreationShouldFailIfTheIfTheOrderValueIsLessThanCODMinValue_CBLPCODDP_ITEM1 = "3835";
		public static final String orderCreationShouldPassIfTheIfTheOrderValueIsMoreThanCODMinValue_CBLPCODDP_ITEM1 = "3835";
		public static final String orderCreationShouldPassIfTheIfTheOrderValueIsMoreThanCODMinValue_CODDP_ITEM1 = "3835";
		public static final String orderCreationShouldFailIfTheOrderValueIsMoreThanCODMaxValue_CODDP_ITEM1 = "1153620";
		public static final String orderCreationShouldFailIfTheOrderValueIsMoreThanCODMaxValue_CBLPCODDP_ITEM1 = "1153620";
		public static final String VerifyOrderStatusShouldBePVWhenPaymentIsVerifiedOnCoDOrderInBountyDP_ITEM1 = "3834";
		public static final String VerifyOrderStatusShouldBePVWhenPaymentIsVerifiedOnCoDOrderInBountyDP1_ITEM1 = "3918";
		public static final String VerifyOrderStatusShouldBePPWhenPaymentIsVerifiedOnCoDOrderInBountyDP_ITEM1 = "3832";
		public static final String verifyTheErrorCodeWhenAlreadyQueuedOrderResultInErrorResponseWithErrorCodeDP_ITEM1 = "828703";
		public static final String verifyQWithoutOnHoldWhenSuccessfullyDebitingAllThePaymentInstrumentsDP_ITEM1 = "828703";
		public static final String verifyOrderStatusIfPaymentIsPPOrderStatusIsQWithOnhold39WhenPreConfirmationStateWhileDebitingOfAsyncPaymentInstrumentsDP_ITEM1 = "828703";
		public static final String verifyOrderStatusIfPaymentIsPVOrderStatusIsQOnhold39DP_ITEM1 = "3834";
		public static final String verifyOrderStatusIfPaymentIsPVOrderStatusIsQOnhold5DP_ITEM1 = "3834";
		public static final String verifyOrderStatusIfPaymentIsPPOrderStatusIsQAndOnhold5DP_ITEM1 = "3832";
		public static final String verifyOrderStatusIfPaymentIsPVOrderStatusIsQAndOnhold5DP_ITEM1 = "3832";
		public static final String verifyOrderStatusIfPaymentIsPVOrderStatusWithoutOnHoldDP_ITEM1 = "3832";
		public static final String verifyOrderStatusIfPaymentIsPVOrderStatusIsQAndOnholdDP_ITEM1 = "3832";
		public static final String bountyServiceCreateOrderBlankSupplyType_ITEM1 = "47466";
		public static final String bountyServiceCreateOrderJIT_ITEM1 = "818509";
		public static final String LOGIN_URL_Decline = "notifyme763@gmail.com";
		public static final String PASSWORD_Decline = "myntra@123";

	}

	public static class Pincodes{
		public static final String PINCODE_560068 = "560068";
	}

}
