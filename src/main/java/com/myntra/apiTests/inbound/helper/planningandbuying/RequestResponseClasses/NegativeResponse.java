package com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses;

/**
 * Created by 300000929 on 27/01/17.
 */
public class NegativeResponse {
    private Status status;

    public NegativeResponse()
    {

    }

    public Status getStatus ()
    {
        return status;
    }

    public void setStatus (Status status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "NegativeResponse [status = "+status+" ]";
    }
}
