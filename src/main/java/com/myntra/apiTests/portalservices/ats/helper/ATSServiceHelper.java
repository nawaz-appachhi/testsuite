package com.myntra.apiTests.portalservices.ats.helper;

import com.myntra.apiTests.erpservices.Constants;
import com.myntra.lordoftherings.Initialize;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by abhijit.pati on 18/06/16.
 */
public class ATSServiceHelper {


    static Initialize init = new Initialize("/Data/configuration");

    /**
     * Add Email to Fraud List
     * @param emailId
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public boolean addEmailToFraudList(String emailId) throws IOException, JAXBException {
        String payload = "{\"keys\" : [{\"key\":\""+emailId+"\",\"value\":\"true\"}],\"totalNumberOfKeys\" : 1,\"namespace\" : \"blockedLogin\"}";
        Svc svc = HttpExecutorService.executeHttpService(Constants.ATS_PATH.BLOCK_USER, null, SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.PUT, payload, getATSHeader());
        if(svc.getResponseStatus()==200)
            return true;
        else
            return false;
    }

    /**
     *
     * @param login
     * @param outStandingCODLimit
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public boolean setOutstandingCODLimitForLogin(String login, String outStandingCODLimit) throws IOException, JAXBException {
        String payload = "{\"userid\":\""+login+"\",\"minCODLimit\":\"1\",\"maxCODLimit\":\""+outStandingCODLimit+"\"}";
        Svc svc = HttpExecutorService.executeHttpService(Constants.ATS_PATH.SET_COD_LIMIT, null, SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.PUT, payload, getATSHeader());
        if(svc.getResponseStatus()==200)
            return true;
        else
            return false;

    }

    private static HashMap<String, String> getATSHeader() {
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Content-Type", "Application/json");
        createOrderHeaders.put("Accept", "Application/json");
        return createOrderHeaders;
    }

}
