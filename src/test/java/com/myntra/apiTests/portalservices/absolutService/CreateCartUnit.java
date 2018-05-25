package com.myntra.apiTests.portalservices.absolutService;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.concurrent.Callable;

@Data
@AllArgsConstructor
public class CreateCartUnit implements Callable<Boolean>{

	private String uidx;
    private String styleId;
    private String skuId;
    private int qty;
    private String newSkuId;
    private String method;
    
	private APIUtilities utilities;
	private End2EndHelper end2EndHelper;
	private String QUEUE_NAME = "cart-123";


    public CreateCartUnit(String uidx, String styleId, String skuId, int qty,String newSkuId, String method) {
		this.uidx = uidx;
		this.styleId = styleId;
		this.skuId = skuId;
		this.qty = qty;
		this.method = method;
		this.newSkuId = newSkuId;
	}

	@Override
    public Boolean call() {
	String payload = null;
    	switch(method) {
    	case "add":
    		Object[] strPayload = {uidx, styleId, skuId, qty};
    		try {
    			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/addcartmessage");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		payload = utilities.preparepayload(payload, strPayload);
    		break;
	
    	case "remove":
    		Object[] strPayload1 = {uidx, styleId, skuId};
    		try {
    			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/removecartmessage");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		payload =  utilities.preparepayload(payload, strPayload1);
    		break;
    	
    case "delete":
    		Object[] strPayload2 = {uidx};
    		try {
    			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/deletecartmessage");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		payload =  utilities.preparepayload(payload, strPayload2);
    		break;
    case "update":
		Object[] strPayload3 = {uidx,styleId, skuId,qty, newSkuId};
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/updatecartmessage");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payload =  utilities.preparepayload(payload, strPayload3);
		break;
    default: 
		payload = "";
    	}
    	end2EndHelper.pushMessageToQueue(payload, QUEUE_NAME);
		return true;
	}
}
