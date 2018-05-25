package com.myntra.apiTests.portalservices.OrderFlowWIthDiscountHelper;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import com.myntra.lordoftherings.Initialize;

import com.myntra.lordoftherings.legolas.Commons;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


public class OrderFlowWIthDiscountServiceHelper  extends CommonUtils {
	Commons commonUtil = new Commons();
	StyleServiceApiHelper styleHelper = new StyleServiceApiHelper();
	static Initialize init = new Initialize("/Data/configuration");
	List<String> style = new ArrayList<String>();
	static String xID, sXid;
	static BountyServiceHelper bountyHelper = new  BountyServiceHelper();




	
	
	List<Integer> styleIdNike = styleHelper.performSeachServiceToGetStyleIds("Roadster", "15", "true", "false");
	List<Integer> styleIdAdidas = styleHelper.performSeachServiceToGetStyleIds("HRX", "15", "true", "false");
	List<Integer> styleIdPuma = styleHelper.performSeachServiceToGetStyleIds("Puma", "15", "true", "false");
	List<Integer> styleIdJackets = styleHelper.performSeachServiceToGetStyleIds("Jackets", "15", "true", "false");
	List<Integer> styleIdShoes = styleHelper.performSeachServiceToGetStyleIds("Shoes", "15", "true", "false");
	List<Integer> styleIdJeans = styleHelper.performSeachServiceToGetStyleIds("Jeans", "15", "true", "false");
	List<Integer> styleIdShirts = styleHelper.performSeachServiceToGetStyleIds("Shirts", "15", "true", "false");
	List<Integer> styleIdTshirts = styleHelper.performSeachServiceToGetStyleIds("Tshirts", "15", "true", "false");
	List<Integer> styleIdSunglasses = styleHelper.performSeachServiceToGetStyleIds("Sunglasses", "15", "true", "false");
	List<Integer> styleIdWallets = styleHelper.performSeachServiceToGetStyleIds("Wallets", "15", "true", "false");
	List<Integer> styleIdSportsShoes = styleHelper.performSeachServiceToGetStyleIds("flip-flops", "15", "true", "false");
	
	
	
	private Object[] sumOfAllStyleIds(){
		int size = styleIdAdidas.size();
		Object[] adidasJackets = ArrayUtils.addAll(styleIdAdidas.toArray(), styleIdJackets.toArray());
		Object[] jeansNike = ArrayUtils.addAll(styleIdJeans.toArray(), styleIdNike.toArray());
		Object[] pumaShirts = ArrayUtils.addAll(styleIdPuma.toArray(), styleIdShirts.toArray());
		Object[] shoesSportsShoes = ArrayUtils.addAll(styleIdShoes.toArray(), styleIdSportsShoes.toArray());
		Object[] tshirtsWallets = ArrayUtils.addAll(styleIdTshirts.toArray(), styleIdWallets.toArray());

		Object[] adidasJacketsJeansNike = ArrayUtils.addAll(adidasJackets, jeansNike);
		Object[] pumaShirtsShoesSportsShoes = ArrayUtils.addAll(pumaShirts, shoesSportsShoes);
		Object[] adidasJacketsJeansNikePumaShirtsShoesSportsShoes = ArrayUtils.addAll(adidasJacketsJeansNike, pumaShirtsShoesSportsShoes);
		Object[] adidasJacketsJeansNikePumaShirtsShoesSportsShoesTshirtsWallets = ArrayUtils.addAll(adidasJacketsJeansNikePumaShirtsShoesSportsShoes, tshirtsWallets);

		return adidasJacketsJeansNikePumaShirtsShoesSportsShoesTshirtsWallets;
	}
	
	//requestMethao
	public RequestGenerator invokeGetStyleData(String StyleId)
	{
		return styleHelper.getStyleDataForSingleStyleId(StyleId);
		
	}
	
	public RequestGenerator invokeGetPdpStyleData(String StyleId)
	{
		return styleHelper.getPdpDataForSingleStyleId(StyleId);
		
	}
	
	public RequestGenerator createFlatDiscount(String expiredDate,
			String startDate, String percent, String styleID)
			throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.CREATEFLATDISCOUNTV2,
				init.Configurations, new String[] { expiredDate, startDate,
						percent, styleID}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Created DIscount Percenatge payload\n" + service.Payload);
	
		return new RequestGenerator(service); 

	}
	
	public List <Integer> getStyleIdsUsingSearchAPI ()
	{ 
		String query = "HRX";
		String rows = "20";
		String returnDocs = "true";
		String isFacet = "true";
		
		MyntraService searchService = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY, init.Configurations,
				new String[] { query, rows, returnDocs, isFacet }, PayloadType.JSON, PayloadType.JSON);
		System.out.println("service url....." +searchService.Payload);
		RequestGenerator searchServiceReqGen = new RequestGenerator(searchService);
		String resp = JsonPath.read(searchServiceReqGen.respvalidate.returnresponseasstring(), "$.data.queryResult");
		return JsonPath.read(resp, "$.response1..products[*].styleid");
	}
	
	public String createAndQueueOrder(String loginId, String sessionId, String binNumber, String pgDiscount, String emiCharge, String addressId, String clientContext, String cartContext, String paymentMethod) throws JsonParseException, JsonMappingException, IOException{
/*
		@SuppressWarnings("deprecation")
		OrderCreationEntry orderEntry =BountyServiceHelper.createOrder(loginId, sessionId, binNumber, pgDiscount, emiCharge, addressId, clientContext, cartContext, paymentMethod);
		@SuppressWarnings("deprecation")
		RequestGenerator requestGenerator = bountyHelper.queueOrder(""+orderEntry.getId(), sessionId, "0.0", loginId, cartContext, binNumber, paymentMethod, pgDiscount, "SUCCESS");
		System.out.println("ORDER ID________ \n" +orderEntry.getId() );
*/
		String orderID = "";//orderEntry.getId().toString();
		return orderID;
	}
	
	public RequestGenerator rindexonestleid(String styleId)
	{

		MyntraService doStyleIndexForSingleStyleIdService = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.REINDEXONESTYLEIDPOST, init.Configurations,new String[] { styleId},PayloadType.JSON,PayloadType.JSON);
		System.out.println("index style url = " +doStyleIndexForSingleStyleIdService.URL);
		System.out.println("index style url = " +doStyleIndexForSingleStyleIdService.Payload);

		return new RequestGenerator(doStyleIndexForSingleStyleIdService); 

	}

	
	public String  getAndSetTokens(String userName, String password) {
		System.out.println("\nPrinting \n Username : " + userName + " \n Password : " + password);

		MyntraService serviceSignIn = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.SIGNIN, init.Configurations,
				new String[] { userName, password });
		System.out.println("\nPrinting IDP Service API URL : " + serviceSignIn.URL);

		System.out.println("\nPrinting IDP Service API Payload : \n\n" + serviceSignIn.Payload);

		RequestGenerator reqSignIn = new RequestGenerator(serviceSignIn);
		System.out.println(
				"\nPrinting IDP Service API response .....\n\n" + reqSignIn.respvalidate.returnresponseasstring());

		MultivaluedMap<String, Object> map = reqSignIn.response.getHeaders();
		for (Map.Entry entry : map.entrySet()) {
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
				xID = entry.getValue().toString();
		}
		System.out.println("\nPrinting xID from Headers  : " + xID);

		xID = xID.substring((xID.indexOf("[") + 1), xID.lastIndexOf("]"));
		sXid = reqSignIn.respvalidate.GetNodeTextUsingIndex("xsrfToken");

		System.out.println("\nPrinting sXid from Response  : " + sXid);

		if (sXid.contains("'"))
			sXid = sXid.substring(sXid.indexOf("'") + 1, sXid.lastIndexOf("'"));
		else
			sXid = sXid.substring(sXid.indexOf("[") + 1, sXid.lastIndexOf("]"));

		System.out.println("\nPrinting final xID : " + xID);

		System.out.println("\nPrinting final sxid : " + sXid);

		System.out.println("\nPrinting IDP Service response : " + reqSignIn.response);
		
		return xID;
	}

	
	
	public List<String>  getValidSkuidUsingStyleAPI (List <Integer> styleIdList)
	{
		List<String> style = new ArrayList<String>();

		String validSkuId = null;
		if (!CollectionUtils.isEmpty(styleIdList))
		{
			for (Integer styleId: styleIdList)
			{
				System.out.println("....style id...."+String.valueOf(styleId));
				MyntraService getStyleDataService = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.GETSTYLEDATAGET, init.Configurations, PayloadType.JSON,new String [] {String.valueOf(styleId)},PayloadType.JSON);
				System.out.println("...url..." +getStyleDataService.URL);
				RequestGenerator getStyleDataReqGen = new RequestGenerator(getStyleDataService);
				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				System.out.println(" getstyle api Response----->> \n" + response);
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
				
				for(int i = 0 ; i < ids.size(); i++)
				{
					String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();

					String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
					String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
					
					if(!inventoryCount.equals("0") && available.equals("true"))
					{
						validSkuId = skuId;
						System.out.println("valid skuid = " +validSkuId);
						style.add(validSkuId);
						style.add(styleId.toString());
						return style;
						
						
						
					}
					
					System.out.println("\nThe skuId: "+skuId+" for the styleId: "+styleId+" is out of stock.\n");
			   }
				
				System.out.println("\nThe style id : "+styleId+" is out of stock.\n");
		}
		
	}
		return style;
}
	
	
	public String getOneStyleID()
	{
		String styleIds = null;
		try {
			styleIds = Commons.getCommaSeperatedValuesFromArray(sumOfAllStyleIds(), 1, false);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return styleIds;
	}

}
