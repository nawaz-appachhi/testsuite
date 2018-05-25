package com.myntra.apiTests.portalservices.lgpServicesOnDemand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.lgpservices.LgpServicesHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.ABTestVariantsDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

import edu.emory.mathcs.backport.java.util.Arrays;

public class ABTestVariant extends ABTestVariantsDP{
	
	public static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(ABTestVariant.class);
	static LgpServicesHelper lgpServiceHelper = new LgpServicesHelper();
	static APIUtilities utilities = new APIUtilities();

	private String username;
	private String password;
	private String variant;
	private String nextURL;
	private String previousURL;
	static String uidx;
	public static String versionSpecification;
	public static HashMap<String, String> headers=new HashMap<String, String>();
	private Map<String,Integer> targetCardIdFrequency = new HashMap<>();
	
	@BeforeClass(alwaysRun = true)
	public void signInAndGetUidx()
	{
		username=System.getenv("username");
		password=System.getenv("password");
		versionSpecification=System.getenv("API_VERSION");
		if(username!=null && password!=null)
		{
			try {
				uidx=lgpServiceHelper.getXidandUidxForCredential(username, password).get("uidx");
				System.out.println(uidx);
				headers.put("uidx", uidx);
				System.out.println("Username : "+username);
				System.out.println("Password : "+password);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(null==versionSpecification)
		{
		 versionSpecification = "v2.7";
		}
	}
	

  @Test(dataProvider="abConfigurationsDP")
  public void testConfigurations(Map<String, Object> configValues) throws IOException {
	  System.out.println(configValues);
		MyntraService streamService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETSTREAM,init.Configurations);
		streamService.URL=utilities.prepareparameterizedURL(streamService.URL, new String[]{versionSpecification});
		RequestGenerator feedreq;
		System.out.println(streamService.URL);
		if(configValues.containsKey("variant")){
			if(configValues.get("variant")==null){
				headers.put("x-myntra-abtest", null);
			}else
				headers.put("x-myntra-abtest", configValues.get("variant").toString());
		}
		if(configValues.containsKey("platform")){
			if(configValues.get("platform")==null)
				headers.put("platform", null);
			else
				headers.put("platform", configValues.get("platform").toString());
		}
		
		if(!headers.containsKey("uidx")){
				headers.put("uidx", "0679f08a.15be.41b3.bfdf.9447d27cbf53PEZCRkqADj");
		}
		if(configValues.containsKey("uidx")){
			if((configValues.get("uidx") instanceof Boolean) && ((Boolean)configValues.get("uidx")==false)){
				headers.remove("uidx");
			}else{
				if(configValues.get("uidx")==null)
					headers.put("uidx", null);
				else
					headers.put("uidx", configValues.get("uidx").toString());
			}
		}
		feedreq = new RequestGenerator(streamService,headers);
		
		String response = feedreq.returnresponseasstring();
		System.out.println(response);
		log.info(response);
		
		ArrayList<String> cardOrderInResponse=new ArrayList<>();
		Map<String, Integer> cardCountInResponse=new HashMap<>();
		getCardOrderAndCardCounts(response, cardOrderInResponse, cardCountInResponse);
		
		ArrayList<String> failures= new ArrayList<>();
		validateStreamResponse(cardOrderInResponse, cardCountInResponse, configValues, failures,"[stream]");
		
		try{
			previousURL=(String)JsonPath.read(response, "$.page.previous");
		}catch(Exception e){
			previousURL=null;
		}
		//Validate history call
		for(int i=0; i<5; i++){
			if(previousURL==null){
				failures.add("previousURL is null");
			}else if(!previousURL.isEmpty()){
				validateHistoryCall(previousURL, configValues, failures);
			}
		}
		try{
			nextURL =(String) JsonPath.read(response, "$.page.next");
		}catch(Exception e){
			nextURL=null;
		}
		
		//Validate more call
		for(int i=0; i<5; i++){
			if(nextURL==null){
				failures.add("nextUrl is null");
			}else if(!nextURL.isEmpty()){
				validateMoreCall(nextURL, configValues, failures);
			}
		}
		
		//Validate slideshow call
		validateSlideShowCall(versionSpecification, configValues, failures);
		
		//Validate update call
		//validateUpdateCall(versionSpecification, configValues, failures);
		
		//Check for target card occurrences
		if(configValues.get("targeted")!=null && configValues.get("targeted").equals("once")){
			for(Map.Entry<String, Integer> entry:targetCardIdFrequency.entrySet()){
				if(entry.getValue()>1){
					failures.add("targeted once: Targeted card id "+entry.getKey()+" has occurred more than once : "+entry.getKey());
				}
			}
		}
		
		if(failures.size()>0){
			Assert.fail(getFailuresPoints(failures));
		}
		
		
	}
  
  
//
//  @Test(dataProvider="abFixedDP")
//  public void testFixedConfiguration(Map<String, Object> configValues) throws IOException {
//	  System.out.println(configValues);
//		MyntraService streamService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETSTREAM,init.Configurations);
//		streamService.URL=utilities.prepareparameterizedURL(streamService.URL, new String[]{versionSpecification});
//		RequestGenerator feedreq;
//		System.out.println(streamService.URL);
//		headers.put("x-myntra-abtest", "lgp.stream.variant=fixed");
//		
//		if(configValues.containsKey("platform")){
//			if(configValues.get("platform")==null)
//				headers.put("platform", null);
//			else
//				headers.put("platform", configValues.get("platform").toString());
//		}
//		
//		feedreq = new RequestGenerator(streamService,headers);
//		
//		String response = feedreq.returnresponseasstring();
//		System.out.println(response);
//		log.info(response);
//		
//		ArrayList<String> cardOrderInResponse=new ArrayList<>();
//		Map<String, Integer> cardCountInResponse=new HashMap<>();
//		getCardOrderAndCardCounts(response, cardOrderInResponse, cardCountInResponse);
//		
//		ArrayList<String> failures= new ArrayList<>();
//		validateStreamResponse(cardOrderInResponse, cardCountInResponse, configValues, failures,"[stream]");
//		
//		//Validate history call
//		for(int i=0; i<5; i++){
//			validateHistoryCall(JsonPath.read(response, "$.page.previous").toString(), configValues, failures);
//		}
//		
//		//Validate more call
//		for(int i=0; i<5; i++){
//			validateMoreCall(JsonPath.read(response, "$.page.next").toString(), configValues, failures);
//		}
//		
//		//Validate slideshow call
//		validateSlideShowCall(versionSpecification, configValues, failures);
//		
//		//Validate update call
//		//validateUpdateCall(versionSpecification, configValues, failures);
//		
//		//Check for target card occurrences
//		if(configValues.get("targeted")!=null && configValues.get("targeted").equals("once")){
//			for(Map.Entry<String, Integer> entry:targetCardIdFrequency.entrySet()){
//				if(entry.getValue()>1){
//					failures.add("targeted once: Targeted card id "+entry.getKey()+" has occurred more than once : "+entry.getKey());
//				}
//			}
//		}
//		
//		if(failures.size()>0){
//			Assert.fail(getFailuresPoints(failures));
//		}
//		
//		
//	}
  
  	public void getCardOrderAndCardCounts(String response,ArrayList<String> cardOrderInResponse,Map<String, Integer> cardCountInResponse){
  		int totalCardCount = Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
		int cardIndex=0;
		int orderIndex=-1;
		
		while(cardIndex<totalCardCount){
			String sourceOfCurrentCard = JsonPath.read(response,"$.cards["+cardIndex+"].props.meta.source").toString().trim();
			//Record the count of the card
				if(cardCountInResponse.containsKey(sourceOfCurrentCard)){
					cardCountInResponse.put(sourceOfCurrentCard, cardCountInResponse.get(sourceOfCurrentCard)+1);
				}else{
					cardCountInResponse.put(sourceOfCurrentCard,  1);
					
				}
				if(!sourceOfCurrentCard.equalsIgnoreCase("history")){
					if(orderIndex==-1 || !cardOrderInResponse.get(orderIndex).equals(sourceOfCurrentCard)){
						cardOrderInResponse.add(sourceOfCurrentCard);
						orderIndex++;
					}
				}
				//Get the frequency of the target cards
				if(sourceOfCurrentCard.equalsIgnoreCase("targeted")){
					String cardId= JsonPath.read(response, "$.cards["+cardIndex+"].props.id");
					if(targetCardIdFrequency.containsKey(cardId)){
						targetCardIdFrequency.put(cardId, targetCardIdFrequency.get(cardId)+1);
					}else{
						targetCardIdFrequency.put(cardId, 1);
					}
						
				}
				cardIndex++;
		}
		log.info("Order of cards in response "+cardOrderInResponse);
		log.info("Count of cards in response "+cardCountInResponse);
		

  	}
	
	public void validateStreamResponse(ArrayList<String> cardOrderInResponse,Map<String, Integer> cardCountInResponse, Map<String, Object> configValues,ArrayList<String> failures,String apiCall){
		//TODO: Validation for default config and fixed config

		String[] order = (String[])configValues.get("order");
		Map<String, Integer> count = (HashMap<String, Integer>) configValues.get("count");

		//Check for the order
		if(!(boolean)configValues.get("randomOrder")){
			if (order.length< cardOrderInResponse.size()){
				failures.add(apiCall+"The order types recieved in response is more than expected");
			}
			
			int expectedOrderIndex=0;
			int responseOrderIndex=0;
			while(responseOrderIndex<cardOrderInResponse.size()){
				//TODO:Check with Swathy for this
				if(cardOrderInResponse.get(responseOrderIndex).equals("history")){
					responseOrderIndex++;
					continue;
				}
				if(cardOrderInResponse.get(responseOrderIndex).equalsIgnoreCase(order[expectedOrderIndex])){
					responseOrderIndex++;
				} 
				if(order.length<= ++expectedOrderIndex && responseOrderIndex<cardOrderInResponse.size()){
					failures.add(apiCall+"order not matching: The order of the response :"+cardOrderInResponse+" does not match the expected order "+Arrays.asList(order));
					break;
				}
			}
		}
		
		//Check for count
		for(Map.Entry<String, Integer> entry:count.entrySet()){
			if(cardCountInResponse.containsKey(entry.getKey())){
				if((entry.getKey().equalsIgnoreCase("targeted") && configValues.get("targeted").equals("disabled"))|| (entry.getKey().equalsIgnoreCase("promoted")&& configValues.get("promoted").equals("disabled"))){
					failures.add(apiCall+"Disabled config: The card source "+entry.getKey()+" is disabled in configuration");
				}else{
					if(cardCountInResponse.get(entry.getKey())>entry.getValue()){
						failures.add(apiCall+"Card Count: The card count of ("+cardCountInResponse.get(entry.getKey())+")"+entry.getKey()+" type is greater than expected "+"("+entry.getValue()+")");
						
					}
				}
			}
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
	
	public void validateHistoryCall(String urlParameter, Map<String, Object> configValues, ArrayList<String> failures){
		String apiCall="[history]";
		MyntraService streamService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETPREVIOUSSTREAM,init.Configurations);
		streamService.URL=utilities.prepareparameterizedURL(streamService.URL, new String[]{urlParameter});
		RequestGenerator feedreq;
		System.out.println(streamService.URL);
		if(headers!=null)
		{
		  feedreq = new RequestGenerator(streamService,headers);
		}else{
			feedreq = new RequestGenerator(streamService);
		}
		
		String response = feedreq.returnresponseasstring();
		
		try{
			previousURL=(String)JsonPath.read(response, "$.page.previous");
		}catch(Exception e){
			previousURL="";
		}
		
		System.out.println("History call response : \n"+response);
		log.info("History call response : \n"+response);
		
		Map<String, Integer> cardCountInResponse=new HashMap<>();
		ArrayList<String> cardOrderInResponse=new ArrayList<>();
		
		getCardOrderAndCardCounts(response, cardOrderInResponse, cardCountInResponse);
		int totalCardCount =Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
		
		if(configValues.containsKey("history")){
				if(configValues.get("history").equals("disabled")){
					if(totalCardCount>0){
						failures.add(apiCall+"history disabled: History call is returning response while history is disabled");
						
					}
				//All the cards returned should be from history if config is set to seen
				}else if(configValues.get("history").equals("seen")){
					
					if(cardCountInResponse.containsKey("history")){
						if(cardCountInResponse.get("history")!=totalCardCount){
							failures.add(apiCall+"history seen: The history call has retrieved cards from other table when config is set to seen");
						}
					}
				//No card should be retrieved from history table if config is set to unseen
				}else if(configValues.get("history").equals("unseen")){
					if(cardCountInResponse.containsKey("history")){
						failures.add("history unseen: The history call has retrieved cards from history table when config value is unseen");
					}
					validateStreamResponse(cardOrderInResponse, cardCountInResponse, configValues, failures,apiCall);
					
				}else if(configValues.get("history").equals("enabled")){
					validateStreamResponse(cardOrderInResponse, cardCountInResponse, configValues, failures,apiCall);
				}
		}else if(!configValues.containsKey("history")){
			validateStreamResponse(cardOrderInResponse, cardCountInResponse, configValues, failures,apiCall);
		}
	}
	
	public void validateMoreCall(String urlParameter, Map<String, Object> configValues, ArrayList<String> failures){
		String apiCall="[more]";
		MyntraService streamService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETNEXTSTREAM,init.Configurations);
		streamService.URL=utilities.prepareparameterizedURL(streamService.URL, new String[]{urlParameter});
		RequestGenerator feedreq;
		System.out.println(streamService.URL);
		if(headers!=null)
		{
		  feedreq = new RequestGenerator(streamService,headers);
		}else{
			feedreq = new RequestGenerator(streamService);
		}
		
		String response = feedreq.returnresponseasstring();
		try{
			nextURL =(String) JsonPath.read(response, "$.page.next");
		}catch(Exception e){
			nextURL="";
		}
		System.out.println("More call response : \n"+response);
		log.info("More call response : \n"+response);
		
		Map<String, Integer> cardCountInResponse=new HashMap<>();
		ArrayList<String> cardOrderInResponse=new ArrayList<>();
		
		getCardOrderAndCardCounts(response, cardOrderInResponse, cardCountInResponse);
		int totalCardCount =Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
		
		if(configValues.containsKey("more")){
			if(configValues.get("more").equals("disabled")){
				if(totalCardCount>0){
					failures.add(apiCall+"more disabled: More call is returning response while more is disabled in config");
					
				}else if(configValues.get("more").equals("enabled")){
					validateStreamResponse(cardOrderInResponse, cardCountInResponse, configValues, failures,apiCall);
				}
			}
		}else if(!configValues.containsKey("more")){
			validateStreamResponse(cardOrderInResponse, cardCountInResponse, configValues, failures,apiCall);
		}
	}
	

	public void validateSlideShowCall(String urlParameter, Map<String, Object> configValues, ArrayList<String> failures){
		String apiCall="[slideshow]";
		MyntraService streamService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETSTREAMSLIDESHOW,init.Configurations);
		streamService.URL=utilities.prepareparameterizedURL(streamService.URL, new String[]{urlParameter});
		RequestGenerator feedreq;
		System.out.println(streamService.URL);
		if(headers!=null)
		{
		  feedreq = new RequestGenerator(streamService,headers);
		}else{
			feedreq = new RequestGenerator(streamService);
		}
		
		String response = feedreq.returnresponseasstring();
		System.out.println("Slideshow call response : \n"+response);
		log.info("Slideshow call response : \n"+response);
		
		/*Map<String, Integer> cardCountInResponse=new HashMap<>();
		ArrayList<String> cardOrderInResponse=new ArrayList<>();
		
		getCardOrderAndCardCounts(response, cardOrderInResponse, cardCountInResponse);*/
		int totalCardCount =Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
		
		if(configValues.containsKey("slideshow")){
			if(configValues.get("slideshow").equals("disabled")){
				if(totalCardCount>0){
					failures.add(apiCall+"slideshow disabled: slideshow call is returning response while slideshow is disabled in config");
					
				}
			}else if(configValues.get("slideshow").equals("enabled")){
				if(totalCardCount<=0){
					failures.add(apiCall+"slideshow enabled: slideshow call is not returning response while slideshow is enabled in config");
					
				}
			}
		}else{
			if(totalCardCount<=0){
				failures.add(apiCall+"slideshow enabled: slideshow call is not returning response while slideshow is enabled in config");
				
			}
		}
	}
	

	public void validateUpdateCall(String version, Map<String, Object> configValues, ArrayList<String> failures){
		String apiCall="[update]";
		String payloadFilePath = "./Data/payloads/JSON/lgp/streamsUpdatePayload";
		MyntraService streamService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.POSTUPDATESTREAM,init.Configurations,PayloadType.JSON,"{\"update\":\"true\"}");
		streamService.URL=utilities.prepareparameterizedURL(streamService.URL, new String[]{version});
		RequestGenerator feedreq;
		System.out.println(streamService.URL);
		if(headers!=null)
		{
		  feedreq = new RequestGenerator(streamService,headers);
		}else{
			feedreq = new RequestGenerator(streamService);
		}
		
		String response = feedreq.returnresponseasstring();
		System.out.println("Update call response : \n"+response);
		log.info("Update call response : \n"+response);
		
		Map<String, Integer> cardCountInResponse=new HashMap<>();
		ArrayList<String> cardOrderInResponse=new ArrayList<>();
		
		getCardOrderAndCardCounts(response, cardOrderInResponse, cardCountInResponse);
		int totalCardCount =Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
		
		if(configValues.containsKey("update")){
			if(configValues.get("update").equals("disabled")){
				if(totalCardCount>0){
					failures.add(apiCall+"update disabled: update call is returning response while update is disabled in config");
					
				}
			}else if(configValues.get("update").equals("enabled")){
				validateStreamResponse(cardOrderInResponse, cardCountInResponse, configValues, failures,apiCall);
			}
		}else if(!configValues.containsKey("update")){
			validateStreamResponse(cardOrderInResponse, cardCountInResponse, configValues, failures,apiCall);
		}
	}
}


