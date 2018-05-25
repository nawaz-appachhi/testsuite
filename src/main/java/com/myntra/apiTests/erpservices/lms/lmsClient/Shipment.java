package com.myntra.apiTests.erpservices.lms.lmsClient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myntra.lms.client.status.ShippingMethod;
import com.myntra.taxmaster.client.enums.TaxType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Shubham Gupta on 5/2/17.
 */
public class Shipment extends BaseShipment{
    public static final Long MYNTRA_STORE_ID = 1L;
    public static final Long JABONG_STORE_ID = 5L;

    private String sellerId;
    private Long storeId;
    private String sellerName;
    private String sellerCode;
    private String tinNumber;
    private String salesOrderId;
    private String warehouseId;
    private String rtoWarehouseId;
    private Long deliveryCenterId;

    public boolean isCod() {
        return cod;
    }

    public void setCod(boolean cod) {
        this.cod = cod;
    }

    private boolean cod;
    private Float codAmount;
    private Float shipmentValue;
    private Double mrpTotal; // subTotal in OrderEntry
    private Float taxAmount; //this is vat in prism. wbu govtTax ?

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message="packedDate cannot be empty")
    private DateTime packedDate;
    private DateTime inscannedDate;
    private DateTime dispatchedDate; //equivalent to shippedOn,expectedShipDate
    private DateTime deliveryDate;
    private DateTime expectedShipDate;
    private String timeToReachCustomer;

    //private ShipmentServiceType shipmentServiceType;
    @NotNull(message="shippingMethod cannot be empty")
    private ShippingMethod shippingMethod;

    private String receivedBy;
    private String receivedAt;

    @Min(value=0 ,message = "packageWeight has to be >= 0 ")
    private Float packageWeight;

    @Min(value=0 ,message = "packageLength has to be >= 0 ")
    private Double packageLength;

    @Min(value=0 ,message = "packageBreadth has to be >= 0 ")
    private Double packageBreadth;

    @Min(value=0 ,message = "packageHeight has to be >= 0 ")
    private Double packageHeight;

    private String inScanLocID;
    private Set<Long> styleIds;
    private List<ShipmentItem> shipmentItems = new ArrayList<ShipmentItem>();
	/*
	what about these fields. Do we need to include these.

	private Long deliveryCenterId;
	private boolean isCardPaymentEnabled=false;
	*/

    private Double cartDiscount;
    private Double cashBack;
    private Double cashRedeemed;
    private Double couponDiscount;
    private Double discount;
    private Float additionalCollectableCharges;
    private Float codAmountCollected;
    private Long triedAndBoughtDuration;

    private Double govtTaxAmount;
    private String invoiceId;
    private String centralSalesTaxNumber;
    private boolean isHazmat;
    private boolean containsFootwear;
    private boolean containsJewellery;
    private boolean isFragile;
    private boolean isLarge;

    private String dispatchHubCode;
    private String rtoHubCode;
    private String lastScanPremisesId;
    private String lastScanPremisesType;

    public Set<Long> getStyleIds() {
        return styleIds;
    }

    public Double getMrpTotal() {
        return mrpTotal;
    }

    public void setMrpTotal(Double mrpTotal) {
        this.mrpTotal = mrpTotal;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(String receivedAt) {
        this.receivedAt = receivedAt;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getSalesOrderId() {
        return salesOrderId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setSalesOrderId(String salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public boolean isHazmat() {
        return isHazmat;
    }

    public void setHazmat(boolean hazmat) {
        this.isHazmat = hazmat;
    }

    public void setStyleIds(Set<Long> styleIds) {
        this.styleIds = styleIds;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public Float getShipmentValue() {
        return shipmentValue;
    }

    public void setShipmentValue(Float shipmentValue) {
        this.shipmentValue = shipmentValue;
    }

    public Float getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Float getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(Float packageWeight) {
        this.packageWeight = packageWeight;
    }

    public DateTime getDispatchedDate() {
        return dispatchedDate;
    }

    public void setDispatchedDate(DateTime dispatchedDate) {
        this.dispatchedDate = dispatchedDate;
    }


    public Float getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(Float codAmount) {
        this.codAmount = codAmount;
    }

    public List<ShipmentItem> getShipmentItems() {
        return shipmentItems;
    }

    public void setShipmentItems(List<ShipmentItem> shipmentItems) {
        this.shipmentItems = shipmentItems;
    }

    public void addShipmentItem(ShipmentItem shipmentItem) {
        this.shipmentItems.add(shipmentItem);
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public String getRtoWarehouseId() {
        return rtoWarehouseId;
    }

    public void setRtoWarehouseId(String rtoWarehouseId) {
        this.rtoWarehouseId = rtoWarehouseId;
    }

    public Long getDeliveryCenterId() {
        return deliveryCenterId;
    }

    public void setDeliveryCenterId(Long deliveryCenterId) {
        this.deliveryCenterId = deliveryCenterId;
    }

    public String getTimeToReachCustomer() {
        return timeToReachCustomer;
    }

    public void setTimeToReachCustomer(String timeToReachCustomer) {
        this.timeToReachCustomer = timeToReachCustomer;
    }

    public String getInScanLocID() {
        return inScanLocID;
    }

    public void setInScanLocID(String inScanLocID) {
        this.inScanLocID = inScanLocID;
    }

    public DateTime getPackedDate() {
        return packedDate;
    }

    public void setPackedDate(DateTime packedDate) {
        this.packedDate = packedDate;
    }

    public DateTime getInscannedDate() {
        return inscannedDate;
    }

    public void setInscannedDate(DateTime inscannedDate) {
        this.inscannedDate = inscannedDate;
    }

    public DateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(DateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public DateTime getExpectedShipDate() {
        return expectedShipDate;
    }

    public void setExpectedShipDate(DateTime expectedShipDate) {
        this.expectedShipDate = expectedShipDate;
    }

    public Double getCartDiscount() {
        return cartDiscount;
    }

    public void setCartDiscount(Double cartDiscount) {
        this.cartDiscount = cartDiscount;
    }

    public Double getCashBack() {
        return cashBack;
    }

    public void setCashBack(Double cashBack) {
        this.cashBack = cashBack;
    }

    public Double getCashRedeemed() {
        return cashRedeemed;
    }

    public void setCashRedeemed(Double cashRedeemed) {
        this.cashRedeemed = cashRedeemed;
    }

    public Double getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(Double couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getGovtTaxAmount() {
        return govtTaxAmount;
    }

    public void setGovtTaxAmount(Double govtTaxAmount) {
        this.govtTaxAmount = govtTaxAmount;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCentralSalesTaxNumber() {
        return centralSalesTaxNumber;
    }

    public void setCentralSalesTaxNumber(String centralSalesTaxNumber) {
        this.centralSalesTaxNumber = centralSalesTaxNumber;
    }

    public boolean isContainsFootwear() {
        return containsFootwear;
    }

    public void setContainsFootwear(boolean containsFootwear) {
        this.containsFootwear = containsFootwear;
    }

    public boolean isContainsJewellery() {
        return containsJewellery;
    }

    public void setContainsJewellery(boolean containsJewellery) {
        this.containsJewellery = containsJewellery;
    }

    public boolean isFragile() {
        return isFragile;
    }

    public void setFragile(boolean fragile) {
        isFragile = fragile;
    }

    public boolean isLarge() {
        return isLarge;
    }

    public void setLarge(boolean large) {
        isLarge = large;
    }

    public Float getAdditionalCollectableCharges() {
        return additionalCollectableCharges;
    }

    public void setAdditionalCollectableCharges(Float additionalCollectableCharges) {
        this.additionalCollectableCharges = additionalCollectableCharges;
    }

    public Float getCodAmountCollected() {
        return codAmountCollected;
    }

    public void setCodAmountCollected(Float codAmountCollected) {
        this.codAmountCollected = codAmountCollected;
    }

    public Long getTriedAndBoughtDuration() {
        return triedAndBoughtDuration;
    }

    public void setTriedAndBoughtDuration(Long triedAndBoughtDuration) {
        this.triedAndBoughtDuration = triedAndBoughtDuration;
    }

    @JsonIgnore
    public boolean isMultiSellerShipment(){
        Set<String>sellerIdSet=new HashSet<>();
        for(ShipmentItem shipmentItem:shipmentItems){
            sellerIdSet.add(shipmentItem.getSellerId());
        }
        return (sellerIdSet.size() > 1);
    }

    public Double getPackageLength() {
        return packageLength;
    }

    public void setPackageLength(Double packageLength) {
        this.packageLength = packageLength;
    }

    public Double getPackageBreadth() {
        return packageBreadth;
    }

    public void setPackageBreadth(Double packageBreadth) {
        this.packageBreadth = packageBreadth;
    }

    public Double getPackageHeight() {
        return packageHeight;
    }

    public void setPackageHeight(Double packageHeight) {
        this.packageHeight = packageHeight;
    }

    public String getDispatchHubCode() {
        return dispatchHubCode;
    }

    public void setDispatchHubCode(String dispatchHubCode) {
        this.dispatchHubCode = dispatchHubCode;
    }

    public String getRtoHubCode() {
        return rtoHubCode;
    }

    public void setRtoHubCode(String rtoHubCode) {
        this.rtoHubCode = rtoHubCode;
    }

    public String getLastScanPremisesId() {
        return lastScanPremisesId;
    }

    public void setLastScanPremisesId(String lastScanPremisesId) {
        this.lastScanPremisesId = lastScanPremisesId;
    }

    public String getLastScanPremisesType() {
        return lastScanPremisesType;
    }

    public void setLastScanPremisesType(String lastScanPremisesType) {
        this.lastScanPremisesType = lastScanPremisesType;
    }

    // Used via SPEL in manifest
    @JsonIgnore
    public String changeDateFormat (DateTime date, String format) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        date = (date == null) ? new DateTime() : date;
        String dateString = fmt.print(date);
        return dateString;
    }

    //getTaxAmount(T(com.myntra.taxmaster.client.enums.TaxType).VAT)
    @JsonIgnore
    public Float getTaxAmount (TaxType type) {
        Float taxAmount = 0F;
        for (ShipmentItem item : shipmentItems) {
            switch (type) {
                case VAT: taxAmount += item.getValueAddedTax() != null ? item.getValueAddedTax() : 0;
                    break;
                case CST: taxAmount += item.getCentralGoodsAndServiceTax() != null ? item.getCentralGoodsAndServiceTax() : 0;
                    break;
                case IGST: taxAmount += item.getIntegratedGoodsAndServiceTax() != null ? item.getIntegratedGoodsAndServiceTax() : 0;
                    break;
                case SGST: taxAmount += item.getStateGoodsAndServiceTax() != null ? item.getStateGoodsAndServiceTax() : 0;
                    break;
                case CGST: taxAmount += item.getCentralGoodsAndServiceTax() != null ? item.getCentralGoodsAndServiceTax() : 0;
                    break;
			default:
				break;
            }
        }
        return taxAmount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
