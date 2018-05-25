package com.myntra.apiTests.erpservices.wms.Tests;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.xml.bind.JAXBException;

import com.myntra.apiTests.erpservices.wms.dp.LocationsTestDataProvider;
import com.myntra.apiTests.erpservices.wms.LocationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.myntra.test.commons.testbase.BaseTest;

/**
 * The term location in WMS vary from WareHouse to a bin From a bin if we go up
 * the hierarchy upto wareHouse , everything is considered as a location and has
 * one to many relationship down the hierarchy
 * 
 * @author 15138
 *
 */
public class LocationTests extends BaseTest {
	private static Logger log = LoggerFactory.getLogger(LocationTests.class);

	/** The test is to try to search a wareHouse by its name and verifies if it is searchable
	 * @param wareHouseName
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
	"Regression" }, dataProvider = "getTestWareHouses", dataProviderClass = LocationsTestDataProvider.class)
	public void searchTheWareHouseByItsLocationName(String wareHouseName) throws UnsupportedEncodingException, JAXBException {
		Assert.assertTrue(LocationUtils.searchWareHouseByName(wareHouseName),"The wareHouse did no exist or cold not be searched..");
	}

	/** The test is to try to search a zone  by its name and verifies if it is searchable
	 * @param wareHouseCode
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
	"Regression" }, dataProvider = "getTestWareHouseCodes", dataProviderClass = LocationsTestDataProvider.class)
	public void getAllZonesByWareHouseCode(String wareHouseCode) throws UnsupportedEncodingException, JAXBException {
		List<String> zoneBarcodes = LocationUtils.searchAndReturnAllZonesByWareHouse(wareHouseCode);
		Assert.assertTrue(!zoneBarcodes.isEmpty(),"There was no zone found in the response..");
	}

	/** The test is to try to search for sections by zone code
	 * @param zoneCode
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
	"Regression" }, dataProvider = "getTestZoneCodes", dataProviderClass = LocationsTestDataProvider.class)
	public void getSectionsByWareHouseCode(String zoneCode) throws UnsupportedEncodingException, JAXBException {
		List<String> sectionBarCodes = LocationUtils.getAllTheSectionBarCodeEntriesByZoneBarcode(zoneCode);
		Assert.assertTrue(!sectionBarCodes.isEmpty(),"There was no zone found in the response..");
	}

	/** The test is to try to create and search a zone 
	 * @param wareHouseCode
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
	"Regression" }, dataProvider = "getTestWareHouseCodes", dataProviderClass = LocationsTestDataProvider.class)
	public void createAndSearchZones(String wareHouseCode) throws UnsupportedEncodingException, JAXBException {
		String zoneBarcode=LocationUtils.createZoneAndReturnZoneBarcode(wareHouseCode);
	Assert.assertTrue(null!=zoneBarcode&& !zoneBarcode.isEmpty(), "The zone was not created");
	Assert.assertTrue(LocationUtils.searchAndVerifyIfZoneExists(zoneBarcode),"The zone "+zoneBarcode+"did not exist which was just created in the warehouse with code "+wareHouseCode);
	}

	/**The test is to try to create and search a section 
	 * @param wareHouseCode
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
	"Regression" }, dataProvider = "getTestWareHouseCodes", dataProviderClass = LocationsTestDataProvider.class)
	public void createAndSearchSection(String wareHouseCode) throws UnsupportedEncodingException, JAXBException {
		List<Long> zones = LocationUtils.getAllTheZoneIdsByWareHouseCode(wareHouseCode);
		Assert.assertTrue(null!=zones&& !zones.isEmpty(), "The zones could not be retrived from the warehouse with code  "+wareHouseCode);
		String sectionBarcode=LocationUtils.createSectionAndReturnSectionBarcode(zones.get(0).longValue());
		Assert.assertTrue(null!=sectionBarcode&& !sectionBarcode.isEmpty(), "The section was not created");
		Assert.assertTrue(LocationUtils.searchAndVerifyIfSectionExists(sectionBarcode),"The section "+sectionBarcode+" could not be retrived ...");
	}

	/**The test is to try to create and search an aisle 
	 * @param wareHouseCode
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
	"Regression" }, dataProvider = "getTestWareHouseCodes", dataProviderClass = LocationsTestDataProvider.class)
	public void createAndSearchAisle(String wareHouseCode) throws UnsupportedEncodingException, JAXBException {
	
		List<String> zones = LocationUtils.searchAndReturnAllZonesByWareHouse(wareHouseCode);
		Assert.assertTrue(null!=zones&& !zones.isEmpty(), "The zones could not be retrived from the warehouse with code  "+wareHouseCode);
		List<String> sections = LocationUtils.getAllTheSectionIdsByZoneBarcode(zones.get(0));
		Assert.assertTrue(null!=sections&& !sections.isEmpty(), "There were no sections retrived..");
		String aisleCode=LocationUtils.createSectionAndReturnAisleBarcode(Long.parseLong(sections.get(0).toString()));
		Assert.assertTrue(null!=aisleCode&& !aisleCode.isEmpty(), "There were no aisle created..");
		Assert.assertTrue(LocationUtils.searchAndVerifyIfAisleExists(aisleCode),"The aisle created could not be found upon searching by its code..");
	}

	/**The test is to try to create a bay
	 * @param wareHouseCode
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
	"Regression" }, dataProvider = "getTestWareHouseCodes", dataProviderClass = LocationsTestDataProvider.class)
	public void createBay(String wareHouseCode) throws UnsupportedEncodingException, JAXBException {
		List<String> zones = LocationUtils.searchAndReturnAllZonesByWareHouse(wareHouseCode);
		Assert.assertTrue(null!=zones&& !zones.isEmpty(), "The zones could not be retrived from the warehouse with code  "+wareHouseCode);
		List<String> sections = LocationUtils.getAllTheSectionIdsByZoneBarcode(zones.get(0));
		Assert.assertTrue(null!=sections&& !sections.isEmpty(), "There were no sections retrived..");
		// The response object of the create Bay does not have  public getData method to parse the data , thus we just assert the type of response as success
		LocationUtils.createBay(sections.get(0));
	}

	/**The test is to try to create and search a rack 
	 * @param wareHouseCode
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
	"Regression" }, dataProvider = "getTestWareHouseCodes", dataProviderClass = LocationsTestDataProvider.class)
	public void createRackAndSearchByItsCode(String wareHouseCode) throws UnsupportedEncodingException, JAXBException {
		
		List<Long> bays = LocationUtils.getAllBaysByWareHouseCode(wareHouseCode);
		Assert.assertTrue(null!=bays&& !bays.isEmpty(), "The bays could not be retreived from the  wareHouse with code "+wareHouseCode);
		String rackBarcode = LocationUtils.createRackAndReturnBarcode(bays.get(0));
		Assert.assertTrue(null!=rackBarcode&& !rackBarcode.isEmpty(), "The barcode could not be retreived upon an attempt to create  ");
		LocationUtils.searchAndVerifyIfRackExists(rackBarcode);
		Assert.assertTrue(LocationUtils.searchAndVerifyIfRackExists(rackBarcode), "The barcode could not be searched with its code :   "+rackBarcode);
	}
	/**The test is to try to create and search a bin 
	 * @param wareHouseCode
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = {
	"Regression" }, dataProvider = "getTestWareHouseCodes", dataProviderClass = LocationsTestDataProvider.class)
	public void createBinAndSearchByItsCode(String wareHouseCode) throws UnsupportedEncodingException, JAXBException {
		
		List<Long> racks = LocationUtils.getAllRacksByWareHouseCode(wareHouseCode);
		Assert.assertTrue(null!=racks&& !racks.isEmpty(), "The bays could not be retreived from the  wareHouse with code "+wareHouseCode);
		String binBarcode = LocationUtils.createBinAndReturnBarcode(racks.get(0));
		Assert.assertTrue(null!=binBarcode&& !binBarcode.isEmpty(), "The barcode could not be retreived upon an attempt to create  ");
		Assert.assertTrue(LocationUtils.searchAndVerifyIfBinExists(binBarcode), "The barcode could not be searched with its code :   "+binBarcode);
	}

}
