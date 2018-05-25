package com.myntra.apiTests.portalservices.appservices;

import argo.saj.InvalidSyntaxException;
import com.myntra.apiTests.dataproviders.SearchDP;
import com.myntra.apiTests.InitializeFramework;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.JSONUtilities;
import com.myntra.lordoftherings.MyntAssert;

import com.myntra.lordoftherings.gandalf.PayloadType;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;

public class SearchTests extends SearchDP
{
	static Initialize init = InitializeFramework.init;
	static Logger log = Logger.getLogger(SearchTests.class);

    
	@Test(groups = { },dataProvider = "getFirstPageOfSearchDataProvider" )
    public void getFirstPageOfSearch(String searchString)
    {
        ServiceType serviceundertest = ServiceType.PORTAL_MOBILEAPPWEBSERVICES;
        APINAME apiundertest = APINAME.MOBILEAPPSEARCHGETFIRSTPAGE;
        MyntraService service = Myntra.getService(serviceundertest,apiundertest, init.Configurations, new String[]{searchString});
        log.info(service.URL);
        //System.out.println(service.Payload);
        RequestGenerator request = new RequestGenerator(service);
        MyntAssert.assertEquals("Response Code",200, request.response.getStatus());
        MultivaluedMap<String, Object> headers = request.response.getHeaders();
        MyntAssert.assertEquals("Response Headers :", "success", responseValidaion(headers));
        MyntAssert.setJsonResponse(request.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }


    @Test (groups = { })
    public void applyFilter()
    {
        ServiceType serviceundertest = ServiceType.PORTAL_MOBILEAPPWEBSERVICES;
        APINAME apiundertest = APINAME.MOBILEAPPSEARCHAPPLYFILTER;
        MyntraService service = Myntra.getService(serviceundertest,apiundertest, init.Configurations);
        log.info(service.URL);
        RequestGenerator request = new RequestGenerator(service);
        MyntAssert.assertEquals("Response Code",200, request.response.getStatus());
        MultivaluedMap<String, Object> headers = request.response.getHeaders();
        MyntAssert.assertEquals("Response Headers :", "success", responseValidaion(headers));
        MyntAssert.setJsonResponse(request.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
    
    @Test(groups = { },dataProvider = "dp_webservice_applyfilter_positive_cases" )
    public void applySingleFilter(String action, String artifact, String value)
    {
    	String[] payloadparams=new String[]{ action, artifact, value }; 
        ServiceType serviceundertest = ServiceType.PORTAL_MOBILEAPPWEBSERVICES;
        APINAME apiundertest = APINAME.MOBILEAPPSEARCHAPPLYFILTER;
        MyntraService service = Myntra.getService(serviceundertest,apiundertest, init.Configurations, payloadparams, PayloadType.JSON,PayloadType.JSON);
        log.info(service.URL);
        RequestGenerator request = new RequestGenerator(service);
        MyntAssert.assertEquals("Response Code",200, request.response.getStatus());
        MultivaluedMap<String, Object> headers = request.response.getHeaders();
        MyntAssert.assertEquals("Response Headers :", "success", responseValidaion(headers));
        MyntAssert.setJsonResponse(request.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
    
    @Test(groups = { },dataProvider = "dp_Webservice_ApplyTwoFilters_Positive_Cases" )
    public void applyTwoFilters(String action1, String artifact1, String value1, String action2, String artifact2, String value2)
    {
    	String[] payloadparams=new String[]{ action1, artifact1, value1, action2, artifact2, value2 };
		String customPayloadDir = System.getProperty("user.dir")+File.separator+"Data"+File.separator+"payloads"+File.separator+"JSON";
		ServiceType serviceundertest = ServiceType.PORTAL_MOBILEAPPWEBSERVICES;
        APINAME apiundertest = APINAME.MOBILEAPPSEARCHAPPLYFILTER;
        //getPayloadAsString("mobileappsearchapplytwofilter", payloadparams);
        MyntraService service = Myntra.getService(serviceundertest, apiundertest, init.Configurations, getPayloadAsString("mobileappsearchapplytwofilter", payloadparams));
        log.info(service.URL);
        RequestGenerator request = new RequestGenerator(service);
        MyntAssert.assertEquals("Response Code",200, request.response.getStatus());
        MultivaluedMap<String, Object> headers = request.response.getHeaders();
        MyntAssert.assertEquals("Response Headers :", "success", responseValidaion(headers));
        MyntAssert.setJsonResponse(request.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
    
    @Test(groups = { },dataProvider = "dp_webservice_applyAllfilters_positive_cases" )
    public void applyAllFilters(String action1, String artifact1, String value1, String action2, String artifact2, String value2, String action3, String artifact3, String value3, String action4, String artifact4, String value4, String action5, String artifact5, String value5)
    {
    	String[] payloadparams=new String[]{ action1, artifact1, value1, action2, artifact2, value2, action3, artifact3, value3, action4, artifact4, value4, action5, artifact5, value5 };
		String customPayloadDir = System.getProperty("user.dir")+File.separator+"Data"+File.separator+"payloads"+File.separator+"JSON";
		ServiceType serviceundertest = ServiceType.PORTAL_MOBILEAPPWEBSERVICES;
        APINAME apiundertest = APINAME.MOBILEAPPSEARCHAPPLYFILTER;
        //System.out.println(getPayloadAsString("mobileappsearchapplyallfilters", payloadparams));
        MyntraService service = Myntra.getService(serviceundertest, apiundertest, init.Configurations, getPayloadAsString("mobileappsearchapplyallfilters", payloadparams));
        log.info(service.URL);
        RequestGenerator request = new RequestGenerator(service);
        MyntAssert.assertEquals("Response Code",200, request.response.getStatus());
        MultivaluedMap<String, Object> headers = request.response.getHeaders();
        MyntAssert.assertEquals("Response Headers :", "success", responseValidaion(headers));
        MyntAssert.setJsonResponse(request.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }

    @Test(groups={ }, dataProvider="mobileGetNextPageOfSearchDataProvider")
    public void getNextPageOfSearch(String action, String currentPageNum)
    {
    	String[] payloadParams = new String[]{ action, currentPageNum }; 
        ServiceType serviceundertest = ServiceType.PORTAL_MOBILEAPPWEBSERVICES;
        APINAME apiundertest = APINAME.MOBILEAPPSEARCHGETNEXTPAGE;
        MyntraService service = Myntra.getService(serviceundertest,apiundertest, init.Configurations, payloadParams);
        log.info(service.URL);
        RequestGenerator request = new RequestGenerator(service);
        MyntAssert.assertEquals("Response Code",200, request.response.getStatus());
        MultivaluedMap<String, Object> headers = request.response.getHeaders();
        MyntAssert.assertEquals("Response Headers :", "success", responseValidaion(headers));
        MyntAssert.setJsonResponse(request.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }

    @Test(groups={ }, dataProvider="mobileSearchSortByDataProvider")
    public void sortBy(String sortParams)
    {
    	String[] payloadParams = new String[]{ sortParams };
        ServiceType serviceundertest = ServiceType.PORTAL_MOBILEAPPWEBSERVICES;
        APINAME apiundertest = APINAME.MOBILEAPPSEARCHSORTBY;
        MyntraService service = Myntra.getService(serviceundertest,apiundertest, init.Configurations, payloadParams);
        log.info(service.URL);
        RequestGenerator request = new RequestGenerator(service);
        MyntAssert.assertEquals("Response Code",200, request.response.getStatus());
        MultivaluedMap<String, Object> headers = request.response.getHeaders();
        MyntAssert.assertEquals("Response Headers :", "success", responseValidaion(headers));
        MyntAssert.setJsonResponse(request.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
    
    @Test(groups = { },dataProvider = "mobileWebServicesRemoveFilterDataProvider" )
    public void removeFilter(String action, String artifact, String value)
    {
    	String [] payloadparams = new String[]{ action, artifact, value };
        ServiceType serviceundertest = ServiceType.PORTAL_MOBILEAPPWEBSERVICES;
        APINAME apiundertest = APINAME.MOBILEAPPSEARCHAPPLYFILTER;
        JSONUtilities jsonUtil = new JSONUtilities();
        String nodeValue = null;
        try
		{
			nodeValue = jsonUtil.GetNodeValue_Argo("searchnext.requestObj.fq", getPayloadAsString("mobileappsearchapplyfilter", payloadparams));
			int firstIndex = nodeValue.indexOf("=");
			int lastIndex = nodeValue.lastIndexOf("}");
			nodeValue = nodeValue.substring((firstIndex+1), lastIndex);
			nodeValue = nodeValue.replaceAll("'", "\"").trim();
			//System.out.println(nodeValue);
			
		} catch (InvalidSyntaxException e)
		{
			e.printStackTrace();
		}
        String finalPayload = getPayloadAsString("mobileappsearchapplyfilter", payloadparams, nodeValue);
        //System.out.println(finalPayload);
        MyntraService service = Myntra.getService(serviceundertest,apiundertest, init.Configurations, finalPayload);
        log.info(service.URL);
        //System.out.println(service.Payload);
        RequestGenerator request = new RequestGenerator(service);
        MyntAssert.assertEquals("Response Code",200, request.response.getStatus());
        MultivaluedMap<String, Object> headers = request.response.getHeaders();
        MyntAssert.assertEquals("Response Headers :", "success", responseValidaion(headers));
        MyntAssert.setJsonResponse(request.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
    
    private String getPayloadAsString(String payloadName, String[] payloadparams){
    	String customPayloadDir = System.getProperty("user.dir")+File.separator+"Data"+File.separator+"payloads"+File.separator+"JSON";
    	StringBuffer sb = new StringBuffer();
    	String finalPayload = "";
    	Scanner sc = null;
		try {
			sc = new Scanner(new File(customPayloadDir+File.separator+payloadName));
			while(sc.hasNextLine())
	    		sb.append(sc.nextLine()+"\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	sc.close();
    	finalPayload = sb.toString();
    	for(int i=0;i<payloadparams.length;i++){
    		finalPayload = finalPayload.replace("${"+i+"}", payloadparams[i]);    		
    	}
    	return finalPayload;
    }
    
    private String getPayloadAsString(String payloadName, String[] payloadparams, String toReplace){
    	String customPayloadDir = System.getProperty("user.dir")+File.separator+"Data"+File.separator+"payloads"+File.separator+"JSON";
    	StringBuffer sb = new StringBuffer();
    	String finalPayload = "";
    	Scanner sc;
		try {
			sc = new Scanner(new File(customPayloadDir+File.separator+payloadName));
			while(sc.hasNextLine())
	    		sb.append(sc.nextLine()+"\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	finalPayload = sb.toString();
    	for(int i=0;i<payloadparams.length;i++){
    		finalPayload = finalPayload.replace("${"+i+"}", payloadparams[i]);    		
    	}
    	finalPayload = finalPayload.replace(toReplace.trim(), removeFilterString(payloadparams,toReplace));
    	//System.out.println(finalPayload);
    	return finalPayload;
    }
    
    private String removeFilterString(String[] payloadparams, String toReplace){
    	if(payloadparams[1].equalsIgnoreCase(":brand"))
    		return "\"brands_filter_facet:(\\\""+payloadparams[2]+"\\\")\",\n"+toReplace;   		
    	else if(payloadparams[1].equalsIgnoreCase(":gender"))
    		return "\"global_attr_gender_string:(\\\""+payloadparams[2]+"\\\")\",\n"+toReplace;
    	else if(payloadparams[1].equalsIgnoreCase(":size"))
    		return "\"sizes_facet:(\\\""+payloadparams[2]+"\\\")\",\n"+toReplace;
    	else if(payloadparams[1].equalsIgnoreCase(":discount"))
    		return "\"discount_percentage:["+payloadparams[2]+" TO *]\",\n"+toReplace;
    	else if(payloadparams[1].equalsIgnoreCase(":color"))
    			return "\"colour_family_list:(\\\""+payloadparams[2]+"\\\")\",\n"+toReplace;
    	else if(payloadparams[1].equalsIgnoreCase(":price"))
    		return "\"discounted_price:["+payloadparams[2]+" TO NaN]\",\n"+toReplace;
    	else
    		return null;
    }
    
    private String responseValidaion(MultivaluedMap<String, Object> headers){
    	boolean dateValidation = true;
    	boolean conValidation = true;
    	boolean contentLenValidation = true;
    	boolean conetntTypeValidation = true;
    	boolean sizeValidation = true;
    	String toReturn = "";

    	log.info(headers.toString());
    	DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    	Date date = new Date();
    	String todaysDate = dateFormat.format(date);

    	if(!(headers.containsKey("Date") && headers.get("Date").toString().contains(todaysDate))){
    		dateValidation = false;
    		toReturn += "Either date is missing or response dosn't contains todays date\n";
    		log.info("Either date is missing or response dosn't contains todays date");
    	}   		

    	if(!(headers.containsKey("Connection") && headers.get("Connection").toString().equalsIgnoreCase("[close]"))){
    		conValidation = false;
    		toReturn += "Either Connection Header is missing or the value of the connection header is not \"close\"\n";
    		log.info("Either Connection Header is missing or the value of the connection header is not \"close\"");
    	}
    	
    	if(!headers.containsKey("Content-Length")){
    		contentLenValidation = false;
    		toReturn +="Content-Length is missing";
    		log.info("Content-Length is missing");
    	}
    	    	
    	if(!(headers.containsKey("Content-Type") && headers.get("Content-Type").toString().equalsIgnoreCase("[application/json; charset=utf-8]"))){
    		conetntTypeValidation = false;
    		toReturn += "Either Content-Type Header is missing or the value of the Content-Type header is not \"application/json; charset=utf-8\"\n";
    		log.info("Either Content-Type Header is missing or the value of the Content-Type header is not \"application/json; charset=utf-8\"");
    	}
    		
    	if(headers.size()!=4){
    		sizeValidation = false;
    		toReturn += "Response header size is not equal to 4. Header size is :"+headers.size()+"\n";
    		log.info("Response header size is not equal to 4. Header size is :"+headers.size());
    	}
    	
    	if(dateValidation && conValidation && contentLenValidation && conetntTypeValidation && sizeValidation)
    		return "success";
    	else
    		return toReturn;
    }

}
