package com.myntra.apiTests.erpservices.lms.tests;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.TMSServiceHelper;
import com.myntra.apiTests.erpservices.lms.dp.LMSTestsDP;
import com.myntra.apiTests.erpservices.lms.lmsClient.JabongCreateShipmentResponse;
import com.myntra.apiTests.erpservices.lms.lmsClient.Shipment;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lms.client.response.*;
import com.myntra.lms.client.status.OrderShipmentAssociationStatus;
import com.myntra.lms.client.status.ShipmentStatus;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lms.client.status.ShippingMethod;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.tms.hub.TMSHubResponse;
import com.myntra.tms.config.hub_to_transporthub_config.LocationHubToTransportHubConfigResponse;
import com.myntra.tms.config.lane_hub_config.LaneHubConfigResponse;
import com.myntra.tms.config.supported_transport_hub_config.SupportedTransportHubConfigResponse;
import com.myntra.tms.config.transporter_lane_config.TransporterLaneConfigResponse;
import com.myntra.tms.container.ContainerResponse;
import com.myntra.tms.domain.TMSMasterbagStatus;
import com.myntra.tms.lane.LaneResponse;
import com.myntra.tms.masterbag.TMSMasterbagEntry;
import com.myntra.tms.masterbag.TMSMasterbagReponse;
import com.myntra.tms.pendency.TmsPreAlertResponse;
import com.myntra.tms.track.TmsTrackingDetailResponse;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Shubham Gupta on 5/15/17.
 */
public class LMS_TMS extends BaseTest {

	private static org.slf4j.Logger log = LoggerFactory.getLogger(LMS_TMS.class);
	private OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	private LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	private TMSServiceHelper tmsServiceHelper = new TMSServiceHelper();
	private LMSHelper lmsHepler = new LMSHelper();

	@SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C136", dataProviderClass = LMSTestsDP.class, dataProvider = "getTransporter")
    public void getTransporter(String param, String status) throws Exception{
        Assert.assertEquals(((TransporterResponse)tmsServiceHelper.getTransporter.apply(param)).getStatus().getStatusType().toString(),status, "Unable to Search transporter");
    }

	@SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C137", dataProviderClass = LMSTestsDP.class, dataProvider = "getLane")
    public void getLane(String param, String status) throws Exception{
        Assert.assertEquals(((LaneResponse)tmsServiceHelper.getLane.apply(param)).getStatus().getStatusType().toString(),status, "Unable to Search Lane");
    }

	@SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C138", dataProviderClass = LMSTestsDP.class, dataProvider = "getTmsHub")
    public void getTmsHub(String param, String status) throws Exception{
        Assert.assertEquals(((TMSHubResponse)tmsServiceHelper.getTmsHub.apply(param)).getStatus().getStatusType().toString(),status, "Unable to Search TMS hub");
    }

	@SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C139", dataProviderClass = LMSTestsDP.class, dataProvider = "getTransportHubPendency")
    public void getTransportHubPendency(String source,String destination, String status) throws Exception{
        Assert.assertEquals(((TransportHubPendencyResponse)tmsServiceHelper.getTransportHubPendency.apply(source, destination)).getStatus().getStatusType().toString(),status, "Unable to Search TransportHubPendency");
    }

	@SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C140", dataProviderClass = LMSTestsDP.class, dataProvider = "getTransportHubPendency")
    public void getMisrouteBagsHubPendency(String source,String destination, String status) throws Exception{
        Assert.assertEquals(((TransportHubPendencyResponse)tmsServiceHelper.getMisrouteBagsHubPendency.apply(source, destination)).getStatus().getStatusType().toString(),status);
    }

	@SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C141", dataProviderClass = LMSTestsDP.class, dataProvider = "getMasterbagPreAlert")
    public void getMasterbagPreAlert(String source,String destination, String transporter,String status) throws Exception{
        Assert.assertEquals(((TmsPreAlertResponse)tmsServiceHelper.getMasterbagPreAlert.apply(source, destination,transporter)).getStatus().getStatusType().toString(),status);
    }

    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C142")
    public void downloadTransporterLaneConfig() throws Exception{
        Assert.assertEquals(((TransporterLaneConfigResponse)tmsServiceHelper.downloadTransporterLaneConfig.get()).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
    }

    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C143")
    public void downloadLocationHubToTransportHubConfig() throws Exception{
        Assert.assertEquals(((LocationHubToTransportHubConfigResponse)tmsServiceHelper.downloadLocationHubToTransportHubConfig.get()).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
    }

    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C144")
    public void downloadLaneHubConfig() throws Exception{
        Assert.assertEquals(((LaneHubConfigResponse)tmsServiceHelper.downloadLaneHubConfig.get()).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
    }

    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C145")
    public void downloadSupportedTransportHubConfig() throws Exception{
        Assert.assertEquals(((SupportedTransportHubConfigResponse)tmsServiceHelper.downloadSupportedTransportHubConfig.get()).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C146", dataProviderClass = LMSTestsDP.class, dataProvider = "getContainer")
    public void getContainer(String param, String status) throws Exception{
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.getContainer.apply(param)).getStatus().getStatusType().toString(),status);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C147", dataProviderClass = LMSTestsDP.class, dataProvider = "getSupportedLane")
    public void getSupportedLane(String source,String destination, String status) throws Exception{
        Assert.assertEquals(((LaneResponse)tmsServiceHelper.getSupportedLanes.apply(source, destination)).getStatus().getStatusType().toString(),status);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C148", dataProviderClass = LMSTestsDP.class, dataProvider = "getSupportedTransportersForLane")
    public void getSupportedTransportersForLane(String laneId, String status) throws Exception{
        Assert.assertEquals(((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getStatus().getStatusType().toString(),status);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C149")
    public void getTmsMasterBagById() throws Exception{
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(9988776655L)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C150")
    public void getContainerTrackingDetail() throws Exception{
        Assert.assertEquals(((TmsTrackingDetailResponse)tmsServiceHelper.getContainerTrackingDetail.apply(100018)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C151")
    public void getContainerManifest() throws Exception{
        Assert.assertEquals(((ManifestResponse)tmsServiceHelper.getContainerManifest.apply(100018)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
    }

    @SuppressWarnings("unchecked")
	@Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C152")
    public void getMasterBagManifest() throws Exception{
        String masterBagId = DBUtilities.exSelectQueryForSingleRecord("select id from masterbag where status in ('RECEIVED','IN_TRANSIT')","myntra_tms").get("id").toString();
        Assert.assertEquals(((ManifestResponse)tmsServiceHelper.getMasterBagManifest.apply(masterBagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C153",  dataProviderClass = LMSTestsDP.class, dataProvider = "addTMSTransporter")
    public void addTMSTransporter(String mode, String status) throws Exception{
        String name = "AutomationTransporter";
        DBUtilities.exUpdateQuery("delete from transporter where name = '"+name+"'", "myntra_tms");
        Assert.assertEquals(((TransporterResponse)tmsServiceHelper.addTMSTransporter.apply(name, mode)).getStatus().getStatusType().toString(), status);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C154",  dataProviderClass = LMSTestsDP.class, dataProvider = "addLane")
    public void addLane(String source, String destination, Object type ,String status) throws Exception{
        String name = "AutomationLane";
        try {
            DBUtilities.exUpdateQuery("delete from lane_hub_config where lane_id = (select id from lane where name = '" + name + "') ", "myntra_tms");
        }catch (Exception e){
            System.out.println(e.toString());
        }
        DBUtilities.exUpdateQuery("delete from lane where name = '"+name+"'", "myntra_tms");
        Assert.assertEquals(((LaneResponse)tmsServiceHelper.addLane.apply(name, source, destination, type)).getStatus().getStatusType().toString(), status);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C155",  dataProviderClass = LMSTestsDP.class, dataProvider = "addTmsHub")
    public void addTmsHub(String pincode, Object type, String status) throws Exception{
        String name = "AutomationHub";
        String code = "AUTOHUB";
        DBUtilities.exUpdateQuery("delete from hub where code = '"+code+"'", "myntra_tms");
        Assert.assertEquals(((TMSHubResponse)tmsServiceHelper.addTMSHub.apply(code, name, pincode, type)).getStatus().getStatusType().toString(), status);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C156", dataProviderClass = LMSTestsDP.class, dataProvider = "createAndReceiveMasterBagInTMS")
    public void createAndReceiveMasterBagInTMS(String source, String destination, String TH_Hub, String type,String status) throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag(source,destination,2);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply(TH_Hub, masterbagId)).getStatus().getStatusType().toString(),status);
        if (status.equals(EnumSCM.SUCCESS) && type.equals("TH") )
            Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        else
            Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.NEW, "Status not in NEW while receiving at DC or in case of error");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C157")
    public void createAndReceiveMasterBagInTMSIdempotent() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","ELC",2);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C158" , dataProviderClass = LMSTestsDP.class, dataProvider = "createContainerForSourceDest")
    public void createContainerForSourceDest(String source, String destination, String status) throws Exception {
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply(source, destination)).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        ContainerResponse containerResponse = (ContainerResponse)tmsServiceHelper.createContainer.apply(source, destination, laneId, transporterId);
        Assert.assertEquals(containerResponse.getStatus().getStatusType().toString(), status);
        Assert.assertNotNull(containerResponse.getContainerEntries().get(0).getId());
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C159", dataProviderClass = LMSTestsDP.class, dataProvider = "createContainerForMisroute")
    public void createContainerForMisroute(String source, String destination, String status) throws Exception {
        String sourceHub = source;
        String destHub = destination;
        long laneId = (long) tmsServiceHelper.getMisrouteLane.get();
        long transporterId = (long) tmsServiceHelper.getMisrouteTransporter.get();
        ContainerResponse container = (ContainerResponse) tmsServiceHelper.createContainer.apply(sourceHub, destHub, laneId, transporterId);
        Assert.assertEquals(container.getStatus().getStatusType().toString(), status);
        Assert.assertNotNull(container.getContainerEntries().get(0).getId());
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C160")
    public void addMBtoContainerIntraCity() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-DEL","CAR",2);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-DEL", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-DEL", "CAR")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-DEL", "CAR", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C161, MB of BLR TH, Try adding in DEL-BLR MB")
    public void addMBtoContainerOfWrongTH() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","CAR",2);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-DEL", "TH-BLR")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-DEL", "TH-BLR", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.ERROR);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS",  "Smoke", "Regression"}, priority = 6, description = "ID: C162, Try adding same masterBag to the same container multiple times. Should get added forst time but should throw Proper Error in further attempts")
    public void addMBtoContainerInterCityTryMutiTime() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-DEL","ELC",2);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-DEL", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-DEL", "TH-BLR")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-DEL", "TH-BLR", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.ERROR);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C163")
    public void addMBtoContainerRHD() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","RHD",2);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-BLR", "TH-MU")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-BLR", "TH-MU", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
    }

    @SuppressWarnings("unchecked")
	@Test(groups = {"TMS",  "Smoke", "Regression"}, priority = 6, description = "ID: C164, Add same masterBag to Different masterBag before shipping any, In this case the masterbag will automatically " +
            "gets removed from first container and gets added to second")
    public void addMBtoContainerInterCityMultiAddDiffMasterBagBeforeShip() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-DEL","ELC",2);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-DEL", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-DEL", "TH-BLR")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-DEL", "TH-BLR", laneId, transporterId)).getContainerEntries().get(0).getId();
        long containerId1 = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-DEL", "TH-BLR", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertNotNull(((ContainerResponse)tmsServiceHelper.getContainer.apply(containerId)).getContainerEntries().get(0).getMasterbagEntries());
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId1,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to the second Container");
        Assert.assertNull(((ContainerResponse)tmsServiceHelper.getContainer.apply(containerId)).getContainerEntries().get(0).getMasterbagEntries());
        Assert.assertNotNull(((ContainerResponse)tmsServiceHelper.getContainer.apply(containerId1)).getContainerEntries().get(0).getMasterbagEntries());
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS",  "Smoke", "Regression"}, priority = 6, description = "ID: C165, Add same masterBag to Different masterBag After shipping first, In this case if we try it to add into other container: " +
            "It should throw error and should net get added to second container")
    public void addMBtoContainerInterCityMultiAddDiffMasterBagAfterShip() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-DEL","ELC",2);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-DEL", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-DEL", "TH-BLR")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-DEL", "TH-BLR", laneId, transporterId)).getContainerEntries().get(0).getId();
        long containerId1 = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-DEL", "TH-BLR", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertNotNull(((ContainerResponse)tmsServiceHelper.getContainer.apply(containerId)).getContainerEntries().get(0).getMasterbagEntries());
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to ship the container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId1,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.ERROR);
        Assert.assertNotNull(((ContainerResponse)tmsServiceHelper.getContainer.apply(containerId)).getContainerEntries().get(0).getMasterbagEntries());
        Assert.assertNull(((ContainerResponse)tmsServiceHelper.getContainer.apply(containerId1)).getContainerEntries().get(0).getMasterbagEntries());
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS",  "Smoke", "Regression"}, priority = 6, description = "ID: C166, Forward masterBag add to container without receive. As its forward MB so it should throw ERROR")
    public void addMBtoContainerInterCityWithOutReceiveFW() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-DEL","ELC",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.NEW);
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-DEL", "TH-BLR")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-DEL", "TH-BLR", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.ERROR);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C167, Reverse masterBag add to container without receive. As its Reverse MB so it should SUCCESS")
    public void addMBtoContainerInterCityWithOutReceiveReverse() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("T-JPR","TH-BLR",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.NEW);
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("T-JPR", "TH-JPR")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("T-JPR", "TH-JPR", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C168")
    public void addMBtoContainerWrongSource() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-DEL","ELC",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-DEL", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-MU", "TH-BLR")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-MU", "TH-BLR", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.ERROR);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS",  "Smoke", "Regression"}, priority = 6, description = "ID: C169, Destination does not matching and does not comes in the same Lane route. Should throw ERROR") //Bug
    public void addMBtoContainerWrongContainerGoingWrongDestination() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-DEL","ELC",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-DEL", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-DEL", "TH-GWH")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-DEL", "TH-GWH", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.ERROR);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS",  "Smoke", "Regression"}, priority = 6, description = "ID: C170, here Lane supports that Hub but we will create container which is ending before that. It should throw ERROR.")
    public void addMBtoContainerDestinationSupporterButNotGoingUptoDest() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","T-AZ",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-BLR", "TH-HYD")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-BLR", "TH-HYD", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.ERROR);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C171")
    public void addMBtoContainerMisrouteCorrect() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","CAR",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-BLR", "TH-DEL")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-BLR", "TH-DEL", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBagForce.apply("TH-JPR",masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        long laneIdmis = (long)tmsServiceHelper.getMisrouteLane.get();
        long transporterIdmis = (long)tmsServiceHelper.getMisrouteTransporter.get();
        long containerIdmis = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-JPR", "CAR", laneIdmis, transporterIdmis)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerIdmis,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerIdmis)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C172")
    public void addMBtoContainerMisrouteWrongSource() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","CAR",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-BLR", "TH-DEL")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-BLR", "TH-DEL", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBagForce.apply("TH-JPR",masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        long laneIdmis = (long)tmsServiceHelper.getMisrouteLane.get();
        long transporterIdmis = (long)tmsServiceHelper.getMisrouteTransporter.get();
        long containerIdmis = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-HYD", "CAR", laneIdmis, transporterIdmis)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerIdmis,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.ERROR);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C173")
    public void addMBtoContainerMisrouteWrongDestination() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","CAR",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-BLR", "TH-DEL")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-BLR", "TH-DEL", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBagForce.apply("TH-JPR",masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        long laneIdmis = (long)tmsServiceHelper.getMisrouteLane.get();
        long transporterIdmis = (long)tmsServiceHelper.getMisrouteTransporter.get();
        long containerIdmis = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-JPR", "ELC", laneIdmis, transporterIdmis)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerIdmis,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.ERROR);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C174")
    public void addMultiMBtoContainerWithFailed() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","T-AZ",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");

        String masterbagId1 = tmsServiceHelper.pushMockTMSMasterBag("DH-DEL","T-AZ",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-DEL", masterbagId1)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId1)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");

        String masterbagId2 = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","T-JPR",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId2)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId2)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");

        String masterbagId3 = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","CAR",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId3)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId3)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");

        String masterbagId4 = tmsServiceHelper.pushMockTMSMasterBag("ELC","RT-DEL",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId4)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId4)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");

        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-BLR", "TH-GWH")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-BLR", "TH-GWH", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId1)).getStatus().getStatusType().toString(), EnumSCM.ERROR);
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId2)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId3)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId4)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to ship container");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C175, Here We have a Lane which goes from TH-JPR -> T-JPR -> T-KT and we add Container from " +
            "TH-JPR->T-JPR so its not going upto T-KT and trying to add MB which belongs to T-KT so it should fail and throw ERROR")
    public void addMBtoContainerWithIntracityCorrectLaneButNotGoingUptoDest() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","T-KT",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        tmsServiceHelper.intercityTransfer.accept(masterbagId);
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-JPR", "T-JPR")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-JPR", "T-JPR", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.ERROR);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C176")
    public void addMBtoContainerSupportedLaneFW() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","T-JPR",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);

        String masterbagId1 = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","T-KT",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId1)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);

        String masterbagId2 = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","T-PK",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId2)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);

        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(4L)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-BLR", "TH-DEL", 4L, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId1)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId2)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus(),TMSMasterbagStatus.ADDED_TO_CONTAINER);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId1)).getMasterbagEntries().get(0).getStatus(),TMSMasterbagStatus.ADDED_TO_CONTAINER);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId2)).getMasterbagEntries().get(0).getStatus(),TMSMasterbagStatus.ADDED_TO_CONTAINER);
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C177")
    public void addMBtoContainerSupportedLaneReverse() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("T-JPR","RT-BLR",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("T-JPR", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        tmsServiceHelper.intracityTransferRev.accept(masterbagId);
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(29L)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-JPR", "TH-DEL", 29L, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus(),TMSMasterbagStatus.ADDED_TO_CONTAINER);
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C178, If you try to receive masterBag which is added to container but not shipped then it should throw ERROR")
    public void receiveAddedToContainerMasterBag() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-DEL","ELC",2);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-DEL", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-DEL", "TH-BLR")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-DEL", "TH-BLR", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBagForce.apply(masterbagId, "TH-BLR")).getStatus().getStatusType().toString(),EnumSCM.ERROR);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C179, Intransit scan when container in NEW state: ERROR, Then Ship and scan in the valid points. Tracking details should get updated for every point")
    public void containerIntransitScanAndTrackingValidation() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","T-AZ",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-BLR", "TH-GWH")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-BLR", "TH-GWH", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.containerInTransitScan.apply(containerId, "TH-HYD")).getStatus().getStatusType().toString(),EnumSCM.ERROR);
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS, "Unable to ship Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.containerInTransitScan.apply(containerId, "TH-HYD")).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.containerInTransitScan.apply(containerId, "TH-DEL")).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.containerInTransitScan.apply(containerId, "TH-KKT")).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.containerInTransitScan.apply(containerId, "TH-GWH")).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        //Tracking Validations
        List<String> inscannedHubs = new ArrayList<>();
        inscannedHubs.add("TH-HYD");
        inscannedHubs.add("TH-DEL");
        inscannedHubs.add("TH-KKT");
        inscannedHubs.add("TH-GWH");
        TmsTrackingDetailResponse containerTracking = (TmsTrackingDetailResponse)tmsServiceHelper.getContainerTrackingDetail.apply(containerId);
        Assert.assertTrue(Math.toIntExact(containerTracking.getTmsTrackingEntryList().stream().filter(entry -> entry.getActivityType().equals("ADD_MASTERBAG")).count())>0,"ADD_MASTERBAG count in tracking is less then 1");
        Assert.assertTrue(Math.toIntExact(containerTracking.getTmsTrackingEntryList().stream().filter(entry -> entry.getActivityType().equals("SHIP_CONTAINER")).count())>0,"SHIP_CONTAINER count in tracking is less then 1");
        Assert.assertTrue(Math.toIntExact(containerTracking.getTmsTrackingEntryList().stream().filter(entry -> (entry.getActivityType().equals("IN_TRANSIT_SCAN")&&entry.getLocation().equals(inscannedHubs.get(0)))).count())>0, "Inscanned tracking not updated at location: "+inscannedHubs.get(0));
        Assert.assertTrue(Math.toIntExact(containerTracking.getTmsTrackingEntryList().stream().filter(entry -> (entry.getActivityType().equals("IN_TRANSIT_SCAN")&&entry.getLocation().equals(inscannedHubs.get(1)))).count())>0, "Inscanned tracking not updated at location: "+inscannedHubs.get(1));
        Assert.assertTrue(Math.toIntExact(containerTracking.getTmsTrackingEntryList().stream().filter(entry -> (entry.getActivityType().equals("IN_TRANSIT_SCAN")&&entry.getLocation().equals(inscannedHubs.get(2)))).count())>0, "Inscanned tracking not updated at location: "+inscannedHubs.get(2));
        Assert.assertTrue(Math.toIntExact(containerTracking.getTmsTrackingEntryList().stream().filter(entry -> (entry.getActivityType().equals("RECEIVE_CONTAINER")&&entry.getLocation().equals(inscannedHubs.get(3)))).count())>0, "Inscanned tracking not updated at location: "+inscannedHubs.get(3));
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C180, Try Inscanning container which does not exist. Should throw error")
    public void containerIntransitScanNonExisted() throws Exception {
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.containerInTransitScan.apply(9008, "TH-HYD")).getStatus().getStatusType().toString(),EnumSCM.ERROR);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C181, Receieve masterBag twice at the TH and check the idempotancy of the RECEIVED_AT_TRANSPORT_HUB operation")
    public void receiveMasterBagIdempotantAtTH() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","CAR",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        tmsServiceHelper.intercityTransfer.accept(masterbagId);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus(), TMSMasterbagStatus.RECEIVED_AT_TRANSPORT_HUB);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-DEL",masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to receive masterBag in dest TH");
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus(), TMSMasterbagStatus.RECEIVED_AT_TRANSPORT_HUB);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C182, Receieve masterBag at wrong TH")
    public void receiveMasterBagMisrouteAtTH() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","CAR",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply("TH-BLR", "TH-DEL")).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-BLR", "TH-DEL", laneId, transporterId)).getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId, masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS, "Unable to ship Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.containerInTransitScan.apply(containerId, "TH-JPR")).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBagForce.apply("TH-JPR",masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to receive masterBag in dest TH");
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus(), TMSMasterbagStatus.RECEIVED_WRONG_ROUTE);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C183, Here on receiving masterBag at delivery hubs(Delivery center) the status should be in NEW only but " +
            "when it goes to TH and there we receive and then moves to RECEIVED_AT_TRANSPORT_HUB")
    public void receiveMasterBagAtDeliveryHubIdempotant() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("ELC","RT-BLR",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("ELC", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.NEW,
                "Status not in NEW while in DC receieve it should be in NEW only");
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("ELC", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.NEW,
                "Status not in NEW while in DC receieve it should be in NEW only");
        tmsServiceHelper.intracityTransferRev.accept(masterbagId);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus(), TMSMasterbagStatus.RECEIVED_AT_TRANSPORT_HUB);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-DEL",masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to receive masterBag in dest TH");
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus(), TMSMasterbagStatus.RECEIVED_AT_TRANSPORT_HUB);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS",  "Smoke","Regression"}, priority = 4, description = "ID: C184, reopenMaterBagAndShip", enabled = true)
    public void reopenMaterBagAndValidateTMS() throws Exception{
    	String releaseId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML","36",EnumSCM.NORMAL,"cod",false, true));
        String masterBagId = lmsHepler.getMasterBagId(""+releaseId);
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(Long.parseLong(masterBagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(lmsServiceHelper.reopenMasterBag( Long.parseLong(masterBagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(lmsHepler.getMasterBagStatus(Long.parseLong(masterBagId)), EnumSCM.NEW, "masterbag status is not updated in DB to `CLOSED`");
        Assert.assertEquals(lmsServiceHelper.removeShipmentFromMasterBag(""+releaseId, masterBagId, ShipmentType.DL, true),EnumSCM.SUCCESS);

        String releaseId1 = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML","36",EnumSCM.NORMAL,"cod",false, true));
        String masterBagId1 = lmsHepler.getMasterBagId(""+releaseId1);
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(Long.parseLong(masterBagId1)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(lmsServiceHelper.reopenMasterBag( Long.parseLong(masterBagId1)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(lmsHepler.getMasterBagStatus(Long.parseLong(masterBagId1)), EnumSCM.NEW, "masterbag status is not updated in DB to `CLOSED`");
        Assert.assertEquals(lmsServiceHelper.removeShipmentFromMasterBag(""+releaseId1, masterBagId1, ShipmentType.DL, true),EnumSCM.SUCCESS);

        Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(""+releaseId1, masterBagId, ShipmentType.DL, true),EnumSCM.SUCCESS);
        Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(""+releaseId, masterBagId1, ShipmentType.DL, true),EnumSCM.SUCCESS);

        Assert.assertEquals(lmsServiceHelper.closeMasterBag(Long.parseLong(masterBagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(Long.parseLong(masterBagId1)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);

        TMSMasterbagEntry masterbag = ((TMSMasterbagReponse) tmsServiceHelper.getTmsMasterBagById.apply(masterBagId)).getMasterbagEntries().get(0);
        TMSMasterbagEntry masterbag1 = ((TMSMasterbagReponse) tmsServiceHelper.getTmsMasterBagById.apply(masterBagId1)).getMasterbagEntries().get(0);

        Assert.assertEquals(masterbag
                .getMasterbagShipmentEntries()
                .stream()
                .filter(x->x.getOrderId().equals(releaseId))
                .map(x -> x.getIsDeleted())
                .findFirst().get().booleanValue(),true);

        Assert.assertEquals(masterbag
                .getMasterbagShipmentEntries()
                .stream()
                .filter(x -> x.getOrderId().equals(releaseId1))
                .map(x -> x.getStatus())
                .findFirst().get(),TMSMasterbagStatus.NEW);

        Assert.assertEquals(masterbag1
                .getMasterbagShipmentEntries()
                .stream()
                .filter(x -> x.getOrderId().equals(releaseId1))
                .map(x -> x.getIsDeleted())
                .findFirst().get().booleanValue(),true);

        Assert.assertEquals(masterbag1
                .getMasterbagShipmentEntries()
                .stream()
                .filter(x -> x.getOrderId().equals(releaseId))
                .map(x -> x.getStatus())
                .findFirst().get(),TMSMasterbagStatus.NEW);

    }

    /**
     * E2E TMS flows starts from here
     */

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C185, Here transportHub and DC are co-located so after shipping container we receive masterBag directly into DC instead of receiving at TH first")
    public void E2E_processTMS_CoLocated_InterCity() throws Exception {
        long masterBagId= Long.parseLong(lmsHepler.getMasterBagId(""+omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true))));
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to close the MasterBag");
        String sourceHub = "TH-BLR";
        String destHub = "TH-DEL";
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply(sourceHub, masterBagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to receive masterBag in TMS HUB");
        long laneId = ((LaneResponse)tmsServiceHelper.getSupportedLanes.apply(sourceHub, destHub)).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        ContainerResponse containerResponse = (ContainerResponse)tmsServiceHelper.createContainer.apply(sourceHub, destHub, laneId , transporterId);
        long containerId = containerResponse.getContainerEntries().get(0).getId();
        Assert.assertEquals(containerResponse.getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to create Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId,42).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C186, tms process RHD")
    public void E2E_processTMS_RHD() throws Exception {
        long dcId = 2281L;
        String releaseID = ""+omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.MUMBAI_DE_RHD, "DE", "36", EnumSCM.NORMAL, "cod", false, true));
        long masterBagId= Long.parseLong(lmsHepler.getMasterBagId(releaseID));
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to close the MasterBag");
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterBagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to receive masterBag in TMS HUB");
        tmsServiceHelper.intercityTransferWithSourceAndDest.accept(masterBagId,"TH-BLR", "TH-MU");
        tmsServiceHelper.intracityTransferFw.accept(masterBagId);
        Assert.assertEquals(lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).
                getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive shipment in DC");
        Assert.assertEquals(lmsServiceHelper.masterBagInScanUpdate(masterBagId, releaseID, "Bangalore", dcId, "DC", 36, ShipmentStatus.RECEIVED_AT_HANDOVER_CENTER, OrderShipmentAssociationStatus.RECEIVED_AT_HANDOVER_CENTER).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Map<String, Object> shipment2 = DBUtilities.exSelectQueryForSingleRecord("select * from shipment where id = " + masterBagId, "lms");
        Assert.assertEquals(shipment2.get("status").toString(), EnumSCM.RECEIVED_AT_HANDOVER_CENTER);
        Assert.assertEquals(shipment2.get("last_scanned_premises_type").toString(), "DC");
        Assert.assertEquals(shipment2.get("last_scanned_premises_id").toString(), "" + dcId);
        Assert.assertEquals(lmsServiceHelper.handoverToRegionalCourier(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to Handover masterbag to 3PL from regional handover DC");
        Map<String, Object> shipment3 = DBUtilities.exSelectQueryForSingleRecord("select * from shipment where id = " + masterBagId, "lms");
        Assert.assertEquals(shipment3.get("status").toString(), EnumSCM.HANDED_OVER_TO_3PL);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C187, tms RHD colocated transport hub")
    public void E2E_processTMS_RHD_CoLocated() throws Exception {
        long dcId = 2281L;
        String releaseID = ""+omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.MUMBAI_DE_RHD, "DE", "36", EnumSCM.NORMAL, "cod", false, true));
        long masterBagId= Long.parseLong(lmsHepler.getMasterBagId(releaseID));
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to close the MasterBag");
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterBagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to receive masterBag in TMS HUB");
        tmsServiceHelper.intercityTransferWithSourceAndDest.accept(masterBagId,"TH-BLR", "TH-MU");
        ExceptionHandler.handleEquals(lmsServiceHelper.masterBagInScan((long) masterBagId, EnumSCM.RECEIVED_AT_HANDOVER_CENTER, "Bangalore",
                (long) lmsServiceHelper.getDCIdForDCCode.apply("RHD"), "HUB").getStatus().getStatusType().toString(),
                EnumSCM.SUCCESS, "Unable to receive masterBag in DC");
        Assert.assertEquals(lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).
                getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive shipment in DC");
        Assert.assertEquals(lmsServiceHelper.masterBagInScanUpdate(masterBagId, releaseID, "Bangalore", dcId, "DC", 36, ShipmentStatus.RECEIVED_AT_HANDOVER_CENTER, OrderShipmentAssociationStatus.RECEIVED_AT_HANDOVER_CENTER).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Map<String, Object> shipment2 = DBUtilities.exSelectQueryForSingleRecord("select * from shipment where id = " + masterBagId, "lms");
        Assert.assertEquals(shipment2.get("status").toString(), EnumSCM.RECEIVED_AT_HANDOVER_CENTER);
        Assert.assertEquals(shipment2.get("last_scanned_premises_type").toString(), "DC");
        Assert.assertEquals(shipment2.get("last_scanned_premises_id").toString(), "" + dcId);
        Assert.assertEquals(lmsServiceHelper.handoverToRegionalCourier(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to Handover masterbag to 3PL from regional handover DC");
        Map<String, Object> shipment3 = DBUtilities.exSelectQueryForSingleRecord("select * from shipment where id = " + masterBagId, "lms");
        Assert.assertEquals(shipment3.get("status").toString(), EnumSCM.HANDED_OVER_TO_3PL);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C188, tms process order via supported lane")
    public void E2E_processTMS_SupportedLane() throws Exception {
        long masterBagId= Long.parseLong(lmsHepler.getMasterBagId(""+omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.JPR_ML, "ML", "36", EnumSCM.NORMAL, "cod", false, true))));
        long masterBagId1= Long.parseLong(lmsHepler.getMasterBagId(""+omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.BBN_ML, "ML", "36", EnumSCM.NORMAL, "cod", false, true))));
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to close the MasterBag");
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterBagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to receive masterBag in TMS HUB");
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId1).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to close the MasterBag");
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterBagId1)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to receive masterBag in TMS HUB");
        ContainerResponse containerResponse = (ContainerResponse)tmsServiceHelper.createContainer.apply("TH-BLR", "TH-DEL", 4L , 1L);
        long containerId = containerResponse.getContainerEntries().get(0).getId();
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterBagId1)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        tmsServiceHelper.containerInTransitScan.apply(containerId, "TH-HYD");
        tmsServiceHelper.tmsReceiveMasterBag.apply("TH-HYD", masterBagId);
        tmsServiceHelper.tmsReceiveMasterBag.apply("TH-HYD", masterBagId1);
        tmsServiceHelper.intracityTransferFw.accept(masterBagId1);

        tmsServiceHelper.intercityTransfer.accept(masterBagId);
        tmsServiceHelper.intracityTransferFw.accept(masterBagId);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C189, , tms process RTO intrecity")
    public void E2E_processTMS_RTO() throws Exception {
        String releaseId = ""+omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.UNRTO, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        LMSHelper lmsHelper = new LMSHelper();
        String origin = "DC", destination = "HUB";
        OrderResponse release = (OrderResponse)lmsServiceHelper.getOrderLMS.apply(releaseId);
        ShipmentResponse shipmentResponse = lmsServiceHelper.createMasterBag(release.getOrders().get(0).getDeliveryCenterId(), origin, "Bangalore", (long)lmsHelper.getRTHubIdForWH.apply(release.getOrders().get(0).getRtoWarehouseId()), destination, "Bangalore", EnumSCM.NORMAL);
        Assert.assertEquals(shipmentResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "MasterBag creation failed");
        long masterBagId = (Long) shipmentResponse.getEntries().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(releaseId, "" + masterBagId, ShipmentType.DL), EnumSCM.SUCCESS, "Unable to save Master Bag");
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close master bag");

        ContainerResponse containerResponse = (ContainerResponse)tmsServiceHelper.createContainer.apply("CAR", "TH-DEL", 28L , 2L);
        long containerId = containerResponse.getContainerEntries().get(0).getId();
        Assert.assertEquals(containerResponse.getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to create Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        tmsServiceHelper.containerInTransitScan.apply(containerId, "TH-DEL");
        tmsServiceHelper.tmsReceiveMasterBag.apply("TH-DEL",masterBagId);
        ContainerResponse containerResponse1 = (ContainerResponse)tmsServiceHelper.createContainer.apply("TH-DEL","TH-BLR",12L, 2L);
        long containerId1 = containerResponse1.getContainerEntries().get(0).getId();
        Assert.assertEquals(containerResponse1.getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to create Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId1,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId1)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        lmsServiceHelper.masterBagInScan(masterBagId,EnumSCM.RECEIVED,"Bangalore",20,"HUB");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C190, , tms process RTO intrecity supported Lane")
    public void E2E_processTMS_RTO_With_SupportedLane() throws Exception {
        String releaseId = ""+omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.UNRTO, LMS_PINCODE.JPR_ML, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        LMSHelper lmsHelper = new LMSHelper();
        String origin = "DC", destination = "HUB";
        OrderResponse release = (OrderResponse)lmsServiceHelper.getOrderLMS.apply(releaseId);
        ShipmentResponse shipmentResponse = lmsServiceHelper.createMasterBag(release.getOrders().get(0).getDeliveryCenterId(), origin, "Bangalore", (long)lmsHelper.getRTHubIdForWH.apply(release.getOrders().get(0).getRtoWarehouseId()), destination, "Bangalore", EnumSCM.NORMAL);
        Assert.assertEquals(shipmentResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "MasterBag creation failed");
        long masterBagId = (Long) shipmentResponse.getEntries().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(releaseId, "" + masterBagId, ShipmentType.DL), EnumSCM.SUCCESS, "Unable to save Master Bag");
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close master bag");

        ContainerResponse containerResponse = (ContainerResponse)tmsServiceHelper.createContainer.apply("T-JPR", "TH-JPR", 15L , 1L);
        long containerId = containerResponse.getContainerEntries().get(0).getId();
        Assert.assertEquals(containerResponse.getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to create Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        tmsServiceHelper.containerInTransitScan.apply(containerId, "TH-JPR");
        tmsServiceHelper.tmsReceiveMasterBag.apply("TH-JPR",masterBagId);

        ContainerResponse containerResponse1 = (ContainerResponse)tmsServiceHelper.createContainer.apply("TH-JPR","TH-DEL",29L, 1L);
        long containerId1 = containerResponse1.getContainerEntries().get(0).getId();
        Assert.assertEquals(containerResponse1.getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to create Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId1,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId1)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        tmsServiceHelper.tmsReceiveMasterBag.apply("TH-DEL",masterBagId);

        ContainerResponse containerResponse2 = (ContainerResponse)tmsServiceHelper.createContainer.apply("TH-DEL","TH-BLR",12L, 2L);
        long containerId2 = containerResponse2.getContainerEntries().get(0).getId();
        Assert.assertEquals(containerResponse2.getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to create Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId2,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId2)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        lmsServiceHelper.masterBagInScan(masterBagId,EnumSCM.RECEIVED,"Bangalore",20,"HUB");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C191, , tms process RTO with intercity supported Lane")
    public void E2E_processTMS_RTO_With_SupportedLaneAZ() throws Exception {
        String releaseId = ""+omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.UNRTO, LMS_PINCODE.GWH_ML, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        LMSHelper lmsHelper = new LMSHelper();
        String origin = "DC", destination = "HUB";
        OrderResponse release = (OrderResponse)lmsServiceHelper.getOrderLMS.apply(releaseId);
        ShipmentResponse shipmentResponse = lmsServiceHelper.createMasterBag(release.getOrders().get(0).getDeliveryCenterId(), origin, "Bangalore", (long)lmsHelper.getRTHubIdForWH.apply(release.getOrders().get(0).getRtoWarehouseId()), destination, "Bangalore", EnumSCM.NORMAL);
        Assert.assertEquals(shipmentResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "MasterBag creation failed");
        long masterBagId = shipmentResponse.getEntries().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(releaseId, "" + masterBagId, ShipmentType.DL), EnumSCM.SUCCESS, "Unable to save Master Bag");
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close master bag");

        ContainerResponse containerResponse = (ContainerResponse)tmsServiceHelper.createContainer.apply("T-AZ", "TH-GWH", 33L , 1L);
        long containerId = containerResponse.getContainerEntries().get(0).getId();
        Assert.assertEquals(containerResponse.getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to create Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        tmsServiceHelper.containerInTransitScan.apply(containerId, "TH-GWH");
        tmsServiceHelper.tmsReceiveMasterBag.apply("TH-GWH",masterBagId);

        ContainerResponse containerResponse1 = (ContainerResponse)tmsServiceHelper.createContainer.apply("TH-GWH","TH-KKT",34L, 1L);
        long containerId1 = containerResponse1.getContainerEntries().get(0).getId();
        Assert.assertEquals(containerResponse1.getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to create Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId1,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId1)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        tmsServiceHelper.containerInTransitScan.apply(containerId1, "TH-KKT");
        tmsServiceHelper.tmsReceiveMasterBag.apply("TH-KKT",masterBagId);

        ContainerResponse containerResponse2 = (ContainerResponse)tmsServiceHelper.createContainer.apply("TH-KKT","TH-BLR",11L, 1L);
        long containerId2 = containerResponse2.getContainerEntries().get(0).getId();
        Assert.assertEquals(containerResponse2.getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to create Container");
        tmsServiceHelper.addMasterBagToContainer.apply(containerId2,masterBagId);
        tmsServiceHelper.shipContainer.apply(containerId2);
        tmsServiceHelper.containerInTransitScan.apply(containerId2, "TH-BLR");
        tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR",masterBagId);
        lmsServiceHelper.masterBagInScan(masterBagId,EnumSCM.RECEIVED,"Bangalore",20,"HUB");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C192, , tms process Misroute")
    public void E2E_processTMS_Misroute() throws Exception {
        long masterBagId= Long.parseLong(lmsHepler.getMasterBagId(""+omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true))));
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to close the MasterBag");
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterBagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to receive masterBag in TMS HUB");
        ContainerResponse containerResponse = (ContainerResponse)tmsServiceHelper.createContainer.apply("TH-BLR", "TH-DEL", 5L , 1L);
        long containerId = containerResponse.getContainerEntries().get(0).getId();
        Assert.assertEquals(containerResponse.getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to create Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.addMasterBagToContainer.apply(containerId,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        tmsServiceHelper.containerInTransitScan.apply(containerId, "TH-JPR");
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBagForce.apply("TH-JPR",masterBagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        tmsServiceHelper.misrouteTransfer.accept(masterBagId);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C193, tms process intrecity")
    public void tmsIntracityAutoBLR() throws Exception{
        long masterBagId= Long.parseLong(lmsHepler.getMasterBagId(""+omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true))));
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to close the MasterBag");
        tmsServiceHelper.processInTMSFromClosedToLastMileInTransit.accept(masterBagId);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "P0", "Smoke", "Regression"}, priority = 6, description = "ID: C194, tms process Intercity")
    public void tmsIntercityAutoBLR() throws Exception{
        long masterBagId= Long.parseLong(lmsHepler.getMasterBagId(""+omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true))));
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to close the MasterBag");
        tmsServiceHelper.processInTMSFromClosedToLastMileInTransit.accept(masterBagId);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TMS", "Smoke", "Regression"}, priority = 6, description = "ID: C195, Intercity End2End with TryAndBuy Shipment")
    public void tmsIntercityAutoDEL_And_TnB() throws Exception{
        long masterBagId= Long.parseLong(lmsHepler.getMasterBagId(""+omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "28", EnumSCM.NORMAL, "cod", true, true))));
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to close the MasterBag");
        tmsServiceHelper.processInTMSFromClosedToLastMileInTransit.accept(masterBagId);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression"}, priority = 6, description = "ID: C196, process Intracity order")
    public void tmsIntracityAutoDEL() throws Exception{
        long masterBagId= Long.parseLong(lmsHepler.getMasterBagId(""+omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.NORTH_CGH, "ML", "28", EnumSCM.NORMAL, "cod", false, true))));
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to close the MasterBag");
        tmsServiceHelper.processInTMSFromClosedToLastMileInTransit.accept(masterBagId);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression"}, priority = 6, description = "ID: C197, process Intercity EXPRESS order")
    public void tmsIntercityAutoBLR_EXPRESS() throws Exception{
        long masterBagId= Long.parseLong(lmsHepler.getMasterBagId(""+omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.XPRESS, "cod", false, true))));
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to close the MasterBag");
        tmsServiceHelper.processInTMSFromClosedToLastMileInTransit.accept(masterBagId);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression"}, priority = 6, description = "ID: C198, , process Intercity SDD order ")
    public void tmsIntercityAutoBLR_SDD() throws Exception{
        long masterBagId= Long.parseLong(lmsHepler.getMasterBagId(""+omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.XPRESS, "cod", false, true))));
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to close the MasterBag");
        tmsServiceHelper.processInTMSFromClosedToLastMileInTransit.accept(masterBagId);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression"}, priority = 6, description = "ID: C199, Intracity RTO processing via auto processing method")
    public void tmsRTOIntracity() throws Exception{
    	String releaseId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.UNRTO, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.XPRESS, "cod", false, true));
        long maserBagId = (long)tmsServiceHelper.createNcloseMBforRTO.apply(releaseId);
        tmsServiceHelper.intracityTransferRev.accept(maserBagId);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression"}, priority = 6, description = "ID: C653, Intercity RTO processing via auto processing method")
    public void tmsRTOInterCityAuto() throws Exception{
    	String releaseId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.UNRTO, LMS_PINCODE.JPR_ML, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        long maserBagId = (long)tmsServiceHelper.createNcloseMBforRTO.apply(releaseId);
        tmsServiceHelper.processInTMSFromClosedToReturnHub.accept(maserBagId);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression"}, priority = 6, description = "ID: C200, Intracity Return processing")
    public void tmsReturnIntracity() throws Exception{
    	String releaseId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.XPRESS, "cod", false, true));
        long returnId = Long.parseLong(lmsServiceHelper.createReturnAndPickupSuccessFul.apply(releaseId, LMS_PINCODE.ML_BLR, 5).toString());
        OrderResponse release = (OrderResponse)lmsServiceHelper.getOrderLMS.apply(releaseId);
        ShipmentResponse shipmentResponse = lmsServiceHelper.createMasterBag(release.getOrders().get(0).getDeliveryCenterId(), "DC", "Bangalore", 20, "HUB", "Bangalore", EnumSCM.NORMAL);
        ExceptionHandler.handleEquals(shipmentResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "MasterBag creation failed");
        long masterBagId = shipmentResponse.getEntries().get(0).getId();
        ExceptionHandler.handleEquals(lmsServiceHelper.addAndSaveMasterBag(""+returnId, "" + masterBagId, ShipmentType.PU), EnumSCM.SUCCESS, "Unable to save Master Bag");
        ExceptionHandler.handleEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close master bag");
        tmsServiceHelper.intracityTransferRev.accept(masterBagId);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression"}, priority = 6, description = "ID: C657, Once order is IN_Transit with container them mark order self mark delivered and then receive master bag with other shipments")
    public void TMS_SELF_MARK_DL_IN_TRANSIT_AND_RECEIVE() throws Exception{
    	String releaseId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
    	String releaseId1 = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        long masterBagId = Long.parseLong(lmsHepler.getMasterBagId(""+releaseId));
        Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(""+releaseId1, ""+masterBagId, ShipmentType.DL).toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close masterBag");
        tmsServiceHelper.processInTMSFromClosedShipped.accept(masterBagId);
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId,EnumSCM.S,5));
        Assert.assertEquals(lmsServiceHelper.selfMarkDL(""+releaseId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to mark delivered via self mark delivered api");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+releaseId, EnumSCM.DELIVERED, 2), "Order not delivered in LMS");
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId, EnumSCM.D, 2), "Order not DL in OMS");
        Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId, 5).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive masterBag in DC");
        Assert.assertEquals(((ShipmentResponse)lmsServiceHelper.getMasterBag.apply(masterBagId)).getEntries().get(0).getStatus(), ShipmentStatus.IN_TRANSIT);
    }

 
    @SuppressWarnings({ "unchecked"})
	@Test(groups = {"Smoke", "Regression"}, priority = 6, description = "ID: C201, TMS process jabong shipment")
    public void tmsProcessJabongShipment() throws Exception{
        Shipment shipment = lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.NORTH_CGH, EnumSCM.wareHouseIdJabong, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, false, false, false, false, false);
        JabongCreateShipmentResponse createOrderResponse = lmsServiceHelper.createJabongShipment(shipment);
        Assert.assertEquals(lmsServiceHelper.orderInScanNew(createOrderResponse.getOrderID(), EnumSCM.wareHouseIdJabong, true), EnumSCM.SUCCESS);
        ShipmentResponse shipmentResponse = lmsServiceHelper.createMasterBag(18, "HUB", "Bangalore", 42, "DC", "Bangalore", EnumSCM.NORMAL);
        Assert.assertEquals(shipmentResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "MasterBag creation failed");
        long masterBagId = shipmentResponse.getEntries().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(createOrderResponse.getOrderID(), "" + masterBagId, ShipmentType.DL), EnumSCM.SUCCESS, "Unable to save Master Bag");
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close master bag");
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterBagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to receive masterBag in TMS HUB");
        tmsServiceHelper.processInTMSFromClosedToLastMileInTransit.accept(masterBagId);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression"}, priority = 6, description = "ID: C202, process multiple masterbag Intercity of different locations")
    public void E2E_tmsMultiMasterBagOfDifferentType() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","T-AZ",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");

        String masterbagId1 = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","T-BBN",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId1)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId1)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");

        String masterbagId2 = tmsServiceHelper.pushMockTMSMasterBag("ELC","RT-DEL",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId2)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId2)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");

        String masterbagId3 = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","T-JPR",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId3)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId3)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");

        String masterbagId4 = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR","CAR",1);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId4)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.getTmsMasterBagById.apply(masterbagId4)).getMasterbagEntries().get(0).getStatus().toString(),EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");

        long laneId = 4L;
        long transporterId = ((TransporterResponse)tmsServiceHelper.getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)tmsServiceHelper.createContainer.apply("TH-BLR", "TH-GWH", laneId, transporterId)).getContainerEntries().get(0).getId();
        List<Long> masterbags = new ArrayList<>();
        masterbags.add(Long.parseLong(masterbagId));
        masterbags.add(Long.parseLong(masterbagId1));
        masterbags.add(Long.parseLong(masterbagId2));
        masterbags.add(Long.parseLong(masterbagId3));
        masterbags.add(Long.parseLong(masterbagId4));
        for (long masterbag: masterbags) {
            Assert.assertEquals(((ContainerResponse) tmsServiceHelper.addMasterBagToContainer.apply(containerId, masterbag)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to add masterBag to Container");
        }
        Assert.assertEquals(((ContainerResponse) tmsServiceHelper.shipContainer.apply(containerId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to ship container");
        Assert.assertEquals(((ContainerResponse)tmsServiceHelper.containerInTransitScan.apply(containerId,"TH-HYD")).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-GWH", masterbagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-HYD", masterbagId1)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-DEL", masterbagId2)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-HYD", masterbagId3)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse)tmsServiceHelper.tmsReceiveMasterBag.apply("TH-DEL", masterbagId4)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression"}, priority = 6, description = "ID: C203, create multiple masterbag for same source and destination then process all the masterbags together both for intercity and intracity.")
    public void E2E_processMultiMasterBagSameSourceAndDestination() throws Exception {
        String masterbagId = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR", "CAR", 1);
        Assert.assertEquals(((TMSMasterbagReponse) tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse) tmsServiceHelper.getTmsMasterBagById.apply(masterbagId)).getMasterbagEntries().get(0).getStatus().toString(), EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");

        String masterbagId1 = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR", "CAR", 1);
        Assert.assertEquals(((TMSMasterbagReponse) tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId1)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse) tmsServiceHelper.getTmsMasterBagById.apply(masterbagId1)).getMasterbagEntries().get(0).getStatus().toString(), EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");

        String masterbagId2 = tmsServiceHelper.pushMockTMSMasterBag("DH-BLR", "CAR", 1);
        Assert.assertEquals(((TMSMasterbagReponse) tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterbagId2)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(((TMSMasterbagReponse) tmsServiceHelper.getTmsMasterBagById.apply(masterbagId2)).getMasterbagEntries().get(0).getStatus().toString(), EnumSCM.RECEIVED_AT_TRANSPORT_HUB, "Status not in RECEIVED_AT_TRANSPORT_HUB");

        List<Long> masterBags = new ArrayList<>();
        masterBags.add(Long.parseLong(masterbagId));
        masterBags.add(Long.parseLong(masterbagId1));
        masterBags.add(Long.parseLong(masterbagId2));
        tmsServiceHelper.intercityTransferWithSourceAndDestMultiMasterBag(masterBags, "TH-BLR", "TH-DEL");
        try {
            tmsServiceHelper.intracityTransferWithSourceAndDestMultiMasterBag(masterBags, "TH-DEL", "CAR");
        }catch (ManagerException e){
            log.info("This exception is expected as here we are pushing mock orders in the TMS and trying to receive in ML DC and it doesn't exist in LMS");
        }
    }
}