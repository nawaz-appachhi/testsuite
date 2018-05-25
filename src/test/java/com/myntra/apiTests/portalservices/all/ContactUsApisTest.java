package com.myntra.apiTests.portalservices.all;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.ContactUsDataProvider;
import com.myntra.apiTests.InitializeFramework;
import com.myntra.apiTests.portalservices.crmservices.CRMPortalService;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.lordoftherings.gandalf.ResponseValidator;
import com.myntra.apiTests.common.ServiceType;

public class ContactUsApisTest extends ContactUsDataProvider {

	static Logger log = Logger.getLogger(CRMPortalService.class);

	Initialize init = InitializeFramework.init;
	APIUtilities apiUtil = new APIUtilities();
	public ResponseValidator respvalidate;

	public static String incidentid;

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "ContactUs_SendQueryDataProvider")
	public void Contactus_SendQuery_verifySuccessResponse(String level1,
			String level2, String emailid, String orderid, String notes) {

		RequestGenerator sendqueryReqGen = invokesendquery(level1, level2, emailid,
				orderid, notes);
	

		String sendQueryResponse = sendqueryReqGen.ResponseValidations.GetResponseAsString();
		

		System.out.println("\nIn master method:.....Printing Createsendquery API response :\n\n"
				+ sendQueryResponse + "\n");
		log.info("\nPrinting Createsendquery API response :\n\n"
				+ sendQueryResponse + "\n");

		AssertJUnit.assertEquals("Createsendquery API is not working", 200,
				sendqueryReqGen.response.getStatus());

	}

	private RequestGenerator invokesendquery(String level1, String level2,
			String emailid, String orderid, String notes) {

		String sendqueryPayload = invokesendqueryPayloadURLEncoded(level1,
				level2, emailid, orderid, notes);
		
		
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU=");
		/*HashMap<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Content-Type", "Application/xml");
		headersMap.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU=");*/
		
		MyntraService sendqueryService = Myntra.getService(
				ServiceType.PORTAL_CONTACTUS, APINAME.SENDQUERY,
				init.Configurations, new String[] { sendqueryPayload },
				PayloadType.URLENCODED, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(sendqueryService,
				getParam);
		
		String sendqueryResponse = req.ResponseValidations.GetResponseAsString();
		System.out
				.println("Send query Payload ............" + sendqueryPayload);

		System.out.println("\nPrinting Createsendquery API URL :"
				+ sendqueryService.URL);
		log.info("\nPrinting Createsendquery API URL :" + sendqueryService.URL);

		System.out.println("\nPrinting Createsendquery  API Payload :\n\n"
				+ sendqueryService.Payload);
		log.info("\nPrinting Createsendquery API Payload :\n\n" + sendqueryService.Payload);
		System.out.println("printing requestgenerator..............."+req.respvalidate.returnresponseasstring());
		//return sendqueryPayload;
		return req;

	}

	private String getPayloadAsString1(String payloadName,
			String[] payloadparams) {
		String customPayloadDir = System.getProperty("user.dir")
				+ File.separator + "Data" + File.separator + "payloads"
				+ File.separator + "URLENCODED";
		StringBuffer sb = new StringBuffer();
		String finalPayload = "";
		Scanner sc = null;
		try {
			sc = new Scanner(new File(customPayloadDir + File.separator
					+ payloadName));
			while (sc.hasNextLine())
				sb.append(sc.nextLine() + "\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (null != sc)
				sc.close();
		}
		finalPayload = sb.toString();
		for (int i = 1; i <= payloadparams.length; i++) {
			finalPayload = finalPayload.replace("${" + i + "}",
					payloadparams[i - 1]);
		}
		// finalPayload = finalPayload.replace("${0}", incidentid);
		return finalPayload;
	}

	public RequestGenerator getincidentidinpayload1(String level1,
			String level2, String emailid, String orderid, String notes,
			String incidentid) {
		String[] PayloadParams = new String[] { level1, level2, emailid,
				orderid, notes };
		String finalPayload = getPayloadAsString1("contactusincident",
				PayloadParams);
		MyntraService getincidentService = Myntra.getService(
				ServiceType.PORTAL_CONTACTUS, APINAME.CONTACTUSINCIDENT,
				init.Configurations, finalPayload);
		getincidentService.payloaddataformat = PayloadType.XML;
		getincidentService.responsedataformat = PayloadType.JSON;
		getincidentService.URL = apiUtil.prepareparameterizedURL(
				getincidentService.URL, incidentid);

		System.out
				.println("\nPrinting getincidentService createSession API URL : "
						+ getincidentService.URL);
		log.info("\nPrinting getincidentService  API URL : "
				+ getincidentService.URL);

		System.out.println("\nPrinting getincidentService  API Payload : \n\n"
				+ getincidentService.Payload);
		log.info("\nPrinting getincidentService  API Payload : \n\n"
				+ getincidentService.Payload);

		HashMap<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Content-Type", "Application/xml");

		return new RequestGenerator(getincidentService, headersMap);
	}

	private String invokesendqueryPayloadURLEncoded(String level1,
			String level2, String emailid, String orderid, String notes) {
		StringBuffer encodedURL = new StringBuffer();
		encodedURL.append("level1api.eq:");
		encodedURL.append(level1.trim());

		encodedURL.append("___level2api.eq:");
		encodedURL.append(level2.trim());

		encodedURL.append("___emailid.eq:");
		encodedURL.append(emailid.trim());

		encodedURL.append("___orderid.eq:");
		encodedURL.append(orderid.trim());

		encodedURL.append("___notes.eq:");
		encodedURL.append(notes.trim());

		return encodedURL.toString();
	}

}
