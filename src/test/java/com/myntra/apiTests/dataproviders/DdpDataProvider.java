package com.myntra.apiTests.dataproviders;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.legolas.Commons;
import org.testng.annotations.DataProvider;

/**
 * Created by swatantra singh on 01/12/16.
 */
public class DdpDataProvider {
    String Env;
    Configuration con = new Configuration("./Data/configuration");
    Commons commonUtil = new Commons();

    @DataProvider()

    public static Object[][] DownloadResultset() {


        return new Object[][] { new String[]{"https","swatantra.singh"}};

    }
    @DataProvider()

    public static Object[][] submitQuery() {


        return new Object[][] { new String[]{"my_query","swat","select%20*%20from%20dim_date%20limit%2010","false","0","http://54.169.211.238/executor-api/rest/v2/queryExecutor"}};

    }

}
