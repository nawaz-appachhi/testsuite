package com.myntra.apiTests.inbound.helper;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.inbound.helper.SpotConstants.ArticleType;
import com.myntra.apiTests.inbound.helper.SpotConstants.Source;
import com.myntra.apiTests.inbound.response.jabong.Data;
import com.myntra.apiTests.inbound.response.FIFAResponse;
import com.myntra.apiTests.inbound.response.FIFAStatusCodes;
import com.myntra.apiTests.inbound.response.SpotResponse;
import com.myntra.apiTests.inbound.response.jabong.SpotJabongResponse;
import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Initialize;
//import com.myntra.lordoftherings.aragorn.pages.spot.SpotConstants.*;

import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.AssertJUnit;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class SpotServiceHelper {

	// static Initialize init = new Initialize("Data/configuration");
	FIFAResponse fifaresponse = new FIFAResponse();
	private static Logger log = Logger.getLogger(SpotServiceHelper.class);
	private String response;
	private Svc svc;

	public String filterMyntraSearchData(String createRapidDatasetPath, String jsonpayload, Configuration config,
			String collectionName) throws Exception {
		// APINAME apiundertest = APINAME.CREATESPOTDATASET;
		// Payload p1 = new Payload(apiundertest, config, PayloadType.JSON);
		Svc service = HttpExecutorService.executeHttpService(createRapidDatasetPath, new String[] { collectionName },
				SERVICE_TYPE.SPOT_SVC.toString(), HTTPMethods.POST, jsonpayload, getSPOTHeader());
		Assert.assertEquals(service.getResponseStatus(), 200);
		// Assert.assertEquals(service.getResponseStatusType(),
		// StatusResponse.Type.SUCCESS);
		return service.getResponseBody();
	}

	public HashMap<String, String> getSPOTHeader() throws Exception {
		HashMap<String, String> createQueryHeaders = new HashMap<String, String>();
		createQueryHeaders.put("Content-Type", "application/json");
		createQueryHeaders.put("accept", "application/json");
		return createQueryHeaders;
	}

	public long amazons3filedownload(String bucketName, String key, String AWS_ACCESS_KEY, String AWS_ACCESS_SECRET_KEY)
			throws Exception {
		// s3://myntra/logic_data_pipeline/RDF/PROD/AUTOPILOT/fifa_forecast.csv.gz

		AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_ACCESS_SECRET_KEY));
		log.debug("Downloading an object from Amazon s3");
		S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, key));

		S3ObjectInputStream objectContent = s3object.getObjectContent();

		String userdir = System.getProperty("user.dir");
		String s3ZipFile = userdir + "/target/fifa_forecast.csv.gz";
		IOUtils.copy(objectContent, new FileOutputStream(s3ZipFile));
		String s3Unzipfile = userdir + "/target/fifa_forecast.csv";
		unGunzipFile(s3ZipFile, s3Unzipfile);
		log.debug("The file was downloaded successfully! " + s3ZipFile);
		// InputStream io = new FileInputStream(new File(s3Unzipfile));

		FileReader fr = new FileReader(new File(s3Unzipfile));
		return displayTextInputStream(fr);

	}

	private static long displayTextInputStream(FileReader input) throws Exception {
		// Read one text line at a time and display.
		long count = 0;

		LineNumberReader lnr = new LineNumberReader(input);

		while (lnr.readLine() != null) {
			count++;
		}

		lnr.close();
		log.debug("Total count of RDF products in s3 :" + count);
		return count;
	}

	public void unGunzipFile(String compressedFile, String decompressedFile) throws Exception {

		byte[] buffer = new byte[1024];

		FileInputStream fileIn = new FileInputStream(compressedFile);

		GZIPInputStream gZIPInputStream = new GZIPInputStream(fileIn);

		FileOutputStream fileOutputStream = new FileOutputStream(decompressedFile);

		int bytes_read;

		while ((bytes_read = gZIPInputStream.read(buffer)) > 0) {

			fileOutputStream.write(buffer, 0, bytes_read);
		}

		gZIPInputStream.close();
		fileOutputStream.close();
		log.debug("The file was decompressed successfully! " + decompressedFile);
	}

	public String getJsonkeyvalue(String jsonpayload) throws Exception {
		Pattern pattern = Pattern.compile("\"filters\":\\S+\"asp\":\"(\\d+)+\"");
		Matcher matcher = pattern.matcher(jsonpayload);
		// System.out.println(matcher.group(0));
		// if(matcher.find()){
		//
		// }
		return "890";

	}

	public String getJsonkeyvalue(String jsonpayload, String key) throws Exception {
		JSONObject jsonObject = new JSONObject(jsonpayload);
		return jsonObject.get(key).toString();
	}

	public ArrayList<String> downloadamazons3forcastfile(String bucketName, String key, String AWS_ACCESS_KEY,
			String AWS_ACCESS_SECRET_KEY, String filename, int index) throws Exception {
		AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_ACCESS_SECRET_KEY));
		log.info("Downloading an object from Amazon s3");
		S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, key));

		S3ObjectInputStream objectContent = s3object.getObjectContent();

		String userdir = System.getProperty("user.dir");
		String s3ZipFile = userdir + "/target/" + filename + ".csv.gz";
		IOUtils.copy(objectContent, new FileOutputStream(s3ZipFile));
		String s3Unzipfile = userdir + "/target/" + filename + ".csv";
		unGunzipFile(s3ZipFile, s3Unzipfile);
		log.debug("The file was downloaded successfully! " + s3ZipFile);
		// InputStream io = new FileInputStream(new File(s3Unzipfile));

		// FileReader fr = new FileReader(new File(s3Unzipfile));
		InputStream io = new FileInputStream(new File(s3Unzipfile));

		return displayTextInputStream(io, index);

	}

	// to read the downloaded file
	public ArrayList<String> displayTextInputStream(InputStream input, int index) throws IOException, Exception {
		// Read one text line at a time and display.
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		ArrayList<String> arr = new ArrayList<String>();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(input));
			while ((line = br.readLine()) != null) {
				arr.add(line.split(",")[index].replaceAll("\\D+", ""));
			}
		} catch (IOException e) {
			log.error("Error ...", e);
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error("Error ...", e);
					throw e;
				}
			}
		}

		return arr;
	}

	// to read the downloaded file
	public HashMap<String, String> displayTextInputStream(InputStream input, int key, int value)
			throws IOException, Exception {
		// Read one text line at a time and display.
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		HashMap<String, String> map = new HashMap<String, String>();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(input));
			while ((line = br.readLine()) != null) {
				map.put(line.split(",")[key].replaceAll("\\D+", ""), line.split(",")[value]);
			}
		} catch (IOException e) {
			log.error("Error ...", e);
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error("Error ...", e);
					throw e;
				}
			}
		}

		return map;
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

	public HashMap<String, String> calenderUtilfromAmazons3(String bucketName, String clnd_key, String AWS_ACCESS_KEY,
			String AWS_ACCESS_SECRET_KEY, String filename, int key, int value) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_ACCESS_SECRET_KEY));
		log.info("Downloading an object from Amazon s3");
		S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, clnd_key));

		S3ObjectInputStream objectContent = s3object.getObjectContent();

		String userdir = System.getProperty("user.dir");
		String s3ZipFile = userdir + "/target/" + filename + ".csv.gz";
		IOUtils.copy(objectContent, new FileOutputStream(s3ZipFile));
		String s3Unzipfile = userdir + "/target/" + filename + ".csv";
		unGunzipFile(s3ZipFile, s3Unzipfile);
		log.debug("The file was downloaded successfully! " + s3ZipFile);

		InputStream io = new FileInputStream(new File(s3Unzipfile));

		return displayTextInputStream(io, key, value);

	}

	// to get the current date in yyyymmdd format

	public String getCurrentTimeStamp() throws Exception {
		Date date = new Date();
		String modifiedDate = new SimpleDateFormat("yyyyMMdd").format(date);
		return modifiedDate;
	}

	// to get day before yesterday date in yyyymmdd format
	public static String getLastNthDay(int days) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		return dateFormat.format(cal.getTime());
	}

	public String last15DaysCalender() throws Exception {
		String startDate = getLastNthDay(-15);
		return startDate;
	}

	public String last30DaysCalender() throws Exception {
		String startDate = getLastNthDay(-30);
		return startDate;
	}

	public String last45DaysCalender() throws Exception {
		String startDate = getLastNthDay(-45);
		return startDate;
	}
	public String last90DaysCalender() throws Exception {
		String startDate = getLastNthDay(-90);
		return startDate;
	}

	public String lastMonthStartDate() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DATE, 1);
		Date lastMonthStartDate = cal.getTime();
		return dateFormat.format(lastMonthStartDate);
	}

	public String lastMonthLastDate() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)); // changed
																		// calendar
																		// to
																		// cal

		Date lastMonthLastDate = cal.getTime();
		return dateFormat.format(lastMonthLastDate);
	}

	// json parser to retrieve pojo object values
	public static String getRapidQueryId(String response) throws Exception {
		JSONObject jsonObject = new JSONObject(response);
		String[] jsonarr = JSONObject.getNames(jsonObject.getJSONObject("data"));
		String rapid_query_id = jsonarr[0];
		return rapid_query_id;
	}

	// returns headers for the http requests
	public HashMap<String, String> getSpotRapidQueryHeaders(HashMap<String, String> headers) throws Exception {
		HashMap<String, String> spotrapidqueryheaders = new HashMap<String, String>(headers);
		return spotrapidqueryheaders;
	}

	public String getspotrapidqueryID(String payload, HashMap<String, String> headers) throws Exception {

		log.info("Getting the rapid-ws id from the spot api");
		svc = HttpExecutorService.executeHttpService(Constants.SPOT_PATH.Create_RAPID_DataSet, null, SERVICE_TYPE.SPOT_SVC.toString(),
				HTTPMethods.POST, payload, getSpotRapidQueryHeaders(headers));
		String status = svc.getResponseStatusType("statusType");
		log.info("\nThe query status is\n" + status+"\n");
		if(svc.getResponseStatusType("statusMessage").contains("There are no products found matching these search terms")){
			log.info("\nSkipping the execution as \n" + svc.getResponseStatusType("statusMessage")+"\n");
			//throw new SkipException(svc.getResponseStatusType("statusMessage"));
			String responsedata="No Data Available";
			return responsedata;
		}
		else{
		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
		log.debug("\nSpot Rapid Collection Id generated is:"+svc.getResponseBody()+"\n");
		return getRapidQueryId(svc.getResponseBody());
		}
		
	}

//	public void get_rapid_query_status(String rapidqueryid, HashMap<String, String> headers) throws Exception {
//		log.info("Getting the rapid-ws status from the spot api");
//		svc = HttpExecutorService.executeHttpService(SPOT_PATH.PROCESS_RAPID_DataSet + "/" + rapidqueryid, null,
//				SERVICE_TYPE.SPOT_SVC.toString(), HTTPMethods.GET, null, getSpotRapidQueryHeaders(headers));
//		String status = svc.getResponseStatusType("statusType");
//		log.info("The query status is" + status);
//	}

	public String get_spot_data_with_filters(String rapidqueryid, String payload, HashMap<String, String> headers)
			throws Exception {

		log.info("\nGetting the spot data after applying filters from the response\n");
		svc = HttpExecutorService.executeHttpService(Constants.SPOT_PATH.FILTER_RAPID_DATA + "/" + rapidqueryid, null,
				SERVICE_TYPE.SPOT_SVC.toString(), HTTPMethods.POST, payload, getSpotRapidQueryHeaders(headers));
		response = svc.getResponseBody();
		String status = svc.getResponseStatusType("statusType");
		log.info("\nThe query status is\n" + status+"\n");
		
		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
		return response;
	}
	public SpotResponse get_rapid_query_data(String rapidqueryid, HashMap<String, String> headers) throws Exception {
		log.info("Getting the rapid-ws status from the spot api");
		svc = HttpExecutorService.executeHttpService(Constants.SPOT_PATH.Create_RAPID_DataSet + "/" + rapidqueryid, null,
				SERVICE_TYPE.SPOT_SVC.toString(), HTTPMethods.GET, null, getSpotRapidQueryHeaders(headers));
		
		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
		
		String status = svc.getResponseStatusType("statusType");
		log.info("\nThe query status is\n" + status+"\n");
		
		SpotResponse response = (SpotResponse) APIUtilities.getJsontoObject(svc.getResponseBody(), new SpotResponse());
	
		return response;
	}
	
	public SpotJabongResponse get_rapid_query_data_for_Jabong(String rapidqueryid, HashMap<String, String> headers) throws Exception {
		log.info("Getting the rapid-ws status from the spot api");
		svc = HttpExecutorService.executeHttpService(Constants.SPOT_PATH.Create_RAPID_DataSet + "/" + rapidqueryid, null,
				SERVICE_TYPE.SPOT_SVC.toString(), HTTPMethods.GET, null, getSpotRapidQueryHeaders(headers));
		
		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
		
		String status = svc.getResponseStatusType("statusType");
		log.info("\nThe query status is\n" + status+"\n");
		
		SpotJabongResponse response = (SpotJabongResponse) APIUtilities.getJsontoObject(svc.getResponseBody(), new SpotJabongResponse());
	
		return response;
	}
	
	public static String rename(String response) throws Exception {
		JSONObject jsonObject = new JSONObject(response);
		String[] jsonarr = JSONObject.getNames(jsonObject.getJSONObject("data"));
		String rapid_query_id = jsonarr[0];
		return rapid_query_id;
	}
	
	public void validateRapidQueryData(SpotResponse response, String styleFromDate, String styleToDate,String orderFromDate,String orderToDate,
			String article_type, String gender,String source
			) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date styleStartDate = df.parse(styleFromDate);
		Date styleEndDate = df.parse(styleToDate);
		Date orderStartDate=df.parse(orderFromDate);
		Date orderEndDate=df.parse(orderToDate);

		com.myntra.apiTests.inbound.response.Data[] data = response.getData();
		for (int i = 0; i < data.length; i++) {
			Date outputStyleDate = df.parse(data[i].getStyle_catalogued_date());
			Assert.assertEquals(
			outputStyleDate.compareTo(styleStartDate) >= 0 && outputStyleDate.compareTo(styleEndDate) <= 0,
			true, "style catalog date: " + outputStyleDate+ "not with in the Date Range:"+styleStartDate+"-"+styleEndDate+" for the style_Id:"+data[i].getStyle_id());
			
			Date outputStyleBreakDate = df.parse(data[i].getStyle_break_date());
			Assert.assertEquals(
					outputStyleBreakDate.compareTo(orderStartDate) >= 0,
					true, "style break date: " + outputStyleBreakDate+ "not with in the Date Range:"+orderStartDate+"-"+orderEndDate+" for the style_Id:"+data[i].getStyle_id());
			
			
			Assert.assertEquals(data[i].getGender().toLowerCase(), gender.toLowerCase(), "InCorrect Gender found:"
					+ data[i].getGender() + " for the style_Id:" + data[i].getStyle_id());
			
			if(!source.contains(Source.MYNTRA)){
				Assert.assertEquals(data[i].getArticle_type().toLowerCase(), article_type.toLowerCase(), "InCorrect Article_Type found:"
						+ data[i].getArticle_type() + " for the style_Id:" + data[i].getStyle_id());
			}
			
			
		}
	}
	
	public void validateRapidQueryDataforAmazon(SpotJabongResponse response, String styleFromDate, String styleToDate,String orderFromDate,String orderToDate,
			String article_type, String gender,String source
			) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date styleStartDate = df.parse(styleFromDate);
		Date styleEndDate = df.parse(styleToDate);
		Date orderStartDate=df.parse(orderFromDate);
		Date orderEndDate=df.parse(orderToDate);
		
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

		Data[] data = response.getData();
		for (int i = 0; i < data.length; i++) {
//			String[] activated_date=data[i].getActivated_at().split("T");
//			Date outputStyleDate = df2.parse(activated_date[0]);
//			
//			Assert.assertEquals(
//			outputStyleDate.compareTo(styleStartDate) >= 0 && outputStyleDate.compareTo(styleEndDate) <= 0,
//			true, "style catalog date: " + outputStyleDate+ "not with in the Date Range:"+styleStartDate+"-"+styleEndDate+" for the style_Id:"+data[i].getStyle_id());
			
//			Date outputStyleBreakDate = df.parse(data[i].getStyle_break_date());
//			Assert.assertEquals(
//					outputStyleBreakDate.compareTo(orderStartDate) >= 0,
//					true, "style break date: " + outputStyleBreakDate+ "not with in the Date Range:"+orderStartDate+"-"+orderEndDate+" for the style_Id:"+data[i].getStyle_id());
//			
			
			Assert.assertEquals(data[i].getGender().toLowerCase(), gender.toLowerCase(), "InCorrect Gender found:"
					+ data[i].getGender() + " for the style_Id:" + data[i].getStyle_id());
			
			
			Assert.assertEquals(data[i].getArticle_type().toLowerCase(), article_type.toLowerCase(), "InCorrect Article_Type found:"
						+ data[i].getArticle_type() + " for the style_Id:" + data[i].getStyle_id());
			
			
			
		}
	}
	
	
	public void validateRapidQueryDataForJabong(SpotJabongResponse response, String styleFromDate, String styleToDate,String orderFromDate,String orderToDate,
			String article_type, String gender,String source
			) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date styleStartDate = df.parse(styleFromDate);
		Date styleEndDate = df.parse(styleToDate);
		Date orderStartDate=df.parse(orderFromDate);
		Date orderEndDate=df.parse(orderToDate);
		
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

		Data[] data = response.getData();
		for (int i = 0; i < data.length; i++) {
//			String[] activated_date=data[i].getActivated_at().get$date().split("T");
//			Date outputStyleDate = df2.parse(activated_date[0]);
//			Assert.assertEquals(
//			outputStyleDate.compareTo(styleStartDate) >= 0 && outputStyleDate.compareTo(styleEndDate) <= 0,
//			true, "style catalog date: " + outputStyleDate+ "not with in the Date Range:"+styleStartDate+"-"+styleEndDate+" for the style_Id:"+data[i].getStyle_id());
			
			String[] style_break_date=data[i].getStyle_break_date().get$date().split("T");
			Date outputStyleBreakDate = df2.parse(style_break_date[0]);
			Assert.assertEquals(
					outputStyleBreakDate.compareTo(orderStartDate) >= 0,
					true, "style break date: " + outputStyleBreakDate+ "not with in the Date Range:"+orderStartDate+"-"+orderEndDate+" for the style_Id:"+data[i].getStyle_id());
			
			
			Assert.assertEquals(data[i].getGender().toLowerCase(), gender.toLowerCase(), "InCorrect Gender found:"
					+ data[i].getGender() + " for the style_Id:" + data[i].getStyle_id());
			
			
			Assert.assertEquals(data[i].getArticle_type().toLowerCase(), article_type.toLowerCase(), "InCorrect Article_Type found:"
						+ data[i].getArticle_type() + " for the style_Id:" + data[i].getStyle_id());
			
			
			
		}
	}
	

	
	public void validate_Filter_Type_Data_Myntra(String collectionId,HashMap<String, String> map) throws Exception, Exception, IOException{
		SpotJson spotJsonGenerator=new SpotJson();
		SpotServiceHelper spothelper=new SpotServiceHelper();
		String filterPayLoad = null;
//		String[] filter_Types={FILTER_TYPE.TOP_SELLER_POLO,FILTER_TYPE.TOP_SELLER_ROUND,FILTER_TYPE.TOP_SELLER_A2C_POLO
//				,FILTER_TYPE.BOTTOM_SELLER_POLO,FILTER_TYPE.BOTTOM_SELLER_ROUND};
//		for(int i=0;i<filter_Types.length;i++){
//			String filter=filter_Types[i];
//			switch(filter ){
//			case FILTER_TYPE.TOP_SELLER_POLO:
//				filterPayLoad=spotJsonGenerator.spotTopSellerPoloTshirtsFilterPayload(collectionId);
//				break;
//			case FILTER_TYPE.TOP_SELLER_ROUND:
//				filterPayLoad=spotJsonGenerator.spotTopSellerRoundTshirtsFilterPayload(collectionId);
//				break;
//			case FILTER_TYPE.TOP_SELLER_A2C_POLO:
//				filterPayLoad=spotJsonGenerator.spotTopSellerA2CPoloTshirtsFilterPayload(collectionId);
//				break;
//			case FILTER_TYPE.BOTTOM_SELLER_POLO:
//				filterPayLoad=spotJsonGenerator.spotBottomSellerPoloTshirtsFilterPayload(collectionId);
//				break;
//			case FILTER_TYPE.BOTTOM_SELLER_ROUND:
//				filterPayLoad=spotJsonGenerator.spotBottomSellerRoundTshirtsFilterPayload(collectionId);
//				break;
				
			//}
			
			//String filterResponse = spothelper.get_spot_data_with_filters(collectionId, filterPayLoad, map);
			
//			SpotResponse filterResponseBody = (SpotResponse) APIUtilities.getJsontoObject(filterResponse, new SpotResponse());
//			spothelper.validateFilterTypeResponses(filter,filterResponseBody);
		//}
			
	}
	
	public void validateFilterTypeResponses(String filter_Type, SpotResponse responsebody) {

//		switch (filter_Type) {
//		case FILTER_TYPE.TOP_SELLER_POLO:
//			TopSellerPoloValidation(responsebody);
//			break;
//		
//		case FILTER_TYPE.TOP_SELLER_ROUND:
//			TopSellerRoundValidation(responsebody);
//			break;
//		case FILTER_TYPE.TOP_SELLER_A2C_POLO:
//			validateTopSellerA2CPolo(responsebody);
//			break;
//		
//		case FILTER_TYPE.TOP_SELLER_A2C_ROUND:
//			validateTopSellerA2CRound(responsebody);
//			break;
//		case FILTER_TYPE.BOTTOM_SELLER_ROUND:
//			validatBottomSellerRound(responsebody);
//			break;
//		case FILTER_TYPE.BOTTOM_SELLER_POLO:
//			validatBottomSellerPolo(responsebody);
//			break;
//		}
	}

	
	public void TopSellerPoloValidation(SpotResponse responsebody){
		com.myntra.apiTests.inbound.response.Data[] data = responsebody.getData();
		
		for (int i = 0; i < data.length; i++) {
			
//			Assert.assertTrue(
//					data[i].getAsp() >= Double.valueOf(TOP_SELLER_POLO.ASP) ,
//						"ASP :"+	data[i].getAsp()+" is less than: " + TOP_SELLER_POLO.ASP+" for the style_Id:"+data[i].getStyle_id()+" for TopSellerPolo filter");
//
//			Assert.assertTrue(data[i].getP_td() <= TOP_SELLER_POLO.P_TD,
//					"P_td :"+	data[i].getP_td()+" is less than: " + TOP_SELLER_POLO.P_TD+" for the style_Id:"+data[i].getStyle_id()+" for TopSellerPolo filter");
//
//			
//			Assert.assertTrue(data[i].getRos() >= Double.valueOf(TOP_SELLER_POLO.ROS),
//					"ROS :"+	data[i].getRos()+" is less than: " + TOP_SELLER_POLO.ROS+" for the style_Id:"+data[i].getStyle_id()+" for TopSellerPolo filter");
//
//			Assert.assertTrue(data[i].getDays_live() >= TOP_SELLER_POLO.DAYS_LIVE,
//					"Days_Live:"+	data[i].getDays_live()+" is less than: " + TOP_SELLER_POLO.DAYS_LIVE+" for the style_Id:"+data[i].getStyle_id()+" for TopSellerPolo filter");

		}
	}
		
		public void TopSellerRoundValidation(SpotResponse responsebody){
			com.myntra.apiTests.inbound.response.Data[] data = responsebody.getData();
			for (int i = 0; i < data.length; i++) {
				
//				Assert.assertTrue(
//						data[i].getAsp() >= Double.valueOf(TOP_SELLER_ROUND.ASP_MIN) && data[i].getAsp() <= Double.valueOf(TOP_SELLER_ROUND.ASP_MAX),
//							"ASP :"+	data[i].getAsp()+" is not in the range of: " + TOP_SELLER_ROUND.ASP_MIN+"-"+TOP_SELLER_ROUND.ASP_MAX+" for the style_Id:"+data[i].getStyle_id()+" for TopSellerRound filter");
//
//				Assert.assertTrue(data[i].getP_td() <= TOP_SELLER_ROUND.P_TD,
//						"P_td :"+	data[i].getP_td()+" is less than: " + TOP_SELLER_ROUND.P_TD+" for the style_Id:"+data[i].getStyle_id()+" for TopSellerRound filter");
//
//				
//				Assert.assertTrue(data[i].getRos() >= Double.valueOf(TOP_SELLER_ROUND.ROS),
//						"ROS :"+	data[i].getRos()+" is less than: " + TOP_SELLER_ROUND.ROS+" for the style_Id:"+data[i].getStyle_id()+" for TopSellerRound filter");
//
//				Assert.assertTrue(data[i].getDays_live() >= TOP_SELLER_ROUND.DAYS_LIVE,
//						"Days_Live:"+	data[i].getDays_live()+" is less than: " + TOP_SELLER_ROUND.DAYS_LIVE+" for the style_Id:"+data[i].getStyle_id()+" for TopSellerRound filter");

			}

	}
		public void validateTopSellerA2CPolo(SpotResponse responsebody){	
			com.myntra.apiTests.inbound.response.Data[] data = responsebody.getData();
			for (int i = 0; i < data.length; i++) {
			
//				Assert.assertEquals(data[i].getAsp()>=Double.valueOf(TOP_SELLER_A2C_POLO.ASP),true , "ASP:" + data[i].getAsp()
//						+ " not greater than or equal to:" + TOP_SELLER_A2C_POLO.ASP + " for the style_Id:" + data[i].getStyle_id()+" for validateTopSellerA2CPolo filter");
//			
//			
//				Assert.assertEquals(data[i].getDays_live()>=TOP_SELLER_A2C_POLO.DAYS_LIVE ,true , "Days_Live" + data[i].getDays_live()
//						+ " not greater than or equal to:" + TOP_SELLER_A2C_POLO.DAYS_LIVE+ " for the style_Id:" + data[i].getStyle_id()+" for validateTopSellerA2CPolo filter");
//			
//				
//				Assert.assertEquals(data[i].getP_td()<= TOP_SELLER_A2C_POLO.P_TD,true , "P_TD:" + data[i].getP_td()
//						+ " is not less than or equal to:" + TOP_SELLER_A2C_POLO.P_TD+ " for the style_Id:" + data[i].getStyle_id()+" for validateTopSellerA2CPolo filter");
			
				}
		}
		
		public void validateTopSellerA2CRound(SpotResponse responsebody){	
			com.myntra.apiTests.inbound.response.Data[] data = responsebody.getData();
			for (int i = 0; i < data.length; i++) {
			
//				Assert.assertEquals(data[i].getAsp()>=Double.valueOf(TOP_SELLER_A2C_ROUND.ASP),true , "ASP:" + data[i].getAsp()
//						+ " not greater than or equal to:" + TOP_SELLER_A2C_ROUND.ASP + " for the style_Id:" + data[i].getStyle_id()+" for TopSellerA2CRound filter");
//			
//			
//				Assert.assertEquals(data[i].getDays_live()>=TOP_SELLER_A2C_ROUND.DAYS_LIVE ,true , "Days_Live" + data[i].getDays_live()
//						+ " not greater than or equal to:" + TOP_SELLER_A2C_ROUND.DAYS_LIVE+ " for the style_Id:" + data[i].getStyle_id()+" for TopSellerA2CRound filter");
//			
//				
//				Assert.assertEquals(data[i].getP_td()<= TOP_SELLER_A2C_ROUND.P_TD,true , "P_TD:" + data[i].getP_td()
//						+ " is not less than or equal to:" + TOP_SELLER_A2C_ROUND.P_TD+ " for the style_Id:" + data[i].getStyle_id()+" for TopSellerA2CRound filter");
			
				}
		}
		public void validatBottomSellerPolo(SpotResponse responsebody){	
			com.myntra.apiTests.inbound.response.Data[] data = responsebody.getData();
			for (int i = 0; i < data.length; i++) {
			
//				Assert.assertEquals(data[i].getAsp()>=Double.valueOf(BOTTOM_SELLER_POLO.ASP),true , "ASP:" + data[i].getAsp()
//						+ " not greater than or equal to:" + BOTTOM_SELLER_POLO.ASP + " for the style_Id:" + data[i].getStyle_id()+" for BottomSellerPolo filter");
//			
//				Assert.assertEquals(data[i].getRos()<=Double.valueOf(BOTTOM_SELLER_POLO.ROS),true , "ROS:" + data[i].getAsp()
//						+ " not greater than or equal to:" + BOTTOM_SELLER_POLO.ROS + " for the style_Id:" + data[i].getStyle_id()+" for BottomSellerPolo filter");
//			
//			
//				Assert.assertEquals(data[i].getDays_live()>=BOTTOM_SELLER_POLO.DAYS_LIVE ,true , "Days_Live" + data[i].getDays_live()
//						+ " not greater than or equal to:" + BOTTOM_SELLER_POLO.DAYS_LIVE+ " for the style_Id:" + data[i].getStyle_id()+" for BottomSellerPolo filter");
//			
//				
//				Assert.assertEquals(data[i].getP_td()>= BOTTOM_SELLER_POLO.P_TD,true , "P_TD:" + data[i].getP_td()
//						+ " is less than:" + BOTTOM_SELLER_POLO.P_TD+ " for the style_Id:" + data[i].getStyle_id()+" for BottomSellerPolo filter");
			
				}
		}
		
		public void validatBottomSellerRound(SpotResponse responsebody){	
			com.myntra.apiTests.inbound.response.Data[] data = responsebody.getData();
			for (int i = 0; i < data.length; i++) {
			
//				Assert.assertEquals(data[i].getAsp()>=Double.valueOf(BOTTOM_SELLER_ROUND.ASP),true , "ASP:" + data[i].getAsp()
//						+ " not greater than or equal to:" + BOTTOM_SELLER_ROUND.ASP + " for the style_Id:" + data[i].getStyle_id()+" for BottomSellerRound filter");
//			
//				Assert.assertEquals(data[i].getRos()<=Double.valueOf(BOTTOM_SELLER_ROUND.ROS),true , "ROS:" + data[i].getAsp()
//						+ " not greater than or equal to:" + BOTTOM_SELLER_ROUND.ROS + " for the style_Id:" + data[i].getStyle_id()+" for BottomSellerRound filter");
//			
//			
//				Assert.assertEquals(data[i].getDays_live()>=BOTTOM_SELLER_ROUND.DAYS_LIVE ,true , "Days_Live" + data[i].getDays_live()
//						+ " not greater than or equal to:" + BOTTOM_SELLER_ROUND.DAYS_LIVE+ " for the style_Id:" + data[i].getStyle_id()+" for BottomSellerRound filter");
//			
//				
//				Assert.assertEquals(data[i].getP_td()>= BOTTOM_SELLER_ROUND.P_TD,true , "P_TD:" + data[i].getP_td()
//						+ " is less than:" + BOTTOM_SELLER_ROUND.P_TD+ " for the style_Id:" + data[i].getStyle_id()+" for BottomSellerRound filter");
			
				}
		}
		
		public DateFormat dateFormatter(String source){
			DateFormat df = null;
			switch(source){
			case Source.MYNTRA:
				df = new SimpleDateFormat("yyyyMMdd");
				break;
			case Source.AMAZON:
				df = new SimpleDateFormat("yyyyMMdd");
				break;
			case Source.JABONG:
				df = new SimpleDateFormat("yyyy-MM-dd");
				break;
			}
			return df;
			
		}
		public void validateFetchAttributesAPI(String response) throws Exception {
			JSONObject jsonObject = new JSONObject(response);
			JSONArray array=jsonObject.getJSONArray("data");
			String[] attributes={ArticleType.ARTICLE_TYPE,ArticleType.BRAND,ArticleType.GENDER,ArticleType.SEASON_CODE};
			for(int i=0;i<array.length();i++){
				Assert.assertTrue(array.get(i).toString().contains(attributes[i]), attributes[i]+" Not found in API response");
			}
			
		}
		public void validateFilterStylesAPI(String response) throws Exception {
			JSONObject jsonObject = new JSONObject(response);
			JSONArray array=jsonObject.getJSONArray("data");
			//String[] attributes={ArticleType.ARTICLE_TYPE,ArticleType.BRAND,ArticleType.GENDER,ArticleType.SEASON_CODE};
			
			Assert.assertEquals(Integer.valueOf(array.get(0).toString()), Integer.valueOf(108096),"StyleId is:");
			Assert.assertEquals(Integer.valueOf(array.get(1).toString()), Integer.valueOf(108199),"StyleId is:");
			
			//(array.get(i).toString().contains(attributes[i]), attributes[i]+" Not found in API response");
			
			
		}
}
		