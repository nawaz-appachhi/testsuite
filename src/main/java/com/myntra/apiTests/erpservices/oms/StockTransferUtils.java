/**
 * 
 */
package com.myntra.apiTests.erpservices.oms;

import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.StnTestConstants;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSHelper;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.client.inventory.enums.Quality;
import com.myntra.client.wms.codes.utils.ItemStatus;
import com.myntra.client.wms.codes.utils.STNStatus;
import com.myntra.client.wms.codes.utils.StnCategory;
import com.myntra.client.wms.response.*;
import com.myntra.client.wms.response.location.WarehouseEntry;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import com.myntra.vms.client.entry.VendorEntry;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import javax.xml.bind.JAXBException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** The class provides methods to perform STN related tasks 
 * @author puneet.khanna1@myntra.com
 *@since July 2016
 *
 */
public class StockTransferUtils {
	private static Logger log = LoggerFactory.getLogger(StockTransferUtils.class);
	static WMSHelper wmsHelper = new WMSHelper();
	
	private static Long vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
	
	private static WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	private static IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	
	/** The method creates STN and returns the StnEntry List from the reponse
	 * The method throws assert error when there is no response received
	 * @param sourceWareHouseId
	 * @param destinationWareId
	 * @param statusOfTheItemToTransfer
	 * @param quality
	 * @param remarks
	 * @param approverEmailAddress
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static List<StnEntry> createStockTransferRequestAndReturnCreatedStnEntryListFromResponse(
			int sourceWareHouseId, int destinationWareId, ItemStatus statusOfTheItemToTransfer, Quality quality,
			String remarks, String approverEmailAddress,StnCategory stnCategory, long vendorId) throws JAXBException, UnsupportedEncodingException {

		StnEntry stockTransferNoteEntry = new StnEntry();

		WarehouseEntry sourceWareHouseEntry = new WarehouseEntry();
		sourceWareHouseEntry.setId((long) sourceWareHouseId);
		WarehouseEntry destinationWareHouse = new WarehouseEntry();
		destinationWareHouse.setId((long) destinationWareId);
		VendorEntry entry = new VendorEntry();
		entry.setId(vendorId);
		stockTransferNoteEntry.setSourceWareHouse(sourceWareHouseEntry);
		stockTransferNoteEntry.setDestinationWareHouse(destinationWareHouse);
		stockTransferNoteEntry.setQuality(quality);
		stockTransferNoteEntry.setStnCategory(stnCategory);
		stockTransferNoteEntry.setItemStatus(statusOfTheItemToTransfer);
		stockTransferNoteEntry.setCreatedBy("");
		stockTransferNoteEntry.setApprover(approverEmailAddress);
		stockTransferNoteEntry.setRemarks(remarks);
		stockTransferNoteEntry.setBuyer(entry);

		String payload = APIUtilities.convertXMLObjectToString(stockTransferNoteEntry);
		log.info("The payLoad to create stockTransferNoteEntry is :: " + payload.toString());

		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.STOCK_TRANSFER_NOTE_PROCESSING_URL,
                                                             new String[] {}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getTokenForStnOperations());

		StnResponse stnCreateCallResponse = (StnResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new StnResponse());

		Assert.assertTrue(null != stnCreateCallResponse, "There was no response obtained from the server");

		log.info("Printing STN create attempt response below  ::- ");

		log.info(stnCreateCallResponse.toString());

		List<StnEntry> stnEntriesList = stnCreateCallResponse.getData();

		return stnEntriesList;
	}
	
	public static void uploadItemsFile(Long stnId, String fileType, String fileName, String filePath) throws UnsupportedEncodingException, JAXBException, InterruptedException{
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		File file = new File(filePath);
		multipartEntityBuilder.addPart("file", new FileBody(file));
		multipartEntityBuilder.setContentType(ContentType.APPLICATION_OCTET_STREAM);
		
		Svc service = HttpExecutorService.executeHttpServiceForUpload(Constants.WMS_PATH.STOCK_TRANSFER_NOTE_PROCESSING_URL,
                new String[] { "validateAndCreateStnItemsSkus/" + stnId + "?fileType=" + fileType + "&filenameWithExtension=" +fileName }, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, multipartEntityBuilder, getTokenForStnFileUpload());
		
		StnResponse response = (StnResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new StnResponse());
		
		Assert.assertEquals(response.getStatus().getStatusMessage(), 
				"Request is submitted. Please check status of the request and download it once complete from the Download Document section .");
		Thread.sleep(15000);
	}
	
	public static String createStnItemsCSVFile(List<String> items) throws IOException{
		File file = new File("./Data/stn.csv");
		if(file.exists()){
			file.delete();
		}
		FileWriter writer = new FileWriter(file, true);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		bufferedWriter.write("Item Id");
		bufferedWriter.newLine();
		for(String item: items){
			bufferedWriter.write(item);
			bufferedWriter.newLine();
		}
		bufferedWriter.close();
		return file.getPath();
	}
	
	public static boolean sendStnForApproval(long stnId) throws UnsupportedEncodingException, JAXBException{
		StnEntry stockTransferNoteEntry = new StnEntry();
		stockTransferNoteEntry.setId(stnId);
		stockTransferNoteEntry.setStnStatus(STNStatus.READY);
		String payload = APIUtilities.convertXMLObjectToString(stockTransferNoteEntry);
		
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.STOCK_TRANSFER_NOTE_PROCESSING_URL, new String[] {""+stnId}, 
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, getTokenForStnOperations());
		StnResponse response = (StnResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new StnResponse());
		if(response.getStatus().getStatusMessage().equalsIgnoreCase("STN updated successfully")){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean validateStnQuantity(long stnId) throws UnsupportedEncodingException, JAXBException{
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.STOCK_TRANSFER_NOTE_PROCESSING_URL, new String[] {"validateStnQty/"+stnId}, 
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, null, getTokenForStnOperations());
		StnResponse response = (StnResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new StnResponse());
		if(response.getStatus().getStatusMessage().equalsIgnoreCase("Success")){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean approveStn(long stnId, int sourceWarehouseId, int destinationWarehouseId, Quality quality,
			ItemStatus itemStatus, StnCategory stnCategory, String approver, String remarks) throws UnsupportedEncodingException, JAXBException{
		
		StnEntry stockTransferNoteEntry = new StnEntry();
		stockTransferNoteEntry.setId(stnId);
		WarehouseEntry sourceWareHouseEntry = new WarehouseEntry();
		sourceWareHouseEntry.setId((long) sourceWarehouseId);
		stockTransferNoteEntry.setSourceWareHouse(sourceWareHouseEntry);
		WarehouseEntry destinationWareHouse = new WarehouseEntry();
		destinationWareHouse.setId((long) destinationWarehouseId);
		stockTransferNoteEntry.setDestinationWareHouse(destinationWareHouse);
		stockTransferNoteEntry.setQuality(quality);
		stockTransferNoteEntry.setStnCategory(stnCategory);
		stockTransferNoteEntry.setItemStatus(itemStatus);
		stockTransferNoteEntry.setApprover(approver);
		stockTransferNoteEntry.setRemarks(remarks);
		stockTransferNoteEntry.setStnStatus(STNStatus.APPROVED);
	
		String payload = APIUtilities.convertXMLObjectToString(stockTransferNoteEntry);
		
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.STOCK_TRANSFER_NOTE_PROCESSING_URL, new String[] {""+stnId}, 
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, getTokenForStnOperations());
		StnResponse response = (StnResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new StnResponse());
		if(response.getStatus().getStatusMessage().equalsIgnoreCase("STN updated successfully")){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean pickInitiate(long stnId, int sourceWarehouseId, int destinationWarehouseId, Quality quality,
			ItemStatus itemStatus, StnCategory stnCategory, String approver, String remarks) throws UnsupportedEncodingException, JAXBException{
		
		StnEntry stockTransferNoteEntry = new StnEntry();
		stockTransferNoteEntry.setId(stnId);
		WarehouseEntry sourceWareHouseEntry = new WarehouseEntry();
		sourceWareHouseEntry.setId((long) sourceWarehouseId);
		stockTransferNoteEntry.setSourceWareHouse(sourceWareHouseEntry);
		WarehouseEntry destinationWareHouse = new WarehouseEntry();
		destinationWareHouse.setId((long) destinationWarehouseId);
		stockTransferNoteEntry.setDestinationWareHouse(destinationWareHouse);
		stockTransferNoteEntry.setQuality(quality);
		stockTransferNoteEntry.setStnCategory(stnCategory);
		stockTransferNoteEntry.setItemStatus(itemStatus);
		stockTransferNoteEntry.setApprover(approver);
		stockTransferNoteEntry.setRemarks(remarks);
		stockTransferNoteEntry.setStnStatus(STNStatus.PICK_INITIATED);
	
		String payload = APIUtilities.convertXMLObjectToString(stockTransferNoteEntry);
		
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.STOCK_TRANSFER_NOTE_PROCESSING_URL, new String[] {""+stnId}, 
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, getTokenForStnOperations());
		StnResponse response = (StnResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new StnResponse());
		if(response.getStatus().getStatusMessage().equalsIgnoreCase("PICK_INITIATED")){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean verifyDownloadDocumentStatus(long stnId, String docStatus) throws UnsupportedEncodingException, JAXBException, InterruptedException{
		Svc service;
		DownloadPdfResponse downloadPdfResponse;
		boolean barcodeFound = false;
		
		String barcode = (String) DBUtilities
				.exSelectQueryForSingleRecord("select barcode from core_stns where id = " + stnId,
						"myntra_wms").get("barcode");
		
		List<DownloadEntry> downloadEntries;

		for(int i=0; i<15; i++){
			
			barcodeFound = false;
			
			service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.STOCK_TRANSFER_DOWNLOAD_DOCUMENT,
					new String[] {"search?q=userName.like:ERP%2BAdministrator&f=id,userName,entityName,barCode,status,remarks,attachmentId,fileName,createdOn,lastModifiedOn&start=0&fetchSize=4&sortBy=id&sortOrder=DESC"}, 
					SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForStnOperations());
			
			downloadPdfResponse = (DownloadPdfResponse) APIUtilities
					.convertXMLStringToObject(service.getResponseBody(), new DownloadPdfResponse());
			
			downloadEntries = downloadPdfResponse.getData();
			
			for(DownloadEntry downloadEntry : downloadEntries){
				if(downloadEntry.getBarCode().equalsIgnoreCase(barcode)){
					
					barcodeFound = true;
					
					if(downloadEntry.getStatus().toString().equalsIgnoreCase("PROCESSING")){
						log.info("Doc Status is in PROCESSING");
						break;
						
					}else if(downloadEntry.getStatus().toString().equalsIgnoreCase("COMPLETED")){
						log.info("Doc Status is in COMPLETED");
						
						if(verifyStatus(downloadEntry.getStatus().toString(), docStatus, downloadEntry.getFileName())){
							return true;
							
						}else{
							return false;
						}
					}else{
						
					}
				}
			}
			if(!barcodeFound){
				log.info("Did not find the STN Barcode in Download Documents");
				Assert.fail("Did not find the STN Barcode in Download Documents");
			}
			Thread.sleep(15000);
		}
		return false;
	}
	
	public static boolean verifyStatus(String actualStatus, String expectedStatus, String fileName){
		if(expectedStatus.equals("")){
			if(!fileName.contains("error")){
				return true;
			}else{
				return false;
			}
		}else{
			if(actualStatus.equalsIgnoreCase(expectedStatus)){
				return true;
			}else{
				return false;
			}
		}
	}
	
	/**The method creates STN and returns the response of the request
	 * @param sourceWareHouseId
	 * @param destinationWareId
	 * @param statusOfTheItemToTransfer
	 * @param quality
	 * @param remarks
	 * @param approverEmailAddress
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static StnResponse createStockTransferRequestAndReturnResponse(
			int sourceWareHouseId, int destinationWareId, ItemStatus statusOfTheItemToTransfer, Quality quality,
			String remarks, String approverEmailAddress, long vendorId) throws JAXBException, UnsupportedEncodingException {
		
		VendorEntry entry = new VendorEntry();
		entry.setId(vendorId);
		
		StnEntry stockTransferNoteEntry = new StnEntry();
		WarehouseEntry sourceWareHouseEntry = new WarehouseEntry();
		sourceWareHouseEntry.setId((long) sourceWareHouseId);
		stockTransferNoteEntry.setSourceWareHouse(sourceWareHouseEntry);
		WarehouseEntry destinationWareHouse = new WarehouseEntry();
		destinationWareHouse.setId((long) destinationWareId);
		stockTransferNoteEntry.setDestinationWareHouse(destinationWareHouse);
		stockTransferNoteEntry.setQuality(quality);
		stockTransferNoteEntry.setItemStatus(statusOfTheItemToTransfer);
		stockTransferNoteEntry.setCreatedBy("");
		stockTransferNoteEntry.setApprover(approverEmailAddress);
		stockTransferNoteEntry.setRemarks(remarks);
		stockTransferNoteEntry.setBuyer(entry);
		// stockTransferNoteEntry.setPackingUser(packingUser);
		String payload = APIUtilities.convertXMLObjectToString(stockTransferNoteEntry);
		log.info("The payLoad to create stockTransferNoteEntry is :: " + payload.toString());
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.STOCK_TRANSFER_NOTE_PROCESSING_URL,
				new String[] {}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getTokenForStnOperations());
		StnResponse stnCreateCallResponse = (StnResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new StnResponse());
		Assert.assertTrue(null != stnCreateCallResponse, "There was no response obtained from the server");
		log.info("Printing STN create attempt response below  ::- ");
		log.info(stnCreateCallResponse.toString());
		
		return stnCreateCallResponse;
	}
	
	/**The method creates STN and verifies the response 
	 * based on the passed params expectedStatusTypeInReponse,expectedmessage,expectedStatusCode,ifSuccessExpected
	 * The method throws assert error if the response is not expected
	 * @param sourceWareHouseId
	 * @param destinationWareId
	 * @param statusOfTheItemToTransfer
	 * @param quality
	 * @param remarks
	 * @param approverEmailAddress
	 * @param expectedInvalidWarehouseInputForStnCreationStatusCode
	 * @param isSuccessExpectedFlag
	 * @param statusTypeError
	 * @param stnCategory
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static void createStockTransferNoteAndVerifyExpectedResponse(int sourceWareHouseId, int destinationWareId,
			ItemStatus statusOfTheItemToTransfer, Quality quality, String remarks, String approverEmailAddress,String STN_Status_Message_InvalidWH,
			int expectedInvalidWarehouseInputForStnCreationStatusCode, boolean isSuccessExpectedFlag, String statusTypeError,
			StnCategory stnCategory) throws JAXBException, UnsupportedEncodingException {

		StnEntry stockTransferNoteEntry = new StnEntry();
		WarehouseEntry sourceWareHouseEntry = new WarehouseEntry();
		sourceWareHouseEntry.setId((long) sourceWareHouseId);
		stockTransferNoteEntry.setSourceWareHouse(sourceWareHouseEntry);
		WarehouseEntry destinationWareHouse = new WarehouseEntry();
		destinationWareHouse.setId((long) destinationWareId);
		stockTransferNoteEntry.setDestinationWareHouse(destinationWareHouse);
		stockTransferNoteEntry.setQuality(quality);
		stockTransferNoteEntry.setItemStatus(statusOfTheItemToTransfer);
		stockTransferNoteEntry.setCreatedBy("");
		stockTransferNoteEntry.setStnCategory(stnCategory);
		stockTransferNoteEntry.setApprover(approverEmailAddress);
		stockTransferNoteEntry.setRemarks(remarks);

		String payload = APIUtilities.convertXMLObjectToString(stockTransferNoteEntry);
		log.info("The payLoad to create stockTransferNoteEntry is :: " + payload.toString());
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.STOCK_TRANSFER_NOTE_PROCESSING_URL,
				new String[] {}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getTokenForStnOperations());
		StnResponse stnCreateCallResponse = (StnResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new StnResponse());

		Assert.assertNotNull(stnCreateCallResponse, "There was no response obtained from the server");
		log.info("Printing STN create attempt response below  ::- ");
		log.info(stnCreateCallResponse.toString());
		log.info("Error Message is ", stnCreateCallResponse.getStatus().getStatusType());
		Assert.assertEquals(statusTypeError,stnCreateCallResponse.getStatus().getStatusType().toString());
		Assert.assertEquals(stnCreateCallResponse.getStatus().getStatusCode(), expectedInvalidWarehouseInputForStnCreationStatusCode);
		Assert.assertEquals(stnCreateCallResponse.getStatus().getStatusMessage().toString(),STN_Status_Message_InvalidWH );
	}

	/**
	 * Headers for the WMS Service
	 * 
	 * @return
	 */
	public static HashMap<String, String> getTokenForStnOperations() {
		HashMap<String, String> stnHeaders = new HashMap<String, String>();
		stnHeaders.put("Authorization", "Basic RVJQIEFkbWluaXN0cmF0b3J+ZXJwYWRtaW46dGVzdA==");
		stnHeaders.put("Content-Type", "Application/xml");
		return stnHeaders;
	}
	
	public static HashMap<String, String> getTokenForStnFileUpload() {
		HashMap<String, String> stnHeaders = new HashMap<String, String>();
		stnHeaders.put("Authorization", "Basic RVJQIEFkbWluaXN0cmF0b3J+ZXJwYWRtaW46dGVzdA==");
		stnHeaders.put("Content-Type", "multipart/form-data");
		return stnHeaders;
	}

	/** The method returns the maximum auto generated item id 
	 * @return
	 */
	static String getMaximumExistingIdForTheSku() {
		return String.valueOf(DBUtilities.exSelectQueryForSingleRecord("select max(`id`) from `item` ", "myntra_wms")
				.get("max(`id`)"));
	}

	/** The method tries to create STN and verifies the success 
	 * The method returns the Stn id if the create STN call is sucessful
	 * @param sourceWareHouseId
	 * @param destinationWareId
	 * @param statusOfTheItemToTransfer
	 * @param quality
	 * @param remarks
	 * @param approverEmailAddress
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static Long createStnAndVerifyCreationToReturnStnId(int sourceWareHouseId, int destinationWareId,
			ItemStatus statusOfTheItemToTransfer, Quality quality, String remarks, String approverEmailAddress,StnCategory stnCategory, long vendorId)
					throws UnsupportedEncodingException, JAXBException {

		Long createdStnId = null;
		List<StnEntry> createdStnEntries = createStockTransferRequestAndReturnCreatedStnEntryListFromResponse(
				sourceWareHouseId, destinationWareId, statusOfTheItemToTransfer, quality, remarks,
				approverEmailAddress,stnCategory, vendorId);

		Assert.assertTrue(null != createdStnEntries && !createdStnEntries.isEmpty(),
				"There are no stns created successfully");

		// Since getData on the response returns a list but there is going to be
		// only 1 STN created , we get the first element in the list and process
		// it
		createdStnId = ((StnEntry) createdStnEntries.get(0)).getId();

		log.info("The created STN Id is " + createdStnId);

		return createdStnId;

	}

	/** The method creates items on demand based on quality ,wareHouse id ,skuId and item status
	 * @param quality
	 * @param quantity
	 * @param skuId
	 * @param wareHouseId
	 * @param itemStatus
	 * @return
	 */
	public static List<String> createTestItems(String quality, int quantity,
			int skuId, int wareHouseId, String itemStatus, long buyerId, int poSkuId) {
		if(quantity<1){
			return null;
		}
		log.info("Creating  "+ quantity+"item(s) with itemStatus as "+itemStatus+" and quality "+quality+" for wareHouseId "+wareHouseId);
		List<String> items = new ArrayList<String>();
		String qualityParam = "'" + quality + "'";
		
		while (quantity > 0) {
			BigInteger id = new BigInteger(getMaximumExistingIdForTheSku());
			log.info("The max barcode that exists in the table is " + id.toString());
			BigInteger valueOneBigIneteger = new BigInteger("1");
			BigInteger newMaxId = id.add(valueOneBigIneteger);
			String insertItemQuery = "INSERT INTO `item` (`id`, `barcode`, `sku_id`, `quality`, `item_status`, `warehouse_id`, `enabled`, `po_id`, `po_barcode`, `po_sku_id`, `lot_id`, `lot_barcode`, `comments`, `order_id`, `bin_id`, `grn_sku_id`, `grn_barcode`, `inwarded_on`, `reject_reason_code`, `reject_reason_description`, `carton_barcode`, `created_on`, `created_by`, `last_modified_on`," +
					" `version`)VALUES ("
					+ newMaxId + "," + newMaxId + "," + skuId + "," + qualityParam + ", '" + itemStatus + "',"
					+ wareHouseId
					+ ", 1, 1,'RHIE161116-02',"+ poSkuId +" , NULL, NULL, 'Created for PO: RHIE161116-02', '0', 271760, NULL, NULL, '2014-04-22 17:16:38', '', '', NULL, '2014-04-18 16:02:48', 'anil.antony', '2014-05-17 21:57:23', 12)";
			DBUtilities.exUpdateQuery(insertItemQuery, "myntra_wms");
			
			insertItemQuery = "INSERT INTO `item_info` (`item_id`, `item_action_status`, `task_id`, `order_barcode`, `created_on`, `created_by`, `last_modified_on`, `version`, `order_id`, `invoice_sku_id`, `agreement_type`, `buyer_id`)"
					+ "VALUES(" + newMaxId +", 'NEW', NULL, NULL, now(), 'erpMessageQueue', now(), 0, NULL, NULL, NULL, " + buyerId + ")";
			DBUtilities.exUpdateQuery(insertItemQuery, "myntra_wms");
			
			items.add((newMaxId + "").trim());
			
			--quantity;
		}
		return items;
	}

	/** The method simulated SKU association and STN approval process and verifies the response of each of the transition phase
	 * @param stnId
	 * @param countofItemsIntentedTobeMovedForTheTestSku
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static void approveStnPostTestSkuAssociationAndVerifySuccess(long stnId, int countofItemsIntentedTobeMovedForTheTestSku)
			throws UnsupportedEncodingException, JAXBException {
		StnSkuResponse skuStnAssociationResponse=StockTransferUtils.associateSkuToStockTransferRequest(stnId, StnTestConstants.TEST_SKU_ID,
				countofItemsIntentedTobeMovedForTheTestSku);
		Assert.assertTrue(null!=skuStnAssociationResponse.getData());
		Assert.assertTrue(skuStnAssociationResponse.getData().get(0).getSku().getId().intValue()==StnTestConstants.TEST_SKU_ID);
		Assert.assertTrue(skuStnAssociationResponse.getData().get(0).getQty().intValue()==countofItemsIntentedTobeMovedForTheTestSku);
		Assert.assertTrue(skuStnAssociationResponse.getData().get(0).getStn().getStnStatus().toString().equals(StnTestConstants.STATE_CREATED));
		StnResponse stnReadyStateTransitionResponse=StockTransferUtils.moveStockTransferToReadyState(stnId);
		Assert.assertTrue(null!=stnReadyStateTransitionResponse.getData());
		Assert.assertTrue(stnReadyStateTransitionResponse.getData().get(0).getStnStatus().toString().equals(StnTestConstants.STATE_READY));
		Assert.assertTrue(stnReadyStateTransitionResponse.getData().get(0).getRemarks().toString().equals(StnTestConstants.REMARKS_TO_MOVE_STN_TO_PICKUP_STATE),"The remarkes added to approval process did not get updated");
		HashMap<Integer, Integer> initialSkuBlockedPorcessingCountMap=	getSkuAndBlockedProcessingCountQuanityAssociatedWithStn(stnId);
		StnResponse stnUpdateEventResponse=StockTransferUtils.approveStockTransferNote(stnId);
		Assert.assertTrue(stnUpdateEventResponse.getData().get(0).getStnStatus().toString().equals(StnTestConstants.STATE_APPROVED), "The stn status is not approved");
		Assert.assertTrue(stnUpdateEventResponse.getData().get(0).getRemarks().toString().equals(StnTestConstants.REMARK_TOS_END_STN_FOR_APPROVAL),"The remarkes added to approval process did not get updated");
		Assert.assertTrue(null!=stnUpdateEventResponse.getData().get(0).getApprovalDate(),"The approval date is null even if the STN is approved");
		HashMap<Integer, Integer> skuBlockedPorcessingCountMapPostApproval=	getSkuAndBlockedProcessingCountQuanityAssociatedWithStn(stnId);
		verifyIfImsgetsUpdatedPostApproval(initialSkuBlockedPorcessingCountMap, skuBlockedPorcessingCountMapPostApproval, countofItemsIntentedTobeMovedForTheTestSku);
	}

	private static void verifyIfImsgetsUpdatedPostApproval(HashMap<Integer, Integer> initialSkuBlockedPorcessingCountMap,HashMap<Integer, Integer> skuBlockedPorcessingCountMapPostApproval,int countofItemsIntentedTobeMovedForTheTestSku ){
	for (Integer skuId : skuBlockedPorcessingCountMapPostApproval.keySet()) {
		
		Assert.assertTrue(skuBlockedPorcessingCountMapPostApproval.get(skuId)==initialSkuBlockedPorcessingCountMap.get(skuId)+countofItemsIntentedTobeMovedForTheTestSku,"The blocked processing count did not increment by "+countofItemsIntentedTobeMovedForTheTestSku);
		
	}
		
		
	}
	/**The method tries to the STN into pick_initiated state and returns the response
	 * @param stnId
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static StnResponse initiatePickUpForStockTransferNote(long stnId)
			throws UnsupportedEncodingException, JAXBException {
		StnEntry stnEntryToSendForPickUp = new StnEntry();
		stnEntryToSendForPickUp.setId(stnId);
		STNStatus stnStatusToMarkForApprovalProcess = STNStatus.PICK_INITIATED;
		stnEntryToSendForPickUp.setRemarks(StnTestConstants.REMARKS_TO_MOVE_STN_TO_PICKUP_STATE);
		stnEntryToSendForPickUp.setStnStatus(stnStatusToMarkForApprovalProcess);
		String payload = APIUtilities.convertXMLObjectToString(stnEntryToSendForPickUp);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToChangeStateOfStockTransferNote(stnId), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, getTokenForStnOperations());
		StnResponse stnUpdateresponse = (StnResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new StnResponse());
		return stnUpdateresponse;

	}
	
	/** The method tries to put STN into pick initiates state and verifies the success
	 * The method throws assert error if the STN does not move to pick initiated state
	 * @param stnId
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static void initiatePickUpForStnAndVerifySuccess(long stnId) throws UnsupportedEncodingException, JAXBException{
		StnResponse stnUpdateresponse =initiatePickUpForStockTransferNote(stnId);
		Assert.assertTrue(null!=stnUpdateresponse.getData() && !stnUpdateresponse.getData().isEmpty(), "Either there is empty or no data in the response");
		Assert.assertTrue(stnUpdateresponse.getData().get(0).getStnStatus().toString().equals(StnTestConstants.STATE_PICKUP_INITIATED), "The STN did not move to pick up state");
		Assert.assertTrue(stnUpdateresponse.getStatus().getStatusMessage().equals(StnTestConstants.STN_UPDATED_SUCCESSFULLY_STATUS_MESSAGE), "The stn update response status message is not "+StnTestConstants.STN_UPDATED_SUCCESSFULLY_STATUS_MESSAGE);
		Assert.assertTrue(stnUpdateresponse.getStatus().getStatusCode()==StnTestConstants.STN_UPDATED_SUCCESSFULLY_STATUS_CODE, "The stn update response status code is not "+StnTestConstants.STN_UPDATED_SUCCESSFULLY_STATUS_CODE);
		Assert.assertTrue(stnUpdateresponse.getData().get(0).getRemarks().equals(StnTestConstants.REMARKS_TO_MOVE_STN_TO_PICKUP_STATE), "The STN did not move to pick up state");
	}

	/**The method tries to do a bulk upload of the item barcodes to STN passed
	 * @param itemBarcodes
	 * @param stnID
	 * @return StnResponse
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static StnResponse dispatchItemsInBulk(List<String> itemBarcodes, long stnID)
			throws UnsupportedEncodingException, JAXBException {
		StnEntry stnEntryToDispatchItems = new StnEntry();
		stnEntryToDispatchItems.setId(stnID);
		stnEntryToDispatchItems.setItemBarcodes(itemBarcodes);
		String payload = APIUtilities.convertXMLObjectToString(stnEntryToDispatchItems);
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.getUrlToDispatch(stnID),
				new String[] {}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getTokenForStnOperations());
		StnResponse stnUpdateresponse = (StnResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new StnResponse());
		
		
		return stnUpdateresponse;

	}
	/** The method tries to do a bulk upload of the item barcodes to STN passed and verifies upload
	 * @param itemBarcodes
	 * @param stnID
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static void dispatchItemsInBulkAndVerifySuccess(List<String> itemBarcodes, long stnID) throws UnsupportedEncodingException, JAXBException{
		StnResponse disptachReponse = dispatchItemsInBulk(itemBarcodes, stnID);
		Assert.assertEquals(disptachReponse.getStatus().getStatusMessage(), StnTestConstants.STN_UPDATED_SUCCESSFULLY_STATUS_MESSAGE, "The stn update response status message is not "+StnTestConstants.STN_UPDATED_SUCCESSFULLY_STATUS_MESSAGE);
	}

	/**The method tries to do an upload of the item barcode to STN passed
	 * @param itemBarcode
	 * @param stnID
	 * @return StnResponse
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static StnResponse dispatchSingleItem(String itemBarcode, long stnID)
			throws UnsupportedEncodingException, JAXBException {
		StnEntry stnEntryToDispatchItems = new StnEntry();
		stnEntryToDispatchItems.setId(stnID);
		List<String> itemsTodispatch = new ArrayList<String>();
		itemsTodispatch.add(itemBarcode);
		// Passing the item Barcodes to disptach them at once
		stnEntryToDispatchItems.setItemBarcodes(itemsTodispatch);
		// STNStatus stnStatusToMarkForApprovalProcess= STNStatus.DISPATCHED;
		// stnEntryToDispatchItems.setStnStatus(stnStatusToMarkForApprovalProcess);
		String payload = APIUtilities.convertXMLObjectToString(stnEntryToDispatchItems);
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.getUrlToDispatch(stnID),
				new String[] {}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getTokenForStnOperations());
		StnResponse stnUpdateresponse = (StnResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new StnResponse());
		return stnUpdateresponse;

	}

	/** The method tries to dispatch STN and returns the response of the attempt
	 * @param stnID
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static StnResponse dispatchStockTransferNote(long stnID) throws UnsupportedEncodingException, JAXBException {
		StnEntry stnEntryToDispatch = new StnEntry();
		stnEntryToDispatch.setId(stnID);
		stnEntryToDispatch.setRemarks(StnTestConstants.REMARK_TO_DISPATCH_STN);
		// Passing the item Barcodes to disptach them at once
		STNStatus stnDispatchStatus = STNStatus.DISPATCHED;
		stnEntryToDispatch.setStnStatus(stnDispatchStatus);
		String payload = APIUtilities.convertXMLObjectToString(stnEntryToDispatch);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToChangeStateOfStockTransferNote(stnID), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, getTokenForStnOperations());
		StnResponse stnUpdateresponse = (StnResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new StnResponse());
		return stnUpdateresponse;

	}
	
	/** The method tries to dispatch STN and returns the response of the attempt
	 * @param stnID
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static void recieveStockTransferNoteAndVerify(long stnID,int destinationWareHouseId) throws UnsupportedEncodingException, JAXBException {
		StnEntry stnEntryToDispatch = new StnEntry();
		stnEntryToDispatch.setId(stnID);
		stnEntryToDispatch.setRemarks(StnTestConstants.REMARK_TO_RECEIVE_STN);
		WarehouseEntry destinationWareHouse= new WarehouseEntry();
		destinationWareHouse.setId((long)destinationWareHouseId);
		stnEntryToDispatch.setDeliveryWareHouse(destinationWareHouse);
		
		// Passing the item Barcodes to disptach them at once
		STNStatus stnDispatchStatus = STNStatus.RECEIVED;
		stnEntryToDispatch.setStnStatus(stnDispatchStatus);
		String payload = APIUtilities.convertXMLObjectToString(stnEntryToDispatch);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToChangeStateOfStockTransferNote(stnID), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, getTokenForStnOperations());
		StnResponse stnReceivingResponse = (StnResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new StnResponse());
		Assert.assertTrue(null!=stnReceivingResponse.getData() && !stnReceivingResponse.getData().isEmpty(), "Either there is empty or no data in the response");
		Assert.assertTrue(stnReceivingResponse.getStatus().getStatusMessage().equals(StnTestConstants.STN_UPDATED_SUCCESSFULLY_STATUS_MESSAGE), "The stn update response status message is not "+StnTestConstants.STN_UPDATED_SUCCESSFULLY_STATUS_MESSAGE);
		Assert.assertTrue(stnReceivingResponse.getData().get(0).getRemarks().equals(StnTestConstants.REMARK_TO_RECEIVE_STN), "The remark sent during dispatching did not persist");
		Assert.assertTrue(stnReceivingResponse.getData().get(0).getStnStatus().toString().equals(STNStatus.RECEIVED.toString()), "The STN is not received state");
		Assert.assertTrue(null!=stnReceivingResponse.getData().get(0).getReceivedDate(), "The STN receive date is null");
	}
	
	
	
	
	/** The method tries to dispatch STN and asserts that the success response is received
	 * @param stnID
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static void disptachStnAndConfirmDisptach(long stnID) throws UnsupportedEncodingException, JAXBException{
		
		StnResponse disptachReponse =dispatchStockTransferNote(stnID);
		log.info("Dispatch response "+disptachReponse.toString());
		Assert.assertTrue(null!=disptachReponse.getData() && !disptachReponse.getData().isEmpty(), "Either there is empty or no data in the response");
		Assert.assertTrue(disptachReponse.getData().get(0).getStnStatus().toString().equals(StnTestConstants.STATE_DISPATCHED), "The STN did not move to DISPTACHED state");
		Assert.assertTrue(disptachReponse.getStatus().getStatusMessage().equals(StnTestConstants.STN_UPDATED_SUCCESSFULLY_STATUS_MESSAGE), "The stn dispatch response status message is not "+StnTestConstants.STN_UPDATED_SUCCESSFULLY_STATUS_MESSAGE);
		Assert.assertTrue(disptachReponse.getStatus().getStatusCode()==StnTestConstants.STN_UPDATED_SUCCESSFULLY_STATUS_CODE, "The stn dispatch response status code is not "+StnTestConstants.STN_UPDATED_SUCCESSFULLY_STATUS_CODE);
		Assert.assertTrue(disptachReponse.getData().get(0).getRemarks().equals(StnTestConstants.REMARK_TO_DISPATCH_STN), "The remark sent during dispatching did not persist");
		
	}

	/** The method tries to associate SKU to STN and returns the reponse of the request
	 * @param stnId
	 * @param skuId
	 * @param quantity
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static StnSkuResponse associateSkuToStockTransferRequest(long stnId, int skuId, Integer quantity)
			throws JAXBException, UnsupportedEncodingException {
		Integer wrappedQuanity = new Integer(quantity);
		// quantity= wrappedQuanity.doubleValue();
		StnSkuEntry stnSkuEntry = new StnSkuEntry();
		StnEntry stnEntry = new StnEntry();
		stnEntry.setId(stnId);
		stnSkuEntry.setStn(stnEntry);
		SkuEntry skuEntry = new SkuEntry();
		skuEntry.setId((long) skuId);
		stnSkuEntry.setSku(skuEntry);
		stnSkuEntry.setQty((double) Math.round(wrappedQuanity.doubleValue()));
		String payload = APIUtilities.convertXMLObjectToString(stnSkuEntry);
		log.info("The payLoad to create stockTransferNoteEntry is :: " + payload.toString());
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.getUrlToSkuStnMappingCall(stnId),
				new String[] {}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getTokenForStnOperations());
		StnSkuResponse stnSkuResponse = (StnSkuResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new StnSkuResponse());
		log.info("Printing stnSkuResponse below ::");
		log.info("Response ::- "+stnSkuResponse.toString());
		return stnSkuResponse;

	}
	
	

	/** The method tries to edit the STN based on the params passed and asserts that the expected behavior is received 
	 * @param createdStnID
	 * @param sourceWareHouseIdToBeSet
	 * @param destinationWareHouseIdToBeSet
	 * @param approverToBeSet
	 * @param remarksToBeSet
	 * @param newStateToBeSet
	 * @param expectedStatusMessage
	 * @param expectedStatusCode
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static void editStnPropertiesAndVerifyExpectedResponse(long createdStnID, int sourceWareHouseIdToBeSet,
			int destinationWareHouseIdToBeSet, String approverToBeSet, String remarksToBeSet, String newStateToBeSet,String expectedStatusMessage,int expectedStatusCode)
					throws JAXBException, UnsupportedEncodingException {

		StnEntry stnEntryToEdit = new StnEntry();
		stnEntryToEdit.setId(createdStnID);
		
		if (sourceWareHouseIdToBeSet > 0) {
			WarehouseEntry sourceWareHouse = new WarehouseEntry();
			sourceWareHouse.setId((long) sourceWareHouseIdToBeSet);
			stnEntryToEdit.setSourceWareHouse(sourceWareHouse);
		}
		if (null != approverToBeSet) {
			stnEntryToEdit.setApprover(approverToBeSet);
		}
		if (null != remarksToBeSet) {
			stnEntryToEdit.setRemarks(remarksToBeSet);
		}

		if (null != newStateToBeSet || !newStateToBeSet.isEmpty()) {
			STNStatus stnStatusToMarkForApprovalProcess = null;
			if (newStateToBeSet.equals(STNStatus.PICK_INITIATED.toString())) {
				stnStatusToMarkForApprovalProcess = STNStatus.PICK_INITIATED;
			} else if (newStateToBeSet.equals(STNStatus.CANCELLED.toString())) {
				stnStatusToMarkForApprovalProcess = STNStatus.CANCELLED;
			} else if (newStateToBeSet.equals(STNStatus.CANCELLED.toString())) {
				stnStatusToMarkForApprovalProcess = STNStatus.CANCELLED;
			} else if (newStateToBeSet.equals(STNStatus.CREATED.toString())) {
				stnStatusToMarkForApprovalProcess = STNStatus.CREATED;
			}
			else if (newStateToBeSet.equals(STNStatus.RECEIVED.toString())) {
				stnStatusToMarkForApprovalProcess = STNStatus.RECEIVED;
			} else if (newStateToBeSet.equals(STNStatus.READY.toString())) {
				stnStatusToMarkForApprovalProcess = STNStatus.READY;
			}
			else if (newStateToBeSet.equals(STNStatus.DISPATCHED.toString())) {
				stnStatusToMarkForApprovalProcess = STNStatus.DISPATCHED;
			}
			stnEntryToEdit.setStnStatus(stnStatusToMarkForApprovalProcess);
		}
		// send update request
		String payload = APIUtilities.convertXMLObjectToString(stnEntryToEdit);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToChangeStateOfStockTransferNote(createdStnID), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, StockTransferUtils.getTokenForStnOperations());
		StnResponse stnUpdateresponse = (StnResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new StnResponse());

		Assert.assertTrue(null!=stnUpdateresponse,"There was no response received from the server..");

		log.info("printing the updated STN response below ");
		log.info(stnUpdateresponse.toString());

		log.info("verifying that the received statusMessage is "+expectedStatusMessage);
		Assert.assertEquals(stnUpdateresponse.getStatus().getStatusMessage(), expectedStatusMessage);
		log.info("verifying that the received statusCode is "+expectedStatusCode);
		Assert.assertEquals(stnUpdateresponse.getStatus().getStatusCode(), expectedStatusCode);
	}

	/** The method tries to add SKUs in bulk to STN and returns the StnResponse
	 * @param stnId
	 * @param skuIds
	 * @param quantityPerSku
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static StnResponse addSkusInBulkToStn(long stnId, List<Integer> skuIds, int quantityPerSku)
			throws JAXBException, UnsupportedEncodingException {

		List<StnSkuEntry> stnSkuEntryList = new ArrayList<StnSkuEntry>();

		StnSkuResponse request = new StnSkuResponse();

		for (Integer skuId : skuIds) {
			StnSkuEntry stnSkuEntry = new StnSkuEntry();
			StnEntry stnEntry = new StnEntry();
			stnEntry.setId(stnId);
			Integer wrappedQuanity = new Integer(quantityPerSku);
			SkuEntry skuEntry = new SkuEntry();
			skuEntry.setId((long) skuId);
			stnSkuEntry.setSku(skuEntry);
			stnSkuEntry.setQty((double) Math.round(wrappedQuanity.doubleValue()));
			stnSkuEntry.setStn(stnEntry);
			stnSkuEntryList.add(stnSkuEntry);
		}
		request.setData(stnSkuEntryList);

		String payload = APIUtilities.convertXMLObjectToString(request);
		log.info("The payLoad to create stockTransferNoteEntry is :: " + payload.toString());
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.getUrlToAssociateSkusToStnInBulk(stnId),
				new String[] {}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getTokenForStnOperations());
		StnResponse stnResponse = (StnResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new StnResponse());
		return stnResponse;

	}

	/** The method tries to move STN to ready state from Created and returns the repsonse
	 * @param stnId
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static StnResponse moveStockTransferToReadyState(long stnId) throws JAXBException, UnsupportedEncodingException {

		StnEntry stnEntryToSendForApproval = new StnEntry();
		stnEntryToSendForApproval.setId(stnId);
		STNStatus stnStatusToMarkForApprovalProcess = STNStatus.READY;
		stnEntryToSendForApproval.setStnStatus(stnStatusToMarkForApprovalProcess);
		stnEntryToSendForApproval.setRemarks(StnTestConstants.REMARKS_TO_MOVE_STN_TO_PICKUP_STATE);
		String payload = APIUtilities.convertXMLObjectToString(stnEntryToSendForApproval);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToChangeStateOfStockTransferNote(stnId), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, getTokenForStnOperations());
		StnResponse stnUpdateresponse = (StnResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new StnResponse());
		return stnUpdateresponse;

	}


	/** The method tries to approve STN and returns the response
	 * @param stnId
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static StnResponse approveStockTransferNote(long stnId) throws JAXBException, UnsupportedEncodingException {

		StnEntry stnEntryToSendForApproval = new StnEntry();
		stnEntryToSendForApproval.setId(stnId);
		STNStatus stnStatusToMarkForApprovalProcess = STNStatus.APPROVED;
		stnEntryToSendForApproval.setStnStatus(stnStatusToMarkForApprovalProcess);
		stnEntryToSendForApproval.setRemarks(StnTestConstants.REMARK_TOS_END_STN_FOR_APPROVAL);
		String payload = APIUtilities.convertXMLObjectToString(stnEntryToSendForApproval);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToChangeStateOfStockTransferNote(stnId), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, getTokenForStnOperations());
		StnResponse stnUpdateresponse = (StnResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new StnResponse());
		return stnUpdateresponse;

	}

	
	/** The method tries to cancel STN and returns the response
	 * @param stnId
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static StnResponse rejectStockTransferNote(long stnId) throws JAXBException, UnsupportedEncodingException {

		StnEntry stnEntryToSendForApproval = new StnEntry();
		stnEntryToSendForApproval.setId(stnId);
		STNStatus stnStatusToMarkForApprovalProcess = STNStatus.CANCELLED;
		stnEntryToSendForApproval.setStnStatus(stnStatusToMarkForApprovalProcess);
		stnEntryToSendForApproval.setRemarks(StnTestConstants.REMARK_TO_SEND_STN_FOR_CANELLATION);
		String payload = APIUtilities.convertXMLObjectToString(stnEntryToSendForApproval);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.getUrlToChangeStateOfStockTransferNote(stnId), new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, getTokenForStnOperations());
		StnResponse stnUpdateresponse = (StnResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new StnResponse());
		return stnUpdateresponse;

	}
	

	
	/**The method tries to reject STN and verify rejection
	 * @param stnId
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static void rejectStnAndVerifyRejection(long stnId) throws UnsupportedEncodingException, JAXBException{
		StnResponse	stnUpdateEventResponse=rejectStockTransferNote(stnId);
		Assert.assertTrue(stnUpdateEventResponse.getData().get(0).getStnStatus().toString().equals(StnTestConstants.STATE_CANCELLED),"The stn status is not cancelled");
		Assert.assertTrue(stnUpdateEventResponse.getData().get(0).getRemarks().toString().equals(StnTestConstants.REMARK_TO_SEND_STN_FOR_CANELLATION),"The remarkes added to rejection process did not get updated");
	}
	

	/** The method returns the sku and quanity associated with STN
	 * @param stnId
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static HashMap<Integer, Integer> getSkuAndBlockedProcessingCountQuanityAssociatedWithStn(long stnId)
			throws JAXBException, UnsupportedEncodingException {
		HashMap<Integer, Integer> skuQuantityMap = null;
		StnEntry stnEntry = new StnEntry();
		stnEntry.setId(stnId);
		String payload = APIUtilities.convertXMLObjectToString(stnEntry);
		log.info("The payLoad to create stockTransferNoteEntry is :: " + payload.toString());
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.getUrlToSkuStnMappingCall(stnId),
				new String[] {}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, payload, getTokenForStnOperations());
		StnSkuResponse stnSkuResponse = (StnSkuResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new StnSkuResponse());

		List<StnSkuEntry> stnSkuEntries = stnSkuResponse.getData();
		Assert.assertTrue(!stnSkuEntries.isEmpty(), "There we no Sku's associated with the Stn");
		skuQuantityMap = new HashMap<Integer, Integer>();
		for (StnSkuEntry stnSkuEntry : stnSkuEntries) {
			int blockedProcessingCount=	getBlockedProcessingCountForSku(stnSkuEntry.getSku().getId().intValue(),stnSkuEntry.getStn().getSourceWareHouse().getId().toString(),stnSkuEntry.getStn().getQuality().toString());
			skuQuantityMap.put(stnSkuEntry.getSku().getId().intValue(),blockedProcessingCount);
		}
		return skuQuantityMap;
	}
	
static int	getBlockedProcessingCountForSku(int skuId,String wareHouseId,String quality){
	quality="'"+quality+"'";
	return (Integer) DBUtilities
	.exSelectQueryForSingleRecord("select  blocked_processing_count from core_inventory_counts where sku_id = " + skuId + " and quality = "+quality+ " and warehouse_id = "+wareHouseId,
			"myntra_ims").get("blocked_processing_count");
		
	}
	
	/** The method returns the sku and quanity associated with STN
	 * @param stnId
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static HashMap<Integer, Integer> getSkuAndQuanityAssociatedWithStn(long stnId)
			throws JAXBException, UnsupportedEncodingException {
		HashMap<Integer, Integer> skuQuantityMap = null;
		StnEntry stnEntry = new StnEntry();
		stnEntry.setId(stnId);
		String payload = APIUtilities.convertXMLObjectToString(stnEntry);
		log.info("The payLoad to create stockTransferNoteEntry is :: " + payload.toString());
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.getUrlToSkuStnMappingCall(stnId),
                                                             new String[] {}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, payload, getTokenForStnOperations());
		StnSkuResponse stnSkuResponse = (StnSkuResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new StnSkuResponse());

		List<StnSkuEntry> stnSkuEntries = stnSkuResponse.getData();
		Assert.assertTrue(!stnSkuEntries.isEmpty(), "There we no Sku's associated with the Stn");
		skuQuantityMap = new HashMap<Integer, Integer>();
		for (StnSkuEntry stnSkuEntry : stnSkuEntries) {
			skuQuantityMap.put(stnSkuEntry.getSku().getId().intValue(), stnSkuEntry.getQty().intValue());
		}
		return skuQuantityMap;
	}

	/** The method tries to dispatch the items sequentially
	 * @param stnId
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static void dispatchItemsSequentially(List<String> itemsToAssociateSequentially, long stnId)
			throws UnsupportedEncodingException, JAXBException {
		for (String itemBarcode : itemsToAssociateSequentially) {
			dispatchSingleItem(itemBarcode, stnId);
		}

	}
	
	public static void updateInventory(int skuId, int warehouseId, int additionalInventoryCount){
		updateAtp(skuId, additionalInventoryCount, warehouseId);
		imsServiceHelper.updateInventoryForSeller(skuId, warehouseId, additionalInventoryCount, vectorSellerID);
	}
	
	public static void updateAtp(int skuId, int additionalInventoryCount, int warehouseId) {

		Map<String, Object> data =  DBUtilities
				.exSelectQueryForSingleRecord("select id,inventory_count, blocked_order_count, blocked_future_count from inventory where sku_id=" + skuId + " and available_in_warehouses like '%"+ warehouseId + "%' and enabled = 1",
						"myntra_atp");

		int inventory_count = Math.abs((int)data.get("inventory_count"));
		int blocked_order_count = Math.abs((int) data.get("blocked_order_count"));
		int blocked_future_count = Math.abs((int) data.get("blocked_future_count"));
		int count = inventory_count + blocked_future_count + blocked_order_count + additionalInventoryCount;
		long id = (long) data.get("id");
		
		DBUtilities.exUpdateQuery(
				"UPDATE `inventory` SET `inventory_count` = " + count + " where id = " + id,
				"myntra_atp");

	}

}

