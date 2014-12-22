package com.wt.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

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
        char flag = (role == Manager.MailRole.SENDER ? '0' : '1');
        String sql = "INSERT INTO mail_info VALUES (null, '" + user + "', b'" +
                flag + "', '" + message.getFrom() + "', '" + message.getTo() +
                "', '" + message.getHeader() + "', " + message.getBytes() + 
                ");";
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
}


