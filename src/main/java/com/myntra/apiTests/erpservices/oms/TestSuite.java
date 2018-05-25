package com.myntra.apiTests.erpservices.oms;

import java.util.ArrayList;

/**
 * @author abhijit.pati
 * @since 12th Aug 2016
 */
public class TestSuite {

    private ArrayList<Testcase> testCaseList;
    private String name;


    public TestSuite(String name) {
        this.name = name;
        testCaseList = new ArrayList<Testcase>();
    }

    public ArrayList<Testcase> getTestCaseList() {
        return testCaseList;
    }

    public void addTestCase(Testcase testCase) {
        testCaseList.add(testCase);
    }

    public void setTestCaseList(ArrayList<Testcase> testCaseList) {
        this.testCaseList = testCaseList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
