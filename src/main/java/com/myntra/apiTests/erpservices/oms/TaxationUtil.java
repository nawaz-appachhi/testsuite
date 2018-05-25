package com.myntra.apiTests.erpservices.oms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.testng.Assert;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.VatTestConstants;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.response.OrderResponse;

/**
 * @author puneet.khanna1@myntra.com
 * @since : June 2016 Description
 * 
 *        The Utility class provides methods to place Orders in OMS ,construct
 *        and return distinct OrderDetails object per order which stores
 *        attributes of the order at orderLine & orderRelease level
 */
public final class TaxationUtil {

	
	private static Logger log = Logger.getLogger(TaxationUtil.class);
	private static OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	private static End2EndHelper e2eHelper = new End2EndHelper();
	private static OrderEntry orderEntry;
	private static String orderReleaseId;
	private static Long vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
	private static String supplyTypeOnHand= "ON_HAND";
	private static IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	

	
	private TaxationUtil() {
		// restrict initialization
	}

	/**
	 * The method returns the taxRate for the wareHouse based o n from which
	 * city it is assigned to,if the City Name could not retrieved from
	 * getCityByWareHouseId , the returned taxRate is 0.0
	 * 
	 * @param wareHouseId
	 * @return (double) @see The VAT rate configured to the warehouse based on city/State
	 */
	public static double getTaxRateByWareHouseId(String wareHouseId) {
		double taxRate = 0;
		String cityOfTheWareHouse = getCityByWareHouseId(wareHouseId);
		if (null != cityOfTheWareHouse) {
			if (cityOfTheWareHouse.equals("Bangalore")) {
				taxRate = 14.5;
			} else if (cityOfTheWareHouse.equals("Gurgaon")) {
				taxRate = 5.25;
			}
		}
		log.info("The retrived taxRate for the warehouse id  " + wareHouseId + "from the city " + cityOfTheWareHouse
				+ " is " + taxRate);
		return taxRate;
	}

	/**
	 * The method returns the city name assigned to the @param warehouse Id
	 * assigned in myntra.wms/core_warehouses table
	 * 
	 * @param wareHouseId
	 * @return String ,null if the city could not be retrived by wareHouseId @see myntra.wms/core_warehouses table
	 */
	public static String getCityByWareHouseId(String wareHouseId) {
		String cityName = null;
		HashMap<String, Object> resultMap = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord(
				"select city from core_warehouses where id = " + wareHouseId, "myntra_wms");
		if (null != resultMap & !resultMap.isEmpty()) {
			cityName = (String) resultMap.get("city");
		} else {
			log.info("Could not retrive CityName for warehouseId " + wareHouseId);
		}
		return cityName;

	}

	/**
	 * The method cancels and order and verifies the cancel event
	 * 
	 * @param orderID
	 * @param login
	 * @return true if order was cancelled successfully ,else false
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 * @throws ManagerException 
	 * @see JAXBException , UnsupportedEncodingException
	 */
	public static boolean cancelOrderAndConfirmCancellation(String orderID, String login)
			throws UnsupportedEncodingException, JAXBException, ManagerException {
		boolean orderIsCancelled = false;
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderReleaseId, "1", login, "TestOrder Cancellation");
		Assert.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
		Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
		Assert.assertEquals(cancellationRes.getData().get(0).getOrderReleases().get(0).getStatus(), "F");
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"F");
		orderIsCancelled = true;
		return orderIsCancelled;
	}

	/**
	 * The method places an order and returns the
	 * com.myntra.apiTests.helper.erpservices.oms.helper.OrderDetails object
	 * 
	 * @param username
	 * @param password
	 * @param addressID
	 * @param skuIdAndQuantities
	 * @param couponId
	 * @param isGiftWrapOpted
	 * @param giftcard
	 * @return OrderDetails object ,null if the order was not placed
	 *         successfully
	 * @throws Exception
	 */
	public static OrderDetails placeAnOrderAndReturnOrderDetails(String username, String password, String addressID,
			String[] skuIdAndQuantities, String couponId, boolean isCashback, boolean areLoyaltyPointsUsed,
			boolean isGiftWrapOpted, String giftcard) throws Exception {
		log.info("is CashBackOrder "+isCashback);
		String orderId = e2eHelper.createOrder(username, password, addressID, skuIdAndQuantities, couponId, isCashback,
				areLoyaltyPointsUsed, isGiftWrapOpted, giftcard,false);
		return returnOrderDetails(orderId);

	}

	/**
	 * The method constructs the OrderDetails method based on the orderId passed
	 * 
	 * @param orderID
	 * @return OrderDetails , null if @param orderID is null or empty
	 * @throws InterruptedException
	 *             as the method implicitly sleeps for 30 seconds for order
	 *             placement events to finish
	 * @throws JAXBException 
	 * @throws UnsupportedEncodingException 
	 * @throws NumberFormatException 
	 * @see InterruptedException for more details
	 */
	private static OrderDetails returnOrderDetails(String orderID) throws InterruptedException, NumberFormatException, UnsupportedEncodingException, JAXBException {
		OrderDetails order = null;
		if (null == orderID & orderID.isEmpty()) {
			log.info("The order Id was not created...Hence returning order Details as null...");
			return order;
		}

		HashMap<Integer, Integer> orderLineIdQuantityMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> orderLineIdWareHouseId = new HashMap<Integer, Integer>();
		HashMap<Integer, HashMap<String, Object>> orderLineIdOrderLineMap = new HashMap<Integer, HashMap<String, Object>>();
		HashMap<Integer, Integer> orderLineIdSkuMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> orderLineIdReleaseIdMap = new HashMap<Integer, Integer>();

		// The call getOrderReleaseIDAndWareHouseIdMapByOrderId internally waits
		// untill wareHouse assignment is complete for the Order
		//orderEntry = omsServiceHelper.getOrderEntry(Long.parseLong(orderID));
		//orderReleaseId = orderEntry.getOrderReleases().get(0).getId();
		List<HashMap<Integer, Object>> orderReleaseId_WHListForOrder = getOrderReleaseIDAndWareHouseIdMapByOrderId(
				""+orderID);

		HashMap<String, Object> orderObj = getOrderDetailsByOrderId(orderID);

		for (HashMap<Integer, Object> orderReleaseRow : orderReleaseId_WHListForOrder) {

			Long orderRelaseId = (Long) orderReleaseRow.get("id");
			int orderReleaseIdInInt=orderRelaseId.intValue();
			for (HashMap<String, Object> tempOrderLineIdAndQtyMap : getOrderLineId_QtyByOrderReleaseId(
					orderRelaseId)) {

				Long orderLineId = (Long) tempOrderLineIdAndQtyMap.get("id");
				int orderLineIdinInt=orderLineId.intValue();
				orderLineIdReleaseIdMap.put(orderLineIdinInt, orderReleaseIdInInt);
				orderLineIdSkuMap.put(orderLineIdinInt, getSkuByOrderLineID(orderLineIdinInt));
				orderLineIdQuantityMap.put(orderLineIdinInt, (Integer) tempOrderLineIdAndQtyMap.get("quantity"));
				orderLineIdWareHouseId.put(orderLineIdinInt, (Integer) orderReleaseRow.get("warehouse_id"));
				orderLineIdOrderLineMap.put(orderLineIdinInt, getOrderLineByOrderLineId(orderLineIdinInt));

			}
		}

		return new OrderDetails(orderID, orderObj, orderLineIdQuantityMap, orderLineIdWareHouseId,
				orderLineIdOrderLineMap, orderLineIdSkuMap, orderLineIdReleaseIdMap);

	}

	/**
	 * The method checks if wareHouse is assigned after every minute for
	 * minutesToWait passed if minutesToWait is null, the default wait time is 5
	 * minutes
	 * 
	 * @param orderRelaseId
	 * @throws InterruptedException
	 */
	private static boolean waitUntilTheWareHouseIsAssignedToTheOrderRelease(long orderRelaseId,
			Integer minutesToWait) throws InterruptedException {
		if(null==minutesToWait || minutesToWait==0){
			log.info("As the expected time was less than average time taken, minutesToWait is set to" +
					VatTestConstants.WAITTIME_FOR_WH_ASSIGNMENT +" minutes (default value)");
			minutesToWait=VatTestConstants.WAITTIME_FOR_WH_ASSIGNMENT;
		}
		log.info("The test will wait for " + minutesToWait
				+ " minutes for wareHouse assignment to complete for order release " + orderRelaseId);
		boolean isAssigned = false;
		long endWaitTime = System.currentTimeMillis() + minutesToWait * 60 * 1000;
		// wait and check until isRefundRequestCreated becomes true
		while (!isAssigned & System.currentTimeMillis() <= endWaitTime) {
			isAssigned = isWareHouseAssignedToTheOrderRelase(orderRelaseId);
			if (isAssigned) {
				break;
			} else {
				log.info("Waiting for 20 seconds..to check again if wareHouse is assigned to order release :: "
						+ orderRelaseId);
				Thread.sleep(20000l);
			}
		}
		if (isAssigned) {
			log.info("The warehouse is assigned...");
		} else {
			log.info("The warHouse was not assigned to order release " + orderRelaseId + " in last " + minutesToWait
					+ " minutes");
		}
		return isAssigned;

	}

	/**
	 * @param orderRelaseId
	 * @return
	 */
	private static boolean isWareHouseAssignedToTheOrderRelase(long orderRelaseId) {
		boolean isAssigned = false;
		List<HashMap<Integer, Object>> result = DBUtilities
				.exSelectQuery("select id,warehouse_id from order_release where id =" + orderRelaseId, "myntra_oms");
		if (null != result & !result.isEmpty()) {
			for (HashMap<Integer, Object> orderReleaseRow : result) {
				if (null != (Integer) orderReleaseRow.get("warehouse_id")) {
					isAssigned = true;
				}

			}
		}
		return isAssigned;
	}

	/**
	 * The method returns the Sku code for the corresponding orderLine,returns
	 * null if skuCode is not retrieved
	 * 
	 * @param orderLineId
	 * @return Integer @see myntra_oms/order_line table
	 */
	private static Integer getSkuByOrderLineID(Integer orderLineId) {
		Integer skuCode = null;
		HashMap<String, Object> resultMap = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord(
				"select `sku_id` from `order_line` where id = " + orderLineId + "", "myntra_oms");
		if (null != resultMap & !resultMap.isEmpty()) {
			skuCode = (Integer) resultMap.get("sku_id");
		} else {
			log.info("Could not retrive skuCode for orderLineId " + orderLineId);
		}
		return skuCode;
	}

	/**
	 * The method returns the orderLine details for the passed OrderLineId
	 * 
	 * @param orderLineId
	 * @return orderLine @see myntra_oms/order_line table
	 */
	private static HashMap<String, Object> getOrderLineByOrderLineId(Integer orderLineId) {
		HashMap<String, Object> orderLine = null;
		List<HashMap<String, Object>> orderLineResult = DBUtilities
				.exSelectQuery("select * from order_line where id =" + orderLineId, "myntra_oms");
		if (null != orderLineResult & !orderLineResult.isEmpty()) {
			orderLine = (HashMap<String, Object>) orderLineResult.get(0);
		} else {
			log.info("Could not retrieve orderLine map for orderLine " + orderLineId);
		}
		return orderLine;

	}

	/**
	 * The method returns order details mapped with OrderId passed
	 * 
	 * @param orderID
	 * @return
	 * @see
	 */
	private static HashMap<String, Object> getOrderDetailsByOrderId(String orderID) {

		HashMap<String, Object> order = null;
		List<HashMap<String, Object>> orderResult = DBUtilities
				.exSelectQuery("select * from orders where id =" + orderID, "myntra_oms");
		if (null != orderResult & !orderResult.isEmpty()) {
			order = orderResult.get(0);
		} else {
			log.info("Could not retrieve Order map for orderId " + orderID);
		}
		return order;
	}

	/**
	 * The method returns quanity per orderId mapped with OrderId passed
	 * 
	 * @param orderReleaseId
	 * @return
	 */
	private static List<HashMap<String, Object>> getOrderLineId_QtyByOrderReleaseId(Long orderReleaseId) {
		List<HashMap<String, Object>> orderLineIdAndQuantityMapList = null;
		List<HashMap<String, Object>> result = DBUtilities.exSelectQuery(
				"select id,quantity from order_line where order_release_id_fk =" + orderReleaseId, "myntra_oms");
		if (null != result & !result.isEmpty()) {
			orderLineIdAndQuantityMapList = result;
		} else {
			log.info("Could not retrieve OrderLineId and qty map for orderReleaseId  " + orderReleaseId);
		}
		return orderLineIdAndQuantityMapList;
	}

	/**
	 * The method returns order release details mapped with OrderId passed after
	 * all releases get wareHouses assigned If one of the release could not get
	 * a wareHouse assigns Assert exception is thrown and abort happens for the
	 * test
	 * 
	 * @param orderID
	 * @return
	 * @throws InterruptedException
	 */
	private static List<HashMap<Integer, Object>> getOrderReleaseIDAndWareHouseIdMapByOrderId(String orderID)
			throws InterruptedException {
		log.info("Waiting for 30  seconds for order relases to be identified by OMS ");
		Thread.sleep(30000l);
		List<HashMap<Integer, Object>> orderReleaseIdAndWareHouseIdMapList = null;

		assertIfWareHouseIsAsignedToAllReleasesByOrderId(orderID);

		// When the control comes to this line ,it means that the wareHouse
		// assignments were complete , re-read the DB and return updated data
		orderReleaseIdAndWareHouseIdMapList = DBUtilities
				.exSelectQuery("select id,warehouse_id from order_release where order_id_fk =" + orderID, "myntra_oms");

		return orderReleaseIdAndWareHouseIdMapList;

	}

	/**
	 * If one of the release could not get a wareHouse assigns Assert exception
	 * is thrown and abort happens for the test
	 * 
	 * @param orderID
	 * @throws InterruptedException
	 */
	private static void assertIfWareHouseIsAsignedToAllReleasesByOrderId(String orderID) throws InterruptedException {

		List<HashMap<Integer, Object>> result = DBUtilities
				.exSelectQuery("select id,warehouse_id from order_release where order_id_fk =" + orderID, "myntra_oms");

		// verify that the wareHouse assignment is done for all the releases of
		// the Order
		if (null != result ) 
			if(!result.isEmpty()){

			// call waitUntilTheWareHouseIsAssignedToTheOrderRelease for every
			// release
			for (HashMap<Integer, Object> orderReleaseRow : result) {

				long orderRelease = (long) orderReleaseRow.get("id");
				// verify if the wareHouse assignment is done for the release
				Assert.assertTrue(waitUntilTheWareHouseIsAssignedToTheOrderRelease(orderRelease, 6),
						"The wareHouse assignment was not done for orderRelease " + orderRelease);
			}
		}

	}

	/**
	 * Adds additionalInventoryCount items for sku in the atp inventory database
	 * with by increasing DB:myntra_atp/Table:inventory/Column:inventory_count
	 * with existing Db:myntra_atp/Table:inventory/Column: blocked_order_count
	 * plus @param additionalInventoryCountForTest
	 * 
	 * @param sku
	 * @param additionalInventoryCount
	 */
	public static void updateAtp(Integer sku, int additionalInventoryCount) {

		int inventoryCount = (Integer) DBUtilities
				.exSelectQueryForSingleRecord("select  blocked_order_count from inventory where sku_id=" + sku,
						"myntra_atp")
				.get("blocked_order_count") + additionalInventoryCount;
		DBUtilities.exUpdateQuery(
				"UPDATE `inventory` SET `inventory_count` = " + inventoryCount + " where sku_id = " + sku,
				"myntra_atp");

	}
	

	/**
	 * Sets up the inventory by updating atp and Ims tables across wareHouseIds
	 * passed
	 * 
	 * @param additionalInventoryCountForTest
	 * @param wareHouseIds
	 * @param sku
	 */
	public static void setUpInventoryForTheSkuUnderTest(int additionalInventoryCountForTest, String[] wareHouseIds,
			Integer sku) {
		if ((null == wareHouseIds & wareHouseIds.length == 0)
				& (wareHouseIds.length < additionalInventoryCountForTest)) {
			log.info(
					"Inventory cannot be set , the wareHouse ids for which th inventory is to be set are unknown or there are less number items to distribute than the warHouse count ");
			return;
		}
		updateAtp(sku, additionalInventoryCountForTest);
		for (String wareHouseId : wareHouseIds)
			imsServiceHelper.updateInventoryForSeller(new String[] {sku+":"+wareHouseId+":"+additionalInventoryCountForTest+":"+vectorSellerID},supplyTypeOnHand);
		//updateIms(additionalInventoryCountForTest, sku, wareHouseIds);
	}

	/**
	 * Evenly distributes M items to N wareHouses if (M%N) is zero If (M%N) is
	 * not zero, Distributes (M-(M%N)) among warehouse ids evenly & then
	 * increments first wareHouse Id in param wareHouseIds by additional (M%N)
	 * 
	 * @param additionalInventoryCountForTest
	 * @param sku
	 * @param wareHouseIds
	 */
	public static void updateIms(int additionalInventoryCountForTest, Integer sku, String[] wareHouseIds) {
		log.info("Dividing " + additionalInventoryCountForTest + " items among " + wareHouseIds.length + " wareHouses");
		Assert.assertTrue(wareHouseIds.length>0, "The warehouse ids were not more than 1");
		int numOfWareHouseIds = wareHouseIds.length;
		int distributionPerWareHouse = additionalInventoryCountForTest / numOfWareHouseIds;
		int expectedBalanceAfterEqualDistribution = additionalInventoryCountForTest % numOfWareHouseIds;

		if (expectedBalanceAfterEqualDistribution != 0) {

			for (String wareHouseId : wareHouseIds) {
				int blockedCount = (Integer) DBUtilities
						.exSelectQueryForSingleRecord("select  blocked_order_count from wh_inventory where sku_id = "
								+ sku.toString().trim() + " and warehouse_id = " + wareHouseId.trim(), "myntra_ims")
						.get("blocked_order_count");
				int inventoryCount = blockedCount + distributionPerWareHouse;
				DBUtilities.exUpdateQuery("UPDATE `wh_inventory` SET `inventory_count` = " + inventoryCount
						+ " where sku_id = " + sku + " and warehouse_id = " + wareHouseId.trim(), "myntra_ims");
			}
			String wareHouseId = wareHouseIds[0];
			int presentCountForSkuPerWareHouse = (Integer) DBUtilities
					.exSelectQueryForSingleRecord("select  `inventory_count` from `wh_inventory` where sku_id = " + sku
							+ " and warehouse_id = " + wareHouseId.trim(), "myntra_ims")
					.get("inventory_count");
			int inventoryCount = presentCountForSkuPerWareHouse + expectedBalanceAfterEqualDistribution;
			DBUtilities.exUpdateQuery("UPDATE `wh_inventory` SET `inventory_count` = " + inventoryCount
					+ " where sku_id = " + sku + " and warehouse_id=" + wareHouseId.trim(), "myntra_ims");
		} else if (expectedBalanceAfterEqualDistribution == 0) {
			for (String wareHouseId : wareHouseIds) {
				log.info("Updating the wareHouse id "+wareHouseId);
					
				HashMap<String, Object> queryResultToFetchBlockedCountInIms= (HashMap<String, Object>) DBUtilities
						.exSelectQueryForSingleRecord("select  blocked_order_count from wh_inventory where sku_id = "
								+ sku + " and warehouse_id = " + wareHouseId.trim(), "myntra_ims");
				Assert.assertTrue(queryResultToFetchBlockedCountInIms.size()>0, 
						"Please passed the correct sku or WareHouse Id As,The sku "+sku+" is not mapped to wareHouse Id "+wareHouseId+" in IMS..aborting....");
				int blockedCount =(Integer)queryResultToFetchBlockedCountInIms
						.get("blocked_order_count");
				int inventoryCount = blockedCount + distributionPerWareHouse;
				DBUtilities.exUpdateQuery("UPDATE `wh_inventory` SET `inventory_count` = " + inventoryCount
						+ " where sku_id = " + sku + " and warehouse_id = " + wareHouseId.trim(), "myntra_ims");
			}

		}

	}

	/**
	 * The method returns MRP by Sku
	 * 
	 * @param sku
	 * @return mrp
	 */
	public static double getMrpBySku(Integer sku) {
		double unitMrp = 0.0;
		unitMrp = (double) DBUtilities.exSelectQueryForSingleRecord(
				"select `mrp` from `core_pi_skus` where `parent_sku_id` = " + sku, "myntra_wms").get("mrp");
		Assert.assertTrue(unitMrp != 0.0, "The unitMrp is zero for sku " + sku);
		return unitMrp;
	}

	/**
	 * @param uIdx
	 * @throws IOException
	 * @throws SQLException
	 * @throws JAXBException 
	 */
	public static void setUpInventoryForTheOrder(VatTestData data, String uIdx) throws SQLException, IOException, JSONException, JAXBException {
		List<SkuTriplet> skuTripletList = data.getSkuTripletList();
		for (SkuTriplet skuTriplet : skuTripletList) {
			setUpInventoryForTheSkuUnderTest(skuTriplet.getQuanity(), skuTriplet.getSkuWareHouseIdsAsArray(),
					Integer.valueOf(skuTriplet.getSkuCode()));

		}
		if (data.isCashbackOrder()) {
			e2eHelper.updateloyalityAndCashBack(uIdx, 500, (int) data.getCashBackRequiredToPlaceTheCompleteOrder());
		}
	}

	public static String[] getskuIdAndQuantityTupleArray(VatTestData data) {
		List<SkuTriplet> listofSkuTriplets = data.getSkuTripletList();
		List<String> skuIdAndQuantityTupleList = new ArrayList<String>();

		for (int i = 0; i < listofSkuTriplets.size(); i++) {
			SkuTriplet skutriplet = (SkuTriplet) listofSkuTriplets.get(i);
			skuIdAndQuantityTupleList
					.add(skutriplet.getSkuCode().toString().trim() + ":" + skutriplet.getQuanity().toString().trim());
		}
		String[] skuIdAndQuantityTupleArray = new String[skuIdAndQuantityTupleList.size()];
		for (int i = 0; i < skuIdAndQuantityTupleList.size(); i++) {
			skuIdAndQuantityTupleArray[i] = (String) skuIdAndQuantityTupleList.get(i);
		}

		return skuIdAndQuantityTupleArray;
	}
}
