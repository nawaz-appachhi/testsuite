package com.myntra.apiTests.portalservices.all;

import com.myntra.apiTests.dataproviders.ContentSearchDP;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;

public class ContentSearchTests extends ContentSearchDP {
	static Logger log = Logger.getLogger(ContentSearchTests.class);
	APIUtilities api = new APIUtilities();
	static Initialize init = new Initialize("/Data/configuration");
	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "addContentDP")
	public void addContentPostiveScenarios(String payloadParam)
	{
		System.out.println(getPayload(payloadParam));
		
	}
	
	private String getPayload(String paramFromDp){
		StringBuffer sb = new StringBuffer();
		sb.append("{\n");
		String[] keyValuePairs = paramFromDp.split(";");
		for(String keyValue : keyValuePairs){
			String keyValueCombo = "";
			String[] jsonLikeKeyAndValue = keyValue.split("\\|");
			if(jsonLikeKeyAndValue[0].equalsIgnoreCase("tags") || jsonLikeKeyAndValue[0].equalsIgnoreCase("persona_tags")){
				String[] splittedTags = jsonLikeKeyAndValue[1].split(":");
				String commaSepTags = "";
				for(String tags : splittedTags){
					commaSepTags = commaSepTags + tags +",";
				}
				commaSepTags = "["+commaSepTags.substring(0, commaSepTags.length()-1)+"],";
				keyValueCombo = "\""+jsonLikeKeyAndValue[0]+"\" :"+commaSepTags+"\n";
			}else if(jsonLikeKeyAndValue[0].equalsIgnoreCase("is_published") || jsonLikeKeyAndValue[0].equalsIgnoreCase("published")){
				keyValueCombo = "\""+jsonLikeKeyAndValue[0]+"\" : "+jsonLikeKeyAndValue[1]+",\n";
			}else{
				keyValueCombo = "\""+jsonLikeKeyAndValue[0]+"\" : \""+jsonLikeKeyAndValue[1]+"\",\n";
				
			}
			sb.append(keyValueCombo);
		}
		String Payload = sb.substring(0, sb.lastIndexOf(",")) + "\n }";
		return Payload;
	}
}
