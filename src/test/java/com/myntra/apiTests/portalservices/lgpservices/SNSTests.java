package com.myntra.apiTests.portalservices.lgpservices;

import java.util.ArrayList;
import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.LgpServDP;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

import net.minidev.json.JSONArray;

public class SNSTests extends LgpServDP {

	public static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SNSTests.class);
	String uidx;
	static LgpServicesHelper lgpServiceHelper = new LgpServicesHelper();
	String xid;

	static HashMap<String, String> headers = new HashMap<String, String>();
	static APIUtilities utilities = new APIUtilities();
	static String username;
	static String password;
	//public static String environment = init.Configurations.GetTestEnvironemnt().toString();
	String environment = System.getenv("environment");

	@BeforeClass(alwaysRun = true)
	public void setUp() {
		/*
		 * username=System.getenv("username");
		 * password=System.getenv("password");
		 */
		String xid = null;

		if (null == username && environment.toLowerCase().contains("fox7")) {
			username = "iosapp@myntra.com";
			password = "qwerty";
			headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json");
			/*
			 * xid="JJN024d35d85bdbd224c9a87c23f07fe7961261438937423M";
			 * uidx="970794db.228e.4133.933e.1574198ef08frA7cPl428x";
			 */
		} else if (null == username
				&& environment.toLowerCase().contains("production")) {
			username = "iosapp10@myntra.com";
			password = "qwerty";
			headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json");
			/*
			 * xid=""; uidx="";
			 */
		} else if (null == username
				&& environment.toLowerCase().contains("stage")) {
			username = "iosapp10@myntra.com";
			password = "qwerty";
			headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json");
			/*
			 * xid=""; uidx="";
			 */
		} else if (null == username
				&& environment.toLowerCase().contains("sfqa")) {
			username = "iosapp10@myntra.com";
			password = "qwerty";
			headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json");
			/*
			 * xid=""; uidx="";
			 */
		}
		// try {
		// xid = lgpServiceHelper.getXidForCredentials(username, password);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// try {
		// uidx=lgpServiceHelper.getUidxForCredential(username, password);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// headers.put("xid", xid);

	}

	@Test(dataProvider = "influencersDefaultDP", groups = "Sanity")
	public void influencersDefaultTests(HashMap<String, ArrayList<String>> values) {
		
		System.out.println(values);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.GETINFLUENCERSDEFAULT,init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(2));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)), expectedValues.get(2));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(2));
		if (expectedValues.get(0).equals("1051")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].userId"), "Atleast one UserID needs to retrieved");
		}

	}

	@Test(dataProvider = "influencersFetchSizeDP", groups = "Sanity")
	public void influencersFetchSizeTests(HashMap<String, ArrayList<String>> values) {
		
		System.out.println(values);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.GETINFLUENCERSFETCHSIZE,init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString(), expectedValues.get(0).toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(2)),expectedValues.get(5));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.totalCount")), Integer.parseInt(expectedValues.get(1)),expectedValues.get(5));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(3)),expectedValues.get(5));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(4),expectedValues.get(5));
		
		if (expectedValues.get(0).equals("1051")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].userId"), "Atleast one UserID needs to retrieved");
		}

	}

	@Test(dataProvider = "influencersFetchSizeDPBadErrorCode", groups = "Sanity")
	public void influencersFetchSizeTestsBadErrorCode(HashMap<String, ArrayList<String>> values) {
		System.out.println(values);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.GETINFLUENCERSFETCHSIZE,init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString(), expectedValues.get(0).toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(1)),expectedValues.get(2));

	}

	@Test(dataProvider = "influencersOffsetDP", groups = "Sanity")
	public void influencersOffsetTests(HashMap<String, ArrayList<String>> values) {
		System.out.println(values);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.GETINFLUENCERSOFFSET,init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString(), expectedValues.get(0).toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(2)),expectedValues.get(5));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(3)),expectedValues.get(5));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(4),expectedValues.get(5));
		if (expectedValues.get(0).equals("1051")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].userId"), "Atleast one UserID needs to retrieved");
		}
	}

	@Test(dataProvider = "influencersOffsetDPBadErrorCode", groups = "Sanity")
	public void influencersOffsetTestsBadErrorCode(HashMap<String, ArrayList<String>> values) {
		System.out.println(values);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.GETINFLUENCERSFETCHSIZE,init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString(), expectedValues.get(0).toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(1)),expectedValues.get(2));

	}

	@Test(dataProvider = "influencersDP", groups = "Sanity")
	public void influencersTests(HashMap<String, ArrayList<String>> values) {
		System.out.println(values);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.GETINFLUENCERSSNS,init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString(), expectedValues.get(0).toString(),expectedValues.get(1).toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(3)),expectedValues.get(6));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.totalCount").toString()), Integer.parseInt(expectedValues.get(2)),expectedValues.get(6));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(4)),expectedValues.get(6));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(5),expectedValues.get(6));
		if (expectedValues.get(0).equals("1051")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].userId"), "Atleast one UserID needs to retrieved");
		}
	}

	@Test(dataProvider = "influencersDPBadErrorCode", groups = "Sanity")
	public void influencersTestsBadErrorCode(HashMap<String, ArrayList<String>> values) {
		System.out.println(values);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.GETINFLUENCERSSNS,init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString(), expectedValues.get(0).toString(),expectedValues.get(1).toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(2)),
				expectedValues.get(3));

	}

	@Test(dataProvider = "followingDefaultDP", groups = "Sanity")
	public void followingTests(HashMap<String, ArrayList<String>> values) {
		System.out.println(values);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.GETFOLLOWINGSNS,init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(2));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(2));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(2));
		if (expectedValues.get(0).equals("1003")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].userId"), "Atleast one UserID needs to retrieved");
		}

	}

	@Test(dataProvider = "followersDefaultDP", groups = "Sanity")
	public void followersTests(HashMap<String, ArrayList<String>> values) {
		System.out.println(values);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.GETFOLLOWERSSNS,init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(2));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(2));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(2));
		if (expectedValues.get(0).equals("1003")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].userId"), "Atleast one UserID needs to retrieved");
		}

	}

	@Test(dataProvider = "topicsToFollowDP", groups = "Sanity")
	public void topicsToFollowTests(HashMap<String, ArrayList<String>> values) {
		
		System.out.println(values);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.GETTOPICSTOFOLLOWSNS,init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(2));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(2));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(2));
		if (expectedValues.get(0).equals("1003")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].userId"), "Atleast one UserID needs to retrieved");
		}

	}

	@Test(dataProvider = "topicsAffinitiesDP", groups = "Sanity")
	public void topicsAffinitiesTests(HashMap<String, ArrayList<String>> values) {
		
		System.out.println(values);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.GETTOPICSAFFINITIESSNS,init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(2));
		Assert.assertEquals(JsonPath.read(response, "$.data.userId"), expectedValues.get(1), expectedValues.get(2));
		Assert.assertEquals(JsonPath.read(response, "$.data.profileGender"), expectedValues.get(2), expectedValues.get(2));
		Assert.assertNotNull(JsonPath.read(response, "$.data.tagAffinityEntries"),"Atleast one tag affinites needs to retrieved");

	}

	@Test(dataProvider = "performActionUserBulkDP", groups = "Sanity")
	public void performActionUserBulkTests(HashMap<String, String[]> values) {
		
		String[] payload = values.get(values.keySet().toArray()[0].toString());
		String scenario = values.keySet().toArray()[0].toString().split(":")[1];
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.PERFORMACTIONONUSERBULKSNS,init.Configurations, payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString().split(":")[0] });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		if (scenario.equals("A")) {
			
			Assert.assertNotNull(response, "Perform Action on User Bulk API Failed");
		} else {
			
			Assert.assertEquals(response, "[ ]", "Perform Action on User Bulk API Failed");
		}
		Assert.assertEquals(requestGen.response.getStatus(), 200, "Perform Action on User Bulk API Failed");
	}

	@Test(dataProvider = "contactsToFollowDP", groups = "Sanity")
	public void contactsToFollowTests(HashMap<String, ArrayList<String>> values) {
		
		System.out.println(values);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.GETCONTACTSTOFOLLOWSNS,init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(2));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(2));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(2));
		if (expectedValues.get(0).equals("1003")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].userId"), "Atleast one UserID needs to retrieved");
		}
	}

	@Test(dataProvider = "performActionUserSingleDP", groups = "Sanity")
	public void performActionUserSingleTests(HashMap<String, ArrayList<String>> values) {
		System.out.println(values);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.PERFORMACTIONONUSERSINGLESNS,init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString().split(":")[0],values.keySet().toArray()[0].toString().split(":")[1] });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(2));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(2));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(2));

	}

	@Test(dataProvider = "performActionObjectBulkDP", groups = "Sanity")
	public void performActionObjectBulkTests(HashMap<String, String[]> values) {
		
		String[] payload = values.get(values.keySet().toArray()[0].toString());
		String scenario = values.keySet().toArray()[0].toString().split(":")[1];
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.PERFORMACTIONONOBJECTBULKSNS,init.Configurations, payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString().split(":")[0] });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		if (scenario.equals("A")) {
			Assert.assertNotNull(response, "Perform Action on User Bulk API Failed");
		} else {
			Assert.assertEquals(response, "[ ]", "Perform Action on User Bulk API Failed");
		}
		Assert.assertEquals(requestGen.response.getStatus(), 200, "Perform Action on User Bulk API Failed");
	}

	@Test(dataProvider = "performActionObjectSingleDP", groups = "Sanity")
	public void performActionObjectSingleTests(HashMap<String, ArrayList<String>> values) {
		
		System.out.println(values);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE,APINAME.PERFORMACTIONONOBJECTSINGLESNS, init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString().split(",")[0],values.keySet().toArray()[0].toString().split(",")[1] });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(2));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(2));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(2));

	}

	@Test(dataProvider = "whoForObjectDP", groups = "Sanity")
	public void getWhoForAnObjectTests(HashMap<String, ArrayList<String>> values) {
		
		System.out.println(values);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.GETWHOFOROBJECTSNS,init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString().split(":")[0],values.keySet().toArray()[0].toString().split(":")[1] });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(2));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(2));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(2));
		if (expectedValues.get(0).equals("1016")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].id"), "Atleast one UserID needs to retrieved");
		}
	}

	@Test(dataProvider = "getObjectByTagDB", groups = "Sanity")
	public void getObjectByTagTests(HashMap<String, ArrayList<String>> values) {
		System.out.println(values);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.GETOBJECTBYTAGSNS,init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(2));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(2));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(2));
		if (expectedValues.get(0).equals("1043")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].appId"), "Atleast one appId needs to retrieved");
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].refId"), "Atleast one refID needs to retrieved");
		}
	}

	@Test(dataProvider = "getTagsDB", groups = "Sanity")
	public void getTagsTests(HashMap<String, ArrayList<String>> values) {
		System.out.println(values);
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_SNSSERVICE, APINAME.GETTAGSSNS,
				init.Configurations);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,
				new String[] { values.keySet().toArray()[0].toString().split(":")[0],
						values.keySet().toArray()[0].toString().split(":")[1],
						values.keySet().toArray()[0].toString().split(":")[2] });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),
				expectedValues.get(2));
		if (Integer.parseInt(expectedValues.get(1)) > 0) {
			JSONArray data = JsonPath.read(response, "$.data");
			Assert.assertEquals(data.size(), Integer.parseInt(expectedValues.get(1)), expectedValues.get(2));
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].name"), "Atleast one name needs to retrieved");
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].type"), "Atleast type name needs to retrieved");
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].followEnabled"),
					"Atleast followEnabled name needs to retrieved");
		}
	}

}
