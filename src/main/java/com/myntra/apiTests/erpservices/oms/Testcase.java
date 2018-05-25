package com.myntra.apiTests.erpservices.oms;

import org.apache.commons.collections.map.CaseInsensitiveMap;

import java.util.Map;

/**
 * @author abhijit.pati
 * @since 12/08/16
 */
public class Testcase {

    private Map<String, String> input;
    private Map output;
    private String description;

    public Map getOutput() {
        return output;
    }

    public void setOutput(Map output) {
        this.output = output;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Map<String, String> getInput() {
        return input;
    }

    public void setInput(Map<String, String> input) {
        this.input = input;
    }

    public Testcase(String description) {
        this.description = description;
        this.input = new CaseInsensitiveMap();
        this.output = new CaseInsensitiveMap();
    }
}
