package com.myntra.apiTests.portalservices.devapiservice;

import java.util.ArrayList;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

import net.minidev.json.JSONArray;

public class SearchTestsHelper extends CommonUtils
{
	static Initialize init = new Initialize("/Data/configuration");
	APIUtilities apiUtilities=new APIUtilities();
	IDPTestsHelper idpHelper = new IDPTestsHelper();
	
	//Method #1 - Search Auto Suggest
	public RequestGenerator getAutoSuggestData(String keyword, String resultSize)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETAUTOSUGGEST, init.Configurations);
		service.URL = apiUtilities.prepareparameterizedURL(service.URL, new String[] {keyword,resultSize});
		System.out.println("Get AutoSuggest Service URL : "+service.URL);
		return new RequestGenerator(service);

	}
	
	//Method #2 - KeyWord Search
	public RequestGenerator performKeywordSearch(String keyword)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIV2SEARCH, init.Configurations);
		service.URL = apiUtilities.prepareparameterizedURL(service.URL, keyword);
		System.out.println("Get Search Service URL : "+service.URL);
		return new RequestGenerator(service);

	}
	
	//Method #3 - Search Offers
	public RequestGenerator searchOffers(String keyword)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISEARCHOFFERS, init.Configurations);
		service.URL = apiUtilities.prepareparameterizedURL(service.URL, keyword);
		System.out.println("Get Search Offers Service URL : "+service.URL);
		return new RequestGenerator(service);

	}
	
	//Method #3 - KeyWord Search
	public ArrayList<String> performKeywordSearchAndReturnResultsAsList(String keyword)
	{
		ArrayList<String> StyleIds = new ArrayList<String>();
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIV2SEARCH, init.Configurations);
		service.URL = apiUtilities.prepareparameterizedURL(service.URL, keyword);
		System.out.println("Get Search Service URL : "+service.URL);
		RequestGenerator request = new RequestGenerator(service);
		String response = request.returnresponseasstring();
		try
		{
			JSONArray Ids = JsonPath.read(response, "$.data.results.products[*].styleid");
			for(int i=0; i<Ids.size(); i++)
			{
				StyleIds.add(Ids.get(i).toString());
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception while fetching style ids");
		}
		return StyleIds;
	}
	
	//Method #4 - Search Trends
	public RequestGenerator searchTrends()
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETSEARCHTRENDS, init.Configurations);
		System.out.println("Get Search Trends Service URL : "+service.URL);
		return new RequestGenerator(service);

	}
	
	
}
