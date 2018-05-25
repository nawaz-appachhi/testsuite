package com.myntra.apiTests.inbound.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.erpservices.Constants;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;

public class CrawlerHelper {
	
	private HashMap<String,String> getAmazonHeader() {
    	HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Content-Type", "Application/json");
		createOrderHeaders.put("Accept", "Application/json");
		return createOrderHeaders;
    }
	
	public String getAmazonList() throws JAXBException, IOException {
		Svc service = HttpExecutorService.executeHttpService(Constants.AMAZON_PATH.GET_TSHIRTS_LIST, null, SERVICE_TYPE.AMAZON_SVC.toString(), HTTPMethods.GET, null, getAmazonHeader());
		return service.getResponseBody();
	}
	
	public HashMap<String, Integer> checkJsonKeyIsAvailable(String key, boolean elementExist,
			HashMap<String, Integer> map) {
		if (elementExist) {
			if (map.get(key) != null)
				map.put(key, map.get(key) + 1);
			else
				map.put(key, 1);
		}
		else {
			if (map.get(key) != null){			
				}			
			else
				map.put(key, 0);
		}
		return map;
	}
	
	public String calculateElementAvailabilityPercentage(HashMap<String, Integer> map, int totalPDPItems, String failedElements ){
		for(Map.Entry<String, Integer> c:map.entrySet()){
			System.out.println(c.getKey()+" :  "+c.getValue());
			int elementPercent=(c.getValue()*100)/totalPDPItems;
			 if(elementPercent<80){
				 failedElements=failedElements+ c.getKey()+": Not Found"+"\n";
			 }
			
		}
		return failedElements;
		
	}
	
	
	

}
