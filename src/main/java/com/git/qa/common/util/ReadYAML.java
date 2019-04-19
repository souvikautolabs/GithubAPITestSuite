package com.git.qa.common.util;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;

public class ReadYAML {

    public static HashMap readYAMLValuesAsHashMap(String file) throws Exception {
        Yaml yaml = new Yaml();
        InputStream input = null;
        HashMap result = null;

        try {
            input = ReadYAML.class.getResourceAsStream(file);
            result = (HashMap)yaml.load(input);
        } finally {
            if (input != null) {
                input.close();
            }
        }
        return result;
    }
}
