package com.myntra.apiTests.erpservices.CustomExceptions;

/**
 * Created by 16553 on 17/05/17.
 */
public class StatusError {
    public static void StatusValidator(String expectedStatus,String ActualStatus) throws SCMExceptions {
        throw (new SCMExceptions("The order is not in "+expectedStatus+". It is in "+ActualStatus));

    }

}
