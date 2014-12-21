package com.wt.utils;

import java.io.IOException;

import org.apache.log4j.*;

/**
 * This is a class to get a logger
 * @author wangtao
 * @time 2014/11/13
 *
 */
public class LoggerFactory {

    /**
     * @param clazz "the class needed to print log"
     * @return
     */
    public static Logger getLogger(Class clazz) {
        Logger logger = Logger.getLogger(clazz);

        logger.setLevel(Level.DEBUG);

        //output to file
        FileAppender appender1 = null;
        //output to console
        ConsoleAppender appender2 = null;
        try {
            appender1 = new FileAppender(new TTCCLayout(), "logs/mailserver.log");
            appender2 = new ConsoleAppender(new TTCCLayout());
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.addAppender(appender1);
        logger.addAppender(appender2);

        return logger;
    }
}
