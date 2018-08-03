package com.myntra.core.central;

import com.myntra.core.central.pageLocator.PageLocatorsLoader;
import com.myntra.core.central.testData.DataCreator;
import com.myntra.core.enums.ChannelUtils;
import com.myntra.core.enums.ConfigType;
import com.myntra.core.exceptions.NoTestDataException;
import com.myntra.core.utils.IniReader;
import com.myntra.testdata.models.pojo.response.Coupon;
import com.myntra.testdata.models.pojo.response.MyntData;
import com.myntra.testdata.models.pojo.response.User;
import com.myntra.ui.Channel;
import com.myntra.utils.config.ConfigManager;
import com.myntra.utils.config.ConfigProperties;
import com.myntra.utils.logger.ILogger;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Properties;

import static com.myntra.core.utils.JavaUtils.generateUserNameForTest;

public enum SFCentral implements ILogger {
    INSTANCE;

    private static ConfigManager CONFIG_MANAGER;
    private ObjectRepository pageDefs = null;
    private HashMap<String, MyntData> loadedTestsData;
    private String environment;

    public synchronized void init() throws Exception {
        CONFIG_MANAGER = ConfigManager.getInstance();
        environment = CONFIG_MANAGER.getString(ConfigProperties.ENVIRONMENT.getKey());
        loadObjectRepository();
        loadedTestsData = new HashMap<>();
    }

    private synchronized void loadObjectRepository() throws Exception {
        if (null == pageDefs) {
            pageDefs = new ObjectRepository(getDirPathForConf(ConfigType.pageLocatorsDir));
        }
    }

    private String getDirPathForConf(ConfigType confConst) {
        return getPath(CONFIG_MANAGER.getString(confConst.name()) + File.separator + environment);
    }

    private String getPath(String relPath) {
        try {
            return (new File(SFCentral.class.getClassLoader()
                                            .getResource(relPath)
                                            .toURI())).getAbsolutePath();
        } catch (URISyntaxException e) {
            throw new NoTestDataException(String.format("Error in getting absolute path to file - %s\n%s", relPath, ExceptionUtils.getStackTrace(e)));
        }
    }

    private HashMap<String, MyntData> loadTestData() {
        Channel channelName = Channel.value(CONFIG_MANAGER.getString(ConfigProperties.CHANNEL.getKey()));
        String channelForTestData = ChannelUtils.mappingChannelForLoadingData(channelName)
                                                .name()
                                                .toLowerCase();
        return loadTestDataFor(channelForTestData);
    }

    private HashMap<String, MyntData> loadTestDataFor(String channel) {
        String commonTestDataFilePath = String.format("%s/commonTestData.ini", getDirPathForConf(ConfigType.testDataDir));
        String testDataFilePath = String.format("%s/%s/testdata.ini", getDirPathForConf(ConfigType.testDataDir), channel);
        LOG.info(String.format("Loading Common Test Data File at Path :: %s", commonTestDataFilePath));
        LOG.info(String.format("Loading Test Data File at Path :: %s", testDataFilePath));
        IniReader iniReader = new IniReader(commonTestDataFilePath, testDataFilePath);
        HashMap<String, MyntData> convertedTestData = iniReader.convert();
        if ("prod".equalsIgnoreCase(environment)) {
            LOG.info("Running tests in environment. We will use the provided test data in file");
        } else {
            DataCreator dataCreator = new DataCreator();
            dataCreator.addStylesToCouponGroup(iniReader.getStyleIds());
        }

        return convertedTestData;
    }

    public synchronized MyntData getTestData(String testName) {
        LOG.info(String.format("Get Test Data for test - %s", testName));

        if (loadedTestsData.isEmpty()) {
            LOG.info("Test data is not loaded. Loading it now.");
            loadedTestsData = loadTestData();
        }
        MyntData testDataFromFile = loadedTestsData.get(testName.toLowerCase());
        if (null == testDataFromFile) {
            LOG.info(String.format("No Test Data provided for test - %s", testName));
            testDataFromFile = new MyntData();
        }
        MyntData testData = createAndUpdateTestData(testName, testDataFromFile);

        LOG.info(String.format("Test: %s :: Loaded Test Data", testName));
        return testData;
    }

    private MyntData createAndUpdateTestData(String testName, MyntData testDataFromFile) {
        if ("prod".equalsIgnoreCase(environment)) {
            LOG.info("Running tests in environment. We will use the provided test data in file");
        } else {
            String userName = generateUserNameForTest(testName);
            String password = "password";

            DataCreator dataCreator = new DataCreator();
            User newUser = dataCreator.createNewUser(testName, userName, password);
            newUser.setAdditionalMapProperties(testDataFromFile.getUser()
                                                               .getAdditionalProperties());
            testDataFromFile.setUser(newUser);

            createAndAddPersonalisedCouponIFSpecifiedInDataFile(dataCreator, testDataFromFile);
            enableMyntCouponForUserIfSpecifiedInDataFile(dataCreator, testDataFromFile);
            createAndAddLoyaltyPointsIfSpecifiedInDataFile(dataCreator, testDataFromFile);
        }
        return testDataFromFile;
    }

    private void createAndAddLoyaltyPointsIfSpecifiedInDataFile(DataCreator dataCreator, MyntData testDataFromFile) {
        String loyaltyPoint = (String) testDataFromFile.getCoupon()
                                                       .getAdditionalProperties()
                                                       .get("loyaltyPoint");
        if ((null != loyaltyPoint)) {
            dataCreator.addLoyaltyPointsForUser(testDataFromFile.getUser(), loyaltyPoint);
        }
    }

    private void enableMyntCouponForUserIfSpecifiedInDataFile(DataCreator dataCreator, MyntData testDataFromFile) {
        if ((null != testDataFromFile.getCoupon()
                                     .getAdditionalProperties()
                                     .get("MyntCoupon"))) {
            String myntCouponForUser = dataCreator.createMyntCouponForUser(testDataFromFile.getUser());
            testDataFromFile.getCoupon()
                            .setAdditionalProperty("MyntCoupon", myntCouponForUser);
        }
    }

    private void createAndAddPersonalisedCouponIFSpecifiedInDataFile(DataCreator dataCreator, MyntData testDataFromFile) {
        if (shouldCreateNewPersonalisedCoupon(testDataFromFile)) {
            Coupon newCoupon = dataCreator.createNewCoupon(testDataFromFile.getUser()
                                                                           .getEmail(), testDataFromFile.getUser()
                                                                                                        .getPassword());
            newCoupon.setAdditionalMapProperties(testDataFromFile.getCoupon()
                                                                 .getAdditionalProperties());
            testDataFromFile.setCoupon(newCoupon);
        }
    }

    private boolean shouldCreateNewPersonalisedCoupon(MyntData testDataFromFile) {
        return (null != testDataFromFile.getCoupon()
                                        .getCouponCode()) && (null == testDataFromFile.getCoupon()
                                                                                      .getAdditionalProperties()
                                                                                      .get("InvalidPersonalisedCoupons"));
    }

    public synchronized Properties convertPageLocatorsToProperties(String pageName, ChannelUtils channelName) {
        PageLocatorsLoader pageDef = getPageDef(pageName);
        return pageDef.convertToProperties(channelName);
    }

    public synchronized PageLocatorsLoader getPageDef(String pageName) {
        return pageDefs.getPageDef(pageName);
    }
}