package com.myntra.apiTests.portalservices.addressServiceV2;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AddressServiceV2Test extends BaseTest implements AddressConstants {

    static Logger log = Logger.getLogger(AddressServiceV2Test.class);
    AddressServiceHelperV2 addressServiceHelperV2 = new AddressServiceHelperV2();
    static Initialize init = new Initialize("/Data/configuration");

    @DataProvider(name = "myntra")
    public Object[][] addNewAddressDP() {
        String[] validDataMyntra = new String[] {"Address line 1", "Bangalore", "IN", "true", "testemail1@myntra.com", "locality1", "Myntra Address", "560034", "KA", "Karanataka", "7000000000"};
        return new Object[][]{validDataMyntra};
    }

    @Test(priority = 1, dataProvider = "myntra")
    public void addNewMyntraAddressWithValidMyntraStoreId(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid="+MYNTRA_STORE_ID + ";uidx="+ tokens[3];
        headers.put("Content-Type", "application/json");
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        assert addressServiceHelperV2.checkIfRequestIsSuccessful(addAddressRequest, "ADD");
        assert addressServiceHelperV2.verifyAddressResponse(addAddressRequest, null, "myntra");
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @DataProvider(name = "jabong")
    public Object[][] addNewAddressJabongDP() {
        String[] validDataMyntra = new String[] {"Address line 1 Jabong", "Bangalore", "IN", "false", "testemail1@myntra.com", "locality1", "Jabong Address", "560034", "KA", "Karanataka", "7000000001"};
        return new Object[][]{validDataMyntra};
    }

    @Test(priority = 2, dataProvider = "jabong")
    public void addNewJabongAddressWithValidJabongStoreId(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid="+JABONG_STORE_ID+ ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        assert addressServiceHelperV2.checkIfRequestIsSuccessful(addAddressRequest, "ADD");
        assert addressServiceHelperV2.verifyAddressResponse(addAddressRequest, null, "jabong");
        addressServiceHelperV2.performSignOutOperation(JABONG_USER, tokens[1]);
    }

    @DataProvider(name = "invalidStoreId")
    public Object[][] addNewAddressWithinvalidStoreid() {
        String[] validDataMyntra = new String[] {"Address line 1 Jabong", "Bangalore", "IN", "true", "testemail1@myntra.com", "locality1", "Jabong Address", "560034", "KA", "Karanataka", "7000000001"};
        return new Object[][]{validDataMyntra};
    }

    @Test(priority = 3, dataProvider = "invalidStoreId")
    public void addNewAddressByPassingInvalidStoreId(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=invalid_store_id" + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        assert Objects.equals(addAddressRequest.respvalidate.returnresponseasstring(), STORE_ID_INVALID_ERR_MSG);
        assert Objects.equals(addAddressRequest.response.getStatus(), 400);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @DataProvider(name = "addNewAddressDPXidHeaderOnly")
    public Object[][] addNewAddressDPXidHeaderOnly() {
        String[] validDataMyntra = new String[] {"Address line 1", "Bangalore", "IN", "true", "testemail1@myntra.com", "locality1", "Myntra Address", "560034", "KA", "Karanataka", "7000000000"};
        return new Object[][]{validDataMyntra};
    }

    @Test(priority = 4, dataProvider = "addNewAddressDPXidHeaderOnly")
    public void addNewAddressByPassingXidHeaderOnlyForMyntra(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        headers.put("xid", tokens[0]);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        assert Objects.equals(addAddressRequest.respvalidate.returnresponseasstring(), STORE_ID_MISSING_ERR_MSG);
        assert Objects.equals(addAddressRequest.response.getStatus(), 400);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 5, dataProvider = "addNewAddressDPXidHeaderOnly")
    public void addNewAddressByPassingXidHeaderOnlyForJabong(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        headers.put("xid", tokens[0]);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        assert Objects.equals(addAddressRequest.respvalidate.returnresponseasstring(), STORE_ID_MISSING_ERR_MSG);
        assert Objects.equals(addAddressRequest.response.getStatus(), 400);
        addressServiceHelperV2.performSignOutOperation(JABONG_USER, tokens[1]);
    }

    @Test(priority = 6, dataProvider = "myntra")
    public void addNewAddressByNotPassingStoreIdHeaderForMyntra(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid="+ ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        addAddressRequest.reqHeader.forEach((key, value) ->
                System.out.println("key is " +key + "value is " + value));
        assert Objects.equals(addAddressRequest.respvalidate.returnresponseasstring(), STORE_ID_MISSING_ERR_MSG);
        assert Objects.equals(addAddressRequest.response.getStatus(), 400);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 7, dataProvider = "jabong")
    public void addNewAddressByNotPassingStoreIdHeaderForJabong(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=" + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        assert Objects.equals(addAddressRequest.respvalidate.returnresponseasstring(), STORE_ID_MISSING_ERR_MSG);
        assert Objects.equals(addAddressRequest.response.getStatus(), 400);
        addressServiceHelperV2.performSignOutOperation(JABONG_USER, tokens[1]);
    }

    @Test(priority = 8, dataProvider = "myntra")
    public void addNewAddressByPassingEmptyNidxForMyntra(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String sessionId = RandomStringUtils.random(46, true, true);
        String xMyntCtx = "nidx=" +";storeid="+MYNTRA_STORE_ID + ";uidx="+ UUID.randomUUID();
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        assert addAddressRequest.respvalidate.returnresponseasstring().equals(NIDX_MISSING_ERR_MSG);
        assert Objects.equals(addAddressRequest.response.getStatus(), 400);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 9, dataProvider = "jabong")
    public void addNewAddressByPassingEmptyNidxForJabong(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String sessionId = RandomStringUtils.random(46, true, true);
        String xMyntCtx = "nidx="+ ";storeid="+JABONG_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        assert addAddressRequest.respvalidate.returnresponseasstring().equals(NIDX_MISSING_ERR_MSG);
        assert Objects.equals(addAddressRequest.response.getStatus(), 400);
        addressServiceHelperV2.performSignOutOperation(JABONG_USER, tokens[1]);
    }

    @Test(priority = 10, dataProvider = "myntra")
    public void addNewAddressWithoutXMyntCtxHeaderForMyntra(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        assert Objects.equals(addAddressRequest.respvalidate.returnresponseasstring(), STORE_ID_MISSING_ERR_MSG);
        assert Objects.equals(addAddressRequest.response.getStatus(), 400);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 11, dataProvider = "jabong")
    public void addNewAddressWithoutXMyntCtxHeaderForJabong(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        assert Objects.equals(addAddressRequest.respvalidate.returnresponseasstring(), STORE_ID_MISSING_ERR_MSG);
        assert Objects.equals(addAddressRequest.response.getStatus(), 400);
        addressServiceHelperV2.performSignOutOperation(JABONG_USER, tokens[1]);
    }

    @DataProvider(name = "updateMyntraAddress")
    public Object[][] updateAddressDPMyntra() {
        String[] validDataMyntra = new String[] {" Update Address line 1", "Bangalore", "IN", "true", "testemail1@myntra.com", "locality2", "Update Myntra Address", "560068", "KA", "Karanataka", "7000000000"};
        return new Object[][]{validDataMyntra};
    }

    @Test(priority = 12, dataProvider = "updateMyntraAddress")
    public void updatedAddressForMyntra(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid="+MYNTRA_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressIds.get(0).toString(), headers);
        assert addressServiceHelperV2.checkIfRequestIsSuccessful(updateAddressReq, "UPDATE");
        assert addressServiceHelperV2.verifyAddressResponse(updateAddressReq, addressIds.get(0).toString(), "myntra");
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @DataProvider(name = "updateJabongAddress")
    public Object[][] updateAddressJabongDP() {
        String[] validDataMyntra = new String[] {"Update Address line 1 Jabong", "Bangalore", "IN", "true", "testemail1@myntra.com", "locality1", "Update Jabong Address", "560034", "KA", "Karanataka", "7000000001"};
        return new Object[][]{validDataMyntra};
    }

    @Test(priority = 13, dataProvider = "updateJabongAddress")
    public void updatedAddressForJabong(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid="+JABONG_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressIds.get(0).toString(), headers);
        assert addressServiceHelperV2.checkIfRequestIsSuccessful(updateAddressReq, "UPDATE");
        assert addressServiceHelperV2.verifyAddressResponse(updateAddressReq, addressIds.get(0).toString(), "jabong");
        addressServiceHelperV2.performSignOutOperation(JABONG_USER, tokens[1]);
    }

    @Test(priority = 14, dataProvider = "updateMyntraAddress")
    public void updateMyntraAddressByPassingJabongStoreIdInHeader(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid="+JABONG_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressIds.get(0).toString(), headers);
        System.out.println(updateAddressReq.respvalidate.returnresponseasstring());
        assert addressServiceHelperV2.verifyFailedRequest(updateAddressReq, "Error occurred while retrieving/processing data", null);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 15, dataProvider = "updateJabongAddress")
    public void updateJabongAddressByPassingMyntraStoreIdInHeader(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid="+MYNTRA_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        String addressId = addressIds.get(0).toString();
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressId, headers);
        assert (addressServiceHelperV2.verifyFailedRequest(updateAddressReq, "Client is not authorized to perform this operation.", null) || addressServiceHelperV2.verifyFailedRequest(updateAddressReq, "Error occurred while retrieving/processing data", null));
        addressServiceHelperV2.performSignOutOperation(JABONG_USER, tokens[1]);
    }

    @Test(priority = 16, dataProvider = "updateMyntraAddress")
    public void updateAddressByPassingInvalidAddressId(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid="+MYNTRA_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String addressId = "999999999";
        String userId = tokens[3];
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressId, headers);
        assert addressServiceHelperV2.verifyFailedRequest(updateAddressReq, "Error occurred while retrieving/processing data", null);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 17, dataProvider = "updateMyntraAddress")
    public void updateAddressByPassingInvalidStoreIdInHeader(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=invalid_store_id" + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressId, headers);
        assert updateAddressReq.respvalidate.returnresponseasstring().equals(STORE_ID_INVALID_ERR_MSG);
        assert updateAddressReq.response.getStatus() == 400;
    }

    @Test(priority = 18, dataProvider = "updateMyntraAddress")
    public void updateAddressByPassingOnlyXidAsHeaderForMyntra(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        headers.put("xid", tokens[0]);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressId, headers);
        assert updateAddressReq.respvalidate.returnresponseasstring().equals(STORE_ID_MISSING_ERR_MSG);
        assert updateAddressReq.response.getStatus() == 400;
    }

    @Test(priority = 19, dataProvider = "updateJabongAddress")
    public void updateAddressByPassingOnlyXidAsHeaderForJabong(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        headers.put("xid", tokens[0]);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        String addressId = addressIds.get(0).toString();
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressId, headers);
        assert updateAddressReq.respvalidate.returnresponseasstring().equals(STORE_ID_MISSING_ERR_MSG);
        assert updateAddressReq.response.getStatus() == 400;
    }

    @Test(priority = 20, dataProvider = "updateMyntraAddress")
    public void updateAddressByNotPassingStoreIdInTheHeaderForMyntra(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressId, headers);
        assert updateAddressReq.respvalidate.returnresponseasstring().equals(STORE_ID_MISSING_ERR_MSG);
        assert updateAddressReq.response.getStatus() == 400;
    }

    @Test(priority = 21, dataProvider = "updateJabongAddress")
    public void updateAddressbyNotPassingStoreidInHeaderForJabong(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        String addressId = addressIds.get(0).toString();
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressId, headers);
        assert updateAddressReq.respvalidate.returnresponseasstring().equals(STORE_ID_MISSING_ERR_MSG);
        assert updateAddressReq.response.getStatus() == 400;
    }

    @Test(priority = 21, dataProvider = "updateMyntraAddress")
    public void updateAddressbyNotPassingXMyntCtxHeader(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressId, headers);
        assert updateAddressReq.respvalidate.returnresponseasstring().equals(STORE_ID_MISSING_ERR_MSG);
        assert updateAddressReq.response.getStatus() == 400;
    }

    @Test(priority = 22, dataProvider =  "updateMyntraAddress")
    public void updateAddressByPassingXidOfOneUserAndAddressIdOfAnotherUser(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens1 = addressServiceHelperV2.loginAndGetMyntraTokens(ANOTHER_USER_EMAIL, ANOTHER_USER_PASS);
        String anotherUserId = tokens1[3];
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid="+MYNTRA_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(anotherUserId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddress(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressId, headers);
        assert addressServiceHelperV2.verifyFailedRequest(updateAddressReq, "Client is not authorized to perform this operation.", null);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 23)
    public void deleteMyntraAddress() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid="+MYNTRA_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteAddress = addressServiceHelperV2.deleteAddress(addressId, headers);
        assert addressServiceHelperV2.checkIfRequestIsSuccessful(deleteAddress, "DEL");
        assert !addressServiceHelperV2.getAddressIdForUser(userId, "myntra").contains(Integer.valueOf(addressId));
    }

    @Test(priority = 24)
    public void deleteJabongAddress() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid="+JABONG_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteAddress = addressServiceHelperV2.deleteAddress(addressId, headers);
        assert addressServiceHelperV2.checkIfRequestIsSuccessful(deleteAddress, "DEL");
        assert !addressServiceHelperV2.getAddressIdForUser(userId, "jabong").contains(Integer.valueOf(addressId));
    }

    @Test(priority = 25)
    public void deleteMyntraAddressByPassingJabongStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid="+JABONG_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteAddress = addressServiceHelperV2.deleteAddress(addressId, headers);
        System.out.println(deleteAddress.respvalidate.returnresponseasstring());
        assert addressServiceHelperV2.verifyFailedRequest(deleteAddress, "Error occurred while retrieving/processing data", null);
        assert addressServiceHelperV2.getAddressIdForUser(userId, "myntra").contains(Integer.valueOf(addressId));
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 26)
    public void deleteJabongAddressByPassingMyntraStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid="+MYNTRA_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteAddress = addressServiceHelperV2.deleteAddress(addressId, headers);
        assert (addressServiceHelperV2.verifyFailedRequest(deleteAddress, "Client is not authorized to perform this operation.", null) || addressServiceHelperV2.verifyFailedRequest(deleteAddress, "Error occurred while retrieving/processing data", null));
        assert addressServiceHelperV2.getAddressIdForUser(userId, "jabong").contains(Integer.valueOf(addressId));
        addressServiceHelperV2.performSignOutOperation(JABONG_USER, tokens[1]);
    }

    @Test(priority = 27)
    public void deleteAddressByNotPassingStoreIdInTheHeader() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteAddress = addressServiceHelperV2.deleteAddress(addressId, headers);
        assert deleteAddress.respvalidate.returnresponseasstring().equals("store Id missing");
        assert deleteAddress.response.getStatus() == 400;
        assert addressServiceHelperV2.getAddressIdForUser(userId, "myntra").contains(Integer.valueOf(addressId));
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 28)
    public void deleteAddressByNotPassingXidInTheHeader() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid="+MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteAddress = addressServiceHelperV2.deleteAddress(addressId, headers);
        assert deleteAddress.respvalidate.returnresponseasstring().equals(NIDX_MISSING_ERR_MSG);
        assert deleteAddress.response.getStatus() == 400;
        assert addressServiceHelperV2.getAddressIdForUser(userId, "myntra").contains(Integer.valueOf(addressId));
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 29)
    public void deleteAddressByNotPassingAnyHeaderInTheRequest() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteAddress = addressServiceHelperV2.deleteAddress(addressId, headers);
        assert deleteAddress.respvalidate.returnresponseasstring().equals(STORE_ID_MISSING_ERR_MSG);
        assert deleteAddress.response.getStatus() == 400;
        assert addressServiceHelperV2.getAddressIdForUser(userId, "myntra").contains(Integer.valueOf(addressId));
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 30)
    public void deleteAddressByPassingEmptyStoreIdInTheHeader() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid="  + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteAddress = addressServiceHelperV2.deleteAddress(addressId, headers);
        assert deleteAddress.respvalidate.returnresponseasstring().equals("store Id missing");
        assert deleteAddress.response.getStatus() == 400;
        assert addressServiceHelperV2.getAddressIdForUser(userId, "myntra").contains(Integer.valueOf(addressId));
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 31)
    public void deleteAddressByPassingWrongAddressIdForMyntra() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=" + MYNTRA_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String addressId = "99990009";
        String userId = tokens[3];
        RequestGenerator deleteAddress = addressServiceHelperV2.deleteAddress(addressId, headers);
        assert addressServiceHelperV2.verifyFailedRequest(deleteAddress, "Error occurred while retrieving/processing data", null);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 32)
    public void deleteAddressByPassingWrongAddressIdForJabong() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=" + JABONG_STORE_ID  + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String addressId = "99990009";
        RequestGenerator deleteAddress = addressServiceHelperV2.deleteAddress(addressId, headers);
        assert addressServiceHelperV2.verifyFailedRequest(deleteAddress, "Error occurred while retrieving/processing data", null);
        addressServiceHelperV2.performSignOutOperation(JABONG_USER, tokens[1]);
    }

    @Test(priority = 33)
    public void deleteAddressByPassingEmptyNidxInTheHeader() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx=" +";storeid=" + MYNTRA_STORE_ID  + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteAddress = addressServiceHelperV2.deleteAddress(addressId, headers);
        assert deleteAddress.respvalidate.returnresponseasstring().equals(NIDX_MISSING_ERR_MSG);
        assert Objects.equals(deleteAddress.response.getStatus(), 400);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 34)
    public void deleteAddressByPassingXidOfOneUserAndAddressIdOfAnother() {
        String[] tokens1 = addressServiceHelperV2.loginAndGetMyntraTokens(ANOTHER_USER_EMAIL, ANOTHER_USER_PASS);
        String anotherUserId = tokens1[3];
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=" + MYNTRA_STORE_ID  + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(anotherUserId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteAddress = addressServiceHelperV2.deleteAddress(addressId, headers);
        assert addressServiceHelperV2.verifyFailedRequest(deleteAddress, "Client is not authorized to perform this operation.", null);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 35)
    public void fetchAllMyntraAddress() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=" + MYNTRA_STORE_ID  + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddress(headers);
        assert addressServiceHelperV2.checkIfRequestIsSuccessful(fetchAllAddressReq, "FETCHALL");
        assert addressServiceHelperV2.verifyAllFetchedAddress(fetchAllAddressReq, userId, "myntra");
    }

    @Test(priority = 36)
    public void fetchAllJabongAddress() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=" + JABONG_STORE_ID  + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddress(headers);
        assert addressServiceHelperV2.checkIfRequestIsSuccessful(fetchAllAddressReq, "FETCHALL");
        assert addressServiceHelperV2.verifyAllFetchedAddress(fetchAllAddressReq, userId, "jabong");
    }

    @Test(priority = 37)
    public void fetchAllMyntraAddressWithoutPassingStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0];
        headers.put("x-mynt-ctx", xMyntCtx);
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddress(headers);
        assert fetchAllAddressReq.respvalidate.returnresponseasstring().equals("store Id missing");
        assert fetchAllAddressReq.response.getStatus() == 400;
    }

    @Test(priority = 38)
    public void fetchAllMyntraAddressWithoutPassingXid() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddress(headers);
        assert fetchAllAddressReq.respvalidate.returnresponseasstring().equals(NIDX_MISSING_ERR_MSG);
        assert fetchAllAddressReq.response.getStatus() == 400;
    }

    @Test(priority = 39)
    public void fetchAllAddressByPassingInvalidStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=invalid_store_id" + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddress(headers);
        assert fetchAllAddressReq.respvalidate.returnresponseasstring().equals(STORE_ID_INVALID_ERR_MSG);
        assert fetchAllAddressReq.response.getStatus() == 400;
    }

    @Test(priority = 40, enabled = false)
    public void fetchAllAddressByPassingEmptyUidx() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        String sessionId = RandomStringUtils.random(46, true, true);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ sessionId +";storeid=" + MYNTRA_STORE_ID + ";uidx=";
        headers.put("x-mynt-ctx", xMyntCtx);
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddress(headers);
        assert fetchAllAddressReq.respvalidate.returnresponseasstring().equals(UIDX_MISSING_ERR_MEG);
        assert Objects.equals(fetchAllAddressReq.response.getStatus(), 400);
    }

    @Test(priority = 40)
    public void fetchAllAddressByPassingOnlyXIDHeader() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        headers.put("xid", tokens[0]);
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddress(headers);
        assert fetchAllAddressReq.respvalidate.returnresponseasstring().equals(STORE_ID_MISSING_ERR_MSG);
        assert fetchAllAddressReq.response.getStatus() == 400;
    }

    @Test(priority = 41)
    public void fetchAllAddressByPassingNoHeader() {
        HashMap<String, String > headers = new HashMap<>();
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddress(headers);
        assert fetchAllAddressReq.respvalidate.returnresponseasstring().equals(STORE_ID_MISSING_ERR_MSG);
        assert fetchAllAddressReq.response.getStatus() == 400;
    }

    @Test(priority = 42)
    public void fetchSingleAddressByPassingMyntraAddressIdAndStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=" + MYNTRA_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchSingleAddress(addressId, headers);
        addressServiceHelperV2.checkIfRequestIsSuccessful(fetchAllAddressReq, "FETCH");
        String resp = fetchAllAddressReq.respvalidate.returnresponseasstring();
        assert (addressServiceHelperV2.getAddressIdForUser(userId, "myntra").contains(JsonPath.read(resp, "$.data[0].id")) && JsonPath.read(resp, "$.data[0].id").toString().equals(addressId));
    }

    @Test(priority = 43)
    public void fetchSingleAddressByPassingJabongAddressIdAndStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=" + JABONG_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchSingleAddress(addressId, headers);
        addressServiceHelperV2.checkIfRequestIsSuccessful(fetchAllAddressReq, "FETCH");
        String resp = fetchAllAddressReq.respvalidate.returnresponseasstring();
        assert (addressServiceHelperV2.getAddressIdForUser(userId, "jabong").contains(JsonPath.read(resp, "$.data[0].id")) && JsonPath.read(resp, "$.data[0].id").toString().equals(addressId));
    }

    @Test(priority = 44)
    public void fetchSingleAddressByPassingMyntraAddressIdAndJabongStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=" + JABONG_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchSingleAddress(addressId, headers);
        assert addressServiceHelperV2.verifyFailedRequest(fetchAllAddressReq, "Error occurred while retrieving/processing data", null);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 45)
    public void fetchSingleAddressByPassingJabongAddressIdAndMyntraStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=" + MYNTRA_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchSingleAddress(addressId, headers);
        String resp = fetchAllAddressReq.respvalidate.returnresponseasstring();
        assert JsonPath.read(resp, "$.status.statusMessage").equals("Address not found") || JsonPath.read(resp, "$.status.statusMessage").equals("Error occurred while retrieving/processing data");
    }

    @Test(priority = 46)
    public void fetchSingleAddressByInvalidStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=invalid_store_id" + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchSingleAddress(addressId, headers);
        assert fetchAllAddressReq.respvalidate.returnresponseasstring().equals(STORE_ID_INVALID_ERR_MSG);
        assert fetchAllAddressReq.response.getStatus() == 400;
    }

    @Test(priority = 47)
    public void fetchSingleAddressByEmptyUidx() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String sessionId = RandomStringUtils.random(46, true, true);
        String xMyntCtx = "nidx="+ sessionId +";storeid=" + MYNTRA_STORE_ID + ";uidx=";
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchSingleAddress(addressId, headers);
        assert fetchAllAddressReq.respvalidate.returnresponseasstring().equals(UIDX_MISSING_ERR_MEG);
        assert Objects.equals(fetchAllAddressReq.response.getStatus(), 400);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 48)
    public void fetchSingleAddressWithoutAnyHeader() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchSingleAddress(addressId, headers);
        assert fetchAllAddressReq.respvalidate.returnresponseasstring().equals(STORE_ID_MISSING_ERR_MSG);
        assert fetchAllAddressReq.response.getStatus() == 400;
    }

    @Test(priority = 49)
    public void fetchSingleAddressByPassingOnlyMyntraXid() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        headers.put("xid", tokens[0]);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchSingleAddress(addressId, headers);
        assert fetchAllAddressReq.respvalidate.returnresponseasstring().equals(STORE_ID_MISSING_ERR_MSG);
        assert fetchAllAddressReq.response.getStatus() == 400;
    }

    @Test(priority = 50)
    public void fetchSingleAddressByPassingOnlyJabongXid() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        headers.put("xid", tokens[0]);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchSingleAddress(addressId, headers);
        assert fetchAllAddressReq.respvalidate.returnresponseasstring().equals(STORE_ID_MISSING_ERR_MSG);
        assert fetchAllAddressReq.response.getStatus() == 400;
    }

    @Test(priority = 51)
    public void fetchSingleAddressByPassingXidOfOneUserAndAddressIdOfAnotherUser() {
        String[] tokens1 = addressServiceHelperV2.loginAndGetMyntraTokens(ANOTHER_USER_EMAIL, ANOTHER_USER_PASS);
        String anotherUserId = tokens1[3];
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=" + MYNTRA_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(anotherUserId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchSingleAddress(addressId, headers);
        String resp = fetchAllAddressReq.respvalidate.returnresponseasstring();
        assert JsonPath.read(resp, "$.status.statusMessage").equals("Address not found");
    }

    @Test(priority = 52)
    public void fetchSingleAddressByPassingEmptyStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=" + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchSingleAddress(addressId, headers);
        assert fetchAllAddressReq.respvalidate.returnresponseasstring().equals("store Id missing");
        assert fetchAllAddressReq.response.getStatus() == 400;
    }

    @Test(priority = 53)
    public void fetchSIngleAddressByPassingInvalidAddressId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "nidx="+ tokens[0] +";storeid=" + MYNTRA_STORE_ID + ";uidx="+ tokens[3];
        headers.put("x-mynt-ctx", xMyntCtx);
        String addressId = "99999000999";
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchSingleAddress(addressId, headers);
        assert addressServiceHelperV2.verifyFailedRequest(fetchAllAddressReq, "Error occurred while retrieving/processing data", null);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 54, dataProvider = "myntra")
    public void addNewMyntraAddressUsingSecureRoute(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid="+MYNTRA_STORE_ID;
        headers.put("Content-Type", "application/json");
        headers.put("role", "OMS_USER");
        headers.put("token", "124");
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddressSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        assert addAddressRequest.response.getStatus() == 200;
        assert addressServiceHelperV2.verifyAddressResponse(addAddressRequest, null, "myntra");
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 55, dataProvider = "jabong")
    public void addNewJabongAddressUsingSecureRoute(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid="+JABONG_STORE_ID;
        headers.put("Content-Type", "application/json");
        headers.put("role", "OMS_USER");
        headers.put("token", "124");
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddressSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        assert addAddressRequest.response.getStatus() == 200;
        assert addressServiceHelperV2.verifyAddressResponse(addAddressRequest, null, "jabong");
        addressServiceHelperV2.performSignOutOperation(JABONG_USER, tokens[1]);
    }

    @Test(priority = 56, dataProvider = "myntra")
    public void addNewMyntraAddressUsingSecureRouteWithInvalidRole(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid="+MYNTRA_STORE_ID;
        headers.put("Content-Type", "application/json");
        headers.put("role", "INVALID");
        headers.put("token", "124");
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddressSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        String resp = addAddressRequest.respvalidate.returnresponseasstring();
        assert addAddressRequest.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 57, dataProvider = "jabong")
    public void addNewJabongAddressUsingSecureRouteWithInvalidRole(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid="+JABONG_STORE_ID;
        headers.put("Content-Type", "application/json");
        headers.put("role", "INVALID");
        headers.put("token", "124");
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddressSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        String resp = addAddressRequest.respvalidate.returnresponseasstring();
        assert addAddressRequest.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 59, dataProvider = "myntra")
    public void addNewMyntraAddressUsingSecureRouteWithoutRole(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid="+MYNTRA_STORE_ID;
        headers.put("Content-Type", "application/json");
        headers.put("token", "124");
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddressSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        String resp = addAddressRequest.respvalidate.returnresponseasstring();
        assert addAddressRequest.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 60, dataProvider = "jabong")
    public void addNewJabongAddressUsingSecureRouteWithoutRole(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid="+JABONG_STORE_ID;
        headers.put("Content-Type", "application/json");
        headers.put("token", "124");
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddressSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        String resp = addAddressRequest.respvalidate.returnresponseasstring();
        assert addAddressRequest.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 61, dataProvider = "myntra")
    public void addNewMyntraAddressUsingSecureRouteWithInvalidToken(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid="+MYNTRA_STORE_ID;
        headers.put("Content-Type", "application/json");
        headers.put("role", "OMS_USER");
        headers.put("token", "1234");
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddressSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        String resp = addAddressRequest.respvalidate.returnresponseasstring();
        assert addAddressRequest.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 62, dataProvider = "jabong")
    public void addNewJabongAddressUsingSecureRouteWithInvalidToken(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid="+JABONG_STORE_ID;
        headers.put("Content-Type", "application/json");
        headers.put("role", "CC_USER");
        headers.put("token", "1234");
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddressSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        String resp = addAddressRequest.respvalidate.returnresponseasstring();
        assert addAddressRequest.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 63, dataProvider = "myntra")
    public void addNewMyntraAddressUsingSecureRouteWithoutToken(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid="+MYNTRA_STORE_ID;
        headers.put("Content-Type", "application/json");
        headers.put("role", "CC_USER");
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddressSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        String resp = addAddressRequest.respvalidate.returnresponseasstring();
        assert addAddressRequest.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 64, dataProvider = "jabong")
    public void addNewJabongAddressUsingSecureRouteWithoutToken(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid="+JABONG_STORE_ID;
        headers.put("Content-Type", "application/json");
        headers.put("role", "CC_USER");
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddressSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        String resp = addAddressRequest.respvalidate.returnresponseasstring();
        assert addAddressRequest.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 65, dataProvider = "myntra")
    public void addNewMyntraAddressUsingSecureRouteWithoutXMyntHeader(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddressSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        String resp = addAddressRequest.respvalidate.returnresponseasstring();
        assert addAddressRequest.response.getStatus() == 400;
        assert Objects.equals(resp, "store Id missing");
    }

    @Test(priority = 66, dataProvider = "myntra")
    public void addNewAddressUsingSecureRouteWithInvalidStoreid(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String xMyntCtx = "storeid=invalid_store_id";
        headers.put("role", "CC_USER");
        headers.put("token", "123");
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddressSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        String resp = addAddressRequest.respvalidate.returnresponseasstring();
        assert addAddressRequest.response.getStatus() == 400;
        assert Objects.equals(resp, STORE_ID_INVALID_ERR_MSG);
    }

    @Test(priority = 67, dataProvider = "myntra")
    public void addNewAddressUsingSecureRouteWithEmptyStoreid(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String xMyntCtx = "storeid=";
        headers.put("role", "CC_USER");
        headers.put("token", "123");
        headers.put("x-mynt-ctx", xMyntCtx);
        String userId = tokens[3];
        RequestGenerator addAddressRequest = addressServiceHelperV2.addNewAddressSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, headers);
        String resp = addAddressRequest.respvalidate.returnresponseasstring();
        assert addAddressRequest.response.getStatus() == 400;
        assert Objects.equals(resp, "store Id missing");
    }

    @Test(priority = 68, dataProvider = "updateMyntraAddress")
    public void updateMyntraAddressUsingSecureRoute(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid="+MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddressUsingSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressIds.get(0).toString(), headers);
        assert updateAddressReq.response.getStatus() == 200;
        assert addressServiceHelperV2.verifyAddressResponse(updateAddressReq, addressIds.get(0).toString(), "myntra");
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 69, dataProvider = "updateJabongAddress")
    public void updateJabongAddressUsingSecureRoute(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid="+JABONG_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddressUsingSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressIds.get(0).toString(), headers);
        assert updateAddressReq.response.getStatus() == 200;
        assert addressServiceHelperV2.verifyAddressResponse(updateAddressReq, addressIds.get(0).toString(), "jabong");
        addressServiceHelperV2.performSignOutOperation(JABONG_USER, tokens[1]);
    }

    @Test(priority = 70, dataProvider = "updateJabongAddress")
    public void updateJabongAddressUsingSecureRouteWithMyntraStoreId(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid="+MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddressUsingSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressIds.get(0).toString(), headers);
        assert updateAddressReq.response.getStatus() == 200;
        assert addressServiceHelperV2.verifyFailedRequest(updateAddressReq, "Error occurred while inserting/processing data", null);
        addressServiceHelperV2.performSignOutOperation(JABONG_USER, tokens[1]);
    }

    @Test(priority = 71, dataProvider = "updateMyntraAddress")
    public void updateMyntraAddressUsingSecureRouteWithJabongStoreId(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid="+JABONG_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddressUsingSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressIds.get(0).toString(), headers);
        assert updateAddressReq.response.getStatus() == 200;
        assert addressServiceHelperV2.verifyFailedRequest(updateAddressReq, "Error occurred while inserting/processing data", null);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 72, dataProvider = "updateMyntraAddress")
    public void updateAddressUsingSecureRouteWithInvalidStoreId(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=invalid_store_id";
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddressUsingSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressIds.get(0).toString(), headers);
        String resp = updateAddressReq.respvalidate.returnresponseasstring();
        assert updateAddressReq.response.getStatus() == 400;
        assert Objects.equals(resp, STORE_ID_INVALID_ERR_MSG);
    }

    @Test(priority = 73, dataProvider = "updateMyntraAddress")
    public void updateAddressUsingSecureRouteWithEmptyStoreId(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=";
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddressUsingSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressIds.get(0).toString(), headers);
        String resp = updateAddressReq.respvalidate.returnresponseasstring();
        assert updateAddressReq.response.getStatus() == 400;
        assert Objects.equals(resp, "store Id missing");
    }

    @Test(priority = 73, dataProvider = "updateMyntraAddress")
    public void updateAddressUsingSecureRouteWithoutXmyntHeader(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        headers.put("role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddressUsingSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressIds.get(0).toString(), headers);
        String resp = updateAddressReq.respvalidate.returnresponseasstring();
        assert updateAddressReq.response.getStatus() == 400;
        assert Objects.equals(resp, "store Id missing");
    }

    @Test(priority = 74, dataProvider = "updateMyntraAddress")
    public void updateAddressUsingSecureRouteWithInvalidRole(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("role", "INVALID");
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddressUsingSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressIds.get(0).toString(), headers);
        String resp = updateAddressReq.respvalidate.returnresponseasstring();
        assert updateAddressReq.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 75, dataProvider = "updateMyntraAddress")
    public void updateAddressUsingSecureRouteWithInvalidToken(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("role", "CC_USER");
        headers.put("token", "1234");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddressUsingSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressIds.get(0).toString(), headers);
        String resp = updateAddressReq.respvalidate.returnresponseasstring();
        assert updateAddressReq.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 76, dataProvider = "updateMyntraAddress")
    public void updateAddressUsingSecureRouteWithInvalidAddressId(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        String addressId = "98765897";
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddressUsingSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressId, headers);
        assert updateAddressReq.response.getStatus() == 200;
        assert addressServiceHelperV2.verifyFailedRequest(updateAddressReq, "Error occurred while inserting/processing data", null);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 77, dataProvider = "updateMyntraAddress", enabled = false)
    public void updateAddressUsingSecureRouteWithJabongAddressId(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddressUsingSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressIds.get(0).toString(), headers);
        assert updateAddressReq.response.getStatus() == 200;
        assert addressServiceHelperV2.verifyFailedRequest(updateAddressReq, "Error occurred while inserting/processing data", null);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 78, dataProvider = "updateMyntraAddress")
    public void updateAddressUsingSecureRouteWithMyntraAddressId(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + JABONG_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddressUsingSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressIds.get(0).toString(), headers);
        assert updateAddressReq.response.getStatus() == 200;
        assert addressServiceHelperV2.verifyFailedRequest(updateAddressReq, "Error occurred while inserting/processing data", null);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 79, dataProvider = "updateMyntraAddress")
    public void updateAddressUsingSecureRouteWithoutRole(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddressUsingSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressIds.get(0).toString(), headers);
        String resp = updateAddressReq.respvalidate.returnresponseasstring();
        assert updateAddressReq.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 80, dataProvider = "updateMyntraAddress")
    public void updateAddressUsingSecureRouteWithoutToken(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "CC_USER");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        RequestGenerator updateAddressReq = addressServiceHelperV2.updateAddressUsingSecureRoute(address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressIds.get(0).toString(), headers);
        String resp = updateAddressReq.respvalidate.returnresponseasstring();
        assert updateAddressReq.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 81)
    public void fetchMyntraAddress() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddressSecureRoute(userId, headers);
        assert addressServiceHelperV2.checkIfRequestIsSuccessful(fetchAllAddressReq, "FETCHALL");
        assert addressServiceHelperV2.verifyAllFetchedAddress(fetchAllAddressReq, userId, "myntra");
    }

    @Test(priority = 82)
    public void fetchJabongAddress() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + JABONG_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddressSecureRoute(userId, headers);
        assert addressServiceHelperV2.checkIfRequestIsSuccessful(fetchAllAddressReq, "FETCHALL");
        assert addressServiceHelperV2.verifyAllFetchedAddress(fetchAllAddressReq, userId, "jabong");
    }

    @Test(priority = 83)
    public void fetchAddressUsingSecureRouteWithInvalidStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=invalid_store_id";
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddressSecureRoute(userId, headers);
        String resp = fetchAllAddressReq.respvalidate.returnresponseasstring();
        assert fetchAllAddressReq.response.getStatus() == 400;
        assert Objects.equals(resp, STORE_ID_INVALID_ERR_MSG);
    }

    @Test(priority = 84)
    public void fetchAllAddressUsingSecureRouteWithoutPassingStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=";
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddressSecureRoute(userId, headers);
        String resp = fetchAllAddressReq.respvalidate.returnresponseasstring();
        assert fetchAllAddressReq.response.getStatus() == 400;
        assert Objects.equals(resp, "store Id missing");
    }

    @Test(priority = 85)
    public void fetchAllAddressUsingSecureRouteWithInvalidToken() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "CC_USER");
        headers.put("token", "1234");
        String userId = tokens[3];
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddressSecureRoute(userId, headers);
        String resp = fetchAllAddressReq.respvalidate.returnresponseasstring();
        assert fetchAllAddressReq.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 86)
    public void fetchAllAddressUsingSecureRouteWithoutToken() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "CC_USER");
        String userId = tokens[3];
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddressSecureRoute(userId, headers);
        String resp = fetchAllAddressReq.respvalidate.returnresponseasstring();
        assert fetchAllAddressReq.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 87)
    public void fetchAllAddressUsingSecureRouteWithInvalidRole() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "INVALID");
        headers.put("token", "123");
        String userId = tokens[3];
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddressSecureRoute(userId, headers);
        String resp = fetchAllAddressReq.respvalidate.returnresponseasstring();
        assert fetchAllAddressReq.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 88)
    public void fetchAllAddressUsingSecureRouteWithoutRole() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("token", "123");
        String userId = tokens[3];
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddressSecureRoute(userId, headers);
        String resp = fetchAllAddressReq.respvalidate.returnresponseasstring();
        assert fetchAllAddressReq.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 89)
    public void fetchAllAddressUsingSecureRouteWithInvalidRoleAndToken() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("token", "1234");
        headers.put("Role", "INVALID");
        String userId = tokens[3];
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddressSecureRoute(userId, headers);
        String resp = fetchAllAddressReq.respvalidate.returnresponseasstring();
        assert fetchAllAddressReq.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 90)
    public void fetchAllAddressUsingSecureRouteWithoutStoreIdHeader() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        headers.put("token", "1234");
        headers.put("Role", "INVALID");
        String userId = tokens[3];
        RequestGenerator fetchAllAddressReq = addressServiceHelperV2.fetchAllAddressSecureRoute(userId, headers);
        String resp = fetchAllAddressReq.respvalidate.returnresponseasstring();
        assert fetchAllAddressReq.response.getStatus() == 400;
        assert Objects.equals(resp, "store Id missing");
    }

    @Test(priority = 91)
    public void fetchSingleAddressUsingSecureRouteForMyntra() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchSingleAddress = addressServiceHelperV2.fetchSingleAddressSecureRoute(addressId, headers);
        addressServiceHelperV2.checkIfRequestIsSuccessful(fetchSingleAddress, "FETCHALL");
        String resp = fetchSingleAddress.respvalidate.returnresponseasstring();
        assert (addressServiceHelperV2.getAddressIdForUser(userId, "myntra").contains(JsonPath.read(resp, "$.data[0].id")) && JsonPath.read(resp, "$.data[0].id").toString().equals(addressId));
    }

    @Test(priority = 92)
    public void fetchSingleAddressUsingSecureRouteForJabong() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + JABONG_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchSingleAddress = addressServiceHelperV2.fetchSingleAddressSecureRoute(addressId, headers);
        addressServiceHelperV2.checkIfRequestIsSuccessful(fetchSingleAddress, "FETCHALL");
        String resp = fetchSingleAddress.respvalidate.returnresponseasstring();
        assert (addressServiceHelperV2.getAddressIdForUser(userId, "jabong").contains(JsonPath.read(resp, "$.data[0].id")) && JsonPath.read(resp, "$.data[0].id").toString().equals(addressId));
    }

    @Test(priority = 93)
    public void fetchSingleAddressUsingSecureRouteForMyntraByPassingJabongStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + JABONG_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchSingleAddress = addressServiceHelperV2.fetchSingleAddressSecureRoute(addressId, headers);
        assert addressServiceHelperV2.verifyFailedRequest(fetchSingleAddress, "Generic error occurred", null);
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 94, enabled = false)
    public void fetchSingleAddressUsingSecureRouteForJabongByPassingMyntraStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchSingleAddress = addressServiceHelperV2.fetchSingleAddressSecureRoute(addressId, headers);
        String resp = fetchSingleAddress.respvalidate.returnresponseasstring();
        assert JsonPath.read(resp, "$.status.statusMessage").equals("Address not found") || JsonPath.read(resp, "$.status.statusMessage").equals("Error occurred while retrieving/processing data");
        addressServiceHelperV2.performSignOutOperation(JABONG_USER, tokens[1]);
    }

    @Test(priority = 95)
    public void fetchSingleAddressUsingSecureRouteByPassingInvalidStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=invalid_store_id";
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "CC_USER");
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchSingleAddress = addressServiceHelperV2.fetchSingleAddressSecureRoute(addressId, headers);
        assert fetchSingleAddress.respvalidate.returnresponseasstring().equals(STORE_ID_INVALID_ERR_MSG);
        assert fetchSingleAddress.response.getStatus() == 400;
    }

    @Test(priority = 96)
    public void fetchSingleAddressUsingSecureRouteByPassingInvalidRole() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "CCUSER");
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchSingleAddress = addressServiceHelperV2.fetchSingleAddressSecureRoute(addressId, headers);
        String resp = fetchSingleAddress.respvalidate.returnresponseasstring();
        assert fetchSingleAddress.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 97)
    public void fetchSingleAddressUsingSecureRouteByPassingInvalidToken() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "CC_USER");
        headers.put("token", "1234");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchSingleAddress = addressServiceHelperV2.fetchSingleAddressSecureRoute(addressId, headers);
        String resp = fetchSingleAddress.respvalidate.returnresponseasstring();
        assert fetchSingleAddress.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 98)
    public void fetchSingleAddressUsingSecureRouteWithoutToken() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "CC_USER");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchSingleAddress = addressServiceHelperV2.fetchSingleAddressSecureRoute(addressId, headers);
        String resp = fetchSingleAddress.respvalidate.returnresponseasstring();
        assert fetchSingleAddress.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 99)
    public void fetchSingleAddressUsingSecureRouteWithoutRole() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchSingleAddress = addressServiceHelperV2.fetchSingleAddressSecureRoute(addressId, headers);
        String resp = fetchSingleAddress.respvalidate.returnresponseasstring();
        assert fetchSingleAddress.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
    }

    @Test(priority = 100)
    public void fetchSingleAddressUsingSecureRouteWithoutStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        headers.put("token", "123");
        headers.put("Role", "CC_USER");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator fetchSingleAddress = addressServiceHelperV2.fetchSingleAddressSecureRoute(addressId, headers);
        assert fetchSingleAddress.respvalidate.returnresponseasstring().equals("store Id missing");
        assert fetchSingleAddress.response.getStatus() == 400;
    }

    @Test(priority = 101)
    public void deleteSingleAddressUsingSecureRouteForMyntra() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("token", "123");
        headers.put("Role", "CC_USER");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteSingleAddress = addressServiceHelperV2.deleteSingleAddressSecureRoute(addressId, headers);
        assert addressServiceHelperV2.checkIfRequestIsSuccessful(deleteSingleAddress, "DEL");
        assert !addressServiceHelperV2.getAddressIdForUser(userId, "myntra").contains(Integer.valueOf(addressId));
    }

    @Test(priority = 102)
    public void deleteSingleAddressUsingSecureRouteForJabong() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + JABONG_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("token", "123");
        headers.put("Role", "CC_USER");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteSingleAddress = addressServiceHelperV2.deleteSingleAddressSecureRoute(addressId, headers);
        assert addressServiceHelperV2.checkIfRequestIsSuccessful(deleteSingleAddress, "DEL");
        assert !addressServiceHelperV2.getAddressIdForUser(userId, "jabong").contains(Integer.valueOf(addressId));
    }

    @Test(priority = 103)
    public void deleteSingleAddressUsingSecureRouteForMyntraUsingJabongStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + JABONG_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("token", "123");
        headers.put("Role", "CC_USER");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteSingleAddress = addressServiceHelperV2.deleteSingleAddressSecureRoute(addressId, headers);
        assert addressServiceHelperV2.verifyFailedRequest(deleteSingleAddress, "Error deleting Address", null);
        assert addressServiceHelperV2.getAddressIdForUser(userId, "myntra").contains(Integer.valueOf(addressId));
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 104, enabled = false)
    public void deleteSingleAddressUsingSecureRouteForJabongUsingMyntraStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(JABONG_USER, JABONG_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("token", "123");
        headers.put("Role", "CC_USER");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "jabong");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteSingleAddress = addressServiceHelperV2.deleteSingleAddressSecureRoute(addressId, headers);
        assert addressServiceHelperV2.verifyFailedRequest(deleteSingleAddress, "Error deleting Address", null);
        assert addressServiceHelperV2.getAddressIdForUser(userId, "jabong").contains(Integer.valueOf(addressId));
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 105)
    public void deleteSingleAddressUsingSecureRouteWithInvalidStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=invalid_store_id";
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("token", "123");
        headers.put("Role", "CC_USER");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteSingleAddress = addressServiceHelperV2.deleteSingleAddressSecureRoute(addressId, headers);
        assert deleteSingleAddress.respvalidate.returnresponseasstring().equals(STORE_ID_INVALID_ERR_MSG);
        assert deleteSingleAddress.response.getStatus() == 400;
        assert addressServiceHelperV2.getAddressIdForUser(userId, "myntra").contains(Integer.valueOf(addressId));
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 106)
    public void deleteSingleAddressUsingSecureRouteWithoutStoreId() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        headers.put("token", "123");
        headers.put("Role", "CC_USER");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteSingleAddress = addressServiceHelperV2.deleteSingleAddressSecureRoute(addressId, headers);
        assert deleteSingleAddress.respvalidate.returnresponseasstring().equals("store Id missing");
        assert deleteSingleAddress.response.getStatus() == 400;
        assert addressServiceHelperV2.getAddressIdForUser(userId, "myntra").contains(Integer.valueOf(addressId));
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 107)
    public void deleteSingleAddressUsingSecureRouteWithInvalidRole() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("token", "123");
        headers.put("Role", "CCUSER");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteSingleAddress = addressServiceHelperV2.deleteSingleAddressSecureRoute(addressId, headers);
        String resp = deleteSingleAddress.respvalidate.returnresponseasstring();
        assert deleteSingleAddress.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
        assert addressServiceHelperV2.getAddressIdForUser(userId, "myntra").contains(Integer.valueOf(addressId));
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 108)
    public void deleteSingleAddressUsingSecureRouteWithoutRole() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("token", "123");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteSingleAddress = addressServiceHelperV2.deleteSingleAddressSecureRoute(addressId, headers);
        String resp = deleteSingleAddress.respvalidate.returnresponseasstring();
        assert deleteSingleAddress.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
        assert addressServiceHelperV2.getAddressIdForUser(userId, "myntra").contains(Integer.valueOf(addressId));
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 109)
    public void deleteSingleAddressUsingSecureRouteWithInvalidToken() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("token", "1232");
        headers.put("Role", "CC_USER");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteSingleAddress = addressServiceHelperV2.deleteSingleAddressSecureRoute(addressId, headers);
        String resp = deleteSingleAddress.respvalidate.returnresponseasstring();
        assert deleteSingleAddress.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
        assert addressServiceHelperV2.getAddressIdForUser(userId, "myntra").contains(Integer.valueOf(addressId));
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }

    @Test(priority = 110)
    public void deleteSingleAddressUsingSecureRouteWithoutToken() {
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(MYNTRA_USER, MYNTRA_PWD);
        HashMap<String, String > headers = new HashMap<>();
        String xMyntCtx = "storeid=" + MYNTRA_STORE_ID;
        headers.put("x-mynt-ctx", xMyntCtx);
        headers.put("Role", "CC_USER");
        String userId = tokens[3];
        List<Integer> addressIds = addressServiceHelperV2.getAddressIdForUser(userId, "myntra");
        String addressId = addressIds.get(0).toString();
        RequestGenerator deleteSingleAddress = addressServiceHelperV2.deleteSingleAddressSecureRoute(addressId, headers);
        String resp = deleteSingleAddress.respvalidate.returnresponseasstring();
        assert deleteSingleAddress.response.getStatus() == 403;
        assert Objects.equals(JsonPath.read(resp, "$.statusCode"), 10012);
        assert Objects.equals(JsonPath.read(resp, "$.statusMessage"), "Authentication failure for this role");
        assert addressServiceHelperV2.getAddressIdForUser(userId, "myntra").contains(Integer.valueOf(addressId));
        addressServiceHelperV2.performSignOutOperation(MYNTRA_USER, tokens[1]);
    }
}