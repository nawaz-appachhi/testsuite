package com.myntra.apiTests.portalservices.lgpServicesOnDemand;

import java.io.IOException;
import java.util.HashMap;

import com.myntra.apiTests.portalservices.lgpServe.feed.FeedObjectHelper;
import com.myntra.apiTests.portalservices.lgpservices.HotListTests;
import com.myntra.apiTests.portalservices.lgpservices.LgpServicesHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;

public class CardsCreationTests {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(HotListTests.class);
	static APIUtilities apiUtil = new APIUtilities();
	static APIUtilities utilities = new APIUtilities();
	static FeedObjectHelper feedHelper= new FeedObjectHelper();
	static LgpServicesHelper lgpServiceHelper=new LgpServicesHelper();
	static String username,uidx,password;
	static HashMap<?, ?> userDetails;
	static String mode,cardType,cardId_whom;
	static HashMap<String, String> headers =new HashMap<>();
	
	static String xid;
	public static String versionSpecification;
	
	@BeforeClass(alwaysRun=true)
	public void getUidx()
	{
		username=System.getenv("username");
		password=System.getenv("password");
		mode=System.getenv("mode");
		cardType=System.getenv("cardType");
		cardId_whom=System.getenv("cardId");
		if(null==username && init.Configurations.GetTestEnvironemnt().toString().toLowerCase().contains("fox7"))
		{
//			username="iosapp@myntra.com";
//			password="qwerty";
			uidx="a5b3c136.0f59.431c.bb3d.4f70472356ef6HJtdiMgcl";
		}
		else if(null==username && init.TestEnvironment.toString().toLowerCase().contains("production"))
		{
			username="iosapp10@myntra.com";
			password="qwerty";
		}
		else{
	 	  try {
			userDetails = lgpServiceHelper.getXidandUidxForCredential(username, password);
			uidx=(String) userDetails.get("uidx");
	 	      } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		     }
		}
		
		if(null==cardType)
		{
			cardType="article"; //By default cardType will be article.
		}
		if(null==mode)
		{
			mode="CREATEPUBLISHDELETE"; //By Default it creates,publishes and deletes the object
		}
		
		if(init.Configurations.GetTestEnvironemnt().toString().toLowerCase().contains("fox7"))
		{
			headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
			headers.put("Accept","application/json");
			headers.put("Content-Type","application/json");
		}
		else
		{
			headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
			headers.put("Accept","application/json");
			headers.put("Content-Type","application/json");
		}
		
	}

	
	@Test (groups ="Sanity")
	public void CardCreationUtility()
	{
		if(mode.equals("CREATEPUBLISH") || mode.equals("CREATEPUBLISHDELETE"))
		{
		  HashMap<String, String> card_details= feedHelper.createandGetCardCreationDetails(cardType,uidx,headers);
		  System.out.println(card_details);
		  String whom = card_details.get("appId")+":"+card_details.get("refId");
		  System.out.println(whom+" created successfully");
		  Assert.assertEquals("200", card_details.get("responseCode"),"The response code is not as expected");
		  HashMap<String, String> publish_details=feedHelper.publishandGetResponseDetails(whom,headers);
		  System.out.println(publish_details);
		  System.out.println(whom+" published successfully");
		  Assert.assertEquals("200", publish_details.get("responseCode"),"The response code is not as expected");
		  if(mode.equals("CREATEPUBLISHDELETE"))
		  {
		   HashMap<String, String> delete_details=feedHelper.deleteandGetResponseDetails(whom,headers);
		   System.out.println(delete_details);
		   System.out.println(whom+" deleted successfully");
		   Assert.assertEquals("200", delete_details.get("responseCode"),"The response code is not as expected");
		  }
		  
		}
		if(mode.equals("DELETE"))
		{
		  HashMap<String, String> delete_details=feedHelper.deleteandGetResponseDetails(cardId_whom,headers);
	      System.out.println(delete_details);
		}	
 	}
	
}
