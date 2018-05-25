package com.myntra.apiTests.dataproviders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.portalservices.tagService.tagServiceHelper;
import com.myntra.lordoftherings.Toolbox;

public class PricingAnalyticsDP {
	tagServiceHelper helper = new tagServiceHelper();

	@DataProvider
	public Object[][] getStyleData(ITestContext iTestContext) {
		int j = 0;
		String dbConnection_Devint = "jdbc:mysql://"+"54.251.242.151"+"/pricinganalytics?"+"user=myntraAppDBUser&password=PRICINGteam&port=3306";
		ArrayList<Integer> arr1 = getStyle(dbConnection_Devint);
		Object[][] dataSet = new Object[arr1.size()][];
		for (Object item : arr1) {
			
			dataSet[j] = new Object[] { item};
			++j;
		}
		/*if (keyFromJenkins != null && valueFromJenkins != null) {
			dataSet[j] = new Object[] { keyFromJenkins, valueFromJenkins };
		}*/

		return dataSet;
//		String[] arr1 = { helper.getOneStyleID() };
//		String[] arr2 = { helper.getOneStyleID() };
//		String[] arr3 = { helper.getOneStyleID() };
//		String[] arr4 = { helper.getOneStyleID() };
//		Object[][] dataSet = { arr1, arr2, arr3, arr4 };
//		return Toolbox.returnReducedDataSet(dataSet,
//				iTestContext.getIncludedGroups(), 4, 4);
	}
	
	
	
	
	@DataProvider
	public Object[][] getmerchandiseTypeWithData(ITestContext iTestContext) {
		String[] arr1 = {"0","10","SOR"};
		String[] arr2 = {"0","10","MMP"};
		String[] arr3 = {"0","10","MFB"};
		String[] arr4 = {"2","15","SOR"};
		String[] arr5 = {"2","15","MMP"};
		String[] arr6 = {"0","15","MFB"};
		String[] arr7 = {"1","20","SOR"};
		String[] arr8 = {"2","21","MMP"};
		String[] arr9 = {"0","30","MFB"};
		Object[][] dataSet = { arr1, arr2, arr3, arr4,arr5, arr6, arr7, arr8,arr9};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 8, 8);
	}
	
	
	
	@DataProvider
	public Object[][] getArticleReferenceData(ITestContext iTestContext) {
		String[] arr1 = {"0","10"};
		String[] arr2 = {"0","20"};
		String[] arr3 = {"0","40"};
		String[] arr4 = {"0","50"};
		String[] arr5 = {"0","70"};
		String[] arr6 = {"0","80"};
		
		Object[][] dataSet = { arr1, arr2,arr3,arr4,arr5,arr6};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 6, 6);
	}
	
	
	
	@DataProvider
	public Object[][] getGendersReferenceData(ITestContext iTestContext) {
		String[] arr1 = {"0","10"};
		String[] arr2 = {"0","20"};
		String[] arr3 = {"0","40"};
		String[] arr4 = {"0","50"};
		String[] arr5 = {"0","70"};
		String[] arr6 = {"0","80"};
		
		Object[][] dataSet = { arr1, arr2,arr3,arr4,arr5,arr6};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 6, 6);
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
		
		String query = "select style_id from style limit 100";
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
