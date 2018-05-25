package com.myntra.apiTests.portalservices.checkoutservice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shankara.c
 *
 */
public enum AddressNodes {
	
	ADDRESS_ID("data.id"),
	ADDRESS_USER_ID("data.userId"),
	ADDRESS_DEFAULT_ADDR("data.defaultAddress"),
	ADDRESS_USER_NAME("data.name"),
	ADDRESS_ADDR("data.address"),
	ADDRESS_CITY("data.city"),
	ADDRESS_STATECODE("data.stateCode"),
	ADDRESS_STATENAME("data.stateName"),
	ADDRESS_COUNTRY_CODE("data.countryCode"),
	ADDRESS_COUNTRY_NAME("data.countryName"),
	ADDRESS_PIN_CODE("data.pincode"),
	ADDRESS_EMAIL("data.email"),
	ADDRESS_MOBILE("data.mobile"),
	ADDRESS_LOCALITY("data.locality"),
	
	ADDRESS_COD_AVAILABILITY_ENTRY("data.codAvailabilityEntry"),
	ADDRESS_COD_AVAILABILITY_SERVICEABILITY("data.codAvailabilityEntry.serviceability"),
	ADDRESS_COD_AVAILABILITY_ERROR_CODE("data.codAvailabilityEntry.errorCode"),
	ADDRESS_COD_AVAILABILITY_ERROR_MSG("data.codAvailabilityEntry.errorMessage"),
	
	ADDRESS_ADDR_SERVICEABILITY_ENTRY("data.addressServiceabilityEntry"),
	ADDRESS_ADDR_SERVICEABILITY_COD_SERVICEABILITY("data.addressServiceabilityEntry.codServiceability"),
	ADDRESS_ADDR_SERVICEABILITY_COD_NON_SERVICEABILITY("data.addressServiceabilityEntry.nonCODServiceability"),
	ADDRESS_ADDR_SERVICEABILITY_EXPRESS_SERVICEABILITIES("data.addressServiceabilityEntry.expressServiceabilities"),
	
	
	ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES("data.addressServiceabilityEntry.addressItemEntries"),
	ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES_DELPROMISETIME("data.addressServiceabilityEntry.addressItemEntries.deliveryPromiseTime"),
	ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES_TNBENABLED("data.addressServiceabilityEntry.addressItemEntries.tryAndBuyEnabled"),
	ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES_TNBOPTED("data.addressServiceabilityEntry.addressItemEntries.tryandBuyOpted"),
	ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES_TNBSERVICEABLE("data.addressServiceabilityEntry.addressItemEntries.tryAndBuyServiceable");
	

	
	private String nodePath;
	
	private AddressNodes(String nodePath) {
		this.nodePath = nodePath;
	}
	
	public String getNodePath() {
		return nodePath;
	}
	
	public static List<String> getAddressNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(AddressNodes.ADDRESS_ID.toString());
		addNodes.add(AddressNodes.ADDRESS_USER_ID.toString());
		addNodes.add(AddressNodes.ADDRESS_DEFAULT_ADDR.toString());
		addNodes.add(AddressNodes.ADDRESS_USER_NAME.toString());
		addNodes.add(AddressNodes.ADDRESS_ADDR.toString());
		addNodes.add(AddressNodes.ADDRESS_CITY.toString());
		addNodes.add(AddressNodes.ADDRESS_STATECODE.toString());
		addNodes.add(AddressNodes.ADDRESS_STATENAME.toString());
		addNodes.add(AddressNodes.ADDRESS_COUNTRY_CODE.toString());
		addNodes.add(AddressNodes.ADDRESS_COUNTRY_NAME.toString());
		addNodes.add(AddressNodes.ADDRESS_PIN_CODE.toString());
		addNodes.add(AddressNodes.ADDRESS_EMAIL.toString());
		addNodes.add(AddressNodes.ADDRESS_MOBILE.toString());
		addNodes.add(AddressNodes.ADDRESS_LOCALITY.toString());
		return addNodes;		
	}
	
	public static List<String> getCODAvailabilityEntryNodes(){
		List<String> codAvailabilityEntryNodes = new ArrayList<>();
		codAvailabilityEntryNodes.add(ADDRESS_COD_AVAILABILITY_ENTRY.toString());
		codAvailabilityEntryNodes.add(ADDRESS_COD_AVAILABILITY_SERVICEABILITY.toString());
		codAvailabilityEntryNodes.add(ADDRESS_COD_AVAILABILITY_ERROR_CODE.toString());
		codAvailabilityEntryNodes.add(ADDRESS_COD_AVAILABILITY_ERROR_MSG.toString());
		
		return codAvailabilityEntryNodes;
		
	}
	
	public static List<String> getAddresServiceabilityEntryNodes(){
		List<String> addrServiceability = new ArrayList<>();
		addrServiceability.add(ADDRESS_ADDR_SERVICEABILITY_ENTRY.toString());
		addrServiceability.add(ADDRESS_ADDR_SERVICEABILITY_COD_SERVICEABILITY.toString());
		addrServiceability.add(ADDRESS_ADDR_SERVICEABILITY_COD_NON_SERVICEABILITY.toString());
		addrServiceability.add(ADDRESS_ADDR_SERVICEABILITY_EXPRESS_SERVICEABILITIES.toString());
		
		return addrServiceability;
	}
	
	public static List<String> getCompleteAddressNodes(){
		List<String> completeAddressList = new ArrayList<>();
		completeAddressList.addAll(getAddressNodes());
		completeAddressList.addAll(getCODAvailabilityEntryNodes());
		completeAddressList.addAll(getAddresServiceabilityEntryNodes());
		
		return completeAddressList;
	}
	
	@Override
	public String toString() {
		return getNodePath();
	}
	
}
