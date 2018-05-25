package com.myntra.apiTests.common;

import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

import java.util.Map;

/**
 * Created by abhijit.pati on 20/05/17.
 */
public class DBValidator {

    static org.slf4j.Logger log = LoggerFactory.getLogger(DBValidator.class);
    public static void validateDBResults(Map expectedResults, Map actualResults) {
        SoftAssert sft = new SoftAssert();
        expectedResults.remove("testcase_id");
        expectedResults.forEach((key, value) -> {
            log.debug("Expected Result for Key:  "+key +" value :  " +value +" Actual Result "+actualResults.get(key));
            if(value.equals("NA")){

            }else if(value == null || value.equals("NULL")){
                sft.assertNull(actualResults.get(key));
            }else {
                sft.assertEquals(value.toString(), actualResults.get(key).toString());
            }
        });
        sft.assertAll();
    }
}
