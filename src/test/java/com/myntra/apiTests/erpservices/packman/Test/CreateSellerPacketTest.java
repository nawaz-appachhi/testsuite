package com.myntra.apiTests.erpservices.packman.Test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.myntra.apiTests.common.Constants.EnumSCM;
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
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.packman.entry.SellerPacketItemEntry;
import com.myntra.packman.response.SellerPacketResponse;
import com.myntra.silkroute.client.response.jabong.BaseSilkrouteRespForJabong;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;

public class CreateSellerPacketTest extends BaseTest{
	static Initialize init = new Initialize("/Data/configuration");
	private String login = "notifyme763@gmail.com";
	private String password = "myntra@123";
	private static Long vectorSellerID;
	private static Logger log = Logger.getLogger(CreateSellerPacketTest.class);    
	SilkRouteServiceHelper silkRouteServiceHelper = new SilkRouteServiceHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	WMSServiceHelper wmsServiceHelper=new WMSServiceHelper();
	PackmanServiceHelper packmanServiceHelper=new PackmanServiceHelper();
	PackmanDBHelper packmanDbHelper=new PackmanDBHelper();
	private boolean giftWrap=false;
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, UnsupportedEncodingException, JAXBException {
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
		vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
	}
	
	@Test(enabled = true,groups={"regression","Packman"},description="Create Seller Packet for Single Item Release")
	public void singleItemReleaseTest() throws Exception {
		log.debug("Vector Seller Id" + vectorSellerID);
		String skuId[] = {"3831"+":4"};
		OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,"560068", giftWrap, vectorSellerID);
		System.out.println(orderEntry.getStoreOrderId());
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry,EnumSCM.STORE_ID_MYNTRA,OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
		List<Map<String,Object>> sellerPacketDbData=packmanDbHelper.getPacketItemDataBySellerPacket(sellerPacketResponse.getData().get(0).getId());
		List<SellerPacketItemEntry> sellerPacketItemEntry=sellerPacketResponse.getData().get(0).getPacketItems();
		packmanServiceHelper.validateDbResponse(sellerPacketItemEntry,sellerPacketDbData);
		packmanServiceHelper.validateSellerPacetRespoonse(sellerPacketResponse,orderEntry,EnumSCM.STORE_ID_MYNTRA,false);
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Create Seller Packet for Single Item Release")
	public void singleItemReleaseTestForGiftWrap() throws Exception{
		giftWrap=true;
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":3"};
		OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, giftWrap, vectorSellerID);
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry,EnumSCM.STORE_ID_MYNTRA,OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
		List<Map<String,Object>> sellerPacketDbData=packmanDbHelper.getPacketItemDataBySellerPacket(sellerPacketResponse.getData().get(0).getId());
		List<SellerPacketItemEntry> sellerPacketItemEntry=sellerPacketResponse.getData().get(0).getPacketItems();
		packmanServiceHelper.validateDbResponse(sellerPacketItemEntry,sellerPacketDbData);
		packmanServiceHelper.validateSellerPacetRespoonse(sellerPacketResponse,orderEntry,EnumSCM.STORE_ID_MYNTRA,true);
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Create Seller Packet for Multiple Release")
	public void multiItemReleaseTest() throws Exception{
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":6"};
		OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, giftWrap, vectorSellerID);
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry,EnumSCM.STORE_ID_MYNTRA,OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
		List<Map<String,Object>> sellerPacketDbData=packmanDbHelper.getPacketItemDataBySellerPacket(sellerPacketResponse.getData().get(0).getId());
		packmanServiceHelper.validateSellerPacetRespoonse(sellerPacketResponse,orderEntry,EnumSCM.STORE_ID_MYNTRA,false);
		List<SellerPacketItemEntry> sellerPacketItemEntry=sellerPacketResponse.getData().get(0).getPacketItems();
		packmanServiceHelper.validateDbResponse(sellerPacketItemEntry,sellerPacketDbData);
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Create Seller Packet for Single Item Release for Flipkart Store",dataProvider = "singleItemReleaseTestForFlipkart", dataProviderClass = SilkRouteServiceDP.class)
	public void singleItemReleaseTestForFlipkart(String environment, String OrderReleaseId, String OrderEventType,
			String status, String Hold,
			String DispatchDate, String DispatchAfter, String quantity,
			String cancelledQuantity, String ListingID, String SKU_barcode,
			String price, String pincode, String warehouse,
			String store_id) throws Exception{
		Svc createOrderReqGen = silkRouteServiceHelper.invokeSilkRouteServiceCreateOrderAPI(environment, OrderReleaseId, OrderEventType, status, Hold, DispatchDate, DispatchAfter, quantity,
				cancelledQuantity, ListingID, SKU_barcode, price, pincode);
		String createOrderResponse = createOrderReqGen.getResponseBody();
		log.debug("CreateOrderResponse:-"+createOrderResponse);
		String orderid = APIUtilities.getElement(createOrderResponse, "orderItemId", "json");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(orderid);
		System.out.println(orderEntry.getStoreOrderId());
		
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry,EnumSCM.STORE_ID_FKART2,OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
		packmanServiceHelper.validateSellerPacetRespoonse(sellerPacketResponse,orderEntry,EnumSCM.STORE_ID_FKART2,false);
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Create Seller Packet for Single Item Release",dataProvider = "singleItemReleaseTestForJabong", dataProviderClass = SilkRouteServiceDP.class)
	public void singleItemReleaseTestForJabong(String[] attributeList, double totalAmount, String shippingPincode, String processingStartDate, String processingEndDate, String type, String errorMessage) throws Exception{
		Svc svc = silkRouteServiceHelper.createOrderJabong(attributeList, totalAmount, shippingPincode, processingStartDate, processingEndDate, type);
		BaseSilkrouteRespForJabong baseSilkrouteRespForJabong = (BaseSilkrouteRespForJabong) APIUtilities.getJsontoObject(svc.getResponseBody(), new BaseSilkrouteRespForJabong());
		Assert.assertEquals(baseSilkrouteRespForJabong.getErrMsg(), errorMessage, "Error Message : ");
		String statusco = attributeList[0];
		String[] status = statusco.split(":");
		System.out.println(status[0]);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID("JB"+status[0]);
		String itemBarcodes[]=new String[orderEntry.getOrderReleases().size()+1];
		for(int i=0;i<2;i++){
			itemBarcodes[i]=LMSUtils.randomGenn(6);
		}
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.createSellerPacket(orderEntry,EnumSCM.STORE_ID_JABONG,OMSTCConstants.WareHouseIds.warehouseId36_BW,itemBarcodes,vectorSellerID);
		packmanServiceHelper.validateSellerPacetRespoonse(sellerPacketResponse,orderEntry,EnumSCM.STORE_ID_JABONG,false);
	}
	
	
}
