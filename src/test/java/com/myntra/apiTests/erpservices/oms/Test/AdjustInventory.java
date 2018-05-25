package com.myntra.apiTests.erpservices.oms.Test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.testbase.BaseTest;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdjustInventory extends BaseTest {
	
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(AdjustInventory.class);
	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	StyleServiceApiHelper styleServiceApiHelper = new StyleServiceApiHelper();
	/**
	 * This Testcase is used to Adjust Inventory in IMS and ATP
	 * @throws IOException 
	 */
	@Test(groups = { "Regression" }, priority = 0)
	public void adjustInventory() throws IOException {
		
			String skuIds = System.getenv("SKUID");
			String warehouseIds = System.getenv("WarehouseId");
			String inventoryCount = System.getenv("InventoryCount");
			String supplyType = System.getenv("SupplyType");
			String sellerId = System.getenv("SellerID");
						
			updateInventoryInIMSAndATP(new String[]{skuIds+":"+warehouseIds+":"+inventoryCount+":"+sellerId},supplyType);
		
	}
	
	/**
	 * This function is used to get StyleId for Particular SKUID
	 * @param skuId
	 * @return
	 */
	private String getStyleForSKU(String skuId){		
		HashMap<String, Object> resultSet = null;
		try {
			resultSet = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from mk_styles_options_skus_mapping where sku_id ='" + skuId + "';", "myntra");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Style Entry in DB : "+ e.getLocalizedMessage());
			Assert.fail("unable to get styleId for sku : "+skuId);
		}
		
		return resultSet.get("style_id").toString();
		
	}
	
	/**
	 * @param inventorydetails{skuid:warehouse_id1,warehouse_id2,warehouse_id3:inventory_count:block_order_count:sellerId}, Supply Type: On_HAND or JUST_IN_TIME
	 * @param supplyType
	 * @throws IOException
	 * This will delete IMS and ATP data for sku and then insert it and will do style reindexing
	 */
	public void updateInventoryInIMSAndATP(String[] inventorydetails,String supplyType) throws IOException{
		ArrayList<String> styleIds = new ArrayList<String>();
		
        for (String inventory : inventorydetails) {
            String[] singleInventory = inventory.split(":");
			String skus[] = singleInventory[0].split(",");
			String warehouseId[] = singleInventory[1].split(",");
			
			//Delete records for skuIds
    		deleteRecordsInIMSandATP(singleInventory[0]);
    		
    		int j=0;
    		for(String skuId: skus){
    			imsServiceHelper.updateInventoryForSeller(new String[] {skuId+":"+singleInventory[1]+":"+singleInventory[2]+":0:"+singleInventory[4]},supplyType);
    		 	log.info("IMS Inventory got Adjusted for ItemId "+skuId+" in WarehouseId: "
    		 	+singleInventory[1]+" with Inventory count "+singleInventory[2]+" and supplyType as "+supplyType);
    		 	
    	        atpServiceHelper.updateInventoryDetailsForSeller(new String[] {skuId+":"+singleInventory[1]+":"+singleInventory[2]+":0:"+singleInventory[4]+":1"},supplyType);
    	        
    	        log.info("ATP Inventory got Adjusted for ItemId "+skuId+" in WarehouseId: "
    	    		 	+singleInventory[1]+" with Inventory count "+singleInventory[2]+" and supplyType as "+supplyType);
    	        
    	        String styleId = getStyleForSKU(skuId);
    	        if(!styleIds.contains(styleId)){
    	        	//lmsServiceHelper.styleReindexForStyleIDs(styleId);
    	        	styleIds.add(j, styleId);
    	        	j++;
    	        	log.info("Style Reindexing is completed for StyleId :"+styleId);
    	        }
    		}
    		
        }
		
		int[] styleIdsForPost = new int[styleIds.size()];
		int i =0;
		for(String styleId:styleIds){
			styleIdsForPost[i] = Integer.parseInt(styleId);
			i++;
		}
		
		styleServiceApiHelper.styleReindexForStyleIDsPost(styleIdsForPost);
	}
	
	/**
	 * @param skuIds
	 * This function is to delete records from IMS and ATP
	 */
	private void deleteRecordsInIMSandATP(String skuIds){
		String deleteQueryATP = "delete  from inventory where sku_id in ("
				+ skuIds + ");";
		String deleteQueryIMS = "delete  from wh_inventory where sku_id in ("
				+ skuIds + ");";
		DBUtilities.exUpdateQuery(deleteQueryATP, "myntra_atp");
		DBUtilities.exUpdateQuery(deleteQueryIMS, "myntra_ims");
	}

}
