package com.myntra.apiTests.inbound.planningandbuying.test;

import com.myntra.apiTests.inbound.dp.buyingandplanningDP;
import com.myntra.apiTests.inbound.helper.planningandbuying.*;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * Created by 16515 on 18/01/17.
 */
public class SkuLookupTests extends BaseTest {

    private static Logger log;
    private HashMap<String, String> create_baseoi_headers;
    private HashMap<String, String> upload_baseoi_headers;
    private HashMap<String, String> download_baseoi_headers;
    private BaseOrderIndentJson baseOI;
    private JeevesHelper jeeveshelper;
    private JeevesValidator jeevesvalidator;
    VendorData bata=new Constants.OIHeaderBATA();

    @BeforeClass(alwaysRun = true)
    public void init() {
        log = Logger.getLogger(InseasonOrderIndent.class);
        upload_baseoi_headers = new HashMap<>();
        baseOI = new BaseOrderIndentJson();
        jeeveshelper = new JeevesHelper();
        jeevesvalidator = new JeevesValidator();


        // headers
        create_baseoi_headers = jeeveshelper.Headers(Constants.HEADERS.AUTHORIZATION,
                Constants.HEADERS.ACCEPT, Constants.HEADERS.CONTENTTYPE);
        download_baseoi_headers = jeeveshelper.Headers(Constants.HEADERS.AUTHORIZATION,
                Constants.HEADERS.OCTED_CONTENTTYPE, Constants.HEADERS.CONTENTTYPE);
    }

    @Test(dataProvider = "SkuLookupOrderIndents", dataProviderClass = buyingandplanningDP.class, groups = {
            "sanity", "order-indents"}, description = "Create OI & PI Test")
    public void skuLookupValidation(String role, String inputDataFile) throws Exception {

        //Create Order Indent
        String OI_ID = jeeveshelper.createOIAndGetOrderId(role, inputDataFile, create_baseoi_headers,bata);

        // Download OI sheet and verify all the skucodes are fetched
        // successfully.
        jeeveshelper.downloadOI(OI_ID, download_baseoi_headers, role, Constants.DATA_FILES.downloaded_file);

        if (!role.contains("cm")) {
            //Previous MRP Column does not exists for Vendor. So no Validation Required.
            jeeveshelper.updateOIStatus(OI_ID, Constants.DATA_FILES.pending_with_myntra_state, create_baseoi_headers, role);

        }
        else{
            jeevesvalidator.validateSkucodesArePresent(3,3,248);
            //jeevesvalidator.validatePreviousMRPValuesArePresent(3,14,235);
        }

        // construct payload
        String updateOI_headers_payload = baseOI.updateOIPayload(bata);

        // updateOI headers
        String update_OI_response = jeeveshelper.updateOIHeaders(OI_ID, updateOI_headers_payload, create_baseoi_headers);
        jeevesvalidator.validateTheResponseStatus(update_OI_response, "UPDATE_OI_HEADERS");

        // create PI
        String create_pi_response = jeeveshelper.createPI(OI_ID, create_baseoi_headers);
        jeevesvalidator.validateTheResponseStatus(create_pi_response, "CREATE_PI");
    }


    @Test(dataProvider = "SkuLookupOrderIndents", dataProviderClass = buyingandplanningDP.class, groups = {
            "sanity", "order-indents"}, description = "Create OI & PI Test")
    public void mrpLookupValidation(String role,String inputDataFile) throws Exception {

        //Create Order Indent
        String OI_ID = jeeveshelper.createOIAndGetOrderId(role, inputDataFile, create_baseoi_headers,bata);

        // Download OI sheet and verify all the skucodes are fetched
        // successfully.
        jeeveshelper.downloadOI(OI_ID, download_baseoi_headers, role, Constants.DATA_FILES.downloaded_file);

        if (!role.contains("cm")) {
            jeeveshelper.updateOIStatus(OI_ID, Constants.DATA_FILES.pending_with_myntra_state, create_baseoi_headers, role);
        }
        else{
            jeevesvalidator.validatePreviousMRPValuesArePresent(3,14,248);
        }

        // construct payload
        String updateOI_headers_payload = baseOI.updateOIPayload(bata);

        // updateOI headers
        String update_OI_response = jeeveshelper.updateOIHeaders(OI_ID, updateOI_headers_payload, create_baseoi_headers);
        jeevesvalidator.validateTheResponseStatus(update_OI_response, "UPDATE_OI_HEADERS");

        // create PI
        String create_pi_response = jeeveshelper.createPI(OI_ID, create_baseoi_headers);
        jeevesvalidator.validateTheResponseStatus(create_pi_response, "CREATE_PI");
    }
}
