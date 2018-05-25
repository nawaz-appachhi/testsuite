package com.myntra.apiTests.erpservices.oms;

import java.math.BigDecimal;
import java.util.*;

import com.myntra.oms.client.entry.OrderReleaseEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import com.myntra.lordoftherings.boromir.DBUtilities;

/** The class represents the details of the Order including attributes from
 *  orderLine,OrderRelease for VAT calculation
 * @author puneet.khanna1@myntra.com
 * @since June 2016
 *
 */
public class OrderDetails {
	private static Logger log = LoggerFactory.getLogger(OrderDetails.class);
	
	// used for equality of OrderDetails object
	private  String orderId;
	
	private HashMap<String, Object> orderObj;
	private HashMap<Integer, Integer> orderLineIdQuantityMap;
	private HashMap<Integer, Integer> orderLineIdWareHouseId;
	private HashMap<Integer, HashMap<String, Object>> orderLineIdOrderLineMap;
	private HashMap<Integer, Integer> orderLineIdSkuMap;
	private HashMap<Integer, List<Integer>> skuOrderLinesMap;
	private HashMap<Integer,Integer> orderLineIdReleaseIdMap;

	/** Constructor of the template OrderDetails
	 * @param orderId
	 * @param orderObj
	 * @param orderLineIdQuantityMap
	 * @param orderLineIdWareHouseId
	 * @param orderLineIdOrderLineMap
	 * @param orderLineIdSkuMap
	 */
	public OrderDetails(String orderId, HashMap<String, Object> orderObj,
			HashMap<Integer, Integer> orderLineIdQuantityMap, HashMap<Integer, Integer> orderLineIdWareHouseId,
			HashMap<Integer, HashMap<String, Object>> orderLineIdOrderLineMap,
			HashMap<Integer, Integer> orderLineIdSkuMap,HashMap<Integer,Integer> orderLineIdReleaseIdMap) {
		this.orderId = orderId;
		this.orderObj = orderObj;
		this.orderLineIdQuantityMap = orderLineIdQuantityMap;
		this.orderLineIdWareHouseId = orderLineIdWareHouseId;
		this.orderLineIdOrderLineMap = orderLineIdOrderLineMap;
		this.orderLineIdSkuMap = orderLineIdSkuMap;
		this.orderLineIdReleaseIdMap=orderLineIdReleaseIdMap;
	}


	/*
	Added by Sneha
	So that other classes can access it without params
	 */
	public OrderDetails(){

	}
	
	/** The method returns the quanitites ordered in a orderLine
	 * @param orderLineId
	 * @return
	 */
	private int getQuantityByOrderLine(Integer orderLineId) {
		HashMap<String, Object> orderLine = orderLineIdOrderLineMap.get(orderLineId);
		return ((Integer) orderLine.get("quantity")).intValue();
	}

	

	
	/** The method returns the expected unit tax amount by orderLine 
	 *  This calculates the to be refunded amount for (1-n) quantities of items ordered in an orderLine
	 *  The method also considers the extra charges applicable to order Release
	 * @param orderLine
	 * @return
	 */
	public double getExpectedUnitVatRefundAmountByOrderLine(Integer orderLine) {

		Long correspondingReleaseId = Long.valueOf(orderLineIdReleaseIdMap.get(orderLine));
		log.info("The OrderRelase for "+orderLine+" is "+correspondingReleaseId);
		double taxableAmount = getTaxableAmountOnReleaseForAllQuantities(correspondingReleaseId);
		log.info("The taxable amount is "+taxableAmount);
		double taxRateAppliedInitialy = getTaxRateFromTheCartForTheOrderLineId(orderLine) / 100D;
		log.info("The tax rate applied initialy at the order placement time is "+taxRateAppliedInitialy);
		double taxRatePerWareHouseLocation = getGovtTaxRatePerWareHouseLocationBasisForOrderLine(orderLine) / 100D;
		log.info("The tax rate applied as per rate assigned on the wareHouse is "+taxRatePerWareHouseLocation);
		int quanityPerOrderLine = getQuantityByOrderLine(orderLine);
		log.info("The quanitites per order line are "+quanityPerOrderLine);
		double totalAdditionalChargesByRelaseId = getTotalAdditionalChargesByReleaseId(correspondingReleaseId);
		log.info("The total additional charges by release id   are "+totalAdditionalChargesByRelaseId);
		double refundableAmountOnTaxableAmount = ((taxRateAppliedInitialy * taxableAmount)
				- (taxRatePerWareHouseLocation * taxableAmount)) / quanityPerOrderLine;
		log.info("The total refundable amount On TaxableAmount  excluding additional charges are "+refundableAmountOnTaxableAmount);
		double refundableAmountOnAdditionalCharges = ((taxRateAppliedInitialy * totalAdditionalChargesByRelaseId)
				- (taxRatePerWareHouseLocation * totalAdditionalChargesByRelaseId)) / quanityPerOrderLine;
		log.info("The refundable amount on additional charges   are "+refundableAmountOnAdditionalCharges);
		log.info("The refundable amount at unit level is :: "+(refundableAmountOnTaxableAmount-refundableAmountOnAdditionalCharges));
		return refundableAmountOnTaxableAmount - refundableAmountOnAdditionalCharges;
	}

	public int getOrderReleaseByOrderLineId(Integer orderLineID){
		return (Integer) this.orderLineIdReleaseIdMap.get(orderLineID);
	}
	
	/** Gives stamped govt Tax on an OrderLine, this will be according to the city/State where the wareHouse is
	 * @param orderLineId
	 * @return
	 */
	private double getGovtTaxRatePerWareHouseLocationBasisForOrderLine(Integer orderLineId){
		
		double govtTaxRate=0.0;
		List<HashMap<String, Object>> orderAdditionalInfoList = DBUtilities.exSelectQuery(
				"select `key`,`value` from `order_line_additional_info` where `order_line_id_fk` = " + orderLineId,
				"myntra_oms");
		
		for (HashMap<String, Object> keyValueRow : orderAdditionalInfoList) {
			if (keyValueRow.get("key").equals("GOVT_TAX_RATE")) {
				govtTaxRate = Double.parseDouble( (String)keyValueRow.get("value"));
				break;
			}
		}
		log.info("The  govtTaxRate is "+govtTaxRate);
		return govtTaxRate;
	}
	

	/** This gives the actal refundedAmount by the orderLine ,fetched from order_line_additional_info table
	 * @param orderLine
	 * @return
	 */
	public Double getActualRefundedAmountForOrderLine(Integer orderLine) {
		
		double actualAmountRefunded =0.0;
		List<HashMap<String, Object>> orderAdditionalInfoList = DBUtilities.exSelectQuery(
				"select `key`,`value` from `order_line_additional_info` where `order_line_id_fk` = " + orderLine,
				"myntra_oms");
		
		for (HashMap<String, Object> keyValueRow : orderAdditionalInfoList) {
			if (keyValueRow.get("key").equals("VAT_ADJ_UNIT_REFUND")) {
				System.out.println(keyValueRow.get("value").toString());
				actualAmountRefunded = Double.parseDouble( (String)keyValueRow.get("value"));
				break;
			}
		}
		
		log.info("The actual amount refunded is "+actualAmountRefunded);
		return actualAmountRefunded;

	}
	
	
	/** The method gives the sum of all additional charges per OrderRelease
	 * Please note that the charges returned are not at the unit level(quantity level) 
	 * @param orderReleaseId
	 * @return
	 */
	private double getTotalAdditionalChargesByReleaseId(Long orderReleaseId) {
		return getGiftChargeByRelease(orderReleaseId) + getShippingChargeByRelease(orderReleaseId) +
		        getEmiChargeByRelease(orderReleaseId) + getCodChargeByRelease(orderReleaseId);
		        
	}
	/** The method returns the taxable amount for all quantities ordered in a release
	 * @param orderReleaseId
	 * @return
	 */
	public double getTaxableAmountOnReleaseForAllQuantities(Long orderReleaseId){
		return getMrpTotalByOrderRelease(orderReleaseId) - getDiscountByOrderRelease(orderReleaseId) - 
				getCouponDiscountByOrderRelease(orderReleaseId) - getCartDiscountByOrderRelease(orderReleaseId) ;
	}

	/** The method returns the taxable amount for all quantities ordered in a release where discount <20%
	 * @param orderReleaseEntry
	 * @return
	 * @author Sneha
	 */
	public double getTaxableAmountOnReleaseDiscountLessThan20(OrderReleaseEntry orderReleaseEntry){
		Long orderReleaseId=(orderReleaseEntry.getId());
		Double taxRate = orderReleaseEntry.getOrderLines().get(0).getGovtTaxRate();
		return ((getMrpTotalByOrderRelease(orderReleaseId)+getTotalAdditionalChargesByReleaseId(orderReleaseId)) - (getDiscountByOrderRelease(orderReleaseId) +
				getCouponDiscountByOrderRelease(orderReleaseId) + getCartDiscountByOrderRelease(orderReleaseId)))*100/(100+taxRate);
	}

	/** The method returns the taxable amount for all quantities ordered in a release where discount <20%
	 * @param orderReleaseEntry
	 * @return
	 * @author Sneha
	 */
	public double getTaxableAmountOnReleaseDiscountmoreThan20(OrderReleaseEntry orderReleaseEntry){
		Long orderReleaseId=(orderReleaseEntry.getId());
		Double taxRate = orderReleaseEntry.getOrderLines().get(0).getGovtTaxRate();
		return ((getMrpTotalByOrderRelease(orderReleaseId)+getTotalAdditionalChargesByReleaseId(orderReleaseId)) - (getDiscountByOrderRelease(orderReleaseId) +
				getCouponDiscountByOrderRelease(orderReleaseId) + getCartDiscountByOrderRelease(orderReleaseId)));
	}


	/**The method returns the coupon discount  for all quantities ordered in a release
	 * @param orderReleaseId
	 * @return
	 */
	private double getCouponDiscountByOrderRelease(Long orderReleaseId) {
		Double result=0.0;
		BigDecimal queryResult=(BigDecimal) DBUtilities.exSelectQueryForSingleRecord(
				"select coupon_discount from order_release where id = " + orderReleaseId, "myntra_oms").get("coupon_discount");
		if(null!=queryResult){
			result=queryResult.doubleValue();
		}
		return result;
	}

	
	/**The method returns the cart discount  for all quantities ordered in a release
	 * @param orderReleaseId
	 * @return
	 */
	private double getCartDiscountByOrderRelease(Long orderReleaseId) {
		Double result=0.0;
		BigDecimal queryResult=(BigDecimal) DBUtilities.exSelectQueryForSingleRecord(
				"select cart_discount from order_release where id = " + orderReleaseId, "myntra_oms").get("cart_discount");
		if(null!=queryResult){
			result=queryResult.doubleValue();
		}
		return result;
	}

	
	/** The method returns the coupon discount  for all quantities ordered in a release
	 * @param orderReleaseId
	 * @return
	 */
	private double getMrpTotalByOrderRelease(Long orderReleaseId) {
		Double result=0.0;
		BigDecimal queryResult=(BigDecimal) DBUtilities.exSelectQueryForSingleRecord(
				"select mrp_total from order_release where id = " + orderReleaseId, "myntra_oms").get("mrp_total");
		if(null!=queryResult){
			result=queryResult.doubleValue();
		}
		return result;
	}

	
	/**The method returns the discount  for all quantities ordered in a release
	 * @param orderReleaseId
	 * @return
	 */
	private double getDiscountByOrderRelease(Long orderReleaseId) {
		Double result=0.0;
		BigDecimal queryResult=(BigDecimal) DBUtilities.exSelectQueryForSingleRecord(
				"select discount from order_release where id = " + orderReleaseId, "myntra_oms").get("discount");
		if(null!=queryResult){
			result=queryResult.doubleValue();
		}
		return result;
	}


	/** The method returns the cod charge  for all quantities ordered in a release
	 * @param orderReleaseId
	 * @return
	 */
	private double getCodChargeByRelease(Long orderReleaseId) {
		Double result=0.0;
		BigDecimal queryResult=(BigDecimal) DBUtilities
				.exSelectQueryForSingleRecord("select cod_charge from order_release where id = " + orderReleaseId, "myntra_oms")
				.get("cod_charge");
		if(null!=queryResult){
			result=queryResult.doubleValue();
		}
		return result;
	}

	/** The method returns the emi charge at the release level
	 * @param orderReleaseId
	 * @return
	 */
	private double getEmiChargeByRelease(Long orderReleaseId) {
		Double result=0.0;
		BigDecimal queryResult=(BigDecimal) DBUtilities
				.exSelectQueryForSingleRecord("select emi_charge from order_release where id = " + orderReleaseId, "myntra_oms")
				.get("emi_charge");
		if(null!=queryResult){
			result=queryResult.doubleValue();
		}
		return result;
	}

	/**The method returns the gift charge  at the release level
	 * @param orderReleaseId
	 * @return
	 */
	private double getGiftChargeByRelease(Long orderReleaseId) {
		Double result=0.0;
		BigDecimal queryResult=(BigDecimal) DBUtilities
				.exSelectQueryForSingleRecord("select gift_charge from order_release where id = " + orderReleaseId, "myntra_oms")
				.get("gift_charge");
		if(null!=queryResult){
			result=queryResult.doubleValue();
		}
		return result;
	}

	/**The method returns the shipping charge  at the release level
	 * @param orderReleaseId
	 * @return
	 */
	private double getShippingChargeByRelease(Long orderReleaseId) {
		Double result=0.0;
		BigDecimal queryResult=(BigDecimal) DBUtilities
				.exSelectQueryForSingleRecord("select shipping_charge from order_release where id = " + orderReleaseId, "myntra_oms")
				.get("shipping_charge");
		if(null!=queryResult){
			result=queryResult.doubleValue();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetails other = (OrderDetails) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		return true;
	}

	/**
	 * @return
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @return
	 */
	public HashMap<String, Object> getOrderDetails() {
		return orderObj;
	}

	/**
	 * @return
	 */
	public HashMap<Integer, Integer> getOrderLineIdQuantityMap() {
		return orderLineIdQuantityMap;
	}

	/**
	 * @return
	 */
	public HashMap<Integer, Integer> getOrderLineIdWareHouseId() {
		return orderLineIdWareHouseId;
	}

	/**
	 * @return
	 */
	public HashMap<Integer, HashMap<String, Object>> getOrderLineIdOrderLineMap() {
		return orderLineIdOrderLineMap;
	}

	/** The method returns all the orderLines created for the current order
	 * @return
	 */
	public Set<Integer> getAllOrderLineIDs() {
		Set<Integer> orderLines=(Set<Integer>) orderLineIdOrderLineMap.keySet();
		Assert.assertTrue(orderLines.size()>0,"There are no order Lines in the Order");
		log.info("The distinct order lines created for the order id "+this.getOrderId()+" are :: "+orderLines.toString());
		return orderLines;
	}



	/** The method returned the tax rate assigned by the cart for the orderLine
	 * @param orderLineId
	 * @return
	 */
	private Double getTaxRateFromTheCartForTheOrderLineId(Integer orderLineId) {
		HashMap<String, Object> orderLine = orderLineIdOrderLineMap.get(orderLineId);
		BigDecimal taxRate = (BigDecimal) orderLine.get("tax_rate");
		log.info("The tax rate assigned by the cart for the orderLine "+orderLineId+" is "+taxRate);
		return taxRate.doubleValue();
	}


	@Override
	public String toString() {
		return "OrderDetails [orderId=" + orderId + ", orderObj=" + orderObj + ", orderLineIdQuantityMap="
				+ orderLineIdQuantityMap + ", orderLineIdWareHouseId=" + orderLineIdWareHouseId
				+ ", orderLineIdOrderLineMap=" + orderLineIdOrderLineMap + ", orderLineIdSkuMap=" + orderLineIdSkuMap
				+ ", skuOrderLinesMap=" + skuOrderLinesMap + "]";
	}

	/**
	 * @param orderLineId
	 * @return
	 */
	public int getWareHouseIdByOrderLineId(Integer orderLineId) {
		return orderLineIdWareHouseId.get(orderLineId);
	}

	/** The method returns the SKU by the serving orderLine
	 * @param OrderLineId
	 * @return
	 */
	public Integer getSkuByOrderLine(Integer OrderLineId) {
		return (Integer) this.orderLineIdSkuMap.get(OrderLineId);
	}

	
	
	/** The method checks for every 20 seconds until the VAT refund is created
	 *  	Returns true if refund was reflected in the database before minutesToWait passed
	 *  	Returns false if refund was not reflected in the database before minutesToWait passed
	 * @param orderLineId
	 * @param minutesToWait
	 * @return
	 * @throws InterruptedException
	 */
	public boolean waitAndVerifyIfVatRefundIsInitiated(Integer orderLineId, int minutesToWait)
			throws InterruptedException {
		List resultSet;
		boolean isRefundRequestCreated = false;
		log.info("Going to check if vat refund was initiated for orderLine :: "+orderLineId+ " in  a time frame of next "+minutesToWait +" minutes");
		long endWaitTime = System.currentTimeMillis() + minutesToWait * 60 * 1000;
		resultSet=DBUtilities.exSelectQuery("Select order_release_id_fk,sku_id  from order_line where order_id_fk="+orderLineId+";","myntra_oms");

		// wait and check until isRefundRequestCreated becomes true
		while (!isRefundRequestCreated & System.currentTimeMillis() <= endWaitTime) {
			isRefundRequestCreated = isVatRefundRequestCreated(orderLineId);
			if (isRefundRequestCreated) {
				break;
			} else {
				log.info("Waiting for 20 seconds..to check again if vat refund is initiated for orderLine :: "+orderLineId);
				Thread.sleep(20000l);
			}
		}
		if(isRefundRequestCreated){
			log.info("The refund was initiated for the orderLine"+orderLineId);
		}else{
			log.info("The refund was not initiated for the orderLine"+orderLineId+"in last "+minutesToWait+" minutes");
		}
		return isRefundRequestCreated;

	}



	/** The method checks if the refund was initiated for the passed orderLine
	 * @param orderLine
	 * @return
	 */
	boolean isVatRefundRequestCreated(Integer orderLine,Long releaseID, Long skuID) {
		boolean isCreated = false;
		List<HashMap<String, Object>> orderAdditionalInfoList = DBUtilities.exSelectQuery(
				"select `key`,`value` from `order_line_additional_info` where `order_line_id_fk` =" + orderLine,
				"myntra_oms");
		for (HashMap<String, Object> keyValueRow : orderAdditionalInfoList) {
			// VAT_ADJ_REFUND_TX_REF_ID
			if (keyValueRow.get("key").equals("VAT_ADJ_REFUND_TX_REF_ID")
					& keyValueRow.get("key").equals("VAT_ADJ_REFUND_TX_REF_ID")) {
				isCreated = true;
				log.info("VAT refund was reflected and VAT Adjustment transaction ID was created....");
				break;
			}
		}
		if(isCreated){
			log.info("The VAT refund was not created...");
		}
		return isCreated;

	}

	/** The method checks if the refund was initiated for the passed orderLine
	 * @param orderLine
	 * @return
	 */
	boolean isVatRefundRequestCreated(Integer orderLine) {
		boolean isCreated = false;
		List<HashMap<String, Object>> orderAdditionalInfoList = DBUtilities.exSelectQuery(
				"select `key`,`value` from `order_line_additional_info` where `order_line_id_fk` =" + orderLine,
				"myntra_oms");
		for (HashMap<String, Object> keyValueRow : orderAdditionalInfoList) {
			// VAT_ADJ_REFUND_TX_REF_ID
			if (keyValueRow.get("key").equals("VAT_ADJ_REFUND_TX_REF_ID")
					& keyValueRow.get("key").equals("VAT_ADJ_REFUND_TX_REF_ID")) {
				isCreated = true;
				log.info("VAT refund was reflected and VAT Adjustment transaction ID was created....");
				break;
			}
		}
		if(isCreated){
			log.info("The VAT refund was not created...");
		}
		return isCreated;

	}


	/** The method returns the orderLine data by orderLine id passed
	 * @param orderLineId
	 * @return
	 */
	public HashMap<String, Object> getOrderLineByOrderLineId(Integer orderLineId) {
		return (HashMap<String, Object>) orderLineIdOrderLineMap.get(orderLineId);
	}

	/**  The method returns the taxRate for the wareHouse based the wareHouse 
	 * it is assigned to
	 * @param orderLineId
	 * @return
	 */
	public double getExpectedTaxRateForTheOrderLineId(Integer orderLineId) {
		log.info("Trying to fetch taxRate by orderLine");
		if(null!=this.orderLineIdWareHouseId.get(orderLineId)){
			return TaxationUtil.getTaxRateByWareHouseId(orderLineIdWareHouseId.get(orderLineId).toString());
		}
		log.info("The wareHouse was not assigned to the orderLine "+orderLineId);
		return 0.0;
	}

	/**The method returns all the orderLines which were serving items per SKU
	 * @return
	 */
	public HashMap<Integer, List<Integer>> getOrderLineListperSkuMap() {
		// fetch the list if it is the first time it is fectched ,if not return the already constructed list
		if (null == skuOrderLinesMap) {
			skuOrderLinesMap = new HashMap<Integer, List<Integer>>();
			Set<Integer> orderLines = getAllOrderLineIDs();
			for (Integer orderLine : orderLines) {
				Integer sku = getSkuByOrderLine(orderLine);
				if (!skuOrderLinesMap.containsKey(sku)) {
					List<Integer> matchedOrderLines = new ArrayList<Integer>();
					matchedOrderLines.add(orderLine);
					skuOrderLinesMap.put(sku, matchedOrderLines);
				} else {
					skuOrderLinesMap.get(getSkuByOrderLine(orderLine)).add(orderLine);
				}
			}
		}
		return skuOrderLinesMap;
	}

}
