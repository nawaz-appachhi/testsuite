package com.myntra.apiTests.portalservices.lgpservices;

public enum LGPENUMS {
	
	
	/* Flags */
	TRUE("true"),
	FALSE("false"),
	
	/* Test Flow Type Enums*/
	
	CARD_TYPE_TESTS("Card-Type Tests"),
	SPONSOR_TESTS("Sponsor-Type Tests"),
	ORGANIC_TARGET_TESTS("Organic-Target Type Tests"),
	SCENARIO_TYPE(""),
	
	/* Card Types */
	
	ARTICLE("article"),
	BANNER("banner"),
	COLLECTIONS("collections"),
	PRODUCT("product"),
	POSToSTYLEUPDATE("post.styleupdate"),
	POSToSHOT("post.shot"),
	SPLITBANNER("split-banner"),
	VIDEO("video"),
	
	
	/* Platform, User specific Targets */
	
	PLATFORM_TARGET(""),
	INCLUDE_EXPRESSION(""),
	
	
	/* Payload Relative Path */
	PAYLOAD_RELATIVE_PATH(""),
	
	/* To include the cards for publish */
	INCLUDE_CARD_FOR_PUBLISH(""),
	
	/* Card Fields */
	
	APP_ID(""),
	REF_ID(""),
	CARD_TYPE(""),
	CARD_TITLE(""),
	CARD_DESCRIPTION(""),
	CARD_URL(""),
	CARD_IMAGE_URL(""),
	CARD_AUTHOR(""),
	CARD_EXTRA_DATA(""),
	CARD_TOPICS(""),
	CARD_PRODUCT_RACK(""),
	CARD_IS_PRIVATE(""),
	CARD_CREATE_ACTION(""),
	CARD_ENABLED(""),
	CARD_IS_SPAM(""),
	
	/* Enum related to card-type validation*/
	CARD_OBJECT_ID(""),
	CARD_COMPONENTS_FOR_VALIDATION(""),
	CARD_COMBINATION_TYPE(""),
	CARD_VERSION_SPECIFICATION("");
	
	public String lgpEnumFieldValue;
	
	private LGPENUMS(String cLgpEnumFieldValue) {
		
		lgpEnumFieldValue = cLgpEnumFieldValue;
		
	}
	
	

}
