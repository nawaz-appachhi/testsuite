package com.myntra.apiTests.portalservices.ideaapi;

import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.idea.response.ProfileResponse;
import com.myntra.lordoftherings.Initialize;

import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


/**
 * @author shankara.c
 *
 */
public class IdeaApiHelper extends CommonUtils
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(IdeaApiHelper.class);
	APIUtilities apiUtil = new APIUtilities();
	
	
	public static HashMap<String, String> getIdeaHeader() {
		HashMap<String, String> createOrderHeaders = new HashMap<>();
		createOrderHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		createOrderHeaders.put("Content-Type", "application/json");
		createOrderHeaders.put("Accept", "application/json");
		return createOrderHeaders;
	}
	
    /**
     * API to Fetch UIDX for a login
     * @param paramAppName
     * @param login
     * @return {@link String}
     * @throws IOException
     */
	public String getUIDXForLogin(String paramAppName, String login) throws IOException {
		Svc service = HttpExecutorService.executeHttpService(Constants.IDEA_PATH.GET_UIDX_DETAILS, new String[] { paramAppName+"/"+login},
                                                             SERVICE_TYPE.IDEA_SVC.toString(), HTTPMethods.GET, null, getIdeaHeader());

        ProfileResponse ideaProfileResponse =(ProfileResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ProfileResponse());
        return ideaProfileResponse.getEntry().getUidx();
	}
	
	public String getUIDXForLoginViaEmail(String paramAppName, String login) throws IOException {
		Svc service = HttpExecutorService.executeHttpService(Constants.IDEA_PATH.GET_UIDX_DETAILS_EMAIL, new String[] { paramAppName+"/"+login},
                                                             SERVICE_TYPE.IDEA_SVC.toString(), HTTPMethods.GET, null, getIdeaHeader());
		
        ProfileResponse ideaProfileResponse =(ProfileResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ProfileResponse());
        return ideaProfileResponse.getEntry().getUidx();
	}
	
	public RequestGenerator invokeIdeaGetProfileByEmail(String paramAppName, String paramEmailId)
	{
		MyntraService ideaGetProfileByEmailIdService = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.IDEAGETUSERPROFILEBYEMAIL, init.Configurations, PayloadType.JSON,
				new String[]{ paramAppName, paramEmailId }, PayloadType.JSON);
		
		System.out.println("\nPrinting IdeaApi getProfileByEmail API URL : "+ideaGetProfileByEmailIdService.URL);
		log.info("\nPrinting IdeaApi getProfileByEmail API URL : "+ideaGetProfileByEmailIdService.URL);
		
		System.out.println("\nPrinting IdeaApi getProfileByEmail API Payload :  \n\n"+ideaGetProfileByEmailIdService.Payload);
		log.info("\nPrinting IdeaApi getProfileByEmail API Payload :  \n\n"+ideaGetProfileByEmailIdService.Payload);
		
		return new RequestGenerator(ideaGetProfileByEmailIdService);
	}
	
	public RequestGenerator invokeIdeaGetProfileByUserId(String paramAppName, String paramUserId)
	{
		MyntraService ideaGetProfileByUserIdService = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.IDEAGETUSERPROFILEBYUSERID, init.Configurations, PayloadType.JSON,
				new String[]{ paramAppName, paramUserId }, PayloadType.JSON);
		
		System.out.println("\nPrinting IdeaApi getProfileByUserId API URL : "+ideaGetProfileByUserIdService.URL);
		log.info("\nPrinting IdeaApi getProfileByUserId API URL : "+ideaGetProfileByUserIdService.URL);
		
		System.out.println("\nPrinting IdeaApi getProfileByUserId API Payload :  \n\n"+ideaGetProfileByUserIdService.Payload);
		log.info("\nPrinting IdeaApi getProfileByUserId API Payload :  \n\n"+ideaGetProfileByUserIdService.Payload);
		
		return new RequestGenerator(ideaGetProfileByUserIdService);
	}
	
	public RequestGenerator invokeIdeaAddEmailToUserProfile(String paramAppName, String paramEmailId, String paramUserId)
	{
		MyntraService ideaAddEmailToUserProfileService = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.IDEAADDEMAILTOUSERPROFILE, init.Configurations,
				new String[]{ paramAppName, paramEmailId, paramUserId } );
		
		System.out.println("\nPrinting IdeaApi addEmailToUserProfile API URL : "+ideaAddEmailToUserProfileService.URL);
		log.info("\nPrinting IdeaApi addEmailToUserProfile API URL : "+ideaAddEmailToUserProfileService.URL);
		
		System.out.println("\nPrinting IdeaApi addEmailToUserProfile API Payload : \n\n"+ideaAddEmailToUserProfileService.Payload);
		log.info("\nPrinting IdeaApi addEmailToUserProfile API Payload : \n\n"+ideaAddEmailToUserProfileService.Payload);
		
		return new RequestGenerator(ideaAddEmailToUserProfileService);
	}
	
	public RequestGenerator invokeIdeaAddPhoneNumberToUserProfile(String paramAppName, String paramPhoneNumber, String paramUserId)
	{
		MyntraService ideaAddPhoneNumberToUserProfileService = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.IDEAADDPHONENUMBERTOUSERPROFILE, init.Configurations,
				new String[]{ paramAppName, paramPhoneNumber, paramUserId } );
		
		System.out.println("\nPrinting IdeaApi addPhoneNumberToUserProfile API URL : "+ideaAddPhoneNumberToUserProfileService.URL);
		log.info("\nPrinting IdeaApi addPhoneNumberToUserProfile API URL : "+ideaAddPhoneNumberToUserProfileService.URL);
		
		System.out.println("\nPrinting IdeaApi addPhoneNumberToUserProfile API Payload :  \n\n"+ideaAddPhoneNumberToUserProfileService.Payload);
		log.info("\nPrinting IdeaApi addPhoneNumberToUserProfile API Payload :  \n\n"+ideaAddPhoneNumberToUserProfileService.Payload);
		
		return new RequestGenerator(ideaAddPhoneNumberToUserProfileService);
	}
	
	public RequestGenerator invokeIdeaSignUp(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, String paramEmailId, 
			String paramPhoneNumber, String paramGender, String paramDateOfBirth)
	{
		MyntraService ideaSignUpService = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.IDEASIGNUP, init.Configurations,
				new String[]{ paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber, paramGender, paramDateOfBirth } );
		
		System.out.println("\nPrinting IdeaApi signUp API URL : "+ideaSignUpService.URL);
		log.info("\nPrinting IdeaApi signUp API URL : "+ideaSignUpService.URL);
		
		System.out.println("\nPrinting IdeaApi signUp API Payload :  \n\n"+ideaSignUpService.Payload);
		log.info("\nPrinting IdeaApi signUp API Payload :  \n\n"+ideaSignUpService.Payload);
		
		return new RequestGenerator(ideaSignUpService);
	}
	
	public RequestGenerator invokeIdeaSignInUsingPhoneNumber(String paramAppName, String paramAccessKey, String paramPhoneNumber)
	{
		MyntraService ideaSignInUsingPhoneNumberService = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.IDEASIGNINUSINGPHONE, init.Configurations,
				new String[]{ paramAppName, paramAccessKey, paramPhoneNumber } );
		
		System.out.println("\nPrinting IdeaApi signInUsingPhoneNumber API URL : "+ideaSignInUsingPhoneNumberService.URL);
		log.info("\nPrinting IdeaApi signInUsingPhoneNumber API URL : "+ideaSignInUsingPhoneNumberService.URL);
		
		System.out.println("\nPrinting IdeaApi signInUsingPhoneNumber API Payload :  \n\n"+ideaSignInUsingPhoneNumberService.Payload);
		log.info("\nPrinting IdeaApi signInUsingPhoneNumber API Payload :  \n\n"+ideaSignInUsingPhoneNumberService.Payload);
		
		return new RequestGenerator(ideaSignInUsingPhoneNumberService);
	}
	
	public RequestGenerator invokeIdeaSignInUsingEmail(String paramAppName, String paramAccessKey, String paramEmail)
	{
		MyntraService ideaSignInUsingEmailService = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.IDEASIGNINUSINGEMAIL, init.Configurations,
				new String[]{ paramAppName, paramAccessKey, paramEmail } );
		
		System.out.println("\nPrinting IdeaApi signInUsingEmail API URL : "+ideaSignInUsingEmailService.URL);
		log.info("\nPrinting IdeaApi signInUsingEmail API URL : "+ideaSignInUsingEmailService.URL);
		
		System.out.println("\nPrinting IdeaApi signInUsingEmail API Payload :  \n\n"+ideaSignInUsingEmailService.Payload);
		log.info("\nPrinting IdeaApi signInUsingEmail API Payload :  \n\n"+ideaSignInUsingEmailService.Payload);
		
		return new RequestGenerator(ideaSignInUsingEmailService);
	}
	
	public RequestGenerator invokeIdeaMobileSignInUsingEmail(String paramAppName, String paramAccessKey, String paramEmail)
	{
		MyntraService ideaMobileSignInUsingEmailService = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.IDEAMOBILESIGNINUSINGEMAIL, init.Configurations,
				new String[]{ paramAppName, paramAccessKey, paramEmail } );
		
		System.out.println("\nPrinting IdeaApi mobileSignInUsingEmail API URL : "+ideaMobileSignInUsingEmailService.URL);
		log.info("\nPrinting IdeaApi mobileSignInUsingEmail API URL : "+ideaMobileSignInUsingEmailService.URL);
		
		System.out.println("\nPrinting IdeaApi mobileSignInUsingEmail API Payload :  \n\n"+ideaMobileSignInUsingEmailService.Payload);
		log.info("\nPrinting IdeaApi mobileSignInUsingEmail API Payload :  \n\n"+ideaMobileSignInUsingEmailService.Payload);
		
		return new RequestGenerator(ideaMobileSignInUsingEmailService);
	}
	
	public RequestGenerator invokeIdeaChangeProfilePassword(String paramAppName, String paramNewAccessKey, String paramAccessKey, String paramUserId)
	{
		MyntraService ideaChangeProfilePasswordService = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.IDEACHANGEPROFILEPASSWORD, init.Configurations,
				new String[]{ paramAppName, paramNewAccessKey, paramAccessKey, paramUserId } );
		
		System.out.println("\nPrinting IdeaApi changeProfilePassword API URL : "+ideaChangeProfilePasswordService.URL);
		log.info("\nPrinting IdeaApi changeProfilePassword API URL : "+ideaChangeProfilePasswordService.URL);
		
		System.out.println("\nPrinting IdeaApi changeProfilePassword API Payload :  \n\n"+ideaChangeProfilePasswordService.Payload);
		log.info("\nPrinting IdeaApi changeProfilePassword API Payload :  \n\n"+ideaChangeProfilePasswordService.Payload);
		
		return new RequestGenerator(ideaChangeProfilePasswordService);
	}
	
	public RequestGenerator invokeIdeaResetPasswordByEmail(String paramAppName, String paramEmailId, String paramResetKey, String paramAccessKey)
	{
		MyntraService ideaResetPasswordByEmailService = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.IDEARESETPASSWORDBYEMAIL, init.Configurations,
				new String[]{ paramAppName, paramEmailId, paramResetKey, paramAccessKey } );
		
		System.out.println("\nPrinting IdeaApi changeProfilePassword API URL : "+ideaResetPasswordByEmailService.URL);
		log.info("\nPrinting IdeaApi changeProfilePassword API URL : "+ideaResetPasswordByEmailService.URL);
		
		System.out.println("\nPrinting IdeaApi changeProfilePassword API Payload :  \n\n"+ideaResetPasswordByEmailService.Payload);
		log.info("\nPrinting IdeaApi changeProfilePassword API Payload :  \n\n"+ideaResetPasswordByEmailService.Payload);
		
		return new RequestGenerator(ideaResetPasswordByEmailService);
	}
	
	public RequestGenerator invokeIdeaPasswordRecoveryByEmail(String paramAppName, String paramEmailId)
	{
		MyntraService ideaPasswordRecoveryByEmailService = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.IDEAPASSWORDRECOVERYBYEMAIL, init.Configurations,
				new String[]{ paramAppName, paramEmailId } );
		
		System.out.println("\nPrinting IdeaApi passwordRecoveryByEmail API URL : "+ideaPasswordRecoveryByEmailService.URL);
		log.info("\nPrinting IdeaApi passwordRecoveryByEmail API URL : "+ideaPasswordRecoveryByEmailService.URL);
		
		System.out.println("\nPrinting IdeaApi passwordRecoveryByEmail API Payload :  \n\n"+ideaPasswordRecoveryByEmailService.Payload);
		log.info("\nPrinting IdeaApi passwordRecoveryByEmail API Payload :  \n\n"+ideaPasswordRecoveryByEmailService.Payload);
		
		return new RequestGenerator(ideaPasswordRecoveryByEmailService);
	}
	
	public RequestGenerator invokeIdeaUpdateProfileInfo(String paramAppName, String paramFirstName, String paramLastName, String paramGender, String paramDateOfBirth, 
			String paramStatus, String paramUserId)
	{
		MyntraService ideaUpdateProfileInfoService = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.IDEAUPDATEPROFILEINFO, init.Configurations,
				new String[]{ paramAppName, paramFirstName, paramLastName, paramGender, paramDateOfBirth, paramStatus, paramUserId } );

		System.out.println("\nPrinting IdeaApi updateProfileInfo API URL : "+ideaUpdateProfileInfoService.URL);
		log.info("\nPrinting IdeaApi updateProfileInfo API URL : "+ideaUpdateProfileInfoService.URL);
		
		System.out.println("\nPrinting IdeaApi updateProfileInfo API Payload :  \n\n"+ideaUpdateProfileInfoService.Payload);
		log.info("\nPrinting IdeaApi updateProfileInfo API Payload :  \n\n"+ideaUpdateProfileInfoService.Payload);
		
		return new RequestGenerator(ideaUpdateProfileInfoService);
	}

	public RequestGenerator invokeIdeaMobileSecureRefresh(String authToken, String refreshToken)
	{
		MyntraService ideaMobileSecureRefreshService = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.IDEAMOBILESECUREREFRESH, init.Configurations,
				new String[]{ authToken, refreshToken } );

		System.out.println("\nPrinting IdeaApi mobileSecureRefresh API URL : "+ideaMobileSecureRefreshService.URL);
		log.info("\nPrinting IdeaApi mobileSecureRefresh API URL : "+ideaMobileSecureRefreshService.URL);
		
		System.out.println("\nPrinting IdeaApi mobileSecureRefresh API Payload :  \n\n"+ideaMobileSecureRefreshService.Payload);
		log.info("\nPrinting IdeaApi mobileSecureRefresh API Payload :  \n\n"+ideaMobileSecureRefreshService.Payload);
		
		return new RequestGenerator(ideaMobileSecureRefreshService);
	}
	
	public String getResetTokenFromDatabase(String emailId, String envName)
	{
		String resetToken = null;
/*
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try 
		{
			connection = DriverManagerDataSource.getDatabaseConnection(envName, "QA_IDEA_DB_CONN");
			preparedStatement = connection.prepareStatement(QueryEnum.IDEA_GET_OTP_TOKEN_QUERY.getQuery());
			preparedStatement.setString(1, emailId);
			preparedStatement.setTimestamp(2, new Timestamp(new Date(Calendar.getInstance().getTimeInMillis()).getTime()));
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				resetToken = resultSet.getString(1);
			}
		} catch (SQLException e) 
		{
			log.error("Error occured to perform database operation",e);
		} finally 
		{
			DriverManagerDataSource.releaseResources(connection, preparedStatement);
		}
*/

		return resetToken;
	}
}
