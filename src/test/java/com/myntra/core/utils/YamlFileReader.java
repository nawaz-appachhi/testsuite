package com.myntra.core.utils;

import com.myntra.core.central.testData.TestDataFile;
import com.myntra.core.exceptions.InvalidTestDataException;
import com.myntra.utils.logger.ILogger;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class YamlFileReader implements ILogger {
    public YamlFileReader() {
    }

    public static void main(String[] args) throws InvalidTestDataException {
        String ymlFileName = "/Users/300039794/src/moolya-ui-automation/src/test/resources/data/stage/desktop/testdata.yml";
        TestDataFile testDataFile = new YamlFileReader().loadTestDataYmlFile(ymlFileName);
    }

    public TestDataFile loadTestDataYmlFile(String ymlFileName) throws InvalidTestDataException {
        LOG.info(String.format("Loading Test Data file - %s", ymlFileName));
        Yaml yaml = new Yaml();
        InputStream input = null;
        TestDataFile testData = null;
        try {
            input = new FileInputStream(new File(ymlFileName));
            Map testDataFile = (Map) yaml.load(input);
            input.close();
            ArrayList allTestsData = (ArrayList) testDataFile.get("tests");
            testData = new TestDataFile().parse((HashMap) allTestsData.get(0));
            LOG.info(String.format("Loaded Test Data for %d tests", testData.getTestData()
                                                                            .size()));
            LOG.info(testData.toString());
        } catch (FileNotFoundException e) {
            LOG.error(String.format("Test Data file not found here - %s", ymlFileName));
            e.printStackTrace();
        } catch (IOException e) {
            LOG.error(String.format("Error in reading Test Data file - %s", ymlFileName));
            e.printStackTrace();
        }
        return testData;
    }
}
