package com.myntra.apiTests.testdevelopmentrnd;

import com.myntra.apiTests.common.Myntra;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class revForm {
	
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(revForm.class);
	
	@Test
		
	public void formDataImpl ()
	{
		String key = "tdRange";
		String value = "{\"startDate\": 976473000000,\"endDate\": 1468125850000,\"minTD\": \"10\",\"maxTD\": \"60\",\"criterias\": {\"brands\": [\"Silly People\",\"Rodamo\"],\"styleIds\": [],\"articleTypes\": [],\"categories\": [],\"categoryManagers\": [],\"subCategories\": [],\"seasonCodes\": [],\"genders\": [],\"merchandiseTypes\": [\"MMP\"],\"mrpRanges\": [{\"minMrp\": \"0\",\"maxMrp\":\"100000\"}],\"name\": \"abc2\",\"description\": \"d3\"}}";
		StringBuffer sb = new StringBuffer();
		sb.append(key);
		sb.append("&");
		sb.append(value);
		System.out.println(sb);
		String cust = sb.toString();
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FORMDATA,APINAME.FORMDATA,init.Configurations,PayloadType.FORMDATA, cust);
		RequestGenerator req = new RequestGenerator (service);
		System.out.println(req.respvalidate.returnresponseasstring());
	}
}
