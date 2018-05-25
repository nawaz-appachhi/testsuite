package com.myntra.apiTests.inbound.response.jabong;

public class Status {
	
	 private String statusCode;

	    private String statusType;

	    private String statusMessage;

	    public String getStatusCode ()
	    {
	        return statusCode;
	    }

	    public void setStatusCode (String statusCode)
	    {
	        this.statusCode = statusCode;
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
	        return "ClassPojo [statusCode = "+statusCode+", statusType = "+statusType+", statusMessage = "+statusMessage+"]";
	    }

}
