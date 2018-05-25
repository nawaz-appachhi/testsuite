package com.myntra.apiTests.portalservices.wms;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.HashMap;
/**
 * @author Abhijit Pati
 *
 */
public class WMSInStock extends BaseTest{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(WMSInStock.class);


	@Test(enabled=true)
	public void executeQuery() throws SQLException {

		String skus[] = {"3827:5000001:1,19,36", "3828:6000001:28,36", "3829:7000001:1,36", "3830:8000001:1",
				"3831:90000001:1,36", "3832:91000001:28", "3833:7100001:19", "3834:6100001:28", "3835:5100001:1",
				"3836:8200001:1,19,28,36", "3837:8300001:36,28", "3838:8400001:28", "8005:8500001:1,36", "8006:8600001:36,28", "8007:8700001:1",
                "15496:8900001:19", "890848:9000001:28", "890847:9100001:28,36", "890849:9200001:36", "1243744:10200001:28,36", "3913:400001:36", "3914:500001:36",
                "3915:600001:36", "3916:700001:36", "3917:800001:36", "3918:900001:36,28,1", "3919:100001:36", "3920:110001:36","3870:120000:28","3869:130000:36","3868:140000:36",
                "3867:150000:36","3866:160000:36","3876:170000:36","3875:180000:36","3874:190000:36","3873:200000:36","3872:210000:36","3871:220000:28","3881:230000:36","3880:240000:36",
                "3879:250000:36","3878:260000:36","3877:270000:36","1074938:280000:20","1074940:290000:20","1087066:300000:20"};

		HashMap<String, String> binEntry = new HashMap<>();
		binEntry.put("1", "403");
		binEntry.put("19", "151924");
		binEntry.put("28", "271760");
		binEntry.put("36", "582762");

		for (String sku : skus) {
			String[] temp;
			String delimiter = ":";
			temp = sku.split(delimiter);
			String skuId = temp[0];
			int id = Integer.parseInt(temp[1]);
			String[] avilableWHs = temp[2].split(",");

			int noofsku = 500;
			// delete previous entries
			int idLimit = id + noofsku+1000;
			String queryDel = "Delete from item where sku_id='"+sku+"' or id between "+id+" and "+idLimit+";";
			System.out.println("Deletion Query : " + queryDel);
			DBUtilities.exUpdateQuery(queryDel, "wms");

			int id2 = id;
			for (int i = 0; i < noofsku - 1; i++) {
				for (String string : avilableWHs) {
					String query = "insert into item (id,barcode,sku_id,quality,item_status,warehouse_id,enabled,po_id,po_barcode,po_sku_id,lot_id,lot_barcode,comments,bin_id) values("
							+ id2 + ", '" + id2 + "'," + skuId + ",'Q1','STORED'," + string
							+ ",1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item', "+ binEntry.get(string)+");";
					System.out.println("Query - "+ query);
					DBUtilities.exUpdateQuery(query, "wms");
					id2++;
				}
				System.out.println("Query updated succesfully");
			}

		}
	}
}
