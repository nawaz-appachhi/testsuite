package com.myntra.apiTests.erpservices.oms.Test;

import argo.saj.InvalidSyntaxException;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.commons.exception.ManagerException;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.http.client.ClientProtocolException;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class CreateOrder extends BaseTest{

    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    End2EndHelper end2EndHelper = new End2EndHelper();
    WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    
    @Test(enabled=true)
    public void createInvoice() throws JAXBException, ClientProtocolException, IOException, InvalidSyntaxException, JSONException, ParseException, SCMExceptions, ManagerException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, InterruptedException, SQLException, XMLStreamException{
        
        
//        int noOfOrders = 1;//Integer.valueOf(System.getenv("noOfOrders"));
//        
//        String skuId[] = {"3831:1"};
//        String orderReleaseId = null;
//        String orderID = null;
//        ArrayList<String> orders = new ArrayList<String>();
//        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{"3831:36:10000:0:21:1"},"ON_HAND");
//        imsServiceHelper.updateInventoryForSeller(new String[]{"3831:36:10000:0:21"},"ON_HAND");
//        String login = "bountytestuser001@myntra.com";
//        for(int i=0;i<noOfOrders;i++){
//            
//            orderID = end2EndHelper.createOrder(login, "123456", "560068", skuId, "", false, false, true, "", false);
//            OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
//            orderReleaseId = orderEntry.getOrderReleases().get(0).getId()+"";
//            orders.add(i, ""+orderReleaseId);
//        }
//        
//        for(int i=0;i<noOfOrders;i++){
//            
//            System.out.print(orders.get(i)+"L,");
//        }
//        
//        omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP"); //    //2147602075
        
    	   // ProcessRelease processRelease = new ProcessRelease();
    	   // processRelease.processReleaseToStatus(new ReleaseEntry.Builder("2147613894926", ReleaseStatus.DL).build());
    }

}