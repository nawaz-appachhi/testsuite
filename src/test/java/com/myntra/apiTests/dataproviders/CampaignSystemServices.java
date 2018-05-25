package com.myntra.apiTests.dataproviders;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.portalservices.lgpservices.LgpServicesHelper;
import com.myntra.apiTests.portalservices.lgpservices.OnBoardingTests;
import com.myntra.lordoftherings.Initialize;

import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.threeten.bp.Instant;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CampaignSystemServices {
	
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(OnBoardingTests.class);
	static LgpServicesHelper lgpServiceHelper = new LgpServicesHelper();
	static HashMap<String, String> headers = new HashMap<String, String>();
	static APIUtilities utilities = new APIUtilities();
	public class AttributeEntry{
	    	public String namespace;
	    	public String key;
	    	public Object value;
	    	public String action;
	    	public AttributeEntry(String namespace,String key,Object value,String action) {
				this.namespace=namespace;
				this.key=key;
				this.value=value;
				this.action=action;
			}
	    	
	    }
		String existingName = "existingname";
	    String existingAdvertiserRefId="existingRefId"+getCurrentTime();
	    @BeforeClass(alwaysRun = true)
		public void setUp() {
			
			String xid = null;
			if (init.Configurations.GetTestEnvironemnt().toString().toLowerCase().contains("fox7")) {
				headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "application/json");
			}
			else if (init.Configurations.GetTestEnvironemnt().toString().toLowerCase().contains("fox8")) {
				headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "application/json");
			}
			else if (init.Configurations.GetTestEnvironemnt().toString().toLowerCase().contains("production")) {
				headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "application/json");
		     }
		}
 
  @DataProvider
  public Object[][] createAd() throws IOException{
	  
	  
	  
	  ArrayList<String> positiveOutput = new ArrayList<String>();
	  positiveOutput.add("200");
	  positiveOutput.add("Ad created successfully.");
	  positiveOutput.add("SUCCESS");
	  positiveOutput.add("1000");
	  
	  ArrayList<String> negativeOutput = new ArrayList<String>();
	  negativeOutput.add("200");
	  negativeOutput.add("Error occurred while creating Ad.");
	  negativeOutput.add("ERROR");
	  negativeOutput.add("2001");
	  
	  ArrayList<String> invalidTimeOutput = new ArrayList<String>();
	  invalidTimeOutput.add("200");
	  invalidTimeOutput.add("Invalid time.");
	  invalidTimeOutput.add("ERROR");
	  invalidTimeOutput.add("2080");
	  
	  ArrayList<String> invalidBoostOutput = new ArrayList<String>();
	  invalidBoostOutput.add("200");
	  invalidBoostOutput.add("Boost value is not valid");
	  invalidBoostOutput.add("ERROR");
	  invalidBoostOutput.add("2082");
	  
	  ArrayList<String> invalidPositionOutput = new ArrayList<String>();
	  invalidPositionOutput.add("200");
	  invalidPositionOutput.add("Slot info is invalid");
	  invalidPositionOutput.add("ERROR");
	  invalidPositionOutput.add("2083");
	  
	  ArrayList<String> badOutput = new ArrayList<String>();
	  badOutput.add("400");
	  badOutput.add("Bad Request");
  
	  //TestCases for name
	  //case1
	  Map<String, Object> case1 = new HashMap<>();
	  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
	  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
	  System.out.println("hre payload is"+payload);
	  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
		String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
		
		
		case1.put("name","\"name  "+getCurrentTime()+"\"");
		case1.put("description", "\"description\"");
		case1.put("objectId","\""+objectId+"\"");
		case1.put("budget", "10000");
		case1.put("boost","0.2");
		case1.put("persona","\"men\"");
		case1.put("startTime", System.currentTimeMillis()+500000);
		case1.put("endTime",System.currentTimeMillis()+500000);
		case1.put("position","\"1\"");
		case1.put("costPerView","\"4\"");
		case1.put("frequencyCap","\"4\"");
		objectId=null;
	  }
	  //case2
		  Map<String, Object> case2 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			
			case2.put("name","\"\"");
			case2.put("description", "\"description\"");
			case2.put("objectId","\""+objectId+"\"");
			case2.put("budget", "10000");
			case2.put("boost","0.2");
			case2.put("persona","\"men\"");
			case2.put("startTime", System.currentTimeMillis()+500000);
			case2.put("endTime",System.currentTimeMillis()+500000);
			case2.put("position","\"1\"");
			case2.put("costPerView","\"4\"");
			case2.put("frequencyCap","\"4\"");
		
	  }
		  
		//case3
		  Map<String, Object> case3 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			
			case3.put("name","\"   \"");
			case3.put("description", "\"description\"");
			case3.put("objectId","\""+objectId+"\"");
			case3.put("budget", "10000");
			case3.put("boost","0.2");
			case3.put("persona","\"men\"");
			case3.put("startTime", System.currentTimeMillis()+500000);
			case3.put("endTime",System.currentTimeMillis()+500000);
			case3.put("position","\"1\"");
			case3.put("costPerView","\"4\"");
			case3.put("frequencyCap","\"4\"");
		
	  }
		  
		//case4
		  Map<String, Object> case4 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			
			case4.put("name","\"   TrailingSpaces\"");
			case4.put("description", "\"description\"");
			case4.put("objectId","\""+objectId+"\"");
			case4.put("budget", "10000");
			case4.put("boost","0.2");
			case4.put("persona","\"men\"");
			case4.put("startTime", System.currentTimeMillis()+500000);
			case4.put("endTime",System.currentTimeMillis()+500000);
			case4.put("position","\"1\"");
			case4.put("costPerView","\"4\"");
			case4.put("frequencyCap","\"4\"");
		
	  }
		  
		//case5
		  Map<String, Object> case5 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			
			case5.put("name","\"leadingSpaces   \"");
			case5.put("description", "\"description\"");
			case5.put("objectId","\""+objectId+"\"");
			case5.put("budget", "10000");
			case5.put("boost","0.2");
			case5.put("persona","\"men\"");
			case5.put("startTime", System.currentTimeMillis()+500000);
			case5.put("endTime",System.currentTimeMillis()+500000);
			case5.put("position","\"1\"");
			case5.put("costPerView","\"4\"");
			case5.put("frequencyCap","\"4\"");
		
	  }
		//case6
		  Map<String, Object> case6 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			
			case6.put("name","\"name"+getCurrentTime()+"\"");
			case6.put("description", "\"description\"");
			case6.put("objectId","\""+objectId+"\"");
			case6.put("budget", "10000");
			case6.put("boost","0.2");
			case6.put("persona","\"men\"");
			case6.put("startTime", System.currentTimeMillis()+500000);
			case6.put("endTime",System.currentTimeMillis()+500000);
			case6.put("position","\"1\"");
			case6.put("costPerView","\"4\"");
			case6.put("frequencyCap","\"4\"");
		
	  }
		  
		//case14
		  Map<String, Object> case14 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			
			case14.put("name","null");
			case14.put("description", "\"description\"");
			case14.put("objectId","\""+objectId+"\"");
			case14.put("budget", "10000");
			case14.put("boost","0.2");
			case14.put("persona","\"men\"");
			case14.put("startTime", System.currentTimeMillis()+500000);
			case14.put("endTime",System.currentTimeMillis()+500000);
			case14.put("position","\"1\"");
			case14.put("costPerView","\"4\"");
			case14.put("frequencyCap","\"4\"");
		
	  }
		  
		//case15
		  Map<String, Object> case15 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			
			case15.put("name","12341");
			case15.put("description", "\"description\"");
			case15.put("objectId","\""+objectId+"\"");
			case15.put("budget", "10000");
			case15.put("boost","0.2");
			case15.put("persona","\"men\"");
			case15.put("startTime", System.currentTimeMillis()+500000);
			case15.put("endTime",System.currentTimeMillis()+500000);
			case15.put("position","\"1\"");
			case15.put("costPerView","\"4\"");
			case15.put("frequencyCap","\"4\"");
		
	  }
		  
		//case15
		  Map<String, Object> case16 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			
			case16.put("name","@@");
			case16.put("description", "\"description\"");
			case16.put("objectId","\""+objectId+"\"");
			case16.put("budget", "10000");
			case16.put("boost","0.2");
			case16.put("persona","\"men\"");
			case16.put("startTime", System.currentTimeMillis()+500000);
			case16.put("endTime",System.currentTimeMillis()+500000);
			case16.put("position","\"1\"");
			case16.put("costPerView","\"4\"");
			case16.put("frequencyCap","\"4\"");
		
	  }
		  //TestCases for Description
		//case7
		  Map<String, Object> case7 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			
			case7.put("name","\"name"+getCurrentTime()+"\"");
			case7.put("description", "\"\"");
			case7.put("objectId","\""+objectId+"\"");
			case7.put("budget", "10000");
			case7.put("boost","0.2");
			case7.put("persona","\"men\"");
			case7.put("startTime", System.currentTimeMillis()+500000);
			case7.put("endTime",System.currentTimeMillis()+500000);
			case7.put("position","\"1\"");
			case7.put("costPerView","\"4\"");
			case7.put("frequencyCap","\"4\"");
		
	  }
		//case8
		  Map<String, Object> case8 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			
			case8.put("name","\"name"+getCurrentTime()+"\"");
			case8.put("description", "\"description with space\"");
			case8.put("objectId","\""+objectId+"\"");
			case8.put("budget", "10000");
			case8.put("boost","0.2");
			case8.put("persona","\"men\"");
			case8.put("startTime", System.currentTimeMillis()+500000);
			case8.put("endTime",System.currentTimeMillis()+500000);
			case8.put("position","\"1\"");
			case8.put("costPerView","\"4\"");
			case8.put("frequencyCap","\"4\"");
		
	  }
		  
		//case9
		  Map<String, Object> case9 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			
			case9.put("name","\"name"+getCurrentTime()+"\"");
			case9.put("description", "\"   \"");
			case9.put("objectId","\""+objectId+"\"");
			case9.put("budget", "10000");
			case9.put("boost","0.2");
			case9.put("persona","\"men\"");
			case9.put("startTime", System.currentTimeMillis()+500000);
			case9.put("endTime",System.currentTimeMillis()+500000);
			case9.put("position","\"1\"");
			case9.put("costPerView","\"4\"");
			case9.put("frequencyCap","\"4\"");
		
	  }
		//case10
		  Map<String, Object> case10 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			
			case10.put("name","\"name"+getCurrentTime()+"\"");
			case10.put("description", "\"   tralining\"");
			case10.put("objectId","\""+objectId+"\"");
			case10.put("budget", "10000");
			case10.put("boost","0.2");
			case10.put("persona","\"men\"");
			case10.put("startTime", System.currentTimeMillis()+500000);
			case10.put("endTime",System.currentTimeMillis()+500000);
			case10.put("position","\"1\"");
			case10.put("costPerView","\"4\"");
			case10.put("frequencyCap","\"4\"");
		
	  }
		  
		//case11
		  Map<String, Object> case11 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			
			case11.put("name","\"name"+getCurrentTime()+"\"");
			case11.put("description", "\"leading   \"");
			case11.put("objectId","\""+objectId+"\"");
			case11.put("budget", "10000");
			case11.put("boost","0.2");
			case11.put("persona","\"men\"");
			case11.put("startTime", System.currentTimeMillis()+500000);
			case11.put("endTime",System.currentTimeMillis()+500000);
			case11.put("position","\"1\"");
			case11.put("costPerView","\"4\"");
			case11.put("frequencyCap","\"4\"");
		
	  }
		  
		//case12
		  Map<String, Object> case12 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			
			case12.put("name","\"name"+getCurrentTime()+"\"");
			case12.put("description", "\"description with numbers12311\"");
			case12.put("objectId","\""+objectId+"\"");
			case12.put("budget", "10000");
			case12.put("boost","0.2");
			case12.put("persona","\"men\"");
			case12.put("startTime", System.currentTimeMillis()+500000);
			case12.put("endTime",System.currentTimeMillis()+500000);
			case12.put("position","\"1\"");
			case12.put("costPerView","\"4\"");
			case12.put("frequencyCap","\"4\"");
		
	  }
		  
			//case17
			  Map<String, Object> case17 = new HashMap<>();
			  {
			  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
			  payload=utilities.preparepayload(payload,new String[] {"2","object "+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
			  System.out.println("hre payload is"+payload);
			  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
				feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
				System.out.println(feedservice.URL);
				RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
				String response = requestGen.respvalidate.returnresponseasstring();
				System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
				String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
				
				case17.put("name","\"name"+getCurrentTime()+"\"");
				case17.put("description", "null");
				case17.put("objectId","\""+objectId+"\"");
				case17.put("budget", "10000");
				case17.put("boost","0.2");
				case17.put("persona","\"men\"");
				case17.put("startTime", System.currentTimeMillis()+500000);
				case17.put("endTime",System.currentTimeMillis()+500000);
				case17.put("position","\"1\"");
				case17.put("costPerView","\"4\"");
				case17.put("frequencyCap","\"4\"");
			
		  }
			  
			//case18
			  Map<String, Object> case18 = new HashMap<>();
			  {
			  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
			  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
			  System.out.println("hre payload is"+payload);
			  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
				feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
				System.out.println(feedservice.URL);
				RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
				String response = requestGen.respvalidate.returnresponseasstring();
				System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
				String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
				
				case18.put("name","\"name"+getCurrentTime()+"\"");
				case18.put("description", "1213241");
				case18.put("objectId","\""+objectId+"\"");
				case18.put("budget", "10000");
				case18.put("boost","0.2");
				case18.put("persona","\"men\"");
				case18.put("startTime", System.currentTimeMillis()+500000);
				case18.put("endTime",System.currentTimeMillis()+500000);
				case18.put("position","\"1\"");
				case18.put("costPerView","\"4\"");
				case18.put("frequencyCap","\"4\"");
			
		  }
			//case19
			  Map<String, Object> case19 = new HashMap<>();
			  {
			  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
			  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
			  System.out.println("hre payload is"+payload);
			  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
				feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
				System.out.println(feedservice.URL);
				RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
				String response = requestGen.respvalidate.returnresponseasstring();
				System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
				String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
				
				case19.put("name","\"name"+getCurrentTime()+"\"");
				case19.put("description", "@@");
				case19.put("objectId","\""+objectId+"\"");
				case19.put("budget", "10000");
				case19.put("boost","0.2");
				case19.put("persona","\"men\"");
				case19.put("startTime", System.currentTimeMillis()+500000);
				case19.put("endTime",System.currentTimeMillis()+500000);
				case19.put("position","\"1\"");
				case19.put("costPerView","\"4\"");
				case19.put("frequencyCap","\"4\"");
			
		  }
			  
		//TestCases on objectId
		//case13
		  Map<String, Object> case13 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","object "+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case13.put("name","\"name"+getCurrentTime()+"\"");
			case13.put("description", "\"description with numbers12311\"");
			case13.put("objectId","\""+objectId+"\"");
			case13.put("budget", "10000");
			case13.put("boost","0.2");
			case13.put("persona","\"men\"");
			case13.put("startTime", System.currentTimeMillis()+500000);
			case13.put("endTime",System.currentTimeMillis()+500000);
			case13.put("position","\"1\"");
			case13.put("costPerView","\"4\"");
			case13.put("frequencyCap","\"4\"");
		
	  }
		
		//case20
		  Map<String, Object> case20 = new HashMap<>();
		  {
			case20.put("name","\"name"+getCurrentTime()+"\"");
			case20.put("description", "\"description with numbers12311\"");
			case20.put("objectId","null");
			case20.put("budget", "10000");
			case20.put("boost","0.2");
			case20.put("persona","\"men\"");
			case20.put("startTime", System.currentTimeMillis()+500000);
			case20.put("endTime",System.currentTimeMillis()+500000);
			case20.put("position","\"1\"");
			case20.put("costPerView","\"4\"");
			case20.put("frequencyCap","\"4\"");
		
	  }
		//case21
		  Map<String, Object> case21 = new HashMap<>();
		  {
			case21.put("name","\"name"+getCurrentTime()+"\"");
			case21.put("description", "\"description with numbers12311\"");
			case21.put("objectId","\"\"");
			case21.put("budget", "10000");
			case21.put("boost","0.2");
			case21.put("persona","\"men\"");
			case21.put("startTime", System.currentTimeMillis()+500000);
			case21.put("endTime",System.currentTimeMillis()+500000);
			case21.put("position","\"1\"");
			case21.put("costPerView","\"4\"");
			case21.put("frequencyCap","\"4\"");
	  }
		//case22
		  Map<String, Object> case22 = new HashMap<>();
		  {
			case22.put("name","\"name"+getCurrentTime()+"\"");
			case22.put("description", "\"description with numbers12311\"");
			case22.put("objectId","\"   \"");
			case22.put("budget", "10000");
			case22.put("boost","0.2");
			case22.put("persona","\"men\"");
			case22.put("startTime", System.currentTimeMillis()+500000);
			case22.put("endTime",System.currentTimeMillis()+500000);
			case22.put("position","\"1\"");
			case22.put("costPerView","\"4\"");
			case22.put("frequencyCap","\"4\"");
	  }
		  
		//case23
		  Map<String, Object> case23 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","  object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case23.put("name","\"name"+getCurrentTime()+"\"");
			case23.put("description", "\"description with numbers12311\"");
			case23.put("objectId","\"   "+objectId+"\"");
			case23.put("budget", "10000");
			case23.put("boost","0.2");
			case23.put("persona","\"men\"");
			case23.put("startTime", System.currentTimeMillis()+500000);
			case23.put("endTime",System.currentTimeMillis()+500000);
			case23.put("position","\"1\"");
			case23.put("costPerView","\"4\"");
			case23.put("frequencyCap","\"4\"");
		
	  }
		  
		//case24
		  Map<String, Object> case24 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2","  object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case24.put("name","\"name"+getCurrentTime()+"\"");
			case24.put("description", "\"description with numbers12311\"");
			case24.put("objectId","\""+objectId+"    \"");
			case24.put("budget", "10000");
			case24.put("boost","0.2");
			case24.put("persona","\"men\"");
			case24.put("startTime", System.currentTimeMillis()+500000);
			case24.put("endTime",System.currentTimeMillis()+500000);
			case24.put("position","\"1\"");
			case24.put("costPerView","\"4\"");
			case24.put("frequencyCap","\"4\"");
		
	  }
		//case25
		  Map<String, Object> case25 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case25.put("name","\"name"+getCurrentTime()+"\"");
			case25.put("description", "\"description with numbers12311\"");
			case25.put("objectId",objectId);
			case25.put("budget", "10000");
			case25.put("boost","0.2");
			case25.put("persona","\"men\"");
			case25.put("startTime", System.currentTimeMillis()+500000);
			case25.put("endTime",System.currentTimeMillis()+500000);
			case25.put("position","\"1\"");
			case25.put("costPerView","\"4\"");
			case25.put("frequencyCap","\"4\"");
		
	  }
		//case26
		  Map<String, Object> case26 = new HashMap<>();
		  {
			case26.put("name","\"name"+getCurrentTime()+"\"");
			case26.put("description", "\"description with numbers12311\"");
			case26.put("objectId","@@");
			case26.put("budget", "10000");
			case26.put("boost","0.2");
			case26.put("persona","\"men\"");
			case26.put("startTime", System.currentTimeMillis()+500000);
			case26.put("endTime",System.currentTimeMillis()+500000);
			case26.put("position","\"1\"");
			case26.put("costPerView","\"4\"");
			case26.put("frequencyCap","\"4\"");
	  }
		//case27
		  Map<String, Object> case27 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case27.put("name","\"name"+getCurrentTime()+"\"");
			case27.put("description", "\"description with numbers12311\"");
			case27.put("objectId","\""+objectId+"\"");
			case27.put("budget", "10000");
			case27.put("boost","0.2");
			case27.put("persona","\"men\"");
			case27.put("startTime", System.currentTimeMillis()+500000);
			case27.put("endTime",System.currentTimeMillis()+500000);
			case27.put("position","\"1\"");
			case27.put("costPerView","\"4\"");
			case27.put("frequencyCap","\"4\"");
		
	  }
		  
		  //TestCases for budget
		//case28
		  Map<String, Object> case28 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case28.put("name","\"name"+getCurrentTime()+"\"");
			case28.put("description", "\"description with numbers12311\"");
			case28.put("objectId","\""+objectId+"\"");
			case28.put("budget", "0");
			case28.put("boost","0.2");
			case28.put("persona","\"men\"");
			case28.put("startTime", System.currentTimeMillis()+500000);
			case28.put("endTime",System.currentTimeMillis()+500000);
			case28.put("position","\"1\"");
			case28.put("costPerView","\"4\"");
			case28.put("frequencyCap","\"4\"");
		
	  }
		//case29
		  Map<String, Object> case29 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case29.put("name","\"name"+getCurrentTime()+"\"");
			case29.put("description", "\"description with numbers12311\"");
			case29.put("objectId","\""+objectId+"\"");
			case29.put("budget", "\"string\"");
			case29.put("boost","0.2");
			case29.put("persona","\"men\"");
			case29.put("startTime", System.currentTimeMillis()+500000);
			case29.put("endTime",System.currentTimeMillis()+500000);
			case29.put("position","\"1\"");
			case29.put("costPerView","\"4\"");
			case29.put("frequencyCap","\"4\"");
		
	  }
		//case30
		  Map<String, Object> case30 = new HashMap<>();
		  {
			  
			  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
			  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
			  System.out.println("hre payload is"+payload);
			  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
				feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
				System.out.println(feedservice.URL);
				RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
				String response = requestGen.respvalidate.returnresponseasstring();
				System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
				String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
				
			case30.put("name","\"name"+getCurrentTime()+"\"");
			case30.put("description", "\"description with numbers12311\"");
			case30.put("objectId","\""+objectId+"\"");
			case30.put("budget", "null");
			case30.put("boost","0.2");
			case30.put("persona","\"men\"");
			case30.put("startTime", System.currentTimeMillis()+500000);
			case30.put("endTime",System.currentTimeMillis()+500000);
			case30.put("position","\"1\"");
			case30.put("costPerView","\"4\"");
			case30.put("frequencyCap","\"4\"");
	  }
		  
		//case31
		  Map<String, Object> case31 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case31.put("name","\"name"+getCurrentTime()+"\"");
			case31.put("description", "\"description with numbers12311\"");
			case31.put("objectId","\""+objectId+"\"");
			case31.put("budget", "-10000");
			case31.put("boost","0.2");
			case31.put("persona","\"men\"");
			case31.put("startTime", System.currentTimeMillis()+500000);
			case31.put("endTime",System.currentTimeMillis()+500000);
			case31.put("position","\"1\"");
			case31.put("costPerView","\"4\"");
			case31.put("frequencyCap","\"4\"");
		
	  }
		//case32
		  Map<String, Object> case32 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case32.put("name","\"name"+getCurrentTime()+"\"");
			case32.put("description", "\"description with numbers12311\"");
			case32.put("objectId","\""+objectId+"\"");
			case32.put("budget", "100.56");
			case32.put("boost","0.2");
			case32.put("persona","\"men\"");
			case32.put("startTime", System.currentTimeMillis()+500000);
			case32.put("endTime",System.currentTimeMillis()+500000);
			case32.put("position","\"1\"");
			case32.put("costPerView","\"4\"");
			case32.put("frequencyCap","\"4\"");
		
	  }
		//case33
		  Map<String, Object> case33 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case33.put("name","\"name"+getCurrentTime()+"\"");
			case33.put("description", "\"description with numbers12311\"");
			case33.put("objectId","\""+objectId+"\"");
			case33.put("budget", "true");
			case33.put("boost","0.2");
			case33.put("persona","\"men\"");
			case33.put("startTime", System.currentTimeMillis()+500000);
			case33.put("endTime",System.currentTimeMillis()+500000);
			case33.put("position","\"1\"");
			case33.put("costPerView","\"4\"");
			case33.put("frequencyCap","\"4\"");
		
	  }
		  //TestCases on TargetInfoPersona
		//case34
		  Map<String, Object> case34 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case34.put("name","\"name"+getCurrentTime()+"\"");
			case34.put("description", "\"description with numbers12311\"");
			case34.put("objectId","\""+objectId+"\"");
			case34.put("budget", "1000");
			case34.put("boost","0.2");
			case34.put("persona","null");
			case34.put("startTime", System.currentTimeMillis()+500000);
			case34.put("endTime",System.currentTimeMillis()+500000);
			case34.put("position","\"1\"");
			case34.put("costPerView","\"4\"");
			case34.put("frequencyCap","\"4\"");
		
	  }
		//case35
		  Map<String, Object> case35 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case35.put("name","\"name"+getCurrentTime()+"\"");
			case35.put("description", "\"description with numbers12311\"");
			case35.put("objectId","\""+objectId+"\"");
			case35.put("budget", "1000");
			case35.put("boost","0.2");
			case35.put("persona","\"\"");
			case35.put("startTime", System.currentTimeMillis()+500000);
			case35.put("endTime",System.currentTimeMillis()+500000);
			case35.put("position","\"1\"");
			case35.put("costPerView","\"4\"");
			case35.put("frequencyCap","\"4\"");
		
	  }
		//case36
		  Map<String, Object> case36 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case36.put("name","\"name"+getCurrentTime()+"\"");
			case36.put("description", "\"description with numbers12311\"");
			case36.put("objectId","\""+objectId+"\"");
			case36.put("budget", "1000");
			case36.put("boost","0.2");
			case36.put("persona","\"   \"");
			case36.put("startTime", System.currentTimeMillis()+500000);
			case36.put("endTime",System.currentTimeMillis()+500000);
			case36.put("position","\"1\"");
			case36.put("costPerView","\"4\"");
			case36.put("frequencyCap","\"4\"");
		
	  }
		//case37
		  Map<String, Object> case37 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case37.put("name","\"name"+getCurrentTime()+"\"");
			case37.put("description", "\"description with numbers12311\"");
			case37.put("objectId","\""+objectId+"\"");
			case37.put("budget", "1000");
			case37.put("boost","0.2");
			case37.put("persona","\"   men\"");
			case37.put("startTime", System.currentTimeMillis()+500000);
			case37.put("endTime",System.currentTimeMillis()+500000);
			case37.put("position","\"1\"");
			case37.put("costPerView","\"4\"");
			case37.put("frequencyCap","\"4\"");
		
	  }
		//case38
		  Map<String, Object> case38 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case38.put("name","\"name"+getCurrentTime()+"\"");
			case38.put("description", "\"description with numbers12311\"");
			case38.put("objectId","\""+objectId+"\"");
			case38.put("budget", "1000");
			case38.put("boost","0.2");
			case38.put("persona","\"men    \"");
			case38.put("startTime", System.currentTimeMillis()+500000);
			case38.put("endTime",System.currentTimeMillis()+500000);
			case38.put("position","\"1\"");
			case38.put("costPerView","\"4\"");
			case38.put("frequencyCap","\"4\"");
		
	  }
		//case39
		  Map<String, Object> case39 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case39.put("name","\"name"+getCurrentTime()+"\"");
			case39.put("description", "\"description with numbers12311\"");
			case39.put("objectId","\""+objectId+"\"");
			case39.put("budget", "1000");
			case39.put("boost","0.2");
			case39.put("persona","123422");
			case39.put("startTime", System.currentTimeMillis()+500000);
			case39.put("endTime",System.currentTimeMillis()+500000);
			case39.put("position","\"1\"");
			case39.put("costPerView","\"4\"");
			case39.put("frequencyCap","\"4\"");
		
	  }
		//case40
		  Map<String, Object> case40 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case40.put("name","\"name"+getCurrentTime()+"\"");
			case40.put("description", "\"description with numbers12311\"");
			case40.put("objectId","\""+objectId+"\"");
			case40.put("budget", "1000");
			case40.put("boost","0.2");
			case40.put("persona","@@");
			case40.put("startTime", System.currentTimeMillis()+500000);
			case40.put("endTime",System.currentTimeMillis()+500000);
			case40.put("position","\"1\"");
			case40.put("costPerView","\"4\"");
			case40.put("frequencyCap","\"4\"");
		
	  }
		//case41
		  Map<String, Object> case41 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case41.put("name","\"name"+getCurrentTime()+"\"");
			case41.put("description", "\"description with numbers12311\"");
			case41.put("objectId","\""+objectId+"\"");
			case41.put("budget", "1000");
			case41.put("boost","0.2");
			case41.put("persona","\"men122\"");
			case41.put("startTime", System.currentTimeMillis()+500000);
			case41.put("endTime",System.currentTimeMillis()+500000);
			case41.put("position","\"1\"");
			case41.put("costPerView","\"4\"");
			case41.put("frequencyCap","\"4\"");
		
	  }
		//case42
		  Map<String, Object> case42 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case42.put("name","\"name"+getCurrentTime()+"\"");
			case42.put("description", "\"description with numbers12311\"");
			case42.put("objectId","\""+objectId+"\"");
			case42.put("budget", "1000");
			case42.put("boost","0.2");
			case42.put("persona","\"men women\"");
			case42.put("startTime", System.currentTimeMillis()+500000);
			case42.put("endTime",System.currentTimeMillis()+500000);
			case42.put("position","\"1\"");
			case42.put("costPerView","\"4\"");
			case42.put("frequencyCap","\"4\"");
		
	  }
		//case43
		  Map<String, Object> case43 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case43.put("name","\"name"+getCurrentTime()+"\"");
			case43.put("description", "\"description with numbers12311\"");
			case43.put("objectId","\""+objectId+"\"");
			case43.put("budget", "1000");
			case43.put("boost","0.2");
			case43.put("persona","\"persona\"");
			case43.put("startTime", System.currentTimeMillis()+500000);
			case43.put("endTime",System.currentTimeMillis()+500000);
			case43.put("position","\"1\"");
			case43.put("costPerView","\"4\"");
			case43.put("frequencyCap","\"4\"");
		
	  }
		//case44
		  Map<String, Object> case44 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case44.put("name","\"name"+getCurrentTime()+"\"");
			case44.put("description", "\"description with numbers12311\"");
			case44.put("objectId","\""+objectId+"\"");
			case44.put("budget", "1000");
			case44.put("boost","0.2");
			case44.put("persona","\"men\"");
			case44.put("startTime", System.currentTimeMillis()+500000);
			case44.put("endTime",System.currentTimeMillis()+500000);
			case44.put("position","\"1\"");
			case44.put("costPerView","\"4\"");
			case44.put("frequencyCap","\"4\"");
		
	  }
		//case45
		  Map<String, Object> case45 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case45.put("name","\"name"+getCurrentTime()+"\"");
			case45.put("description", "\"description with numbers12311\"");
			case45.put("objectId","\""+objectId+"\"");
			case45.put("budget", "1000");
			case45.put("boost","0.2");
			case45.put("persona","\"unisex\"");
			case45.put("startTime", System.currentTimeMillis()+500000);
			case45.put("endTime",System.currentTimeMillis()+500000);
			case45.put("position","\"1\"");
			case45.put("costPerView","\"4\"");
			case45.put("frequencyCap","\"4\"");
		
	  }
		//case46
		  Map<String, Object> case46 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case46.put("name","\"name"+getCurrentTime()+"\"");
			case46.put("description", "\"description with numbers12311\"");
			case46.put("objectId","\""+objectId+"\"");
			case46.put("budget", "1000");
			case46.put("boost","0.2");
			case46.put("persona","\"women\"");
			case46.put("startTime", System.currentTimeMillis()+500000);
			case46.put("endTime",System.currentTimeMillis()+500000);
			case46.put("position","\"1\"");
			case46.put("costPerView","\"4\"");
			case46.put("frequencyCap","\"4\"");
		
	  }
		  //TestCases on start time and end time
		//case47
		  Map<String, Object> case47 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			
			case47.put("name","\"name"+getCurrentTime()+"\"");
			case47.put("description", "\"description with numbers12311\"");
			case47.put("objectId","\""+objectId+"\"");
			case47.put("budget", "1000");
			case47.put("boost","0.2");
			case47.put("persona","\"women\"");
			case47.put("startTime", System.currentTimeMillis()+1000000);
			case47.put("endTime",System.currentTimeMillis()+500000);
			case47.put("position","\"1\"");
			case47.put("costPerView","\"4\"");
			case47.put("frequencyCap","\"4\"");
		
	  }
		//case48
		  Map<String, Object> case48 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			long time = System.currentTimeMillis()+500000;
			case48.put("name","\"name"+getCurrentTime()+"\"");
			case48.put("description", "\"description with numbers12311\"");
			case48.put("objectId","\""+objectId+"\"");
			case48.put("budget", "1000");
			case48.put("boost","0.2");
			case48.put("persona","\"women\"");
			case48.put("startTime", time);
			case48.put("endTime",time);
			case48.put("position","\"1\"");
			case48.put("costPerView","\"4\"");
			case48.put("frequencyCap","\"4\"");
		
	  }
		//case49
		  Map<String, Object> case49 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case49.put("name","\"name"+getCurrentTime()+"\"");
			case49.put("description", "\"description with numbers12311\"");
			case49.put("objectId","\""+objectId+"\"");
			case49.put("budget", "1000");
			case49.put("boost","0.2");
			case49.put("persona","\"women\"");
			case49.put("startTime", System.currentTimeMillis()+500000);
			case49.put("endTime",System.currentTimeMillis()-500000);
			case49.put("position","\"1\"");
			case49.put("costPerView","\"4\"");
			case49.put("frequencyCap","\"4\"");
		
	  }
		//case50
		  Map<String, Object> case50 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case50.put("name","\"name"+getCurrentTime()+"\"");
			case50.put("description", "\"description with numbers12311\"");
			case50.put("objectId","\""+objectId+"\"");
			case50.put("budget", "1000");
			case50.put("boost","0.2");
			case50.put("persona","\"women\"");
			case50.put("startTime", System.currentTimeMillis()-500000);
			case50.put("endTime",System.currentTimeMillis()+500000);
			case50.put("position","\"1\"");
			case50.put("costPerView","\"4\"");
			case50.put("frequencyCap","\"4\"");
		
	  }
		//case51
		  Map<String, Object> case51 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case51.put("name","\"name"+getCurrentTime()+"\"");
			case51.put("description", "\"description with numbers12311\"");
			case51.put("objectId","\""+objectId+"\"");
			case51.put("budget", "1000");
			case51.put("boost","0.2");
			case51.put("persona","\"women\"");
			case51.put("startTime", System.currentTimeMillis()-500000);
			case51.put("endTime",System.currentTimeMillis()-500000);
			case51.put("position","\"1\"");
			case51.put("costPerView","\"4\"");
			case51.put("frequencyCap","\"4\"");
		
	  }
		//case52
		  Map<String, Object> case52 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case52.put("name","\"name"+getCurrentTime()+"\"");
			case52.put("description", "\"description with numbers12311\"");
			case52.put("objectId","\""+objectId+"\"");
			case52.put("budget", "1000");
			case52.put("boost","0.2");
			case52.put("persona","\"women\"");
			case52.put("startTime", System.currentTimeMillis()+500000);
			case52.put("endTime",System.currentTimeMillis()+500000);
			case52.put("position","\"1\"");
			case52.put("costPerView","\"4\"");
			case52.put("frequencyCap","\"4\"");
		
	  }
		//case53
		  Map<String, Object> case53 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case53.put("name","\"name"+getCurrentTime()+"\"");
			case53.put("description", "\"description with numbers12311\"");
			case53.put("objectId","\""+objectId+"\"");
			case53.put("budget", "1000");
			case53.put("boost","0.2");
			case53.put("persona","\"women\"");
			case53.put("startTime", 0);
			case53.put("endTime",System.currentTimeMillis()+500000);
			case53.put("position","\"1\"");
			case53.put("costPerView","\"4\"");
			case53.put("frequencyCap","\"4\"");
		
	  }
		//case54
		  Map<String, Object> case54 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case54.put("name","\"name"+getCurrentTime()+"\"");
			case54.put("description", "\"description with numbers12311\"");
			case54.put("objectId","\""+objectId+"\"");
			case54.put("budget", "1000");
			case54.put("boost","0.2");
			case54.put("persona","\"women\"");
			case54.put("startTime",System.currentTimeMillis()+500000 );
			case54.put("endTime",0);
			case54.put("position","\"1\"");
			case54.put("costPerView","\"4\"");
			case54.put("frequencyCap","\"4\"");
		
	  }
		//case55
		  Map<String, Object> case55 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case55.put("name","\"name"+getCurrentTime()+"\"");
			case55.put("description", "\"description with numbers12311\"");
			case55.put("objectId","\""+objectId+"\"");
			case55.put("budget", "1000");
			case55.put("boost","0.2");
			case55.put("persona","\"women\"");
			case55.put("startTime",0 );
			case55.put("endTime",0);
			case55.put("position","\"1\"");
			case55.put("costPerView","\"4\"");
			case55.put("frequencyCap","\"4\"");
		
	  }
		//case56
		  Map<String, Object> case56 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case56.put("name","\"name"+getCurrentTime()+"\"");
			case56.put("description", "\"description with numbers12311\"");
			case56.put("objectId","\""+objectId+"\"");
			case56.put("budget", "1000");
			case56.put("boost","0.2");
			case56.put("persona","\"women\"");
			case56.put("startTime","null");
			case56.put("endTime",System.currentTimeMillis()+500000 );
			case56.put("position","\"1\"");
			case56.put("costPerView","\"4\"");
			case56.put("frequencyCap","\"4\"");
		
	  }
		//case57
		  Map<String, Object> case57 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case57.put("name","\"name"+getCurrentTime()+"\"");
			case57.put("description", "\"description with numbers12311\"");
			case57.put("objectId","\""+objectId+"\"");
			case57.put("budget", "1000");
			case57.put("boost","0.2");
			case57.put("persona","\"women\"");
			case57.put("startTime",System.currentTimeMillis()+500000);
			case57.put("endTime", "null");
			case57.put("position","\"1\"");
			case57.put("costPerView","\"4\"");
			case57.put("frequencyCap","\"4\"");
		
	  }
		//case58
		  Map<String, Object> case58 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case58.put("name","\"name"+getCurrentTime()+"\"");
			case58.put("description", "\"description with numbers12311\"");
			case58.put("objectId","\""+objectId+"\"");
			case58.put("budget", "1000");
			case58.put("boost","0.2");
			case58.put("persona","\"women\"");
			case58.put("startTime",System.currentTimeMillis()+500000);
			case58.put("endTime", "string");
			case58.put("position","\"1\"");
			case58.put("costPerView","\"4\"");
			case58.put("frequencyCap","\"4\"");
		
	  }
		//case59
		  Map<String, Object> case59 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case59.put("name","\"name"+getCurrentTime()+"\"");
			case59.put("description", "\"description with numbers12311\"");
			case59.put("objectId","\""+objectId+"\"");
			case59.put("budget", "1000");
			case59.put("boost","0.2");
			case59.put("persona","\"women\"");
			case59.put("startTime","String");
			case59.put("endTime", System.currentTimeMillis()+500000);
			case59.put("position","\"1\"");
			case59.put("costPerView","\"4\"");
			case59.put("frequencyCap","\"4\"");
		
	  }//case60
		  Map<String, Object> case60 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case60.put("name","\"name"+getCurrentTime()+"\"");
			case60.put("description", "\"description with numbers12311\"");
			case60.put("objectId","\""+objectId+"\"");
			case60.put("budget", "1000");
			case60.put("boost","0.2");
			case60.put("persona","\"women\"");
			case60.put("startTime",System.currentTimeMillis()+500000);
			case60.put("endTime", "true");
			case60.put("position","\"1\"");
			case60.put("costPerView","\"4\"");
			case60.put("frequencyCap","\"4\"");
		
	  }
		//case61
		  Map<String, Object> case61 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case61.put("name","\"name"+getCurrentTime()+"\"");
			case61.put("description", "\"description with numbers12311\"");
			case61.put("objectId","\""+objectId+"\"");
			case61.put("budget", "1000");
			case61.put("boost","0.2");
			case61.put("persona","\"women\"");
			case61.put("startTime","true");
			case61.put("endTime", System.currentTimeMillis()+500000);
			case61.put("position","\"1\"");
			case61.put("costPerView","\"4\"");
			case61.put("frequencyCap","\"4\"");
		
	  }
		  //TestCases for boost
		//case62
		  Map<String, Object> case62 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case62.put("name","\"name"+getCurrentTime()+"\"");
			case62.put("description", "\"description with numbers12311\"");
			case62.put("objectId","\""+objectId+"\"");
			case62.put("budget", "1000");
			case62.put("boost","0");
			case62.put("persona","\"women\"");
			case62.put("startTime",System.currentTimeMillis()+200);
			case62.put("endTime", System.currentTimeMillis()+500000);
			case62.put("position","\"1\"");
			case62.put("costPerView","\"4\"");
			case62.put("frequencyCap","\"4\"");
		
	  }
		//case63
		  Map<String, Object> case63 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case63.put("name","\"name"+getCurrentTime()+"\"");
			case63.put("description", "\"description with numbers12311\"");
			case63.put("objectId","\""+objectId+"\"");
			case63.put("budget", "1000");
			case63.put("boost","string");
			case63.put("persona","\"women\"");
			case63.put("startTime",System.currentTimeMillis()+200);
			case63.put("endTime", System.currentTimeMillis()+500000);
			case63.put("position","\"1\"");
			case63.put("costPerView","\"4\"");
			case63.put("frequencyCap","\"4\"");
		
	  }
		//case64
		  Map<String, Object> case64 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case64.put("name","\"name"+getCurrentTime()+"\"");
			case64.put("description", "\"description with numbers12311\"");
			case64.put("objectId","\""+objectId+"\"");
			case64.put("budget", "1000");
			case64.put("boost","null");
			case64.put("persona","\"women\"");
			case64.put("startTime",System.currentTimeMillis()+200);
			case64.put("endTime", System.currentTimeMillis()+500000);
			case64.put("position","\"1\"");
			case64.put("costPerView","\"4\"");
			case64.put("frequencyCap","\"4\"");
		
	  }
		//case65
		  Map<String, Object> case65 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case65.put("name","\"name"+getCurrentTime()+"\"");
			case65.put("description", "\"description with numbers12311\"");
			case65.put("objectId","\""+objectId+"\"");
			case65.put("budget", "1000");
			case65.put("boost","-23.10");
			case65.put("persona","\"women\"");
			case65.put("startTime",System.currentTimeMillis()+200);
			case65.put("endTime", System.currentTimeMillis()+500000);
			case65.put("position","\"1\"");
			case65.put("costPerView","\"4\"");
			case65.put("frequencyCap","\"4\"");
		
	  }
		//case66
		  Map<String, Object> case66 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case66.put("name","\"name"+getCurrentTime()+"\"");
			case66.put("description", "\"description with numbers12311\"");
			case66.put("objectId","\""+objectId+"\"");
			case66.put("budget", "1000");
			case66.put("boost","23.10");
			case66.put("persona","\"women\"");
			case66.put("startTime",System.currentTimeMillis()+200);
			case66.put("endTime", System.currentTimeMillis()+500000);
			case66.put("position","\"1\"");
			case66.put("costPerView","\"4\"");
			case66.put("frequencyCap","\"4\"");
		
	  }
		//case67
		  Map<String, Object> case67 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case67.put("name","\"name"+getCurrentTime()+"\"");
			case67.put("description", "\"description with numbers12311\"");
			case67.put("objectId","\""+objectId+"\"");
			case67.put("budget", "1000");
			case67.put("boost","true");
			case67.put("persona","\"women\"");
			case67.put("startTime",System.currentTimeMillis()+200);
			case67.put("endTime", System.currentTimeMillis()+500000);
			case67.put("position","\"1\"");
			case67.put("costPerView","\"4\"");
			case67.put("frequencyCap","\"4\"");
		
	  }
		//case68
		  Map<String, Object> case68 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case68.put("name","\"name"+getCurrentTime()+"\"");
			case68.put("description", "\"description with numbers12311\"");
			case68.put("objectId","\""+objectId+"\"");
			case68.put("budget", "1000");
			case68.put("boost","@@");
			case68.put("persona","\"women\"");
			case68.put("startTime",System.currentTimeMillis()+200);
			case68.put("endTime", System.currentTimeMillis()+500000);
			case68.put("position","\"1\"");
			case68.put("costPerView","\"4\"");
			case68.put("frequencyCap","\"4\"");
		
	  }
		  //TestCases for slotInfo position
		//case69
		  Map<String, Object> case69 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case69.put("name","\"name"+getCurrentTime()+"\"");
			case69.put("description", "\"description with numbers12311\"");
			case69.put("objectId","\""+objectId+"\"");
			case69.put("budget", "1000");
			case69.put("boost","0.2");
			case69.put("persona","\"women\"");
			case69.put("startTime",System.currentTimeMillis()+500000);
			case69.put("endTime", System.currentTimeMillis()+500000);
			case69.put("position","\"0\"");
			case69.put("costPerView","\"4\"");
			case69.put("frequencyCap","\"4\"");
		
	  }
		//case70
		  Map<String, Object> case70 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case70.put("name","\"name"+getCurrentTime()+"\"");
			case70.put("description", "\"description with numbers12311\"");
			case70.put("objectId","\""+objectId+"\"");
			case70.put("budget", "1000");
			case70.put("boost","0.2");
			case70.put("persona","\"women\"");
			case70.put("startTime",System.currentTimeMillis()+200);
			case70.put("endTime", System.currentTimeMillis()+500000);
			case70.put("position","null");
			case70.put("costPerView","\"4\"");
			case70.put("frequencyCap","\"4\"");
		
	  }
		//case71
		  Map<String, Object> case71 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case71.put("name","\"name"+getCurrentTime()+"\"");
			case71.put("description", "\"description with numbers12311\"");
			case71.put("objectId","\""+objectId+"\"");
			case71.put("budget", "1000");
			case71.put("boost","0.2");
			case71.put("persona","\"women\"");
			case71.put("startTime",System.currentTimeMillis()+200);
			case71.put("endTime", System.currentTimeMillis()+500000);
			case71.put("position","\"string\"");
			case71.put("costPerView","\"4\"");
			case71.put("frequencyCap","\"4\"");
		
	  }
		//case72
		  Map<String, Object> case72 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case72.put("name","\"name"+getCurrentTime()+"\"");
			case72.put("description", "\"description with numbers12311\"");
			case72.put("objectId","\""+objectId+"\"");
			case72.put("budget", "1000");
			case72.put("boost","0.2");
			case72.put("persona","\"women\"");
			case72.put("startTime",System.currentTimeMillis()+200);
			case72.put("endTime", System.currentTimeMillis()+500000);
			case72.put("position","\"-1\"");
			case72.put("costPerView","\"4\"");
			case72.put("frequencyCap","\"4\"");
		
	  }
		//case73
		  Map<String, Object> case73 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case73.put("name","\"name"+getCurrentTime()+"\"");
			case73.put("description", "\"description with numbers12311\"");
			case73.put("objectId","\""+objectId+"\"");
			case73.put("budget", "1000");
			case73.put("boost","0.2");
			case73.put("persona","\"women\"");
			case73.put("startTime",System.currentTimeMillis()+200);
			case73.put("endTime", System.currentTimeMillis()+500000);
			case73.put("position","\"1.54\"");
			case73.put("costPerView","\"4\"");
			case73.put("frequencyCap","\"4\"");
		
	  }
		//case74
		  Map<String, Object> case74 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case74.put("name","\"name"+getCurrentTime()+"\"");
			case74.put("description", "\"description with numbers12311\"");
			case74.put("objectId","\""+objectId+"\"");
			case74.put("budget", "1000");
			case74.put("boost","0.2");
			case74.put("persona","\"women\"");
			case74.put("startTime",System.currentTimeMillis()+200);
			case74.put("endTime", System.currentTimeMillis()+500000);
			case74.put("position","@@");
			case74.put("costPerView","\"4\"");
			case74.put("frequencyCap","\"4\"");
		
	  }
		//case75
		  Map<String, Object> case75 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case75.put("name","\"name"+getCurrentTime()+"\"");
			case75.put("description", "\"description with numbers12311\"");
			case75.put("objectId","\""+objectId+"\"");
			case75.put("budget", "1000");
			case75.put("boost","0.2");
			case75.put("persona","\"women\"");
			case75.put("startTime",System.currentTimeMillis()+200);
			case75.put("endTime", System.currentTimeMillis()+500000);
			case75.put("position","true");
			case75.put("costPerView","\"4\"");
			case75.put("frequencyCap","\"4\"");
		
	  }
		//case76
		  Map<String, Object> case76 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case76.put("name","\"name"+getCurrentTime()+"\"");
			case76.put("description", "\"description with numbers12311\"");
			case76.put("objectId","\""+objectId+"\"");
			case76.put("budget", "1000");
			case76.put("boost","0.2");
			case76.put("persona","\"women\"");
			case76.put("startTime",System.currentTimeMillis()+200);
			case76.put("endTime", System.currentTimeMillis()+500000);
			case76.put("position","\"12323243442342434\"");
			case76.put("costPerView","\"4\"");
			case76.put("frequencyCap","\"4\"");
		
	  }
		  //Testcases for slotinfo Costperview
		//case77
		  Map<String, Object> case77 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case77.put("name","\"name"+getCurrentTime()+"\"");
			case77.put("description", "\"description with numbers12311\"");
			case77.put("objectId","\""+objectId+"\"");
			case77.put("budget", "1000");
			case77.put("boost","0.2");
			case77.put("persona","\"women\"");
			case77.put("startTime",System.currentTimeMillis()+200);
			case77.put("endTime", System.currentTimeMillis()+500000);
			case77.put("position","\"1\"");
			case77.put("costPerView","\"0\"");
			case77.put("frequencyCap","\"4\"");
		
	  }
		//case78
		  Map<String, Object> case78 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case78.put("name","\"name"+getCurrentTime()+"\"");
			case78.put("description", "\"description with numbers12311\"");
			case78.put("objectId","\""+objectId+"\"");
			case78.put("budget", "1000");
			case78.put("boost","0.2");
			case78.put("persona","\"women\"");
			case78.put("startTime",System.currentTimeMillis()+200);
			case78.put("endTime", System.currentTimeMillis()+500000);
			case78.put("position","\"1\"");
			case78.put("costPerView","null");
			case78.put("frequencyCap","\"4\"");
		
	  }
		//case79
		  Map<String, Object> case79 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case79.put("name","\"name"+getCurrentTime()+"\"");
			case79.put("description", "\"description with numbers12311\"");
			case79.put("objectId","\""+objectId+"\"");
			case79.put("budget", "1000");
			case79.put("boost","0.2");
			case79.put("persona","\"women\"");
			case79.put("startTime",System.currentTimeMillis()+200);
			case79.put("endTime", System.currentTimeMillis()+500000);
			case79.put("position","\"1\"");
			case79.put("costPerView","string");
			case79.put("frequencyCap","\"4\"");
		
	  }
		//case80
		  Map<String, Object> case80 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case80.put("name","\"name"+getCurrentTime()+"\"");
			case80.put("description", "\"description with numbers12311\"");
			case80.put("objectId","\""+objectId+"\"");
			case80.put("budget", "1000");
			case80.put("boost","0.2");
			case80.put("persona","\"women\"");
			case80.put("startTime",System.currentTimeMillis()+200);
			case80.put("endTime", System.currentTimeMillis()+500000);
			case80.put("position","\"1\"");
			case80.put("costPerView","\"-2\"");
			case80.put("frequencyCap","\"4\"");
		
	  }
		//case81
		  Map<String, Object> case81 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case81.put("name","\"name"+getCurrentTime()+"\"");
			case81.put("description", "\"description with numbers12311\"");
			case81.put("objectId","\""+objectId+"\"");
			case81.put("budget", "1000");
			case81.put("boost","0.2");
			case81.put("persona","\"women\"");
			case81.put("startTime",System.currentTimeMillis()+200);
			case81.put("endTime", System.currentTimeMillis()+500000);
			case81.put("position","\"1\"");
			case81.put("costPerView","\"2.45\"");
			case81.put("frequencyCap","\"4\"");
		
	  }
		//case82
		  Map<String, Object> case82 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case82.put("name","\"name"+getCurrentTime()+"\"");
			case82.put("description", "\"description with numbers12311\"");
			case82.put("objectId","\""+objectId+"\"");
			case82.put("budget", "1000");
			case82.put("boost","0.2");
			case82.put("persona","\"women\"");
			case82.put("startTime",System.currentTimeMillis()+200);
			case82.put("endTime", System.currentTimeMillis()+500000);
			case82.put("position","\"1\"");
			case82.put("costPerView","true");
			case82.put("frequencyCap","\"4\"");
		
	  }
		//case83
		  Map<String, Object> case83 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case83.put("name","\"name"+getCurrentTime()+"\"");
			case83.put("description", "\"description with numbers12311\"");
			case83.put("objectId","\""+objectId+"\"");
			case83.put("budget", "1000");
			case83.put("boost","0.2");
			case83.put("persona","\"women\"");
			case83.put("startTime",System.currentTimeMillis()+200);
			case83.put("endTime", System.currentTimeMillis()+500000);
			case83.put("position","\"1\"");
			case83.put("costPerView","@@");
			case83.put("frequencyCap","\"4\"");
		
	  }
		//TestCases for frequencyCap
		//case84
		  String obj = ""+System.currentTimeMillis();
		  Map<String, Object> case84 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",obj,"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case84.put("name","\"name"+getCurrentTime()+"\"");
			case84.put("description", "\"description with numbers12311\"");
			case84.put("objectId","\"2:"+obj+"\"");
			case84.put("budget", "1000");
			case84.put("boost","0.2");
			case84.put("persona","\"women\"");
			case84.put("startTime",System.currentTimeMillis()+200);
			case84.put("endTime", System.currentTimeMillis()+500000);
			case84.put("position","\"1\"");
			case84.put("costPerView","\"3.5\"");
			case84.put("frequencyCap","\"0\"");
		
	  }
		//case85
		  Map<String, Object> case85 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case85.put("name","\"name"+getCurrentTime()+"\"");
			case85.put("description", "\"description with numbers12311\"");
			case85.put("objectId","\""+objectId+"\"");
			case85.put("budget", "1000");
			case85.put("boost","0.2");
			case85.put("persona","\"women\"");
			case85.put("startTime",System.currentTimeMillis()+200);
			case85.put("endTime", System.currentTimeMillis()+500000);
			case85.put("position","\"1\"");
			case85.put("costPerView","\"3.5\"");
			case85.put("frequencyCap","null");
		
	  }
		//case86
		  Map<String, Object> case86 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case86.put("name","\"name"+getCurrentTime()+"\"");
			case86.put("description", "\"description with numbers12311\"");
			case86.put("objectId","\""+objectId+"\"");
			case86.put("budget", "1000");
			case86.put("boost","0.2");
			case86.put("persona","\"women\"");
			case86.put("startTime",System.currentTimeMillis()+200);
			case86.put("endTime", System.currentTimeMillis()+500000);
			case86.put("position","\"1\"");
			case86.put("costPerView","\"3.5\"");
			case86.put("frequencyCap","\"string\"");
		
	  }
		//case87
		  Map<String, Object> case87 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case87.put("name","\"name"+getCurrentTime()+"\"");
			case87.put("description", "\"description with numbers12311\"");
			case87.put("objectId","\""+objectId+"\"");
			case87.put("budget", "1000");
			case87.put("boost","0.2");
			case87.put("persona","\"women\"");
			case87.put("startTime",System.currentTimeMillis()+200);
			case87.put("endTime", System.currentTimeMillis()+500000);
			case87.put("position","\"1\"");
			case87.put("costPerView","\"3.5\"");
			case87.put("frequencyCap","\"-34\"");
		
	  }
		//case88
		  Map<String, Object> case88 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case88.put("name","\"name"+getCurrentTime()+"\"");
			case88.put("description", "\"description with numbers12311\"");
			case88.put("objectId","\""+objectId+"\"");
			case88.put("budget", "1000");
			case88.put("boost","0.2");
			case88.put("persona","\"women\"");
			case88.put("startTime",System.currentTimeMillis()+200);
			case88.put("endTime", System.currentTimeMillis()+500000);
			case88.put("position","\"1\"");
			case88.put("costPerView","\"3.5\"");
			case88.put("frequencyCap","true");
		
	  }
		//case89
		  Map<String, Object> case89 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case89.put("name","\"name"+getCurrentTime()+"\"");
			case89.put("description", "\"description with numbers12311\"");
			case89.put("objectId","\""+objectId+"\"");
			case89.put("budget", "1000");
			case89.put("boost","0.2");
			case89.put("persona","\"women\"");
			case89.put("startTime",System.currentTimeMillis()+200);
			case89.put("endTime", System.currentTimeMillis()+500000);
			case89.put("position","\"1\"");
			case89.put("costPerView","\"3.5\"");
			case89.put("frequencyCap","@@");
		
	  }
		//case90
		  
		  String name1 = ""+getCurrentTime();
		  Map<String, Object> case90 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case90.put("name","\""+name1+"\"");
			case90.put("description", "\"description with numbers12311\"");
			case90.put("objectId","\""+objectId+"\"");
			case90.put("budget", "1000");
			case90.put("boost","0.2");
			case90.put("persona","\"women\"");
			case90.put("startTime",System.currentTimeMillis()+200);
			case90.put("endTime", System.currentTimeMillis()+500000);
			case90.put("position","\"1\"");
			case90.put("costPerView","\"3.5\"");
			case90.put("frequencyCap","\"2849388583749472939232\"");
		
	  }
		  //Testcases to check uniqueness
		//case91
		  Map<String, Object> case91 = new HashMap<>();
		  {
		  
			case91.put("name","\"name"+getCurrentTime()+"\"");
			case91.put("description", "\"description with numbers12311\"");
			case91.put("objectId","\"2:"+obj+"\"");
			case91.put("budget", "1000");
			case91.put("boost","0.2");
			case91.put("persona","\"women\"");
			case91.put("startTime",System.currentTimeMillis()+200);
			case91.put("endTime", System.currentTimeMillis()+500000);
			case91.put("position","\"1\"");
			case91.put("costPerView","\"3.5\"");
			case91.put("frequencyCap","\"28\"");
		
	  }
		//case92
		  Map<String, Object> case92 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case92.put("name","\""+name1+"\"");
			case92.put("description", "\"description with numbers12311\"");
			case92.put("objectId","\""+objectId+"\"");
			case92.put("budget", "1000");
			case92.put("boost","0.2");
			case92.put("persona","\"women\"");
			case92.put("startTime",System.currentTimeMillis()+200);
			case92.put("endTime", System.currentTimeMillis()+500000);
			case92.put("position","\"1\"");
			case92.put("costPerView","\"3.5\"");
			case92.put("frequencyCap","\"28\"");
		
	  }
		//case91
		  Map<String, Object> case93 = new HashMap<>();
		  {
		  
			case93.put("name","\""+name1+"\"");
			case93.put("description", "\"description with numbers12311\"");
			case93.put("objectId","\"2:"+obj+"\"");
			case93.put("budget", "1000");
			case93.put("boost","0.2");
			case93.put("persona","\"women\"");
			case93.put("startTime",System.currentTimeMillis()+200);
			case93.put("endTime", System.currentTimeMillis()+500000);
			case93.put("position","\"1\"");
			case93.put("costPerView","\"3.5\"");
			case93.put("frequencyCap","\"28\"");
		
	  }
		//case94
		  Map<String, Object> case94 = new HashMap<>();
		  {
		  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
		  System.out.println("hre payload is"+payload);
		  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
			feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
			System.out.println(feedservice.URL);
			RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println("hre "+JsonPath.read(response, "$.data[0].refId").toString());
			String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
			case94.put("name","\"name"+System.currentTimeMillis()+"\"");
			case94.put("description", "\"description with numbers12311\"");
			case94.put("objectId","\""+objectId+"\"");
			case94.put("budget", "1000");
			case94.put("boost","0.2");
			case94.put("persona","\"women\"");
			case94.put("startTime",System.currentTimeMillis()+200);
			case94.put("endTime", System.currentTimeMillis()+500000);
			case94.put("position","\"1\"");
			case94.put("costPerView","\"3.5\"");
			case94.put("frequencyCap","\"28\"");
		
	  }
		  
	  return (new Object[][]{{case1,positiveOutput},{case2,negativeOutput},{case3,negativeOutput},{case4,positiveOutput},{case5,positiveOutput},{case6,positiveOutput}
	  						,{case7,negativeOutput},{case8,positiveOutput},{case9,negativeOutput},{case10,positiveOutput},{case11,positiveOutput},{case12,positiveOutput}
	  						/*,{case13,positiveOutput}*/,{case14,negativeOutput},{case15,positiveOutput},{case16,badOutput},{case17,negativeOutput},{case18,positiveOutput}
	  						,{case19,badOutput},{case20,negativeOutput},{case21,negativeOutput},{case22,negativeOutput},{case23,positiveOutput},{case24,positiveOutput}
	  						,{case25,badOutput},{case26,badOutput},{case27,positiveOutput},{case28,positiveOutput},{case29,badOutput},{case30,negativeOutput}
	  						,{case31,negativeOutput},{case32,positiveOutput},{case33,badOutput},{case34,positiveOutput},{case35,positiveOutput},{case36,positiveOutput}
	  						,{case37,positiveOutput},{case38,positiveOutput},{case39,positiveOutput},{case40,badOutput},{case41,positiveOutput},{case42,positiveOutput}
	  						,{case43,positiveOutput},{case44,positiveOutput},{case45,positiveOutput},{case46,positiveOutput},{case47,invalidTimeOutput},{case48,positiveOutput}
	  						,{case49,invalidTimeOutput},{case50,invalidTimeOutput},{case51,invalidTimeOutput},{case52,positiveOutput},{case53,invalidTimeOutput},{case54,invalidTimeOutput}
	  						,{case55,invalidTimeOutput},{case56,invalidTimeOutput},{case57,invalidTimeOutput},{case58,badOutput},{case59,badOutput},{case60,badOutput},{case61,badOutput}
	  						,{case62,positiveOutput},{case63,badOutput},{case64,invalidBoostOutput},{case65,invalidBoostOutput},{case66,positiveOutput},{case67,badOutput},{case68,badOutput}
	  						,{case69,invalidPositionOutput},{case70,invalidPositionOutput},{case71,badOutput},{case72,invalidPositionOutput},{case73,badOutput},{case74,badOutput}
	  						,{case75,badOutput},{case76,badOutput},{case77,invalidPositionOutput},{case78,invalidPositionOutput},{case79,badOutput},{case80,invalidPositionOutput}
	  						,{case81,positiveOutput},{case82,badOutput},{case83,badOutput},{case84,positiveOutput},{case85,negativeOutput},{case86,badOutput},{case87,negativeOutput}
	  						,{case88,badOutput},{case89,badOutput},{case90,badOutput},{case91,negativeOutput},{case92,positiveOutput},{case93,negativeOutput},{case94,positiveOutput}
	  						});
	  
  }
  	
  @DataProvider
  public Object[][] getAd() throws IOException{
	  
	  ArrayList<String> positiveOutput = new ArrayList<String>();
	  positiveOutput.add("200");
	  positiveOutput.add("Ad retrieved successfully.");
	  positiveOutput.add("SUCCESS");
	  positiveOutput.add("1003");
	  
	  ArrayList<String> negativeOutput = new ArrayList<String>();
	  negativeOutput.add("200");
	  negativeOutput.add("Ad not found");
	  negativeOutput.add("ERROR");
	  negativeOutput.add("2004");
	  
	  ArrayList<String> badOutput = new ArrayList<String>();
	  badOutput.add("400");
	  badOutput.add("Bad Request");
	  
	  
	  //case1
	  ArrayList<String> case1 = new ArrayList<>();
	  {
	  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
	  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
	  System.out.println("hre payload is"+payload);
	  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
		String getAttributePayloadFilePath = "./Data/payloads/JSON/lgp/campaignSystem/lgpCreateAd";
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNCREATEAD,init.Configurations,new String[]{"\"name\"","\"description\"","\""+objectId+"\"","1000","0.2","\"men\"",Long.toString(System.currentTimeMillis()+200),Long.toString(System.currentTimeMillis()+1000),"\"1\"","\"3.5\"","\"4\""},getAttributePayloadFilePath, PayloadType.JSON,PayloadType.JSON);
		getDeviceAttributeService.URL=utilities.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{});
		RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
      case1.add(objectId);
	  }
      //case2
      ArrayList<String> case2 = new ArrayList<>();
      case2.add("null");
      
    //case3
      ArrayList<String> case3 = new ArrayList<>();
      case3.add(Long.toString(System.currentTimeMillis()+200));
      
    //case4
      ArrayList<String> case4 = new ArrayList<>();
      case4.add("@@@");
      
    //case5
      ArrayList<String> case5 = new ArrayList<>();
      {
	  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
	  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
	  System.out.println("hre payload is"+payload);
	  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
		String getAttributePayloadFilePath = "./Data/payloads/JSON/lgp/campaignSystem/lgpCreateAd";
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNCREATEAD,init.Configurations,new String[]{"\"name\"","\"description\"","\""+objectId+"\"","1000","0.2","\"men\"",Long.toString(System.currentTimeMillis()+200),Long.toString(System.currentTimeMillis()+1000),"\"1\"","\"3.5\"","\"4\""},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
		getDeviceAttributeService.URL=utilities.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{});
		RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
      case5.add(objectId);
	  }
	  
    //case6
    //case5
      ArrayList<String> case6 = new ArrayList<>();
      {
	  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
	  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
	  System.out.println("hre payload is"+payload);
	  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
		String getAttributePayloadFilePath = "./Data/payloads/JSON/lgp/campaignSystem/lgpCreateAd";
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNCREATEAD,init.Configurations,new String[]{"\"name\"","\"description\"","\""+objectId+"\"","1000","0.2","\"men\"",Long.toString(System.currentTimeMillis()+200),Long.toString(System.currentTimeMillis()+1000),"\"1\"","\"3.5\"","\"4\""},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
		getDeviceAttributeService.URL=utilities.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{});
		RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
      case6.add(objectId);
	  }
	  return (new Object[][]{{case1,positiveOutput},{case2,negativeOutput},{case3,negativeOutput},{case4,negativeOutput},{case5,positiveOutput},{case6,positiveOutput}});
  }
  
  @DataProvider
  public Object[][] getAdById() throws IOException{
	  
	  ArrayList<String> positiveOutput = new ArrayList<String>();
	  positiveOutput.add("200");
	  positiveOutput.add("Ad retrieved successfully.");
	  positiveOutput.add("SUCCESS");
	  positiveOutput.add("1003");
	  
	  ArrayList<String> negativeOutput = new ArrayList<String>();
	  negativeOutput.add("200");
	  negativeOutput.add("Ad not found");
	  negativeOutput.add("ERROR");
	  negativeOutput.add("2004");
	  
	  ArrayList<String> badOutput = new ArrayList<String>();
	  badOutput.add("400");
	  badOutput.add("Bad Request");
	  
	  
	  //case1
	  ArrayList<String> case1 = new ArrayList<>();
	  {
	  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
	  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
	  System.out.println("hre payload is"+payload);
	  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
		String getAttributePayloadFilePath = "./Data/payloads/JSON/lgp/campaignSystem/lgpCreateAd";
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNCREATEAD,init.Configurations,new String[]{"\"name\"","\"description\"","\""+objectId+"\"","1000","0.2","\"men\"",Long.toString(System.currentTimeMillis()+200),Long.toString(System.currentTimeMillis()+1000),"\"1\"","\"3.5\"","\"4\""},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
		getDeviceAttributeService.URL=utilities.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{});
		RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
		String response2 = getAttributeRequest.respvalidate.returnresponseasstring();
		String Id = JsonPath.read(response2, "$.data[0].id").toString();
		System.out.println("id is"+ Id);
      case1.add(Id);
	  }
      //case2
      ArrayList<String> case2 = new ArrayList<>();
      case2.add("null");
      
    //case3
      ArrayList<String> case3 = new ArrayList<>();
      case3.add(Long.toString(System.currentTimeMillis()+200));
      
    //case4
      ArrayList<String> case4 = new ArrayList<>();
      case4.add("@@@");
      
    //case5
      ArrayList<String> case5 = new ArrayList<>();
      {
	  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
	  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
	  System.out.println("hre payload is"+payload);
	  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
		String getAttributePayloadFilePath = "./Data/payloads/JSON/lgp/campaignSystem/lgpCreateAd";
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNCREATEAD,init.Configurations,new String[]{"\"name\"","\"description\"","\""+objectId+"\"","1000","0.2","\"men\"",Long.toString(System.currentTimeMillis()+200),Long.toString(System.currentTimeMillis()+1000),"\"1\"","\"3.5\"","\"4\""},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
		getDeviceAttributeService.URL=utilities.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{});
		RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
		String response2 = getAttributeRequest.respvalidate.returnresponseasstring();
		String Id = JsonPath.read(response2, "$.data[0].id").toString();
      case5.add(Id);
	  }
	  
    //case6
      ArrayList<String> case6 = new ArrayList<>();
      {
	  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
	  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
	  System.out.println("hre payload is"+payload);
	  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
		String getAttributePayloadFilePath = "./Data/payloads/JSON/lgp/campaignSystem/lgpCreateAd";
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNCREATEAD,init.Configurations,new String[]{"\"name\"","\"description\"","\""+objectId+"\"","1000","0.2","\"men\"",Long.toString(System.currentTimeMillis()+200),Long.toString(System.currentTimeMillis()+1000),"\"1\"","\"3.5\"","\"4\""},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
		getDeviceAttributeService.URL=utilities.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{});
		RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
		String response2 = getAttributeRequest.respvalidate.returnresponseasstring();
		String Id = JsonPath.read(response2, "$.data[0].id").toString();
      case6.add(Id);
	  }
	  return (new Object[][]{{case1,positiveOutput},{case2,badOutput},{case3,negativeOutput},{case4,badOutput},{case5,positiveOutput},{case6,positiveOutput}});
  }
  
  @DataProvider
  public Object[][] deleteAdById() throws IOException{
	  
	  ArrayList<String> positiveOutput = new ArrayList<String>();
	  positiveOutput.add("200");
	  positiveOutput.add("Ad deleted successfully.");
	  positiveOutput.add("SUCCESS");
	  positiveOutput.add("1002");
	  positiveOutput.add("200");
	  positiveOutput.add("Ad not found");
	  positiveOutput.add("ERROR");
	  positiveOutput.add("2004");
	  
	  ArrayList<String> negativeOutput = new ArrayList<String>();
	  negativeOutput.add("200");
	  negativeOutput.add("Ad not found");
	  negativeOutput.add("ERROR");
	  negativeOutput.add("2004");
	  negativeOutput.add("200");
	  negativeOutput.add("Ad not found");
	  negativeOutput.add("ERROR");
	  negativeOutput.add("2004");
	  
	  ArrayList<String> badOutput = new ArrayList<String>();
	  badOutput.add("400");
	  badOutput.add("Bad Request");
	  
	  //case1
	  ArrayList<String> case1 = new ArrayList<>();
	  {
	  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
	  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
	  System.out.println("hre payload is"+payload);
	  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
		String getAttributePayloadFilePath = "./Data/payloads/JSON/lgp/campaignSystem/lgpCreateAd";
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNCREATEAD,init.Configurations,new String[]{"\"name\"","\"description\"","\""+objectId+"\"","1000","0.2","\"men\"",Long.toString(System.currentTimeMillis()+200),Long.toString(System.currentTimeMillis()+1000),"\"1\"","\"3.5\"","\"4\""},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
		getDeviceAttributeService.URL=utilities.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{});
		RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
		String response2 = getAttributeRequest.respvalidate.returnresponseasstring();
		String Id = JsonPath.read(response2, "$.data[0].id").toString();
		System.out.println("id is"+ Id);
      case1.add(Id);
	  }
	//case2
      ArrayList<String> case2 = new ArrayList<>();
      case2.add("null");
      
    //case3
      ArrayList<String> case3 = new ArrayList<>();
      case3.add(Long.toString(System.currentTimeMillis()+200));
      
    //case4
      ArrayList<String> case4 = new ArrayList<>();
      case4.add("@@@");
      
    //case5
	  ArrayList<String> case5 = new ArrayList<>();
	  {
	  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
	  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
	  System.out.println("hre payload is"+payload);
	  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
		String getAttributePayloadFilePath = "./Data/payloads/JSON/lgp/campaignSystem/lgpCreateAd";
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNCREATEAD,init.Configurations,new String[]{"\"name\"","\"description\"","\""+objectId+"\"","1000","0.2","\"men\"",Long.toString(System.currentTimeMillis()+200),Long.toString(System.currentTimeMillis()+1000),"\"1\"","\"3.5\"","\"4\""},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
		getDeviceAttributeService.URL=utilities.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{});
		RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
		String response2 = getAttributeRequest.respvalidate.returnresponseasstring();
		String Id = JsonPath.read(response2, "$.data[0].id").toString();
		System.out.println("id is"+ Id);
      case5.add(Id);
	  }
	  
	//case6
	  ArrayList<String> case6 = new ArrayList<>();
	  {
	  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
	  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
	  System.out.println("hre payload is"+payload);
	  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
		String getAttributePayloadFilePath = "./Data/payloads/JSON/lgp/campaignSystem/lgpCreateAd";
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNCREATEAD,init.Configurations,new String[]{"\"name\"","\"description\"","\""+objectId+"\"","1000","0.2","\"men\"",Long.toString(System.currentTimeMillis()+200),Long.toString(System.currentTimeMillis()+1000),"\"1\"","\"3.5\"","\"4\""},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
		getDeviceAttributeService.URL=utilities.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{});
		RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
		String response2 = getAttributeRequest.respvalidate.returnresponseasstring();
		String Id = JsonPath.read(response2, "$.data[0].id").toString();
		System.out.println("id is"+ Id);
		MyntraService getDeviceAttributeService2 = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNDELETEADBYID,init.Configurations,"");
		  getDeviceAttributeService2.URL=utilities.prepareparameterizedURL(getDeviceAttributeService2.URL, new String[]{Id});
		  RequestGenerator getAttributeRequest2= new RequestGenerator(getDeviceAttributeService2);
      case6.add(Id);
	  }
      
	  return (new Object[][]{{case1,positiveOutput},{case2,badOutput},{case3,negativeOutput},{case4,badOutput},{case5,positiveOutput},{case6,negativeOutput}});
  }
  
  @DataProvider
  public Object[][] deleteAdByObjectId() throws IOException{
	  
	  ArrayList<String> positiveOutput = new ArrayList<String>();
	  positiveOutput.add("200");
	  positiveOutput.add("Ad deleted successfully.");
	  positiveOutput.add("SUCCESS");
	  positiveOutput.add("1002");
	  positiveOutput.add("200");
	  positiveOutput.add("Ad not found");
	  positiveOutput.add("ERROR");
	  positiveOutput.add("2004");
	  
	  ArrayList<String> negativeOutput = new ArrayList<String>();
	  negativeOutput.add("200");
	  negativeOutput.add("Ad not found");
	  negativeOutput.add("ERROR");
	  negativeOutput.add("2004");
	  negativeOutput.add("200");
	  negativeOutput.add("Ad not found");
	  negativeOutput.add("ERROR");
	  negativeOutput.add("2004");
	  
	  ArrayList<String> badOutput = new ArrayList<String>();
	  badOutput.add("400");
	  badOutput.add("Bad Request");
	  
	  //case1
	  ArrayList<String> case1 = new ArrayList<>();
	  {
	  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
	  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
	  System.out.println("hre payload is"+payload);
	  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
		String getAttributePayloadFilePath = "./Data/payloads/JSON/lgp/campaignSystem/lgpCreateAd";
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNCREATEAD,init.Configurations,new String[]{"\"name\"","\"description\"","\""+objectId+"\"","1000","0.2","\"men\"",Long.toString(System.currentTimeMillis()+200),Long.toString(System.currentTimeMillis()+1000),"\"1\"","\"3.5\"","\"4\""},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
		getDeviceAttributeService.URL=utilities.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{});
		RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
		String response2 = getAttributeRequest.respvalidate.returnresponseasstring();
      case1.add(objectId);
	  }
	//case2
      ArrayList<String> case2 = new ArrayList<>();
      case2.add("null");
      
    //case3
      ArrayList<String> case3 = new ArrayList<>();
      case3.add(Long.toString(System.currentTimeMillis()+200));
      
    //case4
      ArrayList<String> case4 = new ArrayList<>();
      case4.add("@@@");
      
    //case5
	  ArrayList<String> case5 = new ArrayList<>();
	  {
	  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
	  payload=utilities.preparepayload(payload,new String[] {"2","object"+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
	  System.out.println("hre payload is"+payload);
	  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
		String getAttributePayloadFilePath = "./Data/payloads/JSON/lgp/campaignSystem/lgpCreateAd";
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNCREATEAD,init.Configurations,new String[]{"\"name\"","\"description\"","\""+objectId+"\"","1000","0.2","\"men\"",Long.toString(System.currentTimeMillis()+200),Long.toString(System.currentTimeMillis()+1000),"\"1\"","\"3.5\"","\"4\""},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
		getDeviceAttributeService.URL=utilities.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{});
		RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
		String response2 = getAttributeRequest.respvalidate.returnresponseasstring();
      case5.add(objectId);
	  }
	  
	//case6
	  ArrayList<String> case6 = new ArrayList<>();
	  {
	  String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
	  payload=utilities.preparepayload(payload,new String[] {"2",""+System.currentTimeMillis(),"order-dispatch","Order Dispatched"});
	  System.out.println("hre payload is"+payload);
	  MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		String objectId = "2:"+JsonPath.read(response, "$.data[0].refId").toString();
		String getAttributePayloadFilePath = "./Data/payloads/JSON/lgp/campaignSystem/lgpCreateAd";
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNCREATEAD,init.Configurations,new String[]{"\"name\"","\"description\"","\""+objectId+"\"","1000","0.2","\"men\"",Long.toString(System.currentTimeMillis()+200),Long.toString(System.currentTimeMillis()+1000),"\"1\"","\"3.5\"","\"4\""},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
		getDeviceAttributeService.URL=utilities.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{});
		RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
		String response2 = getAttributeRequest.respvalidate.returnresponseasstring();
		MyntraService getDeviceAttributeService2 = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNDELETEADBYOBJECTID,init.Configurations,"");
		  getDeviceAttributeService2.URL=utilities.prepareparameterizedURL(getDeviceAttributeService2.URL, new String[]{objectId});
		  RequestGenerator getAttributeRequest2= new RequestGenerator(getDeviceAttributeService2);
      case6.add(objectId);
	  }
      
	  return (new Object[][]{{case1,positiveOutput},{case2,negativeOutput},{case3,negativeOutput},{case4,negativeOutput},{case5,positiveOutput},{case6,negativeOutput}});
  }
  	public String getCurrentTime(){
  		
  		String time;
        SecureRandom random = new SecureRandom();
  		time = Long.toString(Instant.now().toEpochMilli());
  		time="id"+time+new BigInteger(130, random).toString(32);
  		return time;
  	}

}
