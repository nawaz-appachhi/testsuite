package com.myntra.apiTests.erpservices.partners.Tests;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.dataproviders.ManufacturingDP;
import com.myntra.apiTests.portalservices.commons.StatusNodes;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

public class ManufacturingService extends ManufacturingDP {
	
	
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(ManufacturingService.class);
	static String envName = "fox7";

	APIUtilities apiUtil = new APIUtilities();
	
	
	
	@Test(groups = {"Regression"}, dataProvider = "getDefects", priority = 2,description="")
	public void Manufacturing_getDefects(String paramStatusCode,String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MANUFACTURING,
				APINAME.GETDEFECTS, init.Configurations,
				PayloadType.JSON, new String[] {
						paramStatusCode,paramStatusMessage, paramStatusType }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		
		String responseStatusMessage = req.respvalidate.NodeValue(
				StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");

		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue("StatusCode is invalid, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("StatusMessage is invalid, Expected: <"
				+ paramStatusMessage + "> but Actual: <" + responseStatusMessage
				+ ">", responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("StatusType is invalid, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));

	}
	
	@Test(groups = {"Regression"}, dataProvider = "getDefects", priority = 2,description="")
	public void Manufacturing_graphMOQty(String paramStatusCode,String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MANUFACTURING,
				APINAME.GRAPHMOQTY, init.Configurations,
				PayloadType.JSON, new String[] {
						paramStatusCode,paramStatusMessage, paramStatusType }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		
		String responseStatusMessage = req.respvalidate.NodeValue(
				StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");

		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue("StatusCode is invalid, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("StatusMessage is invalid, Expected: <"
				+ paramStatusMessage + "> but Actual: <" + responseStatusMessage
				+ ">", responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("StatusType is invalid, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));

	}
	
	@Test(groups = {"Regression"}, dataProvider = "getDefects", priority = 2,description="")
	public void Manufacturing_graphOfMO(String paramStatusCode,String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MANUFACTURING,
				APINAME.GRAPHOFMOS, init.Configurations,
				PayloadType.JSON, new String[] {
						paramStatusCode,paramStatusMessage, paramStatusType }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		
		String responseStatusMessage = req.respvalidate.NodeValue(
				StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");

		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue("StatusCode is invalid, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("StatusMessage is invalid, Expected: <"
				+ paramStatusMessage + "> but Actual: <" + responseStatusMessage
				+ ">", responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("StatusType is invalid, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));

	}
	
	
	@Test(groups = {"Regression"}, dataProvider = "getAuditRequest", priority = 2,description="")
	public void Manufacturing_getAuditRequest(String Id,String paramStatusCode,String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MANUFACTURING,
				APINAME.GETAUDITREQUEST, init.Configurations,
				PayloadType.JSON, new String[] {Id,
						paramStatusCode,paramStatusMessage, paramStatusType }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		
		String responseStatusMessage = req.respvalidate.NodeValue(
				StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");

		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue("StatusCode is invalid, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("StatusMessage is invalid, Expected: <"
				+ paramStatusMessage + "> but Actual: <" + responseStatusMessage
				+ ">", responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("StatusType is invalid, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));

	}
	
	@Test(groups = {"Regression"}, dataProvider = "getDefects", priority = 2,description="")
	public void Manufacturing_getAuditReequestedMO(String paramStatusCode,String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MANUFACTURING,
				APINAME.GETAUDITREEQUESTEDMO, init.Configurations,
				PayloadType.JSON, new String[] {
						paramStatusCode,paramStatusMessage, paramStatusType }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		
		String responseStatusMessage = req.respvalidate.NodeValue(
				StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");

		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue("StatusCode is invalid, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("StatusMessage is invalid, Expected: <"
				+ paramStatusMessage + "> but Actual: <" + responseStatusMessage
				+ ">", responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("StatusType is invalid, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));

	}
	
	@Test(groups = {"Regression"}, dataProvider = "getDefects", priority = 2,description="")
	public void Manufacturing_updateAuditRequest(String paramStatusCode,String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MANUFACTURING,
				APINAME.UPDATEAUDITREQUEST, init.Configurations,
				new String[] { 
						paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		
		String responseStatusMessage = req.respvalidate.NodeValue(
				StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");

		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue("StatusCode is invalid, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("StatusMessage is invalid, Expected: <"
				+ paramStatusMessage + "> but Actual: <" + responseStatusMessage
				+ ">", responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("StatusType is invalid, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));

	}
	
	
	

}
