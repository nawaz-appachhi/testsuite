/**
 * 
 */
package com.myntra.apiTests.erpservices;

/**
 * @author Puneet.khanna1@myntra.com
 * @since June 2016
 *
 */
public class VatTestConstants {

	// resttrict initialization and subclassing
	private VatTestConstants() {
		
	}
	// as suggested by Engineering , bumping this value as test system showed late refunds due to slowness
	public final static int WAITTIME_REFUND_INITIATE = 4;
	public final static int WAITTIME_NO_REFUND_INITIATIATE = 1;

	public final static String GURGAON_WH = "28";
	public final static String BANGALORE_WH = "36";
	public final static int SKU_GURGAON = 890847;
	public final static int SKU_BANGALORE = 890849;
	public final static int SKU_BLR_GRN = 890844;
	public final static String[] LOW_TAX_WAREHOUSE = { GURGAON_WH };
	public final static int WAITTIME_FOR_WH_ASSIGNMENT = 1;
	
	
	

}
