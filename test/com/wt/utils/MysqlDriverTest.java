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
//    
//    @Test
//    public void testStoreMail() {
//        MailMessage message = new MailMessage();
//        message.setFrom("test0@localhost");
//        message.setTo("xyz@localhost");
//        message.setContent("hello wolrd!!");
//        
//        MysqlDriver driver = new MysqlDriver();
//        boolean flag = driver.storeMail(message, "test0", Manager.MailRole.SENDER);
//        Assert.assertTrue(flag);
//    }
    
    @Test
    public void testGetMailStatus() {
        MysqlDriver driver = new MysqlDriver();
        System.out.println(driver.getStatus(Manager.MailRole.RECEIVER));
    }
    
    
}
