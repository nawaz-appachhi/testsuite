package com.myntra.apiTests.erpservices.lms.lmsClient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.response.AbstractResponse;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Shubham Gupta on 7/26/17.
 */
@SuppressWarnings("serial")
@XmlRootElement(name = "mlShipmentResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonRootName("mlShipmentResponse")
public class MLShipmentResponse extends AbstractResponse {

    @XmlElementWrapper(name = "data")
    @XmlElement(name = "order")
    @JsonProperty(value = "data")
    private List<MLShipmentEntry> mlShipmentEntries;

    public MLShipmentResponse() {
    }

    public MLShipmentResponse(List<MLShipmentEntry> mlShipmentEntries,StatusResponse statusResponse) {
        super(statusResponse);
        this.mlShipmentEntries = mlShipmentEntries;
    }

    public MLShipmentResponse(StatusResponse status) {
        super(status);
    }

    public List<MLShipmentEntry> getMlShipmentEntries() {
        return mlShipmentEntries;
    }

    public void setMlShipmentEntries(List<MLShipmentEntry> mlShipmentEntries) {
        this.mlShipmentEntries = mlShipmentEntries;
    }
}
