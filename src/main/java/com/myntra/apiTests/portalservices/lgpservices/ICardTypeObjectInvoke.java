package com.myntra.apiTests.portalservices.lgpservices;

import org.codehaus.jettison.json.JSONException;

import com.myntra.lordoftherings.LgpArticleCardPOJO;
import com.myntra.lordoftherings.LgpBannerCardPOJO;
import com.myntra.lordoftherings.LgpCollectionsCardPOJO;
import com.myntra.lordoftherings.LgpPostoShotPOJO;
import com.myntra.lordoftherings.LgpPostoStyleUpdatePOJO;
import com.myntra.lordoftherings.LgpProductCardPOJO;
import com.myntra.lordoftherings.LgpSplitBannerPOJO;
import com.myntra.lordoftherings.LgpVideoPOJO;

public interface ICardTypeObjectInvoke {
	
	LgpArticleCardPOJO articleObjectRegister() throws JSONException;
	LgpBannerCardPOJO bannerObjectRegister();
	LgpCollectionsCardPOJO collectionsObjectRegister() throws JSONException;
	LgpProductCardPOJO productObjectRegister() throws JSONException;
	LgpPostoStyleUpdatePOJO postoStyleUpdateRegister() throws JSONException;
	LgpPostoShotPOJO postoShotRegister() throws JSONException;
	LgpSplitBannerPOJO splitBannerRegister() throws JSONException;
	LgpVideoPOJO videoRegister() throws JSONException;
}
