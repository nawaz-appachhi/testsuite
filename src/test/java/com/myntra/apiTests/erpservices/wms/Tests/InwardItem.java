/**
 * 
 */
package com.myntra.apiTests.erpservices.wms.Tests;

import com.myntra.apiTests.erpservices.utility.PurchaseOrderUtil;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.testbase.BaseTest;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author santwana.samantray
 *
 */
public class InwardItem extends BaseTest{

	public static List inwardItem(String sku_id, String quantity, String warehouse)
			throws Exception {
		String bin_id=null;
		String confirmation_code=null;
		SoftAssert sft=new SoftAssert();
		PurchaseOrderUtil purchaseOrderUtil = new PurchaseOrderUtil();
		String po_id=null;
		if(Integer.parseInt(quantity)>1){
			po_id= String.valueOf(purchaseOrderUtil.CreateOIPOInApproveStatus(true,2));
		}else
		{
			po_id= String.valueOf(purchaseOrderUtil.CreateOIPOInApproveStatus(false,2));
		}
		HashMap hm3,hm4,hm5 = new HashMap();
		String query4 = "select count(*) from item where po_id =" + po_id;
		hm3 = (HashMap) DBUtilities.exSelectQueryForSingleRecord(query4,
				"myntra_wms");
		String item_count = hm3.get("count(*)").toString();
		//Assert.assertEquals("2", item_count, "po item_count is :");
		List lotlist = new ArrayList();
		lotlist = WMSTest.addaLot(po_id, "1", quantity, "200", "713", "Lot Created");
		String lotid = lotlist.get(0).toString();
		String lotbarcode = lotlist.get(1).toString();
		String invoice_id = WMSTest.addInvoice(lotid, "200", "7034",
				"Invoice Created Successfully", "SUCESS");
		// addInvoiceSku("1",invoice_id,"983980","200","");
		WMSTest.addInvoiceSku("1", invoice_id, sku_id, "200", "7037",
				"Invoice Sku Added Successfully");
		WMSTest.updateInvoice(lotid, lotbarcode, invoice_id, "READY", "200",
				"7036", "Invoice updated");
		WMSTest.updateInvoice(lotid, lotbarcode, invoice_id, "VERIFIED", "200",
				"7036", "Invoice updated");
		WMSTest.upadtelot(lotid, "STICKER_READY", "200", "716",
				"Lot updated successfully", "SUCCESS");
		String query_getItemID = "select id from item where po_id=" + po_id;
		List itemlist = new ArrayList();
		itemlist = DBUtilities.exSelectQuery(query_getItemID, "wms");
		System.out.println("trying to print the arraylist" + itemlist);

		//add sizing qc
		for (int i = 0; i < itemlist.size(); i++) {
			Map itemidMap = (Map) (itemlist.get(i));
			WMSTest.markitemqcpass(lotbarcode, "C001237890", itemidMap
					.get("id").toString(), "200", "6550",
					"Items inwarded successfully");
			if(warehouse.equalsIgnoreCase("36"))
			{
				 bin_id="BWSTGC-02-A-01";
				 confirmation_code="LML5";
			}
			if(warehouse.equalsIgnoreCase("28"))
			{
				bin_id="GNSTGA-01-C-12";
				 confirmation_code="VQB9";
			}
			/*if(warehouse.equalsIgnoreCase("1"))
			{
				String bin_id="582762";
				String confirmation_code="LML5";
			}*/
			WMSTest.CheckinItem("WMSAutomation",bin_id,confirmation_code,itemidMap.get("id").toString(),"200","744","Location updated successfully");

			sft.assertAll();
		}

		
return itemlist;
	}
/*@Test
public void checkinwardItem() throws JsonParseException, JsonMappingException, IOException, JAXBException, InterruptedException
{
	List list = new ArrayList();
	list=inwardItem("890847","2","36");
	for (int i =0; i<list.size();i++)
	{
		System.out.println("item id is:"+list.get(i));
	}
	}*/


}