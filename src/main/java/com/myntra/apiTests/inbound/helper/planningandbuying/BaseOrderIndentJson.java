package com.myntra.apiTests.inbound.helper.planningandbuying;


import com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses.BuyPlanHeaderUpdateRequest;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.orderindent.client.codes.BuyPlanErrorCodes;
import com.myntra.orderindent.client.entry.BaseOrderIndentEntry;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BaseOrderIndentJson {
	static Logger log = Logger.getLogger(BaseOrderIndentJson.class);

	public String createOIPayload(VendorData data) {

		BaseOrderIndentEntry baseOI = new BaseOrderIndentEntry();

		baseOI.setVendorContactPerson(data.getVENDOR_CONTACT_PERSON());
		baseOI.setVendorName(data.getVENDOR_NAME());
		baseOI.setVendorWarehouseLocation(data.getVENDOR_WAREHOUSE_LOCATION());
		baseOI.setOrderIndentOrderType(data.getORDER_INDENT_ORDER_TYPE());
		baseOI.setVendorAddress(data.getVENDOR_ADDRESS());
		baseOI.setVendorId((long)data.getVENDOR_ID());
		baseOI.setVendorEmail(data.getVENDOR_EMAIL());
		baseOI.setSeason(Long.parseLong(data.getSEASON()));
		baseOI.setOrderIndentSource(data.getORDER_INDENT_SOURCE());
		baseOI.setStockOrigin(data.getSTOCK_ORIGIN());
		baseOI.setSeasonYear(Integer.parseInt(data.getSEASON_YEAR()));
		baseOI.setCommercialType(data.getCOMMERCIAL_TYPE());
//		baseOI.setPaymentTerms(data.getPAYMENT());

		JSONObject obj = new JSONObject(baseOI);
		log.debug("\nBaseOIHeaderRequest json is:" + obj.toString() + "\n");
		return obj.toString();

	}

	public String createOIPayload(VendorData data,String buyplanType) {

		BaseOrderIndentEntry baseOI = new BaseOrderIndentEntry();

		baseOI.setVendorContactPerson(data.getVENDOR_CONTACT_PERSON());
		baseOI.setVendorName(data.getVENDOR_NAME());
		baseOI.setVendorWarehouseLocation(data.getVENDOR_WAREHOUSE_LOCATION());
		baseOI.setOrderIndentOrderType(data.getORDER_INDENT_ORDER_TYPE());
		baseOI.setVendorAddress(data.getVENDOR_ADDRESS());
		baseOI.setVendorId((long)data.getVENDOR_ID());
		baseOI.setVendorEmail(data.getVENDOR_EMAIL());
		baseOI.setSeason(Long.parseLong(data.getSEASON()));
		baseOI.setOrderIndentSource(data.getORDER_INDENT_SOURCE());
		baseOI.setStockOrigin(data.getSTOCK_ORIGIN());
		baseOI.setSeasonYear(Integer.parseInt(data.getSEASON_YEAR()));
		baseOI.setCommercialType(data.getCOMMERCIAL_TYPE());
		baseOI.setOrderIndentType(buyplanType);

		JSONObject obj = new JSONObject(baseOI);
		log.debug("\nBaseOIHeaderRequest json is:" + obj.toString() + "\n");
		return obj.toString();

	}

	public String updateOIPayload(VendorData data){
		UpdateOrderIndentHeaders updateOIHeaders = new UpdateOrderIndentHeaders();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String currentYearString = String.valueOf(currentYear);

		updateOIHeaders.setSeasonYear(currentYearString);
		updateOIHeaders.setSeasonId(data.getSEASON());
		updateOIHeaders.setCommercialType(data.getCOMMERCIAL_TYPE());
		updateOIHeaders.setBuyPlanOrderType(data.getORDER_INDENT_ORDER_TYPE());
		updateOIHeaders.setCategoryManagerEmail(data.getCM_EMAIL_ID2());
		updateOIHeaders.setCreditBasisAsOn(data.getCREDIT_BASIS_AS_ON());
		updateOIHeaders.setPrioritization(data.getPRIORITIZATION());
		updateOIHeaders.setLetterHeading(data.getLETTER_HEADING());
		updateOIHeaders.setBrandType(data.getBRAND_TYPE());
		updateOIHeaders.setBuyerId(data.getBUYER_ID());
		updateOIHeaders.setComments(data.getCOMMENTS());
		updateOIHeaders.setMailText(data.getMAIL_TEXT());

		JSONObject obj = new JSONObject(updateOIHeaders);
		log.debug("\nUpdateOIHeaders json is:" + obj.toString() + "\n");
		return obj.toString();
	}

	public static Object[] updateHeaderPayloadForVendor(VendorData data)
	{
		BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest = new BuyPlanHeaderUpdateRequest();
		HashMap<String, Object> requestData = new HashMap<>();

		buyPlanHeaderUpdateRequest.setBuyPlanOrderType(data.getORDER_INDENT_ORDER_TYPE());
		buyPlanHeaderUpdateRequest.setComments(data.getCOMMENTS());
		buyPlanHeaderUpdateRequest.setSeasonId(Long.parseLong(data.getSEASON()));
		buyPlanHeaderUpdateRequest.setMailText(data.getMAIL_TEXT());
		buyPlanHeaderUpdateRequest.setBuyerId(Long.parseLong(data.getBUYER_ID()));
		buyPlanHeaderUpdateRequest.setSeasonYear(Integer.parseInt(data.getSEASON_YEAR()));
		buyPlanHeaderUpdateRequest.setCategoryManagerEmail(data.getCM_EMAIL_ID2());
		buyPlanHeaderUpdateRequest.setStockOrigin(data.getSTOCK_ORIGIN());


		requestData.put("buyPlanOrderType",buyPlanHeaderUpdateRequest.getBuyPlanOrderType());
		requestData.put("comments",buyPlanHeaderUpdateRequest.getComments());
		requestData.put("seasonId",buyPlanHeaderUpdateRequest.getSeasonId());
		requestData.put("mailText",buyPlanHeaderUpdateRequest.getMailText());
		requestData.put("buyerId",buyPlanHeaderUpdateRequest.getBuyerId());
		requestData.put("seasonYear",buyPlanHeaderUpdateRequest.getSeasonYear());
		requestData.put("categoryManagerEmail",buyPlanHeaderUpdateRequest.getCategoryManagerEmail());
		requestData.put("stockOrigin",buyPlanHeaderUpdateRequest.getStockOrigin());

		return new Object[] { buyPlanHeaderUpdateRequest, requestData};
	}


    public static Object[] updateHeaderPayloadForCM(VendorData data) {
        BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest = new BuyPlanHeaderUpdateRequest();
        HashMap<String, Object> requestData = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        //request date is set as todays date
        Date requestDate =  Calendar.getInstance().getTime();

        //get estimatedDeliveryDate such that it is today+10 days
        Calendar c=new GregorianCalendar();
        c.add(Calendar.DATE, 10);
        Date estimatedDeliveryDate=c.getTime();

        buyPlanHeaderUpdateRequest.setBuyPlanOrderType(data.getORDER_INDENT_ORDER_TYPE());
        buyPlanHeaderUpdateRequest.setComments(data.getCOMMENTS());
        buyPlanHeaderUpdateRequest.setSeasonId(Long.parseLong(data.getSEASON()));
        buyPlanHeaderUpdateRequest.setMailText(data.getMAIL_TEXT());
        buyPlanHeaderUpdateRequest.setBuyerId(Long.parseLong(data.getBUYER_ID()));
        buyPlanHeaderUpdateRequest.setSeasonYear(Integer.parseInt(data.getSEASON_YEAR()));
        buyPlanHeaderUpdateRequest.setCategoryManagerEmail(data.getCM_EMAIL_ID2());
        buyPlanHeaderUpdateRequest.setStockOrigin(data.getSTOCK_ORIGIN());
        buyPlanHeaderUpdateRequest.setPrioritization(data.getPRIORITIZATION());
//        buyPlanHeaderUpdateRequest.setCreditBasisAsOn(data.getCREDIT_BASIS_AS_ON());
        buyPlanHeaderUpdateRequest.setBrandType(data.getBRAND_TYPE());
        buyPlanHeaderUpdateRequest.setLetterHeading(data.getLETTER_HEADING());
        buyPlanHeaderUpdateRequest.setCategoryManagerName("Sachin Kurana");
        buyPlanHeaderUpdateRequest.setCategoryManagerId("Sachin Kurana");
        buyPlanHeaderUpdateRequest.setCommercialType(data.getCOMMERCIAL_TYPE());
        buyPlanHeaderUpdateRequest.setSource("ISB");
        buyPlanHeaderUpdateRequest.setRequestedDate(simpleDateFormat.format(requestDate));
        buyPlanHeaderUpdateRequest.setEstimatedDeliveryDate(simpleDateFormat.format(estimatedDeliveryDate));
        buyPlanHeaderUpdateRequest.setTermsAdditionalClassification(data.getTERMS_ADDITIONAL_CLASSIFICATION());


        requestData.put("buyPlanOrderType",buyPlanHeaderUpdateRequest.getBuyPlanOrderType());
        requestData.put("comments",buyPlanHeaderUpdateRequest.getComments());
        requestData.put("seasonId",buyPlanHeaderUpdateRequest.getSeasonId());
        requestData.put("mailText",buyPlanHeaderUpdateRequest.getMailText());
        requestData.put("buyerId",buyPlanHeaderUpdateRequest.getBuyerId());
        requestData.put("seasonYear",buyPlanHeaderUpdateRequest.getSeasonYear());
        requestData.put("categoryManagerEmail",buyPlanHeaderUpdateRequest.getCategoryManagerEmail());
        requestData.put("stockOrigin",buyPlanHeaderUpdateRequest.getStockOrigin());
        requestData.put("prioritization",buyPlanHeaderUpdateRequest.getPrioritization());
//        requestData.put("creditBasisAsOn",buyPlanHeaderUpdateRequest.getCreditBasisAsOn());
        requestData.put("brandType",buyPlanHeaderUpdateRequest.getBrandType());
        requestData.put("letterHeading",buyPlanHeaderUpdateRequest.getLetterHeading());
        requestData.put("categoryManagerName",buyPlanHeaderUpdateRequest.getCategoryManagerName());
        requestData.put("categoryManagerId",buyPlanHeaderUpdateRequest.getCategoryManagerId());
        requestData.put("commercialType",buyPlanHeaderUpdateRequest.getCommercialType());
        requestData.put("source",buyPlanHeaderUpdateRequest.getSource());
        requestData.put("requestedDate",buyPlanHeaderUpdateRequest.getRequestedDate());
        requestData.put("estimatedDeliveryDate",buyPlanHeaderUpdateRequest.getEstimatedDeliveryDate());

        return new Object[] { buyPlanHeaderUpdateRequest, requestData};

    }

    public static Object[][]  createUpdateHeaderErrorPayload() throws IOException {
		BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest = new BuyPlanHeaderUpdateRequest();
		Constants.OIHeaderUCB oiheader1 = new Constants.OIHeaderUCB();
		Object[][] data = new Object[11][3];

		for(int i=0;i<data.length;i++)
		{
			switch (i)
			{
				case 0:
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
					Date requestDate = new Date();
					requestDate.setTime(1486558800000l);
					buyPlanHeaderUpdateRequest.setRequestedDate(simpleDateFormat.format(requestDate));

					Date estimatedDeliveryDate = new Date();
					estimatedDeliveryDate.setTime(1483880400000l);
					buyPlanHeaderUpdateRequest.setEstimatedDeliveryDate(simpleDateFormat.format(estimatedDeliveryDate));

					data[i][1] = BuyPlanErrorCodes.EDD_BEFORE_REQ_DATE.getStatusCode();
					data[i][2] = BuyPlanErrorCodes.EDD_BEFORE_REQ_DATE.getStatusMessage();
					break;

				case 1:
					buyPlanHeaderUpdateRequest.setSource("ISB");
					data[i][1] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_SOURCE.getStatusCode();
					data[i][2] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_SOURCE.getStatusMessage();
					break;

				case 2:
					buyPlanHeaderUpdateRequest.setCommercialType("SOR");
					data[i][1] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_COMMERCIAL_TYPE.getStatusCode();
					data[i][2] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_COMMERCIAL_TYPE.getStatusMessage();
					break;

				case 3:
					buyPlanHeaderUpdateRequest.setCategoryManagerId("sachin khurana");
					data[i][1] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_CM_ID.getStatusCode();
					data[i][2] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_CM_ID.getStatusMessage();
					break;

				case 4:
					buyPlanHeaderUpdateRequest.setCategoryManagerName("sachin.khurana");
					data[i][1] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_CM_ID.getStatusCode();
					data[i][2] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_CM_ID.getStatusMessage();
					break;

				case 5:
					buyPlanHeaderUpdateRequest.setLetterHeading("Myntra Designs Pvt Ltd");
					data[i][1] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_LETTER_HEADER.getStatusCode();
					data[i][2] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_LETTER_HEADER.getStatusMessage();
					break;

				case 6:
					buyPlanHeaderUpdateRequest.setApprover("sam.joy@myntra.com");
					data[i][1] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_APPROVER_EMAIL_IDS.getStatusCode();
					data[i][2] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_APPROVER_EMAIL_IDS.getStatusMessage();
					break;

				case 7:
					buyPlanHeaderUpdateRequest.setBrandType("INTERNAL");
					data[i][1] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_BRAND_TYPE.getStatusCode();
					data[i][2] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_BRAND_TYPE.getStatusMessage();
					break;

				case 8:
					buyPlanHeaderUpdateRequest.setCreditBasisAsOn("RECEIVED_DATE");
					data[i][1] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_CREDIT_BASIS_AS_ON_TYPE.getStatusCode();
					data[i][2] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_CREDIT_BASIS_AS_ON_TYPE.getStatusMessage();
					break;

				case 9:
					buyPlanHeaderUpdateRequest.setPrioritization("NORMAL");
					data[i][1] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_PI_PRIORITIZATION.getStatusCode();
					data[i][2] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_PI_PRIORITIZATION.getStatusMessage();
					break;

				case 10:
					buyPlanHeaderUpdateRequest.setTermsAdditionalClassification(13l);
					data[i][1] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_TERMS_EXCEPTION.getStatusCode();
					data[i][2] = BuyPlanErrorCodes.VENDOR_CANNOT_EDIT_TERMS_EXCEPTION.getStatusMessage();
					break;

			}

			buyPlanHeaderUpdateRequest.setBuyPlanOrderType(oiheader1.getORDER_INDENT_ORDER_TYPE());
			buyPlanHeaderUpdateRequest.setComments(oiheader1.getCOMMENTS());
			buyPlanHeaderUpdateRequest.setSeasonId(Long.parseLong(oiheader1.getSEASON()));
			buyPlanHeaderUpdateRequest.setMailText(oiheader1.getMAIL_TEXT());
			buyPlanHeaderUpdateRequest.setBuyerId(Long.parseLong(oiheader1.getBUYER_ID()));
			buyPlanHeaderUpdateRequest.setSeasonYear(Integer.parseInt(oiheader1.getSEASON_YEAR()));
			buyPlanHeaderUpdateRequest.setCategoryManagerEmail(oiheader1.getCM_EMAIL_ID2());
			data[i][0] = APIUtilities.getObjectToJSON(buyPlanHeaderUpdateRequest);
			buyPlanHeaderUpdateRequest = new BuyPlanHeaderUpdateRequest();
		}

		return data;
	}
}
