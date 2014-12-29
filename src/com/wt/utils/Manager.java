package com.wt.utils;

import java.util.ArrayList;

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
        SMTPServer.logger.info("A mail from " + message.getFrom() + " to " +
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

    /**
     * To register user
     * @param username
     * @param password
     * @return
     */
    public static boolean register(String username, String password) {
        MysqlDriver driver = new MysqlDriver();
        return driver.registerUser(username, password);
    }
    
    /**
     * To unregister user
     * @param username
     * @param password
     * @return
     */
    public static boolean unRegister(String username, String password) {
        MysqlDriver driver = new MysqlDriver();
        if (driver.authUser(username, password)) {
            return driver.deleteUser(username);
        }
        return false;
    }
    
    /**
     * To get the current mail status
     * @return
     */
    public static String getMailStatus(Manager.MailRole role) {
        MysqlDriver driver = new MysqlDriver();
        return driver.getStatus(role);
    }
    
    /**
     * To get the number of mails
     * @param role
     * @return
     */
    public static int getMailCount(MailRole role) {
        MysqlDriver driver = new MysqlDriver();
        return driver.getMailCount(role);
    }
    
    /**
     * To get the total bytes of mails
     * @param n
     * @return
     */
    public static int getBytes(int n) {
        MysqlDriver driver = new MysqlDriver();
        return driver.getMailBytes(n);
    }
    
    /**
     * To get the list of mails
     * @return
     */
    public static String getMailStatusList() {
        MysqlDriver driver = new MysqlDriver();
        return driver.getMailStatusList();
    }
    
    /**
     * To get the mail content with number num
     * @param role
     * @param num
     * @return
     */
    public static String getMailMessage(MailRole role, int num) {
        MysqlDriver driver = new MysqlDriver();
        int id = Manager.getMailID(role, num);
        driver.readMail(id);
        return driver.getMailMessage(id);
    }
    
    /**
     * To get the mail ids with the que
     * @param que
     * @return
     */
    public static ArrayList<Integer> getMailIDs(MailRole role, 
            ArrayList<Integer> que) {
        MysqlDriver driver = new MysqlDriver();
        ArrayList<Integer> ids = driver.getMailIDs(role);
        ArrayList<Integer> mail_ids = new ArrayList<Integer>();
        for (int i = 1; i <= ids.size(); i++) {
            if (que.contains(i)) {
                mail_ids.add(ids.get(i - 1));
            }
        }
        return mail_ids;
    }
    
    
    /**
     * To get the mail id of number cnt mail
     * @param role
     * @param cnt
     * @return
     */
    public static int getMailID(MailRole role, int cnt) {
        MysqlDriver driver = new MysqlDriver();
        ArrayList<Integer> ids = driver.getMailIDs(role);
        return ids.get(cnt - 1);
    }
    
    
    /**
     * To delete mail with the id
     * @param id
     */
    public static void delMail(int id) {
        MysqlDriver driver = new MysqlDriver();
        driver.deleteMail(id);
    }
    
    
    /**
     * To get the header and the top num line
     * @param id
     * @param lines
     * @return
     */
    public static String getTopContend(int id, int num) {
        MysqlDriver driver = new MysqlDriver();
        String content = driver.getMessageContent(id);
        
        String[] items = content.split("\n\n", 2);
        StringBuffer retBuf = new StringBuffer();
        retBuf.append(items[0] + "\n\n");
        
        if (items.length == 2) {
            String[] lines = items[1].split("\n");
            for (int i = 1; i <= num && i <=lines.length; i++)
                retBuf.append(lines[i - 1] + "\n");
        }
        return retBuf.toString();
    }
}

