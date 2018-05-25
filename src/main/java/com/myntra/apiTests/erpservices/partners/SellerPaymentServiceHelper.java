package com.myntra.apiTests.erpservices.partners;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.erpservices.Constants;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.testng.Assert;
import org.testng.AssertJUnit;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderResponse;
import com.myntra.oms.phonix.event.ReleaseUpdateEventEntry;
import com.myntra.taxmaster.client.response.TaxResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
/*
 * Author: Shubham gupta
 */
public class SellerPaymentServiceHelper {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SellerPaymentServiceHelper.class);
	static String envName = "fox8";
	
	APIUtilities apiUtil = new APIUtilities();
	String login, from, to;
	
	static String rabbitMqName = null;
    
    public SellerPaymentServiceHelper(){
        envName = init.Configurations.GetTestEnvironemnt().name();
        if (envName.equalsIgnoreCase("fox7")) {
            rabbitMqName = "d7erprabbitmq.myntra.com";    
        }
        else if(envName.equalsIgnoreCase("M7")){
            rabbitMqName = "d7erprabbitmq.myntra.com";    
        }
        
        else if(envName.equalsIgnoreCase("stage")){
            rabbitMqName = "d7erprabbitmq.myntra.com";    
        }
        
        else{
        	 rabbitMqName = "sps-erprabbitmq.dockins.myntra.com";  
        }
    }


	
	public void loginAndIntializeDate() throws IOException,
			InterruptedException {
		// Initialize from and to date to -1 and +1
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		from = dateFormat.format(cal.getTime());

		cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		to = dateFormat.format(cal.getTime());
		login = logIn();
	}

	public String randomGenn(int length) {
		Random random = new Random();
		int n = random.nextInt(10);
		for (int i = 0; i < length - 1; i++) {
			n = n * 10;
			n += random.nextInt(10);
		}
		String ar = Integer.toString(Math.abs(n));
		return ar;
	}

	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String D = dateFormat.format(cal.getTime());
		return D;
	}

	public String logIn() throws InterruptedException {
		String payL = "access_key=myntra&secret_key=myntra_password";
		MyntraService service = Myntra.getService(ServiceType.ERP_CITRUS,
				APINAME.LOGIN, init.Configurations, new String[] { payL },
				PayloadType.URLENCODED, PayloadType.JSON);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		log.debug("Citrus API response: "+req.response);
		Assert.assertEquals( req.response.getStatus(),200,"Citrus API Timed out");

		login = req.respvalidate.NodeValue("auth_token", true)
				.replaceAll("\"", "").trim();
		System.out.println(login);
		return login;
	}
	
	public String logInCitrusProd() throws InterruptedException, Exception {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("access_key", "QHRQQ34PXQW7ERM6AHUU"));
		urlParameters.add(new BasicNameValuePair("secret_key", "3b4ca677047449b23196e9aef2a96103a16c5843"));
		
		Svc service = HttpExecutorService.executeHttpServiceForUrlEncode(Constants.SELLERAPI_PATH.AUTH_PROD, null,
				SERVICE_TYPE.CITRUS_SVC.toString(), HTTPMethods.POST, urlParameters,getCitrusAPIHeader() );
		
		Assert.assertEquals(service.getResponseStatus(), 200);
		//String login ="";
		
		JSONObject jsonObject = new JSONObject(service.getResponseBody());
		Object login=jsonObject.get("auth_token");
		return login.toString();
	}
	private HashMap<String, String> getCitrusAPIHeader() {
		HashMap<String, String> createArtieServiceHeaders = new HashMap<String, String>();
		createArtieServiceHeaders.put("accept", "application/json");
		return createArtieServiceHeaders;
	}

	/**
	 * API Name - search transactions in SPS DB and Citrus and get the number of
	 * splits
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 **/
	public int getNoOfSplits(String orderId) throws InterruptedException,
			IOException {
		loginAndIntializeDate();
		System.out.println("Wait for 10 sec....");
		Thread.sleep(10000);
		MyntraService service = Myntra.getService(ServiceType.ERP_CITRUS,
				APINAME.SEARCHTRANSACTIONS, init.Configurations, new String[] {
						from, to }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("auth_token", login);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		log.debug("Citrus API response: "+res);
		Assert.assertEquals( req.response.getStatus(),200,"Citrus API Timed out");
		System.out.println("From Date: " + from + " To: " + to);
		List<String> merchantIds = JsonPath.read(res, "$..merchant_trans_id");
		System.out.println(merchantIds);
		int index = merchantIds.indexOf(orderId);
		System.out.println("Index of your order is: " + index);
		String orderRes = "";
		if (index > -1) {
			orderRes = JsonPath.read(res, "$.[" + index + "]").toString();
			System.out.println(orderRes);
		} else{
			System.out.println("Order doesn't Exist ");
			Assert.fail("Add Order :"+orderId+" doesn't Exist in Citrus");
		}
		
		List<String> splits = JsonPath.read(orderRes, "$..split_id");
		System.out.println(splits);
		return splits.size();
	}

	/**
	 * API Name - search transactions in SPS DB and Citrus and get the number of
	 * Refund
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 **/
	public int getNoOfRefund(String orderId) throws InterruptedException,
			IOException {
		loginAndIntializeDate();
		System.out.println("Wait for 10 sec....");
		Thread.sleep(10000);
		MyntraService service = Myntra.getService(ServiceType.ERP_CITRUS,
				APINAME.SEARCHTRANSACTIONS, init.Configurations, new String[] {
						from, to }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("auth_token", login);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		log.debug("Citrus API response: "+res);
		Assert.assertEquals( req.response.getStatus(),200,"Citrus API Timed out");
		List<String> merchantIds = JsonPath.read(res, "$..merchant_trans_id");
		System.out.println(merchantIds);
		int index = merchantIds.indexOf(orderId);
		System.out.println("Index of your order is: " + index);
		String orderRes = "";
		if (index > -1) {
			orderRes = JsonPath.read(res, "$.[" + index + "]").toString();
			System.out.println(orderRes);
		} else{
			System.out.println("Order doesn't Exist ");
			Assert.fail("Refund Order :"+orderId+" doesn't Exist in Citrus");
		}
		List<String> refund = JsonPath.read(orderRes, "$..refund_id");
		System.out.println(refund);
		return refund.size();
	}

	/**
	 * API Name - search transactions in SPS DB and Citrus and get the number of
	 * Release
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 **/
	public int getNoOfRelease(String orderId) throws InterruptedException,
			IOException {
		loginAndIntializeDate();
		System.out.println("Wait for 10 sec....");
		Thread.sleep(10000);
		MyntraService service = Myntra.getService(ServiceType.ERP_CITRUS,
				APINAME.SEARCHTRANSACTIONS, init.Configurations, new String[] {
						from, to }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("auth_token", login);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		log.debug("Citrus API response: "+res);
		Assert.assertEquals( req.response.getStatus(),200,"Citrus API Timed out");
		
		List<String> merchantIds = JsonPath.read(res, "$..merchant_trans_id");
		System.out.println(merchantIds);
		int index = merchantIds.indexOf(orderId);
		System.out.println("Index of your order is: " + index);
		String orderRes = "";
		if (index > -1) {
			orderRes = JsonPath.read(res, "$.[" + index + "]").toString();
			System.out.println(orderRes);
		} else{
			System.out.println("Order doesn't Exist ");
			Assert.fail("Release Order :"+orderId+" doesn't Exist in Citrus");
		}
		List<String> release = JsonPath.read(orderRes, "$..release_id");
		System.out.println(release);
		return release.size();
	}

	/**
	 * API Name - search transactions in SPS DB and Citrus and get the number of
	 * splits
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 **/
	public List getNoOfSplitsAndFeeAmount(String orderId)
			throws InterruptedException, IOException {
		loginAndIntializeDate();
		List rData = new ArrayList();
		System.out.println("Wait for 10 sec....");
		Thread.sleep(10000);
		MyntraService service = Myntra.getService(ServiceType.ERP_CITRUS,
				APINAME.SEARCHTRANSACTIONS, init.Configurations, new String[] {
						from, to }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("auth_token", login);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		log.debug("Citrus API response: "+res);
		Assert.assertEquals( req.response.getStatus(),200,"Citrus API Timed out");
		System.out.println("From Date: " + from + " To: " + to);
		List<String> merchantIds = JsonPath.read(res, "$..merchant_trans_id");
		System.out.println(merchantIds);
		int index = merchantIds.indexOf(orderId);
		System.out.println("Index of your order is: " + index);
		String orderRes = "";
		if (index > -1) {
			orderRes = JsonPath.read(res, "$.[" + index + "]").toString();
			System.out.println(orderRes);
		} else{
			System.out.println("Order doesn't Exist ");
			Assert.fail("Release Order :"+orderId+" doesn't Exist in Citrus");
		}
		List<String> splits = JsonPath.read(orderRes, "$..split_id");
		System.out.println(splits);
		rData.add(Integer.toString(splits.size()));
		List feeAmount = JsonPath.read(orderRes, "$..fee_amount");
		rData.addAll(feeAmount);
		return rData;
	}
	
	/**
	 * API Name - search transactions in SPS DB and Citrus and get the number of
	 * splits
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 **/
	public List getNoOfSplitsAndFeeAmountAndTxAmount(String orderId)
			throws InterruptedException, IOException {
		loginAndIntializeDate();
		List rData = new ArrayList();
		System.out.println("Wait for 10 sec....");
		Thread.sleep(10000);
		MyntraService service = Myntra.getService(ServiceType.ERP_CITRUS,
				APINAME.SEARCHTRANSACTIONS, init.Configurations, new String[] {
						from, to }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("auth_token", login);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		log.debug("Citrus API response: "+res);
		Assert.assertEquals( req.response.getStatus(),200,"Citrus API Timed out");
		System.out.println("From Date: " + from + " To: " + to);
		List<String> merchantIds = JsonPath.read(res, "$..merchant_trans_id");
		System.out.println(merchantIds);
		int index = merchantIds.indexOf(orderId);
		System.out.println("Index of your order is: " + index);
		String orderRes = "";
		if (index > -1) {
			orderRes = JsonPath.read(res, "$.[" + index + "]").toString();
			System.out.println(orderRes);
		} else{
			System.out.println("Order doesn't Exist ");
			Assert.fail("Order :"+orderId+" doesn't Exist in Citrus");
		}
		List<String> splits = JsonPath.read(orderRes, "$..split_id");
		System.out.println(splits);
		rData.add(Integer.toString(splits.size()));
		List txAmount = JsonPath.read(orderRes, "$..trans_amount");
		rData.add(txAmount.get(0));
		List feeAmount = JsonPath.read(orderRes, "$..fee_amount");
		rData.addAll(feeAmount);		
		return rData;
	}
	
	
	/**
	 * API Name - search transactions in SPS DB and Citrus and get the number of
	 * splits
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 **/
	public List getNoOfSplitsAndSplitAmountAndTxAmount(String orderId)
			throws InterruptedException, IOException {
		loginAndIntializeDate();
		List rData = new ArrayList();
		System.out.println("Wait for 10 sec....");
		Thread.sleep(10000);
		MyntraService service = Myntra.getService(ServiceType.ERP_CITRUS,
				APINAME.SEARCHTRANSACTIONS, init.Configurations, new String[] {
						from, to }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("auth_token", login);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		log.debug("Citrus API response: "+res);
		Assert.assertEquals( req.response.getStatus(),200,"Citrus API Timed out");
		System.out.println("From Date: " + from + " To: " + to);
		List<String> merchantIds = JsonPath.read(res, "$..merchant_trans_id");
		System.out.println(merchantIds);
		int index = merchantIds.indexOf(orderId);
		System.out.println("Index of your order is: " + index);
		String orderRes = "";
		if (index > -1) {
			orderRes = JsonPath.read(res, "$.[" + index + "]").toString();
			System.out.println(orderRes);
		} else{
			System.out.println("Order doesn't Exist ");
			Assert.fail("Order :"+orderId+" doesn't Exist in Citrus");
		}
		List<String> splits = JsonPath.read(orderRes, "$..split_id");
		System.out.println(splits);
		rData.add(Integer.toString(splits.size()));
		List txAmount = JsonPath.read(orderRes, "$..trans_amount");
		rData.add(txAmount.get(0));
		List splitAmount = JsonPath.read(orderRes, "$..split_amount");
		rData.addAll(splitAmount);		
		return rData;
	}

	// Push message to rabbit MQ into queue

	public String rabbitMqAddTx(String message, String orderId) {
		End2EndHelper end2EndHelper = new End2EndHelper();
		Message mess = new Message(message.getBytes(), getMessageProperties());
		RabbitTemplate rt = end2EndHelper.getRabbitMqConnection(rabbitMqName,
				"Q", "oms_rel_status_change");
		rt.send(mess);
		try{
		syncAddTransaction(orderId);
		}
		catch(Exception e){
			log.debug("Add Transaction Sync failed");	
	    }
		return "Message Inserted to Add Tx queue successful";
		
	}
	
	public String rabbitMqRefundTx(String message, String ppsIdRefund) {
		End2EndHelper end2EndHelper = new End2EndHelper();
		Message mess = new Message(message.getBytes(), getMessageProperties());
		RabbitTemplate rt = end2EndHelper.getRabbitMqConnection(rabbitMqName,
				"spsRefundQueue", "sps");
		rt.send(mess);
		String retMessage = "Message Inserted to Refund queue successful";
		try{
			syncRefund(ppsIdRefund);
			}
			catch(Exception e){
			log.debug("Refund Order Sync failed");	
			}
		return retMessage;
	}
	
	public String rabbitMqReleaseCTx(String message, String orderId) {
		End2EndHelper end2EndHelper = new End2EndHelper();
		Message mess = new Message(message.getBytes(), getMessageProperties());
		RabbitTemplate rt = end2EndHelper.getRabbitMqConnection(rabbitMqName,
				"C", "oms_rel_status_change");
		rt.send(mess);
		String retMessage = "Message Inserted to release queue for 'C' status successful";
		try{
			syncReleaseFund(orderId);
			}
			catch(Exception e){
			log.debug("Release Order Sync failed");	
			}
		return retMessage;
	}

	public String rabbitMqReleasePKTx(String message,String orderId){
		End2EndHelper end2EndHelper = new End2EndHelper();
		Message mess = new Message(message.getBytes(), getMessageProperties());
		RabbitTemplate rt = end2EndHelper.getRabbitMqConnection(rabbitMqName,
				"PK", "oms_rel_status_change");
		rt.send(mess);
		String retMessage = "Message Inserted to release queue for 'PK' status successful";
		try{
		syncReleaseFund(orderId);
		}
		catch(Exception e){
		log.debug("Release Order Sync failed");	
		}
		return retMessage;
	}
	
	
	public String rabbitMqReleaseDLTx(String message, String orderId) {
		End2EndHelper end2EndHelper = new End2EndHelper();
		Message mess = new Message(message.getBytes(), getMessageProperties());
		RabbitTemplate rt = end2EndHelper.getRabbitMqConnection(rabbitMqName,
				"DL", "oms_rel_status_change");
		rt.send(mess);
		String retMessage = "Message Inserted to release queue for 'DL' status successful";
		try{
			syncReleaseFund(orderId);
			}
			catch(Exception e){
			log.debug("Release Order Sync failed");	
			}
		return retMessage;
	}

	private static MessageProperties getMessageProperties() {
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setHeader("Authorization",
				"Basic YXBpYWRtaW5+c3lzdGVtOm0xbnRyYVIwY2tldDEzISM=");
		messageProperties.setContentType("text/plain");
		messageProperties.setContentEncoding("UTF-8");
		return messageProperties;
	}
	
//	public String getPincode(String pathParam)
//            throws  IOException, JAXBException {
//		LmsServiceHelper lmsHelper=new LmsServiceHelper();
//        Svc service = HttpExecutorService.executeHttpService(LMS_PATH.GET_ADDRESS_BY_PINCODE, new String[]{"pincode/"+pathParam},
//                SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.GET, null, lmsHelper.getLmsHeaderXML());
//        PincodeResponse response = (PincodeResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
//                new PincodeResponse());
//        return response.getPincodes().get(0).getStateCode();
//       
//    }
	
	
	public TaxResponse getTaxMasterServiceGST_Tax(String sourceStateCode,String destinationStateCode, String sac_code) throws Exception {
		 	String date=getDate()+"T00:00:00+05:30";
	        String payload = "{\"sourceStateCode\" : \""+sourceStateCode+"\", \"destinationStateCode\":\""+destinationStateCode+"\",\"sacCode\":"+sac_code+", \"date\":\""+date+"\"}";
	        Svc service = HttpExecutorService.executeHttpService(Constants.TAXMASTER_PATH.GET_SERVICE_GST_TAX, null, SERVICE_TYPE.TAXMASTER_SVC.toString(), HTTPMethods.POST, payload, getTaxMasterHeaderXML());
	        TaxResponse response = (TaxResponse) APIUtilities.getJsontoObject(service.getResponseBody(),new TaxResponse());
	        return response;
		
	}
	
	public HashMap<String, String> getTaxMasterHeaderXML(){
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
        createOrderHeaders.put("Content-Type", "application/json");
        createOrderHeaders.put("Accept", "application/json");
        return createOrderHeaders;
    }
	
	
	public HashMap<String, String> getSPSHeaders(){
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
        createOrderHeaders.put("Content-Type", "application/json");
        createOrderHeaders.put("Accept", "application/json");
        return createOrderHeaders;
    }
	
	public void syncAddTransaction(String orderId) throws Exception {

		String payload = "["+orderId+"]";
		Thread.sleep(5000);
		Svc service=HttpExecutorService.executeHttpService(Constants.SPS_PATH.SYNC_ADD_TRANSACTION, null, SERVICE_TYPE.SPS_SVC.toString(), HTTPMethods.POST, payload, getSPSHeaders());
		Assert.assertEquals(service.getResponseStatus(), 200,"Response StatusCode : ");
		Assert.assertEquals(service.getResponseStatusType("statusMessage"), "Sync completed","Response Status Message : ");
	}
	
	public void syncReleaseFund(String orderId) throws Exception {
		String payload = "["+orderId+"]";
		Thread.sleep(5000);
		Svc service=HttpExecutorService.executeHttpService(Constants.SPS_PATH.SYNC_RELEASE_FUND, null, SERVICE_TYPE.SPS_SVC.toString(), HTTPMethods.POST, payload, getSPSHeaders());
		Assert.assertEquals(service.getResponseStatus(), 200,"Response StatusCode : ");
		Assert.assertEquals(service.getResponseStatusType("statusMessage"), "Sync completed","Response Status Message : ");
	}
	
	public void syncRefund(String ppsIdRefund) throws Exception {
		String payload = "[\""+ppsIdRefund+"\"]";
		Thread.sleep(20000);
		Svc service=HttpExecutorService.executeHttpService(Constants.SPS_PATH.SYNC_REFUND, null, SERVICE_TYPE.SPS_SVC.toString(), HTTPMethods.POST, payload, getSPSHeaders());
		Assert.assertEquals(service.getResponseStatus(), 200,"Response StatusCode : ");
		Assert.assertEquals(service.getResponseStatusType("statusMessage"), "Sync completed","Response Status Message : ");
	}

	// --------------------------- DB validations Queries
	// -------------------------------------------------------------

	public String getTxAmount(String orderId){
		Map<String, Object> transaction_detail = DBUtilities.exSelectQueryForSingleRecord("select amount from transaction_detail where order_id = "+orderId, "sps");
		return transaction_detail.get("amount").toString();
	}
	public int getSplitTxDBCount(String orderId) throws SQLException {
		String transectionDetailQuery = "select count(*) from transaction_detail where order_id = "
				+ orderId + "";
		List TxQ = DBUtilities.exSelectQuery(transectionDetailQuery, "sps");
		HashMap<String, Long> txResult = (HashMap<String, Long>) TxQ.get(0);
		Long lp = txResult.get("count(*)");
		return lp.intValue();
	}

	public int getSplitPgDBCount(String orderId) throws SQLException {
		String pgsettelmentQuery = "select count(*) from pg_settlement_details where order_id = "
				+ orderId + "";
		List pgQ = DBUtilities.exSelectQuery(pgsettelmentQuery, "sps");
		HashMap<String, Long> pgResult = (HashMap<String, Long>) pgQ.get(0);
		Long pglp = pgResult.get("count(*)");
		return pglp.intValue();
	}

	public int getTxDBStatusCount1(String orderId,String skuId,String status) throws SQLException {
		String transectionDetailQuery = "Select count(*) from transaction_detail where order_id ="
				+ orderId + " and sku_id =" + skuId + "  and status= '"+ status +"'";
		List TxQ = DBUtilities.exSelectQuery(transectionDetailQuery, "sps");
		HashMap<String, Long> txResult = (HashMap<String, Long>) TxQ.get(0);
		Long lp = txResult.get("count(*)");
		return lp.intValue();
	}
	
	public BigDecimal getTxDBAmount(String orderId, String skuId) throws SQLException {
		String transectionDetailQuery = "Select amount from transaction_detail where order_id ="
				+ orderId + " and sku_id = "+skuId+"";
		List TxQ = DBUtilities.exSelectQuery(transectionDetailQuery, "sps");
		HashMap<String, BigDecimal> txResult = (HashMap<String, BigDecimal>) TxQ.get(0);
		BigDecimal lp = txResult.get("amount");
		return lp;
	}

	public int getTxDBStatus(String orderId, String skuId,String status)
			throws SQLException {
		String transectionDetailQuery = "Select count(*) from transaction_detail where order_id ="
				+ orderId + " and sku_id =" + skuId + " and status= '"+status+"'";
		List TxQ = DBUtilities.exSelectQuery(transectionDetailQuery, "sps");
		HashMap<String, Long> txResult = (HashMap<String, Long>) TxQ.get(0);
		Long lp = txResult.get("count(*)");
		return lp.intValue();
	}
	
	public int getTxDBStatus1(String orderId,String status)
			throws SQLException {
		String transectionDetailQuery = "Select count(*) from transaction_detail where order_id ="
				+ orderId + " and status= '"+status+"'";
		List TxQ = DBUtilities.exSelectQuery(transectionDetailQuery, "sps");
		HashMap<String, Long> txResult = (HashMap<String, Long>) TxQ.get(0);
		Long lp = txResult.get("count(*)");
		return lp.intValue();
	}
	
	public String getPgDBStatus(String orderId)
			throws SQLException {
		String PgDBStatusQuery = "Select status from pg_settlement_details where order_id ="
				+ orderId + " ";
		List TxQ = DBUtilities.exSelectQuery(PgDBStatusQuery, "sps");
		HashMap<String, String> txResult = (HashMap<String, String>) TxQ.get(0);
		String lp = txResult.get("status");
		return lp;
	}

	public BigDecimal getSellerMargin(String orderId) throws SQLException {
		String transectionDetailQuery = "Select margin from transaction_detail where order_id ="
				+ orderId + "" ;
		List TxQ = DBUtilities.exSelectQuery(transectionDetailQuery, "sps");
		HashMap<String, BigDecimal> pgResult = (HashMap<String, BigDecimal>) TxQ.get(0);
		BigDecimal lp = pgResult.get("margin");
		return lp;
	}

	public int getTxDBStatusCount(String orderId, String status) throws SQLException {
		String pgsettelmentQuery = "select count(*) from transaction_detail where order_id = "
				+ orderId + " and status = '"+status+"'";
		List pgQ = DBUtilities.exSelectQuery(pgsettelmentQuery, "sps");
		HashMap<String, Long> pgResult = (HashMap<String, Long>) pgQ.get(0);
		Long pglp = pgResult.get("count(*)");
		return pglp.intValue();
	}
	
	public Integer getLineId(String orderId) throws SQLException {
		HashMap<String, Object> orderLine = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select id from order_line where order_id_fk = "+orderId, "oms");
		String ld = orderLine.get("id").toString();
		return Integer.parseInt(ld);
			
	}
	
	public List getOrderIdWithSellerId1(String sellerId,int hours) throws SQLException {
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fromDate = sdf.format(date);
		
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -1);
		Date todate1=cal.getTime();
		String toDate=sdf.format(todate1);
		
		
		String transectionDetailQuery = "Select order_id from `transaction_detail` where seller_id= "
				+ sellerId + " and last_modified_on between '"+toDate+"' and '"+fromDate+"'";
		List TxQ = DBUtilities.exSelectQuery(transectionDetailQuery, "sps");
		System.out.println(TxQ);
		
//		HashMap<String, Long> txResult = (HashMap<String, Long>) TxQ;
//		for(Map.Entry<String, Long> map:txResult.entrySet()){
//			System.out.println(map.getKey()+":"+map.getValue());
//		//Long lp = txResult.get("order_id");
//		}
		return TxQ;
	}
	
	
	public String getTxFundReleaseAmount(String orderId){
		Map<String, Object> transaction_detail = DBUtilities.exSelectQueryForSingleRecord("select fund_release_amount from settlement_detail where order_id = "+orderId, "sps");
		return transaction_detail.get("fund_release_amount").toString();
	}
	
	public String getTxSettlementAmount(String orderId){
		Map<String, Object> transaction_detail = DBUtilities.exSelectQueryForSingleRecord("select settlement_amount from settlement_detail where order_id = "+orderId, "sps");
		return transaction_detail.get("settlement_amount").toString();
	}
	
	
			
	public BigDecimal getSellerMarginPercentage(String sellerId) throws SQLException {
		String transectionDetailQuery = "select `margin_percentage` from `payment_configuration` where `seller_id`="+sellerId+" and `enabled`=1" ;
		List TxQ = DBUtilities.exSelectQuery(transectionDetailQuery, "seller1");
		HashMap<String, BigDecimal> pgResult = (HashMap<String, BigDecimal>) TxQ.get(0);
		BigDecimal lp = pgResult.get("margin_percentage");
		return lp;
	}
	
	public int getSettlementdetailDBStatusCount(String orderId, String status) throws SQLException {
		String pgsettelmentQuery = "select count(*) from settlement_detail where order_id = "
				+ orderId + " and settlement_status = '"+status+"'";
		List pgQ = DBUtilities.exSelectQuery(pgsettelmentQuery, "sps");
		HashMap<String, Long> pgResult = (HashMap<String, Long>) pgQ.get(0);
		Long pglp = pgResult.get("count(*)");
		return pglp.intValue();
	}

	public String getSettlementdetailDBStatus(String orderId,String status) throws SQLException {
		try {
		Map<String, Object> spsStatus =  DBUtilities.exSelectQueryForSingleRecord("select settlement_status from settlement_detail where order_id = "+orderId+" and settlement_status='"+status+"' order by last_modified_on DESC", "sps");
			if (spsStatus == null){
				return "false";
			}
			return spsStatus.get("settlement_status").toString();
		} catch (Exception e) {
			log.error("Error in getOrderFromSPS :- " + e.getMessage());
			return "false";
		}
	}

	public boolean validateOrderStatusInSPS(String orderID, String status, int delaytoCheck) {
		log.info("Validate Order Status in SPS settlement_detail table");
		boolean validateStatus = false;
		try {
			for (int i = 0; i < delaytoCheck; i++) {
				String status_code = getSettlementdetailDBStatus(orderID,status);
				if (status_code.equalsIgnoreCase(status) || status_code.equalsIgnoreCase(status)) {
					validateStatus = true;
					break;
				} else {
					Thread.sleep(2000);
					validateStatus = false;
				}
				log.info("waiting for Order Status in SPS" + status + " .current status=" + status_code + "\t " + i);
			}
		} catch(Exception e) {
			e.printStackTrace();
			validateStatus=false;
		}
		return validateStatus;
	}

	public String getSettlementDetailFundReleaseAmount(String orderId){
		List settlement_detail = DBUtilities.exSelectQuery("select fund_release_amount from settlement_detail where order_id = "+orderId, "sps");
		HashMap<String, BigDecimal> pgResult = (HashMap<String, BigDecimal>) settlement_detail.get(0);
		return pgResult.get("fund_release_amount").toString();
		
	}
	
	public String getSettlementDetailRefundAmount(String orderId,String paymentMethod){
		List settlement_detail = DBUtilities.exSelectQuery("select refund_amount from settlement_detail where order_id = "+orderId +" and payment_method='"+paymentMethod+"' and "
				+ "settlement_status='REFUNDED'", "sps");
		HashMap<String, BigDecimal> pgResult = (HashMap<String, BigDecimal>) settlement_detail.get(0);
		return pgResult.get("refund_amount").toString();
		
	}
	
	public String getSettlementDetailFundReleaseAmount(String orderId,String settlementStatus){
		List settlement_detail = DBUtilities.exSelectQuery("select fund_release_amount from settlement_detail where order_id = "+orderId+" and settlement_status='"+settlementStatus+"'", "sps");
		HashMap<String, BigDecimal> pgResult = (HashMap<String, BigDecimal>) settlement_detail.get(0);
		return pgResult.get("fund_release_amount").toString();
		
	}
	public String getSettlementDetailFundReleaseAmountByItemRef(String orderId,String settlementStatus){
		List settlement_detail = DBUtilities.exSelectQuery("select fund_release_amount from settlement_detail where item_reference = '"+orderId+"' and settlement_status='"+settlementStatus+"'", "sps");
		HashMap<String, BigDecimal> pgResult = (HashMap<String, BigDecimal>) settlement_detail.get(0);
		return pgResult.get("fund_release_amount").toString();
		
	}
	
	public String getSettlementDetailRefundAmountByItemRef(String orderId,String paymentMethod){
		List settlement_detail = DBUtilities.exSelectQuery("select refund_amount from settlement_detail where item_reference = '"+orderId +"' and payment_method='"+paymentMethod+"' and "
				+ "settlement_status='REFUNDED'", "sps");
		HashMap<String, BigDecimal> pgResult = (HashMap<String, BigDecimal>) settlement_detail.get(0);
		return pgResult.get("refund_amount").toString();
		
	}
	public String getSettlementDetailRefundAmountBySku(String orderId,String sku_id){
		List settlement_detail = DBUtilities.exSelectQuery("select refund_amount from settlement_detail where order_id = '"+orderId +"' and sku_id='"+sku_id+"' and "
				+ "settlement_status='REFUNDED'", "sps");
		HashMap<String, BigDecimal> pgResult = (HashMap<String, BigDecimal>) settlement_detail.get(0);
		return pgResult.get("refund_amount").toString();
		
	}
	
	public String getSPSBalanceAmount() {
		Map<String, Object> transaction_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select sum(amount) from transaction_detail where `status` NOT in ('RELEASED', 'REFUNDED') and order_id in (select distinct order_id from transaction_detail where `status` = 'PGSETTLE')",
				"sps");
		String val = transaction_detail.get("sum(amount)").toString();

		return val;
	}
	
	public String getSellerTermsMarginStatus(String sellerId) {
		Map<String, Object> seller_config = DBUtilities.exSelectQueryForSingleRecord(
				"select configuration_value from `seller_configuration` where seller_id="+sellerId+" and enabled=1 and configuration_key='USE_TERMS_MARGIN'",
				"myntra_seller1");
		String val = seller_config.get("configuration_value").toString();
		return val;
	}
	
	//;
	public String getTransactionDetailMargin(String orderId) {
		Map<String, Object> transaction_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select margin from transaction_detail where order_id="+orderId,
				"sps");
		String val = transaction_detail.get("margin").toString();
		return val;
	}
	
	public String getSettlementDetailMargin(String orderId) {
		Map<String, Object> settlement_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select commission_percentage from settlement_detail where order_id="+orderId,
				"sps");
		String val = settlement_detail.get("commission_percentage").toString();
		return val;
	}
	
	public BigDecimal getSettlementDetailCommissionAmount(String orderReleaseId) {
		Map<String, Object> settlement_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select commission_amount from settlement_detail where order_release_id="+orderReleaseId,
				"sps");	
		return (BigDecimal) settlement_detail.get("commission_amount");
	}
	
	public BigDecimal getSettlementDetailCommissionAmount(String orderReleaseId, String status) {
		Map<String, Object> settlement_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select commission_amount from settlement_detail where order_release_id="+orderReleaseId+" and settlement_status='"+status+"'",
				"sps");	
		return (BigDecimal) settlement_detail.get("commission_amount");
	}
	
	public BigDecimal getSettlementDetailCgstTaxRate(String orderReleaseId) {
		Map<String, Object> settlement_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select cgst_tax_rate from settlement_detail where order_release_id="+orderReleaseId,
				"sps");	
		return (BigDecimal) settlement_detail.get("cgst_tax_rate");
	}
	
	public BigDecimal getSettlementDetailCgstTaxRate(String orderReleaseId, String status) {
		Map<String, Object> settlement_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select cgst_tax_rate from settlement_detail where order_release_id="+orderReleaseId+" and settlement_status='"+status+"'",
				"sps");	
		return (BigDecimal) settlement_detail.get("cgst_tax_rate");
	}
	
	public BigDecimal getSettlementDetailSgstTaxRate(String orderReleaseId) {
		Map<String, Object> settlement_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select sgst_tax_rate from settlement_detail where order_release_id="+orderReleaseId,
				"sps");	
		return (BigDecimal) settlement_detail.get("sgst_tax_rate");
	}
	
	public BigDecimal getSettlementDetailSgstTaxRate(String orderReleaseId, String status) {
		Map<String, Object> settlement_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select sgst_tax_rate from settlement_detail where order_release_id="+orderReleaseId+" and settlement_status='"+status+"'",
				"sps");	
		return (BigDecimal) settlement_detail.get("sgst_tax_rate");
	}
	
	public BigDecimal getSettlementDetailIgstTaxRate(String orderReleaseId) {
		Map<String, Object> settlement_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select igst_tax_rate from settlement_detail where order_release_id="+orderReleaseId,
				"sps");	
		return (BigDecimal )settlement_detail.get("igst_tax_rate");
	}
	
	public BigDecimal getSettlementDetailCgstTaxAmount(String orderReleaseId) {
		Map<String, Object> settlement_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select cgst_tax from settlement_detail where order_release_id="+orderReleaseId,
				"sps");	
		return (BigDecimal )settlement_detail.get("cgst_tax");
	}
	
	public BigDecimal getSettlementDetailCgstTaxAmount(String orderReleaseId, String status) {
		Map<String, Object> settlement_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select cgst_tax from settlement_detail where order_release_id="+orderReleaseId+" and settlement_status='"+status+"'",
				"sps");	
		return (BigDecimal )settlement_detail.get("cgst_tax");
	}
	
	public BigDecimal getSettlementDetailSgstTaxAmount(String orderReleaseId) {
		Map<String, Object> settlement_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select sgst_tax from settlement_detail where order_release_id="+orderReleaseId,
				"sps");	
		return (BigDecimal) settlement_detail.get("sgst_tax");
	}
	
	public BigDecimal getSettlementDetailSgstTaxAmount(String orderReleaseId, String status) {
		Map<String, Object> settlement_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select sgst_tax from settlement_detail where order_release_id="+orderReleaseId+" and settlement_status='"+status+"'",
				"sps");	
		return (BigDecimal) settlement_detail.get("sgst_tax");
	}
	
	public BigDecimal getSettlementDetailIgstTaxAmount(String orderReleaseId) {
		Map<String, Object> settlement_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select igst_tax from settlement_detail where order_release_id="+orderReleaseId,
				"sps");	
		return (BigDecimal)settlement_detail.get("igst_tax");
	}
	
	public BigDecimal getSettlementDetailIgstTaxAmount(String orderReleaseId, String status) {
		Map<String, Object> settlement_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select igst_tax from settlement_detail where order_release_id="+orderReleaseId+" and settlement_status='"+status+"'",
				"sps");	
		return (BigDecimal)settlement_detail.get("igst_tax");
	}
	
	public String getSettlementDetailRefundReason(String orderReleaseId) {
		Map<String, Object> settlement_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select refund_reason from settlement_detail where order_release_id="+orderReleaseId,
				"sps");	
		return settlement_detail.get("refund_reason").toString();
	}
	
	public String getSellerBillingPinCode(String seller_id) {
		Map<String, Object> settlement_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select postal_code from address where seller_id="+seller_id+" and address_type='BILLING'",
				"seller1");	
		return settlement_detail.get("postal_code").toString();
	}
	
	// ---------------------------------Insert/Update Query
	// -------------------------------------------------------------
	// ------------------Insert Into OMS
	
	
	public void InsertOrder(String orderId, String mrp_total,
			String final_amount, String shippingCharges, String orderType) throws SQLException {
		String orders = "INSERT INTO `orders` (`id`, `invoice_id`, `login`, `user_contact_no`, `customer_name`, `payment_method`, `status_code`, `coupon_code`, "
				+ "`cash_coupon_code`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, "
				+ "`cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `request_server`, `response_server`, `is_on_hold`, `is_gift`, `notes`, "
				+ "`billing_address_id_fk`, `cancellation_reason_id_fk`, `on_hold_reason_id_fk`,`cancelled_on`, `created_by`,"
				+ " `version`, `order_type`, `loyalty_points_used`, `store_id`, `store_order_id`) VALUES("+ orderId+ ", NULL, 'spsautomation@myntra.com', '7875265650', "
				+ "'SPS Automation', 'on', NULL, '', '', "+ mrp_total+ ", 0.0, 0.0, 0.0, 0.0, 0.0,"+ final_amount+ " , "+ shippingCharges
				+ ", 0.00, 0.00, 0.0, 0.0, 0.00, NULL, NULL, 0, 0, '',"+ " 29318814, NULL, NULL, NULL, 'spsautomation@myntra.com', 3, '"+orderType+"', 0.0, 1, NULL)";
		DBUtilities.exUpdateQuery(orders, "oms");
	}
	
	/*
	 * OrderId mrp_total final_amount shippingCharges taxAmount discount
	 * CartDiscount CouponDiscount giftCharges loyaltyPoint
	 */
	public void InsertOrder(String orderId,String store_order_id, String mrp_total,
			String final_amount, String shippingCharges, String taxAmount,
			String discount, String cartDiscount, String couponDiscount,
			String giftCharges, String loyaltyPoint, String cash_redeemed) throws SQLException {
		String orders = "INSERT INTO `orders` (`id`,`store_order_id`, `invoice_id`, `login`, `user_contact_no`, `customer_name`, `payment_method`, `status_code`, `coupon_code`, "
				+ "`cash_coupon_code`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, "
				+ "`cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `request_server`, `response_server`, `is_on_hold`, `is_gift`, `notes`, "
				+ "`billing_address_id_fk`, `cancellation_reason_id_fk`, `on_hold_reason_id_fk`,`cancelled_on`, `created_by`,"
				+ " `version`, `order_type`, `loyalty_points_used`, `store_id`) VALUES("+ orderId+ ","+store_order_id+", NULL, 'spsautomation@myntra.com', '7875265650', "
				+ "'SPS Automation', 'on', NULL, '', '', "+ mrp_total+ ","+ discount+ ", "+ cartDiscount+ ", "+ couponDiscount+ ", "+cash_redeemed+", 0.0,"+ final_amount+ " , "+ shippingCharges
				+ ", 0.00, 0.00, "+ giftCharges+ ", "+ taxAmount+ ", 0.00, NULL, NULL, 0, 0, '',"+ " 29318814, NULL, NULL, NULL, 'spsautomation@myntra.com', 3, 'on', "
				+ loyaltyPoint + ", 1)";
		DBUtilities.exUpdateQuery(orders, "oms");
	}

	/*
	 * OrderReleaseId OrderId mrp_total final_amount shippingCharges taxAmount
	 * discount CartDiscount CouponDiscount giftCharges loyaltyPoint wareHouseId
	 */
	public void InsertOrderRelease(String OrderReleaseId, String orderId,
			String store_order_id,String mrp_total, String final_amount, String shippingCharges,
			String taxAmount, String discount, String cartDiscount,
			String couponDiscount, String giftCharges, String loyaltyPoint,
			String wareHouseId, String cash_redeemed) throws SQLException {
		
		String orderRelease = "INSERT INTO `order_release` (`id`, `order_id_fk`,`store_order_id`, `login`, `status_code`, `payment_method`, `mrp_total`, `discount`, `cart_discount`, "
				+ "`coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, `cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, "
				+ "`cashback_offered`, `receiver_name`, `address`, `city`, `locality`, `state`, `country`, `zipcode`, `mobile`, `email`, `courier_code`, `tracking_no`, "
				+ "`warehouse_id`, `is_refunded`, `cod_pay_status`, `cancellation_reason_id_fk`, `packed_on`, `shipped_on`, `delivered_on`, `completed_on`, "
				+ "`cancelled_on`, `created_by`, `version`, `is_on_hold`, `invoice_id`, `cheque_no`, `exchange_release_id`, "
				+ "`user_contact_no`, `shipping_method`, `on_hold_reason_id_fk`, `loyalty_points_used`, `store_id`, `store_release_id`) VALUES ("+ OrderReleaseId+ ","+ orderId
				+ ", "+store_order_id+","+ "'spsautomation@myntra.com', 'WP', 'cod', "+ mrp_total+ ", "+ discount+ ", "+ cartDiscount+ ", "+ couponDiscount+ ", "+cash_redeemed+", 0.00, "+ final_amount+ ", "+ shippingCharges
				+ ","+ " 0.00, 0.00, "+ giftCharges+ ", "+ taxAmount+ ", 0.00, 'SPS Automation ', "
				+ "'Myntra design, AKR tech park, Near kudlu gate', 'Bangalore', 'Bangalore', 'KA', 'India', '560068', '7875265650', 'spsautomation&#x40;myntra.com',"
				+ " 'ML', 'ML0008891114', "+ wareHouseId+ ", 0, 'pending', NULL,NULL, NULL, NULL, NULL, NULL, 'spsautomation@myntra.com',"
				+ " 9, 0, NULL, NULL, NULL, '7875265650', 'NORMAL', NULL, "+ loyaltyPoint + ", 1, NULL)";
		DBUtilities.exUpdateQuery(orderRelease, "oms");
	}

	public void InsertOrderRelease(String OrderReleaseId, String orderId, String store_order_id,
			String mrp_total, String final_amount, String shippingCharges, String wareHouseId, String exchangeReleaseId) throws SQLException {
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String deliveredOn=sdf.format(date);
		String orderRelease = "INSERT INTO `order_release` (`id`, `order_id_fk`, `store_order_id`,`login`, `status_code`, `payment_method`, `mrp_total`, `discount`, `cart_discount`, "
				+ "`coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, `cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, "
				+ "`cashback_offered`, `receiver_name`, `address`, `city`, `locality`, `state`, `country`, `zipcode`, `mobile`, `email`, `courier_code`, `tracking_no`, "
				+ "`warehouse_id`, `is_refunded`, `cod_pay_status`, `cancellation_reason_id_fk`, `packed_on`, `shipped_on`, `delivered_on`, `completed_on`, "
				+ "`cancelled_on`, `created_by`, `version`, `is_on_hold`, `invoice_id`, `cheque_no`, `exchange_release_id`, "
				+ "`user_contact_no`, `shipping_method`, `on_hold_reason_id_fk`, `loyalty_points_used`, `store_id`, `store_release_id`) "
				+ "VALUES ("+ OrderReleaseId+ ","+ orderId
				+ ","+store_order_id+", "+ "'spsautomation@myntra.com', 'WP', 'cod', "+ mrp_total+ ", 0.0, 0.0, 0.0, 0.0, 0.00, "+ final_amount+ ", "+ shippingCharges
				+ ","+ " 0.00, 0.00, 0.0, 0.0, 0.00, 'SPS Automation ', "
				+ "'Myntra design, AKR tech park, Near kudlu gate', 'Bangalore', 'Bangalore', 'KA', 'India', '560068', '7875265650', 'spsautomation&#x40;myntra.com',"
				+ " 'ML', 'ML0008891114', "+ wareHouseId+ ", 0, 'pending',NULL,NULL, NULL, NULL, NULL, NULL, 'spsautomation@myntra.com',"
				+ " 9, 0, NULL, NULL, "+exchangeReleaseId+", '7875265650', 'NORMAL', NULL, 0.0, 1, NULL)";
		DBUtilities.exUpdateQuery(orderRelease, "oms");
	}	
	
	/*
	 * orderIdorderReleaseIdstyleIdoptionIdskuIdunitPricequantityfinalAmount
	 * taxAmounttaxRateSellerIddiscountcartDiscountcouponDiscount
	 * cancellationReasonIdexchangeOrderLineIdloyaltyPoint
	 */
	public void InsertOrderLine(String lineId, String orderId,
			String orderReleaseId, String store_order_id,String styleId, String optionId,
			String skuId, String unitPrice, String quantity,
			String finalAmount, String taxAmount, String taxRate,
			String SellerId, String discount, String cartDiscount,
			String couponDiscount, String cancellationReasonId,
			String exchangeOrderLineId, String loyaltyPoint, String supplyType, String cash_redeemed)
			throws SQLException {
		String orderLine = "INSERT INTO `order_line` (`id`,`order_id_fk`, `order_release_id_fk`, `store_order_id`,`style_id`, `option_id`, `sku_id`, `status_code`, `unit_price`, `quantity`, "
				+ "`discounted_quantity`, `discount`, `cart_discount`, `cash_redeemed`, `coupon_discount`, `pg_discount`, `final_amount`, `tax_amount`, `tax_rate`, "
				+ "`cashback_offered`, `disocunt_rule_id`, `discount_rule_rev_id`, `promotion_id`, `is_discounted`, `is_returnable`, `cancellation_reason_id_fk`, `cancelled_on`, "
				+ "`created_by`, `version`, `exchange_orderline_id`, `loyalty_points_used`, `seller_id`, `supply_type`, `vendor_id`, "
				+ "`store_line_id`, `po_status`) VALUES("
				+ lineId
				+ ","
				+ orderId
				+ ","
				+ orderReleaseId
				+ ", "
				+ store_order_id
				+","
				+ styleId
				+ ", "
				+ optionId
				+ ", "
				+ skuId
				+ ", 'A', "
				+ unitPrice
				+ ", "
				+ quantity
				+ ", 0, "
				+ discount
				+ ", "
				+ ""
				+ cartDiscount
				+ ", "+cash_redeemed+","
				+ couponDiscount
				+ ", 0.00,"
				+ finalAmount
				+ ","
				+ taxAmount
				+ ","
				+ taxRate
				+ ", 0.00, 0, 0, NULL, 0, 1, "
				+ cancellationReasonId
				+ ", NULL, 'spsautomation@myntra.com', 3, "
				+ ""
				+ exchangeOrderLineId
				+ ", "
				+ loyaltyPoint
				+ ", "
				+ SellerId
				+ ", " + "'" + supplyType + "', NULL, NULL, 'UNUSED')";
		DBUtilities.exUpdateQuery(orderLine, "oms");
	}
	
	/**
	 * OrderLine cancellation
	 * @param lineId
	 * @param orderId
	 * @param orderReleaseId
	 * @param styleId
	 * @param optionId
	 * @param skuId
	 * @param unitPrice
	 * @param quantity
	 * @param finalAmount
	 * @param taxAmount
	 * @param taxRate
	 * @param SellerId
	 * @param discount
	 * @param cartDiscount
	 * @param couponDiscount
	 * @param cancellationReasonId
	 * @param exchangeOrderLineId
	 * @param loyaltyPoint
	 * @param supplyType
	 * @param cash_redeemed
	 * @throws SQLException
	 */
	public void InsertOrderLineCancellation(String lineId, String orderId,
			String orderReleaseId, String store_order_id,String styleId, String optionId,
			String skuId, String unitPrice, String quantity,
			String finalAmount, String taxAmount, String taxRate,
			String SellerId, String discount, String cartDiscount,
			String couponDiscount, String cancellationReasonId,
			String exchangeOrderLineId, String loyaltyPoint, String supplyType, String cash_redeemed)
			throws SQLException {
		String orderLine = "INSERT INTO `order_line` (`id`,`order_id_fk`, `order_release_id_fk`, `store_order_id`,`style_id`, `option_id`, `sku_id`, `status_code`, `unit_price`, `quantity`, "
				+ "`discounted_quantity`, `discount`, `cart_discount`, `cash_redeemed`, `coupon_discount`, `pg_discount`, `final_amount`, `tax_amount`, `tax_rate`, "
				+ "`cashback_offered`, `disocunt_rule_id`, `discount_rule_rev_id`, `promotion_id`, `is_discounted`, `is_returnable`, `cancellation_reason_id_fk`, `cancelled_on`, "
				+ "`created_by`, `version`, `exchange_orderline_id`, `loyalty_points_used`, `seller_id`, `supply_type`, `vendor_id`, "
				+ "`store_line_id`, `po_status`) VALUES("
				+ lineId
				+ ","
				+ orderId
				+ ","
				+ orderReleaseId
				+ ", "
				+ store_order_id
				+","
				+ styleId
				+ ", "
				+ optionId
				+ ", "
				+ skuId
				+ ", 'IC', "
				+ unitPrice
				+ ", "
				+ quantity
				+ ", 0, "
				+ discount
				+ ", "
				+ ""
				+ cartDiscount
				+ ", "+cash_redeemed+","
				+ couponDiscount
				+ ", 0.00,"
				+ finalAmount
				+ ","
				+ taxAmount
				+ ","
				+ taxRate
				+ ", 0.00, 0, 0, NULL, 0, 1, "
				+ cancellationReasonId
				+ ", NULL, 'spsautomation@myntra.com', 3, "
				+ ""
				+ exchangeOrderLineId
				+ ", "
				+ loyaltyPoint
				+ ", "
				+ SellerId
				+ ", " + "'" + supplyType + "', NULL, NULL, 'UNUSED')";
		DBUtilities.exUpdateQuery(orderLine, "oms");
	}

	/*
	 * orderId ppsId
	 */
	public void InsertOrderAdditionalInfo(String orderId, String ppsId)
			throws SQLException {
		String orderAdditionalInfo = "INSERT INTO `order_additional_info` (`order_id_fk`, `key`, `value`, `created_by`, `version`) VALUES ("
				+ orderId
				+ ", "
				+ "'ORDER_PROCESSING_FLOW', 'OMS', 'pps-admin', 0), ("
				+ orderId
				+ ", 'CHANNEL', 'web', 'pps-admin',  0),("
				+ orderId
				+ ", 'LOYALTY_CONVERSION_FACTOR', '0.0', "
				+ "'pps-admin',  0),("
				+ orderId
				+ ", 'GIFT_CARD_AMOUNT', '0.0', 'pps-admin',  0),("
				+ orderId
				+ ", 'PAYMENT_PPS_ID', '"
				+ ppsId
				+ "', "
				+ "'pps-admin',  0),("
				+ orderId
				+ ", 'STORED_CREDIT_USAGE', '0.0', 'pps-admin', 0),("
				+ orderId
				+ ", 'EARNED_CREDIT_USAGE', '0.0', 'pps-admin', 0)";
		DBUtilities.exUpdateQuery(orderAdditionalInfo, "oms");
	}

	/*
	 * On cancellation Insert only one row into order additional info (Update on
	 * cancellation) orderId ppsIdCancellation key
	 */
	public void InsertOrderAdditionalInfo(String orderId,
			String ppsIdCancellation, String key) throws SQLException {
		String updateOrderAdditionalInfo = "INSERT INTO `order_additional_info` (`order_id_fk`, `key`, `value`, `created_by`,`version`) "
				+ "VALUES("
				+ orderId
				+ ", '"
				+ key
				+ "', '"
				+ ppsIdCancellation + "', 'mobile', 0)";
		DBUtilities.exUpdateQuery(updateOrderAdditionalInfo, "oms");
	}
	
	public void InsertOrderReleaseAdditionalInfo(String order_release_id_fk,
			String ppsId, String key) throws SQLException {
		String updateOrderAdditionalInfo = "INSERT INTO `order_release_additional_info` (`order_release_id_fk`, `key`, `value`, `created_by`,`version`) "
				+ "VALUES("
				+ order_release_id_fk
				+ ", '"
				+ key
				+ "', '"
				+ ppsId + "', 'mobile', 0)";
		DBUtilities.exUpdateQuery(updateOrderAdditionalInfo, "oms");
	}

	/*
	 * Update orders orderId
	 */
	public void updateOrders(String orderId, String cancellationReasonId)
			throws SQLException {
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cancelled_on=sdf.format(date);
		String updateOrders = "update orders SET `cancellation_reason_id_fk` = "
				+ cancellationReasonId + ",`cancelled_on` = '"+ cancelled_on+ "' where id = " + orderId + "";
		DBUtilities.exUpdateQuery(updateOrders, "oms");
	}

	/*
	 * Update orders_release orderReleaseId cancellationId isRefunded status
	 */
	public void updateOrdersRelease(String orderReleaseId,
			String cancellationReasonId, String isRefunded, String status)
			throws SQLException {
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cancelled_on=sdf.format(date);
		String updateOrdersRelease = "update `order_release` SET `cancellation_reason_id_fk` = "
				+ cancellationReasonId
				+ ", `is_refunded` = "
				+ isRefunded
				+ ", `status_code` = '"
				+ status
				+ "', `cancelled_on` = '"+ cancelled_on+ "' where `id` = "
				+ orderReleaseId + "";
		DBUtilities.exUpdateQuery(updateOrdersRelease, "oms");
	}

	/*
	 * Update orders_release orderReleaseId cancellationId isRefunded status
	 */
	public void updateOrdersRelease(String orderReleaseId, String amount,
			String status) throws SQLException {
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cancelled_on=sdf.format(date);
		String updateOrdersRelease = "update `order_release` SET `mrp_total` = '"+amount+ "', `status_code` = '"+ status+ "', `cancelled_on` = '"+ cancelled_on+ "' where `id` = " + orderReleaseId + "";
		DBUtilities.exUpdateQuery(updateOrdersRelease, "oms");
	}

	/*
	 * Update orders orderId
	 */
	public void updateOrderReleaseStatus(String orderId, String Status)
			throws SQLException {
		String updateOrders = "update order_release set status_code = '"
				+ Status + "' where id = " + orderId + "";
		DBUtilities.exUpdateQuery(updateOrders, "oms");
	}
	/*
	 * Update orderRelease DeliveredOn
	 */
	public void updateOrderReleaseDeliveredOn(String orderReleaseId)
			throws SQLException {
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String deliveredOn=sdf.format(date);
		String updateOrders = "update order_release set delivered_on = '"
				+ deliveredOn + "', status_code ='DL'  where id = " + orderReleaseId + "";
		DBUtilities.exUpdateQuery(updateOrders, "oms");
	}
	
	public void updateOrderReleaseStatusAndPackedOn(String OrderReleaseId, String status)
			throws SQLException {
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String packed_on=sdf.format(date);
		String updateOrders = "update order_release set status_code = '"
				+ status + "', packed_on = '"
				+ packed_on + "' where id = " + OrderReleaseId + "";
		DBUtilities.exUpdateQuery(updateOrders, "oms");
	}
	
	/*
	 * Update order Release Packed with Yesterdays date 
	 */
	public void updateOrderReleaseStatusAndPackedOnWithOldDate(String OrderReleaseId, String status)
			throws SQLException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String packed_on=sdf.format(cal.getTime());
		String updateOrders = "update order_release set status_code = '"
				+ status + "', packed_on = '"
				+ packed_on + "' where id = " + OrderReleaseId + "";
		DBUtilities.exUpdateQuery(updateOrders, "oms");
	}
	
	

	/*
	 * Update order status and CancelledOn with Yesterdays date
	 */
	public void updateOrderCancelledOnWithOldDate(String orderId)
			throws SQLException {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String cancelledOn=sdf.format(cal.getTime());
		String updateOrders = "update orders set cancelled_on = '"
				+ cancelledOn + "' where id = " + orderId + "";
		DBUtilities.exUpdateQuery(updateOrders, "oms");
	}
	
	/*
	 * Update order status and CancelledOn
	 */
	public void updateOrderCancelledOn(String orderId)
			throws SQLException {
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cancelledOn=sdf.format(date);
		String updateOrders = "update orders set cancelled_on = '"
				+ cancelledOn + "' where id = " + orderId + "";
		DBUtilities.exUpdateQuery(updateOrders, "oms");
	}
	
	/*
	 * Update orderRelease status and CancelledOn
	 */
	public void updateOrderReleaseStatusAndCancelledOn(String orderReleaseId, String Status)
			throws SQLException {
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cancelledOn=sdf.format(date);
		String updateOrders = "update order_release set cancelled_on = '"
				+ cancelledOn + "', status_code = '"
				+ Status + "' where id = " + orderReleaseId + "";
		DBUtilities.exUpdateQuery(updateOrders, "oms");
	}

	/*
	 * Update orders_Line_Status orderId status
	 */
	public void updateOrderLineStatus(String orderId, String Status) throws SQLException {
		String updateOrders = "update order_line set status_code = '" + Status
				+ "' where order_id_fk = " + orderId + "";
		DBUtilities.exUpdateQuery(updateOrders, "oms");
	}
	
	/*
	 * Update orders_Line_Status orderId status
	 */
	public void updateOrderLineStatusByLineIdAndCancelledOn(String lineId, String Status) throws SQLException {
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cancelledOn=sdf.format(date);
		String updateOrders = "update order_line set status_code = '" + Status
				+ "', cancelled_on = '"
				+ cancelledOn + "' where id = " + lineId + "";
		DBUtilities.exUpdateQuery(updateOrders, "oms");
	}
	
	/*
	 * Update orders_Line_Status orderId status and Cancelled on
	 */
	public void updateOrderLineStatusByLineId(String lineId, String Status) throws SQLException {
		String updateOrders = "update order_line set status_code = '" + Status
				+ "' where id = " + lineId + "";
		DBUtilities.exUpdateQuery(updateOrders, "oms");
	}
	
	public void insertOrderLineAdditionalInfo(String lineId, String ppsIdrefund, String amount, String mistmatchRefund) throws SQLException{
		String insertOrderLineAddInfo = "INSERT INTO `order_line_additional_info` (`order_line_id_fk`, `key`, `value`, `created_by`,`version`) "
				+ "VALUES("+lineId+", 'FRAGILE', 'false', 'pps-admin', 0),"
				+ "("+lineId+", 'HAZMAT', 'false', 'pps-admin',  0),"
				+ "("+lineId+", 'JEWELLERY', 'false', 'pps-admin',  0),"
				+ "("+lineId+", 'CUSTOMIZABLE', 'false', 'pps-admin',  0),"
				+ "("+lineId+", 'CUSTOMIZED_MESSAGE', '', 'pps-admin',  0),"
				+ "("+lineId+", 'PACKAGING_TYPE', 'NORMAL', 'pps-admin',  0),"
				+ "("+lineId+", 'PACKAGING_STATUS', 'NOT_PACKAGED', 'pps-admin', 0),"
				+ "("+lineId+", 'GIFT_CARD_AMOUNT', '0.0', 'pps-admin', 0),"
				+ "("+lineId+", 'GOVT_TAX_RATE', '0.000', 'pps-admin',  0),"
				+ "("+lineId+", 'GOVT_TAX_AMOUNT', '0.00', 'pps-admin', 0),"
				+ "("+lineId+", 'IS_EXCHANGEABLE', 'true', 'pps-admin', 0),"
				+ "("+lineId+", 'STORED_CREDIT_USAGE', '0.0', 'pps-admin',  0),"
				+ "("+lineId+", 'EARNED_CREDIT_USAGE', '0.0', 'pps-admin',  0),"
				+ "("+lineId+", 'GOVT_TAX_TYPE', 'CST', 'System', 0),"
				+ "("+lineId+", 'GOVT_TAXABLE_AMOUNT', '"+amount+"', 'System',  0),"
				+ "("+lineId+", 'PRICE_MISMATCH_REFUND', '"+mistmatchRefund+"', 'system',  0),"
				+ "("+lineId+", 'PRICE_MISMATCH_REFUND_PPS_ID', '"+ppsIdrefund+"', 'system', 0)";
		DBUtilities.exUpdateQuery(insertOrderLineAddInfo, "oms");

	}
	
	public void insertOrderLineAdditionalInfoLineCancellation(String lineId, String ppsIdrefund, String amount) throws SQLException{
		String insertOrderLineAddInfo = "INSERT INTO `order_line_additional_info` (`order_line_id_fk`, `key`, `value`, `created_by`,`version`) "
				+ "VALUES("+lineId+", 'FRAGILE', 'false', 'pps-admin', 0),"
				+ "("+lineId+", 'HAZMAT', 'false', 'pps-admin',  0),"
				+ "("+lineId+", 'JEWELLERY', 'false', 'pps-admin',  0),"
				+ "("+lineId+", 'CUSTOMIZABLE', 'false', 'pps-admin',  0),"
				+ "("+lineId+", 'CUSTOMIZED_MESSAGE', '', 'pps-admin',  0),"
				+ "("+lineId+", 'PACKAGING_TYPE', 'NORMAL', 'pps-admin',  0),"
				+ "("+lineId+", 'PACKAGING_STATUS', 'NOT_PACKAGED', 'pps-admin', 0),"
				+ "("+lineId+", 'GIFT_CARD_AMOUNT', '0.0', 'pps-admin', 0),"
				+ "("+lineId+", 'GOVT_TAX_RATE', '0.000', 'pps-admin',  0),"
				+ "("+lineId+", 'GOVT_TAX_AMOUNT', '0.00', 'pps-admin', 0),"
				+ "("+lineId+", 'IS_EXCHANGEABLE', 'true', 'pps-admin', 0),"
				+ "("+lineId+", 'STORED_CREDIT_USAGE', '0.0', 'pps-admin',  0),"
				+ "("+lineId+", 'EARNED_CREDIT_USAGE', '0.0', 'pps-admin',  0),"
				+ "("+lineId+", 'GOVT_TAX_TYPE', 'CST', 'System', 0),"
				+ "("+lineId+", 'GOVT_TAXABLE_AMOUNT', '"+amount+"', 'System',  0),"
				+ "("+lineId+", 'LINE_CANCELLATION_PPS_ID', '"+ppsIdrefund+"', 'system', 0)";
		DBUtilities.exUpdateQuery(insertOrderLineAddInfo, "oms");

	}

	/**
	 *  VAT Adjust Refund
	 * @param lineId
	 * @param ppsIdrefund
	 * @param amount
	 * @param mistmatchRefund
	 * @throws SQLException
	 */
	public void insertOrderLineAdditionalInfo1(String lineId, String ppsIdrefund, String amount, String mistmatchRefund,String vatAdjustmentRefId) throws SQLException{
		String insertOrderLineAddInfo = "INSERT INTO `order_line_additional_info` (`order_line_id_fk`, `key`, `value`, `created_by`,`version`) "
				+ "VALUES("+lineId+", 'FRAGILE', 'false', 'pps-admin', 0),"
				+ "("+lineId+", 'HAZMAT', 'false', 'pps-admin',  0),"
				+ "("+lineId+", 'JEWELLERY', 'false', 'pps-admin',  0),"
				+ "("+lineId+", 'CUSTOMIZABLE', 'false', 'pps-admin',  0),"
				+ "("+lineId+", 'CUSTOMIZED_MESSAGE', '', 'pps-admin',  0),"
				+ "("+lineId+", 'PACKAGING_TYPE', 'NORMAL', 'pps-admin',  0),"
				+ "("+lineId+", 'PACKAGING_STATUS', 'NOT_PACKAGED', 'pps-admin', 0),"
				+ "("+lineId+", 'GIFT_CARD_AMOUNT', '0.0', 'pps-admin', 0),"
				+ "("+lineId+", 'GOVT_TAX_RATE', '0.000', 'pps-admin',  0),"
				+ "("+lineId+", 'GOVT_TAX_AMOUNT', '0.00', 'pps-admin', 0),"
				+ "("+lineId+", 'IS_EXCHANGEABLE', 'true', 'pps-admin', 0),"
				+ "("+lineId+", 'STORED_CREDIT_USAGE', '0.0', 'pps-admin',  0),"
				+ "("+lineId+", 'EARNED_CREDIT_USAGE', '0.0', 'pps-admin',  0),"
				+ "("+lineId+", 'GOVT_TAX_TYPE', 'CST', 'System', 0),"
				+ "("+lineId+", 'GOVT_TAXABLE_AMOUNT', '"+amount+"', 'System',  0),"
				+ "("+lineId+", 'VAT_ADJ_UNIT_REFUND', '"+mistmatchRefund+"', 'system',  0),"
				+ "("+lineId+", 'VAT_ADJ_REFUND_PPS_ID', '"+ppsIdrefund+"', 'system', 0),"
				+ "("+lineId+", 'VAT_ADJ_REFUND_TX_REF_ID', '"+vatAdjustmentRefId+"', 'system', 0)";
		DBUtilities.exUpdateQuery(insertOrderLineAddInfo, "oms");

	}
	// /-------------------- Insert/Update into PPS-----------------------------------------------------------------------------------------
	
	public void updatePaymentPlan(String ppsId, String actionType)
			throws SQLException {
		String updatePaymentPlan = "update payment_plan set actionType = '"
				+ actionType + "' where id = '"+ppsId+"'";
		DBUtilities.exUpdateQuery(updatePaymentPlan, "pps");
	}
	
	public void updatePaymentPlanInstrumentDetail(String ppsId, String actionType)
			throws SQLException {
		String updatePaymentPlanInstruDetail = "update payment_plan_instrument_details set actionType = '"
				+ actionType + "' where pps_Id = '"+ppsId+"'";
		DBUtilities.exUpdateQuery(updatePaymentPlanInstruDetail, "pps");
	}
	
	/*
	 * ppsId orderId actionType clientTxId SourecId sessionId amount ReturnID
	 */
	public void InsertPaymentPlan(String ppsId, String orderId,
			String actionType, String clientTransectionId, String sourceId,
			String sessionId, String amount, String returnId)
			throws SQLException {
		String paymentPlan = "INSERT INTO `payment_plan` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `clientTransactionId`, `crmRefId`, `login`, "
				+ "`orderId`, `ppsType`, `sourceId`, `state`, `sessionId`, `cartContext`, `totalAmount`, `mobile`, `returnId`, `userAgent`, `clientIP`) "
				+ "VALUES('"
				+ ppsId
				+ "', 'PPS Plan created', 'SYSTEM', 1452168967721, '"
				+ actionType
				+ "', "
				+ "NULL, NULL, 'spsautomation@myntra.com', "
				+ orderId
				+ ", 'ORDER', '"+sourceId+"', "
				+ "'PPFSM Order Taking done', 'JJN006f16b193f216e46ed87a3241ab780df9a1451887524M', 'DEFAULT', "
				+ amount
				+ ", NULL, "
				+ returnId
				+ ", "
				+ "'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36', '27.251.249.86')";
		DBUtilities.exUpdateQuery(paymentPlan, "pps");
	}

	/*
	 * ppsId paymentPlanItemId itemType pricePerUnit qty sellerId skuId
	 */
	public void InsertPaymentPlanItem(String ppsId, String paymentPlanItemId,
			String itemType, String pricePerUnit, String qty, String sellerId,
			String skuId) throws SQLException {
		String paymentPlanItem = "INSERT INTO `payment_plan_item` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `itemType`, `pricePerUnit`, `quantity`, "
				+ "`sellerId`, `skuId`, `pps_Id`) VALUES("
				+ paymentPlanItemId
				+ ", 'Payment Plan Item created', 'SYSTEM', 1452168967883, '"
				+ itemType
				+ "', "
				+ pricePerUnit
				+ ", "
				+ qty
				+ ", '"
				+ sellerId + "', '" + skuId + "', " + "'" + ppsId + "')";
		DBUtilities.exUpdateQuery(paymentPlanItem, "pps");
	}

	/*
	 * amount paymentPlanItemId
	 */
	public void InsertPaymentPlanItemInstrument(String amount,
			String paymentPlanItemId) throws SQLException {
		String paymentPlanItemInstrument = "INSERT INTO `payment_plan_item_instrument` (`comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, "
				+ "`ppsItemId`) VALUES('Payment Plan Item Instrument Detail created', 'SYSTEM', 1452168967895, "
				+ amount + ", 1, " + paymentPlanItemId + ")";
		DBUtilities.exUpdateQuery(paymentPlanItemInstrument, "pps");
	}

	/*
	 * amount 
	 * paymentPlanItemId 
	 * actionType
	 */
	public void InsertPaymentPlanItemInstrument(String amount,
			String paymentPlanItemId, String actionType) throws SQLException {
		String paymentPlanItemInstrument = "INSERT INTO `payment_plan_item_instrument` (`comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, "
				+ "`ppsItemId`, `actionType`) VALUES('Payment Plan Item Instrument Detail created', 'SYSTEM', 1452168967895, "
				+ amount
				+ ", 1, "
				+ paymentPlanItemId
				+ ", '"
				+ actionType
				+ "')";
		DBUtilities.exUpdateQuery(paymentPlanItemInstrument, "pps");
	}
	
	/*
	 * amount paymentPlanItemId
	 */
	public void InsertPaymentPlanItemInstrument(String amount,
			String paymentPlanItemId, String paymentInstrumentType, String updateBy) throws SQLException {
		String paymentPlanItemInstrument = "INSERT INTO `payment_plan_item_instrument` (`comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, "
				+ "`ppsItemId`) VALUES('Payment Plan Item Instrument Detail created', '"+updateBy+"', 1452168967895, "
				+ amount + ", "+paymentInstrumentType+", " + paymentPlanItemId + ")";
		DBUtilities.exUpdateQuery(paymentPlanItemInstrument, "pps");
	}
	
	public void InsertPaymentPlanItemInstrument(String amount,
			String paymentPlanItemId, String paymentInstrumentType,String actionType, String updateBy) throws SQLException {
		String paymentPlanItemInstrument = "INSERT INTO `payment_plan_item_instrument` (`comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, "
				+ "`ppsItemId`,`actionType`) VALUES('Payment Plan Item Instrument Detail created', '"+updateBy+"', 1452168967895, "
				+ amount + ", "+paymentInstrumentType+", " + paymentPlanItemId + ",'"+actionType+"')";
		DBUtilities.exUpdateQuery(paymentPlanItemInstrument, "pps");
	}

	/*
	 * ppsId 
	 * totalAmount 
	 * actionType
	 */
	public void InsertPaymentPlanInstrumentDetail(String ppsId,
			String totalAmount, String actionType) throws SQLException {
		String paymentPlanInstrumentDetail = "INSERT INTO `payment_plan_instrument_details` (`comments`, `updatedBy`, `updatedTimestamp`, `paymentInstrumentType`, "
				+ "`totalPrice`, `pps_Id`, `paymentPlanExecutionStatus_id`, `actionType`, `parentInstrumentDetailId`) VALUES('PPS Plan Instrument Details created', "
				+ "'SYSTEM', 1452168967889, 1, "
				+ totalAmount
				+ ", '"
				+ ppsId
				+ "', 17931, '" + actionType + "', NULL)";
		DBUtilities.exUpdateQuery(paymentPlanInstrumentDetail, "pps");
	}
	
	/*
	 * ppsId 
	 * totalAmount 
	 * actionType
	 */
	public void InsertPaymentPlanInstrumentDetail(String ppsId,
			String totalAmount, String actionType, String paymentInstrumentType) throws SQLException {
		String paymentPlanInstrumentDetail = "INSERT INTO `payment_plan_instrument_details` (`comments`, `updatedBy`, `updatedTimestamp`, `paymentInstrumentType`, "
				+ "`totalPrice`, `pps_Id`, `paymentPlanExecutionStatus_id`, `actionType`, `parentInstrumentDetailId`) VALUES('PPS Plan Instrument Details created', "
				+ "'SYSTEM', 1452168967889, "+paymentInstrumentType+", "
				+ totalAmount
				+ ", '"
				+ ppsId
				+ "', 17931, '" + actionType + "', NULL)";
		DBUtilities.exUpdateQuery(paymentPlanInstrumentDetail, "pps");
	}
	
	/*
	 * ppsId 
	 * totalAmount 
	 * actionType
	 */
	public void InsertPaymentPlanInstrumentDetail(String ppsId,
			String totalAmount, String actionType, String paymentInstrumentType, String paymentPlanExecutionStatus_id) throws SQLException {
		String paymentPlanInstrumentDetail = "INSERT INTO `payment_plan_instrument_details` (`comments`, `updatedBy`, `updatedTimestamp`, `paymentInstrumentType`, "
				+ "`totalPrice`, `pps_Id`, `paymentPlanExecutionStatus_id`, `actionType`) VALUES('PPS Plan Instrument Details created', "
				+ "'SYSTEM', 1452168967889, "+paymentInstrumentType+", "
				+ totalAmount
				+ ", '"
				+ ppsId
				+ "', "+paymentPlanExecutionStatus_id+", '" + actionType + "')";
		DBUtilities.exUpdateQuery(paymentPlanInstrumentDetail, "pps");
	}
	
	public void InsertPaymentPlanExecutionStatus(String id, String actionType, String ppsActionType ) throws SQLException{
	String PaymentPlanExecutionStatus = "INSERT INTO `payment_plan_execution_status` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `instrumentTransactionId`, "
			+ "`invoker`, `invokerTransactionId`, `numOfRetriesDone`, `ppsActionType`, `state`, `status`) "
			+ "VALUES("+id+", 'Payment Plan Execution Status created', 'SYSTEM', 1455272909102, '"+actionType+"', 'ON:a4737dcf-3720-4bad-a81c-e312b027f482', "
			+ "'pps', '7af30fe4-e475-4127-acff-6c846d95e94f', 0, '"+ppsActionType+"', 'PIFSM Payment Successful', 0)";
	DBUtilities.exUpdateQuery(PaymentPlanExecutionStatus, "pps");	
	}
	
//--------------------Delete Record Calls-----------------
	
	public void deleteOrderRecord(String orderId, String ppsId, String paymentPlanItemId) throws SQLException{
		deleteFromOrdersAdditionalInfo(orderId);
		deleteFromOrdersRelease(orderId);
		deleteFromOrdersLine(orderId);
		deleteFromOrders(orderId);
		deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		deleteFromPaymentPlanItem(ppsId);
		deleteFromPaymentPlanInstrumentDetail(ppsId);
		deleteFromPaymentPlan(ppsId);
		System.out.println("Deletion of all the record which we Inserted Successfull");
	}
	public void deleteOrderRecord(String orderId, String ppsId, String paymentPlanItemId, String ppsCancellationId, String paymentPlanItemIdref) throws SQLException{
		deleteFromOrdersAdditionalInfo(orderId);
		deleteFromOrdersRelease(orderId);
		deleteFromOrdersLine(orderId);
		deleteFromOrders(orderId);
		deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		deleteFromPaymentPlanItem(ppsId);
		deleteFromPaymentPlanInstrumentDetail(ppsId);
		deleteFromPaymentPlan(ppsId);
		deleteFromPaymentPlanItemInstrument(paymentPlanItemIdref);
		deleteFromPaymentPlanItem(ppsCancellationId);
		deleteFromPaymentPlanInstrumentDetail(ppsCancellationId);
		deleteFromPaymentPlan(ppsCancellationId);
		System.out.println("Deletion of all the record which we Inserted Successfull");
	}
	
	public void deleteFromOrders(String orderId)
			throws SQLException {
		String updateOrders = "DELETE from orders where id = "+orderId+"";
		DBUtilities.exUpdateQuery(updateOrders, "oms");
	}
	
	public void deleteFromOrdersRelease(String orderId)
			throws SQLException {
		String updateOrdersRelease = "DELETE from order_release where order_id_fk = "+orderId+"";
		DBUtilities.exUpdateQuery(updateOrdersRelease, "oms");
	}
	
	public void deleteFromOrdersLine(String orderId)
			throws SQLException {
		String updateOrdersLine = "DELETE from order_line where order_id_fk = "+orderId+"";
		DBUtilities.exUpdateQuery(updateOrdersLine, "oms");
	}
	
	public void deleteFromOrdersAdditionalInfo(String orderId)
			throws SQLException {
		String updateOrdersAdditioanlIfo = "DELETE from order_additional_info where order_id_fk ="+orderId+"";
		DBUtilities.exUpdateQuery(updateOrdersAdditioanlIfo, "oms");
	}
	
	public void deleteFromPaymentPlan(String ppsId)
			throws SQLException {
		String updatePaymentPlan = "DELETE from payment_plan where id = \""+ppsId+"\"";
		DBUtilities.exUpdateQuery(updatePaymentPlan, "pps");
	}
	
	public void deleteFromPaymentPlanInstrumentDetail(String ppsId)
			throws SQLException {
		String updatePlanInstrumentDetail = "DELETE from payment_plan_instrument_details where pps_Id = \""+ppsId+"\"";
		DBUtilities.exUpdateQuery(updatePlanInstrumentDetail, "pps");
	}
	
	public void deleteFromPaymentPlanItem(String ppsId)
			throws SQLException {
		String updatePaymentPlanItem = "DELETE from payment_plan_item where pps_Id = \""+ppsId+"\"";
		DBUtilities.exUpdateQuery(updatePaymentPlanItem, "pps");
	}
	
	public void deleteFromPaymentPlanItemInstrument(String paymentPlanItemId)
			throws SQLException {
		String updatePaymentPlanItemInstrument = "DELETE from payment_plan_item_instrument where ppsItemId = "+paymentPlanItemId+"";
		DBUtilities.exUpdateQuery(updatePaymentPlanItemInstrument, "pps");
	}
	
	public void deleteFromPaymentPlanExecutionStatus(String paymentPlanExecutionStatus_id)
			throws SQLException {
		String updatePaymentPlanExecutionStatus = "DELETE from payment_plan_execution_status where id = "+paymentPlanExecutionStatus_id+"";
		DBUtilities.exUpdateQuery(updatePaymentPlanExecutionStatus, "pps");
	}
	
	public void deleteFromOrdersLineAdditionalInfo(String lineId)
			throws SQLException {
		String updateOrdersAdditioanlIfo = "DELETE from order_line_additional_info where order_line_id_fk ="+lineId+"";
		DBUtilities.exUpdateQuery(updateOrdersAdditioanlIfo, "oms");
	}
	

////------------------PriceOverWrite API call-----------------------
//	
//	public void priceOverRide(String lineId, String newUnitPrice){
//		String userLogin = "spsautomation@myntra.com";
//		String comment = "Price found on the tag was lesser.";
//		MyntraService service = Myntra.getService(
//				ServiceType.ERP_OMS, APINAME.PRICEOVERRIDE,
//				init.Configurations, new String[] { lineId, newUnitPrice, userLogin, comment},
//				PayloadType.XML, PayloadType.JSON);
//
//		HashMap getParam = new HashMap();
//		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
//
//		System.out.println(service.URL);
//		RequestGenerator req = new RequestGenerator(service, getParam);
//		System.out.println(req.respvalidate.returnresponseasstring());
//		AssertJUnit.assertEquals(200, req.response.getStatus());
//		String statusCode = "1012";
//		String responseStatusCode = req.respvalidate.NodeValue(
//				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
//		AssertJUnit.assertTrue(responseStatusCode.equals(statusCode));
//		
//    }

		
//--------------------SPS payload creation ----------------------------------------------------------------------------------------
	
	//Refund message
	public String RefundMessageCreation(String ppsId, String releaseId,String reason){
		String message = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><spsrefundEntry><version>0</version><ppsId>"+ppsId+"</ppsId><releaseId>"+releaseId+"</releaseId><refundType>"+reason+"</refundType><eventTimeStamp>2017-01-24T13:30:30.664+05:30</eventTimeStamp><sellerRefundReason>Fit Issues</sellerRefundReason></spsrefundEntry>";
		return message;
	}
	
	
	public String RefundOrderCancellationMessageCreation(String orderId, String ppsId, String reason){
		String message = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><spsrefundEntry><version>0</version><orderId>"+orderId+"</orderId><ppsId>"+ppsId+"</ppsId><refundType>"+reason+"</refundType><eventTimeStamp>2017-01-24T13:30:30.664+05:30</eventTimeStamp></spsrefundEntry>";
		return message;
	}
	
	public String RefundOrderCancellationMessageCreationWithOutOrderId(String storeOrderId, String ppsId, String reason){
		String message = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><spsrefundEntry><version>0</version><storeOrderId>"+storeOrderId+"</storeOrderId><ppsId>"+ppsId+"</ppsId><refundType>"+reason+"</refundType><eventTimeStamp>2017-03-10 11:50:26</eventTimeStamp></spsrefundEntry>";
		return message;
	}
	
//	public String RefundOrderCancellationMessageCreationWithOutOrderIdAndOldDate(String storeOrderId, String ppsId, String reason){
//		String message = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><spsrefundEntry><version>0</version><storeOrderId>"+storeOrderId+"</storeOrderId><ppsId>"+ppsId+"</ppsId><refundType>"+reason+"</refundType><eventTimeStamp>2017-01-24T13:30:30.664+05:30</eventTimeStamp></spsrefundEntry>";
//		return message;
//	}
	
	public String RefundParentOrderExchangeMessageCreation(String parentStoreOrderId,String ppsId, String releaseId,String reason){
		String message = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><spsrefundEntry><version>0</version><parentOrderId>"+parentStoreOrderId+"</parentOrderId><ppsId>"+ppsId+"</ppsId><releaseId>"+releaseId+"</releaseId><sellerRefundReason>"+reason+"</sellerRefundReason></spsrefundEntry>";
		return message;
	}
	
    //Create Packet with single release
	public String OMSMessageCreation(long orderId, String store_order_id, String status, String statusDisplay, double mrpTotal, double finalAmount, double ShippingCharge, boolean gift, double giftCardAmount,
			double giftCharge, double loyaltyPointsUsed, double cashRedeemed, 
			long lineId, double couponDiscount, double lineFinalAmount, double lineGiftCardAmount, boolean isExchangable, double lineLoyaltyPointsUsed, long optionId,
			long orderReleaseId, int lineQuantity, long sellerId, long skuId, String lineStatus, String lineStatusDisplayName, long styleId, String supplyType, double unitPrice, double lineCashRedeemed,String paymentPpsId,String paymentMethod) throws JAXBException {
		Date date = new Date();
		OrderReleaseEntry orderReleaseEntry = new OrderReleaseEntry();// old and update entry
		OrderLineEntry orderLineEntry = new OrderLineEntry();
		ReleaseUpdateEventEntry releaseUpdateEventEntry = new ReleaseUpdateEventEntry();

		
		orderReleaseEntry.setAddress("xyz");
		releaseUpdateEventEntry.setEntityId(orderId);
		releaseUpdateEventEntry.setEventTime(date);
		orderReleaseEntry.setOrderDate(null);

		// ------------------set old/update entry
		orderReleaseEntry.setCreatedBy("sps automation");
		orderReleaseEntry.setCreatedOn(date);
//		orderReleaseEntry.setId(orderId); //
		orderReleaseEntry.setId(orderReleaseId);
		orderReleaseEntry.setLastModifiedOn(date);
		orderReleaseEntry.setAddress("Myntra design, AKR tech park, Near kudlu gate");
		orderReleaseEntry.setAddressId((long) 6118996);
		orderReleaseEntry.setCartDiscount(0.0);
		orderReleaseEntry.setCashRedeemed(cashRedeemed);
		orderReleaseEntry.setCity("Bangalore");
		orderReleaseEntry.setCodCharge(0.0);
		orderReleaseEntry.setCodPaymentStatus("pending");
		orderReleaseEntry.setCountry("India");
		orderReleaseEntry.setCouponDiscount(couponDiscount);
		orderReleaseEntry.setDiscount(0.0);
		orderReleaseEntry.setEarnedCredit(0.0);
		orderReleaseEntry.setEmail("spsautomation@myntra.com");
		orderReleaseEntry.setEmiCharge(0.0);
		orderReleaseEntry.setFinalAmount(finalAmount); //
		orderReleaseEntry.setGift(gift);
		orderReleaseEntry.setGiftCardAmount(giftCardAmount);
		orderReleaseEntry.setGiftCharge(giftCharge);
		orderReleaseEntry.setLocality("Bangalore");
		orderReleaseEntry.setLogin("spsautomation@myntra.com");
	//	orderReleaseEntry.setLogin("loadtest123");
		orderReleaseEntry.setLoyaltyConversionFactor(0.0);
		orderReleaseEntry.setLoyaltyPointsCredit(0.0);
		orderReleaseEntry.setLoyaltyPointsUsed(loyaltyPointsUsed);
		orderReleaseEntry.setMobile("9191919191");
		orderReleaseEntry.setMrpTotal(mrpTotal);
		orderReleaseEntry.setOnHold(false);
		orderReleaseEntry.setOrderId(orderId);
		orderReleaseEntry.setPaymentMethod(paymentMethod);
		orderReleaseEntry.setPaymentMethodDisplay("Credit card");
		orderReleaseEntry.setPersonalized(false);
		orderReleaseEntry.setPgDiscount(0.0);
		orderReleaseEntry.setReceiverName("sps automation");
		orderReleaseEntry.setRefunded(false);
		orderReleaseEntry.setShippingCharge(ShippingCharge);
		orderReleaseEntry.setShippingMethod("Normal");
		orderReleaseEntry.setSingleItemRelease(false);
		orderReleaseEntry.setSpecialPincode(false);
		orderReleaseEntry.setState("KA");
		orderReleaseEntry.setStatus(status);
		orderReleaseEntry.setStatusDisplayName(statusDisplay);
		orderReleaseEntry.setStoreOrderId(String.valueOf(store_order_id));
		orderReleaseEntry.setStoreId((long)(1));
		orderReleaseEntry.setStoredCredit(0.0);
		orderReleaseEntry.setTaxAmount(0.0);
		orderReleaseEntry.setTrackingNo("ML" + randomGenn(7));
		orderReleaseEntry.setUserContactNo("9191919191");
		orderReleaseEntry.setZipcode("560068");
		orderReleaseEntry.setPaymentPpsId(paymentPpsId);
		orderReleaseEntry.setDeliveredOn(date);
		orderReleaseEntry.setPackedOn(date);

		// ------------------Set order line
		orderLineEntry.setCreatedBy("sps automation");
		orderLineEntry.setCreatedOn(date);
		orderLineEntry.setId(lineId);// lineId long
		orderLineEntry.setLastModifiedOn(date);
		orderLineEntry.setCartDiscount(0.0);
		orderLineEntry.setCashRedeemed(lineCashRedeemed);
		orderLineEntry.setCashbackOffered(0.0);
		orderLineEntry.setCouponDiscount(couponDiscount);
		orderLineEntry.setCustomizedMessage("Test");
		orderLineEntry.setDiscount(0.0);
		orderLineEntry.setDiscountRuleId(0);
		orderLineEntry.setDiscountRuleRevId(0);
		orderLineEntry.setDiscountedProduct(false);
		orderLineEntry.setDiscountedQuantity(0);
		orderLineEntry.setEarnedCredit(0.0);
		orderLineEntry.setFinalAmount(lineFinalAmount);
		orderLineEntry.setFragile(false);
		orderLineEntry.setGiftCardAmount(lineGiftCardAmount);
		orderLineEntry.setGovtTaxAmount(0.0);
		orderLineEntry.setGovtTaxRate(5.0);
		orderLineEntry.setHazMat(false);
		orderLineEntry.setIsCustomizable(false);
		orderLineEntry.setIsDiscountedProduct(false);
		orderLineEntry.setIsExchangeableProduct(isExchangable);
		orderLineEntry.setIsReturnableProduct(true);
		orderLineEntry.setIsFragile(false);
		orderLineEntry.setIsJewellery(false);
		orderLineEntry.setJewellery(false);
		orderLineEntry.setLoyaltyConversionFactor(0.0);
		orderLineEntry.setLoyaltyPointsAwarded(0.0);
		orderLineEntry.setLoyaltyPointsCredit(0.0);
		orderLineEntry.setLoyaltyPointsUsed(lineLoyaltyPointsUsed);
		orderLineEntry.setOptionId(optionId);
		orderLineEntry.setOrderId(orderId);
		orderLineEntry.setOrderReleaseId(orderReleaseId);
		orderLineEntry.setPackagingStatus("NOT_PACKAGED");
		orderLineEntry.setPackagingType("NORMAL");
		orderLineEntry.setPgDiscount(0.0);
		orderLineEntry.setPoStatus("UNUSED");
		orderLineEntry.setQuantity(lineQuantity);
		orderLineEntry.setSellerId(sellerId);
		orderLineEntry.setSkuId(skuId);
		orderLineEntry.setStatus(lineStatus);
		orderLineEntry.setStatusDisplayName(lineStatusDisplayName);
		orderLineEntry.setStoredCredit(0.0);
		orderLineEntry.setStoreOrderId(String.valueOf(store_order_id));
		orderLineEntry.setStoreLineId(String.valueOf(orderId));
		orderLineEntry.setStyleId(styleId);
		orderLineEntry.setSupplyType(supplyType);
		orderLineEntry.setTaxAmount(0.0);
		orderLineEntry.setTaxRate(5.0);
		orderLineEntry.setUnitPrice(unitPrice);
		
		
		
		List<OrderLineEntry> orderLineEntries = new ArrayList<OrderLineEntry>();
		orderLineEntries.add(orderLineEntry);

		orderReleaseEntry.setOrderLines(orderLineEntries);
		releaseUpdateEventEntry.setOldEntry(orderReleaseEntry);
		releaseUpdateEventEntry.setUpdatedEntry(orderReleaseEntry);

		String message = APIUtilities.convertXMLObjectToString(releaseUpdateEventEntry);
		return message;
	}
	
	
	//Create Packet with single release
		public String OMSMessageCreationReleaseID(long orderId, String store_order_id, String status, String statusDisplay, double mrpTotal, double finalAmount, double ShippingCharge, boolean gift, double giftCardAmount,
				double giftCharge, double loyaltyPointsUsed, double cashRedeemed, 
				long lineId, double couponDiscount, double lineFinalAmount, double lineGiftCardAmount, boolean isExchangable, double lineLoyaltyPointsUsed, long optionId,
				long orderReleaseId, int lineQuantity, long sellerId, long skuId, String lineStatus, String lineStatusDisplayName, long styleId, String supplyType, double unitPrice, double lineCashRedeemed,String paymentPpsId,String setPaymentMethod) throws JAXBException {
			Date date = new Date();
			OrderReleaseEntry orderReleaseEntry = new OrderReleaseEntry();// old and update entry
			OrderLineEntry orderLineEntry = new OrderLineEntry();
			ReleaseUpdateEventEntry releaseUpdateEventEntry = new ReleaseUpdateEventEntry();

			
			orderReleaseEntry.setAddress("xyz");
			releaseUpdateEventEntry.setEntityId(orderReleaseId);
			releaseUpdateEventEntry.setEventTime(date);
			orderReleaseEntry.setOrderDate(date);
			// ------------------set old/update entry
			orderReleaseEntry.setCreatedBy("sps automation");
			orderReleaseEntry.setCreatedOn(date);
			orderReleaseEntry.setId(orderReleaseId); //
			orderReleaseEntry.setLastModifiedOn(date);
			orderReleaseEntry.setAddress("Myntra design, AKR tech park, Near kudlu gate");
			orderReleaseEntry.setAddressId((long) 6118996);
			orderReleaseEntry.setCartDiscount(0.0);
			orderReleaseEntry.setCashRedeemed(cashRedeemed);
			orderReleaseEntry.setCity("Bangalore");
			orderReleaseEntry.setCodCharge(0.0);
			orderReleaseEntry.setCodPaymentStatus("pending");
			orderReleaseEntry.setCountry("India");
			orderReleaseEntry.setCouponDiscount(couponDiscount);
			orderReleaseEntry.setDiscount(0.0);
			orderReleaseEntry.setEarnedCredit(0.0);
			orderReleaseEntry.setEmail("spsautomation@myntra.com");
			orderReleaseEntry.setEmiCharge(0.0);
			orderReleaseEntry.setFinalAmount(finalAmount); //
			orderReleaseEntry.setGift(gift);
			orderReleaseEntry.setGiftCardAmount(giftCardAmount);
			orderReleaseEntry.setGiftCharge(giftCharge);
			orderReleaseEntry.setLocality("Bangalore");
			orderReleaseEntry.setLogin("spsautomation@myntra.com");
			orderReleaseEntry.setLoyaltyConversionFactor(0.0);
			orderReleaseEntry.setLoyaltyPointsCredit(0.0);
			orderReleaseEntry.setLoyaltyPointsUsed(loyaltyPointsUsed);
			orderReleaseEntry.setMobile("9191919191");
			orderReleaseEntry.setMrpTotal(mrpTotal);
			orderReleaseEntry.setOnHold(false);
			orderReleaseEntry.setOrderId(orderId);
			orderReleaseEntry.setPaymentMethod(setPaymentMethod);
			orderReleaseEntry.setPaymentMethodDisplay("Credit card");
			orderReleaseEntry.setPersonalized(false);
			orderReleaseEntry.setPgDiscount(0.0);
			orderReleaseEntry.setPackedOn(date);
			orderReleaseEntry.setReceiverName("sps automation");
			orderReleaseEntry.setRefunded(false);
			orderReleaseEntry.setShippingCharge(ShippingCharge);
			orderReleaseEntry.setShippingMethod("Normal");
			orderReleaseEntry.setSingleItemRelease(false);
			orderReleaseEntry.setSpecialPincode(false);
			orderReleaseEntry.setState("KA");
			orderReleaseEntry.setStatus(status);
			orderReleaseEntry.setStatusDisplayName(statusDisplay);
			orderReleaseEntry.setStoreId((long)(1));
			orderReleaseEntry.setStoreOrderId(String.valueOf(store_order_id));
			orderReleaseEntry.setStoredCredit(0.0);
			orderReleaseEntry.setTaxAmount(0.0);
			orderReleaseEntry.setTrackingNo("ML" + randomGenn(7));
			orderReleaseEntry.setUserContactNo("9191919191");
			orderReleaseEntry.setZipcode("560068");
			orderReleaseEntry.setPaymentPpsId(paymentPpsId);

			// ------------------Set order line
			orderLineEntry.setCreatedBy("sps automation");
			orderLineEntry.setCreatedOn(date);
			orderLineEntry.setId(lineId);// lineId long
			orderLineEntry.setLastModifiedOn(date);
			orderLineEntry.setCartDiscount(0.0);
			orderLineEntry.setCashRedeemed(lineCashRedeemed);
			orderLineEntry.setCashbackOffered(0.0);
			orderLineEntry.setCouponDiscount(couponDiscount);
			orderLineEntry.setCustomizedMessage("Test");
			orderLineEntry.setDiscount(0.0);
			orderLineEntry.setDiscountRuleId(0);
			orderLineEntry.setDiscountRuleRevId(0);
			orderLineEntry.setDiscountedProduct(false);
			orderLineEntry.setDiscountedQuantity(0);
			orderLineEntry.setEarnedCredit(0.0);
			orderLineEntry.setFinalAmount(lineFinalAmount);
			orderLineEntry.setFragile(false);
			orderLineEntry.setGiftCardAmount(lineGiftCardAmount);
			orderLineEntry.setGovtTaxAmount(0.0);
			orderLineEntry.setGovtTaxRate(5.0);
			orderLineEntry.setHazMat(false);
			orderLineEntry.setIsCustomizable(false);
			orderLineEntry.setIsDiscountedProduct(false);
			orderLineEntry.setIsExchangeableProduct(isExchangable);
			orderLineEntry.setIsReturnableProduct(true);
			orderLineEntry.setIsFragile(false);
			orderLineEntry.setIsJewellery(false);
			orderLineEntry.setJewellery(false);
			orderLineEntry.setLoyaltyConversionFactor(0.0);
			orderLineEntry.setLoyaltyPointsAwarded(0.0);
			orderLineEntry.setLoyaltyPointsCredit(0.0);
			orderLineEntry.setLoyaltyPointsUsed(lineLoyaltyPointsUsed);
			orderLineEntry.setOptionId(optionId);
			orderLineEntry.setOrderId(orderId);
			orderLineEntry.setOrderReleaseId(orderReleaseId);
			orderLineEntry.setPackagingStatus("NOT_PACKAGED");
			orderLineEntry.setPackagingType("NORMAL");
			orderLineEntry.setPgDiscount(0.0);
			orderLineEntry.setPoStatus("UNUSED");
			orderLineEntry.setQuantity(lineQuantity);
			orderLineEntry.setSellerId(sellerId);
			orderLineEntry.setSkuId(skuId);
			orderLineEntry.setStatus(lineStatus);
			orderLineEntry.setStatusDisplayName(lineStatusDisplayName);
			orderLineEntry.setStoredCredit(0.0);
			orderLineEntry.setStoreOrderId(String.valueOf(store_order_id));
			orderLineEntry.setStyleId(styleId);
			orderLineEntry.setSupplyType(supplyType);
			orderLineEntry.setTaxAmount(0.0);
			orderLineEntry.setTaxRate(5.0);
			orderLineEntry.setUnitPrice(unitPrice);
			List<OrderLineEntry> orderLineEntries = new ArrayList<OrderLineEntry>();
			orderLineEntries.add(orderLineEntry);

			orderReleaseEntry.setOrderLines(orderLineEntries);
			releaseUpdateEventEntry.setOldEntry(orderReleaseEntry);
			releaseUpdateEventEntry.setUpdatedEntry(orderReleaseEntry);

			String message = APIUtilities.convertXMLObjectToString(releaseUpdateEventEntry);
			return message;
		}

		//Create Packet with three release
		public String OMSMessageCreationReleaseID(long orderId, String store_order_id,String status, String statusDisplay, double mrpTotal, double finalAmount, double ShippingCharge, boolean gift, double giftCardAmount,
				double giftCharge, double loyaltyPointsUsed, double cashRedeemed, 
				long lineId, double couponDiscount, double lineFinalAmount, double lineGiftCardAmount, boolean isExchangable, double lineLoyaltyPointsUsed, long optionId,
				long orderReleaseId, int lineQuantity, long sellerId, long skuId, String lineStatus, String lineStatusDisplayName, long styleId, String supplyType, double unitPrice, double lineCashRedeemed, 
				long lineId1, double couponDiscount1, double lineFinalAmount1, double lineGiftCardAmount1, boolean isExchangable1, double lineLoyaltyPointsUsed1, long optionId1,long orderReleaseId1, int lineQuantity1, 
				long sellerId1, long skuId1, String lineStatus1, String lineStatusDisplayName1, long styleId1, String supplyType1, double unitPrice1, double lineCashRedeemed1,String paymentPpsId,String setPaymentMethod) throws JAXBException {
			Date date = new Date();
			OrderReleaseEntry orderReleaseEntry = new OrderReleaseEntry();// old and update entry
			OrderLineEntry orderLineEntry = new OrderLineEntry();
			ReleaseUpdateEventEntry releaseUpdateEventEntry = new ReleaseUpdateEventEntry();

			
			orderReleaseEntry.setAddress("xyz");
			releaseUpdateEventEntry.setEntityId(orderReleaseId);
			releaseUpdateEventEntry.setEventTime(date);
			orderReleaseEntry.setOrderDate(date);

			// ------------------set old/update entry
			orderReleaseEntry.setCreatedBy("sps automation");
			orderReleaseEntry.setCreatedOn(date);
			orderReleaseEntry.setId(orderReleaseId); //
			orderReleaseEntry.setLastModifiedOn(date);
			orderReleaseEntry.setAddress("Myntra design, AKR tech park, Near kudlu gate");
			orderReleaseEntry.setAddressId((long) 6118996);
			orderReleaseEntry.setCartDiscount(0.0);
			orderReleaseEntry.setCashRedeemed(cashRedeemed);
			orderReleaseEntry.setCity("Bangalore");
			orderReleaseEntry.setCodCharge(0.0);
			orderReleaseEntry.setCodPaymentStatus("pending");
			orderReleaseEntry.setCountry("India");
			orderReleaseEntry.setCouponDiscount(couponDiscount);
			orderReleaseEntry.setDiscount(0.0);
			orderReleaseEntry.setEarnedCredit(0.0);
			orderReleaseEntry.setEmail("spsautomation@myntra.com");
			orderReleaseEntry.setEmiCharge(0.0);
			orderReleaseEntry.setFinalAmount(finalAmount); //
			orderReleaseEntry.setGift(gift);
			orderReleaseEntry.setGiftCardAmount(giftCardAmount);
			orderReleaseEntry.setGiftCharge(giftCharge);
			orderReleaseEntry.setLocality("Bangalore");
			orderReleaseEntry.setLogin("spsautomation@myntra.com");
			orderReleaseEntry.setLoyaltyConversionFactor(0.0);
			orderReleaseEntry.setLoyaltyPointsCredit(0.0);
			orderReleaseEntry.setLoyaltyPointsUsed(loyaltyPointsUsed);
			orderReleaseEntry.setMobile("9191919191");
			orderReleaseEntry.setMrpTotal(mrpTotal);
			orderReleaseEntry.setOnHold(false);
			orderReleaseEntry.setOrderId(orderId);
			orderReleaseEntry.setPaymentMethod(setPaymentMethod);
			orderReleaseEntry.setPaymentMethodDisplay("Credit card");
			orderReleaseEntry.setPersonalized(false);
			orderReleaseEntry.setPgDiscount(0.0);
			orderReleaseEntry.setReceiverName("sps automation");
			orderReleaseEntry.setRefunded(false);
			orderReleaseEntry.setShippingCharge(ShippingCharge);
			orderReleaseEntry.setShippingMethod("Normal");
			orderReleaseEntry.setSingleItemRelease(false);
			orderReleaseEntry.setSpecialPincode(false);
			orderReleaseEntry.setState("KA");
			orderReleaseEntry.setStatus(status);
			orderReleaseEntry.setStatusDisplayName(statusDisplay);
			orderReleaseEntry.setStoreId((long)(1));
			orderReleaseEntry.setStoreOrderId(String.valueOf(store_order_id));
			orderReleaseEntry.setStoredCredit(0.0);
			orderReleaseEntry.setTaxAmount(0.0);
			orderReleaseEntry.setTrackingNo("ML" + randomGenn(7));
			orderReleaseEntry.setUserContactNo("9191919191");
			orderReleaseEntry.setZipcode("560068");
			orderReleaseEntry.setPaymentPpsId(paymentPpsId);

			// ------------------Set order line
			orderLineEntry.setCreatedBy("sps automation");
			orderLineEntry.setCreatedOn(date);
			orderLineEntry.setId(lineId);// lineId long
			orderLineEntry.setLastModifiedOn(date);
			orderLineEntry.setCartDiscount(0.0);
			orderLineEntry.setCashRedeemed(lineCashRedeemed);
			orderLineEntry.setCashbackOffered(0.0);
			orderLineEntry.setCouponDiscount(couponDiscount);
			orderLineEntry.setCustomizedMessage("Test");
			orderLineEntry.setDiscount(0.0);
			orderLineEntry.setDiscountRuleId(0);
			orderLineEntry.setDiscountRuleRevId(0);
			orderLineEntry.setDiscountedProduct(false);
			orderLineEntry.setDiscountedQuantity(0);
			orderLineEntry.setEarnedCredit(0.0);
			orderLineEntry.setFinalAmount(lineFinalAmount);
			orderLineEntry.setFragile(false);
			orderLineEntry.setGiftCardAmount(lineGiftCardAmount);
			orderLineEntry.setGovtTaxAmount(0.0);
			orderLineEntry.setGovtTaxRate(5.0);
			orderLineEntry.setHazMat(false);
			orderLineEntry.setIsCustomizable(false);
			orderLineEntry.setIsDiscountedProduct(false);
			orderLineEntry.setIsExchangeableProduct(isExchangable);
			orderLineEntry.setIsReturnableProduct(true);
			orderLineEntry.setIsFragile(false);
			orderLineEntry.setIsJewellery(false);
			orderLineEntry.setJewellery(false);
			orderLineEntry.setLoyaltyConversionFactor(0.0);
			orderLineEntry.setLoyaltyPointsAwarded(0.0);
			orderLineEntry.setLoyaltyPointsCredit(0.0);
			orderLineEntry.setLoyaltyPointsUsed(lineLoyaltyPointsUsed);
			orderLineEntry.setOptionId(optionId);
			orderLineEntry.setOrderId(orderId);
			orderLineEntry.setOrderReleaseId(orderReleaseId);
			orderLineEntry.setPackagingStatus("NOT_PACKAGED");
			orderLineEntry.setPackagingType("NORMAL");
			orderLineEntry.setPgDiscount(0.0);
			orderLineEntry.setPoStatus("UNUSED");
			orderLineEntry.setQuantity(lineQuantity);
			orderLineEntry.setSellerId(sellerId);
			orderLineEntry.setSkuId(skuId);
			orderLineEntry.setStatus(lineStatus);
			orderLineEntry.setStatusDisplayName(lineStatusDisplayName);
			orderLineEntry.setStoredCredit(0.0);
			orderLineEntry.setStoreOrderId(String.valueOf(store_order_id));
			orderLineEntry.setStyleId(styleId);
			orderLineEntry.setSupplyType(supplyType);
			orderLineEntry.setTaxAmount(0.0);
			orderLineEntry.setTaxRate(5.0);
			orderLineEntry.setUnitPrice(unitPrice);
			
			
			// ------------------Set order line1
			orderLineEntry.setCreatedBy("sps automation");
			orderLineEntry.setCreatedOn(date);
			orderLineEntry.setId(lineId1);// lineId long
			orderLineEntry.setLastModifiedOn(date);
			orderLineEntry.setCartDiscount(0.0);
			orderLineEntry.setCashRedeemed(lineCashRedeemed1);
			orderLineEntry.setCashbackOffered(0.0);
			orderLineEntry.setCouponDiscount(couponDiscount1);
			orderLineEntry.setCustomizedMessage("Test");
			orderLineEntry.setDiscount(0.0);
			orderLineEntry.setDiscountRuleId(0);
			orderLineEntry.setDiscountRuleRevId(0);
			orderLineEntry.setDiscountedProduct(false);
			orderLineEntry.setDiscountedQuantity(0);
			orderLineEntry.setEarnedCredit(0.0);
			orderLineEntry.setFinalAmount(lineFinalAmount1);
			orderLineEntry.setFragile(false);
			orderLineEntry.setGiftCardAmount(lineGiftCardAmount1);
			orderLineEntry.setGovtTaxAmount(0.0);
			orderLineEntry.setGovtTaxRate(5.0);
			orderLineEntry.setHazMat(false);
			orderLineEntry.setIsCustomizable(false);
			orderLineEntry.setIsDiscountedProduct(false);
			orderLineEntry.setIsExchangeableProduct(isExchangable1);
			orderLineEntry.setIsReturnableProduct(true);
			orderLineEntry.setIsFragile(false);
			orderLineEntry.setIsJewellery(false);
			orderLineEntry.setJewellery(false);
			orderLineEntry.setLoyaltyConversionFactor(0.0);
			orderLineEntry.setLoyaltyPointsAwarded(0.0);
			orderLineEntry.setLoyaltyPointsCredit(0.0);
			orderLineEntry.setLoyaltyPointsUsed(lineLoyaltyPointsUsed1);
			orderLineEntry.setOptionId(optionId1);
			orderLineEntry.setOrderId(orderId);
			orderLineEntry.setOrderReleaseId(orderReleaseId1);
			orderLineEntry.setPackagingStatus("NOT_PACKAGED");
			orderLineEntry.setPackagingType("NORMAL");
			orderLineEntry.setPgDiscount(0.0);
			orderLineEntry.setPoStatus("UNUSED");
			orderLineEntry.setQuantity(lineQuantity1);
			orderLineEntry.setSellerId(sellerId1);
			orderLineEntry.setSkuId(skuId1);
			orderLineEntry.setStatus(lineStatus1);
			orderLineEntry.setStatusDisplayName(lineStatusDisplayName1);
			orderLineEntry.setStoredCredit(0.0);
			orderLineEntry.setStoreOrderId(String.valueOf(store_order_id));
			orderLineEntry.setStyleId(styleId1);
			orderLineEntry.setSupplyType(supplyType1);
			orderLineEntry.setTaxAmount(0.0);
			orderLineEntry.setTaxRate(5.0);
			orderLineEntry.setUnitPrice(unitPrice1);
			
						List<OrderLineEntry> orderLineEntries = new ArrayList<OrderLineEntry>();
			orderLineEntries.add(orderLineEntry);

			orderReleaseEntry.setOrderLines(orderLineEntries);
			releaseUpdateEventEntry.setOldEntry(orderReleaseEntry);
			releaseUpdateEventEntry.setUpdatedEntry(orderReleaseEntry);

			String message = APIUtilities.convertXMLObjectToString(releaseUpdateEventEntry);
			return message;
		}		
		
		//Create Packet with three release
				public String OMSMessageCreationReleaseID(long orderId, String store_order_id, String status, String statusDisplay, double mrpTotal, double finalAmount, double ShippingCharge, boolean gift, double giftCardAmount,
						double giftCharge, double loyaltyPointsUsed, double cashRedeemed, 
						long lineId, double couponDiscount, double lineFinalAmount, double lineGiftCardAmount, boolean isExchangable, double lineLoyaltyPointsUsed, long optionId,
						long orderReleaseId, int lineQuantity, long sellerId, long skuId, String lineStatus, String lineStatusDisplayName, long styleId, String supplyType, double unitPrice, double lineCashRedeemed, 
						long lineId1, double couponDiscount1, double lineFinalAmount1, double lineGiftCardAmount1, boolean isExchangable1, double lineLoyaltyPointsUsed1, long optionId1,long orderReleaseId1, int lineQuantity1, 
						long sellerId1, long skuId1, String lineStatus1, String lineStatusDisplayName1, long styleId1, String supplyType1, double unitPrice1, double lineCashRedeemed1,
						long lineId2, double couponDiscount2, double lineFinalAmount2, double lineGiftCardAmount2, boolean isExchangable2, double lineLoyaltyPointsUsed2, long optionId2,long orderReleaseId2, int lineQuantity2, 
						long sellerId2, long skuId2, String lineStatus2, String lineStatusDisplayName2, long styleId2, String supplyType2, double unitPrice2, double lineCashRedeemed2,String paymentPpsId,String paymentMethod) throws JAXBException {
					Date date = new Date();
					OrderReleaseEntry orderReleaseEntry = new OrderReleaseEntry();// old and update entry
					OrderLineEntry orderLineEntry = new OrderLineEntry();
					ReleaseUpdateEventEntry releaseUpdateEventEntry = new ReleaseUpdateEventEntry();

					
					orderReleaseEntry.setAddress("xyz");
					releaseUpdateEventEntry.setEntityId(orderReleaseId);
					releaseUpdateEventEntry.setEventTime(date);
					orderReleaseEntry.setOrderDate(date);

					// ------------------set old/update entry
					orderReleaseEntry.setCreatedBy("sps automation");
					orderReleaseEntry.setCreatedOn(date);
					orderReleaseEntry.setId(orderReleaseId); //
					orderReleaseEntry.setLastModifiedOn(date);
					orderReleaseEntry.setAddress("Myntra design, AKR tech park, Near kudlu gate");
					orderReleaseEntry.setAddressId((long) 6118996);
					orderReleaseEntry.setCartDiscount(0.0);
					orderReleaseEntry.setCashRedeemed(cashRedeemed);
					orderReleaseEntry.setCity("Bangalore");
					orderReleaseEntry.setCodCharge(0.0);
					orderReleaseEntry.setCodPaymentStatus("pending");
					orderReleaseEntry.setCountry("India");
					orderReleaseEntry.setCouponDiscount(couponDiscount);
					orderReleaseEntry.setDiscount(0.0);
					orderReleaseEntry.setEarnedCredit(0.0);
					orderReleaseEntry.setEmail("spsautomation@myntra.com");
					orderReleaseEntry.setEmiCharge(0.0);
					orderReleaseEntry.setFinalAmount(finalAmount); //
					orderReleaseEntry.setGift(gift);
					orderReleaseEntry.setGiftCardAmount(giftCardAmount);
					orderReleaseEntry.setGiftCharge(giftCharge);
					orderReleaseEntry.setLocality("Bangalore");
					orderReleaseEntry.setLogin("spsautomation@myntra.com");
					orderReleaseEntry.setLoyaltyConversionFactor(0.0);
					orderReleaseEntry.setLoyaltyPointsCredit(0.0);
					orderReleaseEntry.setLoyaltyPointsUsed(loyaltyPointsUsed);
					orderReleaseEntry.setMobile("9191919191");
					orderReleaseEntry.setMrpTotal(mrpTotal);
					orderReleaseEntry.setOnHold(false);
					orderReleaseEntry.setOrderId(orderId);
					orderReleaseEntry.setPaymentMethod(paymentMethod);
					orderReleaseEntry.setPaymentMethodDisplay("Credit card");
					orderReleaseEntry.setPersonalized(false);
					orderReleaseEntry.setPgDiscount(0.0);
					orderReleaseEntry.setReceiverName("sps automation");
					orderReleaseEntry.setRefunded(false);
					orderReleaseEntry.setShippingCharge(ShippingCharge);
					orderReleaseEntry.setShippingMethod("Normal");
					orderReleaseEntry.setSingleItemRelease(false);
					orderReleaseEntry.setSpecialPincode(false);
					orderReleaseEntry.setState("KA");
					orderReleaseEntry.setStatus(status);
					orderReleaseEntry.setStatusDisplayName(statusDisplay);
					orderReleaseEntry.setStoreId((long) 1);
					orderReleaseEntry.setStoreOrderId(String.valueOf(store_order_id));
					orderReleaseEntry.setStoredCredit(0.0);
					orderReleaseEntry.setTaxAmount(0.0);
					orderReleaseEntry.setTrackingNo("ML" + randomGenn(7));
					orderReleaseEntry.setUserContactNo("9191919191");
					orderReleaseEntry.setZipcode("560068");
					orderReleaseEntry.setPaymentPpsId(paymentPpsId);

					// ------------------Set order line 0
					orderLineEntry.setCreatedBy("sps automation");
					orderLineEntry.setCreatedOn(date);
					orderLineEntry.setId(lineId);// lineId long
					orderLineEntry.setLastModifiedOn(date);
					orderLineEntry.setCartDiscount(0.0);
					orderLineEntry.setCashRedeemed(lineCashRedeemed);
					orderLineEntry.setCashbackOffered(0.0);
					orderLineEntry.setCouponDiscount(couponDiscount);
					orderLineEntry.setCustomizedMessage("Test");
					orderLineEntry.setDiscount(0.0);
					orderLineEntry.setDiscountRuleId(0);
					orderLineEntry.setDiscountRuleRevId(0);
					orderLineEntry.setDiscountedProduct(false);
					orderLineEntry.setDiscountedQuantity(0);
					orderLineEntry.setEarnedCredit(0.0);
					orderLineEntry.setFinalAmount(lineFinalAmount);
					orderLineEntry.setFragile(false);
					orderLineEntry.setGiftCardAmount(lineGiftCardAmount);
					orderLineEntry.setGovtTaxAmount(0.0);
					orderLineEntry.setGovtTaxRate(5.0);
					orderLineEntry.setHazMat(false);
					orderLineEntry.setIsCustomizable(false);
					orderLineEntry.setIsDiscountedProduct(false);
					orderLineEntry.setIsExchangeableProduct(isExchangable);
					orderLineEntry.setIsReturnableProduct(true);
					orderLineEntry.setIsFragile(false);
					orderLineEntry.setIsJewellery(false);
					orderLineEntry.setJewellery(false);
					orderLineEntry.setLoyaltyConversionFactor(0.0);
					orderLineEntry.setLoyaltyPointsAwarded(0.0);
					orderLineEntry.setLoyaltyPointsCredit(0.0);
					orderLineEntry.setLoyaltyPointsUsed(lineLoyaltyPointsUsed);
					orderLineEntry.setOptionId(optionId);
					orderLineEntry.setOrderId(orderId);
					orderLineEntry.setOrderReleaseId(orderReleaseId);
					orderLineEntry.setPackagingStatus("NOT_PACKAGED");
					orderLineEntry.setPackagingType("NORMAL");
					orderLineEntry.setPgDiscount(0.0);
					orderLineEntry.setPoStatus("UNUSED");
					orderLineEntry.setQuantity(lineQuantity);
					orderLineEntry.setSellerId(sellerId);
					orderLineEntry.setSkuId(skuId);
					orderLineEntry.setStatus(lineStatus);
					orderLineEntry.setStatusDisplayName(lineStatusDisplayName);
					orderLineEntry.setStoredCredit(0.0);
					orderLineEntry.setStoreOrderId(String.valueOf(store_order_id));
					orderLineEntry.setStyleId(styleId);
					orderLineEntry.setSupplyType(supplyType);
					orderLineEntry.setTaxAmount(0.0);
					orderLineEntry.setTaxRate(5.0);
					orderLineEntry.setUnitPrice(unitPrice);
					
					
					// ------------------Set order line1
					orderLineEntry.setCreatedBy("sps automation");
					orderLineEntry.setCreatedOn(date);
					orderLineEntry.setId(lineId1);// lineId long
					orderLineEntry.setLastModifiedOn(date);
					orderLineEntry.setCartDiscount(0.0);
					orderLineEntry.setCashRedeemed(lineCashRedeemed1);
					orderLineEntry.setCashbackOffered(0.0);
					orderLineEntry.setCouponDiscount(couponDiscount1);
					orderLineEntry.setCustomizedMessage("Test");
					orderLineEntry.setDiscount(0.0);
					orderLineEntry.setDiscountRuleId(0);
					orderLineEntry.setDiscountRuleRevId(0);
					orderLineEntry.setDiscountedProduct(false);
					orderLineEntry.setDiscountedQuantity(0);
					orderLineEntry.setEarnedCredit(0.0);
					orderLineEntry.setFinalAmount(lineFinalAmount1);
					orderLineEntry.setFragile(false);
					orderLineEntry.setGiftCardAmount(lineGiftCardAmount1);
					orderLineEntry.setGovtTaxAmount(0.0);
					orderLineEntry.setGovtTaxRate(5.0);
					orderLineEntry.setHazMat(false);
					orderLineEntry.setIsCustomizable(false);
					orderLineEntry.setIsDiscountedProduct(false);
					orderLineEntry.setIsExchangeableProduct(isExchangable1);
					orderLineEntry.setIsReturnableProduct(true);
					orderLineEntry.setIsFragile(false);
					orderLineEntry.setIsJewellery(false);
					orderLineEntry.setJewellery(false);
					orderLineEntry.setLoyaltyConversionFactor(0.0);
					orderLineEntry.setLoyaltyPointsAwarded(0.0);
					orderLineEntry.setLoyaltyPointsCredit(0.0);
					orderLineEntry.setLoyaltyPointsUsed(lineLoyaltyPointsUsed1);
					orderLineEntry.setOptionId(optionId1);
					orderLineEntry.setOrderId(orderId);
					orderLineEntry.setOrderReleaseId(orderReleaseId1);
					orderLineEntry.setPackagingStatus("NOT_PACKAGED");
					orderLineEntry.setPackagingType("NORMAL");
					orderLineEntry.setPgDiscount(0.0);
					orderLineEntry.setPoStatus("UNUSED");
					orderLineEntry.setQuantity(lineQuantity1);
					orderLineEntry.setSellerId(sellerId1);
					orderLineEntry.setSkuId(skuId1);
					orderLineEntry.setStatus(lineStatus1);
					orderLineEntry.setStatusDisplayName(lineStatusDisplayName1);
					orderLineEntry.setStoredCredit(0.0);
					orderLineEntry.setStoreOrderId(String.valueOf(store_order_id));
					orderLineEntry.setStyleId(styleId1);
					orderLineEntry.setSupplyType(supplyType1);
					orderLineEntry.setTaxAmount(0.0);
					orderLineEntry.setTaxRate(5.0);
					orderLineEntry.setUnitPrice(unitPrice1);
					
					// ------------------Set order line2
					orderLineEntry.setCreatedBy("sps automation");
					orderLineEntry.setCreatedOn(date);
					orderLineEntry.setId(lineId2);// lineId long
					orderLineEntry.setLastModifiedOn(date);
					orderLineEntry.setCartDiscount(0.0);
					orderLineEntry.setCashRedeemed(lineCashRedeemed2);
					orderLineEntry.setCashbackOffered(0.0);
					orderLineEntry.setCouponDiscount(couponDiscount2);
					orderLineEntry.setCustomizedMessage("Test");
					orderLineEntry.setDiscount(0.0);
					orderLineEntry.setDiscountRuleId(0);
					orderLineEntry.setDiscountRuleRevId(0);
					orderLineEntry.setDiscountedProduct(false);
					orderLineEntry.setDiscountedQuantity(0);
					orderLineEntry.setEarnedCredit(0.0);
					orderLineEntry.setFinalAmount(lineFinalAmount2);
					orderLineEntry.setFragile(false);
					orderLineEntry.setGiftCardAmount(lineGiftCardAmount2);
					orderLineEntry.setGovtTaxAmount(0.0);
					orderLineEntry.setGovtTaxRate(5.0);
					orderLineEntry.setHazMat(false);
					orderLineEntry.setIsCustomizable(false);
					orderLineEntry.setIsDiscountedProduct(false);
					orderLineEntry.setIsExchangeableProduct(isExchangable2);
					orderLineEntry.setIsReturnableProduct(true);
					orderLineEntry.setIsFragile(false);
					orderLineEntry.setIsJewellery(false);
					orderLineEntry.setJewellery(false);
					orderLineEntry.setLoyaltyConversionFactor(0.0);
					orderLineEntry.setLoyaltyPointsAwarded(0.0);
					orderLineEntry.setLoyaltyPointsCredit(0.0);
					orderLineEntry.setLoyaltyPointsUsed(lineLoyaltyPointsUsed2);
					orderLineEntry.setOptionId(optionId2);
					orderLineEntry.setOrderId(orderId);
					orderLineEntry.setOrderReleaseId(orderReleaseId2);
					orderLineEntry.setPackagingStatus("NOT_PACKAGED");
					orderLineEntry.setPackagingType("NORMAL");
					orderLineEntry.setPgDiscount(0.0);
					orderLineEntry.setPoStatus("UNUSED");
					orderLineEntry.setQuantity(lineQuantity2);
					orderLineEntry.setSellerId(sellerId2);
					orderLineEntry.setSkuId(skuId2);
					orderLineEntry.setStatus(lineStatus2);
					orderLineEntry.setStatusDisplayName(lineStatusDisplayName2);
					orderLineEntry.setStoredCredit(0.0);
					orderLineEntry.setStoreOrderId(String.valueOf(store_order_id));
					orderLineEntry.setStyleId(styleId2);
					orderLineEntry.setSupplyType(supplyType2);
					orderLineEntry.setTaxAmount(0.0);
					orderLineEntry.setTaxRate(5.0);
					orderLineEntry.setUnitPrice(unitPrice2);
					List<OrderLineEntry> orderLineEntries = new ArrayList<OrderLineEntry>();
					orderLineEntries.add(orderLineEntry);

					orderReleaseEntry.setOrderLines(orderLineEntries);
					releaseUpdateEventEntry.setOldEntry(orderReleaseEntry);
					releaseUpdateEventEntry.setUpdatedEntry(orderReleaseEntry);

					String message = APIUtilities.convertXMLObjectToString(releaseUpdateEventEntry);
					return message;
				}
	
	
	 //Create Packet with two release
	public String OMSMessageCreation(long orderId, String store_order_id, String status, String statusDisplay, double mrpTotal, double finalAmount, double ShippingCharge, boolean gift, double giftCardAmount,
				double giftCharge, double loyaltyPointsUsed, double cashRedeemed, 
				long lineId, double couponDiscount, double lineFinalAmount, double lineGiftCardAmount, boolean isExchangable, double lineLoyaltyPointsUsed, long optionId,
				long orderReleaseId, int lineQuantity, long sellerId, long skuId, String lineStatus, String lineStatusDisplayName, long styleId, String supplyType, double unitPrice, double lineCashRedeemed,
				long lineId1, double couponDiscount1, double lineFinalAmount1, double lineGiftCardAmount1, boolean isExchangable1, double lineLoyaltyPointsUsed1, long optionId1,long orderReleaseId1, int lineQuantity1, 
				long sellerId1, long skuId1, String lineStatus1, String lineStatusDisplayName1, long styleId1, String supplyType1, double unitPrice1, double lineCashRedeemed1,String paymentPpsId,String paymentMethod) throws JAXBException {
			Date date = new Date();
			OrderReleaseEntry orderReleaseEntry = new OrderReleaseEntry();// old and update entry
			OrderLineEntry orderLineEntry = new OrderLineEntry();
			OrderLineEntry orderLineEntry1 = new OrderLineEntry();
			ReleaseUpdateEventEntry releaseUpdateEventEntry = new ReleaseUpdateEventEntry();

			
			orderReleaseEntry.setAddress("xyz");
			releaseUpdateEventEntry.setEntityId(orderId);
			releaseUpdateEventEntry.setEventTime(date);
			orderReleaseEntry.setOrderDate(date);

			// ------------------set old/update entry
			orderReleaseEntry.setCreatedBy("sps automation");
			orderReleaseEntry.setCreatedOn(date);
			//orderReleaseEntry.setId(orderId); //
			orderReleaseEntry.setId(orderReleaseId); //
			orderReleaseEntry.setLastModifiedOn(date);
			orderReleaseEntry.setAddress("Myntra design, AKR tech park, Near kudlu gate");
			orderReleaseEntry.setAddressId((long) 6118996);
			orderReleaseEntry.setCartDiscount(0.0);
			orderReleaseEntry.setCashRedeemed(cashRedeemed);
			orderReleaseEntry.setCity("Bangalore");
			orderReleaseEntry.setCodCharge(0.0);
			orderReleaseEntry.setCodPaymentStatus("pending");
			orderReleaseEntry.setCountry("India");
			orderReleaseEntry.setCouponDiscount(0.0);
			orderReleaseEntry.setDiscount(0.0);
			orderReleaseEntry.setEarnedCredit(0.0);
			orderReleaseEntry.setEmail("spsautomation@myntra.com");
			orderReleaseEntry.setEmiCharge(0.0);
			orderReleaseEntry.setFinalAmount(finalAmount); //
			orderReleaseEntry.setGift(gift);
			orderReleaseEntry.setGiftCardAmount(giftCardAmount);
			orderReleaseEntry.setGiftCharge(giftCharge);
			orderReleaseEntry.setLocality("Bangalore");
			orderReleaseEntry.setLogin("spsautomation@myntra.com");
			orderReleaseEntry.setLoyaltyConversionFactor(0.0);
			orderReleaseEntry.setLoyaltyPointsCredit(0.0);
			orderReleaseEntry.setLoyaltyPointsUsed(loyaltyPointsUsed);
			orderReleaseEntry.setMobile("9191919191");
			orderReleaseEntry.setMrpTotal(mrpTotal);
			orderReleaseEntry.setOnHold(false);
			orderReleaseEntry.setOrderId(orderId);
			orderReleaseEntry.setPaymentMethod(paymentMethod);
			orderReleaseEntry.setPaymentMethodDisplay("Credit card");
			orderReleaseEntry.setPersonalized(false);
			orderReleaseEntry.setPgDiscount(0.0);
			orderReleaseEntry.setReceiverName("sps automation");
			orderReleaseEntry.setRefunded(false);
			orderReleaseEntry.setShippingCharge(ShippingCharge);
			orderReleaseEntry.setShippingMethod("Normal");
			orderReleaseEntry.setSingleItemRelease(false);
			orderReleaseEntry.setSpecialPincode(false);
			orderReleaseEntry.setState("KA");
			orderReleaseEntry.setStatus(status);
			orderReleaseEntry.setStatusDisplayName(statusDisplay);
			orderReleaseEntry.setStoreId((long) 1);
			orderReleaseEntry.setStoreOrderId(String.valueOf(store_order_id));
			orderReleaseEntry.setStoredCredit(0.0);
			orderReleaseEntry.setTaxAmount(0.0);
			orderReleaseEntry.setTrackingNo("ML" + randomGenn(7));
			orderReleaseEntry.setUserContactNo("9191919191");
			orderReleaseEntry.setZipcode("560068");
			orderReleaseEntry.setPaymentPpsId(paymentPpsId);
			orderReleaseEntry.setDeliveredOn(date);
			orderReleaseEntry.setPackedOn(date);

			// ------------------Set order line 0
			orderLineEntry.setCreatedBy("sps automation");
			orderLineEntry.setCreatedOn(date);
			orderLineEntry.setId(lineId);// lineId long
			orderLineEntry.setLastModifiedOn(date);
			orderLineEntry.setCartDiscount(0.0);
			orderLineEntry.setCashRedeemed(lineCashRedeemed);
			orderLineEntry.setCashbackOffered(0.0);
			orderLineEntry.setCouponDiscount(couponDiscount);
			orderLineEntry.setCustomizedMessage("Test");
			orderLineEntry.setDiscount(0.0);
			orderLineEntry.setDiscountRuleId(0);
			orderLineEntry.setDiscountRuleRevId(0);
			orderLineEntry.setDiscountedProduct(false);
			orderLineEntry.setDiscountedQuantity(0);
			orderLineEntry.setEarnedCredit(0.0);
			orderLineEntry.setFinalAmount(lineFinalAmount);
			orderLineEntry.setFragile(false);
			orderLineEntry.setGiftCardAmount(lineGiftCardAmount);
			orderLineEntry.setGovtTaxAmount(0.0);
			orderLineEntry.setGovtTaxRate(5.0);
			orderLineEntry.setHazMat(false);
			orderLineEntry.setIsCustomizable(false);
			orderLineEntry.setIsDiscountedProduct(false);
			orderLineEntry.setIsExchangeableProduct(isExchangable);
			orderLineEntry.setIsReturnableProduct(true);
			orderLineEntry.setIsFragile(false);
			orderLineEntry.setIsJewellery(false);
			orderLineEntry.setJewellery(false);
			orderLineEntry.setLoyaltyConversionFactor(0.0);
			orderLineEntry.setLoyaltyPointsAwarded(0.0);
			orderLineEntry.setLoyaltyPointsCredit(0.0);
			orderLineEntry.setLoyaltyPointsUsed(lineLoyaltyPointsUsed);
			orderLineEntry.setOptionId(optionId);
			orderLineEntry.setOrderId(orderId);
			orderLineEntry.setOrderReleaseId(orderReleaseId);
			orderLineEntry.setPackagingStatus("NOT_PACKAGED");
			orderLineEntry.setPackagingType("NORMAL");
			orderLineEntry.setPgDiscount(0.0);
			orderLineEntry.setPoStatus("UNUSED");
			orderLineEntry.setQuantity(lineQuantity);
			orderLineEntry.setSellerId(sellerId);
			orderLineEntry.setSkuId(skuId);
			orderLineEntry.setStatus(lineStatus);
			orderLineEntry.setStatusDisplayName(lineStatusDisplayName);
			orderLineEntry.setStoredCredit(0.0);
			orderLineEntry.setStoreLineId(String.valueOf(orderId));
			orderLineEntry.setStoreOrderId(String.valueOf(store_order_id));
			orderLineEntry.setStyleId(styleId);
			orderLineEntry.setSupplyType(supplyType);
			orderLineEntry.setTaxAmount(0.0);
			orderLineEntry.setTaxRate(5.0);
			orderLineEntry.setUnitPrice(unitPrice);
			
			// ------------------Set order line 1
						orderLineEntry1.setCreatedBy("sps automation");
						orderLineEntry1.setCreatedOn(date);
						orderLineEntry1.setId(lineId1);// lineId long
						orderLineEntry1.setLastModifiedOn(date);
						orderLineEntry1.setCartDiscount(0.0);
						orderLineEntry1.setCashRedeemed(lineCashRedeemed1);
						orderLineEntry1.setCashbackOffered(0.0);
						orderLineEntry1.setCouponDiscount(couponDiscount1);
						orderLineEntry1.setCustomizedMessage("Test");
						orderLineEntry1.setDiscount(0.0);
						orderLineEntry1.setDiscountRuleId(0);
						orderLineEntry1.setDiscountRuleRevId(0);
						orderLineEntry1.setDiscountedProduct(false);
						orderLineEntry1.setDiscountedQuantity(0);
						orderLineEntry1.setEarnedCredit(0.0);
						orderLineEntry1.setFinalAmount(lineFinalAmount1);
						orderLineEntry1.setFragile(false);
						orderLineEntry1.setGiftCardAmount(lineGiftCardAmount1);
						orderLineEntry1.setGovtTaxAmount(0.0);
						orderLineEntry1.setGovtTaxRate(5.0);
						orderLineEntry1.setHazMat(false);
						orderLineEntry1.setIsCustomizable(false);
						orderLineEntry1.setIsDiscountedProduct(false);
						orderLineEntry1.setIsExchangeableProduct(isExchangable1);
						orderLineEntry1.setIsReturnableProduct(true);
						orderLineEntry1.setIsFragile(false);
						orderLineEntry1.setIsJewellery(false);
						orderLineEntry1.setJewellery(false);
						orderLineEntry1.setLoyaltyConversionFactor(0.0);
						orderLineEntry1.setLoyaltyPointsAwarded(0.0);
						orderLineEntry1.setLoyaltyPointsCredit(0.0);
						orderLineEntry1.setLoyaltyPointsUsed(lineLoyaltyPointsUsed1);
						orderLineEntry1.setOptionId(optionId1);
						orderLineEntry1.setOrderId(orderId);
						orderLineEntry1.setOrderReleaseId(orderReleaseId1);
						orderLineEntry1.setPackagingStatus("NOT_PACKAGED");
						orderLineEntry1.setPackagingType("NORMAL");
						orderLineEntry1.setPgDiscount(0.0);
						orderLineEntry1.setPoStatus("UNUSED");
						orderLineEntry1.setQuantity(lineQuantity1);
						orderLineEntry1.setSellerId(sellerId1);
						orderLineEntry1.setSkuId(skuId1);
						orderLineEntry1.setStatus(lineStatus1);
						orderLineEntry1.setStatusDisplayName(lineStatusDisplayName1);
						orderLineEntry1.setStoredCredit(0.0);
						orderLineEntry1.setStoreLineId(String.valueOf(orderId));
						orderLineEntry1.setStoreOrderId(String.valueOf(store_order_id));
						orderLineEntry1.setStyleId(styleId1);
						orderLineEntry1.setSupplyType(supplyType1);
						orderLineEntry1.setTaxAmount(0.0);
						orderLineEntry1.setTaxRate(5.0);
						orderLineEntry1.setUnitPrice(unitPrice1);
			List<OrderLineEntry> orderLineEntries = new ArrayList<OrderLineEntry>();
			orderLineEntries.add(orderLineEntry);
			orderLineEntries.add(orderLineEntry1);

			orderReleaseEntry.setOrderLines(orderLineEntries);
			releaseUpdateEventEntry.setOldEntry(orderReleaseEntry);
			releaseUpdateEventEntry.setUpdatedEntry(orderReleaseEntry);

			String message = APIUtilities.convertXMLObjectToString(releaseUpdateEventEntry);
			return message;
		}
	

	//Create Packet with three release
	public String OMSMessageCreation(long orderId,String store_order_id, String status, String statusDisplay, double mrpTotal, double finalAmount, double ShippingCharge, boolean gift, double giftCardAmount,
				double giftCharge, double loyaltyPointsUsed, double cashRedeemed, 
				long lineId, double couponDiscount, double lineFinalAmount, double lineGiftCardAmount, boolean isExchangable, double lineLoyaltyPointsUsed, long optionId,
				long orderReleaseId, int lineQuantity, long sellerId, long skuId, String lineStatus, String lineStatusDisplayName, long styleId, String supplyType, double unitPrice, double lineCashRedeemed, 
				long lineId1, double couponDiscount1, double lineFinalAmount1, double lineGiftCardAmount1, boolean isExchangable1, double lineLoyaltyPointsUsed1, long optionId1,long orderReleaseId1, int lineQuantity1, 
				long sellerId1, long skuId1, String lineStatus1, String lineStatusDisplayName1, long styleId1, String supplyType1, double unitPrice1, double lineCashRedeemed1,
				long lineId2, double couponDiscount2, double lineFinalAmount2, double lineGiftCardAmount2, boolean isExchangable2, double lineLoyaltyPointsUsed2, long optionId2,long orderReleaseId2, int lineQuantity2, 
				long sellerId2, long skuId2, String lineStatus2, String lineStatusDisplayName2, long styleId2, String supplyType2, double unitPrice2, double lineCashRedeemed2,String paymentPpsId,String paymentMethod) throws JAXBException {
			Date date = new Date();
			OrderReleaseEntry orderReleaseEntry = new OrderReleaseEntry();// old and update entry
			OrderLineEntry orderLineEntry = new OrderLineEntry();
			OrderLineEntry orderLineEntry1 = new OrderLineEntry();
			OrderLineEntry orderLineEntry2 = new OrderLineEntry();
			ReleaseUpdateEventEntry releaseUpdateEventEntry = new ReleaseUpdateEventEntry();

			
			orderReleaseEntry.setAddress("xyz");
			releaseUpdateEventEntry.setEntityId(orderId);
			releaseUpdateEventEntry.setEventTime(date);
			orderReleaseEntry.setOrderDate(date);

			// ------------------set old/update entry
			orderReleaseEntry.setCreatedBy("sps automation");
			orderReleaseEntry.setCreatedOn(date);
			orderReleaseEntry.setId(orderReleaseId); //
			orderReleaseEntry.setLastModifiedOn(date);
			orderReleaseEntry.setAddress("Myntra design, AKR tech park, Near kudlu gate");
			orderReleaseEntry.setAddressId((long) 6118996);
			orderReleaseEntry.setCartDiscount(0.0);
			orderReleaseEntry.setCashRedeemed(cashRedeemed);
			orderReleaseEntry.setCity("Bangalore");
			orderReleaseEntry.setCodCharge(0.0);
			orderReleaseEntry.setCodPaymentStatus("pending");
			orderReleaseEntry.setCountry("India");
			orderReleaseEntry.setCouponDiscount(0.0);
			orderReleaseEntry.setDiscount(0.0);
			orderReleaseEntry.setEarnedCredit(0.0);
			orderReleaseEntry.setEmail("spsautomation@myntra.com");
			orderReleaseEntry.setEmiCharge(0.0);
			orderReleaseEntry.setFinalAmount(finalAmount); //
			orderReleaseEntry.setGift(gift);
			orderReleaseEntry.setGiftCardAmount(giftCardAmount);
			orderReleaseEntry.setGiftCharge(giftCharge);
			orderReleaseEntry.setLocality("Bangalore");
			orderReleaseEntry.setLogin("spsautomation@myntra.com");
			orderReleaseEntry.setLoyaltyConversionFactor(0.0);
			orderReleaseEntry.setLoyaltyPointsCredit(0.0);
			orderReleaseEntry.setLoyaltyPointsUsed(loyaltyPointsUsed);
			orderReleaseEntry.setMobile("9191919191");
			orderReleaseEntry.setMrpTotal(mrpTotal);
			orderReleaseEntry.setOnHold(false);
			orderReleaseEntry.setOrderId(orderId);
			orderReleaseEntry.setPaymentMethod(paymentMethod);
			orderReleaseEntry.setPaymentMethodDisplay("Credit card");
			orderReleaseEntry.setPersonalized(false);
			orderReleaseEntry.setPgDiscount(0.0);
			orderReleaseEntry.setReceiverName("sps automation");
			orderReleaseEntry.setRefunded(false);
			orderReleaseEntry.setShippingCharge(ShippingCharge);
			orderReleaseEntry.setShippingMethod("Normal");
			orderReleaseEntry.setSingleItemRelease(false);
			orderReleaseEntry.setSpecialPincode(false);
			orderReleaseEntry.setState("KA");
			orderReleaseEntry.setStatus(status);
			orderReleaseEntry.setStatusDisplayName(statusDisplay);
			orderReleaseEntry.setStoreId((long) 1);
			orderReleaseEntry.setStoreOrderId(String.valueOf(store_order_id));
			orderReleaseEntry.setStoredCredit(0.0);
			orderReleaseEntry.setTaxAmount(0.0);
			orderReleaseEntry.setTrackingNo("ML" + randomGenn(7));
			orderReleaseEntry.setUserContactNo("9191919191");
			orderReleaseEntry.setZipcode("560068");
			orderReleaseEntry.setPaymentPpsId(paymentPpsId);
			orderReleaseEntry.setPackedOn(date);

			// ------------------Set order line 0
			orderLineEntry.setCreatedBy("sps automation");
			orderLineEntry.setCreatedOn(date);
			orderLineEntry.setId(lineId);// lineId long
			orderLineEntry.setLastModifiedOn(date);
			orderLineEntry.setCartDiscount(0.0);
			orderLineEntry.setCashRedeemed(lineCashRedeemed);
			orderLineEntry.setCashbackOffered(0.0);
			orderLineEntry.setCouponDiscount(couponDiscount);
			orderLineEntry.setCustomizedMessage("Test");
			orderLineEntry.setDiscount(0.0);
			orderLineEntry.setDiscountRuleId(0);
			orderLineEntry.setDiscountRuleRevId(0);
			orderLineEntry.setDiscountedProduct(false);
			orderLineEntry.setDiscountedQuantity(0);
			orderLineEntry.setEarnedCredit(0.0);
			orderLineEntry.setFinalAmount(lineFinalAmount);
			orderLineEntry.setFragile(false);
			orderLineEntry.setGiftCardAmount(lineGiftCardAmount);
			orderLineEntry.setGovtTaxAmount(0.0);
			orderLineEntry.setGovtTaxRate(5.0);
			orderLineEntry.setHazMat(false);
			orderLineEntry.setIsCustomizable(false);
			orderLineEntry.setIsDiscountedProduct(false);
			orderLineEntry.setIsExchangeableProduct(isExchangable);
			orderLineEntry.setIsReturnableProduct(true);
			orderLineEntry.setIsFragile(false);
			orderLineEntry.setIsJewellery(false);
			orderLineEntry.setJewellery(false);
			orderLineEntry.setLoyaltyConversionFactor(0.0);
			orderLineEntry.setLoyaltyPointsAwarded(0.0);
			orderLineEntry.setLoyaltyPointsCredit(0.0);
			orderLineEntry.setLoyaltyPointsUsed(lineLoyaltyPointsUsed);
			orderLineEntry.setOptionId(optionId);
			orderLineEntry.setOrderId(orderId);
			orderLineEntry.setOrderReleaseId(orderReleaseId);
			orderLineEntry.setPackagingStatus("NOT_PACKAGED");
			orderLineEntry.setPackagingType("NORMAL");
			orderLineEntry.setPgDiscount(0.0);
			orderLineEntry.setPoStatus("UNUSED");
			orderLineEntry.setQuantity(lineQuantity);
			orderLineEntry.setSellerId(sellerId);
			orderLineEntry.setSkuId(skuId);
			orderLineEntry.setStatus(lineStatus);
			orderLineEntry.setStatusDisplayName(lineStatusDisplayName);
			orderLineEntry.setStoredCredit(0.0);
			orderLineEntry.setStoreOrderId(String.valueOf(store_order_id));
			orderLineEntry.setStyleId(styleId);
			orderLineEntry.setSupplyType(supplyType);
			orderLineEntry.setTaxAmount(0.0);
			orderLineEntry.setTaxRate(5.0);
			orderLineEntry.setUnitPrice(unitPrice);
			
			// ------------------Set order line 1
						orderLineEntry1.setCreatedBy("sps automation");
						orderLineEntry1.setCreatedOn(date);
						orderLineEntry1.setId(lineId1);// lineId long
						orderLineEntry1.setLastModifiedOn(date);
						orderLineEntry1.setCartDiscount(0.0);
						orderLineEntry1.setCashRedeemed(lineCashRedeemed1);
						orderLineEntry1.setCashbackOffered(0.0);
						orderLineEntry1.setCouponDiscount(couponDiscount1);
						orderLineEntry1.setCustomizedMessage("Test");
						orderLineEntry1.setDiscount(0.0);
						orderLineEntry1.setDiscountRuleId(0);
						orderLineEntry1.setDiscountRuleRevId(0);
						orderLineEntry1.setDiscountedProduct(false);
						orderLineEntry1.setDiscountedQuantity(0);
						orderLineEntry1.setEarnedCredit(0.0);
						orderLineEntry1.setFinalAmount(lineFinalAmount1);
						orderLineEntry1.setFragile(false);
						orderLineEntry1.setGiftCardAmount(lineGiftCardAmount1);
						orderLineEntry1.setGovtTaxAmount(0.0);
						orderLineEntry1.setGovtTaxRate(5.0);
						orderLineEntry1.setHazMat(false);
						orderLineEntry1.setIsCustomizable(false);
						orderLineEntry1.setIsDiscountedProduct(false);
						orderLineEntry1.setIsExchangeableProduct(isExchangable1);
						orderLineEntry1.setIsReturnableProduct(true);
						orderLineEntry1.setIsFragile(false);
						orderLineEntry1.setIsJewellery(false);
						orderLineEntry1.setJewellery(false);
						orderLineEntry1.setLoyaltyConversionFactor(0.0);
						orderLineEntry1.setLoyaltyPointsAwarded(0.0);
						orderLineEntry1.setLoyaltyPointsCredit(0.0);
						orderLineEntry1.setLoyaltyPointsUsed(lineLoyaltyPointsUsed1);
						orderLineEntry1.setOptionId(optionId1);
						orderLineEntry1.setOrderId(orderId);
						orderLineEntry1.setOrderReleaseId(orderReleaseId1);
						orderLineEntry1.setPackagingStatus("NOT_PACKAGED");
						orderLineEntry1.setPackagingType("NORMAL");
						orderLineEntry1.setPgDiscount(0.0);
						orderLineEntry1.setPoStatus("UNUSED");
						orderLineEntry1.setQuantity(lineQuantity1);
						orderLineEntry1.setSellerId(sellerId1);
						orderLineEntry1.setSkuId(skuId1);
						orderLineEntry1.setStatus(lineStatus1);
						orderLineEntry1.setStatusDisplayName(lineStatusDisplayName1);
						orderLineEntry1.setStoredCredit(0.0);
						orderLineEntry1.setStoreOrderId(String.valueOf(store_order_id));
						orderLineEntry1.setStyleId(styleId1);
						orderLineEntry1.setSupplyType(supplyType1);
						orderLineEntry1.setTaxAmount(0.0);
						orderLineEntry1.setTaxRate(5.0);
						orderLineEntry1.setUnitPrice(unitPrice1);
						
						// ------------------Set order line 2
						orderLineEntry2.setCreatedBy("sps automation");
						orderLineEntry2.setCreatedOn(date);
						orderLineEntry2.setId(lineId2);// lineId long
						orderLineEntry2.setLastModifiedOn(date);
						orderLineEntry2.setCartDiscount(0.0);
						orderLineEntry2.setCashRedeemed(lineCashRedeemed2);
						orderLineEntry2.setCashbackOffered(0.0);
						orderLineEntry2.setCouponDiscount(couponDiscount2);
						orderLineEntry2.setCustomizedMessage("Test");
						orderLineEntry2.setDiscount(0.0);
						orderLineEntry2.setDiscountRuleId(0);
						orderLineEntry2.setDiscountRuleRevId(0);
						orderLineEntry2.setDiscountedProduct(false);
						orderLineEntry2.setDiscountedQuantity(0);
						orderLineEntry2.setEarnedCredit(0.0);
						orderLineEntry2.setFinalAmount(lineFinalAmount2);
						orderLineEntry2.setFragile(false);
						orderLineEntry2.setGiftCardAmount(lineGiftCardAmount2);
						orderLineEntry2.setGovtTaxAmount(0.0);
						orderLineEntry2.setGovtTaxRate(5.0);
						orderLineEntry2.setHazMat(false);
						orderLineEntry2.setIsCustomizable(false);
						orderLineEntry2.setIsDiscountedProduct(false);
						orderLineEntry2.setIsExchangeableProduct(isExchangable2);
						orderLineEntry2.setIsReturnableProduct(true);
						orderLineEntry2.setIsFragile(false);
						orderLineEntry2.setIsJewellery(false);
						orderLineEntry2.setJewellery(false);
						orderLineEntry2.setLoyaltyConversionFactor(0.0);
						orderLineEntry2.setLoyaltyPointsAwarded(0.0);
						orderLineEntry2.setLoyaltyPointsCredit(0.0);
						orderLineEntry2.setLoyaltyPointsUsed(lineLoyaltyPointsUsed2);
						orderLineEntry2.setOptionId(optionId2);
						orderLineEntry2.setOrderId(orderId);
						orderLineEntry2.setOrderReleaseId(orderReleaseId2);
						orderLineEntry2.setPackagingStatus("NOT_PACKAGED");
						orderLineEntry2.setPackagingType("NORMAL");
						orderLineEntry2.setPgDiscount(0.0);
						orderLineEntry2.setPoStatus("UNUSED");
						orderLineEntry2.setQuantity(lineQuantity2);
						orderLineEntry2.setSellerId(sellerId2);
						orderLineEntry2.setSkuId(skuId2);
						orderLineEntry2.setStatus(lineStatus2);
						orderLineEntry2.setStatusDisplayName(lineStatusDisplayName2);
						orderLineEntry2.setStoredCredit(0.0);
						orderLineEntry2.setStoreOrderId(String.valueOf(store_order_id));
						orderLineEntry2.setStyleId(styleId);
						orderLineEntry2.setSupplyType(supplyType2);
						orderLineEntry2.setTaxAmount(0.0);
						orderLineEntry2.setTaxRate(5.0);
						orderLineEntry2.setUnitPrice(unitPrice2);
			List<OrderLineEntry> orderLineEntries = new ArrayList<OrderLineEntry>();
			orderLineEntries.add(orderLineEntry);
			orderLineEntries.add(orderLineEntry1);
			orderLineEntries.add(orderLineEntry2);
			
			orderReleaseEntry.setOrderLines(orderLineEntries);
			releaseUpdateEventEntry.setOldEntry(orderReleaseEntry);
			releaseUpdateEventEntry.setUpdatedEntry(orderReleaseEntry);

			String message = APIUtilities.convertXMLObjectToString(releaseUpdateEventEntry);
			return message;
		}
	

	//Create Packet with four release
	public String OMSMessageCreation(long orderId, String store_order_id, String status, String statusDisplay, double mrpTotal, double finalAmount, double ShippingCharge, boolean gift, double giftCardAmount,
					double giftCharge, double loyaltyPointsUsed, double cashRedeemed, long lineId, double couponDiscount, double lineFinalAmount, double lineGiftCardAmount, boolean isExchangable, double lineLoyaltyPointsUsed, long optionId,
					long orderReleaseId, int lineQuantity, long sellerId, long skuId, String lineStatus, String lineStatusDisplayName, long styleId, String supplyType, double unitPrice, double lineCashRedeemed, 
					long lineId1, double couponDiscount1, double lineFinalAmount1, double lineGiftCardAmount1, boolean isExchangable1, double lineLoyaltyPointsUsed1, long optionId1,long orderReleaseId1, int lineQuantity1, 
					long sellerId1, long skuId1, String lineStatus1, String lineStatusDisplayName1, long styleId1, String supplyType1, double unitPrice1, double lineCashRedeemed1,
					long lineId2, double couponDiscount2, double lineFinalAmount2, double lineGiftCardAmount2, boolean isExchangable2, double lineLoyaltyPointsUsed2, long optionId2,long orderReleaseId2, int lineQuantity2, 
					long sellerId2, long skuId2, String lineStatus2, String lineStatusDisplayName2, long styleId2, String supplyType2, double unitPrice2, double lineCashRedeemed2,
					long lineId3, double couponDiscount3, double lineFinalAmount3, double lineGiftCardAmount3, boolean isExchangable3, double lineLoyaltyPointsUsed3, long optionId3,
					long orderReleaseId3, int lineQuantity3, long sellerId3, long skuId3, String lineStatus3, String lineStatusDisplayName3, long styleId3, String supplyType3, double unitPrice3, double lineCashRedeemed3,String paymentPpsId,String paymentMethod ) throws JAXBException {
				Date date = new Date();
				OrderReleaseEntry orderReleaseEntry = new OrderReleaseEntry();// old and update entry
				OrderLineEntry orderLineEntry = new OrderLineEntry();
				OrderLineEntry orderLineEntry1 = new OrderLineEntry();
				OrderLineEntry orderLineEntry2 = new OrderLineEntry();
				OrderLineEntry orderLineEntry3 = new OrderLineEntry();
				ReleaseUpdateEventEntry releaseUpdateEventEntry = new ReleaseUpdateEventEntry();

				
				orderReleaseEntry.setAddress("xyz");
				releaseUpdateEventEntry.setEntityId(orderId);
				releaseUpdateEventEntry.setEventTime(date);
				orderReleaseEntry.setOrderDate(date);

				// ------------------set old/update entry
				orderReleaseEntry.setCreatedBy("sps automation");
				orderReleaseEntry.setCreatedOn(date);
				orderReleaseEntry.setId(orderId); //
				orderReleaseEntry.setLastModifiedOn(date);
				orderReleaseEntry.setAddress("Myntra design, AKR tech park, Near kudlu gate");
				orderReleaseEntry.setAddressId((long) 6118996);
				orderReleaseEntry.setCartDiscount(0.0);
				orderReleaseEntry.setCashRedeemed(cashRedeemed);
				orderReleaseEntry.setCity("Bangalore");
				orderReleaseEntry.setCodCharge(0.0);
				orderReleaseEntry.setCodPaymentStatus("pending");
				orderReleaseEntry.setCountry("India");
				orderReleaseEntry.setCouponDiscount(0.0);
				orderReleaseEntry.setDiscount(0.0);
				orderReleaseEntry.setEarnedCredit(0.0);
				orderReleaseEntry.setEmail("spsautomation@myntra.com");
				orderReleaseEntry.setEmiCharge(0.0);
				orderReleaseEntry.setFinalAmount(finalAmount); //
				orderReleaseEntry.setGift(gift);
				orderReleaseEntry.setGiftCardAmount(giftCardAmount);
				orderReleaseEntry.setGiftCharge(giftCharge);
				orderReleaseEntry.setLocality("Bangalore");
				orderReleaseEntry.setLogin("spsautomation@myntra.com");
				orderReleaseEntry.setLoyaltyConversionFactor(0.0);
				orderReleaseEntry.setLoyaltyPointsCredit(0.0);
				orderReleaseEntry.setLoyaltyPointsUsed(loyaltyPointsUsed);
				orderReleaseEntry.setMobile("9191919191");
				orderReleaseEntry.setMrpTotal(mrpTotal);
				orderReleaseEntry.setOnHold(false);
				orderReleaseEntry.setOrderId(orderId);
				orderReleaseEntry.setPaymentMethod(paymentMethod);
				orderReleaseEntry.setPaymentMethodDisplay("Credit card");
				orderReleaseEntry.setPersonalized(false);
				orderReleaseEntry.setPgDiscount(0.0);
				orderReleaseEntry.setReceiverName("sps automation");
				orderReleaseEntry.setRefunded(false);
				orderReleaseEntry.setShippingCharge(ShippingCharge);
				orderReleaseEntry.setShippingMethod("Normal");
				orderReleaseEntry.setSingleItemRelease(false);
				orderReleaseEntry.setSpecialPincode(false);
				orderReleaseEntry.setState("KA");
				orderReleaseEntry.setStatus(status);
				orderReleaseEntry.setStatusDisplayName(statusDisplay);
				orderReleaseEntry.setStoreId((long) 1);
				orderReleaseEntry.setStoreOrderId(String.valueOf(store_order_id));
				orderReleaseEntry.setStoredCredit(0.0);
				orderReleaseEntry.setTaxAmount(0.0);
				orderReleaseEntry.setTrackingNo("ML" + randomGenn(7));
				orderReleaseEntry.setUserContactNo("9191919191");
				orderReleaseEntry.setZipcode("560068");
				orderReleaseEntry.setPaymentPpsId(paymentPpsId);

				// ------------------Set order line 0
				orderLineEntry.setCreatedBy("sps automation");
				orderLineEntry.setCreatedOn(date);
				orderLineEntry.setId(lineId);// lineId long
				orderLineEntry.setLastModifiedOn(date);
				orderLineEntry.setCartDiscount(0.0);
				orderLineEntry.setCashRedeemed(lineCashRedeemed);
				orderLineEntry.setCashbackOffered(0.0);
				orderLineEntry.setCouponDiscount(couponDiscount);
				orderLineEntry.setCustomizedMessage("Test");
				orderLineEntry.setDiscount(0.0);
				orderLineEntry.setDiscountRuleId(0);
				orderLineEntry.setDiscountRuleRevId(0);
				orderLineEntry.setDiscountedProduct(false);
				orderLineEntry.setDiscountedQuantity(0);
				orderLineEntry.setEarnedCredit(0.0);
				orderLineEntry.setFinalAmount(lineFinalAmount);
				orderLineEntry.setFragile(false);
				orderLineEntry.setGiftCardAmount(lineGiftCardAmount);
				orderLineEntry.setGovtTaxAmount(0.0);
				orderLineEntry.setGovtTaxRate(5.0);
				orderLineEntry.setHazMat(false);
				orderLineEntry.setIsCustomizable(false);
				orderLineEntry.setIsDiscountedProduct(false);
				orderLineEntry.setIsExchangeableProduct(isExchangable);
				orderLineEntry.setIsReturnableProduct(true);
				orderLineEntry.setIsFragile(false);
				orderLineEntry.setIsJewellery(false);
				orderLineEntry.setJewellery(false);
				orderLineEntry.setLoyaltyConversionFactor(0.0);
				orderLineEntry.setLoyaltyPointsAwarded(0.0);
				orderLineEntry.setLoyaltyPointsCredit(0.0);
				orderLineEntry.setLoyaltyPointsUsed(lineLoyaltyPointsUsed);
				orderLineEntry.setOptionId(optionId);
				orderLineEntry.setOrderId(orderId);
				orderLineEntry.setOrderReleaseId(orderReleaseId);
				orderLineEntry.setPackagingStatus("NOT_PACKAGED");
				orderLineEntry.setPackagingType("NORMAL");
				orderLineEntry.setPgDiscount(0.0);
				orderLineEntry.setPoStatus("UNUSED");
				orderLineEntry.setQuantity(lineQuantity);
				orderLineEntry.setSellerId(sellerId);
				orderLineEntry.setSkuId(skuId);
				orderLineEntry.setStatus(lineStatus);
				orderLineEntry.setStatusDisplayName(lineStatusDisplayName);
				orderLineEntry.setStoredCredit(0.0);
				orderLineEntry.setStoreOrderId(String.valueOf(store_order_id));
				orderLineEntry.setStyleId(styleId);
				orderLineEntry.setSupplyType(supplyType);
				orderLineEntry.setTaxAmount(0.0);
				orderLineEntry.setTaxRate(5.0);
				orderLineEntry.setUnitPrice(unitPrice);
				
				// ------------------Set order line 1
							orderLineEntry1.setCreatedBy("sps automation");
							orderLineEntry1.setCreatedOn(date);
							orderLineEntry1.setId(lineId1);// lineId long
							orderLineEntry1.setLastModifiedOn(date);
							orderLineEntry1.setCartDiscount(0.0);
							orderLineEntry1.setCashRedeemed(lineCashRedeemed1);
							orderLineEntry1.setCashbackOffered(0.0);
							orderLineEntry1.setCouponDiscount(couponDiscount1);
							orderLineEntry1.setCustomizedMessage("Test");
							orderLineEntry1.setDiscount(0.0);
							orderLineEntry1.setDiscountRuleId(0);
							orderLineEntry1.setDiscountRuleRevId(0);
							orderLineEntry1.setDiscountedProduct(false);
							orderLineEntry1.setDiscountedQuantity(0);
							orderLineEntry1.setEarnedCredit(0.0);
							orderLineEntry1.setFinalAmount(lineFinalAmount1);
							orderLineEntry1.setFragile(false);
							orderLineEntry1.setGiftCardAmount(lineGiftCardAmount1);
							orderLineEntry1.setGovtTaxAmount(0.0);
							orderLineEntry1.setGovtTaxRate(5.0);
							orderLineEntry1.setHazMat(false);
							orderLineEntry1.setIsCustomizable(false);
							orderLineEntry1.setIsDiscountedProduct(false);
							orderLineEntry1.setIsExchangeableProduct(isExchangable1);
							orderLineEntry1.setIsReturnableProduct(true);
							orderLineEntry1.setIsFragile(false);
							orderLineEntry1.setIsJewellery(false);
							orderLineEntry1.setJewellery(false);
							orderLineEntry1.setLoyaltyConversionFactor(0.0);
							orderLineEntry1.setLoyaltyPointsAwarded(0.0);
							orderLineEntry1.setLoyaltyPointsCredit(0.0);
							orderLineEntry1.setLoyaltyPointsUsed(lineLoyaltyPointsUsed1);
							orderLineEntry1.setOptionId(optionId1);
							orderLineEntry1.setOrderId(orderId);
							orderLineEntry1.setOrderReleaseId(orderReleaseId1);
							orderLineEntry1.setPackagingStatus("NOT_PACKAGED");
							orderLineEntry1.setPackagingType("NORMAL");
							orderLineEntry1.setPgDiscount(0.0);
							orderLineEntry1.setPoStatus("UNUSED");
							orderLineEntry1.setQuantity(lineQuantity1);
							orderLineEntry1.setSellerId(sellerId1);
							orderLineEntry1.setSkuId(skuId1);
							orderLineEntry1.setStatus(lineStatus1);
							orderLineEntry1.setStatusDisplayName(lineStatusDisplayName1);
							orderLineEntry1.setStoredCredit(0.0);
							orderLineEntry1.setStoreOrderId(String.valueOf(store_order_id));
							orderLineEntry1.setStyleId(styleId1);
							orderLineEntry1.setSupplyType(supplyType1);
							orderLineEntry1.setTaxAmount(0.0);
							orderLineEntry1.setTaxRate(5.0);
							orderLineEntry1.setUnitPrice(unitPrice1);
							
							// ------------------Set order line 2
							orderLineEntry2.setCreatedBy("sps automation");
							orderLineEntry2.setCreatedOn(date);
							orderLineEntry2.setId(lineId2);// lineId long
							orderLineEntry2.setLastModifiedOn(date);
							orderLineEntry2.setCartDiscount(0.0);
							orderLineEntry2.setCashRedeemed(lineCashRedeemed2);
							orderLineEntry2.setCashbackOffered(0.0);
							orderLineEntry2.setCouponDiscount(couponDiscount2);
							orderLineEntry2.setCustomizedMessage("Test");
							orderLineEntry2.setDiscount(0.0);
							orderLineEntry2.setDiscountRuleId(0);
							orderLineEntry2.setDiscountRuleRevId(0);
							orderLineEntry2.setDiscountedProduct(false);
							orderLineEntry2.setDiscountedQuantity(0);
							orderLineEntry2.setEarnedCredit(0.0);
							orderLineEntry2.setFinalAmount(lineFinalAmount2);
							orderLineEntry2.setFragile(false);
							orderLineEntry2.setGiftCardAmount(lineGiftCardAmount2);
							orderLineEntry2.setGovtTaxAmount(0.0);
							orderLineEntry2.setGovtTaxRate(5.0);
							orderLineEntry2.setHazMat(false);
							orderLineEntry2.setIsCustomizable(false);
							orderLineEntry2.setIsDiscountedProduct(false);
							orderLineEntry2.setIsExchangeableProduct(isExchangable2);
							orderLineEntry2.setIsReturnableProduct(true);
							orderLineEntry2.setIsFragile(false);
							orderLineEntry2.setIsJewellery(false);
							orderLineEntry2.setJewellery(false);
							orderLineEntry2.setLoyaltyConversionFactor(0.0);
							orderLineEntry2.setLoyaltyPointsAwarded(0.0);
							orderLineEntry2.setLoyaltyPointsCredit(0.0);
							orderLineEntry2.setLoyaltyPointsUsed(lineLoyaltyPointsUsed2);
							orderLineEntry2.setOptionId(optionId2);
							orderLineEntry2.setOrderId(orderId);
							orderLineEntry2.setOrderReleaseId(orderReleaseId2);
							orderLineEntry2.setPackagingStatus("NOT_PACKAGED");
							orderLineEntry2.setPackagingType("NORMAL");
							orderLineEntry2.setPgDiscount(0.0);
							orderLineEntry2.setPoStatus("UNUSED");
							orderLineEntry2.setQuantity(lineQuantity2);
							orderLineEntry2.setSellerId(sellerId2);
							orderLineEntry2.setSkuId(skuId2);
							orderLineEntry2.setStatus(lineStatus2);
							orderLineEntry2.setStatusDisplayName(lineStatusDisplayName2);
							orderLineEntry2.setStoredCredit(0.0);
							orderLineEntry2.setStoreOrderId(String.valueOf(store_order_id));
							orderLineEntry2.setStyleId(styleId);
							orderLineEntry2.setSupplyType(supplyType2);
							orderLineEntry2.setTaxAmount(0.0);
							orderLineEntry2.setTaxRate(5.0);
							orderLineEntry2.setUnitPrice(unitPrice2);

							// ------------------Set order line 3
							orderLineEntry3.setCreatedBy("sps automation");
							orderLineEntry3.setCreatedOn(date);
							orderLineEntry3.setId(lineId3);// lineId long
							orderLineEntry3.setLastModifiedOn(date);
							orderLineEntry3.setCartDiscount(0.0);
							orderLineEntry3.setCashRedeemed(lineCashRedeemed3);
							orderLineEntry3.setCashbackOffered(0.0);
							orderLineEntry3.setCouponDiscount(couponDiscount3);
							orderLineEntry3.setCustomizedMessage("Test");
							orderLineEntry3.setDiscount(0.0);
							orderLineEntry3.setDiscountRuleId(0);
							orderLineEntry3.setDiscountRuleRevId(0);
							orderLineEntry3.setDiscountedProduct(false);
							orderLineEntry3.setDiscountedQuantity(0);
							orderLineEntry3.setEarnedCredit(0.0);
							orderLineEntry3.setFinalAmount(lineFinalAmount3);
							orderLineEntry3.setFragile(false);
							orderLineEntry3.setGiftCardAmount(lineGiftCardAmount3);
							orderLineEntry3.setGovtTaxAmount(0.0);
							orderLineEntry3.setGovtTaxRate(5.0);
							orderLineEntry3.setHazMat(false);
							orderLineEntry3.setIsCustomizable(false);
							orderLineEntry3.setIsDiscountedProduct(false);
							orderLineEntry3.setIsExchangeableProduct(isExchangable3);
							orderLineEntry3.setIsReturnableProduct(true);
							orderLineEntry3.setIsFragile(false);
							orderLineEntry3.setIsJewellery(false);
							orderLineEntry3.setJewellery(false);
							orderLineEntry3.setLoyaltyConversionFactor(0.0);
							orderLineEntry3.setLoyaltyPointsAwarded(0.0);
							orderLineEntry3.setLoyaltyPointsCredit(0.0);
							orderLineEntry3.setLoyaltyPointsUsed(lineLoyaltyPointsUsed3);
							orderLineEntry3.setOptionId(optionId3);
							orderLineEntry3.setOrderId(orderId);
							orderLineEntry3.setOrderReleaseId(orderReleaseId3);
							orderLineEntry3.setPackagingStatus("NOT_PACKAGED");
							orderLineEntry3.setPackagingType("NORMAL");
							orderLineEntry3.setPgDiscount(0.0);
							orderLineEntry3.setPoStatus("UNUSED");
							orderLineEntry3.setQuantity(lineQuantity3);
							orderLineEntry3.setSellerId(sellerId3);
							orderLineEntry3.setSkuId(skuId3);
							orderLineEntry3.setStatus(lineStatus3);
							orderLineEntry3.setStatusDisplayName(lineStatusDisplayName3);
							orderLineEntry3.setStoredCredit(0.0);
							orderLineEntry3.setStoreOrderId(String.valueOf(store_order_id));
							orderLineEntry3.setStyleId(styleId3);
							orderLineEntry3.setSupplyType(supplyType3);
							orderLineEntry3.setTaxAmount(0.0);
							orderLineEntry3.setTaxRate(5.0);
							orderLineEntry3.setUnitPrice(unitPrice3);
						
				List<OrderLineEntry> orderLineEntries = new ArrayList<OrderLineEntry>();
				orderLineEntries.add(orderLineEntry);
				orderLineEntries.add(orderLineEntry1);
				orderLineEntries.add(orderLineEntry2);
				orderLineEntries.add(orderLineEntry3);
				
				orderReleaseEntry.setOrderLines(orderLineEntries);
				releaseUpdateEventEntry.setOldEntry(orderReleaseEntry);
				releaseUpdateEventEntry.setUpdatedEntry(orderReleaseEntry);

				String message = APIUtilities.convertXMLObjectToString(releaseUpdateEventEntry);
				return message;
			}
		
	public String exchangeMessageCreation(long orderId, String store_order_id, double mrpTotal, double finalAmount, 
			long orderReleaseId, long exchangeReleaseId, String relStatus, String relStatusDisplay,
			long lineId, long exchangeLineId, double lineFinalAmount, long optionId, int lineQuantity, long sellerId, long skuId, String lineStatus, String lineStatusDisplayName, 
			long styleId, String supplyType, double unitPrice,String paymentPpsId,String paymentMethod) throws JAXBException{
		Date date = new Date();
		OrderResponse orderResponse = new OrderResponse();
		OrderEntry orderEntry = new OrderEntry();
		OrderReleaseEntry orderReleaseEntry = new OrderReleaseEntry();
		OrderLineEntry orderLineEntry = new OrderLineEntry();
		

		ReleaseUpdateEventEntry releaseUpdateEventEntry = new ReleaseUpdateEventEntry();
		
		releaseUpdateEventEntry.setEventTime(date);
		orderReleaseEntry.setOrderDate(null);
		orderReleaseEntry.setAddress("xyz");
		releaseUpdateEventEntry.setEntityId(orderId);
	

		orderEntry.setCreatedBy("SPS automation");
		orderEntry.setCreatedOn(date);
		orderEntry.setId(orderId);
		orderEntry.setLastModifiedOn(date);
		orderEntry.setVersion((long) 2);
		orderEntry.setCartDiscount(0.0);
		orderEntry.setCashRedeemed(0.0);
		orderEntry.setChannel("web");
		orderEntry.setCodCharge(0.0);
		orderEntry.setCouponCode(null);
		orderEntry.setCouponDiscount(0.0);
		orderEntry.setCustomerName("spsautomation");
		orderEntry.setDiscount(0.0);
		orderEntry.setEarnedCredit(0.0);
		orderEntry.setEmiCharge(0.0);
		orderEntry.setFinalAmount(finalAmount);
		orderEntry.setGiftCardAmount(0.0);
		orderEntry.setGiftCharge(0.0);
		orderEntry.setGiftOrder(false);
		orderEntry.setLogin("spsAutomation@myntra.com");
		orderEntry.setLoyaltyPointsCredit(0.0);
		orderEntry.setLoyaltyPointsUsed(0.0);
		orderEntry.setMrpTotal(mrpTotal);
		orderEntry.setNotes("Test");
		orderEntry.setOnHold(false);
		orderEntry.setOrderType("ex");
		orderEntry.setOrderTypeDisplay("Exchange");
//		orderEntry.setOrderProcessingFlowMode(orderProcessingFlowMode);
		orderEntry.setPaymentMethod(paymentMethod);
		orderEntry.setPaymentMethodDisplay("Online");
		orderEntry.setPgDiscount(0.0);
		orderEntry.setShippingCharge(0.0);
		orderEntry.setTaxAmount(0.0);
		orderEntry.setQueuedOn(date);
		orderEntry.setStoredCredit(0.0);
		orderEntry.setUserContactNo("9898977867");
		orderEntry.setStoreId((long)1);
		orderEntry.setStoreOrderId(String.valueOf(store_order_id));
			
		orderReleaseEntry.setCreatedBy("sps automation");
		orderReleaseEntry.setCreatedOn(date);
		orderReleaseEntry.setId(orderReleaseId); //
		orderReleaseEntry.setLastModifiedOn(date);
		orderReleaseEntry.setAddress("Myntra design, AKR tech park, Near kudlu gate");
		orderReleaseEntry.setAddressId((long) 6118996);
		orderReleaseEntry.setCartDiscount(0.0);
		orderReleaseEntry.setCashRedeemed(0.0);
		orderReleaseEntry.setCity("Bangalore");
		orderReleaseEntry.setCodCharge(0.0);
		orderReleaseEntry.setCodPaymentStatus("pending");
		orderReleaseEntry.setCountry("India");
		orderReleaseEntry.setCouponDiscount(0.0);
		orderReleaseEntry.setDiscount(0.0);
		orderReleaseEntry.setEarnedCredit(0.0);
		orderReleaseEntry.setEmail("spsautomation@myntra.com");
		orderReleaseEntry.setEmiCharge(0.0);
		orderReleaseEntry.setExchangeReleaseId(exchangeReleaseId);
		//orderReleaseEntry.setExchangeReleaseId(orderId);
		orderReleaseEntry.setFinalAmount(finalAmount); 
		orderReleaseEntry.setGift(false);
		orderReleaseEntry.setGiftCardAmount(0.0);
		orderReleaseEntry.setGiftCharge(0.0);
		orderReleaseEntry.setLocality("Bangalore");
		orderReleaseEntry.setLogin("spsautomation@myntra.com");
		orderReleaseEntry.setLoyaltyConversionFactor(0.0);
		orderReleaseEntry.setLoyaltyPointsCredit(0.0);
		orderReleaseEntry.setLoyaltyPointsUsed(0.0);
		orderReleaseEntry.setMobile("9191919191");
		orderReleaseEntry.setMrpTotal(mrpTotal);
		orderReleaseEntry.setOnHold(false);
		orderReleaseEntry.setOrderId(orderId);
		orderReleaseEntry.setPaymentMethod(paymentMethod);
		orderReleaseEntry.setPackedOn(date);
		orderReleaseEntry.setPaymentMethodDisplay("Credit card");
		orderReleaseEntry.setPersonalized(false);
		orderReleaseEntry.setPgDiscount(0.0);
		orderReleaseEntry.setReceiverName("sps automation");
		orderReleaseEntry.setRefunded(false);
		orderReleaseEntry.setShippingCharge(0.0);
		orderReleaseEntry.setShippingMethod("Normal");
		orderReleaseEntry.setSingleItemRelease(false);
		orderReleaseEntry.setSpecialPincode(false);
		orderReleaseEntry.setState("KA");
		orderReleaseEntry.setStatus(relStatus);
		orderReleaseEntry.setStatusDisplayName(relStatusDisplay);
		orderReleaseEntry.setStoreId((long) 1);
		orderReleaseEntry.setStoreOrderId(String.valueOf(store_order_id));
		orderReleaseEntry.setStoredCredit(0.0);
		orderReleaseEntry.setTaxAmount(0.0);
		orderReleaseEntry.setTrackingNo("ML" + randomGenn(7));
		orderReleaseEntry.setUserContactNo("9191919191");
		orderReleaseEntry.setZipcode("560068");
		orderReleaseEntry.setPaymentPpsId(paymentPpsId);
				
		orderLineEntry.setCreatedBy("sps automation");
		orderLineEntry.setCreatedOn(date);
		orderLineEntry.setId(lineId);// lineId long
		orderLineEntry.setLastModifiedOn(date);
		orderLineEntry.setCartDiscount(0.0);
		orderLineEntry.setCashRedeemed(0.0);
		orderLineEntry.setCashbackOffered(0.0);
		orderLineEntry.setCouponDiscount(0.0);
		orderLineEntry.setCustomizedMessage("Test");
		orderLineEntry.setDiscount(0.0);
		orderLineEntry.setDiscountRuleId(0);
		orderLineEntry.setDiscountRuleRevId(0);
		orderLineEntry.setDiscountedProduct(false);
		orderLineEntry.setDiscountedQuantity(0);
		orderLineEntry.setEarnedCredit(0.0);
		orderLineEntry.setExchangeLineId(exchangeLineId);
		//orderLineEntry.setExchangeLineId(lineId);
		orderLineEntry.setFinalAmount(lineFinalAmount);
		orderLineEntry.setFragile(false);
		orderLineEntry.setGiftCardAmount(0.0);
		orderLineEntry.setGovtTaxAmount(0.0);
		orderLineEntry.setGovtTaxRate(5.0);
		orderLineEntry.setHazMat(false);
		orderLineEntry.setIsCustomizable(false);
		orderLineEntry.setIsDiscountedProduct(false);
		orderLineEntry.setIsExchangeableProduct(true);
		orderLineEntry.setIsReturnableProduct(true);
		orderLineEntry.setIsFragile(false);
		orderLineEntry.setIsJewellery(false);
		orderLineEntry.setJewellery(false);
		orderLineEntry.setLoyaltyConversionFactor(0.0);
		orderLineEntry.setLoyaltyPointsAwarded(0.0);
		orderLineEntry.setLoyaltyPointsCredit(0.0);
		orderLineEntry.setLoyaltyPointsUsed(0.0);
		orderLineEntry.setOptionId(optionId);
		orderLineEntry.setOrderId(orderId);
		orderLineEntry.setOrderReleaseId(orderReleaseId);
		orderLineEntry.setPackagingStatus("NOT_PACKAGED");
		orderLineEntry.setPackagingType("NORMAL");
		orderLineEntry.setPgDiscount(0.0);
		orderLineEntry.setPoStatus("UNUSED");
		orderLineEntry.setQuantity(lineQuantity);
		orderLineEntry.setSellerId(sellerId);
		orderLineEntry.setSkuId(skuId);
		orderLineEntry.setStatus(lineStatus);
		orderLineEntry.setStatusDisplayName(lineStatusDisplayName);
		orderLineEntry.setStoredCredit(0.0);
		orderLineEntry.setStoreOrderId(String.valueOf(store_order_id));
		orderLineEntry.setStyleId(styleId);
		orderLineEntry.setSupplyType(supplyType);
		orderLineEntry.setTaxAmount(0.0);
		orderLineEntry.setTaxRate(5.0);
		orderLineEntry.setUnitPrice(unitPrice);
		List<OrderLineEntry> orderLineEntries = new ArrayList<OrderLineEntry>();
		orderLineEntries.add(orderLineEntry);
		orderReleaseEntry.setOrderLines(orderLineEntries);
		
		List<OrderReleaseEntry> orderReleaseEntrys = new ArrayList<OrderReleaseEntry>();
		orderReleaseEntrys.add(orderReleaseEntry);
		orderEntry.setOrderReleases(orderReleaseEntrys);
		
		/*List<OrderEntry> orderEntrys = new ArrayList<OrderEntry>();
		orderEntrys.add(orderEntry);
		orderResponse.setData(orderEntrys);*/
		
		orderReleaseEntry.setOrderLines(orderLineEntries);
		releaseUpdateEventEntry.setOldEntry(orderReleaseEntry);
		releaseUpdateEventEntry.setUpdatedEntry(orderReleaseEntry);

		String message = APIUtilities.convertXMLObjectToString(releaseUpdateEventEntry);
//		String message = APIUtilities.convertXMLObjectToString(orderResponse);
		return message;
	}
	
	public void webHookNotificationPersonal(Initialize init, String printStream, String slackuser, String botname) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WEBHOOK, APINAME.WEBHOOKPERSONAL,
				init.Configurations, new String[] { printStream, slackuser, botname }, PayloadType.JSON,
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Accept", "text/html");
		RequestGenerator req = new RequestGenerator(service, getParam);
		AssertJUnit.assertEquals("Status code does not match", 200, req.response.getStatus());
	}

		
}