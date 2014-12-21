package com.wt.utils;

import com.wt.smtp.SMTPClient;
import com.wt.smtp.SMTPServer;

/**
 * MailManager is to handle all mails and server info
 * @author wangtao
 * @time 2014/12/17
 */
public class Manager {
    public static enum MailRole {SENDER, RECEIVER};
    
    /**
     * It is to handle the mail to server
     * @param message mail
     */
    public static void handleMail(MailMessage message) {
        SMTPServer.logger.debug("A mail from " + message.getFrom() + " to " +
                    message.getTo());
        
        //Store the message into databases
        MysqlDriver driver = new MysqlDriver();
        
        String fromServer = Manager.getMailServer(message.getFrom());
        if (Manager.isLocalServer(fromServer)) {
            String user = Manager.getMailUser(message.getFrom());
            driver.storeMail(message, user, MailRole.SENDER);
        }
        
        String toServer = Manager.getMailServer(message.getTo());
        if (Manager.isLocalServer(toServer)) {
            String user = Manager.getMailUser(message.getTo());
            driver.storeMail(message, user, MailRole.RECEIVER);
        }
        else {
            //Send the mail to the true server
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
        ConfigParser parser = new ConfigParser("wt_mail.properties");
        
        if (parser.getOption("server_ip").equals(server) || parser.getOption(
                "server_name").equals(server))
            return true;
        return false;
    }

    
    /**
     * Determine whether the server is my server
     * @param  server
     * @return true or false
     */
    public static boolean isMyServer(String server) {
        ConfigParser parser = new ConfigParser("wt_mail.properties");
        
        if (parser.getOption("my_server_ips").indexOf(server) != -1 || 
                parser.getOption("my_server_names").indexOf(server) != -1)
            return true;
        return false;
    }
    
    
    /**
     * Get mail server from the mail
     * @param mail
     * @return server
     */
    public static String getMailServer(String mail) {
        int pos = mail.indexOf("@");
        return mail.substring(pos + 1);
    }
    
    /**
     * Get mail user from the mail
     * @param mail
     * @return server
     */
    public static String getMailUser(String mail) {
        int pos = mail.indexOf("@");
        return mail.substring(0, pos);
    }
    
    
    /**
     * To check the password of user
     * @param user
     * @return
     */
    public static boolean authUser(User user) {
        MysqlDriver driver = new MysqlDriver();
        return driver.authUser(user.getUsername(), user.getPassword());
    }
    
    
    /**
     * To check whether user is local user
     * @param username
     * @return 
     */
    public static boolean isLocalUser(String username) {
        MysqlDriver driver = new MysqlDriver();
        return driver.hasUser(username);
    }
}
