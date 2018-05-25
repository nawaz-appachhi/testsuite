package com.myntra.apiTests.portalservices.lgpservices;

import org.json.JSONException;

import java.util.Iterator;

import org.codehaus.jettison.json.JSONObject;


public class Testing {

	public static void main(String[] args) throws JSONException, org.codehaus.jettison.json.JSONException {
		String json ="{\n\n  \"user\": {\n    \"randomUsers\":1\n  },\n\"user\": {\n    \"randomUsers\":1\n  },\n\"user\": {\n    \"randomUsers\":1\n  },\n  \n  \"pumps\": {\n    \"followTopic\": {\n      \"payload\": [\n        {\n          \"whom\": \"occasion:casual\",\n          \"whomType\": \"Tag\"\n        }\n      ]\n    },\n    \"registerObject\": {\n      \"objects\": [\n        {\n          \"count\": 3,\n          \"payload\": {\n            \"type\": \"article\",\n            \"contentType\": \"customTwo\",\n            \"title\": \"Still in love with these?\",\n            \"description\": \"Don\'t let it get away.\",\n            \"url\": \"http://www.myntra.com/cart\",\n            \"imageUrl\": \"http://assets.myntassets.com/h_240,q_100,w_180/v1/image/style/properties/916996/RANGMANCH-BY-PANTALOONS-Women-Kurtas_1_086ca36b57a50e83e1adf99453fc55c0.jpg\",\n            \"extraData\": \"{\\\"aspectRatio\\\":\\\"1:1\\\" }\",\n            \"topics\": [\n              {\n                \"id\": 3806,\n                \"name\": \"casual\",\n                \"type\": \"occasion\",\n                \"displayName\": \"casual\"\n              }\n            ],\n            \"productRack\": \"{\\\"productIds\\\":[\\\"339187\\\",\\\"209125\\\",\\\"288501\\\"]}\",\n            \"isPrivate\": false,\n            \"createAction\": \"register\",\n            \"enabled\": true,\n            \"isSpam\": false\n          }\n        }\n      ]\n    },\n    \"publishObject\": {\n      \"objects\": [\n        {\n          \"objectIds\": \"unpublished\",\n          \"count\": 3,\n          \"payload\": {\n            \"whomType\": \"Object\",\n            \"userMessage\": \"Check out this article!\",\n            \"channels\": [\n              {\n                \"name\": \"andriod\"\n              },\n              {\n                \"name\": \"ios\"\n              }\n            ],\n            \"persona\": \"men\",\n            \"extraData\": \"{\\\"credits\\\":\\\"500\\\" , \\\"boost\\\":\\\"400\\\", \\\"platform\\\" : \\\"android\\\"}\",\n            \"target\": {\n              \"userTargettingType\": \"EVERYONE\",\n              \"segmentQuery\": \"\",\n              \"allowUsers\": \"\",\n              \"allowUsersUrl\": \"\",\n              \"denyUsers\": \"\",\n              \"denyUsersUrl\": \"\"\n            }\n          }\n        }\n      ]\n    }\n  },\n  \"dataStoreAssertions\": {},\n  \"serveAssertions\": {\n  \t\"scenarioType\": \"FollowTopic\",\n    \"platforms\": [\n      \"ios\",\n      \"android\"\n    ],\n    \"validationPoints\": [\n      {\n        \"clientEndpoint\": \"Feeds\"\n      },\n      {\n        \"clientEndpoint\": \"Streams\"\n      }\n    ]\n  }\n}";
		JSONObject jsonObject = new JSONObject(json);
		Iterator<String> iterator = jsonObject.keys();
		while(iterator.hasNext()){
			String key = iterator.next();
			System.out.println(key);
		}

	}

}
