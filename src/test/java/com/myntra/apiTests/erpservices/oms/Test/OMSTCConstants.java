package com.myntra.apiTests.erpservices.oms.Test;

public class OMSTCConstants {
	
	public static class CancellationTillHubTest {
        public static final String LOGIN_URL = "cancellationtillhub@gmail.com";
        public static final String PASSWORD = "123456";
        public static final String cancelReleaseOrderInPKStatus_ITEM1 = "47414"; //3832
        public static final String cancelOrderInPKStatus_ITEM1 = "47415"; //3832
        public static final String cancelMultipleReleasesInPKStatus_ITEM1 = "68508"; //3832
        public static final String cancelMultipleReleasesInPKStatus_ITEM2 = "47416"; //3837
        public static final String cancelOrderOfMultipleReleasesInPKStatus_ITEM1 = "47417"; //3832
        public static final String cancelOrderOfMultipleReleasesInPKStatus_ITEM2 = "47418"; //3837
        public static final String cancelOrderOfMultipleReleasesInSHStatus_ITEM1 = "47419"; //3832
        public static final String cancelOrderOfMultipleReleasesInSHStatus_ITEM2 = "47420"; //3837
        public static final String cancelReleaseOrderInSHStatus_ITEM1 = "47421"; //3832
        public static final String cancelOrderInSHStatus_ITEM1 = "47422"; //3832
        public static final String cancelMultipleReleasesInSHStatus_ITEM1 = "47307"; //3832
        public static final String cancelMultipleReleasesInSHStatus_ITEM2 = "47308"; //3837
        public static final String cancelMultipleReleasesInPKStatusTnB_ITEM1 = "47342"; //3837
        public static final String cancelMultipleReleasesInPKStatusTnB_ITEM2 = "47343"; //3837
    }
	
	public static class OMSForwardFlowTests {
        public static final String LOGIN_URL = "end2endautomation1@gmail.com";
        public static final String PASSWORD = "123456";
        public static final String verifyInventoryReducedAfterOrderPacked_ITEM1 = "47416"; //3837 Need to Run
        public static final String verifyInventoryReducedAfterOrderPacked_ITEM2 = "47417"; //895234 Need to Run
        public static final String verifyInventoryReducedAfterOrderPacked_ITEM3 = "47418"; //1251866 Need to Run
        public static final String verifyInventoryReducedAfterOrderPacked_ITEM4 = "68509"; //1153314 Need to Run
    }
	
	
	public static class SplitOrderTest {
        public static final String LOGIN_URL = "gadem1985@gmail.com";
        public static final String PASSWORD = "1234554321";
        public static final String checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1 = "796931"; //3830   Pass
    }
	
	public static class OMSLineCancellationTest {
        public static final String LOGIN_URL = "omslinecancellation@gmail.com";
        public static final String PASSWORD = "123456";
        public static final String cancelQuantityForWhichWHIsNotAssigned_ITEM1 = "19912"; //984631  Pass
        public static final String checkLineCancellationForEachQuantityWhereLPAndCBIsUsed_ITEM1 = "19911"; //3832  
        public static final String cancelLineWithWMSAssociation_ITEM1 = "19910";//3832  
        public static final String cancelOneQuantityFromLineWithWMSAssociation_ITEM1 = "19909"; //3832 
        public static final String cancelReleasesFollowedByLineCancellation_MultipleQuantity_ITEM1 = "19916"; //3832 
        public static final String cancelOrderReleaseAfterItemAssociation_ITEM1 = "19915"; //3832   
        public static final String cancelOrderReleaseAfterItemAssociation_ITEM2 = "19914"; //3837  
        public static final String cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1 = "19913"; //3856  Pass
        public static final String packOrderAfterSomeQuantityCancellation_ITEM1 = "47361"; //3833  
        
    }
	
	public static class OMSOrderCancellationServiceTest {
        public static final String LOGIN_URL = "omsordercancellation@gmail.com";
        public static final String PASSWORD = "123456";
        public static final String cancelOrderForWhichWHIsNotAssigned_ITEM1 ="47362";// "987669";   Pass
        public static final String cancelOrderWithSingleItemCOD_ITEM1 = "47593"; //3832  Pass
        public static final String cancelOrderAfterItemAssociation_ITEM1 = "47364"; //3832  Pass
        public static final String cancelOrderAfterItemAssociation_ITEM2 = "47365"; //3837   Pass
        public static final String cancelOrderFollowedByItemCancellation_ITEM1 = "47590"; //3833  Pass
        public static final String cancelOrderWithMultpleItemCOD_ITEM1 = "47591"; //3832  Pass
        public static final String cancelOrderWithMultpleItemCOD_ITEM2 = "47592"; //3836  Pass
    }
	
	public static class OMSReleaseCancellation {
        public static final String LOGIN_URL = "omsreleasecancellation@gmail.com";
        public static final String PASSWORD = "123456";
        public static final String cancelReleaseForWhichWHIsNotAssigned_ITEM1 = "47369"; //984634  Pass
        public static final String cancelReleaseWithSingleItemCOD_ITEM1 = "74475"; //3832  Pass
        public static final String cancelOrderFollowedByReleaseCancellation_ITEM1 = "47323"; //3831  Pass
        public static final String cancelOrderFollowedByReleaseCancellation_ITEM2 = "47324"; //3834  Pass
        public static final String cancelOrderReleaseAfterItemAssociation_ITEM1 = "47325"; //3832  Pass
        public static final String cancelOrderReleaseAfterItemAssociation_ITEM2 = "47326"; //3837  Pass
        public static final String cancelReleaseForWhichWHIsAssigned_ITEM1 = "47413"; //3836   Pass
    }
	
	public static class OMSFreeGiftItemTest {
        public static final String LOGIN_URL = "omsreleasecancellation@gmail.com";
        public static final String PASSWORD = "123456";
        public static final String giftItemAssginedToSameWarehouse1 = "1552";
        public static final String giftItemAssginedToSameWarehouse2 = "1551";

    }

    public static class OMSMultiSeller {
        public static final String LOGIN_URL = "multiseller@gmail.com";
        public static final String PASSWORD = "123456";
    }

    public static class ARMOR {
        public static final String LOGIN_URL = "armorTest@myntra.com";
        public static final String PASSWORD = "123456";
        public static final String ADDRESSID = "6132352";
        public static final String SKUID_checkStatusesInArmorInEndToEndFlow = "1243744";
        public static final String[] SKUID_checkStatusesInArmorForSingleReleaseCancellation = {"3832", "3833"};
    }

    // Added by Sneha

    public static class SellerTypeNoneAndSKUs {
        public static final String sellerNone1="19";
        public static final String sellerNone2="21";
        public static final String sellerNone3="25";
        public static final String sellerNone1Sku1="19869";
        public static final String sellerNone1Sku2="19870";
        public static final String sellerNone1Sku3="19871";
        public static final String sellerNone1Sku4="19872";
        public static final String sellerNone2Sku1="3833";
        public static final String sellerNone2Sku2="3832";
        public static final String sellerNone2Sku3="3831";
        public static final String sellerNone2Sku4="3834";
        public static final String sellerNone3Sku1="390016"; //style:113726
        public static final String sellerNone3Sku2="390031"; //113709
        public static final String sellerNone3Sku3="749874";
    }

    //Warehouse id 45
    public static class SellerTypeSellerLevelAndSKUs {
        public static final String sellerLevel1="4";
        public static final String sellerLevel1SKU1="1251666"; //373865
        public static final String sellerLevel1SKU2="1251667";
        public static final String sellerLevel1SKU3="1251668";
        public static final String sellerLevel1SKU4="1251669";
    }

    //Warehouse id 45 //all errors

    public static class SellerTypeSKULevelAndSKUs {
        public static final String sellerSKULevel1="6";
        public static final String sellerSKULevel1SKU1="1128779";//339138
        public static final String sellerSKULevel1SKU2="1128780";
        public static final String sellerSKULevel1SKU3="1128784";
      //  public static final String sellerSKULevel1SKU3="1128786";
        public static final String sellerSKULevel1SKU4="1128784";
    }

    //warehouse id 45
    public static class SellerTypeQtyLevelAndSKUs{
        public static final String sellerQTYLevel1="5";
        public static final String sellerQTYLevel2="3";
        public static final String sellerQTYLevel1SKU1="1251842"; //373900
        public static final String sellerQTYLevel1SKU2="1251843";
        public static final String sellerQTYLevel1SKU3="1251885"; //373908
        public static final String sellerQTYLevel2SKU1="8225387";
    }


//fox 8 data set
  /*  public static class SellerTypeNoneAndSKUs {
=======
   /* public static class SellerTypeNoneAndSKUs {
>>>>>>> 75fd1096285a77cf4543e4c2929cc5f6aea97cc2
        public static final String sellerNone1="19";
        public static final String sellerNone2="21";
        public static final String sellerNone3="1";
        public static final String sellerNone1Sku1="1153029";
        public static final String sellerNone1Sku2="1153280";
        public static final String sellerNone1Sku3="6283520";
        public static final String sellerNone1Sku4="10000004";
        public static final String sellerNone2Sku1="10000010";
        public static final String sellerNone2Sku2="10000013";
        public static final String sellerNone2Sku3="10000016";
        public static final String sellerNone2Sku4="10000019";
        public static final String sellerNone3Sku1="3918";
        public static final String sellerNone3Sku2="3918";
        public static final String sellerNone3Sku3="3918";
    }
    public static class SellerTypeSellerLevelAndSKUs {
        public static final String sellerLevel1="4";
        public static final String sellerLevel1SKU1="1251666"; //373865
        public static final String sellerLevel1SKU2="1251670";
        public static final String sellerLevel1SKU3="1251669";
        public static final String sellerLevel1SKU4="1251668";
    }

    public static class SellerTypeSKULevelAndSKUs {
        public static final String sellerSKULevel1="6";
        public static final String sellerSKULevel1SKU1="1128779";//339138
        public static final String sellerSKULevel1SKU2="1128783";
        public static final String sellerSKULevel1SKU3="1128781";
        //  public static final String sellerSKULevel1SKU3="1128786";
        public static final String sellerSKULevel1SKU4="2447877";
    }

    public static class SellerTypeQtyLevelAndSKUs{
        public static final String sellerQTYLevel1="5";
        public static final String sellerQTYLevel2="3";
        public static final String sellerQTYLevel1SKU1="1251866"; //373900
        public static final String sellerQTYLevel1SKU2="1251867";
        public static final String sellerQTYLevel1SKU3="1251868"; //373908
        public static final String sellerQTYLevel2SKU1="8225387";
<<<<<<< HEAD
    }
    */
    
    public static class OtherSkus{
        public static final String skuId_3827 ="3827";
        public static final String skuId_3828 ="3828";
        public static final String skuId_3829="3829";
        public static final String skuId_3830="3830";
        public static final String skuId_796934="796934";
        public static final String skuId_818510="818510";
        public static final String skuId_818511="818511";
        public static final String skuId_818512="818512";
        public static final String skuId_818506="818506";
        public static final String skuId_818507="818507";
        public static final String skuId_818508="818508";
        public static final String skuId_818509="818509";
        public static final String skuId_1190293="1190293";
        public static final String skuId_1190296="1190296";
        public static final String skuId_1190298="1190298";
        public static final String skuId_1190300="1190300";
        public static final String skuId_3831 ="3831";
        public static final String skuId_3832="3832";
        public static final String skuId_3833="3833";
        public static final String skuId_3834="3834";
        public static final String skuId_3835 ="3839";//"3835";
        public static final String skuId_3836="3840";//"3836";
        public static final String skuId_3837="3841";//"3837";
        public static final String skuId_3838="3842";//"3838";
        public static final String skuId_3843="3843";
        public static final String skuId_3844="3844";
        public static final String skuId_3845="3845";
        public static final String skuId_3846="3846";
        public static final String skuId_3847="3847";
        public static final String skuId_1243741="1243741";
        public static final String skuId_1243742="1243742";
        public static final String skuId_1243743="1243743";
        public static final String skuId_1243744="1243744";
        public static final String skuId_1243745="1243745";
        public static final String skuId_796931="796931";
        public static final String skuId_796932="796932";
        public static final String skuId_796933="796933";
        public static final String skuId_47458= "47458" ;
        public static final String skuId_47459= "47459" ;  
        public static final String skuId_47460= "47460" ;
        public static final String skuId_47461= "47461" ;  
        public static final String skuId_47569= "47569" ;
        public static final String skuId_47572= "47572" ;  
        public static final String skuId_47571= "47571" ;
        public static final String skuId_47573= "47573" ;  
        public static final String skuId_47570= "47570" ;
        public static final String skuId_47578= "47578" ;  
        public static final String skuId_47576= "47576" ;
        public static final String skuId_47574= "47574" ;   
        public static final String skuId_47575= "47575" ;
        public static final String skuId_47577= "47577" ;  
        public static final String skuId_1071773= "1071773" ;
        public static final String skuId_1071770= "1071770" ;  
        public static final String skuId_1071771= "1071771" ;
        public static final String skuId_1071769= "1071769" ;  
        public static final String skuId_1071772= "1071772" ;
        public static final String skuId_1071768= "1071768" ;  
        public static final String skuId_1071774= "1071774" ;
        public static final String skuId_1270332= "1270332" ;  
        public static final String skuId_1190292= "1190292" ;
        public static final String skuId_749877= "749877" ;
        public static final String skuId_749876= "749876" ;
        public static final String skuId_749874= "749874" ;  
        public static final String skuId_749875= "749875" ;
        public static final String skuId_3856 = "3856";
        public static final String skuId_3918 = "3918";
        public static final String skuId_585868 = "585868";
        public static final String skuId_852767 = "852767";
        public static final String skuId_945928 = "945928";
        public static final String skuId_987669 = "987669";
        public static final String skuId_1111187 = "1111187";
        public static final String skuId_1147531 = "1147531";
        public static final String skuId_1153620 = "1153620";
        public static final String skuId_828703 = "828703";
        public static final String skuId_330411 = "330411";
        public static final String skuId_493441 = "493441";
        public static final String skuId_1152952 = "1152952";
        public static final String skuId_1152955 = "1152955";
        public static final String skuId_1152957 = "1152957";
        public static final String skuId_1152958 = "1152958";
        public static final String skuId_1152959 = "1152959";
        public static final String skuId_1152960 = "1152960";
        public static final String skuId_1152961 = "1152961";
        public static final String skuId_1152962 = "1152962";
        public static final String skuId_1152964 = "1152964";
        public static final String skuId_1153080 = "1153080";
        //New Skus
        public static final String skuId_3839 = "3839";
        public static final String skuId_159818 = "159818";
        public static final String skuId_984631 = "984631";
        public static final String skuId_8005 = "8005";
        public static final String skuId_8006 = "8006";
        public static final String skuId_895235 = "895235";
        public static final String skuId_3919 = "3919";
        public static final String skuId_3917 = "3917";
        public static final String skuId_3841 = "3841";
        public static final String skuId_984285 = "984285";
        public static final String skuId_1153008 = "1153008";
        public static final String skuId_1153239 = "1153239";
        public static final String skuId_1153106 = "1153106";
        public static final String skuId_1153416 = "1153416";
        public static final String skuId_159821 = "159821";
        public static final String skuId_47583 = "47583";
        public static final String skuId_47584 = "47584";
        public static final String skuId_30055 = "30055";
        public static final String skuId_30056 = "30056";
        public static final String skuId_473783 = "473783";//"895234";
        public static final String skuId_12266282 = "12266282"; //1787455
        public static final String skuId_3871 ="3871";
    }
    
    public static class PitneyBowesSkus{
    	public static final String skuId_1046378 = "1046378";
    }
    
    public static class Different_HSNCODES_SKU{
    	public static final String skuId_40563="40563";
    	public static final String skuId_12672642="12672642";
    	public static final String skuId_749877="749877";
    	public static final String skuId_40562="40562";
    }
    
    public static class PreOrderSkus{
        public static final String preorderSkuId1Seller21="66095";  //different launch date 66377
        public static final String preorderSkuId2Seller21="66096";  //different launch date
        public static final String preorderSkuId3Seller21="66095";  //same launch date 12348
        public static final String preorderSkuId4Seller21="66377";  //same launch date 12349
        public static final String preorderSkuId3Seller25="3827";
        public static final String preorderSkuId4Seller25="3827";
        public static final String preorderSkuId5Seller21="66382"; // Past Launch Date  12346
        public static final String normalSkuId1Seller21="3827";
        public static final String normalSkuId2Seller21="3827";
        public static final String normalSkuId3Seller25="3827";
        public static final String normalSkuId4Seller25="3827";
        public static final String normalSkuId1TnBSeller21="3831";
        public static final String maduraSKu1="3827";
        public static final String normalSkuId1JITSeller21="3827";
    }
    
    public static class MasterDateStyleIds{
    	public static final int styleId_1530= 1530 ;
    	public static final int styleId_1531= 1531 ;
    	public static final int styleId_1532= 1532 ;
    	public static final int styleId_1534= 1534 ;
    	public static final int styleId_1535= 1535 ;
    	public static final int styleId_371389= 371389 ;
    	public static final int styleId_236593= 236593 ;
    	public static final int styleId_10001= 10001 ;
    	public static final int styleId_10002= 10002 ;
    	public static final int styleId_10005= 10005 ;
    	public static final int styleId_10031= 10031 ;
    	public static final int styleId_10007= 10007 ;
    	public static final int styleId_10008= 10008 ;
    	public static final int styleId_10009= 10009 ;
    	public static final int styleId_10010= 10010 ;
    	public static final int styleId_10011= 10011 ;
    	public static final int styleId_10012= 10012 ;
    	public static final int styleId_321729= 321729 ;
    	public static final int styleId_356809= 356809 ;
    	public static final int styleId_243673= 243673 ;
    	public static final int styleId_243672= 243672 ;
    	public static final int styleId_219266= 219266 ;
    	public static final int styleId_1538 = 1538;
    	public static final int styleId_1555 = 1555;
    	public static final int styleId_95051 = 95051;
    	public static final int styleId_147464 = 147464;
    	public static final int styleId_171643 = 171643;
    	public static final int styleId_247180 = 247180;
    	public static final int styleId_253875 = 253875;
    	public static final int styleId_282672 = 282672;
    	public static final int styleId_295681 = 295681;
    	public static final int styleId_333473 = 333473;
    	public static final int styleId_344866 = 344866;
    	public static final int styleId_346106 = 346106;
    	public static final int styleId_346108 = 346108;
    	public static final int styleId_346109 = 346109;
    	public static final int styleId_346111 = 346111;
    	public static final int styleId_346112 = 346112;
    	public static final int styleId_346113 = 346113;
    	public static final int styleId_346114 = 346114;
    	public static final int styleId_346115 = 346115;
    	public static final int styleId_346116 = 346116;
    	public static final int styleId_346118 = 346118;
    	public static final int styleId_346234 = 346234;
    	public static final int styleId_346661 = 346661;
    	public static final int styleId_12348 = 12348;
    	public static final int styleId_12345 = 12345;
    	public static final int styleId_12346 = 12346;
    	public static final int styleId_373900= 373900;
    	public static final int styleId_373908 = 373908;
    	public static final int styleId_373909 = 373909;
    	public static final int styleId_339138 = 339138;
    	public static final int styleId_339139 = 339139;
    	public static final int styleId_373865 = 373865;
    	public static final int styleId_10016 = 10016;
    	public static final int styleId_113726 = 113726;
    	public static final int styleId_113709 = 113709;
    }
    
    public static class WareHouseIds{
    	public static final int warehouseId1_BA = 1;   //Bangalore
    	public static final int warehouseId19_BG = 19; //BANGALORE TEMP
    	public static final int warehouseId28_GN = 28; //Gurgaon-Binola W/H
    	public static final int warehouseId36_BW = 36; //Bangalore - New Whitefield W/H
    	public static final int warehouseId2_BW = 2; //Bangalore - New Whitefield W/H
    	public static final int warehouseId35_BW = 35; //Bangalore - New Whitefield W/H
    	public static final int warehouseId20_BW = 20; //Bangalore - New Whitefield W/H
    	public static final int warehouseId47_BW = 47; //Bangalore - New Whitefield W/H
    	public static final int warehouseId46_BW = 46; //Bangalore - New Whitefield W/H
    	public static final int warehouseId26_BW = 26; //Bangalore - New Whitefield W/H
    	public static final int warehouseId55_BW = 55;
    }
    
    public static class Pincodes{
    	
        public static final String PINCODE_123456 = "123456";
        public static final String PINCODE_560068 = "560068"; //Pincode : 560068
        public static final String PINCODE_282001 = "282001"; //Pincode : 282001
        public static final String PINCODE_585223 = "585223"; //Pincode : 585223
        public static final String PINCODE_560067 = "560067"; //Pincode : 560067
        public static final String PINCODE_560076 = "560076";
        public static final String PINCODE_411001 = "411001";
        public static final String PINCODE_180001 = "180001";

    }
    
    public static class LoginAndPassword{
    	public static final String WHAssignmentServiceTestLogin = "whassignmentservicetest@gmail.com";
    	public static final String WHAssignmentServiceTestPassword = "123456";
    	public static final String ValidateIdempotencyOMSActionLogin = "validateidempotencyomsaction@gmail.com";
    	public static final String ValidateIdempotencyOMSActionPassword = "123456";
    	public static final String ExchangeServiceLogin = "exchangeautomation1@gmail.com";
    	public static final String ExchangeServicePassword = "123456";
    	public static final String ExpressRefundServiceTestLogin = "expressrefundmyntra1@gmail.com";
    	public static final String ExpressRefundServiceTestPassword = "123456";
    	public static final String GovtTaxCalculationTestLogin = "govttaxcalculationtest@gmail.com";
    	public static final String GovtTaxCalculationTestPassword = "123456";
        public static final String DiscountCouponMoreThan20="govttax";
    	public static final String OMSOnHoldServiceTestLogin = "omsonholdservicetest1@gmail.com";
    	public static final String OMSOnHoldServiceTestPassword = "123456";
    	public static final String OMSTryAndBuyTestLogin = "omstryandbuytest@gmail.com";
    	public static final String OMSTryAndBuyTestPassword = "123456";
    	public static final String PriceOverrideServiceTestLogin = "omspriceoverride1@gmail.com";
    	public static final String PriceOverrideServiceTestPassword = "123456";
    	public static final String SearchServiceTestLogin = "searchservicetest@gmail.com";
    	public static final String SearchServiceTestPassword = "123456";
    	public static final String AddidionalChargeLogin = "additionalcharge@gmail.com";
    	public static final String AddidionalChargePassword = "123456";
    	public static final String OMSEgcAndOnlineTestCaseLogin = "additionalcharge@gmail.com";
    	public static final String OMSEgcAndOnlineTestCasePassword = "123456";
    	
    }

    public static class KeyValue{
        public static final String GiftCharges="giftwrap.charges";
        public static final String GiftChargesValue="25";
        public static final String ShippingCharges="shipping.charges.amount";
        public static final String ShippingChargesValue="99";
    }

    public static class SellerValues{
        public static final int sellerId_HEAL = 21;
        public static final int sellerId_CONS = 25;
        public static final int sellerId_HEAL_JIT = 21;
        public static final int sellerId_CONS_JIT = 25;
    }

    public static class VatAdjutments{
        public static final int skuID_890848=890848;
        public static final int skuID_3133754=3133754;
    }
    
    public static class freeItemTestData{
    	public static final String LOGIN="freegiftitem1@gmail.com";
    	public static final String PASSWORD="123456";
    	public static final String PaidStyle="1551";
    	public static final String FreeStyle="1552";
    	public static final String PaidSku="3914";
    	public static final String FreeSku="3915";
    }
    
    public static class hsnCodes{
    	public static final String hsnCode_12345678="12345678";
    	public static final String hsnCode_94032090="94032090";
    	public static final String hsnCode_Invalid="61052";
    	public static final String hsnCode_12345679="12345679";
    	public static final String hsnCode_61062011="61062011";
    	public static final String hsnCode_61062010="61062010";
    }
    
    public static class OMSEgcAndOnlineTestCase{
    	public static final long skuId_3831 =3831;
    }
    
    public static class GenerateShippingLabel{
     	public static final String firstLogin = "shippinglabel@gmail.com";
     	public static final String firstPassword = "123456";
     }

    public static class ConsolidationEngine{
    	public static final String firstLogin = "consolidation11@gmail.com";
    	public static final String firstPassword = "123456";
    	public static final String secondLogin = "consolidation21@gmail.com";
    	public static final String secondPassword = "123456";

    }
    
    public static class OMSLineStatusChanges{
    	public static final String firstLogin = "omslinestatuschange1@gmail.com";
    	public static final String firstPassword = "123456";
    	public static final String secondLogin = "omslinestatuschange2@gmail.com";
    	public static final String secondPassword = "123456";

    }
    
    public static class AssignWarehouseE2E{
    	public static final String firstLogin = "omslinestatuschange1@gmail.com";
    	public static final String firstPassword = "123456";
    	public static final String secondLogin = "omslinestatuschange2@gmail.com";
    	public static final String secondPassword = "123456";

    }
    
    public static class MarkReadyToDispatch{
    	public static final String Login = "readytodispatch@gmail.com";
    	public static final String Password = "123456";
    }
    
}
