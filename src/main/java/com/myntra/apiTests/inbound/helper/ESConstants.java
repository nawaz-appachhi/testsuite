package com.myntra.apiTests.inbound.helper;

import org.elasticsearch.index.query.QueryBuilders;

public class ESConstants {
	
	public static class INDEX{
		public static final String CMS="fifaes";
		public static final String AMAZON="amazon";
		public static final String JABONG="jabong";
		public static final String SOURCE="source";
		
	}
	
	public static class SOURCE {
		public static final String _SOURCE = "_source";
		public static final String STYLE_ATTRIBUTES = "styleAttributes";
		public static final String STYLE_ATTRIBUTES_SOURCE = "styleAttributes_source";
		public static final String AGGMETRICS_TIMESERIES = "aggMetricsTimeseries";
	}
	
	public static class STYLEATTRIBUTES{
		public static final String STYLE_STATUS="styleAttributes.style_status";
		public static final String STYLE_ID="styleAttributes.style_id";
		public static final String EXTERNAL_STYLE_ID="style_id";
		public static final String JABONG="jabong";	
		public static final String AMAZON="amazon";
		public static final String ATTRS="styleAttributes.attrs";
		public static final String VENDOR_COLOUR="styleAttributes.vendor_colour";
		public static final String ARTICLE_TYPE="styleAttributes.article_type";
		public static final String BRAND="styleAttributes.brand";
		public static final String GENDER="styleAttributes.gender";
		public static final String MRP="styleAttributes.MRP";
		public static final String STYLEATTRIBUTES="styleAttributes";
		public static final String SOURCE="source";
		public static final String COMMERCIAL_TYPE="styleAttributes.commercial_type";
		public static final String SUPPLY_TYPE = "supply_type";
	}
	
	public static class AGGMETRICS{
		public static final String TOTALORDERS="aggMetrics.totalOrders";
		public static final String DAYSLIVE="aggMetrics.daysLive";
		public static final String ROS="aggMetrics.ros";
		public static final String SKU_COUNT="aggMetrics.sku_count";
		public static final String TOTQTY = "aggMetrics.totqty";
		public static final String TSR="aggMetricsTimeseries.tsr";
		public static final String AGGMETRICS="aggMetrics";
		public static final String INVENTORY = "aggMetrics.inventory";
	}
	public static class ATTR_VALUES{
		public static final String STYLE_STATUS_VAL="p";
		public static final String[] COMMERCIAL_TYPE_VAL={"sor","outright"};	
		public static final String SUPPLY_TYPE="on_hand";
		public static final String BRAND = "RoadSter";
	}
	
	public static class DEMANDFORECAST{
		public static final String RDF="demandForecast.rdf";
		public static final String ROS="demandForecast.ros";
		public static final String DEMANDFORECAST = "demandForecast";
	}
	public static class SKU{
		public static final String SKUS="skus";
		public static final String SKU_ID="skus.sku_id";
	}
	public static class THRESHOLD{
		public static final double TENPERCENT=0.1;
		public static final double FIFTYPERCENT=0.5;
	}
	public static class VENDOR_INFO{
		public static final String VENDOR_NAME="vendor_info.vendor_name";
		public static final String BASIS_OF_MARGIN="vendor_info.basis_of_margin";
		public static final String MARGIN_TYPE="vendor_info.margin_type";
		public static final String PO_TYPE="vendor_info.po_type";
		public static final String MARGIN = "vendor_info.margin";
		public static final String VENDOR_INFO = "vendor_info";
		public static final String ITEM_PURCHASE_PRICE = "vendor_info.item_purchase_price";
		public static final String LAST_MRP = "vendor_info.last_mrp";
	}
	public static class CLUSTER_INFO{
		public static final String CLUSTER_INFO="clusterInfo";
	}
	public static class TIMESERIES{
		public static final String TIMESERIES="timeseries";
	}
	public static class FDB{
		public static final String ID="fdb_id";
	}
	public static class STYLEBREAKDATES{
		public static final String STYLEBREAKDATES="styleBreakDates";
	}
	public static class PRODUCTS{
		public static final String PRODUCTSTREAMTIMESERIES="productStreamTimeseries";
		public static final String PRODUCTSTREAMMETRICS="productStreamMetrics";
	}
	public static class ESCLIENT{
		public static final String CLUSTER_NAME="cluster.name";
		public static final String CLUSTER_NAME_VAL="scminbound";
		public static final String CLIENT_TRANSPORT_SNIFF="client.transport.sniff";
	}
	

}
