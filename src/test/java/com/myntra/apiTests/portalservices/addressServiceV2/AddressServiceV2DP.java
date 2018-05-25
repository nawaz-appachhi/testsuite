package com.myntra.apiTests.portalservices.addressServiceV2;

import org.testng.annotations.DataProvider;

public class AddressServiceV2DP {

    //String name, String address, String city, String stateName, String countryName, String pincode, String email, String mobile, String locality, String addressType

    @DataProvider
    public Object[][] addNewAddressDP() {
        String[] validDataMyntra = new String[] {"TestName1", "Address line 1", "Bangalore", "Karanataka", "India", "560068", "testemail1@myntra.com", "10000111101", "locality1", "home"};
        return new Object[][]{validDataMyntra};
    }

}
