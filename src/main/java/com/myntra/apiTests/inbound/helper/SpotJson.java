package com.myntra.apiTests.inbound.helper;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.myntra.lordoftherings.aragorn.pages.spot.SpotConstants.BOTTOM_SELLER_POLO;
//import com.myntra.lordoftherings.aragorn.pages.spot.SpotConstants.BOTTOM_SELLER_ROUND;
//import com.myntra.lordoftherings.aragorn.pages.spot.SpotConstants.TOP_SELLER_A2C_POLO;
//import com.myntra.lordoftherings.aragorn.pages.spot.SpotConstants.TOP_SELLER_POLO;
//import com.myntra.lordoftherings.aragorn.pages.spot.SpotConstants.TOP_SELLER_ROUND;



public class SpotJson {
	static Logger log = Logger.getLogger(SpotJson.class);

	public String spotPayload(String from_style_date, String to_style_date, String from_order_date,
			String to_order_date, String article_type, String brand, String filter_type, String season_code,
			String gender, String source)  throws Exception{

		SpotObjects spot = new SpotObjects();
		spot.setStyle_date_from(from_style_date);
		spot.setStyle_date_to(to_style_date);
		spot.setOrder_date_from(from_order_date);
		spot.setOrder_date_to(to_order_date);
		spot.setArticle_type(article_type);
		spot.setBrand(brand);
		spot.setFilter_type(filter_type);
		spot.setSeason_code(season_code);
		spot.setGender(gender);
		spot.setSource(source);

		JSONObject obj = new JSONObject(spot);
		log.debug("\nSpot Rapid payload file is:"+obj.toString()+"\n");
		return obj.toString();
	}
	
	public String spotPayload(String from_style_date, String to_style_date, String from_order_date,
			String to_order_date, String article_type, String brand, String season_code,
			String gender, String source)  throws Exception{
		SpotObjects spot = new SpotObjects();
		
		spot.setStyle_date_from(from_style_date);
		spot.setStyle_date_to(to_style_date);
		spot.setOrder_date_from(from_order_date);
		spot.setOrder_date_to(to_order_date);
		spot.setArticle_type(article_type);
		spot.setBrand(brand);
		spot.setGender(gender);
		spot.setSource(source);
		
		if(source.equalsIgnoreCase(SpotConstants.Source.MYNTRA)){
			spot.setSeason_code(season_code);
		}
		
		
		 ObjectMapper mapper = new ObjectMapper();
		    mapper.setSerializationInclusion(Include.NON_EMPTY);
		    mapper.setSerializationInclusion(Include.NON_DEFAULT);
		    String payload=  mapper.writeValueAsString(spot);	
		
		
		log.debug("\nSpot Rapid payload file is:"+payload+"\n");
		return payload;
	}

	public String spotTopSellerPoloTshirtsFilterPayload(String collId) throws Exception {

		TopSellerFilter topseller = new TopSellerFilter();
		Filter filter = new Filter();
		Asp asp = new Asp();
		DaysLive dayslive = new DaysLive();
		PTd ptd = new PTd();
		Ros ros = new Ros();
//		asp.setGreater_than_equal_to(TOP_SELLER_POLO.ASP);
//		dayslive.setGreater_than_equal_to(TOP_SELLER_POLO.DAYS_LIVE);
//		ptd.setLess_than_equal_to(TOP_SELLER_POLO.P_TD);
//		ros.setGreater_than_equal_to(TOP_SELLER_POLO.ROS);
//		
//		filter.setNeck_or_Collar(TOP_SELLER_POLO.NECK_COLLAR);
		filter.setAsp(asp);
		filter.setDays_live(dayslive);
		filter.setP_td(ptd);
		filter.setRos(ros);
		
		//filter.
		ArrayList<Filter> list = new ArrayList<Filter>();
		
		list.add(filter);
		topseller.setFilters(list);
		topseller.setColl_id(collId);
		topseller.setSort_by("add_to_cart_count");
		topseller.setSource(SpotConstants.Source.MYNTRA);

		 ObjectMapper mapper = new ObjectMapper();
		    mapper.setSerializationInclusion(Include.NON_EMPTY);
		    mapper.setSerializationInclusion(Include.NON_DEFAULT);
		    String filter_type=  mapper.writeValueAsString(topseller);
		    
		filter_type=filter_type.replace("neck_or_Collar", "Neck or Collar");
		log.debug("\nSpot Rapid filter payload file for Top Seller Polo is:"+filter_type.toString()+"\n");
		return filter_type;
	}
	
	public String spotTopSellerRoundTshirtsFilterPayload(String collId) throws Exception {

		TopSellerFilter topseller = new TopSellerFilter();
		Filter filter = new Filter();
		Asp asp = new Asp();
		DaysLive dayslive = new DaysLive();
		PTd ptd = new PTd();
		Ros ros = new Ros();
//		
//		asp.setGreater_than_equal_to(TOP_SELLER_ROUND.ASP_MIN);
//		asp.setLess_than_equal_to(TOP_SELLER_ROUND.ASP_MAX);	
//		dayslive.setGreater_than_equal_to(TOP_SELLER_ROUND.DAYS_LIVE);
//		ptd.setLess_than_equal_to(TOP_SELLER_ROUND.P_TD);
//		ros.setGreater_than_equal_to(TOP_SELLER_ROUND.ROS);
//		
//		filter.setNeck_or_Collar(TOP_SELLER_ROUND.NECK_COLLAR);
		filter.setAsp(asp);
		filter.setDays_live(dayslive);
		filter.setP_td(ptd);
		filter.setRos(ros);
		
		//filter.
		ArrayList<Filter> list = new ArrayList<Filter>();
		
		list.add(filter);
		topseller.setFilters(list);
		topseller.setColl_id(collId);
		topseller.setSort_by("add_to_cart_count");
		topseller.setSource(SpotConstants.Source.MYNTRA);

		 ObjectMapper mapper = new ObjectMapper();
		    mapper.setSerializationInclusion(Include.NON_EMPTY);
		    String filter_type=  mapper.writeValueAsString(topseller);
		    
		filter_type=filter_type.replace("neck_or_Collar", "Neck or Collar");
		log.debug("\nSpot Rapid filter payload file for Top SellerRound is:"+filter_type.toString()+"\n");
		return filter_type;
	}
	
	public String spotTopSellerA2CPoloTshirtsFilterPayload(String collId) throws Exception {

		TopSellerFilter topseller = new TopSellerFilter();
		Filter filter = new Filter();
		Asp asp = new Asp();
		DaysLive dayslive = new DaysLive();
		PTd ptd = new PTd();
		Ros ros = new Ros();
		
//		asp.setGreater_than_equal_to(TOP_SELLER_A2C_POLO.ASP);
//		asp.setLess_than_equal_to(TOP_SELLER_ROUND.ASP_MAX);	
//		dayslive.setGreater_than_equal_to(TOP_SELLER_ROUND.DAYS_LIVE);
//		ptd.setLess_than_equal_to(TOP_SELLER_ROUND.P_TD);
//		ros.setGreater_than_equal_to(TOP_SELLER_ROUND.ROS);
//		
//		filter.setNeck_or_Collar(TOP_SELLER_ROUND.NECK_COLLAR);
		filter.setAsp(asp);
		filter.setDays_live(dayslive);
		filter.setP_td(ptd);
		filter.setRos(ros);
		
		//filter.
		ArrayList<Filter> list = new ArrayList<Filter>();
		
		list.add(filter);
		topseller.setFilters(list);
		topseller.setColl_id(collId);
		topseller.setSort_by("add_to_cart_count");
		topseller.setSource(SpotConstants.Source.MYNTRA);

		 ObjectMapper mapper = new ObjectMapper();
		    mapper.setSerializationInclusion(Include.NON_EMPTY);
		    String filter_type=  mapper.writeValueAsString(topseller);
		    
		filter_type=filter_type.replace("neck_or_Collar", "Neck or Collar");
		log.debug("\nSpot Rapid filter payload file for Top Seller A2C Round is:"+filter_type.toString()+"\n");
		return filter_type;
	}
	public String spotBottomSellerPoloTshirtsFilterPayload(String collId) throws Exception {

		TopSellerFilter topseller = new TopSellerFilter();
		Filter filter = new Filter();
		Asp asp = new Asp();
		DaysLive dayslive = new DaysLive();
		PTd ptd = new PTd();
		Ros ros = new Ros();
		
//		asp.setGreater_than_equal_to(BOTTOM_SELLER_POLO.ASP);
//		ros.setLess_than_equal_to((BOTTOM_SELLER_POLO.ROS));
//		dayslive.setGreater_than_equal_to(BOTTOM_SELLER_POLO.DAYS_LIVE);
//		ptd.setGreater_than_equal_to(BOTTOM_SELLER_POLO.P_TD);
//		
//		
//		filter.setNeck_or_Collar(BOTTOM_SELLER_POLO.NECK_COLLAR);
		filter.setAsp(asp);
		filter.setDays_live(dayslive);
		filter.setP_td(ptd);
		filter.setRos(ros);
		
		//filter.
		ArrayList<Filter> list = new ArrayList<Filter>();
		
		list.add(filter);
		topseller.setFilters(list);
		topseller.setColl_id(collId);
		topseller.setSort_by("add_to_cart_count");
		topseller.setSource(SpotConstants.Source.MYNTRA);

		 ObjectMapper mapper = new ObjectMapper();
		    mapper.setSerializationInclusion(Include.NON_EMPTY);
		    String filter_type=  mapper.writeValueAsString(topseller);
		    
		filter_type=filter_type.replace("neck_or_Collar", "Neck or Collar");
		log.debug("\nSpot Rapid filter payload file for BottomSellerPolo is:"+filter_type.toString()+"\n");
		return filter_type;
	}
	public String spotBottomSellerRoundTshirtsFilterPayload(String collId) throws Exception {

		TopSellerFilter topseller = new TopSellerFilter();
		Filter filter = new Filter();
		Asp asp = new Asp();
		DaysLive dayslive = new DaysLive();
		PTd ptd = new PTd();
		Ros ros = new Ros();
		
//		asp.setGreater_than_equal_to(BOTTOM_SELLER_ROUND.ASP);
//		ros.setLess_than_equal_to((BOTTOM_SELLER_ROUND.ROS));
//		dayslive.setGreater_than_equal_to(BOTTOM_SELLER_ROUND.DAYS_LIVE);
//		ptd.setGreater_than_equal_to(BOTTOM_SELLER_ROUND.P_TD);
//		
//		
//		filter.setNeck_or_Collar(BOTTOM_SELLER_ROUND.NECK_COLLAR);
		filter.setAsp(asp);
		filter.setDays_live(dayslive);
		filter.setP_td(ptd);
		filter.setRos(ros);
		
		//filter.
		ArrayList<Filter> list = new ArrayList<Filter>();
		
		list.add(filter);
		topseller.setFilters(list);
		topseller.setColl_id(collId);
		topseller.setSort_by("add_to_cart_count");
		topseller.setSource(SpotConstants.Source.MYNTRA);

		 ObjectMapper mapper = new ObjectMapper();
		    mapper.setSerializationInclusion(Include.NON_EMPTY);
		    String filter_type=  mapper.writeValueAsString(topseller);
		    
		filter_type=filter_type.replace("neck_or_Collar", "Neck or Collar");
		log.debug("\nSpot Rapid filter payload file for BottomSellerRound is:"+filter_type.toString()+"\n");
		return filter_type;
	}

}
