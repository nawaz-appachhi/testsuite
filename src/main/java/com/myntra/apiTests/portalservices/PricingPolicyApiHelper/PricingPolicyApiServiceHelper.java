package com.myntra.apiTests.portalservices.PricingPolicyApiHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

import edu.emory.mathcs.backport.java.util.Arrays;
import net.minidev.json.JSONArray;
public class PricingPolicyApiServiceHelper extends CommonUtils

{		static Initialize init = new Initialize("/Data/configuration");
		static Logger log = Logger.getLogger(PricingPolicyApiServiceHelper.class);
		APIUtilities apiUtil=new APIUtilities();
		private static final String seasons[] = { "FW", "FW", "SS", "SS", "SS", "SS", "SS", "SS", "FW", "FW", "FW", "FW"};	
		public long currentTime = System.currentTimeMillis();
		String startTime = String.valueOf(currentTime);
		String endTime = String.valueOf(currentTime+4000000);
		static PricingPolicyApiServiceHelper PPHelper = new PricingPolicyApiServiceHelper();
		
		//General Utilities
		private String randomize(String Data)
		{
			String RandomizedData = Data;
			
			int RangeStart = 10000;
			int RangeEnd = 99999;
			Random obj = new Random();
			RandomizedData = RandomizedData+((obj.nextInt(RangeEnd - RangeStart)+RangeStart));
			
			return RandomizedData;	
		}
		
		private String newLowRange(String HighRange, String LowRange)
		{
			int HRange = Integer.parseInt(HighRange);
			int LRange = Integer.parseInt(LowRange);
			int newLRange = LRange + ((HRange-LRange)/2);
			return Integer.toString(newLRange);
		}
		
		private String newHighRange(String HighRange, String LowRange)
		{
			int HRange = Integer.parseInt(HighRange);
			int LRange = Integer.parseInt(LowRange);
			int newHRange = HRange + ((HRange-LRange)/2);
			return Integer.toString(newHRange);
		}
		
		public String getDiscountCriteria(String AdjustmentApplyCriteria, String DiscountCriteria)
		{
			if(AdjustmentApplyCriteria.equals("Y"))
				return DiscountCriteria;
			else
				return randomize(DiscountCriteria);
		}
		
		public String getDiscountValue(String AdjustmentApplyCriteria, String Discount )
		{
			if(AdjustmentApplyCriteria.equals("Y"))
				return Discount;
			else
				return "0";
		}
		
		public void printAndLog(String Message, String Value)
		{
			System.out.println(Message+Value);
			log.info(Message+Value);
		}
		
		public static String[] splitString(String sentence,String splitstr)
		{
			return sentence.split(splitstr);
		}
			
		public void printAndLog(String Message, int Value)
		{
			System.out.println(Message+Value);
			log.info(Message+Value);
		}
		
		public int findMaxValue(LinkedHashMap<String, Map<String,String>> data, String Key, String[] StyleIds) throws InterruptedException
		{
			int maxValue=0;
			int compareValue=0;
			for(int x=0; x<StyleIds.length;x++)
			{
				if(!data.get(StyleIds[x]).get(Key).toString().equals("null"))
				compareValue=(int) Math.floor(Float.parseFloat(data.get(StyleIds[x]).get(Key)));
				
				if(compareValue > maxValue)
				{
					maxValue = compareValue;
				}
			}
			return maxValue;
		}
		
		public int findMinValue(LinkedHashMap<String, Map<String,String>> data, String Key, String[] StyleIds) throws InterruptedException
		{
			int minValue =0;
			int compareValue=0;
			for(int x=0; x<StyleIds.length;x++)
			{
				if(!data.get(StyleIds[x]).get(Key).toString().equals("null"))
				compareValue=(int) Math.floor(Float.parseFloat(data.get(StyleIds[x]).get(Key)));
				
				if(compareValue <= minValue)
				{
					minValue = compareValue;
				}
			}
			return minValue;
		}
		
		
		public long getEpochTime(Date date)
		{
			long time =0;
			return time;
		}
		
		public long getStartDate()
		{
			long epochStartDate = System.currentTimeMillis();
			return epochStartDate;
		}
		
		public long getEndDate(long epochStartDate)
		{
			long epochEndDate = epochStartDate + 3600000; //Adding One Hour to the End Date
			return epochEndDate;
		}
		public LinkedHashMap<String, Map<String,String>> generateStyleMap(String response)
		{
			LinkedHashMap<String,String> innerStyleMap = new LinkedHashMap<String, String>();
			LinkedHashMap<String, Map<String,String>> finalStyleMap =new LinkedHashMap<String, Map<String,String>>();
			String splitString = null;
					
			for(String str: splitString(response, "},"))
			{
				innerStyleMap=new LinkedHashMap<String, String>();	
				for(String st:splitString(str.substring(str.lastIndexOf("{")).replace("{", "").replace("\"", ""),","))
				{
					innerStyleMap.put(splitString(st, ":")[0].trim(), splitString(st, ":")[1].replace("}", "").replace("]", "").trim());
					if(splitString(st, ":")[0].trim().equals("styleId"))
					{
						splitString=splitString(st, ":")[1];
					}
				}
				finalStyleMap.put(splitString.trim(), innerStyleMap);
			}
			return finalStyleMap;
		}
		
		public double getFinalDiscountOnItem(float calcAdjustments)
		{
					
			double finalDiscount=0;
			return finalDiscount;
			
		}
			
		public void printScenarioDetails(String ScenarioID, String PageNumber, String TotalItemCount, String Operator,String GMMatrixDiscount, String FCMatrixDiscount, String MRP_Adj, String MRP_Discount, String BrokenSKU_Adj, String BrokenSKU_Discount,String OldSeason_Adj, String OldSeason_Discount,String BrandFocus_Adj, String BrandFocus_Discount)
		{
			printAndLog("\nPrinting Scenario Info","");
			printAndLog("Scenario ID : ",ScenarioID);
			printAndLog("Page Number of Analytics Result : ", PageNumber);
			printAndLog("Operator used to select between GM and FC Matrix : ", Operator);
			printAndLog("GM-MatrixDiscount : ",GMMatrixDiscount);
			printAndLog("FC-MatrixDiscount : ",FCMatrixDiscount);
			printAndLog("Apply MRP Adjustment ? ",MRP_Adj);
			printAndLog("MRP Adjustment Discount : ",MRP_Discount);
			printAndLog("Apply Broken SKU Adjustment ? ",BrokenSKU_Adj);
			printAndLog("Broken SKU Adjustment Discount : ",BrokenSKU_Discount);
			printAndLog("Apply Old Season Adjustment ? ",OldSeason_Adj);
			printAndLog("Old Season Adjustment  : ",OldSeason_Discount);
			printAndLog("Apply Brand In Focus Adjustment ? ",BrandFocus_Adj);
			printAndLog("Brand In Focus Adjustment Discount : ",BrandFocus_Discount);
		}	
		
		public String[] getStyleIdArray(String response, int styleCount)
		{
			String StyleIds[] = new String[styleCount];
			
			for(int i=0;i<styleCount;i++)
			{
				StyleIds[i] = JsonPath.read(response, "$.styleList["+i+"].styleId").toString();
			}
			return StyleIds;
			
		}
		
		public String[] getValueArrayFromResponse(String response, int styleCount, String key)
		{
			String StyleIds[] = new String[styleCount];
			
			for(int i=0;i<styleCount;i++)
			{
				StyleIds[i] = JsonPath.read(response, "$.styleList["+i+"]."+key).toString();
			}
			return StyleIds;
			
		}
		
		
		// Step 1: GET BASE DISCOUNT
		// Parameters: Operator, GM_Adj, FC_Adj, BuyingMargin
		
		public double getBaseDiscount(String operator, String GM_Adj, String FC_Adj, double BuyingMargin)
		{
			double BaseDiscount = 0;
			
			if(operator.equals("max"))
			{
				BaseDiscount = Math.max(BuyingMargin - Float.parseFloat(GM_Adj), Float.parseFloat(FC_Adj));
			}
			else 
			{
				BaseDiscount = Math.min(BuyingMargin - Float.parseFloat(GM_Adj), Float.parseFloat(FC_Adj));
			}
					
			return BaseDiscount;
				
		}
		
		//Step 2: GET SEASON ADJUSTMENTS
		//Parameters: Season Code, Season Adjustment
		public float getOldSeasonBoost(String seasonCode, String SeasonAdjustMent)
		{
			float OldSeasonBoost = 0;
			Date Today = new Date();
			Calendar CurrentCal = Calendar.getInstance();
			CurrentCal.setTime(Today);
			int CurrentMonth = (CurrentCal.get(Calendar.MONTH) + 1);
			
			if(!seasons[CurrentMonth].equals(seasonCode))
			OldSeasonBoost = Float.parseFloat(SeasonAdjustMent);		
			
			return OldSeasonBoost;
			
		}
		
		//Step 3: GET Minimum Revenue Gross Margin
		//Parameters: BaseDiscount, MRP, Purchase Price
		public float getRGM(String baseDiscount, String MRP, String purchasePrice)
		{
			float RGM = 0;
			RGM = Float.parseFloat(MRP) - (Float.parseFloat(baseDiscount)/100*Float.parseFloat(MRP)) - Float.parseFloat(purchasePrice);		
			return RGM;
			
		}
		
		public double getPurchasePrice(double mrp, double buyingMargin)
		{
			double purchasePrice = ((100-buyingMargin)/100d)*mrp;
			return purchasePrice;
		}
		
		public double  getRGM(double baseDiscount, double purchasePrice, double mrp) {
		       double rgm = mrp-(baseDiscount/100*mrp)-purchasePrice;
		       return rgm;
		   }
			
		
		public String[] getReducedArray(String[] FullArray, String[] ExclusionArray)
		{
		
			List<List<String>> Complete_Array = new ArrayList<List<String>>();
			List<List<String>> Exclusion_Array = new ArrayList<List<String>>();
			Complete_Array = new ArrayList(Arrays.asList(FullArray));
			Exclusion_Array = new ArrayList(Arrays.asList(ExclusionArray));
			System.out.println("++++++++"+Complete_Array);
			System.out.println("========="+Exclusion_Array);
			Complete_Array.removeAll(Exclusion_Array);
			String[] ReducedArray = Complete_Array.toArray(new String[Complete_Array.size()]);
							
			return ReducedArray;
			
		}
		
		//---SERVICE HELPER METHODS
		public RequestGenerator invokeGetStyleDataService(String PageNumber, String StyleCount, String MerchandiseType)
		{
			MyntraService Service = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE, init.Configurations);
			Service.URL= apiUtil.prepareparameterizedURL(Service.URL, new String[] {PageNumber,StyleCount,MerchandiseType});
			System.out.println("\nGet Style Data Service URL : "+Service.URL);
			
			return new RequestGenerator(Service); 
		}
		
		public RequestGenerator invokeCreateInternalPolicyService(String response, String TotalItemCount, String Operator,String GM_Adj,  String GMMatrixDiscount, String FC_Adj,String FCMatrixDiscount, String STR_Adj, String STR_Discount,String MRP_Adj, String MRP_Discount, String BrokenSKU_Adj, String BrokenSKU_Discount,String OldSeason_Adj, String OldSeason_Discount,String BrandFocus_Adj, String BrandFocus_Discount, String MerchandiseType, String MinimumRGM) throws InterruptedException
		{
			int ageLow, ageHigh, dohLow, dohHigh, strLow, strHigh, mrpLow, mrpHigh, minRGM, bskuLow, bskuHigh;
			
			int TotalStyleCount = Integer.parseInt(TotalItemCount);
			String StyleIds[] = getStyleIdArray(response, Integer.parseInt(TotalItemCount));
			LinkedHashMap<String, Map<String,String>> finalStyleInfoMap =new LinkedHashMap<String, Map<String,String>>();
			
			finalStyleInfoMap = generateStyleMap(response);		
			
			ageLow=findMinValue(finalStyleInfoMap,"age",StyleIds);
			ageHigh=findMaxValue(finalStyleInfoMap,"age",StyleIds);
			dohLow=findMinValue(finalStyleInfoMap,"daysOnHold",StyleIds);
			dohHigh=findMaxValue(finalStyleInfoMap,"daysOnHold",StyleIds);
			strLow=findMinValue(finalStyleInfoMap,"unitsSoldLastWeek",StyleIds);
			strHigh=findMaxValue(finalStyleInfoMap,"unitsSoldLastWeek",StyleIds);
			mrpLow=findMinValue(finalStyleInfoMap,"maximumRetailPrice",StyleIds);
			mrpHigh=findMaxValue(finalStyleInfoMap,"maximumRetailPrice",StyleIds);
			bskuLow=findMinValue(finalStyleInfoMap,"brokenSku",StyleIds);
			bskuHigh=findMaxValue(finalStyleInfoMap,"brokenSku",StyleIds);
		
			//Print Data
			printAndLog("Age Low : ",ageLow);
			printAndLog("Age High : ",ageHigh);
			printAndLog("Days on Hold Low : ",dohLow);
			printAndLog("Days on Hold High : ",dohHigh);
			printAndLog("Sale Through Rate Adj Low : ",strLow);
			printAndLog("Sale Through Rate Adj High : ",strHigh);
			printAndLog("MRP Adj Low : ",mrpLow);
			printAndLog("MRP Adj High : ",mrpHigh);
			printAndLog("Broken SKU Adjustment Low : ",bskuLow);
			printAndLog("Broken SKU Adjustment High : ",bskuHigh);

			String GM_AgeLow = getDiscountCriteria(GM_Adj,Integer.toString(ageLow));
			String GM_AgeHigh = getDiscountCriteria(GM_Adj,Integer.toString(ageHigh));
			String GM_DOHLow = getDiscountCriteria(GM_Adj,Integer.toString(dohLow));
			String GM_DOHHigh = getDiscountCriteria(GM_Adj,Integer.toString(dohHigh));
			String FC_AgeLow = getDiscountCriteria(FC_Adj,Integer.toString(ageLow));
			String FC_AgeHigh = getDiscountCriteria(FC_Adj,Integer.toString(ageHigh));
			String FC_DOHLow = getDiscountCriteria(FC_Adj,Integer.toString(dohLow));
			String FC_DOHHigh = getDiscountCriteria(FC_Adj,Integer.toString(dohHigh));
			String STR_Low = getDiscountCriteria(STR_Adj, Integer.toString(strLow));
			String STR_High = getDiscountCriteria(STR_Adj, Integer.toString(strHigh));
			String MRP_Low = getDiscountCriteria(MRP_Adj,Integer.toString(mrpLow));
			String MRP_High = getDiscountCriteria(MRP_Adj,Integer.toString(mrpHigh));
			String BSKU_Low = getDiscountCriteria(BrokenSKU_Adj,Integer.toString(bskuLow));
			String BSKU_High = getDiscountCriteria(BrokenSKU_Adj,Integer.toString(bskuHigh));
			
			
			
			String[] PolicyParameters={Operator,GM_AgeLow,GM_AgeHigh,GM_DOHLow,GM_DOHHigh,GMMatrixDiscount,FC_AgeLow,FC_AgeHigh,FC_DOHLow,FC_DOHHigh,FCMatrixDiscount,STR_Low,STR_High,
					
					STR_Discount,MRP_Low,MRP_High,MRP_Discount,MinimumRGM,BSKU_Low,BSKU_High,BrokenSKU_Discount,OldSeason_Discount,BrandFocus_Discount,MerchandiseType};
			
			
			MyntraService Service = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICY, APINAME.CREATEINTERNALPRICINGPROMOTIONPOLICY, init.Configurations, PolicyParameters);
			printAndLog("\nCreate Internal Policy Service URL : ",Service.URL);
			printAndLog("\nCreate Internal Policy Service Payload : ",Service.Payload);

			return new RequestGenerator(Service); 

		}
		
		public RequestGenerator getCurrentlyFocussedBrands()
		{
			MyntraService Service = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICY, APINAME.GETCURRENTLYFOCUSSEDBRANDS, init.Configurations);
			printAndLog("\nGet Currently Focussed Brands Service URL : ",Service.URL);
			return new RequestGenerator(Service);
		}
		
		public String[] createCollectionInFocus(String StyleIds[], int Count, String CollectionInFocusBoost)
		{
			String StyleDataForCollection=null;
			String ExcludedStyleData[] = new String[Count];
			ExcludedStyleData[0]=StyleIds[0];

			if(!(Count==0))
			{
				StyleDataForCollection = StyleIds[0];		
				for(int i=1; i<Count;i++)
				{
					if(!(i==Count-1))
					{
						StyleDataForCollection = StyleDataForCollection +','+StyleIds[i]+',';
					}
					else
					{
						StyleDataForCollection = StyleDataForCollection+StyleIds[i];
					}
					ExcludedStyleData[i]=StyleIds[i];
				}
				
			}
			
			long StartDateLong = getStartDate(); 
			long EndDateLong = getEndDate(StartDateLong); //Gives One Hour Break in between till the Policy expires
			
			MyntraService Service = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICY, APINAME.CREATECOLLECTIONINFOCUSUSINGPAYLOAD, init.Configurations,new String[] {Long.toString(StartDateLong),Long.toString(EndDateLong),CollectionInFocusBoost,StyleDataForCollection,randomize("NAME"),randomize("DESC")});
			printAndLog("\nCreate Collection in Focus Service URL : ",Service.URL);
			printAndLog("\nCreate Collection in Focus Service Payload : ",Service.Payload);
			
			RequestGenerator request = new RequestGenerator(Service);
			printAndLog("Create Collection in focus Service Status : ",request.response.getStatus());
			
			String Response =request.respvalidate.returnresponseasstring();
			
			printAndLog("\nCreate Collection in Focus Service Response : ",Response);			
			
			String CollectionInFocusID = JsonPath.read(Response,"$.id").toString();
			
			//Activate Collection in Focus
			Service = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICY, APINAME.UPDATECOLLECTIONINFOCUS, init.Configurations,new String[] {CollectionInFocusID,"Active"});
			printAndLog("\nUpdate Collection in Focus Service URL : ",Service.URL);
			printAndLog("\nUpdate Collection in Focus Service Payload : ",Service.Payload);

			request = new RequestGenerator(Service);
			Response = request.respvalidate.returnresponseasstring();
			System.out.println("\nActivate Collection in Focus Response : \n"+Response);		
			
			
			return ExcludedStyleData;
		}
		
		public RequestGenerator startPricingEngine()
		{
			MyntraService Service = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.STARTPRICINGENGINE, init.Configurations);
			System.out.println("\nStart Pricing Engine URL : "+Service.URL);
			return new RequestGenerator(Service); 
		}
		
		public String mappingStylesId(String[] stylidarr)
		{
			String message= "";
			for(int i=0 ; i<=stylidarr.length-1;i++)
			{
				MyntraService service1 = Myntra.getService(
						ServiceType.PORTAL_PRICINGANALYTICS,
						APINAME.MAPPINGSTYLE, init.Configurations,
						new String[] {stylidarr[i]}, PayloadType.JSON, PayloadType.JSON);
				System.out.println("Service URL== > " +service1.URL );

				RequestGenerator requestGenerator1 = new RequestGenerator(service1);
				
				System.out.println("Service payload== > " +service1.Payload );
				System.out.println("Service Response== > " + requestGenerator1.respvalidate.returnresponseasstring());

				System.out.println("mapping style id " + stylidarr[i]);
				String jsonmessage=requestGenerator1.respvalidate.returnresponseasstring();
			  message  = JsonPath.read(jsonmessage, "$.msg");
			  System.out.println("Printing mapping message--- > " + message);


			}
			
			return message;
		}

		
		public String[] createAndActivateTDRange(double TD_Min, double TD_Max, String MerchandiseType, String[] StyleIds, int Count)
		{
			
			
			String StyleDataForTDRange=null;
			String ExcludedStyleData[] = new String[Count];
			ExcludedStyleData[0]=StyleIds[0];

			if(!(Count==0))
			{
				StyleDataForTDRange = StyleIds[0];		
				for(int i=1; i<Count;i++)
				{
					if(!(i==Count))
					{
						StyleDataForTDRange = StyleDataForTDRange +','+StyleIds[i];
					
					}
					else
					{
						StyleDataForTDRange = StyleDataForTDRange+StyleIds[i];
					}
					ExcludedStyleData[i]=StyleIds[i];
				}
				
			}
			
			
			boolean status1 = false, status2 = false;
			long StartDateLong = getStartDate(); 
			long EndDateLong = getEndDate(StartDateLong); //Gives One Hour Break in between till the Policy expires
			String[] PayloadParams = {Long.toString(StartDateLong), Long.toString(EndDateLong),Double.toString(TD_Min),Double.toString(TD_Max),StyleDataForTDRange,randomize("TDR_NAME"),randomize("TDR_DESC")};
			
			MyntraService Service = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICY, APINAME.CREATETDRANGEUSINGPAYLOAD, init.Configurations,PayloadParams);
			RequestGenerator request = new RequestGenerator(Service);
			System.out.println("Create TD Range Service URL : "+Service.URL);
			System.out.println("Create TD Range Service Payload : "+Service.Payload);

			String response = request.respvalidate.returnresponseasstring();
			System.out.println("Create TD Range Service Response : "+response);

			String RangeID = JsonPath.read(response,"$.id").toString();
			status1 = (request.response.getStatus()==200)?true:false;
			
			String[] UpdatePayloadParams={RangeID,"Active"};
			Service = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICY, APINAME.UPDATETDRANGESTATUS, init.Configurations,UpdatePayloadParams);
			request = new RequestGenerator(Service);
			System.out.println("Update TD Range Service URL : "+Service.URL);
			System.out.println("Update TD Range Service Payload : "+Service.Payload);

			response = request.respvalidate.returnresponseasstring();
			System.out.println("Update TD Range Service Response : "+response);

			status1 = (request.response.getStatus()==200)?true:false;
			return ExcludedStyleData;
			
				
		}

		
		
	//Get MRP from style id list

public String[] getStylemrpArray(String response, int stylecount){
	
	String StyleIdsMrp[]=new String[stylecount];
	for(int i=0;i<stylecount;i++){
		StyleIdsMrp[i]=JsonPath.read(response, "$.styleList["+i+"].maximumRetailPrice").toString();
	}
	return StyleIdsMrp;
}

private static List<String> returnedIds=new ArrayList<String>();

@SuppressWarnings("null")
public static String [] getReturnedIds(List<String>excludeIds) {
	String [] test = new String[excludeIds.size()];;
	for(int i=0;i<=excludeIds.size()-1;i++){
	test[i]=excludeIds.get(i).trim();
	}
	return test;	
}

@SuppressWarnings("unused")
public String[] getExcludedIds(String[] stylidarr, String[] stylmrparr, int count){

	List<String> excludedIdList=new ArrayList<>();
	String[] returnIdsArr = new String[2];
	for (int i=0;i<=stylidarr.length-1;i++) {
		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW,
				APINAME.FILTEREXCLUSIONS, init.Configurations,
				new String[] { stylidarr[i],stylmrparr[i]}, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator requestGenerator1 = new RequestGenerator(service1);
		String jsonFiltrRes=requestGenerator1.respvalidate.returnresponseasstring();
		System.out.println("FilterExclusion Response -->> : "+jsonFiltrRes);
		JSONArray x = JsonPath.read(jsonFiltrRes, "$.styleList".toString());
		//System.out.println("printing x "+x);
		if((x.isEmpty())){
			excludedIdList.add(stylidarr[i]);
			if(excludedIdList.size()==2)
			{
				excludedIdList.toArray(returnIdsArr);
				return returnIdsArr;
			}
			}
	}
	if(excludedIdList.size()<2)
	{
		for(int i=0;i<2;i++)
		{
			MyntraService service2 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICYNEW,
					APINAME.CREATETDEXCLUSIONS, init.Configurations,
					new String[] {startTime,endTime,stylidarr[i]}, PayloadType.JSON, PayloadType.JSON);
			RequestGenerator requestGenerator2 = new RequestGenerator(service2);
			String jsonCreteExclusionRes=requestGenerator2.respvalidate.returnresponseasstring();
			System.out.println("Create Exclusion Response: "+jsonCreteExclusionRes);
			String createtdexclusionid=JsonPath.read(jsonCreteExclusionRes, "$.id".toString());
			System.out.println("createtdexclusion id : "+createtdexclusionid);
			MyntraService updtdexclusion = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICYNEW,
					APINAME.UPDATETDEXCLUSION, init.Configurations,
					new String[] {createtdexclusionid,"Active"}, PayloadType.JSON, PayloadType.JSON);
			RequestGenerator updatdexclusion = new RequestGenerator(updtdexclusion);
			String updatetdexclusionRes=updatdexclusion.respvalidate.returnresponseasstring();
			System.out.println("Updte td exclusion Response -->> : "+updatetdexclusionRes);
			
			excludedIdList.add(stylidarr[i]);
		}
		}
	
	
	return excludedIdList.toArray(returnIdsArr);
	
	}


	public RequestGenerator getStyleDiscountData(String StyleId)
	{
		MyntraService Service = Myntra.getService(ServiceType.PORTAL_DISCOUNTSERVICEV2,APINAME.GETTRADEANDDEALDISCOUNTS,init.Configurations,new String[] {StyleId});
		Service.URL = apiUtil.prepareparameterizedURL(Service.URL, "default");
		printAndLog("Get Dicount Data For Style - URL : ", Service.URL);
		printAndLog("Get Dicount Data For Style - Payload : ", Service.Payload);
		return new RequestGenerator(Service);

	}
	
	public RequestGenerator getBasePricingEngineOutput(String instanceID, String PageNumber, String Size)
	{
		printAndLog("Get Base Pricing Engine : ","START");
		MyntraService Service = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE,APINAME.GETBASEPRICINGENGINEOUTPUT,init.Configurations);
		Service.URL = apiUtil.prepareparameterizedURL(Service.URL, new String[] {instanceID,PageNumber,Size});
		printAndLog("Get Base Pricing Engine Output - URL : ", Service.URL);
		return new RequestGenerator(Service);

	}
	
	
	
	public String deleteTdRange(String id){
		
		
		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.DELETETDRANGE,
				init.Configurations,PayloadType.JSON,new String [] {id},PayloadType.JSON);
		System.out.println(myntraService1.URL);
		RequestGenerator req = new RequestGenerator(myntraService1);
		System.out.println("Payload for filter td range ----  >>?? " + myntraService1.Payload);
		System.out.println("Response for filter td range ---? " + req.respvalidate.returnresponseasstring());
		String jsonRes1 = req.respvalidate.returnresponseasstring();
		String message = JsonPath.read(jsonRes1, "$.message");
		
		return message;
		
	}


	public String deleteCollectionInFocus(String id){
	
	
	MyntraService myntraService1 = Myntra.getService(
			ServiceType.PORTAL_PRICINGPOLICY, APINAME.DELETECOLLECTIONINFOCUS,
			init.Configurations,PayloadType.JSON,new String [] {id},PayloadType.JSON);
	System.out.println(myntraService1.URL);
	RequestGenerator req = new RequestGenerator(myntraService1);
	System.out.println("Payload for Delete colelction in foxus ----  >>?? " + myntraService1.Payload);
	System.out.println("Response for Delete colelction in foxus  " + req.respvalidate.returnresponseasstring());
	String jsonRes1 = req.respvalidate.returnresponseasstring();
	String message = JsonPath.read(jsonRes1, "$.message");
	
	return message;
	
}

public String deleteTdExlusion(String id){
	
	
	MyntraService myntraService1 = Myntra.getService(
			ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.DELETETDEXCLUSION,
			init.Configurations,PayloadType.JSON,new String [] {id},PayloadType.JSON);
	System.out.println(myntraService1.URL);
	RequestGenerator req = new RequestGenerator(myntraService1);
	System.out.println("Payload for DELETE TD EXCLUSION ----  >>?? " + myntraService1.Payload);
	System.out.println("Response for DELETE TD EXCLUSION  ---? " + req.respvalidate.returnresponseasstring());
	String jsonRes1 = req.respvalidate.returnresponseasstring();
	String message = JsonPath.read(jsonRes1, "$.message");
	
	return message;
	
}

public String deleteTdRangeIds(String pageIndex, String pageSize) {

	MyntraService service = Myntra.getService(
			ServiceType.PORTAL_PRICINGPOLICYNEW,
			APINAME.GETTDRANGEBYPAGESIZE, init.Configurations,
			PayloadType.JSON, new String[] { pageIndex, pageSize },
			PayloadType.JSON);
	RequestGenerator requestGenerator = new RequestGenerator(service);

	System.out.println("service url For STYLE DATAPAGE = " + service.URL);
	System.out.println("Response For STYLE DATAPAGE---->>>>>>"
			+ requestGenerator.respvalidate.returnresponseasstring());
	String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
	Integer count=  JsonPath.read(jsonRes, "$.count");
	if(count == 0)
	{
		System.out.println("there is no TD range");

	}
	else {
		List<String> ids = JsonPath.read(jsonRes, "$.tdRangeDTOs..id[*]");
		{
			
			for (int i = 0; i < ids.size(); i++) {
				String deleteMsg = deleteTdRange(ids.get(i).toString());

			}

		}

	
	}

	return "0";


}


public String deleteTdExclusionIds(String pageIndex, String pageSize) {

	MyntraService service = Myntra.getService(
			ServiceType.PORTAL_PRICINGPOLICYNEW,
			APINAME.GETTDEXCLUSIONS, init.Configurations,
			PayloadType.JSON, new String[] { pageIndex, pageSize },
			PayloadType.JSON);
	RequestGenerator requestGenerator = new RequestGenerator(service);

	System.out.println("service url For STYLE DATAPAGE = " + service.URL);
	System.out.println("Response For STYLE DATAPAGE---->>>>>>"
			+ requestGenerator.respvalidate.returnresponseasstring());
	String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
	Integer count=  JsonPath.read(jsonRes, "$.count");
	if(count == 0)
	{
		System.out.println("there is no TD EXLUSION");

	}
	else {
		List<String> ids = JsonPath.read(jsonRes, "$.tdExclusionDTOs..id[*]");
		{
			
			for (int i = 0; i < ids.size(); i++) {
				String deleteMsg = deleteTdExlusion(ids.get(i).toString());

			}

		}

	
	}

	return "0";


}



public String deleteCollectionInFocusIds(String pageIndex, String pageSize) {

	MyntraService service = Myntra.getService(
			ServiceType.PORTAL_PRICINGPOLICY,
			APINAME.GETCOLLECTIONSINFOCUSBYPAGE, init.Configurations,
			PayloadType.JSON, new String[] { pageIndex, pageSize },
			PayloadType.JSON);
	RequestGenerator requestGenerator = new RequestGenerator(service);

	System.out.println("service url For STYLE DATAPAGE FOR COLLECTION IN FOCUS = " + service.URL);
	System.out.println("Response For STYLE DATAPAGE FOR COLLECTION IN FOCUS ---->>>>>>"
			+ requestGenerator.respvalidate.returnresponseasstring());
	String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
	Integer count=  JsonPath.read(jsonRes, "$.count");
	if(count == 0)
	{
		System.out.println("there is no COLLECTION IN FOCUS");

	}
	else {
		List<String> ids = JsonPath.read(jsonRes, "$.collectionInfocusDTOList..id[*]");
		{
			
			for (int i = 0; i < ids.size(); i++) {
				String deleteMsg = deleteCollectionInFocus(ids.get(i).toString());

			}

		}

	
	}

	return "0";

}

	
	
	public String getDayType(){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICY,APINAME.GETDAYCONFIG, init.Configurations,PayloadType.JSON, new String[] { },PayloadType.JSON);
		RequestGenerator requestGenerator = new RequestGenerator(service);
		String response = requestGenerator.respvalidate.returnresponseasstring();            
	    System.out.println(response);
	    String res=JsonPath.read(response, "$.dayType");
	    System.out.println("Sale day Type : --> "+ res);
	    if(res.equals("SALEDAY")){
	    	MyntraService BaseLineDayConfig = Myntra.getService(
	    			ServiceType.PORTAL_PRICINGPOLICY,
	    			APINAME.SETBASELINE, init.Configurations,
	    			new String [] {},PayloadType.JSON,PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(BaseLineDayConfig);
	    	String respBaseline = req.respvalidate.returnresponseasstring();            
	        System.out.println("Set Baseline response : "+respBaseline);
	        String BaseLineRes=JsonPath.read(respBaseline, "$.dayType");
	        System.out.println("BaseLineresponse1: "+BaseLineRes);
	        return BaseLineRes;
	    }else{
	    	return res;
	    }
//	    String BaseLineRes=JsonPath.read(response, "$.dayType");
//        System.out.println("BaseLineRes: "+BaseLineRes);
//		return BaseLineRes;
		
	}
	}




	
	


