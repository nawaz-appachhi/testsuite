package com.myntra.apiTests.common.entries;

import java.util.ArrayList;
import java.util.List;

import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.silkroute.client.entry.flipkart.FlipkartReturnAttributeEntry;
import com.myntra.silkroute.client.entry.flipkart.FlipkartReturnEntry;
import com.myntra.silkroute.client.entry.flipkart.FlipkartReturnItemEntry;
import com.myntra.silkroute.client.enums.flipkart.ReturnEventType;

public class FlipkartReturnEnteries {
	String returnId,eventType,source,timestamp,status,orderItemId; 
	String sourceline,statusline,action,quantity,orderDate,createdDate,UpdatedDate; 
	String tracking;
	String payload;
	public static final String ISO_DATE_FORMATE = "yyyy-MM-dd'T'HH:mm:ss";
	public FlipkartReturnEntry createRequest(String returnId, String eventType, String source){
		FlipkartReturnEntry flipkartReturnEntry=new FlipkartReturnEntry();
		flipkartReturnEntry.setReturnId(returnId);
		switch (eventType) {
		case "return_created":
			flipkartReturnEntry.setEventType(ReturnEventType.return_created);
			break;
		case "return_completed":
			flipkartReturnEntry.setEventType(ReturnEventType.return_completed);
			break;
		case "return_cancelled":
			flipkartReturnEntry.setEventType(ReturnEventType.return_cancelled);
			break;
		case "return_expected_date_changed":
			flipkartReturnEntry.setEventType(ReturnEventType.return_expected_date_changed);
			break;
		}
		flipkartReturnEntry.setSource(source);
		flipkartReturnEntry.setTimestamp(OMSServiceHelper.getDate());
		return flipkartReturnEntry;
	}
	public FlipkartReturnEnteries(String returnId, String eventType, String source, String status,
			String orderItemId, String sourceline, String statusline, String action, String quantity, String tracking) {
		FlipkartReturnEntry flipkartReturnEntry=createRequest(returnId, eventType, source);
		FlipkartReturnAttributeEntry attributeEntry=new FlipkartReturnAttributeEntry();
		attributeEntry.setStatus(status);
		List<FlipkartReturnItemEntry> flipkartReturnItemEntryList=new ArrayList<>();
		FlipkartReturnItemEntry flipkartReturnItemEntry=new FlipkartReturnItemEntry();
		flipkartReturnItemEntry.setReturnId(returnId);
		flipkartReturnItemEntry.setOrderItemId(orderItemId);
		flipkartReturnItemEntry.setSource(sourceline);
		flipkartReturnItemEntry.setStatus(statusline);
		if(!quantity.trim().isEmpty()){
			flipkartReturnItemEntry.setQuantity(Integer.parseInt(quantity));
		}
		flipkartReturnItemEntry.setOrderDate(OMSServiceHelper.getDate());
		flipkartReturnItemEntry.setCreatedDate(OMSServiceHelper.getDate());
		flipkartReturnItemEntry.setUpdatedDate(OMSServiceHelper.getDate());
		flipkartReturnItemEntry.setCourierName("flipkartlogistics");
		flipkartReturnItemEntry.setReason("Automation fk return");
		flipkartReturnItemEntry.setSubReason("Automation fk return sub reason");
		flipkartReturnItemEntry.setTrackingId(tracking);
		flipkartReturnItemEntry.setShipmentId("flipkartlogistics");
		flipkartReturnItemEntry.setComments("Automation fk return");
		flipkartReturnItemEntry.setCompletionDate(OMSServiceHelper.getDate());
		flipkartReturnItemEntry.setReplacementOrderItemId("123");
		flipkartReturnItemEntry.setProductId("productid 123");
		flipkartReturnItemEntry.setListingId("LSTTSHDBN3326TEZHQZMOIRZR");
		flipkartReturnItemEntryList.add(flipkartReturnItemEntry);
		attributeEntry.setReturnItems(flipkartReturnItemEntryList);
		flipkartReturnEntry.setAttributes(attributeEntry);
		payload = APIUtilities.convertJavaObjectToJsonUsingGson(flipkartReturnEntry,ISO_DATE_FORMATE);
	}
	public String getPayload() {
		return payload;
	}
	public FlipkartReturnEnteries(String returnId, String eventType, String source, String status,
			String orderItemId){
		FlipkartReturnEntry flipkartReturnEntry=createRequest(returnId, eventType, source);
		FlipkartReturnAttributeEntry attributeEntry=new FlipkartReturnAttributeEntry();
		attributeEntry.setStatus(status);
		List<FlipkartReturnItemEntry> flipkartReturnItemEntryList=new ArrayList<>();
		FlipkartReturnItemEntry flipkartReturnItemEntry=new FlipkartReturnItemEntry();
		flipkartReturnItemEntry.setOrderItemId(orderItemId);
		flipkartReturnItemEntryList.add(flipkartReturnItemEntry);
		attributeEntry.setReturnItems(flipkartReturnItemEntryList);
		flipkartReturnEntry.setAttributes(attributeEntry);
		payload = APIUtilities.convertJavaObjectToJsonUsingGson(flipkartReturnEntry,ISO_DATE_FORMATE);
	}
}
