package com.myntra.apiTests.common.ProcessOrder.Service;

import org.apache.log4j.Logger;

import com.myntra.apiTests.common.entries.OrderEntry;

/**
 * Created by Shubham Gupta on 9/2/17.
 */
public class ProcessOrderFactory {
    private static Logger log = Logger.getLogger(ProcessOrderFactory.class);

    public ProcessOrderService getProcess(OrderEntry orderEntry){
        if (orderEntry.getReleaseEntries()!=null)
            return new ProcessReleaseList();
         else if (orderEntry.getStoreOrderId()!=null)
             return new ProcessOrderByStoreId();
         else if (orderEntry.getOrderId()!=null)
             return new ProcessOrderByOrderId();
         else {
             log.info("Unable to create Proper Factory. Please pass correct parameters \n" + orderEntry.toString());
             return null;
        }
    }

}
