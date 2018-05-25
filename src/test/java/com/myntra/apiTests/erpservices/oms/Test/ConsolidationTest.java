package com.myntra.apiTests.erpservices.oms.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.javers.common.collections.Arrays;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
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
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.ConsolidationResponseEntry;
import com.myntra.oms.client.entry.ItemGroupEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.test.commons.testbase.BaseTest;

import edu.emory.mathcs.backport.java.util.Collections;

import com.myntra.commons.codes.*;
import com.myntra.commons.codes.StatusResponse.Type;

public class ConsolidationTest extends BaseTest {
	
	static Initialize init = new Initialize("/Data/configuration");
	private static final long serialVersionUID = 1L;
    private static Long vectorSellerID;
    private static Logger log = Logger.getLogger(ConsolidationTest.class);

	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	SoftAssert sft;
	private long sellerId_HEAL=21;
	private String supplyTypeOnHand="ON_HAND";
	private long sellerId_CONS=25;
	private long defaultWaitTime = 5000;
	private Long pincode_560068 = Long.parseLong(OMSTCConstants.Pincodes.PINCODE_560068);
	String login = OMSTCConstants.ConsolidationEngine.firstLogin;
	String password = OMSTCConstants.ConsolidationEngine.firstPassword;
	String secondLogin = OMSTCConstants.ConsolidationEngine.secondLogin;
	String secondPassword = OMSTCConstants.ConsolidationEngine.secondPassword;
	private String uidx;
	private String skuId1= OMSTCConstants.OtherSkus.skuId_3831;
	private String skuId2= OMSTCConstants.OtherSkus.skuId_3832;
	private String skuId3= OMSTCConstants.OtherSkus.skuId_3833;
	private int warehouse28 = OMSTCConstants.WareHouseIds.warehouseId28_GN;
	private int warehouse36 = OMSTCConstants.WareHouseIds.warehouseId36_BW;
	private int warehouse19 = OMSTCConstants.WareHouseIds.warehouseId19_BG;
	ArrayList<String> listOfRelease = null;
	List<List<String>> expectedList = null;

	private int totalNoOfExpectedGroupOfReleases;

	private List<OrderReleaseEntry> orderReleaseEntries;

	private String orderID;
	private String firstCustomerRelease;
	private String secondCustomerRelease;
	private String firstRelease;
	private String secondRelease;
	private String thirdRelease;
	private String orderID2;
	private long delayedTime=5000;
	
	private enum ConsolidationType{
		POSITIVE,INVALID_SKU,INVALID_SKUQTY,INVALID_ORDER
	}
	
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, JAXBException, Exception {
		vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
		refreshApplicationProperty();        
	}
	
	@AfterMethod(alwaysRun=true)
	public void testAfterMethod() throws UnsupportedEncodingException, JAXBException{
		omsServiceHelper.updateApplicationProperty("oms.cross_order_split_enabled", Boolean.TRUE.toString());
		refreshApplicationProperty();
	}
	
	/** Update cross order FG in tools property
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public void updateCrossOrderFG() throws UnsupportedEncodingException, JAXBException{
		omsServiceHelper.updateApplicationProperty("oms.cross_order_split_enabled", Boolean.FALSE.toString());
		refreshApplicationProperty();
	}
	
	/**
	 * This is to update JVM property
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	private void refreshApplicationProperty() throws UnsupportedEncodingException, JAXBException{
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
		End2EndHelper.sleep(delayedTime);
	}
	
	//Passed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC001:C1624 By passing release Which is having only one Item with one quantity. Supply Type On Hand and Courier Code is ML.")
	public void consolidationForSingleRelease() throws Exception {
		
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		sft=new SoftAssert();
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 1)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);
		
		firstRelease = orderReleaseEntries.get(0).getId().toString(); 
		updateDataForRelease(firstRelease,"ML","ON_HAND","21","cod");
		createGroupOfReleasesAndAddToExpectedList(new String[]{firstRelease});
		totalNoOfExpectedGroupOfReleases = 1;
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name());
		verifyConsolidationSuccess(consolidationResponse,totalNoOfExpectedGroupOfReleases,expectedList);
		sft.assertAll();
		
	}
	
	//Passed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC002:C1625 By passing two releases which is having one Item with one quantity each. For both releases SupplyType=ON_HAND and Courier code is ML")
	public void consolidationForTwoRelease() throws Exception {
		
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		sft=new SoftAssert();
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 1),new SkuEntry(skuId2, 1)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1",
				skuId2+":"+warehouse36+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL,
				skuId2+":"+warehouse36+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);
		
		firstRelease = orderReleaseEntries.get(0).getId().toString();
		secondRelease = orderReleaseEntries.get(1).getId().toString(); 
		updateDataForRelease(firstRelease,"ML","ON_HAND","21","cod");
		updateDataForRelease(secondRelease,"ML","ON_HAND","21","cod");
		createGroupOfReleasesAndAddToExpectedList(new String[]{firstRelease,secondRelease});
		totalNoOfExpectedGroupOfReleases = 1;
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationSuccess(consolidationResponse,totalNoOfExpectedGroupOfReleases,expectedList);
		sft.assertAll();
		
	}
	
	//Passed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC003:C1626 By passing two releases which is having one Item with one quantity each. For one release Courier code is ML and EK for other.")
	public void consolidationForTwoReleaseWithDiffCourier() throws Exception {
		
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		sft=new SoftAssert();
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 2)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);
		
		firstRelease = orderReleaseEntries.get(0).getId().toString();
		secondRelease = orderReleaseEntries.get(1).getId().toString();
		updateDataForRelease(firstRelease,"ML","ON_HAND","21","cod");
		updateDataForRelease(secondRelease,"EK","ON_HAND","21","cod");
		createGroupOfReleasesAndAddToExpectedList(new String[]{firstRelease});
		createGroupOfReleasesAndAddToExpectedList(new String[]{secondRelease});
		totalNoOfExpectedGroupOfReleases = 2;
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationSuccess(consolidationResponse,totalNoOfExpectedGroupOfReleases,expectedList);
		sft.assertAll();
		
	}
	
	//Passed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC004:C1627 By passing two releases which is having one Item with one quantity each of different sellers. We need to verify the case of different seller ship together.")
	public void consolidationForTwoReleaseWithDiffSeller() throws Exception {
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		sft=new SoftAssert();
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 2)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);
		
		firstRelease = orderReleaseEntries.get(0).getId().toString();
		secondRelease = orderReleaseEntries.get(1).getId().toString();
		updateDataForRelease(firstRelease,"ML","ON_HAND","21","cod");
		updateDataForRelease(secondRelease,"ML","ON_HAND","25","cod");
		
		createGroupOfReleasesAndAddToExpectedList(new String[]{firstRelease,secondRelease});
		totalNoOfExpectedGroupOfReleases = 1;
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationSuccess(consolidationResponse,totalNoOfExpectedGroupOfReleases,expectedList);
		sft.assertAll();
		
	}
	
	//Passed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC005:C1628 By passing three releases which is having one Item with one quantity each SupplyType=ON_HAND and Courier type for two release is ML and EK for the third.")
	public void consolidationForThreeReleaseWithDiffCourierAndSingleItemEach() throws Exception {
		
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		sft=new SoftAssert();
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 3)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);

		firstRelease = orderReleaseEntries.get(0).getId().toString();
		secondRelease = orderReleaseEntries.get(1).getId().toString();
		thirdRelease = orderReleaseEntries.get(2).getId().toString();
		
		createGroupOfReleasesAndAddToExpectedList(new String[]{firstRelease,secondRelease});
		createGroupOfReleasesAndAddToExpectedList(new String[]{thirdRelease});
		
		updateDataForRelease(firstRelease,"ML","ON_HAND","21","cod");
		updateDataForRelease(secondRelease,"ML","ON_HAND","21","cod");
		updateDataForRelease(thirdRelease,"EK","ON_HAND","21","cod");
		
		totalNoOfExpectedGroupOfReleases = 2;	
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationSuccess(consolidationResponse,totalNoOfExpectedGroupOfReleases,expectedList);
		sft.assertAll();
	}
	
	//Passed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC006:C1629 By passing three releases which is having one Item with one quantity each. Supply Type is JIT for first release and ON_HAND for other two releases.")
	public void consolidationForThreeReleaseWithDiffSupplyTypeAndSingleItemEach() throws Exception {
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		sft=new SoftAssert();
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 3)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);
		
		firstRelease = orderReleaseEntries.get(0).getId().toString();
		secondRelease = orderReleaseEntries.get(1).getId().toString();
		thirdRelease = orderReleaseEntries.get(2).getId().toString();
		updateDataForRelease(firstRelease,"ML","JUST_IN_TIME","21","cod");
		updateDataForRelease(secondRelease,"ML","ON_HAND","21","cod");
		updateDataForRelease(thirdRelease,"ML","ON_HAND","21","cod");
		createGroupOfReleasesAndAddToExpectedList(new String[]{firstRelease,secondRelease,thirdRelease});
		totalNoOfExpectedGroupOfReleases = 1;
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationSuccess(consolidationResponse,totalNoOfExpectedGroupOfReleases,expectedList);
		sft.assertAll();
	}
	
	//Passed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC007:C1630 By passing three releases which is having one Item with one quantity each. Supply Type is JIT for all the releases.")
	public void consolidationForThreeReleaseWithJITSupplyTypeAndSingleItemEach() throws Exception {
		
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		sft=new SoftAssert();
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 3)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);
		
		firstRelease = orderReleaseEntries.get(0).getId().toString();
		secondRelease = orderReleaseEntries.get(1).getId().toString();
		thirdRelease = orderReleaseEntries.get(2).getId().toString();
		updateDataForRelease(firstRelease,"ML","JUST_IN_TIME","21","cod");
		updateDataForRelease(secondRelease,"ML","JUST_IN_TIME","21","cod");
		updateDataForRelease(thirdRelease,"ML","JUST_IN_TIME","21","cod");
		createGroupOfReleasesAndAddToExpectedList(new String[]{firstRelease,secondRelease,thirdRelease});
		totalNoOfExpectedGroupOfReleases = 1;
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationSuccess(consolidationResponse,totalNoOfExpectedGroupOfReleases,expectedList);		
		sft.assertAll();
	}
	
	//Failed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC008:C1631 By passing two releases where one release belongs to different order and other belongs to different")
	public void consolidationForTWOReleaseOFDIFFOrderWithDiffSeller() throws Exception {
		
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		sft=new SoftAssert();
		updateCrossOrderFG();
		
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 1)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);


		orderReleaseEntries = createOrderHelper(login, password, pincode_560068, skuEntries);
		firstCustomerRelease = orderReleaseEntries.get(0).getId().toString();
		updateDataForRelease(firstCustomerRelease,"ML","ON_HAND","21","cod");
		
		orderReleaseEntries = createOrderHelper(login, password, pincode_560068, skuEntries);
		secondCustomerRelease = orderReleaseEntries.get(0).getId().toString();
		updateDataForRelease(secondCustomerRelease,"ML","ON_HAND","25","cod");
		
		createGroupOfReleasesAndAddToExpectedList(new String[]{firstCustomerRelease,secondCustomerRelease});
		totalNoOfExpectedGroupOfReleases = 1;
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationSuccess(consolidationResponse,totalNoOfExpectedGroupOfReleases,expectedList);
		sft.assertAll();
	}
	
	//Failed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC009:C1632 By Passing two releases where one release belongs to different order and other belongs to different order of a same customer with a same courier code")
	public void consolidationForTWOReleaseOFDIFFOrderSameCustomer() throws Exception {
		
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		sft=new SoftAssert();
		updateCrossOrderFG();
		
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 1)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login, password, pincode_560068, skuEntries);
		firstCustomerRelease = orderReleaseEntries.get(0).getId().toString();
		updateDataForRelease(firstCustomerRelease,"ML","ON_HAND","21","cod");

		orderReleaseEntries = createOrderHelper(login, password, pincode_560068, skuEntries);
		secondCustomerRelease = orderReleaseEntries.get(0).getId().toString();
		updateDataForRelease(secondCustomerRelease,"ML","ON_HAND","21","cod");
		
		createGroupOfReleasesAndAddToExpectedList(new String[]{firstCustomerRelease,secondCustomerRelease});
		totalNoOfExpectedGroupOfReleases = 1;
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationSuccess(consolidationResponse,totalNoOfExpectedGroupOfReleases,expectedList);
		sft.assertAll();
	}
	
	//Failed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC010:C1633 Try to consolidate two orders one is having COD as payment mode and other one is having Wallet or Online as payment mode")
	public void consolidationForTWOReleaseOFDIFFPaymentMethod() throws Exception {
		
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		sft=new SoftAssert();
		updateCrossOrderFG();
		
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 1)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login, password, pincode_560068, skuEntries);
		firstCustomerRelease = orderReleaseEntries.get(0).getId().toString();
		updateDataForRelease(firstCustomerRelease,"ML","ON_HAND","21","cod");
		
		orderReleaseEntries = createOrderHelper(login, password, pincode_560068, skuEntries);
		secondCustomerRelease = orderReleaseEntries.get(0).getId().toString();
		updateDataForRelease(secondCustomerRelease,"ML","ON_HAND","21","on");
		
		createGroupOfReleasesAndAddToExpectedList(new String[]{firstCustomerRelease,secondCustomerRelease});
		totalNoOfExpectedGroupOfReleases = 1;
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationSuccess(consolidationResponse,totalNoOfExpectedGroupOfReleases,expectedList);
		sft.assertAll();
	}
	
	//Need to check
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC011:C1634 By passing release Which is having only one Item with one quantity. SupplyType=ON HAND and item price is greater then Courier COD Limit")
	public void consolidationForSingleReleaseWithCourierCODLimit() throws Exception {
		
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		sft=new SoftAssert();
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 1)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		CreateOrderEntry createOrderEntry = new CreateOrderEntryBuilder(login,password,pincode_560068,skuEntries).build();
		orderID = end2EndHelper.createOrder(createOrderEntry);
		List<OrderReleaseEntry> orderReleaseEntries = omsServiceHelper.getOrderEntry(orderID).getOrderReleases();
		for(OrderReleaseEntry orderReleaseEntry:orderReleaseEntries){
			listOfRelease.add(orderReleaseEntry.getId().toString());
		}
		
		totalNoOfExpectedGroupOfReleases = 1;
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationSuccess(consolidationResponse,totalNoOfExpectedGroupOfReleases,expectedList);
		sft.assertAll();
	}
	
	//Need to check
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC012:C1635 By passing two releases which is having One Item With One Quantity each --SupplyType=ON HAND--Same Courier code.(Courier Cod Limit reached)")
	public void consolidationForTwoReleaseWithCourierCODLimit() throws Exception {
		
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		
		sft=new SoftAssert();
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 1)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		CreateOrderEntry createOrderEntry = new CreateOrderEntryBuilder(login,password,pincode_560068,skuEntries).build();
		String orderID = end2EndHelper.createOrder(createOrderEntry);
		List<OrderReleaseEntry> orderReleaseEntries = omsServiceHelper.getOrderEntry(orderID).getOrderReleases();
		for(OrderReleaseEntry orderReleaseEntry:orderReleaseEntries){
			listOfRelease.add(orderReleaseEntry.getId().toString());
		}
		
		totalNoOfExpectedGroupOfReleases = 1;
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationSuccess(consolidationResponse,totalNoOfExpectedGroupOfReleases,expectedList);
		sft.assertAll();
	}
	
	//Failed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC013:C1636 Check Consolidation of a non existent Release id.")
	public void consolidationForNonExistenceReleaseId() throws Exception {
		sft=new SoftAssert();
		
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		
		String nonExistenceReleaseId = "11111";
		listOfRelease.add(nonExistenceReleaseId);
		createGroupOfReleasesAndAddToExpectedList(new String[]{nonExistenceReleaseId});
		
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.INVALID_ORDER.name() );
		verifyConsolidationError(consolidationResponse);
		sft.assertAll();
		
	}
	
	//Passed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC014:C1637 By passing two releases where one release is ON HOLD due to any On Hold reason.")
	public void consolidationForTwoReleaseWithSingleReleaseOnHold() throws Exception {
		sft=new SoftAssert();
		listOfRelease = new ArrayList<>();
		
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 2)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);
		
		firstRelease = orderReleaseEntries.get(0).getId().toString();
		secondRelease = orderReleaseEntries.get(1).getId().toString();
		omsServiceHelper.updateOnHoldForReleaseId(firstRelease,"34");
		
		updateDataForRelease(firstRelease,"ML","ON_HAND","21","cod");
		updateDataForRelease(secondRelease,"ML","ON_HAND","21","cod");
		
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationError(consolidationResponse);
		sft.assertAll();
		
	}
	
	//Passed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC015:C1638 By passing two releases where both releases are ON HOLD due to any On Hold Reason_ Consolidation should not be allowed")
	public void consolidationForTwoReleaseWithBothReleaseOnHold() throws Exception {
		
		sft=new SoftAssert();
		listOfRelease = new ArrayList<>();
		
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 2)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);
		
		firstRelease = orderReleaseEntries.get(0).getId().toString();
		secondRelease = orderReleaseEntries.get(1).getId().toString();
		omsServiceHelper.updateOnHoldForReleaseId(firstRelease,"34");
		omsServiceHelper.updateOnHoldForReleaseId(secondRelease,"34");
		
		updateDataForRelease(firstRelease,"ML","ON_HAND","21","cod");
		updateDataForRelease(secondRelease,"ML","ON_HAND","21","cod");
		
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationError(consolidationResponse);
		sft.assertAll();
		
	}
	
	//Passed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC016:C1639 By passing invalid SKU id in the request")
	public void consolidationForInvalidSkuId() throws Exception {
		
		sft=new SoftAssert();
		listOfRelease = new ArrayList<>();
		
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 1)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);
		
		firstRelease = orderReleaseEntries.get(0).getId().toString();
		
		updateDataForRelease(firstRelease,"ML","ON_HAND","21","cod");
		
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.INVALID_SKU.name() );
		verifyConsolidationError(consolidationResponse);
		sft.assertAll();
		
	}
	
	//Passed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC017:C1640 sku quantity in the request for a release mismatch with what is present in OMS")
	public void consolidationForInvalidSkuQuantity() throws Exception {
		
		sft=new SoftAssert();
		listOfRelease = new ArrayList<>();
		
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 1)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);
		
		firstRelease = orderReleaseEntries.get(0).getId().toString();
		
		updateDataForRelease(firstRelease,"ML","ON_HAND","21","cod");
		
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.INVALID_SKUQTY.name() );
		verifyConsolidationError(consolidationResponse);
		sft.assertAll();
		
	}
	
	//Failed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC018:C1641 By passing two release id's of a different customer")
	public void consolidationForTwoReleaseOfDiffCustomer() throws Exception {
		
		sft=new SoftAssert();
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 1)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);
		firstCustomerRelease = orderReleaseEntries.get(0).getId().toString();
		
		orderReleaseEntries = createOrderHelper(secondLogin,secondPassword,pincode_560068,skuEntries);
		secondCustomerRelease = orderReleaseEntries.get(0).getId().toString();
		
		updateDataForRelease(firstCustomerRelease,"ML","ON_HAND","21","cod");
		updateDataForRelease(secondCustomerRelease,"ML","ON_HAND","21","cod");

		createGroupOfReleasesAndAddToExpectedList(new String[]{firstCustomerRelease});
		createGroupOfReleasesAndAddToExpectedList(new String[]{secondCustomerRelease});
		
		totalNoOfExpectedGroupOfReleases = 2;
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationSuccess(consolidationResponse, totalNoOfExpectedGroupOfReleases,expectedList);
		
		sft.assertAll();
		
	}
	
	//Passed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC019:C1656 Validate consolidation for Jabong orders")
	public void consolidationForJabongOrders() throws Exception {
		
		sft=new SoftAssert();
		listOfRelease = new ArrayList<>();
		
		String jabongReleaseId = getOrderReleaseFromDBWithoutOnHoldOfParticularType("JABONG");
		updateDataForRelease(jabongReleaseId,"ML","JUST_IN_TIME","21","cod");
		listOfRelease.add(jabongReleaseId);
		
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationError(consolidationResponse);
		sft.assertAll();
		
	}
	
	//Passed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC020:C1657 Validate consolidation for Flipkart Orders.")
	public void consolidationForFlipkartOrders() throws Exception {
		
		sft=new SoftAssert();
		listOfRelease = new ArrayList<>();
		
		String flipkartReleaseId = getOrderReleaseFromDBWithoutOnHoldOfParticularType("FLIPKART");
		updateDataForRelease(flipkartReleaseId,"EK","ON_HAND","19","cod");
		listOfRelease.add(flipkartReleaseId);
		
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationError(consolidationResponse);
		sft.assertAll();
		
	}
	
	//Failed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC021:C1658 Validate consolidation for PPMP sellers.")
	public void consolidationForPPMPSellerOrders() throws Exception {
		
		sft=new SoftAssert();
		listOfRelease = new ArrayList<>();
		
		String flipkartReleaseId = getOrderReleaseFromDBWithoutOnHoldOfParticularType("PPMP");
		updateDataForRelease(flipkartReleaseId,"ML","ON_HAND","105","cod");
		listOfRelease.add(flipkartReleaseId);
		
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationError(consolidationResponse);
		sft.assertAll();
		
	}
	
	//Failed
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC022:C1659 Try to consolidate two orders both are having Online as payment mode. Courier Level Cod limit should not be applied.")
	public void consolidationForOnLineOrders() throws Exception {
		
		sft=new SoftAssert();
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		updateCrossOrderFG();
		
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 1)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);
		firstCustomerRelease = orderReleaseEntries.get(0).getId().toString();
		
		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);
		secondCustomerRelease = orderReleaseEntries.get(0).getId().toString();
		
		updateDataForRelease(firstCustomerRelease,"ML","ON_HAND","21","on");
		updateDataForRelease(secondCustomerRelease,"ML","ON_HAND","21","on");
		listOfRelease.add(firstCustomerRelease);
		listOfRelease.add(secondCustomerRelease);
		
		createGroupOfReleasesAndAddToExpectedList(new String[]{firstCustomerRelease,secondCustomerRelease});
		totalNoOfExpectedGroupOfReleases = 1;
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationSuccess(consolidationResponse, totalNoOfExpectedGroupOfReleases,expectedList);
		sft.assertAll();
		
	}
	
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC022:C1659 Try to consolidate two orders both are having Online as payment mode. Courier Level Cod limit should not be applied.")
	public void consolidationForTryAndBuyOrdersMultyQty() throws Exception {
		
		sft=new SoftAssert();
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 2)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);
		firstCustomerRelease = orderReleaseEntries.get(0).getId().toString();
		secondCustomerRelease = orderReleaseEntries.get(1).getId().toString();
		
		String lineId = ""+omsServiceHelper.getOrderReleaseEntry(firstCustomerRelease).getOrderLines().get(0).getId();
		omsServiceHelper.updateOrderLineAdditionInfo(EnumSCM.TRY_AND_BUY, Boolean.TRUE.toString(), lineId);
		
		lineId = ""+omsServiceHelper.getOrderReleaseEntry(secondCustomerRelease).getOrderLines().get(0).getId();
		omsServiceHelper.updateOrderLineAdditionInfo(EnumSCM.TRY_AND_BUY, Boolean.TRUE.toString(), lineId);
		
		updateDataForRelease(firstCustomerRelease,"ML","ON_HAND","21","on");
		updateDataForRelease(secondCustomerRelease,"ML","ON_HAND","21","on");
		listOfRelease.add(firstCustomerRelease);
		listOfRelease.add(secondCustomerRelease);
		
		createGroupOfReleasesAndAddToExpectedList(new String[]{firstCustomerRelease,secondCustomerRelease});
		totalNoOfExpectedGroupOfReleases = 1;
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationSuccess(consolidationResponse, totalNoOfExpectedGroupOfReleases,expectedList);
		sft.assertAll();
		
	}
	
	@Test(enabled = true,groups={"regression","sanity","consolidation"}, description = "TC022:C1659 Try to consolidate two orders both are having Online as payment mode. Courier Level Cod limit should not be applied.")
	public void consolidationForPartialTryAndBuyItemsOrdersMultyQty() throws Exception {
		
		sft=new SoftAssert();
		listOfRelease = new ArrayList<>();
		expectedList = new ArrayList<List<String>>();
		
		SkuEntry[] skuEntries = {new SkuEntry(skuId1, 1),new SkuEntry(skuId2, 1)};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL+":1",
				skuId2+":"+warehouse28+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{skuId1+":"+warehouse28+":100:0:"+sellerId_HEAL,
				skuId2+":"+warehouse28+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login,password,pincode_560068,skuEntries);
		firstCustomerRelease = orderReleaseEntries.get(0).getId().toString();
		secondCustomerRelease = orderReleaseEntries.get(1).getId().toString();
		
		String lineId = ""+omsServiceHelper.getOrderReleaseEntry(firstCustomerRelease).getOrderLines().get(0).getId();
		omsServiceHelper.updateOrderLineAdditionInfo(EnumSCM.TRY_AND_BUY, Boolean.TRUE.toString(), lineId);
		
		lineId = ""+omsServiceHelper.getOrderReleaseEntry(secondCustomerRelease).getOrderLines().get(0).getId();
		omsServiceHelper.updateOrderLineAdditionInfo(EnumSCM.TRY_AND_BUY, Boolean.FALSE.toString(), lineId);
		
		updateDataForRelease(firstCustomerRelease,"ML","ON_HAND","21","on");
		updateDataForRelease(secondCustomerRelease,"ML","ON_HAND","21","on");
		listOfRelease.add(firstCustomerRelease);
		listOfRelease.add(secondCustomerRelease);
		
		createGroupOfReleasesAndAddToExpectedList(new String[]{firstCustomerRelease});
		createGroupOfReleasesAndAddToExpectedList(new String[]{secondCustomerRelease});
		totalNoOfExpectedGroupOfReleases = 2;
		ConsolidationResponseEntry consolidationResponse = omsServiceHelper.getAllConsolidatedLines(listOfRelease,ConsolidationType.POSITIVE.name() );
		verifyConsolidationSuccess(consolidationResponse, totalNoOfExpectedGroupOfReleases,expectedList);
		sft.assertAll();
		
	}

	

	/**
	 * This function verify Success while doing consolidation, also validates number of groups
	 * @param consolidationResponse
	 * @param expectedTotalNumberOfGroup
	 * @param expectedList 
	 */
	private void verifyConsolidationSuccess(ConsolidationResponseEntry consolidationResponse, int expectedTotalNumberOfGroup, List<List<String>> expectedList) {
		// TODO Auto-generated method stub
		sft.assertEquals(consolidationResponse.getStatus().getStatusType(), Type.SUCCESS,"There should be success in Response");
		int actualNumberOfGroups = consolidationResponse.getItemGroups().size();
		sft.assertEquals(actualNumberOfGroups, expectedTotalNumberOfGroup,"Total number of groups are wrong Actual:"+actualNumberOfGroups);
		sft.assertTrue(verifyConsolidationList(consolidationResponse,expectedList),"Grouping of releases in not correct");
	}
	
	public Boolean verifyConsolidationList(ConsolidationResponseEntry consolidationResponse, List<List<String>> expectedList){
		List<List<String>> groupsOfReleases = new ArrayList<>();
		Boolean isGroupsAreSame = false;
		
		for(ItemGroupEntry itemGroupEntry:consolidationResponse.getItemGroups()){
			List<String> releaseList = new ArrayList<>();
			for(OrderReleaseEntry orderReleaseEntry:itemGroupEntry.getOrderReleaseEntries()){
				releaseList.add(orderReleaseEntry.getId().toString());
			}
			
			Collections.sort(releaseList);
			groupsOfReleases.add(releaseList);
		}
		
		Collections.sort(groupsOfReleases,new CustomComparator());
		Collections.sort(expectedList,new CustomComparator());
		
		log.info("Actual List: "+groupsOfReleases);
		log.info("Expected List: "+expectedList);
		
		if(groupsOfReleases.equals(expectedList)){
			log.info("ActualList and ExpectedList are same");
			isGroupsAreSame = true;
		}
		
		return isGroupsAreSame;
		
	}
	
	/**
	 * This function verify error while doing consolidation
	 * @param consolidationResponse
	 */
	private void verifyConsolidationError(ConsolidationResponseEntry consolidationResponse) {
		// TODO Auto-generated method stub
		sft.assertEquals(consolidationResponse.getStatus().getStatusType(), Type.ERROR,"There should be error in Response");

	}
	
	/**
	 * This function is to get order release of particulat type without onhold, e.g jabong, flipkart
	 * @param orderType
	 * @return
	 */
	private String getOrderReleaseFromDBWithoutOnHoldOfParticularType(String orderType){
		String query = null;
		if(orderType.equalsIgnoreCase("PPMP")){
			query = "select * from `order_release` where  `id` = (select `order_release_id_fk`"
					+ " from `order_line` where `seller_id`='105' limit 1);";
		}else{
			query = "select * from `order_release` where `is_on_hold`=0 and `login` like '"+orderType+"' limit 1;";
		}
		
        Map<String,Object> orderReleaseDB = DBUtilities.exSelectQueryForSingleRecord(query,"myntra_oms");
        return orderReleaseDB.get("id").toString();
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
		orderReleaseEntries = addingReleasesToListAndFetchingReleaseEntries(orderID);
		return orderReleaseEntries;
	}
	
	/**
	 * This is to add create list of releaseIds
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	public List<OrderReleaseEntry> addingReleasesToListAndFetchingReleaseEntries(String orderID) throws Exception{
		
		List<OrderReleaseEntry> orderReleaseEntries = omsServiceHelper.getOrderEntry(orderID).getOrderReleases();
		for(OrderReleaseEntry orderReleaseEntry:orderReleaseEntries){
			listOfRelease.add(orderReleaseEntry.getId().toString());
		}
		
		return orderReleaseEntries;
	}
	
	/**
	 * This is to update data in order_line
	 * @param orderReleaseId
	 * @param courierCode
	 * @param supplyType
	 * @param sellerId
	 */
	public void updateDataForRelease(String orderReleaseId,String courierCode,String supplyType,String sellerId,String paymentMethod){
		String query = "update `order_line` set `courier_code`='"+courierCode+"', `supply_type`='"+supplyType+"', "
				+ "`seller_id`='"+sellerId+"',`payment_method`='"+paymentMethod+"' where `order_release_id_fk` ='"+orderReleaseId+"'";
		DBUtilities.exUpdateQuery(query, "myntra_oms");
	}
	
	/**
	 * @param releasesList
	 * @return
	 */
	private void createGroupOfReleasesAndAddToExpectedList(String[] releasesList) {
		// TODO Auto-generated method stub
		List<String> groupOfReleases = new ArrayList<>();
		for(String releaseId:releasesList ){
			groupOfReleases.add(releaseId);
		}
		Collections.sort(groupOfReleases);
		expectedList.add(groupOfReleases);
	}

	

}

class CustomComparator implements Comparator<List<String>> {
	@Override
	public int compare(List<String> o1, List<String> o2) {
		String firstString_o1 = o1.get(0);
		String firstString_o2 = o2.get(0);
		return firstString_o1.compareTo(firstString_o2);
	}
}