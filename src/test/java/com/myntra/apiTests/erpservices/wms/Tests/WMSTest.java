package com.myntra.apiTests.erpservices.wms.Tests;

import com.myntra.apiTests.erpservices.bounty.BountyServiceXPaths;
import com.myntra.apiTests.erpservices.utility.PurchaseOrderUtil;
import com.myntra.apiTests.erpservices.wms.WMSHelper;
import com.myntra.apiTests.erpservices.wms.dp.WMSPipelineDP;
import com.myntra.apiTests.portalservices.commons.XPathReader;
import com.myntra.client.wms.response.*;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;
import javax.xml.xpath.XPathConstants;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static com.myntra.lordoftherings.boromir.DBUtilities.*;

/**
 * @author santwana.samantray
 *
 */

/*
 * 
 * dependency on WMSPipelineDP.java, WMSPipelineHelper.java,
 * BountyServiceXPaths.java
 *
 */

// all the enums for XPaths used in this class are defined in
// BountyServiceXPath.java


	//Inward usecase for two bins in blr wh - one for MJIM and one MJIJ. Pick aisle details and pick sku details
	//Sizing qc


public class WMSTest extends BaseTest {
	 	private static PurchaseOrderUtil purchaseOrderUtil = new PurchaseOrderUtil();
		private static Logger log = Logger.getLogger(WMSTest.class);
		private static WMSHelper WMSItemTransitionHelper1 = new WMSHelper();
		private SoftAssert sft;
		private static Long buyerId_3974= 3974L;
		private String skuDefault="12070042";
		private String [] skuIds={"4051","4052","4053"};
	//validate in item and item info against the owner, buyer id and same po_skuid , parent sku_id and others. Item in not recvd status
	//Open question should item qc be allowed for items not in lot,invoice. And Should GRN get generated for the same dataset
// Qty in item, lot, grn


	@BeforeClass(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" })
	public void insertitems() {
		log.info("Entered insertion loop");
		String queryToDeleteData = "delete from item where id in (1000121,1000122,1000123,1000124,1000125,1000126,1000127,1000128,1000129,1000130,1000131,1000132,1000158,1000134,1000135,1000136,1000137,1000138,1000139,1000140,1000141,1000142,1000143,1000144,1000145,1000146,1000147,1000148,1000149,1000150,1000151,1000152,1000153,1000155,1000156,1000157,1000154,1000159,1000160,1000161,1000162,1000163,1000164,1000165,9100000086116)";
		String queryToDeleteItemInfo="delete from item_info where item_id in (1000121,1000122,1000123,1000124,1000125,1000126,1000127,1000128,1000129,1000130,1000131,1000132,1000133,1000158,1000134,1000135,1000136,1000137,1000138,1000139,1000140,1000141,1000142,1000143,1000144,1000145,1000146,1000147,1000148,1000149,1000150,1000151,1000152,1000153,1000155,1000156,1000157,1000154,1000159,1000158,1000160,1000161,1000162,1000163,1000164,1000165,9100000086116)";
		exUpdateQuery(queryToDeleteData, "myntra_wms");
		exUpdateQuery(queryToDeleteItemInfo, "myntra_wms");
		String insertItemInfo="INSERT INTO `item_info` ( `item_id`, `item_action_status`, `task_id`, `order_barcode`, `created_on`, `created_by`, `last_modified_on`, `version`, `order_id`, `invoice_sku_id`, `agreement_type`, `buyer_id`, `manufacturing_date`)"
				+"VALUES(1000121, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000122, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000123, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000124, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000125, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000126, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000127, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000128, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000129, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000130, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000131, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000132, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000133, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000134, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000135, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000136, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000137, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000138, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000139, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000140, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000141, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000142, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000143, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000144, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000145, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000146, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000147, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000148, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000149, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000150, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000151, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000152, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000153, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000154, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000155, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000156, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000157, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000158, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000160, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000162, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000163, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(1000164, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"
				+"(9100000086116, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL),"

				+"(1000165, 'NEW', NULL, NULL, '2017-08-10 17:51:54', 'erpMessageQueue', '2017-08-10 17:51:54', 0, NULL, NULL, 'OUTRIGHT', 2297, NULL);";
		String Query1 = "insert into item (id,barcode,sku_id,quality,item_status,warehouse_id,enabled,po_id,po_barcode,po_sku_id,lot_id,lot_barcode,comments,bin_id)"
				+ "values(1000121,'1000121',6258,'Q1','ACCEPTED_RETURNS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',128),"
				+ "(1000122,'1000122',6258,'Q1','ACCEPTED_RETURNS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',128),"
				+ "(1000123,'1000123',6258,'Q1','CUSTOMER_RETURNED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',128),"
				+ "(1000124,'1000124',6258,'Q1','CUSTOMER_RETURNED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',128),"
				+ "(1000125,'1000125',6258,'Q1','DETACHED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',50614),"
				+ "(1000126,'1000126',6258,'Q1','DETACHED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',50614),"
				+ "(1000150,'1000150',6258,'Q1','DETACHED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',50614),"
				+ "(1000127,'1000127',6258,'Q1','FOUND',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
				+ "(1000128,'1000128',6258,'Q1','FOUND',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
				+ "(1000129,'1000129',6258,'Q1','ISSUED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48849),"
				+ "(1000130,'1000130',6258,'Q1','ISSUED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48849),"
				+ "(1000131,'1000131',6258,'Q1','ISSUED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48849),"
				+ "(1000132,'1000132',6258,'Q1','NOT_FOUND',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
				+ "(1000158,'1000158',6258,'Q1','NOT_RECEIVED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
				+ "(9100000086116,'9100000086116',12070042,'Q1','NOT_RECEIVED',28,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
				+ "(1000134,'1000134',6258,'Q1','PROCESSING',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48849),"
				+ "(1000135,'1000135',6258,'Q1','PROCESSING',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48849),"
				+ "(1000136,'1000136',6258,'Q1','RETURN_FROM_OPS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
				+ "(1000137,'1000137',6258,'Q1','RETURN_FROM_OPS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
				+ "(1000138,'1000138',6258,'Q1','SHRINKAGE',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',221),"
				+ "(1000139,'1000139',6258,'Q1','STORED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',403),"
				+ "(1000140,'1000140',6258,'Q1','STORED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',403),"
				+ "(1000141,'1000141',6258,'Q1','STORED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',403),"
				+ "(1000142,'1000142',6258,'Q1','STORED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',403),"
				+ "(1000143,'1000143',6258,'Q1','TRANSIT',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',50614),"
				+ "(1000144,'1000144',6258,'Q1','TRANSIT',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',50614),"
				+ "(1000149,'1000149',6258,'Q1','STORED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
				+ "(1000145,'1000145',6258,'Q1','RETURN_FROM_OPS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',403),"
				+ "(1000146,'1000146',6258,'Q1','RETURN_FROM_OPS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
				+ "(1000147,'1000147',6258,'Q1','FOUND',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
				+ "(1000148,'1000148',6258,'Q1','STORED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
				+ "(1000151,'1000151',6258,'Q1','STORED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
				+ "(1000152,'1000152',6258,'Q1','FOUND',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
				+ "(1000153,'1000153',6258,'Q1','NOT_FOUND',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
				+ "(1000154,'1000154',6258,'Q1','PROCESSING',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48849),"
				+ "(1000155,'1000155',6258,'Q2','STORED',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
				+ "(1000160,'1000160',6258,'Q1','STORED',36,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
				+ "(1000162,'1000162',6258,'Q1','STORED',36,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
				+ "(1000163,'1000163',6258,'Q1','STORED',36,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
				+ "(1000165,'1000165',6258,'Q1','STORED',36,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
				+ "(1000156,'1000156',6258,'Q2','FOUND',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',186),"
				+ "(1000157,'1000157',6258,'Q2','RETURN_FROM_OPS',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48574),"
				+ "(1000159,'1000159',6258,'Q1','PROCESSING',1,1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item',48849)";

		exUpdateQuery(Query1, "myntra_wms");
		exUpdateQuery(insertItemInfo, "myntra_wms");


		String Query2 = "update core_inventory_counts set inv_count =30, blocked_manually_count =0,blocked_missed_count=6,blocked_processing_count=13,owner_partner_id=2297  where sku_id = 6258 and warehouse_id =1 and quality='Q1'";
		exUpdateQuery(Query2, "myntra_ims");
		log.info("core_inv_count updated");

		String Query3 = "update wh_inventory set inventory_count =11 ,blocked_order_count =1 where sku_id = 6258 and warehouse_id =1 and seller_id=21";
		exUpdateQuery(Query3, "myntra_ims");
		log.info("wh_inv updated");
		String Query4 = "update inventory set inventory_count =11, blocked_order_count =1 where sku_id = 6258 and seller_id=21 ";
		exUpdateQuery(Query4, "myntra_atp");
		log.info("atp updated");

		String Query5 = "update core_inventory_counts set inv_count =30, blocked_manually_count =0,blocked_missed_count=6,blocked_processing_count=13,owner_partner_id=2297  where sku_id = 6258 and warehouse_id =1 and quality='Q2'";
		exUpdateQuery(Query5, "myntra_ims");
		log.info("core_inv_count updated for q2 ");
		String query1 = "update wh_inventory set inventory_count=1000 where sku_id in (66321,90603,66407,24318,1225025,3864,3869,3870,3871,3857,3858,3859,3860) and store_id =1 and warehouse_id in (36,28) ";
		String query2 = "update inventory set inventory_count=2000 where sku_id in (66321,90603,66407,24318,1225025,3864,3869,3870,3871,3857,3858,3859,3860)";
		String query3 = "update wh_inventory set blocked_order_count=100 where sku_code in ('RDTPFOSH79902','PUMATSRM01391','PUMALNGP01005')and store_id in(2,3) and warehouse_id in (36,28)";
		String query4 = "update wh_inventory set inventory_count=10 where sku_code='KNKHTSHT02241'and store_id =1 and warehouse_id in (36,28);";
		exUpdateQuery(query1, "myntra_ims");
		exUpdateQuery(query3, "myntra_ims");
		exUpdateQuery(query2, "myntra_atp");
		exUpdateQuery(query4, "myntra_ims");

		log.info("Insertion and update done sucessfully");

	}

		@Test(groups = { "Regression" }, dataProvider = "inwardItemMarkQCPass", dataProviderClass = WMSPipelineDP.class)
		public void inwardItemMarkQCPass(String lot_code, String item_code, String carton_code, String status,
				String statusCode, String statusMessage) throws InterruptedException, UnsupportedEncodingException, JAXBException {
			String query2 = "update core_inventory_counts set blocked_manually_count=0,inv_count=0 where sku_id="+skuDefault+" and quality in('Q1','Q2') and owner_partner_id=2297";
			String query1 = "update item set item_status ='NOT_RECEIVED', bin_id=NULL,quality='Q1',enabled=0,inwarded_on=NULL,carton_barcode=NULL where id=9100000086116";
			exUpdateQuery(query1, "wms");
			exUpdateQuery(query2, "ims");

			markitemqcpass(lot_code, carton_code, item_code, status, statusCode, statusMessage);
			Thread.sleep(5000);

			Map<String, Object> hm = new HashMap<>();
			hm = exSelectQueryForSingleRecord(
					"select * from core_inventory_counts where sku_id= "+skuDefault+" and quality='Q1' and warehouse_id=1 and owner_partner_id=2297", "ims");
			String inventory_count = hm.get("inv_count").toString();
			String blockManualCount = hm.get("blocked_manually_count").toString();
			Assert.assertEquals(inventory_count, "1", "inventory count in core_inv_count post inward is:");
			Assert.assertEquals(blockManualCount, "1", "block manual count in core_inv_count post inward is:");

		}

		@Test(groups = { "Regression" }, dataProvider = "inwardItemMarkQCFail", dataProviderClass = WMSPipelineDP.class)
		public void inwardItemMarkQCFail(String lot_code, String item_code, String carton_code, String status,
				String statusCode, String statusMessage, String rejectreason) throws InterruptedException, UnsupportedEncodingException, JAXBException {
			String query2 = "update core_inventory_counts set blocked_manually_count=0,inv_count=0 where sku_id="+skuDefault+" and quality in('Q1','Q2') and owner_partner_id=2297";
			exUpdateQuery(query2, "ims");
			String query1 = "update item set item_status ='NOT_RECEIVED', bin_id=NULL,quality='Q1',enabled=0,inwarded_on=NULL,carton_barcode=NULL,reject_reason_code=NULL,reject_reason_description=NULL where id=9100000086116";
			exUpdateQuery(query1, "wms");
			markitemqcfail(lot_code, carton_code, item_code, status, statusCode, statusMessage);
			Thread.sleep(5000);
			Map<String, Object> hm = new HashMap<String, Object>();
			hm = exSelectQueryForSingleRecord(
					"select * from core_inventory_counts where sku_id= "+skuDefault+" and quality='Q2' and warehouse_id=1 and owner_partner_id=2297" +
							"", "ims");
			String inventory_count = hm.get("inv_count").toString();
			String blockManualCount = hm.get("blocked_manually_count").toString();
			Assert.assertEquals(inventory_count, "1", "inventory count in core_inv_count post inward is:");
			Assert.assertEquals(blockManualCount, "1", "block manual count in core_inv_count post inward is:");
		}

		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "Validate_GRN_API_DP", dataProviderClass = WMSPipelineDP.class)
		public void validateGRNTest(String q, String f, String start, String fetchSize, String sortBy, String sortOrder,
				String status, String statusCode, String statusMessage, String statusType, String totalCount) {
			RequestGenerator getOrderReqGen = WMSHelper.validate_GRN_API_Helper(q, f, start, fetchSize, sortBy, sortOrder);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService validateGRNTest API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService validateGRNTest API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("not working properly", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);
			Boolean isStatusNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));

			AssertJUnit.assertTrue("status node doesn't exists", isStatusNodeExists);

			Boolean isStatusCodeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusCode node doesn't exists", isStatusCodeNodeExists);

			Boolean isStatusMsgNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusMessage node doesn't exists", isStatusMsgNodeExists);

			Boolean isStatusTypeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusType node doesn't exists", isStatusTypeNodeExists);

			Boolean isStatusTotalCountNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.totalCount node doesn't exists", isStatusTotalCountNodeExists);

			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));

			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));

			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

			String receivedStatusType = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_VALUE.getXmlPath()));

			AssertJUnit.assertEquals("statusType is not proper", receivedStatusType, statusType);

			String receivedTotalCount = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_VALUE.getXmlPath()));

			AssertJUnit.assertEquals("Total count is not proper", receivedTotalCount, totalCount);
		}

		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "Validate_IOS_API_DP", dataProviderClass = WMSPipelineDP.class)
		public void validateIOSTest(String q, String f, String start, String fetchSize, String status, String statusCode,
				String statusMessage, String statusType, String stausTotalCount, String createdBy, String createdOn,
				String id, String approver, String barcode, String description, String orderStatus, String orderType,
				String internalOrderTotalCount) {
			RequestGenerator getOrderReqGen = WMSHelper.validate_IOS_API_Helper(q, f, start, fetchSize);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService validateIOSTest API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService validateIOSTest API response :\n\n" + getOrderResponse + "\n");

			// validation of status node
			AssertJUnit.assertEquals("status is improper", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);

			Boolean isStatusNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status node doesn't exists", isStatusNodeExists);

			Boolean isStatusCodeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusCode node doesn't exists", isStatusCodeNodeExists);

			Boolean isStatusMsgNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusMessage node doesn't exists", isStatusMsgNodeExists);

			Boolean isStatusTypeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusType node doesn't exists", isStatusTypeNodeExists);

			Boolean isStatusTotalCountNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.totalCount node doesn't exists", isStatusTotalCountNodeExists);

			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

			String receivedStatusType = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusType is not proper", receivedStatusType, statusType);

			String receivedStatusTotalCount = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("Total count is not proper", receivedStatusTotalCount, stausTotalCount);

			// validation of internalOrder node

			Boolean isCreatedByNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.IOS_CREATED_BY_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("internalOrder.crreatedBy node doesn't exists", isCreatedByNodeExists);

			Boolean isCreatedOnNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.IOS_CREATED_ON_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("internalOrder.createdOn node doesn't exists", isCreatedOnNodeExists);

			Boolean isIdNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.IOS_ID_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("internalOrder.ID node doesn't exists", isIdNodeExists);

			Boolean isApproverNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.IOS_APPROVER_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("internalOrder.approver node doesn't exists", isApproverNodeExists);

			Boolean isBarcodeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.IOS_BARCODE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("internalOrder.barcode node doesn't exists", isBarcodeNodeExists);

			Boolean isDescriptionNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.IOS_DESCRIPTION_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("internalOrder.description node doesn't exists", isDescriptionNodeExists);

			Boolean isOrderTypeNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.IOS_ORDER_TYPE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("internalOrder.orderType node doesn't exists", isOrderTypeNodeExists);

			Boolean isOrderStatusNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.IOS_ORDER_STATUS_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("internalOrder.orderStatus node doesn't exists", isOrderStatusNodeExists);

			Boolean isInternalOrderTotalCountNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.IOS_INTERNAL_ORDER_TOTAL_COUNT_NODE_IS_EXISTS.getXmlPath(),
							XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("internalOrder.totalCount node doesn't exists", isInternalOrderTotalCountNodeExists);

			String receivedCreatedBy = String
					.valueOf(xPathReader.read(BountyServiceXPaths.IOS_CREATED_BY_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("internalOrder.createdBy is not proper", receivedCreatedBy, createdBy);

			String receivedCreatedOn = String
					.valueOf(xPathReader.read(BountyServiceXPaths.IOS_CREATED_ON_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("internalOrder.createdOn is not proper", receivedCreatedOn, createdOn);

			String receivedId = String.valueOf(xPathReader.read(BountyServiceXPaths.IOS_ID_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("internalOrder.ID is not proper", receivedId, id);

			String receivedApprover = String
					.valueOf(xPathReader.read(BountyServiceXPaths.IOS_APPROVER_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("internalOrder.Approver is not proper", receivedApprover, approver);

			String receivedBarcode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.IOS_BARCODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("internalOrder.barcode is not proper", receivedBarcode, barcode);

			String receivedDescription = String
					.valueOf(xPathReader.read(BountyServiceXPaths.IOS_DESCRIPTION_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("internalOrder.Description is not proper", receivedDescription, description);

			String receivedOrderStatus = String
					.valueOf(xPathReader.read(BountyServiceXPaths.IOS_ORDER_STATUS_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("internalOrder.orderStatus is not proper", receivedOrderStatus, orderStatus);

			String receivedOrderType = String
					.valueOf(xPathReader.read(BountyServiceXPaths.IOS_ORDER_TYPE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("internalOrder.orderType is not proper", receivedOrderType, orderType);

			String receivedInternalOrderTotalCount = String
					.valueOf(xPathReader.read(BountyServiceXPaths.IOS_INTERNAL_ORDER_TOTAL_COUNT_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("internalOrder.totalCount is not proper", receivedInternalOrderTotalCount,
					internalOrderTotalCount);
		}

		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "Validate_Invoices_API_DP", dataProviderClass = WMSPipelineDP.class)
		public void validateInvoicesTest(String q, String f, String start, String fetchSize, String sortBy,
				String sortOrder, String status, String statusCode, String statusMessage, String statusType,
				String stausTotalCount, String createdBy, String id) {
			RequestGenerator getOrderReqGen = WMSHelper.validate_Invoices_API_Helper(q, f, start, fetchSize, sortBy,
					sortOrder);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService validateIvoicesTest API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService validateInvoicesTest API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("status is improper", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);

			// validation of status node

			Boolean isStatusNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status node doesn't exists", isStatusNodeExists);

			Boolean isStatusCodeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusCode node doesn't exists", isStatusCodeNodeExists);

			Boolean isStatusMsgNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusMessage node doesn't exists", isStatusMsgNodeExists);

			Boolean isStatusTypeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusType node doesn't exists", isStatusTypeNodeExists);

			Boolean isStatusTotalCountNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.totalCount node doesn't exists", isStatusTotalCountNodeExists);

			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

			String receivedStatusType = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusType is not proper", receivedStatusType, statusType);

			String receivedStatusTotalCount = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("Total count is not proper", receivedStatusTotalCount, stausTotalCount);

			// validation of data node
			/*
			 * Boolean isDataNodeExists = Boolean.valueOf(String.valueOf(xPathReader
			 * .read(BountyServiceXPaths.INVOICES_DATA_NODE_IS_EXISTS .getXmlPath(),
			 * XPathConstants.BOOLEAN))); AssertJUnit.assertTrue(
			 * "data node doesn't exists", isDataNodeExists);
			 *
			 * Boolean isInvoiceNodeExists = Boolean.valueOf(String
			 * .valueOf(xPathReader.read(
			 * BountyServiceXPaths.INVOICES_INVOICE_NODE_IS_EXISTS .getXmlPath(),
			 * XPathConstants.BOOLEAN))); AssertJUnit.assertTrue(
			 * "data.invoice node doesn't exists", isInvoiceNodeExists);
			 *
			 * String receivedCreatedByNodeValue = String.valueOf(xPathReader
			 * .read(BountyServiceXPaths.INVOICES_CREATEDBY_NODE_VALUE
			 * .getXmlPath())); AssertJUnit.assertEquals(
			 * "data.invoice.createdBy node is not proper",
			 * receivedCreatedByNodeValue, createdBy);
			 *
			 * String receivedIdNodeValue = String.valueOf(xPathReader
			 * .read(BountyServiceXPaths.INVOICES_ID_NODE_VALUE.getXmlPath()));
			 * AssertJUnit.assertEquals("data.invoice.id node is not proper",
			 * receivedIdNodeValue, id);
			 */

		}

		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "Validate_Items_API_DP", dataProviderClass = WMSPipelineDP.class)
		public void validateItemsTest(String q, String start, String fetchSize, String sortBy, String sortOrder,
				String status, String statusCode, String statusMessage, String statusType, String stausTotalCount,
				String createdBy, String id) {
			RequestGenerator getOrderReqGen = WMSHelper.validate_Items_API_Helper(q, start, fetchSize, sortBy, sortOrder);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService validateItemsTest API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService validateItemsTest API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("status is improper", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);

			// validation of status node

			Boolean isStatusNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status node doesn't exists", isStatusNodeExists);

			Boolean isStatusCodeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusCode node doesn't exists", isStatusCodeNodeExists);

			Boolean isStatusMsgNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusMessage node doesn't exists", isStatusMsgNodeExists);

			Boolean isStatusTypeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusType node doesn't exists", isStatusTypeNodeExists);

			Boolean isStatusTotalCountNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.totalCount node doesn't exists", isStatusTotalCountNodeExists);

			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

			String receivedStatusType = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusType is not proper", receivedStatusType, statusType);

			/*String receivedStatusTotalCount = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("Total count is not proper", receivedStatusTotalCount, stausTotalCount);*/

			/*
			 * //validation of data node
			 *
			 * Boolean isDataNodeExists = Boolean
			 * .valueOf(String.valueOf(xPathReader
			 * .read(BountyServiceXPaths.ITEMS_DATA_NODE_IS_EXISTS .getXmlPath(),
			 * XPathConstants.BOOLEAN))); AssertJUnit.assertTrue(
			 * "data node doesn't exists", isDataNodeExists);
			 *
			 * Boolean isItemNodeExists = Boolean
			 * .valueOf(String.valueOf(xPathReader
			 * .read(BountyServiceXPaths.ITEMS_ITEM_NODE_IS_EXISTS .getXmlPath(),
			 * XPathConstants.BOOLEAN))); AssertJUnit.assertTrue(
			 * "data.item node doesn't exists", isItemNodeExists);
			 *
			 * String receivedCreatedByNodeValue =
			 * String.valueOf(xPathReader.read(BountyServiceXPaths
			 * .ITEMS_CREATEDBY_NODE_VALUE.getXmlPath())); AssertJUnit.assertEquals(
			 * "data.item.createdBy node is not proper" ,receivedCreatedByNodeValue,
			 * createdBy);
			 *
			 * String receivedIdNodeValue =
			 * String.valueOf(xPathReader.read(BountyServiceXPaths
			 * .ITEMS_ID_NODE_VALUE.getXmlPath())); AssertJUnit.assertEquals(
			 * "data.item.id node is not proper" ,receivedIdNodeValue, id);
			 */

		}

		// no more validation required
		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "Validate_Orders_API_DP", dataProviderClass = WMSPipelineDP.class)
		public void validateOrdersTest(String q_portal_order_release_id, String q_order_id, String start, String fetchSize,
				String sortBy, String sortOrder, String status, String statusCode, String statusMessage, String statusType,
				String stausTotalCount) {
			RequestGenerator getOrderReqGen = WMSHelper.validate_Orders_API_Helper(q_portal_order_release_id, q_order_id,
					start, fetchSize, sortBy, sortOrder);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService validateOrdersTest API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService validateOrdersTest API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("status is improper", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);

			// validation of status node

			Boolean isStatusNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status node doesn't exists", isStatusNodeExists);

			Boolean isStatusCodeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusCode node doesn't exists", isStatusCodeNodeExists);

			Boolean isStatusMsgNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusMessage node doesn't exists", isStatusMsgNodeExists);

			Boolean isStatusTypeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusType node doesn't exists", isStatusTypeNodeExists);

			Boolean isStatusTotalCountNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.totalCount node doesn't exists", isStatusTotalCountNodeExists);

			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

			String receivedStatusType = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusType is not proper", receivedStatusType, statusType);

			String receivedStatusTotalCount = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("Total count is not proper", receivedStatusTotalCount, stausTotalCount);

			// validation of internalOrder node

		}

		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "Validate_SKUs_API_DP", dataProviderClass = WMSPipelineDP.class)
		public void validateSKUsTest(String q, String f, String start, String fetchSize, String sortBy, String sortOrder,
				String status, String statusCode, String statusMessage, String statusType, String stausTotalCount,
				String id, String code) {
			RequestGenerator getOrderReqGen = WMSHelper.validate_SKUs_API_Helper(q, f, start, fetchSize, sortBy, sortOrder);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService validateSKUsTest API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService validateSKUsTest API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("status is improper", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);

			// validation of status node

			Boolean isStatusNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status node doesn't exists", isStatusNodeExists);

			Boolean isStatusCodeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusCode node doesn't exists", isStatusCodeNodeExists);

			Boolean isStatusMsgNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusMessage node doesn't exists", isStatusMsgNodeExists);

			Boolean isStatusTypeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusType node doesn't exists", isStatusTypeNodeExists);

			Boolean isStatusTotalCountNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.totalCount node doesn't exists", isStatusTotalCountNodeExists);

			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

			String receivedStatusType = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusType is not proper", receivedStatusType, statusType);

			String receivedStatusTotalCount = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("Total count is not proper", receivedStatusTotalCount, stausTotalCount);

			// validation of data node

			Boolean isDataNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.SKUS_DATA_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("data node doesn't exists", isDataNodeExists);

			Boolean isSkuNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.SKUS_SKU_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("data.sku node doesn't exists", isSkuNodeExists);

			String receivedIdNodeValue = String
					.valueOf(xPathReader.read(BountyServiceXPaths.SKUS_ID_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("data.sku.id node is not proper", receivedIdNodeValue, id);

			String receivedcodeNodeValue = String
					.valueOf(xPathReader.read(BountyServiceXPaths.SKUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("data.sku.code node is not proper", receivedcodeNodeValue, code);

		}

		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "Validate_Rejected_Items_API_DP", dataProviderClass = WMSPipelineDP.class)
		public void validateRejectedItemsTest(String q, String start, String fetchSize, String sortBy, String sortOrder,
				String status, String statusCode, String statusMessage, String statusType, String stausTotalCount,
				String createdBy, String id) {
			RequestGenerator getOrderReqGen = WMSHelper.validate_Rejected_Items_API_Helper(q, start, fetchSize, sortBy,
					sortOrder);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info(
					"\nPrinting WMSService validateRejectedItemsTest API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService validateRejectedItemsTest API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("status is improper", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);

			// validation of status node

			Boolean isStatusNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status node doesn't exists", isStatusNodeExists);

			Boolean isStatusCodeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusCode node doesn't exists", isStatusCodeNodeExists);

			Boolean isStatusMsgNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusMessage node doesn't exists", isStatusMsgNodeExists);

			Boolean isStatusTypeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusType node doesn't exists", isStatusTypeNodeExists);

			Boolean isStatusTotalCountNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.totalCount node doesn't exists", isStatusTotalCountNodeExists);

			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

			String receivedStatusType = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusType is not proper", receivedStatusType, statusType);



			// validation of data node

			/*
			 * Boolean isDataNodeExists = Boolean
			 * .valueOf(String.valueOf(xPathReader
			 * .read(BountyServiceXPaths.RI_DATA_NODE_IS_EXISTS .getXmlPath(),
			 * XPathConstants.BOOLEAN))); AssertJUnit.assertTrue(
			 * "data node doesn't exists", isDataNodeExists);
			 *
			 * Boolean isRejectedItemNodeExists = Boolean
			 * .valueOf(String.valueOf(xPathReader
			 * .read(BountyServiceXPaths.RI_REJECTED_ITEM_NODE_IS_EXISTS
			 * .getXmlPath(), XPathConstants.BOOLEAN))); AssertJUnit.assertTrue(
			 * "data.rejectedItem node doesn't exists", isRejectedItemNodeExists);
			 *
			 * String receivedCreatedByNodeValue =
			 * String.valueOf(xPathReader.read(BountyServiceXPaths
			 * .RI_CREATEDBY_NODE_VALUE.getXmlPath())); AssertJUnit.assertEquals(
			 * "data.rejectedItem.createdBy node is not proper"
			 * ,receivedCreatedByNodeValue, createdBy);
			 *
			 * String receivedcodeNodeValue =
			 * String.valueOf(xPathReader.read(BountyServiceXPaths
			 * .RI_ID_NODE_VALUE.getXmlPath())); AssertJUnit.assertEquals(
			 * "data.rejectedItem.item.id node is not proper"
			 * ,receivedcodeNodeValue, id);
			 */

		}

		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "Validate_RO_API_DP", dataProviderClass = WMSPipelineDP.class)
		public void validateROTest(String q_barcode, String q_roStatus, String start, String fetchSize, String sortBy,
				String sortOrder, String status, String statusCode, String statusMessage, String statusType,
				String stausTotalCount, String createdBy, String id) {
			RequestGenerator getOrderReqGen = WMSHelper.validate_RO_API_Helper(q_barcode, q_roStatus, start, fetchSize,
					sortBy, sortOrder);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService validateROTest API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService validateROTest API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("status is improper", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);

			// validation of status node

			Boolean isStatusNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status node doesn't exists", isStatusNodeExists);

			Boolean isStatusCodeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusCode node doesn't exists", isStatusCodeNodeExists);

			Boolean isStatusMsgNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusMessage node doesn't exists", isStatusMsgNodeExists);

			Boolean isStatusTypeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusType node doesn't exists", isStatusTypeNodeExists);

			Boolean isStatusTotalCountNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.totalCount node doesn't exists", isStatusTotalCountNodeExists);

			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

			String receivedStatusType = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusType is not proper", receivedStatusType, statusType);

			String receivedStatusTotalCount = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_VALUE.getXmlPath()));

			// log.info(".................................." +
			// receivedStatusTotalCount);
			AssertJUnit.assertEquals("Total count is not proper", receivedStatusTotalCount, stausTotalCount);

			// validation of data node

			/*
			 * Boolean isDataNodeExists = Boolean
			 * .valueOf(String.valueOf(xPathReader
			 * .read(BountyServiceXPaths.RO_DATA_NODE_IS_EXISTS .getXmlPath(),
			 * XPathConstants.BOOLEAN))); AssertJUnit.assertTrue(
			 * "data node doesn't exists", isDataNodeExists);
			 *
			 * Boolean isRONodeExists = Boolean .valueOf(String.valueOf(xPathReader
			 * .read(BountyServiceXPaths.RO_RO_NODE_IS_EXISTS .getXmlPath(),
			 * XPathConstants.BOOLEAN))); AssertJUnit.assertTrue(
			 * "data.ro node doesn't exists", isRONodeExists);
			 *
			 * String receivedCreatedByNodeValue =
			 * String.valueOf(xPathReader.read(BountyServiceXPaths
			 * .RO_CREATEDBY_NODE_VALUE.getXmlPath())); AssertJUnit.assertEquals(
			 * "data.ro.createdBy node is not proper" ,receivedCreatedByNodeValue,
			 * createdBy);
			 *
			 * String receivedIDNodeValue =
			 * String.valueOf(xPathReader.read(BountyServiceXPaths
			 * .RO_ID_NODE_VALUE.getXmlPath())); AssertJUnit.assertEquals(
			 * "data.ro.id node is not proper" ,receivedIDNodeValue, id);
			 */

		}

		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "Validate_RGP_API_DP", dataProviderClass = WMSPipelineDP.class)
		public void validateRGPTest(String q, String f, String start, String fetchSize, String sortBy, String sortOrder,
				String status, String statusCode, String statusMessage, String statusType, String stausTotalCount,
				String createdBy, String barcode) {
			RequestGenerator getOrderReqGen = WMSHelper.validate_RGP_API_Helper(q, f, start, fetchSize, sortBy, sortOrder);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService validateRGPTest API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService validateRGPTest API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("status is improper", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);

			// validation of status node

			Boolean isStatusNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status node doesn't exists", isStatusNodeExists);

			Boolean isStatusCodeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusCode node doesn't exists", isStatusCodeNodeExists);

			Boolean isStatusMsgNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusMessage node doesn't exists", isStatusMsgNodeExists);

			Boolean isStatusTypeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusType node doesn't exists", isStatusTypeNodeExists);

			Boolean isStatusTotalCountNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.totalCount node doesn't exists", isStatusTotalCountNodeExists);

			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

			String receivedStatusType = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusType is not proper", receivedStatusType, statusType);

			String receivedStatusTotalCount = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_VALUE.getXmlPath()));

			// log.info(".................................." +
			// receivedStatusTotalCount);
			AssertJUnit.assertEquals("Total count is not proper", receivedStatusTotalCount, stausTotalCount);

			// inside data node validation

			Boolean isDataNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.RGP_DATA_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("data node doesn't exists", isDataNodeExists);

			Boolean isRGPNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.RGP_RGP_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("data.rgp node doesn't exists", isRGPNodeExists);

			String receivedCreatedByNodeValue = String
					.valueOf(xPathReader.read(BountyServiceXPaths.RGP_CREATEDBY_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("data.rgp.createdBy node is not proper", receivedCreatedByNodeValue, createdBy);

			String receivedBarcodeNodeValue = String
					.valueOf(xPathReader.read(BountyServiceXPaths.RGP_BARCODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("data.rgp.barcode node is not proper", receivedBarcodeNodeValue, barcode);

		}

		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "Validate_STN_API_DP", dataProviderClass = WMSPipelineDP.class)
		public void validateSTNTest(String q, String f, String start, String fetchSize, String sortBy, String sortOrder,
				String status, String statusCode, String statusMessage, String statusType, String stausTotalCount,
				String createdBy, String barcode) {
			RequestGenerator getOrderReqGen = WMSHelper.validate_STN_API_Helper(q, f, start, fetchSize, sortBy, sortOrder);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService validateSTNTest API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService validateSTNTest API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("status is improper", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);

			// validation of status node

			Boolean isStatusNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status node doesn't exists", isStatusNodeExists);

			Boolean isStatusCodeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusCode node doesn't exists", isStatusCodeNodeExists);

			Boolean isStatusMsgNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusMessage node doesn't exists", isStatusMsgNodeExists);

			Boolean isStatusTypeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusType node doesn't exists", isStatusTypeNodeExists);

			Boolean isStatusTotalCountNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.totalCount node doesn't exists", isStatusTotalCountNodeExists);

			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

			String receivedStatusType = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusType is not proper", receivedStatusType, statusType);

			String receivedStatusTotalCount = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_VALUE.getXmlPath()));

			// log.info(".................................." +
			// receivedStatusTotalCount);
			AssertJUnit.assertEquals("Total count is not proper", receivedStatusTotalCount, stausTotalCount);

			// inside data node validation

			Boolean isDataNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STN_DATA_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("data node doesn't exists", isDataNodeExists);

			Boolean isSTNNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STN_STN_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("data.stn node doesn't exists", isSTNNodeExists);

			String receivedCreatedByNodeValue = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STN_CREATEDBY_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("data.stn.createdBy node is not proper", receivedCreatedByNodeValue, createdBy);

			String receivedBarcodeNodeValue = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STN_BARCODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("data.stn.barcode node is not proper", receivedBarcodeNodeValue, barcode);
		}

		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "Validate_STNItems_API_DP", dataProviderClass = WMSPipelineDP.class)
		public void validateSTNItemsTest(String q, String f, String start, String fetchSize, String sortBy,
				String sortOrder, String status, String statusCode, String statusMessage, String statusType,
				String stausTotalCount, String itemId, String barcode) {
			RequestGenerator getOrderReqGen = WMSHelper.validate_STNItems_API_Helper(q, f, start, fetchSize, sortBy,
					sortOrder);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService validateSTNItemsTest API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService validateSTNItemsTest API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("status is improper", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);

			// validation of status node

			Boolean isStatusNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status node doesn't exists", isStatusNodeExists);

			Boolean isStatusCodeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusCode node doesn't exists", isStatusCodeNodeExists);

			Boolean isStatusMsgNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusMessage node doesn't exists", isStatusMsgNodeExists);

			Boolean isStatusTypeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusType node doesn't exists", isStatusTypeNodeExists);

			Boolean isStatusTotalCountNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.totalCount node doesn't exists", isStatusTotalCountNodeExists);

			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

			String receivedStatusType = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusType is not proper", receivedStatusType, statusType);

			String receivedStatusTotalCount = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_VALUE.getXmlPath()));

			// log.info(".................................." +
			// receivedStatusTotalCount);
			AssertJUnit.assertEquals("Total count is not proper", receivedStatusTotalCount, stausTotalCount);

			// inside data node validation
/*
			Boolean isDataNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STNITEMS_DATA_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("data node doesn't exists", isDataNodeExists);

			Boolean isSTNItemNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STNITEMS_STNITEM_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("data.stnItem node doesn't exists", isSTNItemNodeExists);

			Boolean isSTNNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STNITEMS_STN_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("data.stnItem.stn node doesn't exists", isSTNNodeExists);

			String receivedItemIdNodeValue = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STNITEMS_ITEMID_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("data.stnItem.itemId node is not proper", receivedItemIdNodeValue, itemId);

			String receivedBarcodeNodeValue = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STNITEMS_BARCODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("data.stnItem.stn.barcode node is not proper", receivedBarcodeNodeValue, barcode);*/
		}

		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "Validate_Station_Bins_API_DP", dataProviderClass = WMSPipelineDP.class)
		public void validateStationBinsTest(String q, String f, String start, String fetchSize, String sortBy,
				String sortOrder, String status, String statusCode, String statusMessage, String statusType,
				String stausTotalCount, String binBarcode, String name) {
			RequestGenerator getOrderReqGen = WMSHelper.validate_Station_Bins_API_Helper(q, f, start, fetchSize, sortBy,
					sortOrder);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			System.out
					.println("\nPrinting WMSService validateStationBinsTest API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService validateStationBinsTest API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("status is improper", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);

			// validation of status node

			Boolean isStatusNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status node doesn't exists", isStatusNodeExists);

			Boolean isStatusCodeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusCode node doesn't exists", isStatusCodeNodeExists);

			Boolean isStatusMsgNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusMessage node doesn't exists", isStatusMsgNodeExists);

			Boolean isStatusTypeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusType node doesn't exists", isStatusTypeNodeExists);

			Boolean isStatusTotalCountNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.totalCount node doesn't exists", isStatusTotalCountNodeExists);

			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

			String receivedStatusType = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusType is not proper", receivedStatusType, statusType);

			String receivedStatusTotalCount = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_VALUE.getXmlPath()));

			// log.info(".................................." +
			// receivedStatusTotalCount);
			AssertJUnit.assertEquals("Total count is not proper", receivedStatusTotalCount, stausTotalCount);

			// inside data node validation

			Boolean isDataNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.SB_DATA_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("data node doesn't exists", isDataNodeExists);

			Boolean isStationsBinNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.SB_STATIONSBIN_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("data.stationsBin node doesn't exists", isStationsBinNodeExists);

			Boolean isSectionExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.SB_SECTION_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("data.stationsBin.section node doesn't exists", isSectionExists);

			String receivedBinBarcodeNodeValue = String
					.valueOf(xPathReader.read(BountyServiceXPaths.SB_BIN_BARCODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("data.stationsBin.binBarcode node is not proper", receivedBinBarcodeNodeValue,
					binBarcode);

			String receivedNameNodeValue = String
					.valueOf(xPathReader.read(BountyServiceXPaths.SB_NAME_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("data.stationsBin.section.name node is not proper", receivedNameNodeValue, name);

		}

		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "Validate_Purchase_Orders_API_DP", dataProviderClass = WMSPipelineDP.class)
		public void validatePurchaseOrdersTest(String q, String f, String start, String fetchSize, String sortBy,
				String sortOrder, String status, String statusCode, String statusMessage, String statusType,
				String stausTotalCount, String createdBy, String id) {
			RequestGenerator getOrderReqGen = WMSHelper.validate_Purchase_Orders_API_Helper(q, f, start, fetchSize, sortBy,
					sortOrder);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info(
					"\nPrinting WMSService validatePurchaseOrdersTest API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService validatePurchaseOrdersTest API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("status is improper", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);

			// validation of status node

			Boolean isStatusNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status node doesn't exists", isStatusNodeExists);

			Boolean isStatusCodeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusCode node doesn't exists", isStatusCodeNodeExists);

			Boolean isStatusMsgNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusMessage node doesn't exists", isStatusMsgNodeExists);

			Boolean isStatusTypeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusType node doesn't exists", isStatusTypeNodeExists);

			Boolean isStatusTotalCountNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.totalCount node doesn't exists", isStatusTotalCountNodeExists);

			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

			String receivedStatusType = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusType is not proper", receivedStatusType, statusType);

			String receivedStatusTotalCount = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_VALUE.getXmlPath()));

			// log.info(".................................." +
			// receivedStatusTotalCount);
			AssertJUnit.assertEquals("Total count is not proper", receivedStatusTotalCount, stausTotalCount);

			// inside data node validation

			Boolean isDataNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.PO_DATA_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("data node doesn't exists", isDataNodeExists);

			Boolean isPONodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.PO_PO_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("data.po node doesn't exists", isPONodeExists);

			String receivedcreatedByNodeValue = String
					.valueOf(xPathReader.read(BountyServiceXPaths.PO_CREATEDBY_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("data.po.createdBy node is not proper", receivedcreatedByNodeValue, createdBy);

			String receivedIdNodeValue = String
					.valueOf(xPathReader.read(BountyServiceXPaths.PO_ID_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("data.po.id node is not proper", receivedIdNodeValue, id);

		}

		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "Validate_Purchase_Intent_API_DP", dataProviderClass = WMSPipelineDP.class)
		public void validatePurchaseIntentTest(String q, String f, String start, String fetchSize, String sortBy,
				String sortOrder, String status, String statusCode, String statusMessage, String statusType,
				String stausTotalCount, String createdBy, String id) {
			RequestGenerator getOrderReqGen = WMSHelper.validate_Purchase_Intent_API_Helper(q, f, start, fetchSize, sortBy,
					sortOrder);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info(
					"\nPrinting WMSService validatePurchaseIntentTest API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService validatePurchaseIntentTest API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("status is improper", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);

			// validation of status node

			Boolean isStatusNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status node doesn't exists", isStatusNodeExists);

			Boolean isStatusCodeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusCode node doesn't exists", isStatusCodeNodeExists);

			Boolean isStatusMsgNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusMessage node doesn't exists", isStatusMsgNodeExists);

			Boolean isStatusTypeNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.statusType node doesn't exists", isStatusTypeNodeExists);

			Boolean isStatusTotalCountNodeExists = Boolean.valueOf(String.valueOf(xPathReader
					.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("status.totalCount node doesn't exists", isStatusTotalCountNodeExists);

			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

			String receivedStatusType = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TYPE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusType is not proper", receivedStatusType, statusType);

			String receivedStatusTotalCount = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_TOTAL_COUNT_NODE_VALUE.getXmlPath()));

			// log.info(".................................." +
			// receivedStatusTotalCount);
			AssertJUnit.assertEquals("Total count is not proper", receivedStatusTotalCount, stausTotalCount);

			Boolean isDataNodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.PI_DATA_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("data node doesn't exists", isDataNodeExists);

			Boolean isPINodeExists = Boolean.valueOf(String.valueOf(
					xPathReader.read(BountyServiceXPaths.PI_PI_NODE_IS_EXISTS.getXmlPath(), XPathConstants.BOOLEAN)));
			AssertJUnit.assertTrue("data.pi node doesn't exists", isPINodeExists);

			String receivedcreatedByNodeValue = String
					.valueOf(xPathReader.read(BountyServiceXPaths.PI_CREATEDBY_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("data.pi.createdBy node is not proper", receivedcreatedByNodeValue, createdBy);

			String receivedIdNodeValue = String
					.valueOf(xPathReader.read(BountyServiceXPaths.PI_ID_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("data.pi.id node is not proper", receivedIdNodeValue, id);

		}

		// return order
		// Add a RO for INWARD_REJECTS
		// Add a RO for CUSTOMER_RETURNS
		// Add a RO for OUTWARD_REJECTS
		// Add a RO for STOCK_CORRECTION
		// Add a item for INWARD_REJECTS
		// Add a item for CUSTOMER_RETURNS
		// Add a item for OUTWARD_REJECTS
		// Add a item for STOCK_CORRECTION
		// Sent for Approval
		// Approve the RO
		//

		// 2.accepted return to not_found



		public List<Integer> getcore_inv_count_details(String sku_id, String warehouse_id)
				throws InterruptedException, UnsupportedEncodingException, JAXBException {
			List<Integer> invlist = new ArrayList<Integer>();
			int b_mi_c = 0;
			int b_p_c = 0;
			int b_ma_c = 0;
			int inv_count;

			Thread.sleep(1000);
			Map hm = new HashMap();
			hm = exSelectQueryForSingleRecord("select * from core_inventory_counts where sku_id = " + sku_id
					+ " and warehouse_id=" + warehouse_id + " and quality ='Q1' owner_partner_id=2297", "myntra_ims");
			b_p_c = (Integer) hm.get("blocked_processing_count");
			b_mi_c = (Integer) hm.get("blocked_missed_count");
			b_ma_c = (Integer) hm.get("blocked_manually_count");
			inv_count = (Integer) hm.get("inv_count");
			invlist.add(b_p_c);
			invlist.add(b_mi_c);
			invlist.add(b_ma_c);
			invlist.add(inv_count);
			return invlist;
		}

		// Create and approve a PI/PO till item generation
		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "bulkuploadSKUPIQty302", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
		public void GenerateItemSplitPO(String vendorid, String categoryManagerid, String esitimatedshipmentdate,
				String qty, String sku_id1, String sku_code1, String vano1, String size1, String vaname1, String sku_id2,
				String sku_code2, String vano2, String size2, String vaname2, String wh_ids, String status)
				throws Exception {

			String Query1 = "update sku_meta_data set brand_name='Belle Fille',article_name='dresses',sku_id=983980,color='pink',gender='women',size='L' where id=1040650;";
			String Query2 = "update inventory_term_info set inventory_term=10 where sku_meta_data_id= 1040650";
			String Query3 = "update inventory_term_info set inventory_term=-1 where sku_meta_data_id= 1040650 and state_id =2";
			exUpdateQuery(Query1, "myntra_rds");
			exUpdateQuery(Query2, "myntra_rds");
			exUpdateQuery(Query3, "myntra_rds");

			String po_id= String.valueOf(PurchaseOrderUtil.CreateOIPOInApproveStatus(true,2,3,buyerId_3974));
			Thread.sleep(50000);
			HashMap hm2 = new LinkedHashMap();
			String query2 = "select count(*) from item where po_id =" + po_id;
			hm2 = (HashMap) exSelectQueryForSingleRecord(query2, "myntra_wms");
			String item_count = hm2.get("count(*)").toString();
			Assert.assertEquals(item_count,"6", "po item_count is :");
			validateItemInfoDetails(po_id,String.valueOf(buyerId_3974),"NOT_RECEIVED",6);



		}

		public List<Integer> imsandatpdetails(String sku_id, String store_id, String warehouse_id) {
			List<Integer> imsandatpdetailslist = new ArrayList<Integer>();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int ims_inventory_count = 0;
			int ims_inv_stor_1_count;
			int ims_boc = 0;
			int atp_inv_count = 0;
			String query1 = "Select * from wh_inventory where sku_code =" + "'" + sku_id + "'" + " and warehouse_id="
					+ warehouse_id + " and store_id=" + store_id + ";";
			log.info(query1);
			String query2 = "Select * from wh_inventory where sku_code =" + "'" + sku_id + "'" + " and warehouse_id="
					+ warehouse_id + " and store_id=1" + ";";
			log.info(query2);
			String query3 = "Select * from inventory where sku_code =" + "'" + sku_id + "'" + ";";
			log.info(query3);
			try {
				Map rset1, rset2, rset3 = new HashMap();
				rset1 = exSelectQueryForSingleRecord(query1, "myntra_ims");
				ims_inventory_count = (Integer) rset1.get("inventory_count");
				log.info("ims inv count is for  store is : " + ims_inventory_count);
				imsandatpdetailslist.add(ims_inventory_count);// 0th element in list
				ims_boc = (Integer) rset1.get("blocked_order_count");
				log.info("ims boc count is  for  store is : " + ims_boc);

				imsandatpdetailslist.add(ims_boc);// 1st element boc fk store
				rset3 = exSelectQueryForSingleRecord(query3, "myntra_atp");
				// rset3.next();
				atp_inv_count = (Integer) rset3.get("inventory_count");
				log.info("ims inv in store 1" + atp_inv_count);

				imsandatpdetailslist.add(atp_inv_count);// 2nd element atp inv

				rset2 = exSelectQueryForSingleRecord(query2, "myntra_ims");

				ims_inv_stor_1_count = (Integer) rset2.get("inventory_count");
				log.info("inv count ims store_id1" + ims_inv_stor_1_count);

				imsandatpdetailslist.add(ims_inv_stor_1_count);
				// log.info(imsandatpdetailslist);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return imsandatpdetailslist;
		}

		// add a lot
		public static List<String> addaLot(String po_id, String cartonCount, String pieceCount, String status, String statusCode,
										   String statusMessage) throws JsonParseException, JsonMappingException, IOException, JAXBException {
			RequestGenerator getOrderReqGen = WMSHelper.addALot(po_id, cartonCount, pieceCount);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService addLOT API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService addLOT API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("not working properly", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);
			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);
			LotResponse lotResponse = (LotResponse) APIUtilities.convertXMLStringToObject(getOrderResponse,
					new LotResponse());
			log.info(lotResponse.getStatus().getStatusMessage());
			LotEntry lotEntry = lotResponse.getData().get(0);
			String lotid = lotEntry.getId().toString();
			String lotbarcode = lotEntry.getBarcode();
			ArrayList<String> lotlist = new ArrayList<String>();
			lotlist.add(lotid);
			lotlist.add(lotbarcode);
			log.info("the lot id is : " + lotid);
			return lotlist;

		}

		// update alot
		static void upadtelot(String lot_id, String upadtelotstatus, String status, String statusCode,
							  String statusMessage, String statustype)
				throws JsonParseException, JsonMappingException, IOException, JAXBException {
			SoftAssert sft = new SoftAssert();

			RequestGenerator getOrderReqGen = WMSHelper.updateALot(lot_id, upadtelotstatus);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService updateLOT API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService updateLOT API response :\n\n" + getOrderResponse + "\n");

			sft.assertEquals( Integer.parseInt(status), getOrderReqGen.response.getStatus(),"not working properly");

			XPathReader xPathReader = new XPathReader(getOrderResponse);
			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			sft.assertEquals( receivedStatusCode, statusCode,"statusCode is not proper");

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			sft.assertEquals( receivedStatusMessage, statusMessage,"statusMessage is not proper");
			sft.assertAll();
		}

		// addalottest

		// add a invoice
		 @Test(groups = { "Regression" }, dataProvider =
		 "addInvoice",dataProviderClass =WMSPipelineDP.class,alwaysRun =true)
		public static String addInvoice(String lotid, String status, String statusCode, String statusMessage,
				String statustype) throws JsonParseException, JsonMappingException, IOException, JAXBException {

			RequestGenerator getOrderReqGen = WMSHelper.addInvoice(lotid);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService addinvoice API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService addinvoice API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("not working properly", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);
			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);
			InvoiceResponse invoiceResponse = (InvoiceResponse) APIUtilities.convertXMLStringToObject(getOrderResponse,
					new InvoiceResponse());
			log.info(invoiceResponse.getStatus().getStatusMessage());
			InvoiceEntry invoiceEntry = invoiceResponse.getData().get(0);
			String invoiceid = invoiceEntry.getId().toString();
			log.info("invoice id is:" + invoiceid);
			return invoiceid;
		}

		// add invoice skus
		static void addInvoiceSku(String quantity, String invoiceid, String sku_id, String status, String statusCode,
								  String statusMessage) {
			SoftAssert sft=new SoftAssert();
			RequestGenerator getOrderReqGen = WMSHelper.addSkuToInvoice(quantity, invoiceid, sku_id);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService addinvoicesku API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService addinvoicesku API response :\n\n" + getOrderResponse + "\n");

			sft.assertEquals( Integer.parseInt(status), getOrderReqGen.response.getStatus(),"not working properly");

			XPathReader xPathReader = new XPathReader(getOrderResponse);
			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			sft.assertEquals( receivedStatusCode, statusCode,"statusCode is not proper");

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			sft.assertEquals( receivedStatusMessage, statusMessage,"statusMessage is not proper");
			sft.assertAll();

		}

		// update invoice
		static void updateInvoice(String lotid, String lotbarcode, String invoiceid, String invoiceStatus,
								  String status, String statusCode, String statusMessage) {
			SoftAssert sft=new SoftAssert();
			RequestGenerator getOrderReqGen = WMSHelper.updateInvoice(lotid, lotbarcode, invoiceid, invoiceStatus);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService updateinvoice API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService updateinvoice API response :\n\n" + getOrderResponse + "\n");

			sft.assertEquals( Integer.parseInt(status), getOrderReqGen.response.getStatus(),"not working properly");

			XPathReader xPathReader = new XPathReader(getOrderResponse);
			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			sft.assertEquals( receivedStatusCode, statusCode,"statusCode is not proper");

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			sft.assertEquals( receivedStatusMessage, statusMessage,"statusMessage is not proper");
			sft.assertAll();

		}

		// markitemQCpass
		public static void markitemqcpass(String lotbarcode, String cartoncode, String itemcode, String status,
				String statusCode, String statusMessage) {
			RequestGenerator getOrderReqGen = WMSHelper.markItemQAPass(lotbarcode, cartoncode, itemcode);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService markitemqcpass API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService markitemqcpass API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("not working properly", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);
			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

		}

		// markitemQCfail
		public static void markitemqcfail(String lotbarcode, String cartoncode, String itemcode, String status, String statusCode,
										  String statusMessage) {
			RequestGenerator getOrderReqGen = WMSHelper.markItemQAfail(lotbarcode, cartoncode, itemcode);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService markitemqcfail API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPrinting WMSService markitemqcfail API response :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("not working properly", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);
			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

		}

		public static void CheckinItem(String cretedby, String binbarcode, String binconfirmationcode, String itembarcode,
				String status, String statusCode, String statusMessage) {
			RequestGenerator getOrderReqGen = WMSHelper.ItemCheckin(cretedby, binbarcode, binconfirmationcode, itembarcode);

			String getOrderResponse = getOrderReqGen.respvalidate.returnresponseasstring();

			log.info("\nPrinting WMSService markitemqcfail API response :\n\n" + getOrderResponse + "\n");
			log.info("\nPutaway Failed :\n\n" + getOrderResponse + "\n");

			AssertJUnit.assertEquals("not working properly", Integer.parseInt(status), getOrderReqGen.response.getStatus());

			XPathReader xPathReader = new XPathReader(getOrderResponse);
			String receivedStatusCode = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_CODE_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusCode is not proper", receivedStatusCode, statusCode);

			String receivedStatusMessage = String
					.valueOf(xPathReader.read(BountyServiceXPaths.STATUS_MSG_NODE_VALUE.getXmlPath()));
			AssertJUnit.assertEquals("statusMessage is not proper", receivedStatusMessage, statusMessage);

		}

		// 39556
		@Test(groups = {
				"Regression" }, dataProvider = "addInvoice", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
		public void addInvoiceTest(String po_id, String cartonCount, String pieceCount, String lotstatus,
				String lotstatusCode, String lotstatusMessage, String lotstatustype, String status, String statusCode,
				String statusMessage, String statustype)
				throws JsonParseException, JsonMappingException, IOException, JAXBException {
			String lotid = addaLot(po_id, cartonCount, pieceCount, lotstatus, lotstatusCode, lotstatusMessage).get(0)
					.toString();
			log.info("lot id is:" + lotid);
			String lotbarcode = (String)
					exSelectQueryForSingleRecord("select barcode from core_lots where id = " + lotid, "myntra_wms")
					.get("barcode");
			addInvoice(lotid, status, statusCode, statusMessage, statustype);
			/*
			 * updateInvoice(lotid, lotbarcode, addInvoice(lotid, status,
			 * statusCode, statusMessage, statustype), "VERIFIED", "200", "7036",
			 * "Invoice updated"); upadtelot(lotid, "STICKER_READY", "200", "716",
			 * "Lot updated successfully", "SUCCESS");
			 */

		}

		@Test(groups = {
				"Regression" }, dataProvider = "updateInvoiceVerified", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
		public void updateInvoiceTest(String po_id, String cartonCount, String pieceCount, String lotstatus,
				String lotstatusCode, String lotstatusMessage, String lotstatustype, String invoicestatus,
				String invoicestatusCode, String invoicestatusMessage, String invoicestatustype, String invoiceStatusready,
				String invoiceStatusverified, String status, String statusCode, String statusMessage)
				throws JsonParseException, JsonMappingException, IOException, JAXBException {
			sft=new SoftAssert();
			// 39559
			String lotid = addaLot(po_id, cartonCount, pieceCount, lotstatus, lotstatusCode, lotstatusMessage).get(0);
			String lotbarcode = addaLot(po_id, cartonCount, pieceCount, lotstatus, lotstatusCode, lotstatusMessage).get(1);
			log.info("lot id is:" + lotid);
			String invoiceid = addInvoice(lotid, invoicestatus, invoicestatusCode, invoicestatusMessage, invoicestatustype);
			updateInvoice(lotid, lotbarcode, invoiceid, invoiceStatusready, status, statusCode, statusMessage);
			updateInvoice(lotid, lotbarcode, invoiceid, invoiceStatusverified, status, statusCode, statusMessage);
			sft.assertAll();

			/*
			 * updateInvoice(lotid, lotbarcode, addInvoice(lotid, status,
			 * statusCode, statusMessage, statustype), "VERIFIED", "200", "7036",
			 * "Invoice updated"); upadtelot(lotid, "STICKER_READY", "200", "716",
			 * "Lot updated successfully", "SUCCESS");
			 */
		}

		@Test(groups = {
				"Regression" }, dataProvider = "updateInvoiceInvalidtransitions", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
		public void updateInvoiceInvalidtransitions(String lotid, String lotbarcode, String invoiceid, String invoicestatus,
				String status, String statuscode, String statusMessage) {
			sft=new SoftAssert();
			String query = "delete from core_invoices where id in (38904,38233)";
			exUpdateQuery(query, "wms");
			String query1 = "insert into core_invoices (id,barcode,created_by,invoice_status,lot_id) values(38904,'IBW00000134','santwana','READY',42463),(38233,'IBW00000135','santwana','OPEN',42463) ";
			exUpdateQuery(query1, "wms");
			updateInvoice(lotid, lotbarcode, invoiceid, invoicestatus, status, statuscode, statusMessage);
			sft.assertAll();
		}

		@Test(groups = {
				"Regression" }, dataProvider = "markLotStickerReady", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
		public void updateLotToStickerReady(String po_id, String cartonCount, String pieceCount, String lotstatus,
				String lotstatusCode, String lotstatusMessage, String lotstatustype, String invoicestatus,
				String invoicestatusCode, String invoicestatusMessage, String invoicestatustype, String invoiceStatusready,
				String invoiceStatusverified, String status, String statusCode, String statusMessage)
				throws JsonParseException, JsonMappingException, IOException, JAXBException, InterruptedException {
			sft=new SoftAssert();
			String query2 = "delete from core_invoices where invoice_number='auto1'";
			exUpdateQuery(query2, "wms");
			List<String> lotlist = new ArrayList<String>();
			lotlist = addaLot(po_id, cartonCount, pieceCount, lotstatus, lotstatusCode, lotstatusMessage);
			String lotid = lotlist.get(0).toString();
			String lotbarcode = lotlist.get(1).toString();
			log.info("lot id is:" + lotid);

			String query1 = "select id from core_invoices where lot_id=" + lotid;
			Map<String, Object> invoicemap = new HashMap<String, Object>();
			invoicemap = exSelectQueryForSingleRecord(query1, "wms");
			Thread.sleep(1000);
			String invoiceid = invoicemap.get("id").toString();
			addInvoiceSku("1", invoiceid, skuDefault, "200", "7037", "Invoice Sku Added Successfully");
			updateInvoice(lotid, lotbarcode, invoiceid, invoiceStatusready, status, statusCode, statusMessage);
			updateInvoice(lotid, lotbarcode, invoiceid, invoiceStatusverified, status, statusCode, statusMessage);
			upadtelot(lotid, "STICKER_READY", "200", "716", "Lot updated successfully", "SUCCESS");
			sft.assertAll();
		}

		@Test(groups = { "Smoke", "Regression" }, description="Validate GRN creation for a single sku and single quantity" ,alwaysRun = true)
		public void GenerateGRNSingleSku()
				throws Exception {
			SoftAssert sft=new SoftAssert();
			String po_id= String.valueOf(purchaseOrderUtil.CreateOIPOInApproveStatus(false,2));
			Thread.sleep(50000);

			HashMap hm2, hm3, hm4 = null;
			String query2 = "select count(*) from item where po_id =" + po_id;
			hm2 = (HashMap) exSelectQueryForSingleRecord(query2, "myntra_wms");
			String item_count = hm2.get("count(*)").toString();
			Assert.assertEquals(item_count,"2", "po item_count is :");
			List<String> lotlist = null;
			lotlist = addaLot(po_id, "1", "2", "200", "713", "Lot Created");
			String lotid = lotlist.get(0).toString();
			String lotbarcode = lotlist.get(1).toString();
			// addinvoice
			String invoice_id = addInvoice(lotid, "200", "7034", "Invoice Created Successfully", "SUCESS");

			// addInvoiceSku("1",invoice_id,"983980","200","");
			addInvoiceSku("1", invoice_id, skuDefault, "200", "7037", "Invoice Sku Added Successfully");
			updateInvoice(lotid, lotbarcode, invoice_id, "READY", "200", "7036", "Invoice updated");
			updateInvoice(lotid, lotbarcode, invoice_id, "VERIFIED", "200", "7036", "Invoice updated");
			upadtelot(lotid, "STICKER_READY", "200", "716", "Lot updated successfully", "SUCCESS");
			String query_getItemID = "select id from item where po_id=" + po_id;
			List itemlist = null;
			itemlist = exSelectQuery(query_getItemID, "wms");
			log.info("trying to print the arraylist" + itemlist);
			for (int i = 0; i < itemlist.size(); i++) {
				Map itemidMap = (Map) (itemlist.get(i));
				markitemqcpass(lotbarcode, "C001237890", itemidMap.get("id").toString(), "200", "6550",
						"Items inwarded successfully");
				}
			upadtelot(lotid, "CLOSED", "200", "716", "Lot updated successfully", "SUCCESS");
			Thread.sleep(1000);
			String checkgrn = "select * from core_grns where lot_id=" + lotid;
			hm3 = (HashMap) exSelectQueryForSingleRecord(checkgrn, "wms");
			long grn_id = Long.parseLong(hm3.get("id").toString());
			String checkgrnskus = "select count(*) from core_grn_skus where grn_id=" + grn_id;
			hm4 = (HashMap) exSelectQueryForSingleRecord(checkgrnskus, "wms");
			long count = Long.parseLong(hm4.get("count(*)").toString());

			sft.assertEquals(count, 1, "grnsku count is:");
			sft.assertAll();

		}

	@Test(groups = {"Regression" }, description="Validate GRN creation for a multi sku and mulit quantity. Also upload multiple invoice for the same lot",alwaysRun = true)
	public void GenerateGRNMultiSku()
			throws Exception {
		SoftAssert sft=new SoftAssert();
		String po_id= String.valueOf(purchaseOrderUtil.CreateOIPOInApproveStatus(true,5,3,buyerId_3974));
		Thread.sleep(50000);

		HashMap hm2, hm3, hm4 = null;
		String query2 = "select count(*) from item where po_id =" + po_id;
		hm2 = (HashMap) exSelectQueryForSingleRecord(query2, "myntra_wms");
		String item_count = hm2.get("count(*)").toString();
		Assert.assertEquals(item_count,"15", "po item_count is :");
		List<String> lotlist = null;
		lotlist = addaLot(po_id, "1", "15", "200", "713", "Lot Created");
		String lotid = lotlist.get(0).toString();
		String lotbarcode = lotlist.get(1).toString();
		// addinvoice
		String invoice_id = addInvoice(lotid, "200", "7034", "Invoice Created Successfully", "SUCESS");

		// addInvoiceSku("1",invoice_id,"983980","200","");
		addInvoiceSku("5", invoice_id, skuIds[0], "200", "7037", "Invoice Sku Added Successfully");
		addInvoiceSku("5", invoice_id, skuIds[1], "200", "7037", "Invoice Sku Added Successfully");
		addInvoiceSku("5", invoice_id, skuIds[2], "200", "7037", "Invoice Sku Added Successfully");
		updateInvoice(lotid, lotbarcode, invoice_id, "READY", "200", "7036", "Invoice updated");
		updateInvoice(lotid, lotbarcode, invoice_id, "VERIFIED", "200", "7036", "Invoice updated");
		upadtelot(lotid, "STICKER_READY", "200", "716", "Lot updated successfully", "SUCCESS");
		String query_getItemID = "select id from item where po_id=" + po_id;
		List itemlist = null;
		itemlist = exSelectQuery(query_getItemID, "wms");
		log.info("trying to print the arraylist" + itemlist);
		for (int i = 0; i < itemlist.size(); i++) {
			Map itemidMap = (Map) (itemlist.get(i));
			markitemqcpass(lotbarcode, "C001237890", itemidMap.get("id").toString(), "200", "6550",
					"Items inwarded successfully");
		}
		upadtelot(lotid, "CLOSED", "200", "716", "Lot updated successfully", "SUCCESS");
		Thread.sleep(1000);

		String checkgrnskus = "select count(*) from core_grn_skus where grn_id=(select id from core_grns where lot_id="+ lotid+")";
		String count = exSelectQueryForSingleRecord(checkgrnskus, "wms").get("count(*)").toString();
		sft.assertEquals(count, "3", "grnsku count is:");

		sft.assertAll();

	}


	@Test(groups = { "Smoke", "Regression" }, description="Upload multiple lots and invoices against a PO for inwarding",alwaysRun = true)
	public void MultiLotMultiInvoice()
			throws Exception {
		SoftAssert sft=new SoftAssert();
		String po_id= String.valueOf(purchaseOrderUtil.CreateOIPOInApproveStatus(true,10,4,buyerId_3974));
		//String po_id="54140";
		Thread.sleep(50000);

		validateItemInfoDetails(po_id,String.valueOf(buyerId_3974),"NOT_RECEIVED",40);
		List<String> lotlist = null;
		List<String> lotlist_1 = null;
		lotlist = addaLot(po_id, "1", "10", "200", "713", "Lot Created");
		lotlist_1 = addaLot(po_id, "1", "10", "200", "713", "Lot Created");
		String lotid = lotlist.get(0).toString();
		String lotbarcode = lotlist.get(1).toString();

		String lotid_1 = lotlist_1.get(0).toString();
		String lotbarcode_1 = lotlist_1.get(1).toString();

		String[] lotData={lotid,lotid_1};
		Thread.sleep(10000);
		validateLotAndInvoiceCreation(lotData);

		// addinvoice
		String invoice_id = addInvoice(lotid, "200", "7034", "Invoice Created Successfully", "SUCESS");
		String invoice_id_1 = addInvoice(lotid_1, "200", "7034", "Invoice Created Successfully", "SUCESS");


		addInvoiceSku("2", invoice_id, skuIds[0], "200", "7037", "Invoice Sku Added Successfully");
		addInvoiceSku("3", invoice_id, skuIds[1], "200", "7037", "Invoice Sku Added Successfully");
		addInvoiceSku("5", invoice_id, skuIds[2], "200", "7037", "Invoice Sku Added Successfully");

		addInvoiceSku("2", invoice_id_1, skuIds[0], "200", "7037", "Invoice Sku Added Successfully");
		addInvoiceSku("3", invoice_id_1, skuIds[1], "200", "7037", "Invoice Sku Added Successfully");
		addInvoiceSku("5", invoice_id_1, skuIds[2], "200", "7037", "Invoice Sku Added Successfully");

		updateInvoice(lotid, lotbarcode, invoice_id, "READY", "200", "7036", "Invoice updated");
		updateInvoice(lotid, lotbarcode, invoice_id, "VERIFIED", "200", "7036", "Invoice updated");

		updateInvoice(lotid_1, lotbarcode_1, invoice_id_1, "READY", "200", "7036", "Invoice updated");
		updateInvoice(lotid_1, lotbarcode_1, invoice_id_1, "VERIFIED", "200", "7036", "Invoice updated");

		upadtelot(lotid, "STICKER_READY", "200", "716", "Lot updated successfully", "SUCCESS");
		upadtelot(lotid_1, "STICKER_READY", "200", "716", "Lot updated successfully", "SUCCESS");

		String query_getItemID = "select id from item where po_id=" + po_id;
		List itemlist = null;

		itemlist = exSelectQuery(query_getItemID, "wms");
		log.info("trying to print the arraylist" + itemlist);

		for (int i = 0; i < 40; i++) {
			Map itemidMap = (Map) (itemlist.get(i));
			if(i<10){
			markitemqcpass(lotbarcode, "C001237890", itemidMap.get("id").toString(), "200", "6550",
					"Items inwarded successfully");
			}
			else
			markitemqcfail(lotbarcode_1, "C001237891", itemidMap.get("id").toString(), "200", "6550",
					"Items inwarded successfully");
		}
		upadtelot(lotid, "CLOSED", "200", "716", "Lot updated successfully", "SUCCESS");
		upadtelot(lotid_1, "CLOSED", "200", "716", "Lot updated successfully", "SUCCESS");

		Thread.sleep(1000);

		String checkgrn = "select count(*) from core_grns where lot_id=" + lotid;
		String hm3 = exSelectQueryForSingleRecord(checkgrn, "wms").get("count(*)").toString();
		sft.assertEquals(hm3, "1", "grn count is:");

		String checkgrn_1 = "select count(*) from core_grns where lot_id=" + lotid_1;
		String hm4 = exSelectQueryForSingleRecord(checkgrn, "wms").get("count(*)").toString();
		sft.assertEquals(hm3, "1", "grn count is:");
		sft.assertAll();
		HashMap<String,Integer> qualityData=new HashMap<>();
		qualityData.put("Q1",10);
		qualityData.put("Q2",30);
		Thread.sleep(9000);
		validateItemsPostInward(qualityData,po_id);

	}


	@Test(groups = { "Smoke", "Regression" }, description="Upload single lots and single invoice against a PO for inwarding multi skus",alwaysRun = true)
	public void SingleLotSingleInvoiceMultiSku()
			throws Exception {
		SoftAssert sft=new SoftAssert();
		String po_id= String.valueOf(purchaseOrderUtil.CreateOIPOInApproveStatus(true,2,10,buyerId_3974));

		Thread.sleep(50000);

		HashMap hm3, hm4 = null;

		validateItemInfoDetails(po_id,String.valueOf(buyerId_3974),"NOT_RECEIVED",20);

		List<String> lotlist;
		lotlist = addaLot(po_id, "1", "20", "200", "713", "Lot Created");
		String lotid = lotlist.get(0).toString();
		String lotbarcode = lotlist.get(1).toString();

		Thread.sleep(10000);

		validateLotAndInvoiceCreation(lotid);
		// addinvoice
		String invoice_id = addInvoice(lotid, "200", "7034", "Invoice Created Successfully", "SUCESS");


		String dbQuery="select sku_id,count(*) from item where po_id=" +po_id+ " GROUP BY sku_id";
		List<HashMap<String,Object>> skuData= exSelectQuery(dbQuery,"myntra_wms");
		for(HashMap<String,Object> sku:skuData)
			addInvoiceSku(sku.get("count(*)").toString(), invoice_id, sku.get("sku_id").toString(), "200", "7037", "Invoice Sku Added Successfully");


		updateInvoice(lotid, lotbarcode, invoice_id, "READY", "200", "7036", "Invoice updated");
		updateInvoice(lotid, lotbarcode, invoice_id, "VERIFIED", "200", "7036", "Invoice updated");

		upadtelot(lotid, "STICKER_READY", "200", "716", "Lot updated successfully", "SUCCESS");

		String query_getItemID = "select id from item where po_id=" + po_id;
		List itemlist = null;

		itemlist = exSelectQuery(query_getItemID, "wms");
		log.info("trying to print the arraylist" + itemlist);

		for (int i = 0; i < 20; i++) {
			Map itemidMap = (Map) (itemlist.get(i));
			if(i<=10){
				markitemqcpass(lotbarcode, "C001237890", itemidMap.get("id").toString(), "200", "6550",
						"Items inwarded successfully");
			}
			else
				markitemqcfail(lotbarcode, "C001237891", itemidMap.get("id").toString(), "200", "6550",
						"Items inwarded successfully");
		}
		upadtelot(lotid, "CLOSED", "200", "716", "Lot updated successfully", "SUCCESS");

		Thread.sleep(1000);

		String checkgrn = "select * from core_grns where lot_id=" + lotid;
		hm3 = (HashMap) exSelectQueryForSingleRecord(checkgrn, "wms");
		long grn_id = Long.parseLong(hm3.get("id").toString());
		String checkgrnskus = "select count(*) from core_grn_skus where grn_id=" + grn_id;
		hm4 = (HashMap) exSelectQueryForSingleRecord(checkgrnskus, "wms");
		long count = Long.parseLong(hm4.get("count(*)").toString());
		sft.assertEquals(count, 10, "grnsku count is:");
		sft.assertAll();

	}

	private void validateItemsPostInward(HashMap<String,Integer> qualityData,String po_id) {
		sft = new SoftAssert();
		String dbQuery = "select quality,count(*) from item where po_id="+po_id+" GROUP BY quality";
		List<Map<String, Object>>  qualityCount= exSelectQuery(dbQuery,"myntra_wms");
		sft.assertEquals(qualityCount.get(0).get("count(*)").toString(),qualityData.get("Q1").toString()," Q1 count for PO" + po_id);
		sft.assertEquals(qualityCount.get(1).get("count(*)").toString(),qualityData.get("Q2").toString()," Q2 count for PO" + po_id);
		sft.assertAll();


	}

	private void validateLotAndInvoiceCreation(String ...lotIds) {
			SoftAssert sft = new SoftAssert();
			for(String lotId:lotIds) {
				String dbQuery = "select count(*),lot_status from core_lots where id=" + lotId;
				List<Map<String, Object>>  lotData= exSelectQuery(dbQuery,"myntra_wms");
				sft.assertEquals(lotData.get(0).get("count(*)").toString(),"1","Single entry for the lot should be created");
				sft.assertEquals(lotData.get(0).get("lot_status").toString(),"OPEN","LOT status should be OPEN");

				dbQuery = "select invoice_status from core_invoices where lot_id=" + lotId;
				List<Map<String, Object>> invoiceData= exSelectQuery(dbQuery,"myntra_wms");
				sft.assertEquals(invoiceData.size(),1,"Default invoice created for the lot " + lotId);
				sft.assertEquals(invoiceData.get(0).get("invoice_status").toString(),"OPEN","Invoice status should be OPEN");
			}
			sft.assertAll();
	}

	private void validateItemInfoDetails(String po_id,String buyerId,String item_status,int po_size) {
		SoftAssert sft = new SoftAssert();

		String query2 = "select item_status,sku_id from item where po_id =" + po_id;
		List<Map<String, Object>> itemData = exSelectQuery(query2, "myntra_wms");
		sft.assertEquals(itemData.size(),po_size,"The po count should match with item count in item table");
		for(Map<String, Object> hm:itemData) {
			sft.assertEquals(hm.get("item_status").toString(),item_status,"Expected status of item "+item_status);
		}
		query2 = "select count(buyer_id) from item_info where item_id in (select barcode from item where po_id =" + po_id+ ") and buyer_id="+buyerId;
		String buyerCount= exSelectQueryForSingleRecord(query2,"myntra_wms").get("count(buyer_id)").toString();
		sft.assertEquals(buyerCount,String.valueOf(po_size));
		sft.assertAll();
	}

	@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "Reconcileiteminsamebin", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
		public static void Reconcileiteminsamebin(String binbarcode, String[] itembarcode, String status, int statuscode,
				String status_message, String statustype, int totalcount)
				throws UnsupportedEncodingException, JAXBException {
			String query = "update item set item_status='PROCESSING', bin_id=582792 where id='1000162';";
			String query2 = "update item set item_status='ISSUED_FOR_OPS',bin_id=582792 where id='1000165';";
			exUpdateQuery(query, "wms");
			exUpdateQuery(query2, "wms");

			ReconciliationResponse reconciliationResponse;
			reconciliationResponse = WMSHelper.ReconcileItem(binbarcode, itembarcode);
			Assert.assertEquals(reconciliationResponse.getStatus().getStatusCode(), statuscode, "Status code is:");
			Assert.assertEquals(reconciliationResponse.getStatus().getStatusMessage(), status_message,
					"Status Message is:");
			Assert.assertEquals(reconciliationResponse.getStatus().getStatusType().toString(), statustype,
					"Status type is:");
			Assert.assertEquals(reconciliationResponse.getStatus().getTotalCount(), totalcount, "Total count is:");
			String query1 = "select * from item where id in (1000162,1000165)";
			List<Map> list;
			list = exSelectQuery(query1, "wms");
			String item1_status = list.get(0).get("item_status").toString();
			String item1_bin_id = list.get(0).get("bin_id").toString();
			String item2_status = list.get(1).get("item_status").toString();
			String item2_bin_id = list.get(0).get("bin_id").toString();

			Assert.assertEquals(item1_status, "PROCESSING", "Item status is :");
			Assert.assertEquals(item2_status, "ISSUED_FOR_OPS", "Item status is :");

			Assert.assertEquals(item1_bin_id, "582792", "Binid is :");
			Assert.assertEquals(item2_bin_id, "582792", "Binid is :");

		}

		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "Reconcileitemindifferentbin", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
		public static void Reconcileitemindifferentbin(String binbarcode, String[] itembarcode, String status,
				int statuscode, String status_message, String statustype, int totalcount)
				throws UnsupportedEncodingException, JAXBException {
			String query = "update item set item_status='PROCESSING',bin_id=582810 where id=1000160";
			String query2 = "update item set item_status='PROCESSING',bin_id=582792 where id=1000162";
			String query3 = "update item set item_status='ISSUED_FOR_OPS',bin_id=582810 where id=1000163";
			String query4 = "update item set item_status='ISSUED_FOR_OPS',bin_id=582792 where id=1000165";
			exUpdateQuery(query, "wms");
			exUpdateQuery(query2, "wms");
			exUpdateQuery(query3, "wms");
			exUpdateQuery(query4, "wms");
			ReconciliationResponse reconciliationResponse = new ReconciliationResponse();
			reconciliationResponse = WMSItemTransitionHelper1.ReconcileItem(binbarcode, itembarcode);
			Assert.assertEquals(reconciliationResponse.getStatus().getStatusCode(), statuscode, "Status code is:");
			Assert.assertEquals(reconciliationResponse.getStatus().getStatusMessage().toString(), status_message,
					"Status Message is:");
			Assert.assertEquals(reconciliationResponse.getStatus().getStatusType().toString(), statustype,
					"Status type is:");
			Assert.assertEquals(reconciliationResponse.getStatus().getTotalCount(), totalcount, "Total count is:");
			String query1 = "select * from item where id in (1000160,1000163,1000162,1000165)";
			List<Map> list = new LinkedList<Map>();
			list = exSelectQuery(query1, "wms");
			String item1_status = list.get(0).get("item_status").toString();
			String item1_bin_id = list.get(0).get("bin_id").toString();
			String item2_status = list.get(1).get("item_status").toString();
			String item2_bin_id = list.get(1).get("bin_id").toString();

			String item3_status = list.get(2).get("item_status").toString();
			String item3_bin_id = list.get(2).get("bin_id").toString();

			String item4_status = list.get(3).get("item_status").toString();
			String item4_bin_id = list.get(3).get("bin_id").toString();

			Assert.assertEquals(item1_status, "PROCESSING", "Item status is :");
			Assert.assertEquals(item2_status, "PROCESSING", "Item status is :");
			Assert.assertEquals(item3_status, "ISSUED_FOR_OPS", "Item status is :");
			Assert.assertEquals(item4_status, "ISSUED_FOR_OPS", "Item status is :");
			Assert.assertEquals(item1_bin_id, "582792", "Binid is :");
			Assert.assertEquals(item2_bin_id, "582792", "Binid is :");
			Assert.assertEquals(item3_bin_id, "582792", "Binid is :");
			Assert.assertEquals(item4_bin_id, "582792", "Binid is :");
		}

		@Test(groups = { "Smoke",
				"Regression" }, dataProvider = "ReconcileitemMarkNotfound", dataProviderClass = WMSPipelineDP.class, alwaysRun = true)
		public static void ReconcileitemMarkNotfound(String binbarcode, String[] itembarcode, String status, int statuscode,
				String status_message, String statustype, int totalcount)
				throws UnsupportedEncodingException, JAXBException {
			String query = "update item set item_status='PROCESSING',bin_id=582810 where id=1000160";
			String query2 = "update item set item_status='PROCESSING',bin_id=582792 where id=1000162";
			String query3 = "update item set item_status='ISSUED_FOR_OPS',bin_id=582810 where id=1000163";
			String query4 = "update item set item_status='ISSUED_FOR_OPS',bin_id=582792 where id=1000165";
			exUpdateQuery(query, "wms");
			exUpdateQuery(query2, "wms");
			exUpdateQuery(query3, "wms");
			exUpdateQuery(query4, "wms");
			ReconciliationResponse reconciliationResponse = new ReconciliationResponse();
			reconciliationResponse = WMSHelper.ReconcileItem(binbarcode, itembarcode);
			Assert.assertEquals(reconciliationResponse.getStatus().getStatusCode(), statuscode, "Status code is:");
			Assert.assertEquals(reconciliationResponse.getStatus().getStatusMessage(), status_message,
					"Status Message is:");
			Assert.assertEquals(reconciliationResponse.getStatus().getStatusType().toString(), statustype,
					"Status type is:");
			//Assert.assertEquals(reconciliationResponse.getStatus().getTotalCount(), totalcount, "Total count is:");
			String query1 = "select * from item where id in (1000160,1000162,1000163,1000165)";
			List<Map> list = new LinkedList<Map>();
			list = exSelectQuery(query1, "wms");

			String item1_status = list.get(0).get("item_status").toString();
			String item2_status = list.get(1).get("item_status").toString();
			String item1_bin_id = list.get(0).get("bin_id").toString();

			String item2_bin_id = list.get(1).get("bin_id").toString();

			String item3_status = list.get(2).get("item_status").toString();
			String item3_bin_id = list.get(2).get("bin_id").toString();

			String item4_status = list.get(3).get("item_status").toString();
			String item4_bin_id = list.get(3).get("bin_id").toString();

			Assert.assertEquals(item1_status, "NOT_FOUND", "Item status is :");
			Assert.assertEquals(item2_status, "PROCESSING", "Item status is :");
			Assert.assertEquals(item3_status, "NOT_FOUND", "Item status is :");
			Assert.assertEquals(item4_status, "ISSUED_FOR_OPS", "Item status is :");
			Assert.assertEquals(item1_bin_id, "582810", "Binid is :");
			Assert.assertEquals(item2_bin_id, "582810", "Binid is :");
			Assert.assertEquals(item3_bin_id, "582810", "Binid is :");
			Assert.assertEquals(item4_bin_id, "582810", "Binid is :");
		}
		
	}