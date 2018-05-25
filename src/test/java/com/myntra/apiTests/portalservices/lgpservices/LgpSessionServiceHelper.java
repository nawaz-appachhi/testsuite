package com.myntra.apiTests.portalservices.lgpservices;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import com.myntra.apiTests.common.Myntra;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.client.ClientConfig;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.apiTests.common.ServiceType;

public class LgpSessionServiceHelper{
	
	public static Initialize init = new Initialize("/Data/configuration");
	String env = init.Configurations.GetTestEnvironemnt().toString();
	

	
	private String getTimeStampWithInterval(String intervalWithDifference){
		
		String timestampValueInString = null;
		if(intervalWithDifference.equals("case3B")){
			
			long timestampValue = (System.currentTimeMillis()/1000)+(660);
			
			timestampValueInString = String.valueOf(timestampValue);
			System.out.println("Timestamp Value : "+timestampValueInString);
			
		}
		
		else if(intervalWithDifference.equals("case4B")){
			
			long timestampValue = (System.currentTimeMillis()/1000)-(660);
			
			timestampValueInString = String.valueOf(timestampValue);
			System.out.println("Timestamp Value : "+timestampValueInString);
			
		}
		return timestampValueInString;
		
		
	}
	
	public  String xmlParseForData(String xmlString, String requiredTagValue) throws ParserConfigurationException, SAXException, IOException{
		String dataNodeValue ="";
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlString));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("session");

			// iterate
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				NodeList name = element.getElementsByTagName(requiredTagValue);
				Element line = (Element) name.item(0);
				dataNodeValue = getCharacterDataFromElement(line);
				//System.out.println("data: " + getCharacterDataFromElement(line));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return dataNodeValue;
		
	}

	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "?";
	}
	
	public Response executeRequest(String uri, String payload, String requestType)
	{
		
		ClientConfig config = new ClientConfig();
        javax.ws.rs.client.Client client = ClientBuilder.newClient(config);
		WebTarget webTarget = client.target(uri);
		Response responseInfo = null;
		if(requestType.equalsIgnoreCase("post")){
			
			responseInfo = webTarget.request().header(HttpHeaders.ACCEPT, "application/xml").header(HttpHeaders.CONTENT_TYPE, "application/xml").post(Entity.xml(payload));
			
		}
		
		else if(requestType.equalsIgnoreCase("put")){
			
			 responseInfo = webTarget.request().header(HttpHeaders.ACCEPT, "application/xml").header(HttpHeaders.CONTENT_TYPE, "application/xml").put(Entity.xml(payload));
		}
		
		return responseInfo;
		
	}
	
	public String readFileAsString(String filePath) throws IOException
	{
		
		// StringBuffer fileData = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		/*
		 * char[] buf = new char[1024]; int numRead=0;
		 * while((numRead=reader.read(buf)) != -1){ String readData =
		 * String.valueOf(buf, 0, numRead); fileData.append(readData); }
		 * reader.close();
		 */

		// log.info("File content is : "+IOUtils.toString(reader));
		return IOUtils.toString(reader);
	}
	
	public String createJson(String caseValue){
		JsonObject sessionPayload = null;
		Gson gson = null;
		if(caseValue.equalsIgnoreCase("case1")){
			
			sessionPayload = new JsonObject();
			sessionPayload.addProperty("key", "gotTheKey");
			
			JsonObject sessionPayloadChild1 = new JsonObject();
			sessionPayloadChild1.addProperty("show", "better");
			sessionPayloadChild1.addProperty("time", "today");
			
			JsonArray sessionPayloadChild2 = new JsonArray();
			
			JsonObject sessionPayloadChild2ArrayChild1 = new JsonObject();
			sessionPayloadChild2ArrayChild1.addProperty("got", "it");
			
			JsonObject sessionPayloadChild2ArrayChild2 = new JsonObject();
			sessionPayloadChild2ArrayChild2.addProperty("do", "not");
			
			sessionPayloadChild2.add(sessionPayloadChild2ArrayChild1);
			sessionPayloadChild2.add(sessionPayloadChild2ArrayChild2);
			
			sessionPayload.add("dooep", sessionPayloadChild1);
			sessionPayload.add("newDump", sessionPayloadChild2);
			
			gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
			
		}
		else if (caseValue.contains("case2")){
			
			
			sessionPayload = new JsonObject();
			sessionPayload.addProperty("key", "gotTheKey");
			sessionPayload.addProperty("expiryTime", getTimeStampWithInterval("case4B"));
			gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
		}
		
		else if(caseValue.contains("case3")){
			
			if(caseValue.equalsIgnoreCase("case3A")){
				
				sessionPayload = new JsonObject();
				sessionPayload.addProperty("key", "gotTheKey");
				gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
				
			}
			else if(caseValue.equalsIgnoreCase("case3B")){
				
				
				return getTimeStampWithInterval("case3B");
				
				
			}
			
		}
		
		else if(caseValue.contains("case4")){
			
			if(caseValue.equalsIgnoreCase("case4A")){
				
				sessionPayload = new JsonObject();
				sessionPayload.addProperty("key", "gotTheKey");
				gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
				
			}
			else if(caseValue.equalsIgnoreCase("case4B")){
				
				
				return getTimeStampWithInterval("case4B");
				
				
			}
			
		}
		
		else if(caseValue.contains("case8")){
			
			sessionPayload = new JsonObject();
			sessionPayload.addProperty("$$$%%%", "^^^&&&");
			gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
			
		}
		
		else if(caseValue.contains("case9")){
			
			sessionPayload = new JsonObject();
			sessionPayload.addProperty("	", "	");
			gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
			
		}
		
		else if(caseValue.contains("case10")){
			
			sessionPayload = new JsonObject();
			sessionPayload.addProperty("	key", "		gotTheKey");
			gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
			
		}
		
		else if(caseValue.contains("case11")){
			
			sessionPayload = new JsonObject();
			sessionPayload.addProperty("key		", "gotTheKey		");
			gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
			
		}
		
		return gson.toJson(sessionPayload).toString().trim();
	}
	
	
	
	public MyntraService getQueryRequestSingleParam(ServiceType serviceType ,APINAME apiName, String param){
		
		
		MyntraService service = Myntra.getService(serviceType, apiName, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		service.URL = utilities.prepareparameterizedURL(service.URL, param);
		return service;
		
	}

}
