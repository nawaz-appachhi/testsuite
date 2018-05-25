package com.myntra.apiTests.erpservices.oms;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.xml.sax.SAXException;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.Headers;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSHelpersEnums.ReadyToDispatchType;
import com.myntra.apiTests.erpservices.sellerapis.SellerApiHelper;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.catalog.client.domain.response.ProductDetailResponse;
import com.myntra.client.tools.response.ApplicationPropertiesResponse;
import com.myntra.client.wms.response.location.WarehouseEntry;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lms.client.response.OrderTrackingDetailEntry;
import com.myntra.lms.client.response.OrderTrackingEntry;
import com.myntra.lms.client.response.OrderTrackingResponse;
import com.myntra.lms.client.response.ReturnAddress;
import com.myntra.lms.client.response.TrackingNumberResponse;
import com.myntra.lms.client.status.DeliveryStatus;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.SlackMessenger;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.oms.client.entry.ActionEntry;
import com.myntra.oms.client.entry.AddressChangeRequestEntry;
import com.myntra.oms.client.entry.ConsolidationRequestEntry;
import com.myntra.oms.client.entry.ConsolidationResponseEntry;
import com.myntra.oms.client.entry.ExpressChargesRefundEntry;
import com.myntra.oms.client.entry.LineMovementEntry;
import com.myntra.oms.client.entry.OnHoldResolutionEntry;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.entry.OrderUpdateEntry;
import com.myntra.oms.client.entry.PacketEntry;
import com.myntra.oms.client.entry.PriceOverrideEntry;
import com.myntra.oms.client.entry.PromiseDatesEntry;
import com.myntra.oms.client.entry.ReleaseUpdateEntry;
import com.myntra.oms.client.entry.SellerOrderUpdateRequest;
import com.myntra.oms.client.entry.request.BulkLineMovementEntry;
import com.myntra.oms.client.entry.request.ReleaseSplitRequestEntry;
import com.myntra.oms.client.response.LineMovementResponse;
import com.myntra.oms.client.response.OmsCommonResponse;
import com.myntra.oms.client.response.OrderLineResponse;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.oms.client.response.OrderResponse;
import com.myntra.oms.client.response.PacketResponse;
import com.myntra.oms.common.enums.LineMovementAction;
import com.myntra.oms.common.enums.ShippingMethod;
import com.myntra.oms.common.enums.SplittingStrategyCode;
import com.myntra.release.doc.client.entry.ShippingLabelEntry;
import com.myntra.release.doc.client.entry.StickerInvoiceEntry;
import com.myntra.release.doc.client.response.ReleaseDocumentResponse;
import com.myntra.sellers.entry.KYCDocumentEntry;
import com.myntra.sellers.entry.SellerEntry;
import com.myntra.sellers.response.SellerResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;

/*import com.myntra.release.doc.client.entry.ShippingLabelEntry;
import com.myntra.release.doc.client.entry.StickerInvoiceEntry;
import com.myntra.release.doc.client.response.ReleaseDocumentResponse;*/


/**
 * @abhijit.pati
 */
@SuppressWarnings({ "rawtypes", "deprecation" })
public class OMSServiceHelper {
    private static Logger log = Logger.getLogger(OMSServiceHelper.class);
    static Initialize init = new Initialize("/Data/configuration");
    private PromiseDatesEntry promiseDatesEntry;
    private Date today;
    private Date futureDate;
    private OrderEntry orderEntry;
    private List<OrderReleaseEntry> orderReleaseEntries;
    private String orderReleaseId;
	private SoftAssert sft;
	private int delayToCheck = 10;
	private IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	private WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();

    /**
     * Get Order Release Entry for an Order ID
     * @param orderId
     * @return List
     */
    public List getOrderReleaseDBEntry(String orderId){
        List resultSet = null;
        try {
            resultSet = DBUtilities.exSelectQuery("select * from order_release where order_id_fk =" + orderId + ";", "myntra_oms");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Release Entry : "+ e.getLocalizedMessage());
            Assert.fail("Unable to get OrderReleaseDBEntry");
        }
        return resultSet;
    }

    /**
     * Get Order Release Entry for an Order ID
     * @param storeOrderId
     * @return List
     */
    public List getOrderReleaseDBEntryByStoreOrderID(String storeOrderId){
        List resultSet = null;
        try {
            resultSet = DBUtilities.exSelectQuery("select * from order_release where store_order_id =" + storeOrderId + ";", "myntra_oms");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Release Entry : "+ e.getLocalizedMessage());
            Assert.fail("Unable to get OrderReleaseDBEntry");
        }
        return resultSet;
    }

    /**
     * Get Order line additional info Entry for an Order ID
     * @param orderID
     * @return List
     */
    public List getOrderLineAdditionalInfoDBEntry(String orderID){
        List resultSet = null;
        try {
            resultSet = DBUtilities.exSelectQuery("select * from order_line_additional_info where id =" + orderID + ";", "myntra_oms");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Release Entry : "+ e.getLocalizedMessage());
        }
        return resultSet;
    }
    
    public String getPacketIdFromReleasId(String releaseId){
        String packetId = "";
        try {
            
        		Map<String, Object> resultMap = DBUtilities.exSelectQueryForSingleRecord("select packet_id_fk from order_line where order_release_id_fk =" + releaseId + "", "myntra_oms");
        		
        		if(resultMap != null && !resultMap.isEmpty() && resultMap.get("packet_id_fk") != null) {  
        			
        			System.out.println("resultMap - "+resultMap);
        			packetId = (long)resultMap.get("packet_id_fk")+"";
        		}
            
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Release Entry : "+ e.getLocalizedMessage());
        }
        return packetId;
    }

    /**
     * Get Order line Entry for an Order ID
     * @param orderID
     * @return List
     */
    public List getOrderLineDBEntry(String orderID){
        List resultSet = null;
        try {
            resultSet = DBUtilities.exSelectQuery("select * from order_line where order_id_fk =" + orderID + ";", "myntra_oms");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Release Entry : "+ e.getLocalizedMessage());
        }
        return resultSet;
    }

    /**
     * Get Order line Entry for an Order ID
     * @param storeOrderId
     * @return List
     */
    public List getOrderLineDBEntryByStoreID(String storeOrderId){
        List resultSet = null;
        try {
            resultSet = DBUtilities.exSelectQuery("select * from order_line where store_order_id=" + storeOrderId + ";", "myntra_oms");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Release Entry : "+ e.getLocalizedMessage());
        }
        return resultSet;
    }

    /**
     * Get Order Release Entry for a Release ID
     * @param releaseID
     * @return {@link List}
     */
    public List getOrderReleaseDBEntryForRelease(String releaseID){
        List resultSet = null;
        try {
            resultSet = DBUtilities.exSelectQuery("select * from order_release where id =" + releaseID + ";", "myntra_oms");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Release Entry : "+ e.getLocalizedMessage());
            Assert.fail("Unable to get OrderReleaseDBEntryForRelease");
        }
        return resultSet;
    }


    /**
     * Create Order directly in OMS
     * @param payload
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public OrderEntry createOrderInOMS(String payload) throws UnsupportedEncodingException, JAXBException{
        Svc svc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.CREATE_ORDER, null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.POST, payload, getOMSHeader());
        OrderResponse orderResponse = (OrderResponse) APIUtilities.convertXMLStringToObject(svc.getResponseBody(), new OrderResponse());
        Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
        return orderResponse.getData().get(0);
    }

    public OrderEntry evaluateOrderOnholdRules(String orderID) throws UnsupportedEncodingException, JAXBException{
        Svc svc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.EVALUATE_ORDER_ONHOLD, new String[] {""+orderID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
        OrderResponse orderResponse = (OrderResponse) APIUtilities.convertXMLStringToObject(svc.getResponseBody(), new OrderResponse());
        Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
        return orderResponse.getData().get(0);
    }

    /**
     * Resolve On-Hold Order
     * @param orderID
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public OrderEntry resolveOnholdOrders(String orderID) throws UnsupportedEncodingException, JAXBException, ManagerException {
    	OnHoldResolutionEntry onHoldResolutionEntry = new OnHoldResolutionEntry();
        onHoldResolutionEntry.setDescription("Resolved the Fraud User On-Hold Order");
        String payload = APIUtilities.convertXMLObjectToString(onHoldResolutionEntry);

        Svc svc = HttpExecutorService.executeHttpService(Constants.ORCH_PATH.RESOLVE_ONHOLD_ORDER, new String[] {orderID}, SERVICE_TYPE.ORCH_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
        OrderResponse orderResponse = (OrderResponse) APIUtilities.convertXMLStringToObject(svc.getResponseBody(), new OrderResponse());
        ExceptionHandler.handleEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
        return orderResponse.getData().get(0);
    }

    /**
     * Resolve On-Hold Order
     * @param orderReleaseID
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public void resolveOnHoldOrderRelease(String orderReleaseID) throws UnsupportedEncodingException, JAXBException, ManagerException {
        OnHoldResolutionEntry onHoldResolutionEntry = new OnHoldResolutionEntry();
        onHoldResolutionEntry.setDescription("Resolved the Fraud User On-Hold Order");
        String payload = APIUtilities.convertXMLObjectToString(onHoldResolutionEntry);

        Svc svc = HttpExecutorService.executeHttpService(Constants.ORCH_PATH.RESOLVE_ONHOLD_ORDER_RELEASE, new String[] {""+orderReleaseID}, SERVICE_TYPE.ORCH_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
        ExceptionHandler.handleEquals(svc.getResponseStatus(), 200);
    }

    /**
     * Return the OrderEntry for an OrderID
     * @param orderID
     * @return {@link OrderEntry}
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public OrderEntry getOrderEntry(String orderID) throws JAXBException, UnsupportedEncodingException{
       log.info("Order ID"+orderID);
        Svc orderEntrySvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.GET_ORDER, new String[] {""+orderID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
        String orderEntryString = orderEntrySvc.getResponseBody();
       log.info("Order Entry :- "+ orderEntryString);
        OrderResponse orderEntry = (OrderResponse) APIUtilities.convertXMLStringToObject(orderEntryString, new OrderResponse());
        if(!(orderEntry.getData().size()==0)) {
            return orderEntry.getData().get(0);
        }else{
            Assert.fail("No Orders Found for the Given Order ID "+ orderID);
            return null;
        }
    }

    /**
     * Return the OrderEntry for an OrderID
     * @param storeId
     * @return {@link OrderEntry}
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public OrderEntry getOrderEntryByStoreOrderID(String storeId) throws JAXBException, UnsupportedEncodingException{
       log.info("Store Order ID : "+storeId);
        Svc orderEntrySvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.GET_ORDER, new String[] {"getByStoreOrderId", storeId}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
        String orderEntryString = orderEntrySvc.getResponseBody();
        OrderResponse orderEntry = (OrderResponse) APIUtilities.convertXMLStringToObject(orderEntryString, new OrderResponse());
        if(!(orderEntry.getData().size()==0)) {
            return orderEntry.getData().get(0);
        }else{
            return null;
        }
    }

    public OrderResponse getArmorOrder(String queryString) throws UnsupportedEncodingException, JAXBException {
        log.info("Get Order Query String :- "+ queryString);
        Svc orderEntrySvc = HttpExecutorService.executeHttpService(Constants.ARMOR_PATH.GET_ORDER, new String[] {queryString}, SERVICE_TYPE.ARMOR_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
        OrderResponse orderResponse = (OrderResponse) APIUtilities.convertXMLStringToObject(orderEntrySvc.getResponseBody(), new OrderResponse());
        return orderResponse;
    }

    public void FindArmorOrderById(String queryString) throws UnsupportedEncodingException {
        log.info("Get Order Query String :- "+ queryString);
        Svc orderEntrySvc = HttpExecutorService.executeHttpService(Constants.ARMOR_PATH.FIND_ORDER, new String[] {}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
    }


    /**
     * Return the OrderReleaseEntry for an Order Release ID
     * @param orderReleaseID
     * @return {@link OrderReleaseEntry}
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public OrderReleaseEntry getOrderReleaseEntry(String orderReleaseID) throws JAXBException, UnsupportedEncodingException{
        log.info("Order Id "+orderReleaseID);
        Svc orderReleaseEntrySvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.GET_ORDER_RELEASE, new String[] {orderReleaseID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
        String orderReleaseEntryString = orderReleaseEntrySvc.getResponseBody();
        log.info("Order Entry :- "+ orderReleaseEntryString);
        OrderReleaseResponse orderReleaseEntry = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(orderReleaseEntryString, new OrderReleaseResponse());
        return orderReleaseEntry.getData().get(0);
    }
    
    public PacketEntry getPacketEntry(String packetId) throws JAXBException, UnsupportedEncodingException{
    	
   		log.info("PacketId Id "+packetId);
		Svc orderReleaseEntrySvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.GET_PACKET, new String[] {packetId}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
		String orderReleaseEntryString = orderReleaseEntrySvc.getResponseBody();
        log.info("PacketId Entry :- "+ orderReleaseEntryString);
        PacketResponse packetResponse = (PacketResponse) APIUtilities.convertXMLStringToObject(orderReleaseEntryString, new PacketResponse());
        log.info("PacketId :- "+ packetResponse);  		
	    return packetResponse.getData().get(0);
    }


    /**
     * Get Order Line Entries for A Release ID
     * @param orderReleaseID
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public List<OrderLineEntry> getOrderLineEntries(String orderReleaseID) throws JAXBException, UnsupportedEncodingException{
       log.info("Order Id "+orderReleaseID);
        Svc orderReleaseEntrySvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.GET_ORDER_RELEASE, new String[] {orderReleaseID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
        String orderReleaseEntryString = orderReleaseEntrySvc.getResponseBody();
       log.info("Order Entry :- "+ orderReleaseEntryString);
        OrderReleaseResponse orderReleaseEntries = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(orderReleaseEntryString, new OrderReleaseResponse());
        OrderReleaseEntry orderReleaseEntry = orderReleaseEntries.getData().get(0);
        List<OrderLineEntry> orderLineEntries = orderReleaseEntry.getOrderLines();
        return orderLineEntries;
    }

    /**
     * Get Order Line Entry for a Given Line ID
     * @param lineID
     * @return {@link OrderLineEntry}
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public OrderLineEntry getOrderLineEntry(String lineID) throws JAXBException, UnsupportedEncodingException {
       log.info("Line ID"+lineID);
        Svc orderLineEntrySvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.GET_ORDER_LINE, new String[] {lineID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
        String ordeLinerEntryString = orderLineEntrySvc.getResponseBody();
       log.info("Line Entry :- "+ ordeLinerEntryString);
        OrderLineResponse orderLineResponse = (OrderLineResponse) APIUtilities.convertXMLStringToObject(ordeLinerEntryString, new OrderLineResponse());
        return orderLineResponse.getData().get(0);
    }

    public String getReleaseId(String orderId){
        Map<String,Object> orderReleaseDB = DBUtilities.exSelectQueryForSingleRecord("select id from order_release where order_id_fk = "+orderId,"oms");
        return orderReleaseDB.get("id").toString();
    }
    
    public String getPacketId(String orderId) throws InterruptedException{
    	
    		Map<String,Object> orderReleaseDB = new HashMap<>();
    	    int count = 0;
    	    while(count < 5) {
        	         
    	    	      orderReleaseDB = DBUtilities.exSelectQueryForSingleRecord("select packet_id_fk from order_line where order_id_fk = "+orderId+" limit 1","oms");
	       
	          if(orderReleaseDB !=null && !orderReleaseDB.isEmpty()) {	
	        	  
	        	        if(orderReleaseDB.get("packet_id_fk") != null ) { 
	        	  		 
	        	        	   return orderReleaseDB.get("packet_id_fk").toString();
	        	        }
	          } else {	      
	        	  
	        	        Thread.sleep(2000); 
	          }
	      count++;   
    	    } 
    	    
        return null;
    }

    public String getReleaseIdFromStoreOrderId(String storeOrderId){
        Map<String,Object> orderReleaseDB = DBUtilities.exSelectQueryForSingleRecord("select id from order_release where store_order_id = '"+storeOrderId +"'","oms");
        return orderReleaseDB.get("id").toString();
    }

    public String getOrderId(String storeOrderId){
        Map<String,Object> orderReleaseDB = DBUtilities.exSelectQueryForSingleRecord("select id from orders where store_order_id = "+storeOrderId,"oms");
        return orderReleaseDB.get("id").toString();
    }

    public String getOrderIdFromReleaseId(String orderReleaseID){
        Map<String,Object> orderReleaseDB = DBUtilities.exSelectQueryForSingleRecord("select order_id_fk from order_release where id = "+orderReleaseID,"oms");
        return orderReleaseDB.get("order_id_fk").toString();
    }

    public String getOneColumnValue(String ColumnName, String filterColumnName,String filterColumnValue,String tableName,String dbName){
        Map<String,Object> orderReleaseDB = DBUtilities.exSelectQueryForSingleRecord("select "+ ColumnName+" from " + tableName +
                " where "+ filterColumnName +" = '"+ filterColumnValue +"'",dbName);
        return orderReleaseDB.get(ColumnName).toString();
    }

    /**
     * Get OMS Header
     * @return {@link HashMap}
     */
    public static HashMap<String, String> getOMSHeader() {
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
        createOrderHeaders.put("Content-Type", "Application/xml");
        return createOrderHeaders;
    }

    /**
     * Get OMS Header
     * @return {@link HashMap}
     */
    public static HashMap<String, String> getOMSHeaderJson() {
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
        createOrderHeaders.put("Content-Type", "application/json");
        createOrderHeaders.put("Accept", "application/json");
        return createOrderHeaders;
    }

    /**
     * Get OMS Header
     * @return {@link HashMap}
     */
    public static HashMap<String, String> getOMSHeaderXML() {
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
        createOrderHeaders.put("Content-Type", "Application/xml");
        createOrderHeaders.put("Accept", "application/xml");
        return createOrderHeaders;
    }

    /**
     * Get Order Release Entry for an Order ID
     * @param orderReleaseID
     * @return List
     */
    public List getOrderLineDBEntryforRelease(String orderReleaseID){
        List resultSet = null;
        try {
            resultSet = DBUtilities.exSelectQuery("select * from order_line where order_release_id_fk =" + orderReleaseID + ";", "myntra_oms");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Order Line Entry : "+ e.getLocalizedMessage());
            Assert.fail("unable to get OrderLineDBEntryforRelease");
        }
        return resultSet;
    }

    /**
     * Get Order Line Entry for an Line ID
     * @param lineID
     * @return List
     */
    public HashMap getOrderLineDBEntryForLine(String lineID){
        HashMap<String, Object> resultSet = null;
        try {
            resultSet = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from order_line where id =" + lineID + ";", "myntra_oms");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Order Line Entry : "+ e.getLocalizedMessage());
        }
        return resultSet;
    }

    /**
     *
     * OMS Mark Release Pack
     * @param releaseID
     * @return {@link OrderReleaseResponse}
     */
    public OrderReleaseResponse rtsScan(String releaseID) throws JAXBException, UnsupportedEncodingException {
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.RTSSCAN, new String[] {releaseID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, "", getOMSHeader());
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
        return orderReleaseResponse;
    }

    /**
     * Order Cancellation
     *
     * @param orderID
     * @param cancellationReasonId
     * @param userLogin
     * @param comment
     * @return {@link OrderResponse}
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public OrderResponse cancelOrder(String orderID, String cancellationReasonId, String userLogin, String comment) throws JAXBException, UnsupportedEncodingException {

        OrderUpdateEntry orderUpdateEntry = new OrderUpdateEntry();
        orderUpdateEntry.setOrderId(Long.parseLong(orderID));
        orderUpdateEntry.setCancellationReasonId(Long.parseLong(cancellationReasonId));
        orderUpdateEntry.setDoRefund(true);
        orderUpdateEntry.setDoNotify(true);
        orderUpdateEntry.setUserId(userLogin);
        orderUpdateEntry.setComment(comment);

        String payload = APIUtilities.convertXMLObjectToString(orderUpdateEntry);

        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.CANCEL_ORDER, null,SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
        OrderResponse orderResponse = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderResponse());
        return orderResponse;
    }

    /**
     * Order Cancellation
     * @param orderID
     * @param cancellationReasonId
     * @param userLogin
     * @param comment
     * @param doRefund
     * @param doNotify
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public OrderResponse cancelOrder(String orderID, long cancellationReasonId, String userLogin, String comment, boolean doRefund, boolean doNotify) throws JAXBException, UnsupportedEncodingException {

        OrderUpdateEntry orderUpdateEntry = new OrderUpdateEntry();
        orderUpdateEntry.setOrderId(Long.parseLong(orderID));
        orderUpdateEntry.setCancellationReasonId(cancellationReasonId);
        orderUpdateEntry.setDoRefund(doRefund);
        orderUpdateEntry.setDoNotify(doNotify);
        orderUpdateEntry.setUserId(userLogin);
        orderUpdateEntry.setComment(comment);

        String payload = APIUtilities.convertXMLObjectToString(orderUpdateEntry);

        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.CANCEL_ORDER, null,SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.POST, payload, getOMSHeader());
        OrderResponse orderResponse = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderResponse());
        return orderResponse;
    }

    /**
     * Order Release Cancellation
     *
     * @param orderReleaseID
     * @param cancellationReasonId
     * @param userLogin
     * @param comment
     * @return {@link OrderReleaseResponse}
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public OrderReleaseResponse cancelOrderRelease(String orderReleaseID, String cancellationReasonId, String userLogin, String comment) throws JAXBException, UnsupportedEncodingException {

        ReleaseUpdateEntry releaseUpdateEntry = new ReleaseUpdateEntry();
        releaseUpdateEntry.setReleaseId(Long.parseLong(orderReleaseID));
        releaseUpdateEntry.setCancellationReasonId(Long.parseLong(cancellationReasonId));
        releaseUpdateEntry.setUserId(userLogin);
        releaseUpdateEntry.setComment(comment);
        releaseUpdateEntry.setDoRefund(true);
        releaseUpdateEntry.setDoNotify(true);
        releaseUpdateEntry.setCancelFreeItems(true);

        String payload = APIUtilities.convertXMLObjectToString(releaseUpdateEntry);

        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.CANCEL_ORDER_RELEASE, null,SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
        return orderReleaseResponse;
    }


    /**
     * Cancel Order Release
     * @param orderReleaseID
     * @param cancellationReasonId
     * @param userLogin
     * @param comment
     * @param doRefund
     * @param doNotify
     * @param doCancellFreeItem
     * @return {@link OrderReleaseResponse}
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public OrderReleaseResponse cancelOrderRelease(String orderReleaseID, String cancellationReasonId, String userLogin, String comment, boolean doRefund, boolean doNotify, boolean doCancellFreeItem) throws JAXBException, UnsupportedEncodingException {

        OrderReleaseEntry orderReleaseEntry = getOrderReleaseEntry(orderReleaseID);
        ReleaseUpdateEntry releaseUpdateEntry = new ReleaseUpdateEntry();
        releaseUpdateEntry.setReleaseId(Long.parseLong(orderReleaseID));
        releaseUpdateEntry.setCancellationReasonId(Long.parseLong(cancellationReasonId));
        releaseUpdateEntry.setUserId(userLogin);
        releaseUpdateEntry.setComment(comment);
        releaseUpdateEntry.setDoRefund(doRefund);
        releaseUpdateEntry.setDoNotify(doNotify);
        releaseUpdateEntry.setCancelFreeItems(doCancellFreeItem);
        releaseUpdateEntry.setSellerId(orderReleaseEntry.getSellerId());

        String payload = APIUtilities.convertXMLObjectToString(releaseUpdateEntry);

        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.CANCEL_ORDER_RELEASE, null,SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.POST, payload, getOMSHeader());
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
        return orderReleaseResponse;
    }

    /**
     * Cancel Item
     * @param releaseId
     * @param userLogin
     * @param lineIdAndQuantity
     * @param cancellationReasonID
     * @return {@link OrderReleaseResponse}
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public OrderReleaseResponse cancelLine(String releaseId, String userLogin, String[] lineIdAndQuantity, long cancellationReasonID) throws JAXBException, UnsupportedEncodingException {

        OrderReleaseEntry orderReleaseEntry = getOrderReleaseEntry(releaseId);
        ReleaseUpdateEntry lineCancellationEntry = new ReleaseUpdateEntry();

        lineCancellationEntry.setReleaseId(Long.parseLong(releaseId));
        lineCancellationEntry.setUserId(userLogin);
        lineCancellationEntry.setCancellationReasonId(cancellationReasonID);
        lineCancellationEntry.setSellerId(orderReleaseEntry.getSellerId());
        lineCancellationEntry.setDoRefund(true);
        lineCancellationEntry.setDoNotify(true);
        lineCancellationEntry.setCancelFreeItems(true);

        lineCancellationEntry.setLinesToBeCancelled(convertToOrderLineEntries(lineIdAndQuantity));

        String payload = APIUtilities.convertXMLObjectToString(lineCancellationEntry);

        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.CANCEL_LINES, null,SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
        return orderReleaseResponse;
    }

    /**
     * Cancel Item
     * @param releaseId
     * @param userLogin
     * @param lineIdAndQuantity
     * @param cancellationReasonID
     * @return {@link OrderReleaseResponse}
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public OrderReleaseResponse cancelLine(String releaseId, String userLogin, String[] lineIdAndQuantity, long cancellationReasonID, boolean doRefund, boolean doNotify, boolean doCancellFreeItem) throws JAXBException, UnsupportedEncodingException {

        OrderReleaseEntry orderReleaseEntry = getOrderReleaseEntry(releaseId);
        ReleaseUpdateEntry lineCancellationEntry = new ReleaseUpdateEntry();

        lineCancellationEntry.setReleaseId(Long.parseLong(releaseId));
        lineCancellationEntry.setUserId(userLogin);
        lineCancellationEntry.setCancellationReasonId(cancellationReasonID);
        lineCancellationEntry.setDoRefund(doRefund);
        lineCancellationEntry.setDoNotify(doNotify);
        lineCancellationEntry.setCancelFreeItems(doCancellFreeItem);
        lineCancellationEntry.setSellerId(orderReleaseEntry.getSellerId());

        lineCancellationEntry.setLinesToBeCancelled(convertToOrderLineEntries(lineIdAndQuantity));

        String payload = APIUtilities.convertXMLObjectToString(lineCancellationEntry);

        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.CANCEL_LINES, null,SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
        return orderReleaseResponse;
    }

    /**
     * Convert Arrary Of LineID and Quantity to List<OrderLineEntry>
     * @param lineIdAndQuantity
     * @return {@link List<OrderLineEntry>}
     */
    private List<OrderLineEntry> convertToOrderLineEntries(String[] lineIdAndQuantity){
        List<OrderLineEntry> orderLineEntries = new ArrayList<>();
        for(String lineAndQuantity : lineIdAndQuantity) {
            String[] splitLines = lineAndQuantity.split(":");
            OrderLineEntry orderLineEntry = new OrderLineEntry();
            orderLineEntry.setId(Long.parseLong(splitLines[0]));
            orderLineEntry.setQuantity(Integer.parseInt(splitLines[1]));
            orderLineEntries.add(orderLineEntry);
        }
        return orderLineEntries;
    }

    /**
     * Express Refund(Refund Shipping/Express )
     * @param orderID
     * @param userLogin
     * @return {@link RequestGenerator}
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public OrderReleaseResponse expressRefund(String orderID, String userLogin) throws JAXBException, UnsupportedEncodingException {
        ExpressChargesRefundEntry expressChargesRefundEntry = new ExpressChargesRefundEntry();
        expressChargesRefundEntry.setReleaseId(Long.parseLong(orderID));
        expressChargesRefundEntry.setUserLogin(userLogin);
        expressChargesRefundEntry.setComment("Express Refund For Release ID : "+ orderID);

        String payload = APIUtilities.convertXMLObjectToString(expressChargesRefundEntry);

        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.EXPRESS_REFUND, null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.POST, payload, getOMSHeader());
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
        return orderReleaseResponse;
    }
    

    /**
     * @param packetId
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public PacketResponse expressRefundForPacket(String packetId) throws JAXBException, UnsupportedEncodingException {

        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.EXPRESS_REFUND_PACKET, new String[]{packetId}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, null, getOMSHeader());
        PacketResponse packetResponse = (PacketResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new PacketResponse());
        return packetResponse;
    }


    /**
     * Oms PriceOveride Function
     * @param lineId
     * @param newUnitPrice
     * @param userLogin
     * @param comment
     * @return {@link RequestGenerator}
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public OrderLineResponse priceOverride(String lineId, String newUnitPrice, String userLogin, String comment) throws JAXBException, UnsupportedEncodingException {
        PriceOverrideEntry priceOverrideEntry = new PriceOverrideEntry();
        priceOverrideEntry.setLineId(Long.parseLong(lineId));
        priceOverrideEntry.setNewUnitPrice(Double.parseDouble(newUnitPrice));
        priceOverrideEntry.setUserLogin(userLogin);
        priceOverrideEntry.setComment(comment);

        String payload = APIUtilities.convertXMLObjectToString(priceOverrideEntry);

        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.PRICE_OVER_RIDE, null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.POST, payload, getOMSHeader());
        OrderLineResponse orderLineResponse = (OrderLineResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderLineResponse());
        return orderLineResponse;
    }

    /**
     * Manual Reassign WareHouse
     * @param releaseID
     * @param warehouseID
     * @param lineIDAndQuantity
     * @return {@link LineMovementResponse}
     */
    public LineMovementResponse reAssignWareHouse(String releaseID, int warehouseID, String[] lineIDAndQuantity, String courier) throws UnsupportedEncodingException, JAXBException {
        LineMovementEntry lineMovementEntry = new LineMovementEntry();
        lineMovementEntry.setReleaseId(Long.parseLong(releaseID));
        lineMovementEntry.setWarehouseId(warehouseID);
        lineMovementEntry.setCourierCode(courier);
        lineMovementEntry.setUserLogin("erpadmin");

        lineMovementEntry.setLinesToBeMoved(convertToOrderLineEntries(lineIDAndQuantity));
        String payload = APIUtilities.convertXMLObjectToString(lineMovementEntry);

        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.REASSIGNWAREHOUSE, null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.POST, payload, getOMSHeader());
        LineMovementResponse lineMovementResponse = (LineMovementResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new LineMovementResponse());
        return lineMovementResponse;
    }

    /**
     * Manual Split
     * @param releaseID
     * @param warehouseID
     * @Param lineAndQuantity
     * @return {@link RequestGenerator}
     */
    public OrderReleaseResponse splitOrder(String releaseID, int warehouseID, String[] lineIDAndQuantity, String courier, LineMovementAction lineMovementAction) throws JAXBException, UnsupportedEncodingException {
        LineMovementEntry lineMovementEntry = new LineMovementEntry();
        promiseDatesEntry = new PromiseDatesEntry();
        today = new Date();
        futureDate = new Date(today.getTime()+(1000 * 60 * 60 * 48));

        lineMovementEntry.setReleaseId(Long.parseLong(releaseID));
        lineMovementEntry.setAction(lineMovementAction);
        lineMovementEntry.setWarehouseId(warehouseID);
        lineMovementEntry.setUserLogin("erpadmin");
        lineMovementEntry.setCourierCode(courier);
        promiseDatesEntry.setCustomerPromiseDate(futureDate);
        promiseDatesEntry.setExpectedCutoffTime(futureDate);
        promiseDatesEntry.setExpectedPackingTime(futureDate);
        promiseDatesEntry.setExpectedPickingTime(futureDate);
        promiseDatesEntry.setExpectedQCTime(futureDate);
        lineMovementEntry.setPromiseDatesEntry(promiseDatesEntry);

        lineMovementEntry.setLinesToBeMoved(convertToOrderLineEntries(lineIDAndQuantity));
        String payload = APIUtilities.convertXMLObjectToString(lineMovementEntry);

        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.SPLITORDER, null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.POST, payload, getOMSHeader());
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
        return orderReleaseResponse;
    }

    /**
     * Print invoice for a releaseID
     * @param releaseID
     * @return {@link String}
     */
    public String printInvoice(long releaseID) throws UnsupportedEncodingException {
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.INVOICE, new String[] {""+releaseID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
        return service.getResponseBody();
    }

    /**
     * Delete PPS DB Records For a PPS ID
     *
     * @param ppsID
     * @throws SQLException
     */
    public void deletePPSRecords(String ppsID) throws SQLException {
        String deletePaymentPlan = "DELETE FROM `payment_plan` where id in (" + ppsID + ");";
        String deletePaymentPlanItem = "DELETE FROM `payment_plan_item` where `pps_Id` in (" + ppsID + ");";
        String deletePaymentPlanItemInstrument = "DELETE FROM `payment_plan_item_instrument` where ppsItemId in (select id from `payment_plan_item` where `pps_Id` in (" + ppsID + "));";
        String deletePaymentInstrumentDetails = "DELETE FROM `payment_plan_instrument_details` where `pps_Id` in ("
                + ppsID + ");";
        DBUtilities.exUpdateQuery(deletePaymentInstrumentDetails, "pps");
        DBUtilities.exUpdateQuery(deletePaymentPlanItemInstrument, "pps");
        DBUtilities.exUpdateQuery(deletePaymentPlanItem, "pps");
        DBUtilities.exUpdateQuery(deletePaymentPlan, "pps");
    }


    /**
     * Delete PPS DB Records For a PPS ID
     *
     * @param orderID
     * @throws SQLException
     */
    public void deletePPSRecordsByOrderID(String orderID) throws SQLException {
        String deletePaymentPlan = "DELETE FROM `payment_plan` where orderid ="+orderID+";";
        String deletePaymentPlanItem = "DELETE FROM `payment_plan_item` where `pps_Id` in (select id from `payment_plan` where orderid="+orderID+");";
        String deletePaymentPlanItemInstrument = "DELETE FROM `payment_plan_item_instrument` where ppsItemId in (select id from `payment_plan_item` where `pps_Id` in (select id from `payment_plan` where orderid="+orderID+"));";
        String deletePaymentInstrumentDetails = "DELETE FROM `payment_plan_instrument_details` where `pps_Id` in (select id from `payment_plan` where orderid="+orderID+");";
        String deletePaymentPlanExecutionStatus = "DELETE from `payment_plan_execution_status` where `id` not in (select paymentPlanExecutionStatus_id from `payment_plan_instrument_details` where paymentPlanExecutionStatus_id is not null);";

        DBUtilities.exUpdateQuery(deletePaymentInstrumentDetails, "pps");
        DBUtilities.exUpdateQuery(deletePaymentPlanItemInstrument, "pps");
        DBUtilities.exUpdateQuery(deletePaymentPlanItem, "pps");
        DBUtilities.exUpdateQuery(deletePaymentPlan, "pps");
        DBUtilities.exUpdateQuery(deletePaymentPlanExecutionStatus, "pps");
    }

    /**
     * Delete OMS DB Entries For An Order ID
     *
     * @param orderID
     * @throws SQLException
     */
    public void deleteOMSDBEntriesForOrderID(String orderID) throws SQLException {
        String deleteOrderEntry = "Delete from orders where id in (" + orderID + ");";
        String deleteOrderReleaseEntries = "delete from order_release where order_id_fk in (" + orderID + ");";
        String deleteOrderLineEntries = "delete from order_line where order_id_fk in (" + orderID + ");";
        String deleteOrderAdditionalInfo = "delete from order_additional_info where order_id_fk in (" + orderID + ");";
        String deleteOrderReleaseAdditionalInfo = "delete from order_release_additional_info where order_release_id_fk in (select id from order_release where order_id_fk in ("
                + orderID + "));";
        String deleteOrderLineAdditionalInfo = "delete from order_line_additional_info where order_line_id_fk in (select id from order_line where order_id_fk in ("
                + orderID + "));";

        DBUtilities.exUpdateQuery(deleteOrderLineAdditionalInfo, "myntra_oms");
        DBUtilities.exUpdateQuery(deleteOrderLineEntries, "myntra_oms");
        DBUtilities.exUpdateQuery(deleteOrderReleaseAdditionalInfo, "myntra_oms");
        DBUtilities.exUpdateQuery(deleteOrderReleaseEntries, "myntra_oms");
        DBUtilities.exUpdateQuery(deleteOrderAdditionalInfo, "myntra_oms");
        DBUtilities.exUpdateQuery(deleteOrderEntry, "myntra_oms");
    }

    public void deleteOMSDBEntriesForLogin(String login) throws SQLException {
        String deleteOrderEntry = "Delete from orders where login in (" + login + ");";
        String deleteOrderReleaseEntries = "delete from order_release where login in (" + login + ");";
        String deleteOrderLineEntries = "delete from order_line where order_id_fk in (select id from orders where login in (" + login + "));";
        String deleteOrderAdditionalInfo = "delete from order_additional_info where order_id_fk in (select id from orders where login in (" + login + "));";
        String deleteOrderReleaseAdditionalInfo = "delete from order_release_additional_info where order_release_id_fk in (select id from orders where login in (" + login + "));";
        String deleteOrderLineAdditionalInfo = "delete from order_line_additional_info where order_line_id_fk in (select id from order_line where order_id_fk in (select id from orders where login in (" + login + ")));";

        DBUtilities.exUpdateQuery(deleteOrderLineAdditionalInfo, "myntra_oms");
        DBUtilities.exUpdateQuery(deleteOrderLineEntries, "myntra_oms");
        DBUtilities.exUpdateQuery(deleteOrderReleaseAdditionalInfo, "myntra_oms");
        DBUtilities.exUpdateQuery(deleteOrderReleaseEntries, "myntra_oms");
        DBUtilities.exUpdateQuery(deleteOrderAdditionalInfo, "myntra_oms");
        DBUtilities.exUpdateQuery(deleteOrderEntry, "myntra_oms");
    }


    /**
     * Delete OMS DB Entries For An Order ID
     *
     * @param portalOrderReleaseId
     * @throws SQLException
     */
    public void deleteWMSDBEntriesForOrderID(String portalOrderReleaseId) throws SQLException {
        String deleteCaptureOrderEntry = "Delete from capture_order_release where portal_order_release_id in (" + portalOrderReleaseId + ");";
        String deleteCaptureOrderLineEntry = "delete from capture_order_release_line where order_release_id in (select id from capture_order_release where portal_order_release_id in (" + portalOrderReleaseId + "));";

        DBUtilities.exUpdateQuery(deleteCaptureOrderLineEntry, "wms");
        DBUtilities.exUpdateQuery(deleteCaptureOrderEntry, "wms");
    }

    public void chnageOlderOrdersToOnlineForCODTesting(String login, String email) throws SQLException{
        String updateOrderEntries = "UPDATE orders SET payment_method='on' where login='" + login + "';";
        String updateOrderReleaseEntries = "UPDATE order_release SET payment_method='on', status_code='DL' where login='" + login + "';";
        DBUtilities.exUpdateQuery("Delete from billing_address where email='"+ email +"' or id between 100 and 2000;", "myntra_oms");
        DBUtilities.exUpdateQuery(updateOrderEntries, "myntra_oms");
        DBUtilities.exUpdateQuery(updateOrderReleaseEntries, "myntra_oms");
    }

    /**
     * Refresh OMS Apllication Properties
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public Svc refreshOMSApplicationPropertyCache() throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.REFRESHAPPLICATIONPROPERTIES, null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
        return service;
    }


    /**
     * Refresh OMS Apllication Properties
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public void refreshOMSJVMCache() throws UnsupportedEncodingException{
        HashMap<String, String> header = new HashMap<>();
        header.put("Authorization", "Basic bW9uaXRvcjplcnBwZXJm");
        header.put("Accept", "text/html");
        header.put("Cache-Control", "no-cache");
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.CLEAR_JVM_CACHE, null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, header);
        Assert.assertEquals(service.getResponseStatus(), 200);
    }

    /**
     * Change Address
     * @param releaseID
     * @param firstName
     * @param lastName
     * @param city
     * @param locality
     * @param pincode
     * @throws IOException
     * @throws JAXBException
     */
    public OmsCommonResponse changeAddress(String releaseID, String firstName, String lastName, String city, String locality, String pincode, Boolean changeLevel,String userLogin) throws IOException, JAXBException {
        List<Long> releaseOrders = new ArrayList<Long>();
        releaseOrders.add(Long.parseLong(releaseID));
        AddressChangeRequestEntry addressChangeRequestEntry = new AddressChangeRequestEntry();
        addressChangeRequestEntry.setReleaseid(Long.parseLong(releaseID));
        addressChangeRequestEntry.setFirstName(firstName);
        addressChangeRequestEntry.setLastName(lastName);
        addressChangeRequestEntry.setNewAddress("Bangalore");
        addressChangeRequestEntry.setCountry("IN");
        addressChangeRequestEntry.setState("KARNATAKA");
        addressChangeRequestEntry.setStateCode("KA");
        addressChangeRequestEntry.setCity(city);
        addressChangeRequestEntry.setPincode(pincode);
        addressChangeRequestEntry.setMobile("1236373123");
        addressChangeRequestEntry.setEmail("test@myntra.com");
        addressChangeRequestEntry.setLocality(locality);
       // addressChangeRequestEntry.setIndividualReleaseChange(changeLevel);
        addressChangeRequestEntry.setUserLogin(userLogin);
        addressChangeRequestEntry.setNewAddressToBeAdded(true);
        addressChangeRequestEntry.setComment("USerRequest");
        addressChangeRequestEntry.setReleaseOrders(releaseOrders);
        String payload = APIUtilities.convertXMLObjectToString(addressChangeRequestEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.CHANGEADDRESS, null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.POST, payload, getOMSHeaderXML());
        OmsCommonResponse addressChangeResponse = (OmsCommonResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OmsCommonResponse());
        return addressChangeResponse;
    }

    /**
     * Update Govt Tax API to update tax by seller
     * @param skuID
     * @param unitTaxAmount
     * @param taxRate
     * @param taxType
     */
    public void updateTax(String skuID, double unitTaxAmount, double taxRate, String taxType){
        SellerOrderUpdateRequest sellerOrderUpdateRequest = new SellerOrderUpdateRequest();
        List<OrderLineEntry> orderLineEntries = new ArrayList<>();
        OrderLineEntry orderLineEntry = new OrderLineEntry();
        orderLineEntry.setTaxAmount(unitTaxAmount);
        orderLineEntry.setTaxRate(taxRate);
        orderLineEntry.setGovtTaxType(taxType);
        orderLineEntries.add(orderLineEntry);
        sellerOrderUpdateRequest.setOrderLines(orderLineEntries);
    }

    /**
     *
     * @param orderReleaseID
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public void stampGovtTaxForVectorSuccess(String orderReleaseID) throws UnsupportedEncodingException, JAXBException {
    	updateDateInRelease(orderReleaseID);
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.STAMP_GOVT_TAX, new String[] { ""+orderReleaseID }, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
        OrderReleaseResponse orderReleaseResponse =  (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
        if(!(orderReleaseResponse.getStatus().getStatusType() == StatusResponse.Type.SUCCESS)){
            log.info("stampGovtTax Failed For Order ReleaseID "+orderReleaseID);
            SlackMessenger.send("scm_e2e_order_sanity", "stampGovtTax Failed For Order ReleaseID " + orderReleaseID, 2);
            Assert.fail("stampGovtTax Failed For Order ReleaseID "+orderReleaseID);
        }
    }
    
    public void updateDateInRelease(String orderReleaseId) throws UnsupportedEncodingException, JAXBException{
    	String packedOnDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	String query = "update order_release set packed_on = '"+packedOnDate+"' where id = '"+orderReleaseId+"';";
    	DBUtilities.exUpdateQuery(query, "myntra_oms");
    }

    /**
     *
     * @param orderReleaseID
     * @return {@link OrderReleaseResponse}
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public OrderReleaseResponse stampGovtTaxForVector(String orderReleaseID) throws UnsupportedEncodingException, JAXBException {
    	updateDateInRelease(orderReleaseID);
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.STAMP_GOVT_TAX, new String[] { orderReleaseID }, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getOMSHeaderJson());
        OrderReleaseResponse orderReleaseResponse =  (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
        return orderReleaseResponse;
    }

    public void printShippingLable(String releaseID, String sellerID) throws UnsupportedEncodingException {
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.CHANGEADDRESS, null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.POST, null, getOMSHeaderJson());
       log.info(service.getResponseBody());
    }
    /**
     * Return the OrderEntry for an OrderID
     * @param quaryParam
     * @return {@link OrderEntry}
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public OrderResponse getOrderSearch(String quaryParam) throws JAXBException, UnsupportedEncodingException{
        OrderResponse orderResponse;
        Svc orderEntrySvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.GETORDER, new String[]{quaryParam}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
        String orderEntryString = orderEntrySvc.getResponseBody();
        orderResponse = (OrderResponse) APIUtilities.convertXMLStringToObject(orderEntryString, new OrderResponse());
        return orderResponse;
    }




    /**
     * Return the OrderEntry for an OrderID
     * @param quaryParam
     * @return {@link OrderEntry}
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public TrackingNumberResponse getTrackingNumber(String quaryParam) throws JAXBException, UnsupportedEncodingException{
        TrackingNumberResponse trackingNumberResponse;
        Svc tracNumberResponse = HttpExecutorService.executeHttpService(Constants.LMS_PATH.COURIER_TRACKING_NUMBER.concat(quaryParam), null, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, null, getOMSHeader());
        String trackingNumber = tracNumberResponse.getResponseBody();
        trackingNumberResponse = (TrackingNumberResponse) APIUtilities.convertXMLStringToObject(trackingNumber, new TrackingNumberResponse());
        return trackingNumberResponse;

    }

    public OrderReleaseResponse assignTrackingNumber(String releaseId, boolean isDelayed) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.ASSIGN_TRACKING_NUMBER, new String[] { releaseId+"?isDelayed="+isDelayed }, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, null, getOMSHeaderXML());
        OrderReleaseResponse orderReleaseResponse =  (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
        return orderReleaseResponse;
    }


    /**
     * Manual Split
     * @param releaseID
     * @param id
     * @Param lineAndQuantity
     * @return {@link RequestGenerator}
     */
    public OrderTrackingResponse changeCourierCode(String releaseID, int id, String courierOperator) throws JAXBException, UnsupportedEncodingException {
        LmsServiceHelper lmsServiceHelper=new LmsServiceHelper();
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
        OrderTrackingResponse orderTrackingResponse1 = new OrderTrackingResponse();
        OrderTrackingDetailEntry orderTrackingDetailEntry = new OrderTrackingDetailEntry();
        OrderTrackingEntry orderTrackingEntry = new OrderTrackingEntry();

        orderTrackingEntry.setId((long) id);
        orderTrackingEntry.setDeliveryStatus(DeliveryStatus.FIT);
        orderTrackingEntry.setCourierOperator(courierOperator);
        orderTrackingEntry.setOrderId(releaseID);
        List<OrderTrackingDetailEntry> orderTrackingDetailEntries = new ArrayList<>();
        orderTrackingDetailEntries.add(orderTrackingDetailEntry);
        orderTrackingEntry.setOrderTrackingDetailEntry(orderTrackingDetailEntries);
        List<OrderTrackingEntry> orderTrackingEntries = new ArrayList<>();
        orderTrackingEntries.add(orderTrackingEntry);
        //orderTrackingResponse1.setOrderTrackings(orderTrackingEntries);

        String payload = APIUtilities.convertXMLObjectToString(orderTrackingEntries);
        Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.CHANGE_COURIER_CODE+"/"+id+"", null, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, payload, Headers.getLmsHeaderXML());
        OrderTrackingResponse response = (OrderTrackingResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderTrackingResponse());
        return response;

    }


    /**
     * Manual Split
     * @param releaseID
     * @param warehouseID
     * @Param lineAndQuantity
     * @return {@link RequestGenerator}
     */
    public OrderReleaseResponse pushOrderToWms(String releaseID, int warehouseID, String[] lineIDAndQuantity, String courier, LineMovementAction lineMovementAction) throws JAXBException, UnsupportedEncodingException {
        LineMovementEntry lineMovementEntry = new LineMovementEntry();
        lineMovementEntry.setReleaseId(Long.parseLong(releaseID));
        lineMovementEntry.setAction(lineMovementAction);
        lineMovementEntry.setWarehouseId(warehouseID);
        lineMovementEntry.setUserLogin("erpadmin");
        lineMovementEntry.setCourierCode(courier);
        lineMovementEntry.setLinesToBeMoved(convertToOrderLineEntries(lineIDAndQuantity));
        String payload = APIUtilities.convertXMLObjectToString(lineMovementEntry);

        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.SPLITORDER, null, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getOMSHeader());
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
        return orderReleaseResponse;
    }

    /**
     * PushReleaseToLms
     * @param orderReleaseID
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public OrderReleaseResponse pushReleaseToLms(String orderReleaseID) throws JAXBException, UnsupportedEncodingException{
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.PUSH_RELEASE_TO_LMS, new String[] {orderReleaseID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.POST, null, getOMSHeader());
        OrderReleaseResponse response = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
        return response;
    }
    
    public PacketResponse pushPacketToLms(String packetId) throws JAXBException, UnsupportedEncodingException{
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.PUSH_PACKET_TO_LMS, new String[] {packetId}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.POST, null, getOMSHeader());
        PacketResponse response = (PacketResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new PacketResponse());
        return response;
    }

    /**
     * Get OMS Header
     * @return {@link HashMap}
     */
    private static HashMap<String, String> getInvoiceOMSHeader() {
        HttpClient httpclient = new DefaultHttpClient();
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
        createOrderHeaders.put("Content-Type", "multipart/form-data");
        createOrderHeaders.put("Accept", "multipart/form-data");
        return createOrderHeaders;
    }
    /**
     * Get Shipping label Entry
     * @param orderReleaseID
     * @throws UnsupportedEncodingException
     */
    public ShippingLabelEntry getShippingLabelEntry(String orderReleaseID) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.SHIPPING_LABEL, new String[] {""+orderReleaseID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
        ShippingLabelEntry shippingLabelEntry = (ShippingLabelEntry) APIUtilities.convertXMLStringToObject(service.getResponseBody(), ShippingLabelEntry.class);
        return shippingLabelEntry;
    }

    public String getPDFAsString(HttpEntity resEntity) throws TikaException, SAXException, IOException {
        InputStream is = resEntity.getContent();
        ParseContext pcontext = new ParseContext();
        //parsing the document using PDF parser
        PDFParser pdfparser = new PDFParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        pdfparser.parse(is, handler, metadata,pcontext);
        return handler.toString();
    }

    public HashMap<String,String> getInvoicePdf(String releseId,String skuName) throws IOException, SAXException, TikaException{
        HashMap<String,String> hm = new HashMap<String,String>();
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = null;
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.INVOICE_SERVICE, new String[] {""+releseId}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getInvoiceOMSHeader());
        httpget = new HttpGet("http://"+service.getIp()+service.getPath());
        httpget.addHeader("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
        httpget.addHeader("Content-Type", "multipart/form-data");
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity resEntity = response.getEntity();
       log.info(resEntity.isChunked());
       log.info(resEntity.getContentType());

       log.info("---------------------------------------------------------------");
       log.info(response.getStatusLine());
        String invoicePdfData=getPDFAsString(resEntity);


      /*  InputStream is = resEntity.getContent();
        ParseContext pcontext = new ParseContext();

        //parsing the document using PDF parser
        PDFParser pdfparser = new PDFParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        pdfparser.parse(is, handler, metadata,pcontext);*/

        //getting the content of the document
       // String invoicePdfData=handler.toString();
       log.info("invoice pdf data :"+invoicePdfData);
        String buyerAddress = StringUtils.substringBetween(invoicePdfData, "Buyer’s Name and Address", "Seller’s Name and Address If undelivered, please return to");
        String sellerName = StringUtils.substringBetween(invoicePdfData, "Seller’s Name and Address If undelivered, please return to", "VAT/ TIN Number");
        String InvoiceNo = StringUtils.substringBetween(invoicePdfData, "VAT/ TIN Number","Order No:");
        String OrderNo = StringUtils.substringBetween(invoicePdfData,"Order No:","Shipment No");
        String ShipmentNo = StringUtils.substringBetween(invoicePdfData,"Shipment No","Buyer’s Name and Address Seller’s Name and Address");
        String VatNumber = StringUtils.substringBetween(invoicePdfData,"VAT/ TIN Number","Buyer Declaration");
        String ShipmentValue = StringUtils.substringBetween(invoicePdfData,"Shipment Value",".00");
        String AmountPaid = StringUtils.substringBetween(invoicePdfData,"Paid",".00");
        String skuLines = StringUtils.substringBetween(invoicePdfData,"Total Amount","Shipment Value");
        String shippmentLabel = StringUtils.substringBetween(invoicePdfData,"Total Amount","Shipment Value");
        String giftCardAmount = StringUtils.substringBetween(invoicePdfData,"Gift Card Amount","Amount");

        String courierCode = null;
        if(StringUtils.substringBetween(invoicePdfData,"AMOUNT","Buyer’s Name")!=null){
            courierCode=StringUtils.substringBetween(invoicePdfData,"AMOUNT","Buyer’s Name");
        }else if(StringUtils.substringBetween(invoicePdfData,"paid","Buyer’s Name")!=null){
            courierCode=StringUtils.substringBetween(invoicePdfData,"paid","Buyer’s Name");
        }
        String loyaltyCredit= StringUtils.substringBetween(invoicePdfData,"Loyalty Credit","Total Amount");
        String myCashback= StringUtils.substringBetween(invoicePdfData,"'My Cashback' Used (-) ","Amount");

       log.info("            Invoice Values               ");
       log.info("==========================================");

        String arra[]=courierCode.split("\n");
       log.info("Courier code :"+courierCode);
       log.info("My cash back :"+myCashback);
        String[] noOfLines=skuLines.split("\n");
       log.info("Address :"+buyerAddress);
       log.info("loyalty credit :"+loyaltyCredit);
       log.info("my cash back :"+myCashback);
       log.info("gift card amount:::: :"+giftCardAmount);
        String Arreay[]=invoicePdfData.split("\n");
        String exceptedString[] = null;
        for(int i=0;i<noOfLines.length;i++){
            if(noOfLines[i].contains(skuName)){
                exceptedString=noOfLines[i+2].split(" ");
            }
        }
        hm.put("Qty", exceptedString[0].trim());
        hm.put("Size", exceptedString[1].trim());
        hm.put("UnitPrice", exceptedString[2].replace("Rs", "").trim());
        hm.put("OtherCharges", exceptedString[3].replace("Rs", "").trim());
        hm.put("Discount", exceptedString[4].replace("Rs", "").trim());
        hm.put("TaxableAmount", exceptedString[5].trim());
        hm.put("VAT/CST", exceptedString[6].replace("Rs", "").trim());
        hm.put("TaxAmount", exceptedString[7].replace("Rs", "").trim());
        hm.put("TotalAmount", exceptedString[8].replace("Rs", "").trim());
        hm.put("LoyaltyCredit", loyaltyCredit);
        hm.put("Barcode", Arreay[1].trim());
        hm.put("buyerAddress", buyerAddress.trim());
        hm.put("CourierCode", arra[arra.length-1]);
        hm.put("SellerName", sellerName.trim());
        hm.put("VatNumber", VatNumber.trim());
        hm.put("OrderNo", Arreay[1].replace(" ", "").trim());
        hm.put("InvoiceNo", InvoiceNo.trim());
        hm.put("ShipmentNo", ShipmentNo.trim());
        hm.put("ShipmentValue", ShipmentValue.replace("Rs", "").trim());
        hm.put("AmountPaid", AmountPaid.trim());
        hm.put("skuLines", skuLines.trim());
        hm.put("MyCashback", myCashback);
        hm.put("ShipmentLabel", Arreay[3].trim());
        hm.put("ShipmentCode", Arreay[4].trim());
        return hm;
    }


		public boolean getOrderOFFHOLDStatusFromOMS(String orderId) {
	        try {
	            OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	            List list = DBUtilities.exSelectQuery("select is_on_hold,on_hold_reason_id_fk from orders where id=" + orderId, "oms");
	            if (list == null){
	                return false;
	            }
	            HashMap<String, Object> hm = (HashMap<String, Object>) list.get(0);
	            if((boolean) hm.get("is_on_hold") && (int) hm.get("on_hold_reason_id_fk") == 39){
	            	SlackMessenger.send("scm_e2e_order_sanity", "Order on hold reason:" + 39 );
	                return false;
	            }else if((boolean) hm.get("is_on_hold") && (int) hm.get("on_hold_reason_id_fk") != 39){
	            	SlackMessenger.send("scm_e2e_order_sanity", "Order on hold reason:" + 39 );
	                omsServiceHelper.resolveOnholdOrders(orderId);
	                return false;
	            }else{
	                return true;
	            }
	        } catch (Exception e) {
	        	SlackMessenger.send("scm_e2e_order_sanity", "Order exception " + e.getMessage(), 1);
	            log.error("Error in getOrderReleaseStatusFromOMS :- " + e.getMessage());
	            return false;
	        }
	    }

	 /**
     * Validate OrderStatus in OMS Order_release table
     *
     * @param orderReleaseId
     * @param delaytoCheck
     * @return
     */
    public boolean validateOrderOFFHoldStatusInOMS(String orderReleaseId, int delaytoCheck) {
        boolean validateStatus = false;
        try {
            for (int i = 0; i < delaytoCheck; i++) {
                boolean status = getOrderOFFHOLDStatusFromOMS(orderReleaseId);
                if(status){
                    validateStatus = true;
                    break;
                }else{
                    End2EndHelper.sleep(5000L);
                }
                log.info("waiting for Order Status in OMS" + status + " .current status=" + status + "\t " + i);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return validateStatus;
    }

    /**
     * Validate OrderStatus in OMS Order_release table
     *
     * @param orderReleaseId
     * @param status
     * @param delaytoCheck
     * @return
     */
    public boolean validateReleaseStatusInArmor(String orderReleaseId, String status, int delaytoCheck) {
        boolean validateStatus = false;
        String status1 = "";
        String status2 = "";
        String status_code = "";
        String[] statusco = status.split(":");
        if (statusco.length == 1) {
            status1 = statusco[0];
            status2 = "";
        } else {
            status1 = statusco[0];
            status2 = statusco[1];
        }
        try {
            for (int i = 0; i < delaytoCheck; i++) {
                status_code = getOrderReleaseStatusFromOMS(orderReleaseId);
                if (status_code.equalsIgnoreCase(status1) || status_code.equalsIgnoreCase(status2)) {
                    validateStatus = true;
                    break;
                } else {
                    Thread.sleep(10000);
                    validateStatus = false;
                }
                log.info("waiting for Order Status in OMS" + status + " .current status=" + status_code + "\t " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("End Status After wait " + status_code + "\t ");
        return validateStatus;
    }


    /**
     * Validate OrderStatus in OMS Order_release table
     *
     * @param orderID
     * @param status
     * @param delaytoCheck
     * @return
     */
    public boolean  validateReleaseStatusInOMS(String orderID, String status, int delaytoCheck) {
    	boolean validateStatus = false;
        String status1 = "";
        String status2 = "";
        String status_code = "";
        String[] statusco = status.split(":");
        if (statusco.length == 1) {
            status1 = statusco[0];
            status2 = "";
        } else {
            status1 = statusco[0];
            status2 = statusco[1];
        }
        try {
            for (int i = 0; i < delaytoCheck; i++) {
                status_code = getOrderReleaseStatusFromOMS(orderID);
                if (status_code.equalsIgnoreCase(status1) || status_code.equalsIgnoreCase(status2)) {
                    validateStatus = true;
                    break;
                } else {
                    Thread.sleep(5000);
                    validateStatus = false;
                }
                log.info("waiting for Order Status in OMS " + status + " .current status=" + status_code + "\t " + i);
            }
            /*if(!validateStatus)
            Assert.assertFalse(true,"Expected Order Status in OMS is " + status + " .current status=" + status_code);*/
             //StatusError.StatusValidator(status,status_code);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }
        log.info("End Status After wait " + status_code + "\t ");// return validateStatus;
        return validateStatus;
    }
    
    /**
     * Validate OrderLineStatus in OMS Order_release table
     *
     * @param orderID
     * @param status
     * @param delaytoCheck
     * @return
     * @throws JAXBException 
     * @throws UnsupportedEncodingException 
     */
    public boolean  validateLineStatusInOMS(String lineID, String status, int delaytoCheck) throws UnsupportedEncodingException, JAXBException {
    	boolean validateStatus = false;
        String status1 = "";
        String status2 = "";
        String status_code = "";
        String[] statusco = status.split(":");
        if (statusco.length == 1) {
            status1 = statusco[0];
            status2 = "";
        } else {
            status1 = statusco[0];
            status2 = statusco[1];
        }
        try {
            for (int i = 0; i < delaytoCheck; i++) {
                status_code = getOrderLineEntry(lineID).getStatus();
                if (status_code.equalsIgnoreCase(status1) || status_code.equalsIgnoreCase(status2)) {
                    validateStatus = true;
                    break;
                } else {
                    Thread.sleep(5000);
                    validateStatus = false;
                }
                log.info("waiting for Order Status in OMS " + status + " .current status=" + status_code + "\t " + i);
            }
            /*if(!validateStatus)
            Assert.assertFalse(true,"Expected Order Status in OMS is " + status + " .current status=" + status_code);*/
             //StatusError.StatusValidator(status,status_code);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }
        log.info("End Status After wait " + status_code + "\t ");// return validateStatus;
        return validateStatus;
    }

    
    public boolean  validatePacketStatusInOMS(String orderID, String status, int delaytoCheck) {
    	boolean validateStatus = false;
        String status1 = "";
        String status2 = "";
        String status_code = "";
        String[] statusco = status.split(":");
        if (statusco.length == 1) {
            status1 = statusco[0];
            status2 = "";
        } else {
            status1 = statusco[0];
            status2 = statusco[1];
        }
        try {
            for (int i = 0; i < delaytoCheck; i++) {
                status_code = getPacketStatusFromOMS(orderID);
                if (status_code.equalsIgnoreCase(status1) || status_code.equalsIgnoreCase(status2)) {
                    validateStatus = true;
                    break;
                } else {
                    Thread.sleep(2000);
                    validateStatus = false;
                }
                log.info("waiting for Order Status in OMS " + status + " .current status=" + status_code + "\t " + i);
            }
            /*if(!validateStatus)
            Assert.assertFalse(true,"Expected Order Status in OMS is " + status + " .current status=" + status_code);*/
             //StatusError.StatusValidator(status,status_code);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }
        log.info("End Status After wait " + status_code + "\t ");// return validateStatus;
        return validateStatus;
    }
    
    
	/**
	 * This method is hack till m7 env is not putting order in WP
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws JAXBException 
	 */
	public void confirmOrderOnHoldHack(String orderId) throws JsonParseException, JsonMappingException, IOException, JAXBException {

		String storeOrderId = getOrderEntry(orderId).getStoreOrderId();
		
		String shradID = storeOrderId.subSequence(storeOrderId.length() - 2, storeOrderId.length()).toString();

		String updateOnHoldQuery = "update `xcart_orders` set `is_on_hold`=1 where order_number='" + storeOrderId
				+ "';";

		if (shradID.equals("01")) {
			DBUtilities.exUpdateQuery(updateOnHoldQuery, "myntra_order");
		} else if (shradID.equals("02")) {
			DBUtilities.exUpdateQuery(updateOnHoldQuery, "myntra_order_2");
		}
		
	   BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
	   OrderResponse orderResponse = bountyServiceHelper.getOrderByStoreOrderId(storeOrderId);
	   String paymentStatus = orderResponse.getData().get(0).getPaymentMethod();
	   String ppsID = orderResponse.getData().get(0).getPaymentPpsId();
	   Boolean putOnHold = false;
	   bountyServiceHelper.confirmOnHoldOrder(storeOrderId, paymentStatus, ppsID, putOnHold);
	}

    public String getOrderReleaseStatusFromArmor(long orderReleaseId) {
        return null;
    }

    public String getOrderReleaseStatusFromOMS(String orderReleaseId) {
        try {
            List list = DBUtilities.exSelectQuery("select status_code from order_release where id=" + orderReleaseId, "oms");
            if (list == null){
                return "false";
            }
            @SuppressWarnings("unchecked")
			Map<String, Object> hm = (Map<String, Object>) list.get(0);
            return "" + hm.get("status_code");
        } catch (Exception e) {
            log.error("Error in get order release status :- " + e.getMessage());
            return "false";
        }
    }
    
    public String getPacketStatusFromOMS(String orderReleaseId) {
        try {
            List list = DBUtilities.exSelectQuery("select status_code from packet where id=" + orderReleaseId, "oms");
            if (list == null){
                return "false";
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> hm = (Map<String, Object>) list.get(0);
            return "" + hm.get("status_code");
        } catch (Exception e) {
            log.error("Error in get order release status :- " + e.getMessage());
            return "false";
        }
    }
    

    /**
     *
     * @param orderID
     * @param delaytoCheck
     * @return
     */
    public boolean validateTrackingNumberNotNullInOrderRelease(String orderID, int delaytoCheck) {
        log.info("Validate tracking number assignment in order_release");
        boolean validateStatus = false;
        try {
            for (int i = 0; i < delaytoCheck; i++) {
                boolean assignedTN = validateTrackingNumber(orderID);
                if (assignedTN==true) {
                    validateStatus = true;
                    break;
                } else {
                    Thread.sleep(2000);
                    validateStatus = false;
                }
                log.info("waiting for tracking number assignment from LMS");
            }
        } catch (Exception e) {
            e.printStackTrace();
            validateStatus = false;
        }
        return validateStatus;
    }

    /**
     *
     * @param orderReleaseID
     * @return
     */
    public boolean validateTrackingNumber(String orderReleaseID) {
        try {
            Map<String, Object> orderRelease = DBUtilities.exSelectQueryForSingleRecord("select tracking_no from order_release where id=" + orderReleaseID, "oms");
            if (orderRelease == null) {
                return false;
            }
            String val = orderRelease.get("tracking_no").toString();
            if (val.equals(null)) return false;
            else return true;
        } catch (Exception e) {
            log.error("Error in getTrackingNumberFromOMS :- " + e.getMessage());
            return false;
        }
    }

    public String  getQuantity(HashMap<String,String> hashMap){
        String qty=hashMap.get("Qty");
        return qty;
    }

    public String  getShippingAdderss(HashMap<String,String> hashMap){
        String address=hashMap.get("buyerAddress");
        return address;
    }

    public String  getSize(HashMap<String,String> hashMap){
        String size=hashMap.get("Size");
        return size;
    }

    public String  getUnitPrice(HashMap<String,String> hashMap){
        String unitPrice=hashMap.get("UnitPrice");
        return unitPrice;
    }

    public String  getOtherCharges(HashMap<String,String> hashMap){
        String otherCharges=hashMap.get("OtherCharges");
        return otherCharges;
    }

    public String  getDiscount(HashMap<String,String> hashMap){
        String discount=hashMap.get("Discount");
        return discount;
    }

    public String  getTaxableAmount(HashMap<String,String> hashMap){
        String taxableAmount=hashMap.get("TaxableAmount");
        return taxableAmount;
    }

    public String  getVatAndCst(HashMap<String,String> hashMap){
        String VATCST=hashMap.get("VAT/CST");
        return VATCST;
    }

    public String  getCourierCode(HashMap<String,String> hashMap){
        String courierCode=hashMap.get("CourierCode");
        return courierCode;
    }

    public String  getSellerName(HashMap<String,String> hashMap){
        String sellerName=hashMap.get("SellerName");
        return sellerName;
    }

    public String  getBarcode(HashMap<String,String> hashMap){
        String barcode=hashMap.get("Barcode");
        return barcode;
    }

    public String  getTotalAmount(HashMap<String,String> hashMap){
        String totalAmount=hashMap.get("TotalAmount");
        return totalAmount;
    }

    public String  getTaxAmount(HashMap<String,String> hashMap){
        String taxAmount=hashMap.get("TaxAmount");
        return taxAmount;
    }

    public String  getVatNumber(HashMap<String,String> hashMap){
        String vatNumber=hashMap.get("VatNumber");
        return vatNumber;
    }

    public String  getInvoiceNo(HashMap<String,String> hashMap){
        String invoiceNo=hashMap.get("InvoiceNo");
        return invoiceNo;
    }

    public String  getShipmentNo(HashMap<String,String> hashMap){
        String shipmentNo=hashMap.get("ShipmentNo");
        return shipmentNo;
    }

    public String  getOrderNo(HashMap<String,String> hashMap){
        String orderNo=hashMap.get("OrderNo");
        return orderNo;
    }

    public String  getShipmentValue(HashMap<String,String> hashMap){
        String shipmentValue=hashMap.get("ShipmentValue");
        return shipmentValue;
    }

    public String  getskuLines(HashMap<String,String> hashMap){
        String skuLines=hashMap.get("skuLines");
        return skuLines;
    }

    public String  getAmountPaid(HashMap<String,String> hashMap){
        String amountPaid=hashMap.get("AmountPaid");
        return amountPaid;
    }

    public String  getGiftCardAmount(HashMap<String,String> hashMap){
        String amountPaid=hashMap.get("GiftCardAmount");
        return amountPaid;
    }
    public OrderLineResponse markOrderLineQDInOMSFromMojo(String lineID) throws JAXBException, UnsupportedEncodingException {
       log.info("Line ID"+lineID);
        OrderLineEntry orderLineEntry = new OrderLineEntry();
        orderLineEntry.setStatus("QD");
        String payload = APIUtilities.convertXMLObjectToString(orderLineEntry);
        Svc orderLineEntrySvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.GET_ORDER_LINE, new String[] {lineID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
        String ordeLinerEntryString = orderLineEntrySvc.getResponseBody();
       log.info("Line Entry :- "+ ordeLinerEntryString);
        OrderLineResponse orderLineResponse = (OrderLineResponse) APIUtilities.convertXMLStringToObject(ordeLinerEntryString, new OrderLineResponse());
        return orderLineResponse;
    }

    public OrderReleaseResponse isCancellationAllowed(String orderReleaseID) throws JAXBException, UnsupportedEncodingException{
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.IS_CANCELALLOWED, new String[] {""+orderReleaseID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
        return orderReleaseResponse;
    }

    /**
     * Update OrderStatus in OMS Order_release table through DB
     *
     * @param releaseId
     * @param status
     * @return  ReleaseStatus
     */

    public String updateReleaseStatusDB(String releaseId, String status){
        HashMap<String, Object> row = null;
        List resultSet = null;

        String query = "update order_release set status_code='"+status+"' where id = '"+releaseId+"';";
        DBUtilities.exUpdateQuery(query, "myntra_oms");
        query = "update order_line set status_code='"+getRespectiveLineStatusOfRelease(status)+"' where order_release_id_fk = '"+releaseId+"';";
        DBUtilities.exUpdateQuery(query, "myntra_oms");
        
        String query2 = "select * from order_release where id = '"+releaseId+"';";
        resultSet = DBUtilities.exSelectQuery(query2, "myntra_oms");
        row = (HashMap<String, Object>) resultSet.get(0);

        return (String) row.get("status_code");
    }
    
    /**
     * This is to get Respective Line status of release status
     * @param releaseStatus
     * @return
     */
    public String getRespectiveLineStatusOfRelease(String releaseStatus){
    	String lineStatus = null;
    	switch(releaseStatus){
    	 case EnumSCM.DL: lineStatus = EnumSCM.D; break;
    	 case EnumSCM.SH: lineStatus = EnumSCM.S; break;
    	 case EnumSCM.RTO: lineStatus = EnumSCM.RTO; break;
    	 case EnumSCM.WP: lineStatus = EnumSCM.WP; break;
    	 case EnumSCM.PK: lineStatus = EnumSCM.PK; break;
    	 case EnumSCM.C: lineStatus = EnumSCM.C; break;
    	 case EnumSCM.L: lineStatus = EnumSCM.L; break;
    	 case EnumSCM.F: lineStatus = EnumSCM.IC; break;
    	}
    	
    	return lineStatus;
    }


    /**
     * Update OrderStatus in OMS Order_release table through DB
     *
     * @param storeReleaseID
     * @param status
     * @return  ReleaseStatus
     */
    public void updateOrderReleaseStatusInDBByStoreReleaseID(String storeReleaseID, String status){
        String query = "update order_release set status_code='"+status+"' where store_release_id = '"+storeReleaseID+"';";
        DBUtilities.exUpdateQuery(query, "myntra_oms");
    }

    /**
     * Update LineStatus in OMS Order_Line table through DB
     *
     * @param LineId
     * @param status
     * @return  LineStatus
     */

    public String updateOrderLineStatusDB(String LineId, String status){
        HashMap<String, Object> row = null;
        List resultSet = null;

        String query = "update order_line set status_code='"+status+"' where id = '"+LineId+"';";
        DBUtilities.exUpdateQuery(query, "myntra_oms");
        String query2 = "select * from order_line where id = '"+LineId+"';";
        resultSet = DBUtilities.exSelectQuery(query2, "myntra_oms");
        row = (HashMap<String, Object>) resultSet.get(0);

        return (String) row.get("status_code");
    }
    /**
     * Ship release order in OMS
     *
     * @param releaseId
     * @return  OrderReleaseResponse
     */

    public OrderReleaseResponse markReleaseShipped(String releaseId) throws JAXBException, UnsupportedEncodingException
    {
        ReleaseUpdateEntry releaseUpdateEntry = new ReleaseUpdateEntry();
        releaseUpdateEntry.setReleaseId(Long.parseLong(releaseId));
        releaseUpdateEntry.setPromiseDate(new Date());
        releaseUpdateEntry.setComment("Marking shipped as per LMS");
        String payload = APIUtilities.convertXMLObjectToString(releaseUpdateEntry);
        String releaseIdPayload = releaseId+"";

        Svc orderReleaseResponseSvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.MARK_RELEASE_SHIP, new String[] {releaseIdPayload}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
        String ordeReleaseResponseEntryString = orderReleaseResponseSvc.getResponseBody();
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(ordeReleaseResponseEntryString, new OrderReleaseResponse());
        return orderReleaseResponse;
    }

    /**
     * Schedule release in OMS for OrderReleaseID
     *
     * @param releaseId
     * @return  OrderReleaseResponse
     */

    public OrderReleaseResponse scheduledReleaseOrder(String releaseId) throws JAXBException, UnsupportedEncodingException
    {
        ReleaseUpdateEntry releaseUpdateEntry = new ReleaseUpdateEntry();
        releaseUpdateEntry.setReleaseId(Long.parseLong(releaseId));
        releaseUpdateEntry.setPromiseDate(new Date());
        releaseUpdateEntry.setComment("Marking shipped as per LMS");
        String payload = APIUtilities.convertXMLObjectToString(releaseUpdateEntry);
        String releaseIdPayload = releaseId+"";

        Svc orderReleaseResponseSvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.MARK_RELEASE_SHIP, new String[] {releaseIdPayload}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
        String ordeReleaseResponseEntryString = orderReleaseResponseSvc.getResponseBody();
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(ordeReleaseResponseEntryString, new OrderReleaseResponse());
        return orderReleaseResponse;
    }

    /**
     * MarkOrder RFR in OMS for OrderReleaseID
     *
     * @param releaseId
     * @return  OmsCommonResponse
     */

    public OrderReleaseResponse markOrderRFRInOMS(String releaseId)throws JAXBException, UnsupportedEncodingException
    {
        ActionEntry actionEntry = new ActionEntry();
        ReleaseUpdateEntry releaseUpdateEntry = new ReleaseUpdateEntry();
        releaseUpdateEntry.setReleaseId(Long.parseLong(releaseId));
        releaseUpdateEntry.setStatus("RFR");
        releaseUpdateEntry.setUserId("System");
        List<ReleaseUpdateEntry> entries = new ArrayList<ReleaseUpdateEntry>();
        entries.add(releaseUpdateEntry);
        actionEntry.setData(entries);

        String payload = APIUtilities.convertXMLObjectToString(actionEntry);

        Svc omsCommonResponseSvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.SCHEDULE_RELEASE,new String[]{""+releaseId}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
        String omsCommonResponseString = omsCommonResponseSvc.getResponseBody();
        OrderReleaseResponse omsCommonResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(omsCommonResponseString, new OrderReleaseResponse());
        return omsCommonResponse;

    }

    /**
     * Delivered release order in OMS
     *
     * @param releaseId
     * @return  OrderReleaseResponse
     */

    public OrderReleaseResponse markDeliveredOrderRelease(String releaseId,String login) throws JAXBException, UnsupportedEncodingException
    {
        ReleaseUpdateEntry releaseUpdateEntry = new ReleaseUpdateEntry();
        releaseUpdateEntry.setReleaseId(Long.parseLong(releaseId));
        releaseUpdateEntry.setComment("Marking RTO OmsOnCall");
        releaseUpdateEntry.setLogin(login);
        releaseUpdateEntry.setPromiseDate(new Date());
        String payload = APIUtilities.convertXMLObjectToString(releaseUpdateEntry);
        String releaseIdPayload = releaseId+"";

        Svc orderReleaseResponseSvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.MARK_RELEASE_DELIVERED, new String[] {releaseIdPayload}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
        String ordeReleaseResponseEntryString = orderReleaseResponseSvc.getResponseBody();
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(ordeReleaseResponseEntryString, new OrderReleaseResponse());
        return orderReleaseResponse;
    }

    /**
     * Mark release Lost in OMS
     *
     * @param releaseId
     * @return  OrderReleaseResponse
     */

    public OrderReleaseResponse markOrderReleaseLost(String releaseId,String login,long reasonId) throws JAXBException, UnsupportedEncodingException
    {
        ReleaseUpdateEntry releaseUpdateEntry = new ReleaseUpdateEntry();
        releaseUpdateEntry.setReleaseId(Long.parseLong(releaseId));
        releaseUpdateEntry.setComment("Marking RTO OmsOnCall");
        releaseUpdateEntry.setLogin(login);
        releaseUpdateEntry.setPromiseDate(new Date());
        releaseUpdateEntry.setCancellationReasonId(reasonId);
        String payload = APIUtilities.convertXMLObjectToString(releaseUpdateEntry);
        String releaseIdPayload = releaseId+"";

        Svc orderReleaseResponseSvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.MARK_RELEASE_LOST, new String[] {releaseIdPayload}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
        String ordeReleaseResponseEntryString = orderReleaseResponseSvc.getResponseBody();
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(ordeReleaseResponseEntryString, new OrderReleaseResponse());
        return orderReleaseResponse;
    }

    /**
     * MarkOrder WP in OMS for OrderReleaseID
     *
     * @param releaseId
     * @return  OmsCommonResponse
     */

    public OmsCommonResponse markOrderWPInOMS(String releaseId)throws JAXBException, UnsupportedEncodingException
    {
        ActionEntry actionEntry = new ActionEntry();
        ReleaseUpdateEntry releaseUpdateEntry = new ReleaseUpdateEntry();
        releaseUpdateEntry.setReleaseId(Long.parseLong(releaseId));
        releaseUpdateEntry.setStatus("RFR");
        releaseUpdateEntry.setUserId("System");
        List<ReleaseUpdateEntry> entries = new ArrayList<ReleaseUpdateEntry>();
        entries.add(releaseUpdateEntry);
        actionEntry.setData(entries);

        String payload = APIUtilities.convertXMLObjectToString(actionEntry);

        Svc omsCommonResponseSvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.MARK_RELEASE_WP,null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.POST, payload, getOMSHeader());
        String omsCommonResponseString = omsCommonResponseSvc.getResponseBody();
        OmsCommonResponse omsCommonResponse = (OmsCommonResponse) APIUtilities.convertXMLStringToObject(omsCommonResponseString, new OmsCommonResponse());
        return omsCommonResponse;

    }

    /**
     * MarkOrder C in OMS for OrderReleaseID
     *
     * @param releaseId
     * @return  OmsCommonResponse
     */

    public OmsCommonResponse markOrderCInOMS(String releaseId)throws JAXBException, UnsupportedEncodingException
    {
        ActionEntry actionEntry = new ActionEntry();
        ReleaseUpdateEntry releaseUpdateEntry = new ReleaseUpdateEntry();
        releaseUpdateEntry.setReleaseId(Long.parseLong(releaseId));
        releaseUpdateEntry.setPromiseDate(new Date());
        releaseUpdateEntry.setUserId("System");
        List<ReleaseUpdateEntry> entries = new ArrayList<ReleaseUpdateEntry>();
        entries.add(releaseUpdateEntry);
        actionEntry.setData(entries);

        String payload = APIUtilities.convertXMLObjectToString(actionEntry);

        Svc omsCommonResponseSvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.COMPLETE_RELEASE,null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.POST, payload, getOMSHeader());
        String omsCommonResponseString = omsCommonResponseSvc.getResponseBody();
        OmsCommonResponse omsCommonResponse = (OmsCommonResponse) APIUtilities.convertXMLStringToObject(omsCommonResponseString, new OmsCommonResponse());
        return omsCommonResponse;

    }

    /**
     * Mark release RTO in OMS
     *
     * @param releaseId
     * @return  OrderReleaseResponse
     */

    public OrderReleaseResponse markOrderReleaseRTO(String releaseId,String login ) throws JAXBException, UnsupportedEncodingException
    {
        ReleaseUpdateEntry releaseUpdateEntry = new ReleaseUpdateEntry();
        releaseUpdateEntry.setReleaseId(Long.parseLong(releaseId));
        releaseUpdateEntry.setComment("Marking RTO OmsOnCall");
        releaseUpdateEntry.setLogin(login);
        String payload = APIUtilities.convertXMLObjectToString(releaseUpdateEntry);
        String releaseIdPayload = releaseId+"";

        Svc orderReleaseResponseSvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.MARK_RELEASE_RTO, new String[] {releaseIdPayload}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
        String ordeReleaseResponseEntryString = orderReleaseResponseSvc.getResponseBody();
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(ordeReleaseResponseEntryString, new OrderReleaseResponse());
        return orderReleaseResponse;
    }

    /**
     * Update Order onHold ReasonId in OMS Order table through DB
     *
     * @param orderId
     * @param reasonId
     * @return  reasonId
     */

    public int updateOrderHoldReasonIdDB(String orderId, long reasonId){
        HashMap<String, Object> row = null;
        List resultSet = null;

        String query = "update orders set is_on_hold="+Boolean.TRUE+",on_hold_reason_id_fk='"+reasonId+"' where id = "+orderId+";";
        DBUtilities.exUpdateQuery(query, "myntra_oms");
        String query2 = "select * from orders where id = "+orderId+";";
        resultSet = DBUtilities.exSelectQuery(query2, "myntra_oms");
        row = (HashMap<String, Object>) resultSet.get(0);

        return (int) row.get("on_hold_reason_id_fk");
    }

    /**
     * Manual Split
     * @param releaseID
     * @param warehouseID
     * @Param lineAndQuantity
     * @return {@link RequestGenerator}
     */
    public List<OrderReleaseResponse> splitOrderV2(String releaseID, int warehouseID, String[] lineIDAndQuantity, String courier, LineMovementAction lineMovementAction) throws JAXBException, UnsupportedEncodingException {

        LineMovementEntry lineMovementEntryInput = null;
        List<LineMovementEntry> lineMovementEntryList = new ArrayList<LineMovementEntry>();
        List<OrderReleaseResponse> orderResponseList = new ArrayList<OrderReleaseResponse>();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        LineMovementResponse lineMovementResponse = omsServiceHelper .reAssignWareHouse(releaseID, warehouseID,lineIDAndQuantity, "ML");
        String success = lineMovementResponse.getStatus().getStatusType().toString();
        Assert.assertEquals(lineMovementResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
        Assert.assertEquals(lineMovementResponse.getStatus().getStatusMessage().toString(),"Warehouse reassignment possible for these lines.");
        Assert.assertEquals(lineMovementResponse.getStatus().getStatusCode(),1034);

        //System.out.println("Here:"+lineMovementResponse.toString());

        lineMovementEntryList = lineMovementResponse.getLineMovementEntry();
        OrderReleaseResponse orderReleaseResponse = null;

        for(LineMovementEntry lineMovementEntry1:lineMovementEntryList ){
            //	lineMovementEntryInput = lineMovementEntry1;
            lineMovementEntry1.setAction(LineMovementAction.REASSIGN_WAREHOUSE);
            String payload = APIUtilities.convertXMLObjectToString(lineMovementEntry1);
            Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.SPLITORDER, null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.POST, payload, getOMSHeader());
            orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
            orderResponseList.add(orderReleaseResponse);
        }
       log.info("responseList:"+orderResponseList);
        return orderResponseList;
    }


    public static void main(String[] args) throws IOException, SAXException, TikaException{
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    }

    /**
     * Get Sticker Invoice Object
     *
     * @param orderReleaseID
     *
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public StickerInvoiceEntry getStickerInvoiceEntry(String orderReleaseID) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.STICKER_INVOICE, new String[] {""+orderReleaseID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
        ReleaseDocumentResponse releaseDocumentResponse = (ReleaseDocumentResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ReleaseDocumentResponse());
        StickerInvoiceEntry stickerInvoiceEntry = (StickerInvoiceEntry) releaseDocumentResponse.getData().get(0);
        return stickerInvoiceEntry;
    }


    /**
     *
     * @param zipcode
     * @param courierCode
     * @param wareHouseID
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public String getRTOAddress(String zipcode, String courierCode, String wareHouseID) throws IOException, JAXBException {
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();

        ReturnAddress returnAddress = lmsServiceHelper.getRtoAddress(zipcode,courierCode,wareHouseID);

        StringBuilder addressBuilder = new StringBuilder();

        if (StringUtils.isNotEmpty(returnAddress.getName())) {
            addressBuilder.append(returnAddress.getName()).append(", ");
        }

        addressBuilder.append(returnAddress.getAddress()).append(", ");

        if (StringUtils.isNotEmpty(returnAddress.getCity())) {
            addressBuilder.append(returnAddress.getCity()).append(" - ");
        }

        if (StringUtils.isNotEmpty(returnAddress.getPostalCode())) {
            addressBuilder.append(returnAddress.getPostalCode()).append(", ");
        }

        if (StringUtils.isNotEmpty(returnAddress.getState())) {
            addressBuilder.append(returnAddress.getState()).append(", ");
        }

        if (StringUtils.isNotEmpty(returnAddress.getCountry())) {
            addressBuilder.append(returnAddress.getCountry());
        }

        String rtoWareHouse = addressBuilder.toString();
        rtoWareHouse = rtoWareHouse.replaceAll("\n", " ");
        return rtoWareHouse;
    }


    /**
     *
     * @param orderID
     * @param status
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     * This is function to check if all Order Releases are in particular status or not.
     * @throws ManagerException 
     */
    public void checkReleaseStatusForOrder(String orderID,String status) throws UnsupportedEncodingException, JAXBException, ManagerException {

        orderEntry = getOrderEntry(orderID);

        try {
			confirmOrderOnHoldHack(orderID);
		} catch (IOException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        orderReleaseEntries = orderEntry.getOrderReleases();
        for(OrderReleaseEntry releases:orderReleaseEntries){
            orderReleaseId = releases.getId().toString();
            ExceptionHandler.handleTrue(validateReleaseStatusInOMS(orderReleaseId, status, 15));


        }
    }
    
    /**
    *
    * @param orderID
    * @param status
    * @return
    * @throws UnsupportedEncodingException
    * @throws JAXBException
    * This is function to check if all Order Releases are in particular status or not.
     * @throws ManagerException 
    */
    public void checkReleaseStatusForOrderWithHack(String orderID,String status) throws UnsupportedEncodingException, JAXBException, ManagerException {

    	orderEntry = getOrderEntry(orderID);
/*    	try {
    		confirmOrderOnHoldHack(orderID);
    	} catch (IOException | JAXBException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}*/
    	orderReleaseEntries = orderEntry.getOrderReleases();
    	for(OrderReleaseEntry releases:orderReleaseEntries){
    		orderReleaseId = releases.getId().toString();
    		ExceptionHandler.handleTrue(validateReleaseStatusInOMS(orderReleaseId, status, 15));


    	}
    }

   public Map<String, String> getSellerAddress(long wareHouseID, long sellerID) throws UnsupportedEncodingException, JAXBException {

       Map<String, String> sellerDetails = new HashMap<>();
       SellerApiHelper sellerApiHelper = new SellerApiHelper();
       SellerResponse sellerResponse = (SellerResponse) sellerApiHelper.getSellerAddress(""+sellerID, ""+wareHouseID);

       SellerEntry sellerEntry = sellerResponse.getData().get(0);

       String sellerName = sellerEntry.getName();
       WarehouseEntry addressEntry = sellerResponse.getData().get(0).getWarehouses().get(0).getWarehouseEntry();
       String tinnumber = null;
       
       for(KYCDocumentEntry kycDocumentEntry: sellerEntry.getKycDocumentEntries()){
    	   if(kycDocumentEntry.getDocumentType().toString().equalsIgnoreCase("TIN")){
    		   tinnumber = kycDocumentEntry.getDocumentNumber(); 
    	   }
       }
       //String tinnumber = ""+sellerEntry.getKycDocumentEntries().get(0).getDocumentNumber();
       String cstnumber = "";

       String sellerAddress = (addressEntry.getAddress() == null ? "" : (addressEntry.getAddress().replace("\n", " ") + ", ")) +
               (addressEntry.getCity() == null ? "" : (addressEntry.getCity())) +
               (addressEntry.getPostalCode() == null ? "" : (" - " + addressEntry.getPostalCode() + " "))+
               (addressEntry.getState() == null ? "" : addressEntry.getState()) +
               (addressEntry.getCountry() == null ? "" : (" " + addressEntry.getCountry()));

       sellerDetails.put("selleraddress", sellerAddress);
       sellerDetails.put("tinnumber", tinnumber);
       sellerDetails.put("sellername", sellerName);
       sellerDetails.put("cstnumber", cstnumber);

       return sellerDetails;
   }

   /**
    * markReadyToDispatchV2 of Darwin2.3
    *
    * @param orderReleaseID
    * @return OrderReleaseResponse
    * @throws UnsupportedEncodingException
    * @throws JAXBException
    */
   public OrderReleaseResponse markReadyToDispatchV2(String orderReleaseID, String [] lineEntries) throws UnsupportedEncodingException, JAXBException {
       SellerOrderUpdateRequest sellerOrderUpdateRequest = new SellerOrderUpdateRequest();
    //   List<OrderLineEntry> orderLineEntries = getOrderLineEntries(orderReleaseID);
       today = new Date();
       futureDate = new Date(today.getTime()+(1000 * 60 * 60 * 48));
       OrderReleaseEntry orderReleaseEntry = getOrderReleaseEntry(orderReleaseID);
       sellerOrderUpdateRequest.setUpdatedOn(futureDate);
       
       List<OrderLineEntry> orderLineEntries = new ArrayList<OrderLineEntry>();
       OrderLineEntry orderLineEntry = new OrderLineEntry();
       
       int i=0;   
       for(String lineEntry:lineEntries){
    	   String lineEntrySplit=lineEntries[i++];   
    	   String[] lineEntrySplitDetails = lineEntrySplit.split(":");
    	   orderLineEntry.setSkuId(Long.parseLong(lineEntrySplitDetails[0]));
    	   orderLineEntry.setGovtUnitTaxAmount(Double.parseDouble(lineEntrySplitDetails[1]));
    	   orderLineEntry.setGovtTaxRate(Double.parseDouble(lineEntrySplitDetails[2]));
    	   orderLineEntry.setGovtTaxType(lineEntrySplitDetails[3]);
    	   orderLineEntry.setQuantity(Integer.parseInt(lineEntrySplitDetails[4]));
    	   orderLineEntry.setSellerId(Long.parseLong(lineEntrySplitDetails[5]));

    	   orderLineEntries.add(orderLineEntry);
       }
       
       sellerOrderUpdateRequest.setOrderLines(orderLineEntries);
       String payload = APIUtilities.convertXMLObjectToString(sellerOrderUpdateRequest);
    //  log.info("Payload Entry: "+payload);
       
       Svc orderReleaseResponseSvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.MARK_READY_TO_DISPATCHV2, new String[] {""+orderReleaseID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
		String ordeReleaseResponseEntryString = orderReleaseResponseSvc.getResponseBody();
		OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(ordeReleaseResponseEntryString, new OrderReleaseResponse());
		return orderReleaseResponse;		
   }

   /**
    * updateTaxV2 of Darwin2.3
    *
    * @param orderReleaseID
    * @return OrderReleaseResponse
    * @throws UnsupportedEncodingException
    * @throws JAXBException
    */
   public OrderReleaseResponse updateTaxV2(String orderReleaseID,String[] vatEntries) throws UnsupportedEncodingException, JAXBException {
       SellerOrderUpdateRequest sellerOrderUpdateRequest = new SellerOrderUpdateRequest();
       List<OrderLineEntry> orderLineEntries = getOrderLineEntries(orderReleaseID);
       OrderReleaseEntry orderReleaseEntry = getOrderReleaseEntry(orderReleaseID);
       int i=0;   
       for(OrderLineEntry orderLine:orderLineEntries){
    	   String vatEntry=vatEntries[i++];
    	   String[] splitVatEntryDetails = vatEntry.split(":");
    	   orderLine.setGovtUnitTaxAmount(Double.parseDouble(splitVatEntryDetails[1]));
    	   orderLine.setGovtTaxRate(Double.parseDouble(splitVatEntryDetails[2]));
    	   orderLine.setGovtTaxType(splitVatEntryDetails[3]);
       }
       sellerOrderUpdateRequest.setReleaseId(Long.parseLong(orderReleaseID));
       //sellerOrderUpdateRequest.setSellerId(orderReleaseEntry.getSellerId());
       sellerOrderUpdateRequest.setOrderLines(orderLineEntries);
       String payload = APIUtilities.convertXMLObjectToString(sellerOrderUpdateRequest);
       String subString = "<taxEntries>"+ StringUtils.substringBetween(payload,"<taxEntries>","</taxEntries>")+ "</taxEntries>";
       payload =  payload.replaceAll(subString, "");
       payload =  payload.replaceAll("<taxEntries/>", "");
      log.info("Payload Entry After: "+payload);
       Svc orderReleaseResponseSvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.UPDATE_TAXV2, new String[] {""+orderReleaseID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
		String ordeReleaseResponseEntryString = orderReleaseResponseSvc.getResponseBody();
		OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(ordeReleaseResponseEntryString, new OrderReleaseResponse());
		return orderReleaseResponse;		
   }


   /**
 * @param orderReleaseID
 * @param taxEntry
 * @return
 * @throws UnsupportedEncodingException
 * @throws JAXBException
 * This is v1 API for seller Tax Update
 */
public OrderReleaseResponse updateTaxV1(String orderReleaseID,String[] taxEntry) throws UnsupportedEncodingException, JAXBException {
       SellerOrderUpdateRequest sellerOrderUpdateRequest = new SellerOrderUpdateRequest();
       List<OrderLineEntry> orderLineEntries = getOrderLineEntries(orderReleaseID);
       OrderReleaseEntry orderReleaseEntry = getOrderReleaseEntry(orderReleaseID);
       String[] splitVatEntryDetails = null;
       List<OrderLineEntry> orderLines = new ArrayList<OrderLineEntry>();
       int i=0;   
       for(OrderLineEntry orderLine:orderLineEntries){
    	   String vatEntry=taxEntry[i++];
    	   OrderLineEntry orderLineEntry = new OrderLineEntry();
    	   splitVatEntryDetails = vatEntry.split(":");
    	   orderLineEntry.setSkuId(Long.parseLong(splitVatEntryDetails[0]));
    	   orderLineEntry.setGovtUnitTaxAmount(Double.parseDouble(splitVatEntryDetails[1]));
    	   orderLineEntry.setGovtTaxRate(Double.parseDouble(splitVatEntryDetails[2]));
    	   orderLineEntry.setGovtTaxType(splitVatEntryDetails[3]);
    	   orderLines.add(orderLineEntry);
       }
       sellerOrderUpdateRequest.setReleaseId(Long.parseLong(orderReleaseID));
       sellerOrderUpdateRequest.setSellerId(Long.parseLong(splitVatEntryDetails[5]));
       sellerOrderUpdateRequest.setOrderLines(orderLines);
       String payload = APIUtilities.convertXMLObjectToString(sellerOrderUpdateRequest);
      log.info("Payload Entry: "+payload);
       Svc orderReleaseResponseSvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.UPDATE_TAXV1, new String[] {splitVatEntryDetails[5],""+orderReleaseID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
       String ordeReleaseResponseEntryString = orderReleaseResponseSvc.getResponseBody();
       OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(ordeReleaseResponseEntryString, new OrderReleaseResponse());
       return orderReleaseResponse;		
   }

    /**
     *
     * @param releaseId
     * @param lineIdAndQuantity
     * @param cancellationReason
     * @param cancellationType
     * @return {@link OrderResponse}
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
	public OrderReleaseResponse cancelItemsV2(String releaseId,String[] lineIdAndQuantity,
			String cancellationReason, String cancellationType) throws JAXBException, UnsupportedEncodingException {

        SellerOrderUpdateRequest sellerOrderUpdateRequest = new SellerOrderUpdateRequest();
        sellerOrderUpdateRequest.setReleaseId(Long.parseLong(releaseId));
        sellerOrderUpdateRequest.setCancellationReason(cancellationReason);
       // sellerOrderUpdateRequest.setCancellationType(cancellationType);
		List<OrderLineEntry> orderlineEntries = new ArrayList<>();
		OrderReleaseEntry orderReleaseEntry = getOrderReleaseEntry(releaseId);
		
		for (String lineAndQuantity : lineIdAndQuantity) {
			String[] splitLines = lineAndQuantity.split(":");
			OrderLineEntry orderLineEntry = new OrderLineEntry();
			orderLineEntry.setSkuId(Long.parseLong(splitLines[0]));
			orderLineEntry.setQuantity(Integer.parseInt(splitLines[1]));
			orderLineEntry.setSellerId(Long.parseLong(splitLines[2]));
			orderlineEntries.add(orderLineEntry);
		}
		sellerOrderUpdateRequest.setOrderLines(orderlineEntries);
		
		String payload = APIUtilities.convertXMLObjectToString(sellerOrderUpdateRequest);
		
		Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.CANCEL_ITEMSV2, new String[] {""+releaseId}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
		OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
		return orderReleaseResponse;
	}
	
	/**
	 * Get Order additional info Entry for an Order ID
	 * @param orderID
	 * @return List
	 */
	public HashMap<String,Object> getOrderAdditionalInfoDBEntry(String orderID, String key){
        List resultSet = null;
        HashMap<String, Object> hm = null;
		try {
			for(int i=0;i<delayToCheck;i++){
				resultSet =  DBUtilities.exSelectQuery("select * from order_additional_info where order_id_fk =" + orderID + " and `key`='"+key+"';", "myntra_oms");
				if(resultSet!=null){
    				hm = (HashMap<String, Object>) resultSet.get(0);
    				break;
    			}else{
    				Thread.sleep(5000);
    				log.info("waiting for cancellation PPS ID");
    			}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
            log.info("Order Entry : "+ e.getLocalizedMessage());
		}
		//hm = (HashMap<String, Object>) resultSet.get(0);
		return hm;
	}
	
	/**
	 * Get OrderRelease additional info Entry for an Order ID
	 * @param orderReleaseID
	 * @return List
	 */
	public HashMap<String,Object> getOrderReleaseAdditionalInfoDBEntry(String orderReleaseID, String key){
        List resultSet = null;
        HashMap<String, Object> hm = null;
		try {
			for(int i=0;i<delayToCheck;i++){
				resultSet =  DBUtilities.exSelectQuery("select * from order_release_additional_info where order_release_id_fk =" + orderReleaseID + " and `key`='"+key+"';", "myntra_oms");
				if(resultSet!=null){
    				hm = (HashMap<String, Object>) resultSet.get(0);
    				break;
    			}else{
    				Thread.sleep(5000);
    				log.info("waiting for cancellation PPS ID");
    			}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
            log.info("Order Release Entry : "+ e.getLocalizedMessage());
		}
		//hm = (HashMap<String, Object>) resultSet.get(0);
		return hm;
	}
	
	/**
	 * Get Order Line additional info Entry for an Line ID
	 * @param lineId
	 * @param key
	 * @return
	 */
	public HashMap<String,Object> getOrderLineAdditionalInfoDBEntry(String lineId, String key){
        List resultSet = null;
        HashMap<String, Object> hm = null;
        for(int i=0;i<delayToCheck;i++){
        	try {
    			resultSet =  DBUtilities.exSelectQuery("select * from order_line_additional_info where order_line_id_fk =" + lineId + " and `key`='"+key+"';", "myntra_oms");
    			if(resultSet!=null){
    				hm = (HashMap<String, Object>) resultSet.get(0);
    				break;
    			}else{
    				Thread.sleep(5000);
    				log.info("waiting for cancellation PPS ID");
    			}
        	} catch (Exception e) {
    			e.printStackTrace();
                log.info("Order Entry : "+ e.getLocalizedMessage());
    		}
    		
    		//hm = (HashMap<String, Object>) resultSet.get(0);
    		
        }
		return hm;
	}
	
	/**
	 * Update giftwrap.charges in mk_widget_key_value_pairs table
	 * @param value
	 * @return 
	 */
	public void updateGiftWrapCharges(int value){
        HashMap<String, Object> hm = null;
		try {
			 DBUtilities.exUpdateQuery("update mk_widget_key_value_pairs set value="+value+" where `key`='giftwrap.charges'"+";", "myntra");
			 End2EndHelper.sleep(10000L);
		} catch (Exception e) {
			e.printStackTrace();
            log.info("Order Entry : "+ e.getLocalizedMessage());
		}
	}

	/**
	 * @param baseStyle
	 * @param freeStyle
	 * @return
	 * @throws UnsupportedEncodingException
	 * This function is to create new free item and item will be valid for durationInHour
	 */
	public Svc createFreeItem(String baseStyle, String freeStyle, int durationInHour) throws UnsupportedEncodingException{
		Date today = new Date();
		Date futureDate = new Date(today.getTime()+(1000 * 60 * 60 * durationInHour));
		
		String startedOn = ""+today.getTime()/1000;
		String expiredOn = ""+futureDate.getTime()/1000;
		//System.out.println("FutureDate: "+today+"  "+futureDate);
		//System.out.println("FutureDate: "+startedOn+"  "+expiredOn);
		String payload = "{\"name\":\"Sample Cart\", \"description\":\"Sample Cart\",\"expiredOn\":"+expiredOn+
			",\"isDynamic\":false,\"isEnabled\":true,\"startedOn\":"+startedOn+",\"updatedOn\":\"\",\"updatedBy\":\"admin\""
					+ ",\"discountRules\":[ {\"type\":4,\"id\":\"\",\"discountFreeItems\":[{\"type\": \"1\",\"itemId\":" +freeStyle+"}],"
					+ "\"placeHolderText\":\"Items\"}],\"discountStyles\":[{\"styleId\":\""+baseStyle+"\"} ],\"discountFilters\":[],"
					+ "\"onCart\":false,\"discountFunding\":\"vendor-EOSS\",\"fundingPercentage\":\"50\",\"fundingTax\":\"1\""
					+ ",\"fundingBasis\":\"discount\",\"discountLimit\":\"100\"}";
		//System.out.println(payload);
		Svc service = HttpExecutorService.executeHttpService(Constants.PNP_PATH.DELETE_STYLE_DISCOUNT_INFO, null, SERVICE_TYPE.PNP_SVC.toString(), HTTPMethods.POST, payload, getOMSHeaderJson());
		Assert.assertEquals(service.getResponseStatus(), 200,"Item has not discounted as free");
		return service;

	}
	
	/**
	 * Get Order Entry for an Order ID
	 * @param orderID
	 * @return List
	 */
	public List getOrderEntryDB(String orderID){
        List resultSet = null;
		try {
			resultSet = DBUtilities.exSelectQuery("select * from orders where id =" + orderID + ";", "myntra_oms");
		} catch (Exception e) {
			e.printStackTrace();
            log.info("Release Entry : "+ e.getLocalizedMessage());
			Assert.fail("Unable to get OrderDBEntry");
		}
		return resultSet;
	}

	/**
     * Get Order Entry for an Order ID
     * @param storeOrderId
     * @return List
     */
    public List getOrderEntryDBFromStoreOrderID(String storeOrderId){
        List resultSet = null;
        try {
            resultSet = DBUtilities.exSelectQuery("select * from orders where store_order_id =" + storeOrderId + ";", "myntra_oms");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Release Entry : "+ e.getLocalizedMessage());
            Assert.fail("Unable to get OrderDBEntry");
        }
        return resultSet;
    }
    
    public HashMap<String,Object> gettaxationDataInfoDBEntry( String entityType, String entityId, String taxType){
        List resultSet = null;
        HashMap<String, Object> hm = null;
		try {
			resultSet =  DBUtilities.exSelectQuery("select * from taxation_data where is_active=1 and `entity_type`='"+entityType+"' and `entity_id` ='"+entityId +"' and `tax_type`='"+taxType+"';", "myntra_oms");
		} catch (Exception e) {
			e.printStackTrace();
            log.info("taxation_data Entry : "+ e.getLocalizedMessage());
		}
		hm = (HashMap<String, Object>) resultSet.get(0);
		return hm;
	}
    
       
    /**
     * This is updateTaxV3 function for GST
     * @param orderReleaseID
     * @param taxEntries
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public OrderReleaseResponse updateTaxV3(String orderReleaseID, String[] taxEntries) throws UnsupportedEncodingException, JAXBException {

    	String payload = sellerOrderUpdateRequestForV3(orderReleaseID,taxEntries,Boolean.TRUE,Boolean.FALSE);
       log.info("Payload Entry: "+payload);
        
        Svc orderReleaseResponseSvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.UPDATE_TAXV3, new String[] {""+orderReleaseID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
		String ordeReleaseResponseEntryString = orderReleaseResponseSvc.getResponseBody();
		OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(ordeReleaseResponseEntryString, new OrderReleaseResponse());
		return orderReleaseResponse;		
    
    	
    }
    
    public List<com.myntra.oms.client.entry.TaxEntry> getTaxEntries(String unitTaxAmount,String taxRate,String taxType){
    	List<com.myntra.oms.client.entry.TaxEntry> taxEntriesList2 = new ArrayList<>();
    	int taxTypeListSize = taxType.split(",").length;
    	for(int i=0;i<taxTypeListSize;i++){
    		String taxAmountTemp = unitTaxAmount.split(",")[i];
    		String taxRateTemp = taxRate.split(",")[i];
    		String taxTypeTemp = taxType.split(",")[i];
    		com.myntra.oms.client.entry.TaxEntry taxEntry2 = new com.myntra.oms.client.entry.TaxEntry();
    		taxEntry2.setUnitTaxAmount(Double.parseDouble(taxAmountTemp));
    		taxEntry2.setTaxRate(Double.parseDouble(taxRateTemp));
    		taxEntry2.setTaxType(taxTypeTemp);
    		
    		taxEntriesList2.add(taxEntry2);
    		
    	}
    	
    	return taxEntriesList2;
    	
    }
    
    public String getVendorIdFromVendorItemDB( String skuId){
        List resultSet = null;
        HashMap<String, Object> hm = null;
		try {
			resultSet =  DBUtilities.exSelectQuery("select * from vendor_item_master where `enabled`=1 and `sku_id`='"+skuId+"';", "myntra_vms");
		} catch (Exception e) {
			e.printStackTrace();
            log.info("taxation_data Entry : "+ e.getLocalizedMessage());
		}
		hm = (HashMap<String, Object>) resultSet.get(0);
		return  ""+hm.get("vendor_id");
	}
    
    
    /**
     * @param releaseId
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     * This function to getSplit of releases on the basis of split strategy
     */
    public OrderReleaseResponse getSplitRelease(String releaseId, List<SplittingStrategyCode> splittingStrategyCodes)throws JAXBException, UnsupportedEncodingException
    {
    	ReleaseSplitRequestEntry releaseSplitRequestEntry = new ReleaseSplitRequestEntry();
    	releaseSplitRequestEntry.setReleaseId(Long.parseLong(releaseId));
    	releaseSplitRequestEntry.setSplittingStrategyList(splittingStrategyCodes);
        String payload = APIUtilities.convertXMLObjectToString(releaseSplitRequestEntry);

        Svc omsCommonResponseSvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.GET_SPLIT_RELEASE,null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.POST, payload, getOMSHeader());
        String orderReleaseResponseString = omsCommonResponseSvc.getResponseBody();
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(orderReleaseResponseString, new OrderReleaseResponse());
        return orderReleaseResponse;

    }
    
    public void markReleaseDeliveredInOMS(String releaseId,String login) throws UnsupportedEncodingException, JAXBException{
    	List<OrderLineEntry> orderLineEntries = getOrderLineEntries(releaseId);
    	updateReleaseStatusDB(releaseId, "SH");
    	for(OrderLineEntry orderLineEntry: orderLineEntries){
    		String lineId = orderLineEntry.getId().toString();
    		if(!orderLineEntry.getStatus().equalsIgnoreCase("IC")){
    			updateOrderLineStatusDB(lineId, "S");
    		}
    		
    	}
    	markDeliveredOrderRelease(releaseId,login);
    }
    
    public HashMap<String, Object> getKYCDocumentForSellerWarehouse(long sellerId,int warehouseId,String documentType){
    	
    	List resultSet = null;
        HashMap<String, Object> hm = null;
		try {
			resultSet =  DBUtilities.exSelectQuery("select * from kyc_document where entity_id in (select id from seller_warehouse where seller_id='"+sellerId+"' "
					+ "and warehouse_id='"+warehouseId+"') and document_type='"+documentType+"';", "myntra_seller1");
		} catch (Exception e) {
			e.printStackTrace();
            log.info("taxation_data Entry : "+ e.getLocalizedMessage());
		}
		hm = (HashMap<String, Object>) resultSet.get(0);
		return hm;
    	
    }
    
    /**
     * This is to get sellerOrderUpdateRequestForV3 for PB and other Seller
     * @param orderReleaseID
     * @param taxEntries
     * @param isTaxRequired
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public String sellerOrderUpdateRequestForV3(String orderReleaseID, String [] taxEntries,Boolean isTaxRequired,Boolean isInvoiceDetailsNeeded) throws JAXBException, UnsupportedEncodingException{
    	SellerOrderUpdateRequest sellerOrderUpdateRequest = new SellerOrderUpdateRequest();
        List<OrderLineEntry> orderLineEntries = getOrderLineEntries(orderReleaseID);
        today = new Date();
        futureDate = new Date(today.getTime()+(1000 * 60 * 60 * 48));
        OrderReleaseEntry orderReleaseEntry = getOrderReleaseEntry(orderReleaseID);
        sellerOrderUpdateRequest.setUpdatedOn(futureDate);
        sellerOrderUpdateRequest.setReleaseId(Long.parseLong(orderReleaseID));
        sellerOrderUpdateRequest.setUserId("1");
        List<OrderLineEntry> orderLines = new ArrayList<OrderLineEntry>();
    	sellerOrderUpdateRequest.setUserId("1");
    	int i=0;
    	for(OrderLineEntry orderLineEntry:orderLineEntries){
    		String[] taxDataList = taxEntries[i].split(":");
    		String skuId = taxDataList[0];
    		String skuQty = taxDataList[4];
    		String unitTaxAmount = taxDataList[1];
    		String taxRate = taxDataList[2];
    		String taxType = taxDataList[3];
    		String sellerId = taxDataList[5];
    		
    		OrderLineEntry orderLineEntryNew = new OrderLineEntry();
    		if(isInvoiceDetailsNeeded){
        		orderLineEntryNew.setInvoiceDate(new Date());
        		orderLineEntryNew.setInvoiceNumber(""+orderLineEntry.getId());
    		}

    		orderLineEntryNew.setSkuId(Long.parseLong(skuId));
    		orderLineEntryNew.setQuantity(Integer.parseInt(skuQty));
    		orderLineEntryNew.setSellerId(Long.parseLong(sellerId));
    		if(isTaxRequired){
    			orderLineEntryNew.setTaxEntries(getTaxEntries(unitTaxAmount,taxRate,taxType));
    		}
    		orderLines.add(orderLineEntryNew);
    		
    		i++;
    	}
    	sellerOrderUpdateRequest.setOrderLines(orderLines);
        String payload = APIUtilities.convertXMLObjectToString(sellerOrderUpdateRequest);
        return payload;
    }
    
    /**
     * This is V3 version of markReadyToDispatch for PB Seller
     * @param orderReleaseID
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public OrderReleaseResponse markReadyToDispatchV3(String orderReleaseID, String [] taxEntries,Boolean isTaxRequired,Boolean isInvoiceDetailsNeeded) throws UnsupportedEncodingException, JAXBException {
 
        String payload = sellerOrderUpdateRequestForV3(orderReleaseID,taxEntries,isTaxRequired,isInvoiceDetailsNeeded);
        
        Svc orderReleaseResponseSvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.MARK_READY_TO_DISPATCHV3, new String[] {""+orderReleaseID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
 		String ordeReleaseResponseEntryString = orderReleaseResponseSvc.getResponseBody();
 		OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(ordeReleaseResponseEntryString, new OrderReleaseResponse());
 		return orderReleaseResponse;		
    }
    
    /**
     * This is V3 version of markReadyToDispatch for Myntra Seller
     * @param orderReleaseID
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public OrderReleaseResponse markReadyToDispatchV3ForMyntraSeller(String orderReleaseID,String diapatchType) throws UnsupportedEncodingException, JAXBException {
 
        String payload = sellerOrderUpdateRequestForV3(orderReleaseID,diapatchType);     
        Svc orderReleaseResponseSvc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.MARK_READY_TO_DISPATCHV3, new String[] {orderReleaseID}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
 		String ordeReleaseResponseEntryString = orderReleaseResponseSvc.getResponseBody();
 		OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(ordeReleaseResponseEntryString, new OrderReleaseResponse());
 		return orderReleaseResponse;		
    }
 
    
    private String sellerOrderUpdateRequestForV3(String orderReleaseID,String diapatchType) throws UnsupportedEncodingException, JAXBException {
		// TODO Auto-generated method stub
    	SellerOrderUpdateRequest sellerOrderUpdateRequest = new SellerOrderUpdateRequest();
    	sellerOrderUpdateRequest.setReleaseId(Long.parseLong(orderReleaseID));
    	sellerOrderUpdateRequest.setUserId("1");
    	
    	List<OrderLineEntry> orderlines = getOrderLineEntries(orderReleaseID);
    	List<OrderLineEntry> newOrderlinesList = new ArrayList<>();
    	
    	for(OrderLineEntry orderLineEntry: orderlines){
    		OrderLineEntry newOrderLineEntry = new OrderLineEntry();
    		if(!orderLineEntry.getStatus().equalsIgnoreCase("IC")){
    			if(diapatchType.equalsIgnoreCase(ReadyToDispatchType.DIFFERENT_SKU.name())){
    				newOrderLineEntry.setSkuId(orderLineEntry.getSkuId()+123);
    			}else if(diapatchType.equalsIgnoreCase(ReadyToDispatchType.MISSING_SKU.name())){
    				newOrderLineEntry.setSkuId(null);
    			}else{
    				newOrderLineEntry.setSkuId(orderLineEntry.getSkuId());
    			}
    				
    			
    			if(diapatchType.equalsIgnoreCase(ReadyToDispatchType.GREATER_QTY.name())){
    				newOrderLineEntry.setQuantity(orderLineEntry.getQuantity()+1);
    			}else if(diapatchType.equalsIgnoreCase(ReadyToDispatchType.ZERO_QTY.name())){
    				newOrderLineEntry.setQuantity(0);
    			}else{
    				newOrderLineEntry.setQuantity(orderLineEntry.getQuantity());
    			}
    			        		
        		newOrderLineEntry.setSellerId(orderLineEntry.getSellerId());
        		newOrderlinesList.add(newOrderLineEntry);
    		}
    		
    	 }
    	sellerOrderUpdateRequest.setOrderLines(newOrderlinesList);
    	String payload = APIUtilities.convertXMLObjectToString(sellerOrderUpdateRequest);
    	
		return payload;
	}

	/**
     * This is to get request payloadEntry for ConfirmOrder for PB
     * @param orderReleaseID
     * @param taxEntries
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public String requestPayloadEntryForConfirmOrderForPB(String orderReleaseID,String [] taxEntries) throws UnsupportedEncodingException, JAXBException{
    	SellerOrderUpdateRequest sellerOrderUpdateRequest = new SellerOrderUpdateRequest();
        List<OrderLineEntry> orderLineEntries = getOrderLineEntries(orderReleaseID);
        List<OrderLineEntry> orderLines = new ArrayList<OrderLineEntry>();
        
        int i=0;
        for(OrderLineEntry orderLineEntryNew:orderLineEntries){
    		String[] taxDataList = taxEntries[i].split(":");
    		String skuId = taxDataList[0];
    		String skuQty = taxDataList[4];
    		String sellerId = taxDataList[5];
    		
    		orderLineEntryNew.setSkuId(Long.parseLong(skuId));
    		orderLineEntryNew.setQuantity(Integer.parseInt(skuQty));
    		orderLineEntryNew.setSellerId(Long.parseLong(sellerId));
    		orderLines.add(orderLineEntryNew);
        }
        sellerOrderUpdateRequest.setReleaseId(Long.parseLong(orderReleaseID));
        sellerOrderUpdateRequest.setOrderLines(orderLines);
        String payload = APIUtilities.convertXMLObjectToString(sellerOrderUpdateRequest);
        return payload;
    }
    
    /**
     * This function is to confirmOrder for PB Seller
     * @param orderReleaseID
     * @param taxEntries
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public OrderReleaseResponse confirmOrderForPB(String orderReleaseID,String [] taxEntries) throws UnsupportedEncodingException, JAXBException{

        String payload = requestPayloadEntryForConfirmOrderForPB(orderReleaseID,taxEntries);
        
        Svc confirmOrderResponse = HttpExecutorService.executeHttpService(Constants.OMS_PATH.CONFIRM_ORDER_PB,null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
 		String confirmOrderResponseEntry = confirmOrderResponse.getResponseBody();
 		OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(confirmOrderResponseEntry, new OrderReleaseResponse());
 		return orderReleaseResponse;		
    }
    
    /**
     * This is to check if GST FG is on or off
     * @return
     */
    public String getApplicationPropertyValue(String key){
        Map<String,Object> gstValueFromDB = DBUtilities.exSelectQueryForSingleRecord("select `value` from `core_application_properties` where `name` = '"+key+"';","myntra_tools");
        if(gstValueFromDB!=null){
        	return  (String) gstValueFromDB.get("value");
        }else{
        	log.info("Application property "+key+" not found");
        	return null;
        }
    }
    
   
    public String addCustomerAddressForNewUser(String emailId,Long pincode) throws IOException{
    	
    	List resultSet = null;
    	HashMap<String, Object> hm = null;
        String emailToBeInserted = emailId.replaceFirst("@", "&#x40;");
        String addressId=null;
        String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra",emailId.toString());
        
        String query = "INSERT INTO `mk_customer_address` (`login`, `default_address`, `name`, `address`, `city`, `state`, `country`, `pincode`, `email`, `mobile`, `phone`, `datecreated`, `errmask`, `locality`, `updatedBy`, `updatedOn`, `address_scoring`, `address_type`, `available_days`)"+
           "VALUES ('"+uidx+"', 1, 'shubham gupta', 'Flat no 407, Laa lavender, Old mangammanapalaya road, Bommanahalli', 'Bangalore, ', 'KA', 'IN', '"+pincode+"', '"+emailToBeInserted+"', '1234567890', '', NULL, 00000000, 'Bommanahalli  &#x28;Bangalore&#x29;,"
           		+ " ', '"+uidx+"', '2017-07-07 17:47:37', 0, 'HOME', NULL);";
		try {

			DBUtilities.exUpdateQuery(query,"myntra_address");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		addressId = getCustomerAddressForUser(emailId,pincode);
		return addressId;
    	
    }
    
    public String getCustomerAddressForUser(String emailId,Long pincode) throws IOException{
    	
    	List resultSet = null;
    	HashMap<String, Object> hm = null;
    	String addressId=null;
    log.info(emailId);
        String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra",emailId);
        
        String query ="select * from `mk_customer_address` where login = '"+uidx+"' and pincode = '"+pincode+"';";
		try {
			resultSet =  DBUtilities.exSelectQuery(query,"myntra_address");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(resultSet!=null){
			Iterator iterator = resultSet.iterator();
			while(iterator.hasNext()){
				 addressId = (String)((Map)iterator.next()).get("id").toString();
			}
		}
		
		return addressId;
		
    	
    }
    
    /**
     * @param orderReleaseID
     * @param warehouseID
     * @throws UnsupportedEncodingException
     * @throws InterruptedException
     */
    public void pushOrderToWave(String orderReleaseID,int warehouseID) throws UnsupportedEncodingException, InterruptedException{
    	// Push Order to Order wave for picking
    			wmsServiceHelper.pushOrderToWave(orderReleaseID);
    			// End2EndHelper.sleep(20000L);
    			if (!wmsServiceHelper.validateOrderInCoreOrderRelease(orderReleaseID, 8)) {
    				SlackMessenger.send("scm_e2e_order_sanity", "Order not pushed to wave, aborting", 2);
    				wmsServiceHelper.creteRelaseInCore_order_release(orderReleaseID.toString());
    			}
    			List<Map<String, Object>> orderLine = DBUtilities.exSelectQuery("select sku_id, quantity from order_line where order_release_id_fk = "+orderReleaseID,"oms");
    			for (Map<String, Object> line: orderLine){
    				wmsServiceHelper.insertItem(line.get("sku_id").toString(),""+warehouseID,Integer.parseInt(line.get("quantity").toString()));
    			}

    }
    
    public void insertInvoiceAndTaxDetailsForSilkrouteOrders(String orderID) throws Exception{
    	OrderEntry orderEntry = getOrderEntry(orderID);
    	List<OrderReleaseEntry> orderReleaseEntries = orderEntry.getOrderReleases();
    	
    	for(OrderReleaseEntry orderReleaseEntry:orderReleaseEntries){
    		List<OrderLineEntry> orderLineEntries = orderReleaseEntry.getOrderLines();
    		for(OrderLineEntry orderLineEntry:orderLineEntries){
    			insertInTaxationData(orderLineEntry.getId());
    			insertInOrderLineAddInfo(orderLineEntry.getId());
    		}
    	}
    }

	private void insertInTaxationData(Long lineId) {
		// TODO Auto-generated method stub
		String query = "INSERT INTO `taxation_data` (`entity_type`, `entity_id`, `tax_type`, `tax_rate`, `unit_taxable_amount`, `unit_tax_amount`, `is_active`, `created_on`, `created_by`, `last_modified_on`, `version`) "
				+ "VALUES ('ORDER_LINE', '"+lineId+"', 'IGST', 28.000, 858.59, 240.41, 1, '2017-08-01 20:58:38', 'mobile', '2017-08-01 20:58:38', 0);";
		DBUtilities.exUpdateQuery(query, "myntra_oms");
		
	}

	private void insertInOrderLineAddInfo(Long lineId) {
		// TODO Auto-generated method stub
		String query = "INSERT INTO `order_line_additional_info` (`order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) "
				+ "VALUES ('"+lineId+"', 'INVOICE_DATE', '2017-08-02 12:19:28', 'system', '2017-08-02 12:19:28', '2017-08-02 12:19:28', 0), "
				+ "       ('"+lineId+"', 'INVOICE_NUMBER', '"+lineId+"', 'system', '2017-08-02 12:19:28', '2017-08-02 12:19:28', 0);";
		DBUtilities.exUpdateQuery(query, "myntra_oms");
		
	}
	
	public static HashMap<Long, String> getAllDisableProduct(long styleIds[]) throws Exception{
        HashMap<Long,String> finalDisabledStyles = new HashMap<Long,String>();
        
		for(int i=0;i<styleIds.length;i++){
			long styleId = styleIds[i];
	        Svc confirmOrderResponse = HttpExecutorService.executeHttpService(Constants.CMS_CATALOG.PRODUCT,new String[] {""+styleId}, SERVICE_TYPE.CMS_CATALOG.toString(), HTTPMethods.GET,null, getOMSHeader());
	 		String confirmOrderResponseEntry = confirmOrderResponse.getResponseBody();
	 		ProductDetailResponse productDetailResponse = (ProductDetailResponse) APIUtilities.convertXMLStringToObject(confirmOrderResponseEntry, new ProductDetailResponse());
	 		String status = productDetailResponse.getData().get(0).getStyleType();
	 		if(!status.equalsIgnoreCase("P")){
	 			finalDisabledStyles.put(styleId, status);
	 		}
		}

 		return finalDisabledStyles;		
	}
	 /**
     * @param key -- Name of the key we can take from EnumSCM ADDITIONAL_INFO_PACKAGING_TYPE
     * @param value 
     * @param lineId 
     * @throws UnsupportedEncodingException
     * @throws InterruptedException
     */
	public void updateOrderLineAdditionInfo(String key,String value,String lineId){
        try {
            DBUtilities.exUpdateQuery("update order_line_additional_info set `value`='"+value+"' where `key`='"+key+"' and `order_line_id_fk`='"+lineId+"';", "myntra_oms");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Release Entry : "+ e.getLocalizedMessage());
        }
    }
	
	/**
	 * This is to get payload for consolidation
	 * @param listOfRelease
	 * @param consolidationType 
	 * @throws JAXBException 
	 * @throws UnsupportedEncodingException 
	 */
	public String getAllConsolidatedLineRequestPayloadEntry(List<String> listOfRelease, String consolidationType) throws JAXBException, UnsupportedEncodingException{
		ConsolidationRequestEntry consolidationRequest = new ConsolidationRequestEntry();
		List<OrderReleaseEntry> orderReleaseEntryList = new ArrayList<>();
		for(String releaseId:listOfRelease){
			OrderReleaseEntry orderReleaseEntry = getOrderReleaseEntry(releaseId);
			OrderReleaseEntry orderReleaseEntryTemp = new OrderReleaseEntry();
			orderReleaseEntryTemp.setId(Long.parseLong(releaseId));
			List<OrderLineEntry> orderLines = new ArrayList<>();
			for(OrderLineEntry orderLineEntry:orderReleaseEntry.getOrderLines()){
				OrderLineEntry orderLineEntryTemp = new OrderLineEntry();
				if(consolidationType.equalsIgnoreCase("INVALID_SKU")){
					orderLineEntryTemp.setSkuId(orderLineEntry.getSkuId()+1);
				}else{
					orderLineEntryTemp.setSkuId(orderLineEntry.getSkuId());
				}
				if(consolidationType.equalsIgnoreCase("INVALID_SKUQTY")){
					orderLineEntryTemp.setQuantity(orderLineEntry.getQuantity()+5);
				}else{
					orderLineEntryTemp.setQuantity(orderLineEntry.getQuantity());
				}
				
				orderLines.add(orderLineEntryTemp);
			}
			orderReleaseEntryTemp.setOrderLines(orderLines);
			orderReleaseEntryList.add(orderReleaseEntryTemp);
		}
		consolidationRequest.setReleases(orderReleaseEntryList );
		return APIUtilities.convertXMLObjectToString(consolidationRequest);

	}
	
	/**
	 * This gets default payload for invalid order
	 * @param listOfRelease
	 * @param consolidationType
	 * @return
	 * @throws JAXBException
	 */
	private String getPayloadEntryForInvalidOrder(List<String> listOfRelease, String consolidationType) throws JAXBException {
		// TODO Auto-generated method stub
		ConsolidationRequestEntry consolidationRequest = new ConsolidationRequestEntry();
		List<OrderReleaseEntry> orderReleaseEntryList = new ArrayList<>();
		for(String releaseId:listOfRelease){
			OrderReleaseEntry orderReleaseEntryTemp = new OrderReleaseEntry();
			orderReleaseEntryTemp.setId(Long.parseLong(releaseId));
			List<OrderLineEntry> orderLines = new ArrayList<>();
				OrderLineEntry orderLineEntryTemp = new OrderLineEntry();
				orderLineEntryTemp.setSkuId(3831L);
				orderLineEntryTemp.setQuantity(1);
				orderLines.add(orderLineEntryTemp);
			orderReleaseEntryTemp.setOrderLines(orderLines);
			orderReleaseEntryList.add(orderReleaseEntryTemp);
		}
		consolidationRequest.setReleases(orderReleaseEntryList );
		return APIUtilities.convertXMLObjectToString(consolidationRequest);
	}
	
	public ConsolidationResponseEntry getAllConsolidatedLines(List<String> listOfRelease, String consolidationType) throws JAXBException, UnsupportedEncodingException{
		String payload = null;
		if(consolidationType.equalsIgnoreCase("INVALID_ORDER")){
			payload = getPayloadEntryForInvalidOrder(listOfRelease,consolidationType);
		}else{
			payload = getAllConsolidatedLineRequestPayloadEntry(listOfRelease,consolidationType);
		}
		
		Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.CONSOLIDATE, null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.POST, payload,getOMSHeaderXML());

		String consolidationResponseEntry = service.getResponseBody();
		ConsolidationResponseEntry consolidationResponse = (ConsolidationResponseEntry) APIUtilities.convertXMLStringToObject(consolidationResponseEntry, new ConsolidationResponseEntry());
		return consolidationResponse;
	}
	
/*	public String createPayloadForRTDV4(List<String> listOfRelease) throws UnsupportedEncodingException, JAXBException{
		PacketCreationRequest packetCreationRequest = new PacketCreationRequest();
		
		 List<SellerOrderUpdateRequest> sellerOrderUpdateRequestList = new ArrayList<SellerOrderUpdateRequest>();
		 
		 for(String releaseId:listOfRelease){
			SellerOrderUpdateRequest sellerOrderUpdateRequest = new SellerOrderUpdateRequest();
			OrderReleaseEntry orderReleaseEntry = getOrderReleaseEntry(releaseId);
			List<OrderLineEntry> orderLines = new ArrayList<>();
			
			for(OrderLineEntry orderLineEntry:orderReleaseEntry.getOrderLines()){
				OrderLineEntry orderLineEntryTemp = new OrderLineEntry();
				orderLineEntryTemp.setSkuId(orderLineEntry.getSkuId());
				orderLineEntryTemp.setQuantity(orderLineEntry.getQuantity());
				orderLineEntryTemp.setDispatchWarehouseId(orderLineEntry.getDispatchWarehouseId());
				orderLineEntryTemp.setSellerId(orderLineEntry.getSellerId());
				orderLines.add(orderLineEntryTemp);
			}
			sellerOrderUpdateRequest.setOrderLines(orderLines);
			sellerOrderUpdateRequest.setReleaseId(Long.parseLong(releaseId));
			sellerOrderUpdateRequest.setUpdatedOn(new Date());
			sellerOrderUpdateRequest.setUserId(orderReleaseEntry.getEmail());
			sellerOrderUpdateRequestList.add(sellerOrderUpdateRequest);
		}
		 packetCreationRequest.setSellerOrderUpdateRequests(sellerOrderUpdateRequestList);
		 
		 return APIUtilities.convertXMLObjectToString(packetCreationRequest);
		 
	}
	
	public PacketResponse markOrderPackedRDTV4InOMS(List<String> listOfRelease) throws UnsupportedEncodingException, JAXBException{
		String payload = createPayloadForRTDV4(listOfRelease);
		Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.MARK_READY_TO_DISPATCHV4,null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload,getOMSHeaderXML());

		String consolidationResponseEntry = service.getResponseBody();
		PacketResponse packetResponse = (PacketResponse) APIUtilities.convertXMLStringToObject(consolidationResponseEntry, new PacketResponse());
		return packetResponse;
	}*/

	public static Date getDate() {
		DateTime date = new DateTime();
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		String str = fmt.print(date);
		DateTime dt = fmt.parseDateTime(str);
		return dt.toDate();
	}
	
    /**
     * Refresh Munshi Apllication Properties
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     * @throws ManagerException 
     */
    public Svc refreshMunshiApplicationPropertyCache() throws UnsupportedEncodingException, JAXBException, ManagerException {
        Svc service = HttpExecutorService.executeHttpService(Constants.MUNSHI_PATH.REFRESHAPPLICATIONPROPERTIES, null, SERVICE_TYPE.MUNSHI_SVC.toString(), HTTPMethods.GET, null, getOMSHeader());
        return service;
    }
	/**
	 * This function is to update value in order_line_additional_info
	 * 
	 * @param lineId
	 * @param key
	 * @param value
	 */
	public void updateInOrderLineAdditionalInfo(String lineId, String key, String value) {
		String query = "update `order_line_additional_info` set `value`='" + value + "' where `key`='" + key
				+ "' and `order_line_id_fk`='" + lineId + "';";
		DBUtilities.exUpdateQuery(query, "myntra_oms");
	}

	/**
	 * This function is to insert value in order_line_additional_info
	 * 
	 * @param lineId
	 * @param key
	 * @param value
	 */
	public void insertDataInOrderLineAdditionalInfo(String lineId, String key, String value) {
		String query = "INSERT INTO `order_line_additional_info` (`order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`)"
				+ " VALUES (" + lineId + ", '" + key + "', '" + value
				+ "', 'pps-admin', '2017-07-18 10:47:22', '2017-07-18 10:47:22', 0);";
		DBUtilities.exUpdateQuery(query, "myntra_oms");
	}

	/**
	 * This is to update onhold for release id.
	 * @param orderReleaseId
	 * @param reasonId
	 * @throws Exception
	 */
	public void updateOnHoldForReleaseId(String orderReleaseId,String reasonId) throws Exception {
		// TODO Auto-generated method stub
        String query = "update order_release set is_on_hold="+Boolean.TRUE+",on_hold_reason_id_fk='"+reasonId+"' where id = "+orderReleaseId+";";
        DBUtilities.exUpdateQuery(query, "myntra_oms");
        ExceptionHandler.handleTrue(getOrderReleaseEntry(orderReleaseId).getOnHold().booleanValue(),
				"Release "+orderReleaseId+" should be in onhold status");
		
	}
	
    /**
     * Get Order Packet Entry for an Packet ID
     * @param lineID
     * @return List
     */
    public Map getPacketDBEntryForPacketId(String packetId){
        Map<String, Object> resultSet = null;
        try {
            resultSet = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from `packet` where id =" + packetId + ";", "myntra_oms");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Order Line Entry : "+ e.getLocalizedMessage());
        }
        return resultSet;
    }
    
    
	/**
	 * This is to update packet status
	 * @param packetId
	 * @param status
	 * @throws Exception
	 */
	public void updatePacketStatus(String packetId,String status) throws Exception {
		// TODO Auto-generated method stub
        String query = "update packet set status_code='"+status+"' where id = "+packetId+";";
        DBUtilities.exUpdateQuery(query, "myntra_oms");
	}
	
	
    /**
     * This function will wait unless orderRelease is not in Hold with particular reason till delaytime
     * @param orderReleaseId
     * @param reasonId
     * @param delaytoCheck
     * @return
     */
    public boolean verifyOrderIsInOnHoldWithReasonId(String orderReleaseId,int reasonId, int delaytoCheck) {
        boolean releaseIdWithParticularHold = false;
        try {
            for (int i = 0; i < delaytoCheck; i++) {
                Long currentReasonId = getOrderReleaseEntry(orderReleaseId).getOnHoldReasonId();
                if(currentReasonId !=null && currentReasonId==reasonId){
                	releaseIdWithParticularHold = true;
                    break;
                }else{
                    End2EndHelper.sleep(5000L);
                }
                log.info("waiting for Order release to move onhold with Id" + reasonId + " .current status=" + currentReasonId + "\t " + i);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return releaseIdWithParticularHold;
    }

	/**
	 * @param lineMovementEntries
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public OrderReleaseResponse reAssignWareHouseV2(BulkLineMovementEntry bulkLineMovementEntry) throws JAXBException, UnsupportedEncodingException {
		// TODO Auto-generated method stub\

		String payload = APIUtilities.convertXMLObjectToString(bulkLineMovementEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.REASSIGNWAREHOUSEV2, null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.PUT, payload, getOMSHeader());
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
		return orderReleaseResponse;
	}
	
	/**
	 * @param releaseEntries
	 * @param shippingMethod
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public void updateShippingMethodForOrder(List<OrderReleaseEntry> releaseEntries, String shippingMethod) throws UnsupportedEncodingException, JAXBException{

		for(OrderReleaseEntry orderReleaseEntry:releaseEntries){
			
	        String query = "update order_release set shipping_method='"+shippingMethod+"' where id = "+orderReleaseEntry.getId()+";";
	        DBUtilities.exUpdateQuery(query, "myntra_oms");
	        
	        String packetId = getPacketIdFromReleasId(orderReleaseEntry.getId().toString());
	        if(packetId!=null || packetId!=""){
		        query = "update packet set shipping_method='"+shippingMethod+"' where id = "+packetId+";";
		        DBUtilities.exUpdateQuery(query, "myntra_oms");
	        }
		}
	}
	
    /**
     * To update Application properties
     * @param key
     * @param value
     */
    public void updateApplicationProperty(String key,String value){
    	String query = "update `core_application_properties` set `value`='"+value+"' where `name`='"+key+"';";
        DBUtilities.exUpdateQuery(query, "myntra_tools");
    }

}
