package com.myntra.apiTests.erpservices.partners;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.myntra.lordoftherings.boromir.DBUtilities;

public class VMSHelper {

	public void deleteVendor(String Id) {
		deleteFromVendorFinanceInfo(Id);
		deleteFromContactsAddressDetails(Id);
		deleteFromContactsPersonDetails(Id);
		deleteFromVendorWarehouse(Id);
		deleteFromCategoryManager(Id);
		deleteFromBrand(Id);
		deleteFromVendor(Id);
	}

	public void deleteFromVendor(String Id) {
		String updateVendor = "DELETE from vendor where id = " + Id + "";
		DBUtilities.exUpdateQuery(updateVendor, "vms");
	}

	public void deleteFromBrand(String Id) {
		String updateVendor = "DELETE from vendor_brand where vendor_id = " + Id + "";
		DBUtilities.exUpdateQuery(updateVendor, "vms");
	}

	public void deleteFromCategoryManager(String Id) {
		String updateVendor = "DELETE from vendor_category_manager where vendor_id = " + Id + "";
		DBUtilities.exUpdateQuery(updateVendor, "vms");
	}

	public void deleteFromVendorWarehouse(String Id) {
		String updateVendor = "DELETE from vendor_warehouse where vendor_id = " + Id + "";
		DBUtilities.exUpdateQuery(updateVendor, "vms");
	}

	public void deleteFromContactsPersonDetails(String Id) {
		String updateVendor = "DELETE from myntra_contact_person_details where entity_id = " + Id + "";
		DBUtilities.exUpdateQuery(updateVendor, "contacts");
	}

	public void deleteFromContactsAddressDetails(String Id) {
		String updateVendor = "DELETE from myntra_contact_address_details where entity_id = " + Id + "";
		DBUtilities.exUpdateQuery(updateVendor, "contacts");
	}

	public void deleteFromVendorFinanceInfo(String Id) {
		String updateVendor = "DELETE from vendor_finance_info where vendor_id = " + Id + "";
		DBUtilities.exUpdateQuery(updateVendor, "vms");
	}

	public String getIdFromPartnerContactAddress(String Id, String address_type) {
		Map<String, Object> query = DBUtilities
				.exSelectQueryForSingleRecord("select id from partner_contact_address where partner_id=" + Id
						+ " and address_type='" + address_type + "'", "vms");
		String val = query.get("id").toString();
		return val;
	}

	public String getIdFromVendorBrand(String Id, String brandName) {
		Map<String, Object> query = DBUtilities.exSelectQueryForSingleRecord(
				"select id from vendor_brand where vendor_id=" + Id + " and brand_name='" + brandName + "'", "vms");
		String val = query.get("id").toString();
		return val;
	}

	public String getPartyNameFromContractsDb(String vendorId) {
		Map<String, Object> query = DBUtilities.exSelectQueryForSingleRecord(
				"select party_name from account_party where source_system_id='" + vendorId + "'", "contracts");
		String val = query.get("party_name").toString();
		return val;
	}

	public String getVendorNameFromVMS(String Id) {
		Map<String, Object> query = DBUtilities
				.exSelectQueryForSingleRecord("select name from vendor where id=" + Id + "", "vms");
		String val = query.get("name").toString();
		return val;
	}

	public boolean getStatusFromVMSContracts(String vendorId) {
		Map<String, Object> query = DBUtilities
				.exSelectQueryForSingleRecord("select id from contract where partner2_id='" + vendorId + "'", "vms");
		if (query == null)
			return false;
		else
			return true;

	}

	public Map<String, Object> getPartnerRolesFromContractType(String contractTypeId) {
		String query = "select partner1_role,partner2_role from contract_type where id=" + contractTypeId;
		List<Map<String, Object>> roles = DBUtilities.exSelectQuery(query, "vms");
		return roles.get(0);
	}

	public String getBuyerId() {
		String query = "select value from core_application_properties where name='terms.default_buyer_id'";
		Map<String, Object> buyerid = DBUtilities.exSelectQueryForSingleRecord(query, "tools");
		return (String) buyerid.get("value");
	}

	public String getStoreId() {
		String query = "select value from core_application_properties where name='vms.storeId'";
		Map<String, Object> storeid = DBUtilities.exSelectQueryForSingleRecord(query, "tools");
		return (String) storeid.get("value");
	}

	public String getExistingSeller() {
		String query = "select s.id from myntra_seller.seller s,myntra_vms.source_system_partner_mapping m,"
				+ "myntra_vms.contract c,myntra_vms.configuration g where s.id=m.source_system_id and "
				+ "m.partner_id=c.partner2_id and c.status=\"ACTIVE\" and c.contract_type_id=2 and "
				+ "c.id=g.entity_id group by s.id having count(s.id)="
				+ "(select count(*) from myntra_seller.seller_configuration_defaults_master);";
		Map<String, Object> sellerid = DBUtilities.exSelectQueryForSingleRecord(query, "seller");
		return String.valueOf(sellerid.get("id"));
	}

	public int getVersionFromPOTerm(int vendorTermId) {
		String query = "select version from po_term where id=" + vendorTermId;
		Map<String, Object> result = DBUtilities.exSelectQueryForSingleRecord(query, "terms");
		return (int) result.get("version");
	}

	public long getArchiveIdFromPOTerm(int vendorTermId) {
		String query = "select archive_id from po_term where id=" + vendorTermId;
		Map<String, Object> status = DBUtilities.exSelectQueryForSingleRecord(query, "terms");

		return (long) status.get("archive_id");
	}

	public String getStatusFromPOTerm(int vendorTermId) {
		String query = "select status from po_term where id=" + vendorTermId;
		Map<String, Object> result = DBUtilities.exSelectQueryForSingleRecord(query, "terms");
		return (String) result.get("status");
	}

	public String getVendorCodeFromVendor(int vendorTermId) {
		String query = "select barcode from vendor where id=" + vendorTermId;
		Map<String, Object> result = DBUtilities.exSelectQueryForSingleRecord(query, "vms");
		return (String) result.get("barcode");
	}

	public String getApprovedVendorFromVendor() {
		String query = "select barcode from vendor where status='APPROVED'";
		Map<String, Object> result = DBUtilities.exSelectQueryForSingleRecord(query, "vms");
		return (String) result.get("barcode");
	}

	public List getPartnerIdsFromContractAndPOTerm(String buyerid, int agreementTypeId, int addtClassificationId,
			int masterCatId) {
		String query = "select partner2_id from contract c join terms.po_term p where partner1_id=" + buyerid
				+ " and c.id=p.contract_id and p.agreement_type_id=" + agreementTypeId
				+ " and p.addtl_classification_id=" + addtClassificationId + " and p.master_catg_id=" + masterCatId
				+ " limit 2;";
		List<Map<String, Object>> result = DBUtilities.exSelectQuery(query, "vms");
		List partnerIds = new ArrayList();
		for (int i = 0; i < result.size(); i++) {
			Object partnerId = result.get(i).get("partner2_id");
			partnerIds.add(partnerId);
		}
		System.out.println(partnerIds);
		return partnerIds;
	}

	public List getPartnerIdsFromContractAndMoqTerm(String buyerid, String brandcode) {
		String query = "select partner2_id from contract c join terms.moq_term m where partner1_id=" + buyerid
				+ " and c.id=m.contract_id and m.brand_code=\"" + brandcode + "\" limit 2;";
		List<Map<String, Object>> result = DBUtilities.exSelectQuery(query, "vms");
		List partnerIds = new ArrayList();
		for (int i = 0; i < result.size(); i++) {
			Object partnerId = result.get(i).get("partner2_id");
			partnerIds.add(partnerId);
		}
		System.out.println(partnerIds);
		return partnerIds;
	}

	public void insertIntoSourceSystemPartnerMapping(Long sellerId, Long partnerId) {
		String query = "insert into source_system_partner_mapping values(null," + sellerId + ",\"SELLER\"," + partnerId
				+ ",\"2017-05-25\",\"sandal\",\"2017-05-25\",0)";
		DBUtilities.exUpdateQuery(query, "vms");
	}

	public String getConfiguratonIdFromVms(String contractId, String category, String key, String value) {
		String query = "select id from configuration where entity_id=" + contractId + " and category=" + category
				+ " and configuration_key=" + key + " and value=" + value;
		Map<String, Object> result = DBUtilities.exSelectQueryForSingleRecord(query, "vms");
		return (String) result.get("id");
	}

	public void deleteContract(String contractId) {
		String query = "delete from contract where id='" + contractId + "'";
		DBUtilities.exUpdateQuery(query, "vms");
	}

	public void deletePOTerm(String vendorTermId) {
		String query = "delete from po_term where id='" + vendorTermId + "'";
		DBUtilities.exUpdateQuery(query, "terms");
	}

	public void deleteMoqTerm(String moqTermId) {
		String query = "delete from moq_term where id='" + moqTermId + "'";
		DBUtilities.exUpdateQuery(query, "terms");
	}

	public void deleteFromSeller(String sellerid) {
		String query = "delete from seller where id=" + sellerid;
		DBUtilities.exUpdateQuery(query, "seller");
	}

	public void deleteFromSourceSystemPartnerMapping(String sellerId) {
		String query = "delete from source_system_partner_mapping where source_system_id=" + sellerId;
		DBUtilities.exUpdateQuery(query, "vms");
	}

	public void deleteConfigurations(String sellerId, String contractId) {
		String query = "delete from configuration where entity_id=" + contractId;
		DBUtilities.exUpdateQuery(query, "vms");
		query = "delete from seller_configuration where seller_id=" + sellerId;
		DBUtilities.exUpdateQuery(query, "seller");
	}

	public void validatePOTermEntry(int id) {
		String query = "select count(*) from po_term where id=" + id;
		List<Map<String, Object>> result = DBUtilities.exSelectQuery(query, "terms");
		Assert.assertEquals(1, result.size(), "Vendor term entry not found in DB");
	}

	public String getPartner1Id() {
		String query = "select partner1_id from contract where partner2_id=" + getBuyerId()
				+ " order by partner1_id desc";
		Map<String, Object> result = DBUtilities.exSelectQueryForSingleRecord(query, "vms");
		return String.valueOf(result.get("partner1_id"));
	}

	public String getPartner2IdForStore() {
		String query = "select partner2_id from contract where contract_type_id=2 order by partner2_id desc";
		Map<String, Object> result = DBUtilities.exSelectQueryForSingleRecord(query, "vms");
		return String.valueOf(result.get("partner2_id"));
	}

	public String getPartner2Id() {
		String ids = getBuyerId() + "," + getStoreId();
		String query = "select partner2_id from contract where partner1_id not in (" + ids
				+ ") and partner2_id not in (" + ids + ") order by partner2_id desc";
		Map<String, Object> result = DBUtilities.exSelectQueryForSingleRecord(query, "vms");
		return String.valueOf(result.get("partner2_id"));
	}

	public String getPartner2Id(String buyer) {
		String query = "select partner2_id from contract c join terms.po_term p where partner1_id=" + buyer + " and c.id=p.contract_id order by partner2_id desc";
		Map<String, Object> result = DBUtilities.exSelectQueryForSingleRecord(query, "vms");
		return String.valueOf(result.get("partner2_id"));
	}

	public String getPartnerFromSourceSystem(String sourceSystemId) {
		String query = "select partner_id from source_system_partner_mapping where source_system_id=" + sourceSystemId;
		Map<String, Object> result = DBUtilities.exSelectQueryForSingleRecord(query, "vms");
		return String.valueOf(result.get("partner_id"));
	}

	public String getVendorIdFromVIM() {
		String query = "select vendor_id from vendor_item_master where commercial_type='JIT'";
		Map<String, Object> result = DBUtilities.exSelectQueryForSingleRecord(query, "vms");
		return String.valueOf(result.get("vendor_id"));
	}

	public String getVendorSKUCodeFromVIM(String vendorId) {
		String query = "select sku_code from vendor_item_master where vendor_id=" + vendorId;
		Map<String, Object> result = DBUtilities.exSelectQueryForSingleRecord(query, "vms");
		if (result != null) {
			return String.valueOf(result.get("sku_code"));
		} else {
			return null;
		}
	}

	public String getCommercialTypeFromVIM(String vendorId) {
		String query = "select commercial_type from vendor_item_master where vendor_id=" + vendorId;
		Map<String, Object> result = DBUtilities.exSelectQueryForSingleRecord(query, "vms");
		if (result != null) {
			return String.valueOf(result.get("commercial_type"));
		} else {
			return null;
		}
	}

	public String getStyleIdFromVIM(String vendorId) {
		String query = "select style_id from vendor_item_master where vendor_id=" + vendorId;
		Map<String, Object> result = DBUtilities.exSelectQueryForSingleRecord(query, "vms");
		return String.valueOf(result.get("style_id"));
	}

	public String getVIMIdFromVIM(String vendorId) {
		String query = "select id from vendor_item_master where vendor_id=" + vendorId;
		Map<String, Object> result = DBUtilities.exSelectQueryForSingleRecord(query, "vms");
		return String.valueOf(result.get("id"));
	}

}
