package com.myntra.apiTests.portalservices.addressServiceV2;

import com.google.common.base.Strings;
import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.portalservices.absolutService.AbsolutNodes;
import com.myntra.apiTests.portalservices.commons.StatusNodes;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;

import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;

import javax.ws.rs.core.MultivaluedMap;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AddressServiceHelperV2 implements AddressConstants {

    static Initialize init = new Initialize("/Data/configuration");
    static Logger log = Logger.getLogger(AddressServiceHelperV2.class);
    APIUtilities apiUtilities = new APIUtilities();

    public String[] loginAndGetMyntraTokens(String email, String password) {
        String xID = "", sXid ="", statusMessage ="";
        log.info("Logging in with email :" + email + " and password: "+ password);
        String[] payload = new String[] {email, password};
        MyntraService idpService = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.SIGNIN, init.Configurations, payload);
        log.info("IDP Service API URL : " + idpService.URL);

        log.info("IDP Service API Payload : " + idpService.Payload);

        RequestGenerator signInRequest = new RequestGenerator(idpService);
        log.info("Printing IDP Service API response : "+ signInRequest.respvalidate.returnresponseasstring());

        MultivaluedMap<String, Object> map = signInRequest.response.getHeaders();
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getKey().toString().equalsIgnoreCase("xid"))
                xID = entry.getValue().toString();
        }
        log.info("xID is : " + xID);
        xID = xID.substring((xID.indexOf("[") + 1), xID.lastIndexOf("]"));
        sXid = signInRequest.respvalidate.GetNodeTextUsingIndex("xsrfToken");
        String statusResponse = signInRequest.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true);
        statusMessage = statusResponse.split("\"")[1];
        log.info("\nPrinting sXid from Response  : " + sXid);

        if (sXid.contains("'"))
            sXid = sXid.substring(sXid.indexOf("'") + 1, sXid.lastIndexOf("'"));
        else
            sXid = sXid.substring(sXid.indexOf("[") + 1, sXid.lastIndexOf("]"));

        log.info("Final xID : " + xID);
        log.info("Final sxid : " + sXid);
        String userId = JsonPath.read(signInRequest.respvalidate.returnresponseasstring(), "$.user.login");
        log.info("IDP Service response : " + signInRequest.response);
        String[] tokens = { xID, sXid, statusMessage, userId};
        return tokens;
    }

    public RequestGenerator performSignOutOperation(String userName, String xsrfToken) {
        MyntraService signOutService = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.SIGNOUT, init.Configurations, new String[]{ userName });
        String token = "X-CSRF-TOKEN="+xsrfToken;
        System.out.println("token value is:"+token);
        signOutService.URL = signOutService.URL+"?"+token;

        System.out.println("\nPrinting IDPService SignOut API URL : "+signOutService.URL);
        log.info("\nPrinting IDPService SignOut API URL : "+signOutService.URL);

        System.out.println("\nPrinting IDP Service SignOut API Payload : \n\n"+signOutService.Payload);
        log.info("\nPrinting IDP Service SignOut API Payload : \n\n"+signOutService.Payload);

        return new RequestGenerator(signOutService);
    }


    public String[] getJabongTokens() {
        //TODO(Piyush): write implementation for getting jabong user xid and other required details.
        String[] tokens = new String[3];
        return tokens;
    }

    public RequestGenerator addNewAddress(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile, String userId, HashMap<String, String > headers) {
        String[] payloadParams = new String[] {address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId};
        MyntraService addressServiceV2 = Myntra.getService(ServiceType.PORTAL_ADDRESS, APINAME.ADDNEWADDRESSV2, init.Configurations, payloadParams, PayloadType.JSON, PayloadType.JSON);
        log.info("Address service URL " + addressServiceV2.URL);
        log.info("Address service payload " + addressServiceV2.Payload);

        RequestGenerator addNewAddressReq = new RequestGenerator(addressServiceV2, headers);
        log.info("New address addition response : " + addNewAddressReq.respvalidate.returnresponseasstring());
        return addNewAddressReq;
    }

    public RequestGenerator addNewAddressSecureRoute(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile, String userId, HashMap<String, String > headers) {
        String[] payloadParams = new String[] {address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId};
        MyntraService addressServiceV2 = Myntra.getService(ServiceType.PORTAL_ADDRESS, APINAME.CREATENEWADDRESSV2, init.Configurations, payloadParams, PayloadType.JSON, PayloadType.JSON);
        log.info("Address service URL " + addressServiceV2.URL);
        log.info("Address service payload " + addressServiceV2.Payload);

        RequestGenerator addNewAddressReq = new RequestGenerator(addressServiceV2, headers);
        log.info("New address addition response : " + addNewAddressReq.respvalidate.returnresponseasstring());
        return addNewAddressReq;
    }

    public RequestGenerator updateAddress(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile, String userId, String addressId, HashMap<String, String > headers) {
        String[] payloadParams = new String[] {address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressId};
        MyntraService addressServiceV2 = Myntra.getService(ServiceType.PORTAL_ADDRESS, APINAME.UPDATEADDRESSV2, init.Configurations, payloadParams, PayloadType.JSON, PayloadType.JSON);
        addressServiceV2.URL = apiUtilities.prepareparameterizedURL(addressServiceV2.URL, addressId);
        log.info("Address service URL " + addressServiceV2.URL);
        log.info("Address service payload " + addressServiceV2.Payload);

        RequestGenerator updateAddressReq = new RequestGenerator(addressServiceV2, headers);
        log.info("Update address response : " + updateAddressReq.respvalidate.returnresponseasstring());
        return updateAddressReq;
    }

    public RequestGenerator updateAddressUsingSecureRoute(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile, String userId, String addressId, HashMap<String, String > headers) {
        String[] payloadParams = new String[] {address, city, countryCode, defaultAddress, email, locality, name, pincode, stateCode, stateName, mobile, userId, addressId};
        MyntraService addressServiceV2 = Myntra.getService(ServiceType.PORTAL_ADDRESS, APINAME.EDITADDRESSV2, init.Configurations, payloadParams, PayloadType.JSON, PayloadType.JSON);
        addressServiceV2.URL = apiUtilities.prepareparameterizedURL(addressServiceV2.URL, addressId);
        log.info("Address service URL " + addressServiceV2.URL);
        log.info("Address service payload " + addressServiceV2.Payload);

        RequestGenerator updateAddressReq = new RequestGenerator(addressServiceV2, headers);
        log.info("Update address response : " + updateAddressReq.respvalidate.returnresponseasstring());
        return updateAddressReq;
    }

    public RequestGenerator deleteAddress(String addressId, HashMap<String, String > headers) {
        MyntraService addressServiceV2 = Myntra.getService(ServiceType.PORTAL_ADDRESS, APINAME.DELETEADDRESSV2, init.Configurations);
        addressServiceV2.URL = apiUtilities.prepareparameterizedURL(addressServiceV2.URL, addressId);
        log.info("Address service URL : " + addressServiceV2.URL);


        RequestGenerator updateAddressReq = new RequestGenerator(addressServiceV2, headers);
        log.info("Delete address response : " + updateAddressReq.respvalidate.returnresponseasstring());
        return updateAddressReq;
    }

    public RequestGenerator fetchAllAddress(HashMap<String, String > headers) {
        MyntraService addressServiceV2 = Myntra.getService(ServiceType.PORTAL_ADDRESS, APINAME.GETALLADDRESSV2, init.Configurations);
        log.info("Address service URL : " + addressServiceV2.URL);
        RequestGenerator fetchAllAddress = new RequestGenerator(addressServiceV2, headers);
        log.info("Get all address response : " + fetchAllAddress.respvalidate.returnresponseasstring());
        return fetchAllAddress;
    }

    public RequestGenerator fetchSingleAddress(String addressId, HashMap<String, String > headers) {
        MyntraService addressServiceV2 = Myntra.getService(ServiceType.PORTAL_ADDRESS, APINAME.FETCHSINGLEADDRESSV2, init.Configurations);
        addressServiceV2.URL = apiUtilities.prepareparameterizedURL(addressServiceV2.URL, addressId);
        log.info("Address service URL : " + addressServiceV2.URL);
        RequestGenerator fetchSingleAddress = new RequestGenerator(addressServiceV2, headers);
        log.info("Get single address response : " + fetchSingleAddress.respvalidate.returnresponseasstring());
        return fetchSingleAddress;
    }

    public RequestGenerator fetchAllAddressSecureRoute(String userId, HashMap<String, String > headers) {
        MyntraService addressServiceV2 = Myntra.getService(ServiceType.PORTAL_ADDRESS, APINAME.GETALLADDRESSFORCCV2, init.Configurations);
        addressServiceV2.URL = apiUtilities.prepareparameterizedURL(addressServiceV2.URL, userId);
        log.info("Address service URL : " + addressServiceV2.URL);
        RequestGenerator fetchSingleAddress = new RequestGenerator(addressServiceV2, headers);
        log.info("Get all address response : " + fetchSingleAddress.respvalidate.returnresponseasstring());
        return fetchSingleAddress;
    }

    public RequestGenerator fetchSingleAddressSecureRoute(String addressId, HashMap<String, String > headers) {
        MyntraService addressServiceV2 = Myntra.getService(ServiceType.PORTAL_ADDRESS, APINAME.GETSINGLEADDRESSV2, init.Configurations);
        addressServiceV2.URL = apiUtilities.prepareparameterizedURL(addressServiceV2.URL, addressId);
        log.info("Address service URL : " + addressServiceV2.URL);
        RequestGenerator fetchSingleAddress = new RequestGenerator(addressServiceV2, headers);
        log.info("Get all address response : " + fetchSingleAddress.respvalidate.returnresponseasstring());
        return fetchSingleAddress;
    }

    public RequestGenerator deleteSingleAddressSecureRoute(String addressId, HashMap<String, String > headers) {
        MyntraService addressServiceV2 = Myntra.getService(ServiceType.PORTAL_ADDRESS, APINAME.DELETESINGLEADDRESSV2, init.Configurations);
        addressServiceV2.URL = apiUtilities.prepareparameterizedURL(addressServiceV2.URL, addressId);
        log.info("Address service URL : " + addressServiceV2.URL);
        RequestGenerator fetchSingleAddress = new RequestGenerator(addressServiceV2, headers);
        log.info("Get all address response : " + fetchSingleAddress.respvalidate.returnresponseasstring());
        return fetchSingleAddress;
    }

    public boolean verifyAddressResponse(RequestGenerator response, String addressId, String dataSource) {
        String resp = response.respvalidate.returnresponseasstring();
        if (Strings.isNullOrEmpty(addressId)) {
            addressId = JsonPath.read(resp, "$.data[0].id").toString();
        }
        HashMap<String, Object> resultSet = (HashMap<String, Object>) getAddressFromDb(addressId, dataSource);
        Assert.assertTrue(Objects.equals(resultSet.get("login"), JsonPath.read(resp, "$.data[0].userId")), "login id mismatch for the user.");
        Assert.assertTrue(Objects.equals(resultSet.get("name"), JsonPath.read(resp, "$.data[0].name")), "name mismatch for the user.");
        Assert.assertTrue(Objects.equals(resultSet.get("address"), JsonPath.read(resp, "$.data[0].address")), "address mismatch for the user.");
        Assert.assertTrue(Objects.equals(resultSet.get("city"), JsonPath.read(resp, "$.data[0].city")), "city mismatch for the user.");
        Assert.assertTrue(Objects.equals(resultSet.get("state"), JsonPath.read(resp, "$.data[0].stateCode")), "state mismatch for the user.");
        Assert.assertTrue(Objects.equals(resultSet.get("country"), JsonPath.read(resp, "$.data[0].countryCode")), "country mismatch for the user.");
        Assert.assertTrue(Objects.equals(resultSet.get("pincode"), JsonPath.read(resp, "$.data[0].pincode")), "pincode mismatch for the user.");
        Assert.assertTrue(Objects.equals(resultSet.get("email"), JsonPath.read(resp, "$.data[0].email")), "email mismatch for the user.");
        Assert.assertTrue(Objects.equals(resultSet.get("mobile"), JsonPath.read(resp, "$.data[0].mobile")), "mobile mismatch for the user.");
        Assert.assertTrue(Objects.equals(resultSet.get("locality"), JsonPath.read(resp, "$.data[0].locality")), "locality mismatch for the user.");
        return true;
    }

    public Map<String,Object> getAddressFromDb(String addressId, String dataSource) {
        String query = "select `id`, `login`, `default_address`, `name`, `address`, `city`, `state`, `country`, `pincode`, `email`, `mobile`, `phone`, `datecreated`, `locality`, `address_type` from `mk_customer_address` where `id` = "+ addressId;
        return DBUtilities.exSelectQueryForSingleRecord(query, dataSource + "_address");
    }

    public boolean checkIfRequestIsSuccessful(RequestGenerator response, String action) {
        log.info(response.respvalidate.returnresponseasstring());
        Assert.assertTrue(response.response.getStatus() == 200);
        String resp = response.respvalidate.returnresponseasstring();
        switch (action) {
            case "ADD" :
                Assert.assertTrue(Objects.equals(JsonPath.read(resp, "$.status.statusCode"), ADDR_ADD_STATUS_CODE));
                Assert.assertTrue(JsonPath.read(resp, "$.status.statusMessage").equals(ADDR_ADD_STATUS_MSG));
                break;
            case "UPDATE" :
                Assert.assertTrue(Objects.equals(JsonPath.read(resp, "$.status.statusCode"), ADDR_UPD_STATUS_CODE));
                Assert.assertTrue(JsonPath.read(resp, "$.status.statusMessage").equals(ADDR_UPD_STATUS_MSG));
                break;
            case "DEL" :
                Assert.assertTrue(Objects.equals(JsonPath.read(resp, "$.status.statusCode"), ADDR_DEL_STATUS_CODE));
                Assert.assertTrue(JsonPath.read(resp, "$.status.statusMessage").equals(ADDR_DEL_STATUS_MSG));
                break;
            case "NF" :
                Assert.assertTrue(Objects.equals(JsonPath.read(resp, "$.status.statusCode"), ADDR_NF_STATUS_CODE));
                Assert.assertTrue(JsonPath.read(resp, "$.status.statusMessage").equals(ADDR_NF_STATUS_MSG));
                break;
            case "FETCH" :
                Assert.assertTrue(Objects.equals(JsonPath.read(resp, "$.status.statusCode"), ADDR_RET_STATUS_CODE));
                Assert.assertTrue(JsonPath.read(resp, "$.status.statusMessage").equals(ADDR_RET_STATUS_MSG));
                break;
            case "FETCHALL" :
                Assert.assertTrue(Objects.equals(JsonPath.read(resp, "$.status.statusCode"), ADDR_RETALL_STATUS_CODE));
                Assert.assertTrue(JsonPath.read(resp, "$.status.statusMessage").equals(ADDR_RETALL_STATUS_MSG));
                break;
        }
        return true;
    }

    public List<Integer> getAddressIdForUser(String userId, String dataSource) {
        String query = "select `id` from `mk_customer_address` where `login` = '" + userId + "' order by id desc";
        List<HashMap<String, Object>> rs = DBUtilities.exSelectQuery(query, dataSource+ "_address");
        List<Integer> addressIds = new ArrayList<>();
        rs.forEach(resultSet -> {
            resultSet.values().stream().forEach(val -> addressIds.add(Integer.valueOf(val.toString())) );
        });
        return addressIds;
    }

    public boolean verifyFailedRequest(RequestGenerator response, String message, String xsrfToken) {
        String resp = response.respvalidate.returnresponseasstring();
        boolean flag = JsonPath.read(resp, "$.status.statusMessage").toString().contains(message);
        Assert.assertTrue(Objects.equals(JsonPath.read(resp, "$.status.statusType"), "ERROR"));
        if (!Strings.isNullOrEmpty(xsrfToken)) {
            Assert.assertTrue(Objects.equals(JsonPath.read(resp, "$.xsrfToken"), xsrfToken));
        }
        return flag;
    }

    public boolean verifyAllFetchedAddress(RequestGenerator requestGenerator, String userId, String dataSource) {
        String resp = requestGenerator.respvalidate.returnresponseasstring();
        List<Integer> addressIds = JsonPath.read(resp, "$.data..id");
        List<Integer> dbAddressIds = getAddressIdForUser(userId, dataSource);
        if (dbAddressIds.size() > 100 && addressIds.size() == 100) {
            return true;
        }
        return addressIds.containsAll(dbAddressIds);
    }
    
    public String getAddressSecuredAPI(String uidx) throws UnsupportedEncodingException, InterruptedException {
    		Thread.sleep(4000);
		String baseURL = Constants.ATS_PATH.GET_ALLADDRESS + uidx;
		Svc service = HttpExecutorService.executeHttpService(baseURL, new String[] {""},
				SERVICE_TYPE.ADDRESS_SVC.toString(), HTTPMethods.GET, null, getAddressConsumerHeaders(uidx));
		return service.getResponseBody();
	}
    
	private HashMap<String, String> getAddressConsumerHeaders(String uidx) {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "Application/json");
		headers.put("Accept", "Application/json");
		headers.put("token", "123");
		headers.put("role", "CC_USER");
		String xMyntCtx = "storeid=4603";
		headers.put("x-mynt-ctx", xMyntCtx);
		return headers;
	}

}
