package com.myntra.apiTests.portalservices.lgpservices;

public enum CardCombinationEnum {
	
	
	/* Article Combination Types */
	ARTICLE_WITHOUT_AUTHOR_WITH_PRODUCT_RACK("Article without author with product rack"),
	ARTICLE_WITH_AUTHOR_WITHOUT_PRODUCT_RACK("Article with author without product product rack"),
	ARTICLE_WITH_AUTHOR_WITH_PRODUCT_RACK("Article with author with product rack"),
	ARTICLE_WITH_PRODUCT_RACK_INVALID("Article with product rack invalid"),
	ARTICLE_WITH_VERSION_SPECIFICATION("Article with version specification"),
	
	/* Banner Combination */
	BANNER_BASIC_STRUCTURE("Banner basic"),
	
	/* Split Banner Combinations */
	SPLIT_BANNER_BASIC_STRUCTURE("Split Banner Basic Structure"),
	SPLIT_BANNER_WITH_INVALID_CHILDREN("Split Banner with Invalid Children"),
	SPLIT_BANNER_WITH_NO_CHILDREN("Split Banner With no children"),
	SPLIT_BANNER_WITH_THREE_CHILDREN("Split Banner with 3 children"),
	
	/* Collections Combinations*/
	COLLECTIONS_WITH_VERSION_SPECIFICATION("Collections Version Specification"),
	COLLECTIONS_BASIC_STRUCTURE_WITH_INVALID_CREATOR_TITLE("Collections Basic Structure with invalid Creator Title"),
	COLLECTIONS_BASIC_STRUCTURE_WITHOUT_CREATOR_TITLE("Collections Basic Structure without Creator Title"),
	COLLECTIONS_BASIC_STRUCTURE_WITH_CREATOR_TITLE("Collections Basic Structure with Creator Title"),
	
	/* PostoShot Combinations*/
	POSToSHOT_WITH_VERSION_SPECIFICATION("PostoShot with version specification"),
	POSToSHOT_WITH_INVALID_AUTHOR("PostoShot with invalid Author"),
	POSToSHOT_WITHOUT_CREATOR_TITLE_WITH_PRODUCT_RACK("PostoShot without creator title with product rack"),
	POSToSHOT_WITHOUT_CREATOR_TITLE_WITHOUT_PRODUCT_RACK("PostoShot without Creator Title and without product rack"),
	POSToSHOT_WITHOUT_PRODUCT_RACK("PostoShot without Product Rack"),
    POSToSHOT_WITH_INVALID_PRODUCT_IDS_IN_PRODUCT_RACK("PostoShot with invalid product id's in product rack"),
	POSToSHOT_WITH_SEPARATOR_PRODUCT_RACK_CREATOR_TITLE("PostoShot with separator, product rack, creator title");
	
    public String cardCombinationEnumField;
	
	
	private CardCombinationEnum(String cCardCombinationEnum) {
		
		cardCombinationEnumField = cCardCombinationEnum;
	}
	

}
