package com.myntra.apiTests.common;



/**
 * @author ashish.bajpai
 * @Organization Myntra Designs Pvt Ltd
 * @Description Enum to deal with kind of request/responses with webservices
 */
public enum APINAME
{
	//Inbound SPOT
	
	TESTSAMPLEJSON,
	CREATESPOTDATASET,
	CREATECOMPETITORDATASET,
	FILTERMYNTRASEARCHDATA,
	SKUCOUNTEXCEED50,
		GETSTYLEDATAWITHSTYLEID,
		GETCLUSTERDATA,
		UPLOADBIDATA,
		UPLOADPROCESSEDDATA,
		FETCHBIDATA,
		PROCESSBIDATA,
		PRODUCTSMISSINGSKUS,
		ACTIVEPRODUCTSINMYNTRA,
		COUNTOFPRODUCTSMISSINGMINMANDTORYFIELDS,
		COUNTOFILLFORMEDDOCS,
		PRODUCTSHAVING_RDF_ROS_FORECASTS,
		PRODUCTSHAVINGRDFMISSINGROSFORECASTS,
		PRODUCTSMISSINGVENDORINFO,
		PRODUCTSHAVINGMARGINGREATERTHAN100,
		PRODUCTSMISSINGPRODUCTSTREAMATTRIBUTES,
		PRODUCTSHAVINGCLUSTERINFO,
		PRODUCTSHAVINGAGGMETRICSMISSINGTIMESERIES,
		PRODUCTSHAVINGALLMANDATEATTRIBUTES,
		PRODUCTSMISSINGSTYLEIDS,
		PRODUCTSMISSINGSTYLEATTRIBUTESFORMYNTRA,
		PRODUCTSMISSINGVENDORCOLOURATTRIBUTE,
		PRODUCTSHAVINGAGGMETRICSMISSINGSTYLEATTRIBUTES,
		PRODUCTSHAVINGROSLESSTHANOREQUALTOZERO,
		PRODUCTSHAVINGRDFFORECASTINES,
		PRODUCTSHAVINGSAMEMRPANDPURCHASEPRICE,
		PRODUCTSHAVINGSAMEMRPANDASP,

		SPOTNODEFETCHDATA,
		SPOTNODEFILTERSEARCHDATA,
		PRODUCTSHAVINGSTYLEATTRIBUTESOURCEMISSINGSTYLEATTRIBUTE,
		PRODUCTSHAVINGDAYSLIVEATTRIBUTE,
		PRODUCTSHAVINGSAMESTYLEIDBUTDIFFFDBID,
		TOTALCOUNTOFTHEPRODUCTSINES,		
		PRODUCTHAVINGGOLDENINPUTDATAINES,
		NEWUSERFREESHIPPING,


	
	
	STYLEIDS2,
	MARKQUESTIONCOMMENTASSPAM,
	UNMARKQUESTIONCOMMENTASSPAM,
	MARKANSWERCOMMENTASSPAM,
	UNMARKANSWERCOMMENTASSPAM,

	GETPERSONALIZEDDATA,
	//wms ro
	ADDRO,
	ITEMCHECKIN,
	addsinglePIsku,
	
	
	//LGA_LISTS
	GETNEXTFEEDS,
	GETHOTLISTANDROID,
	GETHOTLISTIOS,

	
	//SHORTEN_URL
	CREATESHORTURL,
	CREATESHORTURLFROMQUERYPARAMS,
	GETURLFROMSHORTURLCODE,
	//markitemQCPass,
	rtsscan,

	
	// Search service
	GETQUERY,
	GETQUERYGET,
	FILTEREDSEARCH,
	AUTOSUGGESTAPIGET,
	LANDINGPAGE,
	PARAMETERIZEDPAGE,
	PARAMETERIZEDPAGEKEY,
	STYLES,
	AUTOSUGGEST,
	CURATEDSEARCHLISTBYPARAMPAGEID,
	CURATEDSEARCHLISTBYPARAMPAGEKEY,
	SAVEORUPDATEPARAMPAGE,
	ADDNEWLANDINGPAGE,
	UPDATEEXISTINGLANDINGPAGE,
	GETDISCOUNTBYID,
	GETALLDISCOUNTS,
	CREATEFLATDISCOUNT,
	CREATEFLATCONDITIONAL,
	CREATEFREEITEMDISCOUNT,
	CREATEBUYXGETYDISCOUNT,
	CREATECARTLEVELDISCOUNT,
	UPDATEDISCOUNT,
	AVAILABLEINVENTORY,
	GETCART,
	ADDITEMSTOCART,
	REINDEXONESTYLEIDPOST,
	GETALLTOPNAV,
	GETSTYLEDETAILS,
	REASSIGNWAREHOUSE,
	CHECKSERVICABILITY,
	CHECKSERVICABILITYPUTCALL,
	INVALIDATECACHE,
	RECACHE,
	INVALIDATECACHEALL,
	TRENDS,
	CMSPREORDER,
	GETSELLERCONFIG,

	GETWINDOWSPHONETOPNAV,
	GETANDROIDPHONETOPNAV,
	GETIOSPHONETOPNAV,
	GETMOBILEWEBTOPNAV,

	//ContactUs
	GETCONTACTUSOPTIONS,
	SENDQUERY,
	CONTACTUSINCIDENT,

	//pps
	PAYNOW,
	GETPAYMENTPLAN,
	PAYNOWCOD,
	PAYNOWCODLOYALTY,
	PAYNOWCODCASHBACK,
	PAYNOWCODLOYALTYCASHBACK,
	PAYNOWFORM, 
	PGRESPONSE,	
	PAYUPGRESPONSE,
	GETPAYMENTPAGE,
	GETCARTPAGE,
	GETADDRESSPAGE,
	POSTADDRESSPAGE,
	ORDERCANCEL,
	ORDERRETURN,
	CODORDERRETURN,
	EXCHANGEORDER,

	ORDERCANCELV2,
	ORDERRETURNV2,
	CODORDERRETURNV2,
	
	FETCHALLTESTCASES,
	FETCHTESTCASESWITHSECTION,
	UPDATETESTCASE,
	FETCHTESTCASE,

	
	// Loyality APIS Begin
	CREATELOYALITYGROUP,
	DELETELOYALITYGROUP,
	DELETELOYALITYGROUPBYNAME,
	INDEXSTYLELISTLOYALITYGROUP,
	CREDITLOYALITYPOINTS,
	DEBITLOYALITYPOINTS,
	TRANSACTIONHISTORYINACTIVE,
	ACCOUNTBALANCEV2,
	TRANSACTIONHISTORYACTIVE,
	ACCOUNTINFOLOYALITY,
	CREDITLOYALITYPOINTSV2,
	DEBITLOYALITYPOINTSV2,
	TRANSACTIONHISTORYINACTIVEV2,
	TRANSACTIONHISTORYACTIVEV2,
	ACCOUNTINFOLOYALITYV2,
	VOIDV2,
	REFUNDV2,
	REFERRALCREDITV2,
	CREDITLOYALITYORDERCONFIRMATION,
	CREDITLOYALITYITEMCANCELLATION,
	DEBITLOYALITYORDERUSAGE,
	DEBITLOYALITYITEMCANCELLATION,
	ACTIVATELOYALITYPOINTSFORORDER,
	MYMYNTRAACCOUNTINFO,
	ACCEPTTERMSANDCONDITIONS,
	LOYALITYCONFIGURATION,
	GETKEYNAME,
	COMPUTETIERINFO,
	PROCESSUSERSTIERS,
	PROCESSORDERSACTIVATEPOINTS,
	BULKDEBIT,
	BULKCREDIT,

	// CRM APIs
	CUSTOMERPROFILE,
	RETURN,
	COUPON,
	COMMENT,
	EXCHANGE,
	PAYMENT,
	ONHOLD,
	RTO,

	// CRM ERP APIs
	ORDER,
	RETURNORDER,
	CASHBACK,
	DELIVERYSTAFF,
	PAYMENTLOG,

	//CRM RIGHTNOW
	DRISHTIIVRORDERSTATUS,
	CREATECUSTOMERPROFILE,
	GETNEFTBANKACCOUNT,
	RETURNDETAILS,
	SAVENEFTBANKACCOUNT,
	REFUNDENTRY,
	GETTOKEN,
	// Loyality APIS Ends
	KEYVALUEFETCHALL,
	GETACTIVENOTIFICATIONSCOUNT,
	GETACTIVENOTIFICATIONS,
	CREATENOTIFICATIONSINGLEUSER,
	CREATENOTIFICATIONSETOFUSER,
	CREATEMASTERNOTIFICATION,
	GETPORTALGROUPS,
	GETNOTIFICATIONTYPES,
	GETDETAILSMASTERNOTIFICATION,
	SEARCHMASTERNOTIFICATION,
	SEARCHMASTERNOTIFICATIONWITHSTET,
	DELETENOTIFICATIONS,
	MARKASREAD,
	CREATENOTIFICATIONFORUSER,
	GETNOTIFICATIONS,

	// Checkout services
	GETALLADDRESS,
	FETCHSINGLEADDRESS,
	LISTALLADDRESSWITHSERVICEABILITY,
	PINCODESERVICEABILITYADDRESS,
	OPERATIONFETCHCART,
	OPERATIONFETCHCARTV2,
	OPERATIONFETCHWISHLIST,
	GETMYNTCREDIT,
	APPLYMYNTCREDIT,
	APPLYCOUPON,
	FETCHALLCOUPONS,
	ADDNEWADDRESS,
	UPDATEADDRESS,
	DELETEADDRESS,
	CLEARCART,
	ADDGIFTWRAPANDMESSAGE,
	REMOVEGIFTWRAPANDMESSAGE,
	REMOVEMYNTCREDIT,
	REMOVECOUPON,
	ADDITEMTOCART,
	ADDITEMTOCARTV2,
	UPDATEITEMINCART,
	UPDATEITEMINCARTV2,
	REMOVEITEMFROMCART,
	REMOVEITEMFROMCARTV2,
	CHANGEFREEGIFT,
	MOVETOWISHLIST,
	OPERATIONCUSTOMIZE,
	ADDITEMTOWISHLISTUSINGSKUID,
	ADDITEMTOWISHLISTUSINGSTYLEID,
	REMOVEITEMFROMWISHLIST,
	UPDATEITEMINWISHLIST,
	MOVEITEMTOCART,
	OPTTRYANDBUY,
	FETCHCARTCONTEXT,
	ADDEGIFTCARDTOCART,
	UPDATEEGIFTCARDTOCART,
	GETEGIFTCARDPAYMENTPAGE,
	GETCONFIG,
	UPDATECONFIG,
	PINCODESERVICEABILITY,
	
	// For IDPServices
	FBLOGIN,
	FORGOTPASSWORD,
	RESETPASSWORD,
	GETCAPTCHAREQUIRED,
	SIGNIN,
	SIGNOUT,
	SIGNUP,
	IDPMOBILESIGNIN,

	// Mobile App Services
	MOBILEAPPSEARCHGETFIRSTPAGE,
	MOBILEAPPSEARCHAPPLYFILTER,
	MOBILEAPPSEARCHGETNEXTPAGE,
	MOBILEAPPSEARCHSORTBY,
	MOBILEAPPSEARCHWITHPRESETFILTERS,
	MOBILEAPPGETSTYLEBYID,
	MOBILEAPPGETNAV,
	MOBILEAPPSEARCHDATAQUERY,
	MOBILEAPPSEARCHDATAAPPLYONEFILTER,
	MOBILEAPPSEARCHDATAAPPLYTWOFILTERS,
	MOBILEAPPSEARCHDATAAPPLYALLFILTERS,
	MOBILEAPPSEARCHDATASORTBY,

	// Style Service APIs
	GETSTYLEDATAGET,
	GETSTYLEDATAPOST,
	GETPDPDATAGET,
	GETPDPDATAPOST,
	GETPDPDATABYSKUID,
	DOSTYLEINDEXBYSTYLEID,
	DOSTYLEINDEXSKUS,
	DOSTYLEINDEXPOST,
	DOSTYLEINDEXSKUSPOST,
	FULLREINDEXING,
	GETPDPDATAFORSINGLESTYLEWITHIMAGE,
	DOINVALIDATESTYLES,
	SKUCODETOSTYLEID,
	
	//Maximus
	GETPDP,

	// Mobile Web IDP
	MOBILEIDPLOGIN,

	//Cashback pps Service

	DEBITCASHBACKPPS,
	DEBITCHCKSUMRESPONSE,
	CASHBACKPPSCHECKSUMRESP,
	//here
	REFUNDCASHBACKCHECKSUMRESP,
	CHECKSUMDEBITCASHBACK,
	REFUNDCASHBACKPPS,
	CHECKSUMREFUNDCASHBACK,
	CHECKSUMVOIDCASHBACK,
	VOIDCASHBACKTRANSACTION,
	GETSTATUSCASHBACK,

	
	//PI/PO
		ADDPI,ADDPISKUS,SENDFORPLANNERAPPROVAL,SENDFORPIAPPROVAL,SENDFORPOAPPROVAL,APPROVEPO,SPLITPI,ADDLOT,UPDATELOT,
		ADDINVOICE,UPDATEINVOICE,ADDINVOICESKUS,
	
	//Loyalty PPS Service
	USERINFO,
	DEBITLOYALTY,
	GETSTATUS,
	REFUNDLOYALTY,
	VOIDTRANSACTION,
	CHECKSUMREFUNDDEBIT,
	CHECKSUMVOID,
	CHECKSUMREFUND,
	CHECKSUMSTATUS,


	// AB test
	GETABTEST,
	GETABTESTFORGIVENLIST,
	GETALLABTESTS,
	GETALLABTESTNAMES,
	GETALLABTESTVARIANTS,
	CREATENEWABTEST,
	GETVARIANTOFGIVENABTEST,
	GETVARIANTOFGIVENABTESTWITHFILTER,
	GETVARIANTOFGIVENABTESTWITHSEGMENT,
	GETVARIANTOFGIVENABTESTWITHABTEST,
	GETVARIANTSOFALLACTIVEABTEST,
	GETVARIANTSOFALLACTIVEABTESTWITHFILTER,
	GETVARIANTSOFALLACTIVEABTESTWITHSEGMENT,
	GETVARIANTSOFALLACTIVEABTESTWITHABTEST,
	DELETEEXISTINGABTEST,
	REDEEMGIFTCARDS,
	GETGIFTCARDS,
	UPDATEEXISTINGABTEST,
	
	// IP Location group
	GETLOCATIONGROUP,
	GETLOCATIONGROUPBYID,
	GETLOCATIONBYIP,
	GETLOCATIONGROUPBYIP,
	CREATELOCATIONGROUP,
	DELETELOCATIONGROUP,

	// Key-value pair service
	GETKEYVALUE,
	GETKEYVALUEFORGIVENLIST,
	GETALLKEYVALUEPAIR,
	GETLOOKBOOKBYID,
	CREATENEWFEATUREKEYVALUEPAIR,
	DELETEFEATUREKEYVALUEPAIR,
	UPDATEFEATUREKEYVALUEPAIR,
	UPDATELISTOFFEATUREKEYVALUEPAIR,
	GETWIDGETKEYVALUE,
	WIDGETGETKEYVALUE,
	GETALLWIDGETKEYVALUEPAIR,
	GETWIDGETKEYVALUEFORGIVENLIST,
	CREATENEWWIDGETKEYVALUEPAIR,
	UPDATEWIDGETKEYVALUEPAIR,
	UPDATELISTOFWIDGETKEYVALUEPAIR,

	// Myntcash/cashback services
	CHECKBALANCE,
	CHECKTRANSACTIONLOGSOFUSER,

	// Page configurator
	GETPAGETYPE,
	GETALLPAGEVARIANTS,
	GETALLPAGEVERSIONS,
	GETALLINFOGIVENPAGETYPEANDPAGEURL,
	GETALLPAGEVERSIONSASSOCIATEDWITHWIDGETID,
	GETALLWIDGETSASSOCIATEDWITHPAGEVERSION,
	GETALLWIDGETS,
	// GETWIDGETKEYVALUE,
	CREATENEWPAGEVERSION,
	CREATENEWPAGETYPE,
	CREATENEWPAGEVARIANT,
	MAPPAGEVARIANTTOPAGEVERSION,
	MAPPAGETYPETOPAGEVERSION,
	GETCURRENTVERSIONFORPAGETYPEANDVARIANT,
	CREATEPAGEVERSIONWITHWIDGETDATA,
	CREATEUPDATELISTOFWIDGETS,
	GETALLVARIANTSASSOCIATEDWITHPAGETYPE,
	GETPAGEVERSION,
	GETPAGEMAPPINGVERSION,
	GETPAGEMAPPAGE,
	GETPAGEMAP,
	DELETEPAGEVERSION,
	UPDATEPAGEVERSION,
	MAPWIDGETTOVERSION,
	INVALIDCACHE,
	// GETWIDGETKEYVALUE,

	// Top-Nav
	GETTOPNAV,

	// Banner Service
	GETPERSONALIZEDBANNER,

	// CMS Service
	GETCONTENTBYID,
	FILTEREDCONTENTSEARCH,
	RELATEDCONTENTSEARCH,
	CLEARCONTENTCACHE,
	CLEARALLCONTENTCACHE,
	CLEARRELATEDCONTENTCACHE,
	CLEARALLRELATEDCONTENTCACHE,
	DEBITCASHBACK,
	DEBITCASHBACKOMS,
	CREDITCASHBACK,
	CREDITCASHBACKOMS,
	GETPRODUCTDETAILSFROMCMS,
	GETLOOKBOOKBYIDCMS,
	CREATECONTENTUSINGBODY,
	UPDATECONTENTUSINGBODY,

	// Catalog service
	GETPRODUCTBYID,
	SEARCHFORPRODUCTBYCRITERIA,
	CLEARCACHEFORPRODUCT,
	CLEARCACHEFORALLPRODUCT,
	CLEARCACHEFORLISTOFPRODUCT,
	GETSIZECHARTOFPRODUCTBYID,
	FINDBYLISTOFIDS,

	// Session Service
	GETSESSION,
	UPDATESESSION,
	UPDATESESSIONCDATA,
	CREATESESSION,

	// Payment Service
	SAVEBANKDETAILS,
	GETBANKDETAILSWITHACCOUNTID,
	GETBANKDETAILSWITHOUTACCOUNTID,
	UPDATEBANKNAME,
	UPDATEBRANCHNAME,
	UPDATEACCOUNTTYPE,
	UPDATEACCOUNTNUMBER,
	UPDATEACCOUNTNAME,
	UPDATEIFSCCODE,
	DELETEBANK,
	REFUNDAPI,
	GETSAVEDCARDS,
	GETSAVEDCARD,
	ADDSAVEDCARD,
	UPDATESAVEDCARD,
	DELETESAVEDCARD,

	// Admin order status
	UPDATEORDERSTATUSTOSHIP,
	UPDATEORDERSTATUSTODELIVER,
	UPDATEORDERSTATUSTOQADONE,
	UPDATEORDERSTATUSTOWP,
	UPDATEORDERSTATUSTOCANCELLED,
	AUTOSUGGESTGET,

	//Address score APIs
	SCORE,
	
	//mynt APIs
	MYNTAPISIGNIN,
	MYNTAPISIGNUP,
	MYNTAPIPINCODE,
	MYNTAPISUBSCRIBE,
	MYNTAPIUSERGET,
	MYNTAPIUSERSTATUS,
	MYNTAPIBOTS,
	//Taskey
	TASKEYLOGIN,
	//Webhook
	WEBHOOK,
	WEBHOOKPERSONAL,
	WEBHOOKPERSONALERROR,
	WEBHOOKPERSONALSUCCESS,
	WEBHOOKSLACKCHANNELCREATE,
	//ODIN
	CURRENTONCALL,
	//AddressFg
	GETADDRESSSCORE,
	// Dev Apis
	DEVAPISIGNIN,
	DEVAPISIGNUP,
	DEVAPIREFRESH,
	DEVAPILOGOUT,
	DEVAPIFACEBOOK,
	DEVAPIFORGOTPASSWORD,
	DEVAPISTYLE,
	DEVAPISTYLEWITHWIDTH,
	DEVAPISTYLEOFFERS,
	DEVAPINAV,
	DEVAPISEARCH,
	DEVAPISEARCHWITHFACETS,
	DEVAPISTYLESERVICABILITY,
	DEVAPIADDTOCART,
	DEVAPIGETCART,
	DEVAPIADDTOWISHLIST,
	DEVAPIGETWISHLIST,
	DEVAPIGETALLNOTIFICATIONS,
	DEVAPIGETNOTIFICATIONWITHBATCH,
	DEVAPINOTIFICATIONMARKASREAD,
	DEVAPIGETALLNOTIFICATIONSCOUNT,
	DEVAPIGETNOTIFICATIONNEXT,
	DEVAPISAVEPROFILEINFO,
	UPDATEPROFILE,
	GETPROFILEINFO,
	CITYLIST,
	LIKEPRODUCTS,
	LIKESUMMERY,
	UNLIKEPRODUCTS,
	LIKELIST,
	LIKESCOUNT,
	DEVAPINAVV2,
	DEVAPICHECKPPIDAVAILABILITY,
	DEVAPIGETANDROIDNAVLEFT,
	DEVAPIGETANDROIDNAVRIGHTUSER,
	DEVAPIGETANDROIDNAVRIGHTGUEST,
	DEVAPIV2SEARCH,
	DEVAPISEARCHOFFERS,
	
	
	//DEVAPI LOOKS

	DEVAPIGETALLSTICKERS,
	DEVAPIGETLOOKDETAILS,
	DEVAPIGETTHISUSERLOOKS,
	DEVAPICREATELOOK,
	DEVAPIUPDATELOOK,
	DEVAPIGETLOOKSBYOCCASION,
	DEVAPIGETLOOKSBYREMYX,
	DEVAPIGETALLOCCASIONS,
	
	//Hallmark Services - Dev Api Endpoints
	DEVAPIHALLMARKSAVEONBOARDINGDATA,
	DEVAPIHALLMARKGETONBOARDINGDATA,

	// Fasion corner destination
	// fc-user
	FCCREATEUSER,
	FCGETUSER,
	FCUPDATEEXISTINGUSER,
	FCADDQUESTIONTOUSER,
	FCADDTAGTOUSER,
	FCADDANSWER,
	FCUPDATEQUESTION,
	FCGETANSWERSUMMARY,
	FCGETQUESTIONSUMMARY,
	FCUPDATEANSWER,
	FCADDTAGTOUSERVIAUSER,
	FCDELETEEXISTINGUSER,


	// fc-question
	FCQUESTIONADDTAGTOQUESTION,

	// fc-outfit
	FCOUTFITCREATESTYLE,
	FCOUTFITGETSTYLEDATA,
	FCOUTFITCREATEOUTFIT,
	FCOUTFITGETOUTFIT,
	FCOUTFITUPDATEOUTFIT,
	FCOUTFITDELETEOUTFIT,
	FCOUTFITADDSTYLETOOUTFIT,
	FCOUTFITSEARCHOUTFIT,
	FCOUTFITLIKEDOUTFIT,


	// fc-comment
	FCCOMMENTADDCOMMENTTOUSERANDANS,

	// fc-answer
	FCANSWERADDTAGTOANSWER,
	FCANSWERADDLIKETOANSWER,
	FCANSWERADDTAGTOANSWERV2,
	FCANSWERADDLIKETOANSWERV2,
	FCANSWERGETANSWER,

	// Coupon
	GETALLCOUPONS,
	GETEXPIREDCOUPONS,
	FESTIVALCOUPONSFORORDER,
	COUPONUSAGEHISTORY,
	GETCOUPONSALLEXPIREDACTIVE,
	CREATEFLASHCOUPON,
	CREATEINTENTRULE,
	GETINTENTRULE,
	DELETEINTENTRULE,
	BULKUPDATE,
    COUPONDETAILSBYGROUPNAME,
    BULKUPDATECOUPONTYPE,
    BULKUPDATESTATUS,
    UPDATEBULKLOCK,
	SEARCHALLCOUPONGROUPS,
	CREATECOUPONGROUP,
	UPDATECOUPONGROUP,
	SEARCHCOUPON,
	GETBRANDEXCLUSION,
	CREATEBRANDEXCLUSION,
	DELETEBRANDEXCLUSION,
	STYLEINCLUSIONEXCLUSION,
	LOCKUSERCOUPON,
	UNLOCKUSERCOUPON,
	GETALLDISCOUNTCAP,
	CREATECOUPON,
	UPDATECOUPON,
	SORTCOUPONONCART,
	STYLEDISCOUNTCAPINSERT,
	ADDBINRANGE,
	GETBINRANGE,
	CREATECOUPONTEMPLATE,
	CREATEBULKCOUPONTEMPLATE,
	UPDATECOUPONTEMPLATE,
	UPDATEBULKCOUPONTEMPLATE,
	LISTCOUPONTEMPLATE,
	GETCOUPONTEMPLATEBYATTRIBUTE,
	UPDATEEXISTINGCOUPONTEMPLATE,
	COUPONTEMPLATEACTION,
	CREATECOUPONWITHRULE,
	APPLYMYNTCOUPON,
	MODIFYCOUPON,


	// GiftCard Api's
	GETGIFTCARDBALANCE,
	GETGIFTCARDAMOUNTFROMORDER,
	REDEEMGIFTCARDAMOUNT,
	CANCELREDEEMGIFTCARDAMOUNT,
	ORDERSTATUS,
	DEBITGIFTCARDUSER,
	REFUNDGIFTCARDUSER,
	DEBITCHECKSUMUSER,
	CHECKSUMREFUNDGIFTCARDUSER,
	GIFTVOIDUSERTRANSACTION,
	GETALLACTIVECOUPONSBALANCE,
	GETGIFTCARDADDEDTOUSERACCOUNT,
	GETGIFTCARDADDEDTOUSERACCOUNTWITHPARAM,
	RESETPIN,
	GETGCCONFIG,
	CREATEGCCONFIG,
	UPDATEGCCONFIG,
	
	// Giftcard QC apis
	GIFTCARDNEWBALANCE,
	CHECKSUMDEBITGIFTCARD,
	DEBITGIFTCARDNEW,
	REFUNDGIFTCARDQC,
	CHECKSUMREFUNDGIFTCARDQC,
	GIFTCARDPURCHASEQC,
	ADDGIFTCARDTOUSR,
	GIFTCARDRESET,
	CHECKSUMGIFTVOIDTRANSACTIONQC,
	GIFTVOIDTRANSACTIONQC,

	//Deals service
	CREATENEWDEAL,
	CREATENEWDEALWITHMULTIPLESTYLEIDSCHANNELTYPES,
	GETDEAL,
	ADDSTYLETOEXISTINGDEAL,
	UPDATESTATE,
	UPDATEVISIBILITY,
	UPDATEDEAL,
	GETALLVISIBLEDEALS,
	DELETEDEALS, DELETESTYLEIDS,
	GETALLDEALS,
	
	//Deal of the day
	CREATENEWDEALS,
	CREATENEWDEALSOLDOUT,
	CREATEMULTIPLESTYLEDEAL,
	GETDEALS,
	GETALLDEAL,
	GETALLDEALC,
	GETALLDEALE,
	GETALLDEALFE,
	USERSUBSCRIBED,
	USERSUBSCRIBEDFALSE,
	ISUSERSUBSCRIBED,
	
	

	//Market Place 
	//Score Card
	GETVENDORSCORETRENDENTRY,
	GETVENDORSCOREENTRY,
	GETPARAMETERFORCRITERIA,
	CREATENEWCRITERIA,
	CREATEDUPLICATECRITERIA,
	CREATENEWPARAMETER,
	ISVALIDCRITERIA,
	ISVALIDSCORECARD,
	GETVENDORSCORETOTAL,
	GETALLCRITERIA,
	GETALLPARAMETERS,
	GETALLSCORES,


	//Partner Portal
	GETCATEGORYHIERARCHY,
	GETCATEGORYDASHBOARD,
	GETDISCOUNTS,
	LOADVENDORDATA,
	RELOADVENDORDATA,
	GETSTYLESBYBRAND,
	GETSTYLESWITHOUTBRAND,
	GETSTYLESBYMASTERCATEGORY,
	GETSTYLESBYMASTERSUBCATEGORY,
	GETSUBCATEGORYWITHOUTMASTER,
	GETSTYLESBYARTICLETYPE,
	GETARTICLETYPEWITHOUTMASTER,
	GETSTYLESBYFILTERSTRING,
	GETSTYLESBYSTYLEIDS,
	CREATEDISCOUNTBYPARAM,
	REMOVEDISCOUNTBYPARAM,
	CREATEDISCOUNTBYSTYLEID,
	REMOVEDISCOUNTBYSTYLEID,

	//ManageFulfillment 
	DRAGCOEFFICIENT,
	VENDORAVERAGEORDER,
	GETALLBUCKET,
	FETCHALLPROPERTY,
	ADDBUCKET,
	DELETEBUCKET,
	EDITBUCKET,
	GENERATESCHEDULEDREPORT,
	COMPUTEPROPERTYEDIT,

	//Vendor Management
	FINDVENDORBYID,
	VENDORSEARCH,
	WHSEARCH,
	RETURNTERMSEARCH,
	CATEGORYMANAGERSEARCH,
	VENDORWITHBRANDANDSTYLE,
	GETJITVENDORS,
	SEARCHVENDORBRAND,
	GETVENDORTERMS,
	GETVENDORTERMSMARGINBASIS,
	GETVENDORCONTACTINFO,
	GETVENDORCONTACTPERSON,
	ADDVENDORCONTACT,
	ADDVENDORADDRESS,
	ADDVENDORCATEGORYMANAGER,
	ADDVENDORBRAND,
	ADDVENDORWAREHOUSE,
	ADDVENDOR,
	THIRDPARTYEXPORT,
	ADDVENDORBANKDETAILS,
	ADDVENDORADDITIONALDETAILS,
	ADDKYCDETAILS,
	CREATEVENDORADDRESS,
	GETVENDORCONTACTADDRESS,
	GETWAREHOUSEBYVENDORID,
	GETBANKDETAILSBYVENDORID,
	GETKYCDETAILSBYVENDORID,
	GETVENDORADDRESS,
	APPROVEVENDOR,
	DISABLEBRAND,
	DISABLEWAREHOUSE,
	DISABLEVENDORCATEGORYMANAGER,
	GETPARTYIDBYSOURCESYSTEMID,
	GETPARTYIDBYVENDORID,
	GETCONTRACTSBYSELLER,
	GETCONTRACTSBYBUYER,
	GETALLBUYERSBYSELLER,
	GETALLSELLERSBYBUYER,
	GETALLSTORESBYSELLER,
	GETALLSELLERSBYSTORES,
	GETTERMSBYBUYERANDSELLER,
	GETTERMSBYSELLERANDBUYER,
	
	
	
	//Vendor Stock Movement
	POBASESEARCH,
	GETPODETAILSBYPOID,
	GETSKUSBYPOID,
	GETPOSBYVENDORID,
	

	//Manufacturing Service
		GETAUDITREQUEST,
		UPDATEAUDITREQUEST,
		GETDEFECTS,
		GETAUDITREEQUESTEDMO,
		GRAPHMOQTY,
		GRAPHOFMOS,	

	//VMS SELLER
	CREATESELLER,
	GETALLSELLER,
	GETSELLERINFO,
	GETADDRESSBYSELLER,
	GETALLSELLERADDRESS,

	ADDBILLINGADDRESS,
	ADDSHIPPINGADDRESS,
	ADDKYC,
	ADDCATEGORYMANAGER,
	DISABLECATEGORYMANAGER,
	DISABLEPAYMENTCONFIGURATION,
	ADDPAYMENTCONFIGURATION,
	ADDSELLERWAREHOUSE,
	DISABLESELLERWAREHOUSE,
	FETCHSELLERTERMS,
	ADDSLABS,
	UPDATESLABS,
	APPROVESELLERTERM,
	REOPENSELLERTERM,
	FETCHALLSELLERITEM,
	SELLERITEMBYSELLER,
	SELLERITEMBYSELLERSKU,
	EDITSELLER,
	AddSELLERFINANCIALINFORMATION,
	ADDBRANDTOSELLER,
	ADDMULTIBRANDTOSELLER,
	SEARCHORDERWP,
	SEARCHORDERF,
	SEARCHORDERPK,
	SEARCHORDERC,
	SEARCHORDERDL,
	DISPATCHSHIPMENT,
	CATEGORYHIERARCHY,
	RELOAD,
	LISTINGBYSTYLE,
	BULKUPLOADSELLERITEM,
	INVENTORYUPDATE,
	
	GETPARTNERCONFIGURATION,
	GETINVOICEDETAILSONLYDATE,
	GETINVOICEDETAILS,
	GETINVOICEDETAILSWITHINVREF,
	BULKUPLOADINVOICESINGLE,
	BULKUPLOADINVOICE,
	CREATESELLERSPS,
    RELOADSELLERCACHE,
    
    // SPS
    LOGIN,
    SEARCHTRANSACTIONS,
    GETSELLERBYID,
	UPDATESELLERSPS,
	MARGINCACHEUPDATE,
	STYLEMARGINCACHEUPDATE,


	// RMS GatePass
	CREATEGATEPASSSTR,
	CREATEGATEPASSRTV,
	GETTOPARTY,
	GETGATEPASSTYPES,
	GETGATEPASSITEM,
	SEARCHGATEPASS,
	GETRTVTOPARTY,
	CREATEGATEPASSITEM,
	STATUSUPDATEGATEPASS,
	STATUSUPDATEGATEPASSCLOSE,
	UPDATEGATEPASSITEM,
	CREATEGATEPASSORDER,
	UPDATEGATEPASSORDER,
	

	//Unicom 
	GETITEMDETAILS,


	CREATESTYLE,


	//Warehouse search
	WAREHOUSESEARCH,
	ORDERSEARCH,
	INVOICES,
	ITEMS,
	ORDERS,
	SKUS,
	REJECTEDITEMS,
	RO,
	RGP,
	STN,
	STNITEMS,
	STATIONBINS,
	PURCHASEORDERS,
	PURCHASEINTENT,


	//Bounty API's
	BOUNTYSERVICECREATEORDER,
	BOUNTYSERVICEQUEUEORDER,
	BOUNTYSERVICEGETORDER,

	//Wallet

	WALLETBALANCE,
	MIGRATESTATUS,
	WALLETBALANCEPPS,
	TRANSACTIONSTATUS,
	DEBITPHONEPE,
	CREDITPHONEPE,
	PHONEPEBALANCE,
	PHONEPESIDECREDIT,
	PHONEPESIDEDEBIT,
	PHONEPESIDEBACKTOSOURCE,
	CREDITWALLETPPS,
	WALLETTXLOGS,
	CHECKSUMDEBITCASHBACKPPS,
	CHECKSUMCREDITCASHBACKPPS,
	CHECKSUMREFUNDCASHBACKPPS,
	CHECKSUMVOIDCASHBACKPPS,
	DEBITWALLETPPS,
	REFUNDWALLETPPS,
	VOIDWALLETPPS,
	NORMALCREDIT,
	NORMALDEBIT,
	BALANCECHECK,
	CREDITSUGGEST,
	WITHDRAWPHONEPE,
	DEBITSUGGEST,
	
	
	
	
	//OMS
	OMSWAREHOUSESEARCH,
	LINECANCELLATION,

	// New Session APIs
	CREATENEWSESSION,
	GETNEWSESSION,
	CHANGESESSIONINFO,
	SAVESESSIONINFO,

	//UPS
	GETSCORE,
	GETVENDORSCORETRENDENTITY,

	//Fashion corner LIKE/UNLIKE comment

	FCLIKEOUTFITCOMMENT,
	FCUNLIKEOUTFITCOMMENT,

	FCGIVECOMMENTFIRSTTIME,
	FCGIVECOMMENT,

	// Added for IDEA apis
	IDEAGETUSERPROFILEBYEMAIL,
	IDEAGETUSERPROFILEBYUSERID,
	IDEAADDPHONENUMBERTOUSERPROFILE,
	IDEAADDEMAILTOUSERPROFILE,
	IDEASIGNUP,
	IDEASIGNINUSINGPHONE,
	IDEASIGNINUSINGEMAIL,
	IDEAMOBILESIGNINUSINGEMAIL,
	IDEACHANGEPROFILEPASSWORD,
	IDEARESETPASSWORDBYEMAIL,
	IDEAPASSWORDRECOVERYBYEMAIL,
	IDEAUPDATEPROFILEINFO,
	IDEAMOBILESECUREREFRESH,
	CONTACTGRAPH,
	IDEACHANGEUSERSTATUS,
	IDEAEMAILTOLOGIN,
	IDEAMYNTRAINITPASSWORDRESET,

	IDEALASTPASSWORD,

	IDEASIGNOUT,LINKUSERCLIENT,UNLINKUSERCLIENT,GETPROFILEBYUIDX,

	
	//Api for Inline Content
	CREATEICADMIN,
	GETICADMIN,
	DELETEICADMIN,
	CLIENTSEARCH,
	UPDATEICADMIN,
	SEARCHICADMIN,
	
	FORMDATA,

	GETALLOBJECTID,
	GETVALUEOFOBJECT,
	UPDATEVALUEOBJECT,


	//silkroute APIs 

	CREATEORDER,
	// Discount V2
	GETALLDISCOUNTSV2,
	GETDISCOUNTFORDISCOUNTID,
	GETDSMFORSTYLES,
	GETDSMFORSTYLESV1,
	GETDSMFORSTYLESV1SINGLESTYLEID,
	GETTRADEANDDEALDISCOUNTS,
	GETRULESFROMDISCOUNTID,
	CREATEFLATDISCOUNTV2,
	CREATEFLATDISCOUNTV2MULTISTYLEID,
	CREATEFLATCONDITIONALV2,
	CREATEFLATCONDITIONALAMOUNTV2,
	CREATEFLATCONDITIONALAMOUNTWITHBUYCOUNTV2,
	CREATEFLATCONDITIONALPERCENTWITHBUYCOUNTV2,
	CREATEFREEITEMDISCOUNTV2,
	CREATEBUY1GET2DISCOUNTV2,
	CREATEBUY2GET4DISCOUNTV2,
	CREATEBUYXGETYDISCOUNTV2,
	REMOVESTYLES,
	GETCURRENTDISCOUNT,
	GETCURRENTDISCOUNTV1,
	DELETEDISCOUNTFORDISCOUNTID,
	DELETEDISCOUNTFORDISCOUNTIDF7,
	GETBULKDISCOUNT,
	UPDATEDISCOUNTV2,
	GETALLDISCOUNTFORSTYLES,
	UPDATEFLATCONDITIONALDISCOUNTV2,
	UPDATEBUY1GET2DISCOUNTV2,
	UPDATEBUYXGETYDISCOUNTV2,
	UPDATEFREEITEMDISCOUNTV2,

	//Tag Service
	PUBLISHTAGADD,
	PUBLISHTAGADDDEFAULTTTL,
	PUBLISHTAGADDBULK,
	PUBLISHTAGREMOVE,
	PUBLISHTAGREMOVEBULK,
	PUBLISHTAGREMOVEBULKTAG,
	PUBLISHTAGREMOVEALL,
	PUBLISHTAGFULLREFRESH,
	FETCHTAGWITHNAMESPACE,
	FETCHTAG,
	FETCHSTYLEWITHFULLNAMESPACE,
	FETCHSTYLE,

	//New Address APIs
	CREATENEWADDRESS,
	GETALLADDRESSFORCC,
	EDITADDRESS,

	//Lgp Services APIs
	ABTEST, DISPLAYATTRLIST,
	//IMS
	ALLOCATEINVENTORYSINGLESKU,
	ALLOCATEINVENTORYMULTIPLESKU,

	CLICKFOROFFER, BESTCOUPON,
	MAPPINGSTYLE,
	APPLYCOUPONCART,
	APPLYMYNTS,
	ISCOUPONVALID,
	GETSTYLEDATA,
	GETSTYLECOUNT,
	GETPRICINGENGINEOUTPUT,
	GETPRICINGENGINESTATUS,
	GETSTYLEDATAPAGE,
	FINDBRANDMANAGERSTYLECOLLECTIONINFOCUS,
	FINDBRANDSTYLECOLLECTIONINFOCUS,
	FINDSTYLECOLLECTIONINFOCUS,
	CREATEBRANDMANAGERSTYLECOLLECTIONINFOCUS,
	CREATEBRANDSTYLECOLLECTIONINFOCUS,
	FILTERBRANDMANAGERTDRANGE,
	CREATEBRANDMANAGERTDRANGE,
	GETTDRANGEBYPAGESIZE,
	FILTERBRANDTDRANGE,
	CREATEBRANDTDRANGE,
	FILTERSTYLETDRANGE,
	DELETETDRANGE,
	DELETECOLLECTIONINFOCUS,
	GETTDRANGE,
	FILTEREXCLUSIONS,
	CREATETDEXCLUSIONS,
	GETTDEXCLUSION,
	DELETETDEXCLUSION,
	CREATEBRANDTDEXCLUSION,
	CREATEMRPTDEXCLUSION,
	CREATEBRANDARTICLETDEXCLUSION,
	CREATEBRANDARTICLESEASONTDEXCLUSION,
	UPDATETDEXCLUSION,
	GETTDEXCLUSIONS,
	GETCOLLECTIONSINFOCUSBYPAGE,
	GETARTICLEREFERENCEDATA,
	GETBRANDREFERENCEDATA,
	GETCATEGORYREFERENCEDATA,
	GETSUBCATEGORYREFERENCEDATA,
	GETGENDERSREFERENCEDATA,
	STARTPRICINGENGINE,
	MODIFYPERCENTAGECOUPON,
	MODIFYDUALCOUPON,
	GETCOUPONDETAILS,
	MODIFYPERCENTAGEDUALCOUPON,
	FKGETINVENTORY,
	CANCELRELEASE,
	CREATEDISCOUNTOVERRIDEFORSTYLE,
	CREATEDISCOUNTOVERRIDEFORSELLER,
	CANCELORDER, REFRESHARTICLEDATA, REFRESHBRANDSDATA, REFRESHCATEGORYDATA, REFRESHGENDERSDATA, REFRESHSTYLEDATA, REFRESHSUBCATEGORYDATA, ORDERITEMASSOCIATION,
	TLPFILTER,
	TLPFILTEREOSSSORINSEASONSOR,
	TLPFILTERCATEGORYINSEASONDATA,
	PROFILE,
	GETALLDISCOUNTOVERIDES,
	CREATECFTOPUP,
	CREATEVFTOPUPFLAT,
	CREATEVFTOPUPFLATRUPEE,
	CREATEEVENT,
	GETEVENT,
	UPDATEVFEVENT,
	UPDATECFEVENT,
	EVENTACTION,
	
	VFTOPUPIDBULKAPPROVE,
	
	//atp
	updateatpinventory,
	itembulkupdate,
	updateimsinventory,

	//Knight service
	SETCODLIMIT,
	GETCODLIMIT,
	SETBLOCK,
	GETBLOCK,

	//Pricing Policy
	GETINTERNALPRICINGPOLICY,
	CREATEINTERNALPOLICY,
	CREATEINTERNALPOLICYFG,
	CREATEFEATUREGATE,
	GETDAYCONFIG,
	SETBASELINE,
	//wms

	GRN,
	BOUNTYSERVICECREATEORDERV2, BOUNTYSERVICEQUEUEORDERV2, BOUNTYSERVICEDECLINEORDERV2, IOS, SPLIT, PRICEOVERRIDE, ITEMSEARCH, ITEMSSEARCH,MARKITEMQCPASS,MARKITEMQCFAIL,

	CREATEUSERROLE,

	DEVAPIREFERRALGETCODE,
	DEVAPIREFERRALGETDETAILS,
	DEVAPIREFERRALWELCOME,
	DEVAPIREFERRALGETOTP,
	DEVAPIREFERRALVERIFYREFERCODE_MAGASIN,
	DEVAPIREFERRALVERIFYREFERCODE,

	GETALLSTICKERS,
	GETLOOKDETAILS,
	CREATELOOK,
	UPDATELOOK,
	GETALLOCCASIONS,
	GETLOOKSBYOCCASION,
	GETLOOKSBYREMYX,

	
	ACTION,
	BULKACTIONS,
	FOLLOW,
	UNFOLLOW,
	FOLLOWALL,
	GETFEED,
	GETOBJECT,
	GETUIDX,
	GETPROFILE,
	GETPROFILEBYPPID,
	GETPRIVATEPROFILE,
	GETFOLLOWING,
	GETFOLLOWERS,
	GETINFLUENCERS,

	CREATEDEVICEDATA,

	//pnp test tools
	GETSTYLES,
	CHECKREDIS,
	//STYLE FORUM

	CREATEPOLL,
	CREATETEXTPOLL,
	CREATEIMAGEPOLL,
	CREATEPRODUCTPOLL,
	GETPOLLDETAILS,
	VOTEFORPOLL,
	UPDATEPOLL,
	GETPOLLCOMMENTS,
	COMMENTONPOLL,
	GETRELATEDPOLLS,

	CREATEQUESTION,
	UPDATEQUESTION,
	MARKQUESTIONASSPAM,
	GETANSWERSFORQUESTION,
	ANSWERAQUESTION,
	UPDATEANSWER,
	GETRELATEDQUESTIONS,
	GETUSERFEEDSWITHUIDX,

	CHECKPPIDAVAILABILITY,
	GETMYPROFILE,
	GETFEEDS,
	GETLIKELISTBYEMAIL,
	GETLIKELISTBYPPID,
	UPDATEDEVICEDATA,
	
	//Lgp Actions
	LGPACTION,
	LGPBULKACTIONS,
	LGPFOLLOW,
	LGPUNFOLLOW,
	LGPFOLLOWALL,
	LGPACTIONDELETE,
	
	//Lgp User
	LGPGETPPID,
	LGPGETPROFILE,
	LGPGETPROFILEBYPPID,
	LGPGETPRIVATEPROFILE,
	LGPUSERSSUMMARY,
	LGPUSERSDETAILS,
	LGPUSERFOLLOWING,
	LGPUSERFOLLOWERS,
	LGPUSERINFLUENCERS,
	LGPUSERCONTACTS,
	
	//Pumps Api's
	PUMPSACTION,
	
	
	RTSSCAN, INSCAN, INSCANORDERINMASTERBAG, CANADDTOSHIPMENT, SHIPMASTERBAG, RECEIVEMASTERBAG, CREATETRIP, UPDATETRIPORDER, UPDATEENDODOMETERREADING, 
	STARTTRIP, ADDORDERINTRIP, RECEIVEORDERWITHMASTERBAG, QADONE, DISPATCHORDER, CREATEMASTERBAG, BOUNTYSERVICECONFIRMORDERV2, AUTOASSIGNMENTOFORDERTOTRIP, 
	PAYNOWNETBANKING, PAYNOWCC, PAYNOWWALLET, APPLYLOYALTYPOINTS, INVOICE, EXPRESSREFUND, RMSSERVICESTATECHANGE, GETRETURNDETAILS, RETURNRECEIVED, RETURNQUALITYCHECK, 
	REMOVELOYALTYPOINTS, EDITLIST, GETUSERLISTS, GETLISTBYID, CREATELIST, MODIFYLISTACCESS, DELETELIST, DELETESTYLEFROMLISTUSINGITEMID, 
	GETFEEDOBJECTENTITY, GETPREVIOUSFEEDS, GETHOMEFEEDS,GETHOMEFEEDSQA, GETSTYLEINFO, DELETEPOLL, DELETEQUESTION, COMMENTONQUESTION, MARKCOMMENTASSPAM, GETCOMMENTSFORQUESTION, 
	UNMARKCOMMENTASSPAM, COMMENTONANSWER, LIKEANSWER, UNLIKEANSWER, FOLLOWQUESTION, UNFOLLOWQUESTION, DELETEANSWER, FEATUREQUESTION, FEATUREANSWER, UNFEATUREQUESTION, 
	UNFEATUREANSWER, FEATUREPOLL, UNFEATUREPOLL, CHECKCARDBALANCE, DEBITGIFTCARD, REFUNDGIFTCARD, GIFTVOIDTRANSACTION, TAGLIST, UNTAGLIST, DELETESTYLEFROMLIST, 

	
	ADDSTYLETOLIST, GETCURRENTTRADEDISCOUNTFROMDB, SCROLLFEEDSDOWN, UNMARKQUESTIONASSPAM, GETUSERFEEDS, PUSHORDERWMS, CHECKSUMGIFTCARD, CHECKSUMDEBITGIFT, APPLYAUTOGIFTCARD,APPLYMANUALGIFTCARD,
	CREATESTYLETDEXCLUSION,GETSTYLEPAGE, PUBLISH,GENERATETESTBANNERS,IMPORTTESTBANNERS,GETSTREAMNAV,GETSTREAMSLIDESHOW,GETSTREAM,GETNEXTSTREAM,GETPREVIOUSSTREAM, 
	CREATEINTERNALPRICINGPROMOTIONPOLICY,CREATESTYLETDRANGE,CREATEDISCOUNTOVERRIDE,APPROVEDISCOUNTOVERRIDE,INTERNALLYAPPROVEDDISCOUNTOVERRIDE,MPSPHASEOUTFLATPERCENT,MPSPHASEOUTFLATCONDITIONAL,MPSPHASEOUTFLATCONDITIONALBUYCOUNT,MPSPHASEOUTFLATRUPEE,SELLERAPPROVALDISCOUNTOVERRIDE,GETDISCOUNTOVERRIDEDETAILSBYID,UPDATESTYLETDRANGE,GETDISCOUNTOVERRIDEPAGEBYSTATUS,GETCURRENTLYFOCUSSEDBRANDS,CREATECOLLECTIONINFOCUSUSINGPAYLOAD, UPDATETDRANGESTATUS, UPDATECOLLECTIONINFOCUS, CREATETDRANGEUSINGPAYLOAD,CREATEOBJECT,PUBLISHACTION,DELETEOBJECT, GETCOLLECTIONINFOCUS,MPSPHASEOUTBUYXGETY,

	
	UPDATEUSERATTRIBUTE,GETATTRIBUTESBYUIDX,UPDATEDEVICEATTRIBUTE,GETATTRIBUTESBYDEVICEID,CAMPAIGNCREATEAD,CAMPAIGNGETAD,CAMPAIGNGETADBYID,CAMPAIGNDELETEADBYID,CAMPAIGNDELETEADBYOBJECTID,SEARCHUSERATTRIBUTES,SEARCHDEVICEATTRIBUTES,

	PUSHSTATUS,VIEWCAPPING,UPDATECAPPING,CAPPINGCOUNTER,DELETECAPPINGCOUNTER,NOTIFICATIONDBENTRY,NOTIFICATIONSTATUS,GETDEVICERECORD,SENDNOTIFICATION,


	GETBASEPRICINGENGINEOUTPUT, GETQUERYWITHFQ, AUTOSUGGESTINDEXING, CURATEDPAGECREATION,GETTIMELINECARDS,GETPREVIOUSTIMELINECARDS,FLIPKARTRETURNCREATION,FLIPKARTRETURNCOMPLETE,RMSSHIPMENTRECEIVED,
	GETONBOARDING,
	
	//DevAPISHOTS
	GETSHOTSBYOBJECTID,
	GETSHOTSBYUIDX,
	GETSHOTSBYPPID,
	GETSHOTSBYUSER,
	GETSHOTSBYOCCASION,
	GETTOPICS,
	CREATESHOTS,
	UPDATESHOTS,
	DELETESHOTS,
	SEARCHSHOTS, RECACHECASHBACK,
	DEVAPIGETPROFILECOUNT,
	DEVAPISEARCHWITHREQUESTID, DEVAPIGETAUTOSUGGEST, DEVAPIGETCARTCOUNT,
	
	//LGP SNS
	GETINFLUENCERSDEFAULT,
	GETINFLUENCERSFETCHSIZE,GETINFLUENCERSOFFSET,GETINFLUENCERSSNS,GETFOLLOWINGSNS,GETFOLLOWERSSNS,
	GETLGPABTESTS,PIBULKUPLOAD,GETTOPICSTOFOLLOWSNS,GETTOPICSAFFINITIESSNS,
	GETCONTACTSTOFOLLOWSNS,PERFORMACTIONONUSERBULKSNS,PERFORMACTIONONOBJECTBULKSNS,PERFORMACTIONONUSERSINGLESNS,
	PERFORMACTIONONOBJECTSINGLESNS,GETOTHERTIMELINECARDS,GETWHOFOROBJECTSNS,GETOBJECTBYTAGSNS,GETTAGSSNS,
	HASPERFORMEDACTIONGENERIC,
	
	////////////
	DEVAPIFEEDBACK, DEVAPIGETVISUALSEARCH, DEVAPIPOSTVISUALSEARCH, DEVAPISETUSERPRIVACY, DEVAPIPROFILECOUNTV3,

	GETCARDTYPEFILTER, CLOSEMASTERBAG, DEVAPIGETCASHBACK,CREATELGPMASTERNOTIFICATION,

	//Serviceability

	DEVAPISERVICEABILITYV1,
	DEVAPISERVICEABILITYV2,
	DEVAPISERVICEABILITYV3,
	DEVAPISERVICEABILITYV4,UNPUBLISHOBJECT,UPDATEOBJECT,SPAMOBJECT,UNSPAMOBJECT,GETPUMPSOBJECT,DELETEPUMPSOBJECT,
	
	//FreeShipping-EmailID from UIDX
	GETMAILIDFROMUIDX,
	DEVAPIGETUSERCART,
	LGPTAGSCREATETAGSINBULK,
	LGPTAGSUPDATETAG,
	LGPTAGSFETCHBYTYPEFILTERTOPIC,
	LGPTAGSFINDTAGBYNAME,
	LGPTAGSFETCHBYKEYSINBULK,
	LGPTAGSFETCHBYTYPE,
	LGPTAGSFETCHTAGBYNAMES,
	DEVAPIGETSIMILARITEMS,
	DEVAPIGETUSERCOUPONS,
	LGPTAGSFETCHBYKEY,
	//Template Service
	CREATETEMPLATE,
	DELETETEMPLATE,
	GETTEMPLATE,
	DEVAPIGETANDROIDLAYOUTS,
	//LGP - PUMPS 
	GETPUMPSOBJECTBYID,GETACTIONBYID,PUBLISHPUMPSACTIONOBJECTFOLLOW, PUBLISHPUMPSACTIONTOPICFOLLOW ,
	PUBLISHPUMPSACTION,PUBLISHPUMPSACTIONLIKE,PUBLISHPUMPSACTIONCLICK,PUBLISHPUMPSACTIONSHARE,PUBLISHPUMPSACTIONFOLLOW,UNPUBLISHPUMPSACTION,
	
	GETAEROSPIKE,GETTITAN,
	//Session-Service
	LGPCREATESESSIONXML, LGPOPTCREATESESSIONJSON,LGPGETSESSIONJSON, LGPGETSESSIONXML,LGPOPTUPDATEPARTIALSESSION,LGPOPTCHANGESESSION,LGPOPTGETSESSION,
	UPDATEUSERPRIVACY, FOLLOWCONTACTS, POSTUPDATESTREAM, TELESALESLOGIN, GETIDEAUIDX, DEVAPIGETPRODUCTLIKES, GETQUERYGETREQ, GETSTREAMQA,

	//CMS catalog service
	FINDBYID, FINDBYIDS, SEARCHFORSTYLES, SEARCHFORSTYLE, CMSSEARCHGENERIC_IN, CMSSEARCHGENERIC_EQ, CMSPRODUCTCATEGORYV2, CMSPRODUCTCATEGORY,
	SEARCHFORSTYLE_V2, CMSGETSTYLEV2, CMSGETSTYLE, CMSPUTSTYLEV2_JEANS, CMSPUTSTYLE_JEANS, CMSCHANGEARTICLETYPE_JEANS, CMSV2CHANGEARTICLETYPE_JEANS, CMSPUTSTYLEV2_TSHIRTS, CMSPUTSTYLE_TSHIRTS,
	CMSCHANGEARTICLETYPE_TSHIRTS, CMSV2CHANGEARTICLETYPE_TSHIRTS, CMSPUTSTYLEV2_JEANS_PROD, CMSPUTSTYLE_JEANS_PROD, CMSCHANGEARTICLETYPE_JEANS_PROD, CMSV2CHANGEARTICLETYPE_JEANS_PROD, CMSPUTSTYLEV2_TSHIRTS_PROD, CMSPUTSTYLE_TSHIRTS_PROD,
	CMSCHANGEARTICLETYPE_TSHIRTS_PROD, CMSV2CHANGEARTICLETYPE_TSHIRTS_PROD, CMSSEARCHGENERIC_V2_IN, CMSSEARCHGENERIC_V2_EQ, SEARCHFORSTYLES_V2, SEARCHFORSTYLE_OPER_V2, CMSCLEARCACHE, VB_SKUCREATION, VB_SKUADDITION, VB_BUNDLECREATION, CMS_ATTRIBUTETYPE,
	CMS_ATTRIBUTEVALUE, CMS_BRAND, CMS_STYLEPIM, CMSPRODCAT_PARENTDETAILS, CMSPRODCAT_GETPIMDETAILS, CMS_GLOBALATTR, CMS_COLORVARIANT, DIY_ALLAT, DIY_ALLSTUDIOS, CMS_PIMATT, CMS_PIMTEXTATT, CMS_PIMVALIDVALUE, CMS_PIMVALIDVALUE_ATT, CMS_SEARCHINPIM,
	CMS_PRODUCTTYPE, CMS_OPTIONSKU, CMS_OPTIONSKUPIM, CMS_PRODUCTSTYLE, CMS_SEARCHPIM, CMS_CLEARALLCACHE, CMS_DELCACHE, CMS_RANGE, CMS_ALL, CMS_VALIDATESTYLE, CMS_MEASUREMENT, CMS_SAFETOEXPORT, CMS_SIZEUNIFICATION, CMS_SELLERSEARCH, CMS_SELLERSTYLEDETAILS, 
	CMS_SELLERMOVETO, CMS_MANDATORYMES, CMS_NLPWHITELIST, CMS_STYLESTATUS, CMS_JOBREGISTRY, CMS_JOBTRACKER_FIND, CMS_JOBTRACKER_SEARCH, CMS_STYLEGRP_ALL, CMS_STYLEGRP_COLOR, CMS_STYLEGRP_ADDSTYLES, CMS_STYLEGRP_SEARCHBYSTYLES, CMS_STYLEGRP_DELETESTYLES, 
	CMS_STYLEGRP_MERGEGRPS, CMS_TAXONOMY_ALLMASTER, CMS_TAXONOMY_ALLSUB, CMS_TAXONOMY_PIMALLMASTER, CMS_TAXONOMY_PIMALLSUB, CMSUPDATESTYLE, CMS_LISTASPAYLOAD, PRODUCT_EXPORT_STYLETEMPLATE, PRODUCT_EXPORT_SKUTEMPLATE, PRODUCT_EXPORT_STYLE_SKU, PRODUCT_EXPORT_SIZINGTEMPLATE,
	
	//CMS DIY
	DIY_SHEETVALIDATOR, DIY_TASK, DIY_GETSTYLES, DIY_STYLE, DIY_REJECTLOT, DIY_MOVETOMANUALQC, DIY_INCURATIONUPDATEALL, DIY_INCURATIONSTYLESEARCH,
	
	//CMS HSN
	HSN_FINDAPI, HSN_VALIDATEAPI,
	
	//STYLE FORUM
	GETSTYLEFORUMUSERFEEDS,
	GETSTYLEFORUMFEEDS,
	GETSTYLEFORUMTOPICS,
	GETSTYLEFORUMPERSONALIZEDFEEDS,
	//BI
	DDPSUBMITQUERY,DDPPOLLQUERYSTATUS,DDPDOWNLOADRESULTSET,UDPREPORTS,UDPDIMENSIONS,UDPMETRICS,UDPBRANDVALUES,UDPMETRICSBYDIMENSION,UDPMETRICSBASEDONDATERANGE,
	
	DEVAPIADDTOCARTWITHSTYLEID,
	DEVAPILGPREFERRAL,
	DEVAPIGETSTYLEFROMSKUCODE,

	APIFYPDPV3, APIFYPDPV3SERVICIABILITYCHECK,
	DEVAPIGETSEARCHTRENDS, APIFYPDP, APIFYPDPSIZERECOMMENDATION, APIFYPDPSERVICIABILITYCHECK,

	//ATS
	GETOPENORDERSVALUE,
	GETOUTSTANDINGCCOD,
	GETATSUSERDETAILS,
	POSTATSDETAILS,
	GETLINKEDACCOUNTS,

	//Address MT
	GETALLADDRESSV2,
	FETCHSINGLEADDRESSV2,
	ADDNEWADDRESSV2,
	UPDATEADDRESSV2,
	DELETEADDRESSV2,
	CREATENEWADDRESSV2,
	EDITADDRESSV2,
	GETALLADDRESSFORCCV2,
	GETSINGLEADDRESSV2,
	DELETESINGLEADDRESSV2;
}
    