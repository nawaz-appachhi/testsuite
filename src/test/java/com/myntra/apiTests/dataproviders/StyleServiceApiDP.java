package com.myntra.apiTests.dataproviders;

import java.util.List;
import java.util.Random;

import com.myntra.apiTests.portalservices.checkoutservice.CheckoutDataProviderEnum;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.commons.IServiceConstants;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

/**
 * @author shankara.c
 *
 */
public class StyleServiceApiDP extends CommonUtils implements IServiceConstants
{
	String environment;
	
	public StyleServiceApiDP()
	{
		if(System.getenv(CheckoutDataProviderEnum.ENVIRONMENT.name()) == null)
			environment = CONFIGURATION.GetTestEnvironemnt().toString();
		else
			environment = System.getenv(CheckoutDataProviderEnum.ENVIRONMENT.name());
	}
	
	static StyleServiceApiHelper styleServiceHelper =  new StyleServiceApiHelper();
	
	String[] categories = { "Tshirt", "Shoes", "Shirts", "Jeans", "Kurtas", "Accessories" };
	
	List<Integer>  styleIds1 = styleServiceHelper.performSeachServiceToGetStyleIds(categories[0], "true", "false", "sneha.test01@myntra.com");
	List<Integer>  styleIds2 = styleServiceHelper.performSeachServiceToGetStyleIds(categories[1], "true", "false", "sneha.test01@myntra.com");
	List<Integer>  styleIds3 = styleServiceHelper.performSeachServiceToGetStyleIds(categories[2], "true", "false", "sneha.test01@myntra.com");
	List<Integer>  styleIds4 = styleServiceHelper.performSeachServiceToGetStyleIds(categories[3], "true", "false", "sneha.test01@myntra.com");
	List<Integer>  styleIds5 = styleServiceHelper.performSeachServiceToGetStyleIds(categories[4], "true", "false", "sneha.test01@myntra.com");
	List<Integer>  styleIds6 = styleServiceHelper.performSeachServiceToGetStyleIds(categories[5], "true", "false", "sneha.test01@myntra.com");
	
	List<Integer>  skuIds1 = styleServiceHelper.getSkuIds(String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))));
	List<Integer>  skuIds2 = styleServiceHelper.getSkuIds(String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))));
	List<Integer>  skuIds3 = styleServiceHelper.getSkuIds(String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))));
	List<Integer>  skuIds4 = styleServiceHelper.getSkuIds(String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))));
	List<Integer>  skuIds5 = styleServiceHelper.getSkuIds(String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))));
	List<Integer>  skuIds6 = styleServiceHelper.getSkuIds(String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))));

	@DataProvider     
	public Object[][] StyleServiceApiDP_getStyleDataForSingleStyleId_verifySuccessResponse(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5, 6);
	}
	
	
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_getStyleDataForSingleStyleId_verifySuccessStatusResponse(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "3", "Success", "SUCCESS" };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "3", "Success", "SUCCESS" };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "3", "Success", "SUCCESS" };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))), "3", "Success", "SUCCESS" };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))), "3", "Success", "SUCCESS" };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))), "3", "Success", "SUCCESS" };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_getStyleDataForSingleStyleId_verifyDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 6, 6);
	}
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_getStyleDataForSingleStyleId_verifyArticleTypeDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_getStyleDataForSingleStyleId_verifySubCategoryDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getStyleDataForSingleStyleId_verifyMasterCategoryDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getStyleDataForSingleStyleId_verifyBrandDetailsEntryNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getStyleDataForSingleStyleId_verifyStyleImageNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getStyleDataForSingleStyleId_verifyProductDescriptorsNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getStyleDataForSingleStyleId_verifyStyleOptionsNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getStyleDataForSingleStyleId_verifyArticleAttributeNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getStyleDataForSingleStyleId_verifyRecommendationsNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getStyleDataForSingleStyleId_verifyStyleVisibilityInfoNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getStyleDataForSingleStyleId_verifyTaxEntryDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_getStyleDataForSingleStyleId_verifyFailureStatusResponse(ITestContext Context)
	{
		String[] arr1 = { "-1", "53", "Row with given id not found", "ERROR" }; 
		String[] arr2 = { "0", "53", "Row with given id not found", "ERROR" }; 
	
		Object[][] dataSet = new Object[][]{ arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getStyleDataForListOfStyleId_verifySuccessResponse(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getStyleDataForListOfStyleId_verifySuccessStatusResponse(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())), "3", "Success", "SUCCESS" 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())), "3", "Success", "SUCCESS" 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size())), "3", "Success", "SUCCESS"
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getStyleDataForListOfStyleId_verifyDataNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getStyleDataForListOfStyleId_verifyArticleTypeDataNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getStyleDataForListOfStyleId_verifySubCategoryDataNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getStyleDataForListOfStyleId_verifyMasterCategoryDataNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getStyleDataForListOfStyleId_verifyBrandDetailsEntryNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getStyleDataForListOfStyleId_verifyStyleImageNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getStyleDataForListOfStyleId_verifyProductDescriptorsNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getStyleDataForListOfStyleId_verifyStyleOptionsNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getStyleDataForListOfStyleId_verifyArticleAttributeNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getStyleDataForListOfStyleId_verifyRecommendationsNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getStyleDataForListOfStyleId_verifyStyleVisibilityInfoNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getStyleDataForListOfStyleId_verifyTaxEntryDataNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getStyleDataForListOfStyleId_verifyFailureStatusResponse(ITestContext Context)
	{
		String[] arr1 = { "0"+","+"0"+","+"0", "53", "Row with given id not found", "ERROR" };
		String[] arr2 = { "-1"+","+"-1"+","+"-1", "53", "Row with given id not found", "ERROR" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleId_verifySuccessResponse(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleId_verifySuccessStatusResponse(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "3", "Success", "SUCCESS" };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "3", "Success", "SUCCESS" };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "3", "Success", "SUCCESS" };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))), "3", "Success", "SUCCESS" };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))), "3", "Success", "SUCCESS" };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))), "3", "Success", "SUCCESS" };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);

	}
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleId_verifyDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleId_verifyArticleTypeDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleId_verifySubCategoryDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleId_verifyMasterCategoryDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleId_verifyBrandDetailsEntryNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleId_verifyStyleImageNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleId_verifyProductDescriptorsNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleId_verifyStyleOptionsNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleId_verifyArticleAttributeNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleId_verifyRecommendationsNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleId_verifyStyleVisibilityInfoNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleId_verifyTaxEntryDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleId_verifyFailureStatusResponse(ITestContext Context)
	{
		String[] arr1 = { "-1", "53", "Row with given id not found", "ERROR" }; 
		String[] arr2 = { "0", "53", "Row with given id not found", "ERROR" }; 
	
		Object[][] dataSet = new Object[][]{ arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForListOfStyleId_verifySuccessResponse(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForListOfStyleId_verifySuccessStatusResponse(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())), "3", "Success", "SUCCESS" 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())), "3", "Success", "SUCCESS" 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size())), "3", "Success", "SUCCESS"
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForListOfStyleId_verifyDataNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForListOfStyleId_verifyArticleTypeDataNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForListOfStyleId_verifySubCategoryDataNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForListOfStyleId_verifyMasterCategoryDataNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForListOfStyleId_verifyBrandDetailsEntryNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForListOfStyleId_verifyStyleImageNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForListOfStyleId_verifyProductDescriptorsNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForListOfStyleId_verifyStyleOptionsNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForListOfStyleId_verifyArticleAttributeNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForListOfStyleId_verifyRecommendationsNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForListOfStyleId_verifyStyleVisibilityInfoNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForListOfStyleId_verifyTaxEntryDataNodes(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForListOfStyleId_verifyFailureStatusResponse(ITestContext Context)
	{
		String[] arr1 = { "0"+","+"0"+","+"0", "53", "Row with given id not found", "ERROR" };
		String[] arr2 = { "-1"+","+"-1"+","+"-1", "53", "Row with given id not found", "ERROR" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifySuccessResponse(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "640x480,320x240" };
		String[] arr2 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "1080X1440,540X720" };
		String[] arr3 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "180X240,360X480" };
		String[] arr4 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "150X200,96X128" };
		String[] arr5 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,48X64" };
		String[] arr6 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "540X720,48X64" };
		String[] arr7 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "150X200,180X240" };
		String[] arr8 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,96X128" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifySuccessStatusResponse(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "640x480,320x240", "3", "Success", "SUCCESS"  };
		String[] arr2 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "1080X1440,540X720", "3", "Success", "SUCCESS"  };
		String[] arr3 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "180X240,360X480", "3", "Success", "SUCCESS"  };
		String[] arr4 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "150X200,96X128", "3", "Success", "SUCCESS"  };
		String[] arr5 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,48X64", "3", "Success", "SUCCESS"  };
		String[] arr6 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "540X720,48X64", "3", "Success", "SUCCESS"  };
		String[] arr7 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "150X200,180X240", "3", "Success", "SUCCESS"  };
		String[] arr8 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,96X128", "3", "Success", "SUCCESS"  };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "640x480,320x240" };
		String[] arr2 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "1080X1440,540X720" };
		String[] arr3 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "180X240,360X480" };
		String[] arr4 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "150X200,96X128" };
		String[] arr5 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,48X64" };
		String[] arr6 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "540X720,48X64" };
		String[] arr7 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "150X200,180X240" };
		String[] arr8 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,96X128" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyArticleTypeDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "640x480,320x240" };
		String[] arr2 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "1080X1440,540X720" };
		String[] arr3 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "180X240,360X480" };
		String[] arr4 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "150X200,96X128" };
		String[] arr5 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,48X64" };
		String[] arr6 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "540X720,48X64" };
		String[] arr7 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "150X200,180X240" };
		String[] arr8 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,96X128" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifySubCategoryDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "640x480,320x240" };
		String[] arr2 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "1080X1440,540X720" };
		String[] arr3 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "180X240,360X480" };
		String[] arr4 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "150X200,96X128" };
		String[] arr5 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,48X64" };
		String[] arr6 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "540X720,48X64" };
		String[] arr7 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "150X200,180X240" };
		String[] arr8 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,96X128" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyMasterCategoryDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "640x480,320x240" };
		String[] arr2 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "1080X1440,540X720" };
		String[] arr3 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "180X240,360X480" };
		String[] arr4 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "150X200,96X128" };
		String[] arr5 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,48X64" };
		String[] arr6 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "540X720,48X64" };
		String[] arr7 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "150X200,180X240" };
		String[] arr8 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,96X128" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyBrandDetailsEntryNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "640x480,320x240" };
		String[] arr2 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "1080X1440,540X720" };
		String[] arr3 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "180X240,360X480" };
		String[] arr4 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "150X200,96X128" };
		String[] arr5 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,48X64" };
		String[] arr6 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "540X720,48X64" };
		String[] arr7 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))), "150X200,180X240" };
		String[] arr8 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))), "81X108,48X64" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 8, 8);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyStyleImageNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "640x480,320x240" };
		String[] arr2 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "1080X1440,540X720" };
		String[] arr3 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "180X240,360X480" };
		String[] arr4 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "150X200,96X128" };
		String[] arr5 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,48X64" };
		String[] arr6 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "540X720,48X64" };
		String[] arr7 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "150X200,180X240" };
		String[] arr8 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,96X128" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyProductDescriptorsNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "640x480,320x240" };
		String[] arr2 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "1080X1440,540X720" };
		String[] arr3 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "180X240,360X480" };
		String[] arr4 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "150X200,96X128" };
		String[] arr5 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,48X64" };
		String[] arr6 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "540X720,48X64" };
		String[] arr7 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "150X200,180X240" };
		String[] arr8 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,96X128" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyStyleOptionsNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "640x480,320x240" };
		String[] arr2 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "1080X1440,540X720" };
		String[] arr3 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "180X240,360X480" };
		String[] arr4 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "150X200,96X128" };
		String[] arr5 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,48X64" };
		String[] arr6 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "540X720,48X64" };
		String[] arr7 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "150X200,180X240" };
		String[] arr8 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,96X128" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyArticleAttributeNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "640x480,320x240" };
		String[] arr2 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "1080X1440,540X720" };
		String[] arr3 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "180X240,360X480" };
		String[] arr4 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "150X200,96X128" };
		String[] arr5 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,48X64" };
		String[] arr6 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "540X720,48X64" };
		String[] arr7 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "150X200,180X240" };
		String[] arr8 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,96X128" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyRecommendationsNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "640x480,320x240" };
		String[] arr2 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "1080X1440,540X720" };
		String[] arr3 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "180X240,360X480" };
		String[] arr4 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "150X200,96X128" };
		String[] arr5 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,48X64" };
		String[] arr6 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "540X720,48X64" };
		String[] arr7 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "150X200,180X240" };
		String[] arr8 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,96X128" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyTaxEntryDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "640x480,320x240" };
		String[] arr2 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "1080X1440,540X720" };
		String[] arr3 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "180X240,360X480" };
		String[] arr4 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "150X200,96X128" };
		String[] arr5 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,48X64" };
		String[] arr6 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "540X720,48X64" };
		String[] arr7 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "150X200,180X240" };
		String[] arr8 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,96X128" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyFailureStatusResponse(ITestContext Context)
	{
		String[] arr1 = { ENVIRONMENT_FOX7, "0", "640x480,320x240", "53", "Row with given id not found", "ERROR" };
		String[] arr2 = { ENVIRONMENT_FOX7, "-1", "640x480,320x240", "53", "Row with given id not found", "ERROR" };
		String[] arr3 = { ENVIRONMENT_FOX7, "99999999", "640x480,320x240", "53", "Row with given id not found", "ERROR" };
		
		String[] arr4 = { ENVIRONMENT_PROD, "0", "640x480,320x240", "56", "Error occurred while retrieving/processing data", "ERROR" };
		String[] arr5 = { ENVIRONMENT_PROD, "-1", "640x480,320x240", "56", "Error occurred while retrieving/processing data", "ERROR" };
		String[] arr6 = { ENVIRONMENT_PROD, "99999999", "640x480,320x240", "56", "Error occurred while retrieving/processing data", "ERROR" };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(environment, dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataBySingleSkuId_verifySuccessResponse(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))) };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))) };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))) };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))) };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))) };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataBySingleSkuId_verifySuccessStatusResponse(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))), "3", "Success", "SUCCESS" };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))), "3", "Success", "SUCCESS" };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))), "3", "Success", "SUCCESS" };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))), "3", "Success", "SUCCESS" };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))), "3", "Success", "SUCCESS" };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))), "3", "Success", "SUCCESS" };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);

	}
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_getPdpDataBySingleSkuId_verifyDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))) };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))) };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))) };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))) };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))) };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))) };

		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_getPdpDataBySingleSkuId_verifyArticleTypeDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))) };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))) };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))) };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))) };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))) };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))) };

		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_getPdpDataBySingleSkuId_verifySubCategoryDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))) };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))) };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))) };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))) };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))) };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataBySingleSkuId_verifyMasterCategoryDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))) };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))) };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))) };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))) };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))) };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))) };

		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataBySingleSkuId_verifyBrandDetailsEntryNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))) };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))) };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))) };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))) };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))) };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataBySingleSkuId_verifyStyleImageNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))) };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))) };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))) };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))) };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))) };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataBySingleSkuId_verifyProductDescriptorsNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))) };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))) };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))) };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))) };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))) };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataBySingleSkuId_verifyStyleOptionsNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))) };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))) };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))) };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))) };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))) };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataBySingleSkuId_verifyArticleAttributeNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))) };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))) };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))) };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))) };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))) };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataBySingleSkuId_verifyRecommendationsNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))) };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))) };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))) };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))) };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))) };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] StyleServiceApiDP_getPdpDataBySingleSkuId_verifyTaxEntryDataNodes(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))) };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))) };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))) };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))) };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))) };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataBySingleSkuId_verifyFailureStatusResponse(ITestContext Context)
	{
		/*
		String[] arr1 = { "-192374", "56", "Error occurred while retrieving/processing data", "ERROR" };
		String[] arr2 = { "36#@$#@$@#", "56", "Error occurred while retrieving/processing data", "ERROR" };
		String[] arr3 = { "0", "56", "Error occurred while retrieving/processing data", "ERROR" };
		String[] arr4 = { "12345", "56", "Error occurred while retrieving/processing data", "ERROR" };
		*/
		
		String[] arr1 = { "-192374", "3", "Success", "SUCCESS" };
	//	String[] arr2 = { "36#@$#@$@#", "3", "Success", "SUCCESS" };
		String[] arr3 = { "000000", "3", "Success", "SUCCESS" };
		String[] arr4 = { "0", "3", "Success", "SUCCESS" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doStyleIndexForSingleStyleId_verifySuccessResponse(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		//Negative Style Id
		String[] arr7 = { "-"+styleIds1.get(getRandomNumber(styleIds1.size())) };
		
		//Numeric with Special characters
	//	String[] arr8 = { "36#@$#@$@#" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doStyleIndexForSingleStyleId_verifySuccessStatusResponse(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "3", "Success", "SUCCESS" };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "3", "Success", "SUCCESS" };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "3", "Success", "SUCCESS" };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))), "3", "Success", "SUCCESS" };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))), "3", "Success", "SUCCESS" };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))), "3", "Success", "SUCCESS" };
	
		//Negative Style Id
		String[] arr7 = { "-"+styleIds1.get(getRandomNumber(styleIds1.size())), "3", "Success", "SUCCESS" };
		
		//Numeric with Special characters
	//	String[] arr8 = { "36#@$#@$@#", "3", "Success", "SUCCESS" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doStyleIndexForSingleStyleId_verifyFailureStatusResponse(ITestContext Context)
	{
		String[] arr1 = { "" };
		String[] arr2 = { "36safdsfdsf" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doStyleIndexForListOfStyleId_verifySuccessResponse(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ styleIds2.get(getRandomNumber(styleIds2.size()))+","+
                          styleIds3.get(getRandomNumber(styleIds3.size())) 
                        };

		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+ styleIds5.get(getRandomNumber(styleIds5.size()))+","+
		                  styleIds6.get(getRandomNumber(styleIds6.size())) 
		                };

		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
                          styleIds6.get(getRandomNumber(styleIds6.size()))+","+ styleIds4.get(getRandomNumber(styleIds4.size()))+","+
                          styleIds3.get(getRandomNumber(styleIds3.size()))+","+ styleIds5.get(getRandomNumber(styleIds5.size()))
                        };
		
		//Negative styleIds
		String[] arr4 = { "-1"+","+"-1"+","+"-1" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };	
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);		
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doStyleIndexForListOfStyleId_verifySuccessStatusResponse(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+styleIds2.get(getRandomNumber(styleIds2.size()))+","+
                          styleIds3.get(getRandomNumber(styleIds3.size())), "3", "Success", "SUCCESS"  
                        };

		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+styleIds5.get(getRandomNumber(styleIds5.size()))+","+
		                  styleIds6.get(getRandomNumber(styleIds6.size())), "3", "Success", "SUCCESS"  
		                };

		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
                          styleIds6.get(getRandomNumber(styleIds6.size()))+","+styleIds4.get(getRandomNumber(styleIds4.size()))+","+
                          styleIds3.get(getRandomNumber(styleIds3.size()))+","+styleIds1.get(getRandomNumber(styleIds4.size()))+","+
                          styleIds5.get(getRandomNumber(styleIds5.size())), "3", "Success", "SUCCESS" 
                        };

		//Negative styleIds
		String[] arr4 = { "-1"+","+"-1"+","+"-1", "3", "Success", "SUCCESS"  };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };	
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 4);		
	}
	
	@DataProvider

	public Object[][] StyleServiceApiDP_doStyleIndexForListOfStyleId_verifyFailureStatusResponse(ITestContext Context)
	{
		String[] arr1 = { "11safdsfdsf" };
		String[] arr2 = { "22safdsfdsf"+","+"23safdsfdsf" };
		String[] arr3 = { "33safdsfdsf"+","+"34safdsfdsf"+","+"35safdsfdsf" };
		String[] arr4 = { "44safdsfdsf"+","+"45safdsfdsf"+","+"46safdsfdsf"+","+"47safdsfdsf" };
		String[] arr5 = { "55safdsfdsf"+","+"56safdsfdsf"+","+"57safdsfdsf"+","+"58safdsfdsf"+","+"59safdsfdsf" };

		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}	
	
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doStyleIndexForSingleSkuId_verifySuccessResponse(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))) };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))) };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))) };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))) };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))) };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))) };

		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);		
	}
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_doStyleIndexForSingleSkuId_verifySuccessStatusResponse(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))), "3", "Success", "SUCCESS" };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))), "3", "Success", "SUCCESS" };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))), "3", "Success", "SUCCESS" };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))), "3", "Success", "SUCCESS" };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))), "3", "Success", "SUCCESS" };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))), "3", "Success", "SUCCESS" };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doStyleIndexForSingleSkuId_verifyFailureStatusResponse(ITestContext Context)
	{
		String[] arr1 = { "-1", "1", "Row with given id/info not found", "ERROR" };
	//	String[] arr2 = { "36#@$#@$@#", "1", "Row with given id/info not found", "ERROR" };
	
		Object[][] dataSet = new Object[][]{arr1};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doStyleIndexForListOfSkuId_verifySuccessResponse(ITestContext Context)
	{
		String[] arr1 = { skuIds1.get(getRandomNumber(skuIds1.size()))+","+skuIds2.get(getRandomNumber(skuIds2.size()))+","+
						  skuIds3.get(getRandomNumber(skuIds3.size()))
						};
		
		String[] arr2 = { skuIds4.get(getRandomNumber(skuIds4.size()))+","+skuIds5.get(getRandomNumber(skuIds5.size()))+","+
		                  skuIds6.get(getRandomNumber(skuIds6.size()))
		                };
		
		String[] arr3 = { skuIds1.get(getRandomNumber(skuIds1.size()))+","+skuIds2.get(getRandomNumber(skuIds2.size()))+","+ 
				          skuIds6.get(getRandomNumber(skuIds6.size()))+","+skuIds4.get(getRandomNumber(skuIds4.size()))+","+
				          skuIds3.get(getRandomNumber(skuIds3.size()))+","+skuIds5.get(getRandomNumber(skuIds5.size()))
				        };
		
		//Negative SkuIds
		String[] arr4 =  { "-1"+","+"-1"+","+"-1" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doStyleIndexForListOfSkuId_verifySuccessStatusResponse(ITestContext Context)
	{
		String[] arr1 = { skuIds1.get(getRandomNumber(skuIds1.size()))+","+skuIds2.get(getRandomNumber(skuIds2.size()))+","+
						  skuIds3.get(getRandomNumber(skuIds3.size())), "3", "Success", "SUCCESS" 
						};
		
		String[] arr2 = { skuIds4.get(getRandomNumber(skuIds4.size()))+","+skuIds5.get(getRandomNumber(skuIds5.size()))+","+
		                  skuIds6.get(getRandomNumber(skuIds6.size())), "3", "Success", "SUCCESS" 
		                };
		
		String[] arr3 = { skuIds1.get(getRandomNumber(skuIds1.size()))+","+skuIds2.get(getRandomNumber(skuIds2.size()))+","+ 
				          skuIds6.get(getRandomNumber(skuIds6.size()))+","+skuIds4.get(getRandomNumber(skuIds4.size()))+","+
				          skuIds3.get(getRandomNumber(skuIds3.size()))+","+skuIds5.get(getRandomNumber(skuIds5.size())), "3", "Success", "SUCCESS" 
				        };
		
		//Negative SkuIds
		String[] arr4 =  { "-1"+","+"-1"+","+"-1", "3", "Success", "SUCCESS" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doStyleIndexForListOfSkuId_verifyFailureStatusResponse(ITestContext Context)
	{
		String[] arr1 = { "66safdsfdsf" };
		String[] arr2 = { "77safdsfdsf"+","+"78safdsfdsf" };
		String[] arr3 = { "88safdsfdsf"+","+"89safdsfdsf"+","+"90safdsfdsf" };
		String[] arr4 = { "91safdsfdsf"+","+"92safdsfdsf"+","+"93safdsfdsf"+","+"94safdsfdsf" };
		String[] arr5 = { "22safdsfdsf"+","+"23safdsfdsf"+","+"24safdsfdsf"+","+"25safdsfdsf"+","+"26safdsfdsf" };

		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doInvalidateStyles_verifySuccessResponse(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+styleIds2.get(getRandomNumber(styleIds2.size()))+","+
                		  styleIds3.get(getRandomNumber(styleIds3.size())) 
                        };

		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+styleIds5.get(getRandomNumber(styleIds5.size()))+","+
                          styleIds6.get(getRandomNumber(styleIds6.size()))  
                        };

		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
                          styleIds6.get(getRandomNumber(styleIds6.size()))+","+styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds3.get(getRandomNumber(styleIds3.size()))+","+styleIds1.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size())) 
              			};
		
		//Negative StyleIds
		String[] arr4 =  { "-1"+","+"-1"+","+"-1" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doInvalidateStyles_verifySuccessStatusResponse(ITestContext Context)
	{
		
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+styleIds2.get(getRandomNumber(styleIds2.size()))+","+
						  styleIds3.get(getRandomNumber(styleIds3.size())), "3", "Success", "SUCCESS"  
              			};

		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+styleIds5.get(getRandomNumber(styleIds5.size()))+","+
		                  styleIds6.get(getRandomNumber(styleIds6.size())), "3", "Success", "SUCCESS"  
		              	};
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
		                  styleIds6.get(getRandomNumber(styleIds6.size()))+","+styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds3.get(getRandomNumber(styleIds3.size()))+","+styleIds1.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size())), "3", "Success", "SUCCESS" 
		    			};
		
		//Negative SkuIds
		String[] arr4 =  { "-1"+","+"-1"+","+"-1", "3", "Success", "SUCCESS" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doInvalidateStyles_verifyFailureStatusResponse(ITestContext Context)
	{
		String[] arr1 = { "66safdsfdsf" };
		String[] arr2 = { "77safdsfdsf"+","+"78safdsfdsf" };
		String[] arr3 = { "88safdsfdsf"+","+"89safdsfdsf"+","+"90safdsfdsf" };
		String[] arr4 = { "91safdsfdsf"+","+"92safdsfdsf"+","+"93safdsfdsf"+","+"94safdsfdsf" };
		String[] arr5 = { "22safdsfdsf"+","+"23safdsfdsf"+","+"24safdsfdsf"+","+"25safdsfdsf"+","+"26safdsfdsf" };

		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	private int getRandomNumber(int maxLen){
		System.out.println("maxLen : "+maxLen);
		return new Random().nextInt(maxLen);
	}
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_getStyleDataForSingleStyleId_verifyResponseDataNodesUsingSchemaValidations(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getStyleDataForListOfStyleId_verifyResponseDataNodesUsingSchemaValidations(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleId_verifyResponseDataNodesUsingSchemaValidations(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForListOfStyleId_verifyResponseDataNodesUsingSchemaValidations(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+
						  styleIds2.get(getRandomNumber(styleIds2.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size())) 
				        };
		
		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size()))+","+
				          styleIds6.get(getRandomNumber(styleIds6.size())) 
				        };
		
		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ 
				          styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
				          styleIds6.get(getRandomNumber(styleIds6.size()))+","+
				          styleIds4.get(getRandomNumber(styleIds4.size()))+","+
				          styleIds3.get(getRandomNumber(styleIds3.size()))+","+
				          styleIds5.get(getRandomNumber(styleIds5.size()))
				         };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyResponseDataNodesUsingSchemaValidations(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "640x480,320x240" };
		String[] arr2 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))), "1080X1440,540X720" };
		String[] arr3 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "180X240,360X480" };
		String[] arr4 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))), "150X200,96X128" };
		String[] arr5 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,48X64" };
		String[] arr6 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "540X720,48X64" };
		String[] arr7 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "150X200,180X240" };
		String[] arr8 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))), "81X108,96X128" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_getPdpDataBySingleSkuId_verifyResponseDataNodesUsingSchemaValidations(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))) };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))) };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))) };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))) };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))) };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doStyleIndexForSingleStyleId_verifyResponseDataNodesUsingSchemaValidations(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		//Negative Style Id
		String[] arr7 = { "-"+styleIds1.get(getRandomNumber(styleIds1.size())) };
		
		//Numeric with Special characters
	//	String[] arr8 = { "36#@$#@$@#" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doStyleIndexForListOfStyleId_verifyResponseDataNodesUsingSchemaValidations(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ styleIds2.get(getRandomNumber(styleIds2.size()))+","+
                          styleIds3.get(getRandomNumber(styleIds3.size())) 
                        };

		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+ styleIds5.get(getRandomNumber(styleIds5.size()))+","+
		                  styleIds6.get(getRandomNumber(styleIds6.size())) 
		                };

		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+ styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
                          styleIds6.get(getRandomNumber(styleIds6.size()))+","+ styleIds4.get(getRandomNumber(styleIds4.size()))+","+
                          styleIds3.get(getRandomNumber(styleIds3.size()))+","+ styleIds5.get(getRandomNumber(styleIds5.size()))
                        };
		
		//Negative styleIds
		String[] arr4 = { "-1"+","+"-1"+","+"-1" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };	
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);		
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doStyleIndexForSingleSkuId_verifyResponseDataNodesUsingSchemaValidations(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(skuIds1.get(getRandomNumber(skuIds1.size()))) };
		String[] arr2 = { String.valueOf(skuIds2.get(getRandomNumber(skuIds2.size()))) };
		String[] arr3 = { String.valueOf(skuIds3.get(getRandomNumber(skuIds3.size()))) };
		String[] arr4 = { String.valueOf(skuIds4.get(getRandomNumber(skuIds4.size()))) };
		String[] arr5 = { String.valueOf(skuIds5.get(getRandomNumber(skuIds5.size()))) };
		String[] arr6 = { String.valueOf(skuIds6.get(getRandomNumber(skuIds6.size()))) };

		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);		
	}
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doStyleIndexForListOfSkuId_verifyResponseDataNodesUsingSchemaValidations(ITestContext Context)
	{
		String[] arr1 = { skuIds1.get(getRandomNumber(skuIds1.size()))+","+skuIds2.get(getRandomNumber(skuIds2.size()))+","+
						  skuIds3.get(getRandomNumber(skuIds3.size()))
						};
		
		String[] arr2 = { skuIds4.get(getRandomNumber(skuIds4.size()))+","+skuIds5.get(getRandomNumber(skuIds5.size()))+","+
		                  skuIds6.get(getRandomNumber(skuIds6.size()))
		                };
		
		String[] arr3 = { skuIds1.get(getRandomNumber(skuIds1.size()))+","+skuIds2.get(getRandomNumber(skuIds2.size()))+","+ 
				          skuIds6.get(getRandomNumber(skuIds6.size()))+","+skuIds4.get(getRandomNumber(skuIds4.size()))+","+
				          skuIds3.get(getRandomNumber(skuIds3.size()))+","+skuIds5.get(getRandomNumber(skuIds5.size()))
				        };
		
		//Negative SkuIds
		String[] arr4 =  { "-1"+","+"-1"+","+"-1" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	
	
	@DataProvider
	public Object[][] StyleServiceApiDP_doInvalidateStyles_verifyResponseDataNodesUsingSchemaValidations(ITestContext Context)
	{
		String[] arr1 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+styleIds2.get(getRandomNumber(styleIds2.size()))+","+
                		  styleIds3.get(getRandomNumber(styleIds3.size())) 
                        };

		String[] arr2 = { styleIds4.get(getRandomNumber(styleIds4.size()))+","+styleIds5.get(getRandomNumber(styleIds5.size()))+","+
                          styleIds6.get(getRandomNumber(styleIds6.size()))  
                        };

		String[] arr3 = { styleIds1.get(getRandomNumber(styleIds1.size()))+","+styleIds2.get(getRandomNumber(styleIds2.size()))+","+ 
                          styleIds6.get(getRandomNumber(styleIds6.size()))+","+styleIds4.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds3.get(getRandomNumber(styleIds3.size()))+","+styleIds1.get(getRandomNumber(styleIds4.size()))+","+
		                  styleIds5.get(getRandomNumber(styleIds5.size())) 
              			};
		
		//Negative StyleIds
		String[] arr4 =  { "-1"+","+"-1"+","+"-1" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	
	/* 
     * GET SERVICE
     * Skucode to style id 
     * @author jitender.kumar1
     */
	@DataProvider     
	public Object[][] StyleServiceApiDP_getSkucodeToStyleId(ITestContext Context)
	{
		String[] arr1 = {"RDTLTRSR00020"};
		String[] arr2 = {"RDTLTRSR00022"};
		String[] arr3 = {"RDTLTRSR00023"};
		
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 3, 3);
	}
	
	/* 
     * 
     * Virtual bundling success and return period is checked 
     * @author jitender.kumar1
     */
	@DataProvider     
	public Object[][] StyleServiceApiDP_virtualBundling_verifySuccessResponse(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5, 6);
	}
	
	/* 
     * 
     * Virtual bundling isExchangable field is true
     * @author jitender.kumar1
     */
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_virtualBundling_isExchangeableisTrue(ITestContext Context)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5, 6);
	}
	
	/* 
     * 
     * Virtual return period is less than 30 days 
     * @author jitender.kumar1
     */
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_virtualBundling_returnPeriodisLessthan30(ITestContext Context)
	{
		String[] arr1 = {"1530"};
		String[] arr2 = {"1532"};
		String[] arr3 = {"1542"};
		String[] arr4 = {"10007"};
	
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 4, 4);
	}
	
	/* 
     * 
     * Virtual bundling isExchangeable and is returnable is false 
     * @author jitender.kumar1
     */
	@DataProvider     
	public Object[][] StyleServiceApiDP_virtualBundling_isExchangeableFalse(ITestContext Context)
	{
		String[] arr1 = {"28226"};
		String[] arr2 = {"322917"};
		String[] arr3 = {"44178"};
		String[] arr4 = {"322964"};
		String[] arr5 = {"44147"};
	
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5, 5);
	}
	/* 
     * 
     * Virtual bundling isExchangeable and is returnable is false 
     * @author jitender.kumar1
     */
	
	@DataProvider     
	public Object[][] StyleServiceApiDP_preOrder(ITestContext Context)
	{
		String[] arr1 = {"12349"};
		String[] arr2 = {"12348"};
		
	
	
		Object[][] dataSet = new Object[][]{ arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 2, 2);
	}
	
	
	/* 
     * 
     * PreOrder withCMS product tag 
     * @author jitender.kumar1
     */
	@DataProvider     
	public Object[][] StyleServiceApiDP_preOrderProductTag(ITestContext Context)
	{
		String[] arr1 = {"12349"};
		String[] arr2 = {"12348"};
		
	
	
		Object[][] dataSet = new Object[][]{ arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 1);
	}
	/* 
     * 
     * PreOrder withCMS preorder node 
     * @author jitender.kumar1
     */
	@DataProvider     
	public Object[][] StyleServiceApiDP_preOrderNode(ITestContext Context)
	{
		String[] arr1 = {"12349"};
		String[] arr2 = {"12348"};
		
	
	
		Object[][] dataSet = new Object[][]{ arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 1);
	}
	
	/* 
     * 
     * PreOrder with 
     * @author jitender.kumar1
     */
	@DataProvider     
	public Object[][] StyleServiceApiDP_preOrderWithoutPreOrderTag(ITestContext Context)
	{
		String[] arr1 = {"12347"};
		
	
	
		Object[][] dataSet = new Object[][]{ arr1 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 1);
	}
	
}
