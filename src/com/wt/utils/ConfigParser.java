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
    private FileInputStream inputStream = null;

    public ConfigParser(String path) {
        prop = new Properties();
        try {
            inputStream = new FileInputStream(path);
            prop.load(inputStream);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public String getOption(String key) {
        return this.prop.getProperty(key);
    }

    public Set<Object> getKeys() {
        return this.prop.keySet();
    }
    
    public void closeFile() {
        if (this.inputStream != null) {
            try {
                this.inputStream.close();
                this.inputStream = null;
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}
