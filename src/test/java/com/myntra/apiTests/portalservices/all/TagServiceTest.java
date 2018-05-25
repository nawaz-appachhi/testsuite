package com.myntra.apiTests.portalservices.all;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.tagService.tagServiceHelper;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.apiTests.dataproviders.TagServiceDataProvider;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class TagServiceTest extends TagServiceDataProvider {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(TagServiceTest.class);

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "publishTagAddDataProvider")
	public void publishTagAdd(String styleId, String tagstring1,
			String tagstring2, String tagNamespace) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.PUBLISHTAGADD,
				init.Configurations, new String[] { styleId, tagstring1,
						tagstring2, tagNamespace }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "publishTagAddDataProvider")
	public void TagService_publishTagAddDefaultTTL(String styleId,
			String tagstring1, String tagstring2, String tagNamespace) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.PUBLISHTAGADDDEFAULTTTL,
				init.Configurations, new String[] { styleId, tagstring1,
						tagstring2, tagNamespace }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator requestGenerator = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				requestGenerator.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "publishTagAddBulk")
	public void publishTagAddBulk(String styleId1, String styleId2,
			String styleId3, String styleId4, String styleId5, String styleId6,
			String styleId7, String styleId8, String styleId9,
			String styleId10, String styleId11, String styleId12,
			String tagNamespace, String tag) {
		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.PUBLISHTAGADDBULK,
				init.Configurations, new String[] { styleId1, styleId2,
						styleId3, styleId4, styleId5, styleId6, styleId7,
						styleId8, styleId9, styleId10, styleId11, styleId12,
						tagNamespace, tag }, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator requestGenerator = new RequestGenerator(myntraService);
		log.info(myntraService.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				requestGenerator.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "publishTagRemove")
	public void publishTagRemove(String styleId, String tagstring1,
			String tagstring2, String tagNamespace) {
		tagServiceHelper.publishTagAdd(styleId, tagstring1, tagstring2,
				tagNamespace);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.PUBLISHTAGREMOVE,
				init.Configurations, new String[] { styleId, tagstring1,
						tagstring2, tagNamespace }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());

	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "publishTagRemoveBulk")
	public void publishTagRemoveBulk(String styleId1, String styleId2,
			String styleId3, String styleId4, String styleId5, String styleId6,
			String styleId7, String styleId8, String styleId9,
			String styleId10, String styleId11, String styleId12,
			String tagNamespace, String tag) {
		tagServiceHelper.publishTagAddBulk(styleId1, styleId2, styleId3,
				styleId4, styleId5, styleId6, styleId7, styleId8, styleId9,
				styleId10, styleId11, styleId12, tagNamespace, tag);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.PUBLISHTAGREMOVEBULK,
				init.Configurations, new String[] { styleId1, styleId2,
						styleId3, styleId4, styleId5, styleId6, styleId7,
						styleId8, styleId9, styleId10, styleId11, styleId12,
						tagNamespace, tag }, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator requestGenerator = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				requestGenerator.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "publishTagRemoveBulkTag")
	public void publishTagRemoveBulkTag(String styleId1, String styleId2,
			String styleId3, String styleId4, String styleId5, String styleId6,
			String styleId7, String styleId8, String styleId9,
			String styleId10, String styleId11, String styleId12,
			String tagNamespace, String tag) {
		tagServiceHelper.publishTagAddBulk(styleId1, styleId2, styleId3,
				styleId4, styleId5, styleId6, styleId7, styleId8, styleId9,
				styleId10, styleId11, styleId12, tagNamespace, tag);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.PUBLISHTAGREMOVEBULKTAG,
				init.Configurations, new String[] { tagNamespace, tag },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator requestGenerator = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				requestGenerator.response.getStatus());

	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "publishTagRemoveAll")
	public void publishTagRemoveAll(String styleId1, String styleId2,
			String styleId3, String styleId4, String styleId5, String styleId6,
			String styleId7, String styleId8, String styleId9,
			String styleId10, String styleId11, String styleId12,
			String tagNamespace, String tag) {
		tagServiceHelper.publishTagAddBulk(styleId1, styleId2, styleId3,
				styleId4, styleId5, styleId6, styleId7, styleId8, styleId9,
				styleId10, styleId11, styleId12, tagNamespace, tag);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.PUBLISHTAGREMOVEALL,
				init.Configurations, new String[] { tagNamespace },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator requestGenerator = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				requestGenerator.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "publishTagFullRefresh")
	public void publishTagFullRefresh(String styleId, String tagstring1,
			String tagstring2, String tagNamespace) {
		tagServiceHelper.publishTagRemoveAll(tagNamespace);
		tagServiceHelper.publishTagAdd(styleId, tagstring1, tagstring2,
				tagNamespace);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.PUBLISHTAGFULLREFRESH,
				init.Configurations, new String[] { styleId, tagstring1,
						tagstring2, tagNamespace }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator requestGenerator = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				requestGenerator.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "fetchTag")
	public void fetchTag(String styleId, String tagstring1, String tagstring2,
			String tagNamespace) {
		tagServiceHelper.publishTagAdd(styleId, tagstring1, tagstring2,
				tagNamespace);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.FETCHTAG,
				init.Configurations, new String[] { styleId },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "fetchTag")
	public void fetchTagResponse(String styleId, String tagstring1,
			String tagstring2, String tagNamespace) {
		tagServiceHelper.publishTagRemoveAll(tagNamespace);
		tagServiceHelper.publishTagAdd(styleId, tagstring1, tagstring2,
				tagNamespace);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.FETCHTAG,
				init.Configurations, new String[] { styleId },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertTrue("tag is not mapped",
				tagServiceHelper.fetchTagResponseContainsTagStrings(req,
						tagstring1, tagstring2));
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "fetchTag")
	public void fetchTagWithNamespace(String styleId, String tagstring1,
			String tagstring2, String tagNamespace) {
		tagServiceHelper.publishTagAdd(styleId, tagstring1, tagstring2,
				tagNamespace);
		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.FETCHTAGWITHNAMESPACE,
				init.Configurations, new String[] { styleId },
				new String[] { tagNamespace }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator generator = new RequestGenerator(myntraService);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				generator.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "fetchTag")
	public void fetchTagWithNamespaceResponse(String styleId,
			String tagstring1, String tagstring2, String tagNamespace) {
		tagServiceHelper.publishTagRemoveAll(tagNamespace);
		tagServiceHelper.publishTagAdd(styleId, tagstring1, tagstring2,
				tagNamespace);
		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.FETCHTAGWITHNAMESPACE,
				init.Configurations, new String[] { styleId },
				new String[] { tagNamespace }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(myntraService);
		AssertJUnit.assertTrue("tag is not mapped",
				tagServiceHelper.fetchTagResponseContainsTagStrings(req,
						tagstring1, tagstring2));
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "fetchStyle")
	public void fetchStyleWithFullNamespace(String styleId, String tagString1,
			String tagString2, String tagNamespace) {
		tagServiceHelper.publishTagAdd(styleId, tagString1, tagString2,
				tagNamespace);
		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE,
				APINAME.FETCHSTYLEWITHFULLNAMESPACE, init.Configurations,
				new String[] { "" }, new String[] { tagNamespace, "0" },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator generator = new RequestGenerator(myntraService);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				generator.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "fetchStyle")
	public void fetchStyleWithFullNamespaceResponse(String styleId,
			String tagstring1, String tagstring2, String tagNamespace) {
		tagServiceHelper.publishTagRemoveAll(tagNamespace);
		tagServiceHelper.publishTagAdd(styleId, tagstring1, tagstring2,
				tagNamespace);
		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE,
				APINAME.FETCHSTYLEWITHFULLNAMESPACE, init.Configurations,
				new String[] { "" }, new String[] { tagNamespace, "0" },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(myntraService);
		AssertJUnit.assertTrue("tag is not mapped", tagServiceHelper
				.fetchStyleResponseTagStyleIdValidation(req, tagstring1,
						tagstring2, styleId));
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "fetchStyle")
	public void fetchStyle(String styleId, String tagstring1,
			String tagstring2, String tagNamespace) {
		tagServiceHelper.publishTagAdd(styleId, tagstring1, tagstring2,
				tagNamespace);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.FETCHSTYLE,
				init.Configurations, new String[] { tagstring1, tagstring2 },
				new String[] { tagNamespace, "0" }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "fetchStyle")
	public void fetchStyleResponse(String styleId, String tagstring1,
			String tagstring2, String tagNamespace) {
		tagServiceHelper.publishTagAdd(styleId, tagstring1, tagstring2,
				tagNamespace);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.FETCHSTYLE,
				init.Configurations, new String[] { tagstring1, tagstring2 },
				new String[] { tagNamespace, "0" }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertTrue("tag is not mapped", tagServiceHelper
				.fetchStyleResponseTagStyleIdValidation(req, tagstring1,
						tagstring2, styleId));
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "publishTagAddBulkCustomPayload")
	public void publishTagAddBulkCustomPayload(String styleIds, String tag,
			String tagNamespace) {
		MyntraService customMyntraService = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.PUBLISHTAGADDBULK,
				init.Configurations,
				tagServiceHelper.createPublishTagAddBulkCustomPayload(tag,
						tagNamespace, styleIds));
		RequestGenerator requestGenerator = new RequestGenerator(
				customMyntraService);
		log.info(customMyntraService.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				requestGenerator.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "publishTagAddBulkCustomPayload")
	public void publishTagRemoveBulkCustomPayload(String styleIds, String tag,
			String tagNamespace) {
		tagServiceHelper.publishTagAddBulk(styleIds, tag, tagNamespace);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.PUBLISHTAGREMOVEBULK,
				init.Configurations,
				tagServiceHelper.createPublishTagRemoveBulkCustomPayload(tag,
						tagNamespace, styleIds));
		RequestGenerator requestGenerator = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				requestGenerator.response.getStatus());

	}
}
