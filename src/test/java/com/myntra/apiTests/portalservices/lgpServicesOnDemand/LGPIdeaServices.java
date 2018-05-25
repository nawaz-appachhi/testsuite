package com.myntra.apiTests.portalservices.lgpServicesOnDemand;


import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.dataproviders.LGPIdeaServicesDP;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import net.minidev.json.JSONArray;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * LGP- Idea Services tests. Basically covers the Update Profile API.
 * @author suhas.kashyap
 *
 */
public class LGPIdeaServices extends LGPIdeaServicesDP {

	public static Initialize init = new Initialize("/Data/configuration");
	static Logger logger = Logger.getLogger(LGPIdeaServices.class);
	static APIUtilities utilities = new APIUtilities();
	private static IdeaApiHelper helper = new IdeaApiHelper();



	/**
	 * validate the appname attribute in the Update profile API.
	 * @param inputForAppname -All the required values for the request.
	 * @param outputForAppname -All the verification values in the response.
	 * @throws IOException
	 * @throws InterruptedException
	 * @author Suhas.Kashyap
	 */
	@Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" },dataProvider = "ideaAppNameDP",priority = 1)
	public void appNameValidationUpdateProfile(ArrayList<String> inputForAppname,ArrayList<String> outputForAppname)throws IOException, InterruptedException{
		validateRequest(inputForAppname,outputForAppname);
	}

	/**
	 * validates the UIDX attribute in the Update Profile API.
	 * @param inputForUidx -All the required values for the request.
	 * @param outputForUidx -All the verification values in the response.
	 * @throws IOException
	 * @throws InterruptedException
	 * @author Suhas.Kashyap
	 */
	@Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" },dataProvider = "ideaUIDXDP",priority = 2)
	public void uidxValidationUpdateProfile(ArrayList<String> inputForUidx,ArrayList<String> outputForUidx)throws IOException, InterruptedException{
		validateRequest(inputForUidx,outputForUidx);
	}

	/**
	 * hit the Update Profile API and Verifies the response for the attributes email and phone.
	 * @param input -All the required values for the request.
	 * @param output -All the verification values in the response.
	 * @param payloadContains
	 * @throws IOException
	 * @throws InterruptedException
	 * @author Suhas.Kashyap
	 */
	public void validate(ArrayList<String> input,ArrayList<String> output,String payloadContains)throws IOException, InterruptedException{
		String payloadFile = "";
		if(payloadContains.equalsIgnoreCase("Email and Phone")){
			payloadFile=utilities.readFileAsString("./Data/payloads/JSON/lgp/idea/profile");
			payloadFile=utilities.preparepayload(payloadFile,new String[] {input.get(0),input.get(1),input.get(2),input.get(3)});
		}
		else if(payloadContains.equalsIgnoreCase("Email")){
			payloadFile=utilities.readFileAsString("./Data/payloads/JSON/lgp/idea/profileEmailOnly");
			payloadFile=utilities.preparepayload(payloadFile,new String[] {input.get(0),input.get(1),input.get(2)});
		}
		else if(payloadContains.equalsIgnoreCase("Phone")){
			payloadFile=utilities.readFileAsString("./Data/payloads/JSON/lgp/idea/profilePhoneOnly");
			payloadFile=utilities.preparepayload(payloadFile,new String[] {input.get(0),input.get(1),input.get(2)});
		}
		System.out.println(input);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA,
				APINAME.IDEAUPDATEPROFILEINFO, init.Configurations,payloadFile);
		RequestGenerator request = null;
		request=  new RequestGenerator(service);
		System.out.println("\nService URL---->"+service.URL);
		String resp = request.respvalidate.returnresponseasstring();
		System.out.println("Response-------------\n:"+resp);
		AssertJUnit.assertEquals("Status not equal to "+output.get(0), Integer.parseInt(output.get(0)),request.response.getStatus());
		AssertJUnit.assertEquals("Status message is not equal to "+output.get(1),output.get(1).toLowerCase(), JsonPath.read(resp,"$.status.statusMessage").toString().toLowerCase());
		AssertJUnit.assertEquals("Status Type is not equal to "+output.get(2),output.get(2).toLowerCase(), JsonPath.read(resp,"$.status.statusType").toString().toLowerCase());
		AssertJUnit.assertEquals("Status Code is not equal to "+output.get(3),output.get(3).toLowerCase(), JsonPath.read(resp,"$.status.statusCode").toString().toLowerCase());
		if(output.get(1).equalsIgnoreCase("success")){
			ArrayList<String> phones = null;
			ArrayList<String> emails = null;
			if(payloadContains.equalsIgnoreCase("Email and Phone")){
				phones = JsonPath.read(resp,"$.entry.phoneDetails..phone");
				emails = JsonPath.read(resp,"$.entry.emailDetails..email");
			}
			else if(payloadContains.equalsIgnoreCase("Email"))
				emails = JsonPath.read(resp,"$.entry.emailDetails..email");
			else if(payloadContains.equalsIgnoreCase("phone"))
				phones = JsonPath.read(resp,"$.entry.phoneDetails..phone");
			int index = (payloadContains.equalsIgnoreCase("Email and Phone")) ? 3: 2 ;
			if((payloadContains.equalsIgnoreCase("Email and Phone")||payloadContains.equalsIgnoreCase("Phone"))){ 
				AssertJUnit.assertTrue("Phone Number Found in the response", true);
				System.out.println("Phone Number found at the index:"+phones.indexOf(input.get(index)));
				if(((JSONArray) JsonPath.read(resp,"$.entry.phoneDetails["+phones.indexOf(input.get(index))+"]..primary")).toJSONString().replace("[", "").replace("]", "").equalsIgnoreCase("true"))	
					AssertJUnit.assertTrue("It is a Primary Phone", true);
				else
					AssertJUnit.assertFalse("It is not a Primary Phone", true);
			} 
			else{
				System.out.println("\nPhone not found:\n"+phones+"\n"+input.get(index));
			}

			if((payloadContains.equalsIgnoreCase("Email and Phone")||payloadContains.equalsIgnoreCase("Email")) && emails.contains(input.get(2))){
				AssertJUnit.assertTrue("Email Found in the response", true);
				System.out.println("Email found at the index:"+emails.indexOf(input.get(2)));
				if(((JSONArray) JsonPath.read(resp,"$.entry.emailDetails["+emails.indexOf(input.get(2))+"]..primary")).toJSONString().replace("[", "").replace("]", "").equalsIgnoreCase("true"))
					AssertJUnit.assertTrue("It is a Primary Email", true);
				else
					AssertJUnit.assertFalse("It is not a Primary Email", true);
			} else{
				System.out.println("\nEmail not found:\n"+emails+"\n"+input.get(2));
			}
		}
		else if(output.get(1).equalsIgnoreCase("Error occurred while updating/processing data") && payloadContains.equalsIgnoreCase("Email and Phone")){
			if(output.get(4).contains(",")){
				String[] errorCodes = output.get(4).split(",");
				String respCode1 = JsonPath.read(resp,"$.causeErrorCodes[0]").toString();
				String respCode2 = JsonPath.read(resp,"$.causeErrorCodes[1]").toString();
				if(errorCodes[0].equals(respCode1) ||errorCodes[0].equals(respCode2))
					AssertJUnit.assertTrue("Found the Error Code "+errorCodes[0]+" in the response",true);
				else
					AssertJUnit.assertTrue(" Not found the Error Code "+errorCodes[0]+" in the response",false);
				if(errorCodes[1].equals(respCode1) ||errorCodes[1].equals(respCode2))
					AssertJUnit.assertTrue("Found the Error Code "+errorCodes[1]+" in the response",true);
				else
					AssertJUnit.assertTrue(" Not found the Error Code "+errorCodes[1]+" in the response",false);
			}
			else{
				AssertJUnit.assertEquals("Cause Error code are not matching", output.get(4),JsonPath.read(resp,"$.causeErrorCodes[0]").toString());
			}
		}
		else if(output.get(1).equalsIgnoreCase("Error occurred while updating/processing data") && (payloadContains.equalsIgnoreCase("Email") || payloadContains.equalsIgnoreCase("Phone") )){
			AssertJUnit.assertEquals("Cause Error code are not matching", output.get(4),JsonPath.read(resp,"$.causeErrorCodes[0]").toString());
		}

	}

	/**
	 * Validates the email attribute in Update Profile API.
	 * @param input -All the required values for the request.
	 * @param output -All the verification values in the response.
	 * @throws IOException
	 * @throws InterruptedException
	 * @author Suhas.Kashyap
	 */
	@Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" },dataProvider = "ideaEmailOnlyDP", priority = 3)
	public void validateEmailUpdateProfile(ArrayList<String> input,ArrayList<String> output)throws IOException, InterruptedException{
		validate(input,output,"Email");
	}

	/**
	 * Validates the Phone and email attributes When used together, in the update Profile API
	 * @param input -All the required values for the request.
	 * @param output -All the verification values in the response.
	 * @throws IOException
	 * @throws InterruptedException
	 * @author Suhas.Kashyap
	 */
	@Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" },dataProvider = "ideaEmailPhoneDP", priority = 5)
	public void validateEmailPhoneUpdateProfile(ArrayList<String> input,ArrayList<String> output)throws IOException, InterruptedException{
		validate(input,output,"Email and Phone");
	}

	/**
	 * Validates the phone attribute in the Update Profile API
	 * @param input -All the required values for the request.
	 * @param output -All the verification values in the response.
	 * @throws IOException
	 * @throws InterruptedException
	 * @author Suhas.Kashyap
	 */
	@Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" },dataProvider = "ideaPhoneOnlyDP", priority = 4)
	public void validatePhoneUpdateProfile(ArrayList<String> input,ArrayList<String> output)throws IOException, InterruptedException{
		validate(input,output,"phone");
	}

	/**
	 * Hit the Update Profile API and Validates the response and used in verifying the appname and UIDX attributes.
	 * @param input -All the required values for the request.
	 * @param output -All the verification values in the response.
	 * @throws IOException
	 * @throws InterruptedException
	 * @author Suhas.Kashyap
	 */
	public void validateRequest(ArrayList<String> input,ArrayList<String> output)throws IOException, InterruptedException{
		String payloadFile=utilities.readFileAsString("./Data/payloads/JSON/lgp/idea/profile");
		System.out.println(input);
		payloadFile=utilities.preparepayload(payloadFile,new String[] {input.get(0),input.get(1),input.get(2),input.get(3)});
		MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA,
				APINAME.IDEAUPDATEPROFILEINFO, init.Configurations,payloadFile);

		RequestGenerator request =  new RequestGenerator(service);
		System.out.println("\nService URL---->"+service.URL);
		String resp = request.respvalidate.returnresponseasstring();
		System.out.println("Response-------------\n:"+resp);
		AssertJUnit.assertEquals("Status not equal to "+output.get(0), Integer.parseInt(output.get(0)),request.response.getStatus());
		AssertJUnit.assertEquals("Status message is not equal to "+output.get(1),output.get(1).toLowerCase(), JsonPath.read(resp,"$.status.statusMessage").toString().toLowerCase());
		AssertJUnit.assertEquals("Status Type is not equal to "+output.get(2),output.get(2).toLowerCase(), JsonPath.read(resp,"$.status.statusType").toString().toLowerCase());
		AssertJUnit.assertEquals("Status Code is not equal to "+output.get(3),output.get(3).toLowerCase(), JsonPath.read(resp,"$.status.statusCode").toString().toLowerCase());
	}


	@Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" },dataProvider = "ideaChangeUserStatusDP", priority = 6)
	public void changeUserStatus(Map<String,String> reqData,Map<String,String> resData)throws IOException,InterruptedException{
		String payloadFile=utilities.readFileAsString("./Data/payloads/JSON/lgp/idea/changeUserStat");
		System.out.println(reqData);
		payloadFile=utilities.preparepayload(payloadFile,new String[] {reqData.get("appName"),reqData.get("status"),reqData.get("uidx")});
		MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA,
				APINAME.IDEACHANGEUSERSTATUS, init.Configurations,payloadFile);
		RequestGenerator request =  new RequestGenerator(service);
		System.out.println("\nService URL---->"+service.URL);
		String resp = request.respvalidate.returnresponseasstring();
		System.out.println("\n-----------------Response-------------\n:"+resp);
		AssertJUnit.assertEquals("Status not equal to "+resData.get("status"), Integer.parseInt(resData.get("status")),request.response.getStatus());
		AssertJUnit.assertEquals("Status Code not equal to "+resData.get("statusCode"), resData.get("statusCode").toLowerCase(),JsonPath.read(resp,"$.status.statusCode").toString().toLowerCase());
		AssertJUnit.assertEquals("Status message is not equal to "+resData.get("statusMessage"),resData.get("statusMessage").toLowerCase(), JsonPath.read(resp,"$.status.statusMessage").toString().toLowerCase());
		AssertJUnit.assertEquals("Status Type is not equal to "+resData.get("statusType"),resData.get("statusType").toLowerCase(), JsonPath.read(resp,"$.status.statusType").toString().toLowerCase());
		if(resData.get("statusType").equalsIgnoreCase("Success")){
			AssertJUnit.assertEquals("Account Status Type is not equal to "+resData.get("accountStatus"),resData.get("accountStatus").toLowerCase(), JsonPath.read(resp,"$.entry.status").toString().toLowerCase());
			AssertJUnit.assertEquals("UIDX is not equal to "+resData.get("uidx"),resData.get("uidx").toLowerCase(), JsonPath.read(resp,"$.entry.uidx").toString().toLowerCase());
		}
	}

	@Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" },dataProvider = "ideaEmailToLoginDP", priority = 7)
	public void emailToLogin(Map<String,String> data) throws IOException, InterruptedException, JSONException {
		String payloadFile=utilities.readFileAsString("./Data/payloads/JSON/lgp/idea/emailToLogin");
		System.out.println(data);
		payloadFile = utilities.preparepayload(payloadFile, new String[] {data.get("mailID")});
		MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA,
				APINAME.IDEAEMAILTOLOGIN, init.Configurations,payloadFile);
		RequestGenerator request = new RequestGenerator(service);
		System.out.println("\nService URL---->"+service.URL);
		String resp = request.respvalidate.returnresponseasstring();
		System.out.println("\n-----------------Response-------------\n:"+resp);
		AssertJUnit.assertEquals("Status not equal to "+data.get("status"), Integer.parseInt(data.get("status")),request.response.getStatus());
		AssertJUnit.assertEquals("Status Code not equal to "+data.get("statusCode"), data.get("statusCode").toLowerCase(),JsonPath.read(resp,"$.status.statusCode").toString().toLowerCase());
		AssertJUnit.assertEquals("Status message is not equal to "+data.get("statusMessage"),data.get("statusMessage").toLowerCase(), JsonPath.read(resp,"$.status.statusMessage").toString().toLowerCase());
		AssertJUnit.assertEquals("Status Type is not equal to "+data.get("statusType"),data.get("statusType").toLowerCase(),(JsonPath.read(resp,"$.status.statusType").toString()).toLowerCase());
		if(data.get("statusType").equalsIgnoreCase("Success")){
			String path = JsonPath.read(resp, "$.emailToLoginMap").toString();
			JSONObject jsonObj = new JSONObject(path);
			@SuppressWarnings("unchecked")
			Iterator<String> attributeIterator =   jsonObj.keys();
			Map<String,String> keyValuePair = new LinkedHashMap<>();
			while(attributeIterator.hasNext()){
				String attribute = attributeIterator.next();
				String value = jsonObj.get(attribute).toString();
				keyValuePair.put(attribute, value);
			}
			boolean emailLinksToUIDX = true;
			for(String key : keyValuePair.keySet()){
				String reqByUID =helper.invokeIdeaGetProfileByUserId("myntra",keyValuePair.get(key) ).respvalidate.returnresponseasstring(); 
				System.out.println(reqByUID);
				if( data.get("linked").equals("false") && keyValuePair.get(key).equals("null") ){
					AssertJUnit.assertEquals("Status Message is not equal to No Results Found", "No results found",JsonPath.read(reqByUID,"$.status.statusMessage").toString());
				}
				else{
					if(JsonPath.read(reqByUID,"$..emailDetails..email").toString().replace("[\"", "").replace("\"]", "").equals(key)){
						System.out.println("UIDX for the mail ID: "+keyValuePair.get(key)+" is linked Properly");
						logger.info("UIDX for the mail ID: "+keyValuePair.get(key)+" is linked Properly");
					}
					else{
						emailLinksToUIDX = false;
						System.out.println("UIDX for the mail ID: "+keyValuePair.get(key)+" is not linked properly");
						logger.info("UIDX for the mail ID: "+keyValuePair.get(key)+" is not linked properly");
					}
				}
			}

			AssertJUnit.assertTrue((emailLinksToUIDX)?"All the emailIDs are linked properly":"All the emailIDs are not linked properly",emailLinksToUIDX);
		}
	}

/**
 * Verifies the myntra init password reset API. validates the default 3 days validity and verify the token by hitting reset password and then signin
 * @param request data required for the request payload
 * @param response data which needs to be validated in the response
 * @throws IOException
 * @throws InterruptedException
 * @author Suhas.Kashyap
 */
	@Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" },dataProvider = "ideaMyntraInitPasswordRequestDP", priority = 8)
	public void verifyMyntraInitPasswordReset(HashMap<String,String> request,HashMap<String,String> response)throws IOException,InterruptedException{
		String payloadFile=utilities.readFileAsString("./Data/payloads/JSON/lgp/idea/myntraInitPasswordReset");
		System.out.println(request);
		payloadFile = utilities.preparepayload(payloadFile, new String[] {request.get("appName"),request.get("email")});
		MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA,
				APINAME.IDEAMYNTRAINITPASSWORDRESET, init.Configurations,payloadFile);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		cal.add(Calendar.DATE, 3);
		String constructedDate = dateFormat.format(cal.getTime());
		RequestGenerator requestGen = new RequestGenerator(service);
		System.out.println("\nService URL---->"+service.URL);
		String resp = requestGen.respvalidate.returnresponseasstring();
		System.out.println("\n-----------------Response-------------\n:"+resp);
		AssertJUnit.assertEquals("Status not equal to "+response.get("Status"), Integer.parseInt(response.get("Status")),requestGen.response.getStatus());
		AssertJUnit.assertEquals("Status Code not equal to "+response.get("StatusCode"), response.get("StatusCode").toLowerCase(),JsonPath.read(resp,"$.status.statusCode").toString().toLowerCase());
		AssertJUnit.assertEquals("Status message is not equal to "+response.get("StatusMessage"),response.get("StatusMessage").toLowerCase(), JsonPath.read(resp,"$.status.statusMessage").toString().toLowerCase());
		AssertJUnit.assertEquals("Status Type is not equal to "+response.get("StatusType"),response.get("StatusType").toLowerCase(),(JsonPath.read(resp,"$.status.statusType").toString()).toLowerCase());
		if(response.get("StatusType").equalsIgnoreCase("Success")){
			AssertJUnit.assertEquals("App Name is not equal to "+request.get("appName"),request.get("appName").trim(),(JsonPath.read(resp,"$.entry.appName").toString()));
			AssertJUnit.assertEquals("Email ID is not equal to "+request.get("email"),request.get("email"),(JsonPath.read(resp,"$.entry.email").toString()));
			String dateInResp = JsonPath.read(resp,"$.entry.validity").toString();
			Date respDate = null;
			try {
				respDate = dateFormat.parse(dateInResp);
			} catch (ParseException e2) {

				e2.printStackTrace();
			}
			String constructedRespDate = dateFormat.format(respDate);
			AssertJUnit.assertEquals("Validity is not equal to 3 days", constructedDate,constructedRespDate);
			//java.sql.Connection con = null;
			try{
				/*
				Class.forName("com.mysql.jdbc.Driver");
				String dbDetail ="";
				if(env.equals("d7"))
					dbDetail = "jdbc:mysql://"+"delta7mdb-public"+"/idea?"+"user=MyntStagingUser&password=9eguCrustuBR1!&port=3306";
				else if(env.equals("pp1"))
					dbDetail = "jdbc:mysql://"+"pp1mdb.myntra.com"+"/idea?"+"user=MyntStagingUser&password=9eguCrustuBR1!&port=3306";
				else
					dbDetail = "jdbc:mysql://"+"ideadb2.myntra.com"+"/idea?"+"user=MyntraIDEAUser&password=I88rPEWkTw9c4Tz&port=3307";
				con = DriverManager.getConnection(dbDetail);
				//Statement statmnt = con.createStatement();
				PreparedStatement st = con.prepareStatement("select reset_key from otp_request where email =\""+request.get("email")+"\" and created_on=(select MAX(created_on) from otp_request limit 1);");
				ResultSet rs = st.executeQuery();
				
				//ResultSet rs = statmnt.executeQuery("select reset_key from otp_request where email ="+request.get("email")+" and created_on=(select MAX(created_on) from otp_request limit 1);");
				rs.next();
				*/
				/*System.out.println("*Compare tokens*");
				System.out.println("DB token:"+rs.getString(1));
				System.out.println("Response token:"+(JsonPath.read(resp,"$.entry.resetPasswordLink").toString()));
				AssertJUnit.assertEquals("Reset key is incorrect",rs.getString(1),(JsonPath.read(resp,"$.entry.resetPasswordLink").toString()));
				*/
				//con.close();
				
				String[] token = JsonPath.read(resp,"$.entry.resetPasswordLink").toString().split("key=");
				String accesskeyValue = "Reset"+getcurrentTimestamp();
				String payloadForResetPassword="{"
						+ "\"appName\":\"myntra\","
						+ "\"resetKey\":\""+token[1]+"\", "
						+ "\"accessKey\":\""+accesskeyValue+"\""
						+ "}";
				MyntraService serv = Myntra.getService(ServiceType.PORTAL_IDEA,
						APINAME.IDEARESETPASSWORDBYEMAIL, init.Configurations,payloadForResetPassword);
				RequestGenerator reqst = new RequestGenerator(serv);
				AssertJUnit.assertEquals(200, reqst.response.getStatus());
				if(!(JsonPath.read(reqst.respvalidate.returnresponseasstring(),"$.status.statusType").toString().equalsIgnoreCase("Success")))
					AssertJUnit.assertTrue(false);
				String signInPayload = "{"
						+"\"appName\":\"myntra\","
						+"\"accessKey\":\""+accesskeyValue+"\","
						+"\"emailId\":\""+request.get("email")+"\""
						+"}";
				MyntraService signinService = Myntra.getService(ServiceType.PORTAL_IDEA,
						APINAME.IDEASIGNINUSINGEMAIL, init.Configurations,signInPayload);
				RequestGenerator signinRequest = new RequestGenerator(signinService);
				AssertJUnit.assertEquals(200, signinRequest.response.getStatus());
				AssertJUnit.assertEquals("Status message is not equal to Success","success", JsonPath.read(signinRequest.respvalidate.returnresponseasstring(),"$.status.statusMessage").toString().toLowerCase());

			} catch( Exception e) {
				e.getMessage();
				/*if(con != null)
					try {
						con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}*/
				AssertJUnit.assertTrue(e.getMessage(), false);
			}
		}
	}

	/**
	 * verifies the get last password request API
	 * @param inputValues Attribute values which are used in request
	 * @param outputValues Attribute values which needs to be validated in response.
	 * @throws IOException
	 * @throws InterruptedException
	 * @author Suhas.Kashyap
	 */
	@Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" },dataProvider = "ideaLastPasswordDp", priority = 9)
	public void verifyGetLastPasswordRequest(HashMap<String,String> inputValues, HashMap<String,String> outputValues)throws IOException,InterruptedException{
		String payloadFile=utilities.readFileAsString("./Data/payloads/JSON/lgp/idea/lastPassword");
		System.out.println(inputValues);
		payloadFile = utilities.preparepayload(payloadFile, new String[] {inputValues.get("appName"),inputValues.get("email")});
		MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA,
				APINAME.IDEALASTPASSWORD, init.Configurations,payloadFile);
		RequestGenerator requestGen = new RequestGenerator(service);
		System.out.println("\nService URL---->"+service.URL);
		String resp = requestGen.respvalidate.returnresponseasstring();
		System.out.println("\n-----------------Response-------------\n:"+resp);
		AssertJUnit.assertEquals("Status not equal to "+outputValues.get("Status"), Integer.parseInt(outputValues.get("Status")),requestGen.response.getStatus());
		if(outputValues.get("Status").equals("200")){
			java.sql.Connection con = null;
			String env ="";
			try{
				Class.forName("com.mysql.jdbc.Driver");
				String dbDetail ="";
				if(env.equals("d7"))
					dbDetail = "jdbc:mysql://"+"delta7mdb-public"+"/idea?"+"user=MyntStagingUser&password=9eguCrustuBR1!&port=3306";
				else if(env.equals("pp1"))
					dbDetail = "jdbc:mysql://"+"pp1mdb.myntra.com"+"/idea?"+"user=MyntStagingUser&password=9eguCrustuBR1!&port=3306";
				else
					dbDetail = "jdbc:mysql://"+"ideadb2.myntra.com"+"/idea?"+"user=MyntraIDEAUser&password=I88rPEWkTw9c4Tz&port=3307";
				con = DriverManager.getConnection(dbDetail);

				PreparedStatement st = con.prepareStatement("select created_on,last_modified_on,ends_on from otp_request where email =\""+inputValues.get("email")+"\" and created_on=(select MAX(created_on) from otp_request limit 1);");
				ResultSet rs = st.executeQuery();

				rs.next();
				System.out.println(rs.getString("created_on")+", "+rs.getString("last_modified_on")+", "+rs.getString("ends_on"));
				//AssertJUnit.assertEquals("Status not equal to "+outputValues.get("Status"), Integer.parseInt(outputValues.get("Status")),requestGen.response.getStatus());
				AssertJUnit.assertEquals("Status Code not equal to "+outputValues.get("StatusCode"), outputValues.get("StatusCode").toLowerCase(),JsonPath.read(resp,"$.status.statusCode").toString().toLowerCase());
				AssertJUnit.assertEquals("Status message is not equal to "+outputValues.get("StatusMessage"),outputValues.get("StatusMessage").toLowerCase(), JsonPath.read(resp,"$.status.statusMessage").toString().toLowerCase());
				AssertJUnit.assertEquals("Status Type is not equal to "+outputValues.get("StatusType"),outputValues.get("StatusType").toLowerCase(),(JsonPath.read(resp,"$.status.statusType").toString()).toLowerCase());
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
				if(outputValues.get("StatusType").equalsIgnoreCase("Success")){
					AssertJUnit.assertEquals("App Name is not equal to "+inputValues.get("appName"),inputValues.get("appName").trim(),(JsonPath.read(resp,"$.entry.appName").toString()));
					AssertJUnit.assertEquals("Email is not equal to "+inputValues.get("email"),inputValues.get("email").trim(),(JsonPath.read(resp,"$.entry.email").toString()));
					AssertJUnit.assertEquals("Created date is not equal to the created date in db",dateFormat.format(rs.getString("created_on")),(JsonPath.read(resp,"$.entry.lastRequestCreatedOn").toString()));
					AssertJUnit.assertEquals("Modified date is not equal to the modified date in db",dateFormat.format(rs.getString("last_modified_on")),(JsonPath.read(resp,"$.entry.lastRequestModifiedOn").toString()));
					AssertJUnit.assertEquals("Validity is not equal to the Validity in db",dateFormat.format(rs.getString("ends_on")),(JsonPath.read(resp,"$.entry.validity").toString()));
				}
				con.close();
			} catch( Exception e) {
				e.getMessage();
				if(con != null)
					try {
						con.close();
					} catch (SQLException excep) {

						excep.printStackTrace();
					}
			}
		}
	}


	/**
	 * verifies the get last password request API for the improper payload.
	 * @param input Attribute values which are used in request
	 * @param output Attribute values which needs to be validated in response.
	 * @throws IOException
	 * @throws InterruptedException
	 * @author Suhas.Kashyap
	 */
	@Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" },dataProvider = "ideaLastPasswordInproperDp", priority = 10)
	public void verifyGetLastPasswordRequestImproperPayload(LinkedHashMap<String,String> input,HashMap<String,String> output)throws IOException,InterruptedException{
		String payload="";
		boolean payloadReq = false;
		boolean ignorecomma =true;
		@SuppressWarnings("rawtypes")
		Iterator iterator = input.entrySet().iterator();
		while(iterator.hasNext()){
			@SuppressWarnings("unchecked")
			Map.Entry<String, String> keyValue = (Map.Entry<String, String>)iterator.next();
			if(keyValue.getKey().equalsIgnoreCase("payloadRequired") && keyValue.getValue().equalsIgnoreCase("true")){
				payload ="{\n";
				payloadReq = true;
			}
			else if( !keyValue.getKey().equalsIgnoreCase("payloadRequired")){
				payload = payload +"\t\""+keyValue.getKey()+"\":\""+keyValue.getValue()+"\"";
				ignorecomma = false;
			}
			if(iterator.hasNext() && !ignorecomma)
				payload = payload+",\n";
		}
		if(payloadReq)
			payload = payload+"\n}";
		System.out.println(input);
		System.out.println(payload);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA,
				APINAME.IDEALASTPASSWORD, init.Configurations,payload);
		RequestGenerator requestGen = new RequestGenerator(service);
		System.out.println("\nService URL---->"+service.URL);
		String resp = requestGen.respvalidate.returnresponseasstring();
		System.out.println("\n-----------------Response-------------\n:"+resp);
		AssertJUnit.assertEquals(Integer.parseInt(output.get("Status")), requestGen.response.getStatus());
		if(output.get("Status").equals("200")){
			AssertJUnit.assertEquals("Status Code not equal to "+output.get("StatusCode"), output.get("StatusCode").toLowerCase(),JsonPath.read(resp,"$.status.statusCode").toString().toLowerCase());
			AssertJUnit.assertEquals("Status message is not equal to "+output.get("StatusMessage"),output.get("StatusMessage").toLowerCase(), JsonPath.read(resp,"$.status.statusMessage").toString().toLowerCase());
			AssertJUnit.assertEquals("Status Type is not equal to "+output.get("StatusType"),output.get("StatusType").toLowerCase(),(JsonPath.read(resp,"$.status.statusType").toString()).toLowerCase());	
		}
	}

	@Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" },dataProvider = "ideaSignOut", priority = 11)
	public void ValidateSignOut(HashMap<String,String> headers,HashMap<String,String> resData)throws IOException,InterruptedException{

		HashMap<String, String> tokensSignout = new HashMap<String, String>();
		tokensSignout.put("xid",headers.get("xid"));
		tokensSignout.put("xsrfToken",headers.get("xsrfToken"));

		HashMap<String, String> tokensRefresh = new HashMap<String, String>();
		tokensRefresh.put("xid",headers.get("xid"));

		String payloadFile=utilities.readFileAsString("./Data/payloads/JSON/lgp/idea/idpSignOut");
		//System.out.println("\n \n \n response data is --- "+resData);

		payloadFile = utilities.preparepayload(payloadFile, new String[] {headers.get("AT"),headers.get("RT")});
		MyntraService service = Myntra.getService(ServiceType.PORTAL_IDP,
				APINAME.SIGNOUT, init.Configurations,payloadFile);
		//System.out.println("HERE!!!!the payloadFile for sign out "+payloadFile);
		//System.out.println("the headers are"+headers);
		RequestGenerator request =  new RequestGenerator(service,tokensSignout);
		System.out.println("\nService URL---->"+service.URL);
		String resp = request.respvalidate.returnresponseasstring();
		//System.out.println("\n-----------------Response for sign out-------------\n:"+resp);

		String payloadFile2=utilities.readFileAsString("./Data/payloads/JSON/lgp/idea/ideamobilesecurerefresh");
		payloadFile2=utilities.preparepayload(payloadFile2,new String[] {headers.get("AT2"),headers.get("RT2")});
		MyntraService service2 = Myntra.getService(ServiceType.PORTAL_IDEA,
				APINAME.IDEAMOBILESECUREREFRESH, init.Configurations,payloadFile2);
		//System.out.println("HERE!!!!the payloadFile2 is"+payloadFile2);
		RequestGenerator request2 =  new RequestGenerator(service2);
		System.out.println("\nService URL3---->"+service2.URL);
		String resp2 = request2.respvalidate.returnresponseasstring();
		//System.out.println("\n-----------------Response for refresh-------------\n:"+resp2);

		AssertJUnit.assertEquals("Status not equal to "+resData.get("status"), Integer.parseInt(resData.get("status")),request2.response.getStatus());
		AssertJUnit.assertEquals("Status Code not equal to "+resData.get("statusCode"), resData.get("statusCode").toLowerCase(),JsonPath.read(resp2,"$.status.statusCode").toString().toLowerCase());
		AssertJUnit.assertEquals("Status message is not equal to "+resData.get("statusMessage"),resData.get("statusMessage").toLowerCase(), JsonPath.read(resp2,"$.status.statusMessage").toString().toLowerCase());
		AssertJUnit.assertEquals("Status Type is not equal to "+resData.get("statusType"),resData.get("statusType").toLowerCase(), JsonPath.read(resp2,"$.status.statusType").toString().toLowerCase());

	}


	/**
	 * Linking the user to the client
	 * @param req information for the request call
	 * @param resp information which needs to be validated in the response
	 * @author Suhas.Kashyap
	 */
	@Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" },dataProvider = "linkunlinkUserClient", priority = 12)
	public void linkUserToClient(HashMap<String,String> req,HashMap<String,String> resp){
		System.out.println("request data: "+req+"\nresponse data: "+resp);
		String payloadData = "{\n";
		for(String key: req.keySet()){
			if(! key.equalsIgnoreCase("TC"))
				payloadData = payloadData + "\""+key+"\":"+req.get(key)+",\n";
		}
		String rev ="";
		for(int i=payloadData.length()-1; i>=0;i--)
			rev = rev+payloadData.charAt(i);
		rev = rev.replaceFirst(",", "");
		payloadData = "";
		for(int i=rev.length()-1; i>=0;i--)
			payloadData = payloadData+rev.charAt(i);
		payloadData = payloadData+"}";
		MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA,
				APINAME.LINKUSERCLIENT, init.Configurations,payloadData);

		AssertJUnit.assertTrue("Mapping is incorrect",clintMapping(service,resp,req,"linkUser"));
	}


	/**
	 * Unlinking the user from the client
	 * @param req information for the request call
	 * @param resp information which needs to be validated in the response
	 * @author Suhas.Kashyap
	 */
	/*
	@Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" },dataProvider = "linkunlinkUserClient", priority = 13)
	public void unlinkUserToClient(HashMap<String,String> req,HashMap<String,String> resp){
		System.out.println("request data: "+req+"\nresponse data: "+resp);
		String payloadData = "{\n";
		for(String key: req.keySet()){
			if(! key.equalsIgnoreCase("TC"))
			payloadData = payloadData + "\""+key+"\":"+req.get(key)+",\n";
		}
		String rev ="";
		for(int i=payloadData.length()-1; i>=0;i--)
			rev = rev+payloadData.charAt(i);
		rev = rev.replaceFirst(",", "");
		payloadData = "";
		for(int i=rev.length()-1; i>=0;i--)
			payloadData = payloadData+rev.charAt(i);
		payloadData = payloadData+"}";
		MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA,
				APINAME.UNLINKUSERCLIENT, init.Configurations,payloadData);

		AssertJUnit.assertTrue("Mapping is incorrect",clintMapping(service,resp,req,"unlinkUser"));
	}
	 */

	/**
	 * This method is used to link and unlink the user and client. It calls the getuidx api to make sure the linking/unlinking 
	 * @param service MyntraService object which contains the end point to be hit.
	 * @param resp data which needs to be validated in the response.
	 * @param req data for the getUidx api
	 * @param type string value which should be linkUser or unlinkUser
	 * @return true if successful, false otherwise.
	 * @author Suhas.Kashyap
	 */
	public boolean clintMapping(MyntraService service,HashMap<String,String> resp,HashMap<String,String> req,String type){
		boolean ans = false;
		RequestGenerator rGRes = new RequestGenerator(service);
		System.out.println("\nURL---->>> "+ service.URL);
		System.out.println("\nResponse---->>> "+ rGRes.respvalidate.returnresponseasstring());
		String res = rGRes.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals("Status is not equal", Integer.parseInt(resp.get("status")),rGRes.response.getStatus());
		if(!resp.get("status").equals("200"))
			ans = true;
		if((resp.entrySet().size())>1){
			AssertJUnit.assertEquals("Status Code not equal to "+resp.get("statusCode"), resp.get("statusCode"),JsonPath.read(res,"$.status.statusCode").toString());
			AssertJUnit.assertEquals("Status message is not equal to "+resp.get("statusMessage"),resp.get("statusMessage").toLowerCase(), JsonPath.read(res,"$.status.statusMessage").toString().toLowerCase());
			AssertJUnit.assertEquals("Status Type is not equal to "+resp.get("statusType"),resp.get("statusType").toLowerCase(), JsonPath.read(res,"$.status.statusType").toString().toLowerCase());
			ans = true;
		}
		else if(type.equals("linkUser") && resp.get("status").equals("200") ){
			String msg = JsonPath.read(res,"$.status.statusMessage").toString();
			boolean assertValue = false;
			if(msg.equalsIgnoreCase("Success") || msg.equalsIgnoreCase("User client link already exists"))
				assertValue = true;
			AssertJUnit.assertTrue("Status message is not equal to either Success or User Client link already exists",assertValue);
			MyntraService serviceGetPro = Myntra.getService(ServiceType.PORTAL_IDEA,APINAME.GETUIDX,init.Configurations);
			serviceGetPro.URL = utilities.prepareparameterizedURL(serviceGetPro.URL,new String[] {req.get("uidx").replace("\"", "")});
			RequestGenerator resGetPro = new RequestGenerator(serviceGetPro);
			System.out.println("Get Profile URL::::"+serviceGetPro.URL);
			System.out.println("Get Profile Response :::::"+resGetPro.respvalidate.returnresponseasstring());
			AssertJUnit.assertEquals("Status is not equal to 200", 200,resGetPro.response.getStatus());
			String response = resGetPro.respvalidate.returnresponseasstring();
			String cMap = JsonPath.read(response, "$.entry.clientMap").toString();
			try{
				JSONObject clientMap = new JSONObject(cMap);
				//AssertJUnit.assertTrue(clientMap.has(req.get("clientName").replace("\"","").trim()));
				//JSONObject cntMap = clientMap.getJSONObject(req.get("clientName"));
				//AssertJUnit.assertTrue(clientMap.has("\""+req.get("clientName")+"\""));
				Set keys = (Set) clientMap.keys();
				//AssertJUnit.assertTrue(keys.contains(""+req.get("clientName")));
				Iterator ite = keys.iterator();
				boolean present = false;
				String value = req.get("clientName").replace("\"","").trim();
				while(ite.hasNext()){
					String str = ite.next().toString();
					if(str.equalsIgnoreCase(value))
						present = true;
				}
				AssertJUnit.assertTrue(present);
				ans = true;
			} catch(Exception e){
				ans = false;
			}
		}
		else if(type.equals("unlinkUser") && resp.get("status").equals("200") ){
			String msg = JsonPath.read(res,"$.status.statusMessage").toString();
			boolean assertValue = false;
			if(msg.equalsIgnoreCase("Success") || msg.equalsIgnoreCase("User client link doesn't exists"))
				assertValue = true;
			AssertJUnit.assertTrue("Status message is not equal to either Success or User Client link already exists",assertValue);
			MyntraService serviceGetPro = Myntra.getService(ServiceType.PORTAL_IDEA,APINAME.GETUIDX,init.Configurations);
			serviceGetPro.URL = utilities.prepareparameterizedURL(serviceGetPro.URL,new String[] {req.get("uidx").replace("\"", "")});
			RequestGenerator resGetPro = new RequestGenerator(serviceGetPro);
			AssertJUnit.assertEquals("Status is not equal to 200", 200,resGetPro.response.getStatus());
			String response = resGetPro.respvalidate.returnresponseasstring();
			boolean key = true;
			try{
				JSONObject clientMap = new JSONObject(JsonPath.read(response, "$.entry.clientMap").toString());
				key = clientMap.has(req.get("clientName"));
				if(key)
					AssertJUnit.assertFalse(true);
				else
					ans = true;
			} catch( Exception e){
				AssertJUnit.assertTrue(true);
				ans = true;
			}
		}

		return ans;
	}
}
