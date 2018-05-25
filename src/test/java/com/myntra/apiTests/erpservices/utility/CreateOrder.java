package com.myntra.apiTests.erpservices.utility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants;
import com.myntra.apiTests.erpservices.oms.Test.OMSTryAndBuyTest;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.Initialize;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.test.commons.testbase.BaseTest;

import argo.saj.InvalidSyntaxException;

public class CreateOrder extends BaseTest {
	
    static Initialize init = new Initialize("/Data/configuration");
    private static Logger log = Logger.getLogger(OMSTryAndBuyTest.class);
    End2EndHelper end2EndHelper = new End2EndHelper();
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	private OrderEntry orderEntry;
	private String orderReleaseId;
	private static Long vectorSellerID;
	private String supplyTypeOnHand = "ON_HAND";
	private SoftAssert sft;
	private String orderID;
	private int atpInventory = 1000;
	private int imsInventory = 1000;
	private int onholdReason34 = 34;
	private WMSServiceHelper wmsservicehelper = new WMSServiceHelper();	
	private String skuId = System.getenv("skuId");
	private String quantity = System.getenv("quantity");
    private String login = System.getenv("login");
    private String password = System.getenv("password");;
	
    @BeforeClass(alwaysRun = true)
    public void testBeforeClass() throws SQLException, UnsupportedEncodingException, JAXBException {
    	vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
        omsServiceHelper.refreshOMSApplicationPropertyCache();
        omsServiceHelper.refreshOMSJVMCache();
    }
    
	/**
	 * @param skuId
	 * @param quantity
	 * @param warehouseId
	 * @param pincode
	 * @param availableQty
	 * @param supplyType
	 * @param sellerId
	 * @return
	 * @throws ManagerException
	 * @throws IOException
	 * @throws JAXBException
	 * @throws InvalidSyntaxException
	 * @throws JSONException
	 * @throws ParseException
	 * @throws SCMExceptions
	 */
	private String createOrderHelper(String skuId,String quantity,int warehouseId,String pincode,String supplyType,long sellerId) throws ManagerException, IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException, SCMExceptions{
		String[] skuID = {skuId+":"+quantity};
		updateInventory(skuId, warehouseId,supplyType,sellerId);
    	orderID = end2EndHelper.createOrder(login, password,pincode, skuID, "", false, false, false, "", false);
    	return orderID;
	}
	
	/**
	 * @param skuId
	 * @param warehouseId
	 * @param qty
	 * @param supplyType
	 * @param sellerId
	 */
	private void updateInventory(String skuId,int warehouseId,String supplyType,long sellerId){
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId+":"+warehouseId+":"+atpInventory+":0:"+sellerId+":1"},supplyType);
    	imsServiceHelper.updateInventoryForSeller(new String[]{skuId+":"+warehouseId+":"+imsInventory+":0:"+sellerId},supplyType);
    	
	}
	
	@Test
	public void createOrder() throws Exception{
		orderID = createOrderHelper(skuId,quantity,OMSTCConstants.WareHouseIds.warehouseId36_BW,OMSTCConstants.Pincodes.PINCODE_560068,supplyTypeOnHand,vectorSellerID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, EnumSCM.WP);
		List<OrderReleaseEntry> orderReleaseEntries = omsServiceHelper.getOrderEntry(orderID).getOrderReleases();
		for(OrderReleaseEntry orderReleaseEntry:orderReleaseEntries)
		wmsservicehelper.pushOrderToWave(orderReleaseEntry.getId().toString());
	}
}
