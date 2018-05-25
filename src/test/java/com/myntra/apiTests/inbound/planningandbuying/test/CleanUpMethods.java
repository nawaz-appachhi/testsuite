package com.myntra.apiTests.inbound.planningandbuying.test;

import com.myntra.apiTests.inbound.helper.planningandbuying.Constants.*;
import com.myntra.apiTests.inbound.helper.planningandbuying.JeevesHelper;
import com.myntra.apiTests.inbound.helper.planningandbuying.JeevesValidator;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

public class CleanUpMethods extends BaseTest{

    JeevesHelper jeeveshelper;
    JeevesValidator jeevesValidator;
    Logger log;
    HashMap<String, String> create_baseoi_headers;
    Svc svc = null;

    @BeforeClass(alwaysRun = true)
    public void init()
    {
        jeeveshelper = new JeevesHelper();
        jeevesValidator = new JeevesValidator();
        log = Logger.getLogger(CleanUpMethods.class);
        create_baseoi_headers = jeeveshelper.Headers(HEADERS.AUTHORIZATION, HEADERS.ACCEPT,
                HEADERS.CONTENTTYPE);
    }

    @Test
    public void cleanUpOI() throws Exception {

        String cancelPayload = "{\"status\":\"CANCELLED\"}";
        String[] createdBy = {"sachin.khurana"};
        String[] source = null;
        String[] status = {"DRAFT"};
        String[] ids = fetchBuyPlanId(createdBy,source,status,"");

       for(int j=0;j<ids.length;j++) {

            log.debug("ID: "+ids[j]);
            svc = HttpExecutorService.executeHttpService(JEEVES_PATH.OI_STATUS_UPDATE + ids[j] + "?" + ROLES.CM_ROLE, null,
                    SERVICE_TYPE.OI_SVC.toString(), HTTPMethods.PUT, cancelPayload, create_baseoi_headers);
            String updateResponse = svc.getResponseBody();
            jeevesValidator.validateTheResponseStatus(updateResponse,"UPDATE_OI_HEADERS");
            JSONObject updateObject = new JSONObject(updateResponse);
            JSONArray updateData = updateObject.getJSONArray("data");
            JSONObject obj = updateData.getJSONObject(0);
            Object json_string = obj.get("status");
            String newStatus =json_string.toString();
            Assert.assertEquals(newStatus,"CANCELLED","The BuyPlan of Id "+ids[j]+" was not cancelled");
            log.debug("The BuyPlan of Id "+ids[j]+" was cancelled");
        }

    }

    public String[] fetchBuyPlanId(String[] createdBy,String[] source,String[] status,String createdOn) throws Exception {

        //        Query Example: "createdBy.eq:dipti___source.eq:TSR___status.eq:DRAFT___createdOn.gt:1484505000000&sortBy=lastModifiedOn&sortOrder=DESC";
        String query ="";
        if(createdBy!=null && createdBy.length>0)
        {
            query+="createdBy.in:";
            for (int i = 0; i < createdBy.length; i++)
            {
                query += createdBy[i] + ",";
            }
            query=query.substring(0,query.length()-1)+"___";

        }
        if(source!=null && source.length>0)
        {
            query+="source.in:";
            for (int i = 0; i < source.length; i++)
            {
                query += source[i] + ",";
            }
            query=query.substring(0,query.length()-1)+"___";

        }
        if(status!=null && status.length>0)
        {
            query+="status.in:";
            for (int i = 0; i < status.length; i++)
            {
                query += status[i] + ",";
            }
            query=query.substring(0,query.length()-1)+"___";


        }
        if(createdOn!=null && createdOn.length()>0)
        {
            query+="createdOn.gt:"+createdOn+"___";
        }
        query +=  "&sortBy=lastModifiedOn&sortOrder=DESC&start=0&fetchSize=-1";
        System.out.println(query);

//        Search Service to get all the Buy Plan ids based on the filter provided
        svc = HttpExecutorService.executeHttpService(JEEVES_PATH.SEARCH_QUERY_OI + query,
                null, SERVICE_TYPE.OI_SVC.toString(),
                HTTPMethods.GET, "", create_baseoi_headers);
        String response = svc.getResponseBody();

//        Validate the search response
        jeevesValidator.validateTheResponseStatus(response,"SEARCH_OI");

//        fetch the BuyPlan Id from each of the data returned
        JSONObject object = new JSONObject(response);
        JSONArray data = object.getJSONArray("data");
        log.debug("Total number buyplan id fetched: "+data.length());
        String[] ids= new String[data.length()];
        for(int i=0;i<data.length();i++)
        {
            JSONObject obj = data.getJSONObject(i);
            Object json_string = obj.get("id");
            ids[i]=json_string.toString();
        }
        return ids;

    }
}
