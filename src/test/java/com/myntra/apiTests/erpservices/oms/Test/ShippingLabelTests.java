package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.Testcase;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.oms.TestSuite;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.lms.client.response.PincodeEntry;
import com.myntra.lms.client.response.PincodeResponse;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.parser.CSVParser;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.release.doc.client.entry.SellerDetailsEntry;
import com.myntra.release.doc.client.entry.ShippingLabelEntry;
import com.myntra.test.commons.testbase.BaseTest;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * @author abhijit.pati
 * @since 1st Aug 2016
 */
public class ShippingLabelTests extends BaseTest {

    org.slf4j.Logger log = LoggerFactory.getLogger(End2EndHelper.class);

    String[] expectedMap = {"GiftWrap","TOD","giftcardamount","id","seller_id","SellerName","SellerAddress","ReturnAddress","tinnumber","cstNumber","receiver_name","address","user_contact_no","mobile","shipmentType","shipping_method","city","country","locality","stateDisplayName","zipcode","payment_method","courier_code","tracking_no","dcInfo","final_amount","codamountinwords","customer_promise_time"};
    String[] inputMap = {"Username", "password","SkuID", "AddressID", "coupon","CashBack","CashBackAmount","LoyaltyAmount", "Loyalty", "GiftWrap", "GiftCard", "TOD", "id"};
    private End2EndHelper end2EndHelper = new End2EndHelper();
    private OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    private LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
    private BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();

	private OrderEntry orderEntry;

	private String orderReleaseId;


    @BeforeTest
    public void beforeClass() throws SQLException, IOException, JAXBException {
    	String orderID = "80128470";
        String payment_Plan = "INSERT INTO `payment_plan` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `clientTransactionId`, `crmRefId`, `login`, `orderId`, `ppsType`, `sourceId`, `state`, `sessionId`, `cartContext`, `totalAmount`, `mobile`, `returnId`, `userAgent`, `clientIP`, `bankAccountId`)  VALUES ('a2cdd897-3132-441a-a94a-87ce417852c1', 'PPS Plan created', 'SYSTEM', 1472104262332, 'SALE', NULL, NULL, 'e9183069.5c43.445d.ae5b.75e867618788igBqC1CKVl', '80128470', 'ORDER', '7d74a805-8199-449e-9613-9ce042d9c0ea--s1', 'PPFSM Order Taking done', 'JJN7becc4336a8711e6baa622000a90a0271472104081G', 'DEFAULT', 254500, '1234567890', NULL, 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36 ', '182.156.75.166', NULL);";
        String payment_plan_item = "INSERT INTO `payment_plan_item` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `itemType`, `pricePerUnit`, `quantity`, `sellerId`, `skuId`, `pps_Id`)  VALUES  (79998125502, 'Payment Plan Item created', 'SYSTEM', 1472104261213, 'SHIPPING_CHARGES', 14800, 1, '19', 'PPS_9999', 'a2cdd897-3132-441a-a94a-87ce417852c1'),  (79998125503, 'Payment Plan Item created', 'SYSTEM', 1472104261218, 'SKU', 79900, 3, '19', '3831', 'a2cdd897-3132-441a-a94a-87ce417852c1');";
        String payment_plan_item_instrument = "INSERT INTO `payment_plan_item_instrument` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, `ppsItemId`, `actionType`)  VALUES  (79998131015, 'Payment Plan Item Instrument Detail created', 'SYSTEM', 1472104261214, 11630, 6, 79998125502, NULL),  (79998131016, 'Payment Plan Item Instrument Detail created', 'SYSTEM', 1472104261216, 3169, 5, 79998125502, NULL),  (79998131017, 'Payment Plan Item Instrument Detail created', 'SYSTEM', 1472104261219, 188369, 6, 79998125503, NULL),  (79998131018, 'Payment Plan Item Instrument Detail created', 'SYSTEM', 1472104261220, 51330, 5, 79998125503, NULL);";
        String payment_plan_instrument_details = "INSERT INTO `payment_plan_instrument_details` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `paymentInstrumentType`, `totalPrice`, `pps_Id`, `paymentPlanExecutionStatus_id`, `actionType`, `parentInstrumentDetailId`)  VALUES  (79998116330, 'PPS Plan Instrument Details created', 'SYSTEM', 1472104262056, 6, 199999, 'a2cdd897-3132-441a-a94a-87ce417852c1', 10106214, 'DEBIT', NULL),  (79998116331, 'PPS Plan Instrument Details created', 'SYSTEM', 1472104262290, 5, 54499, 'a2cdd897-3132-441a-a94a-87ce417852c1', 10106215, 'DEBIT', NULL);";

        String xcart_order = "INSERT INTO `xcart_orders` (`orderid`, `invoiceid`, `login`, `membership`, `total`, `giftcert_discount`, `giftcert_ids`, `subtotal`, `discount`, `cart_discount`, `ref_discount`, `coupon`, `coupon_discount`, `shippingid`, `tracking`, `shipping_cost`, `tax`, `taxes_applied`, `date`, `status`, `payment_method`, `flag`, `notes`, `details`, `customer_notes`, `customer`, `title`, `firstname`, `lastname`, `company`, `b_title`, `b_firstname`, `b_lastname`, `b_address`, `b_city`, `b_county`, `b_state`, `b_country`, `b_zipcode`, `b_mobile`, `b_email`, `s_title`, `s_firstname`, `s_lastname`, `s_address`, `s_city`, `s_county`, `s_state`, `s_country`, `s_zipcode`, `s_email`, `phone`, `fax`, `url`, `email`, `recipient_email`, `language`, `clickid`, `extra`, `membershipid`, `paymentid`, `payment_surcharge`, `tax_number`, `tax_exempt`, `shipping_method`, `qtyInOrder`, `mobile`, `ordertype`, `ccavenue`, `gift_status`, `gift_pack`, `gift_charges`, `affiliateid`, `cod`, `queueddate`, `completeddate`, `changerequest_status`, `corporate_request_id`, `quote_number`, `cheque_no`, `cheque_date`, `cheque_bounce`, `shopid`, `aff_discount`, `guid`, `aff_orderid`, `feedback_request`, `orgid`, `courier_service`, `shipment_preferences`, `issues_contact_number`, `additional_info`, `tf_status`, `order_name`, `source_id`, `operations_location`, `shop_bill_number`, `pos_customer_mrp`, `card_type`, `gateway`, `collected_amount`, `refund_amount`, `payment_method_pos`, `cod_pay_status`, `cod_payment_date`, `order_return_reason`, `parent_orderid`, `cashback`, `canceltime`, `cash_redeemed`, `store_credit_usage`, `earned_credit_usage`, `cash_coupon_code`, `pg_discount`, `warehouseid`, `shippeddate`, `delivereddate`, `cashback_processed`, `request_server`, `response_server`, `channel`, `cancellation_code`, `s_locality`, `is_on_hold`, `on_hold_reason_id_fk`, `group_id`, `splitdate`, `packeddate`, `weight`, `is_refunded`, `loyalty_points_used`, `loyalty_points_awarded`, `loyalty_points_conversion_factor`, `gift_card_amount`, `pps_id`) VALUES (80128470, NULL, 'e9183069.5c43.445d.ae5b.75e867618788igBqC1CKVl', '', 2544.98, 0.00, '', 2397.00, 0.00, 0.00, 0.00, '', 0.00, 0, '', 148.00, 0.00, '', 1472104242, 'PV', 'cod', 'N', '', '', '', 'Abhijit', '', 'Abhijit', 'Pati', '', '', 'Abhijit', 'Pati', '', '', '', '', '', '', '1234567890', 'abhijit.pati@myntra.com', '', 'ABHIJIT', '', 'AKR TECH PARK', 'Bangalore', '', 'KA', 'India', '560068', 'abhijit.pati&#x40;myntra.com', '1234567890', '', '', 'abhijit.pati@myntra.com', NULL, 'US', 0, '', 0, 0, 0.00, '', '\\0', 'NORMAL', NULL, '1234567890', 'on', NULL, 'N', 'N', 0.00, 0, 0.00, 1472104243, NULL, 'N', NULL, NULL, NULL, NULL, 'N', NULL, 0.00, '', 0, 'N', 0, '', NULL, '1234567890', '', 'NF', NULL, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'pending', NULL, NULL, 80128470, NULL, NULL, 0.00, 0.00, 0.00, '', 0.00, NULL, NULL, NULL, 1, NULL, NULL, 'web', NULL, 'Bommanahalli  &#x28;Bangalore&#x29;', 0, 39, 80128470, NULL, NULL, NULL, 0, 0.00, 0.00, 0.000, 0.00, 'a2cdd897-3132-441a-a94a-87ce417852c1');";
        String xcart_order_detils = "INSERT INTO `xcart_order_details` (`itemid`, `orderid`, `productid`, `price`, `amount`, `provider`, `product_options`, `extra_data`, `productcode`, `product`, `product_style`, `product_type`, `discount`, `discount_quantity`, `cart_discount_split_on_ratio`, `actual_product_price`, `quantity_breakup`, `item_status`, `assignee`, `location`, `assignment_time`, `completion_time`, `sent_date`, `tax_rate`, `taxamount`, `shipping_amount`, `shipping_tax`, `total_amount`, `tpl_used_id`, `promotion_id`, `style_template`, `estimated_processing_date`, `addonid`, `pos_unit_mrp`, `pos_total_amount`, `type_fulfillment_pos`, `is_customizable`, `coupon_code`, `coupon_discount_product`, `cashback`, `applied_discount_rule`, `applied_discount_algoid`, `applied_discount_data`, `canceltime`, `cash_redeemed`, `store_credit_usage`, `earned_credit_usage`, `cash_coupon_code`, `jit_purchase`, `pg_discount`, `qapass_items`, `cancellation_code`, `discount_rule_id`, `discount_rule_rev_id`, `is_discounted`, `is_returnable`, `is_exchangeable`, `difference_refund`, `item_loyalty_points_used`, `item_loyalty_points_awarded`, `seller_id`, `supply_type`, `packaging_type`, `is_packaged`, `govt_tax_rate`, `govt_tax_amount`, `gift_card_amount`, `sku_id`, `option_id`, `is_hazmat`, `is_jewellery`, `is_fragile`, `is_try_and_buy`, `seller_funded_discount_per_unit`) VALUES (70145548, 80128470, 1531, 799.00, 3, '', '', '', '', '', 1531, 289, 0.00, 0, 0.00, 799.00, NULL, 'UA', NULL, NULL, NULL, NULL, NULL, 5.00, 0.00, NULL, NULL, 2396.99, 0, 0, NULL, 0, NULL, NULL, NULL, NULL, 0, '', 0.00, 0.00, NULL, NULL, NULL, NULL, 0.00, 0.00, 0.00, '', 0, 0.00, NULL, NULL, 0, 0, 0, 1, 1, 0.00, 0.00, 0.00, 19, 'ON_HAND', 'NORMAL', 0, 0.000, 0.00, 0.00, 3831, 4852, 0, 0, 0, 0, 0.00);";

        omsServiceHelper.deletePPSRecords("'a2cdd897-3132-441a-a94a-87ce417852c1'");
        bountyServiceHelper.deleteBountyDBEntries(orderID);
        omsServiceHelper.deleteOMSDBEntriesForOrderID(""+orderID);

        DBUtilities.exUpdateQuery(xcart_order,"order");
        DBUtilities.exUpdateQuery(xcart_order_detils,"order");
        DBUtilities.exUpdateQuery(payment_Plan, "pps");
        DBUtilities.exUpdateQuery(payment_plan_item, "pps");
        DBUtilities.exUpdateQuery(payment_plan_item_instrument, "pps");
        DBUtilities.exUpdateQuery(payment_plan_instrument_details, "pps");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        bountyServiceHelper.pushOrderToOMS(orderReleaseId);

        String message = "<orderUpdateEntry><version>0</version><giftCardAmount>0.0</giftCardAmount><onHold>false</onHold><orderId>"+orderID+"</orderId><status>Q</status></orderUpdateEntry>";
        end2EndHelper.pushMessageToQueue(message,"orderConfirmedEvents","oms");
        end2EndHelper.sleep(1000);
    }

    @Test(dataProvider = "ShippingLabelDP")
    public void testInvoice(String description, Testcase tc) throws Exception {
        log.info("Starting Test "+ description);
        Map input = tc.getInput();
        Map output = tc.getOutput();

        Map<String, Object> expectedOutPut = new HashMap<>();

        String orderID = createOrder(input);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        Map<String, Object> releaseDBEntry = (HashMap<String, Object>) omsServiceHelper.getOrderReleaseDBEntry(orderID).get(0);
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);

        PincodeResponse pincodeRespose = lmsServiceHelper.getPincode(releaseDBEntry.get("zipcode").toString(), releaseDBEntry.get("courier_code").toString());

        PincodeEntry pincodeEntry = pincodeRespose.getPincodes().get(0);

        String returnaddress = omsServiceHelper.getRTOAddress(releaseDBEntry.get("zipcode").toString(), releaseDBEntry.get("courier_code").toString(), ""+orderReleaseEntry.getWarehouseId());

        Map<String, String> sellerDetail = omsServiceHelper.getSellerAddress(orderReleaseEntry.getWarehouseId(), orderReleaseEntry.getSellerId());


        Iterator entries = output.entrySet().iterator();

        while (entries.hasNext()) {

            Map.Entry entry = (Map.Entry) entries.next();
            String value = entry.getValue().toString();

            if (entry.getKey().toString().toLowerCase().contains("customer_promise_time")){
                expectedOutPut.put(entry.getKey().toString() , orderReleaseEntry.getCustomerPromiseTime());
            }else if(entry.getKey().toString().toLowerCase().matches("sellername|selleraddress|tinnumber|cstnumber")){
                expectedOutPut.put(entry.getKey().toString().toLowerCase(), sellerDetail.get(entry.getKey().toString().toLowerCase()));
            }else if(entry.getKey().toString().toLowerCase().matches("returnaddress")){
                expectedOutPut.put(entry.getKey().toString(), returnaddress);
            }else if(value.toLowerCase().contains("false"))
                expectedOutPut.put(entry.getKey().toString(), value.toLowerCase());
            else if(entry.getKey().toString().toLowerCase().contains("dcinfo"))
                expectedOutPut.put(entry.getKey().toString(), pincodeEntry.getCityCode() +" / " + pincodeEntry.getDeliveryCenterCode());
            else if(value.equalsIgnoreCase("db"))
                expectedOutPut.put(entry.getKey().toString(), releaseDBEntry.get(entry.getKey()).toString());
            else
                expectedOutPut.put(entry.getKey().toString(), value);
        }

        ShippingLabelEntry shippingLabelEntry = omsServiceHelper.getShippingLabelEntry(orderReleaseId);
        validateShippingLabelResponse(shippingLabelEntry, expectedOutPut);
    }

    private void validateShippingLabelResponse(ShippingLabelEntry shippingLabelEntry, Map expectedOutPut){
        SoftAssert sft = new SoftAssert();
        sft.assertEquals(shippingLabelEntry.getAddress(), expectedOutPut.get("address"), "Address ");
        sft.assertEquals(shippingLabelEntry.getCountry(), expectedOutPut.get("country"), "County ");
        sft.assertEquals(shippingLabelEntry.getOrderReleaseId().toString(), expectedOutPut.get("id").toString(), "Release ID ");
        sft.assertEquals(shippingLabelEntry.getCity(), expectedOutPut.get("city"), "City ");
        sft.assertEquals(shippingLabelEntry.getLocality(), expectedOutPut.get("locality"), "Locality ");
        sft.assertEquals(shippingLabelEntry.getMobile().toString(), expectedOutPut.get("mobile").toString(), "Mobile ");
        sft.assertEquals(shippingLabelEntry.getPaymentMethod(), expectedOutPut.get("payment_method"), "Payment Method ");
        if(shippingLabelEntry.getCodAmountInWords() != null){
            sft.assertEquals(shippingLabelEntry.getCodAmountInWords(), expectedOutPut.get("codamountinwords"), "COD Amount in Words ");
        }
        sft.assertEquals(shippingLabelEntry.getFinalAmount().doubleValue(), Double.parseDouble(expectedOutPut.get("final_amount").toString()), 0.01, "Final Amount: ");
        sft.assertEquals(shippingLabelEntry.getReceiverName(), expectedOutPut.get("receiver_name"), "Receiver ");
        sft.assertEquals(shippingLabelEntry.isTryAndBuy().booleanValue(), Boolean.parseBoolean(expectedOutPut.get("tod").toString()), "Try On Deliver Enabled ");
        sft.assertEquals(shippingLabelEntry.isGift().booleanValue(), Boolean.parseBoolean(expectedOutPut.get("giftwrap").toString()), "GiftWrap Enabled ");
        sft.assertEquals(shippingLabelEntry.getGiftCardAmount().doubleValue(), Double.parseDouble(expectedOutPut.get("giftcardamount").toString()), "Gift Card Amount ");

        sft.assertEquals(shippingLabelEntry.getCourierCode(), expectedOutPut.get("courier_code"), "Courier Code ");
        sft.assertEquals(shippingLabelEntry.getCustomerPromiseTime(), expectedOutPut.get("customer_promise_time"), "Customer Promise Time ");
       // sft.assertEquals(shippingLabelEntry.getDcInfo(), expectedOutPut.get("dcinfo"), "DC Info ");
        sft.assertEquals(shippingLabelEntry.getZipcode(), expectedOutPut.get("zipcode"), "ZIP Code");
        sft.assertEquals(shippingLabelEntry.getUserContactNo(), expectedOutPut.get("user_contact_no"), "User Contact Number ");
        sft.assertEquals(shippingLabelEntry.getTrackingNo(), expectedOutPut.get("tracking_no"), "Tracking Number ");
        sft.assertEquals(shippingLabelEntry.getStateDisplayName(), expectedOutPut.get("statedisplayname") , "State Display Name ");
        sft.assertEquals(shippingLabelEntry.getShippingMethod(), expectedOutPut.get("shipping_method"), "Shipping Method ");
        sft.assertEquals(shippingLabelEntry.getShipmentType(), expectedOutPut.get("shipmenttype"), "Shipping Type ");
        //
        SellerDetailsEntry sellerDetailsEntry = shippingLabelEntry.getListOfSellers().get(0);
        sft.assertEquals(sellerDetailsEntry.getSellerId().longValue(), Long.parseLong(expectedOutPut.get("seller_id").toString()), "Seller ID ");
        sft.assertEquals(sellerDetailsEntry.getCstNumber(), expectedOutPut.get("cstnumber") , "CST Number ");
        sft.assertEquals(sellerDetailsEntry.getReturnAddress(), expectedOutPut.get("returnaddress"), "RTO Address ");
        sft.assertEquals(sellerDetailsEntry.getSellerName(), expectedOutPut.get("sellername") , "Seller Name ");
        sft.assertEquals(sellerDetailsEntry.getTinNumber(), expectedOutPut.get("tinnumber").toString(), "Seller TIN No ");
        sft.assertEquals(sellerDetailsEntry.getAddress(), expectedOutPut.get("selleraddress"), "Seller Address ");
        sft.assertAll();
    }


	private String createOrder(Map input) throws Exception {

		String orderID = null;

        if(input.get("id") != null && !(input.get("id").toString().equalsIgnoreCase("db"))){
            orderID = input.get("id").toString();
        }else{
            if(Boolean.parseBoolean(input.get("cashback").toString()) && Boolean.parseBoolean(input.get("loyalty").toString())){
                end2EndHelper.updateloyalityAndCashBack(input.get("username").toString(), Integer.parseInt(input.get("loyaltyamount").toString()), Integer.parseInt(input.get("cashbackamount").toString()));
            }else if(Boolean.parseBoolean(input.get("loyalty").toString())){
                end2EndHelper.updateloyalityAndCashBack(input.get("username").toString(), Integer.parseInt(input.get("loyaltyamount").toString()), 0);
            }else if(Boolean.parseBoolean(input.get("cashback").toString())){
                end2EndHelper.updateloyalityAndCashBack(input.get("username").toString(), 0, Integer.parseInt(input.get("cashBackamount").toString()));
            }

            orderID = end2EndHelper.createOrder(input.get("username").toString(), input.get("password").toString(), input.get("addressid").toString(), input.get("skuid").toString().split(","),
                                                ""+input.get("coupon"), Boolean.parseBoolean(input.get("cashback").toString()), Boolean.parseBoolean(input.get("loyalty").toString()), Boolean.parseBoolean(input.get("giftwrap").toString()), "", Boolean.parseBoolean(input.get("tod").toString()));
            omsServiceHelper.validateReleaseStatusInOMS(orderID,"WP", 15);
        }

        OrderReleaseResponse orderReleaseResponse = omsServiceHelper.stampGovtTaxForVector(""+orderID);
        DBUtilities.exUpdateQuery("update order_release set tracking_no='"+"ML"+System.currentTimeMillis() +"' where id="+orderID, "myntra_oms");
        Assert.assertEquals(orderReleaseResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
        return orderID;
    }

    @DataProvider(name = "ShippingLabelDP")
    public Object[][] invoiceDP(ITestContext testContext)
            throws SQLException, IOException, JAXBException {

        TestSuite testSuite = parseTheTestToTestSuite("scm/oms/data/ShippingLabel.csv");

        ArrayList<Testcase> testCaseList = testSuite.getTestCaseList();
        Object dataProviderObject[][] = new Object[testCaseList.size()][2];
        for (int index = 0; index < testCaseList.size(); index++) {
            Testcase testCase = testCaseList.get(index);
            dataProviderObject[index][0] = testCase.getDescription();
            dataProviderObject[index][1] = testCase;
        }
        return dataProviderObject;
    }


    public TestSuite parseTheTestToTestSuite(String path) throws IOException {
        List<Map> csvData = CSVParser.getCSVDataAsListOfMap(path);
        TestSuite testSuite = new TestSuite("Shipping Label");
        for (Map<String, String> rowAsData : csvData) {
            Testcase testcase = new Testcase(rowAsData.get("Description"));
            Map<String, String> inputMaps = getInputMaps(rowAsData);
            Map<String, String> outMaps = getOutPutMaps(rowAsData);
            testcase.setInput(inputMaps);
            testcase.setOutput(outMaps);
            testSuite.addTestCase(testcase);
        }
        return testSuite;
    }

    private Map<String, String> getOutPutMaps(Map<String, String> rowAsData) {
        Map<String, String> outPutMap = new CaseInsensitiveMap();

        for (String text : expectedMap) {
            outPutMap.put(text.toLowerCase(), rowAsData.get(text.toLowerCase()));
        }
        return outPutMap;
    }

    private Map<String, String> getInputMaps(Map<String, String> rowAsData) {
        Map<String, String> inputMap1 = new CaseInsensitiveMap();

        for (String text : inputMap) {
            inputMap1.put(text.toLowerCase(), rowAsData.get(text.toLowerCase()));
        }
        return inputMap1;
    }
}
