package com.myntra.apiTests.portalservices.addressServiceV2;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.concurrent.Callable;

@Data
@AllArgsConstructor
public class CreateAddressUnit implements Callable<Boolean>, AddressConstants {

    private AddressServiceHelperV2 addressServiceHelperV2;
    private String address;
    private String city;
    private String countryCode;
    private String defaultAddress;
    private String email;
    private String locality;
    private String name;
    private String pincode;
    private String stateCode;
    private String stateName;
    private String mobile;
    private String nidx;
    private String userId;
    private String storeId;

    @Override
    public Boolean call() {
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ nidx +";storeid="+storeId + ";uidx=" + userId;
        headers.put("Content-Type", "application/json");
        headers.put("x-mynt-ctx", xMyntCtx);
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        assert addressServiceHelperV2.checkIfRequestIsSuccessful(addAddressRequest, "ADD");
        String resp = addAddressRequest.respvalidate.returnresponseasstring();
        Integer addressId = JsonPath.read(resp, "$.data[0].id");
        return addressServiceHelperV2.getAddressIdForUser(userId,  name.toLowerCase().contains("myntra") ? "myntra" : "jabong").contains(addressId);
    }
}
