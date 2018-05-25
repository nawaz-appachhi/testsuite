package com.myntra.apiTests.portalservices.crmservices;

import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.CRMServiceDP;
import com.myntra.apiTests.portalservices.CRMPortalService.Nodes;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.lordoftherings.gandalf.ResponseValidator;
import com.myntra.apiTests.common.ServiceType;


/**
 * @author eshita
 * 
 */

public class CRMPortalService extends CRMServiceDP {

	static Logger log = Logger.getLogger(CRMPortalService.class);

	static Initialize init = new Initialize("/Data/configuration");
	APIUtilities apiUtil = new APIUtilities();
	public ResponseValidator respvalidate;
	CRMERPService valid = new CRMERPService();

	/**
	 * This Test Case is used to invoke customerprofile API and verification for
	 * success response (200)
	 * 
	 * @param
	 * @param successRespCode
	 * @throws Exception
	 */
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "CRMServiceDP_CustomerProfileDataProvider_verifyAPIResponse")
	public void CRMPortalService_customerprofile_verifySuccessResponse(
			String loginId, String successRespCode, String successStatusCode,
			String successstotalcount, String successstatustype)
			throws Exception {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_CRMSERVICEPORTAL, APINAME.CUSTOMERPROFILE,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, loginId);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());

		Assert.assertTrue(
				req.respvalidate.NodeValue(
						Nodes.STATUS_CODE_PROFILE.toString(), true).contains(
						successStatusCode),
				"CRM PORTAL return API response status code is not a success code");
		Assert.assertTrue(
				req.respvalidate.NodeValue(
						Nodes.STATUS_TYPE_PROFILE.toString(), true).contains(
						successstatustype),
				"CRM PORTAL return API response status type is not a success code");
		Assert.assertTrue(
				req.respvalidate.NodeValue(
						Nodes.STATUS_TOTAL_COUNT_PROFILE.toString(), true)
						.contains(successstotalcount),
				"CRM PORTAL return API response status count is not a success count");
	//	String dob = req.respvalidate.NodeValue(Nodes.DOB.toString(), true);
		//System.out.println(dob);
		//Assert.assertEquals(
				//req.respvalidate.NodeValue(Nodes.DOB.toString(), true),
			//	"1982-02-12T00:00:00+05:30");

		Assert.assertNotNull("CRM PORTAL EMAIL is not a succesS",
				Nodes.EMAIL.toString());
		Assert.assertNotNull("CRM PORTAL Firstlogindetail is not a succesS",
				Nodes.FIRSTLOGIN.toString());
		Assert.assertNotNull("CRM PORTAL Mobile is not a succesS",
				Nodes.MOBILE.toString());
		Assert.assertNotNull("CRM PORTAL Status is not a succesS",
				Nodes.STATUS.toString());

		Assert.assertEquals(200, req.response.getStatus());
		service.responsedataformat = PayloadType.XML;

		Assert.assertEquals(req.respvalidate.ResponseFromService.getStatus(),
				200);
		Toolbox tools = new Toolbox();

		String jsonschema = tools
				.readFileAsString("Data/SchemaSet/JSON/crmportalcustomerprofile.txt");
		System.out.println(jsonschema);

		String jsondata = req.respvalidate.returnresponseasstring();

		System.out.println(jsondata);
		System.out.println("validate(jsondata, jsonschema) = "
				+ valid.validate(jsondata, jsonschema));
		Assert.assertTrue(valid.validate(jsondata, jsonschema));

	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "CRMServiceDP_ReturnDataProvider_verifyAPIResponse")
	public void CRMPortalService_return_verifySuccessResponse(String loginId,
			String successRespCode, String successStatusCode,
			String successstatuscount, String successstatustype)
			throws Exception {

		MyntraService service = Myntra.getService(
				ServiceType.ERP_CRMSERVICEPORTAL, APINAME.RETURN,
				init.Configurations);
		System.out.println("RETURN");
		service.URL = apiUtil.prepareparameterizedURL(service.URL, loginId);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		RequestGenerator req = new RequestGenerator(service, getParam);

		System.out.println(req.respvalidate.returnresponseasstring());

		String statuscode = req.respvalidate.NodeValue(
				Nodes.STATUS_TYPE.toString(), true);
		String statuscount = req.respvalidate.NodeValue(
				Nodes.STATUS_CODE.toString(), true);
		String statustype = req.respvalidate.NodeValue(
				Nodes.STATUS_TOTAL_COUNT.toString(), true);
		String returncity = req.respvalidate.NodeValue(
				Nodes.RETURN_CITY.toString(), true);
		String courier = req.respvalidate.NodeValue(
				Nodes.COURIER_SERVICE.toString(), true);
		String orderid = req.respvalidate.NodeValue(Nodes.ORDER_ID.toString(),
				true);
		String quantity = req.respvalidate.NodeValue(Nodes.QUANTITY.toString(),
				true);
		String returndate = req.respvalidate.NodeValue(
				Nodes.RETURNCREATEDDATE.toString(), true);
		String returnid = req.respvalidate.NodeValue(Nodes.RETURNID.toString(),
				true);
		String returnstatus = req.respvalidate.NodeValue(
				Nodes.RETURNSTATUS.toString(), true);
		String skucode = req.respvalidate.NodeValue(Nodes.SKUCODE.toString(),
				true);
		String trackingno = req.respvalidate.NodeValue(
				Nodes.TRACKINGNUMBER.toString(), true);
		String zipcode = req.respvalidate.NodeValue(Nodes.ZIPCODE.toString(),
				true);

		System.out.println("StatusCode :" + statuscode);
		System.out.println("StatusCount :" + statuscount);
		System.out.println("StatusType :" + statustype);
		System.out.println("ReturnCity :" + returncity);
		System.out.println("Courier :" + courier);
		System.out.println("Orderid :" + orderid);
		System.out.println("Quantity :" + quantity);
		System.out.println("OrderReturndate :" + returndate);
		System.out.println("ReturnId :" + returnid);
		System.out.println("ReturnStatus :" + returnstatus);
		System.out.println("SKU Code:" + skucode);
		System.out.println("ZIP Code:" + zipcode);
		System.out.println("Tracking no:" + trackingno);

		Assert.assertTrue(
				req.respvalidate.NodeValue(Nodes.STATUS_CODE.toString(), true)
						.contains(successStatusCode),
				"CRM PORTAL return API response status code is not a success code");
		Assert.assertTrue(
				req.respvalidate.NodeValue(Nodes.STATUS_TYPE.toString(), true)
						.contains(successstatustype),
				"CRM PORTAL return API response status type is not a success code");
		Assert.assertTrue(
				req.respvalidate.NodeValue(Nodes.STATUS_TOTAL_COUNT.toString(),
						true).contains(successstatuscount),
				"CRM PORTAL return API response status code is not a success count");

		Assert.assertNotNull("CRM PORTAL return city is not a success code",
				Nodes.RETURN_CITY.toString());
		Assert.assertNotNull("CRM PORTAL courier is not a success courier",
				Nodes.COURIER_SERVICE.toString());
		Assert.assertNotNull("CRM PORTAL orderid is not a success orderid",
				Nodes.ORDER_ID.toString());
		Assert.assertNotNull("CRM PORTAL quantity is not a success quantity",
				Nodes.QUANTITY.toString());
		Assert.assertNotNull("CRM PORTAL returndate is not a success date",
				Nodes.RETURNCREATEDDATE.toString());
		Assert.assertNotNull("CRM PORTAL returnid is not a success id",
				Nodes.RETURNID.toString());
		Assert.assertNotNull("CRM PORTAL returnstatus is not a success status",
				Nodes.RETURNSTATUS.toString());
		Assert.assertNotNull("CRM PORTAL skucode is not a success skucode",
				Nodes.SKUCODE.toString());
		Assert.assertNotNull("CRM PORTAL zipcode is not a success zipcode",
				Nodes.ZIPCODE.toString());
		Assert.assertNotNull(
				"CRM PORTAL tracking no is not a success tracking no",
				Nodes.TRACKINGNUMBER.toString());

		Assert.assertEquals(200, req.response.getStatus());

		service.responsedataformat = PayloadType.XML;

		Assert.assertEquals(req.respvalidate.ResponseFromService.getStatus(),
				200);

	}

	/*
	 * @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
	 * "ExhaustiveRegression" }, dataProvider =
	 * "CRMServiceDP_CouponDataProvider_verifyAPIResponse") public void
	 * CRMPortalService_coupon_verifySuccessResponse(String loginId, String
	 * successRespCode, String successStatusCode, String successstotalcount,
	 * String successstatustype) throws Exception { MyntraService service = new
	 * MyntraService( ServiceType.ERP_CRMSERVICEPORTAL, APINAME.COUPON,
	 * init.Configurations); System.out.println("COUPON"); service.URL =
	 * apiUtil.prepareparameterizedURL(service.URL, loginId);
	 * System.out.println(service.URL); HashMap getParam = new HashMap();
	 * getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
	 * RequestGenerator req = new RequestGenerator(service, getParam);
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * 
	 * String statuscode = req.respvalidate.NodeValue(
	 * Nodes.STATUS_CODE_COUPON.toString(), true); String couponcode =
	 * req.respvalidate.NodeValue(
	 * "couponResponse.couponEntryList.couponEntry.coupon", true);
	 * 
	 * System.out.println("Status Code :" + statuscode);
	 * System.out.println("Coupon Code :" + couponcode);
	 * 
	 * Assert.assertTrue(
	 * req.respvalidate.NodeValue(Nodes.STATUS_CODE_COUPON.toString(),
	 * true).contains(successStatusCode),
	 * "CRM PORTAL coupon API response status code is not a success code");
	 * Assert.assertTrue(
	 * 
	 * req.respvalidate.NodeValue(Nodes.STATUS_TYPE_COUPON.toString(), true)
	 * .contains(successstatustype),
	 * "CRM PORTAL return API response status type is not a success type");
	 * Assert.assertFalse( req.respvalidate.NodeValue(
	 * Nodes.STATUS_TOTAL_COUPON.toString(), true).contains(
	 * successstotalcount),
	 * "CRM PORTAL coupon API response count is not a success type" +
	 * successstotalcount);
	 * Assert.assertNotNull("CRM PORTAL coupon is not a success coupon code",
	 * Nodes.COUPON.toString());
	 * Assert.assertNotNull("CRM PORTAL coupon status is not a success type",
	 * Nodes.COUPON_STATUS.toString());
	 * Assert.assertNotNull("CRM PORTAL coupon type is not a success one",
	 * Nodes.COUPON_TYPE.toString()); Assert.assertNotNull(
	 * "CRM PORTAL coupon minimum is not a success value",
	 * Nodes.COUPON_MINIMUM.toString()); Assert.assertNotNull(
	 * "CRM PORTAL coupon mrpamount is not a success value",
	 * Nodes.COUPON_MRPAMOUNT.toString()); Assert.assertNotNull(
	 * "CRM PORTAL coupon percentage is not a success value",
	 * Nodes.COUPON_MRPPERCENTAGE.toString());
	 * Assert.assertNotNull("CRM PORTAL coupon per user a success value",
	 * Nodes.COUPON_PER_USER.toString()); Assert.assertNotNull(
	 * "CRM PORTAL coupon Times used is not a success one",
	 * Nodes.COUPON_TIMES.toString());
	 * 
	 * Assert.assertEquals(200, req.response.getStatus());
	 * 
	 * service.responsedataformat = PayloadType.XML;
	 * 
	 * Assert.assertEquals(req.respvalidate.ResponseFromService.getStatus(),
	 * 200); Toolbox tools = new Toolbox();
	 * 
	 * String jsonschema = tools
	 * .readFileAsString("Data/SchemaSet/JSON/crmportalcoupon.txt");
	 * System.out.println(jsonschema);
	 * 
	 * String jsondata = req.respvalidate.returnresponseasstring(); //
	 * servicetest2 xyz= new servicetest2(); // boolean
	 * validate=xyz.validate(jsondata, jsonschema);
	 * 
	 * System.out.println("validate(jsondata, jsonschema) = " +
	 * valid.validate(jsondata, jsonschema));
	 * Assert.assertTrue(valid.validate(jsondata, jsonschema)); }
	 * 
	 * @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
	 * "ExhaustiveRegression" }, dataProvider =
	 * "CRMServiceDP_CommentDataProvider_verifyAPIResponse") public void
	 * CRMPortalService_comment_verifySuccessResponse(String orderId, String
	 * successRespCode, String successStatusCode, String successstatustype) {
	 * MyntraService service = Myntra.getService(
	 * ServiceType.ERP_CRMSERVICEPORTAL, APINAME.COMMENT, init.Configurations);
	 * System.out.println("COMMENT"); service.URL =
	 * apiUtil.prepareparameterizedURL(service.URL, orderId);
	 * System.out.println(service.URL); HashMap getParam = new HashMap();
	 * getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
	 * RequestGenerator req = new RequestGenerator(service, getParam);
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * 
	 * Assert.assertTrue( req.respvalidate.NodeValue(
	 * Nodes.STATUS_CODE_COMMENT.toString(), true).contains( successStatusCode),
	 * "CRM PORTAL return API response status type is not a success type");
	 * 
	 * Assert.assertTrue( req.respvalidate.NodeValue(
	 * Nodes.STATUS_TYPE_COMMENT.toString(), true).contains( successstatustype),
	 * "CRM PORTAL return API response status type is not a success type");
	 * 
	 * Assert.assertEquals(200, req.response.getStatus());
	 * service.responsedataformat = PayloadType.XML;
	 * 
	 * Assert.assertEquals(req.respvalidate.ResponseFromService.getStatus(),
	 * 200); Toolbox tools = new Toolbox();
	 * 
	 * String jsonschema = null; try { jsonschema = tools
	 * .readFileAsString("Data/SchemaSet/JSON/crmportalcomment.txt"); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * } System.out.println(jsonschema);
	 * 
	 * String jsondata = req.respvalidate.returnresponseasstring(); try {
	 * System.out.println("validate(jsondata, jsonschema) = " +
	 * valid.validate(jsondata, jsonschema));
	 * Assert.assertTrue(valid.validate(jsondata, jsonschema)); } catch
	 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 */

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "CRMServiceDP_onholdDataProvider_verifyAPIResponse")
	public void CRMPortalService_onhold_verifySuccessResponse(String orderId,
			String successRespCode, String successStatusCode,
			String successstatustype, String successstotalcount)
			throws Exception {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_CRMSERVICEPORTAL, APINAME.ONHOLD,
				init.Configurations);
		System.out.println("ONHOLD SERVICE");
		service.URL = apiUtil.prepareparameterizedURL(service.URL, orderId);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());

		Assert.assertEquals(200, req.response.getStatus());

		Assert.assertTrue(
				req.respvalidate.NodeValue(Nodes.STATUS_CODE_ONHOLD.toString(),
						true).contains(successStatusCode),
				"CRM PORTAL return API response status CODE is not a success code");

		Assert.assertTrue(
				req.respvalidate.NodeValue(Nodes.STATUS_TYPE_ONHOLD.toString(),
						true).contains(successstatustype),
				"CRM PORTAL return API response status type is not a success type");

		Assert.assertTrue(
				req.respvalidate.NodeValue(
						Nodes.STATUS_TOTAL_COUNT_ONHOLD.toString(), true)
						.contains(successstotalcount),
				"CRM PORTAL return API response total count is not a success type");
		Assert.assertNotNull(
				"CRM PORTAL ONHOLD DISPOSITION is not a success one",
				Nodes.ONHOLD_DISPOSITION.toString());
		Assert.assertNotNull(
				"CRM PORTAL ONHOLD DISPOSITION is not a success one",
				Nodes.ONHOLD_ORDEID.toString());
		Assert.assertNotNull(
				"CRM PORTAL ONHOLD DISPOSITION is not a success one",
				Nodes.ONHOLD_REASON.toString());

		Toolbox tools = new Toolbox();
		String jsonschema = tools
				.readFileAsString("Data/SchemaSet/JSON/crmportalonhold.txt");
		System.out.println(jsonschema);

		String jsondata = req.respvalidate.returnresponseasstring();
		System.out.println("validate(jsondata, jsonschema) = "
				+ valid.validate(jsondata, jsonschema));
		Assert.assertTrue(valid.validate(jsondata, jsonschema));

	}

	/*
	 * @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
	 * "ExhaustiveRegression" }, dataProvider =
	 * "CRMServiceDP_ExchnageDataProvider_verifyAPIResponse") public void
	 * CRMPortalService_exchange_verifySuccessResponse(String orderId, String
	 * successRespCode, String successStatusCode, String successstatustype) {
	 * MyntraService service = Myntra.getService(
	 * ServiceType.ERP_CRMSERVICEPORTAL, APINAME.EXCHANGE, init.Configurations);
	 * System.out.println("EXCHANGE SERVICE"); service.URL =
	 * apiUtil.prepareparameterizedURL(service.URL, orderId);
	 * System.out.println(service.URL); HashMap getParam = new HashMap();
	 * getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
	 * RequestGenerator req = new RequestGenerator(service, getParam);
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * Assert.assertTrue( req.respvalidate.NodeValue(
	 * Nodes.STATUS_CODE_EXCHANGE.toString(), true).contains(
	 * successStatusCode),
	 * "CRM PORTAL EXCHANGE API response status CODE is not a success code");
	 * 
	 * Assert.assertTrue( req.respvalidate.NodeValue(
	 * Nodes.STATUS_TYPE_EXCHANGE.toString(), true).contains(
	 * successstatustype),
	 * "CRM PORTAL EXCHANGE API response status type is not a success type");
	 * 
	 * Assert.assertEquals(200, req.response.getStatus()); }
	 */
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "CRMServiceDP_PaymentDataProvider_verifyAPIResponse")
	public void CRMPortalService_payment_verifySuccessResponse(String orderId,
			String successRespCode, String successStatusCode,
			String successstatustype) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_CRMSERVICEPORTAL, APINAME.PAYMENT,
				init.Configurations);
		System.out.println("PAYMENT SERVICE");
		service.URL = apiUtil.prepareparameterizedURL(service.URL, orderId);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());

		Assert.assertEquals(200, req.response.getStatus());
	}

}
