package com.myntra.apiTests.common.Builder;

import com.myntra.apiTests.common.entries.SkuEntry;

public class SkuEntryBuilder {

    private String skuId;
    private int quantity;
    private String skuType;
    
    
	public SkuEntryBuilder setSkuId(String skuId) {

			this.skuId = skuId;
     		return this;
	}
	
	public SkuEntryBuilder setQuantity(Integer quantity) {
		if(quantity==null){
			this.quantity = 1;
		}else{
			this.quantity = quantity;
		}
		
		return this;
	}
	public SkuEntryBuilder setSkuType(String skuType) {
		
		if(skuType==null){
			skuType = "ON_HAND";
		}
		
		this.skuType = skuType;
		return this;
	}
	
	public SkuEntry build(){
		return new SkuEntry(skuId,quantity,skuType);
	}
    
    
}
