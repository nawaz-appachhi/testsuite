package com.myntra.apiTests.portalservices.knight.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.knight.cod.client.response.SetCODResponse;
import com.myntra.knight.config.response.KnightKeyValuePairSetRequest;
import com.myntra.knight.config.response.KnightKeyValuePairSetResponse;
import com.myntra.knight.domain.Knight;
import com.myntra.knight.domain.entities.KnightKeyValuePairEntry;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;

/**
 * Created by abhijit.pati on 19/06/16.
 */
public class KnightServiceHelper{

    static Initialize init = new Initialize("/Data/configuration");
    /**
     * Add Email to Fraud List
     * @param emailId
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public boolean addEmailToFraudList(String emailId) throws IOException, JAXBException {
        KnightKeyValuePairSetRequest knightKeyValuePairSetRequest = new KnightKeyValuePairSetRequest(Knight.BLOCKEDLOGIN);
        knightKeyValuePairSetRequest.setTotalNumberOfKeys(1);
        List<KnightKeyValuePairEntry> loginKeys = new ArrayList();
        KnightKeyValuePairEntry knightKeyValuePairEntry = new KnightKeyValuePairEntry();
        knightKeyValuePairEntry.setKey(emailId);
        knightKeyValuePairEntry.setValue("true");
        loginKeys.add(knightKeyValuePairEntry);
        knightKeyValuePairSetRequest.setKeys(loginKeys);
        String payload = APIUtilities.getObjectToJSON(knightKeyValuePairSetRequest);
        Svc svc = HttpExecutorService.executeHttpService(Constants.KNIGHT_PATH.BLOCK_USER, null, SERVICE_TYPE.KNIGHT_SVC.toString(), HTTPMethods.PUT, payload, getKnightHeader());
        KnightKeyValuePairSetResponse knightKeyValuePairSetResponse = (KnightKeyValuePairSetResponse) APIUtilities.getJsontoObject(svc.getResponseBody(), new KnightKeyValuePairSetResponse());
        if(knightKeyValuePairSetResponse.getSuccessfulUpdates()==1){
            return true;
        }else {return false;}
    }

    /**
     * Block Mobile For Fraud User
     * @param mobile
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public boolean addMobileToFraudList(String mobile) throws IOException, JAXBException {
        KnightKeyValuePairSetRequest knightKeyValuePairSetRequest = new KnightKeyValuePairSetRequest(Knight.BLOCKEDMOBILE);
        knightKeyValuePairSetRequest.setTotalNumberOfKeys(1);
        List<KnightKeyValuePairEntry> loginKeys = new ArrayList();
        KnightKeyValuePairEntry knightKeyValuePairEntry = new KnightKeyValuePairEntry();
        knightKeyValuePairEntry.setKey(mobile);
        knightKeyValuePairEntry.setValue("true");
        loginKeys.add(knightKeyValuePairEntry);
        knightKeyValuePairSetRequest.setKeys(loginKeys);
        String payload = APIUtilities.getObjectToJSON(knightKeyValuePairSetRequest);
        Svc svc = HttpExecutorService.executeHttpService(Constants.KNIGHT_PATH.BLOCK_USER, null, SERVICE_TYPE.KNIGHT_SVC.toString(), HTTPMethods.PUT, payload, getKnightHeader());
        KnightKeyValuePairSetResponse knightKeyValuePairSetResponse = (KnightKeyValuePairSetResponse) APIUtilities.getJsontoObject(svc.getResponseBody(), new KnightKeyValuePairSetResponse());
        if(knightKeyValuePairSetResponse.getSuccessfulUpdates()==1){
            return true;
        }else {return false;}
    }

    /**
     * Set COD Out standing Limit
     * @param login
     * @param outStandingCODLimit
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public RequestGenerator setOutstandingCODLimit(String login, String outStandingCODLimit) throws IOException, JAXBException {

        MyntraService outstandingLimit = Myntra.getService(ServiceType.PORTAL_KNIGHTSERVICE, APINAME.SETCODLIMIT, init.Configurations,
                                                        PayloadType.JSON, new String[] { login,outStandingCODLimit }, PayloadType.JSON);
        RequestGenerator outstandingLimitRes = new RequestGenerator(outstandingLimit, getKnightHeader());
        return outstandingLimitRes;
    }

    public boolean setOutstandingCODLimitForLogin(String login, String outStandingCODLimit) throws IOException, JAXBException {
        MyntraService outstandingLimit = Myntra.getService(ServiceType.PORTAL_KNIGHTSERVICE, APINAME.SETCODLIMIT, init.Configurations,
                                                           new String[] { login,outStandingCODLimit },PayloadType.JSON, PayloadType.JSON);
        RequestGenerator outstandingLimitRes = new RequestGenerator(outstandingLimit, getKnightHeader());
        System.out.println("Out Standing COD Limit Response "+outstandingLimitRes.respvalidate.returnresponseasstring());
        SetCODResponse outstandingCODResponse =(SetCODResponse) APIUtilities.getJsontoObject(outstandingLimitRes.respvalidate.returnresponseasstring(), new SetCODResponse());
        if(outstandingCODResponse.getNoOfSuccessUpdates() == 1){
            return true;
        }else{
            return false;
        }
    }


    /**
     * Get
     * @return
     */
    private static HashMap<String, String> getKnightHeader() {
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Content-Type", "Application/json");
        createOrderHeaders.put("Accept", "Application/json");
        return createOrderHeaders;
    }

}
