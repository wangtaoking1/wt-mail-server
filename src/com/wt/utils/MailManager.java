package com.wt.utils;

import com.wt.smtp.SMTPClient;
import com.wt.smtp.SMTPServer;

/**
 * MailManager is to handle all mails and server info
 * @author wangtao
 * @time 2014/12/17
 */
public class MailManager {

    /**
     * It is to handle the mail to server
     * @param message mail
     */
    public static void handleMail(MailMessage message) {
        String server = MailManager.getMailServer(message.getTo());
        if (isLocalServer(server)) {
            //TODO: store the mail into databases
        } else {
            //TODO: store the mail into databases
            SMTPClient client = new SMTPClient(message);
            boolean flag = client.sendMail();
            SMTPServer.logger.debug("send successfully");

            if (!flag) {
                //TODO: Add the mail to Sending_Queue
            }
        }
    }

    /**
     * Determine whether the server is my local server
     * @param  server
     * @return true or false
     */
    public static boolean isLocalServer(String server) {
        if (server.equals("10.0.2.4")) {
            return true;
        }
        return false;
    }

    /**
     * Get mail server from the mail
     * @param mail
     * @return server
     */
    private static String getMailServer(String mail) {
        int pos = mail.indexOf("@");
        return mail.substring(pos + 1);
    }
}
