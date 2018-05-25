package com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses;

/**
 * Created by 300000929 on 16/01/17.
 */
public class JobTrackerResponseData {

    private String resultUrl;

    private String inputFileName;

    private String status;

    private Integer completedStepCount;

    private String remark;

    private String requestJsonEntry;

    private String qualifier;

    private String lastModifiedOn;

    private String type;

    private Long id;

    private Long createdOn;

    private Long expiryTime;

    private String createdBy;

    private Integer totalStepCount;

    public JobTrackerResponseData() {

    }


    public String getResultUrl ()
    {
        return resultUrl;
    }

    public void setResultUrl (String resultUrl)
    {
        this.resultUrl = resultUrl;
    }

    public String getInputFileName ()
    {
        return inputFileName;
    }

    public void setInputFileName (String inputFileName)
    {
        this.inputFileName = inputFileName;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Integer getCompletedStepCount ()
    {
        return completedStepCount;
    }

    public void setCompletedStepCount (Integer completedStepCount)
    {
        this.completedStepCount = completedStepCount;
    }

    public String getRemark ()
    {
        return remark;
    }

    public void setRemark (String remark)
    {
        this.remark = remark;
    }

    public String getRequestJsonEntry ()
    {
        return requestJsonEntry;
    }

    public void setRequestJsonEntry (String requestJsonEntry)
    {
        this.requestJsonEntry = requestJsonEntry;
    }

    public String getQualifier ()
    {
        return qualifier;
    }

    public void setQualifier (String qualifier)
    {
        this.qualifier = qualifier;
    }

    public String getLastModifiedOn ()
    {
        return lastModifiedOn;
    }

    public void setLastModifiedOn (String lastModifiedOn)
    {
        this.lastModifiedOn = lastModifiedOn;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public Long getId ()
    {
        return id;
    }

    public void setId (Long id)
    {
        this.id = id;
    }

    public Long getCreatedOn ()
    {
        return createdOn;
    }

    public void setCreatedOn (Long createdOn)
    {
        this.createdOn = createdOn;
    }

    public Long getExpiryTime ()
    {
        return expiryTime;
    }

    public void setExpiryTime (Long expiryTime)
    {
        this.expiryTime = expiryTime;
    }

    public String getCreatedBy ()
    {
        return createdBy;
    }

    public void setCreatedBy (String createdBy)
    {
        this.createdBy = createdBy;
    }

    public Integer getTotalStepCount ()
    {
        return totalStepCount;
    }

    public void setTotalStepCount (Integer totalStepCount)
    {
        this.totalStepCount = totalStepCount;
    }

    @Override
    public String toString()
    {
        return "JobTrackerResponseData [resultUrl = "+resultUrl+", inputFileName = "+inputFileName+", status = "+status+", completedStepCount = "+completedStepCount+", remark = "+remark+", requestJsonEntry = "+requestJsonEntry+", qualifier = "+qualifier+", lastModifiedOn = "+lastModifiedOn+", type = "+type+", id = "+id+", createdOn = "+createdOn+", expiryTime = "+expiryTime+", createdBy = "+createdBy+", totalStepCount = "+totalStepCount+"]";
    }
}
