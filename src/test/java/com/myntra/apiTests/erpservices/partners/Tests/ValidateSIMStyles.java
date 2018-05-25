package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.apiTests.erpservices.Constants;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateSIMStyles extends BaseTest {

	static Initialize init = new Initialize("Data/configuration");
	static Logger log = Logger.getLogger(ValidateSIMStyles.class);
	String slackuser = System.getenv("slackuser");
	String botname = System.getenv("botname");

	@Test(groups = { "ProdSanity" }, priority = 0)
	public void getSIMStylesAssignedToMulipleSellers() throws Exception {
		String filePath = new java.io.File(".").getCanonicalPath() + "/Data/payloads/EXCELSHEET/SIM_Styles.xlsx";
		System.out.println(filePath);
		List transaction_detail = DBUtilities.exSelectQuery(
				"select style_id, group_concat(seller_id) sellers from seller_item_master group by style_id having count(distinct `seller_id`) > 1;",
				"seller");

		log.debug("Total Styles that are assigned to Multiple Sellers: " + transaction_detail.size());

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("SIM Discrepancy Styles");

		Row row1 = sheet.createRow(0);
		Cell cell1 = row1.createCell(0);
		cell1.setCellValue("StyleId");
		int rowCount = 0;
		cell1 = row1.createCell(1);
		cell1.setCellValue("Sellers Assigned");
		for (int i = 0; i < transaction_detail.size(); i++) {
			Map<String, Object> hm = (Map<String, Object>) transaction_detail.get(i);
			Row row = sheet.createRow(++rowCount);
			Cell cell = row.createCell(0);
			cell.setCellValue(hm.get("style_id").toString());

			cell = row.createCell(1);
			cell.setCellValue(hm.get("sellers").toString());

		}
		try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
			wb.write(outputStream);
		}

		File file = new File(filePath);

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("token", "xoxb-116567850803-aclwfjP19ZxpWMWLkB67v317", ContentType.TEXT_PLAIN);
		builder.addTextBody("channels", "#prod_sim_styles_discr", ContentType.TEXT_PLAIN);
		builder.addTextBody("initial_comment",
				"Total Styles that are mapped to Multiple Sellers: " + transaction_detail.size(),
				ContentType.TEXT_PLAIN);

		builder.addBinaryBody("file", new FileInputStream(file), ContentType.APPLICATION_OCTET_STREAM, file.getName());

		Svc service = HttpExecutorService.executeHttpServiceForUpload(Constants.SLACK_UPLOAD_PATH.UPLOAD, null,
				SERVICE_TYPE.WEBHOOKSLACKCHANNEL_SVC.toString(), HTTPMethods.POST, builder, getPartnerConnectAPIHeader());

	}

	private HashMap<String, String> getPartnerConnectAPIHeader() {
		HashMap<String, String> createPartnerConnectServiceHeaders = new HashMap<String, String>();
		createPartnerConnectServiceHeaders.put("Accept", "application/x-www-form-urlencoded");
		return createPartnerConnectServiceHeaders;
	}

}
