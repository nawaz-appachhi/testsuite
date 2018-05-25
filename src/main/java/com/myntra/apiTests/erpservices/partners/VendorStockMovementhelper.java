package com.myntra.apiTests.erpservices.partners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.myntra.lordoftherings.boromir.DBUtilities;

public class VendorStockMovementhelper {
	
	private static Logger log = Logger.getLogger(VendorStockMovementhelper.class);

	public HashMap<String,String> getHeaders(){
		HashMap<String, String> Headers = new HashMap<String, String>();
		Headers.put("Content-Type", "application/json");
		Headers.put("accept", "application/json");
		Headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");
		return Headers;
	}
	
	public List<Integer> getRejectedItemIdfromWMSdb(long ro_id){
		   List<Map<String, Object>> rejected_item_id = (List<Map<String, Object>>) DBUtilities.exSelectQuery("select rejected_item_id from `ro_item` where ro_id= "+ro_id, "myntra_wms");
	        //String rejected_item_id = hm.get("rejected_item_id").toString();
		   log.debug("Printing Response from WMS database:\n"+rejected_item_id);
		   List<Integer> list = new ArrayList<>();
		   for(int i=0;i<rejected_item_id.size();i++){
			   list.add(Integer.valueOf( rejected_item_id.get(i).get("rejected_item_id").toString()));
		   }
	       
	      log.debug("Printing Rejected Item Ids from WMS database:\n"+list);
			return list;
	        
	}
	
	public List<Integer> getReturncodesByVendorIdfromWMSdb(int vendor_id){
		   List<Map<String, Object>> rejected_item_id = (List<Map<String, Object>>) DBUtilities.exSelectQuery("select id from `return_order` where vendor_id= "+vendor_id, "myntra_wms");
	        //String rejected_item_id = hm.get("rejected_item_id").toString();
		   log.debug("Printing Response from WMS database:\n"+rejected_item_id);
		   List<Integer> list = new ArrayList<>();
		   for(int i=0;i<rejected_item_id.size();i++){
			   list.add(Integer.valueOf( rejected_item_id.get(i).get("id").toString()));
		   }
	       
	      log.debug("Printing Rejected codes from WMS database:\n"+list);
			return list;
	        
	}
	
	public  List<Integer> getAPIReturnedItemIds(String response) throws Exception {
		JSONObject jsonObject = new JSONObject(response);
		JSONArray jsonArray=jsonObject.getJSONObject("roItemResponse").getJSONObject("data").getJSONArray("roItem");
		List<Integer> list=new ArrayList<>();
		for(int i=0;i<jsonArray.length();i++){
			 final JSONObject roItem = jsonArray.getJSONObject(i).getJSONObject("rejectedItem"); 
			 list.add(roItem.getInt("id"));
		}
		log.debug("Printing Rejected Item Ids from API Response:\n"+list);
		
		return list;
	}
	
	public  List<Integer> getAPIReturnCodes(String response) throws Exception {
		JSONObject jsonObject = new JSONObject(response);
		JSONArray jsonArray=jsonObject.getJSONObject("roResponse").getJSONObject("data").getJSONArray("ro");
		List<Integer> list=new ArrayList<>();
		for(int i=0;i<jsonArray.length();i++){
			 final JSONObject roItem = jsonArray.getJSONObject(i); 
			 list.add(roItem.getInt("id"));
		}
		log.debug("Printing Return Codes from API Response:\n"+list);
		
		return list;
	}
	
	public  int getROItemResponseStatusCode(String response) throws Exception {
		JSONObject jsonObject = new JSONObject(response);
		JSONObject status=jsonObject.getJSONObject("roItemResponse").getJSONObject("status");
		int statuscode=Integer.valueOf(status.get("statusCode").toString());
		
		log.debug("Printing Response code from API Response:\n"+status.get("statusCode"));
		
		return statuscode;
	}
	
	public  int getROResponseStatusCode(String response) throws Exception {
		JSONObject jsonObject = new JSONObject(response);
		JSONObject status=jsonObject.getJSONObject("roResponse").getJSONObject("status");
		int statuscode=Integer.valueOf(status.get("statusCode").toString());
		
		log.debug("Printing Response code from API Response:\n"+status.get("statusCode"));
		
		return statuscode;
	}
}
