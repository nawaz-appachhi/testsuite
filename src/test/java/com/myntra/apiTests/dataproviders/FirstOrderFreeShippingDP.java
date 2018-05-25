
package com.myntra.apiTests.dataproviders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.idpservice.IDPServiceHelper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * Data Provider Class for the FirstOrderFreeShipping Class
 * @author Suhas.Kashyap
 *
 */
public class FirstOrderFreeShippingDP {
	
	static IDPServiceHelper idpServiceHelper = new IDPServiceHelper();
	static Initialize init = new Initialize("/Data/configuration");
	public static String password= "password1";
	public static String signUpSessionID;

	@DataProvider
    public static Object[][] NewuserFreeShipping(ITestContext testContext)
    {
		String uidx= signupAndGetUidx();
        Object[] arr1 = {uidx,password,"free_shipping"};
       
        Object[][] dataSet = new Object[][] { arr1};
        return Toolbox.returnReducedDataSet( dataSet, testContext.getIncludedGroups(), 3, 3);
    }
	
	
	/**
	 * Signs up a new user and returns the UIDX of the Same user
	 * @return UIDX of the User as a String
	 * @author Suhas.Kashyap
	 */
	public static String signupAndGetUidx()
	   
	   { 
		   String loginid= generateRandomString();
	       RequestGenerator signUpReqGen = idpServiceHelper.performSignUpOperation(loginid,password, "C", "M", "Mr", "FirstName", "LastName",loginid, "9898989898");//email
	       System.out.println("\nPrinting IDPService signUp API response :\n\n"+signUpReqGen.respvalidate.returnresponseasstring());
	       signUpSessionID = signUpReqGen.response.getHeaderString("Set-Cookie").split(";")[0].split("=")[1];
			System.out.println("Session ID After SignUp : "+signUpSessionID);
	      MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA,
	    		  APINAME.GETUIDX, init.Configurations, PayloadType.JSON,new String[]{loginid},PayloadType.JSON);
			RequestGenerator requestGenerator = new RequestGenerator(service);
			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
			String uidx = JsonPath.read(jsonRes,"$entry.uidx");
			System.out.println("UIDX------"+ uidx);
			return uidx;
	   }
	
	
	
	public static String generateRandomString()
	   {
	       
	       int leftLimit = 97; // letter 'a'
	       int rightLimit = 122; // letter 'z'
	       int targetStringLength = 5;
	       String conctString="";
	       StringBuilder buffer = new StringBuilder(targetStringLength);
	       List<String> emails=new ArrayList<String>();
	       
	           for (int i = 0; i < targetStringLength; i++) {
	               int randomLimitedInt = leftLimit + (int) 
	               (new Random().nextFloat() * (rightLimit - leftLimit));
	               buffer.append((char) randomLimitedInt);
	               }
	               String generatedString = buffer.toString();
	               conctString=generatedString+"@freeshippingtest.com";
	               
	               System.out.println("Random String  for myntra signup is : "+conctString);
	       
	       
	       return conctString;
	       
	   }
		
}
