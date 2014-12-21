package com.wt.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.wt.utils.ConfigParser;

public class ConfigParserTest {

    @Test
    public void testGetOption() {
        ConfigParser parser = new ConfigParser("wt_mail.properties");
        for (Object key : parser.getKeys()) {
            if (!parser.getOption((String)key).equals("")) {
                System.out.println(key + "=" + parser.getOption((String)key));
            }
        }
    }

}
