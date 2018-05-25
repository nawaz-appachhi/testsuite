package com.myntra.apiTests.common.entries;

import com.myntra.apiTests.common.Constants.ReleaseStatus;

/**
 * Created by Shubham Gupta on 8/4/17.
 */
public class RulesEntry {
    private int id;
    private ReleaseStatus status;
    private String methodName;

    public RulesEntry() {}

    public RulesEntry(int id, ReleaseStatus status, String methodName) {
        this.id = id;
        this.status = status;
        this.methodName = methodName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ReleaseStatus getStatus() {
        return status;
    }

    public void setStatus(ReleaseStatus status) {
        this.status = status;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "RulesEntry{" +
                "id=" + id +
                ", status=" + status +
                ", methodName='" + methodName + '\'' +
                '}';
    }
}
