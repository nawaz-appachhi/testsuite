package com.myntra.qnotifications;

//Xpaths for email templates
public class Xpath_Constants {

	public static class ORDER_CONFIRM {
		public static final String SKU_QUANTITY = "3832:1"; //818503
        public static final String NAME = "//td/p";
        public static final String ORDER_ID = "//p[3]";
        public static final String AMOUNT = "//h3/strong";
        public static final String TOTAL_ITEMS ="//td/table/tbody/tr/td/table/tbody/tr/td/h3";
        public static final String DELIVERY_DATE = "///td/p/span";
        public static final String PRODUCT_NAME = "//td[2]/span";
        public static final String PRODUCT_DETAILS = "//tr[3]/td/table/tbody/tr/td[2]";
        public static final String ADDRESS = "//tr[5]/td/table/tbody/tr/td";
        public static final String ORDER_VALUE = "//tr[7]/td/table/tbody/tr/td/table/tbody/tr/td[2]";
        public static final String SHIPPING_CHARGE = "//td/table/tbody/tr[2]/td[2]";
        public static final String TOTAL = "//tr[2]/td/table/tbody/tr/td[2]/span";
        public static final String STYLE = "Puma Men Grey Solid Round Neck T-Shirt ";
        public static final int BOUNTY_SMS_ID = 75;
        public static final int ORDERCONFIRM_SMS_ID = 38;
    }
	
	public static class ORDER_DISPATCHED {
		public static final String SKU_QUANTITY = "3832:2";
        public static final String NAME = "//td/p";
        public static final String EXPECTED_DELIVERY = "//p[3]";
        public static final String SHIPMENT_DETAIL = "//p[4]";
        public static final String PRODUCT_NAME = "//td[2]/p/span";
        public static final String PRODUCT_DETAILS = "//td[2]/p";
        public static final String PACKAGE_VALUE = "//tr[2]/td/table/tbody/tr/td[2]";
        public static final String SHIPPING_CHARGE = "//td/table/tbody/tr[2]/td[2]";
        public static final String ORDER_VALUE = "//td[2]/span";
        public static final String INTENDED_FOR = "//tr[8]/td/p/a";
        public static final String STYLE = "puma men grey solid / plain round neck t-shirt";
        public static final int ORDERDISPATCHED_SMS_ID = 30;
    }
	
	public static class OUT_FOR_DELIVERY {
		public static final String SKU_QUANTITY = "3832:2";
        public static final String NAME = "//td/p";
        public static final String SHIPMENT_DETAIL = "//p[3]";
        public static final String PRODUCT_NAME = "//td[2]/p/span";
        public static final String PRODUCT_DETAILS = "//td[2]/p";
        public static final String ADDRESS = "//tr[5]/td/table";
        public static final String PACKAGE_VALUE = "//tr[2]/td/table/tbody/tr/td[2]";
        public static final String SHIPPING_CHARGE = "//td/table/tbody/tr[2]/td[2]";
        public static final String ORDER_VALUE = "//td/table/tbody/tr/td[2]/span";
        public static final String INTENDED_FOR = "link=end2endautomation4@gmail.com";
        public static final String STYLE = "Puma Men grey solid / plain round neck T-Shirt";
        public static final int OUTFORDELIVERY_SMS_ID = 34;
        
    }
	
	public static class DELIVERED {
		public static final String SKU_QUANTITY = "3832:2";
        public static final String NAME = "//td/p";
        public static final String MESSAGE = "//td/p[2]";
        public static final String ORDER_NO = "//p[3]";
        public static final String PRODUCT_DETAILS = "//td[2]/p";
        public static final String PACKAGE_VALUE = "//tr[6]/td/table/tbody/tr[2]/td/table/tbody/tr/td[2]";
        public static final String SHIPPING_CHARGE = "//td/table/tbody/tr[2]/td[2]";
        public static final String INTENDED_FOR = "//tr[8]/td/p/a";
        public static final String MESSAGE2 = "your myntra order for the following product has been delivered.";
        
    }
	
	public static class FAILED_DELIVERED {
		public static final String SKU_QUANTITY = "3831:1";
        public static final String NAME = "//div[2]/p/span";
        public static final String AMOUNT = "//tr[5]/td[3]/strong";
        public static final int FAILEDDELIVERY_SMS_ID = 49;
        
        
    }
	
	public static class EXCHANGE_CONFIRMED {
		public static final String SKU_QUANTITY = "3874:1";
		public static final String EXCHANGED_SKU = "3875";
        public static final String EXCHANGE_NO = "///p/b";
        public static final String EXCHANGE_DATE = "//b/span/span";
        public static final String EXCHANGE_COURIER = "//p[3]/b";
        public static final String PRODUCT_NAME = "//td[2]/table/tbody/tr/td/span";
        public static final String ORIGINAL_SIZE = "//td[2]/table/tbody/tr[5]/td";
        public static final String EXCHANGE_SIZE = "//td[3]/b";
        public static final String COURIER = "Myntra logistics";
        public static final String ORIGINAL_SIZE2 = "UK9";
        public static final String EXCHANGE_SIZE2 = "UK7";
        public static final String ADDRESS = "//address";
      
        
    }
	
	public static class EXCHANGE_DISPATCHED {
		public static final String SKU_QUANTITY = "3874:1";
		public static final String EXCHANGED_SKU = "3875";
        public static final String EXCHANGE_NO = "//p/b";
        public static final String EXCHANGE_DATE = "//b/span/span";
        public static final String EXCHANGE_COURIER = "//p[3]/b";
        public static final String PRODUCT_NAME = "//td[2]/table/tbody/tr/td/span";
        public static final String ORIGINAL_SIZE = "//td/b";
        public static final String EXCHANGE_SIZE = "//td[3]/b";
        public static final String ADDRESS = "//address";
        public static final String COURIER = "Myntra logistics";
        public static final String ORIGINAL_SIZE2 = "UK9";
        public static final String EXCHANGE_SIZE2 = "UK10";
        public static final String STYLE = "Puma Men's Ballistic Rubber Shoe";
        
    }
	
	public static class EXCHANGE_OUTFORDELIVERY {
		public static final String SKU_QUANTITY = "3874:1";
		public static final String EXCHANGED_SKU = "3875";
        public static final String EXCHANGE_NO = "//p/b";
        public static final String EXCHANGE_SDA = "//p[2]/b";
        public static final String EXCHANGE_SDA_MOBILE = "//p[3]/b";
        public static final String EXCHANGE_COURIER = "//p[4]/b";
        public static final String PRODUCT_NAME = "//td[2]/table/tbody/tr/td/span";
        public static final String ORIGINAL_SIZE = "//td/b";
        public static final String EXCHANGE_SIZE = "//td[3]/b";
        public static final String ADDRESS = "//address";
        public static final String COURIER = "Myntra logistics";
        public static final String ORIGINAL_SIZE2 = "UK9";
        public static final String EXCHANGE_SIZE2 = "UK10";
        public static final String STYLE = "Puma Men's Ballistic Rubber Shoe";
        
    }
	
	public static class EXCHANGE_DELIVERED {
		public static final String SKU_QUANTITY = "3874:1";
		public static final String EXCHANGED_SKU = "3875";
        public static final String EXCHANGE_NO = "//p/b";
        public static final String EXCHANGE_NPS = "//tr[2]/td/p";
        public static final String PRODUCT_NAME = "//td[2]/table/tbody/tr/td/span";
        public static final String ORIGINAL_SIZE = "//td/b";
        public static final String EXCHANGE_SIZE = "//td[3]/b";
        public static final String ORIGINAL_SIZE2 = "UK9";
        public static final String EXCHANGE_SIZE2 = "UK10";
        public static final String STYLE = "Puma Men's Ballistic Rubber Shoe";
        
        
    }
	
	public static class ORDER_CANCELLED {
		public static final String ORDER_NUMBER = "//div[2]/p[2]/span";
		public static final String AMOUNT = "//td[3]/strong";
        
        
        
    }
	public static class RETURN_CONFIRMED {
		public static final String RETURN_NUMBER = "//p/b";
		public static final String RETURN_MODE= "//p[3]/b";
		public static final String RETURN_AMOUNT= "//p[4]/b";
		public static final String EXPECTED_PICKUP= "//b/span/span";
		public static final String COURIER= "//p[6]/b";
		public static final String ADDRESS= "//address";
		public static final String COURIER2 = "Myntra logistics";
		public static final int  RETURNCONFIRMED_SMS_ID = 132;
		
        
    }
	public static class RETURN_PROCESSED {
		public static final String RETURN_NUMBER = "//p/b";
		public static final String RETURN_AMOUNT= "//p[2]/b";
		public static final String NPS= "//td[5]/a";
		public static final String PRODUCT_NAME= "//td[2]/table/tbody/tr/td/span";
		public static final String PRODUCT_DETAILS= "//tr[6]/td";
		public static final String AMOUNT_REFUNDED= "//tr[8]/td/table/tbody/tr/td/table/tbody/tr/td/span/b";
		public static final String STYLE = "puma men grey solid / plain round neck t-shirt";
		public static final int  RETURNCONFIRMED_SMS_ID = 133;
		
        
    }
	public static class RETURN_PICKUPFAILED {
		public static final String RETURN_NUMBER = "//p/b";
		public static final String RETURN_AMOUNT= "//p[2]/b";
		public static final String COURIER= "//p[3]/b";
		public static final String PRODUCT_NAME= "//td[2]/table/tbody/tr/td/span";
		public static final String PRODUCT_DETAILS= "//tr[6]/td";
		public static final String ADDRESS= "//address";
		public static final String STYLE = "puma men grey solid / plain round neck t-shirt";
		public static final int  RETURNCONFIRMED_SMS_ID = 490;
		
        
    }
	
	public static class RETURN_DECLINED {
		public static final String RETURN_NUMBER = "//p/b";
		public static final String PRODUCT_NAME= "//td[2]/table/tbody/tr/td/span";
		public static final String PRODUCT_DETAILS= "//tr[6]/td";
		public static final String ADDRESS= "//address";
		public static final String STYLE = "puma men grey solid / plain round neck t-shirt";
		public static final int  RETURNCONFIRMED_SMS_ID = 487;
		
        
    }
	
	public static class RETURN_ONHOLDREJECTED {
		public static final String RETURN_NUMBER = "//p/b";
		public static final String PRODUCT_NAME= "//td[2]/table/tbody/tr/td/span";
		public static final String PRODUCT_DETAILS= "//tr[6]/td";
		public static final String STYLE = "puma men grey solid / plain round neck t-shirt";
		public static final int  RETURNCONFIRMED_SMS_ID = 487;
		
        
    }
	
	public static class TESTE2ECREATEORDER {
		public static final String ORDER_CONFIRM = "Your Myntra Order confirmation";
    }
	
	public static class TESTE2EMARKORDERDELIEVERED {
		public static final String ORDER_DELIVERED = "Your Myntra shipment has been delivered";
    }
	
	public static class VAT_TEST {
		public static final String SKU_QUANTITY = "47583:1"; //3867
		public static final String ORDER_ID = "//p/b";
		public static final String VAT_AMOUNT = "//p[2]/b";
		public static final String PRODUCT_NAME = "//td[2]/table/tbody/tr/td/span";
		public static final String STYLE = "Puma Men's Ballistic Spike White Green Shoe";
    }
	
}
