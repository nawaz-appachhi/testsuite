package com.myntra.apiTests.erpservices.lms.Helper;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class HttpPostMultipartExample {
	public static void main(String[] args) throws InterruptedException, IOException, JAXBException {
		/*File file = new File("t_Box_Template_1.csv");
		FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addPart("file", fileBody);
		
		HttpEntity entity = builder.build();
		
		HttpPost request = new HttpPost("http://xyz.abc.com/xas-asd-service/platform/v1/pickup/uploadPickupStatus");
		request.setEntity(entity);
		request.setHeader("Authorization", "Basic RVJQIEFkbWluaXN0cmF0b3J+ZXJwYWRtaW46dGVzdA==");
		HttpClient client = HttpClientBuilder.create().build();
		try {
			HttpResponse response = client.execute(request);
		System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	LMS_ReturnHelper lms= new LMS_ReturnHelper();
	//lms.uploadItemsFile();
	lms.getPickupShipmentDetailsLMS(String.valueOf(2001));
	}
	
}