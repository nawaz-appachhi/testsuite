package com.myntra.apiTests.erpservices.ims.Tests;

import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.wms.WMSHelper;
import com.myntra.apiTests.erpservices.wms.dp.WMSPipelineDP;
import com.myntra.client.wms.codes.utils.ItemStatus;
import com.myntra.client.wms.response.ItemResponse;
import com.myntra.ims.client.response.CoreInventoryCountResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Sneha.Agarwal on 07/02/18.
 */
public class ItemStateTransitionStates extends BaseTest {



     IMSServiceHelper imsServiceHelper = new IMSServiceHelper();

     Initialize init = new Initialize("/Data/configuration");
     Logger log = Logger.getLogger(ItemStateTransitionStates.class);
     WMSHelper WMSItemTransitionHelper1 = new WMSHelper();
    private  String ITEM_TRAN_SKU = "6258";
    private  String Seller_id = "21";
    private  String Store_id = "1";
    private  String wh_id = "1";
    SoftAssert sft;
    String default_owner_partner_id="2297";



    // return order
    // Add a RO for INWARD_REJECTS
    // Add a RO for CUSTOMER_RETURNS
    // Add a RO for OUTWARD_REJECTS
    // Add a RO for STOCK_CORRECTION
    // Add a item for INWARD_REJECTS
    // Add a item for CUSTOMER_RETURNS
    // Add a item for OUTWARD_REJECTS
    // Add a item for STOCK_CORRECTION
    // Sent for Approval
    // Approve the RO
    //

    // 2.accepted return to not_found
    @Test(groups = { "Regression" }, dataProvider = "acceptedReturnToNotFound", dataProviderClass = WMSPipelineDP.class)
    public void acceptedReturnToNotFound(String item_id, ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {
        sft=new SoftAssert();
       
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        int b_p_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(0);
        b_p_cb--;
        int b_mi_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(1);
        b_mi_cb++;
        int b_ma_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(2);
        int inv_count_b = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int inv_count_a = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(inv_count_a, inv_count_b, "core_inventory inventory count");
         sft.assertAll();

    }

    // 3.accepted return to shipped
    @Test(groups = { "Regression" }, dataProvider = "acceptedReturnToShipped", dataProviderClass = WMSPipelineDP.class)
    public void acceptedReturnToShipped(String item_id, ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft=new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        int b_p_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(0);
        b_p_cb--;
        int b_mi_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(1);
        int b_ma_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(2);
        int inv_count_b = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
        inv_count_b--;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int inv_count_a = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(inv_count_a, inv_count_b, "core_inventory inventory count");
        sft.assertAll();

    }

    // 4.customer_return to stored
    @Test(groups = {
            "Regression" }, dataProvider = "customer_ReturnToStored", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public void customer_ReturnToStored(String item_id, ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {
        sft=new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        wh_invcountb++;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        atp_invcountb++;
        int b_p_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(0);
        b_p_cb--;
        int b_mi_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(1);
        int b_ma_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(2);
        int inv_count_b = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        Thread.sleep(1000);
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int inv_count_a = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(inv_count_a, inv_count_b, "core_inventory inventory count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
        sft.assertAll();

    }

    // 5.customer_returned to not_found
    @Test(groups = {
            "Regression" }, dataProvider = "customer_ReturnToNotFound", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public void customer_ReturnToNotFound(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {
        sft=new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("1", "1", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        b_p_cb--;
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        b_mi_cb++;
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
        sft.assertAll();
        //  sft.assertEquals(inv_count_a, inv_count_b,
        // "core_inventory inventory count");

    }

    // 6.detached to accepted_return
	/*	@Test(groups = {
				"Regression" }, dataProvider = "detached_to_accepted_return", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
		public  void detached_to_accepted_return(String item_id,  ItemStatus status, String quality, String response)
				throws InterruptedException, UnsupportedEncodingException, JAXBException {
			int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
			int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
			int b_p_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(0);
			b_p_cb++;
			int b_mi_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(1);
			int b_ma_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(2);
			int inv_count_b = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
			inv_count_b++;
			ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
					quality,"0");

			log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
			int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
			int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
			int b_p_ca = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(0);
			int b_mi_ca = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(1);
			int b_ma_ca = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(2);
			int inv_count_a = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
			 sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
			 sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
			 sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
			 sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
			 sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
			 sft.assertEquals(inv_count_a, inv_count_b, "core_inventory inventory count");

		}
	*/
		/*
		 * //7.detached to processing
		 *
		 * @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
		 * "MiniRegression", "ExhaustiveRegression" }, dataProvider =
		 * "detached_to_processing") public  void
		 * detached_to_processing(String item_id ,String status ,String
		 * quality,String response) { int
		 * wh_invcountb=getwh_inv_details(ITEM_TRAN_SKU,"1",Seller_id,"1"); int
		 * atp_invcountb=getatp_inv_details(ITEM_TRAN_SKU,Seller_id,"1"); int b_p_cb
		 * =getcore_inv_count_details(ITEM_TRAN_SKU,"1").get(0); int
		 * b_mi_cb=getcore_inv_count_details(ITEM_TRAN_SKU,"1").get(1); int
		 * b_ma_cb=getcore_inv_count_details(ITEM_TRAN_SKU,"1").get(2); RequestGenerator
		 * WMSbulkupdateitem =
		 * WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id,
		 * status,quality); String getOrderResponse = WMSbulkupdateitem.respvalidate
		 * .returnresponseasstring();log.info(
		 * "\nPrinting WMSbulupdateitem API response :\n\n" + getOrderResponse +
		 * "\n"); log.info("\nPrinting WMSbulupdateitem  API response :\n\n" +
		 * getOrderResponse + "\n");
		 *
		 * AssertJUnit .assertEquals("WMSbulupdateitem  API is not working",
		 * Integer.parseInt(response), WMSbulkupdateitem.response.getStatus()); int
		 * wh_invcounta=getwh_inv_details(ITEM_TRAN_SKU,"1",Seller_id,"1"); int
		 * atp_invcounta=getatp_inv_details(ITEM_TRAN_SKU,Seller_id,"1"); int b_p_ca
		 * =getcore_inv_count_details(ITEM_TRAN_SKU,"1").get(0); int
		 * b_mi_ca=getcore_inv_count_details(ITEM_TRAN_SKU,"1").get(1); int
		 * b_ma_ca=getcore_inv_count_details(ITEM_TRAN_SKU,"1").get(2);
		 *  sft.assertEquals(b_p_ca, b_p_cb);  sft.assertEquals(b_ma_ca,
		 * b_ma_cb);  sft.assertEquals(b_mi_ca, b_mi_cb);
		 *  sft.assertEquals(atp_invcounta, atp_invcountb);
		 *  sft.assertEquals(wh_invcounta, wh_invcountb);
		 *
		 *
		 * }
		 */
    // 8.detached to stored
    @Test(groups = {
            "Regression" }, dataProvider = "detached_to_stored", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public void detached_to_stored(String item_id, ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {
        sft = new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        wh_invcountb++;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        atp_invcountb++;
        int b_p_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(0);
        int b_mi_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(1);
        int b_ma_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(2);
        int core_inv_countb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
        core_inv_countb++;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }

    // 9.found to stored
    @Test(groups = {
            "Regression" }, dataProvider = "found_to_stored", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void found_to_stored(String item_id, ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException, UnsupportedEncodingException, JAXBException {
        sft = new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        wh_invcountb++;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        atp_invcountb++;
        int b_p_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(0);
        int b_mi_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(1);
        b_mi_cb--;
        int b_ma_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(2);
        int core_inv_countb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");

        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta = coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }


    // 11.issued to shipped

		  @Test(groups = { "Regression" }, dataProvider = "issued_to_shipped",
		  dataProviderClass = WMSPipelineDP.class, alwaysRun = true) public
		  void issued_to_shipped(String item_id,  ItemStatus status, String quality,
		 String response) throws InterruptedException, UnsupportedEncodingException, JAXBException {
              sft = new SoftAssert();

         int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU,"1",Seller_id,"1");
		  wh_invcountb--; int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU,Seller_id,"1");
		  atp_invcountb--; int b_p_cb =
		  getcore_inv_count_details(ITEM_TRAN_SKU,"1").get(0); int b_mi_cb =
		  getcore_inv_count_details(ITEM_TRAN_SKU,"1").get(1); int b_ma_cb =
		  getcore_inv_count_details(ITEM_TRAN_SKU,"1").get(2); int core_inv_countb =
		 getcore_inv_count_details(ITEM_TRAN_SKU,"1").get(3); core_inv_countb--;
		 RequestGenerator imsSyncInv = imsServiceHelper
                      .invokeIMSSyncinventoryAPI("1", "1", Seller_id,
                              "ON_HAND", ITEM_TRAN_SKU, null, "SHIPPED",
                              "1");
		  int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU,"1",Seller_id,"1");
		  int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU,Seller_id,"1");
		  int b_p_ca = getcore_inv_count_details(ITEM_TRAN_SKU,"1").get(0);
		  int b_mi_ca = getcore_inv_count_details(ITEM_TRAN_SKU,"1").get(1);
		  int b_ma_ca = getcore_inv_count_details(ITEM_TRAN_SKU,"1").get(2);
		  int core_inv_counta = getcore_inv_count_details(ITEM_TRAN_SKU,"1").get(3);
		  sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
		  sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
		  sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
		  sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
		  sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
		  sft.assertEquals(core_inv_counta, core_inv_countb,
		  "core_inventory inventory count");
		  sft.assertAll();

		  }


    // 12.issued to not found
    @Test(groups = {
            "Regression" }, dataProvider = "issued_to_notfound", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public void issued_to_notfound(String item_id, ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {
        sft = new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        wh_invcountb--;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        atp_invcountb--;

        int b_p_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(0);
        int b_mi_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(1);
        b_mi_cb++;
        int b_ma_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(2);
        int core_inv_countb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);

        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }

    // 13.issued to rfo
		@Test(groups = {
				"Regression" }, dataProvider = "issued_to_rfo", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
		public  void issued_to_rfo(String item_id, ItemStatus status, String quality, String response)
				throws InterruptedException, UnsupportedEncodingException, JAXBException {
            sft = new SoftAssert();

			int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
			wh_invcountb--;
			int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
			atp_invcountb--;
			int b_p_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(0);
			b_p_cb++;
			int b_mi_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(1);
			int b_ma_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(2);
			int core_inv_countb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);

			ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
					quality,"0");

			log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
			int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
			int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
			CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
					.searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
			coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
			int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
		    int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
		    int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
		    int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
			 sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
			 sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
			 sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
			 sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
			 sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
			 sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");

		}

    // 14.not_found to shrinkage
    @Test(groups = {
            "Regression" }, dataProvider = "not_found_to_shrinkage", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public void not_found_to_shrinkage(String item_id, ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {
        
        sft = new SoftAssert();

        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        int b_p_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(0);
        int b_mi_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(1);
        b_mi_cb--;
        int b_ma_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(2);
        int core_inv_countb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
        core_inv_countb--;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }

    // 15.not received to stored
    @Test(groups = {
            "Regression" }, dataProvider = "not_receivedto_stored", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void not_receivedto_stored(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {
        sft = new SoftAssert();

        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        wh_invcountb++;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        atp_invcountb++;
        int b_p_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(0);
        int b_mi_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(1);
        int b_ma_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(2);
        int core_inv_countb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
        core_inv_countb++;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }

    // 16.processing to not found
    @Test(groups = {
            "Regression" }, dataProvider = "processingToNotfound", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public void processingToNotfound(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {
        
        sft= new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        int b_p_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(0);
        b_p_cb--;
        int b_mi_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(1);
        b_mi_cb++;
        int b_ma_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(2);
        int core_inv_countb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }

    // 17.processing to stored
    @Test(groups = {
            "Regression" }, dataProvider = "processing_to_stored", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void processing_to_stored(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {
        sft= new SoftAssert();

        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        wh_invcountb++;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        atp_invcountb++;
        int b_p_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(0);
        b_p_cb--;
        int b_mi_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(1);
        int b_ma_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(2);
        int core_inv_countb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);

        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
         sft.assertAll();
    }

    // 18.rfo to not found
    @Test(groups = {
            "Regression" }, dataProvider = "rfo_to_not_found", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void rfo_to_not_found(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft = new SoftAssert();

        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        int b_p_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(0);
        b_p_cb--;
        int b_mi_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(1);
        b_mi_cb++;
        int b_ma_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(2);
        int core_inv_countb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }

    // processing to issued_for_ops
    @Test(groups = {
            "Regression" }, dataProvider = "processing_to_issued_for_ops", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void processing_to_issued_for_ops(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft = new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        int b_p_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(0);
        int b_mi_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(1);
        int b_ma_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(2);
        int core_inv_countb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }

    // 21.rfo to stored
    @Test(groups = {
            "Regression" }, dataProvider = "rfo_to_stored", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void rfo_to_stored(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft = new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        wh_invcountb++;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        atp_invcountb++;
        int b_p_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(0);
        b_p_cb--;
        int b_mi_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(1);
        int b_ma_cb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(2);
        int core_inv_countb = getcore_inv_count_details(ITEM_TRAN_SKU, "1").get(3);
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }

    // 20.shrinkage to found
    @Test(groups = {
            "Regression" }, dataProvider = "shrinkage_to_found", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void shrinkage_to_found(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {
        sft = new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        wh_invcountb++;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        atp_invcountb++;
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        core_inv_countb++;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }

    // 21.stored to notfound
    @Test(groups = {
            "Regression" }, dataProvider = "stored_to_notfound", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void stored_to_notfound(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft = new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        wh_invcountb--;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        atp_invcountb--;
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        b_mi_cb++;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }

    // 22.stored to return
    @Test(groups = {
            "Regression" }, dataProvider = "stored_to_return", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void stored_to_return(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {
        sft = new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        wh_invcountb--;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        atp_invcountb--;
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        core_inv_countb--;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        Thread.sleep(2000);
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }

    // 23.stored to processing
    @Test(groups = {
            "Regression" }, dataProvider = "stored_to_processing", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void stored_to_processing(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft = new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        wh_invcountb--;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        atp_invcountb--;
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        b_p_cb++;
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();

        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        Thread.sleep(5000);
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }

    // 24.stored to transit
    @Test(groups = {
            "Regression" }, dataProvider = "stored_to_transit", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void stored_to_transit(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft= new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        wh_invcountb--;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        atp_invcountb--;
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        b_p_cb++;
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }

    // 25.transit to detached
    @Test(groups = {
            "Regression" }, dataProvider = "transit_to_detached", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void transit_to_detached(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft= new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        b_p_cb--;
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        core_inv_countb--;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();


    }

    // 26.transit to not found
    @Test(groups = {
            "Regression" }, dataProvider = "transit_to_notfound", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void transit_to_notfound(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft=new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        b_p_cb--;
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        b_mi_cb++;
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }

    // 27. stored q1 to q2
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
            "ExhaustiveRegression" }, dataProvider = "storedq1toq2", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void storedq1toq2(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft=new SoftAssert();
        int b_p_c_Q2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(0);
        int core_inv_countQ2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(3);
        core_inv_countQ2_b++;
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        wh_invcountb--;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        atp_invcountb--;
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,"Q1",default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        core_inv_countb--;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,"Q1",default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
        int b_p_c_Q2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(0);
        int core_inv_countQ2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(3);
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
         sft.assertEquals(b_p_c_Q2_a, b_p_c_Q2_b, "blocked processing count Q2");
         sft.assertEquals(core_inv_countQ2_a, core_inv_countQ2_b, "core_inventory inventory count Q2");
        sft.assertAll();

    }

    // 28.rfo to store q1 to q2
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
            "ExhaustiveRegression" }, dataProvider = "rfo_to_storeq1toq2", dataProviderClass = WMSPipelineDP.class)
    public  void rfo_to_storeq1toq2(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft = new SoftAssert();
        int b_p_c_Q2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(0);
        int core_inv_countQ2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(3);
        core_inv_countQ2_b++;
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,"Q1",default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        b_p_cb--;
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        core_inv_countb--;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,"Q1",default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
        int b_p_c_Q2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(0);
        int core_inv_countQ2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(3);
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
         sft.assertEquals(b_p_c_Q2_a, b_p_c_Q2_b, "blocked processing count Q2");
         sft.assertEquals(core_inv_countQ2_a, core_inv_countQ2_b, "core_inventory inventory count Q2");
        sft.assertAll();

    }

    // 29.rfo to rfo q1 to q2
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
            "ExhaustiveRegression" }, dataProvider = "rfo_to_rfo_q1_to_q2", dataProviderClass = WMSPipelineDP.class)
    public  void rfo_to_rfo_q1_to_q2(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft = new SoftAssert();
        int b_p_c_Q2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(0);
        b_p_c_Q2_b++;
        int core_inv_countQ2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(3);
        core_inv_countQ2_b++;
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,"Q1",default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        b_p_cb--;
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        core_inv_countb--;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,"Q1",default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
        int b_p_c_Q2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(0);
        int core_inv_countQ2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(3);
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
         sft.assertEquals(b_p_c_Q2_a, b_p_c_Q2_b, "blocked processing count Q2");
         sft.assertEquals(core_inv_countQ2_a, core_inv_countQ2_b, "core_inventory inventory count Q2");
        sft.assertAll();

    }

    // 30.found to stores q1 to q2
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
            "ExhaustiveRegression" }, dataProvider = "found_to_stores_q1_to_q2", dataProviderClass = WMSPipelineDP.class)
    public  void found_to_stores_q1_to_q2(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft = new SoftAssert();
        int b_mi_c_Q2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(1);
        int core_inv_countQ2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(3);
        core_inv_countQ2_b++;
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,"Q1",default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        b_mi_cb--;
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        int b_p_c_Q2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(0);
        core_inv_countb--;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");

        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,"Q1",default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
        int b_p_c_Q2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(0);
        int b_mi_c_Q2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(1);
        int core_inv_countQ2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(3);
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
         sft.assertEquals(b_p_c_Q2_a, b_p_c_Q2_b, "blocked processing count Q2");
         sft.assertEquals(core_inv_countQ2_a, core_inv_countQ2_b, "core_inventory inventory count Q2");
         sft.assertEquals(b_mi_c_Q2_a, b_mi_c_Q2_b, "blocked missed count Q2");
        sft.assertAll();

    }

    // stored_to_deleted
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
            "ExhaustiveRegression" }, dataProvider = "stored_to_deleted", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void stored_to_deleted(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {
        sft = new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        wh_invcountb--;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        atp_invcountb--;
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        core_inv_countb--;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }

    // found_to_deleted
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
            "ExhaustiveRegression" }, dataProvider = "found_to_deleted", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void found_to_deleted(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft = new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");

        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");

        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        b_mi_cb--;
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        core_inv_countb--;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
        sft.assertAll();

    }

    // not_found_to_deleted
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
            "ExhaustiveRegression" }, dataProvider = "not_found_to_deleted", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void not_found_to_deleted(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft = new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");

        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");

        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        b_mi_cb--;
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        core_inv_countb--;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
         sft.assertAll();

    }

    // processing_to_deleted
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
            "ExhaustiveRegression" }, dataProvider = "processing_to_deleted", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
    public  void processing_to_deleted(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft = new SoftAssert();
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        // wh_invcountb++;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        // atp_invcountb++;
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        b_p_cb--;
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        core_inv_countb--;

        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
         sft.assertAll();

    }

    // storedq2toq1
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
            "ExhaustiveRegression" }, dataProvider = "storedq2toq1", dataProviderClass = WMSPipelineDP.class)
    public  void storedq2toq1(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft= new SoftAssert();
        int b_mi_c_Q2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(1);
        int core_inv_countQ2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(3);
        core_inv_countQ2_b--;
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        wh_invcountb++;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        atp_invcountb++;
        int b_p_c_Q2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(0);
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,"Q1",default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        core_inv_countb++;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,"Q1",default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
        int b_p_c_Q2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(0);
        int b_mi_c_Q2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(1);
        int core_inv_countQ2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(3);
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
         sft.assertEquals(b_p_c_Q2_a, b_p_c_Q2_b, "blocked processing count Q2");
         sft.assertEquals(core_inv_countQ2_a, core_inv_countQ2_b, "core_inventory inventory count Q2");
         sft.assertEquals(b_mi_c_Q2_a, b_mi_c_Q2_b, "blocked missed count Q2");
         sft.assertAll();
    }

    // rfo_to_rfo_q2_to_q1
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
            "ExhaustiveRegression" }, dataProvider = "rfo_to_rfo_q2_to_q1", dataProviderClass = WMSPipelineDP.class)
    public  void rfo_to_rfo_q2_to_q1(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {

        sft = new SoftAssert();
        int b_p_c_Q2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(0);
        b_p_c_Q2_b--;
        int core_inv_countQ2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(3);
        core_inv_countQ2_b--;
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        b_p_cb++;
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        core_inv_countb++;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");

        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,"Q1",default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
        int b_p_c_Q2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(0);
        int core_inv_countQ2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(3);
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
         sft.assertEquals(b_p_c_Q2_a, b_p_c_Q2_b, "blocked processing count Q2");
         sft.assertEquals(core_inv_countQ2_a, core_inv_countQ2_b, "core_inventory inventory count Q2");
         sft.assertAll();
        //  sft.assertEquals(b_mi_c_Q2_a, b_mi_c_Q2_b,"blocked missed count
        // Q2");
    }

    // found_to_stored_q2_to_q1
    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
            "ExhaustiveRegression" }, dataProvider = "found_to_stored_q2_to_q1", dataProviderClass = WMSPipelineDP.class)
    public  void found_to_stored_q2_to_q1(String item_id,  ItemStatus status, String quality, String response)
            throws InterruptedException, UnsupportedEncodingException, JAXBException, UnsupportedEncodingException, JAXBException {

        sft = new SoftAssert();
        int b_mi_c_Q2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(1);
        b_mi_c_Q2_b--;
        int b_p_c_Q2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(0);

        int core_inv_countQ2_b = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(3);
        core_inv_countQ2_b--;
        int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
        wh_invcountb++;
        int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
        atp_invcountb++;
        CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
        coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
        core_inv_countb++;
        ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
                quality,"0");

        log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
       log.info(wh_invcounta + "before" + wh_invcountb);
        int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
       log.info(atp_invcounta + "before" + atp_invcountb);
        CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
                .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,"Q1",default_owner_partner_id);
        coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
        int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
        int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
        int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
        int b_p_c_Q2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(0);
       log.info(b_p_c_Q2_a + "before" + b_p_c_Q2_b);
        int core_inv_countQ2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(3);
       log.info(b_p_c_Q2_a + "before" + b_p_c_Q2_b);
        int b_mi_c_Q2_a = getcore_inv_count_detailsQ2(ITEM_TRAN_SKU, "1").get(1);
        //log.info(core_inv_countQ2_a+"before"+b_mi_c_Q2_a);
         sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
         sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
         sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
         sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
         sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
         sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
         sft.assertEquals(b_p_c_Q2_a, b_p_c_Q2_b, "blocked processing count Q2");
         sft.assertEquals(core_inv_countQ2_a, core_inv_countQ2_b, "core_inventory inventory count Q2");
         sft.assertEquals(b_mi_c_Q2_a, b_mi_c_Q2_b, "blocked missed count Q2");
         sft.assertAll();
    }
    //issued_for_ops to transit
    @Test(groups = {
    "Regression" }, dataProvider = "issued_for_ops_to_transit", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
public  void issued_for_ops_to_transit(String item_id,  ItemStatus status, String quality, String response)
    throws InterruptedException, UnsupportedEncodingException, JAXBException {

sft = new SoftAssert();
int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
wh_invcountb--;
int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
atp_invcountb--;
CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
        .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
b_p_cb++;
ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
        quality,"0");

log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
        .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();

 sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
 sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
 sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
 sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
 sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
 sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
sft.assertAll();
    }
    //issued_for_ops to liquidated
@Test(groups = {
"Regression" }, dataProvider = "issued_for_ops_to_liquidated", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
public  void issued_for_ops_to_liquidated(String item_id,  ItemStatus status, String quality, String response)
throws InterruptedException, UnsupportedEncodingException, JAXBException {

sft = new SoftAssert();
int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
wh_invcountb--;
int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
atp_invcountb--;
CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
    .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
core_inv_countb--;
ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
    quality,"0");

log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
    .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
sft.assertAll();
}
    //issued_for_ops to returned
@Test(groups = {
"Regression" }, dataProvider = "issued_for_ops_to_returned", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
public  void issued_for_ops_to_returned(String item_id,  ItemStatus status, String quality, String response)
throws InterruptedException, UnsupportedEncodingException, JAXBException {

sft = new SoftAssert();
int wh_invcountb = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
wh_invcountb--;

int atp_invcountb = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
atp_invcountb--;
CoreInventoryCountResponse coreInventoryCount_b=imsServiceHelper
    .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
int b_p_cb = coreInventoryCount_b.getData().get(0).getBlockedProcessingCount().intValue();
int b_mi_cb = coreInventoryCount_b.getData().get(0).getBlockedMissedCount().intValue();
int b_ma_cb = coreInventoryCount_b.getData().get(0).getBlockedManualCount().intValue();
int core_inv_countb=coreInventoryCount_b.getData().get(0).getInventoryCount().intValue();
ItemResponse WMSbulkupdateitem = WMSItemTransitionHelper1.invokeBulkItemTransitionAPI(item_id, status,
    quality,"0");
core_inv_countb--;

log.info("\nPrinting WMSbulupdateitem  API response :\n\n" + WMSbulkupdateitem + "\n");
int wh_invcounta = getwh_inv_details(ITEM_TRAN_SKU, "1", Seller_id, "1");
int atp_invcounta = getatp_inv_details(ITEM_TRAN_SKU, Seller_id, "1");
CoreInventoryCountResponse coreInventoryCount_a=imsServiceHelper
    .searchCoreInvOwnerId("10", "DESC", "1", ITEM_TRAN_SKU,quality,default_owner_partner_id);
coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
int b_p_ca = coreInventoryCount_a.getData().get(0).getBlockedProcessingCount().intValue();
int b_mi_ca = coreInventoryCount_a.getData().get(0).getBlockedMissedCount().intValue();
int b_ma_ca = coreInventoryCount_a.getData().get(0).getBlockedManualCount().intValue();
int core_inv_counta=coreInventoryCount_a.getData().get(0).getInventoryCount().intValue();
sft.assertEquals(b_p_ca, b_p_cb, "blocked processing count");
sft.assertEquals(b_ma_ca, b_ma_cb, "blocked manual count");
sft.assertEquals(b_mi_ca, b_mi_cb, "blocked missed count");
sft.assertEquals(atp_invcounta, atp_invcountb, "atp inventory count");
sft.assertEquals(wh_invcounta, wh_invcountb, "wh_inventory inventory count");
sft.assertEquals(core_inv_counta, core_inv_countb, "core_inventory inventory count");
sft.assertAll();
}


    @BeforeClass(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" })
    public  void insertitems() {
       log.info("Entered insertion loop");
        String queryToDeleteData = "delete from item where id in (1000121,1000122,1000123,1000124,1000125,1000126,1000127,1000128,1000129,1000130,1000131,1000132,1000158,1000134,1000135,1000136,1000137,1000138,1000139,1000140,1000141,1000142,1000143,1000144,1000145,1000146,1000147,1000148,1000149,1000150,1000151,1000152,1000153,1000155,1000156,1000157,1000154,1000159,1000160,1000161,1000162,1000163,1000164,1000165,9100000086116,1000170,1000171,1000172)";
        String queryToDeleteItemInfo="delete from item_info where item_id in (1000121,1000122,1000123,1000124,1000125,1000126,1000127,1000128,1000129,1000130,1000131,1000132,1000133,1000158,1000134,1000135,1000136,1000137,1000138,1000139,1000140,1000141,1000142,1000143,1000144,1000145,1000146,1000147,1000148,1000149,1000150,1000151,1000152,1000153,1000155,1000156,1000157,1000154,1000159,1000158,1000160,1000161,1000162,1000163,1000164,1000165,9100000086116,1000170,1000171,1000172)";
        DBUtilities.exUpdateQuery(queryToDeleteData, "myntra_wms");
        DBUtilities.exUpdateQuery(queryToDeleteItemInfo, "myntra_wms");
        String insertItemInfo="INSERT INTO `item_info` ( `item_id`, `item_action_status`, `task_id`, `order_barcode`, `created_on`, `created_by`, `last_modified_on`, `version`, `order_id`, `invoice_sku_id`, `agreement_type`, `buyer_id`, `manufacturing_date`)"
                +"VALUES(1000121, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000122, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000123, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000124, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000125, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000126, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000127, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000128, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
               // +"(1000129, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000130, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
               // +"(1000131, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000132, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000133, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000134, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000135, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000136, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000137, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000138, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000139, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000140, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000141, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000142, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000143, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000144, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000145, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000146, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000147, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000148, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000149, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000150, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000151, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000152, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000153, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000154, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000155, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000156, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000157, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000158, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000160, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000162, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000163, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000164, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000170, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000171, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000172, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
                +"(9100000086116, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"

                +"(1000165, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL);";
        String Query1 = "insert into item (id,barcode,sku_id,quality,item_status,warehouse_id,enabled,po_id,po_barcode,po_sku_id,lot_id,lot_barcode,comments,bin_id)"
                + "values(1000121,'1000121',6258,'Q1','ACCEPTED_RETURNS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',128),"
                + "(1000122,'1000122',6258,'Q1','ACCEPTED_RETURNS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',128),"
                + "(1000123,'1000123',6258,'Q1','CUSTOMER_RETURNED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',128),"
                + "(1000124,'1000124',6258,'Q1','CUSTOMER_RETURNED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',128),"
                + "(1000125,'1000125',6258,'Q1','DETACHED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',50614),"
                + "(1000126,'1000126',6258,'Q1','DETACHED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',50614),"
                + "(1000150,'1000150',6258,'Q1','DETACHED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',50614),"
                + "(1000127,'1000127',6258,'Q1','FOUND',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
                + "(1000128,'1000128',6258,'Q1','FOUND',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
              //  + "(1000129,'1000129',6258,'Q1','ISSUED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48849),"
                + "(1000130,'1000130',6258,'Q1','ISSUED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48849),"
                //+ "(1000131,'1000131',6258,'Q1','ISSUED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48849),"
                + "(1000132,'1000132',6258,'Q1','NOT_FOUND',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
                + "(1000158,'1000158',6258,'Q1','NOT_RECEIVED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
                + "(9100000086116,'9100000086116',12070042,'Q1','NOT_RECEIVED',28,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
                + "(1000134,'1000134',6258,'Q1','PROCESSING',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48849),"
                + "(1000135,'1000135',6258,'Q1','PROCESSING',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48849),"
                + "(1000136,'1000136',6258,'Q1','RETURN_FROM_OPS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
                + "(1000137,'1000137',6258,'Q1','RETURN_FROM_OPS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
                + "(1000138,'1000138',6258,'Q1','SHRINKAGE',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',221),"
                + "(1000139,'1000139',6258,'Q1','STORED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',403),"
                + "(1000140,'1000140',6258,'Q1','STORED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',403),"
                + "(1000141,'1000141',6258,'Q1','STORED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',403),"
                + "(1000142,'1000142',6258,'Q1','STORED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',403),"
                + "(1000143,'1000143',6258,'Q1','TRANSIT',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',50614),"
                + "(1000144,'1000144',6258,'Q1','TRANSIT',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',50614),"
                + "(1000149,'1000149',6258,'Q1','STORED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
                + "(1000145,'1000145',6258,'Q1','RETURN_FROM_OPS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',403),"
                + "(1000146,'1000146',6258,'Q1','RETURN_FROM_OPS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
                + "(1000147,'1000147',6258,'Q1','FOUND',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
                + "(1000148,'1000148',6258,'Q1','STORED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
                + "(1000151,'1000151',6258,'Q1','STORED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
                + "(1000152,'1000152',6258,'Q1','FOUND',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
                + "(1000153,'1000153',6258,'Q1','NOT_FOUND',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
                + "(1000154,'1000154',6258,'Q1','PROCESSING',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48849),"
                + "(1000155,'1000155',6258,'Q2','STORED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
                + "(1000160,'1000160',6258,'Q1','STORED',36,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
                + "(1000162,'1000162',6258,'Q1','STORED',36,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
                + "(1000163,'1000163',6258,'Q1','STORED',36,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
                + "(1000165,'1000165',6258,'Q1','STORED',36,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
                + "(1000156,'1000156',6258,'Q2','FOUND',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
                + "(1000157,'1000157',6258,'Q2','RETURN_FROM_OPS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
                + "(1000170,'1000170',6258,'Q1','ISSUED_FOR_OPS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
                + "(1000171,'1000171',6258,'Q1','ISSUED_FOR_OPS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
                + "(1000172,'1000172',6258,'Q1','ISSUED_FOR_OPS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"

                + "(1000159,'1000159',6258,'Q1','PROCESSING',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48849)";

        DBUtilities.exUpdateQuery(Query1, "myntra_wms");
        DBUtilities.exUpdateQuery(insertItemInfo, "myntra_wms");

        String QuerywithOrder = "insert into item (id,barcode,sku_id,quality,item_status,warehouse_id,enabled,po_id,po_barcode,po_sku_id,lot_id,lot_barcode,comments,bin_id,order_id)"
                + "values(1000131,'1000131',6258,'Q1','ISSUED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48849,'2147613971256'),"
                + "(1000129,'1000129',6258,'Q1','ISSUED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48849,'2147613971263')";
        DBUtilities.exUpdateQuery(QuerywithOrder, "myntra_wms");

        String insertItemInfoWithOrder="INSERT INTO `item_info` ( `item_id`, `item_action_status`, `task_id`, `order_barcode`, `created_on`, `created_by`, `last_modified_on`, `version`, `order_id`, `invoice_sku_id`, `agreement_type`, `buyer_id`, `manufacturing_date`)"
                +"VALUES(1000131, 'NEW', NULL, '2147613971256','2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, '2147613971256', NULL, 'OUTRIGHT', 2297, NULL),"
                +"(1000129, 'NEW', NULL, '2147613971263', '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, '2147613971263', NULL, 'OUTRIGHT', 2297, NULL)";
        DBUtilities.exUpdateQuery(insertItemInfoWithOrder, "myntra_wms");



        String Query2 = "update core_inventory_counts set inv_count =30, blocked_manually_count =0,blocked_missed_count=6,blocked_processing_count=13 where sku_id = 6258 and warehouse_id =1 and quality='Q1' and owner_partner_id=2297";
        DBUtilities.exUpdateQuery(Query2, "myntra_ims");
       log.info("core_inv_count updated");

        String Query3 = "update wh_inventory set inventory_count =11 ,blocked_order_count =1 where sku_id = 6258 and warehouse_id =1 and seller_id=21";
        DBUtilities.exUpdateQuery(Query3, "myntra_ims");
       log.info("wh_inv updated");
        String Query4 = "update inventory set inventory_count =11, blocked_order_count =1 where sku_id = 6258 and seller_id=21 ";
        DBUtilities.exUpdateQuery(Query4, "myntra_atp");
       log.info("atp updated");

        String Query5 = "update core_inventory_counts set inv_count =30, blocked_manually_count =0,blocked_missed_count=6,blocked_processing_count=13  where sku_id = 6258 and warehouse_id =1 and quality='Q2' and owner_partner_id=2297";
        DBUtilities.exUpdateQuery(Query5, "myntra_ims");
       log.info("core_inv_count updated for q2 ");
        String query1 = "update wh_inventory set inventory_count=1000 where sku_id in (66321,90603,66407,24318,1225025,3864,3869,3870,3871,3857,3858,3859,3860) and store_id =1 and warehouse_id in (36,28) ";
        String query2 = "update inventory set inventory_count=2000 where sku_id in (66321,90603,66407,24318,1225025,3864,3869,3870,3871,3857,3858,3859,3860)";
        String query3 = "update wh_inventory set blocked_order_count=100 where sku_code in ('RDTPFOSH79902','PUMATSRM01391','PUMALNGP01005')and store_id in(2,3) and warehouse_id in (36,28)";
        String query4 = "update wh_inventory set inventory_count=10 where sku_code='KNKHTSHT02241'and store_id =1 and warehouse_id in (36,28);";
        DBUtilities.exUpdateQuery(query1, "myntra_ims");
        DBUtilities.exUpdateQuery(query3, "myntra_ims");
        DBUtilities.exUpdateQuery(query2, "myntra_atp");
        DBUtilities.exUpdateQuery(query4, "myntra_ims");

       log.info("Insertion and update done sucessfully");

    }

    public  List<Integer> getcore_inv_count_details(String sku_id, String warehouse_id)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {
        List<Integer> invlist = new ArrayList<Integer>();
        int b_mi_c = 0;
        int b_p_c = 0;
        int b_ma_c = 0;
        int inv_count;

        Thread.sleep(1000);
        Map hm = new HashMap();
        hm = DBUtilities.exSelectQueryForSingleRecord("select * from core_inventory_counts where sku_id = " + sku_id
                + " and warehouse_id=" + warehouse_id + " and quality ='Q1' and owner_partner_id=2297", "myntra_ims");
        b_p_c = (Integer) hm.get("blocked_processing_count");
        b_mi_c = (Integer) hm.get("blocked_missed_count");
        b_ma_c = (Integer) hm.get("blocked_manually_count");
        inv_count = (Integer) hm.get("inv_count");
        invlist.add(b_p_c);
        invlist.add(b_mi_c);
        invlist.add(b_ma_c);
        invlist.add(inv_count);
        return invlist;
    }

    public  int getwh_inv_details(String sku_id, String warehouse_id, String seller_id, String store_id)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {
        int wh_inventory = 0;
        Thread.sleep(1000);
        Map hm = new HashMap();
        hm = DBUtilities
                .exSelectQueryForSingleRecord(
                        "select inventory_count from wh_inventory where sku_id = " + sku_id + " and warehouse_id= "
                                + warehouse_id + " and seller_id= " + seller_id + " and store_id= " + store_id,
                        "myntra_ims");
        int hmsize = hm.size();
        if (hmsize == 0)
           log.info("No result set found");
        else
            wh_inventory = (Integer) hm.get("inventory_count");
        return wh_inventory;
    }

    public  int getatp_inv_details(String sku_id, String seller_id, String store_id) throws InterruptedException, UnsupportedEncodingException, JAXBException {
        int atp_inventory = 0;
        Thread.sleep(1000);
        Map hm = new HashMap();
        hm = DBUtilities.exSelectQueryForSingleRecord("select inventory_count from inventory where sku_id = " + sku_id
                + " and seller_id=" + seller_id + " and store_id=" + store_id, "myntra_atp");
        int hmsize = hm.size();

        if (hmsize == 0) {
           log.info("No result set found");
            // String hmsizestr=hm.size();
            AssertJUnit.assertEquals(hmsize, 0);

        } else
            atp_inventory = (Integer) hm.get("inventory_count");
        return atp_inventory;
    }

    public  List<Integer> getcore_inv_count_detailsQ2(String sku_id, String warehouse_id)
            throws InterruptedException, UnsupportedEncodingException, JAXBException {
        List<Integer> invlist = new ArrayList<Integer>();
        int b_mi_c = 0;
        int b_p_c = 0;
        int b_ma_c = 0;
        int inv_count;

        Thread.sleep(1000);
        Map hm = new HashMap();
        hm = DBUtilities.exSelectQueryForSingleRecord("select * from core_inventory_counts where sku_id = " + sku_id
                + " and warehouse_id=" + warehouse_id + " and quality ='Q2' and owner_partner_id=2297", "myntra_ims");
        int hmsize = hm.size();

        if (hmsize == 0)
           log.info("No result set found");
        else
            b_p_c = (Integer) hm.get("blocked_processing_count");
        b_mi_c = (Integer) hm.get("blocked_missed_count");
        b_ma_c = (Integer) hm.get("blocked_manually_count");
        inv_count = (Integer) hm.get("inv_count");
        invlist.add(b_p_c);
        invlist.add(b_mi_c);
        invlist.add(b_ma_c);
        invlist.add(inv_count);
        return invlist;
    }


    public  List<Integer> imsandatpdetails(String sku_id, String store_id, String warehouse_id) {
        List<Integer> imsandatpdetailslist = new ArrayList<Integer>();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        int ims_inventory_count = 0;
        int ims_inv_stor_1_count = 0;
        int ims_boc = 0;
        int atp_inv_count = 0;
        String query1 = "Select * from wh_inventory where sku_code =" + "'" + sku_id + "'" + " and warehouse_id="
                + warehouse_id + " and store_id=" + store_id + ";";
       log.info(query1);
        String query2 = "Select * from wh_inventory where sku_code =" + "'" + sku_id + "'" + " and warehouse_id="
                + warehouse_id + " and store_id=1" + ";";
       log.info(query2);
        String query3 = "Select * from inventory where sku_code =" + "'" + sku_id + "'" + ";";
       log.info(query3);
        try {
            Map rset1, rset2, rset3 = new HashMap();
            rset1 = DBUtilities.exSelectQueryForSingleRecord(query1, "myntra_ims");
            ims_inventory_count = (Integer) rset1.get("inventory_count");
           log.info("ims inv count is for  store is : " + ims_inventory_count);
            imsandatpdetailslist.add(ims_inventory_count);// 0th element in list
            ims_boc = (Integer) rset1.get("blocked_order_count");
           log.info("ims boc count is  for  store is : " + ims_boc);

            imsandatpdetailslist.add(ims_boc);// 1st element boc fk store
            rset3 = DBUtilities.exSelectQueryForSingleRecord(query3, "myntra_atp");
            // rset3.next();
            atp_inv_count = (Integer) rset3.get("inventory_count");
           log.info("ims inv in store 1" + atp_inv_count);

            imsandatpdetailslist.add(atp_inv_count);// 2nd element atp inv

            rset2 = DBUtilities.exSelectQueryForSingleRecord(query2, "myntra_ims");

            ims_inv_stor_1_count = (Integer) rset2.get("inventory_count");
           log.info("inv count ims store_id1" + ims_inv_stor_1_count);

            imsandatpdetailslist.add(ims_inv_stor_1_count);
            //log.info(imsandatpdetailslist);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return imsandatpdetailslist;
    }

    }
