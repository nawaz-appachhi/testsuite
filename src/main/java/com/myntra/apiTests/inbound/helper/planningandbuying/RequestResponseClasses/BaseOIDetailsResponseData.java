package com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses;

import com.myntra.commons.entries.BaseEntry;
import com.myntra.orderindent.client.entry.BuyPlanDetailEntry;
import com.myntra.orderindent.client.entry.BuyPlanEntry;
import com.myntra.orderindent.enums.BuyPlanStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 300000929 on 16/01/17.
 */
public class BaseOIDetailsResponseData extends BuyPlanEntry{

    HashMap<String,Object> allValues = new HashMap<>();

    public void setAllValues()
    {
        allValues = new HashMap<>();
        allValues.put("id",getId());
        allValues.put("createdBy",getCreatedBy());
        allValues.put("createdOn",getCreatedOn());
        allValues.put("lastModifiedOn",getLastModifiedOn());
        allValues.put("label",getLabel());
        allValues.put("status",getStatus());
        allValues.put("vendorId",getVendorId());
        allValues.put("vendorName",getVendorName());
        allValues.put("vendorContactPerson",getVendorContactPerson());
        allValues.put("vendorStateCode",getVendorStateCode());
        allValues.put("categoryManagerId",getCategoryManagerId());
        allValues.put("categoryManagerName",getCategoryManagerName());
        allValues.put("categoryManagerEmail",getCategoryManagerEmail());
        allValues.put("toMail",getToMail());
        allValues.put("commercialType",getCommercialType());
        allValues.put("seasonId",getSeasonId());
        allValues.put("seasonYear",getSeasonYear());
        allValues.put("stockOrigin",getStockOrigin());
        allValues.put("paymentTerms",getPaymentTerms());
        allValues.put("approver",getApprover());
        allValues.put("buyPlanOrderType",getBuyPlanOrderType());
        allValues.put("source",getSource());
        allValues.put("totalMrp",getTotalMrp());
        allValues.put("totalQty",getTotalQty());
        allValues.put("validity",getValidity());
        allValues.put("businessUnit",getBusinessUnit());
        allValues.put("baseOrderIndentId",getBaseOrderIndentId());
        allValues.put("attributeSheetPresent",getAttributeSheetPresent());
        allValues.put("comments",getComments());
        allValues.put("mailText",getMailText());
        allValues.put("buyerId",getBuyerId());;
        allValues.put("prioritization",getPrioritization());
        allValues.put("creditBasisAsOn",getCreditBasisAsOn());
        allValues.put("brandType",getBrandType());
        allValues.put("letterHeading",getLetterHeading());
        allValues.put("requestedDate",getRequestedDate());
        allValues.put("estimatedDeliveryDate",getEstimatedDeliveryDate());
    }

    public Object getValue(String key)
    {
        return allValues.get(key);
    }

}
