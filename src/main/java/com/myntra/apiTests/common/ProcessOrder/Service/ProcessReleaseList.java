package com.myntra.apiTests.common.ProcessOrder.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.codehaus.jettison.json.JSONException;

import com.myntra.apiTests.common.entries.OrderEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.commons.exception.ManagerException;

/**
 * Created by Shubham Gupta on 9/2/17.
 */
public class ProcessReleaseList implements ProcessOrderService {
    @Override
    public void process(OrderEntry orderEntry) throws IOException, InstantiationException, JSONException, NoSuchMethodException,
            SQLException, InterruptedException, ManagerException, InvocationTargetException, IllegalAccessException, XMLStreamException, JAXBException {
        ProcessRelease processRelease = new ProcessRelease();
        ReleaseEntryList releaseEntryList = new ReleaseEntryList();
        releaseEntryList.setReleaseEntries(orderEntry.getReleaseEntries());
        processRelease.processReleaseToStatus(releaseEntryList);
    }
}
