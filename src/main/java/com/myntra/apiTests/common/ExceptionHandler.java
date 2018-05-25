package com.myntra.apiTests.common;

import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.exception.ManagerException;

/**
 * Created by abhijit.pati on 18/05/17.
 */
public class ExceptionHandler {

    public static void handleError(StatusResponse statusResponse) throws ManagerException {
        if(!statusResponse.getStatusType().equals(StatusResponse.Type.SUCCESS))
            throw new ManagerException(statusResponse.getStatusMessage(), statusResponse.getStatusCode());
    }

    public static void handleError(StatusResponse statusResponse, String message) throws ManagerException {
        if(!statusResponse.getStatusType().equals(StatusResponse.Type.SUCCESS))
            throw new ManagerException(statusResponse.getStatusMessage()+". "+message, statusResponse.getStatusCode());
    }

    public static void handleNotNull(Object object, String message) throws ManagerException {
        if(object == null)
            throw new ManagerException(message, 2001);
    }

    public static void handleNotNull(Object object) throws ManagerException {
        if(object == null)
            throw new ManagerException("Object is null " ,2001);
    }

    public static void handleTrue(boolean value) throws ManagerException {
        if(value == false)
            throw new ManagerException("Expected true but found false", 2001);
    }

    public static void handleTrue(boolean value, String message) throws ManagerException {
        if(value == false)
            throw new ManagerException(message, 2001);
    }

    public static void handleFalse(boolean value) throws ManagerException {
        if(value == true)
            throw new ManagerException("Expected false but found true", 2001);
    }

    public static void handleFalse(boolean value, String message) throws ManagerException {
        if(value == true)
            throw new ManagerException(message, 2001);
    }

    public static void handleEquals( Object actual, Object expected) throws ManagerException {
        if(!expected.equals(actual))
            throw new ManagerException("Expected: "+expected+" but Actual: "+actual, 2001);
    }

    public static void handleEquals(Object actual, Object expected, String message) throws ManagerException {
        if(!expected.equals(actual))
            throw new ManagerException("Expected: "+expected+" but Actual: "+actual+" , "+message, 2001);
    }

    public static void fail(String message) throws ManagerException {
        throw new ManagerException(message, 2001);
    }

}
