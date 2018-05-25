package com.myntra.apiTests.portalservices.pricingpromotionservices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.PricingAnalyticsDP;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class PricingAnalytics extends PricingAnalyticsDP {
	static Initialize init = new Initialize("/Data/configuration");
	ArrayList datausage = new ArrayList();
	ArrayList merchandise = new ArrayList();
	ArrayList article = new ArrayList();

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getStyleData")
	public void getStyleData(Integer styleId) {
//		String dbConnection_Devint = "jdbc:mysql://"+"54.251.242.151"+"/pricinganalytics?"+"user=myntraAppDBUser&password=PRICINGteam&port=3306";
//		ArrayList arr1 = getStyle(dbConnection_Devint);
//		System.out.println("arr in methiod" + arr1);
		MyntraService myntraService = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATA, init.Configurations, PayloadType.JSON,new String[]{styleId.toString()},PayloadType.JSON);
		System.out.println("URL ------->>" + myntraService.URL);
		RequestGenerator requestGenerator = new RequestGenerator(myntraService);
		System.out.println("---------------->>>>>>>>" +requestGenerator.respvalidate.returnresponseasstring()  );

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
//		String response = requestGenerator.respvalidate.returnresponseasstring();
//		String percentage = JsonPath.read(response, "$.styleId").toString().trim();
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression"})
	public void getStyleCount() {
		
		String dbConnection_Devint = "jdbc:mysql://"+"54.251.242.151"+"/pricinganalytics?"+"user=myntraAppDBUser&password=PRICINGteam&port=3306";
		int count = getCount(dbConnection_Devint);
		String CountNew = String.valueOf(count);
		System.out.println(">>>>>>>>>>>>>>> count" + count);
		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLECOUNT,
				init.Configurations);
		RequestGenerator requestGenerator = new RequestGenerator(myntraService);
		System.out.println("---------------->>>>>>>>" +requestGenerator.respvalidate.returnresponseasstring()  );
		String Countapi = requestGenerator.respvalidate.returnresponseasstring();
		if(CountNew.equals(Countapi)){
			System.out.println("COunt matched");
		}
		AssertJUnit.assertEquals("Count is not macthed", CountNew, Countapi);

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
		
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" },dataProvider = "getmerchandiseTypeWithData")
	public void getStyleDataPage(String page , String data ,String merchandiseType ) {
		
		MyntraService myntraService = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE, init.Configurations, PayloadType.JSON,new String[]{page,data,merchandiseType},PayloadType.JSON);
		System.out.println("URL ------->>" + myntraService.URL);
		RequestGenerator requestGenerator = new RequestGenerator(myntraService);
		System.out.println("---------------->>>>>>>>" +requestGenerator.respvalidate.returnresponseasstring()  );
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
        datausage = JsonPath.read(jsonRes, "$.styleList[*]");
        String jsonres1 = requestGenerator.respvalidate.returnresponseasstring();
        merchandise = JsonPath.read(jsonRes, "$.styleList..merchandiseType");
        System.out.println(merchandiseType);
        for(int i=0;i<merchandise.size();i++)
        {
        	if(merchandise.contains(merchandiseType))
        	{
        		System.out.println("Same MmerchandiseType is available");
        	}
        	else
        	{
        		System.out.println("This is ok ");
        	}
        }
        
        int count = datausage.size();
        String count1 = Integer.toString(count);
        if(count1.equals("0"))
        {
        	System.out.println("list if style id doesn't have data usage");
        }
        
        else
        	{
        	System.out.println("---------------------" + count);
        	AssertJUnit.assertEquals("data not equal", data, count1);
        	}
        

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getArticleReferenceData")
	public void getarticlereferencedata(String pageIndex, String pageSize
			) {

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS,
				APINAME.GETARTICLEREFERENCEDATA, init.Configurations,
				PayloadType.JSON, new String[] { pageIndex, pageSize},
				PayloadType.JSON);
		System.out.println("URL ------->>" + myntraService.URL);
		RequestGenerator requestGenerator = new RequestGenerator(myntraService);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		datausage = JsonPath.read(jsonRes, "$.styleList[*]");
//		String jsonres1 = requestGenerator.respvalidate
//				.returnresponseasstring();
		article = JsonPath.read(jsonRes, "$.referenceDataList..referenceDataType");
		System.out.println(article);
		int count =  article.size();
		String countq = Integer.toString(count);
	
		for (int i = 0; i < article.size(); i++) {
			if (article.get(i).equals("ARTICLE")) {
				System.out.println("Same ARticle is available");
			} else {
				System.out.println("This is ok ");
			}
		}
//
//		int count = datausage.size();
//		String count1 = Integer.toString(count);
//		if (count1.equals("0")) {
//			System.out.println("list if style id doesn't have data usage");
//		}
//
//		else {
//			System.out.println("---------------------" + count);
//			AssertJUnit.assertEquals("data not equal", data, count1);
//		}
//		AssertJUnit.assertEquals("Count not equal", countq, pageSize);


		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" }, dataProvider = "getGendersReferenceData")
public void getgendersreferencedata(String pageIndex, String pageSize
	) {

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS,
				APINAME.GETGENDERSREFERENCEDATA, init.Configurations,
				PayloadType.JSON, new String[] { pageIndex, pageSize },
				PayloadType.JSON);
		System.out.println("URL ------->>" + myntraService.URL);
		RequestGenerator requestGenerator = new RequestGenerator(myntraService);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		datausage = JsonPath.read(jsonRes, "$.styleList[*]");
//		String jsonres1 = requestGenerator.respvalidate
//				.returnresponseasstring();
		article = JsonPath.read(jsonRes, "$.referenceDataList..referenceDataType");
		System.out.println(article);
		int count =  article.size();
		String countq = Integer.toString(count);
	
		for (int i = 0; i < article.size(); i++) {
			if (article.get(i).equals("GENDER")) {
				System.out.println("Same GENDER is available");
			} else {
				System.out.println("This is ok ");
			}
		}

//		AssertJUnit.assertEquals("Count not equal", countq, pageSize);

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression" }, dataProvider = "getGendersReferenceData")
public void getBrandbReferenceData(String pageIndex, String pageSize
	) {

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS,
				APINAME.GETBRANDREFERENCEDATA, init.Configurations,
				PayloadType.JSON, new String[] { pageIndex, pageSize },
				PayloadType.JSON);
		System.out.println("URL ------->>" + myntraService.URL);
		RequestGenerator requestGenerator = new RequestGenerator(myntraService);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		datausage = JsonPath.read(jsonRes, "$.styleList[*]");
//		String jsonres1 = requestGenerator.respvalidate
//				.returnresponseasstring();
		article = JsonPath.read(jsonRes, "$.referenceDataList..referenceDataType");
		System.out.println(article);
		int count =  article.size();
		String countq = Integer.toString(count);
	
		for (int i = 0; i < article.size(); i++) {
			if (article.get(i).equals("BRAND")) {
				System.out.println("Same BRAND is available");
			} else {
				System.out.println("This is ok ");
			}
		}

//		AssertJUnit.assertEquals("Count not equal", countq, pageSize);

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getGendersReferenceData")
	public void getCategoryReferenceData(String pageIndex, String pageSize) {

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS,
				APINAME.GETCATEGORYREFERENCEDATA, init.Configurations,
				PayloadType.JSON, new String[] { pageIndex, pageSize },
				PayloadType.JSON);
		System.out.println("URL ------->>" + myntraService.URL);
		RequestGenerator requestGenerator = new RequestGenerator(myntraService);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		datausage = JsonPath.read(jsonRes, "$.styleList[*]");
//		String jsonres1 = requestGenerator.respvalidate
//				.returnresponseasstring();
		article = JsonPath.read(jsonRes, "$.referenceDataList..referenceDataType");
		System.out.println(article);
//		int count =  article.size();
//		String countq = Integer.toString(count);
	
		for (int i = 0; i < article.size(); i++) {
			if (article.get(i).equals("CATEGORY")) {
				System.out.println("Same CATEGORY is available");
			} else {
				System.out.println("This is ok ");
			}
		}

//		AssertJUnit.assertEquals("Count not equal", countq, pageSize);
		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getGendersReferenceData")
	public void getSubCategoryReferenceData(String pageIndex, String pageSize) {

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS,
				APINAME.GETSUBCATEGORYREFERENCEDATA, init.Configurations,
				PayloadType.JSON, new String[] { pageIndex, pageSize },
				PayloadType.JSON);
		System.out.println("URL ------->>" + myntraService.URL);
		RequestGenerator requestGenerator = new RequestGenerator(myntraService);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		datausage = JsonPath.read(jsonRes, "$.styleList[*]");
//		String jsonres1 = requestGenerator.respvalidate
//				.returnresponseasstring();
		article = JsonPath.read(jsonRes, "$.referenceDataList..referenceDataType");
		System.out.println(article);
		int count =  article.size();
		String countq = Integer.toString(count);
	
		for (int i = 0; i < article.size(); i++) {
			if (article.get(i).equals("SUBCATEGORY")) {
				System.out.println("Same SUBCATEGORY is available");
			} else {
				System.out.println("This is ok ");
			}
		}
//		AssertJUnit.assertEquals("Count not equal", countq, pageSize);

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void refreshArticleData() {

		
		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.REFRESHARTICLEDATA,
				init.Configurations,PayloadType.JSON, PayloadType.JSON);
		
		System.out.println("URL ------->>" + myntraService.URL);
		RequestGenerator requestGenerator = new RequestGenerator(myntraService);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		
		// AssertJUnit.assertEquals("Count not equal", countq, pageSize);
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$.msg").toString();
		System.out.println("Messagee -- >" + msg1);

		 AssertJUnit.assertEquals("data refreshing not initiated.", msg1, "data refreshing job initiated for ARTICLE");

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void refreshBrandsData() {

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS,
				APINAME.REFRESHBRANDSDATA, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);

		System.out.println("URL ------->>" + myntraService.URL);
		RequestGenerator requestGenerator = new RequestGenerator(myntraService);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$.msg").toString();
		System.out.println("Messagee -- >" + msg1);

		 AssertJUnit.assertEquals("data refreshing not initiated.", msg1, "data refreshing job initiated for BRAND");

		// AssertJUnit.assertEquals("Count not equal", countq, pageSize);

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void refreshCategoryData() {

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.REFRESHCATEGORYDATA,
				init.Configurations, PayloadType.JSON, PayloadType.JSON);

		System.out.println("URL ------->>" + myntraService.URL);
		RequestGenerator requestGenerator = new RequestGenerator(myntraService);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$.msg").toString();
		System.out.println("Messagee -- >" + msg1);

		 AssertJUnit.assertEquals("data refreshing not initiated.", msg1, "data refreshing job initiated for CATEGORY");

		// AssertJUnit.assertEquals("Count not equal", countq, pageSize);

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void refreshGendersData() {

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS,
				APINAME.REFRESHGENDERSDATA, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);

		System.out.println("URL ------->>" + myntraService.URL);
		RequestGenerator requestGenerator = new RequestGenerator(myntraService);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$.msg").toString();
		System.out.println("Messagee -- >" + msg1);

		 AssertJUnit.assertEquals("data refreshing not initiated.", msg1, "data refreshing job initiated for GENDER");

		// AssertJUnit.assertEquals("Count not equal", countq, pageSize);

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void refreshStylesData() {

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS,
				APINAME.REFRESHSTYLEDATA, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);

		System.out.println("URL ------->>" + myntraService.URL);
		RequestGenerator requestGenerator = new RequestGenerator(myntraService);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
//		String msg1 = JsonPath.read(jsonRes, "$.msg").toString();
//		System.out.println("Messagee -- >" + msg1);

//		 AssertJUnit.assertEquals("data refreshing not initiated.", msg1, "data refreshing job initiated for STYLES");
		// AssertJUnit.assertEquals("Count not equal", countq, pageSize);

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void refreshSubCategoryData() {

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.REFRESHSUBCATEGORYDATA,
				init.Configurations, PayloadType.JSON, PayloadType.JSON);

		System.out.println("URL ------->>" + myntraService.URL);
		RequestGenerator requestGenerator = new RequestGenerator(myntraService);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$.msg").toString();
		System.out.println("Messagee -- >" + msg1);

		 AssertJUnit.assertEquals("data refreshing not initiated.", msg1, "data refreshing job initiated for SUBCATEGORY");

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	
	private int getCount(String conString){
		Statement stmt = null;
		Connection con = null;
		int count = 0;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(conString);
			System.out.println(":::::::::::::::"+con);
			stmt = con.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String query = "select count(*) from style";
		System.out.println(query);
		try {
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				count = rs.getInt("count(*)");
				//System.out.println("count(*)"+ count);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		return count;
		
	}
	
	private ArrayList<Integer> getStyle(String conString){
		Statement stmt = null;
		Connection con = null;
		int count = 0;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(conString);
			System.out.println(":::::::::::::::"+con);
			stmt = con.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String query = "select style_id from style";
		List<Integer>arr = new ArrayList<Integer>();
		System.out.println(query);
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
			    int id = rs.getInt(1);
			 //   System.out.println("STUyle ->>>>> " +id );
			    // Some other actions
			    arr.add(id);
			}
			System.out.println("array="+arr);
//			ResultSet rs = stmt.executeQuery(query);
//			while(rs.next()){
//				count = rs.getInt("count(*)");
//				//System.out.println("count(*)"+ count);
//			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		return (ArrayList<Integer>) arr;
		
	}
}
