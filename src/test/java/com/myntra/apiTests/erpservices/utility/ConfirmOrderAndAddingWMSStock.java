package com.myntra.apiTests.erpservices.utility;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.test.commons.testbase.BaseTest;

public class ConfirmOrderAndAddingWMSStock extends BaseTest {
	
	static Initialize init = new Initialize("/Data/configuration");
	private static Logger log = Logger.getLogger(MarkOrderDeliverTest.class);
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	
	
	@Test(description="This testcase is to confirm order with hack and inserting sku details in wms")
	public void confirmOrderAndAddingWMSStock() throws Exception {
		
		//get OrderNumber from User
		String storeOrderId = System.getenv("storeOrderId").replace("-", "");
		
		if(storeOrderId.isEmpty() || storeOrderId.equalsIgnoreCase("")){
			log.info("Please provide correct order number");
		}else{
			//Confirm order with hack
			String orderID = omsServiceHelper.getOrderEntryByStoreOrderID(storeOrderId).getId().toString();
			omsServiceHelper.checkReleaseStatusForOrderWithHack(orderID, EnumSCM.WP);
			
			//Insert skus in WMS
			insertItemInWMS(orderID);
		}
	}


	/**
	 * This is to insert data in wms item and item info table
	 * @param orderID
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	private void insertItemInWMS(String orderID) throws UnsupportedEncodingException, JAXBException {
		// TODO Auto-generated method stub
		List<OrderReleaseEntry> orderReleaseEntries = omsServiceHelper.getOrderEntry(orderID).getOrderReleases();
		for(OrderReleaseEntry orderReleaseEntry:orderReleaseEntries){
			List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseEntry.getId().toString());
			
			for(OrderLineEntry orderLineEntry:orderLineEntries){
				String skuId = orderLineEntry.getSkuId().toString();
				String warehouseId = orderLineEntry.getDispatchWarehouseId().toString();
				int qty = orderLineEntry.getQuantity();
				wmsServiceHelper.insertItem(skuId, warehouseId, qty);
			}
		}
		
	}
}
