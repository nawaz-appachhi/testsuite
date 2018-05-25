package com.myntra.apiTests.inbound.response.ArtieService;

public class Status {
	
	 private int statusCode;

	    private String totalCount;

	    private String statusType;

	    private String statusMessage;

	   

	    public int getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}

		public String getTotalCount ()
	    {
	        return totalCount;
	    }

	    public void setTotalCount (String totalCount)
	    {
	        this.totalCount = totalCount;
	    }

	    public String getStatusType ()
	    {
	        return statusType;
	    }

	    public void setStatusType (String statusType)
	    {
	        this.statusType = statusType;
	    }

	    public String getStatusMessage ()
	    {
	        return statusMessage;
	    }

	    public void setStatusMessage (String statusMessage)
	    {
	        this.statusMessage = statusMessage;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [statusCode = "+statusCode+", totalCount = "+totalCount+", statusType = "+statusType+", statusMessage = "+statusMessage+"]";
	    }

}
