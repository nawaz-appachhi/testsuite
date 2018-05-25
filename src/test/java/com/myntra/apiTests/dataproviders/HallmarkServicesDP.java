package com.myntra.apiTests.dataproviders;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;

public class HallmarkServicesDP {
	public class AttributeEntry{
	    	public String namespace;
	    	public String key;
	    	public Object value;
	    	public String action;
	    	public AttributeEntry(String namespace,String key,Object value,String action) {
				this.namespace=namespace;
				this.key=key;
				this.value=value;
				this.action=action;
			}
	    	
	    }

  @DataProvider
  public Object[][] updateUserAttribute() {
	  //Case1
    ArrayList<AttributeEntry> attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_ids", "Nike", "ADD"));
    Map<String, Object> case1 = new HashMap<>();
    case1.put("attributeEntryList", attributeEntryList);
    case1.put("case", "case1");
    
    //Case2
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "testBrand", "Rebook", "UPDATE"));
    Map<String, Object> case2 = new HashMap<>();
    case2.put("attributeEntryList", attributeEntryList);
    case2.put("case", "case2");
   
  //Case3
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_ids", "Nike", "DELETE"));
    Map<String, Object> case3 = new HashMap<>();
    case3.put("attributeEntryList", attributeEntryList);
    case3.put("case", "case3");
    
  //Case4
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "testBrand", "Rebook", "DELETEALL"));
    Map<String, Object> case4 = new HashMap<>();
    case4.put("attributeEntryList", attributeEntryList);
    case4.put("case", "case4");
    
  //Case5
    attributeEntryList = new ArrayList<>();
    ArrayList<AttributeEntry> clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_idList", new Object[]{"Nike","Rebook"}, "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_idList", new Object[]{"Nike","Rebook"}, "ADD"));
    Map<String, Object> case5 = new HashMap<>();
    case5.put("attributeEntryList", attributeEntryList);
    case5.put("clearAttribute", clearAttribute);
    case5.put("case", "case5");
    
  //Case6
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_idList", new Object[]{"Puma","Adidas"}, "ADD"));
    Map<String, Object> case6 = new HashMap<>();
    case6.put("attributeEntryList", attributeEntryList);
    case6.put("case", "case6");
    
  //Case7
    attributeEntryList = new ArrayList<>();
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_idList", new Object[]{"Nike","Rebook"}, "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_idList", new Object[]{"Nike","Rebook"}, "UPDATE"));
    Map<String, Object> case7 = new HashMap<>();
    case7.put("attributeEntryList", attributeEntryList);
    case7.put("clearAttribute", clearAttribute);
    case7.put("case", "case7");
    
  //Case8
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_idList", new Object[]{"Puma","Adidas"}, "UPDATE"));
    Map<String, Object> case8 = new HashMap<>();
    case8.put("attributeEntryList", attributeEntryList);
    case8.put("case", "case8");
    
  //Case9
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_idList", new Object[]{"Puma","Adidas"}, "DELETE"));
    Map<String, Object> case9 = new HashMap<>();
    case9.put("attributeEntryList", attributeEntryList);
    case9.put("case", "case9");
    
  //Case10
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_idList", new Object[]{"Puma","Adidas"}, "DELETEALL"));
    Map<String, Object> case10 = new HashMap<>();
    case10.put("attributeEntryList", attributeEntryList);
    case10.put("case", "case10");
    
    //Case11
    attributeEntryList = new ArrayList<>();
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", "Puma", "ADD"));
    Map<String, Object> case11 = new HashMap<>();
    case11.put("attributeEntryList", attributeEntryList);
    case11.put("clearAttribute", clearAttribute);
    case11.put("case", "case11");
    
  //Case12
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Puma","Adidas"}, "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2","Adidas", "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", new Object[]{"Gas","Fila"}, "UPDATE"));
    Map<String, Object> case12 = new HashMap<>();
    case12.put("attributeEntryList", attributeEntryList);
    case12.put("case", "case12");
    
  //Case13
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Puma"}, "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2","Adidas", "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", new Object[]{"Gas"}, "DELETE"));
    Map<String, Object> case13 = new HashMap<>();
    case13.put("attributeEntryList", attributeEntryList);
    case13.put("case", "case13");
    
  //Case14
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Puma"}, "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2","Adidas", "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", new Object[]{"Gas"}, "DELETEALL"));
    Map<String, Object> case14 = new HashMap<>();
    case14.put("attributeEntryList", attributeEntryList);
    case14.put("case", "case14");
    
  //Case15
    attributeEntryList = new ArrayList<>();
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", "Puma", "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id4", new Object[]{"Nike","Adidas"}, "UPDATE"));
    Map<String, Object> case15 = new HashMap<>();
    case15.put("attributeEntryList", attributeEntryList);
    case15.put("clearAttribute", clearAttribute);
    case15.put("case", "case15");
    
  //Case16
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2",new Object[]{"Nike",}, "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", "Puma", "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id4", new Object[]{"Gas"}, "DELETEALL"));
    Map<String, Object> case16 = new HashMap<>();
    case16.put("attributeEntryList", attributeEntryList);
    case16.put("case", "case16");
    
    
  //Case17
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "DELETEALL"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id5", "Nike", "UPDATE"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id6", new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", "Puma", "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id4", new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id5", "Nike", "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id6", new Object[]{"Nike"}, "DELETE"));
    Map<String, Object> case17 = new HashMap<>();
    case17.put("attributeEntryList", attributeEntryList);
    case17.put("clearAttribute", clearAttribute);
    case17.put("case", "case17");
    
  //Case18
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "DELETEALL"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id5", "Nike", "UPDATE"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id6", new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", "Puma", "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id4", new Object[]{"Adidas"}, "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id5", "Nike", "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id6", new Object[]{"Nike"}, "DELETEALL"));
    Map<String, Object> case18 = new HashMap<>();
    case18.put("attributeEntryList", attributeEntryList);
    case18.put("clearAttribute", clearAttribute);
    case18.put("case", "case18");
    
  //Case19
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id3", "Puma", "UPDATE"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id4", new Object[]{"Adidas","NIKE"}, "UPDATE"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id5", "Nike", "UPDATE"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id6", new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", "Puma", "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id4", new Object[]{"Adidas"}, "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id5", "Nike", "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id6", new Object[]{"Nike"}, "DELETEALL"));
    Map<String, Object> case19 = new HashMap<>();
    case19.put("attributeEntryList", attributeEntryList);
    case19.put("clearAttribute", clearAttribute);
    case19.put("case", "case19");
    
  //Case20
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id2", "Puma", "DELETEALL"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id5", "Nike", "UPDATE"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id6", new Object[]{"Nike","Puma"}, "UPDATE"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id7", "Nike", "UPDATE"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id8", new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", "Puma", "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id4", new Object[]{"Adidas"}, "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id5", "Nike", "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id6", new Object[]{"Nike"}, "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id7", "Nike", "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id8", new Object[]{"Nike"}, "DELETEALL"));
    Map<String, Object> case20 = new HashMap<>();
    case20.put("attributeEntryList", attributeEntryList);
    case20.put("clearAttribute", clearAttribute);
    case20.put("case", "case20");
    
  //Case21
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Adidas"}, "ADD"));
    Map<String, Object> case21 = new HashMap<>();
    case21.put("attributeEntryList", attributeEntryList);
    case21.put("clearAttribute", clearAttribute);
    case21.put("case", "case21");
    
  //Case22
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Adidas"}, "UPDATE"));
    Map<String, Object> case22 = new HashMap<>();
    case22.put("attributeEntryList", attributeEntryList);
    case22.put("clearAttribute", clearAttribute);
    case22.put("case", "case22");
    
  //Case23
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1","Adidas", "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Adidas", "ADD"));
    Map<String, Object> case23 = new HashMap<>();
    case23.put("attributeEntryList", attributeEntryList);
    case23.put("clearAttribute", clearAttribute);
    case23.put("case", "case23");
    
  //Case24
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1","Adidas", "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Adidas", "UPDATE"));
    Map<String, Object> case24 = new HashMap<>();
    case24.put("attributeEntryList", attributeEntryList);
    case24.put("clearAttribute", clearAttribute);
    case24.put("case", "case24");
    
  //Case25
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Nike"}, "ADD"));
    Map<String, Object> case25 = new HashMap<>();
    case25.put("attributeEntryList", attributeEntryList);
    case25.put("case", "case25");
    
  //Case26
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Nike"}, "UPDATE"));
    Map<String, Object> case26 = new HashMap<>();
    case26.put("attributeEntryList", attributeEntryList);
    case26.put("case", "case26");
    
  //Case27
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1",new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Rebook","Puma"}, "DELETE"));
    Map<String, Object> case27 = new HashMap<>();
    case27.put("attributeEntryList", attributeEntryList);
    case27.put("clearAttribute", clearAttribute);
    case27.put("case", "case27");
    
  //Case28
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1",new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Rebook","Puma"}, "DELETEALL"));
    Map<String, Object> case28 = new HashMap<>();
    case28.put("attributeEntryList", attributeEntryList);
    case28.put("clearAttribute", clearAttribute);
    case28.put("case", "case28");
    
  //Case29
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1",new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Adidas", "ADD"));
    Map<String, Object> case29 = new HashMap<>();
    case29.put("attributeEntryList", attributeEntryList);
    case29.put("clearAttribute", clearAttribute);
    case29.put("case", "case29");
    
  //Case30
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1",new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Adidas", "UPDATE"));
    Map<String, Object> case30 = new HashMap<>();
    case30.put("attributeEntryList", attributeEntryList);
    case30.put("clearAttribute", clearAttribute);
    case30.put("case", "case30");
    
  //Case31
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1",new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Adidas", "DELETE"));
    Map<String, Object> case31 = new HashMap<>();
    case31.put("attributeEntryList", attributeEntryList);
    case31.put("clearAttribute", clearAttribute);
    case31.put("case", "case31");
    
  //Case32
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1",new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Adidas", "DELETEALL"));
    Map<String, Object> case32 = new HashMap<>();
    case32.put("attributeEntryList", attributeEntryList);
    case32.put("clearAttribute", clearAttribute);
    case32.put("case", "case32");
    
  //Case33
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1","Adidas", "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Adidas"}, "ADD"));
    Map<String, Object> case33 = new HashMap<>();
    case33.put("attributeEntryList", attributeEntryList);
    case33.put("clearAttribute", clearAttribute);
    case33.put("case", "case33");
    
  //Case34
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1","Adidas", "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Adidas"}, "UPDATE"));
    Map<String, Object> case34 = new HashMap<>();
    case34.put("attributeEntryList", attributeEntryList);
    case34.put("clearAttribute", clearAttribute);
    case34.put("case", "case34");
    
  //Case35
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1","Adidas", "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Adidas"}, "DELETE"));
    Map<String, Object> case35 = new HashMap<>();
    case35.put("attributeEntryList", attributeEntryList);
    case35.put("clearAttribute", clearAttribute);
    case35.put("case", "case35");
    
  //Case36
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1","Adidas", "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Adidas"}, "DELETEALL"));
    Map<String, Object> case36 = new HashMap<>();
    case36.put("attributeEntryList", attributeEntryList);
    case36.put("clearAttribute", clearAttribute);
    case36.put("case", "case36");
    
  //Case37
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "abc1123", "ADD"));
    Map<String, Object> case37 = new HashMap<>();
    case37.put("attributeEntryList", attributeEntryList);
    case37.put("case", "case37");
    
  //Case38
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "abc1123", "UPDATE"));
    Map<String, Object> case38 = new HashMap<>();
    case38.put("attributeEntryList", attributeEntryList);
    case38.put("case", "case38");
    
  //Case39
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "abc1123", "DELETE"));
    Map<String, Object> case39 = new HashMap<>();
    case39.put("attributeEntryList", attributeEntryList);
    case39.put("case", "case39");
    
  //Case40
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "abc1123", "DELETEALL"));
    Map<String, Object> case40 = new HashMap<>();
    case40.put("attributeEntryList", attributeEntryList);
    case40.put("case", "case40");
    
  //Case41
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"abc1123","123vae"}, "ADD"));
    Map<String, Object> case41 = new HashMap<>();
    case41.put("attributeEntryList", attributeEntryList);
    case41.put("case", "case41");
    
  //Case42
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"abc1123","123vae"}, "UPDATE"));
    Map<String, Object> case42 = new HashMap<>();
    case42.put("attributeEntryList", attributeEntryList);
    case42.put("case", "case42");
    
  //Case43
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"abc1123","123vae"}, "DELETE"));
    Map<String, Object> case43 = new HashMap<>();
    case43.put("attributeEntryList", attributeEntryList);
    case43.put("case", "case43");
    
  //Case44
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"abc1123","123vae"}, "DELETEALL"));
    Map<String, Object> case44 = new HashMap<>();
    case44.put("attributeEntryList", attributeEntryList);
    case44.put("case", "case44");
    
  //Case45
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "#####%!@", "ADD"));
    Map<String, Object> case45 = new HashMap<>();
    case45.put("attributeEntryList", attributeEntryList);
    case45.put("case", "case45");
    
  //Case46
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "#####%!@", "UPDATE"));
    Map<String, Object> case46 = new HashMap<>();
    case46.put("attributeEntryList", attributeEntryList);
    case46.put("case", "case46");
    
  //Case47
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "#####%!@", "DELETE"));
    Map<String, Object> case47 = new HashMap<>();
    case47.put("attributeEntryList", attributeEntryList);
    case47.put("case", "case47");
    
  //Case48
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "#####%!@", "DELETEALL"));
    Map<String, Object> case48 = new HashMap<>();
    case48.put("attributeEntryList", attributeEntryList);
    case48.put("case", "case48");
    
  //Case49
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"(*^&$%","(&*&%*^"}, "ADD"));
    Map<String, Object> case49 = new HashMap<>();
    case49.put("attributeEntryList", attributeEntryList);
    case49.put("case", "case49");
    
  //Case50
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"(*^&$%","(&*&%*^"}, "UPDATE"));
    Map<String, Object> case50 = new HashMap<>();
    case50.put("attributeEntryList", attributeEntryList);
    case50.put("case", "case50");
    
  //Case51
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"(*^&$%","(&*&%*^"}, "DELETE"));
    Map<String, Object> case51 = new HashMap<>();
    case51.put("attributeEntryList", attributeEntryList);
    case51.put("case", "case51");
    
  //Case52
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"(*^&$%","(&*&%*^"}, "DELETEALL"));
    Map<String, Object> case52 = new HashMap<>();
    case52.put("attributeEntryList", attributeEntryList);
    case52.put("case", "case52");
    
  //Case53
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{12,"Nike"," ", true,"*&E%^%&"}, "ADD"));
    Map<String, Object> case53 = new HashMap<>();
    case53.put("attributeEntryList", attributeEntryList);
    case53.put("case", "case53");
    
  //Case54
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{12,"Nike"," ", true,"*&E%^%&"}, "UPDATE"));
    Map<String, Object> case54 = new HashMap<>();
    case54.put("attributeEntryList", attributeEntryList);
    case54.put("case", "case54");
    
  //Case55
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{12,"Nike"," ", true,"*&E%^%&"}, "DELETE"));
    Map<String, Object> case55 = new HashMap<>();
    case55.put("attributeEntryList", attributeEntryList);
    case55.put("case", "case55");
    
  //Case56
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{12,"Nike"," ", true,"*&E%^%&"}, "DELETEALL"));
    Map<String, Object> case56 = new HashMap<>();
    case56.put("attributeEntryList", attributeEntryList);
    case56.put("case", "case56");
    
  //Case57
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1",new Object[]{"Adidas","Nike"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{12,23}, "ADD"));
    Map<String, Object> case57 = new HashMap<>();
    case57.put("attributeEntryList", attributeEntryList);
    case57.put("case", "case57");
    
  //Case58
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1",new Object[]{12,23}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Puma"}, "ADD"));
    Map<String, Object> case58 = new HashMap<>();
    case58.put("attributeEntryList", attributeEntryList);
    case58.put("case", "case58");
    
  
    
  //Case59
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", true, "ADD"));
    Map<String, Object> case59 = new HashMap<>();
    case59.put("attributeEntryList", attributeEntryList);
    case59.put("case", "case59");
    
  //Case60
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", true, "UPDATE"));
    Map<String, Object> case60 = new HashMap<>();
    case60.put("attributeEntryList", attributeEntryList);
    case60.put("case", "case60");
    
  //Case61
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", true, "DELETE"));
    Map<String, Object> case61 = new HashMap<>();
    case61.put("attributeEntryList", attributeEntryList);
    case61.put("case", "case61");
    
  //Case62
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", true, "DELETEALL"));
    Map<String, Object> case62 = new HashMap<>();
    case62.put("attributeEntryList", attributeEntryList);
    case62.put("case", "case62");
    
  //Case63
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{true,false}, "ADD"));
    Map<String, Object> case63 = new HashMap<>();
    case63.put("attributeEntryList", attributeEntryList);
    case63.put("case", "case63");
    
  //Case64
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{true,false}, "UPDATE"));
    Map<String, Object> case64 = new HashMap<>();
    case64.put("attributeEntryList", attributeEntryList);
    case64.put("case", "case64");
    
  //Case65
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{true,false}, "DELETE"));
    Map<String, Object> case65 = new HashMap<>();
    case65.put("attributeEntryList", attributeEntryList);
    case65.put("case", "case65");
    
  //Case66
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{true,false}, "DELETEALL"));
    Map<String, Object> case66 = new HashMap<>();
    case66.put("attributeEntryList", attributeEntryList);
    case66.put("case", "case66");
    
  //Case67
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", null, "ADD"));
    Map<String, Object> case67 = new HashMap<>();
    case67.put("attributeEntryList", attributeEntryList);
    case67.put("case", "case67");
    
  //Case68
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", null, "UPDATE"));
    Map<String, Object> case68 = new HashMap<>();
    case68.put("attributeEntryList", attributeEntryList);
    case68.put("case", "case68");
    
  //Case69
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", null, "DELETE"));
    Map<String, Object> case69 = new HashMap<>();
    case69.put("attributeEntryList", attributeEntryList);
    case69.put("case", "case69");
    
  //Case70
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", null, "DELETEALL"));
    Map<String, Object> case70 = new HashMap<>();
    case70.put("attributeEntryList", attributeEntryList);
    case70.put("case", "case70");
    
  //Case71
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{null,null}, "ADD"));
    Map<String, Object> case71 = new HashMap<>();
    case71.put("attributeEntryList", attributeEntryList);
    case71.put("case", "case71");
    
  //Case72
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{null,null}, "UPDATE"));
    Map<String, Object> case72 = new HashMap<>();
    case72.put("attributeEntryList", attributeEntryList);
    case72.put("case", "case72");
    
  //Case73
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{null,null}, "DELETE"));
    Map<String, Object> case73 = new HashMap<>();
    case73.put("attributeEntryList", attributeEntryList);
    case73.put("case", "case73");
    
  //Case74
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{null,null}, "DELETEALL"));
    Map<String, Object> case74 = new HashMap<>();
    case74.put("attributeEntryList", attributeEntryList);
    case74.put("case", "case74");
    
    return(new Object[][]{{case1},{case2},{case3},{case4},{case5},{case6},{case7},{case8},{case9},{case10}
    ,{case11},{case12},{case13},{case14},{case15},{case16},{case17},{case18},{case19},{case20},{case21},
    {case21},{case23},{case24},{case25},{case26},{case27},{case28},{case29},{case30},{case31},{case32},
    {case33},{case34},{case35},{case36},{case37},{case38},{case39},{case40},{case41},{case42},{case43},
    {case44},{case45},{case46},{case47},{case48},{case49},{case50},{case51},{case52},{case53},{case54},
    {case55},{case56},{case57},{case58},{case59},{case60},{case61},{case62},{case63},{case64},{case65},
    {case66},{case67},{case68},{case69},{case70},{case71},{case72},{case73},{case74}});
  }
  

  @DataProvider
  public Object[][] updateDeviceAttribute() {
	  //Case1
    ArrayList<AttributeEntry> attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_ids", "Nike", "ADD"));
    Map<String, Object> case1 = new HashMap<>();
    case1.put("attributeEntryList", attributeEntryList);
    case1.put("case", "case1");
    
    //Case2
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "testBrand", "Rebook", "UPDATE"));
    Map<String, Object> case2 = new HashMap<>();
    case2.put("attributeEntryList", attributeEntryList);
    case2.put("case", "case2");
   
  //Case3
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_ids", "Nike", "DELETE"));
    Map<String, Object> case3 = new HashMap<>();
    case3.put("attributeEntryList", attributeEntryList);
    case3.put("case", "case3");
    
  //Case4
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "testBrand", "Rebook", "DELETEALL"));
    Map<String, Object> case4 = new HashMap<>();
    case4.put("attributeEntryList", attributeEntryList);
    case4.put("case", "case4");
    
  //Case5
    attributeEntryList = new ArrayList<>();
    ArrayList<AttributeEntry> clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_idList", new Object[]{"Nike","Rebook"}, "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_idList", new Object[]{"Nike","Rebook"}, "ADD"));
    Map<String, Object> case5 = new HashMap<>();
    case5.put("attributeEntryList", attributeEntryList);
    case5.put("clearAttribute", clearAttribute);
    case5.put("case", "case5");
    
  //Case6
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_idList", new Object[]{"Puma","Adidas"}, "ADD"));
    Map<String, Object> case6 = new HashMap<>();
    case6.put("attributeEntryList", attributeEntryList);
    case6.put("case", "case6");
    
  //Case7
    attributeEntryList = new ArrayList<>();
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_idList", new Object[]{"Nike","Rebook"}, "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_idList", new Object[]{"Nike","Rebook"}, "UPDATE"));
    Map<String, Object> case7 = new HashMap<>();
    case7.put("attributeEntryList", attributeEntryList);
    case7.put("clearAttribute", clearAttribute);
    case7.put("case", "case7");
    
  //Case8
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_idList", new Object[]{"Puma","Adidas"}, "UPDATE"));
    Map<String, Object> case8 = new HashMap<>();
    case8.put("attributeEntryList", attributeEntryList);
    case8.put("case", "case8");
    
  //Case9
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_idList", new Object[]{"Puma","Adidas"}, "DELETE"));
    Map<String, Object> case9 = new HashMap<>();
    case9.put("attributeEntryList", attributeEntryList);
    case9.put("case", "case9");
    
  //Case10
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_idList", new Object[]{"Puma","Adidas"}, "DELETEALL"));
    Map<String, Object> case10 = new HashMap<>();
    case10.put("attributeEntryList", attributeEntryList);
    case10.put("case", "case10");
    
    //Case11
    attributeEntryList = new ArrayList<>();
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", "Puma", "ADD"));
    Map<String, Object> case11 = new HashMap<>();
    case11.put("attributeEntryList", attributeEntryList);
    case11.put("clearAttribute", clearAttribute);
    case11.put("case", "case11");
    
  //Case12
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Puma","Adidas"}, "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2","Adidas", "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", new Object[]{"Gas","Fila"}, "UPDATE"));
    Map<String, Object> case12 = new HashMap<>();
    case12.put("attributeEntryList", attributeEntryList);
    case12.put("case", "case12");
    
  //Case13
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Puma"}, "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2","Adidas", "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", new Object[]{"Gas"}, "DELETE"));
    Map<String, Object> case13 = new HashMap<>();
    case13.put("attributeEntryList", attributeEntryList);
    case13.put("case", "case13");
    
  //Case14
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Puma"}, "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2","Adidas", "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", new Object[]{"Gas"}, "DELETEALL"));
    Map<String, Object> case14 = new HashMap<>();
    case14.put("attributeEntryList", attributeEntryList);
    case14.put("case", "case14");
    
  //Case15
    attributeEntryList = new ArrayList<>();
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", "Puma", "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id4", new Object[]{"Nike","Adidas"}, "UPDATE"));
    Map<String, Object> case15 = new HashMap<>();
    case15.put("attributeEntryList", attributeEntryList);
    case15.put("clearAttribute", clearAttribute);
    case15.put("case", "case15");
    
  //Case16
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2",new Object[]{"Nike",}, "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", "Puma", "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id4", new Object[]{"Gas"}, "DELETEALL"));
    Map<String, Object> case16 = new HashMap<>();
    case16.put("attributeEntryList", attributeEntryList);
    case16.put("case", "case16");
    
    
  //Case17
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "DELETEALL"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id5", "Nike", "UPDATE"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id6", new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", "Puma", "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id4", new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id5", "Nike", "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id6", new Object[]{"Nike"}, "DELETE"));
    Map<String, Object> case17 = new HashMap<>();
    case17.put("attributeEntryList", attributeEntryList);
    case17.put("clearAttribute", clearAttribute);
    case17.put("case", "case17");
    
  //Case18
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "DELETEALL"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id5", "Nike", "UPDATE"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id6", new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", "Puma", "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id4", new Object[]{"Adidas"}, "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id5", "Nike", "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id6", new Object[]{"Nike"}, "DELETEALL"));
    Map<String, Object> case18 = new HashMap<>();
    case18.put("attributeEntryList", attributeEntryList);
    case18.put("clearAttribute", clearAttribute);
    case18.put("case", "case18");
    
  //Case19
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id3", "Puma", "UPDATE"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id4", new Object[]{"Adidas","NIKE"}, "UPDATE"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id5", "Nike", "UPDATE"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id6", new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", "Puma", "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id4", new Object[]{"Adidas"}, "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id5", "Nike", "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id6", new Object[]{"Nike"}, "DELETEALL"));
    Map<String, Object> case19 = new HashMap<>();
    case19.put("attributeEntryList", attributeEntryList);
    case19.put("clearAttribute", clearAttribute);
    case19.put("case", "case19");
    
  //Case20
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id2", "Puma", "DELETEALL"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id5", "Nike", "UPDATE"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id6", new Object[]{"Nike","Puma"}, "UPDATE"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id7", "Nike", "UPDATE"));
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id8", new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id2", new Object[]{"Nike","Adidas"}, "ADD"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id3", "Puma", "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id4", new Object[]{"Adidas"}, "UPDATE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id5", "Nike", "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id6", new Object[]{"Nike"}, "DELETE"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id7", "Nike", "DELETEALL"));
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id8", new Object[]{"Nike"}, "DELETEALL"));
    Map<String, Object> case20 = new HashMap<>();
    case20.put("attributeEntryList", attributeEntryList);
    case20.put("clearAttribute", clearAttribute);
    case20.put("case", "case20");
    
  //Case21
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Adidas"}, "ADD"));
    Map<String, Object> case21 = new HashMap<>();
    case21.put("attributeEntryList", attributeEntryList);
    case21.put("clearAttribute", clearAttribute);
    case21.put("case", "case21");
    
    
  //Case22
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Adidas"}, "UPDATE"));
    Map<String, Object> case22 = new HashMap<>();
    case22.put("attributeEntryList", attributeEntryList);
    case22.put("clearAttribute", clearAttribute);
    case22.put("case", "case22");
    
  //Case23
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1","Adidas", "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Adidas", "ADD"));
    Map<String, Object> case23 = new HashMap<>();
    case23.put("attributeEntryList", attributeEntryList);
    case23.put("clearAttribute", clearAttribute);
    case23.put("case", "case23");
    
  //Case24
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1","Adidas", "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Adidas", "UPDATE"));
    Map<String, Object> case24 = new HashMap<>();
    case24.put("attributeEntryList", attributeEntryList);
    case24.put("clearAttribute", clearAttribute);
    case24.put("case", "case24");
    
  //Case25
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Nike"}, "ADD"));
    Map<String, Object> case25 = new HashMap<>();
    case25.put("attributeEntryList", attributeEntryList);
    case25.put("case", "case25");
    
  //Case26
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Nike"}, "UPDATE"));
    Map<String, Object> case26 = new HashMap<>();
    case26.put("attributeEntryList", attributeEntryList);
    case26.put("case", "case26");
    
  //Case27
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1",new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Rebook","Puma"}, "DELETE"));
    Map<String, Object> case27 = new HashMap<>();
    case27.put("attributeEntryList", attributeEntryList);
    case27.put("clearAttribute", clearAttribute);
    case27.put("case", "case27");
    
  //Case28
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1",new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Rebook","Puma"}, "DELETEALL"));
    Map<String, Object> case28 = new HashMap<>();
    case28.put("attributeEntryList", attributeEntryList);
    case28.put("clearAttribute", clearAttribute);
    case28.put("case", "case28");
    
  //Case29
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1",new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Adidas", "ADD"));
    Map<String, Object> case29 = new HashMap<>();
    case29.put("attributeEntryList", attributeEntryList);
    case29.put("clearAttribute", clearAttribute);
    case29.put("case", "case29");
    
  //Case30
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1",new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Adidas", "UPDATE"));
    Map<String, Object> case30 = new HashMap<>();
    case30.put("attributeEntryList", attributeEntryList);
    case30.put("clearAttribute", clearAttribute);
    case30.put("case", "case30");
    
  //Case31
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1",new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Adidas", "DELETE"));
    Map<String, Object> case31 = new HashMap<>();
    case31.put("attributeEntryList", attributeEntryList);
    case31.put("clearAttribute", clearAttribute);
    case31.put("case", "case31");
    
  //Case32
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1",new Object[]{"Nike","Adidas"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Adidas", "DELETEALL"));
    Map<String, Object> case32 = new HashMap<>();
    case32.put("attributeEntryList", attributeEntryList);
    case32.put("clearAttribute", clearAttribute);
    case32.put("case", "case32");
    
  //Case33
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1","Adidas", "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Adidas"}, "ADD"));
    Map<String, Object> case33 = new HashMap<>();
    case33.put("attributeEntryList", attributeEntryList);
    case33.put("clearAttribute", clearAttribute);
    case33.put("case", "case33");
    
  //Case34
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1","Adidas", "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Adidas"}, "UPDATE"));
    Map<String, Object> case34 = new HashMap<>();
    case34.put("attributeEntryList", attributeEntryList);
    case34.put("clearAttribute", clearAttribute);
    case34.put("case", "case34");
    
  //Case35
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1","Adidas", "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Adidas"}, "DELETE"));
    Map<String, Object> case35 = new HashMap<>();
    case35.put("attributeEntryList", attributeEntryList);
    case35.put("clearAttribute", clearAttribute);
    case35.put("case", "case35");
    
  //Case36
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1","Adidas", "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Adidas"}, "DELETEALL"));
    Map<String, Object> case36 = new HashMap<>();
    case36.put("attributeEntryList", attributeEntryList);
    case36.put("clearAttribute", clearAttribute);
    case36.put("case", "case36");
    
  //Case37
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "abc1123", "ADD"));
    Map<String, Object> case37 = new HashMap<>();
    case37.put("attributeEntryList", attributeEntryList);
    case37.put("case", "case37");
    
  //Case38
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "abc1123", "UPDATE"));
    Map<String, Object> case38 = new HashMap<>();
    case38.put("attributeEntryList", attributeEntryList);
    case38.put("case", "case38");
    
  //Case39
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "abc1123", "DELETE"));
    Map<String, Object> case39 = new HashMap<>();
    case39.put("attributeEntryList", attributeEntryList);
    case39.put("case", "case39");
    
  //Case40
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "abc1123", "DELETEALL"));
    Map<String, Object> case40 = new HashMap<>();
    case40.put("attributeEntryList", attributeEntryList);
    case40.put("case", "case40");
    
  //Case41
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"abc1123","123vae"}, "ADD"));
    Map<String, Object> case41 = new HashMap<>();
    case41.put("attributeEntryList", attributeEntryList);
    case41.put("case", "case41");
    
  //Case42
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"abc1123","123vae"}, "UPDATE"));
    Map<String, Object> case42 = new HashMap<>();
    case42.put("attributeEntryList", attributeEntryList);
    case42.put("case", "case42");
    
  //Case43
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"abc1123","123vae"}, "DELETE"));
    Map<String, Object> case43 = new HashMap<>();
    case43.put("attributeEntryList", attributeEntryList);
    case43.put("case", "case43");
    
  //Case44
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"abc1123","123vae"}, "DELETEALL"));
    Map<String, Object> case44 = new HashMap<>();
    case44.put("attributeEntryList", attributeEntryList);
    case44.put("case", "case44");
    
  //Case45
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "#####%!@", "ADD"));
    Map<String, Object> case45 = new HashMap<>();
    case45.put("attributeEntryList", attributeEntryList);
    case45.put("case", "case45");
    
  //Case46
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "#####%!@", "UPDATE"));
    Map<String, Object> case46 = new HashMap<>();
    case46.put("attributeEntryList", attributeEntryList);
    case46.put("case", "case46");
    
  //Case47
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "#####%!@", "DELETE"));
    Map<String, Object> case47 = new HashMap<>();
    case47.put("attributeEntryList", attributeEntryList);
    case47.put("case", "case47");
    
  //Case48
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "#####%!@", "DELETEALL"));
    Map<String, Object> case48 = new HashMap<>();
    case48.put("attributeEntryList", attributeEntryList);
    case48.put("case", "case48");
    
  //Case49
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"(*^&$%","(&*&%*^"}, "ADD"));
    Map<String, Object> case49 = new HashMap<>();
    case49.put("attributeEntryList", attributeEntryList);
    case49.put("case", "case49");
    
  //Case50
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"(*^&$%","(&*&%*^"}, "UPDATE"));
    Map<String, Object> case50 = new HashMap<>();
    case50.put("attributeEntryList", attributeEntryList);
    case50.put("case", "case50");
    
  //Case51
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"(*^&$%","(&*&%*^"}, "DELETE"));
    Map<String, Object> case51 = new HashMap<>();
    case51.put("attributeEntryList", attributeEntryList);
    case51.put("case", "case51");
    
  //Case52
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"(*^&$%","(&*&%*^"}, "DELETEALL"));
    Map<String, Object> case52 = new HashMap<>();
    case52.put("attributeEntryList", attributeEntryList);
    case52.put("case", "case52");
    
  //Case53
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{12,"Nike"," ", true,"*&E%^%&"}, "ADD"));
    Map<String, Object> case53 = new HashMap<>();
    case53.put("attributeEntryList", attributeEntryList);
    case53.put("case", "case53");
    
  //Case54
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{12,"Nike"," ", true,"*&E%^%&"}, "UPDATE"));
    Map<String, Object> case54 = new HashMap<>();
    case54.put("attributeEntryList", attributeEntryList);
    case54.put("case", "case54");
    
  //Case55
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{12,"Nike"," ", true,"*&E%^%&"}, "DELETE"));
    Map<String, Object> case55 = new HashMap<>();
    case55.put("attributeEntryList", attributeEntryList);
    case55.put("case", "case55");
    
  //Case56
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{12,"Nike"," ", true,"*&E%^%&"}, "DELETEALL"));
    Map<String, Object> case56 = new HashMap<>();
    case56.put("attributeEntryList", attributeEntryList);
    case56.put("case", "case56");
    
  //Case57
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1",new Object[]{"Adidas","Nike"}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{12,23}, "ADD"));
    Map<String, Object> case57 = new HashMap<>();
    case57.put("attributeEntryList", attributeEntryList);
    case57.put("case", "case57");
    
  //Case58
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1",new Object[]{12,23}, "UPDATE"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"Nike","Puma"}, "ADD"));
    Map<String, Object> case58 = new HashMap<>();
    case58.put("attributeEntryList", attributeEntryList);
    case58.put("case", "case58");
    
  
    
  //Case59
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", true, "ADD"));
    Map<String, Object> case59 = new HashMap<>();
    case59.put("attributeEntryList", attributeEntryList);
    case59.put("case", "case59");
    
  //Case60
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", true, "UPDATE"));
    Map<String, Object> case60 = new HashMap<>();
    case60.put("attributeEntryList", attributeEntryList);
    case60.put("case", "case60");
    
  //Case61
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", true, "DELETE"));
    Map<String, Object> case61 = new HashMap<>();
    case61.put("attributeEntryList", attributeEntryList);
    case61.put("case", "case61");
    
  //Case62
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", true, "DELETEALL"));
    Map<String, Object> case62 = new HashMap<>();
    case62.put("attributeEntryList", attributeEntryList);
    case62.put("case", "case62");
    
  //Case63
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{true,false}, "ADD"));
    Map<String, Object> case63 = new HashMap<>();
    case63.put("attributeEntryList", attributeEntryList);
    case63.put("case", "case63");
    
  //Case64
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{true,false}, "UPDATE"));
    Map<String, Object> case64 = new HashMap<>();
    case64.put("attributeEntryList", attributeEntryList);
    case64.put("case", "case64");
    
  //Case65
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{true,false}, "DELETE"));
    Map<String, Object> case65 = new HashMap<>();
    case65.put("attributeEntryList", attributeEntryList);
    case65.put("case", "case65");
    
  //Case66
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{true,false}, "DELETEALL"));
    Map<String, Object> case66 = new HashMap<>();
    case66.put("attributeEntryList", attributeEntryList);
    case66.put("case", "case66");
    
  //Case67
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", null, "ADD"));
    Map<String, Object> case67 = new HashMap<>();
    case67.put("attributeEntryList", attributeEntryList);
    case67.put("case", "case67");
    
  //Case68
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", null, "UPDATE"));
    Map<String, Object> case68 = new HashMap<>();
    case68.put("attributeEntryList", attributeEntryList);
    case68.put("case", "case68");
    
  //Case69
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", null, "DELETE"));
    Map<String, Object> case69 = new HashMap<>();
    case69.put("attributeEntryList", attributeEntryList);
    case69.put("case", "case69");
    
  //Case70
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", null, "DELETEALL"));
    Map<String, Object> case70 = new HashMap<>();
    case70.put("attributeEntryList", attributeEntryList);
    case70.put("case", "case70");
    
  //Case71
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{null,null}, "ADD"));
    Map<String, Object> case71 = new HashMap<>();
    case71.put("attributeEntryList", attributeEntryList);
    case71.put("case", "case71");
    
  //Case72
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{null,null}, "UPDATE"));
    Map<String, Object> case72 = new HashMap<>();
    case72.put("attributeEntryList", attributeEntryList);
    case72.put("case", "case72");
    
  //Case73
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{null,null}, "DELETE"));
    Map<String, Object> case73 = new HashMap<>();
    case73.put("attributeEntryList", attributeEntryList);
    case73.put("case", "case73");
    
  //Case74
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{null,null}, "DELETEALL"));
    Map<String, Object> case74 = new HashMap<>();
    case74.put("attributeEntryList", attributeEntryList);
    case74.put("case", "case74");
    
  //Case75
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "", "ADD"));
    Map<String, Object> case75 = new HashMap<>();
    case75.put("attributeEntryList", attributeEntryList);
    case75.put("case", "case75");
    
  //Case76
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "", "UPDATE"));
    Map<String, Object> case76 = new HashMap<>();
    case76.put("attributeEntryList", attributeEntryList);
    case76.put("case", "case76");
    
  //Case77
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "", "DELETE"));
    Map<String, Object> case77 = new HashMap<>();
    case77.put("attributeEntryList", attributeEntryList);
    case77.put("case", "case77");
    
  //Case78
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "", "DELETEALL"));
    Map<String, Object> case78 = new HashMap<>();
    case78.put("attributeEntryList", attributeEntryList);
    case78.put("case", "case78");
    
  //Case79
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"",""}, "ADD"));
    Map<String, Object> case79 = new HashMap<>();
    case79.put("attributeEntryList", attributeEntryList);
    case79.put("case", "case79");
    
  //Case80
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"",""}, "UPDATE"));
    Map<String, Object> case80 = new HashMap<>();
    case80.put("attributeEntryList", attributeEntryList);
    case80.put("case", "case80");
    
  //Case81
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"",""}, "DELETE"));
    Map<String, Object> case81 = new HashMap<>();
    case81.put("attributeEntryList", attributeEntryList);
    case81.put("case", "case81");
    
  //Case82
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{"",""}, "DELETEALL"));
    Map<String, Object> case82 = new HashMap<>();
    case82.put("attributeEntryList", attributeEntryList);
    case82.put("case", "case82");
    
  //Case83
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", " ", "ADD"));
    Map<String, Object> case83 = new HashMap<>();
    case83.put("attributeEntryList", attributeEntryList);
    case83.put("case", "case83");
    
  //Case84
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", " ", "UPDATE"));
    Map<String, Object> case84 = new HashMap<>();
    case84.put("attributeEntryList", attributeEntryList);
    case84.put("case", "case84");
    
  //Case85
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", " ", "DELETE"));
    Map<String, Object> case85 = new HashMap<>();
    case85.put("attributeEntryList", attributeEntryList);
    case85.put("case", "case85");
    
  //Case86
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", " ", "DELETEALL"));
    Map<String, Object> case86 = new HashMap<>();
    case86.put("attributeEntryList", attributeEntryList);
    case86.put("case", "case86");
    
  //Case87
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{" "," "}, "ADD"));
    Map<String, Object> case87 = new HashMap<>();
    case87.put("attributeEntryList", attributeEntryList);
    case87.put("case", "case87");
    
  //Case88
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{" "," "}, "UPDATE"));
    Map<String, Object> case88 = new HashMap<>();
    case88.put("attributeEntryList", attributeEntryList);
    case88.put("case", "case88");
    
  //Case89
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{" "," "}, "DELETE"));
    Map<String, Object> case89 = new HashMap<>();
    case89.put("attributeEntryList", attributeEntryList);
    case89.put("case", "case89");
    
  //Case90
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", new Object[]{" "," "}, "DELETEALL"));
    Map<String, Object> case90 = new HashMap<>();
    case90.put("attributeEntryList", attributeEntryList);
    case90.put("case", "case90");
    
  //Case91
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1", "", "DELETEALL"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "ADD"));
    Map<String, Object> case91 = new HashMap<>();
    case91.put("attributeEntryList", attributeEntryList);
    case91.put("case", "case91");
    
  //Case92
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1", "", "DELETEALL"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "UPDATE"));
    Map<String, Object> case92 = new HashMap<>();
    case92.put("attributeEntryList", attributeEntryList);
    case92.put("case", "case92");
    
  //Case93
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1", "", "DELETEALL"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "DELETE"));
    Map<String, Object> case93 = new HashMap<>();
    case93.put("attributeEntryList", attributeEntryList);
    case93.put("case", "case93");
    
  //Case94
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1", "", "DELETEALL"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1", "Nike", "DELETEALL"));
    Map<String, Object> case94 = new HashMap<>();
    case94.put("attributeEntryList", attributeEntryList);
    case94.put("case", "case94");
    
  //Case95
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1123", "", "DELETEALL"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1123", "Nike", "ADD"));
    Map<String, Object> case95 = new HashMap<>();
    case95.put("attributeEntryList", attributeEntryList);
    case95.put("case", "case95");
    
  //Case96
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1123", "", "DELETEALL"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1123", "Nike", "UPDATE"));
    Map<String, Object> case96 = new HashMap<>();
    case96.put("attributeEntryList", attributeEntryList);
    case96.put("case", "case96");
    
  //Case97
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1123", "", "DELETEALL"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1123", "Nike", "DELETE"));
    Map<String, Object> case97 = new HashMap<>();
    case97.put("attributeEntryList", attributeEntryList);
    case97.put("case", "case97");
    
  //Case98
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", "brand_id1123", "", "DELETEALL"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", "brand_id1123", "Nike", "DELETEALL"));
    Map<String, Object> case98 = new HashMap<>();
    case98.put("attributeEntryList", attributeEntryList);
    case98.put("case", "case98");
    
  //Case99
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", null, "Nike", "ADD"));
    Map<String, Object> case99 = new HashMap<>();
    case99.put("attributeEntryList", attributeEntryList);
    case99.put("case", "case99");
    
  //Case100
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", null, "Nike", "UPDATE"));
    Map<String, Object> case100 = new HashMap<>();
    case100.put("attributeEntryList", attributeEntryList);
    case100.put("case", "case100");
    
  //Case101
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", null, "Nike", "DELETE"));
    Map<String, Object> case101 = new HashMap<>();
    case101.put("attributeEntryList", attributeEntryList);
    case101.put("case", "case101");
    
  //Case102
    clearAttribute = new ArrayList<>();
    clearAttribute.add(new AttributeEntry("cms_brands", null, "", "DELETEALL"));
    attributeEntryList = new ArrayList<>();
    attributeEntryList.add(new AttributeEntry("cms_brands", null, "Nike", "DELETEALL"));
    Map<String, Object> case102 = new HashMap<>();
    case102.put("attributeEntryList", attributeEntryList);
    case102.put("case", "case102");
    
    return(new Object[][]{{case1},{case2},{case3},{case4},{case5},{case6},{case7},{case8},{case9},{case10}
    ,{case11},{case12},{case13},{case14},{case15},{case16},{case17},{case18},{case19},{case20},{case21},
    {case21},{case23},{case24},{case25},{case26},{case27},{case28},{case29},{case30},{case31},{case32},
    {case33},{case34},{case35},{case36},{case37},{case38},{case39},{case40},{case41},{case42},{case43},
    {case44},{case45},{case46},{case47},{case48},{case49},{case50},{case51},{case52},{case53},{case54},
    {case55},{case56},{case57},{case58},{case59},{case60},{case61},{case62},{case63},{case64},{case65},
    {case66},{case67},{case68},{case69},{case70},{case71},{case72},{case73},{case74}});
  }
  
  @DataProvider
  public Object[][] getAttributeByUidx() {
	  Map<String, Object> case1 = new HashMap<>();
	  case1.put("namespaceList", "[\"cms_brands\"]");
	  case1.put("case", "case1");
	  case1.put("expectedResponse", 200);
	  
	  Map<String, Object> case2 = new HashMap<>();
	  case2.put("namespaceList", "[\"sdafewwgaaaae\"]");
	  case2.put("case", "case2");
	  case2.put("expectedResponse", 200);
	  
	  Map<String, Object> case3 = new HashMap<>();
	  case3.put("namespaceList", "[\"cms_brands\"]");
	  case3.put("uidx", "safwefaewfef");
	  case3.put("case", "case3");
	  case3.put("expectedResponse", 200);
	  
	  Map<String, Object> case4 = new HashMap<>();
	  case4.put("namespaceList", null);
	  case4.put("case", "case4");
	  case4.put("expectedResponse", 400);
	  
	  return(new Object[][]{{case1},{case2},{case3},{case4}});
  }
  
  @DataProvider
  public Object[][] getAttributeByDeviceId() {
	  Map<String, Object> case1 = new HashMap<>();
	  case1.put("namespaceList", "[\"cms_brands\"]");
	  case1.put("case", "case1");
	  case1.put("expectedResponse", 200);
	  
	  Map<String, Object> case2 = new HashMap<>();
	  case2.put("namespaceList", "[\"sdafewwgaaaae\"]");
	  case2.put("case", "case2");
	  case2.put("expectedResponse", 200);
	  
	  Map<String, Object> case3 = new HashMap<>();
	  case3.put("namespaceList", "[\"cms_brands\"]");
	  case3.put("uidx", "safwefaewfef");
	  case3.put("case", "case3");
	  case3.put("expectedResponse", 200);
	  
	  Map<String, Object> case4 = new HashMap<>();
	  case4.put("namespaceList", null);
	  case4.put("case", "case4");
	  case4.put("expectedResponse", 400);
	  
	  return(new Object[][]{{case1},{case2},{case3},{case4}});
  }
  
  @DataProvider
  public Object[][] searchUserByAttribute() {
	//Case1
	    ArrayList<AttributeEntry> updateAttributeEntryList = new ArrayList<>();
	    updateAttributeEntryList.add(new AttributeEntry("cms_brands", "brand_ids", "Nike", "ADD"));
	    Map<String, Object> case1 = new HashMap<>();
	    ArrayList<AttributeEntry> searchAttributeEntryList = new ArrayList<>();
	    searchAttributeEntryList.add(new AttributeEntry("cms_brands", "brand_ids", "Nike", "ADD"));
	    case1.put("updateAttributeEntryList", updateAttributeEntryList);
	    case1.put("searchAttributeEntryList", searchAttributeEntryList);
	    case1.put("case", "case1");
	    
	    return(new Object[][]{{case1}});
  }
  
  @DataProvider
  public Object[][] searchDeviceByAttribute() {
	//Case1
	  Object[][] dataSet = null;
	  
	  HashMap<String,String> case1 = new HashMap<>();
	  case1.put("namespace", "cms_brands");
	  case1.put("key", "brand_id");
	  case1.put("value", "1");
	  
	  HashMap<String,String> case2 = new HashMap<>();
	  case2.put("namespace", "brands");
	  case2.put("key", "likes");
	  case2.put("value", "[1,2]");
	  
	  HashMap<String,String> case3 = new HashMap<>();
	  case3.put("namespace", "brands");
	  case3.put("key", "likes");
	  case3.put("value", "12313232");
	  
	  HashMap<String,String> case4 = new HashMap<>();
	  case4.put("namespace", "brands");
	  case4.put("key", "likes");
	  case4.put("value", "[12313232,124532311]");
	  
	  HashMap<String,String> case5 = new HashMap<>();
	  case5.put("namespace", "cms_bs");
	  case5.put("key", "brand_id");
	  case5.put("value", "1");
	  
	  HashMap<String,String> case6 = new HashMap<>();
	  case6.put("namespace", "cms_bs");
	  case6.put("key", "brand_id");
	  case6.put("value", "[1,2]");
	  
	  HashMap<String,String> case7 = new HashMap<>();
	  case7.put("namespace", "cms_brands");
	  case7.put("key", "brad");
	  case7.put("value", "1");
	  
	  HashMap<String,String> case8 = new HashMap<>();
	  case8.put("namespace", "cms_brands");
	  case8.put("key", "brad");
	  case8.put("value", "[1,2]");
	  
	  HashMap<String,String> case9 = new HashMap<>();
	  case9.put("namespace", "cmds");
	  case9.put("key", "brad");
	  case9.put("value", "1");
	  
	  HashMap<String,String> case10 = new HashMap<>();
	  case10.put("namespace", "cmds");
	  case10.put("key", "brad");
	  case10.put("value", "[1,2]");
	  
	  HashMap<String,String> case11 = new HashMap<>();
	  case11.put("namespace", "cmds");
	  case11.put("key", "brad");
	  case11.put("value", "1123121211");
	  
	  HashMap<String,String> case12 = new HashMap<>();
	  case12.put("namespace", "cmds");
	  case12.put("key", "brad");
	  case12.put("value", "[112313212,2312311323]");
	  
	  HashMap<String,String> case13 = new HashMap<>();
	  case13.put("namespace", "");
	  case13.put("key", "");
	  case13.put("value", "1");
	  
	  HashMap<String,String> case14 = new HashMap<>();
	  case14.put("namespace", "");
	  case14.put("key", "");
	  case14.put("value", "[1,2]");
	  
	  HashMap<String,String> case15 = new HashMap<>();
	  case15.put("namespace", "");
	  case15.put("key", "");
	  case15.put("value", "");
	    
	  HashMap<String,String> result1 = new HashMap<>();
	  result1.put("status", "200");
	  result1.put("statusCode", "3");
	  result1.put("statusMessage", "Success");
	  result1.put("statusType", "SUCCESS");
	  result1.put("success", "true");
	  result1.put("totalCount","0");
	  
	  HashMap<String,String> result2 = new HashMap<>();
	  result2.put("status", "200");
	  result2.put("statusCode", "2");
	  result2.put("statusMessage", "NO_RESULTS_FOUND");
	  result2.put("statusType", "SUCCESS");
	  result2.put("success", "true");
	  result2.put("totalCount","0");
	  
	  HashMap<String,String> result3 = new HashMap<>();
	  result3.put("status", "400");
	  
	    dataSet = new Object[][]{{case1,result1},{case2,result1},{case3,result2},{case4,result2},{case5,result2},{case6,result2}
	                             ,{case7,result2},{case8,result2},{case9,result2},{case10,result2},{case11,result2},{case12,result2}
	                             ,{case13,result2},{case14,result2},{case15,result3}};
	    
	    return dataSet;
  }

}
