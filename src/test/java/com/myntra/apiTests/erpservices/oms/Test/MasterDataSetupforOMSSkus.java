package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.myntra.MyntraWidgetDataHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.testbase.BaseTest;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;

/**
 * @author Chandra Shekhar
 *
 */
public class MasterDataSetupforOMSSkus extends BaseTest {
	private static Logger log = Logger
			.getLogger(MasterDataSetupforOMSSkus.class);
	static Initialize init = new Initialize("/Data/configuration");
	End2EndHelper end2EndHelper = new End2EndHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
	IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	StyleServiceApiHelper styleServiceApiHelper = new StyleServiceApiHelper();
	MyntraWidgetDataHelper myntraWidgetDataHelper = new MyntraWidgetDataHelper();
	private Long vectorSellerID;
	private Long sellerCONS = 25L;
	private long delayedTime = 5000;
	
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, UnsupportedEncodingException, JAXBException {
		omsServiceHelper.refreshOMSApplicationPropertyCache();
        omsServiceHelper.refreshOMSJVMCache();
        vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
	}
	

	@Test(enabled = true, description = "Master Script to setUp date for OMS", groups = {
			"smoke", "regression" })
	public void masterDataSetupForOMS() throws Exception {

		int[] styleIds = new int[] {
				OMSTCConstants.MasterDateStyleIds.styleId_1530,
				OMSTCConstants.MasterDateStyleIds.styleId_1531,
				OMSTCConstants.MasterDateStyleIds.styleId_1532,
				OMSTCConstants.MasterDateStyleIds.styleId_1534,
				OMSTCConstants.MasterDateStyleIds.styleId_1535,
				OMSTCConstants.MasterDateStyleIds.styleId_371389,
				OMSTCConstants.MasterDateStyleIds.styleId_236593,
				OMSTCConstants.MasterDateStyleIds.styleId_10001,
				OMSTCConstants.MasterDateStyleIds.styleId_10002,
				OMSTCConstants.MasterDateStyleIds.styleId_10005,
				OMSTCConstants.MasterDateStyleIds.styleId_10031,
				OMSTCConstants.MasterDateStyleIds.styleId_10007,
				OMSTCConstants.MasterDateStyleIds.styleId_10008,
				OMSTCConstants.MasterDateStyleIds.styleId_10009,
				OMSTCConstants.MasterDateStyleIds.styleId_10010,
				OMSTCConstants.MasterDateStyleIds.styleId_10011,
				OMSTCConstants.MasterDateStyleIds.styleId_10012,
				OMSTCConstants.MasterDateStyleIds.styleId_321729,
				OMSTCConstants.MasterDateStyleIds.styleId_356809,
				OMSTCConstants.MasterDateStyleIds.styleId_243673,
				OMSTCConstants.MasterDateStyleIds.styleId_243672,
				OMSTCConstants.MasterDateStyleIds.styleId_219266,
				OMSTCConstants.MasterDateStyleIds.styleId_1538,
				OMSTCConstants.MasterDateStyleIds.styleId_1555,
				OMSTCConstants.MasterDateStyleIds.styleId_95051,
				OMSTCConstants.MasterDateStyleIds.styleId_147464,
				OMSTCConstants.MasterDateStyleIds.styleId_171643,
				OMSTCConstants.MasterDateStyleIds.styleId_247180,
				OMSTCConstants.MasterDateStyleIds.styleId_253875,
				OMSTCConstants.MasterDateStyleIds.styleId_282672,
				OMSTCConstants.MasterDateStyleIds.styleId_295681,
				OMSTCConstants.MasterDateStyleIds.styleId_333473,
				OMSTCConstants.MasterDateStyleIds.styleId_344866,
				OMSTCConstants.MasterDateStyleIds.styleId_346106,
				OMSTCConstants.MasterDateStyleIds.styleId_346108,
				OMSTCConstants.MasterDateStyleIds.styleId_346109,
				OMSTCConstants.MasterDateStyleIds.styleId_346111,
				OMSTCConstants.MasterDateStyleIds.styleId_346112,
				OMSTCConstants.MasterDateStyleIds.styleId_346113,
				OMSTCConstants.MasterDateStyleIds.styleId_346114,
				OMSTCConstants.MasterDateStyleIds.styleId_346115,
				OMSTCConstants.MasterDateStyleIds.styleId_346116,
				OMSTCConstants.MasterDateStyleIds.styleId_346118,
				OMSTCConstants.MasterDateStyleIds.styleId_346234,
				OMSTCConstants.MasterDateStyleIds.styleId_346661,
				OMSTCConstants.MasterDateStyleIds.styleId_12345,
				OMSTCConstants.MasterDateStyleIds.styleId_12346,
				OMSTCConstants.MasterDateStyleIds.styleId_373900,
				OMSTCConstants.MasterDateStyleIds.styleId_373908,
				OMSTCConstants.MasterDateStyleIds.styleId_373909,
				OMSTCConstants.MasterDateStyleIds.styleId_339138,
				OMSTCConstants.MasterDateStyleIds.styleId_339139,
				OMSTCConstants.MasterDateStyleIds.styleId_373865,
				OMSTCConstants.MasterDateStyleIds.styleId_10016,
				OMSTCConstants.MasterDateStyleIds.styleId_113726,
				OMSTCConstants.MasterDateStyleIds.styleId_113709,
				OMSTCConstants.MasterDateStyleIds.styleId_219266,};

		try {
			updatewidgetpairs();
			// Delete ATP and IMS records for ONHand Items
			log.info("deleting records for Onhand Items in ATP and IMS...");
			deleteRecordsFromATPandIMSForONHandItem();
			// Insert data in ATP and IMS for On Hand Items
			log.info("inserting records for Onhand Items in ATP and IMS...");
			insertDateForATPandIMSOnHandItems();
			// Insert data in ATP and IMS for JIT Items
			log.info("inserting records for JIT Items in ATP and IMS...");
			insertDateForATPandIMSJITItems();
			log.info("style reindexing...");
			styleServiceApiHelper.styleReindexForStyleIDsPost(styleIds);
			// Waiting for 15 seconds after reindexing
			log.info("waiting for 30 sec...");
			End2EndHelper.sleep(delayedTime*6);
		} catch (Exception e) {
			log.info(e.getMessage());
		}

	}

	/**
	 * This function is to delete reords from ATP and IMS for OnHand items
	 * 
	 */
	public void deleteRecordsFromATPandIMSForONHandItem() {
		String skuIds = OMSTCConstants.OtherSkus.skuId_3827
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_3828
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_3829
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_3830
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_3831
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_3832
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_3833
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_3834
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_3835
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_3836
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_3837
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_3838
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_3843
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_3844
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_3845
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_3846
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_3847
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_1243741
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_1243742
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_1243743
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_1243744
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_1243745
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_796931
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_796932
				+ ","
				+ OMSTCConstants.OtherSkus.skuId_796933
				+ ","
				+ OMSTCConstants.OMSLineCancellationTest.cancelLineWithWMSAssociation_ITEM1
				+ ","
				+ OMSTCConstants.OMSLineCancellationTest.cancelQuantityForWhichWHIsNotAssigned_ITEM1
				+ ","
				+ OMSTCConstants.OMSLineCancellationTest.cancelOneQuantityFromLineWithWMSAssociation_ITEM1
				+ ","
				+ OMSTCConstants.OMSLineCancellationTest.checkLineCancellationForEachQuantityWhereLPAndCBIsUsed_ITEM1
				+ ","
				+ OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1
				+ ","
				+ OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_MultipleQuantity_ITEM1
				+ ","
				+ OMSTCConstants.OMSLineCancellationTest.cancelOrderReleaseAfterItemAssociation_ITEM2
				+ ","
				+ OMSTCConstants.OMSLineCancellationTest.cancelOrderReleaseAfterItemAssociation_ITEM1
				+ ","
				+ OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1
				+ ","
				+ OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2
				+ ","
				+ OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1
				+ ","
				+ OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderForWhichWHIsNotAssigned_ITEM1
				+ ","
				+ OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1
				+ ","
				+ OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1
				+ ","
				+ OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsNotAssigned_ITEM1
				+ ","
				+ OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderFollowedByItemCancellation_ITEM1
				+ ","
				+ OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2
				+ ","
				+ OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1
				+ ","
				+ OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1
				+ ","
				+ OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM2
				+ ","
				+ OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM1
				+ ","
				+ OMSTCConstants.OMSLineCancellationTest.cancelOrderReleaseAfterItemAssociation_ITEM2
				+ ","
				+ OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1
				+ ","
				+ OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsAssigned_ITEM1
				+ ","
				+ OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1
				+ ","
				+ OMSTCConstants.CancellationTillHubTest.cancelOrderInPKStatus_ITEM1
				+ ","
				+ OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM4
				+ ","
				+ OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInPKStatus_ITEM2
				+ ","
				+ OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInPKStatus_ITEM1
				+ ","
				+ OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM2
				+ "," + OMSTCConstants.OtherSkus.skuId_47458 + ","
				+ OMSTCConstants.OtherSkus.skuId_47459 + ","
				+ OMSTCConstants.OtherSkus.skuId_47460 + ","
				+ OMSTCConstants.OtherSkus.skuId_47461 + ","
				+ OMSTCConstants.OtherSkus.skuId_47569 + ","
				+ OMSTCConstants.OtherSkus.skuId_47572 + ","
				+ OMSTCConstants.OtherSkus.skuId_47571 + ","
				+ OMSTCConstants.OtherSkus.skuId_47573 + ","
				+ OMSTCConstants.OtherSkus.skuId_47570 + ","
				+ OMSTCConstants.OtherSkus.skuId_47578 + ","
				+ OMSTCConstants.OtherSkus.skuId_47576 + ","
				+ OMSTCConstants.OtherSkus.skuId_47574 + ","
				+ OMSTCConstants.OtherSkus.skuId_47575 + ","
				+ OMSTCConstants.OtherSkus.skuId_47577 + ","
				+ OMSTCConstants.OtherSkus.skuId_1071773 + ","
				+ OMSTCConstants.OtherSkus.skuId_1071770 + ","
				+ OMSTCConstants.OtherSkus.skuId_1071771 + ","
				+ OMSTCConstants.OtherSkus.skuId_1071769 + ","
				+ OMSTCConstants.OtherSkus.skuId_1071772 + ","
				+ OMSTCConstants.OtherSkus.skuId_1071768 + ","
				+ OMSTCConstants.OtherSkus.skuId_1071774 + ","
				+ OMSTCConstants.OtherSkus.skuId_1270332 + ","
				+ OMSTCConstants.OtherSkus.skuId_1190292 + ","
				+ OMSTCConstants.OtherSkus.skuId_749877 + ","
				+ OMSTCConstants.OtherSkus.skuId_749876 + ","
				+ OMSTCConstants.OtherSkus.skuId_749874 + ","
				+ OMSTCConstants.OtherSkus.skuId_749875 + ","
				+ OMSTCConstants.OtherSkus.skuId_3856 + ","
				+ OMSTCConstants.OtherSkus.skuId_3918 + ","
				+ OMSTCConstants.OtherSkus.skuId_585868 + ","
				+ OMSTCConstants.OtherSkus.skuId_852767 + ","
				+ OMSTCConstants.OtherSkus.skuId_945928 + ","
				+ OMSTCConstants.OtherSkus.skuId_987669 + ","
				+ OMSTCConstants.OtherSkus.skuId_1111187 + ","
				+ OMSTCConstants.OtherSkus.skuId_1147531 + ","
				+ OMSTCConstants.OtherSkus.skuId_1153620;

		String deleteQueryATP = "delete  from inventory where sku_id in ("
				+ skuIds + ");";
		String deleteQueryIMS = "delete  from wh_inventory where sku_id in ("
				+ skuIds + ");";
		DBUtilities.exUpdateQuery(deleteQueryATP, "myntra_atp");
		DBUtilities.exUpdateQuery(deleteQueryIMS, "myntra_ims");
	}

	/**
	 * @throws Exception
	 *             This function is to insert data in ATP and IMS for OnHand
	 *             Items
	 */
	public void insertDateForATPandIMSOnHandItems() throws Exception {

		String[] skusForUpdateInventory = new String[] {
				OMSTCConstants.OtherSkus.skuId_3827 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3828 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3829 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3830 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3831 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3832 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3833 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3834 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3835 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3836 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3837 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3838 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3843 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3844 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3845 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3846 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3847 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1243741 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1243742 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1243743 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1243744 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1243745 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_796931 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_796932 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_796933 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSLineCancellationTest.cancelLineWithWMSAssociation_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSLineCancellationTest.cancelQuantityForWhichWHIsNotAssigned_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSLineCancellationTest.cancelOneQuantityFromLineWithWMSAssociation_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSLineCancellationTest.checkLineCancellationForEachQuantityWhereLPAndCBIsUsed_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_MultipleQuantity_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSLineCancellationTest.cancelOrderReleaseAfterItemAssociation_ITEM2
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSLineCancellationTest.cancelOrderReleaseAfterItemAssociation_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderForWhichWHIsNotAssigned_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsNotAssigned_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderFollowedByItemCancellation_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM2
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSLineCancellationTest.cancelOrderReleaseAfterItemAssociation_ITEM2
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsAssigned_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.CancellationTillHubTest.cancelOrderInPKStatus_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM4
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInPKStatus_ITEM2
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInPKStatus_ITEM1
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM2
						+ ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_47458 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_47459 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_47460 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_47461 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_47569 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_47572 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_47571 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_47573 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_47570 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_47578 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_47576 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_47574 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_47575 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_47577 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1071773 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1071770 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1071771 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1071769 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1071772 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1071768 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1071774 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1270332 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1190292 + ":28,36:1000:0:"+sellerCONS+":1",
				OMSTCConstants.OtherSkus.skuId_749877 + ":28,36:1000:0:"+sellerCONS+":1",
				OMSTCConstants.OtherSkus.skuId_749876 + ":28,36:1000:0:"+sellerCONS+":1",
				OMSTCConstants.OtherSkus.skuId_749874 + ":28,36:1000:0:"+sellerCONS+":1",
				OMSTCConstants.OtherSkus.skuId_749875 + ":28,36:1000:0:"+sellerCONS+":1",
				OMSTCConstants.OtherSkus.skuId_3856 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3918 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_585868 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_852767 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_945928 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_987669 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1111187 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1147531 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1153620 + ":28,36:1000:0:"+vectorSellerID+":1" };
		
		
		
		atpServiceHelper.updateInventoryDetailsForSeller(
				skusForUpdateInventory, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(skusForUpdateInventory,
				"ON_HAND");

	}

	/**
	 * @throws Exception
	 *             This function is to insert Data in ATP and IMS for JIT Items
	 */
	public void insertDateForATPandIMSJITItems() throws Exception {

		String[] skusForUpdateInventory = new String[] {
				OMSTCConstants.OtherSkus.skuId_796934 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_818510 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_818511 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_818512 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_818506 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_818507 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_818508 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_818509 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1190293 + ":28,36:1000:0:"+sellerCONS+":1",
				OMSTCConstants.OtherSkus.skuId_1190296 + ":28,36:1000:0:"+sellerCONS+":1",
				OMSTCConstants.OtherSkus.skuId_1190298 + ":28,36:1000:0:"+sellerCONS+":1",
				OMSTCConstants.OtherSkus.skuId_1190300 + ":28,36:1000:0:"+sellerCONS+":1",
				OMSTCConstants.OtherSkus.skuId_828703 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_330411 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_493441 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1152952 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1152955 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1152957 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1152958 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1152959 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1152960 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1152961 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1152962 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1152964 + ":28,36:1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_1153080 + ":28,36:1000:0:"+vectorSellerID+":1" };
		
		for(String sku:skusForUpdateInventory){
			String skuId = sku.split(":")[0];
			String deleteQueryATP = "delete  from inventory where sku_id in ("
					+ skuId + ");";
			String deleteQueryIMS = "delete  from wh_inventory where sku_id in ("
					+ skuId + ");";
			DBUtilities.exUpdateQuery(deleteQueryATP, "myntra_atp");
			DBUtilities.exUpdateQuery(deleteQueryIMS, "myntra_ims");
		}
		
		atpServiceHelper.updateInventoryDetailsForSeller(
				skusForUpdateInventory, "JUST_IN_TIME");
		imsServiceHelper.updateInventoryForSeller(skusForUpdateInventory,
				"JUST_IN_TIME");

	}

	public void updatewidgetpairs(){

		String [] key_value = new String [] {
				OMSTCConstants.KeyValue.GiftCharges+":"+OMSTCConstants.KeyValue.GiftChargesValue,
				OMSTCConstants.KeyValue.ShippingCharges+":"+OMSTCConstants.KeyValue.ShippingChargesValue
		};
		myntraWidgetDataHelper.updatewidgetpairs(key_value);

	}

}
