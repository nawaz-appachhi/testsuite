package com.myntra.apiTests.erpservices.ims;

import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSHelper;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.client.inventory.SellerSupplyTypeSkuEntry;
import com.myntra.client.inventory.enums.Quality;
import com.myntra.client.inventory.enums.SupplyType;
import com.myntra.commons.response.EmptyResponse;
import com.myntra.ims.client.entry.AllocateExtInventoryEntry;
import com.myntra.ims.client.entry.BlockWhInventoryEntry;
import com.myntra.ims.client.entry.WhSellerSupplyTypeSkuEntry;
import com.myntra.ims.client.entry.WhSyncInventoryEntry;
import com.myntra.ims.client.enums.WhInventoryOperation;
import com.myntra.ims.client.request.AllocateExtInventoryRequest;
import com.myntra.ims.client.request.BlockWhInventoryRequest;
import com.myntra.ims.client.request.CoreInventoryRequest;
import com.myntra.ims.client.request.StoreLvlWhInventoryRequest;
import com.myntra.ims.client.request.WhSyncInventoryRequest;
import com.myntra.ims.client.response.*;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;

import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.*;

public class IMSServiceHelper{

	
    static Initialize init = new Initialize("/Data/configuration");

    static Logger log = Logger.getLogger(WMSHelper.class);

    /**
     * Get IMS Header
     * @return {@link HashMap}
     */
    private static HashMap<String, String> getIMSHeader() {
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
        createOrderHeaders.put("Content-Type", "Application/xml");
        return createOrderHeaders;
    }

    public StoreLvlWhInventoryResponse getStoreLvlWhInventory(String[] sellerSupplyTypes, long storeId, long[] warehouseIds) throws JAXBException, UnsupportedEncodingException{
        StoreLvlWhInventoryRequest storeLvlWhInventoryRequest =new StoreLvlWhInventoryRequest();

        List sellerSupplyTypeSkuEntryList = new ArrayList<>();

        for (String object : sellerSupplyTypes) {
            String[] sellerType = object.split(",");
            SellerSupplyTypeSkuEntry sellerSupplyTypeSkuEntry = new SellerSupplyTypeSkuEntry();
            sellerSupplyTypeSkuEntry.setSellerId(Long.parseLong(sellerType[0]));
            sellerSupplyTypeSkuEntry.setSkuId(Long.parseLong(sellerType[1]));
            if(sellerType[2].equalsIgnoreCase("on_hand")){
                sellerSupplyTypeSkuEntry.setSupplyType(SupplyType.ON_HAND);
            }else
                sellerSupplyTypeSkuEntry.setSupplyType(SupplyType.JUST_IN_TIME);

            sellerSupplyTypeSkuEntryList.add(sellerSupplyTypeSkuEntry);
        }



        storeLvlWhInventoryRequest.setData(sellerSupplyTypeSkuEntryList);
        storeLvlWhInventoryRequest.setStoreId(storeId);

        Set<Long> warhouseIds = new HashSet<>();
        for (long warehouseid : warehouseIds) {
            warhouseIds.add(warehouseid);
        }
        storeLvlWhInventoryRequest.setWarehouseIds(warhouseIds);
        String payload = APIUtilities.convertXMLObjectToString(storeLvlWhInventoryRequest);

        Svc service = HttpExecutorService.executeHttpService(Constants.IMS_PATH.STORE_SELLER_WAREHOUSE, null,SERVICE_TYPE.IMS_SVC.toString(), HTTPMethods.PUT, payload, getIMSHeader());
        StoreLvlWhInventoryResponse storeLvlWhInventoryResponse = (StoreLvlWhInventoryResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new StoreLvlWhInventoryResponse());
       log.info("response is:"+storeLvlWhInventoryResponse);
        return storeLvlWhInventoryResponse;

    }


    //block inventory helper
    public BlockWhInventoryResponse blockInventoryIMSHelper(String [] blockInventoryEntryDP, long storeId) throws JAXBException, UnsupportedEncodingException
    {
        //List blockWhInventoryEntryList =new ArrayList();
        BlockWhInventoryRequest blockWhInventoryRequest = new BlockWhInventoryRequest();
        BlockWhInventoryEntry blockWhInventoryEntry= new BlockWhInventoryEntry();
        List blockWhInventoryEntryList = new ArrayList<>();

        for (String object : blockInventoryEntryDP) {
            String[] sellerType = object.split(",");
            if((sellerType[0]).equalsIgnoreCase("null"))
            {
                blockWhInventoryEntry.setQuantity(null);
            }
            else
            {
                blockWhInventoryEntry.setQuantity(Long.parseLong(sellerType[0]));
            }
            if((sellerType[1]).equalsIgnoreCase("null"))
            {
                blockWhInventoryEntry.setSellerId(null);
            }
            else
            {
                blockWhInventoryEntry.setSellerId(Long.parseLong(sellerType[1]));
            }
            if((sellerType[2]).equalsIgnoreCase("null"))
            {
                blockWhInventoryEntry.setSkuId(null);
            }
            else
            {
                blockWhInventoryEntry.setSkuId(Long.parseLong(sellerType[2]));
            }
            if((sellerType[3]).equalsIgnoreCase("null"))
            {
                blockWhInventoryEntry.setWarehouseId(null);
            }
            else
            {
                blockWhInventoryEntry.setWarehouseId(Long.parseLong(sellerType[3]));
            }
            if(sellerType[4].equalsIgnoreCase("null")){
                blockWhInventoryEntry.setSupplyType(null);
            }else if(sellerType[4].equalsIgnoreCase("on_hand")){
                blockWhInventoryEntry.setSupplyType(SupplyType.ON_HAND);
            }else
                blockWhInventoryEntry.setSupplyType(SupplyType.JUST_IN_TIME);
                blockWhInventoryEntryList.add(blockWhInventoryEntry);

        }
      //  blockWhInventoryEntryList.add(blockWhInventoryEntry);
        blockWhInventoryRequest.setData(blockWhInventoryEntryList);
        if(storeId == 0)
        {
            blockWhInventoryRequest.setStoreId(null);
        }
        else{
            blockWhInventoryRequest.setStoreId(storeId);}
        String payload = APIUtilities.convertXMLObjectToString(blockWhInventoryRequest);

        Svc service = HttpExecutorService.executeHttpService(Constants.IMS_PATH.ORDER_BLOCKING, null,SERVICE_TYPE.IMS_SVC.toString(), HTTPMethods.PUT, payload, getIMSHeader());
        BlockWhInventoryResponse blockWhInventoryResponse = (BlockWhInventoryResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new BlockWhInventoryResponse());
       log.info("response is:"+blockWhInventoryResponse);
        return blockWhInventoryResponse;

    }


    public RequestGenerator invokeAllocateInventoryAPIMultipleSku(String store,
                                                                  String warehouse, String sku, String qty, String sku1, String qty1,
                                                                  String sku2, String qty2) {
        MyntraService inventoryUploadermultiplesku = Myntra.getService(
                ServiceType.ERP_IMS, APINAME.ALLOCATEINVENTORYMULTIPLESKU,
                init.Configurations, new String[] { store, warehouse, sku, qty,
                sku1, qty1, sku2, qty2 }, PayloadType.XML,
                PayloadType.XML);

       log.info("\nPrinting alocate inventory API URL : "
                                   + inventoryUploadermultiplesku.URL);
        log.info("\nPrinting silkroute alocate inventory  API URL : "
                         + inventoryUploadermultiplesku.URL);

       log.info("\nPrinting alocate inventory API Payload : "
                                   + inventoryUploadermultiplesku.Payload);
        log.info("\nPrinting alocate inventory API Payload : "
                         + inventoryUploadermultiplesku.Payload);

        HashMap<String, String> getOrderHeaders = new HashMap<String, String>();
        getOrderHeaders.put("Authorization",
                            "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
        getOrderHeaders.put("Content-Type", "Application/xml");
        ;

        return new RequestGenerator(inventoryUploadermultiplesku,
                                    getOrderHeaders);

    }

    public RequestGenerator invokeAllocateInventoryAPISingleSku(String store,
                                                                String warehouse, String sku, String qty) {
        MyntraService inventoryUploadersinglesku = Myntra.getService(
                ServiceType.ERP_IMS, APINAME.ALLOCATEINVENTORYSINGLESKU,
                init.Configurations,
                new String[] { store, warehouse, sku, qty }, PayloadType.XML,
                PayloadType.XML);

       log.info("\nPrinting alocate inventory API URL : "
                                   + inventoryUploadersinglesku.URL);
        log.info("\nPrinting silkroute alocate inventory  API URL : "
                         + inventoryUploadersinglesku.URL);

       log.info("\nPrinting alocate inventory API Payload : "
                                   + inventoryUploadersinglesku.Payload);
        log.info("\nPrinting alocate inventory API Payload : "
                         + inventoryUploadersinglesku.Payload);

        HashMap<String, String> getOrderHeaders = new HashMap<String, String>();
        getOrderHeaders.put("Authorization",
                            "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
        getOrderHeaders.put("Content-Type", "Application/xml");
        ;

        return new RequestGenerator(inventoryUploadersinglesku, getOrderHeaders);

    }

    public RequestGenerator invokeIMSSyncinventoryAPI(String warehouse ,String store,String seller,String supplytype,String sku_id,
                                                      String sku_code,String whInventoryOperation,String quantity) {
        MyntraService imsSyncInv = Myntra.getService(
                ServiceType.ERP_IMS, APINAME.updateimsinventory,
                init.Configurations, new String[] {warehouse, store,seller,supplytype,sku_id,sku_code,whInventoryOperation,quantity}, PayloadType.XML,
                PayloadType.XML);

       log.info("\nPrinting IMS Sync INV API URL : "
                                   + imsSyncInv.URL);
        log.info("\nPrinting IMS Sync INV API URL : "
                         + imsSyncInv.URL);

       log.info("\nPrinting IMS Sync INV API Payload : "
                                   + imsSyncInv.Payload);
        log.info("\nPrinting IMS Sync INV API Payload : "
                         + imsSyncInv.Payload);

        HashMap<String, String> imsSyncInvHeaders = new HashMap<String, String>();
        imsSyncInvHeaders.put("Authorization",
                              "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
        imsSyncInvHeaders.put("Content-Type", "Application/xml");
        return new RequestGenerator(imsSyncInv, imsSyncInvHeaders);
    }

    public WhSyncInventoryResponse bulkSellerInventoryUpload(String[] bulkEntries) throws JAXBException, UnsupportedEncodingException{
        WhSyncInventoryRequest whSyncInventoryRequest =new WhSyncInventoryRequest();
        // whSyncInventoryRequest.setData(whSyncInventoryEntryList);
        List whSyncInventoryEntryList = new ArrayList<>();

        for (String object : bulkEntries) {
            String[] bulkEntry = object.split(",");

            WhSyncInventoryEntry whSyncInventoryEntry = new WhSyncInventoryEntry();
            whSyncInventoryEntry.setWarehouseId(Long.parseLong(bulkEntry[0]));
            whSyncInventoryEntry.setStoreId(Long.parseLong(bulkEntry[1]));
            whSyncInventoryEntry.setSellerId(Long.parseLong(bulkEntry[2]));
            if(bulkEntry[3].equalsIgnoreCase("on_hand")){
                whSyncInventoryEntry.setSupplyType(SupplyType.ON_HAND);
            }else
                whSyncInventoryEntry.setSupplyType(SupplyType.JUST_IN_TIME);
            whSyncInventoryEntry.setSkuId(Long.parseLong(bulkEntry[4]));
            whSyncInventoryEntry.setSkuCode(bulkEntry[5]);
            if(bulkEntry[6].equalsIgnoreCase("SELLER_ALLOCATE"))
            {
                whSyncInventoryEntry.setWhInventoryOperation(WhInventoryOperation.SELLER_ALLOCATE);
            }


            if(bulkEntry[7].equalsIgnoreCase("q1"))
            {
                whSyncInventoryEntry.setFromQuality(Quality.Q1);
            }
            else
            {
                whSyncInventoryEntry.setFromQuality(Quality.Q2);
            }

            if(bulkEntry[8].equalsIgnoreCase("q1"))
            {
                whSyncInventoryEntry.setToQuality(Quality.Q1);
            }
            else
            {
                {
                    whSyncInventoryEntry.setToQuality(Quality.Q2);
                }
            }
            whSyncInventoryEntry.setQuantity(Long.parseLong(bulkEntry[9]));
            whSyncInventoryEntry.setLeadTime(Long.parseLong(bulkEntry[10]));


            whSyncInventoryEntryList.add(whSyncInventoryEntry);
        }

        whSyncInventoryRequest.setData(whSyncInventoryEntryList);


        String payload = APIUtilities.convertXMLObjectToString(whSyncInventoryRequest);

        Svc service = HttpExecutorService.executeHttpService(Constants.IMS_PATH.BULK_SYNC_INV, null,SERVICE_TYPE.IMS_SVC.toString(), HTTPMethods.POST, payload, getIMSHeader());
        WhSyncInventoryResponse whSyncInventoryResponse = (WhSyncInventoryResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new WhSyncInventoryResponse());
       log.info("response is:"+whSyncInventoryResponse);
        return whSyncInventoryResponse;

    }

    //search core inventory
    public CoreInventoryCountResponse searchCoreInv(String fetchsize, String sortOrder,String warehouse_id,String sku_id,String quality) throws JAXBException, UnsupportedEncodingException
    {
        String[] query_list ={"?q=skuId.eq:"+sku_id+"___warehouseId.eq:"+warehouse_id+"___quality.like:"+quality+"&f=id,skuId,skuCode,warehouseId,quality,inventoryCount,blockedManulCount,blockedMissedCount,blockedProcessingCount,warehouseName,lastModifiedOn,createdBy&start=0&fetchSize="+fetchsize+"&sortBy=id&sortOrder="+sortOrder};
        Svc service = HttpExecutorService.executeHttpService(Constants.IMS_PATH.CORE_INVENTORY_SEARCH,query_list,SERVICE_TYPE.IMS_SVC.toString(), HTTPMethods.GET, null, getIMSHeader());
        CoreInventoryCountResponse coreInventoryCountResponse = (CoreInventoryCountResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new CoreInventoryCountResponse());
       log.info("response is:"+coreInventoryCountResponse);
        return coreInventoryCountResponse;
    }

    
    public CoreInventoryCountResponse searchCoreInvSkuCode(String fetchsize, String sortOrder,String warehouse_id,String sku_code,String quality) throws JAXBException, UnsupportedEncodingException
    {
        String[] query_list ={"?q=skuCode.like:"+sku_code+"___warehouseId.eq:"+warehouse_id+"___quality.like:"+quality+"&f=id,skuId,skuCode,warehouseId,quality,inventoryCount,blockedManulCount,blockedMissedCount,blockedProcessingCount,warehouseName,lastModifiedOn,createdBy&start=0&fetchSize="+fetchsize+"&sortBy=id&sortOrder="+sortOrder};
        Svc service = HttpExecutorService.executeHttpService(Constants.IMS_PATH.CORE_INVENTORY_SEARCH,query_list,SERVICE_TYPE.IMS_SVC.toString(), HTTPMethods.GET, null, getIMSHeader());
        CoreInventoryCountResponse coreInventoryCountResponse = (CoreInventoryCountResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new CoreInventoryCountResponse());
       log.info("response is:"+coreInventoryCountResponse);
        return coreInventoryCountResponse;
    }
// with owner_partner_id
    public CoreInventoryCountResponse searchCoreInvOwnerId(String fetchsize, String sortOrder,String warehouse_id,String sku_id,String quality,String ownerPartnerId) throws JAXBException, UnsupportedEncodingException
    {
        String[] query_list ={"?q=skuId.eq:"+sku_id+"___warehouseId.eq:"+warehouse_id+"___quality.like:"+quality+"___ownerPartnerId.eq:"+ownerPartnerId+"&f=id,skuId,skuCode,warehouseId,quality,inventoryCount,blockedManulCount,blockedMissedCount,blockedProcessingCount,warehouseName,lastModifiedOn,ownerId,createdBy&start=0&fetchSize="+fetchsize+"&sortBy=id&sortOrder="+sortOrder};
        Svc service = HttpExecutorService.executeHttpService(Constants.IMS_PATH.CORE_INVENTORY_SEARCH,query_list,SERVICE_TYPE.IMS_SVC.toString(), HTTPMethods.GET, null, getIMSHeader());
        CoreInventoryCountResponse coreInventoryCountResponse = (CoreInventoryCountResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new CoreInventoryCountResponse());
        log.info("response is:"+coreInventoryCountResponse);
        return coreInventoryCountResponse;
    }

    
    //search inventory
    public WhInventoryCountResponse searchInv(String fetchsize, String sortOrder,String warehouse_id,String sku_code) throws JAXBException, UnsupportedEncodingException
    {
        String[] query_list ={"?q=warehouseId.eq:"+warehouse_id+"___skuCode.like:"+sku_code+"&f=id,warehouseName,supplyType,inventoryCount,warehouseId,storeId,skuCode,createdBy&start=0&fetchSize="+fetchsize+"&sortBy=id&sortOrder="+sortOrder};
        Svc service = HttpExecutorService.executeHttpService(Constants.IMS_PATH.INVENTORY_SEARCH,query_list,SERVICE_TYPE.IMS_SVC.toString(), HTTPMethods.GET, null, getIMSHeader());
        WhInventoryCountResponse inventoryCountResponse = (WhInventoryCountResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new WhInventoryCountResponse());
       log.info("response is:"+inventoryCountResponse);
        return inventoryCountResponse;
    }

    //getSLAinMin
    public EmptyResponse getSLAinMin(String[] sku_seller_wh_st_comb) throws JAXBException, UnsupportedEncodingException{


        List whSellerSupplyTypeSkuEntryList = new ArrayList<>();
        for (String object : sku_seller_wh_st_comb)
        {
            String[] sku_seller_wh_st_combs = object.split(",");


            WhSellerSupplyTypeSkuEntry whSellerSupplyTypeSkuEntry = new WhSellerSupplyTypeSkuEntry();
            whSellerSupplyTypeSkuEntry.setSellerId(Long.parseLong(sku_seller_wh_st_combs[0]));
            whSellerSupplyTypeSkuEntry.setSkuId(Long.parseLong(sku_seller_wh_st_combs[1]));
            if(sku_seller_wh_st_combs[2].equalsIgnoreCase("on_hand")){
                whSellerSupplyTypeSkuEntry.setSupplyType(SupplyType.ON_HAND);
            }else
                whSellerSupplyTypeSkuEntry.setSupplyType(SupplyType.JUST_IN_TIME);
            whSellerSupplyTypeSkuEntry.setWarehouseId(Long.parseLong(sku_seller_wh_st_combs[3]));
            whSellerSupplyTypeSkuEntryList.add(whSellerSupplyTypeSkuEntry);
        }
        WhSellerSupplyTypeSkuResponse whSellerSupplyTypeSkuResponse =new WhSellerSupplyTypeSkuResponse();
        whSellerSupplyTypeSkuResponse.setData(whSellerSupplyTypeSkuEntryList);
        String payload = APIUtilities.convertXMLObjectToString(whSellerSupplyTypeSkuResponse);

        Svc service = HttpExecutorService.executeHttpService(Constants.IMS_PATH.BULK_PROC_SLA, null,SERVICE_TYPE.IMS_SVC.toString(), HTTPMethods.PUT, payload, getIMSHeader());
        EmptyResponse emptyResponse = (EmptyResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new EmptyResponse());
       log.info("response is:"+emptyResponse);
        return emptyResponse;
    }

//core warehouse

    //isValidSellerWarehouse
    public EmptyResponse isvalidSellerWarehouse(String warehouse,String store_id,String seller_id,String type,String allocation_type,String skucode,String skuid,String fromquality,String toquality,String leadtime,String quantity) throws JAXBException, UnsupportedEncodingException{
        WhSyncInventoryEntry whSyncInventoryEntry=new WhSyncInventoryEntry();
        WhSyncInventoryRequest whSyncInventoryRequest =new WhSyncInventoryRequest();

        whSyncInventoryEntry.setWarehouseId(Long.parseLong(warehouse));
        whSyncInventoryEntry.setStoreId(Long.parseLong(store_id));
        whSyncInventoryEntry.setSellerId(Long.parseLong(seller_id));
        if(type.equalsIgnoreCase("on_hand")){
            whSyncInventoryEntry.setSupplyType(SupplyType.ON_HAND);
        }else
            whSyncInventoryEntry.setSupplyType(SupplyType.JUST_IN_TIME);
        whSyncInventoryEntry.setSkuId(Long.parseLong(skuid));
        whSyncInventoryEntry.setSkuCode(skucode);
        if(allocation_type.equalsIgnoreCase("SELLER_ALLOCATE"))
        {
            whSyncInventoryEntry.setWhInventoryOperation(WhInventoryOperation.SELLER_ALLOCATE);
        }


        if(fromquality.equalsIgnoreCase("q1"))
        {
            whSyncInventoryEntry.setFromQuality(Quality.Q1);
        }
        else
        {
            whSyncInventoryEntry.setFromQuality(Quality.Q2);
        }

        if(toquality.equalsIgnoreCase("q1"))
        {
            whSyncInventoryEntry.setToQuality(Quality.Q1);
        }
        else
        {
            {
                whSyncInventoryEntry.setToQuality(Quality.Q2);
            }
        }
        whSyncInventoryEntry.setQuantity(Long.parseLong(quantity));
        whSyncInventoryEntry.setLeadTime(Long.parseLong(leadtime));

        String payload = APIUtilities.convertXMLObjectToString(whSyncInventoryEntry);

        Svc service = HttpExecutorService.executeHttpService(Constants.IMS_PATH.IS_VALIDSELLER_WAREHOUSE, null,SERVICE_TYPE.IMS_SVC.toString(), HTTPMethods.PUT, payload, getIMSHeader());
        EmptyResponse emptyResponse = (EmptyResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new EmptyResponse());
       log.info("response is:"+emptyResponse);
        return emptyResponse;
    }

    public static CoreInventoryResponse core_warehouses(String quality,String warehouseId,long[] skuIds) throws JAXBException, UnsupportedEncodingException
    {
        CoreInventoryRequest coreInventoryRequest = new CoreInventoryRequest();
        if(quality.equalsIgnoreCase("q1"))
        {
            coreInventoryRequest.setQuality(Quality.Q1);
        }
        else

        {
            coreInventoryRequest.setQuality(Quality.Q2);
        }

        List<Long> skuIdlist =new ArrayList<Long>();
        for(int i=0 ;i<skuIds.length;i++)
        {
            long skuid=  skuIds[i];
            skuIdlist.add(skuid);
        }
        coreInventoryRequest.setSkuIds(skuIdlist);
        if(warehouseId==null)
        {
            coreInventoryRequest.setWarehouseId(null);
        }
        else
            coreInventoryRequest.setWarehouseId(Long.parseLong(warehouseId));


        String payload = APIUtilities.convertXMLObjectToString(coreInventoryRequest);

        Svc service = HttpExecutorService.executeHttpService(Constants.IMS_PATH.CORE_WAREHOUSE, null,SERVICE_TYPE.IMS_SVC.toString(), HTTPMethods.PUT, payload, getIMSHeader());
        CoreInventoryResponse coreInventoryResponse = (CoreInventoryResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new CoreInventoryResponse());
       log.info("response is:"+coreInventoryResponse);
        return coreInventoryResponse;
    }


    /**
     * Get IMS BlockOrder and Inventory Count in MAP.
     * @param skuIDs
     * @param wareHouseID
     * @param storeID
     * @param sellerID
     * @return {@link HashMap}
     * @throws SQLException
     */
    public HashMap<String, int[]> getIMSInvAndBlockOrderCount(String[] skuIDs, String wareHouseID, String storeID, String sellerID)
            throws SQLException {
        HashMap<String, int[]> skuMapping = new HashMap();
        for (String sku : skuIDs) {
            List bociocs = DBUtilities
                    .exSelectQuery("select inventory_count, blocked_order_count from wh_inventory where sku_id="
                                           + sku + " and warehouse_id =" + wareHouseID + " and  seller_id=" + sellerID + " and  store_id=" + storeID, "myntra_ims");
            HashMap<String, Object> hm = (HashMap<String, Object>) bociocs.get(0);
            int[] skuquantity = { (Integer) hm.get("inventory_count"), (Integer) hm.get("blocked_order_count") };
            skuMapping.put(sku, skuquantity);
        }
        return skuMapping;
    }

    /**
     * Get IMS BlockOrder and Inventory Count in MAP.
     * @param skuIDAndWareHouses
     * @return {@link HashMap}
     * @throws SQLException
     */
    public HashMap<String, HashMap> getIMSCoreInvDataForSku(String[] skuIDAndWareHouses)
            throws SQLException {
        HashMap<String, HashMap> skuMapping = new HashMap();
        for (String sku : skuIDAndWareHouses) {
            String[] skuIDAndWareHouse = sku.split(":");
            List bociocs = DBUtilities
                    .exSelectQuery("select * from core_inventory_counts where sku_id="
                                           + skuIDAndWareHouse[0] + " and warehouse_id =" + skuIDAndWareHouse[1] +";", "myntra_ims");
            HashMap<String, Object> hm = (HashMap<String, Object>) bociocs.get(0);
            skuMapping.put(skuIDAndWareHouse[0], hm);
        }
        return skuMapping;
    }

    /**
     * Get IMS BlockOrder and Inventory Count in MAP.
     * @param skuWareHouseStoreIDSellerIDs "SKUID:WAREHOUSEID:STOREID:SELLERID"
     * @return {@link HashMap}
     * @throws SQLException
     */
    public HashMap<String, HashMap<String, Object>> getIMSInventoryDetails(String[] skuWareHouseStoreIDSellerIDs)
            throws SQLException {
        HashMap<String, HashMap<String, Object>> skuMapping = new HashMap();
        for (String skuWareHouseStoreIDSellerID : skuWareHouseStoreIDSellerIDs) {
            String[] skuWareHouseStoreIDSellerIDSplit = skuWareHouseStoreIDSellerID.split(":");
            HashMap<String, Object> bociocs = (HashMap<String, Object>) DBUtilities
                    .exSelectQueryForSingleRecord("select * from wh_inventory where sku_id="
                                                          + skuWareHouseStoreIDSellerIDSplit[0] + " and warehouse_id =" + skuWareHouseStoreIDSellerIDSplit[1] + " and  seller_id=" + skuWareHouseStoreIDSellerIDSplit[3] + " and  store_id=" + skuWareHouseStoreIDSellerIDSplit[2], "myntra_ims");
            skuMapping.put(skuWareHouseStoreIDSellerIDSplit[0], bociocs);
        }
        return skuMapping;
    }

    /**
     * @param allocateExtInventoryEntryComb
     * @throws UnsupportedEncodingException 
     * @throws JAXBException 
     */
    public static AllocateExtInventoryRequest allocateExtInventoryRequest(String[] allocateExtInventoryEntryComb) throws UnsupportedEncodingException, JAXBException
    {
    	AllocateExtInventoryRequest allocateExtInventoryRequest =new AllocateExtInventoryRequest();
    	
    	List allocateExtInventoryEntrylist =new ArrayList();
    	 for (String object : allocateExtInventoryEntryComb) {
             String[] allocateExtInventoryEntryArray = object.split(",");
             AllocateExtInventoryEntry allocateExtInventoryEntry= new AllocateExtInventoryEntry();
             allocateExtInventoryEntry.setQuantity(Long.parseLong(allocateExtInventoryEntryArray[0]));
             allocateExtInventoryEntry.setSkuCode(allocateExtInventoryEntryArray[1]);
             allocateExtInventoryEntry.setStoreName(allocateExtInventoryEntryArray[2]);
             allocateExtInventoryEntry.setWarehouseName(allocateExtInventoryEntryArray[3]);
             allocateExtInventoryEntry.setRemarks("Automation Inv update");
             allocateExtInventoryEntry.setOverrideAutoRealloc(Boolean.parseBoolean(allocateExtInventoryEntryArray[4]));
             allocateExtInventoryEntrylist.add(allocateExtInventoryEntry);
    	 }
    	allocateExtInventoryRequest.setData(allocateExtInventoryEntrylist);
    	
    	 String payload = APIUtilities.convertXMLObjectToString(allocateExtInventoryRequest);

         Svc service = HttpExecutorService.executeHttpService(Constants.IMS_PATH.ALLOCATEINV, null,SERVICE_TYPE.IMS_SVC.toString(), HTTPMethods.PUT, payload, getIMSHeader());
         AllocateExtInventoryRequest allocateExtInventoryResponse = (AllocateExtInventoryRequest) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new AllocateExtInventoryRequest());
        log.info("response is:"+allocateExtInventoryResponse);
         return allocateExtInventoryResponse;
    	 
    	
    }

    /**
     * DB Update to WH_INVENTORY for a SkuID and warehouse ID
     * @param skuID
     * @param warehouseID
     * @param inventoryCount
     * @param blockOrderCount
     */
    public void updateInventoryForASkuID(int skuID, int warehouseID, int inventoryCount, int blockOrderCount){
        DBUtilities.exUpdateQuery("update wh_inventory set inventory_count="+inventoryCount +", blocked_order_count="+blockOrderCount+" where sku_id="+skuID+" and warehouse_id="+warehouseID,"myntra_ims");
    }

    /**
     * Update IMS inventory details
     * @param inventorydetails{skuid:warehouse_id:inventory_count:block_order_count}
     */
    public void updateInventory(String[] inventorydetails){

    	List resultSet = null;
    	Long sellerId = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
    	
        for (String inventory : inventorydetails) {
            String[] singleInventory = inventory.split(":");
            
            String selectQuery = "select * from wh_inventory where sku_id ='"+singleInventory[0]+ "' and "+ "warehouse_id='"+singleInventory[1]+ "'";
            resultSet = DBUtilities.exSelectQuery(selectQuery, "myntra_ims");
            String insertQuery = "INSERT INTO `wh_inventory` (`warehouse_id`, `warehouse_name`, `store_id`, `supply_type`, `sku_id`, `sku_code`, `inventory_count`, `blocked_order_count`, `created_by`, `created_on`, `last_modified_on`, `last_synced_on`, `pushed_order_count`, `version`, `vendor_id`, `seller_id`, `proc_sla`, `override_auto_realloc`)"+
            	    "VALUES ("+singleInventory[1]+", 'Gurgaon-Binola W/H', 1, 'ON_HAND', "+singleInventory[0]+", 'PUMATSRM01400',"+ singleInventory[2]+", "+singleInventory[3]+", 'erpadmin', '2015-05-05 14:54:36', '2016-09-21 18:27:56', '2015-05-05 14:54:36', 0, 16447, NULL,"+ sellerId+", 0, 1);";

            if(resultSet==null){
            	DBUtilities.exUpdateQuery(insertQuery, "myntra_ims");
            }else{
            	 DBUtilities.exUpdateQuery("update wh_inventory set inventory_count="+singleInventory[2] +", blocked_order_count="+singleInventory[3]+" where sku_id="+singleInventory[0]+" and warehouse_id in ("+singleInventory[1]+") and seller_id !=1;","myntra_ims");       
            }
            
         }
    }
    
    /**
     * Update IMS inventory details with Supply Type
     * @param inventorydetails{skuid:warehouse_id:inventory_count:block_order_count}, Supply Type: On_HAND or JUST_IN_TIME
     */
    public void updateInventory(String[] inventorydetails,String supplyType){

    	List resultSet = null;
    	Long sellerId = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
    	
        for (String inventory : inventorydetails) {
            String[] singleInventory = inventory.split(":");
            
            String selectQuery = "select * from wh_inventory where sku_id ='"+singleInventory[0]+ "' and "+ "warehouse_id='"+singleInventory[1]+ "'";
            resultSet = DBUtilities.exSelectQuery(selectQuery, "myntra_ims");
            String insertQuery = "INSERT INTO `wh_inventory` (`warehouse_id`, `warehouse_name`, `store_id`, `supply_type`, `sku_id`, `sku_code`, `inventory_count`, `blocked_order_count`, `created_by`, `created_on`, `last_modified_on`, `last_synced_on`, `pushed_order_count`, `version`, `vendor_id`, `seller_id`, `proc_sla`, `override_auto_realloc`)"+
            	    "VALUES ("+singleInventory[1]+", 'Gurgaon-Binola W/H', 1, '"+supplyType+"', "+singleInventory[0]+", 'PUMATSRM01400',"+ singleInventory[2]+", "+singleInventory[3]+", 'erpadmin', '2015-05-05 14:54:36', '2016-09-21 18:27:56', '2015-05-05 14:54:36', 0, 16447, NULL,"+ sellerId+", 0, 1);";

            if(resultSet==null){
            	DBUtilities.exUpdateQuery(insertQuery, "myntra_ims");
            }else{
            	 DBUtilities.exUpdateQuery("update wh_inventory set inventory_count="+singleInventory[2] +", blocked_order_count="+singleInventory[3]+" ,supply_type='"+supplyType+"' where sku_id="+singleInventory[0]+" and warehouse_id in ("+singleInventory[1]+") and seller_id !=1;","myntra_ims");       
            }
            
         }
    }
    
    /**
     * Update IMS inventory details with Supply Type
     * @param inventorydetails{skuid:warehouse_id1,warehouse_id2,warehouse_id3:inventory_count:block_order_count:sellerId}, Supply Type: On_HAND or JUST_IN_TIME
     */
    public void updateInventoryForSeller(String[] inventorydetails,String supplyType){

    	List resultSet = null;
    	String warehouseIds = null;
    	String vendorId = null;
    	String selectQuery=null;
    	String query = null;
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		
        for (String inventory : inventorydetails) {
            String[] singleInventory = inventory.split(":");
            if(supplyType.equalsIgnoreCase("JUST_IN_TIME")){
				vendorId = omsServiceHelper.getVendorIdFromVendorItemDB(singleInventory[0]);
			}
			log.info("VendorId: "+vendorId);
			
            warehouseIds = singleInventory[1];
            String warehouses[] = singleInventory[1].split(",");           
            DBUtilities.exUpdateQuery("update wh_inventory set inventory_count='0',blocked_order_count=0 where sku_id ='"+singleInventory[0]+"' and (warehouse_id not in ("+warehouseIds+") or seller_id!='"+singleInventory[4]+"' or supply_type!='"+supplyType+"');","myntra_ims");
            
            for(String warehouse:warehouses){
            	if(vendorId!=null){
            		 selectQuery = "select * from wh_inventory where sku_id ='"+singleInventory[0]+ "' and "+ "warehouse_id='"+warehouse+ "' and seller_id='"+singleInventory[4]+"' and supply_type='"+supplyType+"' and vendor_id="+vendorId+";";
            	}else{
            		 selectQuery = "select * from wh_inventory where sku_id ='"+singleInventory[0]+ "' and "+ "warehouse_id='"+warehouse+ "' and seller_id='"+singleInventory[4]+"' and supply_type='"+supplyType+"';";
            	}
            	resultSet = DBUtilities.exSelectQuery(selectQuery, "myntra_ims");
                String insertQuery = "INSERT INTO `wh_inventory` (`warehouse_id`, `warehouse_name`, `store_id`, `supply_type`, `sku_id`, `sku_code`, `inventory_count`, `blocked_order_count`, `created_by`, `created_on`, `last_modified_on`, `last_synced_on`, `pushed_order_count`, `version`, `vendor_id`, `seller_id`, `proc_sla`, `override_auto_realloc`)"+
                	    "VALUES ("+warehouse+", 'Gurgaon-Binola W/H', 1, '"+supplyType+"', "+singleInventory[0]+", 'PUMATSRM01400',"+ singleInventory[2]+", "+singleInventory[3]+", 'erpadmin', '2015-05-05 14:54:36', '2016-09-21 18:27:56', '2015-05-05 14:54:36', 0, 16447,"+vendorId+",'"+singleInventory[4]+"', 0, 1);";

                if(resultSet==null){
                	DBUtilities.exUpdateQuery(insertQuery, "myntra_ims");
                }else{
                	if(vendorId!=null){
                		query = "update wh_inventory set inventory_count="+singleInventory[2] +", blocked_order_count="+singleInventory[3]+" ,supply_type='"+supplyType+"' where sku_id="+singleInventory[0]+" and warehouse_id ='"+warehouse+"'  and seller_id='"+singleInventory[4]+"' and supply_type='"+supplyType+"' and vendor_id="+vendorId+";";
                	}else{
                		query = "update wh_inventory set inventory_count="+singleInventory[2] +", blocked_order_count="+singleInventory[3]+" ,supply_type='"+supplyType+"' where sku_id="+singleInventory[0]+" and warehouse_id ='"+warehouse+"'  and seller_id='"+singleInventory[4]+"' and supply_type='"+supplyType+"';";
                	}
                	DBUtilities.exUpdateQuery(query,"myntra_ims");
                }

            }            
         }
    }
    
    public void updateInventoryForSeller(int skuId, int warehouseId, int additionalInventoryCount, long sellerId){
    	Map<String, Object> data =  DBUtilities
				.exSelectQueryForSingleRecord("select id, inventory_count, blocked_order_count from wh_inventory where sku_id=" + skuId + " and warehouse_id = "+ warehouseId + " and seller_id = "+ sellerId,
						"myntra_ims");
		
		int inventoryCount = (int) data.get("inventory_count");
		int blockedOrderCount = (int) data.get("blocked_order_count");
		long id = (long) data.get("id");
		int count = inventoryCount + blockedOrderCount + additionalInventoryCount;
		DBUtilities.exUpdateQuery(
				"UPDATE `wh_inventory` SET `inventory_count` = " + count + " where id = " + id,
				"myntra_ims");
    }
    
}
