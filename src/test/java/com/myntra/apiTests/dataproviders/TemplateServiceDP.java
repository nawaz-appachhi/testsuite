package com.myntra.apiTests.dataproviders;

import java.util.HashMap;

import com.myntra.apiTests.portalservices.lgpservices.LgpPumpsHelper;
import org.testng.annotations.DataProvider;

public class TemplateServiceDP {
 HashMap<String,String> createdTemplate1 = new HashMap<String,String>();
 HashMap<String,String> createdTemplate2 = new HashMap<String,String>();;
  Long time1;
  
  @DataProvider
  public Object[][] createTemplateDP() {
	  Object[][] data = null;
	  //Positive TC.
	  HashMap<String,String> reqValues1 = new HashMap<String,String>();
	  reqValues1.put("TC", "1");
	  reqValues1.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValues1.put("tags","[\"push\"]");
	  reqValues1.put("appid","10");
	  reqValues1.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  createdTemplate1.putAll(reqValues1);

	  HashMap<String,String> respValues1 = new HashMap<String,String>();
	  respValues1.put("status","200");
	  respValues1.put("statusMessage","Template Stored Successfully");
	  respValues1.put("statusType", "Success");
	  respValues1.put("version", "1");
	  
	  HashMap<String,String> reqValues2 = new HashMap<String,String>();
	  reqValues2.put("TC", "2");
	  //Trailing space 
	  reqValues2.put("tgid", "\" AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValues2.put("tags","[\"push\"]");
	  reqValues2.put("appid","10");
	  reqValues2.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  createdTemplate2.putAll(reqValues2);
	  
	  HashMap<String,String> reqValues3 = new HashMap<String,String>();
	  reqValues3.put("TC", "3");
	  //Leading space 
	  reqValues3.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+" \"");
	  reqValues3.put("tags","[\"push\"]");
	  reqValues3.put("appid","10");
	  reqValues3.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues4 = new HashMap<String,String>();
	  reqValues4.put("TC", "4");
	  //Leading space 
	  reqValues4.put("tgid", "\" AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+" \"");
	  reqValues4.put("tags","[\"push\"]");
	  reqValues4.put("appid","10");
	  reqValues4.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues5 = new HashMap<String,String>();
	  reqValues5.put("TC", "5");
	  //Trailing white space 
	  reqValues5.put("tgid", "\"         AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValues5.put("tags","[\"push\"]");
	  reqValues5.put("appid","10");
	  reqValues5.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues6 = new HashMap<String,String>();
	  reqValues6.put("TC", "6");
	  //Leading white space 
	  reqValues6.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"           \"");
	  reqValues6.put("tags","[\"push\"]");
	  reqValues6.put("appid","10");
	  reqValues6.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues7 = new HashMap<String,String>();
	  reqValues7.put("TC", "7");
	  //Leading and Trailing white space 
	  reqValues7.put("tgid", "\"              AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"           \"");
	  reqValues7.put("tags","[\"push\"]");
	  reqValues7.put("appid","10");
	  reqValues7.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues8 = new HashMap<String,String>();
	  reqValues8.put("TC", "8");
	  //empty value
	  reqValues8.put("tgid", "\"\"");
	  reqValues8.put("tags","[\"push\"]");
	  reqValues8.put("appid","10");
	  reqValues8.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues9 = new HashMap<String,String>();
	  reqValues9.put("TC", "9");
	  //only Single space value
	  reqValues9.put("tgid", "\" \"");
	  reqValues9.put("tags","[\"push\"]");
	  reqValues9.put("appid","10");
	  reqValues9.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues10 = new HashMap<String,String>();
	  reqValues10.put("TC", "10");
	  //only white space value
	  reqValues10.put("tgid", "\"        \"");
	  reqValues10.put("tags","[\"push\"]");
	  reqValues10.put("appid","10");
	  reqValues10.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues11 = new HashMap<String,String>();
	  reqValues11.put("TC", "11");
	  //special character
	  reqValues11.put("tgid", "\""+new LgpPumpsHelper().getcurrentTimestamp()+"#$%^&*(*&^&$%$#$&*&%$#$%^&\"");
	  reqValues11.put("tags","[\"push\"]");
	  reqValues11.put("appid","10");
	  reqValues11.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues12 = new HashMap<String,String>();
	  reqValues12.put("TC", "12");
	  //numbers
	  reqValues12.put("tgid", "\""+new LgpPumpsHelper().getcurrentTimestamp()+"123467676682824\"");
	  reqValues12.put("tags","[\"push\"]");
	  reqValues12.put("appid","10");
	  reqValues12.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues13 = new HashMap<String,String>();
	  reqValues13.put("TC", "13");
	  //space in between
	  reqValues13.put("tgid", "\"AutoTgid "+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValues13.put("tags","[\"push\"]");
	  reqValues13.put("appid","10");
	  reqValues13.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	 
	  HashMap<String,String> respValues2 = new HashMap<String,String>();
	  respValues2.put("status","400");
	  respValues2.put("statusMessage","INVALID_TGID");
	  respValues2.put("statusType", "Error");
	  
	  HashMap<String,String> reqValues14 = new HashMap<String,String>();
	  reqValues14.put("TC", "14");
	  reqValues14.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Trailing Space
	  reqValues14.put("tags","[\" push\"]");
	  reqValues14.put("appid","10");
	  reqValues14.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues15 = new HashMap<String,String>();
	  reqValues15.put("TC", "15");
	  reqValues15.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Trailing Space in array
	  reqValues15.put("tags","[     \"push\"]");
	  reqValues15.put("appid","10");
	  reqValues15.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues16 = new HashMap<String,String>();
	  reqValues16.put("TC", "16");
	  reqValues16.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Trailing Space 
	  reqValues16.put("tags","[\"push\",\"   Notification\"]");
	  reqValues16.put("appid","10");
	  reqValues16.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues17 = new HashMap<String,String>();
	  reqValues17.put("TC", "17");
	  reqValues17.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading Space 
	  reqValues17.put("tags","[\"push\",\"Notification     \"]");
	  reqValues17.put("appid","10");
	  reqValues17.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues18 = new HashMap<String,String>();
	  reqValues18.put("TC", "18");
	  reqValues18.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading Space in array
	  reqValues18.put("tags","[\"push\",\"Notification\"       ]");
	  reqValues18.put("appid","10");
	  reqValues18.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues19 = new HashMap<String,String>();
	  reqValues19.put("TC", "19");
	  reqValues19.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading Space
	  reqValues19.put("tags","[\"push       \",\"Notification\"]");
	  reqValues19.put("appid","10");
	  reqValues19.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues20 = new HashMap<String,String>();
	  reqValues20.put("TC", "20");
	  reqValues20.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading and Trailing Space
	  reqValues20.put("tags","[\"    push       \",\"          Notification        \"]");
	  reqValues20.put("appid","10");
	  reqValues20.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues21 = new HashMap<String,String>();
	  reqValues21.put("TC", "21");
	  reqValues21.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading and Trailing Space in array
	  reqValues21.put("tags","[         \"push\",\"Notification\"      ]");
	  reqValues21.put("appid","10");
	  reqValues21.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues22 = new HashMap<String,String>();
	  reqValues22.put("TC", "22");
	  reqValues22.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading and Trailing Space in array
	  reqValues22.put("tags","[         \"     push      \",\"      Notification     \"         ]");
	  reqValues22.put("appid","10");
	  reqValues22.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues23 = new HashMap<String,String>();
	  reqValues23.putAll(reqValues22);
	  reqValues23.put("TC", "23");
	  //empty array
	  reqValues23.put("tags","[]");
	  
	  HashMap<String,String> reqValues24 = new HashMap<String,String>();
	  reqValues24.putAll(reqValues22);
	  reqValues24.put("TC", "24");
	  //empty value
	  reqValues24.put("tags","[\"\"]");
	  
	  HashMap<String,String> reqValues25 = new HashMap<String,String>();
	  reqValues25.putAll(reqValues22);
	  reqValues25.put("TC", "25");
	  //single space in array
	  reqValues25.put("tags","[ ]");
	  
	  HashMap<String,String> reqValues26 = new HashMap<String,String>();
	  reqValues26.putAll(reqValues22);
	  reqValues26.put("TC", "26");
	  //white space in array
	  reqValues26.put("tags","[        ]");
	  
	  HashMap<String,String> reqValues27 = new HashMap<String,String>();
	  reqValues27.putAll(reqValues22);
	  reqValues27.put("TC", "27");
	  //single space
	  reqValues27.put("tags","[\" \"]");
	  
	  HashMap<String,String> reqValues28 = new HashMap<String,String>();
	  reqValues28.putAll(reqValues22);
	  reqValues28.put("TC", "28");
	  //white space in array
	  reqValues28.put("tags","[\"       \"]");	  
	  
	  HashMap<String,String> reqValues29 = new HashMap<String,String>();
	  reqValues29.putAll(reqValues22);
	  reqValues29.put("TC", "29");
	  //white space and space
	  reqValues29.put("tags","[\" \",\"        \",\" \"]");
	  
	  HashMap<String,String> reqValues30 = new HashMap<String,String>();
	  reqValues30.putAll(reqValues22);
	  reqValues30.put("TC", "30");
	  //String, not array
	  reqValues30.put("tags","\"push\"");
	  
	  HashMap<String,String> reqValues31 = new HashMap<String,String>();
	  reqValues31.putAll(reqValues22);
	  reqValues31.put("TC", "31");
	  //String, not array
	  reqValues31.put("tags","[1,2,3,4]");
	  
	  HashMap<String,String> reqValues32 = new HashMap<String,String>();
	  reqValues32.putAll(reqValues22);
	  reqValues32.put("TC", "32");
	  //space in between
	  reqValues32.put("tags","[\"push notification\"]");
	  
	  HashMap<String,String> reqValues33 = new HashMap<String,String>();
	  reqValues33.putAll(reqValues22);
	  reqValues33.put("TC", "33");
	  //numbers
	  reqValues33.put("tags","[\"435678908765435678908\"]");
	  
	  HashMap<String,String> reqValues34 = new HashMap<String,String>();
	  reqValues34.putAll(reqValues22);
	  reqValues34.put("TC", "34");
	  //special characters
	  reqValues34.put("tags","[\"%^&*()*&^%$#@$%^&^%$}:?><\"]");
	  
	  HashMap<String,String> reqValues35 = new HashMap<String,String>();
	  reqValues35.putAll(reqValues22);
	  reqValues35.put("TC", "35");
	  //special characters
	  reqValues35.put("tags","[\"%^&*()*&^%$#@$%^&^%$}:?><\",\"%^&*()*&^%$#@$%^&^%$}:?><\",\"+%^&*()*&^%$#@$%^&^%$}:?><"+new LgpPumpsHelper().getcurrentTimestamp()+"\""+"]");
	  
	  
	  HashMap<String,String> reqValues36 = new HashMap<String,String>();
	  reqValues36.putAll(reqValues22);
	  reqValues36.put("TC", "36");
	  //numbers
	  reqValues36.put("tags","[\"435678908765435678908\",\"435678908765435678908\",\"435678908765435678908\",\"435678908765435678908"+new LgpPumpsHelper().getcurrentTimestamp()+"\""+"]");
	  
	  HashMap<String,String> reqValues37 = new HashMap<String,String>();
	  reqValues37.putAll(reqValues22);
	  reqValues37.put("TC", "37");
	  //valid
	  reqValues37.put("tags","[\"push\"]");
	  
	  HashMap<String,String> reqValues38 = new HashMap<String,String>();
	  reqValues38.putAll(reqValues22);
	  reqValues38.put("TC", "38");
	  //valid
	  reqValues38.put("tags","[\"push\",\"notification\",\"inapp\"]");
	  
	  HashMap<String,String> respValues3 = new HashMap<String,String>();
	  respValues3.put("status","400");
	  respValues3.put("statusMessage","INVALID_TAGS");
	  respValues3.put("statusType", "Error");
	  
	  HashMap<String,String> reqValues39 = new HashMap<String,String>();
	  reqValues39.putAll(reqValues22);
	  reqValues39.put("TC", "39");
	  reqValues39.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //valid
	  reqValues39.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues40 = new HashMap<String,String>();
	  reqValues40.putAll(reqValues22);
	  reqValues40.put("TC", "40");
	  //null
	  reqValues40.put("template","null");
	  
	  HashMap<String,String> reqValues41 = new HashMap<String,String>();
	  reqValues41.putAll(reqValues22);
	  reqValues41.put("TC", "41");
	  //Integer value
	  reqValues41.put("template","123");
	  
	  HashMap<String,String> reqValues42 = new HashMap<String,String>();
	  reqValues42.putAll(reqValues22);
	  reqValues42.put("TC", "42");
	  //empty value
	  reqValues42.put("template","\"\"");
	  
	  HashMap<String,String> reqValues43 = new HashMap<String,String>();
	  reqValues43.putAll(reqValues22);
	  reqValues43.put("TC", "43");
	  reqValues43.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //leading space
	  reqValues43.put("template","\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}       \"");
	  
	  HashMap<String,String> reqValues44 = new HashMap<String,String>();
	  reqValues44.putAll(reqValues22);
	  reqValues44.put("TC", "44");
	  reqValues44.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //trailing space
	  reqValues44.put("template","\"         {\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues45 = new HashMap<String,String>();
	  reqValues45.putAll(reqValues22);
	  reqValues45.put("TC", "45");
	  reqValues45.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //leading and trailing space
	  reqValues45.put("template","\"         {\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}       \"");
	  
	  HashMap<String,String> reqValues46 = new HashMap<String,String>();
	  reqValues46.putAll(reqValues22);
	  reqValues46.put("TC", "46");
	  reqValues46.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //special characters
	  reqValues46.put("template","\"{\\\"@#$%^&*(*&^%$#$%^&*^%$#@!#$%^:><,.?/\\\":\\\"&^%&*(%$^&^^*&^\\\"}\"");
	  
	  HashMap<String,String> reqValues47 = new HashMap<String,String>();
	  reqValues47.putAll(reqValues22);
	  reqValues47.put("TC", "47");
	  reqValues47.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //numbers
	  reqValues47.put("template","\"{\\\"12345678987654321\\\":\\\"34567\\\"}\"");
	  
	  HashMap<String,String> reqValues48 = new HashMap<String,String>();
	  reqValues48.putAll(reqValues22);
	  reqValues48.put("TC", "48");
	  reqValues48.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Capital case attributes
	  reqValues48.put("template","\"{\\\"HEADING\\\":\\\"Myntra\\\",\\\"TITLE\\\":\\\"Myntra Support\\\",\\\"DESCRIPTION\\\":\\\"{$description$}\\\",\\\"NOTIFICATIONTYPE\\\":\\\"Engagement\\\",\\\"PAGEURL\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues49 = new HashMap<String,String>();
	  reqValues49.putAll(reqValues22);
	  reqValues49.put("TC", "49");
	  reqValues49.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //small case attribute
	  reqValues49.put("template","\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationtype\\\":\\\"Engagement\\\",\\\"pageurl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> respValues4 = new HashMap<String,String>();
	  respValues4.put("status","400");
	  respValues4.put("statusMessage","INVALID_TEMPLATE");
	  respValues4.put("statusType", "Error");
	  
	  HashMap<String,String> reqValues50 = new HashMap<String,String>();
	  reqValues50.putAll(reqValues22);
	  reqValues50.put("TC", "50");
	  reqValues50.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //valid
	  reqValues50.put("appid","15");
	  
	  HashMap<String,String> reqValues51 = new HashMap<String,String>();
	  reqValues51.putAll(reqValues22);
	  reqValues51.put("TC", "51");
	  //String value for int attribute
	  reqValues51.put("appid","\"15\"");
	  
	  HashMap<String,String> reqValues52 = new HashMap<String,String>();
	  reqValues52.putAll(reqValues22);
	  reqValues52.put("TC", "52");
	  //null
	  reqValues52.put("appid","null");
	  
	  HashMap<String,String> reqValues53 = new HashMap<String,String>();
	  reqValues53.putAll(reqValues22);
	  reqValues53.put("TC", "53");
	  //negative value
	  reqValues53.put("appid","-10");
	  
	  HashMap<String,String> reqValues54 = new HashMap<String,String>();
	  reqValues54.putAll(reqValues22);
	  reqValues54.put("TC", "54");
	  //negative value
	  reqValues54.put("appid","0");
	  
	  HashMap<String,String> reqValues55 = new HashMap<String,String>();
	  reqValues55.putAll(reqValues22);
	  reqValues55.put("TC", "55");
	  reqValues55.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //leading space
	  reqValues55.put("appid","10   ");
	 
	  HashMap<String,String> reqValues56 = new HashMap<String,String>();
	  reqValues56.putAll(reqValues22);
	  reqValues56.put("TC", "56");
	  reqValues56.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Trailing space
	  reqValues56.put("appid","   10");
	  
	  HashMap<String,String> reqValues57 = new HashMap<String,String>();
	  reqValues57.putAll(reqValues22);
	  reqValues57.put("TC", "57");
	  reqValues57.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Trailing and leading space
	  reqValues57.put("appid","   10      ");
	  
	  HashMap<String,String> respValues5 = new HashMap<String,String>();
	  respValues5.put("status","400");
	  respValues5.put("statusMessage","INVALID_APPID");
	  respValues5.put("statusType", "Error");
	  
	  HashMap<String,String> reqValues58 = new HashMap<String,String>();
	  reqValues58.putAll(reqValues22);
	  reqValues58.put("TC", "58");
	  //Integer value
	  reqValues58.put("tgid", "123467676682824");
	  
	  HashMap<String,String> reqValues59 = new HashMap<String,String>();
	  reqValues59.putAll(reqValues22);
	  reqValues59.put("TC", "59");
	  //Integer value
	  reqValues59.put("tags", "123424");
	  
	  HashMap<String,String> reqValues60 = new HashMap<String,String>();
	  reqValues60.putAll(reqValues22);
	  reqValues60.put("TC", "60");
	  //null
	  reqValues60.put("tags", "null");
	  
	  HashMap<String,String> reqValues61 = new HashMap<String,String>();
	  reqValues61.putAll(reqValues22);
	  reqValues61.put("TC", "61");
	  //null
	  reqValues61.put("tgid", "null");
	  
	  HashMap<String,String> reqValues62 = new HashMap<String,String>();
	  reqValues62.putAll(reqValues1);
	  reqValues62.put("TC", "62");
	  //no tgid attribute
	  reqValues62.remove("tgid");
	  
	  HashMap<String,String> reqValues63 = new HashMap<String,String>();
	  reqValues63.putAll(reqValues1);
	  reqValues63.put("TC", "63");
	  //no tags attribute
	  reqValues63.remove("tags");
	  
	  HashMap<String,String> reqValues64 = new HashMap<String,String>();
	  reqValues64.putAll(reqValues1);
	  reqValues64.put("TC", "64");
	  //no appid attribute
	  reqValues64.remove("appid");
	  
	  HashMap<String,String> reqValues65 = new HashMap<String,String>();
	  reqValues65.putAll(reqValues1);
	  reqValues65.put("TC", "65");
	  //no template attribute
	  reqValues65.remove("template");
	  
	  HashMap<String,String> respValues6 = new HashMap<String,String>();
	  respValues6.put("status","400");
	  respValues6.put("statusMessage","TEMPLATE_DATA_UNMARSHALL_ERROR");
	  respValues6.put("statusType", "Error");
	  
	//data = new Object[][]{{reqValues62,respValues2}};
	
	  data = new Object[][]{{reqValues1,respValues1},{reqValues2,respValues1},{reqValues3,respValues1},{reqValues4,respValues1},{reqValues5,respValues1},{reqValues6,respValues1},{reqValues7,respValues1},
		  {reqValues8,respValues2},{reqValues9,respValues2},{reqValues10,respValues2},{reqValues11,respValues1},{reqValues12,respValues1},{reqValues13,respValues2},
		  {reqValues14,respValues1},{reqValues15,respValues1},{reqValues16,respValues1},{reqValues17,respValues1},{reqValues18,respValues1},{reqValues19,respValues1},{reqValues20,respValues1},{reqValues21,respValues1},{reqValues22,respValues1},
		  {reqValues23,respValues3},{reqValues24,respValues3},{reqValues25,respValues3},{reqValues26,respValues3},{reqValues27,respValues3},{reqValues28,respValues3},{reqValues29,respValues3},{reqValues30,respValues6},{reqValues31,respValues6},
		  {reqValues32,respValues3},{reqValues33,respValues1},{reqValues34,respValues1},{reqValues35,respValues1},{reqValues36,respValues1},{reqValues37,respValues1},{reqValues38,respValues1},{reqValues39,respValues1},{reqValues40,respValues4},
		  {reqValues41,respValues6},{reqValues42,respValues4},{reqValues43,respValues1},{reqValues44,respValues1},{reqValues45,respValues1},{reqValues46,respValues1},{reqValues47,respValues1},{reqValues48,respValues1},{reqValues49,respValues1},
		  {reqValues50,respValues1},{reqValues51,respValues6},{reqValues52,respValues5},{reqValues53,respValues5},{reqValues54,respValues5},{reqValues55,respValues1},{reqValues56,respValues1},{reqValues57,respValues1},{reqValues58,respValues6},
		  {reqValues59,respValues6},{reqValues60,respValues3},{reqValues61,respValues2},{reqValues62,respValues2},{reqValues63,respValues3},{reqValues64,respValues5},{reqValues65,respValues4}};
		 
	return data;
  }
  
  @DataProvider
  public Object[][] deleteTemplateDP() {
	  Object[][] data = null;
	
	  HashMap<String,String> reqValuesGet1 = new HashMap<String,String>();
	  reqValuesGet1.putAll(createdTemplate1);
	  reqValuesGet1.put("TC", ""+(1));
	  reqValuesGet1.put("version","1");
	  
	  
	  HashMap<String,String> respGetValues1 = new HashMap<String,String>();
	  respGetValues1.put("status","200");
	  respGetValues1.put("statusMessage","Template Deleted Successfully");
	  respGetValues1.put("statusType", "Success");
	  respGetValues1.put("version", "1");
	  
	  HashMap<String,String> reqValuesGet2 = new HashMap<String,String>();
	  reqValuesGet2.put("TC", ""+(2));
	  reqValuesGet2.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValuesGet2.put("tags","[\"push\"]");
	  reqValuesGet2.put("appid","10");
	  reqValuesGet2.put("version","100");
	  reqValuesGet2.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> respGetValues2 = new HashMap<String,String>();
	  respGetValues2.put("status","200");
	  respGetValues2.put("statusMessage","NO_RESULT_FOUND");
	  respGetValues2.put("statusType", "Success");
	  
	  
	  HashMap<String,String> reqValuesGet3 = new HashMap<String,String>();
	  reqValuesGet3.put("TC", ""+(3));
	  reqValuesGet3.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValuesGet3.put("tags","[\"push\"]");
	  reqValuesGet3.put("appid","10");
	  reqValuesGet3.put("version","0");
	  reqValuesGet3.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValuesGet4 = new HashMap<String,String>();
	  reqValuesGet4.put("TC", ""+(4));
	  reqValuesGet4.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValuesGet4.put("tags","[\"push\"]");
	  reqValuesGet4.put("appid","10");
	  reqValuesGet4.put("version","-1");
	  reqValuesGet4.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValuesGet5 = new HashMap<String,String>();
	  reqValuesGet5.put("TC", ""+(5));
	  reqValuesGet5.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValuesGet5.put("tags","[\"push\"]");
	  reqValuesGet5.put("appid","10");
	  reqValuesGet5.put("version","null");
	  reqValuesGet5.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> respGetValues3 = new HashMap<String,String>();
	  respGetValues3.put("status","400");
	  respGetValues3.put("statusMessage","INVALID_VERSION");
	  respGetValues3.put("statusType", "Error");
	  
	  
	  HashMap<String,String> reqValuesGet6 = new HashMap<String,String>();
	  reqValuesGet6.put("TC", ""+(6));
	  reqValuesGet6.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValuesGet6.put("tags","[\"push\"]");
	  reqValuesGet6.put("appid","10");
	  reqValuesGet6.put("version","\"1\"");
	  reqValuesGet6.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> respGetValues4 = new HashMap<String,String>();
	  respGetValues4.put("status","400");
	  respGetValues4.put("statusMessage","TEMPLATE_DATA_UNMARSHALL_ERROR");
	  respGetValues4.put("statusType", "Error");
	  
	  
	  HashMap<String,String> reqValuesGet7 = new HashMap<String,String>();
	  reqValuesGet7.put("TC", ""+(7));
	  reqValuesGet7.put("tgid", "\"AutoTgid-Valid\"");
	  reqValuesGet7.put("tags","[\"push\"]");
	  reqValuesGet7.put("appid","10");
	  reqValuesGet7.put("version","3");
	  reqValuesGet7.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValuesGet8 = new HashMap<String,String>();
	  reqValuesGet8.put("TC", ""+(8));
	  reqValuesGet8.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValuesGet8.put("tags","[\"push\"]");
	  reqValuesGet8.put("appid","10");
	  reqValuesGet8.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	 
	  
	  HashMap<String,String> reqValues1 = new HashMap<String,String>();
	  reqValues1.put("TC", "c1");
	  reqValues1.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValues1.put("tags","[\"push\"]");
	  reqValues1.put("appid","10");
	  reqValues1.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues1.put("version","1");
	  
	  HashMap<String,String> respValues1 = new HashMap<String,String>();
	  respValues1.put("status","200");
	  respValues1.put("statusMessage","NO_RESULT_FOUND");
	  respValues1.put("statusType", "Success");
	  respValues1.put("version", "1");
	  
	  HashMap<String,String> reqValues2 = new HashMap<String,String>();
	  reqValues2.put("TC", "c2");
	  //Trailing space 
	  reqValues2.put("tgid", "\" AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValues2.put("tags","[\"push\"]");
	  reqValues2.put("appid","10");
	  reqValues2.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues2.put("version","1");

	  HashMap<String,String> reqValues3 = new HashMap<String,String>();
	  reqValues3.put("TC", "c3");
	  //Leading space 
	  reqValues3.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+" \"");
	  reqValues3.put("tags","[\"push\"]");
	  reqValues3.put("appid","10");
	  reqValues3.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues3.put("version","1");

	  HashMap<String,String> reqValues4 = new HashMap<String,String>();
	  reqValues4.put("TC", "c4");
	  //Leading space 
	  reqValues4.put("tgid", "\" AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+" \"");
	  reqValues4.put("tags","[\"push\"]");
	  reqValues4.put("appid","10");
	  reqValues4.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues4.put("version","1");

	  HashMap<String,String> reqValues5 = new HashMap<String,String>();
	  reqValues5.put("TC", "c5");
	  //Trailing white space 
	  reqValues5.put("tgid", "\"         AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValues5.put("tags","[\"push\"]");
	  reqValues5.put("appid","10");
	  reqValues5.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues5.put("version","1");

	  HashMap<String,String> reqValues6 = new HashMap<String,String>();
	  reqValues6.put("TC", "c6");
	  //Leading white space 
	  reqValues6.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"           \"");
	  reqValues6.put("tags","[\"push\"]");
	  reqValues6.put("appid","10");
	  reqValues6.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues6.put("version","1");

	  HashMap<String,String> reqValues7 = new HashMap<String,String>();
	  reqValues7.put("TC", "c7");
	  //Leading and Trailing white space 
	  reqValues7.put("tgid", "\"              AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"           \"");
	  reqValues7.put("tags","[\"push\"]");
	  reqValues7.put("appid","10");
	  reqValues7.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues7.put("version","1");

	  HashMap<String,String> reqValues8 = new HashMap<String,String>();
	  reqValues8.put("TC", "c8");
	  //empty value
	  reqValues8.put("tgid", "\"\"");
	  reqValues8.put("tags","[\"push\"]");
	  reqValues8.put("appid","10");
	  reqValues8.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues8.put("version","1");

	  HashMap<String,String> reqValues9 = new HashMap<String,String>();
	  reqValues9.put("TC", "c9");
	  //only Single space value
	  reqValues9.put("tgid", "\" \"");
	  reqValues9.put("tags","[\"push\"]");
	  reqValues9.put("appid","10");
	  reqValues9.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues9.put("version","1");

	  HashMap<String,String> reqValues10 = new HashMap<String,String>();
	  reqValues10.put("TC", "c10");
	  //only white space value
	  reqValues10.put("tgid", "\"        \"");
	  reqValues10.put("tags","[\"push\"]");
	  reqValues10.put("appid","10");
	  reqValues10.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues10.put("version","1");

	  HashMap<String,String> reqValues11 = new HashMap<String,String>();
	  reqValues11.put("TC", "c11");
	  //special character
	  reqValues11.put("tgid", "\""+new LgpPumpsHelper().getcurrentTimestamp()+"#$%^&*(*&^&$%$#$&*&%$#$%^&\"");
	  reqValues11.put("tags","[\"push\"]");
	  reqValues11.put("appid","10");
	  reqValues11.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues11.put("version","1");

	  HashMap<String,String> reqValues12 = new HashMap<String,String>();
	  reqValues12.put("TC", "c12");
	  //numbers
	  reqValues12.put("tgid", "\""+new LgpPumpsHelper().getcurrentTimestamp()+"123467676682824\"");
	  reqValues12.put("tags","[\"push\"]");
	  reqValues12.put("appid","10");
	  reqValues12.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues12.put("version","1");

	  HashMap<String,String> reqValues13 = new HashMap<String,String>();
	  reqValues13.put("TC", "c13");
	  //space in between
	  reqValues13.put("tgid", "\"AutoTgid "+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValues13.put("tags","[\"push\"]");
	  reqValues13.put("appid","10");
	  reqValues13.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues13.put("version","1");

	  HashMap<String,String> respValues2 = new HashMap<String,String>();
	  respValues2.put("status","400");
	  respValues2.put("statusMessage","INVALID_TGID");
	  respValues2.put("statusType", "Error");
	  
	  HashMap<String,String> reqValues14 = new HashMap<String,String>();
	  reqValues14.put("TC", "c14");
	  reqValues14.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Trailing Space
	  reqValues14.put("tags","[\" push\"]");
	  reqValues14.put("appid","10");
	  reqValues14.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues14.put("version","1");

	  HashMap<String,String> reqValues15 = new HashMap<String,String>();
	  reqValues15.put("TC", "c15");
	  reqValues15.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Trailing Space in array
	  reqValues15.put("tags","[     \"push\"]");
	  reqValues15.put("appid","10");
	  reqValues15.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues15.put("version","1");

	  HashMap<String,String> reqValues16 = new HashMap<String,String>();
	  reqValues16.put("TC", "c16");
	  reqValues16.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Trailing Space 
	  reqValues16.put("tags","[\"push\",\"   Notification\"]");
	  reqValues16.put("appid","10");
	  reqValues16.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues16.put("version","1");

	  HashMap<String,String> reqValues17 = new HashMap<String,String>();
	  reqValues17.put("TC", "c17");
	  reqValues17.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading Space 
	  reqValues17.put("tags","[\"push\",\"Notification     \"]");
	  reqValues17.put("appid","10");
	  reqValues17.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues17.put("version","1");

	  HashMap<String,String> reqValues18 = new HashMap<String,String>();
	  reqValues18.put("TC", "c18");
	  reqValues18.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading Space in array
	  reqValues18.put("tags","[\"push\",\"Notification\"       ]");
	  reqValues18.put("appid","10");
	  reqValues18.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues18.put("version","1");

	  HashMap<String,String> reqValues19 = new HashMap<String,String>();
	  reqValues19.put("TC", "c19");
	  reqValues19.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading Space
	  reqValues19.put("tags","[\"push       \",\"Notification\"]");
	  reqValues19.put("appid","10");
	  reqValues19.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues19.put("version","1");

	  HashMap<String,String> reqValues20 = new HashMap<String,String>();
	  reqValues20.put("TC", "c20");
	  reqValues20.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading and Trailing Space
	  reqValues20.put("tags","[\"    push       \",\"          Notification        \"]");
	  reqValues20.put("appid","10");
	  reqValues20.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues20.put("version","1");

	  HashMap<String,String> reqValues21 = new HashMap<String,String>();
	  reqValues21.put("TC", "c21");
	  reqValues21.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading and Trailing Space in array
	  reqValues21.put("tags","[         \"push\",\"Notification\"      ]");
	  reqValues21.put("appid","10");
	  reqValues21.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues21.put("version","1");

	  HashMap<String,String> reqValues22 = new HashMap<String,String>();
	  reqValues22.put("TC", "c22");
	  reqValues22.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading and Trailing Space in array
	  reqValues22.put("tags","[         \"     push      \",\"      Notification     \"         ]");
	  reqValues22.put("appid","10");
	  reqValues22.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues22.put("version","1");

	  HashMap<String,String> reqValues23 = new HashMap<String,String>();
	  reqValues23.putAll(reqValues22);
	  reqValues23.put("TC", "c23");
	  //empty array
	  reqValues23.put("tags","[]");
	  
	  HashMap<String,String> reqValues24 = new HashMap<String,String>();
	  reqValues24.putAll(reqValues22);
	  reqValues24.put("TC", "c24");
	  //empty value
	  reqValues24.put("tags","[\"\"]");
	  
	  HashMap<String,String> reqValues25 = new HashMap<String,String>();
	  reqValues25.putAll(reqValues22);
	  reqValues25.put("TC", "c25");
	  //single space in array
	  reqValues25.put("tags","[ ]");
	  
	  HashMap<String,String> reqValues26 = new HashMap<String,String>();
	  reqValues26.putAll(reqValues22);
	  reqValues26.put("TC", "c26");
	  //white space in array
	  reqValues26.put("tags","[        ]");
	  
	  HashMap<String,String> reqValues27 = new HashMap<String,String>();
	  reqValues27.putAll(reqValues22);
	  reqValues27.put("TC", "c27");
	  //single space
	  reqValues27.put("tags","[\" \"]");
	  
	  HashMap<String,String> reqValues28 = new HashMap<String,String>();
	  reqValues28.putAll(reqValues22);
	  reqValues28.put("TC", "c28");
	  //white space in array
	  reqValues28.put("tags","[\"       \"]");	  
	  
	  HashMap<String,String> reqValues29 = new HashMap<String,String>();
	  reqValues29.putAll(reqValues22);
	  reqValues29.put("TC", "c29");
	  //white space and space
	  reqValues29.put("tags","[\" \",\"        \",\" \"]");
	  
	  HashMap<String,String> reqValues30 = new HashMap<String,String>();
	  reqValues30.putAll(reqValues22);
	  reqValues30.put("TC", "c30");
	  //String, not array
	  reqValues30.put("tags","\"push\"");
	  
	  HashMap<String,String> reqValues31 = new HashMap<String,String>();
	  reqValues31.putAll(reqValues22);
	  reqValues31.put("TC", "c31");
	  //String, not array
	  reqValues31.put("tags","[1,2,3,4]");
	  
	  HashMap<String,String> reqValues32 = new HashMap<String,String>();
	  reqValues32.putAll(reqValues22);
	  reqValues32.put("TC", "c32");
	  //space in between
	  reqValues32.put("tags","[\"push notification\"]");
	  
	  HashMap<String,String> reqValues33 = new HashMap<String,String>();
	  reqValues33.putAll(reqValues22);
	  reqValues33.put("TC", "c33");
	  //numbers
	  reqValues33.put("tags","[\"435678908765435678908\"]");
	  
	  HashMap<String,String> reqValues34 = new HashMap<String,String>();
	  reqValues34.putAll(reqValues22);
	  reqValues34.put("TC", "c34");
	  //special characters
	  reqValues34.put("tags","[\"%^&*()*&^%$#@$%^&^%$}:?><\"]");
	  
	  HashMap<String,String> reqValues35 = new HashMap<String,String>();
	  reqValues35.putAll(reqValues22);
	  reqValues35.put("TC", "c35");
	  //special characters
	  reqValues35.put("tags","[\"%^&*()*&^%$#@$%^&^%$}:?><\",\"%^&*()*&^%$#@$%^&^%$}:?><\",\"+%^&*()*&^%$#@$%^&^%$}:?><"+new LgpPumpsHelper().getcurrentTimestamp()+"\""+"]");
	  
	  
	  HashMap<String,String> reqValues36 = new HashMap<String,String>();
	  reqValues36.putAll(reqValues22);
	  reqValues36.put("TC", "c36");
	  //numbers
	  reqValues36.put("tags","[\"435678908765435678908\",\"435678908765435678908\",\"435678908765435678908\",\"435678908765435678908"+new LgpPumpsHelper().getcurrentTimestamp()+"\""+"]");
	  
	  HashMap<String,String> reqValues37 = new HashMap<String,String>();
	  reqValues37.putAll(reqValues22);
	  reqValues37.put("TC", "c37");
	  //valid
	  reqValues37.put("tags","[\"push\"]");
	  
	  HashMap<String,String> reqValues38 = new HashMap<String,String>();
	  reqValues38.putAll(reqValues22);
	  reqValues38.put("TC", "c38");
	  //valid
	  reqValues38.put("tags","[\"push\",\"notification\",\"inapp\"]");
	  
	  HashMap<String,String> respValues3 = new HashMap<String,String>();
	  respValues3.put("status","400");
	  respValues3.put("statusMessage","INVALID_TAGS");
	  respValues3.put("statusType", "Error");
	  
	  HashMap<String,String> reqValues39 = new HashMap<String,String>();
	  reqValues39.putAll(reqValues22);
	  reqValues39.put("TC", "c39");
	  reqValues39.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //valid
	  reqValues39.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues40 = new HashMap<String,String>();
	  reqValues40.putAll(reqValues22);
	  reqValues40.put("TC", "c40");
	  //null
	  reqValues40.put("template","null");
	  
	  HashMap<String,String> reqValues41 = new HashMap<String,String>();
	  reqValues41.putAll(reqValues22);
	  reqValues41.put("TC", "c41");
	  //Integer value
	  reqValues41.put("template","123");
	  
	  HashMap<String,String> reqValues42 = new HashMap<String,String>();
	  reqValues42.putAll(reqValues22);
	  reqValues42.put("TC", "c42");
	  //empty value
	  reqValues42.put("template","\"\"");
	  
	  HashMap<String,String> reqValues43 = new HashMap<String,String>();
	  reqValues43.putAll(reqValues22);
	  reqValues43.put("TC", "c43");
	  reqValues43.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //leading space
	  reqValues43.put("template","\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}       \"");
	  
	  HashMap<String,String> reqValues44 = new HashMap<String,String>();
	  reqValues44.putAll(reqValues22);
	  reqValues44.put("TC", "c44");
	  reqValues44.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //trailing space
	  reqValues44.put("template","\"         {\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues45 = new HashMap<String,String>();
	  reqValues45.putAll(reqValues22);
	  reqValues45.put("TC", "c45");
	  reqValues45.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //leading and trailing space
	  reqValues45.put("template","\"         {\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}       \"");
	  
	  HashMap<String,String> reqValues46 = new HashMap<String,String>();
	  reqValues46.putAll(reqValues22);
	  reqValues46.put("TC", "c46");
	  reqValues46.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //special characters
	  reqValues46.put("template","\"{\\\"@#$%^&*(*&^%$#$%^&*^%$#@!#$%^:><,.?/\\\":\\\"&^%&*(%$^&^^*&^\\\"}\"");
	  
	  HashMap<String,String> reqValues47 = new HashMap<String,String>();
	  reqValues47.putAll(reqValues22);
	  reqValues47.put("TC", "c47");
	  reqValues47.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //numbers
	  reqValues47.put("template","\"{\\\"12345678987654321\\\":\\\"34567\\\"}\"");
	  
	  HashMap<String,String> reqValues48 = new HashMap<String,String>();
	  reqValues48.putAll(reqValues22);
	  reqValues48.put("TC", "c48");
	  reqValues48.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Capital case attributes
	  reqValues48.put("template","\"{\\\"HEADING\\\":\\\"Myntra\\\",\\\"TITLE\\\":\\\"Myntra Support\\\",\\\"DESCRIPTION\\\":\\\"{$description$}\\\",\\\"NOTIFICATIONTYPE\\\":\\\"Engagement\\\",\\\"PAGEURL\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues49 = new HashMap<String,String>();
	  reqValues49.putAll(reqValues22);
	  reqValues49.put("TC", "c49");
	  reqValues49.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //small case attribute
	  reqValues49.put("template","\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationtype\\\":\\\"Engagement\\\",\\\"pageurl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> respValues4 = new HashMap<String,String>();
	  respValues4.put("status","400");
	  respValues4.put("statusMessage","INVALID_TEMPLATE");
	  respValues4.put("statusType", "Error");
	  
	  HashMap<String,String> reqValues50 = new HashMap<String,String>();
	  reqValues50.putAll(reqValues22);
	  reqValues50.put("TC", "c50");
	  reqValues50.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //valid
	  reqValues50.put("appid","15");
	  
	  HashMap<String,String> reqValues51 = new HashMap<String,String>();
	  reqValues51.putAll(reqValues22);
	  reqValues51.put("TC", "c51");
	  //String value for int attribute
	  reqValues51.put("appid","\"15\"");
	  
	  HashMap<String,String> reqValues52 = new HashMap<String,String>();
	  reqValues52.putAll(reqValues22);
	  reqValues52.put("TC", "c52");
	  //null
	  reqValues52.put("appid","null");
	  
	  HashMap<String,String> reqValues53 = new HashMap<String,String>();
	  reqValues53.putAll(reqValues22);
	  reqValues53.put("TC", "c53");
	  //negative value
	  reqValues53.put("appid","-10");
	  
	  HashMap<String,String> reqValues54 = new HashMap<String,String>();
	  reqValues54.putAll(reqValues22);
	  reqValues54.put("TC", "c54");
	  //negative value
	  reqValues54.put("appid","0");
	  
	  HashMap<String,String> reqValues55 = new HashMap<String,String>();
	  reqValues55.putAll(reqValues22);
	  reqValues55.put("TC", "c55");
	  reqValues55.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //leading space
	  reqValues55.put("appid","10   ");
	 
	  HashMap<String,String> reqValues56 = new HashMap<String,String>();
	  reqValues56.putAll(reqValues22);
	  reqValues56.put("TC", "c56");
	  reqValues56.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Trailing space
	  reqValues56.put("appid","   10");
	  
	  HashMap<String,String> reqValues57 = new HashMap<String,String>();
	  reqValues57.putAll(reqValues22);
	  reqValues57.put("TC", "c57");
	  reqValues57.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Trailing and leading space
	  reqValues57.put("appid","   10      ");
	  
	  HashMap<String,String> respValues5 = new HashMap<String,String>();
	  respValues5.put("status","400");
	  respValues5.put("statusMessage","INVALID_APPID");
	  respValues5.put("statusType", "Error");
	  
	  HashMap<String,String> reqValues58 = new HashMap<String,String>();
	  reqValues58.putAll(reqValues22);
	  reqValues58.put("TC", "c58");
	  //Integer value
	  reqValues58.put("tgid", "123467676682824");
	  
	  HashMap<String,String> reqValues59 = new HashMap<String,String>();
	  reqValues59.putAll(reqValues22);
	  reqValues59.put("TC", "c59");
	  //Integer value
	  reqValues59.put("tags", "123424");
	  
	  HashMap<String,String> reqValues60 = new HashMap<String,String>();
	  reqValues60.putAll(reqValues22);
	  reqValues60.put("TC", "c60");
	  //null
	  reqValues60.put("tags", "null");
	  
	  HashMap<String,String> reqValues61 = new HashMap<String,String>();
	  reqValues61.putAll(reqValues22);
	  reqValues61.put("TC", "c61");
	  //null
	  reqValues61.put("tgid", "null");
	  
	  HashMap<String,String> reqValues62 = new HashMap<String,String>();
	  reqValues62.putAll(reqValues1);
	  reqValues62.put("TC", "c62");
	  //no tgid attribute
	  reqValues62.remove("tgid");
	  
	  HashMap<String,String> reqValues63 = new HashMap<String,String>();
	  reqValues63.putAll(reqValues1);
	  reqValues63.put("TC", "c63");
	  //no tags attribute
	  reqValues63.remove("tags");
	  
	  HashMap<String,String> reqValues64 = new HashMap<String,String>();
	  reqValues64.putAll(reqValues1);
	  reqValues64.put("TC", "c64");
	  //no appid attribute
	  reqValues64.remove("appid");
	  
	  HashMap<String,String> reqValues65 = new HashMap<String,String>();
	  reqValues65.putAll(reqValues1);
	  reqValues65.put("TC", "c65");
	  //no template attribute
	  reqValues65.remove("template");
	  
	  	data =new Object[][]{{reqValues1,respValues1},{reqValues2,respValues1},{reqValues3,respValues1},{reqValues4,respValues1},{reqValues5,respValues1},{reqValues6,respValues1},{reqValues7,respValues1},
		  {reqValues8,respValues2},{reqValues9,respValues2},{reqValues10,respValues2},{reqValues11,respValues1},{reqValues12,respValues1},{reqValues13,respValues2},
		  {reqValues14,respValues1},{reqValues15,respValues1},{reqValues16,respValues1},{reqValues17,respValues1},{reqValues18,respValues1},{reqValues19,respValues1},{reqValues20,respValues1},{reqValues21,respValues1},{reqValues22,respValues1},
		  {reqValues23,respValues3},{reqValues24,respValues3},{reqValues25,respValues3},{reqValues26,respValues3},{reqValues27,respValues3},{reqValues28,respValues3},{reqValues29,respValues3},{reqValues30,respGetValues4},{reqValues31,respGetValues4},
		  {reqValues32,respValues3},{reqValues33,respValues1},{reqValues34,respValues1},{reqValues35,respValues1},{reqValues36,respValues1},{reqValues37,respValues1},{reqValues38,respValues1},{reqValues39,respValues1},{reqValues40,respGetValues2},
		  {reqValues41,respGetValues4},{reqValues42,respGetValues2},{reqValues43,respValues1},{reqValues44,respValues1},{reqValues45,respValues1},{reqValues46,respValues1},{reqValues47,respValues1},{reqValues48,respValues1},{reqValues49,respValues1},
		  {reqValues50,respValues1},{reqValues51,respGetValues4},{reqValues52,respValues5},{reqValues53,respValues5},{reqValues54,respValues5},{reqValues55,respValues1},{reqValues56,respValues1},{reqValues57,respValues1},{reqValues58,respGetValues4},
		  {reqValues59,respGetValues4},{reqValues60,respValues3},{reqValues61,respValues2},{reqValues62,respValues2},{reqValues63,respValues3},{reqValues64,respValues5},{reqValues65,respGetValues2},{reqValuesGet1,respGetValues1},{reqValuesGet2,respGetValues2},
		  {reqValuesGet3,respGetValues3},{reqValuesGet4,respGetValues3},{reqValuesGet5,respGetValues3},{reqValuesGet6,respGetValues4},{reqValuesGet7,respGetValues2},{reqValuesGet8,respGetValues3}};
	 
	return data;
  }
  
  @DataProvider
  public Object[][] getTemplateDP() {
	  Object[][] data = null;
	 
	  HashMap<String,String> reqValuesGet1 = new HashMap<String,String>();
	  reqValuesGet1.putAll(createdTemplate2);
	  reqValuesGet1.put("TC", ""+(1));
	  reqValuesGet1.put("version","1");
	  
	  
	  HashMap<String,String> respGetValues1 = new HashMap<String,String>();
	  respGetValues1.put("status","200");
	  respGetValues1.put("statusMessage","Success");
	  respGetValues1.put("statusType", "Success");
	  respGetValues1.put("version", "1");
	  
	  HashMap<String,String> reqValuesGet2 = new HashMap<String,String>();
	  reqValuesGet2.put("TC", ""+(2));
	  reqValuesGet2.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValuesGet2.put("tags","[\"push\"]");
	  reqValuesGet2.put("appid","10");
	  reqValuesGet2.put("version","100");
	  reqValuesGet2.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> respGetValues2 = new HashMap<String,String>();
	  respGetValues2.put("status","200");
	  respGetValues2.put("statusMessage","NO_RESULT_FOUND");
	  respGetValues2.put("statusType", "Success");
	  
	  
	  HashMap<String,String> reqValuesGet3 = new HashMap<String,String>();
	  reqValuesGet3.put("TC", ""+(3));
	  reqValuesGet3.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValuesGet3.put("tags","[\"push\"]");
	  reqValuesGet3.put("appid","10");
	  reqValuesGet3.put("version","0");
	  reqValuesGet3.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValuesGet4 = new HashMap<String,String>();
	  reqValuesGet4.put("TC", ""+(4));
	  reqValuesGet4.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValuesGet4.put("tags","[\"push\"]");
	  reqValuesGet4.put("appid","10");
	  reqValuesGet4.put("version","-1");
	  reqValuesGet4.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValuesGet5 = new HashMap<String,String>();
	  reqValuesGet5.put("TC", ""+(5));
	  reqValuesGet5.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValuesGet5.put("tags","[\"push\"]");
	  reqValuesGet5.put("appid","10");
	  reqValuesGet5.put("version","null");
	  reqValuesGet5.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> respGetValues3 = new HashMap<String,String>();
	  respGetValues3.put("status","400");
	  respGetValues3.put("statusMessage","INVALID_VERSION");
	  respGetValues3.put("statusType", "Error");
	  
	  
	  HashMap<String,String> reqValuesGet6 = new HashMap<String,String>();
	  reqValuesGet6.put("TC", ""+(6));
	  reqValuesGet6.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValuesGet6.put("tags","[\"push\"]");
	  reqValuesGet6.put("appid","10");
	  reqValuesGet6.put("version","\"1\"");
	  reqValuesGet6.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> respGetValues4 = new HashMap<String,String>();
	  respGetValues4.put("status","400");
	  respGetValues4.put("statusMessage","TEMPLATE_DATA_UNMARSHALL_ERROR");
	  respGetValues4.put("statusType", "Error");
	  
	  
	  HashMap<String,String> reqValuesGet7 = new HashMap<String,String>();
	  reqValuesGet7.put("TC", ""+(7));
	  reqValuesGet7.put("tgid", "\"AutoTgid-Valid\"");
	  reqValuesGet7.put("tags","[\"push\"]");
	  reqValuesGet7.put("appid","10");
	  reqValuesGet7.put("version","3");
	  reqValuesGet7.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValuesGet8 = new HashMap<String,String>();
	  reqValuesGet8.put("TC", ""+(8));
	  reqValuesGet8.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValuesGet8.put("tags","[\"push\"]");
	  reqValuesGet8.put("appid","10");
	  reqValuesGet8.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues1 = new HashMap<String,String>();
	  reqValues1.put("TC", "c1");
	  reqValues1.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValues1.put("tags","[\"push\"]");
	  reqValues1.put("appid","10");
	  reqValues1.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues1.put("version","1");
	  
	  HashMap<String,String> respValues1 = new HashMap<String,String>();
	  respValues1.put("status","200");
	  respValues1.put("statusMessage","NO_RESULT_FOUND");
	  respValues1.put("statusType", "Success");
	  respValues1.put("version", "1");
	  
	  HashMap<String,String> reqValues2 = new HashMap<String,String>();
	  reqValues2.put("TC", "c2");
	  //Trailing space 
	  reqValues2.put("tgid", "\" AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValues2.put("tags","[\"push\"]");
	  reqValues2.put("appid","10");
	  reqValues2.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues2.put("version","1");

	  HashMap<String,String> reqValues3 = new HashMap<String,String>();
	  reqValues3.put("TC", "c3");
	  //Leading space 
	  reqValues3.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+" \"");
	  reqValues3.put("tags","[\"push\"]");
	  reqValues3.put("appid","10");
	  reqValues3.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues3.put("version","1");

	  HashMap<String,String> reqValues4 = new HashMap<String,String>();
	  reqValues4.put("TC", "c4");
	  //Leading space 
	  reqValues4.put("tgid", "\" AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+" \"");
	  reqValues4.put("tags","[\"push\"]");
	  reqValues4.put("appid","10");
	  reqValues4.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues4.put("version","1");

	  HashMap<String,String> reqValues5 = new HashMap<String,String>();
	  reqValues5.put("TC", "c5");
	  //Trailing white space 
	  reqValues5.put("tgid", "\"         AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValues5.put("tags","[\"push\"]");
	  reqValues5.put("appid","10");
	  reqValues5.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues5.put("version","1");

	  HashMap<String,String> reqValues6 = new HashMap<String,String>();
	  reqValues6.put("TC", "c6");
	  //Leading white space 
	  reqValues6.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"           \"");
	  reqValues6.put("tags","[\"push\"]");
	  reqValues6.put("appid","10");
	  reqValues6.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues6.put("version","1");

	  HashMap<String,String> reqValues7 = new HashMap<String,String>();
	  reqValues7.put("TC", "c7");
	  //Leading and Trailing white space 
	  reqValues7.put("tgid", "\"              AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"           \"");
	  reqValues7.put("tags","[\"push\"]");
	  reqValues7.put("appid","10");
	  reqValues7.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues7.put("version","1");

	  HashMap<String,String> reqValues8 = new HashMap<String,String>();
	  reqValues8.put("TC", "c8");
	  //empty value
	  reqValues8.put("tgid", "\"\"");
	  reqValues8.put("tags","[\"push\"]");
	  reqValues8.put("appid","10");
	  reqValues8.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues8.put("version","1");

	  HashMap<String,String> reqValues9 = new HashMap<String,String>();
	  reqValues9.put("TC", "c9");
	  //only Single space value
	  reqValues9.put("tgid", "\" \"");
	  reqValues9.put("tags","[\"push\"]");
	  reqValues9.put("appid","10");
	  reqValues9.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues9.put("version","1");

	  HashMap<String,String> reqValues10 = new HashMap<String,String>();
	  reqValues10.put("TC", "c10");
	  //only white space value
	  reqValues10.put("tgid", "\"        \"");
	  reqValues10.put("tags","[\"push\"]");
	  reqValues10.put("appid","10");
	  reqValues10.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues10.put("version","1");

	  HashMap<String,String> reqValues11 = new HashMap<String,String>();
	  reqValues11.put("TC", "c11");
	  //special character
	  reqValues11.put("tgid", "\""+new LgpPumpsHelper().getcurrentTimestamp()+"#$%^&*(*&^&$%$#$&*&%$#$%^&\"");
	  reqValues11.put("tags","[\"push\"]");
	  reqValues11.put("appid","10");
	  reqValues11.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues11.put("version","1");

	  HashMap<String,String> reqValues12 = new HashMap<String,String>();
	  reqValues12.put("TC", "c12");
	  //numbers
	  reqValues12.put("tgid", "\""+new LgpPumpsHelper().getcurrentTimestamp()+"123467676682824\"");
	  reqValues12.put("tags","[\"push\"]");
	  reqValues12.put("appid","10");
	  reqValues12.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues12.put("version","1");

	  HashMap<String,String> reqValues13 = new HashMap<String,String>();
	  reqValues13.put("TC", "c13");
	  //space in between
	  reqValues13.put("tgid", "\"AutoTgid "+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  reqValues13.put("tags","[\"push\"]");
	  reqValues13.put("appid","10");
	  reqValues13.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues13.put("version","1");

	  HashMap<String,String> respValues2 = new HashMap<String,String>();
	  respValues2.put("status","400");
	  respValues2.put("statusMessage","INVALID_TGID");
	  respValues2.put("statusType", "Error");
	  
	  HashMap<String,String> reqValues14 = new HashMap<String,String>();
	  reqValues14.put("TC", "c14");
	  reqValues14.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Trailing Space
	  reqValues14.put("tags","[\" push\"]");
	  reqValues14.put("appid","10");
	  reqValues14.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues14.put("version","1");

	  HashMap<String,String> reqValues15 = new HashMap<String,String>();
	  reqValues15.put("TC", "c15");
	  reqValues15.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Trailing Space in array
	  reqValues15.put("tags","[     \"push\"]");
	  reqValues15.put("appid","10");
	  reqValues15.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues15.put("version","1");

	  HashMap<String,String> reqValues16 = new HashMap<String,String>();
	  reqValues16.put("TC", "c16");
	  reqValues16.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Trailing Space 
	  reqValues16.put("tags","[\"push\",\"   Notification\"]");
	  reqValues16.put("appid","10");
	  reqValues16.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues16.put("version","1");

	  HashMap<String,String> reqValues17 = new HashMap<String,String>();
	  reqValues17.put("TC", "c17");
	  reqValues17.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading Space 
	  reqValues17.put("tags","[\"push\",\"Notification     \"]");
	  reqValues17.put("appid","10");
	  reqValues17.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues17.put("version","1");

	  HashMap<String,String> reqValues18 = new HashMap<String,String>();
	  reqValues18.put("TC", "c18");
	  reqValues18.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading Space in array
	  reqValues18.put("tags","[\"push\",\"Notification\"       ]");
	  reqValues18.put("appid","10");
	  reqValues18.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues18.put("version","1");

	  HashMap<String,String> reqValues19 = new HashMap<String,String>();
	  reqValues19.put("TC", "c19");
	  reqValues19.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading Space
	  reqValues19.put("tags","[\"push       \",\"Notification\"]");
	  reqValues19.put("appid","10");
	  reqValues19.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues19.put("version","1");

	  HashMap<String,String> reqValues20 = new HashMap<String,String>();
	  reqValues20.put("TC", "c20");
	  reqValues20.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading and Trailing Space
	  reqValues20.put("tags","[\"    push       \",\"          Notification        \"]");
	  reqValues20.put("appid","10");
	  reqValues20.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues20.put("version","1");

	  HashMap<String,String> reqValues21 = new HashMap<String,String>();
	  reqValues21.put("TC", "c21");
	  reqValues21.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading and Trailing Space in array
	  reqValues21.put("tags","[         \"push\",\"Notification\"      ]");
	  reqValues21.put("appid","10");
	  reqValues21.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues21.put("version","1");

	  HashMap<String,String> reqValues22 = new HashMap<String,String>();
	  reqValues22.put("TC", "c22");
	  reqValues22.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Leading and Trailing Space in array
	  reqValues22.put("tags","[         \"     push      \",\"      Notification     \"         ]");
	  reqValues22.put("appid","10");
	  reqValues22.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  reqValues22.put("version","1");

	  HashMap<String,String> reqValues23 = new HashMap<String,String>();
	  reqValues23.putAll(reqValues22);
	  reqValues23.put("TC", "c23");
	  //empty array
	  reqValues23.put("tags","[]");
	  
	  HashMap<String,String> reqValues24 = new HashMap<String,String>();
	  reqValues24.putAll(reqValues22);
	  reqValues24.put("TC", "c24");
	  //empty value
	  reqValues24.put("tags","[\"\"]");
	  
	  HashMap<String,String> reqValues25 = new HashMap<String,String>();
	  reqValues25.putAll(reqValues22);
	  reqValues25.put("TC", "c25");
	  //single space in array
	  reqValues25.put("tags","[ ]");
	  
	  HashMap<String,String> reqValues26 = new HashMap<String,String>();
	  reqValues26.putAll(reqValues22);
	  reqValues26.put("TC", "c26");
	  //white space in array
	  reqValues26.put("tags","[        ]");
	  
	  HashMap<String,String> reqValues27 = new HashMap<String,String>();
	  reqValues27.putAll(reqValues22);
	  reqValues27.put("TC", "c27");
	  //single space
	  reqValues27.put("tags","[\" \"]");
	  
	  HashMap<String,String> reqValues28 = new HashMap<String,String>();
	  reqValues28.putAll(reqValues22);
	  reqValues28.put("TC", "c28");
	  //white space in array
	  reqValues28.put("tags","[\"       \"]");	  
	  
	  HashMap<String,String> reqValues29 = new HashMap<String,String>();
	  reqValues29.putAll(reqValues22);
	  reqValues29.put("TC", "c29");
	  //white space and space
	  reqValues29.put("tags","[\" \",\"        \",\" \"]");
	  
	  HashMap<String,String> reqValues30 = new HashMap<String,String>();
	  reqValues30.putAll(reqValues22);
	  reqValues30.put("TC", "c30");
	  //String, not array
	  reqValues30.put("tags","\"push\"");
	  
	  HashMap<String,String> reqValues31 = new HashMap<String,String>();
	  reqValues31.putAll(reqValues22);
	  reqValues31.put("TC", "c31");
	  //String, not array
	  reqValues31.put("tags","[1,2,3,4]");
	  
	  HashMap<String,String> reqValues32 = new HashMap<String,String>();
	  reqValues32.putAll(reqValues22);
	  reqValues32.put("TC", "c32");
	  //space in between
	  reqValues32.put("tags","[\"push notification\"]");
	  
	  HashMap<String,String> reqValues33 = new HashMap<String,String>();
	  reqValues33.putAll(reqValues22);
	  reqValues33.put("TC", "c33");
	  //numbers
	  reqValues33.put("tags","[\"435678908765435678908\"]");
	  
	  HashMap<String,String> reqValues34 = new HashMap<String,String>();
	  reqValues34.putAll(reqValues22);
	  reqValues34.put("TC", "c34");
	  //special characters
	  reqValues34.put("tags","[\"%^&*()*&^%$#@$%^&^%$}:?><\"]");
	  
	  HashMap<String,String> reqValues35 = new HashMap<String,String>();
	  reqValues35.putAll(reqValues22);
	  reqValues35.put("TC", "c35");
	  //special characters
	  reqValues35.put("tags","[\"%^&*()*&^%$#@$%^&^%$}:?><\",\"%^&*()*&^%$#@$%^&^%$}:?><\",\"+%^&*()*&^%$#@$%^&^%$}:?><"+new LgpPumpsHelper().getcurrentTimestamp()+"\""+"]");
	  
	  
	  HashMap<String,String> reqValues36 = new HashMap<String,String>();
	  reqValues36.putAll(reqValues22);
	  reqValues36.put("TC", "c36");
	  //numbers
	  reqValues36.put("tags","[\"435678908765435678908\",\"435678908765435678908\",\"435678908765435678908\",\"435678908765435678908"+new LgpPumpsHelper().getcurrentTimestamp()+"\""+"]");
	  
	  HashMap<String,String> reqValues37 = new HashMap<String,String>();
	  reqValues37.putAll(reqValues22);
	  reqValues37.put("TC", "c37");
	  //valid
	  reqValues37.put("tags","[\"push\"]");
	  
	  HashMap<String,String> reqValues38 = new HashMap<String,String>();
	  reqValues38.putAll(reqValues22);
	  reqValues38.put("TC", "c38");
	  //valid
	  reqValues38.put("tags","[\"push\",\"notification\",\"inapp\"]");
	  
	  HashMap<String,String> respValues3 = new HashMap<String,String>();
	  respValues3.put("status","400");
	  respValues3.put("statusMessage","INVALID_TAGS");
	  respValues3.put("statusType", "Error");
	  
	  HashMap<String,String> reqValues39 = new HashMap<String,String>();
	  reqValues39.putAll(reqValues22);
	  reqValues39.put("TC", "c39");
	  reqValues39.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //valid
	  reqValues39.put("template", "\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues40 = new HashMap<String,String>();
	  reqValues40.putAll(reqValues22);
	  reqValues40.put("TC", "c40");
	  //null
	  reqValues40.put("template","null");
	  
	  HashMap<String,String> reqValues41 = new HashMap<String,String>();
	  reqValues41.putAll(reqValues22);
	  reqValues41.put("TC", "c41");
	  //Integer value
	  reqValues41.put("template","123");
	  
	  HashMap<String,String> reqValues42 = new HashMap<String,String>();
	  reqValues42.putAll(reqValues22);
	  reqValues42.put("TC", "c42");
	  //empty value
	  reqValues42.put("template","\"\"");
	  
	  HashMap<String,String> reqValues43 = new HashMap<String,String>();
	  reqValues43.putAll(reqValues22);
	  reqValues43.put("TC", "c43");
	  reqValues43.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //leading space
	  reqValues43.put("template","\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}       \"");
	  
	  HashMap<String,String> reqValues44 = new HashMap<String,String>();
	  reqValues44.putAll(reqValues22);
	  reqValues44.put("TC", "c44");
	  reqValues44.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //trailing space
	  reqValues44.put("template","\"         {\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues45 = new HashMap<String,String>();
	  reqValues45.putAll(reqValues22);
	  reqValues45.put("TC", "c45");
	  reqValues45.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //leading and trailing space
	  reqValues45.put("template","\"         {\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationType\\\":\\\"Engagement\\\",\\\"pageUrl\\\":{$pageurl$}}       \"");
	  
	  HashMap<String,String> reqValues46 = new HashMap<String,String>();
	  reqValues46.putAll(reqValues22);
	  reqValues46.put("TC", "c46");
	  reqValues46.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //special characters
	  reqValues46.put("template","\"{\\\"@#$%^&*(*&^%$#$%^&*^%$#@!#$%^:><,.?/\\\":\\\"&^%&*(%$^&^^*&^\\\"}\"");
	  
	  HashMap<String,String> reqValues47 = new HashMap<String,String>();
	  reqValues47.putAll(reqValues22);
	  reqValues47.put("TC", "c47");
	  reqValues47.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //numbers
	  reqValues47.put("template","\"{\\\"12345678987654321\\\":\\\"34567\\\"}\"");
	  
	  HashMap<String,String> reqValues48 = new HashMap<String,String>();
	  reqValues48.putAll(reqValues22);
	  reqValues48.put("TC", "c48");
	  reqValues48.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Capital case attributes
	  reqValues48.put("template","\"{\\\"HEADING\\\":\\\"Myntra\\\",\\\"TITLE\\\":\\\"Myntra Support\\\",\\\"DESCRIPTION\\\":\\\"{$description$}\\\",\\\"NOTIFICATIONTYPE\\\":\\\"Engagement\\\",\\\"PAGEURL\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> reqValues49 = new HashMap<String,String>();
	  reqValues49.putAll(reqValues22);
	  reqValues49.put("TC", "c49");
	  reqValues49.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //small case attribute
	  reqValues49.put("template","\"{\\\"heading\\\":\\\"Myntra\\\",\\\"title\\\":\\\"Myntra Support\\\",\\\"description\\\":\\\"{$description$}\\\",\\\"notificationtype\\\":\\\"Engagement\\\",\\\"pageurl\\\":{$pageurl$}}\"");
	  
	  HashMap<String,String> respValues4 = new HashMap<String,String>();
	  respValues4.put("status","400");
	  respValues4.put("statusMessage","INVALID_TEMPLATE");
	  respValues4.put("statusType", "Error");
	  
	  HashMap<String,String> reqValues50 = new HashMap<String,String>();
	  reqValues50.putAll(reqValues22);
	  reqValues50.put("TC", "c50");
	  reqValues50.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //valid
	  reqValues50.put("appid","15");
	  
	  HashMap<String,String> reqValues51 = new HashMap<String,String>();
	  reqValues51.putAll(reqValues22);
	  reqValues51.put("TC", "c51");
	  //String value for int attribute
	  reqValues51.put("appid","\"15\"");
	  
	  HashMap<String,String> reqValues52 = new HashMap<String,String>();
	  reqValues52.putAll(reqValues22);
	  reqValues52.put("TC", "c52");
	  //null
	  reqValues52.put("appid","null");
	  
	  HashMap<String,String> reqValues53 = new HashMap<String,String>();
	  reqValues53.putAll(reqValues22);
	  reqValues53.put("TC", "c53");
	  //negative value
	  reqValues53.put("appid","-10");
	  
	  HashMap<String,String> reqValues54 = new HashMap<String,String>();
	  reqValues54.putAll(reqValues22);
	  reqValues54.put("TC", "c54");
	  //negative value
	  reqValues54.put("appid","0");
	  
	  HashMap<String,String> reqValues55 = new HashMap<String,String>();
	  reqValues55.putAll(reqValues22);
	  reqValues55.put("TC", "c55");
	  reqValues55.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //leading space
	  reqValues55.put("appid","10   ");
	 
	  HashMap<String,String> reqValues56 = new HashMap<String,String>();
	  reqValues56.putAll(reqValues22);
	  reqValues56.put("TC", "c56");
	  reqValues56.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Trailing space
	  reqValues56.put("appid","   10");
	  
	  HashMap<String,String> reqValues57 = new HashMap<String,String>();
	  reqValues57.putAll(reqValues22);
	  reqValues57.put("TC", "c57");
	  reqValues57.put("tgid", "\"AutoTgid"+new LgpPumpsHelper().getcurrentTimestamp()+"\"");
	  //Trailing and leading space
	  reqValues57.put("appid","   10      ");
	  
	  HashMap<String,String> respValues5 = new HashMap<String,String>();
	  respValues5.put("status","400");
	  respValues5.put("statusMessage","INVALID_APPID");
	  respValues5.put("statusType", "Error");
	  
	  HashMap<String,String> reqValues58 = new HashMap<String,String>();
	  reqValues58.putAll(reqValues22);
	  reqValues58.put("TC", "c58");
	  //Integer value
	  reqValues58.put("tgid", "123467676682824");
	  
	  HashMap<String,String> reqValues59 = new HashMap<String,String>();
	  reqValues59.putAll(reqValues22);
	  reqValues59.put("TC", "c59");
	  //Integer value
	  reqValues59.put("tags", "123424");
	  
	  HashMap<String,String> reqValues60 = new HashMap<String,String>();
	  reqValues60.putAll(reqValues22);
	  reqValues60.put("TC", "c60");
	  //null
	  reqValues60.put("tags", "null");
	  
	  HashMap<String,String> reqValues61 = new HashMap<String,String>();
	  reqValues61.putAll(reqValues22);
	  reqValues61.put("TC", "c61");
	  //null
	  reqValues61.put("tgid", "null");
	  
	  HashMap<String,String> reqValues62 = new HashMap<String,String>();
	  reqValues62.putAll(reqValues1);
	  reqValues62.put("TC", "c62");
	  //no tgid attribute
	  reqValues62.remove("tgid");
	  
	  HashMap<String,String> reqValues63 = new HashMap<String,String>();
	  reqValues63.putAll(reqValues1);
	  reqValues63.put("TC", "c63");
	  //no tags attribute
	  reqValues63.remove("tags");
	  
	  HashMap<String,String> reqValues64 = new HashMap<String,String>();
	  reqValues64.putAll(reqValues1);
	  reqValues64.put("TC", "c64");
	  //no appid attribute
	  reqValues64.remove("appid");
	  
	  HashMap<String,String> reqValues65 = new HashMap<String,String>();
	  reqValues65.putAll(reqValues1);
	  reqValues65.put("TC", "c65");
	  //no template attribute
	  reqValues65.remove("template");
	  

	  
	  data =new Object[][]{{reqValues1,respValues1},{reqValues2,respValues1},{reqValues3,respValues1},{reqValues4,respValues1},{reqValues5,respValues1},{reqValues6,respValues1},{reqValues7,respValues1},
		  {reqValues8,respValues2},{reqValues9,respValues2},{reqValues10,respValues2},{reqValues11,respValues1},{reqValues12,respValues1},{reqValues13,respValues2},
		  {reqValues14,respValues1},{reqValues15,respValues1},{reqValues16,respValues1},{reqValues17,respValues1},{reqValues18,respValues1},{reqValues19,respValues1},{reqValues20,respValues1},{reqValues21,respValues1},{reqValues22,respValues1},
		  {reqValues23,respValues3},{reqValues24,respValues3},{reqValues25,respValues3},{reqValues26,respValues3},{reqValues27,respValues3},{reqValues28,respValues3},{reqValues29,respValues3},{reqValues30,respGetValues4},{reqValues31,respGetValues4},
		  {reqValues32,respValues3},{reqValues33,respValues1},{reqValues34,respValues1},{reqValues35,respValues1},{reqValues36,respValues1},{reqValues37,respValues1},{reqValues38,respValues1},{reqValues39,respValues1},{reqValues40,respGetValues2},
		  {reqValues41,respGetValues4},{reqValues42,respGetValues2},{reqValues43,respValues1},{reqValues44,respValues1},{reqValues45,respValues1},{reqValues46,respValues1},{reqValues47,respValues1},{reqValues48,respValues1},{reqValues49,respValues1},
		  {reqValues50,respValues1},{reqValues51,respGetValues4},{reqValues52,respValues5},{reqValues53,respValues5},{reqValues54,respValues5},{reqValues55,respValues1},{reqValues56,respValues1},{reqValues57,respValues1},{reqValues58,respGetValues4},
		  {reqValues59,respGetValues4},{reqValues60,respValues3},{reqValues61,respValues2},{reqValues62,respValues2},{reqValues63,respValues3},{reqValues64,respValues5},{reqValues65,respGetValues2},{reqValuesGet1,respGetValues1},{reqValuesGet2,respGetValues2},{reqValuesGet3,respGetValues3},{reqValuesGet4,respGetValues3},
		  {reqValuesGet5,respGetValues3},{reqValuesGet6,respGetValues4},{reqValuesGet7,respGetValues2},{reqValuesGet8,respGetValues3}};
	  return data;
  }

}
