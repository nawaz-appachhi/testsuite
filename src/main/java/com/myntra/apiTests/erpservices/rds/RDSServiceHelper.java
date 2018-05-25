/**
 * 
 */
package com.myntra.apiTests.erpservices.rds;

import com.myntra.apiTests.erpservices.Constants;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.rds.client.entry.LogParameters;
import com.myntra.rds.client.entry.RDSLookUpEntry;
import com.myntra.rds.client.request.RDSLookUpRequest;
import com.myntra.rds.client.response.RDSLookUpResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author santwana.samantray
 *
 */
public class RDSServiceHelper {
	static Initialize init = new Initialize("/Data/configuration");

	static Logger log = Logger.getLogger(RDSServiceHelper.class);

	private static HashMap<String, String> getRDSHeader() {
		HashMap<String, String> rdsHeaders = new HashMap<String, String>();
		rdsHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		rdsHeaders.put("Content-Type", "Application/xml");
		return rdsHeaders;
	}
		//String articleName, String brandName,String color,String gender,String gtin,String size,long piSkuId,double qty,long skuId,String vendorArticleName,String vendorArticleNo,String[] logparameterlist 
		public static RDSLookUpResponse splitOrder(String[] lookupentries,String [] logparameters  ) throws JAXBException, UnsupportedEncodingException
		{
			RDSLookUpRequest rDSLookUpRequest = new RDSLookUpRequest();
			List rdsEntryList =new ArrayList();
			List loglist=new ArrayList();
			for (String object : lookupentries) {
				String[] lookupentry = object.split(",");			//rDSLookUpRequest.setEntriesList(entriesList);
			RDSLookUpEntry rDSLookUpEntries= new RDSLookUpEntry();
			rDSLookUpEntries.setArticleName(lookupentry[0]);
			rDSLookUpEntries.setBrandName(lookupentry[1]);
			rDSLookUpEntries.setColor(lookupentry[2]);
			rDSLookUpEntries.setGender(lookupentry[3]);
			rDSLookUpEntries.setGtin(lookupentry[4]);
			rDSLookUpEntries.setPiSkuId(Long.parseLong(lookupentry[5]));
			rDSLookUpEntries.setQty(Double.parseDouble(lookupentry[6]));
			rDSLookUpEntries.setSize(lookupentry[7]);
			rDSLookUpEntries.setSkuId(Long.parseLong(lookupentry[8]));
			rDSLookUpEntries.setVendorArticleName(lookupentry[9]);
			rDSLookUpEntries.setVendorArticleNo(lookupentry[10]);
			
			for (String object1 : logparameters) {
				String[] logdetails = object1.split(",");
			LogParameters logparameter = new LogParameters();
			logparameter.setPoSkuId(Long.parseLong(logdetails[0]));
			logparameter.setStateId(Long.parseLong(logdetails[1]));
			loglist.add(logparameter);
			}
			
			rDSLookUpEntries.setLogParametersList(loglist);
			rdsEntryList.add(rDSLookUpEntries);
			}
			rDSLookUpRequest.setEntriesList(rdsEntryList);
			String payload = APIUtilities.convertXMLObjectToString(rDSLookUpRequest);

			Svc service = HttpExecutorService.executeHttpService(Constants.RDS_PATH.SPLITPI, null,SERVICE_TYPE.RDS_SVC.toString(), HTTPMethods.POST, payload, getRDSHeader());
			RDSLookUpResponse inventoryDemandInfoResponse = (RDSLookUpResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new RDSLookUpResponse());
				System.out.println("response is:"+inventoryDemandInfoResponse);
		      return inventoryDemandInfoResponse;
		}
	
	
}
