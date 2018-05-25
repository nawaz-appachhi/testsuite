package com.myntra.apiTests.erpservices.utility;

import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.SQLException;

/**
 * @author Abhijit Pati
 *
 */
public class WMSInStock extends BaseTest{

	static Logger log = Logger.getLogger(WMSInStock.class);
	WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();


	@Test(enabled=true, dataProvider="skus")
	public void executeQuery(String sku) throws SQLException {
		wmsServiceHelper.insertItem(sku);
	}

	@DataProvider(parallel = true)
	static Object [][] skus(ITestContext testContext) {
		Object[] data = {"3827:5000001:1,19,36","3828:6000001:28,36","3829:7000001:1,36","3830:8000001:1","3831:90000001:1,36","3832:91000001:28","3833:7100001:19","3834:6100001:28",
				"3835:5100001:1","3836:8200001:1,19,28,36","3837:8300001:36,28","3838:8400001:28","8005:8500001:1,36","8006:8600001:36,28","8007:8700001:1","15496:8900001:19",
				"890848:9000001:28","890847:9100001:28,36","890849:9200001:36","1243744:10200001:28,36",/*"3913:4000001:36","3914:5000001:36","3915:6000001:36","3916:7000001:36","3917:8000001:36",
				"3918:9000001:36,28,1","3919:1000001:36","3920:1100001:36","3870:1200000:28","3869:1300000:36","3868:1400000:36","3867:1500000:36","3866:1600000:36","3876:1700000:36","3875:1800000:36",
				"3874:1900000:28","3873:2000000:36","3872:2100000:36","3871:2200000:28","3881:2300000:36","3880:2400000:19","3879:2500000:36","3878:2600000:36","3877:2700000:36",*/"1074938:280000:20",
				"1074940:290000:20","1087066:300000:20","47416:410000:1,36,28,19","47417:420000:1,36,28,19","47418:430000:1,36,28,19","68509:440000:1,36,28,19","19910:340000:1,36,28,19",
				"19915:450000:1,36,28,19","19914:460000:1,36,28,19","47364:360000:1,36,28,19","47367:470000:1,36,28,19","47368:480000:1,36,28,19","47411:380000:1,36,28,19","47412:390000:1,36,28,19",
				"47413:490000:1,36,28,19","47365:500000:1,36,28,19","47361:510000:1,36,28,19","19909:520000:1,36,28,19"};

		Object[][] dataSet = new Object[data.length][];
		for(int i=0;i<data.length;i++){
			dataSet[i] = new Object[]{data[i]};
		}
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 50, 50);
	} 
}
