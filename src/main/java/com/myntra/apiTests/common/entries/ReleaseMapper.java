package com.myntra.apiTests.common.entries;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myntra.apiTests.common.Constants.ShipmentSource;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lms.client.response.OrderEntry;
import com.myntra.lms.client.response.OrderResponse;
import com.myntra.lms.client.response.TripOrderAssignmentResponse;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.entry.PacketEntry;

/**
 * Created by Shubham Gupta on 8/2/17.
 */
public class ReleaseMapper {
	
	private static Logger log = LoggerFactory.getLogger(ReleaseMapper.class);

	public List<ReleaseDetailsEntry> mapReleaseToReleasDetail(ReleaseEntryList releaseEntryList, String currentStatus) throws JAXBException, UnsupportedEncodingException {

		List<ReleaseDetailsEntry> releaseDetailsEntries = new ArrayList<>();

		for (ReleaseEntry releaseEntry : releaseEntryList.getReleaseEntries()) {

			ReleaseDetailsEntry releaseDetailsEntry = new ReleaseDetailsEntry();
			OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
			log.info("Mapping Release To ReleasDetail ");
			releaseDetailsEntry.setForce(releaseEntry.getForce());
			releaseDetailsEntry.setReleaseId(releaseEntry.getReleaseId());
			if (currentStatus.equals("afterPK"))
				releaseDetailsEntry.setPacketId(omsServiceHelper.getPacketIdFromReleasId(releaseEntry.getReleaseId()));
			releaseDetailsEntry.setToStatus(releaseEntry.getToStatus());
			releaseDetailsEntry.setTryNBuyEntries(releaseEntry.getTryNBuyEntries());
			if (releaseEntry.getShipmentSource() == ShipmentSource.JABONG) {
				releaseDetailsEntry.setShipmentSource(releaseEntry.getShipmentSource());
			} else
				releaseDetailsEntry.setShipmentSource(ShipmentSource.MYNTRA);

			releaseDetailsEntries.add(releaseDetailsEntry);
		}

		return releaseDetailsEntries;
	}

    public ReleaseDetailsEntry mapReleaseOms(ReleaseDetailsEntry releaseDetailsEntry) throws JAXBException, UnsupportedEncodingException {
       
    	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        
        if(releaseDetailsEntry.getPacketId() != null) {
        	
	        	PacketEntry packet = omsServiceHelper.getPacketEntry(releaseDetailsEntry.getPacketId());   
	        	log.info("mapReleaseOms for packetId "+releaseDetailsEntry.getPacketId());
	        	OrderLineEntry line = omsServiceHelper.getOrderLineEntry(packet.getOrderLines().get(0).getId().toString());
	        releaseDetailsEntry.setWarehouseId(packet.getDispatchWarehouseId().toString());
	        releaseDetailsEntry.setTrackingNumber(packet.getTrackingNo());
	        releaseDetailsEntry.setCourierCode(packet.getCourierCode());
	        releaseDetailsEntry.setOrderId(line.getOrderId().toString());
	        releaseDetailsEntry.setStoreOrderId(line.getStoreOrderId());
	        releaseDetailsEntry.setPincode(packet.getZipcode());
	        releaseDetailsEntry.setPaymentMethod(packet.getPaymentMethod().toString());
	        releaseDetailsEntry.setSupplyType(line.getSupplyType());
	        releaseDetailsEntry.setTryNbuy(line.getIsTryAndBuy());
	        releaseDetailsEntry.setSellerId(line.getSellerId());	     
	        releaseDetailsEntry.setPacketEnable(true);
	        releaseDetailsEntry.setPacketId(packet.getOrderLines().get(0).getPacketId()+"");
        } else {
        	  
        		log.info("mapReleaseOms for releaseId "+releaseDetailsEntry.getReleaseId());
         	OrderReleaseEntry release = omsServiceHelper.getOrderReleaseEntry(releaseDetailsEntry.getReleaseId());
	        OrderLineEntry line = omsServiceHelper.getOrderLineEntry(release.getOrderLines().get(0).getId().toString());
	        releaseDetailsEntry.setWarehouseId(release.getWarehouseId().toString());
	        releaseDetailsEntry.setTrackingNumber(release.getTrackingNo());
	        releaseDetailsEntry.setCourierCode(release.getCourierCode());
	        releaseDetailsEntry.setOrderId(release.getOrderId().toString());
	        releaseDetailsEntry.setStoreOrderId(release.getStoreOrderId());
	        releaseDetailsEntry.setPincode(release.getZipcode());
	        releaseDetailsEntry.setPaymentMethod(release.getPaymentMethod());
	        releaseDetailsEntry.setSupplyType(line.getSupplyType());
	        releaseDetailsEntry.setTryNbuy(line.getIsTryAndBuy());
	        releaseDetailsEntry.setSellerId(line.getSellerId());       	     
        }
        
        return releaseDetailsEntry;
    }

    public ReleaseDetailsEntry mapReleaselms(ReleaseDetailsEntry releaseDetailsEntry) throws InterruptedException, IOException, JAXBException, ManagerException, XMLStreamException, JSONException {
       
    		log.info("Maping Releaselms - "+releaseDetailsEntry.getPacketId());
    	    LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        @SuppressWarnings("unchecked")
		OrderEntry orderLms = ((OrderResponse)lmsServiceHelper.getOrderLMS.apply(releaseDetailsEntry.getPacketId())).getOrders().get(0);
        releaseDetailsEntry.setWarehouseId(orderLms.getWarehouseId().toString());
        releaseDetailsEntry.setTrackingNumber(orderLms.getTrackingNumber());
        releaseDetailsEntry.setCourierCode(orderLms.getCourierOperator());
        releaseDetailsEntry.setPincode(orderLms.getZipcode());
        releaseDetailsEntry.setPaymentMethod(orderLms.getPaymentType());
        releaseDetailsEntry.setShipmentType(orderLms.getShipmentType());
        if (orderLms.getDeliveryCenterId()==null)
            releaseDetailsEntry.setDeliveryCenterId(0);
        else
            releaseDetailsEntry.setDeliveryCenterId(orderLms.getDeliveryCenterId());
        releaseDetailsEntry.setShippingMethod(orderLms.getShippingMethod());
        releaseDetailsEntry.setRtoWarehouse(orderLms.getRtoWarehouseId());
        releaseDetailsEntry.setRtoHubCode(orderLms.getRtoHubCode());
        releaseDetailsEntry.setDispatchHub(orderLms.getDispatchHubCode());
        return releaseDetailsEntry;
    }

    public ReleaseDetailsEntry mapReleaseMasterBag(ReleaseDetailsEntry releaseDetailsEntry) throws InterruptedException, IOException, JAXBException, ManagerException, XMLStreamException, JSONException {
    	
    		log.info("Maping ReleaseMasterBag - "+releaseDetailsEntry.getPacketId());
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        releaseDetailsEntry.setMasterBagId(lmsServiceHelper.getMasterBagID(releaseDetailsEntry.getPacketId()));
        return releaseDetailsEntry;
    }

    public ReleaseDetailsEntry mapReleaseTrip(ReleaseDetailsEntry releaseDetailsEntry) throws InterruptedException, IOException, JAXBException, ManagerException, XMLStreamException, JSONException {
        
    		log.info("Maping ReleaseTrip - "+releaseDetailsEntry.getPacketId());
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        @SuppressWarnings("unchecked")
        OrderEntry orderLms = ((OrderResponse)lmsServiceHelper.getOrderLMS.apply(releaseDetailsEntry.getPacketId())).getOrders().get(0);
        @SuppressWarnings("unchecked")
        TripOrderAssignmentResponse trip = (TripOrderAssignmentResponse) lmsServiceHelper.getTripByTripNumber.apply(orderLms.getTripNumber(),releaseDetailsEntry.getShipmentType());
        releaseDetailsEntry.setWarehouseId(orderLms.getWarehouseId().toString());
        releaseDetailsEntry.setTrackingNumber(orderLms.getTrackingNumber());
        releaseDetailsEntry.setCourierCode(orderLms.getCourierOperator());
        releaseDetailsEntry.setPincode(orderLms.getZipcode());
        releaseDetailsEntry.setPaymentMethod(orderLms.getPaymentType());
        releaseDetailsEntry.setShipmentType(orderLms.getShipmentType());
        if (orderLms.getDeliveryCenterId()==null)
            releaseDetailsEntry.setDeliveryCenterId(0);
        else
            releaseDetailsEntry.setDeliveryCenterId(orderLms.getDeliveryCenterId());
        releaseDetailsEntry.setShippingMethod(orderLms.getShippingMethod());
        releaseDetailsEntry.setRtoWarehouse(orderLms.getRtoWarehouseId());
        releaseDetailsEntry.setRtoHubCode(orderLms.getRtoHubCode());
        releaseDetailsEntry.setDispatchHub(orderLms.getDispatchHubCode());
        releaseDetailsEntry.setTripId(trip.getTripOrders().get(0).getTripId());
        releaseDetailsEntry.setTripOrderAssignmentId(trip.getTripOrders().get(0).getId());
        return releaseDetailsEntry;
    }
}
