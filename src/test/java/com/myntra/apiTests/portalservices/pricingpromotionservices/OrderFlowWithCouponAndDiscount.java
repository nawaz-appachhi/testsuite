package com.myntra.apiTests.portalservices.pricingpromotionservices;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.dataproviders.OrderFlowWithCouponAndDiscountDP;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.portalservices.OrderFlowWIthDiscountHelper.OrderFlowWIthDiscountServiceHelper;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutTestsHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.MyntraListenerV2;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.oms.client.entry.OrderEntry;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;



public class OrderFlowWithCouponAndDiscount extends OrderFlowWithCouponAndDiscountDP {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(OrderFlowWithCouponAndDiscount.class);
	static OrderFlowWIthDiscountServiceHelper OrderDiscounhelper = new OrderFlowWIthDiscountServiceHelper();
	static CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();
	static BountyServiceHelper bountyHelper = new  BountyServiceHelper();
	static OMSServiceHelper OMSHelper = new OMSServiceHelper();
//	static ListPage listPage = new ListPage(init);
	
//	ListPage listPage;




	APIUtilities apiUtil = new APIUtilities();
	public long currentTime = System.currentTimeMillis()/1000;
	String startTime = "" , endTime = "";
	String xID,sXid;
	MyntraListenerV2 myntListner = new MyntraListenerV2();
	int reval;
	Double d1,d2;
	boolean disc= false;
	
	
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression"}, dataProvider = "OrderFlowDiscountDP", dataProviderClass=OrderFlowWithCouponAndDiscountDP.class)
	public void orderFlowWithDiscount(String StartDate , String EndDate,String percentage,String userName ,String password,String quantity,String operation) throws IOException, InterruptedException, JAXBException
	{
		Integer percentage_Int = Integer.parseInt(percentage);
		//Get random One StyleId 
//		String StyleId = OrderDiscounhelper.getOneStyleID();
		
		List<Integer> styleIdList = OrderDiscounhelper.getStyleIdsUsingSearchAPI();
		List<String> styeldetails = OrderDiscounhelper.getValidSkuidUsingStyleAPI (styleIdList);
		String skuId = styeldetails.get(0);
		System.out.println("Sku id for create disount " + skuId);
		String styleId = styeldetails.get(1);
		System.out.println("Style  id for create disount " + styleId);

		
		RequestGenerator getstyleData = OrderDiscounhelper.invokeGetStyleData(styleId);
		String GetstledatResponse = getstyleData.respvalidate.returnresponseasstring().toString();
		System.out.println("Get Data Response------->>>" + GetstledatResponse);
		String oldPrice =JsonPath.read(GetstledatResponse,"$.data..price").toString().replace("[", "").replace("]", "").trim();
		System.out.println("OLD PRICE FOR STYLE ID - \n"+ oldPrice);
		String oldDiscountedPrice =JsonPath.read(GetstledatResponse,"$.data..discountedPrice").toString().replace("[", "").replace("]", "").trim();
		System.out.println("OLD PRICE FOR STYLE ID - \n"+ oldDiscountedPrice);
		
		Double oldPrice_Double = Double.parseDouble(oldPrice);
		Integer oldPrice_Int = oldPrice_Double.intValue();
		Double PriceDiscounted  = (oldPrice_Double*percentage_Int)/100;
		System.out.println("caluuuhdcbhsjdv--- > > > \n" + PriceDiscounted);
		

		
		Double calulation = oldPrice_Int - PriceDiscounted;
		Double calulation_Double =Math.rint(calulation);
		Integer Calculation_int = calulation_Double.intValue();
		System.out.println("Calulated Dicounted price with discount percentage \n" + Calculation_int);
//		System.out.println("Calulated Dicounted price INT  \n" + Cal);
//		System.out.println("Calulated Dicounted price Double  \n" + calulation_int);

		
	

		//Created FlatDiscount
		RequestGenerator createdDiscount= OrderDiscounhelper.createFlatDiscount(StartDate,EndDate,percentage,styleId);
		System.out.println("Percentage given \n" + percentage);
		String createdDiscountResponse = createdDiscount.respvalidate.returnresponseasstring().toString();
		System.out.println("Get Discount Response------->>>" + createdDiscountResponse);
		
		//reindex style 
		
		RequestGenerator reindexStyle = OrderDiscounhelper.rindexonestleid(styleId);
		String reindexResponse = reindexStyle.respvalidate.returnresponseasstring().toString();
		String reindexMessage =JsonPath.read(reindexResponse,"$.status.statusMessage");
		System.out.println("Get Data Response------->>>" + reindexResponse);
		System.out.println("Get Message Response------->>>" + reindexMessage);

		//given 6 minutes for reflecting discount on style
		TimeUnit.MINUTES.sleep(6);
		
		// Again calling Getstyledata after creating discount
		RequestGenerator getstyleDataNew = OrderDiscounhelper.invokeGetStyleData(styleId);
		String GetstledatResponseNew = getstyleDataNew.respvalidate.returnresponseasstring().toString();
		System.out.println("Get style dat responce=  \n" +GetstledatResponseNew );
		String priceAfterDiscount =JsonPath.read(GetstledatResponseNew,"$.data..price").toString().replace("[", "").replace("]", "").trim();
		System.out.println("new PRICE FOR STYLE ID - \n"+ priceAfterDiscount);
		String discountedPriceAfterDiscount =JsonPath.read(GetstledatResponseNew,"$.data..discountedPrice").toString().replace("[", "").replace("]", "").trim();
		System.out.println("new Discounted PRICE FOR STYLE ID - \n"+ discountedPriceAfterDiscount);
		Double discountedPriceAfterDiscount_Double = Double.parseDouble(discountedPriceAfterDiscount);
		Integer discountedPriceAfterDiscount_int = discountedPriceAfterDiscount_Double.intValue();
		String discountPercentage =JsonPath.read(GetstledatResponseNew,"$.data..discountData..discountPercent").toString().replace("[", "").replace("]", "").trim();
        System.out.println("DIscount percentage given \n" +discountPercentage );
//        Double discountPercentage_Double = Double.parseDouble(discountPercentage);
//		Integer discountPercentage_Int = discountPercentage_Double.intValue();
		
		AssertJUnit.assertEquals(Calculation_int, discountedPriceAfterDiscount_int);
//		AssertJUnit.assertEquals(percentage_Int, discountPercentage_Int);
						//Get PDP details and verify
		RequestGenerator getstylePdpData = OrderDiscounhelper.invokeGetPdpStyleData(styleId);
		String GetstylePdpdatResponse = getstylePdpData.respvalidate.returnresponseasstring().toString();
		System.out.println("getStyle data reponse in PDP---- \n" + GetstylePdpdatResponse);
		System.out.println("Get PDP Data Response------->>>" + GetstylePdpdatResponse);
		String priceAfterDiscountInPdp =JsonPath.read(GetstylePdpdatResponse,"$.data..price").toString().replace("[", "").replace("]", "").trim();
		System.out.println("new PRICE FOR STYLE ID - \n"+ priceAfterDiscountInPdp);
		String discountedPriceAfterDiscountInPdp =JsonPath.read(GetstylePdpdatResponse,"$.data..discountedPrice").toString().replace("[", "").replace("]", "").trim();
		System.out.println("new Discounted PRICE FOR STYLE ID - \n"+ discountedPriceAfterDiscount);
		Double discountedPriceAfterDiscountInPdp_Double = Double.parseDouble(discountedPriceAfterDiscountInPdp);
		Integer discountedPriceAfterDiscountInPdp_int = discountedPriceAfterDiscountInPdp_Double.intValue();
		String discountPercentageInPdp =JsonPath.read(GetstylePdpdatResponse,"$.data..discountData..discountPercent").toString().replace("[", "").replace("]", "").trim();
        System.out.println("DIscount percentage given \n" +discountPercentageInPdp );
//		String skuId1 =JsonPath.read(GetstylePdpdatResponse,"$.data..styleOptions..skuId").toString().replace("[", "").replace("]", "").trim();

        Double discountPercentageInPdp_Double = Double.parseDouble(discountPercentageInPdp);
		Integer discountPercentageInPdp_Int = discountPercentageInPdp_Double.intValue();
		
		AssertJUnit.assertEquals(Calculation_int, discountedPriceAfterDiscountInPdp_int);
		AssertJUnit.assertEquals(percentage_Int, discountPercentageInPdp_Int);			
			
		//Clear cart 
			
			checkoutTestsHelper.clearCart(userName, password);
			RequestGenerator addToCart= 	checkoutTestsHelper.addItemToCart(userName, password, skuId, quantity, operation);
			String addToCartResponse = addToCart.respvalidate.returnresponseasstring().toString();
			RequestGenerator fetchAllCouponsReqGen = checkoutTestsHelper.fetchAllCoupons(userName, password);
			System.out.println("\nPrinting CheckoutService fetchAllCoupons API response :\n\n"+fetchAllCouponsReqGen.respvalidate.returnresponseasstring());
			RequestGenerator applyCouponReqGen = checkoutTestsHelper.applyCoupon(userName, password, fetchAllCouponsReqGen.respvalidate.NodeValue("data.coupon", false));			
			System.out.println("Apply coupn response===========>\n" + applyCouponReqGen.returnresponseasstring());

//			System.out.println("Get PDP Data Response------->>>" + addToCartResponse);
			RequestGenerator fetchcart =checkoutTestsHelper.operationFetchCart(userName, password);
			String fetchcartResponse = fetchcart.respvalidate.returnresponseasstring().toString();
			System.out.println("Fetch cart response ---  > > " + fetchcartResponse);
			String SubtotalPrice =JsonPath.read(fetchcartResponse,"$.data..subTotalPrice").toString().replace("[", "").replace("]", "").trim();
	        System.out.println("subtotal given \n" +SubtotalPrice );
			Integer SubtotalPrice_Int = Integer.parseInt(SubtotalPrice);

	        String vatcharges =JsonPath.read(fetchcartResponse,"$.data..vatCharge").toString().replace("[", "").replace("]", "").trim();
	        Double vatcharges_Double = Double.parseDouble(vatcharges);
			Integer vatcharges_Int = vatcharges_Double.intValue();
	        

	        System.out.println("vat Charges given \n" +vatcharges_Int );
	        String totalCouponDiscount =JsonPath.read(fetchcartResponse,"$.data..totalCouponDiscount").toString().split(",")[0].replace("[", "").replace("]", "").trim();
			Integer totalCouponDiscount_Int = Integer.parseInt(totalCouponDiscount);

	        System.out.println("totalCouponDiscount----  \n" +totalCouponDiscount );
	        
	        Integer finalPriceONcart = SubtotalPrice_Int+vatcharges_Int-totalCouponDiscount_Int;

	        String totalEffectivePrice =JsonPath.read(fetchcartResponse,"$.data..totalEffectivePrice").toString().replace("[", "").replace("]", "").trim();
	        System.out.println("totalEffectivePrice----  \n" +totalEffectivePrice );
			Integer totalEffectivePrice_Int = Integer.parseInt(totalEffectivePrice);
			boolean disc = false;

			AssertJUnit.assertEquals(Calculation_int, SubtotalPrice_Int);
			if(finalPriceONcart+1< totalEffectivePrice_Int && finalPriceONcart-1<totalEffectivePrice_Int ){
				disc=true;
				AssertJUnit.assertEquals("Discoun't doesn't match in cart", true, disc);

			}

			String AddressID = bountyHelper.getAddress(userName, password);
			String Xid = OrderDiscounhelper.getAndSetTokens(userName, password);

			String OrderID= OrderDiscounhelper.createAndQueueOrder(userName, Xid,  "0", "0",
					"0.0", AddressID, "responsive", "DEFAULT", "on");
	        
			OrderEntry orderEntry = OMSHelper.getOrderEntry(OrderID);
			String itemDiscount = orderEntry.getDiscount().toString();
			String couponName = orderEntry.getCouponCode().toString();
			String couponDiscount = orderEntry.getCouponDiscount().toString();
			String FinalPAy = orderEntry.getFinalAmount().toString();



			System.out.println("Test "+orderEntry.getDiscount());
			System.out.println("Test2 "+orderEntry.getCouponCode());
			System.out.println("Test4 "+orderEntry.getCouponDiscount());
			System.out.println("Test4 "+orderEntry.getFinalAmount());





			

		
		
		//Get the style data 
		
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETALLDISCOUNTSV2, init.Configurations);
//		RequestGenerator rs = new RequestGenerator(service);
//		String response = rs.returnresponseasstring();

//		AssertJUnit.assertEquals("Response is not 200 OK", 200, rs.response.getStatus());
	}
	
	@Test
	public void testabc() throws UnsupportedEncodingException, JAXBException{
		OrderEntry orderEntry = OMSHelper.getOrderEntry("70043522");
		System.out.println("Test "+orderEntry.getDiscount());
		
	}

}
