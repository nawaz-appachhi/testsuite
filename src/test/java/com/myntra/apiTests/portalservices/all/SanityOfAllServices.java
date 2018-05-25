package com.myntra.apiTests.portalservices.all;
import com.myntra.apiTests.dataproviders.SanityOfAllServicesDP;

public class SanityOfAllServices extends SanityOfAllServicesDP {
	
	/*
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SanityOfAllServices.class);
	APIUtilities apiUtil = new APIUtilities();
	MyntraListenerV2 myntListner = new MyntraListenerV2();
	SessionServiceHelper sessionServiceHelper = new SessionServiceHelper();
	static String xID, sXid;
	static String Signature;
	String serviceUrl = "";
	String payload = "";
	String Response = "";

	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "getProductByIdDataProvider")
	public void CatalogService(String id)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CATALOGSERVICE, APINAME.GETPRODUCTBYID, init.Configurations);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, id);
		HashMap getParam = new HashMap();
		getParam.put("Authorization","Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "CheckoutServiceDP")
	public void CheckoutGetAllAddressService(String userName, String password)
	{
		getAndSetTokens(userName, password);
		MyntraService serviceGetAdd = Myntra.getService(ServiceType.PORTAL_ADDRESS, APINAME.GETALLADDRESS, init.Configurations);
		myntListner.setServiceUrlAndPayload(serviceGetAdd.URL, serviceGetAdd.Payload);
		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
		headersGetAdd.put("X-CSRF-Token", sXid);
		headersGetAdd.put("xid", xID);
		RequestGenerator req = new RequestGenerator(serviceGetAdd, headersGetAdd);
		myntListner.setServiceInfo(serviceGetAdd.URL, serviceGetAdd.Payload, req.response.toString());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "CheckoutServiceDP")
	public void CheckoutAdrressServiceabilityService(String userName, String password)
	{
		getAndSetTokens(userName, password);
		MyntraService serviceGetAdd = Myntra.getService(ServiceType.PORTAL_ADDRESS, APINAME.LISTALLADDRESSWITHSERVICEABILITY, init.Configurations);
		myntListner.setServiceUrlAndPayload(serviceGetAdd.URL, serviceGetAdd.Payload);
		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
		headersGetAdd.put("X-CSRF-Token", sXid);
		headersGetAdd.put("xid", xID);
		RequestGenerator req = new RequestGenerator(serviceGetAdd, headersGetAdd);
		myntListner.setServiceInfo(serviceGetAdd.URL, serviceGetAdd.Payload, req.response.toString());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "CheckoutServiceDP")
	public void CheckoutFetchCartService(String userName, String password)
	{
		getAndSetTokens(userName, password);
		MyntraService serviceGetAdd = Myntra.getService(ServiceType.PORTAL_CART, APINAME.OPERATIONFETCHCART, init.Configurations);
		myntListner.setServiceUrlAndPayload(serviceGetAdd.URL, serviceGetAdd.Payload);
		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
		headersGetAdd.put("X-CSRF-Token", sXid);
		headersGetAdd.put("xid", xID);
		RequestGenerator req = new RequestGenerator(serviceGetAdd, headersGetAdd);
		myntListner.setServiceInfo(serviceGetAdd.URL, serviceGetAdd.Payload, req.response.toString());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "CheckoutServiceDP")
	public void CheckoutFetchWishListService(String userName, String password)
	{
		getAndSetTokens(userName, password);
		MyntraService serviceGetAdd = Myntra.getService(ServiceType.PORTAL_CART, APINAME.OPERATIONFETCHWISHLIST, init.Configurations);
		myntListner.setServiceUrlAndPayload(serviceGetAdd.URL, serviceGetAdd.Payload);
		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
		headersGetAdd.put("X-CSRF-Token", sXid);
		headersGetAdd.put("xid", xID);
		RequestGenerator req = new RequestGenerator(serviceGetAdd, headersGetAdd);
		myntListner.setServiceInfo(serviceGetAdd.URL, serviceGetAdd.Payload, req.response.toString());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "CheckoutServiceDP")
	public void CheckoutMyntCreditsService(String userName, String password)
	{
		getAndSetTokens(userName, password);
		MyntraService serviceGetAdd = Myntra.getService(ServiceType.PORTAL_CART, APINAME.GETMYNTCREDIT, init.Configurations);
		myntListner.setServiceUrlAndPayload(serviceGetAdd.URL, serviceGetAdd.Payload);
		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
		headersGetAdd.put("X-CSRF-Token", sXid);
		headersGetAdd.put("xid", xID);
		RequestGenerator req = new RequestGenerator(serviceGetAdd, headersGetAdd);
		myntListner.setServiceInfo(serviceGetAdd.URL, serviceGetAdd.Payload, req.response.toString());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "CheckoutServiceDP")
	public void CheckoutGetAllCouponsService(String userName, String password)
	{
		getAndSetTokens(userName, password);
		MyntraService serviceGetAdd = Myntra.getService(ServiceType.PORTAL_CART, APINAME.FETCHALLCOUPONS, init.Configurations, PayloadType.JSON, PayloadType.JSON);
		myntListner.setServiceUrlAndPayload(serviceGetAdd.URL, serviceGetAdd.Payload);
		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
		headersGetAdd.put("X-CSRF-Token", sXid);
		headersGetAdd.put("xid", xID);
		RequestGenerator req = new RequestGenerator(serviceGetAdd, headersGetAdd);
		myntListner.setServiceInfo(serviceGetAdd.URL, serviceGetAdd.Payload, req.response.toString());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "CMSServiceDP")
	public void CMSService(String id)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CMSSERVICE, APINAME.GETCONTENTBYID,init.Configurations);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, id);
		HashMap getParam = new HashMap();
		getParam.put("Authorization","Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" })
	public void DiscountGetAllDiscountsService() {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DISCOUNTSERVICE, APINAME.GETALLDISCOUNTS,init.Configurations);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		AssertJUnit.assertEquals("getAllDiscounts is not working", 200,req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "IdpServiceDP")
	public void IDPService(String userName, String password )
	{
		MyntraService signInService = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.SIGNIN, init.Configurations, new String[]{ userName, password });
		myntListner.setServiceUrlAndPayload(signInService.URL, signInService.Payload);
		RequestGenerator signInReqGen = new RequestGenerator(signInService);
		myntListner.setServiceInfo(signInService.URL, signInService.Payload, signInReqGen.response.toString());
		AssertJUnit.assertEquals("IDPService is not working", 200, signInReqGen.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "getKeyValuePairDataProvider")
	public void KeyValuePairService(String urlparams)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CONFIGURATION, APINAME.GETKEYVALUE, init.Configurations, PayloadType.JSON, new String[] { urlparams }, PayloadType.JSON);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		AssertJUnit.assertEquals("Get KeyValuePair service is not working", 200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityonFox", "Sanityonprod" } )
	public void LookGoodService()
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOOKGOOD, APINAME.GETLOOKBOOKBYID,init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, "2");
		System.out.println(service.URL);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		AssertJUnit.assertEquals("Look Good service is not working", 200,req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "getAccountInfoDataProvider")
	public void LoyaltyPointsService(String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY, APINAME.ACCOUNTINFOLOYALITY, init.Configurations, PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		MyntAssert.assertEquals("Response code of getAccountInfo API does not match", 200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" } ,dataProvider = "checkbalanceDataProvider" )
	public void MyntraCashService(String emailID, String newUser)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKBALANCE,init.Configurations, PayloadType.JSON, new String[]{emailID}, PayloadType.JSON);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		AssertJUnit.assertEquals("MyntraCashService is not working", 200,req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "getActiveNotificationsCountDataProvider")
	public void NotificationService(String userId,String portalGroup) {
		MyntraService service = Myntra.getService( ServiceType.PORTAL_NOTIFICATIONS, APINAME.GETACTIVENOTIFICATIONSCOUNT, init.Configurations, PayloadType.JSON, new String[] { userId, portalGroup }, PayloadType.JSON);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		AssertJUnit.assertEquals("getActiveNotificationsCount API is not working", 200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" })
	public void PageConfiguratorService()
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CONFIGURATION, APINAME.GETPAGETYPE, init.Configurations);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		AssertJUnit.assertEquals("getAllPageType API is not working ", 200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices"}, dataProvider = "refundAPIDataProvider")
	public void PaymentServiceRefundAPI(
			String orderID, String clientTransactionId, String amount, String client, String version) {
		Signature = generateSignature(client, version, orderID,clientTransactionId,amount);
		String[] PayloadParams = new String[] { orderID, clientTransactionId,amount,client, version, Signature };
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PAYMENTSERVICE, APINAME.REFUNDAPI, init.Configurations, PayloadParams, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("Refund API is not working", 200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "SearchServiceDP")
	public void SearchService(String param1, String param2, String param3, String param4)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY, init.Configurations, new String[] { param1, param2, param3, param4 });
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		System.out.println(service.Payload);
		AssertJUnit.assertEquals("getQueryWithTrueReturnDoc is not working", 200, req.response.getStatus());
	}
	@Test( groups = {  "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "SessionServiceDP" )
	public void SessionService(String userName, String password)
	{
		String xId = performGetXId(userName, password);
		AssertJUnit.assertNotNull("xId is null", xId);
		MyntraService getSessionService = Myntra.getService(ServiceType.PORTAL_SESSION, APINAME.GETSESSION, init.Configurations, PayloadType.XML, PayloadType.XML);
		System.out.println(getSessionService.URL);
		getSessionService.URL = apiUtil.prepareparameterizedURL(getSessionService.URL, xId);
		System.out.println(getSessionService.URL);
		myntListner.setServiceUrlAndPayload(getSessionService.URL, getSessionService.Payload);
		RequestGenerator getSessionServiceReqGen = new RequestGenerator(getSessionService);
		myntListner.setServiceInfo(getSessionService.URL, getSessionService.Payload, getSessionServiceReqGen.response.toString());
		String getSessionServiceXMLResponse = getSessionServiceReqGen.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals("SessionService getSession API is not working", 200, getSessionServiceReqGen.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "StyleServiceDP")
	public void Style_StyleDataForSingleStyleIdService(String styleId)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.GETSTYLEDATAGET, init.Configurations);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		APIUtilities utilities = new APIUtilities();
		service.URL = utilities.prepareparameterizedURL(service.URL, styleId);
		RequestGenerator req = new RequestGenerator(service);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		AssertJUnit.assertEquals("StyleService is not working",200,req.response.getStatus());
		AssertJUnit.assertTrue("statusType is not SUCCESS", !req.respvalidate.GetNodeValue("status.statusType", req.returnresponseasstring()).contains("\"ERROR\""));
	}
	@Test(groups = { "SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "styleDataForListOfStyleIdDP")
	public void Style_styleDataForListOfStyleIdService(String styleIds)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.GETSTYLEDATAPOST, init.Configurations);
		String payload = Arrays.toString(styleIds.split(","));
		service.Payload = payload;
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("Style Data for List Of StyleId API is not working",200,req.response.getStatus());
		AssertJUnit.assertTrue("statusType is not SUCCESS", !req.respvalidate.GetNodeValue("status.statusType", req.returnresponseasstring()).contains("\"ERROR\""));
	}
	@Test(groups = {"SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "pdpDataForSingleStyleIdDP")
	public void Style_pdpDataForSingleStyleIdService(String styleId)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.GETPDPDATAGET, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		System.out.println(service.URL);
		service.URL = utilities.prepareparameterizedURL(service.URL, styleId);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("pdpDataForSingleStyleId API is not working",200,req.response.getStatus());
		AssertJUnit.assertTrue("statusType is not SUCCESS", !req.respvalidate.GetNodeValue("status.statusType", req.returnresponseasstring()).contains("\"ERROR\""));
	}
	@Test(groups = {"SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "pdpDataForMultipleStyleIdDP")
	public void Style_pdpDataForListOfStyleIdService(String styleIds)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.GETPDPDATAPOST, init.Configurations);
		String payload = Arrays.toString(styleIds.split(","));
		service.Payload = payload;
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("pdpDataForListOfStyleId API is not working",200,req.response.getStatus());
		AssertJUnit.assertTrue("statusType is not SUCCESS", !req.respvalidate.GetNodeValue("status.statusType", req.returnresponseasstring()).contains("\"ERROR\""));
	}
	@Test(groups = {"SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf" }, dataProvider = "pdpDataBySingleSkuIdDP")
	public void Style_pdpDataBySingleSkuIdService(String skuId)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.GETPDPDATABYSKUID, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		System.out.println(service.URL);
		service.URL = utilities.prepareparameterizedURL(service.URL, skuId);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("pdpData Single SkuId API is not working",200,req.response.getStatus());
		AssertJUnit.assertTrue("statusType is not SUCCESS", !req.respvalidate.GetNodeValue("status.statusType", req.returnresponseasstring()).contains("\"ERROR\""));
	}
	@Test(groups = {"SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa", "SanityonFox", "Sanityonprod", "SanityonPerf"})
	public void TopNavService()
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_TOPNAV, APINAME.GETALLTOPNAV,init.Configurations);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		System.out.println(service.URL+"  :  "+service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		AssertJUnit.assertEquals("Get Top Nav API is not working", 200,req.response.getStatus());
	}

	@Test(groups = {"SanityOfAllServices","SanityonFox", "Sanityonprod", "SanityonPerf"})
	public void DevApis_TopNavService()
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPINAV, init.Configurations);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		AssertJUnit.assertEquals("DevApis_TopNavService is not working",200,req.response.getStatus());
	}
	@Test(groups = {"SanityOfAllServices", "SanityonFox", "Sanityonprod", "SanityonPerf"}, dataProvider = "devApisStyleServiceDP")
	public void DevApis_StyleService(String styleId)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISTYLE, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, styleId);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		AssertJUnit.assertEquals("DevApis_TopNavService is not working",200,req.response.getStatus());
	}
	@Test(groups = {"SanityOfAllServices", "SanityonQa"})
	public void MarketPlace_GetVendorScoreTrendEntityService()
	{
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACESCORECARD, APINAME.GETVENDORSCORETRENDENTITY, init.Configurations, PayloadType.XML, PayloadType.XML);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");
		RequestGenerator req = new RequestGenerator(service, getParam);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		AssertJUnit.assertEquals("MarketPlace_GetVendorScoreTrendEntityService is not working",200,req.response.getStatus());
	}
	@Test(groups = {"SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa"})
	public void MarketPlace_GetVendorScoreEntryService()
	{
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACESCORECARD, APINAME.GETVENDORSCOREENTRY, init.Configurations, PayloadType.XML, PayloadType.XML);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");
		RequestGenerator req = new RequestGenerator(service, getParam);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		AssertJUnit.assertEquals("MarketPlace_GetVendorScoreEntryService is not working",200,req.response.getStatus());
	}
	@Test(groups = {"SanityOfAllServices", "SanityOfSelectedServices", "SanityonQa"})
	public void MarketPlace_GetParametersForCriteriaService()
	{
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACESCORECARD, APINAME.GETPARAMETERFORCRITERIA, init.Configurations, PayloadType.XML, PayloadType.XML);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization","Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");
		RequestGenerator req = new RequestGenerator(service, getParam);
		myntListner.setServiceInfo(service.URL, service.Payload, req.response.toString());
		AssertJUnit.assertEquals("MarketPlace_GetParametersForCriteriaService is not working",200,req.response.getStatus());
	}
	@Test(groups = {"SanityOfAllServices", "SanityonQa", "SanityonFox", "Sanityonprod"}, dataProvider = "servicabilitycheckDP")
	public void LMS_CheckservicabilityService(String searchString)
	{
		ServiceType serviceundertest = ServiceType.ERP_LMS;
		APINAME apiundertest = APINAME.CHECKSERVICABILITY;
		MyntraService service = Myntra.getService(serviceundertest,apiundertest, InitializeFramework.init.Configurations);
		service.responsedataformat = PayloadType.XML;
		APIUtilities utils = new APIUtilities();
		service.URL= utils.prepareparameterizedURL(service.URL,searchString);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put(HttpHeaders.AUTHORIZATION, "Basic YTpi");
		RequestGenerator request = new RequestGenerator(service,headers);
		AssertJUnit.assertEquals("Response Code", 200, request.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityonQa", "SanityonFox", "Sanityonprod" } ,dataProvider = "servicabilitycheckDP" )
	public void LMS_CheckservicabilityPutrequestService(String searchString)
	{
		ServiceType serviceundertest = ServiceType.ERP_LMS;
		APINAME apiundertest = APINAME.CHECKSERVICABILITYPUTCALL;
		MyntraService service = Myntra.getService(serviceundertest,apiundertest, InitializeFramework.init.Configurations,new String[]{searchString},PayloadType.XML,PayloadType.XML);
		service.responsedataformat = PayloadType.XML;
		service.payloaddataformat = PayloadType.XML;
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put(HttpHeaders.AUTHORIZATION, "Basic YTpi");
		RequestGenerator request = new RequestGenerator(service,headers);
		AssertJUnit.assertEquals("Response Code", 200, request.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityonQa", "SanityonFox"} )
	public void WMS_WarehouseSearchService()
	{
		ServiceType serviceundertest = ServiceType.ERP_WMS;
		APINAME apiundertest = APINAME.WAREHOUSESEARCH;
		MyntraService service = Myntra.getService(serviceundertest,apiundertest, InitializeFramework.init.Configurations,PayloadType.XML,PayloadType.XML);
		service.responsedataformat = PayloadType.XML;
		service.payloaddataformat = PayloadType.XML;
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put(HttpHeaders.AUTHORIZATION, "Basic RVJQIFNjaGVkdWxlcn5lcnBfc2NoZWR1bGVyfjpzY2hlZHVsZXI=");
		RequestGenerator request = new RequestGenerator(service,headers);
		AssertJUnit.assertEquals("Response Code", 200, request.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityonQa", "SanityonFox"} )
	public void WMS_OrderSearchService()
	{
		ServiceType serviceundertest = ServiceType.ERP_WMS;
		APINAME apiundertest = APINAME.ORDERSEARCH;
		MyntraService service = Myntra.getService(serviceundertest,apiundertest, InitializeFramework.init.Configurations,PayloadType.XML,PayloadType.XML);
		service.responsedataformat = PayloadType.XML;
		service.payloaddataformat = PayloadType.XML;
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put(HttpHeaders.AUTHORIZATION, "Basic RVJQIFNjaGVkdWxlcn5lcnBfc2NoZWR1bGVyfjpzY2hlZHVsZXI=");
		RequestGenerator request = new RequestGenerator(service,headers);
		AssertJUnit.assertEquals("Response Code", 200, request.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityonQa", "SanityonFox"} )
	public void OMS_WarehouseSearchService()
	{
		ServiceType serviceundertest = ServiceType.ERP_OMS;
		APINAME apiundertest = APINAME.OMSWAREHOUSESEARCH;
		MyntraService service = Myntra.getService(serviceundertest,apiundertest, InitializeFramework.init.Configurations,PayloadType.XML,PayloadType.XML);
		service.responsedataformat = PayloadType.XML;
		service.payloaddataformat = PayloadType.XML;
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put(HttpHeaders.AUTHORIZATION, "Basic RVJQIFNjaGVkdWxlcn5lcnBfc2NoZWR1bGVyfjpzY2hlZHVsZXI=");
		RequestGenerator request = new RequestGenerator(service,headers);
		AssertJUnit.assertEquals("Response Code", 200, request.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityonFox", "Sanityonprod" }, dataProvider = "CRMPortalCustomerProfileServiceDP")
	public void CRMPortal_CustomerProfileService(String loginId, String successRespCode, String successStatusCode, String successstotalcount, String successstatustype) throws Exception {
		MyntraService service = Myntra.getService(ServiceType.ERP_CRMSERVICEPORTAL, APINAME.CUSTOMERPROFILE, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, loginId);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		RequestGenerator req = new RequestGenerator(service, getParam);
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityonFox", "Sanityonprod" }, dataProvider = "CRMERPOrderServiceDP")
	public void CRMERP_OrderService(String finalstring1, String finalstring2) throws Exception 
	{
		MyntraService service = Myntra.getService(ServiceType.ERP_CRMSERVICEERP, APINAME.ORDER, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] { finalstring1, "portal-automation@myntra.com", finalstring2 });
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		RequestGenerator req = new RequestGenerator(service, getParam);
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityonFox", "Sanityonprod" }, dataProvider = "CRMRightNowGetNEFTABankccountServiceDP")
	public void CRMRightNow_GetNEFTABankccountService(String emailid, String neftid) throws Exception 
	{
		MyntraService service = Myntra.getService(ServiceType.ERP_CRMSERVICERIGHTNOW, APINAME.GETNEFTBANKACCOUNT, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] { emailid, neftid });
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		RequestGenerator req = new RequestGenerator(service, getParam);
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityonFox", "Sanityonprod" }, dataProvider = "giftcardserviceDP")
	public void Giftcard_GetBalanceService(String giftCardType, String giftCardNumber, String giftCardPinCode, String userLogin) throws Exception 
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_GIFTCARDSERVICE, APINAME.GETGIFTCARDBALANCE, init.Configurations, new String[]{ giftCardType, giftCardNumber, giftCardPinCode, userLogin }, PayloadType.JSON, PayloadType.JSON);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	@Test(groups = { "SanityOfAllServices", "SanityonQa", "SanityonFox" })
	public void Deals_GetAllVisibleDealsService() throws Exception 
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEALSSERVICE, APINAME.GETALLVISIBLEDEALS, init.Configurations, new String[]{ "1","1","1" });
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	public String performGetXId(String userName, String passWord)
	{
		String sessionId = null;
		MyntraService signInService = Myntra.getService(ServiceType.PORTAL_IDP,APINAME.SIGNIN, init.Configurations, new String[]{ userName, passWord });
		RequestGenerator signInReqGen = new RequestGenerator(signInService);
		MultivaluedMap<String, Object> map = signInReqGen.response.getHeaders();
		for (Entry<String, java.util.List<Object>> entry : map.entrySet())
		{
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
				sessionId = entry.getValue().toString();
		}
		sessionId = sessionId.substring((sessionId.indexOf("[") + 1), sessionId.lastIndexOf("]"));
		System.out.println("xId  :  "+sessionId);
		return sessionId;
	}
	private String generateSignature(String client, String version, String orderID, String clientTransactionId, String amount) 
	{
		String Signature = "";
		String parameter = client + "|" + version + "|" + orderID + "|" + clientTransactionId
				+ "|" + amount + "|" + client;
		System.out.println("Parameter Generated :" + parameter);
		try {
			Signature = computeSHA512(new String(parameter));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return (Signature);
	}
	private String computeSHA512(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] hashBytes = md.digest(data.getBytes());
		return Hex.encodeHexString(hashBytes);
	}
	private RequestGenerator getChkBlnceRequest(String userID){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKBALANCE,init.Configurations, PayloadType.JSON, new String[]{userID}, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		return req;
	}
	private Double getBalance(String emailID){
		String strToreturn ;
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKBALANCE,init.Configurations, PayloadType.JSON, new String[]{emailID}, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		strToreturn = req.respvalidate.GetNodeTextUsingIndex("balance");
		strToreturn = strToreturn.substring(strToreturn.indexOf("'")+1, strToreturn.indexOf("'", strToreturn.indexOf("'")+1));
		return Double.parseDouble(strToreturn);
	}
	private void getAndSetTokens(String userName, String password)
	{
		MyntraService serviceSignIn = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.SIGNIN, init.Configurations, new String[]{ userName, password });
		System.out.println(serviceSignIn.URL);
		System.out.println(serviceSignIn.Payload);
		RequestGenerator reqSignIn = new RequestGenerator(serviceSignIn);
		MultivaluedMap<String, Object> map = reqSignIn.response.getHeaders();
		System.out.println(reqSignIn.respvalidate.returnresponseasstring());
		for (Map.Entry entry : map.entrySet())
		{
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
				xID = entry.getValue().toString();
		}
		xID = xID.substring((xID.indexOf("[") + 1), xID.lastIndexOf("]"));
		sXid = reqSignIn.respvalidate.GetNodeTextUsingIndex("xsrfToken");
		System.out.println(xID);
		System.out.println(sXid);
		if(sXid.contains("'"))
			sXid = sXid.substring(sXid.indexOf("'")+1, sXid.lastIndexOf("'"));
		else
			sXid = sXid.substring(sXid.indexOf("[")+1, sXid.lastIndexOf("]"));
	}
	
	*/
}
