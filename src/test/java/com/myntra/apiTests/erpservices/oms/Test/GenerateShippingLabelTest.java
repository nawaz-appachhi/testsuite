package com.myntra.apiTests.erpservices.oms.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.common.Builder.CreateOrderEntryBuilder;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.entries.CreateOrderEntry;
import com.myntra.apiTests.common.entries.SkuEntry;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.InvoiceServiceHelper;
import com.myntra.apiTests.erpservices.oms.InvoiceServiceHelper.DocumentName;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.dp.GenerateShippingLabelTestDP;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.test.commons.testbase.BaseTest;

public class GenerateShippingLabelTest extends BaseTest {
	
	String folderPath = System.getProperty("user.dir")+"/ShippingLabel/";
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	End2EndHelper end2EndHelper = new End2EndHelper();
	InvoiceServiceHelper invoiceServiceHelper = new InvoiceServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	SoftAssert sft;
	private List<OrderReleaseEntry> orderReleaseEntries;
	String login = OMSTCConstants.GenerateShippingLabel.firstLogin;
	String password = OMSTCConstants.GenerateShippingLabel.firstPassword;
	private long sellerId_HEAL=21;
	private String supplyTypeOnHand="ON_HAND";
	private long sellerId_CONS=25;
	private String skuId1= OMSTCConstants.OtherSkus.skuId_3831;
	private String skuId2= OMSTCConstants.OtherSkus.skuId_3832;
	private int warehouse28 = OMSTCConstants.WareHouseIds.warehouseId28_GN;
	private String orderID;
	private String firstRelease;
	

	@BeforeClass(alwaysRun = true, enabled=true)
    public void testBeforeClass() throws SQLException, IOException, JAXBException {
		
		removeFilesFromFolder();
		updateInventoryForSku();
		
     }
	
/*	//For Local testing
	@BeforeMethod(alwaysRun = true, enabled=true)
    public void testBeforeMethod() throws SQLException, IOException, JAXBException {
		
		removeFilesFromFolder();
		updateInventoryForSku();
		
     }*/
	
	/**
	 * This is to update inventory for skus
	 */
	public void updateInventoryForSku(){
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1",
				skuId2+":"+warehouse28+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL,
				skuId2+":"+warehouse28+":100:0:"+sellerId_CONS},supplyTypeOnHand);

	}
	
	/**
	 * This is to remove existing filed from ShippingLabel folder
	 */
	public void removeFilesFromFolder(){
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
	

	@Test(enabled=true,groups={"regression","shippingLabel"},dataProvider = "shippingLabelDP",dataProviderClass = GenerateShippingLabelTestDP.class, description="TC001:Generate ShippingLabel for SingleLine order")
	public void singleLineOrderShippingLabel(Long pincode,String paymentMethod,String courierCode,String shippingMethod,String shipmentType) throws Exception{
		
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 2)};
		orderReleaseEntries = createOrderHelper(login,password,pincode,skuEntries);
		firstRelease = orderReleaseEntries.get(0).getId().toString(); 
		updateDataForRelease(firstRelease,pincode,paymentMethod,courierCode,shippingMethod,shipmentType);
		String documentSuffix = "TC001_"+pincode+"_"+paymentMethod+"_"+courierCode+"_"+shippingMethod+"_"+shipmentType;
		invoiceServiceHelper.addDocumentToFolder(firstRelease,DocumentName.shippingLabel.name(),folderPath,documentSuffix);
		invoiceServiceHelper.addDocumentToFolder(firstRelease,DocumentName.shippingLabelOms.name(),folderPath,documentSuffix+"_OMS");

	}
	
	@Test(enabled=true,groups={"regression","shippingLabel"},dataProvider = "shippingLabelDP",dataProviderClass = GenerateShippingLabelTestDP.class,description="TC002:Generate ShippingLabel for MultipleLine multiseller order")
	public void multiLineMultiSellerOrderShippingLabel(Long pincode,String paymentMethod,String courierCode,String shippingMethod,String shipmentType) throws Exception{
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 2),new SkuEntry(skuId2, 2)};
		orderReleaseEntries = createOrderHelper(login,password,pincode,skuEntries);
		firstRelease = orderReleaseEntries.get(0).getId().toString(); 
		updateDataForRelease(firstRelease,pincode,paymentMethod,courierCode,shippingMethod,shipmentType);
		String documentSuffix = "TC002"+pincode+""+paymentMethod+""+courierCode+""+shippingMethod+shipmentType;
		invoiceServiceHelper.addDocumentToFolder(firstRelease,DocumentName.shippingLabel.name(),folderPath,documentSuffix);
		invoiceServiceHelper.addDocumentToFolder(firstRelease,DocumentName.shippingLabelOms.name(),folderPath,documentSuffix+"_OMS");

	}
	
	/**
	 * This is to create order and return release entries
	 * @param login
	 * @param password
	 * @param pincode
	 * @param skuEntries
	 * @return
	 * @throws Exception
	 */
	public List<OrderReleaseEntry> createOrderHelper(String login,String password,Long pincode,SkuEntry[] skuEntries) throws Exception{
		CreateOrderEntry createOrderEntry = new CreateOrderEntryBuilder(login,password,pincode,skuEntries).build();
		orderID = end2EndHelper.createOrder(createOrderEntry);

		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		orderReleaseEntries = omsServiceHelper.getOrderEntry(orderID).getOrderReleases();
		return orderReleaseEntries;
	}
	
	/**
	 * This is to update data in order_line
	 * @param orderReleaseId
	 * @param courierCode
	 * @param supplyType
	 * @param sellerId
	 * @throws JAXBException 
	 * @throws UnsupportedEncodingException 
	 */
	public void updateDataForRelease(String orderReleaseId,Long pincode,String paymentMethod,String courierCode,String shippingMethod,String shipmentType) throws UnsupportedEncodingException, JAXBException{
		String query = "update `order_release` set `zipcode`='"+pincode+"', `courier_code`='"+courierCode+"',"
				+ "`payment_method`='"+paymentMethod+"',`shipping_method`='"+shippingMethod+"' where `id` ='"+orderReleaseId+"'";
		DBUtilities.exUpdateQuery(query, "myntra_oms");
		
		//Stamping Tracking number in oms
		omsServiceHelper.assignTrackingNumber(orderReleaseId, Boolean.FALSE);
		
		for(OrderLineEntry orderLineEntry:omsServiceHelper.getOrderLineEntries(orderReleaseId)){
			if(shipmentType.equalsIgnoreCase(EnumSCM.TRY_AND_BUY)){
				omsServiceHelper.updateInOrderLineAdditionalInfo(orderLineEntry.getId().toString(),EnumSCM.TRY_AND_BUY,Boolean.TRUE.toString());
			}
		}

	}
	
}
