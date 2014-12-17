package com.wt.main;

import com.wt.smtp.SMTPServer;
import java.util.regex.Pattern;

/**
 * @author wangtao
 * @time 2014/11/12
 */
public class MailServer {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
        (new SMTPServer(SMTPServer.ServerType.FORCLIENT, 465)).start();
        //(new SMTPServer(SMTPServer.ServerType.FORSERVER, 25)).start();
    }
}
