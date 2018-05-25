package com.myntra.apiTests.portalservices.all;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.IPLocationGroupServiceDP;
import com.myntra.lordoftherings.Initialize;


import com.myntra.lordoftherings.gandalf.PayloadType;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;

public class IPLocationGroupService extends IPLocationGroupServiceDP
{

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(IPLocationGroupService.class);

	List<String> actCrtBy = new ArrayList<>(), actDesc = new ArrayList<>(), actCity = new ArrayList<>(), actCountry = new ArrayList<>(), 
			actRegion = new ArrayList<>(), actTitle = new ArrayList<>(), actType = new ArrayList<>();

			static String baseLocNode = "locationResponse.";
			static String baseLocGrpNode = "locationGroupResponse.";
			static String baseNodeStatusPath = baseLocGrpNode+"status.";
			static String baseNodePath = baseLocGrpNode+"data.";
			static String locNodePath = baseLocNode+"Location.";
			static String locNodeStatusPath = baseLocNode+"status.";
			static String locGrpNodePath = baseNodePath+"LocationGroup.";	
			static String locGrpMapLstNodePath = locGrpNodePath+"locationGroupMapList.";

			
			@Test(groups = { /* "Smoke", "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "ProdSanity" */ })
			public void IPLocationGroupService_getLocationGroupService()
			{
				System.out.println("Start");
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION,
						APINAME.GETLOCATIONGROUP, init.Configurations);
				
				System.out.println(service.URL);
				RequestGenerator req = new RequestGenerator(service);

				log.info(service.URL);
				System.out.println(req.respvalidate.returnresponseasstring());
				AssertJUnit.assertEquals("IP Location group service is not working",
						200, req.response.getStatus());
			}

			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "ProdSanity" */}, dataProvider="getLocationGroupServiceByIDDataProvider")
			public void IPLocationGroupService_getLocationGroupServiceByID(String id){
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION,
						APINAME.GETLOCATIONGROUPBYID, init.Configurations, PayloadType.JSON, new String[]{id}, PayloadType.JSON);
				RequestGenerator req = new RequestGenerator(service);
				log.info(service.URL);
				System.out.println(service.URL);
				//System.out.println(req.respvalidate.returnresponseasstring());
				AssertJUnit.assertEquals("getLocationGroupServiceByID is not working", 200, req.response.getStatus());		
			}

			@Test(groups = { /* "MiniRegression","Regression", "ExhasutiveRegression", "ProdSanity" */ }, dataProvider="getLocationByIPDataProvider")
			public void IPLocationGroupService_getLocationByIP(String ip){
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION,
						APINAME.GETLOCATIONBYIP, init.Configurations, PayloadType.JSON, new String[]{ip}, PayloadType.JSON);
				RequestGenerator req = new RequestGenerator(service);
				log.info(service.URL);
				System.out.println(service.URL);
				//System.out.println(req.respvalidate.returnresponseasstring());
				AssertJUnit.assertEquals("getLocationByIP is not working", 200, req.response.getStatus());		
			}

			//---------------------------------------------------------------------------------------------------------

			/**
			 * Verify Location Group Services Response code (service - 1)
			 */
			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "laxmiTc" */ })
			public void IPLocationGroupService_getLocationGroupService_vefifyRespCode()
			{
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONGROUP, init.Configurations);
				RequestGenerator req = new RequestGenerator(service);
				log.info(service.URL);

				AssertJUnit.assertEquals("IP Location group service is not working", 200, req.response.getStatus());
			}

			/**
			 * Verify Location Group Services Response code (service - 1)
			 */
			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "laxmiTc" */ })
			public void IPLocationGroupService_getLocationGroupService_vSuccStatusRespond()
			{
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONGROUP, init.Configurations);
				RequestGenerator req = new RequestGenerator(service);
				log.info(service.URL);

				AssertJUnit.assertEquals("IP Location group service is not working", 200, req.response.getStatus());
			}

			/**
			 * Verify Location Group Services Messages (service - 1)
			 */
			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "laxmiTc" */ }, 
					dataProvider = "getLocationGroupService_Msgs")
			public void IPLocationGroupService_getLocationGroupService_vSuccStatusMsg(String statusCode, 
					String statusMsg, String statusType)
			{
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONGROUP,
						init.Configurations);
				RequestGenerator req = new RequestGenerator(service);
				//	System.out.println(req.respvalidate.returnresponseasstring());

				AssertJUnit.assertEquals("Status code does't match", 
						req.respvalidate.NodeValue(baseNodeStatusPath+"statusCode", true), statusCode);	

				AssertJUnit.assertEquals("Status message dosn't match",
						req.respvalidate.NodeValue(baseNodeStatusPath+"statusMessage", true), statusMsg);

				AssertJUnit.assertEquals("Status type dosn't match",
						req.respvalidate.NodeValue(baseNodeStatusPath+"statusType", true), statusType);

				log.info(req.respvalidate.returnresponseasstring());
			}

			/**
			 * Verify all location Group Nodes (service - 1)
			 */
			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "laxmiTc" */ })
			public void IPLocationGroupService_getLocationGroupService_verifyGroupNodes()
			{
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONGROUP, init.Configurations);
				RequestGenerator req = new RequestGenerator(service);
				//	System.out.println(req.respvalidate.returnresponseasstring());		

				AssertJUnit.assertTrue("Inval Ip Location Group information.", req.respvalidate.DoesNodesExists(getIPLocationGrpNodes()));		
			}

			/**
			 * Verify Location by IP (service - 2)
			 */
			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "laxmiTc" */ }, dataProvider = "getLocationByIPDataProvider")
			public void IPLocGrpSrvc_getLocSrvcByIp(String ip){
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONBYIP, init.Configurations,
						PayloadType.JSON, new String[]{ip}, PayloadType.JSON);
				RequestGenerator req = new RequestGenerator(service);
				System.out.println("service-URL - "+service.URL); 

				AssertJUnit.assertEquals("getLocationByIP is not working", 200, req.response.getStatus());
			}

			/**
			 * Verify Location  by IP Services Messages (service - 2)
			 */
			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "laxmiTc" */ }, 
					dataProvider = "getLocationByIPMsgsDataProvider")
			public void IPLocGrpSrvc_getLocSrvcByIp_vSuccStatusMsg(String ip, String statusCode, String statusMsg, String statusType)
			{
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONBYIP, init.Configurations,
						PayloadType.JSON, new String[]{ip}, PayloadType.JSON);
				RequestGenerator req = new RequestGenerator(service);
				//	System.out.println(req.respvalidate.returnresponseasstring());

				AssertJUnit.assertEquals("Status code does't match", 
						req.respvalidate.NodeValue(locNodeStatusPath+"statusCode", true), statusCode);	

				AssertJUnit.assertEquals("Status message dosn't match",
						req.respvalidate.NodeValue(locNodeStatusPath+"statusMessage", true), statusMsg);

				AssertJUnit.assertEquals("Status type dosn't match",
						req.respvalidate.NodeValue(locNodeStatusPath+"statusType", true), statusType);

				log.info(req.respvalidate.returnresponseasstring());
			}	

			/**
			 * Verify Nodes of Location by IP (service - 2)
			 */
			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "laxmiTc" */ }, 
					dataProvider = "getLocationByIPDataProvider")
			public void IPLocGrpSrvc_getLocSrvcByIp_verifyGroupNodes(String ip)
			{
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONBYIP, init.Configurations,
						PayloadType.JSON, new String[]{ip}, PayloadType.JSON);
				RequestGenerator req = new RequestGenerator(service);
				System.out.println(req.respvalidate.returnresponseasstring());		

				AssertJUnit.assertTrue("Inval Ip Location information.", req.respvalidate.DoesNodesExists(getIpLocationNodes()));		
			}

			/**
			 * Verify create location (service - 3)
			 * @param createdBy
			 * @param Desc
			 * @param city
			 * @param country
			 * @param region
			 * @param title
			 * @param type
			 */

			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "laxmiTc" */ }, dataProvider="createLoactionGroupDataProvider_PositiveCases")
			public void IPLocationGroupService_createLocationGroup(String createdBy, String Desc, String city, String country, String region, String title, String type){
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION,
						APINAME.CREATELOCATIONGROUP, init.Configurations, new String[]{createdBy, Desc, city, country, region, title, type});
				RequestGenerator req = new RequestGenerator(service);
				log.info(service.URL);
				System.out.println(req.respvalidate.returnresponseasstring());
				AssertJUnit.assertEquals("getLocationGroupByIP is not working",	200, req.response.getStatus());		
			}

			/**
			 * Verify Created Group Nodes (service - 3)
			 */
			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "laxmiTc" */ })
			public void IPLocationGroupService_createdGroupService_vGroupNodes()
			{
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.CREATELOCATIONGROUP, init.Configurations);
				RequestGenerator req = new RequestGenerator(service);
				System.out.println(req.respvalidate.returnresponseasstring());		

				AssertJUnit.assertTrue("Inval Ip Location Group information.", req.respvalidate.DoesNodesExists(getCrtedGrpNodes()));		
			}

			/**
			 *  Verify Created Group Nodes values  (service - 3)
			 * @param createdBy
			 * @param Desc
			 * @param city
			 * @param country
			 * @param region
			 * @param title
			 * @param type
			 */
			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "laxmiTc" */ }, 
					dataProvider="createLoactionGroupDataProvider_PositiveCases")
			public void IPLocationGroupService_vGroupNodesVals_PositiveTest(String expCrtBy, String expDesc, String expCity, 
					String expCountry, String expRegion, String expTitle, String expType)
			{
				Random randomNo = new Random();	
				int randVal = randomNo.nextInt(10000);			
				expCrtBy = expCrtBy + randVal;
				expDesc = expDesc + randVal;
				expTitle = expTitle + randVal;
				expType = expType + randVal;

				// Creating group service
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION,
						APINAME.CREATELOCATIONGROUP, init.Configurations, new String[]{expCrtBy, expDesc, expCity, expCountry, expRegion, expTitle, expType});
				RequestGenerator req = new RequestGenerator(service);
				log.info(service.URL);
				System.out.println(req.respvalidate.returnresponseasstring());
				AssertJUnit.assertEquals("getLocationGroupByIP is not working",	200, req.response.getStatus());		

				// Verifying group service with possitive values
				MyntraService service1 = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONGROUP, init.Configurations,
						new String[]{expCrtBy, expDesc, expCity, expCountry, expRegion, expTitle, expType});
				RequestGenerator req1 = new RequestGenerator(service1);					

				actCrtBy = JsonPath.read(req1.respvalidate.returnresponseasstring(), 
						"$.locationGroupResponse.data.LocationGroup[*].createdBy"); 
				AssertJUnit.assertTrue(actCrtBy.toString().contains(expCrtBy));

				actDesc = JsonPath.read(req1.respvalidate.returnresponseasstring(), 
						"$.locationGroupResponse.data.LocationGroup[*].description"); 
				AssertJUnit.assertTrue(actDesc.toString().contains(expDesc));  

				List<String> allNodes = req.respvalidate.GetallvaluesfromarrayNode(locGrpMapLstNodePath, true);
				for(int i = 0; i <allNodes.size(); i++){
					AssertJUnit.assertTrue("city Mismatch : ", 
							req.respvalidate.NodeValue(locGrpMapLstNodePath+"city", true).replaceAll("\"", "").equalsIgnoreCase(expCity));
					AssertJUnit.assertTrue("country Mismatch : ", 
							req.respvalidate.NodeValue(locGrpMapLstNodePath+"country", true).replaceAll("\"", "").equalsIgnoreCase(expCountry));
					AssertJUnit.assertTrue("region Mismatch : ",
							req.respvalidate.NodeValue(locGrpMapLstNodePath+"region", true).replaceAll("\"", "").equalsIgnoreCase(expRegion));
				}
				actTitle = JsonPath.read(req1.respvalidate.returnresponseasstring(), "$.locationGroupResponse.data.LocationGroup[*].title"); 
				AssertJUnit.assertTrue(actTitle.toString().contains(expTitle));		

				actType = JsonPath.read(req1.respvalidate.returnresponseasstring(), "$.locationGroupResponse.data.LocationGroup[*].type"); 
				AssertJUnit.assertTrue(actType.toString().contains(expType));		

				log.info(req1.respvalidate.returnresponseasstring());
			}

			/**
			 *  Verify Created Group Nodes values (service - 3)
			 * @param createdBy
			 * @param Desc
			 * @param city
			 * @param country
			 * @param region
			 * @param title
			 * @param type
			 */
			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "laxmiTc" */ }, 
					dataProvider="createLoactionGroupDataProvider_NegativeCases")
			public void IPLocationGroupService_vGroupNodesVals_NegativeTest(String createdBy, String Desc, String city, 
					String country, String region, String title, String type)
			{
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONGROUP, init.Configurations,
						new String[]{createdBy, Desc, city, country, region, title, type});
				RequestGenerator req = new RequestGenerator(service);
				//	System.out.println(req.respvalidate.returnresponseasstring());

				AssertJUnit.assertEquals("IP Location group service is not working", 200, req.response.getStatus());	
				log.info(req.respvalidate.returnresponseasstring());
			}

			/**
			 * ----------------------------------------Sarath code-------------------------------------------------------------------------
			 */


			/**
			 * This method is used to verify the status code for single location response by id.
			 * 
			 * @param User
			 * @param Pass
			 * @param addressID
			 * @param statusCode
			 * @param statusMsg
			 * @param statusType
			 */

			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "ProdSanity" */ }, dataProvider = "getLocationGroupService_IDDataProvider1")
			public void IPLocationGroupService_fetchbyID(String id) 
			{
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONGROUPBYID,
						init.Configurations, PayloadType.JSON, new String[]{id}, PayloadType.JSON);
				RequestGenerator req = new RequestGenerator(service);
				log.info(service.URL);
				//System.out.println("Service URL: "+service.URL);
				//System.out.println(req.returnresponseasstring());

				AssertJUnit.assertEquals("IP Location group service is not working for dp1", 
						200 , req.response.getStatus());

			}	

			/**
			 * This method is used to verify the location response from fetch single location functionality.
			 * 
			 * @param User
			 * @param Pass
			 * @param addressID
			 * @param statusCode
			 * @param statusMsg
			 * @param statusType
			 */		

			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression" */ }, dataProvider = "getLocationGroupService_IDDataProvider1")
			public void IPLocationGroupService_fetch_ID_VerifyNodeValues(String id) 
			{
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONGROUPBYID,
						init.Configurations, PayloadType.JSON, new String[]{id}, PayloadType.JSON);
				RequestGenerator req = new RequestGenerator(service);
				log.info(service.URL);

				String createdBy = req.respvalidate
						.NodeValue("locationGroupResponse.data.LocationGroup.createdBy", true).replaceAll("\"", "")
						.trim();

				String createdOn = req.respvalidate
						.NodeValue("locationGroupResponse.data.LocationGroup.createdOn", true).replaceAll("\"", "")
						.trim();

				String description = req.respvalidate
						.NodeValue("locationGroupResponse.data.LocationGroup.description", true).replaceAll("\"", "")
						.trim();

				String LGCreatedOn = req.respvalidate
						.NodeValue("locationGroupResponse.data.LocationGroup.locationGroupMapList.createdOn", true).replaceAll("\"", "")
						.trim();
				log.info(LGCreatedOn);
				//System.out.println("LGCreatedOn:"+LGCreatedOn);
				//System.out.println(req.respvalidate.returnresponseasstring());
				AssertJUnit.assertTrue(
						"Created By name dosn't match",
						createdBy.contains("Rajesh"));

				AssertJUnit.assertTrue(
						"Created On dosn't match",
						createdOn.contains("2014-08-07T16:55:59+05:30"));

				AssertJUnit.assertTrue(
						"description message dosn't match",
						description.contains("this is the location group"));

				AssertJUnit.assertTrue(
						"LGCreatedOn message dosn't match",
						LGCreatedOn.contains("2014-08-07T16:55:59+05:30"));
			}

			/**
			 * This method is used to verify all the location nodes are existing in the response
			 * 
			 * @param UserName
			 * @param Pass
			 * @param emptyValue
			 */


			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression" */ }, dataProvider = "getLocationGroupService_IDDataProvider1")
			public void IPLocationGroupService_fetchedID_VerifyNodes(String id) 
			{

				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONGROUPBYID,
						init.Configurations, PayloadType.JSON, new String[]{id}, PayloadType.JSON);
				RequestGenerator req = new RequestGenerator(service);
				log.info(service.URL);
				//System.out.println(req.returnresponseasstring());

				AssertJUnit.assertTrue("Invalid location id nodes", req.respvalidate.DoesNodesExists(getIDLocationNodes()));

			}

			/**
			 * This method is used to verify the status code for single location response by invalid id.
			 * 
			 * @param User
			 * @param Pass
			 * @param addressID
			 * @param statusCode
			 * @param statusMsg
			 * @param statusType
			 */		
			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "ProdSanity" */ }, dataProvider = "getLocationGroupService_InvalidIDDataProvider")
			public void IPLocationGroupService_fetchbyInvalidID(String id) 
			{
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONGROUPBYID,
						init.Configurations, PayloadType.JSON, new String[]{id}, PayloadType.JSON);
				RequestGenerator req = new RequestGenerator(service);
				log.info(service.URL);
				//System.out.println("Service URL: "+service.URL);
				//System.out.println(req.returnresponseasstring()+req.response.getStatus());

				AssertJUnit.assertTrue("Status code does't match", req.respvalidate.NodeValue("locationGroupResponse.status.statusCode",
						true).contains("-1"));
				AssertJUnit.assertTrue("Status Type does't match", req.respvalidate.NodeValue("locationGroupResponse.status.statusType",
						true).contains("ERROR"));
				AssertJUnit.assertTrue("Total Count does't match", req.respvalidate.NodeValue("locationGroupResponse.status.totalCount",
						true).contains("0"));
			}

			/**
			 * This method is used to verify the status code for single location response by negetive id value.
			 * 
			 * @param User
			 * @param Pass
			 * @param addressID
			 * @param statusCode
			 * @param statusMsg
			 * @param statusType
			 */		
			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "ProdSanity" */ }, dataProvider = "getLocationGroupService_NegetiveIDDataProvider")
			public void IPLocationGroupService_fetchbyNegetiveIDValue(String id) 
			{
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONGROUPBYID,
						init.Configurations, PayloadType.JSON, new String[]{id}, PayloadType.JSON);
				RequestGenerator req = new RequestGenerator(service);
				log.info(service.URL);
				//System.out.println("Service URL: "+service.URL);
				//System.out.println(req.returnresponseasstring()+req.response.getStatus());

				AssertJUnit.assertTrue("Status code does't match", req.respvalidate.NodeValue("locationGroupResponse.status.statusCode",
						true).contains("-1"));
				AssertJUnit.assertTrue("Status Type does't match", req.respvalidate.NodeValue("locationGroupResponse.status.statusType",
						true).contains("ERROR"));
				AssertJUnit.assertTrue("Total Count does't match", req.respvalidate.NodeValue("locationGroupResponse.status.totalCount",
						true).contains("0"));
			}

			/**
			 * This method is used to verify the status code for creation single location group.
			 * 
			 * @param User
			 * @param Pass
			 * @param addressID
			 * @param statusCode
			 * @param statusMsg
			 * @param statusType
			 */

			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression" */ }, dataProvider = "setLocationGroupService_Data1")
			public void IPLocationGroupService_creation(String createdBy, String description, String city, String country, String region, String title, String type) 
			{
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.CREATELOCATIONGROUP,
						init.Configurations, new String[]{createdBy, description, city, country, region, title, type});
				RequestGenerator req1 = new RequestGenerator(service);
				log.info(service.URL);
				//System.out.println("Service URL: "+service.URL);
				//System.out.println(req1.returnresponseasstring());		
				AssertJUnit.assertEquals("IP Location group service is not creating group for dp1", 200 , req1.response.getStatus());
				//fetching current values
				MyntraService services = Myntra.getService(ServiceType.PORTAL_LOCATION,
						APINAME.GETLOCATIONGROUP, init.Configurations);
				RequestGenerator req = new RequestGenerator(services);

				String createdBy1=JsonPath.read(req.returnresponseasstring(), "$.locationGroupResponse.data.LocationGroup[*].createdBy[0]");
				String description1=JsonPath.read(req.returnresponseasstring(), 
						"$.locationGroupResponse.data.LocationGroup[*].description[0]");		
				String city1=JsonPath.read(req.returnresponseasstring(), 
						"$.locationGroupResponse.data.LocationGroup[*].locationGroupMapList.city[0]");	
				String country1=JsonPath.read(req.returnresponseasstring(), 
						"$.locationGroupResponse.data.LocationGroup[*].locationGroupMapList.country[0]");		
				String region1=JsonPath.read(req.returnresponseasstring(), 
						"$.locationGroupResponse.data.LocationGroup[*].locationGroupMapList.region[0]");		
				String title1=JsonPath.read(req.returnresponseasstring(), "$.locationGroupResponse.data.LocationGroup[*].title[0]");		
				String type1=JsonPath.read(req.returnresponseasstring(), "$.locationGroupResponse.data.LocationGroup[*].type[0]");

				AssertJUnit.assertEquals("New record value createdBy is not matching", createdBy , createdBy1);		
				AssertJUnit.assertEquals("New record value description is not matching", description , description1);		
				AssertJUnit.assertEquals("New  record value city is not matching", city , city1);		
				AssertJUnit.assertEquals("New record value country is not matching", country , country1);		
				AssertJUnit.assertEquals("New record value region is not matching", region , region1);		
				AssertJUnit.assertEquals("New record value title is not matching", title , title1);		
				AssertJUnit.assertEquals("New record value type is not matching", type , type1);	

			}





			/**
			 * This method is used to verify the status code for single location response delete by id.
			 * 
			 * @param User
			 * @param Pass
			 * @param addressID
			 * @param statusCode
			 * @param statusMsg
			 * @param statusType
			 */


			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression" */ })
			public void IPLocationGroupService_deleteByID() 
			{

				MyntraService services = Myntra.getService(ServiceType.PORTAL_LOCATION,
						APINAME.GETLOCATIONGROUP, init.Configurations);
				RequestGenerator reqGen = new RequestGenerator(services);
				String LocationGroupID = reqGen.respvalidate
						.NodeValue("locationGroupResponse.data.LocationGroup.id", true).replaceAll("\"", "")
						.trim();
				//System.out.println("Deleting id is: "+LocationGroupID);


				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.DELETELOCATIONGROUP,
						init.Configurations, PayloadType.JSON, new String[]{LocationGroupID}, PayloadType.JSON);
				RequestGenerator req = new RequestGenerator(service);
				log.info(service.URL);
				//System.out.println("Service URL: "+service.URL);
				//System.out.println(req.returnresponseasstring()+req.response.getStatus());


				AssertJUnit.assertTrue(
						"Status code does't match", 
						req.respvalidate.NodeValue("locationGroupResponse.status.statusCode",
								true).contains("1"));
				AssertJUnit.assertTrue(
						"Status Message does't match", 
						req.respvalidate.NodeValue("locationGroupResponse.status.statusMessage",
								true).contains("deleted id : "+LocationGroupID));
				AssertJUnit.assertTrue(
						"Status Type does't match", 
						req.respvalidate.NodeValue("locationGroupResponse.status.statusType",
								true).contains("SUCCESS"));
				AssertJUnit.assertTrue(
						"Total Count does't match", 
						req.respvalidate.NodeValue("locationGroupResponse.status.totalCount",
								true).contains("0"));

			}



			/**
			 * This method is used to verify the status code for single location response by ip.
			 * 
			 * @param User
			 * @param Pass
			 * @param addressID
			 * @param statusCode
			 * @param statusMsg
			 * @param statusType
			 */

			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "ProdSanity" */ }, dataProvider="getLocationGroupByIPDataProvider")
			public void IPLocationGroupService_getLocationGroupByIP(String ip){
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION,
						APINAME.GETLOCATIONGROUPBYIP, init.Configurations, PayloadType.JSON, new String[]{ip}, PayloadType.JSON);
				RequestGenerator req = new RequestGenerator(service);
				log.info(service.URL);
				//System.out.println(service.URL);
				//System.out.println(req.respvalidate.returnresponseasstring());
				AssertJUnit.assertEquals("getLocationGroupByIP is not working",
						200, req.response.getStatus());

			}	

			/**
			 * This method is used to verify the status code for single location response by ip.
			 * 
			 * @param UserName
			 * @param Pass
			 * @param emptyValue
			 */

			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "ProdSanity" */ }, dataProvider = "getLocationGroupByIPDataProvider")
			public void IPLocationGroupService_getLocationGroupByIP_VerifyNodes(String ip) 
			{

				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONGROUPBYIP,
						init.Configurations, PayloadType.JSON, new String[]{ip}, PayloadType.JSON);
				RequestGenerator req = new RequestGenerator(service);
				log.info(service.URL);
				//System.out.println(req.returnresponseasstring());

				AssertJUnit.assertTrue("Invalid location ip nodes", req.respvalidate.DoesNodesExists(getIPLocationNodes()));

			}

			/**
			 * This method is used to verify the status code for single location response by invalid ip.
			 * 
			 * @param UserName
			 * @param Pass
			 * @param emptyValue
			 */

			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "ProdSanity" */ }, dataProvider = "getLocationGroupService_InvalidIPDataProvider")
			public void IPLocationGroupService_getLocationGroupByIP_InvalidIP(String ip) 
			{
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONGROUPBYIP,
						init.Configurations, PayloadType.JSON, new String[]{ip}, PayloadType.JSON);
				RequestGenerator req = new RequestGenerator(service);
				log.info(service.URL);
				//System.out.println("Service URL: "+service.URL);
				//System.out.println(req.returnresponseasstring()+req.response.getStatus());

				AssertJUnit.assertTrue(
						"Status code does't match", 
						req.respvalidate.NodeValue("locationGroupResponse.status.statusCode",
								true).contains("-1"));
				AssertJUnit.assertTrue(
						"Status Type does't match", 
						req.respvalidate.NodeValue("locationGroupResponse.status.statusType",
								true).contains("ERROR"));
				AssertJUnit.assertTrue(
						"Total Count does't match", 
						req.respvalidate.NodeValue("locationGroupResponse.status.totalCount",
								true).contains("0"));
			}



			/**
			 * This method is used to verify the status code for single location response by negetive ip value.
			 * 
			 * @param User
			 * @param Pass
			 * @param addressID
			 * @param statusCode
			 * @param statusMsg
			 * @param statusType
			 */

			@Test(groups = { /* "Sanity","MiniRegression","Regression", "ExhasutiveRegression", "ProdSanity" */ }, dataProvider = "getLocationGroupService_NegetiveIPDataProvider")
			public void IPLocationGroupService_getLocationGroupByIP_NegetiveIPValue(String ip) 
			{
				MyntraService service = Myntra.getService(ServiceType.PORTAL_LOCATION, APINAME.GETLOCATIONGROUPBYIP,
						init.Configurations, PayloadType.JSON, new String[]{ip}, PayloadType.JSON);
				RequestGenerator req = new RequestGenerator(service);
				log.info(service.URL);
				//System.out.println("Service URL: "+service.URL);
				//System.out.println(req.returnresponseasstring()+req.response.getStatus());

				AssertJUnit.assertTrue(
						"Status code does't match", 
						req.respvalidate.NodeValue("locationGroupResponse.status.statusCode",
								true).contains("-1"));
				AssertJUnit.assertTrue(
						"Status Type does't match", 
						req.respvalidate.NodeValue("locationGroupResponse.status.statusType",
								true).contains("ERROR"));
				AssertJUnit.assertTrue(
						"Total Count does't match", 
						req.respvalidate.NodeValue("locationGroupResponse.status.totalCount",
								true).contains("0"));
			}
			
			private static List<String> getIPLocationGrpNodes(){
				List<String> addNodes = new ArrayList<>();
				addNodes.add(locGrpNodePath+"createdBy");
				addNodes.add(locGrpNodePath+"createdOn");
				addNodes.add(locGrpNodePath+"id");
				addNodes.add(locGrpNodePath+"description");
				addNodes.add(locGrpNodePath+"title");
				addNodes.add(locGrpNodePath+"type");
				return addNodes;	
			}

			private static List<String> getCrtedGrpNodes(){
				List<String> addNodes = new ArrayList<>();
				addNodes.add(locGrpNodePath+"createdBy");
				addNodes.add(locGrpNodePath+"description");
				addNodes.add(locGrpMapLstNodePath+"city");
				addNodes.add(locGrpMapLstNodePath+"country");
				addNodes.add(locGrpMapLstNodePath+"region");
				addNodes.add(locGrpNodePath+"title");
				addNodes.add(locGrpNodePath+"type");
				return addNodes;	
			}

			private static List<String> getIpLocationNodes(){
				List<String> addNodes = new ArrayList<>();
				addNodes.add(locNodePath+"cityName");
				addNodes.add(locNodePath+"countryCode");
				addNodes.add(locNodePath+"countryName");
				addNodes.add(locNodePath+"latitude");
				addNodes.add(locNodePath+"longitude");
				addNodes.add(locNodePath+"postalCode");
				addNodes.add(locNodePath+"regionName");
				System.out.println(addNodes);
				return addNodes;	
			}	

			public static List<String> getIDLocationNodes(){
				List<String> locationNodes = new ArrayList<>();
				locationNodes.add("locationGroupResponse.data.LocationGroup.createdBy");
				locationNodes.add("locationGroupResponse.data.LocationGroup.createdOn");
				locationNodes.add("locationGroupResponse.data.LocationGroup.id");
				locationNodes.add("locationGroupResponse.data.LocationGroup.description");
				locationNodes.add("locationGroupResponse.data.LocationGroup.title");
				locationNodes.add("locationGroupResponse.data.LocationGroup.type");
				locationNodes.add("locationGroupResponse.data.LocationGroup.locationGroupMapList.createdOn");
				locationNodes.add("locationGroupResponse.data.LocationGroup.locationGroupMapList.id");
				locationNodes.add("locationGroupResponse.data.LocationGroup.locationGroupMapList.city");
				locationNodes.add("locationGroupResponse.data.LocationGroup.locationGroupMapList.country");
				locationNodes.add("locationGroupResponse.data.LocationGroup.locationGroupMapList.region");
				//System.out.println(locationNodes);
				return locationNodes;		
			}	

			public static List<String> getIPLocationNodes(){
				List<String> locationIPNodes = new ArrayList<>();
				locationIPNodes.add("locationGroupResponse.data.LocationGroup.createdBy");
				locationIPNodes.add("locationGroupResponse.data.LocationGroup.createdOn");
				locationIPNodes.add("locationGroupResponse.data.LocationGroup.id");
				locationIPNodes.add("locationGroupResponse.data.LocationGroup.description");
				locationIPNodes.add("locationGroupResponse.data.LocationGroup.title");
				locationIPNodes.add("locationGroupResponse.data.LocationGroup.type");
				//System.out.println(locationIPNodes);
				return locationIPNodes;		
			}		

}
