/**
 * 
 */
package com.myntra.apiTests.erpservices.oms;

import java.util.HashSet;
import java.util.Set;


/**
 * @author puneet.khanna1@myntra.com
 *
 */
public class SkuTriplet {

	private Integer skuCode;
	private Integer  quanity;
	private Set<String> wareHouseIds= new HashSet<String> ();

	
	public SkuTriplet(Integer skuCode, Integer quanity,String[] wareHouseIds) {
		super();
		this.skuCode = skuCode;
		this.quanity = quanity;
		for (String warHouseIds : wareHouseIds) {
			this.wareHouseIds.add(warHouseIds);
		}
	}


	Set<String> addWareHouseIdAndGetWareHouseIdSet(String wareHouseId){
		getWareHouseIds().add(wareHouseId);
		return getWareHouseIds();
	}
	
	
	public Integer getSkuCode() {
		return skuCode;
	}



	public Integer getQuanity() {
		return quanity;
	}



	public Set<String> getWareHouseIds() {
		return wareHouseIds;
	}

	Set<String> addWareHouseIdsAndGetWareHouseIdSet(String wareHouseId){
	  this.wareHouseIds.add(wareHouseId); 
	  return this.wareHouseIds;
   }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((skuCode == null) ? 0 : skuCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SkuTriplet other = (SkuTriplet) obj;
		if (skuCode == null) {
			if (other.skuCode != null)
				return false;
		} else if (!skuCode.equals(other.skuCode))
			return false;
		return true;
	}
	
public	String[] getSkuWareHouseIdsAsArray(){
   return this.getWareHouseIds().toArray(new String[this.getWareHouseIds().size()]);

	}
	
	
}
