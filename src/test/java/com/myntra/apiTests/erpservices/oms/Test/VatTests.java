package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.dp.TaxationTestsDataProvider;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OrderDetails;
import com.myntra.apiTests.erpservices.VatTestConstants;
import com.myntra.apiTests.erpservices.partners.SellerPaymentServiceHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.paymentplan.domain.response.ExchangeOrderResponse;
import com.myntra.test.commons.testbase.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class VatTests extends BaseTest {
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();

	private static Logger log = LoggerFactory.getLogger(VatTests.class);

	/*
	 * The test verifies the refund initiation for different orders passed to it
	 * by TaxationTestsDataProvider
	 */

	@Test(groups = {
			"regression" }, dataProvider = "getDataForRefundInitiationTestsForCodAndCashback", dataProviderClass = TaxationTestsDataProvider.class)
	public void testRefundInitiationForCODAndCashBack(OrderDetails placedOrder, boolean isCashBackOrder,
                                                      String[] skuAndQuantityPassedToTest) throws Exception {
		boolean refundSuccess = false;
		log.info("The order being verified is " + placedOrder.toString());
		Set<Integer> orderLineIds = placedOrder.getAllOrderLineIDs();
		for (Integer orderLineId : orderLineIds) {
			log.info("The order line under test is " + orderLineId);
			refundSuccess = placedOrder.waitAndVerifyIfVatRefundIsInitiated(orderLineId,
					VatTestConstants.WAITTIME_REFUND_INITIATE);
			Assert.assertTrue(refundSuccess, "The refund did not go for orderLine " + orderLineId);
		}

	}



	/*
	 * The test verifies the no refund initiation for different orders passed to
	 * it by TaxationTestsDataProvider
	 */

	@Test(groups = {
			"regression" }, dataProvider = "getDataForNoRefundInitiationTest", dataProviderClass = TaxationTestsDataProvider.class)
	public void testNoRefundInitiationForSingleOrMultiQuantityForSingleSku(OrderDetails placedOrder,
			boolean isCashBackOrder, String[] skuAndQuantityPassedToTest) throws Exception {

		log.info("The order being verified is " + placedOrder.toString());
		for (Integer orderLineId : placedOrder.getAllOrderLineIDs()) {
			
			log.info("The order line under test is  " + orderLineId);
			
			Assert.assertFalse(placedOrder.waitAndVerifyIfVatRefundIsInitiated(orderLineId,
							VatTestConstants.WAITTIME_NO_REFUND_INITIATIATE),"The refund was triggered...");
			
			log.info("As expected,The refund was not initiated for orderLine" + orderLineId);
		}
	}

	/*
	 * The test verifies the refund initiation and no refund initiation for
	 * CashBack based Mixed (of order lines lead to refund and order lines which
	 * do not) orders passed to it by TaxationTestsDataProvider
	 */  
	@Test(groups = {
			"regression" }, dataProvider = "getDataForRefundAndNoRefundTestForMixedCashBackOrder", dataProviderClass = TaxationTestsDataProvider.class)
	public void testRefundAndNoRefundForMixedCashBackOrder(OrderDetails placedOrder, boolean isCashBackOrder,
			String[] skuAndQuantityPassedToTest) throws Exception {
		boolean refundSuccess = false;

		log.info("The order being verified is " + placedOrder.toString());
		Set<Integer> orderLineIds = placedOrder.getAllOrderLineIDs();

		for (Integer orderLineId : orderLineIds) {
			log.info("The order line under test is  " + orderLineId);
			
			boolean ifAssignedWareHouseRefunds = ((List<String>) Arrays.asList(VatTestConstants.LOW_TAX_WAREHOUSE))
					.contains("" + placedOrder.getWareHouseIdByOrderLineId(orderLineId));
			
			if (ifAssignedWareHouseRefunds) {
				log.info("verifying that refund is initiated ");
				refundSuccess = placedOrder.waitAndVerifyIfVatRefundIsInitiated(orderLineId,VatTestConstants.WAITTIME_REFUND_INITIATE);
				// as the value of ifAssignedWareHouseRefunds is true,the
				// refund must be initiated from OMS
				Assert.assertTrue(refundSuccess, "The refund did not go");

			} else if (!ifAssignedWareHouseRefunds) {
				log.info("verifying that refund is not initiated ");
				// verify if the orderline is coming from any warehouse with tax
				// rate equal to which comes from cart ,OMS should not refund
				Assert.assertFalse(
						placedOrder.waitAndVerifyIfVatRefundIsInitiated(orderLineId,
								VatTestConstants.WAITTIME_NO_REFUND_INITIATIATE),
						"The refund was triggered...");
				
				log.info("As extected,The refund was not initiated for orderLine" + orderLineId);
			}
		}
	}

	/*
	 * The test verifies the refund initiation and no refund initiation for COD
	 * based Mixed (of order lines lead to refund and order lines which do not)
	 * orders passed to it by TaxationTestsDataProvider
	 */
	@Test(groups = {
			"regression" }, dataProvider = "getDataForRefundAndNoRefundTestForMixedCodOrder", dataProviderClass = TaxationTestsDataProvider.class)
	public void testRefundAndNoRefundForMixedCodOrder(OrderDetails placedOrder, boolean isCashBackOrder,
			String[] skuAndQuantityPassedToTest) throws Exception {

		boolean refundSuccess = false;
		log.info("The order being verified is " + placedOrder.toString());
		Set<Integer> orderLineIds = placedOrder.getAllOrderLineIDs();

		for (Integer orderLineId : orderLineIds) {
			log.info("The order line under test is  " + orderLineId);
			boolean ifAssignedWareHouseRefunds = ((List<String>) Arrays.asList(VatTestConstants.LOW_TAX_WAREHOUSE))
					.contains("" + placedOrder.getWareHouseIdByOrderLineId(orderLineId));
			if (ifAssignedWareHouseRefunds) {

				refundSuccess = placedOrder.waitAndVerifyIfVatRefundIsInitiated(orderLineId,
						VatTestConstants.WAITTIME_REFUND_INITIATE);
				// as the value of ifAssignedWareHouseRefunds is true,the
				// refund must be initiated from OMS
				Assert.assertTrue(refundSuccess, "The refund did not go");
				log.info("The refund was initiated for orderLine" + orderLineId);
			} else if (!ifAssignedWareHouseRefunds) {
				// verify if the orderline is coming from any warehouse with tax
				// rate equal to or more which comes from cart ,OMS should not refund
				Assert.assertFalse(placedOrder.waitAndVerifyIfVatRefundIsInitiated(orderLineId,
								VatTestConstants.WAITTIME_NO_REFUND_INITIATIATE),"The refund was triggered...");
				
				log.info("As expected,The refund was not initiated for orderLine" + orderLineId);
			}
		}
	}

	/*
	 * The test verifies the refund initiation and checks right amount is
	 * refnded for all the order lines per sku when the order is splitted to a
	 * lower tax rated warehouse and higher tax rated warehouse
	 */
	@Test(groups = {
			"regression" }, dataProvider = "getDataForRefundInitiationTestForSplitSkuCashbackOrder", dataProviderClass = TaxationTestsDataProvider.class)
	public void testRefundForSkuSplitCashbackOrder(OrderDetails placedOrder, boolean isCashBackOrder,
			String[] skuAndQuantityPassedToTest) throws Exception {
		log.info("The order being verified is " + placedOrder.toString());
		HashMap<Integer, List<Integer>> orderLineListperSkuMap = placedOrder.getOrderLineListperSkuMap();

		for (Integer skuCode : orderLineListperSkuMap.keySet()) {

			log.info("The sku picked is " + skuCode.toString());

			Double expectedRefundAmountForAllWareHousesPerSku = null;

			// try to get expectedRefundAmountForAllWareHousesPerSku which will
			// be used for future comparisons
			for (Integer orderLine : orderLineListperSkuMap.get(skuCode)) {
				log.info("The order line under test is  " + orderLine);
				if (((List<String>) Arrays.asList(VatTestConstants.LOW_TAX_WAREHOUSE))
						.contains("" + placedOrder.getWareHouseIdByOrderLineId(orderLine))) {
					placedOrder.waitAndVerifyIfVatRefundIsInitiated(orderLine,VatTestConstants.WAITTIME_REFUND_INITIATE);
					expectedRefundAmountForAllWareHousesPerSku = (double) Math.round(placedOrder.getActualRefundedAmountForOrderLine(orderLine));
					log.info("The expected refund amount for all orderLines is "+ expectedRefundAmountForAllWareHousesPerSku);
					break;
				}
			}

			log.info("Going to verify that the actual refund amount for all the orderLines is "
					+ expectedRefundAmountForAllWareHousesPerSku.toString());

			for (Integer orderLineId : orderLineListperSkuMap.get(skuCode)) {
				log.info("The order line under test is  " + orderLineId);

				// as additional Info table is updated with wareHouseLocal tax rate , 
				// we can pro-actively estimate the tax and compare with actual refund and
				// expectedRefundAmountForAllWareHousesPerSku
				if (((List<String>) Arrays.asList(VatTestConstants.LOW_TAX_WAREHOUSE))
						.contains("" + placedOrder.getWareHouseIdByOrderLineId(orderLineId))) {
					// Wait for VAT Tax stamping and the refund to finish
					placedOrder.waitAndVerifyIfVatRefundIsInitiated(orderLineId, 6);

					// get expectedTax based on pro rated tax calculation
					Double expectedUnitVatRefundAmountByOrderLine = (double) Math
							.round(placedOrder.getExpectedUnitVatRefundAmountByOrderLine(orderLineId));
					// get the actual stamped by the system
					Double actualRefundedAmountForOrderLine = (double) Math
							.round(placedOrder.getActualRefundedAmountForOrderLine(orderLineId));

					// compare if both expected and actual generated by the system are equal and are equal to
					// expectedRefundAmountForAllWareHousesPerSku as well
					Assert.assertTrue(
							((expectedUnitVatRefundAmountByOrderLine.compareTo(actualRefundedAmountForOrderLine) == 0)
									& (actualRefundedAmountForOrderLine
											.compareTo(expectedRefundAmountForAllWareHousesPerSku) == 0)),

					"The  actual Refunded Tax Amount For Lower Tax Rated Warehouse :- "
							+ actualRefundedAmountForOrderLine.toString()
							+ " is not equal to the expected Refunded Tax Amount For Lower Tax Rated Warehosue  which is : "
							+ expectedUnitVatRefundAmountByOrderLine.toString()
							+ " or is not equal to expected refund amount for all warehouses which is :"
							+ expectedRefundAmountForAllWareHousesPerSku.toString());

				} else {

					// as additional Info table is not updated with
					// wareHouseLocal tax rate for order to higher TaxRated WH
					// we compare actual tax in DB
					// withexpectedRefundAmountForAllWareHousesPerSku
					Double actualRefundedAmountForOrderLine = (double) Math
							.round(placedOrder.getActualRefundedAmountForOrderLine(orderLineId));
					Assert.assertTrue(
							actualRefundedAmountForOrderLine.compareTo(expectedRefundAmountForAllWareHousesPerSku) == 0,
							"The  expected Refunded Tax Amount For all wareHouses  :: "
									+ expectedRefundAmountForAllWareHousesPerSku.toString()
									+ " is not equal to the actual Refunded Tax Amount For Higher Tax Rated Warehosue :: "
									+ actualRefundedAmountForOrderLine.toString());
				}

			}

		}
	}

	/*
	 * The test verifies vat refund adjustment for single sku and multiple release
	 *
	 */

	@Test(groups = {"Regression" }, dataProvider = "getDataforSingleSku_MultiRelease", dataProviderClass = TaxationTestsDataProvider.class)
	public void vatRefundforSingleSku_MultiRelease(OrderDetails placedOrder, boolean isCashBackOrder, String[] skuAndQuantityPassedToTest,String release_id,String sku_id) throws Exception {

		log.info("The order being verified is " + placedOrder.toString());

		for (Integer orderLineId : placedOrder.getAllOrderLineIDs()) {

			log.info("The order line under test is  " + orderLineId);

			Assert.assertTrue(placedOrder.waitAndVerifyIfVatRefundIsInitiated(orderLineId,
					VatTestConstants.WAITTIME_NO_REFUND_INITIATIATE),"The refund was triggered...");

			log.info("As expected,The refund was not initiated for orderLine" + orderLineId);
		}
	}

	@Test(groups = {"Regression" }, dataProvider = "getDataforMultiSku_MultiRelease_AllRefund", dataProviderClass = TaxationTestsDataProvider.class)
	public void vatRefundforMultipleSku_MultiRelease_AllRefund(OrderDetails placedOrder, boolean isCashBackOrder, String[] skuAndQuantityPassedToTest) throws Exception {

		log.info("The order being verified is " + placedOrder.toString());

		for (Integer orderLineId : placedOrder.getAllOrderLineIDs()) {

			log.info("The order line under test is  " + orderLineId);

			Assert.assertTrue(placedOrder.waitAndVerifyIfVatRefundIsInitiated(orderLineId,
					VatTestConstants.WAITTIME_NO_REFUND_INITIATIATE),"The refund was triggered...");

			log.info("As expected,The refund was not initiated for orderLine" + orderLineId);
		}
	}

	@Test(groups = {"Regression" }, dataProvider = "getDataforMultiSku_MultiRelease_NotAllRefund", dataProviderClass = TaxationTestsDataProvider.class)
	public void vatRefundforMultipleSku_MultiRelease_NotAllRefund(OrderDetails placedOrder, boolean isCashBackOrder, String[] skuAndQuantityPassedToTest) throws Exception {

		log.info("The order being verified is " + placedOrder.toString());

		for (Integer orderLineId : placedOrder.getAllOrderLineIDs()) {

			log.info("The order line under test is  " + orderLineId);

			Assert.assertTrue(placedOrder.waitAndVerifyIfVatRefundIsInitiated(orderLineId,
					VatTestConstants.WAITTIME_NO_REFUND_INITIATIATE),"The refund was triggered...");

			log.info("As expected,The refund was not initiated for orderLine" + orderLineId);
		}
	}

	@Test(description = "End to End Create, Deliver and Exchange Order", priority =8)
	public void testE2EProcessExchange() throws Exception {
		atpServiceHelper.updateInventoryDetails(new String[] { "1243744:36:1000:0"},"ON_HAND");
		imsServiceHelper.updateInventory(new String[] { "1243744:36:1000:0"},"ON_HAND");
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		ProcessRelease processRelease = new ProcessRelease();
		SellerPaymentServiceHelper spsHelper = new SellerPaymentServiceHelper();
		LMSHelper lmsHelper = new LMSHelper();
		try {
			End2EndHelper end2EndHelper = new End2EndHelper();
			PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();

			String orderID = end2EndHelper.createOrder(OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestLogin, OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestPassword, "6132352", new String[]{"1243744:1"}, "", false, false, false, "", false);
			String orderReleaseId = omsServiceHelper.getReleaseId(orderID);
			boolean status = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 15);
			if (!status) {
				Assert.fail("Order Status is Not WP");
			}
			Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
			System.out.println("Order ID : " + orderID);

			List<ReleaseEntry> releaseEntries = new ArrayList<>();
			releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
			ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
			processRelease.processReleaseToStatus(releaseEntryList);

			Assert.assertEquals(spsHelper.validateOrderStatusInSPS(orderID, "RELEASED",15), true, "Unable to validate order in SPS");
			com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
			String lineID = orderReleaseEntry.getOrderLines().get(0).getId().toString();
			atpServiceHelper.updateInventoryDetails(new String[] { "1243744:28:1000:0","1243744:36:0:0","1251868:45:10000:0" },"ON_HAND");
			imsServiceHelper.updateInventory(new String[] { "1243744:28:1000:0"},"ON_HAND");
			ExchangeOrderResponse exchangeOrderResponse = (ExchangeOrderResponse) ppsServiceHelper.createExchange("" + orderID, "" + lineID, "DNL", 1, "1243744");
			if (!exchangeOrderResponse.getSuccess()) {
				Assert.fail("Exchange Creation failed");
			}

			// vat adjustment validation
			String exchangeOrderID = omsServiceHelper.getOrderEntry(exchangeOrderResponse.getExchangeOrderId()).getId().toString();

			List<ReleaseEntry> releaseEntries_Exe = new ArrayList<>();
			releaseEntries_Exe.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(exchangeOrderID), ReleaseStatus.DL).build());
			ReleaseEntryList releaseEntryList_Exe= new ReleaseEntryList(releaseEntries_Exe);
			processRelease.processReleaseToStatus(releaseEntryList_Exe);

			Assert.assertEquals(spsHelper.validateOrderStatusInSPS(orderID, "REFUNDED",15), true, "Unable to validate order in SPS");
			Assert.assertEquals(spsHelper.validateOrderStatusInSPS(exchangeOrderID, "RELEASED",15), true, "Unable to validate order in SPS");
			String returnID = lmsHelper.getReturnIdFromOrderId(""+orderReleaseId);
			Map<String, Object> pickup = DBUtilities.exSelectQueryForSingleRecord("select * from pickup where return_id = " + returnID, "lms");
			long destWarehouseId = (long) pickup.get("dest_warehouse_id");
			lmsServiceHelper.transferShipmentBackToWH(returnID, destWarehouseId, 5, "DC", "WH");
			} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

}
