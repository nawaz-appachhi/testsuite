package com.myntra.apiTests.erpservices.lms.Helper;

import com.google.gson.Gson;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.Constants.*;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.TryNBuyEntry;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_LOGIN;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.lms.Constants.PaymentMode;
import com.myntra.apiTests.erpservices.lms.lmsClient.*;
import com.myntra.apiTests.erpservices.oms.OMSHelpersEnums.ReadyToDispatchType;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.erpservices.serviceability.Helper.ServiceabilityServiceImpl;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.client.tools.response.ApplicationPropertiesResponse;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.exception.ManagerException;
import com.myntra.commons.response.EmptyResponse;
import com.myntra.cts.entries.CourierTrackingResponse;
import com.myntra.cts.entries.ekart.EkartShipmentUpdateRequest;
import com.myntra.lms.client.response.*;
import com.myntra.lms.client.status.*;
import com.myntra.lms.client.status.ShipmentStatus;
import com.myntra.lms.client.status.ShippingMethod;
import com.myntra.lms.client.types.ManifestType;
import com.myntra.lms.serviceabilityV2.response.CourierServiceabilityInfoResponseV2;
import com.myntra.logistics.platform.domain.*;
import com.myntra.logistics.platform.domain.ml.MLShipmentUpdate;
import com.myntra.logistics.platform.domain.ml.MLShipmentUpdateEvent;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.SlackMessenger;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.redis.RedisUtil;
import com.myntra.oms.client.entry.PacketEntry;
import com.myntra.oms.client.entry.ReleaseUpdateEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.returns.common.enums.RefundMode;
import com.myntra.returns.common.enums.ReturnMode;
import com.myntra.returns.common.enums.code.ReturnLineStatus;
import com.myntra.returns.common.enums.code.ReturnStatus;
import com.myntra.returns.entry.ReturnEntry;
import com.myntra.returns.response.ReturnResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.testng.Assert;
import org.testng.SkipException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;

//import com.myntra.lms.client.types.ManifestType;

//import com.myntra.lms.client.ManifestType;


/**
 * @author abhijit.pati & Shubham gupta
 */
@SuppressWarnings("deprecation")
public class LmsServiceHelper {
    
    private RedisUtil redisUtil = new RedisUtil();
    private OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    private LMSHelper lmsHelper = new LMSHelper();
    private IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
    private RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
    private ProcessRelease processRelease = new ProcessRelease();
    public static String MYNTRA_SOURCE_ID;
    public static String JABONG_SOURCE_ID;
    private int waitTime = 15;
    static Initialize init = new Initialize("/Data/configuration");
    private static String envName = "fox7";
    private static Logger log = Logger.getLogger(LmsServiceHelper.class);

    @SuppressWarnings({ "static-access", "unchecked" })
	public void LmsServiceHelper(){
        try {
        	
			 this.MYNTRA_SOURCE_ID = (String) getShipmentSource.apply("MYNTRA");
			 this.JABONG_SOURCE_ID = (String) getShipmentSource.apply("JABONG");
		        envName = init.Configurations.GetTestEnvironemnt().name();
		} catch (ManagerException | IOException | JAXBException | JSONException | XMLStreamException
				| InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

    @SuppressWarnings("rawtypes")
    public Supplier getDate = ()->ISODateTimeFormat.dateTime().print(new DateTime());

    //dataTime
    @SuppressWarnings("rawtypes")
    public Supplier getDateTimeSQL = () -> {
        return (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    };

    @SuppressWarnings("rawtypes")
    public Supplier getDateOnly = ()-> LocalDateTime.now().toLocalDate().toString();


    /**
     * Update Status to AWAITING in DB
     *
     * @param orderId
     * @return
     */
    @SuppressWarnings({ "unused", "unchecked" })
    private String setStatus(String orderId) throws SQLException {
       
    	List<Map<String, Object>> courieCreationList = DBUtilities.exSelectQuery("select courier_creation_status from order_tracking where order_id=" + orderId, "LMS");
        if (courieCreationList.size() == 0) {
            SlackMessenger.send("scm_e2e_order_sanity", "Unable find order in order_tracking table", 3);
            Assert.fail("Unable find order in order_tracking table");
        }

        Map<String, Object> result = (Map<String, Object>) courieCreationList.get(0);

        String courieCreationStatus = "" + result.get("courier_creation_status");

        if (courieCreationStatus == null && courieCreationStatus.equals("NOT_INITIATED")) {
        	
            DBUtilities.exUpdateQuery("update order_tracking set courier_creation_status='AWAITING' where order_id=" + orderId, "LMS");
        }
        
        return "AWAITING";
    }

    /**
     * Get Delivery Staff ID
     *
     * @param deliveryCenterID
     * @return
     */
    public String getDeliveryStaffID(String deliveryCenterID) {
            Map<String, Object> deliveryStaff = DBUtilities
                    .exSelectQueryForSingleRecord("select distinct ds.id from delivery_staff ds where ds.id not in (select distinct delivery_staff_id from trip " +
                            "where trip_status in('OUT_FOR_DELIVERY','CREATED')) and ds.delivery_staff_type = 'MYNTRA_PAYROLL' " +
                            "and ds.delivery_center_id = "+deliveryCenterID+" order by RAND() limit 1;", "LMS");
            return deliveryStaff.get("id").toString();
    }

    /**
     * getDeliveryStaffID
     * @param deliveryCenterID
     * @return
     */
    public long getDeliveryStaffID(long deliveryCenterID) {
        Map<String, Object> deliveryStaff = DBUtilities
                .exSelectQueryForSingleRecord("select distinct ds.id from delivery_staff ds where ds.id not in (select distinct delivery_staff_id from trip " +
                        "where trip_status in('OUT_FOR_DELIVERY','CREATED')) and ds.delivery_staff_type = 'MYNTRA_PAYROLL' " +
                        "and ds.delivery_center_id = "+deliveryCenterID+" order by RAND() limit 1;", "LMS");
        return (long)deliveryStaff.get("id");
    }

    /**
     * getMasterBagID
     *
     * @param orderReleaseID
     * @return
     */
    @SuppressWarnings("unchecked")
	public Long getMasterBagID(String orderReleaseID) {

        long masterbagId = 0L;
        List<Map<String, Object>> list = DBUtilities.exSelectQuery("select shipment_id from shipment_order_map where order_id='" + orderReleaseID +"'", "myntra_lms");
        Map<String, Object> hm = (Map<String, Object>) list.get(0);
        masterbagId = (long) hm.get("shipment_id");

        return masterbagId;
    }

    public ReturnResponse CreateReturn(String customerName, String email, String login, String mobile, String orderId,
                                       String releaseId, String optionId, String orderLineId, String styleId, String qty, String supplyType,
                                       String skuId, String courierCode)
            throws JAXBException,  IOException {
        return null;

    }

    /**
     * pushPickupToCourier
     *
     * @param returnId
     * @return
     * @throws JAXBException
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public PickupResponse pushPickupToCourier(String returnId)
            throws JAXBException,  IOException {

        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PUSH_PICKUP_TO_COURIER, new String[]{returnId},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, null, Headers.getpushPickupToCourierHeader());
        PickupResponse pickupResponse = (PickupResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
                new PickupResponse());
        return pickupResponse;
    }

    /**
     * updateEndOdometerReading
     *
     * @param tripId
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws JSONException
     * @throws XMLStreamException
     */
    public TripResponse updateEndOdometerReading(String tripId) throws
            IOException, JAXBException, JSONException, XMLStreamException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.UPDATE_END_ODOMETER, new String[]{tripId, "200"},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, null, Headers.getLmsHeaderJSON());
        TripResponse response = (TripResponse) APIUtilities.jsonToObject(service.getResponseBody(),
                TripResponse.class);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Update odometer Failed");
        return response;
    }

    @SuppressWarnings("rawtypes")
	public com.myntra.apiTests.common.Constants.LambdaInterfaces.TriFunction updateOdodmeterReading = (tripId, start, end)->{
        return APIUtilities.jsonToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.UPDATE_ODOMETER, new String[]{""+tripId, ""+start,""+end,"system"},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, null, Headers.getLmsHeaderJSON()).getResponseBody(),
                TripResponse.class);};

    /**
     * addDC
     *
     * @param code
     * @param name
     * @param manager
     * @param storeId
     * @param address
     * @param city
     * @param cityCode
     * @param state
     * @param pincode
     * @param selfShipSupported
     * @param isStrictServiceable
     * @param active
     * @param isCardEnabled
     * @param courierCode
     * @param contactNumber
     * @param type
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public DeliveryCenterResponse addDC(String code, String name, String manager, Long storeId, String address,
                                        String city, String cityCode, String state, String pincode, Boolean selfShipSupported,
                                        Boolean isStrictServiceable, Boolean active, Boolean isCardEnabled, String courierCode,
                                        String contactNumber, DeliveryCenterType type)
            throws  IOException, JAXBException {
        DeliveryCenterEntry deliveryCenterEntry = new DeliveryCenterEntry();
        deliveryCenterEntry.setCode(code);
        deliveryCenterEntry.setName(name);
        deliveryCenterEntry.setManager(manager);
        deliveryCenterEntry.setStoreId(storeId);
        deliveryCenterEntry.setAddress(address);
        deliveryCenterEntry.setCity(city);
        deliveryCenterEntry.setCityCode(cityCode);
        deliveryCenterEntry.setState(state);
        deliveryCenterEntry.setStoreId(storeId);
        deliveryCenterEntry.setPincode(pincode);
        deliveryCenterEntry.setSelfShipSupported(selfShipSupported);
        deliveryCenterEntry.setIsStrictServiceable(isStrictServiceable);
        deliveryCenterEntry.setActive(active);
        deliveryCenterEntry.setIsCardEnabled(isCardEnabled);
        deliveryCenterEntry.setCourierCode(courierCode);
        deliveryCenterEntry.setContactNumber(contactNumber);
        deliveryCenterEntry.setType(type);
        deliveryCenterEntry.setTmsHubCode("TH-BLR");
        deliveryCenterEntry.setBulkInvoiceEnabled(false);
//        deliveryCenterEntry.setRegisteredAddressLine1("test");
//        deliveryCenterEntry.setRegisteredAddressLine2("test1");
//        deliveryCenterEntry.setRegisteredStateCode("KA");
//        deliveryCenterEntry.setRegisteredPincode("");
//        deliveryCenterEntry.setRegisteredCity("test");
//        deliveryCenterEntry.setRegisteredCompanyName("xyz");
        String payload = APIUtilities.convertXMLObjectToString(deliveryCenterEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.DELIVERY_CENTRE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        DeliveryCenterResponse response = (DeliveryCenterResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new DeliveryCenterResponse());
        Assert.assertNotNull(response);
        return response;
    }

    /**
     * updateDC
     *
     * @param id
     * @param code
     * @param name
     * @param manager
     * @param storeId
     * @param address
     * @param city
     * @param cityCode
     * @param state
     * @param pincode
     * @param selfShipSupported
     * @param isStrictServiceable
     * @param active
     * @param isCardEnabled
     * @param courierCode
     * @param contactNumber
     * @param type
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public DeliveryCenterResponse updateDC(String id, String code, String name, String manager, Long storeId,
                                           String address, String city, String cityCode, String state, String pincode, Boolean selfShipSupported,
                                           Boolean isStrictServiceable, Boolean active, Boolean isCardEnabled, String courierCode,
                                           String contactNumber, DeliveryCenterType type)
            throws IOException, JAXBException {
        DeliveryCenterEntry deliveryCenterEntry = new DeliveryCenterEntry();
        deliveryCenterEntry.setCode(code);
        deliveryCenterEntry.setName(name);
        deliveryCenterEntry.setManager(manager);
        deliveryCenterEntry.setStoreId(storeId);
        deliveryCenterEntry.setAddress(address);
        deliveryCenterEntry.setCity(city);
        deliveryCenterEntry.setCityCode(cityCode);
        deliveryCenterEntry.setState(state);
        deliveryCenterEntry.setStoreId(storeId);
        deliveryCenterEntry.setPincode(pincode);
        deliveryCenterEntry.setSelfShipSupported(selfShipSupported);
        deliveryCenterEntry.setIsStrictServiceable(isStrictServiceable);
        deliveryCenterEntry.setActive(active);
        deliveryCenterEntry.setIsCardEnabled(isCardEnabled);
        deliveryCenterEntry.setCourierCode(courierCode);
        deliveryCenterEntry.setContactNumber(contactNumber);
        deliveryCenterEntry.setType(type);
        String payload = APIUtilities.convertXMLObjectToString(deliveryCenterEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.DELIVERY_CENTRE, new String[]{id},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        DeliveryCenterResponse response = (DeliveryCenterResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new DeliveryCenterResponse());
        Assert.assertNotNull(response);
        return response;
    }

    /**
     * getDC
     *
     * @param pathParam
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws JSONException
     * @throws XMLStreamException
     */
    public DeliveryCenterResponse getDC(String pathParam) throws
            IOException, JAXBException, JSONException, XMLStreamException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.DELIVERY_CENTRE, new String[]{pathParam},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderJSON());
        DeliveryCenterResponse response = (DeliveryCenterResponse) APIUtilities.jsonToObject(service.getResponseBody(),
                DeliveryCenterResponse.class);
        Assert.assertNotNull(response);
        return response;
    }

    @SuppressWarnings("rawtypes")
	public LambdaInterfaces.Function getDCIdForDCCode = dcCode -> getDC("search?q=code.like:"+dcCode+"&start=0&fetchSize=20").getDeliveryCenters().get(0).getId();

    @SuppressWarnings("rawtypes")
	public LambdaInterfaces.Function getDCcodeForDCId = id -> getDC("search?q=id.like:"+id+"&start=0&fetchSize=20").getDeliveryCenters().get(0).getCode();

    /**
     * addDeliveryStaff
     *
     * @param code
     * @param firstName
     * @param lastName
     * @param deliveryCenterId
     * @param mobile
     * @param createdBy
     * @param available
     * @param deleted
     * @param modeOfCommute
     * @param employeeCode
     * @param isCardEnabled
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public DeliveryStaffResponse addDeliveryStaff(String code, String firstName, String lastName, long deliveryCenterId,
                                                  String mobile, String createdBy, Boolean available, Boolean deleted, DeliveryStaffCommute modeOfCommute,
                                                  String employeeCode, Boolean isCardEnabled)
            throws IOException, JAXBException {
        DeliveryStaffEntry deliveryStaffEntry = new DeliveryStaffEntry();
        deliveryStaffEntry.setCode(code);
        deliveryStaffEntry.setFirstName(firstName);
        deliveryStaffEntry.setLastName(lastName);
        deliveryStaffEntry.setDeliveryCenterId(deliveryCenterId);
        deliveryStaffEntry.setMobile(mobile);
        deliveryStaffEntry.setCreatedBy(createdBy);
        deliveryStaffEntry.setAvailable(available);
        deliveryStaffEntry.setDeleted(deleted);
        deliveryStaffEntry.setModeOfCommute(modeOfCommute);
        deliveryStaffEntry.setEmpCode(employeeCode);
        deliveryStaffEntry.setIsCardEnabled(isCardEnabled);
        String payload = APIUtilities.convertXMLObjectToString(deliveryStaffEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.DELIVERY_STAFF, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        DeliveryStaffResponse response = (DeliveryStaffResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new DeliveryStaffResponse());
        return response;
    }

    /**
     * updateDeliveryStaff
     *
     * @param id
     * @param code
     * @param firstName
     * @param lastName
     * @param deliveryCenterId
     * @param mobile
     * @param createdBy
     * @param available
     * @param deleted
     * @param modeOfCommute
     * @param employeeCode
     * @param isCardEnabled
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public DeliveryStaffResponse updateDeliveryStaff(String id, String code, String firstName, String lastName,
                                                     long deliveryCenterId, String mobile, String createdBy, Boolean available, Boolean deleted,
                                                     DeliveryStaffCommute modeOfCommute, String employeeCode, Boolean isCardEnabled)
            throws IOException, JAXBException {
        DeliveryStaffEntry deliveryStaffEntry = new DeliveryStaffEntry();
        deliveryStaffEntry.setCode(code);
        deliveryStaffEntry.setFirstName(firstName);
        deliveryStaffEntry.setLastName(lastName);
        deliveryStaffEntry.setDeliveryCenterId(deliveryCenterId);
        deliveryStaffEntry.setMobile(mobile);
        deliveryStaffEntry.setCreatedBy(createdBy);
        deliveryStaffEntry.setAvailable(available);
        deliveryStaffEntry.setDeleted(deleted);
        deliveryStaffEntry.setModeOfCommute(modeOfCommute);
        deliveryStaffEntry.setEmpCode(employeeCode);
        deliveryStaffEntry.setIsCardEnabled(isCardEnabled);
        String payload = APIUtilities.convertXMLObjectToString(deliveryStaffEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.DELIVERY_STAFF, new String[]{id},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        DeliveryStaffResponse response = (DeliveryStaffResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new DeliveryStaffResponse());
        return response;
    }

    /**
     * getDeliveryStaff
     *
     * @param pathParam
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public DeliveryStaffResponse getDeliveryStaff(String pathParam)
            throws IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.DELIVERY_STAFF, new String[]{pathParam},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        DeliveryStaffResponse response = (DeliveryStaffResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new DeliveryStaffResponse());
        return response;
    }

    /**
     * generateTrackingNumber
     *
     * @param courierCode
     * @param start
     * @param end
     * @param increment
     * @param prefix
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public TrackingNumberResponse generateTrackingNumber(String courierCode, long start, long end, int increment,
                                                         String prefix) throws IOException, JAXBException {
        TrackingNumberGenerationEntry trackingNumberGenerationEntry = new TrackingNumberGenerationEntry();
        trackingNumberGenerationEntry.setCourierCode(courierCode);
        trackingNumberGenerationEntry.setStart(start);
        trackingNumberGenerationEntry.setEnd(end);
        trackingNumberGenerationEntry.setIncrement(increment);
        trackingNumberGenerationEntry.setPrefix(prefix);
        String payload = APIUtilities.convertXMLObjectToString(trackingNumberGenerationEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.GENERATE_TRACKING_NUMBER, null,
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TrackingNumberResponse response = (TrackingNumberResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TrackingNumberResponse());
        return response;
    }

    /**
     * getTrackingNumber
     *
     * @param courier
     * @param wh
     * @param isCod
     * @param pinCode
     * @param shipmentType
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public TrackingNumberResponse getTrackingNumber(String courier, String wh, String isCod, String pinCode, String shipmentType)
            throws IOException, JAXBException {
    	    if(shipmentType.equals("XPRESS")) {
    	    	
    	    		shipmentType = "EXPRESS";
    	    }
    	    
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_TRACKING_NUMBER, new String[]{courier, wh, isCod, pinCode, shipmentType},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, null, Headers.getLmsHeaderXML());
        TrackingNumberResponse response = (TrackingNumberResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TrackingNumberResponse());
        return response;
    }

    /**
     * addCourier
     *
     * @param code
     * @param colorCode
     * @param dailyCapacity
     * @param enabled
     * @param isRegional
     * @param manifestTemplate
     * @param name
     * @param pickupSupported
     * @param pickupTrackingNumberSplitEnabled
     * @param regional
     * @param returnSupported
     * @param splitTrackingNumberEnabled
     * @param trackingNoSource
     * @param trackingNumberGenerationSupported
     * @param warehouseSplitEnabled
     * @param website
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public CourierResponse addCourier(String code, String colorCode, Long dailyCapacity, Boolean enabled,
                                      Boolean isRegional, String manifestTemplate, String name, Boolean pickupSupported,
                                      Boolean pickupTrackingNumberSplitEnabled, Boolean regional, Boolean returnSupported,
                                      Boolean splitTrackingNumberEnabled, TrackingNoSource trackingNoSource,
                                      Boolean trackingNumberGenerationSupported, Boolean warehouseSplitEnabled, String website)
            throws IOException, JAXBException {
        CourierEntry courierEntry = new CourierEntry();
        courierEntry.setCode(code);
        courierEntry.setColorCode(colorCode);
        courierEntry.setDailyCapacity(dailyCapacity);
        courierEntry.setEnabled(enabled);
        courierEntry.setIsRegional(isRegional);
        courierEntry.setManifestTemplate(manifestTemplate);
        courierEntry.setName(name);
        courierEntry.setPickupSupported(pickupSupported);
        courierEntry.setPickupTrackingNumberSplitEnabled(pickupTrackingNumberSplitEnabled);
        courierEntry.setRegional(regional);
        courierEntry.setReturnSupported(returnSupported);
        courierEntry.setSplitTrackingNumberEnabled(splitTrackingNumberEnabled);
        courierEntry.setTrackingNoSource(trackingNoSource);
        courierEntry.setTrackingNumberGenerationSupported(trackingNumberGenerationSupported);
        courierEntry.setWarehouseSplitEnabled(warehouseSplitEnabled);
        courierEntry.setWebsite(website);
        courierEntry.setManifestType(ManifestType.SELLER_LEVEL_CONSOLIDATED);
        courierEntry.setSupportsMultipleSellerShipments(false);

        String payload = APIUtilities.convertXMLObjectToString(courierEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.COURIER, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        CourierResponse response = (CourierResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new CourierResponse());
        return response;
    }

    /**
     * updateCourier
     *
     * @param id
     * @param code
     * @param colorCode
     * @param dailyCapacity
     * @param enabled
     * @param isRegional
     * @param manifestTemplate
     * @param name
     * @param pickupSupported
     * @param pickupTrackingNumberSplitEnabled
     * @param regional
     * @param returnSupported
     * @param splitTrackingNumberEnabled
     * @param trackingNoSource
     * @param trackingNumberGenerationSupported
     * @param warehouseSplitEnabled
     * @param website
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public CourierResponse updateCourier(String id, String code, String colorCode, Long dailyCapacity, Boolean enabled,
                                         Boolean isRegional, String manifestTemplate, String name, Boolean pickupSupported,
                                         Boolean pickupTrackingNumberSplitEnabled, Boolean regional, Boolean returnSupported,
                                         Boolean splitTrackingNumberEnabled, TrackingNoSource trackingNoSource,
                                         Boolean trackingNumberGenerationSupported, Boolean warehouseSplitEnabled, String website)
            throws IOException, JAXBException {
        CourierEntry courierEntry = new CourierEntry();
        courierEntry.setCode(code);
        courierEntry.setColorCode(colorCode);
        courierEntry.setDailyCapacity(dailyCapacity);
        courierEntry.setEnabled(enabled);
        courierEntry.setIsRegional(isRegional);
        courierEntry.setManifestTemplate(manifestTemplate);
        courierEntry.setName(name);
        courierEntry.setPickupSupported(pickupSupported);
        courierEntry.setPickupTrackingNumberSplitEnabled(pickupTrackingNumberSplitEnabled);
        courierEntry.setRegional(regional);
        courierEntry.setReturnSupported(returnSupported);
        courierEntry.setSplitTrackingNumberEnabled(splitTrackingNumberEnabled);
        courierEntry.setTrackingNoSource(trackingNoSource);
        courierEntry.setTrackingNumberGenerationSupported(trackingNumberGenerationSupported);
        courierEntry.setWarehouseSplitEnabled(warehouseSplitEnabled);
        courierEntry.setWebsite(website);

        String payload = APIUtilities.convertXMLObjectToString(courierEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.COURIER, new String[]{id},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        CourierResponse response = (CourierResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new CourierResponse());
        return response;
    }

    /**
     * getCourier
     *
     * @param pathParams
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public CourierResponse getCourier(String pathParams)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.COURIER, new String[]{pathParams},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        CourierResponse response = (CourierResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new CourierResponse());
        return response;
    }

    /**
     * getCourierStatistics
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public CourierStatisticsResponse getCourierStatistics()
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.COURIER_STATISTICS, null,
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        CourierStatisticsResponse response = (CourierStatisticsResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new CourierStatisticsResponse());
        return response;
    }

    public PincodeResponse getCourierForPincode(String pincode)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.COURIER_FOR_PINCODE, new String[]{pincode},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        PincodeResponse response = (PincodeResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new PincodeResponse());
        return response;
    }

    /**
     * addRegion
     *
     * @param code
     * @param name
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public RegionResponse addRegion(String code, String name)
            throws  IOException, JAXBException {
        RegionEntry regionEntry = new RegionEntry();
        regionEntry.setCode(code);
        regionEntry.setName(name);
        regionEntry.setDescription("Test Region");
        regionEntry.setCreatedBy("TestAdmin");
        String payload = APIUtilities.convertXMLObjectToString(regionEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.REGION, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        RegionResponse response = (RegionResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new RegionResponse());
        return response;
    }

    /**
     * addRegion
     *
     * @param code
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public RegionResponse addRegion(String code)
            throws  IOException, JAXBException {
        RegionEntry regionEntry = new RegionEntry();
        regionEntry.setCode(code);
        regionEntry.setDescription("Test Region");
        regionEntry.setCreatedBy("TestAdmin");
        String payload = APIUtilities.convertXMLObjectToString(regionEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.REGION, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        RegionResponse response = (RegionResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new RegionResponse());
        return response;
    }

    /**
     * updateRegion
     *
     * @param id
     * @param code
     * @param name
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public RegionResponse updateRegion(String id, String code, String name)
            throws  IOException, JAXBException {
        RegionEntry regionEntry = new RegionEntry();
        regionEntry.setCode(code);
        regionEntry.setName(name);
        regionEntry.setDescription("Test Region");
        regionEntry.setCreatedBy("TestAdmin");
        String payload = APIUtilities.convertXMLObjectToString(regionEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.REGION, new String[]{id},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        RegionResponse response = (RegionResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new RegionResponse());
        return response;
    }

    /**
     * getRegion
     *
     * @param pathParam
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public RegionResponse getRegion(String pathParam)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.REGION, new String[]{pathParam},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        RegionResponse response = (RegionResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new RegionResponse());
        return response;
    }

    /**
     * addPincode
     *
     * @param id
     * @param areaCode
     * @param areaName
     * @param cityCode
     * @param cityName
     * @param regionCode
     * @param state
     * @param stateCode
     * @param createdBy
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public PincodeResponse addPincode(long id, String areaCode, String areaName, String cityCode, String cityName,
                                      String regionCode, String state, String stateCode, String createdBy)
            throws  IOException, JAXBException {
        PincodeEntry pincodeEntry = new PincodeEntry();
        pincodeEntry.setId(id);
        pincodeEntry.setAreaCode(areaCode);
        pincodeEntry.setAreaName(areaName);
        pincodeEntry.setCityCode(cityCode);
        pincodeEntry.setCityName(cityName);
        pincodeEntry.setRegionCode(regionCode);
        pincodeEntry.setState(state);
        pincodeEntry.setStateCode(stateCode);
        pincodeEntry.setCreatedBy(createdBy);

        String payload = APIUtilities.convertXMLObjectToString(pincodeEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PINCODE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        PincodeResponse response = (PincodeResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new PincodeResponse());
        return response;
    }

    /**
     * updatePincode
     *
     * @param id
     * @param areaCode
     * @param areaName
     * @param cityCode
     * @param cityName
     * @param regionCode
     * @param state
     * @param stateCode
     * @param createdBy
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public PincodeResponse updatePincode(String id, String areaCode, String areaName, String cityCode, String cityName,
                                         String regionCode, String state, String stateCode, String createdBy)
            throws  IOException, JAXBException {
        PincodeEntry pincodeEntry = new PincodeEntry();
        pincodeEntry.setId(Long.parseLong(id));
        pincodeEntry.setAreaCode(areaCode);
        pincodeEntry.setAreaName(areaName);
        pincodeEntry.setCityCode(cityCode);
        pincodeEntry.setCityName(cityName);
        pincodeEntry.setRegionCode(regionCode);
        pincodeEntry.setState(state);
        pincodeEntry.setStateCode(stateCode);
        pincodeEntry.setCreatedBy(createdBy);

        String payload = APIUtilities.convertXMLObjectToString(pincodeEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PINCODE, new String[]{id},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        PincodeResponse response = (PincodeResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new PincodeResponse());
        return response;
    }

    /**
     * getPincode
     *
     * @param pathParam
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public PincodeResponse getPincode(String pathParam)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PINCODE, new String[]{pathParam},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        PincodeResponse response = (PincodeResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new PincodeResponse());
        return response;
    }

    /**
     * getPincodev1
     * @param pincode
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public PincodeResponseV2 getPincodeV1(String pincode)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PINCODEV1, new String[]{pincode},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        PincodeResponseV2 response = (PincodeResponseV2) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new PincodeResponseV2());
        return response;
    }

    /**
     * getShippingCutOff : Hit this api to get Shipping cutoff from LMS
     * @param hr
     * @param pincode
     * @param warehouse
     * @param shippingMethod
     * @param courier
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public ShippingCutoffResponse getShippingCutOff(String hr, String pincode, String warehouse, ShippingMethod shippingMethod, String courier ) throws JAXBException, UnsupportedEncodingException {
        OrderEntry order = new OrderEntry();
        order.setZipcode(pincode);
        order.setWarehouseId(warehouse);
        order.setShippingMethod(shippingMethod);
        order.setCourierOperator(courier);
        String payload = APIUtilities.convertXMLObjectToString(order);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_SHIPPING_CUTOFF, new String[]{hr},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ShippingCutoffResponse response = (ShippingCutoffResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new ShippingCutoffResponse());
        return response;
    }
    /**
     * getPincode
     *
     * @param pincode
     * @param courierCode
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public PincodeResponse getPincode(String pincode, String courierCode)
            throws  IOException, JAXBException {
        String pathParam = "search?q=number.eq:" + pincode + "___courierCode.eq:" + courierCode;
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PINCODE_, new String[]{pathParam},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        PincodeResponse response = (PincodeResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new PincodeResponse());
        return response;
    }

    /**
     * getLmsOrders
     *
     * @param pathParam
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public OrderResponse getLmsOrdersByParama(String pathParam)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.ORDER, new String[]{pathParam},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        OrderResponse response = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderResponse());
        return response;
    }

    /**
     * getLmsOrders
     *
     * @param orderId
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public OrderResponse getLmsOrders(String orderId) throws  IOException, JAXBException {
        String pathParam = "dashboardSearch?q=orderId.eq:"+orderId+"&start=0&fetchSize=20&sortBy=id&sortOrder=DESC";
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.ORDER, new String[]{pathParam},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        OrderResponse response = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderResponse());
        return response;
    }

    /**
     * orderInScanNew
     * @param orderId
     * @param warehouse
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
	public String orderInScanNew(Object orderId, String warehouse)
            throws IOException, JAXBException, JSONException, XMLStreamException, InterruptedException, ManagerException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PLATEFORM_SHIPMENT,
                new String[]{""+orderId, "inscan?forceInscan=true&locationId="+getHubConfig(warehouse, "DL")+"&locationType=HUB"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.PUT, null, Headers.getLmsHeaderJSON());
        String response = APIUtilities.getElement(service.getResponseBody(), "ShipmentResponse.status.statusType", "json");
        if (response.equals("ERROR")) {
            OrderEntry order = ((OrderResponse) getOrderLMS.apply(orderId)).getOrders().get(0);
            if (!order.getCourierOperator().equals("ML")){
                DBUtilities.exUpdateQuery("update order_tracking set courier_creation_status = 'ACCEPTED' where order_id = '" + orderId+"'", "lms");
                Svc service1 = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PLATEFORM_SHIPMENT,
                        new String[]{""+orderId, "inscan?forceInscan=true&locationId="+getHubConfig(warehouse, "DL")+"&locationType=HUB"}, SERVICE_TYPE.LMS_SVC.toString(),
                        HTTPMethods.PUT, null, Headers.getLmsHeaderJSON());
                response = APIUtilities.getElement(service1.getResponseBody(), "ShipmentResponse.status.statusType", "json");
            }
        }
        
        return response;
    }
    
    @SuppressWarnings("unchecked")
	public String orderInScanNew(Object orderId, String warehouse, String courierCreationStatus)
            throws IOException, JAXBException, JSONException, XMLStreamException, InterruptedException, ManagerException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PLATEFORM_SHIPMENT,
                new String[]{""+orderId, "inscan?forceInscan=true&locationId="+getHubConfig(warehouse, "DL")+"&locationType=HUB"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.PUT, null, Headers.getLmsHeaderJSON());
        String response = APIUtilities.getElement(service.getResponseBody(), "ShipmentResponse.status.statusType", "json");
        if (response.equals("ERROR")) {
            OrderEntry order = ((OrderResponse) getOrderLMS.apply(orderId)).getOrders().get(0);
            if (!order.getCourierOperator().equals("ML")){
                DBUtilities.exUpdateQuery("update order_tracking set courier_creation_status = '"+courierCreationStatus+"' where order_id = '" + orderId+"'", "lms");
                Svc service1 = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PLATEFORM_SHIPMENT,
                        new String[]{""+orderId, "inscan?forceInscan=true&locationId="+getHubConfig(warehouse, "DL")+"&locationType=HUB"}, SERVICE_TYPE.LMS_SVC.toString(),
                        HTTPMethods.PUT, null, Headers.getLmsHeaderJSON());
                response = APIUtilities.getElement(service1.getResponseBody(), "ShipmentResponse.status.statusType", "json");
            }
        }
        
        return response;
    }

    /**
     * orderInScanNew
     * @param orderId
     * @param hubCode
     * @return
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings("unchecked")
	public String orderInScanInHub(String orderId, String hubCode)
            throws IOException, InterruptedException, JAXBException, ManagerException, XMLStreamException, JSONException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PLATEFORM_SHIPMENT,
                new String[]{""+orderId, "inscan?forceInscan=true&locationId="+hubCode+"&locationType=HUB"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.PUT, null, Headers.getLmsHeaderJSON());
        String response = APIUtilities.getElement(service.getResponseBody(), "ShipmentResponse.status.statusType", "json");
        if (response.equals("ERROR")) {
            OrderEntry order = ((OrderResponse) getOrderLMS.apply(orderId)).getOrders().get(0);
            if (!order.getCourierOperator().equals("ML")){
                DBUtilities.exUpdateQuery("update order_tracking set courier_creation_status = 'ACCEPTED' where order_id = '" + orderId+"'", "lms");
                Svc service1 = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PLATEFORM_SHIPMENT,
                        new String[]{""+orderId, "inscan?forceInscan=true&locationId="+hubCode+"&locationType=HUB"}, SERVICE_TYPE.LMS_SVC.toString(),
                        HTTPMethods.PUT, null, Headers.getLmsHeaderJSON());
                response = APIUtilities.getElement(service1.getResponseBody(), "ShipmentResponse.status.statusType", "json");
            }
        }
        return response;
    }

    /**
     * orderInScanNew
     * @param orderId
     * @param warehouse
     * @param force
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
	public String orderInScanNew(Object orderId, String warehouse, boolean force)
            throws IOException, JAXBException, JSONException, XMLStreamException, InterruptedException, ManagerException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PLATEFORM_SHIPMENT,
                new String[]{""+orderId, "inscan?forceInscan="+force+"&locationId="+getHubConfig(warehouse, "DL")+"&locationType=HUB"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.PUT, null, Headers.getLmsHeaderJSON());
        String response = APIUtilities.getElement(service.getResponseBody(), "ShipmentResponse.status.statusType", "json");
        if (response.equals("ERROR")) {
            OrderEntry order = ((OrderResponse) getOrderLMS.apply(orderId)).getOrders().get(0);
            if (!order.getCourierOperator().equals("ML")) {
                DBUtilities.exUpdateQuery("update order_tracking set courier_creation_status = 'ACCEPTED' where order_id = '" + orderId+"'", "lms");
                Svc service1 = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PLATEFORM_SHIPMENT,
                        new String[]{""+orderId, "inscan?forceInscan="+force+"&locationId="+getHubConfig(warehouse, "DL")+"&locationType=HUB"}, SERVICE_TYPE.LMS_SVC.toString(),
                        HTTPMethods.PUT, null, Headers.getLmsHeaderJSON());
                response = APIUtilities.getElement(service1.getResponseBody(), "ShipmentResponse.status.statusType", "json");
            }
        }
        return response;
    }

    /**
     * orderInScanNew
     * @param orderId
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
	public String orderInScanNew(Object orderId)
            throws IOException, JAXBException, JSONException, XMLStreamException, InterruptedException, ManagerException {
        OrderEntry order = ((OrderResponse) getOrderLMS.apply(orderId)).getOrders().get(0);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PLATEFORM_SHIPMENT,
                new String[]{""+orderId, "inscan?forceInscan=true&locationId="+getHubConfig(order.getWarehouseId(), "DL")+"&locationType=HUB"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.PUT, null, Headers.getLmsHeaderJSON());
        String response = APIUtilities.getElement(service.getResponseBody(), "ShipmentResponse.status.statusType", "json");
        if (!order.getCourierOperator().equals("ML")&&response.equals("ERROR")) {
        	
            DBUtilities.exUpdateQuery("update order_tracking set courier_creation_status = 'ACCEPTED' where order_id = '" + orderId+"'", "lms");
            Svc service1 = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PLATEFORM_SHIPMENT,
                    new String[]{""+orderId, "inscan?forceInscan=true&locationId="+getHubConfig(order.getWarehouseId(), "DL")+"&locationType=HUB"}, SERVICE_TYPE.LMS_SVC.toString(),
                    HTTPMethods.PUT, null, Headers.getLmsHeaderJSON());
            response = APIUtilities.getElement(service1.getResponseBody(), "ShipmentResponse.status.statusType", "json");
        }
        System.out.println("response  response response "+response);
        return response;
    }

    /**
     * orderInScanNew
     * @param orderId
     * @param force
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
	public String orderInScanNew(Object orderId, boolean force)
            throws IOException, JAXBException, JSONException, XMLStreamException, InterruptedException, ManagerException {
        OrderEntry order = ((OrderResponse) getOrderLMS.apply(orderId)).getOrders().get(0);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PLATEFORM_SHIPMENT,
                new String[]{""+orderId, "inscan?forceInscan="+force+"&locationId="+getHubConfig(order.getWarehouseId(), "DL")+"&locationType=HUB"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.PUT, null, Headers.getLmsHeaderXML());
        String response = APIUtilities.getElement(service.getResponseBody(), "ShipmentResponse.status.statusType", "json");
        if (!order.getCourierOperator().equals("ML")&&response.equals("ERROR")) {
            DBUtilities.exUpdateQuery("update order_tracking set courier_creation_status = 'ACCEPTED' where order_id = '" + orderId+"'", "lms");
            Svc service1 = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PLATEFORM_SHIPMENT,
                    new String[]{""+orderId, "inscan?forceInscan="+force+"&locationId="+getHubConfig(order.getWarehouseId(), "DL")+"&locationType=HUB"}, SERVICE_TYPE.LMS_SVC.toString(),
                    HTTPMethods.PUT, null, Headers.getLmsHeaderXML());
             response = APIUtilities.getElement(service1.getResponseBody(), "ShipmentResponse.status.statusType", "json");
        }
        return response;
    }

    /**
     * orderInScanGOR
     * @param orderId
     * @param locationId
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public EmptyResponse orderInScanGOR(Object orderId, String locationId)
            throws IOException, JAXBException {
        String payload = "{\"eventTime\": \""+getDateTimeSQL.get()+"\", \"eventLocation\": \"Myntra Mumbai Warehouse\", \"shipmentId\": \""+orderId+"\"," +
                " \"remarks\": \"\", \"eventLocationId\": \""+locationId+"\", \"event\": \"INSCAN\"}";
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.INSCAN_GOR, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderJSON());
        EmptyResponse response = (EmptyResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new EmptyResponse());
        return response;
    }

    /**
     * getOrderByOrderId
     * @param orderId
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public OrderResponse getOrderByOrderId(String orderId)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.ORDER_BY_ID,
                new String[]{orderId + "?um=true"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        OrderResponse response = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderResponse());
        return response;
    }

    /**
     * getOrderByOrderIdByParam
     * @param orderId
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public OrderResponse getOrderByOrderIdByParam(String orderId)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.ORDER,
                new String[]{"search?fetchSize=-1&q=orderId.in:" + orderId + "&um=true"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        OrderResponse response = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderResponse());
        return response;
    }

    /**
     * getOrderLMS : To get the entire order(Release) in LMS
     */
    @SuppressWarnings("rawtypes")
	public LambdaInterfaces.Function getOrderLMS = orderId -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.ORDER,
            new String[]{"dashboardSearch?q=orderId.like:"+orderId+"&start=0&fetchSize=20&sortBy=id&sortOrder=DESC"}, SERVICE_TYPE.LMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(),
            new OrderResponse());

    /**
     * getOrderLMSDashboard : To get the entire order(Release) from dashboard search in LMS
     */
	@SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getOrderLMSDashboard = orderId -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.ORDER,
            new String[]{"dashboardSearch?q=orderId.like:"+orderId+"&start=0&fetchSize=20&sortBy=id&sortOrder=DESC"}, SERVICE_TYPE.LMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(),
            new OrderDashboardResponse());

    /**
     * getOrderML : To get the order from Myntra Logistics
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getOrderML = trackingNumber -> APIUtilities.getJsontoObjectUsingFasterXML(HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_ML_SHIPMENT_BY_TRACKING_NO,
            new String[]{trackingNumber.toString()}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderJSON()).getResponseBody(),
            new MLShipmentResponse());

    /**
     * getReturnLMS
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getReturnLMS = returnId -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.ORDER,
            new String[]{"dashboardSearch?q=sourceReturnId.like:"+returnId+"&start=0&fetchSize=20&sortBy=id&sortOrder=DESC"}, SERVICE_TYPE.LMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(),
            new OrderResponse());

    /**
     * getDropDownValues
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getDropDownValuesCourier = className -> HttpExecutorService.executeHttpService(Constants.LMS_PATH.COURIER,
            new String[]{"getDropDownValues?className="+className}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderJSON()).getResponseBody();

    /**
     * getMLTrackingDetails
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getMLTrackingDetails = trackingNumber -> APIUtilities.getJsontoObjectUsingFasterXML(HttpExecutorService.executeHttpService(Constants.LMS_PATH.ML_SHIPMENT_SERVICE,
            new String[]{"getOrderTrackingDetail?trackingNumber="+trackingNumber}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderJSON()).getResponseBody(),
            new OrderTrackingResponseV2());

    /**
     * getTripOrderAssignment
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getTripOrderAssignment = trackingNumber -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_ORDER_ASSIGNMENT,
            new String[]{"search?q=trackingNumber.eq:"+trackingNumber+"&sortBy=lastModifiedOn&sortOrder=DESC"}, SERVICE_TYPE.LMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TripOrderAssignmentResponse());

    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function printMasterBagInvoice = masterbagId -> HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG,
            new String[]{"invoices?masterBagId="+masterbagId}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseStatus();

    /**
     * getOrderTracking
     *
     * @param orderId
     * @param shipmentType
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public OrderTrackingResponse getOrderTracking(String orderId, String shipmentType)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.ORDER_TRACKING,
                new String[]{"search?q=orderId.eq:" + orderId + "___shipmentType.eq:" + shipmentType}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        OrderTrackingResponse response = (OrderTrackingResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderTrackingResponse());
        return response;
    }

    /**
     * reassignCourierOnLMS :  This method is used to reassign courier of a shipment from LMS. Order should be in PK or IS state.
     * Object releaseId
     * Object toCourier
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public LambdaInterfaces.BiFunction reassignCourierOnLMS = (releaseId, toCourier) -> {
        OrderResponse orderResponse = (OrderResponse) getOrderLMS.apply(releaseId);
        OrderTrackingResponse orderTrackingResponse = getOrderTracking(releaseId.toString(), orderResponse.getOrders().get(0).getShipmentType().toString());
        String trackingNumber = getTrackingNumber(toCourier.toString(), orderResponse.getOrders().get(0).getWarehouseId(), orderResponse.getOrders().get(0).getCod().toString(),
                orderResponse.getOrders().get(0).getZipcode(), orderResponse.getOrders().get(0).getShippingMethod().toString()).getTrackingNumberEntry().getTrackingNumber();
        OrderTrackingEntry orderTracking = new OrderTrackingEntry();
        orderTracking.setCreatedBy("Automation");
        orderTracking.setId(orderTrackingResponse.getOrderTrackings().get(0).getId());
        orderTracking.setOrderId(releaseId.toString());
        orderTracking.setCourierCreationStatus(CourierCreationStatus.ACCEPTED);
        orderTracking.setCourierOperator(toCourier.toString());
        orderTracking.setDeliveryStatus(orderTrackingResponse.getOrderTrackings().get(0).getDeliveryStatus());
        orderTracking.setFailedAttempts(orderTrackingResponse.getOrderTrackings().get(0).getFailedAttempts());
        orderTracking.setShipmentType(orderTrackingResponse.getOrderTrackings().get(0).getShipmentType());
        orderTracking.setTrackingNumber(trackingNumber);
        String payload = APIUtilities.convertXMLObjectToString(orderTracking);
        return APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.ORDER_TRACKING, new String[]{orderTrackingResponse.getOrderTrackings().get(0).getId().toString()},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getBasicHeaderXML()).getResponseBody(), new OrderTrackingResponse());
    };

    /**
     * pinCodeServiceable
     *
     * @param courierCode
     * @param pincode
     * @param pymentMode
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public PincodeResponse pinCodeServiceable(String courierCode, String pincode, String pymentMode)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PINCODE_SERVICEABLE,
                new String[]{courierCode, pincode, pymentMode}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        PincodeResponse response = (PincodeResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new PincodeResponse());
        return response;
    }

    /**
     * networkSearch
     *
     * @param sourceId
     * @param destId
     * @param sourceType
     * @param destType
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public NetworkResponse networkSearch(String sourceId, String destId, String sourceType, String destType)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.NETWORK,
                new String[]{"search?q=srcId.eq:" + sourceId + "___sourcePremisesType.eq:" + sourceType + "___destId.eq:" + destId + "___destinationPremisesType.eq:" + destType + "___courierCode.eq:ML"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        NetworkResponse response = (NetworkResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new NetworkResponse());
        return response;
    }

    /**
     * bulkUpdateOrderTrackiing
     *
     * @param orderId
     * @param trackingNumber
     * @param activityType
     * @param extTrackingCode
     * @param remark
     * @param location
     * @param courierOperator
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws ParseException
     */
    public OrderTrackingResponse bulkUpdateOrderTrackiing(String orderId, String trackingNumber, String activityType,
                                                          String extTrackingCode, String remark, String location, String courierOperator)
            throws  IOException, JAXBException, ParseException {
        OrderTrackingResponse orderTrackingResponse1 = new OrderTrackingResponse();
        OrderTrackingDetailEntry orderTrackingDetailEntry = new OrderTrackingDetailEntry();
        OrderTrackingEntry orderTrackingEntry = new OrderTrackingEntry();
        orderTrackingDetailEntry.setActivityType(activityType);
        orderTrackingDetailEntry.setExtTrackingCode(extTrackingCode);
        orderTrackingDetailEntry.setRemark(remark);
        orderTrackingDetailEntry.setLocation(location);
        orderTrackingDetailEntry.setActionDate(new Date());
        orderTrackingEntry.setOrderId(orderId);
        orderTrackingEntry.setCourierOperator(courierOperator);
        orderTrackingEntry.setTrackingNumber(trackingNumber);
        List<OrderTrackingDetailEntry> orderTrackingDetailEntries = new ArrayList<>();
        orderTrackingDetailEntries.add(orderTrackingDetailEntry);
        orderTrackingEntry.setOrderTrackingDetailEntry(orderTrackingDetailEntries);
        List<OrderTrackingEntry> orderTrackingEntries = new ArrayList<>();
        orderTrackingEntries.add(orderTrackingEntry);
        orderTrackingResponse1.setOrderTrackings(orderTrackingEntries);
        String payload = APIUtilities.convertXMLObjectToString(orderTrackingResponse1);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.BULK_UPLOAD, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        OrderTrackingResponse response = (OrderTrackingResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new OrderTrackingResponse());
        return response;
    }

    /**
     * bulkUpdateOrderTrackiing
     * @param orderId
     * @param activityType
     * @return
     * @throws IOException
     * @throws JAXBException
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
	public OrderTrackingResponse bulkUpdateOrderTrackiing(String orderId, String activityType)
            throws IOException, JAXBException, ParseException, InterruptedException, ManagerException, JSONException, XMLStreamException {
        OrderEntry order = ((OrderResponse) getOrderLMS.apply(orderId)).getOrders().get(0);
        OrderTrackingResponse orderTrackingResponse1 = new OrderTrackingResponse();
        OrderTrackingDetailEntry orderTrackingDetailEntry = new OrderTrackingDetailEntry();
        OrderTrackingEntry orderTrackingEntry = new OrderTrackingEntry();
        orderTrackingDetailEntry.setActivityType(activityType);
        orderTrackingDetailEntry.setRemark("Test");
        orderTrackingDetailEntry.setLocation("Electronic city DC");
        orderTrackingDetailEntry.setActionDate(new Date());
        orderTrackingEntry.setOrderId(orderId);
        orderTrackingEntry.setCourierOperator(order.getCourierOperator());
        orderTrackingEntry.setTrackingNumber(order.getTrackingNumber());
        List<OrderTrackingDetailEntry> orderTrackingDetailEntries = new ArrayList<>();
        orderTrackingDetailEntries.add(orderTrackingDetailEntry);
        orderTrackingEntry.setOrderTrackingDetailEntry(orderTrackingDetailEntries);
        List<OrderTrackingEntry> orderTrackingEntries = new ArrayList<>();
        orderTrackingEntries.add(orderTrackingEntry);
        orderTrackingResponse1.setOrderTrackings(orderTrackingEntries);
        String payload = APIUtilities.convertXMLObjectToString(orderTrackingResponse1);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.BULK_UPLOAD, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        OrderTrackingResponse response = (OrderTrackingResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new OrderTrackingResponse());
        return response;
    }

    /**
     * handoverToRegionalCourier
     *
     * @param shipmentId
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public ShipmentResponse handoverToRegionalCourier(long shipmentId)
            throws  IOException, JAXBException {
        ShipmentResponse shipmentResponse = new ShipmentResponse();
        ShipmentEntry shipmentEntry = new ShipmentEntry();
        shipmentEntry.setId(shipmentId);
        List<ShipmentEntry> shipmentEntries = new ArrayList<>();
        shipmentEntries.add(shipmentEntry);
        shipmentResponse.setEntries(shipmentEntries);

        String payload = APIUtilities.convertXMLObjectToString(shipmentResponse);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.HANDOVER_TO_RHC, null,
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ShipmentResponse response = (ShipmentResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new ShipmentResponse());
        return response;
    }

    /**
     * getMasterBag
     * @param masterbagId
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getMasterBag =  masterbagId -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, new String[]{masterbagId + "?um=true"},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(),
                new ShipmentResponse());

    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getMasterBagViaSearch =  masterbagId -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, new String[]{"search?q=id.eq:"+masterbagId},
            SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(),
            new ShipmentResponse());

    /**
     * getMasterBagWithParam
     *
     * @param pathParam
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public ShipmentResponse getMasterBagWithParam(String pathParam)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, new String[]{pathParam},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        ShipmentResponse response = (ShipmentResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new ShipmentResponse());
        return response;
    }

    /**
     * getOrderShipmentAssociations
     * Object orderId
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getOrderShipmentAssociations = orderId -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG,
            new String[]{"search?q=orderShipmentAssociations.order.orderId.eq:"+orderId+"&start=0&fetchSize=-1&sortBy=&sortOrder=&um=true"},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new ShipmentResponse());

    /**
     * getCorrectionEvents
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.BiFunction getCorrectionEvents = (orderId,shipmentType) -> HttpExecutorService.executeHttpService(Constants.LMS_PATH.PLATEFORM_ML,
            new String[]{"events?shipmentId="+orderId+"&trackingNumber=&shipmentType="+shipmentType+"&courierCode="},
            SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderJSON()).getResponseBody();

    /**
     * createMasterBagHubToDC
     * @param hubId
     * @param dcId
     * @param shippingMethod
     * @return
     * @throws IOException
     * @throws JAXBException
     */

	public ShipmentResponse createMasterBagHubToDC(long hubId, long dcId, ShippingMethod shippingMethod)
            throws  IOException, JAXBException {
        ShipmentEntry shipment = new ShipmentEntry();
        shipment.setOriginPremisesId(hubId);
        shipment.setOriginPremisesType(PremisesType.HUB);
        shipment.setOriginCity("Bangalore");
        shipment.setDestinationPremisesId(dcId);
        shipment.setDestinationPremisesType(PremisesType.DC);
        shipment.setDestinationCity("Delhi");
        shipment.setCapacity(20);
        shipment.setStatus(ShipmentStatus.NEW);
        shipment.setShippingMethod(shippingMethod);
        String payload = APIUtilities.convertXMLObjectToString(shipment);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * createMasterBagDCtoHUB
     * @param hubId
     * @param dcId
     * @param shippingMethod
     * @return
     * @throws IOException
     * @throws JAXBException
     */
	public ShipmentResponse createMasterBagDCtoHUB(long dcId, long hubId, ShippingMethod shippingMethod)
            throws  IOException, JAXBException {
        ShipmentEntry shipment = new ShipmentEntry();
        shipment.setOriginPremisesId(dcId);
        shipment.setOriginPremisesType(PremisesType.DC);
        shipment.setOriginCity("Bangalore");
        shipment.setDestinationPremisesId(hubId);
        shipment.setDestinationPremisesType(PremisesType.HUB);
        shipment.setDestinationCity("Delhi");
        shipment.setCapacity(20);
        shipment.setStatus(ShipmentStatus.NEW);
        shipment.setShippingMethod(shippingMethod);
        String payload = APIUtilities.convertXMLObjectToString(shipment);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * createMasterBag
     *
     * @param originPremisesId
     * @param originPremisesType
     * @param originCity
     * @param destinationPremisesId
     * @param destinationPremisesType
     * @param destinationCity
     * @param shippingMethod
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */

	public ShipmentResponse createMasterBag(long originPremisesId, String originPremisesType, String originCity,
                                            long destinationPremisesId, String destinationPremisesType, String destinationCity, String shippingMethod)
            throws  IOException, JAXBException {
        PremisesType typeWH = PremisesType.WH;
        PremisesType typeDC = PremisesType.DC;
        PremisesType typeHUB = PremisesType.HUB;
        PremisesType opt, dpt;
        if (originPremisesType.equals("WH")) {
            opt = typeWH;
        } else if (originPremisesType.equals("DC")) {
            opt = typeDC;
        } else {
            opt = typeHUB;
        }
        if (destinationPremisesType.equals("WH")) {
            dpt = typeWH;
        } else if (destinationPremisesType.equals("DC")) {
            dpt = typeDC;
        } else {
            dpt = typeHUB;
        }
        ShippingMethod shipM = ShippingMethod.NORMAL;
        if (shippingMethod.equals(EnumSCM.NORMAL)) {
            shipM = ShippingMethod.NORMAL;
        } else if (shippingMethod.equals(EnumSCM.SDD)) {
            shipM = ShippingMethod.SDD;
        } else if (shippingMethod.equals(EnumSCM.EXPRESS)) {
            shipM = ShippingMethod.EXPRESS;
        } else if (shippingMethod.equals("ALL")) {
            shipM = ShippingMethod.ALL;
        }
        ShipmentEntry shipment = new ShipmentEntry();
        shipment.setOriginPremisesId(originPremisesId);
        shipment.setOriginPremisesType(opt);
        shipment.setOriginCity(originCity);
        shipment.setDestinationPremisesId(destinationPremisesId);
        shipment.setDestinationPremisesType(dpt);
        shipment.setDestinationCity(destinationCity);
        shipment.setCapacity(20);
        shipment.setStatus(ShipmentStatus.NEW);
        shipment.setShippingMethod(shipM);
        String payload = APIUtilities.convertXMLObjectToString(shipment);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * createMasterBag
     *
     * @param originPremisesId
     * @param originPremisesType
     * @param destinationPremisesId
     * @param destinationPremisesType
     * @param shippingMethod
     * @param courierCode
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings({ "unused", "unchecked" })
	public ShipmentResponse createMasterBag(long originPremisesId, String originPremisesType,
                                            long destinationPremisesId, String destinationPremisesType, String shippingMethod, String courierCode)
            throws  IOException, JAXBException {
        long origin = originPremisesId;
        long dest = destinationPremisesId;
        PremisesType typeWH = PremisesType.WH;
        PremisesType typeDC = PremisesType.DC;
        PremisesType typeHUB = PremisesType.HUB;
        PremisesType opt, dpt;

        if (originPremisesType.equals("WH")){
            origin = (long)lmsHelper.getDHHubIdForWH.apply(originPremisesId);
        }else origin = originPremisesId;

        if (destinationPremisesType.equals("WH")){
            dest = (long)lmsHelper.getRTHubIdForWH.apply(destinationPremisesId);
        }else dest = destinationPremisesId;

        if (originPremisesType.equals("WH")) {
            opt = typeHUB;
        } else if (originPremisesType.equals("DC")) {
            opt = typeDC;
        } else {
            opt = typeHUB;
        }
        if (destinationPremisesType.equals("WH")) {
            dpt = typeHUB;
        } else if (destinationPremisesType.equals("DC")) {
            dpt = typeDC;
        } else {
            dpt = typeHUB;
        }
        ShippingMethod shipM = ShippingMethod.NORMAL;
        if (shippingMethod.equals(EnumSCM.NORMAL)) {
            shipM = ShippingMethod.NORMAL;
        } else if (shippingMethod.equals(EnumSCM.SDD)) {
            shipM = ShippingMethod.SDD;
        } else if (shippingMethod.equals(EnumSCM.EXPRESS)) {
            shipM = ShippingMethod.EXPRESS;
        } else if (shippingMethod.equals("ALL")) {
            shipM = ShippingMethod.ALL;
        }
        ShipmentEntry shipment = new ShipmentEntry();
        shipment.setOriginPremisesId(origin);
        shipment.setOriginPremisesType(opt);
        shipment.setOriginCity("Bangalore");
        shipment.setDestinationPremisesId(dest);
        shipment.setDestinationPremisesType(dpt);
        shipment.setDestinationCity("Bangalore");
        shipment.setCapacity(20);
        shipment.setStatus(ShipmentStatus.NEW);
        shipment.setShippingMethod(shipM);
        shipment.setCourier(courierCode);
        String payload = APIUtilities.convertXMLObjectToString(shipment);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * receiveShipmentFromMasterbag
     * @param shipmentId
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
	public ShipmentResponse receiveShipmentFromMasterbag(long shipmentId)
            throws IOException, JAXBException, InterruptedException, ManagerException, JSONException, XMLStreamException {
        ShipmentEntry masterbag = ((ShipmentResponse) getMasterBag.apply(shipmentId)).getEntries().get(0);
        ShipmentEntry shipmentEntry = new ShipmentEntry();

        List<OrderShipmentAssociationEntry> orderShipmentAssociationEntries = new ArrayList<>();
        shipmentEntry.setId(shipmentId);
        if (masterbag != null && masterbag.getCourier() != null && masterbag.getCourier().equals("ML"))
            shipmentEntry.setStatus(ShipmentStatus.RECEIVED);
        else
            shipmentEntry.setStatus(ShipmentStatus.RECEIVED_AT_HANDOVER_CENTER);
        shipmentEntry.setLastScannedCity(masterbag.getLastScannedCity());
        shipmentEntry.setLastScannedPremisesId(masterbag.getLastScannedPremisesId());
        shipmentEntry.setLastScannedPremisesType(masterbag.getLastScannedPremisesType());
        shipmentEntry.setLastScannedOn(new Date());
        shipmentEntry.setArrivedOn(new Date());
        masterbag.getOrderShipmentAssociationEntries()
                .forEach(order->{
                    OrderShipmentAssociationEntry osae = new OrderShipmentAssociationEntry();
                    osae.setOrderId(order.getOrderId());
                    osae.setShipmentId(shipmentId);
                    if (masterbag != null && masterbag.getCourier() != null && masterbag.getCourier().equals("ML"))
                        osae.setStatus(OrderShipmentAssociationStatus.RECEIVED);
                    else
                        osae.setStatus(OrderShipmentAssociationStatus.RECEIVED_AT_HANDOVER_CENTER);
                    orderShipmentAssociationEntries.add(osae);
                });
        shipmentEntry.setOrderShipmentAssociationEntries(orderShipmentAssociationEntries);
        String payload = APIUtilities.convertXMLObjectToString(shipmentEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.RECEIVE_SHIPMENT_IN_DC,
                new String[]{Long.toString(shipmentId)}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload,
                Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }
    public ShipmentResponse receiveReturnShipmentFromMasterbag(long shipmentId,String returnId)
            throws IOException, JAXBException, InterruptedException, ManagerException, JSONException, XMLStreamException {
        ShipmentEntry masterbag = ((ShipmentResponse) getMasterBag.apply(shipmentId)).getEntries().get(0);
        ShipmentEntry shipmentEntry = new ShipmentEntry();
        
        List<OrderShipmentAssociationEntry> orderShipmentAssociationEntries = new ArrayList<>();
        shipmentEntry.setId(shipmentId);
            shipmentEntry.setStatus(ShipmentStatus.RECEIVED_AT_HANDOVER_CENTER);
        shipmentEntry.setLastScannedCity(masterbag.getLastScannedCity());
        shipmentEntry.setLastScannedPremisesId(masterbag.getLastScannedPremisesId());
        shipmentEntry.setLastScannedPremisesType(masterbag.getLastScannedPremisesType());
        shipmentEntry.setLastScannedOn(new Date());
        shipmentEntry.setArrivedOn(new Date());
        masterbag.getOrderShipmentAssociationEntries()
                .forEach(order->{
                    OrderShipmentAssociationEntry osae = new OrderShipmentAssociationEntry();
                    osae.setOrderId(order.getOrderId());
                    osae.setSourceReturnId(returnId);
                    osae.setShipmentId(shipmentId);
                        osae.setStatus(OrderShipmentAssociationStatus.RECEIVED);
                    
                    orderShipmentAssociationEntries.add(osae);
                });
        shipmentEntry.setOrderShipmentAssociationEntries(orderShipmentAssociationEntries);
        String payload = APIUtilities.convertXMLObjectToString(shipmentEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.RECEIVE_SHIPMENT_IN_DC,
                new String[]{Long.toString(shipmentId)}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload,
                Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }
    /**
     * updateMasterBag
     * @param pathParam
     * @param originPremisesId
     * @param originPremisesType
     * @param originCity
     * @param destinationPremisesId
     * @param destinationPremisesType
     * @param destinationCity
     * @param shippingMethod
     * @param status
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
   
	public ShipmentResponse updateMasterBag(String pathParam, long originPremisesId, String originPremisesType,
                                            String originCity, long destinationPremisesId, String destinationPremisesType, String destinationCity,
                                            String shippingMethod, String status)
            throws IOException, JAXBException {
        PremisesType typeWH = PremisesType.WH;
        PremisesType typeDC = PremisesType.DC;
        PremisesType typeHUB = PremisesType.HUB;
        PremisesType opt, dpt;
        if (originPremisesType.equals("WH")) {
            opt = typeWH;
        } else if (originPremisesType.equals("DC")) {
            opt = typeDC;
        } else {
            opt = typeHUB;
        }
        if (destinationPremisesType.equals("WH")) {
            dpt = typeWH;
        } else if (destinationPremisesType.equals("DC")) {
            dpt = typeDC;
        } else {
            dpt = typeHUB;
        }
        ShippingMethod shipM = ShippingMethod.NORMAL;
        if (shippingMethod.equals(EnumSCM.NORMAL)) {
            shipM = ShippingMethod.NORMAL;
        } else if (shippingMethod.equals(EnumSCM.SDD)) {
            shipM = ShippingMethod.SDD;
        } else if (shippingMethod.equals(EnumSCM.EXPRESS)) {
            shipM = ShippingMethod.EXPRESS;
        } else if (shippingMethod.equals("ALL")) {
            shipM = ShippingMethod.ALL;
        }
        ShipmentStatus shipmentStatus = ShipmentStatus.NEW;
        if (status.equals(EnumSCM.IN_TRANSIT)) {
            shipmentStatus = ShipmentStatus.IN_TRANSIT;
        } else if (status.equals(EnumSCM.HANDED_OVER_TO_3PL)) {
            shipmentStatus = ShipmentStatus.HANDED_OVER_TO_3PL;
        } else if (status.equals(EnumSCM.RECEIVED)) {
            shipmentStatus = ShipmentStatus.RECEIVED;
        }
        ShipmentEntry shipment = new ShipmentEntry();
        shipment.setOriginPremisesId(originPremisesId);
        shipment.setOriginPremisesType(opt);
        shipment.setOriginCity(originCity);
        shipment.setDestinationPremisesId(destinationPremisesId);
        shipment.setDestinationPremisesType(dpt);
        shipment.setDestinationCity(destinationCity);
        shipment.setCapacity(20);
        shipment.setStatus(shipmentStatus);
        shipment.setShippingMethod(shipM);
        String payload = APIUtilities.convertXMLObjectToString(shipment);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, new String[]{pathParam},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }


    /**
     *
     * @param masterBagId
     * @param status
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public ShipmentResponse updateMasterBag(String masterBagId,ShipmentStatus status)
            throws IOException, JAXBException {

        ShipmentEntry shipment = new ShipmentEntry();
        shipment.setStatus(status);
        String payload = APIUtilities.convertXMLObjectToString(shipment);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, new String[]{masterBagId},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * markMasterBagLost
     * @param masterBagId
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public String markMasterBagLost(String masterBagId) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTERBAG_NEW, new String[]{"updateAsLost?masterbagIds[]="+masterBagId},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, null, Headers.getLmsHeaderJSON());
        return APIUtilities.getElement(service.getResponseBody(), "MasterbagResponse.status.statusType", "json");
    }

    /**
     * createMasterBag
     *
     * @param pincode
     * @param wareHouseID
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings({ "unchecked" })
	public ShipmentResponse createMasterBag(String pincode, String wareHouseID, ShippingMethod shippingMethod, String delCenterId)
            throws IOException, JAXBException {
        ShipmentEntry shipment = new ShipmentEntry();
        shipment.setOriginPremisesId((long)lmsHelper.getDHHubIdForWH.apply(wareHouseID));
        shipment.setOriginPremisesType(PremisesType.HUB);
        shipment.setOriginCity("Bangalore");
        shipment.setDestinationPremisesId(Long.parseLong(delCenterId));
        shipment.setDestinationPremisesType(PremisesType.DC);
        shipment.setDestinationCity("Bangalore");
        shipment.setCapacity(20);
        shipment.setStatus(ShipmentStatus.NEW);
        shipment.setShippingMethod(shippingMethod);
        String payload = APIUtilities.convertXMLObjectToString(shipment);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * createMasterBag
     *
     * @param dcId
     * @param wareHouseID
     * @param shippingMethod
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings({ "unchecked"})
	public ShipmentResponse createMasterBag(Long dcId, String wareHouseID, ShippingMethod shippingMethod)
            throws IOException, JAXBException {
        String destinationCity = "Bangalore";
        ShipmentEntry shipment = new ShipmentEntry();
        shipment.setOriginPremisesId((long)lmsHelper.getDHHubIdForWH.apply(wareHouseID));
        shipment.setOriginPremisesType(PremisesType.HUB);
        shipment.setOriginCity("Bangalore");
        shipment.setDestinationPremisesId(dcId);
        shipment.setDestinationPremisesType(PremisesType.DC);
        shipment.setDestinationCity(destinationCity);
        shipment.setCapacity(20);
        shipment.setStatus(ShipmentStatus.NEW);
        shipment.setShippingMethod(shippingMethod);
        String payload = APIUtilities.convertXMLObjectToString(shipment);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * createMasterBag
     * @param dcId
     * @param wareHouseID
     * @param shippingMethod
     * @param courierCode
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings({ "unchecked"})
	public ShipmentResponse createMasterBag(Long dcId, String wareHouseID, ShippingMethod shippingMethod, String courierCode)
            throws IOException, JAXBException {
    	 
    	    log.info("Creating master bag "); 
        String destinationCity = "Bangalore";
        ShipmentEntry shipment = new ShipmentEntry();
        shipment.setOriginPremisesId((long)lmsHelper.getDHHubIdForWH.apply(wareHouseID));
        shipment.setOriginPremisesType(PremisesType.HUB);
        shipment.setOriginCity("Bangalore");
        shipment.setDestinationPremisesId(dcId);
        shipment.setDestinationPremisesType(PremisesType.DC);
        shipment.setDestinationCity(destinationCity);
        shipment.setCapacity(20);
        shipment.setStatus(ShipmentStatus.NEW);
        shipment.setShippingMethod(shippingMethod);
        shipment.setCourier(courierCode);
        String payload = APIUtilities.convertXMLObjectToString(shipment);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * scanOrderInMasterBag
     *
     * @param orderId
     * @param shipmentId
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public OrderResponse scanOrderInMasterBag(String orderId, String shipmentId)
            throws  IOException, JAXBException {
        String pathParam = "canAddToShipment?orderId=" + orderId + "&shipmentId=" + shipmentId + "&um=true";
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, new String[]{pathParam},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        OrderResponse response = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderResponse());
        return response;
    }

    /**
     * canCancelShipment
     *
     * @param orderReleaseIds
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public ValidTransitionResponse canCancelShipment(List<Object> orderReleaseIds)
            throws  IOException, JAXBException {
        String shipmentIds = "";
        for (Object orderId : orderReleaseIds) {
            shipmentIds = "shipmentId[]=" + orderId.toString() + "&";
        }
        String param = "checkTransitionValid?" + shipmentIds + "event=CANCEL";
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.PLATEFORM_SHIPMENT, new String[]{param},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        ValidTransitionResponse response = (ValidTransitionResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new ValidTransitionResponse());
        return response;
    }

    /**
     * addAndSaveMasterBag
     *
     * @param orderId
     * @param masterbagId
     * @param shipmentType
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public String addAndSaveMasterBag(String orderId, String masterbagId, ShipmentType shipmentType)
            throws IOException, JAXBException {
    	
    	    Map<String, Object> order = DBUtilities.exSelectQueryForSingleRecord("select * from order_to_ship where order_id = '" + orderId+ "' limit 1", "lms");		
		
    	    if(order != null && !order.isEmpty()) {
    	    	
    	    	    log.info("Add packetId "+orderId+" to masterbag "+masterbagId+" and save masterbag");
	    	    String trackingNumber = (String) order.get("tracking_number");
	        ShipmentUpdateInfo shipmentUpdateInfo = new ShipmentUpdateInfo.Builder(orderId, shipmentType, trackingNumber, 0.0,0.0,0.0,0.0, 0.0, null, false).build();
	        String payload = APIUtilities.getObjectToJSON(shipmentUpdateInfo);
	        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTERBAG_NEW, new String[]{masterbagId, "addShipment"},
	                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, payload, Headers.getLmsHeaderJSON());
	        String response = APIUtilities.getElement(service.getResponseBody(), "MasterbagResponse.status.statusType", "json");
          
	         return response;
    	    } else {
    	    	
    	    	     throw new SkipException(EnumSCM.ERROR.toString()+" Order does not exist or Invalid Order id"+order);
    	    }
    }
	/**
	 * addAndSaveMasterBag
	 *
//	 * @param orderId
	 * @param masterbagId
	 * @param shipmentType
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 */
	public String addAndSaveReturnIntoMasterBag(String returnId, String masterbagId, ShipmentType shipmentType)
			throws IOException, JAXBException {
		
		Map<String, Object> return_shipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnId, "lms");
		LMSHelper lmsHelper= new LMSHelper();
		String trackingNo = return_shipment.get("tracking_number").toString();
			
			ShipmentUpdateInfo shipmentUpdateInfo = new ShipmentUpdateInfo.Builder(returnId, shipmentType, trackingNo, 0.0,0.0,0.0,0.0, 0.0, null, false).build();
			String payload = APIUtilities.getObjectToJSON(shipmentUpdateInfo);
			Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTERBAG_NEW, new String[]{masterbagId, "addShipment"},
					SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, payload, Headers.getLmsHeaderJSON());
			String response = APIUtilities.getElement(service.getResponseBody(), "MasterbagResponse.status.statusType", "json");
			return response;
		
	}
	
	/**
	 * addAndSaveMasterBag
	 *
	 * @param orderId
	 * @param masterbagId
	 * @param shipmentType
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 */
	public String addAndSaveMasterBag(String orderId, String masterbagId, ShipmentType shipmentType,String trackingNumber)
			throws IOException, JAXBException {
		
			ShipmentUpdateInfo shipmentUpdateInfo = new ShipmentUpdateInfo.Builder(orderId, shipmentType, trackingNumber, 0.0,0.0,0.0,0.0, 0.0, null, false).build();
			String payload = APIUtilities.getObjectToJSON(shipmentUpdateInfo);
			Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTERBAG_NEW, new String[]{masterbagId, "addShipment"},
					SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, payload, Headers.getLmsHeaderJSON());
			String response = APIUtilities.getElement(service.getResponseBody(), "MasterbagResponse.status.statusType", "json");
   
			return response;
		
	}
	
	
	
	/**
     * addAndSaveMasterBag
     *
     * @param orderId
     * @param masterbagId
     * @param shipmentType
     * @param ignoreWarning
     * @return
     * @throws IOException
     * @throws JAXBException
     */
	public String addAndSaveMasterBag(String orderId, String masterbagId, ShipmentType shipmentType,
			Boolean ignoreWarning) throws IOException, JAXBException {

		Map<String, Object> order = DBUtilities.exSelectQueryForSingleRecord(
				"select * from order_to_ship where order_id = '" + orderId + "' limit 1", "lms");

		if (order != null && !order.isEmpty()) {

			log.info("Order - " + order);
			String trackingNumber = (String) order.get("tracking_number");
			ShipmentUpdateInfo shipmentUpdateInfo = new ShipmentUpdateInfo.Builder(orderId, shipmentType, trackingNumber, 0.0,0.0, 0.0, 0.0, 0.0, null, ignoreWarning).build();
			String payload = APIUtilities.getObjectToJSON(shipmentUpdateInfo);
			Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTERBAG_NEW, new String[] { masterbagId, "addShipment" }, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST,payload, Headers.getLmsHeaderJSON());
			String response = APIUtilities.getElement(service.getResponseBody(), "MasterbagResponse.status.statusType", "json");
			return response;

		} else {

			throw new SkipException("trackingNumber is null or empty " + order);
		}
	}

    /**
     * addAndSaveMasterBag
     * @param orderId
     * @param masterbagId
     * @param shipmentType
     * @param ignoreWarning
     * @param dunnmy
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public long addAndSaveMasterBag(String orderId, String masterbagId, ShipmentType shipmentType, Boolean ignoreWarning, String dunnmy)
            throws IOException, JAXBException {
    	
    		Map<String, Object> order = DBUtilities.exSelectQueryForSingleRecord(
				"select * from order_to_ship where order_id = '" + orderId + "' limit 1", "lms");

		if (order != null && !order.isEmpty()) { 
			
			log.info("Order - " + order);
			String trackingNumber = (String) order.get("tracking_number");
	        ShipmentUpdateInfo shipmentUpdateInfo = new ShipmentUpdateInfo.Builder(orderId, shipmentType, trackingNumber,0.0,0.0,0.0,0.0, 0.0, null, ignoreWarning).build();
	        String payload = APIUtilities.getObjectToJSON(shipmentUpdateInfo);
	        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTERBAG_NEW, new String[]{masterbagId, "addShipment"},
	                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, payload, Headers.getLmsHeaderJSON());
	        String response = APIUtilities.getElement(service.getResponseBody(), "MasterbagResponse.status.statusCode", "json");
	        return Long.parseLong(response);
        
		} else {

			throw new SkipException("trackingNumber is null or empty " + order);
		}
    }

    /**
     * removeShipmentFromMasterBag
     *
     * @param orderId
     * @param masterbagId
     * @param shipmentType
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public String removeShipmentFromMasterBag(String orderId, String masterbagId, ShipmentType shipmentType, Boolean ignoreWarning)
            throws IOException, JAXBException {
        ShipmentUpdateInfo shipmentUpdateInfo = new ShipmentUpdateInfo.Builder(orderId, shipmentType, null, 0.0,0.0,0.0,0.0, 0.0, null, ignoreWarning).build();
        String payload = "[" + APIUtilities.getObjectToJSON(shipmentUpdateInfo) + "]";
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTERBAG_NEW, new String[]{masterbagId, "removeShipment"},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderJSON());
        String response = APIUtilities.getElement(service.getResponseBody(), "MasterbagResponse.status.statusType", "json");
        return response;
    }

    /**
     * updateShipmentStatus
     *
     * @param orderId
     * @param trackingNumber
     * @return
     * @throws IOException
     * @throws JAXBException response : shipmentUpdateResponseCode : SUCCESS TRANSITION_NOT_ALLOWED, ERROR_DURING_TRANSITION, SHIPMENT_NOT_FOUND, TRANSITION_NOT_CONFIGURED, OUT_OF_SEQUENCE_EVENT
     */
    public String updateShipmentStatus(String orderId, String trackingNumber, ShipmentUpdateEvent event, ShipmentType shipmentType)
            throws IOException, JAXBException {
        ShipmentUpdate shipmentUpdate = new ShipmentUpdate.ShipmentUpdateBuilder(orderId.toString(), event).shipmentType(shipmentType).eventLocation("eventLocation").
                eventTime(new DateTime()).userName("test").shipmentUpdateActivitySource(ShipmentUpdateActivityTypeSource.LogisticsPlatform).remarks("test").build();
        String payload = APIUtilities.getObjectToJSON(shipmentUpdate);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.UPDATE_SHIPMENT_STATUS, null,
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, payload, Headers.getLmsHeaderJSON());
        return service.getResponseBody();
    }

    /**
     * newAdminUpdateShipmentStatus
     * @param orderId
     * @param trackingNumber
     * @param event
     * @param shipmentType
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public String newAdminUpdateShipmentStatus(String orderId, String trackingNumber, String event, ShipmentType shipmentType, ShipmentUpdateActivityTypeSource updateSource)
            throws IOException, JAXBException {
        PlatformMLSharedEntry entry = new PlatformMLSharedEntry();
        entry.setShipmentId(orderId);
        entry.setTrackingNumber(trackingNumber);
        entry.setShipmentType(shipmentType);
        entry.setEvent(event);
        entry.setEventLocation("Myntra");
        entry.setEventTime(new DateTime());
        entry.setRemarks("admin update");
        entry.setShipmentUpdateMode(updateSource);
        String payload = APIUtilities.getObjectToJSON(entry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.NEW_ADMIN_UPDATE_SHIPMENT, null,
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, payload, Headers.getLmsHeaderJSON());
        return service.getResponseBody();
    }

    /**
     * cancelShipmentInLMS
     *
     * @param orderId
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
	public String cancelShipmentInLMS(String orderId) throws IOException, JAXBException, InterruptedException, ManagerException, JSONException, XMLStreamException {
        OrderEntry order = ((OrderResponse)getOrderLMS.apply(orderId)).getOrders().get(0);
        ShipmentType shipmentType = ShipmentType.DL;
        if (order.getShipmentType().toString().equals(EnumSCM.TRY_AND_BUY)) {
            shipmentType = ShipmentType.TRY_AND_BUY;
        }
        String response = updateShipmentStatus(orderId, order.getTrackingNumber(), ShipmentUpdateEvent.CANCEL, shipmentType);
//		return response.getStatus().getStatusType().toString();
        return APIUtilities.getElement(response, "ShipmentUpdateResponse.shipmentUpdateResponseCode", "json");
    }

    /**
     * saveMasterBagDL
     *
     * @param masterBagId
     * @param orderId
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public ShipmentResponse saveMasterBagDL(long masterBagId, String orderId)
            throws IOException, JAXBException {
        ShipmentEntry shipmentEntry = new ShipmentEntry();
        OrderShipmentAssociationEntry orderShipmentAssociationEntry = new OrderShipmentAssociationEntry();
        shipmentEntry.setId(masterBagId);
        orderShipmentAssociationEntry.setShipmentId(masterBagId);
        orderShipmentAssociationEntry.setOrderId(orderId);
        orderShipmentAssociationEntry.setShipmentWeight("DISABLED");
        orderShipmentAssociationEntry.setPickupLineId(90272431L);
        List<OrderShipmentAssociationEntry> orderShipmentAssociationEntries = new ArrayList<>();
        orderShipmentAssociationEntries.add(orderShipmentAssociationEntry);
        shipmentEntry.setOrderShipmentAssociationEntries(orderShipmentAssociationEntries);
        String payload = APIUtilities.convertXMLObjectToString(shipmentEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.SAVE_MASTER_BAG, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * saveMasterBagReturns (Ship back to configured warehosue)
     *
     * @param masterBagId
     * @param returnId
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public ShipmentResponse saveMasterBagRT(long masterBagId, String returnId)
            throws IOException, JAXBException {
        ShipmentEntry shipmentEntry = new ShipmentEntry();
        OrderShipmentAssociationEntry orderShipmentAssociationEntry = new OrderShipmentAssociationEntry();
        shipmentEntry.setId(masterBagId);
        orderShipmentAssociationEntry.setShipmentId(masterBagId);
        orderShipmentAssociationEntry.setSourceReturnId(""+returnId);
        orderShipmentAssociationEntry.setShipmentWeight("DISABLED");
        orderShipmentAssociationEntry.setPickupLineId(90272431L);
        orderShipmentAssociationEntry.setOrderId(lmsHelper.getOrderIdFromReturnId(returnId));
        List<OrderShipmentAssociationEntry> orderShipmentAssociationEntries = new ArrayList<>();
        orderShipmentAssociationEntries.add(orderShipmentAssociationEntry);
        shipmentEntry.setOrderShipmentAssociationEntries(orderShipmentAssociationEntries);
        String payload = APIUtilities.convertXMLObjectToString(shipmentEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.SAVE_MASTER_BAG, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * scanAndSaveMasterBagWithMultiOrders
     *
     * @param masterBagId
     * @param orders
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public void scanAndSaveMasterBagWithMultiOrders(long masterBagId, List<String> orders) throws UnsupportedEncodingException, JAXBException {
        for (String orderId : orders) {
            String pathParam = "canAddToShipment?orderId=" + orderId + "&shipmentId=" + masterBagId + "&um=true";
            Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, new String[]{pathParam},
                    SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML());
            OrderResponse response = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                    new OrderResponse());
            Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        }
        ShipmentEntry shipmentEntry = new ShipmentEntry();
        shipmentEntry.setId(masterBagId);
        List<OrderShipmentAssociationEntry> orderShipmentAssociationEntries = new ArrayList<>();
        for (String orderId : orders) {
            OrderShipmentAssociationEntry orderShipmentAssociationEntry = new OrderShipmentAssociationEntry();
            orderShipmentAssociationEntry.setShipmentId(masterBagId);
            orderShipmentAssociationEntry.setOrderId(orderId);
            orderShipmentAssociationEntry.setShipmentWeight("DISABLED");
            orderShipmentAssociationEntry.setPickupLineId(90272431L);
            orderShipmentAssociationEntries.add(orderShipmentAssociationEntry);
        }
        shipmentEntry.setOrderShipmentAssociationEntries(orderShipmentAssociationEntries);
        String payload = APIUtilities.convertXMLObjectToString(shipmentEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.SAVE_MASTER_BAG, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        Assert.assertEquals(shipmentResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
    }

    /**
     * closeMasterBag
     *
     * @param id
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public ShipmentResponse closeMasterBag(long id)
            throws  IOException, JAXBException {
    	
    		log.info("Closing master bag "+id);
        ShipmentEntry shipmentEntry = new ShipmentEntry();
        ShipmentResponse shipmentResponse1 = new ShipmentResponse();
        shipmentEntry.setId(id);
        List<ShipmentEntry> shipmentEntries = new ArrayList<>();
        shipmentEntries.add(shipmentEntry);
        shipmentResponse1.setEntries(shipmentEntries);
        String payload = APIUtilities.convertXMLObjectToString(shipmentResponse1);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.CLOSE_MASTER_BAG, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * shipMasterBag
     *
     * @param id
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public ShipmentResponse shipMasterBag(long id) throws  IOException, JAXBException {
    	
        ShipmentEntry shipmentEntry = new ShipmentEntry();
        ShipmentResponse shipmentResponse1 = new ShipmentResponse();
        shipmentEntry.setId(id);
        List<ShipmentEntry> shipmentEntries = new ArrayList<>();
        shipmentEntries.add(shipmentEntry);
        shipmentResponse1.setEntries(shipmentEntries);
        String payload = APIUtilities.convertXMLObjectToString(shipmentResponse1);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.SHIP_MASTER_BAG, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * reopenMasterBag
     * @param id
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public ShipmentResponse reopenMasterBag(long id)
            throws  IOException, JAXBException {
        ShipmentEntry shipmentEntry = new ShipmentEntry();
        ShipmentResponse shipmentResponse1 = new ShipmentResponse();
        shipmentEntry.setId(id);
        List<ShipmentEntry> shipmentEntries = new ArrayList<>();
        shipmentEntries.add(shipmentEntry);
        shipmentResponse1.setEntries(shipmentEntries);
        String payload = APIUtilities.convertXMLObjectToString(shipmentResponse1);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, new String[]{"reopen"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }


    /**
     * shipMasterBag
     * @param id
     * @param transporterId
     * @return
     * @throws Exception
     */
    public ShipmentResponse shipMasterBag(long id, long transporterId)
            throws JAXBException, UnsupportedEncodingException {
        ShipmentEntry shipmentEntry = new ShipmentEntry();
        ShipmentResponse shipmentResponse1 = new ShipmentResponse();
        shipmentEntry.setId(id);
        shipmentEntry.setTransporterId(transporterId);
        List<ShipmentEntry> shipmentEntries = new ArrayList<>();
        shipmentEntries.add(shipmentEntry);
        shipmentResponse1.setEntries(shipmentEntries);
        String payload = APIUtilities.convertXMLObjectToString(shipmentResponse1);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.SHIP_MASTER_BAG, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * scanMasterBagInTransportHub
     * @param id
     * @return
     * @throws UnsupportedEncodingException
     */
    public String scanMasterBagInTransportHub(long id)
            throws UnsupportedEncodingException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTERBAG_NEW, new String[]{""+id, "receiveAtTransportHub"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.PUT, null, Headers.getOMSHeaderJSONOnlyAccept());
        APIUtilities.validateResponse("json", service.getResponseBody(), "MasterbagResponse.status.statusType=='SUCCESS'");
        return EnumSCM.SUCCESS;
    }

    /**
     * dcToDcTransferMB
     * Object masterBagid
     */
    @SuppressWarnings("rawtypes")
	public LambdaInterfaces.Function dcToDcTransferMB = masterBagid -> {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTERBAG_NEW, new String[]{"dcToDcTransfer?masterbagIds[]=" + masterBagid}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.PUT, null, Headers.getLmsHeaderJSON());
        String response = APIUtilities.getElement(service.getResponseBody(), "MasterbagResponse.status.statusType", "json");
        return  response;
    };

    /**
     * scanMasterBagInTransportHubV1
     * @param id
     * @throws UnsupportedEncodingException
     * @throws ManagerException
     */
    public void scanMasterBagInTransportHubV1(long id) throws UnsupportedEncodingException, ManagerException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTERBAG_NEW, new String[]{""+id, "receiveAtTransportHub"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.PUT, null, Headers.getOMSHeaderJSONOnlyAccept());
        if(!APIUtilities.getElement(service.getResponseBody(), "MasterbagResponse.status.statusType", "json").equals(EnumSCM.SUCCESS)){
            throw new ManagerException(APIUtilities.getElement(service.getResponseBody(), "MasterbagResponse.status.statusType", "json"), Integer.parseInt(APIUtilities.getElement(service.getResponseBody(), "MasterbagResponse.status.statusCode", "json")));
        }
    }

    /**
     * markReleaseShipped
     *
     * @param releaseId
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public OrderReleaseResponse markReleaseShipped(long releaseId)
            throws  IOException, JAXBException {
        ReleaseUpdateEntry releaseUpdateEntry = new ReleaseUpdateEntry();
        releaseUpdateEntry.setVersion(0L);
        releaseUpdateEntry.setCod(false);
        releaseUpdateEntry.setCodAmount(0.0);
        releaseUpdateEntry.setReleaseId(releaseId);
        releaseUpdateEntry.setPromiseDate(new Date());
        releaseUpdateEntry.setUpdatedOn(new Date());
        String payload = APIUtilities.convertXMLObjectToString(releaseUpdateEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.MARK_RELEASE_SHIP, null, SERVICE_TYPE.OMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        OrderReleaseResponse shipmentResponse = (OrderReleaseResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
        return shipmentResponse;
    }

    /**
     * masterBagInScan
     *
     * @param shipmentId
     * @param deliveryCenterID
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
	public ShipmentResponse masterBagInScan(long shipmentId, long deliveryCenterID)
            throws  IOException, JAXBException {
        PremisesType premisesType = PremisesType.DC;
        ShipmentStatus shipmentStatus = ShipmentStatus.IN_TRANSIT;
        ShipmentEntry shipmentEntry = new ShipmentEntry();
        shipmentEntry.setId(shipmentId);
        shipmentEntry.setStatus(shipmentStatus);
        shipmentEntry.setLastScannedCity("Dummy");
        shipmentEntry.setLastScannedPremisesId(deliveryCenterID);
        shipmentEntry.setLastScannedPremisesType(premisesType);
        shipmentEntry.setLastScannedOn(new Date());
        String payload = APIUtilities.convertXMLObjectToString(shipmentEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG,
                new String[]{Long.toString(shipmentId)}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload,
                Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }


    /**
     * masterBagInScan
     *
     * @param shipmentId
     * @param status
     * @param lastScannedCity
     * @param lastScannedPremisesId
     * @param lastScannedPremisesType
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings({ "unchecked", "unused" })
	public ShipmentResponse masterBagInScan(long shipmentId, String status, String lastScannedCity,
                                            long lastScannedPremisesId, String lastScannedPremisesType)
            throws  IOException, JAXBException {
        long scanned = lastScannedPremisesId;
        if (lastScannedPremisesType.equals("WH")){
            scanned = (long)lmsHelper.getRTHubIdForWH.apply(lastScannedPremisesId);
        }
        PremisesType premisesType = PremisesType.DC;
        if (lastScannedPremisesType.equals("WH")) {
            premisesType = PremisesType.HUB;
        } else if (lastScannedPremisesType.equals("HUB")) {
            premisesType = PremisesType.HUB;
        }
        long inscanAt = lastScannedPremisesId;
        if (lastScannedPremisesType.equals("WH")){
            inscanAt = (long)lmsHelper.getRTHubIdForWH.apply(lastScannedPremisesId);
        }

        ShipmentStatus shipmentStatus = ShipmentStatus.IN_TRANSIT;
        if (status.equals(EnumSCM.RECEIVED)) {
            shipmentStatus = ShipmentStatus.RECEIVED;
        } else if (status.equals(EnumSCM.HANDED_OVER_TO_3PL)) {
            shipmentStatus = ShipmentStatus.HANDED_OVER_TO_3PL;
        } else if (status.equals("NEW")) {
            shipmentStatus = ShipmentStatus.NEW;
        } else if (status.equals(EnumSCM.RECEIVED_AT_HANDOVER_CENTER)) {
            shipmentStatus = ShipmentStatus.RECEIVED_AT_HANDOVER_CENTER;
        }

        ShipmentEntry shipmentEntry = new ShipmentEntry();
        shipmentEntry.setId(shipmentId);
        shipmentEntry.setStatus(ShipmentStatus.IN_TRANSIT);
        shipmentEntry.setLastScannedCity(lastScannedCity);
        shipmentEntry.setLastScannedPremisesId(scanned);
        shipmentEntry.setLastScannedPremisesType(premisesType);
        shipmentEntry.setLastScannedOn(new Date());
        String payload = APIUtilities.convertXMLObjectToString(shipmentEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG,
                new String[]{Long.toString(shipmentId)}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload,
                Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * masterBagInScanUpdate
     *
     * @param id
     * @param orderId
     * @param lastScannedCity
     * @param lastScannedPremisesId
     * @param lastScannedPremisesType
     * @param originPremisesId
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings({"unused", "unchecked" })
	public ShipmentResponse masterBagInScanUpdate(long id, String orderId, String lastScannedCity,
                                                  long lastScannedPremisesId, String lastScannedPremisesType, long originPremisesId)
            throws  IOException, JAXBException {
        PremisesType premisesType = PremisesType.WH;
        if (lastScannedPremisesType.equals("DC")) {
            premisesType = PremisesType.DC;
        } else if (lastScannedPremisesType.equals("HUB")) {
            premisesType = PremisesType.HUB;
        }else if (lastScannedPremisesType.equals("WH")) {
            premisesType = PremisesType.HUB;
        }
        long inscanAt = lastScannedPremisesId;
        if (lastScannedPremisesType.equals("WH")){
            inscanAt = (long)lmsHelper.getRTHubIdForWH.apply(lastScannedPremisesId);
        }
        OrderShipmentAssociationStatus shipmentStatus = OrderShipmentAssociationStatus.RECEIVED;
        ShipmentEntry shipmentEntry = new ShipmentEntry();
        OrderShipmentAssociationEntry orderShipmentAssociationEntry = new OrderShipmentAssociationEntry();
        shipmentEntry.setId(id);
        shipmentEntry.setStatus(ShipmentStatus.RECEIVED);
        shipmentEntry.setLastScannedCity(lastScannedCity);
        shipmentEntry.setLastScannedPremisesId(lastScannedPremisesId);
        shipmentEntry.setLastScannedPremisesType(premisesType);
        shipmentEntry.setArrivedOn(new Date());
        shipmentEntry.setLastScannedOn(new Date());
        shipmentEntry.setCapacity(20);
        orderShipmentAssociationEntry.setOrderId(orderId);
        orderShipmentAssociationEntry.setShipmentId(id);
        orderShipmentAssociationEntry.setStatus(shipmentStatus);
        orderShipmentAssociationEntry.setOriginPremisesId(originPremisesId);

        List<OrderShipmentAssociationEntry> orderShipmentAssociationEntries = new ArrayList<>();
        orderShipmentAssociationEntries.add(orderShipmentAssociationEntry);
        shipmentEntry.setOrderShipmentAssociationEntries(orderShipmentAssociationEntries);
        String payload = APIUtilities.convertXMLObjectToString(shipmentEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, new String[]{Long.toString(id)},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }
    public ShipmentResponse masterBagInScanUpdateReturnNew(long id, String returnId, String lastScannedCity,
                                                  long lastScannedPremisesId, String lastScannedPremisesType, long originPremisesId)
            throws  IOException, JAXBException {
        PremisesType premisesType = PremisesType.WH;
        if (lastScannedPremisesType.equals("DC")) {
            premisesType = PremisesType.DC;
        } else if (lastScannedPremisesType.equals("HUB")) {
            premisesType = PremisesType.HUB;
        }else if (lastScannedPremisesType.equals("WH")) {
            premisesType = PremisesType.HUB;
        }
        long inscanAt = lastScannedPremisesId;
        if (lastScannedPremisesType.equals("WH")){
            inscanAt = (long)lmsHelper.getRTHubIdForWH.apply(lastScannedPremisesId);
        }
        OrderShipmentAssociationStatus shipmentStatus = OrderShipmentAssociationStatus.RECEIVED;
        ShipmentEntry shipmentEntry = new ShipmentEntry();
        OrderShipmentAssociationEntry orderShipmentAssociationEntry = new OrderShipmentAssociationEntry();
        shipmentEntry.setId(id);
        shipmentEntry.setStatus(ShipmentStatus.RECEIVED);
        shipmentEntry.setLastScannedCity(lastScannedCity);
        shipmentEntry.setLastScannedPremisesId(lastScannedPremisesId);
        shipmentEntry.setLastScannedPremisesType(premisesType);
        shipmentEntry.setArrivedOn(new Date());
        shipmentEntry.setLastScannedOn(new Date());
        orderShipmentAssociationEntry.setSourceReturnId(returnId);
        orderShipmentAssociationEntry.setShipmentId(id);
        orderShipmentAssociationEntry.setStatus(shipmentStatus);
        
        List<OrderShipmentAssociationEntry> orderShipmentAssociationEntries = new ArrayList<>();
        orderShipmentAssociationEntries.add(orderShipmentAssociationEntry);
        shipmentEntry.setOrderShipmentAssociationEntries(orderShipmentAssociationEntries);
        String payload = APIUtilities.convertXMLObjectToString(shipmentEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, new String[]{Long.toString(id)},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }
   

    /**
     * masterBagInScanUpdate : can take multiple shipments in one masterbag send String[] as orderId:status
     * @param id
     * @param orderIdStatus
     * @param lastScannedPremisesId
     * @param lastScannedPremisesType
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings({"unchecked" })
	public ShipmentResponse masterBagInScanUpdate(long id, String[] orderIdStatus,
                                                  long lastScannedPremisesId, String lastScannedPremisesType)
            throws  IOException, JAXBException {
        PremisesType premisesType = PremisesType.WH;
        if (lastScannedPremisesType.equals("DC")) {
            premisesType = PremisesType.DC;
        } else if (lastScannedPremisesType.equals("HUB")) {
            premisesType = PremisesType.HUB;
        }else if (lastScannedPremisesType.equals("WH")) {
            premisesType = PremisesType.HUB;
        }
        long inscanAt = lastScannedPremisesId;
        if (lastScannedPremisesType.equals("WH")){
            inscanAt = (long)lmsHelper.getRTHubIdForWH.apply(lastScannedPremisesId);
        }
        ShipmentEntry shipmentEntry = new ShipmentEntry();
        shipmentEntry.setId(id);
        shipmentEntry.setStatus(ShipmentStatus.RECEIVED);
        shipmentEntry.setLastScannedCity("Bangalore");
        shipmentEntry.setLastScannedPremisesId(inscanAt);
        shipmentEntry.setLastScannedPremisesType(premisesType);
        shipmentEntry.setArrivedOn(new Date());
        shipmentEntry.setLastScannedOn(new Date());
        shipmentEntry.setCapacity(20);
        List<OrderShipmentAssociationEntry> orderShipmentAssociationEntries = new ArrayList<>();
        for (String orderNStatus: orderIdStatus) {
            OrderShipmentAssociationEntry orderShipmentAssociationEntry = new OrderShipmentAssociationEntry();
            String[] arr = orderNStatus.split(":");
            OrderShipmentAssociationStatus status = OrderShipmentAssociationStatus.RECEIVED;
            if (arr[1].equals(EnumSCM.SHORTAGE)){
                status = OrderShipmentAssociationStatus.SHORTAGE;
            }else if (arr[1].equals(EnumSCM.RECEIVED_DAMAGED)){
                status = OrderShipmentAssociationStatus.RECEIVED_DAMAGED;
            }
            else if (arr[1].equals(EnumSCM.IN_TRANSIT_WRONG_ROUTE)){
	            status = OrderShipmentAssociationStatus.IN_TRANSIT_WRONG_ROUTE;
            }
	        
            orderShipmentAssociationEntry.setOrderId(arr[0]);
            orderShipmentAssociationEntry.setShipmentId(id);
            orderShipmentAssociationEntry.setStatus(status);
            orderShipmentAssociationEntries.add(orderShipmentAssociationEntry);
        }
        shipmentEntry.setOrderShipmentAssociationEntries(orderShipmentAssociationEntries);
        String payload = APIUtilities.convertXMLObjectToString(shipmentEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, new String[]{Long.toString(id)},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * masterBagInScanUpdateV1
     * @param masteBagId
     * @param lmsOrderEntries
     * @param lastScannedPremisesId
     * @param lastScannedPremisesType
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings({"unlikely-arg-type", "unchecked" })
	public ShipmentResponse masterBagInScanUpdateV1(long masteBagId,LMSOrderEntries lmsOrderEntries,
                                                  long lastScannedPremisesId, PremisesType lastScannedPremisesType)
            throws  IOException, JAXBException {
        long inscanAt;
        OrderShipmentAssociationStatus status;

        if (lastScannedPremisesType.equals("WH")){
            inscanAt = (long)lmsHelper.getRTHubIdForWH.apply(lastScannedPremisesId);
        }else{
            inscanAt = lastScannedPremisesId;
        }

        ShipmentEntry shipmentEntry = new ShipmentEntry();
        shipmentEntry.setId(masteBagId);
        shipmentEntry.setStatus(ShipmentStatus.RECEIVED);
        shipmentEntry.setLastScannedCity("Bangalore");
        shipmentEntry.setLastScannedPremisesId(inscanAt);
        shipmentEntry.setLastScannedPremisesType(lastScannedPremisesType);
        shipmentEntry.setArrivedOn(new Date());
        shipmentEntry.setLastScannedOn(new Date());
        shipmentEntry.setCapacity(20);
        List<OrderShipmentAssociationEntry> orderShipmentAssociationEntries = new ArrayList<>();

        for (LMSOrderEntry orderEntry: lmsOrderEntries.getOrderEntries()) {
            OrderShipmentAssociationEntry orderShipmentAssociationEntry = new OrderShipmentAssociationEntry();

            if (orderEntry.getShipmentStatus().equals(EnumSCM.SHORTAGE)){
                status = OrderShipmentAssociationStatus.SHORTAGE;
            }else if (orderEntry.getShipmentStatus().equals(EnumSCM.RECEIVED_DAMAGED)){
                status = OrderShipmentAssociationStatus.RECEIVED_DAMAGED;
            }else{
                status = OrderShipmentAssociationStatus.RECEIVED;
            }
            orderShipmentAssociationEntry.setOrderId(orderEntry.getOrderID());
            orderShipmentAssociationEntry.setShipmentId(masteBagId);
            orderShipmentAssociationEntry.setStatus(status);
            orderShipmentAssociationEntries.add(orderShipmentAssociationEntry);
        }
        shipmentEntry.setOrderShipmentAssociationEntries(orderShipmentAssociationEntries);
        String payload = APIUtilities.convertXMLObjectToString(shipmentEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, new String[]{Long.toString(masteBagId)},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * masterBagInScanUpdate
     *
     * @param id
     * @param orderId
     * @param lastScannedCity
     * @param lastScannedPremisesId
     * @param lastScannedPremisesType
     * @param originPremisesId
     * @param status
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings({"unchecked" })
	public ShipmentResponse masterBagInScanUpdate(long id, String orderId, String lastScannedCity,
                                                  long lastScannedPremisesId, String lastScannedPremisesType, long originPremisesId, ShipmentStatus status, OrderShipmentAssociationStatus orderShipmentStatus)
            throws  IOException, JAXBException {
        PremisesType premisesType = PremisesType.HUB;
        if (lastScannedPremisesType.equals("DC")) {
            premisesType = PremisesType.DC;
        } else if (lastScannedPremisesType.equals("HUB")) {
            premisesType = PremisesType.HUB;
        }else if (lastScannedPremisesType.equals("WH")) {
            premisesType = PremisesType.HUB;
        }
        long inscanAt = lastScannedPremisesId;
        if (lastScannedPremisesType.equals("WH")){
            inscanAt = (long)lmsHelper.getRTHubIdForWH.apply(lastScannedPremisesId);
        }
        ShipmentEntry shipmentEntry = new ShipmentEntry();
        OrderShipmentAssociationEntry orderShipmentAssociationEntry = new OrderShipmentAssociationEntry();
        shipmentEntry.setId(id);
        shipmentEntry.setStatus(status);
        shipmentEntry.setLastScannedCity(lastScannedCity);
        shipmentEntry.setLastScannedPremisesId(inscanAt);
        shipmentEntry.setLastScannedPremisesType(premisesType);
        shipmentEntry.setArrivedOn(new Date());
        shipmentEntry.setLastScannedOn(new Date());
        shipmentEntry.setCapacity(20);
        orderShipmentAssociationEntry.setOrderId(orderId);
        orderShipmentAssociationEntry.setShipmentId(id);
        orderShipmentAssociationEntry.setStatus(orderShipmentStatus);
        orderShipmentAssociationEntry.setOriginPremisesId(originPremisesId);

        List<OrderShipmentAssociationEntry> orderShipmentAssociationEntries = new ArrayList<>();
        orderShipmentAssociationEntries.add(orderShipmentAssociationEntry);
        shipmentEntry.setOrderShipmentAssociationEntries(orderShipmentAssociationEntries);
        String payload = APIUtilities.convertXMLObjectToString(shipmentEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, new String[]{Long.toString(id)},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * masterBagInScanUpdate
     *
     * @param id
     * @param orderId
     * @param returnId
     * @param lastScannedCity
     * @param lastScannedPremisesId
     * @param lastScannedPremisesType
     * @param originPremisesId
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings({"unchecked" })
	public ShipmentResponse masterBagInScanUpdate(long id, String orderId, String returnId, String lastScannedCity,
                                                  long lastScannedPremisesId, String lastScannedPremisesType, long originPremisesId)
            throws  IOException, JAXBException {
        PremisesType premisesType = PremisesType.HUB;
        if (lastScannedPremisesType.equals("DC")) {
            premisesType = PremisesType.DC;
        } else if (lastScannedPremisesType.equals("HUB")) {
            premisesType = PremisesType.HUB;
        }else if (lastScannedPremisesType.equals("WH")) {
            premisesType = PremisesType.HUB;
        }
        long inscanAt = lastScannedPremisesId;
        if (lastScannedPremisesType.equals("WH")){
            inscanAt = (long)lmsHelper.getRTHubIdForWH.apply(lastScannedPremisesId);
        }
        OrderShipmentAssociationStatus shipmentStatus = OrderShipmentAssociationStatus.RECEIVED;
        ShipmentEntry shipmentEntry = new ShipmentEntry();
        shipmentEntry.setId(id);
        shipmentEntry.setStatus(ShipmentStatus.RECEIVED);
        shipmentEntry.setLastScannedCity(lastScannedCity);
        shipmentEntry.setLastScannedPremisesId(inscanAt);
        shipmentEntry.setLastScannedPremisesType(premisesType);
        shipmentEntry.setArrivedOn(new Date());
        shipmentEntry.setLastScannedOn(new Date());
       
      
        String payload = APIUtilities.convertXMLObjectToString(shipmentEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MASTER_BAG, new String[]{Long.toString(id)},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
        return shipmentResponse;
    }

    /**
     * createTrip
     *
     * @param deliveryCenterId
     * @param deliveryStaffId
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public TripResponse createTrip(long deliveryCenterId, long deliveryStaffId)
            throws  IOException, JAXBException {
        TripEntry tripEntry = new TripEntry();
        tripEntry.setTripDate(new Date());
        tripEntry.setDeliveryCenterId(deliveryCenterId);
        tripEntry.setDeliveryStaffId(deliveryStaffId);
        tripEntry.setCreatedBy("Automation");
        tripEntry.setIsInbound(false);
        String payload = APIUtilities.convertXMLObjectToString(tripEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_CREATE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TripResponse shipmentResponse = (TripResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new TripResponse());
        return shipmentResponse;
    }

    /**
     * assignOrderToTrip
     *
     * @param tripId
     * @param trackingNumber
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public TripOrderAssignmentResponse assignOrderToTrip(long tripId, String trackingNumber)
            throws  IOException, JAXBException {
        TripOrderAssignmentResponse tripOrderAssignmentResponse = new TripOrderAssignmentResponse();
        TripOrderAssignementEntry tripOrderAssignementEntry = new TripOrderAssignementEntry();
        tripOrderAssignementEntry.setTripId(tripId);
        tripOrderAssignementEntry.setTrackingNumber(trackingNumber);
        tripOrderAssignementEntry.setCreatedBy("Automation");
        List<TripOrderAssignementEntry> tripOrderAssignementEntries = new ArrayList<>();
        tripOrderAssignementEntries.add(tripOrderAssignementEntry);
        tripOrderAssignmentResponse.setTripOrderAssignmentEntries(tripOrderAssignementEntries);
        String payload = APIUtilities.convertXMLObjectToString(tripOrderAssignmentResponse);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_ASSIGN_ORDER, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse shipmentResponse = (TripOrderAssignmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TripOrderAssignmentResponse());
        return shipmentResponse;
    }

    /**
     * assignOrderToDC
     */
    @SuppressWarnings("rawtypes")
	public LambdaInterfaces.TriFunction assignOrderToDC = (fromDC,toDC,trackingNumnber)->{
        return APIUtilities.getElement(HttpExecutorService.executeHttpService(Constants.LMS_PATH.ASSIGN_PICKUP_TO_DC, new String[]{fromDC.toString(),toDC.toString()}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, "[\""+trackingNumnber+"\"]".toString(), Headers.getLmsHeaderJSON()).getResponseBody(),"mlShipmentResponse.status.statusType","json");
    };

    /**
     * assignPickupToHLP
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function assignPickupToHLP = trackingNumber -> {
        String payload = "[{\"trackingNumber\":\""+trackingNumber+"\",\"eventTime\":\""+getDateTimeSQL.get()+"\",\"eventAdditionalInfo\":null,\"deliveryCenterId\":\"5\",\"eventLocation\":\"DC-5\"," +
                "\"remarks\":\"ASSIGN_TO_LAST_MILE_PARTNER\",\"tripId\":\"\",\"userName\":null,\"event\":\"ASSIGN_TO_LAST_MILE_PARTNER\",\"shipmentType\":\"PU\",\"shipmentUpdateMode\":\"MyntraLogistics\"" +
                ",\"hlpCourierCode\":\"QA-ML\"}]";
        return APIUtilities.getElement(HttpExecutorService.executeHttpService(Constants.LMS_PATH.ASSIGN_SHIPMENT_TO_HLP, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderJSON()).getResponseBody(),"mlShipmentResponse.status.statusType","json");};

    /**
     * getAllAvailableTripsForDC
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getAllAvailableTripsForDC = dcId-> {
        return  APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_ALL_AVAILABLE_TRIPS, new String[]{""+dcId}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TripResponse());};

    /**
     * getDSRoute
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getDSRoute = dcId-> {
        return  APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_DS_ROUTE, new String[]{""+dcId}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TripResponse());};

    /**
     * getTripsDetailForDate
     */
                @SuppressWarnings("rawtypes")           
    public LambdaInterfaces.Function getTripsDetailForDate = dcId-> {
        return  APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_DETAIL, new String[]{""+dcId,getDateOnly.get().toString()}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TripOrderAssignmentResponse());};

    /**
     * getTripsDetailForTrackingNumber
     * param: TrackingNumber
     */
                @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getTripsDetailForTrackingNumber = trackingNumber-> {
        return  APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_DETAILS_FOR_TRACKINGNO, new String[]{""+trackingNumber}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TripOrderAssignmentResponse());};

    /**
     * getActiveTripForOrder
     */
                @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getActiveTripForOrder = orderId-> {
        return  APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.ACTIVE_TRIP_FOR_ORDER, new String[]{""+orderId}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TripResponse());};

    /**
     * isAutoCardEnabled
     */
                @SuppressWarnings("rawtypes")
    public LambdaInterfaces.BiFunction isAutoCardEnabled = (dcId,dfId)-> {
        return  APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP, new String[]{"autoCardEnabled?dcId="+dcId+"&dfId="+dfId}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TripResponse());};

    /**
     * longCodeUpdateOrder
     */
                @SuppressWarnings("rawtypes")
    public LambdaInterfaces.BiFunction longCodeUpdateOrder = (mobileNo, orderId)-> {
        return  APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP, new String[]{"longCode?Sender=91"+mobileNo+"&Message_Text="+orderId+"&Time_Stamp=2017-04-03"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TripOrderAssignmentResponse());};

    /**
     * updatePaymentType
     */
                @SuppressWarnings("rawtypes")
    public LambdaInterfaces.BiFunction updatePaymentType = (orderId, paymentPOS)-> {
        return  APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.UPDATE_PAYMENT_TYPE, new String[]{""+orderId,""+paymentPOS}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.PUT, null, Headers.getLmsHeaderXML()).getResponseBody(), new TripResponse());};

    /**
     * validateTripOrder
     */
                @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function validateTripOrder = (orderId)-> {
        return  APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.VALIDATE_ORDER, new String[]{""+orderId}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new TripOrderAssignmentResponse());};
    /**
     * startTrip
     *
     * @param tripID
     * @param odometerReading
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public TripOrderAssignmentResponse startTrip(String tripID, String odometerReading)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_START,
                new String[]{tripID, "null/lmsadmin", odometerReading}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST,
                null, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse shipmentResponse = (TripOrderAssignmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TripOrderAssignmentResponse());
        return shipmentResponse;
    }

    /**
     * deleteTrip
     *
     * @param tripID
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public TripResponse deleteTrip(String tripID)
            throws  IOException, JAXBException {
        TripEntry tripEntry = new TripEntry();
        tripEntry.setId(Long.parseLong(tripID));
        tripEntry.setIsDeleted(true);
        String payload = APIUtilities.convertXMLObjectToString(tripEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP,
                new String[]{tripID}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT,
                payload, Headers.getLmsHeaderXML());
        TripResponse response = (TripResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TripResponse());
        return response;
    }

    /**
     * updateOrderInTrip
     *
     * @param tripOrderAssignmentId
     * @param status
     * @param tripAction
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public TripOrderAssignmentResponse updateOrderInTrip(long tripOrderAssignmentId, String status, String tripAction)
            throws  IOException, JAXBException {
        AttemptReasonCode s = AttemptReasonCode.DELIVERED;
        if (status.equals(EnumSCM.LOST)) {
            s = AttemptReasonCode.OTHERS;
        } else if (status.equals(EnumSCM.FAILED)) {
            s = AttemptReasonCode.NOT_REACHABLE_UNAVAILABLE;
        }
        TripAction ta = TripAction.TRIP_COMPLETE;
        if (tripAction.equalsIgnoreCase(EnumSCM.UPDATE)) {
            ta = TripAction.UPDATE;
        } else if (tripAction.equalsIgnoreCase("TRIPSTART")) {
            ta = TripAction.TRIP_START;
        } else if (tripAction.equalsIgnoreCase("MARKRETURNSCAN")) {
            ta = TripAction.MARK_RETURNSCAN;
        } else if (tripAction.equalsIgnoreCase("MARKOUTSCAN")) {
            ta = TripAction.MARK_OUTSCAN;
        }
        TripOrderAssignmentResponse tripOrderAssignmentResponse = new TripOrderAssignmentResponse();
        TripOrderAssignementEntry tripOrderAssignementEntry = new TripOrderAssignementEntry();
        tripOrderAssignementEntry.setId(tripOrderAssignmentId);// assignOrderToTripBulk
        tripOrderAssignementEntry.setRemark("test");
        tripOrderAssignementEntry.setAttemptReasonCode(s);
        tripOrderAssignementEntry.setTripAction(ta);
        tripOrderAssignementEntry.setUpdatedVia(UpdatedVia.WEB);
        tripOrderAssignementEntry.setPaymentType("CASH");
        tripOrderAssignementEntry.setIsOutScanned(true);
        List<TripOrderAssignementEntry> tripOrderAssignementEntries = new ArrayList<>();
        tripOrderAssignementEntries.add(tripOrderAssignementEntry);
        tripOrderAssignmentResponse.setTripOrderAssignmentEntries(tripOrderAssignementEntries);
        String payload = APIUtilities.convertXMLObjectToString(tripOrderAssignmentResponse);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_UPDATE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse shipmentResponse = (TripOrderAssignmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TripOrderAssignmentResponse());
        return shipmentResponse;
    }

    /**
     * updateOrderInTrip
     * @param tripOrderAssignmentId
     * @param reason
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public TripOrderAssignmentResponse updateOrderInTrip(long tripOrderAssignmentId, AttemptReasonCode reason)
            throws  IOException, JAXBException {
        TripAction ta = TripAction.TRIP_COMPLETE;
        TripOrderAssignmentResponse tripOrderAssignmentResponse = new TripOrderAssignmentResponse();
        TripOrderAssignementEntry tripOrderAssignementEntry = new TripOrderAssignementEntry();
        tripOrderAssignementEntry.setId(tripOrderAssignmentId);// assignOrderToTripBulk
        tripOrderAssignementEntry.setRemark("test");
        tripOrderAssignementEntry.setAttemptReasonCode(reason);
        tripOrderAssignementEntry.setTripAction(ta);
        tripOrderAssignementEntry.setUpdatedVia(UpdatedVia.WEB);
        tripOrderAssignementEntry.setPaymentType("CASH");
        tripOrderAssignementEntry.setIsOutScanned(true);
        List<TripOrderAssignementEntry> tripOrderAssignementEntries = new ArrayList<>();
        tripOrderAssignementEntries.add(tripOrderAssignementEntry);
        tripOrderAssignmentResponse.setTripOrderAssignmentEntries(tripOrderAssignementEntries);
        String payload = APIUtilities.convertXMLObjectToString(tripOrderAssignmentResponse);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_UPDATE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse shipmentResponse = (TripOrderAssignmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TripOrderAssignmentResponse());
        return shipmentResponse;
    }

    /**
     * completeTrip
     * @param tripData: List<Map<String, Object>> :: Contains key: trip_order_assignment_id & AttemptReasonCode
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public TripOrderAssignmentResponse completeTrip(List<Map<String, Object>> tripData)
            throws  IOException, JAXBException {

        TripOrderAssignmentResponse tripOrderAssignmentResponse = new TripOrderAssignmentResponse();
        List<TripOrderAssignementEntry> tripOrderAssignementEntries = new ArrayList<>();

        tripData.forEach(data->{
            TripOrderAssignementEntry tripOrderAssignementEntry = new TripOrderAssignementEntry();
            tripOrderAssignementEntry.setId((long)data.get("trip_order_assignment_id"));
            tripOrderAssignementEntry.setRemark("test");
            tripOrderAssignementEntry.setAttemptReasonCode((AttemptReasonCode)data.get("AttemptReasonCode"));
            tripOrderAssignementEntry.setTripAction(TripAction.TRIP_COMPLETE);
            tripOrderAssignementEntry.setUpdatedVia(UpdatedVia.WEB);
            tripOrderAssignementEntry.setPaymentType("CASH");
            tripOrderAssignementEntry.setIsOutScanned(true);
            tripOrderAssignementEntries.add(tripOrderAssignementEntry);
        });

        tripOrderAssignmentResponse.setTripOrderAssignmentEntries(tripOrderAssignementEntries);
        String payload = APIUtilities.convertXMLObjectToString(tripOrderAssignmentResponse);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_UPDATE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse shipmentResponse = (TripOrderAssignmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TripOrderAssignmentResponse());
        return shipmentResponse;
    }

    /**
     * updatePickupInTrip
     *
     * @param tripOrderAssignmentId
     * @param status
     * @param tripAction
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public TripOrderAssignmentResponse updatePickupInTrip(long tripOrderAssignmentId, String status, String tripAction)
            throws  IOException, JAXBException {
//        DeliveryPickupReasonCode reasonCode = DeliveryPickupReasonCode.PICKED_UP_SUCCESSFULLY;
        AttemptReasonCode reasonCode = AttemptReasonCode.PICKED_UP_SUCCESSFULLY;
        if (status.equalsIgnoreCase(EnumSCM.NOT_ABLE_TO_PICKUP)) {
            reasonCode = AttemptReasonCode.CANNOT_PICKUP;
        } else if (status.equalsIgnoreCase(EnumSCM.PICKUP_SUCCESSFUL_QC_PENDING)) {
            reasonCode = AttemptReasonCode.PICKUP_SUCCESSFUL_QC_PENDING;
        } else if (status.equalsIgnoreCase(EnumSCM.RETURNS_CANCELLATION)) {
            reasonCode = AttemptReasonCode.OTHERS;
        } else if (status.equalsIgnoreCase(EnumSCM.RETURN_REJECTED)) {
            reasonCode = AttemptReasonCode.RETURN_QC_FAIL;
        } else if (status.equalsIgnoreCase(EnumSCM.ON_HOLD_DAMAGED_PRODUCT)) {
            reasonCode = AttemptReasonCode.PICKUP_ON_HOLD_DAMAGED_PRODUCT;
        } else if (status.equalsIgnoreCase(EnumSCM.RESCHEDULED_CUSTOMER_NOT_AVAILABLE)) {
            reasonCode = AttemptReasonCode.REQUESTED_RE_SCHEDULE;
        } else if (status.equals(EnumSCM.LOST)) {
            reasonCode = AttemptReasonCode.OTHERS;
        } else if (status.equals(EnumSCM.FAILED)) {
            reasonCode = AttemptReasonCode.REQUESTED_RE_SCHEDULE;
        }
        TripAction ta = TripAction.TRIP_COMPLETE;
        if (tripAction.equalsIgnoreCase(EnumSCM.UPDATE)) {
            ta = TripAction.UPDATE;
        } else if (tripAction.equalsIgnoreCase("TRIPSTART")) {
            ta = TripAction.TRIP_START;
        } else if (tripAction.equalsIgnoreCase("MARKRETURNSCAN")) {
            ta = TripAction.MARK_RETURNSCAN;
        } else if (tripAction.equalsIgnoreCase("MARKOUTSCAN")) {
            ta = TripAction.MARK_OUTSCAN;
        }
        TripOrderAssignmentResponse tripOrderAssignmentResponse = new TripOrderAssignmentResponse();
        TripOrderAssignementEntry tripOrderAssignementEntry = new TripOrderAssignementEntry();
        tripOrderAssignementEntry.setId(tripOrderAssignmentId);// assignOrderToTripBulk
        tripOrderAssignementEntry.setRemark("test");
//        tripOrderAssignementEntry.setPickupReasonCode(reasonCode);
        tripOrderAssignementEntry.setAttemptReasonCode(reasonCode);
        tripOrderAssignementEntry.setTripAction(ta);
        tripOrderAssignementEntry.setUpdatedVia(UpdatedVia.WEB);
        tripOrderAssignementEntry.setPaymentType("CASH");
        tripOrderAssignementEntry.setIsOutScanned(true);
        List<TripOrderAssignementEntry> tripOrderAssignementEntries = new ArrayList<>();
        tripOrderAssignementEntries.add(tripOrderAssignementEntry);
        tripOrderAssignmentResponse.setTripOrderAssignmentEntries(tripOrderAssignementEntries);
        String payload = APIUtilities.convertXMLObjectToString(tripOrderAssignmentResponse);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_UPDATE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse shipmentResponse = (TripOrderAssignmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TripOrderAssignmentResponse());
        return shipmentResponse;
    }

    public String getOrderRelease(String orderId)
            throws JAXBException,  IOException {
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.GET_ORDER_RELEASE, new String[]{orderId},
                SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, Headers.getOmsHeaderXML());
        String reponse = service.getResponseBody().toString();
        return reponse;
    }

    /**
     * ctsShipmentUpdate
     * @param shipmentId
     * @param courierCode
     * @param trackingNo
     * @param event
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public CourierTrackingResponse ctsShipmentUpdate(String shipmentId, String courierCode, String trackingNo, ShipmentUpdateEvent event, ShipmentType shipmentType) throws IOException, JAXBException {
        ShipmentUpdate shipmentUpdate = new ShipmentUpdate.ShipmentUpdateBuilder(shipmentId, courierCode, trackingNo, event).shipmentType(shipmentType).eventLocation("Bangalore").eventTime(new DateTime()).build();
        String payload = APIUtilities.getObjectToJSON(shipmentUpdate);
        return (CourierTrackingResponse) APIUtilities.getJsontoObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.CTS_SHIPMENT_UPDATE, null, SERVICE_TYPE.CTS.toString(),
                HTTPMethods.POST, payload, Headers.getCTSHeaderJSON()).getResponseBody(), new CourierTrackingResponse());
    }

    /**
     * ekartCts
     *
     * @param orderId
     * @param vendor_tracking_id
     * @param shipment_type
     * @param event
     * @param status
     * @param location
     * @param merchant_name
     * @param merchant_code
     * @param seller_id
     * @param Courier_name
     * @param date
     * @return
     * @throws JAXBException
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public CourierTrackingResponse ekartCts(String orderId, String vendor_tracking_id, String shipment_type,
                                            String event, String status, String location, String merchant_name, String merchant_code, String seller_id,
                                            String Courier_name, Date date)
            throws JAXBException,  IOException {
        EkartShipmentUpdateRequest ekartShipmentUpdateRequest = new EkartShipmentUpdateRequest();
        ekartShipmentUpdateRequest.setVendor_tracking_id(vendor_tracking_id);
        ekartShipmentUpdateRequest.setShipment_type(shipment_type);
        ekartShipmentUpdateRequest.setEvent(event);
        ekartShipmentUpdateRequest.setEvent_date(date);
        ekartShipmentUpdateRequest.setMerchant_reference_id(orderId);
        ekartShipmentUpdateRequest.setStatus(status);
        ekartShipmentUpdateRequest.setMerchant_name(merchant_name);
        ekartShipmentUpdateRequest.setMerchant_code(merchant_code);
        ekartShipmentUpdateRequest.setSeller_id(seller_id);
        ekartShipmentUpdateRequest.setCourier_name(Courier_name);
        ekartShipmentUpdateRequest.setLocation(location);
        ekartShipmentUpdateRequest.setReason("Test");
        ekartShipmentUpdateRequest.setRemarks("Test");
        String payload = APIUtilities.getObjectToJSON(ekartShipmentUpdateRequest);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.CTS_EKART_UPDATE, null, SERVICE_TYPE.CTS.toString(),
                HTTPMethods.POST, payload, Headers.getCTSHeaderJSON());
        CourierTrackingResponse reponse = (CourierTrackingResponse) APIUtilities
                .getJsontoObject(service.getResponseBody(), new CourierTrackingResponse());
        return reponse;
    }

    /**
     * EkartCts: Just send orderId and Event to this of an Ekart order to call cts of ekart
     *
     * @param orderId
     * @param event
     * @return
     * @throws Exception
     */
    
    @SuppressWarnings("unchecked")
	public CourierTrackingResponse ekartCts(String orderId, String event)
            throws IOException, InterruptedException, XMLStreamException, ManagerException, JSONException, JAXBException {
        EkartShipmentUpdateRequest ekartShipmentUpdateRequest = new EkartShipmentUpdateRequest();
        OrderEntry order = ((OrderResponse)getOrderLMS.apply(orderId)).getOrders().get(0);

        ekartShipmentUpdateRequest.setVendor_tracking_id(order.getTrackingNumber());
        ekartShipmentUpdateRequest.setShipment_type("OutgoingShipment");
        ekartShipmentUpdateRequest.setEvent(event);
        ekartShipmentUpdateRequest.setEvent_date(new Date());
        ekartShipmentUpdateRequest.setMerchant_reference_id(orderId);
        ekartShipmentUpdateRequest.setStatus(event);
        ekartShipmentUpdateRequest.setMerchant_name("Myntra");
        ekartShipmentUpdateRequest.setMerchant_code("MYN");
        ekartShipmentUpdateRequest.setSeller_id("MYN");
        ekartShipmentUpdateRequest.setCourier_name("flipkartlogistics-cod");
        ekartShipmentUpdateRequest.setLocation("ABC_TRADING_PRIVATE_LIMITED_WHITE_FIELD_BANGALORE");
        ekartShipmentUpdateRequest.setReason("Test");
        ekartShipmentUpdateRequest.setRemarks("Test");
        String payload = APIUtilities.getObjectToJSON(ekartShipmentUpdateRequest);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.CTS_EKART_UPDATE, null, SERVICE_TYPE.CTS.toString(),
                HTTPMethods.POST, payload, Headers.getCTSHeaderJSON());
        CourierTrackingResponse reponse = (CourierTrackingResponse) APIUtilities
                .getJsontoObject(service.getResponseBody(), new CourierTrackingResponse());
        return reponse;
    }

    /**
     * EkartToDL: Process order of EK from SH to DL
     *
     * @param orderId
     * @throws Exception
     */
    public void ekartToDL(String orderId) throws IOException, InterruptedException, JAXBException, ManagerException, JSONException, XMLStreamException {
        Assert.assertEquals(ekartCts("" + orderId, "shipment_created").getStatus().getStatusType().toString(),
                EnumSCM.SUCCESS, "Unable to hit CTS for EKL while processing with shipment_created");
        Assert.assertEquals(ekartCts("" + orderId, "shipment_out_for_delivery").getStatus().getStatusType().toString(),
                EnumSCM.SUCCESS, "Unable to hit CTS for EKL while processing with shipment_out_for_delivery");
        Assert.assertTrue(validateOrderStatusInLMS(orderId, EnumSCM.OUT_FOR_DELIVERY, 10));
        Assert.assertEquals(ekartCts("" + orderId, "shipment_delivered").getStatus().getStatusType().toString(),
                EnumSCM.SUCCESS, "Unable to hit CTS for EKL while processing with shipment_delivered");
        Assert.assertTrue(validateOrderStatusInLMS(orderId, EnumSCM.DELIVERED, 10));
    }

    /**
     * offlineCourierToStatus processing
     * @param orderReleaseId
     * @param Status
     * @throws ParseException
     * @throws JAXBException
     * @throws IOException
     */
    public void offlineCourierToStatus(String orderReleaseId, String Status) throws ParseException, JAXBException, IOException, InterruptedException, ManagerException, JSONException, XMLStreamException {
        Assert.assertEquals(bulkUpdateOrderTrackiing(orderReleaseId, Status).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(validateOrderStatusInLMS(orderReleaseId, EnumSCM.DELIVERED, 10));
    }

    /**
     * markOrderRto
     * @param orderId
     * @param trackingNumber
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public OrderResponse markOrderRto(String orderId, String trackingNumber)
            throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.ORDER_IN_SCAN,
                new String[]{"" + orderId, trackingNumber, EnumSCM.RTO}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, null,
                Headers.getLmsHeaderXML());
        OrderResponse response = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderResponse());
        return response;
    }

    /**
     * generateTrackingNumberManual
     * @param courierCode
     * @param type
     * @param prefix
     * @param numbers
     * @return
     * @throws JAXBException
     * @throws IOException
     */
    public TrackingNumberResponse generateTrackingNumberManual(String courierCode, String type, String prefix, String numbers) throws JAXBException, IOException {
        TrackingNumberGenerationEntry entry = new TrackingNumberGenerationEntry();
        entry.setCourierCode(courierCode);
        entry.setType(type);
        entry.setPrefix(prefix);
        entry.setNumbers(numbers);
        String payload = APIUtilities.convertXMLObjectToString(entry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.GENERATE_TRACKING_NUMBER, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TrackingNumberResponse reponse = (TrackingNumberResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TrackingNumberResponse());
        return reponse;
    }

    /**
     * addTransporter
     * @param transporterNumber
     * @param transporterName
     * @param originWH
     * @param destDC
     * @return
     * @throws Exception
     */
	public TransporterResponse addTransporter(String transporterNumber, String transporterName, long originWH, long destDC) throws UnsupportedEncodingException, JAXBException {
        TransporterEntry transporterEntry = new TransporterEntry();
        transporterEntry.setTransporterNumber(transporterNumber);
        transporterEntry.setTransporterName(transporterName);
        transporterEntry.setTransportMode(TransportMode.ROAD);
        transporterEntry.setDescription("Automation transporter");
        transporterEntry.setContactNo("1234567890");
        transporterEntry.setOriginPremisesId(originWH);
        transporterEntry.setDestinationPremisesId(destDC);
        transporterEntry.setOriginPremisesType(PremisesType.WH);
        transporterEntry.setDestinationPremisesType(PremisesType.DC);
        transporterEntry.setDepartureTime("00:30:00");
        transporterEntry.setTravelDurationInMinutes(180);
        transporterEntry.setActive(true);
        transporterEntry.setCreatedBy("Automation");
        String payload = APIUtilities.convertXMLObjectToString(transporterEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRANSPORTER, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TransporterResponse reponse = (TransporterResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TransporterResponse());
        return  reponse;
    }

    /**
     * addTransporterNetwork
     * @param networkId
     * @param dcId1
     * @param dcId2
     * @return
     * @throws Exception
     */
    public NetworkResponse addTransporterNetwork(long networkId, long dcId1, long dcId2) throws JAXBException, UnsupportedEncodingException {
        NetworkEntry networkEntry = new NetworkEntry();
        networkEntry.setId(networkId);
        List<DeliveryCenterEntry> deliveryCenterEntries = new ArrayList<>();
        DeliveryCenterEntry deliveryCenterEntry = new DeliveryCenterEntry();
        deliveryCenterEntry.setId(dcId1);
        DeliveryCenterEntry deliveryCenterEntry2 = new DeliveryCenterEntry();
        deliveryCenterEntry2.setId(dcId2);
        deliveryCenterEntries.add(deliveryCenterEntry);
        deliveryCenterEntries.add(deliveryCenterEntry2);
        //networkEntry.setDeliveryCenterEntries(deliveryCenterEntries);
        String payload = APIUtilities.convertXMLObjectToString(networkEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.NETWORK, new String[]{""+networkId}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        NetworkResponse reponse = (NetworkResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new NetworkResponse());
        return reponse;
    }

    /**
     * getAllTransporter
     * @param param
     * @return
     * @throws Exception
     */
    public TransporterResponse getAllTransporter(String param) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRANSPORTER, new String[]{param}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        TransporterResponse reponse = (TransporterResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TransporterResponse());
        return  reponse;
    }

    /**
     * markOrderLOSTINTRANSIT
     * @param orderId
     * @throws UnsupportedEncodingException
     * @throws JAXBException                MLShipmentResponse
     */
    public String markOrderLOSTINTRANSIT(String orderId) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.FORCE_UPDATE,
                new String[]{"" + orderId, "LOST/LOST_IN_TRANSIT"}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, null,
                Headers.getLmsAdminHeaderXML());
        Assert.assertEquals(service.getResponseStatus(), 200);
        if (service.getResponseBody().toString().contains(EnumSCM.SUCCESS)) return EnumSCM.SUCCESS;
        else return EnumSCM.ERROR;
    }

    /**
     * markOrderLOSTINDC
     *
     * @param orderId
     * @throws UnsupportedEncodingException
     * @throws JAXBException                MLShipmentResponse
     */
    public String markOrderLOSTINDC(String orderId) throws JAXBException, UnsupportedEncodingException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.FORCE_UPDATE,
                new String[]{"" + orderId, "LOST/LOST_IN_DC"}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, null,
                Headers.getLmsAdminHeaderXML());
        return APIUtilities.getElement(service.getResponseBody(),"orderResponse.status.statusType","json");
    }
    
    public String markOrderLOSTORDERSEIZED(String orderId) throws JAXBException, UnsupportedEncodingException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.FORCE_UPDATE,
                new String[]{"" + orderId, "LOST/LOST_ORDER_SEIZED"}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, null,
                Headers.getLmsAdminHeaderXML());
        return APIUtilities.getElement(service.getResponseBody(),"orderResponse.status.statusType","json");
    }

    /**
     * markOrderLOSTINHUB
     * @param orderId
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public String markOrderLOSTINHUB(String orderId) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.FORCE_UPDATE,
                new String[]{"" + orderId, "LOST/LOST_IN_HUB"}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, null,
                Headers.getLmsAdminHeaderXML());
        Assert.assertEquals(service.getResponseStatus(), 200);
        if (service.getResponseBody().toString().contains(EnumSCM.SUCCESS)) return EnumSCM.SUCCESS;
        else return EnumSCM.ERROR;
    }

    /**
     * forceMarkRTO : to mark order RTO forcefully from admin
     * Object orderId
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function forceMarkRTO = orderId ->{
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.FORCE_UPDATE,
                new String[]{"" + orderId, "RTO/RTO_FOUND_ORDER"}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, null,
                Headers.getLmsAdminHeaderXML());
        return APIUtilities.getElement(service.getResponseBody(), "orderResponse.status.statusType", "json");
    };

    /**
     * forceMarkDL : to mark order DELIVERED forcefully from admin
     * Object orderId
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function forceMarkDL = orderId ->{
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.FORCE_UPDATE,
                new String[]{"" + orderId, "DL/DELIVERED"}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, null,
                Headers.getLmsAdminHeaderXML());
        return APIUtilities.getElement(service.getResponseBody(), "orderResponse.status.statusType", "json");
    };

    /**
     * forceMarkDLFD : to mark order From DELIVERED to FAILED DELIVERED forcefully from admin
     * Object orderId
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function forceMarkDLFD = orderId ->{
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.FORCE_UPDATE,
                new String[]{"" + orderId, "FD/NOT_REACHABLE_UNAVAILABLE"}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, null,
                Headers.getLmsAdminHeaderXML());
        return APIUtilities.getElement(service.getResponseBody(), "orderResponse.status.statusType", "json");
    };

    /**
     * forceMarkRTOL : to mark order From RTO to LOST forcefully from admin
     * Object orderId
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function forceMarkRTOL = orderId ->{
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.FORCE_UPDATE,
                new String[]{"" + orderId, "RTOL/RTO_LOST"}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, null,
                Headers.getLmsAdminHeaderXML());
        return APIUtilities.getElement(service.getResponseBody(), "orderResponse.status.statusType", "json");
    };

    /**
     * updateTODOrderInTripAllBought
     *
     * @param tripOrderAssignmentId
     * @param status
     * @param tripAction
     * @param orderId
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public TripOrderAssignmentResponse updateTODOrderInTripAllBought(long tripOrderAssignmentId, String status, String tripAction, String orderId)
            throws  IOException, JAXBException {
        com.myntra.lms.client.response.OrderEntry orderEntry = new com.myntra.lms.client.response.OrderEntry();
        TripOrderAssignmentResponse tripOrderAssignmentResponse = new TripOrderAssignmentResponse();
        TripOrderAssignementEntry tripOrderAssignementEntry = new TripOrderAssignementEntry();
        Map<String, Object> mlShipment = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from ml_shipment where source_reference_id = " + orderId, "lms");
        String mlShipmentId = mlShipment.get("id").toString();
        String dcId = mlShipment.get("delivery_center_id").toString();
        @SuppressWarnings("unchecked")
		List<Map<String, Object>> tNbItems = DBUtilities.exSelectQuery("select id from ml_try_and_buy_item where ml_trynbuy_shipment_id = " + mlShipmentId, "lms");
//        DeliveryPickupReasonCode s = DeliveryPickupReasonCode.DELIVERED;
        AttemptReasonCode s = AttemptReasonCode.DELIVERED;
        if (status.equals(EnumSCM.LOST)) {
            s = AttemptReasonCode.OTHERS;
        } else if (status.equals(EnumSCM.FAILED)) {
            s = AttemptReasonCode.REFUSED_TO_ACCEPT;
        }
        TripAction ta = TripAction.TRIP_COMPLETE;
        if (tripAction.equalsIgnoreCase(EnumSCM.UPDATE)) {
            ta = TripAction.UPDATE;
        } else if (tripAction.equalsIgnoreCase("TRIPSTART")) {
            ta = TripAction.TRIP_START;
            ;
        } else if (tripAction.equalsIgnoreCase("MARKRETURNSCAN")) {
            ta = TripAction.MARK_RETURNSCAN;
        } else if (tripAction.equalsIgnoreCase("MARKOUTSCAN")) {
            ta = TripAction.MARK_OUTSCAN;
        }
        tripOrderAssignementEntry.setId(tripOrderAssignmentId);// assignOrderToTripBulk
        tripOrderAssignementEntry.setRemark("test");
//        tripOrderAssignementEntry.setDeliveryReasonCode(s);
        tripOrderAssignementEntry.setAttemptReasonCode(s);
        tripOrderAssignementEntry.setTripAction(ta);
        tripOrderAssignementEntry.setUpdatedVia(UpdatedVia.WEB);
        tripOrderAssignementEntry.setPaymentType("CASH");
        tripOrderAssignementEntry.setIsOutScanned(false);
        List<TripOrderAssignementEntry> tripOrderAssignementEntries = new ArrayList<>();
        tripOrderAssignementEntries.add(tripOrderAssignementEntry);
        orderEntry.setOrderId(orderId);
        orderEntry.setDeliveryCenterId(Long.parseLong(dcId));
        List<ItemEntry> itemEntries = new ArrayList<>();
        for (Map<String, Object> hashMap : tNbItems) {
            String id = hashMap.get("id").toString();
            ItemEntry itemEntry = new ItemEntry();
            itemEntry.setId(Long.parseLong(id));
            itemEntry.setStatus(TryAndBuyItemStatus.TRIED_AND_BOUGHT);
            itemEntry.setRemarks("Test");
            itemEntry.setQcStatus(null);
            itemEntry.setTriedAndNotBoughtReason(null);
            itemEntries.add(itemEntry);
        }
        orderEntry.setItemEntries(itemEntries);
        tripOrderAssignementEntry.setOrderEntry(orderEntry);
        tripOrderAssignmentResponse.setTripOrderAssignmentEntries(tripOrderAssignementEntries);
        String payload = APIUtilities.convertXMLObjectToString(tripOrderAssignmentResponse);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_UPDATE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse shipmentResponse = (TripOrderAssignmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TripOrderAssignmentResponse());
        return shipmentResponse;
    }

    /**
     * updateTODOrderInTripAllNotBought
     *
     * @param tripOrderAssignmentId
     * @param status
     * @param tripAction
     * @param orderId
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public TripOrderAssignmentResponse updateTODOrderInTripAllNotBought(long tripOrderAssignmentId, String status, String tripAction, String orderId)
            throws  IOException, JAXBException {
        com.myntra.lms.client.response.OrderEntry orderEntry = new com.myntra.lms.client.response.OrderEntry();
        TripOrderAssignmentResponse tripOrderAssignmentResponse = new TripOrderAssignmentResponse();
        TripOrderAssignementEntry tripOrderAssignementEntry = new TripOrderAssignementEntry();
        Map<String, Object> mlShipment = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from ml_shipment where source_reference_id = " + orderId, "lms");
        String mlShipmentId = mlShipment.get("id").toString();
        String dcId = mlShipment.get("delivery_center_id").toString();
        
        @SuppressWarnings("unchecked")
		List<Map<String, Object>> tNbItems = DBUtilities.exSelectQuery("select id from ml_try_and_buy_item where ml_trynbuy_shipment_id = " + mlShipmentId, "lms");
//        DeliveryPickupReasonCode s = DeliveryPickupReasonCode.DELIVERED;
        AttemptReasonCode s = AttemptReasonCode.DELIVERED;
        if (status.equals(EnumSCM.LOST)) {
            s = AttemptReasonCode.OTHERS;
        } else if (status.equals(EnumSCM.FAILED)) {
            s = AttemptReasonCode.REFUSED_TO_ACCEPT;
        }
        TripAction ta = TripAction.TRIP_COMPLETE;
        if (tripAction.equalsIgnoreCase(EnumSCM.UPDATE)) {
            ta = TripAction.UPDATE;
        } else if (tripAction.equalsIgnoreCase("TRIPSTART")) {
            ta = TripAction.TRIP_START;
            ;
        } else if (tripAction.equalsIgnoreCase("MARKRETURNSCAN")) {
            ta = TripAction.MARK_RETURNSCAN;
        } else if (tripAction.equalsIgnoreCase("MARKOUTSCAN")) {
            ta = TripAction.MARK_OUTSCAN;
        }
        tripOrderAssignementEntry.setId(tripOrderAssignmentId);// assignOrderToTripBulk
        tripOrderAssignementEntry.setRemark("test");
//        tripOrderAssignementEntry.setDeliveryReasonCode(s);
        tripOrderAssignementEntry.setAttemptReasonCode(s);
        tripOrderAssignementEntry.setTripAction(ta);
        tripOrderAssignementEntry.setUpdatedVia(UpdatedVia.WEB);
        tripOrderAssignementEntry.setPaymentType("CASH");
        tripOrderAssignementEntry.setIsOutScanned(false);
        List<TripOrderAssignementEntry> tripOrderAssignementEntries = new ArrayList<>();
        tripOrderAssignementEntries.add(tripOrderAssignementEntry);

        orderEntry.setOrderId(orderId);
        orderEntry.setDeliveryCenterId(Long.parseLong(dcId));
        List<ItemEntry> itemEntries = new ArrayList<>();
        for (Map<String, Object> hashMap : tNbItems) {
            String id = hashMap.get("id").toString();
            ItemEntry itemEntry = new ItemEntry();
            itemEntry.setId(Long.parseLong(id));
            itemEntry.setStatus(TryAndBuyItemStatus.TRIED_AND_NOT_BOUGHT);
            itemEntry.setRemarks("Test");
            itemEntry.setQcStatus(ItemQCStatus.PASSED);
            itemEntry.setTriedAndNotBoughtReason(TryAndBuyNotBoughtReason.DID_NOT_LIKE_DESIGN);
            itemEntries.add(itemEntry);
        }
        orderEntry.setItemEntries(itemEntries);
        tripOrderAssignementEntry.setOrderEntry(orderEntry);

        tripOrderAssignmentResponse.setTripOrderAssignmentEntries(tripOrderAssignementEntries);
        String payload = APIUtilities.convertXMLObjectToString(tripOrderAssignmentResponse);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_UPDATE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse shipmentResponse = (TripOrderAssignmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TripOrderAssignmentResponse());
        return shipmentResponse;
    }

    /**
     * updateTODOrderInTripPartiallyBought
     *
     * @param tripOrderAssignmentId
     * @param status
     * @param tripAction
     * @param orderId
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public TripOrderAssignmentResponse updateTODOrderInTripPartiallyBought(long tripOrderAssignmentId, String status, String tripAction, String orderId)
            throws  IOException, JAXBException {
        com.myntra.lms.client.response.OrderEntry orderEntry = new com.myntra.lms.client.response.OrderEntry();
        TripOrderAssignmentResponse tripOrderAssignmentResponse = new TripOrderAssignmentResponse();
        TripOrderAssignementEntry tripOrderAssignementEntry = new TripOrderAssignementEntry();
        Map<String, Object> mlShipment = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from ml_shipment where source_reference_id = " + orderId, "lms");
        String mlShipmentId = mlShipment.get("id").toString();
        String dcId = mlShipment.get("delivery_center_id").toString();
        @SuppressWarnings("unchecked")
		List<Map<String, Object>> tNbItems = DBUtilities.exSelectQuery("select id from ml_try_and_buy_item where ml_trynbuy_shipment_id = " + mlShipmentId, "lms");
//        DeliveryPickupReasonCode s = DeliveryPickupReasonCode.DELIVERED;
        AttemptReasonCode s = AttemptReasonCode.DELIVERED;
        if (status.equals(EnumSCM.LOST)) {
            s = AttemptReasonCode.OTHERS;
        } else if (status.equals(EnumSCM.FAILED)) {
            s = AttemptReasonCode.REFUSED_TO_ACCEPT;
        }
        TripAction ta = TripAction.TRIP_COMPLETE;
        if (tripAction.equalsIgnoreCase(EnumSCM.UPDATE)) {
            ta = TripAction.UPDATE;
        } else if (tripAction.equalsIgnoreCase("TRIPSTART")) {
            ta = TripAction.TRIP_START;
            ;
        } else if (tripAction.equalsIgnoreCase("MARKRETURNSCAN")) {
            ta = TripAction.MARK_RETURNSCAN;
        } else if (tripAction.equalsIgnoreCase("MARKOUTSCAN")) {
            ta = TripAction.MARK_OUTSCAN;
        }
        tripOrderAssignementEntry.setId(tripOrderAssignmentId);// assignOrderToTripBulk
        tripOrderAssignementEntry.setRemark("test");
//        tripOrderAssignementEntry.setDeliveryReasonCode(s);
        tripOrderAssignementEntry.setAttemptReasonCode(s);
        tripOrderAssignementEntry.setTripAction(ta);
        tripOrderAssignementEntry.setUpdatedVia(UpdatedVia.WEB);
        tripOrderAssignementEntry.setPaymentType("CASH");
        tripOrderAssignementEntry.setIsOutScanned(false);
        List<TripOrderAssignementEntry> tripOrderAssignementEntries = new ArrayList<>();
        tripOrderAssignementEntries.add(tripOrderAssignementEntry);
        orderEntry.setOrderId(orderId);
        orderEntry.setDeliveryCenterId(Long.parseLong(dcId));
        List<ItemEntry> itemEntries = new ArrayList<>();
        int flag = 0;
        for (Map<String, Object> hashMap : tNbItems) {
            String id = hashMap.get("id").toString();
            ItemEntry itemEntry = new ItemEntry();
            itemEntry.setId(Long.parseLong(id));
            itemEntry.setRemarks("Test");
            if (flag == 0) {
                itemEntry.setStatus(TryAndBuyItemStatus.TRIED_AND_NOT_BOUGHT);
                itemEntry.setQcStatus(ItemQCStatus.PASSED);
                itemEntry.setTriedAndNotBoughtReason(TryAndBuyNotBoughtReason.DID_NOT_LIKE_DESIGN);
            } else {
                itemEntry.setStatus(TryAndBuyItemStatus.TRIED_AND_BOUGHT);
                itemEntry.setQcStatus(null);
                itemEntry.setTriedAndNotBoughtReason(null);
            }
            itemEntries.add(itemEntry);
            flag++;
        }
        orderEntry.setItemEntries(itemEntries);
        tripOrderAssignementEntry.setOrderEntry(orderEntry);

        tripOrderAssignmentResponse.setTripOrderAssignmentEntries(tripOrderAssignementEntries);
        String payload = APIUtilities.convertXMLObjectToString(tripOrderAssignmentResponse);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_UPDATE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse shipmentResponse = (TripOrderAssignmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TripOrderAssignmentResponse());
        return shipmentResponse;
    }

    /**
     * updateTODOrderInTrip
     *
     * @param tripOrderAssignmentId
     * @param status
     * @param tripAction
     * @param orderId
     * @param inputskuIDs Map<String, String>
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings("unused")
	public TripOrderAssignmentResponse updateTODOrderInTrip(long tripOrderAssignmentId, String status, String tripAction, String orderId, Map<String, String> inputskuIDs)
            throws  IOException, JAXBException {
        com.myntra.lms.client.response.OrderEntry orderEntry = new com.myntra.lms.client.response.OrderEntry();
        TripOrderAssignmentResponse tripOrderAssignmentResponse = new TripOrderAssignmentResponse();
        TripOrderAssignementEntry tripOrderAssignementEntry = new TripOrderAssignementEntry();
        Map<String, Object> mlShipment = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from ml_shipment where source_reference_id = " + orderId, "lms");
        String mlShipmentId = mlShipment.get("id").toString();
        String dcId = mlShipment.get("delivery_center_id").toString();
        double amountToCollect = 0;
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> tNbItems = DBUtilities.exSelectQuery("select id from ml_try_and_buy_item where ml_trynbuy_shipment_id = " + mlShipmentId, "lms");
//        DeliveryPickupReasonCode s = DeliveryPickupReasonCode.DELIVERED;
        AttemptReasonCode s = AttemptReasonCode.DELIVERED;
        if (status.equals(EnumSCM.LOST)) {
            s = AttemptReasonCode.OTHERS;
        } else if (status.equals(EnumSCM.FAILED)) {
            s = AttemptReasonCode.REFUSED_TO_ACCEPT;
        }
        TripAction ta = TripAction.TRIP_COMPLETE;
        if (tripAction.equalsIgnoreCase(EnumSCM.UPDATE)) {
            ta = TripAction.UPDATE;
        } else if (tripAction.equalsIgnoreCase("TRIPSTART")) {
            ta = TripAction.TRIP_START;
            ;
        } else if (tripAction.equalsIgnoreCase("MARKRETURNSCAN")) {
            ta = TripAction.MARK_RETURNSCAN;
        } else if (tripAction.equalsIgnoreCase("MARKOUTSCAN")) {
            ta = TripAction.MARK_OUTSCAN;
        }
        tripOrderAssignementEntry.setId(tripOrderAssignmentId);// assignOrderToTripBulk
        tripOrderAssignementEntry.setRemark("test");
//        tripOrderAssignementEntry.setDeliveryReasonCode(s);
        tripOrderAssignementEntry.setAttemptReasonCode(s);
        tripOrderAssignementEntry.setTripAction(ta);
        tripOrderAssignementEntry.setUpdatedVia(UpdatedVia.WEB);
        tripOrderAssignementEntry.setPaymentType("CASH");
        tripOrderAssignementEntry.setIsOutScanned(false);
        tripOrderAssignementEntry.setTriedAndBoughtDuration(123L);
        tripOrderAssignementEntry.setShipmentType(ShipmentType.TRY_AND_BUY);
        List<TripOrderAssignementEntry> tripOrderAssignementEntries = new ArrayList<>();
        tripOrderAssignementEntries.add(tripOrderAssignementEntry);
        orderEntry.setOrderId(orderId);
        orderEntry.setDeliveryCenterId(Long.parseLong(dcId));
        List<ItemEntry> itemEntries = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> skuIdLists = (List<Map<String, Object>>) DBUtilities.exSelectQuery("select mtabi.id,si.sku_id from shipment_item "
                + "si,ml_try_and_buy_item mtabi,ml_shipment mlsh where mlsh.source_reference_id='" + orderId + "' and mtabi.ml_trynbuy_shipment_id=mlsh.id and "
                + "mtabi.source_item_reference_id=si.id", "myntra_lms");

        Map<String, String> skuMap = new TreeMap<>();
        for (Map<String, Object> abc : skuIdLists) {
            String skuID = "" + abc.get("sku_id");
            if (skuMap.containsKey(skuID)) {
                String ids = skuMap.get(skuID) + "," + abc.get("id");
                skuMap.put(skuID, ids);
            } else {
                skuMap.put(skuID, "" + abc.get("id"));
            }
        }
        @SuppressWarnings("rawtypes")
		Iterator it = skuMap.entrySet().iterator();
        while (it.hasNext()) {
            @SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry) it.next();
            String[] idList = pair.getValue().toString().split(",");
            String[] tnbList = inputskuIDs.get(pair.getKey()).toString().split(",");
            int i = 0;
            for (String id : idList) {
                ItemEntry itemEntry = new ItemEntry();
                itemEntry.setId(Long.parseLong(id));
                itemEntry.setRemarks("Test");
                if (tnbList[i].equalsIgnoreCase(EnumSCM.TRIED_AND_NOT_BOUGHT)) {
                    itemEntry.setStatus(TryAndBuyItemStatus.TRIED_AND_NOT_BOUGHT);
                    itemEntry.setTriedAndNotBoughtReason(TryAndBuyNotBoughtReason.DID_NOT_LIKE_DESIGN);
                    itemEntry.setQcStatus(ItemQCStatus.PASSED);
                } else if (tnbList[i].equalsIgnoreCase(EnumSCM.SNATCHED)) {
                    itemEntry.setStatus(TryAndBuyItemStatus.SNATCHED);
                } else if (tnbList[i].equalsIgnoreCase(EnumSCM.NOT_TRIED)) {
                    itemEntry.setStatus(TryAndBuyItemStatus.NOT_TRIED);
                    amountToCollect = Double.parseDouble(lmsHelper.getTODItemCodAmount(id));
                } else {
                    itemEntry.setStatus(TryAndBuyItemStatus.TRIED_AND_BOUGHT);
                    amountToCollect = Double.parseDouble(lmsHelper.getTODItemCodAmount(id));
                }
                i++;
                itemEntries.add(itemEntry);
            }
        }
        orderEntry.setItemEntries(itemEntries);
        orderEntry.setCodAmount(amountToCollect);
        tripOrderAssignementEntry.setOrderEntry(orderEntry);
        tripOrderAssignmentResponse.setTripOrderAssignmentEntries(tripOrderAssignementEntries);
        String payload = APIUtilities.convertXMLObjectToString(tripOrderAssignmentResponse);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_UPDATE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse shipmentResponse = (TripOrderAssignmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TripOrderAssignmentResponse());
        return shipmentResponse;
    }

    /**
     * updateTODOrderInTrip
     * @param tripOrderAssignmentId
     * @param status
     * @param tripAction
     * @param orderId
     * @param inputskuIDs
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings("rawtypes")
	public TripOrderAssignmentResponse updateTODOrderInTrip(long tripOrderAssignmentId, String status, String tripAction, String orderId, List<TryNBuyEntry> inputskuIDs)
            throws  IOException, JAXBException {
        com.myntra.lms.client.response.OrderEntry orderEntry = new com.myntra.lms.client.response.OrderEntry();
        TripOrderAssignmentResponse tripOrderAssignmentResponse = new TripOrderAssignmentResponse();
        TripOrderAssignementEntry tripOrderAssignementEntry = new TripOrderAssignementEntry();
        Map<String, Object> mlShipment = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from ml_shipment where source_reference_id = " + orderId, "lms");
        String mlShipmentId = mlShipment.get("id").toString();
        String dcId = mlShipment.get("delivery_center_id").toString();
        double amountToCollect = 0;
        DBUtilities.exSelectQuery("select id from ml_try_and_buy_item where ml_trynbuy_shipment_id = " + mlShipmentId, "lms");
//        DeliveryPickupReasonCode s = DeliveryPickupReasonCode.DELIVERED;
        AttemptReasonCode s = AttemptReasonCode.DELIVERED;
        if (status.equals(EnumSCM.LOST)) {
            s = AttemptReasonCode.OTHERS;
        } else if (status.equals(EnumSCM.FAILED)) {
            s = AttemptReasonCode.REFUSED_TO_ACCEPT;
        }
        TripAction ta = TripAction.TRIP_COMPLETE;
        if (tripAction.equalsIgnoreCase(EnumSCM.UPDATE)) {
            ta = TripAction.UPDATE;
        } else if (tripAction.equalsIgnoreCase("TRIPSTART")) {
            ta = TripAction.TRIP_START;
            ;
        } else if (tripAction.equalsIgnoreCase("MARKRETURNSCAN")) {
            ta = TripAction.MARK_RETURNSCAN;
        } else if (tripAction.equalsIgnoreCase("MARKOUTSCAN")) {
            ta = TripAction.MARK_OUTSCAN;
        }
        tripOrderAssignementEntry.setId(tripOrderAssignmentId);// assignOrderToTripBulk
        tripOrderAssignementEntry.setRemark("test");
//        tripOrderAssignementEntry.setDeliveryReasonCode(s);
        tripOrderAssignementEntry.setAttemptReasonCode(s);
        tripOrderAssignementEntry.setTripAction(ta);
        tripOrderAssignementEntry.setUpdatedVia(UpdatedVia.WEB);
        tripOrderAssignementEntry.setPaymentType("CASH");
        tripOrderAssignementEntry.setIsOutScanned(false);
        tripOrderAssignementEntry.setTriedAndBoughtDuration(123L);
        tripOrderAssignementEntry.setShipmentType(ShipmentType.TRY_AND_BUY);
        List<TripOrderAssignementEntry> tripOrderAssignementEntries = new ArrayList<>();
        tripOrderAssignementEntries.add(tripOrderAssignementEntry);
        orderEntry.setOrderId(orderId);
        orderEntry.setDeliveryCenterId(Long.parseLong(dcId));
        List<ItemEntry> itemEntries = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> skuIdLists = (List<Map<String, Object>>) DBUtilities.exSelectQuery("select mtabi.id,si.sku_id from shipment_item "
                + "si,ml_try_and_buy_item mtabi,ml_shipment mlsh where mlsh.source_reference_id='" + orderId + "' and mtabi.ml_trynbuy_shipment_id=mlsh.id and "
                + "mtabi.source_item_reference_id=si.id", "myntra_lms");

        Map<String, String> skuMap = new TreeMap<>();
        for (Map<String, Object> abc : skuIdLists) {
            String skuID = "" + abc.get("sku_id");
            if (skuMap.containsKey(skuID)) {
                String ids = skuMap.get(skuID) + "," + abc.get("id");
                skuMap.put(skuID, ids);
            } else {
                skuMap.put(skuID, "" + abc.get("id"));
            }
        }
        Iterator it = skuMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String[] idList = pair.getValue().toString().split(",");
            String[] tnbList = inputskuIDs.stream()
                    .filter(sku->sku.getSkuId()==Long.parseLong(pair.getKey().toString()))
                    .findFirst()
                    .map(TryNBuyEntry::getStatus)
                    .get()
                    .split(",");
            int i = 0;
            for (String id : idList) {
                ItemEntry itemEntry = new ItemEntry();
                itemEntry.setId(Long.parseLong(id));
                itemEntry.setRemarks("Test");
                if (tnbList[i].equalsIgnoreCase(EnumSCM.TRIED_AND_NOT_BOUGHT)) {
                    itemEntry.setStatus(TryAndBuyItemStatus.TRIED_AND_NOT_BOUGHT);
                    itemEntry.setTriedAndNotBoughtReason(TryAndBuyNotBoughtReason.DID_NOT_LIKE_DESIGN);
                    itemEntry.setQcStatus(ItemQCStatus.PASSED);
                } else if (tnbList[i].equalsIgnoreCase(EnumSCM.SNATCHED)) {
                    itemEntry.setStatus(TryAndBuyItemStatus.SNATCHED);
                } else if (tnbList[i].equalsIgnoreCase(EnumSCM.NOT_TRIED)) {
                    itemEntry.setStatus(TryAndBuyItemStatus.NOT_TRIED);
                    amountToCollect = Double.parseDouble(lmsHelper.getTODItemCodAmount(id));
                } else {
                    itemEntry.setStatus(TryAndBuyItemStatus.TRIED_AND_BOUGHT);
                    amountToCollect = Double.parseDouble(lmsHelper.getTODItemCodAmount(id));
                }
                i++;
                itemEntries.add(itemEntry);
            }
        }
        orderEntry.setItemEntries(itemEntries);
        orderEntry.setCodAmount(amountToCollect);
        tripOrderAssignementEntry.setOrderEntry(orderEntry);
        tripOrderAssignmentResponse.setTripOrderAssignmentEntries(tripOrderAssignementEntries);
        String payload = APIUtilities.convertXMLObjectToString(tripOrderAssignmentResponse);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_UPDATE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse shipmentResponse = (TripOrderAssignmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TripOrderAssignmentResponse());
        return shipmentResponse;
    }

    /**
     * getTODItemAndStatusMap
     * @param orderId
     * @param tryNBuyEntry
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings("rawtypes")
	public Map<String, String> getTODItemAndStatusMap(String orderId, List<TryNBuyEntry> tryNBuyEntry)
            throws  IOException, JAXBException {
        Map<String, String> itemWithStatus = new HashMap<>();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> skuIdLists = (List<Map<String, Object>>) DBUtilities.exSelectQuery("select mtabi.id,si.sku_id from shipment_item "
                + "si,ml_try_and_buy_item mtabi,ml_shipment mlsh where mlsh.source_reference_id=" + orderId + " and mtabi.ml_trynbuy_shipment_id=mlsh.id "
                + "and mtabi.source_item_reference_id=si.id", "myntra_lms");
        Map<String, String> skuMap = new TreeMap<>();
        for (Map<String, Object> abc : skuIdLists) {
            String skuID = "" + abc.get("sku_id");
            if (skuMap.containsKey(skuID)) {
                String ids = skuMap.get(skuID) + "," + abc.get("id");
                skuMap.put(skuID, ids);
            } else {
                skuMap.put(skuID, "" + abc.get("id"));
            }
        }
        Iterator it = skuMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String[] idList = pair.getValue().toString().split(",");
            String[] tnbList = tryNBuyEntry.stream()
                    .filter(sku->sku.getSkuId()==Long.parseLong(pair.getKey().toString()))
                    .findFirst()
                    .map(TryNBuyEntry::getStatus)
                    .get()
                    .split(",");
            int i = 0;
            for (String id : idList) {
                if (tnbList[i].equalsIgnoreCase(EnumSCM.TRIED_AND_NOT_BOUGHT)) {
                    itemWithStatus.put(id, EnumSCM.TRIED_AND_NOT_BOUGHT);
                } else if (tnbList[i].equalsIgnoreCase(EnumSCM.SNATCHED)) {
                    itemWithStatus.put(id, EnumSCM.SNATCHED);
                } else if (tnbList[i].equalsIgnoreCase(EnumSCM.NOT_TRIED)) {
                    itemWithStatus.put(id, EnumSCM.NOT_TRIED);
                } else {
                    itemWithStatus.put(id,EnumSCM.TRIED_AND_BOUGHT);
                }
                i++;
            }
        }
        return itemWithStatus;
    }

    /**
     * updateTODOrderInTripFailed
     *
     * @param tripOrderAssignmentId
     * @param status
     * @param tripAction
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public TripOrderAssignmentResponse updateTODOrderInTripFailed(long tripOrderAssignmentId, String status, String tripAction) throws  IOException, JAXBException {
//        DeliveryPickupReasonCode tripStatus = DeliveryPickupReasonCode.REFUSED_CUSTOMER_GRIEVANCE;
        AttemptReasonCode tripStatus = AttemptReasonCode.REFUSED_TO_ACCEPT;
        TripAction ta = TripAction.TRIP_COMPLETE;
        if (tripAction.equalsIgnoreCase(EnumSCM.UPDATE)) {
            ta = TripAction.UPDATE;
        } else if (tripAction.equalsIgnoreCase("TRIPSTART")) {
            ta = TripAction.TRIP_START;
            ;
        }
        TripOrderAssignmentResponse tripOrderAssignmentResponse = new TripOrderAssignmentResponse();
        TripOrderAssignementEntry tripOrderAssignementEntry = new TripOrderAssignementEntry();
        OrderEntry orderEntry = new OrderEntry();
        tripOrderAssignementEntry.setId(tripOrderAssignmentId);// assignOrderToTripBulk
        tripOrderAssignementEntry.setRemark("test");
//        tripOrderAssignementEntry.setDeliveryReasonCode(tripStatus);
        tripOrderAssignementEntry.setAttemptReasonCode(tripStatus);
        tripOrderAssignementEntry.setTripAction(ta);
        tripOrderAssignementEntry.setUpdatedVia(UpdatedVia.WEB);
        tripOrderAssignementEntry.setIsOutScanned(true);
        List<TripOrderAssignementEntry> tripOrderAssignementEntries = new ArrayList<>();
        tripOrderAssignementEntries.add(tripOrderAssignementEntry);
        orderEntry.setCodAmount(0.0);
        tripOrderAssignementEntry.setOrderEntry(orderEntry);
        tripOrderAssignmentResponse.setTripOrderAssignmentEntries(tripOrderAssignementEntries);
        String payload = APIUtilities.convertXMLObjectToString(tripOrderAssignmentResponse);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_UPDATE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse response = (TripOrderAssignmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TripOrderAssignmentResponse());
        return response;
    }

    /**
     * bulkOrderReassignment
     *
     * @param orderId
     * @param from
     * @param to
     * @param serviceType
     * @param shippingMethod
     * @param paymentMode
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public ReassignOrderResponse bulkOrderReassignment(String orderId, String from, String to, String serviceType, String shippingMethod, String paymentMode) throws UnsupportedEncodingException, JAXBException {
        String queryParam = "reassignOrderV2?orderId=" + orderId + "&fromCourierCode=" + from + "&toCourierCode=" + to + "&serviceType=" + serviceType + "&shippingMethod=" + shippingMethod + "&paymentMode=" + paymentMode;
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.BULK_ORDER_REASSIGN, new String[]{queryParam}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        ReassignOrderResponse response = (ReassignOrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ReassignOrderResponse());
        return response;
    }


    /**
     * downloadStoreTrip
     *
     * @param queryParam
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public StoreTripResponse downloadStoreTrip(String queryParam)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP, new String[]{queryParam}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        StoreTripResponse response = (StoreTripResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new StoreTripResponse());
        return response;
    }

    /**
     * getTripOrder
     *
     * @param queryParam
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public TripOrderAssignmentResponse getTripOrder(String queryParam)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP, new String[]{queryParam}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse shipmentResponse = (TripOrderAssignmentResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new TripOrderAssignmentResponse());
        return shipmentResponse;
    }

    /**
     * getTrip : to get a trip using query paramas
     * Object param
     */
   
    @SuppressWarnings("rawtypes")
	public LambdaInterfaces.Function getTrip =  queryParam -> {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP, new String[]{""+queryParam}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        TripResponse shipmentResponse = (TripResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new TripResponse());
        return shipmentResponse;
    };

    /**
     * getTripByTripNumber
     * Object tripNumber
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.BiFunction getTripByTripNumber =  (tripNumber,shipmentType )-> {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_BY_TRIP_NUMBER, new String[]{tripNumber.toString(),shipmentType.toString()}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse shipmentResponse = (TripOrderAssignmentResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new TripOrderAssignmentResponse());
        return shipmentResponse;
    };

    /**
     * getTripOrderFinance
     * @param queryParam
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public FinanceReportResponse getTripOrderFinance(String queryParam)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP, new String[]{queryParam}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        FinanceReportResponse shipmentResponse = (FinanceReportResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new FinanceReportResponse());
        return shipmentResponse;
    }

    /**
     * getAllIncompleteOrdersForDC
     *
     * @param queryParam
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public OrderResponse getAllIncompleteOrdersForDC(String queryParam)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.ORDER, new String[]{queryParam}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        OrderResponse shipmentResponse = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderResponse());
        return shipmentResponse;
    }

    /**
     * getReturnAddress
     *
     * @param zipcode
     * @param courierCode
     * @param sourceWarehouseId
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public ReturnAddress getReturnAddress(String zipcode, String courierCode, long sourceWarehouseId)
            throws IOException, JAXBException {
        PickupEntry pickupEntry = new PickupEntry();
        pickupEntry.setZipcode(zipcode);
        pickupEntry.setCourierCode(courierCode);
        pickupEntry.setSourceWarehouseId(sourceWarehouseId);
        String payload = APIUtilities.convertXMLObjectToString(pickupEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_RETURN_ADDRESS, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ReturnAddress response = (ReturnAddress) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new ReturnAddress());
        return response;
    }

    /**
     * getRtoAddress
     *
     * @param zipcode
     * @param courierCode
     * @param warehouseId
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public ReturnAddress getRtoAddress(String zipcode, String courierCode, String warehouseId)
            throws  IOException, JAXBException {
        OrderEntry orderEntry = new OrderEntry();
        orderEntry.setZipcode(zipcode);
        orderEntry.setCourierOperator(courierCode);
        orderEntry.setWarehouseId(warehouseId);
        String payload = APIUtilities.convertXMLObjectToString(orderEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_RTO_ADDRESS, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        ReturnAddress response = (ReturnAddress) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new ReturnAddress());
        return response;
    }

    /**
     * assignOrUnAssignShipmentToHLP
     *
     * @param zipcode
     * @param courierCode
     * @param sourceWarehouseId
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public PickupResponse assignOrUnAssignShipmentToHLP(String zipcode, String courierCode, long sourceWarehouseId)
            throws  IOException, JAXBException {
        String payload = "";
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_RETURN_ADDRESS, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        PickupResponse response = (PickupResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new PickupResponse());
        return response;
    }

    /**
     * addAndOutscanNewOrderToTrip
     *
     * @param tripId
     * @param trackingNumber
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public TripOrderAssignmentResponse addAndOutscanNewOrderToTrip(long tripId, String trackingNumber)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_OUT_SCAN, new String[]{trackingNumber, "" + tripId}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, null, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse response = (TripOrderAssignmentResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new TripOrderAssignmentResponse());
        return response;
    }

    /**
     * unassignOrderFronTrip
     * Object tripOrderAssignmentId
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function unassignOrderFronTrip = tripOrderAssignmentId ->{
        return APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.UNASSIGN_ORDER_FROM_TRIP, new String[]{""+tripOrderAssignmentId}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, null, Headers.getLmsHeaderXML()).getResponseBody(), new TripOrderAssignmentResponse());
    };

    /**
     * unassignOrderFromTripThroughTripId
     * Object orderId,trackingNo,tripId,shipmentType
     */
    
    @SuppressWarnings("rawtypes")
	public LambdaInterfaces.FourFunction unassignOrderFromTripThroughTripId = (orderId,trackingNo,tripId,shipmentType) ->{
        return APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.UNASSIGN_ORDER_FROM_TRIP_THROUGH_TRIP_ID, new String[]{""+orderId,""+trackingNo,""+tripId,""+shipmentType}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, null, Headers.getLmsHeaderXML()).getResponseBody(), new TripOrderAssignmentResponse());
    };

    /**
     * getTripUpdate
     *
     * @param param
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public TripOrderAssignmentResponse getTripUpdate(String param)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP, new String[]{param}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse response = (TripOrderAssignmentResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new TripOrderAssignmentResponse());
        return response;
    }

    /**
     * getTripResult
     *
     * @param param
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public OrderResponse getTripResult(String param)
            throws  IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.ORDER, new String[]{param}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        OrderResponse response = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderResponse());
        return response;
    }

    /**
     * getTripResultPost
     *
     * @param param
     * @param deliveryCenterId
     * @param ShipmentType
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public OrderResponse getTripResultPost(String param, long deliveryCenterId, ShipmentType ShipmentType)
            throws  IOException, JAXBException {
        TripEntry tripEntry = new TripEntry();
        tripEntry.setDeliveryCenterId(deliveryCenterId);
        tripEntry.setShipmentType(ShipmentType);
        tripEntry.setStart(0);
        tripEntry.setLimit(20);
        String payload = APIUtilities.convertXMLObjectToString(tripEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.ORDER, new String[]{param}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        OrderResponse response = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderResponse());
        return response;
    }

    /**
     * selfMarkDL from Prism or from APP by user
     *
     * @param orderReleaseId
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public TripOrderAssignmentResponse selfMarkDL(String orderReleaseId) throws JAXBException, UnsupportedEncodingException {
        TripOrderAssignementEntry tripOrderAssignementEntry = new TripOrderAssignementEntry();
        tripOrderAssignementEntry.setTripOrderStatus(TripOrderStatus.DL);
        tripOrderAssignementEntry.setRemark(EnumSCM.DELIVERED);
        tripOrderAssignementEntry.setCreatedBy("30f43acf.f0f2.4e44.842f.1aeae1b79652VbqNFDEZLY");
        tripOrderAssignementEntry.setOrderId(orderReleaseId);
//        tripOrderAssignementEntry.setDeliveryReasonCode(DeliveryPickupReasonCode.DELIVERED);
        tripOrderAssignementEntry.setAttemptReasonCode(AttemptReasonCode.DELIVERED);
        tripOrderAssignementEntry.setShipmentType(ShipmentType.DL);
        tripOrderAssignementEntry.setMode("Customer"); //Customer / CC
        tripOrderAssignementEntry.setDeliveryTime(new Date());
        String payload = APIUtilities.convertXMLObjectToString(tripOrderAssignementEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.SELF_MARK_DL, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse response = (TripOrderAssignmentResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new TripOrderAssignmentResponse());
        return response;
    }

    /**
     * requeueOrder
     *
     * @param orderId
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public TripOrderAssignmentResponse requeueOrder(String orderId) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.REQUEUE_ORDER, new String[]{orderId}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, null, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse response = (TripOrderAssignmentResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new TripOrderAssignmentResponse());
        return response;
    }

    /**
     * returnApproveOrReject
     *
     * @param returnId
     * @param status
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public OrderResponse returnApproveOrReject(String returnId, String status) throws UnsupportedEncodingException, JAXBException {
        //LMSHelper lmsHelper = new LMSHelper();
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.APPROVE_REJECT_RETURN, new String[]{"" + returnId, status, "aditya.malpani"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, null, Headers.getLmsHeaderXML());
        OrderResponse response = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderResponse());
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to Approve/Reject order");
        /*if (status.equalsIgnoreCase(EnumSCM.APPROVED)) {
			Assert.assertEquals(lmsHelper.getReturnStatus(returnId), EnumSCM.PICKUP_SUCCESSFUL);
		}else{
			Assert.assertEquals(lmsHelper.getReturnStatus(returnId), "RETURN_REJECTED_RESHIP_PENDING");
		}*/
        return response;
    }

    /**
     * requeuePickup after failed pickup
     *
     * @param returnID
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public OrderResponse requeuePickup(String returnID) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.REQUEUE_PICKUP, new String[]{returnID, "REQ"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, null, Headers.getLmsHeaderXML());
        OrderResponse response = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderResponse());
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to requeue order");
        return response;
    }

    /**
     * cancelPickup
     *
     * @param returnID
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public OrderResponse cancelPickup(String returnID) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.REQUEUE_PICKUP, new String[]{returnID, "REJ"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, null, Headers.getLmsHeaderXML());
        OrderResponse response = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderResponse());
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to requeue order");
        return response;
    }

    /**
     * processOrderInLMSFromSHToTripCreations
     *
     * @param orderReleaseID
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws NumberFormatException
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> processOrderInLMSFromSHToTripCreation(String orderReleaseID) throws NumberFormatException, IOException, JAXBException, InterruptedException, ManagerException, JSONException, XMLStreamException {
       
    		Map<String, Object> tn = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select tracking_number from order_to_ship where order_id = " + orderReleaseID, "lms");
        if (tn == null) {
            return null;
        }
        String trackingNo = tn.get("tracking_number").toString();
        Map<String, String> data = new HashMap<>();
        // Receive MasterBag
        String deliveryCenterID = ((OrderResponse) getOrderLMS.apply(orderReleaseID)).getOrders().get(0).getDeliveryCenterId().toString();
        String deliveryStaffID = getDeliveryStaffID(deliveryCenterID);
        log.info("Delivery Center ID :" + deliveryStaffID + "  Delivery Center ID : " + deliveryCenterID);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");

        TripResponse tripResponse = createTrip(Long.parseLong(deliveryCenterID), Long.parseLong(deliveryStaffID));
        Long tripId = tripResponse.getTrips().get(0).getId();
//        TripOrderAssignmentResponse tripOrderAssignmentResponse = lmsServiceHelper.assignOrderToTrip(tripId, Long.parseLong(orderReleaseID), trackingNumber);
        SlackMessenger.send("scm_e2e_order_sanity", "Trip Creation complete");

        // scan tracking number in trip
        log.info("Tracking ID := " + tripId);
        TripOrderAssignmentResponse addAndOutScanOrderToTrip = addAndOutscanNewOrderToTrip(tripId, trackingNo);
        Assert.assertEquals(addAndOutScanOrderToTrip.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);

        // Start Trip
        TripOrderAssignmentResponse startTripRes = startTrip("" + tripId, "10");
        Assert.assertEquals(startTripRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        SlackMessenger.send("scm_e2e_order_sanity", "Trip Started");
        Map<String, Object> toaId = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select id from trip_order_assignment where trip_id = " + tripId, "lms");
        if (toaId == null) {
            return null;
        }
        String tripOrderAssignmentId = toaId.get("id").toString();
        data.put("tripOrderAssignmentId", tripOrderAssignmentId);
        data.put("tripId", tripId.toString());
        return data;
    }

    /**
     * ProcessOrderInLMSFromSHToTripCreation
     *
     * @param orderReleaseID
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws NumberFormatException
     * @throws IOException
     * @throws JAXBException
     */
    public Map<String, String> processOrderInLMSFromSHToTripCreation( String orderReleaseID, String toStatus) throws NumberFormatException, IOException, JAXBException, InterruptedException, ManagerException, XMLStreamException, JSONException {
        
    	    Map<String, String> data = new HashMap<>();
        @SuppressWarnings("unchecked")
		OrderResponse orderResponse = (OrderResponse) getOrderLMS.apply(orderReleaseID);


        if (toStatus.equals(EnumSCM.RECEIVE_IN_DC)) return null;
        SlackMessenger.send("scm_e2e_order_sanity", "Master bag InScan complete");
        String deliveryStaffID = getDeliveryStaffID(orderResponse.getOrders().get(0).getDeliveryCenterId().toString());
        log.info("Delivery Center ID :" + deliveryStaffID + "  Delivery Center ID : " + orderResponse.getOrders().get(0).getDeliveryCenterId().toString());
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");

        TripResponse tripResponse = createTrip(Long.parseLong(orderResponse.getOrders().get(0).getDeliveryCenterId().toString()), Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        Long tripId = tripResponse.getTrips().get(0).getId();
        SlackMessenger.send("scm_e2e_order_sanity", "Trip Creation complete");
        log.info("Tracking ID := " + tripId);
        Assert.assertEquals(addAndOutscanNewOrderToTrip(tripId, orderResponse.getOrders().get(0).getTrackingNumber()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to addAndOutscanNewOrderToTrip");
        Assert.assertEquals(lmsHelper.getMLShipmentStatus(orderReleaseID),EnumSCM.ASSIGNED_TO_SDA);
        TripOrderAssignmentResponse startTripRes = startTrip("" + tripId, "10");
        if (!startTripRes.getStatus().getStatusType().toString().equalsIgnoreCase(EnumSCM.SUCCESS))
            SlackMessenger.send("scm_e2e_order_sanity", "Unable to startTrip", 3);
        Assert.assertEquals(startTripRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to startTrip");
        Thread.currentThread();
		Thread.sleep(2000);
        Assert.assertEquals(lmsHelper.getOrderToShipStatus(orderReleaseID),EnumSCM.OUT_FOR_DELIVERY, "After trip start DB status of order is not `OUT_FOR_DELIVERY`");
        Assert.assertEquals(lmsHelper.getMLShipmentStatus(orderReleaseID),EnumSCM.OUT_FOR_DELIVERY, "After trip start DB status of order in ml_shipment is not `OUT_FOR_DELIVERY`");
        SlackMessenger.send("scm_e2e_order_sanity", "Trip Started");
        Map<String, Object> toaId = DBUtilities.exSelectQueryForSingleRecord("select id from trip_order_assignment where trip_id = " + tripId, "lms");
        if (toaId == null) {
            Assert.fail("Unable to fetch trip_id from DB");
        }
        String tripOrderAssignmentId = toaId.get("id").toString();
        data.put("tripOrderAssignmentId", tripOrderAssignmentId);
        data.put("trackingNumber", orderResponse.getOrders().get(0).getTrackingNumber());
        data.put("tripId", tripId.toString());
        return data;
    }

    /**
     * ProcessOrderFromSHtoReceiveInDC
     *
     * @param masterBagId
     * @param orderReleaseID
     * @param warehouseID
     * @param pincode
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public String processOrderFromSHtoReceiveInDC(long masterBagId, String orderReleaseID, String warehouseID,
                                                  String pincode) throws IOException, JAXBException, InterruptedException, ManagerException, JSONException, XMLStreamException {
        @SuppressWarnings("unchecked")
		String deliveryCenterID = ((OrderResponse) getOrderLMS.apply(orderReleaseID)).getOrders().get(0).getDeliveryCenterId().toString();
        ShipmentResponse receiveMasterBagRes = masterBagInScan(masterBagId, Long.parseLong(deliveryCenterID));
        Assert.assertEquals(receiveMasterBagRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to Inscan MasterBag");
        SlackMessenger.send("scm_e2e_order_sanity", "Receive master bag complete");
        // receive order with master bag
        ExceptionHandler.handleEquals(receiveShipmentFromMasterbag(masterBagId).
                getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive shipment in DC");
        ShipmentResponse receiveOrderWithMasterBagRes = masterBagInScanUpdate(masterBagId, orderReleaseID, "Bangalore", Long.parseLong(deliveryCenterID), "DC", Long.parseLong(warehouseID));
        log.info("Ship Master Bag response : " + receiveOrderWithMasterBagRes.toString());
        Assert.assertEquals(receiveOrderWithMasterBagRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive master bag in DC");
        Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId),EnumSCM.RECEIVED,"Masterbag status is not update in DB to `RECEIVED`");
        Assert.assertEquals(lmsHelper.getMLShipmentStatus(orderReleaseID), EnumSCM.UNASSIGNED, "Shipment Staus is not update to UNASSIGENED in ML shipment2");
        return deliveryCenterID;
    }


    public String processOrderFromSHToReceiveInDCV1(long masterBagId, LMSOrderEntries orderEntries) throws IOException, JAXBException, ManagerException, InterruptedException, XMLStreamException, JSONException {
        Long deliveryCenterID = orderEntries.getDcID();
        String wareHouseID  = orderEntries.getWareHouseID();
        ShipmentResponse receiveMasterBagRes = masterBagInScan(masterBagId, deliveryCenterID);
        if(!receiveMasterBagRes.getStatus().getStatusType().toString().equals(EnumSCM.SUCCESS)){
            throw new ManagerException(receiveMasterBagRes.getStatus().getStatusMessage(), receiveMasterBagRes.getStatus().getStatusCode());
        }

        // receive order with master bag
        for (LMSOrderEntry orderEntry:orderEntries.getOrderEntries()) {
            ExceptionHandler.handleEquals(receiveShipmentFromMasterbag(masterBagId).
                    getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive shipment in DC");
            ShipmentResponse receiveOrderWithMasterBagRes = masterBagInScanUpdate(masterBagId, orderEntry.getOrderID(), "Bangalore", deliveryCenterID, "DC", Long.parseLong(wareHouseID));
            if(!receiveOrderWithMasterBagRes.getStatus().getStatusType().toString().equals(EnumSCM.SUCCESS)){
                throw new ManagerException(receiveOrderWithMasterBagRes.getStatus().getStatusMessage(), receiveOrderWithMasterBagRes.getStatus().getStatusCode());
            }
            if(!lmsHelper.getMLShipmentStatus(orderEntry.getOrderID()).equals(EnumSCM.UNASSIGNED)){
                throw new ManagerException("Shipment Staus is not update to UNASSIGENED in ML", receiveOrderWithMasterBagRes.getStatus().getStatusCode());
            }
        }

        if(!lmsHelper.getMasterBagStatus(masterBagId).equals(EnumSCM.RECEIVED)){
            throw new ManagerException("Master Bag Status is not Received in LMS", 2001);
        }
    return "";

    }

    /**
     * ProcessOrderInLMSFromSHToDL
     *
     * @param orderReleaseID
     * @param warehouseID
     * @param pincode
     * @param trackingNo
     * @param toStatus
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     * @throws JSONException
     * @throws XMLStreamException
     */
    @SuppressWarnings("unchecked")
    public void processOrderInLMSFromSHToDL(String orderReleaseID, String warehouseID,
                                            String pincode, String trackingNo, String toStatus) throws InterruptedException, JAXBException, IOException, JSONException, XMLStreamException, ParseException, ManagerException {

        OrderResponse orderToship = (OrderResponse)getOrderLMS.apply(orderReleaseID);
        trackingNo = orderToship.getOrders().get(0).getTrackingNumber();
        String courierCode = orderToship.getOrders().get(0).getCourierOperator();

        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID, EnumSCM.SH, 2),"order was not in SH state");
        if (courierCode.equalsIgnoreCase("ML")) {
            switch (toStatus) {
                case EnumSCM.UNRTO:
                    markUnassignedToRTO( orderReleaseID, warehouseID, pincode, trackingNo);
                    break;
                case EnumSCM.DL:
                    Map<String, String> data = processOrderInLMSFromSHToTripCreation( orderReleaseID, toStatus);
                    markTripDL(orderReleaseID, data.get("tripOrderAssignmentId"));
                    updateEndOdometerReading(data.get("tripId"));
                    break;
                case EnumSCM.RECEIVE_IN_DC: processOrderInLMSFromSHToTripCreation(orderReleaseID, toStatus);
                    break;
                case EnumSCM.SMDL:
                    Map<String, String> data1 = processOrderInLMSFromSHToTripCreation(orderReleaseID, toStatus);
                    markTripSMDL(orderReleaseID, data1.get("tripOrderAssignmentId"));
                    updateEndOdometerReading(data1.get("tripId"));
                    break;
                case EnumSCM.RTO:
                    Map<String, String> data2 = processOrderInLMSFromSHToTripCreation(orderReleaseID, toStatus);
                    markTripRTO(orderReleaseID, data2.get("tripOrderAssignmentId"), data2.get("trackingNumber"));
                    updateEndOdometerReading(data2.get("tripId"));
                    break;
                case EnumSCM.FD:
                    Map<String, String> data4 = processOrderInLMSFromSHToTripCreation(orderReleaseID, toStatus);
                    markTripFD(orderReleaseID,data4.get("tripOrderAssignmentId"));// On different Trip
                    updateEndOdometerReading(data4.get("tripId"));
                    break;
                case EnumSCM.FDDL:
                    Map<String, String> data3 = processOrderInLMSFromSHToTripCreation(orderReleaseID, toStatus);
                    markTripFDDL(orderReleaseID, data3.get("tripOrderAssignmentId"));// On different Trip
                    updateEndOdometerReading(data3.get("tripId"));
                    break;
                case EnumSCM.FDTODL:
                    Map<String, String> data7 = processOrderInLMSFromSHToTripCreation(orderReleaseID, toStatus);
                    markTripFDTODL(orderReleaseID, data7.get("tripOrderAssignmentId"));// Mark on same Trip
                    updateEndOdometerReading(data7.get("tripId"));
                    break;
                case EnumSCM.FDFDDL:
                    Map<String, String> data5 = processOrderInLMSFromSHToTripCreation(orderReleaseID, toStatus);
                    markTripFDFDDL(orderReleaseID, pincode, data5.get("tripOrderAssignmentId"));
                    updateEndOdometerReading(data5.get("tripId"));
                    break;
                case EnumSCM.LOST:
                    Map<String, String> data6 = processOrderInLMSFromSHToTripCreation(orderReleaseID, toStatus);
                    markLostAfterTrip(orderReleaseID, data6.get("tripOrderAssignmentId"));
                    updateEndOdometerReading(data6.get("tripId"));
                    break;
                case EnumSCM.LOST_IN_DC:
                    markTripLostInDC(orderReleaseID);
                    break;
                case EnumSCM.OFD:
                    Map<String, String> tripData = processOrderInLMSFromSHToTripCreation(orderReleaseID, toStatus);
                    DBUtilities.exUpdateQuery("update trip set trip_status = 'COMPLETED' where id = "+tripData.get("tripId"),"lms");
                    break;
            }
            log.info("Your order processing has been completed successfully\n OrderId: " + orderReleaseID);
        }else if (courierCode.equalsIgnoreCase("EK")){
            ekartToDL(orderReleaseID);
        }else if (courierCode.equalsIgnoreCase("DE")){
            try {
                Long dcId = orderToship.getOrders().get(0).getDeliveryCenterId();
                Map<String, Object> deliveryCenter = DBUtilities.exSelectQueryForSingleRecord("select type from delivery_center where id =" + dcId, "lms");
                if (deliveryCenter.get("type").toString().equals(EnumSCM.COURIER_HANDOVER)) {
                    DelhiveryRHDToDL(orderReleaseID,trackingNo);
                }else {
                    throw new IOException();
                }
            } catch (Exception e) {
                DelhiveryToDL(orderReleaseID, trackingNo);
            }
        }else {
            if (toStatus.equals(EnumSCM.DL)) offlineCourierToStatus(orderReleaseID, EnumSCM.DL);
            else if (toStatus.equals(EnumSCM.LOST)) offlineCourierToStatus(orderReleaseID, EnumSCM.LOST);
            else if (toStatus.equals(EnumSCM.RTO)) offlineCourierToStatus(orderReleaseID, EnumSCM.RTO);
        }
    }


    /**
     * markTripLostInDC
     *
     * @param releaseId
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     */
    public void markTripLostInDC(String releaseId) throws  IOException, JAXBException {
        Assert.assertEquals(markOrderLOSTINDC(releaseId),EnumSCM.SUCCESS);
    }

    /**
     * markUnassignedToRTO
     *
     * @param orderReleaseID
     * @param warehouseID
     * @param pincode
     * @param trackingNo
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    @SuppressWarnings("unchecked")
    private void markUnassignedToRTO( String orderReleaseID, String warehouseID, String pincode,
                                     String trackingNo) throws IOException, JAXBException, InterruptedException, ManagerException, XMLStreamException, JSONException {
        String confirmRTOResponse = mlShipmentUpdate(trackingNo, ((OrderResponse) getOrderLMS.apply(orderReleaseID)).getOrders().get(0).getDeliveryCenterId(), null, EnumSCM.RTO_CONFIRMED, EnumSCM.DL);
        APIUtilities.validateResponse("json", confirmRTOResponse, "mlShipmentResponse.status.statusType=='SUCCESS'");
        Assert.assertTrue(validateOrderStatusInLMS(orderReleaseID,EnumSCM.RTO_CONFIRMED,3), "Order not marked RTO in order_to_ship");

        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID,EnumSCM.RTO,5), "Order not marked RTO in Order_release");
    }

    /**
     * Mark Un Assigned RTO
     * @param lmsOrderEntries
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     * @throws ManagerException
     */
    @SuppressWarnings("unused")
	private void markUnassignedToRTOv1(LMSOrderEntries lmsOrderEntries) throws IOException, JAXBException,
            InterruptedException, ManagerException {

        for (LMSOrderEntry lmsOrderEntry: lmsOrderEntries.getOrderEntries()) {
            String confirmRTOResponse = mlShipmentUpdate(lmsHelper.getTrackingNumber(lmsOrderEntry.getOrderID()), lmsOrderEntries.getDcID(), null, EnumSCM.RTO_CONFIRMED, EnumSCM.DL);

            if(!APIUtilities.getElement(confirmRTOResponse, "mlShipmentResponse.status.statusType", "json").equals("SUCCESS")){
                throw new ManagerException(APIUtilities.getElement(confirmRTOResponse, "mlShipmentResponse.status.statusMessage", "json").toString(), 2001);
            }

            if(!validateOrderStatusInLMS(lmsOrderEntry.getOrderID(),EnumSCM.RTO_CONFIRMED,3)){
                throw new ManagerException("Order not marked RTO in order_to_ship", 2001);
            }
        }

    }

    /**
     * mlShipmentUpdate
     *
     * @param trackingNumber
     * @param dcId
     * @param tripId
     * @param event
     * @param shipmentType
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public String mlShipmentUpdate(String trackingNumber, long dcId, String tripId, String event, String shipmentType) throws JAXBException, UnsupportedEncodingException {
        String payload = lmsHelper.createPayloadMLShipmentUpdate(trackingNumber, dcId, "" + tripId, event, shipmentType);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.ML_SHIPMENT_UPDATE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderJSON());
        log.info(service.getResponseBody());
        return service.getResponseBody();
    }

    /**
     * markLostAfterTrip
     * @param orderReleaseID
     * @param tripOrderAssignmentId
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    private void markLostAfterTrip(String orderReleaseID, String tripOrderAssignmentId)
            throws JAXBException, IOException {
        Assert.assertEquals(updateOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Update order In trip failed in LMS");
        Assert.assertEquals(updateOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.TRIP_COMPLETE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Trip complete failed in LMS");
        Assert.assertEquals(markOrderLOSTINDC(orderReleaseID),EnumSCM.SUCCESS);
    }

    /**
     * markTripFDFDDL
     *
     * @param orderReleaseID
     * @param pincode
     * @param tripOrderAssignmentId
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    private void markTripFDFDDL(String orderReleaseID, String pincode, String tripOrderAssignmentId)
            throws IOException, JAXBException, InterruptedException, JSONException, XMLStreamException, ManagerException {
        TripOrderAssignmentResponse response = updateOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.UPDATE);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Update order In trip failed in LMS");
        TripOrderAssignmentResponse responseComplete = updateOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(responseComplete.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Trip complete failed in LMS");
        requeueOrder("" + orderReleaseID);
        Map<String, String> fdTrip = processOrderInLMSFromSHToTripCreation(orderReleaseID, pincode);
        String fdtripOrderAssignmentId = fdTrip.get("tripOrderAssignmentId");
        TripOrderAssignmentResponse response1 = updateOrderInTrip(Long.parseLong(fdtripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.UPDATE);
        Assert.assertEquals(response1.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Update order In trip failed in LMS");
        TripOrderAssignmentResponse responsefd = updateOrderInTrip(Long.parseLong(fdtripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(responsefd.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Trip complete failed in LMS");
        requeueOrder("" + orderReleaseID);
        Map<String, String> newTrip = processOrderInLMSFromSHToTripCreation(orderReleaseID, pincode);
        String newtripOrderAssignmentId = newTrip.get("tripOrderAssignmentId");
        TripOrderAssignmentResponse newresponse = updateOrderInTrip(Long.parseLong(newtripOrderAssignmentId), EnumSCM.DELIVERED, EnumSCM.UPDATE);
        Assert.assertEquals(newresponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        TripOrderAssignmentResponse response2 = updateOrderInTrip(Long.parseLong(newtripOrderAssignmentId), EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(response2.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
        assertTrue(validateOrderStatusInLMS(orderReleaseID, EnumSCM.DELIVERED, waitTime), "Update trip order api failed for marking Delivered in LMS");
        assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID, "DL:C", waitTime), "Update OMS api failed for marking Delivered in OMS");
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Update order In trip failed in LMS");
        SlackMessenger.send("scm_e2e_order_sanity", "Order delivered");
    }

    /**
     * Mark Trip Delivered After Failed or two Failed Delivery
     * @param lmsOrderEntry
     * @param dcID
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     * @throws ManagerException
     */
    @SuppressWarnings("unchecked")
    private void markOrderFDAfterFD(LMSOrderEntry lmsOrderEntry, Long dcID)
            throws IOException, JAXBException, InterruptedException, ManagerException {

        long deliveryStaffID = Long.parseLong(getDeliveryStaffID(dcID.toString()));
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "myntra_lms");

        TripResponse tripResponse = createTrip(dcID, deliveryStaffID);
        ExceptionHandler.handleError(tripResponse.getStatus());

        long tripId = tripResponse.getTrips().get(0).getId();
        TripOrderAssignmentResponse tripOrderAssignmentResponse = addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(lmsOrderEntry.getOrderID()));
        ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());

        tripOrderAssignmentResponse = startTrip("" + tripId, "10");
        ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());

        Long tripOrderAssignmentId = (Long) lmsHelper.getTripOrderAssignemntIdForOrder.apply(lmsOrderEntry.getOrderID(), tripId);

        TripOrderAssignmentResponse response = updateOrderInTrip(tripOrderAssignmentId, EnumSCM.FAILED, EnumSCM.UPDATE);
        ExceptionHandler.handleError(response.getStatus());
        TripOrderAssignmentResponse responseComplete = updateOrderInTrip(tripOrderAssignmentId, EnumSCM.FAILED, EnumSCM.TRIP_COMPLETE);
        ExceptionHandler.handleError(responseComplete.getStatus());
    }


    /**
     * Mark Trip Delivered After Failed or two Failed Delivery
     * @param lmsOrderEntry
     * @param dcID
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     * @throws ManagerException
     */
    @SuppressWarnings("unchecked")
    private void markOrderDLAfterFDOrFDFDV1(LMSOrderEntry lmsOrderEntry, Long dcID)
            throws IOException, JAXBException, InterruptedException, ManagerException {

        long deliveryStaffID = Long.parseLong(getDeliveryStaffID(dcID.toString()));
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "myntra_lms");

        TripResponse tripResponse = createTrip(dcID, deliveryStaffID);
        ExceptionHandler.handleError(tripResponse.getStatus());

        long tripId = tripResponse.getTrips().get(0).getId();
        TripOrderAssignmentResponse tripOrderAssignmentResponse = addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(lmsOrderEntry.getOrderID()));
        ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());

        tripOrderAssignmentResponse = startTrip("" + tripId, "10");
        ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());

        Long tripOrderAssignmentId = (Long) lmsHelper.getTripOrderAssignemntIdForOrder.apply(lmsOrderEntry.getOrderID(), tripId);

        TripOrderAssignmentResponse response = updateOrderInTrip(tripOrderAssignmentId, EnumSCM.DELIVERED, EnumSCM.UPDATE);
        ExceptionHandler.handleError(response.getStatus());
        TripOrderAssignmentResponse responseComplete = updateOrderInTrip(tripOrderAssignmentId, EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE);
        ExceptionHandler.handleError(responseComplete.getStatus());
    }

    /**
     * markTripFDDL
     *
     * @param orderReleaseID
     * @param tripOrderAssignmentId
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    private void markTripFDDL(String orderReleaseID, String tripOrderAssignmentId)
            throws IOException, JAXBException, InterruptedException, JSONException, XMLStreamException, ManagerException {
        TripOrderAssignmentResponse response = updateOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.UPDATE);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Update order In trip failed in LMS");
        TripOrderAssignmentResponse responseComplete = updateOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(responseComplete.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Trip complete failed in LMS");
        requeueOrder("" + orderReleaseID);
        Map<String, String> newTrip = processOrderInLMSFromSHToTripCreation(orderReleaseID, "FDDL");
        String newtripOrderAssignmentId = newTrip.get("tripOrderAssignmentId");
        TripOrderAssignmentResponse newresponse = updateOrderInTrip(Long.parseLong(newtripOrderAssignmentId), EnumSCM.DELIVERED, EnumSCM.UPDATE);
        Assert.assertEquals(newresponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        TripOrderAssignmentResponse response2 = updateOrderInTrip(Long.parseLong(newtripOrderAssignmentId), EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(response2.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
        assertTrue(validateOrderStatusInLMS(orderReleaseID, EnumSCM.DELIVERED, waitTime), "Update trip order api failed for marking Delivered in LMS");
        assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID, "DL:C", waitTime), "Update OMS api failed for marking Delivered in OMS");
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Update order In trip failed in LMS");
        SlackMessenger.send("scm_e2e_order_sanity", "Order delivered");
    }

    /**
     * markTripFD
     * @param tripOrderAssignmentId
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    public void markTripFD(String releaseId ,String tripOrderAssignmentId) throws  IOException, JAXBException, InterruptedException {
        TripOrderAssignmentResponse response = updateOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.UPDATE);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Update order In trip failed in LMS");
        TripOrderAssignmentResponse responseComplete = updateOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(responseComplete.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Trip complete failed in LMS");
        Assert.assertTrue(validateOrderStatusInLMS(releaseId, EnumSCM.FAILED_DELIVERY, 3), "Order status is not in FAILED_DELIVERY in order_to_ship");
    }

    /**
     * markTripFDTODL
     *
     * @param orderReleaseID
     * @param tripOrderAssignmentId
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    private void markTripFDTODL(String orderReleaseID, String tripOrderAssignmentId)
            throws  IOException, JAXBException, InterruptedException {
        TripOrderAssignmentResponse response = updateOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.UPDATE);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Update order In trip failed in LMS");
        TripOrderAssignmentResponse newresponse = updateOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.DELIVERED, EnumSCM.UPDATE);
        Assert.assertEquals(newresponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        TripOrderAssignmentResponse response2 = updateOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(response2.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
        assertTrue(validateOrderStatusInLMS(orderReleaseID, EnumSCM.DELIVERED, waitTime), "Update trip order api failed for marking Delivered in LMS");
        assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID, "DL:C", waitTime), "Update OMS api failed for marking Delivered in OMS");
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Update order In trip failed in LMS");
        SlackMessenger.send("scm_e2e_order_sanity", "Order delivered");
    }

    /**
     * markTripRTO
     *
     * @param orderReleaseID
     * @param tripOrderAssignmentId
     * @param trackingNumber
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    private void markTripRTO(String orderReleaseID, String tripOrderAssignmentId,
                             String trackingNumber)
            throws  IOException, JAXBException {
        TripOrderAssignmentResponse response = updateOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.UPDATE);
        if (response.getStatus().getStatusType() == StatusResponse.Type.ERROR) {
            SlackMessenger.send("scm_e2e_order_sanity", "`Mark Order RTO Failed " + response.getStatus().getStatusMessage() + "`", 3);
        }
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Update order In trip failed in LMS");
        TripOrderAssignmentResponse responseComplete = updateOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(responseComplete.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Trip complete failed in LMS");
        OrderResponse rtoResponse = markOrderRto(orderReleaseID, trackingNumber);
        if (rtoResponse.getStatus().getStatusType() == StatusResponse.Type.ERROR) {
            SlackMessenger.send("scm_e2e_order_sanity", "`Mark Order RTO Failed " + rtoResponse.getStatus().getStatusMessage() + "`", 3);
            Assert.fail("Unable to update status to RTO(Mark order RTO failing in lms)");
        }
        SlackMessenger.send("scm_e2e_order_sanity", "Order Marked RTO");
        Assert.assertTrue(validateOrderStatusInLMS(orderReleaseID,EnumSCM.RTO_CONFIRMED,3), "Order not marked RTO in order_to_ship");
        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID,EnumSCM.RTO,5), "Order not marked RTO in Order_release");
    }

    /**
     * markTripSMDL
     *
     * @param orderReleaseID
     * @param tripOrderAssignmentId
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     * @throws InterruptedException
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    private void markTripSMDL(String orderReleaseID, String tripOrderAssignmentId) throws JAXBException, InterruptedException, IOException {
        TripOrderAssignmentResponse response = selfMarkDL(orderReleaseID);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders via self mark");
        Thread.sleep(2000);
        TripOrderAssignmentResponse response1 = updateOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE);
        if (response1.getStatus().getStatusType() == StatusResponse.Type.ERROR) {
            SlackMessenger.send("scm_e2e_order_sanity", "`Update Order in Trip Failed For TRIP_COMPLETE event" + response.getStatus().getStatusMessage() + "`", 3);
        }
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
        assertTrue(validateOrderStatusInLMS(orderReleaseID, EnumSCM.DELIVERED, waitTime), "Update trip order api failed for marking Delivered in LMS");
        assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID, "DL:C", waitTime), "Update OMS api failed for marking Delivered in OMS");
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Update order In trip failed in LMS");
        SlackMessenger.send("scm_e2e_order_sanity", "Order delivered");
    }

    /**
     * markTripDL
     *
     * @param orderReleaseID
     * @param tripOrderAssignmentId
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    private void markTripDL(String orderReleaseID, String tripOrderAssignmentId)
            throws  IOException, JAXBException, InterruptedException {
        TripOrderAssignmentResponse response = updateOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.DELIVERED, EnumSCM.UPDATE);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        TripOrderAssignmentResponse response1 = updateOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE);
        if (response1.getStatus().getStatusType() == StatusResponse.Type.ERROR) {
            SlackMessenger.send("scm_e2e_order_sanity", "`Update Order in Trip Failed For TRIP_COMPLETE event" + response.getStatus().getStatusMessage() + "`", 3);
        }
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
        assertTrue(validateOrderStatusInLMS(orderReleaseID, EnumSCM.DELIVERED, waitTime), "Update trip order api failed for marking Delivered in LMS");
        assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID, "DL:C", waitTime), "Update OMS api failed for marking Delivered in OMS");
        SlackMessenger.send("scm_e2e_order_sanity", "Order delivered");
    }

    /**
     * processOrderInLMSFromSHToDL
     *
     * @param orderReleaseID
     * @param toStatus
     * @param skuAndStatus
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     * @throws XMLStreamException
     * @throws JSONException
     */
    public void processOrderInLMSFromSHToDL(String orderReleaseID, String toStatus, HashMap<String, String> skuAndStatus) throws XMLStreamException, JAXBException, JSONException, IOException, InterruptedException, ManagerException {
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        Map<String, String> data = processOrderInLMSFromSHToTripCreation(orderReleaseID, toStatus);
        // update Trip Order
        String tripOrderAssignmentId = data.get("tripOrderAssignmentId");
        String trackingNumber = data.get("trackingNumber");
        String tripId = data.get("tripId");
        switch (toStatus) {
            case EnumSCM.DL:
                todToStatusDL(orderReleaseID, skuAndStatus, lmsServiceHelper, tripOrderAssignmentId);
                break;
            case EnumSCM.RTO:
                todToStatusRTO(orderReleaseID, lmsServiceHelper, tripOrderAssignmentId, trackingNumber);
                break;
            case EnumSCM.LOST:
                todToStatusLost(orderReleaseID, tripOrderAssignmentId);
                break;
            case EnumSCM.FDDL:
                todToStatusFDDL(orderReleaseID, skuAndStatus, lmsServiceHelper, tripOrderAssignmentId);
            default:
                log.info("Please pass the proper Status to process");
                break;
        }
        // Set Odomoter
        lmsServiceHelper.updateEndOdometerReading(tripId);
        log.info("Your order processing has been completed successfully\n OrderId: " + orderReleaseID);
    }

    /**
     * todToStatusLost
     *
     * @param orderReleaseID
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    private void todToStatusLost(String orderReleaseID, String tripOrderAssignmentId)
            throws InterruptedException, JAXBException, IOException {
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        TripOrderAssignmentResponse responseUpdate = lmsServiceHelper.updateTODOrderInTripFailed(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.UPDATE);
        Assert.assertEquals(responseUpdate.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Update order In trip failed in LMS");
        TripOrderAssignmentResponse response = lmsServiceHelper.updateTODOrderInTripFailed(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close trip");
        assertTrue(validateOrderStatusInLMS(orderReleaseID, EnumSCM.FAILED_DELIVERY, waitTime), "Update trip order api failed for marking Failed Delivery in LMS");
        markOrderLOSTINDC(orderReleaseID);
    }

    /**
     * todToStatusRTO
     *
     * @param orderReleaseID
     * @param lmsServiceHelper
     * @param tripOrderAssignmentId
     * @param trackingNumber
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    private void todToStatusRTO(String orderReleaseID, LmsServiceHelper lmsServiceHelper, String tripOrderAssignmentId,
                                String trackingNumber)
            throws  IOException, JAXBException {
        TripOrderAssignmentResponse responseUpdate = lmsServiceHelper.updateTODOrderInTripFailed(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.UPDATE);
        Assert.assertEquals(responseUpdate.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Update order In trip failed in LMS");
        TripOrderAssignmentResponse response = lmsServiceHelper.updateTODOrderInTripFailed(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close trip");
        OrderResponse rtoResponse = lmsServiceHelper.markOrderRto(orderReleaseID, trackingNumber);
        Assert.assertEquals(rtoResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update status to RTO(Mark order RTO failing in lms)");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(orderReleaseID,EnumSCM.RTO_CONFIRMED,3), "Order not RTO_Confirmed in Order_to_ship");
        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID,EnumSCM.RTO,3),"Order not RTO in OMS");
    }

    /**
     * todToStatusDL
     *
     * @param orderReleaseID
     * @param skuAndStatus
     * @param lmsServiceHelper
     * @param tripOrderAssignmentId
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    private void todToStatusDL(String orderReleaseID, HashMap<String, String> skuAndStatus,
                               LmsServiceHelper lmsServiceHelper, String tripOrderAssignmentId)
            throws  IOException, JAXBException, InterruptedException {
        //long tripOrderAssignmentId, String status, String tripAction, long orderId, HashMap<String, String> inputskuIDs
        TripOrderAssignmentResponse response = lmsServiceHelper.updateTODOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.DELIVERED, EnumSCM.UPDATE, orderReleaseID, skuAndStatus);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        TripOrderAssignmentResponse response1 = lmsServiceHelper.updateTODOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE, orderReleaseID, skuAndStatus);
        if (!response1.getStatus().getStatusType().toString().equals(EnumSCM.SUCCESS)) {
            SlackMessenger.send("scm_e2e_order_sanity", "Unable to complete trip", 3);
        } else {
            SlackMessenger.send("scm_e2e_order_sanity", "trip completed successfully");
        }
        Assert.assertEquals(response1.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
        assertTrue(validateOrderStatusInLMS(orderReleaseID, EnumSCM.DELIVERED, waitTime), "Update trip order api failed for marking Delivered in LMS");
        assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID, "DL:C", waitTime), "Update OMS api failed for marking Delivered in OMS");
        SlackMessenger.send("scm_e2e_order_sanity", "Order delivered");
    }

    /**
     * todToStatusFDDL
     *
     * @param orderReleaseID
     * @param skuAndStatus
     * @param lmsServiceHelper
     * @param tripOrderAssignmentId
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    private void todToStatusFDDL(String orderReleaseID, HashMap<String, String> skuAndStatus,
                                 LmsServiceHelper lmsServiceHelper, String tripOrderAssignmentId)
            throws InterruptedException, JAXBException, IOException, JSONException, XMLStreamException, ManagerException {
        //long tripOrderAssignmentId, String status, String tripAction, long orderId, HashMap<String, String> inputskuIDs
        //Mark Failed Delivery
        TripOrderAssignmentResponse response = lmsServiceHelper.updateTODOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.UPDATE, orderReleaseID, skuAndStatus);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update FD orders in Trip.");
        Thread.sleep(2000);
        TripOrderAssignmentResponse response1 = lmsServiceHelper.updateTODOrderInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.FAILED, EnumSCM.TRIP_COMPLETE, orderReleaseID, skuAndStatus);
        Assert.assertEquals(response1.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete FD Trip.");
        assertTrue(validateOrderStatusInLMS(orderReleaseID, EnumSCM.FAILED_DELIVERY, waitTime), "Update trip order api failed for marking Failed Delivery in LMS");
        requeueOrder("" + orderReleaseID);
        //Mark DL
        Map<String, String> newTrip = processOrderInLMSFromSHToTripCreation(orderReleaseID, "FDDL");
        String newtripOrderAssignmentId = newTrip.get("tripOrderAssignmentId");
        TripOrderAssignmentResponse response3 = lmsServiceHelper.updateTODOrderInTrip(Long.parseLong(newtripOrderAssignmentId), EnumSCM.DELIVERED, EnumSCM.UPDATE, orderReleaseID, skuAndStatus);
        Assert.assertEquals(response3.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        TripOrderAssignmentResponse response4 = lmsServiceHelper.updateTODOrderInTrip(Long.parseLong(newtripOrderAssignmentId), EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE, orderReleaseID, skuAndStatus);
        Assert.assertEquals(response4.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
        assertTrue(validateOrderStatusInLMS(orderReleaseID, EnumSCM.DELIVERED, waitTime), "Update trip order api failed for marking Delivered in LMS");
        assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID, "DL:C", waitTime), "Update OMS api failed for marking Delivered in OMS");
    }

    /**
     * processOrderInLMSTillAddedToMB
     * @param releaseID
     * @param warehouseID
     * @param trackingNumber
     * @param dcId
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
    public long processOrderInLMSTillAddedToMB(String releaseID, String warehouseID, String trackingNumber, long dcId) throws Exception {
        OrderEntry order = ((OrderResponse)getOrderLMS.apply(releaseID)).getOrders().get(0);
        ShipmentType shipmentType = ShipmentType.DL;
        if (order.getShipmentType().toString().equals(EnumSCM.TRY_AND_BUY)) {
            shipmentType = ShipmentType.TRY_AND_BUY;
        }
        ShippingMethod shippingMethod = ShippingMethod.NORMAL;

        if (order.getShippingMethod().toString().equals(ShippingMethod.EXPRESS.toString())) {
            shippingMethod = ShippingMethod.EXPRESS;
        } else if (order.getShippingMethod().toString().equals(ShippingMethod.SDD.toString())) {
            shippingMethod = ShippingMethod.SDD;
        }

        if (!lmsHelper.getCourierCodeOfRelease.apply(releaseID).toString().equals("ML")) DBUtilities.exUpdateQuery("update order_tracking set courier_creation_status = 'ACCEPTED' where order_id = '" + releaseID+"'", "lms");

        Assert.assertEquals(orderInScanNew(releaseID, warehouseID), EnumSCM.SUCCESS, "Unable to Inscan order in WH");
        log.debug("Order Release ID : " + releaseID);
        Assert.assertEquals(lmsHelper.getOrderToShipStatus(releaseID), EnumSCM.INSCANNED, "Shipment Staus is not INSCANNED in LMS DB");
        if (order.getCourierOperator().toString().equals("ML"))
            Assert.assertEquals(lmsHelper.getMLShipmentStatus(releaseID), EnumSCM.EXPECTED_IN_DC, "Shipment Staus is not ML shipment in LMS DB");
        ShipmentResponse createMasterBagRes = createMasterBag(dcId, warehouseID, shippingMethod);
        Assert.assertEquals(createMasterBagRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create MaterBag");
        log.info("Order inscanned in MB=" + createMasterBagRes.getEntries().get(0).getId().toString());
        long masterBagId = (long) createMasterBagRes.getEntries().get(0).getId();
        String saveMBResponse = addAndSaveMasterBag(releaseID, "" + masterBagId, shipmentType);
        Assert.assertEquals(saveMBResponse, EnumSCM.SUCCESS, "Unable to save masterBag");
        Assert.assertEquals(lmsHelper.getOrderToShipStatus(releaseID), EnumSCM.ADDED_TO_MB, "Shipment Staus is not ADDED_TO_MB in LMS DB");
        return masterBagId;
    }

    /**
     * processOrderInLMSTillSH
     *
     * @param releaseID
     * @param warehouseID
     * @return
     */
    @SuppressWarnings("unchecked")
    public void processOrderInLMSTillSH(String releaseID, String warehouseID, String toStatus) throws Exception {
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        TMSServiceHelper tmsServiceHelper = new TMSServiceHelper();
        long masterBagId = 0L;
        OrderResponse lmsOrder = (OrderResponse)getOrderLMS.apply(releaseID);
        String status = lmsHelper.getOrderToShipStatus(releaseID);
        if (status.matches(EnumSCM.DELIVERED+"|"+EnumSCM.LOST+"|"+EnumSCM.CANCELLED_IN_HUB+"|"+EnumSCM.SHIPPED))
            return;
        if (status.equals(EnumSCM.PACKED)) {
            String trackingNumber = lmsOrder.getOrders().get(0).getTrackingNumber();
            String courierCode = lmsOrder.getOrders().get(0).getCourierOperator();
            if (courierCode.equalsIgnoreCase("ML")) {
                long dcId = lmsOrder.getOrders().get(0).getDeliveryCenterId();
                if (lmsHelper.getOrderToShipStatus(releaseID).equals(EnumSCM.ADDED_TO_MB)){
                    if (toStatus.equalsIgnoreCase(EnumSCM.ADDED_TO_MB)){
                        return;
                    }
                }else {
                    masterBagId = processOrderInLMSTillAddedToMB(releaseID, warehouseID, trackingNumber, dcId);
                    if (toStatus.equalsIgnoreCase(EnumSCM.ADDED_TO_MB)) {
                        return;
                    }
                }
                masterBagId = getMasterBagID(releaseID);
                Assert.assertEquals(closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close masterBag");
                Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.CLOSED, "masterbag status is not updated in DB to `CLOSED`");
                Thread.sleep(2000);
                tmsServiceHelper.processInTMSFromClosedToLastMileInTransit.accept(masterBagId);
                assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseID, EnumSCM.SHIPPED, waitTime)," Checking the OrderStatus moved to SH in LMS");
                assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseID, EnumSCM.SH, waitTime)," Checking the OrderStatus moved to SH in OMS");
                ExceptionHandler.handleEquals(lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).
                        getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive shipment in DC");
                ShipmentResponse receiveOrderWithMasterBagRes = masterBagInScanUpdate(masterBagId, releaseID, "Bangalore", dcId, "DC", Long.parseLong(warehouseID));
                log.info("Ship Master Bag response : " + receiveOrderWithMasterBagRes.toString());
                Assert.assertEquals(receiveOrderWithMasterBagRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive master bag in DC");
                Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId),EnumSCM.RECEIVED,"Masterbag status is not update in DB to `RECEIVED`");
                Assert.assertEquals(lmsHelper.getMLShipmentStatus(releaseID), EnumSCM.UNASSIGNED, "Shipment Staus is not update to UNASSIGENED in ML shipment2");
            }else if (courierCode.equalsIgnoreCase("EK")) {
                if (lmsHelper.getOrderToShipStatus(releaseID).equals(EnumSCM.ADDED_TO_MB)){
                    if (toStatus.equalsIgnoreCase(EnumSCM.ADDED_TO_MB)){
                        return;
                    }
                }else {
                    DBUtilities.exUpdateQuery("update order_tracking set courier_creation_status = 'ACCEPTED' where order_id = '" + releaseID+"'", "lms");
                    Map<String, Object> deliveryCenter = DBUtilities.exSelectQueryForSingleRecord("select id from delivery_center where code = 'EKART' and name = 'EKART' and courier_code = 'EK'", "lms");
                    masterBagId = processOrderInLMSTillAddedToMB(releaseID, warehouseID, trackingNumber, (long)deliveryCenter.get("id"));
                    if (toStatus.equalsIgnoreCase(EnumSCM.ADDED_TO_MB)) {
                        return;
                    }
                }
                Assert.assertEquals(closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
                Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.CLOSED, "masterbag status is not updated in DB to `CLOSED`");
                Assert.assertEquals(shipMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
                Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.HANDED_OVER_TO_3PL, "MasterBag DB status is not updated to `HANDED_OVER_TO_3PL`");
                if (!lmsServiceHelper.validateOrderStatusInLMS(releaseID, EnumSCM.SHIPPED, waitTime))
                    SlackMessenger.send("scm_e2e_order_sanity", "Checking the OrderStatus moved to SH in LMS", 3);

                assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseID, EnumSCM.SH, waitTime), " Checking the OrderStatus moved to SH in OMS");
                assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseID, EnumSCM.SHIPPED, waitTime),
                        " Checking the OrderStatus moved to SH in LMS");

            } else if (courierCode.equalsIgnoreCase("DE")) {
                try {
                    Long dcId = lmsOrder.getOrders().get(0).getDeliveryCenterId();
                    Map<String, Object> deliveryCenter = DBUtilities.exSelectQueryForSingleRecord("select type from delivery_center where id =" + dcId, "lms");
                    if (deliveryCenter.get("type").toString().equals(EnumSCM.COURIER_HANDOVER)) {
                        masterBagId = processOrderInLMSForRegionalHandover(releaseID, toStatus);
                    }else {
                        throw new IOException();
                    }
                } catch (Exception e) {
                    if (lmsHelper.getOrderToShipStatus(releaseID).equals(EnumSCM.ADDED_TO_MB)){
                        if (toStatus.equalsIgnoreCase(EnumSCM.ADDED_TO_MB)){
                            return;
                        }
                        masterBagId = getMasterBagID(releaseID);
                        Assert.assertEquals(closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
                        Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.CLOSED, "masterbag status is not updated in DB to `CLOSED`");
                        Assert.assertEquals(shipMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
                        Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.HANDED_OVER_TO_3PL, "MasterBag DB status is not updated to `HANDED_OVER_TO_3PL`");
                        if (!lmsServiceHelper.validateOrderStatusInLMS(releaseID, EnumSCM.SHIPPED, waitTime))
                            SlackMessenger.send("scm_e2e_order_sanity", "Checking the OrderStatus moved to SH in LMS", 3);
                        assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseID, EnumSCM.SH, waitTime)," Checking the OrderStatus moved to SH in OMS");
                        assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseID, EnumSCM.SHIPPED, waitTime),
                                " Checking the OrderStatus moved to SH in LMS");
                        return;
                    }else {
                        DBUtilities.exUpdateQuery("update order_tracking set courier_creation_status = 'ACCEPTED' where order_id = '" + releaseID+"'", "lms");
                        Thread.sleep(3000);
                        Map<String, Object> deliveryCenterDE = DBUtilities.exSelectQueryForSingleRecord("select id from delivery_center where code = 'DE' and type = 'OTHER_LOGISTICS'", "lms");
                        masterBagId = processOrderInLMSTillAddedToMB(releaseID, warehouseID, trackingNumber, (long)deliveryCenterDE.get("id"));
                        if (toStatus.equalsIgnoreCase(EnumSCM.ADDED_TO_MB)){
                            return;
                        }
                        Assert.assertEquals(closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
                        Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.CLOSED, "masterbag status is not updated in DB to `CLOSED`");
                        Assert.assertEquals(shipMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
                        Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.HANDED_OVER_TO_3PL, "MasterBag DB status is not updated to `HANDED_OVER_TO_3PL`");
                        if (!lmsServiceHelper.validateOrderStatusInLMS(releaseID, EnumSCM.SHIPPED, waitTime))
                            SlackMessenger.send("scm_e2e_order_sanity", "Checking the OrderStatus moved to SH in LMS", 3);

                        assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseID, EnumSCM.SH, waitTime), " Checking the OrderStatus moved to SH in OMS");
                        assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseID, EnumSCM.SHIPPED, waitTime),
                                " Checking the OrderStatus moved to SH in LMS");
                    }
                }
            }else {
                String currentStatus = lmsHelper.getOrderToShipStatus(releaseID);
                    if (currentStatus.equals(EnumSCM.ADDED_TO_MB)){
                        if (toStatus.equalsIgnoreCase(EnumSCM.ADDED_TO_MB)){
                            return;
                        }
                    }else {
                        Map<String, Object> deliveryCenter = DBUtilities.exSelectQueryForSingleRecord("select id from delivery_center where courier_code = '"+courierCode+"' and type = 'OTHER_LOGISTICS'", "lms");
                        masterBagId = processOrderInLMSTillAddedToMB(releaseID, warehouseID, trackingNumber, (long)deliveryCenter.get("id"));
                        if (toStatus.equalsIgnoreCase(EnumSCM.ADDED_TO_MB)) {
                            return;
                        }
                    }
                    masterBagId = getMasterBagID(releaseID);
                    Assert.assertEquals(closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
                    Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.CLOSED, "masterbag status is not updated in DB to `CLOSED`");
                    Assert.assertEquals(shipMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
                    Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.HANDED_OVER_TO_3PL, "MasterBag DB status is not updated to `HANDED_OVER_TO_3PL`");
                    if (!lmsServiceHelper.validateOrderStatusInLMS(releaseID, EnumSCM.SHIPPED, waitTime))
                        SlackMessenger.send("scm_e2e_order_sanity", "Checking the OrderStatus moved to SH in LMS", 3);
                    assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseID, EnumSCM.SH, waitTime)," Checking the OrderStatus moved to SH in OMS");
                    assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseID, EnumSCM.SHIPPED, waitTime),
                            " Checking the OrderStatus moved to SH in LMS");
                }
        }
    }

    /**
     * processOrderInLMSForRegionalHandover
     *
     * @param releaseID
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public long processOrderInLMSForRegionalHandover(String releaseID, String toStatus)
            throws JAXBException, IOException, JSONException, XMLStreamException, InterruptedException, ManagerException {
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OrderResponse orderResponse = (OrderResponse) getOrderLMS.apply(releaseID);
        long masterBagId = 0L;
        long warehouseID = Long.parseLong(orderResponse.getOrders().get(0).getWarehouseId());
        long dcId = orderResponse.getOrders().get(0).getDeliveryCenterId();
        String courierCode = "DE";

        if (!lmsServiceHelper.validateOrderStatusInLMS(releaseID, EnumSCM.SH, 2) || lmsServiceHelper.validateOrderStatusInLMS(releaseID, EnumSCM.PK, 2)) {
            TMSServiceHelper tmsServiceHelper = new TMSServiceHelper();
            ShipmentType shipmentType = ShipmentType.DL;

            if (lmsHelper.getOrderToShipStatus(releaseID).equals(EnumSCM.ADDED_TO_MB)){
                if (lmsHelper.getOrderToShipStatus(releaseID).equals(EnumSCM.ADDED_TO_MB)) {
                    if (toStatus.equalsIgnoreCase(EnumSCM.ADDED_TO_MB)) {
                        return 0L;
                    }
                }
                masterBagId = getMasterBagID(releaseID);
                ShipmentResponse closeMBResponse = closeMasterBag(masterBagId);
                Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.CLOSED, "MasterBag DB status is not updated to `CLOSED`");
                Assert.assertEquals(closeMBResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close masterBag");
                tmsServiceHelper.processInTMSFromClosedToLastMileInTransit.accept(masterBagId);
                ExceptionHandler.handleEquals(lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).
                        getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive shipment in DC");
                Assert.assertEquals(masterBagInScanUpdate(masterBagId, releaseID, "DC-Delhi", dcId, "DC", warehouseID, ShipmentStatus.RECEIVED_AT_HANDOVER_CENTER, OrderShipmentAssociationStatus.RECEIVED_AT_HANDOVER_CENTER).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
                Map<String, Object> shipment2 = DBUtilities.exSelectQueryForSingleRecord("select * from shipment where id = " + masterBagId, "lms");
                Assert.assertEquals(shipment2.get("status").toString(), EnumSCM.RECEIVED_AT_HANDOVER_CENTER);
                Assert.assertEquals(shipment2.get("last_scanned_premises_type").toString(), "DC");
                Assert.assertEquals(shipment2.get("last_scanned_premises_id").toString(), "" + dcId);
                if (!lmsServiceHelper.validateOrderStatusInLMS(releaseID, EnumSCM.SHIPPED, waitTime))
                    assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseID, EnumSCM.SH, waitTime), " Checking the OrderStatus moved to SH in OMS");
                assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseID, EnumSCM.SHIPPED, waitTime),
                        " Checking the OrderStatus moved to SH in LMS");

                if (lmsHelper.getOrderToShipStatus(releaseID).equals(EnumSCM.SH)) {
                    if (toStatus.equalsIgnoreCase(EnumSCM.SH)) {
                        return 0L;
                    }
                }
            }else {
                if (orderResponse.getOrders().get(0).getShipmentType().toString().equals(EnumSCM.TRY_AND_BUY)) {
                    shipmentType = ShipmentType.TRY_AND_BUY;
                }
                ShippingMethod shippingMethod = ShippingMethod.NORMAL;

                if (orderResponse.getOrders().get(0).getShippingMethod().toString().equals(EnumSCM.EXPRESS)) {
                    shippingMethod = ShippingMethod.EXPRESS;
                } else if (orderResponse.getOrders().get(0).getShippingMethod().toString().equals(EnumSCM.SDD)) {
                    shippingMethod = ShippingMethod.SDD;
                }
                DBUtilities.exUpdateQuery("update order_tracking set courier_creation_status = 'ACCEPTED' where order_id = '" + releaseID+"'", "lms");

                Assert.assertEquals(orderInScanNew(releaseID, "" + warehouseID), EnumSCM.SUCCESS, "Unable to Inscan order in WH");
                log.debug("Order Release ID : " + releaseID);
                Assert.assertEquals(lmsHelper.getOrderToShipStatus(releaseID), EnumSCM.INSCANNED, "Shipment Staus is not INSCANNED in LMS DB");
                ShipmentResponse createMasterBagRes = createMasterBag(warehouseID, "WH", dcId, "DC", "" + shippingMethod, courierCode);
                Assert.assertEquals(createMasterBagRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create MaterBag");
                log.info("Order inscanned in MB=" + createMasterBagRes.getEntries().get(0).getId().toString());
                masterBagId = createMasterBagRes.getEntries().get(0).getId();
                String saveMBResponse = addAndSaveMasterBag(releaseID, "" + masterBagId, shipmentType);
                Assert.assertEquals(saveMBResponse, EnumSCM.SUCCESS, "Unable to save masterBag");
                Assert.assertEquals(lmsHelper.getOrderToShipStatus(releaseID), EnumSCM.ADDED_TO_MB, "Shipment Staus is not ADDED_TO_MB in LMS DB");
                if (lmsHelper.getOrderToShipStatus(releaseID).equals(EnumSCM.ADDED_TO_MB)) {
                    if (toStatus.equalsIgnoreCase(EnumSCM.ADDED_TO_MB)) {
                        return 0L;
                    }
                }
                ShipmentResponse closeMBResponse = closeMasterBag(masterBagId);
                Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.CLOSED, "MasterBag DB status is not updated to `CLOSED`");
                Assert.assertEquals(closeMBResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close masterBag");
                tmsServiceHelper.processInTMSFromClosedToLastMileInTransit.accept(masterBagId);
                ExceptionHandler.handleEquals(lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).
                        getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive shipment in DC");
                Assert.assertEquals(masterBagInScanUpdate(masterBagId, releaseID, "DC-Delhi", dcId, "DC", warehouseID, ShipmentStatus.RECEIVED_AT_HANDOVER_CENTER, OrderShipmentAssociationStatus.RECEIVED_AT_HANDOVER_CENTER).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
                Map<String, Object> shipment2 = DBUtilities.exSelectQueryForSingleRecord("select * from shipment where id = " + masterBagId, "lms");
                Assert.assertEquals(shipment2.get("status").toString(), EnumSCM.RECEIVED_AT_HANDOVER_CENTER);
                Assert.assertEquals(shipment2.get("last_scanned_premises_type").toString(), "DC");
                Assert.assertEquals(shipment2.get("last_scanned_premises_id").toString(), "" + dcId);
                if (!lmsServiceHelper.validateOrderStatusInLMS(releaseID, EnumSCM.SHIPPED, waitTime))
                    assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseID, EnumSCM.SH, waitTime), " Checking the OrderStatus moved to SH in OMS");
                assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseID, EnumSCM.SHIPPED, waitTime),
                        " Checking the OrderStatus moved to SH in LMS");
            }
        } else {
            masterBagId = getMasterBagID(releaseID);
        }
        return masterBagId;
    }

    /**
     * DelhiveryRHDToDL
     * @param releaseID
     * @param trackingNumber
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    public void DelhiveryRHDToDL(String releaseID, String trackingNumber ) throws IOException, JAXBException, InterruptedException {
        long masterBagId = getMasterBagID(releaseID);
        Assert.assertEquals(handoverToRegionalCourier(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to Handover masterbag to 3PL from regional handover DC");
        Map<String, Object> shipment3 = DBUtilities.exSelectQueryForSingleRecord("select * from shipment where id = " + masterBagId, "lms");
        Assert.assertEquals(shipment3.get("status").toString(), EnumSCM.HANDED_OVER_TO_3PL);
        CourierTrackingResponse ctResponse = updateDE_CTS(trackingNumber, "" + releaseID, "Bangalore", "UD", "Dispatched", "Out for delivery");
        Assert.assertEquals(ctResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(validateOrderStatusInLMS(releaseID,EnumSCM.OUT_FOR_DELIVERY,8));
        CourierTrackingResponse ctResponseDL = updateDE_CTS(trackingNumber, "" + releaseID, "Bangalore", EnumSCM.DL, EnumSCM.DELIVERED, "Shipment Delivered");
        Assert.assertEquals(ctResponseDL.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(validateOrderStatusInLMS(releaseID,EnumSCM.DELIVERED,8));
    }

    /**
     * DelhiveryToDL
     * @param releaseID
     * @param trackingNumber
     * @throws IOException
     * @throws InterruptedException
     */
    public void DelhiveryToDL(String releaseID,String trackingNumber) throws IOException, InterruptedException {
        CourierTrackingResponse ctResponse = updateDE_CTS(trackingNumber, "" + releaseID, "Bangalore", "UD", "Dispatched", "Out for delivery");
        Assert.assertEquals(ctResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(validateOrderStatusInLMS(releaseID,EnumSCM.OUT_FOR_DELIVERY,8));
        CourierTrackingResponse ctResponseDL = updateDE_CTS(trackingNumber, "" + releaseID, "Bangalore", EnumSCM.DL, EnumSCM.DELIVERED, "Shipment Delivered");
        Assert.assertEquals(ctResponseDL.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(validateOrderStatusInLMS(releaseID,EnumSCM.DELIVERED,10));
    }

    /**
     * updateDE_CTS
     *
     * @param trackingNumber
     * @param orderId
     * @param statusLocation
     * @param statusType
     * @param status
     * @param instructions
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public CourierTrackingResponse updateDE_CTS(String trackingNumber, String orderId, String statusLocation, String statusType, String status, String instructions) throws  IOException {
        String payload = "{\"Shipment\":{\"Status\":{\"Status\":\"" + status + "\",\"StatusDateTime\":\"" + getDate.get() + "\", \"StatusType\":\"" + statusType + "\", \"StatusLocation\""
                + ":\"" + statusLocation + "\",\"Instructions\":\"" + instructions + "\",\"lenght\": \"35.8\","
                + "\"Breadth\":\"28.5\",\"Height\":\"12.3\",\"Weight\":\"0\",\"ChargedWeight\":\"0\"},"
                + "\"PickUpDate\":\"" + getDate.get() + "\",\"NSLCode\":\"ED-100\",\"Sortcode\""
                + ":\"JDH/BID\",\"ReferenceNo\":\"" + orderId + "\",\"AWB\":\"" + trackingNumber + "\"}}";
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.CTS_DE_UPDATE, null, SERVICE_TYPE.CTS.toString(), HTTPMethods.POST, payload, Headers.getCTSHeader());
        CourierTrackingResponse courierTrackingResponse = (CourierTrackingResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new CourierTrackingResponse());
        return courierTrackingResponse;
    }

    /**
     * Validate OrderStatus in LMS order_to_ship table
     *
     * @param orderID
     * @param status
     * @param delaytoCheck
     * @return
     */
    public boolean validateOrderStatusInLMS(String orderID, String status, int delaytoCheck) {
        log.info("Validate Order Status in LMS order_to_ship table");
        boolean validateStatus = false;
        try {
            for (int i = 0; i < delaytoCheck; i++) {
            	
                String status_code = getOrderStatusFromLMS(orderID);
                if (status_code.equalsIgnoreCase(status) || status_code.equalsIgnoreCase(status)) {
                    validateStatus = true;
                    break;
                } else {
                    Thread.sleep(2000);
                    validateStatus = false;
                }

                log.info("waiting for Order Status in LMS" + status + " .current status=" + status_code + "\t " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            validateStatus = false;
        }
        return validateStatus;
    }

    /**
     * validateOrderStatusInML
     * @param orderID
     * @param status
     * @param delaytoCheck
     * @return
     */
    public boolean validateOrderStatusInML(String orderID, String status, int delaytoCheck) {
        log.info("Validate Order Status in LMS order_to_ship table");
        boolean validateStatus = false;
        try {
            for (int i = 0; i < delaytoCheck; i++) {
                String status_code = getOrderStatusFromML(orderID);
                if (status_code.equalsIgnoreCase(status) || status_code.equalsIgnoreCase(status)) {
                    validateStatus = true;
                    break;
                } else {
                    Thread.sleep(4000);
                    validateStatus = false;
                }

                log.info("waiting for Order Status in LMS" + status + " .current status=" + status_code + "\t " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            validateStatus = false;
        }
        return validateStatus;
    }


    /**
     * validateRmsLmsReturnCreation
     * @param returnId
     * @return
     */
    public boolean validateRmsLmsReturnCreation(String returnId) throws IOException {
        RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
      //  Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId, EnumSCM.PICKUP_CREATED,15),"Pickup is not created in LMS");
        if (rmsServiceHelper.getReturnDetailsNew(returnId).getData().get(0).getReturnMode().toString().equals(EnumSCM.SELF_SHIP))
            Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(returnId,EnumSCM.RRQS,15),"pickup is not created or not in RRQS status in Returns");
        else if(rmsServiceHelper.getReturnDetailsNew(returnId).getData().get(0).getReturnMode().toString().equals(EnumSCM.CLOSED_BOX_PICKUP))
            Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(returnId,EnumSCM.RRQP,15),"pickup is not created or not in RRQP status in Returns");
        else Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(returnId,EnumSCM.RPI,15),"pickup is not created or not in RPI status in Returns");
        Map<String,Object> returnShipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = "+returnId,"lms");
        ReturnResponse returnResponse = rmsServiceHelper.getReturnDetailsNew(returnId);
        Assert.assertEquals(returnShipment.get("return_warehouse_id").toString(), returnResponse.getData().get(0).getReturnAdditionalDetailsEntry().getIdealReturnWarehouse().toString(), "return_warehouse_id mismatch");
        Assert.assertEquals(returnShipment.get("return_type").toString(), returnResponse.getData().get(0).getReturnMode().toString(), "return_mode/return_type Mismatch in LMS-RMS");
        Assert.assertEquals(returnShipment.get("email").toString(), returnResponse.getData().get(0).getEmail().toString(), "email Mismatch in LMS-RMS");
        if (returnShipment.get("return_type").toString().equals(EnumSCM.OPEN_BOX_PICKUP))
            Assert.assertEquals(returnShipment.get("tracking_number").toString(), returnResponse.getData().get(0).getReturnTrackingDetailsEntry().getTrackingNo(), "TrackingNumber Mismatch in LMS-RMS");
        if (!returnShipment.get("return_type").toString().equals(EnumSCM.SELF_SHIP)) {
            Assert.assertEquals(returnShipment.get("courier_code").toString(), returnResponse.getData().get(0).getReturnTrackingDetailsEntry().getCourierCode(), "courierCode Mismatch in LMS-RMS");
            Assert.assertEquals(returnShipment.get("primary_contact_number").toString(), returnResponse.getData().get(0).getMobile().toString(), "country Mismatch in LMS-RMS");
            Assert.assertEquals(returnShipment.get("address").toString(), returnResponse.getData().get(0).getReturnAddressDetailsEntry().getAddress().toString(), "address Mismatch in LMS-RMS");
            Assert.assertEquals(returnShipment.get("city").toString(), returnResponse.getData().get(0).getReturnAddressDetailsEntry().getCity().toString(), "city Mismatch in LMS-RMS");
            Assert.assertEquals(returnShipment.get("country").toString(), returnResponse.getData().get(0).getReturnAddressDetailsEntry().getCountry().toString(), "return_mode Mismatch in LMS-RMS");
            Assert.assertEquals(returnShipment.get("pincode").toString(), returnResponse.getData().get(0).getReturnAddressDetailsEntry().getZipcode().toString(), "pincode Mismatch in LMS-RMS");
        }
        return true;
    }

    /**
     * validateRmsLmsReturnCreationWithPlateformOnhold
     * @param returnId
     * @return
     * @throws IOException
     */
    public boolean validateRmsLmsReturnCreationWithPlateformOnhold(String returnId) throws IOException {
        RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
       // Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId, EnumSCM.ONHOLD_PICKUP_WITH_PLATFORM,15),"Pickup is not created in LMS");
        if (rmsServiceHelper.getReturnDetailsNew(returnId).getData().get(0).getReturnMode().toString().equals(EnumSCM.SELF_SHIP))
            Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(returnId,EnumSCM.RRQS,15),"pickup is not created or not in RRQS status in Returns");
        else if(rmsServiceHelper.getReturnDetailsNew(returnId).getData().get(0).getReturnMode().toString().equals(EnumSCM.CLOSED_BOX_PICKUP))
            Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(returnId,EnumSCM.RRQP,15),"pickup is not created or not in RRQP status in Returns");
        else Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(returnId,EnumSCM.RPI,15),"pickup is not created or not in RPI status in Returns");
        Map<String,Object> returnShipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = "+returnId,"lms");
        ReturnResponse returnResponse = rmsServiceHelper.getReturnDetailsNew(returnId);
        Assert.assertEquals(returnShipment.get("return_warehouse_id").toString(), returnResponse.getData().get(0).getReturnAdditionalDetailsEntry().getIdealReturnWarehouse().toString(), "return_warehouse_id mismatch");
        Assert.assertEquals(returnShipment.get("return_type").toString(), returnResponse.getData().get(0).getReturnMode().toString(), "return_mode/return_type Mismatch in LMS-RMS");
        Assert.assertEquals(returnShipment.get("email").toString(), returnResponse.getData().get(0).getEmail().toString(), "email Mismatch in LMS-RMS");
        if (returnShipment.get("return_type").toString().equals(EnumSCM.OPEN_BOX_PICKUP))
            Assert.assertEquals(returnShipment.get("tracking_number").toString(), returnResponse.getData().get(0).getReturnTrackingDetailsEntry().getTrackingNo(), "TrackingNumber Mismatch in LMS-RMS");
        if (!returnShipment.get("return_type").toString().equals(EnumSCM.SELF_SHIP)) {
            Assert.assertEquals(returnShipment.get("courier_code").toString(), returnResponse.getData().get(0).getReturnTrackingDetailsEntry().getCourierCode(), "courierCode Mismatch in LMS-RMS");
            Assert.assertEquals(returnShipment.get("primary_contact_number").toString(), returnResponse.getData().get(0).getMobile().toString(), "country Mismatch in LMS-RMS");
            Assert.assertEquals(returnShipment.get("address").toString(), returnResponse.getData().get(0).getReturnAddressDetailsEntry().getAddress().toString(), "address Mismatch in LMS-RMS");
            Assert.assertEquals(returnShipment.get("city").toString(), returnResponse.getData().get(0).getReturnAddressDetailsEntry().getCity().toString(), "city Mismatch in LMS-RMS");
            Assert.assertEquals(returnShipment.get("country").toString(), returnResponse.getData().get(0).getReturnAddressDetailsEntry().getCountry().toString(), "return_mode Mismatch in LMS-RMS");
            Assert.assertEquals(returnShipment.get("pincode").toString(), returnResponse.getData().get(0).getReturnAddressDetailsEntry().getZipcode().toString(), "pincode Mismatch in LMS-RMS");
        }
        return true;
    }

    /**
     * validateRmsStatusAndRefund
     * @param returnId
     * @param status
     * @param refund
     */
    public boolean validateRmsStatusAndRefund(String returnId, String status, boolean refund, long wait) throws IOException, InterruptedException {
        RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
        Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(returnId, status, 10), "return is not in "+status+" status in Returns");
        if (refund) {
            log.info("sleeping for "+wait+" msc");
            Thread.sleep(wait);
            ReturnResponse returnResponse = rmsServiceHelper.getReturnDetailsNew(returnId);
            Assert.assertEquals(returnResponse.getData().get(0).getReturnRefundDetailsEntry().getRefunded().toString(), "" + refund, "is_refunded is not as expected. Expected: " + refund + " But not found");
            Assert.assertNotNull(returnResponse.getData().get(0).getReturnRefundDetailsEntry().getRefundPPSId().toString(),"Return PPSID is null in RMS");
        }
        return true;
    }

    /**
     * getOrderStatusFromLMS
     *
     * @param orderReleaseID
     * @return
     */
    public String getOrderStatusFromLMS(String orderReleaseID) {
       
    	   try {
        		@SuppressWarnings("unchecked")
        		List<Map<String, Object>> list = DBUtilities.exSelectQuery("select shipment_status from order_to_ship where order_id='" + orderReleaseID+"'", "myntra_lms");
            if (list == null) {
                return "false";
            }
            Map<String, Object> hm = (Map<String, Object>) list.get(0);
            return "" + hm.get("shipment_status");
        } catch (Exception e) {
            log.error("Error in getOrderStatusFromLMS :- " + e.getMessage());
            return "false";
        }
    }

    /**
     * getOrderStatusFromML
     * @param orderReleaseID
     * @return
     */
    public String getOrderStatusFromML(String orderReleaseID) {
        
    	   try {
        	
        		@SuppressWarnings("unchecked")
            List<Map<String, Object>> list = DBUtilities.exSelectQuery("select shipment_status from ml_shipment where source_reference_id='" + orderReleaseID+"'", "lms");
            if (list == null) {
                return "false";
            }
            Map<String, Object> hm = (Map<String, Object>) list.get(0);
            return "" + hm.get("shipment_status");
        } catch (Exception e) {
            log.error("Error in getOrderStatusFromLMS :- " + e.getMessage());
            return "false";
        }
    }

    /**
     * getPickupStatusFromLMS
     *
     * @param returnId
     * @return
     */
    public String getPickupStatusFromLMS(String returnId) {
       
    	   try {
        	
        		@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = DBUtilities.exSelectQuery("select shipment_status from return_shipment where source_return_id =" + returnId, "lms");
            if (list == null) {
                return "false";
            }
            Map<String, Object> hm = (Map<String, Object>) list.get(0);
            return "" + hm.get("shipment_status");
        } catch (Exception e) {
            log.error("Error in getOrderStatusFromLMS :- " + e.getMessage());
            return "false";
        }
    }



    /**
     * processReturnTillTripCreation
     *
     * @param returnId
     * @param trackingNo
     * @param deliveryCenterID
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws NumberFormatException
     * @throws IOException
     * @throws JAXBException
     */
    public Map<String, String> processReturnTillTripCreation(String returnId, String trackingNo, String deliveryCenterID) throws  NumberFormatException, IOException, JAXBException {
        String deliveryStaffID = getDeliveryStaffID(deliveryCenterID);
        log.info("Delivery Center ID :" + deliveryStaffID + "  Delivery Center ID : " + deliveryCenterID);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        Map<String, String> data = new HashMap<>();
        TripResponse tripResponse = createTrip(Long.parseLong(deliveryCenterID), Long.parseLong(deliveryStaffID));
        Long tripId = tripResponse.getTrips().get(0).getId();
        SlackMessenger.send("scm_e2e_order_sanity", "Trip Creation for pickup complete");
        // scan tracking number in trip
        log.info("Tracking ID := " + tripId);
        TripOrderAssignmentResponse scanTrackingNoInTripRes = assignOrderToTrip(tripId, trackingNo);
        Assert.assertEquals(scanTrackingNoInTripRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        // Start Trip
        TripOrderAssignmentResponse startTripRes = startTrip("" + tripId, "10");
        Assert.assertEquals(startTripRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        SlackMessenger.send("scm_e2e_order_sanity", "Trip Started");
        Map<String, Object> toaId = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select id from trip_order_assignment where trip_id = " + tripId, "lms");
        if (toaId == null) {
            return null;
        }
        String tripOrderAssignmentId = toaId.get("id").toString();
        data.put("tripOrderAssignmentId", tripOrderAssignmentId);
        data.put("tripId", tripId.toString());
        return data;
    }

    /**
     * processReturnInLMS process the return till Shipping back to config source warehouse and receiving in WH. SO it performs the complete action
     *
     * @param returnId/in case of selfShip its orderId
     * @param toStatus
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws NumberFormatException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    public void processReturnInLMS(String returnId, String toStatus) throws InterruptedException, JAXBException, IOException, ManagerException, XMLStreamException, JSONException {
        String trackingNo = null;
        String destWarehouseId = null;
        String deliveryCenterID = null;
        String tripOrderAssignmentId = null;
        //Map<String, String> data;
        if (toStatus.equals(EnumSCM.SELF_SHIP_PICKUP_SUCCESSFUL)){
            selfShipProcessReturnPickupSuccessful(returnId, "5");// Here we are treating lineId as returnId as we are creating return too in it.
            return;
        }else if(toStatus.equals(EnumSCM.SELF_SHIP_ON_HOLD_APPROVE)){
            selfShipProcessReturnOnHoldApprove(returnId,"5");// Here we are treating lineId as returnId as we are creating return too in it.
            return;
        } else if(toStatus.equals(EnumSCM.SELF_SHIP_ON_HOLD_REJECT)){
            selfShipProcessReturnOnHoldReject(returnId,"5");// Here we are treating lineId as returnId as we are creating return too in it.
            return;
        }else if(toStatus.equals(EnumSCM.SELF_SHIP_REJECT)){
            selfShipProcessReturnReject(returnId,"5");// Here we are treating lineId as returnId as we are creating return too in it.
            return;
        }else {
            validateRmsLmsReturnCreation(""+returnId);
            Map<String, Object> pickup= DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnId, "lms");
            if (pickup.get("courier_code").toString().equals("ML"))
                processReturnInML(returnId, toStatus, trackingNo, destWarehouseId, deliveryCenterID, tripOrderAssignmentId, pickup);
            else if(pickup.get("courier_code").toString().equals("EK")) {

            }else if (pickup.get("courier_code").toString().equals("DE")) {

            }else {
                validateRmsLmsReturnCreation(""+returnId);
                processClosedBoxPickup(returnId,toStatus);
            }
        }
        log.info("Your order processing has been completed successfully ReturnId: " + returnId);
    }

    /**
     * processReturnInML
     * @param returnId
     * @param toStatus
     * @param trackingNo
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    public void processReturnInML(String returnId, String toStatus, String trackingNo, String destWarehouseId, String
		    deliveryCenterID, String tripOrderAssignmentId, Map<String, Object> return_shipment) throws IOException, JAXBException, InterruptedException, ManagerException, XMLStreamException, JSONException {
        Map<String, String> data;
        if (!toStatus.equals(EnumSCM.SELF_SHIP)) {
            trackingNo = return_shipment.get("tracking_number").toString();
            destWarehouseId = return_shipment.get("return_warehouse_id").toString();
            deliveryCenterID = return_shipment.get("delivery_center_id").toString();
            data = processReturnTillTripCreation(returnId, trackingNo, deliveryCenterID);
            tripOrderAssignmentId = data.get("tripOrderAssignmentId");
        }
        switch (toStatus) {
            case EnumSCM.PICKED_UP_SUCCESSFULLY:
              //  markTripPickupSuccessful(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId);
                break;
            case EnumSCM.PICKED_UP_QCP_APPROVED_Before_trip_close:
                markTripPickupSuccessfulQCPendingBeforeTripClose(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId, EnumSCM.APPROVED);
                break;
            case EnumSCM.PICKED_UP_QCP_REJECTED_Before_trip_close:
                pickupQCRejectBeforeTripClose(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId);
                break;
            case EnumSCM.PICKED_UP_QCP_APPROVED_After_trip_close:
               // lms_returnHelper.pickupPQCP_CCApprove(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId, trackingNo);
                break;
            case EnumSCM.PICKED_UP_QCP_REJECTED_After_trip_close:
                pickupQCRejectedAfterTripClose(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId);
                break;
            case EnumSCM.ONHOLD_PICKUP_WITH_CUSTOMER_REJECT:
                onholdPickupWithCustomerReject(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId);
                break;
            case EnumSCM.ONHOLD_PICKUP_WITH_CUSTOMER:
                markTripPickupToOnHoldWithCustomer(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId);
                break;
            case EnumSCM.FAILED_PICKUP_ONHOLD_PICKUP_WITH_CUSTOMER_REJECT:
                failedPickupOhHoldWithCustomer(returnId, trackingNo, destWarehouseId, deliveryCenterID, tripOrderAssignmentId);
                break;
            case EnumSCM.ONHOLD_PICKUP_WITH_CUSTOMER_APPROVE:
                onholdPickupWithCustomerApprove(returnId, trackingNo, destWarehouseId, deliveryCenterID, tripOrderAssignmentId);
                break;
            case EnumSCM.ONHOLD_PICKUP_WITH_DC_REJECT:
                onholdPickupWithDCReject(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId);
                break;
            case EnumSCM.ONHOLD_PICKUP_WITH_DC:
                markTripPickupToOnHoldWithDC(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId);
                break;
            case EnumSCM.ONHOLD_PICKUP_WITH_DC_APPROVE:
                onholdPickupWithDCApprove(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId, trackingNo);
                break;
            case EnumSCM.FAILED_PICKUP_AND_SUCCESS:
                failedSuccessPickup(returnId, trackingNo, destWarehouseId, deliveryCenterID, tripOrderAssignmentId);
                break;
            case EnumSCM.FAILED_PICKUP_FAILED_PICKUP_AND_SUCCESS:
                failedFailedSuccessPickup(returnId, trackingNo, destWarehouseId, deliveryCenterID, tripOrderAssignmentId);
                break;
            case EnumSCM.FAILED_PICKUP_AND_SUCCESS_ON_SAMETRIP:
                markTripFailedAndPickupSuccessfulOnSameTrip(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId);
                break;
            default:
                log.info("No matching status");
                break;
        }
    }

    /**
     * processClosedBoxPickup
     * @param returnId
     * @param toStatus
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    public void processClosedBoxPickup(String returnId,String toStatus) throws IOException, JAXBException, InterruptedException {
        switch (toStatus){
            case EnumSCM.PICKUP_SUCCESSFUL_CB_AT_DC:
                pickupSuccessFulClosedBoxAtDC(returnId);
                break;
            case EnumSCM.PICKUP_SUCCESSFUL_CB_AT_WH:
                pickupSuccessFulClosedBoxAtWH(returnId);
                break;
            case EnumSCM.PICKUP_SUCCESSFUL_CB_AT_RPU:
                pickupSuccessFulClosedBoxAtRPU(returnId);
                break;
            case EnumSCM.ONHOLD_PICKUP_WITH_DC_APPROVE:
                pickupSuccessFulClosedBoxOnHoldApprove(returnId);
                break;
            case EnumSCM.ONHOLD_PICKUP_WITH_DC_REJECT:
                pickupSuccessFulClosedBoxOnHoldReject(returnId);
                break;
            default:
                log.info("No matching status");
                break;
        }
    }

    /**
     * pickupSuccessFulClosedBoxAtDC
     * @param returnId
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    public void pickupSuccessFulClosedBoxAtDC (String returnId) throws IOException, JAXBException, InterruptedException {
        ReturnResponse returnResponse = rmsServiceHelper.getReturnDetailsNew(returnId);
        ReturnEntry returnEntry = returnResponse.getData().get(0);
        Map<String, Object> item = DBUtilities.exSelectQueryForSingleRecord("select barcode from item where order_id ="+omsServiceHelper.getReleaseId(returnEntry.getOrderId().toString()),"wms");
        TrackingNumberResponse trackingNumberResponse = getTrackingNumber(returnResponse.getData().get(0).getReturnTrackingDetailsEntry().getCourierCode(),"36","true", "560068","NORMAL");
        Assert.assertEquals(rmsServiceHelper.bulk_statuschange(returnId,ReturnStatus.RPI, trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber() ,"ELC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(rmsServiceHelper.bulk_statuschange(returnId,ReturnStatus.RPU,trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber(),"ELC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(returnId,EnumSCM.RPU,2),"Status not changed to RPU");
        Assert.assertEquals(rmsServiceHelper.bulk_statuschange(returnId,ReturnStatus.RDU,trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber(),"ELC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(returnId,EnumSCM.RDU,2),"Status not changed to RDU");
        Assert.assertEquals(rmsServiceHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RADC, returnEntry.getId(), returnEntry.getReturnLineEntries().get(0).getId(),
                "Pass", item.get("barcode").toString(), "IP", "12345", "shubham", "ELC", 5L, "DC").getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
       // Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId,EnumSCM.PICKUP_SUCCESSFUL,8),"Status is not PICKUP_SUCCESSFUL in LMS ");
        validateRmsStatusAndRefund(returnId,EnumSCM.RADC,true,8000L);
    }

    public void pickupSuccessFulClosedBoxAtWH (String returnId) throws IOException, JAXBException, InterruptedException {
        ReturnResponse returnResponse = rmsServiceHelper.getReturnDetailsNew(returnId);
        ReturnEntry returnEntry = returnResponse.getData().get(0);
        Map<String, Object> item = DBUtilities.exSelectQueryForSingleRecord("select barcode from item where order_id ="+omsServiceHelper.getReleaseId(returnEntry.getOrderId().toString()),"wms");
        TrackingNumberResponse trackingNumberResponse = getTrackingNumber(returnResponse.getData().get(0).getReturnTrackingDetailsEntry().getCourierCode(),"36","true", "560068","NORMAL");
        Assert.assertEquals(rmsServiceHelper.bulk_statuschange(returnId,ReturnStatus.RPI, trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber() ,"ELC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(rmsServiceHelper.bulk_statuschange(returnId,ReturnStatus.RPU,trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber(),"ELC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        validateRmsStatusAndRefund(returnId,EnumSCM.RPU,false,0L);
        Assert.assertEquals(rmsServiceHelper.returnLineStatusProcessNewWarehouse(ReturnLineStatus.RPU, ReturnLineStatus.RRC, returnEntry.getId(), returnEntry.getReturnLineEntries().get(0).getId(),
                "Pass", item.get("barcode").toString(), "IP", "12345", "shubham", 36).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
       // Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId,EnumSCM.PICKUP_SUCCESSFUL,8),"Status is not PICKUP_SUCCESSFUL in LMS ");
        validateRmsStatusAndRefund(returnId,EnumSCM.RRC,false,0L);
    }

    /**
     * pickupSuccessFulClosedBoxAtRPU
     * @param returnId
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    public void pickupSuccessFulClosedBoxAtRPU (String returnId) throws IOException, JAXBException, InterruptedException {
        ReturnResponse returnResponse = rmsServiceHelper.getReturnDetailsNew(returnId);
//        ReturnEntry returnEntry = returnResponse.getData().get(0);
        TrackingNumberResponse trackingNumberResponse = getTrackingNumber(returnResponse.getData().get(0).getReturnTrackingDetailsEntry().getCourierCode(),"36","true", "560068","NORMAL");
        Assert.assertEquals(rmsServiceHelper.bulk_statuschange(returnId,ReturnStatus.RPI, trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber() ,"ELC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(rmsServiceHelper.bulk_statuschange(returnId,ReturnStatus.RPU,trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber(),"ELC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(rmsServiceHelper.bulk_issueRefund(returnId,trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber(),returnResponse.getData().get(0).getReturnTrackingDetailsEntry().getCourierCode(),
                returnResponse.getData().get(0).getReturnLineEntries().get(0).getWarehouseId(),"rms").getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        validateRmsStatusAndRefund(returnId,EnumSCM.RPU,true,8000L);
    }

    /**
     * pickupSuccessFulClosedBoxOnHoldApprove
     * @param returnId
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    public void pickupSuccessFulClosedBoxOnHoldApprove (String returnId) throws IOException, JAXBException, InterruptedException {
        ReturnResponse returnResponse = rmsServiceHelper.getReturnDetailsNew(returnId);
        ReturnEntry returnEntry = returnResponse.getData().get(0);
        Map<String, Object> item = DBUtilities.exSelectQueryForSingleRecord("select barcode from item where order_id =" + omsServiceHelper.getReleaseId(returnEntry.getOrderId().toString()), "wms");
        TrackingNumberResponse trackingNumberResponse = getTrackingNumber(returnResponse.getData().get(0).getReturnTrackingDetailsEntry().getCourierCode(), "36", "true", "560068", "NORMAL");
        Assert.assertEquals(rmsServiceHelper.bulk_statuschange(returnId, ReturnStatus.RPI, trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber(), "ELC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(rmsServiceHelper.bulk_statuschange(returnId, ReturnStatus.RPU, trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber(), "ELC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(returnId, EnumSCM.RPU, 2), "Status not changed to RPU");
        Assert.assertEquals(rmsServiceHelper.bulk_statuschange(returnId, ReturnStatus.RDU, trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber(), "ELC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(returnId, EnumSCM.RDU, 2), "Status not changed to RDU");
        Assert.assertEquals(rmsServiceHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RJDC, returnEntry.getId(), returnEntry.getReturnLineEntries().get(0).getId(),
                "Pass", item.get("barcode").toString(), "IP", "12345", "shubham", "ELC", 5L, "DC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
       // Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId, EnumSCM.ONHOLD_PICKUP_WITH_DC, 8), "Status is not PICKUP_SUCCESSFUL in LMS ");
        Assert.assertEquals(rmsServiceHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RJDC, ReturnLineStatus.CPDC, returnEntry.getId(), returnEntry.getReturnLineEntries().get(0).getId(),
                "Pass", item.get("barcode").toString(), "IP", "12345", "shubham", "ELC", 5L, "DC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        //Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId,EnumSCM.PICKUP_SUCCESSFUL,8),"Status is not PICKUP_SUCCESSFUL in LMS ");
        validateRmsStatusAndRefund(returnId,EnumSCM.CPDC,true,8000L);
    }

    public void pickupSuccessFulClosedBoxOnHoldReject (String returnId) throws IOException, JAXBException, InterruptedException {
        ReturnResponse returnResponse = rmsServiceHelper.getReturnDetailsNew(returnId);
        ReturnEntry returnEntry = returnResponse.getData().get(0);
        Map<String, Object> item = DBUtilities.exSelectQueryForSingleRecord("select barcode from item where order_id =" + omsServiceHelper.getReleaseId(returnEntry.getOrderId().toString()), "wms");
        TrackingNumberResponse trackingNumberResponse = getTrackingNumber(returnResponse.getData().get(0).getReturnTrackingDetailsEntry().getCourierCode(), "36", "true", "560068", "NORMAL");
        Assert.assertEquals(rmsServiceHelper.bulk_statuschange(returnId, ReturnStatus.RPI, trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber(), "ELC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(rmsServiceHelper.bulk_statuschange(returnId, ReturnStatus.RPU, trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber(), "ELC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(returnId, EnumSCM.RPU, 2), "Status not changed to RPU");
        Assert.assertEquals(rmsServiceHelper.bulk_statuschange(returnId, ReturnStatus.RDU, trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber(), "ELC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(returnId, EnumSCM.RDU, 2), "Status not changed to RDU");
        Assert.assertEquals(rmsServiceHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RJDC, returnEntry.getId(), returnEntry.getReturnLineEntries().get(0).getId(),
                "Pass", item.get("barcode").toString(), "IP", "12345", "shubham", "ELC", 5L, "DC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
      //  Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId, EnumSCM.ONHOLD_PICKUP_WITH_DC, 8), "Status is not PICKUP_SUCCESSFUL in LMS ");
        Assert.assertEquals(rmsServiceHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RJDC, ReturnLineStatus.CFDC, returnEntry.getId(), returnEntry.getReturnLineEntries().get(0).getId(),
                "Pass", item.get("barcode").toString(), "IP", "12345", "shubham", "ELC", 5L, "DC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
      //  Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId,EnumSCM.RETURN_REJECTED,8),"Status is not PICKUP_SUCCESSFUL in LMS ");
        validateRmsStatusAndRefund(returnId,EnumSCM.CFDC,false,0L);
    }

    /**
     * selfShipProcessReturnPickupSuccessful
     * @param orderLineId
     * @param deliveryCenterID
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     * @throws JsonParseException
     * @throws JsonMappingException
     */
    public void selfShipProcessReturnPickupSuccessful(String orderLineId, String deliveryCenterID)
            throws IOException, JAXBException, InterruptedException, JSONException, XMLStreamException, ManagerException {
        Map<String, Object> deliverCenterCode = DBUtilities.exSelectQueryForSingleRecord("select code from delivery_center where id = " + deliveryCenterID, "LMS");
        String selfShipReturnId = ""+rmsServiceHelper.selfshipProcesstoDC(orderLineId, deliverCenterCode.get("code").toString(), "BD");
        validateRmsLmsReturnCreation(selfShipReturnId);
        ReturnResponse returnResponse1 = rmsServiceHelper.getReturnDetailsNew(selfShipReturnId);
        ReturnEntry returnEntry = returnResponse1.getData().get(0);
        Map<String, Object> item = DBUtilities.exSelectQueryForSingleRecord("select barcode from item where order_id ="+omsServiceHelper.getReleaseId(returnEntry.getOrderId().toString()),"wms");
        Assert.assertEquals(rmsServiceHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RRQS, ReturnLineStatus.RADC, returnEntry.getId(), returnEntry.getReturnLineEntries().get(0).getId(),
                "Pass", item.get("barcode").toString(), "ML", "12345", "shubham", "ELC", 5L, "DC").getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
       // Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(selfShipReturnId,EnumSCM.PICKUP_SUCCESSFUL,8),"Status is not PICKUP_SUCCESSFUL in LMS ");
        validateRmsStatusAndRefund(selfShipReturnId,EnumSCM.RADC,true,8000);
        log.info(EnumSCM.PICKUP_SUCCESSFUL);
        Map<String, Object> returnShipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + selfShipReturnId, "lms");
        long destWarehouseId = (long) returnShipment.get("return_warehouse_id");
        transferShipmentBackToWH(selfShipReturnId, destWarehouseId, Long.parseLong(deliveryCenterID), "DC", "WH");
    }

    /**
     * selfShipProcessReturnOnHoldApprove
     * @param orderLineId
     * @param deliveryCenterID
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    public void selfShipProcessReturnOnHoldApprove(String orderLineId, String deliveryCenterID)
            throws IOException, JAXBException, InterruptedException, JSONException, XMLStreamException, ManagerException {
        Map<String, Object> deliverCenterCode = DBUtilities.exSelectQueryForSingleRecord("select code from delivery_center where id = " + deliveryCenterID, "LMS");
        String selfShipReturnId = ""+rmsServiceHelper.selfshipProcesstoDC(orderLineId, deliverCenterCode.get("code").toString(), "BD");
        validateRmsLmsReturnCreation(selfShipReturnId);
        ReturnResponse returnResponse1 = rmsServiceHelper.getReturnDetailsNew(selfShipReturnId);
        ReturnEntry returnEntry = returnResponse1.getData().get(0);
        Map<String, Object> item = DBUtilities.exSelectQueryForSingleRecord("select barcode from item where order_id ="+omsServiceHelper.getReleaseId(returnEntry.getOrderId().toString()),"wms");
        Assert.assertEquals(rmsServiceHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RRQS, ReturnLineStatus.RJDC, returnEntry.getId(), returnEntry.getReturnLineEntries().get(0).getId(),
                "Fail", item.get("barcode").toString(), "ML", "12345", "shubham", "ELC", 5L, "DC").getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(selfShipReturnId,EnumSCM.RJDC,2),"Status is not RJDC in RMS ");
       // Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(selfShipReturnId,EnumSCM.ONHOLD_PICKUP_WITH_COURIER,8),"Status is not ONHOLD_PICKUP_WITH_COURIER in LMS ");
        Assert.assertEquals(rmsServiceHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RJDC, ReturnLineStatus.CPDC, returnEntry.getId(), returnEntry.getReturnLineEntries().get(0).getId(),
                "Pass", item.get("barcode").toString(), "ML", "12345", "shubham", "ELC", 5L, "DC").getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        //Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(selfShipReturnId,EnumSCM.PICKUP_SUCCESSFUL,8),"Status is not PICKUP_SUCCESSFUL in LMS ");
        validateRmsStatusAndRefund(selfShipReturnId,EnumSCM.CPDC,true,8000);
        log.info(EnumSCM.PICKUP_SUCCESSFUL);
        Map<String, Object> returnShipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + selfShipReturnId, "lms");
        long destWarehouseId = (long) returnShipment.get("return_warehouse_id");
        transferShipmentBackToWH(selfShipReturnId, destWarehouseId, Long.parseLong(deliveryCenterID), "DC", "WH");
    }

    /**
     * selfShipProcessReturnOnHoldReject
     * @param orderLineId
     * @param deliveryCenterID
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    public void selfShipProcessReturnOnHoldReject(String orderLineId, String deliveryCenterID)
            throws IOException, JAXBException, InterruptedException{
        Map<String, Object> deliverCenterCode = DBUtilities.exSelectQueryForSingleRecord("select code from delivery_center where id = " + deliveryCenterID, "LMS");
        String selfShipReturnId = rmsServiceHelper.selfshipProcesstoDC(orderLineId, deliverCenterCode.get("code").toString(), "BD");
        validateRmsLmsReturnCreation(""+selfShipReturnId);
        ReturnResponse returnResponse1 = rmsServiceHelper.getReturnDetailsNew(selfShipReturnId);
        ReturnEntry returnEntry = returnResponse1.getData().get(0);
        Map<String, Object> item = DBUtilities.exSelectQueryForSingleRecord("select barcode from item where order_id ="+omsServiceHelper.getReleaseId(returnEntry.getOrderId().toString()),"wms");
        Assert.assertEquals(rmsServiceHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RRQS, ReturnLineStatus.RJDC, returnEntry.getId(), returnEntry.getReturnLineEntries().get(0).getId(),
                "Fail", item.get("barcode").toString(), "ML", "12345", "shubham", "ELC", 5L, "DC").getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(selfShipReturnId,EnumSCM.RJDC,2),"Status is not RJDC in RMS ");
       // Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(""+selfShipReturnId,EnumSCM.ONHOLD_PICKUP_WITH_COURIER,8),"Status is not ONHOLD_PICKUP_WITH_COURIER in LMS ");

        Assert.assertEquals(rmsServiceHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RJDC, ReturnLineStatus.CFDC, returnEntry.getId(), returnEntry.getReturnLineEntries().get(0).getId(),
                "Pass", item.get("barcode").toString(), "ML", "12345", "shubham", "ELC", 5L, "DC").getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
      //  Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(""+selfShipReturnId,EnumSCM.RETURN_REJECTED,8),"Status is not RETURN_REJECTED in LMS ");
        Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(selfShipReturnId,EnumSCM.CFDC,2),"Status is not CFDC in RMS ");
    }

    /**
     * selfShipProcessReturnReject
     * @param orderLineId
     * @param deliveryCenterID
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    public void selfShipProcessReturnReject(String orderLineId, String deliveryCenterID)
            throws IOException, JAXBException, InterruptedException{
        Map<String, Object> deliverCenterCode = DBUtilities.exSelectQueryForSingleRecord("select code from delivery_center where id = " + deliveryCenterID, "LMS");
        String selfShipReturnId = rmsServiceHelper.selfshipProcesstoDC(orderLineId, deliverCenterCode.get("code").toString(), "BD");
        validateRmsLmsReturnCreation(""+selfShipReturnId);
        ReturnResponse returnResponse1 = rmsServiceHelper.getReturnDetailsNew(selfShipReturnId);
        ReturnEntry returnEntry = returnResponse1.getData().get(0);
        Assert.assertEquals(rmsServiceHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RRQS, ReturnStatus.RRD).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(selfShipReturnId,EnumSCM.RRD,2),"Status is not RRD in RMS ");
       // Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(""+selfShipReturnId,EnumSCM.RETURN_REJECTED,8),"Status is not RETURN_REJECTED in LMS ");
    }

    /**
     * pickupQCRejectBeforeTripClose
     *
     * @param returnId
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     * @throws UnsupportedEncodingException
     */
    private void pickupQCRejectBeforeTripClose(String returnId, String destWarehouseId, String deliveryCenterID,
                                               String tripOrderAssignmentId) throws IOException, JAXBException,
            InterruptedException, ManagerException, XMLStreamException, JSONException {
        markTripPickupSuccessfulQCPendingBeforeTripClose(returnId, destWarehouseId, deliveryCenterID,
                tripOrderAssignmentId, EnumSCM.REJECTED);
        returnApproveOrReject(returnId, EnumSCM.REJECTED);

        Assert.assertEquals(lmsHelper.getReturnApprovalStatus(returnId), EnumSCM.REJECTED, "Approval status is not REJECTED");
       // Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId,EnumSCM.REJECTED_ONHOLD_PICKUP_WITH_COURIER,10),"return is not REJECTED_ONHOLD_PICKUP_WITH_DC");
        validateRmsStatusAndRefund(returnId,EnumSCM.RRD, false, 1L);
    }

    /**
     * pickupQCApproveAfterTripClose
     *
     * @param returnId
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @param trackingNumber
     * @throws Exception
     */
    private void pickupQCApproveAfterTripClose(String returnId, String destWarehouseId, String deliveryCenterID,
                                               String tripOrderAssignmentId, String trackingNumber) throws InterruptedException, JAXBException, IOException, JSONException, XMLStreamException, ManagerException {
        markTripPickupSuccessfulQCPending(returnId, destWarehouseId, deliveryCenterID,
                tripOrderAssignmentId);
        //long dcId, String trackingNo, MLShipmentUpdateEvent event, String remarks, ShipmentType shipmentType
    //    lms_returnHelper.mlOpenBoxQC(returnId,ShipmentUpdateEvent.PICKUP_ON_HOLD,trackingNumber);
       // mlShipmentUpdate(Long.parseLong(deliveryCenterID), trackingNumber, MLShipmentUpdateEvent.PICKUP_QC_COMPLETE, EnumSCM.PICKED_UP_SUCCESSFULLY, ShipmentType.PU);
     // lms_returnHelper.validatePickupShipmentStatusInLMS(returnId, com.myntra.logistics.platform.domain.ShipmentStatus.ONHOLD_PICKUP_WITH_COURIER.toString(),10);
      //lms_returnHelper.validateReturnShipmentStatusInLMS(returnId, com.myntra.logistics.platform.domain.ShipmentStatus.ONHOLD_RETURN_WITH_COURIER.toString(),10);
      
        validateRmsStatusAndRefund(returnId,EnumSCM.RPU, true, 8000L);
        transferShipmentBackToWH(returnId, Long.parseLong(destWarehouseId), Long.parseLong(deliveryCenterID), "DC", "WH");
    }

    /**
     * pickupQCRejectedAfterTripClose
     *
     * @param returnId
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings("unchecked")
    private void pickupQCRejectedAfterTripClose(String returnId, String destWarehouseId, String deliveryCenterID,
                                                String tripOrderAssignmentId) throws IOException, JAXBException,
            InterruptedException {
        markTripPickupSuccessfulQCPending(returnId, destWarehouseId, deliveryCenterID,
                tripOrderAssignmentId);
//        returnApproveOrReject(returnId, EnumSCM.REJECTED);// RETURN_REJECTED in mlShipmentUpdate

        Assert.assertEquals(mlShipmentUpdate(Long.parseLong(deliveryCenterID), (String)lmsHelper.getReturnsTrackingNumber.apply(returnId), MLShipmentUpdateEvent.PICKUP_QC_FAILED,"RETURN_REJECTED",ShipmentType.PU),EnumSCM.SUCCESS);
        Thread.sleep(2000L);
//        Assert.assertEquals(lmsHelper.getReturnApprovalStatus(returnId), EnumSCM.REJECTED, "Approval status is not REJECTED");
     //   Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId,EnumSCM.RETURN_REJECTED,10),"return is not RETURN_REJECTED");
        Assert.assertTrue(validateOrderStatusInML(""+returnId,EnumSCM.RESHIP_TO_CUSTOMER,10),"return is not RETURN_REJECTED");
        validateRmsStatusAndRefund(returnId,EnumSCM.RRD, false, 0L);
    }

    /**
     * onholdPickupWithCustomerReject
     *
     * @param returnId
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     * @throws UnsupportedEncodingException
     */
    private void onholdPickupWithCustomerReject(String returnId, String destWarehouseId, String deliveryCenterID,
                                                String tripOrderAssignmentId) throws IOException, JAXBException,
            InterruptedException {
        markTripPickupToOnHoldWithCustomer(returnId, destWarehouseId, deliveryCenterID,
                tripOrderAssignmentId);
        returnApproveOrReject(returnId, EnumSCM.REJECTED);
        Thread.sleep(3000L);
      //  Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId,EnumSCM.RETURN_REJECTED,10),"return is not RETURN_REJECTED");
        validateRmsStatusAndRefund(returnId,EnumSCM.RRD, false, 1L);
        Assert.assertEquals(lmsHelper.getReturnApprovalStatus(returnId), EnumSCM.REJECTED, "Approval status is not APPROVED");
    }

    /**
     * failedPickupOhHoldWithCustomer
     *
     * @param returnId
     * @param trackingNo
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     * @throws UnsupportedEncodingException
     */
    private void failedPickupOhHoldWithCustomer(String returnId, String trackingNo, String destWarehouseId,
                                                String deliveryCenterID, String tripOrderAssignmentId) throws
            IOException, JAXBException, InterruptedException {
        markTripPickupFailed(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId);
        OrderResponse requeueResponse = requeuePickup("" + returnId);
        Assert.assertEquals(requeueResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to requeue pickup after failed delivery");
        Map<String, String> data1 = processReturnTillTripCreation(returnId, trackingNo, deliveryCenterID);
        markTripPickupToOnHoldWithCustomer(returnId, destWarehouseId, deliveryCenterID,
                data1.get("tripOrderAssignmentId").toString());
        returnApproveOrReject(returnId, EnumSCM.REJECTED);
       // Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId,EnumSCM.RETURN_REJECTED,10),"return is not RETURN_REJECTED");
        validateRmsStatusAndRefund(returnId,EnumSCM.RRD, false, 1L);
        Assert.assertEquals(lmsHelper.getReturnApprovalStatus(returnId), EnumSCM.REJECTED, "Approval status is not APPROVED");
    }

    /**
     * onholdPickupWithDCApprove
     *
     * @param returnId
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @throws Exception
     */
    private void onholdPickupWithDCApprove(String returnId, String destWarehouseId, String deliveryCenterID,
                                           String tripOrderAssignmentId, String trackingNumber) throws InterruptedException, JAXBException, IOException, JSONException, XMLStreamException, ManagerException {
        markTripPickupToOnHoldWithDC(returnId, destWarehouseId, deliveryCenterID,
                tripOrderAssignmentId);
        returnApproveOrReject(returnId, EnumSCM.APPROVED);
        Thread.sleep(3000L);
//        mlShipmentUpdate(Long.parseLong(deliveryCenterID), trackingNumber, MLShipmentUpdateEvent.PICKUP_QC_COMPLETE, EnumSCM.PICKED_UP_SUCCESSFULLY, ShipmentType.PU);
     //   Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(""+returnId,EnumSCM.PICKUP_DONE_QC_PENDING,10));
        Assert.assertTrue(validateOrderStatusInML(""+returnId,EnumSCM.APPROVED_ONHOLD_PICKUP_WITH_DC,10));
        Assert.assertEquals(lmsHelper.getReturnApprovalStatus(returnId), EnumSCM.APPROVED, "Approval status is not APPROVED");
        AcknowledgeApprovePickup(Long.parseLong(deliveryCenterID),rmsServiceHelper.getReturnDetailsNew(returnId).getData().get(0).getReturnTrackingDetailsEntry().getTrackingNo());
      //  Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId,EnumSCM.PICKUP_SUCCESSFUL,10),"return is not PICKUP_SUCCESSFUL");
        Assert.assertTrue(validateOrderStatusInML(returnId,EnumSCM.PICKUP_SUCCESSFUL,10),"return is not PICKUP_SUCCESSFUL");
        validateRmsStatusAndRefund(returnId,EnumSCM.RPU, true, 10000L);
        transferShipmentBackToWH(returnId, Long.parseLong(destWarehouseId), Long.parseLong(deliveryCenterID), "DC", "WH");
    }

    /**
     * onholdPickupWithDCReject
     *
     * @param returnId
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings("unchecked")
    private void onholdPickupWithDCReject(String returnId, String destWarehouseId, String deliveryCenterID,
                                          String tripOrderAssignmentId) throws IOException, JAXBException,
            InterruptedException {
        markTripPickupToOnHoldWithDC(returnId, destWarehouseId, deliveryCenterID,
                tripOrderAssignmentId);
        returnApproveOrReject(returnId, EnumSCM.REJECTED);
        Thread.sleep(3000L);
        Assert.assertEquals(lmsHelper.getReturnApprovalStatus(returnId), EnumSCM.REJECTED, "Approval status is not Rejected");
      //  Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId,EnumSCM.PICKUP_DONE_QC_PENDING,10),"return is not REJECTED_ONHOLD_PICKUP_WITH_DC");
        Assert.assertTrue(validateOrderStatusInML(returnId,EnumSCM.REJECTED_ONHOLD_PICKUP_WITH_COURIER,3),"return not in REJECTED_ONHOLD_PICKUP_WITH_DC state in ML");
        Assert.assertEquals(mlShipmentUpdate(Long.parseLong(deliveryCenterID), (String)lmsHelper.getReturnsTrackingNumber.apply(returnId), MLShipmentUpdateEvent.RESHIP_TO_CUSTOMER,"RETURN_REJECTED",ShipmentType.PU),EnumSCM.SUCCESS);
      //  Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId,EnumSCM.RETURN_REJECTED,10),"return is not REJECTED_ONHOLD_PICKUP_WITH_DC");
        Assert.assertTrue(validateOrderStatusInML(returnId,EnumSCM.RESHIPPED_TO_CUSTOMER,2));
        validateRmsStatusAndRefund(returnId,EnumSCM.RRD, false, 1L);
    }

    /**
     * onholdPickupWithCustomerApprove
     *
     * @param returnId
     * @param trackingNo
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @throws Exception
     */
    private void onholdPickupWithCustomerApprove(String returnId, String trackingNo, String destWarehouseId,
                                                 String deliveryCenterID, String tripOrderAssignmentId) throws InterruptedException, JAXBException, IOException, ManagerException, XMLStreamException, JSONException {
        markTripPickupToOnHoldWithCustomer(returnId, destWarehouseId, deliveryCenterID,
                tripOrderAssignmentId);
        returnApproveOrReject(returnId, EnumSCM.APPROVED);
        Thread.sleep(5000);
        Map<String, String> data1 = processReturnTillTripCreation(returnId, trackingNo, deliveryCenterID);
        markTripPickupSuccessful(returnId, destWarehouseId, deliveryCenterID, data1.get("tripOrderAssignmentId").toString());
    }

    /**
     * failedSuccessPickup
     *
     * @param returnId
     * @param trackingNo
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @throws Exception
     */
    private void failedSuccessPickup(String returnId, String trackingNo, String destWarehouseId, String deliveryCenterID,
                                     String tripOrderAssignmentId) throws InterruptedException, JAXBException, IOException, ManagerException, XMLStreamException, JSONException {
        markTripPickupFailed(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId);
        OrderResponse requeueResponse = requeuePickup("" + returnId);
        Assert.assertEquals(requeueResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to requeue pickup after failed delivery");
        Map<String, String> data1 = processReturnTillTripCreation(returnId, trackingNo, deliveryCenterID);
        markTripPickupSuccessful(returnId, destWarehouseId, deliveryCenterID, data1.get("tripOrderAssignmentId").toString());
    }

    /**
     * failedFailedSuccessPickup
     *
     * @param returnId
     * @param trackingNo
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @throws Exception
     */
    private void failedFailedSuccessPickup(String returnId, String trackingNo, String destWarehouseId,
                                           String deliveryCenterID, String tripOrderAssignmentId) throws InterruptedException, JAXBException, IOException, ManagerException, XMLStreamException, JSONException {
        markTripPickupFailed(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId);
        OrderResponse requeueResponse = requeuePickup("" + returnId);
        Assert.assertEquals(requeueResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to requeue pickup after failed delivery");
        Map<String, String> data2 = processReturnTillTripCreation(returnId, trackingNo, deliveryCenterID);
        markTripPickupFailed(returnId, destWarehouseId, deliveryCenterID, data2.get("tripOrderAssignmentId").toString());
        OrderResponse requeueResponse2 = requeuePickup("" + returnId);
        Assert.assertEquals(requeueResponse2.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to requeue pickup after failed delivery");
        Map<String, String> data1 = processReturnTillTripCreation(returnId, trackingNo, deliveryCenterID);
        markTripPickupSuccessful(returnId, destWarehouseId, deliveryCenterID, data1.get("tripOrderAssignmentId").toString());
    }

    /**
     * markTripPickupSuccessful
     * @param returnId
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @throws Exception
     */
    private void markTripPickupSuccessful(String returnId, String destWarehouseId,
                                          String deliveryCenterID, String tripOrderAssignmentId)
            throws InterruptedException, IOException, JAXBException, JSONException, XMLStreamException, ManagerException {
        TripOrderAssignmentResponse response = updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.PICKED_UP_SUCCESSFULLY, EnumSCM.UPDATE);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        TripOrderAssignmentResponse response1 = updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.PICKED_UP_SUCCESSFULLY, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(response1.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
        SlackMessenger.send("scm_e2e_order_sanity", "Item picked up successfully");
       // Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId,EnumSCM.PICKUP_SUCCESSFUL,5), "return is not PICKUP_SUCCESSFUL" );
        validateRmsStatusAndRefund(returnId,EnumSCM.RPU, true, 10000L);
//        AcknowledgeApprovePickup(Long.parseLong(deliveryCenterID),rmsServiceHelper.getReturnDetailsNew(Long.parseLong(returnId)).getData().get(0).getReturnTrackingDetailsEntry().getTrackingNo());
        transferShipmentBackToWH(returnId, Long.parseLong(destWarehouseId), Long.parseLong(deliveryCenterID), "DC", "WH");
    }

    /**
     * markTripFailedAndPickupSuccessfulOnSameTrip
     *
     * @param returnId
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @throws Exception
     */
    private void markTripFailedAndPickupSuccessfulOnSameTrip(String returnId, String destWarehouseId,
                                                             String deliveryCenterID, String tripOrderAssignmentId)
            throws IOException, JAXBException, InterruptedException, JSONException, XMLStreamException, ManagerException {
        LMSHelper lmsHelper = new LMSHelper();
        TripOrderAssignmentResponse response = updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.RESCHEDULED_CUSTOMER_NOT_AVAILABLE, EnumSCM.UPDATE);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(3000);
        Assert.assertEquals(lmsHelper.getReturnStatus(returnId), EnumSCM.FAILED_PICKUP);
        TripOrderAssignmentResponse response12 = updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.PICKUP_SUCCESSFUL_QC_PENDING, EnumSCM.UPDATE);
        Assert.assertEquals(response12.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(3000);
        Assert.assertEquals(lmsHelper.getReturnStatus(returnId), EnumSCM.PICKUP_DONE_QC_PENDING);
        TripOrderAssignmentResponse response1 = updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.PICKED_UP_SUCCESSFULLY, EnumSCM.UPDATE);
        Assert.assertEquals(response1.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
        SlackMessenger.send("scm_e2e_order_sanity", "Item picked up successfully");
        Thread.sleep(3000L);
        Assert.assertEquals(lmsHelper.getReturnStatus(returnId), EnumSCM.PICKUP_SUCCESSFUL);
        TripOrderAssignmentResponse response3 = updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.PICKED_UP_SUCCESSFULLY, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(response3.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
        SlackMessenger.send("scm_e2e_order_sanity", "Item picked up successfully");
        Thread.sleep(5000L);
        Assert.assertEquals(lmsHelper.getReturnStatus(returnId), EnumSCM.PICKUP_SUCCESSFUL);
        validateRmsStatusAndRefund(returnId,EnumSCM.RPU,true, 8000L);
        transferShipmentBackToWH(returnId, Long.parseLong(destWarehouseId), Long.parseLong(deliveryCenterID), "DC", "WH");
    }

    /**
     * markTripPickupSuccessfulQCPendingBeforeTripClose
     *
     * @param returnId
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @param qcStatus
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    private void markTripPickupSuccessfulQCPendingBeforeTripClose(String returnId, String destWarehouseId,
                                                                  String deliveryCenterID, String tripOrderAssignmentId, String qcStatus)
            throws IOException, JAXBException, InterruptedException, JSONException, XMLStreamException, ManagerException {
        LMSHelper lmsHelper = new LMSHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        TripOrderAssignmentResponse response = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.PICKUP_SUCCESSFUL_QC_PENDING, EnumSCM.UPDATE);
        if (response.getStatus().getStatusType().toString().equals(EnumSCM.SUCCESS)) {
            SlackMessenger.send("scm_e2e_order_sanity", "Pickup success QC pending");
        } else {
            SlackMessenger.send("scm_e2e_order_sanity", "Unable to mark Pickup success QC pending", 3);
        }
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(3000);
        Assert.assertEquals(lmsHelper.getReturnStatus(returnId), EnumSCM.PICKUP_DONE_QC_PENDING);
//		returnApproveOrReject(returnId, qcStatus);
        Thread.sleep(3000L);
        if (qcStatus.equals(EnumSCM.APPROVED)) {
            TripOrderAssignmentResponse response1 = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.PICKED_UP_SUCCESSFULLY, EnumSCM.UPDATE);
            Assert.assertEquals(response1.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
            Thread.sleep(3000L);
            Assert.assertEquals(lmsHelper.getReturnStatus(returnId), EnumSCM.PICKUP_SUCCESSFUL);
            TripOrderAssignmentResponse response2 = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.PICKED_UP_SUCCESSFULLY, EnumSCM.TRIP_COMPLETE);
            if (response2.getStatus().getStatusType().toString().equals(EnumSCM.SUCCESS)) {
                SlackMessenger.send("scm_e2e_order_sanity", "Pickup success and Completed trip");
            } else {
                SlackMessenger.send("scm_e2e_order_sanity", "Unable to complete trip", 3);
            }
            Assert.assertEquals(response2.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
            validateRmsStatusAndRefund(returnId,EnumSCM.RPU,true, 8000L);
            transferShipmentBackToWH(returnId, Long.parseLong(destWarehouseId), Long.parseLong(deliveryCenterID), "DC", "WH");
        } else {
            TripOrderAssignmentResponse response1 = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.ON_HOLD_DAMAGED_PRODUCT, EnumSCM.UPDATE);
            Assert.assertEquals(response1.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
            Thread.sleep(3000L);
            TripOrderAssignmentResponse response2 = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.ON_HOLD_DAMAGED_PRODUCT, EnumSCM.TRIP_COMPLETE);
            Assert.assertEquals(response2.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
            Thread.sleep(5000L);
            Assert.assertEquals(lmsHelper.getReturnStatus(returnId), EnumSCM.ONHOLD_PICKUP_WITH_COURIER);
        }
    }

    /**
     * markTripPickupSuccessfulQCPending
     *
     * @param returnId
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    private void markTripPickupSuccessfulQCPending(String returnId, String destWarehouseId,
                                                   String deliveryCenterID, String tripOrderAssignmentId)
            throws  IOException, JAXBException, InterruptedException {
        LMSHelper lmsHelper = new LMSHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        TripOrderAssignmentResponse response = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.PICKUP_SUCCESSFUL_QC_PENDING, EnumSCM.UPDATE);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(3000);
       // lms_returnHelper.validatePickupShipmentStatusInLMS(returnId, com.myntra.logistics.platform.domain.ShipmentStatus.PICKUP_DONE.toString(),10);
        Thread.sleep(3000L);
        TripOrderAssignmentResponse response1 = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.PICKUP_SUCCESSFUL_QC_PENDING, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(response1.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
        Thread.sleep(5000L);
       // lms_returnHelper.validatePickupShipmentStatusInLMS(returnId, com.myntra.logistics.platform.domain.ShipmentStatus.PICKUP_DONE.toString(),10);

    }

    /**
     * markTripPickupToOnHoldWithCustomer
     *
     * @param returnId
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    private void markTripPickupToOnHoldWithCustomer(String returnId, String destWarehouseId,
                                                    String deliveryCenterID, String tripOrderAssignmentId)
            throws  IOException, JAXBException, InterruptedException {
        LMSHelper lmsHelper = new LMSHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        TripOrderAssignmentResponse response = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.ON_HOLD_DAMAGED_PRODUCT, EnumSCM.UPDATE);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(3000);
        TripOrderAssignmentResponse response1 = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.ON_HOLD_DAMAGED_PRODUCT, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(response1.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
        Thread.sleep(5000L);
        Assert.assertEquals(lmsHelper.getReturnStatus(returnId), EnumSCM.ONHOLD_PICKUP_WITH_CUSTOMER, "Unable to mark pickup ONHOLD_PICKUP_WITH_CUSTOMER");
    }

    /**
     * markTripPickupToOnHoldWithDC
     *
     * @param returnId
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    private void markTripPickupToOnHoldWithDC(String returnId, String destWarehouseId,
                                              String deliveryCenterID, String tripOrderAssignmentId)
            throws  IOException, JAXBException, InterruptedException {
        LMSHelper lmsHelper = new LMSHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        TripOrderAssignmentResponse response = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.PICKUP_SUCCESSFUL_QC_PENDING, EnumSCM.UPDATE);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(3000);
        TripOrderAssignmentResponse response2ndPickup = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.ON_HOLD_DAMAGED_PRODUCT, EnumSCM.UPDATE);
        Assert.assertEquals(response2ndPickup.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(3000);
        TripOrderAssignmentResponse response1 = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.ON_HOLD_DAMAGED_PRODUCT, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(response1.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
        Thread.sleep(5000L);
        Assert.assertEquals(lmsHelper.getReturnStatus(returnId), EnumSCM.ONHOLD_PICKUP_WITH_COURIER);

    }

    /**
     * markTripPickupFailed
     *
     * @param returnId
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param tripOrderAssignmentId
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     */
    private void markTripPickupFailed(String returnId, String destWarehouseId,
                                      String deliveryCenterID, String tripOrderAssignmentId)
            throws  IOException, JAXBException, InterruptedException {
        LMSHelper lmsHelper = new LMSHelper();
        TripOrderAssignmentResponse response = updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.RESCHEDULED_CUSTOMER_NOT_AVAILABLE, EnumSCM.UPDATE);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(3000);
        TripOrderAssignmentResponse response1 = updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.RESCHEDULED_CUSTOMER_NOT_AVAILABLE, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(response1.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
        Thread.sleep(5000L);
        Assert.assertEquals(lmsHelper.getReturnStatus(returnId), EnumSCM.FAILED_PICKUP);
    }

    /**
     * transferShipmentBackToWH ships the returns/RTO from DC to WH and received at WH
     *
     * @param returnId
     * @param destWarehouseId
     * @param deliveryCenterID
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws NumberFormatException
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
    public void transferShipmentBackToWH(String returnId, long destWarehouseId, long deliveryCenterID, String origin, String destination) throws NumberFormatException, IOException, JAXBException, InterruptedException, ManagerException, JSONException, XMLStreamException {
        Map<String, Object>returnShipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnId, "lms");
        if (returnShipment.size() == 0) {
            Assert.fail("Unable to fetch Pickup from LMS");
        }
        ShipmentResponse shipmentResponse = createMasterBag(deliveryCenterID, origin, "Bangalore", (long)lmsHelper.getRTHubIdForWH.apply(destWarehouseId), "HUB", "Bangalore", EnumSCM.NORMAL);
        Assert.assertEquals(shipmentResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "MasterBag creation failed");
        long masterBagId = (Long) shipmentResponse.getEntries().get(0).getId();
//		 ShipmentResponse saveMBResponse = saveMasterBagRT(masterBagId, returnId);
        String saveMBResponse = addAndSaveMasterBag(returnId, "" + masterBagId, ShipmentType.PU);
        Assert.assertEquals(saveMBResponse, EnumSCM.SUCCESS, "Unable to save Master Bag");
        ShipmentResponse closeMBResponse = closeMasterBag(masterBagId);
        Assert.assertEquals(closeMBResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close master bag");
        ShipmentResponse shipMB = shipMasterBag(masterBagId);
        Assert.assertEquals(shipMB.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to ship master bag");
     //   Assert.assertTrue(lms_returnHelper.validateReturnShipmentStatusInLMS(returnId,EnumSCM.RETURN_IN_TRANSIT,5),"return is not in RETURN_IN_TRANSIT");
        ShipmentResponse inscanResponse = masterBagInScan(masterBagId, EnumSCM.RECEIVED, "Bangalore", deliveryCenterID, "DC");
        Assert.assertEquals(inscanResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to inscan masterBag at WH");
        ExceptionHandler.handleEquals(receiveShipmentFromMasterbag(masterBagId).
                getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive shipment in DC");
        ShipmentResponse inscanUpdateResponse = masterBagInScanUpdate(masterBagId, returnShipment.get("order_id").toString(), returnId, "Bangalore", destWarehouseId, "WH", deliveryCenterID);
        if (!inscanUpdateResponse.getStatus().getStatusType().toString().equalsIgnoreCase(EnumSCM.SUCCESS))
            SlackMessenger.send("scm_e2e_order_sanity", "Unable update receive masterBag at WH", 3);
        Assert.assertEquals(inscanUpdateResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable update receive masterBag at WH");
//        SlackMessenger.send("scm_e2e_order_sanity", "shipment received in configured warehouse");
//        Assert.assertTrue(validatePickupStatusInLMS(returnId,EnumSCM.RETURN_RECEIVED,5),"return is not in RETURN_RECEIVED");
    }
	public void transferShipmentBackToWH(String returnId, long destWarehouseId, long deliveryCenterID, String origin, String destination,String trackingNumber) throws NumberFormatException, IOException, JAXBException, InterruptedException, ManagerException, JSONException, XMLStreamException {
	TMSServiceHelper tmsServiceHelper = new TMSServiceHelper();
			Map<String, Object>returnShipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnId, "lms");
		if (returnShipment.size() == 0) {
			Assert.fail("Unable to fetch Pickup from LMS");
		}
		ShipmentResponse shipmentResponse = createMasterBag(deliveryCenterID, origin, "Bangalore", (long)lmsHelper.getRTHubIdForWH.apply(destWarehouseId), "HUB", "Bangalore", EnumSCM.NORMAL);
		Assert.assertEquals(shipmentResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "MasterBag creation failed");
		long masterBagId = (Long) shipmentResponse.getEntries().get(0).getId();
//		 ShipmentResponse saveMBResponse = saveMasterBagRT(masterBagId, returnId);
		String saveMBResponse = addAndSaveMasterBag(returnId, "" + masterBagId, ShipmentType.PU,trackingNumber);
		Assert.assertEquals(saveMBResponse, EnumSCM.SUCCESS, "Unable to save Master Bag");
		ShipmentResponse closeMBResponse = closeMasterBag(masterBagId);
		Assert.assertEquals(closeMBResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close master bag");
		
		tmsServiceHelper.processInTMSFromClosedToReturnHub.accept(masterBagId);

		/*ShipmentResponse shipMB = shipMasterBag(masterBagId);
		Assert.assertEquals(shipMB.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to ship master bag");
		ShipmentResponse inscanResponse = masterBagInScan(masterBagId, EnumSCM.RECEIVED, "Bangalore", deliveryCenterID, "DC");
		Assert.assertEquals(inscanResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to inscan masterBag at WH");
		ExceptionHandler.handleEquals(receiveShipmentFromMasterbag(masterBagId).
				getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		ShipmentResponse inscanUpdateResponse = masterBagInScanUpdate(masterBagId, returnShipment.get("order_id").toString(), returnId, "Bangalore", destWarehouseId, "WH", deliveryCenterID);
		
		Assert.assertEquals(inscanUpdateResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable update receive masterBag at WH");
*/
//        SlackMessenger.send("scm_e2e_order_sanity", "shipment received in configured warehouse");
//        Assert.assertTrue(validatePickupStatusInLMS(returnId,EnumSCM.RETURN_RECEIVED,5),"return is not in RETURN_RECEIVED");
	
	}

    /**
     * transferShipmentBackToWHRTO
     *
     * @param orderId
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void transferShipmentBackToWHRTO(String orderId) throws IOException, JAXBException, InterruptedException, ManagerException, JSONException, XMLStreamException {
		/*Map<String, Object> pickup = DBUtilities.exSelectQueryForSingleRecord("select * from pickup where order_id = "+orderId,"lms");
		if (pickup.size()==0) {
			Assert.fail("Unable to fetch Pickup from LMS");
		}*/
        String origin = "DC", destination = "HUB";
        Long deliveryCenterID;
        long destWarehouseId;
        OrderEntry order = ((OrderResponse)getOrderLMS.apply(orderId)).getOrders().get(0);
        destWarehouseId = Long.parseLong(order.getRtoWarehouseId().toString());
        deliveryCenterID = order.getDeliveryCenterId();
        ShipmentResponse shipmentResponse = createMasterBag(deliveryCenterID, origin, "Bangalore", (long)lmsHelper.getRTHubIdForWH.apply(destWarehouseId), destination, "Bangalore", EnumSCM.NORMAL);
        Assert.assertEquals(shipmentResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "MasterBag creation failed");
        long masterBagId = (Long) shipmentResponse.getEntries().get(0).getId();
//		 ShipmentResponse saveMBResponse = saveMasterBagRT(masterBagId, returnId);
        String saveMBResponse = addAndSaveMasterBag(orderId, "" + masterBagId, ShipmentType.DL);
        Assert.assertEquals(saveMBResponse, EnumSCM.SUCCESS, "Unable to save Master Bag");
        ShipmentResponse closeMBResponse = closeMasterBag(masterBagId);
        Assert.assertEquals(closeMBResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close master bag");
        ShipmentResponse shipMB = shipMasterBag(masterBagId);
        Assert.assertEquals(shipMB.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to ship master bag");
//        Assert.assertEquals(validateOrderStatusInLMS(orderId,EnumSCM.RTO_IN_TRANSIT,3), "order status not in RTO_IN_TRANSIT after shipping masterBag");
        ShipmentResponse inscanResponse = masterBagInScan(masterBagId, EnumSCM.RECEIVED, "Bangalore", deliveryCenterID, "DC");
        Assert.assertEquals(inscanResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to inscan masterBag at WH");
        ExceptionHandler.handleEquals(receiveShipmentFromMasterbag(masterBagId).
                getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive shipment in DC");
        ShipmentResponse inscanUpdateResponse = masterBagInScanUpdate(masterBagId, orderId, "Bangalore", destWarehouseId, "WH", deliveryCenterID);
        if (!inscanUpdateResponse.getStatus().getStatusType().toString().equalsIgnoreCase(EnumSCM.SUCCESS))
            SlackMessenger.send("scm_e2e_order_sanity", "Unable update receive masterBag at WH", 3);
        //Assert.assertEquals(inscanUpdateResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable update receive masterBag at WH");
        //SlackMessenger.send("scm_e2e_order_sanity", "shipment received in configured warehouse");
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(orderId, EnumSCM.RTO,3), "Order status not in RTO in OMS after receieving");
    }

    /**
     * terminalTransitions
     *
     * @param returnId
     * @param destWarehouseId
     * @param deliveryCenterID
     * @param origin
     * @param destination
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws NumberFormatException
     * @throws IOException
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
    public void terminalTransitions(String returnId, long destWarehouseId, long deliveryCenterID, String origin, String destination) throws NumberFormatException, IOException, JAXBException {
        ShipmentResponse shipmentResponse = createMasterBag(deliveryCenterID, origin, "Bangalore", (long)lmsHelper.getRTHubIdForWH.apply(destWarehouseId), destination, "Bangalore", EnumSCM.NORMAL);
        Assert.assertEquals(shipmentResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        long masterBagId = (Long) shipmentResponse.getEntries().get(0).getId();
//		 ShipmentResponse saveMBResponse = saveMasterBagDL(masterBagId, returnId);
        String saveMBResponse = addAndSaveMasterBag(returnId, "" + masterBagId, ShipmentType.PU);
        Assert.assertEquals(saveMBResponse, EnumSCM.SUCCESS, "Unable to save Master Bag");
        ShipmentResponse closeMBResponse = closeMasterBag(masterBagId);
        Assert.assertEquals(closeMBResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close master bag");
        ShipmentResponse shipMB = shipMasterBag(masterBagId);
        Assert.assertEquals(shipMB.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to ship master bag");
    }

    /**
     * createAndMarkOrderToStatus creats the order and marks it to particular status and validate the same and returns orderId
     * @param login
     * @param skus
     * @param toStatus
     * @param checkStatus
     * @param pincode
     * @return
     * @throws Exception
     */
    public String createAndMarkOrderToStatus(String login, String[] skus, ReleaseStatus toStatus, String checkStatus, String pincode) throws Exception {
        End2EndHelper end2EndHelper = new End2EndHelper();
        String orderId = end2EndHelper.createOrder(login, LMS_LOGIN.PASSWORD, pincode, skus, "", false, false, false, "", false);
        String orderReleaseId = omsServiceHelper.getReleaseId(orderId);
        omsServiceHelper.checkReleaseStatusForOrder(orderId,EnumSCM.WP);
        omsServiceHelper.markReadyToDispatchV3ForMyntraSeller(orderReleaseId,ReadyToDispatchType.POSITIVE.name());
        //insertWMSItem(orderReleaseId, 36);
        String packetId = omsServiceHelper.getPacketId(orderId);
        ExceptionHandler.handleEquals(omsServiceHelper.pushPacketToLms(packetId).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, "PK", 15));
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderId, 15));
        log.info("Order ID : " + orderId);
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, toStatus).shipmentSource(ShipmentSource.MYNTRA).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        return orderId;
    }

    /**
     * createAndMarkOrderToStatus
     *
     * @param skus
     * @param toStatus
     * @param checkStatus
     * @param pincode
     * @param isTod
     * @return
     * @throws Exception
     */
    public String createAndMarkOrderToStatus(String login, String[] skus, ReleaseStatus toStatus, String checkStatus, String pincode, boolean isTod) throws Exception {
        End2EndHelper end2EndHelper = new End2EndHelper();
        String orderID = end2EndHelper.createOrder(login, LMS_LOGIN.PASSWORD, pincode, skus, "", false, false, false, "", isTod);
        String orderReleasId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleasId, "WP", 15));
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
        log.info("Order ID : " + orderID);
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(orderReleasId, toStatus).shipmentSource(ShipmentSource.MYNTRA).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        return orderID;
    }

    /**
     * createAndMarkOrderToStatus
     *
     * @param skus
     * @param toStatus
     * @param checkStatus
     * @param pincode
     * @param isTod
     * @param paymentMode
     * @return
     * @throws Exception
     */
    public String createAndMarkOrderToStatus(String login, String[] skus, ReleaseStatus toStatus, String checkStatus, String pincode, boolean isTod, PaymentMode paymentMode) throws Exception {
        End2EndHelper end2EndHelper = new End2EndHelper();
        String orderID = end2EndHelper.createOrder(login, LMS_LOGIN.PASSWORD, pincode, skus, "", false, false, false, "", isTod, paymentMode);
        String orderReleasId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleasId, "WP", 15));
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
        if (!paymentMode.equals(PaymentMode.COD)){
            end2EndHelper.changePaymentMethodOMS(""+orderID,"on");
        }
        log.info("Order ID : " + orderID);
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(orderReleasId, toStatus).shipmentSource(ShipmentSource.MYNTRA).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        return orderID;
    }

    /**
     * createAndMarkOrderToStatus
     * @param login
     * @param skus
     * @param toStatus
     * @param checkStatus
     * @param pincode
     * @param isTod
     * @param paymentMode
     * @param shippingMethod
     * @return
     * @throws Exception
     */
    public String createAndMarkOrderToStatus(String login, String[] skus, ReleaseStatus toStatus, String checkStatus, String pincode, boolean isTod, PaymentMode paymentMode, String shippingMethod) throws Exception {
        End2EndHelper end2EndHelper = new End2EndHelper();
        String orderID = end2EndHelper.createOrder(login, LMS_LOGIN.PASSWORD, pincode, skus, "", false, false, false, "", isTod, paymentMode);
        String orderReleasId = omsServiceHelper.getReleaseId(orderID);
        DBUtilities.exUpdateQuery("update order_release set shipping_method = '" + shippingMethod + "' where order_id_fk = '" + orderID+"'", "oms");
        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleasId, "WP", 15));
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
        if (!paymentMode.equals(PaymentMode.COD)){
            end2EndHelper.changePaymentMethodOMS(""+orderID,"on");
        }
        log.info("Order ID : " + orderID);
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(orderReleasId, toStatus).shipmentSource(ShipmentSource.MYNTRA).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        return orderID;
    }

    /**
     * fetchAddressIdForLogin
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.BiFunction fetchAddressIdForLogin = (login,pincode)-> DBUtilities.exSelectQueryForSingleRecord
            ("select id from `mk_customer_address` where login = '"+ideaApiHelper.getUIDXForLoginViaEmail("myntra",
                    login.toString())+"' and pincode = "+pincode, "myntra_Address").get("id");

    /**
     * AcknowledgeApprovePickup
     *
     * @param dcId
     * @param trackingNo
     * @throws Exception
     */
    public void AcknowledgeApprovePickup(long dcId, String trackingNo) throws IOException {
        MLShipmentUpdate mlShipmentUpdate = new MLShipmentUpdate.Builder(dcId, trackingNo, MLShipmentUpdateEvent.ACKNOWLEDGE_APPROVE_ONHOLD_PICKUP_WITH_DC, null)
                .eventLocation("DC-" + dcId).remarks("ACKNOWLEDGE").event(MLShipmentUpdateEvent.ACKNOWLEDGE_APPROVE_ONHOLD_PICKUP_WITH_DC).shipmentType(ShipmentType.PU).eventTime(new DateTime()).build();
        String payload = "[" + APIUtilities.getObjectToJSON(mlShipmentUpdate) + "]";
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.UPDATE_ML_SHIPMENT, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderJSON());
        Assert.assertEquals(service.getResponseStatus(), 200, "service not responded with '200'");
        APIUtilities.validateResponse("json", service.getResponseBody(), "mlShipmentResponse.status.statusType=='SUCCESS'");
    }

    /**
     * mlShipmentUpdate
     *
     * @param dcId
     * @param trackingNo
     * @param event
     * @param remarks
     * @param shipmentType
     * @throws Exception
     */
    public String mlShipmentUpdate(long dcId, String trackingNo, MLShipmentUpdateEvent event, String remarks, ShipmentType shipmentType) throws IOException, JAXBException{
        MLShipmentUpdate mlShipmentUpdate = new MLShipmentUpdate.Builder(dcId, trackingNo, event, null)
                .eventLocation("DC-" + dcId).remarks(remarks).event(event).shipmentType(shipmentType).eventTime(new DateTime()).shipmentUpdateActivitySource(ShipmentUpdateActivityTypeSource.CustomerCare).build();
        String payload = "[" + APIUtilities.getObjectToJSON(mlShipmentUpdate) + "]";
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.UPDATE_ML_SHIPMENT, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderJSON());
        Assert.assertEquals(service.getResponseStatus(), 200, "service not responded with '200'");
        APIUtilities.validateResponse("json", service.getResponseBody(), "mlShipmentResponse.status.statusType=='SUCCESS'");
        return APIUtilities.getElement( service.getResponseBody(), "mlShipmentResponse.status.statusType","json");
    }

    /**
     * getReturnsInLMS
     *
     * @param returnId
     * @return
     * @throws Exception
     */
    public OrderResponse getReturnsInLMS(String returnId) throws UnsupportedEncodingException, JAXBException{
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_RETURN, new String[]{returnId}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        OrderResponse response = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderResponse());
        return response;
    }

    /**
     * getOrderTrackingDetail
     *
     * @param courierCode
     * @param trackingNumber
     * @return
     * @throws Exception
     */
    public OrderTrackingResponse getOrderTrackingDetail(String courierCode, String trackingNumber) throws UnsupportedEncodingException, JAXBException{
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_ORDER_TRACKING_DETAIL, new String[]{courierCode, trackingNumber}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        OrderTrackingResponse response = (OrderTrackingResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderTrackingResponse());
        return response;
    }

    /**
     * uploadBulkOrderTracking
     * @param orderId
     * @param activityType
     * @return
     * @throws IOException
     * @throws JAXBException
     * @throws InterruptedException
     * @throws XMLStreamException
     * @throws ManagerException
     * @throws JSONException
     */
    @SuppressWarnings("unchecked")
    public OrderTrackingResponse uploadBulkOrderTracking(String orderId, String activityType) throws IOException, JAXBException, InterruptedException, XMLStreamException, ManagerException, JSONException {
        OrderEntry orderEntry = ((OrderResponse)getOrderLMS.apply(orderId)).getOrders().get(0);
        OrderTrackingEntry orderTracking = new OrderTrackingEntry();
        OrderTrackingDetailEntry orderTrackingDetail = new OrderTrackingDetailEntry();
        List<OrderTrackingDetailEntry> orderTrackingDetails = new ArrayList<>();
        orderTrackingDetail.setActivityType(activityType);
        orderTrackingDetail.setExtTrackingCode(activityType);
        orderTrackingDetail.setRemark("automation update");
        orderTrackingDetail.setLocation("Bangalore");
        orderTrackingDetail.setActionDate(new Date());
        orderTrackingDetails.add(orderTrackingDetail);
        orderTracking.setTrackingNumber(orderEntry.getTrackingNumber());
        orderTracking.setOrderId(orderId);
        orderTracking.setCourierOperator(orderEntry.getCourierOperator());
        orderTracking.setOrderTrackingDetailEntry(orderTrackingDetails);
        OrderTrackingResponse orderTrackingResponse = new OrderTrackingResponse();
        List<OrderTrackingEntry> orderTrackingEntries = new ArrayList<>();
        orderTrackingEntries.add(orderTracking);
        orderTrackingResponse.setOrderTrackings(orderTrackingEntries);
        String payload = APIUtilities.convertXMLObjectToString(orderTrackingResponse);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.BULK_UPLOAD_ORDER_TRACKING, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        OrderTrackingResponse response = (OrderTrackingResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderTrackingResponse());
        return response;
    }

    /**
     * getOrderTrackingDetailV2
     * @param courierCode
     * @param trackingNumber
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public OrderTrackingResponse getOrderTrackingDetailV2(String courierCode, String trackingNumber) throws UnsupportedEncodingException, JAXBException{
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_ORDER_TRACKING_DETAIL_V2, new String[]{"getOrderTrackingDetail?courierOperator="+courierCode+"&trackingNumber="+trackingNumber+"&level=LEVEL2"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        OrderTrackingResponse response = (OrderTrackingResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new OrderTrackingResponse());
        return response;
    }

    /**
     * printShipmentLabel
     *
     * @param shipmentId
     * @return
     * @throws Exception
     */
    public String printShipmentLabel(String shipmentId) throws UnsupportedEncodingException, JAXBException{
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.SHIPMENT_LABEL, new String[]{shipmentId}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        return service.getResponseBody();
    }

    /**
     * shipmentManifestReport
     *
     * @param shipmentId
     * @param courierCode
     * @return
     * @throws Exception
     */
    public ShipmentResponse shipmentManifestReport(String shipmentId, String courierCode) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.SHIPMENT_MANIFEST, new String[]{courierCode, shipmentId}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        ShipmentResponse response = (ShipmentResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new ShipmentResponse());
        return response;
    }

    /**
     * getHub
     *
     * @param param
     * @return
     * @throws Exception
     */
    public HubResponse getHub(String param) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.HUB, new String[]{param}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        HubResponse response = (HubResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new HubResponse());
        return response;
    }

    public HubResponse getHubByCode(String hubCode) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.HUB, new String[]{"search?q=code.like:"+hubCode+"&start=0&fetchSize=20&sortBy=code&sortOrder=ASC"}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        HubResponse response = (HubResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new HubResponse());
        return response;
    }

    /**
     * addHub
     * @param code
     * @param name
     * @param manager
     * @param address
     * @param city
     * @param state
     * @param pincode
     * @param type
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public HubResponse addHub(String code, String name, String manager, String address,
                              String city, String state, String pincode, HubType type)
            throws  IOException, JAXBException {
        HubEntry hub = new HubEntry();
        hub.setCode(code);
        hub.setName(name);
        hub.setManager(manager);
        hub.setAddress(address);
        hub.setCity(city);
        hub.setState(state);
        hub.setPincode(pincode);
        hub.setType(type);
        hub.setContactNumber("1234567890");
        hub.setCreatedBy("Automation");
        hub.setActive(true);
        hub.setTmsTransportHubCode("TH-DEL");
        String payload = APIUtilities.convertXMLObjectToString(hub);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.HUB, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        HubResponse response = (HubResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new HubResponse());
        return response;
    }

    /**
     * editHub
     * @param id
     * @param code
     * @param name
     * @param manager
     * @param address
     * @param city
     * @param state
     * @param pincode
     * @param type
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public HubResponse editHub(long id,String code, String name, String manager, String address,
                               String city, String state, String pincode, HubType type)
            throws  IOException, JAXBException {
        HubEntry hub = new HubEntry();
        hub.setId(id);
        hub.setCode(code);
        hub.setName(name);
        hub.setManager(manager);
        hub.setAddress(address);
        hub.setCity(city);
        hub.setState(state);
        hub.setPincode(pincode);
        hub.setType(type);
        hub.setContactNumber("1234567890");
        hub.setCreatedBy("Automation");
        hub.setActive(true);
        String payload = APIUtilities.convertXMLObjectToString(hub);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.HUB, new String[]{""+id}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        HubResponse response = (HubResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new HubResponse());
        return response;
    }

    /**
     * getAllCourierDetail
     *
     * @param param
     * @return
     * @throws Exception
     */
    public CourierResponse getAllCourierDetail(String param) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_ALL_COURIER, new String[]{param}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        CourierResponse response = (CourierResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
                new CourierResponse());
        return response;
    }

    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Supplier getAttemptReasonCode=()-> {
         return (AttemptReasonCodeResponse) APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_FD_REASONS, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(),
                new AttemptReasonCodeResponse());
    };

    /**
     * getQCPendencyPage
     *
     * @param param
     * @return
     * @throws Exception
     */
    public String getQCPendencyPage(String param) throws UnsupportedEncodingException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.MLSHIPMENT_SERVICE, new String[]{param}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        return service.getResponseBody();
    }


    /**
     * masterTripUpdate: This is Master Trip: trip complete update containing DL/PU/EX/TOD orders
     *
     * @param tripOrderAssignmentIdTOD
     * @param orderIdTOD
     * @param inputskuIDs
     * @param toadIDDL
     * @param toadIDEX
     * @param toadIDPU
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public TripOrderAssignmentResponse masterTripUpdate(long tripOrderAssignmentIdTOD, String orderIdTOD, Map<String, String> inputskuIDs, long toadIDDL, long toadIDEX, long toadIDPU) throws JAXBException, UnsupportedEncodingException {
        //TOD
        com.myntra.lms.client.response.OrderEntry orderEntry = new com.myntra.lms.client.response.OrderEntry();
        TripOrderAssignmentResponse tripOrderAssignmentResponse = new TripOrderAssignmentResponse();
        TripOrderAssignementEntry tripOrderAssignementEntryTOD = new TripOrderAssignementEntry();
        TripOrderAssignementEntry tripOrderAssignementEntryDL = new TripOrderAssignementEntry();
        TripOrderAssignementEntry tripOrderAssignementEntryPU = new TripOrderAssignementEntry();
        TripOrderAssignementEntry tripOrderAssignementEntryEX = new TripOrderAssignementEntry();

        Map<String, Object> mlShipment = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from ml_shipment where source_reference_id = " + orderIdTOD, "lms");
        String mlShipmentId = mlShipment.get("id").toString();
        String dcId = mlShipment.get("delivery_center_id").toString();
        double amountToCollect = 0;
        DBUtilities.exSelectQuery("select id from ml_try_and_buy_item where ml_trynbuy_shipment_id = " + mlShipmentId, "lms");
//        DeliveryPickupReasonCode s = DeliveryPickupReasonCode.DELIVERED;
        AttemptReasonCode s = AttemptReasonCode.DELIVERED;
        TripAction ta = TripAction.TRIP_COMPLETE;
        tripOrderAssignementEntryTOD.setId(tripOrderAssignmentIdTOD);// assignOrderToTripBulk
        tripOrderAssignementEntryTOD.setRemark("test");
//        tripOrderAssignementEntryTOD.setDeliveryReasonCode(s);
        tripOrderAssignementEntryTOD.setAttemptReasonCode(s);
        tripOrderAssignementEntryTOD.setTripAction(ta);
        tripOrderAssignementEntryTOD.setUpdatedVia(UpdatedVia.WEB);
        tripOrderAssignementEntryTOD.setPaymentType("CASH");
        tripOrderAssignementEntryTOD.setIsOutScanned(false);
        tripOrderAssignementEntryTOD.setTriedAndBoughtDuration(123L);
        tripOrderAssignementEntryTOD.setShipmentType(ShipmentType.TRY_AND_BUY);
        List<TripOrderAssignementEntry> tripOrderAssignementEntries = new ArrayList<>();
        orderEntry.setOrderId(orderIdTOD);
        orderEntry.setDeliveryCenterId(Long.parseLong(dcId));
        List<ItemEntry> itemEntries = new ArrayList<>();
        List<Map<String, Object>> skuIdLists = (List<Map<String, Object>>) DBUtilities.exSelectQuery("select mtabi.id,tnbsi.sku_id from try_and_buy_shipment_item "
                + "tnbsi,ml_try_and_buy_item mtabi,ml_shipment mlsh where mlsh.source_reference_id=" + orderIdTOD + " and mtabi.ml_trynbuy_shipment_id=mlsh.id and "
                + "mtabi.source_item_reference_id=tnbsi.id", "myntra_lms");
        Map<String, String> skuMap = new TreeMap<>();
        for (Map<String, Object> abc : skuIdLists) {
            String skuID = "" + abc.get("sku_id");
            if (skuMap.containsKey(skuID)) {
                String ids = skuMap.get(skuID) + "," + abc.get("id");
                skuMap.put(skuID, ids);
            } else {
                skuMap.put(skuID, "" + abc.get("id"));
            }
        }
        Iterator it = skuMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String[] idList = pair.getValue().toString().split(",");
            String[] tnbList = inputskuIDs.get(pair.getKey()).toString().split(",");
            int i = 0;
            for (String id : idList) {
                ItemEntry itemEntry = new ItemEntry();
                itemEntry.setId(Long.parseLong(id));
                itemEntry.setRemarks("Test");
                if (tnbList[i].equalsIgnoreCase(EnumSCM.TRIED_AND_NOT_BOUGHT)) {
                    itemEntry.setStatus(TryAndBuyItemStatus.TRIED_AND_NOT_BOUGHT);
                    itemEntry.setTriedAndNotBoughtReason(TryAndBuyNotBoughtReason.DID_NOT_LIKE_DESIGN);
                    itemEntry.setQcStatus(ItemQCStatus.PASSED);
                } else if (tnbList[i].equalsIgnoreCase(EnumSCM.SNATCHED)) {
                    itemEntry.setStatus(TryAndBuyItemStatus.SNATCHED);
                } else if (tnbList[i].equalsIgnoreCase(EnumSCM.NOT_TRIED)) {
                    itemEntry.setStatus(TryAndBuyItemStatus.NOT_TRIED);
                    amountToCollect = Double.parseDouble(lmsHelper.getTODItemCodAmount(id));
                } else {
                    itemEntry.setStatus(TryAndBuyItemStatus.TRIED_AND_BOUGHT);
                    amountToCollect = Double.parseDouble(lmsHelper.getTODItemCodAmount(id));
                }
                i++;
                itemEntries.add(itemEntry);
            }
        }
        orderEntry.setItemEntries(itemEntries);
        orderEntry.setCodAmount(amountToCollect);
        tripOrderAssignementEntryTOD.setOrderEntry(orderEntry);
        tripOrderAssignementEntries.add(tripOrderAssignementEntryTOD);

        //DL
        tripOrderAssignementEntryDL.setId(toadIDDL);// assignOrderToTripBulk
        tripOrderAssignementEntryDL.setRemark("test");
//        tripOrderAssignementEntryDL.setDeliveryReasonCode(s);
        tripOrderAssignementEntryDL.setAttemptReasonCode(s);
        tripOrderAssignementEntryDL.setTripAction(ta);
        tripOrderAssignementEntryDL.setUpdatedVia(UpdatedVia.WEB);
        tripOrderAssignementEntryDL.setPaymentType("CASH");
        tripOrderAssignementEntryDL.setIsOutScanned(true);
        tripOrderAssignementEntries.add(tripOrderAssignementEntryDL);

        //EX
        tripOrderAssignementEntryEX.setId(toadIDEX);// assignOrderToTripBulk
        tripOrderAssignementEntryEX.setRemark("test");
//        tripOrderAssignementEntryEX.setDeliveryReasonCode(s);
        tripOrderAssignementEntryEX.setAttemptReasonCode(s);
        tripOrderAssignementEntryEX.setTripAction(ta);
        tripOrderAssignementEntryEX.setUpdatedVia(UpdatedVia.WEB);
        tripOrderAssignementEntryEX.setPaymentType("CASH");
        tripOrderAssignementEntryEX.setIsOutScanned(true);
        tripOrderAssignementEntries.add(tripOrderAssignementEntryEX);

        //PU
        tripOrderAssignementEntryPU.setId(toadIDPU);// assignOrderToTripBulk
        tripOrderAssignementEntryPU.setRemark("test");
        tripOrderAssignementEntryPU.setAttemptReasonCode(AttemptReasonCode.PICKED_UP_SUCCESSFULLY);
        tripOrderAssignementEntryPU.setTripAction(ta);
        tripOrderAssignementEntryPU.setUpdatedVia(UpdatedVia.WEB);
        tripOrderAssignementEntryPU.setPaymentType("CASH");
        tripOrderAssignementEntryPU.setIsOutScanned(true);
        tripOrderAssignementEntries.add(tripOrderAssignementEntryPU);

        tripOrderAssignmentResponse.setTripOrderAssignmentEntries(tripOrderAssignementEntries);
        String payload = APIUtilities.convertXMLObjectToString(tripOrderAssignmentResponse);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.TRIP_UPDATE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        TripOrderAssignmentResponse shipmentResponse = (TripOrderAssignmentResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new TripOrderAssignmentResponse());
        return shipmentResponse;
    }

    /**
     * serviceabilityUpdate
     * @param env
     * @param warehouseId
     * @param pincode
     * @param courier
     * @return
     * @throws IOException
     */
    public ServiceabilityUtilResponse serviceabilityUpdate(String env,String warehouseId, String[] pincode, String[] courier) throws IOException {
        ServiceabilityUtilEntry entry = new ServiceabilityUtilEntry();
        entry.setEnv(env);
        entry.setWarehouseid(warehouseId);
        entry.setPincodes(pincode);
        entry.setCourier(courier);
        String payload = APIUtilities.getObjectToJSON(entry);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.SERVICEABILITY, null, SERVICE_TYPE.SERVICEABILITY_UTIL.toString(), HTTPMethods.PUT, payload, Headers.getBasicHeaderJSON());
        ServiceabilityUtilResponse response = (ServiceabilityUtilResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ServiceabilityUtilResponse());
        return response;
    }

    /**
     * refreshServiceabilityForRegion : Trigger refresh serviceability for a particular region
     * Object region
     */
    @SuppressWarnings("rawtypes")
	public LambdaInterfaces.Function refreshServiceabilityForRegion = region -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.RELOADSERVICEABILITY, new String[]{region.toString()}, SERVICE_TYPE.LMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new CourierServiceabilityResponse());

    /**
     * refreshServiceabilityFull : Trigger refresh serviceability for All available regions, Please use this api carefully because it will flood the queue
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Supplier refreshServiceabilityFull = () -> APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.RELOADSERVICEABILITY, null, SERVICE_TYPE.LMS_SVC.toString(),
            HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new CourierServiceabilityResponse());

    /**
     * createReturn
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.BiFunction createReturn = (releaseId, pincode)->{
    		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(""+releaseId);
        ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.OPEN_BOX_PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Chandigarh", "CG", ""+pincode, "ML");
        Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
        String returnId = ""+returnResponse.getData().get(0).getId();
        validateRmsLmsReturnCreation(returnId);
        return returnId;
    };

    /**
     * createReturnAndPickupSuccessFul
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public LambdaInterfaces.TriFunction createReturnAndPickupSuccessFul = (releaseId, pincode, dcId)->{
        String returnID = createReturn.apply(releaseId,pincode).toString();
        validateRmsLmsReturnCreation(returnID);
        String deliveryStaffID = getDeliveryStaffID("" + dcId);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = createTrip((int)dcId, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(assignOrderToTrip(tripId, lmsHelper.getReturnsTrackingNumber.apply(returnID).toString()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(validateOrderStatusInML(returnID, EnumSCM.ASSIGNED_TO_SDA, 2));
        Assert.assertEquals(startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to startTrip");
        Assert.assertTrue(validateOrderStatusInML(returnID, EnumSCM.OUT_FOR_PICKUP, 2));
        Assert.assertEquals(((TripOrderAssignmentResponse) unassignOrderFronTrip.apply(lmsHelper.getTripOrderAssignemntIdForReturn.apply(returnID))).getStatus().getStatusType().toString(), EnumSCM.ERROR);
        TripOrderAssignmentResponse response = updatePickupInTrip((long)lmsHelper.getTripOrderAssignemntIdForReturn.apply(returnID), EnumSCM.PICKED_UP_SUCCESSFULLY, EnumSCM.UPDATE);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        TripOrderAssignmentResponse response1 = updatePickupInTrip((long)lmsHelper.getTripOrderAssignemntIdForReturn.apply(returnID), EnumSCM.PICKED_UP_SUCCESSFULLY, EnumSCM.TRIP_COMPLETE);
        Assert.assertEquals(response1.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
        SlackMessenger.send("scm_e2e_order_sanity", "Item picked up successfully");
     //   Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnID,EnumSCM.PICKUP_SUCCESSFUL,5), "return is not PICKUP_SUCCESSFUL" );
        return returnID;
    };

    /**
     * getDispatchHubFromWarehouse
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
 

    /**
     * getHubCourierConfig
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.BiFunction getHubCourierConfig = (hubCode, courierCode)-> {
        return APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.HUB_HANDOVER_CONFIG, new String[]{"getHubCourierConfig?hubCode="+hubCode+"&courierCode="+courierCode}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(),
                new HubCourierHandoverConfigResponse());
    };

    /**
     * getCourierByPincode
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getCourierByPincode = (pincode)-> {
        return APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.COURIER_BY_PINCODE, new String[]{""+pincode}, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(),
                new PincodeResponse());
    };

    /**
     * getShipmentSource : get the shipment source : MYNTRA/JABONG
     */
    @SuppressWarnings("rawtypes")
    public com.myntra.apiTests.common.Constants.LambdaInterfaces.Function getShipmentSource = name -> {
        ShipmentSourceResponse response = (ShipmentSourceResponse)APIUtilities.convertXMLStringToObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.SHIPMENT_SOURCE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.GET, null, Headers.getLmsHeaderXML()).getResponseBody(), new ShipmentSourceResponse());
        List<ShipmentSourceEntry> collect = response.getShipmentSourceEntries().stream().filter(x -> x.getName().equalsIgnoreCase(name.toString())).collect(Collectors.toList());
        return collect.get(0).getReferenceId();
    };

    /**
     * generateBagLabelForHub
     * @param hubCode
     * @return
     */
    public synchronized long generateBagLabelForHub(String hubCode){
        DBUtilities.exUpdateQuery("INSERT INTO `consolidation_bag` (`id`,`dispatch_hub_code`, `status`, `created_by`) VALUES("+((long)getMaxConsolidationId.get()+1)+",'"+hubCode+"', 'NEW','Automation')","lms");
        return (long)DBUtilities.exSelectQueryForSingleRecord("select id from consolidation_bag where status = 'NEW' order by last_modified_on DESC","lms").get("id");
    }

    /**
     * getTrackingNumberForJabong : this method use to return a unique tracking number using myntra_test database.
     * @return
     */
    public synchronized String getTrackingNumberForJabong(){
        long sequnce = (long) DBUtilities.exSelectQueryForSingleRecord("select id from tracking_number where status = 0 order by id ASC limit 1","myntra_test").get("id");
        DBUtilities.exUpdateQuery("update tracking_number set status = 1 where id = "+sequnce,"myntra_test");
        return "JBML"+sequnce;
    }

    /**
     * getMaxConsolidationId
     */
    @SuppressWarnings("rawtypes")
    public Supplier getMaxConsolidationId = ()->{
        return (Long)DBUtilities.exSelectQueryForSingleRecord("select max(id) from consolidation_bag","lms").get("max(id)");};

    /**
     * closeConsolidationBag
     * @param orderId
     * @param courierCode
     * @param shippingMethod
     * @param dcCode
     * @param consolidationBag
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public EmptyResponse closeConsolidationBag(String orderId,String courierCode,String shippingMethod, String dcCode, String consolidationBag) throws IOException, JAXBException {
        ConsolidationShipment shipment = new ConsolidationShipment();
        List<ConsolidationShipment> shipments = new ArrayList<>();
        Client_data client_data = new Client_data();
        com.myntra.apiTests.erpservices.lms.lmsClient.ConsolidationBagShipmentResponse cb = new com.myntra.apiTests.erpservices.lms.lmsClient.ConsolidationBagShipmentResponse();
        shipment.setBreadth("26.1");
        shipment.setHeight("6.5");
        shipment.setLength("34.8");
        shipment.setWeight("445.0");
        shipment.setAwb(""+orderId);
        shipments.add(shipment);
        cb.setShipments(shipments);
        client_data.setDestinationCode(dcCode);
        client_data.setShippingMethod(shippingMethod);
        client_data.setCourierCode(courierCode);
        client_data.setIsFootwear("false");
        cb.setClient_data(client_data);
        cb.setBagseal(consolidationBag);
        cb.setShipment_count(1);
        String payload = APIUtilities.getObjectToJSON(cb);
        EmptyResponse respose = (EmptyResponse)APIUtilities.getJsontoObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.CONSOLIDATION_BAG_CLOSE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.PUT, payload, Headers.getLmsHeaderJSON()).getResponseBody(), new EmptyResponse());
        return respose;
    }

    /**
     * closeConsolidationBag
     * @param orderIds
     * @param courierCode
     * @param shippingMethod
     * @param dcCode
     * @param consolidationBag
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public EmptyResponse closeConsolidationBag(List<String> orderIds,String courierCode,String shippingMethod, String dcCode, String consolidationBag) throws IOException, JAXBException {
        List<ConsolidationShipment> shipments = new ArrayList<>();
        Client_data client_data = new Client_data();
        com.myntra.apiTests.erpservices.lms.lmsClient.ConsolidationBagShipmentResponse cb = new com.myntra.apiTests.erpservices.lms.lmsClient.ConsolidationBagShipmentResponse();
        orderIds.forEach(orderId->{ConsolidationShipment shipment = new ConsolidationShipment();
            shipment.setBreadth("26.1");
            shipment.setHeight("6.5");
            shipment.setLength("34.8");
            shipment.setWeight("445.0");
            shipment.setAwb(""+orderId);
            shipments.add(shipment);});

        cb.setShipments(shipments);
        client_data.setDestinationCode(dcCode);
        client_data.setShippingMethod(shippingMethod);
        client_data.setCourierCode(courierCode);
        client_data.setIsFootwear("false");
        cb.setClient_data(client_data);
        cb.setBagseal(consolidationBag);
        cb.setShipment_count(1);
        String payload = APIUtilities.getObjectToJSON(cb);
        EmptyResponse respose = (EmptyResponse)APIUtilities.getJsontoObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.CONSOLIDATION_BAG_CLOSE, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.PUT, payload, Headers.getLmsHeaderJSON()).getResponseBody(), new EmptyResponse());
        return respose;
    }

    /**
     * getMasterBagIdForConsolidationBag
     */
    @SuppressWarnings("rawtypes")
    public LambdaInterfaces.Function getMasterBagIdForConsolidationBag = consolidationBagId->{
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.CONSOLIDATION_BAG, new String[]{""+consolidationBagId,"masterbag"},
                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderJSON());
        return APIUtilities.getElement(service.getResponseBody(), "shipmentResponse.data.shipment.id", "json");
    };

    /**
     * setRedis : to set a redis key
     */
    @SuppressWarnings("rawtypes")
    public BiConsumer setRedis = (key, value)-> redisUtil.setValue("testRedis",(String)key,(String)value);

    /**
     * getRedis : to get a redis key
     */
    @SuppressWarnings("rawtypes")
    public Function getRedis = key-> redisUtil.getValue("testRedis",(String)key);

    /**
     * createJabongShipment
     * @param orderId
     * @param trackingNumber
     * @param courierCode
     * @param pincode
     * @param warehouseId
     * @param shippingMethod
     * @param noOfItem
     * @return
     * @throws IOException
     */
    public String createJabongShipment(String orderId, String trackingNumber, String courierCode, String pincode, String warehouseId, ShippingMethod shippingMethod, int noOfItem) throws IOException {
        Shipment shipment = new Shipment();
        ShipmentItem shipmentItem = new ShipmentItem();
        List<ShipmentItem> shipmentItems = new ArrayList<>();
        shipment.setSourceReferenceId(orderId);
        shipment.setTrackingNumber(trackingNumber);
        shipment.setCourierCode(courierCode);
        shipment.setShipmentType(ShipmentType.DL);
        shipment.setRecipientName("LMS automation");
        shipment.setRecipientAddress("LMS automation test address");
        shipment.setLocality("Kudlu gate");
        shipment.setLandmark("Kudlu gate");
        shipment.setCity("Bangalore");
        shipment.setCountry("india");
        shipment.setStateCode("KA");
        shipment.setPincode(pincode);
        shipment.setRecipientContactNumber("9886373389");
        shipment.setAlternateContactNumber("9886373389");
        shipment.setEmail("lmsautomation@jabong.com");
        shipment.setContentsDescription("Test items");
        shipment.setPromiseDate(DateTime.now().plusDays(3));
        shipment.setWarehouseId(warehouseId);
        shipment.setRtoWarehouseId(warehouseId);
        shipment.setPackedDate(DateTime.now());
        shipment.setShippingMethod(shippingMethod);
        shipment.setPackageWeight((float)213);
        shipment.setPackageLength(214.00);
        shipment.setPackageBreadth(123.00);
        shipment.setPackageHeight(314.00);
        shipment.setSourceId("3763");
        shipment.setStoreId(Shipment.JABONG_STORE_ID);
        shipment.setContainsFootwear(false);
        shipment.setContainsJewellery(false);
        shipment.setFragile(false);
        shipment.setHazmat(false);
        shipment.setLarge(false);
        shipment.setAdditionalCollectableCharges((float)50.00);
        while (noOfItem!=0) {
            shipmentItem.setSourceItemReferenceId("43" + LMSUtils.randomGenn(8));
            shipmentItem.setStyleId("99" + LMSUtils.randomGenn(4));
            shipmentItem.setSkuId("77" + LMSUtils.randomGenn(5));
            shipmentItem.setItemDescription("test skuId for jabong");
            shipmentItem.setImageURL("http://logos-download.com/wp-content/uploads/2016/05/Jabong_logo_logotype.png");
            shipmentItem.setItemBarcode("11" + LMSUtils.randomGenn(8));
            shipmentItem.setItemMRP((float) 2399);
            shipmentItem.setItemValue((float) 1999);
            shipmentItem.setCodAmount((float) 1599);
            shipmentItem.setIntegratedGoodsAndServiceTax((float) 0);
            shipmentItem.setCentralGoodsAndServiceTax((float) 0);
            shipmentItem.setStateGoodsAndServiceTax((float) 0);
            shipmentItem.setTaxAmountPaid(159.00);
            shipmentItem.setAdditionalCharges((float) 0);
            shipmentItem.setSellerName("Jabong seller 1");
            shipmentItem.setSellerAddress("Jabong test address");
            shipmentItem.setSellerId("123");
            shipmentItem.setSellerTaxIdentificationNumber("123459876");
            shipmentItem.setSellerCentralSalesTaxNumber("1234598768");
            shipmentItem.setGoodsAndServiceTaxIdentificationNumber("1234598769");
            shipmentItem.setInvoiceId("INVID" + LMSUtils.randomGenn(7));
            shipmentItem.setElectronicReferenceNumber("987654321");
            shipmentItems.add(shipmentItem);
            noOfItem--;
        }
        shipment.setShipmentItems(shipmentItems);
        String payload = APIUtilities.getObjectToJSON(shipment);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.CREATE_JABONG_SHIPMENT, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderJSON());
        return service.getResponseBody();
    }

    /**
     * createJabongShipment
     * @param shipment {@link Shipment}
     * @return
     * @throws IOException
     */
    public JabongCreateShipmentResponse createJabongShipment(Shipment shipment) throws IOException {
        String payload = APIUtilities.getObjectToJSON(shipment);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.CREATE_JABONG_SHIPMENT, null, SERVICE_TYPE.LMS_SVC.toString(),
                HTTPMethods.POST, payload, Headers.getLmsHeaderJSON());

        return new JabongCreateShipmentResponse(APIUtilities.getElement(service.getResponseBody(),"status.statusType","json"),
                shipment, APIUtilities.getElement(service.getResponseBody(),"status.statusCode","json"),
                APIUtilities.getElement(service.getResponseBody(),"status.statusMessage","json"),
                shipment.getSourceReferenceId(), shipment.getTrackingNumber());
    }


    /**
     * Create Jabong  Shipment Request Payload
     * @param pincode
     * @param warehouseId
     * @param courierCode
     * @param shipmentType
     * @param shippingMethod
     * @param noOfItem
     * @param isJewellery
     * @param isFootware
     * @param isFragile
     * @param isHazmat
     * @param isLarge
     */
    public Shipment createJabongShipmentRequestPayload(String pincode, String warehouseId, String courierCode, ShipmentType shipmentType,
                                            ShippingMethod shippingMethod, int noOfItem, boolean isJewellery, boolean isFootware,
                                            boolean isFragile, boolean isHazmat, boolean isLarge){

        Shipment shipment = new Shipment();
        ShipmentItem shipmentItem = new ShipmentItem();
        List<ShipmentItem> shipmentItems = new ArrayList<>();
        shipment.setSourceReferenceId("JABONG"+LMSUtils.randomGenn(10));
        shipment.setTrackingNumber(getTrackingNumberForJabong());
        if(courierCode != null && !courierCode.equals("TagNotPresent")){
            shipment.setCourierCode(courierCode);
        }
        if(shipmentType != null && shipmentType != null){
            shipment.setShipmentType(shipmentType);
        }
        shipment.setRecipientName("LMS automation");
        shipment.setRecipientAddress("LMS automation test address");
        shipment.setLocality("Kudlu gate");
        shipment.setLandmark("Kudlu gate");
        shipment.setCity("Bangalore");
        shipment.setCountry("india");
        shipment.setStateCode("KA");
        shipment.setPincode(pincode);
        shipment.setRecipientContactNumber("9886373389");
        shipment.setAlternateContactNumber("8904653784");
        shipment.setEmail("lmsautomation@gmail.com");
        shipment.setContentsDescription("Puma Shoes");
        shipment.setPromiseDate(DateTime.now().plusDays(3));

        if(warehouseId != null && !warehouseId.equals("TagNotPresent")){
            shipment.setWarehouseId(warehouseId);
            shipment.setRtoWarehouseId(warehouseId);
        }

        shipment.setPackedDate(DateTime.now());
        shipment.setShippingMethod(shippingMethod);
        shipment.setPackageWeight((float)213);
        shipment.setPackageLength(214.00);
        shipment.setPackageBreadth(123.00);
        shipment.setPackageHeight(314.00);
        shipment.setSourceId("3763");
        shipment.setStoreId(Shipment.JABONG_STORE_ID);
        shipment.setContainsFootwear(isFootware);
        shipment.setContainsJewellery(isJewellery);
        shipment.setFragile(isFragile);
        shipment.setHazmat(isHazmat);
        shipment.setLarge(isLarge);
        shipment.setAdditionalCollectableCharges(100 + new Random().nextFloat() * (200 - 100));
        while (noOfItem!=0) {
            Float itemMRP = 400 + new Random().nextFloat() * (2000 - 400);
            shipmentItem.setSourceItemReferenceId("43" + LMSUtils.randomGenn(8));
            shipmentItem.setStyleId("99" + LMSUtils.randomGenn(4));
            shipmentItem.setSkuId("77" + LMSUtils.randomGenn(5));
            shipmentItem.setItemDescription("test skuId for jabong");
            shipmentItem.setImageURL("http://logos-download.com/wp-content/uploads/2016/05/Jabong_logo_logotype.png");
            shipmentItem.setItemBarcode("11" + LMSUtils.randomGenn(8));
            shipmentItem.setItemMRP(itemMRP);
            shipmentItem.setItemValue(itemMRP-100);
            shipmentItem.setCodAmount(itemMRP-200);
            shipmentItem.setIntegratedGoodsAndServiceTax((float) 0);
            shipmentItem.setCentralGoodsAndServiceTax((float) 0);
            shipmentItem.setStateGoodsAndServiceTax((float) 0);
            shipmentItem.setTaxAmountPaid(50 + new Random().nextDouble() * (200 - 50));
            shipmentItem.setAdditionalCharges((float) 0);
            shipmentItem.setSellerName("Jabong seller 1");
            shipmentItem.setSellerAddress("Jabong test address");
            shipmentItem.setSellerId("123");
            shipmentItem.setSellerTaxIdentificationNumber("123459876");
            shipmentItem.setSellerCentralSalesTaxNumber("1234598768");
            shipmentItem.setGoodsAndServiceTaxIdentificationNumber("1234598769");
            shipmentItem.setInvoiceId("INVID" + LMSUtils.randomGenn(7));
            shipmentItem.setElectronicReferenceNumber("987654321");
            shipmentItems.add(shipmentItem);
            noOfItem--;
        }

        shipment.setShipmentItems(shipmentItems);
        return shipment;
    }

    /**
     *
     * @param pincode
     * @param warehouseId
     * @param courierCode
     * @param shipmentType
     * @param shippingMethod
     * @param noOfItem
     * @param isJewellery
     * @param isFootware
     * @param isFragile
     * @param isHazmat
     * @param isLarge
     * @return
     */
    public Shipment createJabongShipmentRequestPayloadOnline(String pincode, String warehouseId, String courierCode, ShipmentType shipmentType,
                                                       ShippingMethod shippingMethod, int noOfItem, boolean isJewellery, boolean isFootware,
                                                       boolean isFragile, boolean isHazmat, boolean isLarge){

        Shipment shipment = new Shipment();
        ShipmentItem shipmentItem = new ShipmentItem();
        List<ShipmentItem> shipmentItems = new ArrayList<>();
        shipment.setSourceReferenceId("JABONG67"+LMSUtils.randomGenn(10));
        shipment.setTrackingNumber(getTrackingNumberForJabong());
        shipment.setCourierCode(courierCode);
        shipment.setShipmentType(shipmentType);
        shipment.setRecipientName("LMS automation");
        shipment.setRecipientAddress("LMS automation test address");
        shipment.setLocality("Kudlu gate");
        shipment.setLandmark("Kudlu gate");
        shipment.setCity("Bangalore");
        shipment.setCountry("india");
        shipment.setStateCode("KA");
        shipment.setPincode(pincode);
        shipment.setRecipientContactNumber("1234567890");
        shipment.setAlternateContactNumber("1234567890");
        shipment.setEmail("lmsautomation@jabong.com");
        shipment.setContentsDescription("Test items");
        shipment.setPromiseDate(DateTime.now().plusDays(3));
        shipment.setWarehouseId(warehouseId);
        shipment.setRtoWarehouseId(warehouseId);
        shipment.setPackedDate(DateTime.now());
        shipment.setShippingMethod(shippingMethod);
        shipment.setPackageWeight((float)213);
        shipment.setPackageLength(214.00);
        shipment.setPackageBreadth(123.00);
        shipment.setPackageHeight(314.00);
        shipment.setSourceId("3763");
        shipment.setStoreId(Shipment.JABONG_STORE_ID);
        shipment.setContainsFootwear(isFootware);
        shipment.setContainsJewellery(isJewellery);
        shipment.setFragile(isFragile);
        shipment.setHazmat(isHazmat);
        shipment.setLarge(isLarge);
        shipment.setAdditionalCollectableCharges(0F);
        while (noOfItem!=0) {
            Float itemMRP = 400 + new Random().nextFloat() * (2000 - 400);
            shipmentItem.setSourceItemReferenceId("43" + LMSUtils.randomGenn(8));
            shipmentItem.setStyleId("99" + LMSUtils.randomGenn(4));
            shipmentItem.setSkuId("77" + LMSUtils.randomGenn(5));
            shipmentItem.setItemDescription("test skuId for jabong");
            shipmentItem.setImageURL("http://logos-download.com/wp-content/uploads/2016/05/Jabong_logo_logotype.png");
            shipmentItem.setItemBarcode("11" + LMSUtils.randomGenn(8));
            shipmentItem.setItemMRP(itemMRP);
            shipmentItem.setItemValue(itemMRP-100);
            shipmentItem.setCodAmount(0F);
            shipmentItem.setIntegratedGoodsAndServiceTax((float) 0);
            shipmentItem.setCentralGoodsAndServiceTax((float) 0);
            shipmentItem.setStateGoodsAndServiceTax((float) 0);
            shipmentItem.setTaxAmountPaid(50 + new Random().nextDouble() * (200 - 50));
            shipmentItem.setAdditionalCharges(0F);
            shipmentItem.setSellerName("Jabong seller 1");
            shipmentItem.setSellerAddress("Jabong test address");
            shipmentItem.setSellerId("123");
            shipmentItem.setSellerTaxIdentificationNumber("123459876");
            shipmentItem.setSellerCentralSalesTaxNumber("1234598768");
            shipmentItem.setGoodsAndServiceTaxIdentificationNumber("1234598769");
            shipmentItem.setInvoiceId("INVID" + LMSUtils.randomGenn(7));
            shipmentItem.setElectronicReferenceNumber("987654321");
            shipmentItems.add(shipmentItem);
            noOfItem--;
        }

        shipment.setShipmentItems(shipmentItems);
        return shipment;
    }


    /**
     * processOrderInLMSFromPKToSH
     * @param lmsOrderEntries
     * @return
     * @throws Exception
     */
    public Long processOrderInLMSFromPKToSH(LMSOrderEntries lmsOrderEntries) throws Exception {
        End2EndHelper.sleep(10000);

        long masterBagId = createMasterBag(lmsOrderEntries.getDcID(), lmsOrderEntries.getWareHouseID(), lmsOrderEntries.getShippingMethod()).getEntries().iterator().next().getId();

        //In-Scan Orders and add it to MasterBag
        for (LMSOrderEntry masterBagOrdrEntry : lmsOrderEntries.getOrderEntries()) {
            if(!orderInScanNew(masterBagOrdrEntry.getOrderID(), lmsOrderEntries.getWareHouseID(), true).equals(EnumSCM.SUCCESS)){
                throw new ManagerException("Shipment InScan Failed", 2001);
            }
            End2EndHelper.sleep(5000);
            if(!addAndSaveMasterBag(masterBagOrdrEntry.getOrderID(), "" + masterBagId, masterBagOrdrEntry.getShipmentType()).equals(EnumSCM.SUCCESS)){
                throw new ManagerException("Add to MasterBag Failed", 2001);
            }
        }
        //Close Master Bag
        ShipmentResponse shipmentResponse = closeMasterBag(masterBagId);
        if(!shipmentResponse.getStatus().isSuccess()){
            throw new ManagerException(shipmentResponse.getStatus().getStatusMessage(), shipmentResponse.getStatus().getStatusCode());
        }

        //Scan MasterBag in Transport Hub
        scanMasterBagInTransportHubV1(masterBagId);
        //Ship MasterBag
        shipmentResponse = shipMasterBag(masterBagId, lmsHelper.getTransporter(Long.parseLong(lmsOrderEntries.getWareHouseID()), lmsOrderEntries.getDcID()));
        if(!shipmentResponse.getStatus().getStatusType().equals(StatusResponse.Type.SUCCESS)){
            throw new ManagerException(shipmentResponse.getStatus().getStatusMessage(), shipmentResponse.getStatus().getStatusCode());
        }


        shipmentResponse = masterBagInScan(masterBagId, lmsOrderEntries.getDcID());
        if(!shipmentResponse.getStatus().getStatusType().equals(StatusResponse.Type.SUCCESS)){
            throw new ManagerException(shipmentResponse.getStatus().getStatusMessage(), shipmentResponse.getStatus().getStatusCode());
        }

        shipmentResponse = masterBagInScanUpdateV1(masterBagId, lmsOrderEntries, lmsOrderEntries.getDcID(), lmsOrderEntries.getPremisesType());
        if(!shipmentResponse.getStatus().getStatusType().equals(StatusResponse.Type.SUCCESS)){
            throw new ManagerException(shipmentResponse.getStatus().getStatusMessage(), shipmentResponse.getStatus().getStatusCode());
        }

        return masterBagId;
    }

    /**
     * processOrderInLMSFromSHToTerminalState
     * @param lmsOrderEntries
     * @throws IOException
     * @throws JAXBException
     * @throws ManagerException
     * @throws InterruptedException
     */
    @SuppressWarnings("unchecked")
	public void processOrderInLMSFromSHToTerminalState(LMSOrderEntries lmsOrderEntries) throws IOException, JAXBException, ManagerException, InterruptedException {

        long deliveryStaffID = Long.parseLong(getDeliveryStaffID(lmsOrderEntries.getDcID().toString()));
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "myntra_lms");

        List<LMSOrderEntry> orderEntries = new ArrayList<>();

        for (LMSOrderEntry lmsOrderEntry: lmsOrderEntries.getOrderEntries()) {
            if(lmsOrderEntry.getFinalStatus().toString().equals(EnumSCM.UNRTO)){
                OrderResponse unRTOResponse1 = markOrderRto(lmsOrderEntry.getOrderID(), lmsHelper.getTrackingNumber(lmsOrderEntry.getOrderID()));
                ExceptionHandler.handleError(unRTOResponse1.getStatus());
            }else{
                orderEntries.add(lmsOrderEntry);
            }
        }

        if(orderEntries.size() == 0){
            log.info("No More Shipment to Process");
            return;
        }

        LMSOrderEntries updatedLMSOrderEntries = new LMSOrderEntries(orderEntries, lmsOrderEntries.getWareHouseID(), lmsOrderEntries.getShippingMethod(),
                lmsOrderEntries.getPremisesType(), lmsOrderEntries.getDcID());

        TripResponse tripResponse = createTrip(updatedLMSOrderEntries.getDcID(), deliveryStaffID);
        ExceptionHandler.handleError(tripResponse.getStatus());

        long tripId = tripResponse.getTrips().get(0).getId();
        for (LMSOrderEntry lmsOrderEntry: updatedLMSOrderEntries.getOrderEntries()) {
            TripOrderAssignmentResponse tripOrderAssignmentResponse = addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(lmsOrderEntry.getOrderID()));
            ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());
        }

        TripOrderAssignmentResponse tripOrderAssignmentResponse = startTrip("" + tripId, "10");
        ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());

        List<Map<String,Object>> tripData = new ArrayList<>();
        for (LMSOrderEntry lmsOrderEntry: updatedLMSOrderEntries.getOrderEntries()) {
        	
            if (lmsOrderEntry.getFinalStatus().toString().equals(EnumSCM.FDDL)) {
                tripOrderAssignmentResponse = updateOrderInTrip((long) lmsHelper.getTripOrderAssignemntIdForOrder.apply(lmsOrderEntry.getOrderID(), tripId), EnumSCM.FAILED, EnumSCM.UPDATE);
                ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());
                tripOrderAssignmentResponse = updateOrderInTrip((long) lmsHelper.getTripOrderAssignemntIdForOrder.apply(lmsOrderEntry.getOrderID(), tripId), EnumSCM.COMPLETED, EnumSCM.UPDATE);
                ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());
                Map<String, Object> mp = new HashMap<>();
                mp.put("trip_order_assignment_id", lmsHelper.getTripOrderAssignemntIdForOrder.apply(lmsOrderEntry.getOrderID(), tripId));
                mp.put("AttemptReasonCode", AttemptReasonCode.DELIVERED);
                tripData.add(mp);
            } else if (lmsOrderEntry.getFinalStatus().toString().equals(EnumSCM.FD) || lmsOrderEntry.getFinalStatus().toString().equals(EnumSCM.FDFDDL)) {
                tripOrderAssignmentResponse = updateOrderInTrip((long) lmsHelper.getTripOrderAssignemntIdForOrder.apply(lmsOrderEntry.getOrderID(), tripId), EnumSCM.FAILED, EnumSCM.UPDATE);
                ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());
                Map<String, Object> mp = new HashMap<>();
                mp.put("trip_order_assignment_id", lmsHelper.getTripOrderAssignemntIdForOrder.apply(lmsOrderEntry.getOrderID(), tripId));
                mp.put("AttemptReasonCode", AttemptReasonCode.NOT_REACHABLE_UNAVAILABLE);
                tripData.add(mp);
            } else if (lmsOrderEntry.getFinalStatus().toString().equals(EnumSCM.RTO)) {
                tripOrderAssignmentResponse = updateOrderInTrip((long) lmsHelper.getTripOrderAssignemntIdForOrder.apply(lmsOrderEntry.getOrderID(), tripId), EnumSCM.FAILED, EnumSCM.UPDATE);
                ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());
                Map<String, Object> mp = new HashMap<>();
                mp.put("trip_order_assignment_id", lmsHelper.getTripOrderAssignemntIdForOrder.apply(lmsOrderEntry.getOrderID(), tripId));
                mp.put("AttemptReasonCode", AttemptReasonCode.REFUSED_TO_ACCEPT);
                tripData.add(mp);
            } else if (lmsOrderEntry.getFinalStatus().toString().equals(EnumSCM.LOST)) {
                tripOrderAssignmentResponse = updateOrderInTrip((long) lmsHelper.getTripOrderAssignemntIdForOrder.apply(lmsOrderEntry.getOrderID(), tripId), EnumSCM.FAILED, EnumSCM.UPDATE);
                ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());
                Map<String, Object> mp = new HashMap<>();
                mp.put("trip_order_assignment_id", lmsHelper.getTripOrderAssignemntIdForOrder.apply(lmsOrderEntry.getOrderID(), tripId));
                mp.put("AttemptReasonCode", AttemptReasonCode.REFUSED_TO_ACCEPT);
                tripData.add(mp);
            } else if (lmsOrderEntry.getFinalStatus().toString().equals(EnumSCM.DL)) {
                tripOrderAssignmentResponse = updateOrderInTrip((long) lmsHelper.getTripOrderAssignemntIdForOrder.apply(lmsOrderEntry.getOrderID(), tripId), EnumSCM.DELIVERED, EnumSCM.UPDATE);
                ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());
                Map<String, Object> mp = new HashMap<>();
                mp.put("trip_order_assignment_id", lmsHelper.getTripOrderAssignemntIdForOrder.apply(lmsOrderEntry.getOrderID(), tripId));
                mp.put("AttemptReasonCode", AttemptReasonCode.DELIVERED);
                tripData.add(mp);
            } else if (lmsOrderEntry.getFinalStatus().toString().equals(EnumSCM.SMDL)) {
                selfMarkDL(lmsOrderEntry.getOrderID());
                tripOrderAssignmentResponse = updateOrderInTrip((long) lmsHelper.getTripOrderAssignemntIdForOrder.apply(lmsOrderEntry.getOrderID(), tripId), EnumSCM.DELIVERED, EnumSCM.UPDATE);
                ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());
                tripOrderAssignmentResponse = updateOrderInTrip((long) lmsHelper.getTripOrderAssignemntIdForOrder.apply(lmsOrderEntry.getOrderID(), tripId), EnumSCM.COMPLETED, EnumSCM.UPDATE);
                ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());
                Map<String, Object> mp = new HashMap<>();
                mp.put("trip_order_assignment_id", lmsHelper.getTripOrderAssignemntIdForOrder.apply(lmsOrderEntry.getOrderID(), tripId));
                mp.put("AttemptReasonCode", AttemptReasonCode.DELIVERED);
                tripData.add(mp);
            }
        }

        tripOrderAssignmentResponse = completeTrip(tripData);
        ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());
        for (LMSOrderEntry lmsOrderEntry: updatedLMSOrderEntries.getOrderEntries()) {
            switch (lmsOrderEntry.getFinalStatus().name()){
                case EnumSCM.RTO :
                    OrderResponse rtoResponse = markOrderRto(lmsOrderEntry.getOrderID(), lmsHelper.getTrackingNumber(lmsOrderEntry.getOrderID()));
                    ExceptionHandler.handleError(rtoResponse.getStatus());
                    break;
                case EnumSCM.LOST :
                    if(!markOrderLOSTINDC(lmsOrderEntry.getOrderID()).equals(EnumSCM.SUCCESS))
                        throw new ManagerException("Mark Lost Fail In DC", 2001);
                    break;
                case EnumSCM.FDFDDL :
                    //Re-Queue Order
                    tripOrderAssignmentResponse = requeueOrder("" + lmsOrderEntry.getOrderID());
                    ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());

                    markOrderFDAfterFD(lmsOrderEntry, updatedLMSOrderEntries.getDcID());

                    tripOrderAssignmentResponse = requeueOrder("" + lmsOrderEntry.getOrderID());
                    ExceptionHandler.handleError(tripOrderAssignmentResponse.getStatus());

                    markOrderDLAfterFDOrFDFDV1(lmsOrderEntry, updatedLMSOrderEntries.getDcID());
                    break;
                default:
                    log.info("Please pass the proper Status to process");
                    break;
            }
        }
    }

    /**
     * getOrderToShipStatus
     * @param status
     * @return
     */
    public String getOrderToShipStatus(String status){

        switch (status){
            case EnumSCM.FDFDDL :
                status = EnumSCM.DELIVERED;
                break;
            case EnumSCM.UNRTO :
                status = EnumSCM.RTO_CONFIRMED;
                break;
            case EnumSCM.RTO :
                status = EnumSCM.RTO_CONFIRMED;
                break;
            case EnumSCM.FDDL :
                status = EnumSCM.DELIVERED;
                break;
            case EnumSCM.FDTODL :
                status = EnumSCM.DELIVERED;
                break;
            case EnumSCM.FD :
                status = EnumSCM.FAILED_DELIVERY;
                break;
            case EnumSCM.DL :
                status = EnumSCM.DELIVERED;
                break;
            case EnumSCM.SMDL :
                status = EnumSCM.DELIVERED;
                break;
            default:
                break;
        }
        return status;
    }
    /**
     * hubServiceabilityUpdate
     * @param warehouseId
     * @param pincode
     * @param couriers
     * @return
     * @throws IOException
     */
    public void hubServiceabilityUpdate(String warehouseId, String pincode, String[] couriers, boolean flushRedis) throws IOException, InterruptedException, JAXBException, JSONException, XMLStreamException, SQLException, ManagerException, ClassNotFoundException {
        ServiceabilityRequest entry = new ServiceabilityRequest();
        ServiceabilityServiceImpl service = new ServiceabilityServiceImpl();
        entry.setWarehouseId(warehouseId);
        entry.setPincode(pincode);
        entry.setCouriers(couriers);
        entry.setFulshRedis(flushRedis);
        service.updateServiceability(entry);
    }

    /**
     * flush lms serviceability keys from redis
     */
    @SuppressWarnings("rawtypes")
	public LambdaInterfaces.Function lmsFlushKeysFromRedis = port -> {
        RedisDeleteRequest req = new RedisDeleteRequest();
        List<String> keys = new ArrayList<>();
        keys.add("serviceability_cache*");
        keys.add("hubRegionPreferenceCache*");
        keys.add("hubConfiguredCapacityCache*");
        keys.add("hubTatInfoCache*");
        String host = "delta7";
        if (envName.equalsIgnoreCase("fox8"))
            host = "pps17";
        else if (envName.equals("m7"))
            host = "m7erpredis.mpreprod.myntra.com";
        req.setHost(host);
        req.setPort((int)port);
        req.setKeys(keys);
        String payload = APIUtilities.getObjectToJSON(req);
        return APIUtilities.getJsontoObject(HttpExecutorService.executeHttpService(Constants.LMS_PATH.REDIS_DELETE_KEYS, null,
            SERVICE_TYPE.JUGAAD.toString(), HTTPMethods.GET, payload, Headers.getBasicHeaderJSON()).getResponseBody(), new RedisResponse());};

    /**
     * flushRedisForServieabilityKeys
     */
    @SuppressWarnings("unchecked")
	public LambdaInterfaces.SimpleCall flushRedisForServieabilityKeys = ()-> {
        List<String> keys = new ArrayList<>();
        keys.add("serviceability_cache*");
        keys.add("hubRegionPreferenceCache*");
        keys.add("hubConfiguredCapacityCache*");
        keys.add("hubTatInfoCache*");
        RedisUtil.getRedisTemplate("erpredis_6378").getConnectionFactory().getConnection().select(1);
        RedisUtil.getRedisTemplate("erpredis_6378").delete(keys);
    };

    public CourierServiceabilityInfoResponse checkServiceability(long warehouseId, boolean isCapacityCheck, boolean isExpress, boolean shipping, String payment, String pin, String service, String shippingMode, boolean isN, boolean isP, boolean isH, boolean isF, boolean isL, boolean isC, int mrp, int cod, boolean isJ) throws UnsupportedEncodingException, JAXBException {
        String payload = "<CourierServiceabilityRequest><data><order><capacityCheckRequired>{isCapacityCheck}</capacityCheckRequired><expressCapacityCheckRequired>{isExpressCapacityCheck}</expressCapacityCheckRequired><shippingCutoffCheckRequired>{isShippingCutOff}</shippingCutoffCheckRequired><items><item><itemTypes><itemType><dataType>BOOLEAN</dataType><name>isTryAndBuyEnabled</name><value>true</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isNormal</name><value>{0}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isPersonalized</name><value>{1}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isHazmat</name><value>{2}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isFragile</name><value>{3}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isJewellery</name><value>{4}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isLarge</name><value>{5}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isComforter</name><value>{6}</value></itemType><itemType><dataType>NUMBER</dataType><name>itemMrpValue</name><value>{7}</value></itemType><itemType><dataType>NUMBER</dataType><name>codValue</name><value>{8}</value></itemType></itemTypes><skuId>12266468</skuId><warehouses><warehouseId>{WAREHOUSE}</warehouseId></warehouses></item></items><paymentMode>{payment}</paymentMode><pincode>{pin}</pincode><serviceType>{service}</serviceType><shippingMethod>{shipping}</shippingMethod></order></data></CourierServiceabilityRequest>";
        payload = payload.replace("{isCapacityCheck}", isCapacityCheck+"");
        payload = payload.replace("{isExpressCapacityCheck}", isExpress+"");
        payload = payload.replace("{isShippingCutOff}", shipping+"");
        payload = payload.replace("{0}", isN+"");
        payload = payload.replace("{1}", isP+"");
        payload = payload.replace("{2}", isH+"");
        payload = payload.replace("{3}", isF+"");
        payload = payload.replace("{4}", isJ+"");
        payload = payload.replace("{5}", isL+"");
        payload = payload.replace("{6}", isC+"");
        payload = payload.replace("{7}", mrp+"");
        payload = payload.replace("{8}", cod+"");
        payload = payload.replace("{payment}", payment+"");
        payload = payload.replace("{pin}", pin+"");
        payload = payload.replace("{service}", service+"");
        payload = payload.replace("{shipping}", shippingMode+"");
        payload = payload.replace("{WAREHOUSE}", warehouseId+"");

        Svc svc = HttpExecutorService.executeHttpService(Constants.LMS_PATH.CHECKSERVE, null, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        return  (CourierServiceabilityInfoResponse) APIUtilities.convertXMLStringToObject(svc.getResponseBody(), new CourierServiceabilityInfoResponse());
    }

    public CourierServiceabilityInfoResponse checkServiceabilitySummary(long warehouseId, boolean isCapacityCheck, boolean isExpress, boolean shipping, String payment, String pin, String service, String shippingMode, boolean isN, boolean isP, boolean isH, boolean isF, boolean isL, boolean isC, int mrp, int cod, boolean isJ) throws UnsupportedEncodingException, JAXBException {
        String payload = "<CourierServiceabilityRequest><data><order><capacityCheckRequired>{isCapacityCheck}</capacityCheckRequired><expressCapacityCheckRequired>{isExpressCapacityCheck}</expressCapacityCheckRequired><shippingCutoffCheckRequired>{isShippingCutOff}</shippingCutoffCheckRequired><items><item><itemTypes><itemType><dataType>BOOLEAN</dataType><name>isTryAndBuyEnabled</name><value>true</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isNormal</name><value>{0}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isPersonalized</name><value>{1}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isHazmat</name><value>{2}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isFragile</name><value>{3}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isJewellery</name><value>{4}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isLarge</name><value>{5}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isComforter</name><value>{6}</value></itemType><itemType><dataType>NUMBER</dataType><name>itemMrpValue</name><value>{7}</value></itemType><itemType><dataType>NUMBER</dataType><name>codValue</name><value>{8}</value></itemType></itemTypes><skuId>6565</skuId><warehouses><warehouseId>{WAREHOUSE}</warehouseId></warehouses></item></items><paymentMode>{payment}</paymentMode><pincode>{pin}</pincode><serviceType>{service}</serviceType><shippingMethod>{shipping}</shippingMethod></order></data></CourierServiceabilityRequest>";
        payload = payload.replace("{isCapacityCheck}", isCapacityCheck+"");
        payload = payload.replace("{isExpressCapacityCheck}", isExpress+"");
        payload = payload.replace("{isShippingCutOff}", shipping+"");
        payload = payload.replace("{0}", isN+"");
        payload = payload.replace("{1}", isP+"");
        payload = payload.replace("{2}", isH+"");
        payload = payload.replace("{3}", isF+"");
        payload = payload.replace("{4}", isJ+"");
        payload = payload.replace("{5}", isL+"");
        payload = payload.replace("{6}", isC+"");
        payload = payload.replace("{7}", mrp+"");
        payload = payload.replace("{8}", cod+"");
        payload = payload.replace("{payment}", payment+"");
        payload = payload.replace("{pin}", pin+"");
        payload = payload.replace("{service}", service+"");
        payload = payload.replace("{shipping}", shippingMode+"");
        payload = payload.replace("{WAREHOUSE}", warehouseId+"");

        Svc result = HttpExecutorService.executeHttpService(Constants.CSS_PATH.CHECK_SERVICABILITY_SUMMARY, null, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        return  (CourierServiceabilityInfoResponse) APIUtilities.convertXMLStringToObject(result.getResponseBody(), new CourierServiceabilityInfoResponse());
    }

    public CourierServiceabilityInfoResponse checkServiceabilityWithAttributes(long warehouseId, boolean isCapacityCheck, boolean isExpress, boolean shipping, String payment, String pin, String service, String shippingMode, boolean isN, boolean isP, boolean isH, boolean isF, boolean isL, boolean isC, int mrp, int cod, boolean isJ) throws UnsupportedEncodingException, JAXBException {
        String payload = "<CourierServiceabilityRequest><data><order><capacityCheckRequired>{isCapacityCheck}</capacityCheckRequired><expressCapacityCheckRequired>{isExpressCapacityCheck}</expressCapacityCheckRequired><shippingCutoffCheckRequired>{isShippingCutOff}</shippingCutoffCheckRequired><items><item><itemTypes><itemType><dataType>BOOLEAN</dataType><name>isTryAndBuyEnabled</name><value>true</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isNormal</name><value>{0}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isPersonalized</name><value>{1}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isHazmat</name><value>{2}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isFragile</name><value>{3}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isJewellery</name><value>{4}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isLarge</name><value>{5}</value></itemType><itemType><dataType>BOOLEAN</dataType><name>isComforter</name><value>{6}</value></itemType><itemType><dataType>NUMBER</dataType><name>itemMrpValue</name><value>{7}</value></itemType><itemType><dataType>NUMBER</dataType><name>codValue</name><value>{8}</value></itemType></itemTypes><skuId>12266468</skuId><warehouses><warehouseId>{WAREHOUSE}</warehouseId></warehouses></item></items><paymentMode>{payment}</paymentMode><pincode>{pin}</pincode><serviceType>{service}</serviceType><shippingMethod>{shipping}</shippingMethod></order></data></CourierServiceabilityRequest>";
        payload = payload.replace("{isCapacityCheck}", isCapacityCheck+"");
        payload = payload.replace("{isExpressCapacityCheck}", isExpress+"");
        payload = payload.replace("{isShippingCutOff}", shipping+"");
        payload = payload.replace("{0}", isN+"");
        payload = payload.replace("{1}", isP+"");
        payload = payload.replace("{2}", isH+"");
        payload = payload.replace("{3}", isF+"");
        payload = payload.replace("{4}", isJ+"");
        payload = payload.replace("{5}", isL+"");
        payload = payload.replace("{6}", isC+"");
        payload = payload.replace("{7}", mrp+"");
        payload = payload.replace("{8}", cod+"");
        payload = payload.replace("{payment}", payment+"");
        payload = payload.replace("{pin}", pin+"");
        payload = payload.replace("{service}", service+"");
        payload = payload.replace("{shipping}", shippingMode+"");
        payload = payload.replace("{WAREHOUSE}", warehouseId+"");

        Svc result = HttpExecutorService.executeHttpService(Constants.CSS_PATH.CHECK_SERVICABILITY_WITH_ATTRIBUTES, null, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
        return  (CourierServiceabilityInfoResponse) APIUtilities.convertXMLStringToObject(result.getResponseBody(), new CourierServiceabilityInfoResponse());

    }


    public CourierServiceabilityInfoResponseV2 checkServiceability(String payload , long warehouseId, String pin, int mrp, int cod){
        payload = payload.replace("{7}", mrp+"");
        payload = payload.replace("{8}", cod+"");
        payload = payload.replace("{pin}", pin+"");
        payload = payload.replace("{WAREHOUSE}", warehouseId+"");

        CourierServiceabilityInfoResponseV2 response = new CourierServiceabilityInfoResponseV2();
        Svc result = null;
        try {
            result = HttpExecutorService.executeHttpService(Constants.LMS_PATH.CHECKSERVE, null, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderXML());
            response = (CourierServiceabilityInfoResponseV2) APIUtilities.convertXMLStringToObject(result.getResponseBody(), new CourierServiceabilityInfoResponseV2());
        } catch (UnsupportedEncodingException | JAXBException e) {
            e.printStackTrace();
        }
        return response;
    }

	public Svc checkShippingBarcodeApi(String courierCode, String myntraSourceId) {

		Svc lmsServiceResponse = null;
		String packetId = null;
		try {

			Map<String, Object> orderData = lmsHelper.getReleaseOrderIdList(courierCode, myntraSourceId);

			if (orderData != null && !orderData.isEmpty()) {
				packetId = orderData.get("order_id") + "";
			} else {
				String orderId = lmsHelper.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, courierCode, "36", EnumSCM.NORMAL, "CC", false, true);
				packetId = omsServiceHelper.getPacketId(orderId);
			}

			if (packetId != null && !packetId.isEmpty()) {

				Gson gson = new Gson();
				Map<String, PacketEntry> payloadMap = new HashMap<>();
				payloadMap.put("orderRelease", omsServiceHelper.getPacketEntry(packetId));
				HashMap<String, String> haederMap = Headers.getLmsHeaderJSON();
				haederMap.remove("Accept");

				// Calling LMS GET_SHIPPING_BARCODE api for orderId
				lmsServiceResponse = HttpExecutorService.executeHttpService(Constants.LMS_PATH.ORDER, new String[] { packetId, "shippingBarcode" }, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, gson.toJson(payloadMap), haederMap);
			} else {

				ExceptionHandler.fail("Failed to get valid orderId");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return lmsServiceResponse;
	}
	
	public Svc getBulkJobFetchApiResponse(String jobType) throws UnsupportedEncodingException {
		
		return HttpExecutorService.executeHttpService(Constants.LMS_PATH.BULKJOB_FETCH + jobType, null, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderJSON());
	}

	
	
	   
    public HubWareHouseConfigResponse getHubFromWarehouse(String warehouseId) throws JAXBException, JsonParseException, JsonMappingException, IOException {
		  
   	 	  HashMap<String, String> headerMap = Headers.getLmsHeaderXML();
   	 	  headerMap.put("Accept", "application/json");
		  Svc svc = HttpExecutorService.executeHttpService(Constants.LMS_PATH.HUB_WH_CONFIG+"?q=warehouseId.eq:"+warehouseId+"&shipmentType.eq:DL", null, SERVICE_TYPE.LMS_SVC.toString(),
		          HTTPMethods.GET, null, headerMap);
		  
		  return (HubWareHouseConfigResponse) APIUtilities.convertXMLStringToObject(svc.getResponseBody(), new HubWareHouseConfigResponse());
	 }
    
    public String getHubConfig(String whId, String warehouseType) {
   	 
   	 	String query = "select * from hub_warehouse_config where warehouse_id="+whId+" and shipment_type='"+warehouseType+"'";
		return (String)DBUtilities.exSelectQueryForSingleRecord(query, "myntra_lms").get("hub_code");	 
    }
    
    @SuppressWarnings("unused")
	public String getHubConfigList(String hubCode, String warehouseType) {
   	 
	 	String query = "select * from hub_warehouse_config where hub_code="+hubCode+" and shipment_type='"+warehouseType+"'";
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> hubConfig = DBUtilities.exSelectQuery(query, "myntra_lms");	 
		return null;
    }
  
    /**
     * Refresh LMS Apllication Properties
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     * @throws ManagerException 
     */
    public Svc refreshLMSApplicationPropertyCache() throws UnsupportedEncodingException, JAXBException, ManagerException {
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.REFRESHAPPLICATIONPROPERTIES, null, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderXML());
        ExceptionHandler.handleEquals(service.getResponseStatus(),200,"LMS Service is not giving 200 response. Actual:"+service.getResponseStatus());
        ApplicationPropertiesResponse cacheRefresh = (ApplicationPropertiesResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ApplicationPropertiesResponse());
        ExceptionHandler.handleEquals(cacheRefresh.getStatus().getStatusType(), StatusResponse.Type.SUCCESS,"There is some issue with LMS service");
        
        return service;
    }
    
    /**
     * Refresh TMS Apllication Properties
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     * @throws ManagerException 
     */
    public Svc refreshTMSApplicationPropertyCache() throws UnsupportedEncodingException, JAXBException, ManagerException {
        Svc service = HttpExecutorService.executeHttpService(Constants.TMS_PATH.REFRESHAPPLICATIONPROPERTIES, null, SERVICE_TYPE.TMS_SVC.toString(), HTTPMethods.GET, null, Headers.getLmsHeaderJSON());
        ExceptionHandler.handleEquals(service.getResponseStatus(),200,"TMS Service is not giving 200 response. Actual:"+service.getResponseStatus());
        ApplicationPropertiesResponse cacheRefresh = (ApplicationPropertiesResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ApplicationPropertiesResponse());
        ExceptionHandler.handleEquals(cacheRefresh.getStatus().getStatusType(), StatusResponse.Type.SUCCESS,"There is some issue with TMS service");
    return service;
    }
	public ShipmentResponse receiveExtraShipmentFromMasterbag(long shipmentId,String orderId)
			throws IOException, JAXBException, InterruptedException, ManagerException, JSONException, XMLStreamException {
		ShipmentEntry masterbag = ((ShipmentResponse) getMasterBag.apply(shipmentId)).getEntries().get(0);
		ShipmentEntry shipmentEntry = new ShipmentEntry();
		
		List<OrderShipmentAssociationEntry> orderShipmentAssociationEntries = new ArrayList<>();
		shipmentEntry.setId(shipmentId);
		if (masterbag != null && masterbag.getCourier() != null && masterbag.getCourier().equals("ML"))
			shipmentEntry.setStatus(ShipmentStatus.RECEIVED);
		else
			shipmentEntry.setStatus(ShipmentStatus.RECEIVED_AT_HANDOVER_CENTER);
		shipmentEntry.setLastScannedCity(masterbag.getLastScannedCity());
		shipmentEntry.setLastScannedPremisesId(masterbag.getLastScannedPremisesId());
		shipmentEntry.setLastScannedPremisesType(masterbag.getLastScannedPremisesType());
		shipmentEntry.setLastScannedOn(new Date());
		shipmentEntry.setArrivedOn(new Date());
		masterbag.getOrderShipmentAssociationEntries()
				.forEach(order->{
					OrderShipmentAssociationEntry osae = new OrderShipmentAssociationEntry();
					osae.setOrderId(order.getOrderId());
					osae.setShipmentId(shipmentId);
					if (masterbag != null && masterbag.getCourier() != null && masterbag.getCourier().equals("ML"))
						osae.setStatus(OrderShipmentAssociationStatus.RECEIVED);
					else
						osae.setStatus(OrderShipmentAssociationStatus.RECEIVED_AT_HANDOVER_CENTER);
					orderShipmentAssociationEntries.add(osae);
				});
		//Receive the extra order from the MB
		OrderShipmentAssociationEntry osae = new OrderShipmentAssociationEntry();
		osae.setOrderId(orderId);
		osae.setShipmentId(shipmentId);
		if (masterbag != null && masterbag.getCourier() != null && masterbag.getCourier().equals("ML"))
			osae.setStatus(OrderShipmentAssociationStatus.RECEIVED);
		else
			osae.setStatus(OrderShipmentAssociationStatus.RECEIVED_AT_HANDOVER_CENTER);
		orderShipmentAssociationEntries.add(osae);
		
		
		shipmentEntry.setOrderShipmentAssociationEntries(orderShipmentAssociationEntries);
		String payload = APIUtilities.convertXMLObjectToString(shipmentEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.RECEIVE_SHIPMENT_IN_DC,
				new String[]{Long.toString(shipmentId)}, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload,
				Headers.getLmsHeaderXML());
		ShipmentResponse shipmentResponse = (ShipmentResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new ShipmentResponse());
		return shipmentResponse;
	}
}
