package com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses;

/**
 * Created by 300000929 on 16/01/17.
 */
public class BaseOIHeaderResponse {

    private Status status;

    private BaseOIHeaderResponseData[] data;

    public BaseOIHeaderResponse() {

    }

    public Status getStatus ()
    {
        return status;
    }

    public void setStatus (Status status)
    {
        this.status = status;
    }

    public BaseOIHeaderResponseData[] getData ()
    {
        return data;
    }

    public void setData (BaseOIHeaderResponseData[] data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "BaseOIHeaderResponse [status = "+status+", data = "+data+"]";
    }
}
