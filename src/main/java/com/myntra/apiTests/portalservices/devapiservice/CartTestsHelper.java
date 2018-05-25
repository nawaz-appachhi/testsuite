package com.myntra.apiTests.portalservices.devapiservice;

import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class CartTestsHelper {

	static Initialize init = new Initialize("/Data/configuration");
	APIUtilities apiUtilities=new APIUtilities();
	IDPTestsHelper idpHelper = new IDPTestsHelper();
	
	//Method #1 - Get User Cart data
	public RequestGenerator getUserCart(String email, String password, boolean tamperSession)
	{
		IDPTestsHelper idpHelper = new IDPTestsHelper();
		MyntraService GetUserCartService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETUSERCART, init.Configurations);
		System.out.println("Get User Cart Service URL : "+GetUserCartService.URL);
		idpHelper.getAndSetTokens(email, password);
		String XID = idpHelper.XID;
		if(tamperSession)
		{
			XID.concat("###$$");
		}
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", XID);
		return new RequestGenerator(GetUserCartService,headers);
	
	}
	
	//Method #2 - Add item to Cart
	public RequestGenerator AddItemToCart(String email, String password, String skuId, boolean tamperSession)
	{
		IDPTestsHelper idpHelper = new IDPTestsHelper();
		MyntraService AddToCartService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIADDTOCART, init.Configurations, new String[]{ skuId });
		idpHelper.getAndSetTokens(email, password);
		String XID = idpHelper.XID;
		if(tamperSession)
		{
			XID.concat("###$$");
		}
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", XID);
		return new RequestGenerator(AddToCartService,headers);
	}

	//Method #3 - Clear user cart
	public void ClearUserCart(String email,String password)
	{
		IDPTestsHelper idpHelper = new IDPTestsHelper();
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART, APINAME.CLEARCART, init.Configurations);
		System.out.println("Clear user cart service URL : "+service.URL);
		idpHelper.getAndSetTokens(email, password);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", idpHelper.XID);
		new RequestGenerator(service, headers);
		
	}
	
	//Method #4 - Add Item to Cart with Style ID
	public RequestGenerator AddItemToCartWithStyleId(String email, String password, String skuId, String styleId, boolean tamperSession)
	{
		IDPTestsHelper idpHelper = new IDPTestsHelper();
		MyntraService AddToCartService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIADDTOCARTWITHSTYLEID, init.Configurations, new String[]{ skuId, styleId });
		idpHelper.getAndSetTokens(email, password);
		String XID = idpHelper.XID;
		if(tamperSession)
		{
			XID.concat("###$$");
		}
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", XID);
		return new RequestGenerator(AddToCartService,headers);


	}
}
