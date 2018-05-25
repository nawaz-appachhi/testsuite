package com.myntra.apiTests.erpservices.oms;


import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.munshi.domain.documents.response.StickerInvoiceResponseEntry;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.test.commons.service.Svc;
import org.apache.cxf.helpers.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.testng.Assert;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.HashMap;
import java.util.List;

/*import com.google.gdata.data.spreadsheet.CustomElementCollection;
>>>>>>> 46eea404572fc728f3546312333eafc33ad71761
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.util.ServiceException;*/

/**
 * Created by 16553 on 14/03/17.
 */
public class InvoiceServiceHelper {

    private static StickerInvoiceResponseEntry releaseDocumentResponse;
   /* static GoogleSheetsApiTest sheetAPI = new GoogleSheetsApiTest();
    private static final String CLIENT_ID = "myntra@myntra-148806.iam.gserviceaccount.com";
    private static final String filePath = System.getProperty("user.dir")+"/Myntra-taxmaster.p12";
    private static final String spreadsheeetTitle="InvoiceTestScenarios";
    private static final String worksheetName="TestScenariosGST";
   */ 
    private static Logger log = Logger.getLogger(InvoiceServiceHelper.class);
    
    public enum DocumentName{
    	physicalCustomerInvoice,
    	customerInvoice,
    	creditNote,
    	financeInvoice,
    	bTobCreditNote,
    	returnCreditNote,
    	shippingLabel,
    	shippingLabelOms
    	
    }
   
   /* 
    public static ReleaseDocumentResponse getreleaseDocumentResponse(Long orderReleaseID) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.INVOICE_SERVICE, new String[] { ""+orderReleaseID }, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, OMSServiceHelper.getOMSHeader());
        String xmlResponse = service.getResponseBody();
       // StickerInvoiceEntry stickerInvoiceEntry=(StickerInvoiceEntry)APIUtilities.convertXMLStringToObject(service.getResponseBody(), new StickerInvoiceEntry());
       ReleaseDocumentResponse releaseDocumentResponse =  (ReleaseDocumentResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ReleaseDocumentResponse());
        return releaseDocumentResponse;
    }*/
    
    public static StickerInvoiceResponseEntry getreleaseDocumentResponse(String orderReleaseID) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.MUNSHI_PATH.B2C_CustomerInvoiceEntry, new String[] { ""+orderReleaseID }, SERVICE_TYPE.MUNSHI_SVC.toString(), HTTPMethods.GET, null, OMSServiceHelper.getOMSHeader());
        String xmlResponse = service.getResponseBody();
        StickerInvoiceResponseEntry releaseDocumentResponse =  (StickerInvoiceResponseEntry) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new StickerInvoiceResponseEntry());
        return releaseDocumentResponse;
    }
    
    public static void getCustomerInvoicePDF(Long orderReleaseID) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.MUNSHI_PATH.B2C_CustomerInvoicePDF, new String[] { ""+orderReleaseID }, SERVICE_TYPE.MUNSHI_SVC.toString(), HTTPMethods.GET, null, OMSServiceHelper.getOMSHeader());
        String xmlResponse = service.getResponseBody();
        StickerInvoiceResponseEntry releaseDocumentResponse =  (StickerInvoiceResponseEntry) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new StickerInvoiceResponseEntry());
        //pomreturn releaseDocumentResponse;
    }



    /*public static List<HashMap<String, String>> getDataFromExcel() throws ServiceException, GeneralSecurityException, URISyntaxException, IOException {
        HashMap<String,String> dataMap;
        List<HashMap<String,String>> dataList = new LinkedList<HashMap<String,String>>();
        List<ListEntry> listfeed = sheetAPI.testConnectToSpreadSheet(CLIENT_ID,filePath,spreadsheeetTitle,worksheetName);
        for (ListEntry listentry : listfeed) {
            CustomElementCollection cec = listentry.getCustomElements();
            dataMap=new HashMap<String,String>();
            for(String keys:cec.getTags()) {
                String key_name = keys.toString();
                dataMap.put(key_name,cec.getValue(key_name));
                }
            dataList.add(dataMap);
            }
            log.info(dataList);
        return dataList;

    }*/
    
    public void addInvoiceToFolder(String releaseId,String folderPath)
			throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = null;

		/*Svc service = HttpExecutorService.executeHttpService(
				Constants.OMS_PATH.INVOICE_SERVICE, new String[] { "" + releaseId },
				Svc.SERVICE_TYPE.OMS_SVC, HTTPMethods.GET, null,
				getInvoiceOMSHeader());*/

		Svc service = HttpExecutorService.executeHttpService(
				Constants.MUNSHI_PATH.B2C_CustomerInvoicePDF, new String[] { "" + releaseId },
				SERVICE_TYPE.MUNSHI_SVC.toString(), HTTPMethods.GET, null,
				getInvoiceOMSHeader());
		httpget = new HttpGet("http://" + service.getIp() + service.getPath());
		httpget.addHeader("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		httpget.addHeader("Content-Type", "multipart/form-data");
		HttpResponse response = httpclient.execute(httpget);

		HttpEntity resEntity = response.getEntity();

		log.info(resEntity.isChunked());
		log.info(resEntity.getContentType());

		System.out
				.println("---------------------------------------------------------------");
		log.info(response.getStatusLine());

		InputStream is = resEntity.getContent();
		String fileName = "" + releaseId;
		// log.info(IOUtils.readStringFromStream(is).getBytes());
		FileOutputStream fileO = new FileOutputStream(
				new File(
						folderPath
								+fileName + ".pdf"));
		IOUtils.copy(is, fileO);
		if (resEntity != null) {
			resEntity.consumeContent();
		}

		fileO.close();
	}

    
    public void addCustomerInvoiceWithShippingLabelToFolder(String releaseId,String folderPath,String testCaseId)
			throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = null;

		/*Svc service = HttpExecutorService.executeHttpService(
				Constants.OMS_PATH.INVOICE_SERVICE, new String[] { "" + releaseId },
				Svc.SERVICE_TYPE.OMS_SVC, HTTPMethods.GET, null,
				getInvoiceOMSHeader());*/

		Svc service = HttpExecutorService.executeHttpService(
				Constants.OMS_PATH.INVOICE_SERVICE_PDF, new String[] { "" + releaseId },
				SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null,
				getInvoiceOMSHeader());
		httpget = new HttpGet("http://" + service.getIp() + service.getPath());
		httpget.addHeader("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		httpget.addHeader("Content-Type", "multipart/form-data");
		HttpResponse response = httpclient.execute(httpget);

		HttpEntity resEntity = response.getEntity();

		log.info(resEntity.isChunked());
		log.info(resEntity.getContentType());

		System.out
				.println("---------------------------------------------------------------");
		log.info(response.getStatusLine());

		InputStream is = resEntity.getContent();
		String fileName = "" + releaseId;
		// log.info(IOUtils.readStringFromStream(is).getBytes());
		FileOutputStream fileO = new FileOutputStream(
				new File(
						folderPath
								+ testCaseId+"_"+fileName + ".pdf"));
		IOUtils.copy(is, fileO);
		if (resEntity != null) {
			resEntity.consumeContent();
		}

		fileO.close();
	}
    
    
    /**
	 * @param orderReleaseID
	 * @throws IOException
     * @throws ManagerException 
	 */
	public void getAllTheInvoicesDetailsAndPutInFolder(String orderReleaseID,String folderPath,String testCaseId) throws IOException, ManagerException{
		List resultSet = null;
		HashMap<String, Object> hm = null;
		
		try {
            resultSet = DBUtilities.exSelectQuery("select * from `document_details` where entity_id ='" + orderReleaseID + "';", "myntra_munshi");
        } catch (Exception e) {
            e.printStackTrace();
          
            Assert.fail("unable to get details");
        }
		
		if(resultSet==null){
			log.info("There is no record for invoices in munshi");
			return;
		}
		
		for(int i=0;i<resultSet.size();i++){
			hm = (HashMap<String, Object>) resultSet.get(i);
			String documentName = hm.get("document_name").toString();
			addDocumentToFolder(orderReleaseID,documentName,folderPath,testCaseId);
		}
	}
	
	/**
	 * @param orderReleaseId
	 * @param documentName
	 * @param folderPath
	 * @throws IOException
	 * @throws ManagerException 
	 */
	public void addDocumentToFolder(String orderReleaseId, String documentName, String folderPath,String testCaseId) throws IOException, ManagerException {

		switch (DocumentName.valueOf(documentName)) {
		case physicalCustomerInvoice:
			addCustomerInvoiceWithShippingLabelToFolder(orderReleaseId,folderPath,testCaseId);
		case customerInvoice:
		case creditNote:
		case returnCreditNote: {
			addCustomerInvoiceWithShippingLabelToFolder(orderReleaseId,folderPath,testCaseId);
			HashMap<String, Object> data = getEntryForInvoiceInMunshi(orderReleaseId, documentName);
			Long partnerId = Long.parseLong(data.get("issued_by").toString());
			generateCustomerInvoice(documentName, orderReleaseId, partnerId, false, folderPath,testCaseId);
			break;
		}
		case financeInvoice:
		case bTobCreditNote: {
			HashMap<String, Object> data = getEntryForInvoiceInMunshi(orderReleaseId, documentName);
			Long partnerId = Long.parseLong(data.get("issued_by").toString());
			Long entity_key = Long.parseLong(data.get("entity_key").toString());
			generateFinanceInvoice(documentName, orderReleaseId, partnerId, false, entity_key, folderPath,testCaseId);
			break;
		}
		
		case shippingLabel:{
			addShippingLabelToFolder(orderReleaseId,folderPath,testCaseId);
		}
		
		case shippingLabelOms:{
			addShippingLabelToFolderOms(orderReleaseId,folderPath,testCaseId);
		}

	   }
	}

	/**
	 * @param orderReleaseID
	 * @param documentName
	 * @return
	 */
	public HashMap<String, Object> getEntryForInvoiceInMunshi(String orderReleaseID, String documentName){
        List resultSet = null;
        HashMap<String, Object> hm = null;
        
        try {
            resultSet = DBUtilities.exSelectQuery("select * from `document_details` where `document_name` ='"+documentName+"' and entity_id ='" + orderReleaseID + "';", "myntra_munshi");
        } catch (Exception e) {
            e.printStackTrace();
          
            Assert.fail("unable to get details");
        }
        hm = (HashMap<String, Object>) resultSet.get(0);
        return hm;
    }
	
	
	/**
	 * @param documentName
	 * @param entityId
	 * @param partnerID
	 * @param doReRender
	 * @param entityKey
	 */
	public void generateFinanceInvoice(String documentName, String entityId, long partnerID, Boolean doReRender,Long entityKey,String folderPath,String testCaseId)throws IOException{
		String payload = "<documentCreationRequestEntry><appName>MUNSHI</appName><documentDate>2017-07-13T14:47:31+05:30</documentDate><documentName>"+documentName+"</documentName><entityId>"+entityId+"</entityId><partnerId>"+partnerID+"</partnerId><reRender>"+doReRender+"</reRender><entityKey>"+entityKey+"</entityKey></documentCreationRequestEntry>";
        HashMap<String, String> header = new HashMap<>();
        header.put("Authorization", "Basic cXFzczphc2RzZA==");
        header.put("Content-Type", "application/xml");
        Svc svc = HttpExecutorService.executeHttpService(Constants.MUNSHI_PATH.GENERATE_DOCUMENT,
                null, SERVICE_TYPE.MUNSHI_SVC.toString(), HTTPMethods.POST, payload, header);
        
        String fileName = entityId+"_"+documentName;
        FileOutputStream fileO = new FileOutputStream(new File(folderPath +testCaseId+"_"+ fileName + ".pdf"));
        IOUtils.copy(new ByteArrayInputStream(svc.getResponseBodyStream()), fileO);
        fileO.close();
	}
    
    /**
     * @param documentName
     * @param entityId
     * @param partnerID
     * @param doReRender
     * @param folderPath
     * @throws IOException
     */
    public void generateCustomerInvoice(String documentName, String entityId, long partnerID, Boolean doReRender,String folderPath,String testCaseId) throws IOException{
		String payload = "<documentCreationRequestEntry><appName>MUNSHI</appName><documentDate>2017-07-13T14:47:31+05:30</documentDate><documentName>"+documentName+"</documentName><entityId>"+entityId+"</entityId><partnerId>"+partnerID+"</partnerId><reRender>"+doReRender.toString()+"</reRender></documentCreationRequestEntry>";
        HashMap<String, String> header = new HashMap<>();
        header.put("Authorization", "Basic cXFzczphc2RzZA==");
        header.put("Content-Type", "application/xml");
        Svc svc = HttpExecutorService.executeHttpService(Constants.MUNSHI_PATH.GENERATE_DOCUMENT,
                null, SERVICE_TYPE.MUNSHI_SVC.toString(), HTTPMethods.POST, payload, header);

        String fileName = entityId+"_"+documentName;
        FileOutputStream fileO = new FileOutputStream(new File(folderPath + testCaseId+"_"+fileName + ".pdf"));
        IOUtils.copy(new ByteArrayInputStream(svc.getResponseBodyStream()), fileO);
        fileO.close();
	}
    
    /**
     * This is to put shipping label to folder
     * @param releaseId
     * @param folderPath
     * @param testCaseId
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ManagerException 
     */

    public void addShippingLabelToFolder(String releaseId,String folderPath,String testCaseId)
			throws ClientProtocolException, IOException, ManagerException {

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = null;

		Svc service = HttpExecutorService.executeHttpService(
				Constants.LMS_PATH.GET_SHIPPING_LABEL, new String[] { "" + releaseId },
				SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null,
				getInvoiceOMSHeader());

		ExceptionHandler.handleEquals(service.getResponseStatus(),200,"There is issue while generating pdf");

		httpget = new HttpGet("http://" + service.getIp() + service.getPath());
		httpget.addHeader("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		httpget.addHeader("Content-Type", "multipart/form-data");
		HttpResponse response = httpclient.execute(httpget);

		HttpEntity resEntity = response.getEntity();

		log.info(resEntity.isChunked());
		log.info(resEntity.getContentType());

		log.info("---------------------------------------------------------------");
		log.info(response.getStatusLine());

		InputStream is = resEntity.getContent();
		String fileName = "" + releaseId;
		// log.info(IOUtils.readStringFromStream(is).getBytes());
		FileOutputStream fileO = new FileOutputStream(
				new File(
						folderPath
								+ testCaseId+"_"+fileName + ".pdf"));
		IOUtils.copy(is, fileO);
		if (resEntity != null) {
			resEntity.consumeContent();
		}

		fileO.close();
	}

    
    /**
     * This is to put shipping label to folder using oms api
     * @param releaseId
     * @param folderPath
     * @param testCaseId
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ManagerException 
     */
    public void addShippingLabelToFolderOms(String releaseId,String folderPath,String testCaseId)
			throws ClientProtocolException, IOException, ManagerException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = null;

		Svc service = HttpExecutorService.executeHttpService(
				Constants.OMS_PATH.GET_SHIPPING_LABEL_DOC, new String[] { "" + releaseId },
				SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null,
				getInvoiceOMSHeader());
		ExceptionHandler.handleEquals(service.getResponseStatus(),200,"There is issue while generating pdf");
		httpget = new HttpGet("http://" + service.getIp() + service.getPath());
		httpget.addHeader("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		httpget.addHeader("Content-Type", "multipart/form-data");
		HttpResponse response = httpclient.execute(httpget);

		HttpEntity resEntity = response.getEntity();

		log.info(resEntity.isChunked());
		log.info(resEntity.getContentType());

		log.info("---------------------------------------------------------------");
		log.info(response.getStatusLine());

		InputStream is = resEntity.getContent();
		String fileName = "" + releaseId;
		// log.info(IOUtils.readStringFromStream(is).getBytes());
		FileOutputStream fileO = new FileOutputStream(
				new File(
						folderPath
								+ testCaseId+"_"+fileName + ".pdf"));
		IOUtils.copy(is, fileO);
		if (resEntity != null) {
			resEntity.consumeContent();
		}

		fileO.close();
	}


	/**
	 * @return
	 */
	private static HashMap<String, String> getInvoiceOMSHeader() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		createOrderHeaders.put("Content-Type", "multipart/form-data");
		createOrderHeaders.put("Accept", "multipart/form-data");
		return createOrderHeaders;
	}

}