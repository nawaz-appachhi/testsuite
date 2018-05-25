package com.myntra.apiTests.dataproviders;

import java.util.HashMap;

import com.myntra.apiTests.portalservices.lgpservices.LgpFeedObjectIndividualHelper;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Initialize;

public class LgpFeedObjectIndividualDP extends LgpFeedObjectIndividualHelper {
	
	public static Initialize init = new Initialize("/Data/configuration");
	String envName = init.Configurations.GetTestEnvironemnt().toString().toLowerCase();
	
	@DataProvider
	public Object[][] diyIndividualCardDP(){
		
		Object[][] dataSet = null;
		
		if(envName.equalsIgnoreCase("fox7")){
			
			/********* case1 ********************************************************/
			
			/*1) Check for the card with all basic components and keys. along with all the data as present in the "PR Screenshot"*/
			
			HashMap<String,HashMap<String,String[]>> mainMap1 = new HashMap<>();
			
			HashMap<String,String[]> subMap1 = new HashMap<>();
			
			String[] componentValidationList = {"TITLE_TEXT","DESCRIPTION_TEXT","CAROUSEL_CARD",
												"CREATOR_TITLE","FOOTER_LIKE_SHARE"};		
			
			subMap1.put("toValidateList", componentValidationList);
			mainMap1.put("2:test-diy-main-set-qa-5==case1", subMap1);
			
			/************************************************************************/
			
			/********* case2 ********************************************************/
			
			/*2)  Without Author fields*/
			
			HashMap<String,HashMap<String,String[]>> mainMap2 = new HashMap<>();
			
			HashMap<String,String[]> subMap2 = new HashMap<>();
			
			String[] componentValidationList2 = {"TITLE_TEXT","DESCRIPTION_TEXT","CAROUSEL_CARD",
												 "FOOTER_LIKE_SHARE"};			
			
			subMap2.put("toValidateList", componentValidationList2);
			mainMap2.put("2:test-diy-main-set-qa-may-27th-2016-01==case2", subMap2);
			
			/************************************************************************/
			
			/********* case3 ********************************************************/
			
			/*3) With invalid Author fields*/
			
			HashMap<String,HashMap<String,String[]>> mainMap3 = new HashMap<>();
			
			HashMap<String,String[]> subMap3 = new HashMap<>();
			
			String[] componentValidationList3 = {"TITLE_TEXT","DESCRIPTION_TEXT","CAROUSEL_CARD",
												 "FOOTER_LIKE_SHARE"};			
			
			subMap3.put("toValidateList", componentValidationList3);
			mainMap3.put("2:test-diy-main-set-qa-may-27th-2016-02==case3", subMap3);
			
			/************************************************************************/
			
			/********* case4 ********************************************************/
			
			/*4) With color modifications*/
			
			HashMap<String,HashMap<String,String[]>> mainMap4 = new HashMap<>();
			
			HashMap<String,String[]> subMap4 = new HashMap<>();
			
			String[] componentValidationList4 = {"TITLE_TEXT","DESCRIPTION_TEXT","CAROUSEL_CARD",
												"CREATOR_TITLE","FOOTER_LIKE_SHARE"};	
			
			String[] jsonKeyValidationList = {"$.cards[0]..children[*].style.color"};
			
			subMap4.put("toValidateList", componentValidationList4);
			subMap4.put("toJsonKeyValidationList", jsonKeyValidationList);
			
			mainMap4.put("2:test-diy-main-set-1==case4", subMap4);
			
			/************************************************************************/
			
			/********* case5 ********************************************************/
			
			/*5) Without title and description in the parent*/
			
			HashMap<String,HashMap<String,String[]>> mainMap5 = new HashMap<>();
			
			HashMap<String,String[]> subMap5 = new HashMap<>();
			
			String[] componentValidationList5 = {"CAROUSEL_CARD","FOOTER_LIKE_SHARE"};			
			
			subMap5.put("toValidateList", componentValidationList5);
			mainMap5.put("2:test-diy-main-set-4==case5", subMap5);
			
			
			/************************************************************************/
			
			/********* case6 ********************************************************/
			/*6) With Empty Object Array */
			
			
			HashMap<String,HashMap<String,String[]>> mainMap6 = new HashMap<>();
			
			HashMap<String,String[]> subMap6 = new HashMap<>();
			
			String[] componentValidationList6 = {"0","[]"};		
			
			subMap6.put("toValidateList", componentValidationList6);
			mainMap6.put("2:test-diy-main-set-6==case6", subMap6);
			
			/************************************************************************/
			
			/********* case7 ********************************************************/
			/*7) With empty title and description strings for children*/
			
			HashMap<String,HashMap<String,String[]>> mainMap7 = new HashMap<>();
			
			HashMap<String,String[]> subMap7 = new HashMap<>();
			
			String[] componentValidationList7 = {"TITLE_TEXT","DESCRIPTION_TEXT","CAROUSEL_CARD",
												"CREATOR_TITLE","FOOTER_LIKE_SHARE"};			
			
			subMap7.put("toValidateList", componentValidationList7);
			mainMap7.put("2:test-diy-May-30th-2016-01==case7", subMap7);
			
			/************************************************************************/

			/********* case8 ********************************************************/
			
			/*8) With version specification to less that <v2.7*/
			
			HashMap<String,HashMap<String,String[]>> mainMap8 = new HashMap<>();
			
			HashMap<String,String[]> subMap8 = new HashMap<>();
			
			String[] componentValidationList8 = {"0","[]"};		
			
			subMap8.put("toValidateList", componentValidationList8);
			mainMap8.put("2:test-diy-May-30th-2016-01==case8", subMap8);
			
			/************************************************************************/

			dataSet = new Object[][]{{mainMap1},{mainMap2},{mainMap3},{mainMap4},{mainMap5},{mainMap6},{mainMap7},{mainMap8}};
		}
		
		else if(envName.equalsIgnoreCase("production")){
			
		
		}
		
		return dataSet;
		
	}
	
	@DataProvider
	public Object[][] merchandisingIndividualCardDP(){
		
		Object[][] dataSet = null;
		
		if(envName.equalsIgnoreCase("fox7")){
			
			/********* case1 ********************************************************/
			/* 1) Check for the card with all basic components and keys.*/
			
			HashMap<String,HashMap<String,String[]>> mainMap1 = new HashMap<>();
			
			HashMap<String,String[]> subMap1 = new HashMap<>();
			
			String[] componentValidationList = {"STORE_BRAND","FOOTER_LIKE_SHARE"};			
			
			subMap1.put("toValidateList", componentValidationList);
			mainMap1.put("2:MERCHANDIZINGCARD==case1", subMap1);
			
			/************************************************************************/
			
			/********* case2 ********************************************************/
			/* 2) Card with only one child object in it. */
			
			HashMap<String,HashMap<String,String[]>> mainMap2 = new HashMap<>();
			
			HashMap<String,String[]> subMap2 = new HashMap<>();
			
			String[] componentValidationList2 = {"STORE_BRAND","FOOTER_LIKE_SHARE"};		
			
			subMap2.put("toValidateList", componentValidationList2);
			mainMap2.put("2:MERCHANDIZINGCARD-One==case2", subMap2);
			
			/************************************************************************/
			
			/********* case3 ********************************************************/
			
			/* 3) Card with no child objects in it. */
			
			HashMap<String,HashMap<String,String[]>> mainMap3 = new HashMap<>();
			
			HashMap<String,String[]> subMap3 = new HashMap<>();
			
			String[] componentValidationList3 = {"0","[]"};
			
			subMap3.put("toValidateList", componentValidationList3);
			mainMap3.put("2:MERCHANDIZINGCARD-Empty==case3", subMap3);
			
			
			/************************************************************************/
			
			/********* case4 ********************************************************/
			
			/* 4) Card with having invalid child objects in it. */
			
			HashMap<String,HashMap<String,String[]>> mainMap4 = new HashMap<>();
			
			HashMap<String,String[]> subMap4 = new HashMap<>();
			
			String[] componentValidationList4 = {"0","[]"};
			
			subMap4.put("toValidateList", componentValidationList4);
			mainMap4.put("2:MERCHANDIZINGCARD-InvalidOne==case4", subMap4);
			
			/************************************************************************/
			
			/********* case5 ********************************************************/
			/* 5) Card with having only one invalid child objects and others 3 are valid objects. */
			
			HashMap<String,HashMap<String,String[]>> mainMap5 = new HashMap<>();
			
			HashMap<String,String[]> subMap5 = new HashMap<>();
			
			String[] componentValidationList5 = {"STORE_BRAND","FOOTER_LIKE_SHARE"};		
			
			subMap5.put("toValidateList", componentValidationList5);
			mainMap5.put("2:MERCHANDIZINGCARD_INVALIDFOUR==case2", subMap5);
			
			/************************************************************************/
			
			/********* case6 ********************************************************/
			
			/* 6) Card having one child object with trailing spaces and others are valid objects. */
			
			HashMap<String,HashMap<String,String[]>> mainMap6 = new HashMap<>();
			
			HashMap<String,String[]> subMap6 = new HashMap<>();
			
			String[] componentValidationList6 = {"STORE_BRAND","FOOTER_LIKE_SHARE"};	
			
			subMap6.put("toValidateList", componentValidationList6);
			mainMap6.put("2:MERCHANDIZINGCARD_INVALIDFOUR==case2", subMap6);
			
			
			/************************************************************************/
			
			/********* case7 ********************************************************/
			
			/* 7) Card having one child object with trailing spaces and others are valid objects. */
			
			HashMap<String,HashMap<String,String[]>> mainMap7 = new HashMap<>();
			
			HashMap<String,String[]> subMap7 = new HashMap<>();
			
			String[] componentValidationList7 = {"0","[]"};	
			
			subMap7.put("toValidateList", componentValidationList7);
			mainMap7.put("2:MERCHANDIZINGCARD==case7", subMap7);
			
			
			/************************************************************************/
			
			dataSet = new Object[][]{{mainMap1},{mainMap2},{mainMap3},{mainMap4},{mainMap5},{mainMap6},{mainMap7}};
		}
		
		else if(envName.equalsIgnoreCase("production")){
			
			/********* case1 ********************************************************/
			
			
			/************************************************************************/
		}
		
		return dataSet;	
	}
	
	@DataProvider
	public Object[][] topShotsIndividualCardDP(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7")){
			
			/********* case2 ********************************************************/
			/* 2) Card having only three children objects and without a image single component.*/
			
			HashMap<String,HashMap<String,String[]>> mainMap2 = new HashMap<>();
			
			HashMap<String,String[]> subMap2 = new HashMap<>();
			
			String[] componentValidationList2 = {"TITLE_TEXT","DESCRIPTION_TEXT","SPLIT_BANNER"};			
			
			subMap2.put("toValidateList", componentValidationList2);
			mainMap2.put("10:TOP-SHOTS13new==case2", subMap2);
			
			/************************************************************************/
			
			/********* case3 ********************************************************/
			
			/* 3) Card having six children objects which comprises two splitbanner components */
			
			HashMap<String,HashMap<String,String[]>> mainMap3 = new HashMap<>();
			
			HashMap<String,String[]> subMap3 = new HashMap<>();
			
			String[] componentValidationList3 = {"TITLE_TEXT","DESCRIPTION_TEXT","SPLIT_BANNER","SPLIT_BANNER"};
			
			subMap3.put("toValidateList", componentValidationList3);
			mainMap3.put("10:TOP-SHOTS6images==case3", subMap3);
			
			
			/************************************************************************/
			
			/********* case4 ********************************************************/
			
			/* 4) Card having five children objects which comprises one splitbanner components */
			
			HashMap<String,HashMap<String,String[]>> mainMap4 = new HashMap<>();
			
			HashMap<String,String[]> subMap4 = new HashMap<>();
			
			String[] componentValidationList4 = {"TITLE_TEXT","DESCRIPTION_TEXT","SPLIT_BANNER"};
			
			subMap4.put("toValidateList", componentValidationList4);
			mainMap4.put("10:TOP-SHOTS5images==case4", subMap4);
			
			
			/************************************************************************/
			
			/********* case5 ********************************************************/
			
			/* 5) Card having ten children objects which comprises two splitbanner components */
			
			HashMap<String,HashMap<String,String[]>> mainMap5 = new HashMap<>();
			
			HashMap<String,String[]> subMap5 = new HashMap<>();
			
			String[] componentValidationList5 = {"TITLE_TEXT","DESCRIPTION_TEXT","SPLIT_BANNER","SPLIT_BANNER"};
			
			subMap5.put("toValidateList", componentValidationList5);
			mainMap5.put("10:TOP-SHOTS10images==case5", subMap5);
			
			
			/************************************************************************/
			
			/********* case6 ********************************************************/
			
			/* 6) Card having twenty children objects which comprises four splitbanner components */
			
			HashMap<String,HashMap<String,String[]>> mainMap6 = new HashMap<>();
			
			HashMap<String,String[]> subMap6 = new HashMap<>();
			
			String[] componentValidationList6 = {"TITLE_TEXT","DESCRIPTION_TEXT","SPLIT_BANNER","SPLIT_BANNER","SPLIT_BANNER","SPLIT_BANNER"};
			
			subMap6.put("toValidateList", componentValidationList6);
			mainMap6.put("10:TOP-SHOTS20images==case6", subMap6);
			
			/************************************************************************/
			
			/********* case7 ********************************************************/
			
			/* 7) Card having twenty one children objects which comprises four splitbanner components */
			
			HashMap<String,HashMap<String,String[]>> mainMap7 = new HashMap<>();
			
			HashMap<String,String[]> subMap7 = new HashMap<>();
			
			String[] componentValidationList7 = {"TITLE_TEXT","DESCRIPTION_TEXT","SPLIT_BANNER","SPLIT_BANNER","SPLIT_BANNER","SPLIT_BANNER"};
			
			subMap7.put("toValidateList", componentValidationList7);
			mainMap7.put("10:TOP-SHOTS21images==case7", subMap7);
			
			/************************************************************************/
			
			/********* case8 ********************************************************/
			
			/* 8) Card having zero children objects which comprises no components */
			
			HashMap<String,HashMap<String,String[]>> mainMap8 = new HashMap<>();
			
			HashMap<String,String[]> subMap8 = new HashMap<>();
			
			String[] componentValidationList8 = {"0","[]"};
			
			subMap8.put("toValidateList", componentValidationList8);
			mainMap8.put("10:TOP-SHOTSEmpty==case8", subMap8);
			
			/************************************************************************/
			
			/********* case9 ********************************************************/
			
			/* 9) Card having invalid shots objects. */
			
			HashMap<String,HashMap<String,String[]>> mainMap9 = new HashMap<>();
			
			HashMap<String,String[]> subMap9 = new HashMap<>();
			
			String[] componentValidationList9 = {"TITLE_TEXT","DESCRIPTION_TEXT","IMAGE_SINGLE","SPLIT_BANNER"};
			
			subMap9.put("toValidateList", componentValidationList9);
			mainMap9.put("10:TOP-SHOTS13invalidobjects==case9", subMap9);
			
			/************************************************************************/ 
			
			/********* case10 ********************************************************/
			
			/* 10) Versioning for Top-Shots with <2.6 */
			
			HashMap<String,HashMap<String,String[]>> mainMap10 = new HashMap<>();
			
			HashMap<String,String[]> subMap10 = new HashMap<>();
			
			String[] componentValidationList10 = {"0","[]"};
			
			subMap10.put("toValidateList", componentValidationList10);
			mainMap10.put("10:TOP-SHOTS13new==case10", subMap10);
			
			/************************************************************************/ 
			
			dataSet = new Object[][]{/*{mainMap1},*/{mainMap2},{mainMap3},{mainMap4},{mainMap5},{mainMap6},{mainMap7},{mainMap8},{mainMap9},{mainMap10}};
			
		}
		else if(envName.equalsIgnoreCase("production")){
			
			/********* case1 ********************************************************/
			
			
			/************************************************************************/
		}
		
		return dataSet;
	}
	
	@DataProvider
	public Object[][] carousalIndividualCardDP(){
		
		Object[][] dataSet = null;
		
		if(envName.equalsIgnoreCase("fox7")){
			
			/********* case1 ********************************************************/
			/* 1)Question Carousel with valid objects */
			
			HashMap<String,HashMap<String,String[]>> mainMap1 = new HashMap<>();
			
			HashMap<String,String[]> subMap1 = new HashMap<>();
			
			String[] componentValidationList1 = {"CAROUSEL_CARD"};
			
			subMap1.put("toValidateList", componentValidationList1);
			mainMap1.put("2:new_carousel_question11==case1", subMap1);
			
			/************************************************************************/
			
			/********* case2 ********************************************************/
			/* 2)Question Carousel with invalid objects on the first position */
			
			HashMap<String,HashMap<String,String[]>> mainMap2 = new HashMap<>();
			
			HashMap<String,String[]> subMap2 = new HashMap<>();
			
			String[] componentValidationList2 = {"CAROUSEL_CARD"};
			
			subMap2.put("toValidateList", componentValidationList2);
			mainMap2.put("2:new_carousel_questioninvalid11==case2", subMap2);
			
			/************************************************************************/
			
			
			/********* case3 ********************************************************/
			/* 3)Question Carousel with invalid objects on the second position */
			
			HashMap<String,HashMap<String,String[]>> mainMap3 = new HashMap<>();
			
			HashMap<String,String[]> subMap3 = new HashMap<>();
			
			String[] componentValidationList3 = {"CAROUSEL_CARD"};
			
			subMap3.put("toValidateList", componentValidationList3);
			mainMap3.put("2:new_carousel_questioninvalid21==case3", subMap3);
			
			/************************************************************************/
			
			
			/********* case4 ********************************************************/
			/* 4)Question Carousel with invalid objects on the third position */
			
			HashMap<String,HashMap<String,String[]>> mainMap4 = new HashMap<>();
			
			HashMap<String,String[]> subMap4 = new HashMap<>();
			
			String[] componentValidationList4 = {"CAROUSEL_CARD"};
			
			subMap4.put("toValidateList", componentValidationList4);
			mainMap4.put("2:new_carousel_questioninvalid31==case4", subMap4);
			
			/************************************************************************/
			
			
			/********* case5 ********************************************************/
			/* 5)Question Carousel with valid single object. */
			
			HashMap<String,HashMap<String,String[]>> mainMap5 = new HashMap<>();
			
			HashMap<String,String[]> subMap5 = new HashMap<>();
			
			String[] componentValidationList5 = {"CAROUSEL_CARD"};
			
			subMap5.put("toValidateList", componentValidationList5);
			mainMap5.put("2:new_carousel_questionsingle1==case5", subMap5);
			
			/************************************************************************/
			
			/********* case6 ********************************************************/
			/* 6) Question Carousel with invalid single object. */
			
			HashMap<String,HashMap<String,String[]>> mainMap6 = new HashMap<>();
			
			HashMap<String,String[]> subMap6 = new HashMap<>();
			
			String[] componentValidationList6 = {"0","[]"};
			
			subMap6.put("toValidateList", componentValidationList6);
			mainMap6.put("2:new_carousel_questionsingleinvalid1==case6", subMap6);
			
			/************************************************************************/
			
			
			/********* case7 ********************************************************/
			/* 7) Question Carousel with zero objects. */
			
			HashMap<String,HashMap<String,String[]>> mainMap7 = new HashMap<>();
			
			HashMap<String,String[]> subMap7 = new HashMap<>();
			
			String[] componentValidationList7 = {"0","[]"};
			
			subMap7.put("toValidateList", componentValidationList7);
			mainMap7.put("2:new_carousel_questionsingleempty1==case7", subMap7);
			
			/************************************************************************/
			
			
			/********* case8 ********************************************************/
			/* 8)  Carousel with valid object along with footer. */
			
			HashMap<String,HashMap<String,String[]>> mainMap8 = new HashMap<>();
			
			HashMap<String,String[]> subMap8 = new HashMap<>();
			
			String[] componentValidationList8 = {"0","[]"};
			
			subMap8.put("toValidateList", componentValidationList8);
			mainMap8.put("2:new_carousel_questionzeroobjectsnew1==case8", subMap8);
			
			/************************************************************************/
			
			/********* case9 ********************************************************/
			/* 9) Question Carousel with valid mix set of objects.  */
			
			HashMap<String,HashMap<String,String[]>> mainMap9 = new HashMap<>();
			
			HashMap<String,String[]> subMap9 = new HashMap<>();
			
			String[] componentValidationList9 = {"CAROUSEL_CARD"};
			
			subMap9.put("toValidateList", componentValidationList9);
			mainMap9.put("2:new_carousel_questionmixedcollections==case9", subMap9);
			
			/************************************************************************/
			
			/********* case10 ********************************************************/
			/* 10) Collections carousel with extra component. */
			
			HashMap<String,HashMap<String,String[]>> mainMap10 = new HashMap<>();
			
			HashMap<String,String[]> subMap10 = new HashMap<>();
			
			String[] componentValidationList10 = {"CAROUSEL_CARD","ONBOARD_CUSTOM_FOOTER"};
			
			subMap10.put("toValidateList", componentValidationList10);
			mainMap10.put("2:new_carousel_extracomponentcarousel==case10", subMap10);
			
			/************************************************************************/
			
			
			/********* case11 ********************************************************/
			/* 11) Question carousel with extra component. */
			
			HashMap<String,HashMap<String,String[]>> mainMap11 = new HashMap<>();
			
			HashMap<String,String[]> subMap11 = new HashMap<>();
			
			String[] componentValidationList11 = {"CAROUSEL_CARD"};
			
			subMap11.put("toValidateList", componentValidationList11);
			mainMap11.put("2:new_carousel_questionobjectwithextracomponent==case11", subMap11);
			
			/************************************************************************/
			
			
			/********* case12 ********************************************************/
			/* 12) Questoing carousel with extra component. */
			
			HashMap<String,HashMap<String,String[]>> mainMap12 = new HashMap<>();
			
			HashMap<String,String[]> subMap12 = new HashMap<>();
			
			String[] componentValidationList12 = {"CAROUSEL_CARD"};
			
			subMap12.put("toValidateList", componentValidationList12);
			mainMap12.put("2:new_carousel_questioninvalid31==case12", subMap12);
			
			/************************************************************************/
			
			
			/********* case13 ********************************************************/
			/* 13) Questoin carousel with extra component along with footer links. */
			
			HashMap<String,HashMap<String,String[]>> mainMap13 = new HashMap<>();
			
			HashMap<String,String[]> subMap13 = new HashMap<>();
			
			String[] componentValidationList13 = {"CAROUSEL_CARD","FOOTER_LINKS","ONBOARD_CUSTOM_FOOTER"};
			
			subMap13.put("toValidateList", componentValidationList13);
			mainMap13.put("2:new_carouselcollectionwithfooterlinks3==case13", subMap13);
			
			/************************************************************************/
			
			/********* case14 ********************************************************/
			/* 14) Questoin carousel with base colour */
			
			HashMap<String,HashMap<String,String[]>> mainMap14 = new HashMap<>();
			
			HashMap<String,String[]> subMap14 = new HashMap<>();
			
			String[] componentValidationList14 = {"CAROUSEL_CARD"};
			
			subMap14.put("toValidateList", componentValidationList14);
			mainMap14.put("2:new_carousel_questionsinglevalidbasecolour==case14", subMap14);
			
			/************************************************************************/
			
			
			/********* case15 ********************************************************/
			/* 15) Collection carousel with extra component along with footer links. */
			
			HashMap<String,HashMap<String,String[]>> mainMap15 = new HashMap<>();
			
			HashMap<String,String[]> subMap15 = new HashMap<>();
			
			String[] componentValidationList15 = {"CAROUSEL_CARD"};
			
			subMap15.put("toValidateList", componentValidationList15);
			mainMap15.put("2:new_carouselcollectionwithfooterlinks1==case15", subMap15);
			
			/************************************************************************/
			
			dataSet = new Object[][]{{mainMap1},{mainMap2},{mainMap3},{mainMap4},{mainMap5},{mainMap6},{mainMap7},{mainMap8},{mainMap9},{mainMap10},
												{mainMap11},{mainMap12},{mainMap13},{mainMap14},{mainMap15}};
		}
	
		return dataSet;
	}

	@DataProvider
	public Object[][] brandCarousalIndividualCardDP(){
		
		Object[][] dataSet = null;
		
		if(envName.equalsIgnoreCase("fox7")){
			
			/********* case1 ********************************************************/
			/* 1) Brand Carousel with valid objects */
			
			HashMap<String,HashMap<String,String[]>> mainMap1 = new HashMap<>();
			
			HashMap<String,String[]> subMap1 = new HashMap<>();
			
			String[] componentValidationList1 = {"TITLE_TEXT","DESCRIPTION_TEXT","SLIDER"};
			
			subMap1.put("toValidateList", componentValidationList1);
			mainMap1.put("9:newBrandCorousel22==case1", subMap1);
			
			/************************************************************************/
			
			/********* case2 ********************************************************/
			/* 2) Brand Carousel with one invalid object at first position and two valid objects */
			
			HashMap<String,HashMap<String,String[]>> mainMap2 = new HashMap<>();
			
			HashMap<String,String[]> subMap2 = new HashMap<>();
			
			String[] componentValidationList2 = {"TITLE_TEXT","DESCRIPTION_TEXT","SLIDER"};
			
			subMap2.put("toValidateList", componentValidationList2);
			mainMap2.put("9:newBrandCorousel22-invalid1==case2", subMap2);
			
			/************************************************************************/
			
			
			/********* case3 ********************************************************/
			/* 3) Brand Carousel with one invalid object at second position and two valid objects. */
			
			HashMap<String,HashMap<String,String[]>> mainMap3 = new HashMap<>();
			
			HashMap<String,String[]> subMap3 = new HashMap<>();
			
			String[] componentValidationList3 = {"TITLE_TEXT","DESCRIPTION_TEXT","SLIDER"};
			
			subMap3.put("toValidateList", componentValidationList3);
			mainMap3.put("9:newBrandCorousel22-invalid2==case3", subMap3);
			
			/************************************************************************/
			
			
			/********* case4 ********************************************************/
			/* 4) Brand Carousel with one invalid object at third position and two valid objects. */
			
			HashMap<String,HashMap<String,String[]>> mainMap4 = new HashMap<>();
			
			HashMap<String,String[]> subMap4 = new HashMap<>();
			
			String[] componentValidationList4 = {"TITLE_TEXT","DESCRIPTION_TEXT","SLIDER"};
			
			subMap4.put("toValidateList", componentValidationList4);
			mainMap4.put("9:newBrandCorousel22-invalid3==case4", subMap4);
			
			/************************************************************************/
			
			
			/********* case5 ********************************************************/
			/* 5)Brand Carousel with two invalid objects and one valid objects. */
			
			HashMap<String,HashMap<String,String[]>> mainMap5 = new HashMap<>();
			
			HashMap<String,String[]> subMap5 = new HashMap<>();
			
			String[] componentValidationList5 = {"TITLE_TEXT","DESCRIPTION_TEXT","SLIDER"};
			
			subMap5.put("toValidateList", componentValidationList5);
			mainMap5.put("9:newBrandCorousel22-invalid4==case5", subMap5);
			
			/************************************************************************/
			
			/********* case6 ********************************************************/
			/* 6) Brand Carousel with only one valid object. */
			
			HashMap<String,HashMap<String,String[]>> mainMap6 = new HashMap<>();
			
			HashMap<String,String[]> subMap6 = new HashMap<>();
			
			String[] componentValidationList6 = {"TITLE_TEXT","DESCRIPTION_TEXT","SLIDER"};
			
			subMap6.put("toValidateList", componentValidationList6);
			mainMap6.put("9:newBrandCorousel22-valid1==case6", subMap6);
			
			/************************************************************************/
			
			
			/********* case7 ********************************************************/
			/* 7) Brand Carousel with zero objects. */
			
			HashMap<String,HashMap<String,String[]>> mainMap7 = new HashMap<>();
			
			HashMap<String,String[]> subMap7 = new HashMap<>();
			
			String[] componentValidationList7 = {"0","[]"};
			
			subMap7.put("toValidateList", componentValidationList7);
			mainMap7.put("9:newBrandCorousel22zero==case7", subMap7);
			
			/************************************************************************/
			
			dataSet = new Object[][]{{mainMap1},{mainMap2},{mainMap3},{mainMap4},{mainMap5},{mainMap6},{mainMap7}};
		}
		
		
		return dataSet;
	}

	@DataProvider
	public Object[][] articleIndividualCardDP(){
		
		Object[][] dataSet = null;
		
		if(envName.equalsIgnoreCase("fox7")){
			
			/********* case1 ********************************************************/
			/* 1) Without author */
			
			HashMap<String,HashMap<String,String[]>> mainMap1 = new HashMap<>();
			
			HashMap<String,String[]> subMap1 = new HashMap<>();
			
			String[] componentValidationList1 = {"IMAGE_SINGLE","TITLE_TEXT","DESCRIPTION_TEXT","QUOTE_TEXT","PRODUCT_RACK","FOOTER_LIKE_SHARE"};
			
			subMap1.put("toValidateList", componentValidationList1);
			mainMap1.put("2:article-qa-01==case1", subMap1);
			
			/************************************************************************/
			
			/********* case2 ********************************************************/
			/* 2) With author as User UIDX */
			
			HashMap<String,HashMap<String,String[]>> mainMap2 = new HashMap<>();
			
			HashMap<String,String[]> subMap2 = new HashMap<>();
			
			String[] componentValidationList2 = {"IMAGE_SINGLE","TITLE_TEXT","DESCRIPTION_TEXT","QUOTE_TEXT","PRODUCT_RACK","FOOTER_LIKE_SHARE"};
			
			subMap2.put("toValidateList", componentValidationList2);
			mainMap2.put("2:article-qa-02==case2", subMap2);
			
			/************************************************************************/
			
			/********* case4 ********************************************************/
			/* 4) Versioning with <=v2.6 (validating FeedbackView like component) */
			
			HashMap<String,HashMap<String,String[]>> mainMap4 = new HashMap<>();
			
			HashMap<String,String[]> subMap4 = new HashMap<>();
			
			String[] componentValidationList4 = {"IMAGE_SINGLE","TITLE_TEXT","DESCRIPTION_TEXT","QUOTE_TEXT","PRODUCT_RACK","FEEDBACK_VIEW_LIKE","FOOTER_LIKE_SHARE"};
			
			subMap4.put("toValidateList", componentValidationList4);
			mainMap4.put("2:article-qa-02==case4", subMap4);
			
			/************************************************************************/
			
			/********* case5 ********************************************************/
			/* 5) Without Product Rack*/
			
			HashMap<String,HashMap<String,String[]>> mainMap5 = new HashMap<>();
			
			HashMap<String,String[]> subMap5 = new HashMap<>();
			
			String[] componentValidationList5 = {"IMAGE_SINGLE","TITLE_TEXT","DESCRIPTION_TEXT","QUOTE_TEXT","FOOTER_LIKE_SHARE"};
			
			subMap5.put("toValidateList", componentValidationList5);
			mainMap5.put("2:article-qa-03==case5", subMap5);
			
			/************************************************************************/
			
			dataSet = new Object[][]{{mainMap1},{mainMap2},{mainMap4},{mainMap5}};
		}
		
		
		
		return dataSet;
	}
		
	@DataProvider
	public Object[][] bannerIndividualCardDP(){
		
		Object[][] dataSet = null;
		
		if(envName.equalsIgnoreCase("fox7")){
			
			/********* case1 ********************************************************/
			/* 1) With basic structure */
			
			HashMap<String,HashMap<String,String[]>> mainMap1 = new HashMap<>();
			
			HashMap<String,String[]> subMap1 = new HashMap<>();
			
			String[] componentValidationList1 = {"BANNER"};
			
			subMap1.put("toValidateList", componentValidationList1);
			mainMap1.put("2:0fffec48-2645-468f-9f99-295302a30c15==case1", subMap1);
			
			/************************************************************************/
			
			dataSet = new Object[][]{{mainMap1}};
		}
		
		return dataSet;
	}

	@DataProvider
	public Object[][] splitBannerIndividualCardDP(){
		
		Object[][] dataSet = null;
		
		if(envName.equalsIgnoreCase("fox7")){
			
			/********* case2 ********************************************************/
			/* 2) Basic Structure working as expected */
			
			HashMap<String,HashMap<String,String[]>> mainMap2 = new HashMap<>();
			
			HashMap<String,String[]> subMap2 = new HashMap<>();
			
			String[] componentValidationList2 = {"TITLE_TEXT","SPLIT_BANNER"};
			
			subMap2.put("toValidateList", componentValidationList2);
			mainMap2.put("2:splitbanner-11-June-2016-08==case2", subMap2);
			
			/************************************************************************/
			
			/********* case3 ********************************************************/
			/* 3) Split Banner with 3 children. */
			
			HashMap<String,HashMap<String,String[]>> mainMap3 = new HashMap<>();
			
			HashMap<String,String[]> subMap3 = new HashMap<>();
			
			String[] componentValidationList3 = {"0","[]"};
			
			subMap3.put("toValidateList", componentValidationList3);
			mainMap3.put("2:splitbanner-11-June-2016-01==case3", subMap3);
			
			/************************************************************************/
			
			/********* case4 ********************************************************/
			/* 4) Split-Banner with 0 children */
			
			HashMap<String,HashMap<String,String[]>> mainMap4 = new HashMap<>();
			
			HashMap<String,String[]> subMap4 = new HashMap<>();
			
			String[] componentValidationList4 = {"0","[]"};
			
			subMap4.put("toValidateList", componentValidationList4);
			mainMap4.put("2:splitbanner-11-June-2016-09==case4", subMap4);
			
			/************************************************************************/
			
			dataSet = new Object[][]{{mainMap2},{mainMap3},{mainMap4}};
		}
		
		return dataSet;
	}

	@DataProvider
	public Object[][] collectionsIndividualCardDP(){
		
		Object[][] dataSet = null;
		
		if(envName.equalsIgnoreCase("fox7")){
			
			/********* case1 ********************************************************/
			/* 1) Basic Structure with Creator Title */
			
			HashMap<String,HashMap<String,String[]>> mainMap1 = new HashMap<>();
			
			HashMap<String,String[]> subMap1 = new HashMap<>();
			
			String[] componentValidationList1 = {"HEADER_CONTEXT","MOSAIC","TITLE_TEXT","DESCRIPTION_TEXT","CREATOR_TITLE","FOOTER_LIKE_SHARE"};
			
			subMap1.put("toValidateList", componentValidationList1);
			mainMap1.put("2:collection-June-13-2016-03==case1", subMap1);
			
			/************************************************************************/
			
			/********* case2 ********************************************************/
			/* 2) Basic Structure without Creator Title */
			
			HashMap<String,HashMap<String,String[]>> mainMap2 = new HashMap<>();
			
			HashMap<String,String[]> subMap2 = new HashMap<>();
			
			String[] componentValidationList2 = {"HEADER_CONTEXT","MOSAIC","TITLE_TEXT","DESCRIPTION_TEXT","FOOTER_LIKE_SHARE"};
			
			subMap2.put("toValidateList", componentValidationList2);
			mainMap2.put("2:collection-June-13-2016-04==case2", subMap2);
			
			/************************************************************************/
			
			/********* case3 ********************************************************/
			/* 3) Check Feedback View like for version < v2.7 (Feedback-View-Like)  */
			
			HashMap<String,HashMap<String,String[]>> mainMap3 = new HashMap<>();
			
			HashMap<String,String[]> subMap3 = new HashMap<>();
			
			String[] componentValidationList3 = {"HEADER_CONTEXT","MOSAIC","TITLE_TEXT","DESCRIPTION_TEXT","FEEDBACK_VIEW_LIKE","FOOTER_LIKE_SHARE"};
			
			subMap3.put("toValidateList", componentValidationList3);
			mainMap3.put("2:collection-June-13-2016-04==case3", subMap3);
			
			/************************************************************************/
			
			/********* case4 ********************************************************/
			/* 4) With invalid Creator title */
			
			HashMap<String,HashMap<String,String[]>> mainMap4 = new HashMap<>();
			
			HashMap<String,String[]> subMap4 = new HashMap<>();
			
			String[] componentValidationList4 = {"HEADER_CONTEXT","MOSAIC","TITLE_TEXT","DESCRIPTION_TEXT","FOOTER_LIKE_SHARE"};
			
			subMap4.put("toValidateList", componentValidationList4);
			mainMap4.put("2:collection-June-20th-2016-06==case4", subMap4);
			
			/************************************************************************/
			
			dataSet = new Object[][]{{mainMap1},{mainMap2},{mainMap3},{mainMap4}};
			
		}
		
		return dataSet;
	}
	
	@DataProvider
	public Object[][] postoShotIndividualCardDP(){
		
		Object[][] dataSet = null;
		
		if(envName.equalsIgnoreCase("fox7")){
			
			/********* case1 ********************************************************/
			/* 1) PostoShot with separator and product rack and creator title */
			
			HashMap<String,HashMap<String,String[]>> mainMap1 = new HashMap<>();
			
			HashMap<String,String[]> subMap1 = new HashMap<>();
			
			String[] componentValidationList1 = {"IMAGE_SINGLE","DESCRIPTION_TEXT","SEPARATOR", "PRODUCT_RACK","CREATOR_TITLE","FOOTER_LIKE_SHARE"};
			
			subMap1.put("toValidateList", componentValidationList1);
			mainMap1.put("2:postoShot-June-20th-2016-02==case1", subMap1);
			
			/************************************************************************/
			
			/********* case2 ********************************************************/
			/* 2) PostoShot with invalid product-id in Product rack */
			
			HashMap<String,HashMap<String,String[]>> mainMap2 = new HashMap<>();
			
			HashMap<String,String[]> subMap2 = new HashMap<>();
			
			String[] componentValidationList2 = {"IMAGE_SINGLE","DESCRIPTION_TEXT","CREATOR_TITLE","FOOTER_LIKE_SHARE"};
			
			subMap2.put("toValidateList", componentValidationList2);
			mainMap2.put("2:postoShot-June-20th-2016-01==case2", subMap2);
			
			/************************************************************************/
			
			/********* case3 ********************************************************/
			/* 3) PostoShot without Product Rack  */
			
			HashMap<String,HashMap<String,String[]>> mainMap3 = new HashMap<>();
			
			HashMap<String,String[]> subMap3 = new HashMap<>();
			
			String[] componentValidationList3 = {"IMAGE_SINGLE","DESCRIPTION_TEXT","CREATOR_TITLE","FOOTER_LIKE_SHARE"};
			
			subMap3.put("toValidateList", componentValidationList3);
			mainMap3.put("2:postoShot-June-20th-2016-03==case3", subMap3);
			
			/************************************************************************/
			
			/********* case4 ********************************************************/
			/* 4) PostoShot without Creator title */
			
			HashMap<String,HashMap<String,String[]>> mainMap4 = new HashMap<>();
			
			HashMap<String,String[]> subMap4 = new HashMap<>();
			
			String[] componentValidationList4 = {"IMAGE_SINGLE","DESCRIPTION_TEXT","FOOTER_LIKE_SHARE"};
			
			subMap4.put("toValidateList", componentValidationList4);
			mainMap4.put("2:postoShot-June-20th-2016-04==case4", subMap4);
			
			/************************************************************************/
			
			/********* case5 ********************************************************/
			/* 5) Versioning check for PostoShot with version <v2.7 (Feedback-View-Like)   */
			
			HashMap<String,HashMap<String,String[]>> mainMap5 = new HashMap<>();
			
			HashMap<String,String[]> subMap5 = new HashMap<>();
			
			String[] componentValidationList5 = {"IMAGE_SINGLE","DESCRIPTION_TEXT","SEPARATOR", "PRODUCT_RACK","CREATOR_TITLE","FEEDBACK_VIEW_LIKE","FOOTER_LIKE_SHARE"};
			
			subMap5.put("toValidateList", componentValidationList5);
			mainMap5.put("2:postoShot-June-20th-2016-02==case5", subMap5);
			
			/************************************************************************/
			
			/********* case6 ********************************************************/
			/* 6) PostoShot with invalid author */
			
			HashMap<String,HashMap<String,String[]>> mainMap6 = new HashMap<>();
			
			HashMap<String,String[]> subMap6 = new HashMap<>();
			
			String[] componentValidationList6 = {"IMAGE_SINGLE","DESCRIPTION_TEXT","FOOTER_LIKE_SHARE"};
			
			subMap6.put("toValidateList", componentValidationList6);
			mainMap6.put("2:postoShot-June-20th-2016-05==case6", subMap6);
			
			/************************************************************************/
			
			
			
			dataSet = new Object[][]{{mainMap1},{mainMap2},{mainMap3},{mainMap4},{mainMap5},{mainMap6}};
			
		}
		
		return dataSet;
	}
	
	
	@DataProvider
	public Object[][] productStoryIndividualCardDP(){
		
		Object[][] dataSet = null;
		
		if(envName.equalsIgnoreCase("fox7")){
			
			/********* case1 ********************************************************/
			/* 1) With 3 valid and 1 invalid product in product rack */
			
			HashMap<String,HashMap<String,String[]>> mainMap1 = new HashMap<>();
			
			HashMap<String,String[]> subMap1 = new HashMap<>();
			
			String[] componentValidationList1 = {"IMAGE_SINGLE","TITLE_TEXT","DESCRIPTION_TEXT", "PRODUCT_RACK","FOOTER_LIKE_SHARE"};
			
			subMap1.put("toValidateList", componentValidationList1);
			mainMap1.put("4:TESTINVALIDPRODUCTRACKOBJ8_june-21-2016-01==case1", subMap1);
			
			/************************************************************************/
			
			/********* case2 ********************************************************/
			/* 2) Basic Structure with components */
			
			HashMap<String,HashMap<String,String[]>> mainMap2 = new HashMap<>();
			
			HashMap<String,String[]> subMap2 = new HashMap<>();
			
			String[] componentValidationList2 = {"IMAGE_SINGLE","TITLE_TEXT","DESCRIPTION_TEXT", "PRODUCT_RACK","FOOTER_LIKE_SHARE"};
			
			subMap2.put("toValidateList", componentValidationList2);
			mainMap2.put("4:product-story-June-21-2016-01==case2", subMap2);
			
			/************************************************************************/
			
			/********* case3 ********************************************************/
			/* 3) Basic Structure with components for version <2.7 (Feedback-View-Like) */
			
			HashMap<String,HashMap<String,String[]>> mainMap3 = new HashMap<>();
			
			HashMap<String,String[]> subMap3 = new HashMap<>();
			
			String[] componentValidationList3 = {"IMAGE_SINGLE","TITLE_TEXT","DESCRIPTION_TEXT", "PRODUCT_RACK","FEEDBACK_VIEW_LIKE","FOOTER_LIKE_SHARE"};
			
			subMap3.put("toValidateList", componentValidationList3);
			mainMap3.put("4:product-story-June-21-2016-01==case3", subMap3);
			
			/************************************************************************/
			
			/********* case4 ********************************************************/
			/* 4) Product rack with all invalid products */
			
			HashMap<String,HashMap<String,String[]>> mainMap4 = new HashMap<>();
			
			HashMap<String,String[]> subMap4 = new HashMap<>();
			
			String[] componentValidationList4 = {"IMAGE_SINGLE","TITLE_TEXT","DESCRIPTION_TEXT","FOOTER_LIKE_SHARE"};
			
			subMap4.put("toValidateList", componentValidationList4);
			mainMap4.put("4:product-story-June-21-2016-02==case4", subMap4);
			
			/************************************************************************/
			
			/********* case5 ********************************************************/
			/* 5) without Product Rack  */
			
			HashMap<String,HashMap<String,String[]>> mainMap5 = new HashMap<>();
			
			HashMap<String,String[]> subMap5 = new HashMap<>();
			
			String[] componentValidationList5 = {"IMAGE_SINGLE","TITLE_TEXT","DESCRIPTION_TEXT","FOOTER_LIKE_SHARE"};
			
			subMap5.put("toValidateList", componentValidationList5);
			mainMap5.put("4:product-story-June-21-2016-03==case5", subMap5);
			
			/************************************************************************/
			
			/********* case6 ********************************************************/
			/* 6) Without Author */
			
			HashMap<String,HashMap<String,String[]>> mainMap6 = new HashMap<>();
			
			HashMap<String,String[]> subMap6 = new HashMap<>();
			
			String[] componentValidationList6 = {"IMAGE_SINGLE","TITLE_TEXT","DESCRIPTION_TEXT","FOOTER_LIKE_SHARE"};
			
			subMap6.put("toValidateList", componentValidationList6);
			mainMap6.put("4:product-story-June-21-2016-04==case6", subMap6);
			
			/************************************************************************/
			
			/********* case6 ********************************************************/
			/* 7) With invalid Author  */
			
			HashMap<String,HashMap<String,String[]>> mainMap7 = new HashMap<>();
			
			HashMap<String,String[]> subMap7 = new HashMap<>();
			
			String[] componentValidationList7 = {"IMAGE_SINGLE","TITLE_TEXT","DESCRIPTION_TEXT","FOOTER_LIKE_SHARE"};
			
			subMap7.put("toValidateList", componentValidationList7);
			mainMap7.put("4:product-story-June-21-2016-05==case7", subMap7);
			
			/************************************************************************/
			
			
			
			dataSet = new Object[][]{{mainMap1},{mainMap2},{mainMap3},{mainMap4},{mainMap5},{mainMap6},{mainMap7}};
			
		}
		
		return dataSet;
	}
	
	@DataProvider
	public Object[][] orderConfirmIndividualCardDP(){
		
		Object[][] dataSet = null;
		
		if(envName.equalsIgnoreCase("fox7")){
			
			/********* case1 ********************************************************/
			/* 1) With valid basic structure for product rack large */
			
			HashMap<String,HashMap<String,String[]>> mainMap1 = new HashMap<>();
			
			HashMap<String,String[]> subMap1 = new HashMap<>();
			
			String[] componentValidationList1 = {"TITLE_TEXT", "PRODUCT_RACK"};
			
			subMap1.put("toValidateList", componentValidationList1);
			mainMap1.put("10:ORDER_CONFIRM3==case1", subMap1);
			
			/************************************************************************/
			
			dataSet = new Object[][]{{mainMap1}};
		}
		
		return dataSet;
	}
	
	@DataProvider
	public Object[][] orderDispatchIndividualCardDP(){
		
		Object[][] dataSet = null;
		
		if(envName.equalsIgnoreCase("fox7")){
			
			/********* case1 ********************************************************/
			/* 1) With valid basic structure for product rack large */
			
			HashMap<String,HashMap<String,String[]>> mainMap1 = new HashMap<>();
			
			HashMap<String,String[]> subMap1 = new HashMap<>();
			
			String[] componentValidationList1 = {"TITLE_TEXT", "PRODUCT_RACK"};
			
			subMap1.put("toValidateList", componentValidationList1);
			mainMap1.put("10:ORDER_DISPATCH1==case1", subMap1);
			
			/************************************************************************/
			
			dataSet = new Object[][]{{mainMap1}};
		}
		
		return dataSet;
	}
	
	@DataProvider
	public Object[][] productRackLargeIndividualCardDP(){
		
		Object[][] dataSet = null;
		
		if(envName.equalsIgnoreCase("fox7")){
			
			/********* case1 ********************************************************/
			/* 1) With valid basic structure for product rack large */
			
			HashMap<String,HashMap<String,String[]>> mainMap1 = new HashMap<>();
			
			HashMap<String,String[]> subMap1 = new HashMap<>();
			
			String[] componentValidationList1 = {"TITLE_TEXT", "PRODUCT_RACK"};
			
			subMap1.put("toValidateList", componentValidationList1);
			mainMap1.put("6:PRODUCTBUGFIX_2==case1", subMap1);
			
			/************************************************************************/
			
			dataSet = new Object[][]{{mainMap1}};
		}
		
		return dataSet;
	}
		
	
	

	
}
