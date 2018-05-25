package com.myntra.apiTests.erpservices.wms.Tests;

import com.myntra.apiTests.erpservices.wms.dp.ROTestDP;
import com.myntra.apiTests.erpservices.wms.WMSHelper;
import com.myntra.client.wms.response.RoItemResponse;
import com.myntra.client.wms.response.RoResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.testbase.BaseTest;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

public class ROTests extends BaseTest {
    static Initialize init = new Initialize("/Data/configuration");
    static Logger log = Logger.getLogger(ROTests.class);
    static WMSHelper WMSItemTransitionHelper1 = new WMSHelper();

    @Test(groups = {"Regression"}, dataProvider = "addARO", dataProviderClass = ROTestDP.class)
    public void addaRO(long vendor_id, long wh_id,
			String roMode,String roType,long buyerid,String respose,long response_code,String response_message,String response_type,long totalcount) throws InterruptedException, UnsupportedEncodingException, JAXBException {
    	RoResponse roResponse=WMSHelper.addRO(vendor_id, wh_id, roMode, roType,buyerid);
    	long ro_id=roResponse.getData().get(0).getId();
    	long status_code=roResponse.getStatus().getStatusCode();
    	String status_message=roResponse.getStatus().getStatusMessage();
    	String status_type =roResponse.getStatus().getStatusType().toString();
    	long status_total_count=roResponse.getStatus().getTotalCount();
    	Map hm = new HashMap();
    	hm=DBUtilities.exSelectQueryForSingleRecord("select * from return_order where id="+ro_id, "wms");
    	String ro_status=hm.get("ro_status").toString();
    	String ro_mode=hm.get("ro_mode").toString();
    	String ro_type=hm.get("ro_type").toString();
    	Assert.assertEquals(response_code, status_code, " status_code is");
    	Assert.assertEquals(response_message, status_message, "status_message is");
    	Assert.assertEquals(response_type, status_type, "status_type is");
    	Assert.assertEquals(totalcount, status_total_count, "status_total_count is");
    	Assert.assertEquals("CREATED", ro_status, "ro_status is");
    	Assert.assertEquals(roMode, ro_mode, "ro_mode  is");
    	Assert.assertEquals(roType, ro_type, "ro_type  is");
    	//Assert.assertEquals(response_code, status_code, "Status code is");
    	
    	

    }
    @Test(groups = {"Regression"}, dataProvider = "addAROitem", dataProviderClass = ROTestDP.class)
    public void addaROitem(long vendor_id, long wh_id,String roMode,String roType,long buyerid,String respose,
    		String itembarcode,long response_code,String response_message,String response_type) throws InterruptedException, UnsupportedEncodingException, JAXBException {
    	RoResponse roResponse=WMSHelper.addRO(vendor_id, wh_id, roMode, roType,buyerid);
    	long ro_id=roResponse.getData().get(0).getId();
    	RoItemResponse roItemResponse =WMSHelper.addItemRo(ro_id, itembarcode);
    	long status_code=roItemResponse.getStatus().getStatusCode();
    	String status_message=roItemResponse.getStatus().getStatusMessage();
    	String status_type =roItemResponse.getStatus().getStatusType().toString();
    	//long status_total_count=roItemResponse.getStatus().getTotalCount();
    	Map hm = new HashMap();
    	hm=DBUtilities.exSelectQueryForSingleRecord("select * from ro_item where ro_id="+ro_id, "wms");
    	String ro_item_status=hm.get("ro_item_status").toString();
    	Assert.assertEquals(response_code, status_code, " status_code is");
    	Assert.assertEquals(response_message, status_message, "status_message is");
    	Assert.assertEquals(response_type, status_type, "status_type is");
    
    	Assert.assertEquals("ACTIVE", ro_item_status, "ro_status is");
    	
    	//Assert.assertEquals(response_code, status_code, "Status code is");
    	String query = "delete from ro_item where ro_id="+ro_id;
    	DBUtilities.exUpdateQuery(query, "wms");
    	

    }
}
