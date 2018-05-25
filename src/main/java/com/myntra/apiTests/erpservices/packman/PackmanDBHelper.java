package com.myntra.apiTests.erpservices.packman;

import java.util.List;
import java.util.Map;

import com.myntra.lordoftherings.boromir.DBUtilities;

public class PackmanDBHelper {
	public long getSellerPacketId(Long storeId,String status_code){
		Map<String, Object> resultSet=null;
		resultSet=DBUtilities.exSelectQueryForSingleRecord("select id from (select id from seller_packet where `store_id`='"+storeId+"' and `status_code`='"+status_code+"' order by `created_on` DESC limit 5) seller_packet order by rand() limit 1;", "myntra_packman");
		if(resultSet.get("id")==null){
			return 0;
		}
		return (long) resultSet.get("id");
	}
	public long getSellerPacketFromSellerPacketItem(String status_code){
		Map<String, Object> resultSet=null;
		resultSet=DBUtilities.exSelectQueryForSingleRecord("select id from (select id from seller_packet_item where `status_code`='"+status_code+"' order by `created_on` DESC limit 5) seller_packet_item order by rand() limit 1;", "myntra_packman");
		return (long) resultSet.get("id");
	}
	public long getSellerPacketByReleaseId(String releaseId){
		Map<String, Object> resultSet=null;
		resultSet=DBUtilities.exSelectQueryForSingleRecord("select `seller_packet_id_fk` FROM seller_packet_item where portal_order_release_id="+releaseId+";", "myntra_packman");
		return (long) resultSet.get("seller_packet_id_fk");
	}
	public String getPacketItemBarcode(String status_code,Long storeId){
		Map<String, Object> resultSet=null;
		resultSet=DBUtilities.exSelectQueryForSingleRecord("select item_barcode from (select item_barcode from seller_packet_item where `status_code`='"+status_code+"' and `store_id`='"+storeId+"' order by `created_on` DESC limit 5) seller_packet_item order by rand() limit 1;", "myntra_packman");
		if(resultSet==null){
			return null;
		}
		return (String)resultSet.get("item_barcode");
	}
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>>  getPacketItemDataBySellerPacket(Long sellerPacketId){
		List<Map<String,Object>> resultSet=null;
		resultSet=DBUtilities.exSelectQuery("select * FROM seller_packet_item where seller_packet_id_fk="+sellerPacketId+";", "myntra_packman");
		if(resultSet==null){
			return null;
		}
		return resultSet;
	}
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>>  getPacketItemDataByItemBarcode(String itemBarcode){
		List<Map<String,Object>> resultSet=null;
		resultSet=DBUtilities.exSelectQuery("select * from seller_packet_item where `item_barcode`="+itemBarcode+";", "myntra_packman");
		if(resultSet==null){
			return null;
		}
		return resultSet;
	}
	public Object getPackagedItem(String selectType, boolean qcPassRequired,String packagingType,Long storeId){
		Map<String,Object> resultSet=null;
		if(qcPassRequired){
			resultSet=DBUtilities.exSelectQueryForSingleRecord("select "+selectType+ " from seller_packet_item where  `store_id`='"+storeId+"' and `packaging_type`='"+packagingType+"' and `status_code`='QD' and `packaging_status` is NULL;", "myntra_packman");
		}else{
			resultSet=DBUtilities.exSelectQueryForSingleRecord("select "+selectType+ " from seller_packet_item where `store_id`='"+storeId+"' and  `packaging_type`='"+packagingType+"' and `status_code`='A' and `packaging_status` is NULL;", "myntra_packman");
		}
		if(resultSet==null){
			return null;
		}
		return resultSet.get(selectType);
	}
}
