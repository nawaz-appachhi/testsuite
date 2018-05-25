package com.myntra.apiTests.portalservices.commons;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingMessage;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.util.JsonLoader;
import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * @author shankara.c
 *
 */
public class CommonUtils 
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(CommonUtils.class);
	static StringBuilder oosString=new StringBuilder();




	/**
	 * This method is used to invoke SearchService getQuery API
	 * 
	 * @param query
	 * @param returnDocs
	 * @param isFacet
	 * @param login
	 * @return collection of styleids for the product
	 */
	public List<Integer> performSeachServiceToGetStyleIds(String query, String returnDocs, String isFacet, String login)
	{
		MyntraService searchService = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY, init.Configurations,
				new String[] { query, returnDocs, isFacet, login });
		//System.out.println("\nPrinting SeachService getQuery API URL : "+searchService.URL);
		log.info("\nPrinting SeachService getQuery API URL : "+searchService.URL);

		//System.out.println("\nPrinting SeachService getQuery API payload : \n\n"+searchService.Payload);
		log.info("\nPrinting SeachService getQuery API payload Payload : \n\n"+searchService.Payload);

		RequestGenerator searchServiceReqGen = new RequestGenerator(searchService);
		//System.out.println("\nPrinting SeachService getQuery API response : \n\n"+searchServiceReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting SeachService getQuery API response :  \n\n"+searchServiceReqGen.respvalidate.returnresponseasstring());

		//String resp = JsonPath.read(searchServiceReqGen.respvalidate.returnresponseasstring(), "$.data.queryResult").toString();
		String resp = searchServiceReqGen.respvalidate.returnresponseasstring();
		
		//return JsonPath.read(resp, "$.response1..products[*].styleid");
		 return JsonPath.read(resp, "$.data.queryResult.response1..products[*].styleid");
		
	}

	/**
	 * This method is used to invoke StyleService getStyleData API
	 * 
	 * @param styleId
	 * @return RequestGenerator
	 */
	public RequestGenerator performGetStyleDataOperation(String styleId)
	{
		MyntraService getStyleDataService = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.GETSTYLEDATAGET, init.Configurations);
		getStyleDataService.URL = new APIUtilities().prepareparameterizedURL(getStyleDataService.URL, String.valueOf(styleId));
		System.out.println("\nPrinting StyleService getStyleData API URL : "+getStyleDataService.URL);
		log.info("\nPrinting StyleService getStyleData API URL : "+getStyleDataService.URL);

		return new RequestGenerator(getStyleDataService);
	}

	/**
	 * This method is used to get the list of skuIds using styleId
	 * 
	 * @param styleId
	 * @return list of skuIds
	 */
	public List<Integer> getSkuIds(String styleId)
	{
		try
		{
			List<Integer> validSkuList = new ArrayList<Integer>();

			RequestGenerator getStyleDataReqGen = performGetStyleDataOperation(styleId);
			String getStyleDataResponse = getStyleDataReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataResponse);
			log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataResponse);	

			List<Integer> skuIds = JsonPath.read(getStyleDataResponse, "$.data..styleOptions[*].skuId");

			for(int count = 0 ; count < skuIds.size(); count++)
			{
				String skuId = JsonPath.read(getStyleDataResponse, "$.data..styleOptions["+count+"].skuId").toString();
				String inventoryCount = JsonPath.read(getStyleDataResponse, "$.data..styleOptions["+count+"].inventoryCount").toString();
				String available = JsonPath.read(getStyleDataResponse, "$.data..styleOptions["+count+"].available").toString();

				if(!inventoryCount.equals(IServiceConstants.ZERO_VALUE) && available.equals(IServiceConstants.TRUE_VALUE))
				{
					validSkuList.add(Integer.parseInt(skuId));

				} else {
					System.out.println("\nThe skuId: "+skuId+" for the styleId: "+styleId+" is out of stock.\n");
					log.info("\nThe skuId: "+skuId+" for the styleId: "+styleId+" is out of stock.\n");
					oosString.append(skuId+",");
					System.out.println("Out of Stock Style="+oosString.toString());
				}
			}

			return validSkuList;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * This method is used to get the list of skuIds using styleId
	 * 
	 * @param List of StyleId
	 * @return list of skuIds
	 */
	public List<Integer> getSkuIds(List<Integer> styleId)
	{

		List<Integer> validSkuList = new ArrayList<Integer>();
		for(int i=0;i<styleId.size();i++){
			RequestGenerator getStyleDataReqGen = performGetStyleDataOperation(styleId.get(i).toString());
			String getStyleDataResponse = getStyleDataReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataResponse);
			log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataResponse);	

			List<Integer> skuIds = JsonPath.read(getStyleDataResponse, "$.data..styleOptions[*].skuId");

			for(int count = 0 ; count < skuIds.size(); count++)
			{
				String skuId = JsonPath.read(getStyleDataResponse, "$.data..styleOptions["+count+"].skuId").toString();
				String inventoryCount = JsonPath.read(getStyleDataResponse, "$.data..styleOptions["+count+"].inventoryCount").toString();
				String available = JsonPath.read(getStyleDataResponse, "$.data..styleOptions["+count+"].available").toString();

				if(!inventoryCount.equals(IServiceConstants.ZERO_VALUE) && available.equals(IServiceConstants.TRUE_VALUE))
				{
					validSkuList.add(Integer.parseInt(skuId));
					i=styleId.size();
					break;

				} else {
					System.out.println("\nThe skuId: "+skuId+" for the styleId: "+styleId+" is out of stock.\n");
					log.info("\nThe skuId: "+skuId+" for the styleId: "+styleId+" is out of stock.\n");
				}
			}

		}
		return validSkuList;
	}


	public static boolean isArraySortedInAscendingOrder(List<Integer> sortedDiscountedPriceList) 
	{
		for (int i = 0; i < sortedDiscountedPriceList.size()-1; i++) {
			if(sortedDiscountedPriceList.get(i).compareTo(sortedDiscountedPriceList.get(i+1))> 0){
				return false;
			}
		}

		return true;
	}

	public static boolean isArraySortedInDescendingOrder(List<Integer> sortedDiscountedPriceList) 
	{
		for (int i = 0; i < sortedDiscountedPriceList.size()-1; i++) {
			if(sortedDiscountedPriceList.get(i+1).compareTo(sortedDiscountedPriceList.get(i))> 0){
				return false;
			}
		}

		return true;
	}

	public List<String> validateServiceResponseNodesUsingSchemaValidator(String jsonData, String jsonSchema) throws Exception
	{
		// create the Json nodes for schema and data
		ProcessingReport report = null;
		ProcessingMessage message = null;
		List<String> missingNodeList = new ArrayList<String>();

		JsonNode schemaNode = JsonLoader.fromString(jsonSchema); // throws JsonProcessingException if error
		JsonNode data = JsonLoader.fromString(jsonData); // same here

		JsonSchemaFactory factory = JsonSchemaFactory.byDefault();

		// load the schema and validate
		JsonSchema schema = factory.getJsonSchema(schemaNode);
		report = schema.validate(data);

		System.out.println("\nProcessing Report = " + report.toString());
		log.info("\nProcessing Report = " + report.toString());

		Iterator<ProcessingMessage> itr = report.iterator();

		while(itr.hasNext())
		{
			message =itr.next();
			if(null != message && null != message.asJson()){
				missingNodeList.add(String.valueOf(JsonPath.read(String.valueOf(message.asJson()), "$.instance.pointer"))+"/"+
						String.valueOf(JsonPath.read(String.valueOf(message.asJson()), "$.missing")));
				System.out.println("\nProcessing Message = "+message+"\n");
				log.info("\nProcessing Message = "+message+"\n");
			}
		}

		return missingNodeList;
	}

	public boolean validateJsonSchema(String jsonData, String jsonSchema) {
		ProcessingReport report = null;
		boolean result = false;
		try { 
			// System.out.println("Applying schema:\n"+jsonSchema+"\n to data:\n"+jsonData+" ");
			JsonNode schemaNode = JsonLoader.fromString(jsonSchema);
			JsonNode data = JsonLoader.fromString(jsonData);         
			JsonSchemaFactory factory = JsonSchemaFactory.byDefault(); 
			JsonSchema schema = factory.getJsonSchema(schemaNode);
			report = schema.validate(data);
		} catch (JsonParseException jpex) {
			System.out.println("Error. Something went wrong trying to parse json data: "+jsonData+
					"or json schema: "+jsonSchema+" Are the double quotes included? "+jpex.getMessage());
			jpex.printStackTrace(); 
		} catch (ProcessingException pex) {  
			System.out.println("Error. Something went wrong trying to process json data: "+jsonData+
					" with json schema: "+jsonSchema+">  "+pex.getMessage());
			pex.printStackTrace(); 
		} catch (IOException e) {
			System.out
					.println("Error. Something went wrong trying to read json data: "+jsonData+
					" or json schema: "+jsonSchema+"");
			e.printStackTrace(); 
		} 
		if (report != null) {
			Iterator<ProcessingMessage> iter = report.iterator();
			while (iter.hasNext()) {
				ProcessingMessage pm = iter.next();
				System.out.println("Processing Message: "+pm.getMessage());
			} 
			result = report.isSuccess();
		} 

		return result;
	} 
}

