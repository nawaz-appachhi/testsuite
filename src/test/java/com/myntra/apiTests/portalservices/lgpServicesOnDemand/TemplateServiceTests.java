package com.myntra.apiTests.portalservices.lgpServicesOnDemand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.lgpservices.LgpPumpsHelper;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.TemplateServiceDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class TemplateServiceTests extends TemplateServiceDP {

	public static Initialize init = new Initialize("/Data/configuration");
	static Logger logger = Logger.getLogger(TemplateServiceTests.class);
	static APIUtilities utilities = new APIUtilities();

	
	
		@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
				dataProvider = "createTemplateDP")
  public void createTemplate(HashMap<String,String> reqParms,HashMap<String,String> resParms)throws IOException,InterruptedException {
	  String payloadFile=utilities.readFileAsString("./Data/payloads/JSON/lgp/Template/create");
		System.out.println(reqParms);
		if(reqParms.containsKey("tgid") && reqParms.containsKey("tags") && reqParms.containsKey("appid") && reqParms.containsKey("template"))
		payloadFile=utilities.preparepayload(payloadFile,new String[] {reqParms.get("tgid"),reqParms.get("tags"),reqParms.get("appid"),reqParms.get("template")});
		else{
			Set<String> keys = new HashSet<String>();
			keys = reqParms.keySet();
			payloadFile = "{\n";
			for(String key : keys){
				if(! key.equalsIgnoreCase("TC"))
				payloadFile = payloadFile+"\""+key+"\":"+reqParms.get(key)+",\n";
			}
			StringBuffer buffer = new StringBuffer(payloadFile);
			String payloadReverse = buffer.reverse().toString().replaceFirst(",", "");
			payloadFile = new StringBuffer(payloadReverse).reverse().toString();
			payloadFile = payloadFile+"}";
		}
		MyntraService service = Myntra.getService(ServiceType.PORTAL_TEMPLATE,
				APINAME.CREATETEMPLATE, init.Configurations,payloadFile);
		System.out.println("\nService URL---->"+service.URL);
		RequestGenerator request =  new RequestGenerator(service);
		String resp = request.respvalidate.returnresponseasstring();
		System.out.println("Response-------------\n:"+resp);
		AssertJUnit.assertEquals("Status not equal to "+resParms.get("status"), Integer.parseInt(resParms.get("status")),request.response.getStatus());
		AssertJUnit.assertTrue("status message is not equal to "+resParms.get("statusMessage"), resParms.get("statusMessage").equalsIgnoreCase(JsonPath.read(resp,"$.status.statusMessage").toString()));
		AssertJUnit.assertTrue("Status Type is not equal to "+resParms.get("statusType"),resParms.get("statusType").equalsIgnoreCase(JsonPath.read(resp, "$.status.statusType").toString()) );
		if(resParms.get("statusType").equalsIgnoreCase("Success")){
			AssertJUnit.assertEquals("tgId is not equal to "+reqParms.get("tgid").replace("\"", "").replaceAll("\\s", ""),reqParms.get("tgid").toLowerCase().replace("\"", "").replaceAll("\\s", ""),JsonPath.read(resp, "$.data.tgId").toString().toLowerCase() );
			//AssertJUnit.assertEquals("tags is/are not equal to "+reqParms.get("tags").replaceAll("\\s", ""),reqParms.get("tags").toLowerCase().replaceAll("\\s", ""),JsonPath.read(resp, "$.data.tags").toString().toLowerCase() );
			ArrayList<String> tagsExpected = new ArrayList<String>();
			String[] tagsFromRequest = reqParms.get("tags").toLowerCase().replaceAll("\\s", "").split(",");
			for(String tags : tagsFromRequest)
				tagsExpected.add(tags);
			ArrayList<String> tagsActual = new ArrayList<String>();
			String[] tagsFromResp = reqParms.get("tags").toLowerCase().replaceAll("\\s", "").split(",");
			for(String tags : tagsFromResp)
				tagsActual.add(tags);
			for(String tags : tagsExpected)
				AssertJUnit.assertTrue("Tag "+tags+" not found",tagsActual.contains(tags));
			AssertJUnit.assertEquals("appId is not equal to "+reqParms.get("appid").replaceAll("\\s", ""),reqParms.get("appid").replaceAll("\\s", ""),JsonPath.read(resp, "$.data.appId").toString() );
			AssertJUnit.assertEquals("template is not equal to "+reqParms.get("template").replace("\\\"", "\""),reqParms.get("template").toLowerCase().replace("\\\"", "\""),"\""+JsonPath.read(resp, "$.data.template").toString().toLowerCase()+"\"" );
			AssertJUnit.assertEquals("version is not equal to "+resParms.get("version"),resParms.get("version"),JsonPath.read(resp, "$.data.version").toString() );
		}
	}


	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "deleteTemplateDP")
	public void deleteTemplate(HashMap<String,String> reqParms,HashMap<String,String> resParms)throws IOException,InterruptedException {
		String payloadFile=utilities.readFileAsString("./Data/payloads/JSON/lgp/Template/delete");
		System.out.println(reqParms);
		if(reqParms.containsKey("tgid") && reqParms.containsKey("tags") && reqParms.containsKey("appid") && reqParms.containsKey("template") && reqParms.containsKey("version"))
		payloadFile=utilities.preparepayload(payloadFile,new String[] {reqParms.get("tgid"),reqParms.get("version"),reqParms.get("tags"),reqParms.get("appid"),reqParms.get("template")});
		else{
			Set<String> keys = new HashSet<String>();
			keys = reqParms.keySet();
			payloadFile = "{\n";
			for(String key : keys){
				if(! key.equalsIgnoreCase("TC"))
				payloadFile = payloadFile+"\""+key+"\":"+reqParms.get(key)+",\n";
			}
			StringBuffer buffer = new StringBuffer(payloadFile);
			String payloadReverse = buffer.reverse().toString().replaceFirst(",", "");
			payloadFile = new StringBuffer(payloadReverse).reverse().toString();
			payloadFile = payloadFile+"}";
		}
		MyntraService service = Myntra.getService(ServiceType.PORTAL_TEMPLATE,
				APINAME.DELETETEMPLATE, init.Configurations,payloadFile);
		RequestGenerator request =  new RequestGenerator(service);
		System.out.println("\nService URL---->"+service.URL);
		String resp = request.respvalidate.returnresponseasstring();
		System.out.println("Response-------------\n:"+resp);
		AssertJUnit.assertEquals("Status not equal to "+resParms.get("status"), Integer.parseInt(resParms.get("status")),request.response.getStatus());
		AssertJUnit.assertTrue("status message is not equal to "+resParms.get("statusMessage"), resParms.get("statusMessage").equalsIgnoreCase(JsonPath.read(resp,"$.status.statusMessage").toString()));
		AssertJUnit.assertTrue("Status Type is not equal to "+resParms.get("statusType"),resParms.get("statusType").equalsIgnoreCase(JsonPath.read(resp, "$.status.statusType").toString()) );
		if((resParms.get("statusType").equalsIgnoreCase("Success")) && resParms.get("statusMessage").equalsIgnoreCase("NO_RESULT_FOUND") ){
			AssertJUnit.assertEquals("entriesMatched is not 0","0",JsonPath.read(resp, "$.status.entriesMatched").toString() );
			AssertJUnit.assertEquals("entriesUpdated is not 0 ","0",JsonPath.read(resp, "$.status.entriesUpdated").toString() );
		}
		else if((resParms.get("statusType").equalsIgnoreCase("Success")) && resParms.get("statusMessage").equalsIgnoreCase("Template Deleted Successfully") ){
			AssertJUnit.assertTrue("entriesMatched is not greater than or equal to 1",Integer.parseInt(JsonPath.read(resp, "$.status.entriesMatched").toString()) >= 1 );
			AssertJUnit.assertTrue("entriesUpdated is not gretaer than or equal to 1 ",Integer.parseInt(JsonPath.read(resp, "$.status.entriesUpdated").toString()) >= 1);
		}
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "getTemplateDP")
	public void getTemplate(HashMap<String,String> reqParms,HashMap<String,String> resParms)throws IOException,InterruptedException {
		String payloadFile=utilities.readFileAsString("./Data/payloads/JSON/lgp/Template/get");
		System.out.println(reqParms);
		if(reqParms.containsKey("tgid") && reqParms.containsKey("tags") && reqParms.containsKey("appid") && reqParms.containsKey("template") && reqParms.containsKey("version"))
		payloadFile=utilities.preparepayload(payloadFile,new String[] {reqParms.get("tgid"),reqParms.get("version"),reqParms.get("tags"),reqParms.get("appid"),reqParms.get("template")});
		else{
			Set<String> keys = new HashSet<String>();
			keys = reqParms.keySet();
			payloadFile = "{\n";
			for(String key : keys){
				if(! key.equalsIgnoreCase("TC"))
				payloadFile = payloadFile+"\""+key+"\":"+reqParms.get(key)+",\n";
			}
			StringBuffer buffer = new StringBuffer(payloadFile);
			String payloadReverse = buffer.reverse().toString().replaceFirst(",", "");
			payloadFile = new StringBuffer(payloadReverse).reverse().toString();
			payloadFile = payloadFile+"}";
		}
		MyntraService service = Myntra.getService(ServiceType.PORTAL_TEMPLATE,
				APINAME.GETTEMPLATE, init.Configurations,payloadFile);
		RequestGenerator request =  new RequestGenerator(service);
		System.out.println("\nService URL---->"+service.URL);
		String resp = request.respvalidate.returnresponseasstring();
		System.out.println("Response-------------\n:"+resp);
		AssertJUnit.assertEquals("Status not equal to "+resParms.get("status"), Integer.parseInt(resParms.get("status")),request.response.getStatus());
		AssertJUnit.assertTrue("status message is not equal to "+resParms.get("statusMessage"), resParms.get("statusMessage").equalsIgnoreCase(JsonPath.read(resp,"$.status.statusMessage").toString()));
		AssertJUnit.assertTrue("Status Type is not equal to "+resParms.get("statusType"),resParms.get("statusType").equalsIgnoreCase(JsonPath.read(resp, "$.status.statusType").toString()) );
		if(resParms.get("statusType").equalsIgnoreCase("Success") && ! resParms.get("statusMessage").equalsIgnoreCase("NO_RESULT_FOUND") ){
			AssertJUnit.assertEquals("tgId is not equal to "+reqParms.get("tgid"),reqParms.get("tgid").toString().toLowerCase().replace("\"", "").replaceAll("\\s", ""),JsonPath.read(resp, "$.data.tgId").toString().toLowerCase());
			//AssertJUnit.assertEquals("tags is/are not equal to "+reqParms.get("tags"),reqParms.get("tags"),JsonPath.read(resp, "$.data.tags").toString() );
			ArrayList<String> tagsExpected = new ArrayList<String>();
			String[] tagsFromRequest = reqParms.get("tags").toLowerCase().replaceAll("\\s", "").split(",");
			for(String tags : tagsFromRequest)
				tagsExpected.add(tags);
			ArrayList<String> tagsActual = new ArrayList<String>();
			String[] tagsFromResp = reqParms.get("tags").toLowerCase().replaceAll("\\s", "").split(",");
			for(String tags : tagsFromResp)
				tagsActual.add(tags);
			for(String tags : tagsExpected)
				AssertJUnit.assertTrue("Tag "+tags+" not found",tagsActual.contains(tags));
			AssertJUnit.assertEquals("appId is not equal to "+reqParms.get("appid").replaceAll("\\s", ""),reqParms.get("appid").replaceAll("\\s", ""),JsonPath.read(resp, "$.data.appId").toString() );
			AssertJUnit.assertEquals("template is not equal to "+reqParms.get("template").replace("\\\"", "\""),reqParms.get("template").toLowerCase().replace("\\\"", "\""),"\""+JsonPath.read(resp, "$.data.template").toString().toLowerCase()+"\"" );
			AssertJUnit.assertEquals("version is not equal to "+resParms.get("version"),resParms.get("version"),JsonPath.read(resp, "$.data.version").toString() );
		}
	}
	
	@Test
	public void createAndGetTemplate() throws IOException, InterruptedException{
		HashMap<String,String> input = new HashMap<String,String>();
		HashMap<String,String> output = new HashMap<String,String>();
		Long time = new LgpPumpsHelper().getcurrentTimestamp();
		  input.put("tgid", "\"AutoTgid"+time+"\"");
		  input.put("tags","[\"push\",\"ios\",\"android\",\"notification\"]");
		  input.put("appid","10");
		  input.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
		  input.put("version","1");
		  output.put("status","200");
		  output.put("statusMessage","Template Stored Successfully");
		  output.put("statusType", "Success");
		  output.put("version","1");
		  createTemplate(input, output);
		  output.put("statusMessage","Success");
		  getTemplate(input, output);
	}
	
	@Test
	public void createMultipleAndGetTemplate() throws IOException, InterruptedException{
		HashMap<String,String> input = new HashMap<String,String>();
		HashMap<String,String> output = new HashMap<String,String>();
		int version = 1;
		Long time = new LgpPumpsHelper().getcurrentTimestamp();
		  input.put("tgid", "\"AutoTgid"+time+"\"");
		  input.put("tags","[\"push\",\"ios\",\"android\",\"notification\"]");
		  input.put("appid","10");
		  input.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
		  input.put("version",""+version);
		  output.put("status","200");
		  output.put("statusMessage","Template Stored Successfully");
		  output.put("statusType", "Success");
		  output.put("version",""+version);
		  createTemplate(input, output);
		  version += 1;
		  input.put("version",""+version);
		  output.put("version",""+version);
		  createTemplate(input, output);
		  output.put("statusMessage","Success");
		  getTemplate(input, output);
	}
	
	@Test
	public void deleteAndGetTemplate() throws IOException, InterruptedException{
		HashMap<String,String> input = new HashMap<String,String>();
		HashMap<String,String> output = new HashMap<String,String>();
		int version = 1;
		Long time = new LgpPumpsHelper().getcurrentTimestamp();
		  input.put("tgid", "\"AutoTgid"+time+"\"");
		  input.put("tags","[\"push\",\"ios\",\"android\",\"notification\"]");
		  input.put("appid","10");
		  input.put("template",  "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
		  input.put("version",""+version);
		  output.put("status","200");
		  output.put("statusMessage","Template Stored Successfully");
		  output.put("statusType", "Success");
		  output.put("version",""+version);
		  createTemplate(input, output);
		  output.put("statusMessage","Template Deleted Successfully");
		  deleteTemplate(input, output);
		  output.put("statusMessage","NO_RESULT_FOUND");
		  getTemplate(input, output);
	}
	
	@Test
	public void deleteAlreadyDeletedTemplate() throws IOException, InterruptedException{
		HashMap<String,String> input = new HashMap<String,String>();
		HashMap<String,String> output = new HashMap<String,String>();
		int version = 1;
		Long time = new LgpPumpsHelper().getcurrentTimestamp();
		  input.put("tgid", "\"AutoTgid"+time+"\"");
		  input.put("tags","[\"push\",\"ios\",\"android\",\"notification\"]");
		  input.put("appid","10");
		  input.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
		  input.put("version",""+version);
		  output.put("status","200");
		  output.put("statusMessage","Template Stored Successfully");
		  output.put("statusType", "Success");
		  output.put("version",""+version);
		  createTemplate(input, output);
		  output.put("statusMessage","Template Deleted Successfully");
		  deleteTemplate(input, output);
		  output.put("statusMessage","NO_RESULT_FOUND");
		  deleteTemplate(input, output);
	}
}

  
