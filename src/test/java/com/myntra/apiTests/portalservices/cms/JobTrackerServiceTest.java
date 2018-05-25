package com.myntra.apiTests.portalservices.cms;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


/**
 * @author Vaishali Behere
 * 
 */

public class JobTrackerServiceTest extends CommonUtils {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(JobTrackerServiceTest.class);
	SoftAssert sft;
	

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify response of /jobtracker/findactivejobsbyjobtype?jobtype=CHANGE_PRICE")
	public void findJobByJobTypeTest() throws Exception {
		sft = new SoftAssert();
		String jobType = "CHANGE_PRICE";
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_JOBTRACKER_FIND, new String[]{jobType});
		sft.assertEquals(JsonPath.read(req.returnresponseasstring(),"$.status.statusMessage").toString(), "Success", "Status msg is not correct");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].createdBy").toString(), "createdBy is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].totalRecords").toString(), "totalRecords is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].processedRecords").toString(), "processedRecords is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].erredRecords").toString(), "erredRecords is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].status").toString(), "status is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].metaData").toString(), "metaData is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].inputFile").toString(), "inputFile is null");
		sft.assertEquals(JsonPath.read(req.returnresponseasstring(),"$.data[0].jobRegistryEntry.jobType").toString(), jobType, "jobType is not correct");
		sft.assertAll();
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify response of /jobtracker/search")
	public void searchJobTest() throws Exception {
		sft = new SoftAssert();
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_JOBTRACKER_SEARCH, null);
		sft.assertEquals(JsonPath.read(req.returnresponseasstring(),"$.status.statusMessage").toString(), "Success", "Status msg is not correct");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].createdBy").toString(), "createdBy is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].totalRecords").toString(), "totalRecords is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].processedRecords").toString(), "processedRecords is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].erredRecords").toString(), "erredRecords is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].status").toString(), "status is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].metaData").toString(), "metaData is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].inputFile").toString(), "inputFile is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].jobRegistryEntry").toString(), "jobRegistryEntry is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].subJobsEntryList").toString(), "subJobsEntryList is null");
		sft.assertAll();
	}
	
	
}
