package com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses;

/**
 * Created by 300000929 on 16/01/17.
 */
public class CreateOIResponse {
    private Status status;

    private CreateOIResponseDate[] data;

    public CreateOIResponse() {

    }

    public Status getStatus ()
    {
        return status;
    }

    public void setStatus (Status status)
    {
        this.status = status;
    }

    public CreateOIResponseDate[] getData ()
    {
        return data;
    }

    public void setData (CreateOIResponseDate[] data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "CreateOIResponse [status = "+status+", data = "+data+"]";
    }
}
