package com.myntra.apiTests.portalservices.appservices;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.SearchDataDP;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.myntra.apiTests.InitializeFramework;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class SearchDataTests extends SearchDataDP {
	static Initialize init = InitializeFramework.init;
	static Logger log = Logger.getLogger(SearchDataTests.class);

	@Test(groups = { }, dataProvider = "searchDataQueryDataProvider")
	public void searchDataQuery(String query) {
		ServiceType serviceUnderTest = ServiceType.PORTAL_MOBILEAPPWEBSERVICES;
		MyntraService service = Myntra.getService(serviceUnderTest,
				APINAME.MOBILEAPPSEARCHDATAQUERY, init.Configurations, null,
				new String[] { query });
		log.info(service.URL);
		RequestGenerator request = new RequestGenerator(service);
		log.info(request.response);
		Assert.assertEquals( request.response.getStatus(),200,
				"Response Code Error");
	}

	@Test(groups = { }, dataProvider = "searchDataWithOneFilterDataProvider")
	public void searchDataWithOneFilter(String query, String artifact,
			String value) {
		String[] urlParams = { query, artifact, value };
		ServiceType serviceUnderTest = ServiceType.PORTAL_MOBILEAPPWEBSERVICES;
		MyntraService service = Myntra.getService(serviceUnderTest,
				APINAME.MOBILEAPPSEARCHDATAAPPLYONEFILTER, init.Configurations,
				null, urlParams);
		System.out.println(service.URL);
		RequestGenerator request = new RequestGenerator(service);
		log.info(request.response);
		Assert.assertEquals(request.response.getStatus(),200,
				"Response Code Error");
	}

	@Test(groups = { }, dataProvider = "searchDataWithTwoFiltersDataProvider")
	public void searchDataWithTwoFilters(String query, String artifact1,
			String value1, String artifact2, String value2) {
		String[] urlParams = { query, artifact1, value1, artifact2, value2 };
		ServiceType serviceUnderTest = ServiceType.PORTAL_MOBILEAPPWEBSERVICES;
		MyntraService service = Myntra.getService(serviceUnderTest,
				APINAME.MOBILEAPPSEARCHDATAAPPLYTWOFILTERS,
				init.Configurations, null, urlParams);
		System.out.println(service.URL);
		RequestGenerator request = new RequestGenerator(service);
		log.info(request.response);
		Assert.assertEquals(request.response.getStatus(), 200,
				"Response Code Error");
	}

	@Test(groups = { }, dataProvider = "searchDataWithAllFiltersDataProvider")
	public void searchDataWithAllFilters(String query, String artifact1,
			String value1, String artifact2, String value2, String artifact3,
			String value3, String artifact4, String value4, String artifact5,
			String value5) {
		String[] urlParams = { query, artifact1, value1, artifact2, value2,
				artifact3, value3, artifact4, value4, artifact5, value5 };
		ServiceType serviceUnderTest = ServiceType.PORTAL_MOBILEAPPWEBSERVICES;
		MyntraService service = Myntra.getService(serviceUnderTest,
				APINAME.MOBILEAPPSEARCHDATAAPPLYALLFILTERS,
				init.Configurations, null, urlParams);
		System.out.println(service.URL);
		RequestGenerator request = new RequestGenerator(service);
		log.info(request.response);
		Assert.assertEquals(request.response.getStatus(),200,
				"Response Code Error");
	}

	@Test(groups = { }, dataProvider = "searchDataSortByDataProvider")
	public void searchDataSortBy(String query, String sortParam) {
		String[] urlParams = { query, sortParam };
		ServiceType serviceUnderTest = ServiceType.PORTAL_MOBILEAPPWEBSERVICES;
		MyntraService service = Myntra.getService(serviceUnderTest,
				APINAME.MOBILEAPPSEARCHDATASORTBY, init.Configurations, null,
				urlParams);
		System.out.println(service.URL);
		RequestGenerator request = new RequestGenerator(service);
		log.info(request.response);
		Assert.assertEquals(request.response.getStatus(),200,
				"Response Code Error");
	}
}