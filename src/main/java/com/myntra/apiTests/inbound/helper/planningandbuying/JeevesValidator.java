package com.myntra.apiTests.inbound.helper.planningandbuying;

import com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses.*;
import com.myntra.filter.client.entry.Attribute;
import com.myntra.filter.client.entry.SearchExpression;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.orderindent.client.codes.BaseOrderIndentSuccessCodes;
import com.myntra.orderindent.client.codes.BuyPlanErrorCodes;
import com.myntra.orderindent.client.codes.BuyPlanSuccessCodes;
import com.myntra.orderindent.enums.BuyPlanStatus;
import com.myntra.orderindent.enums.BuyPlanType;
import com.myntra.orderindent.enums.VendorTermsValidationStatus;
import com.myntra.tenant.Response.TenantResponse;
import com.myntra.tenant.client.entry.RConfigMetadata;
import com.myntra.tenant.client.entry.TenantEntry;
import com.myntra.tenant.client.entry.WorkflowMetadata;
import com.myntra.tenant.client.entry.WorkspaceMetadata;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class JeevesValidator {

    private JeevesHelper jeeveshelper = new JeevesHelper();
    private APIUtilities apiUtilities;
    Logger log = Logger.getLogger(JeevesValidator.class);

    /**
     * This method validate the success response for each of the service
     *
     * @param response
     * @param type
     * @throws Exception
     */
    public void validateTheResponseStatus(String response, String type) throws Exception {
        String expectedStatusCode;
        String expectedStatusMessage;
        String expectedStatusType = "SUCCESS";
        String failureMessage;

        switch (type) {
            case "CREATE_OI":
                expectedStatusCode = Constants.STATUS_CODES.CREATE_OI;
                expectedStatusMessage = Constants.STATUS_MESSAGES.CREATE_OI;
                failureMessage = "Create OI failed. The expected ";
                break;

            case "UPLOAD_OI":
                expectedStatusCode = Constants.STATUS_CODES.UPLOAD_OI;
                expectedStatusMessage = Constants.STATUS_MESSAGES.UPLOAD_OI;
                failureMessage = "Upload OI failed. The expected ";
                break;

            case "UPDATE_OI_HEADERS":
                expectedStatusCode = Constants.STATUS_CODES.UPDATE_OI_HEADERS;
                expectedStatusMessage = Constants.STATUS_MESSAGES.UPDATE_OI_HEADERS;
                failureMessage = "Upload OI headers failed. The expected ";
                break;

            case "CREATE_PI":
                expectedStatusCode = Constants.STATUS_CODES.CREATE_PI;
                expectedStatusMessage = Constants.STATUS_MESSAGES.CREATE_PI;
                failureMessage = "Create PI failed. The expected ";
                break;

            case "UPDATE_OI":
                expectedStatusCode = Constants.STATUS_CODES.UPDATE_OI;
                expectedStatusMessage = Constants.STATUS_MESSAGES.UPDATE_OI;
                failureMessage = "Update OI failed. The expected";
                break;

            case "UPLOAD_TSR_RCONFIG":
                expectedStatusCode = Constants.STATUS_CODES.UPLOAD_TSR_RCONFIG;
                expectedStatusMessage = Constants.STATUS_MESSAGES.UPLOAD_TSR_RCONFIG;
                failureMessage = "Upload tsr rconfig failed. The expected";
                break;

            case "GENERATE_OI":
                expectedStatusCode = Constants.STATUS_CODES.GENERATE_OI;
                expectedStatusMessage = Constants.STATUS_MESSAGES.GENERATE_OI;
                failureMessage = "GENERATE_OI failed. The expected";
                break;

            case "JOB_TRACKER":
                expectedStatusCode = Constants.STATUS_CODES.JOB_TRACKER;
                expectedStatusMessage = Constants.STATUS_MESSAGES.JOB_TRACKER;
                failureMessage = "Job Tracker failed. The expected";
                break;


            default:
                return;
        }

        String actualStatusCode = jeeveshelper.getStatusType(response, "statusCode");
        String actualStatusMessage = jeeveshelper.getStatusType(response, "statusMessage");
        String actualStatusType = jeeveshelper.getStatusType(response, "statusType");

        Assert.assertEquals(actualStatusCode, expectedStatusCode, failureMessage + "status code is" + expectedStatusCode
                + " ,but actual status code is " + actualStatusCode);
        Assert.assertEquals(actualStatusMessage, expectedStatusMessage, failureMessage + "status message is"
                + expectedStatusMessage + " ,but actual status message is " + actualStatusMessage);
        Assert.assertEquals(actualStatusType, expectedStatusType, failureMessage + "status type is"
                + expectedStatusMessage + " ,but actual status type is " + actualStatusMessage);
    }

    public void validateTheResponseStatus(Status response, String type) throws Exception {
        int expectedStatusCode;
        String expectedStatusMessage;
        String expectedStatusType = "SUCCESS";
        String failureMessage;

        switch (type) {
            case "CREATE_OI":
                expectedStatusCode = BaseOrderIndentSuccessCodes.BASE_ORDER_INDENT_ADDED_SUCCESSFULLY.getStatusCode();
                expectedStatusMessage = BaseOrderIndentSuccessCodes.BASE_ORDER_INDENT_ADDED_SUCCESSFULLY.getStatusMessage();
                failureMessage = "Create OI failed. The expected ";
                break;

            case "UPLOAD_OI":
                expectedStatusCode = BaseOrderIndentSuccessCodes.BASE_OI_SHEET_UPLOADED_SUCCESSFULLY.getStatusCode();
                expectedStatusMessage = BaseOrderIndentSuccessCodes.BASE_OI_SHEET_UPLOADED_SUCCESSFULLY.getStatusMessage();
                failureMessage = "Upload OI failed. The expected ";
                break;

            case "UPDATE_OI_HEADERS":
                expectedStatusCode = BuyPlanSuccessCodes.BUY_PLAN_UPDATED_SUCCESSFULLY.getStatusCode();
                expectedStatusMessage = BuyPlanSuccessCodes.BUY_PLAN_UPDATED_SUCCESSFULLY.getStatusMessage();
                failureMessage = "Upload OI headers failed. The expected ";
                break;

            case "UPDATE_OI":
                expectedStatusCode = BuyPlanSuccessCodes.BUY_PLAN_SHEET_UPLOADED_SUCCESSFULLY.getStatusCode();
                expectedStatusMessage = BuyPlanSuccessCodes.BUY_PLAN_SHEET_UPLOADED_SUCCESSFULLY.getStatusMessage();
                failureMessage = "Update OI failed. The expected";
                break;

            case "SEARCH_OI":
            case "CREATE_PI":
                expectedStatusCode = BuyPlanSuccessCodes.BUY_PLAN_FETCHED_SUCCESSFULLY.getStatusCode();
                expectedStatusMessage = BuyPlanSuccessCodes.BUY_PLAN_FETCHED_SUCCESSFULLY.getStatusMessage();
                failureMessage = "Search Failed. The expected";
                break;

            case "UPLOAD_TSR_RCONFIG":
                expectedStatusCode = Integer.parseInt(Constants.STATUS_CODES.UPLOAD_TSR_RCONFIG);
                expectedStatusMessage = Constants.STATUS_MESSAGES.UPLOAD_TSR_RCONFIG;
                failureMessage = "Upload tsr rconfig failed. The expected";
                break;

            case "GENERATE_OI":
                expectedStatusCode = Integer.parseInt(Constants.STATUS_CODES.GENERATE_OI);
                expectedStatusMessage = Constants.STATUS_MESSAGES.GENERATE_OI;
                failureMessage = "GENERATE_OI failed. The expected";
                break;

            case "JOB_TRACKER":
                expectedStatusCode = Integer.parseInt(Constants.STATUS_CODES.JOB_TRACKER);
                expectedStatusMessage = Constants.STATUS_MESSAGES.JOB_TRACKER;
                failureMessage = "Job Tracker failed. The expected";
                break;


            default:
                return;
        }

        Integer actualStatusCode = response.getStatusCode();
        String actualStatusMessage = response.getStatusMessage();
        String actualStatusType = response.getStatusType();

        Assert.assertTrue(actualStatusCode == expectedStatusCode, failureMessage + "status code is" + expectedStatusCode
                + " ,but actual status code is " + actualStatusCode);
        Assert.assertEquals(actualStatusMessage, expectedStatusMessage, failureMessage + "status message is"
                + expectedStatusMessage + " ,but actual status message is " + actualStatusMessage);
        Assert.assertEquals(actualStatusType, expectedStatusType, failureMessage + "status type is"
                + expectedStatusMessage + " ,but actual status type is " + actualStatusMessage);
    }

    public void validateTheResponseStatus(Status response, Integer expectedStatusCode,
                                          String expectedStatusMessage, String expectedStatusType) throws Exception {

        int actualStatusCode = response.getStatusCode();
        String actualStatusMessage = response.getStatusMessage();
        String actualStatusType = response.getStatusType();

        Assert.assertEquals(actualStatusMessage, expectedStatusMessage, "Expected status message is"
                + expectedStatusMessage + " ,but actual status message is " + actualStatusMessage);
        Assert.assertTrue(actualStatusCode==expectedStatusCode,  " Expected status code is " + expectedStatusCode
                + " ,but actual status code is " + actualStatusCode);
        Assert.assertEquals(actualStatusType, expectedStatusType, "Expected status type is"
                + expectedStatusMessage + " ,but actual status type is " + actualStatusMessage);

    }

    /**
     * This method validate if SKU code is present in the download file
     *
     * @param rowNumber
     * @param colNumber
     * @param totalNoOfRows
     */
    public void validateSkucodesArePresent(int rowNumber, int colNumber, int totalNoOfRows) {
        Assert.assertTrue(
                jeeveshelper.checkForEmptyCell(Constants.DATA_FILES.downloaded_file, rowNumber, colNumber, totalNoOfRows),
                "skucode is missing in downloaded  OI sheet");
    }

    /**
     * This method validates the error response in the error file
     *
     * @param actualErrorMessage
     * @param expectedErrorMessage
     */
    public void validateErrorFile(String actualErrorMessage, String expectedErrorMessage) {
        if (!actualErrorMessage.contains(expectedErrorMessage)) {
            Assert.fail("Error Message Saying: " + actualErrorMessage + "was not displayed");
        }
    }

    /**
     * This method is to validate the object from the response string
     *
     * @param response
     * @param object
     */
    public void validateObjectFromTheResponse(String response, String object) {
        Assert.assertEquals(response, object);
    }

    /**
     * this method validates data from the excel file
     *
     * @param file
     * @param data
     * @param row
     * @param col
     * @param errMsg
     */
    public void validateTheFileData(String file, String data, int row, int col, String errMsg) {
        Assert.assertEquals(jeeveshelper.readXLSXFile(file, row, col), data, errMsg);
    }

    public void validateTheFileData(String file, String data, int row, int col) {
        Assert.assertTrue(jeeveshelper.readXLSXFile(file, row, col).contains(data));
    }

    /**
     * This method validates the length of the array object.
     *
     * @param bucket
     * @param errMsg
     */
    public void validateResponseObjectArrayLength(JSONArray bucket, String errMsg) {
        if (bucket.length() == 0) {
            Assert.fail(errMsg);
        }
    }

    /**
     * This method validates the cells are empty or not
     *
     * @param file
     * @param row
     * @param col
     * @param rows_length
     */
    public void validateCellsAreNotEmpty(String file, int row, int col, int rows_length) {
        Assert.assertTrue(jeeveshelper.checkForEmptyCell(file, row, col, rows_length),
                "cell is empty");
    }

    /**
     * This method is to validate the initial OI Status Based on Role
     *
     * @param status
     * @param role
     */
    public void validateTheOIStatusBasedOnRole(BuyPlanStatus status, String role) {
        if (role.contains("cm")) {
            Assert.assertEquals(status.getType(), BuyPlanStatus.DRAFT.getType());
        } else {
            Assert.assertEquals(status.getType(), BuyPlanStatus.VENDOR_DRAFT.getType());
        }
    }

    /**
     * This Methos Validate the Job Tracker Response
     *
     * @param jobTrackerResponse
     * @param role
     */
    public void validateTheJobTrackerResponse(JobTrackerResponse jobTrackerResponse, String role) throws Exception {
        JobTrackerResponseData jobTrackerResponseData = jobTrackerResponse.getData()[0];
        String status = jobTrackerResponseData.getStatus();
        Integer totalStepCount = jobTrackerResponseData.getTotalStepCount();
        Integer completedStepCount = jobTrackerResponseData.getCompletedStepCount();

        validateTheResponseStatus(jobTrackerResponse.getStatus(), "JOB_TRACKER");

        Assert.assertTrue(totalStepCount == completedStepCount);
        Assert.assertEquals(status, "COMPLETED");
        validateTheCreatedByField(jobTrackerResponseData.getCreatedBy(), role);
    }

    /**
     * This method Validates the created By fields in the response
     *
     * @param createdBy
     * @param role
     */
    public void validateTheCreatedByField(String createdBy, String role) {
        if (role.contains("cm")) {
            Assert.assertEquals(createdBy, "dummyuserid");
        } else {
            Assert.assertEquals(createdBy, "benetton");
        }
    }

    /**
     * This method validate sthe Base Oi Header Response
     *
     * @param baseOIHeaderRequest
     * @param baseOIHeaderResponse
     * @param role
     * @throws Exception
     */
    public void validateBaseOIHeaderResponse(BaseOIHeaderRequest baseOIHeaderRequest,
                                             BaseOIHeaderResponse baseOIHeaderResponse, String role)
            throws Exception {

        BaseOIHeaderResponseData baseOIHeaderResponseData = baseOIHeaderResponse.getData()[0];
        baseOIHeaderRequest.setAllValues();
        baseOIHeaderResponseData.setAllValues();

        validateTheResponseStatus(baseOIHeaderResponse.getStatus(), "CREATE_OI");
        validateTheCreatedByField(baseOIHeaderResponseData.getCreatedBy(), role);
        Assert.assertTrue(baseOIHeaderResponseData.getWarningCount() == 0);
        for (String str : baseOIHeaderRequest.getAllAttributes()) {
            Assert.assertEquals(baseOIHeaderRequest.getValue(str), baseOIHeaderResponseData.getValue(str));
        }
    }

    /**
     * This Method Validates The Create OI Response
     *
     * @param baseOIHeaderRequest
     * @param createOIResponse
     * @param role
     * @throws Exception
     */
    public void validateTheCreateOIResponse(BaseOIHeaderRequest baseOIHeaderRequest,
                                            CreateOIResponse createOIResponse, String role)
            throws Exception {
        CreateOIResponseDate createOIResponseData = createOIResponse.getData()[0];
        createOIResponseData.setAllValues();
        validateTheResponseStatus(createOIResponse.getStatus(), "UPLOAD_OI");
        validateTheCreatedByField(createOIResponseData.getCreatedBy(), role);
        for (String str : baseOIHeaderRequest.getAllAttributes()) {
            Assert.assertEquals(baseOIHeaderRequest.getValue(str), createOIResponseData.getValue(str));
        }

    }

    public void validateBuyPlanSearchResponse(BaseOIHeaderRequest baseOIHeaderRequest,
                                              BaseOIDetailsResponse baseOIDetailsResponse, String role) throws Exception {
        BaseOIDetailsResponseData baseOIDetailsResponseData = baseOIDetailsResponse.getData()[0];
        baseOIDetailsResponseData.setAllValues();
        validateTheResponseStatus(baseOIDetailsResponse.getStatus(), "SEARCH_OI");
        validateTheCreatedByField(baseOIDetailsResponseData.getCreatedBy(), role);
        for (String str : baseOIHeaderRequest.getAllAttributes()) {
            switch (str) {
                case "orderIndentSource":
                    Assert.assertEquals(baseOIHeaderRequest.getValue(str), baseOIDetailsResponseData.getValue("source"));
                    break;
                case "orderIndentOrderType":
                    Assert.assertEquals(baseOIHeaderRequest.getValue(str), baseOIDetailsResponseData.getValue("buyPlanOrderType"));
                    break;
                case "vendorEmail":
                    Assert.assertEquals(baseOIHeaderRequest.getValue(str), baseOIDetailsResponseData.getValue("toMail"));
                    break;
                case "season":
                    Assert.assertEquals(baseOIHeaderRequest.getValue(str), baseOIDetailsResponseData.getValue("seasonId"));
                    break;
                case "vendorWarehouseLocation":
                    Assert.assertEquals(baseOIHeaderRequest.getValue(str), baseOIDetailsResponseData.getValue("vendorStateCode"));
                    break;
                default:
                    Assert.assertEquals(baseOIHeaderRequest.getValue(str), baseOIDetailsResponseData.getValue(str));
            }
        }
        validateTheOIStatusBasedOnRole(baseOIDetailsResponseData.getStatus(), role);
    }


    public void validateTheJobTrackerResponseForFailureScenario(JobTrackerResponse jobTrackerResponse, String base_oi_id,String errorMsg) throws Exception {
        JobTrackerResponseData jobTrackerResponseData = jobTrackerResponse.getData()[0];

        validateTheResponseStatus(jobTrackerResponse.getStatus(), "JOB_TRACKER");

        Assert.assertEquals(jobTrackerResponseData.getStatus(), "INTERRUPTED");
        Assert.assertEquals(jobTrackerResponseData.getRemark(), errorMsg);
        Assert.assertEquals(jobTrackerResponseData.getQualifier(), base_oi_id);
    }

    public void validateInvalidUserAccess(int actualStatusCode, String actualStatusMessage, String role) {
        int expectedStatusCode = 0;
        String expectedStatusMessage = "";
        if (role.contains("cm")) {
            expectedStatusCode = BuyPlanErrorCodes.NO_ACCESS_TO_USER_IN_THIS_STATUS.getStatusCode();
            expectedStatusMessage = BuyPlanErrorCodes.NO_ACCESS_TO_USER_IN_THIS_STATUS.getStatusMessage();

        } else if (role.contains("vendor")) {
            expectedStatusCode = BuyPlanErrorCodes.VENDOR_DOES_NOT_HAVE_ACCESS_TO_THIS_BUY_PLAN.getStatusCode();
            expectedStatusMessage = BuyPlanErrorCodes.VENDOR_DOES_NOT_HAVE_ACCESS_TO_THIS_BUY_PLAN.getStatusMessage();
        }

        Assert.assertTrue(actualStatusCode == expectedStatusCode, "Expected status code was " + expectedStatusCode + " but actual status code is " + actualStatusCode);
        Assert.assertEquals(actualStatusMessage, expectedStatusMessage, "Expected status Message was " + expectedStatusMessage + " but actual status message is " + actualStatusMessage);
    }

    public void validateTheStatusChangeAfterShare(String shareResponse, String role) throws IOException {
        BaseOIDetailsResponse baseOIDetailsResponse = (BaseOIDetailsResponse) apiUtilities.getJsontoObject(shareResponse, new BaseOIDetailsResponse());
        BaseOIDetailsResponseData baseOIDetailsResponseData = baseOIDetailsResponse.getData()[0];
        if (role.contains("cm")) {
            Assert.assertEquals(baseOIDetailsResponseData.getStatus().getType(), BuyPlanStatus.PENDING_WITH_PARTNER.getType());
        } else if (role.contains("vendor")) {
            Assert.assertEquals(baseOIDetailsResponseData.getStatus().getType(), BuyPlanStatus.PENDING_WITH_MYNTRA.getType());
        }
    }

    public void validateTemplateHeaders(ArrayList<String> headers, String buyPlanType) {
        switch (buyPlanType) {
            case "MMB":
                for (String str : Constants.TEMPLATE_HEADERS.MMB) {
                    if (!headers.contains(str)) {
                        Assert.fail("The Header " + str + " is not present in the excel");
                    }
                }
                break;
            case "MFB":
                for (String str : Constants.TEMPLATE_HEADERS.MFB) {
                    if (!headers.contains(str)) {
                        Assert.fail("The Header " + str + " is not present in the excel");
                    }
                }
                break;
            default:
                Assert.fail("Invalid BuyPlan Type" + buyPlanType);
        }
    }

    public void validateTemplateFilterData(String downloadFile, String buyPlanType, HashMap<String, String> fileValidator) {
        switch (buyPlanType) {
            case "MMB":
                for (String str : Constants.TEMPLATE_HEADERS.columnsForMMB) {
                    ArrayList<String> columnValues = jeeveshelper.getColumnValueBasedOnColumnName(downloadFile, str);
                    Assert.assertTrue((columnValues.size()+1) == Integer.parseInt(fileValidator.get(str)),
                            "The Data Range filter for the column " + str + " should be appiled from 2 to " + (columnValues.size()+1)
                                    + " but it is applied from 2 to " + fileValidator.get(str));
                }
                break;
            case "MFB":
                for (String str : Constants.TEMPLATE_HEADERS.columnsForMFB) {
                    ArrayList<String> columnValues = jeeveshelper.getColumnValueBasedOnColumnName(downloadFile, str);
                    Assert.assertTrue((columnValues.size()+1) == Integer.parseInt(fileValidator.get(str)),
                            "The Data Range filter for the column " + str + " should be appiled from 2 to " + (columnValues.size()+1)
                                    + " but it is applied from 2 to " + fileValidator.get(str));
                }
                break;
            default:
                Assert.fail("Invalid BuyPlan Type" + buyPlanType);
        }
    }

    public void validateTemplateDataErrorShownInExcel(String fileName) {
        InputStream XlsxFileToRead = null;
        XSSFWorkbook workbook = null;
        try {
            XlsxFileToRead = new FileInputStream(fileName);
            workbook = new XSSFWorkbook(XlsxFileToRead);
            XSSFSheet sheet = workbook.getSheetAt(0);

            List<? extends DataValidation> dataValidations = sheet.getDataValidations();
            for (DataValidation dataValidation : dataValidations) {
                Assert.assertEquals(dataValidation.getErrorBoxText(), Constants.TEMPLATE_HEADERS.excelDataErrorMessage);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void validateTemplateValuesOfMasterSheet(String file, HashMap<String, String> header, String buyPlanType) throws UnsupportedEncodingException, JSONException {
        Set<String> brands = jeeveshelper.getAllBrands(header);
        compareTheServiceAndMasterSheetValue("Brand",file,brands);

        Set<String> articleTypes = jeeveshelper.getAllArticleTypes(header);
        compareTheServiceAndMasterSheetValue("Article Type",file,articleTypes);

        Set<String> genders = jeeveshelper.getAllGenders(header);
        compareTheServiceAndMasterSheetValue("Gender",file,genders);

        Set<String> marginType = jeeveshelper.getAllMarginType(header);
        compareTheServiceAndMasterSheetValue("Margin Type",file,marginType);

        if(buyPlanType.equals(BuyPlanType.MFB.getType())) {
            Set<String> leadTimeCategories = jeeveshelper.getAllLeadTimeCategories(header);
            compareTheServiceAndMasterSheetValue("Lead Time Category", file, leadTimeCategories);
        }

    }

    public void compareTheServiceAndMasterSheetValue(String colName, String file, Set<String> servicedata)
    {
        boolean matched = true;
        ArrayList<String> columnValues = jeeveshelper.getColumnValueBasedOnColumnName(file, colName);
        Assert.assertTrue(servicedata.size()==columnValues.size(),"The total "+colName+" values expected is "
                +servicedata.size()+" but actual is "+columnValues.size());
        for(String str:servicedata)
        {
            if(columnValues.contains(str))
            {
                continue;
            }
            else
            {
                matched = false;
                log.debug("Brand that didn't matched: "+str);
            }
        }
        Assert.assertTrue(matched);
    }

    public void validateTheUpdateHeaderResponse(BaseOIDetailsResponse baseOIDetailsResponse,
                                                HashMap<String, Object> requestData, String role,Object oiheader1) {
        BaseOIDetailsResponseData baseOIDetailsResponseData =baseOIDetailsResponse.getData()[0];
        baseOIDetailsResponseData.setAllValues();

        for(String str: requestData.keySet())
        {
            String actualDate;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            if(baseOIDetailsResponseData.getValue(str) instanceof Date)
            {
                Date actDate = (Date) baseOIDetailsResponseData.getValue(str);
                actualDate =  simpleDateFormat.format(actDate);
                Assert.assertEquals(actualDate, requestData.get(str));
            }
            else {
                Assert.assertEquals(baseOIDetailsResponseData.getValue(str), requestData.get(str));
            }
        }

        if(role.contains("cm")) {

            if(oiheader1 instanceof Constants.OIHeaderUCB)
            {
                Assert.assertEquals(baseOIDetailsResponseData.getCreditBasisAsOn(), "Date of Delivery");
                Assert.assertEquals(baseOIDetailsResponseData.getPaymentTerms(), "Cheque");
            }
            else if (oiheader1 instanceof Constants.OIHeaderBATA)
            {
                Assert.assertEquals(baseOIDetailsResponseData.getCreditBasisAsOn(), "Date of Delivery");
                Assert.assertEquals(baseOIDetailsResponseData.getPaymentTerms(), "Electronic");
            }
            else if(oiheader1 instanceof Constants.OIHeaderFF)
            {
                Assert.assertEquals(baseOIDetailsResponseData.getCreditBasisAsOn(), "Date of GRN");
                Assert.assertEquals(baseOIDetailsResponseData.getPaymentTerms(), "Cheque");
            }

        }
    }

    public void validateTheVendorTermsValidationStatus(BaseOIDetailsResponse baseOIDetailsResponse,
                                                       VendorTermsValidationStatus expectedStatus) {
        BaseOIDetailsResponseData baseOIDetailsResponseData = baseOIDetailsResponse.getData()[0];
        log.debug(baseOIDetailsResponseData.getVendorTermsValidationStatus().getStatus());
        Assert.assertEquals(baseOIDetailsResponseData.getVendorTermsValidationStatus(), expectedStatus);
    }



	public void validatePreviousMRPValuesArePresent(int rowNumber, int colNumber, int totalNoOfRows) throws Exception {
		try {
			Assert.assertTrue(
					jeeveshelper.checkForEmptyCell(Constants.DATA_FILES.downloaded_file, rowNumber, colNumber, totalNoOfRows),
					"Previous MRP Values are missing in downloaded  OI sheet");

		} catch (Exception e) {
			System.out.println("Previous MRP Values are missing in downloaded  OI sheet");
			e.printStackTrace();
			throw new Exception(e);

		}
	}

	public void validateTenantServiceResponse(TenantResponse response) {

	}

	/**
	 * @param arr
	 * @param object
	 * @return
	 */
	public ArrayList<Integer> getArrayObjectsFromResponse(JSONArray arr, String object) throws JSONException {
		ArrayList<Integer> list = new ArrayList<Integer>();
		if (arr.length() != 0) {
			for (int i = 0; i < arr.length(); i++) {
				JSONObject jsonobject = arr.getJSONObject(i);
				list.add(jsonobject.getInt(object));
			}
		} else {
			System.out.println("The Json Array is empty");
		}
		return list;
	}

	/**
	 * @param arr
	 * @param object
	 * @param expectedValue
	 * @param condition
	 */
	public void validateRconfigFilterValues(JSONArray arr, String object, int expectedValue, String condition) throws JSONException {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list = getArrayObjectsFromResponse(arr, object);
		if (!list.isEmpty()) {
			if (condition.equals("MIN")) {
				for (int actualValue : list) {
					Assert.assertTrue(expectedValue <= actualValue, "Values expected is incorrect for: " + object);
				}
			} else if (condition.equals("MAX")) {
				for (int actualValue : list) {
					Assert.assertTrue(expectedValue >= actualValue, "Values expected is incorrect for: " + object);
				}
			}
		} else {
			System.out.println("The list is empty");
		}
	}

	/**
	 * @param tenantEntry
	 * @param tenant
	 */
	public void getTenantEntryAttributes(List<TenantEntry> tenantEntry, String tenant) {
		if (tenant.equalsIgnoreCase(Constants.TENANTS.MMB)) {
			Assert.assertEquals(tenantEntry.get(0).getId(), Constants.TENANTS.MMB, "Incorrect Tenant Id");
			Assert.assertEquals(tenantEntry.get(0).getName(), Constants.TENANT_ATTRIBUTES.TENANT_MMB_NAME, "Incorrect Tenant Name");
			Assert.assertEquals(tenantEntry.get(0).getDescription(), Constants.TENANT_ATTRIBUTES.TENANT_MMB_DESCRIPTION, "Incorrect Tenant Description");
		} else if (tenant.equalsIgnoreCase(Constants.TENANTS.SJIT)) {
			Assert.assertEquals(tenantEntry.get(0).getId(), Constants.TENANTS.SJIT, "Incorrect Tenant Id");
			Assert.assertEquals(tenantEntry.get(0).getName(), Constants.TENANT_ATTRIBUTES.TENANT_SJIT_NAME, "Incorrect Tenant Name");
			Assert.assertEquals(tenantEntry.get(0).getDescription(), Constants.TENANT_ATTRIBUTES.TENANT_SJIT_DESCRIPTION, "Incorrect Tenant Description");
		} else if (tenant.equalsIgnoreCase(Constants.TENANTS.RAPID)) {
			Assert.assertEquals(tenantEntry.get(0).getId(), Constants.TENANTS.RAPID, "Incorrect Tenant Id");
			Assert.assertEquals(tenantEntry.get(0).getName(), Constants.TENANT_ATTRIBUTES.TENANT_RAPID_NAME, "Incorrect Tenant Name");
			Assert.assertEquals(tenantEntry.get(0).getDescription(), Constants.TENANT_ATTRIBUTES.TENANT_RAPID_DESCRIPTION, "Incorrect Tenant Description");
		}
	}

	/**
	 * @param teamDef
	 * @param tenant
	 */
	public void getTenantSearchAttributes(SearchExpression teamDef, String tenant) {
		List<Attribute> searchAttr = teamDef.getSearchAttributes();
		if (tenant.equalsIgnoreCase(Constants.TENANTS.MMB)) {
			Assert.assertEquals(searchAttr.get(0).getName(), Constants.TENANT_ATTRIBUTES.TENANT_DEF_SEARCH_ATTR_BRAND_TYPE,
					"Incorrect brandtype for: " + tenant);
			Assert.assertEquals(searchAttr.get(0).getValue(),
					Constants.TENANT_ATTRIBUTES.MMB_TENANT_DEF_SEARCH_ATTR_BRAND_TYPE_VALUE,
					"Incorrect brandtype value for: " + tenant);
		} else if (tenant.equalsIgnoreCase(Constants.TENANTS.SJIT)) {
			Assert.assertEquals(searchAttr.get(0).getName(), Constants.TENANT_ATTRIBUTES.TENANT_DEF_SEARCH_ATTR_BRAND_TYPE,
					"Incorrect brandtype for: " + tenant);
			Assert.assertEquals(searchAttr.get(0).getValue(),
					Constants.TENANT_ATTRIBUTES.SJIT_TENANT_DEF_SEARCH_ATTR_BRAND_TYPE_VALUE,
					"Incorrect brandtype value for: " + tenant);
		}
	}

	/**
	 * @param teamDef
	 * @param tenant
	 */
	public void getTenantInnerExpression(SearchExpression teamDef, String tenant) {
		SearchExpression innerExpression1 = teamDef.getInnerExpressions().get(0);
		SearchExpression innerExpression2 = teamDef.getInnerExpressions().get(1);
		List<Attribute> InnerExpsearchAttr1 = innerExpression1.getSearchAttributes();
		List<Attribute> InnerExpsearchAttr2 = innerExpression2.getSearchAttributes();
		for (int i = 0; i < InnerExpsearchAttr1.size(); i++) {
			if (tenant.equalsIgnoreCase(Constants.TENANTS.MMB)) {
				Assert.assertEquals(InnerExpsearchAttr1.get(i).getName(),
						Constants.TENANT_ATTRIBUTES.TENANT_DEF_INNER_EXP_COMMERCIAL_TYPE, "Incorrect commercialtype for: " + tenant);
				Assert.assertEquals(InnerExpsearchAttr1.get(i).getValue(),
						Constants.TENANT_ATTRIBUTES.MMB_TENANT_DEF_INNER_EXP_COMMERCIAL_TYPE_VALUE[i],
						"Incorrect commercialtype value for: " + tenant);
			} else if (tenant.equalsIgnoreCase(Constants.TENANTS.SJIT)) {
				Assert.assertEquals(InnerExpsearchAttr1.get(i).getName(),
						Constants.TENANT_ATTRIBUTES.TENANT_DEF_INNER_EXP_COMMERCIAL_TYPE, "Incorrect commercialtype for: " + tenant);
				Assert.assertEquals(InnerExpsearchAttr1.get(i).getValue(),
						Constants.TENANT_ATTRIBUTES.SJIT_TENANT_DEF_INNER_EXP_COMMERCIAL_TYPE_VALUE[i],
						"Incorrect commercialtype value for: " + tenant);
			} else if (tenant.equalsIgnoreCase(Constants.TENANTS.RAPID)) {
				Assert.assertEquals(InnerExpsearchAttr1.get(i).getName(),
						Constants.TENANT_ATTRIBUTES.RAPID_TENANT_DEF_INNER_EXP_COMMERCIAL_TYPE, "Incorrect commercialtype for: " + tenant);
				Assert.assertEquals(InnerExpsearchAttr1.get(i).getValue(),
						Constants.TENANT_ATTRIBUTES.RAPID_TENANT_DEF_INNER_EXP_COMMERCIAL_TYPE_VALUE[i],
						"Incorrect commercialtype value for: " + tenant);
			}
		}
		for (int i = 0; i < InnerExpsearchAttr2.size(); i++) {
			if (tenant.equalsIgnoreCase(Constants.TENANTS.MMB)) {
				Assert.assertEquals(InnerExpsearchAttr2.get(i).getName(),
						Constants.TENANT_ATTRIBUTES.TENANT_DEF_INNER_EXP_SUPPLY_TYPE,
						"Incorrect supplytype value for: " + tenant);
				Assert.assertEquals(InnerExpsearchAttr2.get(i).getValue(),
						Constants.TENANT_ATTRIBUTES.MMB_TENANT_DEF_INNER_EXP_SUPPLY_TYPE_VALUE,
						"Incorrect supplytype value for: " + tenant);
			} else if (tenant.equalsIgnoreCase(Constants.TENANTS.SJIT)) {
				Assert.assertEquals(InnerExpsearchAttr2.get(i).getName(),
						Constants.TENANT_ATTRIBUTES.TENANT_DEF_INNER_EXP_SUPPLY_TYPE, "Incorrect supplytype for: " + tenant);
				Assert.assertEquals(InnerExpsearchAttr2.get(i).getValue(),
						Constants.TENANT_ATTRIBUTES.SJIT_TENANT_DEF_INNER_EXP_SUPPLY_TYPE_VALUE[i],
						"Incorrect supplytype value for: " + tenant);
			}
			if (tenant.equalsIgnoreCase(Constants.TENANTS.RAPID)) {
				Assert.assertEquals(InnerExpsearchAttr2.get(i).getName(),
						Constants.TENANT_ATTRIBUTES.RAPID_TENANT_DEF_SEARCH_ATTR_BRAND_TYPE, "Incorrect Brandtype for: " + tenant);
				Assert.assertEquals(InnerExpsearchAttr2.get(i).getValue(),
						Constants.TENANT_ATTRIBUTES.RAPID_TENANT_DEF_SEARCH_ATTR_BRAND_TYPE_VALUE,
						"Incorrect Brandtype value for: " + tenant);
			}
		}

	}

	/**
	 * This method validates Tenant definition from Tenant entry
	 * @param tenantEntry
	 * @param tenant
	 */
	public void getTenantDefinition(List<TenantEntry> tenantEntry, String tenant) {
		SearchExpression teamDef = tenantEntry.get(0).getTenantDefinition();
		getTenantSearchAttributes(teamDef, tenant);
		getTenantInnerExpression(teamDef, tenant);
	}

	/**
	 * This method validates rconfig metadata
	 * @param tenantEntry
	 */
	public void getRconfiMetadata(List<TenantEntry> tenantEntry) {
		RConfigMetadata rconfigMetadata = tenantEntry.get(0).getrConfigMetadata();
		Assert.assertEquals(rconfigMetadata.getLevel(), Constants.TENANT_ATTRIBUTES.RCONFIG_META_DATA_LEVEL, "Incorrect level");
		for (int i = 0; i < rconfigMetadata.getMandatoryConfigFields().size(); i++) {
			Assert.assertEquals(rconfigMetadata.getMandatoryConfigFields().get(i),
					Constants.TENANT_ATTRIBUTES.RCONFIG_META_DATA_CONFIG_FIELDS[i], "Incorrect field");
		}
	}

	/**
	 * This method validates workflow metadata
	 * @param tenantEntry
	 * @param tenant
	 */
	public void getWorkflowMetadata(List<TenantEntry> tenantEntry, String tenant) {
		WorkflowMetadata workFlowMetadata = tenantEntry.get(0).getWorkflowMetadata();
		Assert.assertEquals(workFlowMetadata.getArtifact(), Constants.TENANT_ATTRIBUTES.WORKFLOW_METADATA_ARTIFACT,
				"Incorrect artifact for:" + tenant);
		for (int i = 0; i < workFlowMetadata.getSplitLogic().size(); i++) {
			if (tenant.equalsIgnoreCase(Constants.TENANTS.MMB)) {
				Assert.assertEquals(workFlowMetadata.getSplitLogic().get(i),
						Constants.TENANT_ATTRIBUTES.MMB_WORKFLOW_METADATA_SPLITLOGIC[i], "Incorrect splitlogic for:" + tenant);
			} else if (tenant.equalsIgnoreCase(Constants.TENANTS.SJIT)) {
				Assert.assertEquals(workFlowMetadata.getSplitLogic().get(i),
						Constants.TENANT_ATTRIBUTES.SJIT_WORKFLOW_METADATA_SPLITLOGIC[i], "Incorrect splitlogic for:" + tenant);
			} else if (tenant.equalsIgnoreCase(Constants.TENANTS.RAPID)) {
				Assert.assertEquals(workFlowMetadata.getSplitLogic().get(i),
						Constants.TENANT_ATTRIBUTES.RAPID_WORKFLOW_METADATA_SPLITLOGIC[i], "Incorrect splitlogic for:" + tenant);
			}
		}
	}

	/**
	 * This method validates workspace metadata
	 * @param tenantEntry
	 * @param tenant
	 */
	public void getWorkspaceMetadata(List<TenantEntry> tenantEntry, String tenant) {
		WorkspaceMetadata workspaceMetadata = tenantEntry.get(0).getWorkspaceMetadata();
		Assert.assertEquals(workspaceMetadata.getDoctype(), Constants.TENANT_ATTRIBUTES.WORKSPACE_METADATA_DOC_TYPE,
				"Incorrect Doc type for:" + tenant);
		if (tenant.equalsIgnoreCase(Constants.TENANTS.MMB)) {
			Assert.assertEquals(workspaceMetadata.getIndex(), Constants.TENANT_ATTRIBUTES.MMB_WORKSPACE_METADATA_INDEX,
					"Incorrect index for:" + tenant);
		} else if (tenant.equalsIgnoreCase(Constants.TENANTS.SJIT)) {
			Assert.assertEquals(workspaceMetadata.getIndex(), Constants.TENANT_ATTRIBUTES.SJIT_WORKSPACE_METADATA_INDEX,
					"Incorrect index for:" + tenant);
		} else if (tenant.equalsIgnoreCase(Constants.TENANTS.RAPID)) {
			Assert.assertEquals(workspaceMetadata.getIndex(), Constants.TENANT_ATTRIBUTES.RAPID_WORKSPACE_METADATA_INDEX,
					"Incorrect index for:" + tenant);
		}
	}
}
