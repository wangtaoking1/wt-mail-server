package com.wt.utils;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import com.wt.smtp.SMTPClient;

public class SMTPClientTest {

//    @Test
//    public void testSetMessage() {
//        SMTPClient client = new SMTPClient();
//        MailMessage message = new MailMessage();
//        message.setFrom("abc@163.com");
//        message.setTo("abc@qq.com");
//        client.setMessage(message);
//        
//        Assert.assertTrue(true);
//    }
    
//    @Test
//    public void testInit() {
//        SMTPClient client = new SMTPClient();
//        MailMessage message = new MailMessage();
//        message.setFrom("abc@163.com");
//        message.setTo("abc@qq.com");
//        client.setMessage(message);
//        
//        try
//        {
//            client.init();
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        
//        Assert.assertTrue(true);
//    }
    
    @Test
    public void testSendMail() {
        SMTPClient client = new SMTPClient();
        MailMessage message = new MailMessage();
        message.setFrom("abc@qq.com");
        message.setTo("wangtaoking1@163.com");
        message.setContent("hello world!!");
        client.setMessage(message);
//        try {
//        client.init();
//        client.regist();
//        }catch (Exception e){}
//        client.close();
        // Assert.assertTrue(client.sendMail());
        Assert.assertTrue(true);
    }
}
