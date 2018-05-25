package com.myntra.apiTests.erpservices.lms.lmsClient;

import com.myntra.apiTests.erpservices.lms.lmsClient.Status;

/**
 * Created by Shubham Gupta on 3/20/17.
 */
public class ServiceabilityUtilResponse {
    private com.myntra.apiTests.erpservices.lms.lmsClient.Status Status;

    public Status getStatus() {
        return Status;
    }

    public void setStatus(Status status) {
        Status = status;
    }
}
