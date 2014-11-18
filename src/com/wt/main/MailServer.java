package com.wt.main;

import com.wt.smtp.SMTPServer;

/**
 * @author wangtao
 * @time 2014/11/12
 */
public class MailServer {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
        (new SMTPServer(SMTPServer.ServerType.FORCLIENT, 25)).start();
    }
    
}
