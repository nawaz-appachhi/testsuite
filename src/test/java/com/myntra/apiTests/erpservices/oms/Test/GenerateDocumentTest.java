package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.oms.InvoiceServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.dp.GenerateDocumentTestDP;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.test.commons.testbase.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenerateDocumentTest extends BaseTest {
	String folderPath = System.getProperty("user.dir")+"/Invoices/";
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	End2EndHelper end2EndHelper = new End2EndHelper();
	InvoiceServiceHelper invoiceServiceHelper = new InvoiceServiceHelper();
	ProcessRelease processRelease = new ProcessRelease();
	
	@BeforeClass(alwaysRun = true, enabled=true)
    public void testBeforeClass() throws SQLException, IOException, JAXBException {
    	File file = new File(folderPath);      
        String[] myFiles;    
            if(file.isDirectory()){
                myFiles = file.list();
                for (int i=0; i<myFiles.length; i++) {
                    File myFile = new File(file, myFiles[i]); 
                    myFile.delete();
                }
             }
    }
	
	
	@Test( description="TC001:This will create order and generate customerInvoice,FinanceInvoice,physicalCustomerInvoice", dataProvider = "documentGenerationDP", dataProviderClass = GenerateDocumentTestDP.class)
	public void forwardFlowTest001(String orderId) throws Exception{
		
		omsServiceHelper.checkReleaseStatusForOrder(orderId, "WP");		
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderId);
		String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		End2EndHelper.sleep(5000L);
		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		invoiceServiceHelper.getAllTheInvoicesDetailsAndPutInFolder(orderReleaseId,folderPath,"TC001");
		
	}
	
	@Test( description="TC002:This will create order and generate customerInvoice,FinanceInvoice,physicalCustomerInvoice", dataProvider = "cancelPKDP", dataProviderClass = GenerateDocumentTestDP.class)
	public void cancelPKFlowTest002(String orderId, String login) throws Exception{
		SoftAssert sft = new SoftAssert();

		omsServiceHelper.checkReleaseStatusForOrder(orderId, "WP");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderId);
		String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "39", login, "Order Release Cancellation");
    	sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006,"Verify response code after cancel release");
    	sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"Verify response type as success after cancel release");
    	sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "F");
    	sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 10),"Release is not in F status");
    	End2EndHelper.sleep(10000L);
		invoiceServiceHelper.getAllTheInvoicesDetailsAndPutInFolder(orderReleaseId,folderPath,"TC002");
		sft.assertAll();
		
	}
	
	@Test( description="TC003:This will create order and generate customerInvoice,FinanceInvoice,physicalCustomerInvoice", dataProvider = "documentGenerationDP", dataProviderClass = GenerateDocumentTestDP.class)
	public void LOSTCreditNoteTest(String orderId) throws Exception{
		
		omsServiceHelper.checkReleaseStatusForOrder(orderId, "WP");		
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderId);
		String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.LOST).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.LOST).build());
		End2EndHelper.sleep(5000L);
		invoiceServiceHelper.getAllTheInvoicesDetailsAndPutInFolder(orderReleaseId,folderPath,"TC003");
		
	}
	
	@Test( description="TC004:This will create order and generate customerInvoice,FinanceInvoice,physicalCustomerInvoice", dataProvider = "documentGenerationDP", dataProviderClass = GenerateDocumentTestDP.class)
	public void RTOCreditNoteTest(String orderId) throws Exception{
		
		omsServiceHelper.checkReleaseStatusForOrder(orderId, "WP");		
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderId);
		String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.RTO).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.RTO).build());
		End2EndHelper.sleep(5000L);
		invoiceServiceHelper.getAllTheInvoicesDetailsAndPutInFolder(orderReleaseId,folderPath,"TC004");
		
	}
	
	
	
	
}
