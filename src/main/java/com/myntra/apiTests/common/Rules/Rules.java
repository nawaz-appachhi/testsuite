package com.myntra.apiTests.common.Rules;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.Constants.ShipmentSource;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.RulesEntry;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.commons.exception.ManagerException;

/**
 * Created by Shubham Gupta on 8/5/17.
 */
public class Rules {
	
	private static Logger log = LoggerFactory.getLogger(Rules.class);

    public List<RulesEntry> get(ReleaseEntry releaseEntry) throws InterruptedException, IOException, JAXBException, ManagerException, XMLStreamException, JSONException {
       
    		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        LMSHelper lmsHelper = new LMSHelper();
        RulesToStatus rulesToStatus = new RulesToStatus();
        String currentStatus = null;
                
        if (releaseEntry.getShipmentSource()== ShipmentSource.MYNTRA){
        	
        	    currentStatus = omsServiceHelper.getOrderReleaseStatusFromOMS(releaseEntry.getReleaseId());
        	    log.info("Current status of release - "+currentStatus);

        	    if (currentStatus.equals("PK")||currentStatus.equals("SH")||currentStatus.equals("S")||currentStatus.equals("DL")||currentStatus.equals("RTO")||currentStatus.equals("L")){
	                
        	    			String packetId =  ""+omsServiceHelper.getOrderReleaseEntry(releaseEntry.getReleaseId()).getOrderLines().get(0).getPacketId();
        	    			
        	    			if(packetId != null && !packetId.isEmpty()) { 
        	    				
        	    				releaseEntry.setPacketId(packetId);
	        	    			currentStatus = lmsServiceHelper.getOrderStatusFromLMS(releaseEntry.getPacketId());
		                if (currentStatus.equals("ADDED_TO_MB")) {
		                    String mbStatus = lmsHelper.getMasterBagStatus(Long.parseLong(lmsHelper.getMasterBagId(releaseEntry.getPacketId())));
		                    if (mbStatus.equals("CLOSED")){
		                        currentStatus = "CLOSED";
		                    }
		                } 
		                if (currentStatus == null || currentStatus.equals("false")) {     	    			   
     	    			   		ExceptionHandler.fail("Packet is not created in LMS for");
     	    		   		}
        	    		   } else {        	    			   
        	    			   ExceptionHandler.fail("Packet is not created in OMS");
        	    		   }
	            }
        }else {
        	
            currentStatus = lmsServiceHelper.getOrderStatusFromLMS(releaseEntry.getReleaseId());
            
            if (currentStatus == null || currentStatus.equals("false")) {     	    			   
			   	ExceptionHandler.fail("Packet Id is not created in LMS");
		   	} else {
		   		
		   		String packetId =  ""+omsServiceHelper.getOrderReleaseEntry(releaseEntry.getReleaseId()).getOrderLines().get(0).getPacketId();
		   		releaseEntry.setPacketId(packetId);
		   		if (currentStatus.equals("ADDED_TO_MB")) {
	                String mbStatus = lmsHelper.getMasterBagStatus(Long.parseLong(lmsHelper.getMasterBagId(releaseEntry.getPacketId())));
	                if (mbStatus.equals("CLOSED")){
	                    currentStatus = "CLOSED";
	                }
	            }
            }
        }
        if (releaseEntry.getToStatus() == ReleaseStatus.FD)
            return computeRules(rulesToStatus.getRuleFD(releaseEntry), currentStatus, releaseEntry.getToStatus().name());
        else if (releaseEntry.getToStatus() == ReleaseStatus.FDDL)
            return computeRules(rulesToStatus.getRuleFDDL(releaseEntry), currentStatus, releaseEntry.getToStatus().name());
        else if (releaseEntry.getToStatus() == ReleaseStatus.FDFDDL)
            return computeRules(rulesToStatus.getRuleFDFDDL(releaseEntry), currentStatus, releaseEntry.getToStatus().name());
        else if (releaseEntry.getToStatus() == ReleaseStatus.FDTODL)
            return computeRules(rulesToStatus.getRuleFDTODL(releaseEntry), currentStatus, releaseEntry.getToStatus().name());
        else if (releaseEntry.getToStatus() == ReleaseStatus.UNRTO)
            return computeRules(rulesToStatus.getRuletoUNRTO(releaseEntry), currentStatus, releaseEntry.getToStatus().name());
        else if (releaseEntry.getToStatus() == ReleaseStatus.RTO)
            return computeRules(rulesToStatus.getRuletoRTO(releaseEntry), currentStatus, releaseEntry.getToStatus().name());
        else if (releaseEntry.getToStatus() == ReleaseStatus.SMDL)
            return computeRules(rulesToStatus.getRuletoSMDL(releaseEntry), currentStatus, releaseEntry.getToStatus().name());
        else if (releaseEntry.getToStatus() == ReleaseStatus.LOST)
            return computeRules(rulesToStatus.getRuletoLOST(releaseEntry), currentStatus, releaseEntry.getToStatus().name());
        else
            return computeRules(rulesToStatus.getRuleDefault(releaseEntry), currentStatus, releaseEntry.getToStatus().name());
    }

    public List<RulesEntry> computeRules(List<RulesEntry> rulesEntries, String currentStatus, String toStatus){
        
    		RulesEntry fromIndex = null;
        RulesEntry toIndex = null;
        log.info("CurrentStatus - "+currentStatus+" || toStatus - "+toStatus);
        if (currentStatus.equals(mapToStatusToActualStatus(toStatus)))
            return null;
        else {
            fromIndex = rulesEntries.stream()
                    .filter(rule -> mapActualStatusToToStatus(currentStatus).equals(rule.getStatus().name()))
                    .findFirst()
                    .get();

            toIndex = rulesEntries.stream()
                    .filter(rule -> toStatus.equals(rule.getStatus().name()))
                    .findFirst()
                    .get();
        }
        int from = fromIndex.getId();
        int to = toIndex.getId();

        List<RulesEntry> rulesToexecute = rulesEntries.stream()
                .filter(rulesEntry -> rulesEntry.getId() > from)
                .filter(rulesEntry -> rulesEntry.getId() <= to)
                .sorted(Comparator.comparing(RulesEntry::getId))
                .collect(Collectors.toList());
        return rulesToexecute;
    }

    public String mapToStatusToActualStatus(String status){
        Map<String, String> map = new HashMap<>();
        map.put("PP","PP");
        map.put("PV", "PV");
        map.put("Q","Q");
        map.put("WP","WP");
        map.put("PK","PACKED");
        map.put("IS","INSCANNED");
        map.put("ADDED_TO_MB","ADDED_TO_MB");
        map.put("CLOSED","CLOSED");
        map.put("SH","SHIPPED");
        map.put("S","SHIPPED");
        map.put("OFD","OUT_FOR_DELIVERY");
        map.put("LOST","LOST");
        map.put("UNRTO","RTO_CONFIRMED");
        map.put("RTO","RTO_CONFIRMED");
        map.put("FD","FAILED_DELIVERY");
        map.put("FDTODL","FAILED_DELIVERY");
        map.put("FDDL","DELIVERED");
        map.put("FDFDDL","DELIVERED");
        map.put("SMDL","DELIVERED");
        map.put("DL","DELIVERED");
        map.put("D","DELIVERED");
        map.put("C","DELIVERED");
        return map.get(status);
    }

    public String mapActualStatusToToStatus(String status){
        Map<String, String> map = new HashMap<>();
        map.put("PP","PP");
        map.put("PV","PV");
        map.put("Q","Q"); 
        map.put("WP","WP");
        map.put("PACKED","PK");
        map.put("INSCANNED","IS");
        map.put("ADDED_TO_MB","ADDED_TO_MB");
        map.put("CLOSED","CLOSED");
        map.put("SHIPPED","SH");
        map.put("S","SH");
        map.put("OUT_FOR_DELIVERY","OFD");
        map.put("LOST","LOST");
        map.put("RTO_CONFIRMED","RTO");
        map.put("FAILED_DELIVERY","FD");
        map.put("DELIVERED","DL");
        return map.get(status);
    }


}
