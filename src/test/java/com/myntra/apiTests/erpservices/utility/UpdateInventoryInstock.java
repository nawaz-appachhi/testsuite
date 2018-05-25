package com.myntra.apiTests.erpservices.utility;

import java.io.IOException;
import java.util.*;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.testbase.BaseTest;

/**
 * @author santwana.samantray
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })

public class UpdateInventoryInstock extends BaseTest {
	static Logger log = Logger.getLogger(UpdateInventoryInstock.class);

	@Test
	public void updateInventory( )
			throws JsonParseException, JsonMappingException, IOException, JAXBException, InterruptedException {

		String sku_id = System.getenv("SKU_id");

		//String wh_id = System.getenv("Warehouse_id");
		//String quantity = System.getenv("Quantity");
		//String seller_id = System.getenv("Seller_id");
		//String sku_id = System.getenv("SKU_id");

				String wh_id = System.getenv("Warehouse_id");
				String quantity = System.getenv("Quantity");
				String seller_id = System.getenv("Seller_id");
				String bfc=System.getenv("Blocked Future Count");
				String boc =System.getenv("Blocked Order Count");
		
		String query="update seller_item_master set seller_id=" +seller_id+ " where sku_id="+sku_id;
		String query0 = "update inventory set enabled=0 where `seller_id` <> " + seller_id + " and sku_id=" + sku_id;
		String query1 = "update inventory set enabled=1 where `seller_id` = " + seller_id + " and sku_id=" + sku_id;
		String query2 = "update inventory set inventory_count= " + quantity + ", available_in_warehouses= " + wh_id+", blocked_order_count ="+boc+" , blocked_future_count= "+bfc+ " where sku_id= " + sku_id + " and seller_id= " + seller_id;
		String query3 = "delete from wh_inventory where warehouse_id="+ wh_id+ " and sku_id= "+sku_id+" and seller_id="+seller_id;
		String query4 = "INSERT INTO `wh_inventory` (`id`, `warehouse_id`, `warehouse_name`, `store_id`, `supply_type`, `sku_id`, `sku_code`, `inventory_count`, `blocked_order_count`, `created_by`, `created_on`, `last_modified_on`, `last_synced_on`, `pushed_order_count`, `version`, `vendor_id`, `seller_id`, `proc_sla`, `override_auto_realloc`) VALUES (null, "
				+ wh_id + ", 'Bangalore - New Whitefield W/H', 1, 'ON_HAND', " + sku_id + ", 'null', " + quantity
				+ ","+ boc+", 'system', '2016-10-12 11:41:11', '2016-10-12 11:41:11', NULL, 0, 35, NULL," + seller_id
				+ ", 0, 1);";
		String query5 = "delete from `core_inventory_counts` where sku_id="+sku_id+" and warehouse_id="+wh_id;
		String query6 = "INSERT INTO `core_inventory_counts` (`id`, `warehouse_id`, `warehouse_name`, `quality`, `sku_id`, `sku_code`, `inv_count`, `blocked_manually_count`, `blocked_missed_count`, `created_by`, `created_on`, `last_modified_on`, `version`, `blocked_processing_count`) VALUES (null, "
				+ wh_id + ", 'Delhi-Gurgaon', 'Q1', " + sku_id + ", NULL, " + quantity
				+ ", 0, 0, 'kishore', '2013-07-05 11:08:18', '2016-02-12 14:37:28', 420, 0);";
		String query7="select * from seller_item_master where sku_id="+sku_id;
		Map simdata= new HashMap();
		simdata=DBUtilities.exSelectQueryForSingleRecord(query7, "myntra_seller1");
		String style_id=simdata.get("style_id").toString();
		DBUtilities.exUpdateQuery(query, "myntra_seller1");
		DBUtilities.exUpdateQuery(query0, "myntra_atp");
		DBUtilities.exUpdateQuery(query1,"myntra_atp");
		DBUtilities.exUpdateQuery(query2, "myntra_atp");
		DBUtilities.exUpdateQuery(query3, "myntra_ims");
		DBUtilities.exUpdateQuery(query4, "myntra_ims");
		DBUtilities.exUpdateQuery(query5, "myntra_ims");
		DBUtilities.exUpdateQuery(query6, "myntra_ims");
		StyleServiceApiHelper styleServiceApiHelper = new StyleServiceApiHelper();
		styleServiceApiHelper.styleReindexForStyleIDs(style_id);
		

	}
	/*@Test
	public void updateInventorysku() 
			throws JsonParseException, JsonMappingException, IOException, JAXBException, InterruptedException {
		updateInventory("19910","28","100","73");
	}*/
	}

