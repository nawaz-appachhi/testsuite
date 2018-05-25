package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.testbase.BaseTest;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class OMSEGiftCardTest extends BaseTest {


    static org.slf4j.Logger log = LoggerFactory.getLogger(OMSEGiftCardTest.class);
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    End2EndHelper end2EndHelper = new End2EndHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();

    String uidx = "8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R";
    static Initialize init = new Initialize("/Data/configuration");
    String password = "123456";
    String login = "end2endautomation1@gmail.com";

	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, UnsupportedEncodingException, JAXBException {
		DBUtilities.exUpdateQuery("UPDATE on_hold_reasons_master SET is_enabled=0 where id in (35,23,37);", "myntra_oms");
//		omsServiceHelper.refreshOMSApplicationPropertyCache();
//		omsServiceHelper.refreshOMSJVMCache();
//		End2EndHelper.sleep(60000L);
	}

	
	
	@Test(enabled = true, groups={"smoke","regression","exchangeService"}, description = "Exchange Creation Should Fail when inventory is not available")
    public void createEGiftCardOrder() throws Exception {
   
/*		String skuId[] = { "11024406:1" };
		String orderID = end2EndHelper.createOrderWithEGiftCard(login, password,
				"6119045", skuId,"EndToEnd","Good Luck!","sasasasas","abhijit.pati@myntra.com");
		log.info("OrderId : "+orderID);
		Assert.assertNotNull(orderID, "Order is not created");*/
    }


}
