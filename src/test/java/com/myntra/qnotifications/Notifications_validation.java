package com.myntra.qnotifications;

import com.myntra.lordoftherings.parser.emailParser.Email;
import com.myntra.lordoftherings.parser.emailParser.ParseEmail;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

import javax.mail.MessagingException;

/**
 * Created by 16553 on 01/09/16.
 */
public class Notifications_validation {

    static Email email = new Email();
    static ParseEmail parse = new ParseEmail();
    static String placeholderVariable="$";
    static org.slf4j.Logger log = LoggerFactory.getLogger(Notifications_validation.class);

    public static void login_delete_emails(String username, String pwd) throws MessagingException{
        email.login(username,pwd);
        email.deleteAllMailsFromInbox();
    }

    public static void verify_emails_for_placeholders(String username, String pwd) throws MessagingException {
        email.login(username, pwd);
        parse.verifyContent(email, placeholderVariable);
    }
    public static void verify_emails_for_placeholders(String username, String pwd,String placeholderVariable, boolean expected_or_not) throws MessagingException {
        email.login(username, pwd);
        SoftAssert sft = new SoftAssert();
        boolean result =parse.verifyContent(email, placeholderVariable);
        sft.assertEquals(result,expected_or_not,"email check for " + placeholderVariable +" is " + result);
        log.info("email check for " + placeholderVariable +" is " + result);
        sft.assertAll();

    }

    public static void login_retrieveText(String username, String pwd) throws MessagingException{
        email.login(username,pwd);
        parse.searchContent(email,"//p[3]");
    }

}
