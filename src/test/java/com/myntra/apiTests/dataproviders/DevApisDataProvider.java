package com.myntra.apiTests.dataproviders;

import java.util.List;
import java.util.Random;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

/**
 * @author shankara.c
 *
 */
public class DevApisDataProvider extends CommonUtils
{
	String Env;
	Configuration con = new Configuration("./Data/configuration");
	static DevApiServiceHelper devApiServiceHelper = new DevApiServiceHelper();

	public DevApisDataProvider() {
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}

	@DataProvider
	public Object[][] DevApisDataProvider_signIn_verifySuccessResponse(ITestContext testContext)
	{
		String[] login1 = { "Production", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login2 = { "Production", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com", "XH3b1SKWA0vK3MX" };
		
		String[] login3 = { "Fox7", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login4 = { "Fox7", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login5 = { "Fox7", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login6 = { "Fox7", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login7 = { "Fox7", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login8 = { "Fox7", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login9 = { "Fox7", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login10 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Case Sensitive UserId
		String[] login11 = { "Fox7", "signuP188_nx1gqlepuuajxaQ@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Leading Spaces UserId
		String[] login12 = { "Fox7", "signuP188_nx1gqlepuuajxaQ@myntra.com   ", "Nx1gqlEPuuAjXAq" };
		
		String[] login13 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login14 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login15 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login16 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login17 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login18 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login19 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login20 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Case Sensitive UserId
		String[] login21 = { "Functional", "signuP188_nx1gqlepuuajxaQ@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Leading Spaces UserId
		String[] login22 = { "Functional", "signuP188_nx1gqlepuuajxaQ@myntra.com   ", "Nx1gqlEPuuAjXAq" };
		
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14, login15,
				login16, login17, login18, login19, login20, login21, login22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_signIn_verifyMetaTagNodes(ITestContext testContext)
	{
		String[] login1 = { "Production", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login2 = { "Production", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com", "XH3b1SKWA0vK3MX" };
		
		String[] login3 = { "Fox7", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login4 = { "Fox7", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login5 = { "Fox7", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login6 = { "Fox7", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login7 = { "Fox7", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login8 = { "Fox7", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login9 = { "Fox7", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login10 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Case Sensitive UserId
		String[] login11 = { "Fox7", "signuP188_nx1gqlepuuajxaQ@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Leading Spaces UserId
		String[] login12 = { "Fox7", "signuP188_nx1gqlepuuajxaQ@myntra.com   ", "Nx1gqlEPuuAjXAq" };
		
		String[] login13 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login14 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login15 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login16 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login17 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login18 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login19 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login20 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Case Sensitive UserId
		String[] login21 = { "Functional", "signuP188_nx1gqlepuuajxaQ@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Leading Spaces UserId
		String[] login22 = { "Functional", "signuP188_nx1gqlepuuajxaQ@myntra.com   ", "Nx1gqlEPuuAjXAq" };
		
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14, login15,
				login16, login17, login18, login19, login20, login21, login22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_signIn_verifyNotificationTagNodes(ITestContext testContext)
	{
		String[] login1 = { "Production", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login2 = { "Production", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com", "XH3b1SKWA0vK3MX" };
		
		String[] login3 = { "Fox7", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login4 = { "Fox7", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login5 = { "Fox7", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login6 = { "Fox7", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login7 = { "Fox7", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login8 = { "Fox7", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login9 = { "Fox7", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login10 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Case Sensitive UserId
		String[] login11 = { "Fox7", "signuP188_nx1gqlepuuajxaQ@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Leading Spaces UserId
		String[] login12 = { "Fox7", "signuP188_nx1gqlepuuajxaQ@myntra.com   ", "Nx1gqlEPuuAjXAq" };
		
		String[] login13 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login14 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login15 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login16 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login17 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login18 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login19 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login20 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Case Sensitive UserId
		String[] login21 = { "Functional", "signuP188_nx1gqlepuuajxaQ@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Leading Spaces UserId
		String[] login22 = { "Functional", "signuP188_nx1gqlepuuajxaQ@myntra.com   ", "Nx1gqlEPuuAjXAq" };
		
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14, login15,
				login16, login17, login18, login19, login20, login21, login22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] DevApisDataProvider_signIn_verifyDataTagNodes(ITestContext testContext)
	{
		String[] login1 = { "Production", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login2 = { "Production", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com", "XH3b1SKWA0vK3MX" };
		
		String[] login3 = { "Fox7", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login4 = { "Fox7", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login5 = { "Fox7", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login6 = { "Fox7", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login7 = { "Fox7", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login8 = { "Fox7", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login9 = { "Fox7", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login10 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Case Sensitive UserId
		String[] login11 = { "Fox7", "signuP188_nx1gqlepuuajxaQ@myntra.com", "Nx1gqlEPuuAjXAq" };
//		// Leading Spaces UserId
//		String[] login12 = { "Fox7", "signuP188_nx1gqlepuuajxaQ@myntra.com   ", "Nx1gqlEPuuAjXAq" };
		String [] login12={"Fox7","manu.chadha@myntra.com","trinity"};
		
		String[] login13 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login14 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login15 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login16 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login17 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login18 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login19 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login20 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Case Sensitive UserId
		String[] login21 = { "Functional", "signuP188_nx1gqlepuuajxaQ@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Leading Spaces UserId
		String[] login22 = { "Functional", "signuP188_nx1gqlepuuajxaQ@myntra.com   ", "Nx1gqlEPuuAjXAq" };
		
		Object[][] dataSet = new Object[][] { login1, login2,login12, login13, login14, login15,
				login16, login17, login18, login19, login20, login21, login22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_signIn_verifyFailureResponse(ITestContext testContext) 
	{
		// Invalid UserId
		String[] login1 = { "Fox7", "InvalidEmailId@myntra.com", "W6Uk0CgEH9CHJX8", "401", "The username or password you entered is incorrect" };
		// Empty Password
		String[] login2 = { "Fox7", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "", "401", "The username or password you entered is incorrect" };
		// Empty User Name
		String[] login3 = { "Fox7", "", "KbiUnOVvb2CD9OR", "401", "The username or password you entered is incorrect" };
		// Sql Injections for UserID
		String[] login4 = { "Fox7", "1=1' or '1=1", "8bwlE2bET9RnXBj", "401", "The username or password you entered is incorrect" };
		// Invalid emaildId
		String[] login5 = { "Fox7", "2987129837498123%^#^%%(*^&^)", "`p15wY6totzOKZX3", "401", "The username or password you entered is incorrect" };
		// Sql Injections for Password
		String[] login8 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "1=1' or '1=1", "401", "The username or password you entered is incorrect" };
		// Case Sensitive Password
		String[] login9 = { "Fox7", "SignuP188_nx1gqlepuuajxaQ@myntra.com", "nx1gqlEPuuAjXAQ", "401", "The username or password you entered is incorrect" };
		// Trailing Spaces UserId
		String[] login11 = { "Fox7", "   signuP188_nx1gqlepuuajxaQ@myntra.com", "Nx1gqlEPuuAjXAq", "401", "The username or password you entered is incorrect" };
		// Special Characters UserId
		String[] login12 = { "Fox7", "SignUp100_#$%^W6Uk0CgEH9CHJX8@hotmail.com", "Nx1gqlEPuuAjXAq", "401", "The username or password you entered is incorrect" };
		
		// Invalid UserId
		String[] login13 = { "Functional", "InvalidEmailId@myntra.com", "W6Uk0CgEH9CHJX8", "401", "The username or password you entered is incorrect" };
		// Empty Password
		String[] login14 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "", "401", "The username or password you entered is incorrect" };
		// Empty User Name
		String[] login15 = { "Functional", "", "KbiUnOVvb2CD9OR", "401", "The username or password you entered is incorrect" };
		// Sql Injections for UserID
		String[] login16 = { "Functional", "1=1' or '1=1", "8bwlE2bET9RnXBj", "401", "The username or password you entered is incorrect" };
		// Invalid emaildId
		String[] login17 = { "Functional", "2987129837498123%^#^%%(*^&^)", "`p15wY6totzOKZX3", "401", "The username or password you entered is incorrect" };
		// Sql Injections for Password
		String[] login18 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "1=1' or '1=1", "401", "The username or password you entered is incorrect" };
		// Case Sensitive Password
		String[] login19 = { "Functional", "SignuP188_nx1gqlepuuajxaQ@myntra.com", "nx1gqlEPuuAjXAQ", "401", "The username or password you entered is incorrect" };
		// Trailing Spaces UserId
		String[] login20 = { "Functional", "   signuP188_nx1gqlepuuajxaQ@myntra.com", "Nx1gqlEPuuAjXAq", "401", "The username or password you entered is incorrect" };
		// Special Characters UserId
		String[] login21 = { "Functional", "SignUp100_#$%^W6Uk0CgEH9CHJX8@hotmail.com", "Nx1gqlEPuuAjXAq", "401", "The username or password you entered is incorrect" };
		
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login8, login9, login11, login12, login13, login14, login15, login16, login17,
				login18, login19, login20, login21};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] DevApisDataProvider_signUp_verifySuccessResponse(ITestContext testContext) 
	{
		String[] login1 = { "Fox7", getRandomEmailId(15), "" };
		String[] login2 = { "Fox7", getRandomEmailId(15), "" };
		String[] login3 = { "Fox7", getRandomEmailId(15), "" };
		String[] login4 = { "Fox7", getRandomEmailId(15), "" };
		String[] login5 = { "Fox7", getRandomEmailId(15), "" };
		String[] login6 = { "Production", getRandomEmailId(15), "" };
		String[] login7 = { "Production", getRandomEmailId(15), "" };
		// Leading, Trailing and middle Spaces Password
		String[] login8 = { "Fox7", getRandomEmailId(15), " Myntra 123 " };

		String[] login9 = { "Functional", getRandomEmailId(15), "" };
		String[] login10 = { "Functional", getRandomEmailId(15), "" };
		String[] login11 = { "Functional", getRandomEmailId(15), "" };
		String[] login12 = { "Functional", getRandomEmailId(15), "" };
		String[] login13 = { "Functional", getRandomEmailId(15), "" };
		String[] login14 = { "Functional", getRandomEmailId(15), " Myntra 123 " };
		
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_signUp_verifyMetaTagNodes(ITestContext testContext) 
	{
		String[] login1 = { "Fox7", getRandomEmailId(15), "" };
		String[] login2 = { "Fox7", getRandomEmailId(15), "" };
		String[] login3 = { "Fox7", getRandomEmailId(15), "" };
		String[] login4 = { "Fox7", getRandomEmailId(15), "" };
		String[] login5 = { "Fox7", getRandomEmailId(15), "" };
		String[] login6 = { "Production", getRandomEmailId(15), "" };
		String[] login7 = { "Production", getRandomEmailId(15), "" };
		// Leading, Trailing and middle Spaces Password
		String[] login8 = { "Fox7", getRandomEmailId(15), " Myntra 123 " };

		String[] login9 = { "Functional", getRandomEmailId(15), "" };
		String[] login10 = { "Functional", getRandomEmailId(15), "" };
		String[] login11 = { "Functional", getRandomEmailId(15), "" };
		String[] login12 = { "Functional", getRandomEmailId(15), "" };
		String[] login13 = { "Functional", getRandomEmailId(15), "" };
		String[] login14 = { "Functional", getRandomEmailId(15), " Myntra 123 " };
		
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] DevApisDataProvider_signUp_verifyNotificationTagNodes(ITestContext testContext) 
	{
		String[] login1 = { "Fox7", getRandomEmailId(15), "" };
		String[] login2 = { "Fox7", getRandomEmailId(15), "" };
		String[] login3 = { "Fox7", getRandomEmailId(15), "" };
		String[] login4 = { "Fox7", getRandomEmailId(15), "" };
		String[] login5 = { "Fox7", getRandomEmailId(15), "" };
		String[] login6 = { "Production", getRandomEmailId(15), "" };
		String[] login7 = { "Production", getRandomEmailId(15), "" };
		// Leading, Trailing and middle Spaces Password
		String[] login8 = { "Fox7", getRandomEmailId(15), " Myntra 123 " };

		String[] login9 = { "Functional", getRandomEmailId(15), "" };
		String[] login10 = { "Functional", getRandomEmailId(15), "" };
		String[] login11 = { "Functional", getRandomEmailId(15), "" };
		String[] login12 = { "Functional", getRandomEmailId(15), "" };
		String[] login13 = { "Functional", getRandomEmailId(15), "" };
		String[] login14 = { "Functional", getRandomEmailId(15), " Myntra 123 " };
		
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_signUp_verifyDataTagNodes(ITestContext testContext) 
	{
		String[] login1 = { "Fox7", getRandomEmailId(15), "123456" };
		String[] login2 = { "Fox7", getRandomEmailId(15), "123456" };
		String[] login3 = { "Fox7", getRandomEmailId(15), "123456" };
		String[] login4 = { "Fox7", getRandomEmailId(15), "123456" };
		String[] login5 = { "Fox7", getRandomEmailId(15), "123456" };
		String[] login6 = { "Production", getRandomEmailId(15), "123456" };
		String[] login7 = { "Production", getRandomEmailId(15), "123456" };
		// Leading, Trailing and middle Spaces Password
		String[] login8 = { "Fox7", getRandomEmailId(15), " Myntra 123 " };

		String[] login9 = { "Functional", getRandomEmailId(15), "123456" };
		String[] login10 = { "Functional", getRandomEmailId(15), "123456" };
		String[] login11 = { "Functional", getRandomEmailId(15), "123456" };
		String[] login12 = { "Functional", getRandomEmailId(15), "123456" };
		String[] login13 = { "Functional", getRandomEmailId(15), "123456" };
		String[] login14 = { "Functional", getRandomEmailId(15), " Myntra 123 " };
		
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 0, 0);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_signUp_verifyFailureResponse(ITestContext testContext) 
	{
		// Already registered user name
		String[] login1 = { "Fox7", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8", "405", "The username you entered already exists" };
		// Empty username and pass
		String[] login2 = { "Fox7", "", "", "400", "Invalid input" };
		// Empty user name
		String[] login3 = { "Fox7", "", "password", "400", "Invalid input" };
		// Empty password
		String[] login4 = { "Fox7", "username@myntra.com", "", "400", "Invalid input" };
		// invalid username
		String[] login5 = { "Fox7", "username@", "username", "400", "Invalid input" };
		// Invalid User Name
		String[] login6 = { "Fox7", "username@myntra", "username", "400", "Invalid input" };
		// Leading and Trailing Spaces UserId
		String[] login7 = { "Fox7", " SignUpNewUser100_W6Uk0CgEH9CHJX8123@hotmail.com ", "W6Uk0CgEH9CHJX8", "400", "Invalid input" };
		// Already registered userId with Case Sensitive
		String[] login8 = { "Fox7", "sIGNuP100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8", "405", "The username you entered already exists" };
		// Special characters UserId
		String[] login9 = { "Fox7", "$#%SignUpNewUser100_W6Uk0CgEH9CHJX8123@hotmail.com", "W6Uk0CgEH9CHJX8", "400", "Invalid input" };

		// Already registered user name
		String[] login10 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8", "405", "The username you entered already exists" };
		// Empty username and pass
		String[] login11 = { "Functional", "", "", "400", "Signup error occured" };
		// Empty user name
		String[] login12 = { "Functional", "", "password", "400", "Signup error occured" };
		// Empty password
		String[] login13 = { "Functional", "username@myntra.com", "", "400", "Signup error occured" };
		// invalid username
		String[] login14 = { "Functional", "username@", "username", "400", "Signup error occured" };
		// Invalid User Name
		String[] login15 = { "Functional", "username@myntra", "username", "400", "Signup error occured" };
		// Leading and Trailing Spaces UserId
		String[] login16 = { "Functional", " SignUpNewUser100_W6Uk0CgEH9CHJX8123@hotmail.com ", "W6Uk0CgEH9CHJX8", "500", "Signup error occured" };
		// Already registered userId with Case Sensitive
		String[] login17 = { "Functional", "sIGNuP100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8", "405", "The username you entered already exists" };
		// Special characters UserId
		String[] login18 = { "Functional", "$#%SignUpNewUser100_W6Uk0CgEH9CHJX8123@hotmail.com", "W6Uk0CgEH9CHJX8", "500", "Signup error occured" };
				
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14, 
				login15, login16, login17, login18 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] DevApisDataProvider_refresh_verifySuccessResponse(ITestContext testContext)
	{
				String[] login11 = { "Production", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login12 = { "Production", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		
		String[] login13 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login15 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login16 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login17 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login18 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login19 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login20 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login21 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login22 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		Object[][] dataSet = new Object[][] {login11, login12, login13, login15, 
				login16, login17, login18, login19, login20, login21, login22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_refresh_verifyMetaTagNodes(ITestContext testContext)
	{
		String[] login11 = { "Production", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login12 = { "Production", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		
		String[] login13 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login15 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login16 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login17 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login18 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login19 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login20 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login21 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login22 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		Object[][] dataSet = new Object[][] { login11, login12, login13, login15, 
				login16, login17, login18, login19, login20, login21, login22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_refresh_verifyNotificationTagNodes(ITestContext testContext)
	{
		String[] login11 = { "Production", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login12 = { "Production", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		
		String[] login13 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login15 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login16 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login17 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login18 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login19 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login20 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login21 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login22 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		Object[][] dataSet = new Object[][] {login11, login12, login13, login15, 
				login16, login17, login18, login19, login20, login21, login22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_refresh_verifyDataTagNodes(ITestContext testContext)
	{
		String[] login11 = { "Production", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login12 = { "Production", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		
		String[] login13 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login15 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login16 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login17 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login18 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login19 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login20 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login21 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login22 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		Object[][] dataSet = new Object[][] {login11, login12, login13, login15, 
				login16, login17, login18, login19, login20, login21, login22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_refresh_verifyFailureResponse(ITestContext testContext)
	{
		String[] login11 = { "Production", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login12 = { "Production", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		
		String[] login13 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login15 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login16 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login17 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login18 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login19 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login20 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login21 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login22 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		Object[][] dataSet = new Object[][] {login11, login12, login13, login15, 
				login16, login17, login18, login19, login20, login21, login22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] DevApisDataProvider_signOut_verifySuccessResponse(ITestContext testContext) 
	{
		String[] login1 = { "Fox7", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login2 = { "Fox7", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com", "XH3b1SKWA0vK3MX" };
		String[] login3 = { "Fox7", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login4 = { "Fox7", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login5 = { "Fox7", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login6 = { "Fox7", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login7 = { "Fox7", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login8 = { "Fox7", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login9 = { "Fox7", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login10 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		String[] login11 = { "Production", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login12 = { "Production", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		
		String[] login13 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login14 = { "Functional", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com", "XH3b1SKWA0vK3MX" };
		String[] login15 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login16 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login17 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login18 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login19 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login20 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login21 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login22 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14, login15,
				login16, login17, login18, login19, login20, login21, login22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_signOut_verifyMetaTagNodes(ITestContext testContext) 
	{
		String[] login1 = { "Fox7", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login2 = { "Fox7", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com", "XH3b1SKWA0vK3MX" };
		String[] login3 = { "Fox7", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login4 = { "Fox7", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login5 = { "Fox7", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login6 = { "Fox7", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login7 = { "Fox7", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login8 = { "Fox7", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login9 = { "Fox7", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login10 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		String[] login11 = { "Production", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login12 = { "Production", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		
		String[] login13 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login14 = { "Functional", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com", "XH3b1SKWA0vK3MX" };
		String[] login15 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login16 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login17 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login18 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login19 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login20 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login21 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login22 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14, login15,
				login16, login17, login18, login19, login20, login21, login22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_signOut_verifyNotificationTagNodes(ITestContext testContext) 
	{
		String[] login1 = { "Fox7", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login2 = { "Fox7", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com", "XH3b1SKWA0vK3MX" };
		String[] login3 = { "Fox7", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login4 = { "Fox7", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login5 = { "Fox7", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login6 = { "Fox7", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login7 = { "Fox7", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login8 = { "Fox7", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login9 = { "Fox7", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login10 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		String[] login11 = { "Production", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login12 = { "Production", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		
		String[] login13 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login14 = { "Functional", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com", "XH3b1SKWA0vK3MX" };
		String[] login15 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login16 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login17 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login18 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login19 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login20 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login21 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login22 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14, login15,
				login16, login17, login18, login19, login20, login21, login22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_signOut_verifyDataTagNodes(ITestContext testContext) 
	{
		String[] login1 = { "Fox7", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login2 = { "Fox7", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com", "XH3b1SKWA0vK3MX" };
		String[] login3 = { "Fox7", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login4 = { "Fox7", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login5 = { "Fox7", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login6 = { "Fox7", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login7 = { "Fox7", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login8 = { "Fox7", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login9 = { "Fox7", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login10 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		String[] login11 = { "Production", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login12 = { "Production", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		
		String[] login13 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login14 = { "Functional", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com", "XH3b1SKWA0vK3MX" };
		String[] login15 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login16 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login17 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login18 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login19 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login20 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login21 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login22 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14, login15,
				login16, login17, login18, login19, login20, login21, login22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_forgotPassword_verifySuccessResponse(ITestContext testContext)
	{
		String[] login1 = { "Fox7", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com" };
		String[] login2 = { "Fox7", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com" };
		String[] login3 = { "Fox7", "SignUp102_KbiUnOVvb2CD9OR@gmail.com" };
		String[] login4 = { "Fox7", "SignUp105_8bwlE2bET9RnXBj@yahoo.com" };
		String[] login5 = { "Fox7", "SignUp105_p15wY6totzOKZX3@gmail.com" };
		String[] login6 = { "Fox7", "SignUp107_9Yg72jSSkGN0bsf@myntra.com" };
		String[] login7 = { "Fox7", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com" };
		String[] login8 = { "Fox7", "SignUp133_8u28ANTiGTUEpId@hotmail.com" };
		String[] login9 = { "Fox7", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com" };
		String[] login10 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com" };
		// Case Sensitive UserId
		String[] login11 = { "Fox7", "sIGNuP188_Nx1gqlEPuuAjXAq@myntra.com" };
		// Leading Spaces UserId
		String[] login12 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com  " };
		
		String[] login13 = { "Production", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com" };
		String[] login14 = { "Production", "SignUp105_p15wY6totzOKZX3@gmail.com" };

		String[] login15 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com" };
		String[] login16 = { "Functional", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com" };
		String[] login17 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com" };
		String[] login18 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com" };
		String[] login19 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com" };
		String[] login20 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com" };
		String[] login21 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com" };
		String[] login22 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com" };
		String[] login23 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com" };
		String[] login24 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com" };
		// Case Sensitive UserId
		String[] login25 = { "Functional", "sIGNuP188_Nx1gqlEPuuAjXAq@myntra.com" };
		// Leading Spaces UserId
		String[] login26 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com  " };

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14, 
				login15, login16, login17, login18, login19, login20, login21, login22, login23, login24, login25, login26 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_forgotPassword_verifyMetaTagNodes(ITestContext testContext)
	{
		String[] login1 = { "Fox7", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com" };
		String[] login2 = { "Fox7", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com" };
		String[] login3 = { "Fox7", "SignUp102_KbiUnOVvb2CD9OR@gmail.com" };
		String[] login4 = { "Fox7", "SignUp105_8bwlE2bET9RnXBj@yahoo.com" };
		String[] login5 = { "Fox7", "SignUp105_p15wY6totzOKZX3@gmail.com" };
		String[] login6 = { "Fox7", "SignUp107_9Yg72jSSkGN0bsf@myntra.com" };
		String[] login7 = { "Fox7", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com" };
		String[] login8 = { "Fox7", "SignUp133_8u28ANTiGTUEpId@hotmail.com" };
		String[] login9 = { "Fox7", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com" };
		String[] login10 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com" };
		// Case Sensitive UserId
		String[] login11 = { "Fox7", "sIGNuP188_Nx1gqlEPuuAjXAq@myntra.com" };
		// Leading Spaces UserId
		String[] login12 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com  " };
		
		String[] login13 = { "Production", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com" };
		String[] login14 = { "Production", "SignUp105_p15wY6totzOKZX3@gmail.com" };

		String[] login15 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com" };
		String[] login16 = { "Functional", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com" };
		String[] login17 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com" };
		String[] login18 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com" };
		String[] login19 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com" };
		String[] login20 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com" };
		String[] login21 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com" };
		String[] login22 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com" };
		String[] login23 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com" };
		String[] login24 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com" };
		// Case Sensitive UserId
		String[] login25 = { "Functional", "sIGNuP188_Nx1gqlEPuuAjXAq@myntra.com" };
		// Leading Spaces UserId
		String[] login26 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com  " };

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14, 
				login15, login16, login17, login18, login19, login20, login21, login22, login23, login24, login25, login26 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_forgotPassword_verifyNotificationTagNodes(ITestContext testContext)
	{
		String[] login1 = { "Fox7", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com" };
		String[] login2 = { "Fox7", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com" };
		String[] login3 = { "Fox7", "SignUp102_KbiUnOVvb2CD9OR@gmail.com" };
		String[] login4 = { "Fox7", "SignUp105_8bwlE2bET9RnXBj@yahoo.com" };
		String[] login5 = { "Fox7", "SignUp105_p15wY6totzOKZX3@gmail.com" };
		String[] login6 = { "Fox7", "SignUp107_9Yg72jSSkGN0bsf@myntra.com" };
		String[] login7 = { "Fox7", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com" };
		String[] login8 = { "Fox7", "SignUp133_8u28ANTiGTUEpId@hotmail.com" };
		String[] login9 = { "Fox7", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com" };
		String[] login10 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com" };
		// Case Sensitive UserId
		String[] login11 = { "Fox7", "sIGNuP188_Nx1gqlEPuuAjXAq@myntra.com" };
		// Leading Spaces UserId
		String[] login12 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com  " };
		
		String[] login13 = { "Production", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com" };
		String[] login14 = { "Production", "SignUp105_p15wY6totzOKZX3@gmail.com" };

		String[] login15 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com" };
		String[] login16 = { "Functional", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com" };
		String[] login17 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com" };
		String[] login18 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com" };
		String[] login19 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com" };
		String[] login20 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com" };
		String[] login21 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com" };
		String[] login22 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com" };
		String[] login23 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com" };
		String[] login24 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com" };
		// Case Sensitive UserId
		String[] login25 = { "Functional", "sIGNuP188_Nx1gqlEPuuAjXAq@myntra.com" };
		// Leading Spaces UserId
		String[] login26 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com  " };

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14, 
				login15, login16, login17, login18, login19, login20, login21, login22, login23, login24, login25, login26 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] DevApisDataProvider_forgotPassword_verifyFailureResponse(ITestContext testContext)
	{
		// Trailing Spaces UserId
		String[] login1 = { "Fox7", "   SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "500", "The login supplied is not signed up." };
		// Special characters UserId
		String[] login2 = { "Fox7", "$#%SignUpNewUser100_W6Uk0CgEH9CHJX8123@hotmail.com", "500", "The login supplied is not signed up." };
		// invalid user name
		String[] login4 = { "Fox7", "username@", "500", "The login supplied is not signed up." };
		// Invalid User Name
		String[] login5 = { "Fox7", "username@myntra", "500", "The login supplied is not signed up." };
		// Sql Injections for UserID
		String[] login6 = { "Fox7", "1=1' or '1=1", "500", "The login supplied is not signed up." };

		// Trailing Spaces UserId
		String[] login7 = { "Functional", "   SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "401", "The login supplied is not signed up." };
		// Special characters UserId
		String[] login8 = { "Functional", "$#%SignUpNewUser100_W6Uk0CgEH9CHJX8123@hotmail.com", "401", "The login supplied is not signed up." };
		// invalid user name
		String[] login9 = { "Functional", "username@", "401", "The login supplied is not signed up." };
		// Invalid User Name
		String[] login10 = { "Functional", "username@myntra", "401", "The login supplied is not signed up." };
		// Sql Injections for UserID
		String[] login11 = { "Functional", "1=1' or '1=1", "401", "The login supplied is not signed up." };
		
		Object[][] dataSet = new Object[][] { login1, login2, login4, login5, login6, login7, login8, login9, login10, login11 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] DevApisDataProvider_style_verifySuccessResponse(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7", nikeStyleIds.get(1).toString() };
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = { "Production", nikeStyleIds.get(0).toString() };
		String[] styleid8 = { "Production", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional", adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_style_verifyMetaTagNodes(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7", nikeStyleIds.get(1).toString() };
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = { "Production", nikeStyleIds.get(0).toString() };
		String[] styleid8 = { "Production", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional", adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] DevApisDataProvider_style_verifyDataNodes(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7", nikeStyleIds.get(1).toString() };
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = { "Production", nikeStyleIds.get(0).toString() };
		String[] styleid8 = { "Production", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional", adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_style_verifyArticleAttributeDataNodes(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7", nikeStyleIds.get(1).toString() };
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = { "Production", nikeStyleIds.get(0).toString() };
		String[] styleid8 = { "Production", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional", adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_style_verifyStyleImagesDataNodes(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7", nikeStyleIds.get(1).toString() };
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = { "Production", nikeStyleIds.get(0).toString() };
		String[] styleid8 = { "Production", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional", adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_style_verifyMasterCatagoryDataNodes(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7", nikeStyleIds.get(1).toString() };
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = { "Production", nikeStyleIds.get(0).toString() };
		String[] styleid8 = { "Production", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional", adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_style_verifySubCatagoryDataNodes(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7", nikeStyleIds.get(1).toString() };
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = { "Production", nikeStyleIds.get(0).toString() };
		String[] styleid8 = { "Production", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional", adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_style_verifySubArticleDataNodes(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7", nikeStyleIds.get(1).toString() };
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = { "Production", nikeStyleIds.get(0).toString() };
		String[] styleid8 = { "Production", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional", adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_style_verifyProductDescriptorDataNodes(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7", nikeStyleIds.get(1).toString() };
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = { "Production", nikeStyleIds.get(0).toString() };
		String[] styleid8 = { "Production", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional", adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_style_verifyStyleOptionsDataNodes(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7", nikeStyleIds.get(1).toString() };
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = { "Production", nikeStyleIds.get(0).toString() };
		String[] styleid8 = { "Production", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional", adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_styleOffers_verifySuccessResponse(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7", nikeStyleIds.get(1).toString() };
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = { "Production", nikeStyleIds.get(0).toString() };
		String[] styleid8 = { "Production", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional", adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_styleOffers_verifyMetaTagNodes(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7", nikeStyleIds.get(1).toString() };
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = { "Production", nikeStyleIds.get(0).toString() };
		String[] styleid8 = { "Production", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional", adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_styleOffers_verifyNotificationTagNodes(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7", nikeStyleIds.get(1).toString() };
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = { "Production", nikeStyleIds.get(0).toString() };
		String[] styleid8 = { "Production", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional", adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_styleOffers_verifyDataTagNodes(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7", nikeStyleIds.get(1).toString() };
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = { "Production", nikeStyleIds.get(0).toString() };
		String[] styleid8 = { "Production", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional", adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_search_verifySuccessResponse(ITestContext testContext) 
	{
		String[] id1 = { "Fox7", "woodland" };
		String[] id2 = { "Fox7", "levis" };
		String[] id3 = { "Fox7", "lee%20cooper" };
		String[] id4 = { "Fox7", "adidas%20 shoes" };
		String[] id5 = { "Fox7", "wallets" };
		String[] id6 = { "Fox7", "sweatshirts" };
		String[] id7 = { "Fox7", "nike%20flip%20flops" };
		String[] id8 = { "Fox7", "mast%20&amp;%20harbour" };
		String[] id9 = { "Fox7", "men%20track%20pants" };
		String[] id10 = { "Fox7", "united%20colors%20of%20benetton%20men" };
		
		String[] id11 = { "Production", "wallets" };
		String[] id12 = { "Production", "adidas%20shoes" };

		String[] id13 = { "Functional", "woodland" };
		String[] id14 = { "Functional", "levis" };
		String[] id15 = { "Functional", "lee%20cooper" };
		String[] id16 = { "Functional", "adidas%20shoes" };
		String[] id17 = { "Functional", "wallets" };
		String[] id18 = { "Functional", "sweatshirts" };
		String[] id19 = { "Functional", "nike%20flip%20flops" };
		String[] id20 = { "Functional", "mast%20&amp;%20harbour" };
		String[] id21 = { "Functional", "men%20track%20pants" };
		String[] id22 = { "Functional", "united%20colors%20of%20benetton%20men" };
		
		Object[][] dataSet = new Object[][] { id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12, id13, id14, id15, id16, id17, id18, id19, id20, id21, id22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_search_verifyDataTagNodes(ITestContext testContext) 
	{
		String[] id1 = { "Fox7", "woodland" };
		String[] id2 = { "Fox7", "levis" };
		String[] id3 = { "Fox7", "lee%20cooper" };
		String[] id4 = { "Fox7", "adidas%20shoes" };
		String[] id5 = { "Fox7", "wallets" };
		String[] id6 = { "Fox7", "sweatshirts" };
		String[] id7 = { "Fox7", "nike%20flip%20flops" };
		String[] id8 = { "Fox7", "mast%20&amp;%20harbour" };
		String[] id9 = { "Fox7", "men%20track%20pants" };
		String[] id10 = { "Fox7", "united%20colors%20of%20benetton%20men" };
		
		String[] id11 = { "Production", "wallets" };
		String[] id12 = { "Production", "adidas%20shoes" };

		String[] id13 = { "Functional", "woodland" };
		String[] id14 = { "Functional", "levis" };
		String[] id15 = { "Functional", "lee%20cooper" };
		String[] id16 = { "Functional", "adidas%20shoes" };
		String[] id17 = { "Functional", "wallets" };
		String[] id18 = { "Functional", "sweatshirts" };
		String[] id19 = { "Functional", "nike%20flip%20flops" };
		String[] id20 = { "Functional", "mast%20&amp;%20harbour" };
		String[] id21 = { "Functional", "men%20track%20pants" };
		String[] id22 = { "Functional", "united%20colors%20of%20benetton%20men" };
		
		Object[][] dataSet = new Object[][] { id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12, id13, id14, id15, id16, id17, id18, id19, id20, id21, id22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] DevApisDataProvider_search_verifyFailureResponse(ITestContext testContext)
	{
		String[] id1 = { "Fox7", "%27", "200" };
		String[] id2 = { "Fox7", "%25", "500" };
		String[] id3 = { "Fox7", "~!@#$%^&*()", "404" };
		String[] id8 = { "Production", "%25", "500" };
		String[] id9 = { "Production", "~!@#$%^&*()", "404" };
		List<Integer> styleId = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		String[] styleid1 = { "Fox7", styleId.get(0).toString(), "200" };

		String[] id4 = { "Functional", "'", "200" };
		String[] id5 = { "Functional", "%", "400" };
		String[] id6 = { "Functional", "~!@#$%^&*()", "404" };
		
		Object[][] dataSet = new Object[][] { id1, id2, id3, styleid1, id4, id5, id6,id8,id9 };
		
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] DevApisDataProvider_searchWithfacets_verifySuccessResponse(ITestContext testContext) 
	{
		String[] id1 = { "Fox7", 
				"lucky-size-flash-sale-saree?f=gender:Men::categories:Free%2520Gifts::discount:40.0::size:35::discount:low::brand:Sia%2520Art%2520Jewellery::price:5027.0,7732.0&p=15" };
		String[] id2 = { "Fox7", "paris-calling-mens?f=discount:10.0::size:20::gender:Boys::price:6248.0,8724.0::categories:Brooch&p=17" };
		String[] id3 = { "Fox7", "duke-men-apparel-lounge?f=categories:Footwear::gender:Women" };
		String[] id4 = { "Fox7", "summer-day-men-1?f=price:3982.0,4410.0" };
		String[] id5 = { "Fox7", "sweaters?f=categories:Home::price:1944.0,2635.0::brand:Lomani&p=1" };
		String[] id6 = { "Fox7", "happy-hour-womens-day-beauty-product?f=size:32::gender:Women::categories:Gloves::discount:20.0::brand:Wildcraft" };
		String[] id7 = { "Fox7", "stylish-footwear-sale-flip?f=p=9" };
		String[] id8 = { "Fox7", "women-watches-2-pink-bling-fs-duffle?f=discount:low::price:3230.0,6966.0::size:44::discount:40.0&p=18" };
		String[] id9 = { "Fox7", "roadster-offer?f=categories:Stoles::price:1052.0,6461.0" };
		String[] id10 = { "Fox7", "reebok-sandal-sports?f=size:35::discount:high::gender:Women::discount:20.0::price:4335.0,6045.0&p=12" };
		
		String[] id11 = { "Production", "duke-men-apparel-lounge?f=categories:Footwear::gender:Women" };
		String[] id12 = { "Production", "summer-day-men-1?f=price:3982.0,4410.0" };

		String[] id13 = { "Functional",
				"lucky-size-flash-sale-saree?f=gender:Men::categories:Free%2520Gifts::discount:40.0::size:35::discount:low::brand:Sia%2520Art%2520Jewellery::price:5027.0,7732.0&p=15" };
		String[] id14 = { "Functional", "paris-calling-mens?f=discount:10.0::size:20::gender:Boys::price:6248.0,8724.0::categories:Brooch&p=17" };
		String[] id15 = { "Functional", "duke-men-apparel-lounge?f=categories:Footwear::gender:Women" };
		String[] id16 = { "Functional",
				"summer-day-men-1?f=price:3982.0,4410.0" };
		String[] id17 = { "Functional",
				"sweaters?f=categories:Home::price:1944.0,2635.0::brand:Lomani&p=1" };
		String[] id18 = {
				"Functional",
				"happy-hour-womens-day-beauty-product?f=size:32::gender:Women::categories:Gloves::discount:20.0::brand:Wildcraft" };
		String[] id19 = { "Functional", "stylish-footwear-sale-flip?f=p=9" };
		String[] id20 = {
				"Functional",
				"women-watches-2-pink-bling-fs-duffle?f=discount:low::price:3230.0,6966.0::size:44::discount:40.0&p=18" };
		String[] id21 = { "Functional",
				"roadster-offer?f=categories:Stoles::price:1052.0,6461.0" };
		String[] id22 = {
				"Functional",
				"reebok-sandal-sports?f=size:35::discount:high::gender:Women::discount:20.0::price:4335.0,6045.0&p=12" };
		
		Object[][] dataSet = new Object[][] { id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12, id13, id14, id15, id16, id17, id18, id19, id20, id21, id22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_searchWithfacets_verifyDataNodes(ITestContext testContext) 
	{
		String[] id1 = { "Fox7", 
				"lucky-size-flash-sale-saree?f=gender:Men::categories:Free%2520Gifts::discount:40.0::size:35::discount:low::brand:Sia%2520Art%2520Jewellery::price:5027.0,7732.0&p=15" };
		String[] id2 = { "Fox7", "paris-calling-mens?f=discount:10.0::size:20::gender:Boys::price:6248.0,8724.0::categories:Brooch&p=17" };
		String[] id3 = { "Fox7", "duke-men-apparel-lounge?f=categories:Footwear::gender:Women" };
		String[] id4 = { "Fox7", "summer-day-men-1?f=price:3982.0,4410.0" };
		String[] id5 = { "Fox7", "sweaters?f=categories:Home::price:1944.0,2635.0::brand:Lomani&p=1" };
		String[] id6 = { "Fox7", "happy-hour-womens-day-beauty-product?f=size:32::gender:Women::categories:Gloves::discount:20.0::brand:Wildcraft" };
		String[] id7 = { "Fox7", "stylish-footwear-sale-flip?f=p=9" };
		String[] id8 = { "Fox7", "women-watches-2-pink-bling-fs-duffle?f=discount:low::price:3230.0,6966.0::size:44::discount:40.0&p=18" };
		String[] id9 = { "Fox7", "roadster-offer?f=categories:Stoles::price:1052.0,6461.0" };
		String[] id10 = { "Fox7", "reebok-sandal-sports?f=size:35::discount:high::gender:Women::discount:20.0::price:4335.0,6045.0&p=12" };
		
		String[] id11 = { "Production", "duke-men-apparel-lounge?f=categories:Footwear::gender:Women" };
		String[] id12 = { "Production", "summer-day-men-1?f=price:3982.0,4410.0" };

		String[] id13 = { "Functional",
				"lucky-size-flash-sale-saree?f=gender:Men::categories:Free%2520Gifts::discount:40.0::size:35::discount:low::brand:Sia%2520Art%2520Jewellery::price:5027.0,7732.0&p=15" };
		String[] id14 = { "Functional", "paris-calling-mens?f=discount:10.0::size:20::gender:Boys::price:6248.0,8724.0::categories:Brooch&p=17" };
		String[] id15 = { "Functional", "duke-men-apparel-lounge?f=categories:Footwear::gender:Women" };
		String[] id16 = { "Functional",
				"summer-day-men-1?f=price:3982.0,4410.0" };
		String[] id17 = { "Functional",
				"sweaters?f=categories:Home::price:1944.0,2635.0::brand:Lomani&p=1" };
		String[] id18 = {
				"Functional",
				"happy-hour-womens-day-beauty-product?f=size:32::gender:Women::categories:Gloves::discount:20.0::brand:Wildcraft" };
		String[] id19 = { "Functional", "stylish-footwear-sale-flip?f=p=9" };
		String[] id20 = {
				"Functional",
				"women-watches-2-pink-bling-fs-duffle?f=discount:low::price:3230.0,6966.0::size:44::discount:40.0&p=18" };
		String[] id21 = { "Functional",
				"roadster-offer?f=categories:Stoles::price:1052.0,6461.0" };
		String[] id22 = {
				"Functional",
				"reebok-sandal-sports?f=size:35::discount:high::gender:Women::discount:20.0::price:4335.0,6045.0&p=12" };
		
		Object[][] dataSet = new Object[][] { id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12, id13, id14, id15, id16, id17, id18, id19, id20, id21, id22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] DevApisDataProvider_styleWithServicability_verifySuccessResponse(ITestContext testContext) 
	{
		String[] id1 = { "Fox7", "pincode=560037&styleid=57161&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id2 = { "Fox7", "pincode=560098&styleid=61547&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id3 = { "Fox7", "pincode=560068&styleid=60665&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id4 = { "Fox7", "pincode=560036&styleid=50610&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id5 = { "Fox7", "pincode=560040&styleid=106954&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id6 = { "Fox7", "pincode=560050&styleid=18995&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id7 = { "Fox7", "pincode=560001&styleid=4327&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id8 = { "Fox7", "pincode=560089&styleid=44890&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id9 = { "Fox7", "pincode=560019&styleid=53583&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id10 = { "Fox7", "pincode=560060&styleid=61431&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		
		String[] id11 = { "Production", "pincode=560037&styleid=57161&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id12 = { "Production", "pincode=560098&styleid=61547&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		
		String[] id13 = { "Functional", "pincode=711312&styleid=57161&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id14 = { "Functional", "pincode=784112&styleid=61547&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id15 = { "Functional", "pincode=691601&styleid=60665&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id16 = { "Functional", "pincode=388140&styleid=50610&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id17 = { "Functional", "pincode=341510&styleid=106954&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id18 = { "Functional", "pincode=796290&styleid=18995&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id19 = { "Functional", "pincode=533003&styleid=4327&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id20 = { "Functional", "pincode=444606&styleid=44890&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id21 = { "Functional", "pincode=788168&styleid=53583&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id22 = { "Functional", "pincode=768227&styleid=61431&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		
		Object[][] dataSet = new Object[][] { id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12, id13, id14, id15, id16, id17, id18, id19, id20, id21, id22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_styleWithServicability_verifyMetaTagNodes(ITestContext testContext) 
	{
		String[] id1 = { "Fox7", "pincode=560037&styleid=57161&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id2 = { "Fox7", "pincode=560098&styleid=61547&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id3 = { "Fox7", "pincode=560068&styleid=60665&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id4 = { "Fox7", "pincode=560036&styleid=50610&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id5 = { "Fox7", "pincode=560040&styleid=106954&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id6 = { "Fox7", "pincode=560050&styleid=18995&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id7 = { "Fox7", "pincode=560001&styleid=4327&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id8 = { "Fox7", "pincode=560089&styleid=44890&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id9 = { "Fox7", "pincode=560019&styleid=53583&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id10 = { "Fox7", "pincode=560060&styleid=61431&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		
		String[] id11 = { "Production", "pincode=560037&styleid=57161&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id12 = { "Production", "pincode=560068&styleid=61547&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };

		String[] id13 = { "Functional", "pincode=711312&styleid=57161&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id14 = { "Functional", "pincode=784112&styleid=61547&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id15 = { "Functional", "pincode=691601&styleid=60665&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id16 = { "Functional", "pincode=388140&styleid=50610&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id17 = { "Functional", "pincode=341510&styleid=106954&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id18 = { "Functional", "pincode=796290&styleid=18995&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id19 = { "Functional", "pincode=533003&styleid=4327&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id20 = { "Functional", "pincode=444606&styleid=44890&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id21 = { "Functional", "pincode=788168&styleid=53583&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id22 = { "Functional", "pincode=768227&styleid=61431&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		
		Object[][] dataSet = new Object[][] { id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12, id13, id14, id15, id16, id17, id18, id19, id20, id21, id22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_styleWithServicability_verifyNotificationTagNodes(ITestContext testContext) 
	{
		String[] id1 = { "Fox7", "pincode=560037&styleid=57161&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id2 = { "Fox7", "pincode=560098&styleid=61547&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id3 = { "Fox7", "pincode=560068&styleid=60665&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id4 = { "Fox7", "pincode=560036&styleid=50610&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id5 = { "Fox7", "pincode=560040&styleid=106954&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id6 = { "Fox7", "pincode=560050&styleid=18995&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id7 = { "Fox7", "pincode=560001&styleid=4327&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id8 = { "Fox7", "pincode=560089&styleid=44890&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id9 = { "Fox7", "pincode=560019&styleid=53583&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id10 = { "Fox7", "pincode=560060&styleid=61431&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		
		String[] id11 = { "Production", "pincode=560037&styleid=57161&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id12 = { "Production", "pincode=560098&styleid=61547&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };

		String[] id13 = { "Functional", "pincode=711312&styleid=57161&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id14 = { "Functional", "pincode=784112&styleid=61547&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id15 = { "Functional", "pincode=691601&styleid=60665&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id16 = { "Functional", "pincode=388140&styleid=50610&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id17 = { "Functional", "pincode=341510&styleid=106954&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id18 = { "Functional", "pincode=796290&styleid=18995&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id19 = { "Functional", "pincode=533003&styleid=4327&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id20 = { "Functional", "pincode=444606&styleid=44890&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id21 = { "Functional", "pincode=788168&styleid=53583&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id22 = { "Functional", "pincode=768227&styleid=61431&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		
		Object[][] dataSet = new Object[][] { id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12, id13, id14, id15, id16, id17, id18, id19, id20, id21, id22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_styleWithServicability_verifyDataTagNodes(ITestContext testContext) 
	{
		String[] id1 = { "Fox7", "pincode=560037&styleid=57161&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id2 = { "Fox7", "pincode=560098&styleid=61547&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id3 = { "Fox7", "pincode=560068&styleid=60665&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id4 = { "Fox7", "pincode=560036&styleid=50610&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id5 = { "Fox7", "pincode=560040&styleid=106954&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id6 = { "Fox7", "pincode=560050&styleid=18995&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id7 = { "Fox7", "pincode=560001&styleid=4327&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id8 = { "Fox7", "pincode=560089&styleid=44890&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id9 = { "Fox7", "pincode=560019&styleid=53583&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id10 = { "Fox7", "pincode=560060&styleid=61431&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
				
		String[] id11 = { "Production", "pincode=560037&styleid=57161&availableinwarehouses=28&leadtime=0&supplytype=ON_HAND" };
		String[] id12 = { "Production", "pincode=560098&styleid=61547&availableinwarehouses=28&leadtime=0&supplytype=ON_HAND" };

		String[] id13 = { "Functional", "pincode=711312&styleid=57161&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id14 = { "Functional", "pincode=784112&styleid=61547&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id15 = { "Functional", "pincode=691601&styleid=60665&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id16 = { "Functional", "pincode=388140&styleid=50610&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id17 = { "Functional", "pincode=341510&styleid=106954&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id18 = { "Functional", "pincode=796290&styleid=18995&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id19 = { "Functional", "pincode=533003&styleid=4327&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id20 = { "Functional", "pincode=444606&styleid=44890&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id21 = { "Functional", "pincode=788168&styleid=53583&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id22 = { "Functional", "pincode=768227&styleid=61431&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		
		Object[][] dataSet = new Object[][] { id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12, id13, id14, id15, id16, id17, id18, id19, id20, id21, id22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] DevApisDataProvider_styleWithServicability_verifyFailureResponse(ITestContext testContext) 
	{
		String[] id1 = { "Fox7", "pincode=null&styleid=57161" };
		String[] id2 = { "Fox7", "pincode=56006A&styleid=266314&availableinwarehouses=1%2C19&leadtime=0&supplytype=ON_HAND" };
		
		String[] id3 = { "Functional", "pincode=null&styleid=57161" };
		String[] id4 = { "Functional", "pincode=560098&styleid=57161&availableinwarehouses=null" };
		
		Object[][] dataSet = new Object[][] { id1, id2,id3,id4 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
    public Object[][] DevApisDataProvider_addToCart_verifySuccessResponse(ITestContext testContext)
	{
		String[] brands = { "Shirts", "Pants", "Shoes" };
		
		List<Integer>  styleIds1 = devApiServiceHelper.performSeachServiceToGetStyleIds(brands[0], "100", "true", "true");
		List<Integer>  styleIds2 = devApiServiceHelper.performSeachServiceToGetStyleIds(brands[1], "100", "true", "true");
		List<Integer>  styleIds3 = devApiServiceHelper.performSeachServiceToGetStyleIds(brands[2], "100", "true", "true");
		
		List<Integer>  skuIds1 = devApiServiceHelper.getSkuIds(styleIds1);
		List<Integer>  skuIds2 = devApiServiceHelper.getSkuIds(styleIds2);
		List<Integer>  skuIds3 = devApiServiceHelper.getSkuIds(styleIds3);
		//clear cart
	    devApiServiceHelper.userClearCart("devapitesting100@myntra.com", "123456");
		
		String[] arr1 = new String[]{ "Production", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds1.get(randomNumberUptoLimit(skuIds1.size()))) };
		String[] arr2 = new String[]{ "Production", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds2.get(randomNumberUptoLimit(skuIds2.size()))) };
		String[] arr3 = new String[]{ "Production", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds3.get(randomNumberUptoLimit(skuIds3.size()))) };
		
		String[] arr4 = new String[]{ "Fox7", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds1.get(randomNumberUptoLimit(skuIds1.size()))) };
		String[] arr5 = new String[]{ "Fox7", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds2.get(randomNumberUptoLimit(skuIds2.size()))) };
		String[] arr6 = new String[]{ "Fox7", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds3.get(randomNumberUptoLimit(skuIds3.size()))) };
		
		String[] arr7 = new String[]{ "Functional", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds1.get(randomNumberUptoLimit(skuIds1.size()))) };
		String[] arr8 = new String[]{ "Functional", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds2.get(randomNumberUptoLimit(skuIds2.size()))) };
		String[] arr9 = new String[]{ "Functional", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds3.get(randomNumberUptoLimit(skuIds3.size()))) };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9 };
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] DevApisDataProvider_getCart_verifySuccessResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ "Production", "devapitesting100@myntra.com", "123456" };
		String[] arr2 = new String[]{ "Fox7", "devapitesting100@myntra.com", "123456" };
		String[] arr3 = new String[]{ "Functional", "devapitesting100@myntra.com", "123456" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	
	@DataProvider
    public Object[][] DevApisDataProvider_addToWishlist_verifySuccessResponse(ITestContext testContext)
	{
		String[] brands = { "Shirts", "Pants", "Shoes" };
		
		List<Integer>  styleIds1 = devApiServiceHelper.performSeachServiceToGetStyleIds(brands[0], "49", "true", "true");
		List<Integer>  styleIds2 = devApiServiceHelper.performSeachServiceToGetStyleIds(brands[1], "49", "true", "true");
		List<Integer>  styleIds3 = devApiServiceHelper.performSeachServiceToGetStyleIds(brands[2], "49", "true", "true");
		
		List<Integer>  skuIds1 = devApiServiceHelper.getSkuIds(String.valueOf(styleIds1.get(randomNumberUptoLimit(styleIds1.size()))));
		List<Integer>  skuIds2 = devApiServiceHelper.getSkuIds(String.valueOf(styleIds2.get(randomNumberUptoLimit(styleIds2.size()))));
		List<Integer>  skuIds3 = devApiServiceHelper.getSkuIds(String.valueOf(styleIds3.get(randomNumberUptoLimit(styleIds3.size()))));
		
		String[] arr1 = new String[]{ "Production", "devapitesting100@myntra.com", "123456", String.valueOf(styleIds1.get(randomNumberUptoLimit(styleIds1.size()))) };
		String[] arr2 = new String[]{ "Production", "devapitesting100@myntra.com", "123456", String.valueOf(styleIds2.get(randomNumberUptoLimit(styleIds2.size()))) };
		String[] arr3 = new String[]{ "Production", "devapitesting100@myntra.com", "123456", String.valueOf(styleIds3.get(randomNumberUptoLimit(styleIds3.size()))) };
		
		String[] arr4 = new String[]{ "Fox7", "devapitesting100@myntra.com", "123456", String.valueOf(styleIds1.get(randomNumberUptoLimit(styleIds1.size()))) };
		String[] arr5 = new String[]{ "Fox7", "devapitesting100@myntra.com", "123456", String.valueOf(styleIds2.get(randomNumberUptoLimit(styleIds2.size()))) };
		String[] arr6 = new String[]{ "Fox7", "devapitesting100@myntra.com", "123456", String.valueOf(styleIds3.get(randomNumberUptoLimit(styleIds3.size()))) };
		
		String[] arr7 = new String[]{ "Functional", "devapitesting100@myntra.com", "123456", String.valueOf(styleIds1.get(randomNumberUptoLimit(styleIds1.size()))) };
		String[] arr8 = new String[]{ "Functional", "devapitesting100@myntra.com", "123456", String.valueOf(styleIds2.get(randomNumberUptoLimit(styleIds2.size()))) };
		String[] arr9 = new String[]{ "Functional", "devapitesting100@myntra.com", "123456", String.valueOf(styleIds3.get(randomNumberUptoLimit(styleIds3.size()))) };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9 };
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] DevApisDataProvider_getWishlist_verifySuccessResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ "Production", "devapitesting100@myntra.com", "123456" };
		String[] arr2 = new String[]{ "Fox7", "devapitesting100@myntra.com", "123456" };
		String[] arr3 = new String[]{ "Functional", "devapitesting100@myntra.com", "123456" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }

	public String getRandomEmailId(int length) {
		String prefix = "DevApiSignup_";
		String randomString = "abcdefghijlkmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		char[] c = randomString.toCharArray();
		String toReturn = "";
		for (int i = 0; i < length; i++) {
			toReturn += c[randomNumberUptoLimit(c.length)];
		}
		return prefix + toReturn + getEmaildSuffix();

	}

	private int randomNumberUptoLimit(int limit) {
		Random random = new Random();
		int index = random.nextInt(limit);
		return index;
	}

	private String getEmaildSuffix() {
		String[] suffix = { "@gmail.com", "@rediffmail.com", "@myntra.com",
				"@yahoo.com", "@hotmail.com" };
		return suffix[randomNumberUptoLimit(suffix.length)];
	}

	@DataProvider
	public Object[][] DevApisDataProvider_signIn_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] login1 = { "Production", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login2 = { "Production", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com", "XH3b1SKWA0vK3MX" };
		
		String[] login3 = { "Fox7", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login4 = { "Fox7", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login5 = { "Fox7", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login6 = { "Fox7", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login7 = { "Fox7", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login8 = { "Fox7", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login9 = { "Fox7", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login10 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Case Sensitive UserId
		String[] login11 = { "Fox7", "signuP188_nx1gqlepuuajxaQ@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Leading Spaces UserId
		String[] login12 = { "Fox7", "signuP188_nx1gqlepuuajxaQ@myntra.com   ", "Nx1gqlEPuuAjXAq" };
		
		String[] login13 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login14 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login15 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login16 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login17 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login18 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login19 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login20 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Case Sensitive UserId
		String[] login21 = { "Functional", "signuP188_nx1gqlepuuajxaQ@myntra.com", "Nx1gqlEPuuAjXAq" };
		// Leading Spaces UserId
		String[] login22 = { "Functional", "signuP188_nx1gqlepuuajxaQ@myntra.com   ", "Nx1gqlEPuuAjXAq" };
		
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14, login15,
				login16, login17, login18, login19, login20, login21, login22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_signUp_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext) 
	{
		String[] login1 = { "Fox7", getRandomEmailId(15), "" };
		String[] login2 = { "Fox7", getRandomEmailId(15), "" };
		String[] login3 = { "Fox7", getRandomEmailId(15), "" };
		String[] login4 = { "Fox7", getRandomEmailId(15), "" };
		String[] login5 = { "Fox7", getRandomEmailId(15), "" };
		String[] login6 = { "Production", getRandomEmailId(15), "" };
		String[] login7 = { "Production", getRandomEmailId(15), "" };
		// Leading, Trailing and middle Spaces Password
		String[] login8 = { "Fox7", getRandomEmailId(15), " Myntra 123 " };

		String[] login9 = { "Functional", getRandomEmailId(15), "" };
		String[] login10 = { "Functional", getRandomEmailId(15), "" };
		String[] login11 = { "Functional", getRandomEmailId(15), "" };
		String[] login12 = { "Functional", getRandomEmailId(15), "" };
		String[] login13 = { "Functional", getRandomEmailId(15), "" };
		String[] login14 = { "Functional", getRandomEmailId(15), " Myntra 123 " };
		
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_refresh_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] login1 = { "", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login2 = { "", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com", "XH3b1SKWA0vK3MX" };
		String[] login3 = { "", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login4 = { "", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login5 = { "", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login6 = { "", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login7 = { "", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login8 = { "", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login9 = { "", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login10 = { "", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		String[] login11 = { "Production", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login12 = { "Production", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		
		String[] login13 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login15 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login16 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login17 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login18 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login19 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login20 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login21 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login22 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login15, 
				login16, login17, login18, login19, login20, login21, login22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_signOut_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext) 
	{
		String[] login1 = { "Fox7", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login2 = { "Fox7", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com", "XH3b1SKWA0vK3MX" };
		String[] login3 = { "Fox7", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login4 = { "Fox7", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login5 = { "Fox7", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login6 = { "Fox7", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login7 = { "Fox7", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login8 = { "Fox7", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login9 = { "Fox7", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login10 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		String[] login11 = { "Production", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login12 = { "Production", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		
		String[] login13 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com", "W6Uk0CgEH9CHJX8" };
		String[] login14 = { "Functional", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com", "XH3b1SKWA0vK3MX" };
		String[] login15 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com", "KbiUnOVvb2CD9OR" };
		String[] login16 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com", "8bwlE2bET9RnXBj" };
		String[] login17 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com", "p15wY6totzOKZX3" };
		String[] login18 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com", "9Yg72jSSkGN0bsf" };
		String[] login19 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com", "JK7i9DA02ajW1cD" };
		String[] login20 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com", "8u28ANTiGTUEpId" };
		String[] login21 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com", "IJ5n7U6TE6fi00z" };
		String[] login22 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com", "Nx1gqlEPuuAjXAq" };
		
		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14, login15,
				login16, login17, login18, login19, login20, login21, login22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_forgotPassword_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] login1 = { "Fox7", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com" };
		String[] login2 = { "Fox7", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com" };
		String[] login3 = { "Fox7", "SignUp102_KbiUnOVvb2CD9OR@gmail.com" };
		String[] login4 = { "Fox7", "SignUp105_8bwlE2bET9RnXBj@yahoo.com" };
		String[] login5 = { "Fox7", "SignUp105_p15wY6totzOKZX3@gmail.com" };
		String[] login6 = { "Fox7", "SignUp107_9Yg72jSSkGN0bsf@myntra.com" };
		String[] login7 = { "Fox7", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com" };
		String[] login8 = { "Fox7", "SignUp133_8u28ANTiGTUEpId@hotmail.com" };
		String[] login9 = { "Fox7", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com" };
		String[] login10 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com" };
		// Case Sensitive UserId
		String[] login11 = { "Fox7", "sIGNuP188_Nx1gqlEPuuAjXAq@myntra.com" };
		// Leading Spaces UserId
		String[] login12 = { "Fox7", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com  " };
		
		String[] login13 = { "Production", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com" };
		String[] login14 = { "Production", "SignUp105_p15wY6totzOKZX3@gmail.com" };

		String[] login15 = { "Functional", "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com" };
		String[] login16 = { "Functional", "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com" };
		String[] login17 = { "Functional", "SignUp102_KbiUnOVvb2CD9OR@gmail.com" };
		String[] login18 = { "Functional", "SignUp105_8bwlE2bET9RnXBj@yahoo.com" };
		String[] login19 = { "Functional", "SignUp105_p15wY6totzOKZX3@gmail.com" };
		String[] login20 = { "Functional", "SignUp107_9Yg72jSSkGN0bsf@myntra.com" };
		String[] login21 = { "Functional", "SignUp123_JK7i9DA02ajW1cD@rediffmail.com" };
		String[] login22 = { "Functional", "SignUp133_8u28ANTiGTUEpId@hotmail.com" };
		String[] login23 = { "Functional", "SignUp157_IJ5n7U6TE6fi00z@hotmail.com" };
		String[] login24 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com" };
		// Case Sensitive UserId
		String[] login25 = { "Functional", "sIGNuP188_Nx1gqlEPuuAjXAq@myntra.com" };
		// Leading Spaces UserId
		String[] login26 = { "Functional", "SignUp188_Nx1gqlEPuuAjXAq@myntra.com  " };

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4, login5, login6, login7, login8, login9, login10, login11, login12, login13, login14, 
				login15, login16, login17, login18, login19, login20, login21, login22, login23, login24, login25, login26 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_style_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7", nikeStyleIds.get(1).toString() };
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = { "Production", nikeStyleIds.get(0).toString() };
		String[] styleid8 = { "Production", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional", adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_styleOffers_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7", nikeStyleIds.get(1).toString() };
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = { "Production", nikeStyleIds.get(0).toString() };
		String[] styleid8 = { "Production", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional", adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_search_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext) 
	{
		String[] id1 = { "Fox7", "woodland" };
		String[] id2 = { "Fox7", "levis" };
		String[] id3 = { "Fox7", "lee%20cooper" };
		String[] id4 = { "Fox7", "adidas%20shoes" };
		String[] id5 = { "Fox7", "wallets" };
		String[] id6 = { "Fox7", "sweatshirts" };
		String[] id7 = { "Fox7", "nike%20flip%20flops" };
		String[] id8 = { "Fox7", "mast%20&amp;%20harbour" };
		String[] id9 = { "Fox7", "men%20track%20pants" };
		String[] id10 = { "Fox7", "united%20colors%20of%20benetton%20men" };
		
		String[] id11 = { "Production", "wallets" };
		String[] id12 = { "Production", "adidas%20shoes" };

		String[] id13 = { "Functional", "woodland" };
		String[] id14 = { "Functional", "levis" };
		String[] id15 = { "Functional", "lee%20cooper" };
		String[] id16 = { "Functional", "adidas%20shoes" };
		String[] id17 = { "Functional", "wallets" };
		String[] id18 = { "Functional", "sweatshirts" };
		String[] id19 = { "Functional", "nike%20flip%20flops" };
		String[] id20 = { "Functional", "mast%20&amp;%20harbour" };
		String[] id21 = { "Functional", "men%20track%20pants" };
		String[] id22 = { "Functional", "united%20colors%20of%20benetton%20men" };
		
		Object[][] dataSet = new Object[][] { id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12, id13, id14, id15, id16, id17, id18, id19, id20, id21, id22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 3, 2);
	}

	@DataProvider
	public Object[][] DevApisDataProvider_searchWithfacets_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext) 
	{
		String[] id1 = { "Fox7", 
				"lucky-size-flash-sale-saree?f=gender:Men::categories:Free%2520Gifts::discount:40.0::size:35::discount:low::brand:Sia%2520Art%2520Jewellery::price:5027.0,7732.0&p=15" };
		String[] id2 = { "Fox7", "paris-calling-mens?f=discount:10.0::size:20::gender:Boys::price:6248.0,8724.0::categories:Brooch&p=17" };
		String[] id3 = { "Fox7", "duke-men-apparel-lounge?f=categories:Footwear::gender:Women" };
		String[] id4 = { "Fox7", "summer-day-men-1?f=price:3982.0,4410.0" };
		String[] id5 = { "Fox7", "sweaters?f=categories:Home::price:1944.0,2635.0::brand:Lomani&p=1" };
		String[] id6 = { "Fox7", "happy-hour-womens-day-beauty-product?f=size:32::gender:Women::categories:Gloves::discount:20.0::brand:Wildcraft" };
		String[] id7 = { "Fox7", "stylish-footwear-sale-flip?f=p=9" };
		String[] id8 = { "Fox7", "women-watches-2-pink-bling-fs-duffle?f=discount:low::price:3230.0,6966.0::size:44::discount:40.0&p=18" };
		String[] id9 = { "Fox7", "roadster-offer?f=categories:Stoles::price:1052.0,6461.0" };
		String[] id10 = { "Fox7", "reebok-sandal-sports?f=size:35::discount:high::gender:Women::discount:20.0::price:4335.0,6045.0&p=12" };
		
		String[] id11 = { "Production", "duke-men-apparel-lounge?f=categories:Footwear::gender:Women" };
		String[] id12 = { "Production", "summer-day-men-1?f=price:3982.0,4410.0" };

		String[] id13 = { "Functional",
				"lucky-size-flash-sale-saree?f=gender:Men::categories:Free%2520Gifts::discount:40.0::size:35::discount:low::brand:Sia%2520Art%2520Jewellery::price:5027.0,7732.0&p=15" };
		String[] id14 = { "Functional", "paris-calling-mens?f=discount:10.0::size:20::gender:Boys::price:6248.0,8724.0::categories:Brooch&p=17" };
		String[] id15 = { "Functional", "duke-men-apparel-lounge?f=categories:Footwear::gender:Women" };
		String[] id16 = { "Functional",
				"summer-day-men-1?f=price:3982.0,4410.0" };
		String[] id17 = { "Functional",
				"sweaters?f=categories:Home::price:1944.0,2635.0::brand:Lomani&p=1" };
		String[] id18 = {
				"Functional",
				"happy-hour-womens-day-beauty-product?f=size:32::gender:Women::categories:Gloves::discount:20.0::brand:Wildcraft" };
		String[] id19 = { "Functional", "stylish-footwear-sale-flip?f=p=9" };
		String[] id20 = {
				"Functional",
				"women-watches-2-pink-bling-fs-duffle?f=discount:low::price:3230.0,6966.0::size:44::discount:40.0&p=18" };
		String[] id21 = { "Functional",
				"roadster-offer?f=categories:Stoles::price:1052.0,6461.0" };
		String[] id22 = {
				"Functional",
				"reebok-sandal-sports?f=size:35::discount:high::gender:Women::discount:20.0::price:4335.0,6045.0&p=12" };
		
		Object[][] dataSet = new Object[][] { id1, id2, id3, id4, id5, id6, id8, id9, id10, id11, id12, id13, id14, id15, id16, id17, id18, id19, id20, id21, id22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 4, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_styleWithServicability_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext) 
	{
		String[] id1 = { "Fox7", "pincode=560037&styleid=57161&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id2 = { "Fox7", "pincode=560098&styleid=61547&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id3 = { "Fox7", "pincode=560068&styleid=60665&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id4 = { "Fox7", "pincode=400003&styleid=50610&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id5 = { "Fox7", "pincode=400004&styleid=106954&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id6 = { "Fox7", "pincode=400005&styleid=18995&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id7 = { "Fox7", "pincode=110005&styleid=4327&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id8 = { "Fox7", "pincode=110006&styleid=44890&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id9 = { "Fox7", "pincode=110021&styleid=53583&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id10 = { "Fox7", "pincode=110022&styleid=61431&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		
		String[] id11 = { "Production", "pincode=110021&styleid=57161&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id12 = { "Production", "pincode=110022&styleid=61547&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };

		String[] id13 = { "Functional", "pincode=711312&styleid=57161&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id14 = { "Functional", "pincode=784112&styleid=61547&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id15 = { "Functional", "pincode=691601&styleid=60665&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id16 = { "Functional", "pincode=388140&styleid=50610&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id17 = { "Functional", "pincode=341510&styleid=106954&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id18 = { "Functional", "pincode=796290&styleid=18995&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id19 = { "Functional", "pincode=533003&styleid=4327&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id20 = { "Functional", "pincode=444606&styleid=44890&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id21 = { "Functional", "pincode=788168&styleid=53583&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		String[] id22 = { "Functional", "pincode=768227&styleid=61431&availableinwarehouses=1,19&leadtime=0&supplytype=ON_HAND" };
		
		Object[][] dataSet = new Object[][] { id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12, id13, id14, id15, id16, id17, id18, id19, id20, id21, id22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
    public Object[][] DevApisDataProvider_addToCart_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		devApiServiceHelper.userClearCart("devapitesting100@myntra.com", "123456");
		String[] brands = { "Shirts", "Pants", "Shoes" };
		
		List<Integer>  styleIds1 = devApiServiceHelper.performSeachServiceToGetStyleIds(brands[0], "100", "true", "true");
		List<Integer>  styleIds2 = devApiServiceHelper.performSeachServiceToGetStyleIds(brands[1], "100", "true", "true");
		List<Integer>  styleIds3 = devApiServiceHelper.performSeachServiceToGetStyleIds(brands[2], "100", "true", "true");
		
		List<Integer>  skuIds1 = devApiServiceHelper.getSkuIds(styleIds1);
		List<Integer>  skuIds2 = devApiServiceHelper.getSkuIds(styleIds2);
		List<Integer>  skuIds3 = devApiServiceHelper.getSkuIds(styleIds3);
			
		String[] arr1 = new String[]{ "Production", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds1.get(randomNumberUptoLimit(skuIds1.size()))) };
		String[] arr2 = new String[]{ "Production", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds2.get(randomNumberUptoLimit(skuIds2.size()))) };
		String[] arr3 = new String[]{ "Production", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds3.get(randomNumberUptoLimit(skuIds3.size()))) };
		
		String[] arr4 = new String[]{ "Fox7", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds1.get(randomNumberUptoLimit(skuIds1.size()))) };
		String[] arr5 = new String[]{ "Fox7", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds2.get(randomNumberUptoLimit(skuIds2.size()))) };
		String[] arr6 = new String[]{ "Fox7", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds3.get(randomNumberUptoLimit(skuIds3.size()))) };
		
		String[] arr7 = new String[]{ "Functional", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds1.get(randomNumberUptoLimit(skuIds1.size()))) };
		String[] arr8 = new String[]{ "Functional", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds2.get(randomNumberUptoLimit(skuIds2.size()))) };
		String[] arr9 = new String[]{ "Functional", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds3.get(randomNumberUptoLimit(skuIds3.size()))) };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9 };
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] DevApisDataProvider_getCart_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ "Production", "devapitesting100@myntra.com", "123456" };
		String[] arr2 = new String[]{ "Fox7", "devapitesting100@myntra.com", "123456" };
		String[] arr3 = new String[]{ "Functional", "devapitesting100@myntra.com", "123456" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] DevApisDataProvider_addToWishlist_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		devApiServiceHelper.removeItemsFromWishList("devapitesting100@myntra.com", "123456");
		String[] brands = { "Shirts", "Pants", "Shoes" };
		
		List<Integer>  styleIds1 = devApiServiceHelper.performSeachServiceToGetStyleIds(brands[0], "100", "true", "true");
		List<Integer>  styleIds2 = devApiServiceHelper.performSeachServiceToGetStyleIds(brands[1], "100", "true", "true");
		List<Integer>  styleIds3 = devApiServiceHelper.performSeachServiceToGetStyleIds(brands[2], "100", "true", "true");
		
		List<Integer>  skuIds1 = devApiServiceHelper.getSkuIds(styleIds1);
		List<Integer>  skuIds2 = devApiServiceHelper.getSkuIds(styleIds2);
		List<Integer>  skuIds3 = devApiServiceHelper.getSkuIds(styleIds3);
		
		
		String[] arr1 = new String[]{ "Production", "devapitesting100@myntra.com", "123456", String.valueOf(styleIds1.get(randomNumberUptoLimit(skuIds1.size()))) };
		String[] arr2 = new String[]{ "Production", "devapitesting100@myntra.com", "123456", String.valueOf(styleIds2.get(randomNumberUptoLimit(skuIds2.size()))) };
		String[] arr3 = new String[]{ "Production", "devapitesting100@myntra.com", "123456", String.valueOf(styleIds3.get(randomNumberUptoLimit(skuIds3.size()))) };
		
		String[] arr4 = new String[]{ "Fox7", "devapitesting100@myntra.com", "123456", String.valueOf(styleIds1.get(randomNumberUptoLimit(skuIds1.size()))) };
		String[] arr5 = new String[]{ "Fox7", "devapitesting100@myntra.com", "123456", String.valueOf(styleIds2.get(randomNumberUptoLimit(skuIds2.size()))) };
		String[] arr6 = new String[]{ "Fox7", "devapitesting100@myntra.com", "123456", String.valueOf(styleIds3.get(randomNumberUptoLimit(skuIds3.size()))) };
		
		String[] arr7 = new String[]{ "Functional", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds1.get(randomNumberUptoLimit(skuIds1.size()))) };
		String[] arr8 = new String[]{ "Functional", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds2.get(randomNumberUptoLimit(skuIds2.size()))) };
		String[] arr9 = new String[]{ "Functional", "devapitesting100@myntra.com", "123456", String.valueOf(skuIds3.get(randomNumberUptoLimit(skuIds3.size()))) };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9 };
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] DevApisDataProvider_getWishlist_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ "Production", "devapitesting100@myntra.com", "123456" };
		String[] arr2 = new String[]{ "Fox7", "devapitesting100@myntra.com", "123456" };
		String[] arr3 = new String[]{ "Functional", "devapitesting100@myntra.com", "123456" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] DevApisNotificationNext(ITestContext testContext)
	{
		
		String[] arr1 = new String[]{"Production", "aravind.raj@myntra.com","welcome","2"};
		String[] arr3 = new String[]{"Functional", "manu.chadha@myntra.com","welcome","2"};
        Object[][] dataSet = new Object[][]{ arr1,arr3 };
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] DevApisGetNotificationWithBatch(ITestContext testContext)
	{
		String[] arr1 = new String[]{"Functional","manu.chadha@myntra.com","welcome","2"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome","10"};
        Object[][] dataSet = new Object[][]{ arr1 ,arr3};
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getExcludedGroups(), 3, 2);
    }
	
	@DataProvider
    public Object[][] DevApisGetNotificationCount(ITestContext testContext)
	{
		String[] arr2 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Functional","manu.chadha@myntra.com","welcome"};
        Object[][] dataSet = new Object[][]{ arr2,arr3};
        return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	
	@DataProvider
    public Object[][] DevApisGetAllNotification(ITestContext testContext)
	{
		
		String[] arr2 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Functional","aravind.raj@myntra.com","welcome"};
        Object[][] dataSet = new Object[][]{ arr2,arr3};
       
		return Toolbox.returnReducedDataSet(Env,dataSet,testContext.getIncludedGroups(),1,2);
    }
	
	@DataProvider
    public Object[][] DevApisNotificationMarkAsRead(ITestContext testContext)
	{ 
		//devApiServiceHelper.invokeCreateNotificationsForUser("manu1234.chadha@myntra.com");
		
		
		String[] arr2 = new String[]{"Functional","aravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","manu.chadha@myntra.com","welcome"};
        Object[][] dataSet = new Object[][]{ arr2,arr3};
        return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
    }
	@DataProvider
    public Object[][] DevApisGetProfileInfo(ITestContext testContext)
	{
		String[] arr1 = new String[]{"Production","manu.chadha@myntra.com","welcome"};
		String[] arr2 = new String[]{"Fox7","myntraidea@myntra.com","myntraidea@myntra.com"};
		String[] arr3 = new String[]{"Functional","manu.chadha@myntra.com","welcome"};
        Object[][] dataSet = new Object[][]{ arr1 ,arr2,arr3};
        
         return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] DevApisGetLikeSummery(ITestContext testContext)
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		String[] arr1 = new String[]{"Production","manu.chadha@myntra.com","welcome",nikeStyleIds.get(0).toString()};
		String[] arr2 = new String[]{"Fox7","myntraidea@myntra.com","myntraidea@myntra.com",nikeStyleIds.get(1).toString()};
		String[] arr3 = new String[]{"Functional","manu.chadha@myntra.com","welcome",nikeStyleIds.get(2).toString()};
        Object[][] dataSet = new Object[][]{ arr1 ,arr2,arr3};
        
         return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] DevApisGetCityList(ITestContext testContext)
	{
		String[] arr1 = new String[]{"Fox7","manu.chadha@myntra.com","trinity","Bangalore"};
		String[] arr2=new String[]{"Production","aravind.raj@myntra.com","welcome","Bangalore"};
		String[] arr3=new String[]{"Function","manu.chadha@myntra.com","welcome","Delhi"};	
        Object[][] dataSet = new Object[][]{ arr1 ,arr2,arr3};
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] DevApisGetCityListWithSchemaValidation(ITestContext testContext)
	{
		String[] arr1 = new String[]{"Production","manu.chadha@myntra.com","welcome","Bangalore"};
		String[] arr2 = new String[]{"Fox7","myntraidea@myntra.com","myntraidea@myntra.com","Bangalore"};
		String[] arr3 = new String[]{"Functional","manu.chadha@myntra.com","welcome","Bangalore"};
        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
        
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] DevApisGetCityListNegative(ITestContext testContext)
	{
		String[] arr1 = new String[]{"Production","manu.chadha@myntra.com","welcome","abc"};
		String[] arr2=new String[]{"Production","manu.chadha@myntra.com","welcome","!@#"};
		String[] arr3=new String[]{"Production","manu.chadha@myntra.com","welcome","123"};
		String[] arr4=new String[]{"Production","manu.chadha@myntra.com","welcome","!@12AS"};
		
		
		String[] arr5 = new String[]{"Fox7","myntraidea@myntra.com","myntraidea@myntra.com","abc"};
		String[] arr6=new String[]{"Fox7","myntraidea@myntra.com","myntraidea@myntra.com","!@#"};
		String[] arr7=new String[]{"Fox7","myntraidea@myntra.com","myntraidea@myntra.com","123"};
		String[] arr8=new String[]{"Fox7","myntraidea@myntra.com","myntraidea@myntra.com","!@12#AS"};
		
		String[] arr9 = new String[]{"Functional","manu.chadha@myntra.com","welcome","abc"};
		String[] arr10=new String[]{"Functional","manu.chadha@myntra.com","welcome","!@#"};
		String[] arr11=new String[]{"Functional","manu.chadha@myntra.com","welcome","123"};
		String[] arr12=new String[]{"Functional","manu.chadha@myntra.com","welcome","123"};
		
        Object[][] dataSet = new Object[][]{ arr1 ,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12};
        
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] DevApisGetLikeList(ITestContext testContext)
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		String[] arr1 = new String[]{"Fox7","myntraidea@myntra.com","myntraidea@myntra.com","1","0",nikeStyleIds.get(0).toString()};
		String[] arr2 = new String[]{"Functional","manu.chadha@myntra.com","welcome","1","0",nikeStyleIds.get(1).toString()};
		String[] arr3 = new String[]{"Production","manu.chadha@myntra.com","welcome","1","0",nikeStyleIds.get(2).toString()};
        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
        
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	@DataProvider
    public Object[][] DevApisGetLikeListWithSchemaValidation(ITestContext testContext)
	{
		String[] arr1 = new String[]{"myntraidea@myntra.com","myntraidea@myntra.com","1","0"};
        Object[][] dataSet = new Object[][]{ arr1};
        return dataSet;
    }
	@DataProvider
    public Object[][] DevApisGetLikeListWithDataTagValidation(ITestContext testContext)
	{
		String[] arr1 = new String[]{"manu.chadha@myntra.com","welcome","1","1"};
        Object[][] dataSet = new Object[][]{ arr1};
        return dataSet;
    }
	
	@DataProvider
    public Object[][] DevApisGetCityListWithSchemavalidation(ITestContext testContext)
	{
		String[] arr1 = new String[]{"Fox7","manu.chadha@myntra.com","welcome","Bangalore"};
		String[] arr2 = new String[]{"Functional","myntraidea@myntra.com","myntraidea@myntra.com","Bangalore"};
		String[] arr3 = new String[]{"Production","myntraidea@myntra.com","myntraidea@myntra.com","Bangalore"};
        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
        return Toolbox.returnReducedDataSet(Env, dataSet,testContext.getIncludedGroups(), 1,2);
    }
	
	@DataProvider
	public Object[][] DevApisDataProvider_LikeProducts(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7","myntraidea@myntra.com","myntraidea@myntra.com", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7","myntraidea@myntra.com", "myntraidea@myntra.com",nikeStyleIds.get(1).toString() };
		String[] styleid3 = {"Fox7","myntraidea@myntra.com","myntraidea@myntra.com",  nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7","myntraidea@myntra.com","myntraidea@myntra.com", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7","myntraidea@myntra.com","myntraidea@myntra.com", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", "myntraidea@myntra.com","myntraidea@myntra.com",adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = {"Production", "manu.chadha@myntra.com","welcome", nikeStyleIds.get(0).toString() };
		String[] styleid8 = {"Production", "manu.chadha@myntra.com","welcome", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", "manu.chadha@myntra.com","welcome",nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", "manu.chadha@myntra.com","welcome",nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional","manu.chadha@myntra.com","welcome", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional","manu.chadha@myntra.com","welcome", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional","manu.chadha@myntra.com","welcome" ,adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional","manu.chadha@myntra.com","welcome", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", "manu.chadha@myntra.com","welcome",adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_LikesCount(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7","manu.chadha@myntra.com","trinity", nikeStyleIds.get(0).toString() };
		String[] styleid2 = { "Fox7","manu.chadha@myntra.com", "trinity",nikeStyleIds.get(1).toString() };
		String[] styleid3 = {"Fox7","manu.chadha@myntra.com","trinity",  nikeStyleIds.get(2).toString() };
		String[] styleid4 = { "Fox7","manu.chadha@myntra.com","trinity", adidasStyleIds.get(0).toString() };
		String[] styleid5 = { "Fox7","manu.chadha@myntra.com","trinity", adidasStyleIds.get(1).toString() };
		String[] styleid6 = { "Fox7", "manu.chadha@myntra.com","trinity",adidasStyleIds.get(2).toString() };
		
		String[] styleid7 = {"Production", "manu.chadha@myntra.com","welcome", nikeStyleIds.get(0).toString() };
		String[] styleid8 = {"Production", "manu.chadha@myntra.com","welcome", nikeStyleIds.get(1).toString() };
		String[] styleid9 = { "Production", "manu.chadha@myntra.com","welcome",nikeStyleIds.get(2).toString() };

		String[] styleid10 = { "Functional", "jeevan.kk@myntra.com","myntra",nikeStyleIds.get(0).toString() };
		String[] styleid11 = { "Functional","jeevan.kk@myntra.com","myntra", nikeStyleIds.get(1).toString() };
		String[] styleid12 = { "Functional","jeevan.kk@myntra.com","myntra", nikeStyleIds.get(2).toString() };
		String[] styleid13 = { "Functional","jeevan.kk@myntra.com","myntra" ,adidasStyleIds.get(0).toString() };
		String[] styleid14 = { "Functional","jeevan.kk@myntra.com","myntra", adidasStyleIds.get(1).toString() };
		String[] styleid15 = { "Functional", "jeevan.kk@myntra.com","myntra",adidasStyleIds.get(2).toString() };
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	

	@DataProvider
	public Object[][] DevApisDataProvider_LikesCountInvalidStyleId(ITestContext testContext) 
	{
		String[] styleId1={"Fox7","jeevan.kk@myntra.com","myntra","!@#","400"};
		String[] styleId2={"Fox7","jeevan.kk@myntra.com","myntra","123ASD","400"};
		String[] styleId3={"Fox7","jeevan.kk@myntra.com","myntra","123!@#$","400"};
		
		String[] styleId4={"Production","jeevan.kk@myntra.com","myntra","!@#","400"};
		String[] styleId5={"Production","jeevan.kk@myntra.com","myntra","123ASD","400"};
		String[] styleId6={"Production","jeevan.kk@myntra.com","myntra","123!@#$","400"};
		
		String[] styleId7={"Functional","jeevan.kk@myntra.com","myntra","!@#","400"};
		String[] styleId8={"Functional","jeevan.kk@myntra.com","myntra","123ASD","400"};
		String[] styleId9={"Functional","jeevan.kk@myntra.com","myntra","123!@#$","400"};
		
		Object[][] dataSet = new Object[][] { styleId1,styleId2,styleId3,styleId4,styleId5,styleId6,styleId7,styleId8,styleId9 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 3, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_SearchAndGetStyleId(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString(),"480" };
		String[] styleid2 = { "Fox7",nikeStyleIds.get(1).toString() ,"720"};
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString(),"1080" };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString(),"480" };
		String[] styleid5 = { "Fox7",adidasStyleIds.get(1).toString(),"720" };
		String[] styleid6 = { "Fox7",adidasStyleIds.get(2).toString() ,"1080"};
		String[] styleid16 = { "Fox7",adidasStyleIds.get(2).toString() ,"180"};
		String[] styleid17 = { "Fox7",adidasStyleIds.get(2).toString() ,"48"};
		String[] styleid18 = { "Fox7",adidasStyleIds.get(2).toString() ,"360"};
		String[] styleid19 = { "Fox7",adidasStyleIds.get(2).toString() ,"150"};
		
		String[] styleid7 = {"Production", nikeStyleIds.get(0).toString(),"480" };
		String[] styleid8 = {"Production", nikeStyleIds.get(1).toString(),"720" };
		String[] styleid9 = {"Production", nikeStyleIds.get(2).toString() ,"1080"};

		String[] styleid10 = { "Functional",nikeStyleIds.get(0).toString(),"480" };
		String[] styleid11 = { "Functional",nikeStyleIds.get(1).toString() ,"720"};
		String[] styleid12 = { "Functional",nikeStyleIds.get(2).toString() ,"1080"};
		String[] styleid13 = { "Functional",adidasStyleIds.get(0).toString() ,"480"};
		String[] styleid14 = { "Functional",adidasStyleIds.get(1).toString() ,"720"};
		String[] styleid15 = { "Functional",adidasStyleIds.get(2).toString() ,"1080"};
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 ,styleid16,styleid17,styleid18,styleid19};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
		
	@DataProvider
	public Object[][] DevApisDataProvider_SearchAndGetStyleIdWithHeight(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString(),"480" ,"100"};
		String[] styleid2 = { "Fox7",nikeStyleIds.get(1).toString() ,"720","1000"};
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString(),"1080","500" };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString(),"480","2000" };
		String[] styleid5 = { "Fox7",adidasStyleIds.get(1).toString(),"720","420" };
		String[] styleid6 = { "Fox7",adidasStyleIds.get(2).toString() ,"1080","1200"};
		
		String[] styleid7 = {"Production", nikeStyleIds.get(0).toString(),"480" ,"100"};
		String[] styleid8 = {"Production", nikeStyleIds.get(1).toString(),"720" ,"1000"};
		String[] styleid9 = {"Production", nikeStyleIds.get(2).toString() ,"1080","2000"};

		String[] styleid10 = { "Functional",nikeStyleIds.get(0).toString(),"480","100" };
		String[] styleid11 = { "Functional",nikeStyleIds.get(1).toString() ,"720","340"};
		String[] styleid12 = { "Functional",nikeStyleIds.get(2).toString() ,"1080","520"};
		String[] styleid13 = { "Functional",adidasStyleIds.get(0).toString() ,"480","320"};
		String[] styleid14 = { "Functional",adidasStyleIds.get(1).toString() ,"720","500"};
		String[] styleid15 = { "Functional",adidasStyleIds.get(2).toString() ,"1080","3000"};
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_SearchAndGetStyleIdWithQuality(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString(),"480" ,"FULL"};
		String[] styleid2 = { "Fox7",nikeStyleIds.get(1).toString() ,"720","HIGH"};
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString(),"1080","Q90" };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString(),"480","MID" };
		String[] styleid5 = { "Fox7",adidasStyleIds.get(1).toString(),"720","LOW" };
		
		String[] styleid6 = { "Production",adidasStyleIds.get(2).toString() ,"1080","FULL"};
		String[] styleid7 = {"Production", nikeStyleIds.get(0).toString(),"480" ,"HIGH"};
		String[] styleid8 = {"Production", nikeStyleIds.get(1).toString(),"720" ,"Q90"};
		String[] styleid9 = {"Production", nikeStyleIds.get(2).toString() ,"1080","MID"};
		String[] styleid10 = { "Production",nikeStyleIds.get(0).toString(),"480","LOW" };
		
		String[] styleid11 = { "Functional",nikeStyleIds.get(1).toString() ,"720","FULL"};
		String[] styleid12 = { "Functional",nikeStyleIds.get(2).toString() ,"1080","HIGH"};
		String[] styleid13 = { "Functional",adidasStyleIds.get(0).toString() ,"480","Q90"};
		String[] styleid14 = { "Functional",adidasStyleIds.get(1).toString() ,"720","MID"};
		String[] styleid15 = { "Functional",adidasStyleIds.get(2).toString() ,"1080","LOW"};
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_SearchAndGetStyleIdWithQualityFull(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString(),"480" ,"FULL"};
		String[] styleid2 = { "Fox7",nikeStyleIds.get(1).toString() ,"720","FULL"};
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString(),"1080","FULL" };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString(),"480","FULL" };
		String[] styleid5 = { "Fox7",adidasStyleIds.get(1).toString(),"720","FULL" };
		
		String[] styleid6 = { "Production",adidasStyleIds.get(2).toString() ,"1080","FULL"};
		String[] styleid7 = {"Production", nikeStyleIds.get(0).toString(),"480" ,"FULL"};
		String[] styleid8 = {"Production", nikeStyleIds.get(1).toString(),"720" ,"FULL"};
		String[] styleid9 = {"Production", nikeStyleIds.get(2).toString() ,"1080","FULL"};
		String[] styleid10 = { "Production",nikeStyleIds.get(0).toString(),"480","FULL" };
		
		String[] styleid11 = { "Functional",nikeStyleIds.get(1).toString() ,"720","FULL"};
		String[] styleid12 = { "Functional",nikeStyleIds.get(2).toString() ,"1080","FULL"};
		String[] styleid13 = { "Functional",adidasStyleIds.get(0).toString() ,"480","FULL"};
		String[] styleid14 = { "Functional",adidasStyleIds.get(1).toString() ,"720","FULL"};
		String[] styleid15 = { "Functional",adidasStyleIds.get(2).toString() ,"1080","FULL"};
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_SearchAndGetStyleIdWithQualityHigh(ITestContext testContext) 
	{
		List<Integer> nikeStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("nike", "3", "true", "true");
		List<Integer> adidasStyleIds = devApiServiceHelper.performSeachServiceToGetStyleIds("adidas", "3", "true", "true");
		
		String[] styleid1 = { "Fox7", nikeStyleIds.get(0).toString(),"480" ,"HIGH"};
		String[] styleid2 = { "Fox7",nikeStyleIds.get(1).toString() ,"720","HIGH"};
		String[] styleid3 = { "Fox7", nikeStyleIds.get(2).toString(),"1080","HIGH" };
		String[] styleid4 = { "Fox7", adidasStyleIds.get(0).toString(),"480","HIGH" };
		String[] styleid5 = { "Fox7",adidasStyleIds.get(1).toString(),"720","HIGH" };
		
		String[] styleid6 = { "Production",adidasStyleIds.get(2).toString() ,"1080","HIGH"};
		String[] styleid7 = {"Production", nikeStyleIds.get(0).toString(),"480" ,"HIGH"};
		String[] styleid8 = {"Production", nikeStyleIds.get(1).toString(),"720" ,"HIGH"};
		String[] styleid9 = {"Production", nikeStyleIds.get(2).toString() ,"1080","HIGH"};
		String[] styleid10 = { "Production",nikeStyleIds.get(0).toString(),"480","HIGH" };
		
		String[] styleid11 = { "Functional",nikeStyleIds.get(1).toString() ,"720","HIGH"};
		String[] styleid12 = { "Functional",nikeStyleIds.get(2).toString() ,"1080","HIGH"};
		String[] styleid13 = { "Functional",adidasStyleIds.get(0).toString() ,"480","HIGH"};
		String[] styleid14 = { "Functional",adidasStyleIds.get(1).toString() ,"720","HIGH"};
		String[] styleid15 = { "Functional",adidasStyleIds.get(2).toString() ,"1080","HIGH"};
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7, styleid8, styleid9, styleid10, styleid11, styleid12, 
				styleid13, styleid14, styleid15 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	

	@DataProvider
	public Object[][] DevApisDataProvider_SearchAndGetStyleIdWithTopImageNode(ITestContext testContext) 
	{
		String[] styleid1 = { "Fox7","388782" ,"?w=480"};
		String[] styleid2 = { "Fox7","388778","?w=720"};
		String[] styleid3 = { "Fox7", "388051 ","?w=1080" };
		String[] styleid4 = { "Fox7", "386091","?w=480" };
		String[] styleid5 = { "Fox7","386091 ","?w=720"};
		
		String[] styleid6 = { "Production","388782" ,"?w=480"};
		String[] styleid7 = { "Production","388778","?w=720"};
		String[] styleid8 = { "Production", "388051 ","?w=1080" };
		String[] styleid9 = { "Production", "386091","?w=480" };
		String[] styleid10 = { "Production","386091 ","?w=720"};
		
		String[] styleid11 = { "Functional","388782" ,"?w=480"};
		String[] styleid12 = { "Functional","388778","?w=720"};
		String[] styleid13 = { "Functional", "388051 ","?w=1080" };
		String[] styleid14 = { "Functional", "386091","?w=480" };
		String[] styleid15 = { "Functional","386091 ","?w=720"};
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5 ,styleid6, styleid7, styleid8, styleid9, styleid10,
				styleid11, styleid12, styleid13, styleid14, styleid15};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
		
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_SearchAndGetStyleIdWithVideoAlbumList(ITestContext testContext) 
	{
		String[] styleid1 = { "Fox7","15631" ,"?w=480"};
		String[] styleid2 = { "Fox7","28719","?w=720"};
		String[] styleid3 = { "Fox7", "57098 ","?w=1080" };
		String[] styleid4 = { "Fox7", "104447","?w=480" };
		String[] styleid5 = { "Fox7","98498 ","?w=720"};
		
		String[] styleid6 = { "Production","15631" ,"?w=480"};
		String[] styleid7 = { "Production","28719","?w=720"};
		String[] styleid8 = { "Production", "57098 ","?w=1080" };
		String[] styleid9 = { "Production", "104447","?w=480" };
		String[] styleid10 = { "Production","98498 ","?w=720"};
		
		String[] styleid11 = { "Functional","15631" ,"?w=480"};
		String[] styleid12 = { "Functional","28719","?w=720"};
		String[] styleid13 = { "Functional", "57098 ","?w=1080" };
		String[] styleid14 = { "Functional", "104447","?w=480" };
		String[] styleid15 = { "Functional","98498 ","?w=720"};
		
		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5 ,styleid6, styleid7, styleid8, styleid9, styleid10,
				styleid11, styleid12, styleid13, styleid14, styleid15};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
		
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_AppReferral_GetReferralCode(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","aravind.raj@myntra.com" ,"welcome"};
		String[] UserData2 = { "Production","aravind.raj@myntra.com" ,"welcome"};
		String[] UserData3 = { "Functional","aravind.raj@myntra.com" ,"welcome" };
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_AppReferral_VerifyReferralCode(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","aravind.raj@myntra.com" ,"welcome","ermb0h"};
		String[] UserData2 = { "Production","aravind.raj@myntra.com" ,"welcome","ermb0h"};
		String[] UserData3 = { "Functional","aravind.raj@myntra.com" ,"welcome","ermb0h" };
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_AppReferral_VerifyReferralCode_Failure(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","aravind.raj@myntra.com" ,"welcome","ermb0h","N"};
		String[] UserData2 = { "Fox7","aravind.raj@myntra.com" ,"welcome","ermb0ha","P"};
		String[] UserData3 = { "Production","aravind.raj@myntra.com" ,"welcome","ermb0h","N"};
		String[] UserData4 = { "Production","aravind.raj@myntra.com" ,"welcome","ermb0ha","P"};
		String[] UserData5 = { "Functional","aravind.raj@myntra.com" ,"welcome","ermb0h","N"};
		String[] UserData6 = { "Functional","aravind.raj@myntra.com" ,"welcome","ermb0ha","P"};
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3,UserData4,UserData5,UserData6};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_AppReferral_PostEvent(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","aravind.raj@myntra.com" ,"welcome","ermb0h"};
		String[] UserData2 = { "Production","aravind.raj@myntra.com" ,"welcome","ermb0h"};
		String[] UserData3 = { "Functional","aravind.raj@myntra.com" ,"welcome","ermb0h" };
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	
	@DataProvider
	public Object[][] DevApisDataProvider_AppReferral_VerifyOTP(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","aravind.raj@myntra.com" ,"welcome","9741284454","8977"};
		String[] UserData2 = { "Production","aravind.raj@myntra.com" ,"welcome","9741284454","8977"};
		String[] UserData3 = { "Functional","aravind.raj@myntra.com" ,"welcome","9741284454","8977" };
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_AppReferral_Welcome(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","aravind.raj@myntra.com" ,"welcome","ermb0h"};
		String[] UserData2 = { "Production","aravind.raj@myntra.com" ,"welcome","ermb0h"};
		String[] UserData3 = { "Functional","aravind.raj@myntra.com" ,"welcome","ermb0h"};
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_AppReferral_Welcome_Failure(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","aravind.raj@myntra.com" ,"welcome","ermb0h","N"};
		String[] UserData2 = { "Fox7","aravind.raj@myntra.com" ,"welcome","ermb0ha","P"};
		String[] UserData3 = { "Production","aravind.raj@myntra.com" ,"welcome","ermb0h","N"};
		String[] UserData4 = { "Production","aravind.raj@myntra.com" ,"welcome","ermb0ha","P"};
		String[] UserData5 = { "Functional","aravind.raj@myntra.com" ,"welcome","ermb0h","N"};
		String[] UserData6 = { "Functional","aravind.raj@myntra.com" ,"welcome","ermb0ha","P"};
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3,UserData4,UserData5,UserData6};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_AppReferral_GetDetails(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","aravind.raj@myntra.com" ,"welcome"};
		String[] UserData2 = { "Production","aravind.raj@myntra.com" ,"welcome"};
		String[] UserData3 = { "Functional","aravind.raj@myntra.com" ,"welcome"};
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApisDataProvider_AppReferral_GetOTP_Failure(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","aravind.raj@myntra.com" ,"welcome","9741284454","N"};
		String[] UserData2 = { "Fox7","aravind.raj@myntra.com" ,"welcome","9741284454ABCD","P"};
		String[] UserData3 = { "Production","aravind.raj@myntra.com" ,"welcome","9741284454","N"};
		String[] UserData4 = { "Production","aravind.raj@myntra.com" ,"welcome","9741284454ABCD","P"};
		String[] UserData5 = { "Production","aravind.raj@myntra.com" ,"welcome","0741284454","P"};
		String[] UserData6 = { "Production","aravind.raj@myntra.com" ,"welcome","1741284454","P"};
		String[] UserData7 = { "Production","aravind.raj@myntra.com" ,"welcome","2741284454","P"};
		String[] UserData8 = { "Production","aravind.raj@myntra.com" ,"welcome","3741284454","P"};
		String[] UserData9 = { "Production","aravind.raj@myntra.com" ,"welcome","4741284454","P"};
		String[] UserData10 = { "Production","aravind.raj@myntra.com" ,"welcome","5741284454","P"};
		String[] UserData11 = { "Production","aravind.raj@myntra.com" ,"welcome","6741284454","P"};
		String[] UserData12 = { "Production","aravind.raj@myntra.com" ,"welcome","7741284454","P"};
		String[] UserData13= { "Functional","aravind.raj@myntra.com" ,"welcome","9741284454","N"};
		String[] UserData14 = { "Functional","aravind.raj@myntra.com" ,"welcome","9741284454ABCD","P"};
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3,UserData4,UserData5,UserData6,UserData7,UserData8,UserData9,UserData10,UserData11,UserData12,UserData13,UserData14};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
    public Object[][] DevApisGetAutoSuggest(ITestContext testContext)
	{
		String[] arr1 = new String[]{"Fox7","aravind.devapi.usr01@myntra.com","randompass","nik"};
		String[] arr2 = new String[]{"Production","aravind.devapi.usr01@myntra.com","randompass","reeb"};
		String[] arr3 = new String[]{"Functional","aravind.devapi.usr01@myntra.com","randompass","lee"};
        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
        
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] DevApisGetCartCount(ITestContext testContext)
	{
		String[] arr1 = new String[]{"Fox7","aravind.devapi.usr02@myntra.com","randompass","123456"};
		String[] arr2 = new String[]{"Production","aravind.devapi.usr02@myntra.com","randompass","123456"};
		String[] arr3 = new String[]{"Functional","aravind.devapi.usr02@myntra.com","randompass","123456"};
        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
        
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] DevApisGetUserTimeline(ITestContext testContext)
	{
		String[] arr1 = new String[]{"Fox7","aravind.devapi.usr01@myntra.com","randompass"};
		String[] arr2 = new String[]{"Production","aravind.devapi.usr01@myntra.com","randompass"};
		String[] arr3 = new String[]{"Functional","aravind.devapi.usr01@myntra.com","randompass"};
        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
        
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	

	@DataProvider
    public Object[][] DevApisGetProfileCount(ITestContext testContext)
	{
		String[] arr1 = new String[]{"Fox7","aravind.devapi.usr01@myntra.com","randompass"};
		String[] arr2 = new String[]{"Production","aravind.devapi.usr01@myntra.com","randompass"};
		String[] arr3 = new String[]{"Functional","aravind.devapi.usr01@myntra.com","randompass"};
        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
        
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] DevApi_Search_withRequestId(ITestContext testContext)
	{
		String[] arr1 = new String[]{"Fox7","nike","null","1"};
		String[] arr2 = new String[]{"Production","nike","null","1"};
		String[] arr3 = new String[]{"Functional","nike","null","1"};
        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
        
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] DevApi_feedback_Success(ITestContext testContext)
	{
		String[] arr1 = new String[]{"Fox7","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Report an error","false"};
		String[] arr2 = new String[]{"Fox7","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Give a suggestion","false"};
		String[] arr3 = new String[]{"Fox7","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Anything else","false"};
		String[] arr4 = new String[]{"Fox7","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Report an error","false"};
		String[] arr5 = new String[]{"Fox7","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Give a suggestion","false"};
		String[] arr6 = new String[]{"Fox7","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Anything else","false"};
		String[] arr7 = new String[]{"Fox7","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Report an error","true"};
		String[] arr8 = new String[]{"Fox7","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Give a suggestion","true"};
		String[] arr9 = new String[]{"Fox7","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Anything else","true"};
		String[] arr10 = new String[]{"Fox7","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Report an error","true"};
		String[] arr11 = new String[]{"Fox7","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Give a suggestion","true"};
		String[] arr12 = new String[]{"Fox7","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Anything else","true"};
		String[] arr13 = new String[]{"Fox7","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","","true"};
		String[] arr14 = new String[]{"Fox7","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","","true"};
		String[] arr15 = new String[]{"Fox7","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","","Anything else","true"};
		String[] arr16  = new String[]{"Fox7","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","","Report an error","true"};
	
		String[] arr17 = new String[]{"Production","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Report an error","false"};
		String[] arr18 = new String[]{"Production","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Give a suggestion","false"};
		String[] arr19 = new String[]{"Production","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Anything else","false"};
		String[] arr20= new String[]{"Production","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Report an error","false"};
		String[] arr21 = new String[]{"Production","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Give a suggestion","false"};
		String[] arr22= new String[]{"Production","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Anything else","false"};
		String[] arr23= new String[]{"Production","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Report an error","true"};
		String[] arr24 = new String[]{"Production","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Give a suggestion","true"};
		String[] arr25 = new String[]{"Production","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Anything else","true"};
		String[] arr26 = new String[]{"Production","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Report an error","true"};
		String[] arr27 = new String[]{"Production","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Give a suggestion","true"};
		String[] arr28 = new String[]{"Production","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Anything else","true"};
		String[] arr29 = new String[]{"Production","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","","true"};
		String[] arr30 = new String[]{"Production","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","","true"};
		String[] arr31 = new String[]{"Production","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","","Anything else","true"};
		String[] arr32 = new String[]{"Production","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","","Report an error","true"};

		String[] arr33 = new String[]{"Functional","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Report an error","false"};
		String[] arr34 = new String[]{"Functional","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Give a suggestion","false"};
		String[] arr35 = new String[]{"Functional","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Anything else","false"};
		String[] arr36= new String[]{"Functional","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Report an error","false"};
		String[] arr37 = new String[]{"Functional","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Give a suggestion","false"};
		String[] arr38= new String[]{"Functional","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Anything else","false"};
		String[] arr39= new String[]{"Functional","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Report an error","true"};
		String[] arr40 = new String[]{"Functional","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Give a suggestion","true"};
		String[] arr41 = new String[]{"Functional","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Anything else","true"};
		String[] arr42 = new String[]{"Functional","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Report an error","true"};
		String[] arr43 = new String[]{"Functional","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Give a suggestion","true"};
		String[] arr44 = new String[]{"Functional","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","Anything else","true"};
		String[] arr45 = new String[]{"Functional","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","","true"};
		String[] arr46 = new String[]{"Functional","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","Feedback Message!","","true"};
		String[] arr47 = new String[]{"Functional","RegisteredUser","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","","Anything else","true"};
		String[] arr48 = new String[]{"Functional","Anonymous","aravind.raj@myntra.com","welcome","aravind.raj@myntra.com","","Report an error","true"};

		
		
		   Object[][] dataSet = new Object[][]{ arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12,arr13,arr14,arr14,arr15,arr16,arr17,arr18,arr19,arr20,arr21,arr22,arr23,arr24,arr25,arr26,arr27,arr28,arr29,arr30,arr31,arr32,arr33,arr34,arr35,arr36,arr37,arr38,arr39,arr40,arr41,arr42,arr43,arr45,arr46,arr47,arr48};
        
        return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
	public Object[][] DevApisGetVisualSearch(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","http://assets.myntassets.com/assets/images/2016/3/16/11458112304923-14657-1rv0g8.png" ,"true"};
		String[] UserData2 = { "Fox7","http://assets.myntassets.com/assets/images/2016/3/16/11458112304923-14657-1rv0g8.png" ,"true"};
		String[] UserData3 = { "Production","http://assets.myntassets.com/assets/images/2016/3/16/11458112304923-14657-1rv0g8.png" ,"true"};
		String[] UserData4 = { "Production","http://assets.myntassets.com/assets/images/2016/3/16/11458112304923-14657-1rv0g8.png" ,"true"};
		String[] UserData5 = { "Functional","http://assets.myntassets.com/assets/images/2016/3/16/11458112304923-14657-1rv0g8.png" ,"true"};
		String[] UserData6 = { "Functional","http://assets.myntassets.com/assets/images/2016/3/16/11458112304923-14657-1rv0g8.png" ,"true"};
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3,UserData4,UserData5,UserData6};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApiSetUserPrivacy(ITestContext testContext) 
	{
		String[] UserData1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome","0"};
		String[] UserData2 = new String[]{"Production","aravind.raj@myntra.com","welcome","0"};
		String[] UserData3 = new String[]{"Functional","aravind.raj@myntra.com","welcome","0"};
       		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApiSetUserPrivacyFlow(ITestContext testContext) 
	{
		String[] UserData1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome","aravind.privacy.2@myntra.com","randompass"};
		String[] UserData2 = new String[]{"Production","aravind.raj@myntra.com","welcome","aravind.privacy.2@myntra.com","randompass"};
		String[] UserData3 = new String[]{"Functional","aravind.raj@myntra.com","welcome","aravind.privacy.2@myntra.com","randompass"};
       		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] Pumps_PPID_UpdateFlow(ITestContext testContext) 
	{
		String[] UserData1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] UserData2 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
		String[] UserData3 = new String[]{"Functional","aravind.raj@myntra.com","welcome"};
       	Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	//PPID Availability
	@DataProvider
	public Object[][] Check_PPID_Availability(ITestContext testContext) 
	{
		String[] UserData1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome","RandomPPID"};
		String[] UserData2 = new String[]{"Production","aravind.raj@myntra.com","welcome","RandomPPID"};
		String[] UserData3 = new String[]{"Functional","aravind.raj@myntra.com","welcome","RandomPPID"};
       	Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	
	@DataProvider
	public Object[][] Check_PPID_Availability_Failure(ITestContext testContext) 
	{
		String[] UserData1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome","RandomPPID","aravind.privacy.2@myntra.com","randompass"};
		String[] UserData2 = new String[]{"Production","aravind.raj@myntra.com","welcome","RandomPPID","aravind.privacy.2@myntra.com","randompass"};
		String[] UserData3 = new String[]{"Functional","aravind.raj@myntra.com","welcome","RandomPPID","aravind.privacy.2@myntra.com","randompass"};
       	Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	
	//Hall Mark Services - GET & SAVE User Onboarding State
	//3rd Parameter is test scenario - NONE,DEVICEID,XID,BOTH
	@DataProvider
	public Object[][] HallMark_GetOnBoardingStrategies(ITestContext testContext) 
	{
		String[] UserData1 = new String[]{"Fox7",getRandomEmailId(15),"welcome","NONE","new","DeviceID123"};
		String[] UserData2 = new String[]{"Fox7",getRandomEmailId(15),"welcome","DEVICEID","new","DeviceID123"};
		String[] UserData3 = new String[]{"Fox7",getRandomEmailId(15),"welcome","XID","new","DeviceID123"};
		String[] UserData4 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData5 = new String[]{"Fox7",getRandomEmailId(15),"welcome","NONE","upgrade","DeviceID123"};
		String[] UserData6 = new String[]{"Fox7",getRandomEmailId(15),"welcome","DEVICEID","upgrade","DeviceID123"};
		String[] UserData7 = new String[]{"Fox7",getRandomEmailId(15),"welcome","XID","upgrade","DeviceID123"};
		String[] UserData8 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
		String[] UserData9 = new String[]{"Production",getRandomEmailId(15),"welcome","NONE","new","DeviceID123"};
		String[] UserData10 = new String[]{"Production",getRandomEmailId(15),"welcome","DEVICEID","new","DeviceID123"};
		String[] UserData11= new String[]{"Production",getRandomEmailId(15),"welcome","XID","new","DeviceID123"};
		String[] UserData12 = new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData13= new String[]{"Production",getRandomEmailId(15),"welcome","NONE","upgrade","DeviceID123"};
		String[] UserData14 = new String[]{"Production",getRandomEmailId(15),"welcome","DEVICEID","upgrade","DeviceID123"};
		String[] UserData15 = new String[]{"Production",getRandomEmailId(15),"welcome","XID","upgrade","DeviceID123"};
		String[] UserData16 = new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
   	Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3,UserData4,UserData5,UserData6,UserData7,UserData8,UserData9,UserData10,UserData11,UserData12,UserData13,UserData14,UserData15,UserData16};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] HallMark_SaveOnBoardingStrategies(ITestContext testContext) 
	{
		String[] UserData1 = new String[]{"Fox7",getRandomEmailId(15),"welcome","NONE","new","DeviceID123"};
		String[] UserData2 = new String[]{"Fox7",getRandomEmailId(15),"welcome","DEVICEID","new","DeviceID123"};
		String[] UserData3 = new String[]{"Fox7",getRandomEmailId(15),"welcome","XID","new","DeviceID123"};
		String[] UserData4 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData5 = new String[]{"Fox7",getRandomEmailId(15),"welcome","NONE","upgrade","DeviceID123"};
		String[] UserData6 = new String[]{"Fox7",getRandomEmailId(15),"welcome","DEVICEID","upgrade","DeviceID123"};
		String[] UserData7 = new String[]{"Fox7",getRandomEmailId(15),"welcome","XID","upgrade","DeviceID123"};
		String[] UserData8 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
		String[] UserData9 = new String[]{"Production",getRandomEmailId(15),"welcome","NONE","new","DeviceID123"};
		String[] UserData10 = new String[]{"Production",getRandomEmailId(15),"welcome","DEVICEID","new","DeviceID123"};
		String[] UserData11= new String[]{"Production",getRandomEmailId(15),"welcome","XID","new","DeviceID123"};
		String[] UserData12 = new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData13= new String[]{"Production",getRandomEmailId(15),"welcome","NONE","upgrade","DeviceID123"};
		String[] UserData14 = new String[]{"Production",getRandomEmailId(15),"welcome","DEVICEID","upgrade","DeviceID123"};
		String[] UserData15 = new String[]{"Production",getRandomEmailId(15),"welcome","XID","upgrade","DeviceID123"};
		String[] UserData16 = new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
   	Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3,UserData4,UserData5,UserData6,UserData7,UserData8,UserData9,UserData10,UserData11,UserData12,UserData13,UserData14,UserData15,UserData16};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevAPI_saveOnboardingState_Dependant_Flow1_SameUser_SameDevice(ITestContext testContext) 
	{
		
		String[] UserData3 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData7 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
		String[] UserData11= new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData15 = new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
   	Object[][] dataSet = new Object[][] {UserData3,UserData7,UserData11,UserData15};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevAPI_saveOnboardingState_Main_Flow3_SameDevice_DifferentUser(ITestContext testContext) 
	{
		
		String[] UserData3 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData7 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
		String[] UserData11= new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData15 = new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
   	Object[][] dataSet = new Object[][] {UserData3,UserData7,UserData11,UserData15};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApiGetEffectivePercentage(ITestContext testContext) 
	{
		String[] UserData1 = new String[]{"Fox7","308428","B2G1"};
		String[] UserData2 = new String[]{"Fox7","252817","B3G1"};
		String[] UserData3 = new String[]{"Fox7","252752","B3G2"};		
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public Object[][] DevApiGetDisclaimerMessage(ITestContext testContext) 
	{
		String[] UserData1 = new String[]{"Fox7","boxer"};
		String[] UserData2 = new String[]{"Fox7","trunk"};
		String[] UserData3 = new String[]{"Production","boxer"};		
		String[] UserData4 = new String[]{"Production","trunk"};	
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	

	@DataProvider
	public Object[][] StyleServiceability_V3(ITestContext testContext) 
	{
		String[] id1 = { "Fox7", "pincode=560068&styleid=1531&availableinwarehouses=36"};
		String[] id2 = { "Fox7", "pincode=560001&styleid=1535&availableinwarehouses=36"};
		String[] id3 = { "Fox7", "pincode=560068&styleid=1538&availableinwarehouses=36"};
		String[] id4 = { "Fox7", "pincode=560001&styleid=1549&availableinwarehouses=36"};
		String[] id5 = { "Fox7", "pincode=560001&styleid=1549&availableinwarehouses=36"};
		String[] id6 = { "Fox7", "pincode=560068&styleid=1554&availableinwarehouses=36"};
		String[] id7 = { "Fox7", "pincode=560001&styleid=1556&availableinwarehouses=36"};
		String[] id8 = { "Fox7", "pincode=560068&styleid=1557&availableinwarehouses=36"};
		String[] id9 = { "Fox7", "pincode=560068&styleid=282672&availableinwarehouses=36"};
		String[] id10 = { "Fox7", "pincode=560001&styleid=292918&availableinwarehouses=36"};
				
		String[] id11 = { "#Production", "pincode=560068&styleid=106236&availableinwarehouses=36"};
		String[] id12 = { "#Production", "pincode=560068&styleid=1268840&availableinwarehouses=36"};

		String[] id13 = { "Functional", "pincode=560068&styleid=1531&availableinwarehouses=36"};
		String[] id14 = { "Functional", "pincode=560068&styleid=1531&availableinwarehouses=36"};
		String[] id15 = { "Functional", "pincode=560068&styleid=1531&availableinwarehouses=36"};
		String[] id16 = { "Functional", "pincode=560068&styleid=1531&availableinwarehouses=36"};
		String[] id17 = { "Functional", "pincode=560068&styleid=1531&availableinwarehouses=36"};
		String[] id18 = { "Functional", "pincode=560068&styleid=1531&availableinwarehouses=36"};
		String[] id19 = { "Functional", "pincode=560068&styleid=1531&availableinwarehouses=36"};
		String[] id20 = { "Functional", "pincode=560068&styleid=1531&availableinwarehouses=36"};
		String[] id21 = { "Functional", "pincode=560068&styleid=1531&availableinwarehouses=36"};
		String[] id22 = { "Functional", "pincode=560068&styleid=1531&availableinwarehouses=36"};
		
		Object[][] dataSet = new Object[][] { id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12, id13, id14, id15, id16, id17, id18, id19, id20, id21, id22 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] AddToCArt_50Item_errorCheck(ITestContext testContext) 
	{
		String[] UserData1 = new String[]{"Fox7","manu.chadha@myntra.com","trinity"};
		String[] UserData2 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
		   	Object[][] dataSet = new Object[][] {UserData1,UserData2};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] ServiceabilityDisclaimerLogicTest(ITestContext testContext)
	{
		String[] case1 = {"Fox7","true","false","false",""};
		Object[][] dataSet = new Object[][] { case1};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	
	}
	
	@DataProvider
	public Object[][] V2Serviceability(ITestContext testContext)
	{
		String[] case1 = { "Fox7", "pincode=560068&styleid=1557&availableinwarehouses=36"};
		String[] case2 = { "Fox7", "pincode=560068&styleid=282672&availableinwarehouses=36"};
		String[] case3 = { "Fox7", "pincode=560001&styleid=292918&availableinwarehouses=36"};				
		String[] case4 = { "Production", "pincode=560068&styleid=106236&availableinwarehouses=36"};
		String[] case5 = { "Production", "pincode=560068&styleid=1268840&availableinwarehouses=36"};

		Object[][] dataSet = new Object[][] { case1,case2,case3,case4,case5};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	
	}
	
	@DataProvider
	public Object[][] V3Serviceability(ITestContext testContext)
	{
		String[] case1 = { "Fox7", "pincode=560068&styleid=1557&availableinwarehouses=36"};
		String[] case2 = { "Fox7", "pincode=560068&styleid=282672&availableinwarehouses=36"};
		String[] case3 = { "Fox7", "pincode=560001&styleid=292918&availableinwarehouses=36"};				
		String[] case4 = { "Production", "pincode=560068&styleid=106236&availableinwarehouses=36"};
		String[] case5 = { "Production", "pincode=560068&styleid=1268840&availableinwarehouses=36"};

		Object[][] dataSet = new Object[][] { case1,case2,case3,case4,case5};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	
	}
	
	@DataProvider
	public Object[][] GetUserCoupons_Success(ITestContext testContext)
	{
		//Last parameter decides whether to use Mail to get the coupons
		String[] case1 = { "Production", "aravind.raj@myntra.com","welcome","true"};
		String[] case2 = { "Production", "aravind.raj@myntra.com","welcome","false"};
		Object[][] dataSet = new Object[][] { case1,case2};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	
	}
	
	@DataProvider
	public Object[][] GetUserCoupons_ByEmail(ITestContext testContext)
	{
		//Last parameter decides whether to use Mail to get the coupons
		String[] case1 = { "Production", "aravind.raj@myntra.com","welcome","true"};
		Object[][] dataSet = new Object[][] { case1};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	
	}
	
	@DataProvider
	public Object[][] GetUserCoupons_ByUIDX(ITestContext testContext)
	{
		//Last parameter decides whether to use Mail to get the coupons
		String[] case1 = { "Production", "aravind.raj@myntra.com","welcome","false"};
		Object[][] dataSet = new Object[][] { case1};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	
	}
	
	
	@DataProvider
	public Object[][] GetSimilarItems_Success(ITestContext testContext)
	{
		String[] case1 = { "Production", "flying-machine-navy-casual-shirt","1239469","false"};
		String[] case2 = { "Production", "campus-sutra-black--grey-colourblock-t-shirt","1405187","false"};
		Object[][] dataSet = new Object[][] { case1,case2};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	
	}
	
	@DataProvider
	public Object[][] GetSimilarItems_Failure(ITestContext testContext)
	{
		String[] case1 = { "Production", "flying-machine-navy-casual-shirt","asdasdsad","false","404"};
		String[] case2 = { "Production", "werwrwerwer","1405187","false","200"};
		Object[][] dataSet = new Object[][] { case1,case2};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	
	}
	
	@DataProvider
	public Object[][] GetVisuallySimilarItems_Success(ITestContext testContext)
	{
		String[] case1 = { "Production", "flying-machine-navy-casual-shirt","1239469","true"};
		String[] case2 = { "Production", "campus-sutra-black--grey-colourblock-t-shirt","1405187","true"};
		Object[][] dataSet = new Object[][] { case1,case2};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	
	}
	
	@DataProvider
	public Object[][] VisualSimilarItems_Success(ITestContext testContext)
	{
		String[] case1 = { "Production", "pincode=560068&styleid=1557&availableinwarehouses=36"};
		String[] case2 = { "Production", "pincode=560068&styleid=282672&availableinwarehouses=36"};
		String[] case3 = { "Production", "pincode=560001&styleid=292918&availableinwarehouses=36"};				
		String[] case4 = { "Production", "pincode=560068&styleid=106236&availableinwarehouses=36"};
		String[] case5 = { "Production", "pincode=560068&styleid=1268840&availableinwarehouses=36"};

		Object[][] dataSet = new Object[][] { case1,case2,case3,case4,case5};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	
	}
	
	
	
	
}
