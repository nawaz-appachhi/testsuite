package com.myntra.apiTests.erpservices.wms.Tests;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.packman.PackmanServiceHelper;
import com.myntra.apiTests.erpservices.wms.WMSConstants;
import com.myntra.apiTests.erpservices.wms.WMSOrderFlowHelper;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.client.wms.response.VirtualPacketResponse;
import com.myntra.commons.exception.ManagerException;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.test.commons.testbase.BaseTest;

import argo.saj.InvalidSyntaxException;

public class WMSOrderFlowRegression extends BaseTest{
	
	static Logger log = Logger.getLogger(WMSServiceHelper.class);
	
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	End2EndHelper end2EndHelper = new End2EndHelper();
    WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
    WMSOrderFlowHelper wmsOrderFlowHelper = new WMSOrderFlowHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
    PackmanServiceHelper packmanServiceHelper=new PackmanServiceHelper();
    
    
    
    @BeforeClass(alwaysRun=true)
    public void setupInventory(){
  //  	wmsOrderFlowHelper.updateInventory(WMSConstants.SKU1_ON_HAND, WMSConstants.WAREHOUSE_BANGALORE, WMSConstants.SELLER1);
   // 	wmsOrderFlowHelper.updateInventory(WMSConstants.SKU3_JIT, WMSConstants.WAREHOUSE_SAMALKA, WMSConstants.SELLER1);
    }
    
    @Test
    public void singleQuantityRelease() throws ManagerException, IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException, SCMExceptions, InterruptedException, XMLStreamException{
    	
    	int quantity = 1;
    	ArrayList<HashMap> items = null;
		HashMap<Integer, String> warehouseIdsAndPickTypes;
    	String skuId[] = {WMSConstants.SKU1_ON_HAND+":"+quantity};
    	HashMap<String, String> lmcSortingResponse;
    			
    	String login = "bountytestuser001@myntra.com";

    	String orderID = end2EndHelper.createOrder(login, "123456", "560068", skuId, "", false, false, false, "", false);
    	//String orderID = "2147523499";
    	OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
    	String storeOrderId = orderEntry.getStoreOrderId();
    			
    	omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
    	
    	HashMap orderLine = wmsOrderFlowHelper.getOrderDetails(storeOrderId).get(0);
    	warehouseIdsAndPickTypes = wmsOrderFlowHelper.getWarehouseIdsAndPickTypes(orderEntry.getOrderReleases().get(0).getOrderId().toString());
    	
    	// Push Order to Order wave for picking
  //  	wmsOrderFlowHelper.pushOrderToWave(warehouseIdsAndPickTypes);
    	if(!wmsServiceHelper.validateOrderInCoreOrderRelease(orderLine.get("order_release_id_fk").toString(), 8)){
    		log.info("Entry was not yet created in Core_order_release so inserting");
    		wmsServiceHelper.creteRelaseInCore_order_release(orderLine.get("order_release_id_fk").toString());
    	}
    	
    	//Item checkout
    	items = (ArrayList<HashMap>) wmsOrderFlowHelper.insertItems(orderLine.get("sku_id").toString(), orderLine.get("source_wh_id").toString(), (int)orderLine.get("quantity"), WMSConstants.BUYER1, 313, 1);
    	wmsOrderFlowHelper.itemCheckout(items, orderLine.get("order_release_id_fk").toString());
    		
    	lmcSortingResponse = wmsOrderFlowHelper.lmcSorting(items.get(0).get("barcode").toString());
    	
    }
    
    @Test
    public void multiQuantityOrder() throws UnsupportedEncodingException, ManagerException, JAXBException, InterruptedException, JSONException, XMLStreamException, ParseException{
    	
    	int quantity = 2;
    	ArrayList<HashMap> items = null;
		HashMap<Integer, String> warehouseIdsAndPickTypes;
    	String skuId[] = {WMSConstants.SKU1_ON_HAND+":"+quantity};
    	ArrayList<String> orders = new ArrayList<String>();
    	HashMap<String, String> lmcSortingResponse = null;
		VirtualPacketResponse virtualPacketResponse;
		boolean orderCheckout = false;
    			
    	String login = "bountytestuser001@myntra.com";

    	//String orderID = end2EndHelper.createOrder(login, "123456", "560068", skuId, "", false, false, false, "", false);
    	String orderID = "2147523498";
    	OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
    	//String storeOrderId = orderEntry.getStoreOrderId();
    	String storeOrderId = "106375265999340134402";
    			
    	omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
    	
    	List<HashMap> orderLines = wmsOrderFlowHelper.getOrderDetails(storeOrderId);
    	warehouseIdsAndPickTypes = wmsOrderFlowHelper.getWarehouseIdsAndPickTypes(orderEntry.getOrderReleases().get(0).getOrderId().toString());
    	
    	// Push Order to Order wave for picking
    	if(!wmsOrderFlowHelper.isPushOrderToWaveApiHit(orderID)){
    		wmsOrderFlowHelper.pushOrderToWave(warehouseIdsAndPickTypes);
    	}
    	
    	if(!wmsServiceHelper.validateOrderInCoreOrderRelease(orderLines.get(0).get("order_release_id_fk").toString(), 8)){
    		log.info("Entry was not yet created in Core_order_release so inserting");
    		
    		for(HashMap orderLine : orderLines){
    			if(!wmsOrderFlowHelper.isOrderPushedToWMS(orderLine.get("order_release_id_fk").toString())){
    				wmsServiceHelper.creteRelaseInCore_order_release(orderLine.get("order_release_id_fk").toString());
    			}
    		}
    	}
    	
    	//Item checkout and Consolidation
    	for(HashMap orderLine : orderLines){
    		if(!wmsOrderFlowHelper.isOrderCheckedOut(orderLine.get("order_release_id_fk").toString())){
    			items = (ArrayList<HashMap>) wmsOrderFlowHelper.insertItems(orderLine.get("sku_id").toString(), orderLine.get("source_wh_id").toString(), (int)orderLine.get("quantity"), WMSConstants.BUYER1, 313, 1);
        		wmsOrderFlowHelper.itemCheckout(items, orderLine.get("order_release_id_fk").toString());
        		orderCheckout = true;
    		}
    		
    		if(!orderCheckout){
    			items = wmsOrderFlowHelper.getItemsAssociatedWithOrder(orderLine.get("order_release_id_fk").toString());
    		}
    		
    		for(HashMap item : items){
    			if(!wmsOrderFlowHelper.isItemConsolidated(item.get("barcode").toString())){
    				lmcSortingResponse = wmsOrderFlowHelper.lmcSorting(item.get("barcode").toString());
        			Assert.assertEquals(lmcSortingResponse.get("statusMessage"), "Please keep item into bin/section for consolidation", lmcSortingResponse.get("statusMessage"));
        			wmsOrderFlowHelper.prepareAndConsolidate(item.get("barcode").toString(), orderLine.get("source_wh_id").toString());
    			}
    		}
    	}
    	
    	virtualPacketResponse = wmsOrderFlowHelper.flushBin(lmcSortingResponse.get("binNumber"));
    	wmsOrderFlowHelper.markVirtualPacketPicked(virtualPacketResponse.getData().get(0).getConsolidationPacketId());
    	packmanServiceHelper.markItemTillPack(orderLines.get(0).get("order_release_id_fk").toString());
    }
    
    @Test
    public void markOrderTillPK() throws UnsupportedEncodingException, JAXBException, ManagerException, JSONException, XMLStreamException, ParseException, InterruptedException{ 
    	
    	ArrayList<HashMap> items = null;
		HashMap<Integer, String> warehouseIdsAndPickTypes;
    	HashMap<String, String> lmcSortingResponse = null;
		VirtualPacketResponse virtualPacketResponse;
		boolean orderCheckout = false;
		boolean isBinFlushRequired = true;
    			
    	String orderID = "2147715026";
    	OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
    	String storeOrderId = orderEntry.getStoreOrderId();
    			
    	omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
    	
    	List<HashMap> orderLines = wmsOrderFlowHelper.getOrderDetails(storeOrderId);
    	warehouseIdsAndPickTypes = wmsOrderFlowHelper.getWarehouseIdsAndPickTypes(orderEntry.getOrderReleases().get(0).getOrderId().toString());
    	
    	// Push Order to Order wave for picking
    	if(!wmsOrderFlowHelper.isPushOrderToWaveApiHit(orderID)){
    		wmsOrderFlowHelper.pushOrderToWave(warehouseIdsAndPickTypes);
    	}
    	
    	if(!wmsServiceHelper.validateOrderInCoreOrderRelease(orderLines.get(0).get("order_release_id_fk").toString(), 8)){
    		log.info("Entry was not yet created in Core_order_release so inserting");
    		
    		for(HashMap orderLine : orderLines){
    			if(!wmsOrderFlowHelper.isOrderPushedToWMS(orderLine.get("order_release_id_fk").toString())){
    				wmsServiceHelper.creteRelaseInCore_order_release(orderLine.get("order_release_id_fk").toString());
    			}
    		}
    	}
    	
    	//Item checkout and Consolidation
    	for(HashMap orderLine : orderLines){
    		if(!wmsOrderFlowHelper.isOrderCheckedOut(orderLine.get("order_release_id_fk").toString())){
    			items = (ArrayList<HashMap>) wmsOrderFlowHelper.insertItems(orderLine.get("sku_id").toString(), orderLine.get("source_wh_id").toString(), (int)orderLine.get("quantity"), WMSConstants.BUYER1, 313, 1);
        		wmsOrderFlowHelper.itemCheckout(items, orderLine.get("order_release_id_fk").toString());
        		orderCheckout = true;
        		Thread.sleep(1000);
    		}
    		
    		if(!orderCheckout){
    			items = wmsOrderFlowHelper.getItemsAssociatedWithOrder(orderLine.get("order_release_id_fk").toString());
    		}
  /*  		
    		for(HashMap item : items){
    			if(!wmsOrderFlowHelper.isItemConsolidated(item.get("barcode").toString())){
    				lmcSortingResponse = wmsOrderFlowHelper.lmcSorting(item.get("barcode").toString());
    				if(lmcSortingResponse.get("statusMessage").contains("Please send item to packing desk.") || lmcSortingResponse.get("statusMessage").contains("PACK DESK")){
    					System.out.println("Please send item to packing desk.");
    					isBinFlushRequired = false;
    					
    				}else if(lmcSortingResponse.get("statusMessage").contains("Please keep item into bin/section for consolidation")){
    					System.out.println("Please keep item into bin/section for consolidation");
    					wmsOrderFlowHelper.prepareAndConsolidate(item.get("barcode").toString(), orderLine.get("source_wh_id").toString());
    					
    				}else if(lmcSortingResponse.get("statusMessage").contains("MULTI - Consolidation Bin")){	
    					System.out.println("MULTI - Consolidation Bin");
    					wmsOrderFlowHelper.prepareAndConsolidate(item.get("barcode").toString(), orderLine.get("source_wh_id").toString());
    					
    				}else{
    					Assert.fail(lmcSortingResponse.get("statusMessage"));
    				}
    			}
    		}*/
    	}
    	
    	if(isBinFlushRequired){
    //		virtualPacketResponse = wmsOrderFlowHelper.flushBin(lmcSortingResponse.get("binNumber"));
     //   	wmsOrderFlowHelper.markVirtualPacketPicked(virtualPacketResponse.getData().get(0).getConsolidationPacketId());
    	}
    	
   // 	packmanServiceHelper.markItemTillPack(orderLines.get(0).get("order_release_id_fk").toString());
    	
    }
    
}
