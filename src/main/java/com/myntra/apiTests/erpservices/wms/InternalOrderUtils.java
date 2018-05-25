package com.myntra.apiTests.erpservices.wms;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.erpservices.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import com.myntra.apiTests.erpservices.InternalOrderTestConstants;
import com.myntra.client.inventory.enums.Quality;
import com.myntra.client.wms.codes.WmsSuccessCodes;
import com.myntra.client.wms.codes.utils.InternalOrderStatus;
import com.myntra.client.wms.codes.utils.InternalOrderType;
import com.myntra.client.wms.response.CheckoutEntry;
import com.myntra.client.wms.response.IOItemEntry;
import com.myntra.client.wms.response.IOItemResponse;
import com.myntra.client.wms.response.IOSkuEntry;
import com.myntra.client.wms.response.IOSkuResponse;
import com.myntra.client.wms.response.InternalOrderEntry;
import com.myntra.client.wms.response.InternalOrderResponse;
import com.myntra.client.wms.response.ItemEntry;
import com.myntra.client.wms.response.ItemResponse;
import com.myntra.client.wms.response.SkuEntry;
import com.myntra.client.wms.response.location.WarehouseEntry;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;

/**
 * The utils class provides different  static methods which can do the
 * work  to perform action(creation) and events(send for approval etc,
 * associating entities to the IO) 
 * Since the behavior and associations of each
 * type of internal order is different , we have distinct methods for each of
 * the item 
 * Below is the explanation of the lifecycle of an internal order 
 * 1.Created State when you create one of the these type of internal order 
 * a)BuyToBuy 
 * b) Promotional goods 
 * c) others , 
 * the procedure for creating all these types of the orders is same 
 * 2. The second phase is to associate the
 * entities (Sku or items) to the internal Order (This phase is distinct for the
 * type of internal order)
 * 3. The order then is sent for approval
 * 4. Once the
 * order is approved , the order can be sent to on hold state or can be declined
 * 5. The shipped state is post checking out the order and items associated.
 * 
 * 
 * @author puneet.khanna1@myntra.com
 *
 */
public class InternalOrderUtils {
	private static Logger log = LoggerFactory.getLogger(InternalOrderUtils.class);
	private static WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	private InternalOrderUtils() {

	}

	/**
	 * The method attempts to create an internal order bases on the
	 * internalOrdertTypeToCreate passed The method asserts if the order is
	 * created or not and throws if the responses either null or not success
	 * 
	 * @param internalOrdertTypeToCreate
	 * @return the internal Order id created
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static long createAnInternalOrderAndReturnInternalOrderId(InternalOrderType internalOrdertTypeToCreate)
			throws JAXBException, UnsupportedEncodingException {
		InternalOrderEntry internalOrderEntry = new InternalOrderEntry();
		internalOrderEntry.setOrderType(internalOrdertTypeToCreate);
		WarehouseEntry wareHouseEntry = new WarehouseEntry();
		wareHouseEntry.setId((long) InternalOrderTestConstants.DEFAULT_TEST_WAREHOUSE_ID);
		internalOrderEntry.setWarehouse(wareHouseEntry);
		internalOrderEntry.setOrderStatus(InternalOrderStatus.CREATED);
		internalOrderEntry.setApprover(InternalOrderTestConstants.DEFAULT_APPROVER);
		internalOrderEntry.setDescription(InternalOrderTestConstants.DEFAULT_REMARKS_TO_CREATE_ORDER);
		String payload = APIUtilities.convertXMLObjectToString(internalOrderEntry);
		log.info("The payLoad to create Internal order is :: " + payload.toString());
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.INTERNAL_ORDER_PROCESSING_URL,
				new String[] {}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload,
				getTokenForIntenalOrderOperations());
		InternalOrderResponse internalOrderCreateCallResponse = (InternalOrderResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new InternalOrderResponse());
		Assert.assertTrue(null != internalOrderCreateCallResponse, "There was no response obtained from the server");
		log.info("Printing Internal Order create attempt response below  ::- ");
		log.info(internalOrderCreateCallResponse.toString());
		Assert.assertTrue(internalOrderCreateCallResponse.getStatus().isSuccess(),
				"The internal status retrieved on placing the internal order was not SUCCESS....please see the response above for status code and message");
		Assert.assertEquals(internalOrderCreateCallResponse.getStatus().getStatusMessage(),
				InternalOrderTestConstants.CREATE_INTERNAL_ORDER_SUCCESS_REPONSE_STATUS_MESSAGE);
		Assert.assertEquals(internalOrderCreateCallResponse.getStatus().getStatusCode(),
				InternalOrderTestConstants.CREATE_INTERNAL_ORDER_SUCCESS_REPONSE_STATUS_CODE);
		Assert.assertFalse(internalOrderCreateCallResponse.getData().isEmpty(),
				"There was no internal order id generated." + ".although create call sent a success response above..");
		long internalOrderId=internalOrderCreateCallResponse.getData().get(0).getId();
		log.info("The created internalOrder Id is "+internalOrderId);
		return internalOrderId;
	}

	/** The method returns the internal order itemBarcode based on the internalOrderid passed
	 * The method throws assert Error if either itemBarcode is null or is an empty string
	 * @param internalOrderid
	 * @return
	 */
	public static String getInternalOrderBarcodeByOrderId(long internalOrderid) {
		String orderBarcode = null;
		HashMap<String, Object> resultMap = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord(
				"select barcode from core_ios where id = " + internalOrderid, "myntra_wms");
		if (null != resultMap && !resultMap.isEmpty()) {
			orderBarcode = (String) resultMap.get("barcode");
		} else {
			log.info("Could not retrive orderBarcode for internalOrderid " + internalOrderid);
		}
		Assert.assertTrue((null!=orderBarcode && orderBarcode.length()>0),
				"The orderBarcode could not be retreived by internal order id ["+internalOrderid+"]");
		return orderBarcode;

	}

	/** The method attempts to associate the item passed to the internalOrderId
	 * @param itemBarcode
	 * @param buyToBuyInternalOrderId
	 * @throws JAXBException ,Assert Error if the association is not successful
	 * @throws UnsupportedEncodingException
	 */
	public static void associateItemToTheBuyToBuyInternalOrder(String itemBarcode, long buyToBuyInternalOrderId)
			throws JAXBException, UnsupportedEncodingException {
		IOItemEntry ioItemEntry = new IOItemEntry();
		ItemEntry itemEntry = new ItemEntry();
		itemEntry.setBarcode(itemBarcode + "");
		ioItemEntry.setItem(itemEntry);
		InternalOrderEntry internalOrderEntry = new InternalOrderEntry();
		internalOrderEntry.setId(buyToBuyInternalOrderId);
		ioItemEntry.setInternalOrder(internalOrderEntry);
		String payload = APIUtilities.convertXMLObjectToString(ioItemEntry);
		log.info("The payLoad to create Internal order is :: " + payload.toString());
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToItemAndInternalOrderAssociationMappingCall(buyToBuyInternalOrderId),
				new String[] {}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload,
				getTokenForIntenalOrderOperations());
		IOItemResponse itemInterOrderAssociationResponse = (IOItemResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new IOItemResponse());
		Assert.assertTrue(null != itemInterOrderAssociationResponse, "There was no response obtained from the server");
		log.info("Printing Internal Order and Item association  attempt response below  ::- ");
		log.info(itemInterOrderAssociationResponse.toString());
		Assert.assertTrue(itemInterOrderAssociationResponse.getStatus().isSuccess(),
				"The internal status retrieved on associating item with IO was not success....please see the response above for status code and message");
		Assert.assertEquals(itemInterOrderAssociationResponse.getStatus().getStatusMessage(),
				InternalOrderTestConstants.ITEM_INTERNAL_ORDER_ASSOCIATION_SUCCESS_REPONSE_STATUS_MESSAGE);
		Assert.assertEquals(itemInterOrderAssociationResponse.getStatus().getStatusCode(),
				InternalOrderTestConstants.ITEM_INTERNAL_ORDER_ASSOCIATION_SUCCESS_REPONSE_STATUS_CODE);
	}

	/** The method tries to de-associate the item from an internal Order and asserts if the response was successful
	 * @param itemBarcode
	 * @param buyToBuyInternalOrderId
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static void deAssociateAnItemFromBuyToBuyInternalOrder(String itemBarcode, long buyToBuyInternalOrderId)
			throws JAXBException, UnsupportedEncodingException {
		IOItemEntry ioItemEntry = new IOItemEntry();
		ItemEntry itemEntry = new ItemEntry();
		itemEntry.setBarcode(itemBarcode + "");
		ioItemEntry.setItem(itemEntry);
		InternalOrderEntry internalOrderEntry = new InternalOrderEntry();
		internalOrderEntry.setId(buyToBuyInternalOrderId);
		ioItemEntry.setInternalOrder(internalOrderEntry);
		String payload = APIUtilities.convertXMLObjectToString(ioItemEntry);
		log.info("The payLoad to create Internal order is :: " + payload.toString());
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToItemAndInternalOrderAssociationMappingCall(buyToBuyInternalOrderId),
				new String[] {}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload,
				getTokenForIntenalOrderOperations());
		IOItemResponse itemInterOrderAssociationResponse = (IOItemResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new IOItemResponse());
		Assert.assertTrue(null != itemInterOrderAssociationResponse, "There was no response obtained from the server");
		log.info("Printing Internal Order and Item association  attempt response below  ::- ");
		log.info(itemInterOrderAssociationResponse.toString());
		Assert.assertTrue(itemInterOrderAssociationResponse.getStatus().isSuccess(),
				"The internal status retrieved on associating item with IO was not success....please see the response above for status code and message");
		Assert.assertEquals(itemInterOrderAssociationResponse.getStatus().getStatusMessage(),
				InternalOrderTestConstants.ITEM_INTERNAL_ORDER_ASSOCIATION_SUCCESS_REPONSE_STATUS_MESSAGE);
		Assert.assertEquals(itemInterOrderAssociationResponse.getStatus().getStatusCode(),
				InternalOrderTestConstants.ITEM_INTERNAL_ORDER_ASSOCIATION_SUCCESS_REPONSE_STATUS_CODE);
	}

	/** The method tries to send the internal order for approval and verifies if the operation was sucessfull
	 * @param internalOrderId
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static void sendInternalOrderForApprovalAndVerify(long internalOrderId)
			throws JAXBException, UnsupportedEncodingException {

		InternalOrderResponse internalOrderUpdateResponse = sendInternalOrderForApprovalAndReturnResponse(
				internalOrderId);
		Assert.assertTrue(internalOrderUpdateResponse.getStatus().isSuccess(),
				"The internal status internalOrderUpdateResponse on associating item with IO was not success....please see the response above for status code and message");
		Assert.assertEquals(internalOrderUpdateResponse.getStatus().getStatusMessage(),
				InternalOrderTestConstants.INTERNAL_ORDER_STATE_TRANSITION_SUCCESS_MESSAGE);
		Assert.assertEquals(internalOrderUpdateResponse.getStatus().getStatusCode(),
				InternalOrderTestConstants.INTERNAL_ORDER_STATE_TRANSITION_SUCCESS_CODE);
	}

	/** The method tries to send an internal order for approval and sends back the response to the caller.
	 * @param internalOrderId
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static InternalOrderResponse sendInternalOrderForApprovalAndReturnResponse(long internalOrderId)
			throws JAXBException, UnsupportedEncodingException {
		InternalOrderEntry internalOrderEntryToSendForApproval = new InternalOrderEntry();
		internalOrderEntryToSendForApproval.setId(internalOrderId);
		InternalOrderStatus internalOrderReadyStatus = InternalOrderStatus.READY;
		internalOrderEntryToSendForApproval.setOrderStatus(internalOrderReadyStatus);
		internalOrderEntryToSendForApproval
				.setDescription(InternalOrderTestConstants.REMARKS_TO_MOVE_IO_TO_SEND_FOR_APPROVAL);
		String payload = APIUtilities.convertXMLObjectToString(internalOrderEntryToSendForApproval);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToUpdateInternalOrder(internalOrderId), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, getTokenForIntenalOrderOperations());
		InternalOrderResponse internalOrderUpdateResponse = (InternalOrderResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new InternalOrderResponse());

		return internalOrderUpdateResponse;

	}

	/** The method tries to put an internal order on hold and verifies the success
	 * @param internalOrderId
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static void putAnInternalOrderToHoldAndVerifySuccess(long internalOrderId)
			throws JAXBException, UnsupportedEncodingException {
		InternalOrderEntry internalOrderEntryToSendForApproval = new InternalOrderEntry();
		internalOrderEntryToSendForApproval.setId(internalOrderId);
		InternalOrderStatus internalOrderReadyStatus = InternalOrderStatus.ON_HOLD;
		internalOrderEntryToSendForApproval.setOrderStatus(internalOrderReadyStatus);
		internalOrderEntryToSendForApproval
				.setDescription(InternalOrderTestConstants.REMARKS_TO_MOVE_IO_TO_SEND_FOR_APPROVAL);
		String payload = APIUtilities.convertXMLObjectToString(internalOrderEntryToSendForApproval);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToUpdateInternalOrder(internalOrderId), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, getTokenForIntenalOrderOperations());
		InternalOrderResponse internalOrderUpdateResponse = (InternalOrderResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new InternalOrderResponse());
		Assert.assertTrue(internalOrderUpdateResponse.getStatus().isSuccess(),
				"The internal status of the approval was not successfull...please see the response above for status code and message");
		Assert.assertEquals(internalOrderUpdateResponse.getStatus().getStatusMessage(),
				InternalOrderTestConstants.INTERNAL_ORDER_STATE_TRANSITION_SUCCESS_MESSAGE);
		Assert.assertEquals(internalOrderUpdateResponse.getStatus().getStatusCode(),
				InternalOrderTestConstants.INTERNAL_ORDER_STATE_TRANSITION_SUCCESS_CODE);
	}

	/** The method approves an internal order and verifies that the order was approved
	 * The method throws assert error in case the approval process was not successfull
	 * @param internalOrderId
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static void approveAnInternalOrderAndVerifyApproval(long internalOrderId)
			throws JAXBException, UnsupportedEncodingException {
		InternalOrderResponse internalOrderUpdateResponse = approveAnInternalOrderAndReturnResponse(internalOrderId);
		Assert.assertTrue(internalOrderUpdateResponse.getStatus().isSuccess(),
				"The internal status of the approval was not successfull...please see the response above for status code and message");
		Assert.assertEquals(internalOrderUpdateResponse.getStatus().getStatusMessage(),
				InternalOrderTestConstants.INTERNAL_ORDER_STATE_TRANSITION_SUCCESS_MESSAGE);
		Assert.assertEquals(internalOrderUpdateResponse.getStatus().getStatusCode(),
				InternalOrderTestConstants.INTERNAL_ORDER_STATE_TRANSITION_SUCCESS_CODE);
	}

	/** The method approves an internal order and returns the response received
	 * @param internalOrderId
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static InternalOrderResponse approveAnInternalOrderAndReturnResponse(long internalOrderId)
			throws JAXBException, UnsupportedEncodingException {
		InternalOrderEntry internalOrderEntryToSendForApproval = new InternalOrderEntry();
		internalOrderEntryToSendForApproval.setId(internalOrderId);
		InternalOrderStatus internalOrderReadyStatus = InternalOrderStatus.APPROVED;
		internalOrderEntryToSendForApproval.setOrderStatus(internalOrderReadyStatus);
		internalOrderEntryToSendForApproval
				.setDescription(InternalOrderTestConstants.REMARKS_TO_APPROVE_AN_INTERNAL_ORDER);
		String payload = APIUtilities.convertXMLObjectToString(internalOrderEntryToSendForApproval);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToUpdateInternalOrder(internalOrderId), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, getTokenForIntenalOrderOperations());
		InternalOrderResponse internalOrderUpdateResponse = (InternalOrderResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new InternalOrderResponse());
		return internalOrderUpdateResponse;

	}

	/** The method marks an internal order as shipped and verifies that the operation was successful
	 * @param internalOrderId
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static void shipAnInternalOrderAndverify(long internalOrderId)
			throws JAXBException, UnsupportedEncodingException {
		InternalOrderResponse internalOrderUpdateResponse = shipAnInternalOrder(internalOrderId);
		Assert.assertTrue(internalOrderUpdateResponse.getStatus().isSuccess(),
				"The response status  for shipping the order was not successfull...please see the response above for status code and message");
		Assert.assertEquals(internalOrderUpdateResponse.getStatus().getStatusMessage(),
				InternalOrderTestConstants.INTERNAL_ORDER_STATE_TRANSITION_SUCCESS_MESSAGE);
		Assert.assertEquals(internalOrderUpdateResponse.getStatus().getStatusCode(),
				InternalOrderTestConstants.INTERNAL_ORDER_STATE_TRANSITION_SUCCESS_CODE);
	}

	/** The method marks an internal order as shipped and returns the response
	 * @param internalOrderId
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static InternalOrderResponse shipAnInternalOrder(long internalOrderId)
			throws JAXBException, UnsupportedEncodingException {
		InternalOrderEntry internalOrderEntryToSendForApproval = new InternalOrderEntry();
		internalOrderEntryToSendForApproval.setId(internalOrderId);
		InternalOrderStatus internalOrderReadyStatus = InternalOrderStatus.SHIPPED;
		internalOrderEntryToSendForApproval.setOrderStatus(internalOrderReadyStatus);
		internalOrderEntryToSendForApproval
				.setDescription(InternalOrderTestConstants.REMARKS_TO_MOVE_IO_TO_SHIPPED_STATE);
		String payload = APIUtilities.convertXMLObjectToString(internalOrderEntryToSendForApproval);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToUpdateInternalOrder(internalOrderId), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, getTokenForIntenalOrderOperations());
		InternalOrderResponse internalOrderUpdateResponse = (InternalOrderResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new InternalOrderResponse());
		return internalOrderUpdateResponse;
	}

	/** The method sends and internal order to pick initiation and asserts the success
	 * @param internalOrderId
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static void sendAnInternalOrderForPickInitiation(long internalOrderId)
			throws JAXBException, UnsupportedEncodingException {
		InternalOrderEntry internalOrderEntryToSendForApproval = new InternalOrderEntry();
		internalOrderEntryToSendForApproval.setId(internalOrderId);
		InternalOrderStatus internalOrderReadyStatus = InternalOrderStatus.PICK_INITIATED;
		internalOrderEntryToSendForApproval.setOrderStatus(internalOrderReadyStatus);
		internalOrderEntryToSendForApproval
				.setDescription(InternalOrderTestConstants.REMARKS_TO_MOVE_IO_TO_PICK_INITIATION);
		String payload = APIUtilities.convertXMLObjectToString(internalOrderEntryToSendForApproval);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToUpdateInternalOrder(internalOrderId), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, getTokenForIntenalOrderOperations());
		InternalOrderResponse internalOrderUpdateResponse = (InternalOrderResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new InternalOrderResponse());
		Assert.assertTrue(internalOrderUpdateResponse.getStatus().isSuccess(),
				"The internal status of the approval was not successfull...please see the response above for status code and message");
		Assert.assertEquals(internalOrderUpdateResponse.getStatus().getStatusMessage(),
				InternalOrderTestConstants.INTERNAL_ORDER_STATE_TRANSITION_SUCCESS_MESSAGE);
		Assert.assertEquals(internalOrderUpdateResponse.getStatus().getStatusCode(),
				InternalOrderTestConstants.INTERNAL_ORDER_STATE_TRANSITION_SUCCESS_CODE);
	}

	/** The method tries to decline an internal order and verifies success
	 * @param internalOrderId
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static void declineAnInternalOrderAndVerifySuccess(long internalOrderId)
			throws JAXBException, UnsupportedEncodingException {
		InternalOrderEntry internalOrderEntryToSendForApproval = new InternalOrderEntry();
		internalOrderEntryToSendForApproval.setId(internalOrderId);
		InternalOrderStatus internalOrderReadyStatus = InternalOrderStatus.DECLINED;
		internalOrderEntryToSendForApproval.setOrderStatus(internalOrderReadyStatus);
		internalOrderEntryToSendForApproval
				.setDescription(InternalOrderTestConstants.REMARKS_TO_MOVE_IO_TO_SEND_FOR_APPROVAL);
		String payload = APIUtilities.convertXMLObjectToString(internalOrderEntryToSendForApproval);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToUpdateInternalOrder(internalOrderId), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, getTokenForIntenalOrderOperations());
		InternalOrderResponse internalOrderUpdateResponse = (InternalOrderResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new InternalOrderResponse());
		Assert.assertTrue(internalOrderUpdateResponse.getStatus().isSuccess(),
				"The internal status of the approval was not successfull...please see the response above for status code and message");
		Assert.assertEquals(internalOrderUpdateResponse.getStatus().getStatusMessage(),
				InternalOrderTestConstants.INTERNAL_ORDER_STATE_TRANSITION_SUCCESS_MESSAGE);
		Assert.assertEquals(internalOrderUpdateResponse.getStatus().getStatusCode(),
				InternalOrderTestConstants.INTERNAL_ORDER_STATE_TRANSITION_SUCCESS_CODE);
	}

	/** The method tries to associate the SKU with an internal order passed 
	 * @param internalOrderId
	 * @param skuId
	 * @param quantity
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static void associateSkuWithAnInternalOrder(long internalOrderId, long skuId, int quantity)
			throws JAXBException, UnsupportedEncodingException {

		IOSkuEntry ioSkuEntry = new IOSkuEntry();
		ioSkuEntry.setQuality(Quality.Q1);
		ioSkuEntry.setQuantity((double) quantity);
		ioSkuEntry.setId(internalOrderId);
		SkuEntry skuEntry = new SkuEntry();
		skuEntry.setId(skuId);
		ioSkuEntry.setSku(skuEntry);
		String payload = APIUtilities.convertXMLObjectToString(ioSkuEntry);

		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToSkuAndInternalOrderAssociationApi(internalOrderId), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getTokenForIntenalOrderOperations());
		IOSkuResponse ioSkuResponse = (IOSkuResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new IOSkuResponse());
		Assert.assertEquals(ioSkuResponse.getStatus().getStatusMessage(),
				WmsSuccessCodes.IO_SKU_ADDED.getStatusMessage());
		Assert.assertEquals(ioSkuResponse.getStatus().getStatusCode(), WmsSuccessCodes.IO_SKU_ADDED.getStatusCode());

	}

	/** The method reads the internal order properties to find the IOSKU association to de-associate the internal order with SKU
	 * @param internalOrderId
	 * @param skuId
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static void findAndDeleteSkuAssociationFromAnInternalOrder(long internalOrderId, long skuId)
			throws JAXBException, UnsupportedEncodingException {

		IOSkuEntry ioSkuEntry = new IOSkuEntry();
		ioSkuEntry.setIoId(internalOrderId);
		String payload = APIUtilities.convertXMLObjectToString(ioSkuEntry);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToSkuAndInternalOrderAssociationApi(internalOrderId), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, payload, getTokenForIntenalOrderOperations());
		IOSkuResponse iOSkuResponse = (IOSkuResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new IOSkuResponse());
		Assert.assertTrue(iOSkuResponse.getStatus().isSuccess(),
				"The internal status of the approval was not successfull...please see the response above for status code and message");
		Assert.assertEquals(iOSkuResponse.getStatus().getStatusMessage(),
				WmsSuccessCodes.IO_SKU_RETRIEVED.getStatusMessage());
		Assert.assertEquals(iOSkuResponse.getStatus().getStatusCode(),
				WmsSuccessCodes.IO_SKU_RETRIEVED.getStatusCode());
		List<IOSkuEntry> iOSkuEntries = iOSkuResponse.getData();
		for (IOSkuEntry eachIoSkuEntry : iOSkuEntries) {
			if (eachIoSkuEntry.getSku().getId() == skuId) {
				deleteSkuAssociationFromAnInternalOrder(eachIoSkuEntry.getId());
			}
		}

	}

	/**The method deletes the passed ioSkuEntryId  and removes the association of SKU and internal order Id
	 * @param ioSkuEntryId
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	private static void deleteSkuAssociationFromAnInternalOrder(long ioSkuEntryId)
			throws JAXBException, UnsupportedEncodingException {
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToDeleteIoSkuAssociation(ioSkuEntryId), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.DELETE, "", getTokenForIntenalOrderOperations());
		IOSkuResponse iOSkuResponse = (IOSkuResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new IOSkuResponse());
		Assert.assertTrue(iOSkuResponse.getStatus().isSuccess(),
				"The internal status of the approval was not successfull...please see the response above for status code and message");
		Assert.assertEquals(iOSkuResponse.getStatus().getStatusMessage(),
				WmsSuccessCodes.IO_SKU_DELETED.getStatusMessage());
		Assert.assertEquals(iOSkuResponse.getStatus().getStatusCode(), WmsSuccessCodes.IO_SKU_DELETED.getStatusCode());

	}

	/** The method returns all the skus associated with the internal order id passed
	 * @param internalOrderId
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static Set<Integer> getAllSkusAssociatedWithTheIO(long internalOrderId)
			throws JAXBException, UnsupportedEncodingException {
		Set<Integer> skuSet = new HashSet<Integer>();
		IOSkuEntry ioSkuEntry = new IOSkuEntry();
		ioSkuEntry.setId(internalOrderId);

		String payload = APIUtilities.convertXMLObjectToString(ioSkuEntry);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToSkuAndInternalOrderAssociationApi(internalOrderId), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, payload, getTokenForIntenalOrderOperations());
		IOSkuResponse iOSkuResponse = (IOSkuResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new IOSkuResponse());
		Assert.assertTrue(iOSkuResponse.getStatus().isSuccess(),
				"The internal status of the approval was not successfull...please see the response above for status code and message");
		Assert.assertEquals(iOSkuResponse.getStatus().getStatusMessage(),
				WmsSuccessCodes.IO_SKU_RETRIEVED.getStatusMessage());
		Assert.assertEquals(iOSkuResponse.getStatus().getStatusCode(),
				WmsSuccessCodes.IO_SKU_RETRIEVED.getStatusCode());
		List<IOSkuEntry> iOSkuEntries = iOSkuResponse.getData();
		for (IOSkuEntry eachIoSkuEntry : iOSkuEntries) {
			skuSet.add(eachIoSkuEntry.getSku().getId().intValue());
		}
		return skuSet;
	}

	/** The method returns all the distinct items associated with the passed internal order Id
	 * @param internalOrderId
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static Set<Integer> getAllItemsAssociatedWithTheIO(long internalOrderId)
			throws JAXBException, UnsupportedEncodingException {
		Set<Integer> items = new HashSet<Integer>();
		IOItemEntry ioItemEntry = new IOItemEntry();
		ioItemEntry.setId(internalOrderId);
		String payload = APIUtilities.convertXMLObjectToString(ioItemEntry);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToItemInternalAssociationApi(internalOrderId), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, payload, getTokenForIntenalOrderOperations());
		IOItemResponse itemIoresponse = (IOItemResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new IOItemResponse());
		Assert.assertTrue(itemIoresponse.getStatus().isSuccess(),
				"The internal status of the approval was not successfull...please see the response above for status code and message");
		Assert.assertEquals(itemIoresponse.getStatus().getStatusMessage(),
				WmsSuccessCodes.IO_ITEMS_RETRIEVED.getStatusMessage());
		Assert.assertEquals(itemIoresponse.getStatus().getStatusCode(),
				WmsSuccessCodes.IO_ITEMS_RETRIEVED.getStatusCode());
		List<IOItemEntry> itemList = itemIoresponse.getData();
		for (IOItemEntry eachIoItemEntry : itemList) {
			items.add(eachIoItemEntry.getItem().getId().intValue());
		}
		return items;
	}

	/** The method checks out the order id and item id
	 * @param myntraOrderId
	 * @param itemBarcode
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static void simulateItemAnOrderCheckoutEventInMaterialMovementsPage(long myntraOrderId, String itemBarcode)
			throws JAXBException, UnsupportedEncodingException {
		CheckoutEntry checkoutEntry = new CheckoutEntry();
		checkoutEntry.setOrderId(getInternalOrderBarcodeByOrderId(myntraOrderId));
		List<ItemEntry> listOfItemsToCheckout = new ArrayList<ItemEntry>();
		ItemEntry itemEntry = new ItemEntry();
		itemEntry.setId(Long.valueOf(itemBarcode));
		listOfItemsToCheckout.add(itemEntry);
		checkoutEntry.setItems(listOfItemsToCheckout);
		String payload = APIUtilities.convertXMLObjectToString(checkoutEntry);
		log.info("The payLoad to create Internal order is :: " + payload.toString());
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToCheckOutOrderFromMaterialMovementsPage(), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getTokenForIntenalOrderOperations());
		ItemResponse checkOutResponse = (ItemResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new ItemResponse());
		Assert.assertTrue(checkOutResponse.getStatus().isSuccess(),
				"The order-item checkout response was not success....please see the response above for status code and message");
		Assert.assertEquals(checkOutResponse.getStatus().getStatusMessage(),
				InternalOrderTestConstants.ITEM_CHECK_OUT_SUCCESS_MESSAGE);
		Assert.assertEquals(checkOutResponse.getStatus().getStatusCode(),
				InternalOrderTestConstants.ITEM_CHECK_OUT_SUCCESS_CODE);

	}

	/** The method tries to change the following properties of the internal order
	 * 1.WareHouse id 
	 * 2.InternalOrderType
	 * 3.Approver to a non-existing user
	 * @param internalOrderId
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static InternalOrderResponse editInternalOrderAndReturnResponse(long internalOrderId)
			throws JAXBException, UnsupportedEncodingException {

		InternalOrderEntry internalOrderEntry = new InternalOrderEntry();
		internalOrderEntry.setOrderType(InternalOrderType.Flipkart_Sale);
		internalOrderEntry.setId(internalOrderId);
		WarehouseEntry wareHouseEntry = new WarehouseEntry();
		wareHouseEntry.setId((long) 28);
		internalOrderEntry.setWarehouse(wareHouseEntry);
		internalOrderEntry.setApprover("non existing user");

		String payload = APIUtilities.convertXMLObjectToString(internalOrderEntry);
		log.info("The payLoad to create Internal order is :: " + payload.toString());
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.INTERNAL_ORDER_PROCESSING_URL,
				new String[] {}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload,
				getTokenForIntenalOrderOperations());
		InternalOrderResponse internalOrderCreateCallResponse = (InternalOrderResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new InternalOrderResponse());
		return internalOrderCreateCallResponse;
	}

	/**
	 * The method creates items on demand based on quality ,wareHouse id ,skuId
	 * and item status
	 * 
	 * @param quantity
	 * @param skuId
	 * @param wareHouseId
	 * @return
	 */
	public static List<String> createTestStoredItemsWithQualityQ2(int quantityToCreate, int skuId, int wareHouseId, long buyerId) {
		if (quantityToCreate < 1) {
			return null;
		}
		int quantity = quantityToCreate;
		String quality = Quality.Q2.toString();
		String itemStatus = "STORED";
		
		String agreementType = wmsServiceHelper.getAgreementType(25657);
		
		log.info("Creating  " + quantity + "item(s) with itemStatus as " + itemStatus + " and quality " + quality
				+ " for wareHouseId " + wareHouseId);
		List<String> items = new ArrayList<String>();
		String qualityParam = "'" + quality + "'";
		while (quantity > 0) {
			BigInteger id = new BigInteger(getMaximumExistingIdForTheSku());
			log.info("The max barcode that exists in the table is " + id.toString());
			BigInteger valueOneBigIneteger = new BigInteger("1");
			BigInteger newMaxId = id.add(valueOneBigIneteger);
			String insertItemQuery = "INSERT INTO `item` (`id`, `barcode`, `sku_id`, `quality`, `item_status`, `warehouse_id`, `enabled`, `po_id`, `po_barcode`, `po_sku_id`, `lot_id`, `lot_barcode`, `comments`, `order_id`, `bin_id`, `grn_sku_id`, `grn_barcode`, `inwarded_on`, `reject_reason_code`, `reject_reason_description`, `carton_barcode`, `created_on`, `created_by`, `last_modified_on`, `version`)VALUES ("
					+ newMaxId + "," + newMaxId + "," + skuId + "," + qualityParam + ", '" + itemStatus + "',"
					+ wareHouseId
					+ ", 1, 25657, 'AEXA180414-00', 2378007, 27232, 'LAEXA22041401', 'Created for PO: AEXA180414-00', '0', 271760, NULL, NULL, '2014-04-22 17:16:38', '', '', NULL, '2014-04-18 16:02:48', 'anil.antony', '2014-05-17 21:57:23', 12)";
			DBUtilities.exUpdateQuery(insertItemQuery, "myntra_wms");
			
			insertItemQuery = "INSERT INTO `item_info` (`item_id`, `item_action_status`, `task_id`, `order_barcode`, `created_on`, `created_by`, `last_modified_on`, `version`, `order_id`, `invoice_sku_id`, `agreement_type`, `buyer_id`)"
					+ "VALUES(" + newMaxId +", 'NEW', NULL, NULL, now(), 'erpMessageQueue', now(), 0, NULL, NULL, '"+ agreementType +"', " + buyerId + ")";
			DBUtilities.exUpdateQuery(insertItemQuery, "myntra_wms");
			
			items.add((newMaxId + "").trim());
			--quantity;
		}
		Assert.assertTrue((items.size() > 0 && items.size() == quantityToCreate),
				"The items generated were not equal to the quanity [ " + quantity + " ]  which was desired..");
		return items;
	}

	/**
	 * The method returns the maximum auto generated item id
	 * 
	 * @return
	 */
	static String getMaximumExistingIdForTheSku() {
		return String.valueOf(DBUtilities.exSelectQueryForSingleRecord("select max(`id`) from `item` ", "myntra_wms")
				.get("max(`id`)"));
	}

	void putOrderOnHold() {

	}

	/**
	 * Headers for the WMS Service
	 * 
	 * @return
	 */
	public static HashMap<String, String> getTokenForIntenalOrderOperations() {
		HashMap<String, String> stnHeaders = new HashMap<String, String>();
		stnHeaders.put("Authorization", "Basic RVJQIEFkbWluaXN0cmF0b3J+ZXJwYWRtaW46dGVzdA==");
		stnHeaders.put("Content-Type", "Application/xml");
		return stnHeaders;
	}
	
	public static void makeInventoryEmpty(int skuId, int warehouseId, long sellerId){
		
		DBUtilities.exUpdateQuery(
				"UPDATE `inventory` SET `inventory_count` = 0, blocked_order_count = 0 where sku_id=" + skuId + " and available_in_warehouses like '%"+ warehouseId + "%' and seller_id = " + sellerId + " and enabled = 1",
				"myntra_atp");
		
		DBUtilities.exUpdateQuery(
				"UPDATE `wh_inventory` SET `inventory_count` = 0, blocked_order_count = 0 where sku_id=" + skuId + " and warehouse_id = "+ warehouseId + " and seller_id = "+ sellerId,
				"myntra_ims");
	}
}
