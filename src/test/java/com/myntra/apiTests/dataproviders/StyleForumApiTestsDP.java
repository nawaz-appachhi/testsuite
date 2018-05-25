package com.myntra.apiTests.dataproviders;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

public class StyleForumApiTestsDP extends CommonUtils {

	String Env;
	Configuration con = new Configuration("./Data/configuration");
	static DevApiServiceHelper devApiServiceHelper = new DevApiServiceHelper();

	public StyleForumApiTestsDP() {
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}

	// Poll DP
	@DataProvider
	public Object[][] CreatePollFlow(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"What is the best shirt to wear with military green cargos?", "White Shirt", "Military Shirt", "1" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"What is the best tie to wear with Red solid shirt? ", "Black Solid Tie", "White Solid Tie", "2" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"What is the best shirt to wear with military green cargos?", "White Shirt", "Military Shirt", "1" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"What is the best tie to wear with Red solid shirt? ", "Black Solid Tie", "White Solid Tie", "2" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"What is the best shirt to wear with military green cargos?", "White Shirt", "Military Shirt", "1" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"What is the best tie to wear with Red solid shirt? ", "Black Solid Tie", "White Solid Tie", "2" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);

	}

	@DataProvider
	public Object[][] UpdatePollFlow(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"What is the best shirt to wear with military green cargos?", "White Shirt", "Military Shirt", "1",
				"What is the best shirt to wear with Red cargos?", "Black Shirt", "Orange Shirt" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"What is the best tie to wear with Red solid shirt? ", "Black Solid Tie", "White Solid Tie", "2",
				"What is the best tie to wear with Black Stripe shirt? ", "Yellow Solid Tie", "White Stripped Tie" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"What is the best shirt to wear with military green cargos?", "White Shirt", "Military Shirt", "1",
				"What is the best shirt to wear with Red cargos?", "Black Shirt", "Orange Shirt" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"What is the best tie to wear with Red solid shirt? ", "Black Solid Tile", "White Solid Tie", "2",
				"What is the best tie to wear with Black Stripe shirt? ", "Yellow Solid Tie", "White Stripped Tie" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"What is the best shirt to wear with military green cargos?", "White Shirt", "Military Shirt", "1",
				"What is the best shirt to wear with Red cargos?", "Black Shirt", "Orange Shirt" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"What is the best tie to wear with Red solid shirt? ", "Black Solid Tile", "White Solid Tie", "2",
				"What is the best tie to wear with Black Stripe shirt? ", "Yellow Solid Tie", "White Stripped Tie" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);

	}

	@DataProvider
	public Object[][] VoteForPollFlow(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"manu.chadha@myntra.com", "trinity",
				"aravind.styleforum.Automation06@myntra.com",
				"What is the best shirt to wear with military green cargos?", "White Shirt", "Military Shirt", "1" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"manu.chadha@myntra.com", "trinity",
				"aravind.styleforum.Automation06@myntra.com", "What is the best tie to wear with Red solid shirt? ",
				"Black Solid Tie", "White Solid Tie", "2" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"manu.chadha@myntra.com", "trinity",
				"aravind.styleforum.Automation06@myntra.com",
				"What is the best shirt to wear with military green cargos?", "White Shirt", "Military Shirt", "1" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"manu.chadha@myntra.com", "trinity",
				"aravind.styleforum.Automation06@myntra.com", "What is the best tie to wear with Red solid shirt? ",
				"Black Solid Tie", "White Solid Tie", "2" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"manu.chadha@myntra.com", "trinity",
				"aravind.styleforum.Automation06@myntra.com",
				"What is the best shirt to wear with military green cargos?", "White Shirt", "Military Shirt", "1" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"manu.chadha@myntra.com", "trinity",
				"aravind.styleforum.Automation06@myntra.com", "What is the best tie to wear with Red solid shirt? ",
				"Black Solid Tie", "White Solid Tie", "2" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);

	}

	@DataProvider
	public Object[][] CommentonPollFlow(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"manu.chadha@myntra.com", "trinity",
				"What is the best shirt to wear with military green cargos?", "White Shirt", "Military Shirt", "1",
				"No Comments!!!", "10" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"manu.chadha@myntra.com", "trinity",
				"What is the best tie to wear with Red solid shirt? ", "Black Solid Tie", "White Solid Tie", "2",
				"No Comments!!!", "10" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"manu.chadha@myntra.com", "trinity",
				"What is the best shirt to wear with military green cargos?", "White Shirt", "Military Shirt", "1",
				"No Comments!!!", "10" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"manu.chadha@myntra.com", "trinity",
				"What is the best tie to wear with Red solid shirt? ", "Black Solid Tie", "White Solid Tie", "2",
				"No Comments!!!", "10" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"manu.chadha@myntra.com", "trinity",
				"What is the best shirt to wear with military green cargos?", "White Shirt", "Military Shirt", "1",
				"No Comments!!!", "10" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"manu.chadha@myntra.com", "trinity",
				"What is the best tie to wear with Red solid shirt? ", "Black Solid Tie", "White Solid Tie", "2",
				"No Comments!!!", "10" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);

	}

	// Question Related Polls
	@DataProvider
	public Object[][] CreateQuestionFlow(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Which hair style will suite for square face?", "1" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"What is the best pair to go with red cargos?", "1" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] UpdateQuestionFlow(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Which hair style will suite for square face?", "1", "Which hair style will suite for square face?" };
		String[] UserData2 = { "Fox7", "manu.chadha@myntra.com", "trinity",
				"What is the best pair to go with red cargos?", "1", "Which hair style will suite for square face?" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1",
				"Which hair style will suite for square face?" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1",
				"Which hair style will suite for square face?" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1",
				"Which hair style will suite for square face?" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1",
				"Which hair style will suite for square face?" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] AnswerAQuestionFlow(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Which hair style will suite for square face?", "1", "Sack Cut" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"What is the best pair to go with red cargos?", "1", "Sack Cut" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Yes" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Yes" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Yes" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Yes" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] CommentonAnswerFlow(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Which hair style will suite for square face?", "1", "Sack Cut","Comment" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"What is the best pair to go with red cargos?", "1", "Sack Cut","Comment" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Yes","Comment" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Yes" ,"Comment"};
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Yes","Comment" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Yes","Comment" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] UpdateAnswerFlow(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Which hair style will suite for square face?", "1", "Sack Cut", "Military Cut" };
		String[] UserData2 = { "Fox7", "manu.chadha@myntra.com", "trinity",
				"What is the best pair to go with red cargos?", "1", "Sack Cut", "Military Cut" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Yes", "No" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Yes", "No" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Yes", "No" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Yes", "No" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] CommentOnQuestion(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Which hair style will suite for square face?", "1", "Comment" };
		String[] UserData2 = { "Fox7", "manu.chadha@myntra.com", "trinity",
				"What is the best pair to go with red cargos?", "1", "Comment" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Comment" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Comment" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Comment" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Comment" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] CommentOnAnswer(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Which hair style will suite for square face?", "1", "Sack Cut", "Military Cut" };
		String[] UserData2 = { "Fox7", "manu.chadha@myntra.com", "trinity",
				"What is the best pair to go with red cargos?", "1", "Sack Cut", "Military Cut" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Yes", "No" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Yes", "No" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Yes", "No" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "1", "Yes", "No" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] GetFeeds(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome", "0", "new" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome","0", "new" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome","0", "featured" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome","0", "featured" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome","0", "featured" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome","0", "featured" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
}

	// DataProviders
	@DataProvider
	public Object[][] StyleForum_Profile_GetUserProfile_SuccessResponse(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "randompass" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "randompass" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "randompass" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "randompass" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Profile_GetFeeds(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome", "ON", "1", "featured" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome", "ON", "2",
				"featured" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome", "ON", "1",
				"top" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome", "ON", "2",
				"featured" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome", "ON", "1",
				"top" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome", "ON", "2",
				"featured" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Poll_CreatePoll_verifySuccess(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a black shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Poll_CreatePoll_verifyData(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a Red tie to be worn with White Shirt?", "Yes", "No" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a whitr t-shirt to be worn with green cargos?", "Yes", "No" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Poll_UpdatePoll(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a Red tie to be worn with White Shirt?", "Yes", "No",
				"Will it look good to wear Red tie with White Shirt?", "Hope So!", "I dont think so!" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a whitr t-shirt to be worn with green cargos?", "Yes", "No",
				"Will it look good to wear Red tie with Black Shirt?", "Absolutely!", "Definitely No!" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Poll_UpdatePoll_Failure(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Will it look good to wear Red tie with White Shirt?", "Hope So!", "I dont think so!" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Will it look good to wear Red tie with Black Shirt?", "Absolutely!", "Definitely No!" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Poll_CommentonPoll_SameUser(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Will it look good to wear Red tie with White Shirt?", "Hope So!", "I dont think so!",
				"Comment added by Automation Suite with Same User" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Will it look good to wear Red tie with Black Shirt?", "Absolutely!", "Definitely No!",
				"Comment added by Automation Suite with Same User" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Poll_CommentonPoll_DifferentUser(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Will it look good to wear Red tie with White Shirt?", "Hope So!", "I dont think so!",
				"manu.chadha@myntra.com", "trinity",
				"Comment added by Automation Suite with different User" };
		String[] UserData2 = { "Fox7", "aravind.StyleForum.Automation03@myntra.com", "randompass",
				"Will it look good to wear Red tie with Black Shirt?", "Absolutely!", "Definitely No!",
				"aravind.StyleForum.Automation03@myntra.com", "randompass",
				"Comment added by Automation Suite with different User" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Poll_VoteForPoll_SameUser_VerifyVoteisAllowedOnce(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Will it look good to wear Red tie with White Shirt?", "Hope So!", "I dont think so!" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Will it look good to wear Red tie with Black Shirt?", "Absolutely!", "Definitely No!" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Poll_VoteForPoll_DifferentUser_VerifyVoteisAllowed(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Will it look good to wear Red tie with White Shirt?", "Hope So!", "I dont think so!",
				"aravind.StyleForum.Automation02@myntra.com", "randompass" };
		String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Will it look good to wear Red tie with Black Shirt?", "Absolutely!", "Definitely No!",
				"aravind.StyleForum.Automation03@myntra.com", "randompass" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	// Questions Related DataProviders
	@DataProvider
	public Object[][] StyleForum_Poll_CreateQuestion(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Which hair style will suite for square face?" };
		String[] UserData2 = { "Fox7", "manu.chadha@myntra.com", "trinity",
				"What is the best pair to go with red cargos?" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Poll_UpdateQuestion(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Which hair style will suite for square face?",
				"Which hair style will suite for round face?-Updated!!!" };
		String[] UserData2 = { "Fox7", "manu.chadha@myntra.com", "trinity",
				"What is the best pair to go with red cargos?",
				"What is the best pair to go with black cargos?-Updated!!!" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Questions_CreateQuestion(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Which hair style will suite for square face?" };
		String[] UserData2 = { "Fox7", "manu.chadha@myntra.com", "trinity",
				"What is the best pair to go with red cargos?" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Questions_UpdateQuestion(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Which hair style will suite for square face?",
				"Would you recommend a white shirt to be worn with blue denim?" };
		String[] UserData2 = { "Fox7", "manu.chadha@myntra.com", "trinity",
				"What is the best pair to go with red cargos?",
				"Would you recommend a white shirt to be worn with blue denim?" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?",
				"Which hair style will suite for square face?" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?",
				"Which hair style will suite for square face?" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?",
				"Which hair style will suite for square face?" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?",
				"Which hair style will suite for square face?" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Questions_AnswerAQuestion(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "manu.chadha@myntra.com", "trinity",
				"Which hair style will suite for square face?", "Sack Cut" };
		String[] UserData2 = { "Fox7", "manu.chadha@myntra.com", "trinity",
				"What is the best pair to go with red cargos?", "white Casual Shirt" };
		String[] UserData3 = { "PROD", "manu.chadha@myntra.com", "trinity",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes" };
		String[] UserData4 = { "PROD", "manu.chadha@myntra.com", "trinity",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes" };
		String[] UserData5 = { "Functional", "manu.chadha@myntra.com", "trinity",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes" };
		String[] UserData6 = { "Functional", "manu.chadha@myntra.com", "trinity",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Questions_UpdateAnAnswer(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Which hair style will suite for square face?", "Sack Cut", "A Military cut would be much better!" };
		String[] UserData2 = { "Fox7", "manu.chadha@myntra.com", "trinity",
				"What is the best pair to go with red cargos?", "white Casual Shirt",
				"A Green T shirt will also suit!" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Sack Cut",
				"A Military cut would be much better!" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Sack Cut",
				"A Military cut would be much better!" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Sack Cut",
				"A Military cut would be much better!" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Sack Cut",
				"A Military cut would be much better!" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Questions_GetAnswers_Text_VerifySuccess(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.StyleForum.Automation03@myntra.com", "randompass",
				"Which hair style will suite for square face?", "Short and Sweet",
				"A Military cut would be much better!" };
		String[] UserData2 = { "Fox7", "aravind.StyleForum.Automation04@myntra.com", "randompass",
				"What is the best pair to go with red cargos?", "white Casual Shirt",
				"A Green T shirt will also suit!" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Sack Cut",
				"A Military cut would be much better!" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Sack Cut",
				"A Military cut would be much better!" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Sack Cut",
				"A Military cut would be much better!" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Sack Cut",
				"A Military cut would be much better!" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Questions_GetMulitpleAnswersByDifferentUsers_verifyData(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.StyleForum.Automation03@myntra.com",
				"aravind.StyleForum.Automation02@myntra.com", "aravind.StyleForum.Automation04@myntra.com",
				"randompass", "Question" };
		String[] UserData2 = { "Fox7", "aravind.StyleForum.Automation03@myntra.com",
				"aravind.StyleForum.Automation02@myntra.com", "aravind.StyleForum.Automation04@myntra.com",
				"randompass", "Question" };
		String[] UserData3 = { "PROD", "aravind.StyleForum.Automation03@myntra.com",
				"aravind.StyleForum.Automation02@myntra.com", "aravind.StyleForum.Automation04@myntra.com",
				"randompass", "Question" };
		String[] UserData4 = { "PROD", "aravind.StyleForum.Automation03@myntra.com",
				"aravind.StyleForum.Automation02@myntra.com", "aravind.StyleForum.Automation04@myntra.com",
				"randompass", "Question" };
		String[] UserData5 = { "Functional", "aravind.StyleForum.Automation03@myntra.com",
				"aravind.StyleForum.Automation02@myntra.com", "aravind.StyleForum.Automation04@myntra.com",
				"randompass", "Question" };
		String[] UserData6 = { "Functional", "aravind.StyleForum.Automation03@myntra.com",
				"aravind.StyleForum.Automation02@myntra.com", "aravind.StyleForum.Automation04@myntra.com",
				"randompass", "Question" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Questions_AnswerSpamQuestion_VerifyFailure(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Which hair style will suite for square face?", "Sack Cut" };
		String[] UserData2 = { "Fox7", "manu.chadha@myntra.com", "trinity",
				"What is the best pair to go with red cargos?", "white Casual Shirt" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	// Data providers for improvised test case flows

	@DataProvider
	public Object[][] StyleForum_Poll_CreatePoll_Flow_verifySuccess(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
				"Which hair style will suite for square face?", "Sack Cut", "Short Cut", "1" };
		String[] UserData2 = { "Fox7", "manu.chadha@myntra.com", "trinity",
				"What is the best pair to go with red cargos?", "white Casual Shirt", "Blue T Shirt", "1" };
		String[] UserData3 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No", "1" };
		String[] UserData4 = { "PROD", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No", "1" };
		String[] UserData5 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No", "1" };
		String[] UserData6 = { "Functional", "aravind.raj@myntra.com", "welcome",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No", "1" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] StyleForum_Poll_CreatePoll_Flow_ProfileCount(ITestContext testContext) {
		String[] UserData1 = { "Fox7", "manu.chadha@myntra.com", "trinity",
				"Which hair style will suite for square face?", "Sack Cut", "Short Cut", "1" };
		String[] UserData2 = { "Fox7", "manu.chadha@myntra.com", "trinity",
				"What is the best pair to go with red cargos?", "white Casual Shirt", "Blue T Shirt", "1" };
		String[] UserData3 = { "PROD", "manu.chadha@myntra.com", "trinity",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No", "1" };
		String[] UserData4 = { "PROD", "manu.chadha@myntra.com", "trinity",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No", "1" };
		String[] UserData5 = { "Functional", "manu.chadha@myntra.com", "trinity",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No", "1" };
		String[] UserData6 = { "Functional", "manu.chadha@myntra.com", "trinity",
				"Would you recommend a white shirt to be worn with blue denim?", "Yes", "No", "1" };
		Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	// Question Related Polls
		@DataProvider
		public Object[][] CreateQuestionFlow_ProfileCount(ITestContext testContext) {
			String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome",
					"Which hair style will suite for square face?", "1" };
			String[] UserData2 = { "Fox7", "aravind.raj@myntra.com", "welcome",
					"What is the best pair to go with red cargos?", "1" };
			String[] UserData3 = { "PROD", "manu.chadha@myntra.com", "trinity",
					"Would you recommend a white shirt to be worn with blue denim?", "1" };
			String[] UserData4 = { "PROD", "manu.chadha@myntra.com", "trinity",
					"Would you recommend a white shirt to be worn with blue denim?", "1" };
			String[] UserData5 = { "Functional", "manu.chadha@myntra.com", "trinity",
					"Would you recommend a white shirt to be worn with blue denim?", "1" };
			String[] UserData6 = { "Functional", "manu.chadha@myntra.com", "trinity",
					"Would you recommend a white shirt to be worn with blue denim?", "1" };
			Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
			return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
		}

		//Fixes
		@DataProvider
		public Object[][] GetUserFeeds(ITestContext testContext) {
			String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome"};
			String[] UserData2 = { "PROD", "aravind.raj@myntra.com", "welcome"};
			String[] UserData3 = { "Functional", "aravind.raj@myntra.com", "welcome"};
			Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3};
			return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
		}
		
		@DataProvider
		public Object[][] GetUserFeeds_ScrollFeeds(ITestContext testContext) {
			String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome","0", "featured" };
			String[] UserData2 = { "PROD", "aravind.raj@myntra.com", "welcome","0", "featured" };
			String[] UserData3 = { "Functional", "aravind.raj@myntra.com", "welcome","0", "featured" };
			Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3};
			return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
		}
		
		@DataProvider
		public Object[][] Create_TextPoll(ITestContext testContext) {
			String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome","POll Description","Option 1","Option 2" };
			String[] UserData2 = { "PROD", "aravind.raj@myntra.com", "welcome","The best shoe to wear with Denim blue jean would be?","Black Sports Shoe","White Casual Shoe" };
			String[] UserData3 = { "Functional", "aravind.raj@myntra.com", "welcome","POll Description","Option 1","Option 2" };
			Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3};
			return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
		}
		
		@DataProvider
		public Object[][] Create_ProductPoll(ITestContext testContext) {
			String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome","POll Description","298476","297222" };
			String[] UserData2 = { "PROD", "aravind.raj@myntra.com", "welcome","The best shoe to wear with Denim blue jean would be?","298476","297222" };
			String[] UserData3 = { "Functional", "aravind.raj@myntra.com", "welcome","POll Description","298476","297222" };
			Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3};
			return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
		}
		
		@DataProvider
		public Object[][] Create_PhotoPoll(ITestContext testContext) {
			String[] UserData1 = { "Fox7", "aravind.raj@myntra.com", "welcome","POll Description","308396","8971" };
			String[] UserData2 = { "PROD", "aravind.raj@myntra.com", "welcome","The best shoe to wear with Denim blue jean would be?","308396","8971"  };
			String[] UserData3 = { "Functional", "aravind.raj@myntra.com", "welcome","POll Description","308396","8971" };
			Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3};
			return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
		}
		
		
		

	
}
