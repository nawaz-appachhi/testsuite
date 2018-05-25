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
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants;
import com.myntra.apiTests.erpservices.packman.PackmanDBHelper;
import com.myntra.apiTests.erpservices.packman.PackmanServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteServiceHelper;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.inbound.response.FIFAStatusCodes.Type;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.packman.entry.SellerPacketItemEntry;
import com.myntra.packman.response.SellerPacketItemResponse;
import com.myntra.packman.response.SellerPacketResponse;
import com.myntra.test.commons.testbase.BaseTest;

public class CancelItemsBySellerTest extends BaseTest{
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
	private int count;
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, UnsupportedEncodingException, JAXBException {
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
		vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
	}
	public void assertMarkItemQcPass(SellerPacketItemResponse cancelItemBySeller,String packagingType){
		sft = new SoftAssert();
		StatusResponse statusResponse=cancelItemBySeller.getStatus();
		sft.assertEquals(statusResponse.getStatusType(), Type.SUCCESS,"Status type does not matched");
		for(int i=0;i<cancelItemBySeller.getData().size();i++){
			SellerPacketItemEntry sellerPacketItemEntry=cancelItemBySeller.getData().get(i);
			sft.assertEquals(sellerPacketItemEntry.getStatus(),EnumSCM.SELLER_PACKET_ITEM_STATUS_IC,"Status does not matched. It should be PK");
		}
		sft.assertAll();
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Get Seller Packet by Seller Packet ID")
	public void cancelSellerPacketForSingleItem() throws Exception{
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":1"};
		OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);
		Long orderReleaseId=orderEntry.getOrderReleases().get(0).getId();
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry, EnumSCM.STORE_ID_MYNTRA, OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
		Long sellerPacketId=sellerPacketResponse.getData().get(0).getId();
		String [] itemBarcodes=packmanServiceHelper.getItemBarcode(sellerPacketResponse.getData().get(0).getPacketItems());
		SellerPacketResponse cancelItemsBySeller=packmanServiceHelper.cancelItemsBySeller(sellerPacketId, itemBarcodes, EnumSCM.QC_DESK_CODE, 1L);
		List<OrderLineEntry> orderLineEntries= omsServiceHelper.getOrderLineEntries(String.valueOf(orderReleaseId));
		for(int i=0;i<orderLineEntries.size();i++){
			System.out.println(orderLineEntries.get(i).getStatus() +" and "+orderLineEntries.get(i).getCancellationReason());
		}
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Create Seller Packet for Multiple Release")
	public void multiItemReleaseTest() throws Exception{
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":2"};
		OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);
		Long orderReleaseId=orderEntry.getOrderReleases().get(0).getId();
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry, EnumSCM.STORE_ID_MYNTRA, OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
		Long sellerPacketId=sellerPacketResponse.getData().get(0).getId();
		String [] itemBarcodes=packmanServiceHelper.getItemBarcode(sellerPacketResponse.getData().get(0).getPacketItems());
		packmanServiceHelper.cancelItemsBySeller(sellerPacketId, itemBarcodes, EnumSCM.QC_DESK_CODE, 1L);
		
		List lineEnteries=omsServiceHelper.getOrderLineDBEntryforRelease(String.valueOf(orderReleaseId));
		System.out.println(lineEnteries);
	}
	@Test(enabled = true,groups={"regression","Packman"},description="Get Seller Packet by Seller Packet ID")
	public void cancelSellerPacketForSingleItemForQcPassItem() throws Exception{
		SellerPacketItemResponse cancelItemsBySeller=null;
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":1"};
		OrderEntry orderEntry= packmanServiceHelper.getOrderEntry(login, password, skuId,OMSTCConstants.Pincodes.PINCODE_560068, false, vectorSellerID);
		SellerPacketResponse sellerPacketResponse=packmanServiceHelper.getDataForCreateSellerPacket(orderEntry, EnumSCM.STORE_ID_MYNTRA, OMSTCConstants.WareHouseIds.warehouseId36_BW,vectorSellerID);
		cancelItemsBySeller=(SellerPacketItemResponse) packmanServiceHelper.createSellerPacketInDesiredState(sellerPacketResponse,EnumSCM.SELLER_PACKET_ITEM_STATUS_IC);
		if(cancelItemsBySeller.getStatus().getStatusType().equals(Type.SUCCESS)){
			System.out.println("Cancellation Api is working fine now.");
		}
	}
}
