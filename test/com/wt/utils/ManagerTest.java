package com.wt.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import com.wt.utils.Manager.MailRole;

public class ManagerTest {

//    @Test
//    public void testIsLocalServer() {
//        Assert.assertTrue(Manager.isLocalServer("server_0"));
//        Assert.assertTrue(Manager.isLocalServer("10.0.2.4"));
//    }
//
//    @Test
//    public void testAuthUser() {
//        User user = new User("test0", "test0");
//        Assert.assertTrue(Manager.authUser(user));
//    }
//
//    @Test
//    public void testIsLocalUser() {
//        Assert.assertTrue(Manager.isLocalUser("test0"));
//    }

    @Test
    public void testGetMailStatus() {
        System.out.println(Manager.getMailStatus("test1", MailRole.RECEIVER));
        System.out.println("--------------");
        System.out.println(Manager.getMailStatus("test1", MailRole.SENDER));
    }
    
//    @Test
//    public void testGetMailCount() {
//        System.out.println(Manager.getMailCount(MailRole.RECEIVER));
//        System.out.println("--------------");
//        System.out.println(Manager.getMailCount(MailRole.SENDER));
//    }
    
//    @Test
//    public void testGetBytes() {
//        System.out.println(Manager.getBytes(0));
//        System.out.println(Manager.getBytes(1));
//    }
//    
//    @Test 
//    public void testGetMailStatusList() {
//        System.out.println(Manager.getMailStatusList());
//    }
//    
//    @Test
//    public void testgetMailMessage() {
//        System.out.println("--------------");
//        System.out.println(Manager.getMailMessage(MailRole.RECEIVER, 1));
//        System.out.println("--------------");
//    }
//    
//    @Test
//    public void testGetMailID() {
//        ArrayList<Integer> que = new ArrayList<Integer>();
//        que.add(1);
//        que.add(3);
//        ArrayList<Integer> ids = Manager.getMailIDs(MailRole.SENDER, que);
//        for (int id : ids) {
//            System.out.println(id);
//        }
//    }
}
