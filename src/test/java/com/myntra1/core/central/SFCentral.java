package com.myntra.core.central;

import com.myntra.core.central.pageLocator.PageLocatorsLoader;
import com.myntra.core.enums.ChannelUtils;
import com.myntra.core.enums.ConfigType;
import com.myntra.core.utils.IniReader;
import com.myntra.ui.Channel;
import com.myntra.utils.config.ConfigManager;
import com.myntra.utils.config.ConfigProperties;
import com.myntra.utils.logger.ILogger;
import org.testng.ITestContext;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public enum SFCentral implements ILogger {
    INSTANCE;

    private static ConfigManager CONFIG_MANAGER;
    private Map<String, IniReader> contextTestData = new HashMap<>();
    private ObjectRepository pageDefs = null;

    public synchronized void init() throws Exception {
        CONFIG_MANAGER = ConfigManager.getInstance();
        loadObjectRepository();
    }

    private synchronized void loadObjectRepository() throws Exception {
        if (null == pageDefs) {
            pageDefs = new ObjectRepository(getDirPathForConf(ConfigType.pageLocatorsDir));
        }
    }

    private String getDirPathForConf(ConfigType confConst) throws Exception {
        return getPath(CONFIG_MANAGER.getString(confConst.name()));
    }

    private String getPath(String relPath) throws Exception {
        return (new File(SFCentral.class.getClassLoader()
                                        .getResource(relPath)
                                        .toURI())).getAbsolutePath();
    }

    public synchronized void registerContext(ITestContext context) throws Exception {
        String testDataFilePath = String.format("%s/%s/%s/testdata.ini", getDirPathForConf(ConfigType.testDataDir),
                CONFIG_MANAGER.getString(ConfigProperties.ENVIRONMENT.getKey()),
                ChannelUtils.mappingChannelForLoadingData(
                        Channel.value(CONFIG_MANAGER.getString(ConfigProperties.CHANNEL.getKey())))
                            .name()
                            .toLowerCase());
        LOG.info(String.format("Loading Test Data File at Path :: %s", testDataFilePath));
        IniReader reader = new IniReader(testDataFilePath);
        contextTestData.put(context.getName()
                                   .toLowerCase(), reader);
    }

    public synchronized Map<String, String> getTestData(ITestContext context, String testSectionName) {
        LOG.info(String.format("Get Test Data for test - %s", testSectionName));

        Map<String, String> testData = contextTestData.get(context.getName()
                                                                  .toLowerCase())
                                                      .getSectionData(testSectionName);
        return testData;
    }

    public synchronized Properties convertPageLocatorsToProperties(String pageName, ChannelUtils channelName) {
        PageLocatorsLoader pageDef = getPageDef(pageName);
        return pageDef.convertToProperties(channelName);
    }

    public synchronized PageLocatorsLoader getPageDef(String pageName) {
        return pageDefs.getPageDef(pageName);
    }
}