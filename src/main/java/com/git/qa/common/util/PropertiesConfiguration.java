package com.git.qa.common.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfiguration {
    private String filepath;

    public PropertiesConfiguration(String propertiesPath) throws Exception {
        if (propertiesPath != null) {
            this.filepath = propertiesPath;
        } else {
            throw new Exception("No properties file defined");
        }
    }

    public String getStringValue(String key) throws Exception {
        return this.readPropertyValue(key);
    }

    public int getIntegerValue(String key) throws Exception {
        return Integer.parseInt(this.readPropertyValue(key));
    }

    private String readPropertyValue(String key) throws Exception {
        Properties prop = new Properties();
        InputStream input = null;
        String result = null;

        try {
            input = this.getClass().getResourceAsStream(this.filepath);
            prop.load(input);
            result = prop.getProperty(key);
        } finally {
            if (input != null) {
                input.close();
            }

        }
        return result;
    }
}
