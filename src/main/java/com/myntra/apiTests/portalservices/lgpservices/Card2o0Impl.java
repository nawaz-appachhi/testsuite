package com.myntra.apiTests.portalservices.lgpservices;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.CardComponents;
import com.myntra.lordoftherings.ILgpJsonKeys;

import java.util.HashMap;

public class Card2o0Impl implements ILgpJsonKeys {


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
	public HashMap<String,String> getHotListCardJsonPaths() {
		// TODO Auto-generated method stub
		HashMap<String, String> jsonPathListings = new HashMap<>();
		jsonPathListings.put("type","$.cards[0].props.meta.og:name");
		jsonPathListings.put("link-men","$.cards[0].children[0].props.splits[0].link");
		jsonPathListings.put("link-women","$.cards[0].children[0].props.splits[1].link");
		jsonPathListings.put("link-kid","$.cards[0].children[0].props.splits[2].link");
		return jsonPathListings;
	}

	@Override
	public HashMap<String, String> getStreamCardJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, String> getStreamSlideshowCardJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getHeaderContextJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getMosaicJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getCreatorTitleJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getFeedbackViewlikeJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getProductRackJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getFooterLikeShareJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentCardJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDescriptionTextJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getTitleTextJsonPaths() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getBannerJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getSplitBannerJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getVideoJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getCarouselJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentCardHotListJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getFooterShareJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getFooterLinksJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getProductSingleJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getAnswerSummaryJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String[] getOnBoardCustomContentJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getOnBoardCustomInfoJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getPersonalizedTopicsRackJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getOnBoardEditProfileJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getOnBoardCustomFooterJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForOnboardWelcome() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForOnboardWelBack() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForOnboardFashion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForOnboardCouponReferral() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForOnboardGuestUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForOnboardCoupon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForOnboardCollections() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForOnboardTopics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForOnboardEditProfile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForOnboardStyleForumQuestion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getOnBoardForWelBackJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getOnBoardForWelComeJsonPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForOnboardReturnExchange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForOnboardDelivery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForOnboardPayment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForAnswerCard(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForArticleCard(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForBannerCard(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForcarouselBannerCard(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForCollectionsBannerCard(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForPostShotCard(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForPostStyleUpdateCard(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForProductStoryCard(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForProductCard(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForProductV2Card(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForQuestionCard(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForSlideshowCard(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForSplitBannerCard(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getComponentsForStyletipCard(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
