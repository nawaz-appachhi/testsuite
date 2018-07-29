package com.myntra.core.utils;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class IniReader {
    private Map<String, Map<String, String>> data = new HashMap<>();

    public IniReader(String path) throws Exception {
        Ini iniFile = new Ini(new File(path));
        readTestDataFile(iniFile);
    }

    private void readTestDataFile(Ini iniFile) {
        for (String name : iniFile.keySet()) {
            Section section = iniFile.get(name);
            Map<String, String> sectionData = new HashMap<>();
            readTestDataSection(name, section, sectionData);
        }
    }

    private void readTestDataSection(String name, Section section, Map<String, String> sectionData) {
        for (String key : section.keySet()) {
            sectionData.put(key, section.get(key));
        }
        data.put(name.toLowerCase(), sectionData);
    }

    public Map<String, String> getSectionData(String section) {
        return data.get(section.toLowerCase());
    }
}
