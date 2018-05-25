package com.myntra.apiTests.portalservices.pricingpromotionservices.helper;

import com.myntra.apiTests.erpservices.Constants;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by 8403 on 03/08/16.
 */
public class PnPServiceHelper {
    /**
     * Get CashBack Balance Entry for
     * @param uidx
     * @return {@link CashBackBalanceEntry}
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static CashBackBalanceEntry getCashbackBalanceEntry(String uidx) throws IOException, JSONException {
        Svc service = HttpExecutorService.executeHttpService(Constants.PNP_PATH.VIEW_CASHBACK_ACCOUNT, new String[] {uidx}, SERVICE_TYPE.PNP_SVC.toString(), HTTPMethods.GET, null, getPnPHeader());
        String response = service.getResponseBody();
        JSONObject cashbackResponse = new JSONObject(response);
        JSONObject cashbackData = cashbackResponse.getJSONObject("data");

        CashBackBalanceEntry cashbackBalanceEntry = (CashBackBalanceEntry) APIUtilities.getJsontoObject(cashbackData.toString(), new CashBackBalanceEntry());

        System.out.println(cashbackBalanceEntry.toString());
        return cashbackBalanceEntry;
    }


    /**
     * Credit Amount for Particular login ID
     * @param uidx
     * @param amount
     */
    public static void creditAmountToWallet(String uidx, double amount) throws IOException {
        CashBackCreditRequestEntry cashBackCreditRequestEntry = new CashBackCreditRequestEntry();
        cashBackCreditRequestEntry.setLogin(uidx);
        cashBackCreditRequestEntry.setBusinessProcess("CASHBACK_REVERSAL");
        cashBackCreditRequestEntry.setDescription("Crediting wrongly debited cashback Rs.2000 on cancellation of order 78531367");
        cashBackCreditRequestEntry.setEarnedCreditAmount(amount);
        cashBackCreditRequestEntry.setStoreCreditAmount(0d);
        cashBackCreditRequestEntry.setDebitAmount(0d);
        cashBackCreditRequestEntry.setItemType("ORDER");
        cashBackCreditRequestEntry.setItemId("123412");
        String payload = APIUtilities.getObjectToJSON(cashBackCreditRequestEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.PNP_PATH.CREDIT_CASHBACK, null, SERVICE_TYPE.PNP_SVC.toString(), HTTPMethods.POST, payload, getPnPHeader());
        Assert.assertEquals(service.getResponseStatus(), 200);
    }

    /**
     * Credit Amount for Particular login ID
     * @param uidx
     * @param amount
     */
    public static void debitAmountFromWallet(String uidx, double amount) throws IOException {
        CashBackCreditRequestEntry cashBackCreditRequestEntry = new CashBackCreditRequestEntry();
        cashBackCreditRequestEntry.setLogin(uidx);
        cashBackCreditRequestEntry.setBusinessProcess("CASHBACK_TO_BANK_TRANSFER");
        cashBackCreditRequestEntry.setDescription("Crediting wrongly debited cashback Rs.2000 on cancellation of order 78531367");
        cashBackCreditRequestEntry.setDebitAmount(amount);
        cashBackCreditRequestEntry.setStoreCreditAmount(0d);
        cashBackCreditRequestEntry.setDebitAmount(0d);
        cashBackCreditRequestEntry.setItemType("ORDER");
        cashBackCreditRequestEntry.setItemId("123412");
        String payload = APIUtilities.getObjectToJSON(cashBackCreditRequestEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.PNP_PATH.CREDIT_CASHBACK, null, SERVICE_TYPE.PNP_SVC.toString(), HTTPMethods.POST, payload, getPnPHeader());
        Assert.assertEquals(service.getResponseStatus(), 200);
    }


    private static HashMap<String, String> getPnPHeader() {
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
        createOrderHeaders.put("Content-Type", "Application/json");
        return createOrderHeaders;
    }
    
    public void removeDiscount(String styleID) throws UnsupportedEncodingException, JSONException{
        Svc service = HttpExecutorService.executeHttpService(Constants.PNP_PATH.GET_STYLE_DISCOUNT_INFO, null, SERVICE_TYPE.PNP_SVC.toString(), HTTPMethods.POST, "{\""+styleID+"\":1000}", getPnPHeader());
        JSONObject disCountResponse = new JSONObject(service.getResponseBody());
        String discountId= "";
        try {
        	JSONObject discountData = (JSONObject) disCountResponse.getJSONObject(styleID).getJSONArray("tradeDiscount").get(0);
	        discountId = ""+discountData.getInt("discountId");
	        System.out.println("Discount ID - "+discountId);
		} catch (JSONException e) {
			return;
		}
        Svc deleteDiscountservice = HttpExecutorService.executeHttpService(Constants.PNP_PATH.DELETE_STYLE_DISCOUNT_INFO, new String[] {discountId}, SERVICE_TYPE.PNP_SVC.toString(), HTTPMethods.DELETE, null, getPnPHeader());
        Assert.assertEquals(deleteDiscountservice.getResponseStatus(), 200);
    }




}
