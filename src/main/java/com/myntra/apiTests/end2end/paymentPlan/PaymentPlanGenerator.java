package com.myntra.apiTests.end2end.paymentPlan;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.myntra.apiTests.common.entries.CreateOrderEntry;
import com.myntra.apiTests.common.entries.SkuEntry;
import com.myntra.apiTests.end2end.paymentPlanEntries.PaymentPlan;
import com.myntra.apiTests.end2end.paymentPlanEntries.PaymentPlanAndPaymentPlanItems;
import com.myntra.apiTests.end2end.paymentPlanEntries.PaymentPlanExecutionStatus;
import com.myntra.apiTests.end2end.paymentPlanEntries.PaymentPlanInstrumentDetails;
import com.myntra.apiTests.end2end.paymentPlanEntries.PaymentPlanItem;
import com.myntra.apiTests.erpservices.lms.Helper.LMSUtils;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.lordoftherings.boromir.DBUtilities;

public class PaymentPlanGenerator {
	
	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	
	public PaymentPlanAndPaymentPlanItems getPaymentPlan(CreateOrderEntry createOrderEntry){
		
		PaymentPlanAndPaymentPlanItems paymentPlanAndPaymentPlanItems = new PaymentPlanAndPaymentPlanItems();
		
		ArrayList<PaymentPlanInstrumentDetails> paymentPlanInstrumentDetails;
		//ArrayList<PaymentPlanExecutionStatus> paymentPlanExecutionStatusList;
		ArrayList<PaymentPlanItem> paymentPlanItems;
		double totalAmount;
		
		String storeOrderId = new StringBuilder("20").append(LMSUtils.randomGenn(17)).append("001").toString();
		String ppsId = "f"+LMSUtils.randomGenn(3)+"xy"+LMSUtils.randomGenn(2)+"-f"+LMSUtils.randomGenn(2)+"c-9z0r-y"+LMSUtils.randomGenn(2)+"e-"+LMSUtils.randomGenn(7)+"oi7xg";
		
		paymentPlanItems = getPaymentPlanItems(createOrderEntry.getSkuEntries(), 
				createOrderEntry.getShipmentMethod().toString(), createOrderEntry.isGiftWrapEnabled());
		
		totalAmount = getTotalAmount(paymentPlanItems);
		
		PaymentPlanInstrumentType paymentPlanInstrumentType = new PaymentPlanInstrumentType(ppsId, totalAmount);
		
		PaymentPlan paymentPlan = new PaymentPlan();
		paymentPlan.setOrderId(storeOrderId);
		paymentPlan.setId(ppsId);
		paymentPlan.setTotalAmount((long)totalAmount);
		
		paymentPlanInstrumentDetails = paymentPlanInstrumentType.getPaymentPlanInstrumentDetailObjects(createOrderEntry.getPaymentInstruments());
		
		insertPaymentPlan(paymentPlan);
		insertPaymentPlanInstrumentDetails(paymentPlan, paymentPlanInstrumentDetails);
		insertPaymentPlanExecutionStatus(paymentPlanInstrumentDetails);
		updatePaymentPlanInstrumentDetails(paymentPlanInstrumentDetails);
		insertPaymentPlanItem(paymentPlanItems, paymentPlan);
		insertPaymentPlanItemInstrument(totalAmount, paymentPlanInstrumentDetails, paymentPlanItems);
		
		paymentPlanAndPaymentPlanItems.setPaymentPlan(paymentPlan);
		paymentPlanAndPaymentPlanItems.setPaymentPlanItems(paymentPlanItems);
		
		return paymentPlanAndPaymentPlanItems;
	}
	
	public ArrayList<PaymentPlanItem> getPaymentPlanItems(List<SkuEntry> skuEntries, String shipmentMethod, boolean isGiftWrapEnabled){
    	String skuId;
        int styleId;
        double price;
        PaymentPlanItem paymentPlanItem;
        ArrayList<PaymentPlanItem> paymentPlanItems = new ArrayList<>();
        
        for(SkuEntry skuEntry : skuEntries){
        	paymentPlanItem = new PaymentPlanItem();
        
    		skuId = skuEntry.getSkuId()+"";
    		styleId = (int) DBUtilities.exSelectQueryForSingleRecord("select style_id from `mk_styles_options_skus_mapping` where sku_id = "+skuId,"myntra").get("style_id");
    		price = (double) DBUtilities.exSelectQueryForSingleRecord("select price from mk_product_options where style = "+styleId,"myntra").get("price") * 100;
    		
    		paymentPlanItem.setSkuId(skuId);
    		paymentPlanItem.setQuantity(skuEntry.getQuantity());
    		paymentPlanItem.setPricePerUnit(price);
    		paymentPlanItem.setItemType("SKU");
    		
    		paymentPlanItems.add(paymentPlanItem);
    	}
        
        if(shipmentMethod.equalsIgnoreCase("Next Day Delivery") || shipmentMethod.equalsIgnoreCase("Same Day Delivery")){
        	paymentPlanItem = new PaymentPlanItem();
			paymentPlanItem.setSkuId(PaymentPlanConstants.NDD_SDD_SKU);
    		paymentPlanItem.setQuantity(1);
    		paymentPlanItem.setPricePerUnit(PaymentPlanConstants.SHIPPING_CHARGE_AMOUNT);
    		paymentPlanItem.setItemType(PaymentPlanConstants.ITEM_TYPE_SHIPPING_CHARGE);
    		paymentPlanItems.add(paymentPlanItem);
    		
        }else if(shipmentMethod.equalsIgnoreCase("Value shipping")){
        	
        }else{
        	
        }
        
        if(isGiftWrapEnabled){
        	paymentPlanItem = new PaymentPlanItem();
			paymentPlanItem.setSkuId(PaymentPlanConstants.GIFTWRAP_SKU);
    		paymentPlanItem.setQuantity(1);
    		paymentPlanItem.setPricePerUnit(PaymentPlanConstants.GIFTWRAP_CHARGE_AMOUNT);
    		paymentPlanItem.setItemType(PaymentPlanConstants.ITEM_TYPE_GIFTWRAP_CHARGE);
    		paymentPlanItems.add(paymentPlanItem);
			
		}
        
        return paymentPlanItems;
    }
	
	public double getTotalAmount(ArrayList<PaymentPlanItem> paymentPlanItems){
		double totalAmount = 0;
		for(PaymentPlanItem paymentPlanItem : paymentPlanItems){
			totalAmount = totalAmount + paymentPlanItem.getPricePerUnit() * paymentPlanItem.getQuantity();
		}
		return totalAmount;
	}
	
	
	public void insertPaymentPlan(PaymentPlan paymentPlan){
	    
    	String insertQuery = "INSERT INTO `payment_plan` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `login`, `orderId`, `ppsType`, `sourceId`, `state`, `sessionId`, `cartContext`, `totalAmount`, `clientIP`) " +
				"VALUES('"+paymentPlan.getId()+"', 'PPS Plan created', 'SYSTEM', now(), 'SALE', '2aa1d18c.86c9.4eff.88c0.a7ef8c677eb7TK8AVXQJJz', '"+paymentPlan.getOrderId()+"', 'ORDER', " +
				"'e2c75adf-9179-48a8-92c9-94042a055c44', 'PPFSM Order Taking done', 'JJN1c52f20ed63b11e6b03422000a90a0271483946004G', 'DEFAULT', " + paymentPlan.getTotalAmount() + ", '1.1.1.1')";
		DBUtilities.exUpdateQuery(insertQuery, "pps");
    }
	
	public void insertPaymentPlanInstrumentDetails(PaymentPlan paymentPlan, ArrayList<PaymentPlanInstrumentDetails> paymentPlanInstrumentDetails){
	    	String query;
	    	
	    	for(PaymentPlanInstrumentDetails paymentPlanInstrumentDetail : paymentPlanInstrumentDetails){
	    		query = "INSERT INTO `payment_plan_instrument_details` (`comments`, `updatedBy`, `updatedTimestamp`, `paymentInstrumentType`, `totalPrice`, `pps_Id`, `actionType`) VALUES " +
	    				"('PPS Plan Instrument Details created', 'SYSTEM', now(), "+paymentPlanInstrumentDetail.getPaymentInstrumentType()+", " + paymentPlanInstrumentDetail.getTotalPrice() + ", '"+paymentPlan.getId()+"', 'DEBIT')";
	    		DBUtilities.exUpdateQuery(query, "pps");
	    		long id = (long) DBUtilities.exSelectQueryForSingleRecord("select id from payment_plan_instrument_details where pps_Id = '"+paymentPlan.getId()+"' and paymentInstrumentType = "+paymentPlanInstrumentDetail.getPaymentInstrumentType(), "pps").get("id");
	    		paymentPlanInstrumentDetail.setId(id);
	    	}
	}
	
	 public ArrayList<PaymentPlanExecutionStatus> insertPaymentPlanExecutionStatus(ArrayList<PaymentPlanInstrumentDetails> paymentPlanInstrumentDetails){
	    	String query;
	    	PaymentPlanExecutionStatus paymentPlanExecutionStatus;
	    	ArrayList<PaymentPlanExecutionStatus> paymentPlanExecutionStatusList = new ArrayList<>();
	    	
	    	for(PaymentPlanInstrumentDetails paymentPlanInstrumentDetail : paymentPlanInstrumentDetails){
	    		
	    		paymentPlanExecutionStatus = new PaymentPlanExecutionStatus();
	    		
	    		query = "INSERT INTO `payment_plan_execution_status` (`comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `instrumentTransactionId`, `invoker`, `invokerTransactionId`, `numOfRetriesDone`, " +
	    				"`ppsActionType`, `state`, `status`, `paymentPlanInstrumentDetailId`) VALUES	" +
	    				"('Payment Plan Execution Status created', 'SYSTEM', now(), 'DEBIT', 'd35a502f-0785-4d4b-84ab-07b7d64beaec', 'pps', '5bdc8ac0-0b04-42b5-baad-ac41c8c2819b', 0, 'SALE', " +
	    				"'PIFSM Payment Successful', 0, "+paymentPlanInstrumentDetail.getId()+")";
	    		DBUtilities.exUpdateQuery(query, "pps");
	    		Long id = (Long) DBUtilities.exSelectQueryForSingleRecord("select id from payment_plan_execution_status where paymentPlanInstrumentDetailId = "+paymentPlanInstrumentDetail.getId(), "pps").get("id");
	    		paymentPlanInstrumentDetail.setPaymentPlanExecutionStatusId(id+"");
	    		
	    		paymentPlanExecutionStatus.setId(id);
	    		paymentPlanExecutionStatus.setPaymentPlanInstrumentDetailId(paymentPlanInstrumentDetail.getId());
	    		paymentPlanExecutionStatusList.add(paymentPlanExecutionStatus);
	    	}
	    	return paymentPlanExecutionStatusList;
	    }
	
	 
	 public void updatePaymentPlanInstrumentDetails(ArrayList<PaymentPlanInstrumentDetails> paymentPlanInstrumentDetails){
	    	for(PaymentPlanInstrumentDetails paymentPlanInstrumentDetail : paymentPlanInstrumentDetails){
	    		DBUtilities.exUpdateQuery("update payment_plan_instrument_details set paymentPlanExecutionStatus_id = "+paymentPlanInstrumentDetail.getPaymentPlanExecutionStatusId()+" where id = "+paymentPlanInstrumentDetail.getId(), "pps");
	    	}
	 }
	 
	 public void insertPaymentPlanItem(ArrayList<PaymentPlanItem> paymentPlanItems, PaymentPlan paymentPlan){
	    	String query;
	    	long ppItemId;
	    	for(PaymentPlanItem paymentPlanItem : paymentPlanItems){
	    		query = "INSERT INTO `payment_plan_item` (`comments`, `updatedBy`, `updatedTimestamp`, `itemType`, `pricePerUnit`, `quantity`, `sellerId`, `skuId`, `pps_Id`) VALUES " +
						"('Payment Plan Item created', 'SYSTEM', now(), '"+ paymentPlanItem.getItemType() +"', "+ paymentPlanItem.getPricePerUnit() 
						+", "+ paymentPlanItem.getQuantity() +", '21', '"+ paymentPlanItem.getSkuId() +"', '" + paymentPlan.getId() + "')";
	    		DBUtilities.exUpdateQuery(query,"pps");
	    		ppItemId = (long) DBUtilities.exSelectQueryForSingleRecord("select id from `payment_plan_item` where skuId = '"+paymentPlanItem.getSkuId()+"' and pps_id = '"+paymentPlan.getId()+"'","pps").get("id");
	    		paymentPlanItem.setId(ppItemId);;
	    	}
	 }
	 
	 public HashMap<Integer, Double> getPaymentInstrumentPercentage(ArrayList<PaymentPlanInstrumentDetails> paymentPlanInstrumentDetails, double totalAmount){
	    	DecimalFormat df = new DecimalFormat("#.00");
	    	double percentage;
	    	HashMap<Integer, Double> instrumentPricePercentageMap = new HashMap<>();
	    	for(PaymentPlanInstrumentDetails paymentPlanInstrumentDetail : paymentPlanInstrumentDetails){
	    		percentage = (paymentPlanInstrumentDetail.getTotalPrice() / totalAmount) * 100;
	    		df.format(percentage);
	    		instrumentPricePercentageMap.put(paymentPlanInstrumentDetail.getPaymentInstrumentType(), percentage);
	    	}
	    	return instrumentPricePercentageMap;
	    }
	
	 public void insertPaymentPlanItemInstrument(double totalAmount, ArrayList<PaymentPlanInstrumentDetails> paymentPlanInstrumentDetails, 
			 ArrayList<PaymentPlanItem> paymentPlanItems){
	    	double skuLevelAmount;
	    	long instrumentLevelAmount;
	    	String query;
	    	HashMap<Integer, Double> instrumentPricePercentageMap = getPaymentInstrumentPercentage(paymentPlanInstrumentDetails, totalAmount);
	    	
	    	for(PaymentPlanItem paymentPlanItem : paymentPlanItems){
	    		
	    		skuLevelAmount =  paymentPlanItem.getQuantity() * paymentPlanItem.getPricePerUnit();
	    		
	    		for(int instrumentType : instrumentPricePercentageMap.keySet()){
	    			instrumentLevelAmount = (long) ((skuLevelAmount * instrumentPricePercentageMap.get(instrumentType)) / 100);
	    			
	    			query = "INSERT INTO `payment_plan_item_instrument` (`comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, `ppsItemId`) VALUES" +
	    					"('Payment Plan Item Instrument Detail created', 'SYSTEM', now(), "+instrumentLevelAmount+", "+instrumentType+", "+paymentPlanItem.getId()+")";
	    			DBUtilities.exUpdateQuery(query, "pps");
	    		}
	    	}
	    }
	
}
