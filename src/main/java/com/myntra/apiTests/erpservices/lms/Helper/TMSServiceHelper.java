package com.myntra.apiTests.erpservices.lms.Helper;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.Headers;
import com.myntra.apiTests.common.Constants.LambdaInterfaces;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lms.client.response.*;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.tms.hub.TMSHubResponse;
import com.myntra.tms.config.hub_to_transporthub_config.LocationHubToTransportHubConfigResponse;
import com.myntra.tms.config.lane_hub_config.LaneHubConfigResponse;
import com.myntra.tms.config.supported_transport_hub_config.SupportedTransportHubConfigResponse;
import com.myntra.tms.config.transporter_lane_config.TransporterLaneConfigResponse;
import com.myntra.tms.container.ContainerEntry;
import com.myntra.tms.container.ContainerResponse;
import com.myntra.tms.domain.TMSMasterbagStatus;
import com.myntra.tms.hub.TMSHubEntry;
import com.myntra.tms.hub.TMSHubType;
import com.myntra.tms.lane.LaneEntry;
import com.myntra.tms.lane.LaneResponse;
import com.myntra.tms.lane.LaneType;
import com.myntra.tms.masterbag.TMSMasterbagEntry;
import com.myntra.tms.masterbag.TMSMasterbagReponse;
import com.myntra.tms.masterbag.TMSMasterbagShipmentEntry;
import com.myntra.tms.pendency.TmsPreAlertResponse;
import com.myntra.tms.track.TmsTrackingDetailResponse;
import com.myntra.tms.transporter.TMSTransporterEntry;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * Created by Shubham Gupta on 5/15/17.
 */
public class TMSServiceHelper {
	
	private static Initialize init = new Initialize("/Data/configuration");
    static Logger log = Logger.getLogger(TMSServiceHelper.class);
	private LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();

	private static String envName = init.Configurations.GetTestEnvironemnt().name();
	static String rabbitMqName = null;

    public TMSServiceHelper() {
    	
        if (envName.equalsIgnoreCase("fox7")) {
            rabbitMqName = "d7erprabbitmq.myntra.com";
        } else if (envName.equalsIgnoreCase("M7")) {
            rabbitMqName = "d7erprabbitmq.myntra.com";
        } else {
            rabbitMqName = "pp1erprabbitmq.myntra.com";
        }
    }

    /**
     * getTransporter
     * Object param
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getTransporter = param -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TMS_TRANSPORTER, new String[]{param.toString()}, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TransporterResponse());

    /**
     * getTmsHub
     * Object param
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getTmsHub = param -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TMS_HUB, new String[]{param.toString()}, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getBasicHeaderXML()).getResponseBody(), new TMSHubResponse());

    /**
     * getLane
     * Object param
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getLane = param -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.LANE, new String[]{param.toString()}, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new LaneResponse());

    /**
     * addTMSTransporter
     * Object name
     * Object mode
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.BiFunction addTMSTransporter = (name, mode) -> {
    	
        TMSTransporterEntry transporter = new TMSTransporterEntry();
        transporter.setName(name.toString());
        transporter.setMode(mode.toString());
        transporter.setContactNumber("1234567890");
        transporter.setActive(false);
        transporter.setType("DIRECT");
        String payload = APIUtilities.convertXMLObjectToString(transporter);
        return APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TMS_TRANSPORTER, null, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.POST, payload, Headers.getLmsHeaderXML()).getResponseBody(), new TransporterResponse());
    };

    /**
     * addLane
     * Object name
     * Object source
     * Object destination
     * Object type
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.FourFunction addLane = (name, source, destination, type) -> {
        LaneEntry lane = new LaneEntry();
        lane.setName(name.toString());
        lane.setActive(false);
        lane.setType((LaneType)type);
        lane.setSourceHubCode(source.toString());
        lane.setDestinationHubCode(destination.toString());
        String payload = APIUtilities.convertXMLObjectToString(lane);
        return APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.LANE, null, SERVICE_TYPE.TMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML()).getResponseBody(), new LaneResponse());
    };

    /**
     * addTMSHub
     * Object code
     * Object name
     * Object pincode
     * Object type
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.FourFunction addTMSHub = (code, name, pincode, type) -> {
        TMSHubEntry hub = new TMSHubEntry();
        hub.setCode(code.toString());
        hub.setName(name.toString());
        hub.setActive(false);
        hub.setType((TMSHubType)type);
        hub.setPincode(pincode.toString());
        hub.setContactNumber("1234567890");
        hub.setAddress("Automation test address");
        hub.setCity("Test city");
        hub.setState("KA");
        hub.setManager("Test manager");
        String payload = APIUtilities.convertXMLObjectToString(hub);
        return APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TMS_HUB, null, SERVICE_TYPE.TMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML()).getResponseBody(), new TMSHubResponse());
    };

    /**
     * getTmsMasterBag
     * Object param
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getTmsMasterBag = param -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TMS_MASTERBAG, new String[]{param.toString()}, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TMSMasterbagReponse());

    /**
     * getTmsMasterBagById
     * id
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getTmsMasterBagById = id -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TMS_MASTERBAG, new String[]{"search?q=masterbagId.eq:" + id}, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TMSMasterbagReponse());

    /**
     * getTransportHubPendency
     * sourceCode, destinationCode
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.BiFunction getTransportHubPendency = (sourceCode, destinationCode) -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_TRANSPORTER_HUB_PENDENCY,
            new String[]{sourceCode.toString(), destinationCode.toString(), LocalDateTime.now().minusDays(3).toLocalDate().toString(), LocalDateTime.now().plusDays(1).toLocalDate().toString()}, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TransportHubPendencyResponse());

    /**
     * getMisrouteBagsHubPendency
     * sourceCode, destinationCode
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.BiFunction getMisrouteBagsHubPendency = (sourceCode, destinationCode) -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_MISROUTE_PENDENCY,
            new String[]{sourceCode.toString(), destinationCode.toString(), LocalDateTime.now().minusDays(3).toLocalDate().toString(), LocalDateTime.now().plusDays(1).toLocalDate().toString()}, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TransportHubPendencyResponse());

    /**
     * getMasterbagPreAlert
     * sourceCode, destinationCode, transporter)
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.TriFunction getMasterbagPreAlert = (sourceCode, destinationCode, transporter) -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTERBAG_PREALERT,
            new String[]{sourceCode.toString(),sourceCode.toString(), destinationCode.toString(),transporter.toString()}, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TmsPreAlertResponse());

    /**
     * getContainer
     * param
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getContainer = param -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.CONTAINER, new String[]{param.toString()}, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new ContainerResponse());

    /**
     * getContainerTrackingDetail
     * param
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getContainerTrackingDetail = param -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.CONTAINER_TRACKING_DETAIL, new String[]{param.toString()}, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TmsTrackingDetailResponse());

    /**
     * getLocationHubConfig
     * locationHubCode
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getLocationHubConfig = locationHubCode -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_LOCATION_HUB_CONFIG, new String[]{locationHubCode.toString()}, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new LocationHubToTransportHubConfigResponse());

    /**
     * getLocationHubConfig
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Supplier downloadTransporterLaneConfig = () -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRANSPORTER_LANE_CONFIG_DOWNLOAD, null, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TransporterLaneConfigResponse());

    /**
     * downloadLocationHubToTransportHubConfig
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Supplier downloadLocationHubToTransportHubConfig = () -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.LH_TH_CONFIG_DOWNLOAD, null, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new LocationHubToTransportHubConfigResponse());

    /**
     * downloadLaneHubConfig
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Supplier downloadLaneHubConfig = () -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.LANE_HUB_CONFIG_DOWNLOAD, null, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new LaneHubConfigResponse());

    /**
     * downloadSupportedTransportHubConfig
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Supplier downloadSupportedTransportHubConfig = () -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.SUPPORTED_TRANSPORTHUB_CONFIG_DOWNLOAD, null, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new SupportedTransportHubConfigResponse());

    /**
     * getContainerManifest
     * containerId
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getContainerManifest = containerId -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TMS_CONTAINER_MANIFEST, new String[]{"container?containerIds[]="+containerId}, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new ManifestResponse());

    /**
     * getMasterBagManifest
     * Object masterBagId
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getMasterBagManifest = masterbagId -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TMS_MASTERBAG_MANIFEST, new String[]{masterbagId.toString()}, SERVICE_TYPE.TMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new ManifestResponse());

    /**
     * tmsReceiveMasterBag
     * hubCode, masterBagId
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.BiFunction tmsReceiveMasterBag = (hubCode, masterBagId) -> {
        TMSMasterbagEntry tmsMasterbagEntry = new TMSMasterbagEntry();
        tmsMasterbagEntry.setMasterbagId(masterBagId.toString());
        tmsMasterbagEntry.setWeight(15.00);
        tmsMasterbagEntry.setSealId(Long.parseLong(LMSUtils.randomGenn(8)));
        String payload = APIUtilities.convertXMLObjectToString(tmsMasterbagEntry);
        return APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TMS_RECEIVE_MASTERBAG, new String[]{hubCode.toString(), "false"}, SERVICE_TYPE.TMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML()).getResponseBody(), new TMSMasterbagReponse());
    };

    /**
     * tmsReceiveMasterBagForce
     * transportHubId, masterBagId
     */
    @SuppressWarnings("rawtypes")
	public LambdaInterfaces.BiFunction tmsReceiveMasterBagForce = (transportHubId, masterBagId) -> {
        TMSMasterbagEntry tmsMasterbagEntry = new TMSMasterbagEntry();
        tmsMasterbagEntry.setMasterbagId(masterBagId.toString());
        String payload = APIUtilities.convertXMLObjectToString(tmsMasterbagEntry);
        return APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TMS_RECEIVE_MASTERBAG, new String[]{transportHubId.toString(), "true"}, SERVICE_TYPE.TMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML()).getResponseBody(), new TMSMasterbagReponse());
    };

    /**
     * createContainer
     * source, dest, laneId, transporterId
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.FourFunction createContainer = (source, dest, laneId, transporterId) -> {
        ContainerEntry container = new ContainerEntry();
        container.setOriginHubCode(source.toString());
        container.setDestinationHubCode(dest.toString());
        container.setLaneId((long) laneId);
        container.setTransporterId((long) transporterId);
        container.setDriverMobileNumber(LMSUtils.randomGenn(10));
        container.setDriverName("Automation driver");
        container.setDocketNumber(LMSUtils.randomGenn(8));
        container.setVehicleNumber("KA-01 TE-" + LMSUtils.randomGenn(4));
        String payload = APIUtilities.convertXMLObjectToString(container);
        return APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TMS_CONTAINER, null, SERVICE_TYPE.TMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML()).getResponseBody(), new ContainerResponse());
    };

    /**
     * getMisrouteTransporter
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public LambdaInterfaces.Supplier getMisrouteTransporter = ()-> ((TransporterResponse) getTransporter.apply("search?q=name.like:MISROUT&start=0&fetchSize=-1&sortBy=id&sortOrder=ASC")).getTransporterEntries().get(0).getId();

    /**
     * getMisrouteLane
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public LambdaInterfaces.Supplier getMisrouteLane = ()-> ((LaneResponse) getLane.apply("search?q=name.like:MISROUTE___type.like:MISROUTE&start=0&fetchSize=20&sortBy=name&sortOrder=ASC")).getLaneEntries().get(0).getId();

    /**
     * addMasterBagToContainer
     * containerId, masterbagId
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.BiFunction addMasterBagToContainer = (containerId, masterbagId)-> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TMS_CONTAINER, new String[]{containerId.toString(),"addMasterbag",masterbagId.toString()},
            SERVICE_TYPE.TMS_SVC.toString(), HTTPMethods.POST, null, Headers.getLmsHeaderXML()).getResponseBody(), new ContainerResponse());

    /**
     * shipContainer
     * containerId
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function shipContainer = containerId -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.SHIP_CONTAINER, new String[]{containerId.toString()},
            SERVICE_TYPE.TMS_SVC.toString(), HTTPMethods.POST, null, Headers.getLmsHeaderXML()).getResponseBody(), new ContainerResponse());

    /**
     * containerInTransitScan
     * containerId, hubId
     */
    @SuppressWarnings("rawtypes")
	public LambdaInterfaces.BiFunction containerInTransitScan = (containerId, hubId)-> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TMS_CONTAINER, new String[]{containerId.toString(),"inTransitScan",hubId.toString()},
            SERVICE_TYPE.TMS_SVC.toString(), HTTPMethods.POST, null, Headers.getLmsHeaderXML()).getResponseBody(), new ContainerResponse());

    /**
     * containerInTransitScan
     * sourceHubCode, destinationHubCode
     */
	@SuppressWarnings("rawtypes")
    public LambdaInterfaces.BiFunction getSupportedLanes = (sourceHubCode, destinationHubCode)-> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_SUPPORTED_LANES, new String[]{sourceHubCode.toString(),destinationHubCode.toString()},
            SERVICE_TYPE.TMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new LaneResponse());

    /**
     * getSupportedTransportersForLane
     * laneId
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getSupportedTransportersForLane = laneId-> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_SUPORTED_TRANSPORTER_FOR_LANE_, new String[]{laneId.toString()},
            SERVICE_TYPE.TMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TransporterResponse());

    /**
     * getTHForLH
     * lhCode
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public LambdaInterfaces.Function getTHForLH = lhCode -> ((LocationHubToTransportHubConfigResponse)getLocationHubConfig.apply(lhCode)).getLocationHubToTransportHubConfigEntries().get(0).getTransportHubCode();

    /**
     * intracityTransferFw
     * Object masterBagId
     */
   
    @SuppressWarnings("unchecked")
	public LambdaInterfaces.Consumer<Object> intracityTransferFw = masterBagId -> {
        TMSMasterbagReponse masterBag = (TMSMasterbagReponse)getTmsMasterBagById.apply(masterBagId);
        String sourceHub = masterBag.getMasterbagEntries().get(0).getLastScannedHub();
        String destHub = masterBag.getMasterbagEntries().get(0).getDestinationHub();
        long laneId = ((LaneResponse)getSupportedLanes.apply(sourceHub, destHub)).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)createContainer.apply(sourceHub, destHub, laneId, transporterId)).getContainerEntries().get(0).getId();
        ExceptionHandler.handleEquals(((ContainerResponse)addMasterBagToContainer.apply(containerId,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        ExceptionHandler.handleEquals(((ContainerResponse)shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        String courierCode = ((ShipmentResponse)lmsServiceHelper.getMasterBag.apply(masterBagId)).getEntries().get(0).getCourier();
        if(courierCode.equals("ML")) {
            ExceptionHandler.handleEquals(lmsServiceHelper.masterBagInScan((long) masterBagId, EnumSCM.RECEIVED, "Bangalore",
                    (long) lmsServiceHelper.getDCIdForDCCode.apply(masterBag.getMasterbagEntries().get(0).getDestinationHub()), "HUB").getStatus().getStatusType().toString(),
                    EnumSCM.SUCCESS, "Unable to receive masterBag in DC");
        }else {
            ExceptionHandler.handleEquals(lmsServiceHelper.masterBagInScan((long) masterBagId, EnumSCM.RECEIVED_AT_HANDOVER_CENTER, "Bangalore",
                    (long) lmsServiceHelper.getDCIdForDCCode.apply(masterBag.getMasterbagEntries().get(0).getDestinationHub()), "HUB").getStatus().getStatusType().toString(),
                    EnumSCM.SUCCESS, "Unable to receive masterBag in DC");
        }
    };

    /**
     * intracityTransferFwTillShipped
     * Object masterBagId
     */
    @SuppressWarnings({ "unchecked", "unused" })
	public LambdaInterfaces.Consumer<Object> intracityTransferFwTillShipped = masterBagId -> {
        TMSMasterbagReponse masterBag = (TMSMasterbagReponse)getTmsMasterBagById.apply(masterBagId);
        String sourceHub = masterBag.getMasterbagEntries().get(0).getLastScannedHub();
        String destHub = masterBag.getMasterbagEntries().get(0).getDestinationHub();
        long laneId = ((LaneResponse)getSupportedLanes.apply(sourceHub, destHub)).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)createContainer.apply(sourceHub, destHub, laneId, transporterId)).getContainerEntries().get(0).getId();
        ExceptionHandler.handleEquals(((ContainerResponse)addMasterBagToContainer.apply(containerId,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        ExceptionHandler.handleEquals(((ContainerResponse)shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        String courierCode = ((ShipmentResponse)lmsServiceHelper.getMasterBag.apply(masterBagId)).getEntries().get(0).getCourier();
    };

    /**
     * intracityTransferRev
     * Object masterBagId
     */
    @SuppressWarnings("unchecked")
    public LambdaInterfaces.Consumer<Object> intracityTransferRev = masterBagId -> {
        TMSMasterbagReponse masterBag = (TMSMasterbagReponse)getTmsMasterBagById.apply(masterBagId);
        String sourceHub = masterBag.getMasterbagEntries().get(0).getSourceHub();
        String destHub = getTHForLH.apply(sourceHub).toString();
        long laneId = ((LaneResponse)getSupportedLanes.apply(sourceHub, destHub)).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)createContainer.apply(sourceHub, destHub, laneId, transporterId)).getContainerEntries().get(0).getId();
        ExceptionHandler.handleEquals(((ContainerResponse)addMasterBagToContainer.apply(containerId,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        ExceptionHandler.handleEquals(((ContainerResponse)shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        ExceptionHandler.handleEquals(((ContainerResponse)containerInTransitScan.apply(containerId, destHub)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to do intransit scan of the container");
        ExceptionHandler.handleEquals(((TMSMasterbagReponse)tmsReceiveMasterBag.apply(destHub,masterBagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to receive masterBag in dest TH");
    };

    /**
     * intracityTransferToReturnHub
     * For Now we will directly receive the masterbag in Returns hub once its available in the Corresponding Transport Hub. We have not defined config for TH->RT hubs as it is the same in prod.
     */
    @SuppressWarnings("unchecked")
    public LambdaInterfaces.Consumer<Object> intracityTransferToReturnHub = masterBagId -> {
        TMSMasterbagReponse masterBag = (TMSMasterbagReponse)getTmsMasterBagById.apply(masterBagId);
//        String sourceHub = masterBag.getMasterbagEntries().get(0).getLastScannedHub();
        String destHub = masterBag.getMasterbagEntries().get(0).getDestinationHub();
        /*long laneId = ((LaneResponse)getSupportedLanes.apply(sourceHub, destHub)).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)createContainer.apply(sourceHub, destHub, laneId, transporterId)).getContainerEntries().get(0).getId();
        ExceptionHandler.handleEquals(((ContainerResponse)addMasterBagToContainer.apply(containerId,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        ExceptionHandler.handleEquals(((ContainerResponse)shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        ExceptionHandler.handleEquals(((ContainerResponse)containerInTransitScan.apply(containerId, destHub)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to do intransit scan of the container");
        ExceptionHandler.handleEquals(((TMSMasterbagReponse)tmsReceiveMasterBag.apply(destHub,masterBagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to receive masterBag in dest TH");
*/        ExceptionHandler.handleEquals(lmsServiceHelper.masterBagInScan((long)masterBagId, EnumSCM.RECEIVED, "Bangalore",
                lmsServiceHelper.getHubByCode(destHub.toString()).getHub().get(0).getId(), "HUB").getStatus().getStatusType().toString(),
                EnumSCM.SUCCESS, "Unable to receive masterBag in DC");
    };

    /**
     * intercityTransfer
     * Object masterBagId
     */
    @SuppressWarnings("unchecked")
    public LambdaInterfaces.Consumer<Object> intercityTransfer = masterBagId -> {
        TMSMasterbagReponse masterBag = (TMSMasterbagReponse)getTmsMasterBagById.apply(masterBagId);
        String sourceHub = masterBag.getMasterbagEntries().get(0).getLastScannedHub();
        String destHub = (String) getTHForLH.apply(masterBag.getMasterbagEntries().get(0).getDestinationHub());
        long laneId = ((LaneResponse)getSupportedLanes.apply(sourceHub, destHub)).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)createContainer.apply(sourceHub, destHub, laneId, transporterId)).getContainerEntries().get(0).getId();
        ExceptionHandler.handleEquals(((ContainerResponse)addMasterBagToContainer.apply(containerId,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        ExceptionHandler.handleEquals(((ContainerResponse)shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        ExceptionHandler.handleEquals(((ContainerResponse)containerInTransitScan.apply(containerId, destHub)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to do intransit scan of the container");
        ExceptionHandler.handleEquals(((TMSMasterbagReponse)tmsReceiveMasterBag.apply(destHub,masterBagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to receive masterBag in dest TH");
    };

    /**
     * intercityTransferTillShipped
     * Object masterBagId
     */
    @SuppressWarnings("unchecked")
    public LambdaInterfaces.Consumer<Object> intercityTransferTillShipped = masterBagId -> {
        TMSMasterbagReponse masterBag = (TMSMasterbagReponse)getTmsMasterBagById.apply(masterBagId);
        String sourceHub = masterBag.getMasterbagEntries().get(0).getLastScannedHub();
        String destHub = (String) getTHForLH.apply(masterBag.getMasterbagEntries().get(0).getDestinationHub());
        long laneId = ((LaneResponse)getSupportedLanes.apply(sourceHub, destHub)).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)createContainer.apply(sourceHub, destHub, laneId, transporterId)).getContainerEntries().get(0).getId();
        ExceptionHandler.handleEquals(((ContainerResponse)addMasterBagToContainer.apply(containerId,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        ExceptionHandler.handleEquals(((ContainerResponse)shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
    };

    /**
     * intercityTransferWithSourceAndDest
     * masterBagId, sourceHub, destHub
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public LambdaInterfaces.TriConsumer intercityTransferWithSourceAndDest = (masterBagId, sourceHub, destHub) -> {
        long laneId = ((LaneResponse)getSupportedLanes.apply(sourceHub, destHub)).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)createContainer.apply(sourceHub, destHub, laneId, transporterId)).getContainerEntries().get(0).getId();
        ExceptionHandler.handleEquals(((ContainerResponse)addMasterBagToContainer.apply(containerId,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        ExceptionHandler.handleEquals(((ContainerResponse)shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        ExceptionHandler.handleEquals(((ContainerResponse)containerInTransitScan.apply(containerId, destHub)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to do intransit scan of the container");
        ExceptionHandler.handleEquals(((TMSMasterbagReponse)tmsReceiveMasterBagForce.apply(destHub,masterBagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to receive masterBag in dest TH");
    };

    /**
     * intercityTransferWithSourceAndDestMultiMasterBag
     * @param masterBagIds
     * @param sourceHub
     * @param destHub
     * @throws InterruptedException
     * @throws JAXBException
     * @throws IOException
     * @throws ManagerException
     * @throws JSONException
     * @throws XMLStreamException
     */
    @SuppressWarnings("unchecked")
    public void intercityTransferWithSourceAndDestMultiMasterBag (List<Long> masterBagIds, String sourceHub, String destHub) throws InterruptedException, JAXBException, IOException, ManagerException, JSONException, XMLStreamException {
        long laneId = ((LaneResponse)getSupportedLanes.apply(sourceHub, destHub)).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)createContainer.apply(sourceHub, destHub, laneId, transporterId)).getContainerEntries().get(0).getId();

        for (long masterBagId: masterBagIds) {
            ExceptionHandler.handleEquals(((ContainerResponse) addMasterBagToContainer.apply(containerId, masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to add masterBag to Container, masterBagId: "+masterBagId);
        }
        ExceptionHandler.handleEquals(((ContainerResponse)shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        ExceptionHandler.handleEquals(((ContainerResponse)containerInTransitScan.apply(containerId, destHub)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to do intransit scan of the container");
        for (long masterBagId: masterBagIds) {
                ExceptionHandler.handleEquals(((TMSMasterbagReponse)tmsReceiveMasterBag.apply(destHub, masterBagId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"Unable to receive masterBag in dest TH");
        }
    }

    /**
     * intracityTransferWithSourceAndDestMultiMasterBag
     * @param masterBagIds
     * @param sourceHub
     * @param destHub
     * @throws InterruptedException
     * @throws JAXBException
     * @throws IOException
     * @throws ManagerException
     * @throws JSONException
     * @throws XMLStreamException
     */
    @SuppressWarnings("unchecked")
    public void intracityTransferWithSourceAndDestMultiMasterBag (List<Long> masterBagIds, String sourceHub, String destHub) throws InterruptedException, JAXBException, IOException, ManagerException, JSONException, XMLStreamException {
        long laneId = ((LaneResponse)getSupportedLanes.apply(sourceHub, destHub)).getLaneEntries().get(0).getId();
        long transporterId = ((TransporterResponse)getSupportedTransportersForLane.apply(laneId)).getTransporterEntries().get(0).getId();
        long containerId = ((ContainerResponse)createContainer.apply(sourceHub, destHub, laneId, transporterId)).getContainerEntries().get(0).getId();
        for (long masterBagId: masterBagIds) {
                ExceptionHandler.handleEquals(((ContainerResponse) addMasterBagToContainer.apply(containerId, masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to add masterBag to Container, masterBagId: "+masterBagId);
            }
        ExceptionHandler.handleEquals(((ContainerResponse)shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        for (long masterBagId: masterBagIds) {
                ExceptionHandler.handleEquals(lmsServiceHelper.masterBagInScan(masterBagId, EnumSCM.RECEIVED, "Bangalore",
                        (long) lmsServiceHelper.getDCIdForDCCode.apply(destHub), "HUB").getStatus().getStatusType().toString(),
                        EnumSCM.SUCCESS, "Unable to receive masterBag in DC");
        }
    }

    /**
     * misrouteTransfer
     * Object masterBagId
     */
    @SuppressWarnings("unchecked")
    public LambdaInterfaces.Consumer<Object> misrouteTransfer = masterBagId -> {
        TMSMasterbagReponse masterBag = (TMSMasterbagReponse)getTmsMasterBagById.apply(masterBagId);
        String sourceHub = masterBag.getMasterbagEntries().get(0).getLastScannedHub();
        String destHub = masterBag.getMasterbagEntries().get(0).getDestinationHub();
        long laneId = (long)getMisrouteLane.get();
        long transporterId = (long)getMisrouteTransporter.get();
        long containerId = ((ContainerResponse)createContainer.apply(sourceHub, destHub, laneId, transporterId)).getContainerEntries().get(0).getId();
        ExceptionHandler.handleEquals(((ContainerResponse)addMasterBagToContainer.apply(containerId,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        ExceptionHandler.handleEquals(((ContainerResponse)shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        String courierCode = ((ShipmentResponse)lmsServiceHelper.getMasterBag.apply(masterBagId)).getEntries().get(0).getCourier();
        if(courierCode.equals("ML")) {
            ExceptionHandler.handleEquals(lmsServiceHelper.masterBagInScan((long) masterBagId, EnumSCM.RECEIVED, "Bangalore",
                    (long) lmsServiceHelper.getDCIdForDCCode.apply(masterBag.getMasterbagEntries().get(0).getDestinationHub()), "HUB").getStatus().getStatusType().toString(),
                    EnumSCM.SUCCESS, "Unable to receive masterBag in DC");
        }else {
            ExceptionHandler.handleEquals(lmsServiceHelper.masterBagInScan((long) masterBagId, EnumSCM.RECEIVED_AT_HANDOVER_CENTER, "Bangalore",
                    (long) lmsServiceHelper.getDCIdForDCCode.apply(masterBag.getMasterbagEntries().get(0).getDestinationHub()), "HUB").getStatus().getStatusType().toString(),
                    EnumSCM.SUCCESS, "Unable to receive masterBag in DC");
        }
    };

    /**
     * misrouteTransferTillShipped
     * Object masterBagId
     */
    @SuppressWarnings({ "unchecked", "unused" })
    public LambdaInterfaces.Consumer<Object> misrouteTransferTillShipped = masterBagId -> {
        TMSMasterbagReponse masterBag = (TMSMasterbagReponse)getTmsMasterBagById.apply(masterBagId);
        String sourceHub = masterBag.getMasterbagEntries().get(0).getLastScannedHub();
        String destHub = masterBag.getMasterbagEntries().get(0).getDestinationHub();
        long laneId = (long)getMisrouteLane.get();
        long transporterId = (long)getMisrouteTransporter.get();
        long containerId = ((ContainerResponse)createContainer.apply(sourceHub, destHub, laneId, transporterId)).getContainerEntries().get(0).getId();
        ExceptionHandler.handleEquals(((ContainerResponse)addMasterBagToContainer.apply(containerId,masterBagId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to add masterBag to Container");
        ExceptionHandler.handleEquals(((ContainerResponse)shipContainer.apply(containerId)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        String courierCode = ((ShipmentResponse)lmsServiceHelper.getMasterBag.apply(masterBagId)).getEntries().get(0).getCourier();
    };

    /**
     * processInTMSFromClosedToLastMileInTransit
     * Object masterBagId
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public com.myntra.apiTests.common.Constants.LambdaInterfaces.Consumer processInTMSFromClosedToLastMileInTransit = masterBagId -> {
        TMSMasterbagEntry masterBag = ((TMSMasterbagReponse)getTmsMasterBagById.apply(masterBagId)).getMasterbagEntries().get(0);
        String sourceTH = (String) getTHForLH.apply(masterBag.getSourceHub());
        String destTH = (String) getTHForLH.apply(masterBag.getDestinationHub());
        ExceptionHandler.handleEquals(((TMSMasterbagReponse)tmsReceiveMasterBag.apply(sourceTH,masterBagId)).getStatus().getStatusType().toString(),
                EnumSCM.SUCCESS,"Unable to receive MasterBag in source TH");
//        try {
            if (sourceTH.equals(destTH))
                intracityTransferFw.accept(masterBagId);
            else {
                intercityTransfer.accept(masterBagId);
                intracityTransferFw.accept(masterBagId);
            }
//        }catch (ManagerException e){
//            misrouteTransfer.accept(masterBagId);
//        }
    };

    /**
     * processInTMSFromClosedToReturnHub
     * Object masterBagId
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public LambdaInterfaces.Consumer processInTMSFromClosedToReturnHub = masterBagId -> {
        TMSMasterbagEntry masterBag = ((TMSMasterbagReponse)getTmsMasterBagById.apply(masterBagId)).getMasterbagEntries().get(0);
        String sourceTH = (String) getTHForLH.apply(masterBag.getSourceHub());
        String destTH = (String) getTHForLH.apply(masterBag.getDestinationHub());
        ExceptionHandler.handleError(((TMSMasterbagReponse)tmsReceiveMasterBag.apply(masterBag.getSourceHub(), masterBagId)).getStatus(),"Unable to receive MasterBag in source TH");
        try {
            if (sourceTH.equals(destTH)) {
                intracityTransferRev.accept(masterBagId);
                intracityTransferToReturnHub.accept(masterBagId);
            }
            else {
                intracityTransferRev.accept(masterBagId);
                intercityTransfer.accept(masterBagId);
                intracityTransferToReturnHub.accept(masterBagId);
            }
        }catch (ManagerException e){
            misrouteTransfer.accept(masterBagId);
        }
    };

    /**
     * processInTMSFromClosedShipped
     * Object masterBagId
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public LambdaInterfaces.Consumer processInTMSFromClosedShipped = masterBagId -> {
        TMSMasterbagEntry masterBag = ((TMSMasterbagReponse)getTmsMasterBagById.apply(masterBagId)).getMasterbagEntries().get(0);
        String sourceTH = (String) getTHForLH.apply(masterBag.getSourceHub());
        String destTH = (String) getTHForLH.apply(masterBag.getSourceHub());
        ExceptionHandler.handleError(((TMSMasterbagReponse)tmsReceiveMasterBag.apply(sourceTH,masterBagId)).getStatus(),"Unable to receive MasterBag in source TH");
        try {
            if (sourceTH.equals(destTH))
                intracityTransferFwTillShipped.accept(masterBagId);
            else {
                intercityTransferTillShipped.accept(masterBagId);
            }
        }catch (ManagerException e){
            misrouteTransferTillShipped.accept(masterBagId);
        }
    };

    /**
     * createNcloseMBforRTO
     * Object masterBagId
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
      public LambdaInterfaces.Function createNcloseMBforRTO = releaseId -> {
        OrderResponse release = (OrderResponse)lmsServiceHelper.getOrderLMS.apply(releaseId);
        ShipmentResponse shipmentResponse = lmsServiceHelper.createMasterBag(release.getOrders().get(0).getDeliveryCenterId(), "DC", "Bangalore", lmsServiceHelper.getHubByCode(release.getOrders().get(0).getRtoHubCode()).getHub().get(0).getId(), "HUB", "Bangalore", EnumSCM.NORMAL);
        ExceptionHandler.handleEquals(shipmentResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "MasterBag creation failed");
        long masterBagId = shipmentResponse.getEntries().get(0).getId();
        ExceptionHandler.handleEquals(lmsServiceHelper.addAndSaveMasterBag(""+releaseId, "" + masterBagId, ShipmentType.DL), EnumSCM.SUCCESS, "Unable to save Master Bag");
        ExceptionHandler.handleEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close master bag");
        return masterBagId;
    };
    public LambdaInterfaces.Function createNcloseMBforReturn = returnId -> {
       // OrderResponse release = (OrderResponse)lmsServiceHelper.getOrderLMS.apply(returnId);
        Map<String, Object> return_shipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnId, "lms");
        LMSHelper lmsHelper= new LMSHelper();
        String  destWarehouseId = return_shipment.get("return_warehouse_id").toString();
        String  deliveryCenterID = return_shipment.get("delivery_center_id").toString();
        String expectedReturnHub = lmsHelper.getReturnHubCodeForWarehouse.apply(Long.parseLong(destWarehouseId)).toString();
    
        ShipmentResponse shipmentResponse = lmsServiceHelper.createMasterBag(Long.parseLong(deliveryCenterID), "DC", "Bangalore", lmsServiceHelper.getHubByCode(expectedReturnHub).getHub().get(0).getId(), "HUB", "Bangalore", EnumSCM.NORMAL);
        ExceptionHandler.handleEquals(shipmentResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "MasterBag creation failed");
        long masterBagId = shipmentResponse.getEntries().get(0).getId();
        ExceptionHandler.handleEquals(lmsServiceHelper.addAndSaveReturnIntoMasterBag(""+returnId, "" + masterBagId, ShipmentType.PU), EnumSCM.SUCCESS, "Unable to save Master Bag");
        ExceptionHandler.handleEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close master bag");
        return masterBagId;
    };

    /**
     * pushMockTMSMasterBag
     * @param source
     * @param destination
     * @param noOfShipments
     * @return
     * @throws JAXBException
     * @throws IOException
     * @throws ManagerException
     */
    public String pushMockTMSMasterBag(String source, String destination, int noOfShipments) throws JAXBException, IOException, ManagerException {
        TMSMasterbagEntry tmsMasterbagEntry = new TMSMasterbagEntry();
        List<TMSMasterbagShipmentEntry> masterbagShipmentEntries = new ArrayList<>();
        String masterBagId = "98"+LMSUtils.randomGenn(8);
        for (int i=0;i<noOfShipments;i++){
            TMSMasterbagShipmentEntry masterbagShipmentEntry = new TMSMasterbagShipmentEntry();
            masterbagShipmentEntry.setSecondaryMobileNumber("1234567890");
            masterbagShipmentEntry.setCustomerName("lmsautomation");
            masterbagShipmentEntry.setTmsMasterbagId(Long.parseLong(masterBagId));
            masterbagShipmentEntry.setConsignorAddress("Delhi");
            masterbagShipmentEntry.setConsignorName("lms automation");
            masterbagShipmentEntry.setWeight(123.00);
            masterbagShipmentEntry.setShipmentAttribute("Large");
            masterbagShipmentEntry.setStatus(TMSMasterbagStatus.NEW);
            masterbagShipmentEntry.setCodAmount(1099.00);
            masterbagShipmentEntry.setVat(159.00);
            masterbagShipmentEntry.setState("KA");
            masterbagShipmentEntry.setConsignorTin("1234567890");
            masterbagShipmentEntry.setPrimaryMobileNumber("1234567890");
            masterbagShipmentEntry.setCity("Bangalore");
            masterbagShipmentEntry.setCountry("INDIA");
            masterbagShipmentEntry.setPincode("560068");
            masterbagShipmentEntry.setShipmentValue(1599.00);
            masterbagShipmentEntry.setTrackingNumber("ML"+LMSUtils.randomGenn(10));
            masterbagShipmentEntry.setEmail("lmsautomation@myntra.com");
            masterbagShipmentEntry.setCustomerAddress("lmsautomation test address");
            masterbagShipmentEntry.setItemDetails("test item");
            masterbagShipmentEntry.setOrderId("987"+LMSUtils.randomGenn(8));
            masterbagShipmentEntries.add(masterbagShipmentEntry);
        }
        tmsMasterbagEntry.setLastScannedOn(new Date());
        tmsMasterbagEntry.setDestinationHub(destination);
        tmsMasterbagEntry.setSourceHub(source);
        tmsMasterbagEntry.setStatus(TMSMasterbagStatus.NEW);
        tmsMasterbagEntry.setLastScannedCity("Bangalore");
        tmsMasterbagEntry.setMasterbagId(masterBagId);
        tmsMasterbagEntry.setLastScannedHub(source);
        tmsMasterbagEntry.setMasterbagShipmentEntries(masterbagShipmentEntries);
        String payload = APIUtilities.convertXMLObjectToString(tmsMasterbagEntry);
        ExceptionHandler.handleEquals(((TMSMasterbagReponse)APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TMS_CREATE_UPDATE, null,
                SERVICE_TYPE.TMS_SVC.toString(), HTTPMethods.POST, payload, Headers.getLmsHeaderXML()).getResponseBody(), new TMSMasterbagReponse())).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
         return masterBagId;
    }

}
