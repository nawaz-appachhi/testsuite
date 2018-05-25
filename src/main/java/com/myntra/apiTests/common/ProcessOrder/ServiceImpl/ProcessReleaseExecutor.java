package com.myntra.apiTests.common.ProcessOrder.ServiceImpl;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.Constants.ShipmentSource;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.entries.ReleaseDetailsEntry;
import com.myntra.apiTests.common.entries.ReleaseDetailsEntryList;
import com.myntra.apiTests.common.entries.ReleaseMapper;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.lms.Constants.CourierCode;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.packman.PackmanServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lms.client.response.ShipmentResponse;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.ParseException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shubham Gupta on 8/2/17.
 */
public class ProcessReleaseExecutor {

    private static Logger log = Logger.getLogger(ProcessReleaseExecutor.class);
    private ReleaseMapper mapper = new ReleaseMapper();
    ProcessReleaseExecutorHelper processReleaseExecutorHelper = new ProcessReleaseExecutorHelper();
    
    public ReleaseDetailsEntry setPacketId(ReleaseDetailsEntry releaseDetailsEntry) throws UnsupportedEncodingException, JAXBException {
    	
    	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String packetId =  ""+omsServiceHelper.getOrderReleaseEntry(releaseDetailsEntry.getReleaseId()).getOrderLines().get(0).getPacketId();
		releaseDetailsEntry.setPacketId(packetId);
		return releaseDetailsEntry;
    }

    public void processReleaseToWP(ReleaseDetailsEntryList releaseDetailsEntryList) throws JAXBException, UnsupportedEncodingException, ManagerException {
        
	    	log.info("Processing release to WP for releaseId "+ releaseDetailsEntryList);
	    	for(ReleaseDetailsEntry releaseDetailsEntry:releaseDetailsEntryList.getReleaseDetailsEntries()){
	    		processReleaseExecutorHelper.processReleaseToWPHelper(releaseDetailsEntry);
	    		log.info("ReleaseDetail Entry:"+releaseDetailsEntry);
	    	}
    }
      
    public void processReleaseFromWPtoPK(ReleaseDetailsEntryList releaseDetailsEntryList) throws JAXBException, IOException, ManagerException, SQLException, InterruptedException, XMLStreamException, JSONException, ParseException {

	    	log.info("Processing release WP to PK for releaseId");
	    	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	    PackmanServiceHelper packmanServiceHelper = new PackmanServiceHelper();
	    	ProcessReleaseWMSnSellerHelper processHelper = new ProcessReleaseWMSnSellerHelper();
  	
        for(ReleaseDetailsEntry releaseDetailsEntry: releaseDetailsEntryList.getReleaseDetailsEntries()){
        	
        		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseDetailsEntry.getReleaseId());
            if (!orderReleaseEntry.getStatus().equals(ReleaseStatus.WP.toString())) return;
            
            mapper.mapReleaseOms(releaseDetailsEntry);
            SellerConfig sellerConfig = new SellerConfig();
            if (sellerConfig.isVector(""+releaseDetailsEntry.getSellerId()) && releaseDetailsEntry.getSupplyType().equalsIgnoreCase(EnumSCM.ON_HAND)) {
                processHelper.processReleaseInWMS(releaseDetailsEntryList);
				packmanServiceHelper.markItemTillPack(releaseDetailsEntry.getReleaseId());
            } else if (releaseDetailsEntry.getSupplyType().equalsIgnoreCase(EnumSCM.JUST_IN_TIME)) {
                processHelper.processForJITItems(releaseDetailsEntry);
            }else if (releaseDetailsEntry.getSellerId()==18L) {
                processHelper.processMaduraTillPK(releaseDetailsEntry);
            } else {
                processHelper.processSmallSellerTillPK(releaseDetailsEntry);
            }
        }
        
        Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap = packetIdReleaseDetailsEntryMap(releaseDetailsEntryList);
        
        for (String packetId : packetIdReleaseDetailsEntryMap.keySet()) { 
        	
        	     if(packetId != null && ! packetId.isEmpty()) {       	    	 
        	    	 	ExceptionHandler.handleEquals( omsServiceHelper.pushPacketToLms(packetId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        	     } else {      	    	 
        	    	    log.error("packetId is null or empty "+packetId);
        	     }
        }
//        if ((orderReleaseEntry.getTrackingNo() == null) || (orderReleaseEntry.getTrackingNo().equalsIgnoreCase("")))
//            ExceptionHandler.handleEquals(omsServiceHelper.assignTrackingNumber(releaseDetailsEntry.getReleaseId(), false).getStatus().getStatusType().toString(),
//                    "SUCCESS", "Unable to assign Tracking Number");
//        ExceptionHandler.handleTrue(omsServiceHelper.validateTrackingNumberNotNullInOrderRelease(orderReleaseEntry.getId().toString(),8)
//                ,"Tracking number is not assigned yet !!");
       
    }

    public void processReleaseFromWPtoPKMock(ReleaseDetailsEntry releaseDetailsEntry) throws JAXBException, IOException, ManagerException, InterruptedException, SQLException, XMLStreamException, JSONException {
          		
    		log.info("Processing release WP to PK for releaseId "+ releaseDetailsEntry.getReleaseId());
    		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        ProcessReleaseWMSnSellerHelper processHelper = new ProcessReleaseWMSnSellerHelper();
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseDetailsEntry.getReleaseId());
        if (!orderReleaseEntry.getStatus().equals(ReleaseStatus.WP.toString())) return;
        if ((orderReleaseEntry.getTrackingNo() == null) || (orderReleaseEntry.getTrackingNo().equalsIgnoreCase("")))
            ExceptionHandler.handleEquals(omsServiceHelper.assignTrackingNumber(releaseDetailsEntry.getReleaseId(), false).getStatus().getStatusType().toString(),
                    "SUCCESS", "Unable to assign Tracking Number");
        ExceptionHandler.handleTrue(omsServiceHelper.validateTrackingNumberNotNullInOrderRelease(orderReleaseEntry.getId().toString(),8)
                ,"Tracking number is not assigned yet !!");
        mapper.mapReleaseOms(releaseDetailsEntry);
        omsServiceHelper.stampGovtTaxForVectorSuccess(releaseDetailsEntry.getReleaseId());
        processHelper.processForOnHandInWarehouseMock(releaseDetailsEntry);
    }
    
	public Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap(ReleaseDetailsEntryList releaseDetailsEntryList) {

		Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap = new HashMap<>();

		if (releaseDetailsEntryList.getReleaseDetailsEntries() != null && !releaseDetailsEntryList.getReleaseDetailsEntries().isEmpty()) {

			for (ReleaseDetailsEntry releaseDetailsEntry : releaseDetailsEntryList.getReleaseDetailsEntries()) {

				if (releaseDetailsEntry.getPacketId() != null && !releaseDetailsEntry.getPacketId().isEmpty()) {
					packetIdReleaseDetailsEntryMap.put(releaseDetailsEntry.getPacketId(), releaseDetailsEntry);
				} else {
					log.error("Packet is null or empty " + releaseDetailsEntry.getPacketId());
				}
			}
		} else {
			log.error("ReleaseDetailsEntries is null or empty " + releaseDetailsEntryList.getReleaseDetailsEntries());
		}

		return packetIdReleaseDetailsEntryMap;
	}

    public void processFromPKToIS(ReleaseDetailsEntryList releaseDetailsEntryList) throws InterruptedException, IOException, JAXBException, ManagerException, XMLStreamException, JSONException {
        	
    	//Adding temp wait time to read from LMS till delay issue is fixed,
    	//As delay is of 10 sec for adding 15 sec for safe side. We will remove this once issue is fixed.
    		End2EndHelper.sleep(15000);
    		
    		log.info("Processing packet PK to IS for packetId ");
    	    Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap = packetIdReleaseDetailsEntryMap(releaseDetailsEntryList);

    	    for(String packetId : packetIdReleaseDetailsEntryMap.keySet()) {
    	    	
    	    		ReleaseDetailsEntry releaseEntry = packetIdReleaseDetailsEntryMap.get(packetId);
    	    		log.info("Processing packet PK to IS for packetId "+ releaseEntry.getPacketId());
			LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		    String lmsStatus = lmsServiceHelper.getOrderStatusFromLMS(releaseEntry.getPacketId());
		    if (releaseEntry.getShipmentSource()== ShipmentSource.MYNTRA) mapper.mapReleaseOms(releaseEntry);
		    mapper.mapReleaselms(releaseEntry);
		    if (lmsStatus.equals(ReleaseStatus.PACKED.name())|| lmsStatus.equals(ReleaseStatus.INSCANNED.name())) {
		        ExceptionHandler.handleEquals(lmsServiceHelper.orderInScanInHub(releaseEntry.getPacketId(), releaseEntry.getDispatchHub()), EnumSCM.SUCCESS, "Unable to Inscan order in Dispatch HUB");
		        ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseEntry.getPacketId(), ReleaseStatus.INSCANNED.name(), 2), "Shipment Status is not INSCANNED in LMS");
		        if (releaseEntry.getCourierCode().equals(CourierCode.ML.toString()))
		            ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInML(releaseEntry.getPacketId(), ReleaseStatus.EXPECTED_IN_DC.name(), 1),
		                    "Shipment Status in ML is still not in EXPECTED_IN_DC");
		    }else return;
    	    }
	}

	public void processFromISToAddedToMB(ReleaseDetailsEntryList releaseDetailsEntryList) throws InterruptedException, IOException, JAXBException, ManagerException, XMLStreamException, JSONException {
	    
		Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap = packetIdReleaseDetailsEntryMap(releaseDetailsEntryList);
	    
	    for(String packetId : packetIdReleaseDetailsEntryMap.keySet()) {
	    	
	    		ReleaseDetailsEntry releaseEntry = packetIdReleaseDetailsEntryMap.get(packetId);	    	
			log.info("Processing packet IS to AddedToMB for packetId "+ releaseEntry.getPacketId());
			LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		    LMSHelper lmsHelper = new LMSHelper();
		    if (releaseEntry.getCourierCode() == null && releaseEntry.getShipmentSource()==ShipmentSource.MYNTRA)
		        mapper.mapReleaseOms(releaseEntry);
		
		    mapper.mapReleaselms(releaseEntry);
		    String lmsStatus = lmsServiceHelper.getOrderStatusFromLMS(releaseEntry.getPacketId());
		    if (!lmsStatus.equals(ReleaseStatus.INSCANNED.name()))
		        return;
		    long dcId = releaseEntry.getDeliveryCenterId();
		    if (releaseEntry.getCourierCode().equals(CourierCode.ML.toString())) {}
		    else if (releaseEntry.getCourierCode().equals(CourierCode.EK.toString())) {
		        lmsHelper.updateOrderTrackingToAccepted(releaseEntry.getPacketId());
		        dcId = (long) DBUtilities.exSelectQueryForSingleRecord("select id from delivery_center where code = 'EKART' and name = 'EKART' and courier_code = 'EK'", "lms").get("id");
		    } else if (releaseEntry.getCourierCode().equals(CourierCode.DE.toString()) && releaseEntry.getDeliveryCenterId() == 2281){
		        lmsHelper.updateOrderTrackingToAccepted(releaseEntry.getPacketId());
		        dcId = releaseEntry.getDeliveryCenterId();
		    }
		    else if (releaseEntry.getCourierCode().equals(CourierCode.DE.toString())){
		        lmsHelper.updateOrderTrackingToAccepted(releaseEntry.getPacketId());
		        dcId = (long) DBUtilities.exSelectQueryForSingleRecord("select id from delivery_center where code = 'DE' and type = 'OTHER_LOGISTICS'", "lms").get("id");
		    }else {
		        dcId = (long) DBUtilities.exSelectQueryForSingleRecord("select id from delivery_center where courier_code = '"+releaseEntry.getCourierCode()
		                +"' and type = 'OTHER_LOGISTICS'", "lms").get("id");
		    }
		    ShipmentResponse createMasterBagRes = null;
		    if (releaseEntry.getDeliveryCenterId()==2281)
		        createMasterBagRes = lmsServiceHelper.createMasterBag(dcId, releaseEntry.getWarehouseId(), releaseEntry.getShippingMethod(), CourierCode.DE.toString());
		    else
		        createMasterBagRes = lmsServiceHelper.createMasterBag(dcId, releaseEntry.getWarehouseId(), releaseEntry.getShippingMethod());
		    ExceptionHandler.handleEquals(createMasterBagRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create MaterBag");
		    long masterBagId = createMasterBagRes.getEntries().get(0).getId();
		    ExceptionHandler.handleEquals(lmsServiceHelper.addAndSaveMasterBag(releaseEntry.getPacketId(), "" + masterBagId,
		            releaseEntry.getShipmentType()), EnumSCM.SUCCESS, "Unable to save masterBag");
		    ExceptionHandler.handleEquals(lmsHelper.getOrderToShipStatus(releaseEntry.getPacketId()), ReleaseStatus.ADDED_TO_MB.name(),
		            "Shipment Status is not ADDED_TO_MB in LMS DB");
	    }
	}
	
	public void processFromAddedToMBToClosed(ReleaseDetailsEntryList releaseDetailsEntryList) throws InterruptedException, IOException, JAXBException, ManagerException, XMLStreamException, JSONException {
	    
		Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap = packetIdReleaseDetailsEntryMap(releaseDetailsEntryList);
	    
	    for(String packetId : packetIdReleaseDetailsEntryMap.keySet()) {
	    	
	    		ReleaseDetailsEntry releaseEntry = packetIdReleaseDetailsEntryMap.get(packetId);	    	
			log.info("Processing packet AddedToMB to Closed for packetId "+ releaseEntry.getPacketId());
			LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		    LMSHelper lmsHelper = new LMSHelper();
		    if (releaseEntry.getCourierCode()==null && releaseEntry.getShipmentSource()==ShipmentSource.MYNTRA)
		        mapper.mapReleaseOms(releaseEntry);
		    mapper.mapReleaselms(releaseEntry);
		    mapper.mapReleaseMasterBag(releaseEntry);
		    if (!lmsServiceHelper.getOrderStatusFromLMS(releaseEntry.getPacketId()).equals(ReleaseStatus.ADDED_TO_MB.name()))
		        return;
		    ExceptionHandler.handleEquals(lmsServiceHelper.closeMasterBag(releaseEntry.getMasterBagId()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		    ExceptionHandler.handleEquals(lmsHelper.getMasterBagStatus(releaseEntry.getMasterBagId()), EnumSCM.CLOSED,
		            "masterbag status is not updated in DB to `CLOSED`");
	    }
	}
	
	public void processFromClosedToSH(ReleaseDetailsEntryList releaseDetailsEntryList) throws InterruptedException, JAXBException, IOException, XMLStreamException, ManagerException, JSONException {
	    
		Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap = packetIdReleaseDetailsEntryMap(releaseDetailsEntryList);
	    
	    for(String packetId : packetIdReleaseDetailsEntryMap.keySet()) {
	    	
	    		ReleaseDetailsEntry releaseEntry = packetIdReleaseDetailsEntryMap.get(packetId);
	    	
			log.info("Processing packet master Closed to SH for packetId "+ releaseEntry.getPacketId());
			LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		    LMSHelper lmsHelper = new LMSHelper();
		    if (releaseEntry.getCourierCode()==null && releaseEntry.getShipmentSource()==ShipmentSource.MYNTRA)
		        mapper.mapReleaseOms(releaseEntry);
		    mapper.mapReleaselms(releaseEntry);
		    mapper.mapReleaseMasterBag(releaseEntry);
		    ProcessReleaseLMSHelper processReleaseLMSHelper = new ProcessReleaseLMSHelper();
		    if (lmsServiceHelper.getOrderStatusFromLMS(releaseEntry.getPacketId()).equals(ReleaseStatus.ADDED_TO_MB.name()) &&
		            lmsHelper.getMasterBagStatus(releaseEntry.getMasterBagId()).equals(EnumSCM.CLOSED)) {
		        if (releaseEntry.getCourierCode().equals(CourierCode.ML.toString()))
		            processReleaseLMSHelper.processFromClosedToUnassignedForML(releaseEntry);
		        else if (releaseEntry.getCourierCode().equals(CourierCode.DE.toString()) && releaseEntry.getDeliveryCenterId() > 0)
		            processReleaseLMSHelper.processFromClosedtoSHforDE_RHD(releaseEntry);
		        else
		            processReleaseLMSHelper.processFromClosedToSHfor3PL(releaseEntry);
		    }else return;
	    }
	}

	public void processFromSHToOFD(ReleaseDetailsEntryList releaseDetailsEntryList) throws ManagerException, JAXBException, IOException, InterruptedException, XMLStreamException, JSONException {
	    
		Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap = packetIdReleaseDetailsEntryMap(releaseDetailsEntryList);
	   
		for(String packetId : packetIdReleaseDetailsEntryMap.keySet()) {
	    	
	    		ReleaseDetailsEntry releaseEntry = packetIdReleaseDetailsEntryMap.get(packetId);
	    	
			log.info("Processing packet SH to OFD for packetId "+ releaseEntry.getPacketId());
			LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		    ProcessReleaseLMSHelper processReleaseLMSHelper = new ProcessReleaseLMSHelper();
		    if (releaseEntry.getShipmentSource()==ShipmentSource.MYNTRA) { 
		    	
		    		mapper.mapReleaseOms(releaseEntry);
		    }
		    
		    mapper.mapReleaselms(releaseEntry);
		    
		    if (!lmsServiceHelper.getOrderStatusFromLMS(releaseEntry.getPacketId()).equals(ReleaseStatus.SHIPPED.name()))
		        return;
		    if (releaseEntry.getCourierCode().equals(CourierCode.ML.toString())) {
		        if (!lmsServiceHelper.getOrderStatusFromML(releaseEntry.getPacketId()).equals(ReleaseStatus.UNASSIGNED.name()))
		            return;
		        processReleaseLMSHelper.processFromReceivedToOFD_ML(releaseEntry);
		        mapper.mapReleaseTrip(releaseEntry);
		    }
		    else processReleaseLMSHelper.processFromSHtoOFD3PL(releaseEntry);
	    }
	}

	public void processFromOFDtoFD(ReleaseDetailsEntryList releaseDetailsEntryList) throws ManagerException, JAXBException, IOException, InterruptedException, XMLStreamException, JSONException {
	    
		Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap = packetIdReleaseDetailsEntryMap(releaseDetailsEntryList);

	    for(String packetId : packetIdReleaseDetailsEntryMap.keySet()) {
	    	
	    		ReleaseDetailsEntry releaseEntry = packetIdReleaseDetailsEntryMap.get(packetId);
			log.info("Processing packet OFD to FD for packetId "+ releaseEntry.getPacketId());
			LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		    ProcessReleaseLMSHelper processReleaseLMSHelper = new ProcessReleaseLMSHelper();
		    if (releaseEntry.getShipmentSource()==ShipmentSource.MYNTRA) mapper.mapReleaseOms(releaseEntry);
		        mapper.mapReleaselms(releaseEntry);
		    if (!lmsServiceHelper.getOrderStatusFromLMS(releaseEntry.getPacketId()).equals(ReleaseStatus.OUT_FOR_DELIVERY.name()))
		        return;
		    if (releaseEntry.getCourierCode().equals(CourierCode.ML.toString())) {
		        if (lmsServiceHelper.getOrderStatusFromML(releaseEntry.getPacketId()).equals(ReleaseStatus.ASSIGNED_TO_SDA.name()))
		            return;
		        if (releaseEntry.getTripId()==0) mapper.mapReleaseTrip(releaseEntry);
		        processReleaseLMSHelper.processFromOFDtoFDforML(releaseEntry);
		    }else {
		        if (releaseEntry.getCourierCode()==null)
		        processReleaseLMSHelper.processFromOFDtoFDfor3PL(releaseEntry);
		    }
	    }
	}
	
	public void processFromOFDtoSMDL(ReleaseDetailsEntryList releaseDetailsEntryList) throws ManagerException, JAXBException, IOException, InterruptedException, XMLStreamException, JSONException {
	    
		Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap = packetIdReleaseDetailsEntryMap(releaseDetailsEntryList);
	    
	    for(String packetId : packetIdReleaseDetailsEntryMap.keySet()) {
	    	
	    		ReleaseDetailsEntry releaseEntry = packetIdReleaseDetailsEntryMap.get(packetId);
			log.info("Processing packet OFD to SMDL for packetId "+ releaseEntry.getPacketId());
			LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		    ProcessReleaseLMSHelper processReleaseLMSHelper = new ProcessReleaseLMSHelper();
		    mapper.mapReleaselms(releaseEntry);
		    if (!lmsServiceHelper.getOrderStatusFromLMS(releaseEntry.getPacketId()).equals(ReleaseStatus.OUT_FOR_DELIVERY.name()))
		        return;
		    if (releaseEntry.getCourierCode().equals(CourierCode.ML.toString())) {
		        if (lmsServiceHelper.getOrderStatusFromML(releaseEntry.getPacketId()).equals(ReleaseStatus.ASSIGNED_TO_SDA.name()))
		            return;
		        mapper.mapReleaseTrip(releaseEntry);
		        processReleaseLMSHelper.processFromOFDtoSMDLforML(releaseEntry);
		    }else {
		        if (releaseEntry.getCourierCode()==null)
		        processReleaseLMSHelper.processFromOFDtoSMDLfor3PL(releaseEntry);
		    }
	    }
	}

	public void processFromSHtoRTO(ReleaseDetailsEntryList releaseDetailsEntryList) throws JAXBException, ManagerException, IOException, InterruptedException, XMLStreamException, JSONException {
	    
		Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap = packetIdReleaseDetailsEntryMap(releaseDetailsEntryList);
	    
	    for(String packetId : packetIdReleaseDetailsEntryMap.keySet()) {
	    	
	    		ReleaseDetailsEntry releaseEntry = packetIdReleaseDetailsEntryMap.get(packetId);
			log.info("Processing packet SH to RTO for packetId "+ releaseEntry.getPacketId());
			LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		    ProcessReleaseLMSHelper processReleaseLMSHelper = new ProcessReleaseLMSHelper();
		    mapper.mapReleaselms(releaseEntry);
		    if (!lmsServiceHelper.getOrderStatusFromLMS(releaseEntry.getPacketId()).equals(ReleaseStatus.SHIPPED.name()))
		        return;
		    if (releaseEntry.getCourierCode().equals(CourierCode.ML.toString())) {
		        if (!lmsServiceHelper.getOrderStatusFromML(releaseEntry.getPacketId()).equals(ReleaseStatus.UNASSIGNED.name()))
		            return;
		
		        processReleaseLMSHelper.processFromSHtoRTOforML(releaseEntry);
		    }else
		        processReleaseLMSHelper.processToRTO3PL(releaseEntry);
	    }
	}
	
	public void processFromOFDtoFDRTO(ReleaseDetailsEntryList releaseDetailsEntryList) throws ManagerException, JAXBException, IOException, InterruptedException, XMLStreamException, JSONException {
	   
		Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap = packetIdReleaseDetailsEntryMap(releaseDetailsEntryList);
	    
	    for(String packetId : packetIdReleaseDetailsEntryMap.keySet()) {
	    	
	    		ReleaseDetailsEntry releaseEntry = packetIdReleaseDetailsEntryMap.get(packetId);
			log.info("Processing packet OFD to FDRTO for packetId "+ releaseEntry.getPacketId());
			LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		    ProcessReleaseLMSHelper processReleaseLMSHelper = new ProcessReleaseLMSHelper();
		    mapper.mapReleaselms(releaseEntry);
		    if (!lmsServiceHelper.getOrderStatusFromLMS(releaseEntry.getPacketId()).equals(ReleaseStatus.OUT_FOR_DELIVERY.name()))
		        return;
		    if (releaseEntry.getCourierCode().equals(CourierCode.ML.toString())) {
		        if (!lmsServiceHelper.getOrderStatusFromML(releaseEntry.getPacketId()).equals(ReleaseStatus.OUT_FOR_DELIVERY.name()))
		            return;
		        mapper.mapReleaseTrip(releaseEntry);
		        processReleaseLMSHelper.processFromOFDtoFDforML(releaseEntry);
		        processReleaseLMSHelper.processFromFDtoRTOforML(releaseEntry);
		    }else {
		        processReleaseLMSHelper.processFromOFDtoFDfor3PL(releaseEntry);
		        processReleaseLMSHelper.processToRTO3PL(releaseEntry);
		    }
	    }
	}

	public void processFromOFDtoDL(ReleaseDetailsEntryList releaseDetailsEntryList) throws JAXBException, ManagerException, IOException, InterruptedException, XMLStreamException, JSONException {
	   
		Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap = packetIdReleaseDetailsEntryMap(releaseDetailsEntryList);
 
	    for(String packetId : packetIdReleaseDetailsEntryMap.keySet()) {
	    	
	    		ReleaseDetailsEntry releaseEntry = packetIdReleaseDetailsEntryMap.get(packetId);
			log.info("Processing packet OFD to DL for packetId "+ releaseEntry.getPacketId());
			LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		    ProcessReleaseLMSHelper processReleaseLMSHelper = new ProcessReleaseLMSHelper();
		    mapper.mapReleaselms(releaseEntry);
		    if (!lmsServiceHelper.getOrderStatusFromLMS(releaseEntry.getPacketId()).equals(ReleaseStatus.OUT_FOR_DELIVERY.name()))
		        return;
		    if (releaseEntry.getCourierCode().equals(CourierCode.ML.toString())) {
		        if (!lmsServiceHelper.getOrderStatusFromML(releaseEntry.getPacketId()).equals(ReleaseStatus.OUT_FOR_DELIVERY.name()))
		            return;
		        mapper.mapReleaseTrip(releaseEntry);
		        if (releaseEntry.isTryNbuy()==true)
		            processReleaseLMSHelper.processFromOFDtoDLforML_TOD(releaseEntry);
		        else
		            processReleaseLMSHelper.processFromOFDtoDLforML(releaseEntry);
		    }else
		        processReleaseLMSHelper.processFromOFDtoDLfor3PL(releaseEntry);
	    }
	}
	
	public void processFromOFDtoFDTODL(ReleaseDetailsEntryList releaseDetailsEntryList) throws JAXBException, ManagerException, IOException, InterruptedException, XMLStreamException, JSONException {
	    
		Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap = packetIdReleaseDetailsEntryMap(releaseDetailsEntryList);

	    for(String packetId : packetIdReleaseDetailsEntryMap.keySet()) {
	    	
	    		ReleaseDetailsEntry releaseEntry = packetIdReleaseDetailsEntryMap.get(packetId);
			log.info("Processing packet OFD to FDTODL for packetId "+ releaseEntry.getPacketId());
			LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		    ProcessReleaseLMSHelper processReleaseLMSHelper = new ProcessReleaseLMSHelper();
		    mapper.mapReleaselms(releaseEntry);
		    if (!lmsServiceHelper.getOrderStatusFromLMS(releaseEntry.getPacketId()).equals(ReleaseStatus.OUT_FOR_DELIVERY.name()))
		        return;
		    if (releaseEntry.getCourierCode().equals(CourierCode.ML.toString())) {
		        if (!lmsServiceHelper.getOrderStatusFromML(releaseEntry.getPacketId()).equals(ReleaseStatus.OUT_FOR_DELIVERY.name()))
		            return;
		        mapper.mapReleaseTrip(releaseEntry);
		        if (releaseEntry.isTryNbuy()==true)
		            processReleaseLMSHelper.processFromOFDtoFDTODLforML_TOD(releaseEntry);
		        else
		            processReleaseLMSHelper.processFromOFDtoFDTODLforML(releaseEntry);
		    }else
		        processReleaseLMSHelper.processFromOFDtoDLfor3PL(releaseEntry);
	    }
	}

	public void processFromOFDtoFDDL(ReleaseDetailsEntryList releaseDetailsEntryList) throws ManagerException, JAXBException, IOException, InterruptedException, XMLStreamException, JSONException {
	    
		Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap = packetIdReleaseDetailsEntryMap(releaseDetailsEntryList);

	    for(String packetId : packetIdReleaseDetailsEntryMap.keySet()) {
	    	
	    		ReleaseDetailsEntry releaseEntry = packetIdReleaseDetailsEntryMap.get(packetId);
			log.info("Processing packet OFD to FDDL for packetId "+ releaseEntry.getPacketId());
			LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		    ProcessReleaseLMSHelper processReleaseLMSHelper = new ProcessReleaseLMSHelper();
		    mapper.mapReleaselms(releaseEntry);
		    if (!lmsServiceHelper.getOrderStatusFromLMS(releaseEntry.getPacketId()).equals(ReleaseStatus.OUT_FOR_DELIVERY.name()))
		        return;
		    if (releaseEntry.getCourierCode().equals(CourierCode.ML.toString())) {
		        if (!lmsServiceHelper.getOrderStatusFromML(releaseEntry.getPacketId()).equals(ReleaseStatus.OUT_FOR_DELIVERY.name()))
		            return;
		        mapper.mapReleaseTrip(releaseEntry);
		        if (releaseEntry.isTryNbuy()==true) {
		            processReleaseLMSHelper.processFromOFDtoFDforML_TOD(releaseEntry);
		            lmsServiceHelper.requeueOrder(releaseEntry.getPacketId());
		            processReleaseLMSHelper.processFromReceivedToOFD_ML(releaseEntry);
		            mapper.mapReleaseTrip(releaseEntry);
		            processReleaseLMSHelper.processFromOFDtoDLforML_TOD(releaseEntry);
		        }
		        else {
		            processReleaseLMSHelper.processFromOFDtoFDforML(releaseEntry);
		            lmsServiceHelper.requeueOrder(releaseEntry.getPacketId());
		            processReleaseLMSHelper.processFromReceivedToOFD_ML(releaseEntry);
		            mapper.mapReleaseTrip(releaseEntry);
		            processReleaseLMSHelper.processFromOFDtoDLforML(releaseEntry);
		        }
		    }else {
		        processReleaseLMSHelper.processFromOFDtoFDfor3PL(releaseEntry);
		        processReleaseLMSHelper.processFromSHtoOFD3PL(releaseEntry);
		        processReleaseLMSHelper.processFromOFDtoDLfor3PL(releaseEntry);
		    }
	    }
	}

	public void processFromOFDtoFDFDDL(ReleaseDetailsEntryList releaseDetailsEntryList) throws ManagerException, JAXBException, IOException, InterruptedException, XMLStreamException, JSONException {
	    
		Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap = packetIdReleaseDetailsEntryMap(releaseDetailsEntryList);
 
	    for(String packetId : packetIdReleaseDetailsEntryMap.keySet()) {
	    	
	    		ReleaseDetailsEntry releaseEntry = packetIdReleaseDetailsEntryMap.get(packetId);
			log.info("Processing packet OFD to FDFDDL for packetId "+ releaseEntry.getPacketId());
			LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		    ProcessReleaseLMSHelper processReleaseLMSHelper = new ProcessReleaseLMSHelper();
		    mapper.mapReleaselms(releaseEntry);
		    if (!lmsServiceHelper.getOrderStatusFromLMS(releaseEntry.getPacketId()).equals(ReleaseStatus.OUT_FOR_DELIVERY.name()))
		        return;
		    if (releaseEntry.getCourierCode().equals(CourierCode.ML.toString())) {
		        if (!lmsServiceHelper.getOrderStatusFromML(releaseEntry.getPacketId()).equals(ReleaseStatus.OUT_FOR_DELIVERY.name()))
		            return;
		        mapper.mapReleaseTrip(releaseEntry);
		        if (releaseEntry.isTryNbuy()==true) {
		            processReleaseLMSHelper.processFromOFDtoFDforML_TOD(releaseEntry);
		            lmsServiceHelper.requeueOrder(releaseEntry.getPacketId());
		            processReleaseLMSHelper.processFromReceivedToOFD_ML(releaseEntry);
		            mapper.mapReleaseTrip(releaseEntry);
		            processReleaseLMSHelper.processFromOFDtoFDforML_TOD(releaseEntry);
		            lmsServiceHelper.requeueOrder(releaseEntry.getPacketId());
		            processReleaseLMSHelper.processFromReceivedToOFD_ML(releaseEntry);
		            mapper.mapReleaseTrip(releaseEntry);
		            processReleaseLMSHelper.processFromOFDtoDLforML_TOD(releaseEntry);
		        }
		        else {
		            processReleaseLMSHelper.processFromOFDtoFDforML(releaseEntry);
		            lmsServiceHelper.requeueOrder(releaseEntry.getPacketId());
		            processReleaseLMSHelper.processFromReceivedToOFD_ML(releaseEntry);
		            mapper.mapReleaseTrip(releaseEntry);
		            processReleaseLMSHelper.processFromOFDtoFDforML(releaseEntry);
		            lmsServiceHelper.requeueOrder(releaseEntry.getPacketId());
		            processReleaseLMSHelper.processFromReceivedToOFD_ML(releaseEntry);
		            mapper.mapReleaseTrip(releaseEntry);
		            processReleaseLMSHelper.processFromOFDtoDLforML(releaseEntry);
		        }
		    }else {
		        processReleaseLMSHelper.processFromOFDtoFDfor3PL(releaseEntry);
		        processReleaseLMSHelper.processFromSHtoOFD3PL(releaseEntry);
		        processReleaseLMSHelper.processFromOFDtoFDfor3PL(releaseEntry);
		        processReleaseLMSHelper.processFromSHtoOFD3PL(releaseEntry);
		        processReleaseLMSHelper.processFromOFDtoDLfor3PL(releaseEntry);
		    }
	    }
	}

	public void processFromSHtoLOST(ReleaseDetailsEntryList releaseDetailsEntryList) throws IOException, JAXBException, ManagerException, InterruptedException, XMLStreamException, JSONException {
	    
		Map<String, ReleaseDetailsEntry> packetIdReleaseDetailsEntryMap = packetIdReleaseDetailsEntryMap(releaseDetailsEntryList);

	    for(String packetId : packetIdReleaseDetailsEntryMap.keySet()) {
	    	
	    		ReleaseDetailsEntry releaseEntry = packetIdReleaseDetailsEntryMap.get(packetId);
			log.info("Processing packet SH to LOST for packetId "+ releaseEntry.getPacketId());
			LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		    ProcessReleaseLMSHelper processReleaseLMSHelper = new ProcessReleaseLMSHelper();
		    mapper.mapReleaselms(releaseEntry);
		    if (!lmsServiceHelper.getOrderStatusFromLMS(releaseEntry.getPacketId()).equals(ReleaseStatus.SHIPPED.name()))
		        return;
		    if (releaseEntry.getCourierCode().equals(CourierCode.ML.toString())) {
		        if (!lmsServiceHelper.getOrderStatusFromML(releaseEntry.getPacketId()).equals(ReleaseStatus.UNASSIGNED.name()))
		            return;
		        processReleaseLMSHelper.processFromSHtoLOSTforML(releaseEntry);
		    }else
		        processReleaseLMSHelper.processFromSHtoLOSTfor3PL(releaseEntry);
	    }
	}

}
