package com.myntra.apiTests.common.ProcessOrder.ServiceImpl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.Constants.ShipmentSource;
import com.myntra.apiTests.common.entries.ReleaseDetailsEntry;
import com.myntra.apiTests.common.entries.TryNBuyEntry;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.TMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lms.client.response.TripResponse;
import com.myntra.lms.client.status.OrderShipmentAssociationStatus;
import com.myntra.lms.client.status.ShipmentStatus;
import com.myntra.logistics.platform.domain.ShipmentUpdateEvent;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.oms.client.entry.OrderLineEntry;

/**
 * Created by Shubham Gupta on 8/3/17.
 */
public class ProcessReleaseLMSHelper {
	
	private static Logger log = Logger.getLogger(ProcessReleaseLMSHelper.class);

    @SuppressWarnings("unchecked")
	public void processFromClosedToUnassignedForML(ReleaseDetailsEntry releaseEntry) throws InterruptedException, JAXBException, IOException, XMLStreamException, ManagerException, JSONException {
        
    		log.info("processFromClosedToUnassignedForML for packetId - "+releaseEntry.getPacketId());
    		TMSServiceHelper tmsServiceHelper = new TMSServiceHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        LMSHelper lmsHelper = new LMSHelper();
        
        tmsServiceHelper.processInTMSFromClosedToLastMileInTransit.accept(releaseEntry.getMasterBagId());
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), EnumSCM.SHIPPED, 3),
                " Checking the OrderStatus moved to SH in LMS");
        if (releaseEntry.getShipmentSource()== ShipmentSource.MYNTRA) { 
        	
        	    if(releaseEntry.isPacketEnable()) {
        	    	
        	    		ExceptionHandler.handleTrue(omsServiceHelper.validatePacketStatusInOMS(releaseEntry.getPacketId(), EnumSCM.S, 2),
                            " Checking the OrderStatus moved to SH in OMS");
        	    } else {
        	    	
        	    		ExceptionHandler.handleTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseEntry.getReleaseId(), EnumSCM.SH, 2),
                    " Checking the OrderStatus moved to SH in OMS");
        	    }
        }
        ExceptionHandler.handleEquals(lmsServiceHelper.receiveShipmentFromMasterbag(releaseEntry.getMasterBagId()).
                getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive shipment in DC");
        ExceptionHandler.handleEquals(lmsServiceHelper.masterBagInScanUpdate(releaseEntry.getMasterBagId(), releaseEntry.getPacketId(), "Bangalore",
                releaseEntry.getDeliveryCenterId(), "DC", Long.parseLong(releaseEntry.getWarehouseId())).
                getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive master bag in DC");
        ExceptionHandler.handleEquals(lmsHelper.getMasterBagStatus(releaseEntry.getMasterBagId()),EnumSCM.RECEIVED,
                "Masterbag status is not update in DB to `RECEIVED`");
        ExceptionHandler.handleEquals(lmsHelper.getMLShipmentStatus(releaseEntry.getPacketId()), EnumSCM.UNASSIGNED,
                "Shipment Status is not update to UNASSIGENED in ML shipment2");
    }

    @SuppressWarnings("unchecked")
	public void processFromClosedtoSHforDE_RHD(ReleaseDetailsEntry releaseEntry) throws InterruptedException, JAXBException, IOException, XMLStreamException, ManagerException, JSONException {
        
    		log.info("processFromClosedtoSHforDE_RHD for packetId - "+releaseEntry.getPacketId());
    		TMSServiceHelper tmsServiceHelper = new TMSServiceHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        LMSHelper lmsHelper = new LMSHelper();
        tmsServiceHelper.processInTMSFromClosedToLastMileInTransit.accept(releaseEntry.getMasterBagId());
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), EnumSCM.SHIPPED, 2),
                " Checking the OrderStatus moved to SH in LMS");
		if (releaseEntry.getShipmentSource() == ShipmentSource.MYNTRA) {
			
			if (releaseEntry.isPacketEnable()) {

				ExceptionHandler.handleTrue(
						omsServiceHelper.validatePacketStatusInOMS(releaseEntry.getPacketId(), EnumSCM.S, 2),
						" Checking the OrderStatus moved to SH in OMS");
			} else {

				ExceptionHandler.handleTrue(
						omsServiceHelper.validateReleaseStatusInOMS(releaseEntry.getReleaseId(), EnumSCM.SH, 2),
						" Checking the OrderStatus moved to SH in OMS");
			}
		}
            
        ExceptionHandler.handleEquals(lmsServiceHelper.receiveShipmentFromMasterbag(releaseEntry.getMasterBagId()).
                getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive shipment in DC");
        ExceptionHandler.handleEquals(lmsServiceHelper.masterBagInScanUpdate(releaseEntry.getMasterBagId(), releaseEntry.getPacketId(), "DC-Delhi",
                releaseEntry.getDeliveryCenterId(), "DC", Long.parseLong(releaseEntry.getWarehouseId()),
                ShipmentStatus.RECEIVED_AT_HANDOVER_CENTER, OrderShipmentAssociationStatus.RECEIVED_AT_HANDOVER_CENTER).
                getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive master bag in DC");
        ExceptionHandler.handleEquals(lmsHelper.getMasterBagStatus(releaseEntry.getMasterBagId()), EnumSCM.RECEIVED_AT_HANDOVER_CENTER,
                "MasterBag Status is not updated to RECEIVED_AT_HANDOVER_CENTER");
        ExceptionHandler.handleEquals(lmsServiceHelper.handoverToRegionalCourier(releaseEntry.getMasterBagId()).getStatus().getStatusType().toString(),
                EnumSCM.SUCCESS, "Unable to Handover masterbag to 3PL from regional handover DC");
        ExceptionHandler.handleEquals(lmsHelper.getMasterBagStatus(releaseEntry.getMasterBagId()), EnumSCM.HANDED_OVER_TO_3PL);
    }

    public void processFromClosedToSHfor3PL(ReleaseDetailsEntry releaseEntry) throws InterruptedException, JAXBException, IOException, XMLStreamException, ManagerException, JSONException {
        
    		log.info("processFromClosedToSHfor3PL for packetId - "+releaseEntry.getPacketId());
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        LMSHelper lmsHelper = new LMSHelper();
        ExceptionHandler.handleEquals(lmsServiceHelper.shipMasterBag(releaseEntry.getMasterBagId()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        ExceptionHandler.handleEquals(lmsHelper.getMasterBagStatus(releaseEntry.getMasterBagId()), EnumSCM.HANDED_OVER_TO_3PL,
                "MasterBag DB status is not updated to `HANDED_OVER_TO_3PL`");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), ReleaseStatus.SHIPPED.name(), 4));
        if (releaseEntry.getShipmentSource()==ShipmentSource.MYNTRA){
	        	if(releaseEntry.isPacketEnable()) {
	    	    	
		    		ExceptionHandler.handleTrue(omsServiceHelper.validatePacketStatusInOMS(releaseEntry.getPacketId(), EnumSCM.S, 2),
	                    " Checking the OrderStatus moved to SH in OMS");
		    } else {
		    	
		    		ExceptionHandler.handleTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseEntry.getReleaseId(), EnumSCM.SH, 2),
	            " Checking the OrderStatus moved to SH in OMS");
		    }
        }
    }

    public void processFromSHtoOFD3PL(ReleaseDetailsEntry releaseEntry) throws IOException, JAXBException, ManagerException {
         	
    		log.info("processFromSHtoOFD3PL for packetId - "+releaseEntry.getPacketId());
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        ExceptionHandler.handleEquals(lmsServiceHelper.ctsShipmentUpdate(releaseEntry.getPacketId(), releaseEntry.getCourierCode(), releaseEntry.getTrackingNumber(),
                ShipmentUpdateEvent.OUT_FOR_DELIVERY, releaseEntry.getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(),
                ReleaseStatus.OUT_FOR_DELIVERY.name(), 2), "LMS status not OUT_FOR_DELIVERY");
    }

    public void processFromReceivedToOFD_ML(ReleaseDetailsEntry releaseEntry) throws IOException, JAXBException, ManagerException {
       
    		log.info("processFromReceivedToOFD_ML for packetId - "+releaseEntry.getPacketId());
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        long deliveryStaffID = lmsServiceHelper.getDeliveryStaffID(releaseEntry.getDeliveryCenterId());
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(releaseEntry.getDeliveryCenterId(), deliveryStaffID);
        ExceptionHandler.handleEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        ExceptionHandler.handleEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, releaseEntry.getTrackingNumber()).getStatus().getStatusType().toString(),
                EnumSCM.SUCCESS, "Unable to addAndOutScanNewOrderToTrip");
        ExceptionHandler.handleEquals(lmsServiceHelper.getOrderStatusFromML(releaseEntry.getPacketId()),EnumSCM.ASSIGNED_TO_SDA);
        ExceptionHandler.handleEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(),
                EnumSCM.SUCCESS, "Unable to startTrip");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInML(releaseEntry.getPacketId(), ReleaseStatus.OUT_FOR_DELIVERY.name(),1),
                "After trip start order in ML is not `OUT_FOR_DELIVERY`");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(),ReleaseStatus.OUT_FOR_DELIVERY.name(),2),
                "After trip start order in LMS is not `OUT_FOR_DELIVERY`");
    }

    public void processFromSHtoRTOforML(ReleaseDetailsEntry releaseEntry) throws IOException, JAXBException, ManagerException {
       
    		log.info("processFromSHtoRTOforML for packetId - "+releaseEntry.getPacketId());
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        String confirmRTOResponse = lmsServiceHelper.mlShipmentUpdate(releaseEntry.getTrackingNumber(),
                releaseEntry.getDeliveryCenterId(), null, EnumSCM.RTO_CONFIRMED, EnumSCM.DL);
        ExceptionHandler.handleEquals(APIUtilities.getElement(confirmRTOResponse,"mlShipmentResponse.status.statusType","json"),
                EnumSCM.SUCCESS,"Unable to update ML for RTO_CONFIRMED event");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), EnumSCM.RTO_CONFIRMED,3),
                "Order not marked RTO in order_to_ship");
        if (releaseEntry.getShipmentSource()==ShipmentSource.MYNTRA) {
        	
	        	if(releaseEntry.isPacketEnable()) {
	    	    	
	        		ExceptionHandler.handleTrue(omsServiceHelper.validatePacketStatusInOMS(releaseEntry.getPacketId(), EnumSCM.RTO, 5),
	                        "Order not marked RTO in packet");
		    } else {
		    	
		    		ExceptionHandler.handleTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseEntry.getReleaseId(), EnumSCM.RTO, 2),
	            " Checking the OrderStatus moved to SH in OMS");
		    }
        }
            
    }

    public void processToRTO3PL(ReleaseDetailsEntry releaseEntry) throws IOException, JAXBException, ManagerException {
        
    		log.info("processToRTO3PL for packetId - "+releaseEntry.getPacketId());
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        ExceptionHandler.handleEquals(lmsServiceHelper.ctsShipmentUpdate(releaseEntry.getPacketId(), releaseEntry.getCourierCode(), releaseEntry.getTrackingNumber(),
                ShipmentUpdateEvent.RTO_CONFIRMED, releaseEntry.getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), EnumSCM.RTO_CONFIRMED,4),
                "Order not marked RTO in order_to_ship");
		if (releaseEntry.getShipmentSource() == ShipmentSource.MYNTRA) {

			if (releaseEntry.isPacketEnable()) {

				ExceptionHandler.handleTrue(
						omsServiceHelper.validatePacketStatusInOMS(releaseEntry.getPacketId(), EnumSCM.RTO, 5),
						"Order not marked RTO in packet");
			} else {

				ExceptionHandler.handleTrue(
						omsServiceHelper.validateReleaseStatusInOMS(releaseEntry.getReleaseId(), EnumSCM.RTO, 2),
						" Checking the OrderStatus moved to SH in OMS");
			}
		}
    }

    public void processFromFDtoRTOforML(ReleaseDetailsEntry releaseEntry) throws IOException, JAXBException, ManagerException {
        
    		log.info("processFromFDtoRTOforML for packetId - "+releaseEntry.getPacketId());
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        ExceptionHandler.handleEquals(lmsServiceHelper.markOrderRto(releaseEntry.getPacketId(), releaseEntry.getTrackingNumber()).getStatus().getStatusType().toString(),
                EnumSCM.SUCCESS,"Unable to update ML for RTO_CONFIRMED event");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), EnumSCM.RTO_CONFIRMED,3),
                "Order not marked RTO in order_to_ship");
        if (releaseEntry.getShipmentSource() == ShipmentSource.MYNTRA) {

			if (releaseEntry.isPacketEnable()) {

				ExceptionHandler.handleTrue(
						omsServiceHelper.validatePacketStatusInOMS(releaseEntry.getPacketId(), EnumSCM.RTO, 5),
						"Order not marked RTO in packet");
			} else {

				ExceptionHandler.handleTrue(
						omsServiceHelper.validateReleaseStatusInOMS(releaseEntry.getReleaseId(), EnumSCM.RTO, 2),
						" Checking the OrderStatus moved to SH in OMS");
			}
		}
    }

    public void processFromOFDtoFDforML(ReleaseDetailsEntry releaseEntry) throws IOException, JAXBException, ManagerException {
        
    		log.info("processFromOFDtoFDforML for packetId - "+releaseEntry.getPacketId());
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        ExceptionHandler.handleEquals(lmsServiceHelper.updateOrderInTrip(releaseEntry.getTripOrderAssignmentId(),
                EnumSCM.FAILED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Update order In trip failed in LMS");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInML(releaseEntry.getPacketId(), ReleaseStatus.FAILED_DELIVERY.name(), 1),
                "Order status is not in FAILED_DELIVERY in ML");
        ExceptionHandler.handleEquals(lmsServiceHelper.updateOrderInTrip(releaseEntry.getTripOrderAssignmentId(),
                EnumSCM.FAILED, EnumSCM.TRIP_COMPLETE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Trip complete failed in LMS");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInML(releaseEntry.getPacketId(), ReleaseStatus.RECEIVED_IN_DC.name(), 1),
                "Order status is not in FAILED_DELIVERY in ML");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), ReleaseStatus.FAILED_DELIVERY.name(), 3),
                "Order status is not in FAILED_DELIVERY in LMS");
    }

    public void processFromOFDtoFDfor3PL(ReleaseDetailsEntry releaseEntry) throws IOException, JAXBException, ManagerException {
        
    		log.info("processFromOFDtoFDfor3PL for packetId - "+releaseEntry.getPacketId());
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        ExceptionHandler.handleEquals(lmsServiceHelper.ctsShipmentUpdate(releaseEntry.getPacketId(), releaseEntry.getCourierCode(), releaseEntry.getTrackingNumber(),
                ShipmentUpdateEvent.FAILED_DELIVERY, releaseEntry.getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), ReleaseStatus.FAILED_DELIVERY.name(), 4),
                "Order status is not in FAILED_DELIVERY in LMS");
    }

    public void processFromOFDtoSMDLforML(ReleaseDetailsEntry releaseEntry) throws IOException, JAXBException, ManagerException {
       
    		log.info("processFromOFDtoSMDLforML for packetId - "+releaseEntry.getPacketId());
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        ExceptionHandler.handleEquals(lmsServiceHelper.selfMarkDL(releaseEntry.getPacketId()).getStatus().getStatusType().toString(),
                EnumSCM.SUCCESS, "Unable to update orders via self mark");
        ExceptionHandler.handleEquals(lmsServiceHelper.updateOrderInTrip(releaseEntry.getTripOrderAssignmentId(), EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE).
                getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to complete Trip ");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), EnumSCM.DELIVERED, 2), "Update trip order api failed for marking Delivered in LMS");
		if (releaseEntry.getShipmentSource() == ShipmentSource.MYNTRA) {

			if (releaseEntry.isPacketEnable()) {

				ExceptionHandler.handleTrue(
						omsServiceHelper.validatePacketStatusInOMS(releaseEntry.getPacketId(), "D:C", 3),
						"Update OMS api failed for marking Delivered in OMS");
			} else {
				ExceptionHandler.handleTrue(
						omsServiceHelper.validateReleaseStatusInOMS(releaseEntry.getReleaseId(), "DL:C", 3),
						"Update OMS api failed for marking Delivered in OMS");
			}
		}
    }

    public void processFromOFDtoSMDLfor3PL(ReleaseDetailsEntry releaseEntry) throws IOException, JAXBException, ManagerException {
        
    		log.info("processFromOFDtoSMDLfor3PL for packetId - "+releaseEntry.getPacketId());
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        ExceptionHandler.handleEquals(lmsServiceHelper.selfMarkDL(releaseEntry.getPacketId()).getStatus().getStatusType().toString(),
                EnumSCM.SUCCESS, "Unable to update orders via self mark");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), EnumSCM.DELIVERED, 4),
                "Update trip order api failed for marking Delivered in LMS");
		if (releaseEntry.getShipmentSource() == ShipmentSource.MYNTRA) {

			if (releaseEntry.isPacketEnable()) {

				ExceptionHandler.handleTrue(
						omsServiceHelper.validatePacketStatusInOMS(releaseEntry.getPacketId(), "D:C", 3),
						"Update OMS api failed for marking Delivered in OMS");
			} else {
				ExceptionHandler.handleTrue(
						omsServiceHelper.validateReleaseStatusInOMS(releaseEntry.getReleaseId(), "DL:C", 3),
						"Update OMS api failed for marking Delivered in OMS");
			}
		}
    }

    public void processFromOFDtoDLforML(ReleaseDetailsEntry releaseEntry) throws IOException, JAXBException, ManagerException {
        
    		log.info("processFromOFDtoDLforML for packetId - "+releaseEntry.getPacketId());
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        ExceptionHandler.handleEquals(lmsServiceHelper.updateOrderInTrip(releaseEntry.getTripOrderAssignmentId(), EnumSCM.DELIVERED,
                EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        ExceptionHandler.handleEquals(lmsServiceHelper.updateOrderInTrip(releaseEntry.getTripOrderAssignmentId(), EnumSCM.DELIVERED,
                EnumSCM.TRIP_COMPLETE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInML(releaseEntry.getPacketId(), EnumSCM.DELIVERED, 1),
                "Update trip order api failed for marking Delivered in ML");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), EnumSCM.DELIVERED, 2),
                "Update trip order api failed for marking Delivered in LMS");
		if (releaseEntry.getShipmentSource() == ShipmentSource.MYNTRA) {

			if (releaseEntry.isPacketEnable()) {

				ExceptionHandler.handleTrue(
						omsServiceHelper.validatePacketStatusInOMS(releaseEntry.getPacketId(), "D:C", 3),
						"Update OMS api failed for marking Delivered in OMS");
			} else {
				ExceptionHandler.handleTrue(
						omsServiceHelper.validateReleaseStatusInOMS(releaseEntry.getReleaseId(), "DL:C", 3),
						"Update OMS api failed for marking Delivered in OMS");
			}
		}
    }

    public void processFromOFDtoFDTODLforML(ReleaseDetailsEntry releaseEntry) throws IOException, JAXBException, ManagerException {
        
    		log.info("processFromOFDtoFDTODLforML for packetId - "+releaseEntry.getPacketId());
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        ExceptionHandler.handleEquals(lmsServiceHelper.updateOrderInTrip(releaseEntry.getTripOrderAssignmentId(), EnumSCM.FAILED,
                EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        ExceptionHandler.handleEquals(lmsServiceHelper.updateOrderInTrip(releaseEntry.getTripOrderAssignmentId(), EnumSCM.DELIVERED,
                EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        ExceptionHandler.handleEquals(lmsServiceHelper.updateOrderInTrip(releaseEntry.getTripOrderAssignmentId(), EnumSCM.DELIVERED,
                EnumSCM.TRIP_COMPLETE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInML(releaseEntry.getPacketId(), EnumSCM.DELIVERED, 1),
                "Update trip order api failed for marking Delivered in ML");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), EnumSCM.DELIVERED, 2),
                "Update trip order api failed for marking Delivered in LMS");
		if (releaseEntry.getShipmentSource() == ShipmentSource.MYNTRA) {

			if (releaseEntry.isPacketEnable()) {

				ExceptionHandler.handleTrue(
						omsServiceHelper.validatePacketStatusInOMS(releaseEntry.getPacketId(), "D:C", 3),
						"Update OMS api failed for marking Delivered in OMS");
			} else {
				ExceptionHandler.handleTrue(
						omsServiceHelper.validateReleaseStatusInOMS(releaseEntry.getReleaseId(), "DL:C", 3),
						"Update OMS api failed for marking Delivered in OMS");
			}
		}
    }

    public void processFromOFDtoDLfor3PL(ReleaseDetailsEntry releaseEntry) throws IOException, JAXBException, ManagerException {
        
    		log.info("processFromOFDtoDLfor3PL for packetId - "+releaseEntry.getPacketId());
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        ExceptionHandler.handleEquals(lmsServiceHelper.ctsShipmentUpdate(releaseEntry.getPacketId(), releaseEntry.getCourierCode(), releaseEntry.getTrackingNumber(),
                ShipmentUpdateEvent.DELIVERED, releaseEntry.getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), EnumSCM.DELIVERED, 4),
                "Update trip order api failed for marking Delivered in LMS");
		if (releaseEntry.getShipmentSource() == ShipmentSource.MYNTRA) {

			if (releaseEntry.isPacketEnable()) {

				ExceptionHandler.handleTrue(
						omsServiceHelper.validatePacketStatusInOMS(releaseEntry.getPacketId(), "D:C", 3),
						"Update OMS api failed for marking Delivered in OMS");
			} else {
				ExceptionHandler.handleTrue(
						omsServiceHelper.validateReleaseStatusInOMS(releaseEntry.getReleaseId(), "DL:C", 3),
						"Update OMS api failed for marking Delivered in OMS");
			}
		}
	}

    public void processFromOFDtoDLforML_TOD(ReleaseDetailsEntry releaseEntry) throws IOException, JAXBException, ManagerException {
        
    		log.info("processFromOFDtoDLforML_TOD for packetId - "+releaseEntry.getPacketId());
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        List<TryNBuyEntry> skuAndStatus = new ArrayList<>();
        if (releaseEntry.getTryNBuyEntries()==null){
            List<OrderLineEntry> orderLines = omsServiceHelper.getOrderReleaseEntry(releaseEntry.getPacketId()).getOrderLines();
            for (OrderLineEntry orderLine: orderLines){
                skuAndStatus.add(new TryNBuyEntry(orderLine.getSkuId(),getTODskuDefaultStatus(orderLine.getQuantity())));
            }
            releaseEntry.setTryNBuyEntries(skuAndStatus);
        }else
            skuAndStatus = releaseEntry.getTryNBuyEntries();
        ExceptionHandler.handleEquals(lmsServiceHelper.updateTODOrderInTrip(releaseEntry.getTripOrderAssignmentId(), EnumSCM.DELIVERED, EnumSCM.UPDATE,
                releaseEntry.getPacketId(), skuAndStatus).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        ExceptionHandler.handleEquals(lmsServiceHelper.updateTODOrderInTrip(releaseEntry.getTripOrderAssignmentId(), EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE,
                releaseEntry.getPacketId(), skuAndStatus).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInML(releaseEntry.getPacketId(), EnumSCM.DELIVERED, 1),
                "Update trip order api failed for marking Delivered in ML");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), EnumSCM.DELIVERED, 2),
                "Update trip order api failed for marking Delivered in LMS");
		if (releaseEntry.getShipmentSource() == ShipmentSource.MYNTRA) {

			if (releaseEntry.isPacketEnable()) {

				ExceptionHandler.handleTrue(
						omsServiceHelper.validatePacketStatusInOMS(releaseEntry.getPacketId(), "D:C", 3),
						"Update OMS api failed for marking Delivered in OMS");
			} else {
				ExceptionHandler.handleTrue(
						omsServiceHelper.validateReleaseStatusInOMS(releaseEntry.getReleaseId(), "DL:C", 3),
						"Update OMS api failed for marking Delivered in OMS");
			}
		}
    }

    public void processFromOFDtoFDTODLforML_TOD(ReleaseDetailsEntry releaseEntry) throws IOException, JAXBException, ManagerException {
        
    		log.info("processFromOFDtoFDTODLforML_TOD for packetId - "+releaseEntry.getPacketId());	
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        List<TryNBuyEntry> skuAndStatus = new ArrayList<>();
        if (releaseEntry.getTryNBuyEntries()==null){
            List<OrderLineEntry> orderLines = omsServiceHelper.getOrderReleaseEntry(releaseEntry.getPacketId()).getOrderLines();
            for (OrderLineEntry orderLine: orderLines){
                skuAndStatus.add(new TryNBuyEntry(orderLine.getSkuId(),getTODskuDefaultStatus(orderLine.getQuantity())));
            }
            releaseEntry.setTryNBuyEntries(skuAndStatus);
        }else
            skuAndStatus = releaseEntry.getTryNBuyEntries();
        ExceptionHandler.handleEquals(lmsServiceHelper.updateTODOrderInTrip(releaseEntry.getTripOrderAssignmentId(), EnumSCM.FAILED, EnumSCM.UPDATE,
                releaseEntry.getPacketId(), skuAndStatus).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        ExceptionHandler.handleEquals(lmsServiceHelper.updateTODOrderInTrip(releaseEntry.getTripOrderAssignmentId(), EnumSCM.DELIVERED, EnumSCM.UPDATE,
                releaseEntry.getPacketId(), skuAndStatus).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        ExceptionHandler.handleEquals(lmsServiceHelper.updateTODOrderInTrip(releaseEntry.getTripOrderAssignmentId(), EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE,
                releaseEntry.getPacketId(), skuAndStatus).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInML(releaseEntry.getPacketId(), EnumSCM.DELIVERED, 1),
                "Update trip order api failed for marking Delivered in ML");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), EnumSCM.DELIVERED, 2),
                "Update trip order api failed for marking Delivered in LMS");
		if (releaseEntry.getShipmentSource() == ShipmentSource.MYNTRA) {

			if (releaseEntry.isPacketEnable()) {

				ExceptionHandler.handleTrue(
						omsServiceHelper.validatePacketStatusInOMS(releaseEntry.getPacketId(), "D:C", 3),
						"Update OMS api failed for marking Delivered in OMS");
			} else {
				ExceptionHandler.handleTrue(
						omsServiceHelper.validateReleaseStatusInOMS(releaseEntry.getReleaseId(), "DL:C", 3),
						"Update OMS api failed for marking Delivered in OMS");
			}
		}
    }

    public void processFromOFDtoFDforML_TOD(ReleaseDetailsEntry releaseEntry) throws IOException, JAXBException, ManagerException {
    	
    		log.info("processFromOFDtoFDforML_TOD for packetId - "+releaseEntry.getPacketId());	
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        List<TryNBuyEntry> skuAndStatus = new ArrayList<>();
        if (releaseEntry.getTryNBuyEntries()==null){
            List<OrderLineEntry> orderLines = omsServiceHelper.getOrderReleaseEntry(releaseEntry.getPacketId()).getOrderLines();
            for (OrderLineEntry orderLine: orderLines){
                skuAndStatus.add(new TryNBuyEntry(orderLine.getSkuId(),getTODskuDefaultStatus(orderLine.getQuantity())));
            }
            releaseEntry.setTryNBuyEntries(skuAndStatus);
        }else
            skuAndStatus = releaseEntry.getTryNBuyEntries();
        ExceptionHandler.handleEquals(lmsServiceHelper.updateTODOrderInTrip(releaseEntry.getTripOrderAssignmentId(), EnumSCM.FAILED, EnumSCM.UPDATE,
                releaseEntry.getPacketId(), skuAndStatus).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInML(releaseEntry.getPacketId(), ReleaseStatus.FAILED_DELIVERY.name(), 1),
                "Order status is not in FAILED_DELIVERY in ML");
        ExceptionHandler.handleEquals(lmsServiceHelper.updateTODOrderInTrip(releaseEntry.getTripOrderAssignmentId(), EnumSCM.FAILED, EnumSCM.TRIP_COMPLETE,
                releaseEntry.getPacketId(), skuAndStatus).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInML(releaseEntry.getPacketId(), ReleaseStatus.RECEIVED_IN_DC.name(), 1),
                "Order status is not in FAILED_DELIVERY in ML");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), ReleaseStatus.FAILED_DELIVERY.name(), 3),
                "Order status is not in FAILED_DELIVERY in LMS");
    }

    public String getTODskuDefaultStatus(int qty){
        switch (qty) {
            case 1: return EnumSCM.TRIED_AND_BOUGHT;
            case 2: return EnumSCM.TRIED_AND_BOUGHT + "," + EnumSCM.TRIED_AND_BOUGHT;
            case 3: return EnumSCM.TRIED_AND_BOUGHT + "," + EnumSCM.TRIED_AND_BOUGHT + "," + EnumSCM.TRIED_AND_BOUGHT;
        }
        return null;
    }

    public void processFromSHtoLOSTforML(ReleaseDetailsEntry releaseEntry) throws JAXBException, UnsupportedEncodingException, ManagerException {
        
    		log.info("processFromSHtoLOSTforML for packetId - "+releaseEntry.getPacketId());	
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        ExceptionHandler.handleEquals(lmsServiceHelper.markOrderLOSTINDC(releaseEntry.getPacketId()), EnumSCM.SUCCESS);
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInML(releaseEntry.getPacketId(), ReleaseStatus.LOST.name(), 1),
                "Order status is not in LOST in ML");
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), ReleaseStatus.LOST.name(), 3),
                "Order status is not in LOST in LMS");
        if (releaseEntry.getShipmentSource() == ShipmentSource.MYNTRA) {

			if (releaseEntry.isPacketEnable()) {

				ExceptionHandler.handleTrue(omsServiceHelper.validatePacketStatusInOMS(releaseEntry.getPacketId(), ReleaseStatus.L.name(), 3),
	                    "OMS status is not in L");
			} else {
				ExceptionHandler.handleTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseEntry.getReleaseId(), ReleaseStatus.LOST.name(), 3),
	                    "OMS status is not in L");
			}
		}
            
    }

    public void processFromSHtoLOSTfor3PL(ReleaseDetailsEntry releaseEntry) throws JAXBException, UnsupportedEncodingException, ManagerException {
        
    		log.info("processFromSHtoLOSTfor3PL for packetId - "+releaseEntry.getPacketId());	
    		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        ExceptionHandler.handleEquals(lmsServiceHelper.markOrderLOSTINTRANSIT(releaseEntry.getPacketId()), EnumSCM.SUCCESS);
        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), ReleaseStatus.LOST.name(), 3),
                "Order status is not in LOST in LMS");
        if (releaseEntry.getShipmentSource() == ShipmentSource.MYNTRA) {

			if (releaseEntry.isPacketEnable()) {

				ExceptionHandler.handleTrue(omsServiceHelper.validatePacketStatusInOMS(releaseEntry.getPacketId(), ReleaseStatus.L.name(), 3),
	                    "OMS status is not in L");
			} else {
				ExceptionHandler.handleTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseEntry.getReleaseId(), ReleaseStatus.LOST.name(), 3),
	                    "OMS status is not in L");
			}
		}
    }
}
