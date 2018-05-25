package com.myntra.apiTests.bi;


import java.util.HashMap;
import java.util.List;

import com.myntra.apiTests.dataproviders.BiUdpDataprovider;
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
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import com.myntra.apiTests.common.Myntra;

/**
 * Created by swatantra singh on 01/12/16.
 */
public class UdpApiTests extends BiUdpDataprovider {
    String dimension=null;
    String rollUpName=null;
    static Initialize init = new Initialize("/Data/configuration");
    static Logger log = Logger.getLogger(CouponServiceTests.class);
    APIUtilities apiUtil = new APIUtilities();
    MyntraListenerV2 myntListner = new MyntraListenerV2();
    //String queryId=null;
    String queryId=null;

    @Test(description = "Provides all the available report names with description")
    public void getReports() {
        MyntraService service = Myntra.getService(ServiceType.BI_UDPSERVICES, APINAME.UDPREPORTS, init.Configurations);
        HashMap<String, String> headersGetAdd = new HashMap<String, String>();
        headersGetAdd.put("Content-Type", "application/x-www-form-urlencoded");
        headersGetAdd.put("username", "swatantra.singh@myntra.com");
        headersGetAdd.put("metrics", "frequency:DESC");
        headersGetAdd.put("dimensions", "search_query");
        headersGetAdd.put("fromTime", "1454137181000");
        headersGetAdd.put("toTime", "1454137181000");
        headersGetAdd.put("limit", "100");
        headersGetAdd.put("defaultDateColumn", "date");
        RequestGenerator req = new RequestGenerator(service,headersGetAdd);
        String response = req.respvalidate.returnresponseasstring();
        List<String> dimensionList= JsonPath.read(response, "$..reportFactTable");
        dimension=dimensionList.get(4);
        AssertJUnit.assertEquals(200, req.response.getStatus());
    }

    @Test(dependsOnMethods = "getReports",description = "Provides valid dimensions for the given report name with description")
    public void getReportsUDPDIMENSIONS() {
        MyntraService service = Myntra.getService(ServiceType.BI_UDPSERVICES, APINAME.UDPDIMENSIONS, init.Configurations);
        System.out.println(dimension);
        service.URL = apiUtil.prepareparameterizedURL(service.URL,dimension);
        HashMap<String, String> headersGetAdd = new HashMap<String, String>();
        headersGetAdd.put("username", "swatantra.singh@myntra.com");
        RequestGenerator req = new RequestGenerator(service,headersGetAdd);
        String response = req.respvalidate.returnresponseasstring();
        AssertJUnit.assertEquals(200, req.response.getStatus());


    }
    @Test(dependsOnMethods = "getReports",description = "Provides all the metrics for the given report name.")
    public void getReportsUDPUDPMETRICS() {
        MyntraService service = Myntra.getService(ServiceType.BI_UDPSERVICES, APINAME.UDPMETRICS, init.Configurations);
        System.out.println(dimension);
        service.URL = apiUtil.prepareparameterizedURL(service.URL,dimension);
        HashMap<String, String> headersGetAdd = new HashMap<String, String>();
        headersGetAdd.put("username", "swatantra.singh@myntra.com");
        RequestGenerator req = new RequestGenerator(service,headersGetAdd);
        String response = req.respvalidate.returnresponseasstring();
        List<String> rollupNameList= JsonPath.read(response, "$..rollUp..rollUpName");
        rollUpName=rollupNameList.get(1);
        AssertJUnit.assertEquals(200, req.response.getStatus());
    }
    @Test(dependsOnMethods = "getReports")
    public void getReportsUdpBrandValues() {
        MyntraService service = Myntra.getService(ServiceType.BI_UDPSERVICES, APINAME.UDPBRANDVALUES, init.Configurations);
        System.out.println(dimension);
        service.URL = apiUtil.prepareparameterizedURL(service.URL,dimension);
        HashMap<String, String> headersGetAdd = new HashMap<String, String>();
        headersGetAdd.put("username", "swatantra.singh@myntra.com");
        RequestGenerator req = new RequestGenerator(service,headersGetAdd);
        String response = req.respvalidate.returnresponseasstring();
        AssertJUnit.assertEquals(200, req.response.getStatus());
    }
    @Test(dataProvider = "GetMetrics",description = "Provides all the values metrics for the given dimension values *Direct API")
    public void getMetricsGroupedByDimensionWithFilters(String metrics,String metricSortOrderMap,String dimensions,String dimensionVAlueMap,String connector,String fromTime,String toTime,String limit,String sendTotal,String DefaultDateColoumn)
    {
        String createGetMetricsPayload=prepareCreategetMetricsPayloadURLEncoded(metrics,metricSortOrderMap,dimensions,dimensionVAlueMap,connector,fromTime,toTime,limit,sendTotal,DefaultDateColoumn);
        MyntraService service = Myntra.getService(ServiceType.BI_UDPSERVICES,APINAME.UDPMETRICSBYDIMENSION, init.Configurations,
                new String[] { createGetMetricsPayload },
                PayloadType.URLENCODED, PayloadType.JSON);
        System.out.println(service.URL);
        //service.URL = apiUtil.prepareparameterizedURL(service.URL,new String[]{dimension,rollUpName});

        System.out.println("\nPrinting udpmetricsbydimension API URL :"+service.URL);
        System.out.println("\nPrinting udpmetricsbydimension API Payload :\n\n"+service.Payload);

        HashMap<String, String> headersGetAdd = new HashMap<String, String>();
        headersGetAdd.put("Content-Type", "application/x-www-form-urlencoded");
        headersGetAdd.put("username", "swatantra.singh@myntra.com");
        headersGetAdd.put("apiKey", "L2li15tf7A");
        RequestGenerator req = new RequestGenerator(service,headersGetAdd);
        String jsonResp = req.respvalidate.returnresponseasstring();
        List<String> brand= JsonPath.read(jsonResp, "$.result..brand");
        for(int i=0;i<brand.size();i++)
        {
            Assert.assertTrue(brand.get(i).equalsIgnoreCase("puma")||brand.get(i).equalsIgnoreCase("Adidas"));
        }
    }
    @Test(dataProvider = "GetMetricsBasedOnDateRange",description = "Provide metrics value based on the dimension and date ranges")
    public void getMetricsBasedOnDateRange(String metrics,String metricSortOrderMap,String dimensions,String dimensionVAlueMap,String connector,String dateRange1,String dateRange2,String limit,String sendTotal,String DefaultDateColoumn,String seriesLimit)
    {
        String createGetMetricsPayload=prepareGetMetricsBasedOnRangePayloadURLEncoded(metrics,metricSortOrderMap,dimensions,dimensionVAlueMap,connector,dateRange1,dateRange2,limit,sendTotal,DefaultDateColoumn,seriesLimit);
        MyntraService service = Myntra.getService(ServiceType.BI_UDPSERVICES,APINAME.UDPMETRICSBASEDONDATERANGE, init.Configurations,
                new String[] { createGetMetricsPayload },
                PayloadType.URLENCODED, PayloadType.JSON);
        System.out.println(service.URL);
        //service.URL = apiUtil.prepareparameterizedURL(service.URL,new String[]{dimension,rollUpName});

        System.out.println("\nPrinting GetMetricsBasedOnDateRange API URL :"+service.URL);
        System.out.println("\nPrinting GetMetricsBasedOnDateRange API Payload :\n\n"+service.Payload);

        HashMap<String, String> headersGetAdd = new HashMap<String, String>();
        headersGetAdd.put("Content-Type", "application/x-www-form-urlencoded");
        headersGetAdd.put("username", "swatantra.singh@myntra.com");
        headersGetAdd.put("apiKey", "L2li15tf7A");
        RequestGenerator req = new RequestGenerator(service,headersGetAdd);
        String jsonResp = req.respvalidate.returnresponseasstring();
        List<String> brand= JsonPath.read(jsonResp, "$.DR1..brand");
        for(int i=0;i<brand.size();i++)
        {
            Assert.assertTrue(brand.get(i).equalsIgnoreCase("puma")||brand.get(i).equalsIgnoreCase("Adidas"));
        }
    }

    private String prepareCreategetMetricsPayloadURLEncoded(String metrics,String metricSortOrderMap,String dimensions,String dimensionVAlueMap,String connector,String fromTime,String toTime,String limit,String sendTotal,String DefaultDateColoumn)
    {
        StringBuffer encodedURL = new StringBuffer();
        encodedURL.append("metrics=");
        encodedURL.append(metrics.trim());
        encodedURL.append("&");

        encodedURL.append("metricSortOrderMap=");
        encodedURL.append(metricSortOrderMap.trim());
        encodedURL.append("&");
        encodedURL.append("dimensions=");
        encodedURL.append(dimensions.trim());
        encodedURL.append("&");

        encodedURL.append("dimensionsValueMap=");
        encodedURL.append(dimensionVAlueMap.trim());
        encodedURL.append("&");

        encodedURL.append("connector=");
        encodedURL.append(connector.trim());
        encodedURL.append("&");

        encodedURL.append("fromTime=");
        encodedURL.append(fromTime.trim());
        encodedURL.append("&");

        encodedURL.append("toTime=");
        encodedURL.append(toTime.trim());
        encodedURL.append("&");

        encodedURL.append("limit=");
        encodedURL.append(limit.trim());
        encodedURL.append("&");

        encodedURL.append("sendTotal=");
        encodedURL.append(sendTotal.trim());
        encodedURL.append("&");

        encodedURL.append("defaultDateColumn=");
        encodedURL.append(DefaultDateColoumn.trim());



        return encodedURL.toString();
    }
    private String prepareGetMetricsBasedOnRangePayloadURLEncoded(String metrics,String metricSortOrderMap,String dimensions,String dimensionVAlueMap,String connector,String dateRange1,String dateRange2,String limit,String sendTotal,String DefaultDateColoumn,String seriesLimit)
    {
        StringBuffer encodedURL = new StringBuffer();
        encodedURL.append("metrics=");
        encodedURL.append(metrics.trim());
        encodedURL.append("&");

        encodedURL.append("metricSortOrderMap=");
        encodedURL.append(metricSortOrderMap.trim());
        encodedURL.append("&");
        encodedURL.append("dimensions=");
        encodedURL.append(dimensions.trim());
        encodedURL.append("&");

        encodedURL.append("dimensionsValueMap=");
        encodedURL.append(dimensionVAlueMap.trim());
        encodedURL.append("&");

        encodedURL.append("connector=");
        encodedURL.append(connector.trim());
        encodedURL.append("&");

        encodedURL.append("dateRange1=");
        encodedURL.append(dateRange1.trim());
        encodedURL.append("&");

        encodedURL.append("dateRange2=");
        encodedURL.append(dateRange2.trim());
        encodedURL.append("&");

        encodedURL.append("limit=");
        encodedURL.append(limit.trim());
        encodedURL.append("&");

        encodedURL.append("sendTotal=");
        encodedURL.append(sendTotal.trim());
        encodedURL.append("&");

        encodedURL.append("defaultDateColumn=");
        encodedURL.append(DefaultDateColoumn.trim());
        encodedURL.append("&");

        encodedURL.append("seriesLimit=");
        encodedURL.append(seriesLimit.trim());



        return encodedURL.toString();
    }


}
