package com.myntra.apiTests.inbound.helper;

public class SpotConstants {

	public static class ArticleType {
		public static final String TSHIRTS = "Tshirts";
		public static final String JEANS = "Jeans";
		public static final String KURTAS = "Kurtas";
		public static final String ARTICLE_TYPE="article_type";
		public static final String BRAND="brand";
		public static final String GENDER="gender";
		public static final String SEASON_CODE="season_code";
	}

	public static class FilterType {
		public static final String NONE = "NONE";
		public static final String TOP_SELLER_ROUND = "Top Seller Round";
		public static final String BOTTOM_SELLER_ROUND = "Bottom Seller Round";
		public static final String TOP_SELLER_POLO = "Top Seller Polo";
		public static final String TOP_SELLER_A2C_ROUND = "Top Seller A2C Round";
		public static final String TOP_SELLER_A2C_POLO = "Top Seller A2C Polo";
		public static final String BETA_TOP_SELLER = "Beta Top Seller";
		public static final String BOTTOM_SELLER_POLO = "Bottom Seller Polo";
	}

	public static class Brands {
		public static final String ALL = "All";
		public static final String REEBOK = "REEBOK";
		public static final String PUMA = "Puma";
	}

	public static class SeasonCode {
		public static final String SEASONCODE = "SS16,SS15,FW12,FW13,FW14,FW15,SS12,SS13,SS14,FW11,SS11";
	}

	public static class Source {
		public static final String MYNTRA = "cms";
		public static final String JABONG = "jabong";
		public static final String AMAZON = "amazon";
	}

	public static class Gender {
		public static final String MEN = "Men";
		public static final String WOMEN = "Women";
		public static final String BOYS = "Boys";
		public static final String GIRLS = "Girls";
		public static final String UNISEX = "Unisex";
	}
	public static class Jabong_Gender {
		public static final String MEN = "men";
		public static final String WOMEN = "women";
		public static final String BOYS = "boys";
		public static final String GIRLS = "girls";
		public static final String UNISEX = "unisex";
		public static final String KIDS = "kids";
	}

	public static class TopSeller_Round_TSHIRTS {
		public static final String NECKCOLLAR = "Neck or Collar";
		public static final String NECKCOLLAR_VALUE = "Round Neck";
		public static final Integer ASPMIN = 400;
		public static final Integer ASPMAX = 9999;
		public static final Integer DAYSLIVE = 4;
		public static final Double PTD = 0.3;
		public static final Integer ROS = 5;
	}

	public static class BottomSeller {
		public static final String NECKCOLLAR = "Round Neck";
		public static final Integer ASPMIN = 400;
		public static final Integer DAYSLIVE = 4;
		public static final Double PTD = 0.3;
		public static final Integer ROS = 1;

	}

	public static class TopSellerA2C {
		public static final String NECKCOLLAR = "Round Neck";
		public static final Integer ASPMIN = 400;
		public static final Integer DAYSLIVE = 4;
		public static final Double PTD = 0.3;
		public static final Integer ROS = 1;

	}

	public static class AMAZON_LIST{
		public static final String RESULTS = "results";
		public static final String SECTIONS = "sections";
		public static final String ITEMS = "items";
		public static final String ASIN = "asin";
		public static final String TITLE = "title";
		public static final String BRANDNAME = "brandName";
		public static final String LINK = "link";
		public static final String URL = "url";
		public static final String LINK_URL = "Link_Url";
		public static final String IMAGE = "image";
		public static final String IMAGE_URL = "Image_Url";
		public static final String PRICES = "prices";
		public static final String BUY = "buy";
		public static final String LISTPRICE = "listPrice";
		public static final String PRICE = "price";
		public static final String AMAZON_LIST_ASIN = "Amazon_List_Asin";
		public static final String AMAZON_LIST_TITLE = "Amazon_List_title";
		public static final String AMAZON_LIST_BRANDNAME = "Amazon_List_brandName";
		public static final String AMAZON_LIST_LINK = "Amazon_List_link";
		public static final String AMAZON_LIST_URL = "Amazon_List_url";
		public static final String AMAZON_LIST_LINK_URL = "Amazon_List_Link_Url";
		public static final String AMAZON_LIST_IMAGE = "Amazon_List_image";
		public static final String AMAZON_LIST_ = "Amazon_List_Image_Url";
		public static final String AMAZON_LIST_PRICES = "Amazon_List_prices";
		public static final String AMAZON_LIST_BUY = "Amazon_List_buy";
		public static final String AMAZON_LIST_LISTPRICE = "Amazon_List_listPrice";
		public static final String AMAZON_LIST_PRICE = "Amazon_List_price";
		
		
	}

}
