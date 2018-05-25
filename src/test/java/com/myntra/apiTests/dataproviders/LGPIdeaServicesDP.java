package com.myntra.apiTests.dataproviders;


import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;

import javax.ws.rs.core.MultivaluedMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.lgpservices.LgpPumpsHelper;
import org.testng.annotations.DataProvider;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;




/**
 *  Data Provider class for LGPIdeaServices
 * @author Suhas.Kashyap
 *
 */
public class LGPIdeaServicesDP extends LgpPumpsHelper {

	static APIUtilities utilities = new APIUtilities();
	static Initialize init = new Initialize("/Data/configuration");
	private static enum status{
		ACTIVE,
		INACTIVE,
		ACCOUNT_SUSPENDED,
		BLACKLISTED;
	}
	
	
	/**
	 * Used for appName attribute verification. Positive and Negative test values
	 * @return dataSet object of all the positive and negative values.
	 * @author Suhas.Kashyap
	 */
	@DataProvider
	public Object[][] ideaAppNameDP(){
		
		Object[][] dataSet = null;
		
			/* Positive cases */
			 ArrayList<String> req1 = new ArrayList<String>();
			 req1.add("myntra");
			 req1.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req1.add("automationUpdateEmailID"+getcurrentTimestamp()+"@myntra.com");
			 req1.add("2323232323");
			 ArrayList<String> res1 = new ArrayList<String>();
			 res1.add("200");
			 res1.add("Success");
			 res1.add("success");
			 res1.add("3");
			 
			/*Negative cases*/
			 ArrayList<String> req2 = new ArrayList<String>();
			 req2.add("myn");
			 req2.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req2.add("automationUpdateEmailID1@myntra.com");
			 req2.add("2323232323");
			 ArrayList<String> res2 = new ArrayList<String>();
			 res2.add("200");
			 res2.add("Invalid App");
			 res2.add("Error");
			 res2.add("111020");
			
			ArrayList<String> req3 = new ArrayList<String>();
			 req3.add("@^&*!");
			 req3.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req3.add("automationUpdateEmailID1@myntra.com");
			 req3.add("2323232323");
			 ArrayList<String> res3 = new ArrayList<String>();
			 res3.add("200");
			 res3.add("Invalid App");
			 res3.add("Error");
			 res3.add("111020");
			 
			ArrayList<String> req4 = new ArrayList<String>();
			 req4.add("");
			 req4.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req4.add("automationUpdateEmailID1@myntra.com");
			 req4.add("2323232323");
			 ArrayList<String> res4 = new ArrayList<String>();
			 res4.addAll(res3);
			 
			ArrayList<String> req5 = new ArrayList<String>();
			 req5.add(" ");
			 req5.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req5.add("automationUpdateEmailID1@myntra.com");
			 req5.add("2323232323");
			 ArrayList<String> res5 = new ArrayList<String>();
			 res5.addAll(res3);
			
			ArrayList<String> req6 = new ArrayList<String>();
			 req6.add("      ");
			 req6.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req6.add("automationUpdateEmailID1@myntra.com");
			 req6.add("2323232323");
			 ArrayList<String> res6 = new ArrayList<String>();
			 res6.addAll(res3);
			
			ArrayList<String> req7 = new ArrayList<String>();
			 req7.add(" myntra");
			 req7.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req7.add("automationUpdateEmailID1@myntra.com");
			 req7.add("2323232323");
			 ArrayList<String> res7 = new ArrayList<String>();
			 res7.addAll(res1);
			
			ArrayList<String> req8 = new ArrayList<String>();
			 req8.add("myntra ");
			 req8.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req8.add("automationUpdateEmailID1@myntra.com");
			 req8.add("2323232323");
			 ArrayList<String> res8 = new ArrayList<String>();
			 res8.add("200");
			 res8.add("Email Already Exists");
			 res8.add("Error");
			 res8.add("111014");

			ArrayList<String> req9 = new ArrayList<String>();
			 req9.add(" myntra ");
			 req9.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req9.add("automationUpdateEmailID1@myntra.com");
			 req9.add("2323232323");
			 ArrayList<String> res9 = new ArrayList<String>();
			 res9.addAll(res8);
			
			ArrayList<String> req10 = new ArrayList<String>();
			 req10.add("myn#ra@");
			 req10.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req10.add("automationUpdateEmailID1@myntra.com");
			 req10.add("2323232323");
			 ArrayList<String> res10 = new ArrayList<String>();
			 res10.addAll(res3);
			
			dataSet = new Object[][]{{req1,res1},{req2,res2},{req3,res3},{req4,res4},{req5,res5},{req6,res6},{req7,res7},{req8,res8},{req9,res9},{req10,res10}};
			return dataSet;
	}

	/**
	 * Used for UIDX attribute verification. Positive and Negative test values.
	 * @return dataSet array of positive and negative array values.
	 * @author Suhas.Kashyap
	 */
	@DataProvider
	public Object[][] ideaUIDXDP(){
		
		Object[][] dataSet = null;
		
			/* Positive cases */
			 ArrayList<String> req1 = new ArrayList<String>();
			 req1.add("myntra");
			 req1.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req1.add("automationUpdateEmailID"+getcurrentTimestamp()+"@myntra.com");
			 req1.add("2323232323");
			 ArrayList<String> resSuccess = new ArrayList<String>();
			 resSuccess.add("200");
			 resSuccess.add("Success");
			 resSuccess.add("success");
			 resSuccess.add("3");
			 
			/*Negative cases*/
			 ArrayList<String> req2 = new ArrayList<String>();
			 req2.add(req1.get(0));
			 req2.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQFsuhas");
			 req2.add("automationUpdateEmailID1@myntra.com");
			 req2.add(req1.get(3));
			 ArrayList<String> resFailure = new ArrayList<String>();
			 resFailure.add("200");
			 resFailure.add("Account Doesn't exists");
			 resFailure.add("Error");
			 resFailure.add("111004");
			 
			 ArrayList<String> resFailureAlreadyExists = new ArrayList<String>();
			 resFailureAlreadyExists.add("200");
			 resFailureAlreadyExists.add("Email Already exists");
			 resFailureAlreadyExists.add("Error");
			 resFailureAlreadyExists.add("111014");
			
			ArrayList<String> req3 = new ArrayList<String>();
			 req3.add(req1.get(0));
			 req3.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330");
			 req3.add(req2.get(2));
			 req3.add(req1.get(3));
			
			 
			ArrayList<String> req4 = new ArrayList<String>();
			 req4.add(req1.get(0));
			 req4.add("");
			 req4.add(req2.get(2));
			 req4.add(req1.get(3));
			
			 
			ArrayList<String> req5 = new ArrayList<String>();
			 req5.add(req1.get(0));
			 req5.add(" ");
			 req5.add(req2.get(2));
			 req5.add(req1.get(3));
			
			
			ArrayList<String> req6 = new ArrayList<String>();
			 req6.add(req1.get(0));
			 req6.add("     ");
			 req6.add(req2.get(2));
			 req6.add(req1.get(3));
			 
			
			ArrayList<String> req7 = new ArrayList<String>();
			 req7.add(req1.get(0));
			 req7.add(" b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req7.add(req2.get(2));
			 req7.add(req1.get(3));
			
			
			ArrayList<String> req8 = new ArrayList<String>();
			 req8.add(req1.get(0));
			 req8.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C ");
			 req8.add(req2.get(2));
			 req8.add(req1.get(3));
			
			ArrayList<String> req9 = new ArrayList<String>();
			 req9.add(req1.get(0));
			 req9.add(" b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C ");
			 req9.add(req2.get(2));
			 req9.add(req1.get(3));
			
			
			ArrayList<String> req10 = new ArrayList<String>();
			 req10.add(req1.get(0));
			 req10.add("b44c6cd#.08f4.4^0d.b323.c&36@391d422FlOQF4330@");
			 req10.add(req2.get(2));
			 req10.add(req1.get(3));
			
			 ArrayList<String> req11 = new ArrayList<String>();
			 req11.add(req1.get(0));
			 req11.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330CA");
			 req11.add(req2.get(2));
			 req11.add(req1.get(3));
			 
			 ArrayList<String> req12 = new ArrayList<String>();
			 req12.add(req1.get(0));
			 req12.add("@#$%^&*()*&^%$.^%$#@!");
			 req12.add(req2.get(2));
			 req12.add(req1.get(3));
			
			dataSet = new Object[][]{{req1,resSuccess},{req2,resFailure},{req3,resFailure},{req4,resFailure},{req5,resFailure},{req6,resFailure},{req7,resSuccess},{req8,resFailureAlreadyExists},{req9,resFailureAlreadyExists},{req10,resFailure},{req11,resFailure},{req12,resFailure}};
			return dataSet;
	}

	/**
	 * Used for verifying the Email attribute. Positive and negative test values.
	 * @return dataSet array of objects which contains the positive and negative test values
	 * @author Suhas.Kashyap
	 */
	@DataProvider
	public Object[][] ideaEmailOnlyDP(){
		
		Object[][] dataSet = null;
			
			/* Positive cases */
		String mailId = "automationupdateemailid"+getcurrentTimestamp()+"@myntra.com";
			 ArrayList<String> req1 = new ArrayList<String>();
			 req1.add("myntra");
			 req1.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req1.add(mailId);
			 
			 ArrayList<String> resSuccess = new ArrayList<String>();
			 resSuccess.add("200");
			 resSuccess.add("Success");
			 resSuccess.add("success");
			 resSuccess.add("3");
			 
			/*Negative cases*/
			 ArrayList<String> req2 = new ArrayList<String>();
			 req2.add("myntra");
			 req2.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req2.add(mailId);
			 ArrayList<String> res2 = new ArrayList<String>();
			 res2.add("200");
			 res2.add("email already exists");
			 res2.add("Error");
			 res2.add("111014");
			 
			 ArrayList<String> req3 = new ArrayList<String>();
			 req3.add("myntra");
			 req3.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req3.add("automationUpdateEmailIDmyntra.com");
			 
			 ArrayList<String> resIncorrect = new ArrayList<String>();
			 resIncorrect.add("200");
			 //resIncorrect.add("Incorrect EmailID");
			 resIncorrect.add("error occurred while updating/processing data");
			 resIncorrect.add("Error");
			 resIncorrect.add("58");
			 resIncorrect.add("111012");
			 
			 ArrayList<String> req4 = new ArrayList<String>();
			 req4.add("myntra");
			 req4.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req4.add("automationUpdateEmailID@myntracom");
			 
			 ArrayList<String> req5 = new ArrayList<String>();
			 req5.add("myntra");
			 req5.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req5.add("automationUpdateEmailIDmyntracom");
			 
			 ArrayList<String> req6 = new ArrayList<String>();
			 req6.add("myntra");
			 req6.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req6.add("automationUpdateEmailIDmyntracom");
			 
			 ArrayList<String> req7 = new ArrayList<String>();
			 req7.add("myntra");
			 req7.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req7.add(" automationUpdateEmailID@myntra.com");
			 
			 ArrayList<String> req8 = new ArrayList<String>();
			 req8.add("myntra");
			 req8.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req8.add("automationUpdateEmailID@myntra.com ");
			 
			 ArrayList<String> req9 = new ArrayList<String>();
			 req9.add("myntra");
			 req9.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req9.add(" automationUpdateEmailID@myntra.com ");
			 
			 ArrayList<String> req10 = new ArrayList<String>();
			 req10.add("myntra");
			 req10.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req10.add("");
			 
			 ArrayList<String> req11 = new ArrayList<String>();
			 req11.add("myntra");
			 req11.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req11.add(" ");
			 
			 ArrayList<String> req12 = new ArrayList<String>();
			 req12.add("myntra");
			 req12.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req12.add("      ");
			 
			 ArrayList<String> req13 = new ArrayList<String>();
			 req13.add("myntra");
			 req13.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req13.add("@#$%^&*()$#@^#.^%$#(");
			 
			 ArrayList<String> req14 = new ArrayList<String>();
			 req14.add("myntra");
			 req14.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req14.add("@utomation#mailID"+getcurrentTimestamp()+"@myntra.com");
			dataSet = new Object[][]{{req1,resSuccess},{req2,res2},{req3,resIncorrect},{req4,resIncorrect},{req5,resIncorrect},{req6,resIncorrect},{req7,resIncorrect},{req8,resIncorrect},{req9,resIncorrect},{req10,resIncorrect},{req11,resIncorrect},{req12,resIncorrect},{req13,resIncorrect},{req14,resIncorrect}};
			return dataSet;
	}

/**
 * Used for verifying the Phone attribute.
 * @return dataSet array of the Positive and Negative test values	
 * @author Suhas.kashyap
 */
	@DataProvider
	public Object[][] ideaPhoneOnlyDP(){
		
		Object[][] dataSet = null;
			
			/* Positive cases */
		String phNum = "9876598765";
			 ArrayList<String> req1 = new ArrayList<String>();
			 req1.add("myntra");
			 req1.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req1.add(phNum);
			 
			 ArrayList<String> resSuccess = new ArrayList<String>();
			 resSuccess.add("200");
			 resSuccess.add("Success");
			 resSuccess.add("success");
			 resSuccess.add("3");
			 
			/*Negative cases*/
			 //Not required for now. As the APi accepts the the same number.
			 /*
			 ArrayList<String> req2 = new ArrayList<String>();
			 req2.add("myntra");
			 req2.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req2.add(phNum);
			 ArrayList<String> res2 = new ArrayList<String>();
			 res2.add("200");
			 res2.add("Phone Number already exists");
			 res2.add("Error");
			 res2.add("51");
			 */
			 
			 ArrayList<String> req3 = new ArrayList<String>();
			 req3.add("myntra");
			 req3.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req3.add("98098789899");
			 
			 ArrayList<String> resIncorrect = new ArrayList<String>();
			 resIncorrect.add("200");
			 resIncorrect.add("error occurred while updating/processing data");
			 resIncorrect.add("Error");
			 resIncorrect.add("58");
			 resIncorrect.add("111013");
			 
			 ArrayList<String> req4 = new ArrayList<String>();
			 req4.add("myntra");
			 req4.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req4.add("980987898");
			 
			 ArrayList<String> req5 = new ArrayList<String>();
			 req5.add("myntra");
			 req5.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req5.add("0980987898");
			 
			 ArrayList<String> req6 = new ArrayList<String>();
			 req6.add("myntra");
			 req6.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req6.add("9809878989 ");
			 
			 ArrayList<String> req7 = new ArrayList<String>();
			 req7.add("myntra");
			 req7.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req7.add(" 9809878989");
			 
			 ArrayList<String> req8 = new ArrayList<String>();
			 req8.add("myntra");
			 req8.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req8.add(" 9809878989 ");
			 
			 ArrayList<String> req9 = new ArrayList<String>();
			 req9.add("myntra");
			 req9.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req9.add("");
			 
			 ArrayList<String> req10 = new ArrayList<String>();
			 req10.add("myntra");
			 req10.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req10.add(" ");
			 
			 ArrayList<String> req11 = new ArrayList<String>();
			 req11.add("myntra");
			 req11.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req11.add("          ");
			 
			 ArrayList<String> req12 = new ArrayList<String>();
			 req12.add("myntra");
			 req12.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req12.add("@%$%$#^&*(");
			 
			 ArrayList<String> req13 = new ArrayList<String>();
			 req13.add("myntra");
			 req13.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req13.add("dghjuikjhg");
			 
			 ArrayList<String> req14 = new ArrayList<String>();
			 req14.add("myntra");
			 req14.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req14.add("87678^7889");
			 
			 ArrayList<String> req15 = new ArrayList<String>();
			 req15.add("myntra");
			 req15.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req15.add("+918654787656");
			dataSet = new Object[][]{{req1,resSuccess},{req3,resIncorrect},{req4,resIncorrect},{req5,resIncorrect},{req5,resIncorrect},{req6,resIncorrect},{req7,resIncorrect},{req8,resIncorrect},{req9,resIncorrect},{req10,resIncorrect},{req11,resIncorrect},{req12,resIncorrect},{req13,resIncorrect},{req14,resIncorrect},{req15,resIncorrect}};
			return dataSet;
	}

/**
 * used for email and phone attribute when updating together.
 * @return dataSet object array of positive and negative test values.
 * @author Suhas.Kashyap
 */
	@DataProvider
	public Object[][] ideaEmailPhoneDP(){
		
		Object[][] dataSet = null;
			
			/* Positive cases */
		String mailId = "automationupdateemailid"+getcurrentTimestamp()+"@myntra.com";
		String phoneNum = "8989898989";
			 ArrayList<String> req1 = new ArrayList<String>();
			 req1.add("myntra");
			 req1.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req1.add(mailId);
			 req1.add(phoneNum);
			 
			 ArrayList<String> resSuccess = new ArrayList<String>();
			 resSuccess.add("200");
			 resSuccess.add("Success");
			 resSuccess.add("success");
			 resSuccess.add("3");
			 
			/*Negative cases*/
			 ArrayList<String> req2 = new ArrayList<String>();
			 req2.add("myntra");
			 req2.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req2.add(mailId);
			 req2.add(phoneNum);
			 ArrayList<String> res2 = new ArrayList<String>();
			 res2.add("200");
			 res2.add("email already exists");
			 res2.add("Error");
			 res2.add("111014");
			 
			 ArrayList<String> req3 = new ArrayList<String>();
			 req3.add("myntra");
			 req3.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req3.add("automationUpdateEmailIDmyntra.com");
			 req3.add(phoneNum);
			 
			 ArrayList<String> resIncorrectEmail = new ArrayList<String>();
			 resIncorrectEmail.add("200");
			 resIncorrectEmail.add("error occurred while updating/processing data");
			 resIncorrectEmail.add("Error");
			 resIncorrectEmail.add("58");
			 resIncorrectEmail.add("111012");
			 
			 ArrayList<String> req4 = new ArrayList<String>();
			 req4.add("myntra");
			 req4.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req4.add("automationUpdateEmailID@myntra.com");
			 req4.add("546567876");
			 
			 ArrayList<String> resIncorrectPhone = new ArrayList<String>();
			 resIncorrectPhone.add("200");
			 resIncorrectPhone.add("error occurred while updating/processing data");
			 resIncorrectPhone.add("Error");
			 resIncorrectPhone.add("58");
			 resIncorrectPhone.add("111013");
			 
			 ArrayList<String> req5 = new ArrayList<String>();
			 req5.add("myntra");
			 req5.add("b44c6cd8.08f4.420d.b323.c836a391d422FlOQF4330C");
			 req5.add("automationUpdateEmailIDmyntracom");
			 req5.add("87678987898");
			 
			 ArrayList<String> resIncorrectEmailAndPhone = new ArrayList<String>();
			 resIncorrectEmailAndPhone.add("200");
			 resIncorrectEmailAndPhone.add("error occurred while updating/processing data");
			 resIncorrectEmailAndPhone.add("Error");
			 resIncorrectEmailAndPhone.add("58");
			 resIncorrectEmailAndPhone.add("111012,111013");
			 dataSet = new Object[][]{{req1,resSuccess},{req2,res2},{req3,resIncorrectEmail},{req4,resIncorrectPhone},{req5,resIncorrectEmailAndPhone}};
			return dataSet;
	}

	
	@DataProvider
	public Object[][] ideaChangeUserStatusDP(){
		
		Object[][] dataSet = null;
			
			/* Positive cases */
		Map<String,String> req1 = new HashMap<String,String>();
		req1.put("Sl.No", "1");// For Debugging purpose
		req1.put("appName", "myntra");
		req1.put("status",status.ACTIVE.toString() );
		req1.put("uidx", "cc9f7ab3.c7dc.4eaa.aba4.8436437a8d45aFUc1hop4U");
		Map<String,String> resSuccess1 = new HashMap<String,String>();
		resSuccess1.put("status", "200");
		resSuccess1.put("statusCode", "3");
		resSuccess1.put("statusMessage", "Success");
		resSuccess1.put("statusType", "SUCCESS");
		resSuccess1.put("accountStatus", status.ACTIVE.toString());
		resSuccess1.put("uidx","cc9f7ab3.c7dc.4eaa.aba4.8436437a8d45aFUc1hop4U");
		
		Map<String,String> req2 = new HashMap<String,String>();
		req2.put("Sl.No", "2");
		req2.put("appName", "myntra");
		req2.put("status",status.INACTIVE.toString() );
		req2.put("uidx", "cc9f7ab3.c7dc.4eaa.aba4.8436437a8d45aFUc1hop4U");
		Map<String,String> resSuccess2 = new HashMap<String,String>();
		resSuccess2.putAll(resSuccess1);
		resSuccess2.put("accountStatus", status.INACTIVE.toString());
		
		Map<String,String> req3 = new HashMap<String,String>();
		req3.put("Sl.No", "3");
		req3.put("appName", "myntra");
		req3.put("status",status.BLACKLISTED.toString());
		req3.put("uidx", "cc9f7ab3.c7dc.4eaa.aba4.8436437a8d45aFUc1hop4U");
		Map<String,String> resSuccess3 = new HashMap<String,String>();
		resSuccess3.putAll(resSuccess1);
		resSuccess3.put("accountStatus", status.BLACKLISTED.toString());
		
		Map<String,String> req4 = new HashMap<String,String>();
		req4.put("Sl.No", "4");
		req4.put("appName", "myntra");
		req4.put("status", status.ACCOUNT_SUSPENDED.toString());
		req4.put("uidx", "cc9f7ab3.c7dc.4eaa.aba4.8436437a8d45aFUc1hop4U");
		Map<String,String> resSuccess4 = new HashMap<String,String>();
		resSuccess4.putAll(resSuccess1);
		resSuccess4.put("accountStatus", status.ACCOUNT_SUSPENDED.toString());
		
		//Negative Cases
		Map<String,String> req5 = new HashMap<String,String>();
		req5.put("Sl.No", "5");
		req5.put("appName", "myntr");
		req5.put("status", status.ACCOUNT_SUSPENDED.toString());
		req5.put("uidx", "cc9f7ab3.c7dc.4eaa.aba4.8436437a8d45aFUc1hop4U");
		Map<String,String> resError1 = new HashMap<String,String>();
		resError1.put("status", "200");
		resError1.put("statusCode", "111020");
		resError1.put("statusMessage", "Invalid App");
		resError1.put("statusType", "ERROR");
		
		Map<String,String> req6 = new HashMap<String,String>();
		req6.putAll(req4);
		req6.put("Sl.No", "6");
		req6.put("appName", "myntra ");
		
		Map<String,String> req7 = new HashMap<String,String>();
		req7.putAll(req4);
		req7.put("Sl.No", "7");
		req7.put("appName", " myntra");
		
		Map<String,String> req8 = new HashMap<String,String>();
		req8.putAll(req4);
		req8.put("Sl.No", "8");
		req8.put("appName", " myntra ");
		
		Map<String,String> req9 = new HashMap<String,String>();
		req9.putAll(req4);
		req9.put("Sl.No", "9");
		req9.put("appName", "562367");
		
		Map<String,String> req10 = new HashMap<String,String>();
		req10.putAll(req4);
		req10.put("Sl.No", "10");
		req10.put("appName", "");
		
		Map<String,String> req11 = new HashMap<String,String>();
		req11.putAll(req4);
		req11.put("Sl.No", "11");
		req11.put("appName", " ");
		
		Map<String,String> req12 = new HashMap<String,String>();
		req12.putAll(req4);
		req12.put("Sl.No", "12");
		req12.put("appName", "$%&%&#(");
		
		Map<String,String> req13 = new HashMap<String,String>();
		req13.putAll(req1);
		req13.put("Sl.No", "13");
		req13.put("status",status.ACTIVE.toString()+" ");
		
		Map<String,String> req14 = new HashMap<String,String>();
		req14.putAll(req1);
		req14.put("Sl.No", "14");
		req14.put("status", " "+status.ACTIVE.toString());
		
		Map<String,String> req15 = new HashMap<String,String>();
		req15.putAll(req1);
		req15.put("Sl.No", "15");
		req15.put("status", " "+status.ACTIVE.toString()+" ");
		
		Map<String,String> req16 = new HashMap<String,String>();
		req16.putAll(req1);
		req16.put("Sl.No", "16");
		req16.put("status", status.ACTIVE.toString().toLowerCase());
		
		Map<String,String> req17 = new HashMap<String,String>();
		req17.putAll(req1);
		req17.put("Sl.No", "17");
		req17.put("status", status.ACTIVE.toString().toLowerCase().replace("a", "A"));
		
		Map<String,String> req18 = new HashMap<String,String>();
		req18.putAll(req1);
		req18.put("Sl.No", "18");
		req18.put("status", status.ACTIVE.toString().replace(status.ACTIVE.toString(), "$#_&^%$()&>:"));
		
		Map<String,String> req19 = new HashMap<String,String>();
		req19.putAll(req1);
		req19.put("Sl.No", "19");
		req19.put("status", status.ACTIVE.toString().replace("A","Z"));
		
		Map<String,String> req20 = new HashMap<String,String>();
		req20.putAll(req1);
		req20.put("Sl.No", "20");
		req20.put("status", status.ACTIVE.toString().replace(status.ACTIVE.toString(),""));
		
		Map<String,String> req21 = new HashMap<String,String>();
		req21.putAll(req1);
		req21.put("Sl.No", "21");
		req21.put("status", status.ACTIVE.toString().replace(status.ACTIVE.toString()," "));
		
		Map<String,String> resError2 = new HashMap<String,String>();
		resError2.put("status", "200");
		resError2.put("statusCode", "51");
		resError2.put("statusMessage", "Generic Exception");
		resError2.put("statusType", "ERROR");
		
		Map<String,String> req22 = new HashMap<String,String>();
		req22.putAll(req1);
		req22.put("Sl.No", "22");
		req22.put("uidx","cc9f7ab3.c7dc.4eaa.aba4.8436437a8d45aFUc1hop4U ");

		Map<String,String> req23 = new HashMap<String,String>();
		req23.putAll(req1);
		req23.put("Sl.No", "23");
		req23.put("uidx"," cc9f7ab3.c7dc.4eaa.aba4.8436437a8d45aFUc1hop4U");
		
		Map<String,String> req24 = new HashMap<String,String>();
		req24.putAll(req1);
		req24.put("Sl.No", "24");
		req24.put("uidx"," cc9f7ab3.c7dc.4eaa.aba4.8436437a8d45aFUc1hop4U ");
		
		Map<String,String> req25 = new HashMap<String,String>();
		req25.putAll(req1);
		req25.put("Sl.No", "25");
		req25.put("uidx","cc9f7ab3.c7dc.  4eaa.aba4.   8436437a8d45aFUc1hop4U");
		
		Map<String,String> req26 = new HashMap<String,String>();
		req26.putAll(req1);
		req26.put("Sl.No", "26");
		req26.put("uidx","%^%$$#(&*>.*^%$%&^$^%>.*^&(*)(&*_(&^&%.+>(((*((**($$%");
		
		Map<String,String> req27 = new HashMap<String,String>();
		req27.putAll(req1);
		req27.put("Sl.No", "27");
		req27.put("uidx","");
		
		Map<String,String> req28= new HashMap<String,String>();
		req28.putAll(req1);
		req28.put("Sl.No", "28");
		req28.put("uidx"," ");
		
		Map<String,String> req29= new HashMap<String,String>();
		req29.putAll(req1);
		req29.put("Sl.No", "29");
		req29.put("uidx","        ");
		
		Map<String,String> req30= new HashMap<String,String>();
		req30.putAll(req1);
		req30.put("Sl.No", "30");
		req30.put("uidx","cc9f7ab3.c7dc.4eaa.aba4.8436437a8d45aFUc1#)p4U");
		
		Map<String,String> req31= new HashMap<String,String>();
		req31.putAll(req1);
		req31.put("Sl.No", "31");
		req31.put("uidx","cc9f7ab3.c7dc.4eaa.aba4.8436437a8d45aFUc1hop4USuhas");
		
		Map<String,String> req32= new HashMap<String,String>();
		req32.putAll(req1);
		req32.put("Sl.No", "32");
		req32.put("uidx","Suhascc9f7ab3.c7dc.4eaa.aba4.8436437a8d45aFUc1hop4U");
		
		Map<String,String> resError3 = new HashMap<String,String>();
		resError3.put("status", "200");
		resError3.put("statusCode", "111004");
		resError3.put("statusMessage", "Account Doesn't exists");
		resError3.put("statusType", "ERROR");
		
		
					 dataSet = new Object[][]{{req1,resSuccess1},{req2,resSuccess2},{req3,resSuccess3},{req4,resSuccess4},{req5,resError1},
						 {req6,resSuccess4},{req7,resSuccess4},{req8,resSuccess4},{req9,resError1},{req10,resError1},{req11,resError1},
						 {req12,resError1},{req13,resError2},{req14,resError2},{req15,resError2},{req16,resError2},{req17,resError2},
						 {req18,resError2},{req19,resError2},{req20,resError2},{req21,resError2},{req22,resSuccess1},{req23,resSuccess1},
						 {req24,resSuccess1},{req25,resError3},{req26,resError3},{req27,resError3},{req28,resError3},{req29,resError3},
						 {req30,resError3},{req31,resError3},{req32,resError3}};
			return dataSet;
	}

	@DataProvider
	public Object[][] ideaEmailToLoginDP(){
		
		Object[][] dataSet = null;
			
			/* Positive cases */
		Map<String,String> test1 = new HashMap<String,String>();
		test1.put("Sl.No", "1");// For Debugging purpose
		test1.put("mailID","idea1@myntra.com");
		test1.put("status", "200");
		test1.put("statusCode", "3");
		test1.put("statusMessage", "Success");
		test1.put("statusType", "SUCCESS");
		test1.put("linked","true");

		Map<String,String> test2 = new HashMap<String,String>();
		test2.putAll(test1);
		test2.put("Sl.No", "2");
		test2.put("mailID"," idea1@myntra.com");
		test2.put("linked","false");
		
		Map<String,String> test3 = new HashMap<String,String>();
		test3.putAll(test1);
		test3.put("Sl.No", "3");
		test3.put("mailID","idea1@myntra.com ");
		test3.put("linked","false");
		
		Map<String,String> test4 = new HashMap<String,String>();
		test4.putAll(test1);
		test4.put("Sl.No", "4");
		test4.put("mailID"," idea1@myntra.com ");
		test4.put("linked","false");
		
		Map<String,String> test5 = new HashMap<String,String>();
		test5.put("Sl.No", "5");
		test5.put("mailID","idea1 @myntra.com");
		test5.put("linked","false");
		test5.put("status", "200");
		test5.put("statusCode", "3");
		test5.put("statusMessage", "Success");
		test5.put("statusType", "Success");
		
		Map<String,String> test6 = new HashMap<String,String>();
		test6.putAll(test5);
		test6.put("Sl.No", "6");
		test6.put("mailID","%^#%&^@&**@&*(@*&^");
		test6.put("linked","false");
		
		Map<String,String> test7 = new HashMap<String,String>();
		test7.putAll(test5);
		test7.put("Sl.No", "7");
		test7.put("mailID","ide@1@myntra.com");
		test7.put("linked","false");
		
		Map<String,String> test8 = new HashMap<String,String>();
		test8.putAll(test5);
		test8.put("Sl.No", "8");
		test8.put("mailID","");
		test8.put("linked","false");
		
		Map<String,String> test9 = new HashMap<String,String>();
		test9.putAll(test5);
		test9.put("Sl.No", "9");
		test9.put("mailID"," ");
		test9.put("linked","false");
		
		Map<String,String> test10 = new HashMap<String,String>();
		test10.putAll(test5);
		test10.put("Sl.No", "10");
		test10.put("mailID","         ");
		test10.put("linked","false");
		
		Map<String,String> test11 = new HashMap<String,String>();
		test11.putAll(test5);
		test11.put("Sl.No", "11");
		test11.put("mailID","idea@myntracom");
		test11.put("linked","false");
		
		Map<String,String> test12 = new HashMap<String,String>();
		test12.putAll(test5);
		test12.put("Sl.No", "12");
		test12.put("mailID","ideamyntra.com");
		test12.put("linked","false");
		
		Map<String,String> test13 = new HashMap<String,String>();
		test13.putAll(test5);
		test13.put("Sl.No", "13");
		test13.put("mailID","ideamyntracom");
		test13.put("linked","false");
		
		/*Multiple mail id's */
		Map<String,String> test14 = new HashMap<String,String>();
		test14.putAll(test1);
		test14.put("Sl.No", "14");
		test14.put("mailID","idea1@myntra.com\",\"idea2@myntra.com");
		test14.put("linked","true");

		Map<String,String> test15 = new HashMap<String,String>();
		test15.putAll(test5);
		test15.put("Sl.No", "15");
		test15.put("mailID","idea1@myntra.com\",\"idea2@myntra.com\",\"$#^%^#^@&%&^.&%\",\"idea3 @myntra.com\",\" idea4@myntra.com \",\"\",\" ");
		test15.put("linked","false");
		
		Map<String,String> test16 = new HashMap<String,String>();
		test16.putAll(test5);
		test16.put("Sl.No", "16");
		test16.put("mailID","idea@myntra.com\",\"idea@2@myntra.com\",\"$#^%^#^@&%&^.&%\",\"idea3 @myntra.com\",\" idea4@myntra.com \",\"\",\" ");
		test16.put("linked","false");
		
		Map<String,String> test17 = new HashMap<String,String>();
		test17.putAll(test5);
		test17.put("Sl.No", "17");
		test17.put("mailID","47647648@784.87468");
		test17.put("linked","false");
		
		dataSet = new Object[][]{{test1},{test2},{test3},{test4},{test5},{test6},{test7},{test8},{test9},{test10},{test11},{test12},{test13},{test14},{test15},{test16},{test17}};
		return dataSet;
	}
	
	@DataProvider
	public Object[][] ideaMyntraInitPasswordRequestDP(){
		
		Object[][] dataSet = null;
		String email = "idea1@myntra.com";
			/* Positive cases */
		Map<String,String> req1 = new HashMap<String,String>();
		req1.put("Sl.No", "1");
		req1.put("appName","myntra");
		req1.put("email", email);
	
		Map<String,String> req2 = new HashMap<>();
		req2.put("Sl.No", "2");
		req2.put("appName","MYNTRA");
		req2.put("email", email);
		
		Map<String,String> req3 = new HashMap<>();
		req3.put("Sl.No", "3");
		req3.put("appName","MYNTRA");
		req3.put("email", email.toUpperCase());
		
		Map<String,String> respSuccess = new HashMap<String,String>();
		respSuccess.put("Status", "200");
		respSuccess.put("StatusCode", "3");
		respSuccess.put("StatusMessage", "Success");
		respSuccess.put("StatusType", "Success");
		
		Map<String,String> req4 = new HashMap<>();
		req4.putAll(req1);
		req4.put("Sl.No", "4");
		req4.put("appName", "");
		
		Map<String,String> req5 = new HashMap<>();
		req5.putAll(req1);
		req5.put("Sl.No", "5");
		req5.put("appName", " ");
		
		Map<String,String> req6 = new HashMap<>();
		req6.putAll(req1);
		req6.put("Sl.No", "6");
		req6.put("appName", "     ");
		
		Map<String,String> req7 = new HashMap<>();
		req7.putAll(req1);
		req7.put("Sl.No", "7");
		req7.put("appName", "myntra ");
		
		Map<String,String> req8 = new HashMap<>();
		req8.putAll(req1);
		req8.put("Sl.No", "8");
		req8.put("appName", " myntra");
		
		Map<String,String> req9 = new HashMap<>();
		req9.putAll(req1);
		req9.put("Sl.No", "9");
		req9.put("appName", " myntra ");
		
		Map<String,String> req10 = new HashMap<>();
		req10.putAll(req1);
		req10.put("Sl.No", "10");
		req10.put("appName", "3438184");
		
		Map<String,String> req11 = new HashMap<>();
		req11.putAll(req1);
		req11.put("Sl.No", "11");
		req11.put("appName", "$%^$#^&%");
		
		Map<String,String> req12 = new HashMap<>();
		req12.putAll(req1);
		req12.put("Sl.No", "12");
		req12.put("appName", "myntra1");
		
		Map<String,String> respAppError = new HashMap<>();
		respAppError.put("Status", "200");
		respAppError.put("StatusCode", "111020");
		respAppError.put("StatusMessage", "Invalid App");
		respAppError.put("StatusType", "Error");
		
		Map<String,String> req13 = new HashMap<>();
		req13.putAll(req1);
		req13.put("Sl.No", "13");
		req13.put("email", "");
		
		Map<String,String> req14 = new HashMap<>();
		req14.putAll(req1);
		req14.put("Sl.No", "14");
		req14.put("email", " ");
		
		Map<String,String> req15 = new HashMap<>();
		req15.putAll(req1);
		req15.put("Sl.No", "15");
		req15.put("email", "        ");
		
		Map<String,String> req16 = new HashMap<>();
		req16.putAll(req1);
		req16.put("Sl.No", "16");
		req16.put("email", email+" ");
		
		Map<String,String> req17 = new HashMap<>();
		req17.putAll(req1);
		req17.put("Sl.No", "17");
		req17.put("email", " "+email);
		
		Map<String,String> req18 = new HashMap<>();
		req18.putAll(req1);
		req18.put("Sl.No", "18");
		req18.put("email", " "+email+" ");
		
		Map<String,String> req19 = new HashMap<>();
		req19.putAll(req1);
		req19.put("Sl.No", "19");
		req19.put("email", "     "+email+"      ");
		
		Map<String,String> req20 = new HashMap<>();
		req20.putAll(req1);
		req20.put("Sl.No", "20");
		req20.put("email", email.replace("@", "   @"));
		
		Map<String,String> req21 = new HashMap<>();
		req21.putAll(req1);
		req21.put("Sl.No", "21");
		req21.put("email", email.replace("@", ""));
		
		Map<String,String> req22 = new HashMap<>();
		req22.putAll(req1);
		req22.put("Sl.No", "22");
		req22.put("email", email.replace(".", ""));
		
		Map<String,String> req23 = new HashMap<>();
		req23.putAll(req1);
		req23.put("Sl.No", "23");
		req23.put("email", email.replace(".", "").replace("@", ""));
		
		Map<String,String> req24 = new HashMap<>();
		req24.putAll(req1);
		req24.put("Sl.No", "24");
		String mail = "%$#&$^#$%^@%&$&.&%&^";
		mail = Matcher.quoteReplacement(mail);
		req24.put("email", email.replaceAll(email,mail));
		
		Map<String,String> req25 = new HashMap<>();
		req25.putAll(req1);
		req25.put("Sl.No", "25");
		req25.put("email", email.replaceAll(email, "7834672@87487.7823"));
		
		Map<String,String> req26 = new HashMap<>();
		req26.putAll(req1);
		req26.put("Sl.No", "26");
		req26.put("email", email.replace("@","&^*@"));
		
		Map<String,String> req27 = new HashMap<>();
		req27.putAll(req1);
		req27.put("Sl.No", "27");
		req27.put("email", "ssk@ssk.com");
		
		Map<String,String> respEmailError = new HashMap<>();
		respEmailError.put("Status", "200");
		respEmailError.put("StatusCode", "111004");
		respEmailError.put("StatusMessage", "Invalid Email ID");
		respEmailError.put("StatusType", "Error");
		
		Map<String,String> respEmailAccError = new HashMap<>();
		respEmailAccError.putAll(respEmailError);
		respEmailAccError.put("StatusMessage", "Account Doesn't exists");
		
		
				dataSet = new Object[][]{{req1,respSuccess},{req2,respSuccess},{req3,respSuccess},{req4,respAppError},{req5,respAppError},{req6,respAppError},
					{req7,respSuccess},{req8,respSuccess},{req9,respSuccess},{req10,respAppError},{req11,respAppError},{req12,respAppError},{req13,respEmailAccError},
					{req14,respEmailAccError},{req15,respEmailAccError},{req16,respSuccess},{req17,respEmailAccError},{req18,respEmailAccError},{req19,respEmailAccError},{req20,respEmailAccError},
					{req21,respEmailAccError},{req22,respEmailAccError},{req23,respEmailAccError},{req24,respEmailAccError},{req25,respEmailAccError},{req26,respEmailAccError},{req27,respEmailAccError}};
		return dataSet;
	}


	@DataProvider
	public Object[][] ideaLastPasswordDp(){

		Object[][] dataSet = null;

		/* Positive cases */
		HashMap<String,String> req1 = new HashMap<String,String>();
		req1.put("Sl.No", "1");
		req1.put("appName","myntra");
		req1.put("email","idea1@myntra.com");
		HashMap<String,String> res1 = new HashMap<String,String>();
		res1.put("Status","200");
		res1.put("StatusMessage","Success");
		res1.put("StatusType","success");
		res1.put("StatusCode","3");
		HashMap<String,String> req2 = new HashMap<String,String>();
		req2.put("Sl.No", "2");
		req2.put("appName","myntra ");
		req2.put("email","idea1@myntra.com");
		HashMap<String,String> req3 = new HashMap<String,String>();
		req3.put("Sl.No", "3");
		req3.put("appName"," myntra");
		req3.put("email","idea1@myntra.com");
		HashMap<String,String> req4 = new HashMap<String,String>();
		req4.put("Sl.No", "4");
		req4.put("appName"," myntra ");
		req4.put("email","idea1@myntra.com");
		/* Negative cases for appname */
		HashMap<String,String> req5 = new HashMap<String,String>();
		req5.put("Sl.No", "5");
		req5.put("appName","");
		req5.put("email","idea1@myntra.com");
		HashMap<String,String> req6 = new HashMap<String,String>();
		req6.put("Sl.No", "6");
		req6.put("appName"," ");
		req6.put("email","idea1@myntra.com");
		HashMap<String,String> req7 = new HashMap<String,String>();
		req7.put("Sl.No", "7");
		req7.put("appName","          ");
		req7.put("email","idea1@myntra.com");
		HashMap<String,String> req8 = new HashMap<String,String>();
		req8.put("Sl.No", "8");
		req8.put("appName","myntra1");
		req8.put("email","idea1@myntra.com");
		HashMap<String,String> req9 = new HashMap<String,String>();
		req9.put("Sl.No", "9");
		req9.put("appName","*&^&^%$#%$^%");
		req9.put("email","idea1@myntra.com");
		HashMap<String,String> req10 = new HashMap<String,String>();
		req10.put("Sl.No", "10");
		req10.put("appName","873468736");
		req10.put("email","idea1@myntra.com");
		HashMap<String,String> res2 = new HashMap<String,String>();
		res2.put("Status","200");
		res2.put("StatusMessage","Invalid App");
		res2.put("StatusType","Error");
		res2.put("StatusCode","111020");
		/*Positive scenarios for email */
		/*HashMap<String,String> req11 = new HashMap<String,String>();
		req11.put("Sl.No", "11");
		req11.put("appName","myntra");
		req11.put("email","idea1@myntra.com");
		*/
		HashMap<String,String> req12 = new HashMap<String,String>();
		req12.put("Sl.No", "12");
		req12.put("appName","myntra");
		req12.put("email","idea1@myntra.com ");
		HashMap<String,String> req13 = new HashMap<String,String>();
		req13.put("Sl.No", "13");
		req13.put("appName","myntra");
		req13.put("email"," idea1@myntra.com");
		HashMap<String,String> req14 = new HashMap<String,String>();
		req14.put("Sl.No", "14");
		req14.put("appName","myntra");
		req14.put("email"," idea1@myntra.com ");
		/*Negative Scenarios for email */
		HashMap<String,String> req15 = new HashMap<String,String>();
		req15.put("Sl.No", "15");
		req15.put("appName","myntra");
		req15.put("email","");
		HashMap<String,String> req16 = new HashMap<String,String>();
		req16.put("Sl.No", "16");
		req16.put("appName","myntra");
		req16.put("email"," ");
		HashMap<String,String> req17 = new HashMap<String,String>();
		req17.put("Sl.No", "17");
		req17.put("appName","myntra");
		req17.put("email","       ");
		HashMap<String,String> req18 = new HashMap<String,String>();
		req18.put("Sl.No", "18");
		req18.put("appName","myntra");
		req18.put("email","idea@myntra.com");
		HashMap<String,String> req19 = new HashMap<String,String>();
		req19.put("Sl.No", "19");
		req19.put("appName","myntra");
		req19.put("email","idea1myntra.com");
		HashMap<String,String> req20 = new HashMap<String,String>();
		req20.put("Sl.No", "20");
		req20.put("appName","myntra");
		req20.put("email","idea1@myntracom");
		HashMap<String,String> req21 = new HashMap<String,String>();
		req21.put("Sl.No", "21");
		req21.put("appName","myntra");
		req21.put("email","idea1myntracom");
		HashMap<String,String> req22 = new HashMap<String,String>();
		req22.put("Sl.No", "22");
		req22.put("appName","myntra");
		req22.put("email","$$^%&^%*^&%*^%^&^&%*");
		HashMap<String,String> req23 = new HashMap<String,String>();
		req23.put("Sl.No", "23");
		req23.put("appName","myntra");
		req23.put("email","7845589226");
		HashMap<String,String> res3 = new HashMap<String,String>();
		res3.put("Status","200");
		res3.put("StatusMessage","Account Doesn't exists");
		res3.put("StatusType","Error");
		res3.put("StatusCode","111004");
		HashMap<String,String> req24 = new HashMap<String,String>();
		req24.put("Sl.No", "24");
		req24.put("appName","&^%^*");
		req24.put("email","&^****R%^#@#@##$##$%>&^%^&^.Y%%");

		dataSet = new Object[][]{{req1,res1},{req2,res1},{req3,res1},{req4,res1},{req5,res2},{req6,res2},{req7,res2},{req8,res2},{req9,res2},{req10,res2},
			{req12,res1},{req13,res1},{req14,res1},{req15,res3},{req16,res3},{req17,res3},{req18,res3},{req19,res3},{req20,res3},{req21,res3},
			{req22,res3},{req23,res3},{req24,res2}};
			return dataSet;
	}
	
	@DataProvider
	public Object[][] ideaLastPasswordInproperDp(){

		Object[][] dataSet = null;

		/* Positive cases */
		LinkedHashMap<String,String> req1 = new LinkedHashMap<String,String>();
		req1.put("payloadrequired", "true");
		req1.put("appName","myntra");
		
		HashMap<String,String> res1 = new HashMap<String,String>();
		res1.put("Status","200");
		res1.put("StatusMessage","Account Doesn't exists");
		res1.put("StatusType","Error");
		res1.put("StatusCode","111004");
		
		LinkedHashMap<String,String> req2 = new LinkedHashMap<String,String>();
		req2.put("payloadrequired", "true");
		req2.put("email","idea1@myntra.com");
		
		HashMap<String,String> res2 = new HashMap<String,String>();
		res2.put("Status","200");
		res2.put("StatusMessage","Invalid App");
		res2.put("StatusType","Error");
		res2.put("StatusCode","111020");
		
		LinkedHashMap<String,String> req3 = new LinkedHashMap<String,String>();
		req3.put("payloadrequired", "true");
		
		LinkedHashMap<String,String> req4 = new LinkedHashMap<String,String>();
		req4.put("payloadrequired", "false");
		
		HashMap<String,String> res3 = new HashMap<String,String>();
		res3.put("Status","400");
		
		LinkedHashMap<String,String> req5 = new LinkedHashMap<String,String>();
		req5.put("appName","myntra");
		req5.put("email","idea1@myntra.com");
		
		dataSet = new Object[][]{{req1,res1},{req2,res2},{req3,res2},{req4,res3},{req5,res3}};
			return dataSet;
	}

	
	@DataProvider
	public Object[][] ideaSignOut() throws IOException{
		Object[][] dataSet = null;
		
		System.out.println("you are here");
		String payloadFile=utilities.readFileAsString("./Data/payloads/JSON/signin");
		payloadFile=utilities.preparepayload(payloadFile,new String[] {"sidd_test4@gmail.com","sid123"});
		MyntraService service = Myntra.getService(ServiceType.PORTAL_IDP,
				APINAME.IDPMOBILESIGNIN , init.Configurations,payloadFile);
		
		RequestGenerator request =  new RequestGenerator(service);
		
		System.out.println("\nService URL for sign in is---->"+service.URL);
		String resp = request.respvalidate.returnresponseasstring();
		MultivaluedMap<String, Object> header = request.response.getHeaders();
		
		
		String AT = header.get("AT").toString().replace("[", "");
		AT = AT.replace("]", "");
        String RT = header.get("RT").toString().replace("]", "");
        RT = RT.replace("[", "");
		String xid = header.get("xid").toString().replace("[", "");
		xid = xid.replace("]","");
		System.out.println("AT is"+AT+"RT is"+RT);
		System.out.println("the token is"+JsonPath.read(resp,"$.xsrfToken").toString());
		HashMap<String,String> headers = new HashMap<String, String>();
		headers.put("AT", AT);
		headers.put("RT", RT);
		headers.put("xid",xid);
		headers.put("xsrfToken",JsonPath.read(resp,"$.xsrfToken").toString());
		headers.put("AT2", AT);
		headers.put("RT2", RT);
		
		Map<String,String> test1 = new HashMap<String,String>();
		test1.put("status", "200");
		test1.put("statusCode", "3");
		test1.put("statusMessage", "Success");
		test1.put("statusType", "SUCCESS");
		
		payloadFile=utilities.preparepayload(payloadFile,new String[] {"fox7testuser.1@myntra.com","randompass"});
		service = Myntra.getService(ServiceType.PORTAL_IDP,
				APINAME.IDPMOBILESIGNIN , init.Configurations,payloadFile);
	    request =  new RequestGenerator(service);
	    
	    System.out.println("\nService URL for sign 22 in is---->"+service.URL);
	    resp = request.respvalidate.returnresponseasstring();
	    MultivaluedMap<String, Object> header2 = request.response.getHeaders();
	    AT = header2.get("AT").toString().replace("[", "");
		AT = AT.replace("]", "");
        RT = header2.get("RT").toString().replace("]", "");
        RT = RT.replace("[", "");
		System.out.println("AT is"+AT+"RT is"+RT);
		xid = header2.get("xid").toString().replace("[", "");
		xid = xid.replace("]","");
		
		HashMap<String, String> headers2 = new HashMap<String, String>();
		headers2.put("AT","");
		headers2.put("RT", "");
		headers2.put("xid",xid);
		headers2.put("xsrfToken",JsonPath.read(resp,"$.xsrfToken").toString());
		headers2.put("AT2", AT);
		headers2.put("RT2", RT);
		
		HashMap<String, String> headers3 = new HashMap<String, String>();
		headers3.put("AT","@@@@@@@");
		headers3.put("RT","@@@@@@");
		headers3.put("xid",xid);
		headers3.put("xsrfToken",JsonPath.read(resp,"$.xsrfToken").toString());
		headers3.put("AT2", AT);
		headers3.put("RT2", RT);
		
		HashMap<String, String> headers4 = new HashMap<String, String>();
		headers4.put("AT",AT+"ssewd");
		headers4.put("RT", RT+"ssewd");
		headers4.put("xid",xid);
		headers4.put("xsrfToken",JsonPath.read(resp,"$.xsrfToken").toString());
		headers4.put("AT2", AT);
		headers4.put("RT2", RT);
		
		HashMap<String, String> headers5 = new HashMap<String, String>();
		headers5.put("AT","@@@");
		headers5.put("RT", "@@@");
		headers5.put("xid",xid);
		headers5.put("xsrfToken",JsonPath.read(resp,"$.xsrfToken").toString());
		headers5.put("AT2", "@@@");
		headers5.put("RT2", "@@@");
		
		HashMap<String, String> headers6 = new HashMap<String, String>();
		headers6.put("AT",AT);
		headers6.put("RT", RT);
		headers6.put("xid","");
		headers6.put("xsrfToken","");
		headers6.put("AT2", AT);
		headers6.put("RT2",RT);
		
		HashMap<String, String> headers7 = new HashMap<String, String>();
		headers7.put("AT",AT);
		headers7.put("RT", RT);
		headers7.put("xid","@@@@");
		headers7.put("xsrfToken","@@@");
		headers7.put("AT2", AT);
		headers7.put("RT2",RT);
		
		HashMap<String, String> headers8 = new HashMap<String, String>();
		headers8.put("AT","");
		headers8.put("RT", RT);
		headers8.put("xid",xid);
		headers8.put("xsrfToken",JsonPath.read(resp,"$.xsrfToken").toString());
		headers8.put("AT2", AT);
		headers8.put("RT2",RT);
		
		HashMap<String, String> headers9 = new HashMap<String, String>();
		headers9.put("AT","");
		headers9.put("RT", RT);
		headers9.put("xid",xid);
		headers9.put("xsrfToken",JsonPath.read(resp,"$.xsrfToken").toString());
		headers9.put("AT2", AT);
		headers9.put("RT2",RT);
		
		HashMap<String, String> headers10 = new HashMap<String, String>();
		headers10.put("AT",AT);
		headers10.put("RT", RT);
		headers10.put("xid","");
		headers10.put("xsrfToken",JsonPath.read(resp,"$.xsrfToken").toString());
		headers10.put("AT2", AT);
		headers10.put("RT2",RT);
		
		HashMap<String, String> headers11 = new HashMap<String, String>();
		headers11.put("AT",AT);
		headers11.put("RT", RT);
		headers11.put("xid",xid);
		headers11.put("xsrfToken","");
		headers11.put("AT2", AT);
		headers11.put("RT2",RT);
		
		Map<String,String> test2 = new HashMap<>();
		test2.put("status", "200");
		test2.put("statusCode", "111030");
		test2.put("statusMessage", "Invalid token. Login session id is already deleted.");
		test2.put("statusType", "ERROR");
		
		Map<String,String> test3 = new HashMap<>();
		test3.put("status", "200");
		test3.put("statusCode", "111022");
		test3.put("statusMessage", "Invalid refresh token");
		test3.put("statusType", "ERROR");
		
		
		dataSet = new Object[][]{{headers,test2},{headers2,test1},{headers3,test1},{headers4,test1},{headers5,test3},{headers6,test1},{headers7,test1},{headers8,test1},{headers9,test1},{headers10,test1},{headers11,test1}};
		return dataSet;
		
	}
	
	public enum cName{
		COLLECTION,
		HELPSHIFT,
		SPE,
		SHOP,
		STORE
	}
	@DataProvider
	public Object[][] linkunlinkUserClient(Method method){
		Object[][] data = null;
		String uidx = "";
		String methodName = method.getName();
		if(envName.equalsIgnoreCase("Fox8"))
			uidx="14e7a939.146f.4324.ac04.c9e599b4fe00Ch2k8BfrQO";
		else if(envName.equalsIgnoreCase("Production"))
			uidx="";
		else if(envName.equalsIgnoreCase("Fox7"))
			uidx="e7362f2b.09cb.4be0.a2cf.a793757f2317q3DVA7VY2J";
		
		cName[] clientArray = cName.values();
		int len = clientArray.length;
		Random r = new Random();
		int random = r.nextInt(len);
		String client = clientArray[random].toString();
				
		HashMap<String,String> request1 = new HashMap<String,String>();
		request1.put("TC", "1");
		request1.put("appName", "\"myntra\"");
		request1.put("uidx", "\""+uidx+"\"");
		request1.put("clientName", "\""+client+"\"");
		
		HashMap<String,String> respons1 = new HashMap<String,String>();
		respons1.put("status", "200");
		respons1.put("statusCode", "3");
		respons1.put("statusMessage","Success");
		respons1.put("statusType", "Success");
		
		HashMap<String,String> request2 = new HashMap<String,String>();
		request2.put("TC", "2");
		request2.put("appName", "\"myntra \"");
		request2.put("uidx", "\""+uidx+"\"");
		request2.put("clientName", "\""+client+"\"");
		
		HashMap<String,String> respons2 = new HashMap<String,String>();
		respons2.put("status", "200");
		respons2.put("statusCode", "111041");
		respons2.put("statusMessage","User client link already exists");
		respons2.put("statusType", "Error");
		
		HashMap<String,String> responsD2 = new HashMap<String,String>();
		responsD2.put("status", "200");
		responsD2.put("statusCode", "111042");
		responsD2.put("statusMessage","User client link doesn't exists");
		responsD2.put("statusType", "Error");
		
		HashMap<String,String> request3 = new HashMap<String,String>();
		request3.putAll(request1);
		request3.put("TC", "3");
		request3.put("appName", "\" myntra\"");
		
		HashMap<String,String> request4 = new HashMap<String,String>();
		request4.putAll(request1);
		request4.put("TC", "4");
		request4.put("appName", "\" myntra \"");
		
		HashMap<String,String> request5 = new HashMap<String,String>();
		request5.putAll(request1);
		request5.put("TC", "5");
		request5.put("appName", "\"\"");
		
		HashMap<String,String> respons3 = new HashMap<String,String>();
		respons3.put("status", "200");
		respons3.put("statusCode", "111020");
		respons3.put("statusMessage","Invalid App");
		respons3.put("statusType", "Error");
		
		HashMap<String,String> request6 = new HashMap<String,String>();
		request6.putAll(request1);
		request6.put("TC", "6");
		request6.put("appName", "\" \"");
		
		HashMap<String,String> request7 = new HashMap<String,String>();
		request7.putAll(request1);
		request7.put("TC", "7");
		request7.put("appName", "\"     \"");
		
		HashMap<String,String> request8 = new HashMap<String,String>();
		request8.putAll(request1);
		request8.put("TC", "8");
		request8.put("appName", "\"xyzzy\"");
		
		HashMap<String,String> request9 = new HashMap<String,String>();
		request9.putAll(request1);
		request9.put("TC", "9");
		request9.put("appName", "\"#$%^&*()(*&^%$#@#$%^\"");
		
		HashMap<String,String> request10 = new HashMap<String,String>();
		request10.putAll(request1);
		request10.put("TC", "10");
		request10.put("appName", "\"1234567865\"");
		
		HashMap<String,String> request11 = new HashMap<String,String>();
		request11.putAll(request1);
		request11.put("TC", "11");
		request11.put("appName", "\"myn tra\"");
		
		HashMap<String,String> request12 = new HashMap<String,String>();
		request12.putAll(request1);
		request12.put("TC", "12");
		request12.put("appName", "null");
		
		HashMap<String,String> respons4 = new HashMap<String,String>();
		respons4.put("status", "400");
		
		HashMap<String,String> request13 = new HashMap<String,String>();
		request13.putAll(request1);
		request13.put("TC", "13");
		request13.put("appName", "\"MYNTRA\"");
		
		HashMap<String,String> request14 = new HashMap<String,String>();
		request14.putAll(request1);
		request14.put("TC", "14");
		request14.put("appName", "\"MynTra\"");
		
		HashMap<String,String> request15 = new HashMap<String,String>();
		request15.putAll(request1);
		request15.put("TC", "15");
		request15.put("appName", "1");
		
		HashMap<String,String> request16 = new HashMap<String,String>();
		request16.putAll(request1);
		request16.put("TC", "16");
		request16.put("uidx", "\""+uidx+" \"");
		
		HashMap<String,String> request17 = new HashMap<String,String>();
		request17.putAll(request1);
		request17.put("TC", "17");
		request17.put("uidx", "\" "+uidx+"\"");
		
		HashMap<String,String> request18 = new HashMap<String,String>();
		request18.putAll(request1);
		request18.put("TC", "18");
		request18.put("uidx", "\" "+uidx+" \"");
		
		HashMap<String,String> respons5 = new HashMap<String,String>();
		respons5.put("status", "200");
		respons5.put("statusCode", "111004");
		respons5.put("statusMessage","Account Doesn't exists");
		respons5.put("statusType", "Error");
		
		HashMap<String,String> request19 = new HashMap<String,String>();
		request19.putAll(request1);
		request19.put("TC", "19");
		request19.put("uidx", "\"\"");
		
		HashMap<String,String> request20 = new HashMap<String,String>();
		request20.putAll(request1);
		request20.put("TC", "20");
		request20.put("uidx", "\" \"");
		
		HashMap<String,String> request21 = new HashMap<String,String>();
		request21.putAll(request1);
		request21.put("TC", "21");
		request21.put("uidx", "\"      \"");
		
		HashMap<String,String> request22 = new HashMap<String,String>();
		request22.putAll(request1);
		request22.put("TC", "22");
		request22.put("uidx", "\"aaaaaa.bbbbbb.cccccc.ddddddd\"");
		
		HashMap<String,String> request23 = new HashMap<String,String>();
		request23.putAll(request1);
		request23.put("TC", "23");
		request23.put("uidx", "\"#$%^&*.#$%^&*().*&^%$#^%$#$.^%$%^&\"");
		
		HashMap<String,String> request24 = new HashMap<String,String>();
		request24.putAll(request1);
		request24.put("TC", "24");
		request24.put("uidx", "\"42536475.3245634.445555555553.654321345\"");
		
		HashMap<String,String> request25 = new HashMap<String,String>();
		request25.putAll(request1);
		request25.put("TC", "25");
		request25.put("uidx", "\""+uidx.replaceFirst(".", "    .")+"\"");
		
		HashMap<String,String> request26 = new HashMap<String,String>();
		request26.putAll(request1);
		request26.put("TC", "26");
		request26.put("uidx", "null");
		
		HashMap<String,String> respons6 = new HashMap<String,String>();
		respons6.put("status", "200");
		respons6.put("statusCode", "111043");
		respons6.put("statusMessage","Invalid Uidx");
		respons6.put("statusType", "Error");
		
		HashMap<String,String> request27 = new HashMap<String,String>();
		request27.putAll(request1);
		request27.put("TC", "27");
		request27.put("uidx", "\""+invertCase(uidx)+"\"");
		
		HashMap<String,String> request28 = new HashMap<String,String>();
		request28.putAll(request1);
		request28.put("TC", "28");
		request28.put("clientName", "\""+client+" \"");
		
		HashMap<String,String> request29 = new HashMap<String,String>();
		request29.putAll(request1);
		request29.put("TC", "29");
		request29.put("clientName", "\" "+client+"\"");
		
		HashMap<String,String> request30 = new HashMap<String,String>();
		request30.putAll(request1);
		request30.put("TC", "30");
		request30.put("clientName", "\" "+client+" \"");
		
		HashMap<String,String> respons7 = new HashMap<String,String>();
		respons7.put("status", "200");
		respons7.put("statusCode", "111040");
		respons7.put("statusMessage","Invalid Client Name");
		respons7.put("statusType", "Error");
		
		HashMap<String,String> request31 = new HashMap<String,String>();
		request31.putAll(request1);
		request31.put("TC", "31");
		request31.put("clientName", "\"\"");
		
		HashMap<String,String> request32 = new HashMap<String,String>();
		request32.putAll(request1);
		request32.put("TC", "32");
		request32.put("clientName", "\" \"");
		
		HashMap<String,String> request33 = new HashMap<String,String>();
		request33.putAll(request1);
		request33.put("TC", "33");
		request33.put("clientName", "\"      \"");
		
		HashMap<String,String> request34 = new HashMap<String,String>();
		request34.putAll(request1);
		request34.put("TC", "34");
		request34.put("clientName", "\"hjsdgfjsg\"");
		
		HashMap<String,String> request35 = new HashMap<String,String>();
		request35.putAll(request1);
		request35.put("TC", "35");
		request35.put("clientName", "\"64572424822\"");
		
		HashMap<String,String> request36 = new HashMap<String,String>();
		request36.putAll(request1);
		request36.put("TC", "36");
		request36.put("clientName", "\"$%^&*&^%$*($#%\"");
		
		HashMap<String,String> request37 = new HashMap<String,String>();
		request37.putAll(request1);
		request37.put("TC", "37");
		request37.put("clientName", "\""+new StringBuffer(client).replace(client.length()/2, (client.length()/2)+1, client.charAt(client.length()/2)+" ")+"\"");
		
		HashMap<String,String> request38 = new HashMap<String,String>();
		request38.putAll(request1);
		request38.put("TC", "38");
		request38.put("clientName", "\""+client.toUpperCase()+"\"");
		
		HashMap<String,String> request39 = new HashMap<String,String>();
		request39.putAll(request1);
		request39.put("TC", "39");
		request39.put("clientName", "\""+client.toLowerCase()+"\"");
		
		HashMap<String,String> request40 = new HashMap<String,String>();
		request40.putAll(request1);
		request40.put("TC", "40");
		request40.put("clientName", "null");
		
		HashMap<String,String> request41 = new HashMap<String,String>();
		request41.putAll(request1);
		request41.put("TC", "41");
		request41.put("clientName", "\""+client.toUpperCase().replaceFirst(".", invertCase(""+client.charAt(0)))+"\"");

		HashMap<String,String> respons8 = new HashMap<String,String>();
		respons8.put("status", "200");
		
		if(methodName.equals("linkUserToClient")){
		data = new Object[][]{{request1,respons8},{request2,respons2},{request3,respons2},{request4,respons2},{request5,respons3},{request6,respons3},{request7,respons3},{request8,respons3},{request9,respons3},{request10,respons3},
			{request11,respons3},{request12,respons4},{request13,respons2},{request14,respons2},{request15,respons3},{request16,respons2},{request17,respons5},{request18,respons5},{request19,respons5},{request20,respons5},{request21,respons5},
			{request22,respons5},{request23,respons5},{request24,respons5},{request25,respons5},{request26,respons6},{request27,respons2},{request28,respons8},{request29,respons7},{request30,respons7},{request31,respons7},{request32,respons7},{request33,respons7},
			{request34,respons7},{request35,respons7},{request36,respons7},{request37,respons7},{request38,respons8},{request39,respons8},{request40,respons7},{request41,respons8}};
		}
		if(methodName.equals("unlinkUserToClient")){
				data = new Object[][]{{request1,respons8},{request2,responsD2},{request3,responsD2},{request4,responsD2},{request5,respons3},{request6,respons3},{request7,respons3},{request8,respons3},{request9,respons3},{request10,respons3},
					{request11,respons3},{request12,respons4},{request13,responsD2},{request14,responsD2},{request15,respons3},{request16,respons2},{request17,respons5},{request18,respons5},{request19,respons5},{request20,respons5},{request21,respons5},
					{request22,respons5},{request23,respons5},{request24,respons5},{request25,respons5},{request26,respons6},{request27,respons2},{request28,respons8},{request29,respons7},{request30,respons7},{request31,respons7},{request32,respons7},{request33,respons7},
					{request34,respons7},{request35,respons7},{request36,respons7},{request37,respons7},{request38,respons8},{request39,respons8},{request40,respons7},{request41,respons8}};
		}
		
		//data = new Object[][]{{request21,respons5}};
		return data;
	}
	
	
	public String invertCase(String value){
		String invertedValue = "";
		for(int i=0;i <value.length();i++){
			char c = value.charAt(i);
			if(c >= 65 && c<=90)
				c= (char)(c+32);
			else if(c>=97 && c<=122)
				c= (char)(c-32);
			invertedValue=invertedValue+c;
		}
		return invertedValue;
	}
}
