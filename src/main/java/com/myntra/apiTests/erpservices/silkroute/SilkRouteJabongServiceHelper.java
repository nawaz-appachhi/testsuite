package com.myntra.apiTests.erpservices.silkroute;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.Constants.Headers;
import com.myntra.apiTests.common.Constants.LambdaInterfaces;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.oms.client.entry.ReleaseUpdateEntry;
import com.myntra.silkroute.client.entry.jabong.OrderItemAttributes;
import com.myntra.silkroute.client.entry.jabong.OrderItemEntry;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sneha.Agarwal on 07/11/17.
 */
public class SilkRouteJabongServiceHelper {
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    static Logger log = LoggerFactory.getLogger(SilkRouteJabongServiceHelper.class);

    public Svc createOrderMFBJabong(HashMap<String,String> attributeList, double totalAmount, String shippingPincode, String processingStartDate, String processingEndDate, String type, String paymentMode) throws IOException {
        OrderItemAttributes orderItemAttributes= (OrderItemAttributes) setOrderItemAttributes.apply(attributeList.get("itemId"),attributeList.get("itemMrp"),attributeList.get("itemAmountBeforeTax"),
                attributeList.get("quantity"), attributeList.get("skuId"),attributeList.get("supplyType"),attributeList.get("sellerId"),attributeList.get("warehouseId"));
        OrderItemEntry orderItemEntry = (OrderItemEntry) createOrderItemEntry.apply(orderItemAttributes,totalAmount,shippingPincode,processingStartDate,processingEndDate,type,paymentMode);
        String payload = APIUtilities.getObjectToJSON(orderItemEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.SILKROUTE_PATH.CREATE_ORDER_JABONG_V1, null, SERVICE_TYPE.SILKJAB_SVC.toString(), HTTPMethods.POST, payload, Headers.JabongHeader());
        return service;
    }

    private LambdaInterfaces.SevenFunction createOrderItemEntry = (orderItemAttributes,totalAmount,shippingPincode,processingStartDate,processingEndDate,type,paymentMode) -> {
        List<OrderItemAttributes> orderItemAttributesEntries = new ArrayList<>();
        orderItemAttributesEntries.add ((OrderItemAttributes) orderItemAttributes);

        OrderItemEntry orderItemEntry = new OrderItemEntry();
        orderItemEntry.setItems(orderItemAttributesEntries);
        orderItemEntry.setTotalAmount((Double.parseDouble(String.valueOf( totalAmount))));
        orderItemEntry.setShippingPincode(String.valueOf(shippingPincode));
        orderItemEntry.setProcessingStartDate(String.valueOf(processingStartDate));
        orderItemEntry.setProcessingEndDate(String.valueOf(processingEndDate));
        orderItemEntry.setType(String.valueOf(type));
        orderItemEntry.setPaymentMethod(String.valueOf(paymentMode));
        return orderItemEntry;
    };


    private LambdaInterfaces.EightFunction setOrderItemAttributes = (itemID,itemMRP,itemAmountBeforeTax,quantity,skuID,supplyType,sellerID,warehouseId) -> {
        OrderItemAttributes orderItemAttributes = new OrderItemAttributes();
        orderItemAttributes.setSkuId(String.valueOf(skuID));
        orderItemAttributes.setSupplyType(String.valueOf(supplyType));
        orderItemAttributes.setItemId(String.valueOf(itemID));
        orderItemAttributes.setItemMrp((Double.parseDouble(String.valueOf(itemMRP))));
        orderItemAttributes.setItemAmountBeforeTax((Double.parseDouble(String.valueOf(itemAmountBeforeTax))));
        orderItemAttributes.setSellerId(String.valueOf(sellerID));
        orderItemAttributes.setQuantity((Integer.parseInt(String.valueOf(quantity))));
        orderItemAttributes.setWarehouseId((Long.parseLong(String.valueOf(warehouseId))));
        return orderItemAttributes;
    };


    public Svc blockWhInventory(Long releaseId,Integer warehouseId)throws Exception{
        ReleaseUpdateEntry releaseUpdateEntry = (ReleaseUpdateEntry) createReleaseUpdateInventory.apply(releaseId,warehouseId);
        String payload=APIUtilities.getObjectToJSON(releaseUpdateEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.SILKROUTE_PATH.BLOCK_INVENTORY_STAMP_WAREHOUSE,null,SERVICE_TYPE.OMS_SVC.toString(),HTTPMethods.PUT,payload,Headers.getBasicHeaderJSON());
        return service;
    }


    private LambdaInterfaces.BiFunction createReleaseUpdateInventory = (releaseId,warehouseId) -> {
        ReleaseUpdateEntry releaseUpdateEntry = new ReleaseUpdateEntry();
        releaseUpdateEntry.setReleaseId((long)releaseId);
        releaseUpdateEntry.setWarehouseId((int)warehouseId);
        return releaseUpdateEntry;
    };

    //To get the relevant store_order_id from a Jabong MFB order in Silkroute
    public String getStore_order_id(String Jabong_Item_id){
        String orderReleaseId=omsServiceHelper.getOneColumnValue("order_release_id_fk","store_line_id",Jabong_Item_id,"order_line","silkroute");
        return omsServiceHelper.getOneColumnValue("store_release_id","id",orderReleaseId,"order_release","silkroute");
    }

    // TO get the onhold status for a order at release level
    public String validateOnHoldStatus(String store_order_id){
        return (omsServiceHelper.getOneColumnValue("is_on_hold","store_order_id",store_order_id,"order_release","oms"));
    }

    public Svc getStockJabong(){
        return null;

    }


    public Svc resolveOnHoldOrders(String orderReleaseId) throws IOException{
        Svc service = HttpExecutorService.executeHttpService(Constants.SILKROUTE_PATH.RESOLVE_ON_HOLD,new String[] {orderReleaseId},SERVICE_TYPE.SILKJAB_SVC.toString(),HTTPMethods.PUT,"",Headers.getBasicHeaderJSON());
        return service;
    }

    public Svc repushToOMS(String storeReleaseId) throws IOException {
        Svc service = HttpExecutorService.executeHttpService(Constants.SILKROUTE_PATH.REPUSH_TO_OMS,new String[] {storeReleaseId},SERVICE_TYPE.SILKJAB_SVC.toString(),HTTPMethods.PUT,"",Headers.getBasicHeaderJSON());
        return service;
    }
}
