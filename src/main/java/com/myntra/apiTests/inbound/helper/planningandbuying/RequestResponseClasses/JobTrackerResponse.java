package com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses;

/**
 * Created by 300000929 on 16/01/17.
 */
public class JobTrackerResponse {
    private Status status;

    private JobTrackerResponseData[] data;

    public JobTrackerResponse() {

    }

    public Status getStatus ()
    {
        return status;
    }

    public void setStatus (Status status)
    {
        this.status = status;
    }

    public JobTrackerResponseData[] getData ()
    {
        return data;
    }

    public void setData (JobTrackerResponseData[] data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "JobTrackerResponse [status = "+status+", data = "+data+"]";
    }
}
