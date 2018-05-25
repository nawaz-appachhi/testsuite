package com.myntra.apiTests.erpservices.oms.Test;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import org.testng.annotations.Test;

import com.myntra.test.commons.testbase.BaseTest;
/**
 * Update/Insert IMS and ATP inventory details with Supply Type
 */
public class InsertSKUDataScriptInOMS extends BaseTest {
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	
	  @Test(enabled=true,description = "Insert SKUs in DB")
	    public void insertSKU() throws IOException, JAXBException {
	      ArrayList<String> skuList = new ArrayList<String>();
	      skuList.add("47414:36:10000:0:21:1");
	      skuList.add("47415:36:10000:0:21:1");
	      skuList.add("68508:36:10000:0:21:1");
	      skuList.add("11024769:36:10000:0:21:1");
	      skuList.add("11024761:36:10000:0:21:1");
	      skuList.add("11024762:36:10000:0:21:1");
	      skuList.add("11024763:36:10000:0:21:1");
	      skuList.add("11024764:36:10000:0:21:1");
	      skuList.add("11024765:36:10000:0:21:1");
	      skuList.add("11023505:36:10000:0:21:1");
	      skuList.add("11023506:36:10000:0:21:1");
	      skuList.add("11023507:36:10000:0:21:1");
	      skuList.add("47416:36:10000:0:21:1");
	      skuList.add("47417:36:10000:0:21:1");
	      skuList.add("47418:36:10000:0:21:1");
	      skuList.add("68509:36:10000:0:21:1");
	      skuList.add("796931:36:10000:0:21:1");
	      skuList.add("19912:36:10000:0:21:1");
	      skuList.add("19911:36:10000:0:21:1");
	      skuList.add("19910:36:10000:0:21:1");
	      skuList.add("19909:36:10000:0:21:1");
	      skuList.add("19916:36:10000:0:21:1");
	      skuList.add("19915:36:10000:0:21:1");
	      skuList.add("19914:36:10000:0:21:1");
	      skuList.add("19913:36:10000:0:21:1");
	      skuList.add("42161:36:10000:0:21:1");
	      skuList.add("42162:36:10000:0:21:1");
	      skuList.add("42163:36:10000:0:21:1");
	      skuList.add("42164:36:10000:0:21:1");
	      skuList.add("42165:36:10000:0:21:1");
	      skuList.add("42166:36:10000:0:21:1");
	      skuList.add("42167:36:10000:0:21:1");
	      skuList.add("42168:36:10000:0:21:1");
	      skuList.add("42169:36:10000:0:21:1");
	      skuList.add("74475:36:10000:0:21:1");
	      skuList.add("47409:36:10000:0:21:1");
	      skuList.add("47410:36:10000:0:21:1");
	      skuList.add("47411:36:10000:0:21:1");
	      skuList.add("47412:36:10000:0:21:1");
	      skuList.add("47413:36:10000:0:21:1");
	      skuList.add("3831:36:10000:0:21:1");
	      skuList.add("3832:36:10000:0:21:1");
	      skuList.add("3833:36:10000:0:21:1");
	      skuList.add("3834:36:10000:0:21:1");
	      skuList.add("3835:36:10000:0:21:1");
	      skuList.add("3836:36:10000:0:21:1");
	      skuList.add("3837:36:10000:0:21:1");
	      skuList.add("3838:36:10000:0:21:1");
	      skuList.add("3839:36:10000:0:21:1");
	      skuList.add("3840:36:10000:0:21:1");
	      skuList.add("3841:36:10000:0:21:1");
	      skuList.add("3842:36:10000:0:21:1");
	      skuList.add("3843:36:10000:0:21:1");
	      skuList.add("3844:36:10000:0:21:1");
	      skuList.add("3845:36:10000:0:21:1");
	      skuList.add("3846:36:10000:0:21:1");
	      skuList.add("3847:36:10000:0:21:1");
	      skuList.add("796931:36:10000:0:21:1");
	      skuList.add("796932:36:10000:0:21:1");
	      skuList.add("796933:36:10000:0:21:1");
	      skuList.add("796934:36:10000:0:21:1");
	      skuList.add("19909:36:10000:0:21:1");
	      skuList.add("19910:36:10000:0:21:1");
	      skuList.add("19911:36:10000:0:21:1");
	      skuList.add("19912:36:10000:0:21:1");
	      skuList.add("19913:36:10000:0:21:1");
	      skuList.add("19914:36:10000:0:21:1");
	      skuList.add("19915:36:10000:0:21:1");
	      skuList.add("19916:36:10000:0:21:1");
	      skuList.add("47361:36:10000:0:21:1");
	      skuList.add("47362:36:10000:0:21:1");
	      skuList.add("47363:36:10000:0:21:1");
	      skuList.add("47364:36:10000:0:21:1");
	      skuList.add("47365:36:10000:0:21:1");
	      skuList.add("47366:36:10000:0:21:1");
	      skuList.add("47367:36:10000:0:21:1");
	      skuList.add("47368:36:10000:0:21:1");
	      skuList.add("47369:36:10000:0:21:1");
	      skuList.add("74475:36:10000:0:21:1");
	      skuList.add("47409:36:10000:0:21:1");
	      skuList.add("47410:36:10000:0:21:1");
	      skuList.add("47411:36:10000:0:21:1");
	      skuList.add("47412:36:10000:0:21:1");
	      skuList.add("47413:36:10000:0:21:1");
	      skuList.add("47414:36:10000:0:21:1");
	      skuList.add("47415:36:10000:0:21:1");
	      skuList.add("68508:36:10000:0:21:1");
	      skuList.add("68509:36:10000:0:21:1");
	      skuList.add("47416:36:10000:0:21:1");
	      skuList.add("47417:36:10000:0:21:1");
	      skuList.add("47418:36:10000:0:21:1");
	      
	      
	      
	      for(String sku : skuList){
	    	  System.out.println(sku);
	    	  atpServiceHelper.updateInventoryDetailsForSeller(new String[]{sku}, "ON_HAND");
	    	  imsServiceHelper.updateInventoryForSeller(new String[]{sku}, "ON_HAND");
	      }

	        
	    }

}
