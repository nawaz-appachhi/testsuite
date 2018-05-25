package com.myntra.apiTests.portalservices.pricingpromotionservices.pricingservices;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import com.myntra.apiTests.dataproviders.PricingPolicyApiTestsDP;
import com.myntra.apiTests.portalservices.PricingPolicyApiHelper.PricingPolicyApiServiceHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Myntra;
	
	public class PricingPolicyApiTests extends PricingPolicyApiTestsDP
	{
		static Initialize init = new Initialize("/Data/configuration");
		static APIUtilities apiUtil = new APIUtilities();
		static Logger log = Logger.getLogger(PricingPolicyApiTests.class);
		static PricingPolicyApiServiceHelper PPHelper = new PricingPolicyApiServiceHelper();
		static PricingTDExclusionFlow TDEHelper= new PricingTDExclusionFlow();
		WebTarget target;
		ClientConfig config;
		Client client;
		Invocation.Builder invBuilder;
		
		public void printAndLog(String Message, String Value)
		{
			System.out.println(Message+Value);
			log.info(Message+Value);
		}
		
		public void printAndLog(String Message, int Value)
		{
			System.out.println(Message+Value);
			log.info(Message+Value);
		}
		
		public void printAndLog(String Message, String[] Array)
		{
			System.out.println(Message);
			log.info(Message);
			for(int i=0;i<Array.length;i++)
			{
				System.out.println(Array[i]);
				log.info(Array[i]);
			}
		}
		
		public void printAndLog(String message, double value) 
		{
			System.out.println(message+value);
			log.info(message+value);
		}

		public void  printAndLog(String message, long value)
		{
			System.out.println(message+value);
			log.info(message+value);
		}
		
		
		//Create Internal Style Policy 
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "createInternalPolicy_IntegrationTestFlow")
		public void PricingPolicy_createOUTRIGHTInternalPolicy_And_Validate(String ScenarioID, String PageNumber, String TotalItemCount, String Operator,String GM_Adj,  String GMMatrixDiscount, String FC_Adj,String FCMatrixDiscount, String STR_Adj, String STR_Discount,String MRP_Adj, String MRP_Discount, String BrokenSKU_Adj, String BrokenSKU_Discount,String OldSeason_Adj, String OldSeason_Discount,String BrandFocus_Adj, String BrandFocus_Discount, String MerchandiseType, String TDExclusionCount, String TDRangeCount, String CollectionInFocusCount,  String CIF_Boost, String TDRange_Adj, String MinimumRGM) throws InterruptedException, JSONException 
		{
			PPHelper.deleteTdRangeIds("0", "400");
			PPHelper.deleteTdExclusionIds("0","300");
			PPHelper.deleteCollectionInFocusIds("0","300");
			PPHelper.getDayType();
			MyntraService service1 = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICY,APINAME.CREATEINTERNALPOLICYFG, init.Configurations, new String[] { },PayloadType.JSON,PayloadType.JSON);
	    	RequestGenerator requestGenerator = new RequestGenerator(service1);
	    	System.out.println("Base URL : "+service1.URL);
	    	System.out.println("Payload : "+service1.Payload);
	    	String response1 = requestGenerator.respvalidate.returnresponseasstring(); 
	    	System.out.println("Create Internal policy fg ----->> "+response1);
	    	
			LinkedHashMap<String, Map<String,String>> StyleInfoMap = new LinkedHashMap<String, Map<String,String>>();
			LinkedHashMap<String, Map<String,String>> DiscountInfoMap = new LinkedHashMap<String, Map<String,String>>();
			RequestGenerator request;
			String response;
			String BPE_Size = "100000";
			int BPE_Size_int = Integer.parseInt(BPE_Size);
			int TotalStyleCount = Integer.parseInt(TotalItemCount);			
			printAndLog("Total Style Count used	:	",TotalStyleCount);
			PPHelper.printScenarioDetails( ScenarioID, PageNumber, TotalItemCount,  Operator, GMMatrixDiscount,  FCMatrixDiscount,  MRP_Adj,  MRP_Discount,  BrokenSKU_Adj,  BrokenSKU_Discount, OldSeason_Adj,  OldSeason_Discount, BrandFocus_Adj,  BrandFocus_Discount);
			String EngineInstanceID="";
			
			//Get Style Information from Analytics Service
			request = PPHelper.invokeGetStyleDataService(PageNumber, TotalItemCount, MerchandiseType); //Enter Total Number of Items as Parameter
			response = request.respvalidate.returnresponseasstring();			
			printAndLog("Analytics Service Response : ",response);			
			StyleInfoMap = PPHelper.generateStyleMap(response);
			System.out.println("Generated Style Map from Analytics Response	:	"+StyleInfoMap);
			AssertJUnit.assertEquals("Error getting style data from Analytics", 200,request.response.getStatus());
			
			//Create Internal Policy based on the style information and scenario requirements
			RequestGenerator InternalPolicyCreateRequest = PPHelper.invokeCreateInternalPolicyService(response, TotalItemCount, Operator, GM_Adj, GMMatrixDiscount, FC_Adj, FCMatrixDiscount, STR_Adj, STR_Discount, MRP_Adj, MRP_Discount, BrokenSKU_Adj, BrokenSKU_Discount, OldSeason_Adj, OldSeason_Discount, BrandFocus_Adj, BrandFocus_Discount, MerchandiseType,MinimumRGM);
			String 	InternalPolicyCreateResponse = InternalPolicyCreateRequest.respvalidate.returnresponseasstring();
			EngineInstanceID =JsonPath.read(InternalPolicyCreateResponse,"$.instanceid" );
			printAndLog("Create Internal Policy Service Response : ",InternalPolicyCreateResponse);
			AssertJUnit.assertEquals("Error Creating Internal Policy", 200,InternalPolicyCreateRequest.response.getStatus());
					
			//Extract Style ID and MRP information from Response to create TD Exclusions		
			String StyleIdArray[] = PPHelper.getStyleIdArray(response, TotalStyleCount);
			String StyleMRPArray[] = PPHelper.getValueArrayFromResponse(response, TotalStyleCount, "maximumRetailPrice");
			printAndLog("Extracted Style IDS from Response : ",StyleIdArray);
			printAndLog("Extracted MRP from Response : ",StyleMRPArray);
			PPHelper.mappingStylesId(StyleIdArray);

			
			//Create TD Exclusion for few style ids from the analytics response
			String TD_Excluded_Styles[] = new String[Integer.parseInt(TDExclusionCount)];			
			TD_Excluded_Styles = PPHelper.getExcludedIds(StyleIdArray, StyleMRPArray, Integer.parseInt(TDExclusionCount));			
			String TDE_ReducedStyles[] =  PPHelper.getReducedArray(StyleIdArray, TD_Excluded_Styles);			
			printAndLog("Style IDS used for TD Exclusion	:	",TD_Excluded_Styles);
			printAndLog("Style IDs that are not used in TD Exclusion : ",TDE_ReducedStyles);

			//	sddsssdfsdsdfsdfsfd		
			//Create Collection in Focus
			String[] CIF_Response = PPHelper.createCollectionInFocus(TDE_ReducedStyles,Integer.parseInt(CollectionInFocusCount),CIF_Boost);		
			String[] TDE_CIF_ReducedArray = PPHelper.getReducedArray(TDE_ReducedStyles, CIF_Response);
			printAndLog("Style IDS used for Collection In Focus : ",CIF_Response);
			printAndLog("Style IDs that are not used in TD Exclusion & Collection In Focus : ",TDE_CIF_ReducedArray);

			for(int i=0;i<StyleIdArray.length;i++)
			{
				double BuyingMargin=Double.parseDouble(JsonPath.read(response, "$.styleList["+i+"].buyingMargin").toString());
				//printAndLog("Buying Margin : ",BuyingMargin);
				
				double MRP = Double.parseDouble(JsonPath.read(response, "$.styleList["+i+"].maximumRetailPrice").toString());
				//printAndLog("MRP : ",MRP);
								
				double brandInFocusBoost = 0.0;
				//printAndLog("Brand In Focus Boost : ",brandInFocusBoost);
				
				
				double baseDiscount = PPHelper.getBaseDiscount(Operator, GMMatrixDiscount, FCMatrixDiscount, BuyingMargin);
				//printAndLog("Base Discount : ",baseDiscount);

				
				double purchasePrice = PPHelper.getPurchasePrice(MRP, BuyingMargin);
				//printAndLog("Purchase Price : ",purchasePrice);
	

				double RGM = Double.parseDouble(MinimumRGM);					
				double OldSeasonBoost = PPHelper.getOldSeasonBoost(JsonPath.read(response, "$.styleList["+i+"].season").toString(),OldSeason_Discount);				
				//printAndLog("OldSeasonBoost : ",OldSeasonBoost);

				
				double CollectionInFocusBoost = (ArrayUtils.contains(CIF_Response,StyleIdArray[i]))?Double.parseDouble(CIF_Boost):0;
				//printAndLog("CollectionInFocusBoost : ",CollectionInFocusBoost);

				
				/*if(!FocussedBrandsResponse.isEmpty())
				{
					if(FocussedBrandsResponse.contains(JsonPath.read(response, StyleIdArray[i]).toString()))
					{
						brandInFocusBoost = Double.parseDouble(BrandFocus_Adj);
					}
				}*/
				
				double additionalDiscount = OldSeasonBoost+brandInFocusBoost+(((MRP_Adj.equals("Y"))?Double.parseDouble(MRP_Discount):0)+((BrokenSKU_Adj.equals("Y"))?Double.parseDouble(BrokenSKU_Discount):0));
				double MIN_RGM = Double.parseDouble(MinimumRGM);
				double TD = baseDiscount+additionalDiscount; //Trade Discount
				
				
				double RGM_CALC =PPHelper.getRGM(TD, purchasePrice, MRP);

				//System.out.println("Calculate RGM	:	"+RGM_CALC);
	
				while(RGM_CALC<MIN_RGM)
				{
					//printAndLog("Current RGM : ",RGM_CALC);
					//printAndLog("Current Discount : ",TD);

					TD--;
					RGM_CALC = PPHelper.getRGM(TD, purchasePrice, MRP);
					
					//printAndLog("Calculated RGM : ",RGM_CALC);
					//printAndLog("Calculated Discount : ",TD);
				}
				
				if(TD<0)
				{
					TD=0;
				}
				
				
				
				DecimalFormat df = new DecimalFormat("##.##");
				double RGM_NormalizedDiscount =Double.parseDouble(df.format(TD));
				CollectionInFocusBoost=Double.parseDouble(df.format(CollectionInFocusBoost));
				
				double finalDiscount = RGM_NormalizedDiscount + CollectionInFocusBoost;
				
				LinkedHashMap<String, String> DiscountSubMap = new LinkedHashMap<String, String>();	
				
				DiscountSubMap.put("BuyingMargin", Double.toString(BuyingMargin));
				DiscountSubMap.put("MRP", Double.toString(MRP));
				DiscountSubMap.put("brandInFocusBoost", Double.toString(brandInFocusBoost));
				DiscountSubMap.put("baseDiscount", Double.toString(baseDiscount));
				DiscountSubMap.put("purchasePrice", Double.toString(purchasePrice));
				DiscountSubMap.put("RGM", Double.toString(RGM));
				DiscountSubMap.put("OldSeasonBoost", Double.toString(OldSeasonBoost));
				DiscountSubMap.put("RGM_NormalizedDiscount", Double.toString(RGM_NormalizedDiscount));
				DiscountSubMap.put("CollectionInFocusBoost",Double.toString(CollectionInFocusBoost));
				DiscountSubMap.put("FinalTradeDiscount",Double.toString(finalDiscount));
				System.out.println("Calculated Discount Info Map : "+DiscountSubMap);
				DiscountInfoMap.put(StyleIdArray[i], DiscountSubMap);
							
			}
			
			System.out.println("\nFinal Discount Maps : "+DiscountInfoMap);
			
			for(int i=0;i<StyleIdArray.length;i++)
			{
				String StyleID = StyleIdArray[i];
				String Discount = DiscountInfoMap.get(StyleIdArray[i]).get("FinalTradeDiscount");
				System.out.println("Calculated Discount for Stlye ID "+StyleID+" : "+Discount);
			}
			
			//Calculating TD Ranges
			double TD_Min = 10;
			double TD_Max = 11;
			
			/*if(TDRange_Adj.equals("Y")) //Does the scenario requires TD Range Adjustment for Testing
			{
				double MaxDiscount= PPHelper.findMaxValue(DiscountInfoMap, "FinalTradeDiscount", StyleIdArray);
				double MinDiscount = PPHelper.findMinValue(DiscountInfoMap, "FinalTradeDiscount", StyleIdArray);
				TD_Min = MinDiscount + (MaxDiscount/2);
				TD_Max = MaxDiscount + (MaxDiscount/2);
				printAndLog("Minimum TD Range : ",TD_Min);
				printAndLog("Maximum TD Range : ",TD_Max);	
			}*/
			
			
			//Create TD Range and get style Ids used in the TD Range
			String[] TDRangeStyles = PPHelper.createAndActivateTDRange(TD_Min, TD_Max, MerchandiseType,TDE_CIF_ReducedArray,2);
			
			String[] TDE_CIF_TDR_ReducedStyles = PPHelper.getReducedArray(TDE_CIF_ReducedArray, TDRangeStyles);
			printAndLog("FINAL ARRAY___ >>> ", TDE_CIF_TDR_ReducedStyles);
			
			printAndLog("FINAL TD RANGE ARRAY___ >>> ", TDRangeStyles);
			
			printAndLog("FINAL TD Colelction ARRAY___ >>> ", CIF_Response);
			printAndLog("INtail all  ARRAY___ >>> ", StyleIdArray);




			
			
			
			RequestGenerator StartEngineRequest = PPHelper.startPricingEngine();
            String EngineStartResponse = StartEngineRequest.respvalidate.returnresponseasstring();
            printAndLog("Pricing Engine Start Response : ", EngineStartResponse);
            printAndLog("Pricing Engine Start Request Status : ",StartEngineRequest.response.getStatus());
            AssertJUnit.assertEquals("Unable to Start Pricing Engine - Aborting Tests", 200,StartEngineRequest.response.getStatus());
		    EngineInstanceID=JsonPath.read(EngineStartResponse,"$.instanceid").toString();
		    printAndLog("Engine Instance ID : ",EngineInstanceID);

			//LET's WAIT FOR 1 MINUTES TO FACILITATE STYLE DISCOUNT REINDEXING	
			TimeUnit.MINUTES.sleep(1);
			 
			//Internal Policy Verifications
			
			/*for(int i=0;i<TDE_ReducedStyles.length;i++)
			{
				double CalcDiscount =Double.parseDouble(DiscountInfoMap.get(TDE_ReducedStyles[i]).get("FinalTradeDiscount"));
				double AppliedDiscount;
				if(CalcDiscount<TD_Min)
				{
					AppliedDiscount = TD_Min;
				}
				else if(CalcDiscount>TD_Max)
				{
					AppliedDiscount = TD_Max;
				}
				else
				{
					AppliedDiscount = CalcDiscount;
				}
				
				String Discount = Double.toString(AppliedDiscount);
								
				RequestGenerator StyleRequest = PPHelper.getStyleDiscountData(TDE_ReducedStyles[i]);
				String StyleDiscountResponse = StyleRequest.respvalidate.returnresponseasstring();
				printAndLog("Style Discount Details for the Current Style", StyleDiscountResponse);
				
				//AssertJUnit.assertEquals("Base Pricing Engine Discount Not Applied", Discount,JsonPath.read(StyleDiscountResponse,"$."+TDE_ReducedStyles[i]+".tradeDiscount[0].discountPercent"));
			}
			
			// For TD Excluded Style IDs
			
			for(int i=0;i<TD_Excluded_Styles.length;i++)
			{
				String AppliedDiscount = StyleInfoMap.get(TD_Excluded_Styles[i]).get("currentTradeDiscountPercentage");
				RequestGenerator StyleRequest = PPHelper.getStyleDiscountData(TD_Excluded_Styles[i]);
				String StyleDiscountResponse = StyleRequest.respvalidate.returnresponseasstring();
				printAndLog("Style Discount Details for the Current Style", StyleDiscountResponse);
				
				//AssertJUnit.assertEquals("Base Pricing Engine Discount Not Applied", AppliedDiscount,JsonPath.read(StyleDiscountResponse,"$."+TD_Excluded_Styles[i]+".tradeDiscount[0].discountPercent"));
			}
			*/
			
			
			
			
			//Validate Base Pricing Engine Output
			RequestGenerator BPE_Request = PPHelper.getBasePricingEngineOutput(EngineInstanceID, "0", BPE_Size);
			String BPE_Response = BPE_Request.respvalidate.returnresponseasstring();
			printAndLog("Response from Base Pricing Engine : ",BPE_Response);
			JSONArray discountArray = new JSONArray(BPE_Response);
			
			
			System.out.println("Discount Array Size : " +discountArray.length());
			HashMap<String, String> BPE_DiscountMap = new HashMap<String,String>();
			for(int i=0;i<discountArray.length();i++)
			{
				JSONObject record = discountArray.getJSONObject(i);
				String styleID = Integer.toString(record.getInt("styleId"));
				String discount = record.getString("payload");
				BPE_DiscountMap.put(styleID, discount);
			}
			
			System.out.println("RESPONSE MAP : \n "+BPE_DiscountMap);		
			
			if(BPE_DiscountMap.isEmpty())
			{
				AssertJUnit.assertEquals("Style IDS are not available in the BPE OUTPUT",1, 2);
			}
			
			
			//STYLE IDs that doesn't come under TD Exclusion, TD Range and Collection In Focus
			for(int i=0;i<TDE_CIF_TDR_ReducedStyles.length;i++)
			{
				String AppliedDiscount = DiscountInfoMap.get(TDE_CIF_TDR_ReducedStyles[i]).get("FinalTradeDiscount");
				String BPE_Discount = BPE_DiscountMap.get(TDE_CIF_TDR_ReducedStyles[i]);
				printAndLog("FINAL ARRAY WITH OUTPUT", TDE_CIF_TDR_ReducedStyles[i]);
				boolean disc = false;
				if(Double.parseDouble(BPE_Discount)-3 < Double.parseDouble(AppliedDiscount) && Double.parseDouble(AppliedDiscount)<Double.parseDouble(BPE_Discount)+3)
					disc = true;
				System.out.println("Expected Discount : "+AppliedDiscount);
				AssertJUnit.assertEquals("BPE valid Discounts are not valid", true,disc);

			}
			
			
			System.out.println("Printing Style info for Final Array:\n ");
			for(int i=0;i<TDE_CIF_TDR_ReducedStyles.length;i++)
			{
				System.out.println("Style ID : "+TDE_CIF_TDR_ReducedStyles[i]);
				System.out.println("Discount : "+DiscountInfoMap.get(TDE_CIF_TDR_ReducedStyles[i]).get("FinalTradeDiscount"));
			}
			
			
			//Stlye IDs that come under TD Range
			for(int i=0; i<TDRangeStyles.length;i++)
			{
				double AppliedDiscount = Double.parseDouble(DiscountInfoMap.get(TDRangeStyles[i]).get("FinalTradeDiscount"));
				String BPE_Discount = BPE_DiscountMap.get(TDRangeStyles[i]);
				printAndLog("FINAL TD range ARRAY WITH OUTPUT", TDRangeStyles[i]);


				if (AppliedDiscount < TD_Min)
				{
					AppliedDiscount = TD_Min;
				}
				
				if(AppliedDiscount > TD_Max)
				{
					AppliedDiscount = TD_Max;
				}
				AssertJUnit.assertEquals("BPE TD ranegDiscounts are  not valid", Double.toString(AppliedDiscount),BPE_Discount);

			}
			
		//StyleIds that come under Collection in Focus
//			for(int i=0; i<CIF_Response.length;i++)
//			{
//				double AppliedDiscount = Double.parseDouble(DiscountInfoMap.get(CIF_Response[i]).get("FinalTradeDiscount"));
//				double BPE_Discount = Double.parseDouble(BPE_DiscountMap.get(CIF_Response[i]));
//				double ExpectedDiscount = AppliedDiscount-Double.parseDouble(CIF_Boost);
//				printAndLog("FINAL  colelction  ARRAY WITH OUTPUT", CIF_Response[i]);
//				boolean disc = false;
//				if(BPE_Discount-3 < AppliedDiscount-Double.parseDouble(CIF_Boost) && AppliedDiscount-Double.parseDouble(CIF_Boost)<BPE_Discount+3)
//					disc = true;
//			
//
//				AssertJUnit.assertEquals("BPE Collection Discounts are not valid", true,disc);
//
//			}


		
		}
		
		
		

		
		
		}





