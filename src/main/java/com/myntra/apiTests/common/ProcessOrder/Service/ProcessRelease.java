package com.myntra.apiTests.common.ProcessOrder.Service;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.ServiceImpl.ProcessReleaseExecutor;
import com.myntra.apiTests.common.Rules.Rules;
import com.myntra.apiTests.common.entries.*;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.commons.exception.ManagerException;
import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham Gupta on 8/1/17.
 */
public class ProcessRelease {

    private static Logger log = LoggerFactory.getLogger(ProcessRelease.class);
    
    List<ReleaseEntry> releaseEntriesInWP = new ArrayList<>();
    List<ReleaseEntry> releaseEntriesMoreThanWP = new ArrayList<>();
    List<ReleaseEntry> releaseEntriesLessThanWP = new ArrayList<>();
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    
    
    /**
     * @param releaseEntryList
     * @throws JAXBException
     * @throws ManagerException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws InterruptedException
     * @throws SQLException
     * @throws XMLStreamException
     * @throws JSONException
     */
    public void processReleaseToStatusHelper(ReleaseEntryList releaseEntryList) throws JAXBException, ManagerException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, IOException, InterruptedException, SQLException, XMLStreamException, JSONException{
    	log.info("Executing processReleaseToStatusHelper function");
    	
    	for(ReleaseEntry releaseEntry:releaseEntryList.getReleaseEntries()){
    		String releaseId = releaseEntry.getReleaseId();
    		String status = ""+omsServiceHelper.getOrderReleaseEntry(releaseId).getStatus();
    		if(status.equalsIgnoreCase(EnumSCM.WP)){
    			releaseEntriesInWP.add(releaseEntry);
    		}else if(status.equalsIgnoreCase(EnumSCM.PK)||status.equalsIgnoreCase(EnumSCM.SH)
    				||status.equalsIgnoreCase(EnumSCM.DL)||status.equalsIgnoreCase(EnumSCM.C)
    				||status.equalsIgnoreCase(EnumSCM.RTO)||status.equalsIgnoreCase(EnumSCM.L)){
    			releaseEntriesMoreThanWP.add(releaseEntry);
    		}else{
    			releaseEntriesLessThanWP.add(releaseEntry);
    		}
    	}
    	
    	processAllTypeReleases();

    }
    
    /**
     * @throws ManagerException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws JAXBException
     * @throws IOException
     * @throws InterruptedException
     * @throws SQLException
     * @throws XMLStreamException
     * @throws JSONException
     */
    private void processAllTypeReleases() throws ManagerException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, JAXBException, IOException, InterruptedException, SQLException, XMLStreamException, JSONException {
		// TODO Auto-generated method stub
    	
    	log.info("Processing all releases");
    	if(releaseEntriesInWP.size()>0){//Process all the release in WP status
    		processReleaseToStatus(getReleaseEntryList(releaseEntriesInWP));
    	}
    	
    	if(releaseEntriesLessThanWP.size()>0){//Process All releasesIn LessThan WP status
    		processReleaseToStatus(getReleaseEntryList(releaseEntriesLessThanWP));
    	}
    	
    	
    	if(releaseEntriesMoreThanWP.size()>0){ //Sending Releases one by one as Multiple packets can be in multiple status
        	for(ReleaseEntry releaseEntry:releaseEntriesMoreThanWP){
        		List<ReleaseEntry> releaseEntryTemp = new ArrayList<>();
        		releaseEntryTemp.add(releaseEntry);
        		processReleaseToStatus(getReleaseEntryList(releaseEntryTemp));
        	}
    	}
	}

	/**
	 * @param releaseEntries
	 * @return
	 */
	public ReleaseEntryList getReleaseEntryList(List<ReleaseEntry> releaseEntries){
    	ReleaseEntryList releaseEntryList = new ReleaseEntryList();
    	releaseEntryList.setReleaseEntries(releaseEntries);
		return releaseEntryList;
    }
    
    
    /**
     * @param releaseEntryList
     * @throws JAXBException
     * @throws IOException
     * @throws ManagerException
     * @throws InterruptedException
     * @throws SQLException
     * @throws XMLStreamException
     * @throws JSONException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public void processReleaseToStatus(ReleaseEntryList releaseEntryList) throws JAXBException, IOException, ManagerException, InterruptedException,
            SQLException, XMLStreamException, JSONException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        ReleaseMapper mapper = new ReleaseMapper();
        Rules rules = new Rules();
		List<RulesEntry> rulesList = null;
        ReleaseDetailsEntryList releaseDetailsEntryList = new ReleaseDetailsEntryList();
        releaseDetailsEntryList.setReleaseDetailsEntries(mapper.mapReleaseToReleasDetail(releaseEntryList, "beforePK"));

        for(ReleaseEntry releaseEntry : releaseEntryList.getReleaseEntries()){
        	
			rulesList = rules.get(releaseEntry);
			rulesList.stream().forEach(rule->log.info(rule.toString()));
        }
        
                
        List<RulesEntry> beforePKRulesList = new ArrayList<>();
        List<RulesEntry> afterPKRulesList = new ArrayList<>();
        
		for (RulesEntry rulesEntry : rulesList) {

			if (rulesEntry.getMethodName().contains(ReleaseStatus.WP.toString())) {
				beforePKRulesList.add(rulesEntry);
			} else {
				afterPKRulesList.add(rulesEntry);
			}
		}

        if(beforePKRulesList != null && !beforePKRulesList.isEmpty()) { 		
        		applyRule(beforePKRulesList, releaseDetailsEntryList);
        } 
        	
        if(afterPKRulesList != null && !afterPKRulesList.isEmpty()) {        	
        		releaseDetailsEntryList.setReleaseDetailsEntries(mapper.mapReleaseToReleasDetail(releaseEntryList, "afterPK"));
	        	applyRule(afterPKRulesList, releaseDetailsEntryList);
        }

    }
    
    public void applyRule(List<RulesEntry> rulesList, ReleaseDetailsEntryList releaseDetailsEntryList) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        for (RulesEntry rule : rulesList) {
            log.info("Current rule - "+rule);
            Method privateMethod = ProcessReleaseExecutor.class.getDeclaredMethod(rule.getMethodName(), ReleaseDetailsEntryList.class);
            privateMethod.setAccessible(true);
            privateMethod.invoke(ProcessReleaseExecutor.class.newInstance(), releaseDetailsEntryList);
	    }
    }
}
