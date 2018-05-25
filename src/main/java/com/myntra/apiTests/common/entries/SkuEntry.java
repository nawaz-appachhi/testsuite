package com.myntra.apiTests.common.entries;

/**
 * Created by 8403 on 27/05/17.
 */
public class SkuEntry {

	private String skuId;
    private int quantity;
    private String skuType;

    public SkuEntry(){

    }   

    public SkuEntry(String skuId, int quantity, String skuType) {
		this.skuId = skuId;
		this.quantity = quantity;
		this.skuType = skuType;
	}
    
    public SkuEntry(String skuId, int quantity) {
		this.skuId = skuId;
		this.quantity = quantity;
		this.skuType = "ON_HAND";
	}
       
    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
    		this.skuId = skuId;
     
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType;
    }

	@Override
	public String toString() {
		return "SkuEntry [SkuId=" + skuId + ", quantity=" + quantity + ", skuType=" + skuType + "]";
	}
    
    

}
