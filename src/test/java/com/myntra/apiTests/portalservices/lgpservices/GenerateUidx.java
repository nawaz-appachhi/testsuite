package com.myntra.apiTests.portalservices.lgpservices;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import com.myntra.apiTests.portalservices.lgpServe.feed.FeedObjectHelper;
import com.myntra.lordoftherings.Initialize;

import com.myntra.lordoftherings.gandalf.PayloadType;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.Myntra;

public class GenerateUidx {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(OnBoardingTests.class);
	static APIUtilities apiUtil = new APIUtilities();
	static APIUtilities utilities = new APIUtilities();
	static FeedObjectHelper feedHelper= new FeedObjectHelper();
	//static LgpServicesHelper lgpServiceHelper=new LgpServicesHelper();
	static DevApiServiceHelper devApiServiceHelper = new DevApiServiceHelper();
	static HashMap<String,String> headers;
	HashMap<Integer,String> map= new HashMap<>(); 
	GenerateUidx(){
		map.put(1, "segment1");
		map.put(2, "segment2");
		map.put(3, "segment3");
		map.put(4, "segment4");
		map.put(5, "segment5");
		map.put(6, "segment6");
	}
	public static void main(String args[]) throws IOException, ParseException{
		
		headers = new HashMap<String,String>();
		headers.put(HttpHeaders.ACCEPT, "application/json");
		headers.put(HttpHeaders.CONTENT_TYPE, "application/json");
        
		GenerateUidx gUidx = new GenerateUidx();
        PrintWriter out = new PrintWriter("./Data/payloads/JSON/lgp/UidxContainer");
        
		for(int i=0;i<500;i++){
			
			String userName = "abc"+System.currentTimeMillis()+"@myntra.com";
			String password = "abcxyz";
			
			MyntraService devApiSignUpService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS, APINAME.DEVAPISIGNUP, init.Configurations, new String[] { userName, password });
			//RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(userName, password);
			
			RequestGenerator devApiSignUpReqGen= new RequestGenerator(devApiSignUpService,headers);
			
			String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();
			String uidx = JsonPath.read(devApiSignUpResponse,"$.data.uidx").toString();
			
			String getAttributePayloadFilePath = "./Data/payloads/JSON/Hallmark/getAttributeByUidxPayload";
			String namespacesForGetAttributeCall = gUidx.getNamespacesAsPayloadString(uidx);
			MyntraService getUserAttributeService = Myntra.getService(ServiceType.LGP_HALLMARKSERVICE,APINAME.UPDATEUSERATTRIBUTE,init.Configurations,new String[]{namespacesForGetAttributeCall},getAttributePayloadFilePath, PayloadType.JSON,PayloadType.JSON);
			getUserAttributeService.URL=apiUtil.prepareparameterizedURL(getUserAttributeService.URL, new String[]{uidx});
			RequestGenerator getAttributeRequest= new RequestGenerator(getUserAttributeService,headers);
			String attributesBeforeUpdateCall = getAttributeRequest.returnresponseasstring();
			
			try {
		          out.println(JsonPath.read(devApiSignUpResponse,"$.data.uidx").toString());
		         
		        } catch (Exception e) {
		            System.out.println("Error!"+e);
		        }
		}
		System.out.println("End!!!");
		}
	public String getNamespacesAsPayloadString(String uidx) throws ParseException{
		
		Random r = new Random();
		
		JSONObject obj1 = new JSONObject();
		obj1.put("uidx", uidx);
		
		JSONObject obj2=new JSONObject();
		obj2.put("namespace", "morpheus");
		obj2.put("key", "segments");
		
		JSONArray jarray = new JSONArray();
		String val1= map.get(r.nextInt(6-1)+1);
		String val2= map.get(r.nextInt(6-1)+1);
		String val3= map.get(r.nextInt(6-1)+1);
		
		jarray.add(val1);
		jarray.add(val2);
		jarray.add(val3);
		obj2.put("value",jarray);
		obj2.put("action", "ADD");
		String s = "["+obj2+"]";
		JSONParser parser = new JSONParser();
		JSONArray json2 = (JSONArray) parser.parse(s);
		obj1.put("attributeEntryList", json2);
		s="["+obj1+"]";
		JSONArray json3 = (JSONArray) parser.parse(s);
		System.out.println(json3.toString());
		return s;
	}

}
