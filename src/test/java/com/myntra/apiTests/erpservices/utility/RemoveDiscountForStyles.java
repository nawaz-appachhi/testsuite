package com.myntra.apiTests.erpservices.utility;

import com.myntra.apiTests.portalservices.pricingpromotionservices.helper.PnPServiceHelper;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.test.commons.testbase.BaseTest;

import org.codehaus.jettison.json.JSONException;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;

/**
 * @author abhijit.pati
 * @since 31/07/16
 */
public class RemoveDiscountForStyles extends BaseTest{

	PnPServiceHelper pnpServiceHelper = new PnPServiceHelper();
	StyleServiceApiHelper styleServiceApiHelper = new StyleServiceApiHelper();
	
    @Test(enabled=true,groups = {"Regression", "MiniRegression",
            "ExhaustiveRegression", "PPS"}, dataProvider = "dp", priority = 0)
    public void removeStyleDiscount(String style) throws IOException, JSONException {
		pnpServiceHelper.removeDiscount(style);
        pnpServiceHelper.removeDiscount(style);
    }

    @Test(enabled=true,groups = {"Regression", "MiniRegression",
            "ExhaustiveRegression", "PPS"}, dataProvider = "dp", priority = 1)
    public void reindexAllTheStyle(String style) throws IOException, JSONException {
        styleServiceApiHelper.styleReindexForStyleIDs(style);
        styleServiceApiHelper.styleReindexForStyleIDs(style);
    }

    @DataProvider(parallel = true)
    public static Object[][] dp(ITestContext testContext) throws SQLException, IOException, JAXBException, JSONException {

        Object[] arr1 = { "1530" };
        Object[] arr2 = { "1531" };
        Object[] arr3 = { "1532" };
        Object[] arr4 = { "1533" };
        Object[] arr5 = { "1534" };
        Object[] arr6 = { "1541" };
        Object[] arr7 = { "1542" };
        Object[] arr8 = { "1543" };
        Object[] arr9 = { "1550" };
        Object[] arr10 = { "1552" };
        Object[] arr11 = { "1553" };
        Object[] arr12 = { "1554" };
        Object[] arr13 = { "1555" };
        Object[] arr14 = { "1556" };
        Object[] arr15 = { "1557" };

        Object[][] dataSet = new Object[][] { arr1, arr2,arr3, arr4,arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), dataSet.length, dataSet.length);
    }

}
