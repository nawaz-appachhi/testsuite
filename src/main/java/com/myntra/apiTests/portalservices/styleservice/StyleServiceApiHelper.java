package com.myntra.apiTests.portalservices.styleservice;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.common.Constants.Headers;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.styleservice.domain.response.StyleIndexResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Constants.Headers;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


/**
 * @author shankara.c
 *
 */
public class StyleServiceApiHelper extends CommonUtils
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(StyleServiceApiHelper.class);
	static APIUtilities apiUtil= new APIUtilities();

	/**
	 * This method is used to invoke StyleService getStyleDataForSingleStyleId API
	 * 
	 * @param styleId
	 * @return RequestGenerator
	 */
	public RequestGenerator getStyleDataForSingleStyleId(String styleId)
	{
		MyntraService getStyleDataForSingleStyleIdService = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.GETSTYLEDATAGET, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		getStyleDataForSingleStyleIdService.URL = utilities.prepareparameterizedURL(getStyleDataForSingleStyleIdService.URL, styleId );
		
		System.out.println("\nPrinting StyleService getStyleDataForSingleStyleId API URL : "+getStyleDataForSingleStyleIdService.URL);
		log.info("\nPrinting StyleService getStyleDataForSingleStyleId API URL : "+getStyleDataForSingleStyleIdService.URL);
		
		System.out.println("Printing StyleService getStyleDataForSingleStyleId API Payload : \n\n"+getStyleDataForSingleStyleIdService.Payload+"\n");
		log.info("Printing StyleService getStyleDataForSingleStyleId API Payload : \n\n"+getStyleDataForSingleStyleIdService.Payload+"\n");
		
		return new RequestGenerator(getStyleDataForSingleStyleIdService);
	}
	
	/**
	 * This TestCase is used to invoke StyleService getStyleDataForListOfStyleId API
	 * 
	 * @param styleIds
	 * @return RequestGenerator
	 */
	public RequestGenerator getStyleDataForListOfStyleId(String styleIds)
	{
		MyntraService getStyleDataForListOfStyleIdService = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.GETSTYLEDATAPOST, init.Configurations);
		String payload = Arrays.toString(styleIds.split(","));
		getStyleDataForListOfStyleIdService.Payload = payload;
		
		System.out.println("\nPrinting StyleService getStyleDataForListOfStyleId API URL : "+getStyleDataForListOfStyleIdService.URL);
		log.info("\nPrinting StyleService getStyleDataForListOfStyleId API URL : "+getStyleDataForListOfStyleIdService.URL);
		
		System.out.println("\nPrinting StyleService getStyleDataForListOfStyleId API Payload : \n\n"+getStyleDataForListOfStyleIdService.Payload+"\n");
		log.info("\nPrinting StyleService getStyleDataForListOfStyleId API Payload : \n\n"+getStyleDataForListOfStyleIdService.Payload+"\n");
		
		return new RequestGenerator(getStyleDataForListOfStyleIdService);
	}
	
	/**
	 * This method is used to invoke StyleService getPdpDataForSingleStyleId API
	 * 
	 * @param styleId
	 * @return RequestGenerator
	 */
	public RequestGenerator getPdpDataForSingleStyleId(String styleId)
	{
		MyntraService getPdpDataForSingleStyleIdService = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.GETPDPDATAGET, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		getPdpDataForSingleStyleIdService.URL = utilities.prepareparameterizedURL(getPdpDataForSingleStyleIdService.URL, styleId );
		
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleId API URL : "+getPdpDataForSingleStyleIdService.URL);
		log.info("\nPrinting StyleService getPdpDataForSingleStyleId API URL : "+getPdpDataForSingleStyleIdService.URL);
		
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleId API Payload : \n\n"+getPdpDataForSingleStyleIdService.Payload+"\n");
		log.info("\nPrinting StyleService getPdpDataForSingleStyleId API Payload : \n\n"+getPdpDataForSingleStyleIdService.Payload+"\n");
		
		return new RequestGenerator(getPdpDataForSingleStyleIdService);
	}
	
	/**
	 * This method is used to invoke StyleService getPdpDataForListOfStyleId API
	 * 
	 * @param styleIds
	 * @return RequestGenerator
	 */
	public RequestGenerator getPdpDataForListOfStyleId(String styleIds)
	{
		MyntraService getPdpDataForListOfStyleIdService = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.GETPDPDATAPOST, init.Configurations);
		String payload = Arrays.toString(styleIds.split(","));
		getPdpDataForListOfStyleIdService.Payload = payload;
		
		System.out.println("\nPrinting StyleService getPdpDataForListOfStyleId API URL : "+getPdpDataForListOfStyleIdService.URL);
		log.info("\nPrinting StyleService getPdpDataForListOfStyleId API URL : "+getPdpDataForListOfStyleIdService.URL);
		
		System.out.println("\nPrinting StyleService getPdpDataForListOfStyleId API Payload : \n\n"+getPdpDataForListOfStyleIdService.Payload+"\n");
		log.info("\nPrinting StyleService getPdpDataForListOfStyleId API Payload : \n\n"+getPdpDataForListOfStyleIdService.Payload+"\n");
		
		return new RequestGenerator(getPdpDataForListOfStyleIdService);
	}
	
	/**
	 * This method is used to invoke StyleService getPdpDataForSingleStyleIdWithImage API
	 * 
	 * @param styleId
	 * @param fetchResolutions
	 * @return RequestGenerator
	 */
	public RequestGenerator getPdpDataForSingleStyleIdWithImage(String styleId, String fetchResolutions)
	{
		
		MyntraService getPdpDataForSingleStyleIdWithImageService = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.GETPDPDATAFORSINGLESTYLEWITHIMAGE,
				init.Configurations);
		getPdpDataForSingleStyleIdWithImageService.URL = new APIUtilities().prepareparameterizedURL(getPdpDataForSingleStyleIdWithImageService.URL, 
				new String[]{ styleId, fetchResolutions });
		
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API URL : "+getPdpDataForSingleStyleIdWithImageService.URL);
		log.info("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API URL : "+getPdpDataForSingleStyleIdWithImageService.URL);
		
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API Payload : \n\n"+getPdpDataForSingleStyleIdWithImageService.Payload+"\n");
		log.info("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API Payload : \n\n"+getPdpDataForSingleStyleIdWithImageService.Payload+"\n");
		
		return new RequestGenerator(getPdpDataForSingleStyleIdWithImageService);
	}
	
	/**
	 * This method is used to invoke StyleService getPdpDataBySkuId API
	 * 
	 * @param skuId
	 * @return RequestGenerator
	 */
	public RequestGenerator getPdpDataBySingleSkuId(String skuId)
	{
		MyntraService getPdpDataBySkuIdService = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.GETPDPDATABYSKUID, init.Configurations);
		getPdpDataBySkuIdService.URL = new APIUtilities().prepareparameterizedURL(getPdpDataBySkuIdService.URL, skuId);
		
		System.out.println("\nPrinting StyleService getPdpDataBySkuId API URL : "+getPdpDataBySkuIdService.URL);
		log.info("\nPrinting StyleService getPdpDataBySkuId API URL : "+getPdpDataBySkuIdService.URL);
		
		System.out.println("\nPrinting StyleService getPdpDataBySkuId API Payload : \n\n"+getPdpDataBySkuIdService.Payload+"\n");
		log.info("\nPrinting StyleService getPdpDataBySkuId API Payload : \n\n"+getPdpDataBySkuIdService.Payload+"\n");
		
		return new RequestGenerator(getPdpDataBySkuIdService);
	}
	
	/**
	 * This method is used to invoke StyleService doStyleIndexForSingleStyleId API
	 * 
	 * @param styleId
	 * @return RequestGenerator
	 */
	public RequestGenerator doStyleIndexForSingleStyleId(String styleId)
	{
		MyntraService doStyleIndexForSingleStyleIdService = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.DOSTYLEINDEXBYSTYLEID, init.Configurations);
		doStyleIndexForSingleStyleIdService.URL = new APIUtilities().prepareparameterizedURL(doStyleIndexForSingleStyleIdService.URL, styleId);
		
		System.out.println("\nPrinting StyleService doStyleIndexForSingleStyleId API URL : "+doStyleIndexForSingleStyleIdService.URL);
		log.info("\nPrinting StyleService doStyleIndexForSingleStyleId API URL : "+doStyleIndexForSingleStyleIdService.URL);
		
		System.out.println("\nPrinting StyleService doStyleIndexForSingleStyleId API Payload : \n\n"+doStyleIndexForSingleStyleIdService.Payload+"\n");
		log.info("\nPrinting StyleService doStyleIndexForSingleStyleId API Payload : \n\n"+doStyleIndexForSingleStyleIdService.Payload+"\n");
		
		return new RequestGenerator(doStyleIndexForSingleStyleIdService);
	}
	
	/**
	 * This method is used to invoke StyleService doStyleIndexForListOfStyleId API 
	 * 
	 * @param styleIds
	 * @return RequestGenerator
	 */
	public RequestGenerator doStyleIndexForListOfStyleId(String styleIds)
	{
		MyntraService doStyleIndexForListOfStyleIdService = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.DOSTYLEINDEXPOST, init.Configurations);
		String payload = Arrays.toString(styleIds.split(","));
		doStyleIndexForListOfStyleIdService.Payload = payload;
		
		System.out.println("\nPrinting StyleService doStyleIndexForListOfStyleId API URL : "+doStyleIndexForListOfStyleIdService.URL);
		log.info("\nPrinting StyleService doStyleIndexForListOfStyleId API URL : "+doStyleIndexForListOfStyleIdService.URL);
		
		System.out.println("\nPrinting StyleService doStyleIndexForListOfStyleId API Payload : \n\n"+doStyleIndexForListOfStyleIdService.Payload+"\n");
		log.info("\nPrinting StyleService doStyleIndexForListOfStyleId API Payload : \n\n"+doStyleIndexForListOfStyleIdService.Payload+"\n");
		
		return new RequestGenerator(doStyleIndexForListOfStyleIdService);
	}
	
	/**
	 * This method is used to invoke StyleService doStyleIndexForSingleSkuId API
	 * 
	 * @param skuId
	 * @return RequestGenerator
	 */
	public RequestGenerator doStyleIndexForSingleSkuId(String skuId)
	{
		MyntraService doStyleIndexForSingleSkuIdService = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.DOSTYLEINDEXSKUS, init.Configurations);
		doStyleIndexForSingleSkuIdService.URL = new APIUtilities().prepareparameterizedURL(doStyleIndexForSingleSkuIdService.URL, skuId);
		
		System.out.println("\nPrinting StyleService doStyleIndexForSingleSkuId API URL : "+doStyleIndexForSingleSkuIdService.URL);
		log.info("\nPrinting StyleService doStyleIndexForSingleSkuId API URL : "+doStyleIndexForSingleSkuIdService.URL);
		
		System.out.println("\nPrinting StyleService doStyleIndexForSingleSkuId API Payload : \n\n"+doStyleIndexForSingleSkuIdService.Payload+"\n");
		log.info("\nPrinting StyleService doStyleIndexForSingleSkuId API Payload : \n\n"+doStyleIndexForSingleSkuIdService.Payload+"\n");
		
		return new RequestGenerator(doStyleIndexForSingleSkuIdService);
	}
	
	/**
	 * This method is used to invoke StyleService doStyleIndexForListOfSkuId API
	 * 
	 * @param skuIds
	 * @return RequestGenerator
	 */
	public RequestGenerator doStyleIndexForListOfSkuId(String skuIds)
	{
		MyntraService doStyleIndexForListOfSkuIdService = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.DOSTYLEINDEXSKUSPOST, init.Configurations);
		String payload = Arrays.toString(skuIds.split(","));
		doStyleIndexForListOfSkuIdService.Payload = payload;
		
		System.out.println("\nPrinting StyleService doStyleIndexForListOfSkuId API URL : "+doStyleIndexForListOfSkuIdService.URL);
		log.info("\nPrinting StyleService doStyleIndexForListOfSkuId API URL : "+doStyleIndexForListOfSkuIdService.URL);
		
		System.out.println("\nPrinting StyleService doStyleIndexForListOfSkuId API Payload : \n\n"+doStyleIndexForListOfSkuIdService.Payload+"\n");
		log.info("\nPrinting StyleService doStyleIndexForListOfSkuId API Payload : \n\n"+doStyleIndexForListOfSkuIdService.Payload+"\n");
		
		return new RequestGenerator(doStyleIndexForListOfSkuIdService);
	}
	
	/**
	 * This method is used to invoke StyleService doInvalidateStyles API
	 * 
	 * @param styleIds
	 * @return RequestGenerator
	 */
	public RequestGenerator doInvalidateStyles(String styleIds)
	{
		MyntraService doInvalidateStylesService = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.DOINVALIDATESTYLES, init.Configurations);
		String payload = Arrays.toString(styleIds.split(","));
		doInvalidateStylesService.Payload = payload;
		
		System.out.println("\nPrinting StyleService doInvalidateStyles API URL : "+doInvalidateStylesService.URL);
		log.info("\nPrinting StyleService doInvalidateStyles API URL : "+doInvalidateStylesService.URL);
		
		System.out.println("\nPrinting StyleService doInvalidateStyles API Payload : \n\n"+doInvalidateStylesService.Payload+"\n");
		log.info("\nPrinting StyleService doInvalidateStyles API Payload : \n\n"+doInvalidateStylesService.Payload+"\n");
		
		return new RequestGenerator(doInvalidateStylesService);
	}
	
	public RequestGenerator getStyleDataForSingleStyleId1(String styleId)
	{
		MyntraService getStyleDataForSingleStyleIdService = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.GETSTYLEDATAGET, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		getStyleDataForSingleStyleIdService.URL = utilities.prepareparameterizedURL(getStyleDataForSingleStyleIdService.URL, styleId );
		
		System.out.println("\nPrinting StyleService getStyleDataForSingleStyleId API URL : "+getStyleDataForSingleStyleIdService.URL);
		log.info("\nPrinting StyleService getStyleDataForSingleStyleId API URL : "+getStyleDataForSingleStyleIdService.URL);
		
		System.out.println("Printing StyleService getStyleDataForSingleStyleId API Payload : \n\n"+getStyleDataForSingleStyleIdService.Payload+"\n");
		log.info("Printing StyleService getStyleDataForSingleStyleId API Payload : \n\n"+getStyleDataForSingleStyleIdService.Payload+"\n");
		
		return new RequestGenerator(getStyleDataForSingleStyleIdService);
	}


    /**
     * Style Reindex for Particular Style
     * @param styleIDs
     * @throws IOException
     */
    public void styleReindexForStyleIDs(String[] styleIDs) throws IOException {
        Svc service = HttpExecutorService.executeHttpService(Constants.STYLE_PATH.STYLE_REINDEX, null, SERVICE_TYPE.STYLE_SVC.toString(), HTTPMethods.POST, null, null);
        StyleIndexResponse styleEntry = (StyleIndexResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new StyleIndexResponse());
        Assert.assertEquals(styleEntry.getStatus().getStatusType(), StatusResponse.Type.SUCCESS, "Style ReIndex Failure");
    }
    
    /**
	 * This method is used to invoke StyleService getStyleDataForSingleStyleId API
	 * 
	 * @param skucode to styleId
	 * @return RequestGenerator
	 */
	public RequestGenerator getSkuCodetoStyleId(String skuCode)
	{
		MyntraService getSkuCodeToStyleId = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.SKUCODETOSTYLEID, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		getSkuCodeToStyleId.URL = utilities.prepareparameterizedURL(getSkuCodeToStyleId.URL, skuCode);
		
		System.out.println("\nPrinting StyleService getStyleDataForSingleStyleId API URL : "+getSkuCodeToStyleId.URL);
		log.info("\nPrinting StyleService getStyleDataForSingleStyleId API URL : "+getSkuCodeToStyleId.URL);
		
		
		
		return new RequestGenerator(getSkuCodeToStyleId);
	}
	
	public RequestGenerator cmsPreOrder(String query)
	{
	 
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CATALOGSERVICES, APINAME.CMSPREORDER, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, query);
		System.out.println("SERVICE URL-->"+service.URL);
		HashMap<String,String> getParam = new HashMap<String,String>();
		getParam.put("Authorization",
				"Basic WVhCcFlXUnRhVzQ2YlRGOnVkSEpoVWpCamEyVjBNVE1oSXc9PQ==");
		
		
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		//System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	 
	 return req; 
	}
	
	/**
     * styleFullReindex
     *
     * @throws Exception
     */
    public void styleFullReindex() throws UnsupportedEncodingException {
        Svc service = HttpExecutorService.executeHttpService(Constants.STYLE_PATH.STYLE_FULL_REINDEX, null, SERVICE_TYPE.STYLE_SVC.toString(),
                HTTPMethods.GET, null, Headers.getStyleHeaderXML());
        log.info(service);
    }

    /**
     * styleReindexForStyleIDs
     *
     * @param styleId
     * @throws IOException
     */
    public void styleReindexForStyleIDs(String styleId) throws IOException {
        Svc service = HttpExecutorService.executeHttpService(Constants.STYLE_PATH.STYLE_REINDEX, new String[]{styleId}, SERVICE_TYPE.STYLE_SVC.toString(), HTTPMethods.GET, null, Headers.getOMSHeaderJSON());
        StyleIndexResponse styleEntry = (StyleIndexResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new StyleIndexResponse());
        Assert.assertEquals(styleEntry.getStatus().getStatusType(), StatusResponse.Type.SUCCESS, "Style ReIndex Failure");
    }


    /**
     * @param styleIds
     * @throws IOException
     */
    public void styleReindexForStyleIDsPost(int[] styleIds) throws IOException {
        /*String payload = "[";
        for (int styleId : styleIds)
            payload += styleId + ",";
        Svc service = HttpExecutorService.executeHttpService(Constants.STYLE_PATH.STYLE_REINDEX, null, SERVICE_TYPE.STYLE_SVC.toString(), HTTPMethods.POST, payload.substring(0, payload.length() - 1) + "]", Headers.getOMSHeaderJSON());
        StyleIndexResponse styleEntry = (StyleIndexResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new StyleIndexResponse());
        Assert.assertEquals(styleEntry.getStatus().getStatusType(), StatusResponse.Type.SUCCESS, "Style ReIndex Failure");
        */
        //Bolt Reindexing Need to add assert once service will start working
        String payload = "[";
        for (int styleId : styleIds)
            payload += styleId + ",";
        Svc service = HttpExecutorService.executeHttpService(Constants.STYLE_PATH.STYLE_REINDEX_BOLT, null, SERVICE_TYPE.BOLT_SVC.toString(), HTTPMethods.POST, payload.substring(0, payload.length() - 1) + "]", Headers.getBoltHeader());
        //StyleIndexResponse styleEntry = (StyleIndexResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new StyleIndexResponse());
        //Assert.assertEquals(service.getResponseStatus(), 200, "Style ReIndex Failure");
    }

	public static <T> String getInnerQueryElements(List<T> inputList) {
		if (inputList != null && !inputList.isEmpty()) {
			Optional<String> optional = Optional.of(inputList.stream().filter(Objects::nonNull).map(String::valueOf)
					.collect(Collectors.joining("','", "'", "'")));
			if (optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Double> getPriceBySkuId(List<String> skuIds) {
		List<Map<String, Object>> resultSet = null;
		Map<String, Double> skuToPriceMap = new LinkedHashMap<String, Double>();
		String skuIdInner = getInnerQueryElements(skuIds);
		resultSet = DBUtilities.exSelectQuery(
				"select sku_id,price from mk_product_style LEFT JOIN mk_styles_options_skus_mapping ON mk_product_style.id=mk_styles_options_skus_mapping.style_id WHERE `sku_id` IN ("
						+ skuIdInner + ");",
				"myntra");
		for (Map<String, Object> hmap : resultSet) {
			skuToPriceMap.put(String.valueOf(hmap.get("sku_id")), (Double) hmap.get("price"));
		}
		return skuToPriceMap;
	}
}
