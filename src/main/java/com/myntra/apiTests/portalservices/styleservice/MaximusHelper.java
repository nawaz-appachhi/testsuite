package com.myntra.apiTests.portalservices.styleservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.maximus.Fields;
import com.myntra.maximus.PdpResponse;
import com.myntra.maximus.client.MaximusClient;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishwa on 31/08/17.
 */
public class MaximusHelper {
    private static int retryCount = 3;
    static Initialize init = new Initialize("/Data/configuration");
    static Logger log = Logger.getLogger(MaximusHelper.class);

    public static String getStyleDataForStyle(Long styleId) {
        String response = null;

        for(int i=0; i<retryCount && response==null; i++) {
            MyntraService maximus = Myntra.getService(ServiceType.PORTAL_MAXIMUS, APINAME.GETPDP, init.Configurations);
            String maximusURL = maximus.URL.replaceAll("/", "");
            List<Long> styleList = new ArrayList<Long>();

            styleList.add(styleId);

            Gson gson = new GsonBuilder().create();
            PdpResponse maximusResponse = new PdpResponse();

            try {
                MaximusClient maximusClient = new MaximusClient(maximusURL, Integer.parseInt("80"), 30000, 30000);
                maximusResponse = maximusClient.getClient("MYNTRA").getPdpData(styleList.get(0), Fields.ALL);
                response = gson.toJson(maximusResponse.getData().get(0));
            }
            catch (TException ex) {
                log.info("Thrift exception. Going to retry loop-"+(i+1));
            }
            catch (Exception ex) {
                log.info("Thrift exception. Going to retry loop-"+(i+1));
            }

            if(response==null || response.isEmpty())
                log.error("Failed to get response from maximus");
        }

        return response;
    }
}
