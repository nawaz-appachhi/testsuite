package com.myntra.apiTests.erpservices.wms;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.xml.bind.JAXBException;

import com.myntra.apiTests.erpservices.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import com.myntra.client.wms.codes.WmsSuccessCodes;
import com.myntra.client.wms.response.location.AisleEntry;
import com.myntra.client.wms.response.location.AisleResponse;
import com.myntra.client.wms.response.location.BayEntry;
import com.myntra.client.wms.response.location.BayResponse;
import com.myntra.client.wms.response.location.BinEntry;
import com.myntra.client.wms.response.location.BinResponse;
import com.myntra.client.wms.response.location.LocationEntry;
import com.myntra.client.wms.response.location.LocationResponse;
import com.myntra.client.wms.response.location.RackEntry;
import com.myntra.client.wms.response.location.RackResponse;
import com.myntra.client.wms.response.location.SectionEntry;
import com.myntra.client.wms.response.location.SectionResponse;
import com.myntra.client.wms.response.location.WarehouseEntry;
import com.myntra.client.wms.response.location.ZoneEntry;
import com.myntra.client.wms.response.location.ZoneResponse;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;



public class LocationUtils {
	private static Logger log = LoggerFactory.getLogger(LocationUtils.class);
	

	/** The method returns a boolean which describes the result of the search 
	 * @param wareHouseName
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static boolean searchWareHouseByName(String wareHouseName) throws UnsupportedEncodingException, JAXBException {
		
		boolean success=false;
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL,
                                                             ServiceParamUtils.getUrlParamsToSearchForWareHouseByLocationName(wareHouseName), SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria=locationSearchResponse.getData();
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			if(eachLocationEntry.getWarehouseName().equals(wareHouseName)){
				success=true;
				break;
			}
		}
		return success;
	}

	/**The method returns a boolean which describes the result of the search 
	 * @param wareHouseCode
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static boolean searchWareHouseByWareHouseCode(String wareHouseCode) throws UnsupportedEncodingException, JAXBException {
		boolean success=false;
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL,
				ServiceParamUtils.getUrlParamsToSearchForWareHouseByWareHouseBarcode(wareHouseCode), SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria=locationSearchResponse.getData();
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			if(eachLocationEntry.getWarehouseName().equals(wareHouseCode)){
				success=true;
				break;
			}
		}
		return success;
	}

	/**The method returns a list of zones 
	 * @param wareHouseCode
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static List<String> searchAndReturnAllZonesByWareHouse(String wareHouseCode) throws UnsupportedEncodingException, JAXBException {
		List<String> zoneBarCodes= new ArrayList<String>();
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL, ServiceParamUtils.getUrlParamsToFetchZonesByWareHouseBarcode(wareHouseCode),
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria = locationSearchResponse.getData();
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			zoneBarCodes.add(eachLocationEntry.getZoneBarcode());
		}
		return zoneBarCodes;
	}
	/**The method returns a list of zone location code entries
	 * @param wareHouseCode
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static List<String> getAllTheZoneLocationCodeEntriesByWareHouseCode(String wareHouseCode) throws UnsupportedEncodingException, JAXBException {
		List<String> zoneLocationNames= new ArrayList<String>();
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL, ServiceParamUtils.getUrlParamsToFetchZonesByWareHouseBarcode(wareHouseCode),
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria = locationSearchResponse.getData();
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			zoneLocationNames.add(eachLocationEntry.getZoneName());
		}
		return zoneLocationNames;
	}
	
	/**The method returns a list of zone ids
	 * @param wareHouseCode
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static List<Long> getAllTheZoneIdsByWareHouseCode(String wareHouseCode) throws UnsupportedEncodingException, JAXBException {
		List<Long> zoneIdList= new ArrayList<Long>();
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL,ServiceParamUtils.getUrlParamsToFetchZonesByWareHouseBarcode(wareHouseCode),
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria = locationSearchResponse.getData();
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			zoneIdList.add(eachLocationEntry.getZoneId());
		}
		return zoneIdList;
	}
	
	
	
	/**The method returns a list of section barcodes
	 * @param zoneBarCode
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static List<String> getAllTheSectionBarCodeEntriesByZoneBarcode(String zoneBarCode) throws UnsupportedEncodingException, JAXBException {
		List<String> sectionBarCodes= new ArrayList<String>();
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL, ServiceParamUtils.getUrlParamsToFetchSectionsByZoneBarCode(zoneBarCode),
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria = locationSearchResponse.getData();
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			sectionBarCodes.add(eachLocationEntry.getSectionBarcode());
		}
		return sectionBarCodes;
	}
	/**The method returns a list of zone barcode
	 * @param zoneBarCode
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static List<String> getAllTheSectionIdsByZoneBarcode(String zoneBarCode) throws UnsupportedEncodingException, JAXBException {
		List<String> sectionIds= new ArrayList<String>();
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL, ServiceParamUtils.getUrlParamsToFetchSectionsByZoneBarCode(zoneBarCode),
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria = locationSearchResponse.getData();
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			sectionIds.add(eachLocationEntry.getSectionId().toString());
		}
		return sectionIds;
	}
	
	/**The method returns a list of bays by warehouse code
	 * @param wareHouseCode
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static List<Long> getAllBaysByWareHouseCode(String wareHouseCode) throws UnsupportedEncodingException, JAXBException {
		List<Long> bayIds= new ArrayList<Long>();
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL, ServiceParamUtils.getUrlParamsToGetAllBaysByWareHouseCode(wareHouseCode),
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria = locationSearchResponse.getData();
		
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			bayIds.add(eachLocationEntry.getBayId());
		}
		return bayIds;
	}
	/**The method returns a list of racks
	 * @param wareHouseCode
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static List<Long> getAllRacksByWareHouseCode(String wareHouseCode) throws UnsupportedEncodingException, JAXBException {
		List<Long> rackIds= new ArrayList<Long>();
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL,ServiceParamUtils.getUrlParamsToSearchForRackByWareHouseCode(wareHouseCode),
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria = locationSearchResponse.getData();
		
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			rackIds.add(eachLocationEntry.getBayId());
		}
		return rackIds;
	}

	
	
	/**The method returns a list of aisles
	 * @param zoneBarCode
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static List<String> getAllAislesByWareHouseBarCode(String zoneBarCode) throws UnsupportedEncodingException, JAXBException {
		List<String> sectionBarCodes= new ArrayList<String>();
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL,ServiceParamUtils.getUrlParamsToFetchAislesByWareHouseBarCode(zoneBarCode),
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria = locationSearchResponse.getData();
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			sectionBarCodes.add(eachLocationEntry.getAisleBarcode());
		}
		return sectionBarCodes;
	}
	/**The method returns a list of aisles
	 * @param zoneBarCode
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static List<Long> getAllAisleIdsByWareHouseBarCode(String zoneBarCode) throws UnsupportedEncodingException, JAXBException {
		List<Long> aisleIds= new ArrayList<Long>();
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL, ServiceParamUtils.getUrlParamsToFetchAislesByWareHouseBarCode(zoneBarCode),
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria = locationSearchResponse.getData();
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			aisleIds.add(eachLocationEntry.getAisleId());
		}
		return aisleIds;
	}
	
	private static String getCurrentTimeForLocationCreation(){
		long timeInMillis = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMillis);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
		                                "dd-MM--HH-mm-ss");
	return  dateFormat.format(calendar.getTime());
		
		
	}
	public static boolean createAndSearchZone(String wareHouseBarCode) throws UnsupportedEncodingException, JAXBException{
		String zoneBarcode=createZoneAndReturnZoneBarcode(wareHouseBarCode);
		return searchAndVerifyIfZoneExists(zoneBarcode);
	}
	
	public static boolean createAndSearchSection(long zoneId) throws UnsupportedEncodingException, JAXBException{
		String sectionBarcode=createSectionAndReturnSectionBarcode(zoneId);
	return	searchAndVerifyIfSectionExists(sectionBarcode);
	}
	
	public static boolean createAndSearchAisle(String sectionId) throws UnsupportedEncodingException, JAXBException{
		Long sectionIdInLong=Long.parseLong(sectionId);
		String aisleCode=createSectionAndReturnAisleBarcode(sectionIdInLong);
		return	searchAndVerifyIfAisleExists(aisleCode);
	}
	
/** The method returns a boolean which describes the result of the search 
 * @param locationName
 * @return
 * @throws UnsupportedEncodingException
 * @throws JAXBException
 */
public	static boolean searchAndVerifyIfZoneExists(String locationName) throws UnsupportedEncodingException, JAXBException {
		boolean isZonePresentInTheSearchresults=false;
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL, ServiceParamUtils.getUrlParamsToSearchForZoneByLocationName(locationName),
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria = locationSearchResponse.getData();
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			if(eachLocationEntry.getZoneBarcode().equals(locationName)){
				isZonePresentInTheSearchresults=true;
				log.info("The zone was found in search results..");
				break;
			}
		}
			return isZonePresentInTheSearchresults;
	}
	
/** The method returns a boolean which describes the result of the search 
 * @param rackBarcode
 * @return
 * @throws UnsupportedEncodingException
 * @throws JAXBException
 */
public	static boolean searchAndVerifyIfRackExists(String rackBarcode) throws UnsupportedEncodingException, JAXBException {
		boolean isRackPresentInTheSearchresults=false;
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL,  ServiceParamUtils.getUrlParamsToSearchForRackByrackBarcode(rackBarcode),
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria = locationSearchResponse.getData();
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			if(eachLocationEntry.getRackBarcode().equals(rackBarcode)){
				isRackPresentInTheSearchresults=true;
				log.info("The zone was found in search results..");
				break;
			}
		}
			return isRackPresentInTheSearchresults;
	}

	
	/** The method returns a boolean which describes the result of the search 
	 * @param aisleBarCode
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static boolean searchAndVerifyIfAisleExists(String aisleBarCode) throws UnsupportedEncodingException, JAXBException {
		boolean isAislePresentInTheSearchResults=false;
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL,  ServiceParamUtils.getUrlParamsToSearchForAisleByBarcode(aisleBarCode),
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria = locationSearchResponse.getData();
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			if(eachLocationEntry.getAisleBarcode().equals(aisleBarCode)){
				isAislePresentInTheSearchResults=true;
				log.info("The zone was found in search results..");
				break;
			}
		}
			return isAislePresentInTheSearchResults;
	}
	
	
	
	/** The method returns a boolean which describes the result of the search 
	 * @param sectionBarCodeToSearch
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static boolean searchAndVerifyIfSectionExists(String sectionBarCodeToSearch) throws UnsupportedEncodingException, JAXBException {
		boolean isSectionPresentInTheSearchresults=false;
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL,  ServiceParamUtils.getUrlParamsToSearchForSectionBySectionBarcode(sectionBarCodeToSearch),
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria = locationSearchResponse.getData();
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			if(eachLocationEntry.getSectionBarcode().equals(sectionBarCodeToSearch)){
				isSectionPresentInTheSearchresults=true; 
				log.info("The zone was found in search results..");
				break;
			}
		}
			return isSectionPresentInTheSearchresults;
	}
	
	
	public static boolean searchAndVerifyIfBinExists(String binCodeToSearch) throws UnsupportedEncodingException, JAXBException {
		boolean isSectionPresentInTheSearchresults=false;
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL, ServiceParamUtils.getUrlParamsToSearchForBinByBinBarcode(binCodeToSearch),
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria = locationSearchResponse.getData();
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			if(eachLocationEntry.getBinBarcode().equals(binCodeToSearch)){
				isSectionPresentInTheSearchresults=true; 
				log.info("The zone was found in search results..");
				break;
			}
		}
			return isSectionPresentInTheSearchresults;
	}
	static	long getZoneIdByItsZoneCode(String zoneCode) throws UnsupportedEncodingException, JAXBException{
		Long zoneId=null;
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL, ServiceParamUtils.getUrlParamsToSearchForZoneByLocationName(zoneCode),
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria = locationSearchResponse.getData();
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			if(eachLocationEntry.getZoneBarcode().equals(zoneCode)){
				zoneId=eachLocationEntry.getZoneId();
				break;
				}
		}
		return zoneId;
	}
	static	long getSectiondByItsLocationName(String sectionLocationName) throws UnsupportedEncodingException, JAXBException{
		Long sectionId=null;
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.SEARCH_LOCATION_SERVICE_URL, ServiceParamUtils.getUrlParamsToSearchForSectionByLocationName(sectionLocationName),
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getTokenForIntenalOrderOperations());
		LocationResponse locationSearchResponse = (LocationResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationResponse());
		Assert.assertTrue(null!=locationSearchResponse.getData() && !locationSearchResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		List<LocationEntry> locationEntriesRetrivedMatchingTheCriteria = locationSearchResponse.getData();
		for (LocationEntry eachLocationEntry : locationEntriesRetrivedMatchingTheCriteria) {
			if(eachLocationEntry.getSectionBarcode().equals(sectionLocationName)){
				sectionId=eachLocationEntry.getSectionId();
				break;
				}
		}
		return sectionId;
	}
	
	/** The method returns a created zone barcode
	 * @param wareHouseBarCode
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static String createZoneAndReturnZoneBarcode(String wareHouseBarCode) throws JAXBException, UnsupportedEncodingException {
		ZoneEntry zone= new ZoneEntry();
		zone.setName("Zone"+getCurrentTimeForLocationCreation());
		WarehouseEntry warehouseEntry= new WarehouseEntry();
		warehouseEntry.setId(getWareHouseIdByWareHouseBarCode(wareHouseBarCode));
		zone.setWarehouse(warehouseEntry);
		String payload = APIUtilities.convertXMLObjectToString(zone);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.LOCATIONS_ZONE, new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getTokenForIntenalOrderOperations());
		ZoneResponse zoneResponse=(ZoneResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new ZoneResponse());
		Assert.assertTrue(null!=zoneResponse.getData() && !zoneResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		return zoneResponse.getData().get(0).getBarcode();
	}
	/**The method returns a created section barcode
	 * @param sectionId
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static String createSectionAndReturnAisleBarcode(Long sectionId) throws JAXBException, UnsupportedEncodingException {
		
		AisleEntry aisle= new AisleEntry();
		aisle.setName("Aisle"+getCurrentTimeForLocationCreation());
		SectionEntry section= new SectionEntry();
		section.setId(sectionId);
		aisle.setSection(section);
		
		
	    String payload = APIUtilities.convertXMLObjectToString(aisle);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.LOCATIONS_AISLE, new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getTokenForIntenalOrderOperations());
		AisleResponse aisleResponse=(AisleResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new AisleResponse());
		Assert.assertTrue(null!=aisleResponse.getData() && !aisleResponse.getData().isEmpty() , "Either there was a null response or an empty Response");
		return aisleResponse.getData().get(0).getBarcode();
	}
	
	public static void createBay(String aisleId) throws JAXBException, UnsupportedEncodingException {
		long aisleIdInLong=Long.parseLong(aisleId);
		BayEntry bay= new BayEntry();
		AisleEntry aisle= new AisleEntry();
		aisle.setId(aisleIdInLong);
		bay.setAisle(aisle);
		bay.setName("Bay"+getCurrentTimeForLocationCreation());
		String payload = APIUtilities.convertXMLObjectToString(bay);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.LOCATIONS_BAY, new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getTokenForIntenalOrderOperations());
		BayResponse bayResponse=(BayResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new BayResponse());
		Assert.assertTrue( bayResponse.getStatus().getStatusMessage().equals(WmsSuccessCodes.BAY_ADDED.getStatusMessage().toString()),"The bay was not created");
	}
	
	
	/**The method returns a created rack barcode
	 * @param bayId
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static String createRackAndReturnBarcode(Long bayId) throws JAXBException, UnsupportedEncodingException {
		RackEntry rack= new RackEntry();
		BayEntry bay=new BayEntry();
		bay.setId(bayId);
		rack.setBay(bay);
		rack.setName("Rack-"+getCurrentTimeForLocationCreation());
		String payload = APIUtilities.convertXMLObjectToString(rack);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.LOCATIONS_RACK, new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getTokenForIntenalOrderOperations());
		RackResponse rackResponse=(RackResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new RackResponse());
		Assert.assertTrue( rackResponse.getStatus().getStatusMessage().equals(WmsSuccessCodes.RACK_ADDED.getStatusMessage().toString()),"The Rack was not created");
		return rackResponse.getData().get(0).getBarcode();
	}
	/**The method returns a created bin barcode
	 * @param rackId
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static String createBinAndReturnBarcode(Long rackId) throws JAXBException, UnsupportedEncodingException {
		
		BinEntry bin= new BinEntry();
		bin.setName("Bin-"+getCurrentTimeForLocationCreation());
		
		RackEntry rack= new RackEntry();
		rack.setId(rackId);
		bin.setRack(rack);
		String payload = APIUtilities.convertXMLObjectToString(bin);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.LOCATIONS_RACK, new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getTokenForIntenalOrderOperations());
		BinResponse binResponse=(BinResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new BinResponse());
		Assert.assertTrue( binResponse.getStatus().getStatusMessage().equals(WmsSuccessCodes.RACK_ADDED.getStatusMessage().toString()),"The Rack was not created");
		return binResponse.getData().get(0).getBarcode();
	}
	
	
	
	/**The method returns a created section barcode
	 * @param zoneId
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static String createSectionAndReturnSectionBarcode(Long zoneId) throws JAXBException, UnsupportedEncodingException {
		SectionEntry section= new SectionEntry();
		section.setName("Section"+getCurrentTimeForLocationCreation());
	    ZoneEntry zone= new ZoneEntry();
	    zone.setId(zoneId);
	    section.setZone(zone);
	    section.setIsConsolidation(false);
	    String payload = APIUtilities.convertXMLObjectToString(section);
		Svc service = HttpExecutorService.executeHttpService(
				Constants.WMS_PATH.LOCATIONS_SECTION, new String[] {},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getTokenForIntenalOrderOperations());
		SectionResponse sectionResponse=(SectionResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new SectionResponse());
		return sectionResponse.getData().get(0).getBarcode();
	}
	

	private static long getWareHouseIdByWareHouseBarCode(String wareHouseBarCode){
		Long wareHouseId = null;
		HashMap<String, Object> resultMap = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord(
				"select `id` from `core_warehouses` where `barcode` = " + "'"+ wareHouseBarCode+ "'", "myntra_wms");
		if (null != resultMap && !resultMap.isEmpty()) {
			wareHouseId = (Long) resultMap.get("id");
		} else {
			log.info("Could not retrive wareHouse id by wareHouse barcode passed , the wareHouse barcode does not exist " );
		}
		return wareHouseId ;
	}

	/**
	 * Headers for the WMS Service
	 * 
	 * @return
	 */
	public static HashMap<String, String> getTokenForIntenalOrderOperations() {
		HashMap<String, String> stnHeaders = new HashMap<String, String>();
		stnHeaders.put("Authorization", "Basic RVJQIEFkbWluaXN0cmF0b3J+ZXJwYWRtaW46dGVzdA==");
		stnHeaders.put("Content-Type", "Application/xml");
		return stnHeaders;
	}

}
