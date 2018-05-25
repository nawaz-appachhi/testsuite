package com.myntra.apiTests.portalservices.lgpServicesOnDemand;

import java.io.IOException;
import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.lgpservices.LgpServicesHelper;
import com.myntra.apiTests.portalservices.lgpservices.LgpUserTests;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class ABTestsNew {
	
	public static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(LgpUserTests.class);
	String uidx;
	static LgpServicesHelper lgpServiceHelper=new LgpServicesHelper();
	String xid;
	
	static HashMap<String, String> headers = new HashMap<String,String>();
	static APIUtilities utilities = new APIUtilities();
	static String username;
	static String password;
	static HashMap<String,String> dataValues;
	static String state;
	
	@BeforeClass(alwaysRun=true)
	public void setUp()
	{
		/*username=System.getenv("username");
		password=System.getenv("password");*/
		String xid = null;
		if(null==username && init.Configurations.GetTestEnvironemnt().toString().toLowerCase().contains("fox7"))
		{
			username="iosapp@myntra.com";
			password="qwerty";

			/*xid="JJN024d35d85bdbd224c9a87c23f07fe7961261438937423M";
			uidx="970794db.228e.4133.933e.1574198ef08frA7cPl428x";*/
		}
		else if(null==username && init.Configurations.GetTestEnvironemnt().toString().toLowerCase().contains("production"))
		{
			username="iosapp10@myntra.com";
			password="qwerty";
			/*xid="";
			uidx="";*/
		}
	 try {
			xid = lgpServiceHelper.getXidForCredentials(username, password);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		headers.put("xid", xid);
        
	
	}
	
   @Test(priority=0)
   public void abSchemaValidationTest() 
   {
	String schema =null; 
	MyntraService feedservice = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETLGPABTESTS, init.Configurations);
	System.out.println(feedservice.URL);
	RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
	String response = requestGen.respvalidate.returnresponseasstring();
	System.out.println(response);
	dataValues = getDataForABTests(response);
	state= JsonPath.read(response, "$.state");
	try {
		 schema = new Toolbox().readFileAsString("./Data/SchemaSet/JSON/lgp/LGP-ABTests.txt");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	boolean schemaValidationResult = new CommonUtils().validateJsonSchema(response, schema);
	Assert.assertEquals(requestGen.response.getStatus(),200,"The response code is not as expected");
	Assert.assertTrue(schemaValidationResult,"The schema validations failed for AB Tests");
	Assert.assertNotNull(JsonPath.read(response, "$.data"),"The data seems to be null");
	Assert.assertNotNull(JsonPath.read(response, "$.state"),"The data seems to be null");
	Assert.assertNotNull(JsonPath.read(response, "$.gaSlots"),"The data seems to be null");
	Assert.assertNotNull(JsonPath.read(response, "$.values"),"The data seems to be null");
   }
   
   @Test(priority=1)	
   public void abTestHeaderValidationTest() 
   {
	MyntraService feedservice = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETLGPABTESTS, init.Configurations);
	System.out.println(feedservice.URL);
	RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
	String response = requestGen.respvalidate.returnresponseasstring();
	System.out.println(response);
	HashMap<String, String> dataValuesWithHeader=getDataForABTests(response);
	boolean checkFlag=compareDataValues(dataValues,dataValuesWithHeader);
	Assert.assertEquals(requestGen.response.getStatus(),200,"The response code is not as expected");
	Assert.assertTrue(checkFlag,"The values in the data key are not as same as the header");
	
   }
   
   @Test(priority=2)	
   public void emailHeaderValidationTest() 
   {
	   boolean flag =false;
	   if(init.Configurations.GetTestEnvironemnt().toString().toLowerCase().contains("fox7"))
	   {
	      headers.put("email", "iosapp5@myntra.com");
	   }
	MyntraService feedservice = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETLGPABTESTS, init.Configurations);
	System.out.println(feedservice.URL);
	RequestGenerator requestGen = new RequestGenerator(feedservice,headers);
	String response = requestGen.respvalidate.returnresponseasstring();
	System.out.println(response);
	HashMap<String,String> dataValues=getDataForABTests(response);
	if(dataValues.get("\"lgp.personalization.rollout\"").contains("enabled") && dataValues.get("\"pdp.video\"").contains("enabled"))
	{
		flag=true;
	}
	Assert.assertEquals(requestGen.response.getStatus(),200,"The response code is not as expected");
	Assert.assertTrue(flag,"The lgp personalization rollout value is not retrived as expected");
	
   }

   private boolean compareDataValues(HashMap<String, String> dataValues,
		HashMap<String, String> dataValuesWithHeader) {
	   for(String values : dataValues.keySet())
	   {
		  if(!dataValuesWithHeader.containsKey(values))
		  {
			 return false; 
		  }
	   }
	return true;
}

private HashMap<String, String> getDataForABTests(String response) {
	HashMap<String, String> dataValues = new HashMap<>();
	String[] data = JsonPath.read(response,"$.data").toString().split(",");
	for(int i =0;i<data.length;i++)
	{
		dataValues.put(data[i].toString().split(":")[0].replace("{", ""),data[i].toString().split(":")[1].replace("}",""));
	}
	return dataValues;
}

}
