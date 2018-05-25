package com.myntra.apiTests.portalservices.lgpservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.myntra.apiTests.portalservices.lgpServe.feed.FeedObjectHelper;
import org.apache.commons.lang.ArrayUtils;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

import net.minidev.json.JSONArray;

public class LgpFeedObjectIndividualHelper extends FeedObjectHelper {
	
	
	
	public String serveRequest(String objectIdReference, String versionSpecification, String caseValue){
		
		
		RequestGenerator request = getQueryRequestMultiParam(APINAME.GETFEEDOBJECTENTITY, objectIdReference, versionSpecification);
		String response = request.respvalidate.returnresponseasstring();
		
		System.out.println("LGP-Serve response for ---- > "+caseValue);
		System.out.println(response);
		System.out.println("=============== END ===================");
		
		return response;
	}
	

	protected String caseValueIdentifier(HashMap<String,HashMap<String,String[]>> mainMap) {
		
		String caseValue = null;
		for(Entry<String, HashMap<String, String[]>> mainMapEntrySet : mainMap.entrySet()){
			
			String mainMapKey = mainMapEntrySet.getKey();
			String[] mainMapKeyArray = mainMapKey.split("==");
			caseValue = mainMapKeyArray[1];
		}
		return caseValue;
	}
	
	protected String objectValueIdentifier(HashMap<String,HashMap<String,String[]>> mainMap){
		
		
		String objectValue = null;
		for(Entry<String, HashMap<String, String[]>> mainMapEntrySet : mainMap.entrySet()){
			
			String mainMapKey = mainMapEntrySet.getKey();
			String[] mainMapKeyArray = mainMapKey.split("==");
			objectValue = mainMapKeyArray[0];
		}
		
		
		return objectValue;
	}
	
	protected String[] getInnerList(HashMap<String,HashMap<String,String[]>> mainMap){
		
		String[] toValidateList = null;
		
		for(Entry<String, HashMap<String, String[]>> mainMapEntrySet : mainMap.entrySet()){
			
			HashMap<String,String[]>  innerMap =  mainMapEntrySet.getValue();
			toValidateList = innerMap.get("toValidateList");
		}
		
		return toValidateList;
	}

	protected String[] getJsonKeyList(HashMap<String,HashMap<String,String[]>> mainMap){
		
		String[] toValidateJsonKeyList = null;
		
		for(Entry<String, HashMap<String, String[]>> mainMapEntrySet : mainMap.entrySet()){
			
			HashMap<String,String[]>  innerMap =  mainMapEntrySet.getValue();
			toValidateJsonKeyList = innerMap.get("toJsonKeyValidationList");
		}
		
		return toValidateJsonKeyList;
	}
	
	public boolean cardComponentsValidationProcess(String[] feedCardComponents, String jsonResponse) {

		boolean result = false;
		ArrayList<String> referenceList = new ArrayList<String>();
		JSONArray components = JsonPath.read(jsonResponse, "$.cards[*].children[*].type");
		
		if(components.toArray().length == feedCardComponents.length){
			
			referenceList.add("true");
			
			for (int i = 0; i < feedCardComponents.length; i++) {
				if (ArrayUtils.contains(components.toArray(), feedCardComponents[i])) {
					referenceList.add(feedCardComponents[i] + "-true");
				} else {
					referenceList.add(feedCardComponents[i] + "-false");
				}
			}
			
		}else{
		    
			printList(components.toArray(),"Components Provided by the JsonResponse");
			printList(feedCardComponents, "Components Provided by the Data Provider");
			referenceList.add("false");
		}
		
		System.out.println("Card Components Validations--------------> " + referenceList);
		result = checkFinalResult(referenceList);
		return result;

	}
	
	public void printList(Object[] compList, String source){
		
		System.out.println("---------- "+source+" --------------");
		
		for(int compListIndex = 0; compListIndex < compList.length; compListIndex++ ){
			
			System.out.println(compList[compListIndex]);
			
		}
		
		System.out.println("--------------- End ----------------");
		
	}
	
	public boolean mainComponentCardValidation(String jsonResponse,String versionSpecification){
		
		boolean componentCardValidationResult = false;
		
		String modifiedResponse = JsonPath.read(jsonResponse, "$.cards[0]").toString();
		String basicComponentCard = JsonPath.read(jsonResponse, "$.cards[0].type").toString();
		
		componentCardValidationResult = jsonKeyValidationsForComponents(basicComponentCard, versionSpecification, modifiedResponse, 0, "CardObjectIndividualTests");
		
		return componentCardValidationResult;
	}
	
	public boolean jsonKeyValidationForCardComponentList(String[] componentList,String versionSpecification, String jsonResponse){
		
		boolean cardComponentListResult = false;
		ArrayList<String> cardComponentList = new ArrayList<String>();
		String modifiedCardResponse  = JsonPath.read(jsonResponse,"$.cards[0]").toString();
		
		JSONArray componentsFromModifiedResponse = JsonPath.read(modifiedCardResponse, "$.children[*].type");
		
		if(componentsFromModifiedResponse.toArray().length == componentList.length){
			
			for(int componentListIndex = 0; componentListIndex < componentList.length; componentListIndex++ ){
				
				if(ArrayUtils.contains(componentsFromModifiedResponse.toArray(), componentList[componentListIndex])){
					
					String componentResult = String.valueOf(jsonKeyValidationsForComponents(componentList[componentListIndex], versionSpecification, modifiedCardResponse, componentListIndex, "CardObjectIndividualTests"));
					cardComponentList.add(componentResult);
				} 
				
				else {
					
					cardComponentList.add(componentList[componentListIndex]+"-false");
					
				}
					
			}
			
		}else{
			
			printList(componentsFromModifiedResponse.toArray(),"Components Provided by the JsonResponse");
			printList(componentList,"Components Provided by the Data Provider");
			cardComponentList.add("false");
		}
		cardComponentListResult = checkFinalResult(cardComponentList);
		return cardComponentListResult;
	}

	private boolean checkFinalResult(ArrayList<String> referenceList) {

		for (int referenceListIndex = 0; referenceListIndex < referenceList.size(); referenceListIndex++) {
			if (referenceList.get(referenceListIndex).contains("false")) {
				return false;
			}
		}
		return true;
	}

}
