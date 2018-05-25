package com.myntra.apiTests.inbound.helper.planningandbuying;

import org.apache.commons.lang.RandomStringUtils;

public class TeamServiceConstants {
	
	public static class TEAMSERVICE_ENDPOINTS {
		public static final String APPLICATION = "team-service/application/";
		public static final String TEAM_TYPE = "team-service/type/";
		public static final String TEAM = "team-service/team/";
	}
	
	public static class STATUS_CODES {
		public static final String CREATE_APPLICATION = "30001";
		public static final String CREATE_TEAMTYPE = "30001";
		public static final String CREATE_TEAM = "30001";
		public static final String GET_APPLICATION_BY_ID = "30003";
		public static final String GET_TEAMTYPE_BY_ID = "30003";
		public static final String GET_TEAM_BY_ID = "30003";
		public static final String CREATE_DUPLICATE_APPLICATION = "74";
		
		
	}
	
	public static class STATUS_MESSAGES {
		public static final String CREATE_APPLICATION = "Application added successfully";
		public static final String CREATE_TEAMTYPE = "Team type added successfully";
		public static final String CREATE_TEAM = "Team added successfully";
		public static final String GET_APPLICATION_BY_ID = "Application fetched successfully";
		public static final String GET_TEAMTYPE_BY_ID = "Team type fetched successfully";
		public static final String GET_TEAM_BY_ID = "Team fetched successfully";
		public static final String CREATE_DUPLICATE_APPLICATION = "Unique Key contraint violation";
	}
	
	public static class STATUS_TYPES {
		public static final String SUCCESS = "SUCCESS";
		public static final String FAILURE = "ERROR";
	}
	
	public static class ATTRIBUTES {
		public static final String[] MMB_ATTRIBUTETYPE_CODE = {"BU", "BRAND", "ARTICLETYPE"};
		public static final String[] MFB_ATTRIBUTETYPE_CODE = {"MASTERCATEGORY", "BRANDGROUP", "GENDER", "USAGE", "COLLECTION"};
		public static final int[] LEVEL = {0,1,2,3,4};
		public static final String[] MMB_ATTRIBUTE_CODE = {"MC", "NIKE" + RandomStringUtils.randomAlphanumeric(5), "TSHRT"};
		public static final String[] MFB_ATTRIBUTE_CODE = {};
		
	}

}
