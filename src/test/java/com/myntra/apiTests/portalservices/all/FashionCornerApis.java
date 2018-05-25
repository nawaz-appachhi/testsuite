package com.myntra.apiTests.portalservices.all;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.myntra.apiTests.portalservices.fashionCorner.FashionCornerTestsHelper;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.FashionCornerApisDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Myntra;

/**
 * @author manu.c,Sabyasachi
 * Moved below test cases in best practises:
 * 1. FasionDestination_updateExistingUserNegative 
 */


public class FashionCornerApis extends FashionCornerApisDP{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DevApis.class);
	APIUtilities apiUtil = new APIUtilities();
	//CheckoutTestsHelper checkoutTestHelper=new CheckoutTestsHelper();
	FashionCornerTestsHelper fashionCornerTestsHelper=new FashionCornerTestsHelper();
	static int quesId = 0;
	static int outfitId = 0;
	int idofPreviousCall = 0;
	int idofComment = 0;
	private int toCheckCommentId;

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "createUserPositiveDP")
	public void FasionDestination_createUserPositive(String id, String name, String email, String role, String from, String bio)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCCREATEUSER, init.Configurations, new String[]{id, name, email, role, from, bio});
		System.out.println(id+" : "+name+" :"+email+" :"+role+" :"+from+" :"+bio);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		System.out.println(jsonResponse);
		AssertJUnit.assertTrue("Value of meta.code should be 200 in Response", req.respvalidate.GetNodeValue("meta.code", jsonResponse).equalsIgnoreCase("200"));
		AssertJUnit.assertTrue("Validation of response keys", req.respvalidate.DoesNodesExists(responseCreateUser()));
		AssertJUnit.assertTrue("Validation of id", req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(id));
		AssertJUnit.assertTrue("Validation of name", req.respvalidate.GetNodeValue("data.name", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(name));
		AssertJUnit.assertTrue("Validation of email", req.respvalidate.GetNodeValue("data.email", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(email));
		AssertJUnit.assertTrue("Validation of role", req.respvalidate.GetNodeValue("data.role", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(role));
		AssertJUnit.assertTrue("Validation of from", req.respvalidate.GetNodeValue("data.from", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(from));
	}

	@Test(groups = {"ExhaustiveRegression"}, dataProvider = "createUserNegativeDP")
	public void FasionDestination_createUserNegative(String id, String name, String email, String role, String from, String bio)
	{
		System.out.println(id+" : "+name+" :"+email+" :"+role+" :"+from+" :"+bio);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCCREATEUSER, init.Configurations, new String[]{id, name, email, role, from, bio});
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		//Not valid any more
		//		AssertJUnit.assertTrue("Value of meta.code should be 200 in Response", req.respvalidate.GetNodeValue("meta.code", jsonResponse).equalsIgnoreCase("200"));
		//		AssertJUnit.assertTrue("Validation of response keys", req.respvalidate.DoesNodesExists(responseCreateUser()));
		//		AssertJUnit.assertTrue("Validation of id", req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(id));
		//		AssertJUnit.assertTrue("Validation of name", req.respvalidate.GetNodeValue("data.name", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(name));
		//		AssertJUnit.assertTrue("Validation of email", req.respvalidate.GetNodeValue("data.email", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(email));
		//		AssertJUnit.assertTrue("Validation of role", req.respvalidate.GetNodeValue("data.role", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(role));
		//		AssertJUnit.assertTrue("Validation of from", req.respvalidate.GetNodeValue("data.from", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(from));
	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "getUserPositiveDP")
	public void FasionDestination_getUserPositive(String id, String isNewUser)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCGETUSER, init.Configurations, PayloadType.JSON, PayloadType.JSON);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, id);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertTrue("Value of meta.code should be 200 in Response", req.respvalidate.GetNodeValue("meta.code", jsonResponse).equalsIgnoreCase("200"));
		AssertJUnit.assertTrue("Validation of id", req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(id));
	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "getUserNegativeDP")
	public void FasionDestination_getUserNegative(String id)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCGETUSER, init.Configurations, PayloadType.JSON, PayloadType.JSON);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, id);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		//AssertJUnit.assertTrue("Validation of data tag", req.respvalidate.GetNodeValue("data", jsonResponse).equalsIgnoreCase("null"));
	}


	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "updateExistingPositiveDP")
	public void FasionDestination_updateExistingUserPositive(String id, String name, String email, String role, String from, String bio)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCUPDATEEXISTINGUSER, init.Configurations, new String[]{name, email, role, from, bio});
		service.URL = apiUtil.prepareparameterizedURL(service.URL, id);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of response keys", req.respvalidate.DoesNodesExists(responseUpdateUser()));
		AssertJUnit.assertTrue("Validation of id", req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(id));
		AssertJUnit.assertTrue("Validation of name", req.respvalidate.GetNodeValue("data.name", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(name));
		AssertJUnit.assertTrue("Validation of email", req.respvalidate.GetNodeValue("data.email", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(email));
		AssertJUnit.assertTrue("Validation of role", req.respvalidate.GetNodeValue("data.role", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(role));
		AssertJUnit.assertTrue("Validation of from", req.respvalidate.GetNodeValue("data.from", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(from));
	}


	@Test(groups = { /* "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression" */ }, dataProvider = "updateExistingNegativeDP")
	public void FasionDestination_updateExistingUserNegative(String id, String name, String email, String role, String from, String bio)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCUPDATEEXISTINGUSER, init.Configurations, new String[]{name, email, role, from, bio});
		service.URL = apiUtil.prepareparameterizedURL(service.URL, id);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		//		AssertJUnit.assertTrue("Validation of response keys", req.respvalidate.DoesNodesExists(responseUpdateUser()));
		//		AssertJUnit.assertTrue("Validation of id", req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(id));
		//		AssertJUnit.assertTrue("Validation of name", req.respvalidate.GetNodeValue("data.name", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(name));
		//		AssertJUnit.assertTrue("Validation of email", req.respvalidate.GetNodeValue("data.email", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(email));
		//		AssertJUnit.assertTrue("Validation of role", req.respvalidate.GetNodeValue("data.role", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(role));
		//		AssertJUnit.assertTrue("Validation of from", req.respvalidate.GetNodeValue("data.from", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(from));	
	}




	@Test(groups = {"ExhaustiveRegression"}, dataProvider = "updateNonExistingPositiveDP")
	public void FasionDestination_updateNonExistingUserPositive(String id, String name, String email, String role, String from, String bio)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCUPDATEEXISTINGUSER, init.Configurations, new String[]{name, email, role, from, bio});
		service.URL = apiUtil.prepareparameterizedURL(service.URL, id);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("500"));
		AssertJUnit.assertTrue("Validation of meta.error", req.respvalidate.GetNodeValue("meta.error", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("Id not found"));
		AssertJUnit.assertTrue("Validation of data", req.respvalidate.GetNodeValue("data", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("{}"));
	}


	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "deleteUserPositiveDP")
	public void FasionDestination_deleteExistingUserPositive(String id, String name, String email, String role, String from, String bio)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCCREATEUSER, init.Configurations, new String[]{id, name, email, role, from, bio});
		System.out.println(id+" : "+name+" :"+email+" :"+role+" :"+from+" :"+bio);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCDELETEEXISTINGUSER, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, id);
		System.out.println("URL : "+service.URL);
		req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "deleteUserNegitiveDP")
	public void FasionDestination_deleteExistingUserNegetive(String id)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCDELETEEXISTINGUSER, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, id);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	}



	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "addQuestionToUserPositiveDP")
	//	public void FasionDestination_addQuestionToUserPositive(String title, String desc, String userId, String approved)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCADDQUESTIONTOUSER, init.Configurations, new String[]{title, desc, userId, approved});
	//		RequestGenerator req = new RequestGenerator(service);
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//		AssertJUnit.assertTrue("Validation of response keys", req.respvalidate.DoesNodesExists(addQuestionToUser()));
	//		AssertJUnit.assertTrue("Validation of title", req.respvalidate.GetNodeValue("data.title", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(title));
	//		AssertJUnit.assertTrue("Validation of userId", req.respvalidate.GetNodeValue("data.UserId", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(userId));
	//		AssertJUnit.assertTrue("Validation of approved", req.respvalidate.GetNodeValue("data.approved", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(approved));
	//		AssertJUnit.assertTrue("Validation of description", req.respvalidate.GetNodeValue("data.description", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(desc));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "addQuestionToUserNegativeDP")
	//	public void FasionDestination_addQuestionToUserNegative(String title, String desc, String userId, String approved)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCADDQUESTIONTOUSER, init.Configurations, new String[]{title, desc, userId, approved});
	//		RequestGenerator req = new RequestGenerator(service);
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(service.Payload);
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//		AssertJUnit.assertTrue("Validation of response keys", req.respvalidate.DoesNodesExists(addQuestionToUser()));
	//		AssertJUnit.assertTrue("Validation of title", req.respvalidate.GetNodeValue("data.title", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(title));
	//		AssertJUnit.assertTrue("Validation of userId", req.respvalidate.GetNodeValue("data.UserId", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(userId));
	//		AssertJUnit.assertTrue("Validation of approved", req.respvalidate.GetNodeValue("data.approved", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(approved));
	//		AssertJUnit.assertTrue("Validation of description", req.respvalidate.GetNodeValue("data.description", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(desc));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "addTagToUserPositiveDP")
	//	public void FasionDestination_addTagToUserPositive(String tag, String id)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCADDTAGTOUSER, init.Configurations, new String[]{tag, id});
	//		RequestGenerator req = new RequestGenerator(service);
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//		AssertJUnit.assertTrue("Validation of data.value", req.respvalidate.GetNodeValue("data.value", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(tag));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "addTagToUserNegativeDP")
	//	public void FasionDestination_addTagToUserNegative(String tag, String id)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCADDTAGTOUSER, init.Configurations, new String[]{tag, id});
	//		RequestGenerator req = new RequestGenerator(service);
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//		AssertJUnit.assertTrue("Validation of data.value", req.respvalidate.GetNodeValue("data.value", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(tag));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "addAnswerdPositiveDP")
	//	public void FasionDestination_addAnswerUserPositive(String title, String desc, String userId, String app, String quesId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCADDANSWER, init.Configurations, new String[]{title, desc, userId,app,quesId});
	//		RequestGenerator req = new RequestGenerator(service);
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//		AssertJUnit.assertTrue("Validation of response keys", req.respvalidate.DoesNodesExists(addAnswerToUser()));
	//		AssertJUnit.assertTrue("Validation of title", req.respvalidate.GetNodeValue("data.title", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(title));
	//		AssertJUnit.assertTrue("Validation of userId", req.respvalidate.GetNodeValue("data.UserId", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(userId));
	//		AssertJUnit.assertTrue("Validation of approved", req.respvalidate.GetNodeValue("data.approved", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(app));
	//		AssertJUnit.assertTrue("Validation of description", req.respvalidate.GetNodeValue("data.description", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(desc));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "addAnswerdNegativeDP")
	//	public void FasionDestination_addAnswerUserNegative(String title, String desc, String userId, String app, String quesId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCADDANSWER, init.Configurations, new String[]{title, desc, userId,app,quesId});
	//		RequestGenerator req = new RequestGenerator(service);
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//		AssertJUnit.assertTrue("Validation of response keys", req.respvalidate.DoesNodesExists(addAnswerToUser()));
	//		AssertJUnit.assertTrue("Validation of title", req.respvalidate.GetNodeValue("data.title", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(title));
	//		AssertJUnit.assertTrue("Validation of userId", req.respvalidate.GetNodeValue("data.UserId", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(userId));
	//		AssertJUnit.assertTrue("Validation of approved", req.respvalidate.GetNodeValue("data.approved", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(app));
	//		AssertJUnit.assertTrue("Validation of description", req.respvalidate.GetNodeValue("data.description", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(desc));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "updateQuestionPositiveDP")
	//	public void FasionDestination_updateQuestionPositive(String title, String desc, String quesId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCUPDATEQUESTION, init.Configurations, new String[]{title, desc}, new String[]{quesId});
	//		RequestGenerator req = new RequestGenerator(service);
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//		AssertJUnit.assertTrue("Validation of response keys", req.respvalidate.DoesNodesExists(addQuestionToUser()));
	//		AssertJUnit.assertTrue("Validation of title", req.respvalidate.GetNodeValue("data.title", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(title));
	//		AssertJUnit.assertTrue("Validation of description", req.respvalidate.GetNodeValue("data.description", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(desc));
	//		AssertJUnit.assertTrue("Validation of id", req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(quesId));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "updateQuestionNegativeeDP")
	//	public void FasionDestination_updateQuestionNegative(String title, String desc, String quesId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCUPDATEQUESTION, init.Configurations, new String[]{title, desc}, new String[]{quesId});
	//		RequestGenerator req = new RequestGenerator(service);
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//		AssertJUnit.assertTrue("Validation of response keys", req.respvalidate.DoesNodesExists(addQuestionToUser()));
	//		AssertJUnit.assertTrue("Validation of title", req.respvalidate.GetNodeValue("data.title", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(title));
	//		AssertJUnit.assertTrue("Validation of description", req.respvalidate.GetNodeValue("data.description", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(desc));
	//		AssertJUnit.assertTrue("Validation of id", req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(quesId));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "getAnswerSummaryPositiveDP")
	//	public void FasionDestination_getAnswerSummaryPositive(String userId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCGETANSWERSUMMARY, init.Configurations);
	//		RequestGenerator req = new RequestGenerator(service);
	//		service.URL = apiUtil.prepareparameterizedURL(service.URL, userId);
	//		System.out.println("URL : "+service.URL);
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "getAnswerSummaryNegativeDP")
	//	public void FasionDestination_getAnswerSummaryNegative(String userId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCGETANSWERSUMMARY, init.Configurations);
	//		RequestGenerator req = new RequestGenerator(service);
	//		service.URL = apiUtil.prepareparameterizedURL(service.URL, userId);
	//		System.out.println("URL : "+service.URL);
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "getQuestionSummaryPositiveDP")
	//	public void FasionDestination_getQuestionSummaryPositive(String userId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCGETQUESTIONSUMMARY, init.Configurations);
	//		RequestGenerator req = new RequestGenerator(service);
	//		service.URL = apiUtil.prepareparameterizedURL(service.URL, userId);
	//		System.out.println("URL : "+service.URL);
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "getQuestionSummaryNegativeDP")
	//	public void FasionDestination_getQuestionSummaryNegative(String userId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCGETQUESTIONSUMMARY, init.Configurations);
	//		RequestGenerator req = new RequestGenerator(service);
	//		service.URL = apiUtil.prepareparameterizedURL(service.URL, userId);
	//		System.out.println("URL : "+service.URL);
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "updateAnswerPositiveDP")
	//	public void FasionDestination_updateAnswerPositive(String title, String desc, String ansId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCUPDATEANSWER, init.Configurations, new String[]{title, desc}, new String[]{ansId});
	//		RequestGenerator req = new RequestGenerator(service);
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//		AssertJUnit.assertTrue("Validation of response keys", req.respvalidate.DoesNodesExists(addQuestionToUser()));
	//		AssertJUnit.assertTrue("Validation of title", req.respvalidate.GetNodeValue("data.title", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(title));
	//		AssertJUnit.assertTrue("Validation of description", req.respvalidate.GetNodeValue("data.description", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(desc));
	//		AssertJUnit.assertTrue("Validation of id", req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(ansId));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "updateAnswerNegativeDP")
	//	public void FasionDestination_updateAnswerNegative(String title, String desc, String ansId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCUPDATEANSWER, init.Configurations, new String[]{title, desc}, new String[]{ansId});
	//		RequestGenerator req = new RequestGenerator(service);
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//		AssertJUnit.assertTrue("Validation of response keys", req.respvalidate.DoesNodesExists(addQuestionToUser()));
	//		AssertJUnit.assertTrue("Validation of title", req.respvalidate.GetNodeValue("data.title", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(title));
	//		AssertJUnit.assertTrue("Validation of description", req.respvalidate.GetNodeValue("data.description", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(desc));
	//		AssertJUnit.assertTrue("Validation of id", req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(ansId));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "addTagToUserViaUsereDP")
	//	public void FasionDestination_addTagToUserViaUserPositive(String tagValue, String userId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCADDTAGTOUSERVIAUSER, init.Configurations, new String[]{tagValue}, new String[]{userId});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "addTagToUserViaUsereNegativeDP")
	//	public void FasionDestination_addTagToUserViaUserNegative(String tagValue, String userId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCADDTAGTOUSERVIAUSER, init.Configurations, new String[]{tagValue}, new String[]{userId});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcQuestionAddTagToQuestionPositiveDP")
	//	public void FasionDestination_fcQuestionAddTagToQuestionPositive(String tagValue, String quesId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCQUESTIONADDTAGTOQUESTION, init.Configurations, new String[]{tagValue, quesId});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcQuestionAddTagToQuestionNegativeDP")
	//	public void FasionDestination_fcQuestionAddTagToQuestionNegative(String tagValue, String quesId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCQUESTIONADDTAGTOQUESTION, init.Configurations, new String[]{tagValue, quesId});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	
	//fc-outfit
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcoutfitCreateStylePositiveDP")
	public void FasionDestination_fcoutfitCreateStylePositive(String styleId, String desc, String isExisted)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCOUTFITCREATESTYLE, init.Configurations, new String[]{styleId, desc});
		System.out.println("URL : "+service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);		
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		if(isExisted.equalsIgnoreCase("false")){
			AssertJUnit.assertEquals(200, req.response.getStatus());
			AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
			AssertJUnit.assertTrue("Validation of id", req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(styleId));
			AssertJUnit.assertTrue("Validation of description", req.respvalidate.GetNodeValue("data.description", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(desc));
		}else{
			AssertJUnit.assertEquals(500, req.response.getStatus());
			AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("500"));
			AssertJUnit.assertTrue("Validation of error.code", req.respvalidate.GetNodeValue("meta.error.parent.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("ER_DUP_ENTRY"));
			AssertJUnit.assertTrue("Validation of error.errno", req.respvalidate.GetNodeValue("meta.error.parent.errno", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("1062"));
		}
	}

	@Test(groups = { "ExhaustiveRegression"}, dataProvider = "fcoutfitCreateStyleNegativeDP")
	public void FasionDestination_fcoutfitCreateStyleNegative(String styleId, String desc)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCOUTFITCREATESTYLE, init.Configurations, new String[]{styleId, desc});
		System.out.println("URL : "+service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);		
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("500"));
		AssertJUnit.assertTrue("Validation of error.code", req.respvalidate.GetNodeValue("meta.error.parent.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("ER_DUP_ENTRY"));
		AssertJUnit.assertTrue("Validation of error.errno", req.respvalidate.GetNodeValue("meta.error.parent.errno", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("1062"));

	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcoutfitGetStyleDataPositiveDP")
	public void FasionDestination_fcoutfitGetStyleDataPositive(String styleId)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCOUTFITGETSTYLEDATA, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, styleId);
		System.out.println("URL : "+service.URL);
		//System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);		
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
		AssertJUnit.assertTrue("Validation of style.id", req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(styleId));

	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcoutfitGetStyleDataNegativeDP")
	public void FasionDestination_fcoutfitGetStyleDataNegative(String styleId)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCOUTFITGETSTYLEDATA, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, styleId);
		System.out.println("URL : "+service.URL);
		//System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);		
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	}

	@Test(groups = {"ExhaustiveRegression"}, dataProvider = "fcoutfitCreateOutfitNegitiveDP")
	public void FasionDestination_fcoutfitCreateOutfitNegitive(String name,String likeCount,String layout,String UserId,String occasions1,String occasions2,String gender,String description,String stylesid1,String styleDescription,String stylePosition)
	{
		//Create outfit
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCOUTFITCREATEOUTFIT, init.Configurations, new String[]{name,likeCount,layout,UserId,occasions1,occasions2,gender,description,stylesid1,styleDescription,stylePosition});
		System.out.println("URL : "+service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);		
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	}


	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcoutfitCreateOutfitPositiveDP")
	public void FasionDestination_fcoutfitCreateOutfitAndGetOutFitPositive(String name,String likeCount,String layout,String UserId,String occasions1,String occasions2,String gender,String description,String stylesid1,String styleDescription,String stylePosition)
	{

		//Create outfit
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCOUTFITCREATEOUTFIT, init.Configurations, new String[]{name,likeCount,layout,UserId,occasions1,occasions2,gender,description,stylesid1,styleDescription,stylePosition});
		System.out.println("URL : "+service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);		
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
		AssertJUnit.assertTrue("Validation of data.id", req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").matches("\\d*"));
		//AssertJUnit.assertTrue("Validation of data.likeCount", req.respvalidate.GetNodeValue("data.likeCount", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(likeCount));
		AssertJUnit.assertTrue("Validation of data.description", req.respvalidate.GetNodeValue("data.description", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(description));;
		AssertJUnit.assertTrue("Validation of data.layout", req.respvalidate.GetNodeValue("data.layout", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(layout));;
		AssertJUnit.assertTrue("Validation of data.UserId", req.respvalidate.GetNodeValue("data.UserId", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(UserId));;
		AssertJUnit.assertTrue("Validation of data.name", req.respvalidate.GetNodeValue("data.name", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(name));;
		if(!req.respvalidate.GetNodeValue("data.occasions", jsonResponse).equalsIgnoreCase("[ ]"))
		{
			AssertJUnit.assertTrue("Validation of data.occasions", req.respvalidate.GetNodeValue("data.occasions", jsonResponse).replaceAll("\"", "").toLowerCase().contains(occasions1));
			AssertJUnit.assertTrue("Validation of data.occasions", req.respvalidate.GetNodeValue("data.occasions", jsonResponse).replaceAll("\"", "").toLowerCase().contains(occasions2));
		}
		AssertJUnit.assertTrue("Validation of data.styles.id", req.respvalidate.GetNodeValue("data.styles.id", jsonResponse).replaceAll("\"", "").matches("\\d*"));
		AssertJUnit.assertTrue("Validation of data.styles.styleid", req.respvalidate.GetNodeValue("data.styles.styleid", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(stylesid1));
		AssertJUnit.assertTrue("Validation of data.styles.position", req.respvalidate.GetNodeValue("data.styles.position", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(stylePosition));
		AssertJUnit.assertTrue("Validation of data.styles.description", req.respvalidate.GetNodeValue("data.styles.description", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(styleDescription));
		AssertJUnit.assertTrue("Validation of data.styles.OutfitId", req.respvalidate.GetNodeValue("data.styles.OutfitId", jsonResponse).replaceAll("\"", "").contains(req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "")));
		String outfitId=req.respvalidate.GetNodeValue("data.styles.OutfitId", jsonResponse).replaceAll("\"", "");
		String stylesIDGenerated=req.respvalidate.GetNodeValue("data.styles.id", jsonResponse).replaceAll("\"", "");

		//Get outfit
		service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCOUTFITGETOUTFIT, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, outfitId);
		System.out.println("URL : "+service.URL);
		req = new RequestGenerator(service);	
		jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
		AssertJUnit.assertTrue("Validation of data.id", req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").matches("\\d*"));
		//AssertJUnit.assertTrue("Validation of data.likeCount", req.respvalidate.GetNodeValue("data.likeCount", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(likeCount));
		AssertJUnit.assertTrue("Validation of data.description", req.respvalidate.GetNodeValue("data.description", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(description));
		AssertJUnit.assertTrue("Validation of data.layout", req.respvalidate.GetNodeValue("data.layout", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(layout));
		AssertJUnit.assertTrue("Validation of data.UserId", req.respvalidate.GetNodeValue("data.UserId", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(UserId));
		AssertJUnit.assertTrue("Validation of data.name", req.respvalidate.GetNodeValue("data.name", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(name));
		//Need to discuss with anunay because occasion are not coming in sequence
		//AssertJUnit.assertTrue("Validation of data.Outfitoccasions.value[0]", JsonPath.read(req.respvalidate.returnresponseasstring(), "$.data..Outfitoccasions.value[0]").toString().toLowerCase().contains(occasions1));
		//AssertJUnit.assertTrue("Validation of data.Outfitoccasions.OutfitoccasionsOutfit.OutfitoccasionValue[0]",JsonPath.read(req.respvalidate.returnresponseasstring(), "$.data..Outfitoccasions.OutfitoccasionsOutfit.OutfitoccasionValue[0]").toString().toLowerCase().contains(occasions1));
		AssertJUnit.assertTrue("Validation of data.Outfitoccasions.OutfitoccasionsOutfit.OutfitId[0]",JsonPath.read(req.respvalidate.returnresponseasstring(), "$.data..Outfitoccasions.OutfitoccasionsOutfit.OutfitId[0]").toString().toLowerCase().equalsIgnoreCase(outfitId));		
		//AssertJUnit.assertTrue("Validation of data.Outfitoccasions.value[1]", JsonPath.read(req.respvalidate.returnresponseasstring(), "$.data..Outfitoccasions.value[1]").toString().toLowerCase().contains(occasions2));
		//AssertJUnit.assertTrue("Validation of data.Outfitoccasions.OutfitoccasionsOutfit.OutfitoccasionValue[1]",JsonPath.read(req.respvalidate.returnresponseasstring(), "$.data..Outfitoccasions.OutfitoccasionsOutfit.OutfitoccasionValue[1]").toString().toLowerCase().contains(occasions2));
		AssertJUnit.assertTrue("Validation of data.Outfitoccasions.OutfitoccasionsOutfit.OutfitId[1]",JsonPath.read(req.respvalidate.returnresponseasstring(), "$.data..Outfitoccasions.OutfitoccasionsOutfit.OutfitId[1]").toString().toLowerCase().equalsIgnoreCase(outfitId));
		AssertJUnit.assertTrue("Validation of data.Styles.id", req.respvalidate.GetNodeValue("data.Styles.id", jsonResponse).replaceAll("\"", "").toLowerCase().equalsIgnoreCase(stylesIDGenerated));
		AssertJUnit.assertTrue("Validation of data.Styles.styleid", req.respvalidate.GetNodeValue("data.Styles.styleid", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(stylesid1));
		AssertJUnit.assertTrue("Validation of data.Styles.position", req.respvalidate.GetNodeValue("data.Styles.position", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(stylePosition));
		AssertJUnit.assertTrue("Validation of data.Styles.description", req.respvalidate.GetNodeValue("data.Styles.description", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(styleDescription));
		AssertJUnit.assertTrue("Validation of data.Styles.OutfitId", req.respvalidate.GetNodeValue("data.Styles.OutfitId", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(outfitId));

	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcoutfitCreateAndUpdateOutfitPositiveDP")
	public void FasionDestination_fcoutfitCreateOutfitAndUpdateOutFitPositive(String name,String likeCount,String layout,String UserId,String occasions1,String occasions2,String gender,String description,String stylesid1,String styleDescription,String stylePosition,String outfitId,String generatedStyleID)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCOUTFITUPDATEOUTFIT, init.Configurations, new String[]{name,likeCount,layout,UserId,occasions1,occasions2,gender,description,stylesid1,styleDescription,stylePosition,generatedStyleID});
		service.URL = apiUtil.prepareparameterizedURL(service.URL, outfitId);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);	
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
		AssertJUnit.assertTrue("Validation of data.id", req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(outfitId));
		//AssertJUnit.assertTrue("Validation of data.likeCount", req.respvalidate.GetNodeValue("data.likeCount", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(likeCount));
		AssertJUnit.assertTrue("Validation of data.description", req.respvalidate.GetNodeValue("data.description", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(description));;
		AssertJUnit.assertTrue("Validation of data.layout", req.respvalidate.GetNodeValue("data.layout", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(layout));;
		AssertJUnit.assertTrue("Validation of data.UserId", req.respvalidate.GetNodeValue("data.UserId", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(UserId));;
		AssertJUnit.assertTrue("Validation of data.name", req.respvalidate.GetNodeValue("data.name", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(name));;
		if(!req.respvalidate.GetNodeValue("data.occasions", jsonResponse).equalsIgnoreCase("[ ]"))
		{
			AssertJUnit.assertTrue("Validation of data.occasions", req.respvalidate.GetNodeValue("data.occasions", jsonResponse).replaceAll("\"", "").toLowerCase().contains(occasions1));
			AssertJUnit.assertTrue("Validation of data.occasions", req.respvalidate.GetNodeValue("data.occasions", jsonResponse).replaceAll("\"", "").toLowerCase().contains(occasions2));
		}
		AssertJUnit.assertTrue("Validation of data.styles.id", req.respvalidate.GetNodeValue("data.styles.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(generatedStyleID));
		AssertJUnit.assertTrue("Validation of data.styles.styleid", req.respvalidate.GetNodeValue("data.styles.styleid", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(stylesid1));
		AssertJUnit.assertTrue("Validation of data.styles.position", req.respvalidate.GetNodeValue("data.styles.position", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(stylePosition));
		AssertJUnit.assertTrue("Validation of data.styles.description", req.respvalidate.GetNodeValue("data.styles.description", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(styleDescription));
		AssertJUnit.assertTrue("Validation of data.styles.OutfitId", req.respvalidate.GetNodeValue("data.styles.OutfitId", jsonResponse).replaceAll("\"", "").contains(req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "")));
	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcoutfitCreateOutfitAndReturnOutfitIdPositiveDP")
	public void FasionDestination_fcoutfitCreateOutfitAndDeleteOutFitPositive(String outfitID)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCOUTFITDELETEOUTFIT, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, outfitID);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);	
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcoutfitNegitiveDP")
	public void FasionDestination_fcoutfitCreateOutfitAndDeleteOutFitNegitive(String outfitID)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCOUTFITDELETEOUTFIT, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, outfitID);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);	
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	}



	@Test(groups = {"ExhaustiveRegression"}, dataProvider = "fcoutfitCreateOutfitNegativeDP")
	public void FasionDestination_fcoutfitCreateOutfitNegative(String userId)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCOUTFITCREATEOUTFIT, init.Configurations, new String[]{userId});
		System.out.println("URL : "+service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);		
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		if(outfitId == 0){
			idofPreviousCall = Integer.parseInt(req.respvalidate.GetNodeValue("data.id", jsonResponse));
		}
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
		AssertJUnit.assertTrue("Validation of data.id", Integer.parseInt(req.respvalidate.GetNodeValue("data.id", jsonResponse))==(idofPreviousCall+outfitId));
		AssertJUnit.assertTrue("Validation of data.UserId", req.respvalidate.GetNodeValue("data.UserId", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(userId));
		++outfitId;
	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcoutfitSearchPositiveDP")
	public void FasionDestination_fcoutfitSearchOutfitPositive(String outfits)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATIONMYNTAPI, APINAME.FCOUTFITSEARCHOUTFIT, init.Configurations, new String[]{outfits});
		System.out.println("URL : "+service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);		
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of data.row.id", req.respvalidate.GetNodeValue("data.rows.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(outfits));	
	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcoutfitSearchNegitiveDP")
	public void FasionDestination_fcoutfitSearchOutfitNegitive(String outfits)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATIONMYNTAPI, APINAME.FCOUTFITSEARCHOUTFIT, init.Configurations, new String[]{outfits});
		System.out.println("URL : "+service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);		
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
		AssertJUnit.assertTrue("Validation of data.count", req.respvalidate.GetNodeValue("data.count", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("0"));

	}
	
	/*
	 * TestName:FashionDestination_LikeComment
	 */
	@Test(groups = { "Miniregression", "Regression", "ExhaustiveRegression"},dataProvider="fcCornerOutfitLikeUnlikeComment")
	public void FashionDestination_LikeComment(String outfitId){
		//System.out.println(outfitId);	
		//checkoutTestHelper.getAndSetTokens("sabyasachi135@gmail.com", "Sab2189@in");
		//String[] xId=checkoutTestHelper.idp_GetTokens("newcart-regression@myntra.com", "testing!123");
		
		HashMap<String,String> tokens=new HashMap<String, String>();
		tokens=fashionCornerTestsHelper.getXIDandSXidHeader("newcart-regression@myntra.com", "testing!123");
		//tokens.put("xid", xId[0]);
		//tokens.put("X-CSRF-Token", xId[1]);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATIONMYNTAPI, APINAME.FCLIKEOUTFITCOMMENT, init.Configurations, PayloadType.JSON,new String[]{outfitId},PayloadType.JSON);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, outfitId);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service,tokens);		
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
		AssertJUnit.assertTrue("Validation of data.count", req.respvalidate.GetNodeValue("data.count", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("0"));
		
	
	}
	
	@Test(groups = {"Miniregression", "Regression", "ExhaustiveRegression"},dataProvider="fcCornerOutfitLikeUnlikeComment")
	public void FashionDestination_UnLikeComment(String outfitId){
		
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATIONMYNTAPI, APINAME.FCUNLIKEOUTFITCOMMENT, init.Configurations,PayloadType.JSON, new String[]{outfitId},PayloadType.JSON);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, outfitId);
		System.out.println("URL :"+service.URL);
		//System.out.println(service.Payload);
		//String[] headers=checkoutTestHelper.idp_GetTokens("newcart-regression@myntra.com", "testing!123");
		
		HashMap<String,String> tokens=new HashMap<String, String>();
		//tokens.put("xid", headers[0]);
		//tokens.put("X-CSRF-Token", headers[1]);
		tokens=fashionCornerTestsHelper.getXIDandSXidHeader("newcart-regression@myntra.com", "testing!123");
		
		RequestGenerator req = new RequestGenerator(service,tokens);		
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
		AssertJUnit.assertTrue("Validation of data.count", req.respvalidate.GetNodeValue("data.count", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("0"));
		
	
	}
	@Test(groups = { "Miniregression", "Regression", "ExhaustiveRegression"},dataProvider="fcgiveComment_FirstTime")
	public void FashionDestination_GiveComment_FirstTime(String name,String desc,String outfitid){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATIONMYNTAPI, APINAME.FCGIVECOMMENTFIRSTTIME, init.Configurations, new String[]{name,desc,outfitid});
		System.out.println("URL :"+service.URL);
		System.out.println(service.Payload);
		//String[] headers=checkoutTestHelper.idp_GetTokens("newcart-regression@myntra.com", "testing!123");
		
		HashMap<String,String> tokens=new HashMap<String, String>();
		//tokens.put("xid", headers[0]);
		//tokens.put("X-CSRF-Token", headers[1]);
		tokens=fashionCornerTestsHelper.getXIDandSXidHeader("newcart-regression@myntra.com", "testing!123");
		
		RequestGenerator req = new RequestGenerator(service,tokens);		
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
		AssertJUnit.assertTrue("Validation of data.count", req.respvalidate.GetNodeValue("data.count", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("0"));
	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"},dataProvider="fcCornerOutfitLikeUnlikeComment")
	public void FashionDestination_GiveComment(String outfitid){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATIONMYNTAPI, APINAME.FCGIVECOMMENT, init.Configurations, new String[]{outfitid});
		System.out.println("URL :"+service.URL);
		System.out.println(service.Payload);
		//String[] headers=checkoutTestHelper.idp_GetTokens("newcart-regression@myntra.com", "testing!123");
		
		HashMap<String,String> tokens=new HashMap<String, String>();
		//tokens.put("xid", headers[0]);
		//tokens.put("X-CSRF-Token", headers[1]);
		tokens=fashionCornerTestsHelper.getXIDandSXidHeader("newcart-regression@myntra.com", "testing!123");
		
		RequestGenerator req = new RequestGenerator(service,tokens);		
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
		AssertJUnit.assertTrue("Validation of data.count", req.respvalidate.GetNodeValue("data.count", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("0"));
	}
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcoutfitSearchNegitiveDP")
	//	public void FasionDestination_fcoutfitLikedByPositive(String outfits)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATIONMYNTAPI, APINAME.FCOUTFITLIKEDOUTFIT, init.Configurations, new String[]{outfits});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		FashionCornerTestsHelper fashionCornerHelper= new FashionCornerTestsHelper();
	//		String xid=fashionCornerHelper.getXIDHeader("mobile-automation@myntra.com","testing!123");
	//		HashMap getParam = new HashMap();
	//		getParam.put("Authorization",
	//				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
	//		getParam.put("Xid",xid);
	//		RequestGenerator req = new RequestGenerator(service, getParam);
	//		String jsonRes = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonRes);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		//AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//		//AssertJUnit.assertTrue("Validation of data.count", req.respvalidate.GetNodeValue("data.count", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("0"));
	//		
	//		}




	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcoutfitAddStyletoOutfitPositiveDP")
	//	public void FasionDestination_fcoutfitAddStyletoOutfitPositive(String userId, String outfit, String style)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCOUTFITADDSTYLETOOUTFIT, init.Configurations, new String[]{userId}, new String[]{outfit, style});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		if(isStyleIdExists(style)){
	//			AssertJUnit.assertEquals(200, req.response.getStatus());
	//			AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//			AssertJUnit.assertTrue("Validation of data.id", req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(outfit));
	//		}else{
	//			AssertJUnit.assertEquals(500, req.response.getStatus());
	//			AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("500"));
	//			AssertJUnit.assertTrue("Validation of meta.error", req.respvalidate.GetNodeValue("meta.error", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("Style not found"));
	//		}
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcoutfitAddStyletoOutfitNegativeDP")
	//	public void FasionDestination_fcoutfitAddStyletoOutfitNegative(String userId, String outfit, String style)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCOUTFITADDSTYLETOOUTFIT, init.Configurations, new String[]{userId}, new String[]{outfit, style});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		if(isStyleIdExists(style)){
	//			AssertJUnit.assertEquals(200, req.response.getStatus());
	//			AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//			AssertJUnit.assertTrue("Validation of data.id", req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(outfit));
	//		}else{
	//			AssertJUnit.assertEquals(500, req.response.getStatus());
	//			AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("500"));
	//			AssertJUnit.assertTrue("Validation of meta.error", req.respvalidate.GetNodeValue("meta.error", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("Style not found"));
	//		}
	//	}

	//	//fc-comment
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fccommentAddCommentToUserAndAnsPositiveDP")
	//	public void FasionDestination_fccommentAddCommentToUserAndAnsPositive(String title, String desc, String userId, String app, String ansId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCCOMMENTADDCOMMENTTOUSERANDANS, init.Configurations, new String[]{title, desc, userId, app, ansId});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		if(toCheckCommentId == 0){
	//			idofComment = Integer.parseInt(req.respvalidate.GetNodeValue("data.id", jsonResponse));
	//		}
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//		AssertJUnit.assertTrue("Validation of data.id", Integer.parseInt(req.respvalidate.GetNodeValue("data.id", jsonResponse))==(idofComment+toCheckCommentId));
	//		AssertJUnit.assertTrue("Validation of data.UserId", req.respvalidate.GetNodeValue("data.UserId", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(userId));
	//		++toCheckCommentId;
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fccommentAddCommentToUserAndAnsNegativeDP")
	//	public void FasionDestination_fccommentAddCommentToUserAndAnsNegative(String title, String desc, String userId, String app, String ansId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCCOMMENTADDCOMMENTTOUSERANDANS, init.Configurations, new String[]{title, desc, userId, app, ansId});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		if(toCheckCommentId == 0){
	//			idofComment = Integer.parseInt(req.respvalidate.GetNodeValue("data.id", jsonResponse));
	//		}
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//		AssertJUnit.assertTrue("Validation of data.id", Integer.parseInt(req.respvalidate.GetNodeValue("data.id", jsonResponse))==(idofComment+toCheckCommentId));
	//		AssertJUnit.assertTrue("Validation of data.UserId", req.respvalidate.GetNodeValue("data.UserId", jsonResponse).replaceAll("\"", "").equalsIgnoreCase(userId));
	//		++toCheckCommentId;
	//	}
	//	
	//	//fc-answer
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcanswerAddTagToAnswerPositiveDP")
	//	public void FasionDestination_fcanswerAddTagToAnswerPositive(String value, String ansId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCANSWERADDTAGTOANSWER, init.Configurations, new String[]{value, ansId});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcanswerAddTagToAnswerNegativeDP")
	//	public void FasionDestination_fcanswerAddTagToAnswerNegative(String value, String ansId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCANSWERADDTAGTOANSWER, init.Configurations, new String[]{value, ansId});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcanswerAddLikeToAnswerPositiveDP")
	//	public void FasionDestination_fcanswerAddLikeToAnswerPositive(String ansId, String userId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCANSWERADDLIKETOANSWER, init.Configurations, new String[]{ansId, userId});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcanswerAddLikeToAnswerPositiveDP")
	//	public void FasionDestination_fcanswerAddLikeToAnswerNegative(String ansId, String userId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCANSWERADDLIKETOANSWER, init.Configurations, new String[]{ansId, userId});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcanswerAddTagToAnswerV2PositiveDP")
	//	public void FasionDestination_fcanswerAddTagToAnswerV2Positive(String value, String ansId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCANSWERADDTAGTOANSWERV2, init.Configurations, new String[]{value}, new String[]{ansId});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcanswerAddTagToAnswerV2NegativeDP")
	//	public void FasionDestination_fcanswerAddTagToAnswerV2Negative(String value, String ansId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCANSWERADDTAGTOANSWERV2, init.Configurations, new String[]{value}, new String[]{ansId});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcanswerAddLikeToAnswerV2PositiveDP")
	//	public void FasionDestination_fcanswerAddLikeToAnswerV2Positive(String ansId, String userId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCANSWERADDLIKETOANSWERV2, init.Configurations);
	//		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[]{ansId, userId});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcanswerAddLikeToAnswerV2NegativeDP")
	//	public void FasionDestination_fcanswerAddLikeToAnswerV2Negative(String ansId, String userId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCANSWERADDLIKETOANSWERV2, init.Configurations);
	//		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[]{ansId, userId});
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcanswerGetAnswerPositiveDP")
	//	public void FasionDestination_fcanswerGetAnswerPositive(String ansId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCANSWERGETANSWER, init.Configurations);
	//		service.URL = apiUtil.prepareparameterizedURL(service.URL, ansId);
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	
	//	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fcanswerGetAnswerPositiveDP")
	//	public void FasionDestination_fcanswerGetAnswerNegative(String ansId)
	//	{
	//		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCANSWERGETANSWER, init.Configurations);
	//		service.URL = apiUtil.prepareparameterizedURL(service.URL, ansId);
	//		System.out.println("URL : "+service.URL);
	//		System.out.println(service.Payload);
	//		RequestGenerator req = new RequestGenerator(service);		
	//		String jsonResponse = req.respvalidate.returnresponseasstring();
	//		System.out.println(jsonResponse);
	//		AssertJUnit.assertEquals(200, req.response.getStatus());
	//		AssertJUnit.assertTrue("Validation of meta.code", req.respvalidate.GetNodeValue("meta.code", jsonResponse).replaceAll("\"", "").equalsIgnoreCase("200"));
	//	}
	//	


	public boolean isStyleIdExists(String styleId)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCOUTFITGETSTYLEDATA, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, styleId);
		RequestGenerator req = new RequestGenerator(service);		
		String jsonResponse = req.respvalidate.returnresponseasstring();
		if(req.respvalidate.GetNodeValue("data", jsonResponse).equalsIgnoreCase("null"))
			return false;
		else
			return true;
	}
	private List responseCreateUser(){
		List<String> responseCreateUser = new ArrayList();
		responseCreateUser.add("meta.code");
		responseCreateUser.add("data.id");
		responseCreateUser.add("data.name");
		responseCreateUser.add("data.email");
		responseCreateUser.add("data.role");
		responseCreateUser.add("data.from");
		responseCreateUser.add("data.bio");
		responseCreateUser.add("data.updatedAt");
		responseCreateUser.add("data.createdAt");
		return responseCreateUser;
	}

	private List responseGetUser(){
		List<String> responseGetUser = new ArrayList();
		responseGetUser.add("meta.code");
		responseGetUser.add("data.id");
		responseGetUser.add("data.pic");
		responseGetUser.add("data.email");
		responseGetUser.add("data.role");
		responseGetUser.add("data.from");
		responseGetUser.add("data.bio");
		responseGetUser.add("data.fb");
		responseGetUser.add("data.twitter");
		responseGetUser.add("data.instagram");
		responseGetUser.add("data.favourites");
		responseGetUser.add("data.telephonicSession");
		responseGetUser.add("data.inPersonSession");
		responseGetUser.add("data.updatedAt");
		responseGetUser.add("data.createdAt");
		responseGetUser.add("data.outfits");
		responseGetUser.add("data.usertags");
		responseGetUser.add("data.portfolioes");
		responseGetUser.add("data.following");
		return responseGetUser;
	}

	private List responseUpdateUser(){
		List<String> responseGetUser = new ArrayList();
		responseGetUser.add("meta.code");
		responseGetUser.add("data.id");
		responseGetUser.add("data.name");
		responseGetUser.add("data.pic");
		responseGetUser.add("data.email");
		responseGetUser.add("data.role");
		responseGetUser.add("data.from");
		responseGetUser.add("data.bio");
		responseGetUser.add("data.fb");
		responseGetUser.add("data.twitter");
		responseGetUser.add("data.instagram");
		responseGetUser.add("data.favourites");
		responseGetUser.add("data.telephonicSession");
		responseGetUser.add("data.inPersonSession");
		responseGetUser.add("data.updatedAt");
		responseGetUser.add("data.createdAt");
		return responseGetUser;
	}

	private List addQuestionToUser(){
		List<String> list = new ArrayList();
		list.add("meta.code");
		list.add("data.id");
		list.add("data.title");
		list.add("data.description");
		list.add("data.UserId");
		list.add("data.approved");
		list.add("data.updatedAt");
		list.add("data.createdAt");
		return list;
	}

	private List addAnswerToUser(){
		List<String> list = new ArrayList();
		list.add("meta.code");
		list.add("data.id");
		list.add("data.title");
		list.add("data.description");
		list.add("data.UserId");
		list.add("data.approved");
		list.add("data.QuestionId");
		list.add("data.updatedAt");
		list.add("data.createdAt");
		return list;
	}


	private String getValueOfOneFromAll(String all, String one, String firstDelimiter, String secDelimiter ){
		String[] afterFirestDelimit = all.split(firstDelimiter);
		String toReturn = "";
		for(String s : afterFirestDelimit){
			if(s.contains(one)){
				toReturn = s.split(secDelimiter)[1];
			}
		}
		return toReturn.trim();
	}

}
