package com.myntra.apiTests.portalservices.cms;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.portalservices.lgpservices.LgpServicesHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import edu.emory.mathcs.backport.java.util.Arrays;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class e2eRegressionCmsHelper {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(e2eRegressionCmsHelper.class);
	static APIUtilities apiUtil = new APIUtilities();
	static LgpServicesHelper lgpServiceHelper = new LgpServicesHelper();
	static APIUtilities utilities = new APIUtilities();
	String env = init.Configurations.GetTestEnvironemnt().toString();
	static String xid;
	public static String versionSpecification;
	public static HashMap<String, String> headers = new HashMap<String, String>();
	String username = System.getenv("username");
	String password = System.getenv("password");
	String singlestyle = System.getenv("singlestyle");
	String multiplestyle = System.getenv("multiplestyle");

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	public static String getFindByIdCMS(String singlestyle) {
		MyntraService service = Myntra.getService(ServiceType.CMS_CATALOG,
				APINAME.FINDBYID, init.Configurations, PayloadType.JSON,
				new String[] { singlestyle }, PayloadType.JSON);
		System.out.println(service.Payload);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		RequestGenerator req = new RequestGenerator(service, getParam);
		// RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println("response = " + req.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		String productId = req.respvalidate.NodeValue("data.productId", true);
		String title = req.respvalidate.NodeValue("data.title", true);
		return req.returnresponseasstring();
	}

	public static void getFindByIdsCMS(String multiplestyle) throws JSONException {
		RequestGenerator req = getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.FINDBYIDS, new String[] {multiplestyle});
		System.out.println("response = " + req.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		JSONArray jsonArray = getJSONArrayInsideData(req);
		AssertJUnit.assertEquals("Find by ids didn't give correct response", 3, jsonArray.length()); 
	}

	public static void getSearchForMultipleStylesCMS(String multiplestyle) throws JSONException {
		RequestGenerator req = getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.SEARCHFORSTYLES, new String[] {multiplestyle});
		System.out.println("response = " + req.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		JSONArray jsonArray = getJSONArrayInsideData(req);
		AssertJUnit.assertEquals("Search multiple ids didn't give correct response", 3, jsonArray.length()); 
	}

	public static void getSearchForSingleStylesCMS(String singlestyle) {
		MyntraService service = Myntra.getService(
				ServiceType.CMS_CATALOG, APINAME.SEARCHFORSTYLE,
				init.Configurations, PayloadType.JSON,
				new String[] { singlestyle }, PayloadType.JSON);
		System.out.println(service.Payload);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		RequestGenerator req = new RequestGenerator(service, getParam);
		// RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println("response = " + req.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
	}
	
	public static String getNodeValue(RequestGenerator req, String path, Boolean flag){
		String nodeValue = null;
		try{
			nodeValue = req.respvalidate.NodeValue(path, flag);
		}
		catch(NullPointerException e){
			e.printStackTrace();
		}
		return nodeValue;
	}
	
	public static JSONArray getJSONArrayInsideData(RequestGenerator req) throws JSONException {
		String response = req.returnresponseasstring();
		JSONObject jsonObj = new JSONObject(response);
		JSONArray jsonArray = (JSONArray) jsonObj.get("data");
		return jsonArray;
	}
	
	public static RequestGenerator getRequestObjForCMSForQueryParam(ServiceType serviceType, APINAME apiName, String [] params){
		MyntraService service = Myntra.getService(serviceType,
				apiName, init.Configurations, PayloadType.JSON,
				params, PayloadType.JSON);
		log.info("Payload "+service.Payload);
		log.info("URL "+service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		return req;
	}
	
	public static RequestGenerator getRequestObjForCMSForPayloadAndQueryParam(APINAME apiName, String [] PayloadParams, String[] urlparams){
		MyntraService service = Myntra.getService(ServiceType.CMS_CATALOG, apiName, init.Configurations, PayloadParams, urlparams);
		log.info("Payload "+service.Payload);
		log.info("URL "+service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		return req;
	}
	
	/** Returns json nodes with paths. Pass withId=true for /style/<id> or /v2/style/<id> api**/
	public static HashMap<String, Object> getAllJSONNodesWithPaths(JSONObject parent, String path, HashMap<String, Object> pureKeyValNodeHM, boolean withId) throws JSONException{
		Iterator itr = parent.keys();
		while(itr.hasNext()){
			String key = (String) itr.next();
			if(parent.optJSONArray(key) == null && parent.optJSONObject(key) == null){
				if(withId){
					if(!key.contains("code") && !key.contains("attributeCode")){
						pureKeyValNodeHM.put(path+"."+key, parent.get(key));
					}
				}
				else{
					pureKeyValNodeHM.put(path+"."+key, parent.get(key));
				}
			}
			
			if(parent.optJSONArray(key) != null){
				if(parent.getJSONArray(key).length()!=0){
					if(parent.getJSONArray(key).optJSONObject(0) != null)
						for(int i=0; i<parent.getJSONArray(key).length(); i++){
							if(withId){
								String id = "";
								if(key.equals("productOptionEntries"))
									id = String.valueOf(parent.getJSONArray(key).getJSONObject(i).get("skuId"));
								else if(key.equals("attributeValues"))
									id = parent.getJSONArray(key).getJSONObject(i).getString("attributeValue");
								else
									id = String.valueOf(parent.getJSONArray(key).getJSONObject(i).get("id"));
								getAllJSONNodesWithPaths(parent.getJSONArray(key).getJSONObject(i), path+"."+key+"."+id, pureKeyValNodeHM, withId);
							}
							else
								getAllJSONNodesWithPaths(parent.getJSONArray(key).getJSONObject(i), path+"."+key+"["+i+"]", pureKeyValNodeHM, withId);
						}
					else
						for(int i=0;i<parent.getJSONArray(key).length();i++){
							pureKeyValNodeHM.put(path+"."+key+"["+i+"]", parent.getJSONArray(key).get(i));
						}
				}else
					pureKeyValNodeHM.put(path+"."+key, "");
			}
			
			if(parent.optJSONObject(key) != null){
				getAllJSONNodesWithPaths(parent.getJSONObject(key), path+"."+key, pureKeyValNodeHM, withId);
			}
		}
		return pureKeyValNodeHM;
	}
	
	/** Pass requests from jsons to compare 
	 * Pass withId=true for /style/<id> or /v2/style/<id> api**/
	public static Diff returnJsonDiff(RequestGenerator req1, RequestGenerator req2, boolean withId) throws JSONException {
		JSONArray jsonArray1 = e2eRegressionCmsHelper.getJSONArrayInsideData(req1);
		JSONArray jsonArray2 = e2eRegressionCmsHelper.getJSONArrayInsideData(req2);
		
		HashMap<String, Object> global1 = new HashMap<String, Object>();
		HashMap<String, Object> global2 = new HashMap<String, Object>();
		
		HashMap<String, Object> hm1 = e2eRegressionCmsHelper.getAllJSONNodesWithPaths(jsonArray1.getJSONObject(0), "data", global1, withId);
		HashMap<String, Object> hm2 = e2eRegressionCmsHelper.getAllJSONNodesWithPaths(jsonArray2.getJSONObject(0), "data", global2, withId);
		
		Javers javers = JaversBuilder.javers().build();
		Diff diff = javers.compare(hm1, hm2);
		
		return diff;
	}
	
	/** First 19 params passed in payload_params from test are global attributes **/
	public static void validateGlobalAttributesStyleAPI(RequestGenerator req, String[] payload_params, String articleTypeCode){
		String response = req.returnresponseasstring();
		AssertJUnit.assertTrue(articleTypeCode+" :  description Global attributes not set correctly",payload_params[0].equals(JsonPath.read(response, "$.data[0].description").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : price Global attributes not set correctly",payload_params[1].equals(JsonPath.read(response, "$.data[0].price").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : stylestatus Global attributes not set correctly",payload_params[2].equals(JsonPath.read(response, "$.data[0].styleStatus").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : productDisplayName Global attributes not set correctly",payload_params[3].equals(JsonPath.read(response, "$.data[0].productDisplayName").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : styleNote Global attributes not set correctly",payload_params[4].equals(JsonPath.read(response, "$.data[0].styleNote").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : materialCareDescription Global attributes not set correctly",payload_params[5].equals(JsonPath.read(response, "$.data[0].materialCareDescription").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : sizeFitDescription Global attributes not set correctly",payload_params[6].equals(JsonPath.read(response, "$.data[0].sizeFitDescription").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : comments Global attributes not set correctly",payload_params[7].equals(JsonPath.read(response, "$.data[0].comments").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : listViewName Global attributes not set correctly",payload_params[8].equals(JsonPath.read(response, "$.data[0].listViewName").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : baseColour Global attributes not set correctly",payload_params[9].equals(JsonPath.read(response, "$.data[0].globalAttributes.brandName").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : season Global attributes not set correctly",payload_params[10].equals(JsonPath.read(response, "$.data[0].globalAttributes.ageGroup").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : usage Global attributes not set correctly",payload_params[11].equals(JsonPath.read(response, "$.data[0].globalAttributes.gender").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : gender Global attributes not set correctly",payload_params[12].equals(JsonPath.read(response, "$.data[0].globalAttributes.baseColour").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : year Global attributes not set correctly",payload_params[13].equals(JsonPath.read(response, "$.data[0].globalAttributes.colour1").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : colour2 Global attributes not set correctly",payload_params[14].equals(JsonPath.read(response, "$.data[0].globalAttributes.colour2").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : brandName Global attributes not set correctly",payload_params[15].equals(JsonPath.read(response, "$.data[0].globalAttributes.fashionType").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : colour1 Global attributes not set correctly",payload_params[16].equals(JsonPath.read(response, "$.data[0].globalAttributes.season").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : ageGroup Global attributes not set correctly",payload_params[17].equals(JsonPath.read(response, "$.data[0].globalAttributes.year").toString()));
		AssertJUnit.assertTrue(articleTypeCode+" : fashionType Global attributes not set correctly",payload_params[18].equals(JsonPath.read(response, "$.data[0].globalAttributes.usage").toString()));
	}
	
	/** Extract attribute values from attr ids passed in payload_params using productcategory api
	 * Compare with attribute values got in response of style api
	 * @param req
	 * @param payload_params
	 * @param articleTypeCode
	 */
	public static void validateATSAStyleAPI(RequestGenerator req, String[] payload_params, String articleTypeCode){
		List<String> attributeIdStored = JsonPath.read(req.returnresponseasstring(), "$.data[0].attributeValues[*].id");
		List<String> attributeIdString = new ArrayList<String>();
		for(int i=0;i<attributeIdStored.size();i++){
			try{
				String id = JsonPath.read(req.returnresponseasstring(), "$.data[0].attributeValues["+i+"].id").toString();
				attributeIdString.add(id);
			}
			catch(NullPointerException e){
				e.printStackTrace();
			}
		}
		ArrayList<String> payloadValue = new ArrayList<String>(Arrays.asList(payload_params).subList(20, payload_params.length-2));
		if(!attributeIdString.isEmpty()){
			payloadValue.removeAll(attributeIdString);
			AssertJUnit.assertTrue(articleTypeCode+" : Some values didn't get set "+payloadValue, payloadValue.isEmpty());
		}
		else{
			Assert.fail(articleTypeCode+" : Style response returned empty attributeValues[] array for article type code: "+articleTypeCode);
		}
	}
	
	/** Extract ATSA values and code from response
	 * Match if the code returned is correct for the attr value
	 * @param req
	 */
	public static void validateATSACodeWithValue(RequestGenerator req, String articleTypeCode){
		List<String> attValList = JsonPath.read(req.returnresponseasstring(), "$.data[0].attributeValues[*].attributeValue");
		for(int i =0;i<attValList.size();i++){
			String attVal= JsonPath.read(req.returnresponseasstring(), "$.data[0].attributeValues["+i+"].attributeValue");
			String attCode= JsonPath.read(req.returnresponseasstring(), "$.data[0].attributeValues["+i+"].attributeCode");
			String code= JsonPath.read(req.returnresponseasstring(), "$.data[0].attributeValues["+i+"].code");
			AssertJUnit.assertEquals(articleTypeCode+" : attributeCode mismatch "+attCode, attVal.toUpperCase().replace(" ", "_"), attCode);
			AssertJUnit.assertEquals(articleTypeCode+" : code mismatch "+code, attVal.toUpperCase().replace(" ", "_"), code);
		}
	}
	
	public static String[] udpateArticleType(APINAME apiname, String styleId, String article_id, String articleName){
		RequestGenerator reqOldAfterUpdate = e2eRegressionCmsHelper
				.getRequestObjForCMSForPayloadAndQueryParam(apiname, new String[] {article_id, articleName, styleId},
						new String[] { styleId });
		RequestGenerator reqNewAfterUpdate = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSGETSTYLE,
						new String[] { styleId });
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.FINDBYID,
						new String[] { styleId });
		return new String[] {reqOldAfterUpdate.returnresponseasstring(), reqNewAfterUpdate.returnresponseasstring(), req.returnresponseasstring()};
		
	}
	
	public static void assertArticleTypeChange(String[] responses, String article_id, String articleName, String parent1Name, String parent2Name){
		AssertJUnit.assertEquals(article_id+ " : Article type didn't get changed in old style api", article_id, JsonPath.read(responses[0], "$.data[0].global_attr_article_type").toString());
		AssertJUnit.assertEquals(article_id+ " : Article type didn't get changed in old style api", articleName, JsonPath.read(responses[0], "$.data[0].global_attr_article_type_name").toString());
		AssertJUnit.assertEquals(article_id+ " : Article type didn't get changed in old style api", parent1Name, JsonPath.read(responses[0], "$.data[0].parent1Name").toString());
		AssertJUnit.assertEquals(article_id+ " : Article type didn't get changed in old style api", parent2Name, JsonPath.read(responses[0], "$.data[0].parent2Name").toString());
		
		AssertJUnit.assertEquals(article_id+ " : Article type didn't get changed in new style api", article_id, JsonPath.read(responses[1], "$.data[0].global_attr_article_type").toString());
		AssertJUnit.assertEquals(article_id+ " : Article type didn't get changed in new style api", articleName, JsonPath.read(responses[1], "$.data[0].global_attr_article_type_name").toString());
		AssertJUnit.assertEquals(article_id+ " : Article type didn't get changed in new style api", parent1Name, JsonPath.read(responses[1], "$.data[0].parent1Name").toString());
		AssertJUnit.assertEquals(article_id+ " : Article type didn't get changed in new style api", parent2Name, JsonPath.read(responses[1], "$.data[0].parent2Name").toString());
		
		AssertJUnit.assertEquals(article_id+ " : Article type didn't get changed in product api", article_id, JsonPath.read(responses[2], "$.data[0].articleType.id").toString());
		AssertJUnit.assertEquals(article_id+ " : Article type didn't get changed in product api", articleName, JsonPath.read(responses[2], "$.data[0].articleType.typeName").toString());
		AssertJUnit.assertEquals(article_id+ " : Article type didn't get changed in product api", parent1Name, JsonPath.read(responses[2], "$.data[0].subCatagory.typeName").toString());
		AssertJUnit.assertEquals(article_id+ " : Article type didn't get changed in product api", parent2Name, JsonPath.read(responses[2], "$.data[0].masterCatagory.typeName").toString());
	}
	
	public static void sleep(long time) {
		try {
			log.info("Sleeping for Time " + time);
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
