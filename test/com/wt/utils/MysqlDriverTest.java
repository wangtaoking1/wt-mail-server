package com.wt.utils;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class MysqlDriverTest {

//    @Test
//    public void testRegisterUser() {
//        MysqlDriver driver = new MysqlDriver();
//        driver.registerUser("test0", "test0");
//    }
    
//    @Test
//    public void testStoreMail() {
//        MailMessage message = new MailMessage();
//        message.setFrom("test0@localhost");
//        message.setTo("xyz@localhost");
//        message.setContent("from: test0@localhost\nto: xyz@localhost\n" + 
//        "subject: hello\n\nhello wolrd, I am a coder!!\nI am so happy!");
//        
//        MysqlDriver driver = new MysqlDriver();
//        boolean flag = driver.storeMail(message, "test0", Manager.MailRole.SENDER);
//        Assert.assertTrue(flag);
//    }
    
//    @Test
//    public void testGetMailStatus() {
//        MysqlDriver driver = new MysqlDriver();
//        System.out.println(driver.getStatus("test1", Manager.MailRole.SENDER));
//    }
//    
//    @Test
//    public void testGetMessageContent() {
//        MysqlDriver driver = new MysqlDriver();
//        System.out.println(driver.getMessageContent(4));
//    }
    
    @Test 
    public void testReadMail() {
        MysqlDriver driver = new MysqlDriver();
        Assert.assertTrue(driver.readMail(6));
    }
}
