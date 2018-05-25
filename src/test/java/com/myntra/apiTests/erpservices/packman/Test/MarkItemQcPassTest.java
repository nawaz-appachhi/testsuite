package com.myntra.apiTests.erpservices.packman.Test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LMSUtils;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants;
import com.myntra.apiTests.erpservices.packman.PackmanDBHelper;
import com.myntra.apiTests.erpservices.packman.PackmanServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteServiceHelper;
import com.myntra.apiTests.erpservices.silkroute.dp.SilkRouteServiceDP;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.packman.entry.SellerPacketItemEntry;
import com.myntra.packman.response.SellerPacketItemResponse;
import com.myntra.packman.response.SellerPacketResponse;
import com.myntra.silkroute.client.response.jabong.BaseSilkrouteRespForJabong;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;

public class MarkItemQcPassTest extends BaseTest{
	static Initialize init = new Initialize("/Data/configuration");
	private String login = "preordertest@gmail.com";
	private String password = "123456";
	private static Long vectorSellerID;
	SoftAssert sft=null;
	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	WMSServiceHelper wmsServiceHelper=new WMSServiceHelper();
	SilkRouteServiceHelper silkRouteServiceHelper = new SilkRouteServiceHelper();
	PackmanServiceHelper packmanServiceHelper=new PackmanServiceHelper();
	PackmanDBHelper packmanDbHelper=new PackmanDBHelper();
	SellerPacketItemResponse markItemQcPass=null;
	Long sellerPacketId=0L;
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, UnsupportedEncodingException, JAXBException {
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
		vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
	}
	public void assertMarkItemQcPass(SellerPacketItemResponse markItemQcPassResponse,String packagingType){
		sft = new SoftAssert();
		StatusResponse statusResponse=markItemQcPassResponse.getStatus();
		sft.assertEquals(statusResponse.getStatusType(), Type.SUCCESS,"Status type does not matched");
		for(int i=0;i<markItemQcPassResponse.getData().size();i++){
			SellerPacketItemEntry sellerPacketItemEntry=markItemQcPassResponse.getData().get(i);
			sft.assertEquals(sellerPacketItemEntry.getStatus(),EnumSCM.SELLER_PACKET_ITEM_STATUS_QD,"Status does not matched. It should be PK");
		}
		sft.assertAll();
	}
	public void assertNegativeTestCase(SellerPacketItemResponse markItemQcPassResponse,int statusCode,String message){
		sft = new SoftAssert();
		StatusResponse statusResponse=markItemQcPassResponse.getStatus();
		sft.assertEquals(statusResponse.getStatusCode(), statusCode,"Status code does not matched");
		sft.assertEquals(statusResponse.getStatusType(), Type.ERROR,"Status type does not matched");
		sft.assertEquals(statusResponse.getStatusMessage(), message,"Status Message does not matched");
		sft.assertAll();
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Get seller packet by seller packet Id for already packed item")
	public void getSellerPacketByIdForAlreadyPackedItem() throws Exception{
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":1"};
		OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry, EnumSCM.STORE_ID_MYNTRA, OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
		markItemQcPass=(SellerPacketItemResponse) packmanServiceHelper.createSellerPacketInDesiredState(sellerPacketResponse,EnumSCM.SELLER_PACKET_ITEM_STATUS_QD);
		assertMarkItemQcPass(markItemQcPass,EnumSCM.SELLER_PACKET_ITEM_STATUS_QD);
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Create Seller Packet for Multiple Release")
	public void multiItemReleaseTest() throws Exception{
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":2"};
		OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry,EnumSCM.STORE_ID_MYNTRA,OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
		List<Map<String,Object>> sellerPacketDbData=packmanDbHelper.getPacketItemDataBySellerPacket(sellerPacketResponse.getData().get(0).getId());
		packmanServiceHelper.validateSellerPacetRespoonse(sellerPacketResponse,orderEntry,EnumSCM.STORE_ID_MYNTRA,false);
		List<SellerPacketItemEntry> sellerPacketItemEntry=sellerPacketResponse.getData().get(0).getPacketItems();
		packmanServiceHelper.validateDbResponse(sellerPacketItemEntry,sellerPacketDbData);
		
		markItemQcPass=(SellerPacketItemResponse) packmanServiceHelper.createSellerPacketInDesiredState(sellerPacketResponse,EnumSCM.SELLER_PACKET_ITEM_STATUS_QD);
		assertMarkItemQcPass(markItemQcPass,EnumSCM.SELLER_PACKET_ITEM_STATUS_QD);
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Create Seller Packet for Multiple Release")
	public void byPassingInvalidSellerPacketId() throws Exception{
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":2"};
		OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry,EnumSCM.STORE_ID_MYNTRA,OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
		List<Map<String,Object>> sellerPacketDbData=packmanDbHelper.getPacketItemDataBySellerPacket(sellerPacketResponse.getData().get(0).getId());
		packmanServiceHelper.validateSellerPacetRespoonse(sellerPacketResponse,orderEntry,EnumSCM.STORE_ID_MYNTRA,false);
		List<SellerPacketItemEntry> sellerPacketItemEntry=sellerPacketResponse.getData().get(0).getPacketItems();
		packmanServiceHelper.validateDbResponse(sellerPacketItemEntry,sellerPacketDbData);
		String itemBarcodes[]=packmanServiceHelper.getItemBarcode(sellerPacketItemEntry);
		
		markItemQcPass=packmanServiceHelper.markItemQcPass(232112L, itemBarcodes);
		List<Map<String,Object>> getBarcodeSpecificData=packmanDbHelper.getPacketItemDataByItemBarcode(itemBarcodes[0]);
		Long sellerPacketId=(Long)getBarcodeSpecificData.get(0).get("seller_packet_id_fk");
		String errorMessage="Expected sellerPacketId : "+sellerPacketId+" but actual 232112";
		assertNegativeTestCase(markItemQcPass,1007,errorMessage);
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Mark Release premium packed for packed item")
	public void tryToQcPassAlreadyPackedItem() throws Exception{
		String itemBarcode=packmanDbHelper.getPacketItemBarcode(EnumSCM.SELLER_PACKET_ITEM_STATUS_PK,EnumSCM.STORE_ID_MYNTRA);
		String[] itemBarcodes={itemBarcode};
		if(itemBarcode==null){
			String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":1"};
			OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);
			SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry, EnumSCM.STORE_ID_MYNTRA, OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
			packmanServiceHelper.createSellerPacketInDesiredState(sellerPacketResponse,EnumSCM.SELLER_PACKET_ITEM_STATUS_PK);
			sellerPacketId=sellerPacketResponse.getData().get(0).getId();
			itemBarcodes=packmanServiceHelper.getItemBarcode(sellerPacketResponse.getData().get(0).getPacketItems());
		}
		markItemQcPass=packmanServiceHelper.markItemQcPass(sellerPacketId, itemBarcodes);
		String errorMessage="No active item found for barcode(s) "+itemBarcodes[0];
		assertNegativeTestCase(markItemQcPass,1033,errorMessage);
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Create Seller Packet for Single Item Release for Flipkart Store",dataProvider = "singleItemReleaseTestForFlipkart", dataProviderClass = SilkRouteServiceDP.class)
	public void markItemQcPassForFlipkartItem(String environment, String OrderReleaseId, String OrderEventType,
			String status, String Hold,
			String DispatchDate, String DispatchAfter, String quantity,
			String cancelledQuantity, String ListingID, String SKU_barcode,
			String price, String pincode, String warehouse,
			String store_id) throws Exception{
		Svc createOrderReqGen = silkRouteServiceHelper.invokeSilkRouteServiceCreateOrderAPI(environment, OrderReleaseId, OrderEventType, status, Hold, DispatchDate, DispatchAfter, quantity,
				cancelledQuantity, ListingID, SKU_barcode, price, pincode);
		String createOrderResponse = createOrderReqGen.getResponseBody();
 		String orderid = APIUtilities.getElement(createOrderResponse, "orderItemId", "json");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(orderid);
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry,EnumSCM.STORE_ID_FKART2,OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
		packmanServiceHelper.validateSellerPacetRespoonse(sellerPacketResponse,orderEntry,EnumSCM.STORE_ID_FKART2,false);
		
		sellerPacketId=sellerPacketResponse.getData().get(0).getId();
		String[] itemBarcodes=packmanServiceHelper.getItemBarcode(sellerPacketResponse.getData().get(0).getPacketItems());
		markItemQcPass=packmanServiceHelper.markItemQcPass(sellerPacketId, itemBarcodes);
		assertMarkItemQcPass(markItemQcPass,EnumSCM.SELLER_PACKET_ITEM_STATUS_QD);
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Create Seller Packet for Single Item Release",dataProvider = "singleItemReleaseTestForJabong", dataProviderClass = SilkRouteServiceDP.class)
	public void markItemQcPassForJabongItem(String[] attributeList, double totalAmount, String shippingPincode, String processingStartDate, String processingEndDate, String type, String errorMessage) throws Exception{
		Svc svc = silkRouteServiceHelper.createOrderJabong(attributeList, totalAmount, shippingPincode, processingStartDate, processingEndDate, type);
		BaseSilkrouteRespForJabong baseSilkrouteRespForJabong = (BaseSilkrouteRespForJabong) APIUtilities.getJsontoObject(svc.getResponseBody(), new BaseSilkrouteRespForJabong());
		Assert.assertEquals(baseSilkrouteRespForJabong.getErrMsg(), errorMessage, "Error Message : ");
		String statusco = attributeList[0];
		String[] status = statusco.split(":");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID("JB"+status[0]);
		String itemBarcodes[]=new String[orderEntry.getOrderReleases().size()+1];
		for(int i=0;i<2;i++){
			itemBarcodes[i]=LMSUtils.randomGenn(6);
		}
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.createSellerPacket(orderEntry,EnumSCM.STORE_ID_JABONG,OMSTCConstants.WareHouseIds.warehouseId36_BW,itemBarcodes,vectorSellerID);
		packmanServiceHelper.validateSellerPacetRespoonse(sellerPacketResponse,orderEntry,EnumSCM.STORE_ID_JABONG,false);
		
		sellerPacketId=sellerPacketResponse.getData().get(0).getId();
		markItemQcPass=packmanServiceHelper.markItemQcPass(sellerPacketId, itemBarcodes);
		assertMarkItemQcPass(markItemQcPass,EnumSCM.SELLER_PACKET_ITEM_STATUS_QD);
	}
}
