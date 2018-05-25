package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.apiTests.erpservices.partners.EventsDiscountingServiceHelper;
import com.myntra.apiTests.erpservices.partners.dp.EventsDiscountingServiceDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class EventsDiscountingService extends BaseTest{
	
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(EventsDiscountingService.class);
	static String envName = "fox8";
	public static EventsDiscountingServiceHelper eventsDiscountingServiceHelper;

	@BeforeClass()
	public void testAfterClass() throws SQLException {
		init = new Initialize("Data/configuration");
		eventsDiscountingServiceHelper = new EventsDiscountingServiceHelper();

	}

	@Test(enabled = true, groups = { "sanity",
	"Regression" }, dataProviderClass = EventsDiscountingServiceDP.class, dataProvider = "getDiscountsByVendorIdAndEventId", description = "Gets list of discounts created for a vendor and the event")
	public void getDiscountsByVendorIdAndEventId(String vendorId, String eventId) throws Exception {
		eventsDiscountingServiceHelper.getDiscountsByVendorIdAndEventId( vendorId,  eventId);
		//Assert.assertEquals(brandResponse.getData().get(0).getBrandName(), "Adidas", "Get Brand Name by Brand Code : ");

	}
	
	@Test(enabled = true, groups = { "sanity",
	"Regression" }, dataProviderClass = EventsDiscountingServiceDP.class, dataProvider = "getEventsListByVendorId", description = "Gets list of discounts created for a vendor and the event")
	public void getEventsListByVendorId(String vendorId) throws Exception {
		eventsDiscountingServiceHelper.getEventsListByVendorId( vendorId);

	}
	
	@Test(enabled = true, groups = { "sanity",
	"Regression" }, dataProviderClass = EventsDiscountingServiceDP.class, dataProvider = "getAllStylesAvailableForDiscounts", description = "API to provide all the styleId, VAN available for discounting.")
	public void getAllStylesAvailableForDiscounts(String vendorId) throws Exception {
		eventsDiscountingServiceHelper.getAllStylesAvailableForDiscounts( vendorId);

	}
	
	@Test(enabled = true, groups = { "sanity",
	"Regression" }, dataProviderClass = EventsDiscountingServiceDP.class, dataProvider = "addDiscounts", description = "API to provide all the styleId, VAN available for discounting.")
	public void addDiscounts(String vendorId, String payload) throws Exception {
		eventsDiscountingServiceHelper.addDiscounts(vendorId, payload);

	}

}
