/**
 * 
 */
package com.myntra.apiTests.erpservices.security.Tests;

import org.testng.annotations.Test;

import com.myntra.lordoftherings.ManualTest;

/**
 * @author santwana.samantray
 *
 */
public class SecurityServiceTest {

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "Login with existing password, Expected :1.User should be allowed to login with existing pw")
	public void login_with_existing_pw() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "Change pw and logout and login with new pw, Expected :1.User shoould be allowed to login with new pw(changed pw)")
	public void change_pw() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "click on forget pw and login with the new pw, Expected :1.new  pw should be sent via mail 2.user should be able to login with that pw")
	public void forgetpw() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "removeroles, Expected :1.User should be allowed to see only those pages for which the access is provided")
	public void removeroles() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "assignroles, Expected :1.User should be allowed to see only those pages for which the access is provided")
	public void assignroles() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "assinrolesasadmin, Expected :1.User should be allowed to see only those pages for which the access is provided")
	public void assinrolesasadmin() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "newuserwithoutanyrole, Expected :1.User should be allowed to login without seeing any screen")
	public void newuserwithoutanyrole() {
		
	}

	
	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "newUserProvidedWithRoles, Expected :1.User should be allowed to login with existing pw and should be allowed to see only those pages for which the access is provided")
	public void newUserProvidedWithRoles() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "loginWithDisablesUser, Expected :1.User should not be allowed to login")
	public void loginWithDisablesUser() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "DisableaUser, Expected :1.User should be marked disabled in the db")
	public void DisableaUser() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "EnableUser, Expected :1.User should be enabled in the db")
	public void EnableUser() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "EnableDisabledUserLogin, Expected :1.User should be allowed to login with existing pw")
	public void EnableDisabledUserLogin() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "LoginAsLockedUser, Expected :1.User shouldnot be allowed to login as asked to login later")
	public void LoginAsLockedUSer() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "LoginwithWrongpw, Expected :1.User should be not be allowed to login")
	public void LoginwithWrongpw() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "LoginwithWrongpwfor20timeUserLocked, Expected :1.User should be locked")
	public void LoginwithWrongpwfor20timeUserLocked() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "forceresetpw, Expected :1.Users pw should be updated")
	public void forceresetpw() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "changeUsernameAsAdmin, Expected :1.User name should be updated for user")
	public void changeUsernameAsAdmin() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "changeLoginAsAdmin, Expected :1.Login should be updated for user")
	public void changeLoginAsAdmin() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "createUserWithExistingUsername, Expected :1.User should not be created")
	public void createUserWithExistingUsername() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "createUserWithUsernamehavingSpecialChar, Expected :1.User should not be created")
	public void createUserWithUsernamehavingSpecialChar() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "createUserWithExistingEmail, Expected :1.User should not be created")
	public void createUserWithExistingEmail() {
		
	}

	@ManualTest   @Test(groups = { "Smoke", "Regression" }, description = "createUserWithInvalidEmailformat, Expected :1.User should not be created")
	public void createUserWithInvalidEmailformat() {
		
	}
}
