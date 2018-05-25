package com.myntra.apiTests.portalservices.idpservice;

import com.myntra.apiTests.common.Myntra;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import org.apache.log4j.Logger;

/**
 * @author shankara.c
 *
 */
public class IDPServiceHelper
{
	
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(IDPServiceHelper.class);
	
	/**
	 * This method is used to invoke IDPService SignIn API
	 * 
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator performSignInOperation(String userName, String password)
	{
		MyntraService signInService = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.SIGNIN, init.Configurations, new String[]{ userName, password });
	    
	    System.out.println("\nPrinting IDPService SignIn API URL : "+signInService.URL);
		log.info("\nPrinting IDPService SignIn API URL : "+signInService.URL);
		
		System.out.println("\nPrinting IDPService SignIn API Payload : \n\n"+signInService.Payload);
		log.info("\nPrinting IDPService SignIn API Payload : \n\n"+signInService.Payload);
		
		return new RequestGenerator(signInService);
	}
	
	/**
	 * This method is used to invoke IDPService SignUp API
	 *
	 * @param login
	 * @param password
	 * @param userType
	 * @param gender
	 * @param title
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param mobileNumber
	 * @return RequestGenerator
	 */
	public RequestGenerator performSignUpOperation(String login, String password, String userType, String gender, String title, String firstName, String lastName, 
			String emailId, String mobileNumber)
	{
		MyntraService signUpService = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.SIGNUP, init.Configurations,
				new String[]{ login, password, userType, gender, title, firstName, lastName, emailId, mobileNumber });
	    
	    System.out.println("\nPrinting IDPService SignUp API URL : "+signUpService.URL);
		log.info("\nPrinting IDPService SignUp API URL : "+signUpService.URL);
		
		System.out.println("\nPrinting IDPService SignUp API Payload : \n\n"+signUpService.Payload);
		log.info("\nPrinting IDPService SignUp API Payload : \n\n"+signUpService.Payload);
		
		return new RequestGenerator(signUpService);
		
	}
	
	/**
	 * This method is used to invoke IDPService SignOut API
	 * 
	 * @param userName
	 * @param signInReqGen
	 * @return RequestGenerator
	 */
	public RequestGenerator performSignOutOperation(String userName, RequestGenerator signInReqGen)
	{
		MyntraService signOutService = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.SIGNOUT, init.Configurations, new String[]{ userName });
		String token = "X-CSRF-TOKEN="+signInReqGen.respvalidate.NodeValue("xsrfToken", false).replace("\"", "");
		signOutService.URL = signOutService.URL+"?"+token;
		
	    System.out.println("\nPrinting IDPService SignOut API URL : "+signOutService.URL);
		log.info("\nPrinting IDPService SignOut API URL : "+signOutService.URL);
		
		System.out.println("\nPrinting IDP Service SignOut API Payload : \n\n"+signOutService.Payload);
		log.info("\nPrinting IDP Service SignOut API Payload : \n\n"+signOutService.Payload);
		
		return new RequestGenerator(signOutService);
	}
	
	/**
	 * This method is used to invoke IDPService ForgotPassword API
	 * 
	 * @param login
	 * @return RequestGenerator
	 */
	public RequestGenerator performForgotPasswordOperation(String login)
	{
		MyntraService forgotPasswordService = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.FORGOTPASSWORD, init.Configurations, new String[]{ login });
				
	    System.out.println("\nPrinting IDPService ForgotPassword API URL : "+forgotPasswordService.URL);
		log.info("\nPrinting IDPService ForgotPassword API URL : "+forgotPasswordService.URL);
		
		System.out.println("\nPrinting IDPService ForgotPassword API Payload : \n\n"+forgotPasswordService.Payload);
		log.info("\nPrinting IDPService ForgotPassword API Payload : \n\n"+forgotPasswordService.Payload);
		
		return new RequestGenerator(forgotPasswordService);
	}
	
	/**
	 * This method is used to invoke IDPService ResetPassword API
	 * 
	 * @param login
	 * @param password
	 * @param resetToken
	 * @return RequestGenerator
	 */
	public RequestGenerator performResetPasswordOperation(String login, String password, String resetToken)
	{
		MyntraService resetPasswordService = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.RESETPASSWORD, init.Configurations, new String[]{ login, password, resetToken });
		//String token = "X-CSRF-TOKEN="+forgotPasswordReqGen.respvalidate.NodeValue("xsrfToken", false).replace("\"", "");
		//resetPasswordService.URL = resetPasswordService.URL+"?"+token;
		
	    System.out.println("\nPrinting IDPService ResetPassword API URL : "+resetPasswordService.URL);
		log.info("\nPrinting IDPService ResetPassword API URL : "+resetPasswordService.URL);
		
		System.out.println("\nPrinting IDPService ResetPassword API Payload : \n\n"+resetPasswordService.Payload);
		log.info("\nPrinting IDPService ResetPassword API Payload : \n\n"+resetPasswordService.Payload);
		
		return new RequestGenerator(resetPasswordService);
	}
	
	/**
	 * This method is used to invoke IDPService FaceBookLogin API
	 * 
	 * @param accessToken
	 * @param expiresIn
	 * @param code
	 * @return RequestGenerator
	 */
	public RequestGenerator performFaceBookLoginOperation(String accessToken, String expiresIn, String code)
	{
		MyntraService faceBookLoginService = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.FBLOGIN, init.Configurations, new String[]{ accessToken, expiresIn, code });
		
	    System.out.println("\nPrinting IDPService FaceBookLogin API URL : "+faceBookLoginService.URL);
		log.info("\nPrinting IDPService FaceBookLogin API URL : "+faceBookLoginService.URL);
		
		System.out.println("\nPrinting IDPService FaceBookLogin API Payload : \n\n"+faceBookLoginService.Payload);
		log.info("\nPrinting IDPService FaceBookLogin API Payload : \n\n"+faceBookLoginService.Payload);
		
		return new RequestGenerator(faceBookLoginService);
	}
	
	/**
	 * This method is used to get the reset token from the database
	 * 
	 * @param login
	 * @param envName
	 * @return resetToken
	 */
	public String getResetTokenFromDatabase(String login, String envName)
	{
		String resetToken = null;
/*
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try 
		{
			connection = DriverManagerDataSource.getDatabaseConnection(envName);
			preparedStatement = connection.prepareStatement(QueryEnum.GET_RP_RESET_TOKEN.getQuery());
			preparedStatement.setString(1, login);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) resetToken = resultSet.getString(1);
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
	
	
	/**
	 * This method is used to get the access token from the database
	 * 
	 * @param fbLoginId
	 * @param envName
	 * @return fbAccessToken
	 */
	public String getFacebookLoginAccessTokenFromDatabase(String fbLoginId, String envName)
	{
		String fbAccessToken = null;
/*
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try 
		{
			connection = DriverManagerDataSource.getDatabaseConnection(envName);
			preparedStatement = connection.prepareStatement(QueryEnum.GET_FBLOGIN_ACCESS_TOKEN.getQuery());
			preparedStatement.setString(1, fbLoginId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) fbAccessToken = resultSet.getString(1);
		} catch (SQLException e) 
		{
			log.error("Error occured to perform database operation",e);
		} finally 
		{
			DriverManagerDataSource.releaseResources(connection, preparedStatement);
		}
*/

		return fbAccessToken;
	}
	
}
