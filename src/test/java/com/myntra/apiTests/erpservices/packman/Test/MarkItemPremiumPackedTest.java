package com.myntra.apiTests.erpservices.packman.Test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.xml.bind.JAXBException;

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
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.lordoftherings.Initialize;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.packman.entry.SellerPacketItemEntry;
import com.myntra.packman.response.SellerPacketItemResponse;
import com.myntra.packman.response.SellerPacketResponse;
import com.myntra.test.commons.testbase.BaseTest;

public class MarkItemPremiumPackedTest extends BaseTest{
	static Initialize init = new Initialize("/Data/configuration");
	private String login = "preordertest@gmail.com";
	private String password = "123456";
	private static Long vectorSellerID;
	SoftAssert sft=null;
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	WMSServiceHelper wmsServiceHelper=new WMSServiceHelper();
	PackmanServiceHelper packmanServiceHelper=new PackmanServiceHelper();
	PackmanDBHelper packmanDbHelper=new PackmanDBHelper();
	private String orderReleaseId;
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, UnsupportedEncodingException, JAXBException {
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
		vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
	}

	@Test(enabled = true,groups={"regression","Packman"},description="Mark Release premium packed")
	public void markReleasePremiumPackedWithoutQcPass() throws Exception{
		String item_barcode=(String)packmanDbHelper.getPackagedItem("item_barcode",false,EnumSCM.ADDITIONAL_INFO_PACKAGING_VALUE_PREMIUM,EnumSCM.STORE_ID_MYNTRA);
		if(item_barcode==null||item_barcode.isEmpty()){
			String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":1"};
			OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);
			orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
			List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
			String lineID = ""+orderLineEntries.get(0).getId();
			omsServiceHelper.updateOrderLineAdditionInfo(EnumSCM.ADDITIONAL_INFO_PACKAGING_TYPE, EnumSCM.ADDITIONAL_INFO_PACKAGING_VALUE_PREMIUM, lineID);

			SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry,EnumSCM.STORE_ID_MYNTRA,OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
			List<SellerPacketItemEntry> sellerPacketItemEntry=sellerPacketResponse.getData().get(0).getPacketItems();
			item_barcode=sellerPacketItemEntry.get(0).getItemBarcode();
		}
		SellerPacketItemResponse sellerPacketItemResponseAfterPremiumPacked = packmanServiceHelper.markItemPremiumPacked(item_barcode);
		StatusResponse statusResponse=sellerPacketItemResponseAfterPremiumPacked.getStatus();
		sft = new SoftAssert();
		sft.assertEquals(statusResponse.getStatusType(), Type.ERROR,"Status code does not matched");
		sft.assertEquals(statusResponse.getStatusMessage(), "Incorrect status of operating entry","Status message does not matched");
		sft.assertAll();
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Mark Release premium packed")
	public void markReleasePremiumPackedWithQcPass() throws Exception{
		String itemBarcode=(String)packmanDbHelper.getPackagedItem("item_barcode",true,EnumSCM.ADDITIONAL_INFO_PACKAGING_VALUE_PREMIUM,EnumSCM.STORE_ID_MYNTRA);
		SellerPacketItemResponse markItemQcPass=null;
		if(itemBarcode==null||itemBarcode.isEmpty()){
			String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":1"};
			OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);

			orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
			List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
			String lineID = ""+orderLineEntries.get(0).getId();
			omsServiceHelper.updateOrderLineAdditionInfo(EnumSCM.ADDITIONAL_INFO_PACKAGING_TYPE, EnumSCM.ADDITIONAL_INFO_PACKAGING_VALUE_PREMIUM, lineID);
				
		
			SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry,EnumSCM.STORE_ID_MYNTRA,OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
			markItemQcPass=(SellerPacketItemResponse) packmanServiceHelper.createSellerPacketInDesiredState(sellerPacketResponse,EnumSCM.SELLER_PACKET_ITEM_STATUS_QD);
			itemBarcode=markItemQcPass.getData().get(0).getItemBarcode();
		}
		SellerPacketItemResponse sellerPacketItemResponseAfterPremiumPacked = packmanServiceHelper.markItemPremiumPacked(itemBarcode);
		SellerPacketItemEntry sellerPacketItemEntry=sellerPacketItemResponseAfterPremiumPacked.getData().get(0);
		sft = new SoftAssert();
		sft.assertEquals(sellerPacketItemEntry.getPackagingStatus(), "PACKAGED","Seller Packaging status should be Packaged");
		sft.assertEquals(sellerPacketItemEntry.getPackagingType(), EnumSCM.ADDITIONAL_INFO_PACKAGING_VALUE_PREMIUM,"Seller Packaging status should be Packaged");
		sft.assertAll();
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Mark Release premium packed for Normal Item Non Qc pass")
	public void markReleasePremiumPackedNormalItem() throws Exception{
		String itemBarcode=(String)packmanDbHelper.getPackagedItem("item_barcode",false,EnumSCM.ADDITIONAL_INFO_PACKAGING_VALUE_NORMAL,EnumSCM.STORE_ID_MYNTRA);
		sft = new SoftAssert();
		if(itemBarcode==null||itemBarcode.isEmpty()){
			String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":1"};
			OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);
			SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry,EnumSCM.STORE_ID_MYNTRA,OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
			itemBarcode=sellerPacketResponse.getData().get(0).getPacketItems().get(0).getItemBarcode();
		}
		SellerPacketItemResponse sellerPacketItemResponseAfterPremiumPacked = packmanServiceHelper.markItemPremiumPacked(itemBarcode);
		StatusResponse statusResponse=sellerPacketItemResponseAfterPremiumPacked.getStatus();
		sft.assertEquals(statusResponse.getStatusCode(), 71,"Status code does not matched");
		sft.assertEquals(statusResponse.getStatusType(), Type.ERROR,"Status type does not matched");
		sft.assertEquals(statusResponse.getStatusMessage(), "This operation is not supported in this context.","Status code does not matched");
		sft.assertAll();
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Mark Release premium packed for Normal Item Non Qc pass")
	public void markReleasePremiumPackedNormalItemWithQcPass() throws Exception{
		String itemBarcode=(String)packmanDbHelper.getPackagedItem("item_barcode",true,EnumSCM.ADDITIONAL_INFO_PACKAGING_VALUE_NORMAL,EnumSCM.STORE_ID_MYNTRA);
		SellerPacketItemResponse markItemQcPass=null;
		sft = new SoftAssert();
		if(itemBarcode==null||itemBarcode.isEmpty()){
			String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":1"};
			OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);
			SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry,EnumSCM.STORE_ID_MYNTRA,OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
			markItemQcPass=(SellerPacketItemResponse) packmanServiceHelper.createSellerPacketInDesiredState(sellerPacketResponse,EnumSCM.SELLER_PACKET_ITEM_STATUS_QD);
			itemBarcode=markItemQcPass.getData().get(0).getItemBarcode();
		}
		SellerPacketItemResponse sellerPacketItemResponseAfterPremiumPacked = packmanServiceHelper.markItemPremiumPacked(itemBarcode);
		StatusResponse statusResponse=sellerPacketItemResponseAfterPremiumPacked.getStatus();
		sft.assertEquals(statusResponse.getStatusCode(), 71,"Status code does not matched");
		sft.assertEquals(statusResponse.getStatusType(), Type.ERROR,"Status type does not matched");
		sft.assertEquals(statusResponse.getStatusMessage(), "This operation is not supported in this context.");
		sft.assertAll();
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Mark Release premium packed")
	public void markItemPremiumPackedForNonExistingItem() throws Exception{
		sft = new SoftAssert();
		String itemBarcode=LMSUtils.randomGenn(6);
		SellerPacketItemResponse sellerPacketItemResponseAfterPremiumPacked = packmanServiceHelper.markItemPremiumPacked(itemBarcode);
		StatusResponse statusResponse=sellerPacketItemResponseAfterPremiumPacked.getStatus();
		sft.assertEquals(statusResponse.getStatusCode(), 1033,"Status code does not matched");
		sft.assertEquals(statusResponse.getStatusType(), Type.ERROR,"Status type does not matched");
		sft.assertEquals(statusResponse.getStatusMessage(), "No active item found for barcode(s) "+itemBarcode);
		sft.assertAll();
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Mark Release premium packed for packed item")
	public void markItemPremiumPackedForPackedItem() throws Exception{
		String itemBarcode=packmanDbHelper.getPacketItemBarcode(EnumSCM.SELLER_PACKET_ITEM_STATUS_PK,EnumSCM.STORE_ID_MYNTRA);
		SellerPacketResponse markItemPacked=null;
		if(itemBarcode==null){
			String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":1"};
			OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);
			SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry, EnumSCM.STORE_ID_MYNTRA, OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
			markItemPacked=(SellerPacketResponse) packmanServiceHelper.createSellerPacketInDesiredState(sellerPacketResponse,EnumSCM.SELLER_PACKET_ITEM_STATUS_PK);
			itemBarcode=markItemPacked.getData().get(0).getPacketItems().get(0).getItemBarcode();
		}
		sft = new SoftAssert();
		SellerPacketItemResponse sellerPacketItemResponseAfterPremiumPacked = packmanServiceHelper.markItemPremiumPacked(itemBarcode);
		StatusResponse statusResponse=sellerPacketItemResponseAfterPremiumPacked.getStatus();
		sft.assertEquals(statusResponse.getStatusCode(),1033,"Status code does not matched");
		sft.assertEquals(statusResponse.getStatusType(), Type.ERROR,"Status type does not matched");
		sft.assertEquals(statusResponse.getStatusMessage(), "No active item found for barcode(s) "+ itemBarcode);
		sft.assertAll();
	}
	@Test(enabled = false,groups={"regression","Packman"},description="Mark Release premium packed for Cancelled Item")
	public void markItemPremiumPackedForCancelledItem() throws Exception{
		String itemBarcode=packmanDbHelper.getPacketItemBarcode(EnumSCM.SELLER_PACKET_ITEM_STATUS_IC,EnumSCM.STORE_ID_MYNTRA);
		sft = new SoftAssert();
		SellerPacketItemResponse sellerPacketItemResponseAfterPremiumPacked = packmanServiceHelper.markItemPremiumPacked(itemBarcode);
		StatusResponse statusResponse=sellerPacketItemResponseAfterPremiumPacked.getStatus();
		sft.assertEquals(statusResponse.getStatusCode(), 1033,"Status code does not matched");
		sft.assertEquals(statusResponse.getStatusType(), Type.ERROR,"Status type does not matched");
		sft.assertEquals(statusResponse.getStatusMessage(), "No active item found for barcode(s) "+itemBarcode);
		sft.assertAll();
	}
}
