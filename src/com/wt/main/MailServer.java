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
    public static void main(String[] args) {
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
            System.out.println(e);
        }
        System.out.println("end...");
    }
}
