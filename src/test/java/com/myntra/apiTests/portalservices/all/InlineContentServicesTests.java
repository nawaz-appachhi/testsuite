package com.myntra.apiTests.portalservices.all;

import com.myntra.apiTests.dataproviders.InlineContentDP;
import com.myntra.apiTests.portalservices.inlineContent.InlineContentHelper;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Myntra;

public class InlineContentServicesTests extends InlineContentDP {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(InlineContentServicesTests.class);
	APIUtilities apiUtil = new APIUtilities();
	InlineContentHelper icHelper=new InlineContentHelper();
//	LinkedHashSet<String> icAdminId=new LinkedHashSet<String>();
	
	@Test(groups = { "Smoke", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "createIc")
	public void createTextIcAdminTest(String author,String color,String department,String brand,String articleType,String masterCategory) {
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_INLINECONTENT, APINAME.CREATEICADMIN, init.Configurations, new String[]{author, color, department, brand, articleType, masterCategory},PayloadType.JSON,PayloadType.JSON);
		System.out.println("URL : "+service.URL);
		//System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals("Error in api",200, req.response.getStatus());
		System.out.println(jsonResponse);
		AssertJUnit.assertNotNull(req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").isEmpty());
		AssertJUnit.assertTrue("Email is not correct",req.respvalidate.GetNodeValue("data.rules.author", jsonResponse).replaceAll("\"", "").toLowerCase().contains(author));
		AssertJUnit.assertEquals(req.respvalidate.GetNodeValue("data.rules.color", jsonResponse).replaceAll("\"", ""), color);
		AssertJUnit.assertEquals(req.respvalidate.GetNodeValue("data.rules.department", jsonResponse).replaceAll("\"", ""), department);
		AssertJUnit.assertEquals(req.respvalidate.GetNodeValue("data.rules.brand", jsonResponse).replaceAll("\"", ""), brand);
		AssertJUnit.assertEquals(req.respvalidate.GetNodeValue("data.rules.articletype", jsonResponse).replaceAll("\"", ""), articleType);
		AssertJUnit.assertEquals(req.respvalidate.GetNodeValue("data.rules.mastercategory", jsonResponse).replaceAll("\"", ""), masterCategory);
	}
	
	@Test(groups = { "Smoke", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "createIcNegative")
	public void createIcAdminTestNegative(String author,String color,String department,String brand,String articleType,String masterCategory) {
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_INLINECONTENT, APINAME.CREATEICADMIN, init.Configurations, new String[]{author, color, department, brand, articleType, masterCategory},PayloadType.JSON,PayloadType.JSON);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals("Error in api",200, req.response.getStatus());
		System.out.println(jsonResponse);
		//icAdminId.add(req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", ""));
		AssertJUnit.assertTrue("Email is not correct",req.respvalidate.GetNodeValue("data.rules.author", jsonResponse).replaceAll("\"", "").toLowerCase().contains(author));
		AssertJUnit.assertEquals(req.respvalidate.GetNodeValue("data.rules.color", jsonResponse).replaceAll("\"", ""), color);
		AssertJUnit.assertEquals(req.respvalidate.GetNodeValue("data.rules.department", jsonResponse).replaceAll("\"", ""), department);
		AssertJUnit.assertEquals(req.respvalidate.GetNodeValue("data.rules.brand", jsonResponse).replaceAll("\"", ""), brand);
		AssertJUnit.assertEquals(req.respvalidate.GetNodeValue("data.rules.articletype", jsonResponse).replaceAll("\"", ""), articleType);
		AssertJUnit.assertEquals(req.respvalidate.GetNodeValue("data.rules.mastercategory", jsonResponse).replaceAll("\"", ""), masterCategory);
	}
		
	
	@Test(groups = { "Smoke", "Miniregression", "Regression", "ExhaustiveRegression"})
	public void createVideoIcAdminTest() {
		
	//TODO
		
	}
	
	@Test(groups = { "Smoke", "Miniregression", "Regression", "ExhaustiveRegression"})
	public void createImageIcAdminTest() {
	
	//TODO
		
	}
	
	@Test(groups = { "Smoke", "Miniregression", "Regression", "ExhaustiveRegression"})
	public void getICAdminTest(){
		String id=icHelper.getInlineContentId();
		MyntraService service = Myntra.getService(ServiceType.PORTAL_INLINECONTENT, APINAME.GETICADMIN, init.Configurations,PayloadType.JSON,PayloadType.JSON);
		service.URL=apiUtil.prepareparameterizedURL(service.URL, id);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals("Error in api",200, req.response.getStatus());
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", ""),id);
		
	}
	
	@Test(groups = { "Smoke", "Miniregression", "Regression", "ExhaustiveRegression"},dataProvider="getIcAdminNegative")
	public void getICAdminTestNegative(String id){
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_INLINECONTENT, APINAME.GETICADMIN, init.Configurations,PayloadType.JSON,PayloadType.JSON);
		service.URL=apiUtil.prepareparameterizedURL(service.URL, id);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals("Error in api",200, req.response.getStatus());
		System.out.println(jsonResponse);
		//AssertJUnit.assertTrue(req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(id));
		AssertJUnit.assertEquals(req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", ""),id);
	}
	
	@Test(groups = { "Smoke", "Miniregression", "Regression", "ExhaustiveRegression"})
	public void deleteIcAdminTest(){
		String id=icHelper.getInlineContentId();
		MyntraService service=Myntra.getService(ServiceType.PORTAL_INLINECONTENT,APINAME.DELETEICADMIN,init.Configurations,new String[]{},PayloadType.JSON,PayloadType.JSON);
		service.URL=apiUtil.prepareparameterizedURL(service.URL, id);
	    System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		//System.out.println(req.respvalidate.GetNodeValue("message", jsonResponse).replaceAll("\"", ""));
		AssertJUnit.assertEquals("Error in api",200, req.response.getStatus());
		System.out.println(jsonResponse);
	}
	
	@Test(groups = { "Smoke", "Miniregression", "Regression", "ExhaustiveRegression"},dataProvider="deleteIcAdminNegative")
	public void deleteIcAdminTestNegative(String id){
		MyntraService service=Myntra.getService(ServiceType.PORTAL_INLINECONTENT,APINAME.DELETEICADMIN,init.Configurations,new String[]{},PayloadType.JSON,PayloadType.JSON);
		service.URL=apiUtil.prepareparameterizedURL(service.URL, id);
	    System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		//System.out.println(req.respvalidate.GetNodeValue("message", jsonResponse).replaceAll("\"", ""));
		AssertJUnit.assertEquals("Error in api",200, req.response.getStatus());
		System.out.println(jsonResponse);
	}
	
	@Test(groups = { "Smoke", "Miniregression", "Regression", "ExhaustiveRegression"},dataProvider="clientSearchIc")
	public void clientSearchIcTest(String id){
		//String id=icHelper.getInlineContentId();
		//System.out.println(id);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_INLINECONTENT, APINAME.CLIENTSEARCH, init.Configurations, new String[]{id},PayloadType.JSON,PayloadType.JSON);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals("Error in api",200, req.response.getStatus());
		
		AssertJUnit.assertTrue(req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(id));
	}
	
	@Test(groups = { "Smoke", "Miniregression", "Regression", "ExhaustiveRegression"},dataProvider="clientSearchIcNegative")
	public void clientSearchIcTestNegative(String id){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_INLINECONTENT, APINAME.CLIENTSEARCH, init.Configurations, new String[]{id},PayloadType.JSON,PayloadType.JSON);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals("Error in api",200, req.response.getStatus());
		System.out.println(jsonResponse);
		AssertJUnit.assertTrue(req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(id));
	}
	
	@Test(groups = { "Smoke", "Miniregression", "Regression", "ExhaustiveRegression"},dataProvider="updateIcAdmin")
	public void updateIcAdminTest(String id,String type){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_INLINECONTENT, APINAME.UPDATEICADMIN, init.Configurations, new String[]{type},PayloadType.JSON,PayloadType.JSON);
		service.URL=apiUtil.prepareparameterizedURL(service.URL, id);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals("Error in api",200, req.response.getStatus());
		System.out.println(jsonResponse);
		
		AssertJUnit.assertTrue(req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(id));
		AssertJUnit.assertTrue(req.respvalidate.GetNodeValue("data.type", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(type));
	}
	
	@Test(groups = { "Smoke", "Miniregression", "Regression", "ExhaustiveRegression"})
	public void searchIcAdminTest() {
		String id=icHelper.getInlineContentId();
		MyntraService service = Myntra.getService(ServiceType.PORTAL_INLINECONTENT, APINAME.SEARCHICADMIN, init.Configurations, new String[]{id},PayloadType.JSON,PayloadType.JSON);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals("Error in api",200, req.response.getStatus());
		System.out.println(jsonResponse);
		AssertJUnit.assertTrue(req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(id));
	}
	
	@Test(groups = { "Smoke", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "searchIcAdminNegative")
	public void searchIcAdminTestNegative(String id) {
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_INLINECONTENT, APINAME.SEARCHICADMIN, init.Configurations, new String[]{id},PayloadType.JSON,PayloadType.JSON);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals("Error in api",200, req.response.getStatus());
		System.out.println(jsonResponse);
		AssertJUnit.assertTrue(req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(id));
	}
}
