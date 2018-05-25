package com.myntra.apiTests.erpservices.munshi;

import org.testng.annotations.Test;

/**
 * Created by 16553 on 18/05/17.
 */
public class ServiceAPIs {

    @Test(description="This API only generates document Info. It does not generate document.",dataProviderClass = ServiceAPIsDP.class,dataProvider = "generateInfoDP")
    public void generateInfoAPI(){


    }
}
