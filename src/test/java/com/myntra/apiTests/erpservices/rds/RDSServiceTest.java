	/**
	 * 
	 */
	package com.myntra.apiTests.erpservices.rds;
	
	import java.io.IOException;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;

    import com.myntra.apiTests.erpservices.rds.dp.RDSServiceDP;
    import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

    import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.rds.client.response.RDSLookUpResponse;
import com.myntra.test.commons.testbase.BaseTest;
	
	/**
	 * @author santwana.samantray
	 *
	 */
	public class RDSServiceTest extends BaseTest{
		Logger log = Logger.getLogger(RDSServiceTest.class);
		RDSServiceHelper rdsServiceHelper = new RDSServiceHelper();
	
		@Test(groups = { "Smoke", "Regression" }, dataProvider = "baseFormulaVerification", dataProviderClass = RDSServiceDP.class)
	public void baseFormulaVerification(String[] lookupentries,
				String[] logparameters, String status, long statusCode,
				String statusMessage, String statusType)
				 throws IOException, JAXBException, SQLException {
			String query1="update sku_meta_data set brand_name='Belle Fille',article_name='dresses',sku_id=983980,color='pink',gender='women',size='L' where id=1040650";
			String query2="update inventory_term_info set inventory_term=10 where sku_meta_data_id= 1040650";
			DBUtilities.exUpdateQuery(query1, "rds");
			DBUtilities.exUpdateQuery(query2, "rds");
	
	
			RDSLookUpResponse testresponse = RDSServiceHelper.splitOrder(
					lookupentries, logparameters);
			System.out.println("response for the service is:"
					+ testresponse.toString());
			System.out.println("response is:"
					+ testresponse.getStatus().getStatusCode());
			System.out.println("Status Message is:"
					+ testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
					.getStatus().getStatusCode());
			AssertJUnit.assertEquals("Status message is", statusMessage,
					testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status message is", statusType,
					testresponse.getStatus().getStatusType().toString());
			AssertJUnit.assertEquals("Allocate inventory is state 1", "122.8", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(0)
					.getQty().toString());
		AssertJUnit.assertEquals("Allocate inventory is state 2", "56.4", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(1)
					.getQty().toString());
			AssertJUnit.assertEquals("Allocate inventory is state 3", "122.8", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(2)
					.getQty().toString());
		}
	
		@Test(groups = { "Smoke", "Regression" }, dataProvider = "MumbaiAllocationZeroSplitInGurNBangwithMumInvTermHigh", dataProviderClass = RDSServiceDP.class)
		public void MumbaiAllocationZeroSplitInGurNBangwithMumInvTermHigh(String[] lookupentries,
				String[] logparameters, String status, long statusCode,
				String statusMessage, String statusType) throws IOException, JAXBException, SQLException {
			String query1="update sku_meta_data set brand_name='Belle Fille',article_name='dresses',sku_id=983980,color='pink',gender='women',size='L' where id=1040650;";
			String query2="update inventory_term_info set inventory_term=10 where sku_meta_data_id= 1040650";
			String query3="update inventory_term_info set inventory_term=1000000 where sku_meta_data_id= 1040650 and state_id=3";
			DBUtilities.exUpdateQuery(query1, "RDS");
			DBUtilities.exUpdateQuery(query2, "RDS");
			DBUtilities.exUpdateQuery(query3, "RDS");

			RDSLookUpResponse testresponse = RDSServiceHelper.splitOrder(
					lookupentries, logparameters);
			System.out.println("response for the service is:"
					+ testresponse.toString());
			System.out.println("response is:"
					+ testresponse.getStatus().getStatusCode());
			System.out.println("Status Message is:"
					+ testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
					.getStatus().getStatusCode());
			AssertJUnit.assertEquals("Status message is", statusMessage,
					testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status message is", statusType,
					testresponse.getStatus().getStatusType().toString());
			AssertJUnit.assertEquals("Available inventory is", "204.66666666666666", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(0)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "97.33333333333333", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(1)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "0.0", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(2)
					.getQty().toString());
		}
	
		@Test(groups = { "Smoke", "Regression"  }, dataProvider = "GurgaonAllocationZeroSplitInMumNBangwithGurInvTermHigh", dataProviderClass = RDSServiceDP.class)
		public void GurgaonAllocationZeroSplitInMumNBangwithGurInvTermHigh(String[] lookupentries,
				String[] logparameters, String status, long statusCode,
				String statusMessage, String statusType) throws IOException, JAXBException, SQLException {
			String query1="update sku_meta_data set brand_name='Belle Fille',article_name='dresses',sku_id=983980,color='pink',gender='women',size='L' where id=1040650;";
			String query2="update inventory_term_info set inventory_term=10 where sku_meta_data_id= 1040650";
			String query3="update inventory_term_info set inventory_term=1000000 where sku_meta_data_id= 1040650 and state_id=2";

			DBUtilities.exUpdateQuery(query1, "RDS");
			DBUtilities.exUpdateQuery(query2, "RDS");
			DBUtilities.exUpdateQuery(query3, "RDS");

			
			RDSLookUpResponse testresponse = RDSServiceHelper.splitOrder(
					lookupentries, logparameters);
			System.out.println("response for the service is:"
					+ testresponse.toString());
			System.out.println("response is:"
					+ testresponse.getStatus().getStatusCode());
			System.out.println("Status Message is:"
					+ testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
					.getStatus().getStatusCode());
			AssertJUnit.assertEquals("Status message is", statusMessage,
					testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status message is", statusType,
					testresponse.getStatus().getStatusType().toString());
			AssertJUnit.assertEquals("Available inventory is", "151.0", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(0)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "0.0", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(1)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "151.0", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(2)
					.getQty().toString());
		}
	
		@Test(groups = { "Smoke", "Regression"  }, dataProvider = "BangloreAllocationZeroSplitInMumNGurwithBangInvTermHigh", dataProviderClass = RDSServiceDP.class)
		public void BangloreAllocationZeroSplitInMumNGurwithBangInvTermHigh(String[] lookupentries,
				String[] logparameters, String status, long statusCode,
				String statusMessage, String statusType) throws IOException, JAXBException, SQLException {
			String query1="update sku_meta_data set brand_name='Belle Fille',article_name='dresses',sku_id=983980,color='pink',gender='women',size='L' where id=1040650;";
			String query2="update inventory_term_info set inventory_term=10 where sku_meta_data_id= 1040650";
			String query3="update inventory_term_info set inventory_term=1000000 where sku_meta_data_id= 1040650 and state_id=1";

			DBUtilities.exUpdateQuery(query1, "RDS");
			DBUtilities.exUpdateQuery(query2, "RDS");
			DBUtilities.exUpdateQuery(query3, "RDS");

			RDSLookUpResponse testresponse = RDSServiceHelper.splitOrder(
					lookupentries, logparameters);
			System.out.println("response for the service is:"
					+ testresponse.toString());
			System.out.println("response is:"
					+ testresponse.getStatus().getStatusCode());
			System.out.println("Status Message is:"
					+ testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
					.getStatus().getStatusCode());
			AssertJUnit.assertEquals("Status message is", statusMessage,
					testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status message is", statusType,
					testresponse.getStatus().getStatusType().toString());
			AssertJUnit.assertEquals("Available inventory is", "0.0", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(0)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "97.33333333333333", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(1)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "204.66666666666666", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(2)
					.getQty().toString());
		}
	
		@Test(groups = { "Regression" }, dataProvider = "sku_idSet0SearchWithBAGSCinfo", dataProviderClass = RDSServiceDP.class)
		public void sku_idSet0SearchWithBAGSCinfo(String[] lookupentries,
				String[] logparameters, String status, long statusCode,
				String statusMessage, String statusType) throws IOException, JAXBException, SQLException {
			String query1="update sku_meta_data set brand_name='Belle Fille',article_name='dresses',sku_id=0,color='pink',gender='women',size='L' where id=1040650;";
			String query2="update inventory_term_info set inventory_term=10 where sku_meta_data_id= 1040650";
			DBUtilities.exUpdateQuery(query1, "RDS");
			DBUtilities.exUpdateQuery(query2, "RDS");
			RDSLookUpResponse testresponse = RDSServiceHelper.splitOrder(
					lookupentries, logparameters);
			System.out.println("response for the service is:"
					+ testresponse.toString());
			System.out.println("response is:"
					+ testresponse.getStatus().getStatusCode());
			System.out.println("Status Message is:"
					+ testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
					.getStatus().getStatusCode());
			AssertJUnit.assertEquals("Status message is", statusMessage,
					testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status message is", statusType,
					testresponse.getStatus().getStatusType().toString());
			AssertJUnit.assertEquals("Available inventory is", "122.8", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(0)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "56.4", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(1)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "122.8", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(2)
					.getQty().toString());
		}
	
		@Test(groups = { "Regression" }, dataProvider = "sku_idSet0andCSetOtherandSearchWithBAGinfo", dataProviderClass = RDSServiceDP.class)
		public void sku_idSet0andCSetOtherandSearchWithBAGinfo(
				String[] lookupentries, String[] logparameters, String status,
				long statusCode, String statusMessage, String statusType) throws IOException,
				JAXBException, SQLException {
			String query1="update sku_meta_data set brand_name='Belle Fille',article_name='dresses',sku_id=983980,color='pink',gender='women',size='L' where id=1040650;";
			String query2="update sku_meta_data set brand_name='Belle Fille',article_name='dresses',sku_id=0,color='other',gender='women',size='other' where id=1040650;";

			String query3="update inventory_term_info set inventory_term=10 where sku_meta_data_id= 1040650";
			DBUtilities.exUpdateQuery(query1, "RDS");
			DBUtilities.exUpdateQuery(query2, "RDS");
			DBUtilities.exUpdateQuery(query3, "RDS");

			RDSLookUpResponse testresponse = RDSServiceHelper.splitOrder(
					lookupentries, logparameters);
			System.out.println("response for the service is:"
					+ testresponse.toString());
			System.out.println("response is:"
					+ testresponse.getStatus().getStatusCode());
			System.out.println("Status Message is:"
					+ testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
					.getStatus().getStatusCode());
			AssertJUnit.assertEquals("Status message is", statusMessage,
					testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status message is", statusType,
					testresponse.getStatus().getStatusType().toString());
			AssertJUnit.assertEquals("Available inventory is", "122.8", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(0)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is","56.4", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(1)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "122.8", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(2)
					.getQty().toString());
		}
	
		@Test(groups = { "Regression" }, dataProvider = "sku_idSet0andBGCSetOtherandSearchWithAinfo", dataProviderClass = RDSServiceDP.class)
		public void sku_idSet0andBGSCSetOtherandSearchWithAinfo(
				String[] lookupentries, String[] logparameters, String status,
				long statusCode, String statusMessage, String statusType) throws IOException,
				JAXBException, SQLException {
			String query1="update sku_meta_data set brand_name='Belle Fille',article_name='dresses',sku_id=983980,color='pink',gender='women',size='L' where id=1040650;";
			String query2="update sku_meta_data set brand_name='other',article_name='dresses',sku_id=0,color='other',gender='other',size='other' where id=1040650;";

			String query3="update inventory_term_info set inventory_term=10 where sku_meta_data_id= 1040650";
			DBUtilities.exUpdateQuery(query1, "RDS");
			DBUtilities.exUpdateQuery(query2, "RDS");
			RDSLookUpResponse testresponse = RDSServiceHelper.splitOrder(
					lookupentries, logparameters);
			System.out.println("response for the service is:"
					+ testresponse.toString());
			System.out.println("response is:"
					+ testresponse.getStatus().getStatusCode());
			System.out.println("Status Message is:"
					+ testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
					.getStatus().getStatusCode());
			AssertJUnit.assertEquals("Status message is", statusMessage,
					testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status message is", statusType,
					testresponse.getStatus().getStatusType().toString());
			AssertJUnit.assertEquals("Available inventory is", "122.8", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(0)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is","56.4", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(1)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "122.8", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(2)
					.getQty().toString());
		}
	
		@Test(groups = { "Regression"}, dataProvider = "DivideEquallyWhenAllsetToOthers", dataProviderClass = RDSServiceDP.class)
		public void DivideEquallyWhenAllsetToOthers(String[] lookupentries,
				String[] logparameters, String status, long statusCode,
				String statusMessage, String statusType) throws IOException, JAXBException, SQLException {
			//String Query1="update sku_meta_data set inventory_term=1000000 where state_id in (1,3)";
			String query1="update sku_meta_data set brand_name='Belle Fille',article_name='dresses',sku_id=983980,color='pink',gender='women',size='L' where id=1040650;";
			String query2="update sku_meta_data set brand_name='other',article_name='other',sku_id=0,color='other',gender='other',size='other' where id=1040650;";

			String query3="update inventory_term_info set inventory_term=10 where sku_meta_data_id= 1040650";
			DBUtilities.exUpdateQuery(query1, "RDS");
			DBUtilities.exUpdateQuery(query2, "RDS");
			DBUtilities.exUpdateQuery(query3, "RDS");

	
			RDSLookUpResponse testresponse = RDSServiceHelper.splitOrder(
					lookupentries, logparameters);
			System.out.println("response for the service is:"
					+ testresponse.toString());
			System.out.println("response is:"
					+ testresponse.getStatus().getStatusCode());
			System.out.println("Status Message is:"
					+ testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
					.getStatus().getStatusCode());
			AssertJUnit.assertEquals("Status message is", statusMessage,
					testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status message is", statusType,
					testresponse.getStatus().getStatusType().toString());
			AssertJUnit.assertEquals("Available inventory is", "100.66666666666667", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(0)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "100.66666666666667", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(1)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "100.66666666666667", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(2)
					.getQty().toString());
		}
	
		@Test(groups = { "Regression" }, dataProvider = "AllinventoryGettingAssignedToMumbai", dataProviderClass = RDSServiceDP.class)
		public void AllinventoryGettingAssignedToMumbai(String[] lookupentries,
				String[] logparameters, String status, long statusCode,
				String statusMessage, String statusType) throws IOException, JAXBException, SQLException {
			String query1="update sku_meta_data set brand_name='Belle Fille',article_name='dresses',sku_id=983980,color='pink',gender='women',size='L' where id=1040650;";
			String query2="update inventory_term_info set inventory_term=10 where sku_meta_data_id= 1040650";
			String query3="update inventory_term_info set inventory_term=1000000 where sku_meta_data_id= 1040650 and state_id in(2,1)";
            DBUtilities.exUpdateQuery(query1, "RDS");
			DBUtilities.exUpdateQuery(query2, "RDS");
			DBUtilities.exUpdateQuery(query3, "RDS");
			RDSLookUpResponse testresponse = RDSServiceHelper.splitOrder(
					lookupentries, logparameters);
			System.out.println("response for the service is:"
					+ testresponse.toString());
			System.out.println("response is:"
					+ testresponse.getStatus().getStatusCode());
			System.out.println("Status Message is:"
					+ testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
					.getStatus().getStatusCode());
			AssertJUnit.assertEquals("Status message is", statusMessage,
					testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status message is", statusType,
					testresponse.getStatus().getStatusType().toString());
			AssertJUnit.assertEquals("Available inventory is", "0.0", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(0)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "0.0", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(1)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "302.0", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(2)
					.getQty().toString());
		}
	
		@Test(groups = { "Regression" }, dataProvider = "AllinventoryGettingAssignedToGurgaon", dataProviderClass = RDSServiceDP.class)
		public void AllinventoryGettingAssignedToGurgaon(String[] lookupentries,
				String[] logparameters, String status, long statusCode,
				String statusMessage, String statusType) throws IOException, JAXBException, SQLException {
			String query1="update sku_meta_data set brand_name='Belle Fille',article_name='dresses',sku_id=983980,color='pink',gender='women',size='L' where id=1040650;";
			String query2="update inventory_term_info set inventory_term=10 where sku_meta_data_id= 1040650";
			String query3="update inventory_term_info set inventory_term=1000000 where sku_meta_data_id= 1040650 and state_id in(1,3)";
            DBUtilities.exUpdateQuery(query1, "RDS");
			DBUtilities.exUpdateQuery(query2, "RDS");
			DBUtilities.exUpdateQuery(query3, "RDS");
			
			RDSLookUpResponse testresponse = RDSServiceHelper.splitOrder(
					lookupentries, logparameters);
			System.out.println("response for the service is:"
					+ testresponse.toString());
			System.out.println("response is:"
					+ testresponse.getStatus().getStatusCode());
			System.out.println("Status Message is:"
					+ testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
					.getStatus().getStatusCode());
			AssertJUnit.assertEquals("Status message is", statusMessage,
					testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status message is", statusType,
					testresponse.getStatus().getStatusType().toString());
			AssertJUnit.assertEquals("Available inventory is", "0.0", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(0)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "302.0", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(1)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "0.0", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(2)
					.getQty().toString());
		}
	
		@Test(groups = { "Regression"}, dataProvider = "AllinventoryGettingAssignedToBanglore", dataProviderClass = RDSServiceDP.class)
		public void AllinventoryGettingAssignedToBanglore(String[] lookupentries,
				String[] logparameters, String status, long statusCode,
				String statusMessage, String statusType) throws IOException, JAXBException, SQLException {
			String query1="update sku_meta_data set brand_name='Belle Fille',article_name='dresses',sku_id=983980,color='pink',gender='women',size='L' where id=1040650;";
			String query2="update inventory_term_info set inventory_term=10 where sku_meta_data_id= 1040650";
			String query3="update inventory_term_info set inventory_term=1000000 where sku_meta_data_id= 1040650 and state_id in(2,3)";
            DBUtilities.exUpdateQuery(query1, "RDS");
			DBUtilities.exUpdateQuery(query2, "RDS");
			DBUtilities.exUpdateQuery(query3, "RDS");

			RDSLookUpResponse testresponse = RDSServiceHelper.splitOrder(
					lookupentries, logparameters);
			System.out.println("response for the service is:"
					+ testresponse.toString());
			System.out.println("response is:"
					+ testresponse.getStatus().getStatusCode());
			System.out.println("Status Message is:"
					+ testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
					.getStatus().getStatusCode());
			AssertJUnit.assertEquals("Status message is", statusMessage,
					testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status message is", statusType,
					testresponse.getStatus().getStatusType().toString());
			AssertJUnit.assertEquals("Status message is", statusType,
					testresponse.getStatus().getStatusType().toString());
			AssertJUnit.assertEquals("Available inventory is", "302.0", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(0)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "0.0", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(1)
					.getQty().toString());
			AssertJUnit.assertEquals("Available inventory is", "0.0", testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(2)
					.getQty().toString());
		}
	
		@Test(groups = { "Regression" }, dataProvider = "INvTermnegativeformumbai", dataProviderClass = RDSServiceDP.class)
		public void INvTermnegativeforMumbai(String[] lookupentries,
				String[] logparameters, String status, long statusCode,
				String statusMessage, String statusType) throws IOException, JAXBException {
			String query1="update sku_meta_data set brand_name='Belle Fille',article_name='dresses',sku_id=983980,color='pink',gender='women',size='L' where id=1040650;";
			String query2="update inventory_term_info set inventory_term=10 where sku_meta_data_id= 1040650";
			String query3="update inventory_term_info set inventory_term=-1000 where sku_meta_data_id= 1040650 and state_id =3";
            DBUtilities.exUpdateQuery(query1, "RDS");
			DBUtilities.exUpdateQuery(query2, "RDS");
			DBUtilities.exUpdateQuery(query3, "RDS");
			RDSLookUpResponse testresponse = RDSServiceHelper.splitOrder(
					lookupentries, logparameters);
			System.out.println("response for the service is:"
					+ testresponse.toString());
			System.out.println("response is:"
					+ testresponse.getStatus().getStatusCode());
			System.out.println("Status Message is:"
					+ testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
					.getStatus().getStatusCode());
			AssertJUnit.assertEquals("Status message is", statusMessage,
					testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status message is", statusType,
					testresponse.getStatus().getStatusType().toString());
			AssertJUnit.assertEquals("Available inventory is", 0.0, testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(0)
					.getQty());
			AssertJUnit.assertEquals("Available inventory is", 0.0, testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(1)
					.getQty());
			AssertJUnit.assertEquals("Available inventory is", 302.0, testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(2)
					.getQty());
		}
		@Test(groups = { "Regression" }, dataProvider = "INvTermnegativeformumbai", dataProviderClass = RDSServiceDP.class)
		public void INvTermnegativeforGurgaon(String[] lookupentries,
				String[] logparameters, String status, long statusCode,
				String statusMessage, String statusType) throws IOException, JAXBException {
			String query1="update sku_meta_data set brand_name='Belle Fille',article_name='dresses',sku_id=983980,color='pink',gender='women',size='L' where id=1040650;";
			String query2="update inventory_term_info set inventory_term=10 where sku_meta_data_id= 1040650";
			String query3="update inventory_term_info set inventory_term=-1 where sku_meta_data_id= 1040650 and state_id =2";
            DBUtilities.exUpdateQuery(query1, "RDS");
			DBUtilities.exUpdateQuery(query2, "RDS");
			DBUtilities.exUpdateQuery(query3, "RDS");
			RDSLookUpResponse testresponse = RDSServiceHelper.splitOrder(
					lookupentries, logparameters);
			System.out.println("response for the service is:"
					+ testresponse.toString());
			System.out.println("response is:"
					+ testresponse.getStatus().getStatusCode());
			System.out.println("Status Message is:"
					+ testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
					.getStatus().getStatusCode());
			AssertJUnit.assertEquals("Status message is", statusMessage,
					testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status message is", statusType,
					testresponse.getStatus().getStatusType().toString());
			AssertJUnit.assertEquals("Available inventory is", 118.4, testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(0)
					.getQty());
			AssertJUnit.assertEquals("Available inventory is", 65.2, testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(1)
					.getQty());
			AssertJUnit.assertEquals("Available inventory is",118.4, testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(2)
					.getQty());
		}
		@Test(groups = { "Regression" }, dataProvider = "INvTermnegativeformumbai", dataProviderClass = RDSServiceDP.class)
		public void INvTermnegativeforBanglore(String[] lookupentries,
				String[] logparameters, String status, long statusCode,
				String statusMessage, String statusType) throws IOException, JAXBException {
			String query1="update sku_meta_data set brand_name='Belle Fille',article_name='dresses',sku_id=983980,color='pink',gender='women',size='L' where id=1040650;";
			String query2="update inventory_term_info set inventory_term=10 where sku_meta_data_id= 1040650";
			String query3="update inventory_term_info set inventory_term=-10 where sku_meta_data_id= 1040650 and state_id =1";
            DBUtilities.exUpdateQuery(query1, "RDS");
			DBUtilities.exUpdateQuery(query2, "RDS");
			DBUtilities.exUpdateQuery(query3, "RDS");
			RDSLookUpResponse testresponse = RDSServiceHelper.splitOrder(
					lookupentries, logparameters);
			System.out.println("response for the service is:"
					+ testresponse.toString());
			System.out.println("response is:"
					+ testresponse.getStatus().getStatusCode());
			System.out.println("Status Message is:"
					+ testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
					.getStatus().getStatusCode());
			AssertJUnit.assertEquals("Status message is", statusMessage,
					testresponse.getStatus().getStatusMessage());
			AssertJUnit.assertEquals("Status message is", statusType,
					testresponse.getStatus().getStatusType().toString());
			AssertJUnit.assertEquals("Available inventory is", 134.8, testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(0)
					.getQty());
			AssertJUnit.assertEquals("Available inventory is", 52.4, testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(1)
					.getQty());
			AssertJUnit.assertEquals("Available inventory is",114.8, testresponse
					.getEntriesList().get(0).getInventorySplitInfoEntries().get(2)
					.getQty());
		}
		
	
	}
