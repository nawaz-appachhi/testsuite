package com.myntra.apiTests.erpservices.wms.Tests;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBException;

import com.myntra.apiTests.erpservices.wms.InternalOrderUtils;
import com.myntra.apiTests.erpservices.wms.dp.InternalOrderTestsDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.myntra.apiTests.erpservices.InternalOrderTestConstants;
import com.myntra.apiTests.erpservices.StnTestConstants;
import com.myntra.apiTests.erpservices.oms.StockTransferUtils;
import com.myntra.client.wms.codes.utils.InternalOrderType;
import com.myntra.test.commons.testbase.BaseTest;



/**
 * @author puneet.khanna1@myntra.com
 * The class covers the positive tests for Internal order feature
 *
 */
public class InternalOrderPositiveTests extends BaseTest {
	private static Logger log = LoggerFactory.getLogger(InternalOrderPositiveTests.class);

	/** The test covers the approval process for an internal order of type Buy to Buy
	 * Steps : 
	 * 1) Create the internal order and verify it was created
	 * 2) Create new items to associate with the internal order
	 * 3) Associate the items to the order
	 * 4) Send the IO for approval
	 * 5) Approve the internal order and verify approval
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
			"Regression"})
	public void testApprovalOfABuyToBuyInternalOrder() throws UnsupportedEncodingException, JAXBException {
		long createdInternalOrderId = InternalOrderUtils
				.createAnInternalOrderAndReturnInternalOrderId(InternalOrderType.B2B_Sales);
		List<String> items = InternalOrderUtils.createTestStoredItemsWithQualityQ2(
				InternalOrderTestConstants.DEFAULT_QUANTITY_OF_ITEMS_TO_TEST,
				InternalOrderTestConstants.DEFAULT_TEST_SKU_ID, InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID, 
				InternalOrderTestConstants.BUYER_ID);
		InternalOrderUtils.associateItemToTheBuyToBuyInternalOrder((String) items.get(0), createdInternalOrderId);
		InternalOrderUtils.sendInternalOrderForApprovalAndVerify(createdInternalOrderId);
		InternalOrderUtils.approveAnInternalOrderAndVerifyApproval(createdInternalOrderId);

	}

	/**The test covers the shipping process for an internal order of type Buy to Buy
	 * * Steps : 
	 * 1) Create the internal order and verify it was created
	 * 2) Create new items to associate with the internal order
	 * 3) Associate the items to the order
	 * 4) Send the IO for approval
	 * 5) Approve the internal order and verify approval
	 * 6)Send the IO for pick initiation
	 * 7) checkout the order and items 
	 * 8) Ship the internal order
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
	"Regression"})
	public void testShippingOfBuyToBuyInternalAprroval() throws UnsupportedEncodingException, JAXBException {
		long createdInternalOrderId = InternalOrderUtils
				.createAnInternalOrderAndReturnInternalOrderId(InternalOrderType.B2B_Sales);
		List<String> items = InternalOrderUtils.createTestStoredItemsWithQualityQ2(
				InternalOrderTestConstants.DEFAULT_QUANTITY_OF_ITEMS_TO_TEST,
				InternalOrderTestConstants.DEFAULT_TEST_SKU_ID, InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID,
				InternalOrderTestConstants.BUYER_ID);
		InternalOrderUtils.associateItemToTheBuyToBuyInternalOrder((String) items.get(0), createdInternalOrderId);
		InternalOrderUtils.sendInternalOrderForApprovalAndVerify(createdInternalOrderId);
		InternalOrderUtils.approveAnInternalOrderAndVerifyApproval(createdInternalOrderId);
		InternalOrderUtils.sendAnInternalOrderForPickInitiation(createdInternalOrderId);
		InternalOrderUtils.simulateItemAnOrderCheckoutEventInMaterialMovementsPage(createdInternalOrderId,
				(String) items.get(0));
		InternalOrderUtils.shipAnInternalOrderAndverify(createdInternalOrderId);

	}

	/** The test covers the approval process for an internal order of type Others and promotional goods
	 * * Steps : 
	 * 1) Create the internal order and verify it was created
	 * 2) Associate the SKU to the order
	 * 3) Send the IO for approval
	 * 4) Approve the internal order and verify approval
	 * @param internalOrderType
	 * @param testSkuToAssociate
	 * @param quanity
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
			"Regression" }, dataProvider = "getOrderTypes", dataProviderClass = InternalOrderTestsDataProvider.class)
	public void testInternalOrderAprrovalProcess(InternalOrderType internalOrderType, int testSkuToAssociate, int quanity)
			throws UnsupportedEncodingException, JAXBException {
		long createdInternalOrderId = InternalOrderUtils
				.createAnInternalOrderAndReturnInternalOrderId(internalOrderType);
		
		InternalOrderUtils.associateSkuWithAnInternalOrder(createdInternalOrderId,
				InternalOrderTestConstants.DEFAULT_TEST_SKU_ID,
				InternalOrderTestConstants.DEFAULT_QUANTITY_OF_ITEMS_TO_TEST);
		
		StockTransferUtils.updateInventory(InternalOrderTestConstants.DEFAULT_TEST_SKU_ID, 
				InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID, InternalOrderTestConstants.DEFAULT_QUANTITY_OF_ITEMS_TO_TEST);
		
		InternalOrderUtils.sendInternalOrderForApprovalAndVerify(createdInternalOrderId);
		InternalOrderUtils.approveAnInternalOrderAndVerifyApproval(createdInternalOrderId);

	}

	/**The test covers the sequential SKU association process for an internal order of type Others and promotional goods
	 * * * Steps : 
	 * 1) Create the internal order and verify it was created
	 * 2) Associate the SKU to the order
	 * 3) Send the IO for approval
	 * @param internalOrderType
	 * @param testSkuToAssociate
	 * @param quanity
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
			"Regression" }, dataProvider = "getOrderTypes", dataProviderClass = InternalOrderTestsDataProvider.class)
	public void testSequentialSkuAssociationToInternalOrder(InternalOrderType internalOrderType, int testSkuToAssociate,
			int quanity) throws UnsupportedEncodingException, JAXBException {

		long createdInternalOrderId = InternalOrderUtils
				.createAnInternalOrderAndReturnInternalOrderId(internalOrderType);
		Assert.assertTrue(InternalOrderUtils.getAllSkusAssociatedWithTheIO(createdInternalOrderId).size() == 0,
				"The sku list is not empty, while there are no sku attached");
		
		StockTransferUtils.updateInventory(testSkuToAssociate, 
				InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID, quanity);
		
		InternalOrderUtils.associateSkuWithAnInternalOrder(createdInternalOrderId, testSkuToAssociate, quanity);
		Assert.assertTrue(InternalOrderUtils.getAllSkusAssociatedWithTheIO(createdInternalOrderId).size() == 1,
				"The sku list is not empty, while there are no sku attached");
		
		StockTransferUtils.updateInventory(testSkuToAssociate, 
				InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID, quanity);
		
		InternalOrderUtils.associateSkuWithAnInternalOrder(createdInternalOrderId, InternalOrderTestConstants.DEFAULT_TEST_SKU_ID_1, quanity);
		Assert.assertTrue(InternalOrderUtils.getAllSkusAssociatedWithTheIO(createdInternalOrderId).size() == 2,
				"The sku list is not empty, while there are no sku attached");

		InternalOrderUtils.sendInternalOrderForApprovalAndVerify(createdInternalOrderId);

	}

	/**The test covers the decline process for an internal order of type Others and promotional goods
	 * * * Steps : 
	 * 1) Create the internal order and verify it was created
	 * 2) Associate the SKU to the order
	 * 3) Send the IO for approval
	 * 4) Decline the internal order and verify decline process success
	 * @param internalOrderType
	 * @param testSkuToAssociate
	 * @param quanity
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
			"Regression" }, dataProvider = "getOrderTypes", dataProviderClass = InternalOrderTestsDataProvider.class)
	public void testDeclineEventOnAnInternalOrder(InternalOrderType internalOrderType, int testSkuToAssociate, int quanity)
			throws UnsupportedEncodingException, JAXBException {
		long createdInternalOrderId = InternalOrderUtils
				.createAnInternalOrderAndReturnInternalOrderId(internalOrderType);
		
		StockTransferUtils.updateInventory(testSkuToAssociate, 
				InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID, quanity);
		
		InternalOrderUtils.associateSkuWithAnInternalOrder(createdInternalOrderId, testSkuToAssociate, quanity);
		InternalOrderUtils.sendInternalOrderForApprovalAndVerify(createdInternalOrderId);
		InternalOrderUtils.declineAnInternalOrderAndVerifySuccess(createdInternalOrderId);

	}

	/**The test covers the sequential deletion of SKU  for an internal order of type Others and promotional goods
	 * * * Steps : 
	 * 1) Create the internal order and verify it was created
	 * 2) Associate the SKU to the order
	 * 3) Fetch all the skus from the order and verify that addition
	 * @param internalOrderType
	 * @param testSkuToAssociate
	 * @param quanity
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
			"Regression" }, dataProvider = "getOrderTypes", dataProviderClass = InternalOrderTestsDataProvider.class)
	public void testSequentialDeletionOfSkuIoAssociation(InternalOrderType internalOrderType, int testSkuToAssociate,
			int quanity) throws UnsupportedEncodingException, JAXBException {

		long createdInternalOrderId = InternalOrderUtils
				.createAnInternalOrderAndReturnInternalOrderId(internalOrderType);
		
		StockTransferUtils.updateInventory(testSkuToAssociate, 
				InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID, quanity);
		
		InternalOrderUtils.associateSkuWithAnInternalOrder(createdInternalOrderId, testSkuToAssociate, quanity);
		
		Assert.assertTrue(InternalOrderUtils.getAllSkusAssociatedWithTheIO(createdInternalOrderId).size() == 1,
				"The sku list is not empty, while there are no sku attached");

		InternalOrderUtils.findAndDeleteSkuAssociationFromAnInternalOrder(createdInternalOrderId,
				InternalOrderTestConstants.DEFAULT_TEST_SKU_ID);
		Assert.assertTrue(InternalOrderUtils.getAllSkusAssociatedWithTheIO(createdInternalOrderId).size() == 0,
				"The sku list is not empty, while there are no sku attached");

	}

	/**The test covers the on hold pushing for an internal order of type Others and promotional goods
	 * * * Steps : 
	 * 1) Create the internal order and verify it was created
	 * 2) Associate the SKU to the order
	 * 3) Send the IO for approval
	 * 4) Approve the internal order and verify approval
	 * 5) Put the order on hold
	 * @param internalOrderType
	 * @param testSkuToAssociate
	 * @param quanity
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
			"Regression" }, dataProvider = "getOrderTypes", dataProviderClass = InternalOrderTestsDataProvider.class)
	public void testOnHoldPushForAnInternalOrder(InternalOrderType internalOrderType, int testSkuToAssociate,
			int quanity) throws UnsupportedEncodingException, JAXBException {
		long createdInternalOrderId = InternalOrderUtils
				.createAnInternalOrderAndReturnInternalOrderId(internalOrderType);
		Assert.assertTrue(InternalOrderUtils.getAllSkusAssociatedWithTheIO(createdInternalOrderId).size() == 0,
				"The sku list is not empty, while there are no sku attached");
		
		StockTransferUtils.updateInventory(testSkuToAssociate, 
				InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID, quanity);
		
		InternalOrderUtils.associateSkuWithAnInternalOrder(createdInternalOrderId, testSkuToAssociate, quanity);
		Assert.assertTrue(InternalOrderUtils.getAllSkusAssociatedWithTheIO(createdInternalOrderId).size() == 1,
				"The sku list is not empty, while there are no sku attached");
		InternalOrderUtils.sendInternalOrderForApprovalAndVerify(createdInternalOrderId);
		InternalOrderUtils.approveAnInternalOrderAndVerifyApproval(createdInternalOrderId);
		InternalOrderUtils.putAnInternalOrderToHoldAndVerifySuccess(createdInternalOrderId);
	}

	/**The test covers the association of SKU to an on hold internal order of type Others and promotional goods
	 * * * * Steps : 
	 * 1) Create the internal order and verify it was created
	 * 2) Associate the SKU to the order
	 * 3) Send the IO for approval
	 * 4) Approve the internal order and verify approval
	 * 5) Put the order on hold
	 * 6) Associate another SKU and verify addition
	 * @param internalOrderType
	 * @param testSkuToAssociate
	 * @param quanity
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
			"Regression" }, dataProvider = "getOrderTypes", dataProviderClass = InternalOrderTestsDataProvider.class)
	public void testAddSkuToAnOnHoldInternalOrder(InternalOrderType internalOrderType, int testSkuToAssociate,
			int quanity) throws UnsupportedEncodingException, JAXBException {
		long createdInternalOrderId = InternalOrderUtils
				.createAnInternalOrderAndReturnInternalOrderId(internalOrderType);
		
		StockTransferUtils.updateInventory(testSkuToAssociate, 
				InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID, quanity);
		
		InternalOrderUtils.associateSkuWithAnInternalOrder(createdInternalOrderId, testSkuToAssociate, quanity);
		Assert.assertTrue(InternalOrderUtils.getAllSkusAssociatedWithTheIO(createdInternalOrderId).size() == 1,
				"The sku list is  empty, while there are no sku attached");
		InternalOrderUtils.sendInternalOrderForApprovalAndVerify(createdInternalOrderId);
		InternalOrderUtils.approveAnInternalOrderAndVerifyApproval(createdInternalOrderId);
		InternalOrderUtils.putAnInternalOrderToHoldAndVerifySuccess(createdInternalOrderId);
		log.info("Adding anothe sku after putting the order on hold");
		
		StockTransferUtils.updateInventory(testSkuToAssociate, 
				InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID, quanity);
		
		InternalOrderUtils.associateSkuWithAnInternalOrder(createdInternalOrderId, InternalOrderTestConstants.DEFAULT_TEST_SKU_ID_1, quanity);
		Assert.assertTrue(InternalOrderUtils.getAllSkusAssociatedWithTheIO(createdInternalOrderId).size() == 2,
				"The sku list is not of size 2");
		
	}

	/**The test covers the approval process for an on hold internal order of type Others and promotional goods
	 * * * * Steps : 
	 * 1) Create the internal order and verify it was created
	 * 2) Associate the SKU to the order
	 * 3) Send the IO for approval
	 * 4) Approve the internal order and verify approval
	 * 5) Put the order on hold
	 * 6) Approve the IO again and verify success
	 * @param internalOrderType
	 * @param testSkuToAssociate
	 * @param quanity
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
			"Regression" }, dataProvider = "getOrderTypes", dataProviderClass = InternalOrderTestsDataProvider.class)
	public void testApprovalOfAnOnHoldInternalOrder(InternalOrderType internalOrderType, int testSkuToAssociate,
			int quanity) throws UnsupportedEncodingException, JAXBException {
		long createdInternalOrderId = InternalOrderUtils
				.createAnInternalOrderAndReturnInternalOrderId(internalOrderType);
		Assert.assertTrue(InternalOrderUtils.getAllSkusAssociatedWithTheIO(createdInternalOrderId).size() == 0,
				"The sku list is not empty, while there are no sku attached");
		
		StockTransferUtils.updateInventory(testSkuToAssociate, 
				InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID, quanity);
		
		InternalOrderUtils.associateSkuWithAnInternalOrder(createdInternalOrderId, testSkuToAssociate, quanity);
		Assert.assertTrue(InternalOrderUtils.getAllSkusAssociatedWithTheIO(createdInternalOrderId).size() == 1,
				"The sku list is  empty, while there are no sku attached");
		InternalOrderUtils.sendInternalOrderForApprovalAndVerify(createdInternalOrderId);
		InternalOrderUtils.approveAnInternalOrderAndVerifyApproval(createdInternalOrderId);
		InternalOrderUtils.putAnInternalOrderToHoldAndVerifySuccess(createdInternalOrderId);
		InternalOrderUtils.approveAnInternalOrderAndVerifyApproval(createdInternalOrderId);
	}
	

	/** The test covers that the barcode format of a new internal order follows the  format = "INDDMMYY0000X"
	 * * * * Steps : 
	 * 1) Create the internal order and verify it was created
	 * 2) Verify that the IO barcode was having the format INDDMMYY0000X
	 * @param internalOrderType
	 * @param testSkuToAssociate
	 * @param quanity
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
			"Regression" }, dataProvider = "getOrderTypes", dataProviderClass = InternalOrderTestsDataProvider.class)
	public void testInternalOrderBarcodeFormat(InternalOrderType internalOrderType, int testSkuToAssociate,
			int quanity) throws UnsupportedEncodingException, JAXBException {
		long createdInternalOrderId = InternalOrderUtils
				.createAnInternalOrderAndReturnInternalOrderId(internalOrderType);
			String internalOrderBarcode=	InternalOrderUtils.getInternalOrderBarcodeByOrderId(createdInternalOrderId);
			log.info("The internal orderbarcode is "+internalOrderBarcode);
			String timeStamp = new SimpleDateFormat("ddMMyy").format(new Date());
			String patternToMatch = "IN" + timeStamp;
			log.info("The pattern to match is "+patternToMatch);
			Assert.assertTrue(internalOrderBarcode.startsWith(patternToMatch),"The item barcode ["+internalOrderBarcode+"] is not of the desired format");
	}
	

}
