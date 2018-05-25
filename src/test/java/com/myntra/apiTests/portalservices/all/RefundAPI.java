package com.myntra.apiTests.portalservices.all;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.RefundAPIsDP;
import org.apache.commons.codec.binary.Hex;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class RefundAPI extends RefundAPIsDP {
	
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(BankAPIs.class);
	static String Signature;
	APIUtilities apiUtil = new APIUtilities();
	
	@Test(groups = {}, dataProvider = "refundAPIDataProvider")
	public void PaymentService_refundAPI_verifySuccessResponse(
			String orderID, String clientTransactionId, String amount, String client, String version) {
		Signature = generateSignature(client, version, orderID,clientTransactionId,amount);

		String[] PayloadParams = new String[] { orderID, clientTransactionId,amount,client, version,
				Signature };
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PAYMENTSERVICE, APINAME.REFUNDAPI,
				init.Configurations, PayloadParams, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("Refund API is not working", 200,
				req.response.getStatus());

	//	String statusMessage = req.respvalidate.NodeValue("status", true)
	//			.replaceAll("\"", "").trim();
	//	log.info(statusMessage);
	//	System.out.println(statusMessage);
	//	AssertJUnit.assertTrue("Status Message doesn't match",
	//			statusMessage.contains("SUCCESS"));
	}
	
	private String generateSignature(String client, String version,
			String orderID, String clientTransactionId, String amount) {

		String Signature = "";
		String parameter = client + "|" + version + "|" + orderID + "|" + clientTransactionId
				+ "|" + amount + "|" + client;
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
