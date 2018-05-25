package com.myntra.apiTests.erpservices.oms.Test;

import com.google.gdata.util.ServiceException;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.InvoiceServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.lordoftherings.Initialize;
import com.myntra.munshi.domain.documents.entry.InvoiceItemEntry;
import com.myntra.munshi.domain.documents.entry.StickerInvoiceEntry;
import com.myntra.munshi.domain.documents.response.StickerInvoiceResponseEntry;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.oms.common.enums.LineMovementAction;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 16553 on 27/02/17.
 */
public class InvoiceResponseValidationTests extends BaseTest{
    static Initialize init = new Initialize("/Data/configuration");
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	ProcessRelease processRelease = new ProcessRelease();
    private static Long vectorSellerID;
    String login = OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestLogin;
    String uidx;
    String password = OMSTCConstants.CancellationTillHubTest.PASSWORD;
    static String login_discount = OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestLogin;
    static String password_discount = OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestPassword;
	SoftAssert sft;
    static List<HashMap<String, String>> excelData;
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    End2EndHelper end2EndHelper = new End2EndHelper();
    InvoiceServiceHelper invoiceserviceHelper=new InvoiceServiceHelper();
    OMSServiceHelper omserviceHelper=new OMSServiceHelper();
    private String supplyTypeOnHand = "ON_HAND";
    private OrderEntry orderEntry;
    private String orderReleaseId;
    String coupon_More_Than_20="govttax";
    String coupon_Less_Than_20="govttax1";
    private String orderID;
    boolean isDelayed=false;
    private static Logger log = Logger.getLogger(InvoiceResponseValidationTests.class);
    String folderPath="../Desktop/invoice/Other";

    @BeforeClass(alwaysRun = true)
    public void testBeforeClass() throws SQLException, JAXBException, IOException, GeneralSecurityException, ServiceException, URISyntaxException {
        //vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
        vectorSellerID=21L;
        omsServiceHelper.refreshOMSApplicationPropertyCache();
        omsServiceHelper.refreshOMSJVMCache();
        uidx = ideaApiHelper.getUIDXForLogin("myntra", login_discount);
       // excelData = InvoiceServiceHelper.getDataFromExcel();
        uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login_discount);
       // excelData = InvoiceServiceHelper.getDataFromExcel();
        excelData=null;
    }

    @Test
    public void OrderWithMultipleLines_AndSingleLineCancellation_WithDiscounts() throws Exception {
       String skuId[] = { "3867"+":2"};
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_3835+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,OMSTCConstants.OtherSkus.skuId_3835+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},supplyTypeOnHand);
        String orderID = end2EndHelper.createOrder(login_discount, password_discount, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        String lineID = ""+orderLineEntries.get(0).getId();
        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login_discount, new String[] {lineID +":1"}, 41);
        List<OrderReleaseEntry> releases = orderEntry.getOrderReleases();
        for(OrderReleaseEntry releaseEntry: releases) {
            omsServiceHelper.stampGovtTaxForVectorSuccess(releaseEntry.getId().toString());
            End2EndHelper.sleep(10000);

			List<ReleaseEntry> releaseEntries = new ArrayList<>();
			releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
			ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
			processRelease.processReleaseToStatus(releaseEntryList);

			//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(releaseEntry.getId().toString(), ReleaseStatus.PK).build());
            StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(releaseEntry.getId().toString());
            validate(releaseDocumentResponse, excelData.get(15),15);
            invoiceserviceHelper.addInvoiceToFolder(releaseEntry.getId().toString(),folderPath);
        }
    }

    @Test
    public void ReassignedOrder_NotHavingVatRefund_SingleLine () throws Exception
        {
            String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831 + ":1"};
            atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID + ":1"}, supplyTypeOnHand);
            imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID},
                    supplyTypeOnHand);

            orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, coupon_Less_Than_20, false, true, false, "", false);
            log.info("OrderID: " + orderID);
            omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
            orderEntry = omsServiceHelper.getOrderEntry(orderID);
            OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
            orderReleaseId = orderReleaseEntry.getId().toString();
            omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
            String lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();

            //Reassign to new warehouse 36
            Integer wareHouseId = 28;
            String corrierCode = String.valueOf(orderReleaseEntry.getCourierCode());
            String LineIDAndQuantity[] = {"" + lineId + ":1"};
            imsServiceHelper.updateInventoryForSeller(new String[]{"3831" + ":" + OMSTCConstants.WareHouseIds.warehouseId28_GN + ":10000:0:" + vectorSellerID},
                    supplyTypeOnHand);
            OrderReleaseResponse orderReleaseResponse = omsServiceHelper.splitOrder(orderReleaseId, OMSTCConstants.WareHouseIds.warehouseId28_GN, LineIDAndQuantity, corrierCode, LineMovementAction.REASSIGN_WAREHOUSE);
            orderReleaseEntry = orderEntry.getOrderReleases().get(0);
            lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();
            omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
           // omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

			List<ReleaseEntry> releaseEntries = new ArrayList<>();
			releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
			ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
			processRelease.processReleaseToStatus(releaseEntryList);

			//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
            StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseEntry.getId().toString());
            validate(releaseDocumentResponse, excelData.get(15),15);
            invoiceserviceHelper.addInvoiceToFolder(orderReleaseEntry.getId().toString(),folderPath);
    }


    @Test
    public void ReassignedOrder_HavingVatRefund_SingleLine () throws Exception{
        String skuId[] = { OMSTCConstants.VatAdjutments.skuID_890848+":1" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_890848+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_890848+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
                supplyTypeOnHand);

        orderID = end2EndHelper.createOrder(login_discount, password_discount, OMSTCConstants.Pincodes.PINCODE_560068, skuId, coupon_More_Than_20, false, false, false, "", false);
        log.info("OrderID: "+orderID);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
        orderReleaseId = orderReleaseEntry.getId().toString();
        omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
        String lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();

        //Reassign to new warehouse 36
        Integer wareHouseId = 28;
        String corrierCode = String.valueOf(orderReleaseEntry.getCourierCode());
        String LineIDAndQuantity[] = { "" + lineId + ":1"};
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_890848+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
                supplyTypeOnHand);

        OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, OMSTCConstants.WareHouseIds.warehouseId28_GN, LineIDAndQuantity, corrierCode, LineMovementAction.REASSIGN_WAREHOUSE);
        orderReleaseEntry = orderEntry.getOrderReleases().get(0);
        omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseEntry.getId().toString());
        omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
        StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseEntry.getId().toString());
        validate(releaseDocumentResponse, excelData.get(32),32);
        invoiceserviceHelper.addInvoiceToFolder(orderReleaseEntry.getId().toString(),folderPath);
    }

    

    @Test
    public void ReassignedOrder_HavingVatRefund_MultiSkus_All_HavingRefund () throws Exception {
        String skuId[] = { OMSTCConstants.VatAdjutments.skuID_890848+":2" , OMSTCConstants.VatAdjutments.skuID_3133754+":2"};
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_890848+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_890848+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
                supplyTypeOnHand);
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_3133754+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_3133754+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
                supplyTypeOnHand);

        orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, coupon_More_Than_20, false, false, false, "", false);
        log.info("OrderID: "+orderID);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
        orderReleaseId = orderReleaseEntry.getId().toString();
        omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_890848+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
                supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_3133754+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
                supplyTypeOnHand);
        for(OrderLineEntry orderlineId : orderReleaseEntry.getOrderLines()) {
            String lineId = orderlineId.getId().toString();
            Integer wareHouseId = 28;
            String corrierCode = String.valueOf(orderReleaseEntry.getCourierCode());
            String LineIDAndQuantity[] = { "" + lineId + ":2"};
            OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, OMSTCConstants.WareHouseIds.warehouseId28_GN, LineIDAndQuantity, corrierCode, LineMovementAction.REASSIGN_WAREHOUSE);
            String orderReleaseId = orderReleaseResponse.getData().get(0).getId().toString();
            omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
            omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

			List<ReleaseEntry> releaseEntries = new ArrayList<>();
			releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
			ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
			processRelease.processReleaseToStatus(releaseEntryList);

			//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
            StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseEntry.getId().toString());
            validate(releaseDocumentResponse, excelData.get(32),32);
            invoiceserviceHelper.addInvoiceToFolder(orderReleaseEntry.getId().toString(),folderPath);
        }
    }


	@Test
	public void ReassignedOrder_NotHavingVatRefund_SingleLine_() throws Exception{

		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3831+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, coupon_Less_Than_20, false, true, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		orderReleaseId = orderReleaseEntry.getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		String lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();

		//Reassign to new warehouse 36
		Integer wareHouseId = 28;
		String corrierCode = String.valueOf(orderReleaseEntry.getCourierCode());
		String LineIDAndQuantity[] = { "" + lineId + ":1"};
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);


		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, OMSTCConstants.WareHouseIds.warehouseId28_GN, LineIDAndQuantity, corrierCode, LineMovementAction.REASSIGN_WAREHOUSE);
		orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();

		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

	//	processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
        StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseEntry.getId().toString());
        validate(releaseDocumentResponse, excelData.get(32),32);
        invoiceserviceHelper.addInvoiceToFolder(orderReleaseEntry.getId().toString(),folderPath);
	}


	@Test
	public void ReassignedOrder_HavingVatRefund_SingleLine_() throws Exception {
		String skuId[] = { OMSTCConstants.VatAdjutments.skuID_890848+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_890848+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_890848+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, coupon_More_Than_20, false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		orderReleaseId = orderReleaseEntry.getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		String lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();

		//Reassign to new warehouse 36
		Integer wareHouseId = 28;
		String corrierCode = String.valueOf(orderReleaseEntry.getCourierCode());
		String LineIDAndQuantity[] = { "" + lineId + ":1"};
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_890848+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, OMSTCConstants.WareHouseIds.warehouseId28_GN, LineIDAndQuantity, corrierCode, LineMovementAction.REASSIGN_WAREHOUSE);
		orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseEntry.getId().toString());
		omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
        StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseEntry.getId().toString());
        validate(releaseDocumentResponse, excelData.get(32),32);
        invoiceserviceHelper.addInvoiceToFolder(orderReleaseEntry.getId().toString(),folderPath);
	}

	@Test
	public void ReassignedOrder_HavingVatRefund_MultiSkus_NotAll_HavingRefund_() throws Exception {
		String skuId[] = { OMSTCConstants.VatAdjutments.skuID_890848+":1" , OMSTCConstants.OtherSkus.skuId_3831+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_890848+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_890848+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, coupon_More_Than_20, false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		orderReleaseId = orderReleaseEntry.getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_890848+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);
		for(OrderLineEntry orderlineId : orderReleaseEntry.getOrderLines()) {
			String lineId = orderlineId.getId().toString();
			Integer wareHouseId = 28;
			String corrierCode = String.valueOf(orderReleaseEntry.getCourierCode());
			String LineIDAndQuantity[] = { "" + lineId + ":1"};
			OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, OMSTCConstants.WareHouseIds.warehouseId28_GN, LineIDAndQuantity, corrierCode, LineMovementAction.REASSIGN_WAREHOUSE);
			String orderReleaseId = orderReleaseResponse.getData().get(0).getId().toString();
			omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
			 omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

			List<ReleaseEntry> releaseEntries = new ArrayList<>();
			releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
			ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
			processRelease.processReleaseToStatus(releaseEntryList);

			//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
	        StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseEntry.getId().toString());
	        validate(releaseDocumentResponse, excelData.get(32),32);
	        invoiceserviceHelper.addInvoiceToFolder(orderReleaseEntry.getId().toString(),folderPath);
		}
	}


	@Test
	public void ReassignedOrder_HavingVatRefund_MultiSkus_All_HavingRefund_() throws Exception {
		String skuId[] = { OMSTCConstants.VatAdjutments.skuID_890848+":2" , OMSTCConstants.VatAdjutments.skuID_3133754+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_890848+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_890848+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_3133754+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_3133754+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, coupon_More_Than_20, false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		orderReleaseId = orderReleaseEntry.getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_890848+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.VatAdjutments.skuID_3133754+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);
		for(OrderLineEntry orderlineId : orderReleaseEntry.getOrderLines()) {
			String lineId = orderlineId.getId().toString();
			Integer wareHouseId = 28;
			String corrierCode = String.valueOf(orderReleaseEntry.getCourierCode());
			String LineIDAndQuantity[] = { "" + lineId + ":2"};
			OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, OMSTCConstants.WareHouseIds.warehouseId28_GN, LineIDAndQuantity, corrierCode, LineMovementAction.REASSIGN_WAREHOUSE);
			String orderReleaseId = orderReleaseResponse.getData().get(0).getId().toString();
			omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
			 omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

			List<ReleaseEntry> releaseEntries = new ArrayList<>();
			releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
			ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
			processRelease.processReleaseToStatus(releaseEntryList);

			//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
	        StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseEntry.getId().toString());
	        validate(releaseDocumentResponse, excelData.get(32),32);
	        invoiceserviceHelper.addInvoiceToFolder(orderReleaseEntry.getId().toString(),folderPath);
		}
	}
	
	@Test
	public void OrderwithLP_COD_() throws Exception {
		String skuId[] = {OMSTCConstants.Different_HSNCODES_SKU.skuId_40563 + ":4"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.Different_HSNCODES_SKU.skuId_40563 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID + ":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.Different_HSNCODES_SKU.skuId_40563 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID},
				supplyTypeOnHand);
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3835 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID + ":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3835  + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID},
				supplyTypeOnHand);
		end2EndHelper.updateloyalityAndCashBack(uidx, 400, 0);
		orderID = end2EndHelper.createOrder(login_discount, password_discount, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, true, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		orderReleaseId = orderReleaseEntry.getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
        StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseEntry.getId().toString());
        validate(releaseDocumentResponse, excelData.get(22),22);
        invoiceserviceHelper.addInvoiceToFolder(orderReleaseEntry.getId().toString(),folderPath);
	}
	@Test
	public void OrderwithLP_COD_WithDiscounts() throws Exception {
		String skuId[] = {OMSTCConstants.Different_HSNCODES_SKU.skuId_40563 + ":2",OMSTCConstants.Different_HSNCODES_SKU.skuId_749877 + ":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.Different_HSNCODES_SKU.skuId_40563 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID + ":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.Different_HSNCODES_SKU.skuId_40563 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID},
				supplyTypeOnHand);
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.Different_HSNCODES_SKU.skuId_749877 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:25:1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.Different_HSNCODES_SKU.skuId_749877  + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:25"},
				supplyTypeOnHand);
		end2EndHelper.updateloyalityAndCashBack(uidx, 400, 0);
		orderID = end2EndHelper.createOrder(login_discount, password_discount, OMSTCConstants.Pincodes.PINCODE_560068, skuId, coupon_More_Than_20, false, true, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		orderReleaseId = orderReleaseEntry.getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
        StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseEntry.getId().toString());
        validate(releaseDocumentResponse, excelData.get(32),32);
        invoiceserviceHelper.addInvoiceToFolder(orderReleaseEntry.getId().toString(),folderPath);
	}
	
	@Test
	public void OrderwithLP_COD_WithDiscountsAndCharges() throws Exception {
		String skuId[] = {OMSTCConstants.Different_HSNCODES_SKU.skuId_40563 + ":2",OMSTCConstants.Different_HSNCODES_SKU.skuId_40562 + ":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.Different_HSNCODES_SKU.skuId_40563 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID + ":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.Different_HSNCODES_SKU.skuId_40563 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID},
				supplyTypeOnHand);
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.Different_HSNCODES_SKU.skuId_40562 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:25:1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.Different_HSNCODES_SKU.skuId_40562  + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:25"},
				supplyTypeOnHand);
		end2EndHelper.updateloyalityAndCashBack(uidx, 500, 0);
		orderID = end2EndHelper.createOrder(login_discount, password_discount, OMSTCConstants.Pincodes.PINCODE_560068, skuId, coupon_More_Than_20, false, true, true, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		orderReleaseId = orderReleaseEntry.getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
        StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseEntry.getId().toString());
        validate(releaseDocumentResponse, excelData.get(32),32);
        invoiceserviceHelper.addInvoiceToFolder(orderReleaseEntry.getId().toString(),folderPath);
	}
	
	

	@Test(enabled=true ,description = "TC17: OrderWithMultipleLines AndMultipleLineCancellation_WithoutDiscounts")
	public void OrderWithMultipleLinesAndMultipleLineCancellation_WithoutDiscounts() throws Exception{
		sft = new SoftAssert();

		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":4",OMSTCConstants.OtherSkus.skuId_3832+":4"};

		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"},"ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},"ON_HAND");

		String orderID = end2EndHelper.createOrder(login, password, "6132352", skuId, "", false, false, false, "", true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
		String lineID1 = ""+orderLineEntries.get(0).getId();
		String lineID2 = ""+orderLineEntries.get(1).getId();

		OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login, new String[] {lineID1 +":2"}, 41);
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"There should be Success but Actual:"+cancellationRes.getStatus().getStatusType());

		cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login, new String[] {lineID2 +":2"}, 41);
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"There should be Success but Actual:"+cancellationRes.getStatus().getStatusType());
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
        StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseId);
        validate(releaseDocumentResponse, excelData.get(17),17);
        invoiceserviceHelper.addInvoiceToFolder(orderReleaseId,folderPath);
	}

	@Test(enabled=true ,description = "TC18: OrderWithMultipleLinesAndMultipleLineCancellation_WithAdditionalCharges_DiscountsLessThan20")
	public void OrderWithMultipleLinesAndMultipleLineCancellation_WithAdditionalCharges_DiscountsLessThan20() throws Exception{
		sft = new SoftAssert();

		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":4",OMSTCConstants.OtherSkus.skuId_3832+":4"};

		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"},"ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},"ON_HAND");

		String orderID = end2EndHelper.createOrder(login_discount, password_discount, "6132352", skuId, coupon_Less_Than_20, false, false, true, "", true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
		String lineID1 = ""+orderLineEntries.get(0).getId();
		String lineID2 = ""+orderLineEntries.get(1).getId();

		OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login_discount, new String[] {lineID1 +":2"}, 41);
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"There should be Success but Actual:"+cancellationRes.getStatus().getStatusType());

		cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login_discount, new String[] {lineID2 +":2"}, 41);
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"There should be Success but Actual:"+cancellationRes.getStatus().getStatusType());
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

	//	processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
        StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseId);
        validate(releaseDocumentResponse, excelData.get(18),18);
        invoiceserviceHelper.addInvoiceToFolder(orderReleaseId,folderPath);
	}

	@Test(enabled=true ,description = "TC19: OrderWithMultipleLinesAndMultipleLineCancellation_WithAdditionalCharges_DiscountsMoreThan20")
	public void OrderWithMultipleLinesAndMultipleLineCancellation_WithAdditionalCharges_DiscountsMoreThan20() throws Exception {
		sft = new SoftAssert();

		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":4",OMSTCConstants.OtherSkus.skuId_3832+":4"};

		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"},"ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},"ON_HAND");

		String orderID = end2EndHelper.createOrder(login_discount, password_discount, "6132352", skuId, coupon_Less_Than_20, false, false, true, "", true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
		String lineID1 = ""+orderLineEntries.get(0).getId();
		String lineID2 = ""+orderLineEntries.get(1).getId();

		OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login_discount, new String[] {lineID1 +":2"}, 41);
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"There should be Success but Actual:"+cancellationRes.getStatus().getStatusType());

		cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login_discount, new String[] {lineID2 +":2"}, 41);
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"There should be Success but Actual:"+cancellationRes.getStatus().getStatusType());
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseId);
		validate(releaseDocumentResponse, excelData.get(19),19);
		 invoiceserviceHelper.addInvoiceToFolder(orderReleaseId,folderPath);
	}

	@Test(enabled=true, description = "TC20")
	public void createInvoiceOfTwoQtyCancelOneQtyForMultipleItems() throws Exception {
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":4",OMSTCConstants.OtherSkus.skuId_3832+":4"};

		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"},"ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},"ON_HAND");

		String orderID = end2EndHelper.createOrder(login, password, "6132352", skuId, "", false, false, false, "", false);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines().get(0);
		OrderLineEntry orderLineEntry1 = omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines().get(1);

		OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, "erpadmin", new String[] {orderLineEntry.getId() +":2"}, 39);
		Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"Verify success response");

		cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, "erpadmin", new String[] {orderLineEntry1.getId() +":2"}, 39);
		Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"Verify success response");
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

	//	processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
        StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseId);
        validate(releaseDocumentResponse, excelData.get(32),32);
        invoiceserviceHelper.addInvoiceToFolder(orderReleaseId,folderPath);
	}

	@Test(enabled=true, description = "TC21: OrderWith4QtyandCancellationof2QtyForSameSkus")
	public void OrderWith4QtyandCancellationof2QtyForSameSkus() throws Exception {
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":4"};

		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"},"ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},"ON_HAND");

		String orderID = end2EndHelper.createOrder(login, password, "6132352", skuId, "", false, false, false, "", false);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines().get(0);
		OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, "erpadmin", new String[] {orderLineEntry.getId() +":1"}, 39);
		Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"Verify success response");
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
        StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseId);
        validate(releaseDocumentResponse, excelData.get(32),32);
        invoiceserviceHelper.addInvoiceToFolder(orderReleaseId,folderPath);
	}

	@Test(enabled=true, description = "TC22:OrderWith4QtyandCancellationof2QtyForDiffSkusWithAdditionalCharges")
	public void OrderWith4QtyandCancellationof2QtyForDiffSkusWithAdditionalCharges() throws Exception {
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":4",OMSTCConstants.OtherSkus.skuId_3832+":4"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"},"ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},"ON_HAND");

		String orderID = end2EndHelper.createOrder(login, password, "6132352", skuId, "", false, false, true, "", false);        
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines().get(0);
		OrderLineEntry orderLineEntry1 = omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines().get(1);

		OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, "erpadmin", new String[] {orderLineEntry.getId() +":2"}, 39);
		Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"Verify success response");

		cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, "erpadmin", new String[] {orderLineEntry1.getId() +":2"}, 39);
		Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"Verify success response");
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
        StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseId);
        validate(releaseDocumentResponse, excelData.get(32),32);
        invoiceserviceHelper.addInvoiceToFolder(orderReleaseId,folderPath);
	}

	@Test(enabled=true, description = "TC23 : createInvoiceOfSevenQtyCancelThreeQty")
	public void createInvoiceOfSevenQtyCancelThreeQty() throws Exception {
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":7"};

		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"},"ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},"ON_HAND");

		String orderID = end2EndHelper.createOrder(login, password, "6132352", skuId, "", false, false, false, "", true);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines().get(0);
		OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, "erpadmin", new String[] {orderLineEntry.getId() +":3"}, 39);
		Assert.assertEquals(cancellationRes.getStatus().getStatusCode(), 1034,"Status code should be same");
		Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"Verify success response");
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
        StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseId);
        validate(releaseDocumentResponse, excelData.get(32),32);
        invoiceserviceHelper.addInvoiceToFolder(orderReleaseId,folderPath);
	}

	@Test(enabled=true, description = "TC24")
	public void createInvoiceOfFiveandSevenQtyCancelTwoAndThreeQtyForMultipleItems() throws Exception{
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":5",OMSTCConstants.OtherSkus.skuId_3832+":7"};

		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"},"ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},"ON_HAND");

		String orderID = end2EndHelper.createOrder(login, password, "6132352", skuId, "", false, false, false, "", true);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines().get(0);
		OrderLineEntry orderLineEntry1 = omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines().get(1);

		OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, "erpadmin", new String[] {orderLineEntry.getId() +":2"}, 39);
		Assert.assertEquals(cancellationRes.getStatus().getStatusCode(), 1034,"Status code should be same");
		Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"Verify success response");

		cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, "erpadmin", new String[] {orderLineEntry1.getId() +":3"}, 39);
		Assert.assertEquals(cancellationRes.getStatus().getStatusCode(), 1034,"Status code should be same");
		Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"Verify success response");
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

	//	processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
        StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseId);
        validate(releaseDocumentResponse, excelData.get(32),32);
        invoiceserviceHelper.addInvoiceToFolder(orderReleaseId,folderPath);
	}
	//2147335659 - with discount and cart tax applied
	 @Test
	    public void Order_GSTAdjustment_ForSingleLine () throws Exception 
	        {
	            String skuId[] = {OMSTCConstants.Different_HSNCODES_SKU.skuId_40563 + ":1"};
	            atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.Different_HSNCODES_SKU.skuId_40563 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID + ":1"}, supplyTypeOnHand);
	            imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.Different_HSNCODES_SKU.skuId_40563 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID},
	                    supplyTypeOnHand);
	            orderID = end2EndHelper.createOrder(login_discount, password_discount, OMSTCConstants.Pincodes.PINCODE_560068, skuId, coupon_More_Than_20, false, true, false, "", false);
	            log.info("OrderID: " + orderID);
	            omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
	            orderEntry = omsServiceHelper.getOrderEntry(orderID);
	            OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
	            orderReleaseId = orderReleaseEntry.getId().toString();
	            omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
	            String lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();
	           // processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
	            //Reassign to new warehouse 36
	            Integer wareHouseId = 28;
	            String corrierCode = String.valueOf(orderReleaseEntry.getCourierCode());
	            String LineIDAndQuantity[] = {"" + lineId + ":1"};
	            imsServiceHelper.updateInventoryForSeller(new String[]{ OMSTCConstants.Different_HSNCODES_SKU.skuId_40563 + ":" + OMSTCConstants.WareHouseIds.warehouseId28_GN + ":10000:0:" + vectorSellerID},
	                    supplyTypeOnHand);
	            OrderReleaseResponse orderReleaseResponse = omsServiceHelper.splitOrder(orderReleaseId, OMSTCConstants.WareHouseIds.warehouseId28_GN, LineIDAndQuantity, corrierCode, LineMovementAction.REASSIGN_WAREHOUSE);
	            orderReleaseEntry = orderEntry.getOrderReleases().get(0);
	            lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();
	            omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
	            omserviceHelper.assignTrackingNumber(orderReleaseId, isDelayed);

				List<ReleaseEntry> releaseEntries = new ArrayList<>();
				releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
				ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
				processRelease.processReleaseToStatus(releaseEntryList);

				//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
	            StickerInvoiceResponseEntry releaseDocumentResponse = InvoiceServiceHelper.getreleaseDocumentResponse(orderReleaseId);
	            validate(releaseDocumentResponse, excelData.get(32),32); 
	            invoiceserviceHelper.addInvoiceToFolder(orderReleaseId,folderPath);
	   }

	private void validate(StickerInvoiceResponseEntry releaseDocumentResponse, HashMap<String, String> excelHashMap, int rowNum) {
		SoftAssert sft = new SoftAssert();
		StickerInvoiceEntry stickerInvoiceEntry =  (StickerInvoiceEntry)releaseDocumentResponse.getData().get(0);
		List<InvoiceItemEntry> itemList= stickerInvoiceEntry.getItems();

		Double amttobepaid=Double.parseDouble(excelHashMap.get("amttobepaid"));
		Double specialdiscount=Double.parseDouble(excelHashMap.get("specialdiscount"));
		Double amountactuallypaidbycustomer=Double.parseDouble(excelHashMap.get("amountactuallypaidbycustomer"));
		for(int i=1;i<=Integer.parseInt(excelHashMap.get("nooflinesperrelease"));i++){

			sft.assertEquals(roundOff(itemList.get(i-1).getTotalTaxableAmount()),roundOff(Double.parseDouble(excelHashMap.get("govttaxableamount")))," :govttaxableamount: ");
			sft.assertEquals(roundOff(itemList.get(i-1).getTaxEntries().get(i-1).getTaxAmount()),roundOff(Double.parseDouble(excelHashMap.get("taxamount")))," :taxamount: ");
			HashMap<String, String> excelHashMapNext =excelData.get(rowNum+i);
			amttobepaid=amttobepaid+Double.parseDouble(excelHashMapNext.get("amttobepaid"));
		}
		sft.assertEquals(releaseDocumentResponse.getStatus().getStatusCode(),3);
		sft.assertEquals(releaseDocumentResponse.getStatus().getStatusMessage(),"Success");
		sft.assertEquals(roundOff(stickerInvoiceEntry.getShipmentValue()),roundOff(amttobepaid)," :amttobepaid: ");
		sft.assertEquals(roundOff(stickerInvoiceEntry.getSpecialDiscount()),roundOff(stickerInvoiceEntry.getShipmentValue()-stickerInvoiceEntry.getCodAndOnAmount())," :specialdiscount: ");
		sft.assertEquals(roundOff0(stickerInvoiceEntry.getCodAndOnAmount()),roundOff0(amountactuallypaidbycustomer), " :amountactuallypaidbycustomer: ");
		sft.assertAll();
	}

	public double roundOff(Double d)
	{
		DecimalFormat f = new DecimalFormat("##.0");
		return Double.parseDouble((f.format(d)));
	}
	public double roundOff0(Double d)
	{
		DecimalFormat f = new DecimalFormat("##");
		return Double.parseDouble((f.format(d)));
	}



}
