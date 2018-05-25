package com.myntra.apiTests.portalservices.devapiservice;

import java.util.HashMap;
import java.util.Random;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.Myntra;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

import net.minidev.json.JSONArray;

public class OnBoardingTestsHelper 
{
	static Initialize init = new Initialize("/Data/configuration");
	APIUtilities apiUtilities=new APIUtilities();
	IDPTestsHelper idpHelper = new IDPTestsHelper();
	
	public String randomize(String Data)
	{
		String RandomizedData = Data;
		
		int RangeStart = 1000;
		int RangeEnd = 9999;
		Random obj = new Random();
		RandomizedData = RandomizedData+((obj.nextInt(RangeEnd - RangeStart)+RangeStart));
		
		return RandomizedData;	
	}

	public HashMap<String,String> SetScenario(String username, String password, String Scn, String appStat, String deviceId)
	{
		idpHelper.getAndSetTokens(username,password);
		String deviceID_D =deviceId;
		boolean addXid = true;
		
		switch(Scn)
		{
			case "NONE":
				{
					deviceID_D="";
					addXid=false;
					System.out.println("Get/Set Data without Device ID and XID");
					break;
				}
			case "DEVICEID":
				{
					addXid=false;
					System.out.println("Get/Set Data with Device ID alone");
					break;
				}
			case "XID":
				{
					deviceID_D="";
					addXid=true;
					System.out.println("Get/Set Data with  XID alone");
					break;
				}
			case "BOTH":
			{
				
				addXid=true;
				System.out.println("Get/Set Data with XID & Device ID both");
				break;
			}	
		}
		String xid_L="";
		if(addXid)
		{
			xid_L = IDPTestsHelper.XID;
		}
		
		HashMap<String,String> Scenario = new HashMap<String,String>();
		Scenario.put("XID", xid_L);
		Scenario.put("DEVICE_ID", deviceID_D);
		
		return Scenario;
	}	
	
	public RequestGenerator getHallmarkUserOnboardingData(String username, String password, String Scn, String appStat, String deviceId)
	{
		
		HashMap<String,String> scn = SetScenario(username, password, Scn,  appStat,  deviceId);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIHALLMARKGETONBOARDINGDATA,init.Configurations);
		HashMap<String, String> headers = new HashMap<String,String>();
		if(!(scn.get("XID").toString().isEmpty()))
			{
				headers.put("xid", IDPTestsHelper.XID);
			}
		headers.put("Content-Type", "application/json");
		service.URL=apiUtilities.prepareparameterizedURL(service.URL, new String[] {appStat,scn.get("DEVICE_ID").toString()});
		System.out.println("Get user onboarding state service URL : "+service.URL);
		return new RequestGenerator(service,headers);
	}
	
	//Hallmark Services - Helpers
	public String getSingleStrategy(String response, String StrategyType)
	{
		String strategy = "";
		String Path="";
		if(StrategyType.equals("DEPENDENT"))
		{
			Path = "$.data.steps[*].props.dependent[*]";
		}
		if(StrategyType.equals("MAIN"))
		{
			Path = "$.data.steps[*].strategy-id";
		}
		
		int responseStatus = Integer.parseInt(JsonPath.read(response,"$.meta.code").toString());
		if(responseStatus==200)
		{
			JSONArray Strategies = JsonPath.read(response,Path);
			System.out.println(StrategyType+" Strategy Array Length : "+Strategies.size());
			int strategyIndex = getRandomNumberInRange(0,Strategies.size());
			System.out.println("Random Index : "+strategyIndex);
			strategy = Strategies.get(strategyIndex).toString();
		}
		
		
		return strategy;
		
	}
	
	public int getRandomNumberInRange(int min, int max)
	{
		
		return (min + (int)(Math.random() * max));
		
	}
	
	public String getStrategyJSONPath(String StrategyType)
	{
		String Path="";
		if(StrategyType.equals("DEPENDENT"))
		{
			Path = "$.data.steps[*].props.dependent[*]";
		}
		if(StrategyType.equals("MAIN"))
		{
			Path = "$.data.steps[*].strategy-id";
		}
		return Path;
	}
	
	
	
	public String getMultipleStrategies(String response, String StrategyType, int numberOfStrategies)
	{
		String strategy = "";
		String Path=getStrategyJSONPath(StrategyType);
		
		int responseStatus = Integer.parseInt(JsonPath.read(response,"$.meta.code").toString());
		if(responseStatus==200)
		{
			JSONArray Strategies = JsonPath.read(response,Path);
			int ArraySize = Strategies.size();
			//System.out.println("Strategy Array Length : "+ArraySize);
			//System.out.println("Required number of Strategies : "+numberOfStrategies);
			if(numberOfStrategies>ArraySize)
			{
				numberOfStrategies=ArraySize;
			}
			//System.out.println("Processed number of Strategies : "+numberOfStrategies);
			int randomIndices[] = new int[numberOfStrategies];
			int strategyIndex =-1;
			boolean contains=false;
			//Random Order of Strategies
			for(int k=0;k<randomIndices.length;k++)
			{
				randomIndices[k]=-1;
			}
			for(int i=0; i<numberOfStrategies;i++)
			{
				do{	
					/*for(int k=0;k<randomIndices.length;k++)
					{
						System.out.println("Random Indices : "+randomIndices[k]);
					}*/
					contains=false;
					strategyIndex = getRandomNumberInRange(0,Strategies.size());
					//System.out.println("Calculated Index : "+strategyIndex);
					for(int j=0;j<randomIndices.length;j++)
					{
						if (randomIndices[j]==strategyIndex)
						{
							//System.out.println("FOUND MATCH!!!");
							contains=true;
						}
					}				
				}while(contains);
				
				randomIndices[i]=strategyIndex;
			}
			
			for(int i=0;i<randomIndices.length;i++)
			{
				//System.out.println("Random Indices : "+randomIndices[i]);
				
				if(i==randomIndices.length-1)
				{
					strategy = strategy+Strategies.get(randomIndices[i]);
				}
				else
				{
				strategy = strategy+Strategies.get(randomIndices[i])+"\",\"";
				}
			}
									
			System.out.println("Final Strategy List :\n "+strategy);
			//strategy = Strategies.get(strategyIndex).toString();
		}
				
		return strategy;
		
	}
	
	
	public String[] getMultipleStrategiesArr(String response, String StrategyType, int numberOfStrategies)
	{
		String strategy = "";
		String Path=getStrategyJSONPath(StrategyType);
		int responseStatus = Integer.parseInt(JsonPath.read(response,"$.meta.code").toString());
		String[] StrategiesArr = null;
		if(responseStatus==200)
		{
			JSONArray Strategies = JsonPath.read(response,Path);
			int ArraySize = Strategies.size();
			//System.out.println("Strategy Array Length : "+ArraySize);
			//System.out.println("Required number of Strategies : "+numberOfStrategies);
			if(numberOfStrategies>ArraySize)
			{
				numberOfStrategies=ArraySize;
			}
			StrategiesArr = new String[numberOfStrategies];
			//System.out.println("Processed number of Strategies : "+numberOfStrategies);
			
			int randomIndices[] = new int[numberOfStrategies];
			int strategyIndex =-1;
			boolean contains=false;
			//Random Order of Strategies
			for(int k=0;k<randomIndices.length;k++)
			{
				randomIndices[k]=-1;
			}
			for(int i=0; i<numberOfStrategies;i++)
			{
				do{	
					/*for(int k=0;k<randomIndices.length;k++)
					{
						System.out.println("Random Indices : "+randomIndices[k]);
					}*/
					contains=false;
					strategyIndex = getRandomNumberInRange(0,Strategies.size());
					//System.out.println("Calculated Index : "+strategyIndex);
					for(int j=0;j<randomIndices.length;j++)
					{
						if (randomIndices[j]==strategyIndex)
						{
							//System.out.println("FOUND MATCH!!!");
							contains=true;
						}
					}				
				}while(contains);
				
				randomIndices[i]=strategyIndex;
			}
			
			for(int i=0;i<randomIndices.length;i++)
			{
				//System.out.println("Random Indices : "+randomIndices[i]);
				StrategiesArr[i]=Strategies.get(randomIndices[i]).toString();
				if(i==randomIndices.length-1)
				{
					strategy = strategy+Strategies.get(randomIndices[i]);
				}
				else
				{
				strategy = strategy+Strategies.get(randomIndices[i])+"\",\"";
				}
			}
									
			System.out.println("Final Strategy List :\n "+strategy);
			//strategy = Strategies.get(strategyIndex).toString();
		}
				
		
		return StrategiesArr;
		
	}
	
	
	
	
	public boolean isStrategyRemoved(String response, String strategyType, String Strategy)
	{
		String Path=getStrategyJSONPath(strategyType);
		boolean Removed = true;
		int responseStatus = Integer.parseInt(JsonPath.read(response,"$.meta.code").toString());
		if(responseStatus==200)
		{
			JSONArray Strategies = JsonPath.read(response,Path);
			int ArraySize = Strategies.size();
			for(int i =0; i<ArraySize;i++)
			{
				if(Strategies.get(i).equals(Strategy))
				{
					Removed = false;
				}
			}
		}		
		System.out.println("IS STRATEGY REMOVED? - "+Removed);
		return Removed;
	}
	
	public String[] getDependantStrategies(String response, String MainStrategy)
	{
		
		String[] dependentStrategies = new String[0];
		String Path=getStrategyJSONPath("MAIN");		
		JSONArray DependentStrategies = null;
		int responseStatus = Integer.parseInt(JsonPath.read(response,"$.meta.code").toString());
		if(responseStatus==200)
		{
			JSONArray Strategies = JsonPath.read(response,Path);
			int ArraySize = Strategies.size();
			for(int i =0; i<ArraySize;i++)
			{
				if(Strategies.get(i).equals(MainStrategy))
				{
					String newPath = "$.data.steps["+i+"].props.dependent[*]";					
					DependentStrategies = JsonPath.read(response,newPath);
					dependentStrategies=new String[DependentStrategies.size()];
				}
			}
		}
		
		for(int i=0;i<DependentStrategies.size();i++)
		{
			dependentStrategies[i]=DependentStrategies.get(i).toString();
		}
		
		return dependentStrategies;		
	}
	
	public String getMainStrategyWithoutDependents(String response)
	{
		String Path=getStrategyJSONPath("MAIN");
		String Strategy=null;
		String newPath="";
		JSONArray D_Strategies = null;
		int responseStatus = Integer.parseInt(JsonPath.read(response,"$.meta.code").toString());
		if(responseStatus==200)
		{
			JSONArray Strategies = JsonPath.read(response,Path);
			int ArraySize = Strategies.size();
			for(int i =0; i<ArraySize;i++)
			{
				newPath = "$.data.steps["+i+"].props.dependent[*]";
				try
				{	D_Strategies = JsonPath.read(response,newPath);
					if(D_Strategies.isEmpty())
					{
						Strategy = Strategies.get(i).toString();
						return Strategy;
					} 
				}
				catch(com.jayway.jsonpath.PathNotFoundException e)
				{
					continue;
				}
			}
		}
		
		return Strategy;
		
	}
	
	public String getMainStrategyWithDependents(String response)
	{
		String Path=getStrategyJSONPath("MAIN");
		String Strategy=null;
		String newPath="";
		JSONArray D_Strategies = null;
		int responseStatus = Integer.parseInt(JsonPath.read(response,"$.meta.code").toString());
		if(responseStatus==200)
		{
			JSONArray Strategies = JsonPath.read(response,Path);
			int ArraySize = Strategies.size();
			for(int i =0; i<ArraySize;i++)
			{
				newPath = "$.data.steps["+i+"].props.dependent[*]";
				try
				{	D_Strategies = JsonPath.read(response,newPath);
					if(!D_Strategies.isEmpty())
					{
						Strategy = Strategies.get(i).toString();
						return Strategy;
					} 
				}
				catch(com.jayway.jsonpath.PathNotFoundException e)
				{
					continue;
				}
			}
		}
		
		return Strategy;
		
	}
	
	
	//Hallmark Services - Save onboarding Data
	public RequestGenerator SaveHallmarkUserOnboardingData(String username, String password, String Scn, String appStat, String deviceId, String Strategy)
	{
		HashMap<String,String> scn = SetScenario(username, password, Scn,  appStat,  deviceId);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIHALLMARKSAVEONBOARDINGDATA,init.Configurations,new String[] {scn.get("DEVICE_ID").toString(),"\""+Strategy+"\""});
		HashMap<String, String> headers = new HashMap<String,String>();
		if(!(scn.get("XID").toString().isEmpty()))
			{
				headers.put("xid", IDPTestsHelper.XID);
			}
		headers.put("Content-Type", "application/json");
		System.out.println("Save user onboarding state service URL : "+service.URL);
		System.out.println("Save user onboarding state service URL : "+service.URL);

		return new RequestGenerator(service,headers);
	}
}
