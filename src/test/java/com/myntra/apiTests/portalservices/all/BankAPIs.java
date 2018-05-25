package com.myntra.apiTests.portalservices.all;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.myntra.apiTests.common.Myntra;
import org.apache.commons.codec.binary.Hex;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.myntra.apiTests.dataproviders.BankAPIsDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class BankAPIs extends BankAPIsDP {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(BankAPIs.class);
	static String Signature;
	APIUtilities apiUtil = new APIUtilities();

	@Test(groups = {}, dataProvider = "saveBankDetailsDataProvider")
	public void PaymentService_SaveBankDetails_verifySuccessResponse(
			String login, String bank, String branch, String accountType,
			String accountNumber, String accountName, String ifsc,
			String client, String version) {
		String Signature = "";
		String parameter = client + "|" + version + "|" + login + "|" + bank
				+ "|" + branch + "|" + accountType + "|" + accountNumber + "|"
				+ accountName + "|" + ifsc + "|" + client;
		System.out.println("Parameter Generated :" + parameter);

		try {
			Signature = computeSHA512(new String(parameter));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Signature Generated Key:" + Signature);

		String[] PayloadParams = new String[] { login, bank, branch,
				accountType, accountNumber, accountName, ifsc, client, version,
				Signature };
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PAYMENTSERVICE, APINAME.SAVEBANKDETAILS,
				init.Configurations, PayloadParams, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("Save Bank Details API is not working", 200,
				req.response.getStatus());

	}

	@Test(groups = {}, dataProvider = "saveBankDetailsDataProvider")
	public void PaymentService_SaveBankDetails_verifyStatusMessage(
			String login, String bank, String branch, String accountType,
			String accountNumber, String accountName, String ifsc,
			String client, String version) {

		Signature = generateSignature(client, version, login, bank, branch,
				accountType, accountNumber, accountName, ifsc);

		String[] PayloadParams = new String[] { login, bank, branch,
				accountType, accountNumber, accountName, ifsc, client, version,
				Signature };
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PAYMENTSERVICE, APINAME.SAVEBANKDETAILS,
				init.Configurations, PayloadParams, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("Save Bank Details API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate.NodeValue("status", true)
				.replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("SUCCESS"));

		String accountId = req.respvalidate.NodeValue("accountId", true)
				.replaceAll("\"", "").trim();
		System.out.println("Account ID Captured in response is :" + accountId);
	}

	@Test(groups = {}, dataProvider = "getBankDetailsWithAccountIDDataProvider")
	public void PaymentService_getBankDetailsWithAccountID_verifySuccessResponse(
			String login, String accountID, String client, String version) {
		Signature = generateSignature(client, version, login, accountID);

		String[] PayloadParams = new String[] { login, accountID, client, version,
				Signature };
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PAYMENTSERVICE, APINAME.GETBANKDETAILSWITHACCOUNTID,
				init.Configurations, PayloadParams, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("Get Bank Details API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate.NodeValue("status", true)
				.replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("SUCCESS"));
	}
	
	@Test(groups = {}, dataProvider = "getBankDetailsWithoutAccountIDDataProvider")
	public void PaymentService_getBankDetailsWithoutAccountID_verifySuccessResponse(
			String login, String client, String version) {
		Signature = generateSignature(client, version, login);

		String[] PayloadParams = new String[] { login, client, version,
				Signature };
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PAYMENTSERVICE, APINAME.GETBANKDETAILSWITHOUTACCOUNTID,
				init.Configurations, PayloadParams, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("Get Bank Details API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate.NodeValue("status", true)
				.replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("SUCCESS"));
	}

	@Test(groups = {}, dataProvider = "updateBankNameDetailDataProvider")
	public void PaymentService_updateBankNameDetails_verifySuccessResponse(
			String login, String accountId, String bankName, String client, String version) {
		Signature = generateSignature(client, version, login,accountId,bankName);

		String[] PayloadParams = new String[] { login, accountId,bankName,client, version,
				Signature };
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PAYMENTSERVICE, APINAME.UPDATEBANKNAME,
				init.Configurations, PayloadParams, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("Update Bank Details API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate.NodeValue("status", true)
				.replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("SUCCESS"));
	}
	
	@Test(groups = {}, dataProvider = "updateBranchNameDetailDataProvider")
	public void PaymentService_updateBranchNameDetails_verifySuccessResponse(
			String login, String accountId, String branchName, String client, String version) {
		Signature = generateSignature(client, version, login,accountId,branchName);

		String[] PayloadParams = new String[] { login, accountId,branchName,client, version,
				Signature };
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PAYMENTSERVICE, APINAME.UPDATEBRANCHNAME,
				init.Configurations, PayloadParams, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("Update Bank Details API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate.NodeValue("status", true)
				.replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("SUCCESS"));
	}
	
	@Test(groups = {}, dataProvider = "updateAccountTypeDataProvider")
	public void PaymentService_updateAccountTypeDetails_verifySuccessResponse(
			String login, String accountId, String accountType, String client, String version) {
		Signature = generateSignature(client, version, login,accountId,accountType);

		String[] PayloadParams = new String[] { login, accountId,accountType,client, version,
				Signature };
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PAYMENTSERVICE, APINAME.UPDATEACCOUNTTYPE,
				init.Configurations, PayloadParams, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("Update Bank Details API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate.NodeValue("status", true)
				.replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("SUCCESS"));
	}
	
	// Test Case is getting failed
	@Test(groups = {}, dataProvider = "updateAccountNumberDataProvider")
	public void PaymentService_updateAccountNumber_verifySuccessResponse(
			String login, String accountId, String accountNumber, String client, String version) {
		Signature = generateSignature(client, version, login,accountId,accountNumber);

		String[] PayloadParams = new String[] { login, accountId,accountNumber,client, version,
				Signature };
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PAYMENTSERVICE, APINAME.UPDATEACCOUNTNUMBER,
				init.Configurations, PayloadParams, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("Update Bank Details API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate.NodeValue("status", true)
				.replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("SUCCESS"));
	}
	
	@Test(groups = {}, dataProvider = "updateAccountNameDataProvider")
	public void PaymentService_updateAccountName_verifySuccessResponse(
			String login, String accountId, String accountName, String client, String version) {
		Signature = generateSignature(client, version, login,accountId,accountName);

		String[] PayloadParams = new String[] { login, accountId,accountName,client, version,
				Signature };
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PAYMENTSERVICE, APINAME.UPDATEACCOUNTNAME,
				init.Configurations, PayloadParams, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("Update Bank Details API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate.NodeValue("status", true)
				.replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("SUCCESS"));
	}
	
	@Test(groups = {}, dataProvider = "updateIFSCCodeDataProvider")
	public void PaymentService_updateIFSCCode_verifySuccessResponse(
			String login, String accountId, String IFSC, String client, String version) {
		Signature = generateSignature(client, version, login,accountId,IFSC);

		String[] PayloadParams = new String[] { login, accountId,IFSC,client, version,
				Signature };
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PAYMENTSERVICE, APINAME.UPDATEIFSCCODE,
				init.Configurations, PayloadParams, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("Update Bank Details API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate.NodeValue("status", true)
				.replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("SUCCESS"));
	}
	
	@Test(groups = {}, dataProvider = "deleteBankDataProvider")
	public void PaymentService_deleteBank_verifySuccessResponse(
			String login, String accountId, String client, String version) {
		Signature = generateSignature(client, version, login,accountId);

		String[] PayloadParams = new String[] { login, accountId,client, version,
				Signature };
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PAYMENTSERVICE, APINAME.DELETEBANK,
				init.Configurations, PayloadParams, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("Update Bank Details API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate.NodeValue("status", true)
				.replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("SUCCESS"));
	}
	

	private String generateSignature(String client, String version,
			String login, String bank, String branch, String accountType,
			String accountNumber, String accountName, String ifsc) {

		String Signature = "";
		String parameter = client + "|" + version + "|" + login + "|" + bank
				+ "|" + branch + "|" + accountType + "|" + accountNumber + "|"
				+ accountName + "|" + ifsc + "|" + client;
		System.out.println("Parameter Generated :" + parameter);

		try {
			Signature = computeSHA512(new String(parameter));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Signature Generated Key:" + Signature);
		return (Signature);

	}

	private String generateSignature(String client, String version,
			String login, String accountID) {

		String Signature = "";
		String parameter = client + "|" + version + "|" + login + "|"
				+ accountID + "|" + client;
		System.out.println("Parameter Generated :" + parameter);

		try {
			Signature = computeSHA512(new String(parameter));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Signature Generated Key:" + Signature);
		return (Signature);

	}
	
	private String generateSignature(String client, String version,
			String login, String accountID, String param) {

		String Signature = "";
		String parameter = client + "|" + version + "|" + login + "|"
				+ accountID + "|" + param + "|" + client;
		System.out.println("Parameter Generated :" + parameter);

		try {
			Signature = computeSHA512(new String(parameter));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Signature Generated Key:" + Signature);
		return (Signature);

	}

	private String generateSignature(String client, String version, String login) {

		String Signature = "";
		String parameter = client + "|" + version + "|" + login + "|" + client;
		System.out.println("Parameter Generated :" + parameter);

		try {
			Signature = computeSHA512(new String(parameter));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Signature Generated Key:" + Signature);
		return (Signature);

	}

	private String computeSHA512(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] hashBytes = md.digest(data.getBytes());
		return Hex.encodeHexString(hashBytes);
	}

}