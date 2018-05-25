package com.myntra.apiTests.portalservices.lgpservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import com.myntra.apiTests.portalservices.lgpServe.feed.FeedObjectHelper;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

import net.minidev.json.JSONArray;

public class LgpServicesHelper {
	
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DevApiServiceHelper.class);
	static String xId, sXid;
	APIUtilities apiUtil=new APIUtilities();
	WebTarget target;
	ClientConfig config;
	Client client;
	Response response;
	Invocation.Builder invBuilder;
	static FeedObjectHelper feedhelper= new FeedObjectHelper();
	static APIUtilities utilities = new APIUtilities();
	static int cnt;
	
	
	private void getAndSetTokens(String userName, String password)
	{
		System.out.println("User name :" + userName + "  Pass: " + password);
		MyntraService serviceSignIn = Myntra.getService(ServiceType.PORTAL_IDP,
				APINAME.SIGNIN, init.Configurations, new String[] { userName,
						password });
		System.out.println(serviceSignIn.URL);
		log.info(serviceSignIn.URL);
		RequestGenerator reqSignIn = new RequestGenerator(serviceSignIn);
		System.out.println(reqSignIn.response.getHeaderString(sXid));
		MultivaluedMap<String, Object> map = reqSignIn.response.getHeaders();
		for (Map.Entry<?,?> entry : map.entrySet()) {
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
				xId = entry.getValue().toString();
		}
		System.out.println("xID from Headers  : " + xId);
		xId = xId.substring((xId.indexOf("[") + 1), xId.lastIndexOf("]"));
		sXid = reqSignIn.respvalidate.GetNodeTextUsingIndex("xsrfToken");
		System.out.println("sXid from Response  : " + sXid);
		if (sXid.contains("'"))
			sXid = sXid.substring(sXid.indexOf("'") + 1, sXid.lastIndexOf("'"));
		else
			sXid = sXid.substring(sXid.indexOf("[") + 1, sXid.lastIndexOf("]"));
		System.out.println("xID :" + xId);
		System.out.println("sxid : " + sXid);
		log.info(reqSignIn.response);
	}
	
	public HashMap<String,String> getXIDandSXidHeader(String userName, String password)
	{
		String xID = "", sXid="";
		HashMap<String, String> xidAndsxId=new HashMap<String, String>();
		System.out.println("\nPrinting \n Username : "+userName+" \n Password : "+password);
		log.info("\nPrinting \n Username :"+userName+" \n Password: "+password);
		
		MyntraService serviceSignIn = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.SIGNIN, init.Configurations, new String[]{ userName, password });
		System.out.println("\nPrinting IDP Service API URL : "+serviceSignIn.URL);
		log.info("\nPrinting IDP Service API URL : "+serviceSignIn.URL);
		
		System.out.println("\nPrinting IDP Service API Payload : \n\n"+serviceSignIn.Payload);
		log.info("\nPrinting IDP Service API Payload : \n\n"+serviceSignIn.Payload);
		
		RequestGenerator reqSignIn = new RequestGenerator(serviceSignIn);
		System.out.println("\nPrinting IDP Service API response .....\n\n"+reqSignIn.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDP Service API response .....\n\n"+reqSignIn.respvalidate.returnresponseasstring());
		
		MultivaluedMap<String, Object> map = reqSignIn.response.getHeaders();
		for (Map.Entry<?,?> entry : map.entrySet())
		{
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
				xID = entry.getValue().toString();
		}
		//System.out.println("\nPrinting xID from Headers  : "+xID);
		log.info("\nPrinting xID from Headers  : "+xID);
		
		xID = xID.substring((xID.indexOf("[") + 1), xID.lastIndexOf("]"));
		
		//System.out.println("\nPrinting final xID : "+xID);
		log.info("\nPrinting final xID : "+xID);
		sXid = reqSignIn.respvalidate.GetNodeTextUsingIndex("xsrfToken");
		
		if(sXid.contains("'"))
			sXid = sXid.substring(sXid.indexOf("'")+1, sXid.lastIndexOf("'"));
		else
			sXid = sXid.substring(sXid.indexOf("[")+1, sXid.lastIndexOf("]"));
		//System.out.println("\nPrinting final sxid : " + sXid);
		log.info("\nPrinting final sxid : " + sXid);
		xidAndsxId.put("xid", xID);
		xidAndsxId.put("X-CSRF-Token", sXid);
		System.out.println("xID :" + xID);
		System.out.println("sxid : " + sXid);
		
		return xidAndsxId;
	}
	
	
	public RequestGenerator invokeLgpserviceAbTest(String username,String password){
		
		getAndSetTokens(username, password);
		HashMap<String, String> abtestTokens=new HashMap<String, String>();
		abtestTokens.put("xsrf", sXid);
		abtestTokens.put("xid", xId);
		
		MyntraService lgpService=Myntra.getService(ServiceType.LGP_ABTESTSERVICE, APINAME.ABTEST, init.Configurations);
		RequestGenerator lgpServiceReq=new RequestGenerator(lgpService,abtestTokens);
		
		return lgpServiceReq;
		
		
		
	}
	
	public String getxid(String username,String password) throws Exception{
		
		String url="http://54.251.114.253:6060/idp/auth/signin";
		String XiD=null;
		String payload=apiUtil.preparepayload(new Toolbox().readFileAsString("./Data/payloads/JSON/signin"), new String[]{username,password});
		client=ClientBuilder.newClient();
		target=client.target(url);
		invBuilder=target.request(MediaType.APPLICATION_JSON);
		response=invBuilder.accept(MediaType.APPLICATION_JSON).post(Entity.json(payload));
		
		MultivaluedMap<String, Object> map =response.getHeaders();
		for (Map.Entry<?,?> entry : map.entrySet())
		{
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
				XiD = entry.getValue().toString();
		}
		//System.out.println("\nPrinting xID from Headers  : "+xID);
		log.info("\nPrinting xID from Headers  : "+XiD);
		
		XiD = XiD.substring((XiD.indexOf("[") + 1), XiD.lastIndexOf("]"));
		
		
		System.out.println("Xid:"+XiD);
		
		return XiD;
		
		
	}
	
	public int getNumberFromString(String value){
		StringBuilder sb=new StringBuilder();
		char[] ch=value.substring(0, value.indexOf("||")).toCharArray();
		
		for(char c:ch){
			if(Character.isDigit(c)){
				sb.append(c);
			}
			
		}
		
		return Integer.parseInt(sb.toString());
	}

	
	//LGP SERVE - SERVICE HELPER METHODS
	
	public RequestGenerator invokeLGPServeActions(String username, String password, APINAME APIName)
	{
		getAndSetTokens(username, password);
		HashMap<String, String> lgpServeActionTokens=new HashMap<String, String>();
		lgpServeActionTokens.put("xid", xId);
		MyntraService lgpActionService = Myntra.getService(ServiceType.LGP_SERVE, APIName, init.Configurations);
		RequestGenerator lgpActionServiceRequest = new RequestGenerator(lgpActionService,lgpServeActionTokens);
		return lgpActionServiceRequest;
		
	};
	
	public String getXidForCredentials(String username,String password) throws IOException
	{
		String xid="";
		String payload=apiUtil.preparepayload(new Toolbox().readFileAsString("./Data/payloads/JSON/devapisignin"), new String[]{username,password});
		MyntraService signInService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS,APINAME.DEVAPISIGNIN,init.Configurations,payload);
		RequestGenerator reqSignIn = new RequestGenerator(signInService);
		String response=reqSignIn.returnresponseasstring();
		xid =JsonPath.read(response, "$.meta.token").toString().trim();
		return xid;
		
	}
	
	public HashMap<String,String> getXidandUidxForCredential(String username, String password) throws IOException{
		
		HashMap<String,String> userIds = new HashMap<String, String>();
		String xidValue = null;
		String uidxValue = null;
		
		String payload=apiUtil.preparepayload(new Toolbox().readFileAsString("./Data/payloads/JSON/devapisignin"), new String[]{username,password});
		MyntraService signInService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS,APINAME.DEVAPISIGNIN,init.Configurations,payload);
		RequestGenerator reqSignIn = new RequestGenerator(signInService);
		String response=reqSignIn.returnresponseasstring();
	
		
		JsonParser parser = new JsonParser();
		JsonObject jsonObj = (JsonObject) parser.parse(response);
		
		if(jsonObj.has("meta") && jsonObj.has("data")){
			
			xidValue = JsonPath.read(response, "$.meta.token").toString().trim();
			uidxValue = JsonPath.read(response, "$.data.uidx").toString().trim();
			
			
		}
		
		userIds.put("xid", xidValue);
		userIds.put("uidx", uidxValue);
		
		return userIds;
	}
	
	public String getUidxForCredential(String username, String password) throws IOException{
		
		String uidxValue;
		
		String payload=apiUtil.preparepayload(new Toolbox().readFileAsString("./Data/payloads/JSON/devapisignin"), new String[]{username,password});
		MyntraService signInService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS,APINAME.DEVAPISIGNIN,init.Configurations,payload);
		RequestGenerator reqSignIn = new RequestGenerator(signInService);
		String response=reqSignIn.returnresponseasstring();
		
		uidxValue = JsonPath.read(response,"$.data.uidx").toString().trim();
		
		return uidxValue;
		
	}
	
	public boolean validateSchemaValidation(String cardResponse)
	{
		boolean check = false;
		
		return check;
	}
	
	public HashMap<String, Object> validateFeedCardsIndividually(String response,String apiVersion,String tests) throws IOException
	{
		cnt=0;
		String result ="false";
		String componentResult="false";
		String basicComponentresult="false";
		HashMap<String, Object> validationDetails = new HashMap<>();
		ArrayList<String> individualCardResults = new ArrayList<>();
		ArrayList<String> individualComponentResults = new ArrayList<>();
		int count = Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
		String[] cartResponses = getIndividualCardResponses(response,count);
		for(int i=0;i<cartResponses.length;i++)
		{
			String card_type=JsonPath.read(cartResponses[i], "$.props.meta.og:name");
			String objectId = JsonPath.read(cartResponses[i], "$.props.id");
			String basicComponentType=JsonPath.read(cartResponses[i], "$.type");
			JSONArray components=JsonPath.read(cartResponses[i], "$.children");
			System.out.println(components.size());
			if(tests!="timelines")
			{
				componentResult=String.valueOf(feedhelper.componentValidationsForFeeds(card_type,apiVersion,cartResponses[i]));
				individualCardResults.add(card_type+" Components-"+componentResult);
			}
			basicComponentresult=String.valueOf(feedhelper.jsonKeyValidationsForComponents(basicComponentType, apiVersion, cartResponses[i],0,tests));
//			FeedCards filename = FeedCards.getFeedCard(card_type);

			 String jsonSchemaRetrieved = feedhelper.getComponentCardSchema("genericCard.txt", apiVersion);
			 result=String.valueOf(feedhelper.componentCardSchemaValidation(response, jsonSchemaRetrieved, apiVersion));
			 
			individualCardResults.add("object_id <=========> "+objectId);
			individualCardResults.add(card_type+"SchemaValidation-"+result);
			individualCardResults.add(card_type+basicComponentType+"Validation-"+basicComponentresult);

			for(int j=0;j<components.size();j++)
			{
				String type =JsonPath.read(components.get(j), "$.type");
				componentResult=String.valueOf(feedhelper.jsonKeyValidationsForComponents(type, apiVersion, cartResponses[i],j,tests));
				individualComponentResults.add("object_id <=========> "+objectId);
				individualComponentResults.add(type+"ComponentsKeysValidation-"+componentResult);
			}
		}
		cnt++;
		validationDetails.put("cardValidationResult", individualCardResults);
		validationDetails.put("componentValidationResult", individualComponentResults);
		validationDetails.put("TotalCntofCards", String.valueOf(cartResponses.length));
		
		System.out.println(individualCardResults);
		System.out.println(individualComponentResults);
		System.out.println(validationDetails);
		return validationDetails;
	}

	public String[] getIndividualCardResponses(String response, int count) {
		// TODO Auto-generated method stub
		String[] cardResponses = new String[count];
		for(int i=0;i<count;i++)
		{
			cardResponses[i]=JsonPath.read(response, "$.cards["+i+"]").toString();
		}
		return cardResponses;
	}
	
	public HashMap<String, Object> validateOnBoardingCardsIndividually(String response,String apiVersion,String tests) throws IOException
	{
		cnt=0;
		String result ="false";
		String componentResult="false";
		String basicComponentresult="false";
		String componentKeysResult="false";
		String titleTextResult="false";
		HashMap<String, Object> validationDetails = new HashMap<>();
		ArrayList<String> individualCardResults = new ArrayList<>();
		ArrayList<String> individualComponentResults = new ArrayList<>();
		int count = Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
		String[] cartResponses = getIndividualCardResponses(response,count);
		for(int i=0;i<cartResponses.length;i++)
		{
			String card_type=JsonPath.read(cartResponses[i], "$.props.meta.og:name");
			String basicComponentType=JsonPath.read(cartResponses[i], "$.type");
			JSONArray components=JsonPath.read(cartResponses[i], "$.children");
			System.out.println(components.size());
			componentResult=String.valueOf(feedhelper.componentValidationsForOnBoarding(card_type,apiVersion,cartResponses[i]));
			basicComponentresult=String.valueOf(feedhelper.jsonKeyValidationsForComponents(basicComponentType, apiVersion, cartResponses[i],0,tests));
			 
			 String jsonSchemaRetrieved = feedhelper.getComponentCardSchema("genericCard.txt", apiVersion);
			 result=String.valueOf(feedhelper.componentCardSchemaValidation(response, jsonSchemaRetrieved, apiVersion));
			
			individualCardResults.add(card_type+" SchemaValidation-"+result);
			individualCardResults.add(card_type+" BASE Component "+basicComponentType+"-"+basicComponentresult);
			individualCardResults.add(card_type+" Components-"+componentResult);
			
			for(int j=0;j<components.size();j++)
			{
				String type =JsonPath.read(components.get(j), "$.type");
				componentKeysResult=String.valueOf(feedhelper.jsonKeyValidationsForComponents(type, apiVersion, cartResponses[i],j,tests));
				individualComponentResults.add(type+"ComponentsKeysValidation-"+componentKeysResult);
				if(card_type.equals("onboard-d0-welback")||card_type.equals("onboard-d0-welcome"))
				{
					String title =JsonPath.read(cartResponses[i], "$..children["+j+"].props.title");
					titleTextResult=String.valueOf(feedhelper.checkTitleTextResult(title));
					individualCardResults.add(card_type+ " TITLETEXT Validation-"+ titleTextResult);
				}
			}
		}
		cnt++;
		validationDetails.put("cardValidationResult", individualCardResults);
		validationDetails.put("componentValidationResult", individualComponentResults);
		validationDetails.put("TotalCntofCards", String.valueOf(cartResponses.length));
		
		System.out.println(individualCardResults);
		System.out.println(individualComponentResults);
		System.out.println(validationDetails);
		return validationDetails;
	}
}
