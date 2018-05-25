package com.myntra.apiTests.common.ProcessOrder.Service;

import com.myntra.apiTests.common.entries.OrderEntry;
import com.myntra.commons.exception.ManagerException;
import org.codehaus.jettison.json.JSONException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by Shubham Gupta on 9/2/17.
 */
public interface ProcessOrderService {

    void process(OrderEntry orderEntry) throws IOException, InstantiationException, JSONException, NoSuchMethodException, SQLException, InterruptedException, ManagerException, InvocationTargetException, IllegalAccessException, XMLStreamException, JAXBException;

}
