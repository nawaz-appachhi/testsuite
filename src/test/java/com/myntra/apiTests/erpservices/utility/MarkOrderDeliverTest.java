package com.myntra.apiTests.erpservices.utility;


import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ShipmentSource;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.partners.SellerServicesHelper;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.Initialize;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.sellers.response.SellerWarehouseResponse;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhijit.Pati on 29/10/15.
 */

public class MarkOrderDeliverTest extends BaseTest {

	static Initialize init = new Initialize("/Data/configuration");
	private static Logger log = Logger.getLogger(MarkOrderDeliverTest.class);
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	End2EndHelper end2EndHelper = new End2EndHelper();
	ProcessRelease processRelease = new ProcessRelease();
	CreateAndMarkOrderDeliver createAndMarkOrderDeliver = new CreateAndMarkOrderDeliver();
	WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
	List<OrderLineEntry> orderLineEntries = null;
	
	@Test
	public void markOrderDelivered() throws Exception {

		String id = System.getenv("Id").replace("-", "");
		String status = System.getenv("ToStatus");
		String type = System.getenv("Type");
		String origOrderId = null;

		if (null == id || id.equalsIgnoreCase("")) {
			log.info("Please pass the Order/Release ID to Proceed");
			Assert.fail();
		}

		if (type == null || type.isEmpty()) {
			type = "storeOrderId";
		}

		if (status == null || status.isEmpty()) {
			status = "DL";
		}

		validateHealthCheckofServices();
		log.info("HealthCheck for services completed, there is no issues with services");
		
		log.info("Start Processing For ID " + id + "------ To " + status);

		String finalReleaseToBeMarkedAsDL = null;

		// If User is providing type as StoreOrderId or orderId we will always
		// move first release in particular status
		if (type.equalsIgnoreCase("storeOrderID")) {

			finalReleaseToBeMarkedAsDL = omsServiceHelper.getReleaseIdFromStoreOrderId(id);

		} else if (type.equalsIgnoreCase("order")) {

			finalReleaseToBeMarkedAsDL = omsServiceHelper.getReleaseId(id);

		} else if (type.equalsIgnoreCase("release")) { // If user is providing
														// release then we will
														// move that release in
														// particular status
			finalReleaseToBeMarkedAsDL = id;
		}

		// Hack to push confirmOrder till m7 issue is fixed, Removing this Hack
		// as cluster is working fine now
		/*
		 * if(type.equalsIgnoreCase("release") &&
		 * (status.equalsIgnoreCase("Q")||status.equalsIgnoreCase("RFR"))){
		 * origOrderId =
		 * omsServiceHelper.getOrderReleaseEntry(finalReleaseToBeMarkedAsDL).
		 * getOrderId().toString();
		 * omsServiceHelper.checkReleaseStatusForOrder(origOrderId, "WP"); }
		 */
		
		//Only If release is in WP status then only item association need to check
		//No need to check for orders more than WP status
		String releaseStatus = omsServiceHelper.getOrderReleaseEntry(finalReleaseToBeMarkedAsDL).getStatus();
		if(releaseStatus.equalsIgnoreCase(EnumSCM.WP)){
			updateLineStatusToActiveForOrderRelease(finalReleaseToBeMarkedAsDL);
		}


		// Fixed it for Single Release Order
		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(finalReleaseToBeMarkedAsDL, createAndMarkOrderDeliver.mapStatus(status)).shipmentSource(ShipmentSource.MYNTRA).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);
	}
	
	/**
	 * This function will validate if service is running and giving 200 response
	 * @throws JAXBException
	 * @throws IOException
	 * @throws ManagerException
	 */
	public void validateHealthCheckofServices() throws JAXBException, IOException, ManagerException{
		//Validate omsservice
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		//Validate munshi service
		omsServiceHelper.refreshMunshiApplicationPropertyCache();
		//Validate wormsService
		wmsServiceHelper.refreshWormsApplicationPropertyCache();
		//Validate Wmsservice
		wmsServiceHelper.refreshWMSApplicationPropertyCache();
		//Validate LMS service
		lmsServiceHelper.refreshLMSApplicationPropertyCache();
		//Validate tms service
		//lmsServiceHelper.refreshTMSApplicationPropertyCache();
		//Validate sellerServices service
		SellerWarehouseResponse response = sellerServicesHelper.getWarehouseBySeller("21");
		ExceptionHandler.handleEquals(response.getStatus().getStatusType().toString(), Type.SUCCESS.toString(),"There is some error in sellerServices");
	}
	
	/**
	 * This function will move orderLine to A status only if item association is not done
	 * @param orderReleaseId
	 * @throws ManagerException
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	private void updateLineStatusToActiveForOrderRelease(String orderReleaseId) throws ManagerException, UnsupportedEncodingException, JAXBException{
		List resultSet = wmsServiceHelper.getItemEntryInWMS("order_id", orderReleaseId);
		if(resultSet==null){ //verify Item association is not done
			orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
			for(OrderLineEntry orderLineEntry:orderLineEntries){
				String currentLineStatus = orderLineEntry.getStatus();
				
				if(currentLineStatus.equalsIgnoreCase(EnumSCM.QD)){
					String updatedStatus = omsServiceHelper.updateOrderLineStatusDB(orderLineEntry.getId().toString(),EnumSCM.A);
					log.info("OrderLine "+orderLineEntry.getId()+" status moved to "+updatedStatus+" successfully");
				}

			}
			
		}else{
			log.info("This order cannot be processed further please place new order and try again");
			ExceptionHandler.fail("Quit the job");// Added above log till jenkins job not showing correct error output
		}
	}
}