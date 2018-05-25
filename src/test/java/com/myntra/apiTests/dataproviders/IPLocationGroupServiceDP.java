package com.myntra.apiTests.dataproviders;

import com.myntra.lordoftherings.Toolbox;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;


public class IPLocationGroupServiceDP
{
	@DataProvider
	public Object[][] getLocationGroupServiceByIDDataProvider(ITestContext testContext)
	{
		String[] id1 = {"9"};		
		Object [][] dataSet =  new Object[][]{id1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] getLocationByIPDataProvider(ITestContext testContext)
	{
		String[] ip1 = {"182.71.248.194"};		
		Object [][] dataSet =  new Object[][]{ip1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] getLocationGroupService_Msgs(ITestContext testContext)
	{
		String[] ip1 = {"1", "\"success for fetchAll\"", "\"SUCCESS\""};		
		Object [][] dataSet =  new Object[][]{ip1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] getLocationGroupByIPDataProvider(ITestContext testContext)
	{
		String[] ip1 = {"182.71.248.194"};		
		Object [][] dataSet =  new Object[][]{ip1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] getLocationByIPMsgsDataProvider(ITestContext testContext)
	{
		String[] ip1 = {"182.71.248.194", "1", "\"this location for given ip : 182.71.248.194\"", "\"SUCCESS\""};		
		Object [][] dataSet =  new Object[][]{ip1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] createLoactionGroupDataProvider_PositiveCases(ITestContext testContext)
	{
		/*{
            "LocationGroup": {
                "createdBy": "${0}",
                 
                "description": "${1}",
                "locationGroupMapList": {
                     
                    "city": "${2}",
                    "country": "${3}",
                    "region": "${4}"
                },
                "title": "${5}",
                "type": "${6}"
            }
		}*/
		String[] ip1 = {"Laxmi","Test Description","Bangalore","INDIA","KARNATAKA","Bangalore test","G"};	
		String[] ip2 = {"Laxmi123","Test Description","Hyderabad","INDIA","Andhra Pradesh","Hyderabad test","G"};	
		String[] ip3 = {"Mohit","Description","Mumbai","INDIA","Mumbai","Mumbai test","I"};
		String[] ip4 = {"Mohit","Description","Mumbai","INDIA","Mumbai","Mumbai test","J"};
		String[] ip5 = {"123Laxmi","Description","Pune","INDIA","Pune","Pune test","K"};	
		String[] ip6 = {"Mohit@@@","Description","Mumbai","INDIA","Mumbai","Mumbai test","A"};
		String[] ip7 = {"Sarat","Description","Bangalore","INDIA","Bangalore Pradesh","Bangalore test","M"};
		Object [][] dataSet =  new Object[][]{ip1, ip2, ip3, ip4, ip5, ip6, ip7};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] createLoactionGroupDataProvider_NegativeCases(ITestContext testContext)
	{
		/*{
            "LocationGroup": {
                "createdBy": "${0}",
                 
                "description": "${1}",
                "locationGroupMapList": {
                     
                    "city": "${2}",
                    "country": "${3}",
                    "region": "${4}"
                },
                "title": "${5}",
                "type": "${6}"
            }
		}*/
		String[] ip1 = {"","","","","","",""};	
		String[] ip2 = {"BBBBBBBBB","Test Description","Hyderabad","INDIA","","Hyderabad test",""};	
		String[] ip3 = {"CCCCCCCCC","Test Description","Hyderabad","INDIA","Andhra Pradesh","Hyderabad test","G"};	
		String[] ip4 = {"234234","343242","4324234","-35435435","#$%$#%$#%","000000000000","fsdf^&*%&"};	
		String[] ip5 = {"","Test Description","Hyderabad","INDIA","Andhra Pradesh","","G"};	
		String[] ip6 = {null,"343242","4324234","-35435435",null,"-9882374983","fsdf^&*%&"};	
		String[] ip7 = {null,null,null,null,null,null,null};
		Object [][] dataSet =  new Object[][]{ip1, ip2, ip3, ip4, ip5, ip6, ip7};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	//---------------------------------------------------------------------------------------------
	

		
		@DataProvider
		public Object[][] getLocationGroupService_IDDataProvider1(ITestContext testContext)
		{
			String[] id1 = {"84"};		
			Object [][] dataSet =  new Object[][]{id1};
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
		}
		
		@DataProvider
		public Object[][] getLocationGroupService_InvalidIDDataProvider(ITestContext testContext)
		{
			String[] id1 = {"9001"};		
			Object [][] dataSet =  new Object[][]{id1};
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
		}
		
		@DataProvider
		public Object[][] getLocationGroupService_NegetiveIDDataProvider(ITestContext testContext)
		{
			String[] id1 = {"-1"};		
			Object [][] dataSet =  new Object[][]{id1};
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
		}
		
		
		@DataProvider
		public Object[][] getLocationGroupService_InvalidIPDataProvider(ITestContext testContext)
		{
			String[] ip1 = {"182.71.248"};		
			Object [][] dataSet =  new Object[][]{ip1};
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
		}
		
		@DataProvider
		public Object[][] getLocationGroupService_NegetiveIPDataProvider(ITestContext testContext)
		{
			String[] ip1 = {"-1"};		
			Object [][] dataSet =  new Object[][]{ip1};
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
		}
		
		@DataProvider
	    public Object[][] setLocationGroupService_Data1(ITestContext testContext){
			
			String[] arr1 = new String[]{"Rajesh","this is the location group","Bangalore","india","karnataka","title","G"}; 
			
	        Object[][] dataSet = new Object[][]{ arr1 };
	        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	            
	    }
	
	
	
	@DataProvider
	public Object[][] getLocationGroupServiceInvalidIDDataProvider(ITestContext testContext)
	{
		String[] id1 = {"9001"};		
		Object [][] dataSet =  new Object[][]{id1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] getLocationGroupServiceNegetiveIDDataProvider(ITestContext testContext)
	{
		String[] id1 = {"-1"};		
		Object [][] dataSet =  new Object[][]{id1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	
	@DataProvider
	public Object[][] getLocationGroupServiceInvalidIPDataProvider(ITestContext testContext)
	{
		String[] ip1 = {"182.71.248"};		
		Object [][] dataSet =  new Object[][]{ip1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] getLocationGroupServiceNegetiveIPDataProvider(ITestContext testContext)
	{
		String[] ip1 = {"-1"};		
		Object [][] dataSet =  new Object[][]{ip1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
    public Object[][] setLocationGroupServiceData(ITestContext testContext){
		
		String[] arr1 = new String[]{"Rajesh","this is the location group","Bangalore","india","karnataka","title","G"}; 
		
        Object[][] dataSet = new Object[][]{ arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
            
    }
}


