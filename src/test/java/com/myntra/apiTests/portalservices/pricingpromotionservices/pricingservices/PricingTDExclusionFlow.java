package com.myntra.apiTests.portalservices.pricingpromotionservices.pricingservices;
import java.util.ArrayList;
import java.util.List;

import com.myntra.apiTests.dataproviders.PricingDP;
import com.myntra.apiTests.portalservices.PricingPolicyApiHelper.PricingPolicyApiServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Myntra;


public class PricingTDExclusionFlow extends PricingDP {
	
	static PricingPolicyApiServiceHelper PPHelper = new PricingPolicyApiServiceHelper();
	private static List<String> returnedIds=new ArrayList<String>();
	
	@SuppressWarnings("null")
	public static String [] getReturnedIds(List<String>excludeIds) {
		String [] test = new String[excludeIds.size()];;
		for(int i=0;i<=excludeIds.size()-1;i++){
		test[i]=excludeIds.get(i).trim();
		}
		return test;	
	}
	
	public static void callingMethod(String[]styleIds) {
		for(int i=0;i<styleIds.length-1;i++){
		System.out.println("Inside Populated Method : "+ returnedIds);
		}
		//return returnedIds;	
	}

	Initialize init = new Initialize("/Data/configuration");
	
	private static Logger logger = LoggerFactory.getLogger(PricingTDExclusionFlow.class);
	public long currentTime = System.currentTimeMillis();
	String startTime = String.valueOf(currentTime);
	String endTime = String.valueOf(currentTime+4000000);
	PricingPolicyApiServiceHelper pps=new PricingPolicyApiServiceHelper();


	public void printAndLog(String Message, String[] Array)
    {
        System.out.println(Message);
       // log.info(Message);
        for(int i=0;i<Array.length;i++)
        {
            System.out.println(Array[i]);
          //  log.info(Array[i]);
            
        }
    }
        
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression" } )
	public void createStyleTdExclusionFlow1( ) {
		RequestGenerator request = PPHelper.invokeGetStyleDataService("10", "10", "OUTRIGHT"); //Enter Total Number of Items as Parameter
        String response = request.respvalidate.returnresponseasstring();            
        System.out.println(response);
        
        String StyleIdArray[] = PPHelper.getStyleIdArray(response, 10);
        String MrpArray[]=PPHelper.getValueArrayFromResponse(response, 10, "maximumRetailPrice");
        printAndLog("Extracted Style IDS from Response : ",StyleIdArray);
        pps.getExcludedIds(StyleIdArray, MrpArray, 5);
        
	}
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression" })
	public void isBaseline() {
	MyntraService service = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICY,APINAME.GETDAYCONFIG, init.Configurations,PayloadType.JSON, new String[] { },PayloadType.JSON);
	RequestGenerator requestGenerator = new RequestGenerator(service);
	
	String response = requestGenerator.respvalidate.returnresponseasstring();            
    System.out.println(response);
    String res=JsonPath.read(response, "$.dayType");
    System.out.println("Sale day Type : --> "+ res);
    if(res.equals("SALEDAY")){
    	MyntraService BaseLineDayConfig = Myntra.getService(
    			ServiceType.PORTAL_PRICINGPOLICY,
    			APINAME.SETBASELINE, init.Configurations,
    			new String [] {},PayloadType.JSON,PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(BaseLineDayConfig);
		
    	String respBaseline = req.respvalidate.returnresponseasstring();            
        System.out.println("Set Baseline response : "+respBaseline);
    }
	
}
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression" })
	public void createPPfg() {
		//MyntraService service = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICY,APINAME.GETDAYCONFIG, init.Configurations,PayloadType.JSON, new String[] { },PayloadType.JSON);
		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICY,APINAME.CREATEINTERNALPOLICYFG, init.Configurations, new String[] { },PayloadType.JSON,PayloadType.JSON);
	RequestGenerator requestGenerator = new RequestGenerator(service1);
	System.out.println("Base URL : "+service1.URL);
	System.out.println("Payload : "+service1.Payload);
	String response1 = requestGenerator.respvalidate.returnresponseasstring(); 
	System.out.println("Create Internal policy fg ----->> "+response1);
	}
	
}




