package com.myntra.apiTests.portalservices.devapiservice;

import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import org.apache.log4j.Logger;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class GeneralDevAPITestsHelper {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(IDPTestsHelper.class);
	APIUtilities apiUtil=new APIUtilities();

	//Method #1 Get Navigation Config
	public RequestGenerator getGuestNavMenuConfig(APINAME apiname)
	{
		MyntraService GetNavMenuConfig = Myntra.getService(ServiceType.PORTAL_DEVAPIS, apiname, init.Configurations);
		System.out.println("Get Style Information Service URL : "+GetNavMenuConfig.URL);
		return new RequestGenerator(GetNavMenuConfig);
	}
	
	public RequestGenerator getUserNavMenuConfig(APINAME apiname, String email, String password)
	{
		IDPTestsHelper Obj = new IDPTestsHelper();
		Obj.getAndSetTokens(email, password);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("sxid", Obj.SXID);
		headers.put("xid", Obj.XID);
		MyntraService GetNavMenuConfig = Myntra.getService(ServiceType.PORTAL_DEVAPIS, apiname, init.Configurations);
		System.out.println("Get Style Information Service URL : "+GetNavMenuConfig.URL);
		return new RequestGenerator(GetNavMenuConfig,headers);
	}
	
	public RequestGenerator getAndroidLayouts(String LayoutName)
	{
		MyntraService GetAndroidLayouts = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETANDROIDLAYOUTS, init.Configurations);
		GetAndroidLayouts.URL = apiUtil.prepareparameterizedURL(GetAndroidLayouts.URL, new String[] {LayoutName});
		System.out.println("Get Android Layouts Service URL : "+GetAndroidLayouts.URL);
		return new RequestGenerator(GetAndroidLayouts);
	}
	
	public RequestGenerator getUserCoupons(String username, String password, String useMail)
	{
		IDPTestsHelper Obj = new IDPTestsHelper();
		Obj.getAndSetTokens(username, password);
		String serviceParam =Obj.UIDX;
		if(useMail.equals("true"))
		serviceParam=username;
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS, APINAME.DEVAPIGETUSERCOUPONS, init.Configurations);
		service.URL=apiUtil.prepareparameterizedURL(service.URL, serviceParam);
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("xid", Obj.XID);
		return new RequestGenerator(service,headers);	
	}
	
	public RequestGenerator getLgpReferral(String username, String password, String feedVersion, boolean tamperSession)
	{
		IDPTestsHelper Obj = new IDPTestsHelper();
		Obj.getAndSetTokens(username, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPILGPREFERRAL, init.Configurations);
		service.URL=apiUtil.prepareparameterizedURL(service.URL, feedVersion);
		System.out.println("URL : "+service.URL);
		HashMap<String, String> headers = new HashMap<String,String>();
		if(tamperSession) headers.put("xid","##@#");
		else headers.put("xid", Obj.XID);
		return new RequestGenerator(service,headers);	
	}
	
}
