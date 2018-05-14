package com.automation.core.Common;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class SendMail {
	JavaUtils utils = new JavaUtils();

	// reportFileName = TestExecutionResultFileName
	public void execute() throws Exception {
		String reportFileName = "TestReport.xlsx";
		String path = "./reports/TestReport.xlsx";
		String[] to = { "aravindanath.dm@moolya.com" };
		// String[] cc = { "sudheer@moolya.in", "nagaujjwala@moolya.in",
		// "sachins@moolya.in", "aravindanath.dm@moolya.com" };
		String[] cc = {};
		String[] bcc = {};
		// emailSummaryReport("system_auto@moolya.in", "system_auto", "smtp.gmail.com",
		// "465", "true", "true", true,
		// "javax.net.ssl.SSLSocketFactory", "false", to, cc, bcc, "GV Automation
		// Execution Result",
		// "HI, PFB Execution report for GV end to end flow.", path, reportFileName);
	}

	@Test
	public void emailSummaryReport() {
		try {
			String excelReportFilePath = "./reports/TestReport.xlsx";
			// String[] to = {
			// "aravindanath.dm@moolya.com","idfc-assets-qa@moolya.in","sachins@moolya.in","nitin@moolya.in
			// "};
			String[] to = { "aravindanath.dm@moolya.com" };
			String subject = " GV Automation Test Execution Status :  " + getBuildVersion();
			String bodyText = "Hi All," + System.lineSeparator() + System.lineSeparator()
					+ "PFA GV Automation Execution report." + '\n' + '\n' + "Regards, moolya Automation Team"
					+ System.lineSeparator() + System.lineSeparator()
					+ "---This is an auto-generated email, Please do not reply to this.<br/><br/><br/>"
					+ utils.report();
			System.out.println(bodyText);
			Properties properties = System.getProperties();
			properties.setProperty("mail.transport.protocol", "smtp");
			properties.setProperty("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			final String from = "system_auto@moolya.in";
			final String fromPass = "system_auto";
			Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, fromPass);
				}
			});
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			for (String str : to) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(str));
			}
			message.setSubject(subject);
			Multipart multipart = new MimeMultipart();
			MimeBodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(bodyText, "text/html");
			multipart.addBodyPart(messageBodyPart1);
			MimeBodyPart messageBodyPart3 = new MimeBodyPart();
			DataSource source1 = new FileDataSource(excelReportFilePath);
			messageBodyPart3.setDataHandler(new DataHandler(source1));
			messageBodyPart3.setFileName("TestReport.xlsx");
			multipart.addBodyPart(messageBodyPart3);
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Mail sent...");
		} catch (MessagingException ex) {
			ex.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getBuildVersion() throws InvalidFileFormatException, IOException {
		Reporter.log("reading the build version details from a .ini file", true);
		Ini ini = new Ini(new File("./test-data/test-data.ini"));
		String BUILD = ini.get("BuildVersion", "BUILD");
		System.err.println("BUILD Version-->" + BUILD);
		return BUILD;
	}
}