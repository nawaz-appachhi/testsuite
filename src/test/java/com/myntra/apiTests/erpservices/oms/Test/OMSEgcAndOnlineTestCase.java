package com.myntra.apiTests.erpservices.oms.Test;

import java.sql.SQLException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.common.Builder.CreateOrderEntryBuilder;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.entries.CreateOrderEntry;
import com.myntra.apiTests.common.entries.SkuEntry;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSHelpersEnums.ReadyToDispatchType;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.test.commons.testbase.BaseTest;

public class OMSEgcAndOnlineTestCase extends BaseTest {
	
	
	static Initialize init = new Initialize("/Data/configuration");
	//String login = OMSTCConstants.LoginAndPassword.OMSEgcAndOnlineTestCaseLogin;
	String uidx;
	//String password = OMSTCConstants.LoginAndPassword.OMSEgcAndOnlineTestCasePassword;
    private static Long vectorSellerID;
    private static Logger log = Logger.getLogger(OMSEgcAndOnlineTestCase.class);
	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
	private String supplyTypeOnHand = "ON_HAND";
	private int sellerId_HEAL = 21;
	SoftAssert sft;
	private String orderID;
	private OrderEntry orderEntry;
	private String orderReleaseId;
	private List<OrderReleaseEntry> orderReleaseEntries;
	private List<String> listOfRelease;
	private String skuId1=OMSTCConstants.OtherSkus.skuId_3831;
	private long pincode_560068=560068;
	private String login="notifyme763@gmail.com";
	private String password="myntra@123";
	
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, JAXBException, Exception {
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
        vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
      //  uidx = ideaApiHelper.getUIDXForLogin("myntra", login);
        
	}
	
	@Test(enabled = true,groups={"regression","sanity","onlineOrder"}, description = "TC001:Verify Egc orders should directly move to DL status")
	public void createEGCOrderAndVerifyOrderMovesToCompletedStatus() throws Exception {

		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 1)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,EnumSCM.WP);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        for(OrderLineEntry orderLineEntry:omsServiceHelper.getOrderLineEntries(orderReleaseId)){
        	omsServiceHelper.insertDataInOrderLineAdditionalInfo(orderLineEntry.getId().toString(),"ITEM_TYPE",EnumSCM.ITEM_TYPE);
        }
        omsServiceHelper.markReadyToDispatchV3ForMyntraSeller(orderReleaseId,ReadyToDispatchType.POSITIVE.name());
        omsServiceHelper.checkReleaseStatusForOrder(orderID,EnumSCM.DL);
               
	}
	
	
	
		/**
		 * This is to create order and return release entries
		 * @param login
		 * @param password
		 * @param pincode
		 * @param skuEntries
		 * @return
		 * @throws Exception
		 */
		public List<OrderReleaseEntry> createOrderHelper(String login,String password,Long pincode,SkuEntry[] skuEntries) throws Exception{
			CreateOrderEntry createOrderEntry = new CreateOrderEntryBuilder(login,password,pincode,skuEntries).build();
			orderID = end2EndHelper.createOrder(createOrderEntry);
			orderReleaseEntries = omsServiceHelper.getOrderEntry(orderID).getOrderReleases();
			return orderReleaseEntries;
		}
		
	
}