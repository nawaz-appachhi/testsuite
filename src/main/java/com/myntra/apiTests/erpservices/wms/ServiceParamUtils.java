package com.myntra.apiTests.erpservices.wms;

import com.myntra.apiTests.erpservices.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceParamUtils {
	private static Logger log = LoggerFactory.getLogger(ServiceParamUtils.class);
	private final static String SEARCH_LOCATION_SUFFIX = "&p=&start=0&fetchSize=100";

	public static final String[] getUrlParamsToSearchForWareHouseByLocationName(String wareHouseLocationName) {
		
		
		return new String[] { Constants.WMS_PATH.SEARCH_LOCATION_PREFIX + "3AWAREHOUSE___locationName%3A"
				+ wareHouseLocationName + SEARCH_LOCATION_SUFFIX };
	}

	public static final String[] getUrlParamsToSearchForWareHouseByWareHouseBarcode(String wareHouseBarcode) {
		return new String[] { Constants.WMS_PATH.SEARCH_LOCATION_PREFIX + "3AWAREHOUSE___warehouseBarcode%3A"
				+ wareHouseBarcode + SEARCH_LOCATION_SUFFIX };
	}

	public static final String[] getUrlParamsToFetchZonesByWareHouseBarcode(String wareHouseBarcode) {
		return new String[] { Constants.WMS_PATH.SEARCH_LOCATION_PREFIX + "3AZONE___warehouseBarcode%3A"
				+ wareHouseBarcode + SEARCH_LOCATION_SUFFIX };
	}

	public static final String[] getUrlParamsToFetchSectionsByZoneBarCode(String zoneBarCode) {
		return new String[] { Constants.WMS_PATH.SEARCH_LOCATION_PREFIX + "3ASECTION___zoneBarcode%3A" + zoneBarCode
				+ SEARCH_LOCATION_SUFFIX };
	}

	public static final String[] getUrlParamsToFetchAislesByWareHouseBarCode(String zoneBarCode) {
		return new String[] { Constants.WMS_PATH.SEARCH_LOCATION_PREFIX + "3AAISLE___warehouseBarcode%3A" + zoneBarCode
				+ SEARCH_LOCATION_SUFFIX };
	}

	public static final String[] getUrlParamsToFetchBaysByWareHouseBarCode(String warehouseBarcode) {
		return new String[] { Constants.WMS_PATH.SEARCH_LOCATION_PREFIX + "3ABAY___warehouseBarcode%3A"
				+ warehouseBarcode + SEARCH_LOCATION_SUFFIX };
	}

	public static final String[] getUrlParamsToFetchRacksByWareHouseBarCode(String warehouseBarcode) {
		return new String[] { Constants.WMS_PATH.SEARCH_LOCATION_PREFIX + "3ARACK___warehouseBarcode%3A"
				+ warehouseBarcode + SEARCH_LOCATION_SUFFIX };
	}

	public static final String[] getUrlParamsToSearchForZoneByLocationName(String zoneBarcode) {
		return new String[] { Constants.WMS_PATH.SEARCH_LOCATION_PREFIX + "3AZONE___zoneBarcode%3A" + zoneBarcode
				+ SEARCH_LOCATION_SUFFIX };

	}

	public static final String[] getUrlParamsToSearchForSectionByLocationName(String locationName) {
		return new String[] { Constants.WMS_PATH.SEARCH_LOCATION_PREFIX + "3ASECTION___locationName%3A" + locationName
				+ SEARCH_LOCATION_SUFFIX };

	}

	public static final String[] getUrlParamsToSearchForSectionBySectionBarcode(String sectionBarCode) {
		return new String[] { Constants.WMS_PATH.SEARCH_LOCATION_PREFIX + "3ASECTION___sectionBarcode%3A"
				+ sectionBarCode + SEARCH_LOCATION_SUFFIX };

	}

	public static final String[] getUrlParamsToSearchForAisleByBarcode(String aisleBarcode) {
		return new String[] { Constants.WMS_PATH.SEARCH_LOCATION_PREFIX + "3AAISLE___aisleBarcode%3A" + aisleBarcode
				+ SEARCH_LOCATION_SUFFIX };

	}

	public static final String[] getUrlParamsToGetAllBaysByWareHouseCode(String wareHouseCode) {
		return new String[] { Constants.WMS_PATH.SEARCH_LOCATION_PREFIX + "3ABAY___warehouseBarcode%3A" + wareHouseCode
				+ SEARCH_LOCATION_SUFFIX };

	}

	public static final String[] getUrlParamsToSearchForRackByrackBarcode(String rackBarcode) {
		return new String[] { Constants.WMS_PATH.SEARCH_LOCATION_PREFIX + "3ARACK___rackBarcode%3A" + rackBarcode
				+ SEARCH_LOCATION_SUFFIX };

	}

	public static final String[] getUrlParamsToSearchForRackByWareHouseCode(String wareHouseCode) {
		return new String[] { Constants.WMS_PATH.SEARCH_LOCATION_PREFIX+"3ARACK___warehouseBarcode%3A" + wareHouseCode
				+ SEARCH_LOCATION_SUFFIX };

	}

	public static final String[] getUrlParamsToSearchForBinByBinBarcode(String binCode) {
		return new String[] { Constants.WMS_PATH.SEARCH_LOCATION_PREFIX + "3ABIN___binBarcode%3A" + binCode
				+ SEARCH_LOCATION_SUFFIX };
	}
}
