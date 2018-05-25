package com.myntra.apiTests.portalservices.lgpServicesOnDemand;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.CardObjectDP;
import com.myntra.apiTests.portalservices.lgpServe.feed.FeedObjectHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.CardPayloads;
import com.myntra.lordoftherings.CardTypes;
import com.myntra.lordoftherings.ILgpJsonKeys;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.LgpArticleCardPOJO;
import com.myntra.lordoftherings.LgpBannerCardPOJO;
import com.myntra.lordoftherings.LgpCardObject2o6Impl;
import com.myntra.lordoftherings.LgpCollectionsCardPOJO;
import com.myntra.lordoftherings.LgpPostoShotPOJO;
import com.myntra.lordoftherings.LgpPostoStyleUpdatePOJO;
import com.myntra.lordoftherings.LgpProductCardPOJO;
import com.myntra.lordoftherings.LgpSplitBannerPOJO;
import com.myntra.lordoftherings.LgpVideoPOJO;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

import net.minidev.json.JSONArray;



public class CardObjectTests extends CardObjectDP {

	static Initialize init = new Initialize("/Data/configuration");
	String env = init.Configurations.GetTestEnvironemnt().toString();
	static Logger log = Logger.getLogger(CardObjectTests.class);
	
	FeedObjectHelper cardObjectHelper = new FeedObjectHelper();
	
	static APIUtilities apiUtil = new APIUtilities();
	
	HashMap<String, String> headers;
	public static String versionSpecification;
	
	
	
	@BeforeClass(alwaysRun=true)
	public void init(){
		
		headers = new HashMap<String,String>();
		headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		
		versionSpecification=System.getenv("API_VERSION");
		
		if(null==versionSpecification)
		{
			versionSpecification = "v2.6";
		}
	}

	@AfterMethod 
	protected void endOfTestMethod(Method method) throws Exception { 
		
		String methodName = method.getName();
		System.out.println("End of method -------- "+methodName);
		System.out.println("=====================================================================================================");
	}

	public String getPayLoadForCard(CardPayloads cardPayloadType) {
		
		String payload = null;
		String defaultPath = "Data/payloads/JSON/lgp/";
		
		switch (cardPayloadType) {

		case ARTICLE_CARD:

			try {

				payload = apiUtil.readFileAsString(defaultPath.concat(cardPayloadType.filename));

			} catch (IOException e) {

				e.printStackTrace();
			}
			break;

		case BANNER_CARD:

			try {

				payload = apiUtil.readFileAsString(defaultPath.concat(cardPayloadType.filename));

			} catch (IOException e) {

				e.printStackTrace();
			}

			break;
			
		case COLLECTIONS_CARD:
			
			try {

				payload = apiUtil.readFileAsString(defaultPath.concat(cardPayloadType.filename));

			} catch (IOException e) {

				e.printStackTrace();
			}

			break;
			
		case PRODUCT_CARD:
			
			try {

				payload = apiUtil.readFileAsString(defaultPath.concat(cardPayloadType.filename));

			} catch (IOException e) {

				e.printStackTrace();
			}
			
			break;
			
		case POSToSTYLEUPDATE:
			
			try {

				payload = apiUtil.readFileAsString(defaultPath.concat(cardPayloadType.filename));

			} catch (IOException e) {

				e.printStackTrace();
			}

			break;
			
			
		case POSToSHOT_CARD:
			
			try {

				payload = apiUtil.readFileAsString(defaultPath.concat(cardPayloadType.filename));

			} catch (IOException e) {

				e.printStackTrace();
			}

			break;
			
		case SPLIT_BANNER_CARD:
			
			try {

				payload = apiUtil.readFileAsString(defaultPath.concat(cardPayloadType.filename));

			} catch (IOException e) {

				e.printStackTrace();
			}

			break;
			
		case VIDEO_CARD:
			
			try {

				payload = apiUtil.readFileAsString(defaultPath.concat(cardPayloadType.filename));

			} catch (IOException e) {

				e.printStackTrace();
			}

			break;

			
					   
		 default:
			 payload ="";
		 
		}
		
		return payload;
	}

	
	protected boolean cardComponentsValidationProcess(String[] feedCardComponents, String jsonResponse) {
		
		boolean result=false;
		ArrayList<String> referenceList = new ArrayList<String>();
		JSONArray components = JsonPath.read(jsonResponse,"$.cards[*].children[*]..type");
		for(int i=0;i<feedCardComponents.length;i++)
		{
			if(ArrayUtils.contains(components.toArray(),feedCardComponents[i]))
			{
				referenceList.add(feedCardComponents[i]+"-true");
			}
			else{
				referenceList.add(feedCardComponents[i]+"-false");
			}
		}
		System.out.println("Card Components Validations--------------> " + referenceList);
		result = checkFinalResult(referenceList);
		return result;
		
	}
	
	public static String[] getStringArray(Object[] cardComponentObjects){
		
	    String[] stringArray = null;
	    
	    int length = cardComponentObjects.length;
	    
	    if(cardComponentObjects!=null){
	        stringArray = new String[length];
	        for(int i=0;i<length;i++){
	            
	            stringArray[i] = cardComponentObjects[i].toString();
	        }
	    }
	    return stringArray;
	}
	
	private boolean checkFinalResult(ArrayList<String> referenceList) {
		
		for(int referenceListIndex=0;referenceListIndex<referenceList.size();referenceListIndex++)
		{
			if(referenceList.get(referenceListIndex).contains("false"))
			{
				return false;
			}
		}
		return true;
	}
	
	private ILgpJsonKeys cardObjectImplementationInstance(String versionSpecification){
		
		
		ILgpJsonKeys iCardObjectInstance = null;
		
		switch(versionSpecification){
			
			case "v2.6":
				
				iCardObjectInstance = new LgpCardObject2o6Impl();
				break;
			
			
		
		}
		
		return iCardObjectInstance;
		
	}
	
	public boolean mainComponentCardValidation(String jsonResponse){
		
		boolean componentCardValidationResult = false;
		
		String modifiedResponse = JsonPath.read(jsonResponse, "$.cards[0]").toString();
		String basicComponentCard = JsonPath.read(jsonResponse, "$.cards[0].type").toString();
		
		componentCardValidationResult = jsonKeyValidationsForComponents(basicComponentCard, versionSpecification, modifiedResponse, 0, "CardObjectTests");
		
		return componentCardValidationResult;
	}
	
	public boolean componentValidationsForCardType(CardTypes cardType, String versionSpecification, String jsonResponse)
    {
    	
		boolean result =false;
		ILgpJsonKeys vCardObjectImplementation = cardObjectImplementationInstance(versionSpecification); 
		
		
		switch(cardType){
			
			case ARTICLE:
				
				String[] articleComponents = vCardObjectImplementation.getComponentsForArticleCard(jsonResponse);
				result = cardComponentsValidationProcess(articleComponents, jsonResponse);
				break;
				
			case BANNER:
				
				String[] bannerComponents = vCardObjectImplementation.getComponentsForBannerCard(jsonResponse);
				result = cardComponentsValidationProcess(bannerComponents, jsonResponse);
				break;
				
			case COLLECTIONS:
				
				String[] collectionComponents = vCardObjectImplementation.getComponentsForCollectionsBannerCard(jsonResponse);
				result = cardComponentsValidationProcess(collectionComponents,jsonResponse);
				break;
				
			case PRODUCT:
				
				String[] productComponents = vCardObjectImplementation.getComponentsForProductCard(jsonResponse);
				result = cardComponentsValidationProcess(productComponents,jsonResponse);
				break;
				
			case POSToSTYLEUPDATE:
				
				String[] postoStyleUpdateComponents = vCardObjectImplementation.getComponentsForPostStyleUpdateCard(jsonResponse);
				result = cardComponentsValidationProcess(postoStyleUpdateComponents,jsonResponse);
				break;
				
			case POSToSHOT:
				
				String[] postoShotComponents = vCardObjectImplementation.getComponentsForPostShotCard(jsonResponse);
				result = cardComponentsValidationProcess(postoShotComponents,jsonResponse);
				break;
				
			case SPLITBANNER:
				
				String[] splitBannerComponents = vCardObjectImplementation.getComponentsForSplitBannerCard(jsonResponse);
				result = cardComponentsValidationProcess(splitBannerComponents,jsonResponse);
				break;
				
			case VIDEO:
				
				String[] videoComponents = vCardObjectImplementation.getComponentsForVideoCard(jsonResponse);
				result = cardComponentsValidationProcess(videoComponents,jsonResponse);
				
			default:
				result=true;
				break;
		}
		
		return result;
    }

	public boolean cardComponentsJsonKeyValidation(String jsonResponse){
		
		JSONArray cardComponents = JsonPath.read(jsonResponse,"$.cards[*].children[*].type");
		
		String modifiedCardResponse  = JsonPath.read(jsonResponse,"$.cards[0]").toString();
		
		String [] cardComponentArray = getStringArray(cardComponents.toArray());
		
		
		ArrayList<String> cardComponentsResultList = new ArrayList<String>();
		
		for(int cardComponentsIndex = 0; cardComponentsIndex < cardComponentArray.length; cardComponentsIndex++){
			
			String componentResult = String.valueOf(jsonKeyValidationsForComponents(cardComponentArray[cardComponentsIndex], versionSpecification, modifiedCardResponse, cardComponentsIndex, "CardObjectTests"));
			cardComponentsResultList.add(componentResult);
		}
		boolean finalResult = checkFinalResult(cardComponentsResultList);
		
		return finalResult;
	}
	
	public String serveSideObjectParse(String appId, String refId, String cardType){
	
		String objectIdReference = appId+":"+refId;
		
		RequestGenerator request = getQueryRequestMultiParam(APINAME.GETFEEDOBJECTENTITY, objectIdReference, versionSpecification);
		String response = request.respvalidate.returnresponseasstring();
		
		System.out.println("LGP-Serve response for ---- > "+cardType);
		System.out.println(response);
		System.out.println("=============== END ===================");
		
		return response;
	}
	
	@Test(groups = {"Sanity"}, dataProvider = "articleRegisterDP", priority = 1)
    public void articleRegistrationValidationsTest(LgpArticleCardPOJO articleObject) throws IOException{
		
		String[] articlePayload = {articleObject.getAppId(), articleObject.getRefId(), articleObject.getType(),
				articleObject.getTitle(), articleObject.getDescription(), articleObject.getUrl(),
				articleObject.getImageUrl(), articleObject.getExtraData(), articleObject.getTopics(),
				articleObject.getProductRack(), articleObject.getIsPrivate(), articleObject.getCreateAction(),
				articleObject.getEnabled(), articleObject.getIsSpam()};
		
	    String payloadInString = getPayLoadForCard(CardPayloads.ARTICLE_CARD);
	    String preparedPayload = apiUtil.preparepayload(payloadInString, articlePayload);
		
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT, init.Configurations,preparedPayload);
        RequestGenerator request = new RequestGenerator(service, headers);
        String response = request.returnresponseasstring();
        
        System.out.println("============    Pumps Object Registeration Response for : ============== "+CardTypes.ARTICLE.cardName);
        System.out.println(response);
        System.out.println("=================================================================================================");
        
		Assert.assertEquals(request.response.getStatus(), 200, "Status Code Fail");
		Assert.assertEquals(JsonPath.read(response,"$.status.statusType"), "SUCCESS", "Status Type Fail");
		Assert.assertEquals(JsonPath.read(response,"$.status.statusMessage"), "Object added successfully", "Status Message Mis-match");
		
		String serveSideResponse = serveSideObjectParse(articleObject.getAppId(), articleObject.getRefId(), CardPayloads.ARTICLE_CARD.filename);
		String cardSchemaRetrieved = cardObjectHelper.getComponentCardSchema(CardPayloads.GENERIC_CARD.filename, versionSpecification);
		
		boolean componentCardValidationResult = mainComponentCardValidation(serveSideResponse);
		boolean schemaValidationResult = cardObjectHelper.componentCardSchemaValidation(serveSideResponse, cardSchemaRetrieved, versionSpecification);
		boolean componentValidationResult = componentValidationsForCardType(CardTypes.ARTICLE, versionSpecification, serveSideResponse);
		boolean cardKeyValidationResult = cardComponentsJsonKeyValidation(serveSideResponse);

		Assert.assertEquals(componentCardValidationResult, true, "Main Component Card Validation Failed for : -- >"+CardTypes.ARTICLE.cardName);
		Assert.assertEquals(schemaValidationResult, true, "Schema Validation Failed for : -- > "+CardTypes.ARTICLE.cardName);
		Assert.assertEquals(componentValidationResult, true,"Basic Component Validation Fail for : -- > "+CardTypes.ARTICLE.cardName);
		Assert.assertEquals(cardKeyValidationResult, true, "Key Validations Fail for : -- > "+CardTypes.ARTICLE.cardName);
	}
	
	@Test(groups = {"Sanity"}, dataProvider = "bannerRegisterDP", priority = 2)
    public void bannerRegistrationValidationsTest(LgpBannerCardPOJO bannerObject) throws IOException{
		
		String[] bannerPayload = {bannerObject.getAppId(), bannerObject.getRefId(), bannerObject.getType(),
				bannerObject.getTitle(), bannerObject.getUrl(), bannerObject.getImageUrl(), bannerObject.getExtraData(),
				bannerObject.getIsPrivate(), bannerObject.getCreateAction(), bannerObject.getEnabled(), 
				bannerObject.getIsSpam()};
		
		String payloadInString = getPayLoadForCard(CardPayloads.BANNER_CARD);
		String preparedPayload = apiUtil.preparepayload(payloadInString, bannerPayload);
			
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT, init.Configurations,preparedPayload);
        RequestGenerator request = new RequestGenerator(service, headers);
        String response = request.returnresponseasstring();
        
        System.out.println("============    Pumps Object Registeration Response for : ============== "+CardTypes.BANNER.cardName);
        System.out.println(response);
        System.out.println("=================================================================================================");
      
        Assert.assertEquals(request.response.getStatus(), 200, "Status Code Fail");
		Assert.assertEquals(JsonPath.read(response,"$.status.statusType"), "SUCCESS", "Status Type Fail");
		Assert.assertEquals(JsonPath.read(response,"$.status.statusMessage"), "Object added successfully", "Status Message Mis-match");
		
		String serveSideResponse = serveSideObjectParse(bannerObject.getAppId(), bannerObject.getRefId(), CardPayloads.BANNER_CARD.filename);
		String cardSchemaRetrieved = cardObjectHelper.getComponentCardSchema(CardPayloads.GENERIC_CARD.filename, versionSpecification);
		
		boolean componentCardValidationResult = mainComponentCardValidation(serveSideResponse);
		boolean schemaValidationResult = cardObjectHelper.componentCardSchemaValidation(serveSideResponse, cardSchemaRetrieved, versionSpecification);
		boolean componentValidationResult = componentValidationsForCardType(CardTypes.BANNER, versionSpecification, serveSideResponse);
		boolean cardKeyValidationResult = cardComponentsJsonKeyValidation(serveSideResponse);
		
		Assert.assertEquals(componentCardValidationResult, true, "Main Component Card Validation Failed for : -- >"+CardTypes.BANNER.cardName);
		Assert.assertEquals(schemaValidationResult, true, "Schema Validation Failed for : -- > "+CardTypes.BANNER.cardName);
		Assert.assertEquals(componentValidationResult, true,"Basic Component Validation Fail for : -- > "+CardTypes.BANNER.cardName);
		Assert.assertEquals(cardKeyValidationResult, true, "Key Validations Fail for : -- > "+CardTypes.BANNER.cardName);
		
	}
	
	@Test(groups = {"Sanity"}, dataProvider = "collectionsRegisterDP", priority = 3)
	public void collectionsRegistrationValidationsTest(LgpCollectionsCardPOJO collectionsObject) throws IOException{
		
		String[] collectionsPayload = {collectionsObject.getAppId(), collectionsObject.getRefId(), collectionsObject.getType(), 
				collectionsObject.getTitle(), collectionsObject.getDescription(), collectionsObject.getUrl(), collectionsObject.getImageUrl(),
				collectionsObject.getExtraData(), collectionsObject.getTopics(),collectionsObject.getIsPrivate(), collectionsObject.getCreateAction(),
				collectionsObject.getEnabled(),collectionsObject.getIsSpam()};
		
		String payloadInString = getPayLoadForCard(CardPayloads.COLLECTIONS_CARD);
		String preparedPayload = apiUtil.preparepayload(payloadInString, collectionsPayload);
		
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT, init.Configurations,preparedPayload);
        RequestGenerator request = new RequestGenerator(service, headers);
        String response = request.returnresponseasstring();
        
        System.out.println("============    Pumps Object Registeration Response for : ============== "+CardTypes.COLLECTIONS.cardName);
        System.out.println(response);
        System.out.println("=================================================================================================");
      
        Assert.assertEquals(request.response.getStatus(), 200, "Status Code Fail");
		Assert.assertEquals(JsonPath.read(response,"$.status.statusType"), "SUCCESS", "Status Type Fail");
		Assert.assertEquals(JsonPath.read(response,"$.status.statusMessage"), "Object added successfully", "Status Message Mis-match");
		
		String serveSideResponse = serveSideObjectParse(collectionsObject.getAppId(), collectionsObject.getRefId(), CardPayloads.COLLECTIONS_CARD.filename);
		String cardSchemaRetrieved = cardObjectHelper.getComponentCardSchema(CardPayloads.GENERIC_CARD.filename, versionSpecification);
		
		boolean componentCardValidationResult = mainComponentCardValidation(serveSideResponse);
		boolean schemaValidationResult = cardObjectHelper.componentCardSchemaValidation(serveSideResponse, cardSchemaRetrieved, versionSpecification);
		boolean componentValidationResult = componentValidationsForCardType(CardTypes.COLLECTIONS, versionSpecification, serveSideResponse);
		boolean cardKeyValidationResult = cardComponentsJsonKeyValidation(serveSideResponse);
		
		Assert.assertEquals(componentCardValidationResult, true, "Main Component Card Validation Failed for : -- >"+CardTypes.COLLECTIONS.cardName);
		Assert.assertEquals(schemaValidationResult, true, "Schema Validation Failed for : -- > "+CardTypes.COLLECTIONS.cardName);
		Assert.assertEquals(componentValidationResult, true,"Basic Component Validation Fail for : -- > "+CardTypes.COLLECTIONS.cardName);
		Assert.assertEquals(cardKeyValidationResult, true, "Key Validations Fail for : -- > "+CardTypes.COLLECTIONS.cardName);
	}
	
	@Test(groups = {"Sanity"}, dataProvider = "productRegisterDP", priority = 4)
	public void productRegistrationValidationsTest(LgpProductCardPOJO productObject) throws IOException{
		
		String[] productPayload = {productObject.getAppId(), productObject.getRefId(), productObject.getType(), 
				productObject.getTitle(), productObject.getDescription(), productObject.getUrl(), productObject.getImageUrl(),
				productObject.getExtraData(), productObject.getTopics(),productObject.getIsPrivate(), productObject.getCreateAction(),
				productObject.getEnabled(),productObject.getIsSpam()};
		
		String payloadInString = getPayLoadForCard(CardPayloads.PRODUCT_CARD);
		String preparedPayload = apiUtil.preparepayload(payloadInString, productPayload);
		
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT, init.Configurations,preparedPayload);
        RequestGenerator request = new RequestGenerator(service, headers);
        String response = request.returnresponseasstring();
        
        System.out.println("============    Pumps Object Registeration Response for : ============== "+CardTypes.PRODUCT.cardName);
        System.out.println(response);
        System.out.println("=================================================================================================");
      
        Assert.assertEquals(request.response.getStatus(), 200, "Status Code Fail");
		Assert.assertEquals(JsonPath.read(response,"$.status.statusType"), "SUCCESS", "Status Type Fail");
		Assert.assertEquals(JsonPath.read(response,"$.status.statusMessage"), "Object added successfully", "Status Message Mis-match");
		
		String serveSideResponse = serveSideObjectParse(productObject.getAppId(), productObject.getRefId(), CardPayloads.PRODUCT_CARD.filename);
		String cardSchemaRetrieved = cardObjectHelper.getComponentCardSchema(CardPayloads.GENERIC_CARD.filename, versionSpecification);
		
		boolean componentCardValidationResult = mainComponentCardValidation(serveSideResponse);
		boolean schemaValidationResult = cardObjectHelper.componentCardSchemaValidation(serveSideResponse, cardSchemaRetrieved, versionSpecification);
		boolean componentValidationResult = componentValidationsForCardType(CardTypes.PRODUCT, versionSpecification, serveSideResponse);
		boolean cardKeyValidationResult = cardComponentsJsonKeyValidation(serveSideResponse);
		
		Assert.assertEquals(componentCardValidationResult, true, "Main Component Card Validation Failed for : -- >"+CardTypes.PRODUCT.cardName);
		Assert.assertEquals(schemaValidationResult, true, "Schema Validation Failed for : -- > "+CardTypes.PRODUCT.cardName);
		Assert.assertEquals(componentValidationResult, true,"Basic Component Validation Fail for : -- > "+CardTypes.PRODUCT.cardName);
		Assert.assertEquals(cardKeyValidationResult, true, "Key Validations Fail for : -- > "+CardTypes.PRODUCT.cardName);
	}
	
	@Test(groups = {"Sanity"}, dataProvider = "postoStyleUpateDP", priority = 5)
	public void postoStyleUpdateValidationsTest(LgpPostoStyleUpdatePOJO productObject) throws IOException{
		
		String[] productPayload = {productObject.getAppId(), productObject.getRefId(), productObject.getType(), 
				productObject.getTitle(), productObject.getDescription(), productObject.getUrl(), productObject.getImageUrl(),
				productObject.getExtraData(), productObject.getAuthor(),productObject.getTopics(), productObject.getProductRack(),
				productObject.getIsPrivate(), productObject.getCreateAction(), productObject.getEnabled(),productObject.getIsSpam()};
		
		String payloadInString = getPayLoadForCard(CardPayloads.POSToSTYLEUPDATE);
		String preparedPayload = apiUtil.preparepayload(payloadInString, productPayload);
		
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT, init.Configurations,preparedPayload);
        RequestGenerator request = new RequestGenerator(service, headers);
        String response = request.returnresponseasstring();
        
        System.out.println("============    Pumps Object Registeration Response for : ============== "+CardTypes.POSToSTYLEUPDATE.cardName);
        System.out.println(response);
        System.out.println("=================================================================================================");
      
        Assert.assertEquals(request.response.getStatus(), 200, "Status Code Fail");
		Assert.assertEquals(JsonPath.read(response,"$.status.statusType"), "SUCCESS", "Status Type Fail");
		Assert.assertEquals(JsonPath.read(response,"$.status.statusMessage"), "Object added successfully", "Status Message Mis-match");
		
		String serveSideResponse = serveSideObjectParse(productObject.getAppId(), productObject.getRefId(), CardPayloads.POSToSTYLEUPDATE.filename);
		String cardSchemaRetrieved = cardObjectHelper.getComponentCardSchema(CardPayloads.GENERIC_CARD.filename, versionSpecification);
		
		boolean componentCardValidationResult = mainComponentCardValidation(serveSideResponse);
		boolean schemaValidationResult = cardObjectHelper.componentCardSchemaValidation(serveSideResponse, cardSchemaRetrieved, versionSpecification);
		boolean componentValidationResult = componentValidationsForCardType(CardTypes.POSToSTYLEUPDATE, versionSpecification, serveSideResponse);
		boolean cardKeyValidationResult = cardComponentsJsonKeyValidation(serveSideResponse);
		
		Assert.assertEquals(componentCardValidationResult, true, "Main Component Card Validation Failed for : -- >"+CardTypes.POSToSTYLEUPDATE.cardName);
		Assert.assertEquals(schemaValidationResult, true, "Schema Validation Failed for : -- > "+CardTypes.POSToSTYLEUPDATE.cardName);
		Assert.assertEquals(componentValidationResult, true,"Basic Component Validation Fail for : -- > "+CardTypes.POSToSTYLEUPDATE.cardName);
		Assert.assertEquals(cardKeyValidationResult, true, "Key Validations Fail for : -- > "+CardTypes.POSToSTYLEUPDATE.cardName);
	}
	
	@Test(groups = {"Sanity"}, dataProvider = "postoShotDP", priority = 6)
	public void postoShotValidationsTest(LgpPostoShotPOJO productObject) throws IOException{
		
		String[] productPayload = {productObject.getAppId(), productObject.getRefId(), productObject.getType(), 
				productObject.getTitle(), productObject.getDescription(), productObject.getUrl(), productObject.getImageUrl(),
				productObject.getExtraData(), productObject.getAuthor(),productObject.getTopics(), productObject.getProductRack(),
				productObject.getIsPrivate(), productObject.getCreateAction(), productObject.getEnabled(),productObject.getIsSpam()};
		
		String payloadInString = getPayLoadForCard(CardPayloads.POSToSHOT_CARD);
		String preparedPayload = apiUtil.preparepayload(payloadInString, productPayload);
		
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT, init.Configurations,preparedPayload);
        RequestGenerator request = new RequestGenerator(service, headers);
        String response = request.returnresponseasstring();
        
        System.out.println("============    Pumps Object Registeration Response for : ============== "+CardTypes.POSToSHOT.cardName);
        System.out.println(response);
        System.out.println("=================================================================================================");
      
        Assert.assertEquals(request.response.getStatus(), 200, "Status Code Fail");
		Assert.assertEquals(JsonPath.read(response,"$.status.statusType"), "SUCCESS", "Status Type Fail");
		Assert.assertEquals(JsonPath.read(response,"$.status.statusMessage"), "Object added successfully", "Status Message Mis-match");
		
		String serveSideResponse = serveSideObjectParse(productObject.getAppId(), productObject.getRefId(), CardPayloads.POSToSHOT_CARD.filename);
		String cardSchemaRetrieved = cardObjectHelper.getComponentCardSchema(CardPayloads.GENERIC_CARD.filename, versionSpecification);
		
		boolean componentCardValidationResult = mainComponentCardValidation(serveSideResponse);
		boolean schemaValidationResult = cardObjectHelper.componentCardSchemaValidation(serveSideResponse, cardSchemaRetrieved, versionSpecification);
		boolean componentValidationResult = componentValidationsForCardType(CardTypes.POSToSHOT, versionSpecification, serveSideResponse);
		boolean cardKeyValidationResult = cardComponentsJsonKeyValidation(serveSideResponse);
		
		Assert.assertEquals(componentCardValidationResult, true, "Main Component Card Validation Failed for : -- >"+CardTypes.POSToSHOT.cardName);
		Assert.assertEquals(schemaValidationResult, true, "Schema Validation Failed for : -- > "+CardTypes.POSToSHOT.cardName);
		Assert.assertEquals(componentValidationResult, true,"Basic Component Validation Fail for : -- > "+CardTypes.POSToSHOT.cardName);
		Assert.assertEquals(cardKeyValidationResult, true, "Key Validations Fail for : -- > "+CardTypes.POSToSHOT.cardName);
	}
	
	@Test(groups = {"Sanity"}, dataProvider = "splitBannerDP", priority = 7)
	public void splitBannerValidationsTest(LgpSplitBannerPOJO productObject) throws IOException{
		
		String[] productPayload = {productObject.getAppId(), productObject.getRefId(), productObject.getType(), 
				productObject.getTitle(), productObject.getImageUrl(),productObject.getExtraData(),productObject.getTopics(),
				productObject.getIsPrivate(), productObject.getCreateAction(), productObject.getEnabled(),productObject.getIsSpam()};
		
		String payloadInString = getPayLoadForCard(CardPayloads.SPLIT_BANNER_CARD);
		String preparedPayload = apiUtil.preparepayload(payloadInString, productPayload);
		
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT, init.Configurations,preparedPayload);
        RequestGenerator request = new RequestGenerator(service, headers);
        String response = request.returnresponseasstring();
        
        System.out.println("============    Pumps Object Registeration Response for : ============== "+CardTypes.SPLITBANNER.cardName);
        System.out.println(response);
        System.out.println("=================================================================================================");
      
        Assert.assertEquals(request.response.getStatus(), 200, "Status Code Fail");
		Assert.assertEquals(JsonPath.read(response,"$.status.statusType"), "SUCCESS", "Status Type Fail");
		Assert.assertEquals(JsonPath.read(response,"$.status.statusMessage"), "Object added successfully", "Status Message Mis-match");
		
		String serveSideResponse = serveSideObjectParse(productObject.getAppId(), productObject.getRefId(), CardPayloads.SPLIT_BANNER_CARD.filename);
		String cardSchemaRetrieved = cardObjectHelper.getComponentCardSchema(CardPayloads.GENERIC_CARD.filename, versionSpecification);
		
		boolean componentCardValidationResult = mainComponentCardValidation(serveSideResponse);
		boolean schemaValidationResult = cardObjectHelper.componentCardSchemaValidation(serveSideResponse, cardSchemaRetrieved, versionSpecification);
		boolean componentValidationResult = componentValidationsForCardType(CardTypes.SPLITBANNER, versionSpecification, serveSideResponse);
		boolean cardKeyValidationResult = cardComponentsJsonKeyValidation(serveSideResponse);
		
		Assert.assertEquals(componentCardValidationResult, true, "Main Component Card Validation Failed for : -- >"+CardTypes.SPLITBANNER.cardName);
		Assert.assertEquals(schemaValidationResult, true, "Schema Validation Failed for : -- > "+CardTypes.SPLITBANNER.cardName);
		Assert.assertEquals(componentValidationResult, true,"Basic Component Validation Fail for : -- > "+CardTypes.SPLITBANNER.cardName);
		Assert.assertEquals(cardKeyValidationResult, true, "Key Validations Fail for : -- > "+CardTypes.SPLITBANNER.cardName);
	}
	
	@Test(groups = {"Sanity"}, dataProvider = "videoDP", priority = 7)
	public void videoValidationsTest(LgpVideoPOJO productObject) throws IOException{
		
		String[] productPayload = {productObject.getAppId(), productObject.getRefId(), productObject.getType(), 
				productObject.getTitle(), productObject.getDescription(), productObject.getUrl(), productObject.getImageUrl(),
				productObject.getExtraData(), productObject.getAuthor(),productObject.getTopics(), productObject.getProductRack(),
				productObject.getIsPrivate(), productObject.getCreateAction(), productObject.getEnabled(),productObject.getIsSpam()};
		
		String payloadInString = getPayLoadForCard(CardPayloads.VIDEO_CARD);
		String preparedPayload = apiUtil.preparepayload(payloadInString, productPayload);
		
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT, init.Configurations,preparedPayload);
        RequestGenerator request = new RequestGenerator(service, headers);
        String response = request.returnresponseasstring();
        
        System.out.println("============    Pumps Object Registeration Response for : ============== "+CardTypes.VIDEO.cardName);
        System.out.println(response);
        System.out.println("=================================================================================================");
      
        Assert.assertEquals(request.response.getStatus(), 200, "Status Code Fail");
		Assert.assertEquals(JsonPath.read(response,"$.status.statusType"), "SUCCESS", "Status Type Fail");
		Assert.assertEquals(JsonPath.read(response,"$.status.statusMessage"), "Object added successfully", "Status Message Mis-match");
		
		String serveSideResponse = serveSideObjectParse(productObject.getAppId(), productObject.getRefId(), CardPayloads.VIDEO_CARD.filename);
		String cardSchemaRetrieved = cardObjectHelper.getComponentCardSchema(CardPayloads.GENERIC_CARD.filename, versionSpecification);
		
		boolean componentCardValidationResult = mainComponentCardValidation(serveSideResponse);
		boolean schemaValidationResult = cardObjectHelper.componentCardSchemaValidation(serveSideResponse, cardSchemaRetrieved, versionSpecification);
		boolean componentValidationResult = componentValidationsForCardType(CardTypes.VIDEO, versionSpecification, serveSideResponse);
		boolean cardKeyValidationResult = cardComponentsJsonKeyValidation(serveSideResponse);
		
		Assert.assertEquals(componentCardValidationResult, true, "Main Component Card Validation Failed for : -- >"+CardTypes.VIDEO.cardName);
		Assert.assertEquals(schemaValidationResult, true, "Schema Validation Failed for : -- > "+CardTypes.VIDEO.cardName);
		Assert.assertEquals(componentValidationResult, true,"Basic Component Validation Fail for : -- > "+CardTypes.VIDEO.cardName);
		Assert.assertEquals(cardKeyValidationResult, true, "Key Validations Fail for : -- > "+CardTypes.VIDEO.cardName);
	}
	
}
















