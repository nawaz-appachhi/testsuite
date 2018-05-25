package com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses;

/**
 * Created by 300000929 on 16/01/17.
 */
public class BaseOIDetailsResponse {
    private Status status;

    private BaseOIDetailsResponseData[] data;

    public BaseOIDetailsResponse() {

    }

    public Status getStatus ()
    {
        return status;
    }

    public void setStatus (Status status)
    {
        this.status = status;
    }

    public BaseOIDetailsResponseData[] getData ()
    {
        return data;
    }

    public void setData (BaseOIDetailsResponseData[] data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "BaseOIDetailsResponse [status = "+status+", data = "+data+"]";
    }
}
