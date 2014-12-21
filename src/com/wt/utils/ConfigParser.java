package com.wt.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * ConfigParser is to parse the properties file
 * @author wangtao
 * @time 2014/12/20
 */
public class ConfigParser {
    private Properties prop = null;
    private Logger logger = LoggerFactory.getLogger(ConfigParser.class);

    public ConfigParser(String path) {
        prop = new Properties();
        try {
            prop.load(new FileInputStream(path));
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public String getOption(String key) {
        return this.prop.getProperty(key);
    }

    public Set<Object> getKeys() {
        return this.prop.keySet();
    }

}
