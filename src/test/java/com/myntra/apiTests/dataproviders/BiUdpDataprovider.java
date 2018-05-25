package com.myntra.apiTests.dataproviders;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.legolas.Commons;
import org.testng.annotations.DataProvider;

/**
 * Created by 17787 on 02/12/16.
 */
public class BiUdpDataprovider {
    String Env;
    Configuration con = new Configuration("./Data/configuration");
    Commons commonUtil = new Commons();

    @DataProvider()

    public static Object[][] GetMetrics() {


        return new Object[][] { new String[]{"rto_initiated_perc:DESC,rto_restocked_perc:DESC","realised_rgmα1000αGREATER_THAN","brand","brandαPumaµAdidasαINαONLY_SHOW","AND","1454137181000","1480489181000","10","true","date"}};

    }
    @DataProvider()

    public static Object[][] GetMetricsBasedOnDateRange() {


        return new Object[][] { new String[]{"rto_initiated_perc:DESC,rto_restocked_perc:DESC","realised_rgmα1000αGREATER_THAN","brand","brandαPumaµAdidasαINαONLY_SHOW","AND","1454137181000:1480489181000","1456894202000:1480489181000","10","true","date","0"}};

    }
}
