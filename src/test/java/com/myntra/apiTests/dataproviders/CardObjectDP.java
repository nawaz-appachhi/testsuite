package com.myntra.apiTests.dataproviders;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.myntra.apiTests.portalservices.lgpServe.feed.FeedObjectHelper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.portalservices.lgpservices.SNSTests;
import com.myntra.lordoftherings.CardPayloads;
import com.myntra.lordoftherings.LgpArticleCardPOJO;
import com.myntra.lordoftherings.LgpBannerCardPOJO;
import com.myntra.lordoftherings.LgpCollectionsCardPOJO;
import com.myntra.lordoftherings.LgpPostoShotPOJO;
import com.myntra.lordoftherings.LgpPostoStyleUpdatePOJO;
import com.myntra.lordoftherings.LgpProductCardPOJO;
import com.myntra.lordoftherings.LgpSplitBannerPOJO;
import com.myntra.lordoftherings.LgpVideoPOJO;

public class CardObjectDP extends FeedObjectHelper {
	
	String envName = SNSTests.init.Configurations.GetTestEnvironemnt().toString().toLowerCase();
	
	public String getCurrentTimeStamp(){
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		
		String currentTimeStamp = dateFormat.format(cal.getTime());
		return currentTimeStamp;
	}
	
	public String getUniqueCardObjectId(CardPayloads cardType){
		
		String cardObjectId = null;
		String currentTimeStamp = null;
		
		switch (cardType) {

			case ARTICLE_CARD:
				
				currentTimeStamp = getCurrentTimeStamp();
				cardObjectId = cardType.filename.concat("-").concat(currentTimeStamp);
				
				break;
	
			case BANNER_CARD:
				
				currentTimeStamp = getCurrentTimeStamp();
				cardObjectId = cardType.filename.concat("-").concat(currentTimeStamp);
				
				break;
	
			case COLLECTIONS_CARD:
				
				currentTimeStamp = getCurrentTimeStamp();
				cardObjectId = cardType.filename.concat("-").concat(currentTimeStamp);
				
				break;
				
			case POSToSTYLEUPDATE:
				
				currentTimeStamp = getCurrentTimeStamp();
				cardObjectId = cardType.filename.concat("-").concat(currentTimeStamp);
				break;
				
			case POSToSHOT_CARD:
				
				currentTimeStamp = getCurrentTimeStamp();
				cardObjectId = cardType.filename.concat("-").concat(currentTimeStamp);
				break;
				
			case PRODUCT_CARD:
				
				currentTimeStamp = getCurrentTimeStamp();
				cardObjectId = cardType.filename.concat("-").concat(currentTimeStamp);
				
				break;
			
			case SPLIT_BANNER_CARD:
				
				currentTimeStamp = getCurrentTimeStamp();
				cardObjectId = cardType.filename.concat("-").concat(currentTimeStamp);
				
				break;
				
			case VIDEO_CARD:
				
				currentTimeStamp = getCurrentTimeStamp();
				cardObjectId = cardType.filename.concat("-").concat(currentTimeStamp);
				
				break;
			
			case POLL_CARD:
				break;
			case QUESTION_CARD:
				break;
			
			default:
				break;
		}
		
		
		
		return cardObjectId;
	}
	
	@DataProvider
	public Object[][] articleRegisterDP() throws JSONException{
		
		Object[][] dataSet = null;
		LgpArticleCardPOJO articleObj1 = new LgpArticleCardPOJO(
				
				"2", 
				getUniqueCardObjectId(CardPayloads.ARTICLE_CARD), 
				"article", 
				"Still in love with these?", 
				"Don't let it get away.", 
				"http://www.myntra.com/cart", 
				"http://assets.myntassets.com/h_240,q_100,w_180/v1/image/style/properties/916996/RANGMANCH-BY-PANTALOONS-Women-Kurtas_1_086ca36b57a50e83e1adf99453fc55c0.jpg", 
				JSONObject.escape("{\"aspectRatio\":\"251:167\",\"quote\" : \"article quote\"}"), 
				new JSONArray("[{\"id\" : 3806,\"name\": \"casual\",\"type\": \"occasion\", \"displayName\": \"casual\"}]").toString(), 
				JSONObject.escape("{\"productIds\":[\"339187\",\"209125\",\"288501\"]}"), 
				"false", 
				"published", 
				"true", 
				"false");
		
		LgpArticleCardPOJO[] articleCardObjectArray1 = {articleObj1};
		
		dataSet = new Object[][]{articleCardObjectArray1};
		
		
		return dataSet;
	}
	
	@DataProvider
	public Object[][] bannerRegisterDP() throws JSONException{
		
		Object[][] dataSet = null;
		
		LgpBannerCardPOJO bannerObj1 = new LgpBannerCardPOJO(
				
				"2", 
				getUniqueCardObjectId(CardPayloads.BANNER_CARD), 
				"banner", 
				"/men-leather-jackets?sort=popularity",
				"/men-leather-jackets?sort=popularity", 
				"http://assets.myntassets.com/v1/Trend-refresh-02_021445922499126_eq47qs.jpg",
				JSONObject.escape("{\"aspectRatio\":\"251:167\"}"),
				"false", 
				"posted", 
				"true", 
				"false");
		
		LgpBannerCardPOJO[] bannerCardObjectArray1 = {bannerObj1};
		
		dataSet = new Object[][]{bannerCardObjectArray1};
		
		return dataSet;
		
	}
	
	@DataProvider
	public Object[][] collectionsRegisterDP() throws JSONException{
		
		Object[][] dataSet = null;
		
		LgpCollectionsCardPOJO collectionsObj1 = new LgpCollectionsCardPOJO(
				
				"5",
				getUniqueCardObjectId(CardPayloads.COLLECTIONS_CARD),
				"collections",
				"Mumbai Sartorial Guide 2016", 
				"The ultimate men's shopping guide for Mumbai city. We at UrbanEye, India's coolest menswear and luxury blog present the best addresses for men to shop in Mumbai.", 
				"http://www.urbaneye.in/urbanstyle/mumbai-sartorial-guide-2015/6", 
				"http://assets.myntassets.com/assets/images/2015/8/4/11438709917576-file", 
				JSONObject.escape("{\"aspectRatio\":\"251:167\"}"),
				new JSONArray("[{\"id\" : 4049,\"name\": \"test\",\"type\": \"theme\", \"displayName\": \"test\"},"
						+ "	{\"id\" : 260,\"name\": \"hrx\",\"type\": \"brand\", \"displayName\": \"Hrx\"}]").toString(), 
				"false",
				"posted", 
				"true", 
				"false");
		
		LgpCollectionsCardPOJO[] collectionsCardObjectArray1 = {collectionsObj1};
		dataSet = new Object[][]{collectionsCardObjectArray1};
		
		return dataSet;
	}
	
	
	@DataProvider
	public Object[][] productRegisterDP() throws JSONException{
		
		Object[][] dataSet = null;
		
		LgpProductCardPOJO productObj1 = new LgpProductCardPOJO(
				
				"2",
				getUniqueCardObjectId(CardPayloads.PRODUCT_CARD),
				"product",
				"Mumbai Sartorial Guide 2016",
				"The ultimate men's shopping guide for Mumbai city. We at UrbanEye, India's coolest menswear and luxury blog present the best addresses for men to shop in Mumbai.",
				"http://www.urbaneye.in/urbanstyle/mumbai-sartorial-guide-2015/6",
				"http://assets.myntassets.com/assets/images/2015/8/4/11438709917576-file",
				JSONObject.escape("{\"aspectRatio\":\"251:167\"}"),
				new JSONArray("[{\"id\" : 4049,\"name\": \"test\",\"type\": \"theme\", \"displayName\": \"test\"},"
							+ "	{\"id\" : 3806,\"name\": \"casual\",\"type\": \"occasion\", \"displayName\": \"casual\"}]").toString(),
				"false", 
				"posted",
				"true", 
				"false");
		
		
		LgpProductCardPOJO[] productCardObjectaArray1 = {productObj1};
		dataSet = new Object[][]{productCardObjectaArray1};
		
		return dataSet;
	}
	
	@DataProvider
	public Object[][] postoStyleUpateDP() throws JSONException{
		
		Object[][] dataSet = null;
		
		LgpPostoStyleUpdatePOJO postoStyleUpdateObj1 = new LgpPostoStyleUpdatePOJO(
				
				"7", 
				getUniqueCardObjectId(CardPayloads.POSToSTYLEUPDATE), 
				"post.styleupdate", 
				"POST STYLEUPDATE !!", 
				"Enjoy your time out in the sun with the range from all about you.", 
				"http://www.myntra.com/carlton-london?userQuery=true&s=acc&f=gender%3Amen%2520women%2Cwomen", 
				"http://assets.myntassets.com/assets/images/2016/2/26/11456467033748-nn.jpg", 
				JSONObject.escape("{\"aspectRatio\":\"251:167\"}"), 
				"970794db.228e.4133.933e.1574198ef08frA7cPl428x", 
				new JSONArray("[{\"id\" : 1746,\"name\": \"tops\",\"type\": \"articletype\", \"displayName\": \"Tops\"},"
						+ "	{\"id\" : 3806,\"name\": \"casual\",\"type\": \"occasion\", \"displayName\": \"casual\"}]").toString(),
				JSONObject.escape("{\"productIds\":[\"339187\",\"209125\",\"288501\"]}"),
				"false", 
				"publish", 
				"true", 
				"false");
		LgpPostoStyleUpdatePOJO[] postoStyleUpdateArray1 = {postoStyleUpdateObj1};
		
		dataSet = new Object[][]{postoStyleUpdateArray1};
		return dataSet;
	}
	
	@DataProvider
	public Object[][] postoShotDP() throws JSONException{
		
		Object[][] dataSet = null;
		String refId = getUniqueCardObjectId(CardPayloads.POSToSHOT_CARD);
		
		LgpPostoShotPOJO postoShotObj1 = new LgpPostoShotPOJO(
				
				"7", 
				refId, 
				"post.shot", 
				"SHOTS !!!", 
				"#ootd #date #dealdress #luvdit", 
				"http://myntra.com/shot/7:"+refId, 
				"http://assets.myntassets.com/assets/images/2016/4/28/11461850324812-142959-7qv7nh.jpg", 
				JSONObject.escape("{\"aspectRatio\":\"251:167\"}"), 
				"970794db.228e.4133.933e.1574198ef08frA7cPl428x", 
				new JSONArray("[{\"id\" : 1238,\"name\": \"deal jeans\",\"type\": \"brand\", \"displayName\": \"Deal jeans\"},"
						+ "	{\"id\" : 1675,\"name\": \"dresses\",\"type\": \"articletype\", \"displayName\": \"Dresses\"}]").toString(), 
				JSONObject.escape("{\"productIds\":[\"339187\",\"209125\",\"288501\"]}"),
				"false", 
				"publish", 
				"true", 
				"false");
		
		LgpPostoShotPOJO[] postoShotArray1 = {postoShotObj1};
		
		dataSet = new Object[][]{postoShotArray1};
		
		
		return dataSet;
		
	}
	
	@DataProvider
	public Object[][] splitBannerDP() throws JSONException{
		
		Object[][] dataSet = null;
		
		LgpSplitBannerPOJO splitBannerObj1 = new LgpSplitBannerPOJO(
				
				"2", 
				getUniqueCardObjectId(CardPayloads.SPLIT_BANNER_CARD), 
				"split-banner", 
				"Split Banner", 
				"http://assets.myntassets.com/assets/images/banners/2016/1/13/11452679232432-brand-splits_11.jpg,http://assets.myntassets.com/assets/images/banners/2016/1/27/11453877033903-brand-splits_10_3_1453196077660_vjbay5.jpg", 
				JSONObject.escape("{\"children\":[\"2:b98d4fc0-fa71-4749-a173-a8745c39b1d8\",\"2:64d5aa80-8715-4094-8909-72e8e8246d4a\"]}"), 
				new JSONArray("[{\"id\" : 3796,\"name\": \"deals\",\"type\": \"brand\", \"theme\": \"Deals\"},"
						+ "	{\"id\" : 1675,\"name\": \"dresses\",\"type\": \"articletype\", \"displayName\": \"Dresses\"}]").toString(), 
				"false", 
				"publish", 
				"true", 
				"false");
		
		LgpSplitBannerPOJO[] splitBannerArray1 = {splitBannerObj1};
		dataSet = new Object[][]{splitBannerArray1};
		
		return dataSet;
		
	}
	
	@DataProvider
	public Object[][] videoDP() throws JSONException{
		
		Object[][] dataSet = null;
		
		LgpVideoPOJO videoObj1 = new LgpVideoPOJO(
				
				"2", 
				getUniqueCardObjectId(CardPayloads.VIDEO_CARD), 
				"video", 
				"Testing Video Card", 
				"Validating rules for Video card", 
				"https://www.youtube.com/watch?v=XKbiIahG6okkkdcmnklnknckmcklmc", 
				"http://assets.myntassets.com/v1/image/style/properties/777272/Lomdmflkdmcmmxklsmdklmsksflkaummlmis-Philippe-Menfgsgfgsdhfgdsk-Formal-Shoes-mc,mv,cxblack_1_2c8da54fdc92ae82bd4a8da6e1d46618.jpg",
				JSONObject.escape("{\"aspectRatio\":\"251:167\"}"),
				"970794db.228e.4133.933e.1574198ef08frA7cPl428x", 
				new JSONArray("[{\"id\" : 260,\"name\": \"hrx\",\"type\": \"brand\", \"theme\": \"HRX\"},"
						+ "	{\"id\" : 1675,\"name\": \"dresses\",\"type\": \"articletype\", \"displayName\": \"Dresses\"}]").toString(),
				JSONObject.escape( "{\"productIds\":[\"339187\",\"209125\",\"288501\"]}"), 
				"false", 
				"publish", 
				"true", 
				"false");
		
		LgpVideoPOJO[] videoArray1 = {videoObj1};
		dataSet = new Object[][]{videoArray1};
		
		return dataSet;
		
	}

}

























