package com.myntra.apiTests.inbound.response;

public class Status {
	private int statusCode;
    private String statusMessage;
    private String statusType;

    
    
    public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	
	 @Override
	    public String toString()
	    {
	        return "ClassPojo [statusCode = "+statusCode+", statusType = "+statusType+", statusMessage = "+statusMessage+"]";
	    }
	

}
