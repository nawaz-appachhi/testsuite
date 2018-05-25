package com.myntra.apiTests.erpservices.utility;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.idea.response.ProfileResponse;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.testbase.BaseTest;
import org.codehaus.jettison.json.JSONException;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by abhijit.pati on 09/06/16.
 */
public class UpdateLPAndCB extends BaseTest{
    
	@Test
    public void updateLPAndCB() throws IOException, NumberFormatException, SQLException, JAXBException, JSONException {

        String login = System.getenv("Login");
        String loyaltyPoint = System.getenv("CashBack");
        String cashBack = System.getenv("LoyaltyPoints");
        
        if(login !=null && loyaltyPoint != null && cashBack != null){
        	System.out.println("Login, Loyalty point and Cashback is Null");
        }
        
        End2EndHelper end2EndHelper = new End2EndHelper();
        IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
        RequestGenerator requestGenerator = ideaApiHelper.invokeIdeaGetProfileByEmail("myntra", login);
        System.out.println(requestGenerator.returnresponseasstring());
        ProfileResponse profileResponse =(ProfileResponse) APIUtilities.getJsontoObject(requestGenerator.returnresponseasstring(), new ProfileResponse());
        String uidx = profileResponse.getEntry().getUidx();
        System.out.println("UIDX "+ uidx);
        end2EndHelper.updateloyalityAndCashBack(uidx, Integer.parseInt(loyaltyPoint), Integer.parseInt(loyaltyPoint));
        System.out.println("CashBack - " + end2EndHelper.getCashBackPoints(uidx));
        System.out.println("CashBack - " + end2EndHelper.getLoyaltyPoints(uidx));
    }
}
