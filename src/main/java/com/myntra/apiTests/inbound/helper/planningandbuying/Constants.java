package com.myntra.apiTests.inbound.helper.planningandbuying;


import com.sun.jersey.core.header.FormDataContentDisposition;

import java.util.HashMap;

public class Constants {

	public static class JEEVES_PATH {
		public static final String BASE_OI_HEADERS = "order-indent/baseorderindent";
		public static final String BASE_OI_UPLOAD = "order-indent/baseorderindent/uploadbaseorderindentsheet/";
		public static final String UPDATE_OI_HEADERS = "order-indent/buyplan/";
		public static final String BULK_UPDATE_OI = "order-indent/buyplan/";
		public static final String FETCH_OI = "order-indent/buyplan";
		public static final String OI_STATUS_UPDATE = "order-indent/buyplan/";
		public static final String INLINE_EDIT = "order-indent/buyplandetail/";
		public static final String EXCEL_DOWNLOAD = "order-indent/buyplan/getstream?id=";
		public static final String SEARCH_QUERY_OI = "order-indent/buyplan/search?q=";
		public static final String EXPIRE_BUY_PLAN = "order-indent/buyplan/expire?period=";
		public static final String CREATE_PI = "order-indent/buyplan/createpi/";
		public static final String UPLOAD_ATTRIBUTE = "order-indent/buyplan/uploadattributesheet?id=";
		public static final String DOWNLOAD_ATTRIBUTE = "order-indent/buyplan/downloadattributesheet/";
		public static final String DELETE_ATTRIBUTE = "order-indent/buyplan/deleteattributesheet?attachmentid=";
		public static final String JOB_TRACKER_STATUS = "job-tracker-service/jobtracker/job/";
		public static final String TSR_RUN_CONFIG = "computemetrics?businessunit=";
		public static final String SEARCH_QUERY = "job-tracker-service/jobtracker/job/search?q=";
		public static final String TSR_GENERATE_OI = "replenish";
		public static final String UPLOAD_TSR_RCONFIG = "importexcel";
		public static final String DOWNLOAD_TSR_RCONFIG = "exportexcel?businessunit=";
		public static final String SUMMARY_DATA_FOR_BU = "getsummary?businessunit=";
		public static final String REPLENISHMENT_SET = "getreplenishmentset?businessunit=";
		public static final String EXPORT_REPLENISHMENT_SET = "exportreplenishmentset?businessunit=";
		public static final String DOWNLOAD_TEMPLATE = "order-indent/baseorderindent/sampletemplate?orderIndentType=";
		public static final String ALL_BRANDS = "globalattribute/v2/allBrands";
		public static final String ALL_ARTICLE_TYPE = "productcategory/all";
		public static final String ALL_GENDER = "attributeValidValues/myntra_mobile_app/gender/list/all";
		public static final String ALL_MARGIN_TYPE = "taxonomy/MarginBasis";
		public static final String ALL_LEAD_TIME_CATEGORY = "manufacturing/plans/APPAREL";
		public static final String TENANT_SERVICE = "replenishment-tenant-service/tenant/";
	}

	public static class ROLES {
		public static final String CM_ROLE = "role=cm";
		public static final String VENDOR_ROLE = "role=vendor&vendorId=";
		public static final String UCB_VENDOR_ROLE = "role=vendor&vendorId=8";
		public static final String FF_VENDOR_ROLE = "role=vendor&vendorId=39";
		public static final String VENDOR_ROLE_2 = "role=vendor&vendorId=623";
		public static final String BATA_VENDOR_ROLE = "role=vendor&vendorId=623";
		public static final String CM_ID = "22";

	}

	public static class HEADERS {
		public static final String AUTHORIZATION = "Basic YTpi";
		public static final String UPLOAD_OI_AUTHORIZATION = "Basic Y21+ZHVtbXl1c2VyaWR+OmR1bW15cGFzcw==";
		public static final String CM_BENETTON_UPLOAD_OI_AUTHORIZATION = "Basic Y21+ZHVtbXl1c2VyaWR+OmR1bW15cGFzcw==";
		public static final String VENDOR_BENETTON_UPLOAD_OI_AUTHORIZATION = "Basic dmVuZG9yfmJlbmV0dG9ufjpiZW5ldHRvbjEyMw==";
		public static final String ACCEPT = "application/json";
		public static final String CONTENTTYPE = "application/json";
		public static final String OCTED_CONTENTTYPE = "application/octet-stream";
		public static final String MULTIPART_CONTENTTYPE = "multipart/form-data";
		public static final String ACCEPT_XML_FORMAT = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		public static final String[] replenishment_style_sheet_headers = { "Brand Catalog Id", "Vendor Article Number", "Vendor Article Name",
				"StyleID", "Article Type", "Gender", "Vendor color", "Brand", "Season code", "Commercial Type",
				"Fashion Type", "DOH", "ROS", "RGM per unit", "Discount", "Age", "Open Purchase Order Qty", "Current Stock",
				"Gross margin", "Quantity sold overall", "Quantity sold in timeframe", "Mrp", "Simple forecast",
				"System forecast", "Replenish quantity", "Item purchase price", "Required OTB", "Priority score",
				"IsTopSeller", "Rate Of Returns" };
	}


	public static class OIHeaderUCB extends VendorData {
		public  OIHeaderUCB()
		{
			//Integer LP_VENDOR_ID = 167;
			VENDOR_ID =8;
			//String  LP_VENDOR_NAME = "Aditya Birla Nuvo Ltd";
			VENDOR_NAME ="Benetton India Pvt. Ltd";
		}

	}

	public static class OIHeaderFF extends VendorData {
		public  OIHeaderFF()
		{
			VENDOR_ID =1277;
			VENDOR_NAME ="Fractal Fashion";
			CM_EMAIL_ID2 = "garima.juyal@myntra.com";
		}



	}

	public static class OIHeaderBATA extends VendorData{

		public String getCM_EMAIL_ID() { return this.CM_EMAIL_ID; }

		public String getCM_EMAIL_ID2() { return this.CM_EMAIL_ID2;}

		public Long getTERMS_ADDITIONAL_CLASSIFICATION() { return TERMS_ADDITIONAL_CLASSIFICATION; }

		public OIHeaderBATA(){
			VENDOR_ID=623;
			VENDOR_NAME = "Bata India Limited";
		}
	}


	public static class STATUS_CODES {
		public static final String CREATE_OI = "23003";
		public static final String UPLOAD_OI = "23004";
		public static final String UPDATE_OI = "21005";
		public static final String UPDATE_OI_REMARKS = "";
		public static final String JOB_TRACKER = "3";
		public static final String SEARCH_OI = "21001";
		public static final String UPDATE_OI_HEADERS = "21002";
		public static final String ATTRIBUTE_SHEET_UPLOAD = "21104";
		public static final String CREATE_PI = "21001";
		public static final String UPLOAD_TSR_RCONFIG = "7004";
		public static final String GENERATE_OI = "9005";
		public static final String TENANT_SERVICE = "3";
	}

	public static class STATUS_MESSAGES {
		public static final String CREATE_OI = "Base Order Indent added successfully";
		public static final String UPLOAD_OI = "Base Order Indent excel sheet uploaded successfully";
		public static final String UPDATE_OI = "Buy Plan excel sheet uploaded successfully";
		public static final String SEARCH_OI = "Buy Plan fetched successfully";
		public static final String UPDATE_OI_HEADERS = "Buy Plan updated successfully";
		public static final String ATTRIBUTE_SHEET_UPLOAD = "Attribute sheet uploaded successfully";
		public static final String CREATE_PI = "Buy Plan fetched successfully";
		public static final String UPDATE_OI_REMARKS = "Completed the updation of OI details";
		public static final String UPLOAD_TSR_RCONFIG = "Replenishment configuration added successfully";
		public static final String GENERATE_OI = "Replenished successfully";
		public static final String JOB_TRACKER = "Success";
		public static final String TENANT_SERVICE = "Success";
	}

	public static class ERROR_MESSAGE {
		public static final String EXCEL_SHEET_ERROR_MSG1 = "Unable to find BU, CM for: for: Brand:United Colors of Benetton articleType:Bangle gender:Men";
		public static final String EXCEL_SHEET_ERROR_MSG2 = "Article Type is either empty or invalid";
		public static final String NO_ATTRIBUTE_SHEET_FOUND_ERROR = "No Attribute sheet found for the order indent id";
	}

	public static class BUSINESS_UNITS {
		public static final String SPORTS = "Sports";
		public static final String MENS_CASUAL_FOOTWEAR = "men's casual footwear";
		public static final String MENS_CASUAL = "men's casual";
	}

	public static class TENANTS {
		public static final String SJIT = "sjit";
		public static final String MMB = "mmb";
		public static final String RAPID = "rapid";
	}

	public static class VMS_PATH {
		public static final String ADD_VENDOR_CATEGORY_MANAGER = "vendors/categoryManager/create/";
	}

	public static class DATA_FILES {
		public static FormDataContentDisposition fileDetail;
		public static final String userdir = System.getProperty("user.dir");
		public static final String invalid_file = userdir + "/Data/payloads/EXCELSHEET/InvalidFileFormat.txt";
		public static final String file1 = userdir + "/Data/payloads/EXCELSHEET/UCB_228_ENTRIES.xlsx";
		public static final String file3 = userdir + "/Data/payloads/EXCELSHEET/UCB_ENTRIES_VendorTerms.xlsx";
		public static final String fileModifiedByCM = userdir + "/Data/payloads/EXCELSHEET/skulookup/BATA_SKU_Loookup_251_Update.xlsx";
		public static final String fileModifiedByVendor = userdir + "/Data/payloads/EXCELSHEET/skulookup/BATA_SKU_Loookup_251_UpdateVendor.xlsx";
		public static final String file2 = userdir + "/Data/payloads/EXCELSHEET/updated_ucb_oi_sheet.xlsx";
		public static final String file6 = userdir + "/Data/payloads/EXCELSHEET/skulookup/BATA_VendorTerms.xlsx";
		public static final String file7 = userdir + "/Data/payloads/EXCELSHEET/Roadster-MFB-Data.xlsx";
		public static final String attributeSheet = userdir + "/Data/payloads/EXCELSHEET/skulookup/BATA_AttributeSheet.xlsx";
		public static final String skuLookupFile250 = userdir + "/Data/payloads/EXCELSHEET/skulookup/BATA_SKU_Loookup_250.xlsx";
		public static final String skuLookupFile251 = userdir + "/Data/payloads/EXCELSHEET/skulookup/BATA_SKU_Loookup_251.xlsx";
		public static final String skuLookupFile4333 = userdir + "/Data/payloads/EXCELSHEET/skulookup/BATA_SKU_Loookup_4333.xlsx";
		public static final String ucb_failed_skulookup_file = userdir+ "/Data/payloads/EXCELSHEET/ucb_failed_skulookup_sheet.xlsx";
		public static final String invalid_entry_file = userdir + "/Data/payloads/EXCELSHEET/ucb_invalid_article_type_entry.xlsx";
		public static final String missing_mandatory_field_entry_file = userdir + "/Data/payloads/EXCELSHEET/ucb_missing_field_entry.xlsx";
		public static final String error_file = userdir + "/target/errorfile.xlsx";
		public static final String downloaded_file = userdir + "/target/downloadfile.xlsx";
		public static final String downloaded_rconfig = userdir + "/target/downloadrconfig.xlsx";
		public static final String pending_with_partner_state = "{\"status\":\"PENDING_WITH_PARTNER\"}";
		public static final String pending_with_myntra_state = "{\"status\":\"PENDING_WITH_MYNTRA\"}";
		public static final String bu_sports_generate_oi_status_query = "qualifier.eq:sports___&start=0&fetchSize=1&sortBy=lastModifiedOn&sortOrder=DESC";
		public static final String OI_query = "source.eq:TSR___status.eq:DRAFT___&start=0&fetchSize=1&sortBy=lastModifiedOn&sortOrder=DESC";
		public static final String filter = "&groupby=commercialtype,articletype,brand";
		public static final String OI_Error_data =userdir +  "/Data/payloads/EXCELSHEET/CreateOIErrorData.xlsx";
		public static final String VendorTermsErrorData_Error_data =userdir +  "/Data/payloads/EXCELSHEET/skulookup/BATA_Create_VendorTerms.xlsx";
		public static final String OI_data_file =userdir +  "/Data/payloads/EXCELSHEET/DataFile.xlsx";
		public static final String sjit_tenant_mens_casual_rconfig_file = userdir + "/Data/payloads/EXCELSHEET/sjit_tenant_mens_casual.xlsx";
		public static final String mmb_tenant_mens_casual_rconfig_file  = userdir + "/Data/payloads/EXCELSHEET/mmb_tenant_mens_casual.xlsx";
		public static final String rapid_tenant_mens_casual_rconfig_file  = userdir + "/Data/payloads/EXCELSHEET/rapid_tenant_mens_casual.xlsx";
	}

	public static class PAYLOADS {
		public static final String MENS_CAUSAL_FOOTWEAR = "{\"businessunit\":\"men's casual footwear\"}";
		public static final String SPORTS = "{\"businessunit\":\"sports\"}";

	}

	public static class JOB_TRACKER_STATUS {
		public static final String CREATED = "CREATED";
		public static final String INPROGRESS = "IN_PROGRESS";
		public static final String INTERRUPTED = "INTERRUPTED";
		public static final String COMPLETED = "COMPLETED";
	}

	public static class TEMPLATE_HEADERS {
		public static  final String[] MMB = {
			"Brand","GTIN","Vendor Article Number","Vendor Article Name","Moq","Size","Colour","Article Type",
				"Gender","Core/Fashion","Mrp","Margin","Margin Type","Order Qty"
		};
		public static  final String[] MFB = {
				"Brand","GTIN","Vendor Article Number","Vendor Article Name","Moq","Size",
				"Colour","Article Type","Gender","Core/Fashion","Mrp","Margin","Margin Type","Order Qty",
				"ListPrice(fob+transport-excise)","Excise Duty Percent","Order Confirmation Date(DD/MM/YYYY)",
				"Lead Time Category","Estimated Delivery Date(DD/MM/YYYY)"
		};
		public static  final String[] columnsForMFB = {
				"Brand","Article Type","Gender","Core/Fashion","Margin Type","Lead Time Category"
		};
		public static  final String[] columnsForMMB = {
				"Brand","Article Type","Gender","Core/Fashion","Margin Type"
		};
		public static final String excelDataErrorMessage = "Please choose one among the values in the list only";
		static HashMap<String,String> columnMap = new HashMap<>();

		public static void setColumnMap() {
			columnMap.put("A","Brand");
			columnMap.put("H","Article Type");
			columnMap.put("I","Gender");
			columnMap.put("J","Core/Fashion");
			columnMap.put("M","Margin Type");
			columnMap.put("R","Lead Time Category");
		}

		public static String getColumnMapValue(String str) {
			return columnMap.get(str);
		}
	}
	
	public static class RUN_CONFIG_FILTERS {
        public static final Integer DOH = 14;
        public static final Integer ROS = 1;
        public static final Integer RGM = 10;
        public static final Integer DISCOUNT = 75;
        public static final Integer AGE = 100;
        public static final Integer RATE_OF_RETURNS = 25;
    }

	public static class TENANT_ATTRIBUTES {
		public static final String TENANT_DEF_SEARCH_ATTR_BRAND_TYPE = "styleAttributes.brand_type";
		public static final String RAPID_TENANT_DEF_SEARCH_ATTR_BRAND_TYPE = "styleAttributes_source.brand_type";
		public static final String MMB_TENANT_DEF_SEARCH_ATTR_BRAND_TYPE_VALUE = "EXTERNAL";
		public static final String TENANT_DEF_INNER_EXP_COMMERCIAL_TYPE = "styleAttributes.commercial_type";
		public static final String RAPID_TENANT_DEF_INNER_EXP_COMMERCIAL_TYPE = "styleAttributes_source.commercial_type";
		public static final String[] MMB_TENANT_DEF_INNER_EXP_COMMERCIAL_TYPE_VALUE = {"SOR", "OUTRIGHT"};
		public static final String TENANT_DEF_INNER_EXP_SUPPLY_TYPE = "styleAttributes.supply_type";
		public static final String MMB_TENANT_DEF_INNER_EXP_SUPPLY_TYPE_VALUE = "ON_HAND";
		public static final String RCONFIG_META_DATA_LEVEL = "business_unit";
		public static final String[] RCONFIG_META_DATA_CONFIG_FIELDS = {"ArticleType", "Gender"};
		public static final String WORKFLOW_METADATA_ARTIFACT = "order-indent";
		public static final String[] MMB_WORKFLOW_METADATA_SPLITLOGIC = {"business_unit", "commercial_type", "vendor_id", "category_manager", "fashion_type"};
		public static final String MMB_WORKSPACE_METADATA_INDEX = "top_sellers";
		public static final String WORKSPACE_METADATA_DOC_TYPE = "product";
		public static final String SJIT_TENANT_DEF_SEARCH_ATTR_BRAND_TYPE_VALUE = "External";
		public static final String[] SJIT_TENANT_DEF_INNER_EXP_COMMERCIAL_TYPE_VALUE = {"SOR", "OR"};
		public static final String[] SJIT_TENANT_DEF_INNER_EXP_SUPPLY_TYPE_VALUE = {"on_hand", "just_in_time"};
		public static final String[] SJIT_WORKFLOW_METADATA_SPLITLOGIC = {"business_unit", "commercial_type", "vendor_id", "category_manager"};
		public static final String SJIT_WORKSPACE_METADATA_INDEX = "sjit_top_sellers";
		public static final String[] RAPID_TENANT_DEF_INNER_EXP_COMMERCIAL_TYPE_VALUE = {"outright", "sor"};
		public static final String RAPID_TENANT_DEF_SEARCH_ATTR_BRAND_TYPE_VALUE = "private";
		public static final String[] RAPID_TENANT_DEF_INNER_EXP_SUPPLY_TYPE_VALUE = {"on_hand", "just_in_time"};
		public static final String[] RAPID_WORKFLOW_METADATA_SPLITLOGIC = {"business_unit", "commercial_type", "vendor_id", "category_manager"};
		public static final String RAPID_WORKSPACE_METADATA_INDEX = "rapid_top_sellers";
		public static final String TENANT_MMB_NAME = "MMB";
		public static final String TENANT_MMB_DESCRIPTION = "MMB replenishment";
		public static final String TENANT_SJIT_NAME = "Smart JIT";
		public static final String TENANT_SJIT_DESCRIPTION = "SJIT replenishment";
		public static final String TENANT_RAPID_NAME = "Rapid";
		public static final String TENANT_RAPID_DESCRIPTION = "Rapid replenishment";
	}
}
