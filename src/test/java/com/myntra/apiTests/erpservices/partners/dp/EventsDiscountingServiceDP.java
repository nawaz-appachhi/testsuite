package com.myntra.apiTests.erpservices.partners.dp;

import com.myntra.lordoftherings.Toolbox;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class EventsDiscountingServiceDP {
	
	@DataProvider(name = "getDiscountsByVendorIdAndEventId")
	public static Object[][] getDiscountsByVendorIdAndEventId(ITestContext testContext) throws Exception {
			String[] arr1 = { "593","5"};
			Object[][] dataSet = new Object[][] { arr1,  };
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	@DataProvider(name = "getEventsListByVendorId")
	public static Object[][] getEventsListByVendorId(ITestContext testContext) throws Exception {
			String[] arr1 = { "593"};
			Object[][] dataSet = new Object[][] { arr1,  };
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	@DataProvider(name = "getAllStylesAvailableForDiscounts")
	public static Object[][] getAllStylesAvailableForDiscounts(ITestContext testContext) throws Exception {
			String[] arr1 = { "162"};
			Object[][] dataSet = new Object[][] { arr1,  };
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	//
//	{
//	    "user": "Sahil Enterprises",
//	    "email": "abc@xyz.com",
//	    "vendorId": 593,
//	    "event": {
//	        "id": 5,
//	        "vfStyleDTOs": [
//	            {
//	                "styleId": 362023,
//	                "vendorFundedPercentage": 100
//	            },
//	            {
//	                "styleId": 362025,
//	                "vendorFundedPercentage": 12
//	            },
//	            {
//	                "styleId": 362024,
//	                "vendorFundedPercentage": 13
//	            }  
//	        ]
//	    }
//	}
	@DataProvider(name = "addDiscounts")
	public static Object[][] addDiscounts(ITestContext testContext) throws Exception {
//		com.myntra.pricingengine.domain.Event event=new Event();
//		EventVFStyle eventVFStyle=new EventVFStyle();
//		eventVFStyle.setStyleId(362023l);
//		eventVFStyle.setVendorFundedPercentage(10f);
//		
//		EventVFStyle eventVFStyle1=new EventVFStyle();
//		eventVFStyle1.setStyleId(362025l);
//		eventVFStyle1.setVendorFundedPercentage(30.5f);
//		
//		List<EventVFStyle> vfStyleDTOs=new ArrayList<EventVFStyle>();
//		vfStyleDTOs.add(eventVFStyle);
//		vfStyleDTOs.add(eventVFStyle1);	
//		event.setVfStyleDTOs(vfStyleDTOs);
//		
//		String payload = APIUtilities.getObjectToJSON(event);
//		payload=+payload+"}";
		
		//
		String payload="{\"user\":\"Sahil Enterprises\",\"email\":\"abc@xyz.com\",\"vendorId\": 966,\"event\": {\"id\": 5,\"vfStyleDTOs\": [{\"styleId\": 362023,\"vendorFundedPercentage\": 20},{\"styleId\":362025,\"vendorFundedPercentage\":12},{\"styleId\": 362024,\"vendorFundedPercentage\":13}]}}";
		
		
		//System.out.println(payload);
		
			String[] arr1 = { "593",payload};
			Object[][] dataSet = new Object[][] { arr1,  };
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

}
