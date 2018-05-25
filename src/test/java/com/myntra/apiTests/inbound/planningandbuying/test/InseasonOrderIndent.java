package com.myntra.apiTests.inbound.planningandbuying.test;

import com.myntra.apiTests.inbound.dp.buyingandplanningDP;
import com.myntra.apiTests.inbound.helper.planningandbuying.*;
import com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses.*;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.orderindent.client.codes.BaseOrderIndentErrorCodes;
import com.myntra.orderindent.client.codes.BuyPlanErrorCodes;
import com.myntra.orderindent.enums.BuyPlanType;
import com.myntra.orderindent.enums.VendorTermsValidationStatus;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is a test class for Inseason OI flow
 */

public class InseasonOrderIndent extends BaseTest {
    private static Logger log;
    private BaseOrderIndentJson baseOI;
    private JeevesHelper jeeveshelper;
    private JeevesValidator jeevesvalidator;
    private VendorData benetton;

    @BeforeClass(alwaysRun = true)
    public void init() {
        log = Logger.getLogger(InseasonOrderIndent.class);
        baseOI = new BaseOrderIndentJson();
        jeeveshelper = new JeevesHelper();
        jeevesvalidator = new JeevesValidator();
        Constants.TEMPLATE_HEADERS.setColumnMap();
        benetton=new Constants.OIHeaderUCB();
    }

    @Test(dataProvider = "CreateOI", dataProviderClass = buyingandplanningDP.class, groups = {"newTestCases"})
    public void createBaseOI(String role,String paylad, String file) throws Exception {
        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");

        //create OI
        Object[] obj = jeeveshelper.createOI(paylad,file,role);
        JobTrackerResponse jobTrackerResponse = (JobTrackerResponse)obj[0];
        BaseOIHeaderRequest baseOIHeaderRequest = (BaseOIHeaderRequest) obj[1];

        //Get the BuyPlan Id from Job Tracker Responsex
        String buyPlanId = jeeveshelper.getBuyPlanIDFromJobTrackerResponse(jobTrackerResponse);


        //Search for the Buy Plan once job is Completed
        String OI_Search_Deatils = jeeveshelper.searchBuyPlan(buyPlanId, create_baseoi_headers, role);
        log.debug("Buy Plan Details :" + OI_Search_Deatils);
        BaseOIDetailsResponse baseOIDetailsResponse = (BaseOIDetailsResponse)
                APIUtilities.getJsontoObject(OI_Search_Deatils, new BaseOIDetailsResponse());
        jeevesvalidator.validateBuyPlanSearchResponse(baseOIHeaderRequest, baseOIDetailsResponse, role);

        jeevesvalidator.validateTheVendorTermsValidationStatus(baseOIDetailsResponse, VendorTermsValidationStatus.PENDING);

    }

    @Test(dataProvider = "roles", dataProviderClass = buyingandplanningDP.class, groups = {"newTestCases"})
    public void createOIHeaderAndUploadFileOfWrongFormat(String role) throws Exception {
        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");

        // construct payload
        String payload = baseOI.createOIPayload(benetton);
        BaseOIHeaderRequest baseOIHeaderRequest = (BaseOIHeaderRequest) APIUtilities.
                getJsontoObject(payload, new BaseOIHeaderRequest());

        // Create baseOI
        String base_oi_id = jeeveshelper.createBaseOrderIndent(baseOIHeaderRequest,payload,role);

        // upload baseOI
        String job_id = jeeveshelper.uploadExcelForCreateOI(Constants.DATA_FILES.invalid_file,
                baseOIHeaderRequest,base_oi_id,role);

        //Wait for the job tracker to complete
        String jobTrackerResponseString = jeeveshelper.checkJobStatusInJobTrackerForNegativeCases(job_id, create_baseoi_headers);

        //Validate the Job Tracker Response
        JobTrackerResponse jobTrackerResponse = (JobTrackerResponse) APIUtilities.getJsontoObject
                (jobTrackerResponseString, new JobTrackerResponse());
        jeevesvalidator.validateTheJobTrackerResponseForFailureScenario(jobTrackerResponse, base_oi_id,
                BaseOrderIndentErrorCodes.INVALID_EXCEL_FORMAT.getStatusMessage());

    }

    @Test(dataProvider = "roles", dataProviderClass = buyingandplanningDP.class, groups = {"newTestCases"})
    public void searchBuyPlanWithInvalidAccess(String role) throws Exception {
        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");

        //Create OI
        Object[] obj = jeeveshelper.createOI(Constants.DATA_FILES.file1, role);
        JobTrackerResponse jobTrackerResponse = (JobTrackerResponse)obj[0];

        //Get the BuyPlan Id from Job Tracker Response
        String BuyPlanId = jeeveshelper.getBuyPlanIDFromJobTrackerResponse(jobTrackerResponse);

        String OI_Search_Deatils = "";
        if (role.contains("cm")) {
            OI_Search_Deatils = jeeveshelper.searchBuyPlan(BuyPlanId, create_baseoi_headers, Constants.ROLES.UCB_VENDOR_ROLE);
        } else if (role.contains("vendor")) {
            OI_Search_Deatils = jeeveshelper.searchBuyPlan(BuyPlanId, create_baseoi_headers, Constants.ROLES.VENDOR_ROLE_2);
        }

        //Validation to check Vendors can view only their respective OI and CM cant view Vendor Draft OI
        BaseOIDetailsResponse baseOIDetailsResponse = (BaseOIDetailsResponse) APIUtilities.
                getJsontoObject(OI_Search_Deatils, new BaseOIDetailsResponse());
        int statusCode = baseOIDetailsResponse.getStatus().getStatusCode();
        String statusMessage = baseOIDetailsResponse.getStatus().getStatusMessage();
        jeevesvalidator.validateInvalidUserAccess(statusCode, statusMessage, role);

    }

    @Test(dataProvider = "roles", dataProviderClass = buyingandplanningDP.class, groups = {"newTestCases"})
    public void shareOIIndent(String role) throws Exception {
        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");

        //Create OI
        Object[] obj = jeeveshelper.createOI(Constants.DATA_FILES.file1,role);
        JobTrackerResponse jobTrackerResponse = (JobTrackerResponse)obj[0];
        BaseOIHeaderRequest baseOIHeaderRequest = (BaseOIHeaderRequest) obj[1];

        //Get the BuyPlan Id from Job Tracker Response
        String BuyPlanId = jeeveshelper.getBuyPlanIDFromJobTrackerResponse(jobTrackerResponse);

        //Search for the BuyPlan Once The Job is completed
        String OI_Search_Deatils = jeeveshelper.searchBuyPlan(BuyPlanId, create_baseoi_headers, role);
        log.debug("Buy Plan Details :" + OI_Search_Deatils);
        BaseOIDetailsResponse baseOIDetailsResponse = (BaseOIDetailsResponse) APIUtilities.
                getJsontoObject(OI_Search_Deatils, new BaseOIDetailsResponse());
        jeevesvalidator.validateBuyPlanSearchResponse(baseOIHeaderRequest, baseOIDetailsResponse, role);

        //validate the Share Response after update
        jeeveshelper.shareAndValidateTheStatusChange(BuyPlanId,role,benetton);



    }


    @Test(dataProvider = "oiRolesAndBuyPlanType", dataProviderClass = buyingandplanningDP.class, groups = {"newTestCases"},
            enabled = false)
    public void validateTheOITemplate(String role, String buyPlanType) throws Exception {
        HashMap<String, String> download_baseoi_headers = jeeveshelper.initializeHeader(role, "downloadfile");
        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");

        //Download the template for Different buyPlan types
        String downloadFile = Constants.DATA_FILES.downloaded_file;
        jeeveshelper.downloadOITemplate(download_baseoi_headers, role, downloadFile, buyPlanType);

        //Validate that the template data validation error message shown in the excel sheet
        jeevesvalidator.validateTemplateDataErrorShownInExcel(downloadFile);

        //Validates the Template headers
        ArrayList<String> headers = jeeveshelper.getFileHeaders(downloadFile);
        jeevesvalidator.validateTemplateHeaders(headers, buyPlanType);

        //Validates the Data Validator Ranges applied for the entire data
        HashMap<String, String> fileValidator = jeeveshelper.getFileValidatorRangeData(downloadFile);
        jeevesvalidator.validateTemplateFilterData(downloadFile, buyPlanType, fileValidator);

        //Validates all the data values fetched from each of the services against the ones shown in the master sheet of excel
        jeevesvalidator.validateTemplateValuesOfMasterSheet(downloadFile, create_baseoi_headers, buyPlanType);


    }

    @Test(dataProvider = "CreateOIDataAndBuyPlanType", dataProviderClass = buyingandplanningDP.class, groups = {"newTestCases"})
    public void CreateBaseOIFromDownloadedTemplate(String role, String buyPlanType, Object[][] testData) throws Exception {

        HashMap<String, String> download_baseoi_headers = jeeveshelper.initializeHeader(role, "downloadfile");
        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");

        //Download the template
        String downloadFile = Constants.DATA_FILES.downloaded_file;
        jeeveshelper.downloadOITemplate(download_baseoi_headers, role, downloadFile, buyPlanType);

        //Write to the downloaded template file
        jeeveshelper.appendDataToExcelFile(testData, downloadFile);
        String payload ="";

        if(buyPlanType.equals(BuyPlanType.MMB.getType())) {
            Constants.OIHeaderUCB UCB = new Constants.OIHeaderUCB();
            // construct payload
            payload = baseOI.createOIPayload(UCB,BuyPlanType.MMB.getType());
        }
        else if(buyPlanType.equals(BuyPlanType.MFB.getType())) {
            Constants.OIHeaderFF FractalFashion
                    = new Constants.OIHeaderFF();
            // construct payload
            payload = baseOI.createOIPayload(FractalFashion,BuyPlanType.MFB.getType());
        }

        //Create OI by uploading the template
        Object[] obj = jeeveshelper.createOI(payload,downloadFile,role);
        JobTrackerResponse jobTrackerResponse = (JobTrackerResponse)obj[0];
        BaseOIHeaderRequest baseOIHeaderRequest = (BaseOIHeaderRequest) obj[1];

        //Get the BuyPlan Id from Job Tracker Response
        String BuyPlanId = jeeveshelper.getBuyPlanIDFromJobTrackerResponse(jobTrackerResponse);

        //Search for the Buy Plan once job is Completed
        String OI_Search_Deatils = jeeveshelper.searchBuyPlan(BuyPlanId, create_baseoi_headers, role);
        log.debug("Buy Plan Details :" + OI_Search_Deatils);
        BaseOIDetailsResponse baseOIDetailsResponse = (BaseOIDetailsResponse) APIUtilities.
                getJsontoObject(OI_Search_Deatils, new BaseOIDetailsResponse());
        jeevesvalidator.validateBuyPlanSearchResponse(baseOIHeaderRequest, baseOIDetailsResponse, role);

    }

    @Test(dataProvider = "roles", dataProviderClass = buyingandplanningDP.class, groups = {"newTestCases"})
    public void CreateOIErrorValidation(String role) throws Exception {

        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");

        Object[][] data = new Object[30][15];
        String[][] errorCode = new String[30][1];

        boolean testFailed = false;

        for (int i = 1; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i - 1][j] = jeeveshelper.readDataFile(Constants.DATA_FILES.OI_Error_data, i, j);
                if (j == data[i].length - 1) {
                    if (data[i - 1][j] instanceof String) {
                        errorCode[i][0] = (String) data[i - 1][j];
                    }
                }
            }
            //Upload file for error scenarios
            jeeveshelper.writeDataFile(data[i - 1], Constants.DATA_FILES.OI_data_file);

            // construct payload
            String payload = baseOI.createOIPayload(benetton);
            BaseOIHeaderRequest baseOIHeaderRequest = (BaseOIHeaderRequest) APIUtilities.getJsontoObject(payload, new BaseOIHeaderRequest());

            //Create The Base OI
            String base_oi_id = jeeveshelper.createBaseOrderIndent(baseOIHeaderRequest,payload,role);

            //Upload Excel Sheet
            String job_id = jeeveshelper.uploadExcelForCreateOI(Constants.DATA_FILES.OI_data_file,baseOIHeaderRequest,base_oi_id,role);

            //Wait for the job tracker to complete
            String jobTrackerResponseString = jeeveshelper.checkJobStatusInJobTrackerForNegativeCases(job_id, create_baseoi_headers);

            JobTrackerResponse jobTrackerResponse = (JobTrackerResponse) APIUtilities.getJsontoObject(jobTrackerResponseString, new JobTrackerResponse());
            JobTrackerResponseData jobTrackerResponseData = jobTrackerResponse.getData()[0];
            if (jobTrackerResponseData.getStatus().equalsIgnoreCase("INTERRUPTED")) {
                if(i==data.length-1)
                {
                    jeevesvalidator.validateTheJobTrackerResponseForFailureScenario(jobTrackerResponse, base_oi_id,
                            BaseOrderIndentErrorCodes.PLANNING_TAXONOMY_NO_BUCM_FOR_BAG.getStatusMessage());
                }
                else
                {
                    jeevesvalidator.validateTheJobTrackerResponseForFailureScenario(jobTrackerResponse, base_oi_id,
                            "Invalid entries for Base Order Indent");
                }


                String jobTrackerErrorFileResponseString = jeeveshelper.obtainJobTrackerErrorFileResponse(job_id, create_baseoi_headers);

                //Obtain the Error File URl and download the file
                JobTrackerErrorFileResponse jobTrackerErrorFileResponse = (JobTrackerErrorFileResponse) APIUtilities.getJsontoObject(jobTrackerErrorFileResponseString
                        , new JobTrackerErrorFileResponse());
                JobTrackerErrorFileResponseData jobTrackerErrorFileResponseData = jobTrackerErrorFileResponse.getData()[0];
                String errorFileUrl = jobTrackerErrorFileResponseData.getUrl();
                jeeveshelper.downloadUsingStream(errorFileUrl, Constants.DATA_FILES.error_file);

                //Validate the error message
                String OI_error = jeeveshelper.readXLSXFile(Constants.DATA_FILES.error_file, 1, 15);
                jeevesvalidator.validateErrorFile(OI_error, errorCode[i][0]);

            } else {
                testFailed = true;
                String completeData = "";
                for (int k = 0; k < data[i - 1].length - 1; k++) {
                    String str = data[i - 1][k].toString();
                    completeData = completeData + " " + str;
                }

                log.error("For Data: " + completeData + " expected Error Message: " + errorCode[i][0]);
            }
        }
        if (testFailed) {
            Assert.fail("Test Case failed please check error logs displayed");
        }
    }

    @Test(dataProvider = "update", dataProviderClass = buyingandplanningDP.class,groups = {"newTestCases"})
    public void CreateOIErrorValidationForVendorTerms(BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest,
                                                      String role, HashMap<String, Object> requestData) throws Exception {

        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");
        HashMap<String, String> download_baseoi_headers = jeeveshelper.initializeHeader(role, "downloadfile");

        Constants.OIHeaderBATA bata = new Constants.OIHeaderBATA();

        Object[][] data = new Object[4][15];
        String[][] errorCode = new String[4][1];

        for (int i = 1; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = jeeveshelper.readDataFile(Constants.DATA_FILES.VendorTermsErrorData_Error_data, i, j);
                if (j == data[i].length - 1) {
                    if (data[i][j] instanceof String) {
                        errorCode[i][0] = (String) data[i][j];
                    }
                }
            }
            //Upload file for error scenarios
            jeeveshelper.writeDataFile(data[i], Constants.DATA_FILES.OI_data_file);

            // construct payload
            String payload = baseOI.createOIPayload(bata);
            BaseOIHeaderRequest baseOIHeaderRequest = (BaseOIHeaderRequest) APIUtilities.getJsontoObject(payload, new BaseOIHeaderRequest());

            //Create The Base OI
            String base_oi_id = jeeveshelper.createBaseOrderIndent(baseOIHeaderRequest,payload,role);

            //Upload Excel Sheet
            String job_id = jeeveshelper.uploadExcelForCreateOI(Constants.DATA_FILES.OI_data_file,
                    baseOIHeaderRequest,base_oi_id,role);

            //Wait for the job tracker to complete
            String jobTrackerResponseString = jeeveshelper.checkOIStatusInJobTracker(job_id, create_baseoi_headers);
            JobTrackerResponse jobTrackerResponse = (JobTrackerResponse) APIUtilities.
                    getJsontoObject(jobTrackerResponseString,new JobTrackerResponse()) ;
            String buyPlanId = jeeveshelper.getBuyPlanIDFromJobTrackerResponse(jobTrackerResponse);

            if(role.contains("vendor"))
            {
                jeeveshelper.shareAndValidateTheStatusChange(buyPlanId,role,bata);
                role = Constants.ROLES.CM_ROLE;

                Object[] obj = BaseOrderIndentJson.updateHeaderPayloadForCM(bata);
                buyPlanHeaderUpdateRequest = (BuyPlanHeaderUpdateRequest) obj[0];
            }

            create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");
            //Update header
            String updatePayload = APIUtilities.getObjectToJSON(buyPlanHeaderUpdateRequest);
            String update_OI_response = jeeveshelper.updateOIHeaders(buyPlanId, updatePayload, create_baseoi_headers, role);
            log.debug(update_OI_response);
            BaseOIDetailsResponse baseOIDetailsResponse = (BaseOIDetailsResponse) APIUtilities.
                    getJsontoObject(update_OI_response, new BaseOIDetailsResponse());

            log.debug(i);
            if(i==data.length-1)
            {
                jeevesvalidator.validateTheVendorTermsValidationStatus(baseOIDetailsResponse, VendorTermsValidationStatus.ABSENT);
            }else {
                jeevesvalidator.validateTheVendorTermsValidationStatus(baseOIDetailsResponse, VendorTermsValidationStatus.FAILURE);
            }

            //Download OI sheet
            jeeveshelper.downloadOI(buyPlanId, download_baseoi_headers, role, Constants.DATA_FILES.downloaded_file);

            //Validate the error message
            String OI_error = jeeveshelper.readXLSXFile(Constants.DATA_FILES.downloaded_file, 1, 48);
            jeevesvalidator.validateErrorFile(OI_error, errorCode[i][0]);
        }
    }

    @Test(dataProvider = "update", dataProviderClass = buyingandplanningDP.class,groups = {"newTestCases"})
    public void updateBuyPlanHeaderAndShare(BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest, String role, HashMap<String, Object> requestData) throws Exception {
        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");
        Constants.OIHeaderBATA bata = new Constants.OIHeaderBATA();
        String payload = baseOI.createOIPayload(bata, BuyPlanType.MMB.getType());

        //Create OI
        Object[] obj = jeeveshelper.createOI(payload,Constants.DATA_FILES.skuLookupFile251, role);
        JobTrackerResponse jobTrackerResponse = (JobTrackerResponse) obj[0];

        //Get the BuyPlan Id from Job Tracker Response
        String buyPlanId = jeeveshelper.getBuyPlanIDFromJobTrackerResponse(jobTrackerResponse);

        //Search for the Buy Plan once job is Completed
        String OI_Search_Deatils = jeeveshelper.searchBuyPlan(buyPlanId, create_baseoi_headers, role);
        BaseOIDetailsResponse baseOIDetailsResponse = (BaseOIDetailsResponse) APIUtilities.
                getJsontoObject(OI_Search_Deatils, new BaseOIDetailsResponse());
        jeevesvalidator.validateTheVendorTermsValidationStatus(baseOIDetailsResponse, VendorTermsValidationStatus.PENDING);

        //update OI headers
        jeeveshelper.updateHeaderSuccessfullyAndValidate(buyPlanHeaderUpdateRequest,buyPlanId,role,
                baseOIDetailsResponse,requestData,bata);

        //validate the Share Response after update
        jeeveshelper.shareAndValidateTheStatusChange(buyPlanId,role,bata);

    }

    @Test(dataProvider = "updateMFB", dataProviderClass = buyingandplanningDP.class,groups = {"newTestCases"})
    public void updateBuyPlanHeaderForMFB(BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest, String role, HashMap<String, Object> requestData) throws Exception {
        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");

        Constants.OIHeaderFF FractalFashion=new Constants.OIHeaderFF();
        String payload = baseOI.createOIPayload(FractalFashion,BuyPlanType.MFB.getType());

        //Create OI
        Object[] obj = jeeveshelper.createOI(payload,Constants.DATA_FILES.file7, role);
        JobTrackerResponse jobTrackerResponse = (JobTrackerResponse) obj[0];

        //Get the BuyPlan Id from Job Tracker Response
        String buyPlanId = jeeveshelper.getBuyPlanIDFromJobTrackerResponse(jobTrackerResponse);

        //Search for the Buy Plan once job is Completed
        String OI_Search_Deatils = jeeveshelper.searchBuyPlan(buyPlanId, create_baseoi_headers, role);
        BaseOIDetailsResponse baseOIDetailsResponse = (BaseOIDetailsResponse) APIUtilities.
                getJsontoObject(OI_Search_Deatils, new BaseOIDetailsResponse());
        jeevesvalidator.validateTheVendorTermsValidationStatus(baseOIDetailsResponse, VendorTermsValidationStatus.PENDING);

        //update OI headers
        jeeveshelper.updateHeaderSuccessfullyAndValidate(buyPlanHeaderUpdateRequest,
                buyPlanId,role,baseOIDetailsResponse,requestData,FractalFashion);

    }


    @Test(dataProvider = "errorDataForBuyPlanHeaderUpdate", dataProviderClass = buyingandplanningDP.class,groups = {"newTestCases"})
    public void UpdateBuyPlanHeaderErrorValidationForVendor(String updatePayload, Integer expectedErrorCode,
                                                            String expectedErrorMessage) throws Exception {

        String role = Constants.ROLES.UCB_VENDOR_ROLE;
        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");

        //Create OI
        Object[] obj = jeeveshelper.createOI(Constants.DATA_FILES.file1,role);
        JobTrackerResponse jobTrackerResponse = (JobTrackerResponse)obj[0];

        //Get the BuyPlan Id from Job Tracker Response
        String BuyPlanId = jeeveshelper.getBuyPlanIDFromJobTrackerResponse(jobTrackerResponse);

        log.debug("payload: "+updatePayload);

        // updateOI headers
        String update_OI_response = jeeveshelper.updateOIHeaders(BuyPlanId, updatePayload, create_baseoi_headers, role);
        NegativeResponse negativeResponse = (NegativeResponse)APIUtilities.getJsontoObject(update_OI_response,new NegativeResponse());

        Status status = negativeResponse.getStatus();
        jeevesvalidator.validateTheResponseStatus(status, expectedErrorCode, expectedErrorMessage, "ERROR");

    }

    @Test(dataProvider = "update", dataProviderClass = buyingandplanningDP.class,groups = {"newTestCases"})
    public void updateHeaderWithInvalidAccess(BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest, String role, HashMap<String, Object> requestData) throws Exception {
        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");

        //Create OI
        Object[] obj = jeeveshelper.createOI(Constants.DATA_FILES.file1, role);
        JobTrackerResponse jobTrackerResponse = (JobTrackerResponse) obj[0];

        //Get the BuyPlan Id from Job Tracker Response
        String BuyPlanId = jeeveshelper.getBuyPlanIDFromJobTrackerResponse(jobTrackerResponse);

        // updateOI headers
        String updatePayload = APIUtilities.getObjectToJSON(buyPlanHeaderUpdateRequest);

        String newRole= jeeveshelper.changeRole(role);

        String update_OI_response = jeeveshelper.updateOIHeaders(BuyPlanId, updatePayload, create_baseoi_headers, newRole);
        NegativeResponse negativeResponse = (NegativeResponse)APIUtilities.getJsontoObject(update_OI_response,new NegativeResponse());

        Status status = negativeResponse.getStatus();
        jeevesvalidator.validateTheResponseStatus(status, BuyPlanErrorCodes.NO_ACCESS_TO_USER_IN_THIS_STATUS.getStatusCode(),
                BuyPlanErrorCodes.NO_ACCESS_TO_USER_IN_THIS_STATUS.getStatusMessage(), "ERROR");

    }

    @Test(dataProvider = "update", dataProviderClass = buyingandplanningDP.class, groups = {"newTestCases"})
    public void BulkUpdate(BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest, String role, HashMap<String, Object> requestData) throws Exception {
        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");
        HashMap<String, String> download_baseoi_headers = jeeveshelper.initializeHeader(role, "downloadfile");
        HashMap<String, String> upload_baseoi_headers = jeeveshelper.initializeHeader(role, "uploadfile");
        Constants.OIHeaderBATA oiheader1 = new Constants.OIHeaderBATA();
        String payload = baseOI.createOIPayload(oiheader1, BuyPlanType.MMB.getType());

        //Create OI
        Object[] obj = jeeveshelper.createOI(payload,Constants.DATA_FILES.skuLookupFile251, role);
        JobTrackerResponse jobTrackerResponse = (JobTrackerResponse) obj[0];

        //Get the BuyPlanId from Job Tracker Response
        String buyPlanId = jeeveshelper.getBuyPlanIDFromJobTrackerResponse(jobTrackerResponse);

        //Search for the BuyPlan once job is Completed
        String OI_Search_Deatils = jeeveshelper.searchBuyPlan(buyPlanId, create_baseoi_headers, role);
        BaseOIDetailsResponse baseOIDetailsResponse = (BaseOIDetailsResponse) APIUtilities.
                getJsontoObject(OI_Search_Deatils, new BaseOIDetailsResponse());
        jeevesvalidator.validateTheVendorTermsValidationStatus(baseOIDetailsResponse, VendorTermsValidationStatus.PENDING);

        //update OI headers
        jeeveshelper.updateHeaderSuccessfullyAndValidate(buyPlanHeaderUpdateRequest,buyPlanId,role,
                baseOIDetailsResponse,requestData,oiheader1);

        //Download OI sheet
        jeeveshelper.downloadOI(buyPlanId, download_baseoi_headers, role, Constants.DATA_FILES.downloaded_file);

        //Update OI by uploading a modified OI sheet
        jeeveshelper.updateOIValidateTheResponse(upload_baseoi_headers, buyPlanId, role);
    }


    @Test(groups = {"newTestCases"})
    public void updateBuyPlanHeaderForOICreatedByVendor() throws Exception {

        Constants.OIHeaderUCB oiheader1 = new Constants.OIHeaderUCB();
        String role = Constants.ROLES.UCB_VENDOR_ROLE;

        Object[] object = BaseOrderIndentJson.updateHeaderPayloadForVendor(oiheader1);
        BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest = (BuyPlanHeaderUpdateRequest) object[0];
        HashMap<String, Object> requestData = (HashMap<String, Object>) object[1];


        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");

        //Create OI by vendor
        Object[] obj = jeeveshelper.createOI(Constants.DATA_FILES.file1, role);
        JobTrackerResponse jobTrackerResponse = (JobTrackerResponse) obj[0];

        //Get the BuyPlan Id from Job Tracker Response
        String buyPlanId = jeeveshelper.getBuyPlanIDFromJobTrackerResponse(jobTrackerResponse);

        //Search for the Buy Plan once job is Completed
        String OI_Search_Deatils = jeeveshelper.searchBuyPlan(buyPlanId, create_baseoi_headers, role);
        BaseOIDetailsResponse baseOIDetailsResponse = (BaseOIDetailsResponse) APIUtilities.
                getJsontoObject(OI_Search_Deatils, new BaseOIDetailsResponse());
        jeevesvalidator.validateTheVendorTermsValidationStatus(baseOIDetailsResponse, VendorTermsValidationStatus.PENDING);

        //update OI headers By vendor
        jeeveshelper.updateHeaderSuccessfullyAndValidate(buyPlanHeaderUpdateRequest,buyPlanId,
                role,baseOIDetailsResponse,requestData,oiheader1);

        //Share the OI to CM
        jeeveshelper.shareAndValidateTheStatusChange(buyPlanId,role,oiheader1);

        object = BaseOrderIndentJson.updateHeaderPayloadForCM(oiheader1);
        buyPlanHeaderUpdateRequest = (BuyPlanHeaderUpdateRequest) object[0];
        requestData = (HashMap<String, Object>) object[1];

        role = Constants.ROLES.CM_ROLE;

        //Update OI by CM
        jeeveshelper.updateHeaderSuccessfullyAndValidate(buyPlanHeaderUpdateRequest,buyPlanId,
                role,baseOIDetailsResponse,requestData,oiheader1);


    }

    @Test(groups = {"newTestCases"})
    public void VendorTermsForFailure() throws Exception {

        Constants.OIHeaderUCB oiheader1 = new Constants.OIHeaderUCB();
        String payload = baseOI.createOIPayload(oiheader1);

        String role = Constants.ROLES.UCB_VENDOR_ROLE;

        Object[] object = BaseOrderIndentJson.updateHeaderPayloadForVendor(oiheader1);
        BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest = (BuyPlanHeaderUpdateRequest) object[0];
        HashMap<String, Object> requestData = (HashMap<String, Object>) object[1];


        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");

        //Create OI by vendor
        Object[] obj = jeeveshelper.createOI(payload,Constants.DATA_FILES.file3, role);
        JobTrackerResponse jobTrackerResponse = (JobTrackerResponse) obj[0];

        //Get the BuyPlan Id from Job Tracker Response
        String buyPlanId = jeeveshelper.getBuyPlanIDFromJobTrackerResponse(jobTrackerResponse);

        //Search for the Buy Plan once job is Completed
        String OI_Search_Deatils = jeeveshelper.searchBuyPlan(buyPlanId, create_baseoi_headers, role);
        BaseOIDetailsResponse baseOIDetailsResponse = (BaseOIDetailsResponse) APIUtilities.
                getJsontoObject(OI_Search_Deatils, new BaseOIDetailsResponse());
        jeevesvalidator.validateTheVendorTermsValidationStatus(baseOIDetailsResponse, VendorTermsValidationStatus.PENDING);

        //update OI headers By vendor
        jeeveshelper.updateHeaderSuccessfullyAndValidate(buyPlanHeaderUpdateRequest,buyPlanId,
                role,baseOIDetailsResponse,requestData,oiheader1);

        //Share the OI to CM
        jeeveshelper.shareAndValidateTheStatusChange(buyPlanId,role,oiheader1);

        object = BaseOrderIndentJson.updateHeaderPayloadForCM(oiheader1);
        buyPlanHeaderUpdateRequest = (BuyPlanHeaderUpdateRequest) object[0];
        requestData = (HashMap<String, Object>) object[1];

        role = Constants.ROLES.CM_ROLE;

        //Update OI by CM
        jeeveshelper.updateHeaderAndValidateResponse(buyPlanHeaderUpdateRequest,buyPlanId,role,
                requestData,VendorTermsValidationStatus.FAILURE,oiheader1);

        // create PI
        String piResponse = jeeveshelper.createPI(buyPlanId, create_baseoi_headers);
        log.debug(piResponse);
        NegativeResponse negativeResponse = (NegativeResponse) APIUtilities.
                getJsontoObject(piResponse,new NegativeResponse());

        jeevesvalidator.validateTheResponseStatus(negativeResponse.getStatus(),
                BuyPlanErrorCodes.VENDOR_TERMS_VALIDATION_NOT_DONE.getStatusCode(),
                BuyPlanErrorCodes.VENDOR_TERMS_VALIDATION_NOT_DONE.getStatusMessage(),"ERROR");


    }

    @Test(groups = {"newTestCases"})
    public void VendorTermsForAbsent() throws Exception {

        Constants.OIHeaderUCB oiheader1 = new Constants.OIHeaderUCB();
        String payload = baseOI.createOIPayload(oiheader1);

        String role = Constants.ROLES.UCB_VENDOR_ROLE;
        HashMap<String, String> download_baseoi_headers = jeeveshelper.initializeHeader(role, "downloadfile");

        Object[] object = BaseOrderIndentJson.updateHeaderPayloadForVendor(oiheader1);
        BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest = (BuyPlanHeaderUpdateRequest) object[0];
        HashMap<String, Object> requestData = (HashMap<String, Object>) object[1];


        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");

        //Create OI by vendor
        Object[] obj = jeeveshelper.createOI(payload,Constants.DATA_FILES.file3, role);
        JobTrackerResponse jobTrackerResponse = (JobTrackerResponse) obj[0];

        //Get the BuyPlan Id from Job Tracker Response
        String buyPlanId = jeeveshelper.getBuyPlanIDFromJobTrackerResponse(jobTrackerResponse);

        //Search for the Buy Plan once job is Completed
        String OI_Search_Deatils = jeeveshelper.searchBuyPlan(buyPlanId, create_baseoi_headers, role);
        BaseOIDetailsResponse baseOIDetailsResponse = (BaseOIDetailsResponse) APIUtilities.
                getJsontoObject(OI_Search_Deatils, new BaseOIDetailsResponse());
        jeevesvalidator.validateTheVendorTermsValidationStatus(baseOIDetailsResponse, VendorTermsValidationStatus.PENDING);

        //update OI headers By vendor
        jeeveshelper.updateHeaderSuccessfullyAndValidate(buyPlanHeaderUpdateRequest,buyPlanId,
                role,baseOIDetailsResponse,requestData,oiheader1);

        //Share the OI to CM
        jeeveshelper.shareAndValidateTheStatusChange(buyPlanId,role,oiheader1);

        object = BaseOrderIndentJson.updateHeaderPayloadForCM(oiheader1);
        buyPlanHeaderUpdateRequest = (BuyPlanHeaderUpdateRequest) object[0];
        buyPlanHeaderUpdateRequest.setTermsAdditionalClassification(11l);
        requestData = (HashMap<String, Object>) object[1];

        role = Constants.ROLES.CM_ROLE;

        //Update OI by CM
        jeeveshelper.updateHeaderAndValidateResponse(buyPlanHeaderUpdateRequest,buyPlanId,role,
                requestData,VendorTermsValidationStatus.ABSENT,oiheader1);

        //Download OI sheet
        jeeveshelper.downloadOI(buyPlanId, download_baseoi_headers, role, Constants.DATA_FILES.downloaded_file);

        jeevesvalidator.validateTheFileData(Constants.DATA_FILES.downloaded_file,
                BuyPlanErrorCodes.TERMS_NOT_FOUND.getStatusMessage(),
                2,48);

        // create PI
        String piResponse = jeeveshelper.createPI(buyPlanId, create_baseoi_headers);
        log.debug(piResponse);
        NegativeResponse negativeResponse = (NegativeResponse) APIUtilities.
                getJsontoObject(piResponse,new NegativeResponse());

        jeevesvalidator.validateTheResponseStatus(negativeResponse.getStatus(),
                BuyPlanErrorCodes.VENDOR_TERMS_VALIDATION_NOT_DONE.getStatusCode(),
                BuyPlanErrorCodes.VENDOR_TERMS_VALIDATION_NOT_DONE.getStatusMessage(),"ERROR");


    }

    @Test(dataProvider = "update", dataProviderClass = buyingandplanningDP.class, groups = {"newTestCases"})
    public void CreatePI(BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest, String role, HashMap<String, Object> requestData) throws Exception {
        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");
        HashMap<String, String> download_baseoi_headers = jeeveshelper.initializeHeader(role, "downloadfile");
        HashMap<String, String> upload_baseoi_headers = jeeveshelper.initializeHeader(role, "uploadfile");

        Constants.OIHeaderBATA oiheader1 = new Constants.OIHeaderBATA();
        String payload = baseOI.createOIPayload(oiheader1, BuyPlanType.MMB.getType());

        //Create OI
        Object[] object = jeeveshelper.createOI(payload,Constants.DATA_FILES.skuLookupFile251, role);
        JobTrackerResponse jobTrackerResponse = (JobTrackerResponse) object[0];

        //Get the BuyPlanId from Job Tracker Response
        String buyPlanId = jeeveshelper.getBuyPlanIDFromJobTrackerResponse(jobTrackerResponse);

        //Search for the BuyPlan once job is Completed
        String OI_Search_Deatils = jeeveshelper.searchBuyPlan(buyPlanId, create_baseoi_headers, role);
        BaseOIDetailsResponse baseOIDetailsResponse = (BaseOIDetailsResponse) APIUtilities.
                getJsontoObject(OI_Search_Deatils, new BaseOIDetailsResponse());
        jeevesvalidator.validateTheVendorTermsValidationStatus(baseOIDetailsResponse, VendorTermsValidationStatus.PENDING);

        //update OI headers
        jeeveshelper.updateHeaderSuccessfullyAndValidate(buyPlanHeaderUpdateRequest,buyPlanId,
                role,baseOIDetailsResponse,requestData,oiheader1);

        //Download OI sheet
        jeeveshelper.downloadOI(buyPlanId, download_baseoi_headers, role, Constants.DATA_FILES.downloaded_file);

        //Update OI by uploading a modified OI sheet
        jeeveshelper.updateOIValidateTheResponse(upload_baseoi_headers, buyPlanId, role);

        //When the role is vendor
        if (role.contains("vendor")) {
            //Share the OI to CM
            jeeveshelper.shareAndValidateTheStatusChange(buyPlanId,role,oiheader1);

            object = BaseOrderIndentJson.updateHeaderPayloadForCM(oiheader1);
            buyPlanHeaderUpdateRequest = (BuyPlanHeaderUpdateRequest) object[0];
            requestData = (HashMap<String, Object>) object[1];

            role = Constants.ROLES.CM_ROLE;

            //Update OI by CM
            jeeveshelper.updateHeaderSuccessfullyAndValidate(buyPlanHeaderUpdateRequest,buyPlanId,
                    role,baseOIDetailsResponse,requestData,oiheader1);
        }
        else
        {
            //Upload Attribute sheet
            String uploadAttributeResponse = jeeveshelper.uploadAttributeSheet(Constants.DATA_FILES.attributeSheet,
                    upload_baseoi_headers, buyPlanId, role);
            BaseOIDetailsResponse baseOIDetailsResponse1 = (BaseOIDetailsResponse) APIUtilities.getJsontoObject
                    (uploadAttributeResponse,new BaseOIDetailsResponse());
            String jobId = String.valueOf(baseOIDetailsResponse1.getData()[0].getJobId());
            jeeveshelper.checkOIStatusInJobTracker(jobId, create_baseoi_headers);
        }

        // create PI
        String piResponse = jeeveshelper.createPI(buyPlanId, create_baseoi_headers);
        log.debug(piResponse);
        jeevesvalidator.validateTheResponseStatus(piResponse, "CREATE_PI");

        // Try to create PI again for same BuyPlan
        piResponse = jeeveshelper.createPI(buyPlanId, create_baseoi_headers);
        log.debug(piResponse);
        NegativeResponse negativeResponse = (NegativeResponse) APIUtilities.
                getJsontoObject(piResponse,new NegativeResponse());

        jeevesvalidator.validateTheResponseStatus(negativeResponse.getStatus(),
                BuyPlanErrorCodes.TRANSITION_NOT_ALLOWED.getStatusCode(),
                BuyPlanErrorCodes.TRANSITION_NOT_ALLOWED.getStatusMessage(),"ERROR");


    }

    @Test(dataProvider = "updateMFB", dataProviderClass = buyingandplanningDP.class,groups = {"newTestCases"})
    public void CeatePIForMFB(BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest, String role, HashMap<String, Object> requestData) throws Exception {
        HashMap<String, String> create_baseoi_headers = jeeveshelper.initializeHeader(role, "create");

        Constants.OIHeaderFF FractalFashion=new Constants.OIHeaderFF();
        String payload = baseOI.createOIPayload(FractalFashion,BuyPlanType.MFB.getType());

        //Create OI
        Object[] obj = jeeveshelper.createOI(payload,Constants.DATA_FILES.file7, role);
        JobTrackerResponse jobTrackerResponse = (JobTrackerResponse) obj[0];

        //Get the BuyPlan Id from Job Tracker Response
        String buyPlanId = jeeveshelper.getBuyPlanIDFromJobTrackerResponse(jobTrackerResponse);

        //Search for the Buy Plan once job is Completed
        String OI_Search_Deatils = jeeveshelper.searchBuyPlan(buyPlanId, create_baseoi_headers, role);
        BaseOIDetailsResponse baseOIDetailsResponse = (BaseOIDetailsResponse) APIUtilities.
                getJsontoObject(OI_Search_Deatils, new BaseOIDetailsResponse());
        jeevesvalidator.validateTheVendorTermsValidationStatus(baseOIDetailsResponse, VendorTermsValidationStatus.PENDING);

        //update OI headers
        jeeveshelper.updateHeaderSuccessfullyAndValidate(buyPlanHeaderUpdateRequest,
                buyPlanId,role,baseOIDetailsResponse,requestData,FractalFashion);

        // create PI
        String piResponse = jeeveshelper.createPI(buyPlanId, create_baseoi_headers);
        log.debug(piResponse);
        jeevesvalidator.validateTheResponseStatus(piResponse, "CREATE_PI");

        // Try to create PI again for same BuyPlan
        piResponse = jeeveshelper.createPI(buyPlanId, create_baseoi_headers);
        log.debug(piResponse);
        NegativeResponse negativeResponse = (NegativeResponse) APIUtilities.
                getJsontoObject(piResponse,new NegativeResponse());

        jeevesvalidator.validateTheResponseStatus(negativeResponse.getStatus(),
                BuyPlanErrorCodes.TRANSITION_NOT_ALLOWED.getStatusCode(),
                BuyPlanErrorCodes.TRANSITION_NOT_ALLOWED.getStatusMessage(),"ERROR");

    }

}
