package com.myntra.apiTests.erpservices.partners.Tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.myntra.apiTests.erpservices.partners.BulkUploadHelper;
import com.myntra.apiTests.erpservices.partners.VMSHelper;
import com.myntra.apiTests.erpservices.partners.VendorServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.test.commons.testbase.BaseTest;

/**
 * 
 * @author sandal.iqbal
 *
 */
public class TermsTest extends BaseTest {
	static Logger log = LoggerFactory.getLogger(TermsTest.class);
	public static Initialize init = new Initialize("Data/configuration");
	VendorServiceHelper vendorHelper;
	VMSHelper vmsHelper = new VMSHelper();
	public static int poVendorId;
	public static int poContractId;
	public static int poVersion;
	int contractId;
	int vendorTermId;
	int moqTermId;

	@BeforeClass(alwaysRun = true)
	public void beforeClass(ITestContext testContext) throws Exception {
		vendorHelper = new VendorServiceHelper(testContext);
		poVendorId = vendorHelper.createVendor();
		poContractId = vendorHelper.createContract(poVendorId);
	}

	@Test(enabled = true, groups = { "sanity", "Regression", "terms" }, description = "creates a new vendor term")
	public void createNewVendorTerm(ITestContext testContext) throws Exception {
		try {
			vendorTermId = vendorHelper.createPOTerm(poVendorId);
		} finally {
			vmsHelper.deletePOTerm(String.valueOf(vendorTermId));
		}
	}

	@Test(enabled = true, groups = { "sanity", "Regression", "terms" }, description = "searches a contract by its id")
	public void searchContractById(ITestContext testContext) throws Exception {
		vendorHelper.searchContractbyId(poContractId);
	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"terms" }, description = "searches contract between two partners")
	public void searchContractBetweenPartners(ITestContext testContext) throws Exception {
		vendorHelper.searchContractBetweenPartners(poVendorId);
	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"terms" }, description = "searches contract between multiple partners")
	public void searchContractBetweenMultiplePartners(ITestContext testContext) throws Exception {
		vendorHelper.searchContractBetweenMultiplePartners();
	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"terms" }, description = "searches po term between two partners")
	public void searchTermBetweenPartners(ITestContext testContext) throws Exception {
		try {
			vendorTermId = vendorHelper.createPOTerm(poVendorId);
			vendorHelper.searchTermBetweenPartners(poVendorId);
		} finally {
			vmsHelper.deletePOTerm(String.valueOf(vendorTermId));
		}
	}

	@Test(enabled = true, groups = { "sanity", "Regression", "terms" }, description = "searches a term by contract id")
	public void searchTermByContractId(ITestContext testContext) throws Exception {
		try {
			vendorTermId = vendorHelper.createPOTerm(poVendorId);
			vendorHelper.searchTermbyContractId(poContractId);
		} finally {
			vmsHelper.deletePOTerm(String.valueOf(vendorTermId));
		}
	}

	@Test(enabled = true, groups = { "sanity", "Regression", "terms" }, description = "updates a field in po term")
	public void updateDraftPOTerm(ITestContext testContext) throws Exception {
		try {
			contractId = vendorHelper.createContract();
			vendorTermId = vendorHelper.createPOTerm(VendorServiceHelper.vendorid);
			vendorHelper.updatePOTerm(vendorTermId, contractId, VendorServiceHelper.vendorid);
		} finally {
			vmsHelper.deleteFromVendor(String.valueOf(VendorServiceHelper.vendorid));
			vmsHelper.deleteContract(String.valueOf(contractId));
			vmsHelper.deletePOTerm(String.valueOf(vendorTermId));
		}
	}

	@Test(enabled = true, groups = { "sanity", "Regression", "terms" }, description = "sends a po term for activation")
	public void sendPOForActivation(ITestContext testContext) throws Exception {
		try {
			contractId = vendorHelper.createContract();
			vendorTermId = vendorHelper.createPOTerm(VendorServiceHelper.vendorid);
			vendorHelper.sendTermForActivation(vendorTermId, VendorServiceHelper.vendorid);
		} finally {
			vmsHelper.deleteFromVendor(String.valueOf(VendorServiceHelper.vendorid));
			vmsHelper.deleteContract(String.valueOf(contractId));
			vmsHelper.deletePOTerm(String.valueOf(vendorTermId));
		}
	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"terms" }, expectedExceptions = IllegalStateException.class, description = "edits a po term pending for activation")
	public void editPOPendingActivation(ITestContext testContext) throws Exception {
		try {
			contractId = vendorHelper.createContract();
			vendorTermId = vendorHelper.createPOTerm(VendorServiceHelper.vendorid);
			vendorHelper.sendTermForActivation(vendorTermId, VendorServiceHelper.vendorid);
			vendorHelper.updatePendingActivePOTerm(vendorTermId, contractId, VendorServiceHelper.vendorid);
		} finally {
			vmsHelper.deleteFromVendor(String.valueOf(VendorServiceHelper.vendorid));
			vmsHelper.deleteContract(String.valueOf(contractId));
			vmsHelper.deletePOTerm(String.valueOf(vendorTermId));
		}
	}

	@Test(enabled = true, groups = { "send", "sanity", "Regression", "terms" }, description = "sends back a po term")
	public void sendBackPO(ITestContext testContext) throws Exception {
		try {
			contractId = vendorHelper.createContract();
			vendorTermId = vendorHelper.createPOTerm(VendorServiceHelper.vendorid);
			vendorHelper.sendTermForActivation(vendorTermId, VendorServiceHelper.vendorid);
			vendorHelper.sendBackPOTerm(vendorTermId, VendorServiceHelper.vendorid);
		} finally {
			vmsHelper.deleteFromVendor(String.valueOf(VendorServiceHelper.vendorid));
			vmsHelper.deleteContract(String.valueOf(contractId));
			vmsHelper.deletePOTerm(String.valueOf(vendorTermId));
		}
	}

	@Test(enabled = true, groups = { "sanity", "Regression", "terms" }, description = "updates a sent back po term")
	public void sendBackPOAndEdit(ITestContext testContext) throws Exception {
		try {
			contractId = vendorHelper.createContract();
			vendorTermId = vendorHelper.createPOTerm(VendorServiceHelper.vendorid);
			vendorHelper.sendTermForActivation(vendorTermId, VendorServiceHelper.vendorid);
			vendorHelper.sendBackPOTerm(vendorTermId, VendorServiceHelper.vendorid);
			vendorHelper.updatePOTermAfterSendBack(vendorTermId, contractId, VendorServiceHelper.vendorid);
		} finally {
			vmsHelper.deleteFromVendor(String.valueOf(VendorServiceHelper.vendorid));
			vmsHelper.deleteContract(String.valueOf(contractId));
			vmsHelper.deletePOTerm(String.valueOf(vendorTermId));
		}
	}

	@Test(enabled = true, groups = { "sanity", "Regression", "terms" }, description = "sends a po term for approval")
	public void sendPOForApproval(ITestContext testContext) throws Exception {
		try {
			contractId = vendorHelper.createContract();
			vendorTermId = vendorHelper.createPOTerm(VendorServiceHelper.vendorid);
			vendorHelper.sendTermForActivation(vendorTermId, VendorServiceHelper.vendorid);
			vendorHelper.sendTermForApproval(vendorTermId, VendorServiceHelper.vendorid);
		} finally {
			vmsHelper.deleteFromVendor(String.valueOf(VendorServiceHelper.vendorid));
			vmsHelper.deleteContract(String.valueOf(contractId));
			vmsHelper.deletePOTerm(String.valueOf(vendorTermId));
		}
	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"terms" }, expectedExceptions = IllegalStateException.class, description = "edits an active po term")
	public void editActivePOTerm(ITestContext testContext) throws Exception {
		try {
			contractId = vendorHelper.createContract();
			vendorTermId = vendorHelper.createPOTerm(VendorServiceHelper.vendorid);
			vendorHelper.sendTermForActivation(vendorTermId, VendorServiceHelper.vendorid);
			vendorHelper.sendTermForApproval(vendorTermId, VendorServiceHelper.vendorid);
			vendorHelper.updateActivePOTerm(vendorTermId, contractId, VendorServiceHelper.vendorid);
		} finally {
			vmsHelper.deleteFromVendor(String.valueOf(VendorServiceHelper.vendorid));
			vmsHelper.deleteContract(String.valueOf(contractId));
			vmsHelper.deletePOTerm(String.valueOf(vendorTermId));
		}
	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"terms" }, description = "copy an active po term and activate it. Previous term should get archived.")
	public void copyActivePOTerm(ITestContext testContext) throws Exception {
		int vendorTermId2 = 0;
		try {
			contractId = vendorHelper.createContract();
			vendorTermId = vendorHelper.createPOTerm(VendorServiceHelper.vendorid);
			vendorHelper.sendTermForActivation(vendorTermId, VendorServiceHelper.vendorid);
			vendorHelper.sendTermForApproval(vendorTermId, VendorServiceHelper.vendorid);
			vendorTermId2 = vendorHelper.createPOTerm(VendorServiceHelper.vendorid);
			vendorHelper.sendTermForActivation(vendorTermId2, VendorServiceHelper.vendorid);
			vendorHelper.sendTermForApproval(vendorTermId2, VendorServiceHelper.vendorid);
			Assert.assertEquals(vmsHelper.getStatusFromPOTerm(vendorTermId), "ARCHIVED",
					"Expected status to be ARCHIVED");
		} finally {
			vmsHelper.deleteFromVendor(String.valueOf(VendorServiceHelper.vendorid));
			vmsHelper.deleteContract(String.valueOf(contractId));
			vmsHelper.deletePOTerm(String.valueOf(vendorTermId));
			vmsHelper.deletePOTerm(String.valueOf(vendorTermId2));
		}
	}

	@Test(enabled = true, groups = { "sanity", "Regression", "terms" }, description = "archives an active po term")
	public void archiveActivePOTerm(ITestContext testContext) throws Exception {
		try {
			contractId = vendorHelper.createContract();
			vendorTermId = vendorHelper.createPOTerm(VendorServiceHelper.vendorid);
			vendorHelper.sendTermForActivation(vendorTermId, VendorServiceHelper.vendorid);
			vendorHelper.sendTermForApproval(vendorTermId, VendorServiceHelper.vendorid);
			vendorHelper.archivePOTerm(vendorTermId, contractId, VendorServiceHelper.vendorid);
		} finally {
			vmsHelper.deleteFromVendor(String.valueOf(VendorServiceHelper.vendorid));
			vmsHelper.deleteContract(String.valueOf(contractId));
			vmsHelper.deletePOTerm(String.valueOf(vendorTermId));
		}
	}

	@Test(enabled = true, groups = { "sanity", "Regression", "terms" }, description = "creates a new moq term")
	public void createNewMoqTerm(ITestContext testContext) throws Exception {
		try {
			moqTermId = vendorHelper.createMoqTerm(poVendorId);
		} finally {
			vmsHelper.deleteMoqTerm(String.valueOf(moqTermId));
		}
	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"terms" }, description = "searches moq term between multiple partners")
	public void searchMoqBetweenMultiplePartners(ITestContext testContext) throws Exception {
		try {
			moqTermId = vendorHelper.createMoqTerm(poVendorId);
			vendorHelper.searchMoqBetweenMultiplePartners();
		} finally {
			vmsHelper.deleteMoqTerm(String.valueOf(moqTermId));
		}
	}

	@Test(enabled = true, groups = { "sanity", "Regression", "terms" }, description = "sends a moq term for activation")
	public void sendMoqForActivation(ITestContext testContext) throws Exception {
		try {
			contractId = vendorHelper.createContract();
			moqTermId = vendorHelper.createMoqTerm(VendorServiceHelper.vendorid);
			vendorHelper.sendMoqTermForActivation(moqTermId, VendorServiceHelper.vendorid);
		} finally {
			vmsHelper.deleteFromVendor(String.valueOf(VendorServiceHelper.vendorid));
			vmsHelper.deleteContract(String.valueOf(contractId));
			vmsHelper.deleteMoqTerm(String.valueOf(moqTermId));
		}
	}

	@Test(enabled = true, groups = { "sanity", "Regression", "terms" }, description = "does bulk upload of a csv file")
	public void bulkUpload(ITestContext testContext) throws Exception {
		BulkUploadHelper bulkHelper = new BulkUploadHelper(testContext);
		String username = testContext.getSuite().getParameter("unityUsername");
		String password = testContext.getSuite().getParameter("unityPassword");
		String filename = testContext.getSuite().getParameter("bulkUploadFile");
		bulkHelper.doBulkUpload(username, password, filename);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() throws Exception {
		vmsHelper.deleteFromVendor(String.valueOf(poVendorId));
		vmsHelper.deleteContract(String.valueOf(poContractId));
	}

}
