package com.myntra.apiTests.portalservices.addressServiceV2;

import com.myntra.lordoftherings.Configuration;

public interface AddressConstants {

    Configuration CONFIGURATION = new Configuration("./Data/configuration");

    String Prod_ENV ="Prod";
    String Stage_ENV = "stage";

    String CONCURRENCY_USER = "concurrency.user@myntra.com";
    String CONCURRENCY_PWD = "123456";

    String MYNTRA_USER = "address.user1@myntra.com";
    String MYNTRA_PWD = "kT3Yc&RL";

    String JABONG_USER = "jabong.user@myntra.com";
    String JABONG_PWD = "123456";

    String ANOTHER_USER_EMAIL = "automationtest@myntra.com";
    String ANOTHER_USER_PASS = "123456";

    int ADDR_RETALL_STATUS_CODE= 3;
    String ADDR_RETALL_STATUS_MSG="Success";

    int ADDR_RET_STATUS_CODE= 15002;
    String ADDR_RET_STATUS_MSG="Address(s) retrieved successfully";

    int ADDR_NF_STATUS_CODE= 10008;
    String ADDR_NF_STATUS_MSG="Address not found";

    int ADDR_ADD_STATUS_CODE= 15001;
    String ADDR_ADD_STATUS_MSG="Address added successfully";

    int ADDR_UPD_STATUS_CODE= 15003;
    String ADDR_UPD_STATUS_MSG="Address updated successfully";

    int ADDR_DEL_STATUS_CODE= 15004;
    String ADDR_DEL_STATUS_MSG="Address deleted successfully";

    int ERROR_STATUS_CODE = 56;

    String MYNTRA_STORE_ID = "2297";
    String JABONG_STORE_ID = "4603";

    String STORE_ID_MISSING_ERR_MSG = "store Id missing";
    String STORE_ID_INVALID_ERR_MSG = "invalid store id";
    String NIDX_MISSING_ERR_MSG = "nidx missing";
    String UIDX_MISSING_ERR_MEG = "uidx missing";

}
