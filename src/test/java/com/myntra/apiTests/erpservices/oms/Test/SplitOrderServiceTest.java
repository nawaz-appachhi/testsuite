package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.apiTests.portalservices.pricingpromotionservices.helper.PnPServiceHelper;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import com.myntra.client.notification.response.StatusResponse;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OmsCommonResponse;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.oms.common.enums.SplittingStrategyCode;
import com.myntra.test.commons.testbase.BaseTest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author abhijit.pati
 *
 *         Test Cases Covered 1. State Wise Split COD Order - Order Value Much
 *         Higher than the threshold limit. 2. State Wise Split CB Order - Order
 *         Value Greater than the threshold limit. 3. State Wise Split CB+LP+COD
 *         Order - Order Value Greater than the threshold limit. 4. State Wise
 *         Split Order - Single Item Order Value Greater than the threshold
 *         limit - No SPlit should Happen. 5. State Wise Split Order - MultiItem
 *         Order which order values greater than the threshold limit. 6. State
 *         Wise Split Order - SingleItem Order which order Value less than state
 *         split limit. 7. State Wise Split Order - MultipleItem Order which
 *         order Value less than state split limit. 8. State Wise Split Order -
 *         MultiItem from Different WH And OrderValueLess than state split
 *         limit. 9. State Wise Split Order - MultiItem from Different WH And
 *         Order Value More Than state split limit. 10. State Wise Split Order -
 *         MultiItem from Different WH And Single WH Order Value More Than state
 *         split limit. 11. State Wise Split Order - Multiple Quantity of Single
 *         Item from Same WH and Single Quantity value is greater than the State
 *         Limit. 12. State Wise Split Order - Check state wise split should not
 *         happen for other states. 13. State Wise Split Order - Order should
 *         not be split if the Order is just greater than the limit including
 *         the Shipping charges. 14. State Wise Split Order - Order should not
 *         be split if the Order is just greater than the limit including the
 *         Gift charges.
 */

public class SplitOrderServiceTest extends BaseTest {
    static Initialize init = new Initialize("/Data/configuration");
    String password = "123456";
    String login = "splitorderservicetest@gmail.com";
    String uidx;

    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    End2EndHelper end2EndHelper = new End2EndHelper();
    IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    private static Long vectorSellerID;
	private List<OrderReleaseEntry> listOrderReleaseEntry;

	private SoftAssert sft;
	private String sellerId25 = "25";
	private int sellerId_HEAL = 21;
	private String supplyTypeOnHand = "ON_HAND";


    @BeforeClass(alwaysRun = true)
    public void testBeforeClass() throws SQLException, JAXBException, IOException {
    	vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
        DBUtilities.exUpdateQuery("UPDATE on_hold_reasons_master SET is_enabled=0 where id in (35, 23, 37);", "myntra_oms");
        DBUtilities.exUpdateQuery("UPDATE state_shipment_limit SET amount_limit=2000 where state_code='UP'", "myntra_oms");
        end2EndHelper.refreshToolsApplicationPropertyCache();
        omsServiceHelper.refreshOMSApplicationPropertyCache();
        omsServiceHelper.refreshOMSJVMCache();
        uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
    }
    
        
    /**
     * @param orderID
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public double getFinalAmountForRlease(String orderID) throws UnsupportedEncodingException, JAXBException{
    	Double finalTotal = 0.0;
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        for (OrderReleaseEntry orderReleaseEntry : listOrderReleaseEntry) {
        	finalTotal += orderReleaseEntry.getFinalAmount();
        }
        return finalTotal;
    }

    @Test(enabled = true, description = "State Wise Split COD Order - Order Value Much Higher than the threshold limit.",groups={"smoke","regression","splitorder","sanity"})
    public void stateWiseSplitOrderWithCODForSingleSeller() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":15" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_282001, skuId, "", false, false, false, "", false);
        
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        sft.assertEquals(listOrderReleaseEntry.size(), 8, "Check the State Wise Split");
        Double giftCharge = listOrderReleaseEntry.get(0).getGiftCharge();
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832) * 15;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();
    }
    
    
    @Test(enabled = true, description = "State Wise Split COD Order - Order Value Much Higher than the threshold limit.",groups={"smoke","regression","splitorder","sanity"})
    public void stateWiseSplitOrderWithCODForMultiseller() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_749874);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":17",OMSTCConstants.OtherSkus.skuId_749874+":10" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1",
        		OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId25+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+sellerId25},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_282001, skuId, "", false, false, false, "",false);
 
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"RFR:WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        sft.assertEquals(listOrderReleaseEntry.size(), 12, "Check the State Wise Split");
        
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832) * 17 + skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_749874) *10;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();
    }
    
    @Test(enabled = true, description = "Courier Wise Split COD Order - Order Value Much Higher than the threshold limit.",groups={"smoke","regression","splitorder"})
    public void courierWiseSplitOrderWithCODForMultiseller() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_749874);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":24",OMSTCConstants.OtherSkus.skuId_749874+":10" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId25+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21",OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+25},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "",false);

        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        sft.assertEquals(listOrderReleaseEntry.size(), 2, "Check the State Wise Split");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832) * 24 + skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_749874) *10;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();
    }
    
    @Test(enabled = true, description = "Courier Wise Split COD Order - Order Value Much Higher than the threshold limit.",groups={"smoke","regression","splitorder"})
    public void courierWiseSplitOrderWithCODForSingleseller() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":26" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21"},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "",false);

        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        sft.assertEquals(listOrderReleaseEntry.size(), 2, "Check the State Wise Split");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832) * 26;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();

    }
    
    
    @Test(enabled = true, description = "State Wise Split COD Order - Order Value Much Higher than the threshold limit.",groups={"smoke","regression","splitorder"})
    public void stateWiseSplitOrderWithCODWhenEachSellerAmountIsLessThanThresholdLimit() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_749874);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":2",OMSTCConstants.OtherSkus.skuId_749874+":7" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId25+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21",OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+25},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_282001, skuId, "", false, false, false, "",false);
   
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        sft.assertEquals(listOrderReleaseEntry.size(), 3, "Check the State Wise Split");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832) * 2 + skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_749874) * 7;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();

    }


    @Test(enabled = true, description = "State Wise Split CB Order - Order Value Greater than the threshold limit.",groups={"smoke","regression","splitorder"})
    public void stateWiseSplitOrderWithCB() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":15" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21"},"ON_HAND");
        
        end2EndHelper.updateloyalityAndCashBack(uidx, 0, 11985);
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_282001, skuId, "", false, false, false, "",false);
   
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        
        sft.assertEquals(listOrderReleaseEntry.size(), 8, "Check the State Wise Split");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832) * 15;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();

    }
    
    @Test(enabled = true, description = "State Wise Split CB Order - Order Value Greater than the threshold limit.",groups={"smoke","regression","splitorder"})
    public void stateWiseSplitOrderWithCBForMultiseller() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_749874);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":17",OMSTCConstants.OtherSkus.skuId_749874+":10" };
        end2EndHelper.updateloyalityAndCashBack(uidx, 0, 11985);
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId25+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21",OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+25},"ON_HAND");
    
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_282001, skuId, "", false, false, false, "",false);
    
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        
        sft.assertEquals(listOrderReleaseEntry.size(), 12, "Check the State Wise Split");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832) * 17 + skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_749874) * 10;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();

    }


    @Test(enabled = true,groups={"smoke","regression","splitorder"}, description = "State Wise Split CB+LP+COD Order - Order Value Greater than the threshold limit")
    public void stateWiseSplitOrderForCBLPCOD() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":6" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21"},"ON_HAND");
        
        end2EndHelper.updateloyalityAndCashBack(uidx, 188, 4000);
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_282001, skuId, "", false, true, false, "",false);
      
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        listOrderReleaseEntry = orderEntry.getOrderReleases();
        sft.assertEquals(listOrderReleaseEntry.size(), 3, "Check the State Wise Split");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832)* 6 ;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();

    }

    @Test(enabled = true, groups={"smoke","regression","splitorder"},description = "State Wise Split Order - Single Item Order Value Greater than the threshold limit - No SPlit should Happen.")
    public void checkStateWiseSplitOrderForSingleItemWhichAmountIsGreaterThanTheLimit() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_1071769);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_1071769+":1" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_1071769+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_1071769+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21"},"ON_HAND");
        
        PnPServiceHelper pnpServiceHelper = new PnPServiceHelper();
        pnpServiceHelper.removeDiscount("321729");
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_282001, skuId, "", false, false, false, "",false);
        
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        
        sft.assertEquals(listOrderReleaseEntry.size(), 1, "Check the State Wise Split");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_1071769) * 1 ;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();

    }

    @Test(enabled = true, groups={"smoke","regression","splitorder"},description = "State Wise Split Order - MultiItem Order which order values greater than the threshold limit.")
    public void checkStateWiseSplitOneItemMultipleQuantityAndsingleQuantityAmountIsGreaterThanTheStateLimit() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_1071769);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
    	String skuId[] = { OMSTCConstants.OtherSkus.skuId_1071769+":2" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_1071769+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_1071769+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21"},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_282001, skuId, "", false, false, false, "",false);
        
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        

        sft.assertEquals(listOrderReleaseEntry.size(), 1, "Check the State Wise Split");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_1071769) * 2 ;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();

    }

    @Test(enabled = true,groups={"smoke","regression","splitorder"}, description = "")
    public void checkStateWiseSplitOrderForMultipleItemWhichAmountIsGreaterThanStateLimit() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_984285);
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_1153008);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_984285+":4", OMSTCConstants.OtherSkus.skuId_1153008+":4" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_984285+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_1153008+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_984285+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21",OMSTCConstants.OtherSkus.skuId_1153008+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21"},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_282001, skuId, "", false, false, false, "",false);
        
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        
        sft.assertEquals(listOrderReleaseEntry.size(), 2, "Check the State Wise Split");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_984285) * 4 + skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_1153008)  * 4 ;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();
    }

    @Test(enabled = true,groups={"smoke","regression","splitorder"})
    public void checkStateWiseSplitSingleItemOrderValueLessThanSplitLimit() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":2" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":28,36:1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_282001, skuId, "", false, false, false, "",false);
        
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        
        sft.assertEquals(listOrderReleaseEntry.size(), 1, "Check the State Wise Split should not happen");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832) * 2 ;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();

    }

    @Test(enabled = true,groups={"smoke","regression","splitorder"})
    public void checkStateWiseSplitMultiItemOrderValueLessThanSplitLimit() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3843);

    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1", OMSTCConstants.OtherSkus.skuId_3843+":1" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_3843+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21",OMSTCConstants.OtherSkus.skuId_3843+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21"},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "",false);
        
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        
        sft.assertEquals(listOrderReleaseEntry.size(), 1, "Check the State Wise Split should not happen");
        Double orderTotal =  skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832)* 1 + skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3843) * 1;
        

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();

    }

    @Test(enabled = true,groups={"smoke","regression","splitorder"})
    public void checkStateWiseSplitMultiItemFromDifferentWHAndOrderValueLessThanSplitLimit() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_984285);
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_1153008);

    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);

    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_984285+":1", OMSTCConstants.OtherSkus.skuId_1153008+":1" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_984285+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_1153008+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_984285+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:21",OMSTCConstants.OtherSkus.skuId_1153008+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1"},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_282001, skuId, "", false, false, false, "",false);
        
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
      
        sft.assertEquals(listOrderReleaseEntry.size(), 2, "Check the State Wise Split should not happen");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_984285)* 2 + skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_1153008) * 1 + getShippingAmount(orderID) ;
        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();

    }

    @Test(enabled = true,groups={"smoke","regression","splitorder"})
    public void checkStateWiseSplitMultiItemFromDifferentWHAndOrderValueMoreThanSplitLimit() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3841);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":2", OMSTCConstants.OtherSkus.skuId_3841+":2" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1"
        		,OMSTCConstants.OtherSkus.skuId_3841+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.OtherSkus.skuId_3841+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_282001, skuId, "", false, false, false, "",false);
        
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        
        sft.assertEquals(listOrderReleaseEntry.size(), 2, "Check the State Wise Split should not happen");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832)* 2 + skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3841) * 2 ;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();

    }

    @Test(enabled = true,groups={"smoke","regression","splitorder"})
    public void checkStateWiseSplitMultiItemFromDifferentWHAndSingleWHOrderValueMoreThanSplitLimit() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3841);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":3", OMSTCConstants.OtherSkus.skuId_3841+":3" };

       atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",
    		   OMSTCConstants.OtherSkus.skuId_3841+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"},"ON_HAND");
       imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
    		   OMSTCConstants.OtherSkus.skuId_3841+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},"ON_HAND");
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_282001, skuId, "", false, false, false, "",false);
        
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        
        sft.assertEquals(listOrderReleaseEntry.size(), 4, "Check the State Wise Split should not happen");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832)* 3 + skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3841) * 3 ;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();

    }

    @Deprecated
    @Test(enabled = false,groups={"smoke","regression","splitorder"})
    public void checkStateWiseSplitShouldNotSplitTheShippingChargesAsDifferentShipment() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3841);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":3", OMSTCConstants.OtherSkus.skuId_3841+":3" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_3841+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21",OMSTCConstants.OtherSkus.skuId_3841+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1"},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_282001, skuId, "", false, false, false, "",false);
        End2EndHelper.sleep(10000L);
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        sft.assertEquals(listOrderReleaseEntry.size(), 4, "Check the State Wise Split should not happen");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832) * 3 + skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3841)* 3 ;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();

    }

    @Deprecated
    @Test(enabled = false,groups={"smoke","regression","splitorder"})
    public void checkStateWiseSplitShouldNotSplitTheGiftChargesAsDifferentShipment() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3841);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":3", OMSTCConstants.OtherSkus.skuId_3841+":3" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21"},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_282001, skuId, "", false, false, false, "",false);
        End2EndHelper.sleep(10000L);
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        sft.assertEquals(listOrderReleaseEntry.size(), 4, "Check the State Wise Split should not happen");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832) * 3 + skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3841) * 3 ;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();

    }

    @Test(enabled = true,groups={"smoke","regression","splitorder"})
    public void checkStateWiseSplitShouldNotHappenForOtherStates() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3841);
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_749874);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3841+":3", OMSTCConstants.OtherSkus.skuId_3832+":3" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"
        		,OMSTCConstants.OtherSkus.skuId_3841+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
        		OMSTCConstants.OtherSkus.skuId_3841+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "",false);
        
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        System.out.println("listOrderReleaseEntry :"+listOrderReleaseEntry.size());
        sft.assertEquals(listOrderReleaseEntry.size(), 2, "Check the State Wise Split should not happen");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832)* 3 + skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3841) * 3 ;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();
    }
    
    @Test(enabled = true,groups={"smoke","regression","splitorder"})
    public void checkStateWiseSplitShouldNotHappenForOtherStatesForMultiseller() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3841);
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_749874);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3841+":3", OMSTCConstants.OtherSkus.skuId_3832+":3",OMSTCConstants.OtherSkus.skuId_749874+":2" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",
        		OMSTCConstants.OtherSkus.skuId_3841+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1",
        		OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+sellerId25+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
        		OMSTCConstants.OtherSkus.skuId_3841+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,
        		OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+sellerId25},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "",false);
        
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        System.out.println("listOrderReleaseEntry :"+listOrderReleaseEntry.size());
        sft.assertEquals(listOrderReleaseEntry.size(), 2, "Check the State Wise Split should not happen");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3841) * 3 + skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832) * 3 +
        		skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_749874) * 2 ;

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();
    }

	@Test(enabled = false,groups={"smoke","regression","splitorder"})
    public void checkSplitShouldHappenforEachItemForMadura(){

    }



    @Test(enabled = true, groups={"smoke","regression","splitorder"},description = "1.Create order with WH-20 Vendor-1292 qty2 sku1/qty2 sku2. \n 2.Check Order is in WP State \n 3.Check there is 1 Release \n 4. Cancel the Order 5.NOTE:A. marketplace.singleitemsplit.warehouseids - 26  \n B. marketplace.vendorsplit.warehouseids - 20 \n C. Warehouse does not exist in both properties - 17,27")
    public void multipleQtyMultiSkuAndCancelOrder() throws Exception {
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_1153416+":2", OMSTCConstants.OtherSkus.skuId_1153008+":2" }; // Sku:MSMRFHSR00467/MSMRFHSR00059
        // Style
        // id:346570/346162
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_1153416+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_1153008+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_1153416+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21",OMSTCConstants.OtherSkus.skuId_1153008+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21"},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "",false);
        
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        
        int releaseResult = getNoOfReleaseCount(orderID);
        System.out.println("DB check Order Release table Release count: " + releaseResult);
        sft.assertEquals(1, releaseResult);
        sft.assertAll();
    }




    @Test(groups ={"smoke","splitorder",
            "regression" }, description = "" +
            "1.Create order with qty1 sku1(Vendor 1292)/qty1 sku1( Vendor 1010) for WH 20. " +
            "2.Check Order is in WP State." +
            "3.Check there is 2 Release" +
            "4. Cancel the Orders" +
            "5.NOTE:A. marketplace.singleitemsplit.warehouseids - 26  \n B. marketplace.vendorsplit.warehouseids - 20 \n C. Warehouse does not exist in both properties - 17,27")
    public void MultiSkuVendorAndCancelOrder() throws Exception {
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_818510+":2", OMSTCConstants.OtherSkus.skuId_818511+":2" }; // Sku:
        // MSMRFHSR00059/BLFLTOPS00101
        // Style
        // id:346162/294294 ,
        //atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId99+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId98+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_818510+":"+"20"+":1000:0:21",
        		OMSTCConstants.OtherSkus.skuId_818511+":"+"26"+":100:0:21"},"JUST_IN_TIME");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "",false);
        
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        int releaseResult = getNoOfReleaseCount(orderID);
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        System.out.println("DB check Order Release table Release count: " + releaseResult);
        sft.assertEquals(2, releaseResult,"Release count should be 2 but Actual:"+releaseResult);
        sft.assertAll();
    }

    @Test(groups = {"smoke","splitorder",
            "regression" }, description = "1.Select WH-20:Vendor 1292(qty1) Vendor 1010(qty1) \n 2.Select WH-26:Vendor 1292(qty1) Vendor 1010(qty1)\n 3.Place Order \n 4.Check there is 6 Release 2 for WH-20 4 for WH-26 \n 5. Cancel the Order 6.NOTE:A. marketplace.singleitemsplit.warehouseids - 26  \n B. marketplace.vendorsplit.warehouseids - 20 \n C. Warehouse does not exist in both properties - 17,27")
    public void WHLevelSplitCase_3() throws Exception {
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_1153008+":1", OMSTCConstants.OtherSkus.skuId_984285+":1", OMSTCConstants.OtherSkus.skuId_1153239+":2", OMSTCConstants.OtherSkus.skuId_1153106+":2" }; // Sku:MSMRFHSR00059/BLFLTOPS00101/MSMRFHSR00290/MSMRFHSR00157
        // Style
        // id:346162/294294
        // 346393/346260
        // ,
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_1153008+":20:100:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_984285+":19:100:0:"+vectorSellerID+":1"
        		,OMSTCConstants.OtherSkus.skuId_1153239+":26:100:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_1153106+":26:100:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_1153008+":20:100:0:"+vectorSellerID,OMSTCConstants.OtherSkus.skuId_984285+":19:100:0:"+vectorSellerID
        		,OMSTCConstants.OtherSkus.skuId_1153239+":26:100:0:"+vectorSellerID,OMSTCConstants.OtherSkus.skuId_1153106+":26:100:0:"+vectorSellerID},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",false);
        
        //Warehouse 26 orders are stuck in RFR status,
        //For this scenario only validation is there should be 6 split release so adding RFR as well.
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"RFR:WP");
        int releaseResult = getNoOfReleaseCount(orderID);
        System.out.println("DB check Order Release table Release count: " + releaseResult);
        sft.assertEquals(6, releaseResult,"Order relseses should be 6.");
        sft.assertAll();
    }
    
    @Test(enabled = true, description = "State Wise Split COD Order - Order Value Much Higher than the threshold limit.",groups={"smoke","regression","splitorder"})
    public void courierWiseSplitOrderForIPConfig() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_749874);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1",OMSTCConstants.OtherSkus.skuId_749874+":1" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId25+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21",OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+25},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_585223, skuId, "", false, false, false, "",false);
   
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        sft.assertEquals(listOrderReleaseEntry.size(), 2, "Check courier Wise Split should happen");
        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832) * 1 + skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_749874) * 1 + getShippingAmount(orderID);

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();

    }
    
    @Test(enabled = true, description = "State Wise Split COD Order - Order Value Much Higher than the threshold limit.",groups={"smoke","regression","splitorder","sanity"})
    public void stateWiseSplitOrderWithCODForSingleSellerWithGiftCharge() throws Exception {
    	List<String> skuIds=new ArrayList<String>();
    	skuIds.add(OMSTCConstants.OtherSkus.skuId_3832);
    	
    	Map<String, Double> skuToPriceMap=StyleServiceApiHelper.getPriceBySkuId(skuIds);
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":15" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_282001, skuId, "", false, false, true, "", false);
        
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        sft.assertEquals(listOrderReleaseEntry.size(), 8, "Check the State Wise Split");

        Double orderTotal = skuToPriceMap.get(OMSTCConstants.OtherSkus.skuId_3832) * 15 + getGiftCharges(orderID);

        sft.assertEquals(getFinalAmountForRlease(orderID), orderTotal,"Spilt amount total is not correct");
        sft.assertAll();
    }
    
    @Test(enabled = true, description = "if all item is cancelled before split then release should move to F status",groups={"smoke","regression","splitorder"})
    public void releaseShouldBeCancelledIfAllItemIsCancelledBeforeSplit() throws Exception {
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1",OMSTCConstants.OtherSkus.skuId_749874+":1" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId25+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:21",OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+25},"ON_HAND");
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "",false);
   
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        //cancel one Line
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> listOrderReleaseEntry = orderEntry.getOrderReleases();
        String orderReleaseId = listOrderReleaseEntry.get(0).getId().toString();
        sft.assertEquals(listOrderReleaseEntry.size(), 1, "There should be single release before address change");
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        String lineID = ""+orderLineEntries.get(0).getId();
        
    	OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login, new String[] {lineID +":1"}, 41);
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1034,"Status code should be same after cancelLine");
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"There should be success message Actual:"+cancellationRes.getStatus().getStatusType());
        //Change address for IP courier
        OmsCommonResponse omsResponse=omsServiceHelper.changeAddress(orderReleaseId, "Sneha", "Agarwal", "Pune", "Hosur","585223",true,login);
        sft.assertEquals(StatusResponse.Type.SUCCESS.toString(),omsResponse.getStatus().getStatusType().toString());

        
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        listOrderReleaseEntry = orderEntry.getOrderReleases();
        sft.assertEquals(listOrderReleaseEntry.size(), 2, "Check courier Wise Split should happen");
        Boolean isReleaseCancelled = false;
        for(OrderReleaseEntry orderReleaseEntry: listOrderReleaseEntry){
        	orderReleaseId = orderReleaseEntry.getId().toString();
        	if(orderReleaseEntry.getStatus().equalsIgnoreCase("WP")){
        		omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 15);
        	}else if(orderReleaseEntry.getStatus().equalsIgnoreCase("F")){
        		omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 15);
        		isReleaseCancelled = true;
        	}
        }
        sft.assertEquals(isReleaseCancelled, Boolean.TRUE, "There should be one release in cancelled status");
        
        sft.assertAll();

    }
    
    @Test(enabled = true, description = "Verify split releases by seller for releases having multiple seller",groups={"smoke","regression","splitorder"})
    public void verifySplitRleasesBySellerForRelease() throws Exception {
    	sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId25+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId25},supplyTypeOnHand);

		String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        verifySplitReleases(SplittingStrategyCode.SPLIT_RELEASES_BY_SELLER, orderID, 2);
        sft.assertAll();

    }
    
    @Test(enabled = true, description = "Verify split releases by qty for releases having multiple seller",groups={"smoke","regression","splitorder"})
    public void verifySplitRleasesByQtyForRelease() throws Exception {
    	sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":4",OMSTCConstants.OtherSkus.skuId_749877+":4"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId25+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId25},supplyTypeOnHand);

		String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        verifySplitReleases(SplittingStrategyCode.SPLIT_RELEASES_BY_QUANTITY, orderID, 8);
        sft.assertAll();
    }
    
    public void verifySplitReleases(SplittingStrategyCode splitCode,String orderId,int splitCount) throws UnsupportedEncodingException, JAXBException{
    	OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderId);
    	String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
    	List<SplittingStrategyCode> splittingStrategyCodes = new ArrayList<SplittingStrategyCode>();
    	splittingStrategyCodes.add(splitCode);
    	OrderReleaseResponse response = omsServiceHelper.getSplitRelease(orderReleaseId, splittingStrategyCodes);
    	sft.assertEquals(response.getStatus().getStatusType().toString(), Type.SUCCESS.toString(),"There should be success in response");
    	List<OrderReleaseEntry> orderReleases  = response.getData();
    	sft.assertEquals(orderReleases.size(), splitCount,"There should be "+splitCount+" releases but Actual: "+orderReleases.size());
    	//Verify number of releases in DB, it should be 1 as above split is virtual for Munshi
    	int noOfReleasesInDB = omsServiceHelper.getOrderReleaseDBEntry(orderId).size();
    	sft.assertEquals(noOfReleasesInDB, 1,"There should be 1 release but Actual: "+noOfReleasesInDB);

    }

    
    
    




    // This query gets the number of releases from order_release table
    /**
     * @param orderID
     * @return
     * @throws SQLException
     */
    public int getNoOfReleaseCount(String orderID) throws SQLException {
        String releaseCount = "select count(*) from order_release where order_id_fk = " + orderID + "";
        List Rel = DBUtilities.exSelectQuery(releaseCount, "myntra_oms");
        HashMap<String, Long> releaseResult = (HashMap<String, Long>) Rel.get(0);
        Long lp = releaseResult.get("count(*)");
        return lp.intValue();
    }
    
    /**
     * @param orderID
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public Double getShippingAmount(String orderID) throws UnsupportedEncodingException, JAXBException{
    	Double shippingCharge = 0.0;
    	OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
    	List<OrderReleaseEntry> orderReleaseEntries = orderEntry.getOrderReleases();
    	for(OrderReleaseEntry orderReleaseEntry:orderReleaseEntries){
    		shippingCharge += orderReleaseEntry.getShippingCharge();
    	}
    	
    	return shippingCharge;
    }
    
    /**
     * @param orderID
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public Double getGiftCharges(String orderID) throws UnsupportedEncodingException, JAXBException{
    	Double giftCharge = 0.0;
    	OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
    	List<OrderReleaseEntry> orderReleaseEntries = orderEntry.getOrderReleases();
    	for(OrderReleaseEntry orderReleaseEntry:orderReleaseEntries){
    		giftCharge += orderReleaseEntry.getGiftCharge();
    	}
    	
    	return giftCharge;
    }
    
    
}
