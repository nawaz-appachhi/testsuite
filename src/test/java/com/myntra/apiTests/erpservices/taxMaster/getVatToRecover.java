package com.myntra.apiTests.erpservices.taxMaster;

import com.myntra.apiTests.erpservices.Constants;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.taxmaster.client.entry.VatRecoveryEntry;
import com.myntra.taxmaster.client.entry.request.customerVat.BulkVatRecoverRequestEntry;
import com.myntra.taxmaster.client.response.VatRecoveryResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 16553 on 23/09/16.
 */
public class getVatToRecover extends BaseTest {

    static Initialize init = new Initialize("/Data/configuration");
    static org.slf4j.Logger log = LoggerFactory.getLogger(VatRuleEngine.class);
    VatRuleEngineHelper vatHelper = new VatRuleEngineHelper();
   

    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" },dataProviderClass = getVatToRecoverDP.class,dataProvider = "getVatToRecoverData", enabled = true)
    public void vatToRecover(BulkVatRecoverRequestEntry bulkVatRecoverRequestEntry,HashMap<Long,Double> expected_result_oms,HashMap<Long,Double> cart_rate) throws JAXBException,IOException {
        String payload = APIUtilities.convertXMLObjectToString(bulkVatRecoverRequestEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.TAXMASTER_PATH.VAT_TO_RECOVER, null, SERVICE_TYPE.TAXMASTER_SVC.toString(), HTTPMethods.POST, payload, vatHelper.getTaxMasterHeaderXML());
        VatRecoveryResponse response = (VatRecoveryResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new VatRecoveryResponse());
        validateResponse(response,expected_result_oms,cart_rate);
    }
    

    @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" },dataProviderClass = getVatToRecoverDP.class,dataProvider = "getVatToRecoverDataMaterial", enabled = true)
    public void vatToRecoverForMaterial(BulkVatRecoverRequestEntry bulkVatRecoverRequestEntry,HashMap<Long,Double> expected_result_oms,HashMap<Long,Double> cart_rate) throws JAXBException,IOException {
        String payload = APIUtilities.convertXMLObjectToString(bulkVatRecoverRequestEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.TAXMASTER_PATH.VAT_TO_RECOVER, null, SERVICE_TYPE.TAXMASTER_SVC.toString(), HTTPMethods.POST, payload, vatHelper.getTaxMasterHeaderXML());
        VatRecoveryResponse response = (VatRecoveryResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new VatRecoveryResponse());
        validateResponse(response,expected_result_oms,cart_rate);
        
    }
    private void validateResponse(VatRecoveryResponse response, HashMap<Long, Double> expected_result_oms, HashMap<Long, Double> cart_rate) {
        List<VatRecoveryEntry> vre= response.getData();
        SoftAssert sft = new SoftAssert();
        for (VatRecoveryEntry vatRecoveryEntry:vre) {
            Double vatEntry = vatRecoveryEntry.getTaxRate();
            Long sku = vatRecoveryEntry.getskuId();
            log.info("The vat rates for getVatToRecover API : " + vatEntry + " For OMS : " + expected_result_oms.get(sku) + " For cart : " + cart_rate.get(sku) + " SKUID: " +sku );
            sft.assertEquals( cart_rate.get(sku),expected_result_oms.get(sku), "The vat rates returned by cart  : " + cart_rate.get(sku) + " For OMS : " + expected_result_oms.get(sku) +" SKUID: " +sku );
            sft.assertEquals(vatEntry,expected_result_oms.get(sku),  "The vat rates for getVatToRecover API : " + vatEntry + " For OMS : " + expected_result_oms.get(sku) + " For cart : " + cart_rate.get(sku) + " SKUID: " +sku );
            sft.assertAll();
        }

    }
}