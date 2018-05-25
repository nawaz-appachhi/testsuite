package com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses;

/**
 * Created by 300000929 on 25/01/17.
 */
public class JobTrackerErrorFileResponse {

    private Status status;

    private JobTrackerErrorFileResponseData[] data;

    public JobTrackerErrorFileResponse() {

    }

    public Status getStatus ()
    {
        return status;
    }

    public void setStatus (Status status)
    {
        this.status = status;
    }

    public JobTrackerErrorFileResponseData[] getData ()
    {
        return data;
    }

    public void setData (JobTrackerErrorFileResponseData[] data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "JobTrackerErrorFileResponse [status = "+status+", data = "+data+"]";
    }
}
