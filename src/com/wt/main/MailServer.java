package com.wt.main;

import org.apache.log4j.Logger;

import com.wt.smtp.SMTPServer;
import com.wt.utils.LoggerFactory;

/**
 * @author wangtao
 * @time 2014/11/12
 */
public class MailServer {
    public static Logger logger = LoggerFactory.getLogger(MailServer.class);
    /**
     * @param args
     */
    public static void main(String[] args) {
        logger.info("server starting ...");
        Thread smtpForClient = new Thread(new SMTPServer(
                                                SMTPServer.ServerType.FORCLIENT, 465));
        Thread smtpForServer = new Thread(new SMTPServer(
                                                SMTPServer.ServerType.FORSERVER, 25));
        smtpForClient.start();
        smtpForServer.start();
        try {
            smtpForClient.join();
            smtpForServer.join();
        }
        catch (Exception e) {
            logger.error(e);
        }
        
        logger.info("server endding ...");
    }
}
