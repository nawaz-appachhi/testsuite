package com.myntra.apiTests.erpservices.lms.lmsClient;

import com.myntra.logistics.platform.domain.TryAndBuyItemStatus;
import com.myntra.logistics.platform.domain.TryAndBuyNotBoughtReason;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Shubham Gupta on 5/2/17.
 */
public class ShipmentItem {

    private Long id;

    @NotEmpty(message="sourceItemReferenceId cannot be empty")
    @Size(max = 255, message = "sourceItemReferenceId max length is 255 characters")
    private String sourceItemReferenceId;

    @Size(max = 32, message = "styleId max length is 32 characters")
    private String styleId;

    @Size(max = 32, message = "skuId max length is 32 characters")
    private String skuId;

    @NotEmpty(message="itemDescription cannot be empty")
    @Size(max = 255, message = "itemDescription max length is 255 characters")
    private String itemDescription;

    @Size(max = 255, message = "imageURL max length is 255 characters")
    private String imageURL;

    @Size(max = 255, message = "itemBarcode max length is 255 characters")
    private String itemBarcode;

    @Min(value=0 ,message = "itemValue has to be >= 0 ")
    @NotNull(message = "itemValue cannot be empty")
    private Float itemValue;

    @Min(value=0 ,message = "codAmount has to be >= 0 ")
    @NotNull(message = "codAmount cannot be empty")
    private Float codAmount;

    @Min(value=0 ,message = "taxAmountPaid has to be >= 0 ")
    private Double taxAmountPaid;

    @Min(value=0 ,message = "additionalCharges has to be >= 0 ")
    @NotNull(message = "additionalCharges cannot be empty")
    private Float additionalCharges;

    @NotEmpty(message="sellerId cannot be empty")
    @Size(max = 20, message = "sellerId max length is 20 characters")
    private String sellerId;

    @NotEmpty(message="sellerTaxIdentificationNumber cannot be empty")
    @Size(max = 64, message = "sellerTaxIdentificationNumber max length is 64 characters")
    private String sellerTaxIdentificationNumber;

    @NotEmpty(message="sellerCentralSalesTaxNumber cannot be empty")
    @Size(max = 64, message = "sellerCentralSalesTaxNumber max length is 64 characters")
    private String sellerCentralSalesTaxNumber;

    @NotEmpty(message="invoiceId cannot be empty")
    @Size(max = 64, message = "invoiceId max length is 64 characters")
    private String invoiceId;

    private TryAndBuyItemStatus tryAndBuyItemstatus;

    private Long tryAndBuyReturnId;

    private String tryAndBuyReturnQCRemarks;

    private TryAndBuyNotBoughtReason triedAndNotBoughtReason;

    @Min(value=0 ,message = "itemMRP has to be >= 0 ")
    @NotNull(message = "itemMRP cannot be empty")
    private Float itemMRP;

    private Float centralSalesTax;

    private Float valueAddedTax;

    @Min(value=0 ,message = "integratedGoodsAndServiceTax has to be >= 0 ")
    private Float integratedGoodsAndServiceTax;

    @Min(value=0 ,message = "centralGoodsAndServiceTax has to be >= 0 ")
    private Float centralGoodsAndServiceTax;

    @Min(value=0 ,message = "stateGoodsAndServiceTax has to be >= 0 ")
    private Float stateGoodsAndServiceTax;

    private Float cess;

    @NotEmpty(message="sellerName cannot be empty")
    @Size(max = 128, message = "sellerName max length is 128 characters")
    private String sellerName;

    @NotEmpty(message="sellerAddress cannot be empty")
    @Size(max = 2048, message = "sellerAddress max length is 2048 characters")
    private String sellerAddress;

    @Size(max = 64, message = "goodsAndServiceTaxIdentificationNumber max length is 64 characters")
    private String goodsAndServiceTaxIdentificationNumber;

    @Size(max = 64, message = "electronicReferenceNumber max length is 64 characters")
    private String electronicReferenceNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceItemReferenceId() {
        return sourceItemReferenceId;
    }

    public void setSourceItemReferenceId(String sourceItemReferenceId) {
        this.sourceItemReferenceId = sourceItemReferenceId;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getItemBarcode() {
        return itemBarcode;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public Float getItemValue() {
        return itemValue;
    }

    public void setItemValue(Float itemValue) {
        this.itemValue = itemValue;
    }

    public Float getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(Float codAmount) {
        this.codAmount = codAmount;
    }

    public Double getTaxAmountPaid() {
        return taxAmountPaid;
    }

    public void setTaxAmountPaid(Double taxAmountPaid) {
        this.taxAmountPaid = taxAmountPaid;
    }

    public Float getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(Float additionalCollectableCharges) {
        this.additionalCharges = additionalCollectableCharges;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerTaxIdentificationNumber() {
        return sellerTaxIdentificationNumber;
    }

    public void setSellerTaxIdentificationNumber(String sellerTaxIdentificationNumber) {
        this.sellerTaxIdentificationNumber = sellerTaxIdentificationNumber;
    }

    public String getSellerCentralSalesTaxNumber() {
        return sellerCentralSalesTaxNumber;
    }

    public void setSellerCentralSalesTaxNumber(String sellerCentralSalesTaxNumber) {
        this.sellerCentralSalesTaxNumber = sellerCentralSalesTaxNumber;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public TryAndBuyItemStatus getTryAndBuyItemstatus() {
        return tryAndBuyItemstatus;
    }

    public void setTryAndBuyItemstatus(TryAndBuyItemStatus tryAndBuyItemstatus) {
        this.tryAndBuyItemstatus = tryAndBuyItemstatus;
    }

    public Long getTryAndBuyReturnId() {
        return tryAndBuyReturnId;
    }

    public void setTryAndBuyReturnId(Long tryAndBuyReturnId) {
        this.tryAndBuyReturnId = tryAndBuyReturnId;
    }

    public String getTryAndBuyReturnQCRemarks() {
        return tryAndBuyReturnQCRemarks;
    }

    public void setTryAndBuyReturnQCRemarks(String tryAndBuyReturnQCRemarks) {
        this.tryAndBuyReturnQCRemarks = tryAndBuyReturnQCRemarks;
    }

    public TryAndBuyNotBoughtReason getTriedAndNotBoughtReason() {
        return triedAndNotBoughtReason;
    }

    public void setTriedAndNotBoughtReason(TryAndBuyNotBoughtReason triedAndNotBoughtReason) {
        this.triedAndNotBoughtReason = triedAndNotBoughtReason;
    }

    public Float getItemMRP() {
        return itemMRP;
    }

    public void setItemMRP(Float itemMRP) {
        this.itemMRP = itemMRP;
    }

    public Float getInvoiceValue(){
        return getItemValue() + getAdditionalCharges();
    }

    public Float getCentralSalesTax() {
        return centralSalesTax;
    }

    public void setCentralSalesTax(Float centralSalesTax) {
        this.centralSalesTax = centralSalesTax;
    }

    public Float getValueAddedTax() {
        return valueAddedTax;
    }

    public void setValueAddedTax(Float valueAddedTax) {
        this.valueAddedTax = valueAddedTax;
    }

    public Float getIntegratedGoodsAndServiceTax() {
        return integratedGoodsAndServiceTax;
    }

    public void setIntegratedGoodsAndServiceTax(Float integratedGoodsAndServiceTax) {
        this.integratedGoodsAndServiceTax = integratedGoodsAndServiceTax;
    }

    public Float getCentralGoodsAndServiceTax() {
        return centralGoodsAndServiceTax;
    }

    public void setCentralGoodsAndServiceTax(Float centralGoodsAndServiceTax) {
        this.centralGoodsAndServiceTax = centralGoodsAndServiceTax;
    }

    public Float getStateGoodsAndServiceTax() {
        return stateGoodsAndServiceTax;
    }

    public void setStateGoodsAndServiceTax(Float stateGoodsAndServiceTax) {
        this.stateGoodsAndServiceTax = stateGoodsAndServiceTax;
    }

    public Float getCess() {
        return cess;
    }

    public void setCess(Float cess) {
        this.cess = cess;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getGoodsAndServiceTaxIdentificationNumber() {
        return goodsAndServiceTaxIdentificationNumber;
    }

    public void setGoodsAndServiceTaxIdentificationNumber(String goodsAndServiceTaxIdentificationNumber) {
        this.goodsAndServiceTaxIdentificationNumber = goodsAndServiceTaxIdentificationNumber;
    }

    public String getElectronicReferenceNumber() {
        return electronicReferenceNumber;
    }

    public void setElectronicReferenceNumber(String electronicReferenceNumber) {
        this.electronicReferenceNumber = electronicReferenceNumber;
    }
}
