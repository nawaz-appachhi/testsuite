package com.myntra.apiTests.portalservices.all;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.couponServicesSchemaVAlidationDP;
import com.myntra.apiTests.portalservices.idpservice.IDPServiceHelper;
import com.myntra.lordoftherings.*;

import com.myntra.lordoftherings.gandalf.PayloadType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Statement;
import java.util.*;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import argo.saj.InvalidSyntaxException;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.MyntraListenerV2;

import javax.ws.rs.core.MultivaluedMap;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;

/**
 * Created by swatantra singh on 28/09/16.
 */
public class couponServicesSchemaValidation extends couponServicesSchemaVAlidationDP {
    static Initialize init = new Initialize("/Data/configuration");
    static Logger log = Logger.getLogger(CouponServiceTests.class);
    ArrayList Expire1 = new ArrayList();

    APIUtilities apiUtil = new APIUtilities();
    public long currentTime = System.currentTimeMillis() / 1000;
    String startTime = "", endTime = "";
    String xID, sXid;
    static IDPServiceHelper idpServiceHelper = new IDPServiceHelper();
    MyntraListenerV2 myntListner = new MyntraListenerV2();
    int reval;
    Double d1, d2;

    @Test(groups = {"Smoke", "Sanity", "Fox7Sanity","Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "getFestivalCouponsForOrderDP")
    public void CouponService_getFestivalCouponsForOrderSchemaValidator(String orderId) {
        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.FESTIVALCOUPONSFORORDER, init.Configurations, null, new String[]{orderId});
        System.out.println(service.URL);
        String jsonschema = null;
        List<String> missingNodeList = null;
        RequestGenerator req = new RequestGenerator(service);
        String response = req.respvalidate.returnresponseasstring();
        try {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_getFestivalCoupon-SchemaSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);

        } catch (Exception e) {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in getFestivalCouponsForOrder API response", CollectionUtils.isEmpty(missingNodeList));


    }

    @Test(groups = {"Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"}, dataProvider = "couponUsageHistoryDP")
    public void CouponService_couponUsageHistorySchemaValidator(String userId) throws NumberFormatException, InvalidSyntaxException {
        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.COUPONUSAGEHISTORY, init.Configurations, null, new String[]{userId});
        System.out.println(service.URL);
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList<String>();
        RequestGenerator req = new RequestGenerator(service);
        String response = req.respvalidate.returnresponseasstring();

        try {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/couponService_HistoryUsageSchemaValidatorSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in couponUsageHistory API response", CollectionUtils.isEmpty(missingNodeList));

    }

    @Test(groups = {"Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"}, dataProvider = "getCouponsAllActiveExpiredDP")
    public void CouponService_getCouponsAllExpiredSchemaValidation(String userId, String status) {
        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETCOUPONSALLEXPIREDACTIVE, init.Configurations);
        service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[]{userId, status});
        System.out.println(service.URL);
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        RequestGenerator req = new RequestGenerator(service);
        String response = req.respvalidate.returnresponseasstring();
        try {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/couponService_getCouponsAllActiveExpiredSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in getCouponsAllExpired API response", CollectionUtils.isEmpty(missingNodeList));

    }

    @Test
            (groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Fox7Sanity", "Regression", "Fox7Sanity"}, dataProvider = "getStyleidForCoupon", priority = 1)
    public void CouponService_clickForOfferSchemaValidation(String styleid) {
        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.CLICKFOROFFER, init.Configurations);
        service.URL = apiUtil.prepareparameterizedURL(service.URL, styleid);
        RequestGenerator rs = new RequestGenerator(service);
        String response = rs.returnresponseasstring();
        System.out.println(response);
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_clickForOfferSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in clickForOffer API response", CollectionUtils.isEmpty(missingNodeList));

    }

    @Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression", "Fox7Sanity"}, dataProvider = "getStyleidForCoupon", priority = 1)
    public void CouponService_clickForOfferPercentageCouponWithLoginSchemaValidation(String styleid) {
        startTime = String.valueOf(currentTime);
        modifyPercentageCoupon(startTime, startTime, "couponTesting", "90", "coupontesting@myntra.com");
        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.CLICKFOROFFER, init.Configurations);
        service.URL = apiUtil.prepareparameterizedURL(service.URL, styleid);
        System.out.println("service URL-" + service.URL);
        HashMap getParam = new HashMap();
        getParam.put("login",
                "coupontesting@myntra.com");
        RequestGenerator req = new RequestGenerator(service, getParam);
        String response = req.respvalidate.returnresponseasstring();
        System.out.println("heyswat" + response + "endhelo");
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();

        try {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_clickForOfferPercentageCouponWithLoginSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in click For Offer Percentage Coupon With Login API response", CollectionUtils.isEmpty(missingNodeList));


    }
    @Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression", "Fox7Sanity"}, dataProvider="getStyleidForCoupon", priority=1)
    public void CouponService_clickForOfferDualCouponWithLoginSchemaValidation(String styleid) {
        startTime = String.valueOf(currentTime);

        modifydualCoupon(startTime, startTime, "couponTesting", "90", "3000", "coupontesting@myntra.com");
        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.CLICKFOROFFER, init.Configurations);
        service.URL = apiUtil.prepareparameterizedURL(service.URL, styleid);

        HashMap getParam = new HashMap();
        getParam.put("login",
                "coupontesting@myntra.com");
        RequestGenerator req = new RequestGenerator(service, getParam);
        String response = req.respvalidate.returnresponseasstring();
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();

        try {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_clickForOfferDualCouponWithLoginSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in click For Offer Dual Coupon With Login API response", CollectionUtils.isEmpty(missingNodeList));


    }
    @Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression", "Fox7Sanity"}, dataProvider="getStyleidForCoupon", priority=1)
    public void CouponService_clickForOfferDualPercentageCouponWithLoginSchemaValidation(String styleid)
    {
        startTime = String.valueOf(currentTime);
        modifyPercentageDualCoupon(startTime, startTime, "couponTesting", "89", "90", "coupontesting@myntra.com");
        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.CLICKFOROFFER, init.Configurations);
        service.URL = apiUtil.prepareparameterizedURL(service.URL, styleid);
        System.out.println("------------->>>>>>>>>>>> url " + service.URL);
        HashMap getParam = new HashMap();
        getParam.put("login","coupontesting@myntra.com");
        RequestGenerator req = new RequestGenerator(service, getParam);
        String response = req.respvalidate.returnresponseasstring();
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();

        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_clickForOfferDualPercentageCouponWithLoginSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in click For Offer Dual Percentage Coupon With Login API response", CollectionUtils.isEmpty(missingNodeList));


    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "Fox7Sanity","ExhaustiveRegression", "Fox7Sanity"}, dataProvider="getCouponnameForDifferentUser")
    public void CouponService_applyCouponOnCartWithMsgNotApplicableForThisUserSchemaValidation(String CouponName) {
        getAndSetTokens("coupontesting@myntra.com", "coupontesting@myntra.com");

        MyntraService service = Myntra.getService(ServiceType.PORTAL_CART, APINAME.APPLYCOUPONCART, init.Configurations, new String[]{CouponName}, PayloadType.JSON, PayloadType.JSON);
        HashMap<String, String> headersGetAdd = new HashMap<String, String>();
        headersGetAdd.put("X-CSRF-Token", sXid);
        headersGetAdd.put("xid", xID);
        System.out.println(service.URL);
        RequestGenerator req = new RequestGenerator(service, headersGetAdd);
        String response = req.respvalidate.returnresponseasstring();
        System.out.println(response);
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();

        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_clickForOfferDualPercentageCouponWithLoginSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in apply Coupon On Cart With Msg Not Applicable For This User API response", CollectionUtils.isEmpty(missingNodeList));

    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","Fox7Sanity", "ExhaustiveRegression", "Fox7Sanity"}, dataProvider="getMinimumAmtCoupon")
    public void CouponService_applyCouponOnCartWithMinimumAMountCouponMsgSchemaValidation(String CouponName) {
        getAndSetTokens("coupontesting@myntra.com", "coupontesting@myntra.com");

        MyntraService service = Myntra.getService(ServiceType.PORTAL_CART, APINAME.APPLYCOUPONCART, init.Configurations, new String[]{CouponName}, PayloadType.JSON, PayloadType.JSON);
        HashMap<String, String> headersGetAdd = new HashMap<String, String>();
        headersGetAdd.put("X-CSRF-Token", sXid);
        headersGetAdd.put("xid", xID);
        System.out.println(service.URL);
        RequestGenerator req = new RequestGenerator(service, headersGetAdd);
        String response = req.respvalidate.returnresponseasstring();
        System.out.println(response);
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();

        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_clickForOfferDualPercentageCouponWithLoginSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in applyCouponOnCartWithMinimumAMountCouponMsg API response", CollectionUtils.isEmpty(missingNodeList));


    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","Fox7Sanity", "ExhaustiveRegression", "Fox7Sanity"}, dataProvider="getMultipleItemCoupon")
    public void CouponService_applyCouponOnCartWithMultipleItemCouponMsgSchemaValidation(String CouponName) {
        getAndSetTokens("coupontesting@myntra.com", "coupontesting@myntra.com");

        MyntraService service = Myntra.getService(ServiceType.PORTAL_CART, APINAME.APPLYCOUPONCART, init.Configurations, new String[]{CouponName}, PayloadType.JSON, PayloadType.JSON);
        HashMap<String, String> headersGetAdd = new HashMap<String, String>();
        headersGetAdd.put("X-CSRF-Token", sXid);
        headersGetAdd.put("xid", xID);
        System.out.println(service.URL);
        RequestGenerator req = new RequestGenerator(service, headersGetAdd);
        String response = req.respvalidate.returnresponseasstring();
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();

        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_applyCouponOnCartWithMultipleItemCouponMsgSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in applyCouponOnCartWithMultipleItemCouponMsg API response", CollectionUtils.isEmpty(missingNodeList));



    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"}, dataProvider="getExpiredCoupon")
    public void CouponService_applyCouponOnCartWithExpiredCouponMsg(String CouponName) {
        getAndSetTokens("coupontesting@myntra.com", "coupontesting@myntra.com");

        MyntraService service = Myntra.getService(ServiceType.PORTAL_CART, APINAME.APPLYCOUPONCART, init.Configurations, new String[]{CouponName}, PayloadType.JSON, PayloadType.JSON);
        HashMap<String, String> headersGetAdd = new HashMap<String, String>();
        headersGetAdd.put("X-CSRF-Token", sXid);
        headersGetAdd.put("xid", xID);
        System.out.println(service.URL);
        RequestGenerator req = new RequestGenerator(service, headersGetAdd);
        String response = req.respvalidate.returnresponseasstring();
        System.out.print(response);
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_applyCouponOnCartWithExpiredCouponMsgSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in applyCouponOnCartWithMultipleItemCouponMsg API response", CollectionUtils.isEmpty(missingNodeList));


    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"}, dataProvider="getLockedCoupon")
    public void CouponService_applyCouponOnCartWithLockedCouponMsgSchema(String CouponName) {
        getAndSetTokens("coupontesting@myntra.com", "coupontesting@myntra.com");

        MyntraService service = Myntra.getService(ServiceType.PORTAL_CART, APINAME.APPLYCOUPONCART, init.Configurations, new String[]{CouponName}, PayloadType.JSON, PayloadType.JSON);
        HashMap<String, String> headersGetAdd = new HashMap<String, String>();
        headersGetAdd.put("X-CSRF-Token", sXid);
        headersGetAdd.put("xid", xID);
        System.out.println(service.URL);
        RequestGenerator req = new RequestGenerator(service, headersGetAdd);
        String response = req.respvalidate.returnresponseasstring();
        System.out.print(response);
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_applyCouponOnCartWithLockedCouponMsgSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in applyCouponOnCartWithLockedCoupon API response", CollectionUtils.isEmpty(missingNodeList));

    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"}, dataProvider="getInvalidCoupon")
    public void CouponService_applyCouponOnCartWithInvalidCouponMsgSchema(String CouponName) {
        getAndSetTokens("coupontesting@myntra.com", "coupontesting@myntra.com");

        MyntraService service = Myntra.getService(ServiceType.PORTAL_CART, APINAME.APPLYCOUPONCART, init.Configurations, new String[]{CouponName}, PayloadType.JSON, PayloadType.JSON);
        HashMap<String, String> headersGetAdd = new HashMap<String, String>();
        headersGetAdd.put("X-CSRF-Token", sXid);
        headersGetAdd.put("xid", xID);
        System.out.println(service.URL);
        RequestGenerator req = new RequestGenerator(service, headersGetAdd);
        String response = req.respvalidate.returnresponseasstring();
        System.out.print(response);
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_applyCouponOnCartWithInvalidCouponMsgSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in applyCouponOnCartWithLockedCoupon API response", CollectionUtils.isEmpty(missingNodeList));


    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"}, dataProvider = "isCouponvalid")
    public void CouponService_isCouponValidSchema(String couponName, String login ,String channel) {
        MyntraService service = Myntra.getService(
                ServiceType.PORTAL_COUPONSERVICE,
                APINAME.ISCOUPONVALID, init.Configurations,
                new String[]{couponName, login, channel}, PayloadType.JSON, PayloadType.JSON);
        System.out.println("json paylod---- >" + service.Payload);
        RequestGenerator req = new RequestGenerator(service);
        String response = req.respvalidate.returnresponseasstring();
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_isCouponValidSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CouponService_isCouponValid API response", CollectionUtils.isEmpty(missingNodeList));



    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"}, dataProvider = "fLasMPDCoupon")
    public void CouponService_createMPDCouponSchema(String startDate, String ExpireDate ,String CouponName,String CreatedTime ,String mrpAmount, String mrpPercentage, String Description, String CouponType) {
        MyntraService service = Myntra.getService(
                ServiceType.PORTAL_COUPONSERVICE,
                APINAME.CREATEFLASHCOUPON, init.Configurations,
                new String[]{startDate, ExpireDate, CouponName, CreatedTime, mrpAmount,
                        mrpPercentage, Description, CouponType}, PayloadType.JSON, PayloadType.JSON);
        System.out.println("json paylod---- >" + service.Payload);
        RequestGenerator req = new RequestGenerator(service);
        String response=req.respvalidate.returnresponseasstring();
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_createMPDCouponSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CouponService_createMPDCoupon API response", CollectionUtils.isEmpty(missingNodeList));
    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"}, dataProvider = "IntenCouponScore")
    public void CouponService_createIntentRuleSchema(String couponName, String score) {
        startTime = String.valueOf(currentTime);
        endTime = String.valueOf(currentTime + 4000);
        createMPDCoupon(startTime, endTime, couponName, startTime, "30", "40", "Test", "max_percentage_dual");
        MyntraService service = Myntra.getService(
                ServiceType.PORTAL_COUPONSERVICE,
                APINAME.CREATEINTENTRULE, init.Configurations,
                new String[]{couponName, score}, PayloadType.JSON, PayloadType.JSON);
        System.out.println("json paylod---- >" + service.Payload);
        RequestGenerator req = new RequestGenerator(service);
        String response=req.respvalidate.returnresponseasstring();
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_createIntentRuleSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CouponService_createIntentRule API response", CollectionUtils.isEmpty(missingNodeList));


    }

    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"})
    public void CouponService_SearchAllCouponGroupsSchemaVAlidation()
    {
        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.SEARCHALLCOUPONGROUPS,
                init.Configurations, new String[] {""}, PayloadType.JSON,
                PayloadType.JSON);
        System.out.println("coupon URL-----\n" + service.URL);
        System.out.println("coupon Payload-----\n" + service.Payload);
        RequestGenerator req = new RequestGenerator(service);
        String response=req.respvalidate.returnresponseasstring();
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_SearchAllCouponGroupsSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CouponService_SearchAllCouponGroups API response", CollectionUtils.isEmpty(missingNodeList));


    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"}, dataProvider = "couponGroup")
    public void CouponService_SearchCouponSchemaValidation(String couponGroup ,String []coupon) {

        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.SEARCHCOUPON,
                init.Configurations, new String[]{couponGroup}, PayloadType.JSON,
                PayloadType.JSON);
        System.out.println("coupon URL-----\n" + service.URL);
        System.out.println("coupon Payload-----\n" + service.Payload);
        RequestGenerator req = new RequestGenerator(service);
        String response = req.respvalidate.returnresponseasstring();
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_SearchCouponSchemaValidation");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CouponService_SearchCoupon API response", CollectionUtils.isEmpty(missingNodeList));

    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"})
    public void CouponService_GetBrandExclusionsSchemaValidation()
    {

        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETBRANDEXCLUSION, init.Configurations );
        System.out.println("coupon URL-----\n" + service.URL);
        RequestGenerator req = new RequestGenerator(service);
        String response = req.respvalidate.returnresponseasstring();
//        System.out.print(response);
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_GetBrandExclusionsSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CouponService_GetBrandExclusion API response", CollectionUtils.isEmpty(missingNodeList));


    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"},dataProvider = "CreatebrandExclusinSuccessMsg")
    public void CouponService_CreateBrandExclusionSchemaValidation(String createBrandExclusionSuccessMessage ,String[]brand)
    {


        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.CREATEBRANDEXCLUSION, init.Configurations, brand, PayloadType.JSON, PayloadType.JSON);
        System.out.println("coupon URL-----\n" + service.URL);
        RequestGenerator req = new RequestGenerator(service);
        String response = req.respvalidate.returnresponseasstring();
        System.out.println(response);
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_CreateBrandExclusionSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CouponService_CreateBrandExclusion API response", CollectionUtils.isEmpty(missingNodeList));



    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression","Regression", "ExhaustiveRegression", "Fox7Sanity"})
    public void CouponService_DeleteBrandinclusion() {
        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETBRANDEXCLUSION, init.Configurations);
        System.out.println("coupon URL-----\n" + service.URL);
        RequestGenerator req = new RequestGenerator(service);
        String jsonResp = req.respvalidate.returnresponseasstring();
        System.out.println(jsonResp);
        List styleId = JsonPath.read(jsonResp, "$.couponExcludedBrandEntries..id");
        System.out.print(styleId);
        System.out.print(styleId.get(1));
        MyntraService serviceA = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.DELETEBRANDEXCLUSION, init.Configurations);
        serviceA.URL = apiUtil.prepareparameterizedURL(serviceA.URL, styleId.get(1).toString());
        RequestGenerator req1 = new RequestGenerator(serviceA);
        String response = req1.respvalidate.returnresponseasstring();
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponServices_DeleteBrandExclusionSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CouponServices_DeleteBrandExclusion API response", CollectionUtils.isEmpty(missingNodeList));

    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"},dataProvider = "styleInclusionExclusion")
    public void CouponService_StyleInclusionExclusionSchemaValidation(String styleInclusionExclusionmsg ,String []couponGroup) {


        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.STYLEINCLUSIONEXCLUSION, init.Configurations, couponGroup, PayloadType.JSON, PayloadType.JSON);
        RequestGenerator req = new RequestGenerator(service);
        String response = req.respvalidate.returnresponseasstring();
        System.out.print(response);
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponServices_DeleteBrandExclusionSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CouponService_StyleInclusionExclusion API response", CollectionUtils.isEmpty(missingNodeList));



    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"},dataProvider = "lockUserCoupon")
    public void CouponService_LockUserCouponSchemaVAlidation(String coupon,String user,String successMSg) {

        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.LOCKUSERCOUPON, init.Configurations, new String[]{coupon,user},PayloadType.JSON, PayloadType.JSON);
        RequestGenerator req = new RequestGenerator(service);
        String response = req.respvalidate.returnresponseasstring();
        System.out.print(response);
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_LockUserCouponSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CouponService_LockUserCoupon API response", CollectionUtils.isEmpty(missingNodeList));


    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"},dataProvider = "unlockUserCoupon")
    public void CouponService_unLockUserCouponSchemaValidation(String coupon,String user,String successMSg) {

        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.UNLOCKUSERCOUPON, init.Configurations, new String[]{coupon,user},PayloadType.JSON, PayloadType.JSON);
        RequestGenerator req = new RequestGenerator(service);
        String response = req.respvalidate.returnresponseasstring();
        System.out.print(response);
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_unLockUserCouponSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CouponService_unLockUserCoupon API response", CollectionUtils.isEmpty(missingNodeList));


    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"})
    public void CouponService_getAllDiscountCapSchemaValidation() {
        //creating Random styleId Of Five digit
        Random r = new Random(System.currentTimeMillis());
        int rand = ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
        String styleID1 = Integer.toString(rand);
        String styleid2 = Integer.toString(rand + 1);
        HashMap<String, String> headersGetAdd = new HashMap<String, String>();
        headersGetAdd.put("user", "coupontesting@myntra.com");
        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.STYLEDISCOUNTCAPINSERT, init.Configurations, new String[]{styleID1, styleid2}, PayloadType.JSON, PayloadType.JSON);
        RequestGenerator req = new RequestGenerator(service, headersGetAdd);
        String jsonResp = req.respvalidate.returnresponseasstring();
        MyntraService service1 = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETALLDISCOUNTCAP, init.Configurations);
        RequestGenerator req1 = new RequestGenerator(service1);
        String response = req1.respvalidate.returnresponseasstring();
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponServices_getAllDiscountPlcSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CouponService_getAllDiscount API response", CollectionUtils.isEmpty(missingNodeList));


    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"})
    public void CouponService_styleDiscountCapInsertSchemaValidation() {
        Connection con = null;
        Statement stmt = null;
        int discountcap = 0;
        String user = null;
        //creating Random styleId Of Five digit
        Random r = new Random(System.currentTimeMillis());
        int rand = ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
        String styleID1 = Integer.toString(rand);
        String styleid2 = Integer.toString(rand + 1);
        HashMap<String, String> headersGetAdd = new HashMap<String, String>();
        headersGetAdd.put("user", "coupontesting@myntra.com");
        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.STYLEDISCOUNTCAPINSERT, init.Configurations, new String[]{styleID1, styleid2}, PayloadType.JSON, PayloadType.JSON);
        RequestGenerator req = new RequestGenerator(service, headersGetAdd);
        String response = req.respvalidate.returnresponseasstring();
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_styleDiscountCapInsertSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CouponService_styleDiscountCapInsert API response", CollectionUtils.isEmpty(missingNodeList));


    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "Fox7Sanity","ExhaustiveRegression", "Fox7Sanity"})
    public void CouponService_AddBinRangeSchemaValidation()
    {
        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.ADDBINRANGE, init.Configurations );
        System.out.println("coupon URL-----\n" + service.URL);
        RequestGenerator req = new RequestGenerator(service);
        String response = req.respvalidate.returnresponseasstring();
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponServices_addBinRangeSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CouponService_AddBinRange API response", CollectionUtils.isEmpty(missingNodeList));





    }
    @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression", "Fox7Sanity"},dataProvider = "getBinRange" )
    public void CouponService_GetBinRangeSchema(String coupon)
    {
        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETBINRANGE, init.Configurations );
        service.URL = apiUtil.prepareparameterizedURL(service.URL,coupon);
        System.out.println("coupon URL-----\n" + service.URL);
        RequestGenerator req = new RequestGenerator(service);
        String response = req.respvalidate.returnresponseasstring();
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_GetBinRangeSchema");
            missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CouponService_GetBinRange API response", CollectionUtils.isEmpty(missingNodeList));






    }

    private void modifyPercentageCoupon(String startDate, String lastEdited, String CouponName, String mrpPercentage, String user) {
        MyntraService service = Myntra.getService(
                ServiceType.PORTAL_COUPONSERVICE,
                APINAME.MODIFYPERCENTAGECOUPON, init.Configurations,
                new String[]{startDate, lastEdited, CouponName,
                        mrpPercentage, user}, PayloadType.JSON, PayloadType.JSON);
        System.out.println("json paylod---- >" + service.Payload);
        RequestGenerator req = new RequestGenerator(service);

        System.out.println("Response in modify coupon--->>> " + req.respvalidate.returnresponseasstring());
    }
    private void modifyPercentageDualCoupon(String startDate, String lastEdited ,String CouponName,String mrpAmount,String mrpPercentage,String user)
    {
        MyntraService service = Myntra.getService(
                ServiceType.PORTAL_COUPONSERVICE,
                APINAME.MODIFYPERCENTAGEDUALCOUPON, init.Configurations,
                new String[] { startDate, lastEdited, CouponName,mrpAmount,
                        mrpPercentage,user}, PayloadType.JSON, PayloadType.JSON);
        System.out.println( "json paylod---- >" + service.Payload);
        RequestGenerator req = new RequestGenerator(service);

        System.out.println(req.respvalidate.returnresponseasstring());
        AssertJUnit.assertEquals(200, req.response.getStatus());
    }
    private void getAndSetTokens(String userName, String password)
    {
        MyntraService serviceSignIn = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.SIGNIN, init.Configurations, new String[]{ userName, password });
        RequestGenerator reqSignIn = new RequestGenerator(serviceSignIn);
        MultivaluedMap<String, Object> map = reqSignIn.response.getHeaders();
        System.out.println(reqSignIn.respvalidate.returnresponseasstring());

        for (Map.Entry entry : map.entrySet())
        {
            if (entry.getKey().toString().equalsIgnoreCase("xid"))
                xID = entry.getValue().toString();
        }
        xID = xID.substring((xID.indexOf("[") + 1), xID.lastIndexOf("]"));
        sXid = reqSignIn.respvalidate.GetNodeTextUsingIndex("xsrfToken");
        System.out.println(xID);
        System.out.println(sXid);
        if(sXid.contains("'"))
            sXid = sXid.substring(sXid.indexOf("'")+1, sXid.lastIndexOf("'"));
        else
            sXid = sXid.substring(sXid.indexOf("[")+1, sXid.lastIndexOf("]"));
    }
    private void modifydualCoupon(String startDate, String lastEdited ,String CouponName,String mrpAmount, String mrpPercentage,String user)
    {
        MyntraService service = Myntra.getService(
                ServiceType.PORTAL_COUPONSERVICE,
                APINAME.MODIFYDUALCOUPON, init.Configurations,
                new String[] { startDate, lastEdited, CouponName, mrpAmount,
                        mrpPercentage,user}, PayloadType.JSON, PayloadType.JSON);
        System.out.println( "json paylod---- >" + service.Payload);
        RequestGenerator req = new RequestGenerator(service);

        System.out.println(req.respvalidate.returnresponseasstring());
        AssertJUnit.assertEquals(200, req.response.getStatus());
    }
    private void createMPDCoupon(String startDate, String ExpireDate ,String CouponName,String CreatedTime ,String mrpAmount, String mrpPercentage, String Description, String CouponType)
    {
        MyntraService service = Myntra.getService(
                ServiceType.PORTAL_COUPONSERVICE,
                APINAME.CREATEFLASHCOUPON, init.Configurations,
                new String[] { startDate, ExpireDate,CouponName, CreatedTime,mrpAmount,
                        mrpPercentage,Description,CouponType}, PayloadType.JSON, PayloadType.JSON);
        System.out.println( "json paylod---- >" + service.Payload);
        RequestGenerator req = new RequestGenerator(service);

        System.out.println(req.respvalidate.returnresponseasstring());
        AssertJUnit.assertEquals(200, req.response.getStatus());
    }

}









