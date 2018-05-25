package com.myntra.apiTests.portalservices.pps;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Constants.Headers;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.apiTests.portalservices.pricingpromotionservices.helper.CashBackBalanceEntry;
import com.myntra.apiTests.portalservices.sessionnewservice.SessionNewServiceApiHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.SlackMessenger;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.paymentplan.domain.*;
import com.myntra.paymentplan.domain.request.AdditionalParam;
import com.myntra.paymentplan.domain.request.ExchangeOrderRequest;
import com.myntra.paymentplan.domain.request.PayNowRequest;
import com.myntra.paymentplan.domain.response.ExchangeOrderResponse;
import com.myntra.paymentplan.domain.response.PayNowResponse;
import com.myntra.paymentplan.domain.response.PaymentPlanResponse;
import com.myntra.paymentplan.enums.PPSItemType;
import com.myntra.paymentplan.enums.PaymentMethod;
import com.myntra.paymentplan.exception.ErrorResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.AssertJUnit;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PPSServiceHelperV2 {

	String tenantId;
	public PPSServiceHelperV2() {
	}

    public PPSServiceHelperV2(String tenantId) {
		super();
		this.tenantId = tenantId;
	}
    

	Document doc;

    static Initialize init = new Initialize("/Data/configuration");
    static Logger log = LoggerFactory.getLogger(PPSServiceHelperV2.class);

    private String getEncodedCSRFTokenFromPaymentPage(String xid, String sXID) throws UnsupportedEncodingException {

        log.debug("XID-- " + xid + "   SXID-- " +sXID);
        Svc paymentCSRF = HttpExecutorService.executeHttpService(Constants.CHECKOUT_PATH.GETPAYMENTPAGE, null, SERVICE_TYPE.CHECKOUT_SVC.toString(),
                HTTPMethods.GET, null, Headers.getPaymentPageHeader(xid, sXID));

        log.debug("getEncodedCSRFTokenFromPaymentPage -- "+ paymentCSRF);
        Document doc = Jsoup.parseBodyFragment(paymentCSRF.getResponseBody());
        Element body = doc.body();

        Element input = body.select("input[id=form-csrf-token]").first();
        String xyz = input.attr("value").toString();
        String abc = xyz.replaceAll("\n", "").trim();
        log.debug("final csrf token=" + abc);
        String encodedcsrf = URLEncoder.encode(abc, "UTF-8");
        return encodedcsrf;
    }

    /**
     * Hit PayNow from both for COD and Online
     * @param xid
     * @param addressId
     * @param csrf_token
     * @param isCod
     * @return {@link String}
     * @throws UnsupportedEncodingException
     */
    private String hitPayNowForm(String xid, String addressId, String csrf_token, boolean isCod) throws UnsupportedEncodingException {
        List<NameValuePair> payload;
        if (isCod)
            payload = createPayLoadCODOrder(addressId, csrf_token);
        else
            payload = createPayLoadOnLineOrder(addressId, csrf_token);

        Svc paynowForm = HttpExecutorService.executeHttpServiceForUrlEncode(Constants.PPS_PATH.PAYNOWFORM, null , SERVICE_TYPE.PPS_SVC.toString(),
				HTTPMethods.POST, payload , Headers.getPayNowHeader(xid, csrf_token));
				String responseN = paynowForm.getResponseBody();
        return responseN;
    }

    /**
     * Get PG Response
     * @param cardInfoPayload
     * @param xid
     * @param csrf_token
     * @return {@link String}
     */
    private String getPGResponse(List<NameValuePair> cardInfoPayload, String xid, String csrf_token) throws UnsupportedEncodingException {
        Svc paynowForm = HttpExecutorService.executeHttpServiceForUrlEncode(Constants.PPS_PATH.PGRESPONSE, null , SERVICE_TYPE.PPS_SVC.toString(),
                HTTPMethods.POST, cardInfoPayload, Headers.getPGResponseHeader(xid, csrf_token));
        String responseN = paynowForm.getResponseBody();
        log.info("PayNow Form Response:-   "+ responseN);
        doc = Jsoup.parse(responseN);
        String name = doc.select(".msg-num").get(0).text();
        String orderId = name.substring(name.lastIndexOf(".") + 2).replace("-", "").trim();
        return orderId;
    }

    private List<NameValuePair> createPayLoadOnLineOrder(String addressId, String token) {
        String useSavedCard = "false";
        String pm = "creditcard";
        String clientCode = "responsive";
        String cartContext = "default";
        String userGroup = "normal";
        String mobile = "9999999999";
        String wallet_enabled = "true";
        String other_cards = "false";
        String card_number = "4556053172667502";
        //String card_number = "5123456789012346";
        String bill_name = "Test card";
        //String card_month = "07";
        String card_month = "05";
        //String card_year = "22";
        String card_year = "20";
        String cvv_code = "123";
        String addressSel = addressId;
        String b_firstname = "PPS Service";
        String b_address = "PPS Service Automation Myntra address";
        String b_city = "Bangalore";
        String b_state = "Karnataka";
        String b_zipcode = "560045";
        String b_country = "IN";
        String email_online = "abhijit.pati@myntra.com";

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("csrf_token", token));
        nameValuePairs.add(new BasicNameValuePair("use_saved_card", useSavedCard.trim()));
        nameValuePairs.add(new BasicNameValuePair("use_saved_card", useSavedCard.trim()));
        nameValuePairs.add(new BasicNameValuePair("address", addressId.trim()));
        nameValuePairs.add(new BasicNameValuePair("pm", pm.trim()));
        nameValuePairs.add(new BasicNameValuePair("clientCode", clientCode.trim()));
        nameValuePairs.add(new BasicNameValuePair("cartContext", cartContext.trim()));
        nameValuePairs.add(new BasicNameValuePair("userGroup", userGroup.trim()));
        nameValuePairs.add(new BasicNameValuePair("email", email_online.trim()));
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile.trim()));
        nameValuePairs.add(new BasicNameValuePair("wallet_enabled", wallet_enabled.trim()));
        nameValuePairs.add(new BasicNameValuePair("other_cards", other_cards.trim()));
        nameValuePairs.add(new BasicNameValuePair("card_number", card_number.trim()));
        nameValuePairs.add(new BasicNameValuePair("bill_name", bill_name.trim()));
        nameValuePairs.add(new BasicNameValuePair("card_month", card_month.trim()));
        nameValuePairs.add(new BasicNameValuePair("card_year", card_year.trim()));
        nameValuePairs.add(new BasicNameValuePair("cvv_code", cvv_code.trim()));
        nameValuePairs.add(new BasicNameValuePair("address-sel", addressSel.trim()));
        nameValuePairs.add(new BasicNameValuePair("b_firstname", b_firstname.trim()));
        nameValuePairs.add(new BasicNameValuePair("b_address", b_address.trim()));
        nameValuePairs.add(new BasicNameValuePair("b_city", b_city.trim()));
        nameValuePairs.add(new BasicNameValuePair("b_state", b_state.trim()));
        nameValuePairs.add(new BasicNameValuePair("b_zipcode", b_zipcode.trim()));
        nameValuePairs.add(new BasicNameValuePair("b_country", b_country.trim()));

        return nameValuePairs;

    }

    private List<NameValuePair> createPayLoadCODOrder(String addressId, String token) {

        String clientCode = "responsive";
        String cartContext = "default";
        String pm = "cod";
        String userGroup = "normal";
        String mobile = "1111111111";
        String b_firstname = "PPS Service";
        String b_address = "PPS Service Automation Myntra address";
        String b_city = "Bangalore";
        String b_state = "Karnataka";
        String b_zipcode = "560045";
        String b_country = "India";
        String wallet_enabled = "true";
        String userinputcaptcha = "246";
        String email_online = "abhijit.pati@myntra.com";


        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("csrf_token", token));
        nameValuePairs.add(new BasicNameValuePair("address", addressId.trim()));
        nameValuePairs.add(new BasicNameValuePair("pm", pm.trim()));
        nameValuePairs.add(new BasicNameValuePair("clientCode", clientCode.trim()));
        nameValuePairs.add(new BasicNameValuePair("cartContext", cartContext.trim()));
        nameValuePairs.add(new BasicNameValuePair("userGroup", userGroup.trim()));
        nameValuePairs.add(new BasicNameValuePair("email", email_online.trim()));
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile.trim()));
        nameValuePairs.add(new BasicNameValuePair("wallet_enabled", wallet_enabled.trim()));
        nameValuePairs.add(new BasicNameValuePair("b_firstname", b_firstname.trim()));
        nameValuePairs.add(new BasicNameValuePair("b_address", b_address.trim()));
        nameValuePairs.add(new BasicNameValuePair("b_city", b_city.trim()));
        nameValuePairs.add(new BasicNameValuePair("b_state", b_state.trim()));
        nameValuePairs.add(new BasicNameValuePair("b_zipcode", b_zipcode.trim()));
        nameValuePairs.add(new BasicNameValuePair("b_country", b_country.trim()));
        nameValuePairs.add(new BasicNameValuePair("b_country", b_country.trim()));
        nameValuePairs.add(new BasicNameValuePair("b_country", b_country.trim()));
        nameValuePairs.add(new BasicNameValuePair("userinputcaptcha", userinputcaptcha.trim()));

        return nameValuePairs;
    }


    /**
     * Create Order PPS Online Flow
     * @param addressId
     * @param sessionID
     * @param isEgiftCard
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws SCMExceptions
     */
    public String payNowFormOnline(String addressId, String sessionID, Boolean isEgiftCard) throws IOException, ParseException, SCMExceptions {

        SessionNewServiceApiHelper sessionNewServiceApiHelper = new SessionNewServiceApiHelper();
        Svc svc = sessionNewServiceApiHelper.getSessionDetailsBySessionID(sessionID);

        String user_token = APIUtilities.getElement(svc.getResponseBody(), "USER_TOKEN", "JSON");
        String sxid = APIUtilities.getElement(svc.getResponseBody(), "sxid", "JSON");

        log.debug("user_token=" + user_token);
        log.debug("session xid=" + sxid);

        sessionNewServiceApiHelper.updateSession(sessionID, addressId);
        String csrf_token = getEncodedCSRFTokenFromPaymentPage(sessionID, sxid);


        String payNowFormResponse = hitPayNowForm(sessionID, addressId, csrf_token, false);
        List pgResponsePayload = extractCardInfoDetails(payNowFormResponse);
        String orderID = getPGResponse(pgResponsePayload, sessionID, csrf_token);
        return orderID;
    }

    /**
     * PayNow Create Order PPS Flow
     * @param addressId
     * @param sessionID
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws SCMExceptions
     */
    public String payNowFormCOD(String addressId, String sessionID) throws IOException, ParseException, SCMExceptions {

        SessionNewServiceApiHelper sessionNewServiceApiHelper = new SessionNewServiceApiHelper();
        Svc svc = sessionNewServiceApiHelper.getSessionDetailsBySessionID(sessionID);

        String user_token = APIUtilities.getElement(svc.getResponseBody(), "USER_TOKEN", "JSON");
        String sxid = APIUtilities.getElement(svc.getResponseBody(), "sxid", "JSON");

        log.debug("user_token=" + user_token);
        log.debug("session xid=" + sxid);

        sessionNewServiceApiHelper.updateSession(sessionID, addressId);
        String csrf_token = getEncodedCSRFTokenFromPaymentPage(sessionID, sxid);

        String payNowPayload = hitPayNowForm(sessionID, addressId, csrf_token, true);
        log.debug("Paynow Payload" + payNowPayload);
        doc = Jsoup.parse(payNowPayload);
        String title = doc.title();
        log.debug("Order status: " + title);

        String name = doc.select(".msg-num").get(0).text();
        log.debug("Name:"+name);
        String orderId = StringUtils.substringBetween(name, "Order No.", "TIP").trim().replaceAll("-", "");
        return orderId;
    }


    /**
     * @param addressId
     * @param sessionID
     * @param type
     * @param instrumentDetails
     * @return {@Link PayNowResponse}
     * @throws Exception
     */
    public PayNowResponse paynowCOD(String login, String addressId, String sessionID, String type, HashMap instrumentDetails, Boolean isEgiftCard)
            throws IOException, JSONException {
        PayNowRequest payNowRequest = new PayNowRequest();
        Map<AdditionalParam, String> additionalParam = getAdditionalParams(addressId, isEgiftCard);

        IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
        String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);

        CashBackBalanceEntry cashBackBalanceEntry = null;/*PnPServiceHelper.getCashbackBalanceEntry(uidx);*/
        if (cashBackBalanceEntry != null && cashBackBalanceEntry.getMigrationStatus().equalsIgnoreCase("MIGRATED"))
            additionalParam.put(AdditionalParam.WALLET_ENABLED, "true");
        else
            additionalParam.put(AdditionalParam.WALLET_ENABLED, "false");

        payNowRequest.setAdditionalParams(additionalParam);

        payNowRequest.setClientIP("1.1.1.1");
        payNowRequest.setSessionId(sessionID);
        if (type == null || type.equals("")) {

        } else {
            payNowRequest.setPaymentsDetailsMap(getPaymentDetailMap(sessionID, type, instrumentDetails));
        }

        String payload = APIUtilities.getObjectToJSON(payNowRequest);
        Svc service = HttpExecutorService.executeHttpService(Constants.PPS_PATH.PAYNOW, null, SERVICE_TYPE.PPS_SVC.toString(), HTTPMethods.POST, payload, getPPSHeader());
        SlackMessenger.send("scm_e2e_order_sanity", "PPS Order response " + service.getResponseBody());
        PayNowResponse payNowResponse = (PayNowResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new PayNowResponse());
        return payNowResponse;
    }

    private List<NameValuePair> extractCardInfoDetails(String responseN) {
        Document doc1 = Jsoup.parseBodyFragment(responseN);
        Element body1 = doc1.body();

        Element amount = body1.select("input[name=vpc_Amount]").first();
        String vpc_Amount = amount.attr("value").toString();
        log.debug("amount=" + vpc_Amount);

        Element info = body1.select("input[name=vpc_OrderInfo]").first();
        String vpc_orderInfo = info.attr("value").toString();
        log.debug("amount=" + vpc_orderInfo);

        Element message = body1.select("input[name=vpc_Message]").first();
        String vpc_Message = message.attr("value").toString();
        log.debug("amount=" + vpc_Message);

        Element desc = body1.select("input[name=vpc_Desc]").first();
        String vpc_Desc = desc.attr("value").toString();
        log.debug("amount=" + vpc_Desc);

        Element url = body1.select("input[name=vpc_ReturnURL]").first();
        String vpc_ReturnURL = url.attr("value").toString();
        log.debug("amount=" + vpc_ReturnURL);

        Element hash = body1.select("input[name=vpc_SecureHash]").first();
        String vpc_SecureHash = hash.attr("value").toString();
        log.debug("amount=" + vpc_SecureHash);

        Element cardNum = body1.select("input[name=vpc_CardNum]").first();
        String vpc_CardNum = cardNum.attr("value").toString();
        log.debug("amount=" + vpc_CardNum);

        Element authorized = body1.select("input[name=vpc_AuthorizeId]").first();
        String vpc_AuthorizeId = authorized.attr("value").toString();
        log.debug("amount=" + vpc_AuthorizeId);

        Element reference = body1.select("input[name=vpc_MerchTxnRef]").first();
        String vpc_MerchTxnRef = reference.attr("value").toString();
        log.debug("amount=" + vpc_MerchTxnRef);

        Element receipt = body1.select("input[name=vpc_ReceiptNo]").first();
        String vpc_ReceiptNo = receipt.attr("value").toString();
        log.debug("amount=" + vpc_ReceiptNo);

        Element expiry = body1.select("input[name=vpc_CardExp]").first();
        String vpc_CardExp = expiry.attr("value").toString();
        log.debug("amount=" + vpc_CardExp);

        Element code = body1.select("input[name=vpc_CardSecurityCode]").first();
        String vpc_CardSecurityCode = code.attr("value").toString();
        log.debug("amount=" + vpc_CardSecurityCode);

        Element card = body1.select("input[name=vpc_card]").first();
        String vpc_card = card.attr("value").toString();
        log.debug("amount=" + vpc_card);

        Element responseCode = body1.select("input[name=vpc_TxnResponseCode]").first();
        String vpc_TxnResponseCode = responseCode.attr("value").toString();
        log.debug("amount=" + vpc_TxnResponseCode);

        List<NameValuePair> cardInfoPayload = createPayloadCardInfo(vpc_Amount, vpc_orderInfo, vpc_Message, vpc_Desc, vpc_ReturnURL, vpc_SecureHash, vpc_CardNum, vpc_AuthorizeId,
                vpc_MerchTxnRef, vpc_ReceiptNo, vpc_CardExp, vpc_CardSecurityCode, vpc_card, vpc_TxnResponseCode);

        return cardInfoPayload;

    }

    private List<NameValuePair> createPayloadCardInfo(String vpc_Amount, String vpc_orderInfo, String vpc_Message, String vpc_Desc,
                                                      String vpc_ReturnURL, String vpc_SecureHash, String vpc_CardNum, String vpc_AuthorizeId,
                                                      String vpc_MerchTxnRef, String vpc_ReceiptNo, String vpc_CardExp, String vpc_CardSecurityCode,
                                                      String vpc_card, String vpc_TxnResponseCode) {

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("vpc_Amount", vpc_Amount.trim()));
        nameValuePairs.add(new BasicNameValuePair("vpc_orderInfo", vpc_orderInfo));
        nameValuePairs.add(new BasicNameValuePair("vpc_Message", vpc_Message.trim()));
        nameValuePairs.add(new BasicNameValuePair("vpc_Desc", vpc_Desc.trim()));
        nameValuePairs.add(new BasicNameValuePair("vpc_ReturnURL", vpc_ReturnURL.trim()));
        nameValuePairs.add(new BasicNameValuePair("vpc_SecureHash", vpc_SecureHash.trim()));
        nameValuePairs.add(new BasicNameValuePair("vpc_CardNum", vpc_CardNum.trim()));
        nameValuePairs.add(new BasicNameValuePair("vpc_AuthorizeId", vpc_AuthorizeId.trim()));
        nameValuePairs.add(new BasicNameValuePair("vpc_MerchTxnRef", vpc_MerchTxnRef.trim()));
        nameValuePairs.add(new BasicNameValuePair("vpc_ReceiptNo", vpc_ReceiptNo.trim()));
        nameValuePairs.add(new BasicNameValuePair("vpc_CardExp", vpc_CardExp.trim()));
        nameValuePairs.add(new BasicNameValuePair("vpc_CardSecurityCode", vpc_CardSecurityCode.trim()));
        nameValuePairs.add(new BasicNameValuePair("vpc_TxnResponseCode", vpc_TxnResponseCode.trim()));
        nameValuePairs.add(new BasicNameValuePair("vpc_card", vpc_card.trim()));
        return nameValuePairs;
    }

    public Object createExchange(String originalOrderID, String lineID, String reasonCode, int quantity, String exchangeSkuID) throws JAXBException, IOException {

        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(originalOrderID);
        OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntry(lineID);

        ExchangeOrderRequest ppsExOrderRequest = new ExchangeOrderRequest();
        ppsExOrderRequest.setOriginalOrderID(originalOrderID);
        ppsExOrderRequest.setOriginalPpsID(orderEntry.getPaymentPpsId());
        ppsExOrderRequest.setPaymentMethodMap(null);
        ppsExOrderRequest.setShippingAddress(getShippingAddress(originalOrderID));
        ppsExOrderRequest.setTxRefId("Exchange " + System.currentTimeMillis());
        ppsExOrderRequest.setUserComments("Exchange Request For Order ID " + originalOrderID + "  PPS ID " + orderEntry.getPaymentPpsId());
        ppsExOrderRequest.setUserLogin(orderEntry.getLogin());
        ppsExOrderRequest.setReasonCode(reasonCode);

        List<ExchangeItem> ppsExItemList = new ArrayList<ExchangeItem>();
        ExchangeItem ppsExItem = new ExchangeItem();

        ppsExItem.setOriginalOrderLineId(lineID);
        ppsExItem.setReasonCode(reasonCode);

        ExchangeItemDetail exItemDetail = new ExchangeItemDetail();

        exItemDetail.setType(PPSItemType.SKU);
        exItemDetail.setItemIdentifier(exchangeSkuID);
        exItemDetail.setQuantity(quantity);
        exItemDetail.setSellerId(orderLineEntry.getSellerId().toString());
        exItemDetail.setOptionId(orderLineEntry.getOptionId().toString());
        exItemDetail.setStyleId(orderLineEntry.getStyleId().toString());
        exItemDetail.setSupplyType(orderLineEntry.getSupplyType());

        ppsExItem.setExchange(exItemDetail);

        ExchangeItemDetail originalItemDetail = new ExchangeItemDetail();
        originalItemDetail.setItemIdentifier(orderLineEntry.getSkuId().toString());
        originalItemDetail.setOptionId(orderLineEntry.getOptionId().toString());
        originalItemDetail.setStyleId(orderLineEntry.getStyleId().toString());
        originalItemDetail.setType(PPSItemType.SKU);
        originalItemDetail.setQuantity(orderLineEntry.getQuantity());
        originalItemDetail.setSupplyType(orderLineEntry.getSupplyType());


        ppsExItem.setOriginal(originalItemDetail);


        ppsExItemList.add(ppsExItem);
        ppsExOrderRequest.setItems(ppsExItemList);

        String payload = APIUtilities.getObjectToJSON(ppsExOrderRequest);

        Svc service = HttpExecutorService.executeHttpService(Constants.PPS_PATH.EXCHANGE, null, SERVICE_TYPE.PPS_SVC.toString(), HTTPMethods.POST, payload, getPPSHeader());
        if (service.getResponseStatus() == 200) {
            ExchangeOrderResponse exchangeOrderResponse = (ExchangeOrderResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ExchangeOrderResponse());
            return exchangeOrderResponse;
        } else {
            ErrorResponse errorResponse = (ErrorResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ErrorResponse());
            return errorResponse;
        }
    }

    private ShippingAddress getShippingAddress(String originalOrderID) {
        ShippingAddress shippingAddressEntry = new ShippingAddress();
        Map<String, Object> orderRelease = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from order_release where order_id_fk = " + originalOrderID, "oms");
        shippingAddressEntry.setReceiverName(orderRelease.get("receiver_name").toString());
        shippingAddressEntry.setReceiverEmail(orderRelease.get("email").toString());
        shippingAddressEntry.setReceiverMobile(orderRelease.get("user_contact_no").toString());
        shippingAddressEntry.setShippingAddress(orderRelease.get("address").toString());
        shippingAddressEntry.setShippingLocality(orderRelease.get("locality").toString());
        shippingAddressEntry.setShippingCity(orderRelease.get("city").toString());
        shippingAddressEntry.setShippingCountry("India");
        shippingAddressEntry.setShippingState(orderRelease.get("state").toString());
        shippingAddressEntry.setShippingZipcode(orderRelease.get("zipcode").toString());
        return shippingAddressEntry;
    }


    /**
     * @param addressID
     * @return
     */
    private Map<AdditionalParam, String> getAdditionalParams(String addressID, Boolean isEGiftCard) {
        HashMap<AdditionalParam, String> hmAdditionalParameter = new HashMap<>();
        hmAdditionalParameter.put(AdditionalParam.ADDRESS_ID, addressID);
        if (isEGiftCard) {
            hmAdditionalParameter.put(AdditionalParam.CART_CONTEXT, "EGIFTCARD");
        } else {
            hmAdditionalParameter.put(AdditionalParam.CART_CONTEXT, "DEFAULT");
        }
        hmAdditionalParameter.put(AdditionalParam.CLIENT_CONTEXT, "responsive");
        return hmAdditionalParameter;
    }

    /**
     * @param sessionID
     * @param type
     * @param details
     * @return
     */
    private Map<PaymentMethod, PaymentDetails> getPaymentDetailMap(String sessionID, String type, HashMap details) {

        HashMap<PaymentMethod, PaymentDetails> hmPaymentDetails = new HashMap<>();
        String[] paymentType = type.split(":");

        for (String string : paymentType) {
            PaymentDetails paymentDetails = new PaymentDetails();
            String[] detailArray = ("" + details.get(string.toLowerCase())).split(":");
            paymentDetails.setInstrAmount(Long.parseLong(detailArray[0]));

            if (string.equalsIgnoreCase("cod")) {
                paymentDetails.setInstrumentParams(getInstrumentParamsCOD(sessionID, "testcheckout"));
                hmPaymentDetails.put(PaymentMethod.COD, paymentDetails);
            } else if (string.equalsIgnoreCase("netbanking")) {
                hmPaymentDetails.put(PaymentMethod.NETBANKING, paymentDetails);
            } else if (string.equalsIgnoreCase("giftcard")) {
                paymentDetails.setInstrumentParams(getInstrumentParamsGiftCard(detailArray[1], detailArray[2]));
                hmPaymentDetails.put(PaymentMethod.GIFTCARD, paymentDetails);
            } else if (string.equalsIgnoreCase("DEBITCARD")) {
                hmPaymentDetails.put(PaymentMethod.DEBITCARD, paymentDetails);
            }
        }
        return hmPaymentDetails;
    }

    private HashMap<InstrumentParam, String> getInstrumentParamsGiftCard(String cardNumber, String pin) {
        HashMap<InstrumentParam, String> hmInstrument = new HashMap<>();
        hmInstrument.put(InstrumentParam.GIFTCARD_NUMBER, cardNumber);
        hmInstrument.put(InstrumentParam.GIFTCARD_PIN, pin);
        return hmInstrument;
    }

    private HashMap<InstrumentParam, String> getInstrumentParamsCOD(String sessionID, String captcha) {
        HashMap<InstrumentParam, String> hmInstrument = new HashMap<>();
        hmInstrument.put(InstrumentParam.COD_CAPTCHA, captcha);
        hmInstrument.put(InstrumentParam.SESSION_ID, sessionID);
        return hmInstrument;
    }


    /**
     * Headers for the Bounty Service
     *
     * @return
     */
    private static HashMap<String, String> getPPSHeader() {
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
        createOrderHeaders.put("Content-Type", "Application/json");
        return createOrderHeaders;
    }

    public PaymentPlanResponse getRefundAmountFromPaymentPlan(String ppsId) throws JAXBException, JsonParseException, JsonMappingException, IOException, JSONException, XMLStreamException {
        String payload = "{\"ppsId\": \"" + ppsId + "\"}";
        log.debug("payload: " + payload);
        Svc service = HttpExecutorService.executeHttpService(Constants.PPS_PATH.GET_PAYMENT_PLAN, null, SERVICE_TYPE.PPS_SVC.toString(), HTTPMethods.POST, payload, getPPSHeader());

        String paymentPlanResponseString = service.getResponseBody();
        //Remove Date part from Response
        int startIndex = paymentPlanResponseString.indexOf("\"updateTime\"");
        int endIndex = paymentPlanResponseString.indexOf("}", startIndex);
        paymentPlanResponseString = paymentPlanResponseString.replaceAll(paymentPlanResponseString.substring(startIndex - 1, endIndex), "");
        log.debug(paymentPlanResponseString);

        PaymentPlanResponse paymentPlanResponse = (PaymentPlanResponse) APIUtilities.getJsontoObject(paymentPlanResponseString, new PaymentPlanResponse());
        return paymentPlanResponse;
    }

    public String getPPSId(String orderID) {
    	String dbName="";
    	Map<String, Object> payment_planTable;
    	if(tenantId.equalsIgnoreCase("jabong"))
    		dbName="jabong_pps";
    	else if(tenantId.equalsIgnoreCase("myntra"))
    		dbName="pps";
    	else
    		dbName="pps";
    	payment_planTable = DBUtilities.exSelectQueryForSingleRecord("select id from payment_plan where orderid = " + orderID, dbName);
    	return (String) payment_planTable.get("id");
    }

    public RequestGenerator cancelOrReturnOrder(String orderId,String ppsId,String actionType,String payload)
    {
        RequestGenerator requestGenerator=null;
        HashMap<String, String> hmr = new HashMap<String,String>();
        if(tenantId.equalsIgnoreCase("jabong"))
            hmr.put("x-meta-ctx", "pcid=jabong;");
        else if(tenantId.equalsIgnoreCase("myntra"))
            hmr.put("x-meta-ctx", "pcid=myntra;");
        else
            hmr=null;
        if(actionType!=null && actionType.equalsIgnoreCase("CANCELLATION"))
        {
            MyntraService service = Myntra.getService(ServiceType.PORTAL_PPS,
                    APINAME.ORDERCANCELV2, init.Configurations,
                    new String[] { orderId+"_1",ppsId,actionType,orderId,payload}, PayloadType.JSON,
                    PayloadType.JSON);
            if(hmr==null)
                requestGenerator = new RequestGenerator(service);
            else
                requestGenerator = new RequestGenerator(service,hmr);
        }
        else if(actionType!=null && actionType.equalsIgnoreCase("RETURN"))
        {
            MyntraService service = Myntra.getService(ServiceType.PORTAL_PPS,
                    APINAME.ORDERRETURNV2, init.Configurations,
                    new String[] { orderId+"_1",ppsId,actionType,orderId,orderId+"_123456_1",payload}, PayloadType.JSON,
                    PayloadType.JSON);
            if(hmr==null)
                requestGenerator = new RequestGenerator(service);
            else
                requestGenerator = new RequestGenerator(service,hmr);

        }
        return requestGenerator;
    }

    public RequestGenerator returnCODOrder(String orderId,String ppsId,String actionType,String payload)
    {
        HashMap<String, String> hmr = new HashMap<String,String>();
        if(tenantId.equalsIgnoreCase("jabong"))
            hmr.put("x-meta-ctx", "pcid=jabong;");
        else if(tenantId.equalsIgnoreCase("myntra"))
            hmr.put("x-meta-ctx", "pcid=myntra;");
        else
            hmr=null;

        RequestGenerator requestGenerator=null;

        MyntraService service = Myntra.getService(ServiceType.PORTAL_PPS,
                APINAME.CODORDERRETURNV2, init.Configurations,
                new String[] { orderId+"_1",ppsId,actionType,orderId,orderId+"_123456_1",payload}, PayloadType.JSON,
                PayloadType.JSON);
        if(hmr==null)
            requestGenerator = new RequestGenerator(service);
        else
            requestGenerator = new RequestGenerator(service,hmr);

        return requestGenerator;
    }


	public static String getItemPayload(String paymentPlanItems) throws org.json.JSONException {
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
    	if(tenantId.equalsIgnoreCase("jabong"))
    		hmp.put("x-meta-ctx", "pcid=jabong;");
    	else if(tenantId.equalsIgnoreCase("myntra"))
    		hmp.put("x-meta-ctx", "pcid=myntra;");

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
     	if(tenantId.equalsIgnoreCase("jabong"))
     		hm.put("x-meta-ctx", "pcid=jabong;");
     	else if(tenantId.equalsIgnoreCase("myntra"))
     		hm.put("x-meta-ctx", "pcid=myntra;");

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
     	if(tenantId.equalsIgnoreCase("jabong"))
     		hmx.put("x-meta-ctx", "pcid=jabong;");
     	else if(tenantId.equalsIgnoreCase("myntra"))
     		hmx.put("x-meta-ctx", "pcid=myntra;");

    	MyntraService serviceCard = Myntra.getService(ServiceType.PORTAL_PPS, APINAME.PGRESPONSE, init.Configurations,
				new String[] { cardInfoPayload }, PayloadType.URLENCODED, PayloadType.HTML);
		RequestGenerator reqCard = new RequestGenerator (serviceCard,hmx);
		return(reqCard.respvalidate.returnresponseasstring());
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
        HashMap<String, String> headerTenent = new HashMap<String,String>();
        if(tenantId.equalsIgnoreCase("jabong"))
        {
            headerTenent.put("x-meta-ctx", "pcid=jabong;");
        }
        else if(tenantId.equalsIgnoreCase("myntra"))
        {
            headerTenent.put("x-meta-ctx", "pcid=myntra;");
        }
        else
        {
            headerTenent=null;
        }


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

        RequestGenerator requestGenerator = null;
        if(headerTenent==null)
            requestGenerator = new RequestGenerator(service);
        else
            requestGenerator = new RequestGenerator(service,headerTenent);

        return requestGenerator;

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

	public static String getPaymentMethodPayload() {
		StringBuffer payload=new StringBuffer();
			payload=payload.append("{");
			payload.append("\"paymentMethodMap\":\""+"{");
			payload.append("\"WALLET\":\""+"{");
			payload.append("\"instrAmount\":\""+"79900");
			payload.append("}");
			payload=payload.append("}");
			//System.out.println("Sku id is : "+((JSONArray) jsonArray.get(i)).get("skuId"));
	//}
		System.out.println("Payload is :" + payload.toString());
		return payload.toString();
	}

	public String getOrderIdFk(String orderId) {
        Map<String,Object> orderIdFk = DBUtilities.exSelectQueryForSingleRecord("select order_id_fk from order_release where store_order_id = "+orderId,"oms");
        return orderIdFk.get("order_id_fk").toString();
	}

	public String getOrderLineId(String orderId) {
        Map<String,Object> orderLineId = DBUtilities.exSelectQueryForSingleRecord("select id from order_line where store_order_id = "+orderId,"oms");
        return orderLineId.get("id").toString();
	}

	public RequestGenerator exchangeOrder(String orderIdFk, String orderLineId, String ppsId, String skuId,
			String styleId, String exchangeSkuId, String uidx) {
        HashMap<String, String> hmr = new HashMap<String,String>();
        if(tenantId.equalsIgnoreCase("jabong"))
            hmr.put("x-meta-ctx", "pcid=jabong;");
        else if(tenantId.equalsIgnoreCase("myntra"))
            hmr.put("x-meta-ctx", "pcid=myntra;");
        else
            hmr=null;

        RequestGenerator requestGenerator=null;
    	System.out.println("original sku id:"+skuId);
    	System.out.println("original style id:"+styleId);
    	System.out.println("exchanged sku id:"+exchangeSkuId);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_PPS,
				APINAME.EXCHANGEORDER, init.Configurations,
				new String[] { orderIdFk,ppsId,"Exchange_"+orderIdFk+"_SKU_1234_456",skuId,styleId,exchangeSkuId,orderLineId,uidx}, PayloadType.JSON,
				PayloadType.JSON);

        if(hmr==null)
            requestGenerator = new RequestGenerator(service);
        else
            requestGenerator = new RequestGenerator(service,hmr);
	
	return requestGenerator;
	}

	public void markOrderDLQuery(String orderIdFk) {
        String updateOrderReleaseStatusCode = "UPDATE order_release SET status_code = 'DL' WHERE order_id_fk='" + orderIdFk + "';";
        String updateOrderReleaseDeliveredOn = "UPDATE order_release SET delivered_on=NOW() WHERE order_id_fk='" + orderIdFk + "';";
        String updateOrderLineStatusCode = "UPDATE order_line SET status_code='D' WHERE order_id_fk='" + orderIdFk + "';";

        DBUtilities.exUpdateQuery(updateOrderReleaseStatusCode, "myntra_oms");
        DBUtilities.exUpdateQuery(updateOrderReleaseDeliveredOn, "myntra_oms");
        DBUtilities.exUpdateQuery(updateOrderLineStatusCode, "myntra_oms");

	}

}
