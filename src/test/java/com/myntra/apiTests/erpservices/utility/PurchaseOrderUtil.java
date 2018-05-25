package com.myntra.apiTests.erpservices.utility;

/**
 * Created by Sneha.Agarwal on 13/02/18.
 */

import Helpers.JeevesHelper;
import Helpers.POHelper;
import Helpers.PO_PayloadHelper.OtherPayloadHelper;
import Helpers.ServiceCalls.PoServiceCalls;
import Validators.PurchaseOrder.PoOIValidator;
import com.myntra.env.SERVICE_TYPE;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.purchaseorder.entry.PurchaseOrderEntry;
import com.myntra.purchaseorder.enums.PurchaseOrderStatus;
import com.myntra.purchaseorder.response.PurchaseOrderResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import org.apache.log4j.Logger;

import java.util.HashMap;

public class PurchaseOrderUtil {

    String response;
    static Logger log = Logger.getLogger(PurchaseOrderUtil.class);
    static HashMap<String, String> header = (new JeevesHelper()).Headers("Basic YTpi", "application/json", "application/json");
    public PurchaseOrderUtil() {
    }

    public static long CreateOIPOInApproveStatus(boolean multi, Integer qty) throws Exception {
        CreateOIPOPayload createOIPORequest = new CreateOIPOPayload();
        PurchaseOrderEntry purchaseOrderEntry = createOIPORequest.createPayload(multi, qty);
        PoServiceCalls poServiceCalls = new PoServiceCalls();
        PoOIValidator poOIValidator = new PoOIValidator();
        POHelper poHelper = new POHelper();
        String createResponse =createPO_OI(purchaseOrderEntry);
        poOIValidator.validateOiPoInInitiatedStatus(createResponse, purchaseOrderEntry);
        PurchaseOrderResponse purchaseOrderResponse = (PurchaseOrderResponse)APIUtilities.getJsontoObject(createResponse, new PurchaseOrderResponse());
        Long poId = ((PurchaseOrderEntry)purchaseOrderResponse.getData().get(0)).getId();
        String searchResponse = poHelper.statusChangeFromInitiated(poId.longValue());
        poOIValidator.validateOiPoInCreatedStatus(searchResponse);
        OtherPayloadHelper otherPayloadHelper = new OtherPayloadHelper();
        String updatePayload = otherPayloadHelper.createUpdatePayload(PurchaseOrderStatus.APPROVED);
        String updateResponse = poServiceCalls.POStatusUpdate(poId.longValue(), updatePayload);
        poOIValidator.validateOiPoStatusUpdate(updateResponse, PurchaseOrderStatus.APPROVED);
        return poId.longValue();
    }

    public static long CreateOIPOInApproveStatus(boolean multi, Integer qty,Integer skuCount,Long buyerId) throws Exception {
        CreateOIPOPayload createOIPORequest = new CreateOIPOPayload();
        PurchaseOrderEntry purchaseOrderEntry = createOIPORequest.createPayload(multi, qty,skuCount,buyerId);
        PoServiceCalls poServiceCalls = new PoServiceCalls();
        PoOIValidator poOIValidator = new PoOIValidator();
        POHelper poHelper = new POHelper();
        String createResponse =createPO_OI(purchaseOrderEntry);
        poOIValidator.validateOiPoInInitiatedStatus(createResponse, purchaseOrderEntry);
        PurchaseOrderResponse purchaseOrderResponse = (PurchaseOrderResponse)APIUtilities.getJsontoObject(createResponse, new PurchaseOrderResponse());
        Long poId = ((PurchaseOrderEntry)purchaseOrderResponse.getData().get(0)).getId();
        String searchResponse = poHelper.statusChangeFromInitiated(poId.longValue());
        poOIValidator.validateOiPoInCreatedStatus(searchResponse);
        OtherPayloadHelper otherPayloadHelper = new OtherPayloadHelper();
        String updatePayload = otherPayloadHelper.createUpdatePayload(PurchaseOrderStatus.APPROVED);
        String updateResponse = poServiceCalls.POStatusUpdate(poId.longValue(), updatePayload);
        poOIValidator.validateOiPoStatusUpdate(updateResponse, PurchaseOrderStatus.APPROVED);
        return poId.longValue();
    }

    public static String createPO_OI(PurchaseOrderEntry purchaseOrderEntry) throws Exception {
        String purchaseOrderEntryJson = APIUtilities.getObjectToJSON(purchaseOrderEntry);
        String payload = "[" + purchaseOrderEntryJson + "]";
        Svc svc = HttpExecutorService.executeHttpService("po/order-indent", null, SERVICE_TYPE.PO_SVC.toString(), HTTPMethods.POST, payload, header);
        String response = svc.getResponseBody();
        log.debug("\nCreate PO[Type:OI-PO] Response:\n" + response + "\n");
        return response;
    }
}
