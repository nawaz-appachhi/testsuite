package com.myntra.apiTests.testdevelopmentrnd;

import argo.saj.InvalidSyntaxException;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.dataproviders.devRequestsDP;
import com.myntra.apiTests.InitializeFramework;


import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.Payload;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by ashish.bajpai on 21/05/14.
 */
public class devRequests extends devRequestsDP
{

	@Test(groups = { "Devask" }, dataProvider = "DevaskDP")
	public void BrandsTest(String param1, String param2, String param3,
			String param4) throws InvalidSyntaxException, InterruptedException
	// public void BrandsTest()
	{
		System.out.println("param1+param2+param3+param3 = " + param1 + param2
				+ param3 + param3);
		Thread.sleep(1000);
		Payload pl = new Payload(APINAME.GETQUERY.toString(),
				InitializeFramework.init.Configurations, PayloadType.JSON);

		String PayLoad = pl.ParamatereizedPayload(new String[] { param1,
				param2, param3, param4 });
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				InitializeFramework.init.Configurations, PayLoad);
		service.URL = GetRandomEndpoint();// "http://180.179.145.1:7400/search-service/searchservice/search/getquery";
		RequestGenerator req = new RequestGenerator(service);
		// req.respvalidate.
		System.out.println("URL = " + service.URL);
		String response = req.respvalidate.returnresponseasstring();

		data(param1 + ","
				+ JsonPath.read(response, "$.data.queryType.[0]").toString()
				+ "\n");
		// data(param1+","+req.respvalidate.GetNodeValue_Argo("data.queryType",
		// req.respvalidate.returnresponseasstring()));
		// data(service)
		// AssertJUnit.assertEquals("getQueryWithTrueReturnDoc is not working",200,
		// req.response.getStatus());
	}

	public String GetRandomEndpoint()
	{
		String[] fruits = {
				"http://180.179.145.1:7400/search-service/searchservice/search/getquery",
				"http://180.179.145.2:7400/search-service/searchservice/search/getquery",
				"http://180.179.145.7:7400/search-service/searchservice/search/getquery" };
		int idx = new Random().nextInt(fruits.length);
		return (fruits[idx]);
	}

	public void data(String data)
	{
		try
		{
			// String data = " This content will append to the end of the file";
			System.out.println("data = " + data);
			// File outputfile = new File("Users/ashish.bajpai/brands.txt");
			/*
			 * if(!outputfile.exists()){ outputfile.createNewFile(); }
			 */
			FileWriter fileWritter = new FileWriter(
					"/Users/ashish.bajpai/brands.txt", true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(data);
			bufferWritter.close();

			System.out.println("Done");
			/*
			 * File outputFile = new File("outagain.txt");
			 * 
			 * //FileInputStream fis = new FileInputStream(inputFile);
			 * FileOutputStream fos = new FileOutputStream(outputFile); int c;
			 * 
			 * while ((c = fis.read()) != -1) { fos.write(c); }
			 * 
			 * fis.close(); fos.close();
			 */
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
