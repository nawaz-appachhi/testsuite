package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.apiTests.erpservices.partners.SellerServicesHelper;
import com.myntra.apiTests.erpservices.partners.dp.SellerDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.sellers.enums.AddressType;
import com.myntra.sellers.response.*;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.sql.SQLException;

public class SellerServices extends BaseTest{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SellerServices.class);
	APIUtilities apiUtil = new APIUtilities();
	
	
static Long sellerId;
static String sellerBarcode;
	
	@Test(groups = { "Smoke","Regression"}, priority = 0, dataProviderClass = SellerDP.class, dataProvider = "e2e_createSeller",description = "1.Create Seller \n 2. Validates Seller created successfully")
		public void Seller_createSeller(String name , String displayName, Boolean international ) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.createSeller(name, displayName,  international);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1625);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller has been added successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
				Long id= sellerResponse.getData().get(0).getId();
				String barcode= sellerResponse.getData().get(0).getBarcode();
				System.out.println("Seller Id: "+id);
				System.out.println("Seller barcode: "+barcode);
				sellerId = id;
				sellerBarcode = barcode;
			}
	
	@Test(groups = {"Regression",}, priority = 1, dataProviderClass = SellerDP.class, dataProvider = "e2e_addcategoryManager",description = "1.Add Category Manager for the Seller\n 2. Validates Category manager created successfully")
		public void Seller_addCategoryManager(String enabled,String userId) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.addCategoryManager(enabled,sellerId.toString(),userId);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1629);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller Category Manager has been added successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = { "Regression"}, priority = 2, dataProviderClass = SellerDP.class, dataProvider = "e2e_addBrand",description = "1.Add Brand for the Seller \n 2. Validates Brand created successfully")
		public void Seller_addBrand(String brandId,String brandName) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.addSellerBrand(brandId,brandName,sellerId.toString());
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1636);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller Brand has been added successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = { "Regression" }, priority = 3, dataProviderClass = SellerDP.class, dataProvider = "e2e_addWarehouse",description = "1.Add Warehouse for the Seller \n 2. Validates Warehouse created successfully")
		public void Seller_addWarehouse(String leadTime,String warehouseId) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.addWarehouse(leadTime, sellerId.toString(), warehouseId);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1629);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller warehouse added successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = { "Regression" }, priority = 4, dataProviderClass = SellerDP.class, dataProvider = "e2e_addFinancialInfo",description = "1.Add Financial Info for the Seller \n 2. Validates Financial Info created successfully")
		public void Seller_addFinancialInfo(String accountId,String enabled) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.addFinancialInfo( sellerId.toString(), accountId, enabled);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1631);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller finance info has been added successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	@Test(groups = { "Regression" }, priority = 5, dataProviderClass = SellerDP.class, dataProvider = "e2e_addPaymentConfig",description = "1.Add Payment Configuration for the Seller \n 2. Validates Payment Config created successfully")
		public void Seller_addPaymentConfig(String marginPercentage,String enabled) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.addPaymentConfig( sellerId.toString(), marginPercentage, enabled);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1633);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller payment configuration has been added successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	@Test(groups = {  "Regression" }, priority = 6, dataProviderClass = SellerDP.class, dataProvider = "e2e_addKycDoc",description = "1.Add KYC Document for the Seller \n 2. Validates Kyc Doc created successfully")
		public void Seller_addKycDoc(String documentNumber,String documentRefId,String documentExtension,String enabled) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.addKycDoc( sellerId.toString(), documentNumber, documentRefId, documentExtension, enabled, sellerId.toString());
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1639);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller KYC Document added successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	@Test(groups = { "Regression" }, priority = 7, dataProviderClass = SellerDP.class, dataProvider = "e2e_addShipping",description = "1.Add Shipping Address for the Seller \n 2. Validates Shipping created successfully")
		public void Seller_addShipping(String address1,String city,String country,String email1,String enabled, String phone1,String pinCode,String state) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.addShipping( address1, city, country, email1, enabled, sellerId.toString(),  phone1, pinCode, state);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1640);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller Contact Address added successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	@Test(groups = { "Regression"}, priority = 8, dataProviderClass = SellerDP.class, dataProvider = "e2e_addBilling",description = "1.Add Billing Address for the Seller \n 2. Validates Billing created successfully")
		public void Seller_addBilling(String address1,String city,String country,String email1,String enabled, String phone1,String pinCode,String state) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.addBilling( address1, city, country, email1, enabled, sellerId.toString(),  phone1, pinCode, state);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1640);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller Contact Address added successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = {"Regression"}, priority = 9, dataProviderClass = SellerDP.class, dataProvider = "SellerServices_addSellerItem",description = "1.Add Seller Item Master for the Seller \n 2. Validate Seller Item master added successfully")
	public void Seller_addSellerItem( String sellerSkuCode , String myntraSkuCode,String enabled,String styleId) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
		{  	
	 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
	 		SellerItemMasterResponse sellerItemMasterResponse = sellerServicesHelper.addSellerItemMaster( sellerId.toString(),  sellerSkuCode ,  myntraSkuCode, enabled, styleId, sellerBarcode);
			Assert.assertEquals(sellerItemMasterResponse.getStatus().getStatusCode() , 4001);
			Assert.assertEquals(sellerItemMasterResponse.getStatus().getStatusMessage().toString(), "Seller Item Master retrieved successfully");
			Assert.assertEquals(sellerItemMasterResponse.getStatus().getStatusType().toString(), "SUCCESS");
			//Assert.assertEquals(sellerItemMasterResponse.getData().get(0).getMessage().toString(), "Successfully Added");
			
		}
	
	@Test(groups = {"Regression"}, priority = 10, dataProviderClass = SellerDP.class, dataProvider = "SellerServices_addSellerItem",description = "1.Add Seller Item Master for the Seller with Same SkuCodes \n 2.Validates duplicate Seller item master is not created")
	public void Seller_addSellerItemWithDup( String sellerSkuCode , String myntraSkuCode,String enabled,String styleId) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
		{  	
	 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
	 		SellerItemMasterResponse sellerItemMasterResponse = sellerServicesHelper.addSellerItemMaster( sellerId.toString(),  sellerSkuCode ,  myntraSkuCode, enabled, styleId, sellerBarcode);
			Assert.assertEquals(sellerItemMasterResponse.getStatus().getStatusCode() , 4001);
			Assert.assertEquals(sellerItemMasterResponse.getStatus().getStatusMessage().toString(), "Seller Item Master retrieved successfully");
			Assert.assertEquals(sellerItemMasterResponse.getStatus().getStatusType().toString(), "SUCCESS");
			//Assert.assertEquals(sellerItemMasterResponse.getData().get(0).getStatus().toString(), "ERROR");
			
		}
	
	@Test(groups = {"Regression"}, priority = 11,dataProviderClass = SellerDP.class, dataProvider = "SellerServices_getSellerItemBySkuId",description = "1.Get Seller Item master By SkuId")
	public void Seller_getSellerItemBySkuId(String skuId) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
	{
		//	List<String> skuList = new ArrayList<>(); 
			//skuList.add(skuId);
	 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
	 		SellerItemMasterResponse sellerItemMasterResponse = sellerServicesHelper.getSellerItemBySkuId( sellerId.toString() ,skuId);
			Assert.assertEquals(sellerItemMasterResponse.getStatus().getStatusCode() , 4001);
			Assert.assertEquals(sellerItemMasterResponse.getStatus().getStatusMessage().toString(), "Seller Item Master retrieved successfully");
			Assert.assertEquals(sellerItemMasterResponse.getStatus().getStatusType().toString(), "SUCCESS");
			Assert.assertEquals(sellerItemMasterResponse.getData().get(0).getSkuId().toString(), "1251896");
			
		}
	
	@Test(groups = {"Regression"}, priority = 12,dataProviderClass = SellerDP.class, dataProvider = "SellerServices_getSellerItemBySkuCode",description = "1.Get Seller Item master By SkuCode")
	public void Seller_getSellerItemBySkuCode(String sellerSkuCode) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
	{
		
			//List<String> skuList = new ArrayList<>(); 
			//skuList.add("\""+skuCode+"\"");
	 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
	 		SellerItemMasterResponse sellerItemMasterResponse = sellerServicesHelper.getSellerItemBySkuCode( sellerId.toString() ,sellerSkuCode);
			Assert.assertEquals(sellerItemMasterResponse.getStatus().getStatusCode() , 4001);
			Assert.assertEquals(sellerItemMasterResponse.getStatus().getStatusMessage().toString(), "Seller Item Master retrieved successfully");
			Assert.assertEquals(sellerItemMasterResponse.getStatus().getStatusType().toString(), "SUCCESS");
			Assert.assertEquals(sellerItemMasterResponse.getData().get(0).getSellerSkuCode().toString(), "XNYYSHRT00251MADURAINT1");
			
		}
	
	@Test(groups = {"Regression"}, priority = 13,description = "1.Get Seller Item master By Seller Id")
	public void Seller_getSellerItemBySeller() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
		{  	
	 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
	 		SellerItemMasterResponse sellerItemMasterResponse = sellerServicesHelper.getSellerItemBySeller(sellerId.toString());
			Assert.assertEquals(sellerItemMasterResponse.getStatus().getStatusCode() , 4001);
			Assert.assertEquals(sellerItemMasterResponse.getStatus().getStatusMessage().toString(), "Seller Item Master retrieved successfully");
			Assert.assertEquals(sellerItemMasterResponse.getStatus().getStatusType().toString(), "SUCCESS");
			Assert.assertEquals(sellerItemMasterResponse.getData().get(0).getSellerId(), sellerId);
		}
	
	@Test(groups = {"Regression"}, priority = 14,description = "1.Get Warehouse By Seller Id")
	public void Seller_getWarehouseBySeller() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
		{  	
	 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
	 		SellerWarehouseResponse sellerWarehouseResponse = sellerServicesHelper.getWarehouseBySeller(sellerId.toString());
			Assert.assertEquals(sellerWarehouseResponse.getStatus().getStatusCode() , 1630);
			Assert.assertEquals(sellerWarehouseResponse.getStatus().getStatusMessage().toString(), "Seller warehouse has been retrieved successfully");
			Assert.assertEquals(sellerWarehouseResponse.getStatus().getStatusType().toString(), "SUCCESS");
			//Assert.assertEquals(sellerWarehouseResponse.getData().get(0).getSellerEntry().getId().toString(),sellerId);
		}
	
	@Test(groups = {"Regression"}, priority = 15,description = "1.Get Financial Info By Seller Id")
	public void Seller_getFinanceInfoBySeller() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
		{  	
	 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
	 		FinanceInformationResponse financeInformationResponse = sellerServicesHelper.getFinancialInfoBySeller(sellerId.toString());
			Assert.assertEquals(financeInformationResponse.getStatus().getStatusCode() , 4004);
			Assert.assertEquals(financeInformationResponse.getStatus().getStatusMessage().toString(), "Seller finance info retrieved successfully");
			Assert.assertEquals(financeInformationResponse.getStatus().getStatusType().toString(), "SUCCESS");
			Assert.assertEquals(financeInformationResponse.getData().get(0).getAccountId(), "272");
		}
	
	@Test(groups = {"Regression"}, priority = 16,description = "1.Get Kyc Doc By Seller Id")
	public void Seller_getKycDocBySeller() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
		{  	
	 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
	 		KYCDocumentResponse kYCDocumentResponse = sellerServicesHelper.getKycDocBySeller(sellerId.toString());
			Assert.assertEquals(kYCDocumentResponse.getStatus().getStatusCode() , 4006);
			Assert.assertEquals(kYCDocumentResponse.getStatus().getStatusMessage().toString(), "Seller kyc documents retrieved successfully");
			Assert.assertEquals(kYCDocumentResponse.getStatus().getStatusType().toString(), "SUCCESS");
			Assert.assertEquals(kYCDocumentResponse.getData().get(0).getSellerId(), sellerId);
			
		}
	
	@Test(groups = {"Regression"}, priority = 17,description = "1.Get Payment Config By Seller Id")
	public void Seller_getPaymentConfigBySeller() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
		{  	
	 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
            PaymentConfigurationResponse paymentConfigurationResponse = sellerServicesHelper.getPaymentConfigBySeller(sellerId.toString());
			Assert.assertEquals(paymentConfigurationResponse.getStatus().getStatusCode() , 4005);
			Assert.assertEquals(paymentConfigurationResponse.getStatus().getStatusMessage().toString(), "Seller payment config retrieved successfully");
			Assert.assertEquals(paymentConfigurationResponse.getStatus().getStatusType().toString(), "SUCCESS");
			//Assert.assertEquals(paymentConfigurationResponse.getData().get(0).getSellerId(), sellerId);
			
		}
	
	@Test(groups = {"Regression"}, priority = 18,description = "1.Get Styles By Seller Id")
	public void Seller_getStyleIdsBySeller() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
		{  	
	 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
	 		SellerResponse sellerResponse = sellerServicesHelper.getStyleIdsBySeller(sellerId.toString());
			Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 4001);
			Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller Item Master retrieved successfully");
			Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");

		}

	@Test(groups = { "Regression"},dataProviderClass = SellerDP.class, dataProvider = "SellerServices_createSellerNegative",description = "1.Create Seller by Wrong Input \n 2. Validates Seller should not get Created")
		public void Seller_createSellerNegative(String name) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.createSellerNegative(name);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 54);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Error occurred while inserting/processing data");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "ERROR");
			}

	/*@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
	"ExhaustiveRegression" }, priority = 0, dataProviderClass = SellerDP.class, dataProvider = "SellerServices_updateSeller")
		public void Seller_updateSeller(String sellerId, String name, String barcode, SellerStatus status) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.updateSeller(sellerId, name, barcode, status);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1625);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller has been added successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
				Long id= sellerResponse.getData().get(0).getId();
				System.out.println("Seller Id: "+id);
			}*/
	
	@Test(groups = { "Regression"},description = "1.Get All Sellers")
		public void Seller_getAllSeller() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.getAllSeller();
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1626);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller has been retrieved successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = {"Regression"},description = "1.Get Seller Info")
		public void Seller_getSellerInfo() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.getSellerInfo();
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1626);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller has been retrieved successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = {"Regression"},description = "1.Get Brand")
		public void Seller_brandSearch() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.brandSearch();
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1637);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller Brand has been retrieved successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = {"Regression"},description = "1.Get Category Manager")
		public void Seller_searchCategoryManager() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.searchCategoryManager();
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1632);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller Category Manager has been retrieved successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = {"Regression"},description = "1.Get Seller Warehouse")
		public void Seller_searchSellerWarehouse() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.searchSellerWarehouse();
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1630);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller warehouse has been retrieved successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = { "Regression"},description = "1.Get Seller Address")
		public void Seller_getSellerAddress() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.getSellerAddress();
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1640);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller Contact Address retrieved successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = {"Regression"},description = "1.Get Seller By Warehouse Id")
		public void Seller_getSellerbyWarehouseId() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.getSellerbyWarehouseId();
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1626);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller has been retrieved successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = {"Regression"},description = "1.Get Settlement info by Seller Id")
		public void Seller_getSettelmentInfoBySellerId() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		String sellerId = "1";
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.getSellerSettelmentInfoBySellerId(sellerId);
				Assert.assertEquals(sellerResponse.getData().get(0).getCreatedBy().toString() , "System");
			}
	
	@Test(groups = {"Regression"},description = "1.Get Seller Configuration")
		public void Seller_getSellerConfiguration() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.getSellerConfiguration();
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1653);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Partner configuration retrieved successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = {"Regression"},description = "1.Get All Seller Address")
		public void Seller_getAllAddress() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.getAllAddress();
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1640);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller Contact Address retrieved successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = {"Regression"},description = "1.Get Address By Seller")
		public void Seller_getAddressBySeller() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.getAddressBySeller();
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1640);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller Contact Address retrieved successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	
	@Test(groups = {"Regression"},description = "1.Get  All Seller Item")
		public void Seller_getAllSellerItem() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.getAllSellerItem();
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 4001);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller Item Master retrieved successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = {"Regression"},dataProviderClass = SellerDP.class, dataProvider = "getSellerComm",description = "1.Get Seller Commission Percentage")
	public void Seller_getSellerCommission(String active,String status,String articleType,String brand,String gender,String masterCategory,String sellerId  ) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
		{  	
	 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
	 		SellerTermsResponse sellerTermsResponse = sellerServicesHelper.getSellerCommision(active, status, articleType, brand, gender, masterCategory,sellerId);
			Assert.assertEquals(sellerTermsResponse.getStatus().getStatusCode() , 2001);
			Assert.assertEquals(sellerTermsResponse.getStatus().getStatusMessage().toString(), "Seller Term(s) retrieved successfully");
			Assert.assertEquals(sellerTermsResponse.getStatus().getStatusType().toString(), "SUCCESS");
			Assert.assertEquals(sellerTermsResponse.getData().get(0).getSellerId().toString(), "5");
			
		}
	
	@Test(groups = {"Regression"},description = "1.Get  All Seller Item")
	public void Seller_getFinancialInfo() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
		{  	
	 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
            SellerResponse sellerResponse = sellerServicesHelper.searchFinancialInfo();
			Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 4004);
			Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller finance info retrieved successfully");
			Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
		}
	
	@Test(groups = {"Regression"},description = "1.Get  All Seller Item")
	public void Seller_getKycDoc() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
		{  	
	 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
            SellerResponse sellerResponse = sellerServicesHelper.searchKycDoc();
			Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 4006);
			Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller kyc documents retrieved successfully");
			Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
		}
	
	@Test(groups = {"Regression"},description = "1.Get  All Seller Item")
	public void Seller_getPaymentConfig() throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
		{  	
	 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
            SellerResponse sellerResponse = sellerServicesHelper.searchPaymentConfig();
			Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 4005);
			Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller payment config retrieved successfully");
			Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
		}
	
	
	
	@Test(groups = {"Regression"},dataProviderClass = SellerDP.class, dataProvider = "SellerServices_addBrandNegative",description = "1.Add invalid Brand \n 2. Validates it should not get added")
		public void Seller_addBrandnegative(String brandId,String brandName,String sellerId) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.addSellerBrand(brandId,brandName,sellerId);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1816);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Error adding Brand");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "ERROR");
			}
	
	
	
	@Test(groups = {"Regression"}, dataProviderClass = SellerDP.class, dataProvider = "SellerServices_addcategoryManagerNegative",description = "1.Add invalid Category Manager \n 2. Validates it should not get added")
		public void Seller_addCategoryManagerNegative(String enabled,String sellerId,String userId) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.addCategoryManager(enabled,sellerId,userId);
                Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1810);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Error adding category manager");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "ERROR");
			}
	
	
	
	@Test(groups = {"Regression"}, dataProviderClass = SellerDP.class, dataProvider = "SellerServices_addWarehouseNegative",description = "1.Add invalid Warehouse \n 2. Validates it should not get added")
		public void Seller_addWarehouseNegative(String leadTime,String sellerId,String warehouseId) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.addWarehouse(leadTime, sellerId, warehouseId);
                Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1807);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Error in adding seller warehouse");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "ERROR");
			}
	
	
	
	@Test(groups = {"Regression"}, dataProviderClass = SellerDP.class, dataProvider = "SellerServices_addFinancialInfoNegative",description = "1.Add invalid Financial Info \n 2. Validates it should not get added")
		public void Seller_addFinancialInfoNegative(String sellerId,String accountId,String enabled) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.addFinancialInfo( sellerId, accountId, enabled);
                Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1811);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Error adding finance information");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "ERROR");
			}
	
	
	
	@Test(groups = { "Regression"}, dataProviderClass = SellerDP.class, dataProvider = "SellerServices_addPaymentConfigNegative",description = "1.Add invalid Payment Config \n 2. Validates it should not get added")
		public void Seller_addPaymentConfigNegative(String sellerId,String marginPercentage,String enabled) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.addPaymentConfig( sellerId, marginPercentage, enabled);
                Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1812);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Error adding payment configuration");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "ERROR");
			}
	
	
	
	@Test(groups = {"Regression"}, dataProviderClass = SellerDP.class, dataProvider = "SellerServices_addKycDocNegative",description = "1.Add invalid Kyc Doc \n 2. Validates it should  not get added")
		public void Seller_addKycDocNegative(String sellerId,String documentNumber,String documentRefId,String documentExtension,String enabled,String entityId) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.addKycDoc( sellerId, documentNumber, documentRefId, documentExtension, enabled, entityId);
                Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 2222);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Invalid Entity Id");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "ERROR");
			}
	
	
	
	@Test(groups = { "Regression"}, dataProviderClass = SellerDP.class, dataProvider = "SellerServices_addShippingNegative",description = "1.Add invalid Shipping \n 2. Validates it should not get added")
		public void Seller_addShippingNegative(String address1,String city,String country,String email1,String enabled,String sellerId, String phone1,String pinCode,String state) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.addShipping( address1, city, country, email1, enabled, sellerId,  phone1, pinCode, state);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1818);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Error adding contact Address");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "ERROR");
			}
	
	
	@Test(groups = {"Regression"},dataProviderClass = SellerDP.class, dataProvider ="disable",description = "1.Disable Brand ")
		public void Seller_disableBrand(String Id) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.disableBrand(Id);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1635);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller Brand has been updated successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = {"Regression"},dataProviderClass = SellerDP.class, dataProvider ="disable",description = "1.Disable Category Manager ")
		public void Seller_disableCategoryManager(String Id) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.disableCategoryManager(Id);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1628);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller Category Manager has been updated successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = {"Regression"},dataProviderClass = SellerDP.class, dataProvider ="disable",description = "1.Disable Warehouse ")
		public void Seller_disableWarehouse(String Id) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.disableWarehouse(Id);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1629);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller warehouse updated successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = {"Regression"},dataProviderClass = SellerDP.class, dataProvider ="disable",description = "1.Disable Financial Info ")
		public void Seller_disableFinanceInfo(String Id) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.disableFinanceInfo(Id);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1641);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller Finance Information updated successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	@Test(groups = {"Regression"},dataProviderClass = SellerDP.class, dataProvider ="disable",description = "1.Disable Payment Config ")
		public void Seller_disablePaymentConfig(String Id) throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException
			{  	
		 		SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
                SellerResponse sellerResponse = sellerServicesHelper.disablePaymentConfig(Id);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1638);
				Assert.assertEquals(sellerResponse.getStatus().getStatusMessage().toString(), "Seller Payment Configuration has been updated successfully");
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
			}
	
	
	@AfterClass(alwaysRun = true)
	public void testAfterClass() throws SQLException {
		deleteSellerRecord(sellerId);
	}
	
	
	// --------------------Delete Record Calls-----------------
		public void deleteSellerRecord(Long sellerId2) throws SQLException {
			deleteSellerConfig(sellerId2);
			deleteSellerItem(sellerId2);
			deleteAddress(sellerId2);
			deleteSellerPaymentConfig(sellerId2);
			deleteSellerKyc(sellerId2);
			deleteSellerBankDetails(sellerId2);
			deleteSellerWarehouse(sellerId2);
			deleteSellerBrand(sellerId2);
			deleteSellerCategoryManager(sellerId2);
			deleteSeller(sellerId2);

			System.out.println("Deletion of all the record which we Inserted Successfull");
		}

		public void deleteSeller(Long sellerId2) throws SQLException {
			String updateSeller = "DELETE from seller where id = " + sellerId2 + "";
			DBUtilities.exUpdateQuery(updateSeller, "seller");
		}

		public void deleteSellerCategoryManager(Long sellerId2) throws SQLException {
			String updateSellerCategoryManager = "DELETE from category_manager where seller_id = " + sellerId2 + "";
			DBUtilities.exUpdateQuery(updateSellerCategoryManager, "seller");
		}

		public void deleteSellerBrand(Long sellerId2) throws SQLException {
			String updateSellerBrand = "DELETE from brand where seller_id = " + sellerId2 + "";
			DBUtilities.exUpdateQuery(updateSellerBrand, "seller");
		}

		public void deleteSellerWarehouse(Long sellerId2) throws SQLException {
			String updateSellerWarehouse = "DELETE from seller_warehouse where seller_id = " + sellerId2 + "";
			DBUtilities.exUpdateQuery(updateSellerWarehouse, "seller");
		}

		public void deleteSellerBankDetails(Long sellerId2) throws SQLException {
			String updateSellerBankDetails = "DELETE from finance_info where seller_id = " + sellerId2 + "";
			DBUtilities.exUpdateQuery(updateSellerBankDetails, "seller");
		}

		public void deleteSellerKyc(Long sellerId2) throws SQLException {
			String updateSellerKyc = "DELETE from kyc_document where seller_id = " + sellerId2 + "";
			DBUtilities.exUpdateQuery(updateSellerKyc, "seller");
		}

		public void deleteSellerPaymentConfig(Long sellerId2) throws SQLException {
			String updateSellerPaymentConfig = "DELETE from payment_configuration where seller_id = " + sellerId2
					+ "";
			DBUtilities.exUpdateQuery(updateSellerPaymentConfig, "seller");
		}
		
		public void deleteAddress(Long sellerId2) throws SQLException {
			String updateSellerAddress = "DELETE from address where seller_id = " + sellerId2
					+ "";
			DBUtilities.exUpdateQuery(updateSellerAddress, "seller");
		}
		
		public void deleteSellerItem(Long sellerId2) throws SQLException {
			String updateSellerItem = "DELETE from seller_item_master where seller_id = " + sellerId2
					+ "";
			DBUtilities.exUpdateQuery(updateSellerItem, "seller");
		}
		
		public void deleteSellerConfig(Long sellerId2) throws SQLException {
			String updateSellerConfig = "DELETE from seller_configuration where seller_id = " + sellerId2
					+ "";
			DBUtilities.exUpdateQuery(updateSellerConfig, "seller");
		}
}
	

