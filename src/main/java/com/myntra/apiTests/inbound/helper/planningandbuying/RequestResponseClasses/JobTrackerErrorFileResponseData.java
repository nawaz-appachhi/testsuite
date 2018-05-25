package com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses;

/**
 * Created by 300000929 on 25/01/17.
 */
public class JobTrackerErrorFileResponseData {
    private Long jobId;
    private String name;
    private String url;

    public JobTrackerErrorFileResponseData() {

    }

    public Long getJobId() {
        return jobId;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "JobTrackerErrorFileResponseData [jobId = "+jobId+", name = "+name+", url = "+url+" ]";
    }
}
