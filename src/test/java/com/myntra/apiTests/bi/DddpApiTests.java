package com.myntra.apiTests.bi;


import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.DdpDataProvider;
import com.myntra.apiTests.portalservices.all.CouponServiceTests;
import org.apache.log4j.Logger;
import org.testng.Assert;
import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.MyntraListenerV2;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import org.testng.annotations.Test;

/**
 * Created by swatantra singh on 24/11/16.
 */
public class DddpApiTests extends DdpDataProvider {
    static Initialize init = new Initialize("/Data/configuration");
    static Logger log = Logger.getLogger(CouponServiceTests.class);
    APIUtilities apiUtil = new APIUtilities();
    MyntraListenerV2 myntListner = new MyntraListenerV2();
    //String queryId=null;
    String queryId=null;

    @Test(priority=1,dataProvider = "submitQuery")
    public void SubmitQuery(String submitQuery1,String submitQuery2,String submitQuery3,String submitQuery4,String submitQuery5,String getStatus)
    {

        String createMasterNotificationPayload=prepareCreateBiDdpPayloadURLEncoded(submitQuery1,submitQuery2,submitQuery3,submitQuery4,submitQuery5);
        MyntraService service = Myntra.getService(ServiceType.BI_BISERVICE,APINAME.DDPSUBMITQUERY, init.Configurations,
                new String[] { createMasterNotificationPayload },
                PayloadType.URLENCODED, PayloadType.JSON);

        System.out.println("\nPrinting Submit Query API URL :"+service.URL);
        System.out.println("\nPrinting Submit Query API Payload :\n\n"+service.Payload);

        HashMap<String, String> headersGetAdd = new HashMap<String, String>();
        headersGetAdd.put("Content-Type", "application/x-www-form-urlencoded");
        headersGetAdd.put("submitted-by", "swatantra.singh@myntra.com");
        RequestGenerator req = new RequestGenerator(service,headersGetAdd);
        String jsonResp = req.respvalidate.returnresponseasstring();
        queryId =JsonPath.read(jsonResp,"$.queryId").toString().replaceAll("[^A-Za-z0-9/ ]","");
        String status=JsonPath.read(jsonResp,"$.actions..getStatus").toString().replaceAll("[^A-Za-z0-9/.:-]","");
        Assert.assertTrue(status.contains(getStatus),"Get status Url Missmatch"+status);




    }
    @Test(priority=2)
    public void pollQuery()throws Exception
    {
        String successMsg=null;
        long start_time = System.currentTimeMillis();
        long wait_time = 600000;
        long end_time = start_time + wait_time;

        MyntraService service = Myntra.getService(ServiceType.BI_BISERVICE,APINAME.DDPPOLLQUERYSTATUS, init.Configurations);
        service.URL = apiUtil.prepareparameterizedURL(service.URL,queryId);
        System.out.println(service.URL);
        RequestGenerator req = new RequestGenerator(service);
        System.out.println(req.respvalidate.returnresponseasstring());
        String jsonResp = req.respvalidate.returnresponseasstring();
        successMsg =JsonPath.read(jsonResp,"$.queryStatus").toString().replaceAll("[^A-Za-z0-9/ ]","");
        while (successMsg.equalsIgnoreCase("running"))
        {
            Thread.sleep(5000);
            RequestGenerator req1 = new RequestGenerator(service);
            String jsonResp1 = req1.respvalidate.returnresponseasstring();
            successMsg =JsonPath.read(jsonResp1,"$.queryStatus").toString().replaceAll("[^A-Za-z0-9/ ]","");
            System.out.println(successMsg);
        }
        Assert.assertTrue(successMsg.equalsIgnoreCase("completed") );

    }

    @Test(priority=3,dataProvider = "DownloadResultset")
    public void DownloadResultSet(String successmsg1,String successmg2)
    {
        MyntraService service = Myntra.getService(ServiceType.BI_BISERVICE,APINAME.DDPDOWNLOADRESULTSET, init.Configurations);
        service.URL = apiUtil.prepareparameterizedURL(service.URL,queryId);
        service.responsedataformat=PayloadType.STRING;
        service.payloaddataformat=PayloadType.STRING;
        System.out.println(service.URL);
        RequestGenerator req = new RequestGenerator(service);
        String response=req.respvalidate.returnresponseasstring();
        Assert.assertTrue(response.contains(successmsg1)&&response.contains(successmg2),"invalid response"+response);





    }
    private String prepareCreateBiDdpPayloadURLEncoded(String queryName, String queryDescription, String query, String unload, String clusterID)
    {
        StringBuffer encodedURL = new StringBuffer();
        encodedURL.append("query-name=");
        encodedURL.append(queryName.trim());
        encodedURL.append("&");

        encodedURL.append("query-description=");
        encodedURL.append(queryDescription.trim());
        encodedURL.append("&");

        encodedURL.append("query=");
        encodedURL.append(query.trim());
        encodedURL.append("&");

        encodedURL.append("unload=");
        encodedURL.append(unload.trim());
        encodedURL.append("&");

        encodedURL.append("clusterID=");
        encodedURL.append(clusterID.trim());

        return encodedURL.toString();
    }
}
