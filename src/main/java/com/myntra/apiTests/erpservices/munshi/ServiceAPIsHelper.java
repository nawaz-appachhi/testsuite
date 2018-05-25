package com.myntra.apiTests.erpservices.munshi;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.munshi.domain.entry.DocumentRequestEntry;
import com.myntra.munshi.domain.response.DocumentDetailsResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by 16553 on 18/05/17.
 */
public class ServiceAPIsHelper {

    public static DocumentDetailsResponse generateInvoiceInfoAPI() throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.MUNSHI_PATH.GENERATE_INFO, new String[] { "" }, SERVICE_TYPE.MUNSHI_SVC.toString(), HTTPMethods.GET, null, OMSServiceHelper.getOMSHeader());
        String xmlResponse = service.getResponseBody();
        DocumentDetailsResponse documentDetailsResponse =  (DocumentDetailsResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new DocumentDetailsResponse());
        return documentDetailsResponse;
    }

   /* public static Response generateDocumentAPI() throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.MUNSHI_PATH.GENERATE_DOCUMENT, new String[] { "" }, SERVICE_TYPE.MUNSHI_SVC.toString(), HTTPMethods.GET, null, OMSServiceHelper.getOMSHeader());
        String xmlResponse = service.getResponseBody();
        Response documentResponse =  (Response) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new Response());
        return documentResponse;
    }

    public static DocumentDetailsResponse generateDocumentWithInfoAPI() throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.MUNSHI_PATH.GENERATE_DOCUMENT_WITH_INFO, new String[] { "" }, SERVICE_TYPE.MUNSHI_SVC.toString(), HTTPMethods.GET, null, OMSServiceHelper.getOMSHeader());
        String xmlResponse = service.getResponseBody();
        DocumentDetailsResponse documentDetailsResponse =  (DocumentDetailsResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new DocumentDetailsResponse());
        return documentDetailsResponse;
    }

    public static Response fetchUsingPartnerIdsAPI() throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.MUNSHI_PATH.FETCH_USING_PARTNER_IDS, new String[] { "" }, SERVICE_TYPE.MUNSHI_SVC.toString(), HTTPMethods.GET, null, OMSServiceHelper.getOMSHeader());
        String xmlResponse = service.getResponseBody();
        Response documentResponse =  (Response) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new Response());
        return documentResponse;
    }

    public static Response fetchUsingDocumentIds() throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.MUNSHI_PATH.FETCH_USING_DOCUMENT_IDS, new String[] { "" }, SERVICE_TYPE.MUNSHI_SVC.toString(), HTTPMethods.GET, null, OMSServiceHelper.getOMSHeader());
        String xmlResponse = service.getResponseBody();
        Response documentResponse =  (Response) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new Response());
        return documentResponse;
    }*/

    public DocumentRequestEntry setDocumentRequestEntry(String entityId, String documnetName, String entityKey, List<String> DocumentIds, List<String> PartnerIds){
        DocumentRequestEntry docEntry=new DocumentRequestEntry ();
        docEntry.setEntityId(entityId);
        docEntry.setDocumentName(documnetName);
        docEntry.setEntityKey(entityKey);
        docEntry.setDocumentIds(DocumentIds);
        docEntry.setPartnerIds(PartnerIds);
        return null;
    }

}
