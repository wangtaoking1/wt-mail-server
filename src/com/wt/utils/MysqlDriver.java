package com.wt.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.wt.utils.Manager.MailRole;

/**
 * MysqlDriver is to communicate with mysql
 * @author wangtao
 * @time 2014/12/20
 */
public class MysqlDriver {
    public Logger logger = LoggerFactory.getLogger(MysqlDriver.class);
    
    private String mysql_url = "127.0.0.1:3306/wt_mail";
    private String mysql_user = "root";
    private String mysql_pw = "admin123";
    
    private Statement stmt;
    

    public MysqlDriver() {
        this.readConfigs();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(
                                 "jdbc:mysql://127.0.0.1:3306/wt_mail", "root",
                                 "admin123");
            stmt = con.createStatement();
        } catch (Exception e) {
            logger.error("Mysql connection failed.");
            logger.error(e);
        }
    }

    /**
     * To read the mysql configuration options
     */
    private void readConfigs() {
        ConfigParser parser = new ConfigParser("wt_mail.properties");
        if (!parser.getOption("mysql_url").equals("")) {
            mysql_url = parser.getOption("mysql_url");
        }
        if (!parser.getOption("mysql_user").equals("")) {
            mysql_user = parser.getOption("mysql_user");
        }
        if (!parser.getOption("mysql_pw").equals("")) {
            mysql_pw = parser.getOption("mysql_pw");
        }
    }
    
    
    /**
     * registerUser function is to register a user
     * @param user
     * @param pw
     * @return whether or not insert successfully
     */
    public boolean registerUser(String user, String pw) {
        String sql = "INSERT INTO user (username, password) VALUES ('" + user 
                + "', '" + pw + "');";
        
        logger.debug(sql);
        
        try {
            this.stmt.executeUpdate(sql);
            return true;
        }
        catch (SQLException e) {
            logger.error(e);
            return false;
        }
    }
    
    
    /**
     * To delete a user
     * @param user
     * @param pw
     * @return success or fail
     */
    public boolean deleteUser(String username) {
        String sql = "DELETE FROM user WHERE username='" + username + "';";
        
        logger.debug(sql);
        
        try {
            this.stmt.executeUpdate(sql);
            return true;
        }
        catch (SQLException e) {
            logger.error(e);
            return false;
        }
    }
    
    /**
     * To store the message into database
     * @param message
     * @param user
     * @param role
     * @return success or fail
     */
    public boolean storeMail(MailMessage message, String user, 
            Manager.MailRole role) {
        String sql = "INSERT INTO mail_info VALUES (null, '" + user + "', b'" +
                role.ordinal() + "', '" + message.getFrom() + "', '" + 
                message.getTo() + "', '" + message.getHeader() + "', " + 
                message.getBytes() + ");";
        logger.debug(sql);
        
        int mail_id = -1;
        try {
            mail_id = executeAndGetId(sql);
        
            if (mail_id == -1) {
                logger.error("Get the mail id failed.");
                return false;
            }
        }
        catch (Exception e) {
            logger.error(e);
            return false;
        }
        
        sql = "INSERT INTO message VALUES (null, " + mail_id + ", '" 
                + message.getContent() + "', b'0');";
        logger.debug(sql);
        
        try {
            this.stmt.executeUpdate(sql);
            return true;
        }
        catch (SQLException e) {
            logger.error(e);
            return false;
        }
    }
    
    
    /**
     * To get the id after execute the sql
     * @param sql
     * @return id
     */
    private int executeAndGetId(String sql) throws Exception {
        int id = -1;
        stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next())
            id = rs.getInt(1);
        return id;
    }
    
    
    /**
     * To check whether the user is the local user
     * @param username
     * @return
     */
    public boolean hasUser(String username) {
        String sql = "SELECT * FROM user WHERE username='" +username + "';"; 
        logger.debug(sql);
        
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                return true;
            else
                return false;
        }
        catch (Exception e) {
            logger.error(e);
            return false;
        }
    }
    
    
    /**
     * To auth the user
     * @param username
     * @param password
     * @return
     */
    public boolean authUser(String username, String password) {
//        logger.debug(username + " : " + password);
        String sql = "SELECT * FROM user WHERE username='" +username + "';"; 
        logger.debug(sql);
        
        logger.debug(sql);
        
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String pw = rs.getString("password");
                if (pw.equals(password))
                    return true;
                else
                    return false;
            }
            else
                return false;
        }
        catch (Exception e) {
            logger.error(e);
            return false;
        }
    }
    
    /**
     * To get the current mail status
     * @param role
     * @return
     */
    public String getStatus(Manager.MailRole role) {
        String sql = "SELECT Count(*), Sum(bytes) FROM mail_info " + 
                "WHERE role=" + role.ordinal() + ";";
        
        logger.debug(sql);
        
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int cnt = rs.getInt(1);
                //When there are no mails
                if (cnt == 0)
                    return "0 0";
                else {
                    int bytes = rs.getInt(2);
                    return cnt + " " + bytes;
                }
            }
            else
                return "0 0";
        }
        catch (Exception e) {
            logger.error(e);
            return "0 0";
        }
    }
    
    /**
     * To get the count of mails
     * @param role
     * @return
     */
    public int getMailCount(Manager.MailRole role) {
        String sql = "SELECT Count(*) FROM mail_info " + 
                "WHERE role=" + role.ordinal() + ";";
        
        logger.debug(sql);
        
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int cnt = rs.getInt(1);
                return cnt;
            }
            else
                return 0;
        }
        catch (Exception e) {
            logger.error(e);
            return 0;
        }
    }
    
    
    /**
     * To get the bytes of mails
     * @param n "mail number"
     * @return
     */
    public int getMailBytes(int n) {
        if (n == 0) {
            String sql = "SELECT Sum(bytes) FROM mail_info " + 
                    "WHERE role=1;";
            
            logger.debug(sql);
            
            try {
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    int cnt = rs.getInt(1);
                    return cnt;
                }
                else
                    return 0;
            }
            catch (Exception e) {
                logger.error(e);
                return 0;
            }
        }
        else {
            String sql = "SELECT bytes FROM mail_info " + 
                    "WHERE role=1;";
            
            logger.debug(sql);
            
            try {
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    int cnt = rs.getInt(n);
                    return cnt;
                }
                else
                    return 0;
            }
            catch (Exception e) {
                logger.error(e);
                return 0;
            }
        }
    }
    
    /**
     * To the list of mails status
     * @return
     */
    public String getMailStatusList() {
        String sql = "SELECT bytes FROM mail_info " + 
                "WHERE role=1;";
        
        logger.debug(sql);
        
        StringBuffer buffer = new StringBuffer();
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int rid = rs.getRow();
                int cnt = rs.getInt("bytes");
                buffer.append(rid + " " + cnt + "\n");
            }
        }
        catch (Exception e) {
            logger.error(e);
        }
        return buffer.toString();
    }
    
    /**
     * To get the mail content with number num
     * @param role
     * @param num
     * @return
     */
    public String getMailMessage(MailRole role, int num) {
        String sql = "SELECT mail_info.mail_id, message.content FROM mail_info"
                + ", message WHERE role=" + role.ordinal() + 
                " and mail_info.mail_id=message.mail_id;";
        
        logger.debug(sql);
        
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (num == rs.getRow())
                    return rs.getString("content");
            }
        }
        catch (Exception e) {
            logger.error(e);
        }
        return "";
    }
    
    
    /**
     * To get all mail ids
     * @param role
     * @return
     */
    public ArrayList<Integer> getMailIDs(MailRole role) {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        String sql = "SELECT mail_id FROM mail_info WHERE role=" + 
                role.ordinal() + ";";
        
        logger.debug(sql);
        
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ids.add(rs.getInt("mail_id"));
            }
        }
        catch (Exception e) {
            logger.error(e);
        }
        return ids;
    }
    
    /**
     * To delete the mail with the id
     * @param id
     * @return
     */
    public boolean deleteMail(int id) {
        String sql = "DELETE * FROM mail_info WHERE mail_id=" + id + ";";
        
        logger.debug(sql);
        
        try {
            this.stmt.executeUpdate(sql);
            return true;
        }
        catch (SQLException e) {
            logger.error(e);
            return false;
        }
    }
}


