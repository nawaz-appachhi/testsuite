package com.myntra.apiTests.portalservices.payments;

import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.AssertJUnit;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class PaymentsServiceHelper {


    Document doc;

    static Initialize init = new Initialize("/Data/configuration");
    static Logger log = LoggerFactory.getLogger(PaymentsServiceHelper.class);

    


    String tenantId="jabong";


    public String getPPSId(String orderID) {
        Map<String, Object> payment_planTable = DBUtilities.exSelectQueryForSingleRecord("select id from payment_plan where orderid = " + orderID, "pps");
        return (String) payment_planTable.get("id");
    }
    
    
   
	public static String getItemPayload(String paymentPlanItems) throws JSONException {
		JSONArray jsonArray= new JSONArray(paymentPlanItems);
		StringBuffer payload=new StringBuffer();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject=new JSONObject(jsonArray.get(i).toString());
			/*{
			     "type":"VAT_ADJ_REFUND",
			     "itemIdentifier":"3834",
			     "quantity":"1",
			     "instrAmount":"2000"
			 }*/
			payload=payload.append("{");
			payload.append("\"type\":\""+jsonObject.getString("itemType")+"\",");
			payload.append("\"itemIdentifier\":\""+jsonObject.getString("skuId")+"\",");
			payload.append("\"quantity\":\""+jsonObject.getInt("quantity")+"\",");
			payload.append("\"instrAmount\":\""+jsonObject.getInt("pricePerUnit")+"\"");
			payload=payload.append("}");
			if(i+1<jsonArray.length())
				payload=payload.append(",");
			//System.out.println("Sku id is : "+((JSONArray) jsonArray.get(i)).get("skuId"));
		}
		System.out.println("Payload is :" + payload.toString());
		return payload.toString();
	}
	
	public String getEncodedTokenFromPaymentPage(String xid, String sxid, String host,String referer) {
		String encodedcsrf = null;
		String value_add = "fox-xid="+xid +"; fox-sxid="+sxid;
		System.out.println("value1:"+value_add);
    		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    		HashMap<String,String>hmp = new HashMap<String,String>();
    		hmp.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:51.0) Gecko/20100101 Firefox/51.0");
    		hmp.put("Host", host);
    		hmp.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    		hmp.put("Accept-Language", "en-US,en;q=0.5");
    		hmp.put("Cookie", value_add);
    		hmp.put("Referer", referer);

		MyntraService service_payment = Myntra.getService(ServiceType.PORTAL_CHECKOUT, APINAME.GETPAYMENTPAGE, init.Configurations,PayloadType.XML,PayloadType.HTML);
		System.out.println(service_payment.URL);
        RequestGenerator req_payment = new RequestGenerator (service_payment,hmp);
        String response1 = req_payment.respvalidate.returnresponseasstring();

        Document doc = Jsoup.parseBodyFragment(response1);
        Element body = doc.body();
        
        Element input = body.select("input[name=csrf_token]").first();
        System.out.println("csrfToken="+input.attr("value"));
        String xyz = input.attr("value").toString();
        System.out.println("csrf token mod ="+xyz);
        String abc = xyz.replaceAll("\n", "").trim();
        System.out.println("final csrf token="+abc);
        System.out.println("xid="+xid);
        try {
        	encodedcsrf = URLEncoder.encode(abc, "UTF-8");
        } catch (UnsupportedEncodingException ignored) {
            // Can be safely ignored because UTF-8 is always supported
        }
        System.out.println("encoded csrf token="+encodedcsrf);
		return encodedcsrf;
	}

	
	public String getEncodedTokenFromPaymentPageForGiftcard(String xid, String sxid, String host,String referer) {
		String encodedcsrf = null;
		String value_add = "fox-xid="+xid +"; fox-sxid="+sxid;
    	System.out.println("value1:"+value_add);
    	System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    	HashMap<String,String>hmp = new HashMap<String,String>();
        hmp.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:51.0) Gecko/20100101 Firefox/51.0");
    	hmp.put("Host", host);
    	hmp.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    	hmp.put("Accept-Language", "en-US,en;q=0.5");
    	hmp.put("Cookie", value_add);
    	hmp.put("Referer", referer);

		MyntraService service_payment = Myntra.getService(ServiceType.PORTAL_CHECKOUT, APINAME.GETEGIFTCARDPAYMENTPAGE, init.Configurations,PayloadType.XML,PayloadType.HTML);
		System.out.println(service_payment.URL);
        RequestGenerator req_payment = new RequestGenerator (service_payment,hmp);
        String response1 = req_payment.respvalidate.returnresponseasstring();

        Document doc = Jsoup.parseBodyFragment(response1);
        Element body = doc.body();
        
        Element input = body.select("input[name=csrf_token]").first();
        System.out.println("csrfToken="+input.attr("value"));
        String xyz = input.attr("value").toString();
        System.out.println("csrf token mod ="+xyz);
        String abc = xyz.replaceAll("\n", "").trim();
        System.out.println("final csrf token="+abc);
        System.out.println("xid="+xid);
        try {
        	encodedcsrf = URLEncoder.encode(abc, "UTF-8");
        } catch (UnsupportedEncodingException ignored) {
            // Can be safely ignored because UTF-8 is always supported
        }
        System.out.println("encoded csrf token="+encodedcsrf);
		return encodedcsrf;
	}
	
	

	public String getPaynowResponse(String xid, String host, String encodedcsrf, String payload) {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        HashMap<String,String>hm = new HashMap<String,String>();
        String value = "fox-xid="+xid;
        hm.put("cookie", value);
        hm.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        hm.put("Accept-Language", "en-US,en;q=0.5");
        hm.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:50.0) Gecko/20100101 Firefox/50.0");
        hm.put("Host", host);
        hm.put("Content-Type", "application/x-www-form-urlencoded ; charset=UTF-8");
        hm.put("csrf_token", encodedcsrf);

        MyntraService service_PayNowForm = Myntra.getService(ServiceType.PORTAL_PPS, APINAME.PAYNOWFORM, init.Configurations,
				new String[] { payload }, PayloadType.URLENCODED, PayloadType.HTML);
		RequestGenerator req2 = new RequestGenerator (service_PayNowForm,hm);
		return(req2.respvalidate.returnresponseasstring());
	}


	public String getPGResponse(String xid, String sxid, String host, String refere,String cardInfoPayload) {

		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        HashMap<String,String>hmx = new HashMap<String,String>();
        String valueCard = "fox-xid="+xid;
        hmx.put("cookie", valueCard);
        hmx.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        hmx.put("Accept-Language", "en-US,en;q=0.5");
        hmx.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:50.0) Gecko/20100101 Firefox/50.0");
        hmx.put("Host", host);
        hmx.put("Content-Type", "application/x-www-form-urlencoded ; charset=UTF-8");
        hmx.put("Refere", refere);
        hmx.put("csrf_token", sxid);

    	MyntraService serviceCard = Myntra.getService(ServiceType.PORTAL_PPS, APINAME.PGRESPONSE, init.Configurations,
				new String[] { cardInfoPayload }, PayloadType.URLENCODED, PayloadType.HTML);
		RequestGenerator reqCard = new RequestGenerator (serviceCard,hmx);
		return(reqCard.respvalidate.returnresponseasstring());
	}
	
	
	public String getPayuPGResponse(String xid, String sxid, String host, String refere,String cardInfoPayload) {

		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        HashMap<String,String>hmx = new HashMap<String,String>();
        String valueCard = "fox-xid="+xid;
        hmx.put("cookie", valueCard);
        hmx.put("Origin", "http://payments-pps.dockins.myntra.com");
        hmx.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        hmx.put("Accept-Language", "en-US,en;q=0.5");
        hmx.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:50.0) Gecko/20100101 Firefox/50.0");
        //hmx.put("Accept-Encoding", "gzip, deflate, br");
        hmx.put("Content-Type", "application/x-www-form-urlencoded ; charset=UTF-8");
        hmx.put("Refere", refere);
        hmx.put("Upgrade-Insecure-Requests", "1");
        MyntraService serviceCard = Myntra.getService(ServiceType.PORTAL_PAYU, APINAME.PAYUPGRESPONSE, init.Configurations,
				new String[] { cardInfoPayload }, PayloadType.URLENCODED, PayloadType.XML);
        System.out.println("URL IS IS : "+serviceCard.URL);
        System.out.println("The payload is : "+ serviceCard.Payload);
		RequestGenerator reqCard = new RequestGenerator (serviceCard,hmx);
		return(reqCard.respvalidate.returnresponseasstring());
	}
	

	public RequestGenerator getSavedCards(String uidx)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PAYMENTSERVICE,
				APINAME.GETSAVEDCARDS, init.Configurations,
				new String[] {uidx}, PayloadType.JSON, PayloadType.JSON);
		HashMap<String, String> header=new HashMap<String, String>();
		header.put("x-meta-ctx", "cli="+ tenantId +";uidx=" + uidx+";nidx=myntra;");
		return new RequestGenerator(service,header);
	}
	
	
	public RequestGenerator getSavedCard(String instrumentId,String uidx)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PAYMENTSERVICE,
				APINAME.GETSAVEDCARD, init.Configurations,
				PayloadType.JSON, new String[] { instrumentId }, PayloadType.JSON);
		log.info(service.URL);
		HashMap<String, String> header=new HashMap<String, String>();
		header.put("x-meta-ctx", "cli="+ tenantId +";uidx=" + uidx+";nidx=myntra;");
		return new RequestGenerator(service,header);
	}
	
	
	public RequestGenerator addSavedCard(String uidx, String cardNumber, String expMonth, String expYear,String cardName,String csrfToken) {
	MyntraService service = Myntra.getService(ServiceType.PORTAL_PAYMENTSERVICE, APINAME.ADDSAVEDCARD, init.Configurations,
			new String[] { cardNumber,expMonth,expYear,cardName,csrfToken }, PayloadType.JSON, PayloadType.JSON);
	log.info(service.URL);
	HashMap<String, String> header=new HashMap<String, String>();
	header.put("x-meta-ctx", "cli="+ tenantId +";uidx=" + uidx+";nidx=myntra;");
	return new RequestGenerator(service,header);
	}
	
	
	public RequestGenerator updateSavedCard(String uidx, String expMonth, String expYear,String csrfToken,String instrumentId) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PAYMENTSERVICE, APINAME.UPDATESAVEDCARD, init.Configurations, new String[] {expMonth,expYear}, new String[] {instrumentId} , PayloadType.JSON,  PayloadType.JSON);
		log.info(service.URL);
		HashMap<String, String> header=new HashMap<String, String>();
		header.put("x-meta-ctx", "cli="+ tenantId +";uidx=" + uidx+";nidx=myntra;");
		return new RequestGenerator(service,header);
		}
	
	public RequestGenerator deleteSavedCard(String uidx,String instrumentId) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PAYMENTSERVICE,
				APINAME.DELETESAVEDCARD, init.Configurations,
				PayloadType.JSON, new String[] { instrumentId }, PayloadType.JSON);
		log.info(service.URL);
		HashMap<String, String> header=new HashMap<String, String>();
		header.put("x-meta-ctx", "cli="+ tenantId +";uidx=" + uidx+";nidx=myntra;");
		System.out.println("cli=myntra;uidx=" + uidx+";nidx=myntra;");
		return new RequestGenerator(service,header);
		}
	
	
	public String[] getPPSIdAndOrderIdFromResponse(String responseC) {
		doc = Jsoup.parse(responseC);
		String[] result=new String[2];
		String title = doc.title();  
		System.out.println("Order status: " + title);  
     //   String name = doc.select(".msg-num").get(0).text();
        String name = doc.select(".msg-num strong").get(0).text();

     //	String app1 = name.substring(name.lastIndexOf(".") + 2);
   // 	String app1 = name.substring(name.indexOf(".") + 2, name.indexOf(" "));

      //  String app2 = app1.replace("-", "").trim();
        String app2 = name.replace("-", "").trim();

        String ppsId = getPPSId(app2.toString().trim());
        System.out.println("Order id:"+app2.toString());
        System.out.println("PPS id :" +ppsId);
        result[0]=ppsId;
        result[1]=app2.toString();
        return result;
	}

	/**
	 * This method is used to place orders using PayNow PPS API
	 * 
	 * @param ppsID
	 * @return RequestGenerator
	 */

	public RequestGenerator getPaymentPlan(String ppsID) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PPS,
				APINAME.GETPAYMENTPLAN, init.Configurations,
				new String[] {ppsID}, PayloadType.JSON, PayloadType.JSON);
		System.out
				.println("\nPrinting CheckoutService getPaymentPlan API URL :"
						+ service.URL);
		log.info("\nPrinting CheckoutService getPaymentPlan API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService getPaymentPlan API Payload :\n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService getPaymentPlan API Payload : \n\n"
				+ service.Payload);

		return new RequestGenerator(service);
	}
	
	public String verifyPaymentPlanAndReturnItemList(String ppsId) {
		RequestGenerator req3 = getPaymentPlan(ppsId);
        System.out.println(req3.respvalidate.returnresponseasstring());
        
        String state = req3.respvalidate
				.NodeValue("paymentPlanEntry.state", true).replaceAll("\"", "")
				.trim();
		System.out.println(state);
		
		String paymentPlanItems = req3.respvalidate
				.NodeValue("paymentPlanEntry.paymentPlanItems", true);

		System.out.println("paymentPlanItem is : "+paymentPlanItems);
		
		AssertJUnit.assertTrue("Order state in PPS is not success",
				state.contains("PPFSM Order Taking done"));
		return paymentPlanItems;
		
	}



	
	
}
