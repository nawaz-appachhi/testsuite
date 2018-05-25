/**
 * 
 */
package com.myntra.apiTests.erpservices.oms.dp;

import java.util.ArrayList;
import java.util.List;

import com.myntra.apiTests.erpservices.oms.SkuTriplet;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants;
import com.myntra.apiTests.erpservices.oms.VatTestData;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.oms.TaxationUtil;
import com.myntra.lordoftherings.boromir.DBUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import com.myntra.apiTests.erpservices.oms.OrderDetails;
import com.myntra.apiTests.erpservices.VatTestConstants;

/**
 * @author puneet.khanna1@myntra.com
 * @since June 2016
 *
 */
public class TaxationTestsDataProvider {
	
	
	private static Logger log = LoggerFactory.getLogger(TaxationTestsDataProvider.class);
	private static String userName = "connect2pkhanna@gmail.com";
	private static String password = "123456";
	private static String destinationAddress = "560068";
	private static String uIdx = "17fb26c8.f6d0.45a2.a711.4101c9b37f36UjP3uBVI4l";
	private static String couponIdFor22PercentDiscount = "puneet22";
	private static String giftcard = "";
	private static Long vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
	private static String supplyTypeOnHand= "ON_HAND";
	private static IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	private static ATPServiceHelper atpServiceHelper = new ATPServiceHelper();


	@DataProvider
	public static Object[][] getDataForRefundInitiationTestsForCodAndCashback(
			ITestContext testContext) throws Exception {

		// The data to place a COD order for quantity 1 which goes to GURGAON_WH
			List<SkuTriplet> singleItemCod_LowerWH = new ArrayList<SkuTriplet>();
		singleItemCod_LowerWH.add(new SkuTriplet(VatTestConstants.SKU_GURGAON, 1, new String[] { VatTestConstants.GURGAON_WH }));
		VatTestData singleItemCod_LowTaxratedWH = new VatTestData(
				"singleItemCod_LowTaxratedWH", userName, password, destinationAddress,
				couponIdFor22PercentDiscount, false, false, false, giftcard, singleItemCod_LowerWH);

		Object[] singleItemCod_LowerTaxratedWH = getOneDArray_VatTestDataPassed(
				singleItemCod_LowTaxratedWH);

		// The data to place a COD order for quantity 2 which goes to GURGAON_WH
		List<SkuTriplet> multiItemCod_LowWH = new ArrayList<SkuTriplet>();
		multiItemCod_LowWH.add(new SkuTriplet(VatTestConstants.SKU_GURGAON, 2, new String[] { VatTestConstants.GURGAON_WH}));
		VatTestData multiItemCod_LowTaxratedWH = new VatTestData(
				"multiItemCod_LowTaxratedWH", userName, password, destinationAddress,
				couponIdFor22PercentDiscount, false, false, false, giftcard, multiItemCod_LowWH);

		Object[] multiItemCodOrderToLowerWareHouseParams = getOneDArray_VatTestDataPassed(
				multiItemCod_LowTaxratedWH);

		// The data to place a CashBack order for quantity 1 which goes to GURGAON_WH 
		List<SkuTriplet> singleItemCashBackOrderToLowerWareHouseSkuList = new ArrayList<SkuTriplet>();
		singleItemCashBackOrderToLowerWareHouseSkuList.add(new SkuTriplet(VatTestConstants.SKU_GURGAON, 1, new String[] { VatTestConstants.GURGAON_WH }));
		VatTestData singleItemCBOrder_LowTaxratedWH = new VatTestData(
				"singleItemCBOrder_LowTaxratedWH", userName, password, destinationAddress,
				couponIdFor22PercentDiscount, false, false, false, giftcard,
				singleItemCashBackOrderToLowerWareHouseSkuList);

		Object[] singleItemCashBackOrderToLowerTaxratedWareHouseParams = getOneDArray_VatTestDataPassed(
				singleItemCBOrder_LowTaxratedWH);
		
		// The data to place a CashBack order for quantity 2 which goes to GURGAON_WH 
		List<SkuTriplet> multiItemCashBackOrderToLowerWareHouseSkuList = new ArrayList<SkuTriplet>();
		multiItemCashBackOrderToLowerWareHouseSkuList.add(new SkuTriplet(VatTestConstants.SKU_GURGAON, 2, new String[] { VatTestConstants.GURGAON_WH}));
		VatTestData multiItemCashBackOrderToLowerTaxratedWareHouseData = new VatTestData(
				"multiItemCashBackOrderToLowerTaxratedWareHouseData", userName, password, destinationAddress,
				couponIdFor22PercentDiscount, false, false, false, giftcard,
				multiItemCashBackOrderToLowerWareHouseSkuList);

		Object[] multiItemCashBackOrderToLowerWareHouseParams = getOneDArray_VatTestDataPassed(
				multiItemCashBackOrderToLowerTaxratedWareHouseData);

		// The data to place a CashBack order with additional charges for quantity 2 which goes to GURGAON_WH 
			List<SkuTriplet> multiItemCashBackOrderWithAdditionalChargesToLowerWareHouseSkuList = new ArrayList<SkuTriplet>();
			multiItemCashBackOrderWithAdditionalChargesToLowerWareHouseSkuList.add(new SkuTriplet(VatTestConstants.SKU_GURGAON, 2, new String[] { VatTestConstants.GURGAON_WH }));
		VatTestData multiItemCashBackOrderWithAdditionalChargesToLowerTaxratedWareHouseData = new VatTestData(
				"multiItemCashBackOrderWithAdditionalChargesToLowerTaxratedWareHouseData", userName, password, destinationAddress,
				couponIdFor22PercentDiscount, false, false, true, giftcard,
				multiItemCashBackOrderWithAdditionalChargesToLowerWareHouseSkuList  );

		Object[] multiItemCashBackOrderWithAdditionalChargesToLowerWareHouseParams = getOneDArray_VatTestDataPassed(
				multiItemCashBackOrderWithAdditionalChargesToLowerTaxratedWareHouseData);

		Object[][] dataSet = new Object[][] { multiItemCashBackOrderWithAdditionalChargesToLowerWareHouseParams, singleItemCod_LowerTaxratedWH,multiItemCodOrderToLowerWareHouseParams,
					singleItemCashBackOrderToLowerTaxratedWareHouseParams,multiItemCashBackOrderToLowerWareHouseParams	};
		log.info("The total test orders places  by dataprovider getDataForRefundInitiationTestsForCodAndCashback were "+ dataSet.length);			
		return dataSet;

	}

	@DataProvider
	public static Object[][] getDataForNoRefundInitiationTest(
			ITestContext testContext) throws Exception {
		
		// The data to place a CashBack order for quantity 1 which goes to BANGALORE_WH 
		List<SkuTriplet> singleItemOrderToHigherWareHouseSkuList = new ArrayList<SkuTriplet>();
		singleItemOrderToHigherWareHouseSkuList.add(new SkuTriplet(VatTestConstants.SKU_BANGALORE, 1, new String[] { VatTestConstants.BANGALORE_WH  }));
		VatTestData singleItemOrderToLowerTaxratedWareHouseData = new VatTestData(
				"singleItemDataForNoRefundInitiationTest", userName, password, destinationAddress,
				couponIdFor22PercentDiscount, false, false, false, giftcard, singleItemOrderToHigherWareHouseSkuList);

		Object[] singleItemOrderToLowerTaxratedWareHouseParams = getOneDArray_VatTestDataPassed(
				singleItemOrderToLowerTaxratedWareHouseData);

		// The data to place a COD order for quantity >1 which goes to BANGALORE_WH 
		List<SkuTriplet> multiItemOrdr_HighWHSkuList = new ArrayList<SkuTriplet>();
		multiItemOrdr_HighWHSkuList.add(new SkuTriplet(VatTestConstants.SKU_BANGALORE, 10, new String[] { VatTestConstants.BANGALORE_WH  }));
		VatTestData multiItemOrderToLowerTaxratedWareHouseData = new VatTestData(
				"multiItemDataForNoRefundInitiationTest", userName, password, destinationAddress,
				couponIdFor22PercentDiscount, false, false, false, giftcard, multiItemOrdr_HighWHSkuList);

		Object[] multiItemOrderToLowerWareHouseParams = getOneDArray_VatTestDataPassed(
				multiItemOrderToLowerTaxratedWareHouseData);
		
		
		Object[][] dataSet = new Object[][] { singleItemOrderToLowerTaxratedWareHouseParams,
			multiItemOrderToLowerWareHouseParams };
			log.info("The total test orders places  by dataprovider getDataForNoRefundInitiationTest were "+ dataSet.length);	
		return dataSet;

	}

	@DataProvider
	public static Object[][] getDataForRefundInitiationTestForSplitSkuCashbackOrder(ITestContext testContext) throws Exception {
		
		// The data to place a CashBack order for even number of quantities with extra charges which splits between  BANGALORE_WH and GURGAON_WH
		List<SkuTriplet> evenSingleSkuSplit_ExtraCharges = new ArrayList<SkuTriplet>();
		evenSingleSkuSplit_ExtraCharges.add(new SkuTriplet(VatTestConstants.SKU_BLR_GRN, 4, new String[] { VatTestConstants.BANGALORE_WH , VatTestConstants.GURGAON_WH }));
		VatTestData evenItemsSkuSplitOrderVatDataWithExtraCharges = new VatTestData("evenItemsDataForRefundInitiationTestForSplitSkuCashbackOrder", userName, password,
				destinationAddress, couponIdFor22PercentDiscount, false, false, true, giftcard,
				evenSingleSkuSplit_ExtraCharges);

		Object[] evenSkuSplitOrder_ExtraCharges = getOneDArray_VatTestDataPassed(
				evenItemsSkuSplitOrderVatDataWithExtraCharges);
		
		// The data to place a CashBack order for odd number of quantities with extra charges which splits between  BANGALORE_WH and GURGAON_WH
		List<SkuTriplet> oddItemSkuSplitOrderDataWithExtraCharges = new ArrayList<SkuTriplet>();
		oddItemSkuSplitOrderDataWithExtraCharges.add(new SkuTriplet(VatTestConstants.SKU_BLR_GRN, 7, new String[] { VatTestConstants.BANGALORE_WH , VatTestConstants.GURGAON_WH }));
		VatTestData oddItemOrderSplitOrderWithExtraCharges = new VatTestData("oddItemsDataForRefundInitiationTestForSplitSkuCashbackOrder",
				userName, password, destinationAddress, couponIdFor22PercentDiscount, false, false, true, giftcard,
				oddItemSkuSplitOrderDataWithExtraCharges);

		Object[] oddItemSplit_ExtraCharges = getOneDArray_VatTestDataPassed(
				oddItemOrderSplitOrderWithExtraCharges);
			 
		
		Object[][] dataSet = new Object[][] {evenSkuSplitOrder_ExtraCharges, oddItemSplit_ExtraCharges};
		log.info("The total test orders places  by dataprovider getDataForRefundInitiationTestForSplitSkuCashbackOrder were "+ dataSet.length);
		return dataSet;
		
		
		

	}
	
	@DataProvider
	public static Object[][] getDataForRefundInitiationTestForSplitSkuCodOrder(ITestContext testContext) throws Exception {

		// The data to place a COD order for even number of quantities which splits between  BANGALORE_WH and GURGAON_WH
		List<SkuTriplet> evenSingleSkuSplitOrderDataWithExtraCharges = new ArrayList<SkuTriplet>();
		evenSingleSkuSplitOrderDataWithExtraCharges.add(new SkuTriplet(VatTestConstants.SKU_BLR_GRN, 4, new String[] { VatTestConstants.BANGALORE_WH , VatTestConstants.GURGAON_WH  }));
		VatTestData evenItemsSkuSplitOrderVatDataWithExtraCharges = new VatTestData("evenItemsDataForRefundInitiationTestForSplitSkuCodOrder", userName, password,
				destinationAddress, couponIdFor22PercentDiscount, false, false, true, giftcard,
				evenSingleSkuSplitOrderDataWithExtraCharges);

		Object[] evenSkuSplitOrderDataParamsWithExtraChargesParams = getOneDArray_VatTestDataPassed(
				evenItemsSkuSplitOrderVatDataWithExtraCharges);
		
		// The data to place a COD order for odd number of quantities which splits between  BANGALORE_WH and GURGAON_WH
		List<SkuTriplet> oddItemSkuSplitOrderDataWithExtraCharges = new ArrayList<SkuTriplet>();
		oddItemSkuSplitOrderDataWithExtraCharges.add(new SkuTriplet(VatTestConstants.SKU_BLR_GRN, 7, new String[] { VatTestConstants.BANGALORE_WH , VatTestConstants.GURGAON_WH  }));
		VatTestData oddItemOrderSplitOrderWithExtraCharges = new VatTestData("oddItemsDataForRefundInitiationTestForSplitSkuCodOrder",
				userName, password, destinationAddress, couponIdFor22PercentDiscount, false, false, false, giftcard,
				oddItemSkuSplitOrderDataWithExtraCharges);

		Object[] oddItemSplitOrderDataParamsWithExtraCharges = getOneDArray_VatTestDataPassed(
				oddItemOrderSplitOrderWithExtraCharges);
			 
		
		Object[][] dataSet = new Object[][] { evenSkuSplitOrderDataParamsWithExtraChargesParams,oddItemSplitOrderDataParamsWithExtraCharges };
		log.info("The total test orders places  by dataprovider getDataForRefundInitiationTestForSplitSkuCodOrder were "+ dataSet.length);
		return dataSet;

	}
	
	@DataProvider
	public static Object[][] getDataForRefundAndNoRefundTestForMixedCashBackOrder(ITestContext testContext) throws Exception {
		
		// The data to place a Cashaback order with distinct sku's (sku's which are mapped distinctively to GURGAON_WH/BANGALORE_WH
		
		List<SkuTriplet> Rfd_NonRefund_SkuSplitCBOrder = new ArrayList<SkuTriplet>();

		Rfd_NonRefund_SkuSplitCBOrder.add(new SkuTriplet(VatTestConstants.SKU_GURGAON, 7, new String[] { VatTestConstants.GURGAON_WH }));
		Rfd_NonRefund_SkuSplitCBOrder.add(new SkuTriplet(VatTestConstants.SKU_BANGALORE, 7, new String[] { VatTestConstants.BANGALORE_WH }));

		VatTestData RFD_NonRFDVatData = new VatTestData("MixedCashBackOrder", userName,
				password, destinationAddress, couponIdFor22PercentDiscount, true, false, false, giftcard,
				Rfd_NonRefund_SkuSplitCBOrder);

		Object[] mixedVatTestDataParams = getOneDArray_VatTestDataPassed(
				RFD_NonRFDVatData);

		Object[][] dataSet = new Object[][] { mixedVatTestDataParams };
		log.info("The total test orders places  by dataprovider getDataForRefundAndNoRefundTestForMixedCashBackOrder were "+ dataSet.length);
		return dataSet;

	}

	
	@DataProvider
	public static Object[][] getDataForRefundAndNoRefundTestForMixedCodOrder(ITestContext testContext) throws Exception {
		// The data to place a COD order with distinct sku's (sku's which are mapped distinctively to GURGAON_WH/BANGALORE_WH
		List<SkuTriplet> Rfd_NonRfd_SkuSplitCodOdr = new ArrayList<SkuTriplet>();

		Rfd_NonRfd_SkuSplitCodOdr.add(new SkuTriplet(VatTestConstants.SKU_GURGAON, 7, new String[] { VatTestConstants.GURGAON_WH }));
		Rfd_NonRfd_SkuSplitCodOdr.add(new SkuTriplet(VatTestConstants.SKU_BANGALORE, 7, new String[] { VatTestConstants.BANGALORE_WH }));

		VatTestData refundableAndNonRefundableVatDataParams = new VatTestData("MixedCodOrder", userName,
				password, destinationAddress, couponIdFor22PercentDiscount, false, false, false, giftcard,
				Rfd_NonRfd_SkuSplitCodOdr);

		Object[] mixedVatTestDataParams = getOneDArray_VatTestDataPassed(
				refundableAndNonRefundableVatDataParams);
		
		Object[][] dataSet = new Object[][] { mixedVatTestDataParams };
		log.info("The total test orders places  by dataprovider getDataForRefundAndNoRefundTestForMixedCodOrder were "+ dataSet.length);
		return dataSet;

	}

	@DataProvider
	public static Object[][] getDataforSingleSku_MultiRelease(
			ITestContext testContext) throws Exception {
		List resultSet = null;
		// The data to place a COD order for quantity >1 which goes to BANGALORE_WH
		List<SkuTriplet> multiQtyOrdr = new ArrayList<SkuTriplet>();
		multiQtyOrdr.add(new SkuTriplet(3867, 2, new String[] { VatTestConstants.BANGALORE_WH,VatTestConstants.GURGAON_WH }));
		VatTestData data = new VatTestData(
				"multiItemDataForNoRefundInitiationTest", userName, password, destinationAddress,
				couponIdFor22PercentDiscount, false, false, false, giftcard, multiQtyOrdr);


		log.info("Going to place order for the data :: "+ data.toString());
		//TaxationUtil.setUpInventoryForTheOrder(dataInv,uIdx);
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{3867+":"+VatTestConstants.BANGALORE_WH+":2:0:"+19+":1"}, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[] {3836+":"+ VatTestConstants.BANGALORE_WH +","+VatTestConstants.GURGAON_WH+ ":1:0:"+19},supplyTypeOnHand);


		// Constructed to return from data provider for easily availability for tests
		String[]	skuIdAndQuantityTupleArray	= TaxationUtil.getskuIdAndQuantityTupleArray(data);
		log.info("skuIdAndQuantityTupleArray for the order is  "+ skuIdAndQuantityTupleArray.toString());

		OrderDetails placedOrder = TaxationUtil.placeAnOrderAndReturnOrderDetails(data.getUsername(), data.getPassword() ,data.getDestinationAddress(),
				skuIdAndQuantityTupleArray, data.getCouponId(),data.isCashbackOrder() ,data.isIfLoyalityPointsUsed(),
				data.isGiftWrapOpted(),"");
		placedOrder.getOrderId();
		resultSet=DBUtilities.exSelectQuery("Select order_release_id_fk,sku_id  from order_line where order_id_fk="+placedOrder.getOrderId()+";","myntra_oms");

		log.info("Details of the placed Order : "+ placedOrder.toString());

		Object[] multiItemOrderToLowerWareHouseParams = { placedOrder, data.isCashbackOrder(), skuIdAndQuantityTupleArray,resultSet.get(0),resultSet.get(1) };


		Object[][] dataSet = new Object[][] {
				multiItemOrderToLowerWareHouseParams };
		log.info("The total test orders places  by dataprovider getDataForNoRefundInitiationTest were "+ dataSet.length);
		return dataSet;

	}


	@DataProvider
	public static Object[][] getDataforMultiSku_MultiRelease_AllRefund(
			ITestContext testContext) throws Exception {

		// The data to place a COD order for quantity >1 which goes to BANGALORE_WH
		List<SkuTriplet> multiQtyOrdr = new ArrayList<SkuTriplet>();
		multiQtyOrdr.add(new SkuTriplet(3836, 2, new String[] { VatTestConstants.BANGALORE_WH,VatTestConstants.GURGAON_WH }));
		multiQtyOrdr.add(new SkuTriplet(3837, 2, new String[] { VatTestConstants.BANGALORE_WH,VatTestConstants.GURGAON_WH }));
		VatTestData data = new VatTestData(
				"multiItemDataForNoRefundInitiationTest", OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestLogin, OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestPassword, destinationAddress,
				couponIdFor22PercentDiscount, false, false, false, giftcard, multiQtyOrdr);

		log.info("Going to place order for the data :: "+ data.toString());

		TaxationUtil.updateAtp(3836, 2);
		TaxationUtil.updateAtp(3837, 2);
		imsServiceHelper.updateInventoryForSeller(new String[] {3836+":"+ VatTestConstants.BANGALORE_WH +","+VatTestConstants.GURGAON_WH+ ":1:0:"+vectorSellerID},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[] {3837+":"+ VatTestConstants.BANGALORE_WH +","+VatTestConstants.GURGAON_WH+ ":1:0:"+vectorSellerID},supplyTypeOnHand);


		// Constructed to return from data provider for easily availability for tests
		String[]	skuIdAndQuantityTupleArray	=TaxationUtil.getskuIdAndQuantityTupleArray(data);
		log.info("skuIdAndQuantityTupleArray for the order is  "+ skuIdAndQuantityTupleArray.toString());

		OrderDetails placedOrder = TaxationUtil.placeAnOrderAndReturnOrderDetails(data.getUsername(), data.getPassword() ,data.getDestinationAddress(),
				skuIdAndQuantityTupleArray, data.getCouponId(),data.isCashbackOrder() ,data.isIfLoyalityPointsUsed(),
				data.isGiftWrapOpted(),"");
		placedOrder.getOrderDetails();

		log.info("Details of the placed Order : "+ placedOrder.toString());

		Object[] multiItemOrderToLowerWareHouseParams = { placedOrder, data.isCashbackOrder(), skuIdAndQuantityTupleArray };

		Object[][] dataSet = new Object[][] {
				multiItemOrderToLowerWareHouseParams };
		log.info("The total test orders places  by dataprovider getDataForNoRefundInitiationTest were "+ dataSet.length);
		return dataSet;

	}


	@DataProvider
	public static Object[][] getDataforMultiSku_MultiRelease_NotAllRefund(
			ITestContext testContext) throws Exception {

		// The data to place a COD order for quantity >1 which goes to BANGALORE_WH
		List<SkuTriplet> multiQtyOrdr = new ArrayList<SkuTriplet>();
		multiQtyOrdr.add(new SkuTriplet(3836, 2, new String[] { VatTestConstants.BANGALORE_WH,VatTestConstants.GURGAON_WH }));
		multiQtyOrdr.add(new SkuTriplet(3837, 2, new String[] { VatTestConstants.BANGALORE_WH,VatTestConstants.GURGAON_WH }));
		multiQtyOrdr.add(new SkuTriplet(3898, 1, new String[] { VatTestConstants.BANGALORE_WH,VatTestConstants.GURGAON_WH }));
		VatTestData data = new VatTestData(
				"multiItemDataForNoRefundInitiationTest", OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestLogin, OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestPassword, destinationAddress,
				couponIdFor22PercentDiscount, false, false, false, giftcard, multiQtyOrdr);

		log.info("Going to place order for the data :: "+ data.toString());
		//TaxationUtil.setUpInventoryForTheOrder(dataInv,uIdx);
		TaxationUtil.updateAtp(3836, 2);
		TaxationUtil.updateAtp(3837, 2);
		TaxationUtil.updateAtp(3898, 1);
		imsServiceHelper.updateInventoryForSeller(new String[] {3836+":"+ VatTestConstants.BANGALORE_WH +","+VatTestConstants.GURGAON_WH+ ":1:0:"+vectorSellerID},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[] {3837+":"+ VatTestConstants.BANGALORE_WH +","+VatTestConstants.GURGAON_WH+ ":2:0:"+vectorSellerID},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[] {3898+":"+ VatTestConstants.BANGALORE_WH +","+VatTestConstants.GURGAON_WH+ ":2:0:"+vectorSellerID},supplyTypeOnHand);

		// Constructed to return from data provider for easily availability for tests
		String[]	skuIdAndQuantityTupleArray	=TaxationUtil.getskuIdAndQuantityTupleArray(data);
		log.info("skuIdAndQuantityTupleArray for the order is  "+ skuIdAndQuantityTupleArray.toString());

		OrderDetails placedOrder = TaxationUtil.placeAnOrderAndReturnOrderDetails(data.getUsername(), data.getPassword() ,data.getDestinationAddress(),
				skuIdAndQuantityTupleArray, data.getCouponId(),data.isCashbackOrder() ,data.isIfLoyalityPointsUsed(),
				data.isGiftWrapOpted(),"");
		placedOrder.getOrderDetails();

		log.info("Details of the placed Order : "+ placedOrder.toString());

		Object[] multiItemOrderToLowerWareHouseParams = { placedOrder, data.isCashbackOrder(), skuIdAndQuantityTupleArray };


		Object[][] dataSet = new Object[][] {
				multiItemOrderToLowerWareHouseParams };
		log.info("The total test orders places  by dataprovider getDataForNoRefundInitiationTest were "+ dataSet.length);
		return dataSet;

	}

	/** The method reads VatTestData object passed ,sets up inventory ,places an order 
	 * and returns array{placedOrder, data.isCashbackOrder(), skuIdAndQuantityTupleArray}
	 * @param data
	 * @return
	 * @throws Exception
	 */
	static Object[] getOneDArray_VatTestDataPassed(VatTestData data) throws Exception {
		
		log.info("Going to place order for the data :: "+ data.toString());
		TaxationUtil.setUpInventoryForTheOrder(data,uIdx);
		
		// Constructed to return from data provider for easily availability for tests
	    String[]	skuIdAndQuantityTupleArray	=TaxationUtil.getskuIdAndQuantityTupleArray(data);
	    log.info("skuIdAndQuantityTupleArray for the order is  "+ skuIdAndQuantityTupleArray.toString());

		OrderDetails placedOrder = TaxationUtil.placeAnOrderAndReturnOrderDetails(data.getUsername(), data.getPassword() ,data.getDestinationAddress(),
				skuIdAndQuantityTupleArray, data.getCouponId(),data.isCashbackOrder() ,data.isIfLoyalityPointsUsed(),
				data.isGiftWrapOpted(),"");
		
		log.info("Details of the placed Order : "+ placedOrder.toString()); 
		
		Object[] dataArray = { placedOrder, data.isCashbackOrder(), skuIdAndQuantityTupleArray };
		return dataArray;
	}



}
