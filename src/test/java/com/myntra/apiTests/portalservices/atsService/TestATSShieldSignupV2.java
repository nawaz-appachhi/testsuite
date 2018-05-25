package com.myntra.apiTests.portalservices.atsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;
import org.testng.annotations.Test;

import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.test.commons.testbase.BaseTest;

public class TestATSShieldSignupV2 extends BaseTest implements ATSConstants {

	static Initialize init = new Initialize("Data/configuration");

	static ATSHelper atsHelper = new ATSHelper();
	
	static ATSHelperV2 atsHelperV2 = new ATSHelperV2();

	static IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
		
	static List<String> uidxList = new ArrayList<String>();
	
	static String userEmail = "",uidx = "", userStatus = "";
	
	@Test(priority = 1, alwaysRun = true)
	public static void verifyCreatedAccountActive() throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		userEmail = signupPrefix.concat("1@myntra.com");
		uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(userEmail));
		SignupHelper.verifyATSUserDetails(uidx, false,minCod_299,maxCod_20000, withTenentId);
		SignupHelper.verifyLinkedAccountsByPhone(SIGNUP_MOBILE_NUMBER, addAndSort(uidx), withTenentId);
	}
	
	@Test(priority = 2, alwaysRun = true)
	public void verifyLinkedAccountActive() throws IOException, InterruptedException {
		userEmail = signupPrefix.concat("2@myntra.com");
		uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(userEmail));
		SignupHelper.verifyATSUserDetails(uidx,false,minCod_299,maxCod_20000, withTenentId);
		SignupHelper.verifyLinkedAccountsByPhone(SIGNUP_MOBILE_NUMBER, addAndSort(uidx), withTenentId);
	}
	
	@Test(priority = 3, alwaysRun = true)
	public void verifyBlackListedStatus() throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		String userEmail1 = signupPrefix.concat("1@myntra.com");
		String uidx1 = ideaApiHelper.getUIDXForLoginViaEmail("myntra", userEmail1);
		SignupHelper.changeUserStatus(uidx1, blockListed);
		SignupHelper.assertBlacklistedState(SignupHelper.getUserStatus(userEmail1));
		String userEmail2 = signupPrefix.concat("2@myntra.com");
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(userEmail2));
		String userEmail3 = signupPrefix.concat("3@myntra.com");
		String uidx3 = atsHelper.performSignup(userEmail3).getEntry().getUidx();
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(userEmail3));
		SignupHelper.verifyATSUserDetails(uidx3, false,minCod_299,maxCod_20000, withTenentId);
		SignupHelper.verifyLinkedAccountsByPhone(SIGNUP_MOBILE_NUMBER, addAndSort(uidx3), withTenentId);
	}
	
	@Test(priority = 4, alwaysRun = true)
	public void verifyBlacklistAllandCreateNew() throws IOException, JSONException, InterruptedException {
		for (int i = 1; i <= 3; i++) {
			String emailId = signupPrefix + String.valueOf(i) + "@myntra.com";
			String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", emailId);
			SignupHelper.changeUserStatus(uidx, blockListed);
			SignupHelper.assertBlacklistedState(SignupHelper.getUserStatus(emailId));
		}
		String userEmail = signupPrefix.concat("4@myntra.com");
		String uidx4 = atsHelper.performSignup(userEmail).getEntry().getUidx();
		SignupHelper.assertBlacklistedState(SignupHelper.getUserStatus(userEmail));
		SignupHelper.verifyATSUserDetails(uidx4, false, minCod_299,maxCod_20000, withTenentId);
		SignupHelper.verifyLinkedAccountsByPhone(SIGNUP_MOBILE_NUMBER, addAndSort(uidx4), withTenentId);
	}
	
	@Test(priority = 5, alwaysRun = true)
	public void verifyBlocklistedRAUserBlocklisted() throws IOException, InterruptedException {
		String emailId = signupPrefix.concat("4@myntra.com");
		String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", emailId);
		atsHelperV2.putReturnAbuserDetails(uidx, true, withTenentId);
		SignupHelper.assertBlacklistedState(SignupHelper.getUserStatus(emailId));
		SignupHelper.verifyATSUserDetails(uidx, true,minCod_299,maxCod_20000, withTenentId);

		String userEmail = signupPrefix.concat("5@myntra.com");
		String uidx5  = atsHelper.performSignup(userEmail).getEntry().getUidx();
		SignupHelper.assertBlacklistedState(SignupHelper.getUserStatus(userEmail));
		SignupHelper.verifyATSUserDetails(uidx5, false,minCod_299,maxCod_20000, withTenentId);
		SignupHelper.verifyLinkedAccountsByPhone(SIGNUP_MOBILE_NUMBER, addAndSort(uidx5), withTenentId);
	}
	
	@Test(priority = 6, alwaysRun = true)
	public void VerifyAllAccountsActiveandCreatedUserActive() throws IOException, InterruptedException, JSONException {
		for (int i = 1; i <= 5; i++) {
			String emailId = signupPrefix + String.valueOf(i) + "@myntra.com";
			String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", emailId);
			SignupHelper.changeUserStatus(uidx, active);
			SignupHelper.assertActiveState(SignupHelper.getUserStatus(emailId));
		}

		String userEmail6 = signupPrefix.concat("6@myntra.com");
		String uidx6 = atsHelper.performSignup(userEmail6).getEntry().getUidx();
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(userEmail6));
		SignupHelper.verifyATSUserDetails(uidx6, false,minCod_299,maxCod_20000, withTenentId);
		SignupHelper.verifyLinkedAccountsByPhone(SIGNUP_MOBILE_NUMBER, addAndSort(uidx6), withTenentId);
	}
	
	@Test(priority = 7, alwaysRun = true)
	public void verifyAlluserRAandcreatedAccountActiveRA() throws IOException, InterruptedException, JSONException {
		for (int i = 1; i <= 6; i++) {
			String emailId = signupPrefix + String.valueOf(i) + "@myntra.com";
			String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", emailId);
			atsHelperV2.putReturnAbuserDetails(uidx, true, withTenentId);
			SignupHelper.assertActiveState(SignupHelper.getUserStatus(emailId));
			SignupHelper.verifyATSUserDetails(uidx,true, minCod_299,maxCod_20000, withTenentId);
		}

		String userEmail7 = signupPrefix.concat("7@myntra.com");
		String uidx7 = atsHelper.performSignup(userEmail7).getEntry().getUidx();
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(userEmail7));
		SignupHelper.verifyATSUserDetails(uidx7,true, minCod_299,maxCod_20000, withTenentId);
		SignupHelper.verifyLinkedAccountsByPhone(SIGNUP_MOBILE_NUMBER, addAndSort(uidx7), withTenentId);
	}
	
	@Test(priority = 8, alwaysRun = true)
	public void verifyRTOUserwithLabel2() throws IOException, InterruptedException {
		atsHelperV2.postATSUserDetails(ideaApiHelper.getUIDXForLoginViaEmail("myntra", signupPrefix + "7@myntra.com"),
				"2", true, withTenentId);
		
		String userEmail8 = signupPrefix.concat("8@myntra.com");
		String uidx8 = atsHelper.performSignup(userEmail8).getEntry().getUidx();
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(userEmail8));
		SignupHelper.verifyATSUserDetails(uidx8,true, minCod_299,maxCod_20000, withTenentId);
		SignupHelper.verifyLinkedAccountsByPhone(SIGNUP_MOBILE_NUMBER, addAndSort(uidx8), withTenentId);
	}
	
	@Test(priority = 9, alwaysRun = true)
	public void verifyRTOUserwithLabel1() throws IOException, InterruptedException {		
		atsHelperV2.postATSUserDetails(ideaApiHelper.getUIDXForLoginViaEmail("myntra", signupPrefix + "8@myntra.com"),
				"1", true, withTenentId);
		
		String userEmail9 = signupPrefix.concat("9@myntra.com");
		String uidx9 = atsHelper.performSignup(userEmail9).getEntry().getUidx();
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(userEmail9));
		SignupHelper.verifyATSUserDetails(uidx9,true, minCod_299,maxCod_20000, withTenentId);
		SignupHelper.verifyLinkedAccountsByPhone(SIGNUP_MOBILE_NUMBER, addAndSort(uidx9), withTenentId);
	}
	
	@Test(priority = 10, alwaysRun = true)
	public void verifyAllRTOuserCreatewithLabel2withNotRA() throws IOException, InterruptedException {
		for (int i = 1; i <= 9; i++) {
		String emailId = signupPrefix + String.valueOf(i) + "@myntra.com";
		String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", emailId);
		atsHelperV2.postATSUserDetails(uidx, "2", false, withTenentId);
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(emailId));
		SignupHelper.verifyATSUserDetails(uidx,false, minCod_299,maxCod_1500, withTenentId);
		}
		
		String userEmail10 = signupPrefix.concat("10@myntra.com");
		String uidx10 = atsHelper.performSignup(userEmail10).getEntry().getUidx();	
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(userEmail10));
		SignupHelper.verifyATSUserDetails(uidx10,false, minCod_299,maxCod_1500, withTenentId);
		SignupHelper.verifyLinkedAccountsByPhone(SIGNUP_MOBILE_NUMBER, addAndSort(uidx10), withTenentId);
	}
	
	@Test(priority = 11, alwaysRun = true)
	public void verifyAllRTOuserCreatewithLabel2withRA() throws IOException, InterruptedException {
		for (int i = 1; i <= 10; i++) {
		String emailId = signupPrefix + String.valueOf(i) + "@myntra.com";
		String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", emailId);
		atsHelperV2.postATSUserDetails(uidx, "2", true, withTenentId);
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(emailId));
		SignupHelper.verifyATSUserDetails(uidx,true, minCod_299,maxCod_1500, withTenentId);
		}
		
		String userEmail11 = signupPrefix.concat("11@myntra.com");
		String uidx11 =  atsHelper.performSignup(userEmail11).getEntry().getUidx();
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(userEmail11));
		SignupHelper.verifyATSUserDetails(uidx11,true, minCod_299,maxCod_20000, withTenentId);
		SignupHelper.verifyLinkedAccountsByPhone(SIGNUP_MOBILE_NUMBER, addAndSort(uidx11), withTenentId);
	}
	
	@Test(priority = 12, alwaysRun = true)
	public void verifyAllRTOuserCreatewithLabel1withRA() throws IOException, InterruptedException {
		for (int i = 1; i <= 11; i++) {
		String emailId = signupPrefix + String.valueOf(i) + "@myntra.com";
		String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", emailId);
		atsHelperV2.postATSUserDetails(uidx, "1", true, withTenentId);
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(emailId));
		SignupHelper.verifyATSUserDetails(uidx,true, minCod_0,minCod_0, withTenentId);
		}
		
		String userEmail12 = signupPrefix.concat("12@myntra.com");
		String uidx12 = atsHelper.performSignup(userEmail12).getEntry().getUidx();
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(userEmail12));
		SignupHelper.verifyATSUserDetails(uidx12,true, minCod_299,maxCod_20000, withTenentId);
		SignupHelper.verifyLinkedAccountsByPhone(SIGNUP_MOBILE_NUMBER, addAndSort(uidx12), withTenentId);
	}
	
	@Test(priority = 13, alwaysRun = true)
	public void verifyAllRTOuserCreatewithLabel1withNotRA() throws IOException, InterruptedException {
		for (int i = 1; i <= 12; i++) {
		String emailId = signupPrefix + String.valueOf(i) + "@myntra.com";
		String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", emailId);
		atsHelperV2.postATSUserDetails(uidx, "1", false, withTenentId);
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(emailId));
		SignupHelper.verifyATSUserDetails(uidx,false, minCod_0,minCod_0, withTenentId);
		}
		
		String userEmail13 = signupPrefix.concat("13@myntra.com");
		String uidx13 = atsHelper.performSignup(userEmail13).getEntry().getUidx();
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(userEmail13));
		SignupHelper.verifyATSUserDetails(uidx13,false, minCod_0,minCod_0, withTenentId);
		SignupHelper.verifyLinkedAccountsByPhone(SIGNUP_MOBILE_NUMBER, addAndSort(uidx13), withTenentId);
	}
	
	@Test(priority = 14, alwaysRun = true)
	public void verifyAllRTOuserCreatewithLabel3() throws IOException, InterruptedException {
		String userEmail = signupPrefix.concat("3@myntra.com");
		String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", userEmail);
		atsHelperV2.postATSUserDetails(uidx, "3", false, withTenentId);

		String userEmail14 = signupPrefix.concat("14@myntra.com");
		String uidx14 = atsHelper.performSignup(userEmail14).getEntry().getUidx();
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(userEmail14));
		SignupHelper.verifyATSUserDetails(uidx14,false, minCod_299,maxCod_20000, withTenentId);
		SignupHelper.verifyLinkedAccountsByPhone(SIGNUP_MOBILE_NUMBER, addAndSort(uidx14), withTenentId);
	}
	
	@Test(priority = 15, alwaysRun = true)
	public void verifyAllRTOuserCreatewithLabel4() throws IOException, InterruptedException {
		for (int i = 1; i <= 14; i++) {
			String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", signupPrefix + String.valueOf(i) + "@myntra.com");
			atsHelperV2.postATSUserDetails(uidx, "1", false, withTenentId);
		}
		String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", signupPrefix.concat("4@myntra.com"));
		atsHelperV2.postATSUserDetails(uidx, "4", false, withTenentId);

		String userEmail15 = signupPrefix.concat("15@myntra.com");
		String uidx15 = atsHelper.performSignup(userEmail15).getEntry().getUidx();
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(userEmail15));
		SignupHelper.verifyATSUserDetails(uidx15,false, minCod_299,maxCod_20000, withTenentId);
		SignupHelper.verifyLinkedAccountsByPhone(SIGNUP_MOBILE_NUMBER, addAndSort(uidx15), withTenentId);
	}
	
	@Test(priority = 16, alwaysRun = true)
	public void verifyAllRTOuserCreatewithLabel7() throws IOException, InterruptedException {
		for (int i = 1; i <= 15; i++) {
			String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", signupPrefix + String.valueOf(i) + "@myntra.com");
			atsHelperV2.postATSUserDetails(uidx, "1", false, withTenentId);
		}
		String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", signupPrefix.concat("4@myntra.com"));
		atsHelperV2.postATSUserDetails(uidx, "7", false, withTenentId);

		String userEmail16 = signupPrefix.concat("16@myntra.com");
		String uidx16 = atsHelper.performSignup(userEmail16).getEntry().getUidx();
		SignupHelper.assertActiveState(SignupHelper.getUserStatus(userEmail16));
		SignupHelper.verifyATSUserDetails(uidx16,false, minCod_299,maxCod_20000, withTenentId);
		SignupHelper.verifyLinkedAccountsByPhone(SIGNUP_MOBILE_NUMBER, addAndSort(uidx16), withTenentId);
	}

	private static List<String> addAndSort(String uidx) {
		uidxList.add(uidx);
		Collections.sort(uidxList);
		return uidxList;
	}
}