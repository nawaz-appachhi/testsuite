package com.myntra.apiTests.dataproviders;
import org.testng.AssertJUnit;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.legolas.Commons;
import com.myntra.apiTests.common.Myntra;


public class FashionCornerApisDP {
	static Initialize init = new Initialize("/Data/configuration");
	Commons comUtil = new Commons();
	List<String> ids = new ArrayList();
	List<String> emails = new ArrayList();
	String completeInfoOfUserDefault = "id:${id},name:${name},email:${email},role:${role},from:${name},bio:${bio}";
	String completeInfoOfUserCreateUser = null;
	boolean isCreateUser = false;
	ArrayList styleIds = new ArrayList();

	@DataProvider
	public Object[][] createUserPositiveDP(ITestContext testContext)
	{
		//Will use these list in getUser
		String id1 = getRandomId();
		String id2 = getRandomId();
		String name1 = getName();
		String name2 = getName();
		String email1 = getEmail();
		String email2 = getEmail();
		String role1 = getRole();
		String role2 = getRole();
		String bio1 = getBio(50);
		String bio2 = getBio(100);
		completeInfoOfUserCreateUser = "id:${id},name:${name},email:${email},role:${role},from:${from},bio:${bio}";
		completeInfoOfUserCreateUser = completeInfoOfUserCreateUser.replace("${id}", id1).replace("${email}", email1).replace("${name}", name1).replace("${bio}", bio1).replace("${role}", role1).replace("${from}", name1);
		ids.add(id1);
		ids.add(id2);
		emails.add(email1);
		emails.add(email1);
		isCreateUser = true;

		String[] input1 = {id1,name1, email1, role1, name1, bio1};
		String[] input2 = {id2,name2, email2, role2, name2, bio2};
		Object[][] dataSet = new Object[][] { input1, input2 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] createUserNegativeDP(ITestContext testContext)
	{
		ids.add("H9483");
		//Existing and blank
		String[] input1 = {ids.get(comUtil.randomNumberUptoLimit(ids.size())),"CreateUserNegative_Name", "CreateUserNegative"+getEmail(), "CreateUserNegative_Role", "CreateUserNegative_City", "CreateUserNegative_Bio Existing user"};
		String[] input2 = {" ","CreateUserNegative_Name", "CreateUserNegative_Email"+getEmail(), "CreateUserNegative_Role", "CreateUserNegative_City", "CreateUserNegative_Bio Existing user"};
		String[] input3 = {"CreateUserNegative_id"," ", "CreateUserNegative_Email"+getEmail(), "CreateUserNegative_Role", "CreateUserNegative_City", "CreateUserNegative_Bio Existing user"};
		String[] input4 = {"CreateUserNegative_id","CreateUserNegative_Name", " ", "CreateUserNegative_Role", "CreateUserNegative_City", "CreateUserNegative_Bio Existing user"};
		String[] input5 = {"CreateUserNegative_id","CreateUserNegative_Name", "CreateUserNegative_Email"+getEmail(), " ", "CreateUserNegative_City", "CreateUserNegative_Bio Existing user"};
		String[] input6 = {"CreateUserNegative_id","CreateUserNegative_Name", "CreateUserNegative_Email"+getEmail(), "CreateUserNegative_Role", " ", "CreateUserNegative_Bio Existing user"};
		String[] input7 = {"CreateUserNegative_id","CreateUserNegative_Name", "CreateUserNegative_Email"+getEmail(), "CreateUserNegative_Role", "CreateUserNegative_City", " "};

		//null
		String[] input8 = {null,"CreateUserNegative_Name", "CreateUserNegative_Email"+getEmail(), "CreateUserNegative_Role", "CreateUserNegative_City", "CreateUserNegative_Bio Existing user"};
		String[] input9 = {"CreateUserNegative_id", null, "CreateUserNegative_Email"+getEmail(), "CreateUserNegative_Role", "CreateUserNegative_City", "CreateUserNegative_Bio Existing user"};
		String[] input10 = {"CreateUserNegative_id","CreateUserNegative_Name", null, "CreateUserNegative_Role", "CreateUserNegative_City", "CreateUserNegative_Bio Existing user"};
		String[] input11 = {"CreateUserNegative_id","CreateUserNegative_Name", "CreateUserNegative_Email"+getEmail(), null, "CreateUserNegative_City", "CreateUserNegative_Bio Existing user"};
		String[] input12 = {"CreateUserNegative_id","CreateUserNegative_Name", "CreateUserNegative_Email"+getEmail(), "CreateUserNegative_Role", null, "CreateUserNegative_Bio Existing user"};
		String[] input13 = {"CreateUserNegative_id","CreateUserNegative_Name", "CreateUserNegative_Email"+getEmail(), "CreateUserNegative_Role", "CreateUserNegative_City", null};

		//Special Chars
		String[] input14 = {"~!@#$%^&*()","CreateUserNegative_Name", "CreateUserNegative_Email"+getEmail(), "CreateUserNegative_Role", "CreateUserNegative_City", "CreateUserNegative_Bio Existing user"};
		String[] input15 = {"CreateUserNegative_id", "~!@#$%^&*()", "CreateUserNegative_Email"+getEmail(), "CreateUserNegative_Role", "CreateUserNegative_City", "CreateUserNegative_Bio Existing user"};
		String[] input16 = {"CreateUserNegative_id","CreateUserNegative_Name", "~!@#$%^&*()"+getEmail(), "CreateUserNegative_Role", "CreateUserNegative_City", "CreateUserNegative_Bio Existing user"};
		String[] input17 = {"CreateUserNegative_id","CreateUserNegative_Name", "CreateUserNegative_Email"+getEmail(), "~!@#$%^&*()", "CreateUserNegative_City", "CreateUserNegative_Bio Existing user"};
		String[] input18 = {"CreateUserNegative_id","CreateUserNegative_Name", "CreateUserNegative_Email"+getEmail(), "CreateUserNegative_Role", "~!@#$%^&*()", "CreateUserNegative_Bio Existing user"};
		String[] input19 = {"CreateUserNegative_id","CreateUserNegative_Name", "CreateUserNegative_Email"+getEmail(), "CreateUserNegative_Role", "CreateUserNegative_City", "~!@#$%^&*()"};
		Object[][] dataSet = new Object[][] { input1, input2, input3, input4, 
				input5, input6, input7, input8,
				input9, input10, input11, input12,
				input13, input14, input15, input16,
				input17, input18, input19
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 10, 10);
	}


	@DataProvider
	public Object[][] getUserPositiveDP(ITestContext testContext)
	{
		Object[][] dataSet;
		//Adding existing id
		String[] input1 = {"varun.leo@myntra.com","false"};
		String[] input2 = {"archita.banerjee@myntra.com","false"};
		String[] input3 = {"jatin.jay@myntra.com","false"};

		dataSet = new Object[][] { input1,
				input2,
				input3,

		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 3, 3);
	}

	@DataProvider
	public Object[][] getUserNegativeDP(ITestContext testContext)
	{
		Object[][] dataSet;
		//Adding existing id
		ids.add("H9483");
		String[] input1 = {"ABCDEF"};
		String[] input2 = {" "};
		String[] input3 = {"null"};
		String[] input4 = {"1=1' or '1=1"};
		dataSet = new Object[][] { input1,
				input2,
				input3,
				input4
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 4, 4);
	}


	@DataProvider
	public Object[][] updateExistingPositiveDP(ITestContext testContext)
	{
		//Adding existing id
		//ids.add("jatin.jay@myntra.com");

		String id1 = getRandomId();
		String id2 = getRandomId();
		String name1 = getName();
		String name2 = getName();
		String email1 = getEmail();
		String email2 = getEmail();
		String role1 = getRole();
		String role2 = getRole();
		String bio1 = getBio(50);
		String bio2 = getBio(100);
		String[] input1 = {"jatin.jay@myntra.com",name1, email1, role1, name1, bio1};
		String[] input2 = {"manu.chadha@myntra.com",name2, email2, role2, name2, bio2};
		Object[][] dataSet = new Object[][] { input1, input2 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] updateExistingNegativeDP(ITestContext testContext)
	{
		String[] input1 = {"null","updated name", "updated email", "updated role", "updated city", "updated bio"};
		String[] input2 = {" ", "updated name", "updated email", "updated role", "updated city", "updated bio"};
		String[] input3 = {"1=1' or '1=1","updated name", "updated email", "updated role", "updated city", "updated bio"};
		Object[][] dataSet = new Object[][] { input1, input2 , input3};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 3, 3);
	}


	@DataProvider
	public Object[][] updateNonExistingPositiveDP(ITestContext testContext)
	{
		String[] input1 = {getRandomId()+"Non", getName(), getEmail(), getRole(), getName(), getBio(50)};
		String[] input2 = {getRandomId()+"Non", getName(), getEmail(), getRole(), getName(), getBio(100)};
		Object[][] dataSet = new Object[][] { input1, input2 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 2);

	}
	@DataProvider
	public Object[][] deleteUserPositiveDP(ITestContext testContext)
	{
		//Will use these list in getUser
		String id1 = getRandomId();
		String name1 = getName();
		String email1 = getEmail();
		String role1 = getRole();
		String bio1 = getBio(50);
		String[] input1 = {id1,name1, email1, role1, name1, bio1};
		Object[][] dataSet = new Object[][] {input1};

		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] deleteUserNegitiveDP(ITestContext testContext)
	{
		//Will use these list in getUser
		String id1 = getRandomId();
		String[] input1 = {id1};
		Object[][] dataSet = new Object[][] {input1};

		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 2);
	}


	@DataProvider
	public Object[][] addQuestionToUserPositiveDP(ITestContext testContext)
	{
		ids.add("H9483");
		addIdsAndEmailbyCallingCreateUser();
		//Default id
		String[] input1 = {"This is test ans "+getRandomId(), "This is a test ans desc "+getRandomId(), "H9483", getRandomTrueOrFalse(), };
		//Random id
		String[] input2 = {"This is test ans "+getRandomId(), "This is a test ans desc "+getRandomId(), ids.get(comUtil.randomNumberUptoLimit(ids.size())), getRandomTrueOrFalse()};
		Object[][] dataSet = new Object[][] { input1, input2 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] addQuestionToUserNegativeDP(ITestContext testContext)
	{
		//Blank
		String[] input1 = {" ", "This is a test ans desc "+getRandomId(), "H9483", getRandomTrueOrFalse() };
		String[] input2 = {"This is test ans "+getRandomId(), " ", "H9483", getRandomTrueOrFalse() };
		String[] input3 = {"This is test ans "+getRandomId(), "This is a test ans desc "+getRandomId(), " ", getRandomTrueOrFalse() };
		String[] input4 = {"This is test ans "+getRandomId(), "This is a test ans desc "+getRandomId(), "H9483", " "};

		//null
		String[] input5 = {"null", "This is a test ans desc "+getRandomId(), "H9483", getRandomTrueOrFalse() };
		String[] input6 = {"This is test ans "+getRandomId(), "null", "H9483", getRandomTrueOrFalse() };
		String[] input7 = {"This is test ans "+getRandomId(), "This is a test ans desc "+getRandomId(), "null", getRandomTrueOrFalse() };
		String[] input8 = {"This is test ans "+getRandomId(), "This is a test ans desc "+getRandomId(), "H9483", "null"};

		//Invalid user id
		String[] input9 =  {"This is test ans "+getRandomId(), "This is a test ans desc "+getRandomId(), "1=1' or '1=1", getRandomTrueOrFalse() };
		String[] input10 = {"This is test ans "+getRandomId(), "This is a test ans desc "+getRandomId(), "ABCDEF12345", getRandomTrueOrFalse() };

		Object[][] dataSet = new Object[][] { input1, input2, input3, input4, 
				input5, input6, input7, input8,
				input9, input10
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 10, 10);
	}

	@DataProvider
	public Object[][] addAnswerdPositiveDP(ITestContext testContext)
	{
		ids.add("H9483");
		addIdsAndEmailbyCallingCreateUser();
		//Default id
		String[] input1 = {"This is test title "+getRandomId(), "This is a test desc "+getRandomId(), "H9483", getRandomTrueOrFalse(), getRandomQuestionId()};
		//Random id
		String[] input2 = {"This is test title "+getRandomId(), "This is a test desc "+getRandomId(), ids.get(comUtil.randomNumberUptoLimit(ids.size())), getRandomTrueOrFalse(),  getRandomQuestionId()};
		Object[][] dataSet = new Object[][] { input1, input2 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] addAnswerdNegativeDP(ITestContext testContext)
	{
		//Blank
		String[] input1 = {" ", "This is a test desc "+getRandomId(), "H9483", getRandomTrueOrFalse(), getRandomQuestionId()};
		String[] input2 = {"This is test title "+getRandomId(), " ", "H9483", getRandomTrueOrFalse(), getRandomQuestionId()};
		String[] input3 = {"This is test title "+getRandomId(), "This is a test desc "+getRandomId(), " ", getRandomTrueOrFalse(), getRandomQuestionId()};
		String[] input4 = {"This is test title "+getRandomId(), "This is a test desc "+getRandomId(), "H9483", " ", getRandomQuestionId()};
		String[] input5 = {"This is test title "+getRandomId(), "This is a test desc "+getRandomId(), "H9483", getRandomTrueOrFalse(), " "};
		//null
		String[] input6 = {"null", "This is a test desc "+getRandomId(), "H9483", getRandomTrueOrFalse(), getRandomQuestionId()};
		String[] input7 = {"This is test title "+getRandomId(), "null", "H9483", getRandomTrueOrFalse(), getRandomQuestionId()};
		String[] input8 = {"This is test title "+getRandomId(), "This is a test desc "+getRandomId(), "null", getRandomTrueOrFalse(), getRandomQuestionId()};
		String[] input9 = {"This is test title "+getRandomId(), "This is a test desc "+getRandomId(), "H9483", "null", getRandomQuestionId()};
		String[] input10 = {"This is test title "+getRandomId(), "This is a test desc "+getRandomId(), "H9483", getRandomTrueOrFalse(), "null"};
		//Invalid quesId
		String[] input11 = {"This is test title "+getRandomId(), "This is a test desc "+getRandomId(), "H9483", getRandomTrueOrFalse(), "0000001"};
		String[] input12 = {"This is test title "+getRandomId(), "This is a test desc "+getRandomId(), "H9483", getRandomTrueOrFalse(), "01001101"};
		Object[][] dataSet = new Object[][] { input1, input2, input3, input4, 
				input5, input6, input7, input8,
				input9, input10, input11, input12
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 12,12);
	}

	@DataProvider
	public Object[][] addTagToUserPositiveDP(ITestContext testContext)
	{
		ids.add("H9483");
		addIdsAndEmailbyCallingCreateUser();
		String[] tags = {"ethnic","western","hiphop","trendy","designer","xyz"};
		//Default id
		String[] input1 = {tags[comUtil.randomNumberUptoLimit(tags.length)]+getRandomId(), ids.get(comUtil.randomNumberUptoLimit(ids.size()))};
		//Random id
		String[] input2 = {tags[comUtil.randomNumberUptoLimit(tags.length)]+getRandomId(), ids.get(comUtil.randomNumberUptoLimit(ids.size()))};
		Object[][] dataSet = new Object[][] { input1, input2 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] addTagToUserNegativeDP(ITestContext testContext)
	{
		String[] input1 = {" ", "H9483"};
		String[] input2 = {"null", "H9483"};
		String[] input3 = {"ethnic", " "};
		String[] input4 = {"ethnic", "null"};
		String[] input5 = {"!@#$%^&*()_+", "H9483"};
		String[] input6 = {"ethnic", "!@#$%^&*()_+"};
		String[] input7 = {"ethnic", "1=1' or '1=1"};
		Object[][] dataSet = new Object[][] { input1, input2, input3, input4, 
				input5, input6, input7
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 7, 7);
	}


	@DataProvider
	public Object[][] updateQuestionPositiveDP(ITestContext testContext)
	{
		//ids.add("H9483");
		//addIdsAndEmailbyCallingCreateUser();
		//Default id
		String[] input1 = {"This is updated title "+getRandomId(), "This is a updated desc "+getRandomId(), getRandomQuestionId()};
		//Random id
		String[] input2 = {"This is updated title "+getRandomId(), "This is a updated desc "+getRandomId(), getRandomQuestionId()};
		Object[][] dataSet = new Object[][] { input1, input2 };		
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] updateQuestionNegativeDP(ITestContext testContext)
	{
		//ids.add("H9483");
		//addIdsAndEmailbyCallingCreateUser();
		//Default id
		String[] input1 = {"This is updated title "+getRandomId(), "This is a updated desc "+getRandomId(), "ABCD0002"};
		//Random id
		String[] input2 = {"This is updated title "+getRandomId(), "This is a updated desc "+getRandomId(), "1=1' or '1=1"};
		//correct question id
		String[] input3 = {" ", "null", getRandomQuestionId()};
		String[] input4 = {"null", " ", getRandomQuestionId()};
		Object[][] dataSet = new Object[][] { input1, input2, input3, input4 };		
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 4, 4);
	}

	@DataProvider
	public Object[][] getAnswerSummaryPositiveDP(ITestContext testContext)
	{
		ids.add("H9483");
		//Default id
		String[] input1 = {"H9483"};
		//Random id
		String[] input2 = {ids.get(comUtil.randomNumberUptoLimit(ids.size()))};
		Object[][] dataSet = new Object[][] { input1, input2 };		
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] getAnswerSummaryNegativeDP(ITestContext testContext)
	{
		ids.add("H9483");
		String[] input1 = {"1=1' or '1=1"};
		String[] input2 = {ids.get(comUtil.randomNumberUptoLimit(ids.size()))+"ABCD0003"};
		Object[][] dataSet = new Object[][] { input1, input2 };		
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] getQuestionSummaryPositiveDP(ITestContext testContext)
	{
		ids.add("H9483");
		//Default id
		String[] input1 = {"H9483"};
		//Random id
		String[] input2 = {ids.get(comUtil.randomNumberUptoLimit(ids.size()))};
		Object[][] dataSet = new Object[][] { input1, input2 };		
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] getQuestionSummaryNegativeDP(ITestContext testContext)
	{
		ids.add("H9483");
		String[] input1 = {"1=1' or '1=1"};
		String[] input2 = {ids.get(comUtil.randomNumberUptoLimit(ids.size()))+"ABCD0003"};
		Object[][] dataSet = new Object[][] { input1, input2 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] updateAnswerPositiveDP(ITestContext testContext)
	{
		//ids.add("H9483");
		//addIdsAndEmailbyCallingCreateUser();
		//Default id
		String[] input1 = {"This is updated title "+getRandomId(), "This is a updated desc "+getRandomId(), getRandomAnswerId()};
		//Random id
		String[] input2 = {"This is updated title "+getRandomId(), "This is a updated desc "+getRandomId(), getRandomAnswerId()};
		Object[][] dataSet = new Object[][] { input1, input2 };		
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] updateAnswerNegativeDP(ITestContext testContext)
	{
		String[] input1 = {"This is updated title "+getRandomId(), "This is a updated desc "+getRandomId(), "ABCD0004"};
		String[] input2 = {"This is updated title "+getRandomId(), "This is a updated desc "+getRandomId(), "1=1' or '1=1"};
		String[] input3 = {" ", "null", getRandomAnswerId()};
		String[] input4 = {"null", " ", getRandomAnswerId()};
		Object[][] dataSet = new Object[][] { input1, input2, input3, input4 };		
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 4, 4);
	}

	@DataProvider
	public Object[][] addTagToUserViaUsereDP(ITestContext testContext)
	{
		ids.add("H9483");
		addIdsAndEmailbyCallingCreateUser();
		String[] tags = {"ethnic","western","hiphop","trendy","designer","xyz"};
		//Default id
		String[] input1 = {tags[comUtil.randomNumberUptoLimit(tags.length)]+getRandomId(), ids.get(comUtil.randomNumberUptoLimit(ids.size()))};
		//Random id
		String[] input2 = {tags[comUtil.randomNumberUptoLimit(tags.length)]+getRandomId(), ids.get(comUtil.randomNumberUptoLimit(ids.size()))};
		Object[][] dataSet = new Object[][] { input1, input2 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] addTagToUserViaUsereNegativeDP(ITestContext testContext)
	{
		ids.add("H9483");
		addIdsAndEmailbyCallingCreateUser();
		String[] input1 = {" ", ids.get(comUtil.randomNumberUptoLimit(ids.size()))};
		String[] input2 = {"null", ids.get(comUtil.randomNumberUptoLimit(ids.size()))};
		String[] input3 = {" ", "1=1' or '1=1"};
		String[] input4 = {"!@#$%^^&*(", ids.get(comUtil.randomNumberUptoLimit(ids.size()))};
		String[] input5 = {"!@#$%^^&*(", "ABCD0005"};
		Object[][] dataSet = new Object[][] { input1, input2, input3, input4, input5 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 5, 5);
	}

	@DataProvider
	public Object[][] fcQuestionAddTagToQuestionPositiveDP(ITestContext testContext)
	{
		ids.add("H9483");
		addIdsAndEmailbyCallingCreateUser();
		String[] tags = {"ethnic","western","hiphop","trendy","designer","xyz"};
		//Default id
		String[] input1 = {tags[comUtil.randomNumberUptoLimit(tags.length)]+getRandomId(), getRandomQuestionId()};
		//Random id
		String[] input2 = {tags[comUtil.randomNumberUptoLimit(tags.length)]+getRandomId(), getRandomQuestionId()};
		Object[][] dataSet = new Object[][] { input1, input2 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] fcQuestionAddTagToQuestionNegativeDP(ITestContext testContext)
	{
		String[] tags = {"ethnic","western","hiphop","trendy","designer","xyz"};
		//Default id
		String[] input1 = {tags[comUtil.randomNumberUptoLimit(tags.length)]+getRandomId(), "001109901"};
		//Random id
		String[] input2 = {tags[comUtil.randomNumberUptoLimit(tags.length)]+getRandomId(), "87766A"};
		Object[][] dataSet = new Object[][] { input1, input2 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	//fc-outfit

	@DataProvider
	public Object[][] fcoutfitCreateStylePositiveDP(ITestContext testContext)
	{
		int styleId1 = new Random().nextInt(1000000);
		int styleId2 = new Random().nextInt(2000000);
		styleIds.add(styleId1);
		styleIds.add(styleId2);
		Object[][] dataSet = new Object[][] { new String[]{styleId1+"", "Desc of style!!!", "false"},
				//	new String[]{"11234", "Desc of style Hardcoaded!!!", "true"},
				new String[]{styleId2+"", "Desc of style!!!", "false"}
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 3, 3);
	}

	@DataProvider
	public Object[][] fcoutfitCreateStyleNegativeDP(ITestContext testContext)
	{
		int styleId1 = new Random().nextInt(1000000);
		int styleId2 = new Random().nextInt(2000000);
		styleIds.add(styleId1);
		styleIds.add(styleId2);
		Object[][] dataSet = new Object[][] { new String[]{"-"+styleId1+"", "Desc of style!!!"},
				new String[]{"123456", "Desc of style Hardcoaded!!!"},
				new String[]{"12345", "Desc of style!!!"}
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 3, 3);
	}

	@DataProvider
	public Object[][] fcoutfitGetStyleDataPositiveDP(ITestContext testContext)
	{
		if(styleIds.size()==0){
			styleIds.add("3");
			styleIds.add("4");
			styleIds.add("5");
		}
		Object[][] dataSet;
		if(styleIds.size()==1)
		{
			dataSet = new Object[][] { new String[]{styleIds.get(0)+""}};
		}else if(styleIds.size()==2){
			dataSet = new Object[][] { new String[]{styleIds.get(0)+""},
					new String[]{styleIds.get(1)+""}
			};
		}else{
			dataSet = new Object[][] { new String[]{styleIds.get(0)+""},
					new String[]{styleIds.get(1)+""},
					new String[]{styleIds.get(2)+""}
			};
		}
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] fcoutfitGetStyleDataNegativeDP(ITestContext testContext)
	{
		/*
		styleIds.clear();
		styleIds.add("ABCD0001");
		styleIds.add("1=1' or '1=1");
		styleIds.add("\"null\"");
		 */

		Object[][] dataSet = new Object[][] { new String[]{"ABCD0001"},
				new String[]{"1=1' or '1=1"},
				new String[]{"\"null\""}
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}


	@DataProvider
	public Object[][] fcoutfitCreateOutfitPositiveDP(ITestContext testContext)
	{

		String name=getName();
		String likeCount=getRandomLikeCount(100);
		String layout=getRandomLayoutCount(1);
		String UserId="manu.chadha@myntra.com";
		String occasions1=getOccassion();
		String occasions2=getOccassion();
		String gender=getGenderMaleOrFemale();
		String description=getBio(20);
		String stylesid1=String.valueOf(new Random().nextInt(1000000));
		String styleDescription=getBio(20);
		String stylePosition=getStylePositionCount(3);	
		String[] inputA = {name,likeCount,layout,UserId,occasions1,occasions2,gender,description,stylesid1,styleDescription,stylePosition};
		Object[][] dataSet = new Object[][] {inputA};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] fcoutfitCreateOutfitNegitiveDP(ITestContext testContext)
	{

		String name=getName();
		String likeCount=getRandomLikeCount(100);
		String layout=getRandomLayoutCount(1);
		String UserId="manu.chadha1@myntra.com";
		String occasions1=getOccassion();
		String occasions2=getOccassion();
		String gender=getGenderMaleOrFemale();
		String description=getBio(20);
		String stylesid1=String.valueOf(new Random().nextInt(1000000));
		String styleDescription=getBio(20);
		String stylePosition=getStylePositionCount(3);	
		String[] inputA = {name,likeCount,layout,UserId,occasions1,occasions2,gender,description,stylesid1,styleDescription,stylePosition};
		Object[][] dataSet = new Object[][] {inputA};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] fcoutfitCreateAndUpdateOutfitPositiveDP(ITestContext testContext)
	{
		String createdOutfit[]=createNewOutfit();
		String name=getName();
		String likeCount=getRandomLikeCount(100);
		String layout=getRandomLayoutCount(1);
		String UserId="manu.chadha@myntra.com";
		String occasions1=getOccassion();
		String occasions2=getOccassion();
		String gender=getGenderMaleOrFemale();
		String description=getBio(20);
		String stylesid1=String.valueOf(new Random().nextInt(1000000));
		String styleDescription=getBio(20);
		String stylePosition=getStylePositionCount(3);	
		String[] inputA = {name,likeCount,layout,UserId,occasions1,occasions2,gender,description,createdOutfit[1],styleDescription,stylePosition,createdOutfit[0],createdOutfit[1]};
		Object[][] dataSet = new Object[][] {inputA};
		System.out.println(dataSet);
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] fcoutfitCreateOutfitAndReturnOutfitIdPositiveDP(ITestContext testContext)
	{
		String createdOutfit[]=createNewOutfit();
		String[] inputA = {createdOutfit[0]};
		Object[][] dataSet = new Object[][] {inputA};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}


	@DataProvider
	public Object[][] fcoutfitNegitiveDP(ITestContext testContext)
	{
		String[] inputA = {getRandomNo(6)};
		String[] inputB = {"^"+getRandomSpecialCharactor(6)};
		String[] inputC = {" "};
		String[] inputD = {"null"};
		String[] inputE = {"1=1' or '1=1"};
		String[] inputF = {"ABCD0005"};
		String[] inputG = {"**#^(%/%&#/#*@"};		
		Object[][] dataSet = new Object[][] {inputA,inputB,inputC,inputD,inputE,inputF,inputG};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 6, 6);
	}

	@DataProvider
	public Object[][] fcoutfitSearchPositiveDP(ITestContext testContext)
	{
		String[] inputA = {"4"};	
		String[] inputB = {"5"};	
		Object[][] dataSet = new Object[][] {inputA,inputB};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 6, 6);
	}

	@DataProvider
	public Object[][] fcoutfitSearchNegitiveDP(ITestContext testContext)
	{
		String[] inputA = {getRandomNo(6)};
		String[] inputD = {"null"};	
		Object[][] dataSet = new Object[][] {inputA,inputD};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 6, 6);
	}



	@DataProvider
	public Object[][] fcoutfitCreateOutfitNegativeDP(ITestContext testContext)
	{
		ids.add("H9483");
		addIdsAndEmailbyCallingCreateUser();
		Object[][] dataSet = new Object[][] { new String[]{ids.get(comUtil.randomNumberUptoLimit(ids.size()))+"ABCD0006"},
				new String[]{"1=1' or '1=1"}
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] fcoutfitAddStyletoOutfitPositiveDP(ITestContext testContext)
	{
		ids.add("H9483");
		addIdsAndEmailbyCallingCreateUser();
		Object[][] dataSet = new Object[][] { new String[]{ids.get(comUtil.randomNumberUptoLimit(ids.size())), (getOutfitData(ids.get(comUtil.randomNumberUptoLimit(ids.size())))+1000000), "3"},
				new String[]{ids.get(comUtil.randomNumberUptoLimit(ids.size())), getOutfitData(ids.get(comUtil.randomNumberUptoLimit(ids.size())))+5656565, new Random().nextInt(1000)+""}
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] fcoutfitAddStyletoOutfitNegativeDP(ITestContext testContext)
	{
		ids.add("H9483");
		addIdsAndEmailbyCallingCreateUser();
		Object[][] dataSet = new Object[][] { new String[]{ids.get(comUtil.randomNumberUptoLimit(ids.size()))+"ABCD0007", getOutfitData(ids.get(comUtil.randomNumberUptoLimit(ids.size()))), "3"},
				new String[]{ids.get(comUtil.randomNumberUptoLimit(ids.size())), getOutfitData(ids.get(comUtil.randomNumberUptoLimit(ids.size()))), new Random().nextInt(1000)+""}
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	//fc-comment
	@DataProvider
	public Object[][] fccommentAddCommentToUserAndAnsPositiveDP(ITestContext testContext)
	{
		ids.add("H9483");
		addIdsAndEmailbyCallingCreateUser();
		String[] inputA = {"Title of question", "This is a discription", ids.get(comUtil.randomNumberUptoLimit(ids.size())), "true", getRandomAnswerId()};
		String[] inputB = {"Title of question", "This is a discription", ids.get(comUtil.randomNumberUptoLimit(ids.size())), "false", getRandomAnswerId()};
		Object[][] dataSet = new Object[][] {inputA, inputB };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] fccommentAddCommentToUserAndAnsNegativeDP(ITestContext testContext)
	{
		ids.add("H9483");
		addIdsAndEmailbyCallingCreateUser();
		String[] inputA = {"Title of question", "This is a discription", ids.get(comUtil.randomNumberUptoLimit(ids.size())), "true", "ABCD001"};
		String[] inputB = {"Title of question", "This is a discription", ids.get(comUtil.randomNumberUptoLimit(ids.size())), "false", "1=1' or '1=1"};
		Object[][] dataSet = new Object[][] {inputA, inputB };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	//fc-answer
	@DataProvider
	public Object[][] fcanswerAddTagToAnswerPositiveDP(ITestContext testContext)
	{
		String[] tags = {"Trendy","Ethnic","Winter","Summer","Monsoon","Wedding","Sports","Formal","Casual"};
		Object[][] dataSet = new Object[][] { new String[]{tags[comUtil.randomNumberUptoLimit(tags.length)], getRandomAnswerId()},
				new String[]{tags[comUtil.randomNumberUptoLimit(tags.length)], getRandomAnswerId()}
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] fcanswerAddTagToAnswerNegativeDP(ITestContext testContext)
	{
		String[] tags = {"Trendy","Ethnic","Winter","Summer","Monsoon","Wedding","Sports","Formal","Casual"};
		Object[][] dataSet = new Object[][] { new String[]{" ", getRandomAnswerId()},
				new String[]{tags[comUtil.randomNumberUptoLimit(tags.length)], " "},
				new String[]{"null", getRandomAnswerId()},
				new String[]{tags[comUtil.randomNumberUptoLimit(tags.length)], "null"},
				new String[]{"1=1' or '1=1", getRandomAnswerId()},
				new String[]{tags[comUtil.randomNumberUptoLimit(tags.length)], "1=1' or '1=1"},
				new String[]{"!@#$%^&*(*()", getRandomAnswerId()},
				new String[]{tags[comUtil.randomNumberUptoLimit(tags.length)], "ABCD0001"}
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 8, 8);
	}

	@DataProvider
	public Object[][] fcanswerAddLikeToAnswerPositiveDP(ITestContext testContext)
	{
		ids.add("H9483");
		addIdsAndEmailbyCallingCreateUser();
		Object[][] dataSet = new Object[][] { new String[]{getRandomAnswerId(), ids.get(comUtil.randomNumberUptoLimit(ids.size()))},
				new String[]{getRandomAnswerId(), ids.get(comUtil.randomNumberUptoLimit(ids.size()))}
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] fcanswerAddLikeToAnswerNegativeDP(ITestContext testContext)
	{
		ids.add("H9483");
		addIdsAndEmailbyCallingCreateUser();
		Object[][] dataSet = new Object[][] { new String[]{" ", ids.get(comUtil.randomNumberUptoLimit(ids.size()))},
				new String[]{getRandomAnswerId(), " "},
				new String[]{"null", ids.get(comUtil.randomNumberUptoLimit(ids.size()))},
				new String[]{getRandomAnswerId(), "null"},
				new String[]{"1=1' or '1=1", ids.get(comUtil.randomNumberUptoLimit(ids.size()))},
				new String[]{getRandomAnswerId(), "1=1' or '1=1"},
				new String[]{"ABCD0001", ids.get(comUtil.randomNumberUptoLimit(ids.size()))},
				new String[]{getRandomAnswerId(), "ABCD0001"}
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 8, 8);
	}

	@DataProvider
	public Object[][] fcanswerAddTagToAnswerV2PositiveDP(ITestContext testContext)
	{
		String[] tags = {"Trendy","Ethnic","Winter","Summer","Monsoon","Wedding","Sports","Formal","Casual"};
		Object[][] dataSet = new Object[][] { new String[]{tags[comUtil.randomNumberUptoLimit(tags.length)], getRandomAnswerId()},
				new String[]{tags[comUtil.randomNumberUptoLimit(tags.length)], getRandomAnswerId()}
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] fcanswerAddTagToAnswerV2NegativeDP(ITestContext testContext)
	{
		String[] tags = {"Trendy","Ethnic","Winter","Summer","Monsoon","Wedding","Sports","Formal","Casual"};
		Object[][] dataSet = new Object[][] { new String[]{" ", getRandomAnswerId()},
				new String[]{tags[comUtil.randomNumberUptoLimit(tags.length)], " "},
				new String[]{"null", getRandomAnswerId()},
				new String[]{tags[comUtil.randomNumberUptoLimit(tags.length)], "null"},
				new String[]{"1=1' or '1=1", getRandomAnswerId()},
				new String[]{tags[comUtil.randomNumberUptoLimit(tags.length)], "1=1' or '1=1"},
				new String[]{"!@#$%^&*(*()", getRandomAnswerId()},
				new String[]{tags[comUtil.randomNumberUptoLimit(tags.length)], "ABCD0001"}
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 8, 8);
	}

	@DataProvider
	public Object[][] fcanswerAddLikeToAnswerV2PositiveDP(ITestContext testContext)
	{
		ids.add("H9483");
		addIdsAndEmailbyCallingCreateUser();
		Object[][] dataSet = new Object[][] { new String[]{getRandomAnswerId(), ids.get(comUtil.randomNumberUptoLimit(ids.size()))},
				new String[]{getRandomAnswerId(), ids.get(comUtil.randomNumberUptoLimit(ids.size()))}
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] fcanswerAddLikeToAnswerV2NegativeDP(ITestContext testContext)
	{
		ids.add("H9483");
		addIdsAndEmailbyCallingCreateUser();
		Object[][] dataSet = new Object[][] { new String[]{" ", ids.get(comUtil.randomNumberUptoLimit(ids.size()))},
				new String[]{getRandomAnswerId(), " "},
				new String[]{"null", ids.get(comUtil.randomNumberUptoLimit(ids.size()))},
				new String[]{getRandomAnswerId(), "null"},
				new String[]{"1=1' or '1=1", ids.get(comUtil.randomNumberUptoLimit(ids.size()))},
				new String[]{getRandomAnswerId(), "1=1' or '1=1"}
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 6, 6);
	}

	@DataProvider
	public Object[][] fcanswerGetAnswerPositiveDP(ITestContext testContext)
	{
		Object[][] dataSet = new Object[][] { new String[]{getRandomAnswerId()},
				new String[]{getRandomAnswerId()}
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] fcanswerGetAnswerNegativeDP(ITestContext testContext)
	{
		Object[][] dataSet = new Object[][] { new String[]{" "},
				new String[]{"null"},
				new String[]{"ABCD001"},
				new String[]{"1=1' or '1=1"}
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 4, 4);
	}

	/*
	 * Author:Sabyasachi
	 * DataProvider name:fcCornerOutfitLikeUnlikeComment
	 */
	@DataProvider
	public Object[][] fcgiveComment_FirstTime(ITestContext testContext){
		String[] inputA = {"sabyasachi","test","237"};
		String[] inputB = {" ","test2","237"};
		String[] inputC = {"sabyasachi"," ","237"};
		String[] inputD = {" "," ","237"};
		String[] inputE = {"sabyasachi","etst","-237"};
		String[] inputF = {"sabyasachi","etst","@#$"};
		String[] inputG = {" "," "," "};		 
		Object[][] dataSet = new Object[][] {inputA,inputB,inputC,inputD,inputE,inputF,inputG};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 7, 7);
		}
		
	@DataProvider
	public Object[][] fcCornerOutfitLikeUnlikeComment(ITestContext testContext){
		Object[][] dataset=new Object[][]{new String[]{"237"},
				new String[]{"-237"},
				new String[]{"12344"},
				new String[]{"XYZ123"},
				new String[]{"@#$"}
				//new String[]{" "}
		};
		return Toolbox.returnReducedDataSet(dataset,testContext.getIncludedGroups(), 6, 6);
		}
		
	private String getRandomQuestionId(){
		return new Random().nextInt(getQuestionIds())+"";
	}

	private String getRandomAnswerId(){
		return new Random().nextInt(getAnswerIds())+"";
	}

	private String getOutfitData(String userId){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCOUTFITCREATEOUTFIT, init.Configurations, new String[]{userId});
		RequestGenerator req = new RequestGenerator(service);		
		String jsonResponse = req.respvalidate.returnresponseasstring();
		int maxLength = Integer.parseInt(req.respvalidate.GetNodeValue("data.id", jsonResponse));
		return new Random().nextInt(maxLength)+"";
	}

	private int getQuestionIds(){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCADDQUESTIONTOUSER, init.Configurations, new String[]{"Demo Titile", "Demo desc", "H9483", "true"});
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		int quesId = Integer.parseInt(req.respvalidate.GetNodeValue("data.id", jsonResponse));
		System.out.println(quesId);
		return quesId;
	}

	private int getAnswerIds(){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCADDANSWER, init.Configurations, new String[]{"Demo Titile", "Demo desc", "H9483", "true", getRandomQuestionId()});
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		int ansId = Integer.parseInt(req.respvalidate.GetNodeValue("data.id", jsonResponse));
		System.out.println(ansId);
		return ansId;
	}


	private String[] createNewOutfit(){
		//Create outfit
		String name=getName();
		String likeCount=getRandomLikeCount(100);
		String layout=getRandomLayoutCount(1);
		String UserId="manu.chadha@myntra.com";
		String occasions1=getOccassion();
		String occasions2=getOccassion();
		String gender=getGenderMaleOrFemale();
		String description=getBio(20);
		String stylesid1=String.valueOf(new Random().nextInt(1000000));
		String styleDescription=getBio(20);
		String stylePosition=getStylePositionCount(3);	
		String[] str=new String[2];
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCOUTFITCREATEOUTFIT, init.Configurations, new String[]{name,likeCount,layout,UserId,occasions1,occasions2,gender,description,stylesid1,styleDescription,stylePosition});
		System.out.println("URL : "+service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);		
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		str[0]=req.respvalidate.GetNodeValue("data.styles.OutfitId", jsonResponse).replaceAll("\"", "");
		str[1]=req.respvalidate.GetNodeValue("data.styles.id", jsonResponse).replaceAll("\"", "");
		return str;

	}

	private void addIdsAndEmailbyCallingCreateUser(){
		//ids.clear();
		emails.clear();
		String id1 = "A"+getRandomId();
		String id2 = "B"+getRandomId();
		String name1 = getName();
		String name2 = getName();
		String email1 = getEmail();
		String email2 = getEmail();
		String role1 = getRole();
		String role2 = getRole();
		String bio1 = getBio(50);
		String bio2 = getBio(100);
		ids.add(id1);
		ids.add(id2);
		emails.add(email1);
		emails.add(email2);
		System.out.println(id1+"   "+id2);
		//First
		MyntraService serviceA = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCCREATEUSER, init.Configurations, new String[]{id1, name1, email1, role1, name1, bio1});
		RequestGenerator reqA = new RequestGenerator(serviceA);
		//Second
		MyntraService serviceB = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCCREATEUSER, init.Configurations, new String[]{id2, name2, email2, role2, name2, bio2});
		RequestGenerator reqB = new RequestGenerator(serviceB);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private String getRandomId(){
		String[] all =  {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		String[] randArray = Commons.createArrayOfInts(0,10000);
		return all[comUtil.randomNumberUptoLimit(all.length)]+randArray[comUtil.randomNumberUptoLimit(randArray.length)];
	}

	private String getRandomId(int length){
		String[] all =  {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		String[] randArray = Commons.createArrayOfInts(0,length);
		return all[comUtil.randomNumberUptoLimit(all.length)]+randArray[comUtil.randomNumberUptoLimit(randArray.length)];
	}

	private String getName(){
		String[] all =  {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		String toReturn = null ;
		try {
			toReturn = comUtil.getCommaSeperatedValuesFromArray(all, 10, true);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toReturn.replaceAll(",", "").toLowerCase();
	}

	private String getEmail(){
		String[] emails = {"@gmail.com", "@myntra.com", "@rediffmail.com", "@times.com", "@xyz.com"};
		return getName()+emails[comUtil.randomNumberUptoLimit(emails.length)];
	}

	private String getRole(){
		String[] role = {"Customer", "Admin", "Administrator", "Read", "Write"};
		return role[comUtil.randomNumberUptoLimit(role.length)];
	}

	private String getOccassion(){
		String[] role = {"diwali", "holi", "wedding", "party", "eid","casual"};
		return role[comUtil.randomNumberUptoLimit(role.length)];
	}

	private String getBio(int length){
		String[] all =  {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"," "};
		return comUtil.getRandomValuesFromArrayTillLength(all, length).toLowerCase();
	}

	private String getRandomTrueOrFalse(){
		String[] all =  {"true", "false"};
		return all[new Random().nextInt(all.length)];
	}

	private String getGenderMaleOrFemale(){
		String[] all =  {"Male", "Female"};
		return all[new Random().nextInt(all.length)];
	}

	private String getRandomLikeCount(int length){
		Random ran=new Random();
		int i=ran.nextInt(length);
		return String.valueOf(i);
	}

	private String getRandomLayoutCount(int length){
		String[] all =  {"4","5","6"};
		return comUtil.getRandomValuesFromArrayTillLength(all, length).toLowerCase();
	}

	private String getStylePositionCount(int length){
		String[] all =  {"4","5","6","1","2"};
		return comUtil.getRandomValuesFromArrayTillLength(all, length).toLowerCase();
	}


	private String getRandomNo(int length){
		String[] all =  {"1","2","3","4","5","6","7","8","9","0"};
		return comUtil.getRandomValuesFromArrayTillLength(all, length).toLowerCase();
	}

	private String getRandomSpecialCharactor(int length){
		String[] all =  {"!","@","#","%","^","&","*"};
		return comUtil.getRandomValuesFromArrayTillLength(all, length).toLowerCase();
	}



	private String getValueOfOneFromAll(String all, String one, String firstDelimiter, String secDelimiter ){
		String[] afterFirestDelimit = all.split(firstDelimiter);
		String toReturn = "";
		for(String s : afterFirestDelimit){
			if(s.contains(one)){
				toReturn = s.split(secDelimiter)[1];
			}
		}
		return toReturn.trim();
	}


}
