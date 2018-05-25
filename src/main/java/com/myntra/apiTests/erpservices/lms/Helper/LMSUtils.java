package com.myntra.apiTests.erpservices.lms.Helper;

import com.myntra.lms.serviceabilityV2.response.CourierServiceabilityInfoResponseV2;
import com.myntra.serviceability.client.response.OrderServiceabilityDetailResponse;
import com.myntra.test.commons.service.impl.BaseSvcImpl;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

//import com.myntra.serviceability.client.response.OrderServiceabilityDetailResponse;

public class LMSUtils {

	public static String randomGenn(int length) {

		Random random = new Random();
		int n = random.nextInt(10);
		for (int i = 0; i < length - 1; i++) {
			n = n * 10;
			n += random.nextInt(10);
		}
		String ar = Integer.toString(Math.abs(n));
		return ar;
	}

	public static String getDate() {

		DateTime date = new DateTime();
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		String str = fmt.print(date);
		return str;
	}

	public static String getCurrSqlDate() {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String addDate(int days) {

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(cal.getTime());
	}
	public String put(String baseUrl, String endPoint, String payload, Map<String, String> requestHeader)
			throws UnsupportedEncodingException {
		
		String responseMessage = "";
		BaseSvcImpl baseSvcImpl = new BaseSvcImpl();
		HttpPut httpPut = new HttpPut(baseUrl + endPoint);
		
		for (String key : requestHeader.keySet()) {
			httpPut.addHeader(key, requestHeader.get(key));
		}
		
		StringEntity se = new StringEntity(payload);
		httpPut.setEntity(se);
		
		try (CloseableHttpClient httpclient = baseSvcImpl.getClient()) {
			
			CloseableHttpResponse response = httpclient.execute(httpPut);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				
				try (BufferedReader buffer = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()))) {
					
					// apiResponse.setResponseHeaderMap(getHeaderMap(response.getAllHeaders()));
					// apiResponse.setResponseCode(String.valueOf(response.getStatusLine().getStatusCode()));
					responseMessage = buffer.lines().collect(Collectors.joining(System.getProperty("line.separator")));
				}
			} else {
				
				System.out.println("Response Code - " + response.getStatusLine().getStatusCode());
			}

		System.out.println("Payload - " + payload);
			System.out.println("Response - " + responseMessage);
		} catch (IOException e) {
			throw new RuntimeException("Error calling put request " + e.getMessage(), e);
		}
		
		return responseMessage;
	}
	
	public String post(String baseUrl, String endPoint, String payload, Map<String, String> requestHeader)
			throws UnsupportedEncodingException {
		
		String responseMessage = "";
		BaseSvcImpl baseSvcImpl = new BaseSvcImpl();
		HttpPost httpPost = new HttpPost(baseUrl + endPoint);
		
		for (String key : requestHeader.keySet()) {
			httpPost.addHeader(key, requestHeader.get(key));
		}
		
		StringEntity se = new StringEntity(payload);
		httpPost.setEntity(se);
		
		try (CloseableHttpClient httpclient = baseSvcImpl.getClient()) {
			
			CloseableHttpResponse response = httpclient.execute(httpPost);
			
			try (BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
				
				// apiResponse.setResponseHeaderMap(getHeaderMap(response.getAllHeaders()));
				// apiResponse.setResponseCode(String.valueOf(response.getStatusLine().getStatusCode()));
				responseMessage = buffer.lines().collect(Collectors.joining(System.getProperty("line.separator")));
				
				System.out.println("Payload - " + payload);
				System.out.println("Response Obj - " + responseMessage);
			}
		} catch (IOException e) {
			throw new RuntimeException("Error calling post request " + e.getMessage(), e);
		}
		
		return responseMessage;
	}
	
	public static <T> Object getRandomObject(List<T> inputList) {
		
		if (inputList != null && !inputList.isEmpty()) {
			
			Random rand = new Random();
			int random = rand.nextInt(inputList.size());
			return inputList.get(random);
		} else {
			
			return null;
		}
	}
	
	public static <T> Object getRandomObject(Set<T> inputSet) {
		
		if (inputSet != null && !inputSet.isEmpty()) {
			
			Random rand = new Random();
			List<Object> inputList = new ArrayList<>(inputSet);
			int random = rand.nextInt(inputList.size());
			return inputList.get(random);
		} else {
			
			return null;
		}
	}
	public static <T> T getJavaFromXml(String xml, Class<T> clazz) throws JAXBException {
		
		if (xml != null && !xml.isEmpty()) {
			
			JAXBContext jc = JAXBContext.newInstance(CourierServiceabilityInfoResponseV2.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			@SuppressWarnings("unchecked")
			T responseObject = (T) unmarshaller.unmarshal(reader);
			return (T) responseObject;
		} else {
			
			return null;
		}
	}
	
	public static <T> T getJavaFromXml_MT(String xml, Class<T> clazz) throws JAXBException {
		
		if (xml != null && !xml.isEmpty()) {
			
			JAXBContext jc = JAXBContext.newInstance(OrderServiceabilityDetailResponse.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			@SuppressWarnings("unchecked")
			T responseObject ;
			responseObject=	(T) unmarshaller.unmarshal(reader);
			return responseObject;
		} else {
			
			return null;
		}
	}
}
