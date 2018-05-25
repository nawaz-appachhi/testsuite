package com.myntra.apiTests.erpservices.taxMaster;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.client.tools.response.ApplicationPropertiesResponse;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.taxmaster.client.entry.request.CustomerVatRequestEntry;
import com.myntra.taxmaster.client.response.TaxResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by 16553 on 26/08/16.
 */
public class VatRuleEngineHelper {

    public static CustomerVatRequestEntry createRequestObject(Long ArticleId, String CourierCode, Date date, String DestinationCode, String Material, Double MRP, String SourceCode) {
        CustomerVatRequestEntry newObject = new CustomerVatRequestEntry();
        newObject.setArticleId(ArticleId);
        newObject.setCourierCode(CourierCode);
        newObject.setDate(date);
        newObject.setDestinationStateCode(DestinationCode);
        newObject.setMaterial(Material);
        newObject.setMrpValue(MRP);
        newObject.setSourceStateCode(SourceCode);
        return newObject;
    }

    public Double getGovtTaxRate(Long ArticleId, String CourierCode, Date date, String DestinationCode, String Material, Double MRP, String SourceCode) throws IOException, JAXBException {
        CustomerVatRequestEntry cvr = createRequestObject(ArticleId, CourierCode, date, DestinationCode, Material, MRP, SourceCode);
        String payload = APIUtilities.convertXMLObjectToString(cvr);
        Svc service = HttpExecutorService.executeHttpService(Constants.TAXMASTER_PATH.GET_TAX_VAT, null, SERVICE_TYPE.TAXMASTER_SVC.toString(), HTTPMethods.POST, payload, getTaxMasterHeaderXML());
        TaxResponse response = (TaxResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new TaxResponse());
        return (response.getData().iterator().next().getTaxRate());
    }

    public HashMap<String, String> getTaxMasterHeaderXML(){
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Authorization", "Basic bG1zYWRtaW5+bG1zYWRtaW46dGVzdA==");
        createOrderHeaders.put("Content-Type", "Application/xml");
        createOrderHeaders.put("Accept", "application/xml");
        return createOrderHeaders;
    }

    public static ApplicationPropertiesResponse getApplicationPropertyValue(String[] pathParams) throws UnsupportedEncodingException, JAXBException {

        Svc service = HttpExecutorService.executeHttpService(Constants.TOOLS_PATH.QUERYAPPLICATIONPROPERTY, pathParams, SERVICE_TYPE.TOOLS_SVC.toString(), HTTPMethods.GET, null, End2EndHelper.getXMLHeader());
        ApplicationPropertiesResponse queryResult = (ApplicationPropertiesResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ApplicationPropertiesResponse());
        String[] maxArticleids = queryResult.getData().get(0).getValue().split(",");
        return queryResult;
    }

    }
