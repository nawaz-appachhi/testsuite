package com.myntra.apiTests.dataproviders;

import java.util.UUID;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.portalservices.idpservice.IDPServiceDataProviderEnum;
import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

/**
 * @author shankara.c
 *
 */
public class MobileIdpDP extends CommonUtils
{
	static Logger log = Logger.getLogger(MobileIdpDP.class);
	
	Configuration con = new Configuration("./Data/configuration");
	
	String env;
	
	public MobileIdpDP(){
		
		if(System.getenv(IDPServiceDataProviderEnum.ENVIRONMENT.name())==null)
			env = con.GetTestEnvironemnt().toString();
		else
			env = System.getenv(IDPServiceDataProviderEnum.ENVIRONMENT.name());
		
	}
	
	@DataProvider
	public Object[][] MobileIdpDP_signInDataProvider_verifySuccessResponse(ITestContext iTestContext)
	{
		String[] arr1 = { "mobileappservice-idp@myntra.com", "testing",	"signin", "200" };
		String[] arr2 = { "mobileappservice-idp@yahoo.com", "testing",	"signin", "200" };
		String[] arr3 = { "mobileappservice-100@rediffmail.com", "testing",	"signin", "200" };
		String[] arr4 = { "mobileappservice-101@gmail.com", "testing", "signin", "200" };
		String[] arr5 = { "mobileappservice_101@hotmail.com", "testing", "signin", "200" };
		String[] arr6 = { "mobileappservice_Nx1gqlEPuuAjXAq@hotmail.com", "Nx1gqlEPuuAjXAq", "signin", "200" };
		String[] arr7 = { "mobileappservice_idp100@myntra.com", "123456", "signin", "200" };
		String[] arr8 = { "mobileappservice_idp100@gmail.com", "123456", "signin", "200" };
		//Case Sensitive UserId
		String[] arr9 = {"MOBILEappservicE_Nx1gqlEPuuAjXAq@hotmail.com","Nx1gqlEPuuAjXAq", "signin", "200" };
		//Leading Spaces UserId
		String[] arr10 = {"mobileappservice_101@hotmail.com   ","testing", "signin", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileIdpDP_signInDataProvider_verifyMetaTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "mobileappservice-idp@myntra.com", "testing",	"signin", "200" };
		String[] arr2 = { "mobileappservice-idp@yahoo.com", "testing",	"signin", "200" };
		String[] arr3 = { "mobileappservice-100@rediffmail.com", "testing",	"signin", "200" };
		String[] arr4 = { "mobileappservice-101@gmail.com", "testing", "signin", "200" };
		String[] arr5 = { "mobileappservice_101@hotmail.com", "testing", "signin", "200" };
		String[] arr6 = { "mobileappservice_Nx1gqlEPuuAjXAq@hotmail.com", "Nx1gqlEPuuAjXAq", "signin", "200" };
		String[] arr7 = { "mobileappservice_idp100@myntra.com", "123456", "signin", "200" };
		String[] arr8 = { "mobileappservice_idp100@gmail.com", "123456", "signin", "200" };
		//Case Sensitive UserId
		String[] arr9 = {"MOBILEappservicE_Nx1gqlEPuuAjXAq@hotmail.com","Nx1gqlEPuuAjXAq", "signin", "200" };
		//Leading Spaces UserId
		String[] arr10 = {"mobileappservice_101@hotmail.com   ","testing", "signin", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileIdpDP_signInDataProvider_verifyNotificationTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "mobileappservice-idp@myntra.com", "testing",	"signin", "200" };
		String[] arr2 = { "mobileappservice-idp@yahoo.com", "testing",	"signin", "200" };
		String[] arr3 = { "mobileappservice-100@rediffmail.com", "testing",	"signin", "200" };
		String[] arr4 = { "mobileappservice-101@gmail.com", "testing", "signin", "200" };
		String[] arr5 = { "mobileappservice_101@hotmail.com", "testing", "signin", "200" };
		String[] arr6 = { "mobileappservice_Nx1gqlEPuuAjXAq@hotmail.com", "Nx1gqlEPuuAjXAq", "signin", "200" };
		String[] arr7 = { "mobileappservice_idp100@myntra.com", "123456", "signin", "200" };
		String[] arr8 = { "mobileappservice_idp100@gmail.com", "123456", "signin", "200" };
		//Case Sensitive UserId
		String[] arr9 = {"MOBILEappservicE_Nx1gqlEPuuAjXAq@hotmail.com","Nx1gqlEPuuAjXAq", "signin", "200" };
		//Leading Spaces UserId
		String[] arr10 = {"mobileappservice_101@hotmail.com   ","testing", "signin", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileIdpDP_signInDataProvider_verifyDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "mobileappservice-idp@myntra.com", "testing",	"signin", "200" };
		String[] arr2 = { "mobileappservice-idp@yahoo.com", "testing",	"signin", "200" };
		String[] arr3 = { "mobileappservice-100@rediffmail.com", "testing",	"signin", "200" };
		String[] arr4 = { "mobileappservice-101@gmail.com", "testing", "signin", "200" };
		String[] arr5 = { "mobileappservice_101@hotmail.com", "testing", "signin", "200" };
		String[] arr6 = { "mobileappservice_Nx1gqlEPuuAjXAq@hotmail.com", "Nx1gqlEPuuAjXAq", "signin", "200" };
		String[] arr7 = { "mobileappservice_idp100@myntra.com", "123456", "signin", "200" };
		String[] arr8 = { "mobileappservice_idp100@gmail.com", "123456", "signin", "200" };
		//Case Sensitive UserId
		String[] arr9 = {"MOBILEappservicE_Nx1gqlEPuuAjXAq@hotmail.com","Nx1gqlEPuuAjXAq", "signin", "200" };
		//Leading Spaces UserId
		String[] arr10 = {"mobileappservice_101@hotmail.com   ","testing", "signin", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] MobileIdpDP_signInDataProvider_verifyFailureResponse(ITestContext iTestContext)
	{
		//Invalid UserId
		String[] arr1 = { "dfdffdfk@fjkdfjkd.com", "Sherlock1@12345", "signin",	"401", "The username or password you entered is incorrect","ERROR" };
		//Empty Password
		String[] arr2 = {"mobileappservice-idp@myntra.com", "", "signin", "401", "The username or password you entered is incorrect","ERROR" };
		//Empty User Name
		String[] arr3 = {"","123456", "signin", "401", "The username or password you entered is incorrect","ERROR" };
		//Sql Injections for UserID
		String[] arr4 = {"1=1' or '1=1","123456", "signin","401", "The username or password you entered is incorrect","ERROR" };
		//Invalid emaildId
		String[] arr5 = {"2987129837498123%^#^%%(*^&^)", "123456", "signin", "401", "The username or password you entered is incorrect","ERROR" };
		//Special characters userId
		String[] arr6 = { "mobileappservice-idp!#$%@myntra.com", "123456", "signin", "401", "The username or password you entered is incorrect","ERROR" };
		//Trailing spaces userId
		String[] arr7 = { "   mobileappservice-idp@myntra.com", "123456", "signin", "401", "The username or password you entered is incorrect","ERROR" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] MobileIdpDP_signUpDataProvider_verifySuccessResponse(ITestContext iTestContext)
	{
		String[] arr1 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr2 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr3 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr4 = { generateRandomId() + "@myntra.com", "123456123", "signup", "200" };
		String[] arr5 = { generateRandomId() + "@rediffmail.com", "123456123", "signup", "200" };
		String[] arr6 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr7 = { generateRandomId() + "@yahoo.com", "123456123", "signup", "200" };
		String[] arr8 = { generateRandomId() + "@rediffmail.com", "123456123", "signup", "200" };
		//Leading, Trailing and middle Spaces Password
		String[] arr9 = {generateRandomId() + "@gmail.com", " 12345  6123 ", "signup", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileIdpDP_signUpDataProvider_verifyMetaTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr2 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr3 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr4 = { generateRandomId() + "@myntra.com", "123456123", "signup", "200" };
		String[] arr5 = { generateRandomId() + "@rediffmail.com", "123456123", "signup", "200" };
		String[] arr6 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr7 = { generateRandomId() + "@yahoo.com", "123456123", "signup", "200" };
		String[] arr8 = { generateRandomId() + "@rediffmail.com", "123456123", "signup", "200" };
		//Leading, Trailing and middle Spaces Password
		String[] arr9 = {generateRandomId() + "@gmail.com", " 12345  6123 ", "signup", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileIdpDP_signUpDataProvider_verifyNotificationTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr2 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr3 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr4 = { generateRandomId() + "@myntra.com", "123456123", "signup", "200" };
		String[] arr5 = { generateRandomId() + "@rediffmail.com", "123456123", "signup", "200" };
		String[] arr6 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr7 = { generateRandomId() + "@yahoo.com", "123456123", "signup", "200" };
		String[] arr8 = { generateRandomId() + "@rediffmail.com", "123456123", "signup", "200" };
		//Leading, Trailing and middle Spaces Password
		String[] arr9 = {generateRandomId() + "@gmail.com", " 12345  6123 ", "signup", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileIdpDP_signUpDataProvider_verifyDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr2 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr3 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr4 = { generateRandomId() + "@myntra.com", "123456123", "signup", "200" };
		String[] arr5 = { generateRandomId() + "@rediffmail.com", "123456123", "signup", "200" };
		String[] arr6 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr7 = { generateRandomId() + "@yahoo.com", "123456123", "signup", "200" };
		String[] arr8 = { generateRandomId() + "@rediffmail.com", "123456123", "signup", "200" };
		//Leading, Trailing and middle Spaces Password
		String[] arr9 = {generateRandomId() + "@gmail.com", " 12345  6123 ", "signup", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileIdpDP_signUpDataProvider_verifyFailureResponse(ITestContext iTestContext)
	{
		//Invalid UserId
		String[] arr1 = { "dfdffdfk@fjkdfjkd.com", "Sherlock1@12345", "signin",	"401", "The username or password you entered is incorrect","ERROR" };
		//Empty username and pass
		String[] arr2 = { "", "", "signup", "500", "Signup error occured", "ERROR" };
		//invalid username
		String[] arr3 = { "username@", "username", "signup", "500", "Signup error occured", "ERROR" };
		//Invalid User Name
		String[] arr4 = { "username@myntra", "username", "signup", "500", "Signup error occured", "ERROR" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileIdpDP_signUpDataProvider_verifyForExistingUser(ITestContext iTestContext)
	{
		//Already registered user name
		String[] arr1 = { "mobileappservice-idp@myntra.com", "testing",	"signup", "405", "The username you entered already exists", "ERROR" };
		//Case sensitive user name
		String[] arr2 = { "MOBILEappservice-idp@myntra.com", "testing",	"signup", "405", "The username you entered already exists", "ERROR" };
		//Leading, Trailing spaces password
		String[] arr3 = { "mobileappservice-idp@myntra.com", "   testing   ", "signup", "405", "The username you entered already exists", "ERROR" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	public static String generateRandomId()
	{
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(uuid.lastIndexOf("-") + 1, uuid.length());
	}
	
	@DataProvider
	public Object[][] MobileIdpDP_signInDataProvider_verifyResponseDataNodesUsingSchemaValidations(ITestContext iTestContext)
	{
		String[] arr1 = { "mobileappservice-idp@myntra.com", "testing",	"signin", "200" };
		String[] arr2 = { "mobileappservice-idp@yahoo.com", "testing",	"signin", "200" };
		String[] arr3 = { "mobileappservice-100@rediffmail.com", "testing",	"signin", "200" };
		String[] arr4 = { "mobileappservice-101@gmail.com", "testing", "signin", "200" };
		String[] arr5 = { "mobileappservice_101@hotmail.com", "testing", "signin", "200" };
		String[] arr6 = { "mobileappservice_Nx1gqlEPuuAjXAq@hotmail.com", "Nx1gqlEPuuAjXAq", "signin", "200" };
		String[] arr7 = { "mobileappservice_idp100@myntra.com", "123456", "signin", "200" };
		String[] arr8 = { "mobileappservice_idp100@gmail.com", "123456", "signin", "200" };
		//Case Sensitive UserId
		String[] arr9 = {"MOBILEappservicE_Nx1gqlEPuuAjXAq@hotmail.com","Nx1gqlEPuuAjXAq", "signin", "200" };
		//Leading Spaces UserId
		String[] arr10 = {"mobileappservice_101@hotmail.com   ","testing", "signin", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileIdpDP_signUpDataProvider_verifyResponseDataNodesUsingSchemaValidations(ITestContext iTestContext)
	{
		String[] arr1 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr2 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr3 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr4 = { generateRandomId() + "@myntra.com", "123456123", "signup", "200" };
		String[] arr5 = { generateRandomId() + "@rediffmail.com", "123456123", "signup", "200" };
		String[] arr6 = { generateRandomId() + "@gmail.com", "123456123", "signup", "200" };
		String[] arr7 = { generateRandomId() + "@yahoo.com", "123456123", "signup", "200" };
		String[] arr8 = { generateRandomId() + "@rediffmail.com", "123456123", "signup", "200" };
		//Leading, Trailing and middle Spaces Password
		String[] arr9 = {generateRandomId() + "@gmail.com", " 12345  6123 ", "signup", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
}
