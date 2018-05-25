package com.myntra.apiTests.dataproviders;


import java.util.HashMap;
import java.util.Map;

import com.myntra.apiTests.portalservices.lgpservices.LgpSessionServiceHelper;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.portalservices.lgpservices.SessionServiceTests;

public class LgpSessionServiceDP extends LgpSessionServiceHelper {
	
	String envName = SessionServiceTests.init.Configurations.GetTestEnvironemnt().toString().toLowerCase();

	@DataProvider
	public Object[][] createSessionXMLDP(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7")){
			
			String [] payloadReference1 = {createJson("case1"),"case1"};
			String [] payloadReference2 = {createJson("case2"),"case2"};
			String [] payloadReference3 = {createJson("case3A")+"=="+createJson("case3B"),"case3"};
			String [] payloadReference4 = {createJson("case4A")+"=="+createJson("case4B"),"case4"};
			String [] payloadReference5 = {"","case5"};
			String [] payloadReference6 = {"","case6"};
			String [] payloadReference7 = {"","case7"};
			String [] payloadReference8 = {createJson("case8"),"case8"};
			String [] payloadReference9 = {createJson("case9"),"case9"};
			String [] payloadReference10 = {createJson("case10"),"case10"};
			String [] payloadReference11 = {createJson("case11"),"case11"};
			String [] payloadReference12 = {"5====7","case12"};
			
			dataSet = new Object[][]{payloadReference1,payloadReference2,payloadReference3,payloadReference4,payloadReference5,
				payloadReference6,payloadReference7,payloadReference8,payloadReference9,payloadReference10,payloadReference11,
				payloadReference12};
			
		}else if(envName.equalsIgnoreCase("production")){
			
			String [] payloadReference1 = {createJson("case1"),"case1"};
			String [] payloadReference2 = {createJson("case2"),"case2"};
			String [] payloadReference3 = {createJson("case3A")+"=="+createJson("case3B"),"case3"};
			String [] payloadReference4 = {createJson("case4A")+"=="+createJson("case4B"),"case4"};
			String [] payloadReference5 = {"","case5"};
			String [] payloadReference6 = {"","case6"};
			String [] payloadReference7 = {"","case7"};
			String [] payloadReference8 = {createJson("case8"),"case8"};
			String [] payloadReference9 = {createJson("case9"),"case9"};
			String [] payloadReference10 = {createJson("case10"),"case10"};
			String [] payloadReference11 = {createJson("case11"),"case11"};
			String [] payloadReference12 = {"5====7","case12"};
			
			dataSet = new Object[][]{payloadReference1,payloadReference2,payloadReference3,payloadReference4,payloadReference5,
				payloadReference6,payloadReference7,payloadReference8,payloadReference9,payloadReference10,payloadReference11,
				payloadReference12};
			
			
		}
		return dataSet;
		
	}
	
	@DataProvider
	public Object[][] optCreateSessionJsonDP(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7")){
			
			String [] payloadReference1 = {createJson("case1"),"case1"};
			String [] payloadReference2 = {"5====7","case2"};
			String [] payloadReference3 = {createJson("case1"),"case3"};
			String [] payloadReference4 = {createJson("case2"),"case4"};
			String [] payloadReference5 = {"","case5"};
			String [] payloadReference8 = {createJson("case8"),"case8"};
			String [] payloadReference9 = {createJson("case9"),"case9"};
			String [] payloadReference10 = {createJson("case10"),"case10"};
			String [] payloadReference11 = {createJson("case11"),"case11"};
			
			dataSet = new Object[][]{payloadReference1,payloadReference2,payloadReference3,payloadReference4,payloadReference5,
				payloadReference8,payloadReference9,payloadReference10,payloadReference11};
			
		}else if(envName.equalsIgnoreCase("production")){
			
			String [] payloadReference1 = {createJson("case1"),"case1"};
			String [] payloadReference2 = {"5====7","case2"};
			String [] payloadReference3 = {createJson("case1"),"case3"};
			String [] payloadReference4 = {createJson("case2"),"case4"};
			String [] payloadReference5 = {"","case5"};
			String [] payloadReference8 = {createJson("case8"),"case8"};
			String [] payloadReference9 = {createJson("case9"),"case9"};
			String [] payloadReference10 = {createJson("case10"),"case10"};
			String [] payloadReference11 = {createJson("case11"),"case11"};
			
			dataSet = new Object[][]{payloadReference1,payloadReference2,payloadReference3,payloadReference4,payloadReference5,
				payloadReference8,payloadReference9,payloadReference10,payloadReference11};
			
			
		}
		return dataSet;
	}
	
	@DataProvider
	public Object[][] getSessionForXMLandJsonDP(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7")){
			
			String [] payloadReference1 = {SessionServiceTests.sessionValue,"case1"};
			String [] payloadReference2 = {"JJN5713c183822470583601G","case2"};
			String [] payloadReference7 = {SessionServiceTests.sessionValue+"    ","case7"};
			
			dataSet = new Object[][]{payloadReference1,payloadReference2,payloadReference7};
			
		}else if(envName.equalsIgnoreCase("production")){
			
			String [] payloadReference1 = {SessionServiceTests.sessionValue,"case1"};
			String [] payloadReference2 = {"JJN5713c183822470583601G","case2"};
			String [] payloadReference7 = {SessionServiceTests.sessionValue+"    ","case7"};
			
			dataSet = new Object[][]{payloadReference1,payloadReference2,payloadReference7};
			
		}
		return dataSet;
		
	}
	
	@DataProvider
	public Object[][] partialUpdateSessionDP(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7")){
			
			String [] payloadReference1 = {SessionServiceTests.sessionValue,createJson("case3A"),"case1"};
			String [] payloadReference2 = {SessionServiceTests.sessionValue,createJson("case3A"),"case2"};
			String [] payloadReference3 = {SessionServiceTests.sessionValue,createJson("case3A")+"=="+createJson("case3B"),"case3"};
			String [] payloadReference4 = {SessionServiceTests.sessionValue,createJson("case4A")+"=="+createJson("case4B"),"case4"};
			String [] payloadReference5 = {SessionServiceTests.sessionValue,null,"case5"};
			String [] payloadReference6 = {SessionServiceTests.sessionValue,null,"case6"};
			String [] payloadReference7 = {SessionServiceTests.sessionValue,null,"case7"};
			String [] payloadReference8 = {SessionServiceTests.sessionValue,createJson("case8"),"case8"};
			String [] payloadReference9 = {SessionServiceTests.sessionValue,createJson("case9"),"case9"};
			String [] payloadReference10 = {SessionServiceTests.sessionValue,createJson("case10"),"case10"};
			String [] payloadReference11 = {SessionServiceTests.sessionValue,createJson("case11"),"case11"};
			String [] payloadReference12 = {"JJN3749bf0564d611e6acd722000a90a0271471478189G",createJson("case3A"),"case12"};
			String [] payloadReference13 = {SessionServiceTests.sessionValue,createJson("case3A"),"case13"};
			String [] payloadReference14 = {"JJN3749bf0564d611e6acd  722000a90a0271471478189G",createJson("case3A"),"case14"};
			String [] payloadReference15 = {SessionServiceTests.sessionValue,createJson("case3A"),"case15"};
			String [] payloadReference16 = {SessionServiceTests.sessionValue,createJson("case3A"),"case16"};
			String [] payloadReference17 = {SessionServiceTests.sessionValue,createJson("case3A"),"case17"};
			String [] payloadReference18 = {SessionServiceTests.sessionValue,createJson("case3A"),"case18"};
			String [] payloadReference19 = {SessionServiceTests.sessionValue,createJson("case3A")+"=="+createJson("case3B"),"case19"};
			String [] payloadReference20 = {SessionServiceTests.sessionValue,createJson("case3A")+"=="+createJson("case3B"),"case20"};
			String [] payloadReference21 = {"JJN3749bf0564d611e6acd722000a90a0271471478189G",createJson("case3A"),"case21"};
			String [] payloadReference22 = {"JJN3749bf0564d611e6acd722000a90a0271471478189G",createJson("case3A"),"case22"};
			String [] payloadReference23 = {SessionServiceTests.sessionValue,"5===7","case23"};
			String [] payloadReference24 = {"randomString","5===7","case24"};
			
			dataSet = new Object[][]{payloadReference1,payloadReference2,payloadReference3,payloadReference4,payloadReference5,payloadReference6,payloadReference7,
				payloadReference8,payloadReference9,payloadReference10,payloadReference11,payloadReference12,payloadReference13,payloadReference14,
				payloadReference15,payloadReference16,payloadReference17,payloadReference18,payloadReference19,payloadReference20,payloadReference21,payloadReference22,
				payloadReference23,payloadReference24};
			
		}else if(envName.equalsIgnoreCase("production")){
			
			String [] payloadReference1 = {SessionServiceTests.sessionValue,createJson("case3A"),"case1"};
			String [] payloadReference2 = {SessionServiceTests.sessionValue,createJson("case3A"),"case2"};
			String [] payloadReference3 = {SessionServiceTests.sessionValue,createJson("case3A")+"=="+createJson("case3B"),"case3"};
			String [] payloadReference4 = {SessionServiceTests.sessionValue,createJson("case4A")+"=="+createJson("case4B"),"case4"};
			String [] payloadReference5 = {SessionServiceTests.sessionValue,null,"case5"};
			String [] payloadReference6 = {SessionServiceTests.sessionValue,null,"case6"};
			String [] payloadReference7 = {SessionServiceTests.sessionValue,null,"case7"};
			String [] payloadReference8 = {SessionServiceTests.sessionValue,createJson("case8"),"case8"};
			String [] payloadReference9 = {SessionServiceTests.sessionValue,createJson("case9"),"case9"};
			String [] payloadReference10 = {SessionServiceTests.sessionValue,createJson("case10"),"case10"};
			String [] payloadReference11 = {SessionServiceTests.sessionValue,createJson("case11"),"case11"};
			String [] payloadReference12 = {"JJN3749bf0564d611e6acd722000a90a0271471478189G",createJson("case3A"),"case12"};
			String [] payloadReference13 = {SessionServiceTests.sessionValue,createJson("case3A"),"case13"};
			String [] payloadReference14 = {"JJN3749bf0564d611e6acd  722000a90a0271471478189G",createJson("case3A"),"case14"};
			String [] payloadReference15 = {SessionServiceTests.sessionValue,createJson("case3A"),"case15"};
			String [] payloadReference16 = {SessionServiceTests.sessionValue,createJson("case3A"),"case16"};
			String [] payloadReference17 = {SessionServiceTests.sessionValue,createJson("case3A"),"case17"};
			String [] payloadReference18 = {SessionServiceTests.sessionValue,createJson("case3A"),"case18"};
			String [] payloadReference19 = {SessionServiceTests.sessionValue,createJson("case3A")+"=="+createJson("case3B"),"case19"};
			String [] payloadReference20 = {SessionServiceTests.sessionValue,createJson("case3A")+"=="+createJson("case3B"),"case20"};
			String [] payloadReference21 = {"JJN3749bf0564d611e6acd722000a90a0271471478189G",createJson("case3A"),"case21"};
			String [] payloadReference22 = {"JJN3749bf0564d611e6acd722000a90a0271471478189G",createJson("case3A"),"case22"};
			String [] payloadReference23 = {SessionServiceTests.sessionValue,"5===7","case23"};
			String [] payloadReference24 = {"randomString","5===7","case24"};
			
			dataSet = new Object[][]{payloadReference1,payloadReference2,payloadReference3,payloadReference4,payloadReference5,payloadReference6,payloadReference7,
				payloadReference8,payloadReference9,payloadReference10,payloadReference11,payloadReference12,payloadReference13,payloadReference14,
				payloadReference15,payloadReference16,payloadReference17,payloadReference18,payloadReference19,payloadReference20,payloadReference21,payloadReference22,
				payloadReference23,payloadReference24};
			
			
		}
		return dataSet;
		
	}
	
	@DataProvider
	public Object[][] optUpdatePartialSession_DP(){
		Object[][] dataSet = null;
		//Case 1
		Map<String,Object> case1 = new HashMap<>();
		case1.put("createSessionPayload", "{"+
    "\"key\": \"gotTheKey\","+
    "\"value\": \"gotTheVALUE\","+
    "\"deep\": {"+
        "\"show\": \"Better\","+
        "\"time\": \"today\","+
        "\"deep\": {"+
            "\"key\": \"key\","+
            "\"value\": \"value\""+
        "}"+
    "}"+
"}");
		case1.put("updateSessionPayload", "{"+
    "\"key\": \"new1111 key\","+
    "\"deep\" : {"+
        "\"deep\": {"+
          "\"key\": \"new key\","+
          "\"value\": \"value\""+
        "},"+
        "\"show\": \"Better\","+
        "\"time\": \"today\""+
    "},"+
    "\"val\": \"new val\""+
"}");
		case1.put("expectedResponse", 200);
		case1.put("case", "case1");
		// Case 2
		Map<String,Object> case2 = new HashMap<>();
		case2.put("case", "case2");
		case2.put("createSessionPayload", "{"+
			    "\"key\": \"gotTheKey\","+
			    "\"value\": \"gotTheVALUE\","+
			    "\"deep\": {"+
			        "\"show\": \"Better\","+
			        "\"time\": \"today\","+
			        "\"deep\": {"+
			            "\"key\": \"key\","+
			            "\"value\": \"value\""+
			        "}"+
			    "}"+
			"}");
		case2.put("updateSessionPayload", "{"+
			    "\"key\": \"new1111 key\","+
			    "\"deep\" : {"+
			        "\"deep\": {"+
			          "\"key\": \"new key\","+
			          "\"value\": \"value\""+
			        "},"+
			        "\"show\": \"Better\","+
			        "\"time\": \"today\""+
			    "},afwefa"+
			    //Malformed json here
			"afaw}");
		case2.put("expectedResponse", 200);
		
		//Case 3 
		Map<String,Object> case3 = new HashMap<>();
		case3.put("createSessionPayload", "{"+
			    "\"key\": \"gotTheKey\","+
			    "\"value\": \"gotTheVALUE\","+
			    "\"deep\": {"+
			        "\"show\": \"Better\","+
			        "\"time\": \"today\","+
			        "\"deep\": {"+
			            "\"key\": \"key\","+
			            "\"value\": \"value\""+
			        "}"+
			    "}"+
			"}");
		case3.put("updateSessionPayload", "{"+
    "\"key\": \"new1111 key\","+
    "\"deep\" : {"+
        "\"deep\": {"+
          "\"key\": \"new key\","+
          "\"value\": \"value\""+
        "},"+
        "\"show\": \"Better\","+
        "\"time\": \"today\""+
    "},"+
    "\"val\": \"new val\""+
"}");
		case3.put("XED", 600);
		case3.put("expectedResponse", 200);
		
		//Case 4 
		Map<String,Object> case4 = new HashMap<>();
		case4.put("createSessionPayload", "{"+
			    "\"key\": \"gotTheKey\","+
			    "\"value\": \"gotTheVALUE\","+
			    "\"deep\": {"+
			        "\"show\": \"Better\","+
			        "\"time\": \"today\","+
			        "\"deep\": {"+
			            "\"key\": \"key\","+
			            "\"value\": \"value\""+
			        "}"+
			    "}"+
			"}");
		case4.put("updateSessionPayload", "{"+
    "\"key\": \"new1111 key\","+
    "\"deep\" : {"+
        "\"deep\": {"+
          "\"key\": \"new key\","+
          "\"value\": \"value\""+
        "},"+
        "\"show\": \"Better\","+
        "\"time\": \"today\""+
    "},"+
    "\"val\": \"new val\""+
"}");
		case4.put("XED", -600);
		case4.put("expectedResponse", 404);
		
		//Case 5
				Map<String,Object> case5 = new HashMap<>();
				case5.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case5.put("updateSessionPayload", null);
				case5.put("expectedResponse", 400);
				
				//Case 6
				Map<String,Object> case6 = new HashMap<>();
				case6.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case6.put("updateSessionPayload", "\b");
				case6.put("expectedResponse", 200);
				
				//Case 8
				Map<String,Object> case8 = new HashMap<>();
				case8.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case8.put("updateSessionPayload", "{"+
		    "\"key&*\": \"new1111 key^\","+
		    "\"deep@#\" : {"+
		        "\"deep#$\": {"+
		          "\"@#key\": \"new #!#key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show$%%@\": \"Better!@\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new va#@%l\""+
		"}");
				case8.put("expectedResponse", 200);
				//Case 9
				Map<String,Object> case9 = new HashMap<>();
				case9.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case9.put("updateSessionPayload", "     ");
				case9.put("updateFailureStatus", 400);
				
				//Case 10
				Map<String,Object> case10 = new HashMap<>();
				case10.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case10.put("updateSessionPayload", "    {"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}");
				case10.put("expectedResponse", 200);
				
				//Case 11
				Map<String,Object> case11 = new HashMap<>();
				case11.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case11.put("updateSessionPayload", "{"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}           ");
				case11.put("expectedResponse", 200);
				
				//Case 12
				Map<String,Object> case12 = new HashMap<>();
				case12.put("xid", "JJNb00071665edf11e691b122000a90a0271470822550G");
				case12.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case12.put("updateSessionPayload", "{"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}           ");
				case12.put("updateFailureStatus", 404);
				
				//Case 13
				Map<String,Object> case13 = new HashMap<>();
				case13.put("xid", "");
				case13.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case13.put("updateSessionPayload", "{"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}           ");
				case13.put("updateFailureStatus", 400);
				
				//Case 14
				Map<String,Object> case14 = new HashMap<>();
				case14.put("xid", " ");
				case14.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case14.put("updateSessionPayload", "{"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}           ");
				case14.put("updateFailureStatus", 400);
				
				//Case 18
				Map<String,Object> case18 = new HashMap<>();
				case18.put("xid", "");
				case18.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case18.put("updateSessionPayload", "{"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}           ");
				case18.put("updateFailureStatus", 400);
				
				//Case 19
				Map<String,Object> case19 = new HashMap<>();
				case19.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case19.put("updateSessionPayload", "{"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}           ");
				case19.put("XLA", -600);
				case19.put("expectedResponse", 200);
				
				//Case 20
				Map<String,Object> case20 = new HashMap<>();
				case20.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case20.put("updateSessionPayload", "{"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}           ");
				case20.put("XLM", -600);
				case20.put("expectedResponse", 200);
				
				//Case 21
				Map<String,Object> case21 = new HashMap<>();
				case21.put("xid", "kasofija");
				case21.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case21.put("updateSessionPayload", "{"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}           ");
				case21.put("updateFailureStatus", 400);
		dataSet = new Object[][]{{case1},{case3},{case4},{case6},{case8},{case9},{case10},{case11},{case12},{case13},{case18},{case19},{case20},{case21}};	
		//dataSet = new Object[][]{{case14}};
		return dataSet;
	}
	
	

	@DataProvider
	public Object[][] optChangeSession_DP(){
		Object[][] dataSet = null;
		//Case 1
		Map<String,Object> case1 = new HashMap<>();
		case1.put("createSessionPayload", "{"+
    "\"key\": \"gotTheKey\","+
    "\"value\": \"gotTheVALUE\","+
    "\"deep\": {"+
        "\"show\": \"Better\","+
        "\"time\": \"today\","+
        "\"deep\": {"+
            "\"key\": \"key\","+
            "\"value\": \"value\""+
        "}"+
    "}"+
"}");
		case1.put("changeSessionPayload", "{"+
    "\"key\": \"new1111 key\","+
    "\"deep\" : {"+
        "\"deep\": {"+
          "\"key\": \"new key\","+
          "\"value\": \"value\""+
        "},"+
        "\"show\": \"Better\","+
        "\"time\": \"today\""+
    "},"+
    "\"val\": \"new val\""+
"}");
		case1.put("expectedResponse", 200);
		case1.put("case", "case1");
		// Case 2
		Map<String,Object> case2 = new HashMap<>();
		case2.put("case", "case2");
		case2.put("createSessionPayload", "{"+
			    "\"key\": \"gotTheKey\","+
			    "\"value\": \"gotTheVALUE\","+
			    "\"deep\": {"+
			        "\"show\": \"Better\","+
			        "\"time\": \"today\","+
			        "\"deep\": {"+
			            "\"key\": \"key\","+
			            "\"value\": \"value\""+
			        "}"+
			    "}"+
			"}");
		case2.put("changeSessionPayload", "{"+
			    "\"key\": \"new1111 key\","+
			    "\"deep\" : {"+
			        "\"deep\": {"+
			          "\"key\": \"new key\","+
			          "\"value\": \"value\""+
			        "},"+
			        "\"show\": \"Better\","+
			        "\"time\": \"today\""+
			    "},afwefa"+
			    //Malformed json here
			"afaw}");
		case2.put("expectedResponse", 200);
		
		//Case 3 
		Map<String,Object> case3 = new HashMap<>();
		case3.put("createSessionPayload", "{"+
			    "\"key\": \"gotTheKey\","+
			    "\"value\": \"gotTheVALUE\","+
			    "\"deep\": {"+
			        "\"show\": \"Better\","+
			        "\"time\": \"today\","+
			        "\"deep\": {"+
			            "\"key\": \"key\","+
			            "\"value\": \"value\""+
			        "}"+
			    "}"+
			"}");
		case3.put("changeSessionPayload", "{"+
    "\"key\": \"new1111 key\","+
    "\"deep\" : {"+
        "\"deep\": {"+
          "\"key\": \"new key\","+
          "\"value\": \"value\""+
        "},"+
        "\"show\": \"Better\","+
        "\"time\": \"today\""+
    "},"+
    "\"val\": \"new val\""+
"}");
		case3.put("XED", 600);
		case3.put("expectedResponse", 200);
		
		//Case 4 
		Map<String,Object> case4 = new HashMap<>();
		case4.put("createSessionPayload", "{"+
			    "\"key\": \"gotTheKey\","+
			    "\"value\": \"gotTheVALUE\","+
			    "\"deep\": {"+
			        "\"show\": \"Better\","+
			        "\"time\": \"today\","+
			        "\"deep\": {"+
			            "\"key\": \"key\","+
			            "\"value\": \"value\""+
			        "}"+
			    "}"+
			"}");
		case4.put("changeSessionPayload", "{"+
    "\"key\": \"new1111 key\","+
    "\"deep\" : {"+
        "\"deep\": {"+
          "\"key\": \"new key\","+
          "\"value\": \"value\""+
        "},"+
        "\"show\": \"Better\","+
        "\"time\": \"today\""+
    "},"+
    "\"val\": \"new val\""+
"}");
		case4.put("XED", -600);
		case4.put("changeFailureStatus", 400);
		
		//Case 5 Not applicable
				Map<String,Object> case5 = new HashMap<>();
				case5.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case5.put("changeSessionPayload", null);
				case5.put("changeFailureStatus", 400);
				
				//Case 6
				Map<String,Object> case6 = new HashMap<>();
				case6.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case6.put("changeSessionPayload", "\b");
				case6.put("expectedResponse", 200);
				
				//Case 8
				Map<String,Object> case8 = new HashMap<>();
				case8.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case8.put("changeSessionPayload", "{"+
		    "\"key&*\": \"new1111 key^\","+
		    "\"deep@#\" : {"+
		        "\"deep#$\": {"+
		          "\"@#key\": \"new #!#key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show$%%@\": \"Better!@\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new va#@%l\""+
		"}");
				case8.put("expectedResponse", 200);
				//Case 9
				Map<String,Object> case9 = new HashMap<>();
				case9.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case9.put("changeSessionPayload", "     ");
				case9.put("changeFailureStatus", 400);
				
				//Case 10
				Map<String,Object> case10 = new HashMap<>();
				case10.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case10.put("changeSessionPayload", "    {"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}");
				case10.put("expectedResponse", 200);
				
				//Case 11
				Map<String,Object> case11 = new HashMap<>();
				case11.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case11.put("changeSessionPayload", "{"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}           ");
				case11.put("expectedResponse", 200);
				
				//Case 12
				Map<String,Object> case12 = new HashMap<>();
				case12.put("xid", "JJNb00071665edf11e691b122000a90a0271470822550G");
				case12.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case12.put("changeSessionPayload", "{"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}           ");
				case12.put("changeFailureStatus", 404);
				
				//Case 13
				Map<String,Object> case13 = new HashMap<>();
				case13.put("xid", "");
				case13.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case13.put("changeSessionPayload", "{"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}           ");
				case13.put("changeFailureStatus", 400);
				
				//Case 14
				Map<String,Object> case14 = new HashMap<>();
				case14.put("xid", " ");
				case14.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case14.put("changeSessionPayload", "{"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}           ");
				case14.put("changeFailureStatus", 400);
				
				//Case 18
				Map<String,Object> case18 = new HashMap<>();
				case18.put("xid", "");
				case18.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case18.put("changeSessionPayload", "{"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}           ");
				case18.put("changeFailureStatus", 400);
				
				//Case 19
				Map<String,Object> case19 = new HashMap<>();
				case19.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case19.put("changeSessionPayload", "{"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}           ");
				case19.put("XLA", -600);
				case19.put("expectedResponse", 200);
				
				//Case 20
				Map<String,Object> case20 = new HashMap<>();
				case20.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case20.put("changeSessionPayload", "{"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}           ");
				case20.put("XLM", -600);
				case20.put("expectedResponse", 200);
				
				//Case 21
				Map<String,Object> case21 = new HashMap<>();
				case21.put("xid", "kasofija");
				case21.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case21.put("changeSessionPayload", "{"+
		    "\"key\": \"new1111 key\","+
		    "\"deep\" : {"+
		        "\"deep\": {"+
		          "\"key\": \"new key\","+
		          "\"value\": \"value\""+
		        "},"+
		        "\"show\": \"Better\","+
		        "\"time\": \"today\""+
		    "},"+
		    "\"val\": \"new val\""+
		"}           ");
				case21.put("changeFailureStatus", 400);
		dataSet = new Object[][]{{case1},{case3},{case4},{case6},{case8},{case9},{case10},{case11},{case12},{case13},{case18},{case19},{case20},{case21}};	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] optGetSession_DP(){
		
		Object[][] dataSet = null;
		//Case 1
		Map<String,Object> case1 = new HashMap<>();
		
		case1.put("createSessionPayload", "{"+
			    "\"key\": \"gotTheKey\","+
			    "\"value\": \"gotTheVALUE\","+
			    "\"deep\": {"+
			        "\"show\": \"Better\","+
			        "\"time\": \"today\","+
			        "\"deep\": {"+
			            "\"key\": \"key\","+
			            "\"value\": \"value\""+
			        "}"+
			    "}"+
			"}");
		case1.put("expectedResponse", 200);
		
		//Case 2
				Map<String,Object> case2 = new HashMap<>();
				case2.put("xid", "JJN724c4b6463af11e6acd722000a90a0271471351586G");
				case2.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case2.put("expectedResponse", 404);
				
				//Case 6
				Map<String,Object> case6 = new HashMap<>();
				case6.put("xid", "");
				case6.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case6.put("expectedResponse", 405);
				
				
				//Case 7
				Map<String,Object> case7 = new HashMap<>();
				case7.put("xid", "wergfser");
				case7.put("createSessionPayload", "{"+
					    "\"key\": \"gotTheKey\","+
					    "\"value\": \"gotTheVALUE\","+
					    "\"deep\": {"+
					        "\"show\": \"Better\","+
					        "\"time\": \"today\","+
					        "\"deep\": {"+
					            "\"key\": \"key\","+
					            "\"value\": \"value\""+
					        "}"+
					    "}"+
					"}");
				case7.put("expectedResponse", 404);
				
		dataSet = new Object[][]{{case1},{case2},{case6},{case7}};	
		return dataSet;
	}
	
}


