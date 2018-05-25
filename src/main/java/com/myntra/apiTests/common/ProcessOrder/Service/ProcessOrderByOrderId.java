package com.myntra.apiTests.common.ProcessOrder.Service;

import com.myntra.apiTests.common.entries.OrderEntry;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.commons.exception.ManagerException;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import org.codehaus.jettison.json.JSONException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham Gupta on 9/2/17.
 */
public class ProcessOrderByOrderId implements ProcessOrderService {
    @Override
    public void process(OrderEntry orderEntry) throws JAXBException, IOException, InstantiationException, JSONException, NoSuchMethodException, SQLException, InterruptedException, ManagerException, InvocationTargetException, IllegalAccessException, XMLStreamException {
        ProcessRelease processRelease = new ProcessRelease();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        
        ReleaseEntryList releaseEntryList = new ReleaseEntryList();
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        
        for (OrderReleaseEntry orderReleaseEntry: omsServiceHelper.getOrderEntry(orderEntry.getOrderId()).getOrderReleases()) {
        	releaseEntries.add(new ReleaseEntry.Builder(orderReleaseEntry.getId().toString(),
                    orderEntry.getToStatus()).force(orderEntry.getForce()).build());
        }
        releaseEntryList.setReleaseEntries(releaseEntries);
        processRelease.processReleaseToStatus(releaseEntryList);

    }
}
