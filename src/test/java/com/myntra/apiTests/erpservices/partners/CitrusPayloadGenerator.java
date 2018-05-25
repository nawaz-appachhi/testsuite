package com.myntra.apiTests.erpservices.partners;
import com.myntra.lordoftherings.gandalf.APIUtilities;
	
	public class CitrusPayloadGenerator {
		private long limit;
	    private long page_number;
	    private long seller_id;
	    private String to_date;
	    private String from_date;
	
		public String getTo_date() {
			return to_date;
		}
	
		public long getLimit() {
			return limit;
		}
	
		public void setLimit(long limit) {
			this.limit = limit;
		}
	
		public long getPage_number() {
			return page_number;
		}
	
		public void setPage_number(long page_number) {
			this.page_number = page_number;
		}
	
		public long getSeller_id() {
			return seller_id;
		}
	
		public void setSeller_id(long seller_id) {
			this.seller_id = seller_id;
		}
	
		public void setTo_date(String to_date) {
			this.to_date = to_date;
		}
	
		public String getFrom_date() {
			return from_date;
		}
	
		public void setFrom_date(String from_date) {
			this.from_date = from_date;
		}
		
		@Override
	   public String toString()
	    {
	        return "ClassPojo [limit = "+limit+", page_number = "+page_number+", seller_id = "+seller_id+", to_date = "+to_date+", from_date = "+from_date+"]";
	    }
		
		public String payloadGenerator(String from_date, String to_date, long seller_id, long limit,
				long page_number) throws Exception {
			CitrusPayloadGenerator citrusPayloadGenerator=new CitrusPayloadGenerator();
			citrusPayloadGenerator.setFrom_date(from_date);
			citrusPayloadGenerator.setTo_date(to_date);
			citrusPayloadGenerator.setSeller_id(seller_id);
			citrusPayloadGenerator.setLimit(limit);
			citrusPayloadGenerator.setPage_number(page_number);
			String message = APIUtilities.getObjectToJSON(citrusPayloadGenerator);
			return message;
			
		}
	
	}


