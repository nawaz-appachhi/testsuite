package com.myntra.apiTests.portalservices.checkoutservice;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.PropertyReader;

/**
 * @author shankara.c
 *
 */
public interface CheckoutServiceConstants 
{
	public static final PropertyReader PROPERTY_READER = new PropertyReader(new String[]{"./Data/dataproviders/checkoutdp.properties"});
	public static final Configuration CONFIGURATION = new Configuration("./Data/configuration");

	// ALL ENVIRONMENTS
	public static final String ENVIRONMENT_PROD = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ENVIRONMENT_PROD.name());
	public static final String ENVIRONMENT_FOX7 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ENVIRONMENT_FOX7.name());
	public static final String ENVIRONMENT_FUNC = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ENVIRONMENT_FUNC.name());
	public static final String ENVIRONMENT_PERF = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ENVIRONMENT_PERF.name());
	public static final String ENVIRONMENT_FOX8 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ENVIRONMENT_FOX8.name());
	
	//FOX8 ENV RELATED DATA
	public static final String FOX8_VALID_USERNAME1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX8_VALID_USERNAME1.name());
	public static final String FOX8_VALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX8_VALID_PASSWORD1.name()); 
	public static final String FOX8_VALID_USERNAME2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX8_VALID_USERNAME2.name());
	public static final String FOX8_VALID_PASSWORD2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX8_VALID_PASSWORD2.name()); 
	public static final String FOX8_VALID_USERNAME3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX8_VALID_USERNAME3.name());
	public static final String FOX8_VALID_PASSWORD3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX8_VALID_PASSWORD3.name()); 
	public static final String FOX8_VALID_USERNAME4 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX8_VALID_USERNAME4.name());
	public static final String FOX8_VALID_PASSWORD4 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX8_VALID_PASSWORD4.name()); 
	public static final String FOX8_VALID_USERNAME5 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX8_VALID_USERNAME5.name());
	public static final String FOX8_VALID_PASSWORD5 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX8_VALID_PASSWORD5.name()); 
		
	public static final String FOX8_CART_ADD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX8_CART_ADD_ITEM_QTY.name());
	public static final String FOX8_CART_UPD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX8_CART_UPD_ITEM_QTY.name());
		
	public static final String FOX8_PAYNOW_AMOUNT = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX8_PAYNOW_AMOUNT.name());
		
		
	
	// PROD ENV RELATED DATA
	public static final String PROD_VALID_USERNAME1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_USERNAME1.name());
	public static final String PROD_VALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_PASSWORD1.name()); 
	public static final String PROD_VALID_USERNAME2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_USERNAME2.name());
	public static final String PROD_VALID_PASSWORD2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_PASSWORD2.name()); 
	public static final String PROD_VALID_USERNAME3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_USERNAME3.name());
	public static final String PROD_VALID_PASSWORD3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_PASSWORD3.name());
	public static final String PROD_INVALID_USERNAME1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_INVALID_USERNAME1.name());
	public static final String PROD_INVALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_INVALID_PASSWORD1.name());
	public static final String PROD_INVALID_USERNAME2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_INVALID_USERNAME2.name());
	public static final String PROD_INVALID_PASSWORD2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_INVALID_PASSWORD2.name());
	public static final String PROD_VALID_ADD_ID = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_ADD_ID.name());
	public static final String PROD_INVALID_ADD_ID = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_INVALID_ADD_ID.name());
	public static final String PROD_COD_ABILITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_COD_ABILITY.name());
	public static final String PROD_COD_SERVICEABILITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_COD_SERVICEABILITY.name());
	public static final String PROD_NON_COD_SERVICEABILITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_NON_COD_SERVICEABILITY.name());
	public static final String PROD_VALID_ADDRESS1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_ADDRESS1.name());
	public static final String PROD_VALID_ADDRESS2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_ADDRESS2.name());
	public static final String PROD_VALID_ADDRESS3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_ADDRESS3.name());
	public static final String PROD_VALID_CITY1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_CITY1.name());
	public static final String PROD_VALID_CITY2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_CITY2.name());
	public static final String PROD_VALID_CITY3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_CITY3.name());
	public static final String PROD_VALID_COUNTRY_CODE1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_COUNTRY_CODE1.name());
	public static final String PROD_VALID_COUNTRY_CODE2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_COUNTRY_CODE2.name());
	public static final String PROD_VALID_COUNTRY_CODE3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_COUNTRY_CODE3.name());
	public static final String PROD_VALID_DEFAULT_ADDR1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_DEFAULT_ADDR1.name());
	public static final String PROD_VALID_DEFAULT_ADDR2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_DEFAULT_ADDR2.name());
	public static final String PROD_VALID_DEFAULT_ADDR3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_DEFAULT_ADDR3.name());
	public static final String PROD_VALID_LOCALITY1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_LOCALITY1.name());
	public static final String PROD_VALID_LOCALITY2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_LOCALITY2.name());
	public static final String PROD_VALID_LOCALITY3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_LOCALITY3.name());
	public static final String PROD_VALID_PIN_CODE1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_PIN_CODE1.name()); 
	public static final String PROD_VALID_PIN_CODE2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_PIN_CODE2.name());  
	public static final String PROD_VALID_PIN_CODE3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_PIN_CODE3.name()); 
	public static final String PROD_VALID_STATE_CODE1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_STATE_CODE1.name());
	public static final String PROD_VALID_STATE_CODE2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_STATE_CODE2.name());
	public static final String PROD_VALID_STATE_CODE3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_STATE_CODE3.name());
	public static final String PROD_VALID_STATE_NAME1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_STATE_NAME1.name()); 
	public static final String PROD_VALID_STATE_NAME2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_STATE_NAME2.name()); 
	public static final String PROD_VALID_STATE_NAME3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_STATE_NAME3.name()); 
	public static final String PROD_VALID_MOBILE_NO = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_VALID_MOBILE_NO.name()); 
	public static final String PROD_EMPTY_ADDRESS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_EMPTY_ADDRESS.name()); 
	public static final String PROD_EMPTY_CITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_EMPTY_CITY.name()); 
	public static final String PROD_EMPTY_COUNTRY_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_EMPTY_COUNTRY_CODE.name()); 
	public static final String PROD_EMPTY_STATE_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_EMPTY_STATE_CODE.name()); 
	public static final String PROD_UPD_ADDRESS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_UPD_ADDRESS.name());  
	public static final String PROD_UPD_CITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_UPD_CITY.name());  
	public static final String PROD_UPD_COUNTRY_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_UPD_COUNTRY_CODE.name());  
	public static final String PROD_UPD_DEFAULT_ADDR = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_UPD_DEFAULT_ADDR.name()); 
	public static final String PROD_UPD_LOCALITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_UPD_LOCALITY.name());
	public static final String PROD_UPD_PIN_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_UPD_PIN_CODE.name());
	public static final String PROD_UPD_STATE_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_UPD_STATE_CODE.name()); 
	public static final String PROD_UPD_STATE_NAME = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_UPD_STATE_NAME.name()); 
	public static final String PROD_UPD_MOBILE_NO = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_UPD_MOBILE_NO.name()); 
	public static final String PROD_CART_ADD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_CART_ADD_ITEM_QTY.name()); 
	public static final String PROD_CART_ADD_SKU_ID = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_CART_ADD_SKU_ID.name()); 
	public static final String PROD_CART_UPD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_CART_UPD_ITEM_QTY.name()); 
	public static final String PROD_WL_ADD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_WL_ADD_ITEM_QTY.name()); 
	public static final String PROD_WL_UPD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_WL_UPD_ITEM_QTY.name()); 
	public static final String PROD_AGW_GIFT_SENDER = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_AGW_GIFT_SENDER.name()); 
	public static final String PROD_AGW_GIFT_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_AGW_GIFT_MSG.name()); 
	public static final String PROD_AGW_GIFT_RECEPIENT = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_AGW_GIFT_RECEPIENT.name()); 
	public static final String PROD_CUSTOM_NAME = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_CUSTOM_NAME.name());
	public static final String PROD_CUSTOM_NUMBER = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_CUSTOM_NUMBER.name());
	public static final String PROD_DO_CUSTOMIZE1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_DO_CUSTOMIZE1.name());
	public static final String PROD_CREDIT_AMT = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PROD_CREDIT_AMT.name());
	
	// FOX7 ENV RELATED DATA
	public static final String FOX7_VALID_USERNAME1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_USERNAME1.name());
	public static final String FOX7_VALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_PASSWORD1.name()); 
	public static final String FOX7_VALID_USERNAME2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_USERNAME2.name());
	public static final String FOX7_VALID_PASSWORD2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_PASSWORD2.name()); 
	public static final String FOX7_VALID_USERNAME3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_USERNAME3.name());
	public static final String FOX7_VALID_PASSWORD3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_PASSWORD3.name()); 
	public static final String FOX7_VALID_USERNAME4 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_USERNAME4.name());
	public static final String FOX7_VALID_PASSWORD4 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_PASSWORD4.name()); 
	public static final String FOX7_VALID_USERNAME5 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_USERNAME5.name());
	public static final String FOX7_VALID_USERNAME6 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_USERNAME6.name());
	public static final String FOX7_VALID_PASSWORD = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_PASSWORD.name()); 
	
	public static final String FOX7_INVALID_USERNAME1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_INVALID_USERNAME1.name());
	public static final String FOX7_INVALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_INVALID_PASSWORD1.name()); 
	public static final String FOX7_INVALID_USERNAME2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_INVALID_USERNAME2.name());
	public static final String FOX7_INVALID_PASSWORD2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_INVALID_PASSWORD2.name()); 
	public static final String FOX7_VALID_ADD_ID = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_ADD_ID.name()); 
	public static final String FOX7_INVALID_ADD_ID = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_INVALID_ADD_ID.name()); 
	public static final String FOX7_COD_ABILITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_COD_ABILITY.name()); 
	public static final String FOX7_COD_SERVICEABILITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_COD_SERVICEABILITY.name()); 
	public static final String FOX7_NON_COD_SERVICEABILITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_NON_COD_SERVICEABILITY.name());
	public static final String FOX7_VALID_ADDRESS1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_ADDRESS1.name());
	public static final String FOX7_VALID_ADDRESS2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_ADDRESS2.name());
	public static final String FOX7_VALID_ADDRESS3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_ADDRESS3.name());
	public static final String FOX7_VALID_CITY1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_CITY1.name());
	public static final String FOX7_VALID_CITY2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_CITY2.name());
	public static final String FOX7_VALID_CITY3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_CITY3.name());
	public static final String FOX7_VALID_COUNTRY_CODE1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_COUNTRY_CODE1.name());
	public static final String FOX7_VALID_COUNTRY_CODE2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_COUNTRY_CODE2.name());
	public static final String FOX7_VALID_COUNTRY_CODE3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_COUNTRY_CODE3.name());
	public static final String FOX7_VALID_DEFAULT_ADDR1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_DEFAULT_ADDR1.name());
	public static final String FOX7_VALID_DEFAULT_ADDR2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_DEFAULT_ADDR2.name());
	public static final String FOX7_VALID_DEFAULT_ADDR3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_DEFAULT_ADDR3.name());
	public static final String FOX7_VALID_LOCALITY1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_LOCALITY1.name());
	public static final String FOX7_VALID_LOCALITY2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_LOCALITY2.name());
	public static final String FOX7_VALID_LOCALITY3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_LOCALITY3.name());
	public static final String FOX7_VALID_PIN_CODE1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_PIN_CODE1.name());
	public static final String FOX7_VALID_PIN_CODE2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_PIN_CODE2.name());
	public static final String FOX7_VALID_PIN_CODE3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_PIN_CODE3.name());
	public static final String FOX7_VALID_STATE_CODE1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_STATE_CODE1.name());
	public static final String FOX7_VALID_STATE_CODE2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_STATE_CODE2.name());
	public static final String FOX7_VALID_STATE_CODE3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_STATE_CODE3.name());
	public static final String FOX7_VALID_STATE_NAME1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_STATE_NAME1.name());
	public static final String FOX7_VALID_STATE_NAME2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_STATE_NAME2.name());
	public static final String FOX7_VALID_STATE_NAME3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_STATE_NAME3.name());
	public static final String FOX7_VALID_MOBILE_NO = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_VALID_MOBILE_NO.name());
	public static final String FOX7_EMPTY_ADDRESS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_EMPTY_ADDRESS.name());
	public static final String FOX7_EMPTY_CITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_EMPTY_CITY.name());
	public static final String FOX7_EMPTY_COUNTRY_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_EMPTY_COUNTRY_CODE.name());
	public static final String FOX7_EMPTY_STATE_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_EMPTY_STATE_CODE.name());
	public static final String FOX7_UPD_ADDRESS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_UPD_ADDRESS.name());
	public static final String FOX7_UPD_CITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_UPD_CITY.name());
	public static final String FOX7_UPD_COUNTRY_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_UPD_COUNTRY_CODE.name());
	public static final String FOX7_UPD_DEFAULT_ADDR = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_UPD_DEFAULT_ADDR.name());
	public static final String FOX7_UPD_LOCALITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_UPD_LOCALITY.name());
	public static final String FOX7_UPD_PIN_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_UPD_PIN_CODE.name());
	public static final String FOX7_UPD_STATE_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_UPD_STATE_CODE.name());
	public static final String FOX7_UPD_STATE_NAME = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_UPD_STATE_NAME.name());
	public static final String FOX7_UPD_MOBILE_NO = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_UPD_MOBILE_NO.name());
	public static final String FOX7_CART_ADD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_CART_ADD_ITEM_QTY.name());
	public static final String FOX7_CART_ADD_SKU_ID = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_CART_ADD_SKU_ID.name());
	public static final String FOX7_CART_UPD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_CART_UPD_ITEM_QTY.name());
	public static final String FOX7_WL_ADD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_WL_ADD_ITEM_QTY.name());
	public static final String FOX7_WL_UPD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_WL_UPD_ITEM_QTY.name());
	public static final String FOX7_AGW_GIFT_SENDER = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_AGW_GIFT_SENDER.name());
	public static final String FOX7_AGW_GIFT_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_AGW_GIFT_MSG.name());
	public static final String FOX7_AGW_GIFT_RECEPIENT = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_AGW_GIFT_RECEPIENT.name());
	public static final String FOX7_CUSTOM_NAME = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_CUSTOM_NAME.name());
	public static final String FOX7_CUSTOM_NUMBER = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_CUSTOM_NUMBER.name());
	public static final String FOX7_DO_CUSTOMIZE1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_DO_CUSTOMIZE1.name());
	public static final String FOX7_CREDIT_AMT = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_CREDIT_AMT.name());
	public static final String FOX7_PAYNOW_AMOUNT = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX7_PAYNOW_AMOUNT.name());
	
	// FUNC ENV RELATED DATA
	public static final String FUNC_VALID_USERNAME1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_USERNAME1.name());
	public static final String FUNC_VALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_PASSWORD1.name()); 
	public static final String FUNC_VALID_USERNAME2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_USERNAME2.name());
	public static final String FUNC_VALID_PASSWORD2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_PASSWORD2.name());
	public static final String FUNC_VALID_USERNAME3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_USERNAME3.name());
	public static final String FUNC_VALID_PASSWORD3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_PASSWORD3.name());
	public static final String FUNC_INVALID_USERNAME1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_INVALID_USERNAME1.name());
	public static final String FUNC_INVALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_INVALID_PASSWORD1.name()); 
	public static final String FUNC_INVALID_USERNAME2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_INVALID_USERNAME2.name());
	public static final String FUNC_INVALID_PASSWORD2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_INVALID_PASSWORD2.name());
	public static final String FUNC_VALID_ADD_ID = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_ADD_ID.name());
	public static final String FUNC_INVALID_ADD_ID = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_INVALID_ADD_ID.name());
	public static final String FUNC_COD_ABILITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_COD_ABILITY.name());
	public static final String FUNC_COD_SERVICEABILITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_COD_SERVICEABILITY.name()); 
	public static final String FUNC_NON_COD_SERVICEABILITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_NON_COD_SERVICEABILITY.name()); 
	public static final String FUNC_VALID_ADDRESS1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_ADDRESS1.name()); 
	public static final String FUNC_VALID_ADDRESS2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_ADDRESS2.name()); 
	public static final String FUNC_VALID_ADDRESS3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_ADDRESS3.name()); 
	public static final String FUNC_VALID_CITY1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_CITY1.name());
	public static final String FUNC_VALID_CITY2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_CITY2.name());
	public static final String FUNC_VALID_CITY3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_CITY3.name());
	public static final String FUNC_VALID_COUNTRY_CODE1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_COUNTRY_CODE1.name());
	public static final String FUNC_VALID_COUNTRY_CODE2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_COUNTRY_CODE2.name()); 
	public static final String FUNC_VALID_COUNTRY_CODE3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_COUNTRY_CODE3.name());  
	public static final String FUNC_VALID_DEFAULT_ADDR1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_DEFAULT_ADDR1.name()); 
	public static final String FUNC_VALID_DEFAULT_ADDR2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_DEFAULT_ADDR2.name()); 
	public static final String FUNC_VALID_DEFAULT_ADDR3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_DEFAULT_ADDR3.name());
	public static final String FUNC_VALID_LOCALITY1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_LOCALITY1.name()); 
	public static final String FUNC_VALID_LOCALITY2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_LOCALITY2.name()); 
	public static final String FUNC_VALID_LOCALITY3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_LOCALITY3.name()); 
	public static final String FUNC_VALID_PIN_CODE1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_PIN_CODE1.name()); 
	public static final String FUNC_VALID_PIN_CODE2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_PIN_CODE2.name()); 
	public static final String FUNC_VALID_PIN_CODE3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_PIN_CODE3.name()); 
	public static final String FUNC_VALID_STATE_CODE1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_STATE_CODE1.name());
	public static final String FUNC_VALID_STATE_CODE2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_STATE_CODE2.name());
	public static final String FUNC_VALID_STATE_CODE3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_STATE_CODE3.name());
	public static final String FUNC_VALID_STATE_NAME1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_STATE_NAME1.name());
	public static final String FUNC_VALID_STATE_NAME2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_STATE_NAME2.name());
	public static final String FUNC_VALID_STATE_NAME3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_STATE_NAME3.name());
	public static final String FUNC_VALID_MOBILE_NO = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_VALID_MOBILE_NO.name());
	public static final String FUNC_EMPTY_ADDRESS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_EMPTY_ADDRESS.name());
	public static final String FUNC_EMPTY_CITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_EMPTY_CITY.name());
	public static final String FUNC_EMPTY_COUNTRY_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_EMPTY_COUNTRY_CODE.name());
	public static final String FUNC_EMPTY_STATE_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_EMPTY_STATE_CODE.name());
	public static final String FUNC_UPD_ADDRESS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_UPD_ADDRESS.name());
	public static final String FUNC_UPD_CITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_UPD_CITY.name());
	public static final String FUNC_UPD_COUNTRY_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_UPD_COUNTRY_CODE.name());
	public static final String FUNC_UPD_DEFAULT_ADDR = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_UPD_DEFAULT_ADDR.name());
	public static final String FUNC_UPD_LOCALITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_UPD_LOCALITY.name());
	public static final String FUNC_UPD_PIN_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_UPD_PIN_CODE.name());
	public static final String FUNC_UPD_STATE_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_UPD_STATE_CODE.name());
	public static final String FUNC_UPD_STATE_NAME = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_UPD_STATE_NAME.name());
	public static final String FUNC_UPD_MOBILE_NO = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_UPD_MOBILE_NO.name());
	public static final String FUNC_CART_ADD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_CART_ADD_ITEM_QTY.name());
	public static final String FUNC_CART_ADD_SKU_ID = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_CART_ADD_SKU_ID.name());
	public static final String FUNC_CART_UPD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_CART_UPD_ITEM_QTY.name());
	public static final String FUNC_WL_ADD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_WL_ADD_ITEM_QTY.name());
	public static final String FUNC_WL_UPD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_WL_UPD_ITEM_QTY.name());
	public static final String FUNC_AGW_GIFT_SENDER = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_AGW_GIFT_SENDER.name());
	public static final String FUNC_AGW_GIFT_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_AGW_GIFT_MSG.name());
	public static final String FUNC_AGW_GIFT_RECEPIENT = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_AGW_GIFT_RECEPIENT.name());
	public static final String FUNC_CUSTOM_NAME = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_CUSTOM_NAME.name());
	public static final String FUNC_CUSTOM_NUMBER = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_CUSTOM_NUMBER.name());
	public static final String FUNC_DO_CUSTOMIZE1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_DO_CUSTOMIZE1.name());
	public static final String FUNC_CREDIT_AMT = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_CREDIT_AMT.name());
	
	// PERF RELATED DATA
	public static final String PERF_VALID_USERNAME1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_USERNAME1.name());
	public static final String PERF_VALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_PASSWORD1.name()); 
	public static final String PERF_VALID_USERNAME2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_USERNAME2.name());
	public static final String PERF_VALID_PASSWORD2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_PASSWORD2.name());
	public static final String PERF_VALID_USERNAME3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_USERNAME3.name());
	public static final String PERF_VALID_PASSWORD3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_PASSWORD3.name());
	public static final String PERF_INVALID_USERNAME1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_INVALID_USERNAME1.name());
	public static final String PERF_INVALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_INVALID_PASSWORD1.name()); 
	public static final String PERF_INVALID_USERNAME2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_INVALID_USERNAME2.name());
	public static final String PERF_INVALID_PASSWORD2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_INVALID_PASSWORD2.name());
	public static final String PERF_VALID_ADD_ID = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_ADD_ID.name());
	public static final String PERF_INVALID_ADD_ID = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_INVALID_ADD_ID.name());
	public static final String PERF_COD_ABILITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_COD_ABILITY.name());
	public static final String PERF_COD_SERVICEABILITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_COD_SERVICEABILITY.name()); 
	public static final String PERF_NON_COD_SERVICEABILITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_NON_COD_SERVICEABILITY.name()); 
	public static final String PERF_VALID_ADDRESS1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_ADDRESS1.name()); 
	public static final String PERF_VALID_ADDRESS2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_ADDRESS2.name()); 
	public static final String PERF_VALID_ADDRESS3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_ADDRESS3.name()); 
	public static final String PERF_VALID_CITY1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_CITY1.name());
	public static final String PERF_VALID_CITY2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_CITY2.name());
	public static final String PERF_VALID_CITY3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_CITY3.name());
	public static final String PERF_VALID_COUNTRY_CODE1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_COUNTRY_CODE1.name());
	public static final String PERF_VALID_COUNTRY_CODE2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_COUNTRY_CODE2.name()); 
	public static final String PERF_VALID_COUNTRY_CODE3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_COUNTRY_CODE3.name());  
	public static final String PERF_VALID_DEFAULT_ADDR1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_DEFAULT_ADDR1.name()); 
	public static final String PERF_VALID_DEFAULT_ADDR2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_DEFAULT_ADDR2.name()); 
	public static final String PERF_VALID_DEFAULT_ADDR3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_DEFAULT_ADDR3.name());
	public static final String PERF_VALID_LOCALITY1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_LOCALITY1.name()); 
	public static final String PERF_VALID_LOCALITY2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_LOCALITY2.name()); 
	public static final String PERF_VALID_LOCALITY3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_LOCALITY3.name()); 
	public static final String PERF_VALID_PIN_CODE1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_PIN_CODE1.name()); 
	public static final String PERF_VALID_PIN_CODE2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_PIN_CODE2.name()); 
	public static final String PERF_VALID_PIN_CODE3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_PIN_CODE3.name()); 
	public static final String PERF_VALID_STATE_CODE1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_STATE_CODE1.name());
	public static final String PERF_VALID_STATE_CODE2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_STATE_CODE2.name());
	public static final String PERF_VALID_STATE_CODE3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_STATE_CODE3.name());
	public static final String PERF_VALID_STATE_NAME1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_STATE_NAME1.name());
	public static final String PERF_VALID_STATE_NAME2 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_STATE_NAME2.name());
	public static final String PERF_VALID_STATE_NAME3 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_STATE_NAME3.name());
	public static final String PERF_VALID_MOBILE_NO = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_VALID_MOBILE_NO.name());
	public static final String PERF_EMPTY_ADDRESS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_EMPTY_ADDRESS.name());
	public static final String PERF_EMPTY_CITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_EMPTY_CITY.name());
	public static final String PERF_EMPTY_COUNTRY_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_EMPTY_COUNTRY_CODE.name());
	public static final String PERF_EMPTY_STATE_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_EMPTY_STATE_CODE.name());
	public static final String PERF_UPD_ADDRESS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_UPD_ADDRESS.name());
	public static final String PERF_UPD_CITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_UPD_CITY.name());
	public static final String PERF_UPD_COUNTRY_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_UPD_COUNTRY_CODE.name());
	public static final String PERF_UPD_DEFAULT_ADDR = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_UPD_DEFAULT_ADDR.name());
	public static final String PERF_UPD_LOCALITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_UPD_LOCALITY.name());
	public static final String PERF_UPD_PIN_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_UPD_PIN_CODE.name());
	public static final String PERF_UPD_STATE_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_UPD_STATE_CODE.name());
	public static final String PERF_UPD_STATE_NAME = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_UPD_STATE_NAME.name());
	public static final String PERF_UPD_MOBILE_NO = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_UPD_MOBILE_NO.name());
	public static final String PERF_CART_ADD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_CART_ADD_ITEM_QTY.name());
	public static final String PERF_CART_ADD_SKU_ID = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_CART_ADD_SKU_ID.name());
	public static final String PERF_CART_UPD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_CART_UPD_ITEM_QTY.name());
	public static final String PERF_WL_ADD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_WL_ADD_ITEM_QTY.name());
	public static final String PERF_WL_UPD_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_WL_UPD_ITEM_QTY.name());
	public static final String PERF_AGW_GIFT_SENDER = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_AGW_GIFT_SENDER.name());
	public static final String PERF_AGW_GIFT_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_AGW_GIFT_MSG.name());
	public static final String PERF_AGW_GIFT_RECEPIENT = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_AGW_GIFT_RECEPIENT.name());
	public static final String PERF_CUSTOM_NAME = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_CUSTOM_NAME.name());
	public static final String PERF_CUSTOM_NUMBER = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_CUSTOM_NUMBER.name());
	public static final String PERF_DO_CUSTOMIZE1 = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.PERF_DO_CUSTOMIZE1.name());
	public static final String PERF_CREDIT_AMT = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FUNC_CREDIT_AMT.name());
	
	// DATA RELATED addNewAddress API
	public static final String VALID_ADDRESS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.VALID_ADDRESS.toString()); 
	public static final String VALID_CITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.VALID_CITY.toString()); 
	public static final String VALID_COUNTRY_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.VALID_COUNTRY_CODE.toString()); 
	public static final String VALID_DEFAULT_ADDRESS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.VALID_DEFAULT_ADDR.toString()); 
	public static final String VALID_LOCALITY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.VALID_LOCALITY.toString());
	public static final String VALID_PINCODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.VALID_PIN_CODE.toString()); 
	public static final String VALID_STATE_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.VALID_STATE_CODE.toString()); 
	public static final String VALID_STATE_NAME = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.VALID_STATE_NAME.toString()); 
	public static final String VALID_MOBILE_NUMBER = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.VALID_MOBILE_NO.toString());
	
	// COMMONS DATA
	public static final String EMPTY_VALUE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.EMPTY_VALUE.name());
	public static final String TRUE_VALUE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.TRUE_VALUE.toString());
	public static final String FALSE_VALUE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FALSE_VALUE.toString());

	// SUCCESS RELATED DATA
	public static final String SUCCESS_RESPONSE_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.VALID_RESP_CODE.name());
	public static final String SUCCESS_STATUS_TYPE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.SUCCESS_STATUS_TYPE.toString());
	public static final String SUCCESS_STATUS_CODE = 	PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.SUCCESS_STATUS_CODE.name());
	public static final String SUCCESS_STATUS_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.SUCCESS_STATUS_MSG.name()); 
	public static final String ADDR_RET_STATUS_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ADDR_RET_STATUS_CODE.name());
	public static final String ADDR_RET_STATUS_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ADDR_RET_STATUS_MSG.name());
	public static final String ADDR_ADD_STATUS_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ADDR_ADD_STATUS_CODE.name()); 
	public static final String ADDR_ADD_STATUS_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ADDR_ADD_STATUS_MSG.name());
	public static final String ADDR_UPD_STATUS_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ADDR_UPD_STATUS_CODE.name());
	public static final String ADDR_UPD_STATUS_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ADDR_UPD_STATUS_MSG.name());
	public static final String ADDR_DEL_STATUS_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ADDR_DEL_STATUS_CODE.name());
	public static final String ADDR_DEL_STATUS_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ADDR_DEL_STATUS_MSG.name());
	public static final String ADDR_NF_STATUS_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ADDR_NF_STATUS_CODE.name());
	public static final String ADDR_NF_STATUS_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ADDR_NF_STATUS_MSG.name());
	
	// FAILURE RELATED DATA
	public static final String FAILURE_STATUS_TYPE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FAILURE_STATUS_TYPE.name());
	public static final String NOT_AUTH_STATUS_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.NOT_AUTH_STATUS_CODE.name());
	public static final String NOT_AUTH_STATUS_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.NOT_AUTH_STATUS_MSG.name());
	public static final String INV_ADDR_STATUS_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.INV_ADDR_STATUS_CODE.name());
	public static final String INV_ADDR_STATUS_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.INV_ADDR_STATUS_MSG.name());
	public static final String INV_CITY_STATUS_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.INV_CITY_STATUS_CODE.name());
	public static final String INV_CITY_STATUS_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.INV_CITY_STATUS_MSG.name());
	public static final String INV_CNTRY_STATUS_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.INV_CNTRY_STATUS_CODE.name());
	public static final String INV_CNTRY_STATUS_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.INV_CNTRY_STATUS_MSG.name());
	public static final String INV_ST_STATUS_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.INV_ST_STATUS_CODE.name());
	public static final String INV_ST_STATUS_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.INV_ST_STATUS_MSG.name());
	public static final String ERROR_STATUS_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ERROR_STATUS_CODE.name());
	public static final String ERROR_STATUS_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ERROR_STATUS_MSG.name());
	public static final String INV_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.INV_ITEM_QTY.name());
	public static final String NEG_INV_ITEM_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.NEG_INV_ITEM_QTY.name());
	
	public static final String INVALID_RESP_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.INVALID_RESP_CODE.name());
	
	// DATA RELATED TO CART SERVICES
	public static final String USE_CREDIT_AMT = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.USE_CREDIT_AMT.name());
	public static final String RGW_GIFT_SENDER = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.RGW_GIFT_SENDER.name());
	public static final String RGW_GIFT_MSG = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.RGW_GIFT_MSG.name());
	public static final String RGW_GIFT_RECEPIENT = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.RGW_GIFT_RECEPIENT.name());
	
	public static final String LAA_SEARCH_PROD = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.LAA_SEARCH_PROD.toString());
	public static final String LAA_SEARCH_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.LAA_SEARCH_QTY.toString());
	public static final String LAA_SEARCH_RET_DOCS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.LAA_SEARCH_RET_DOCS.toString());
	public static final String LAA_SEARCH_IS_FACET = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.LAA_SEARCH_IS_FACET.toString());
	
	public static final String AC_SEARCH_PROD = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.AC_SEARCH_PROD.toString());
	public static final String AC_SEARCH_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.AC_SEARCH_QTY.toString());
	public static final String AC_SEARCH_RET_DOCS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.AC_SEARCH_RET_DOCS.toString());
	public static final String AC_SEARCH_IS_FACET = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.AC_SEARCH_IS_FACET.toString());
	
	public static final String CC_SEARCH_PROD = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.CC_SEARCH_PROD.toString());
	public static final String CC_SEARCH_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.CC_SEARCH_QTY.toString());
	public static final String CC_SEARCH_RET_DOCS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.CC_SEARCH_RET_DOCS.toString());
	public static final String CC_SEARCH_IS_FACET = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.CC_SEARCH_IS_FACET.toString());
	
	public static final String UC_SEARCH_PROD = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.UC_SEARCH_PROD.toString());
	public static final String UC_SEARCH_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.UC_SEARCH_QTY.toString());
	public static final String UC_SEARCH_RET_DOCS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.UC_SEARCH_RET_DOCS.toString());
	public static final String UC_SEARCH_IS_FACET = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.UC_SEARCH_IS_FACET.toString());
	
	public static final String RC_SEARCH_PROD = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.RC_SEARCH_PROD.toString());
	public static final String RC_SEARCH_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.RC_SEARCH_QTY.toString());
	public static final String RC_SEARCH_RET_DOCS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.RC_SEARCH_RET_DOCS.toString());
	public static final String RC_SEARCH_IS_FACET = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.RC_SEARCH_IS_FACET.toString());
	
	public static final String AGW_SEARCH_PROD = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.AGW_SEARCH_PROD.toString());
	public static final String AGW_SEARCH_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.AGW_SEARCH_QTY.toString());
	public static final String AGW_SEARCH_RET_DOCS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.AGW_SEARCH_RET_DOCS.toString());
	public static final String AGW_SEARCH_IS_FACET = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.AGW_SEARCH_IS_FACET.toString());
	
	public static final String RGW_SEARCH_PROD = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.RGW_SEARCH_PROD.toString());
	public static final String RGW_SEARCH_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.RGW_SEARCH_QTY.toString());
	public static final String RGW_SEARCH_RET_DOCS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.RGW_SEARCH_RET_DOCS.toString());
	public static final String RGW_SEARCH_IS_FACET = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.RGW_SEARCH_IS_FACET.toString());
	
	public static final String CFG_SEARCH_PROD = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.CFG_SEARCH_PROD.toString());
	public static final String CFG_SEARCH_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.CFG_SEARCH_QTY.toString());
	public static final String CFG_SEARCH_RET_DOCS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.CFG_SEARCH_RET_DOCS.toString());
	public static final String CFG_SEARCH_IS_FACET = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.CFG_SEARCH_IS_FACET.toString());

	public static final String MWL_SEARCH_PROD = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.MWL_SEARCH_PROD.toString());
	public static final String MWL_SEARCH_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.MWL_SEARCH_QTY.toString());
	public static final String MWL_SEARCH_RET_DOCS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.MWL_SEARCH_RET_DOCS.toString());
	public static final String MWL_SEARCH_IS_FACET = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.MWL_SEARCH_IS_FACET.toString());
	
	public static final String OCC_SEARCH_PROD = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.OCC_SEARCH_PROD.toString());
	public static final String OCC_SEARCH_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.OCC_SEARCH_QTY.toString());
	public static final String OCC_SEARCH_RET_DOCS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.OCC_SEARCH_RET_DOCS.toString());
	public static final String OCC_SEARCH_IS_FACET = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.OCC_SEARCH_IS_FACET.toString());

	// DATA FOR WISHLIST API'S
	public static final String AWL_SEARCH_PROD = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.AWL_SEARCH_PROD.toString());
	public static final String AWL_SEARCH_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.AWL_SEARCH_QTY.toString());
	public static final String AWL_SEARCH_RET_DOCS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.AWL_SEARCH_RET_DOCS.toString());
	public static final String AWL_SEARCH_IS_FACET = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.AWL_SEARCH_IS_FACET.toString());
	
	public static final String UWL_SEARCH_PROD = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.UWL_SEARCH_PROD.toString());
	public static final String UWL_SEARCH_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.UWL_SEARCH_QTY.toString());
	public static final String UWL_SEARCH_RET_DOCS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.UWL_SEARCH_RET_DOCS.toString());
	public static final String UWL_SEARCH_IS_FACET = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.UWL_SEARCH_IS_FACET.toString());
	
	public static final String RWL_SEARCH_PROD = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.RWL_SEARCH_PROD.toString());
	public static final String RWL_SEARCH_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.RWL_SEARCH_QTY.toString());
	public static final String RWL_SEARCH_RET_DOCS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.RWL_SEARCH_RET_DOCS.toString());
	public static final String RWL_SEARCH_IS_FACET = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.RWL_SEARCH_IS_FACET.toString());
	
	public static final String MC_SEARCH_PROD = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.MC_SEARCH_PROD.toString());
	public static final String MC_SEARCH_QTY = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.MC_SEARCH_QTY.toString());
	public static final String MC_SEARCH_RET_DOCS = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.MC_SEARCH_RET_DOCS.toString());
	public static final String MC_SEARCH_IS_FACET = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.MC_SEARCH_IS_FACET.toString());
	
	// OPERATIONS FOR CART/WISHLIST API'S
	public static final String ADD_OPERATION = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.ADD_OPERATION.toString());
	public static final String UPDATE_OPERATION = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.UPDATE_OPERATION.toString());
	public static final String DELETE_OPERATION = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.DELETE_OPERATION.toString());
	public static final String CUSTOMIZE_OPERATION = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.CUSTOMIZE_OPERATION.toString());
	
	public static final String FOX_VALID_ROLE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX_VALID_ROLE.toString());
	public static final String FOX_INVALID_ROLE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX_INVALID_ROLE.toString());
	public static final String FOX_VALID_LOGIN = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX_VALID_LOGIN.toString());
	public static final String FOX_INVALID_LOGIN = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX_INVALID_LOGIN.toString());
	public static final String FOX_VALID_TOKEN = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX_VALID_TOKEN.toString());
	public static final String FOX_INVALID_TOKEN = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX_INVALID_TOKEN.toString());
	public static final String FOX_VALID_STATUS_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX_VALID_STATUS_CODE.toString());
	public static final String FOX_INVALID_STATUS_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX_INVALID_STATUS_CODE.toString());
	public static final String FOX_VALID_RESPONSE_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX_VALID_RESPONSE_CODE.toString());
	public static final String FOX_INVALID_RESPONSE_CODE = PROPERTY_READER.getPropertyValue(CheckoutDataProviderEnum.FOX_INVALID_RESPONSE_CODE.toString());
	
	
}
