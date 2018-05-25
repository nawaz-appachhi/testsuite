package com.myntra.apiTests.dataproviders;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;

public class ABTestVariantsDP {
	
	Map<String,Object>  variantA = new HashMap<>();
	Map<String,Object>  variantB = new HashMap<>();
	Map<String,Object>  variantC = new HashMap<>();
	Map<String,Object>  variantD = new HashMap<>();
	Map<String,Object>  variantE = new HashMap<>();
	Map<String,Object>  variantF = new HashMap<>();
	Map<String,Object>  variantG = new HashMap<>();
	Map<String,Object>  defaultVariant = new HashMap<>();
	Map<String,Object>  fixedVariant = new HashMap<>();
	
	public ABTestVariantsDP(){
		//VariantA
		String[] order=new String[]{"sponsored","targeted","promoted","organic"};
		Map<String, Integer> count=new HashMap<>();
		  count.put("sponsored", 5);
		  count.put("promoted", 2);
		  count.put("organic", 20);
		  count.put("targeted", 15);
		  
		  variantA.put("slideshow", "enabled");
		  variantA.put("targeted", "enabled");
		  variantA.put("promoted", "enabled");
		  variantA.put("history", "disabled");
		  variantA.put("more", "disabled");
		  variantA.put("update", "enabled");
		  variantA.put("randomOrder", true);
		  variantA.put("count", count);
		  variantA.put("order", order);
		  
		  //VariantB
		  order=new String[]{"sponsored","targeted","promoted","organic"};
		  
		  count=new HashMap<>();
		  count.put("sponsored", 100);
		  count.put("promoted", 0);
		  count.put("organic", 0);
		  count.put("targeted", 0);
		  
		  variantB.put("slideshow", "disabled");
		  variantB.put("targeted", "disabled");
		  variantB.put("promoted", "disabled");
		  variantB.put("history", "disabled");
		  variantB.put("more", "disabled");
		  variantB.put("update", "disabled");
		  variantB.put("randomOrder", false);
		  variantB.put("count", count);
		  variantB.put("order", order);
		  
		  //VariantC
		  order=new String[]{"sponsored","targeted","promoted","organic"};
		  
		  count=new HashMap<>();
		  count.put("sponsored", 100);
		  count.put("promoted", 4);
		  count.put("organic", 10);
		  count.put("targeted", 0);
		  
		  variantC.put("slideshow", "disabled");
		  variantC.put("targeted", "disabled");
		  variantC.put("promoted", "disabled");
		  variantC.put("history", "unseen");
		  variantC.put("more", "disabled");
		  variantC.put("update", "disabled");
		  variantC.put("randomOrder", false);
		  variantC.put("count", count);
		  variantC.put("order", order);
		  
		  //VariantD
		  order=new String[]{"sponsored","targeted","promoted","organic"};
		  
		  count=new HashMap<>();
		  count.put("sponsored", 5);
		  count.put("promoted", 2);
		  count.put("organic", 20);
		  count.put("targeted", 15);
		  
		  variantD.put("slideshow", "enabled");
		  variantD.put("targeted", "enabled");
		  variantD.put("promoted", "disabled");
		  variantD.put("history", "disabled");
		  variantD.put("more", "disabled");
		  variantD.put("update", "enabled");
		  variantD.put("randomOrder", true);
		  variantD.put("count", count);
		  variantD.put("order", order);
		  
		  //VariantE
		  order=new String[]{"sponsored","targeted","promoted","organic"};
		  
		  count=new HashMap<>();
		  count.put("sponsored", 1);
		  count.put("promoted", 4);
		  count.put("organic", 8);
		  count.put("targeted", 0);
		  
		  variantE.put("slideshow", "disabled");
		  variantE.put("targeted", "disabled");
		  variantE.put("promoted", "enabled");
		  variantE.put("history", "seen");
		  variantE.put("more", "enabled");
		  variantE.put("update", "enabled");
		  variantE.put("randomOrder", false);
		  variantE.put("count", count);
		  variantE.put("order", order);
		  
		  //VariantF
		  order=new String[]{"sponsored","targeted","promoted","organic"};
		  
		  count=new HashMap<>();
		  count.put("sponsored", 100);
		  count.put("promoted", 0);
		  count.put("organic", 0);
		  count.put("targeted", 0);
		  
		  variantF.put("slideshow", "disabled");
		  variantF.put("targeted", "disabled");
		  variantF.put("promoted", "disabled");
		  variantF.put("history", "disabled");
		  variantF.put("more", "disabled");
		  variantF.put("update", "enabled");
		  variantF.put("randomOrder", false);
		  variantF.put("count", count);
		  variantF.put("order", order);
		  
		//VariantG
		  order=new String[]{"sponsored","targeted","promoted","organic"};
		  
		  count=new HashMap<>();
		  count.put("sponsored", 1);
		  count.put("promoted", 4);
		  count.put("organic", 10);
		  count.put("targeted", 0);
		  
		  variantG.put("slideshow", "disabled");
		  variantG.put("targeted", "disabled");
		  variantG.put("promoted", "enabled");
		  variantG.put("history", "seen");
		  variantG.put("more", "enabled");
		  variantG.put("update", "enabled");
		  variantG.put("randomOrder", false);
		  variantG.put("count", count);
		  variantG.put("order", order);
		  
		  //defaultVariant
		  order=new String[]{"sponsored","promoted","targeted","organic"};
		  
		  count=new HashMap<>();
		  count.put("sponsored", 1);
		  count.put("promoted", 2);
		  count.put("organic", 10);
		  count.put("targeted", 6);
		  
		  defaultVariant.put("slideshow", "enabled");
		  defaultVariant.put("targeted", "enabled");
		  defaultVariant.put("promoted", "enabled");
		  defaultVariant.put("history", "disabled");
		  defaultVariant.put("more", "disabled");
		  defaultVariant.put("update", "disabled");
		  defaultVariant.put("randomOrder", true);
		  defaultVariant.put("count", count);
		  defaultVariant.put("order", order);
		  
		  order=new String[]{"sponsored","promoted","targeted","organic"};
		  
		  count=new HashMap<>();
		  count.put("sponsored", 5);
		  count.put("promoted", 2);
		  count.put("organic", 20);
		  count.put("targeted", 10);
		  
		  fixedVariant.put("slideshow", "enabled");
		  fixedVariant.put("targeted", "enabled");
		  fixedVariant.put("promoted", "enabled");
		  fixedVariant.put("history", "disabled");
		  fixedVariant.put("more", "disabled");
		  fixedVariant.put("update", "disabled");
		  fixedVariant.put("randomOrder", true);
		  fixedVariant.put("count", count);
		  fixedVariant.put("order", order);
	}
  @DataProvider
  public Object[][] abConfigurationsDP() {
	  //VariantA
	  
	  Map<String,Object>  config1 = new HashMap<>();
	  config1.putAll(variantA);
	  config1.put("variant", "lgp.stream.variant=variantA");
	  config1.put("case", "case1");
	  
	  //variantB
	  Map<String,Object>  config2 = new HashMap<>();
	  config2.putAll(variantB);
	  config2.put("variant", "lgp.stream.variant=variantB");
	  config2.put("case", "case2");
	  
	  
	  //VariantC
	  Map<String,Object>  config3 = new HashMap<>();
	  config3.putAll(variantC);
	  config3.put("variant", "lgp.stream.variant=variantC");
	  config3.put("case", "case3");
	  
	  
	  
	  Map<String,Object>  config4 = new HashMap<>();
	  config4.putAll(variantD);
	  config4.put("variant", "lgp.stream.variant=variantD");
	  config4.put("case", "case4");
	  
	  //variantE
	  Map<String,Object>  config5 = new HashMap<>();
	  config5.putAll(variantE);
	  config5.put("variant", "lgp.stream.variant=variantE");
	  config5.put("case", "case5");
	  
	  //variantF
	  
	  Map<String,Object>  config6 = new HashMap<>();
	  config6.putAll(variantF);
	  config6.put("variant", "lgp.stream.variant=variantF");
	  config6.put("case", "case6");
	  
	  //variantG
	  
	  Map<String,Object>  config6_1 = new HashMap<>();
	  config6_1.putAll(variantG);
	  config6_1.put("variant", "lgp.stream.variant=variantG");
	  config6_1.put("case", "case6_1");
	  
	  //fixedVariant
	  Map<String,Object>  config7 = new HashMap<>();
	  config7.putAll(fixedVariant);
	  config7.put("variant", "lgp.stream.variant=fixed");
	  config7.put("case", "case7");
	  
	  //For ios should default to variantC 
	  
	  Map<String,Object>  config8a = new HashMap<>();
	  config8a.putAll(variantC);
	  config8a.put("variant", "lgp.stream.variant=variantA");
	  config8a.put("platform", "ios");
	  config8a.put("case", "case8a");
	  
	  //For ios should default to variantC 
	  
	  Map<String,Object>  config8b = new HashMap<>();
	  config8b.putAll(variantC);
	  config8b.put("variant", "lgp.stream.variant=variantB");
	  config8b.put("platform", "ios");
	  config8b.put("case", "case8b");
	  
	  //For ios should default to variantC 
	  
	  Map<String,Object>  config8c = new HashMap<>();
	  config8c.putAll(variantC);
	  config8c.put("variant", "lgp.stream.variant=variantC");
	  config8c.put("platform", "ios");
	  config8c.put("case", "case8c");
	  
	  //For ios should default to variantC 
	  
	  Map<String,Object>  config8d = new HashMap<>();
	  config8d.putAll(variantC);
	  config8d.put("variant", "lgp.stream.variant=variantD");
	  config8d.put("platform", "ios");
	  config8d.put("case", "case8d");
	  
	  //For ios should default to variantC 
	  
	  Map<String,Object>  config8e = new HashMap<>();
	  config8e.putAll(variantC);
	  config8e.put("variant", "lgp.stream.variant=variantE");
	  config8e.put("platform", "ios");
	  config8e.put("case", "case8e");
	  
	  //For ios should default to variantC 
	  
	  Map<String,Object>  config8f = new HashMap<>();
	  config8f.putAll(variantC);
	  config8f.put("variant", "lgp.stream.variant=variantF");
	  config8f.put("platform", "ios");
	  config8f.put("case", "case8f");
	  
	  
//For ios should default to variantC 
	  
	  Map<String,Object>  config8g = new HashMap<>();
	  config8g.putAll(variantC);
	  config8g.put("variant", "lgp.stream.variant=variantG");
	  config8g.put("platform", "ios");
	  config8g.put("case", "case8g");
	  
	  
	  //For android should take the given config 
	  
	  Map<String,Object>  config9a = new HashMap<>();
	  config9a.putAll(variantA);
	  config9a.put("variant", "lgp.stream.variant=variantA");
	  config9a.put("platform", "android");
	  config9a.put("case", "case9a");
	  
	  //For android should take the given config 
	  
	  Map<String,Object>  config9b = new HashMap<>();
	  config9b.putAll(variantB);
	  config9b.put("variant", "lgp.stream.variant=variantB");
	  config9b.put("platform", "android");
	  config9b.put("case", "case9b");
	  
//For android should take the given config 
	  
	  Map<String,Object>  config9c = new HashMap<>();
	  config9c.putAll(variantC);
	  config9c.put("variant", "lgp.stream.variant=variantC");
	  config9c.put("platform", "android");
	  config9c.put("case", "case9c");
	  
//For android should take the given config 
	  
	  Map<String,Object>  config9d = new HashMap<>();
	  config9d.putAll(variantD);
	  config9d.put("variant", "lgp.stream.variant=variantD");
	  config9d.put("platform", "android");
	  config9d.put("case", "case9d");
	  
//For android should take the given config 
	  
	  Map<String,Object>  config9e = new HashMap<>();
	  config9e.putAll(variantE);
	  config9e.put("variant", "lgp.stream.variant=variantE");
	  config9e.put("platform", "android");
	  config9e.put("case", "case9e");
	  
//For android should take the given config 
	  
	  Map<String,Object>  config9f = new HashMap<>();
	  config9f.putAll(variantF);
	  config9f.put("variant", "lgp.stream.variant=variantF");
	  config9f.put("platform", "android");
	  config9f.put("case", "case9f");
	  
//For android should take the given config 
	  
	  Map<String,Object>  config9g = new HashMap<>();
	  config9g.putAll(variantG);
	  config9g.put("variant", "lgp.stream.variant=variantG");
	  config9g.put("platform", "android");
	  config9g.put("case", "case9G");
	  
	  
	  
	  //Invalid variant name (default config)
	  
	  Map<String,Object>  config10 = new HashMap<>();
	  config10.putAll(defaultVariant);
	  config10.put("variant", "lgp.stream.variant=afwe");
	  config10.put("case", "case10");
	  
	  
	//Invalid variant name with platform android (default config)
	  Map<String,Object>  config11 = new HashMap<>();
	  config11.putAll(defaultVariant);
	  config11.put("variant", "lgp.stream.variant=afwe");
	  config11.put("platform", "android");
	  config11.put("case", "case11");
	  
	  
	//Invalid variant name with platform ios (default config)
	  Map<String,Object>  config12 = new HashMap<>();
	  config12.putAll(defaultVariant);
	  config12.put("variant", "lgp.stream.variant=afewabva");
	  config12.put("platform", "ios");
	  config12.put("case", "case12");
	  
	  
	//null variant name with platform android (default config)
	  Map<String,Object>  config13 = new HashMap<>();
	  config13.putAll(defaultVariant);
	  config13.put("variant", null);
	  config13.put("platform", "android");
	  config13.put("case", "case13");
	  
	  
	//null variant name with platform ios (default config)
	  Map<String,Object>  config14 = new HashMap<>();
	  config14.putAll(defaultVariant);
	  config14.put("variant", null);
	  config14.put("platform", "ios");
	  config14.put("case", "case14");
	  
	//integer variant name with platform android (default config)
	  Map<String,Object>  config15 = new HashMap<>();
	  config15.putAll(defaultVariant);
	  config15.put("variant", 2);
	  config15.put("platform", "android");
	  config15.put("case", "case15");
	  
	  
	//integer variant name with platform ios (default config)
	  Map<String,Object>  config16 = new HashMap<>();
	  config16.putAll(defaultVariant);
	  config16.put("variant", 2);
	  config16.put("platform", "ios");
	  config16.put("case", "case16");
	  
	//special characters variant name with platform android (default config)
	  Map<String,Object>  config17 = new HashMap<>();
	  config17.putAll(defaultVariant);
	  config17.put("variant", "$&^%*((");
	  config17.put("platform", "android");
	  config17.put("case", "case17");
	  
	  
	//special characters variant name with platform ios (default config)
	  Map<String,Object>  config18 = new HashMap<>();
	  config18.putAll(defaultVariant);
	  config18.put("variant", "$&^%*((");
	  config18.put("platform", "ios");
	  config18.put("case", "case18");
	  

	//empty string variant name with platform android (default config)
	  Map<String,Object>  config19 = new HashMap<>();
	  config19.putAll(defaultVariant);
	  config19.put("variant", "");
	  config19.put("platform", "android");
	  config19.put("case", "case19");
	  
	  
	//empty string variant name with platform ios (default config)
	  Map<String,Object>  config20 = new HashMap<>();
	  config20.putAll(defaultVariant);
	  config20.put("variant", "");
	  config20.put("platform", "ios");
	  config20.put("case", "case20");
	  
	//leading space variant name with platform android (default config)
	  Map<String,Object>  config21 = new HashMap<>();
	  config21.putAll(variantB);
	  config21.put("variant", "  lgp.stream.variant=variantB");
	  config21.put("platform", "android");
	  config21.put("case", "case21");
	  
	  
	//leading space variant name with platform ios (default config)
	  Map<String,Object>  config22 = new HashMap<>();
	  config22.putAll(variantC);
	  config22.put("variant", " lgp.stream.variant=variantB");
	  config22.put("platform", "ios");
	  config22.put("case", "case22");
	  
	//trailing space variant name with platform android (default config)
	  Map<String,Object>  config23 = new HashMap<>();
	  config23.putAll(variantB);
	  config23.put("variant", "lgp.stream.variant=variantB   ");
	  config23.put("platform", "android");
	  config23.put("case", "case23");
	  
	  
	//trailing space variant name with platform ios (default config)
	  Map<String,Object>  config24 = new HashMap<>();
	  config24.putAll(variantC);
	  config24.put("variant", "lgp.stream.variant=variantB   ");
	  config24.put("platform", "ios");
	  config24.put("case", "case24");
	  
	  
	//trailing space variant name with platform android (default config)
	  Map<String,Object>  config25 = new HashMap<>();
	  config25.putAll(variantB);
	  config25.put("platform", "android");
	  config25.put("case", "case25");
	  
	  
	//trailing space variant name with platform ios (default config)
	  Map<String,Object>  config26 = new HashMap<>();
	  config26.putAll(variantC);
	  config26.put("platform", "ios");
	  config26.put("case", "case26");
	  
	//platform null
	  Map<String,Object>  config27 = new HashMap<>();
	  config27.putAll(defaultVariant);
	  config27.put("platform", null);
	  config27.put("case", "case27");
	  
	//platform integer
	  Map<String,Object>  config28 = new HashMap<>();
	  config28.putAll(defaultVariant);
	  config28.put("platform", 8);
	  config28.put("case", "case28");
	  
	//platform special characters
	  Map<String,Object>  config29 = new HashMap<>();
	  config29.putAll(defaultVariant);
	  config29.put("platform", "*^*%&^^");
	  config29.put("case", "case29");
	  
	//platform empty string
	  Map<String,Object>  config30 = new HashMap<>();
	  config30.putAll(defaultVariant);
	  config30.put("platform", "");
	  config30.put("case", "case30");
	  
	//platform leading space
	  Map<String,Object>  config31 = new HashMap<>();
	  config31.putAll(defaultVariant);
	  config31.put("platform", "  android");
	  config31.put("case", "case31");
	  
	//platform trailing space
	  Map<String,Object>  config32 = new HashMap<>();
	  config32.putAll(defaultVariant);
	  config32.put("platform", "android  ");
	  config32.put("case", "case32");
	  
	//platform invalid platform
	  Map<String,Object>  config33 = new HashMap<>();
	  config33.putAll(defaultVariant);
	  config33.put("platform", "dafwefs");
	  config33.put("case", "case33");
	  
	//platform fixed variant ios platform
	  Map<String,Object>  config34 = new HashMap<>();
	  config34.putAll(variantC);
	  config34.put("variant", "lgp.stream.variant=fixed");
	  config34.put("platform", "ios");
	  config34.put("case", "case34");
	  
	//Pass the request without uidx with android platform
	  Map<String,Object>  config35 = new HashMap<>();
	  config35.putAll(defaultVariant);
	  config35.put("uidx", false);
	  config35.put("variant", "lgp.stream.variant=variantA");
	  config35.put("platform", "android");
	  config35.put("case", "case35");
	  
	//Pass the request without uidx with ios platform
	  Map<String,Object>  config36 = new HashMap<>();
	  config36.putAll(defaultVariant);
	  config36.put("uidx", false);
	  config36.put("variant", "lgp.stream.variant=variantA");
	  config36.put("platform", "ios");
	  config36.put("case", "case36");
	  
	//Pass special character for uidx with platform android
	  Map<String,Object>  config37 = new HashMap<>();
	  config37.putAll(defaultVariant);
	  config37.put("uidx", "()&^*%(&(*");
	  config37.put("variant", "lgp.stream.variant=variantA");
	  config37.put("platform", "android");
	  config37.put("case", "case37");
	  
	//Pass special character for uidx with platform ios
	  Map<String,Object>  config38 = new HashMap<>();
	  config38.putAll(defaultVariant);
	  config38.put("uidx", ")(&^%^&*");
	  config38.put("variant", "lgp.stream.variant=variantA");
	  config38.put("platform", "ios");
	  config38.put("case", "case38");
	  
	//Pass null for uidx with platform android
	  Map<String,Object>  config39 = new HashMap<>();
	  config39.putAll(defaultVariant);
	  config39.put("uidx", null);
	  config39.put("variant", "lgp.stream.variant=variantA");
	  config39.put("platform", "android");
	  config39.put("case", "case39");
	  
	//Pass null for uidx with platform ios
	  Map<String,Object>  config40 = new HashMap<>();
	  config40.putAll(defaultVariant);
	  config40.put("uidx", null);
	  config40.put("variant", "lgp.stream.variant=variantA");
	  config40.put("platform", "ios");
	  config40.put("case", "case40");
	  
	//Pass blank space for uidx with platform android
	  Map<String,Object>  config41 = new HashMap<>();
	  config41.putAll(defaultVariant);
	  config41.put("uidx", " ");
	  config41.put("variant", "lgp.stream.variant=variantA");
	  config41.put("platform", "android");
	  config41.put("case", "case41");
	  
	//Pass blank space for uidx with platform ios
	  Map<String,Object>  config42 = new HashMap<>();
	  config42.putAll(defaultVariant);
	  config42.put("uidx", " ");
	  config42.put("variant", "lgp.stream.variant=variantA");
	  config42.put("platform", "ios");
	  config42.put("case", "case42");
	  
	//Pass empty string for uidx with platform android
	  Map<String,Object>  config43 = new HashMap<>();
	  config43.putAll(defaultVariant);
	  config43.put("uidx", "");
	  config43.put("variant", "lgp.stream.variant=variantA");
	  config43.put("platform", "android");
	  config43.put("case", "case43");
	  
	//Pass empty string for uidx with platform ios
	  Map<String,Object>  config44 = new HashMap<>();
	  config44.putAll(defaultVariant);
	  config44.put("uidx", "");
	  config44.put("variant", "lgp.stream.variant=variantA");
	  config44.put("platform", "ios");
	  config44.put("case", "case44");
	  
	//Pass integer for uidx with platform android
	  Map<String,Object>  config45 = new HashMap<>();
	  config45.putAll(defaultVariant);
	  config45.put("uidx", 234);
	  config45.put("variant", "lgp.stream.variant=variantA");
	  config45.put("platform", "android");
	  config45.put("case", "case45");
	  
	//Pass integer for uidx with platform ios
	  Map<String,Object>  config46 = new HashMap<>();
	  config46.putAll(defaultVariant);
	  config46.put("uidx", 123);
	  config46.put("variant", "lgp.stream.variant=variantA");
	  config46.put("platform", "ios");
	  config46.put("case", "case46");
	  
	//Pass boolean for uidx with platform android
	  Map<String,Object>  config47 = new HashMap<>();
	  config47.putAll(defaultVariant);
	  config47.put("uidx", true);
	  config47.put("variant", "lgp.stream.variant=variantA");
	  config47.put("platform", "android");
	  config47.put("case", "case47");
	  
	//Pass boolean for uidx with platform ios
	  Map<String,Object>  config48 = new HashMap<>();
	  config48.putAll(defaultVariant);
	  config48.put("uidx", true);
	  config48.put("variant", "lgp.stream.variant=variantA");
	  config48.put("platform", "ios");
	  config48.put("case", "case48");
		
	  
	  
    return new Object[][] {
      {config1},{config2},{config3},{config4},{config5},{config6},{config6_1},{config7},{config8a},{config8b},{config8c},{config8d},{config8e},{config8f},
      {config8g},{config9a},{config9b},{config9c},{config9d},{config9e},{config9f},{config9g},{config10},{config11},{config12},{config13},{config14}
      ,{config15},{config16},{config17},{config18},{config19},{config20},{config21},{config22},{config23},{config24},{config25},{config26},
      {config27},{config28},{config29},{config30},{config31},{config32},{config33},{config34},{config35},{config36},{config37},{config38},
      {config39},{config40},{config41},{config42},{config43},{config44},{config45},{config46},{config47},{config48}
    };
  }
  
  @DataProvider
  public Object[][] abFixedDP() {
	  //Default config scenario
	  String[] order={"sponsored","promoted","targeted","organic"};
	  
	  Map<String, Integer> count=new HashMap<>();
	  count.put("sponsored", 5);
	  count.put("promoted", 2);
	  count.put("organic", 20);
	  count.put("targeted", 10);
	  
	  Map<String,Object>  config1 = new HashMap<>();
	  config1.put("history", "disabled");
	  config1.put("more", "disabled");
	  config1.put("update", "disabled");
	  config1.put("randomOrder", true);
	  config1.put("variant", "lgp.stream.variant=fixed");
	  config1.put("count", count);
	  config1.put("order", order);
	  
	  
    return new Object[][] {
      {config1}
    };
  }
}
