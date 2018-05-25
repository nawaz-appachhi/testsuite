package com.myntra.apiTests.inbound.helper.planningandbuying;

import com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses.*;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.orderindent.enums.VendorTermsValidationStatus;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class JeevesHelper {

    Svc svc;
    String response;
    static Logger log = Logger.getLogger(JeevesHelper.class);

    // returns headers for the http requests
    public HashMap<String, String> getHeaders(HashMap<String, String> headers) throws Exception {
        HashMap<String, String> header = new HashMap<String, String>(headers);
        return header;
    }

    public HashMap<String, String> Headers(String authorization, String accept, String contentType) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", authorization);
        headers.put("Accept", accept);
        headers.put("Content-Type", contentType);
        return headers;
    }

    public String readXLSXFile(String fileName, int rowIndex, int columnIndex) {
        InputStream XlsxFileToRead = null;
        XSSFWorkbook workbook = null;
        Row row = null;
        Cell cell = null;
        try {
            XlsxFileToRead = new FileInputStream(fileName);

            // Getting the workbook instance for xlsx file
            workbook = new XSSFWorkbook(XlsxFileToRead);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        XSSFSheet sheet = workbook.getSheetAt(0);
        row = sheet.getRow(rowIndex);
        cell = row.getCell(columnIndex);
        log.debug("\nJson string:\n" + cell.toString() + "\n");
        return cell.toString();
    }

    public Object readDataFile(String fileName, int rowIndex, int columnIndex) {
        InputStream XlsxFileToRead = null;
        XSSFWorkbook workbook = null;
        Row row = null;
        Cell cell = null;
        try {
            XlsxFileToRead = new FileInputStream(fileName);

            // Getting the workbook instance for xlsx file
            workbook = new XSSFWorkbook(XlsxFileToRead);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        XSSFSheet sheet = workbook.getSheetAt(0);
        row = sheet.getRow(rowIndex);
        cell = row.getCell(columnIndex);

        if(cell==null)
        {
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();

            case Cell.CELL_TYPE_NUMERIC:
                    if(DateUtil.isCellDateFormatted(cell)){
                        DataFormatter df = new DataFormatter();
                        FormulaEvaluator fe = new XSSFWorkbook().getCreationHelper().createFormulaEvaluator();
                        String str= df.formatCellValue(cell, fe);
                        return str;
                    }
                    else
                        return cell.getNumericCellValue();

            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();

            default:
                return cell.toString();
        }
    }

    public ArrayList<String> getFileHeaders(String fileName) {
        InputStream XlsxFileToRead = null;
        XSSFWorkbook workbook = null;
        ArrayList<String> allHeaders = new ArrayList<>();
        try {
            XlsxFileToRead = new FileInputStream(fileName);
            // Getting the workbook instance for xlsx file
            workbook = new XSSFWorkbook(XlsxFileToRead);
            Sheet sheet = workbook.getSheetAt(0);
            Row header_row = sheet.getRow(0);

            Iterator<Cell> cell = header_row.cellIterator();
            while (cell.hasNext()) {
                Cell header_cell = cell.next();
                allHeaders.add(header_cell.getStringCellValue());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allHeaders;
    }


    public ArrayList<String> getColumnValueBasedOnColumnName(String fileName, String colName) {
        InputStream XlsxFileToRead = null;
        XSSFWorkbook workbook = null;
        ArrayList<String> colValues = new ArrayList<>();
        try {
            XlsxFileToRead = new FileInputStream(fileName);
            // Getting the workbook instance for xlsx file
            workbook = new XSSFWorkbook(XlsxFileToRead);
            XSSFSheet sheet = workbook.getSheetAt(1);
            String columnWanted = colName;
            Integer columnNo = null;
            List<Cell> cells = new ArrayList<Cell>();

            Row firstRow = sheet.getRow(0);

            for (Cell cell : firstRow) {
                if (cell.getStringCellValue().equals(columnWanted)) {
                    columnNo = cell.getColumnIndex();
                }
            }
            int headerCount = 0;
            if (columnNo != null) {
                for (Row row : sheet) {
                    if (headerCount == 0) {
                        headerCount++;
                        continue;
                    }
                    Cell c = row.getCell(columnNo);
                    if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK) {
                        // Nothing in the cell in this row hence skip it
                    } else {
                        colValues.add(c.getStringCellValue());
                    }
                }
            }
            headerCount = 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return colValues;
    }

    public HashMap<String, String> getFileValidatorRangeData(String fileName) {
        InputStream XlsxFileToRead = null;
        XSSFWorkbook workbook = null;
        HashMap<String, String> dataRange = new HashMap<>();
        try {
            XlsxFileToRead = new FileInputStream(fileName);
            workbook = new XSSFWorkbook(XlsxFileToRead);
            XSSFSheet sheet = workbook.getSheetAt(0);

            List<? extends DataValidation> dataValidations = sheet.getDataValidations();
            for (DataValidation dataValidation : dataValidations) {
                String[] explicitListValues = dataValidation.getValidationConstraint().getExplicitListValues();
                if (explicitListValues == null) {
                    continue;
                } else {
                    for (String str : explicitListValues) {
                        String endRange = str.split("\\$")[4];
                        String columnName = str.split("\\$")[1];
                        dataRange.put(Constants.TEMPLATE_HEADERS.getColumnMapValue(columnName), endRange);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataRange;
    }

    public boolean checkForEmptyCell(String fileName, int rowIndex, int columnIndex, int count) {
        boolean success = true;
        DataFormatter formatter = new DataFormatter();
        InputStream XlsxFileToRead = null;
        XSSFWorkbook workbook = null;
        Row row = null;
        Cell cell = null;

        try {
            XlsxFileToRead = new FileInputStream(fileName);

            // Getting the workbook instance for xlsx file
            workbook = new XSSFWorkbook(XlsxFileToRead);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        XSSFSheet sheet = workbook.getSheetAt(0);

        for (int i = rowIndex; i <= count; i++) {
            row = sheet.getRow(i);
            cell = row.getCell(columnIndex);
            log.debug("\nJson string:\n" + cell.toString() + "\n");
            if (formatter.formatCellValue(cell).isEmpty()) {
                // log.debug("\nMissing skucode is at row: \n" + row. + "\n");
                success = false;
            }
        }
        return success;
    }

    public void downloadUsingStream(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = bis.read(buffer, 0, 1024)) != -1) {
            fileOutputStream.write(buffer, 0, count);
        }
        fileOutputStream.close();
        bis.close();
    }

    // to read the downloaded file
    public String displayTextInputStream(InputStream input) throws IOException, Exception {
        // Read one text line at a time and display.

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(input));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    public String getobjectfromjson(String response, String object) throws Exception {
        log.debug("Get Object from Response: " + response);
        JSONObject responsebody = new JSONObject(response);
        JSONArray data = responsebody.getJSONArray("data");
        JSONObject obj = data.getJSONObject(0);
        Object json_string = obj.get(object);
        log.debug("\nJson string:\n" + json_string + "\n");
        return json_string.toString();
    }

    public String getStatusType(String object) throws Exception {
        JSONObject responsebody = new JSONObject(this.response);
        JSONObject status = responsebody.getJSONObject("status");
        Object status_type = status.get(object);
        return status_type.toString();
    }

    public String getStatusType(String response, String object) throws Exception {
        JSONObject responseBody = new JSONObject(response);
        JSONObject status = responseBody.getJSONObject("status");
        Object statusType = status.get(object);
        return statusType.toString();
    }

    public String createOI(String payload, HashMap<String, String> headers, String role) throws Exception {
        svc = HttpExecutorService.executeHttpService(Constants.JEEVES_PATH.BASE_OI_HEADERS + "?" + role, null,
                SERVICE_TYPE.OI_SVC.toString(), HTTPMethods.POST, payload, headers);
        response = svc.getResponseBody();
        log.debug("\nOI Response:\n" + response + "\n");
        return response;
    }

    public MultipartEntityBuilder multipartBuilder(String file) {

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("file", new File(file), ContentType.APPLICATION_OCTET_STREAM, "file.ext");
        log.debug("\nMultipart builder response:\n" + builder + "\n");
        return builder;
    }

    public String uploadOI(String file, HashMap<String, String> headers, String id, String role) throws Exception {
        MultipartEntityBuilder builder = multipartBuilder(file);

        svc = HttpExecutorService.executeHttpServiceForUpload(Constants.JEEVES_PATH.BASE_OI_UPLOAD + id + "?" + role, null,
                SERVICE_TYPE.OI_SVC.toString(), HTTPMethods.POST, builder, headers);
        response = svc.getResponseBody();
        return response;

    }

    public String checkOIStatusInJobTracker(String id, HashMap<String, String> headers) throws Exception {
        String status;
        long startTime = System.currentTimeMillis();
        do {
            svc = HttpExecutorService.executeHttpService(Constants.JEEVES_PATH.JOB_TRACKER_STATUS + id, null,
                    SERVICE_TYPE.JOBTRACKER_SVC.toString(), HTTPMethods.GET, "", headers);
            response = svc.getResponseBody();
            status = getobjectfromjson(response, "status");
            Thread.sleep(1000);
            if(status.equalsIgnoreCase("INTERRUPTED"))
                break;
        } while ((!status.equalsIgnoreCase("COMPLETED")) && (System.currentTimeMillis() - startTime) < 120000);
        Assert.assertTrue(status.equalsIgnoreCase("COMPLETED"), "The job tracker status is "
                + status + " expected status is COMPLETED");
        log.debug("\nThe status:\n" + status + "\n");
        return response;
    }

    public String checkJobStatusInJobTrackerForNegativeCases(String id, HashMap<String, String> headers) throws Exception {
        String status;
        long startTime = System.currentTimeMillis();
        do {
            svc = HttpExecutorService.executeHttpService(Constants.JEEVES_PATH.JOB_TRACKER_STATUS + id, null,
                    SERVICE_TYPE.JOBTRACKER_SVC.toString(), HTTPMethods.GET, "", headers);
            response = svc.getResponseBody();
            status = getobjectfromjson(response, "status");
            Thread.sleep(1000);
        } while ((!status.equalsIgnoreCase("INTERRUPTED")) && (System.currentTimeMillis() - startTime) < 10000);
        return response;
    }

    public String obtainJobTrackerErrorFileResponse(String id, HashMap<String, String> headers) throws Exception {
        svc = HttpExecutorService.executeHttpService(Constants.JEEVES_PATH.JOB_TRACKER_STATUS + id+ "/error-file", null,
                SERVICE_TYPE.JOBTRACKER_SVC.toString(), HTTPMethods.GET, "", headers);
        response = svc.getResponseBody();
        return response;
    }

    public String getOiData(String query, String object, HashMap<String, String> headers) throws Exception {
        svc = HttpExecutorService.executeHttpService(Constants.JEEVES_PATH.SEARCH_QUERY_OI + query, null, SERVICE_TYPE.OI_SVC.toString(),
                HTTPMethods.GET, "", headers);
        response = svc.getResponseBody();
        Assert.assertEquals(getStatusType("statusCode"), Constants.STATUS_CODES.SEARCH_OI,
                "Upload OI status code is" + getStatusType("statusCode"));
        Assert.assertEquals(getStatusType("statusType"), "SUCCESS",
                "Upload OI Status type is" + svc.getResponseStatusType("statusType"));
        Assert.assertEquals(getStatusType("statusMessage"), Constants.STATUS_MESSAGES.SEARCH_OI,
                "Upload OI status message is" + getStatusType("statusMessage"));

        String data = getobjectfromjson(response, object);
        log.debug("\nOI string:\n" + object + ":" + data + "\n");
        return data;
    }

    public String updateOIHeaders(String baseOIId, String payload, HashMap<String, String> headers) throws Exception {
        svc = HttpExecutorService.executeHttpService(Constants.JEEVES_PATH.UPDATE_OI_HEADERS + baseOIId + "?" + Constants.ROLES.CM_ROLE,
                null, SERVICE_TYPE.OI_SVC.toString(), HTTPMethods.PUT, payload, headers);
        response = svc.getResponseBody();
        log.debug("\nOI Response:\n" + response + "\n");
        return response;
    }

    public String updateOIHeaders(String baseOIId, String payload, HashMap<String, String> headers, String role) throws Exception {
        svc = HttpExecutorService.executeHttpService(Constants.JEEVES_PATH.UPDATE_OI_HEADERS + baseOIId + "?" + role,
                null, SERVICE_TYPE.OI_SVC.toString(), HTTPMethods.PUT, payload, headers);
        response = svc.getResponseBody();
        log.debug("\nOI Response:\n" + response + "\n");
        return response;
    }

    public String uploadAttributeSheet(String file, HashMap<String, String> headers, String id, String role)
            throws Exception {

        MultipartEntityBuilder builder = multipartBuilder(file);
        svc = HttpExecutorService.executeHttpServiceForUpload(Constants.JEEVES_PATH.UPLOAD_ATTRIBUTE + id + "&" + role, null,
                SERVICE_TYPE.OI_SVC.toString(), HTTPMethods.POST, builder, headers);
        response = svc.getResponseBody();
        log.debug("\nUploadAttributeSheet response:\n" + response + "\n");
        getobjectfromjson(response, "id");
        return response;
    }

    public String bulkUpdateOI(String file, HashMap<String, String> headers, String baseOIId, String role)
            throws Exception {
        MultipartEntityBuilder builder = multipartBuilder(file);
        svc = HttpExecutorService.executeHttpServiceForUpload(Constants.JEEVES_PATH.BULK_UPDATE_OI + baseOIId + "?" + role, null,
                SERVICE_TYPE.OI_SVC.toString(), HTTPMethods.PUT, builder, headers);
        response = svc.getResponseBody();
        log.debug("\nOI Response:\n" + response + "\n");
        return response;
    }

    public String createPI(String OI_ID, HashMap<String, String> headers) throws Exception {
        svc = HttpExecutorService.executeHttpService(Constants.JEEVES_PATH.CREATE_PI + OI_ID, null, SERVICE_TYPE.OI_SVC.toString(),
                HTTPMethods.PUT, "", headers);
        response = svc.getResponseBody();
        log.debug("\nCreatePI response:\n" + response + "\n");
        return response;
    }

    public String geterrorFile(String jobID, HashMap<String, String> headers) throws Exception {
        long startTime = System.currentTimeMillis();
        do {
            svc = HttpExecutorService.executeHttpService(Constants.JEEVES_PATH.JOB_TRACKER_STATUS + jobID + "/error-file", null,
                    SERVICE_TYPE.JOBTRACKER_SVC.toString(), HTTPMethods.GET, "", headers);
            response = svc.getResponseBody();
        } while ((!getStatusType("statusType").equalsIgnoreCase("SUCCESS"))
                && (System.currentTimeMillis() - startTime) < 30000);
        return getobjectfromjson(response, "url");
    }

    public String downloadOI(String OIID, HashMap<String, String> headers, String role, String file) throws Exception {
        svc = HttpExecutorService.executeHttpServiceForBinaryData(
                Constants.JEEVES_PATH.EXCEL_DOWNLOAD + OIID + "&format=xlsx&" + role, null, SERVICE_TYPE.OI_SVC.toString(),
                HTTPMethods.GET, null, headers, file);

        response = svc.getResponseBody();
        log.debug("\nDownload OI response:\n" + response + "\n");
        return response;
    }

    public String updateOIStatus(String OIID, String payload, HashMap<String, String> headers, String role)
            throws Exception {
        svc = HttpExecutorService.executeHttpService(Constants.JEEVES_PATH.OI_STATUS_UPDATE + OIID + "?" + role, null,
                SERVICE_TYPE.OI_SVC.toString(), HTTPMethods.PUT, payload, headers);
        response = svc.getResponseBody();
        log.debug("\nUpdate OI status response:\n" + response + "\n");
        return response;
    }

    public String searchBuyPlan(String OIID, HashMap<String, String> headers, String role)
            throws Exception {
        svc = HttpExecutorService.executeHttpService(Constants.JEEVES_PATH.OI_STATUS_UPDATE + OIID + "?" + role, null,
                SERVICE_TYPE.OI_SVC.toString(), HTTPMethods.GET, null, headers);
        response = svc.getResponseBody();
        return response;
    }

    public String job_tracker_query(String query, HashMap<String, String> headers) throws Exception {
        svc = HttpExecutorService.executeHttpService(Constants.JEEVES_PATH.SEARCH_QUERY + query, null,
                SERVICE_TYPE.JOBTRACKER_SVC.toString(), HTTPMethods.GET, "", headers);
        response = svc.getResponseBody();
        JSONObject responsebody = new JSONObject(response);
        JSONArray data = responsebody.getJSONArray("data");
        if (data.length() == 0) {
            String new_query = query.substring(0, query.length() - 11) + "COMPLETED";
            svc = HttpExecutorService.executeHttpService(Constants.JEEVES_PATH.SEARCH_QUERY + new_query, null,
                    SERVICE_TYPE.JOBTRACKER_SVC.toString(), HTTPMethods.GET, "", headers);
            response = svc.getResponseBody();
            log.debug("\nJOB TRACKER:\n" + response + "\n");
        }
        log.debug("\nJob tracker query response:\n" + response + "\n");
        return response;
    }

    public String uploadTsrRconfig(String file, HashMap<String, String> headers, String tenant) throws Exception {
        MultipartEntityBuilder builder = multipartBuilder(file);
        svc = HttpExecutorService.executeHttpServiceForUpload(tenant + "/" + Constants.JEEVES_PATH.UPLOAD_TSR_RCONFIG, null,
                SERVICE_TYPE.TSR_RCONFIG.toString(), HTTPMethods.POST, builder, headers);
        response = svc.getResponseBody();
        log.debug("\nuploaded tsr config for BU:\n" + response + "\n");
        return response;
    }

    public String downloadTsrRconfig(String file, HashMap<String, String> headers, String tenant, String business_unit)
            throws Exception {
        svc = HttpExecutorService.executeHttpServiceForBinaryData(
                tenant + "/" + Constants.JEEVES_PATH.DOWNLOAD_TSR_RCONFIG + URLEncoder.encode(business_unit, "UTF-8"), null,
                SERVICE_TYPE.TSR_RCONFIG.toString(), HTTPMethods.GET, null, headers, file);
        response = svc.getResponseBody();
        return response;
    }

    public String runTsrConfigforBU(String tenant, String business_unit, HashMap<String, String> headers)
            throws Exception {
        svc = HttpExecutorService.executeHttpService(
                tenant + "/" + Constants.JEEVES_PATH.TSR_RUN_CONFIG + URLEncoder.encode(business_unit, "UTF-8"), null,
                SERVICE_TYPE.TSR_SVC.toString(), HTTPMethods.GET, "", headers);
        response = svc.getResponseBody();
        log.debug("\nTSR run config response:\n" + response + "\n");
        return response;
    }

    public String generateTsrOI(String tenant, String payload, HashMap<String, String> headers) throws Exception {
        long startTime = System.currentTimeMillis();
        do {
            svc = HttpExecutorService.executeHttpService(tenant + "/" + Constants.JEEVES_PATH.TSR_GENERATE_OI, null,
                    SERVICE_TYPE.TSR_SVC.toString(), HTTPMethods.POST, payload, headers);
            response = svc.getResponseBody();
        } while ((!getStatusType("statusType").equalsIgnoreCase("SUCCESS"))
                && (System.currentTimeMillis() - startTime) < 80000);
        return response;
    }

    public String vendorToCmMapping(String vendor_id, String cm_id, HashMap<String, String> headers) throws Exception {
        String payload = "{\"vendorCategoryManagerResponse\": { \"data\": { \"vendorCategoryManager\": {\"enabled\":\"true\", \"user\": {\"id\": \""
                + cm_id + "\"}, \"vendor\": { \"id\": \"" + vendor_id + "\"}}}}}";
        svc = HttpExecutorService.executeHttpService(Constants.VMS_PATH.ADD_VENDOR_CATEGORY_MANAGER, null, SERVICE_TYPE.VMS.toString(),
                HTTPMethods.POST, payload, headers);
        response = svc.getResponseBody();
        log.debug("\nVendor to CM mapping response:\n" + response + "\n");
        return response;
    }

    public String getSummaryDataForBu(String tenant, String business_unit, String filters,
                                      HashMap<String, String> headers) throws Exception {
        svc = HttpExecutorService.executeHttpService(
                tenant + "/" + Constants.JEEVES_PATH.SUMMARY_DATA_FOR_BU + URLEncoder.encode(business_unit, "UTF-8") + filters,
                null, SERVICE_TYPE.TSR_SVC.toString(), HTTPMethods.GET, "", headers);
        response = svc.getResponseBody();
        log.debug("\nSummary Data response:\n" + response + "\n");
        return response;
    }

    public String getReplenishmentSet(String tenant, String business_unit, HashMap<String, String> headers)
            throws Exception {
        svc = HttpExecutorService.executeHttpService(
                tenant + "/" + Constants.JEEVES_PATH.REPLENISHMENT_SET + URLEncoder.encode(business_unit, "UTF-8"), null,
                SERVICE_TYPE.TSR_SVC.toString(), HTTPMethods.GET, "", headers);
        response = svc.getResponseBody();
        log.debug("\nReplenishment set response:\n" + response + "\n");
        return response;
    }

    public String downloadReplenishmentStyles(String file, HashMap<String, String> headers, String tenant,
                                              String business_unit) throws Exception {
        svc = HttpExecutorService.executeHttpServiceForBinaryData(
                tenant + "/" + Constants.JEEVES_PATH.EXPORT_REPLENISHMENT_SET + URLEncoder.encode(business_unit, "UTF-8"), null,
                SERVICE_TYPE.TSR_SVC.toString(), HTTPMethods.GET, null, headers, file);
        response = svc.getResponseBody();
        return response;
    }

    public HashMap<String, String> initializeUploadBaseOIHeader(String role) {
        if (role.contains("cm")) {
            log.debug("CM HEADERS");
            return Headers(Constants.HEADERS.CM_BENETTON_UPLOAD_OI_AUTHORIZATION, Constants.HEADERS.ACCEPT,
                    Constants.HEADERS.MULTIPART_CONTENTTYPE);
        } else {
            log.debug("VENDOR HEADER");
            return Headers(Constants.HEADERS.VENDOR_BENETTON_UPLOAD_OI_AUTHORIZATION, Constants.HEADERS.ACCEPT,
                    Constants.HEADERS.MULTIPART_CONTENTTYPE);
        }


    }

    public HashMap<String, String> initializeHeader(String role, String action) {
        String authorization = new String();
        String accept = new String();
        String contentType = new String();

        if (role.contains("cm")) {
            log.debug("CM Header");
            authorization = Constants.HEADERS.CM_BENETTON_UPLOAD_OI_AUTHORIZATION;
        } else if (role.contains("vendor")) {
            authorization = Constants.HEADERS.VENDOR_BENETTON_UPLOAD_OI_AUTHORIZATION;
        }

        switch (action) {
            case "downloadfile":
                accept = Constants.HEADERS.OCTED_CONTENTTYPE;
                break;
            default:
                accept = Constants.HEADERS.ACCEPT;
        }

        switch (action) {
            case "uploadfile":
                contentType = Constants.HEADERS.MULTIPART_CONTENTTYPE;
                break;
            default:
                contentType = Constants.HEADERS.CONTENTTYPE;
        }
        return Headers(authorization, accept, contentType);
    }


    public String createOIAndGetOrderId(String role, String uploadFile, HashMap<String, String> create_baseoi_headers)
            throws Exception {
        String base_oi_id = createOIHeader(role, create_baseoi_headers);
        String job_id = uploadFileOI(role, uploadFile, base_oi_id);

        // wait till status is completed in job tracker
        checkOIStatusInJobTracker(job_id, create_baseoi_headers);

        // return OI ID:
        String query = "baseOrderIndentId.eq:" + base_oi_id;
        return getOiData(query, "id", create_baseoi_headers);
    }

    public String createOIHeader(String role, HashMap<String, String> create_baseoi_headers) throws Exception {
        BaseOrderIndentJson baseOI = new BaseOrderIndentJson();
        JeevesValidator jeevesvalidator = new JeevesValidator();
        Constants.OIHeaderUCB oiheader1 = new Constants.OIHeaderUCB();
        // construct payload
        String payload = baseOI.createOIPayload(oiheader1);

        // Create baseOI
        String OIDetails_response = createOI(payload, create_baseoi_headers, role);
        jeevesvalidator.validateTheResponseStatus(OIDetails_response, "CREATE_OI");
        String base_oi_id = getobjectfromjson(OIDetails_response, "id");
        log.debug("\nbaseOI ID:\n" + base_oi_id + "\n");

        return base_oi_id;
    }




	public String createOIAndGetOrderId(String role, String uploadFile, HashMap<String, String> create_baseoi_headers, VendorData vendor)
			throws Exception {

		String base_oi_id = createOIHeader(role, create_baseoi_headers,vendor);
		String job_id = uploadFileOI(role, uploadFile, base_oi_id);

		// wait till status is completed in job tracker
		checkOIStatusInJobTracker(job_id, create_baseoi_headers);

		// return OI ID:
		String query = "baseOrderIndentId.eq:" + base_oi_id;
		return getOiData(query, "id", create_baseoi_headers);
	}

	public String createOIHeader(String role, HashMap<String, String> create_baseoi_headers, VendorData vendor) throws Exception {


		BaseOrderIndentJson baseOI = new BaseOrderIndentJson();
		JeevesValidator jeevesvalidator = new JeevesValidator();
		//vendor = new Constants.OIHEADER1();
		// construct payload
		String payload = baseOI.createOIPayload(vendor);

		// Create baseOI
		String OIDetails_response = createOI(payload, create_baseoi_headers, role);
		jeevesvalidator.validateTheResponseStatus(OIDetails_response, "CREATE_OI");
		String base_oi_id = getobjectfromjson(OIDetails_response, "id");
		log.debug("\nbaseOI ID:\n" + base_oi_id + "\n");

		return base_oi_id;
	}

	public String uploadFileOI(String role, String uploadFile, String baseOiId) throws Exception {
		JeevesValidator jeevesvalidator = new JeevesValidator();
		// upload baseOI
		HashMap<String, String> upload_baseoi_headers = initializeUploadBaseOIHeader(role);
		String upload_response = uploadOI(uploadFile, upload_baseoi_headers, baseOiId, role);
		jeevesvalidator.validateTheResponseStatus(upload_response, "UPLOAD_OI");
		String job_id = getobjectfromjson(upload_response, "jobId");
		log.debug("\nJOB ID:\n" + job_id + "\n");

		return job_id;
	}

    public JSONArray getReplenishmentSetFromRespose(String replenishmentset_resp) throws JSONException {
        JSONObject replenishmentset_data = new JSONObject(replenishmentset_resp);
        JSONArray replenishmentset = replenishmentset_data.getJSONArray("replenishmentSet");
        return replenishmentset;
    }

    public JSONArray getBucketFromSummaryDataResponse(String summary_data) throws JSONException {
        JSONObject responsebody = new JSONObject(summary_data);
        JSONObject aggregation = responsebody.getJSONObject("aggregations");
        JSONObject commercialtype = aggregation.getJSONObject("commercialtype");
        JSONArray bucket = commercialtype.getJSONArray("buckets");
        return bucket;
    }

    public void downloadRconfigForBuAndValidateBu(String tenant, String bu, HashMap<String, String> download_rconfig_headers)
            throws Exception {
        JeevesValidator jeevesvalidator = new JeevesValidator();
        log.info("Downloading Rconfig file for: " + bu + "in tenant: " + tenant);
        downloadTsrRconfig(Constants.DATA_FILES.downloaded_rconfig, download_rconfig_headers, tenant, bu);

        // assert that the downloaded sheet is of the correct BU
        jeevesvalidator.validateTheFileData(Constants.DATA_FILES.downloaded_rconfig, bu, 1, 0, bu + " is incorrect");
//		jeevesvalidator.validateCellvalueFromTheDownloadedSheet(DATA_FILES.downloaded_rconfig, bu, 1,0);
    }

    public void uploadRconfigForBu(String tenant, HashMap<String, String> upload_rconfig_headers) throws Exception {
        JeevesValidator jeevesvalidator = new JeevesValidator();
        log.info("Uploading Rconfig file in tenant: " + tenant);
        // upload rconfig
        String upload_rconfig_resp = uploadTsrRconfig(Constants.DATA_FILES.downloaded_rconfig, upload_rconfig_headers, tenant);
        jeevesvalidator.validateTheResponseStatus(upload_rconfig_resp, "UPLOAD_TSR_RCONFIG");
        log.info("Upload completed for Rconfig file in tenant: " + tenant);
    }

    public void generateOIAndWaitTillSuccess(String tenant, String payload, String query, HashMap<String, String> create_baseoi_headers)
            throws Exception {
        JeevesValidator jeevesvalidator = new JeevesValidator();
        String generate_tsr_oi_resp = generateTsrOI(tenant, payload, create_baseoi_headers);
        jeevesvalidator.validateTheResponseStatus(generate_tsr_oi_resp, "GENERATE_OI");
        String generate_oi_query_response = job_tracker_query(query,
                create_baseoi_headers);
        String job_id = getobjectfromjson(generate_oi_query_response, "id");

        // run this query till job tracker status changes from inprogress to
        // completed
        checkOIStatusInJobTracker(job_id, create_baseoi_headers);
        Thread.sleep(15000);
    }


	public void uploadRconfigForBu(String file, String tenant, HashMap<String, String> upload_rconfig_headers) throws Exception {
		JeevesValidator jeevesvalidator = new JeevesValidator();
		log.info("Uploading Rconfig file in tenant: " + tenant);
		// upload rconfig
		String upload_rconfig_resp = uploadTsrRconfig(file, upload_rconfig_headers, tenant);
		jeevesvalidator.validateTheResponseStatus(upload_rconfig_resp, "UPLOAD_TSR_RCONFIG");
		log.info("Upload completed for Rconfig file in tenant: " + tenant);
	}

	public void runTsrRconfigAndWaitTillSuccess(String tenant, String bu, String query,
			HashMap<String, String> create_baseoi_headers) throws Exception {

		// Run tsr configuration to generate replinsments
		runTsrConfigforBU(tenant, bu, create_baseoi_headers);

		// get the id of the BU run
		String run_config_query_response = job_tracker_query(query, create_baseoi_headers);
		String job_id = getobjectfromjson(run_config_query_response, "id");

		// run this query till job tracker status changes from inprogress to
		// completed
		checkOIStatusInJobTracker(job_id, create_baseoi_headers);
	}

	public void bulkUpdateOIAndWaitTillSuccess(String file, String OI_ID, HashMap<String, String> create_baseoi_headers, HashMap<String, String> upload_baseoi_headers) throws Exception {
		JeevesValidator jeevesvalidator = new JeevesValidator();
		// Update OI by uploading a modified oi sheet
        String updateOI_response = bulkUpdateOI(file,
                upload_baseoi_headers, OI_ID, Constants.ROLES.CM_ROLE);
        jeevesvalidator.validateTheResponseStatus(updateOI_response, "UPDATE_OI");

        // check the status of the sheet got uploaded or not
        String updateOI_job_id = getobjectfromjson(updateOI_response, "jobId");
        String jobtracker_status_for_updateOI = checkOIStatusInJobTracker(updateOI_job_id,
                create_baseoi_headers);
        String updateOI_remark = getobjectfromjson(jobtracker_status_for_updateOI, "remark");
        jeevesvalidator.validateObjectFromTheResponse(updateOI_remark, Constants.STATUS_MESSAGES.UPDATE_OI_REMARKS);
    }


    public String shareOI(String buyPlanId, HashMap<String, String> create_baseoi_headers, String role) throws Exception {
        String shareResponse = new String();
        if (role.contains("cm")) {
            shareResponse = updateOIStatus(buyPlanId, Constants.DATA_FILES.pending_with_partner_state, create_baseoi_headers, role);
        } else if (role.contains("vendor")) {
            shareResponse = updateOIStatus(buyPlanId, Constants.DATA_FILES.pending_with_myntra_state, create_baseoi_headers, role);
        }
        return shareResponse;
    }

    public String downloadOITemplate(HashMap<String, String> headers, String role, String file, String buyPlanType) throws Exception {
        svc = HttpExecutorService.executeHttpServiceForBinaryData(
                Constants.JEEVES_PATH.DOWNLOAD_TEMPLATE + buyPlanType, null, SERVICE_TYPE.OI_SVC.toString(),
                HTTPMethods.GET, null, headers, file);

        response = svc.getResponseBody();
        log.debug("\nDownload OI response:\n" + response + "\n");
        return response;
    }

    public Set<String> getAllBrands(HashMap<String, String> headers) throws UnsupportedEncodingException, JSONException {
        svc = HttpExecutorService.executeHttpService(
                Constants.JEEVES_PATH.ALL_BRANDS, null, SERVICE_TYPE.CATALOG_SERVICE.toString(),
                HTTPMethods.GET, null, headers);
        Set<String> brands = new HashSet<>();
        response = svc.getResponseBody();
        JSONObject responsebody = new JSONObject(response);
        JSONArray data = responsebody.getJSONArray("attributeTypes");
        JSONObject obj = data.getJSONObject(0);
        JSONArray allAttributeValues = obj.getJSONArray("allAttributeValues");
        for (int i = 0; i < allAttributeValues.length(); i++) {
            JSONObject brandData = allAttributeValues.getJSONObject(i);
            String brand = brandData.getString("attributeValue");
            brands.add(brand);
        }
        return brands;
    }

    public Set<String> getAllArticleTypes(HashMap<String, String> headers) throws UnsupportedEncodingException, JSONException {
        svc = HttpExecutorService.executeHttpService(
                Constants.JEEVES_PATH.ALL_ARTICLE_TYPE, null, SERVICE_TYPE.CATALOG_SERVICE.toString(),
                HTTPMethods.GET, null, headers);
        Set<String> articleTypes = new HashSet<>();
        response = svc.getResponseBody();
        JSONObject responsebody = new JSONObject(response);
        JSONArray data = responsebody.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject brandData = data.getJSONObject(i);
            String articleType = brandData.getString("typeName");
            articleTypes.add(articleType);
        }
        return articleTypes;
    }

    public Set<String> getAllGenders(HashMap<String, String> headers) throws UnsupportedEncodingException, JSONException {
        svc = HttpExecutorService.executeHttpService(
                Constants.JEEVES_PATH.ALL_GENDER, null, SERVICE_TYPE.PIM_SERVICE.toString(),
                HTTPMethods.GET, null, headers);
        Set<String> genders = new HashSet<>();
        response = svc.getResponseBody();
        JSONObject responsebody = new JSONObject(response);
        JSONArray data = responsebody.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject brandData = data.getJSONObject(i);
            String gender = brandData.getString("description");
            genders.add(gender);
        }
        return genders;
    }

    public Set<String> getAllMarginType(HashMap<String, String> headers) throws UnsupportedEncodingException, JSONException {
        svc = HttpExecutorService.executeHttpService(
                Constants.JEEVES_PATH.ALL_MARGIN_TYPE, null, SERVICE_TYPE.TERMS_SERVICE.toString(),
                HTTPMethods.GET, null, headers);
        Set<String> marginTypes = new HashSet<>();
        response = svc.getResponseBody();
        JSONObject responsebody = new JSONObject(response);
        JSONArray data = responsebody.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject brandData = data.getJSONObject(i);
            String marginType = brandData.getString("displayName");
            marginTypes.add(marginType.toLowerCase());
        }
        return marginTypes;

    }

    public Set<String> getAllLeadTimeCategories(HashMap<String, String> headers) throws UnsupportedEncodingException, JSONException {
        svc = HttpExecutorService.executeHttpService(
                Constants.JEEVES_PATH.ALL_LEAD_TIME_CATEGORY, null, SERVICE_TYPE.MANUFACTURE_SERVICE.toString(),
                HTTPMethods.GET, null, headers);
        Set<String> leadTimeCategories = new HashSet<>();
        response = svc.getResponseBody();
        JSONObject responsebody = new JSONObject(response);
        JSONArray data = responsebody.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject brandData = data.getJSONObject(i);
            String leadTimeCategory = brandData.getString("categoryName");
            leadTimeCategories.add(leadTimeCategory);
        }
        return leadTimeCategories;
    }

    public void writeDataFile(Object[] data, String excelFileName) throws IOException {

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();
        String[] headers = Constants.TEMPLATE_HEADERS.MMB;

        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i< Constants.TEMPLATE_HEADERS.MMB.length; i++)
        {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }

        XSSFRow row1 = sheet.createRow(1);
        for (int r = 0; r < data.length-1; r++)
        {
            XSSFCell cell = row1.createCell(r);
            Object obj = data[r];
            if (obj instanceof String) {
                String cellValue = (String) obj;
                if(cellValue.trim().equalsIgnoreCase("blank"))
                {
                    cellValue="";
                }
                cell.setCellValue(cellValue);
            } else if (obj instanceof Boolean) {
                cell.setCellValue((Boolean) obj);
            } else if (obj instanceof Date) {
                cell.setCellValue((Date) obj);
            } else if (obj instanceof Double) {
                cell.setCellValue((Double) obj);
            }
        }

        FileOutputStream fileOut = new FileOutputStream(excelFileName);
        wb.write(fileOut);
        fileOut.close();
    }

    public void appendDataToExcelFile(Object[][] data, String excelFileName) throws IOException {
        File excel = new File(excelFileName);
        FileInputStream fis = new FileInputStream(excel);
        XSSFWorkbook book = new XSSFWorkbook(fis);
        XSSFSheet sheet = book.getSheetAt(0);

        int rownum = sheet.getLastRowNum();

        for (int i=0;i<data.length;i++) {
            Row row = sheet.createRow(rownum++);
            int cellnum = 0;
            for (Object obj : data[i]) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Boolean) {
                    cell.setCellValue((Boolean) obj);
                } else if (obj instanceof Date) {
                    cell.setCellValue((Date) obj);
                } else if (obj instanceof Double) {
                    cell.setCellValue((Double) obj);
                }
            }
        }


        // open an OutputStream to save written data into Excel file
        FileOutputStream os = new FileOutputStream(excel);
        book.write(os);

        // Close OutputStream and Excel file to prevent leak
        os.close();
        fis.close();

    }


    public Boolean compareExcelFiles(String filePath1, String filePath2) {
        FileInputStream excellFile1 = null;
        FileInputStream excellFile2 = null;

        try {
            // get input excel files
            excellFile1 = new FileInputStream(new File(filePath1));
            excellFile2 = new FileInputStream(new File(filePath2));

            // Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook1 = new XSSFWorkbook(excellFile1);
            XSSFWorkbook workbook2 = new XSSFWorkbook(excellFile2);

            // Get first/desired sheet from the workbook
            XSSFSheet sheet1 = workbook1.getSheetAt(0);
            XSSFSheet sheet2 = workbook2.getSheetAt(0);

            // Compare sheets
            if (compareTwoSheets(sheet1, sheet2)) {
                return  true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //close files
                excellFile1.close();
                excellFile2.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    // Compare Two Sheets
    private boolean compareTwoSheets(XSSFSheet sheet1, XSSFSheet sheet2) {
        int firstRow1 = sheet1.getFirstRowNum();
        int lastRow1 = sheet1.getLastRowNum();
        boolean equalSheets = true;
        for (int i = firstRow1; i <= lastRow1; i++) {
            log.debug("\nComparing Row " + i);

            XSSFRow row1 = sheet1.getRow(i);
            XSSFRow row2 = sheet2.getRow(i);
            if (!compareTwoRows(row1, row2)) {
                equalSheets = false;
                log.debug("Row " + i + " - Not Equal");
                break;
            } else {
//                log.debug("Row " + i + " - Equal");
            }
        }
        return equalSheets;
    }

    // Compare Two Rows
    private boolean compareTwoRows(XSSFRow row1, XSSFRow row2) {
        if ((row1 == null) && (row2 == null)) {
            return true;
        } else if ((row1 == null) || (row2 == null)) {
            return false;
        }

        int firstCell1 = row1.getFirstCellNum();
        int lastCell1 = row1.getLastCellNum();
        boolean equalRows = true;

        // Compare all cells in a row
        for (int i = firstCell1; i <= lastCell1; i++) {
            XSSFCell cell1 = row1.getCell(i);
            XSSFCell cell2 = row2.getCell(i);
            if (!compareTwoCells(cell1, cell2)) {
                equalRows = false;
                log.error("Cell " + i + " - Not Equal");
                break;
            } else {
//                log.debug("Cell " + i + " - Equal");
            }
        }
        return equalRows;
    }

    // Compare Two Cells
    private boolean compareTwoCells(XSSFCell cell1, XSSFCell cell2) {
        if ((cell1 == null) && (cell2 == null)) {
            return true;
        } else if (cell1 == null) {
            if(cell2.getCellType()==XSSFCell.CELL_TYPE_BLANK)
                return true;
            else
                return false;
        }else if (cell2 == null) {
            if(cell1.getCellType()==XSSFCell.CELL_TYPE_BLANK)
                return true;
            else
                return false;
        }

        boolean equalCells = false;

        int type1 = cell1.getCellType();
        int type2 = cell2.getCellType();
        if (type1 == type2) {
                // Compare cells based on its type
                switch (cell1.getCellType()) {
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        if (cell1.getNumericCellValue() == cell2
                                .getNumericCellValue()) {
                            equalCells = true;
                        }
                        break;
                    case XSSFCell.CELL_TYPE_STRING:
                        if (cell1.getStringCellValue().equals(cell2
                                .getStringCellValue())) {
                            equalCells = true;
                        }
                        break;
                    case XSSFCell.CELL_TYPE_BOOLEAN:
                        if (cell1.getBooleanCellValue() == cell2
                                .getBooleanCellValue()) {
                            equalCells = true;
                        }
                        break;
                    default:
                        equalCells = true;
                }
        } else {
            return false;
        }
        return equalCells;
    }

    /**
     * Method to Create OI
     *
     * @param fileName
     * @param role
     * @return
     * @throws Exception
     */
    public Object[] createOI(String fileName, String role) throws Exception {
        JeevesValidator jeevesvalidator = new JeevesValidator();
        BaseOrderIndentJson baseOI = new BaseOrderIndentJson();
        Constants.OIHeaderUCB oiheader1 = new Constants.OIHeaderUCB();

        // construct payload
        String payload = baseOI.createOIPayload(oiheader1);
        BaseOIHeaderRequest baseOIHeaderRequest = (BaseOIHeaderRequest) APIUtilities.getJsontoObject(payload, new BaseOIHeaderRequest());

        //Create The Base OI
        String base_oi_id = createBaseOrderIndent(baseOIHeaderRequest,payload,role);

        //Upload Excel Sheet
        String job_id = uploadExcelForCreateOI(fileName,baseOIHeaderRequest,base_oi_id,role);

        //validate JobTracker response
        JobTrackerResponse jobTrackerResponse = getJobTrackerResponse(job_id,role);
        jeevesvalidator.validateTheJobTrackerResponse(jobTrackerResponse, role);

        return new Object[] {jobTrackerResponse,baseOIHeaderRequest};
    }

    public Object[] createOI(String payload, String fileName, String role) throws Exception {
        JeevesValidator jeevesvalidator = new JeevesValidator();
        BaseOrderIndentJson baseOI = new BaseOrderIndentJson();

        // construct base oi request
        BaseOIHeaderRequest baseOIHeaderRequest = (BaseOIHeaderRequest)
                APIUtilities.getJsontoObject(payload, new BaseOIHeaderRequest());

        //Create The Base OI
        String base_oi_id = createBaseOrderIndent(baseOIHeaderRequest,payload,role);

        //Upload Excel Sheet
        String job_id = uploadExcelForCreateOI(fileName,baseOIHeaderRequest,base_oi_id,role);

        //validate JobTracker response
        JobTrackerResponse jobTrackerResponse = getJobTrackerResponse(job_id,role);
        jeevesvalidator.validateTheJobTrackerResponse(jobTrackerResponse, role);

        return new Object[] {jobTrackerResponse,baseOIHeaderRequest};
    }

    /**
     * Method to Create Base OI
     *
     * @param baseOIHeaderRequest
     * @param payload
     * @param role
     * @return
     * @throws Exception
     */
    public String createBaseOrderIndent(BaseOIHeaderRequest baseOIHeaderRequest, String payload, String role) throws Exception
    {
        JeevesValidator jeevesvalidator = new JeevesValidator();
        HashMap<String, String> create_baseoi_headers = initializeHeader(role, "create");

        String OIDetails_response = createOI(payload, create_baseoi_headers, role);
        BaseOIHeaderResponse baseOIHeaderResponse = (BaseOIHeaderResponse) APIUtilities.getJsontoObject(OIDetails_response,
                new BaseOIHeaderResponse());
        jeevesvalidator.validateBaseOIHeaderResponse(baseOIHeaderRequest, baseOIHeaderResponse, role);
        String base_oi_id = String.valueOf(baseOIHeaderResponse.getData()[0].getId());

        return base_oi_id;
    }

    /**
     * Method Upload file to base OI created
     *
     * @param filePath
     * @param baseOIHeaderRequest
     * @param base_oi_id
     * @param role
     * @return
     * @throws Exception
     */
    public String uploadExcelForCreateOI(String filePath, BaseOIHeaderRequest baseOIHeaderRequest
            ,String base_oi_id, String role) throws Exception
    {
        JeevesValidator jeevesvalidator = new JeevesValidator();
        HashMap<String, String> upload_baseoi_headers = initializeHeader(role, "uploadfile");

        // upload baseOI
        String upload_response = uploadOI(filePath, upload_baseoi_headers, base_oi_id, role);
        CreateOIResponse createOIResponse = (CreateOIResponse) APIUtilities.getJsontoObject(upload_response, new CreateOIResponse());
        jeevesvalidator.validateTheCreateOIResponse(baseOIHeaderRequest, createOIResponse, role);

        //Get Job Id from the response
        String job_id = String.valueOf(createOIResponse.getData()[0].getJobId());

        return job_id;

    }

    /**
     * Method to validate and get the job tracker success response
     *
     * @param job_id
     * @param role
     * @return
     * @throws Exception
     */
    public JobTrackerResponse getJobTrackerResponse(String job_id, String role) throws Exception {
        HashMap<String, String> create_baseoi_headers = initializeHeader(role, "create");

        //Wait for the job tracker to complete
        String jobTrackerResponseString = checkOIStatusInJobTracker(job_id, create_baseoi_headers);

        //Validate the Job Tracker Response
        JobTrackerResponse jobTrackerResponse = (JobTrackerResponse) APIUtilities.
                getJsontoObject(jobTrackerResponseString, new JobTrackerResponse());

        return jobTrackerResponse;

    }

    /**
     * Method to update the Header of the BuyPlan created and validate
     *
     * @param buyPlanHeaderUpdateRequest
     * @param buyPlanId
     * @param role
     * @param baseOIDetailsResponse
     * @param requestData
     * @throws Exception
     */
    public void updateHeaderSuccessfullyAndValidate(BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest,
                                                    String buyPlanId, String role, BaseOIDetailsResponse baseOIDetailsResponse,
                                                    HashMap<String, Object> requestData,Object oiheader) throws Exception
    {
        JeevesValidator jeevesvalidator = new JeevesValidator();
        HashMap<String, String> create_baseoi_headers = initializeHeader(role, "create");


        String updatePayload = APIUtilities.getObjectToJSON(buyPlanHeaderUpdateRequest);
        String update_OI_response = updateOIHeaders(buyPlanId, updatePayload, create_baseoi_headers, role);
        baseOIDetailsResponse = (BaseOIDetailsResponse) APIUtilities.getJsontoObject(update_OI_response, new BaseOIDetailsResponse());

        if (role.contains("cm")) {
            jeevesvalidator.validateTheVendorTermsValidationStatus(baseOIDetailsResponse, VendorTermsValidationStatus.SUCCESS);
        } else {
            jeevesvalidator.validateTheVendorTermsValidationStatus(baseOIDetailsResponse, VendorTermsValidationStatus.PENDING);
        }

        Status status = baseOIDetailsResponse.getStatus();
        jeevesvalidator.validateTheResponseStatus(status, "UPDATE_OI_HEADERS");
        jeevesvalidator.validateTheUpdateHeaderResponse(baseOIDetailsResponse, requestData,role,oiheader);
    }

    /**
     * Method to share and validate if the share action reflects in status change
     *
     * @param buyPlanId
     * @param role
     * @throws Exception
     */
    public void shareAndValidateTheStatusChange(String buyPlanId, String role, Object oiheader1) throws Exception {
        JeevesValidator jeevesvalidator = new JeevesValidator();
        HashMap<String, String> create_baseoi_headers = initializeHeader(role, "create");

        String shareResponse = shareOI(buyPlanId, create_baseoi_headers, role);
        jeevesvalidator.validateTheStatusChangeAfterShare(shareResponse, role);

        //Validate after sharing the BuyPlan can be viewed by the shared person
        String searchResponse = "";
        if (role.contains("cm")) {
            if(oiheader1 instanceof Constants.OIHeaderUCB)
            {
                searchResponse = searchBuyPlan(buyPlanId, create_baseoi_headers, Constants.ROLES.UCB_VENDOR_ROLE);
            }
            else if (oiheader1 instanceof Constants.OIHeaderBATA)
            {
                searchResponse = searchBuyPlan(buyPlanId, create_baseoi_headers, Constants.ROLES.BATA_VENDOR_ROLE);
            }
            else if(oiheader1 instanceof Constants.OIHeaderFF)
            {
                searchResponse = searchBuyPlan(buyPlanId, create_baseoi_headers, Constants.ROLES.FF_VENDOR_ROLE);
            }
        } else if (role.contains("vendor")) {
            searchResponse = searchBuyPlan(buyPlanId, create_baseoi_headers, Constants.ROLES.CM_ROLE);
        }
        BaseOIDetailsResponse baseOIDetailsResponse = (BaseOIDetailsResponse) APIUtilities.getJsontoObject(searchResponse, new BaseOIDetailsResponse());
        jeevesvalidator.validateTheResponseStatus(baseOIDetailsResponse.getStatus(), "SEARCH_OI");

    }

    /**
     * Method to change the roles
     *
     * @param role
     * @return
     */
    public String changeRole(String role) {
        String newRole = "";
        if(role.contains("cm"))
        {
            newRole = Constants.ROLES.UCB_VENDOR_ROLE;
        }
        else
        {
            newRole = Constants.ROLES.CM_ROLE;
        }
        return newRole;
    }


	public JSONArray getReplenishmentSummaryData(String response) throws JSONException {
		JSONObject responsebody = new JSONObject(response);
		JSONObject aggregation = responsebody.getJSONObject("aggregations");
		JSONObject commercialtype = aggregation.getJSONObject("commercialtype");
		JSONArray bucket = commercialtype.getJSONArray("buckets");
		return bucket;
	}
	
	public JSONArray getReplenishmentStyleData(String response) throws JSONException {
		JSONObject responsebody = new JSONObject(response);
		JSONArray replenishmentset = responsebody.getJSONArray("replenishmentSet");
		return replenishmentset;
	}
	
	public String generateRconfigStatusQuery(String tenant, String bu, String status) throws Exception {
		String query = "qualifier.eq:" + tenant + "." + URLEncoder.encode(bu, "UTF-8") + "___status.eq:" + status;
		return query;
	}
	
	public String generateOIQuery(String tenant, String bu) throws Exception {
		String query = "qualifier.eq:" + tenant + "." + URLEncoder.encode(bu, "UTF-8");
		return query;
	}
	
	public String getOiId(String oi_status) {
		String query = "source.eq:TSR___status.eq:"+ oi_status + "___&start=0&fetchSize=1&sortBy=lastModifiedOn&sortOrder=DESC";
		return query;
	}

	public String getBuyPlanIDFromJobTrackerResponse(JobTrackerResponse jobTrackerResponse) {
		JobTrackerResponseData[] Data = jobTrackerResponse.getData();
		String remark= Data[0].getRemark();
		String BuyPlanId = remark.split("\\[")[1].split("]")[0].trim();
		return  BuyPlanId;
	}
	
	public String getTenantService(String tenant, HashMap<String, String> headers) throws Exception {
        svc = HttpExecutorService.executeHttpService(Constants.JEEVES_PATH.TENANT_SERVICE + tenant ,
                null, SERVICE_TYPE.REPLENISHMENT_TENANT.toString(), HTTPMethods.GET, "", headers);
        response = svc.getResponseBody();
        log.debug("\nTenant service response:\n" + response + "\n");
        return response;
    }

    public void updateHeaderAndValidateResponse(BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest,
                                                String buyPlanId, String role,
                                                HashMap<String, Object> requestData,
                                                VendorTermsValidationStatus expectedStatus,Object oiheader1) throws Exception {
        JeevesValidator jeevesvalidator = new JeevesValidator();
        HashMap<String, String> create_baseoi_headers = initializeHeader(role, "create");


        String updatePayload = APIUtilities.getObjectToJSON(buyPlanHeaderUpdateRequest);
        String update_OI_response = updateOIHeaders(buyPlanId, updatePayload, create_baseoi_headers, role);
        BaseOIDetailsResponse baseOIDetailsResponse = (BaseOIDetailsResponse) APIUtilities.getJsontoObject(update_OI_response, new BaseOIDetailsResponse());

        jeevesvalidator.validateTheVendorTermsValidationStatus(baseOIDetailsResponse, expectedStatus);


        Status status = baseOIDetailsResponse.getStatus();
        jeevesvalidator.validateTheResponseStatus(status, "UPDATE_OI_HEADERS");

        if(expectedStatus.equals(VendorTermsValidationStatus.SUCCESS)) {
            jeevesvalidator.validateTheUpdateHeaderResponse(baseOIDetailsResponse, requestData, role, oiheader1);
        }
    }

    public void updateOIValidateTheResponse(HashMap<String, String> upload_baseoi_headers,
                                            String buyPlanId, String role) throws Exception {
        JeevesValidator jeevesvalidator = new JeevesValidator();
        HashMap<String, String> create_baseoi_headers = initializeHeader(role, "create");
        HashMap<String, String> download_baseoi_headers = initializeHeader(role, "downloadfile");

        String uploadFile = "";
        if (role.contains("cm")) {
            uploadFile = Constants.DATA_FILES.fileModifiedByCM;
        } else {
            uploadFile = Constants.DATA_FILES.fileModifiedByVendor;
        }

        String updateOI_response = bulkUpdateOI(uploadFile,
                upload_baseoi_headers, buyPlanId, role);
        log.debug("updateOI_response:" + updateOI_response);
        jeevesvalidator.validateTheResponseStatus(updateOI_response, "UPDATE_OI");

        //check the status of the sheet, if it got uploaded or not
        String updateOI_job_id = getobjectfromjson(updateOI_response, "jobId");
        String jobtracker_status_for_updateOI = checkOIStatusInJobTracker(updateOI_job_id,
                create_baseoi_headers);
        String updateOI_remark = getobjectfromjson(jobtracker_status_for_updateOI, "remark");
        Assert.assertEquals(updateOI_remark, Constants.STATUS_MESSAGES.UPDATE_OI_REMARKS);
        log.debug(updateOI_remark + "\nDownload and upload of OI is successfull");

        //Download OIsheet
        downloadOI(buyPlanId, download_baseoi_headers, role, Constants.DATA_FILES.downloaded_file);

        //Check to see if the changes are reflected
        if (!compareExcelFiles(uploadFile, Constants.DATA_FILES.downloaded_file)) {
            Assert.fail("The values are not updated");
        }


    }
}
