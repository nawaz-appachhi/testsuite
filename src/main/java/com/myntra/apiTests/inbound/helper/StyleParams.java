package com.myntra.apiTests.inbound.helper;

public class StyleParams {
	
	private String ros;

    private String asp;

    public String getRos ()
    {
        return ros;
    }

    public void setRos (String ros)
    {
        this.ros = ros;
    }

    public String getAsp ()
    {
        return asp;
    }

    public void setAsp (String asp)
    {
        this.asp = asp;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ros = "+ros+", asp = "+asp+"]";
    }

}
