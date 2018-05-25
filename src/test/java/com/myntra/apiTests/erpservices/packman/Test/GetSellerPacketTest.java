package com.myntra.apiTests.erpservices.packman.Test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.lordoftherings.Initialize;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.packman.entry.SellerPacketItemEntry;
import com.myntra.packman.response.SellerPacketResponse;
import com.myntra.test.commons.testbase.BaseTest;

public class GetSellerPacketTest extends BaseTest{
	static Initialize init = new Initialize("/Data/configuration");
	private String login = "preordertest@gmail.com";
	private String password = "123456";
	private static Long vectorSellerID;
	SoftAssert sft=null;

	SilkRouteServiceHelper silkRouteServiceHelper = new SilkRouteServiceHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	WMSServiceHelper wmsServiceHelper=new WMSServiceHelper();
	PackmanServiceHelper packmanServiceHelper=new PackmanServiceHelper();
	PackmanDBHelper packmanDbHelper=new PackmanDBHelper();
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, UnsupportedEncodingException, JAXBException {
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
		vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Get Seller Packet by Seller Packet ID")
	public void getSellerPacketByIdForSingleReleaseTestCase() throws Exception{
		long sellerPacketId=packmanDbHelper.getSellerPacketId(EnumSCM.STORE_ID_MYNTRA,EnumSCM.SELLER_PACKET_STATUS_Q);
		if(sellerPacketId==0){
			String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":1"};
			OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);
			SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry, EnumSCM.STORE_ID_MYNTRA, OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
			List<Map<String,Object>> sellerPacketDbData=packmanDbHelper.getPacketItemDataBySellerPacket(sellerPacketResponse.getData().get(0).getId());
			List<SellerPacketItemEntry> sellerPacketItemEntry=sellerPacketResponse.getData().get(0).getPacketItems();
			packmanServiceHelper.validateDbResponse(sellerPacketItemEntry,sellerPacketDbData);
			packmanServiceHelper.validateSellerPacetRespoonse(sellerPacketResponse,orderEntry,EnumSCM.STORE_ID_MYNTRA,false);
			sellerPacketId=sellerPacketResponse.getData().get(0).getId();
		}else{
			SellerPacketResponse sellerPacketResponseByPacketId=packmanServiceHelper.getSellerPacketByPacketId(sellerPacketId);
			List<Map<String,Object>> sellerPacketDbData=packmanDbHelper.getPacketItemDataBySellerPacket(sellerPacketId);
			OrderReleaseEntry orderReleaseEntry=omsServiceHelper.getOrderReleaseEntry(String.valueOf(sellerPacketDbData.get(0).get("portal_order_release_id")));
			Long orderId=orderReleaseEntry.getOrderId();
			OrderEntry orderEntry = omsServiceHelper.getOrderEntry(String.valueOf(orderId));
			packmanServiceHelper.validateSellerPacetRespoonse(sellerPacketResponseByPacketId,orderEntry,EnumSCM.STORE_ID_MYNTRA,false);
			packmanServiceHelper.validateDbResponse(sellerPacketResponseByPacketId.getData().get(0).getPacketItems(),sellerPacketDbData);
		}
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Get seller packet by seller packet Id for already packed item")
	public void getSellerPacketByIdForAlreadyPackedItem() throws Exception{
		String itemBarcode=packmanDbHelper.getPacketItemBarcode(EnumSCM.SELLER_PACKET_ITEM_STATUS_PK,EnumSCM.STORE_ID_MYNTRA);
		SellerPacketResponse markItemPacked=null;
		if(itemBarcode==null){
			String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":1"};
			OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);
			SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry, EnumSCM.STORE_ID_MYNTRA, OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
			markItemPacked=(SellerPacketResponse) packmanServiceHelper.createSellerPacketInDesiredState(sellerPacketResponse,EnumSCM.SELLER_PACKET_ITEM_STATUS_PK);
			itemBarcode=markItemPacked.getData().get(0).getPacketItems().get(0).getItemBarcode();
		}
		SellerPacketResponse sellerPacketResponseByPacketId=packmanServiceHelper.getSellerPacketByItemBarcode(itemBarcode);
		System.out.println(sellerPacketResponseByPacketId);
		sft = new SoftAssert();
		sft.assertEquals(sellerPacketResponseByPacketId.getStatus().getStatusType(), Type.ERROR,"Issue in Seller Packet API");
		sft.assertEquals(sellerPacketResponseByPacketId.getStatus().getStatusMessage(), "No active item found for barcode(s) "+itemBarcode);
		sft.assertAll();
	}
	
	@Test(enabled = false,groups={"regression","Packman"},description="Get seller packet by seller packet Id for already cancelled item")
	public void getSellerPacketByIdForAlreadyCancelledItem() throws Exception{
		String itemBarcode=packmanDbHelper.getPacketItemBarcode(EnumSCM.SELLER_PACKET_ITEM_STATUS_IC,EnumSCM.STORE_ID_MYNTRA);
		if(itemBarcode==null||itemBarcode.isEmpty()){
			AssertJUnit.fail();
		}else{
			SellerPacketResponse sellerPacketResponseByPacketId=packmanServiceHelper.getSellerPacketByItemBarcode(itemBarcode);
			sft = new SoftAssert();
			sft.assertEquals(sellerPacketResponseByPacketId.getStatus().getStatusType(), Type.ERROR,"Issue in Seller Packet API");
			sft.assertEquals(sellerPacketResponseByPacketId.getStatus().getStatusMessage(), "No active item found for barcode(s) ");
			sft.assertAll();
		}
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Get seller packet by Id for invalid seller packet")
	public void invalidSellerPacketById() throws Exception{
		long sellerPacketId=Long.valueOf(LMSUtils.randomGenn(6));
		SellerPacketResponse sellerPacketResponseByPacketId=packmanServiceHelper.getSellerPacketByPacketId(sellerPacketId);
		sft = new SoftAssert();
		sft.assertEquals(sellerPacketResponseByPacketId.getStatus().getStatusType(), Type.SUCCESS,"Issue in Seller Packet API");
		sft.assertEquals(sellerPacketResponseByPacketId.getStatus().getStatusMessage(), "No results found","Issue in Seller Packet API");
		sft.assertAll();
	}

	@Test(enabled = true,groups={"regression","Packman"},description="Get seller packet by Item Barcode")
	public void getSellerPacketByItemBarcode() throws Exception{
		String item_barcode=packmanDbHelper.getPacketItemBarcode(EnumSCM.SELLER_PACKET_ITEM_STATUS_A,EnumSCM.STORE_ID_MYNTRA);
		SellerPacketResponse sellerPacketResponseByPacketId=packmanServiceHelper.getSellerPacketByItemBarcode(item_barcode);
		List<Map<String,Object>> getBarcodeSpecificData=packmanDbHelper.getPacketItemDataByItemBarcode(item_barcode);
		List<Map<String,Object>> sellerPacketDbData=packmanDbHelper.getPacketItemDataBySellerPacket((Long)getBarcodeSpecificData.get(0).get("seller_packet_id_fk"));
		OrderReleaseEntry orderReleaseEntry=omsServiceHelper.getOrderReleaseEntry(String.valueOf(sellerPacketDbData.get(0).get("portal_order_release_id")));
		Long orderId=orderReleaseEntry.getOrderId();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(String.valueOf(orderId));
		packmanServiceHelper.validateSellerPacetRespoonse(sellerPacketResponseByPacketId,orderEntry,EnumSCM.STORE_ID_MYNTRA,false);
		packmanServiceHelper.validateDbResponse(sellerPacketResponseByPacketId.getData().get(0).getPacketItems(),sellerPacketDbData);
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Get seller packet by ItemBarcode for invalid seller packet")
	public void invalidSellerItemBarcode() throws Exception{
		String item_barcode=LMSUtils.randomGenn(9);
		SellerPacketResponse sellerPacketResponseByPacketId=packmanServiceHelper.getSellerPacketByItemBarcode(item_barcode);
		sft = new SoftAssert();
		sft.assertEquals(sellerPacketResponseByPacketId.getStatus().getStatusType(), Type.ERROR,"Issue in Seller Packet API");
		sft.assertEquals(sellerPacketResponseByPacketId.getStatus().getStatusMessage(), "No active item found for barcode(s) "+item_barcode,"Issue in Seller Packet API");
		sft.assertAll();
	}
	
}
