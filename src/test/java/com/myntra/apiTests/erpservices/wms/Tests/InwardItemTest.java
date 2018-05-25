/**
 * 
 */
package com.myntra.apiTests.erpservices.wms.Tests;

import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author santwana.samantray
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
public class InwardItemTest extends BaseTest {
	static Logger log = Logger.getLogger(InwardItemTest.class);
	@Test
	public void generateitemids()
			throws Exception {
		List itemlist = new ArrayList();

		String sku_id = System.getenv("sku_id");

		String wh_id = System.getenv("warehouse_id");
		String quantity = System.getenv("quantity");
		InwardItem inwardItem1 = new InwardItem();
		//itemlist = inwardItem1.inwardItem(sku_id,quantity,wh_id);
		itemlist = inwardItem1.inwardItem("12070042","1","36");
		//System.out.println(Arrays.asList(itemlist));
		log.info(itemlist);
	}

}
