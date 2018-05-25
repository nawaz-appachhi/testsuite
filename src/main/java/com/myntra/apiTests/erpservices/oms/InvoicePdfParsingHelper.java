package com.myntra.apiTests.erpservices.oms;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Chandra Shekhar
 *
 */
public class InvoicePdfParsingHelper extends BaseTest{
	private static Logger log = Logger.getLogger(InvoicePdfParsingHelper.class);
    static Initialize init = new Initialize("/Data/configuration");
    private String uidx;
    private String login="end2endautomation1@gmail.com";
    private String login2 = "bountytestuser001@myntra.com";
    End2EndHelper end2EndHelper = new End2EndHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
	IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	private String shippingLabelPdfData;
	private HashMap<String, String> hashinvoiceDetails;
	private HashMap<String,String> sellerAddressMap= new HashMap<String,String>() ;
	private String invoiceDetailsForParticularSeller;
	private HashMap<String, String> buyerAddressMap=new HashMap<String,String>();
	DecimalFormat df = new DecimalFormat("####0.00");
	String sellerId=null;
	String shipmentNumber = null;
	String invoiceNumber = null;
	Date ordeDate = null;
	String sellerAddress=null;
	String buyerAddress=null;
	String mrpvalue = null;
	String skuCode = null;
	String quantity = null;
	String size = null;
	String unitPrice=null;
	String skuId = null;
	String tinNumber = null;
	private String discount;
	private String lineId;
	private String taxableAmount;
	private String vatOrCst;
	private String taxAmount;
	private String totalAmount;
	private String seller1Address;
	private String sellerShippingAddress;
	private String sellerLabelAddress;
	private String buyerLabelAddress;
	private String otherCharges;
	private String amountToBePaid;

	
    /**
     * @return
     */
    private static HashMap<String, String> getInvoiceOMSHeader() {
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
        createOrderHeaders.put("Content-Type", "multipart/form-data");
        createOrderHeaders.put("Accept", "multipart/form-data");
        return createOrderHeaders;
    }
	
	/**
	 * @param releseId
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	public void getInvoicePdf(String releseId) throws IOException, SAXException, TikaException{
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = null;
        String specialSpace = " ";

        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.INVOICE_SERVICE_PDF, new String[] {""+releseId}, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, getInvoiceOMSHeader());
        httpget = new HttpGet("http://"+service.getIp()+service.getPath());
        httpget.addHeader("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
        httpget.addHeader("Content-Type", "multipart/form-data");
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity resEntity = response.getEntity();
        InputStream is = resEntity.getContent();

        ParseContext pcontext = new ParseContext();

        //parsing the document using PDF parser
        PDFParser pdfparser = new PDFParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        pdfparser.parse(is, handler, metadata,pcontext);

        String invoicePdfData=handler.toString().replaceAll(specialSpace, " ");
        
        //Validate That Invoice Pdf does not contains $ symbol
        Assert.assertEquals(checkHowManyTimesStringAppear(invoicePdfData,"$"),0, "$ symbol should not be there");
        String [] splittedPdfData = invoicePdfData.split("Tax/ Retail Invoice");
        //Shipping label pdf data
        shippingLabelPdfData = splittedPdfData[0];
        
        String invoiceDetails = null;
        log.info("Length: "+splittedPdfData.length);
        log.info(splittedPdfData[1]);
        hashinvoiceDetails = new HashMap<String, String>();
        for(int i=1; i<splittedPdfData.length;i++){
        	invoiceDetails = getInvoiceNumber(splittedPdfData[i]);
        	hashinvoiceDetails.put(invoiceDetails, splittedPdfData[i]);
        	System.out.println("invoiceDetails:"+invoiceDetails);
        }
               
    }
	
	/**
	 * @param invoiceDetails
	 * @return
	 */
	public String getInvoiceNumber(String invoiceDetails){
		String invoiceNumber = StringUtils.substringBetween(invoiceDetails, "Invoice Number:", "Date:").trim();
		
		log.info("This is invoice number"+invoiceNumber);
		return invoiceNumber;
	}
	
	/**
	 * This is to mapped seller Address with sellerId
	 */
	public void mappedSellerAddress(){
         /*//Fox8 mappping
         seller1Address = "Health &Happiness Pvt Ltd\nSurvey Numbers 231, 232 and 233, Soukya\nRoad, Samethanahalli Village,\nAnugondanahalli Hobli, Hoskote Taluk .,\nBangalore - 560067 Karnataka India";
         String seller2Address="SantwanaTestSeller\nSurvey Numbers 231, 232 and 233, Soukya\nRoad, Samethanahalli Village,\nAnugondanahalli Hobli, Hoskote Taluk .,\nBangalore - 560067 Karnataka India";
         
         String seller3Address = "SantwanaTestSeller\nSurvey Numbers 231, 232 and\n233, Soukya Road,\nSamethanahalli Village,\nAnugondanahalli Hobli, Hoskote\nTaluk ., Bangalore - 560067\nKarnataka India";
        String seller4Address="Health &Happiness Pvt Ltd\nSurvey Numbers 231, 232 and\n233, Soukya Road,\nSamethanahalli Village,\nAnugondanahalli Hobli, Hoskote\nTaluk ., Bangalore - 560067\nKarnataka India";
        sellerAddressMap.put("21", seller1Address);
        sellerAddressMap.put("32", seller2Address);
        sellerAddressMap.put("32Label", seller3Address);
        sellerAddressMap.put("21Label", seller4Address);*/
		
        //Fox7 mappping
        seller1Address = "Health &Happiness Pvt Ltd Sy No.2/1\n,Krupa Godowns, Soukya\nRoad,Thirumalashetty Halli Village,\nArugondanahalli Hobli, Hoskote Taluk,\nGurgaon, Haryana - 122413";
        String seller2Address="Consulting Rooms Private Limited Sy\nNo.2/1 ,Krupa Godowns, Soukya\nRoad,Thirumalashetty Halli Village,\nArugondanahalli Hobli, Hoskote Taluk,\nGurgaon, Haryana - 122413";
        
        String seller3Address = "Health &Happiness Pvt Ltd Sy\nNo.2/1 ,Krupa Godowns, Soukya\nRoad,Thirumalashetty Halli\nVillage, Arugondanahalli Hobli,\nHoskote Taluk, Gurgaon, Haryana\n- 122413";
       String seller4Address="Consulting Rooms Private Limited\nConsulting Rooms Private Limited\nSy No.2/1 ,Krupa Godowns,\nSoukya Road,Thirumalashetty\nHalli Village, Arugondanahalli\nHobli, Hoskote Taluk, Gurgaon,\nHaryana - 122413";
       sellerAddressMap.put("21", seller1Address);
       sellerAddressMap.put("25", seller2Address);
       sellerAddressMap.put("21Label", seller3Address);
       sellerAddressMap.put("25Label", seller4Address);
	}
	
	/**
	 * @throws IOException 
	 * This is to mapped Buyer Address with login
	 */
	public void mappedBuyerAddress() throws IOException{
        String buyerAddress1 = "Abhijit Pati\nMyntra Design Pvt. Ltd. Begur Bangalore -\n 560068  KARNATAKA, India";
        uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
        String buyerLabelAddress1 = "Abhijit Pati\nMyntra Design Pvt.\nLtd. Begur Bangalore - 560068 \nKARNATAKA, India";
        buyerAddressMap.put(uidx, buyerAddress1);
        buyerAddressMap.put(uidx+"Label", buyerLabelAddress1);
        
        uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login2);
        buyerAddress1 = "chandra\nPostmaster, Post Office C D A O C D A\n(O) Pune - 411001  MAHARASHTRA, India";
        buyerLabelAddress1 = "chandra\nPostmaster, Post Office C D A O C D A\n(O) Pune - 411001 \nMAHARASHTRA, India";
        buyerAddressMap.put(uidx, buyerAddress1);
        buyerAddressMap.put(uidx+"Label", buyerLabelAddress1);
	}
	
	/**
	 * @param sellerId
	 * This function will generate invoice for particular sellerid
	 */
	public void invoiceForParticularSeller(String sellerId){
        for( String key : hashinvoiceDetails.keySet()) {

		    if(key.endsWith("_"+sellerId)){
		    	invoiceDetailsForParticularSeller = hashinvoiceDetails.get(key);
		    }else{
		    	log.info("Invoice is not available for seller: "+sellerId);
		    }
		}
	}
	
	//Shipping Label
	/**
	 * @param sellerId
	 * @param textToBeSearched
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	public Boolean validateShippingLabelPartOfInvoice(String sellerId,String textToBeSearched) throws IOException, SAXException, TikaException{
		//log.info("------------------Header Portion----------------------------------------------");
		//log.info(shippingLabelPdfData);
		if(!textToBeSearched.isEmpty() && shippingLabelPdfData.contains(textToBeSearched)){
			//log.info("Address is available");
			return true;
		}else{
			log.info("Shipping Label: Either text is empty or is not available in Shipping label");
			return false;
		}
	}
	
	
	/**
	 *
	 * @param sellerId
	 * @param textToBeSearched
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 * This function will validate
	 * Buyer’s Name and Address, Seller’s Name and Address
	 */
	public Boolean validateInvoicePortion(String sellerId,String textToBeSearched) throws IOException, SAXException, TikaException{

			invoiceForParticularSeller(sellerId);

		    if(invoiceDetailsForParticularSeller.contains(textToBeSearched) ){
			    //log.info("----------------Invoice-------------");
		    	//log.info("Text "+textToBeSearched+" ##is available in invoice");
		    	return true;
		    }else{
		    	//log.info("Searching text "+textToBeSearched+" ##is not available in invoice");
		    	return false;
		    }

	}
	
	/**
	 * @param sellerId
	 * @param tinNumberPassed
	 */
	public void validateVATandTINNumberInInvoice(String sellerId,String tinNumberPassed){
		invoiceForParticularSeller(sellerId);
		String vatAndTin = StringUtils.substringBetween(invoiceDetailsForParticularSeller, "VAT/ TIN Number:", "\n").trim();
		//log.info("Vat and Tin Number:"+tinNumberPassed);
		Assert.assertEquals(tinNumberPassed, vatAndTin,"Vat/Tin number is mot correct in Invoice PDF");
	}
	
	/**
	 * @param sellerId
	 * @param invoiceNumberPassed
	 * @return
	 */
	public void validateInvoiceNumberInInvoice(String sellerId,String invoiceNumberPassed){
		invoiceForParticularSeller(sellerId);
		String invoiceNumber = StringUtils.substringBetween(invoiceDetailsForParticularSeller, "Invoice Number:", "Date").trim();
		//log.info("Invoice number:"+invoiceNumber);
		Assert.assertEquals(invoiceNumber, invoiceNumberPassed,"Invoice number is incorrect in PDF");
	}
	
	
	/**
	 * @param sellerId
	 * @param orderNumberPassed
	 * @return
	 */
	public void validateOrderNumberInInvoice(String sellerId,String orderNumberPassed){
		invoiceForParticularSeller(sellerId);
		String orderNumber = StringUtils.substringBetween(invoiceDetailsForParticularSeller, "Order Number:", "Date")+"\n";
		//log.info("orderNumber:"+orderNumber);
		Assert.assertEquals(orderNumber.replaceAll("-", "").trim(), orderNumberPassed,"Order number is incorrect in Invoice Pdf");
	}
	
	
	/**
	 * @param sellerId
	 * @param shipmentNumberPassed
	 * @return
	 */
	public void validateShipmentNumberInInvoice(String sellerId,String shipmentNumberPassed){
		invoiceForParticularSeller(sellerId);
		String shipmentNumber = StringUtils.substringBetween(invoiceDetailsForParticularSeller, "Shipment No:", "\n").trim();
		//log.info("shipmentNumber:"+shipmentNumber);
		Assert.assertEquals(shipmentNumber, shipmentNumberPassed,"Shipment number is incorrect in Invoice Pdf");

	}
	
	/**
	 * @param sellerId
	 * @param shipmentValuePassed
	 * @return
	 */
	public Boolean validateShipmentValueInInvoice(String sellerId,String shipmentValuePassed){
		invoiceForParticularSeller(sellerId);
		String shipmentValue = StringUtils.substringBetween(invoiceDetailsForParticularSeller, "Shipment Value", "\n").
				trim().replaceAll("Rs", "").trim();
		log.info("shipmentValue:"+shipmentValue+" "+shipmentValuePassed);
		if(shipmentValuePassed.equalsIgnoreCase(shipmentValue)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * @param sellerId
	 * @param shipmentAmount
	 * @return
	 */
	public Boolean validateAmountPaidInInvoice(String sellerId,String shipmentAmount){
		invoiceForParticularSeller(sellerId);
		//System.out.println(invoiceDetailsForParticularSeller);
		String loyaltyPointsUsed = StringUtils.substringBetween(invoiceDetailsForParticularSeller, "Loyalty Credit", "\n");
		
		if(loyaltyPointsUsed==null){
			loyaltyPointsUsed = "0.0";
		}else{
			loyaltyPointsUsed = loyaltyPointsUsed.trim().replaceAll("Rs", "").replaceAll("\\(\\-\\)", "").trim();
		}
		
		String cashBacksUsed = StringUtils.substringBetween(invoiceDetailsForParticularSeller, "'My Cashback' Used", "\n");
		
		if(cashBacksUsed==null){
			cashBacksUsed = "0.0";
		}else{
			cashBacksUsed = cashBacksUsed.trim().replaceAll("Rs", "").replaceAll("\\(\\-\\)", "").trim();
		}
		
		String giftCardAmount = StringUtils.substringBetween(invoiceDetailsForParticularSeller, "Gift Card Amount", "\n");

		if(giftCardAmount==null){
			giftCardAmount = "0.0";
		}else{
			giftCardAmount = giftCardAmount.trim().replaceAll("Rs", "").replaceAll("\\(\\-\\)", "").trim();
		}
		
		String specialDiscount = StringUtils.substringBetween(invoiceDetailsForParticularSeller, "Special Discount", "\n");

		if(specialDiscount==null){
			specialDiscount = "0.0";
		}else{
			specialDiscount = specialDiscount.trim().replaceAll("Rs", "").replaceAll("\\(\\-\\)", "").trim();
		}
		
		System.out.println("loyaltyPointsUsed: "+loyaltyPointsUsed+" "+giftCardAmount+" "+specialDiscount);
		Double amountPaid = (Double.parseDouble(shipmentAmount) - (Double.parseDouble(loyaltyPointsUsed)
				+Double.parseDouble(giftCardAmount)+Double.parseDouble(specialDiscount)+Double.parseDouble(cashBacksUsed)));
		long amount = (long)(amountPaid*1);
	    amountToBePaid = StringUtils.substringBetween(invoiceDetailsForParticularSeller, "Amount to be Paid", ".").
				trim().replaceAll("Rs", "").trim();
		if(amountToBePaid.equalsIgnoreCase(amount+"")){
			System.out.println("Values: "+amount+" "+amountToBePaid);
			return true;
		}
		System.out.println("Values: "+amount+" "+amountToBePaid);
		return false;
	}
	

	/**
	 * @param sellerId
	 * @param skuCode
	 * @param skuValues
	 * @param totalAmount
	 * @return
	 */
	public Boolean validateLinesInPDF(String sellerId,String skuCode,String[] skuValues,String totalAmount){
		invoiceForParticularSeller(sellerId);
		//System.out.println(invoiceDetailsForParticularSeller);
		int i = 0 ;
		String[] finalValues = new String[9];
		Boolean flag  = false;
		if(invoiceDetailsForParticularSeller.contains(skuCode)){
			String lines = skuCode+StringUtils.substringBetween(invoiceDetailsForParticularSeller, skuCode, totalAmount+"\n")+totalAmount+"\n";
			String[] splittedString = lines.split("\n");
			for(String skuLine : splittedString){
				if(skuLine.endsWith(totalAmount)){
					String [] splittedSkusValues = skuLine.replaceAll("Rs", "").trim().split(" ");
					for(String value:splittedSkusValues){
						value = value.trim();
						if((!value.equals("")||!value.isEmpty()) /*&& value.equalsIgnoreCase(skuValues)*/){
							//log.info(value+" "+skuValues);
							finalValues[i] = value;
							i++;
							//return true;							
						}else{
							flag = false;
							//log.info("False: "+value+" "+skuValues);
						}							
					}
					
					
				}
			}
			
		}else{
			log.info("This line is not available in this invoice");
		}
		
		for(int j=0 ;j<finalValues.length;j++){
			//System.out.println("FinalValue:"+finalValues[j]);
			if(finalValues[j].equalsIgnoreCase(skuValues[j])){
				flag = true;
				//System.out.println(finalValues[j]+" "+skuValues[j]);
			}else{
				flag = false;
				System.out.println(finalValues[j]+" "+skuValues[j]);
			}
		}
		return flag;
	}
	
	/**
	 * @param releaseId

	 * @return
	 * @throws ParseException 
	 * @throws TikaException 
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws JAXBException 
	 * @throws NumberFormatException 
	 */
	public void validateInvoiceAndShippingPartOfSellerInInvoicePDF(String releaseId) throws ParseException, IOException, SAXException, TikaException, NumberFormatException, JAXBException{
		getInvoicePdf(releaseId+"");
		List releaseLineList = omsServiceHelper.getOrderLineDBEntryforRelease(releaseId);
		HashMap<String ,Object> orderReleaseHashMap = (HashMap<String, Object>) omsServiceHelper.
				getOrderReleaseDBEntryForRelease(releaseId).get(0);
		String orderNumber = orderReleaseHashMap.get("store_order_id").toString();
		String wareHouseId = orderReleaseHashMap.get("warehouse_id").toString();
		mappedSellerAddress();
		mappedBuyerAddress();
		String shipmentAmount=null;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<releaseLineList.size();i++){
			HashMap<String ,Object> hashLineMap = (HashMap<String, Object>) releaseLineList.get(i);
			sellerId = (String) hashLineMap.get("seller_id").toString();
			lineId = (String)hashLineMap.get("id").toString();
			Map sellerAddressDetailsMap = omsServiceHelper.getSellerAddress(Long.parseLong(wareHouseId), Long.parseLong(sellerId));
			shipmentNumber = hashLineMap.get("order_release_id_fk").toString();
			invoiceNumber = "INV_"+shipmentNumber+"_"+sellerId;
			sellerAddress = sellerAddressMap.get(sellerId+"");
			sellerLabelAddress = sellerAddressMap.get(sellerId+"Label");
			tinNumber = (String) sellerAddressDetailsMap.get("tinnumber");
			uidx = (String) orderReleaseHashMap.get("login");
			buyerAddress = buyerAddressMap.get(uidx);
			buyerLabelAddress = buyerAddressMap.get(uidx+"Label");
			skuId = hashLineMap.get("sku_id").toString();
			HashMap<Object,String> hashMapWms = (HashMap<Object, String>) getSkuCodeFromSkuId(skuId).get(0);
			skuCode = hashMapWms.get("code");
			shipmentAmount = getShipmentValueForRelease(releaseId+"", sellerId);
			//Get Line Level Details
			quantity = hashLineMap.get("quantity").toString();
			size = hashMapWms.get("size");
			unitPrice =hashLineMap.get("unit_price").toString();
			discount = hashLineMap.get("discount").toString();
			taxableAmount = (String) omsServiceHelper.getOrderLineAdditionalInfoDBEntry(lineId, "GOVT_TAXABLE_AMOUNT").get("value");
			vatOrCst = (String) omsServiceHelper.getOrderLineAdditionalInfoDBEntry(lineId, "GOVT_TAX_RATE").get("value")+"%";
			taxAmount = (String) omsServiceHelper.getOrderLineAdditionalInfoDBEntry(lineId, "GOVT_TAX_AMOUNT").get("value");
			totalAmount = df.format((Double.parseDouble(taxableAmount)+Double.parseDouble(taxAmount)));
			String additionalChargeSellerId = (String) omsServiceHelper.getOrderReleaseAdditionalInfoDBEntry(releaseId, "ADDITIONAL_CHARGES_SELLER_ID").get("value");
			if(additionalChargeSellerId.equalsIgnoreCase(sellerId)){
				 otherCharges = ""+(Double.parseDouble(""+orderReleaseHashMap.get("shipping_charge"))+
						 Double.parseDouble(""+orderReleaseHashMap.get("gift_charge"))); 
			}else{
				otherCharges ="0.00";
			}
			String [] expectedLineValues = new String[]{quantity,size,unitPrice,otherCharges,
					discount,taxableAmount,vatOrCst,taxAmount,totalAmount};
			
			//log.info(quantity+" "+size+" "+unitPrice);
			Assert.assertTrue(validateLinesInPDF(sellerId,skuCode,expectedLineValues,totalAmount),"Values are not correct in Invoice PDF");
			log.info("Line Values are correct!!!.");
			
			validateInvoiceNumberInInvoice(sellerId,invoiceNumber);
			validateShipmentNumberInInvoice(sellerId,shipmentNumber);
			validateOrderNumberInInvoice(sellerId,orderNumber);
			validateVATandTINNumberInInvoice(sellerId, tinNumber);
			SoftAssert sft = new SoftAssert();
			sft.assertEquals(checkHowManyTimesStringAppear(invoiceDetailsForParticularSeller,"VAT/ TIN Number: "+tinNumber), 1,"String appears more than expected.");
			sft.assertTrue(validateInvoicePortion(sellerId,sellerAddress),"Seller Address is missing in Invoice PDF for "+sellerId);
			sft.assertTrue(validateInvoicePortion(sellerId,buyerAddress),"Buyer Address is missing in Invoice PDF for "+sellerId);
			sft.assertTrue(validateShipmentValueInInvoice(sellerId, shipmentAmount),"Shipment Value is incorrect");
			sft.assertTrue(validateAmountPaidInInvoice(sellerId, shipmentAmount),"Amount to be Paid Value is incorrect");
			
			//Validate Shipping Label Portion
			sft.assertTrue(validateShippingLabelPartOfInvoice(sellerId,sellerLabelAddress),
					"Seller Address is missing in Shipping Label for seller "+sellerId);
			sft.assertTrue(validateShippingLabelPartOfInvoice(sellerId,buyerLabelAddress),
					"Buyer Address is missing in Shipping Label");
			sft.assertTrue(validateShippingLabelPartOfInvoice(sellerId,invoiceNumber),
					"Invoice Number is missing in Shipping Label");
			sft.assertTrue(validateShippingLabelPartOfInvoice(sellerId,"VAT/ TIN Number: "+tinNumber),
					"Seller Id is missing in Shipping Label");
			sft.assertAll();
		}

	}
	
	/**
	 * @param sourceString
	 * @param findStr
	 * @return
	 */
	public int checkHowManyTimesStringAppear(String sourceString,String findStr){
		int count = StringUtils.countMatches(sourceString, findStr);
		System.out.println(findStr+" appears "+count+" times!!!");
		return count;
	}
	
	/**
	 * @param releaseId
	 * @return
	 */
	public List getDataFromLineForThatRelease(String releaseId){
		List releaseList = omsServiceHelper.getOrderLineDBEntryforRelease(releaseId);
		//HashMap<String ,String> hashMap = (HashMap<String, String>) releaseList.get(0);
		return releaseList;
	}
	
	/**
	 * @param skuId
	 * @return
	 */
	public List getSkuCodeFromSkuId(String skuId){

        List resultSet = null;
        String query2 = "select * from mk_skus where id = '"+skuId+"';";
        resultSet = DBUtilities.exSelectQuery(query2, "myntra_wms");
        //row = (HashMap<String, Object>) resultSet.get(0);

        return resultSet;
	}
	
	/**
	 * @param releaseId
	 * @param sellerid
	 * @return
	 */
	public String getFinalAmountFromLine(String releaseId,String sellerid){

        List resultSet = null;
        String query = "select sum(final_amount) as final_amount from order_line where"
        		+ " order_release_id_fk='"+releaseId+"' and seller_id='"+sellerid+"';";
        resultSet = DBUtilities.exSelectQuery(query, "myntra_oms");
        HashMap<String, Object> row = (HashMap<String, Object>) resultSet.get(0);
        return row.get("final_amount").toString();
	}
	
	public String getShipmentValueForRelease(String releaseId,String sellerid){

        List resultSet = null;
        String query = "select CAST(sum(`value`) as DECIMAL(9,2)) as shipmentValue from order_line_additional_info olai, order_line ol " 
                        +"where ol.id=olai.order_line_id_fk and order_release_id_fk in ('"+releaseId+"') and seller_id='"+sellerid+"' and `key` in ('GOVT_TAX_AMOUNT','GOVT_TAXABLE_AMOUNT') and status_code not in ('IC');";
        resultSet = DBUtilities.exSelectQuery(query, "myntra_oms");
        HashMap<String, Object> row = (HashMap<String, Object>) resultSet.get(0);
        return row.get("shipmentValue").toString();
	}
	
	public void validateDocumentNumberInPFD(String releaseId) throws IOException, SAXException, TikaException, JAXBException{
		getInvoicePdf(""+releaseId);
		int warehouseId = omsServiceHelper.getOrderReleaseEntry(releaseId).getWarehouseId();
		List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(releaseId);
		for(OrderLineEntry orderLineEntry:orderLineEntries){
			long sellerId = orderLineEntry.getSellerId();
			Map<String,String> hm = omsServiceHelper.getSellerAddress(warehouseId, sellerId);
			log.info("Tin number: "+ hm.get("tinnumber"));
			String tinNumber = hm.get("tinnumber").toString();
			invoiceForParticularSeller(""+sellerId);
			Assert.assertTrue(invoiceDetailsForParticularSeller.contains(tinNumber),"Tin number "+tinNumber+" should be available in invoice");
			//getTinNumber from DB
			HashMap<String, Object> kycDocumentValue = omsServiceHelper.getKYCDocumentForSellerWarehouse(sellerId, warehouseId, "TIN");
			String tinNumberFromDB = kycDocumentValue.get("document_number").toString();
			log.info("Tin Number from DB: "+tinNumberFromDB);
			Assert.assertEquals(tinNumber, tinNumberFromDB,"Tin number should be same returened from seller and present in DB. From DB: "+tinNumberFromDB+" From Seller: "+tinNumberFromDB);
		}
		
	}

}
