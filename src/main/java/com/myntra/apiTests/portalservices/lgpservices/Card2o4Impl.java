package com.myntra.apiTests.portalservices.lgpservices;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.CardComponents;
import com.myntra.lordoftherings.ILgpJsonKeys;

import java.util.HashMap;

public class Card2o4Impl implements ILgpJsonKeys {


	@Override
	public String[] getArticleCardJsonPaths() {
		
		String[] jsonPathListings = {	
				 
				"$.cards[*].type[0]",
				"$.cards[*].props.id[0]",
				"$.cards[*].props.meta.og:name[0]",
				"$.cards[*].props.meta.og:app.id[0]",
				"$.cards[*].props.meta.og:title[0]",
				"$.cards[*].props.meta.og:url[0]",
				"$.cards[*].props.meta.og:image:url[0]",
				"$.cards[*].props.meta.share.og:title[0]",
				"$.cards[*].props.meta.share.og:url[0]",
				"$.cards[*].props.meta.share.og:image:url[0]",
				"$..children[0].type",
				"$..children[1].type",
				"$..children[2].type",
				"$..children[3].type",
				"$..children[4].type",
				"$..children[0].props.link",
				"$..children[1].props.link",
				"$..children[2].props.link"
				
		};
		 
		 return jsonPathListings;
	}

	@Override
	public String[] getQuestionCardJsonPaths() {
		
		return null;
	}

	@Override
	public String[] getBannerCardJsonPaths() {
		
		return null;
	}

	@Override
	public String[] getSplitBannerCardJsonPaths() {
		
		return null;
	}

	@Override
	public String[] getVideoCardJsonPaths() {
		
		return null;
	}

	@Override
	public String[] getPollCardJsonpaths() {
		
		return null;
	}

	@Override
	public HashMap<String,String> getStreamCardJsonPaths() {
		// TODO Auto-generated method stub
		HashMap<String, String> jsonPathListings = new HashMap<>();
		jsonPathListings.put("type","$.cards[0].props.meta.og:name");
		jsonPathListings.put("link1","$.cards[0].children[0].props.splits[0].link");
		jsonPathListings.put("link2","$.cards[0].children[0].props.splits[1].link");
		jsonPathListings.put("link3","$.cards[0].children[0].props.splits[2].link");
		return jsonPathListings;
	}

	@Override
	public HashMap<String, String> getHotListCardJsonPaths() {
		// TODO Auto-generated method stub
		HashMap<String, String> jsonPathListings = new HashMap<>();
		jsonPathListings.put("type","$.cards[0].children[0].type");
		jsonPathListings.put("link-men","$.cards[0].children[0].props.splits[0].link");
		jsonPathListings.put("link-women","$.cards[0].children[0].props.splits[1].link");
		jsonPathListings.put("link-kid","$.cards[0].children[0].props.splits[2].link");
		return jsonPathListings;
	}

	@Override
	public HashMap<String, String> getStreamSlideshowCardJsonPaths() {
		HashMap<String, String> jsonPathListings = new HashMap<>();
		jsonPathListings.put("type","$.cards[0].props.meta.og:name");
		jsonPathListings.put("children_type","$.cards[0].children[0].type");
		return jsonPathListings;
	}

	@Override
	public String[] getHeaderContextJsonPaths() {
		String[] jsonPathListings = {	
				 
				"$..children[#].type",
				"$..children[#].props",
//				"$..children[#].props.title[0].text"
		};
		 
		 return jsonPathListings;
	}

	@Override
	public String[] getMosaicJsonPaths() {
		String[] jsonPathListings = {	
				 
				"$..children[#].type"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getCreatorTitleJsonPaths() {
		String[] jsonPathListings = {	
				 
				"$..children[#].type",
				"$..children[#].props",
				"$..children[#].props.action",
				"$..children[#].props.creator",
				"$..children[#].props.creator.link",
				"$..children[#].props.creator.uidx",
				"$..children[#].props.creator.publicProfileId",
		};
		 
		 return jsonPathListings;
	}

	@Override
	public String[] getFeedbackViewlikeJsonPaths() {
		String[] jsonPathListings = {	
				 
				"$..children[#].type",
				"$.props.activities.like"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getProductRackJsonPaths() {
		String[] jsonPathListings = {	
				 
				"$..children[#].type",
				"$..children[#].props",
				"$..children[#].props.products",
				"$..children[#].props.products[0].styleid",
				"$..children[#].props.products[0].brands_facet",
				"$..children[#].props.products[0].stylename",
				"$..children[#].props.products[0].discounted_price",
				"$..children[#].props.products[0].price",
				"$..children[#].props.products[0].link",
				"$..children[#].props.products[0].image",
				"$..children[#].props.products[0].image.width",
				"$..children[#].props.products[0].image.height",
				"$..children[#].props.products[0].image.src",
				
		};
		return jsonPathListings;
	}

	@Override
	public String[] getFooterLikeShareJsonPaths() {
		String[] jsonPathListings = {	
				 
				"$..children[#].type",
				"$.props.activities.like",
				"$.props.meta.share",
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentCardJsonPaths() {
		String[] jsonPathListings = {	
				 
				"$.props",
				"$.type",
				"$.props.id",
				"$.props.meta.og:name"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getDescriptionTextJsonPaths() {
		String[] jsonPathListings = {	
				 
				"$..children[#].type",
				"$..children[#].props",
				"$..children[#].props.text"
//				"$.props.link"
		};
		 
		 return jsonPathListings;
	}

	@Override
	public String[] getTitleTextJsonPaths() {
		String[] jsonPathListings = {	
				 
				"$..children[#].type",
				"$..children[#].props",
				"$..children[#].props.text"
//				"$.props.link"
		};
		 
		 return jsonPathListings;
	}
	
	@Override
	public String[] getQuoteTextJsonPaths(){
		
		String[] jsonPathListings = {	
				 
			    "$..children[#].props.text"
			
		};
		 
		 return jsonPathListings;
		
	}

	@Override
	public String[] getImageSingleJsonPaths() {
		String[] jsonPathListings = {	
				 
				"$..children[#].type",
				"$..children[#].props",
				"$..children[#].props.image",
				"$..children[#].props.image.src"
//				"$.props.image.link"
		};
		 
		 return jsonPathListings;
	}

	@Override
	public String[] getBannerJsonPaths() {
		String[] jsonPathListings = {	
				 
				"$..children[#].type",
				"$..children[#].props",
				"$..children[#].props.image",
				"$..children[#].props.image.src",
				"$..children[#].props.image.width",
				"$..children[#].props.image.height"
//				"$.props.image.link"
		};
		 
		 return jsonPathListings;
	}

	@Override
	public String[] getSplitBannerJsonPaths() {
		String[] jsonPathListings = {	
				 
			"$..children[#].type",
		    "$..children[#].props",
		    "$..children[#].props.splits",
		    "$..children[#].props.splits[0].width",
		    "$..children[#].props.splits[0].height",
		    "$..children[#].props.splits[0].src"
		};
		
		return jsonPathListings;
	}

	@Override
	public String[] getCarouselJsonPaths() {
		String[] jsonPathListings = {	
				 
				"$..children[#].type",
			    "$..children[#].props",
			    "$..children[#].props.carousel",
			    "$..children[#].props.carousel[0].width",
			    "$..children[#].props.carousel[0].height",
			    "$..children[#].props.carousel[0].src"
			};
			
			return jsonPathListings;
	}

	@Override
	public String[] getVideoJsonPaths() {
		String[] jsonPathListings = {	
				 
				"$..children[#].type",
			    "$..children[#].props",
			    "$..children[#].props.image",
			    "$..children[#].props.image.src",
			    "$..children[#].props.video",
			    "$..children[#].props.video.src"
			};
			
			return jsonPathListings;
	}

	@Override
	public String[] getComponentCardHotListJsonPaths() {
		String[] jsonPathListings = {	
				 
				"$.props",
				"$.type",
				"$.props.meta.og:name"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getFooterShareJsonPaths() {
		String[] jsonPathListings = {
				
				"$..children[#].type",
				
		};
		return jsonPathListings;
	}

	@Override
	public String[] getFooterLinksJsonPaths() {
        String[] jsonPathListings = {
				
				"$..children[#].type",	
				"$..children[#].props",
				"$..children[#].props.links",
				"$..children[#].props.links.link",
				"$..children[#].props.links.title",
				"$..children[#].props.links.type"
				
		};
		return jsonPathListings;
	}

	@Override
	public String[] getProductSingleJsonPaths() {
        String[] jsonPathListings = {
				
				"$..children[#].type",
				"$..children[#].props",
				"$..children[#].props.product",
				"$..children[#].props.product.brands_facet",
				"$..children[#].props.product.discounted_price",
				"$..children[#].props.product.price",
				"$..children[#].props.product.styleid",
				"$..children[#].props.product.link",
				"$..children[#].props.product.stylename",
				"$..children[#].props.product.image",
				"$..children[#].props.product.image.src",
				
				
		};
		return jsonPathListings;
	}

	@Override
	public String[] getAnswerSummaryJsonPaths() {
        String[] jsonPathListings = {
				
				"$..children[#].type",
				"$..children[#].props",
				"$..children[#].props.answerData"
				
		};
		return jsonPathListings;
	}

	@Override
	public String[] getOnBoardForWelBackJsonPaths() {
        String[] jsonPathListings = {
				
				"$..children[#].type",
				"$..children[#].props",
				"$..children[#].props.title"
				
		};
		return jsonPathListings;
	}
	
	@Override
	public String[] getOnBoardForWelComeJsonPaths() {
        String[] jsonPathListings = {
				
				"$..children[#].type",
				"$..children[#].props",
				"$..children[#].props.title",
				"$..children[#].props.image",
				"$..children[#].props.image.src",
				"$..children[#].props.image.width",
				"$..children[#].props.image.height"
				
		};
		return jsonPathListings;
	}

	@Override
	public String[] getOnBoardCustomContentJsonPaths() {
         String[] jsonPathListings = {
				
				"$..children[#].type",
				"$..children[#].props",
				"$..children[#].props.baseColor",
				"$..children[#].props.image",
				"$..children[#].props.image.src",
				"$..children[#].props.image.width",
				"$..children[#].props.image.height"
				
		};
		return jsonPathListings;
	}

	@Override
	public String[] getOnBoardCustomInfoJsonPaths() {
		 String[] jsonPathListings = {
					
					"$..children[#].type",
					"$..children[#].props",
					"$..children[#].props.baseColor",
					"$..children[#].props.image",
					"$..children[#].props.image.src",
					"$..children[#].props.image.width",
					"$..children[#].props.image.height"
					
			};
			return jsonPathListings;
	}

	@Override
	public String[] getPersonalizedTopicsRackJsonPaths() {
		 String[] jsonPathListings = {
					
					"$..children[#].type",
					"$..children[#].props",
					"$..children[#].props.topics",
					
			};
			return jsonPathListings;
	}

	@Override
	public String[] getOnBoardEditProfileJsonPaths() {
		String[] jsonPathListings = {
				
				"$..children[#].type",
				"$..children[#].props",
				"$..children[#].props.bioHint",
				"$..children[#].props.nameHint",
				"$..children[#].props.submitButtonText",
				
		};
		return jsonPathListings;
	}

	@Override
	public String[] getOnBoardCustomFooterJsonPaths() {
		 String[] jsonPathListings = {
					
					"$..children[#].type",
					"$..children[#].props",
					"$..children[#].props.baseColor",
					"$..children[#].props.image",
					"$..children[#].props.image.src",
					"$..children[#].props.image.width",
					"$..children[#].props.image.height"
					
			};
			return jsonPathListings;
	}

	@Override
	public String[] getComponentsForOnboardWelcome() {
		String[] jsonPathListings = {	
				 
				"ONBOARD"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForOnboardWelBack() {
		String[] jsonPathListings = {	
				 
				"ONBOARD"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForOnboardFashion() {
		String[] jsonPathListings = {	
				 
				"ONBOARD"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForOnboardCouponReferral() {
		String[] jsonPathListings = {	
				 
				"TITLE_TEXT",
				"DESCRIPTION_TEXT",
				"ONBOARD_CUSTOM_CONTENT",
				"FOOTER_SHARE"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForOnboardGuestUser() {
		String[] jsonPathListings = {	
				 
				"TITLE_TEXT",
				"DESCRIPTION_TEXT",
				"ONBOARD_CUSTOM_CONTENT",
				"FOOTER_LINKS"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForOnboardCoupon() {
		String[] jsonPathListings = {	
				 
				"TITLE_TEXT",
				"DESCRIPTION_TEXT",
				"ONBOARD_CUSTOM_CONTENT",
				"FOOTER_LINKS"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForOnboardCollections() {
		String[] jsonPathListings = {	
				 
				"ONBOARD_CUSTOM_INFO",
				"MOSAIC",
				"TITLE_TEXT",
				"CREATOR_TITLE",
				"ONBOARD_CUSTOM_FOOTER"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForOnboardTopics() {
		String[] jsonPathListings = {	
				 
				"TITLE_TEXT",
				"DESCRIPTION_TEXT",
				"PERSONALIZED_TOPICS_RACK",
				"FOOTER_LINKS"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForOnboardEditProfile() {
		String[] jsonPathListings = {	
				 
				"TITLE_TEXT",
				"DESCRIPTION_TEXT",
				"ONBOARD_EDIT_PROFILE"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForOnboardStyleForumQuestion() {
		String[] jsonPathListings = {	
				 
				"ONBOARD_CUSTOM_INFO",
				"QUESTION_TEXT",
				"PRODUCT_SINGLE",
				"ANSWER_SUMMARY",
				"CREATOR_TITLE",
				"ONBOARD_CUSTOM_FOOTER"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForOnboardReturnExchange() {
		String[] jsonPathListings = {	
				 
				"TITLE_TEXT",
				"DESCRIPTION_TEXT",
				"IMAGE_SINGLE",
				"FOOTER_LINKS"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForOnboardDelivery() {
		String[] jsonPathListings = {	
				 
				"TITLE_TEXT",
				"DESCRIPTION_TEXT",
				"IMAGE_SINGLE",
				"FOOTER_LINKS"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForOnboardPayment() {
		String[] jsonPathListings = {	
				 
				"TITLE_TEXT",
				"DESCRIPTION_TEXT",
				"IMAGE_SINGLE",
				"FOOTER_LINKS"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForAnswerCard(String jsonResponse) {
		String[] jsonPathListings = {	
				 
				"HEADER_CONTEXT",
				"FOOTER_FOLLOW_ANSWER_SHARE",
				"ANSWER_SUMMARY"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForArticleCard(String jsonResponse) {
		String[] jsonPathListings = {	
				 
				"HEADER_CONTEXT",
				"FOOTER_LIKE_SHARE"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForBannerCard(String jsonResponse) {
		String[] jsonPathListings = {	
				 
				"BANNER"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForcarouselBannerCard(String jsonResponse) {
		String[] jsonPathListings = {	
				 
				"CAROUSEL_BANNER"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForCollectionsBannerCard(String jsonResponse) {
		String[] jsonPathListings = {	
				 
				"HEADER_CONTEXT",
				"MOSAIC",
				"FOOTER_LIKE_SHARE"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForInstaCardsProductCard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForMerchandisingCard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForOfferCard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForPollCard(String jsonResponse) {
		String[] jsonPathListings = {	
				 
				"HEADER_CONTEXT",
				"FOOTER_COMMENT_SHARE"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForPostShotCard(String jsonResponse) {
		String[] jsonPathListings = {	
				 
				"HEADER_CONTEXT",
				"IMAGE_SINGLE",
				"CREATOR_TITLE",
				"FOOTER_LIKE_SHARE"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForPostStyleUpdateCard(String jsonResponse) {
		String[] jsonPathListings = {	
				 
				"HEADER_CONTEXT",
				"CREATOR_TITLE",
				"FOOTER_LIKE_SHARE"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForProductStoryCard(String jsonResponse) {
		String[] jsonPathListings = {	
				 
				"HEADER_CONTEXT",
				"IMAGE_SINGLE",
				"FOOTER_LIKE_SHARE"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForProductCard(String jsonResponse) {
		String[] jsonPathListings = {	
				 
				"HEADER_CONTEXT",
				"IMAGE_SINGLE",
				"FOOTER_LIKE_SHARE"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForProductV2Card(String jsonResponse) {
		String[] jsonPathListings = {	
				 
				"HEADER_CONTEXT",
				"PRODUCT",
				"FOOTER_LIKE_SHARE"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForQuestionCard(String jsonResponse) {
		String[] jsonPathListings = {	
				 
				"HEADER_CONTEXT",
				"FOOTER_FOLLOW_ANSWER_SHARE"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForSlideshowCard(String jsonResponse) {
		String[] jsonPathListings = {	
				 
				"SLIDESHOW"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForSplitBannerCard(String jsonResponse) {
		String[] jsonPathListings = {	
				 
				"SPLIT_BANNER"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForStyletipCard(String jsonResponse) {
		String[] jsonPathListings = {	
				 
				"HEADER_CONTEXT",
				"FOOTER_LIKE_SHARE"
		};
		return jsonPathListings;
	}

	@Override
	public String[] getComponentsForSuggestionsCard(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForVideoCard(String jsonResponse) {

		String publisherTagType = JsonPath.read(jsonResponse, "$.props.meta.publisherTag");
		
		if(publisherTagType.contains("sponsored")){
			
			String[] jsonPathListings = {	
					 
					CardComponents.VIDEO.componentName
			};
			return jsonPathListings;
			
		}else{
			

			String[] jsonPathListings = {	
					 
					CardComponents.VIDEO.componentName,
					CardComponents.FOOTER_LIKE_SHARE.componentName
			};
			return jsonPathListings;
			
		}
	}

	@Override
	public String[] getProductV2JsonPaths() {
		// TODO Auto-generated method stub
				return null;
	}

	@Override
	public String[] getSlideShowJsonPaths() {
		String[] jsonPathListings = {	
				 
				"$..children[#].type",
				"$..children[#].props",
				"$..children[#].props.slides[*]..src",
				"$..children[#].props.slides[*]..height",
				"$..children[#].props.slides[*]..width",
				"$..children[#].props.slides[*]..provider",
				"$..children[#].props.slides[*]..alt",
				"$..children[#].props.slides[*]..alt-h1",
				"$..children[#].props.slides[*]..id",
				"$..children[#].props.slides[*]..link"
		};
		 
		 return jsonPathListings;
	}

	@Override
	public String[] getCarouselCardJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String[] getComponentsForCarousel(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String[] getComponentsForOnBoardCarousel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getOnBoardCustomFooterJsonPathsForCarousal() {
		
		 String[] jsonPathListings = {
					
					"$..children[#].type",
					"$..children[#].props",
					"$..children[#].props.links[*]",
					"$..children[#].props.links[*].link",
					"$..children[#].props.links[*].title",
					"$..children[#].props.links[*].type",
					
			};
			return jsonPathListings;
	}
	
	@Override
	public String[] getComponentsForSponsoredVideoCard(){
		
		String[] jsonPathListings = {	
				 
				CardComponents.VIDEO.componentName
		};
		return jsonPathListings;
		
	}
}
