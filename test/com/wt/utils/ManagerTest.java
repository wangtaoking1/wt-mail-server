package com.wt.utils;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class ManagerTest {

    @Test
    public void testIsLocalServer() {
        Assert.assertTrue(Manager.isLocalServer("mail_server_0"));
        Assert.assertTrue(Manager.isLocalServer("10.0.2.4"));
    }

    @Test
    public void testAuthUser() {
        User user = new User("test0", "test0");
        Assert.assertTrue(Manager.authUser(user));
    }

    @Test
    public void testIsLocalUser() {
        Assert.assertTrue(Manager.isLocalUser("test0"));
    }

}
