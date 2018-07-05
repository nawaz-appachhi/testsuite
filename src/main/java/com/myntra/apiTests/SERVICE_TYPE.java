package com.myntra.apiTests;

/**
 * Created by 8403 on 04/05/17.
 */
public enum SERVICE_TYPE{
    ES_CLIENT_SVC("esclient"),
    DDP_SVC("ddp"),
    ES_SVC("es"),
    SPOT_SVC("spot"),
    SPOT_CATALOG_SVC("spot_catalog"),
    SPOT_NODE_SVC("spotnode"),
    RDS_SVC("rds"),
    LMS_SVC("lms"),
    TMS_SVC("tms"),
    CSS_SVC("css"),
    IMS_SVC("ims"),
    OMS_SVC("oms"),
    ARMOR_SVC("armor"),
    TOOLS_SVC("tools"),
    WMS_SVC("wms"),
    ATP_SVC("atp"),
    PPS_SVC("pps"),
    PAYU_SVC("payu"),
    RMS_SVC("rms"),
    MPS_SVC("mps"),
    BOUNTY_SVC("bounty"),
    SILKR_SVC("silkroute"),
    SILKR_SVC_JBG("silkroute-jabong"),
    SILKJAB_SVC("silkroutejabong"),
    JROUTE("jroute"),
    RIGHT_NOW_SVC("crmservicerightnow"),
    SEARCH_SVC("search"),
    PORTAL("portal"),
    VMS("marketplacevms"),
    CART_SVC("absolut"),
    PNP_SVC("pnp"),
    LOYALTY_SVC("loyality"),
    SELLER_SVC("sellerapi"),
    VMS_SVC("marketplaceseller"),
    SELLERSERVICES("sellerservices"),
    VIM_SVC("vendoritemmaster"),
    IDEA_SVC("idea"),
    ABSOLUT_SVC("cart"),
    SESSION_SVC("session"),
    DELHIVERY("lmsdelhivery"),
    CTS("cts"),
    ORCH_SVC("orchestrator"),
    WORMS_SVC("worms"),
    ATS_SVC("ats"),
    KNIGHT_SVC("knightservice"),
    STYLE_SVC("style"),
    VENDOR_SVC("marketplacepartnerportal"),
    TAXMASTER_SVC("taxmaster"),
    TAXMASTER_JABONG_SVC("taxmaster_jabong"),
    JOBTRACKER_SVC("jobtracker"),
    OI_SVC("oi"),
    TSR_SVC("marvin"),
    TSR_RCONFIG("marvin_rconfig"),
    NOTIFICATIONS_SVC("notifications_revamp"),
    ARTIE_SVC("artieservice"),
    VENDORTERMS_SVC("vendorterms"),
    CONTRACT_SVC("contractservice"),
    IDP_SERVICE("idp"),
    CITRUS_SVC("citrus"),
    PARTNERCONNECT_SVC("partnerconnect"),
    SECURITY_SVC("securityservice"),
    WEBHOOKSLACKCHANNEL_SVC("webhookslackchannel"),
    PIM_SERVICE("pimService"),
    MANUFACTURE_SERVICE("manufactureService"),
    TERMS_SERVICE("termService"),
    CATALOG_SERVICE("catalogservice"),
    REPLENISHMENT_TENANT("replenishment_tenant"),
    TEAM_SERVICE("team_service"),
    PSC_SVC("psc"),
    SERVICEABILITY_UTIL("serviceabilityUtil"),
    BOLT_SVC("boltservices"),
    CMS_CATALOG("catalog"),
    CMS_HSN("hsn"),
    AMAZON_SVC("amazon_service"),
    MUNSHI_SVC("munshi"),
    JUGAAD("jugaad"),
    SPS_SVC("sps"),
	CHECKOUT_SVC("checkout"),
	ERPRABBITMQ("erprabbitmq"),
	INWARDASSIST("inwardassist"),
	PACKMAN_SVC("packman"),
	ADDRESS_SVC("address"),
	MANUFACTURING("manufacturing"),
	FLIPKART_SVC("flipkart"),
    SLACK("slack");
    private String svcName;

    private SERVICE_TYPE(String svcName){
        this.svcName = svcName;
    }

    @Override
    public String toString(){
        return svcName;
    }

}