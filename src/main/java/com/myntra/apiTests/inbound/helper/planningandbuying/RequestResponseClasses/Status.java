package com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses;

/**
 * Created by 300000929 on 16/01/17.
 */
public class Status {

        private Integer statusCode;
        private String totalCount;
        private String statusType;
        private String statusMessage;

        public Status() {

        }

    public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public String getStatusType() {
            return statusType;
        }

        public void setStatusType(String statusType) {
            this.statusType = statusType;
        }

        public String getStatusMessage() {
            return statusMessage;
        }

        public void setStatusMessage(String statusMessage) {
            this.statusMessage = statusMessage;
        }

        @Override
        public String toString() {
            return "ClassPojo [statusCode = " + statusCode + ", totalCount = " + totalCount + ", statusType = " + statusType + ", statusMessage = " + statusMessage + "]";
        }
    }

