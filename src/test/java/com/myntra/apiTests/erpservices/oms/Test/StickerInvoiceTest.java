package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.release.doc.client.entry.InvoiceItemEntry;
import com.myntra.release.doc.client.entry.SellerDetailsEntry;
import com.myntra.release.doc.client.entry.StickerInvoiceEntry;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @author abhijit.pati on 24/08/16.
 */
public class StickerInvoiceTest extends BaseTest {

    Logger log = Logger.getLogger(OMSServiceHelper.class);
    String login = "omsautomation12@myntra.com";
    String password = "123456";
    String addressID = "6132570";
    String uidx;

    private IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
    private OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    private End2EndHelper end2EndHelper = new End2EndHelper();

    @BeforeClass
    public void beforeClass() throws IOException {
        uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
    }

    /**
     * Generate Invoice For COD Order
     */
    @Test(enabled = true, description = "Generate Sticker Invoice for COD Order")
    public void generateStickerInvoiceForCODOrder() throws Exception {
    	String orderID = createOrderAndStampTax(new String[]{"3831:2"}, addressID,"", false, false, false, false);
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderID);
        StickerInvoiceEntry stickerInvoiceEntry = omsServiceHelper.getStickerInvoiceEntry(orderID);

        SoftAssert sft = new SoftAssert();
        //Verify User Details
        assertUserInfo(stickerInvoiceEntry, orderReleaseEntry);

        //payment Info
        assertPaymentInfo(stickerInvoiceEntry, orderReleaseEntry);

        //Assert Seller Details
        assertSellerDetails(stickerInvoiceEntry, orderReleaseEntry);
        sft.assertNotNull(stickerInvoiceEntry.getInvoiceNumber());
        sft.assertEquals(stickerInvoiceEntry.getInvoiceDate(), orderReleaseEntry.getOrderDate());

        //Assert Item Entries
        asserItemDetails(stickerInvoiceEntry, orderReleaseEntry);
        sft.assertAll();
    }

    private void assertUserInfo(StickerInvoiceEntry stickerInvoiceEntry, OrderReleaseEntry orderReleaseEntry) {
        SoftAssert sft = new SoftAssert();
        sft.assertEquals(stickerInvoiceEntry.getReceiverName(), orderReleaseEntry.getReceiverName());
        sft.assertEquals(stickerInvoiceEntry.getMobile(), orderReleaseEntry.getMobile());
        sft.assertEquals(stickerInvoiceEntry.getAddress(), orderReleaseEntry.getAddress());
        sft.assertEquals(stickerInvoiceEntry.getCity(), orderReleaseEntry.getCity());
        sft.assertEquals(stickerInvoiceEntry.getCountry(), orderReleaseEntry.getCountry());
        sft.assertEquals(stickerInvoiceEntry.getLocality(), orderReleaseEntry.getLocality());
        sft.assertEquals(stickerInvoiceEntry.getZipcode(), orderReleaseEntry.getZipcode());
        sft.assertEquals(stickerInvoiceEntry.getUserContactNo(), orderReleaseEntry.getUserContactNo());
        sft.assertAll();
    }

    private void asserItemDetails(StickerInvoiceEntry stickerInvoiceEntry, OrderReleaseEntry orderReleaseEntry) throws JAXBException, UnsupportedEncodingException {
        SoftAssert sft = new SoftAssert();
        List<InvoiceItemEntry> invoiceItemEntries = stickerInvoiceEntry.getItems();
        //sft.assertEquals(stickerInvoiceEntry.getItemsInShipment(), orderReleaseEntry.getQuantity());

        for (InvoiceItemEntry invoiceItemEntry : invoiceItemEntries) {
            OrderLineEntry orderLineEntry1 = omsServiceHelper.getOrderLineEntry("" + invoiceItemEntry.getOrderLineId());
            /*sft.assertEquals(invoiceItemEntry.getGovtTaxableAmount(), orderLineEntry1.getGovtTaxableAmount(), "Govt Taxable Amount :");
            sft.assertEquals(invoiceItemEntry.getGovtTaxRate(), orderLineEntry1.getGovtTaxRate(), "Govt Tax Rate ");
            sft.assertEquals(invoiceItemEntry.getGovtTaxAmount(), orderLineEntry1.getGovtTaxAmount(), "Govt TaxAmount ");
            sft.assertEquals(invoiceItemEntry.getItemTotalAmount(), (orderLineEntry1.getQuantity() * orderLineEntry1.getUnitPrice()), "Item Total Amount ");*/
            sft.assertEquals(invoiceItemEntry.getItemTotalDiscount(), orderLineEntry1.getDiscount(), "Item Discount ");
            sft.assertEquals(invoiceItemEntry.getOrderLineId(), orderLineEntry1.getId(), "Line ID ");
            sft.assertEquals(invoiceItemEntry.getOrderReleaseId(), orderLineEntry1.getOrderReleaseId(), "Order Release ID ");
            sft.assertEquals(invoiceItemEntry.getProRatedAdditionalCharges(), orderLineEntry1.getProRatedAdditionalCharges(), "Pro Rated Additional Cahrges");
            sft.assertEquals(invoiceItemEntry.getProductDescription(), orderLineEntry1.getProductDescription(), "Product Description ");
            sft.assertEquals(invoiceItemEntry.getQuantity(), orderLineEntry1.getQuantity(), "Get Item Quantity ");
            sft.assertEquals(invoiceItemEntry.getSize(), orderLineEntry1.getSize(), "Get Item Size ");
            sft.assertEquals(invoiceItemEntry.getUnitPrice(), orderLineEntry1.getUnitPrice(), "Unit Price ");
        }
        sft.assertAll();
    }


    private void assertPaymentInfo(StickerInvoiceEntry stickerInvoiceEntry, OrderReleaseEntry orderReleaseEntry) {
        SoftAssert sft = new SoftAssert();
        sft.assertEquals(stickerInvoiceEntry.getPaymentMethod(), orderReleaseEntry.getPaymentMethod());
        sft.assertEquals(stickerInvoiceEntry.getCashRedeemed(), orderReleaseEntry.getCashRedeemed());
        sft.assertEquals(stickerInvoiceEntry.getGiftCardAmount(), orderReleaseEntry.getGiftCardAmount());
      /*  sft.assertEquals(stickerInvoiceEntry.getFinalAmount(), orderReleaseEntry.getFinalAmount());*/
        sft.assertEquals(stickerInvoiceEntry.getLoyaltyPointsCredit(), orderReleaseEntry.getLoyaltyPointsUsed());
        sft.assertEquals(stickerInvoiceEntry.getWalletAmount(), orderReleaseEntry.getWalletAmount());
        sft.assertAll();
    }

    private void assertSellerDetails(StickerInvoiceEntry stickerInvoiceEntry, OrderReleaseEntry orderReleaseEntry) throws UnsupportedEncodingException, JAXBException {
        SellerDetailsEntry sellerDetailsEntry = stickerInvoiceEntry.getSellerDetails();
        Map<String, String> sellerDetail = omsServiceHelper.getSellerAddress(orderReleaseEntry.getWarehouseId(), orderReleaseEntry.getSellerId());
        SoftAssert sft = new SoftAssert();
        sft.assertEquals(sellerDetailsEntry.getAddress(), sellerDetail.get("selleraddress").toString());
        sft.assertEquals(sellerDetailsEntry.getTinNumber(), sellerDetail.get("tinnumber").toString());
        sft.assertEquals(sellerDetailsEntry.getCstNumber(), sellerDetail.get("cstnumber").toString());
        sft.assertEquals(sellerDetailsEntry.getCstNumber(), sellerDetail.get("sellername").toString());
        sft.assertAll();
    }

    public void generateInvoiceForOnlineOrder() {

    }

    public void generateInvoiceForGCOrder() {

    }

    public void generateInvoiceForCombinationOfCODAndLPOrder() {

    }

    public void generateInvoiceForOrderWhichHavingShippingCharges() {

    }

    public void generateInvoiceForOrderWhichHavingGiftCharges() {

    }

    public void generateInvoiceForCODAndGiftChargeOrders() {

    }

    public void generateInvoiceAfterOneItemCancellation() {

    }

    public void generateInvoiceWhenFreeItemIsIncluded() {

    }


    private String createOrderAndStampTax(String[] skuID, String addressID,String coupon, boolean wallet, boolean loyalty, boolean giftWrap, boolean tryNBuy) throws Exception {
    	String orderID = end2EndHelper.createOrder(login, password, addressID, skuID, coupon, wallet, loyalty, giftWrap, "", tryNBuy);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        omsServiceHelper.stampGovtTaxForVectorSuccess(orderID);
        return orderID;
    }

}
