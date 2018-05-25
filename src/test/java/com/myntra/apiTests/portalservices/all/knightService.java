package com.myntra.apiTests.portalservices.all;
import com.myntra.apiTests.dataproviders.KnightServiceDataProvider;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Myntra;

public class knightService extends KnightServiceDataProvider {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(knightService.class);
	static APIUtilities apiUtil = new APIUtilities();
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider="setBlockDataProvider", 
			description ="1. Pass valid comibination of keys & block. \n 2. Hit SetBlock API of KnightService." 
			+"\n 3. Verify Nodes values for success.")
	public void KnightService_setBlock(String keys, String block)
	{
		String count = (keys.split("key\"").length - 1)+"";
		MyntraService ms = Myntra.getService(ServiceType.PORTAL_KNIGHTSERVICE, APINAME.SETBLOCK, init.Configurations, new String[]{ keys, count, block });
		System.out.println(ms.Payload);
		RequestGenerator rg = new RequestGenerator(ms);
		String response = rg.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("Response is not 200 OK", 200, rg.response.getStatus());
		AssertJUnit.assertEquals("Response is not matching[Keys]", true,"[]".equalsIgnoreCase(rg.respvalidate.GetNodeValue("keys", response)));
		AssertJUnit.assertEquals("Response is not matching[totalNoOfKeys]", true,count.equalsIgnoreCase(rg.respvalidate.GetNodeValue("totalNoOfKeys", response)));
		AssertJUnit.assertEquals("Response is not matching[successfulUpdates]", true,count.equalsIgnoreCase(rg.respvalidate.GetNodeValue("successfulUpdates", response)));
		AssertJUnit.assertEquals("Response is not matching[failedUpdates]", true,"0".equalsIgnoreCase(rg.respvalidate.GetNodeValue("failedUpdates", response)));
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider="setBlockDataProviderNegative",
			description ="1. Pass random comibination of keys & block. \n 2. Hit SetBlock API of KnightService." 
					+"\n 3. Verify API doesnt fails and sets the key block pairs.")
	public void KnightService_setBlock_Negative(String keys, String block)
	{
		String count = (keys.split("key\"").length - 1)+"";
		MyntraService ms = Myntra.getService(ServiceType.PORTAL_KNIGHTSERVICE, APINAME.SETBLOCK, init.Configurations, new String[]{ keys, count, block });
		System.out.println(ms.Payload);
		RequestGenerator rg = new RequestGenerator(ms);
		String response = rg.respvalidate.returnresponseasstring();
		System.out.println("Response::\n"+response);
		AssertJUnit.assertEquals("Response is not 200 OK", 200, rg.response.getStatus());
		AssertJUnit.assertEquals("Either value is not true/false or namespace is not valid [blockedLogin/blockedMobile/blockedIMEI][totalNoOfKeys]", true,count.equalsIgnoreCase(rg.respvalidate.GetNodeValue("totalNoOfKeys", response)));
		AssertJUnit.assertEquals("Either value is not true/false or namespace is not valid [blockedLogin/blockedMobile/blockedIMEI][successfulUpdates]", true,count.equalsIgnoreCase(rg.respvalidate.GetNodeValue("successfulUpdates", response)));
		AssertJUnit.assertEquals("Either value is not true/false or namespace is not valid [blockedLogin/blockedMobile/blockedIMEI][failedUpdates]", true, "0".equalsIgnoreCase(rg.respvalidate.GetNodeValue("failedUpdates", response)));
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider="getBlockDataProvider", 
			description= "1. Pass combination of keys & block. \n2.hit getBlock API of KnightService. \n3.Verify Nodes Values in response.")
	public void KnightService_getBlock(String keys, String block)
	{	
		int	count = (keys.split(",").length);		
		MyntraService ms = Myntra.getService(ServiceType.PORTAL_KNIGHTSERVICE, APINAME.GETBLOCK, init.Configurations, new String[]{ keys, count+"", block });
		System.out.println(ms.Payload);
		RequestGenerator rg = new RequestGenerator(ms);
		String response = rg.respvalidate.returnresponseasstring();
		System.out.println("Response:::"+response);
		AssertJUnit.assertEquals("Response is not 200 OK", 200, rg.response.getStatus());
		for(int i=0;i<keys.split(",").length;i++)
			AssertJUnit.assertEquals("Response is not matching[Keys]", true,response.contains(keys.split(",")[i]));	
		
		AssertJUnit.assertEquals("Response is not matching[totalNoOfKeys]", true,count == Integer.parseInt((rg.respvalidate.GetNodeValue("totalNoOfKeys", response))));
		AssertJUnit.assertEquals("Response is not matching[successfulFetches]", true,(response.split("value").length - 1) == Integer.parseInt((rg.respvalidate.GetNodeValue("successfulFetches", response))));
		AssertJUnit.assertEquals("Response is not matching[failedFetches]", true,( count - Integer.parseInt((rg.respvalidate.GetNodeValue("successfulFetches", response)))) == Integer.parseInt((rg.respvalidate.GetNodeValue("failedFetches", response))));
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider="getBlockDataProvider")
	public void KnightService_setCODBulk(String keys, String block)
	{	
		int	count = (keys.split(",").length);		
		MyntraService ms = Myntra.getService(ServiceType.PORTAL_KNIGHTSERVICE, APINAME.GETBLOCK, init.Configurations, new String[]{ keys, count+"", block });
		System.out.println(ms.Payload);
		RequestGenerator rg = new RequestGenerator(ms);
		String response = rg.respvalidate.returnresponseasstring();
		System.out.println("Response:::"+response);
		AssertJUnit.assertEquals("Response is not 200 OK", 200, rg.response.getStatus());
		for(int i=0;i<keys.split(",").length;i++)
			AssertJUnit.assertEquals("Response is not matching[Keys]", true,response.contains(keys.split(",")[i]));	
		
		AssertJUnit.assertEquals("Response is not matching[totalNoOfKeys]", true,count == Integer.parseInt((rg.respvalidate.GetNodeValue("totalNoOfKeys", response))));
		AssertJUnit.assertEquals("Response is not matching[successfulFetches]", true,(response.split("value").length - 1) == Integer.parseInt((rg.respvalidate.GetNodeValue("successfulFetches", response))));
		AssertJUnit.assertEquals("Response is not matching[failedFetches]", true,( count - Integer.parseInt((rg.respvalidate.GetNodeValue("successfulFetches", response)))) == Integer.parseInt((rg.respvalidate.GetNodeValue("failedFetches", response))));
	}
	
}


