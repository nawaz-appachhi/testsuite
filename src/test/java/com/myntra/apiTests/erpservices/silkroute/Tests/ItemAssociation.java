package com.myntra.apiTests.erpservices.silkroute.Tests;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.client.wms.response.CheckoutEntry;
import com.myntra.client.wms.response.ItemEntry;
import com.myntra.client.wms.response.ItemResponse;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sneha.Agarwal on 15/11/17.
 */
public class ItemAssociation extends BaseTest{
    WMSServiceHelper wmsServiceHelper = new WMSServiceHelper() ;


    @Test
    public void createItems() throws UnsupportedEncodingException, JAXBException {

        String skuBuyerQuantityWarehouse1 = "895235:2297:1:36";
        //String skuBuyerQuantityWarehouse2 = "3835:3465:1:36";

        String[] inputData = {skuBuyerQuantityWarehouse1};
        //String[] inputData = {skuBuyerQuantityWarehouse1, skuBuyerQuantityWarehouse2};

        ArrayList<HashMap> items = new ArrayList<>();

        for(String data : inputData){
            List<HashMap> list = insertItems(data.split(":")[0], data.split(":")[3], Integer.parseInt(data.split(":")[2]), Integer.parseInt(data.split(":")[1]), 313, 1);
            items.addAll(list);
        }
        itemCheckout(items, "21576277331");

        for(HashMap item : items){
            //wmsServiceHelper.prepareAndConsolidate(Long.parseLong(item.get("barcode").toString()), "36");
        }
    }

    public List<HashMap> insertItems(String skuId, String warehosueId, int qty, int buyerId, int poId, int poSkuId){

        HashMap<String, String> binEntry = new HashMap<>();
        binEntry.put("1", "403");
        binEntry.put("19", "151924");
        binEntry.put("28", "271760");
        binEntry.put("36", "1324873");

        for (int i =0; i<qty;i++) {

            long id = getMaxItemId()+1;

            DBUtilities.exUpdateQuery("INSERT INTO `item` (id, `barcode`, `sku_id`, `quality`, `item_status`, `warehouse_id`, `enabled`, `po_id`, `po_barcode`, `po_sku_id`, `lot_id`, `lot_barcode`, " +
                    "`comments`, `order_id`, `bin_id`) VALUES ("+id+", " + id + ", " + skuId + ", 'Q1', 'STORED', " + warehosueId + ", 1, "+ poId +", 'OPST050911-09', "+ poSkuId +", 1, 'LOTVHGA-01', 'Automatio item', NULL, " +
                    "" + binEntry.get("" + warehosueId) + ")", "wms");

            String insertItemQuery = "INSERT INTO `item_info` (`item_id`, `item_action_status`, `task_id`, `order_barcode`, `created_on`, `created_by`, `last_modified_on`, `version`, `order_id`, `invoice_sku_id`, `agreement_type`, `buyer_id`)"
                    + "VALUES(" + id +", 'NEW', NULL, NULL, now(), 'erpMessageQueue', now(), 0, NULL, NULL, 'OUTRIGHT', " + buyerId + ")";
            DBUtilities.exUpdateQuery(insertItemQuery, "myntra_wms");
        }

        String selectQuery = "select barcode from item where sku_id = "+ skuId +" and po_id = "+ poId +" and po_sku_id = "+ poSkuId +" and warehouse_id = "+ warehosueId +" order by id desc limit "+qty;
        List<HashMap> list = DBUtilities.exSelectQuery(selectQuery, "myntra_wms");
        return list;
    }


    public void itemCheckout(List<HashMap> list, String orderId) throws UnsupportedEncodingException, JAXBException{
        List<ItemEntry> items = new ArrayList<ItemEntry>();
        ItemEntry item;
        for (HashMap list1 : list) {
            System.out.println(list1.get("barcode"));
            item = new ItemEntry();
            item.setId(Long.parseLong(list1.get("barcode").toString()));
            items.add(item);
        }

        CheckoutEntry entry = new CheckoutEntry();
        entry.setOrderId(orderId);
        entry.setCartonBarcode("");
        entry.setItems(items);

        String checkout = APIUtilities.convertXMLObjectToString(entry);


        Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.ITEM_CHECKOUT,
                new String[] { "checkout" }, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST,
                APIUtilities.convertXMLObjectToString(entry), wmsServiceHelper.getWMSHeader());
        System.out.println(service.getResponseBody());
        ItemResponse response = (ItemResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ItemResponse());

    }

    public long getMaxItemId(){
        Map<String, Object> getId = DBUtilities.exSelectQueryForSingleRecord("select max(id) from item","wms");
        Long maxId = (Long)getId.get("max(id)");
        if(maxId==null){
            maxId = 0L;
        }
        return maxId;
    }
}

