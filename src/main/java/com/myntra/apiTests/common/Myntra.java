package com.myntra.apiTests.common;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.gandalf.Method;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;

/**
 * Created by 300001851 on 02/05/17.
 */
public class Myntra{

    public static MyntraService getService() {
        return new MyntraService();
    }
    public static MyntraService getService(ServiceType portalSearchservice, APINAME getquery, Configuration configurations, String[] strings) {
        return new MyntraService(portalSearchservice.toString(), getquery.toString(), configurations,strings);
    }

    public static MyntraService getService(ServiceType Servicename, APINAME apiundertest, Configuration config, String[] urlparams, String CustomPayload ) {
        return new MyntraService(Servicename.toString(),apiundertest.toString(), config, urlparams,CustomPayload);
    }

    public static MyntraService getService(ServiceType Servicename, Configuration config) {
        return new MyntraService(Servicename.toString(),config);
    }

    public static MyntraService getService(ServiceType Servicename, APINAME apiundertest, Configuration config) {
        return new MyntraService(Servicename.toString(),apiundertest.toString(), config);
    }

    public static MyntraService getService(ServiceType Servicename, APINAME apiundertest, Configuration config, PayloadType PayloaddataFormat, PayloadType ResponseDataFormat) {
        return new MyntraService(Servicename.toString(),apiundertest.toString(), config, PayloaddataFormat, ResponseDataFormat);
    }

    public static MyntraService getService(ServiceType Servicename, APINAME apiundertest, Configuration config, PayloadType PayloaddataFormat, String custom) {
        return new MyntraService(Servicename.toString(),apiundertest.toString(), config, PayloaddataFormat, custom);
    }

    public static MyntraService getService(String apiName, Method requestSendMethod, boolean payloadReq, boolean queryParamReq) {
        return new MyntraService(apiName,requestSendMethod, payloadReq,queryParamReq);
    }

    public static MyntraService getService(ServiceType Servicename, APINAME apiundertest, Configuration config, String CustomPayload ) {
        return new MyntraService(Servicename.toString(),apiundertest.toString(), config,CustomPayload);
    }

    //public static MyntraService getService(ServiceType servicename2, ServiceType Servicename, APINAME apiundertest, Configuration config, String[] PayloadParams) {
    //    return new MyntraService(servicename2, Servicename.toString(),apiundertest.toString(), config, PayloadParams);
    //}

    //public static MyntraService getService(String Servicename, APINAME apiundertest, Configuration config, String[] PayloadParams) {
    //    return new MyntraService(Servicename.toString(),apiundertest.toString(), config, PayloadParams);
    //}

    public static MyntraService getService(ServiceType Servicename, APINAME apiundertest, Configuration config, String[] PayloadParams, String[] urlparams) {
        return new MyntraService(Servicename.toString(),apiundertest.toString(), config, PayloadParams,urlparams);
    }

    public static MyntraService getService(ServiceType Servicename, APINAME apiundertest, Configuration config, String[] PayloadParams, String[] urlparams, PayloadType PayloadDataFormat, PayloadType ReponseAcceptDataFormat) {
        return new MyntraService(Servicename.toString(),apiundertest.toString(), config, PayloadParams,urlparams, PayloadDataFormat, ReponseAcceptDataFormat);
    }

    public static MyntraService getService(ServiceType Servicename, APINAME apiundertest, Configuration config, String[] PayloadParams, PayloadType PayloadDataFormat, PayloadType ReponseAcceptDataFormat) {
        return new MyntraService(Servicename.toString(),apiundertest.toString(), config, PayloadParams,PayloadDataFormat, ReponseAcceptDataFormat);
    }

    public static MyntraService getService(ServiceType Servicename, APINAME apiundertest, Configuration config, String[] PayloadParams, String fileExtension , PayloadType PayloadDataFormat, PayloadType ReponseAcceptDataFormat) {
        return new MyntraService(Servicename.toString(),apiundertest.toString(), config, PayloadParams,fileExtension, PayloadDataFormat, ReponseAcceptDataFormat);
    }

    public static MyntraService getService(ServiceType Servicename, APINAME apiundertest, Configuration config, String[] PayloadParams, PayloadType PayloadDataFormat, PayloadType ReponseAcceptDataFormat, String subPayload) {
        return new MyntraService(Servicename.toString(),apiundertest.toString(), config, PayloadParams,PayloadDataFormat, ReponseAcceptDataFormat, subPayload);
    }

    public static MyntraService getService(ServiceType Servicename, APINAME apiundertest, Configuration config, PayloadType PayloadDataFormat, String[] urlparams, PayloadType ReponseAcceptDataFormat) {
        return new MyntraService(Servicename.toString(),apiundertest.toString(), config, PayloadDataFormat, urlparams, ReponseAcceptDataFormat);
    }
}
