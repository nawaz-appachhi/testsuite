package com.myntra.apiTests.erpservices.taxMaster;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.util.ServiceException;
import com.myntra.absolut.cart.client.response.entry.CartEntry;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.client.tools.response.ApplicationPropertiesResponse;
import com.myntra.lordoftherings.Toolbox;

import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.parser.GoogleSheetsApiTest;
import com.myntra.taxmaster.client.entry.request.CustomerVatRequestEntry;
import com.myntra.taxmaster.client.entry.request.customerVat.BulkVatRecoverRequestEntry;
import com.myntra.taxmaster.client.entry.request.customerVat.CustomerVatRecoverRequestEntry;
import com.myntra.taxmaster.client.entry.request.customerVat.CustomerVatSourceDetailEntry;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;


import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * Created by 16553 on 20/09/16.
 */


public class getVatToRecoverDP {

    private static org.slf4j.Logger log = LoggerFactory.getLogger(getVatToRecoverDP.class);
    static HashMap<Long,Double> expected_result_oms = new HashMap<Long,Double>();
    static HashMap<Long,Double> result_cart = new HashMap<Long,Double>();
    private static Date date = new Date();
    private static String value="";
    private static String sku;
    private static List<String> articles;
    private static List<?> warehouseline;
    private static HashMap<String, Object> sellerline;
    private static final String property_name = "taxmaster.article_ids_for_max_vat";
    private static VatRuleEngine vre = new VatRuleEngine();
    private static End2EndHelper e2e = new End2EndHelper();
    private static final String CLIENT_ID = "myntra@myntra-148806.iam.gserviceaccount.com";
    private static final String filePath = System.getProperty("user.dir")+"/Myntra-taxmaster.p12";
    private static final String spreadsheeetTitle="Global Tax Master Configuration";
    private static final String worksheetName="OMS Global Tax Master";
    static GoogleSheetsApiTest sheetAPI = new GoogleSheetsApiTest();
    private static final HashMap<Long, String> wh_state = new HashMap<Long, String>() {
        private static final long serialVersionUID = 1L;
		{
            put(36L, "KAR");
            put(9L, "WES");
            put(28L, "HAR");
            put(20L, "DEL");
            put(93L, "TEL");
            put(81L, "MAH");

        }
    };

    @SuppressWarnings("unchecked")
    @DataProvider
	public static Object[][] getVatToRecoverDataMaterial(ITestContext testContext) throws InterruptedException, IOException, JAXBException, Exception {
    	ArrayList<Object[]> list = new ArrayList<Object[]>();
        BulkVatRecoverRequestEntry bulkVatRecoverRequestEntry;
        List<CustomerVatRecoverRequestEntry> list_CustomerVatRecoverRequestEntry ;
        List<CustomerVatSourceDetailEntry> list_CustomerVatSourceDetailEntry;
        Long previousarticleId=0L;
        Long articleid;
        getMaxArticleList();
        List<ListEntry> listfeed = getArticleIdsFromSpreadsheet();
        for (ListEntry listentry : listfeed) {
            CustomerVatRequestEntry cvr = new CustomerVatRequestEntry();
            CustomElementCollection cec = listentry.getCustomElements();
            String artclId=cec.getValue("articleid");
            articleid=Long.parseLong(artclId);

        // to retrieve the skus based on article ids
            if (!(cec.getValue("material")==null) && articleid!=previousarticleId) {
                previousarticleId=articleid;
                value = (articles.contains(articleid)) ? "max" : "min";
                bulkVatRecoverRequestEntry = new BulkVatRecoverRequestEntry();
                list_CustomerVatRecoverRequestEntry = new LinkedList<CustomerVatRecoverRequestEntry>();
                CustomerVatRecoverRequestEntry customerVatRecoverRequestEntry = new CustomerVatRecoverRequestEntry();
                getDbData(articleid);

                if (sku != null && warehouseline != null) {
                    list_CustomerVatSourceDetailEntry = new LinkedList<CustomerVatSourceDetailEntry>();
                    Double mrp = genarateRandomNumber();
                    createCustomerVatRecoverRequestEntry(articleid, Long.parseLong(sku), mrp, "Khaadi", customerVatRecoverRequestEntry);
                    // to retrieve the list of warehouse ids for a sku
                    for (int i = 0; i < warehouseline.size(); i++) {
                        HashMap<String, Object> warehouse = (HashMap<String, Object>) warehouseline.get(i);
                        String wareHouseId = warehouse.get("warehouse_id").toString();
                        // imsServiceHelper.updateInventory(new String[] {sku+":"+Integer.parseInt(wareHouseId)+":10:0"});
                        createCustomerVatSourceDetailEntry(Long.parseLong(wareHouseId), Long.parseLong(sellerline.get("seller_id").toString()), customerVatRecoverRequestEntry, list_CustomerVatSourceDetailEntry);
                    }
                    customerVatRecoverRequestEntry.setSourceDetails(list_CustomerVatSourceDetailEntry);
                    if (!list_CustomerVatRecoverRequestEntry.add(customerVatRecoverRequestEntry))
                        log.info("customerVatRecoverRequestEntry not added");
                    Double tax_rate_oms = getExpectedTaxRateOMS(warehouseline, mrp, articleid, "Khaadi", value);
                    expected_result_oms.put(Long.parseLong(sku), tax_rate_oms);
                    Double tax_rate_cart = getCartTaxRate(sku);
                    result_cart.put(Long.parseLong(sku), tax_rate_cart);
                    bulkVatRecoverRequestEntry.setVatRecoverRequestEntries(list_CustomerVatRecoverRequestEntry);
                    list.add(new Object[]{bulkVatRecoverRequestEntry, expected_result_oms, result_cart});
                } else
                    log.info("no warehouseId/sku found found for " + articleid + " : articleid");
            }
        }
        return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
                list.size());
    }


    @SuppressWarnings("unchecked")
    @DataProvider
    public static Object[][] getVatToRecoverData(ITestContext testContext) throws InterruptedException, IOException, JAXBException, Exception {
    	ArrayList<Object[]> list = new ArrayList<Object[]>();
        BulkVatRecoverRequestEntry bulkVatRecoverRequestEntry;
        List<CustomerVatRecoverRequestEntry> list_CustomerVatRecoverRequestEntry ;
        List<CustomerVatSourceDetailEntry> list_CustomerVatSourceDetailEntry;
        Long previousarticleId=0L;
        Long articleid;
        getMaxArticleList();
        List<ListEntry> listfeed = getArticleIdsFromSpreadsheet();

       // for (ListEntry listentry : listfeed) {
            for (int ij=1;ij<=3;ij++){
                ListEntry listentry = listfeed.get(ij);
                CustomElementCollection cec = listentry.getCustomElements();
                String artclId = cec.getValue("articleid");
                articleid = Long.parseLong(artclId);
                log.info("Article Id " + articleid);

                // to retrieve the skus based on article ids
                if (articleid != previousarticleId) {
                    CustomerVatRequestEntry cvr = new CustomerVatRequestEntry();
                    previousarticleId = articleid;
                    value = (articles.contains(articleid)) ? "max" : "min";
                    bulkVatRecoverRequestEntry = new BulkVatRecoverRequestEntry();
                    list_CustomerVatRecoverRequestEntry = new LinkedList<CustomerVatRecoverRequestEntry>();
                    CustomerVatRecoverRequestEntry customerVatRecoverRequestEntry = new CustomerVatRecoverRequestEntry();
                    getDbData(articleid);
                    if (sku != null && warehouseline != null) {
                        list_CustomerVatSourceDetailEntry = new LinkedList<CustomerVatSourceDetailEntry>();
                        Double mrp = genarateRandomNumber();
                        createCustomerVatRecoverRequestEntry(articleid, Long.parseLong(sku), mrp, "", customerVatRecoverRequestEntry);
                        // to retrieve the list of warehouse ids for a sku
                        for (int i = 0; i < warehouseline.size(); i++) {
                            HashMap<String, Object> warehouse = (HashMap<String, Object>) warehouseline.get(i);
                            String wareHouseId = warehouse.get("warehouse_id").toString();
                            log.info("warehousId" + wareHouseId);
                            log.info(customerVatRecoverRequestEntry.toString());
                            // imsServiceHelper.updateInventory(new String[] {sku+":"+Integer.parseInt(wareHouseId)+":10:0"});
                            createCustomerVatSourceDetailEntry(Long.parseLong(wareHouseId), Long.parseLong(sellerline.get("seller_id").toString()), customerVatRecoverRequestEntry, list_CustomerVatSourceDetailEntry);
                        }
                        customerVatRecoverRequestEntry.setSourceDetails(list_CustomerVatSourceDetailEntry);
                        if (!list_CustomerVatRecoverRequestEntry.add(customerVatRecoverRequestEntry))
                            log.info("customerVatRecoverRequestEntry not added");
                        log.info("Hitting OMS for Taxrate");
                        Double tax_rate_oms = getExpectedTaxRateOMS(warehouseline, mrp, articleid, "", value);
                        expected_result_oms.put(Long.parseLong(sku), tax_rate_oms);
                        log.info("Hitting cart for taxRate");
                        Double tax_rate_cart = 0.0;
                        try {
                            tax_rate_cart = getCartTaxRate(sku);
                        } catch (Exception e) {
                            log.info(e.getMessage());
                        }
                        result_cart.put(Long.parseLong(sku), tax_rate_cart);
                        bulkVatRecoverRequestEntry.setVatRecoverRequestEntries(list_CustomerVatRecoverRequestEntry);
                        log.info(bulkVatRecoverRequestEntry.toString());
                        list.add(new Object[]{bulkVatRecoverRequestEntry, expected_result_oms, result_cart});
                    } else
                        log.info("no warehouseId/sku found found for " + articleid + " : articleid");
                }
            //}
        }
        return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
                list.size());
    }
    


    private static void createCustomerVatRecoverRequestEntry(Long articleid, Long skuId, Double mrp, String material,CustomerVatRecoverRequestEntry customerVatRecoverRequestEntry) {
        customerVatRecoverRequestEntry.setArticleId(articleid);
        customerVatRecoverRequestEntry.setskuId(skuId);
        customerVatRecoverRequestEntry.setMaterial(material);
        customerVatRecoverRequestEntry.setMrp(mrp);
    }

    private static void createCustomerVatSourceDetailEntry(Long warehouseId, Long sellerId, CustomerVatRecoverRequestEntry customerVatRecoverRequestEntry, List<CustomerVatSourceDetailEntry> list_CustomerVatSourceDetailEntry) {
        CustomerVatSourceDetailEntry customerVatSourceDetailEntry = new CustomerVatSourceDetailEntry();
        customerVatSourceDetailEntry.setSellerId(sellerId);
        customerVatSourceDetailEntry.setWarehouseId(warehouseId);
        if (!list_CustomerVatSourceDetailEntry.add(customerVatSourceDetailEntry))
            log.info("customerVatSourceDetailEntry not added");
    }


    private static Double genarateRandomNumber() {
        Random r = new Random();
        return r.nextInt((int) ((1000.99 - 0.00))) + 0.00;
    }

    private static Double getExpectedTaxRateOMS(List<?> warehouseline, Double mrp, Long articleId, String material,String min_or_max) throws IOException, JAXBException,Exception {
        Double tax_rate_from_oms = 0.0;
        Double max_tax_rate = 0.0;
        Double min_tax_rate = 100.0;
        Double final_tax_rate = 0.0;
        try {
            for (int i = 0; i < warehouseline.size(); i++) {

                HashMap<String, Object> warehouse = (HashMap<String, Object>) warehouseline.get(i);
                String wareHouseId = warehouse.get("warehouse_id").toString();
                String stateCode = wh_state.get(Long.parseLong(wareHouseId));
                tax_rate_from_oms = vre.TaxRuleEngine(VatRuleEngineDP_global.createRequestObject(articleId, "ML", date, stateCode, material, mrp, stateCode));
                switch (min_or_max) {
                    case "min":
                        if (tax_rate_from_oms < min_tax_rate) {
                            min_tax_rate = tax_rate_from_oms;
                            final_tax_rate = min_tax_rate;
                        } else
                            final_tax_rate = min_tax_rate;
                        break;
                    case "max":
                        if (tax_rate_from_oms > max_tax_rate) {
                            max_tax_rate = tax_rate_from_oms;
                            final_tax_rate = min_tax_rate = max_tax_rate;
                        } else
                            final_tax_rate = max_tax_rate;
                        break;
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return final_tax_rate;
    }
    private static Double getCartTaxRate(String skuId) throws IOException, JAXBException,Exception {
        String username="end2endautomation1@gmail.com";
        String pwd="123456";
        String addressId="6118982";
        String [] skuIdQty= {skuId+":1"};
        Thread.sleep(500);
        CartEntry ce = null;
        try {
             ce = e2e.getTheCartEntryDetails(username, pwd, addressId, skuIdQty, "", "", "", "", "", "");
        }
        catch(Exception e){
            log.info(e.getMessage());
        }
        if(ce.getCartItemEntries().size()>0)
            return ce.getCartItemEntries().get(0).getVatRate();
        else
            return -1.0;
    }

    
    public static void getMaxArticleList() throws UnsupportedEncodingException, JAXBException {
    	 /*HashMap<String, Object> rsetline = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord(
                 "select * from core_application_properties where name='" + property_name + "';", "myntra_tools");
         String[] maxArticleids = rsetline.get("value").toString().split(",");
         articles = new ArrayList<String>(Arrays.asList(maxArticleids));*/
        String [] pathParams= {"search?q=name.eq:taxmaster.article_ids_for_max_vat"};
        ApplicationPropertiesResponse queryResult=VatRuleEngineHelper.getApplicationPropertyValue(pathParams);
        String[] maxArticleids=queryResult.getData().get(0).getValue().split(",");
        articles = Arrays.asList(maxArticleids);
        log.info(queryResult.toString());

    }
    

	public static void getDbData(Long articleid) throws InterruptedException{
    	sellerline = null;
        HashMap<String, Object> styleline = null;
        warehouseline = null;
        sku = null;
    	List<?> skuIds = DBUtilities.exSelectQuery(
                 "select id from mk_skus where article_type_id = " + articleid, "myntra_wms");
        if(skuIds!=null)
         for (int i = 0; i < skuIds.size(); i++) {
             HashMap<String, Object> skuId = (HashMap<String, Object>) skuIds.get(i);
             Thread.sleep(500);
             sellerline = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord(
                     "select seller_id from wh_inventory where sku_id = " + skuId.get("id").toString() + ";", "myntra_ims");
             Thread.sleep(500);
             styleline = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord(
                     "select style_id from mk_styles_options_skus_mapping where sku_id = " + skuId.get("id").toString() + ";", "myntra");
             if (sellerline != null && styleline != null) {
                 sku = skuId.get("id").toString();
                 warehouseline = DBUtilities.exSelectQuery(
                         "select warehouse_id from seller_warehouse where seller_id=" + sellerline.get("seller_id") + " and warehouse_id IN (36,9,28,20,93,81);", "myntra_seller1");
                 break;
             } else
                 sku = null;
         }
    }


    public static List<ListEntry>  getArticleIdsFromSpreadsheet() throws ServiceException, GeneralSecurityException, URISyntaxException, IOException {
       List<ListEntry> listfeed = sheetAPI.testConnectToSpreadSheet(CLIENT_ID,filePath,spreadsheeetTitle,worksheetName);
        return listfeed;
    }
}
