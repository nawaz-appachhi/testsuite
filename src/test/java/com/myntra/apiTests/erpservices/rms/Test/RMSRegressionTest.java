

package com.myntra.apiTests.erpservices.rms.Test;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
//import com.myntra.apiTests.erpservices.lms.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LMS_ReturnHelper;
import com.myntra.apiTests.erpservices.rms.Test.RMS_Constants.LMS_STATUS;
import com.myntra.apiTests.erpservices.rms.Test.RMS_Constants.RMS_STATUS;
import com.myntra.apiTests.erpservices.rms.dp.RmsDP;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.parser.emailParser.XMLParser;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.returns.common.enums.RefundMode;
import com.myntra.returns.common.enums.ReturnMode;
import com.myntra.returns.common.enums.ReturnType;
import com.myntra.returns.common.enums.code.ReturnActionCode;
import com.myntra.returns.common.enums.code.ReturnLineStatus;
import com.myntra.returns.common.enums.code.ReturnStatus;
import com.myntra.returns.entry.ReturnEntry;
import com.myntra.returns.entry.ReturnLineEntry;
import com.myntra.returns.response.ReturnLineResponse;
import com.myntra.returns.response.ReturnResponse;
import com.myntra.silkroute.client.response.RtoResponse;
import com.myntra.test.commons.testbase.BaseTest;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


/**
 * @author Pawell Soni
 *
 */

public class RMSRegressionTest extends BaseTest {
	
	Initialize init = new Initialize("/Data/configuration");
	Logger log = Logger.getLogger(RMSRegressionTest.class);
	RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    End2EndHelper end2EndHelper = new End2EndHelper();
    LMS_ReturnHelper lmsReturnHelper = new LMS_ReturnHelper();
    
    LMSHelper lmsHepler = new LMSHelper();
	
	End2EndHelper e2e = new End2EndHelper();
	private String BLRAddressId = "6136170";
	XMLParser read = new XMLParser();
	final String emailId = "end2endautomation4@gmail.com";
	final String password = "myntra@123";
    private static Long vectorSellerID;
    SoftAssert sft;
    private String supplyTypeOnHand = "ON_HAND";
    private String orderID;
    private OrderEntry orderEntry;
	private OrderReleaseEntry orderReleaseEntry;
	private String orderReleaseId;
	private boolean orderStatusPK;
	String login = OMSTCConstants.CancellationTillHubTest.LOGIN_URL;
	String password2 = OMSTCConstants.CancellationTillHubTest.PASSWORD;
	private String lmsStatus;
	private Assertion hardAssert = new Assertion();
	private SoftAssert softAssert = new SoftAssert();
	

	
	
    @BeforeClass
    public void testBeforeClass(){
        vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.ALFA);
        RmsDP.vectorSellerID2=vectorSellerID;
    }

    
/*
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=1,description="Return is picked up and marked QC Pass",enabled = false)
	public void PickupAndRestock() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.OPEN_BOX_PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		returnHelper.ItemBarcodeStamp(returnId, "100026329239");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		
		log.info("Courier Code" +returnEntry3.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry3.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry3.getReturnTrackingDetailsEntry().getTrackingNo());
		Assert.assertNotNull(returnEntry3.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		
		returnHelper.returnPickupProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPU, returnId, 10));
		ReturnLineEntry entry = returnEntry3.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RPU, ReturnLineStatus.RRC, entry.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "", "", "Pawell", (int)(long)returnEntry3.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 15));
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RRC, returnId, 15));
		Thread.sleep(6000);

		ReturnLineResponse returnResponse4 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, "70041385", returnEntry3.getId().toString(), "", "", RMS_STATUS.QA_PERSON, (int)(long)returnEntry3.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(),"Q1",RMS_STATUS.QC_REASON,RMS_STATUS.QC_DESCRIPTION);
		ReturnLineEntry returnEntry4 = returnResponse4.getData().get(0);

		Assert.assertEquals("70041385", ""+returnEntry4.getOrderId());
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPC, returnId, 15));

		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 15));

        Assert.assertEquals(RMS_STATUS.QC_REASON, returnEntry5.getReturnLineEntries().get(0).getReturnLineAdditionalDetailsEntry().getQaFailReason());
        Assert.assertEquals(RMS_STATUS.QC_DESCRIPTION, returnEntry5.getReturnLineEntries().get(0).getReturnLineAdditionalDetailsEntry().getQcDescription());
        Assert.assertEquals(RMS_STATUS.QA_PERSON, returnEntry5.getReturnLineEntries().get(0).getReturnLineAdditionalDetailsEntry().getQaPerson());

        log.info("\n Refund pps id :" +returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
        
	}	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=2,description="Return is picked up and marked QC Pass with Wallet as refund instrument",enabled = false)
	public void PickupAndRestockForWallet() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.WALLET, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertEquals(returnEntry2.getReturnRefundDetailsEntry().getRefundMode().toString(), "WALLET");
		
		returnHelper.returnPickupProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPU, returnId, 10));
		ReturnLineEntry entry = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RPU, ReturnLineStatus.RRC, entry.getId(), returnEntry.getReturnLineEntries().get(0).getId(), "", "100026329239", "", "", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
		returnHelper.returnPickupProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RRC);
		ReturnLineResponse returnResponse3 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, "70041385", returnEntry.getId().toString(), "100026329239", "", RMS_STATUS.QA_PERSON, 1,"Q1",RMS_STATUS.QC_REASON,RMS_STATUS.QC_DESCRIPTION);
		ReturnLineEntry returnEntry3 = returnResponse3.getData().get(0);

		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPC, returnId, 10));
		
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);

		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		//Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        System.out.println("Return PPS id : "+returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        
	}	
	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService"},priority=3,description="Self Ship return is restocked and status checked in LMS",enabled = false)
	public void SelfShipAndRestock() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
				
		ReturnLineEntry entry = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RRQS, ReturnLineStatus.RRC, entry.getId(), returnEntry.getReturnLineEntries().get(0).getId(), "", "100026329239", "BD", "BD12408712306", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));

		returnHelper.ItemBarcodeStamp(returnEntry.getId().toString().toString(), "100026329239");
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));

		ReturnLineResponse returnResponse3 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, "70041385", returnEntry.getId().toString(), "", "", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(),"Q1","","");
		
		ReturnLineEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPC, returnId, 10));
		
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
        Assert.assertEquals(799.00, returnEntry4.getReturnRefundDetailsEntry().getRefundAmount());
        System.out.println("Return PPS id : "+returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
		Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        

	}	

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService"},priority=4,description="Self Ship return is marked QC Fail and re-shipped back to customer",enabled = false)
	public void SelfShipQCFailAndReship() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Assert.assertEquals("70041385", ""+returnEntry2.getOrderId());
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));

		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		ReturnLineEntry entry = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RRQS, ReturnLineStatus.RRC, entry.getId(), returnEntry.getReturnLineEntries().get(0).getId(), "", "100026329239", "BD", "ML12408712306", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));

		returnHelper.ItemBarcodeStamp(returnEntry.getId().toString().toString(), "100026329239");
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));

		
		ReturnLineResponse returnResponse3 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQF, "70041385", returnEntry.getId().toString(), "", "", "Pawell", 1,"Q2","MD","Damage");
		ReturnLineEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertEquals("70041385", ""+returnEntry3.getOrderId());
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));
		Assert.assertEquals("Q2", returnEntry3.getQuality());
		Assert.assertEquals("MD", returnEntry3.getReturnLineAdditionalDetailsEntry().getQaFailReason());
		Assert.assertEquals("Damage", returnEntry3.getReturnLineAdditionalDetailsEntry().getQcDescription());

		ReturnLineResponse returnResponse4 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RQF, ReturnLineStatus.RQSF, "70041385", returnEntry.getId().toString(), "", "", "Pawell", 1,"Q2","MD","Damage");
		ReturnLineEntry returnEntry4 = returnResponse4.getData().get(0);
		
		Assert.assertEquals("70041385", ""+returnEntry4.getOrderId());
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQSF, returnId, 10));
		Assert.assertEquals("Q2", returnEntry4.getQuality());
		Assert.assertEquals("MD", returnEntry4.getReturnLineAdditionalDetailsEntry().getQaFailReason());
		Assert.assertEquals("Damage", returnEntry4.getReturnLineAdditionalDetailsEntry().getQcDescription());
		
		ReturnLineResponse returnResponse5 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RQSF, ReturnLineStatus.RQCF, "70041385", returnEntry.getId().toString(), "", "", "Pawell", 1,"Q2","MD","Damage");
		ReturnLineEntry returnEntry5 = returnResponse5.getData().get(0);
		
		Assert.assertEquals("70041385", ""+returnEntry5.getOrderId());
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQCF, returnId, 10));
		Assert.assertEquals("Q2", returnEntry5.getQuality());
		Assert.assertEquals("MD", returnEntry5.getReturnLineAdditionalDetailsEntry().getQaFailReason());
		Assert.assertEquals("Damage", returnEntry5.getReturnLineAdditionalDetailsEntry().getQcDescription());
		
		ReturnResponse returnResponse6 = returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPC, ReturnStatus.RRRS);
		ReturnEntry returnEntry6 = returnResponse6.getData().get(0);
		Assert.assertEquals(ReturnStatus.RRRS, returnEntry6.getStatusCode());
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRRS, returnId, 10));

		ReturnResponse returnResponse7 = returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RRRS, ReturnStatus.RRS);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertEquals(ReturnStatus.RRS, returnEntry7.getStatusCode());
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRS, returnId, 10));

		ReturnResponse returnResponse8 = returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RRS, ReturnStatus.RSD);
		ReturnEntry returnEntry8 = returnResponse8.getData().get(0);
		Assert.assertEquals(ReturnStatus.RSD, returnEntry8.getStatusCode());
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RSD, returnId, 10));

	}	
	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService"}, priority=5,description="Self Ship return is accepted at DC and pickup verified in LMS",enabled = false)
	public void SelfShipAcceptedDCSupervisorPass() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
	
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RRQS, ReturnStatus.RDU);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RADC, entry2.getId(), returnEntry.getReturnLineEntries().get(0).getId(), RMS_STATUS.QC_DESCRIPTION, "100026329239", "BD", "ML12408712306", RMS_STATUS.QA_PERSON, "MHP", RMS_STATUS.RMS_DC, "DC");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RADC, returnId, 10));
		ReturnLineEntry entry = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RADC, ReturnLineStatus.RRC, entry.getId(), returnEntry.getReturnLineEntries().get(0).getId(), "", "100026329239", "BD", "ML12408712306", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		returnHelper.ItemBarcodeStamp(returnEntry.getId().toString().toString(), "100026329239");
		
		ReturnLineResponse returnResponse3 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQF, "70041385", returnEntry.getId().toString(), "", "", "Pawell", 1,"Q3","MD","Damage");
		ReturnLineEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertEquals("70041385", ""+returnEntry3.getOrderId());
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));
		
		ReturnLineResponse returnResponse4 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RQF, ReturnLineStatus.RQSP, "70041385", returnEntry.getId().toString(), "", "", "Pawell", 1,"Q1","","");
		ReturnLineEntry returnEntry4 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPC, returnId, 15));

		Assert.assertEquals("70041385", ""+returnEntry3.getOrderId());
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));

		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		  
        //Assert.assertTrue(returnEntry5.getReturnRefundDetailsEntry().getRefunded());
        //Assert.assertEquals(returnEntry.getStatusCode().RPC, returnEntry5.getStatusCode());
        Assert.assertEquals(799.00, returnEntry5.getReturnRefundDetailsEntry().getRefundAmount());

        System.out.println("Return PPS id : "+returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));
        Assert.assertEquals(returnHelper.getReturnDC(returnId), RMS_STATUS.RMS_DC);
        Assert.assertEquals(returnHelper.getReturnWH(returnId),(int)returnEntry5.getReturnLineEntries().get(0).getWarehouseId());

	}	
	

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=6,description="Pickup fail 3 times and convert to Self Ship")
	public void PickupRetryConvertToSelfShip() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
	
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF, ReturnStatus.RPUQ2);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPUQ2, ReturnStatus.RPI2);
		ReturnResponse returnResponse6 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry6 = returnResponse6.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI2, ReturnStatus.RPF2);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF2, returnId, 10));

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF2, ReturnStatus.RPUQ3);
		ReturnResponse returnResponse8 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry8 = returnResponse8.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ3, returnId, 10));

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPUQ3, ReturnStatus.RPI3);
		ReturnResponse returnResponse9 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry9 = returnResponse9.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI3, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI3, ReturnStatus.RPF3);
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF3, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF3, ReturnStatus.RRQS);
		ReturnResponse returnResponse11 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry11 = returnResponse11.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
	
	}	

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=7,description="Pickup details updated and declined in LMS")
	public void PickupRDUDecline() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		String returnId = returnEntry.getId().toString().toString();
		
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Thread.sleep(3000);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI, ReturnStatus.RDU);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RDU, ReturnStatus.RRD);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRD, returnId, 10));
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));
		
	}	

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=8,description="Pickup declined and verified in LMS")
	public void RPIDecline() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Thread.sleep(15000);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI, ReturnStatus.RRD);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRD, returnId, 10));
		
		Thread.sleep(3000);
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));
	
	}	

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=9,description="Second Pickup declined and verified in LMS")
	public void RPI2Decline() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF, ReturnStatus.RPUQ2);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPUQ2, ReturnStatus.RPI2);
		ReturnResponse returnResponse6 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry6 = returnResponse6.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI2, returnId, 10));
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI2, ReturnStatus.RRD);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRD, returnId, 10));
		Thread.sleep(3000);
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));
	}	
	*/
	/*@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=10,description="Third Pickup declined and verified in LMS")
	public void RPI3Decline() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		System.out.println(returnEntry.getId().toString()); 
		System.out.println(returnEntry.getComment());
		System.out.println(returnEntry.getEmail());
		System.out.println(returnEntry.getReturnTrackingDetailsEntry());
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RRQP, ReturnStatus.RPI);

		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF, ReturnStatus.RPUQ2);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPUQ2, ReturnStatus.RPI2);
		ReturnResponse returnResponse6 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry6 = returnResponse6.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI2, ReturnStatus.RPF2);
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF2, ReturnStatus.RPUQ3);
		ReturnResponse returnResponse8 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry8 = returnResponse8.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ3, returnId, 10));

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPUQ3, ReturnStatus.RPI3);
		ReturnResponse returnResponse9 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry9 = returnResponse9.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI3, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI3, ReturnStatus.RRD);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRD, returnId, 10));
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=11,description="Selfship rejected and passed by CC at DC")
	public void SelfShipRejecteddcCCPass() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RRQS, ReturnLineStatus.RJDC, entry2.getId(), returnEntry.getReturnLineEntries().get(0).getId(), RMS_STATUS.QC_DESCRIPTION, "100026329239", "BD", "ML12408712306", RMS_STATUS.QA_PERSON, "MHP", RMS_STATUS.RMS_DC, "DC");
		//Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RJDC, returnId, 15));
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RJDC, returnId, 10));
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RJDC, ReturnLineStatus.CPDC, entry2.getId(), returnEntry.getReturnLineEntries().get(0).getId(), RMS_STATUS.QC_DESCRIPTION, "100026329239", "BD", "ML12408712306", RMS_STATUS.QA_PERSON, "MHP", RMS_STATUS.RMS_DC, "DC");
		//Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.CPDC, returnId, 15));
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.CPDC, returnId, 10));

        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));
		Thread.sleep(4000);

		ReturnLineEntry entry = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.CPDC, ReturnLineStatus.RRC, entry.getId(), returnEntry.getReturnLineEntries().get(0).getId(), "", "100026329239", "", "", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
		//returnHelper.returnStatusProcessNew(returnId, ReturnStatus.CPDC, ReturnStatus.RRC);

		returnHelper.ItemBarcodeStamp(returnEntry.getId().toString().toString(), "100026329239");
		Thread.sleep(4000);

		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineResponse returnResponse3 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQF, "70041385", returnEntry.getId().toString(), "", "", "Pawell", 1,"Q3","MD","Damage");
		ReturnLineEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));
		Thread.sleep(4000);
		returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RQF, ReturnLineStatus.RQSF, "70041385", returnEntry.getId().toString(), "", "", "Pawell", 1,"Q3","MD","Damage");
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQSF, returnId, 10));
		Thread.sleep(4000);
		ReturnLineResponse returnResponse8 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RQSF, ReturnLineStatus.RQCP, "70041385", returnEntry.getId().toString(), "", "", "Pawell", 1,"Q1","","");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPC, returnId, 10));

		ReturnLineEntry returnEntry4 = returnResponse8.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		  
		Assert.assertTrue(returnEntry5.getReturnRefundDetailsEntry().getRefunded());
        Assert.assertEquals(returnEntry.getStatusCode().RPC, returnEntry5.getStatusCode());
        
        System.out.println("Return PPS id : "+returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());

	}	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=12,description="Selfship rejected at dc and re-shipped back")
	public void SelfShipRejecteddcReshipped() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
	
		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RRQS, ReturnLineStatus.RJDC, entry2.getId(), returnEntry.getReturnLineEntries().get(0).getId(), RMS_STATUS.QC_DESCRIPTION, "100026329239", "BD", "ML12408712306", RMS_STATUS.QA_PERSON, "MHP", RMS_STATUS.RMS_DC, "DC");
        Thread.sleep(2000);
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_ONHOLD, returnId, 10));

		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RJDC, ReturnLineStatus.CFDC, entry2.getId(), returnEntry.getReturnLineEntries().get(0).getId(), RMS_STATUS.QC_DESCRIPTION, "100026329239", "BD", "ML12408712306", RMS_STATUS.QA_PERSON, "MHP", RMS_STATUS.RMS_DC, "DC");
        Thread.sleep(2000);
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.CFDC, ReturnStatus.RRRS);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRRS, returnId, 10));

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RRRS, ReturnStatus.RRS);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRS, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RRS, ReturnStatus.RSD);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RSD, returnId, 10));
	}	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=13,description="Closed Box Pickup declined")
	public void PickupDeclined() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RRQP, ReturnStatus.RRD);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RRD, returnId, 10));
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));


	}
	

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=14,description="Self-ship declined")
	public void SelfShipDeclined() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
	
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RRQS, ReturnStatus.RRD);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRD, returnId, 10));
		Thread.sleep(2000);
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));


	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=15,description="Closed Box pickup declined")
	public void PickupFailedToDecline() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		Thread.sleep(3000);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RRQP, ReturnStatus.RRD);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRD, returnId, 10));
		
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=16,description="Closed Box pickup failed 2 to declined")
	public void PickupFailed2ToDecline() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
	
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());

		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF, ReturnStatus.RPUQ2);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPUQ2, ReturnStatus.RPI2);
		ReturnResponse returnResponse6 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry6 = returnResponse6.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI2, ReturnStatus.RPF2);
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF2, ReturnStatus.RRD);
		ReturnResponse returnResponse8 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry8 = returnResponse8.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRD, returnId, 10));
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=17,description="Open Box pickup failed 3 to declined")
	public void PickupFailed3ToDecline() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF, ReturnStatus.RPUQ2);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPUQ2, ReturnStatus.RPI2);
		ReturnResponse returnResponse6 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry6 = returnResponse6.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI2, ReturnStatus.RPF2);
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF2, ReturnStatus.RPUQ3);
		ReturnResponse returnResponse8 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry8 = returnResponse8.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ3, returnId, 10));

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPUQ3, ReturnStatus.RPI3);
		ReturnResponse returnResponse9 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry9 = returnResponse9.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI3, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI3, ReturnStatus.RPF3);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF3, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF3, ReturnStatus.RRD);
		ReturnResponse returnResponse11 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry11 = returnResponse11.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRD, returnId, 10));
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));

	}
	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=18,description="Self-ship accepted at DC")
	public void SelfShipRDUAcceptedDC() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);

		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RRQS, ReturnLineStatus.RADC, entry2.getId(), returnEntry.getReturnLineEntries().get(0).getId(), RMS_STATUS.QC_DESCRIPTION, "100026329239", "BD", "ML12408712306", RMS_STATUS.QA_PERSON, "MHP", RMS_STATUS.RMS_DC, "DC");
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));
	}	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=19,description="Pickup queued 2nd time to declined")
	public void PickupQueued2ToDecline() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Thread.sleep(2000);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF, ReturnStatus.RPUQ2);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPUQ2, ReturnStatus.RRD);
		ReturnResponse returnResponse6 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry6 = returnResponse6.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRD, returnId, 10));
		Thread.sleep(2000);
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));
		
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=20,description="Pickup queued 3rd time to declined")
	public void PickupQueued3ToDecline() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF, ReturnStatus.RPUQ2);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPUQ2, ReturnStatus.RPI2);
		ReturnResponse returnResponse6 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry6 = returnResponse6.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI2, ReturnStatus.RPF2);
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF2, ReturnStatus.RPUQ3);
		ReturnResponse returnResponse8 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry8 = returnResponse8.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ3, returnId, 10));

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPUQ3, ReturnStatus.RRD);
		ReturnResponse returnResponse9 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry9 = returnResponse9.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRD, returnId, 10));
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=21,description="Pickup initiated 2nd time to picked up")
	public void PickupInitiated2ToPickedUp() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF, ReturnStatus.RPUQ2);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPUQ2, ReturnStatus.RPI2);
		ReturnResponse returnResponse6 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry6 = returnResponse6.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI2, ReturnStatus.RPU);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPU, returnId, 10));
		//Assert.assertNotNull(returnEntry7.getReturnRefundDetailsEntry().getRefunded());
		//Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));


	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=22,description="Pickup initiated 3rd time to picked up")
	public void PickupInitiated3ToPickedUp() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());

		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF, ReturnStatus.RPUQ2);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPUQ2, ReturnStatus.RPI2);
		ReturnResponse returnResponse6 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry6 = returnResponse6.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI2, ReturnStatus.RPF2);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPF2, ReturnStatus.RPUQ3);
		ReturnResponse returnResponse8 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry8 = returnResponse8.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ3, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPUQ3, ReturnStatus.RPI3);
		ReturnResponse returnResponse9 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry9 = returnResponse9.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI3, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI3, ReturnStatus.RPU);
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPU, returnId, 10));
		Thread.sleep(8000);
		//Assert.assertNotNull(returnEntry10.getReturnRefundDetailsEntry().getRefunded());
		//Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=23,description="Pickup initiated to details updated")
	public void PickupInitiatedToDetailsUpdated() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
	
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI, ReturnStatus.RDU);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_CREATED, returnId, 10));

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=24,description="Closed Box Pickup and QC Pass")
	public void NEFTPickupAndRestock() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
				
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertEquals("pawell.soni@myntra.com" , returnEntry2.getEmail());
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPI, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 15));
		ReturnLineEntry entry = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RDU, ReturnLineStatus.RRC, entry.getId(), returnEntry.getReturnLineEntries().get(0).getId(), "", "100026329239", "BD", "ML12408712306", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));

		returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, "70044366", returnEntry.getId().toString(), "100026329239", "", "Pawell", 1,"Q1","","");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPC, returnId, 10));
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);

		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		  
		Assert.assertTrue(returnEntry4.getReturnRefundDetailsEntry().getRefunded());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertTrue(returnEntry4.getReturnRefundDetailsEntry().getRefunded());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());*//*

        System.out.println("Return PPS id : "+returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));

	}
	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=25,description="Pickup to details updated")
	public void PickedUpToDetailsUpdated() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Thread.sleep(3000);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.returnPickupProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI);
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPU, ReturnStatus.RDU);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RDU, returnId, 10));
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));

		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		 
		Assert.assertTrue(returnEntry5.getReturnRefundDetailsEntry().getRefunded());
        Assert.assertNotNull(returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
		Assert.assertTrue(returnEntry5.getReturnRefundDetailsEntry().getRefunded());
        Assert.assertNotNull(returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());*//*

        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_CREATED, returnId, 10));

        System.out.println("Return PPS id : "+returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=26,description="Pickup queued second time to details updated")
	public void PickeupQueued2ToDetailsUpdated() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
	
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPF, ReturnStatus.RPUQ2);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPUQ2, ReturnStatus.RDU);
		ReturnResponse returnResponse6 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry6 = returnResponse6.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_CREATED, returnId, 10));

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=27,description="Pickup queued third time to details updated")
	public void PickeupQueued3ToDetailsUpdated() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPF, ReturnStatus.RPUQ2);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPUQ2, ReturnStatus.RPI2);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI2, ReturnStatus.RPF2);
		ReturnResponse returnResponse8 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry8 = returnResponse8.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPF2, ReturnStatus.RPUQ3);
		ReturnResponse returnResponse9 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry9 = returnResponse9.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ3, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPUQ3, ReturnStatus.RDU);
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_CREATED, returnId, 10));

		
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=28,description="Pickup failed to self ship")
	public void PickeupFailedTOSelfShip() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 16L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
	
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
	
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPF, ReturnStatus.RRQS);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_CREATED, returnId, 10));

		
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=29,description="Pickup failed 2 to self ship")
	public void PickeupFailed2TOSelfShip() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 16L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPF, ReturnStatus.RPUQ2);
		ReturnResponse returnResponse8 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry8 = returnResponse8.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPUQ2, ReturnStatus.RPI2);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI2, ReturnStatus.RPF2);
		ReturnResponse returnResponse6 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry6 = returnResponse6.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPF2, ReturnStatus.RRQS);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_CREATED, returnId, 10));

	}
	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=30,description="Pickup failed 3 to self ship")
	public void PickeupFailed3TOSelfShip() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 16L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RRQP, ReturnStatus.RPI);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertEquals(ReturnStatus.RPI, returnEntry3.getStatusCode());
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RRQP, ReturnStatus.RPI);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertEquals(ReturnStatus.RPI, returnEntry3.getStatusCode());*//*


		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPF, ReturnStatus.RPUQ2);
		ReturnResponse returnResponse8 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry8 = returnResponse8.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPUQ2, ReturnStatus.RPI2);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI2, ReturnStatus.RPF2);
		ReturnResponse returnResponse6 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry6 = returnResponse6.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF2, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPF2, ReturnStatus.RPUQ3);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPUQ3, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPUQ3, ReturnStatus.RPI3);
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI3, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI3, ReturnStatus.RPF3);
		ReturnResponse returnResponse11 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry11 = returnResponse11.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF3, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPF3, ReturnStatus.RRQS);
		ReturnResponse returnResponse12 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry12 = returnResponse12.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=31,description="Self-ship to RADC and verified in LMS")
	public void ReceivedDCToPPSRefund() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		returnHelper.returnStatusProcessNew(returnEntry2.getId().toString(), ReturnStatus.RRQS, ReturnStatus.RDU);

		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RADC, entry2.getId(), returnEntry.getReturnLineEntries().get(0).getId(), RMS_STATUS.QC_DESCRIPTION, "100026329239", "BD", "ML12408712306", RMS_STATUS.QA_PERSON, "MHP", RMS_STATUS.RMS_DC, "DC");

		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		 
		Assert.assertTrue(returnEntry5.getReturnRefundDetailsEntry().getRefunded());
        Assert.assertNotNull(returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
		Assert.assertTrue(returnEntry5.getReturnRefundDetailsEntry().getRefunded());
        Assert.assertNotNull(returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());*//*
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));

        System.out.println("Return PPS id : "+returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=32,description="Self ship detail updated to RJDC : Pickup Onhold")
	public void DetailsUpdatedToRejectedAtDC() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RRQS, ReturnStatus.RDU);
		
		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RJDC, entry2.getId(), returnEntry.getReturnLineEntries().get(0).getId(), RMS_STATUS.QC_DESCRIPTION, "100026329239", "BD", "ML12408712306", RMS_STATUS.QA_PERSON, "MHP", RMS_STATUS.RMS_DC, "DC");
		//Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RJDC, returnId, 10));
		
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_ONHOLD, returnId, 10));

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=33,description="RJUP to return rejected in LMS")
	public void PickeupFailedTOnHoldRejected() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 16L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPF, returnId, 10));
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPF, ReturnStatus.RJUP);
		ReturnResponse returnResponse8 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry8 = returnResponse8.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RJUP, returnId, 10));
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));

	}
	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=34,description="Open box to return not received")
	public void PickeupInitiatedToNotReceived() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		
		ReturnLineEntry entry = returnEntry2.getReturnLineEntries().get(0);
		ReturnLineResponse returnResponse5 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RPI, ReturnLineStatus.RNC, "70044366", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 1,"","","");
	
		Assert.assertEquals("Last Item in returns cannot be marked RNC. Decline the return is Not Item is recieved.", returnResponse5.getStatus().getStatusMessage());
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=35,description="Close box to return not received")
	public void PickedupToNotReceived() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPI, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPU, returnId, 10));
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		ReturnLineEntry entry = returnEntry2.getReturnLineEntries().get(0);
		Thread.sleep(4000);
		ReturnLineResponse returnResponse5 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RDU, ReturnLineStatus.RNC, "70044366", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 1,"","","");	
		Assert.assertEquals("Last Item in returns cannot be marked RNC. Decline the return is Not Item is recieved.", returnResponse5.getStatus().getStatusMessage());
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=36,description="Return details updated to return not received")
	public void DetailsUpdatedToNotReceived() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
	
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		ReturnLineResponse returnResponse5= returnHelper.returnLineStatusProcessNew(entry2.getId().toString(), ReturnLineStatus.RRQS, ReturnLineStatus.RNC, "70044366", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 1,"","","");
		
		Assert.assertEquals("Last Item in returns cannot be marked RNC. Decline the return is Not Item is recieved.", returnResponse5.getStatus().getStatusMessage());
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=37,description="Self ship to return not received")
	public void SelfShipToNotReceived() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RRQS, ReturnStatus.RDU);

		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		ReturnLineResponse returnResponse5= returnHelper.returnLineStatusProcessNew(entry2.getId().toString(), ReturnLineStatus.RDU, ReturnLineStatus.RNC, "70044366", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 1,"","","");
		
		Assert.assertEquals("Last Item in returns cannot be marked RNC. Decline the return is Not Item is recieved.", returnResponse5.getStatus().getStatusMessage());
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=38,description="Self ship to QC Pass")
	public void QualityPassToPPSRefund() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RRQS, ReturnLineStatus.RRC, entry2.getId(), returnEntry.getReturnLineEntries().get(0).getId(), "", "100026329239", "BD", "ML12408712306", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");

		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));

		ReturnLineResponse returnResponse3 = returnHelper.returnLineStatusProcessNew(entry2.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, "70044366", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 1,"Q1","","");
		ReturnLineEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RIS, returnId, 10));
		
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
        
	}
	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=39,description="Self ship to Quality level 3")
	public void QualityPassQ2ToPPSRefund() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RRQS, ReturnLineStatus.RRC, entry2.getId(), returnEntry.getReturnLineEntries().get(0).getId(), "", "100026329239", "BD", "ML12408712306", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));
		ReturnLineResponse returnResponse3 = returnHelper.returnLineStatusProcessNew(entry2.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, "70044366", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 1,"Q3","","");
		ReturnLineEntry returnEntry3 = returnResponse3.getData().get(0);

		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RIS, returnId, 10));
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		  
        Assert.assertEquals(662.77, returnEntry4.getReturnRefundDetailsEntry().getRefundAmount());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));
        System.out.println("Return PPS id : "+returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=40,description="Self ship to Supervisor pass")
	public void SupersvisorPassToPPS() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RRQS, ReturnLineStatus.RRC, entry2.getId(), returnEntry.getReturnLineEntries().get(0).getId(), "", "100026329239", "BD", "ML12408712306", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineResponse returnResponse3 = returnHelper.returnLineStatusProcessNew(entry2.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQF, "70044366", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 1,"Q3","MD","Damage");
		ReturnLineEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));
		
		ReturnLineResponse returnResponse4 = returnHelper.returnLineStatusProcessNew(entry2.getId().toString(), ReturnLineStatus.RQF, ReturnLineStatus.RQSP, "70044366", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 1,"Q1","","");
		ReturnLineEntry returnEntry4 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPC, returnId, 10));

		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));  
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnEntry5.getReturnRefundDetailsEntry().getRefunded());
        Assert.assertEquals(returnEntry.getStatusCode().RPC, returnEntry5.getStatusCode());
		Assert.assertTrue(returnEntry5.getReturnRefundDetailsEntry().getRefunded());
        Assert.assertEquals(returnEntry.getStatusCode().RPC, returnEntry5.getStatusCode());*//*
        Assert.assertEquals(662.77, returnEntry5.getReturnRefundDetailsEntry().getRefundAmount());
        Assert.assertNotNull(returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
        
        System.out.println("Return PPS id : "+returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));
	}	
	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=40,description="Self ship to CC Champion pass")
	public void CChampionPassToPPS() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
	
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RRQS, ReturnLineStatus.RRC, entry2.getId(), returnEntry.getReturnLineEntries().get(0).getId(), "", "100026329239", "BD", "ML12408712306", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineResponse returnResponse3 = returnHelper.returnLineStatusProcessNew(entry2.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQF, "70044366", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 1,"Q3","MD","Damage");
		ReturnLineEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertEquals("70044366", ""+returnEntry3.getOrderId());
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));
		
		ReturnLineResponse returnResponse4 = returnHelper.returnLineStatusProcessNew(entry2.getId().toString(), ReturnLineStatus.RQF, ReturnLineStatus.RQSF, "70044366", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 1,"Q1","","");
		ReturnLineEntry returnEntry4 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQSF, returnId, 10));

		ReturnLineResponse returnResponse6 = returnHelper.returnLineStatusProcessNew(entry2.getId().toString(), ReturnLineStatus.RQSF, ReturnLineStatus.RQCP, "70044366", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 1,"Q1","","");
		ReturnLineEntry returnEntry6 = returnResponse6.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPC, returnId, 10));

		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
        Assert.assertEquals(662.77, returnEntry5.getReturnRefundDetailsEntry().getRefundAmount());
        Assert.assertNotNull(returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
        
        System.out.println("Return PPS id : "+returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=41,description="Self ship to CC Champion at DC pass")
	public void CCPassAtDCToPPS() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		//dp.NewOrder_delete();
		//dp.NewOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RRQS, ReturnLineStatus.RJDC, entry2.getId(), returnEntry.getReturnLineEntries().get(0).getId(), RMS_STATUS.QC_DESCRIPTION, "100026329239", "BD", "ML12408712306", RMS_STATUS.QA_PERSON, "MHP", RMS_STATUS.RMS_DC, "DC");
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_ONHOLD, returnId, 10));
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RJDC, ReturnLineStatus.CPDC, entry2.getId(), returnEntry.getReturnLineEntries().get(0).getId(), RMS_STATUS.QC_DESCRIPTION, "100026329239", "BD", "ML12408712306", RMS_STATUS.QA_PERSON, "MHP", RMS_STATUS.RMS_DC, "DC");
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		
        Assert.assertEquals(662.77, returnEntry5.getReturnRefundDetailsEntry().getRefundAmount());
        Assert.assertNotNull(returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
        
        System.out.println("Return PPS id : "+returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
        
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=42,description="Open box pickup declined")
	public void PickupInitiatedToReceived() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI, ReturnStatus.RRD);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRD, returnId, 10));
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));

		
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=43,description="PurePlay Pickup initiated")
	public void PurePlay_RRQP_PickupInitiated() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPI, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		returnHelper.bulk_issueRefund(returnEntry.getId().toString(), "123", returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), 28, "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));


	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=44,description="PurePlay details updated")
	public void PurePlay_RPI_DetailsUpdated() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 15));	
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPI, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 15));

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=45,description="PurePlay details updated & issue refund")
	public void PurePlay_RDU_refund() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
	
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPI, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		
		returnHelper.bulk_issueRefund(returnEntry.getId().toString(), "123", "ML", 1,"rms_s");
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		
		boolean refundFlag=returnEntry5.getReturnRefundDetailsEntry().getRefunded();
		Assert.assertEquals(true, refundFlag);
        Assert.assertEquals(662.77, returnEntry5.getReturnRefundDetailsEntry().getRefundAmount());
        Assert.assertNotNull(returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
        
        System.out.println("Return PPS id : "+returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());

	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=46,description="PurePlay details updated")
	public void PurePlay_RDU_RRC_pass() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPI, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=47,description="PurePlay pickup to Return reshipped")
	public void PurePlay_Pickup_RSD() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		System.out.println(returnEntry.getId().toString()); 
		System.out.println(returnEntry.getComment());
		System.out.println(returnEntry.getEmail());
		System.out.println(returnEntry.getReturnTrackingDetailsEntry());
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RPI, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RDU, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));

		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RRC, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineEntry returnEntry8 = returnEntry7.getReturnLineEntries().get(0);

		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), ReturnStatus.RRS, "123", "MYQ", "ML", "555");
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRS, returnId, 10));

		Assert.assertEquals("ML", returnEntry10.getReturnAdditionalDetailsEntry().getReshipCourierService());
		Assert.assertEquals("555", returnEntry10.getReturnAdditionalDetailsEntry().getReshipTrackingNumber());
		boolean refundFlag=returnEntry10.getReturnRefundDetailsEntry().getRefunded();
        Assert.assertEquals(false, refundFlag);
        
        returnHelper.bulk_statuschange(returnEntry.getId().toString(), ReturnStatus.RSD, "123", "MYQ", "ML", "555");
		ReturnResponse returnResponse12 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry12 = returnResponse12.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RSD, returnId, 10));
		Assert.assertEquals("ML", returnEntry12.getReturnAdditionalDetailsEntry().getReshipCourierService());
		Assert.assertEquals("555", returnEntry12.getReturnAdditionalDetailsEntry().getReshipTrackingNumber());

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=48,description="PurePlay pickup to Return QC Fail")
	public void PurePlay_RDU_RRC_fail() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		System.out.println(returnEntry.getId().toString()); 
		System.out.println(returnEntry.getComment());
		System.out.println(returnEntry.getEmail());
		System.out.println(returnEntry.getReturnTrackingDetailsEntry());
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RPI, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RDU, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RRC, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineEntry returnEntry8 = returnEntry7.getReturnLineEntries().get(0);

		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=49,description="PurePlay pickup to Return reshipped")
	public void PurePlay_RQF_RRSfail() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		System.out.println(returnEntry.getId().toString()); 
		System.out.println(returnEntry.getComment());
		System.out.println(returnEntry.getEmail());
		System.out.println(returnEntry.getReturnTrackingDetailsEntry());
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RPI, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RDU, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RRC, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineEntry returnEntry8 = returnEntry7.getReturnLineEntries().get(0);

		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), ReturnStatus.RRS, "123", "MYQ");
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineEntry returnEntry11 = returnEntry10.getReturnLineEntries().get(0);

		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));


	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=50,description="PurePlay pickup to Return reshipped")
	public void PurePlay_RQP_RRSpass() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RPI, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RDU, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RRC, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),true);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineEntry returnEntry8 = returnEntry7.getReturnLineEntries().get(0);

		//Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), ReturnStatus.RRS, "123", "MYQ", "ML", "555");
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);

		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));


	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=51,description="PurePlay pickup to Return reshipped")
	public void PurePlay_RRS_RSD() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RPI, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RDU, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RRC, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineEntry returnEntry8 = returnEntry7.getReturnLineEntries().get(0);

		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), ReturnStatus.RRS, "123", "MYQ", "ML", "555");
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRS, returnId, 10));
		//Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));

		boolean refundFlag=returnEntry10.getReturnRefundDetailsEntry().getRefunded();
        Assert.assertEquals(false, refundFlag);
        
        returnHelper.bulk_statuschange(returnEntry.getId().toString(), ReturnStatus.RSD, "123", "MYQ", "ML", "555");
		ReturnResponse returnResponse12 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry12 = returnResponse12.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RSD, returnId, 10));

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=52,description="PurePlay selfship to Return details updated")
	public void PurePlay_RRQS_DetailsUpdated() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RDU, "123", "MYQ",1,"BD",false);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=53,description="PurePlay selfship to Return details updated issue refund")
	public void PurePlay_RRQS_DetailsUpdated_refund() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RDU, "123", "MYQ",1,"BD",false);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		
		returnHelper.bulk_issueRefund(returnEntry.getId().toString(), "123", "ML", 1,"rms_s");
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		boolean refundFlag=returnEntry5.getReturnRefundDetailsEntry().getRefunded();
		//Assert.assertEquals(true, refundFlag);
        Assert.assertEquals(662.77, returnEntry5.getReturnRefundDetailsEntry().getRefundAmount());
       // Assert.assertNotNull(returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
        
        System.out.println("Return PPS id : "+returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=54,description="PurePlay selfship to Return details updated QC Pass")
	public void PurePlay_SelfShip_RDU_RQP() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RDU, "123", "MYQ",1,"BD",false);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RRC, "123", "MYQ",1,"BD",true);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineEntry returnEntry8 = returnEntry7.getReturnLineEntries().get(0);

		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQP, returnId, 10));
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		boolean refundFlag=returnEntry7.getReturnRefundDetailsEntry().getRefunded();
		//Assert.assertEquals(true, refundFlag);
        Assert.assertEquals(662.77, returnEntry7.getReturnRefundDetailsEntry().getRefundAmount());
        //Assert.assertNotNull(returnEntry7.getReturnRefundDetailsEntry().getRefundPPSId());
        
        System.out.println("Return PPS id : "+returnEntry7.getReturnRefundDetailsEntry().getRefundPPSId());

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=55,description="PurePlay selfship to Return details updated QC Pass")
	public void PurePlay_SelfShip_RQP() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);

        String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RDU, "123", "MYQ",1,"BD",false);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RRC, "123", "MYQ",1,"BD",true);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineEntry returnEntry8 = returnEntry7.getReturnLineEntries().get(0);

		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQP, returnId, 10));
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		boolean refundFlag=returnEntry7.getReturnRefundDetailsEntry().getRefunded();
		//Assert.assertEquals(true, refundFlag);
        Assert.assertEquals(662.77, returnEntry7.getReturnRefundDetailsEntry().getRefundAmount());
        //Assert.assertNotNull(returnEntry7.getReturnRefundDetailsEntry().getRefundPPSId());
        
        System.out.println("Return PPS id : "+returnEntry7.getReturnRefundDetailsEntry().getRefundPPSId());

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=56,description="PurePlay selfship to Return details updated QC Fail")
	public void PurePlay_SelfShip_RQF() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
        String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RDU, "123", "MYQ",1,"BD",false);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RRC, "123", "MYQ",1,"BD",false);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineEntry returnEntry8 = returnEntry7.getReturnLineEntries().get(0);

		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));
		

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=57,description="PurePlay pickup to Reshipped")
	public void PurePlay_SelfShip_RSD() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
        String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RDU, "123", "MYQ",1,"BD",false);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RRC, "123", "MYQ",1,"BD",false);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineEntry returnEntry8 = returnEntry7.getReturnLineEntries().get(0);

		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), ReturnStatus.RRS, "123", "MYQ", "ML", "555");
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRS, returnId, 10));
		boolean refundFlag=returnEntry10.getReturnRefundDetailsEntry().getRefunded();
        Assert.assertEquals(false, refundFlag);
        
        returnHelper.bulk_statuschange(returnEntry.getId().toString(), ReturnStatus.RSD, "123", "MYQ", "ML", "555");
		ReturnResponse returnResponse12 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry12 = returnResponse12.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RSD, returnId, 10));

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=58,description="PurePlay pickup to declined")
	public void PurePlay_RRQP_Declined() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
        String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), ReturnStatus.RRD, "123", "MYQ", "ML", "555");
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRD, returnId, 10));
		
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=59,description="PurePlay selfship to declined")
	public void PurePlay_RRQS_Declined() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
        String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), ReturnStatus.RRD, "123", "MYQ", "ML", "555");
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRD, returnId, 10));
		
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=60,description="PurePlay pickup to received")
	public void PurePlay_RPI_RRC() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
        String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RPI, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RRC, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineEntry returnEntry8 = returnEntry7.getReturnLineEntries().get(0);

		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), ReturnStatus.RRS, "123", "MYQ", "ML", "555");
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRS, returnId, 10));
		boolean refundFlag=returnEntry10.getReturnRefundDetailsEntry().getRefunded();
        Assert.assertEquals(false, refundFlag);

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=61,description="PurePlay pickup to declined")
	public void PurePlay_RPI_RRD() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);

        String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RPI, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RRD, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRD, returnId, 10));


	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=62,description="PurePlay pickup to RDU declined")
	public void PurePlay_RDU_RRD() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);

        String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RPI, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RDU, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RRD, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRD, returnId, 10));
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));

		

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=63,description="PurePlay pickup to RRC declined")
	public void PurePlay_RRC_RRD() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);

        String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RPI, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RRC, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineEntry returnEntry8 = returnEntry7.getReturnLineEntries().get(0);

		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RRD, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=64,description="PurePlay pickup to RRC declined")
	public void PurePlay_RSD_RRD() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
        String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		Thread.sleep(5000);
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RPI, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RDU, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 15));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RRC, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 15));
		ReturnLineEntry returnEntry8 = returnEntry7.getReturnLineEntries().get(0);

		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), ReturnStatus.RRS, "123", "MYQ", "ML", "555");
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRS, returnId, 15));

		boolean refundFlag=returnEntry10.getReturnRefundDetailsEntry().getRefunded();
        Assert.assertEquals(false, refundFlag);
        
        returnHelper.bulk_statuschange(returnEntry.getId().toString(), ReturnStatus.RSD, "123", "MYQ", "ML", "555");
		ReturnResponse returnResponse12 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry12 = returnResponse12.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RSD, returnId, 15));
		

	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=65,description="PurePlay pickup to Return reshipped")
	public void PurePlay_RSS_RRD() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.PurePlay_CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RPI, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RDU, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
		
		returnHelper.bulk_statuschangePurePlay(returnEntry.getId().toString(), ReturnStatus.RRC, "123", "MYQ",1,returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),false);
		ReturnResponse returnResponse7 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineEntry returnEntry8 = returnEntry7.getReturnLineEntries().get(0);

		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), ReturnStatus.RRS, "123", "MYQ", "ML", "555");
		ReturnResponse returnResponse10 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry10 = returnResponse10.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRS, returnId, 10));

		boolean refundFlag=returnEntry10.getReturnRefundDetailsEntry().getRefunded();
        Assert.assertEquals(false, refundFlag);
        

	}




    @Test(priority = 10, enabled = true, groups = { "smoke", "lineCancel",
            "coupon" }, description = "Return of Price override Exchange Item. Return Refund should extract the price overide amount from total amount ")
    public void replacementcouponGenerationOnReturn() throws Exception{
        RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        End2EndHelper end2EndHelper = new End2EndHelper();

        String login = "end2endautomation1@gmail.com";
        String uidx = "8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R";

        String skuId[] = { "3832:1" };

        DBUtilities.exUpdateQuery("Delete from xcart_discount_coupons where coupon not in ('pati1','pati2','pati3') and users='"+ uidx +"';", "myntra");
        end2EndHelper.updateloyalityAndCashBack(uidx, 0, 200);
        String orderID = end2EndHelper.createOrder(login, "123456", "6118982", skuId, "pati1", true, false,false, "", false);
        omsServiceHelper.validateReleaseStatusInOMS(orderID, "WP", 10);
        String lineID = "" + omsServiceHelper.getOrderReleaseEntry(orderID).getOrderLines().get(0).getId();

        DBUtilities.exUpdateQuery("update item set item_status='SHIPPED', order_id="+ orderID +", bin_id=271521, `last_modified_on`=now() where id='91000007';", "wms");
        DBUtilities.exUpdateQuery("UPDATE order_release SET status_code = 'DL' WHERE order_id_fk="+ orderID+";","myntra_oms");
        DBUtilities.exUpdateQuery("UPDATE order_release SET delivered_on=NOW() WHERE order_id_fk="+ orderID +";","myntra_oms");
        DBUtilities.exUpdateQuery("UPDATE order_line SET status_code='D' WHERE order_id_fk="+ orderID +";","myntra_oms");

        ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineID, 1, ReturnMode.PICKUP, 27L);
        String returnId = returnResponse.getData().get(0).getId().toString();
        end2EndHelper.sleep(10000L);
        end2EndHelper.completeOrderInRMS(returnId);
        end2EndHelper.sleep(10000L);
        List coupon = DBUtilities.exSelectQuery("Select * from xcart_discount_coupons where coupon like 'CAN%' and MRPpercentage=20 and MRPAmount=159.80 and users='"+ uidx +"';", "myntra");
        Assert.assertEquals(coupon.size(), 1, "Cancellation Replacement coupon is not generated");
    }

    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=66,description="Pickup restocked")
	public void QualityPassToPPSRef() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		Assert.assertEquals("pawell.soni@myntra.com" , returnEntry2.getEmail());
		
		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RRQS, ReturnLineStatus.RRC, returnEntry2.getId(), returnEntry.getReturnLineEntries().get(0).getId(), "", "100026329239", "BD", "ML12408712306", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineResponse returnResponse3 = returnHelper.returnLineStatusProcessNew(entry2.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, "70044366", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 1,"Q1","","");
		ReturnLineEntry returnEntry3 = returnResponse3.getData().get(0);

		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPC, returnId, 10));
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		boolean refundFlag=returnEntry4.getReturnRefundDetailsEntry().getRefunded();
        Assert.assertEquals(true, refundFlag);
        Assert.assertEquals(662.77, returnEntry4.getReturnRefundDetailsEntry().getRefundAmount());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        
        System.out.println("Return PPS id : "+returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundTxRefId());
        Assert.assertNotNull(returnEntry4.getReturnAdditionalDetailsEntry().getCouponCode());
        Assert.assertNotNull(returnEntry4.getReturnAdditionalDetailsEntry().getPaymentPpsId());
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));

	}
    
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=67,description="Selfship restocked")
	public void ReceivedDCToPPSRef() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		
		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RRQS, ReturnLineStatus.RADC, entry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), RMS_STATUS.QC_DESCRIPTION, "100026329239", "BD", "ML12408712306", RMS_STATUS.QA_PERSON, "MHP", RMS_STATUS.RMS_DC, "DC");
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		 
		boolean refundFlag=returnEntry5.getReturnRefundDetailsEntry().getRefunded();
        Assert.assertEquals(true, refundFlag);
        Assert.assertNotNull(returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());

        System.out.println("Return PPS id : "+returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertNotNull(returnEntry5.getReturnRefundDetailsEntry().getRefundTxRefId());
        Assert.assertNotNull(returnEntry5.getReturnAdditionalDetailsEntry().getCouponCode());
        Assert.assertNotNull(returnEntry5.getReturnAdditionalDetailsEntry().getPaymentPpsId());
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));

	}
    
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=68,description="Selfship CC Pass")
	public void CChampionPassToPPSRef() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CODOrder_delete();
		dp.CODOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52705190", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
	
		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RRQS, ReturnLineStatus.RRC, entry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), "", "100026329239", "BD", "ML12408712306", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
		ReturnLineResponse returnResponse3 = returnHelper.returnLineStatusProcessNew(entry2.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQF, "70044366", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 1,"Q3","MD","Damage");

		ReturnLineEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQF, returnId, 10));
		
		ReturnLineResponse returnResponse4 = returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RQF, ReturnLineStatus.RQSF, entry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), RMS_STATUS.QC_DESCRIPTION, "100026329239", "BD", "ML12408712306", RMS_STATUS.QA_PERSON, "MHP", RMS_STATUS.RMS_DC, "DC");
		ReturnLineEntry returnEntry4 = returnResponse3.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RQSF, returnId, 10));
		
		ReturnLineResponse returnResponse6 = returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RQSF, ReturnLineStatus.RQCP, entry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), RMS_STATUS.QC_DESCRIPTION, "100026329239", "BD", "ML12408712306", RMS_STATUS.QA_PERSON, "MHP", RMS_STATUS.RMS_DC, "DC");
		ReturnLineEntry returnEntry6 = returnResponse6.getData().get(0);
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPC, returnId, 10));

		boolean refundFlag=returnEntry5.getReturnRefundDetailsEntry().getRefunded();
        Assert.assertEquals(true, refundFlag);
        Assert.assertEquals(662.77, returnEntry5.getReturnRefundDetailsEntry().getRefundAmount());
        Assert.assertNotNull(returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
        
        System.out.println("Return PPS id : "+returnEntry5.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertNotNull(returnEntry5.getReturnRefundDetailsEntry().getRefundTxRefId());
        Assert.assertNotNull(returnEntry5.getReturnAdditionalDetailsEntry().getCouponCode());
        Assert.assertNotNull(returnEntry5.getReturnAdditionalDetailsEntry().getPaymentPpsId());
	}

	//Unchanged
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=69,description = "Create and refund Try n Buy return")
	public void QualityPassToPPSRefundTnB() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.TnBOrder_delete();
		dp.TnBOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturnTnB("70061891", 1, ReturnMode.TRY_AND_BUY, null, 27L,RefundMode.NEFT, "ML", "123");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		System.out.println(returnEntry.getId().toString()); 
		System.out.println(returnEntry.getComment());
		System.out.println(returnEntry.getEmail());
		System.out.println(returnEntry.getReturnTrackingDetailsEntry());
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPU, returnId, 10));
		//Assert.assertEquals(ReturnMode.TRY_AND_BUY, returnEntry2.getReturnMode());
		
		Thread.sleep(16000);
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));

		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		  
		Assert.assertTrue(returnEntry4.getReturnRefundDetailsEntry().getRefunded());
		
        Assert.assertEquals(returnEntry.getStatusCode().RPU, returnEntry4.getStatusCode());
        //Assert.assertEquals(76.16, returnEntry4.getReturnRefundDetailsEntry().getRefundAmount());
        
        Assert.assertEquals(returnEntry4.getReturnMode(), returnEntry.getReturnMode().TRY_AND_BUY);
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertEquals(returnEntry4.getLogin(), "8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R");
        Assert.assertEquals(returnEntry4.getMobile(), "9823888800");
        Assert.assertEquals(returnEntry4.getEmail(), "pawell.soni@myntra.com");
        Assert.assertEquals(returnEntry4.getCustomerName().toString().trim(), "Pawell Soni" );
        Assert.assertEquals(returnEntry4.getStoreId().toString(),"1");
        Assert.assertEquals(returnEntry4.getCreatedBy().toString().trim(),"RMS-SYSTEM");
        
        ReturnLineEntry LineEntry = returnEntry4.getReturnLineEntries().get(0);
        
        //Assert.assertEquals(LineEntry.getOrderReleaseId().toString().trim(),"70061105");
        //Assert.assertEquals(LineEntry.getOrderLineId().toString().trim(),"70061891");
        //Assert.assertEquals(LineEntry.getSkuId().toString().trim(),"3831");
        //Assert.assertEquals(LineEntry.getOptionId().toString().trim(),"4852");
        //Assert.assertEquals(LineEntry.getReturnReasonId().toString().trim(),"27");
        //Assert.assertEquals(LineEntry.getQuantity().toString().trim(),"1");
        Assert.assertEquals(LineEntry.getStatusCode().toString().trim(), "RPU");
        Assert.assertEquals(LineEntry.getWarehouseId().toString().trim(),"36");
        //Assert.assertEquals(LineEntry.getSupplyType().toString().trim(), "ON_HAND");
        //Assert.assertEquals(LineEntry.getUnitPrice().toString().trim(),"799.0");
        Assert.assertEquals(LineEntry.getSellerId().toString().trim(), ""+vectorSellerID);
        //Assert.assertEquals(LineEntry.getReturnLineRefundDetailsEntry().getRefundAmount().toString().trim(),"76.16");
        Assert.assertEquals(LineEntry.getReturnLineRefundDetailsEntry().getRefunded().toString().trim(),"true");
        Assert.assertNotNull(LineEntry.getReturnLineRefundDetailsEntry().getRefundedOn());
        Assert.assertEquals(returnEntry4.getReturnAddressDetailsEntry().getAddress().toString().trim(),"Myntra Design Pvt. Ltd.");
        Assert.assertEquals(returnEntry4.getReturnAddressDetailsEntry().getCity().toString().trim(),"Bangalore");
        Assert.assertEquals(returnEntry4.getReturnAddressDetailsEntry().getState().toString().trim(),"KA");
        Assert.assertEquals(returnEntry4.getReturnAddressDetailsEntry().getCountry().toString().trim(),"India");
        Assert.assertEquals(returnEntry4.getReturnAddressDetailsEntry().getZipcode().toString().trim(),"560068");
        
        Assert.assertEquals(returnEntry4.getReturnTrackingDetailsEntry().getCourierCode().toString().trim(),"ML");
        Assert.assertEquals(returnEntry4.getReturnTrackingDetailsEntry().getTrackingNo().toString().trim(),"123");
        
        Assert.assertEquals(returnEntry4.getReturnRefundDetailsEntry().getDeliveryCredit().toString().trim(),"0.0");
        Assert.assertEquals(returnEntry4.getReturnRefundDetailsEntry().getPickupCharges().toString().trim(),"0.0");
        Assert.assertEquals(returnEntry4.getReturnRefundDetailsEntry().getRefundAmount().toString().trim(),"76.16");
        Assert.assertEquals(returnEntry4.getReturnRefundDetailsEntry().getRefunded().toString().trim(),"true");
        Assert.assertEquals(returnEntry4.getReturnRefundDetailsEntry().getRefundMode().toString().trim(),"OR");
        Assert.assertEquals(returnEntry4.getReturnRefundDetailsEntry().getRefundMode().toString().trim(),"OR");

        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundedOn());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundTxRefId());

        System.out.println("Return PPS id : "+returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        
	}
	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=70,description = "Verifies Try n Buy return is not created without courier")
	public void QualityPassRefundTnBMandatoryCourier() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.TnBOrder_delete();
		dp.TnBOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturnTnB("70061891", 1, ReturnMode.TRY_AND_BUY, null, 27L,RefundMode.NEFT, "", "123");
		
		Assert.assertEquals(returnResponse.getStatus().getStatusMessage().toString().trim(),"Missing or incorrect data : Tracking Details incomplete");
		
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=71,description = "Verifies Try n Buy return is not created without tracking number")
	public void QualityPassRefundTnBMandatoryTracking() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.TnBOrder_delete();
		dp.TnBOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturnTnB("70061891", 1, ReturnMode.TRY_AND_BUY, null, 27L,RefundMode.NEFT, "ML", "");
		
		Assert.assertEquals(returnResponse.getStatus().getStatusMessage().toString().trim(),"Missing or incorrect data : Tracking Details incomplete");
		
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=72,description = "Verifies Try n Buy return is created with mandatory warehouse")
	public void QualityPassRefundTnBMandatoryWarehouse() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.TnBOrder_delete();
		dp.TnBOrder_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturnTnB("70061891", 1, ReturnMode.TRY_AND_BUY, 36, 27L,RefundMode.NEFT, "ML", "123");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		System.out.println(returnEntry.getId().toString()); 
		System.out.println(returnEntry.getComment());
		System.out.println(returnEntry.getEmail());
		System.out.println(returnEntry.getReturnTrackingDetailsEntry());
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    //Assert.assertEquals("70061105", ""+returnEntry2.getOrderId());
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPU, returnId, 10));
		//Assert.assertEquals(ReturnMode.TRY_AND_BUY, returnEntry2.getReturnMode());
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));		
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		boolean refundFlag=returnEntry4.getReturnRefundDetailsEntry().getRefunded();
        Assert.assertEquals(true, refundFlag);
        Assert.assertEquals(returnEntry.getStatusCode().RPU, returnEntry4.getStatusCode());
        //Assert.assertEquals(76.16, returnEntry4.getReturnRefundDetailsEntry().getRefundAmount());
        
        Assert.assertEquals(returnEntry4.getReturnMode(), returnEntry.getReturnMode().TRY_AND_BUY);
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertEquals(returnEntry4.getMobile(), "9823888800");
        Assert.assertEquals(returnEntry4.getEmail(), "pawell.soni@myntra.com");
        Assert.assertEquals(returnEntry4.getCustomerName().toString().trim(), "Pawell Soni" );
        Assert.assertEquals(returnEntry4.getStoreId().toString().trim(), "1");
        Assert.assertEquals(returnEntry4.getCreatedBy().toString().trim(),"RMS-SYSTEM");
        Assert.assertEquals(LineEntry.getSkuId().toString().trim(),"3831");
        Assert.assertEquals(LineEntry.getOptionId().toString().trim(),"4852");
        Assert.assertEquals(LineEntry.getReturnReasonId().toString().trim(),"27");
        Assert.assertEquals(LineEntry.getQuantity().toString().trim(),"1");
        Assert.assertEquals(LineEntry.getStatusCode().toString().trim(), "RPU");
        Assert.assertEquals(LineEntry.getWarehouseId().toString().trim(),"36");
        Assert.assertEquals(LineEntry.getSupplyType().toString().trim(), "ON_HAND");
        Assert.assertEquals(LineEntry.getUnitPrice().toString().trim(),"799.0");
        Assert.assertEquals(LineEntry.getSellerId().toString().trim(),""+vectorSellerID);
        Assert.assertEquals(LineEntry.getReturnLineRefundDetailsEntry().getRefundAmount().toString().trim(),"76.16");
        Assert.assertEquals(LineEntry.getReturnLineRefundDetailsEntry().getRefunded().toString().trim(),"true");
        Assert.assertNotNull(LineEntry.getReturnLineRefundDetailsEntry().getRefundedOn());
        
        Assert.assertEquals(returnEntry4.getReturnAddressDetailsEntry().getAddress().toString().trim(),"Myntra Design Pvt. Ltd.");
        Assert.assertEquals(returnEntry4.getReturnAddressDetailsEntry().getCity().toString().trim(),"Bangalore");
        Assert.assertEquals(returnEntry4.getReturnAddressDetailsEntry().getState().toString().trim(),"KA");
        Assert.assertEquals(returnEntry4.getReturnAddressDetailsEntry().getCountry().toString().trim(),"India");
        Assert.assertEquals(returnEntry4.getReturnAddressDetailsEntry().getZipcode().toString().trim(),"560068");
        
        Assert.assertEquals(returnEntry4.getReturnTrackingDetailsEntry().getCourierCode().toString().trim(),"ML");
        Assert.assertEquals(returnEntry4.getReturnTrackingDetailsEntry().getTrackingNo().toString().trim(),"123");
        
        Assert.assertEquals(returnEntry4.getReturnRefundDetailsEntry().getDeliveryCredit().toString().trim(),"0.0");
        Assert.assertEquals(returnEntry4.getReturnRefundDetailsEntry().getPickupCharges().toString().trim(),"0.0");
        Assert.assertEquals(returnEntry4.getReturnRefundDetailsEntry().getRefundAmount().toString().trim(),"76.16");
        Assert.assertEquals(returnEntry4.getReturnRefundDetailsEntry().getRefunded().toString().trim(),"true");
        Assert.assertEquals(returnEntry4.getReturnRefundDetailsEntry().getRefundMode().toString().trim(),"OR");
        Assert.assertEquals(returnEntry4.getReturnRefundDetailsEntry().getRefundMode().toString().trim(),"OR");

        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundedOn());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundTxRefId());
        
        //Assert.assertEquals(returnEntry4.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse().toString().trim(),"36");
        //Assert.assertEquals(returnEntry4.getReturnAdditionalDetailsEntry().getPaymentPpsId().toString().trim(),"2ab80681-c76d-43bd-a870-10094a5ccf1e");

        System.out.println("Return PPS id : "+returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
	}
	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=73,description = "Verifies return is not received in a non ideal warehouse")
	public void NonIdealWarehouse() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		System.out.println(returnEntry.getId().toString()); 
		System.out.println(returnEntry.getComment());
		System.out.println(returnEntry.getEmail());
		System.out.println(returnEntry.getReturnTrackingDetailsEntry());
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		
		returnHelper.returnPickupProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI);
		
		ReturnLineEntry entry = returnEntry2.getReturnLineEntries().get(0);
		ReturnLineResponse returnResponse9= returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RPU, ReturnLineStatus.RRC, "70041385", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 1,"","","");
		
		Assert.assertTrue(returnResponse9.getStatus().getStatusMessage().toString().trim().endsWith("not allowed to be received in warehouse 1"));
        Assert.assertEquals(returnResponse9.getStatus().getStatusType().toString().trim(), "ERROR");
	}	
		
	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService","RMSRevamp" },priority=74,description = "Creates Open Box return")
	public void ReturnCreationOpenBox() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Thread.sleep(3000);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Tracking no : " +returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		
	}	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService","RMSRevamp" },priority=75,description = "Creates Closed Box return")
	public void ReturnCreationClosedBox() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Thread.sleep(3000);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNull(returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		
	}	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService","RMSRevamp" },priority=76,description = "Creates Selfship return")
	public void ReturnCreationSelfShip() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Thread.sleep(3000);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQS, returnId, 10));
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		
	}	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService","RMSRevamp" },priority=77,description = "Closed Box RDU refund")
	public void CloseBoxRDUrefund() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertNull(returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		Integer warehouseid = ((Number)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse()).intValue();
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), ReturnStatus.RPI, "DE12345678", "");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), ReturnStatus.RPU, "DE12345678", "");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPU, returnId, 10));
		returnHelper.bulk_issueRefund(returnEntry.getId().toString(), "", "", warehouseid,"pawell");
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));

		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertEquals(warehouseid, returnEntry3.getReturnLineEntries().get(0).getWarehouseId());
		boolean refundFlag=returnEntry3.getReturnRefundDetailsEntry().getRefunded();
		boolean refundFlagLine=returnEntry3.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefunded();
        Assert.assertEquals(true, refundFlag);
        Assert.assertEquals(true, refundFlagLine);
        
        Assert.assertNotNull(returnEntry3.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefundedOn());
        Assert.assertNotNull(returnEntry3.getReturnRefundDetailsEntry().getRefundedOn());
        Assert.assertNotNull(returnEntry3.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertNotNull(returnEntry3.getReturnRefundDetailsEntry().getRefundTxRefId());
        
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPU, returnId, 10));
        System.out.println("Return PPS id : "+returnEntry3.getReturnRefundDetailsEntry().getRefundPPSId());


	}	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService","RMSRevamp" },priority=78,description = "Closed Box RDU to RADC")
	public void CloseBoxRDUtoRADC() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertNull(returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		Integer warehouseid = ((Number)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse()).intValue();
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPI, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));

		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPU, returnId, 15));

		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 15));


		log.info("Return id : " +returnEntry2.getId());	
		
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RADC, returnEntry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), "Pass", "100026329239", "IP", "12345", "pawell", "MYQ", 23L, "DC");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RADC, returnId, 10));

		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertEquals(warehouseid, returnEntry3.getReturnLineEntries().get(0).getWarehouseId());
		boolean refundFlag=returnEntry3.getReturnRefundDetailsEntry().getRefunded();
		boolean refundFlagLine=returnEntry3.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefunded();
        Assert.assertEquals(true, refundFlag);
        Assert.assertEquals(true, refundFlagLine);
        
        Assert.assertNotNull(returnEntry3.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefundedOn());
        Assert.assertNotNull(returnEntry3.getReturnRefundDetailsEntry().getRefundedOn());
        Assert.assertNotNull(returnEntry3.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertNotNull(returnEntry3.getReturnRefundDetailsEntry().getRefundTxRefId());
        
        System.out.println("Return PPS id : "+returnEntry3.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));
        

	}	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService","RMSRevamp" },priority=79,description = "Closed Box RDU to RJDC")
	public void CloseBoxRDUtoRJDC() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		//Assert.assertNull(returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		Integer warehouseid = ((Number)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse()).intValue();
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPI, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPU, returnId, 10));
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 10));

		log.info("Return id : " +returnEntry2.getId());	
		
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RJDC, returnEntry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), "Pass", "100026329239", "DE", "12345", "pawell", "MYQ", 23L, "DC");
		
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertEquals(warehouseid, returnEntry3.getReturnLineEntries().get(0).getWarehouseId());
		boolean refundFlag=returnEntry3.getReturnRefundDetailsEntry().getRefunded();
		boolean refundFlagLine=returnEntry3.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefunded();
        Assert.assertEquals(false, refundFlag);
        Assert.assertEquals(false, refundFlagLine);
       
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_ONHOLD, returnId, 10));
        

	}	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService","RMSRevamp" },priority=80,description = "Closed Box RDU to RJDC")
	public void CloseBoxRDUtoCPDC() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertNull(returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		Integer warehouseid = ((Number)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse()).intValue();
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPI, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 15));
        Thread.sleep(5000);
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
        Thread.sleep(5000);

		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPU, returnId, 15));

		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RDU, returnId, 15));


		log.info("Return id : " +returnEntry2.getId());	
		
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RJDC, returnEntry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), "Pass", "100026329239", "ML", "12345", "pawell", "MYQ", 23L, "DC");
		
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertEquals(warehouseid, returnEntry3.getReturnLineEntries().get(0).getWarehouseId());
		boolean refundFlag=returnEntry3.getReturnRefundDetailsEntry().getRefunded();
		boolean refundFlagLine=returnEntry3.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefunded();
        Assert.assertEquals(false, refundFlag);
        Assert.assertEquals(false, refundFlagLine);
       
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_ONHOLD, returnId, 10));
        returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RJDC, ReturnLineStatus.CPDC, returnEntry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), "Pass", "100026329239", "ML", "12345", "pawell", "MYQ", 23L, "DC");
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		
		Assert.assertEquals(warehouseid, returnEntry4.getReturnLineEntries().get(0).getWarehouseId());
		boolean refundFlag2=returnEntry4.getReturnRefundDetailsEntry().getRefunded();
		boolean refundFlagLine2=returnEntry4.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefunded();
        Assert.assertEquals(true, refundFlag2);
        Assert.assertEquals(true, refundFlagLine2);
        
        Assert.assertNotNull(returnEntry4.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefundedOn());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundedOn());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundTxRefId());
        
        System.out.println("Return PPS id : "+returnEntry3.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));

	}	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService","RMSRevamp" },priority=81,description = "Closed Box RDU to CFDC")
	public void CloseBoxRDUtoCFDC() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertNull(returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		Integer warehouseid = ((Number)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse()).intValue();
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPI, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");

		log.info("Return id : " +returnEntry2.getId());	
		
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RJDC, returnEntry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), "Pass", "100026329239", "ML", "12345", "pawell", "MYQ", 23L, "DC");
		
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertEquals(warehouseid, returnEntry3.getReturnLineEntries().get(0).getWarehouseId());
		boolean refundFlag=returnEntry3.getReturnRefundDetailsEntry().getRefunded();
		boolean refundFlagLine=returnEntry3.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefunded();
        Assert.assertEquals(false, refundFlag);
        Assert.assertEquals(false, refundFlagLine);
       
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_ONHOLD, returnId, 10));
        returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RJDC, ReturnLineStatus.CFDC, returnEntry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), "Pass", "100026329239", "ML", "12345", "pawell", "MYQ", 23L, "DC");
        
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));

	}	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService","RMSRevamp" },priority=82,description = "Closed Box Received")
	public void CloseBoxRRC() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		//Assert.assertNull(returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo());
		//Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		Integer warehouseid = ((Number)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse()).intValue();
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPI, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPU, returnId, 10));
		log.info("Return id : " +returnEntry2.getId());	
		
		//returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RJDC, returnEntry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), "Pass", "100026329239", "ML", "12345", "pawell", "MYQ", 23L, "DC");
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RPU, ReturnLineStatus.RRC, returnEntry2.getId(), returnEntry.getReturnLineEntries().get(0).getId(), "", "100026329239", "BD", "ML12408712306", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));

		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertEquals(warehouseid, returnEntry3.getReturnLineEntries().get(0).getWarehouseId());
		
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.PICKUP_SUCCESS, returnId, 10));
       

	}	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService","RMSRevamp" },priority=83,description = "Open Box declined")
	public void ReturnOpenBoxtoRRD() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Tracking no : " +returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI, ReturnStatus.RRD);
        Assert.assertTrue(returnHelper.getReturnStatusfromLMS(LMS_STATUS.RETURN_REJECTED, returnId, 10));
		
	}	
	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=84,description="Return is picked up and marked QC Pass")
	public void VerifyCourierDrop() throws JAXBException, IOException, SQLException, InterruptedException {
		
		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();
		
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "BD");

		ReturnEntry returnEntry = returnResponse.getData().get(0);
		String returnId = returnEntry.getId().toString().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Assert.assertEquals("70041385", ""+returnEntry2.getOrderId());
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		
		returnHelper.returnPickupProcessNew(returnEntry.getId().toString().toString(), ReturnStatus.RPI);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RPU, returnId, 10));

		ReturnLineEntry entry = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RPU, ReturnLineStatus.RRC, entry.getId(), returnEntry.getReturnLineEntries().get(0).getId(), "", "100026329239", "", "", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRC, returnId, 10));

		ReturnLineResponse returnResponse3 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, "70041385", returnEntry.getId().toString(), "100026329239", "", RMS_STATUS.QA_PERSON, 1,"Q1",RMS_STATUS.QC_REASON,RMS_STATUS.QC_DESCRIPTION);
		ReturnLineEntry returnEntry3 = returnResponse3.getData().get(0);

		Assert.assertEquals("70041385", ""+returnEntry3.getOrderId());
		Assert.assertTrue(returnHelper.WaitReturnLineStatus(ReturnLineStatus.RIS, returnId, 10));
		Assert.assertEquals(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode().toString(),"ML");
		Assert.assertTrue(returnHelper.WaitRefundStatus(returnId, 10));
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		 
		Assert.assertTrue(returnEntry4.getReturnRefundDetailsEntry().getRefunded());

        Assert.assertEquals(RMS_STATUS.QC_REASON, returnEntry4.getReturnLineEntries().get(0).getReturnLineAdditionalDetailsEntry().getQaFailReason());
        Assert.assertEquals(RMS_STATUS.QC_DESCRIPTION, returnEntry4.getReturnLineEntries().get(0).getReturnLineAdditionalDetailsEntry().getQcDescription());
        Assert.assertEquals(RMS_STATUS.QA_PERSON, returnEntry4.getReturnLineEntries().get(0).getReturnLineAdditionalDetailsEntry().getQaPerson());

        System.out.println("Return PPS id : "+returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        
	}	
	*/
    
	/*@Test(enabled = true,groups={"regression"}, description = "Cancel Release order in PK state")
	public void recieveShipmentAfterRTO() throws Exception {
		ProcessRelease processRelease = new ProcessRelease();
    	sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		//  login = "end2endautomation4@gmail.com"; password="myntra@123";
		orderID = end2EndHelper.createOrder(login, password2, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);

		End2EndHelper.sleep(10000L);

		processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.RTO).build());
		orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "RTO", 10);
		sft.assertTrue(orderStatusPK, "Order Status is not in RTO");
		//Verify status in LMS
		verifyStatusInLMS(orderReleaseId,"RTO");
		
		RtoResponse response = rmsServiceHelper.recieveShipmentInRejoy(orderReleaseId, 36);
		//  Assert.assertTrue(response.getStatus().getStatusCode(),, "Order Status is not in RTO");
		sft.assertEquals(response.getStatus().getStatusType(),Type.SUCCESS, "Order Status is not in RTO");

		verifyStatusInLMS(orderReleaseId,"RTO_R");
		//Validate item status
		validateItemStatusInWMS(OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1, orderReleaseId, "ACCEPTED_RETURNS");
		//Restock Item in RMS
		HashMap<String, Object> itemDetailInWMS = (HashMap<String, Object>) 
				DBUtilities.exSelectQueryForSingleRecord("select * from item where order_id ="+orderReleaseId, "wms");
		String barcode = (String) itemDetailInWMS.get("barcode");
		
		response = rmsServiceHelper.reStockItemInRejoy(barcode, 36);
		sft.assertEquals(response.getStatus().getStatusType(),Type.SUCCESS, "Restock didn't happen");
		itemDetailInWMS = (HashMap<String, Object>) 
				DBUtilities.exSelectQueryForSingleRecord("select * from item where barcode ="+barcode, "wms");
		String itemStatus = (String) itemDetailInWMS.get("item_status");
		sft.assertEquals("CUSTOMER_RETURNED", itemStatus,"Item status is not CUSTOMER_RETURNED after RTO recieve");
		sft.assertAll();
	}
*/	
	
/**
     * This function is to validate status in LMS
     * @param orderReleaseId
     * @param status
     *//*

    public void verifyStatusInLMS(String orderReleaseId,String status){
			HashMap<String, Object> orderToShip = (HashMap<String, Object>) 
					DBUtilities.exSelectQueryForSingleRecord("select * from order_to_ship where order_id = "+orderReleaseId, "lms");
			lmsStatus = (String) orderToShip.get("status");
			sft.assertEquals(lmsStatus, status,"Release is not in "+status+" status");
      }
    
    public void validateItemStatusInWMS(String skuId,String orderId,String expectedStatus){
		HashMap<String, Object> itemDetailInWMS = (HashMap<String, Object>) 
				DBUtilities.exSelectQueryForSingleRecord("select * from item where order_id ="+orderId+" and sku_id='"+skuId+"'", "wms");
		String itemStatus = (String) itemDetailInWMS.get("item_status");
		sft.assertEquals(expectedStatus, itemStatus,"Item status is not "+expectedStatus+" after RTO recieve");
	}
    
	*/
/*@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=1,description="Return is picked up and marked QC Pass")
	public void DeleteLater() throws JAXBException, IOException, SQLException, InterruptedException {
		RMSServiceHelper returnHelper = new RMSServiceHelper();
	     returnHelper.ProcessReturnRevamped(12354824L);
	}	*//*

    
    /*------------------------------------------------------------------------------------------------- */
    //Revamped RMS
    
    
    
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },priority=1,description="Return creation")
	public void ReturnCreation_OpenBox() throws Exception {
		
        String releaseId = omsServiceHelper.getReleaseId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
	    OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    	
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.OPEN_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418");
	
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		String returnId = returnEntry.getId().toString();
		log.info("\n Return id :" +returnId);
		
		ReturnResponse returnResponse2 = rmsServiceHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
		softAssert.assertEquals(releaseEntry.getOrderId(), returnEntry2.getOrderId());	
		softAssert.assertEquals(releaseEntry.getStoreOrderId(), returnEntry2.getStoreOrderId());	
		softAssert.assertEquals(releaseEntry.getLogin(), returnEntry2.getLogin());	
		softAssert.assertEquals(returnEntry2.getStatusCode(), ReturnStatus.LPI);	
		softAssert.assertEquals(releaseEntry.getEmail(), returnEntry2.getEmail());	
		softAssert.assertEquals(releaseEntry.getMobile(), returnEntry2.getMobile());	
		softAssert.assertEquals(releaseEntry.getAddress(), returnEntry2.getReturnAddressDetailsEntry().getAddress());
		softAssert.assertEquals(releaseEntry.getCity(), returnEntry2.getReturnAddressDetailsEntry().getCity());
		softAssert.assertEquals(releaseEntry.getCountry(), returnEntry2.getReturnAddressDetailsEntry().getCountry());
		softAssert.assertEquals(releaseEntry.getState(), returnEntry2.getReturnAddressDetailsEntry().getState());
		softAssert.assertEquals(releaseEntry.getZipcode(), returnEntry2.getReturnAddressDetailsEntry().getZipcode());	
		softAssert.assertEquals(releaseEntry.getAddressId(), returnEntry2.getReturnAddressDetailsEntry().getAddressId());	
		softAssert.assertEquals(returnEntry2.getReturnRefundDetailsEntry().getRefundMode(), RefundMode.NEFT);	
		softAssert.assertEquals(returnEntry2.getReturnRefundDetailsEntry().getRefundAccountId(), "418");	
		softAssert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo(),"Tracking number missing :");
		softAssert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(),"Courier code missing :");
		softAssert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getLogisticsProcessingInitOn());
		softAssert.assertEquals(returnEntry2.getReturnMode(), ReturnMode.OPEN_BOX_PICKUP);	
		softAssert.assertEquals(returnEntry2.getReturnType(), ReturnType.NORMAL);	
		softAssert.assertNotNull(returnEntry2.getCreatedBy());
		softAssert.assertNotNull(returnEntry2.getCreatedOn());
		softAssert.assertEquals(releaseEntry.getStoreOrderId(), returnEntry2.getStoreOrderId());	

		softAssert.assertEquals(releaseEntry.getOrderId(), returnEntry2.getReturnLineEntries().get(0).getOrderId());
		softAssert.assertEquals(releaseEntry.getId(), returnEntry2.getReturnLineEntries().get(0).getOrderReleaseId());	
		softAssert.assertEquals(releaseEntry.getOrderLines().get(0).getId(), returnEntry2.getReturnLineEntries().get(0).getOrderLineId());
		softAssert.assertEquals(releaseEntry.getOrderLines().get(0).getSkuId(), returnEntry2.getReturnLineEntries().get(0).getSkuId());
		softAssert.assertEquals(releaseEntry.getOrderLines().get(0).getOptionId(), returnEntry2.getReturnLineEntries().get(0).getOptionId());
		softAssert.assertFalse(returnEntry2.getReturnRefundDetailsEntry().getRefunded());
		//softAssert.assertNotNull(returnEntry2.getReturnLineEntries().get(0).getItemBarcode(),"Barcode missing :");
		softAssert.assertEquals(releaseEntry.getWarehouseId(), returnEntry2.getReturnLineEntries().get(0).getWarehouseId());
		softAssert.assertEquals(releaseEntry.getOrderLines().get(0).getStyleId(), returnEntry2.getReturnLineEntries().get(0).getStyleId());
		softAssert.assertEquals(releaseEntry.getOrderLines().get(0).getSellerId(), returnEntry2.getReturnLineEntries().get(0).getSellerId());
		softAssert.assertAll();
	}	
    
    
	
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },description="Declining return")
	public void OpenBox_LPI_to_Decline() throws Exception {
		
        String releaseId = omsServiceHelper.getReleaseId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
	    OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    	
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.OPEN_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418");
	
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		String returnId = returnEntry.getId().toString();
		log.info("\n Return id :" +returnId);
		
		rmsServiceHelper.returnDecline(Long.valueOf(returnId).longValue(), ReturnActionCode.DECLINE_RETURN, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		
		ReturnResponse returnResponse2 = rmsServiceHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);

		rmsServiceHelper.validateReturnStatusInRMS(returnId, "DEC", 5);
		softAssert.assertFalse(returnEntry2.getReturnRefundDetailsEntry().getRefunded(), "Checking Refund Status");
		softAssert.assertAll();

	}	
	
    
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },description="Return receive")
	public void OpenBox_LPI_to_RL() throws Exception {
		
        String releaseId = omsServiceHelper.getReleaseId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
	    OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    	
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.OPEN_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418");
	
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		String returnId = returnEntry.getId().toString();
		log.info("\n Return id :" +returnId);
		
		rmsServiceHelper.returnReceive(Long.valueOf(returnId).longValue(), ReturnActionCode.RECEIVE_AT_LOGISTICS, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		
		ReturnResponse returnResponse2 = rmsServiceHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);

		softAssert.assertEquals(returnEntry2.getStatusCode(),ReturnStatus.RL);
		softAssert.assertFalse(returnEntry2.getReturnRefundDetailsEntry().getRefunded(), "Checking Refund Status");
		softAssert.assertAll();

	}	
	
    
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },description="Initiate reship")
	public void OpenBox_RL_to_RTR() throws Exception {
		
        String releaseId = omsServiceHelper.getReleaseId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
	    OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    	
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.OPEN_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418");
	
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		String returnId = returnEntry.getId().toString();
		log.info("\n Return id :" +returnId);
		
		rmsServiceHelper.returnReceive(Long.valueOf(returnId).longValue(), ReturnActionCode.RECEIVE_AT_LOGISTICS, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		rmsServiceHelper.returnReadyToReship(Long.valueOf(returnId).longValue(), ReturnActionCode.INITIATE_RESHIP_TO_CUSTOMER, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		
		ReturnResponse returnResponse2 = rmsServiceHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);

		softAssert.assertEquals(returnEntry2.getStatusCode(),ReturnStatus.RRSH);
		softAssert.assertFalse(returnEntry2.getReturnRefundDetailsEntry().getRefunded(), "Checking Refund Status");
		softAssert.assertAll();

	}	
    
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },description="Ship to Customer")
	public void OpenBox_RTR_to_SHC() throws Exception {
		
        String releaseId = omsServiceHelper.getReleaseId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
	    OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    	
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.OPEN_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418");
	
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		String returnId = returnEntry.getId().toString();
		log.info("\n Return id :" +returnId);
		
		rmsServiceHelper.returnReceive(Long.valueOf(returnId).longValue(), ReturnActionCode.RECEIVE_AT_LOGISTICS, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		rmsServiceHelper.returnReadyToReship(Long.valueOf(returnId).longValue(), ReturnActionCode.INITIATE_RESHIP_TO_CUSTOMER, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		rmsServiceHelper.returnShipToCustomer(Long.valueOf(returnId).longValue(), ReturnActionCode.SHIP_TO_CUSTOMER, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());

		ReturnResponse returnResponse2 = rmsServiceHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);

		softAssert.assertEquals(returnEntry2.getStatusCode(),ReturnStatus.SHC);
		softAssert.assertFalse(returnEntry2.getReturnRefundDetailsEntry().getRefunded(), "Checking Refund Status");
		softAssert.assertAll();

	}	
	
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },description="Delivered to Customer")
	public void OpenBox_SHC_to_DLC() throws Exception {
		
        String releaseId = omsServiceHelper.getReleaseId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
	    OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    	
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.OPEN_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418");
	
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		String returnId = returnEntry.getId().toString();
		log.info("\n Return id :" +returnId);
		
		
		rmsServiceHelper.returnReceive(Long.valueOf(returnId).longValue(), ReturnActionCode.RECEIVE_AT_LOGISTICS, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		rmsServiceHelper.returnReadyToReship(Long.valueOf(returnId).longValue(), ReturnActionCode.INITIATE_RESHIP_TO_CUSTOMER, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		rmsServiceHelper.returnShipToCustomer(Long.valueOf(returnId).longValue(), ReturnActionCode.SHIP_TO_CUSTOMER, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		rmsServiceHelper.returnCompleteLogisticsProcessing(Long.valueOf(returnId).longValue(), ReturnActionCode.COMPLETE_LOGISTICS_PROCESSING, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		
		ReturnResponse returnResponse2 = rmsServiceHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);

		softAssert.assertEquals(returnEntry2.getStatusCode(),ReturnStatus.DLC);
		softAssert.assertFalse(returnEntry2.getReturnRefundDetailsEntry().getRefunded(), "Checking Refund Status");
		softAssert.assertAll();

	}	
	
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },description="Lost after Receive")
	public void OpenBox_RTR_to_Lost() throws Exception {
		
        String releaseId = omsServiceHelper.getReleaseId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
	    OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    	
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.OPEN_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418");
	
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		String returnId = returnEntry.getId().toString();
		log.info("\n Return id :" +returnId);
		
		
		rmsServiceHelper.returnReceive(Long.valueOf(returnId).longValue(), ReturnActionCode.RECEIVE_AT_LOGISTICS, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		rmsServiceHelper.returnLost(Long.valueOf(returnId).longValue(), ReturnActionCode.LOST, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());

		ReturnResponse returnResponse2 = rmsServiceHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);

		softAssert.assertEquals(returnEntry2.getStatusCode(),ReturnStatus.L);
		softAssert.assertFalse(returnEntry2.getReturnRefundDetailsEntry().getRefunded(), "Checking Refund Status");
		softAssert.assertAll();

	}	
    
    
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },description="Lost post initiating reship")
	public void OpenBox_RL_to_Lost() throws Exception {
		
        String releaseId = omsServiceHelper.getReleaseId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
	    OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    	
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.OPEN_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418");
	
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		String returnId = returnEntry.getId().toString();
		log.info("\n Return id :" +returnId);
		
		
		rmsServiceHelper.returnReceive(Long.valueOf(returnId).longValue(), ReturnActionCode.RECEIVE_AT_LOGISTICS, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		rmsServiceHelper.returnReadyToReship(Long.valueOf(returnId).longValue(), ReturnActionCode.INITIATE_RESHIP_TO_CUSTOMER, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		rmsServiceHelper.returnLost(Long.valueOf(returnId).longValue(), ReturnActionCode.LOST, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());

		ReturnResponse returnResponse2 = rmsServiceHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);

		softAssert.assertEquals(returnEntry2.getStatusCode(),ReturnStatus.L);
		softAssert.assertFalse(returnEntry2.getReturnRefundDetailsEntry().getRefunded(), "Checking Refund Status");
		softAssert.assertAll();

	}	
    
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },description="Lost post Shipping to customer")
	public void OpenBox_SHC_to_Lost() throws Exception {
		
        String releaseId = omsServiceHelper.getReleaseId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
	    OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    	
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.OPEN_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418");
	
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		String returnId = returnEntry.getId().toString();
		log.info("\n Return id :" +returnId);
		
		rmsServiceHelper.returnReceive(Long.valueOf(returnId).longValue(), ReturnActionCode.RECEIVE_AT_LOGISTICS, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		rmsServiceHelper.returnReadyToReship(Long.valueOf(returnId).longValue(), ReturnActionCode.INITIATE_RESHIP_TO_CUSTOMER, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		rmsServiceHelper.returnShipToCustomer(Long.valueOf(returnId).longValue(), ReturnActionCode.SHIP_TO_CUSTOMER, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		rmsServiceHelper.returnLost(Long.valueOf(returnId).longValue(), ReturnActionCode.LOST, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());

		ReturnResponse returnResponse2 = rmsServiceHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);

		softAssert.assertEquals(returnEntry2.getStatusCode(),ReturnStatus.L);
		softAssert.assertFalse(returnEntry2.getReturnRefundDetailsEntry().getRefunded(), "Checking Refund Status");
		softAssert.assertAll();

	}	
    
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression","RMSService" },description="Received and Shipped to Seller (Auto-transition to DLS)")
	public void OpenBox_RL_to_SHS() throws Exception {
		
    	String releaseId = omsServiceHelper.getReleaseId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
	    OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    	
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.OPEN_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418");
	
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		String returnId = returnEntry.getId().toString();
		log.info("\n Return id :" +returnId);
		
		
		rmsServiceHelper.returnReceive(Long.valueOf(returnId).longValue(), ReturnActionCode.RECEIVE_AT_LOGISTICS, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		rmsServiceHelper.returnReceive(Long.valueOf(returnId).longValue(), ReturnActionCode.SHIP_TO_SELLER, returnEntry.getReturnLineEntries().get(0).getSkuId(), returnEntry.getReturnLineEntries().get(0).getQuantity());
		
		ReturnResponse returnResponse2 = rmsServiceHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);

		softAssert.assertEquals(returnEntry2.getStatusCode(),ReturnStatus.DLS);
		softAssert.assertFalse(returnEntry2.getReturnRefundDetailsEntry().getRefunded(), "Checking Refund Status");
		softAssert.assertAll();


	}	
    
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
 			"MiniRegression", "ExhaustiveRegression","RMSService" },description="Return pickup and refund")
 	public void OpenBoxRestockMock() throws Exception {
 		
         String releaseId = omsServiceHelper.getReleaseId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
         OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
 	    OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
     	
 		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.OPEN_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418");
 	
 		ReturnEntry returnEntry = returnResponse.getData().get(0);
 		String returnId = returnEntry.getId().toString();
 		log.info("\n Return id :" +returnId);
 		
 		ReturnResponse returnResponse2 = rmsServiceHelper.getReturnDetailsNew(returnId);
 		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
 		
 		lmsReturnHelper.processOpenBoxReturn(returnId.toString(), EnumSCM.PICKED_UP_SUCCESSFULLY);
 		
		rmsServiceHelper.returnLineStatusProcessNew(returnEntry.getReturnLineEntries().get(0).getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, lineEntry.getId().toString(), returnEntry.getId().toString(), returnEntry2.getReturnLineEntries().get(0).getItemBarcode(), returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo(), "Pawell", returnEntry2.getReturnLineEntries().get(0).getWarehouseId(),"Q1","","");
		ReturnResponse returnResponse3 = rmsServiceHelper.getReturnDetailsNew(returnId);
 		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
 		
		rmsServiceHelper.WaitRefundStatus(returnId, 5);
		softAssert.assertEquals(returnEntry3.getStatusCode(),ReturnStatus.RPC);
		softAssert.assertEquals(returnEntry3.getReturnLineEntries().get(0).getStatusCode(),ReturnLineStatus.RIS);
		
		softAssert.assertAll();
 	}	
    
    
}

