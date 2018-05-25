package com.myntra.apiTests.erpservices.sellerapis;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abhijit.pati on 05/07/16.
 */
public class SellerConfig{

    static Initialize init = new Initialize("/Data/configuration");
    static org.slf4j.Logger log = LoggerFactory.getLogger(SellerConfig.class);

    public enum SellerName{
        ALFA, MADURA, HANDH,PITNYBOWES;
    }

    public enum VectorSellerCode{
        VECT, TECH, HEAL, CONS;
    }

    public static Long getSellerID(SellerName sellerName){

        boolean isMigrated = init.Configurations.isInventoryMigrated();
        if(!isMigrated){
            log.info("Seller ID Fetched - 1");
            return 1L;
        }


        String name = null;
        switch (sellerName) {
            case ALFA:
                name = "TECH";
                break;
            case MADURA:
                name = "ADIT";
                break;
            case HANDH:
                name = "HEAL";
                break;
            case PITNYBOWES:
                name = "PITY";
                break;
            default:
                name = null;

            }
                HashMap<String, Object> sellerConfig = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select id from seller where barcode ='"+name+"';", "myntra_seller");
                long sellerID = (Long) sellerConfig.get("id");
                Assert.assertNotNull(sellerID, "SellerId for HEAL is null");
                log.info("Seller ID Fetched - "+sellerID);
                return sellerID;
        }

    public Boolean isVector(String sellerId){
        List<Map<String, Object>> vectSellerMaps = DBUtilities.exSelectQuery("select id from seller where barcode in ('"+VectorSellerCode.VECT+"','"+VectorSellerCode.TECH+"','" +
                ""+VectorSellerCode.CONS+"','"+VectorSellerCode.HEAL+"')","myntra_seller1");
        for (Map<String, Object> sellerIds:vectSellerMaps){
            if (sellerIds.get("id").toString().equals(""+sellerId)){
                return true;
            }
        }
        return false;
    }

}


