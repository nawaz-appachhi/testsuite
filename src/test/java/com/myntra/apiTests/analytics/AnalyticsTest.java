package com.myntra.apiTests.analytics;

import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.core.HttpHeaders;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;

import static org.testng.Assert.assertFalse;

public class AnalyticsTest 
{
	String testQuery="select 	(to_char(a.yday_revenue,'99,99,99,99,999')) as yday_rev, 	trunc((a.yday_revenue - a.yday_minus_1_revenue)*100/nullif(a.yday_minus_1_revenue,0),1) as p1,  	a.yday_mobile_revenue/yday_revenue as yday_mobile_rev, 	(a.yday_mobile_revenue/yday_revenue) - (yday_minus_1_mobile_revenue/yday_mobile_revenue)/(a.yday_mobile_revenue/yday_revenue) as p4,  	(to_char(a.yday_order,'99,99,99,99,999')) as yday_order, 	trunc((a.yday_order - a.yday_minus_1_order)*100/nullif(a.yday_minus_1_order,0),1) as p2, 	(to_char(a.yday_revenue/a.yday_order,'99,99,99,99,999')) as yday_asp, 	trunc((a.yday_revenue/nullif(a.yday_order,0) - a.yday_minus_1_revenue/nullif(a.yday_minus_1_order,0))*100/(a.yday_minus_1_revenue/nullif(a.yday_minus_1_order,0)),1) as p3, 	 	to_char(a.mtd_revenue,'99,99,99,99,999') as mtd_revenue,  	to_char(a.month_minus_1_revenue,'99,99,99,99,999') as month_minus_1_revenue,  	to_char(a.month_minus_2_revenue,'99,99,99,99,999') as month_minus_2_revenue,  	to_char(a.month_minus_3_revenue,'99,99,99,99,999') as month_minus_3_revenue, 	 	to_char(a.yday_item_count,'99,99,99,99,999') as yday_item_count,  	to_char(a.mtd_item_count,'99,99,99,99,999') as mtd_item_count,  	to_char(a.month_minus_1_item_count,'99,99,99,99,999') as month_minus_1_item_count,  	to_char(a.month_minus_2_item_count,'99,99,99,99,999') as month_minus_2_item_count, 	to_char(a.month_minus_3_item_count,'99,99,99,99,999') as month_minus_3_item_count,	 	trunc((a.yday_item_count - a.yday_minus_1_item_count)*100/nullif(a.yday_minus_1_item_count,0),1) as p6,  	 	to_char(a.month_minus_1_revenue,'99,99,99,99,999') as month_minus_1_revenue,  	to_char(a.month_minus_2_revenue,'99,99,99,99,999') as month_minus_2_revenue,  	to_char(a.month_minus_3_revenue,'99,99,99,99,999') as month_minus_3_revenue, 	 	 	 	a.mtd_mobile_revenue/mtd_revenue as mtd_mobile_revenue,  	a.month_minus_1_mobile_revenue/month_minus_1_revenue as month_minus_1_mobile_revenue,  	a.month_minus_2_mobile_revenue/month_minus_2_revenue as month_minus_2_mobile_revenue,  	a.month_minus_3_mobile_revenue/month_minus_3_revenue as month_minus_3_mobile_revenue, 	a.mtd_order,  	a.month_minus_1_order,     month_minus_2_order,     month_minus_3_order, 	a.mtd_revenue/nullif(a.mtd_order,0) as mtd_asp,  	a.month_minus_1_revenue/nullif(a.month_minus_1_order,0) as month_minus_1_asp,  	a.month_minus_2_revenue/nullif(month_minus_2_order,0) as month_minus_2_asp,  	a.month_minus_3_revenue/nullif(month_minus_3_order,0) as month_minus_3_asp from 	 	(select  		sum(case when dt.day_diff = 1 then o.shipped_order_revenue_inc_cashback end) as yday_revenue, 		sum(case when dt.day_diff = 1 and o.order_channel like 'mobile%' then o.shipped_order_revenue_inc_cashback end) as yday_mobile_revenue, 		count(distinct (case when dt.day_diff = 1 then o.order_group_id end)) as yday_order, 		sum(case when dt.day_diff = 1 then o.shipped_item_count end) as yday_item_count, 	 		sum(case when dt.day_diff = 2 then o.shipped_order_revenue_inc_cashback end) as yday_minus_1_revenue, 		sum(case when dt.day_diff = 2 and o.order_channel like 'mobile%' then o.shipped_order_revenue_inc_cashback end) as yday_minus_1_mobile_revenue, 		count(distinct (case when dt.day_diff = 2 then o.order_group_id end)) as yday_minus_1_order, 		sum(case when dt.day_diff = 2 then o.shipped_item_count end) as yday_minus_1_item_count, 	 		 		sum(case when dt.month_to_date = 1 then o.shipped_item_count end) as mtd_item_count, 		sum(case when dt.month_diff = 1 then o.shipped_item_count end) as month_minus_1_item_count, 		sum(case when dt.month_diff = 2 then o.shipped_item_count end) as month_minus_2_item_count, 		sum(case when dt.month_diff = 3 then o.shipped_item_count end) as month_minus_3_item_count, 			 		sum(case when dt.month_to_date = 1 then o.shipped_order_revenue_inc_cashback end) as mtd_revenue, 		sum(case when dt.month_diff = 1 then o.shipped_order_revenue_inc_cashback end) as month_minus_1_revenue, 		sum(case when dt.month_diff = 2 then o.shipped_order_revenue_inc_cashback end) as month_minus_2_revenue, 		sum(case when dt.month_diff = 3 then o.shipped_order_revenue_inc_cashback end) as month_minus_3_revenue,  		sum(case when dt.month_to_date = 1 and o.order_channel like 'mobile%' then o.shipped_order_revenue_inc_cashback end) as mtd_mobile_revenue, 		sum(case when dt.month_diff = 1 and o.order_channel like 'mobile%' then o.shipped_order_revenue_inc_cashback end) as month_minus_1_mobile_revenue, 		sum(case when dt.month_diff = 2 and o.order_channel like 'mobile%' then o.shipped_order_revenue_inc_cashback end) as month_minus_2_mobile_revenue, 		sum(case when dt.month_diff = 3 and o.order_channel like 'mobile%' then o.shipped_order_revenue_inc_cashback end) as month_minus_3_mobile_revenue, 	 		count(distinct (case when dt.month_to_date = 1 then o.order_group_id end)) as mtd_order, 		count(distinct (case when dt.month_diff = 1 then o.order_group_id end)) as month_minus_1_order, 		count(distinct (case when dt.month_diff = 2 then o.order_group_id end)) as month_minus_2_order, 		count(distinct (case when dt.month_diff = 3 then o.order_group_id end)) as month_minus_3_order 	 	from  		fact_order o, dim_date dt 	where  		dt.month_diff<=3  		and store_id=1 		and o.order_created_date = dt.full_date  		and (o.is_shipped = 1 or o.is_realised=1)         and o.order_created_date >= to_char(dateadd(month,-3,convert_timezone('Asia/Calcutta',getdate())),'YYYYMM01')) a";
	final String PAYLOAD="metrics=<metrics>:<orderby>&dimensions=<dimensions>&fromTime=<fromTime>&toTime=<toTime>&"
			+ "limit=<limit>&offset=<offset>&sendTotal=<sendTotal>";
	final String URL="http://udp-test-service.myntra.com/udp-api/rest/v1/reports/fact_revenue_metrics/daily/getMetricsGroupedByDimensionWithFilters";
	final Double THRESHOLD_PERCENTAGE=2D;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	AnalyticsUtil utilObj=null;
	StringBuilder reportBuilder=null;
	boolean isPassed=true;
	public AnalyticsTest()
	{
		utilObj=new AnalyticsUtil();
		reportBuilder=new StringBuilder();
	}

	@Test(dataProvider="BIDataSource")
	public void executeTest(TestDataParam td)
	{
		try
		{
			String payload=td.getPayload().trim().replaceAll("\n", "");
			MyntraService service=createServiceRequest(payload,URL);
			HashMap<String, String> headers=getHeader();
			RequestGenerator req=new RequestGenerator(service,headers);
			String response=req.returnresponseasstring();
			System.out.println(payload);
			String responseVal= APIUtilities.getElement(response, td.getPathToVerify(), "JSON");
			ResultSet rSet=utilObj.getDataFromRedshift(td.getQuery());
			rSet.next();
			String redshiftVal=rSet.getString(td.getQuyeryColumn());
			redshiftVal=redshiftVal.replaceAll(",","");
			Double redshiftNumber=Double.valueOf(redshiftVal);
			Double udpNumber=Double.valueOf(responseVal);
			Double diff=redshiftNumber-udpNumber;
			Double total=redshiftNumber+udpNumber;
			Double percentage=(diff/total)*100;
			System.out.println("Test Case : "+td.getTestCaseId());
			System.out.println("redshiftNumber : "+redshiftNumber);
			System.out.println("udpNumber : "+udpNumber);
			System.out.println("diff : "+diff);
			System.out.println("% Difference : "+percentage);
			if(percentage > THRESHOLD_PERCENTAGE)
			{
				isPassed=false;
				reportBuilder.append("UDP Value="+udpNumber+"\tredshiftNumber="+redshiftNumber+"\t tc id="+td.getTestCaseId());
				reportBuilder.append("\n");
			}
			System.out.println("\n-----------------------------------------------------------\n");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@AfterSuite
	public void generateReport()
	{
		if(isPassed==false)
		{
			assertFalse(true,reportBuilder.toString());
		}
	}

	public HashMap<String, String> getHeader()
	{
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put(HttpHeaders.ACCEPT, "application/json");
		headers.put(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
		headers.put("username", "gurushant.jidagi@myntra.com");
		return headers;
	}
	public MyntraService createServiceRequest(String payload,String url)
	{
		MyntraService service=null;//TestFlow.getMyntraService("Post");
		HashMap<String, String> headers = new HashMap<String, String>();
		service.responsedataformat=PayloadType.JSON;
		service.Payload=payload;
		service.URL=url;
		service.payloaddataformat=PayloadType.JSON;
		return service;
	}

	@DataProvider(name = "BIDataSource")
	public  Iterator<Object[]> dataProvider() {
		AnalyticsDataProvider data=new AnalyticsDataProvider();
		return data;

	}


}

class AnalyticsDataProvider implements Iterator<Object[]>
{
	Object[][] testData=null;
	int index=-1;
	public AnalyticsDataProvider()
	{
		testData = AnalyticsUtil.readTestData("regression", "Data/analytics_queries/testcases_analytics.xls", "TestCases");
		System.out.println(testData.length);
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		index++;
		if(index < testData.length)
			return true;
		else
			return false;
	}

	@Override
	public Object[] next() {
		// TODO Auto-generated method stub
		return testData[index];
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}






