package com.myntra.apiTests.portalservices.atsService;

import java.util.Random;
import java.util.UUID;

public interface ATSConstants {
	
	//static String tenantId = System.getenv("tenantId");
	//static String version = System.getenv("version");

	static String tenantId = "myntra";
	static String version  = "v1";
	static Boolean withTenentId = true;

	//static Boolean withTenentId = Boolean.parseBoolean(System.getenv("withTenentId"));
	
	 UUID idOne = UUID.randomUUID();
	 UUID idTwo = UUID.randomUUID();
	 static String signupPrefix = String.valueOf(idOne);
	 static String SIGNUP_DEVICEID = String.valueOf(idTwo);
	 
	 Random rand = new Random();
	 long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
	 static String SIGNUP_MOBILE_NUMBER = String.valueOf(number);

	
	String M7_ENV = "M7";
	String Fox7_ENV ="Fox7";
	String Prod_ENV ="Prod";
	String Sfqa_ENV = "sfqa";
	String Scmqa_ENV = "scmq";
	String Stage_ENV = "stage";
	
	String Password = "password123";
	String Pincode = "560068";
	String skuId_1541 = "3866";
	String skuId_1531 = "3831";
	String paramAppName = "myntra";
	
	//m7 users
	String m7_atsUser1 = "m7atsuser1@myntra.com";
	String m7_atsUser2 = "m7atsuser2@myntra.com";
	String m7_atsUser3 = "m7atsuser3@myntra.com";
	String m7_atsUser4 = "m7atsuser4@myntra.com";
	String m7_atsUser5 = "m7atsuser5@myntra.com";
	
	//stage users
	String stage_atsUser1 = "stageatsuser1@myntra.com";
	String stage_atsUser2 = "stageatsuser2@myntra.com";
	String stage_atsUser3 = "stageatsuser3@myntra.com";
	String stage_atsUser4 = "stageatsuser4@myntra.com";
	String stage_atsUser5 = "stageatsuser5@myntra.com";
	String stage_atsUser6 = "stageatsuser6@myntra.com";
	
	//SFQA Users
	String sfqa_atsUser1 = "sfqaatsuser1@myntra.com";
	String sfqa_atsUser2 = "sfqaatsuser2@myntra.com";
	String sfqa_atsUser3 = "sfqaatsuser3@myntra.com";
	String sfqa_atsUser4 = "sfqaatsuser4@myntra.com";
	String sfqa_atsUser5 = "sfqaatsuser5@myntra.com";
	
	//Stage Users Uidx
	String stage_atsUser1Uidx = "66607cca.e461.4943.9785.c9bcc4b39775q4PYyiSryW";
	String stage_atsUser2Uidx = "e1f936f7.0028.439a.9388.73efa63f81bcULRa6WA14n";
	String stage_atsUser3Uidx = "fc4706eb.4387.4dce.bac2.abcd657e9d77wXOPTUTheh";
	String stage_atsUser4Uidx = "5b5d7b4d.4b84.4e66.b08d.cb888e73c871eD3DYwDosv";
	String stage_atsUser5Uidx = "e7c77c60.396a.4043.86fc.230b063faceepQeZOIKg2E";

	
	//Stage Users Uidx
	String sfqa_atsUser1Uidx = "586c6f28.6332.4125.88e3.edd832e21f6c1hrH5Exbca";
	String sfqa_atsUser2Uidx = "58da8b72.e49f.49be.9f87.0e31e475fb34BKxdVZ41gR";
	String sfqa_atsUser3Uidx = "372dfc2f.9220.49e9.843f.dd209e238b4fPdOuyXL0PP";
	String sfqa_atsUser4Uidx = "2fe63084.3e30.402f.9eb6.38671a536777RVdBNKJ2EZ";
	String sfqa_atsUser5Uidx = "c0b4b483.0efd.487d.b584.c268f1900f57nsYncWOjxF";
 
	//
	String SIGNUP_FIRST_NAME = "First Name";
	String SIGNUP_LAST_NAME = "Last Name";
	String SIGNUP_GENDER_MALE = "M";
	String SIGNUP_TITLE_MR = "Mr";
	String SIGNUP_USERTYPE = "C";
	
	//Status Types
	String SUCCESS_STATUS_TYPE="SUCCESS";
	String FAILURE_STATUS_TYPE="ERROR";
	
	//Status Response Codes
	int VALID_RESP_CODE = 200;
	int BADREQ_RESP_CODE = 400;
	int NOTFOUND_RESP_CODE = 404;
	int INVALID_RESP_CODE = 500;
	
	
	//Sample Address
	static String address = "Test CCOD Address, 560068";
	
	static Double minCod_0 = Double.valueOf(0.0);
	static Double minCod_299 = Double.valueOf(299.0);
	static Double maxCod_1500 = Double.valueOf(1500.0);
	static Double maxCod_20000 = Double.valueOf(20000.0);
	
	static Long delay = 10000L;
	
	
	static String signupPrefix2="mnop";
	
	static String blockListed = "BLACKLISTED";
	
	static String returnAbuser = "RETURN ABUSER";
	
	static String active = "ACTIVE";
}
