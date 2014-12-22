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
    private static String logPath = "logs/mailserver.log";
    /**
     * @param clazz "the class needed to print log"
     * @return
     */
    public static Logger getLogger(Class clazz) {
        Logger logger = Logger.getLogger(clazz);

        ConfigParser parser = new ConfigParser("wt_mail.properties");
        if (parser.getOption("debug") != null && parser.getOption("debug")
                .equals("true"))
            logger.setLevel(Level.DEBUG);
        else
            logger.setLevel(Level.INFO);
        
        if (!parser.getOption("logs_path").equals(""))
            logPath = parser.getOption("logs_path");

        //output to file
        FileAppender appender1 = null;
        //output to console
        ConsoleAppender appender2 = null;
        try {
            appender1 = new FileAppender(new TTCCLayout(), logPath);
            appender2 = new ConsoleAppender(new TTCCLayout());
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.addAppender(appender1);
        logger.addAppender(appender2);

        return logger;
    }
}
