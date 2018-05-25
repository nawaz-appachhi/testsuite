package com.myntra.apiTests.common.ProcessOrder.ServiceImpl;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.entries.ReleaseDetailsEntry;
import com.myntra.apiTests.common.entries.ReleaseMapper;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.commons.exception.ManagerException;
import com.myntra.oms.client.entry.OrderReleaseEntry;

public class ProcessReleaseExecutorHelper {
	
	//private static Logger log = Logger.getLogger(ProcessReleaseExecutor.class);
	private ReleaseMapper mapper = new ReleaseMapper();
	
	public void processReleaseToWPHelper(ReleaseDetailsEntry releaseEntry) throws UnsupportedEncodingException, JAXBException, ManagerException{
    	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        End2EndHelper end2EndHelper = new End2EndHelper();
        mapper.mapReleaseOms(releaseEntry);
        OrderReleaseEntry orderRelease = omsServiceHelper.getOrderReleaseEntry(releaseEntry.getReleaseId());
        if (orderRelease.getStatus().equals(ReleaseStatus.Q.toString())||
                orderRelease.getStatus().equals(ReleaseStatus.WP.toString())||
                orderRelease.getStatus().equals(ReleaseStatus.RFR.toString())||
                orderRelease.getStatus().equals(ReleaseStatus.PP.toString())||
                orderRelease.getStatus().equals(ReleaseStatus.PV.toString())) {
            	omsServiceHelper.validateOrderOFFHoldStatusInOMS(releaseEntry.getReleaseId(), 15);
            if (orderRelease.getStatus().equals(ReleaseStatus.Q.toString())) {
                omsServiceHelper.resolveOnholdOrders(orderRelease.getOrderId().toString());
                omsServiceHelper.resolveOnHoldOrderRelease(releaseEntry.getReleaseId());
            }
            if (omsServiceHelper.getOrderReleaseStatusFromOMS(releaseEntry.getReleaseId()).equals(ReleaseStatus.Q.toString()))
                ExceptionHandler.handleTrue(end2EndHelper.resolveOrderOnHold34AndMarkOrderWP(releaseEntry.getReleaseId()), "order release is not in WP status");
            	ExceptionHandler.handleTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseEntry.getReleaseId(), EnumSCM.WP, 10), "Order Not in WP");
        }else return;
    }


}
