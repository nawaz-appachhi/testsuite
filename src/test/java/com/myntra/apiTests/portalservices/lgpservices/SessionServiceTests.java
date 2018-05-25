package com.myntra.apiTests.portalservices.lgpservices;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.LgpSessionServiceDP;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;



public class SessionServiceTests extends LgpSessionServiceDP {
	
	
	public static Initialize init = new Initialize("/Data/configuration");
	String env = init.Configurations.GetTestEnvironemnt().toString();
	static Logger log = Logger.getLogger(SessionServiceTests.class);
	static APIUtilities apiUtil = new APIUtilities();
	
	private String globalCaseValue;
	
	HashMap<String, String> headersForXML;
	HashMap<String, String> headersForJSON;
	
	public static String sessionValue = null;
	
	String responseFromGetXml = null;
	String responseFromGetJson = null;
	
	@BeforeClass(alwaysRun=true)
	public void init(){
		
		headersForXML = new HashMap<String,String>();
		headersForXML.put(HttpHeaders.ACCEPT, "application/xml");
		headersForXML.put(HttpHeaders.CONTENT_TYPE, "application/xml");
		
		headersForJSON = new HashMap<String,String>();
		headersForJSON.put(HttpHeaders.ACCEPT, "application/json");
		
	}
	
	@AfterMethod 
	protected void endOfTestMethod(Method method) throws Exception {
		
		String methodName = method.getName();
		System.out.println("End of method -------- "+methodName +" For --> "+ globalCaseValue);
		System.out.println("=====================================================================================================");
	}
	
	
	@Test(groups = {"Sanity"}, dataProvider = "createSessionXMLDP", priority = 1)
	public void createSessionXMLTests(String payloadReferenceValue, String caseValue) throws IOException, ParserConfigurationException, SAXException{
		globalCaseValue = caseValue;
		String uriRequest = null;
		if(caseValue.equalsIgnoreCase("case1") ||caseValue.equalsIgnoreCase("case8") || caseValue.equalsIgnoreCase("case9") || 
				caseValue.equalsIgnoreCase("case10") || caseValue.equalsIgnoreCase("case11") || caseValue.equalsIgnoreCase("case12")){
			
			String completeFilePath = "./Data/payloads/XML/goSessionService/createSessionXMLValid";
			String payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{payloadReferenceValue});
			
			System.out.println("======== Payload ======= for : "+caseValue);
			System.out.println(payload);
			System.out.println("========================");
			
			if(env.equalsIgnoreCase("fox7")){
				
				 uriRequest = "http://d7session.myntra.com/SessionStoreService/rest/sessions/";
				
			}else if(env.equalsIgnoreCase("production")){
				
				uriRequest = "http://session.myntra.com/SessionStoreService/rest/sessions/";
				
			}
			
            Response response = executeRequest(uriRequest, payload,"put");
           
            		
            System.out.println(response.toString());
            System.out.println(response.getEntity().toString());
            
            String outPutXid = response.readEntity(String.class);
            
            getSessionXMLTests(outPutXid, "case1");
            String dataTagValueFromXmlString = xmlParseForData(responseFromGetXml,"data");
            String dataTagValueFromPayload = xmlParseForData(payload,"data");
            
            
            Assert.assertEquals(response.getStatus(), 200,"Status response mismatch for : "+globalCaseValue);
            
            if(caseValue.equalsIgnoreCase("case1"))
            Assert.assertEquals(dataTagValueFromXmlString.trim(), dataTagValueFromPayload.trim(),"Data Tag Value Mis-Match For : "+globalCaseValue);
            
			/*MyntraService sessionService = Myntra.getService(ServiceType.LGP_SESSIONSERVICE,APINAME.LGPCREATESESSIONXML,init.Configurations,new String[]{payloadReferenceValue},completeFilePath,PayloadType.XML,PayloadType.XML);
			RequestGenerator request= new RequestGenerator(sessionService,headersForXML);
			System.out.println(sessionService.URL);
		    System.out.println(request.returnresponseasstring());
			
		    */
		}
		
		 
		else if(caseValue.equalsIgnoreCase("case2")){
			
			String completeFilePath = "./Data/payloads/XML/goSessionService/createSessionXMLInvalid";
			
			String payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{payloadReferenceValue});
			System.out.println("======== Payload ======= for : "+caseValue);
			System.out.println(payload);
			System.out.println("========================");
			if(env.equalsIgnoreCase("fox7")){
				
				 uriRequest = "http://d7session.myntra.com/SessionStoreService/rest/sessions/";
				
			}else if(env.equalsIgnoreCase("production")){
				
				uriRequest = "http://session.myntra.com/SessionStoreService/rest/sessions/";
				
			}
            Response response = executeRequest(uriRequest, payload,"put");
            		
            System.out.println(response.toString());
            System.out.println(response.getEntity().toString());
            System.out.println(response.readEntity(String.class));
            
            Assert.assertEquals(response.getStatus(), 400,"Status response mismatch for :"+globalCaseValue);
            	
		}
		
		else if(caseValue.equalsIgnoreCase("case3")){
			
			String[] payloadParams = payloadReferenceValue.split("==");
			String completeFilePath = "./Data/payloads/XML/goSessionService/createSessionXMLTimestamp";
			
			String payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{payloadParams[0],payloadParams[1]});
			System.out.println("======== Payload ======= for : "+caseValue);
			System.out.println(payload);
			System.out.println("========================"); 
			
			uriRequest = null;
			if(env.equalsIgnoreCase("fox7")){
				
				 uriRequest = "http://d7session.myntra.com/SessionStoreService/rest/sessions/";
				
			}else if(env.equalsIgnoreCase("production")){
				
				uriRequest = "http://session.myntra.com/SessionStoreService/rest/sessions/";
				
			}
            Response response = executeRequest(uriRequest, payload,"put");
            		
            System.out.println(response.toString());
            System.out.println(response.getEntity().toString());
            System.out.println(response.readEntity(String.class));
            
            Assert.assertEquals(response.getStatus(), 200,"Status response mismatch for :"+globalCaseValue);
            
			
		}
		
		else if(caseValue.equalsIgnoreCase("case4")){
			
			String[] payloadParams = payloadReferenceValue.split("==");
			String completeFilePath = "./Data/payloads/XML/goSessionService/createSessionXMLTimestamp";
			
			String payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{payloadParams[0],payloadParams[1]});
			System.out.println("======== Payload ======= for : "+caseValue);
			System.out.println(payload);
			System.out.println("========================");
			if(env.equalsIgnoreCase("fox7")){
				
				 uriRequest = "http://d7session.myntra.com/SessionStoreService/rest/sessions/";
				
			}else if(env.equalsIgnoreCase("production")){
				
				uriRequest = "http://session.myntra.com/SessionStoreService/rest/sessions/";
				
			}
            Response response = executeRequest(uriRequest, payload,"put");
            		
            System.out.println(response.toString());
            System.out.println(response.getEntity().toString());
            System.out.println(response.readEntity(String.class));
            
            Assert.assertEquals(response.getStatus(), 200,"Status response mismatch for :"+globalCaseValue);
            
			
		}
		
		else if(caseValue.equalsIgnoreCase("case5")){
			
			String completeFilePath = "./Data/payloads/XML/goSessionService/emptyPayload";
			
			String payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{payloadReferenceValue});
			System.out.println("======== Payload ======= for : "+caseValue);
			System.out.println(payload);
			System.out.println("========================");
			if(env.equalsIgnoreCase("fox7")){
				
				 uriRequest = "http://d7session.myntra.com/SessionStoreService/rest/sessions/";
				
			}else if(env.equalsIgnoreCase("production")){
				
				uriRequest = "http://session.myntra.com/SessionStoreService/rest/sessions/";
				
			}
            Response response = executeRequest(uriRequest, payload,"put");
            		
            System.out.println(response.toString());
            System.out.println(response.getEntity().toString());
            System.out.println(response.readEntity(String.class));
            
            Assert.assertEquals(response.getStatus(), 400,"Status response mismatch for :"+globalCaseValue);
            
		}
		
		else if(caseValue.equalsIgnoreCase("case6")){
			
			String completeFilePath = "./Data/payloads/XML/goSessionService/createSessionXMLEmptyData";
			
			String payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{payloadReferenceValue});
			System.out.println("======== Payload ======= for : "+caseValue);
			System.out.println(payload);
			System.out.println("========================");   
			if(env.equalsIgnoreCase("fox7")){
				
				 uriRequest = "http://d7session.myntra.com/SessionStoreService/rest/sessions/";
				
			}else if(env.equalsIgnoreCase("production")){
				
				uriRequest = "http://session.myntra.com/SessionStoreService/rest/sessions/";
				
			}
            Response response = executeRequest(uriRequest, payload,"put");
            		
            System.out.println(response.toString());
            System.out.println(response.getEntity().toString());
            System.out.println(response.readEntity(String.class));
            
            Assert.assertEquals(response.getStatus(), 400,"Status response mismatch for :"+globalCaseValue);
            
		}
		else if(caseValue.equalsIgnoreCase("case7")){
			
			String completeFilePath = "./Data/payloads/XML/goSessionService/createSessionXMLEmptySession";
			
			String payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{payloadReferenceValue});
			System.out.println("======== Payload ======= for : "+caseValue);
			System.out.println(payload);
			System.out.println("========================");
			
			if(env.equalsIgnoreCase("fox7")){
				
				 uriRequest = "http://d7session.myntra.com/SessionStoreService/rest/sessions/";
				
			}else if(env.equalsIgnoreCase("production")){
				
				uriRequest = "http://session.myntra.com/SessionStoreService/rest/sessions/";
				
			}
            Response response = executeRequest(uriRequest, payload,"put");
            		
            System.out.println(response.toString());
            System.out.println(response.getEntity().toString());
            System.out.println(response.readEntity(String.class));
            
            Assert.assertEquals(response.getStatus(), 400,"Status response mismatch for :"+globalCaseValue);
            
		}
	}
	
	@Test(groups = {"Sanity"}, dataProvider = "optCreateSessionJsonDP", priority = 2)
	public void optCreateSessionJsonTests(String payloadReferenceValue, String caseValue) throws IOException, ParserConfigurationException, SAXException{
		
		globalCaseValue = caseValue;
		String completeFilePath = "./Data/payloads/JSON/goSessionService/optCreateSessionJson";
		MyntraService sessionService = Myntra.getService(ServiceType.LGP_SESSIONSERVICE,APINAME.LGPOPTCREATESESSIONJSON,init.Configurations,new String[]{payloadReferenceValue},completeFilePath,PayloadType.JSON,PayloadType.XML);
		if(caseValue.equalsIgnoreCase("case1")){
			
			RequestGenerator request= new RequestGenerator(sessionService,headersForJSON);
			System.out.println(sessionService.URL);
		    sessionValue = request.returnresponseasstring();
		    
		    getSessionJsonTests(sessionValue, "caseFromOptCreateSessionJsonTests");
		    
			Assert.assertEquals(request.response.getStatus(), 200,"Status response mismatch for :"+globalCaseValue);
			Assert.assertEquals(sessionService.Payload, xmlParseForData(responseFromGetJson, "data"),"Response Mis-Match for data key for : "+caseValue);
		}
		
		if(caseValue.equalsIgnoreCase("case3") || caseValue.equalsIgnoreCase("case4")|| caseValue.equalsIgnoreCase("case8") 
			|| caseValue.equalsIgnoreCase("case9") || caseValue.equalsIgnoreCase("case10") || caseValue.equalsIgnoreCase("case11")){
			
			RequestGenerator request= new RequestGenerator(sessionService,headersForJSON);
			System.out.println(sessionService.URL);
		    System.out.println(request.returnresponseasstring());
			Assert.assertEquals(request.response.getStatus(), 200,"Status response mismatch for :"+globalCaseValue);
		
		}
		
		if(caseValue.equalsIgnoreCase("case2")){
			
			RequestGenerator request= new RequestGenerator(sessionService,headersForJSON );
			System.out.println(sessionService.URL);
		    System.out.println(request.returnresponseasstring());
			Assert.assertEquals(request.response.getStatus(), 200,"Status response mismatch for :"+globalCaseValue);
		
		}
		
		

	}

	@Test(groups = {"Sanity"}, dataProvider = "getSessionForXMLandJsonDP",priority = 3)
	public void getSessionJsonTests(String urlReferenceValue, String caseValue) throws IOException, ParserConfigurationException, SAXException{
		
		globalCaseValue = caseValue;
		
		if(caseValue.equalsIgnoreCase("case1") || caseValue.equalsIgnoreCase("case7")){
			
			optCreateSessionJsonTests(createJson("case1"), "case1");
			
			if(caseValue.equalsIgnoreCase("case1")){
				
				urlReferenceValue = sessionValue;
			}
			
			else if (caseValue.equalsIgnoreCase("case7")){
				
				urlReferenceValue = sessionValue+"      ";
			}
			
			urlReferenceValue = sessionValue;
			MyntraService sessionService = getQueryRequestSingleParam(ServiceType.LGP_SESSIONSERVICE,APINAME.LGPGETSESSIONXML, urlReferenceValue);
			RequestGenerator request= new RequestGenerator(sessionService);
			System.out.println(sessionService.URL);
		    System.out.println(request.returnresponseasstring());
			Assert.assertEquals(request.response.getStatus(), 200,"Status response mismatch for :"+globalCaseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case2")){
			
			MyntraService sessionService = getQueryRequestSingleParam(ServiceType.LGP_SESSIONSERVICE,APINAME.LGPGETSESSIONXML, urlReferenceValue);
			RequestGenerator request= new RequestGenerator(sessionService);
			System.out.println(sessionService.URL);
		    System.out.println(request.returnresponseasstring());
			Assert.assertEquals(request.response.getStatus(), 404,"Status response mismatch for :"+globalCaseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("caseFromOptCreateSessionJsonTests")){
			
			MyntraService sessionService = getQueryRequestSingleParam(ServiceType.LGP_SESSIONSERVICE,APINAME.LGPGETSESSIONXML, urlReferenceValue);
			RequestGenerator request= new RequestGenerator(sessionService);
			System.out.println(sessionService.URL);
			responseFromGetJson = request.returnresponseasstring();
		    System.out.println(responseFromGetJson);
			Assert.assertEquals(request.response.getStatus(), 200,"Status response mismatch for :"+globalCaseValue);
		}
		
	}
	
	@Test(groups = {"Sanity"}, dataProvider = "getSessionForXMLandJsonDP",priority = 4)
	public void getSessionXMLTests(String urlReferenceValue, String caseValue) throws IOException, ParserConfigurationException, SAXException{
		
		globalCaseValue = caseValue;
		
		if(caseValue.equalsIgnoreCase("case1") || caseValue.equalsIgnoreCase("case7")){
			
			optCreateSessionJsonTests(createJson("case1"), "case1");
			
			urlReferenceValue = sessionValue;
			
			MyntraService sessionService = getQueryRequestSingleParam(ServiceType.LGP_SESSIONSERVICE,APINAME.LGPGETSESSIONXML, urlReferenceValue);
			RequestGenerator request= new RequestGenerator(sessionService);
			System.out.println(sessionService.URL);
			
			if(caseValue.equalsIgnoreCase("case1")){
				
				responseFromGetXml = request.returnresponseasstring();
			    System.out.println(responseFromGetXml);
			    
			}else{
				
				 System.out.println(request.returnresponseasstring());
				
			}
			
		    String idTagValueFromXmlString = xmlParseForData(request.returnresponseasstring(),"id");
			Assert.assertEquals(request.response.getStatus(), 200,"Status response mismatch for :"+globalCaseValue);
			Assert.assertEquals(idTagValueFromXmlString, urlReferenceValue,"Xid Mismatch for : "+caseValue);
			Assert.assertEquals(idTagValueFromXmlString.length(), urlReferenceValue.length(),"Xid Length Mis-Match for : "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("casePartialUpdate")){
			
			MyntraService sessionService = getQueryRequestSingleParam(ServiceType.LGP_SESSIONSERVICE,APINAME.LGPGETSESSIONXML, urlReferenceValue);
			RequestGenerator request= new RequestGenerator(sessionService);
			responseFromGetXml = request.returnresponseasstring();
		    System.out.println(responseFromGetXml);
			System.out.println(sessionService.URL);
		    System.out.println(request.returnresponseasstring());
			Assert.assertEquals(request.response.getStatus(), 200,"Status response mismatch for :"+globalCaseValue);
			
		}	
			
		
		
		else if(caseValue.equalsIgnoreCase("case2")){
			
			MyntraService sessionService = getQueryRequestSingleParam(ServiceType.LGP_SESSIONSERVICE,APINAME.LGPGETSESSIONXML, urlReferenceValue);
			RequestGenerator request= new RequestGenerator(sessionService);
			System.out.println(sessionService.URL);
		    System.out.println(request.returnresponseasstring());
			Assert.assertEquals(request.response.getStatus(), 404,"Status response mismatch for :"+globalCaseValue);
			
		}
		
	}
	
	@Test(groups = {"Sanity"}, dataProvider = "partialUpdateSessionDP",priority = 5)
	public void partialUpdateSession(String urlReference, String jsonData, String caseValue) throws IOException, ParserConfigurationException, SAXException{
		
		globalCaseValue = caseValue;
		String uriRequest = null;
		if(caseValue.equalsIgnoreCase("case1") || caseValue.equalsIgnoreCase("case8") || caseValue.equalsIgnoreCase("case2") || caseValue.equalsIgnoreCase("case9")
				|| caseValue.equalsIgnoreCase("case10") || caseValue.equalsIgnoreCase("case11")|| caseValue.equalsIgnoreCase("case23")){
			
			optCreateSessionJsonTests(createJson("case1"), "case1");
			urlReference = sessionValue;
			
			String completeFilePath = "./Data/payloads/XML/goSessionService/partialUpdateValid";
			String payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{urlReference,jsonData});
			System.out.println("======== Payload ======= for : "+caseValue);
			System.out.println(payload);
			System.out.println("========================");
			
			if(env.equalsIgnoreCase("fox7")){
				
				 uriRequest = "http://d7session.myntra.com/SessionStoreService/rest/sessions/"+urlReference;
				
			}else if(env.equalsIgnoreCase("production")){
				
				uriRequest = "http://session.myntra.com/SessionStoreService/rest/sessions/"+urlReference;
				
			}
			
			Response response = executeRequest(uriRequest, payload,"post");
			
			System.out.println(response.toString());
	        System.out.println(response.getEntity().toString());
	        //System.out.println(response.readEntity(String.class));
	           
	        
	        String dataTagValueFromPayload = xmlParseForData(payload,"data");
	        String outPutXid = response.readEntity(String.class).split("/")[2];
            getSessionXMLTests(outPutXid, "casePartialUpdate");
            String dataTagValueFromXmlString = xmlParseForData(responseFromGetXml,"data");
            
	        Assert.assertEquals(dataTagValueFromXmlString.trim(), dataTagValueFromPayload.trim(),"Data Tag Value Mis-Match For : "+globalCaseValue);
	        
	        Assert.assertEquals(response.getStatus(), 200,"Status response mismatch for :"+globalCaseValue);
	        Assert.assertEquals(outPutXid, urlReference,"Response XID mis-match : "+globalCaseValue);
	        
		}
		
		else if(caseValue.equalsIgnoreCase("case21") || caseValue.equalsIgnoreCase("case22")){
			
			optCreateSessionJsonTests(createJson("case1"), "case1");
			//urlReference = sessionValue;
			
			String completeFilePath = null;
			String payload = null;
			
			completeFilePath = "./Data/payloads/XML/goSessionService/partialUpdateValid";
			
			if(caseValue.equalsIgnoreCase("case21")){
				
				payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{sessionValue,jsonData});
				System.out.println("======== Payload ======= for : "+caseValue);
				System.out.println(payload);
				System.out.println("========================");
				
			}
			
			else if(caseValue.equalsIgnoreCase("case22")){
				
				payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{urlReference,jsonData});
				System.out.println("======== Payload ======= for : "+caseValue);
				System.out.println(payload);
				System.out.println("========================");
			}
			
			if(env.equalsIgnoreCase("fox7")){
				
				 uriRequest = "http://d7session.myntra.com/SessionStoreService/rest/sessions/"+urlReference;
				
			}else if(env.equalsIgnoreCase("production")){
				
				uriRequest = "http://session.myntra.com/SessionStoreService/rest/sessions/"+urlReference;
				
			}
			Response response = executeRequest(uriRequest, payload,"post");
			
			System.out.println(response.toString());
	        System.out.println(response.getEntity().toString());
	        System.out.println(response.readEntity(String.class));
	        
	        
	        if(caseValue.equalsIgnoreCase("case21")){
	        	
	        	Assert.assertEquals(response.getStatus(), 400,"Status response mismatch for :"+globalCaseValue);
	        }
	        
	        else if(caseValue.equalsIgnoreCase("case22")){
	        	
	        	Assert.assertEquals(response.getStatus(), 404,"Status response mismatch for :"+globalCaseValue);
	        }
			
		}
		
		else if(caseValue.equalsIgnoreCase("case12") || caseValue.equalsIgnoreCase("case13") || caseValue.equalsIgnoreCase("case14")
				||caseValue.equalsIgnoreCase("case15") || caseValue.equalsIgnoreCase("case16") || caseValue.equalsIgnoreCase("case17") || caseValue.equalsIgnoreCase("case18")){
			
			optCreateSessionJsonTests(createJson("case1"), "case1");
			//urlReference = sessionValue;
			
			String completeFilePath = null;
			String payload = null;
			
			if(caseValue.equalsIgnoreCase("case12") || caseValue.equalsIgnoreCase("case14")){
				
				 completeFilePath = "./Data/payloads/XML/goSessionService/partialUpdateValid";
				 payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{urlReference,jsonData});
				 System.out.println("======== Payload ======= for : "+caseValue);
				 System.out.println(payload);
				 System.out.println("========================");
			}else if (caseValue.equalsIgnoreCase("case13")){
				
				completeFilePath = "./Data/payloads/XML/goSessionService/partialUpdateEmptyId";
				payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{jsonData});
				System.out.println("======== Payload ======= for : "+caseValue);
				System.out.println(payload);
				System.out.println("========================");
			}
			
			else if (caseValue.equalsIgnoreCase("case15")){
				
				urlReference = "@#$"+sessionValue;
				completeFilePath = "./Data/payloads/XML/goSessionService/partialUpdateEmptyId";
				payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{urlReference,jsonData});
				System.out.println("======== Payload ======= for : "+caseValue);
				System.out.println(payload);
				System.out.println("========================");
			}
			
			else if (caseValue.equalsIgnoreCase("case16")){
				
				urlReference = "  "+sessionValue;
				completeFilePath = "./Data/payloads/XML/goSessionService/partialUpdateEmptyId";
				payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{urlReference,jsonData});
				System.out.println("======== Payload ======= for : "+caseValue);
				System.out.println(payload);
				System.out.println("========================");
			}
			
			else if (caseValue.equalsIgnoreCase("case17")){
				
				urlReference = sessionValue+"  ";
				completeFilePath = "./Data/payloads/XML/goSessionService/partialUpdateEmptyId";
				payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{urlReference,jsonData});
				System.out.println("======== Payload ======= for : "+caseValue);
				System.out.println(payload);
				System.out.println("========================");
			}
			
			else if (caseValue.equalsIgnoreCase("case18")){
				
				completeFilePath = "./Data/payloads/XML/goSessionService/partialUpdateWithoutId";
				payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{jsonData});
				System.out.println("======== Payload ======= for : "+caseValue);
				System.out.println(payload);
				System.out.println("========================");
			}
			
			if(env.equalsIgnoreCase("fox7")){
				
				 uriRequest = "http://d7session.myntra.com/SessionStoreService/rest/sessions/"+sessionValue;
				
			}else if(env.equalsIgnoreCase("production")){
				
				uriRequest = "http://session.myntra.com/SessionStoreService/rest/sessions/"+sessionValue;
				
			}
			
			Response response = executeRequest(uriRequest, payload,"post");
			
			System.out.println(response.toString());
	        System.out.println(response.getEntity().toString());
	        System.out.println(response.readEntity(String.class));
	            
	        Assert.assertEquals(response.getStatus(), 400,"Status response mismatch for :"+globalCaseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case3") || caseValue.equalsIgnoreCase("case4") || caseValue.equalsIgnoreCase("case19") || caseValue.equalsIgnoreCase("case20")){
			
			optCreateSessionJsonTests(createJson("case1"), "case1");
			urlReference = sessionValue;
			
			String[] payloadParams = jsonData.split("==");
			
			String completeFilePath = null;
			
			if(caseValue.equalsIgnoreCase("case3") || caseValue.equalsIgnoreCase("case4")){
				
				 completeFilePath = "./Data/payloads/XML/goSessionService/partialUpdateXMLTimeStamp";
				
			}
			
			else if(caseValue.equalsIgnoreCase("case19")){
				
				 completeFilePath = "./Data/payloads/XML/goSessionService/partialUpdateLastAccessedDate";
				
			}
			
			else if(caseValue.equalsIgnoreCase("case20")){
				
				completeFilePath = "./Data/payloads/XML/goSessionService/partialUpdateLastModifiedDate";
				
			}
			String payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{urlReference,payloadParams[0],payloadParams[1]});
			System.out.println("======== Payload ======= for : "+caseValue);
			System.out.println(payload);
			System.out.println("========================");
			
			if(env.equalsIgnoreCase("fox7")){
				
				 uriRequest = "http://d7session.myntra.com/SessionStoreService/rest/sessions/"+urlReference;
				
			}else if(env.equalsIgnoreCase("production")){
				
				uriRequest = "http://session.myntra.com/SessionStoreService/rest/sessions/"+urlReference;
				
			}
			
			Response response = executeRequest(uriRequest, payload,"post");
			
			System.out.println(response.toString());
	        System.out.println(response.getEntity().toString());
	        System.out.println(response.readEntity(String.class));
	            
	        Assert.assertEquals(response.getStatus(), 200,"Status response mismatch for :"+globalCaseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case5") || caseValue.equalsIgnoreCase("case7") ){
			
			optCreateSessionJsonTests(createJson("case1"), "case1");
			urlReference = sessionValue;
			String completeFilePath = null;
			if(caseValue.equalsIgnoreCase("case5")){
				
				 completeFilePath = "./Data/payloads/XML/goSessionService/partialUpdateXMLTimeStamp";
				
			}else if (caseValue.equalsIgnoreCase("case7")){
				
				completeFilePath = "./Data/payloads/XML/goSessionService/createSessionXMLEmptySession";
			}
			
			
			String payload = readFileAsString(completeFilePath);
			
			if(env.equalsIgnoreCase("fox7")){
				
				 uriRequest = "http://d7session.myntra.com/SessionStoreService/rest/sessions/"+urlReference;
				
			}else if(env.equalsIgnoreCase("production")){
				
				uriRequest = "http://session.myntra.com/SessionStoreService/rest/sessions/"+urlReference;
				
			}
			Response response = executeRequest(uriRequest, payload,"post");
			
			System.out.println(response.toString());
	        System.out.println(response.getEntity().toString());
	        System.out.println(response.readEntity(String.class));
	            
	        Assert.assertEquals(response.getStatus(), 400,"Status response mismatch for :"+globalCaseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case6")){
			
			optCreateSessionJsonTests(createJson("case1"), "case1");
			urlReference = sessionValue;
			
			String completeFilePath = "./Data/payloads/XML/goSessionService/partialUpdateEmptyData";
			String payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{urlReference});
			System.out.println("======== Payload ======= for : "+caseValue);
			System.out.println(payload);
			System.out.println("========================");
			
			if(env.equalsIgnoreCase("fox7")){
				
				 uriRequest = "http://d7session.myntra.com/SessionStoreService/rest/sessions/"+urlReference;
				
			}else if(env.equalsIgnoreCase("production")){
				
				uriRequest = "http://session.myntra.com/SessionStoreService/rest/sessions/"+urlReference;
				
			}
			Response response = executeRequest(uriRequest, payload,"post");
			
			System.out.println(response.toString());
	        System.out.println(response.getEntity().toString());
	        System.out.println(response.readEntity(String.class));
	            
	        Assert.assertEquals(response.getStatus(), 400,"Status response mismatch for :"+globalCaseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case24")){
			
			sessionValue = "randomId";
			
			String completeFilePath = "./Data/payloads/XML/goSessionService/partialUpdateValid";
			String payload = apiUtil.preparepayload(apiUtil.readFileAsString(completeFilePath), new String[]{urlReference,jsonData});
			System.out.println("======== Payload ======= for : "+caseValue);
			System.out.println(payload);
			System.out.println("========================");
			
			if(env.equalsIgnoreCase("fox7")){
				
				 uriRequest = "http://d7session.myntra.com/SessionStoreService/rest/sessions/"+sessionValue;
				
			}else if(env.equalsIgnoreCase("production")){
				
				uriRequest = "http://session.myntra.com/SessionStoreService/rest/sessions/"+sessionValue;
				
			}
			Response response = executeRequest(uriRequest, payload,"post");
			
			System.out.println(response.toString());
	        System.out.println(response.getEntity().toString());
	        System.out.println(response.readEntity(String.class));
	            
	        Assert.assertEquals(response.getStatus(), 400,"Status response mismatch for :"+globalCaseValue);
			
		}
		
	}
	

	@Test(groups = {"Sanity"}, dataProvider = "optUpdatePartialSession_DP",priority = 1)
	public void optUpdatePartialSessionTest(Map<String,Object> testData) throws IOException{
		String xid=null;
		ArrayList<String> failures = new ArrayList<>();
		String completeFilePath = "./Data/payloads/JSON/goSessionService/optCreateSessionJson";
		//Create session call
		if(testData.containsKey("xid")){
			xid=(String)testData.get("xid");
		}else if(testData.containsKey("createSessionPayload")){
			
			MyntraService sessionService = Myntra.getService(ServiceType.LGP_SESSIONSERVICE,APINAME.LGPOPTCREATESESSIONJSON,init.Configurations,new String[]{(String)testData.get("createSessionPayload")},completeFilePath,PayloadType.JSON,PayloadType.XML);
			RequestGenerator request= new RequestGenerator(sessionService,headersForJSON);
			System.out.println(sessionService.URL);
			System.out.println(request.response);
			xid = request.returnresponseasstring();
			System.out.println("xid: "+xid);
			
			
		}
		
		MyntraService sessionService = Myntra.getService(ServiceType.LGP_SESSIONSERVICE,APINAME.LGPOPTUPDATEPARTIALSESSION,init.Configurations,new String[]{(String)testData.get("updateSessionPayload")},completeFilePath,PayloadType.JSON,PayloadType.XML);
		HashMap<String,String> header = new HashMap<>();
		APIUtilities utilities = new APIUtilities();
		sessionService.URL = utilities.prepareparameterizedURL(sessionService.URL, xid);
		header.putAll(headersForJSON);
		//Add the headers if any specified
		String xed="";
		if(testData.containsKey("XED")){
			xed = ""+((System.currentTimeMillis()/1000)+(int)testData.get("XED"));
			header.put("XED", xed);
		}
		String xla="";
		if(testData.containsKey("XLA")){
			xla = ""+((System.currentTimeMillis()/1000)+(int)testData.get("XLA"));
			header.put("XLA", xla);
		}
		String xlm="";
		if(testData.containsKey("XLM")){
			xlm= ""+((System.currentTimeMillis()/1000)+(int)testData.get("XLM"));
			header.put("XLM", xlm);
		}
		
		RequestGenerator request= new RequestGenerator(sessionService, header);
		if(testData.containsKey("updateFailureStatus")){
			if(!(request.response.getStatus()==(int)testData.get("updateFailureStatus"))){
				failures.add("updateSessionFailureStatus : The update session was expected to fail with status "+testData.get("updateFailureStatus")+" but return "+request.response.getStatus());
			}
		}else{
			
			MyntraService getSessionService = getQueryRequestSingleParam(ServiceType.LGP_SESSIONSERVICE,APINAME.LGPOPTGETSESSION, xid);
			RequestGenerator getRequest = new RequestGenerator(getSessionService);
			String response = getRequest.returnresponseasstring();
			String updateSessionJson=(String)testData.get("updateSessionPayload");
			
			try {
				response = new JSONObject(response).toString();
				updateSessionJson = new JSONObject(updateSessionJson).toString();
			} catch (JSONException e) {
				
			}
			
			if(getRequest.response.getStatus()!=(int)testData.get("expectedResponse")){
				failures.add("response code : The response expected ("+testData.get("expectedResponse")+") does not match the actual response "+getRequest.response.getStatus());
			}else if(getRequest.response.getStatus()==200){
				if(!response.equals(updateSessionJson)){
					failures.add("session response: The session payload returned does not match with the one updated");
				}
			}
			MultivaluedMap<String, Object> responseHeaders = getRequest.response.getHeaders();
			if(testData.containsKey("XLA")){
				String responseXla=((LinkedList<Object>)responseHeaders.get("Xla")).getFirst().toString();
				if(responseXla.equals(xla)){
					failures.add("xla : The XLA should not be updated with the given data");
				}
			}
			if(testData.containsKey("XLM")){
				String responseXlm=((LinkedList<Object>)responseHeaders.get("Xlm")).getFirst().toString();
				if(responseXlm.equals(xlm)){
					failures.add("xlm : The XLM should not be updated with the given data");
				}
			}
		}
		if(failures.size()>0){
			Assert.fail(getFailuresPoints(failures));
		}
	}
	public String getFailuresPoints(ArrayList<String> failures){
		String failureAsPoints="";
		int i=1;
		for(String errMsg:failures){
			failureAsPoints= failureAsPoints +(i++)+". "+errMsg;
		}
		return(failureAsPoints);
	}
		
	

	@Test(groups = {"Sanity"}, dataProvider = "optChangeSession_DP",priority = 1)
	public void optChangeSessionTest(Map<String,Object> testData) throws IOException{
		String xid=null;
		ArrayList<String> failures = new ArrayList<>();
		String completeFilePath = "./Data/payloads/JSON/goSessionService/optCreateSessionJson";
		//Create session call
		if(testData.containsKey("xid")){
			xid=(String)testData.get("xid");
		}else if(testData.containsKey("createSessionPayload")){
			
			MyntraService sessionService = Myntra.getService(ServiceType.LGP_SESSIONSERVICE,APINAME.LGPOPTCREATESESSIONJSON,init.Configurations,new String[]{(String)testData.get("createSessionPayload")},completeFilePath,PayloadType.JSON,PayloadType.XML);
			RequestGenerator request= new RequestGenerator(sessionService,headersForJSON);
			System.out.println(sessionService.URL);
			System.out.println(request.response);
			xid = request.returnresponseasstring();
			System.out.println("xid: "+xid);
			
			
		}
		
		MyntraService sessionService = Myntra.getService(ServiceType.LGP_SESSIONSERVICE,APINAME.LGPOPTCHANGESESSION,init.Configurations,new String[]{(String)testData.get("changeSessionPayload")},completeFilePath,PayloadType.JSON,PayloadType.XML);
		HashMap<String,String> header = new HashMap<>();
		APIUtilities utilities = new APIUtilities();
		sessionService.URL = utilities.prepareparameterizedURL(sessionService.URL, xid);
		header.putAll(headersForJSON);
		//Add the headers if any specified
		String xed="";
		if(testData.containsKey("XED")){
			xed = ""+((System.currentTimeMillis()/1000)+(int)testData.get("XED"));
			header.put("XED", xed);
		}
		String xla="";
		if(testData.containsKey("XLA")){
			xla = ""+((System.currentTimeMillis()/1000)+(int)testData.get("XLA"));
			header.put("XLA", xla);
		}
		String xlm="";
		if(testData.containsKey("XLM")){
			xlm= ""+((System.currentTimeMillis()/1000)+(int)testData.get("XLM"));
			header.put("XLM", xlm);
		}
		
		RequestGenerator request= new RequestGenerator(sessionService, header);
		if(testData.containsKey("changeFailureStatus")){
			if(!(request.response.getStatus()==(int)testData.get("changeFailureStatus"))){
				failures.add("changeSessionFailureStatus : The change session was expected to fail with status "+testData.get("changeFailureStatus")+" but returned "+request.response.getStatus());
			}
		}else{
			if(xid == request.returnresponseasstring()){
				failures.add("change xid: The xid is not changed after change call");
			}
			xid = request.returnresponseasstring();
			MyntraService getSessionService = getQueryRequestSingleParam(ServiceType.LGP_SESSIONSERVICE,APINAME.LGPOPTGETSESSION, xid);
			RequestGenerator getRequest = new RequestGenerator(getSessionService);
			String response = getRequest.returnresponseasstring();
			String changeSessionJson=(String)testData.get("changeSessionPayload");
			
			try {
				response = new JSONObject(response).toString();
				changeSessionJson = new JSONObject(changeSessionJson).toString();
			} catch (JSONException e) {
				
			}
			
			if(getRequest.response.getStatus()!=(int)testData.get("expectedResponse")){
				failures.add("response code : The response expected ("+testData.get("expectedResponse")+") does not match the actual response "+getRequest.response.getStatus());
			}else if(getRequest.response.getStatus()==200){
				if(!response.equals(changeSessionJson)){
					failures.add("session response: The session payload returned does not match with the one updated");
				}
			}
			MultivaluedMap<String, Object> responseHeaders = getRequest.response.getHeaders();
			if(testData.containsKey("XLA")){
				String responseXla=((LinkedList<Object>)responseHeaders.get("Xla")).getFirst().toString();
				if(responseXla.equals(xla)){
					failures.add("xla : The XLA should not be updated with the given data");
				}
			}
			if(testData.containsKey("XLM")){
				String responseXlm=((LinkedList<Object>)responseHeaders.get("Xlm")).getFirst().toString();
				if(responseXlm.equals(xlm)){
					failures.add("xlm : The XLM should not be updated with the given data");
				}
			}
		}
		if(failures.size()>0){
			Assert.fail(getFailuresPoints(failures));
		}
	}
	
	

	@Test(groups = {"Sanity"}, dataProvider = "optGetSession_DP",priority = 1)
	public void optGetSessionTest(Map<String,Object> testData) throws IOException{
		String xid=null;
		ArrayList<String> failures = new ArrayList<>();
		String completeFilePath = "./Data/payloads/JSON/goSessionService/optCreateSessionJson";
		//Create session call
		if(testData.containsKey("xid")){
			xid=(String)testData.get("xid");
		}else if(testData.containsKey("createSessionPayload")){
			
			MyntraService sessionService = Myntra.getService(ServiceType.LGP_SESSIONSERVICE,APINAME.LGPOPTCREATESESSIONJSON,init.Configurations,new String[]{(String)testData.get("createSessionPayload")},completeFilePath,PayloadType.JSON,PayloadType.XML);
			RequestGenerator request= new RequestGenerator(sessionService,headersForJSON);
			System.out.println(sessionService.URL);
			System.out.println(request.response);
			xid = request.returnresponseasstring();
			System.out.println("xid: "+xid);
			
			
		}
		
		MyntraService getSessionService = getQueryRequestSingleParam(ServiceType.LGP_SESSIONSERVICE,APINAME.LGPOPTGETSESSION, xid);
		RequestGenerator getRequest = new RequestGenerator(getSessionService);
		String response = getRequest.returnresponseasstring();
		String createSessionPayload=(String)testData.get("createSessionPayload");
		
		try {
			response = new JSONObject(response).toString();
			createSessionPayload = new JSONObject(createSessionPayload).toString();
		} catch (JSONException e) {
			
		}
		
		if(getRequest.response.getStatus()!=(int)testData.get("expectedResponse")){
			failures.add("response code : The response expected ("+testData.get("expectedResponse")+") does not match the actual response "+getRequest.response.getStatus());
		}else if(getRequest.response.getStatus()==200){
			if(!response.equals(createSessionPayload)){
				failures.add("session response: The session payload returned does not match");
			}
		}
		
		if(failures.size()>0){
			Assert.fail(getFailuresPoints(failures));
		}
	}
}











