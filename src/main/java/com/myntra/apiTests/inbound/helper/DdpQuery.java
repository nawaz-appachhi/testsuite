package com.myntra.apiTests.inbound.helper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;

import com.myntra.apiTests.erpservices.Constants;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;

/**
 * 
 * @author 14977 DDP exposes a REST API to submit query and get results out of
 *         it.
 * 
 *         The flow of the ddp api's are as follows:
 * 
 *         Submit the SQL query to DDP and get QueryId Poll with the QueryId
 *         till the query is complete Obtain download link Download the result
 *         as a gzipped CSV
 * 
 *         POST http://54.251.230.158/executor-api/rest/v1/queryExecutor/submit
 *         Headers:
 * 
 *         submitted-by: <user>@myntra.com Content-Type:
 *         application/x-www-form-urlencoded
 * 
 *         Parameters: query = <ddp query> query-name=test
 *         query-description=test unload=true
 * 
 *         Poll query status: GET
 *         http://54.251.230.158/executor-api/rest/v1/queryExecutor/
 *         <queryid>/status GET
 *         http://54.251.230.158/executor-api/rest/v1/queryExecutor/
 *         <queryid>/download
 */

public class DdpQuery {

	private static Logger log = Logger.getLogger(DdpQuery.class);

	public final static String DDP_API_HOST = "http://54.251.230.158/executor-api/rest/v1/queryExecutor";
	public final static String DDP_SUBMIT_QUERY = DDP_API_HOST + "/submit";
	public static String response;
	public String status;
	public Svc svc;
	public FileOutputStream fileOutputStream;
	public byte[] buffer;

	// this function returns you the queryID,inputs to this functions are the
	// parameters and the userid
	public String get_query_id_from_ddp(List<NameValuePair> urlParameters, String user)
			throws UnsupportedEncodingException, JSONException {

		
		log.info("Getting the queryID from the ddp api");

		svc = HttpExecutorService.executeHttpServiceForUrlEncode(Constants.DDP_PATH.GET_DDP_SUBMIT, null,
				SERVICE_TYPE.DDP_SVC.toString(), HTTPMethods.POST, urlParameters, getDdpHeaders(user));
		response = svc.getResponseBody();
		String queryID = getjsonObject(response, "queryId");
		return queryID;
	}

	// this function is to poll query status till the download_url is returned
	// we are passing the queryid generated from function get_query_id_from_ddp
	// to this method and the userid
	public void get_query_status(String queryID, String user) throws UnsupportedEncodingException, JSONException {
		long startTime = System.currentTimeMillis();

		log.info("wait till the queryStatus becomes completed");

		do {
			svc = HttpExecutorService.executeHttpService(queryID + Constants.DDP_PATH.GET_DDP_STATUS, null,
					SERVICE_TYPE.DDP_SVC.toString(), HTTPMethods.GET, null, getDdpHeaders(user));
			response = svc.getResponseBody();
			status = getjsonObject(response, "queryStatus");
			System.out.println("status of query is: " + status);
		} while ((status != "COMPLETED") && (System.currentTimeMillis() - startTime) < 500000);
		log.info("The query status is" +status );
	}

	// this function gets the download link from the download url.Inputs are
	// queryID and the userid
	public String get_download_link(String queryID, String user) throws UnsupportedEncodingException {


		log.info("Getting the ddp query data downloadlink");

		svc = HttpExecutorService.executeHttpService(queryID + Constants.DDP_PATH.GET_DDP_DOWNLOAD_LINK, null,
				SERVICE_TYPE.DDP_SVC.toString(), HTTPMethods.GET, null, getDdpHeaders(user));
		response = svc.getResponseBody();
		return response;

	}

	// json parser to retrieve pojo object values
	public static String getjsonObject(String response, String Obj) throws JSONException {
		JSONObject jsonObject = new JSONObject(response);
		String jsonobj = jsonObject.getString(Obj);
		return jsonobj;
	}

	// to get the current date in yyyymmdd format
	public static String getCurrentTimeStamp() {
		Date date = new Date();
		String modifiedDate = new SimpleDateFormat("yyyyMMdd").format(date);
		return modifiedDate;
	}

	// to get day before yesterday date in yyyymmdd format

	public static String getLastNthDay(int days) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);

		return dateFormat.format(cal.getTime());
	}

	// return List of namevaluepair
	public List<NameValuePair> passParameters(String query_data, String query_name, String query_description,
			String unload) {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("query", query_data));
		urlParameters.add(new BasicNameValuePair("query-name", query_name));
		urlParameters.add(new BasicNameValuePair("query-description", query_description));
		urlParameters.add(new BasicNameValuePair("unload", unload));

		return urlParameters;
	}

	// returns headers for the http requests
	public HashMap<String, String> getDdpHeaders(String user) {
		HashMap<String, String> ddpheaders = new HashMap<String, String>();
		ddpheaders.put("submitted-by", user);
		ddpheaders.put("Content-Type", "application/x-www-form-urlencoded");
		return ddpheaders;
	}

	// to extract the compressed file
	public void unGunzipFile(String compressedFile, String decompressedFile) {

		buffer = new byte[1024];

		try {

			FileInputStream fileIn = new FileInputStream(compressedFile);

			GZIPInputStream gZIPInputStream = new GZIPInputStream(fileIn);

			fileOutputStream = new FileOutputStream(decompressedFile);

			int bytes_read;

			while ((bytes_read = gZIPInputStream.read(buffer)) > 0) {

				fileOutputStream.write(buffer, 0, bytes_read);
			}

			gZIPInputStream.close();
			fileOutputStream.close();
			log.debug("The file was decompressed successfully! " + decompressedFile);
			System.out.println("The file was decompressed successfully!");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// to read the downloaded file
	public String displayTextInputStream(InputStream input) throws IOException, Exception {
		// Read one text line at a time and display.

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(input));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	// to download the file
	public void downloadUsingStream(String urlStr, String file) throws IOException {
		URL url = new URL(urlStr);
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		fileOutputStream = new FileOutputStream(file);
		buffer = new byte[1024];
		int count = 0;
		while ((count = bis.read(buffer, 0, 1024)) != -1) {
			fileOutputStream.write(buffer, 0, count);
		}
		fileOutputStream.close();
		bis.close();
	}

}
