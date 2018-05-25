package com.myntra.apiTests.dataproviders;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

/**
 * @author shankara.c
 *
 */
public class MobileSearchServiceDP extends CommonUtils
{
	@DataProvider
	public Object[][] MobileSearchServiceDP_getFirstPageOfSearch_verifySuccessResponse(ITestContext iTestContext)
	{
		String[] arr1 = { "Football Store", "10", "true", "true", "200" };
		String[] arr2 = { "Casual Shirts", "5", "true", "true", "200" };
		String[] arr3 = { "T-shirts & Collared Tees", "2", "true", "true", "200" };
		String[] arr4 = { "Formal Shirts", "1", "true", "true", "200" };
		String[] arr5 = { "Ethnic Wear", "20", "true", "true", "200" };
		String[] arr6 = { "Jeans", "6", "true", "true", "200" };
		String[] arr7 = { "Pants / Trousers", "5", "true", "true", "200" };
		String[] arr8 = { "Shorts", "14", "true", "true", "200" };
		String[] arr9 = { "Inner & Sleepwear", "16", "true", "true", "200" };
		String[] arr10 = { "Tracksuits & Pants", "12", "true", "true", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getFirstPageOfSearch_verifyResponseHeaders(ITestContext iTestContext)
	{
		String[] arr1 = { "Clothing", "20", "true", "true", "200" };
		String[] arr2 = { "Swimwear", "12", "true", "true", "200" };
		String[] arr3 = { "Sweaters & Sweatshirts", "17", "true", "true", "200" };
		String[] arr4 = { "Jackets & Blazers", "9", "true", "true", "200" };
		String[] arr5 = { "Gloves, Mufflers & Scarves", "6", "true", "true", "200" };
		String[] arr6 = { "Footwear", "6", "true", "true", "200" };
		String[] arr7 = { "Casual Shoes", "6", "true", "true", "200" };
		String[] arr8 = { "Sports Shoes", "6", "true", "true", "200" };
		String[] arr9 = { "Formal Shoes", "6", "true", "true", "200" };
		String[] arr10 = { "Casual & Sports Sandals", "6", "true", "true", "200" };
												
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getFirstPageOfSearch_verifyMetaTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Slippers & Flip Flops", "6", "true", "true", "200" };
		String[] arr2 = { "Men", "6", "true", "true", "200" };
		String[] arr3 = { "Sale", "6", "true", "true", "200" };
		String[] arr4 = { "New Arrivals", "6", "true", "true", "200" };
		String[] arr5 = { "Clothing", "6", "true", "true", "200" };
		String[] arr6 = { "Footwear", "6", "true", "true", "200" };
		String[] arr7 = { "Accessories", "6", "true", "true", "200" };
		String[] arr8 = { "Bags & Backpacks", "6", "true", "true", "200" };
		String[] arr9 = { "Belts, Ties & Cufflinks", "6", "true", "true", "200" };
		String[] arr10 = { "Caps & Hats", "6", "true", "true", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getFirstPageOfSearch_verifyNotificationTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "shoes", "0", "true", "true", "200" };
		String[] arr2 = { "Jewellery", "6", "true", "true", "200" };
		String[] arr3 = { "Sunglasses", "6", "true", "true", "200" };
		String[] arr4 = { "Wallets", "6", "true", "true", "200" };
		String[] arr5 = { "Watches", "6", "true", "true", "200" };
		String[] arr6 = { "Grooming", "6", "true", "true", "200" };
		String[] arr7 = { "Deos & Perfumes", "6", "true", "true", "200" };
		String[] arr8 = { "Bath, Body & Skin Care", "6", "true", "true", "200" };
		String[] arr9 = { "Featured Brand Stores", "6", "true", "true", "200" };
		String[] arr10 = { "Nike", "6", "true", "true", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getFirstPageOfSearch_verifyRequestObjectDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Men track pants", "10", "true", "true", "200" };
		String[] arr2 = { "Sandals & Clogs", "10", "true", "true", "200" };
		String[] arr3 = { "Girls Apparel", "10", "true", "true", "200" };
		String[] arr4 = { "Frocks", "10", "true", "true", "200" };
		String[] arr5 = { "Tops & T-shirts", "10", "true", "true", "200" };
		String[] arr6 = { "Bottomwear", "10", "true", "true", "200" };
		String[] arr7 = { "Sweaters, Sweatshirts, Jackets", "10", "true", "true", "200" };
		String[] arr8 = { "Girls Footwear", "10", "true", "true", "200" };
		String[] arr9 = { "Ballets", "10", "true", "true", "200" };
		String[] arr10 = { "Myntra Originals", "6", "true", "true", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getFirstPageOfSearch_verifyRequestObjectWithCuratedQueryDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "nike", "10", "true", "true", "200" };
		String[] arr2 = { "Puma", "6", "true", "true", "200" };
		String[] arr3 = { "Adidas", "6", "true", "true", "200" };
		String[] arr4 = { "United Colors of Benetton", "6", "true", "true", "200" };
		String[] arr5 = { "Reebok", "6", "true", "true", "200" };
		String[] arr6 = { "Arrow", "6", "true", "true", "200" };
		String[] arr7 = { "CAT", "6", "true", "true", "200" };
		String[] arr8 = { "Wrangler", "6", "true", "true", "200" };
		String[] arr9 = { "Superdry", "6", "true", "true", "200" };
		String[] arr10 = { "U.S. Polo Assn. Kids", "6", "true", "true", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getFirstPageOfSearch_verifyBodyWithFiltersDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Men track pants", "10", "true", "true", "200" };
		String[] arr2 = { "Myntra Originals", "10", "true", "true", "200" };
		String[] arr3 = { "Roadster", "10", "true", "true", "200" };
		String[] arr4 = { "HRX by Hrithik", "10", "true", "true", "200" };
		String[] arr5 = { "View All", "10", "true", "true", "200" };
		String[] arr6 = { "A-Z Brands", "10", "true", "true", "200" };
		String[] arr7 = { "Experience Stores", "10", "true", "true", "200" };
		String[] arr8 = { "Football Store", "10", "true", "true", "200" };
		String[] arr9 = { "Activewear Store", "10", "true", "true", "200" };
		String[] arr10 = { "Denim Store", "10", "true", "true", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getFirstPageOfSearch_verifyBodyWithProductsDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "nike", "10", "true", "true", "200" };
		String[] arr2 = { "Shoe store", "10", "true", "true", "200" };
		String[] arr3 = { "Designers", "10", "true", "true", "200" };
		String[] arr4 = { "Rohit Bal", "10", "true", "true", "200" };
		String[] arr5 = { "Rajesh Pratap Singh", "10", "true", "true", "200" };
		String[] arr6 = { "Premium Brands", "10", "true", "true", "200" };
		String[] arr7 = { "Desigual", "10", "true", "true", "200" };
		String[] arr8 = { "Timberland", "10", "true", "true", "200" };
		String[] arr9 = { "Sandals", "10", "true", "true", "200" };
		String[] arr10 = { "Wedges", "10", "true", "true", "200" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getNextPageOfSearch_verifySuccessResponse(ITestContext iTestContext)
	{
		String[] arr1 = { "Men track pants", "10", "true", "true", "next", "30", "200"  };
		String[] arr2 = { "All Heels", "10", "true", "true", "next", "30", "200"  };
		String[] arr3 = { "Flats & Ballets", "10", "true", "true", "next", "30", "200"  };
		String[] arr4 = { "Slippers & Flip Flops", "10", "true", "true", "next", "30", "200"  };
		String[] arr5 = { "Casual Shoes", "10", "true", "true", "next", "30", "200"  };
		String[] arr6 = { "Sports Shoes", "10", "true", "true", "next", "30", "200"  };
		String[] arr7 = { "Boots", "10", "true", "true", "next", "30", "200"  };
		String[] arr8 = { "Socks", "10", "true", "true", "next", "30", "200"  };
		String[] arr9 = { "Western Wear", "10", "true", "true", "next", "30", "200"  };
		String[] arr10 = { "Dresses", "10", "true", "true", "next", "30", "200"  };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getNextPageOfSearch_verifyResponseHeaders(ITestContext iTestContext)
	{
		String[] arr1 = { "Shirts", "10", "true", "true", "next", "30", "200"  };
		String[] arr2 = { "Tops & Tees", "10", "true", "true", "next", "30", "200"  };
		String[] arr3 = { "Shrugs & Jackets", "10", "true", "true", "next", "30", "200"  };
		String[] arr4 = { "Plus Sizes (Curvy)", "10", "true", "true", "next", "30", "200"  };
		String[] arr5 = { "Jeans", "10", "true", "true", "next", "30", "200"  };
		String[] arr6 = { "Jeggings", "10", "true", "true", "next", "30", "200"  };
		String[] arr7 = { "Shorts & Skirts", "10", "true", "true", "next", "30", "200"  };
		String[] arr8 = { "Pants & Trousers", "10", "true", "true", "next", "30", "200"  };
		String[] arr9 = { "Leggings & Capris", "10", "true", "true", "next", "30", "200"  };
		String[] arr10 = { "Ethnic Wear", "10", "true", "true", "next", "30", "200"  };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getNextPageOfSearch_verifyMetaTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Sarees", "10", "true", "true", "next", "30", "200"  };
		String[] arr2 = { "Kurtas, Kurtis & Suits", "10", "true", "true", "next", "30", "200"  };
		String[] arr3 = { "Salwars & Churidars", "10", "true", "true", "next", "30", "200"  };
		String[] arr4 = { "Dress Material", "10", "true", "true", "next", "30", "200"  };
		String[] arr5 = { "Lingerie & Sleepwear", "10", "true", "true", "next", "30", "200"  };
		String[] arr6 = { "Accessories", "10", "true", "true", "next", "30", "200"  };
		String[] arr7 = { "Bags & Wallets", "10", "true", "true", "next", "30", "200"  };
		String[] arr8 = { "Sunglasses", "10", "true", "true", "next", "30", "200"  };
		String[] arr9 = { "Watches", "10", "true", "true", "next", "30", "200"  };
		String[] arr10 = { "Belts", "10", "true", "true", "next", "30", "200"  };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getNextPageOfSearch_verifyNotificationTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Jewellery", "10", "true", "true", "next", "30", "200"  };
		String[] arr2 = { "Hair Accessories", "10", "true", "true", "next", "30", "200"  };
		String[] arr3 = { "Personal Care", "10", "true", "true", "next", "30", "200"  };
		String[] arr4 = { "Makeup", "10", "true", "true", "next", "30", "200"  };
		String[] arr5 = { "Skin Care", "10", "true", "true", "next", "30", "200"  };
		String[] arr6 = { "Hair & Body-Care", "10", "true", "true", "next", "30", "200"  };
		String[] arr7 = { "Deos & Perfumes ", "10", "true", "true", "next", "30", "200"  };
		String[] arr8 = { "Featured Brand Stores", "10", "true", "true", "next", "30", "200"  };
		String[] arr9 = { "United Colors of Benetton", "10", "true", "true", "next", "30", "200"  };
		String[] arr10 = { "Sisley", "10", "true", "true", "next", "30", "200"  };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getNextPageOfSearch_verifyRequestObjectDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "BIBA", "30", "true", "true", "next", "50", "200"  };
		String[] arr2 = { "Fabindia", "30", "true", "true", "next", "50", "200"  };
		String[] arr3 = { "Vero Moda", "30", "true", "true", "next", "50", "200"  };
		String[] arr4 = { "Levis", "30", "true", "true", "next", "50", "200"  };
		String[] arr5 = { "Pepe Jeans", "30", "true", "true", "next", "50", "200"  };
		String[] arr6 = { "Superdry", "30", "true", "true", "next", "50", "200"  };
		String[] arr7 = { "Myntra Originals", "30", "true", "true", "next", "50", "200"  };
		String[] arr8 = { "Dressberry", "30", "true", "true", "next", "50", "200"  };
		String[] arr9 = { "Roadster", "30", "true", "true", "next", "50", "200"  };
		String[] arr10 = { "Anouk", "30", "true", "true", "next", "50", "200"  };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getNextPageOfSearch_verifyRequestObjectWithCuratedQueryDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "nike", "10", "true", "true", "next", "30", "200" };
		String[] arr2 = { "Puma", "10", "true", "true", "next", "30", "200" };
		String[] arr3 = { "Adidas", "10", "true", "true", "next", "30", "200" };
		String[] arr4 = { "United Colors of Benetton", "10", "true", "true", "next", "30", "200" };
		String[] arr5 = { "Reebok", "10", "true", "true", "next", "30", "200" };
		String[] arr6 = { "Arrow", "10", "true", "true", "next", "30", "200" };
		String[] arr7 = { "CAT", "10", "true", "true", "next", "30", "200" };
		String[] arr8 = { "Wrangler", "10", "true", "true", "next", "30", "200" };
		String[] arr9 = { "Superdry", "10", "true", "true", "next", "30", "200" };
		String[] arr10 = { "U.S. Polo Assn. Kids", "10", "true", "true", "next", "30", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getNextPageOfSearch_verifyBodyWithFiltersDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "A-Z Brands", "30", "true", "true", "next", "50", "200"  };
		String[] arr2 = { "Myntra Picks", "30", "true", "true", "next", "50", "200"  };
		String[] arr3 = { "Party Edition", "30", "true", "true", "next", "50", "200"  };
		String[] arr4 = { "College Store", "30", "true", "true", "next", "50", "200"  };
		String[] arr5 = { "Shoe Store", "30", "true", "true", "next", "50", "200"  };
		String[] arr6 = { "Ethnic Store", "30", "true", "true", "next", "50", "200"  };
		String[] arr7 = { "Designers", "30", "true", "true", "next", "50", "200"  };
		String[] arr8 = { "Ritu Kumar", "30", "true", "true", "next", "50", "200"  };
		String[] arr9 = { "Manish Arora", "30", "true", "true", "next", "50", "200"  };
		String[] arr10 = { "Premium Brands", "30", "true", "true", "next", "50", "200"  };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getNextPageOfSearch_verifyBodyWithProductsDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Desigual", "30", "true", "true", "next", "50", "200"  };
		String[] arr2 = { "Guess", "30", "true", "true", "next", "50", "200"  };
		String[] arr3 = { "Kids", "30", "true", "true", "next", "50", "200"  };
		String[] arr4 = { "Boys Apparel", "30", "true", "true", "next", "50", "200"  };
		String[] arr5 = { "T-shirts & Shirts", "30", "true", "true", "next", "50", "200"  };
		String[] arr6 = { "Jeans, Trousers, Shorts", "30", "true", "true", "next", "50", "200"  };
		String[] arr7 = { "Sweaters, Sweatshirts, Jackets", "30", "true", "true", "next", "50", "200"  };
		String[] arr8 = { "Boys Footwear", "30", "true", "true", "next", "50", "200"  };
		String[] arr9 = { "Casual Shoes", "30", "true", "true", "next", "50", "200"  };
		String[] arr10 = { "Sports Shoes", "30", "true", "true", "next", "50", "200"  };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getSortedMobileSearch_verifySuccessResponse(ITestContext iTestContext)
	{
		String[] arr1 = { "Clothing", "10", "true", "true", ":popularity", "200"  };
		String[] arr2 = { "Clothing", "10", "true", "true", ":discount", "200"  };
		String[] arr3 = { "Clothing", "10", "true", "true", ":new", "200"  };
		String[] arr4 = { "Clothing", "10", "true", "true", ":high", "200"  };
		String[] arr5 = { "Clothing", "10", "true", "true", ":low", "200"  };
		
		String[] arr6 = { "Footwear", "10", "true", "true", ":popularity", "200"  };
		String[] arr7 = { "Footwear", "10", "true", "true", ":discount", "200"  };
		String[] arr8 = { "Footwear", "10", "true", "true", ":new", "200"  };
		String[] arr9 = { "Footwear", "10", "true", "true", ":high", "200"  };
		String[] arr10 = { "Footwear", "10", "true", "true", ":low", "200"  };
		
		String[] arr11 = { "Accessories", "10", "true", "true", ":popularity", "200"  };
		String[] arr12 = { "Accessories", "10", "true", "true", ":discount", "200"  };
		String[] arr13 = { "Accessories", "10", "true", "true", ":new", "200"  };
		String[] arr14 = { "Accessories", "10", "true", "true", ":high", "200"  };
		String[] arr15 = { "Accessories", "10", "true", "true", ":low", "200"  };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getSortedMobileSearch_verifyResponseHeaders(ITestContext iTestContext)
	{
		String[] arr1 = { "Grooming", "10", "true", "true", ":popularity", "200"  };
		String[] arr2 = { "Grooming", "10", "true", "true", ":discount", "200"  };
		String[] arr3 = { "Grooming", "10", "true", "true", ":new", "200"  };
		String[] arr4 = { "Grooming", "10", "true", "true", ":high", "200"  };
		String[] arr5 = { "Grooming", "10", "true", "true", ":low", "200"  };
		
		String[] arr6 = { "Featured Brand Stores", "10", "true", "true", ":popularity", "200"  };
		String[] arr7 = { "Featured Brand Stores", "10", "true", "true", ":discount", "200"  };
		String[] arr8 = { "Featured Brand Stores", "10", "true", "true", ":new", "200"  };
		String[] arr9 = { "Featured Brand Stores", "10", "true", "true", ":high", "200"  };
		String[] arr10 = { "Featured Brand Stores", "10", "true", "true", ":low", "200"  };
		
		String[] arr11 = { "Myntra Originals", "10", "true", "true", ":popularity", "200"  };
		String[] arr12 = { "Myntra Originals", "10", "true", "true", ":discount", "200"  };
		String[] arr13 = { "Myntra Originals", "10", "true", "true", ":new", "200"  };
		String[] arr14 = { "Myntra Originals", "10", "true", "true", ":high", "200"  };
		String[] arr15 = { "Myntra Originals", "10", "true", "true", ":low", "200"  };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getSortedMobileSearch_verifyMetaTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Eyewear", "10", "true", "true", ":popularity", "200"  };
		String[] arr2 = { "Eyewear", "10", "true", "true", ":discount", "200"  };
		String[] arr3 = { "Eyewear", "10", "true", "true", ":new", "200"  };
		String[] arr4 = { "Eyewear", "10", "true", "true", ":high", "200"  };
		String[] arr5 = { "Eyewear", "10", "true", "true", ":low", "200"  };
		
		String[] arr6 = { "Jewellery", "10", "true", "true", ":popularity", "200"  };
		String[] arr7 = { "Jewellery", "10", "true", "true", ":discount", "200"  };
		String[] arr8 = { "Jewellery", "10", "true", "true", ":new", "200"  };
		String[] arr9 = { "Jewellery", "10", "true", "true", ":high", "200"  };
		String[] arr10 = { "Jewellery", "10", "true", "true", ":low", "200"  };
		
		String[] arr11 = { "Sports Accessories", "5", "true", "true", ":popularity", "200"  };
		String[] arr12 = { "Sports Accessories", "5", "true", "true", ":discount", "200"  };
		String[] arr13 = { "Sports Accessories", "5", "true", "true", ":new", "200"  };
		String[] arr14 = { "Sports Accessories", "5", "true", "true", ":high", "200"  };
		String[] arr15 = { "Sports Accessories", "5", "true", "true", ":low", "200"  };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getSortedMobileSearch_verifyNotificationTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Bags", "10", "true", "true", ":popularity", "200"  };
		String[] arr2 = { "Bags", "10", "true", "true", ":discount", "200"  };
		String[] arr3 = { "Bags", "10", "true", "true", ":new", "200"  };
		String[] arr4 = { "Bags", "10", "true", "true", ":high", "200"  };
		String[] arr5 = { "Bags", "10", "true", "true", ":low", "200"  };
		
		String[] arr6 = { "Bottomwear", "10", "true", "true", ":popularity", "200"  };
		String[] arr7 = { "Bottomwear", "10", "true", "true", ":discount", "200"  };
		String[] arr8 = { "Bottomwear", "10", "true", "true", ":new", "200"  };
		String[] arr9 = { "Bottomwear", "10", "true", "true", ":high", "200"  };
		String[] arr10 = { "Bottomwear", "10", "true", "true", ":low", "200"  };
		
		String[] arr11 = { "Shoe Accessories", "6", "true", "true", ":popularity", "200"  };
		String[] arr12 = { "Shoe Accessories", "6", "true", "true", ":discount", "200"  };
		String[] arr13 = { "Shoe Accessories", "6", "true", "true", ":new", "200"  };
		String[] arr14 = { "Shoe Accessories", "6", "true", "true", ":high", "200"  };
		String[] arr15 = { "Shoe Accessories", "6", "true", "true", ":low", "200"  };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getSortedMobileSearch_verifyRequestObjectDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Topwear", "10", "true", "true", ":popularity", "200"  };
		String[] arr2 = { "Topwear", "10", "true", "true", ":discount", "200"  };
		String[] arr3 = { "Topwear", "10", "true", "true", ":new", "200"  };
		String[] arr4 = { "Topwear", "10", "true", "true", ":high", "200"  };
		String[] arr5 = { "Topwear", "10", "true", "true", ":low", "200"  };
		
		String[] arr6 = { "Flip Flops", "10", "true", "true", ":popularity", "200"  };
		String[] arr7 = { "Flip Flops", "10", "true", "true", ":discount", "200"  };
		String[] arr8 = { "Flip Flops", "10", "true", "true", ":new", "200"  };
		String[] arr9 = { "Flip Flops", "10", "true", "true", ":high", "200"  };
		String[] arr10 = { "Flip Flops", "10", "true", "true", ":low", "200"  };
		
		String[] arr11 = { "Innerwear", "4", "true", "true", ":popularity", "200"  };
		String[] arr12 = { "Innerwear", "4", "true", "true", ":discount", "200"  };
		String[] arr13 = { "Innerwear", "4", "true", "true", ":new", "200"  };
		String[] arr14 = { "Innerwear", "4", "true", "true", ":high", "200"  };
		String[] arr15 = { "Innerwear", "4", "true", "true", ":low", "200"  };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getSortedMobileSearch_verifyRequestObjectWithCuratedQueryDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "nike", "10", "true", "true", ":popularity", "200"  };
		String[] arr2 = { "nike", "10", "true", "true", ":discount", "200"  };
		String[] arr3 = { "nike", "10", "true", "true", ":new", "200"  };
		String[] arr4 = { "nike", "10", "true", "true", ":high", "200"  };
		String[] arr5 = { "nike", "10", "true", "true", ":low", "200"  };
		
		String[] arr6 = { "Reebok", "10", "true", "true", ":popularity", "200"  };
		String[] arr7 = { "Reebok", "10", "true", "true", ":discount", "200"  };
		String[] arr8 = { "Reebok", "10", "true", "true", ":new", "200"  };
		String[] arr9 = { "Reebok", "10", "true", "true", ":high", "200"  };
		String[] arr10 = { "Reebok", "10", "true", "true", ":low", "200"  };
		
		String[] arr11 = { "Arrow", "10", "true", "true", ":popularity", "200"  };
		String[] arr12 = { "Arrow", "10", "true", "true", ":discount", "200"  };
		String[] arr13 = { "Arrow", "10", "true", "true", ":new", "200"  };
		String[] arr14 = { "Arrow", "10", "true", "true", ":high", "200"  };
		String[] arr15 = { "Arrow", "10", "true", "true", ":low", "200"  };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getSortedMobileSearch_verifyBodyWithFiltersDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Socks", "10", "true", "true", ":popularity", "200"  };
		String[] arr2 = { "Socks", "10", "true", "true", ":discount", "200"  };
		String[] arr3 = { "Socks", "10", "true", "true", ":new", "200"  };
		String[] arr4 = { "Socks", "10", "true", "true", ":high", "200"  };
		String[] arr5 = { "Socks", "10", "true", "true", ":low", "200"  };
		
		String[] arr6 = { "Gloves", "4", "true", "true", ":popularity", "200"  };
		String[] arr7 = { "Gloves", "4", "true", "true", ":discount", "200"  };
		String[] arr8 = { "Gloves", "4", "true", "true", ":new", "200"  };
		String[] arr9 = { "Gloves", "4", "true", "true", ":high", "200"  };
		String[] arr10 = { "Gloves", "4", "true", "true", ":low", "200"  };
		
		String[] arr11 = { "Accessories", "9", "true", "true", ":popularity", "200"  };
		String[] arr12 = { "Accessories", "9", "true", "true", ":discount", "200"  };
		String[] arr13 = { "Accessories", "9", "true", "true", ":new", "200"  };
		String[] arr14 = { "Accessories", "9", "true", "true", ":high", "200"  };
		String[] arr15 = { "Accessories", "9", "true", "true", ":low", "200"  };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getSortedMobileSearch_verifyBodyWithProductsDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Headwear", "10", "true", "true", ":popularity", "200"  };
		String[] arr2 = { "Headwear", "10", "true", "true", ":discount", "200"  };
		String[] arr3 = { "Headwear", "10", "true", "true", ":new", "200"  };
		String[] arr4 = { "Headwear", "10", "true", "true", ":high", "200"  };
		String[] arr5 = { "Headwear", "10", "true", "true", ":low", "200"  };
		
		String[] arr6 = { "Accessories", "4", "true", "true", ":popularity", "200"  };
		String[] arr7 = { "Accessories", "4", "true", "true", ":discount", "200"  };
		String[] arr8 = { "Accessories", "4", "true", "true", ":new", "200"  };
		String[] arr9 = { "Accessories", "4", "true", "true", ":high", "200"  };
		String[] arr10 = { "Accessories", "4", "true", "true", ":low", "200"  };
		
		String[] arr11 = { "Watches", "7", "true", "true", ":popularity", "200"  };
		String[] arr12 = { "Watches", "7", "true", "true", ":discount", "200"  };
		String[] arr13 = { "Watches", "7", "true", "true", ":new", "200"  };
		String[] arr14 = { "Watches", "7", "true", "true", ":high", "200"  };
		String[] arr15 = { "Watches", "7", "true", "true", ":low", "200"  };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getSortedMobileSearch_verifyIsProductsInSortedOrder(ITestContext iTestContext)
	{
		String[] arr1 = { "Men track pants", "10", "true", "true", ":high", "200"  };
		String[] arr2 = { "Men track pants", "10", "true", "true", ":low", "200"  };
		
		String[] arr3 = { "Shop by Occasion", "10", "true", "true", ":high", "200"  };
		String[] arr4 = { "Shop by Occasion", "10", "true", "true", ":low", "200"  };
		
		String[] arr5 = { "Street Style", "10", "true", "true", ":high", "200"  };
		String[] arr6 = { "Street Style", "10", "true", "true", ":low", "200"  };
		
		String[] arr7 = { "Whats Hot", "10", "true", "true", ":high", "200"  };
		String[] arr8 = { "Whats Hot", "10", "true", "true", ":low", "200"  };
		
		String[] arr9 = { "Mega Trend", "10", "true", "true", ":high", "200"  };
		String[] arr10 = { "Mega Trend", "10", "true", "true", ":low", "200"  };
		

		String[] arr11 = { "Mini Trend", "10", "true", "true", ":high", "200"  };
		String[] arr12 = { "Mini Trend", "10", "true", "true", ":low", "200"  };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchWithPresetFilters_verifySuccessResponse(ITestContext iTestContext)
	{
		// Add gender as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add Brand as filter
		String[] arr5 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Nike").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Pepe Jeans").toJSONString(), "200" };
		String[] arr7 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Lee").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr9 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200"  };
		String[] arr10 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200"  };
		String[] arr11 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200"  };
		String[] arr12 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200"  };
		
		// Add color as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200"  };
		String[] arr14 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200"  };
		String[] arr15 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200"  };
		String[] arr16 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200"  };
		
		// Add discount as filter
		String[] arr17 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200"  };
		String[] arr18 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200"  };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchWithPresetFilters_verifyResponseHeaders(ITestContext iTestContext)
	{
		// Add gender as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add Brand as filter
		String[] arr5 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Nike").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Pepe Jeans").toJSONString(), "200" };
		String[] arr7 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Lee").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr9 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200"  };
		String[] arr10 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200"  };
		String[] arr11 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200"  };
		String[] arr12 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200"  };
		
		// Add color as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200"  };
		String[] arr14 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200"  };
		String[] arr15 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200"  };
		String[] arr16 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200"  };
		
		// Add discount as filter
		String[] arr17 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200"  };
		String[] arr18 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200"  };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchWithPresetFilters_verifyMetaTagNodes(ITestContext iTestContext)
	{
		// Add gender as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add Brand as filter
		String[] arr5 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Nike").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Pepe Jeans").toJSONString(), "200" };
		String[] arr7 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Lee").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr9 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200" };
		String[] arr10 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200" };
		String[] arr11 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200" };
		
		// Add color as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200" };
		String[] arr15 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200" };
		String[] arr16 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200" };
		
		// Add discount as filter
		String[] arr17 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchWithPresetFilters_verifyNotificationTagNodes(ITestContext iTestContext)
	{
		// Add gender as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add Brand as filter
		String[] arr5 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Nike").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Pepe Jeans").toJSONString(), "200" };
		String[] arr7 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Lee").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr9 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200" };
		String[] arr10 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200" };
		String[] arr11 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200" };
		
		// Add color as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200" };
		String[] arr15 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200" };
		String[] arr16 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200" };
		
		// Add discount as filter
		String[] arr17 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchWithPresetFilters_verifyRequestObjectDataNodes(ITestContext iTestContext)
	{
		// Add gender as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add Brand as filter
		String[] arr5 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Nike").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Pepe Jeans").toJSONString(), "200" };
		String[] arr7 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Lee").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr9 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200" };
		String[] arr10 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200" };
		String[] arr11 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200" };
		
		// Add color as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200" };
		String[] arr15 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200" };
		String[] arr16 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200" };
		
		// Add discount as filter
		String[] arr17 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchWithPresetFilters_verifyRequestObjectWithCuratedQueryDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr5 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200" };
		String[] arr6 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200" };
		String[] arr7 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200" };
		String[] arr8 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200" };
		
		// Add color as filter
		String[] arr9 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200" };
		String[] arr10 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200" };
		String[] arr11 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200" };
		String[] arr12 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200" };
		
		// Add discount as filter
		String[] arr13 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200" };
		String[] arr14 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchWithPresetFilters_verifyBodyWithFiltersDataNodes(ITestContext iTestContext)
	{
		// Add gender as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add Brand as filter
		String[] arr5 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Nike").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Pepe Jeans").toJSONString(), "200" };
		String[] arr7 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Lee").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr9 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200" };
		String[] arr10 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200" };
		String[] arr11 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200" };
		
		// Add color as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200" };
		String[] arr15 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200" };
		String[] arr16 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200" };
		
		// Add discount as filter
		String[] arr17 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchWithPresetFilters_verifyBodyWithProductsDataNodess(ITestContext iTestContext)
	{
		// Add gender as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add Brand as filter
		String[] arr5 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Nike").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Pepe Jeans").toJSONString(), "200" };
		String[] arr7 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Lee").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr9 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200" };
		String[] arr10 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200" };
		String[] arr11 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200" };
		
		// Add color as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200" };
		String[] arr15 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200" };
		String[] arr16 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200" };
		
		// Add discount as filter
		String[] arr17 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplySingleFilter_verifySuccessResponse(ITestContext iTestContext)
	{
		// Add gender as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add Brand as filter
		String[] arr5 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Nike").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Pepe Jeans").toJSONString(), "200" };
		String[] arr7 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Lee").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr9 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200" };
		String[] arr10 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200" };
		String[] arr11 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200" };
		
		// Add color as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200" };
		String[] arr15 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200" };
		String[] arr16 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200" };
		
		// Add discount as filter
		String[] arr17 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplySingleFilter_verifyResponseHeaders(ITestContext iTestContext)
	{
		// Add gender as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add Brand as filter
		String[] arr5 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Nike").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Pepe Jeans").toJSONString(), "200" };
		String[] arr7 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Lee").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr9 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200" };
		String[] arr10 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200" };
		String[] arr11 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200" };
		
		// Add color as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200" };
		String[] arr15 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200" };
		String[] arr16 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200" };
		
		// Add discount as filter
		String[] arr17 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplySingleFilter_verifyMetaTagNodes(ITestContext iTestContext)
	{
		// Add gender as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add Brand as filter
		String[] arr5 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Nike").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Pepe Jeans").toJSONString(), "200" };
		String[] arr7 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Lee").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr9 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200" };
		String[] arr10 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200" };
		String[] arr11 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200" };
		
		// Add color as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200" };
		String[] arr15 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200" };
		String[] arr16 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200" };
		
		// Add discount as filter
		String[] arr17 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplySingleFilter_verifyNotificationTagNodes(ITestContext iTestContext)
	{
		// Add gender as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add Brand as filter
		String[] arr5 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Nike").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Pepe Jeans").toJSONString(), "200" };
		String[] arr7 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Lee").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr9 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200" };
		String[] arr10 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200" };
		String[] arr11 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200" };
		
		// Add color as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200" };
		String[] arr15 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200" };
		String[] arr16 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200" };
		
		// Add discount as filter
		String[] arr17 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplySingleFilter_verifyRequestObjectDataNodes(ITestContext iTestContext)
	{
		// Add gender as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add Brand as filter
		String[] arr5 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Nike").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Pepe Jeans").toJSONString(), "200" };
		String[] arr7 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Lee").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr9 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200" };
		String[] arr10 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200" };
		String[] arr11 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200" };
		
		// Add color as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200" };
		String[] arr15 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200" };
		String[] arr16 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200" };
		
		// Add discount as filter
		String[] arr17 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplySingleFilter_verifyRequestObjectWithCuratedQueryDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr5 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200" };
		String[] arr6 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200" };
		String[] arr7 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200" };
		String[] arr8 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200" };
		
		// Add color as filter
		String[] arr9 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200" };
		String[] arr10 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200" };
		String[] arr11 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200" };
		String[] arr12 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200" };
		
		// Add discount as filter
		String[] arr13 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200" };
		String[] arr14 = { "nike", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplySingleFilter_verifyBodyWithFiltersDataNodes(ITestContext iTestContext)
	{
		// Add gender as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add Brand as filter
		String[] arr5 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Nike").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Pepe Jeans").toJSONString(), "200" };
		String[] arr7 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Lee").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr9 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200" };
		String[] arr10 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200" };
		String[] arr11 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200" };
		
		// Add color as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200" };
		String[] arr15 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200" };
		String[] arr16 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200" };
		
		// Add discount as filter
		String[] arr17 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplySingleFilter_verifyBodyWithProductsDataNodes(ITestContext iTestContext)
	{
		// Add gender as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add Brand as filter
		String[] arr5 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Nike").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Pepe Jeans").toJSONString(), "200" };
		String[] arr7 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Lee").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr9 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200" };
		String[] arr10 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200" };
		String[] arr11 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200" };
		
		// Add color as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200" };
		String[] arr15 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200" };
		String[] arr16 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200" };
		
		// Add discount as filter
		String[] arr17 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyDoubleFilters_verifySuccessResponse(ITestContext iTestContext)
	{
		// Add two Genders as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "women").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "boys").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "girls").toJSONString(), "200" };

		// Add two brands as filter
		String[] arr4 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "Pepe Jeans", "add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr5 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "Lee", "add", ":brand", "LOCOMOTIVE").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "HRX Jeans", "add", ":brand","Flying Machine").toJSONString(), "200" };

		// Add two Sizes as filter
		String[] arr7 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "0-1Y", "add", ":size", "1-3M").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "24", "add", ":size", "25/32").toJSONString(), "200" };
		String[] arr9 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "28/M", "add", ":size", "28/L").toJSONString(), "200" };

		// Add two colors as filter
		String[] arr10 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Red", "add", ":color", "Navy").toJSONString(), "200"  };
		String[] arr11 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Yellow", "add", ":color", "Pink").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Green", "add", ":color", "White").toJSONString(), "200" };

		// Add two discounts as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":discount", "20.0", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":discount", "10.0", "add", ":discount", "20.0").toJSONString(), "200" };

		// Gender and brand as filter
		String[] arr15 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":brand", "Levis").toJSONString(), "200"  };
		String[] arr16 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":brand", "Lee").toJSONString(), "200"  };
		String[] arr17 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":brand", "Nautica").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":brand", "Nautica").toJSONString(), "200" };

		// Gender and size as filter
		String[] arr19 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":size", "30").toJSONString(), "200" };
		String[] arr20 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":size", "27/32").toJSONString(), "200" };
		String[] arr21 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":size", "8-9Y").toJSONString(), "200" };
		String[] arr22 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":size", "6-12M").toJSONString(), "200" };

		// Gender and color
		String[] arr23 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr24 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr25 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":color", "Blue").toJSONString(), "200" };
		String[] arr26 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":color", "Blue").toJSONString(), "200" };

		// Gender and discount
		String[] arr27 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr28 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr29 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":discount", "0.0").toJSONString(), "200" };
		String[] arr30 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":discount", "10.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, 
				arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyDoubleFilters_verifyResponseHeaders(ITestContext iTestContext)
	{
		// Add two Genders as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "women").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "boys").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "girls").toJSONString(), "200" };

		// Add two brands as filter
		String[] arr4 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "Pepe Jeans", "add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr5 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "Lee", "add", ":brand", "LOCOMOTIVE").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "HRX Jeans", "add", ":brand","Flying Machine").toJSONString(), "200" };

		// Add two Sizes as filter
		String[] arr7 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "0-1Y", "add", ":size", "1-3M").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "24", "add", ":size", "25/32").toJSONString(), "200" };
		String[] arr9 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "28/M", "add", ":size", "28/L").toJSONString(), "200" };

		// Add two colors as filter
		String[] arr10 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Red", "add", ":color", "Navy").toJSONString(), "200"  };
		String[] arr11 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Yellow", "add", ":color", "Pink").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Green", "add", ":color", "White").toJSONString(), "200" };

		// Add two discounts as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":discount", "20.0", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":discount", "10.0", "add", ":discount", "20.0").toJSONString(), "200" };

		// Gender and brand as filter
		String[] arr15 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":brand", "Levis").toJSONString(), "200"  };
		String[] arr16 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":brand", "Lee").toJSONString(), "200"  };
		String[] arr17 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":brand", "Nautica").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":brand", "Nautica").toJSONString(), "200" };

		// Gender and size as filter
		String[] arr19 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":size", "30").toJSONString(), "200" };
		String[] arr20 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":size", "27/32").toJSONString(), "200" };
		String[] arr21 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":size", "8-9Y").toJSONString(), "200" };
		String[] arr22 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":size", "6-12M").toJSONString(), "200" };

		// Gender and color
		String[] arr23 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr24 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr25 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":color", "Blue").toJSONString(), "200" };
		String[] arr26 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":color", "Blue").toJSONString(), "200" };

		// Gender and discount
		String[] arr27 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr28 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr29 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":discount", "0.0").toJSONString(), "200" };
		String[] arr30 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":discount", "10.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, 
				arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyDoubleFilters_verifyMetaTagNodes(ITestContext iTestContext)
	{
		// Add two Genders as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "women").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "boys").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "girls").toJSONString(), "200" };

		// Add two brands as filter
		String[] arr4 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "Pepe Jeans", "add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr5 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "Lee", "add", ":brand", "LOCOMOTIVE").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "HRX Jeans", "add", ":brand","Flying Machine").toJSONString(), "200" };

		// Add two Sizes as filter
		String[] arr7 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "0-1Y", "add", ":size", "1-3M").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "24", "add", ":size", "25/32").toJSONString(), "200" };
		String[] arr9 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "28/M", "add", ":size", "28/L").toJSONString(), "200" };

		// Add two colors as filter
		String[] arr10 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Red", "add", ":color", "Navy").toJSONString(), "200"  };
		String[] arr11 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Yellow", "add", ":color", "Pink").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Green", "add", ":color", "White").toJSONString(), "200" };

		// Add two discounts as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":discount", "20.0", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":discount", "10.0", "add", ":discount", "20.0").toJSONString(), "200" };

		// Gender and brand as filter
		String[] arr15 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":brand", "Levis").toJSONString(), "200"  };
		String[] arr16 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":brand", "Lee").toJSONString(), "200"  };
		String[] arr17 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":brand", "Nautica").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":brand", "Nautica").toJSONString(), "200" };

		// Gender and size as filter
		String[] arr19 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":size", "30").toJSONString(), "200" };
		String[] arr20 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":size", "27/32").toJSONString(), "200" };
		String[] arr21 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":size", "8-9Y").toJSONString(), "200" };
		String[] arr22 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":size", "6-12M").toJSONString(), "200" };

		// Gender and color
		String[] arr23 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr24 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr25 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":color", "Blue").toJSONString(), "200" };
		String[] arr26 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":color", "Blue").toJSONString(), "200" };

		// Gender and discount
		String[] arr27 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr28 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr29 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":discount", "0.0").toJSONString(), "200" };
		String[] arr30 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":discount", "10.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, 
				arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyDoubleFilters_verifyNotificationTagNodes(ITestContext iTestContext)
	{
		// Add two Genders as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "women").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "boys").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "girls").toJSONString(), "200" };

		// Add two brands as filter
		String[] arr4 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "Pepe Jeans", "add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr5 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "Lee", "add", ":brand", "LOCOMOTIVE").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "HRX Jeans", "add", ":brand","Flying Machine").toJSONString(), "200" };

		// Add two Sizes as filter
		String[] arr7 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "0-1Y", "add", ":size", "1-3M").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "24", "add", ":size", "25/32").toJSONString(), "200" };
		String[] arr9 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "28/M", "add", ":size", "28/L").toJSONString(), "200" };

		// Add two colors as filter
		String[] arr10 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Red", "add", ":color", "Navy").toJSONString(), "200"  };
		String[] arr11 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Yellow", "add", ":color", "Pink").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Green", "add", ":color", "White").toJSONString(), "200" };

		// Add two discounts as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":discount", "20.0", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":discount", "10.0", "add", ":discount", "20.0").toJSONString(), "200" };

		// Gender and brand as filter
		String[] arr15 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":brand", "Levis").toJSONString(), "200"  };
		String[] arr16 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":brand", "Lee").toJSONString(), "200"  };
		String[] arr17 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":brand", "Nautica").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":brand", "Nautica").toJSONString(), "200" };

		// Gender and size as filter
		String[] arr19 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":size", "30").toJSONString(), "200" };
		String[] arr20 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":size", "27/32").toJSONString(), "200" };
		String[] arr21 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":size", "8-9Y").toJSONString(), "200" };
		String[] arr22 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":size", "6-12M").toJSONString(), "200" };

		// Gender and color
		String[] arr23 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr24 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr25 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":color", "Blue").toJSONString(), "200" };
		String[] arr26 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":color", "Blue").toJSONString(), "200" };

		// Gender and discount
		String[] arr27 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr28 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr29 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":discount", "0.0").toJSONString(), "200" };
		String[] arr30 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":discount", "10.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, 
				arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyDoubleFilters_verifyRequestObjectDataNodes(ITestContext iTestContext)
	{
		// Add two Genders as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "women").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "boys").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "girls").toJSONString(), "200" };

		// Add two brands as filter
		String[] arr4 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "Pepe Jeans", "add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr5 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "Lee", "add", ":brand", "LOCOMOTIVE").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "HRX Jeans", "add", ":brand","Flying Machine").toJSONString(), "200" };

		// Add two Sizes as filter
		String[] arr7 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "0-1Y", "add", ":size", "1-3M").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "24", "add", ":size", "25/32").toJSONString(), "200" };
		String[] arr9 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "28/M", "add", ":size", "28/L").toJSONString(), "200" };

		// Add two colors as filter
		String[] arr10 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Red", "add", ":color", "Navy").toJSONString(), "200"  };
		String[] arr11 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Yellow", "add", ":color", "Pink").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Green", "add", ":color", "White").toJSONString(), "200" };

		// Add two discounts as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":discount", "20.0", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":discount", "10.0", "add", ":discount", "20.0").toJSONString(), "200" };

		// Gender and brand as filter
		String[] arr15 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":brand", "Levis").toJSONString(), "200"  };
		String[] arr16 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":brand", "Lee").toJSONString(), "200"  };
		String[] arr17 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":brand", "Nautica").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":brand", "Nautica").toJSONString(), "200" };

		// Gender and size as filter
		String[] arr19 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":size", "30").toJSONString(), "200" };
		String[] arr20 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":size", "27/32").toJSONString(), "200" };
		String[] arr21 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":size", "8-9Y").toJSONString(), "200" };
		String[] arr22 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":size", "6-12M").toJSONString(), "200" };

		// Gender and color
		String[] arr23 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr24 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr25 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":color", "Blue").toJSONString(), "200" };
		String[] arr26 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":color", "Blue").toJSONString(), "200" };

		// Gender and discount
		String[] arr27 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr28 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr29 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":discount", "0.0").toJSONString(), "200" };
		String[] arr30 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":discount", "10.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, 
				arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyDoubleFilters_verifyRequestObjectWithCuratedQueryDataNodes(ITestContext iTestContext)
	{
		// Add two Genders as filter
		String[] arr1 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "women").toJSONString(), "200" };
		String[] arr2 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "boys").toJSONString(), "200" };
		String[] arr3 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "girls").toJSONString(), "200" };
		
		// Gender and discount
		String[] arr4 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr5 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr6 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":discount", "0.0").toJSONString(), "200" };
		
		// Add two Sizes as filter
		String[] arr7 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":size", "0-1Y", "add", ":size", "1-3M").toJSONString(), "200" };
		String[] arr8 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":size", "24", "add", ":size", "25/32").toJSONString(), "200" };
		String[] arr9 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":size", "28/M", "add", ":size", "28/L").toJSONString(), "200" };

		// Add two colors as filter
		String[] arr10 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":color", "Red", "add", ":color", "Navy").toJSONString(), "200"  };
		String[] arr11 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":color", "Yellow", "add", ":color", "Pink").toJSONString(), "200" };
		String[] arr12 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":color", "Green", "add", ":color", "White").toJSONString(), "200" };

		// Add two discounts as filter
		String[] arr13 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":discount", "20.0", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr14 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":discount", "10.0", "add", ":discount", "20.0").toJSONString(), "200" };

		// Gender and color
		String[] arr15 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr16 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr17 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":color", "Blue").toJSONString(), "200" };
		String[] arr18 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":color", "Blue").toJSONString(), "200" };
		
		// Gender and size as filter
		String[] arr19 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":size", "30").toJSONString(), "200" };
		String[] arr20 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":size", "27/32").toJSONString(), "200" };
		String[] arr21 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":size", "8-9Y").toJSONString(), "200" };
		String[] arr22 = { "nike", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":size", "6-12M").toJSONString(), "200" };

		Object[][] dataSet = new Object[][] {  arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, 
				arr16, arr17, arr18, arr19, arr20, arr21, arr22 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyDoubleFilters_verifyBodyWithFiltersDataNodes(ITestContext iTestContext)
	{
		// Add two Genders as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "women").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "boys").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "girls").toJSONString(), "200" };

		// Add two brands as filter
		String[] arr4 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "Pepe Jeans", "add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr5 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "Lee", "add", ":brand", "LOCOMOTIVE").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "HRX Jeans", "add", ":brand","Flying Machine").toJSONString(), "200" };

		// Add two Sizes as filter
		String[] arr7 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "0-1Y", "add", ":size", "1-3M").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "24", "add", ":size", "25/32").toJSONString(), "200" };
		String[] arr9 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "28/M", "add", ":size", "28/L").toJSONString(), "200" };

		// Add two colors as filter
		String[] arr10 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Red", "add", ":color", "Navy").toJSONString(), "200"  };
		String[] arr11 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Yellow", "add", ":color", "Pink").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Green", "add", ":color", "White").toJSONString(), "200" };

		// Add two discounts as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":discount", "20.0", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":discount", "10.0", "add", ":discount", "20.0").toJSONString(), "200" };

		// Gender and brand as filter
		String[] arr15 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":brand", "Levis").toJSONString(), "200"  };
		String[] arr16 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":brand", "Lee").toJSONString(), "200"  };
		String[] arr17 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":brand", "Nautica").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":brand", "Nautica").toJSONString(), "200" };

		// Gender and size as filter
		String[] arr19 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":size", "30").toJSONString(), "200" };
		String[] arr20 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":size", "27/32").toJSONString(), "200" };
		String[] arr21 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":size", "8-9Y").toJSONString(), "200" };
		String[] arr22 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":size", "6-12M").toJSONString(), "200" };

		// Gender and color
		String[] arr23 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr24 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr25 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":color", "Blue").toJSONString(), "200" };
		String[] arr26 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":color", "Blue").toJSONString(), "200" };

		// Gender and discount
		String[] arr27 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr28 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr29 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":discount", "0.0").toJSONString(), "200" };
		String[] arr30 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":discount", "10.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, 
				arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyDoubleFilters_verifyBodyWithProductsDataNodes(ITestContext iTestContext)
	{
		// Add two Genders as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "women").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "boys").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "girls").toJSONString(), "200" };

		// Add two brands as filter
		String[] arr4 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "Pepe Jeans", "add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr5 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "Lee", "add", ":brand", "LOCOMOTIVE").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "HRX Jeans", "add", ":brand","Flying Machine").toJSONString(), "200" };

		// Add two Sizes as filter
		String[] arr7 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "0-1Y", "add", ":size", "1-3M").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "24", "add", ":size", "25/32").toJSONString(), "200" };
		String[] arr9 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "28/M", "add", ":size", "28/L").toJSONString(), "200" };

		// Add two colors as filter
		String[] arr10 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Red", "add", ":color", "Navy").toJSONString(), "200"  };
		String[] arr11 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Yellow", "add", ":color", "Pink").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Green", "add", ":color", "White").toJSONString(), "200" };

		// Add two discounts as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":discount", "20.0", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":discount", "10.0", "add", ":discount", "20.0").toJSONString(), "200" };

		// Gender and brand as filter
		String[] arr15 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":brand", "Levis").toJSONString(), "200"  };
		String[] arr16 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":brand", "Lee").toJSONString(), "200"  };
		String[] arr17 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":brand", "Nautica").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":brand", "Nautica").toJSONString(), "200" };

		// Gender and size as filter
		String[] arr19 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":size", "30").toJSONString(), "200" };
		String[] arr20 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":size", "27/32").toJSONString(), "200" };
		String[] arr21 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":size", "8-9Y").toJSONString(), "200" };
		String[] arr22 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":size", "6-12M").toJSONString(), "200" };

		// Gender and color
		String[] arr23 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr24 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr25 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":color", "Blue").toJSONString(), "200" };
		String[] arr26 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":color", "Blue").toJSONString(), "200" };

		// Gender and discount
		String[] arr27 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr28 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr29 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":discount", "0.0").toJSONString(), "200" };
		String[] arr30 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":discount", "10.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, 
				arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyAllFilters_verifySuccessResponse(ITestContext iTestContext)
	{
		String[] arr1 = { "clothing", "10", "true", "true", prepareAllFilters("add", ":gender", "men", "add", ":brand", "Adidas", "add", ":size", "30", 
				"add", ":color", "Red", "add", ":discount", "0.0" ).toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyAllFilters_verifyResponseHeaders(ITestContext iTestContext)
	{
		String[] arr1 = { "clothing", "10", "true", "true", prepareAllFilters("add", ":gender", "men", "add", ":brand", "Adidas", "add", ":size", "30", 
				"add", ":color", "Red", "add", ":discount", "0.0" ).toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyAllFilters_verifyMetaTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "clothing", "10", "true", "true", prepareAllFilters("add", ":gender", "men", "add", ":brand", "Adidas", "add", ":size", "30", 
				"add", ":color", "Red", "add", ":discount", "0.0" ).toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyAllFilters_verifyNotificationTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "clothing", "10", "true", "true", prepareAllFilters("add", ":gender", "men", "add", ":brand", "Adidas", "add", ":size", "30", 
				"add", ":color", "Red", "add", ":discount", "0.0" ).toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyAllFilters_verifyRequestObjectDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "clothing", "10", "true", "true", prepareAllFilters("add", ":gender", "men", "add", ":brand", "Adidas", "add", ":size", "30", 
				"add", ":color", "Red", "add", ":discount", "0.0" ).toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyAllFilters_verifyRequestObjectWithCuratedQueryDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "nike", "10", "true", "true", prepareAllFilters("add", ":gender", "men", "add", ":color", "Green", "add", ":size", "30", 
				"add", ":color", "Red", "add", ":discount", "0.0" ).toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyAllFilters_verifyBodyWithFiltersDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "clothing", "10", "true", "true", prepareAllFilters("add", ":gender", "men", "add", ":brand", "Adidas", "add", ":size", "30", "add", 
				":color", "Red", "add", ":discount", "0.0" ).toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyAllFilters_verifyBodyWithProductsDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "clothing", "10", "true", "true", prepareAllFilters("add", ":gender", "men", "add", ":color", "Blue", "add", ":size", "30", "add", 
				":color", "Red", "add", ":discount", "0.0" ).toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQuery_verifySuccessResponse(ITestContext iTestContext)
	{
		String[] arr1 = { "Sandals & Clogs", "200" };
		String[] arr2 = { "Girls Apparel", "200" };
		String[] arr3 = { "Frocks", "200" };
		String[] arr4 = { "Tops & T-shirts", "200" };
		String[] arr5 = { "Bottomwear", "200" };
		String[] arr6 = { "Sweaters, Sweatshirts, Jackets", "200" };
		String[] arr7 = { "Girls Footwear", "200" };
		String[] arr8 = { "Elle Kids", "200" };
		String[] arr9 = { "Happy Face", "200" };
		String[] arr10 = { "Gini & Jony", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQuery_verifyDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Sandals & Clogs", "200" };
		String[] arr2 = { "Girls Apparel", "200" };
		String[] arr3 = { "Frocks", "200" };
		String[] arr4 = { "Tops & T-shirts", "200" };
		String[] arr5 = { "Bottomwear", "200" };
		String[] arr6 = { "Sweaters, Sweatshirts, Jackets", "200" };
		String[] arr7 = { "Girls Footwear", "200" };
		String[] arr8 = { "Elle Kids", "200" };
		String[] arr9 = { "Happy Face", "200" };
		String[] arr10 = { "Gini & Jony", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQuery_verifyFiltersDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Sandals & Clogs", "200" };
		String[] arr2 = { "Girls Apparel", "200" };
		String[] arr3 = { "Frocks", "200" };
		String[] arr4 = { "Tops & T-shirts", "200" };
		String[] arr5 = { "Bottomwear", "200" };
		String[] arr6 = { "Sweaters, Sweatshirts, Jackets", "200" };
		String[] arr7 = { "Girls Footwear", "200" };
		String[] arr8 = { "Tracksuits", "200" };
		String[] arr9 = { "Wristbands", "200" };
		String[] arr10 = { "Sweaters", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQuery_verifyProductsDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Nike", "200" };
		String[] arr2 = { "Clutches", "200" };
		String[] arr3 = { "Earrings", "200" };
		String[] arr4 = { "Sunglasses", "200" };
		String[] arr5 = { "Bottomwear", "200" };
		String[] arr6 = { "Sweaters, Sweatshirts, Jackets", "200" };
		String[] arr7 = { "Swimwear", "200" };
		String[] arr8 = { "Tracksuits", "200" };
		String[] arr9 = { "Flats", "200" };
		String[] arr10 = { "Shorts", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithOneFilter_verifySuccessResponse(ITestContext iTestContext)
	{
		String[] arr1 = { "Nike", "categories", "Sports&#32;Shoes", "200" };
		String[] arr2 = { "Dress", "brands", "Anouk", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithOneFilter_verifyDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Nike", "categories", "Sports&#32;Shoes", "200" };
		String[] arr2 = { "Dress", "brands", "Anouk", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithOneFilter_verifyFiltersDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Nike", "categories", "Sports&#32;Shoes", "200" };
		String[] arr2 = { "Dress", "brands", "Anouk", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithOneFilter_verifyProductsDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Nike", "categories", "Sports&#32;Shoes", "200" };
		String[] arr2 = { "Dress", "brands", "Anouk", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithTwoFilters_verifySuccessResponse(ITestContext iTestContext)
	{
		String[] arr1 = { "Dress", "brands", "Anouk", "colour", "blue", "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithTwoFilters_verifyDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Dress", "brands", "Anouk", "colour", "blue", "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithTwoFilters_verifyFiltersDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Dress", "brands", "Anouk", "colour", "blue", "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithTwoFilters_verifyProductsDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Dress", "brands", "Nike", "colour", "blue", "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithAllFilters_verifySuccessResponse(ITestContext iTestContext)
	{
		String[] arr1 = { "Jeans", "brands", "Lee", "colour", "blue", "discount", "20.0", "gender", "men", "size", "30", "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithAllFilters_verifyDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Jeans", "brands", "Lee", "colour", "blue", "discount", "20.0", "gender", "men", "size", "30", "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithAllFilters_verifyFiltersDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Jeans", "brands", "Lee", "colour", "blue", "discount", "20.0", "gender", "men", "size", "30", "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithAllFilters_verifyProductsDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Puma", "categories", "Sports&#32;Shoes", "colour", "blue", "discount", "0.0", "gender", "men", "size", "10", "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithSortBy_verifySuccessResponse(ITestContext iTestContext)
	{
		String[] arr1 = { "Buy 1 Get 1", "popularity", "200" };
		String[] arr2 = { "Buy 1 Get 1", "new", "200" };
		String[] arr3 = { "Buy 1 Get 1", "discount", "200" };
		String[] arr4 = { "Buy 1 Get 1", "low", "200" };
		String[] arr5 = { "Buy 1 Get 1", "high", "200" };
		
		String[] arr6 = { "AtoZ", "popularity", "200" };
		String[] arr7 = { "AtoZ", "new", "200" };
		String[] arr8 = { "AtoZ", "discount", "200" };
		String[] arr9 = { "AtoZ", "low", "200" };
		String[] arr10 = { "AtoZ", "high", "200" };
		
		String[] arr11 = { "Shirts", "popularity", "200" };
		String[] arr12 = { "Shirts", "new", "200" };
		String[] arr13 = { "Shirts", "discount", "200" };
		String[] arr14 = { "Shirts", "low", "200" };
		String[] arr15 = { "Shirts", "high", "200" };
		
		String[] arr16 = { "Tops", "popularity", "200" };
		String[] arr17 = { "Tops", "new", "200" };
		String[] arr18 = { "Tops", "discount", "200" };
		String[] arr19 = { "Tops", "low", "200" };
		String[] arr20 = { "Tops", "high", "200" };
		
		String[] arr21 = { "Heels", "popularity", "200" };
		String[] arr22 = { "Heels", "new", "200" };
		String[] arr23 = { "Heels", "discount", "200" };
		String[] arr24 = { "Heels", "low", "200" };
		String[] arr25 = { "Heels", "high", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15,
				arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithSortBy_verifyDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Buy 1 Get 1", "popularity", "200" };
		String[] arr2 = { "Buy 1 Get 1", "new", "200" };
		String[] arr3 = { "Buy 1 Get 1", "discount", "200" };
		String[] arr4 = { "Buy 1 Get 1", "low", "200" };
		String[] arr5 = { "Buy 1 Get 1", "high", "200" };
		
		String[] arr6 = { "AtoZ", "popularity", "200" };
		String[] arr7 = { "AtoZ", "new", "200" };
		String[] arr8 = { "AtoZ", "discount", "200" };
		String[] arr9 = { "AtoZ", "low", "200" };
		String[] arr10 = { "AtoZ", "high", "200" };
		
		String[] arr11 = { "Shirts", "popularity", "200" };
		String[] arr12 = { "Shirts", "new", "200" };
		String[] arr13 = { "Shirts", "discount", "200" };
		String[] arr14 = { "Shirts", "low", "200" };
		String[] arr15 = { "Shirts", "high", "200" };
		
		String[] arr16 = { "Tops", "popularity", "200" };
		String[] arr17 = { "Tops", "new", "200" };
		String[] arr18 = { "Tops", "discount", "200" };
		String[] arr19 = { "Tops", "low", "200" };
		String[] arr20 = { "Tops", "high", "200" };
		
		String[] arr21 = { "Heels", "popularity", "200" };
		String[] arr22 = { "Heels", "new", "200" };
		String[] arr23 = { "Heels", "discount", "200" };
		String[] arr24 = { "Heels", "low", "200" };
		String[] arr25 = { "Heels", "high", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15,
				arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithSortBy_verifyFiltersDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Buy 1 Get 1", "popularity", "200" };
		String[] arr2 = { "Buy 1 Get 1", "new", "200" };
		String[] arr3 = { "Buy 1 Get 1", "discount", "200" };
		String[] arr4 = { "Buy 1 Get 1", "low", "200" };
		String[] arr5 = { "Buy 1 Get 1", "high", "200" };
		
		String[] arr6 = { "AtoZ", "popularity", "200" };
		String[] arr7 = { "AtoZ", "new", "200" };
		String[] arr8 = { "AtoZ", "discount", "200" };
		String[] arr9 = { "AtoZ", "low", "200" };
		String[] arr10 = { "AtoZ", "high", "200" };
		
		String[] arr11 = { "Shirts", "popularity", "200" };
		String[] arr12 = { "Shirts", "new", "200" };
		String[] arr13 = { "Shirts", "discount", "200" };
		String[] arr14 = { "Shirts", "low", "200" };
		String[] arr15 = { "Shirts", "high", "200" };
		
		String[] arr16 = { "Tops", "popularity", "200" };
		String[] arr17 = { "Tops", "new", "200" };
		String[] arr18 = { "Tops", "discount", "200" };
		String[] arr19 = { "Tops", "low", "200" };
		String[] arr20 = { "Tops", "high", "200" };
		
		String[] arr21 = { "Heels", "popularity", "200" };
		String[] arr22 = { "Heels", "new", "200" };
		String[] arr23 = { "Heels", "discount", "200" };
		String[] arr24 = { "Heels", "low", "200" };
		String[] arr25 = { "Heels", "high", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15,
				arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithSortBy_verifyProductsDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { "Buy 1 Get 1", "popularity", "200" };
		String[] arr2 = { "Buy 1 Get 1", "new", "200" };
		String[] arr3 = { "Buy 1 Get 1", "discount", "200" };
		String[] arr4 = { "Buy 1 Get 1", "low", "200" };
		String[] arr5 = { "Buy 1 Get 1", "high", "200" };
		
		String[] arr6 = { "AtoZ", "popularity", "200" };
		String[] arr7 = { "AtoZ", "new", "200" };
		String[] arr8 = { "AtoZ", "discount", "200" };
		String[] arr9 = { "AtoZ", "low", "200" };
		String[] arr10 = { "AtoZ", "high", "200" };
		
		String[] arr11 = { "Shirts", "popularity", "200" };
		String[] arr12 = { "Shirts", "new", "200" };
		String[] arr13 = { "Shirts", "discount", "200" };
		String[] arr14 = { "Shirts", "low", "200" };
		String[] arr15 = { "Shirts", "high", "200" };
		
		String[] arr16 = { "Tops", "popularity", "200" };
		String[] arr17 = { "Tops", "new", "200" };
		String[] arr18 = { "Tops", "discount", "200" };
		String[] arr19 = { "Tops", "low", "200" };
		String[] arr20 = { "Tops", "high", "200" };
		
		String[] arr21 = { "Heels", "popularity", "200" };
		String[] arr22 = { "Heels", "new", "200" };
		String[] arr23 = { "Heels", "discount", "200" };
		String[] arr24 = { "Heels", "low", "200" };
		String[] arr25 = { "Heels", "high", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15,
				arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithSortBy_verifyIsProductsInSortedOrder(ITestContext iTestContext)
	{
		String[] arr1 = { "Tops", "low", "200" };
		String[] arr2 = { "Tops", "high", "200" };
		String[] arr3 = { "Heels", "low", "200" };
		String[] arr4 = { "Heels", "high", "200" };
		String[] arr5 = { "Street Style", "low", "200" };
		String[] arr6 = { "Street Style", "high", "200" };
		String[] arr7 = { "Featured Brand Stores", "low", "200" };
		String[] arr8 = { "Featured Brand Stores", "high", "200" };
		String[] arr9 = { "Bracelet", "low", "200" };
		String[] arr10 = { "Bracelet", "high", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	private JSONArray prepareSingleFilter(String action, String artifact, String value)
	{
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj1 = new JSONObject();
		jsonObj1.put("action", action);
		jsonObj1.put("key", artifact);
		jsonObj1.put("value", value);
		jsonArray.add(jsonObj1);
		
		return jsonArray;	
	}
	
	private JSONArray prepareDoubleFilters(String action1, String artifact1, String value1, String action2, String artifact2, String value2)
	{
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj1 = new JSONObject();
		jsonObj1.put("action", action1);
		jsonObj1.put("key", artifact1);
		jsonObj1.put("value", value1);
		jsonArray.add(jsonObj1);
		
		JSONObject jsonObj2 = new JSONObject();
		jsonObj2.put("action", action2);
		jsonObj2.put("key", artifact2);
		jsonObj2.put("value", value2);
		jsonArray.add(jsonObj2);
		
		return jsonArray;	
	}
	
	private JSONArray prepareAllFilters(String action1, String artifact1, String value1, String action2, String artifact2, String value2, 
			String action3, String artifact3, String value3, String action4, String artifact4, String value4, 
			String action5, String artifact5, String value5)
	{
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj1 = new JSONObject();
		jsonObj1.put("action", action1);
		jsonObj1.put("key", artifact1);
		jsonObj1.put("value", value1);
		jsonArray.add(jsonObj1);
		
		JSONObject jsonObj2 = new JSONObject();
		jsonObj2.put("action", action2);
		jsonObj2.put("key", artifact2);
		jsonObj2.put("value", value2);
		jsonArray.add(jsonObj2);
		
		JSONObject jsonObj3 = new JSONObject();
		jsonObj3.put("action", action3);
		jsonObj3.put("key", artifact3);
		jsonObj3.put("value", value3);
		jsonArray.add(jsonObj3);
		
		JSONObject jsonObj4 = new JSONObject();
		jsonObj4.put("action", action4);
		jsonObj4.put("key", artifact4);
		jsonObj4.put("value", value4);
		jsonArray.add(jsonObj4);
		
		JSONObject jsonObj5 = new JSONObject();
		jsonObj5.put("action", action5);
		jsonObj5.put("key", artifact5);
		jsonObj5.put("value", value5);
		jsonArray.add(jsonObj5);
		
		return jsonArray;	
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getFirstPageOfSearch_verifyResponseDataNodesUsingSchemaValidations(ITestContext iTestContext)
	{
		String[] arr1 = { "Football Store", "10", "true", "true", "200" };
		String[] arr2 = { "Casual Shirts", "5", "true", "true", "200" };
		String[] arr3 = { "T-shirts & Collared Tees", "2", "true", "true", "200" };
		String[] arr4 = { "Formal Shirts", "1", "true", "true", "200" };
		String[] arr5 = { "Ethnic Wear", "20", "true", "true", "200" };
		String[] arr6 = { "Jeans", "6", "true", "true", "200" };
		String[] arr7 = { "Pants / Trousers", "5", "true", "true", "200" };
		String[] arr8 = { "Shorts", "14", "true", "true", "200" };
		String[] arr9 = { "Inner & Sleepwear", "16", "true", "true", "200" };
		String[] arr10 = { "Tracksuits & Pants", "12", "true", "true", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getNextPageOfSearch_verifyResponseDataNodesUsingSchemaValidations(ITestContext iTestContext)
	{
		String[] arr1 = { "Men track pants", "10", "true", "true", "next", "30", "200"  };
		String[] arr2 = { "All Heels", "10", "true", "true", "next", "30", "200"  };
		String[] arr3 = { "Flats & Ballets", "10", "true", "true", "next", "30", "200"  };
		String[] arr4 = { "Slippers & Flip Flops", "10", "true", "true", "next", "30", "200"  };
		String[] arr5 = { "Casual Shoes", "10", "true", "true", "next", "30", "200"  };
		String[] arr6 = { "Sports Shoes", "10", "true", "true", "next", "30", "200"  };
		String[] arr7 = { "Boots", "10", "true", "true", "next", "30", "200"  };
		String[] arr8 = { "Socks", "10", "true", "true", "next", "30", "200"  };
		String[] arr9 = { "Western Wear", "10", "true", "true", "next", "30", "200"  };
		String[] arr10 = { "Dresses", "10", "true", "true", "next", "30", "200"  };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_getSortedMobileSearch_verifyResponseDataNodesUsingSchemaValidations(ITestContext iTestContext)
	{
		String[] arr1 = { "Clothing", "10", "true", "true", ":popularity", "200"  };
		String[] arr2 = { "Clothing", "10", "true", "true", ":discount", "200"  };
		String[] arr3 = { "Clothing", "10", "true", "true", ":new", "200"  };
		String[] arr4 = { "Clothing", "10", "true", "true", ":high", "200"  };
		String[] arr5 = { "Clothing", "10", "true", "true", ":low", "200"  };
		
		String[] arr6 = { "Footwear", "10", "true", "true", ":popularity", "200"  };
		String[] arr7 = { "Footwear", "10", "true", "true", ":discount", "200"  };
		String[] arr8 = { "Footwear", "10", "true", "true", ":new", "200"  };
		String[] arr9 = { "Footwear", "10", "true", "true", ":high", "200"  };
		String[] arr10 = { "Footwear", "10", "true", "true", ":low", "200"  };
		
		String[] arr11 = { "Accessories", "10", "true", "true", ":popularity", "200"  };
		String[] arr12 = { "Accessories", "10", "true", "true", ":discount", "200"  };
		String[] arr13 = { "Accessories", "10", "true", "true", ":new", "200"  };
		String[] arr14 = { "Accessories", "10", "true", "true", ":high", "200"  };
		String[] arr15 = { "Accessories", "10", "true", "true", ":low", "200"  };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchWithPresetFilters_verifyResponseDataNodesUsingSchemaValidations(ITestContext iTestContext)
	{
		// Add gender as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add Brand as filter
		String[] arr5 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Nike").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Pepe Jeans").toJSONString(), "200" };
		String[] arr7 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Lee").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr9 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200"  };
		String[] arr10 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200"  };
		String[] arr11 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200"  };
		String[] arr12 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200"  };
		
		// Add color as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200"  };
		String[] arr14 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200"  };
		String[] arr15 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200"  };
		String[] arr16 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200"  };
		
		// Add discount as filter
		String[] arr17 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200"  };
		String[] arr18 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200"  };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplySingleFilter_verifyResponseDataNodesUsingSchemaValidations(ITestContext iTestContext)
	{
		// Add gender as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "men").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "women").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "boys").toJSONString(), "200" };
		String[] arr4 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":gender", "girls").toJSONString(), "200" };
		
		// Add Brand as filter
		String[] arr5 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Nike").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Pepe Jeans").toJSONString(), "200" };
		String[] arr7 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":brand", "Lee").toJSONString(), "200" };
		
		// Add size as filter
		String[] arr9 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "0-1Y").toJSONString(), "200" };
		String[] arr10 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "26/30").toJSONString(), "200" };
		String[] arr11 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "40").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":size", "30/L").toJSONString(), "200" };
		
		// Add color as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Green").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Blue").toJSONString(), "200" };
		String[] arr15 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Navy").toJSONString(), "200" };
		String[] arr16 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":color", "Red").toJSONString(), "200" };
		
		// Add discount as filter
		String[] arr17 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "20.0").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareSingleFilter("add", ":discount", "40.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyDoubleFilters_verifyResponseDataNodesUsingSchemaValidations(ITestContext iTestContext)
	{
		// Add two Genders as filter
		String[] arr1 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "women").toJSONString(), "200" };
		String[] arr2 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "boys").toJSONString(), "200" };
		String[] arr3 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":gender", "girls").toJSONString(), "200" };

		// Add two brands as filter
		String[] arr4 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "Pepe Jeans", "add", ":brand", "Levis").toJSONString(), "200" };
		String[] arr5 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "Lee", "add", ":brand", "LOCOMOTIVE").toJSONString(), "200" };
		String[] arr6 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":brand", "HRX Jeans", "add", ":brand","Flying Machine").toJSONString(), "200" };

		// Add two Sizes as filter
		String[] arr7 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "0-1Y", "add", ":size", "1-3M").toJSONString(), "200" };
		String[] arr8 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "24", "add", ":size", "25/32").toJSONString(), "200" };
		String[] arr9 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":size", "28/M", "add", ":size", "28/L").toJSONString(), "200" };

		// Add two colors as filter
		String[] arr10 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Red", "add", ":color", "Navy").toJSONString(), "200"  };
		String[] arr11 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Yellow", "add", ":color", "Pink").toJSONString(), "200" };
		String[] arr12 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":color", "Green", "add", ":color", "White").toJSONString(), "200" };

		// Add two discounts as filter
		String[] arr13 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":discount", "20.0", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr14 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":discount", "10.0", "add", ":discount", "20.0").toJSONString(), "200" };

		// Gender and brand as filter
		String[] arr15 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":brand", "Levis").toJSONString(), "200"  };
		String[] arr16 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":brand", "Lee").toJSONString(), "200"  };
		String[] arr17 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":brand", "Nautica").toJSONString(), "200" };
		String[] arr18 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":brand", "Nautica").toJSONString(), "200" };

		// Gender and size as filter
		String[] arr19 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":size", "30").toJSONString(), "200" };
		String[] arr20 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":size", "27/32").toJSONString(), "200" };
		String[] arr21 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":size", "8-9Y").toJSONString(), "200" };
		String[] arr22 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":size", "6-12M").toJSONString(), "200" };

		// Gender and color
		String[] arr23 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr24 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":color", "Red").toJSONString(), "200" };
		String[] arr25 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":color", "Blue").toJSONString(), "200" };
		String[] arr26 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":color", "Blue").toJSONString(), "200" };

		// Gender and discount
		String[] arr27 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "men", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr28 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "women", "add", ":discount", "10.0").toJSONString(), "200" };
		String[] arr29 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "boys", "add", ":discount", "0.0").toJSONString(), "200" };
		String[] arr30 = { "clothing", "10", "true", "true", prepareDoubleFilters("add", ":gender", "girls", "add", ":discount", "10.0").toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, 
				arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_searchAndApplyAllFilters_verifyResponseDataNodesUsingSchemaValidations(ITestContext iTestContext)
	{
		String[] arr1 = { "clothing", "10", "true", "true", prepareAllFilters("add", ":gender", "men", "add", ":brand", "Adidas", "add", ":size", "30", 
				"add", ":color", "Red", "add", ":discount", "0.0" ).toJSONString(), "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQuery_verifyResponseDataNodesUsingSchemaValidations(ITestContext iTestContext)
	{
		String[] arr1 = { "Sandals & Clogs", "200" };
		String[] arr2 = { "Girls Apparel", "200" };
		String[] arr3 = { "Frocks", "200" };
		String[] arr4 = { "Tops & T-shirts", "200" };
		String[] arr5 = { "Bottomwear", "200" };
		String[] arr6 = { "Sweaters, Sweatshirts, Jackets", "200" };
		String[] arr7 = { "Girls Footwear", "200" };
		String[] arr8 = { "Elle Kids", "200" };
		String[] arr9 = { "Happy Face", "200" };
		String[] arr10 = { "Gini & Jony", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithOneFilter_verifyResponseDataNodesUsingSchemaValidations(ITestContext iTestContext)
	{
		String[] arr1 = { "Nike", "categories", "Sports&#32;Shoes", "200" };
		String[] arr2 = { "Dress", "brands", "Anouk", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithTwoFilters_verifyResponseDataNodesUsingSchemaValidations(ITestContext iTestContext)
	{
		String[] arr1 = { "Dress", "brands", "Anouk", "colour", "blue", "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithAllFilters_verifyResponseDataNodesUsingSchemaValidations(ITestContext iTestContext)
	{
		String[] arr1 = { "Jeans", "brands", "Lee", "colour", "blue", "discount", "20.0", "gender", "men", "size", "30", "200" };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithSortBy_verifyResponseDataNodesUsingSchemaValidations(ITestContext iTestContext)
	{
		String[] arr1 = { "Buy 1 Get 1", "popularity", "200" };
		String[] arr2 = { "Buy 1 Get 1", "new", "200" };
		String[] arr3 = { "Buy 1 Get 1", "discount", "200" };
		String[] arr4 = { "Buy 1 Get 1", "low", "200" };
		String[] arr5 = { "Buy 1 Get 1", "high", "200" };
		
		String[] arr6 = { "AtoZ", "popularity", "200" };
		String[] arr7 = { "AtoZ", "new", "200" };
		String[] arr8 = { "AtoZ", "discount", "200" };
		String[] arr9 = { "AtoZ", "low", "200" };
		String[] arr10 = { "AtoZ", "high", "200" };
		
		String[] arr11 = { "Shirts", "popularity", "200" };
		String[] arr12 = { "Shirts", "new", "200" };
		String[] arr13 = { "Shirts", "discount", "200" };
		String[] arr14 = { "Shirts", "low", "200" };
		String[] arr15 = { "Shirts", "high", "200" };
		
		String[] arr16 = { "Tops", "popularity", "200" };
		String[] arr17 = { "Tops", "new", "200" };
		String[] arr18 = { "Tops", "discount", "200" };
		String[] arr19 = { "Tops", "low", "200" };
		String[] arr20 = { "Tops", "high", "200" };
		
		String[] arr21 = { "Heels", "popularity", "200" };
		String[] arr22 = { "Heels", "new", "200" };
		String[] arr23 = { "Heels", "discount", "200" };
		String[] arr24 = { "Heels", "low", "200" };
		String[] arr25 = { "Heels", "high", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15,
				arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
}
