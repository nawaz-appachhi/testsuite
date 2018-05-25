/*
package com.myntra.apiTests.end2end;

import com.google.gdata.util.ServiceException;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.test.commons.testbase.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class InvoiceNumberLoadTest extends BaseTest {
	private String login = "end2endautomation4@gmail.com";
	private String password = "myntra@123";
	String uidx;
	IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	private static Long vectorSellerID;
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	End2EndHelper end2EndHelper = new End2EndHelper();
	private String supplyTypeOnHand = "ON_HAND";
	private OrderEntry orderEntry;
	private Long orderReleaseId;
	private Long orderID;
	StringBuilder sb = new StringBuilder();
	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	FileWriter fileWriter = null;
	private static final String FILE_HEADER = "orderReleaseId";
	private String fileName = "/Users/16553/Documents/jmxFiles/B2BData_sameSKU.csv";

	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, JAXBException, IOException, GeneralSecurityException, ServiceException, URISyntaxException {
		//vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
		vectorSellerID = 21L;
		//omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
		uidx = ideaApiHelper.getUIDXForLogin("myntra", login);
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID + ":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID},
				supplyTypeOnHand);
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID + ":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832 + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID},
				supplyTypeOnHand);
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{"3867" + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID + ":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{"3867" + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID},
				supplyTypeOnHand);
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{"890848" + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID + ":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{"890848" + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID},
				supplyTypeOnHand);
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{"3133754" + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID + ":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{"3133754" + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID},
				supplyTypeOnHand);
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{"40563" + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID + ":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{"40563" + ":" + OMSTCConstants.WareHouseIds.warehouseId36_BW + ":10000:0:" + vectorSellerID},
				supplyTypeOnHand);
		
		
		try {
			fileWriter = new FileWriter(fileName);
			//Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());
			//Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);
		} catch (FileNotFoundException fe) {
			fe.getStackTrace();
		}
	}

	@AfterClass(alwaysRun = true)
	public void testAfterClass() {
		try {
			fileWriter.write(sb.toString());

			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Error while flushing/closing fileWriter !!!");
			e.printStackTrace();
		}
	}

	
	 @Test(description = "End to End Create And Mark Order PK", priority =1,dataProvider="skuList")
	 public void testE2EMarkOrderPK(int sku) throws Exception {
		 String skuId[] = {sku + ":1"};
		 orderID = end2EndHelper.createOrder(login, password, "6140312", skuId, "", false, false, false, "", false);
		 omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		 orderEntry = omsServiceHelper.getOrderEntry(orderID);
		 OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		 orderReleaseId = orderReleaseEntry.getId();
		 omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		// DBUtilities.exUpdateQuery("update order_release set tracking_no='ML123456',status_code='PK',packed_on='2017-03-22 15:56:17' where id='"+orderReleaseId +"'",
				 //"OMS");
		// DBUtilities.exUpdateQuery("update order_line set status_code='QD' where order_release_id_fk='"+orderReleaseId +"'",
				 //"OMS");
		// end2EndHelper.markOrderDelivered(orderReleaseId, "PK", EnumSCM.RELEASE);
		 writeToCSV(orderReleaseId);
	 }

	 
	 private void writeToCSV(Long orderReleaseID) {
	 	sb.append(orderReleaseID);
	 	sb.append(COMMA_DELIMITER);
	 	sb.append(NEW_LINE_SEPARATOR);
	}

	@DataProvider
	 public Object[][] skuList(){
		 Integer skus[]={3831,3832,3867,890848,3133754,40563};
		//Integer skus[]={3831};
		//Integer skus[]={3832};
		 ArrayList<Object[]> list = new ArrayList<Object[]>();
		 Random rn = new Random();
		 for (int i=1;i<=100;i++)
		 {
			 int rndNum = (rn.nextInt(skus.length -0)+0 );
			 list.add(new Object[]{skus[rndNum]});
		 }
		 return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), null, list.size(),
	                list.size());
	    }
		 
	 }
	 
*/
