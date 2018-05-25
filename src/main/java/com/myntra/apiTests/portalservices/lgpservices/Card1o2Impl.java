package com.myntra.apiTests.portalservices.lgpservices;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.CardComponents;
import com.myntra.lordoftherings.ILgpJsonKeys;

import java.util.HashMap;

public class Card1o2Impl implements ILgpJsonKeys {


	@Override
	public String[] getArticleCardJsonPaths() {
		
		 String[] jsonPathListings = {	
				 
				 "$.count",
				 "$.data[*].object.id[0]",
				 "$.data[*].object.type[0]",
				 "$.data[*].object.title[0]",
				 "$.data[*].object.url[0]",
				 "$.data[*].object.image.url[0]",
				 "$.data[*].object.app.id[0]"
				 
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
	public HashMap<String, String> getHotListCardJsonPaths() {
		// TODO Auto-generated method stub
		return null;
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
