package com.myntra.apiTests.erpservices.packman.Test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

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
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.packman.entry.SellerPacketItemEntry;
import com.myntra.packman.response.SellerPacketResponse;
import com.myntra.silkroute.client.response.jabong.BaseSilkrouteRespForJabong;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;

public class MarkReadyToDispatchTest extends BaseTest{
	static Initialize init = new Initialize("/Data/configuration");
	private String login = "preordertest@gmail.com";
	private String password = "123456";
	private static Long vectorSellerID;
	SoftAssert sft=null;
	End2EndHelper end2EndHelper = new End2EndHelper();
	SilkRouteServiceHelper silkRouteServiceHelper = new SilkRouteServiceHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	WMSServiceHelper wmsServiceHelper=new WMSServiceHelper();
	PackmanServiceHelper packmanServiceHelper=new PackmanServiceHelper();
	PackmanDBHelper packmanDbHelper=new PackmanDBHelper();
	private String orderReleaseId;
	private Long sellerPacketId;
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, UnsupportedEncodingException, JAXBException {
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
		vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
	}
	public void assertNegativeTestCase(SellerPacketResponse markReadyToDispatchResponse,int statusCode){
		sft = new SoftAssert();
		StatusResponse statusResponse=markReadyToDispatchResponse.getStatus();
		sft.assertEquals(statusResponse.getStatusCode(), statusCode,"Status code does not matched");
		sft.assertEquals(statusResponse.getStatusType(), Type.ERROR,"Status type does not matched");
		sft.assertAll();
	}
	public void assertReadyToDispatch(SellerPacketResponse markReadyToDispatchResponse,String packagingType){
		sft = new SoftAssert();
		StatusResponse statusResponse=markReadyToDispatchResponse.getStatus();
		sft.assertEquals(statusResponse.getStatusType(), Type.SUCCESS,"Status type does not matched");
		List<SellerPacketItemEntry> sellerPacketItemEntry=markReadyToDispatchResponse.getData().get(0).getPacketItems();
		for(int i=0;i<sellerPacketItemEntry.size();i++){
			sft.assertEquals(sellerPacketItemEntry.get(i).getStatus(),EnumSCM.SELLER_PACKET_STATUS_PK,"Status does not matched. It should be PK");
			sft.assertEquals(sellerPacketItemEntry.get(i).getPackagingType(),packagingType,"Packaging type does not matched");
		}
		sft.assertAll();
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Ready To D")
	public void markReadyToDispatchWithoutQcPassForSingleItem() throws Exception{
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":1"};
		OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry, EnumSCM.STORE_ID_MYNTRA, OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
		if(sellerPacketResponse.getStatus().getStatusType().equals(Type.SUCCESS)){
			Long sellerPacketId=sellerPacketResponse.getData().get(0).getId();
			SellerPacketResponse markReadyToDispatchResponse=packmanServiceHelper.markItemReadyToDispatch(sellerPacketId);
			assertNegativeTestCase(markReadyToDispatchResponse,1011);
		}else{
			Assert.fail("Create seller packet API failed");
		}
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Get Seller Packet by Seller Packet ID")
	public void markReadyToDispatchWithoutQcPassForMultiRelease() throws Exception{
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":2"};
		OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry,EnumSCM.STORE_ID_MYNTRA,OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
		if(sellerPacketResponse.getStatus().getStatusType().equals(Type.SUCCESS)){
			sellerPacketId = sellerPacketResponse.getData().get(0).getId();
			SellerPacketResponse markReadyToDispatchResponse=packmanServiceHelper.markItemReadyToDispatch(sellerPacketId);
			assertNegativeTestCase(markReadyToDispatchResponse,1011);
		}else{
			Assert.fail("Create seller packet API failed");
		}
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Mark Release premium packed for Normal Item Non Qc pass")
	public void readyToDispatchNormalItemWithQcPass() throws Exception{
		Object dbResponse=packmanDbHelper.getPackagedItem("seller_packet_id_fk",true,EnumSCM.ADDITIONAL_INFO_PACKAGING_VALUE_NORMAL,EnumSCM.STORE_ID_MYNTRA);
		Long sellerPacketId=dbResponse==null?0L:(Long)dbResponse;
		SellerPacketResponse markItemPacked=null;
		if(sellerPacketId==0){
			String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":1"};
			OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);
			SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry,EnumSCM.STORE_ID_MYNTRA,OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
			sellerPacketId = sellerPacketResponse.getData().get(0).getId();
			markItemPacked=(SellerPacketResponse) packmanServiceHelper.createSellerPacketInDesiredState(sellerPacketResponse,EnumSCM.SELLER_PACKET_ITEM_STATUS_PK);
			markItemPacked.getData().get(0).getPacketItems().get(0).getItemBarcode();
			assertReadyToDispatch(markItemPacked, EnumSCM.ADDITIONAL_INFO_PACKAGING_VALUE_NORMAL);
		}
		markItemPacked=packmanServiceHelper.markItemReadyToDispatch(sellerPacketId);
		assertReadyToDispatch(markItemPacked, EnumSCM.ADDITIONAL_INFO_PACKAGING_VALUE_NORMAL);
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Mark Release premium packed")
	public void readyToDispatchPremiumItemWithQcPass() throws Exception{
		Object dbResponse=packmanDbHelper.getPackagedItem("seller_packet_id_fk",true,EnumSCM.ADDITIONAL_INFO_PACKAGING_VALUE_PREMIUM,EnumSCM.STORE_ID_MYNTRA);
		Long sellerPacketId=dbResponse==null?0L:(Long)dbResponse;
		SellerPacketItemEntry markOrderPremiumPacked = null;
		if(sellerPacketId==0){
			String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":1"};
			OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);

			orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
			List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
			String lineID = ""+orderLineEntries.get(0).getId();
			omsServiceHelper.updateOrderLineAdditionInfo(EnumSCM.ADDITIONAL_INFO_PACKAGING_TYPE, EnumSCM.ADDITIONAL_INFO_PACKAGING_VALUE_PREMIUM, lineID);	
			SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry,EnumSCM.STORE_ID_MYNTRA,OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
			sellerPacketId=sellerPacketResponse.getData().get(0).getId();
			
			markOrderPremiumPacked=(SellerPacketItemEntry) packmanServiceHelper.createSellerPacketInDesiredState(sellerPacketResponse,EnumSCM.SELLER_PACKET_ITEM_STATUS_PP);
			sft = new SoftAssert();
			sft.assertEquals(markOrderPremiumPacked.getPackagingStatus(), "PACKAGED","Seller Packaging status should be Packaged");
			sft.assertEquals(markOrderPremiumPacked.getPackagingType(), EnumSCM.ADDITIONAL_INFO_PACKAGING_VALUE_PREMIUM,"Seller Packaging status should be Packaged");
			sft.assertAll();
		}
		SellerPacketResponse markReadyToDispatchResponse=packmanServiceHelper.markItemReadyToDispatch(sellerPacketId);
		assertReadyToDispatch(markReadyToDispatchResponse, EnumSCM.ADDITIONAL_INFO_PACKAGING_VALUE_PREMIUM);
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Create Seller Packet for Single Item Release for Flipkart Store",dataProvider = "singleItemReleaseTestForFlipkart", dataProviderClass = SilkRouteServiceDP.class)
	public void singleItemReleaseTestForFlipkartReadyToDispatch(String environment, String OrderReleaseId, String OrderEventType,
			String status, String Hold,
			String DispatchDate, String DispatchAfter, String quantity,
			String cancelledQuantity, String ListingID, String SKU_barcode,
			String price, String pincode, String warehouse,
			String store_id) throws Exception{
		sft = new SoftAssert();
		Svc createOrderReqGen = silkRouteServiceHelper.invokeSilkRouteServiceCreateOrderAPI(environment, OrderReleaseId, OrderEventType, status, Hold, DispatchDate, DispatchAfter, quantity,
				cancelledQuantity, ListingID, SKU_barcode, price, pincode);
		String createOrderResponse = createOrderReqGen.getResponseBody();
		String orderid = APIUtilities.getElement(createOrderResponse, "orderItemId", "json");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(orderid);
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry,EnumSCM.STORE_ID_FKART2,OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
		Long sellerPacketId=sellerPacketResponse.getData().get(0).getId();
		
		packmanServiceHelper.createSellerPacketInDesiredState(sellerPacketResponse,EnumSCM.SELLER_PACKET_ITEM_STATUS_QD);
		
		SellerPacketResponse markReadyToDispatchResponse=packmanServiceHelper.markItemReadyToDispatch(sellerPacketId);
		assertReadyToDispatch(markReadyToDispatchResponse, EnumSCM.ADDITIONAL_INFO_PACKAGING_VALUE_NORMAL);
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Create Seller Packet for Single Item Release",dataProvider = "singleItemReleaseTestForJabong", dataProviderClass = SilkRouteServiceDP.class)
	public void singleItemReleaseTestForJabongReadyToDispatch(String[] attributeList, double totalAmount, String shippingPincode, String processingStartDate, String processingEndDate, String type, String errorMessage) throws Exception{
		Svc svc = silkRouteServiceHelper.createOrderJabong(attributeList, totalAmount, shippingPincode, processingStartDate, processingEndDate, type);
		BaseSilkrouteRespForJabong baseSilkrouteRespForJabong = (BaseSilkrouteRespForJabong) APIUtilities.getJsontoObject(svc.getResponseBody(), new BaseSilkrouteRespForJabong());
		Assert.assertEquals(baseSilkrouteRespForJabong.getErrMsg(), errorMessage, "Error Message : ");
		String statusco = attributeList[0];
		String[] status = statusco.split(":");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID("JB"+status[0]);		
		String itemBarcodes[]={LMSUtils.randomGenn(6)};
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.createSellerPacket(orderEntry,EnumSCM.STORE_ID_JABONG,OMSTCConstants.WareHouseIds.warehouseId36_BW,itemBarcodes,vectorSellerID);
		packmanServiceHelper.validateSellerPacetRespoonse(sellerPacketResponse,orderEntry,EnumSCM.STORE_ID_JABONG,false);
		Long sellerPacketId=sellerPacketResponse.getData().get(0).getId();
	
		packmanServiceHelper.createSellerPacketInDesiredState(sellerPacketResponse,EnumSCM.SELLER_PACKET_ITEM_STATUS_QD);
		
		SellerPacketResponse markReadyToDispatchResponse=packmanServiceHelper.markItemReadyToDispatch(sellerPacketId);
		assertReadyToDispatch(markReadyToDispatchResponse, EnumSCM.ADDITIONAL_INFO_PACKAGING_VALUE_NORMAL);
		
		
	}
}
