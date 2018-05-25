package com.myntra.apiTests.erpservices.wms.Tests;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.xml.bind.JAXBException;

import com.myntra.apiTests.erpservices.InternalOrderTestConstants;
import com.myntra.apiTests.erpservices.oms.StockTransferUtils;
import com.myntra.apiTests.erpservices.wms.InternalOrderUtils;
import com.myntra.apiTests.erpservices.wms.dp.InternalOrderTestsDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.myntra.client.wms.codes.utils.InternalOrderType;
import com.myntra.client.wms.response.InternalOrderResponse;
import com.myntra.test.commons.testbase.BaseTest;

public class InternalOrderNegativeTests extends BaseTest {
	private static Logger log = LoggerFactory.getLogger(InternalOrderNegativeTests.class);
	
	/** The test verifies that if an other order is created and associated with thh SKU with no inventory ,it cannot be approved
	 *  * Steps : 
	 * 1) Create the internal order and verify it was created
	 * 2) Associate SKU with IO
	 * 3) Send the IO for approval
	 * 5) Try t approve the IO and see the failure 
	 * @param internalOrderType
	 * @param testSkuToAssociate
	 * @param quanity
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
	"Regression" }, dataProvider = "getOrderTypesToTestNoInventorySkuAssociation", dataProviderClass = InternalOrderTestsDataProvider.class)
	public void testSkuWithNoInventoryAssociationWithAnInternalOrder(InternalOrderType internalOrderType, int testSkuToAssociate, int quanity) throws UnsupportedEncodingException, JAXBException {
		long createdInternalOrderId = InternalOrderUtils
				.createAnInternalOrderAndReturnInternalOrderId(internalOrderType);
		InternalOrderUtils.makeInventoryEmpty(testSkuToAssociate, InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID, 
				InternalOrderTestConstants.DEFAULT_TEST_SELLER_ID);
		InternalOrderUtils.associateSkuWithAnInternalOrder(createdInternalOrderId, testSkuToAssociate, quanity);
		InternalOrderUtils.sendInternalOrderForApprovalAndVerify(createdInternalOrderId);
		InternalOrderResponse	internalOrderUpdateResponse =InternalOrderUtils.approveAnInternalOrderAndReturnResponse(createdInternalOrderId);
		Assert.assertFalse( internalOrderUpdateResponse.getStatus().isSuccess(),
				"The internal status of the approval was  successfull...please see the response above for status code and message");
		Assert.assertFalse(internalOrderUpdateResponse.getStatus().getStatusMessage().equals(InternalOrderTestConstants.CREATE_INTERNAL_ORDER_SUCCESS_REPONSE_STATUS_MESSAGE),"The approval response status message was success");
		Assert.assertFalse(internalOrderUpdateResponse.getStatus().getStatusCode()==InternalOrderTestConstants.CREATE_INTERNAL_ORDER_SUCCESS_REPONSE_STATUS_CODE,"The approval response status code was success");

	}
	/**The test verifies that edition of an IO which is sent for Approval cannot be done
	 *   * Steps : 
	 * 1) Create the internal order and verify it was created
	 * 2) Associate SKU with IO
	 * 3) Send the IO for approval 
	 * 4) Try to edit the IO 
	 * 5) The IO could not be edited
	 * @param internalOrderType
	 * @param testSkuToAssociate
	 * @param quanity
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
	"Regression" }, dataProvider = "getOrderTypes", dataProviderClass = InternalOrderTestsDataProvider.class)
	public void testEditAnInternalOrderWhichIsSentForApproval(InternalOrderType internalOrderType, int testSkuToAssociate, int quanity) throws UnsupportedEncodingException, JAXBException {
		long createdInternalOrderId = InternalOrderUtils
				.createAnInternalOrderAndReturnInternalOrderId(internalOrderType);
		
		StockTransferUtils.updateInventory(testSkuToAssociate, 
				InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID, quanity);
		
		InternalOrderUtils.associateSkuWithAnInternalOrder(createdInternalOrderId,
				testSkuToAssociate,
				quanity);
		
		InternalOrderUtils.sendInternalOrderForApprovalAndVerify(createdInternalOrderId);
		InternalOrderResponse	editInternalOrderResponse = InternalOrderUtils.editInternalOrderAndReturnResponse(createdInternalOrderId);
		Assert.assertTrue(null != editInternalOrderResponse, "There was no response obtained from the server");
		log.info("Printing Internal Order create attempt response below  ::- ");
		log.info(editInternalOrderResponse.toString());
	
		Assert.assertTrue(editInternalOrderResponse.getStatus().getStatusMessage().equals(InternalOrderTestConstants.INTERNAL_ORDER_TRANSITIONAL_FAILURE_MESSAGE),"The edit response status message was success");
		Assert.assertTrue(editInternalOrderResponse.getStatus().getStatusCode()==InternalOrderTestConstants.INTERNAL_ORDER_TRANSITIONAL_FAILURE_CODE,"The edit response status code was success");
	}
	/**The test verifies that edition of a declined IOcannot be done
	 *  *   * Steps : 
	 * 1) Create the internal order and verify it was created
	 * 2) Associate SKU with IO
	 * 3) Send the IO for approval  and decline the order
	 * 4) Try to edit the IO 
	 * 5) The IO could not be edited
	 * @param internalOrderType
	 * @param testSkuToAssociate
	 * @param quanity
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
	"Regression" }, dataProvider = "getOrderTypes", dataProviderClass = InternalOrderTestsDataProvider.class)
	public void testEditingOfADeclinedInternalOrder(InternalOrderType internalOrderType, int testSkuToAssociate, int quanity) throws UnsupportedEncodingException, JAXBException {
		long createdInternalOrderId = InternalOrderUtils
				.createAnInternalOrderAndReturnInternalOrderId(internalOrderType);
		StockTransferUtils.updateInventory(testSkuToAssociate, 
				InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID, quanity);
		InternalOrderUtils.associateSkuWithAnInternalOrder(createdInternalOrderId, testSkuToAssociate, quanity);
		InternalOrderUtils.sendInternalOrderForApprovalAndVerify(createdInternalOrderId);
		InternalOrderUtils.declineAnInternalOrderAndVerifySuccess(createdInternalOrderId);
		InternalOrderResponse	editInternalOrderResponse=InternalOrderUtils.editInternalOrderAndReturnResponse(createdInternalOrderId);
		Assert.assertTrue(null != editInternalOrderResponse, "There was no response obtained from the server");
		log.info("Printing Internal Order create attempt response below  ::- ");
		log.info(editInternalOrderResponse.toString());

		Assert.assertTrue(editInternalOrderResponse.getStatus().getStatusMessage().equals(InternalOrderTestConstants.INTERNAL_ORDER_TRANSITIONAL_FAILURE_MESSAGE),"The edit response status message was success");
		Assert.assertTrue(editInternalOrderResponse.getStatus().getStatusCode()==InternalOrderTestConstants.INTERNAL_ORDER_TRANSITIONAL_FAILURE_CODE,"The edit response status code was success");
	}
	

	/**The test verifies that edition of an approved IO cannot be done
	 *  *   * Steps : 
	 * 1) Create the internal order and verify it was created
	 * 2) Associate SKU with IO
	 * 3) Send the IO for approval  and approve the IO
	 * 4) Try to edit the IO 
	 * 5) The IO could not be edited
	 * @param internalOrderType
	 * @param testSkuToAssociate
	 * @param quanity
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
	"Regression" }, dataProvider = "getOrderTypes", dataProviderClass = InternalOrderTestsDataProvider.class)
	public void testEditingOfAnAprrovalInternalOrder(InternalOrderType internalOrderType, int testSkuToAssociate, int quanity) throws UnsupportedEncodingException, JAXBException {

		long createdInternalOrderId = InternalOrderUtils
				.createAnInternalOrderAndReturnInternalOrderId(internalOrderType);
		
		StockTransferUtils.updateInventory(InternalOrderTestConstants.DEFAULT_TEST_SKU_ID, 
				InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID, InternalOrderTestConstants.DEFAULT_QUANTITY_OF_ITEMS_TO_TEST);
		
		InternalOrderUtils.associateSkuWithAnInternalOrder(createdInternalOrderId,
				InternalOrderTestConstants.DEFAULT_TEST_SKU_ID,
				InternalOrderTestConstants.DEFAULT_QUANTITY_OF_ITEMS_TO_TEST);
		
		InternalOrderUtils.sendInternalOrderForApprovalAndVerify(createdInternalOrderId);
		InternalOrderUtils.approveAnInternalOrderAndVerifyApproval(createdInternalOrderId);
		InternalOrderResponse	editInternalOrderResponse=InternalOrderUtils.editInternalOrderAndReturnResponse(createdInternalOrderId);
		Assert.assertTrue(null != editInternalOrderResponse, "There was no response obtained from the server");
		log.info("Printing Internal Order create attempt response below  ::- ");
		log.info(editInternalOrderResponse.toString());
		Assert.assertTrue(editInternalOrderResponse.getStatus().getStatusMessage().equals(InternalOrderTestConstants.INTERNAL_ORDER_TRANSITIONAL_FAILURE_MESSAGE),"The edit response status message was success");
		Assert.assertTrue(editInternalOrderResponse.getStatus().getStatusCode()==InternalOrderTestConstants.INTERNAL_ORDER_TRANSITIONAL_FAILURE_CODE,"The edit response status code was success");


	}
	
	/**The test verifies that edition of a Shipped  IO cannot be done
	 *  *   * Steps : 
	 * 1) Create the internal order and verify it was created
	 * 2) Associate SKU with IO
	 * 3) Send the IO for approval 
	 * 4) Ship the IO
	 * 5) Try to edit the IO 
	 * 6) The IO could not be edited
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
	"Regression"})
	public void testEditingOfAShippedInternalOrder() throws UnsupportedEncodingException, JAXBException {

		long createdInternalOrderId = InternalOrderUtils.createAnInternalOrderAndReturnInternalOrderId(InternalOrderType.B2B_Sales);
		List<String> items=InternalOrderUtils.createTestStoredItemsWithQualityQ2(InternalOrderTestConstants.DEFAULT_QUANTITY_OF_ITEMS_TO_TEST, 
				InternalOrderTestConstants.DEFAULT_TEST_SKU_ID, InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID, InternalOrderTestConstants.BUYER_ID);
		InternalOrderUtils.associateItemToTheBuyToBuyInternalOrder((String)items.get(0),createdInternalOrderId);
		InternalOrderUtils.sendInternalOrderForApprovalAndVerify(createdInternalOrderId);
		InternalOrderUtils.approveAnInternalOrderAndVerifyApproval(createdInternalOrderId);
		InternalOrderUtils.sendAnInternalOrderForPickInitiation(createdInternalOrderId);
		InternalOrderUtils.simulateItemAnOrderCheckoutEventInMaterialMovementsPage(createdInternalOrderId, (String)items.get(0));
		InternalOrderUtils.shipAnInternalOrder(createdInternalOrderId);
		InternalOrderResponse	editInternalOrderResponse=InternalOrderUtils.editInternalOrderAndReturnResponse(createdInternalOrderId);
		
		Assert.assertTrue(null != editInternalOrderResponse, "There was no response obtained from the server");
		log.info("Printing Internal Order create attempt response below  ::- ");
		log.info(editInternalOrderResponse.toString());
		Assert.assertTrue(editInternalOrderResponse.getStatus().getStatusMessage().equals(InternalOrderTestConstants.INTERNAL_ORDER_TRANSITIONAL_FAILURE_MESSAGE),"The edit response status message was success");
		Assert.assertTrue(editInternalOrderResponse.getStatus().getStatusCode()==InternalOrderTestConstants.INTERNAL_ORDER_TRANSITIONAL_FAILURE_CODE,"The edit response status code was success");
	}
}
